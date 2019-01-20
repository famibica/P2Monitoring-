package com.sap.p2monitoring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comments {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String incidentID;
	
	@Column(nullable = false)
	private String comment;
	
	@Column(nullable = false)
	private String user;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDateTime;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCommentDateTime() {
		return commentDateTime;
	}

	public void setCommentDateTime(Date commentDateTime) {
		this.commentDateTime = commentDateTime;
	}

	public Date getLastEditDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastEditDateTime(Date lastEditDateTime) {
		this.lastUpdateDateTime = lastEditDateTime;
	}
	
}
