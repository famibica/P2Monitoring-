package com.sap.p2monitoring.controller;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.IncidentICPDAO;
import com.sap.p2monitoring.dao.IncidentICPStatusDAO;
import com.sap.p2monitoring.model.IncidentICP;

@Controller
@RequestMapping("/incidentICP")
public class IncidentICPController {

	@Autowired
	private IncidentICPDAO incidentICPDao;

	@Autowired
	private IncidentICPStatusDAO incidentICPStatusDao;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentICP> findAll() {
		return incidentICPDao.getAllIncidents();
	}

	@RequestMapping(value = "/{objectID}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentICP> findById(@PathVariable String objectID) {
		return incidentICPDao.getIncidentById(objectID);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<IncidentICP> create(@RequestBody List<IncidentICP> object) {
		if (object != null) {
			int statusActionNeeded = 0;
			int statusActionNeededT1 = 0;
			int statusActionNeededT2 = 0;
			int statusActionNeededT3 = 0;
			int statusActionNeededT4 = 0;
			int statusActionNeededT5 = 0;
			int statusNew = 0;
			int statusNewT1 = 0;
			int statusNewT2 = 0;
			int statusNewT3 = 0;
			int statusNewT4 = 0;
			int statusNewT5 = 0;
			int statusInProcess = 0;
			int statusHandover = 0;
			int statusHandoverT1 = 0;
			int statusHandoverT2 = 0;
			int statusHandoverT3 = 0;
			int statusHandoverT4 = 0;
			int statusHandoverT5 = 0;
			
			Date newDate = new Date();
					
			for (int i = 0; i < object.size(); i++) {
				if(incidentICPDao.getIncidentIDandByUploadDateTime(object.get(i).getObjectID(), newDate)== null){
					
					object.get(i).setUploadDateTime(newDate);
										
					incidentICPDao.createIncident(object.get(i));
					DateTime changedDate = new DateTime (object.get(i).getChangedDate());
					DateTime newTime = new DateTime(newDate);
					Duration duration = new Duration(changedDate, newTime);
									
					if(object.get(i).getPriority() == "Very_High")
					{
						
						switch(object.get(i).getStatus()){
							case  "Action needed":
									statusActionNeeded++;
									if(duration.getStandardMinutes() < 30){
										statusActionNeededT1++;
									}else
									{
										if(duration.getStandardMinutes() > 30)
										{
											statusActionNeededT4++;
										}else
										{
											if(duration.getStandardMinutes() > 60)
											{
												statusActionNeededT5++;
											}
										}
									}
									break;
							case  "Handover":
									statusHandover++;
									if(duration.getStandardMinutes() < 30){
										statusHandoverT1++;
									}else
									{
										if(duration.getStandardMinutes() > 30)
										{
											statusHandoverT4++;
										}else
										{
											if(duration.getStandardMinutes() > 60)
											{
												statusHandoverT5++;
											}
										}
									}
									break;
							case  "New":
									statusNew++;
									if(duration.getStandardMinutes() < 30){
										statusNewT1++;
									}else
									{
										if(duration.getStandardMinutes() > 30)
										{
											statusNewT4++;
										}else
										{
											if(duration.getStandardMinutes() > 60)
											{
												statusNewT5++;
											}
										}
									}
									break;
							case  "In Process":
									statusInProcess++;
									break;
						}
					
					}			
					else
					{
						
						switch(object.get(i).getStatus()){
							case  "Action needed":
									statusActionNeeded++;
									if(duration.getStandardMinutes() < 60)
									{
										statusActionNeededT1++;
									}else
									{
										if(duration.getStandardMinutes() < 120)
										{
											statusActionNeededT2++;
										}else
										{
											if(duration.getStandardMinutes() < 180)
											{
												statusActionNeededT3++;
											}else
											{
												if(duration.getStandardMinutes() < 240)
												{
													statusActionNeededT4++;
												}else
												{
													statusActionNeededT5++;
												}												
											}
										}										
									}
									break;
							case  "Handover":
									statusHandover++;
									if(duration.getStandardMinutes() < 60)
									{
										statusHandoverT1++;
									}else
									{
										if(duration.getStandardMinutes() < 120)
										{
											statusHandoverT2++;
										}else
										{
											if(duration.getStandardMinutes() < 180)
											{
												statusHandoverT3++;
											}else
											{
												if(duration.getStandardMinutes() < 240)
												{
													statusHandoverT4++;
												}else
												{
													statusHandoverT5++;
												}												
											}
										}										
									}
									break;
							case  "New":
									statusNew++;
									if(duration.getStandardMinutes() < 60)
									{
										statusNewT1++;
									}else
									{
										if(duration.getStandardMinutes() < 120)
										{
											statusNewT2++;
										}else
										{
											if(duration.getStandardMinutes() < 180)
											{
												statusNewT3++;
											}else
											{
												if(duration.getStandardMinutes() < 240)
												{
													statusNewT4++;
												}else
												{
													statusNewT5++;
												}												
											}
										}										
									}
									break;
							case  "In Process":
									statusInProcess++;
									break;
						}
						
					}
					
				}
				else{
					//here we should send an error message in some way
				}
			}
			
			if(incidentICPStatusDao.createIncidentICPStatus(newDate, statusActionNeeded, statusActionNeededT1, statusActionNeededT2, statusActionNeededT3, statusActionNeededT4, statusActionNeededT5, statusHandover, statusHandoverT1, statusHandoverT2, statusHandoverT3, statusHandoverT4, statusHandoverT5, statusNew, statusNewT1, statusNewT2, statusNewT3, statusNewT4, statusNewT5, statusInProcess))
			{
			}
			else
			{
				System.out.println("Problem when creating ICPStatus");
			}
		}
		else
		{

		}
		return object;
	}

	// Maybe it need rework because of the changedDate inclusion, dependes on
	// the objective
	@RequestMapping(value = "/{objectID}/{uploadDateTime}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String objectID, @PathVariable Date uploadDateTime) {
		incidentICPDao.deleteIncident(objectID, uploadDateTime);
	}

	// Maybe it need rework because of the changedDate inclusion, dependes on
	// the objective
	@RequestMapping(value = "/{objectID}/{uploadDateTime}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody IncidentICP setOccupied(@PathVariable String objectID, @PathVariable Date uploadDateTime,
			@RequestBody IncidentICP object) {
		return incidentICPDao.updateIncident(objectID, uploadDateTime, object);
	}

}
