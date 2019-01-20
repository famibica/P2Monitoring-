package com.sap.p2monitoring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.p2monitoring.model.ids.IncidentICPID;

@Entity
@IdClass(IncidentICPID.class)
public class IncidentICP {

	@Id
	private String objectID;

	@Column(nullable = true)
	private String mainCategory;

	@Column(nullable = true)
	private String serviceTeam;

	@Column(nullable = true)
	private String employeeResponsible;

	@Column(nullable = true)
	private String status;

	@Column(nullable = true)
	private String category01;

	@Column(nullable = true)
	private String category02;

	@Column(nullable = true)
	private String messageNumber;

	@Column(nullable = true)
	private String priority;

	@Column(nullable = true)
	private String customer;

	@Column(nullable = true)
	private String country;

	@Column(nullable = true)
	private String customerContact;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date changedDate;

	@Column(nullable = true)
	private String reportMain;

	@Column(nullable = true)
	private String description;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextUpdateTime;

	@Column(nullable = true)
	private String sentFrom;

	@Column(nullable = true)
	private String component;

	@Column(nullable = true)
	private String messageStatus;

	@Column(nullable = true)
	private String messageLevel;

	@Column(nullable = true)
	private String messagePriority;

	@Column(nullable = true)
	private String messageProcessor;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date messageChangedTime;

	@Column(nullable = true)
	private String acrfInfo;

	@Column(nullable = true)
	private String reason;

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDateTime;

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

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public String getEmployeeResponsible() {
		return employeeResponsible;
	}

	public void setEmployeeResponsible(String employeeResponsible) {
		this.employeeResponsible = employeeResponsible;
	}

	public String getServiceTeam() {
		return serviceTeam;
	}

	public void setServiceTeam(String serviceTeam) {
		this.serviceTeam = serviceTeam;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getCategory01() {
		return category01;
	}

	public void setCategory01(String category01) {
		this.category01 = category01;
	}

	public String getMessagePriority() {
		return messagePriority;
	}

	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}

	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getMessageProcessor() {
		return messageProcessor;
	}

	public void setMessageProcessor(String messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public String getAcrfInfo() {
		return acrfInfo;
	}

	public void setAcrfInfo(String acrfInfo) {
		this.acrfInfo = acrfInfo;
	}

	public String getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(String messageLevel) {
		this.messageLevel = messageLevel;
	}

	public String getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getReportMain() {
		return reportMain;
	}

	public void setReportMain(String reportMain) {
		this.reportMain = reportMain;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public Date getNextUpdateTime() {
		return nextUpdateTime;
	}

	public void setNextUpdateTime(Date nextUpdateTime) {
		this.nextUpdateTime = nextUpdateTime;
	}

	public Date getMessageChangedTime() {
		return messageChangedTime;
	}

	public void setMessageChangedTime(Date messageChangedTime) {
		this.messageChangedTime = messageChangedTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory02() {
		return category02;
	}

	public void setCategory02(String category02) {
		this.category02 = category02;
	}

}
