/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class PortalDTO {
	
	private static final long serialVersionUID = 1L;

	private int templateId;

	private String creationDate;

	private String displayName;

	private String method;

	private String template;
	
	private String publishStatus;
	
	private String state;
	
//	private List<Template_Custom_Field> additionalFields;
	
	private String photoThreshold;
	
	private Boolean isDocsRequired;
	
//	private List<IntegrationURLs> integrationUrls;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getPhotoThreshold() {
		return photoThreshold;
	}

	public void setPhotoThreshold(String photoThreshold) {
		this.photoThreshold = photoThreshold;
	}

	public Boolean getIsDocsRequired() {
		return isDocsRequired;
	}

	public void setIsDocsRequired(Boolean isDocsRequired) {
		this.isDocsRequired = isDocsRequired;
	}

	@Override
	public String toString() {
		return "PortalDTO [templateId=" + templateId + ", creationDate=" + creationDate + ", displayName=" + displayName
				+ ", method=" + method + ", template=" + template + ", publishStatus=" + publishStatus + ", state="
				+ state + ", photoThreshold=" + photoThreshold + ", isDocsRequired=" + isDocsRequired + "]";
	}

}
