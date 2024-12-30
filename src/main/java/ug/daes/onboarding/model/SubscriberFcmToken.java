package ug.daes.onboarding.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the subscriber_fcm_token database table.
 * 
 */
@Entity
@Table(name="subscriber_fcm_token")
@NamedQuery(name="SubscriberFcmToken.findAll", query="SELECT s FROM SubscriberFcmToken s")
public class SubscriberFcmToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="created_date")
	private String createdDate;

	@Column(name="fcm_token")
	private String fcmToken;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="subscriber_fcm_token_id")
	private int subscriberFcmTokenId;

	@Column(name="subscriber_uid")
	private String subscriberUid;

	public SubscriberFcmToken() {
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getFcmToken() {
		return this.fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public int getSubscriberFcmTokenId() {
		return this.subscriberFcmTokenId;
	}

	public void setSubscriberFcmTokenId(int subscriberFcmTokenId) {
		this.subscriberFcmTokenId = subscriberFcmTokenId;
	}

	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

}