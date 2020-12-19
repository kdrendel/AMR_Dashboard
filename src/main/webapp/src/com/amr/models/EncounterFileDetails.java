package com.amr.models;

import java.util.List;

import com.amr.models.EncounterCounts;

public class EncounterFileDetails {

	public String billableEntityName;
	public String planSubCode;
	public String memberID;
	public String firstName;
	public String lastName;
	public String dob;
	public String stopID;
	public String hcpc;
	public String dx;
	public String tripModifier;
	public String billingModifier;
	public String mileage;
	public String pUName;
	public String pUAddress1;
	public String pUAddress2;
	public String pUCity;
	public String pUState;
	public String pUZIPCode;
	public String destinationName;
	public String destinationAddress1;
	public String destinationAddress2;
	public String destinationCity;
	public String destinationState;
	public String destinationZIPCode;
	public String billingFileName;
	public String systemCreateDate;
	public String sOAError;
	public String status;
	public String description;
	public String priorActor;
	public String statusColor;
	public String sentToOracle;
	public String oracleSentDate;
	public String billAmount;
	public String paidAmount;
	public String planPaidDate;
	public String transportationProvider;
	public String serviceDateFrom;
	public String serviceDateTo;
	public String groupID;
	public String memberExtendedCode1;
	public String memberExtendedCode2;
	public String memberExtendedCode3;
	public String memberExtendedCode4;
	public String memberExtendedCode5;
	public String memberExtendedCode6;
	public String memberExtendedCode7;
	public String difference;
	public String Errors;
	public String StatusId;
	public String providerPayDate;
	public String tripModifier2;
	public String billingClaimType;
	public String billingClaimNumber;
	

