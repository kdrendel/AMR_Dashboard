package com.amr.models;

public class EncounterSOAInstances {
	public String created;
	public String updated;
	public String flowid;
	public String composite;
	public String state;
	public String filecount;
	public String claimcount;
	public String pullcount;
	public String errorMessage;
	public String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String  flow_correlation_id;
	public String  kick_off;
	
	
	public String getKick_off() {
		return kick_off;
	}
	public void setKick_off(String kick_off) {
		this.kick_off = kick_off;
	}
	public String getPullcount() {
		return pullcount;
	}
	public void setPullcount(String pullcount) {
		this.pullcount = pullcount;
	}
	public String getFilecount() {
		return filecount;
	}
	public void setFilecount(String filecount) {
		this.filecount = filecount;
	}
	public String getClaimcount() {
		return claimcount;
	}
	public void setClaimcount(String claimcount) {
		this.claimcount = claimcount;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public String getComposite() {
		return composite;
	}
	public void setComposite(String composite) {
		this.composite = composite;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getFlow_correlation_id() {
		return flow_correlation_id;
	}
	public void setFlow_correlation_id(String flow_correlation_id) {
		this.flow_correlation_id = flow_correlation_id;
	}



}