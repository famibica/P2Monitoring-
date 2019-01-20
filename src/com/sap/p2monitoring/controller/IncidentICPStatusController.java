package com.sap.p2monitoring.controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.IncidentICPStatusDAO;
import com.sap.p2monitoring.formatters.IncidentICPStatusResults;
import com.sap.p2monitoring.formatters.IncidentICPStatusResultsUser;
import com.sap.p2monitoring.model.IncidentICPStatus;

@Controller
@RequestMapping("/incidentICPStatus")
public class IncidentICPStatusController {
	@Autowired
	private IncidentICPStatusDAO incidentICPStatusDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentICPStatus> findAll() {
		return incidentICPStatusDao.getAllIncidents();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentICPStatus> findById(@PathVariable long id) {
		return incidentICPStatusDao.getIncidentStatusById(id);
	}
	
	@RequestMapping(value = "/SearchStatusByDay/{day}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentICPStatusResults> findIncidentStatus(@PathVariable String day) throws ParseException {
		return incidentICPStatusDao.getIncidentStatusByDay(day);
	}
	
	@RequestMapping(value = "/SearchLastDay/", method = RequestMethod.GET)
	public @ResponseBody List<IncidentICPStatusResults> findLastIncidentStatus() throws ParseException {
		return incidentICPStatusDao.getIncidentStatusLastDay();
	}
	
	@RequestMapping(value = "/SearchByUserID/{userID}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentICPStatusResultsUser> findLastIncidentStatusByUserID(@PathVariable String userID) throws ParseException {
		return incidentICPStatusDao.getIncidentStatusByUser(userID);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody IncidentICPStatus create(@RequestBody IncidentICPStatus incidentICPStatus) {
		if (incidentICPStatus != null) {
			if (incidentICPStatusDao.getIncidentICPStatusByScheduledDateTime(incidentICPStatus.getCompletedDateTime()) == null) {
				incidentICPStatusDao.createIncidentICPStatus(incidentICPStatus);
			}
		}
		return incidentICPStatus;
	}

	// Maybe it need rework because of the changedDate inclusion, dependes on
	// the objective
	@RequestMapping(value = "/{id}/{completedDateTime}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable long id, @PathVariable Date completedDateTime) {
		incidentICPStatusDao.deleteIncidentICPStatus(id, completedDateTime);
	}

	// Maybe it need rework because of the changedDate inclusion, dependes on
	// the objective
	@RequestMapping(value = "/{id}/{completedDateTime}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody IncidentICPStatus setOccupied(@PathVariable long id, @PathVariable Date completedDateTime, @RequestBody IncidentICPStatus incidentICPStatus) {
		return incidentICPStatusDao.updateIncidentICPStatus(id, completedDateTime, incidentICPStatus);
	}
}
