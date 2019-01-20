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

import com.sap.p2monitoring.dao.DevCompDAO;
import com.sap.p2monitoring.model.DevComp;

@Controller
@RequestMapping("/DevComp")
public class DevCompController {

	@Autowired
	private DevCompDAO devCompDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<DevComp> findAll() {
		return devCompDAO.getAllDevs();
	}

	@RequestMapping(value = "/{developerID}/{component}", method = RequestMethod.GET)
	public @ResponseBody DevComp findByIDAndComponent(@PathVariable String developerID, @PathVariable String component) {
		return devCompDAO.getByIDAndComponent(developerID, component);
	}
	
	@RequestMapping(value = "/{componentOrDeveloperID}", method = RequestMethod.GET)
	public @ResponseBody List<DevComp> findByComponentOrDeveloper(@PathVariable String componentOrDeveloperID) {
		return devCompDAO.getDevByComponentOrDeveloper(componentOrDeveloperID); 
	}
			
	@RequestMapping(value = "/searchByComponent/{component}", method = RequestMethod.GET)
	public @ResponseBody List<DevComp> findByComponent(@PathVariable String component) {
		return devCompDAO.getDevByComponent(component); 
	}
	
	@RequestMapping(value = "/searchByID/{developerID}", method = RequestMethod.GET)
	public @ResponseBody List<DevComp> findByDev(@PathVariable String developerID) {
		return devCompDAO.getDevByID(developerID); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<DevComp> create(@RequestBody List<DevComp> devComp) {
		if (devComp != null) {
			for (int i = 0; i < devComp.size(); i++) {
				if(devCompDAO.getByIDAndComponent(devComp.get(i).getDeveloperID(), devComp.get(i).getComponent()) == null)
				{
					devCompDAO.createDevComp(devComp.get(i));
				}
			}
		}
		return devComp;
	}

	@RequestMapping(value = "/{developerID}/{component}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable String developerID,@PathVariable String component) {
		devCompDAO.deleteDevComp(developerID, component);
	}

	@RequestMapping(value = "/{developerID}/{component}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody DevComp setOccupied(@PathVariable String developerID, @PathVariable String component, @RequestBody DevComp devComp) {
		return devCompDAO.updateDevComp(developerID, component, devComp);
	}

}