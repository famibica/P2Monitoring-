package com.sap.p2monitoring.model.ids;

import java.util.Date;

public class IncidentICPID {
	private String objectID;
	private Date uploadDateTime;
	
	public IncidentICPID() {
	}
	
	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
}
