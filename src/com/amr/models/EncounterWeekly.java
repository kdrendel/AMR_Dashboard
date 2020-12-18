package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EncounterWeekly {

	public String weekStart;
	public String weekEnd;
	public String status;
	public Number total;
	public Number weekNumber;

	public Number acceptedCnt;
	public Number rejectedCnt;
	public Number submittedCnt;
	public Number readyCnt;
	public Number soaerrorCnt;
	public Number nostatusCnt;
	public Number nonbilledCnt;

	public Number getTotal() {
		return total;
	}

	public void setTotal(Number total) {
		this.total = total;
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

	public String getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}

	public String getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Number getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Number weekNumber) {
		this.weekNumber = weekNumber;
	}

}