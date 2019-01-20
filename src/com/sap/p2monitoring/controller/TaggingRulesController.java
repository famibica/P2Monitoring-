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

import com.sap.p2monitoring.dao.TaggingRulesDAO;
import com.sap.p2monitoring.model.TaggingRules;

@Controller
@RequestMapping("/TaggingRules")
public class TaggingRulesController {
	@Autowired
	private TaggingRulesDAO taggingRulesDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<TaggingRules> findAll() {
		return taggingRulesDAO.getAllTaggingRules();
	}

	@RequestMapping(value = "/{tagName}", method = RequestMethod.GET)
	public @ResponseBody TaggingRules findByTagName(@PathVariable String tagName) {
		return taggingRulesDAO.getByTagName(tagName); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody TaggingRules create(@RequestBody TaggingRules taggingRules) {
		taggingRulesDAO.createTaggingRules(taggingRules);
				
		return taggingRules;
	}

	@RequestMapping(value = "/{tagName}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String tagName) {
		taggingRulesDAO.deleteTaggingRules(tagName);
	}

	@RequestMapping(value = "/UpdateTaggingRules/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody TaggingRules setOccupied(@RequestBody TaggingRules tag) {
		return taggingRulesDAO.updateTaggingRules(tag);
	}
}
