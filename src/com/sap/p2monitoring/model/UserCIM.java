package com.sap.p2monitoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserCIM {
	
	@Id 
	@Column(nullable = false)
	private String userID;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String region;
	
	@Column(nullable = false)
	private String email;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
