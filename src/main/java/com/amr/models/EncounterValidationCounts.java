package com.amr.models;

import java.util.List;

public class EncounterValidationCounts {
	public String billingid;
	public String healthplan;
	public String count;
	public String billableEntityName;
	


	public String getBillableEntityName() {
		return billableEntityName;
	}
	public void setBillableEntityName(String billableEntityName) {
		this.billableEntityName = billableEntityName;
	}
	public String getBillingid() {
		return billingid;
	}
	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}
	public String getHealthplan() {
		return healthplan;
	}
	public void setHealthplan(String healthplan) {
		this.healthplan = healthplan;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

}