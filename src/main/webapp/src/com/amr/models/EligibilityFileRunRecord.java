package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EligibilityFileRunRecord {

	public String creationDate;
	public String modifyDate;
	public String triedTimes;
	public String state;
	public String stateText;
	public String fileName;
	public String planName;
	public String loadStatus;
	public String faultText;
	public String memberCount;
	public String threshHoldvalue;
	public String instanceKey;
	public String flowId;
	public String compositeName;
	public String lastAction;
	public Number successfulInd;
	public Number changePercentage;
	public Number oldCount;
	public Number newCount;
	public Number fileMemberCount;
	public Number updated;
	public Number removed;
	public Number added;
	public Long searchDate;
	public String b2bError;
	public String approver;
	public String subCoontractorId;
	public String validationsCount;
	public String urlLink;
	
	

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public String getValidationsCount() {
		return validationsCount;
	}

	public void setValidationsCount(String validationsCount) {
		this.validationsCount = validationsCount;
	}

	public String processingTime;

	public String getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
	}

	public String getB2bError() {
		return b2bError;
	}

	public void setB2bError(String b2bError) {
		this.b2bError = b2bError;
	}

	public Number getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Long searchDate) {
		this.searchDate = searchDate;
	}

	public Number getAdded() {
		return added;
	}

	public void setAdded(Number added) {
		this.added = added;
	}

	public String getSubCoontractorId() {
		return subCoontractorId;
	}

	public void setSubCoontractorId(String subCoontractorId) {
		this.subCoontractorId = subCoontractorId;
	}

	public Number getSuccessfulInd() {
		return successfulInd;
	}

	public void setSuccessfulInd(Number successfulInd) {
		this.successfulInd = successfulInd;
	}

	public Number getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(Number changePercentage) {
		this.changePercentage = changePercentage;
	}

	public Number getOldCount() {
		return oldCount;
	}

	public void setOldCount(Number oldCount) {
		this.oldCount = oldCount;
	}

	public Number getNewCount() {
		return newCount;
	}

	public void setNewCount(Number newCount) {
		this.newCount = newCount;
	}

	public Number getFileMemberCount() {
		return fileMemberCount;
	}

	public void setFileMemberCount(Number fileMemberCount) {
		this.fileMemberCount = fileMemberCount;
	}

	public Number getUpdated() {
		return updated;
	}

	public void setUpdated(Number updated) {
		this.updated = updated;
	}

	public Number getRemoved() {
		return removed;
	}

	public void setRemoved(Number removed) {
		this.removed = removed;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getFaultText() {
		return faultText;
	}

	public void setFaultText(String faultText) {
		this.faultText = faultText;
	}

	public String getInstanceKey() {
		return instanceKey;
	}

	public void setInstanceKey(String instanceKey) {
		this.instanceKey = instanceKey;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getTriedTimes() {
		return triedTimes;
	}

	public void setTriedTimes(String triedTimes) {
		this.triedTimes = triedTimes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}

	public String getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}

	public String getThreshHoldvalue() {
		return threshHoldvalue;
	}

	public void setThreshHoldvalue(String threshHoldvalue) {
		this.threshHoldvalue = threshHoldvalue;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCompositeName() {
		return compositeName;
	}

	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}

	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

}