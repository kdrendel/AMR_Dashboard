package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EncounterFile {
	
	public String rownum;
	public String flowid;
	public String filename;
	public String claimcount;
	public String directory;
	public String payload;
	public String comparepayload;
	
	
	
	public String getComparepayload() {
		return comparepayload;
	}
	public void setComparepayload(String comparepayload) {
		this.comparepayload = comparepayload;
	}
	public String getRownum() {
		return rownum;
	}
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getClaimcount() {
		return claimcount;
	}
	public void setClaimcount(String claimcount) {
		this.claimcount = claimcount;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
}