package com.sap.p2monitoring.formatters;

public class IncidentICPStatusResults {
	
	private String completedDateTime;
	private int statusInProcess;
	private TWorkload statusNew;
	private TWorkload statusActionNeeded;
	private TWorkload statusHandover;
	
	public String getCompletedDateTime() {
		return completedDateTime;
	}
	public void setCompletedDateTime(String completedDateTime) {
		this.completedDateTime = completedDateTime;
	}
	public int getStatusInProcess() {
		return statusInProcess;
	}
	public void setStatusInProcess(int statusInProcess) {
		this.statusInProcess = statusInProcess;
	}
	public TWorkload getStatusNew() {
		return statusNew;
	}
	public void setStatusNew(TWorkload statusNew) {
		this.statusNew = statusNew;
	}
	public TWorkload getStatusActionNeeded() {
		return statusActionNeeded;
	}
	public void setStatusActionNeeded(TWorkload statusActionNeeded) {
		this.statusActionNeeded = statusActionNeeded;
	}
	public TWorkload getStatusHandover() {
		return statusHandover;
	}
	public void setStatusHandover(TWorkload statusHandover) {
		this.statusHandover = statusHandover;
	}	
	
}
