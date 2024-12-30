/**
 * 
 */
package ug.daes.onboarding.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Raxit Dubey
 *
 */
public class NotificationContextDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String PREF_ONBOARDING_STATUS;

    private String PREF_ONBOARDING_APPROVAL_STATUS;

    private String PREF_CERTIFICATE_STATUS;

    private String PREF_CERTIFICATE_REVOKE_STATUS;

    private String PROMOTIONAL_NOTIFICATION;
    
    private Map<String,String> pREF_PAYMENT_STATUS;

	private Map<String,String> pREF_TRANSACTION_ID;

    public void setPREF_ONBOARDING_STATUS(String PREF_ONBOARDING_STATUS){
        this.PREF_ONBOARDING_STATUS = PREF_ONBOARDING_STATUS;
    }
    
    public String getPREF_ONBOARDING_STATUS(){
        return this.PREF_ONBOARDING_STATUS;
    }
    
    public void setPREF_ONBOARDING_APPROVAL_STATUS(String PREF_ONBOARDING_APPROVAL_STATUS){
        this.PREF_ONBOARDING_APPROVAL_STATUS = PREF_ONBOARDING_APPROVAL_STATUS;
    }
    
    public String getPREF_ONBOARDING_APPROVAL_STATUS(){
        return this.PREF_ONBOARDING_APPROVAL_STATUS;
    }
    
    public void setPREF_CERTIFICATE_STATUS(String PREF_CERTIFICATE_STATUS){
        this.PREF_CERTIFICATE_STATUS = PREF_CERTIFICATE_STATUS;
    }
    
    public String getPREF_CERTIFICATE_STATUS(){
        return this.PREF_CERTIFICATE_STATUS;
    }
    
    public void setPREF_CERTIFICATE_REVOKE_STATUS(String PREF_CERTIFICATE_REVOKE_STATUS){
        this.PREF_CERTIFICATE_REVOKE_STATUS = PREF_CERTIFICATE_REVOKE_STATUS;
    }
    
    public String getPREF_CERTIFICATE_REVOKE_STATUS(){
        return this.PREF_CERTIFICATE_REVOKE_STATUS;
    }
    
    public void setPROMOTIONAL_NOTIFICATION(String PROMOTIONAL_NOTIFICATION){
        this.PROMOTIONAL_NOTIFICATION = PROMOTIONAL_NOTIFICATION;
    }
    
    public String getPROMOTIONAL_NOTIFICATION(){
        return this.PROMOTIONAL_NOTIFICATION;
    }
    
    

	public Map<String, String> getpREF_PAYMENT_STATUS() {
		return pREF_PAYMENT_STATUS;
	}

	public void setpREF_PAYMENT_STATUS(Map<String, String> pREF_PAYMENT_STATUS) {
		this.pREF_PAYMENT_STATUS = pREF_PAYMENT_STATUS;
	}

	public Map<String, String> getpREF_TRANSACTION_ID() {
		return pREF_TRANSACTION_ID;
	}

	public void setpREF_TRANSACTION_ID(Map<String, String> pREF_TRANSACTION_ID) {
		this.pREF_TRANSACTION_ID = pREF_TRANSACTION_ID;
	}

	@Override
	public String toString() {
		return "NotificationContextDTO [PREF_ONBOARDING_STATUS=" + PREF_ONBOARDING_STATUS
				+ ", PREF_ONBOARDING_APPROVAL_STATUS=" + PREF_ONBOARDING_APPROVAL_STATUS + ", PREF_CERTIFICATE_STATUS="
				+ PREF_CERTIFICATE_STATUS + ", PREF_CERTIFICATE_REVOKE_STATUS=" + PREF_CERTIFICATE_REVOKE_STATUS
				+ ", PROMOTIONAL_NOTIFICATION=" + PROMOTIONAL_NOTIFICATION + ", pREF_PAYMENT_STATUS="
				+ pREF_PAYMENT_STATUS + ", pREF_TRANSACTION_ID=" + pREF_TRANSACTION_ID + "]";
	}

//	@Override
//	public String toString() {
//		return "NotificationContextDTO [PREF_ONBOARDING_STATUS=" + PREF_ONBOARDING_STATUS
//				+ ", PREF_ONBOARDING_APPROVAL_STATUS=" + PREF_ONBOARDING_APPROVAL_STATUS + ", PREF_CERTIFICATE_STATUS="
//				+ PREF_CERTIFICATE_STATUS + ", PREF_CERTIFICATE_REVOKE_STATUS=" + PREF_CERTIFICATE_REVOKE_STATUS
//				+ ", PROMOTIONAL_NOTIFICATION=" + PROMOTIONAL_NOTIFICATION + "]";
//	}
	
	
	
}
