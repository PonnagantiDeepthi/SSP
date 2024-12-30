package ug.daes.onboarding.dto;

import java.io.Serializable;

public class IssueCertDTO implements Serializable{

	private String subscriberUniqueId;
	
	public String getSubscriberUniqueId() {
		return subscriberUniqueId;
	}

	public void setSubscriberUniqueId(String subscriberUniqueId) {
		this.subscriberUniqueId = subscriberUniqueId;
	}

	@Override
	public String toString() {
		return "IssueCertDTO [subscriberUniqueId=" + subscriberUniqueId + "]";
	}	
}
