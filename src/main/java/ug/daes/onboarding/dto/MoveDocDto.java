/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class MoveDocDto {
	
	private String documents_pk_list;

    public MoveDocDto() { }

    public String getDocuments_pk_list() {
        return documents_pk_list;
    }

    public void setDocuments_pk_list(String documents_pk_list) {
        this.documents_pk_list = documents_pk_list;
    }

    @Override
    public String toString() {
        return "moveDocDto{" +
                "documents_pk_list='" + documents_pk_list + '\'' +
                '}';
    }

}
