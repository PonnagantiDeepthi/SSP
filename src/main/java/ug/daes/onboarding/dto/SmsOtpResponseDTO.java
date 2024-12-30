package ug.daes.onboarding.dto;

import java.io.Serializable;
import java.util.List;

public class SmsOtpResponseDTO implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uuid;
	 
	 private String sender;
	 
	 private String receiver;
	 
	 private String text;
	 
	 private String url;
	 
	 private String operator;
	 
	 private String created_at;
	 
	 private String dlr_url;
	 
	 private String dlr_status;
	 
	 private String external_ref;
	 
	 private String created_by;
	 
	 private String organization;
	 
	 private List<String> non_field_errors;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getDlr_url() {
		return dlr_url;
	}

	public void setDlr_url(String dlr_url) {
		this.dlr_url = dlr_url;
	}

	public String getDlr_status() {
		return dlr_status;
	}

	public void setDlr_status(String dlr_status) {
		this.dlr_status = dlr_status;
	}

	public String getExternal_ref() {
		return external_ref;
	}

	public void setExternal_ref(String external_ref) {
		this.external_ref = external_ref;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public List<String> getNon_field_errors() {
		return non_field_errors;
	}

	public void setNon_field_errors(List<String> non_field_errors) {
		this.non_field_errors = non_field_errors;
	}

	@Override
	public String toString() {
		return "SmsOtpResponseDTO [uuid=" + uuid + ", sender=" + sender + ", receiver=" + receiver + ", text=" + text
				+ ", url=" + url + ", operator=" + operator + ", created_at=" + created_at + ", dlr_url=" + dlr_url
				+ ", dlr_status=" + dlr_status + ", external_ref=" + external_ref + ", created_by=" + created_by
				+ ", organization=" + organization + ", non_field_errors=" + non_field_errors + "]";
	}
	 
}
