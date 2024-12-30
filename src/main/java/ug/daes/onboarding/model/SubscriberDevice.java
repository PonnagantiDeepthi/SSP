package ug.daes.onboarding.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the subscriber_devices database table.
 * 
 */
@Entity
@Table(name="subscriber_devices")
@NamedQuery(name="SubscriberDevice.findAll", query="SELECT s FROM SubscriberDevice s")
public class SubscriberDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Column(name="created_date")
	private String createdDate;

	@Column(name="device_status")
	private String deviceStatus;

	@Column(name="device_uid")
	private String deviceUid;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="subscriber_device_id")
	private int subscriberDeviceId;

	@Column(name="subscriber_uid")
	private String subscriberUid;

	@Column(name="updated_date")
	private String updatedDate;

	public SubscriberDevice() {
	}

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeviceStatus() {
		return this.deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceUid() {
		return this.deviceUid;
	}

	public void setDeviceUid(String deviceUid) {
		this.deviceUid = deviceUid;
	}

	public int getSubscriberDeviceId() {
		return this.subscriberDeviceId;
	}

	public void setSubscriberDeviceId(int subscriberDeviceId) {
		this.subscriberDeviceId = subscriberDeviceId;
	}

	public String getSubscriberUid() {
		return this.subscriberUid;
	}

	public void setSubscriberUid(String subscriberUid) {
		this.subscriberUid = subscriberUid;
	}

	public String getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}