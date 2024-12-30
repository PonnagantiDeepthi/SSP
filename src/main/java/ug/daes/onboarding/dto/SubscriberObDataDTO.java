/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class SubscriberObDataDTO {
	
	
	private int documentType;
	
	private String primaryIdentifier;
	
	private String secondaryIdentifier;
	
	private String nationality;
	
	private String gender;
	
	private String dateOfBirth;
	
	private String optionalData1;
	
	private String optionalData2;
	
	private String documentNumber;
	
	private String dateOfExpiry;
	
	private String documentCode;
	
	private String issuingState;
	
	private String photoVerificationPerc;
	
	private String subscriberSelfie;

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getPrimaryIdentifier() {
		return primaryIdentifier;
	}

	public void setPrimaryIdentifier(String primaryIdentifier) {
		this.primaryIdentifier = primaryIdentifier;
	}

	public String getSecondaryIdentifier() {
		return secondaryIdentifier;
	}

	public void setSecondaryIdentifier(String secondaryIdentifier) {
		this.secondaryIdentifier = secondaryIdentifier;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getOptionalData1() {
		return optionalData1;
	}

	public void setOptionalData1(String optionalData1) {
		this.optionalData1 = optionalData1;
	}

	public String getOptionalData2() {
		return optionalData2;
	}

	public void setOptionalData2(String optionalData2) {
		this.optionalData2 = optionalData2;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getIssuingState() {
		return issuingState;
	}

	public void setIssuingState(String issuingState) {
		this.issuingState = issuingState;
	}

	public String getPhotoVerificationPerc() {
		return photoVerificationPerc;
	}

	public void setPhotoVerificationPerc(String photoVerificationPerc) {
		this.photoVerificationPerc = photoVerificationPerc;
	}

	public String getSubscriberSelfie() {
		return subscriberSelfie;
	}

	public void setSubscriberSelfie(String subscriberSelfie) {
		this.subscriberSelfie = subscriberSelfie;
	}

	@Override
	public String toString() {
		return "SubscriberObDataDTO [documentType=" + documentType + ", primaryIdentifier=" + primaryIdentifier
				+ ", secondaryIdentifier=" + secondaryIdentifier + ", nationality=" + nationality + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", optionalData1=" + optionalData1 + ", optionalData2="
				+ optionalData2 + ", documentNumber=" + documentNumber + ", dateOfExpiry=" + dateOfExpiry
				+ ", documentCode=" + documentCode + ", issuingState=" + issuingState + ", photoVerificationPerc="
				+ photoVerificationPerc + ", subscriberSelfie=" + subscriberSelfie + "]";
	}

}
