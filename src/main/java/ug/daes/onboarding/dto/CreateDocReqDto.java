/**
 * 
 */
package ug.daes.onboarding.dto;

import org.springframework.core.io.Resource;

/**
 * @author Raxit Dubey
 *
 */
public class CreateDocReqDto {
	
	private String description;
    private int documentType;
    private int id;
    private Resource file;
    private String label;
    private String language;
    private int documentTypeId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resource getFile() {
        return file;
    }

    public void setFile(Resource file) {
        this.file = file;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
    
}
