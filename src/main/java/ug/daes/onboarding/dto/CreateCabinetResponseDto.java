/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class CreateCabinetResponseDto {
	
	 private String label;
	    private int id;
	    private String parent;

	    public CreateCabinetResponseDto() { }

	    public String getLabel() {
	        return label;
	    }

	    public void setLabel(String label) {
	        this.label = label;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getParent() {
	        return parent;
	    }

	    public void setParent(String parent) {
	        this.parent = parent;
	    }

	    @Override
	    public String toString() {
	        return "CreateCabinetResponseDto{" +
	                "label='" + label + '\'' +
	                ", id=" + id +
	                ", parent='" + parent + '\'' +
	                '}';
	    }

}
