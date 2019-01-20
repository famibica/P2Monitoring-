package com.sap.p2monitoring.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.p2monitoring.dao.DevIncidentDAO;
import com.sap.p2monitoring.model.DevIncident;

@Controller
@RequestMapping("/DevIncident")
public class DevIncidentController {

	@Autowired
	private DevIncidentDAO devIncidentDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<DevIncident> findAll() {
		return devIncidentDAO.getAllDevIncidentRelations();
	}

	@RequestMapping(value = "/{incidentID}/{user}", method = RequestMethod.GET)
	public @ResponseBody DevIncident findByID(@PathVariable String incidentID, @PathVariable String user) {
		return devIncidentDAO.getDevIncidentByIncidentIDandUser(incidentID, user); 
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody boolean create(@RequestBody DevIncident devIncident) {
		devIncidentDAO.createDevIncident(devIncident);		
		return true;
	}

	@RequestMapping(value = "/{incidentID}/{user}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String incidentID, @PathVariable String user) {
		devIncidentDAO.deleteDevIncident(incidentID, user);		
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean updateDevIncident(@RequestBody DevIncident devIncident) {
		return devIncidentDAO.updateDevIncident(devIncident);
	}

}