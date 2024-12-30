package ug.daes.onboarding.dto;

import java.io.Serializable;

public class SelfieResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selfieUrl;
	
	private String selfieThumbnailUrl;

	public String getSelfieUrl() {
		return selfieUrl;
	}

	public void setSelfieUrl(String selfieUrl) {
		this.selfieUrl = selfieUrl;
	}

	public String getSelfieThumbnailUrl() {
		return selfieThumbnailUrl;
	}

	public void setSelfieThumbnailUrl(String selfieThumbnailUrl) {
		this.selfieThumbnailUrl = selfieThumbnailUrl;
	}

	@Override
	public String toString() {
		return "SelfieResponseDto [selfieUrl=" + selfieUrl + ", selfieThumbnailUrl=" + selfieThumbnailUrl + "]";
	}

}
