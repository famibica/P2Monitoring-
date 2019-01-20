package com.sap.p2monitoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class TaggingRules {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String tagName;
	
	@Column(nullable = true)
	private String system;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = true)
	private String rule1;
	
	@Column(nullable = true)
	private String rule2;
	
	@Column(nullable = true)
	private String rule3;
	
	@Column(nullable = true)
	private String rule4;
	
	@Column(nullable = true)
	private String rule5;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRule1() {
		return rule1;
	}

	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	public String getRule2() {
		return rule2;
	}

	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	public String getRule3() {
		return rule3;
	}

	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}

	public String getRule4() {
		return rule4;
	}

	public void setRule4(String rule4) {
		this.rule4 = rule4;
	}

	public String getRule5() {
		return rule5;
	}

	public void setRule5(String rule5) {
		this.rule5 = rule5;
	}
	
	
	
}
