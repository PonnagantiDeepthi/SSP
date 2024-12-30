package ug.daes.onboarding.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenDTO [token=" + token + "]";
	}
	
}
