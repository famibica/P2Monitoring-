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

import com.sap.p2monitoring.dao.NotWorkingComponentsDAO;
import com.sap.p2monitoring.model.NotWorkingComponents;

@Controller
@RequestMapping("/NotWorkingComponents")
public class NotWorkingComponentsController {
	@Autowired
	private NotWorkingComponentsDAO notWorkingComponentsDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<NotWorkingComponents> findAll() {
		return notWorkingComponentsDAO.getAllComponents();
	}

	@RequestMapping(value = "/{componentCode}", method = RequestMethod.GET)
	public @ResponseBody NotWorkingComponents findByTagName(@PathVariable String componentCode) {
		return notWorkingComponentsDAO.getByComponentCode(componentCode); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody NotWorkingComponents create(@RequestBody NotWorkingComponents component) {
		notWorkingComponentsDAO.createComponent(component);
				
		return component;
	}

	@RequestMapping(value = "/{componentCode}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String componentCode) {
		notWorkingComponentsDAO.deleteComponent(componentCode);
	}

	@RequestMapping(value = "/UpdateComponent/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody NotWorkingComponents setOccupied(@RequestBody NotWorkingComponents component) {
		return notWorkingComponentsDAO.updateComponent(component);
	}
}
