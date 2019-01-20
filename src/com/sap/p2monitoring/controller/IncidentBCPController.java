package com.sap.p2monitoring.controller;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.DevCompDAO;
import com.sap.p2monitoring.dao.DevIncidentDAO;
import com.sap.p2monitoring.dao.IncidentBCPDAO;
import com.sap.p2monitoring.dao.NotWorkingComponentsDAO;
import com.sap.p2monitoring.dao.TagIncidentDAO;
import com.sap.p2monitoring.dao.TaggingRulesDAO;
import com.sap.p2monitoring.model.DevComp;
import com.sap.p2monitoring.model.DevIncident;
import com.sap.p2monitoring.model.IncidentBCP;

@Controller
@RequestMapping("/incidentBCP")
public class IncidentBCPController {

	@Autowired
	private DevIncidentDAO devIncidentDAO;
	
	@Autowired
	private IncidentBCPDAO incidentDao;

	@Autowired
	private DevCompDAO devCompDAO;

	@Autowired
	private TagIncidentDAO tagIncidentDAO;

	@Autowired
	private NotWorkingComponentsDAO notWorkingComponentsDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentBCP> findAll() {
		return incidentDao.getAllIncidents();
	}

	@RequestMapping(value = "/{incidentID}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentBCP> findById(@PathVariable String incidentID) {
		return incidentDao.getIncidentById(incidentID);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<IncidentBCP> create(@RequestBody List<IncidentBCP> incident) {
		if (incident != null) {
			Date newDate = new Date();

			// Remove components that CIM is not working atm
			int j = 0;
			while (j < incident.size()) {
				if (notWorkingComponentsDAO.getByComponentCode(incident.get(j).getComponent()) != null) {
					incident.remove(j);
				}
				j++;
			}
			//Creating the incident in our DB
			for (int i = 0; i < incident.size(); i++) {
				if (incidentDao.getByIncidentIDandUploadDateTime(incident.get(i).getIncidentID(),
						incident.get(i).getUploadDateTime()) == null) {
					incident.get(i).setUploadDateTime(newDate);
					incidentDao.createIncident(incident.get(i));
				} else {
					// here we should send an error message in some way
				}

				//Verify if the processing organization is from a Developer Org.
				if (incident.get(i).getProcessingOrg().startsWith("DS")
						&& !(incident.get(i).getProcessorID().equals(""))
						&& !((incident.get(i).getComponent().equals("")))) {
					// if there isn't a Developer X Component relationship
					if (devCompDAO.getByIDAndComponent(incident.get(i).getProcessorID(),
							incident.get(i).getComponent()) == null) {
						if (incident.get(i).getProcessorID() != ""
								&& !(incident.get(i).getProcessorID().equals(null))) {
							// create a new relationship between them
							DevComp newDevComp = new DevComp();
							newDevComp.setComponent(incident.get(i).getComponent());
							newDevComp.setDeveloperID(incident.get(i).getProcessorID());
							newDevComp.setLastTimeUpdate(new Date());
							newDevComp.setLevel(1);
							newDevComp.setTimesDevComp(1);

							// Insert at DevComp Table
							devCompDAO.createDevComp(newDevComp);
							
							//create the connection between this Processor and the IncidentID
							DevIncident newDevIncident = new DevIncident();
							newDevIncident.setIncidentID(incident.get(i).getIncidentID());
							newDevIncident.setUser(incident.get(i).getProcessorID());
							
							//Insert at DevIncidentTable
							devIncidentDAO.createDevIncident(newDevIncident);							
						}
					} else {
						//If there is none relation between this incident and the processor, 
						//we did that to ensure that we do not count many times the same incident as Component works
						if(devIncidentDAO.getDevIncidentByIncidentIDandUser(incident.get(i).getIncidentID(), incident.get(i).getProcessorID()) == null)
						{
							// create a new relationship between them
							DevComp newDevComp = devCompDAO.getByIDAndComponent(incident.get(i).getProcessorID(), incident.get(i).getComponent());
							newDevComp.setTimesDevComp(newDevComp.getTimesDevComp() + 1);
	
							// update Number of DevComp situations
							devCompDAO.updateDevCompTimes(newDevComp.getDeveloperID(), newDevComp.getComponent(), newDevComp);
							
							//create the connection between this Processor and the IncidentID
							DevIncident newDevIncident = new DevIncident();
							newDevIncident.setIncidentID(incident.get(i).getIncidentID());
							newDevIncident.setUser(incident.get(i).getProcessorID());
							
							//Insert at DevIncidentTable
							devIncidentDAO.createDevIncident(newDevIncident);											
						}
					}
				}
			}
			// UPDATES
			// Split in GroupA and GroupB
			tagIncidentDAO.getAllTaggedAndSplit();
			//Update Lists of Delayed and ServiceRequestCreated
			tagIncidentDAO.checkSRandTagUpdating();

		}
		return incident;
	}

	/**
	 * Gets number of days between two dates. Ignoring weekends.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return Credits to Samir Machado de Oliveira '@samir-machado-de-oliveira'
	 *         from stackoverflow
	 * 
	 */
	public static int getDaysBetweenIgnoreWeekends(DateTime startDate, DateTime endDate) {
		// If the start date is equal to the closing date, spent 0 days
		if (startDate.equals(endDate))
			return 0;

		// A number that represents the day for the start date, Monday = 1 ,
		// Tuesday = 2 , Wednesday = 3 ...
		int dayOfWeekStartDateNumber = startDate.getDayOfWeek();

		// If the starting date is Saturday or Sunday , pretend to be Monday
		if (dayOfWeekStartDateNumber == 6 || dayOfWeekStartDateNumber == 7) {
			int DaysToAdd = 8 - dayOfWeekStartDateNumber;
			startDate = startDate.plusDays(DaysToAdd);
			dayOfWeekStartDateNumber = Integer.valueOf(startDate.dayOfWeek().getAsString());
		}

		// How many days have passed counting weekends
		int days = Days.daysBetween(startDate, endDate).getDays();

		// How many weeks have passed
		int weeks = days / 7;
		// Excess days left. E.g. one week and three days the excess will be 3
		int excess = days % 7;

		// Excess of days spent for the weekend , then it must be removed two
		// days
		// the final number of days
		if (excess + dayOfWeekStartDateNumber >= 6) {
			// Week count * 5 working days + excess days - the weekend that
			// excess crossed
			return weeks * 5 + excess - 2;
		}
		// Weeks count * 5 working days + excess days
		return weeks * 5 + excess;
	}

	// need changes becuase of the new ID LastUpdateBySAP, if needed
	@RequestMapping(value = "/{incidentID}/{uploadDateTime}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String incidentID, @PathVariable Date uploadDateTime) {
		incidentDao.deleteIncident(incidentID, uploadDateTime);
	}

	// need changes becuase of the new ID LastUpdateBySAP, if needed
	@RequestMapping(value = "/{incidentID}/{uploadDateTime}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody IncidentBCP setOccupied(@PathVariable String incidentID, @PathVariable Date uploadDateTime,
			@RequestBody IncidentBCP incident) {
		return incidentDao.updateIncident(incidentID, uploadDateTime, incident);
	}

}
