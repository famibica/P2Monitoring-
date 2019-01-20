package com.sap.p2monitoring.model.ids;

import java.util.Date;

public class IncidentBCPID {
	private String incidentID;
	private Date uploadDateTime;

	public IncidentBCPID() {
	}
	
	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}
}
