package ug.daes.onboarding.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the assurance_levels database table.
 * 
 */
//@Entity
@Table(name="assurance_levels")
@NamedQuery(name="AssuranceLevel.findAll", query="SELECT a FROM AssuranceLevel a")
public class AssuranceLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="assurance_level")
	private String assuranceLevel;

	@Column(name="assurance_level_value")
	private int assuranceLevelValue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	public AssuranceLevel() {
	}

	public String getAssuranceLevel() {
		return this.assuranceLevel;
	}

	public void setAssuranceLevel(String assuranceLevel) {
		this.assuranceLevel = assuranceLevel;
	}

	public int getAssuranceLevelValue() {
		return this.assuranceLevelValue;
	}

	public void setAssuranceLevelValue(int assuranceLevelValue) {
		this.assuranceLevelValue = assuranceLevelValue;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}