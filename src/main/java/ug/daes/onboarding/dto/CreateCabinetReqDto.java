/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class CreateCabinetReqDto {

	private String label;
    private String parent;

    public CreateCabinetReqDto() { }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "CreateCabinetReqDto{" +
                "label='" + label + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
	
}
