/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class CreateDocResDto {
	
	private String description;
    private int document_type;
    private int id;
    private String label;
    private String language;

    public CreateDocResDto() { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDocument_type() {
        return document_type;
    }

    public void setDocument_type(int document_type) {
        this.document_type = document_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CreateDocResDto{" +
                "description='" + description + '\'' +
                ", document_type=" + document_type +
                ", id=" + id +
                ", label='" + label + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

}
