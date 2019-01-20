package com.sap.p2monitoring.formatters;

import java.util.Date;

public class IncidentICPStatusResultsUser {
	
	private Date uploadDateTime;
	private long InProcess;
	private long Handover;
	private long New;
	private long ActionNeeded;
	
	public IncidentICPStatusResultsUser (Date uploadDateTime, long InProcess, long Handover, long New, long ActionNeeded) {
		this.uploadDateTime = uploadDateTime;
		this.InProcess = InProcess;
		this.Handover = Handover;
		this.New = New;
		this.ActionNeeded = ActionNeeded;
	}
	
	public Date getCompletedDateTime() {
		return uploadDateTime;
	}
	public Date setCompletedDateTime(Date completedDateTime) {
		return uploadDateTime;
	}
	public long getInProcess() {
		return InProcess;
	}
	public void setInProcess(long inProcess) {
		InProcess = inProcess;
	}
	public long getHandover() {
		return Handover;
	}
	public void setHandover(long handover) {
		Handover = handover;
	}
	public long getNew() {
		return New;
	}
	public void setNew(long new1) {
		New = new1;
	}
	public long getActionNeeded() {
		return ActionNeeded;
	}
	public void setActionNeeded(long actionNeeded) {
		ActionNeeded = actionNeeded;
	}
	
}
