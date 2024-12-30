package ug.daes.onboarding.dto;

import java.io.Serializable;

public class GetSubscriberObDataDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String suid;
	
	private boolean selfieRequired;

	public String getSuid() {
		return suid;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}

	public boolean isSelfieRequired() {
		return selfieRequired;
	}

	public void setSelfieRequired(boolean selfieRequired) {
		this.selfieRequired = selfieRequired;
	}

	@Override
	public String toString() {
		return "GetSubscriberObDataDTO [suid=" + suid + ", selfieRequired=" + selfieRequired + "]";
	}
}
