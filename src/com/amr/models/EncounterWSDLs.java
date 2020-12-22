package com.amr.models;

import java.util.List;

public class EncounterWSDLs {
	public String id;
	public String name;
	public String wsdl;
	public String wsdlDisplay;
	public String billingid;
	public String comments;
	public String lastUpdated;
	public String validations;
	public  List<EncounterSOAInstances>  usecases;

	public List<EncounterSOAInstances> getUsecases() {
		return usecases;
	}

	public void setUsecases(List<EncounterSOAInstances> usecases) {
		this.usecases = usecases;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getWsdlDisplay() {
		return wsdlDisplay;
	}

	public void setWsdlDisplay(String wsdlDisplay) {
		this.wsdlDisplay = wsdlDisplay;
	}

	public List<EncounterReadyCounts> readyitems;

	public List<EncounterReadyCounts> getReadyitems() {
		return readyitems;
	}

	public void setReadyitems(List<EncounterReadyCounts> readyitems) {
		this.readyitems = readyitems;
	}

	public String getBillingid() {
		return billingid;
	}

	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWsdl() {
		return wsdl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

}