package ug.daes.onboarding.dto;

public class OtpDTO {
    private String identifier;


    
    public OtpDTO(String identifier) {
		super();
		this.identifier = identifier;
	}

	public OtpDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

	@Override
	public String toString() {
		return "OtpDTO [identifier=" + identifier + "]";
	}
    
    
    
}