	public String getProviderPayDate() {
		return providerPayDate;
	}
	public void setProviderPayDate(String providerPayDate) {
		this.providerPayDate = providerPayDate;
	}
	public String getTripModifier2() {
		return tripModifier2;
	}
	public void setTripModifier2(String tripModifier2) {
		this.tripModifier2 = tripModifier2;
	}
	public String getBillingClaimType() {
		return billingClaimType;
	}
	public void setBillingClaimType(String billingClaimType) {
		this.billingClaimType = billingClaimType;
	}
	public String getBillingClaimNumber() {
		return billingClaimNumber;
	}
	public void setBillingClaimNumber(String billingClaimNumber) {
		this.billingClaimNumber = billingClaimNumber;
	}
	public String getStatusId() {
		return StatusId;
	}
	public void setStatusId(String statusId) {
		StatusId = statusId;
	}
	public String getErrors() {
		return Errors;
	}
	public void setErrors(String errors) {
		Errors = errors;
	}
	public String getDifference() {
		return difference;
	}
	public void setDifference(String difference) {
		this.difference = difference;
	}
	public String getHcpc() {
		return hcpc;
	}
	public void setHcpc(String hcpc) {
		this.hcpc = hcpc;
	}
	public String getDx() {
		return dx;
	}
	public void setDx(String dx) {
		this.dx = dx;
	}
	public String getBillableEntityName() {
		return billableEntityName;
	}
	public void setBillableEntityName(String billableEntityName) {
		this.billableEntityName = billableEntityName;
	}
	public String getPlanSubCode() {
		return planSubCode;
	}
	public void setPlanSubCode(String planSubCode) {
		this.planSubCode = planSubCode;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getStopID() {
		return stopID;
	}
	public void setStopID(String stopID) {
		this.stopID = stopID;
	}
	public String gethCPC() {
		return hcpc;
	}
	public void sethCPC(String hcpc) {
		this.hcpc = hcpc;
	}

	public String getTripModifier() {
		return tripModifier;
	}
	public void setTripModifier(String tripModifier) {
		this.tripModifier = tripModifier;
	}
	public String getBillingModifier() {
		return billingModifier;
	}
	public void setBillingModifier(String billingModifier) {
		this.billingModifier = billingModifier;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getpUName() {
		return pUName;
	}
	public void setpUName(String pUName) {
		this.pUName = pUName;
	}
	public String getpUAddress1() {
		return pUAddress1;
	}
	public void setpUAddress1(String pUAddress1) {
		this.pUAddress1 = pUAddress1;
	}
	public String getpUAddress2() {
		return pUAddress2;
	}
	public void setpUAddress2(String pUAddress2) {
		this.pUAddress2 = pUAddress2;
	}
	public String getpUCity() {
		return pUCity;
	}
	public void setpUCity(String pUCity) {
		this.pUCity = pUCity;
	}
	public String getpUState() {
		return pUState;
	}
	public void setpUState(String pUState) {
		this.pUState = pUState;
	}
	public String getpUZIPCode() {
		return pUZIPCode;
	}
	public void setpUZIPCode(String pUZIPCode) {
		this.pUZIPCode = pUZIPCode;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationAddress1() {
		return destinationAddress1;
	}
	public void setDestinationAddress1(String destinationAddress1) {
		this.destinationAddress1 = destinationAddress1;
	}
	public String getDestinationAddress2() {
		return destinationAddress2;
	}
	public void setDestinationAddress2(String destinationAddress2) {
		this.destinationAddress2 = destinationAddress2;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public String getDestinationZIPCode() {
		return destinationZIPCode;
	}
	public void setDestinationZIPCode(String destinationZIPCode) {
		this.destinationZIPCode = destinationZIPCode;
	}
	public String getBillingFileName() {
		return billingFileName;
	}
	public void setBillingFileName(String billingFileName) {
		this.billingFileName = billingFileName;
	}
	public String getSystemCreateDate() {
		return systemCreateDate;
	}
	public void setSystemCreateDate(String systemCreateDate) {
		this.systemCreateDate = systemCreateDate;
	}
	public String getsOAError() {
		return sOAError;
	}
	public void setsOAError(String sOAError) {
		this.sOAError = sOAError;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriorActor() {
		return priorActor;
	}
	public void setPriorActor(String priorActor) {
		this.priorActor = priorActor;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
	public String getSentToOracle() {
		return sentToOracle;
	}
	public void setSentToOracle(String sentToOracle) {
		this.sentToOracle = sentToOracle;
	}
	public String getOracleSentDate() {
		return oracleSentDate;
	}
	public void setOracleSentDate(String oracleSentDate) {
		this.oracleSentDate = oracleSentDate;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getPlanPaidDate() {
		return planPaidDate;
	}
	public void setPlanPaidDate(String planPaidDate) {
		this.planPaidDate = planPaidDate;
	}
	public String getTransportationProvider() {
		return transportationProvider;
	}
	public void setTransportationProvider(String transportationProvider) {
		this.transportationProvider = transportationProvider;
	}
	public String getServiceDateFrom() {
		return serviceDateFrom;
	}
	public void setServiceDateFrom(String serviceDateFrom) {
		this.serviceDateFrom = serviceDateFrom;
	}
	public String getServiceDateTo() {
		return serviceDateTo;
	}
	public void setServiceDateTo(String serviceDateTo) {
		this.serviceDateTo = serviceDateTo;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getMemberExtendedCode1() {
		return memberExtendedCode1;
	}
	public void setMemberExtendedCode1(String memberExtendedCode1) {
		this.memberExtendedCode1 = memberExtendedCode1;
	}
	public String getMemberExtendedCode2() {
		return memberExtendedCode2;
	}
	public void setMemberExtendedCode2(String memberExtendedCode2) {
		this.memberExtendedCode2 = memberExtendedCode2;
	}
	public String getMemberExtendedCode3() {
		return memberExtendedCode3;
	}
	public void setMemberExtendedCode3(String memberExtendedCode3) {
		this.memberExtendedCode3 = memberExtendedCode3;
	}
	public String getMemberExtendedCode4() {
		return memberExtendedCode4;
	}
	public void setMemberExtendedCode4(String memberExtendedCode4) {
		this.memberExtendedCode4 = memberExtendedCode4;
	}
	public String getMemberExtendedCode5() {
		return memberExtendedCode5;
	}
	public void setMemberExtendedCode5(String memberExtendedCode5) {
		this.memberExtendedCode5 = memberExtendedCode5;
	}
	public String getMemberExtendedCode6() {
		return memberExtendedCode6;
	}
	public void setMemberExtendedCode6(String memberExtendedCode6) {
		this.memberExtendedCode6 = memberExtendedCode6;
	}
	public String getMemberExtendedCode7() {
		return memberExtendedCode7;
	}
	public void setMemberExtendedCode7(String memberExtendedCode7) {
		this.memberExtendedCode7 = memberExtendedCode7;
	}



}