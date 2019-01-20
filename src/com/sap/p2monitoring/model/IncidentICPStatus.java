package com.sap.p2monitoring.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.p2monitoring.formatters.IncidentICPStatusResults;
import com.sap.p2monitoring.formatters.TWorkload;
import com.sap.p2monitoring.model.ids.IncidentICPStatusID;

@Entity
@IdClass(IncidentICPStatusID.class)
public class IncidentICPStatus {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedDateTime;
	
	@Column(nullable = true)
	private int statusInProcess;
	
	@Column(nullable = true)
	private int statusHandover;
	
	@Column(nullable = true)
	private int statusHandoverT1;
	
	@Column(nullable = true)
	private int statusHandoverT2;
	
	@Column(nullable = true)
	private int statusHandoverT3;
	
	@Column(nullable = true)
	private int statusHandoverT4;
	
	@Column(nullable = true)
	private int statusHandoverT5;
	
	@Column(nullable = true)
	private int statusNew;
	
	@Column(nullable = true)
	private int statusNewT1;
	
	@Column(nullable = true)
	private int statusNewT2;
	
	@Column(nullable = true)
	private int statusNewT3;
	
	@Column(nullable = true)
	private int statusNewT4;
	
	@Column(nullable = true)
	private int statusNewT5;
	
	@Column(nullable = true)
	private int statusActionNeeded;
	
	@Column(nullable = true)
	private int statusActionNeededT1;
	
	@Column(nullable = true)
	private int statusActionNeededT2;
	
	@Column(nullable = true)
	private int statusActionNeededT3;
	
	@Column(nullable = true)
	private int statusActionNeededT4;
	
	@Column(nullable = true)
	private int statusActionNeededT5;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatusInProcess() {
		return statusInProcess;
	}

	public void setStatusInProcess(int statusInProcess) {
		this.statusInProcess = statusInProcess;
	}

	public int getStatusHandover() {
		return statusHandover;
	}

	public void setStatusHandover(int statusHandover) {
		this.statusHandover = statusHandover;
	}

	public int getStatusNew() {
		return statusNew;
	}

	public void setStatusNew(int statusNew) {
		this.statusNew = statusNew;
	}

	public int getStatusActionNeeded() {
		return statusActionNeeded;
	}

	public void setStatusActionNeeded(int statusActionNeeded) {
		this.statusActionNeeded = statusActionNeeded;
	}

	public Date getCompletedDateTime() {
		return completedDateTime;
	}

	public void setCompletedDateTime(Date completedDateTime) {
		this.completedDateTime = completedDateTime;
	}
	
	public int getStatusHandoverT1() {
		return statusHandoverT1;
	}

	public void setStatusHandoverT1(int statusHandoverT1) {
		this.statusHandoverT1 = statusHandoverT1;
	}

	public int getStatusHandoverT2() {
		return statusHandoverT2;
	}

	public void setStatusHandoverT2(int statusHandoverT2) {
		this.statusHandoverT2 = statusHandoverT2;
	}

	public int getStatusHandoverT3() {
		return statusHandoverT3;
	}

	public void setStatusHandoverT3(int statusHandoverT3) {
		this.statusHandoverT3 = statusHandoverT3;
	}

	public int getStatusHandoverT4() {
		return statusHandoverT4;
	}

	public void setStatusHandoverT4(int statusHandoverT4) {
		this.statusHandoverT4 = statusHandoverT4;
	}

	public int getStatusHandoverT5() {
		return statusHandoverT5;
	}

	public void setStatusHandoverT5(int statusHandoverT5) {
		this.statusHandoverT5 = statusHandoverT5;
	}

	public int getStatusNewT1() {
		return statusNewT1;
	}

	public void setStatusNewT1(int statusNewT1) {
		this.statusNewT1 = statusNewT1;
	}

	public int getStatusNewT2() {
		return statusNewT2;
	}

	public void setStatusNewT2(int statusNewT2) {
		this.statusNewT2 = statusNewT2;
	}

	public int getStatusNewT3() {
		return statusNewT3;
	}

	public void setStatusNewT3(int statusNewT3) {
		this.statusNewT3 = statusNewT3;
	}

	public int getStatusNewT4() {
		return statusNewT4;
	}

	public void setStatusNewT4(int statusNewT4) {
		this.statusNewT4 = statusNewT4;
	}

	public int getStatusNewT5() {
		return statusNewT5;
	}

	public void setStatusNewT5(int statusNewT5) {
		this.statusNewT5 = statusNewT5;
	}

	public int getStatusActionNeededT1() {
		return statusActionNeededT1;
	}

	public void setStatusActionNeededT1(int statusActionNeededT1) {
		this.statusActionNeededT1 = statusActionNeededT1;
	}

	public int getStatusActionNeededT2() {
		return statusActionNeededT2;
	}

	public void setStatusActionNeededT2(int statusActionNeededT2) {
		this.statusActionNeededT2 = statusActionNeededT2;
	}

	public int getStatusActionNeededT3() {
		return statusActionNeededT3;
	}

	public void setStatusActionNeededT3(int statusActionNeededT3) {
		this.statusActionNeededT3 = statusActionNeededT3;
	}

	public int getStatusActionNeededT4() {
		return statusActionNeededT4;
	}

	public void setStatusActionNeededT4(int statusActionNeededT4) {
		this.statusActionNeededT4 = statusActionNeededT4;
	}

	public int getStatusActionNeededT5() {
		return statusActionNeededT5;
	}

	public void setStatusActionNeededT5(int statusActionNeededT5) {
		this.statusActionNeededT5 = statusActionNeededT5;
	}

	public IncidentICPStatusResults getResults(){
		
		IncidentICPStatusResults result = new IncidentICPStatusResults();
		
		TWorkload workloadActionNeeded = new TWorkload();
		TWorkload workloadNew = new TWorkload();
		TWorkload workloadHandover = new TWorkload();
		
		workloadActionNeeded.setTotal(statusActionNeeded);
		workloadActionNeeded.setT1(statusActionNeededT1);
		workloadActionNeeded.setT2(statusActionNeededT2);
		workloadActionNeeded.setT3(statusActionNeededT3);
		workloadActionNeeded.setT4(statusActionNeededT4);
		workloadActionNeeded.setT5(statusActionNeededT5);
		
		workloadNew.setTotal(statusNew);
		workloadNew.setT1(statusNewT1);
		workloadNew.setT2(statusNewT2);
		workloadNew.setT3(statusNewT3);
		workloadNew.setT4(statusNewT4);
		workloadNew.setT5(statusNewT5);
		
		workloadHandover.setTotal(statusHandover);
		workloadHandover.setT1(statusHandoverT1);
		workloadHandover.setT2(statusHandoverT2);
		workloadHandover.setT3(statusHandoverT3);
		workloadHandover.setT4(statusHandoverT4);
		workloadHandover.setT5(statusHandoverT5);
		
		result.setCompletedDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(completedDateTime));
		result.setStatusInProcess(statusInProcess);
		result.setStatusActionNeeded(workloadActionNeeded);
		result.setStatusHandover(workloadHandover);
		result.setStatusNew(workloadNew);
				
		return result;
	}
}
