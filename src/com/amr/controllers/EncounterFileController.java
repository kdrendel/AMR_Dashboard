package com.amr.controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.amr.models.Encounter;
import com.amr.models.EncounterCounts;
import com.amr.models.EncounterFileDetails;

@ManagedBean
@SessionScoped
public class EncounterFileController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Connection StatsCon = null;
	private List<EncounterFileDetails> encounterFileFilter;

	public List<EncounterFileDetails> getEncounterFileFilter() {
		return encounterFileFilter;
	}

	public void setEncounterFileFilter(List<EncounterFileDetails> encounterFileFilter) {
		this.encounterFileFilter = encounterFileFilter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String previousPage = null;

	public Connection getStatsCon() {
		return StatsCon;
	}

	public void setStatsCon(Connection statsCon) {
		StatsCon = statsCon;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public void openSQLServerConnection() throws Exception {
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SpiceApps");
		StatsCon = dataSourceSOA.getConnection();

	}

	public void closeSQLServerConnection() throws Exception {
		StatsCon.close();
	}

	public List<EncounterFileDetails> getFileList() throws Exception {
		List<EncounterFileDetails> list = new ArrayList<EncounterFileDetails>();
		openSQLServerConnection();
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		params.get("logID");
		String logID = params.get("logID");

		PreparedStatement ps = StatsCon.prepareStatement(
				"use a2cbilling select  ProviderPayDate,TripModifier2, BillingClaimType ,CLM.BillingClaimNumber, StatusId,CASE  WHEN BSC.Status not in  ('Non-Billable','Accepted') THEN         DATEDIFF(d, BF.SystemCreateDate, SYSDATETIME() )  else         null  END  as Difference ,BillableEntityName,HealthPlanCode PlanSubCode,MemberID,FirstName,LastName,DOB,StopID,HCPC , DiagnosisCode DX, TripModifier,BillingModifier,Mileage ,PUName,PUAddress1, PUAddress2,PUCity,PUState,PUZIPCode ,DestinationName,DestinationAddress1,DestinationAddress2,DestinationCity,DestinationState,DestinationZIPCode ,BillingFileName,BF.SystemCreateDate,'SOA Error' SOAError,BSC.Status,Description,PriorActor,StatusColor ,SentToOracle,OracleSentDate,BillAmount,ProviderPayableAmount PaidAmount,PlanPaidDate,TransportationProvider,ServiceDateFrom ,ServiceDateTo,GroupID,MemberExtendedCode1,MemberExtendedCode2,MemberExtendedCode3,MemberExtendedCode4 ,MemberExtendedCode5,MemberExtendedCode6,MemberExtendedCode7  ,ERRORS = STUFF(               (                      SELECT ', ' + ERR.[BillingClaimResponseErrorMessage]                      FROM [dbo].BillingClaimResponseError ERR (nolock)                      WHERE RSP.[BillingClaimResponse_ID] = ERR.[BillingClaimResponse_ID]                      ORDER BY ERR.[BillingClaimResponseErrorMessage]                      FOR XML PATH (''), TYPE               ).value('.', 'varchar(max)'), 1, 1, '')   from dbo.BillingFile BF with (nolock)   INNER JOIN dbo.BillableEntity ENT with (nolock) ON BF.BillableEntity_ID = ENT.BillableEntity_ID   INNER JOIN dbo.BillingClaim CLM with (nolock) ON BF.BillingFile_ID = CLM.BillingFile_ID   INNER JOIN dbo.BillingClaimLine CLN with (nolock) ON CLM.BillingClaim_ID = CLN.BillingClaim_ID   INNER JOIN dbo.BillingClaimLineSource SRC with (nolock) ON CLN.BillingClaimLine_ID = SRC.BillingClaimLine_ID  INNER JOIN dbo.vwEMSCBilling837Interface REP with (nolock) ON SRC.BillingRepository_ID = REP.BillingRepository_ID   INNER JOIN dbo.BillingProcessingStatusCodes BSC with (nolock) ON BSC.StatusID = REP.ProcessingStatus_ID  LEFT OUTER JOIN dbo.BillingClaimResponse RSP with (nolock) ON CLM.[BillingClaimNumber] = RSP.[BillingClaimNumber] where BF.BillingFile_ID = "
						+ logID);

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterFileDetails EncounterFileRec = new EncounterFileDetails();

			EncounterFileRec.setProviderPayDate(result.getString("providerPayDate"));
			EncounterFileRec.setTripModifier2(result.getString("tripModifier2"));
			EncounterFileRec.setBillingClaimType(result.getString("billingClaimType"));
			EncounterFileRec.setBillingClaimNumber(result.getString("billingClaimNumber"));
			
			EncounterFileRec.setBillableEntityName(result.getString("billableEntityName"));
			EncounterFileRec.setPlanSubCode(result.getString("PlanSubCode"));
			EncounterFileRec.setMemberID(result.getString("MemberID"));
			EncounterFileRec.setFirstName(result.getString("FirstName"));
			EncounterFileRec.setLastName(result.getString("LastName"));
			EncounterFileRec.setDob(result.getString("DOB"));
			EncounterFileRec.setStatusId(result.getString("StatusId"));
			EncounterFileRec.setStopID(result.getString("StopID"));
			EncounterFileRec.setHcpc(result.getString("HCPC"));
			EncounterFileRec.setErrors(result.getString("ERRORS"));
			EncounterFileRec.setDx(result.getString("DX"));
			EncounterFileRec.setTripModifier(result.getString("TripModifier"));
			EncounterFileRec.setBillingModifier(result.getString("BillingModifier"));
			EncounterFileRec.setMileage(result.getString("Mileage"));
			EncounterFileRec.setpUName(result.getString("PUName"));
			EncounterFileRec.setpUAddress1(result.getString("PUAddress1"));
			EncounterFileRec.setpUAddress2(result.getString("PUAddress2"));
			EncounterFileRec.setpUCity(result.getString("PUCity"));
			EncounterFileRec.setpUState(result.getString("PUState"));
			EncounterFileRec.setpUZIPCode(result.getString("PUZIPCode"));
			EncounterFileRec.setDestinationName(result.getString("DestinationName"));
			EncounterFileRec.setDestinationAddress1(result.getString("DestinationAddress1"));
			EncounterFileRec.setDestinationAddress2(result.getString("DestinationAddress2"));
			EncounterFileRec.setDestinationCity(result.getString("DestinationCity"));
			EncounterFileRec.setDestinationState(result.getString("DestinationState"));
			EncounterFileRec.setDestinationZIPCode(result.getString("DestinationZIPCode"));
			EncounterFileRec.setBillingFileName(result.getString("BillingFileName"));
			EncounterFileRec.setsOAError(result.getString("SOAError"));
			EncounterFileRec.setStatus(result.getString("Status"));
			EncounterFileRec.setDescription(result.getString("Description"));
			EncounterFileRec.setPriorActor(result.getString("PriorActor"));
			EncounterFileRec.setStatusColor(result.getString("StatusColor"));
			EncounterFileRec.setSentToOracle(result.getString("SentToOracle"));
			EncounterFileRec.setOracleSentDate(result.getString("OracleSentDate"));
			EncounterFileRec.setBillAmount(result.getString("BillAmount"));
			EncounterFileRec.setPaidAmount(result.getString("PaidAmount"));
			EncounterFileRec.setPlanPaidDate(result.getString("PlanPaidDate"));
			EncounterFileRec.setTransportationProvider(result.getString("TransportationProvider"));
			EncounterFileRec.setServiceDateFrom(result.getString("ServiceDateFrom"));
			EncounterFileRec.setServiceDateTo(result.getString("ServiceDateTo"));
			EncounterFileRec.setGroupID(result.getString("GroupID"));
			EncounterFileRec.setMemberExtendedCode1(result.getString("MemberExtendedCode1"));
			EncounterFileRec.setMemberExtendedCode2(result.getString("MemberExtendedCode2"));
			EncounterFileRec.setMemberExtendedCode3(result.getString("MemberExtendedCode3"));
			EncounterFileRec.setMemberExtendedCode4(result.getString("MemberExtendedCode4"));
			EncounterFileRec.setMemberExtendedCode5(result.getString("MemberExtendedCode5"));
			EncounterFileRec.setMemberExtendedCode6(result.getString("MemberExtendedCode6"));
			EncounterFileRec.setMemberExtendedCode7(result.getString("MemberExtendedCode7"));
			EncounterFileRec.setDifference(result.getString("difference"));

			list.add(EncounterFileRec);
		}
		ps.close();
		result.close();
		closeSQLServerConnection();
		return list;
	}



	@PreDestroy
	public void cleanUp() throws Exception {

	}

}