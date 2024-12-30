/**
 * 
 */
package ug.daes.onboarding.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the Consent database table.
 * 
 */
@Entity
@Table(name="consent")
@NamedQuery(name="Consent.findAll", query="SELECT c FROM Consent c")
public class Consent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="consent_id")
	private Integer consentId;
	
	@Column(name="consent")
	private String consent;
	
	@Column(name="created_on")
	private String createdOn;
	
	@Column(name="updated_on")
	private String updatedOn;
	
	@Column(name="consent_type")
	private String consentType;
	
	@Column(name="status")
	private String status;
	
	@Column(name="privacy_consent")
	private String privacyConsent;

	public Integer getConsentId() {
		return consentId;
	}

	public void setConsentId(Integer consentId) {
		this.consentId = consentId;
	}

	public String getConsent() {
		return consent;
	}

	public void setConsent(String consent) {
		this.consent = consent;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getConsentType() {
		return consentType;
	}

	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrivacyConsent() {
		return privacyConsent;
	}

	public void setPrivacyConsent(String privacyConsent) {
		this.privacyConsent = privacyConsent;
	}

	@Override
	public String toString() {
		return "Consent [consentId=" + consentId + ", consent=" + consent + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", consentType=" + consentType + ", status=" + status + ", privacyConsent="
				+ privacyConsent + "]";
	}

	
}
