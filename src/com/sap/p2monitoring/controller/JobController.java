package com.sap.p2monitoring.controller;

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

import com.sap.p2monitoring.dao.JobDAO;
import com.sap.p2monitoring.model.Job;

@Controller
@RequestMapping("/Job")
public class JobController {

	@Autowired
	private JobDAO jobDAO;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<Job> findAll() {
		return jobDAO.getAllJobs();
	}

	@RequestMapping(value = "/{jobDateTime}", method = RequestMethod.GET)
	public @ResponseBody Job findByDateAndTime(@PathVariable Date jobDateTime) {
		return jobDAO.getByDateAndTime(jobDateTime); 
	}
	
	@RequestMapping(value = "/NextJob/", method = RequestMethod.GET)
	public @ResponseBody Job findNextJob() {
		return jobDAO.getNextJob(); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<Job> create(@RequestBody List<Job> jobs) {
		if (jobs != null) {
			for (int i = 0; i < jobs.size(); i++) {
				if(jobDAO.getByDateAndTime(jobs.get(i).getJobDateTime()) == null)
				{
					jobDAO.createJob(jobs.get(i));
				}
				else
				{
					//here we should send an error message in some way
					//maybe return a list of jobs that we had inserted and the ones we didn't showing that
				}
			}
		}
		return jobs;
	}

	@RequestMapping(value = "/{jobDateTime}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void delete(@PathVariable Date jobDateTime) {
		jobDAO.deleteJob(jobDateTime);
	}

	@RequestMapping(value = "/UpdateJob/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Job setOccupied(@RequestBody Job job) {
		return jobDAO.updateJob(job);
	}

}