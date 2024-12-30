package ug.daes.onboarding.dto;

import java.io.Serializable;
import java.util.Arrays;

public class MobileOTPDto implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String subscriberName;
	private String deviceId;
	private String subscriberMobileNumber;
	private String subscriberEmail;
	private String fcmToken;
	private Boolean otpStatus;
	private String suID;
	private String image ;
	private String osName;
	private String osVersion;
	private String appVersion;
	private String deviceInfo;

	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSubscriberMobileNumber() {
		return subscriberMobileNumber;
	}
	public void setSubscriberMobileNumber(String subscriberMobileNumber) {
		this.subscriberMobileNumber = subscriberMobileNumber;
	}
	public String getSubscriberEmail() {
		return subscriberEmail;
	}
	public void setSubscriberEmail(String subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	public Boolean getOtpStatus() {
		return otpStatus;
	}
	public void setOtpStatus(Boolean otpStatus) {
		this.otpStatus = otpStatus;
	}
	public String getSuID() {
		return suID;
	}
	public void setSuID(String suID) {
		this.suID = suID;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	@Override
	public String toString() {
		return "MobileOTPDto [subscriberName=" + subscriberName + ", deviceId=" + deviceId + ", subscriberMobileNumber="
				+ subscriberMobileNumber + ", subscriberEmail=" + subscriberEmail + ", fcmToken=" + fcmToken
				+ ", otpStatus=" + otpStatus + ", suID=" + suID + ", image=" + image + ", osName=" + osName
				+ ", osVersion=" + osVersion + ", appVersion=" + appVersion + ", deviceInfo=" + deviceInfo + "]";
	}
	
}
