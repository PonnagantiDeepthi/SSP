package ug.daes.onboarding.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ug.daes.onboarding.enums.LogMessageType;
import ug.daes.onboarding.enums.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditDTO implements Serializable {
	@NotNull
	private String serviceName;
	@NotNull
	private TransactionType transactionType;
	@NotNull
	private LogMessageType logMessageType;

	private String signatureType;

	@NotNull
	@NotEmpty
	private String identifier;
	@NotNull
	@NotEmpty
	private String correlationID;
	@NotNull
	@NotEmpty
	private String transactionID;
	private String subTransactionID;
	@NotNull
	@NotEmpty
	private String startTime;
	private String endTime;
	private String geoLocation;
	private String callStack;
	@NotNull
	@NotEmpty
	private String logMessage;
	private String transactionSubType;
	private String serviceProviderName;
	private String serviceProviderAppName;
	private Boolean eSealUsed;
	@NotNull
	@NotEmpty
	private String checksum;
	private String timestamp;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LogMessageType getLogMessageType() {
		return logMessageType;
	}

	public void setLogMessageType(LogMessageType logMessageType) {
		this.logMessageType = logMessageType;
	}

	// public SignatureType getSignatureType() {
	// return signatureType;
	// }
	//
	// public void setSignatureType(SignatureType signatureType) {
	// this.signatureType = signatureType;
	// }

	public String getIdentifier() {
		return identifier;
	}

	public String getSignatureType() {
		return signatureType;
	}

	public void setSignatureType(String signatureType) {
		this.signatureType = signatureType;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getSubTransactionID() {
		return subTransactionID;
	}

	public void setSubTransactionID(String subTransactionID) {
		this.subTransactionID = subTransactionID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getCallStack() {
		return callStack;
	}

	public void setCallStack(String callStack) {
		this.callStack = callStack;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getServiceProviderAppName() {
		return serviceProviderAppName;
	}

	public void setServiceProviderAppName(String serviceProviderAppName) {
		this.serviceProviderAppName = serviceProviderAppName;
	}

	public Boolean geteSealUsed() {
		return eSealUsed;
	}

	public void seteSealUsed(Boolean eSealUsed) {
		this.eSealUsed = eSealUsed;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "AuditDTO{" + "serviceName=" + serviceName + ", transactionType=" + transactionType + ", logMessageType="
				+ logMessageType + ", signatureType=" + signatureType + ", identifier='" + identifier + '\''
				+ ", correlationID='" + correlationID + '\'' + ", transactionID='" + transactionID + '\''
				+ ", subTransactionID='" + subTransactionID + '\'' + ", timestamp=" + timestamp + ", startTime='"
				+ startTime + '\'' + ", endTime='" + endTime + '\'' + ", geoLocation='" + geoLocation + '\''
				+ ", callStack='" + callStack + '\'' + ", logMessage='" + logMessage + '\'' + ", transactionSubType='"
				+ transactionSubType + '\'' + ", serviceProviderName='" + serviceProviderName + '\''
				+ ", serviceProviderAppName='" + serviceProviderAppName + '\'' + ", eSealUsed=" + eSealUsed
				+ ", checksum='" + checksum + '\'' + '}';
	}
}
