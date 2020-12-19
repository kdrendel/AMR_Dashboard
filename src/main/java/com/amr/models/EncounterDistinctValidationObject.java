package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EncounterDistinctValidationObject {

	public String healthPlancode;
	public String stopid;	
	public List<EncounterCounts> billingClaimErrorMessage;
	public String getHealthPlancode() {
		return healthPlancode;
	}
	public void setHealthPlancode(String healthPlancode) {
		this.healthPlancode = healthPlancode;
	}
	public String getStopid() {
		return stopid;
	}
	public void setStopid(String stopid) {
		this.stopid = stopid;
	}
	public List<EncounterCounts> getBillingClaimErrorMessage() {
		return billingClaimErrorMessage;
	}
	public void setBillingClaimErrorMessage(List<EncounterCounts> billingClaimErrorMessage) {
		this.billingClaimErrorMessage = billingClaimErrorMessage;
	}

		
}