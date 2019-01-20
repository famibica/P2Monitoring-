package com.sap.p2monitoring.model.ids;

import java.util.Date;

public class IncidentICPStatusID {
	private long id;
	private Date completedDateTime;

	public IncidentICPStatusID() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCompletedDateTime() {
		return completedDateTime;
	}

	public void setCompletedDateTime(Date completedDateTime) {
		this.completedDateTime = completedDateTime;
	}
}
