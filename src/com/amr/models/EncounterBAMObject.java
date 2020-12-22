package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EncounterBAMObject {

	public String creationDate;
	public String type;
	
	public String claimsCount;
	public String fileName;
	public String status;
	public String plancode;
	
	public String url;
	public String errorText;
	
	public String billingid;
	public String pullCount;
	public String flowid;
	public String path;
	
	
	public String getBillingid() {
		return billingid;
	}
	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}
	public String getPullCount() {
		return pullCount;
	}
	public void setPullCount(String pullCount) {
		this.pullCount = pullCount;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getClaimsCount() {
		return claimsCount;
	}
	public void setClaimsCount(String claimsCount) {
		this.claimsCount = claimsCount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlancode() {
		return plancode;
	}
	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}
	
	
}