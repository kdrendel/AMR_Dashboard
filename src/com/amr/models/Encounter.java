package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class Encounter {

	public String planName;
	public String logID;
	public String fileName;
	public String minDOS;
	public String maxDOS;
	public String periodStart;
	public String periodEnd;
	public String totalFare;
	public String tripCount;
	public String totalUnits;
	public String tripCountGrouped;
	public String batchDate;
	public String batchType;
	public Number acceptedCnt;
	public Number rejectedCnt;
	public Number submittedCnt;
	public Number readyCnt;
	public Number soaerrorCnt;
	public Number nostatusCnt;
	public Number nonbilledCnt;
	public String completed;
	public String percentage;
	public String Entityname;
	
	public String getEntityname() {
		return Entityname;
	}

	public void setEntityname(String entityname) {
		Entityname = entityname;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public Number getAcceptedCnt() {
		return acceptedCnt;
	}

	public void setAcceptedCnt(Number acceptedCnt) {
		this.acceptedCnt = acceptedCnt;
	}

	public Number getRejectedCnt() {
		return rejectedCnt;
	}

	public void setRejectedCnt(Number rejectedCnt) {
		this.rejectedCnt = rejectedCnt;
	}

	public Number getSubmittedCnt() {
		return submittedCnt;
	}

	public void setSubmittedCnt(Number submittedCnt) {
		this.submittedCnt = submittedCnt;
	}

	public Number getReadyCnt() {
		return readyCnt;
	}

	public void setReadyCnt(Number readyCnt) {
		this.readyCnt = readyCnt;
	}

	public Number getSoaerrorCnt() {
		return soaerrorCnt;
	}

	public void setSoaerrorCnt(Number soaerrorCnt) {
		this.soaerrorCnt = soaerrorCnt;
	}

	public Number getNostatusCnt() {
		return nostatusCnt;
	}

	public void setNostatusCnt(Number nostatusCnt) {
		this.nostatusCnt = nostatusCnt;
	}

	public Number getNonbilledCnt() {
		return nonbilledCnt;
	}

	public void setNonbilledCnt(Number nonbilledCnt) {
		this.nonbilledCnt = nonbilledCnt;
	}

	public List<EncounterCounts> statusCounts;

	public List<EncounterCounts> getStatusCounts() {
		return statusCounts;
	}

	public void setStatusCounts(List<EncounterCounts> statusCounts) {
		this.statusCounts = statusCounts;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getMinDOS() {
		return minDOS;
	}

	public void setMinDOS(String minDOS) {
		this.minDOS = minDOS;
	}

	public String getMaxDOS() {
		return maxDOS;
	}

	public void setMaxDOS(String maxDOS) {
		this.maxDOS = maxDOS;
	}

	public String getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(String totalFare) {
		this.totalFare = totalFare;
	}

	public String getTripCount() {
		return tripCount;
	}

	public void setTripCount(String tripCount) {
		this.tripCount = tripCount;
	}

	public String getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(String totalUnits) {
		this.totalUnits = totalUnits;
	}

	public String getTripCountGrouped() {
		return tripCountGrouped;
	}

	public void setTripCountGrouped(String tripCountGrouped) {
		this.tripCountGrouped = tripCountGrouped;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

}