package ug.daes.onboarding.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the subscriber_certificate_pin_history database table.
 * 
 */
@Entity
@Table(name="subscriber_certificate_pin_history")
@NamedQuery(name="SubscriberCertificatePinHistory.findAll", query="SELECT s FROM SubscriberCertificatePinHistory s")
public class SubscriberCertificatePinHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="auth_pin_list")
	private String authPinList;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="created_date")
//	private Date createdDate;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="pin_set_date")
//	private Date pinSetDate;

	@Column(name="sign_pin_list")
	private String signPinList;

	@Column(name="subscriber_certificate_pin_history_id")
	private int subscriberCertificatePinHistoryId;

	@Id
	@Column(name="subscriber_uid",nullable = false, unique = true)
	private String subscriberUid;

	public SubscriberCertificatePinHistory() {
	}

	public String getAuthPinList() {
		return this.authPinList;
	}

	public void setAuthPinList(String authPinList) {
		this.authPinList = authPinList;
	}

//	public Date getCreatedDate() {
//		return this.createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}

//	public Date getPinSetDate() {
//		return this.pinSetDate;
//	}
//
//	public void setPinSetDate(Date pinSetDate) {
//		this.pinSetDate = pinSetDate;
//	}

	public String getSignPinList() {
		return this.signPinList;
	}

	public void setSignPinList(String signPinList) {
		this.signPinList = signPinList;
	}

	public int getSubscriberCertificatePinHistoryId() {
		return this.subscriberCertificatePinHistoryId;
	}

	public void setSubscriberCertificatePinHistoryId(int subscriberCertificatePinHistoryId) {
		this.subscriberCertificatePinHistoryId = subscriberCertificatePinHistoryId;
	}

	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

}