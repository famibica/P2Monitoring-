package com.sap.p2monitoring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
public class Job {

	@Id @Temporal(TemporalType.TIMESTAMP)
	private Date jobDateTime;
	
	@Column(nullable = false)
	private String system;
	
	@Column(nullable = false)
	private String region;
	
	@Column(nullable = false)
	private String personalId;
	
	@Column(nullable = false)
	private String search;
	
	@Column(nullable = false)
	private int lapseInMinutes;
	
	@Column(nullable = false)
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date completedDate;
	
	public Date getJobDateTime() {
		return jobDateTime;
	}

	public void setJobDateTime(Date jobDateTime) {
		this.jobDateTime = jobDateTime;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public int getLapseInMinutes() {
		return lapseInMinutes;
	}

	public void setLapseInMinutes(int lapseInMinutes) {
		this.lapseInMinutes = lapseInMinutes;
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
