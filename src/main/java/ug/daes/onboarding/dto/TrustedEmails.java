package ug.daes.onboarding.dto;

public class TrustedEmails {

	private String email;
	
	private String name;
	
	private String mobileNo;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "TrustedEmails [email=" + email + ", name=" + name + ", mobileNo=" + mobileNo + "]";
	}

}
