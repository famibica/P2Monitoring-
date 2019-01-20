package com.sap.p2monitoring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.p2monitoring.model.ids.IncidentBCPID;

@Entity @IdClass(IncidentBCPID.class) //Creating a class to make composite ID
public class IncidentBCP {
	
	@Id
	private String incidentID;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeOfLastReaction;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateBySAPAt;
	
	@Column(nullable = true)
	private String wp;
	
	@Column(nullable = true)
	private String escalation;
	
	@Column(nullable = true)
	private String contractType;
	
	@Column(nullable = true)
	private String priority;
	
	@Column(nullable = true)
	private String component;
	
	@Column(nullable = true)
	private String customer;
	
	@Column(nullable = true)
	private String status;
	
	@Column(nullable = true)
	private String processor;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = true)
	private String irtTrafficLight;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date irtPlannedEndDate;
	
	@Column(nullable = true)
	private String transactionType;
	
	@Column(nullable = true)
	private String cimServiceRequest;
	
	@Column(nullable = true)
	private String devHelpRequest;
	
	@Column(nullable = true)
	private String incidentUpdated;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mptPlannedEndDate;
	
	@Column(nullable = true)
	private String mptTrafficLight;
	
	@Column(nullable = true)
	private String customerCallback;
	
	@Column(nullable = true)
	private String rampUp;
	
	@Column(nullable = true)
	private String incidentYear;
	
	@Column(nullable = true)
	private String incidentNumber;
	
	@Column(nullable = true)
	private String incidentIDCustomer;
	
	@Column(nullable = true)
	private String country;
	
	@Column(nullable = true)
	private String numberCallsFromCustomer;
	
	@Column(nullable = true)
	private String processorID;
	
	@Column(nullable = true)
	private String processingOrg;
	
	@Column(nullable = true)
	private String serviceTeam;
	
	@Column @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Id  @Temporal(TemporalType.TIMESTAMP)
	private Date uploadDateTime;
	
	//Creating Get and Set for each table Column	
	public String getCountry() {
		return country;
	}

	public String getProcessingOrg() {
		return processingOrg;
	}

	public void setProcessingOrg(String processingOrg) {
		this.processingOrg = processingOrg;
	}

	public String getServiceTeam() {
		return serviceTeam;
	}

	public void setServiceTeam(String serviceTeam) {
		this.serviceTeam = serviceTeam;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNumberCallsFromCustomer() {
		return numberCallsFromCustomer;
	}

	public void setNumberCallsFromCustomer(String numberCallsFromCustomer) {
		this.numberCallsFromCustomer = numberCallsFromCustomer;
	}

	public String getProcessorID() {
		return processorID;
	}

	public void setProcessorID(String processorID) {
		this.processorID = processorID;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public String getWp() {
		return wp;
	}

	public void setWp(String wp) {
		this.wp = wp;
	}

	public String getEscalation() {
		return escalation;
	}

	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIrtTrafficLight() {
		return irtTrafficLight;
	}

	public void setIrtTrafficLight(String irtTrafficLight) {
		this.irtTrafficLight = irtTrafficLight;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getDevHelpRequest() {
		return devHelpRequest;
	}

	public void setDevHelpRequest(String devHelpRequest) {
		this.devHelpRequest = devHelpRequest;
	}

	public String getIncidentUpdated() {
		return incidentUpdated;
	}

	public void setIncidentUpdated(String incidentUpdated) {
		this.incidentUpdated = incidentUpdated;
	}

	public Date getTimeOfLastReaction() {
		return timeOfLastReaction;
	}

	public void setTimeOfLastReaction(Date timeOfLastReaction) {
		this.timeOfLastReaction = timeOfLastReaction;
	}

	public Date getLastUpdateBySAPAt() {
		return lastUpdateBySAPAt;
	}

	public void setLastUpdateBySAPAt(Date lastUpdateBySAPAt) {
		this.lastUpdateBySAPAt = lastUpdateBySAPAt;
	}

	public Date getIrtPlannedEndDate() {
		return irtPlannedEndDate;
	}

	public void setIrtPlannedEndDate(Date irtPlannedEndDate) {
		this.irtPlannedEndDate = irtPlannedEndDate;
	}

	public Date getMptPlannedEndDate() {
		return mptPlannedEndDate;
	}

	public void setMptPlannedEndDate(Date mptPlannedEndDate) {
		this.mptPlannedEndDate = mptPlannedEndDate;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public String getMptTrafficLight() {
		return mptTrafficLight;
	}

	public void setMptTrafficLight(String mptTrafficLight) {
		this.mptTrafficLight = mptTrafficLight;
	}

	public String getCustomerCallback() {
		return customerCallback;
	}

	public void setCustomerCallback(String customerCallback) {
		this.customerCallback = customerCallback;
	}

	public String getRampUp() {
		return rampUp;
	}

	public void setRampUp(String rampUp) {
		this.rampUp = rampUp;
	}

	public String getIncidentYear() {
		return incidentYear;
	}

	public void setIncidentYear(String incidentYear) {
		this.incidentYear = incidentYear;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getIncidentIDCustomer() {
		return incidentIDCustomer;
	}

	public void setIncidentIDCustomer(String incidentIDCustomer) {
		this.incidentIDCustomer = incidentIDCustomer;
	}

	public String getCimServiceRequest() {
		return cimServiceRequest;
	}

	public void setCimServiceRequest(String cimServiceRequest) {
		this.cimServiceRequest = cimServiceRequest;
	}
	
}