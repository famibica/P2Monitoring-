package com.sap.p2monitoring.controller;

import java.util.Collection;
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

import com.sap.p2monitoring.dao.TagIncidentDAO;
import com.sap.p2monitoring.model.IncidentBCP;
import com.sap.p2monitoring.model.TagIncident;

@Controller
@RequestMapping("/TagIncident")
public class TagIncidentController {
	@Autowired
	private TagIncidentDAO tagIncidentDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<TagIncident> findAll() {
		return tagIncidentDAO.getAllTags();
	}

	@RequestMapping(value = "/{tagName}", method = RequestMethod.GET)
	public @ResponseBody List<TagIncident> findByTagName(@PathVariable String tagName) {
		return tagIncidentDAO.getAllIncidentsByTagName(tagName); 
	}
	
	@RequestMapping(value = "/CustomTag/{customTags}", method = RequestMethod.GET)
	public @ResponseBody List<IncidentBCP> findCustomTag(@PathVariable String customTags) {
		return tagIncidentDAO.getAllIncidentsByCustomTag(customTags); 
	}
	
	@RequestMapping(value = "/{incidentID}/{tagName}", method = RequestMethod.GET)
	public @ResponseBody TagIncident findByIncidentIDTagName(@PathVariable String incidentID, @PathVariable String tagName) {
		return tagIncidentDAO.getByIncidentIDTagName(incidentID, tagName); 
	}
	
	@RequestMapping(value = "/UpdateList/Create2Lists", method = RequestMethod.GET)
	public @ResponseBody boolean findAllTaggedAndSplit() {
		return tagIncidentDAO.getAllTaggedAndSplit(); 
	}
	
	@RequestMapping(value = "/TestUpdate", method = RequestMethod.GET)
	public @ResponseBody boolean testUpdates() {
		return tagIncidentDAO.checkSRandTagUpdating(); 
	}
	
	@RequestMapping(value = "/UpdateList/SRPercentage", method = RequestMethod.GET)
	public @ResponseBody String findSRPercentageGroupAandB() {
		return tagIncidentDAO.getSRPercentage(); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody TagIncident create(@RequestBody TagIncident tagIncident) {
		tagIncidentDAO.createTagIncident(tagIncident);
				
		return tagIncident;
	}

	@RequestMapping(value = "/{incidentID}/{tagName}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String incidentID, @PathVariable String tagName) {
		tagIncidentDAO.deleteTagIncident(incidentID, tagName);
	}

	@RequestMapping(value = "/UpdateTagIncident/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody TagIncident setOccupied(@RequestBody TagIncident tag) {
		return tagIncidentDAO.updateTagIncident(tag);
	}
}
