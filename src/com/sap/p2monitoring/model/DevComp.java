package com.sap.p2monitoring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.p2monitoring.model.ids.DevCompID;

@Entity @IdClass(DevCompID.class)
public class DevComp {
	
	@Id 
	private String developerID;
	
	@Id 
	private String component;
	
	@Column(nullable = true)
	private int level;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date manualLastUpdate;

	@Column(nullable = true)
	private String manualUpdatedBy;
	
	@Column(nullable = true)
	private long timesDevComp;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeUpdate;

	public String getDeveloperID() {
		return developerID;
	}

	public void setDeveloperID(String developerID) {
		this.developerID = developerID;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getManualLastUpdate() {
		return manualLastUpdate;
	}

	public void setManualLastUpdate(Date manualLastUpdate) {
		this.manualLastUpdate = manualLastUpdate;
	}

	public String getManualUpdatedBy() {
		return manualUpdatedBy;
	}

	public void setManualUpdatedBy(String manualUpdatedBy) {
		this.manualUpdatedBy = manualUpdatedBy;
	}

	public long getTimesDevComp() {
		return timesDevComp;
	}

	public void setTimesDevComp(long timesDevComp) {
		this.timesDevComp = timesDevComp;
	}

	public Date getLastTimeUpdate() {
		return lastTimeUpdate;
	}

	public void setLastTimeUpdate(Date lastTimeUpdate) {
		this.lastTimeUpdate = lastTimeUpdate;
	}
		
}
