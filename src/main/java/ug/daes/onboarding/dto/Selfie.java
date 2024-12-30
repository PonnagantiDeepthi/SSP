/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class Selfie {
	

	private String subscriberSelfie;
	private String subscriberUniqueId;

	public Selfie() { }

	public String getSubscriberSelfie() {
	    return subscriberSelfie;
	}

	public void setSubscriberSelfie(String subscriberSelfie) {
	    this.subscriberSelfie = subscriberSelfie;
	}

	public String getSubscriberUniqueId() {
	    return subscriberUniqueId;
	}

	public void setSubscriberUniqueId(String subscriberUniqueId) {
	    this.subscriberUniqueId = subscriberUniqueId;
	}

	@Override
	public String toString() {
	    return "Selfie{" +
	            "subscriberSelfie='" + subscriberSelfie + '\'' +
	            ", subscriberUniqueId='" + subscriberUniqueId + '\'' +
	            '}';
	}
	
}
