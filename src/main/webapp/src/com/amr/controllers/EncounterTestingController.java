package com.amr.controllers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.nio.*;
import java.nio.file.*;
import java.io.*;
import java.util.stream.*;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.xml.sax.SAXException;

import com.amr.models.EligibilityFileRunRecord;
import com.amr.models.EligibilityValidationInvalids;
import com.amr.models.Encounter;
import com.amr.models.EncounterBAMObject;
import com.amr.models.EncounterCounts;
import com.amr.models.EncounterReadyCounts;
import com.amr.models.EncounterSOAInstances;
import com.amr.models.EncounterValidationCounts;
import com.amr.models.EncounterValidationObject;
import com.amr.models.EncounterWSDLs;
import com.amr.models.EncounterWeekly;

@ManagedBean
@SessionScoped
public class EncounterTestingController implements Serializable {
	public String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	private static final long serialVersionUID = 1L;
	private static Connection SOACon = null;
	private String currentPartner = "X";
	private Connection StatsCon = null;

	public String getCurrentPartner() {
		return currentPartner;
	}

	public void setCurrentPartner(String currentPartner) {
		this.currentPartner = currentPartner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String scrollPos1 = "0";
	private String scrollPos2 = "0";

	public String getScrollPos2() {
		return scrollPos2;
	}

	public void setScrollPos2(String scrollPos2) {
		this.scrollPos2 = scrollPos2;
	}

	private List<EncounterWSDLs> encounterExecuteList = null;
	private List<EncounterReadyCounts> encounterRecordsSQLServer = null;

	private List<EncounterValidationCounts> encounterValidationsSQLServer = null;
	private List<EncounterValidationObject> encounterDistinctValidationsSQLServer = null;
	private List<EncounterValidationObject> encounterValidationsListSQLServer = null;

	public List<EncounterValidationObject> getEncounterDistinctValidationsSQLServer() {
		return encounterDistinctValidationsSQLServer;
	}

	public void setEncounterDistinctValidationsSQLServer(
			List<EncounterValidationObject> encounterDistinctValidationsSQLServer) {
		this.encounterDistinctValidationsSQLServer = encounterDistinctValidationsSQLServer;
	}

	private List<EncounterSOAInstances> encounterCurrentSOAInstances = null;
	private List<EncounterBAMObject> encounterBAMObjectInstances = null;

	public List<EncounterValidationObject> getEncounterValidationsListSQLServer() {
		return encounterValidationsListSQLServer;
	}

	public void setEncounterValidationsListSQLServer(
			List<EncounterValidationObject> encounterValidationsListSQLServer) {
		this.encounterValidationsListSQLServer = encounterValidationsListSQLServer;
	}

	public List<EncounterValidationCounts> getEncounterValidationsSQLServer() {
		return encounterValidationsSQLServer;
	}

	public void setEncounterValidationsSQLServer(List<EncounterValidationCounts> encounterValidationsSQLServer) {
		this.encounterValidationsSQLServer = encounterValidationsSQLServer;
	}

	public String getScrollPos1() {
		return scrollPos1;
	}

	public void setScrollPos1(String scrollPos1) {
		this.scrollPos1 = scrollPos1;
	}

	public List<EncounterBAMObject> getEncounterBAMObjectInstances() {
		return encounterBAMObjectInstances;
	}

	public void setEncounterBAMObjectInstances(List<EncounterBAMObject> encounterBAMObjectInstances) {
		this.encounterBAMObjectInstances = encounterBAMObjectInstances;
	}

	public List<EncounterReadyCounts> getEncounterRecordsSQLServer() {
		return encounterRecordsSQLServer;
	}

	public void setEncounterRecordsSQLServer(List<EncounterReadyCounts> encounterRecordsSQLServer) {
		this.encounterRecordsSQLServer = encounterRecordsSQLServer;
	}

	public List<EncounterSOAInstances> getEncounterCurrentSOAInstances() {
		return encounterCurrentSOAInstances;
	}

	public void setEncounterCurrentSOAInstances(List<EncounterSOAInstances> encounterCurrentSOAInstances) {
		this.encounterCurrentSOAInstances = encounterCurrentSOAInstances;
	}

	public List<EncounterWSDLs> getEncounterExecuteList() {
		return encounterExecuteList;
	}

	public void setEncounterExecuteList(List<EncounterWSDLs> encounterExecuteList) {
		this.encounterExecuteList = encounterExecuteList;
	}

	private String previousPage = null;
	private String previousPage2 = null;

	public String getPreviousPage2() {
		return previousPage2;
	}

	public void setPreviousPage2(String previousPage2) {
		this.previousPage2 = previousPage2;
	}

	public Connection getSOACon() {
		return SOACon;
	}

	public void setSOACon(Connection sOACon) {
		SOACon = sOACon;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public void checkF5() throws Exception {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		String id = viewRoot.getViewId();
		if (previousPage != null && (previousPage.equals(id))) {
			if (SOACon.isClosed()) {

			}
			{

				closeSoaInfraConnection();
			}
			openSoaInfraConnection();
			getInstanceList();
			getWSDLList();

			closeSoaInfraConnection();
		}
		previousPage = id;

	}

	public static void openSoaInfraConnection() throws Exception {
		// System.out.println("Open SOA Connection");
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SOAReporting");
		SOACon = dataSourceSOA.getConnection();

	}

	public void closeSQLServerConnection() throws Exception {
		// System.out.println("Close SQL Server Connection");
		StatsCon.close();
	}

	public void openSQLServerConnection() throws Exception {
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SpiceApps");
		StatsCon = dataSourceSOA.getConnection();
		// System.out.println("Open SQL Server Connection");
	}

	public void getEncounterDataSQLServer() throws Exception {
		List<EncounterReadyCounts> list = new ArrayList<EncounterReadyCounts>();
		openSQLServerConnection();

		PreparedStatement ps = StatsCon.prepareStatement(
				"use a2cbilling Select   b.BillableEntity_ID ,be.BillableEntityName,be.BillableEntity_ID,b.HealthPlanCode,FORMAT((Select Count(1) from vwBillableLineItems a  Where b.HealthPlanCode = a.HealthPlanCode and ProcessingStatus_ID = 6 ),'#,#') count from BillablePlanCode b,BillableEntity be where be.BillableEntity_ID = b.BillableEntity_ID group by  b.BillableEntity_ID ,be.BillableEntityName,be.BillableEntity_ID,b.HealthPlanCode order by be.BillableEntityName,b.BillableEntity_ID,HealthPlanCode");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			// System.out.println("In row " + result.getString("creationDate"));
			EncounterReadyCounts EligibilityRec = new EncounterReadyCounts();
			EligibilityRec.setBillingid(result.getString("BillableEntity_ID"));
			EligibilityRec.setHealthplan(result.getString("HealthPlanCode"));
			EligibilityRec.setBillableEntityName(result.getString("BillableEntityName"));

			EligibilityRec.setCount(result.getString("count"));

			list.add(EligibilityRec);
		}
		result.close();
		ps.close();
		encounterRecordsSQLServer = list;
		getEncounterValidationDataSQLServer();
		closeSQLServerConnection();

	}

	public void getEncounterValidationDataSQLServer() throws Exception {
		List<EncounterValidationCounts> list = new ArrayList<EncounterValidationCounts>();

		PreparedStatement ps = StatsCon.prepareStatement(
				"SELECT   [dbo].BillableEntity.BillableEntity_ID,  count(1) count FROM [A2CBilling].[dbo].[BillingClaimError]      INNER JOIN [A2CBilling].[dbo].[BillingClaimLine]        ON [A2CBilling].[dbo].[BillingClaimError].BillingClaim_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaim_ID      INNER JOIN [A2CBilling].[dbo].[BillingClaimLineSource]        ON [A2CBilling].[dbo].[BillingClaimLineSource].BillingClaimLine_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaimLine_ID      INNER JOIN [A2C].[dbo].[EMSCBilling837Interface]        ON [A2C].[dbo].[EMSCBilling837Interface].BillingRepository_ID = [A2CBilling].[dbo].[BillingClaimLineSource].BillingRepository_ID,      [dbo].BillablePlanCode,      [dbo].BillableEntity WHERE ProcessingStatus_ID = '8' AND [dbo].BillablePlanCode.BillableEntity_ID = [dbo].BillableEntity.BillableEntity_ID AND [A2C].[dbo].[EMSCBilling837Interface].HealthPlancode = [dbo].BillablePlanCode.HealthPlanCode group by [dbo].BillableEntity.BillableEntity_ID ORDER BY 1, 2");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			// System.out.println("In row " + result.getString("creationDate"));
			EncounterValidationCounts EligibilityRec = new EncounterValidationCounts();
			EligibilityRec.setBillingid(result.getString("BillableEntity_ID"));
			EligibilityRec.setCount(result.getString("count"));
			list.add(EligibilityRec);
		}
		result.close();
		ps.close();
		encounterValidationsSQLServer = list;

	}

	public String getTPName(String as_billingid) throws Exception {
		List<EncounterReadyCounts> list = new ArrayList<EncounterReadyCounts>();

		for (EncounterReadyCounts temp : encounterRecordsSQLServer) {

			if (temp.getBillingid().equalsIgnoreCase(as_billingid)) {

				return temp.getBillableEntityName();

			}

		}
		return null;
	}

	public String getValidationsLookup(String as_billingid) throws Exception {

		for (EncounterValidationCounts temp : encounterValidationsSQLServer) {

			if (temp.getBillingid().equalsIgnoreCase(as_billingid)) {

				return temp.count;

			}

		}
		return "0";

	}

	public List<EncounterReadyCounts> getBillingLookup(String as_billingid) throws Exception {
		List<EncounterReadyCounts> list = new ArrayList<EncounterReadyCounts>();

		for (EncounterReadyCounts temp : encounterRecordsSQLServer) {

			if (temp.getBillingid().equalsIgnoreCase(as_billingid)) {

				list.add(temp);

			}

		}
		return list;
	}

	public List<EncounterSOAInstances> getUseCasesLookup(String as_kickoff) throws Exception {
		List<EncounterSOAInstances> list = new ArrayList<EncounterSOAInstances>();

		for (EncounterSOAInstances temp : encounterCurrentSOAInstances) {

			if (temp.getKick_off().equalsIgnoreCase(as_kickoff)) {

				list.add(temp);

			}

		}
		return list;
	}

	public void getInstanceList() throws Exception {

		List<EncounterSOAInstances> list = new ArrayList<EncounterSOAInstances>();
		// System.out.println("In getInstanceList");
		FacesContext fc = FacesContext.getCurrentInstance();

		PreparedStatement ps = SOACon.prepareStatement(
				"SELECT     ln.name,ln.KICKOFF_ID,LN.Flowid,            LN.Billingid,            LN.Partnername composite,            LN.Instance,            LN.Pullcount,            'Completed' state,             LN.Created Created_Time,            ( SELECT   SUM( Claimcount )                FROM   Soareporting.Instance_Flow_Payload Ip               WHERE   Ip.Flowid = LN.Flowid )               claimcount,            ( SELECT   COUNT(distinct filename )                FROM   Soareporting.Instance_Flow_Payload Ip               WHERE   Ip.Flowid = LN.Flowid )               Filecount     FROM   Soareporting.Instance_Info LN where   ( SELECT   COUNT( distinct filename )                FROM   Soareporting.Instance_Flow_Payload Ip               WHERE   Ip.Flowid = LN.Flowid ) > 0 ORDER BY   1 DESC");

		ResultSet result = ps.executeQuery();

		while (result.next()) {

			EncounterSOAInstances EncounterFileRec = new EncounterSOAInstances();

			EncounterFileRec.setCreated(result.getString("CREATED_TIME"));
			EncounterFileRec.setFlowid(result.getString("flowid"));
			EncounterFileRec.setComposite(result.getString("composite"));
			EncounterFileRec.setState(result.getString("state"));
			EncounterFileRec.setClaimcount(result.getString("claimcount"));
			EncounterFileRec.setFilecount(result.getString("Filecount"));
			EncounterFileRec.setPullcount(result.getString("pullcount"));
			EncounterFileRec.setKick_off(result.getString("KICKOFF_ID"));
			EncounterFileRec.setName(result.getString("name"));

			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();
		encounterCurrentSOAInstances = list;
	}

	public void getBAMLogs() throws Exception {

		List<EncounterBAMObject> list = new ArrayList<EncounterBAMObject>();
		getEncounterDataSQLServer();

		PreparedStatement ps = SOACon.prepareStatement( //
				// "SELECT DECODE( NVL( LENGTH( NVL( B.Url, '' ) ), 0 ), 0, DECODE(
				// A.Claims_Count, 0, 'Encounter', 'Response' ), 'Encounter' ) TYPE, TO_CHAR(
				// A.Created_Date, 'MM-DD HH24:MI:SS' ) Created_Date, A.Claims_Count, DECODE(
				// NVL( LENGTH( NVL( B.Url, '' ) ), 0 ), 0, DECODE( B.State, 'MSG_ERROR',
				// A.File_Name, '/mnt/edi/Encounter/' || A.Plan_Name || '/Inbound/' || NVL(
				// B.Protocol_Message_Id, A.File_Name ) ), SUBSTR( B.Url, INSTR( B.Url, '/mnt' )
				// ) || '/' || DECODE( B.State, 'MSG_ERROR', A.File_Name, NVL(
				// B.Protocol_Message_Id, A.File_Name ) ) ) File_Name, B.Url, DECODE(
				// A.Claims_Count, 0, 'No Claims', B.State ) File_Status, A.Plan_Name,
				// B.ERROR_TEXT,direction FROM (SELECT GREATEST( Dataobject_Created,
				// Dataobject_Modified ) Created_Date, Claims_Count, File_Name, File_Status,
				// Plan_Name FROM Spiceprod_Soainfra.Beam_View_57 ORDER BY GREATEST(
				// Dataobject_Created, Dataobject_Modified ) DESC) A, ( SELECT B.Receiver_Name,
				// B.Payload_Name, B.State, TO_CHAR( E.Error_Text_Clob ) Error_Text_1, NVL(
				// E.ERROR_TEXT, W.ERROR_TEXT ) ERROR_TEXT, NVL( E.Error_Description,
				// W.Error_Description ) Error_Description, W.Protocol_Message_Id,
				// Url,w.direction FROM Spiceprod_Soainfra.B2b_Wire_Message W,
				// Spiceprod_Soainfra.B2b_Business_Message B,
				// Spiceprod_Soainfra.B2b_Ext_Business_Message E WHERE E.Id(+) =
				// B.Ext_Business_Message AND W.Id = B.Wire_Message AND B.Payload_Name IS NOT
				// NULL ORDER BY W.Created DESC ) B WHERE B.Payload_Name(+) = A.File_Name AND
				// A.Claims_Count IS NOT NULL AND ROWNUM < 100");
				"SELECT     DISTINCT 'Encounter' TYPE,                     TO_CHAR( Ew.Created_Time, 'YYYY-MM-DD HH24:MI:SS' ) Created_Date,                     Fp.Claimcount Claims_Count,                     Ew.PATH File_Name,                     '' Url,                     Ew.State File_Status,                     Ii.Partnername Plan_Name,                     '' ERROR_TEXT,                     'Outbound' Direction,                     Ii.Billingid,                     Ii.Pullcount,                     Ew.Flow_Id,                     REPLACE( Url, 'file://localhost/', '' ) PATH     FROM   Soareporting.Instance_Info Ii, Soareporting.Soa_Encounter_Wire Ew, Soareporting.Instance_Flow_Payload Fp    WHERE   Ii.Flowid = Ew.Flow_Id AND Ew.Flow_Id = Fp.Flowid AND Ew.PATH = Fp.Filename ORDER BY   2 DESC");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterBAMObject EncounterFileRec = new EncounterBAMObject();

			EncounterFileRec.setType(result.getString("type"));
			EncounterFileRec.setCreationDate(result.getString("CREATED_DATE"));
			EncounterFileRec.setClaimsCount(result.getString("claims_count"));
			EncounterFileRec.setFileName(result.getString("file_name"));
			EncounterFileRec.setStatus(result.getString("file_status"));
			EncounterFileRec.setPlancode(result.getString("plan_name"));
			EncounterFileRec.setErrorText(result.getString("ERROR_TEXT"));
			EncounterFileRec.setBillingid(result.getString("BILLINGID"));
			EncounterFileRec.setPullCount(result.getString("Pullcount"));
			EncounterFileRec.setFlowid(result.getString("flow_id"));
			EncounterFileRec.setPath(result.getString("PATH"));

			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();

		encounterBAMObjectInstances = list;
	}

	public List<EncounterValidationObject> getValidationsList() throws Exception {

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		params.get("logID");
		String logID = params.get("logID");

		List<EncounterValidationObject> list = new ArrayList<EncounterValidationObject>();
		List<EncounterValidationObject> list2 = new ArrayList<EncounterValidationObject>();
		String lastStopID = "X";
		String lastHealthPlan = "X";
		String lastKey = "X";
		String errorMessage = "";
		Boolean bCommitied = false;
		openSQLServerConnection();
		PreparedStatement ps = StatsCon.prepareStatement( // //
				"SELECT  [A2C].[dbo].[EMSCBilling837Interface].HealthPlancode,   [A2C].[dbo].[EMSCBilling837Interface].STOPID,   [A2CBilling].[dbo].[BillingClaimError].BillingClaimErrorMessage,   [A2C].[dbo].[EMSCBilling837Interface].HCPC,   [A2C].[dbo].[EMSCBilling837Interface].DiagnosisCode,   [A2C].[dbo].[EMSCBilling837Interface].BillingModifier,   [A2C].[dbo].[EMSCBilling837Interface].Mileage,   [A2C].[dbo].[EMSCBilling837Interface].TransportationProvider,   [A2C].[dbo].[EMSCBilling837Interface].ProviderPayDate,   [A2C].[dbo].[EMSCBilling837Interface].FirstName,   [A2C].[dbo].[EMSCBilling837Interface].LastName,   [A2C].[dbo].[EMSCBilling837Interface].POS,   [A2C].[dbo].[EMSCBilling837Interface].TripModifier,   [A2C].[dbo].[EMSCBilling837Interface].TripModifier2,   [A2C].[dbo].[EMSCBilling837Interface].BillAmount,   [A2C].[dbo].[EMSCBilling837Interface].PaidAmount,   [A2C].[dbo].[EMSCBilling837Interface].AppointmentDate,   [A2C].[dbo].[EMSCBilling837Interface].ServiceDateFrom,   [A2C].[dbo].[EMSCBilling837Interface].ServiceDateTo,   [A2C].[dbo].[EMSCBilling837Interface].MemberID,     [A2C].[dbo].[EMSCBilling837Interface].PUName,   [A2C].[dbo].[EMSCBilling837Interface].PUAddress1,   [A2C].[dbo].[EMSCBilling837Interface].PUAddress2,   [A2C].[dbo].[EMSCBilling837Interface].PUCity,   [A2C].[dbo].[EMSCBilling837Interface].PUCounty,   [A2C].[dbo].[EMSCBilling837Interface].PUState,   [A2C].[dbo].[EMSCBilling837Interface].PUZIPCode,       [A2C].[dbo].[EMSCBilling837Interface].DestinationName,   [A2C].[dbo].[EMSCBilling837Interface].DestinationAddress1 ,   [A2C].[dbo].[EMSCBilling837Interface].DestinationAddress2,   [A2C].[dbo].[EMSCBilling837Interface].DestinationCity,   [A2C].[dbo].[EMSCBilling837Interface].DestinationCounty,   [A2C].[dbo].[EMSCBilling837Interface].DestinationState,   [A2C].[dbo].[EMSCBilling837Interface].DestinationZIPCode,   [A2C].[dbo].[EMSCBilling837Interface].MemberAddress1,   [A2C].[dbo].[EMSCBilling837Interface].MemberAddress2,   [A2C].[dbo].[EMSCBilling837Interface].MemberCity,   [A2C].[dbo].[EMSCBilling837Interface].MemberCounty,   [A2C].[dbo].[EMSCBilling837Interface].MemberState,   [A2C].[dbo].[EMSCBilling837Interface].MemberZIPCode FROM [A2CBilling].[dbo].[BillingClaimError]      INNER JOIN [A2CBilling].[dbo].[BillingClaimLine]        ON [A2CBilling].[dbo].[BillingClaimError].BillingClaim_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaim_ID      INNER JOIN [A2CBilling].[dbo].[BillingClaimLineSource]        ON [A2CBilling].[dbo].[BillingClaimLineSource].BillingClaimLine_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaimLine_ID      INNER JOIN [A2C].[dbo].[EMSCBilling837Interface]        ON [A2C].[dbo].[EMSCBilling837Interface].BillingRepository_ID = [A2CBilling].[dbo].[BillingClaimLineSource].BillingRepository_ID,      [dbo].BillablePlanCode,      [dbo].BillableEntity WHERE ProcessingStatus_ID = '8' and  [dbo].BillablePlanCode.BillableEntity_ID = [dbo].BillableEntity.BillableEntity_ID AND [A2C].[dbo].[EMSCBilling837Interface].HealthPlancode = [dbo].BillablePlanCode.HealthPlanCode AND [dbo].BillableEntity.BillableEntity_ID = "
						+ logID + " ORDER BY 1, 2 ");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			bCommitied = false;
			EncounterValidationObject EncounterFileRec = new EncounterValidationObject();
			EncounterValidationObject ValidationDistinctRec = new EncounterValidationObject();
			EncounterFileRec.setHealthPlancode(result.getString("healthPlancode"));
			EncounterFileRec.setStopid(result.getString("stopid"));

			if (lastKey.equalsIgnoreCase(result.getString("healthPlancode") + result.getString("stopid"))) {
				errorMessage = errorMessage + "∙" + result.getString("billingClaimErrorMessage");

			} else {
				if (lastStopID != "X") {
					ValidationDistinctRec.setHealthPlancode(lastHealthPlan);
					ValidationDistinctRec.setStopid(lastStopID);
					ValidationDistinctRec.setBillingClaimErrorMessage(errorMessage);
					list2.add(ValidationDistinctRec);
				}

				lastHealthPlan = result.getString("healthPlancode");
				lastStopID = result.getString("stopid");
				errorMessage = result.getString("billingClaimErrorMessage");

			}
			lastKey = result.getString("healthPlancode") + result.getString("stopid");
			EncounterFileRec.sethCPC(result.getString("hCPC"));
			EncounterFileRec.setDiagnosisCode(result.getString("diagnosisCode"));
			EncounterFileRec.setBillingModifier(result.getString("billingModifier"));
			EncounterFileRec.setMileage(result.getString("mileage"));
			EncounterFileRec.setTransportationProvider(result.getString("transportationProvider"));
			EncounterFileRec.setProviderPayDate(result.getString("providerPayDate"));
			EncounterFileRec.setFirstName(result.getString("firstName"));
			EncounterFileRec.setLastName(result.getString("lastName"));
			EncounterFileRec.setpOS(result.getString("pOS"));
			EncounterFileRec.setTripModifier(result.getString("tripModifier"));
			EncounterFileRec.setTripModifier2(result.getString("tripModifier2"));
			EncounterFileRec.setBillAmount(result.getString("billAmount"));
			EncounterFileRec.setPaidAmount(result.getString("paidAmount"));
			EncounterFileRec.setAppointmentDate(result.getString("appointmentDate"));
			EncounterFileRec.setServiceDateFrom(result.getString("serviceDateFrom"));
			EncounterFileRec.setServiceDateTo(result.getString("serviceDateTo"));
			EncounterFileRec.setMemberID(result.getString("memberID"));
			EncounterFileRec.setpUName(result.getString("pUName"));
			EncounterFileRec.setpUAddress1(result.getString("pUAddress1"));
			EncounterFileRec.setpUAddress2(result.getString("pUAddress2"));
			EncounterFileRec.setpUCity(result.getString("pUCity"));
			EncounterFileRec.setpUCounty(result.getString("pUCounty"));
			EncounterFileRec.setpUState(result.getString("pUState"));
			EncounterFileRec.setpUZIPCode(result.getString("pUZIPCode"));
			EncounterFileRec.setDestinationName(result.getString("destinationName"));
			EncounterFileRec.setDestinationAddress1(result.getString("destinationAddress1"));
			EncounterFileRec.setDestinationAddress2(result.getString("destinationAddress2"));
			EncounterFileRec.setDestinationCity(result.getString("destinationCity"));
			EncounterFileRec.setDestinationCounty(result.getString("destinationCounty"));
			EncounterFileRec.setDestinationState(result.getString("destinationState"));
			EncounterFileRec.setDestinationZIPCode(result.getString("destinationZIPCode"));
			EncounterFileRec.setMemberAddress1(result.getString("memberAddress1"));
			EncounterFileRec.setMemberAddress2(result.getString("memberAddress2"));
			EncounterFileRec.setMemberCity(result.getString("memberCity"));
			EncounterFileRec.setMemberCounty(result.getString("memberCounty"));
			EncounterFileRec.setMemberState(result.getString("memberState"));
			EncounterFileRec.setMemberZIPCode(result.getString("memberZIPCode"));
			EncounterFileRec.setBillingClaimErrorMessage(result.getString("billingClaimErrorMessage"));

			list.add(EncounterFileRec);
		}
		EncounterValidationObject ValidationDistinctRec1 = new EncounterValidationObject();
		ValidationDistinctRec1.setHealthPlancode(lastHealthPlan);
		ValidationDistinctRec1.setStopid(lastStopID);
		ValidationDistinctRec1.setBillingClaimErrorMessage(errorMessage);
		list2.add(ValidationDistinctRec1);
		result.close();
		ps.close();
		closeSQLServerConnection();
		encounterDistinctValidationsSQLServer = list2;
		return list;

	}

	public void getWSDLList() throws Exception {

		List<EncounterWSDLs> list = new ArrayList<EncounterWSDLs>();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select Soareporting.F_Get_Last_Update( Encounter_Entityid ) Last_Updated,id,encounter_name, Substr(substr(encounter_wsdl,instr(encounter_wsdl,'default/') + 8),1,instr(substr(encounter_wsdl,instr(encounter_wsdl,'default/') + 8),'_') - 1 ) wsdlDisplay, encounter_wsdl,Encounter_Entityid, Comments from soareporting.Soa_Encounter_Wsdls");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWSDLs EncounterFileRec = new EncounterWSDLs();
			EncounterFileRec.setId(result.getString("id"));
			EncounterFileRec.setLastUpdated(result.getString("Last_Updated"));
			EncounterFileRec.setWsdl(result.getString("encounter_wsdl"));
			EncounterFileRec.setBillingid(result.getString("Encounter_Entityid"));
			EncounterFileRec.setComments(result.getString("Comments"));
			EncounterFileRec.setValidations(getValidationsLookup(result.getString("Encounter_Entityid")));
			EncounterFileRec.setWsdlDisplay(result.getString("wsdlDisplay"));
			EncounterFileRec.setReadyitems(getBillingLookup(result.getString("Encounter_Entityid")));
			EncounterFileRec.setName(getTPName(result.getString("Encounter_Entityid")));
			EncounterFileRec.setUsecases(getUseCasesLookup(result.getString("id")));
			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();

		encounterExecuteList = list;
	}

	public static void closeSoaInfraConnection() throws Exception {
		SOACon.close();
		// System.out.println("Close SOA Connection");
	}

	public void flagforretrieval() throws Exception {
		if (SOACon.isClosed()) {
			// System.out.println("Check connection Closed");
		}
		{
			// System.out.println("Check connection Open");
			closeSoaInfraConnection();
		}
		openSoaInfraConnection();
		getInstanceList();
		getBAMLogs();
		getWSDLList();
		closeSoaInfraConnection();
		// System.out.println("Out flagforretrieval");
	}

	@PostConstruct
	public void reset() throws Exception {
		try {
			userid = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			openSoaInfraConnection();
			getBAMLogs();
			getInstanceList();
			getWSDLList();

			closeSoaInfraConnection();
		} catch (Exception e) {
			e.printStackTrace();
			// or handle more gracefully
		}

	}

	public static void callEncounterUpdateProc() throws IOException {
		callEncounterUpdateFileDataProc();
		callEncounterUpdateDataProc();

	}

	public static void callEncounterUpdateFileDataProc() throws IOException {

		String responseString = "";
		String outputString = "";
		// String wsEndPoint =
		// "https://a2c-spice.evhc.net/soa-infra/services/default/SyncSOAPreportingEncounterFileData/bpelprocesssyncencounterfiledata_client_ep?WSDL";
		String wsEndPoint = "https://a2c-spice-test.evhc.net/soa-infra/services/default/SyncSOAPreportingEncounterFileData/bpelprocesssyncencounterfiledata_client_ep?WSDL";
		URL url = new URL(wsEndPoint);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "    	<soap:Body>\r\n"
				+ "        		<ns1:process xmlns:ns1=\"http://xmlns.oracle.com/CommonArtifacts/SyncSOAPreportingEncounterFileData/BPELProcessSyncEncounterFileData\">\r\n"
				+ "            			<ns1:input></ns1:input>\r\n" + "        </ns1:process>\r\n"
				+ "    </soap:Body>\r\n" + "</soap:Envelope>\r\n" + "";
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();

		String SOAPAction = "process";
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		OutputStream out = httpConn.getOutputStream();
		// Write the content of the request to the outputstream of the HTTP
		// Connection.
		out.write(b);
		out.close();
		// Ready with sending the request.
		// Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
		BufferedReader in = new BufferedReader(isr);
		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}

	}

	public static void callEncounterUpdateDataProc() throws IOException {

		String responseString = "";
		String outputString = "";
		// String wsEndPoint =
		// "https://a2c-spice.evhc.net/soa-infra/services/default/SyncSOAReportingEncountersData/bpelprocesssyncencounterdata_client_ep?WSDL";
		String wsEndPoint = "https://a2c-spice-test.evhc.net/soa-infra/services/default/SyncSOAReportingEncountersData/bpelprocesssyncencounterdata_client_ep?WSDL";
		URL url = new URL(wsEndPoint);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "    	<soap:Body>\r\n"
				+ "        		<ns1:process xmlns:ns1=\"http://xmlns.oracle.com/CommonArtifacts/SyncSOAReportingEncountersData/BPELProcessSyncEncounterData\">\r\n"
				+ "            			<ns1:input></ns1:input>\r\n" + "        </ns1:process>\r\n"
				+ "    </soap:Body>\r\n" + "</soap:Envelope>\r\n" + "";
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();

		String SOAPAction = "process";
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		OutputStream out = httpConn.getOutputStream();
		// Write the content of the request to the outputstream of the HTTP
		// Connection.
		out.write(b);
		out.close();
		// Ready with sending the request.
		// Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
		BufferedReader in = new BufferedReader(isr);
		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}

	}

	public void reprocessTransaction() throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String wsdl = params.get("wsdl");
		String billingID = params.get("billingID");
		String kickoffID = params.get("kickoffID");
		String processname = params.get("processname");
		String usecase = params.get("usecase");

		System.out.println("wsdl = " + wsdl);
		System.out.println("billingID = " + billingID);
		System.out.println("kickoffID = " + kickoffID);
		System.out.println("processname = " + processname);
		System.out.println("usecase = " + usecase);
		try {
			callEncounterProc(wsdl, processname, billingID, kickoffID, usecase);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void callEncounterProc(String wsdl, String processname, String billingID, String kickoffID, String usecase)
			throws Exception {

		String responseString = "";
		String outputString = "";
		String wsEndPoint = wsdl;
		String runningprocesses = "0";
		String tempFlowId = UUID.randomUUID().toString().replace("-", "");
		openSoaInfraConnection();
		openSQLServerConnection();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select count(1) counter from soareporting.soa_encounter_instances where state = 'Running' and replace(replace(replace(replace(replace(replace(replace(replace(composite,'_EncounterProcessing',''),'_EncounterService',''),'_EncounterProcess6400',''),'_EncounterProcess',''),'SyncSOAPreportingEncounterFileDataByEntity','Update Detail Data ' || initcap(title)),'SyncSOAReportingEncountersDataByEntity','Update Summary Data ' || initcap(title)),'SyncSOAReportingEncountersData','Update All Detail Data'),'SyncSOAPreportingEncounterFileData','Update All Summary Data') = '"
						+ processname + "'");

		ResultSet result = ps.executeQuery();

		while (result.next()) {

			runningprocesses = result.getString("counter");

		}
		result.close();
		ps.close();

		if (runningprocesses.equalsIgnoreCase("0")) {
			if (usecase.equalsIgnoreCase("X")) {

				PreparedStatement ps1 = StatsCon.prepareStatement("SELECT [EMSCBilling837Interface_ID]\r\n"
						+ "      ,bi.[HealthPlanCode]\r\n" + "      ,[StopID]\r\n" + "      ,[AppointmentDate]\r\n"
						+ "      ,[Mileage]\r\n" + "      ,[ActualPrice]\r\n" + "      ,[MemberID]\r\n"
						+ "      ,[LastName]\r\n" + "      ,[FirstName]\r\n" + "      ,[MiddleInitial]\r\n"
						+ "      ,[DOB]\r\n" + "      ,[EncryptedSSN]\r\n" + "      ,[GroupID]\r\n" + "      ,[Age]\r\n"
						+ "      ,[MemberAddress1]\r\n" + "      ,[MemberAddress2]\r\n" + "      ,[MemberCity]\r\n"
						+ "      ,[MemberCounty]\r\n" + "      ,[MemberState]\r\n" + "      ,[MemberZIPCode]\r\n"
						+ "      ,[MemberPhoneNumber]\r\n" + "      ,[PUAddress1]\r\n" + "      ,[PUAddress2]\r\n"
						+ "      ,[PUCity]\r\n" + "      ,[PUCounty]\r\n" + "      ,[PUState]\r\n"
						+ "      ,[PUZIPCode]\r\n" + "      ,[DestinationAddress1]\r\n"
						+ "      ,[DestinationAddress2]\r\n" + "      ,[DestinationCity]\r\n"
						+ "      ,[DestinationCounty]\r\n" + "      ,[DestinationState]\r\n"
						+ "      ,[DestinationZIPCode]\r\n" + "      ,[FinalLOS]\r\n" + "      ,[HCPC]\r\n"
						+ "      ,[TransportationProvider]\r\n" + "      ,[APVenderID]\r\n" + "      ,[MRBTypeID]\r\n"
						+ "      ,[MRBTypeName]\r\n" + "      ,[Status]\r\n" + "      ,[FinalStatusDate]\r\n"
						+ "      ,[ConfirmationNumber]\r\n" + "      ,[DiagnosisCode]\r\n" + "      ,[TripModifier]\r\n"
						+ "      ,[BillingModifier]\r\n" + "      ,[CostPlusAmount]\r\n" + "      ,[BillAmount]\r\n"
						+ "      ,[POS]\r\n" + "      ,[NetStop_ID]\r\n" + "      ,[NetMember_ID]\r\n"
						+ "      ,[TransportationProvider_ID]\r\n" + "      ,[AddressMember_ID]\r\n"
						+ "      ,[AddressPickup_ID]\r\n" + "      ,[AddressDestination_ID]\r\n"
						+ "      ,[ActualLegCostAmount]\r\n" + "      ,[ReportedLegCostAmount]\r\n"
						+ "      ,[ProviderPayableAmount]\r\n" + "      ,[ProviderReceivedAmount]\r\n"
						+ "      ,[ProviderPayDate]\r\n" + "      ,[EMSCPayableAmount]\r\n"
						+ "      ,[EMSCReceivedAmount]\r\n" + "      ,[CreatedByID]\r\n"
						+ "      ,bi.[ModifiedByID]\r\n" + "      ,[CreateDate]\r\n" + "      ,bi.[ModifiedDate]\r\n"
						+ "      ,[ExternalFileGeneratedDate]\r\n" + "      ,[IsActive]\r\n" + "      ,[Gender]\r\n"
						+ "      ,[IsExtracted]\r\n" + "      ,[ExtractedDate]\r\n" + "      ,[TripNumber]\r\n"
						+ "      ,[ProgramType]\r\n" + "      ,[MemberExtendedCode1]\r\n"
						+ "      ,[MemberExtendedCode2]\r\n" + "      ,[MemberExtendedCode3]\r\n"
						+ "      ,[MemberExtendedCode4]\r\n" + "      ,[MemberExtendedCode5]\r\n"
						+ "      ,[MemberExtendedCode6]\r\n" + "      ,[MemberExtendedCode7]\r\n"
						+ "      ,[TransportationProviderAddress1]\r\n" + "      ,[TransportationProviderAddress2]\r\n"
						+ "      ,[TransportationProviderCity]\r\n" + "      ,[TransportationProviderState]\r\n"
						+ "      ,[TransportationProviderZip]\r\n" + "      ,[PUName]\r\n"
						+ "      ,[DestinationName]\r\n" + "      ,[CaseNumber]\r\n" + "      ,[ServiceDateTo]\r\n"
						+ "      ,[ServiceDateFrom]\r\n" + "      ,[TripModifier2]\r\n" + "      ,[ServiceUnits]\r\n"
						+ "      ,[UnitPrice]\r\n" + "      ,[CertificationCode]\r\n" + "      ,[AcknowledgeDate]\r\n"
						+ "      ,[CompletedDate]\r\n" + "      ,[TransportationProviderFedID]\r\n"
						+ "      ,[SentToOracle]\r\n" + "      ,[OracleSentDate]\r\n"
						+ "      ,[PolicyHolderNetMemberID]\r\n" + "      ,[PolicyHolderMemberID]\r\n"
						+ "      ,[PolicyHolderFirstName]\r\n" + "      ,[PolicyHolderMiddleInitial]\r\n"
						+ "      ,[PolicyHolderLastName]\r\n" + "      ,[PolicyHolderAddress1]\r\n"
						+ "      ,[PolicyHolderAddress2]\r\n" + "      ,[PolicyHolderCity]\r\n"
						+ "      ,[PolicyHolderState]\r\n" + "      ,[PolicyHolderZIPCode]\r\n"
						+ "      ,[PolicyHolderDOB]\r\n" + "      ,[PolicyHolderGender]\r\n"
						+ "      ,[PolicyHolderGroupID]\r\n" + "      ,[PolicyHolderGroupName]\r\n"
						+ "      ,[PolicyHolderPolicyNo]\r\n" + "      ,[RelationshipCode]\r\n"
						+ "      ,[TransportationProviderNPI]\r\n" + "      ,[TransportationProviderMedicaidID]\r\n"
						+ "      ,[TripCreateDate]\r\n" + "      ,[AddlPassengerCount]\r\n"
						+ "      ,[SchedulePickupTime]\r\n" + "      ,[AppointmentTime]\r\n"
						+ "      ,[MemberMedicaidID]\r\n" + "      ,[MemberMedicareID]\r\n"
						+ "      ,[MemberAlternativeID]\r\n" + "      ,[PickupTime]\r\n"
						+ "      ,[DestinationTime]\r\n" + "      ,[TripOutcome]\r\n"
						+ "      ,[TransportationProviderZip4]\r\n" + "      ,[ClientAuthCode]\r\n"
						+ "      ,[Case_ExpenseLineItemDetails_ID]\r\n" + "      ,[DriverName]\r\n"
						+ "      ,[DriverLicenseNumber]\r\n" + "      ,[DriverLicenseState]\r\n"
						+ "      ,[A2CDriverID]\r\n" + "      ,[ProviderNPI]\r\n" + "      ,[ProviderTaxID]\r\n"
						+ "      ,[ProviderTaxonomyCode]\r\n" + "      ,[ProviderStateProviderID]\r\n"
						+ "      ,[EquipmentAndSpecialNeeds]\r\n" + "      ,[VehicleIDNumber]\r\n"
						+ "      ,[PerformingProviderName]\r\n" + "      ,[PaymentStatus_ID]\r\n"
						+ "      ,[PlanPaidDate]\r\n" + "      ,[CheckNumber]\r\n" + "      ,[ReasonCode]\r\n"
						+ "      ,[PaidAmount]\r\n" + "      ,[ReasonDescription]\r\n" + "      ,[ReSubmissionDate]\r\n"
						+ "      ,[UpdateStatus]\r\n" + "      ,[A2CProcessingStatus]\r\n"
						+ "      ,[PlanStatusCode]\r\n" + "      ,[ProcessedDate]\r\n" + "      ,[SystemCreateDate]\r\n"
						+ "      ,[ClaimResubmissionPeriod]\r\n" + "      ,[ClientClaimNumber]\r\n"
						+ "      ,[AltClientClaimNumber]\r\n" + "      ,[ProcessingStatus_ID]\r\n"
						+ "      ,[A2CClaimNumber]\r\n" + "      ,[ClaimTypeID]\r\n" + "      ,[EncryptedDriverSSN]\r\n"
						+ "      ,[ClearedDate]\r\n" + "      ,[NetStopAttribute_ARLineItem_ID]\r\n"
						+ "      ,[ExtractedDate2]\r\n" + "      ,[ExternalFileGeneratedDate2]\r\n"
						+ "      ,[AcknowledgeDate2]\r\n" + "      ,[CompletedDate2]\r\n"
						+ "      ,[ProcessingStatus_ID2]\r\n" + "      ,[ProcessedDate2]\r\n"
						+ "      ,[PlanStatusCode2]\r\n" + "      ,[ReasonCode2]\r\n"
						+ "      ,[ReasonDescription2]\r\n" + "      ,[EncryptedTPFedID]\r\n"
						+ "      ,[ChangedbyUserName]\r\n" + "      ,[ChangeReason]\r\n"
						+ "      ,[BillingRepository_ID]\r\n"
						+ "  FROM [A2C].[dbo].[EMSCBilling837Interface] bi, [A2CBilling].[dbo].[BillableEntity] be,[A2CBilling].[dbo].[BillablePlanCode] bpc  where [IsActive] = 1 and \r\n"
						+ "  bi.HealthPlanCode = bpc.HealthPlanCode and bpc.BillableEntity_ID = be.BillableEntity_ID and bi.ProcessingStatus_ID = 6 and bpc.BillableEntity_ID = "
						+ billingID

				);

				ResultSet result1 = ps1.executeQuery();
				System.out.println("In Save");
				while (result1.next()) {

					PreparedStatement statement = SOACon.prepareStatement(
							"INSERT INTO BILLING837INTERFACE (EMSCBILLING837INTERFACE_ID,HEALTHPLANCODE,STOPID,APPOINTMENTDATE,MILEAGE,ACTUALPRICE,MEMBERID,LASTNAME,FIRSTNAME,MIDDLEINITIAL"
									+ ",DOB,ENCRYPTEDSSN,GROUPID,AGE,MEMBERADDRESS1,MEMBERADDRESS2,MEMBERCITY,MEMBERCOUNTY,MEMBERSTATE,MEMBERZIPCODE,MEMBERPHONENUMBER,PUADDRESS1,PUADDRESS2,PUCITY,PUCOUNTY,PUSTATE,PUZIPCODE,DESTINATIONADDRESS1,DESTINATIONADDRESS2,DESTINATIONCITY,DESTINATIONCOUNTY,DESTINATIONSTATE,DESTINATIONZIPCODE,FINALLOS,HCPC,TRANSPORTATIONPROVIDER,APVENDERID,MRBTYPEID,MRBTYPENAME,"
									+ "STATUS,FinalStatusDate,ConfirmationNumber,DiagnosisCode,TripModifier,BillingModifier,CostPlusAmount,BillAmount,POS,NetStop_ID,NetMember_ID,TransportationProvider_ID,AddressMember_ID,AddressPickup_ID,AddressDestination_ID,ActualLegCostAmount,ReportedLegCostAmount,ProviderPayableAmount,ProviderReceivedAmount,ProviderPayDate,EMSCPayableAmount,EMSCReceivedAmount,CreatedByID,ModifiedByID,CreateDate"
									+ ",ModifiedDate,ExternalFileGeneratedDate,IsActive,Gender,IsExtracted,ExtractedDate,TripNumber,ProgramType,MemberExtendedCode1,MemberExtendedCode2,MemberExtendedCode3,MemberExtendedCode4,MemberExtendedCode5,MemberExtendedCode6,MemberExtendedCode7,TransportationProviderAddress1,TransportationProviderAddress2,TransportationProviderCity,TransportationProviderState,TransportationProviderZip,PUName,"
									+ "DestinationName,CaseNumber,ServiceDateTo,ServiceDateFrom,TripModifier2,ServiceUnits,UnitPrice,CertificationCode,AcknowledgeDate,CompletedDate,TransportationProviderFedID,SentToOracle,OracleSentDate,PolicyHolderNetMemberID,POLICYHOLDERMEMBERID"
									+ ",POLICYHOLDERFIRSTNAME,POLICYHOLDERMIDDLEINITIAL,POLICYHOLDERLASTNAME,POLICYHOLDERADDRESS1,POLICYHOLDERADDRESS2,POLICYHOLDERCITY,POLICYHOLDERSTATE,POLICYHOLDERZIPCODE,POLICYHOLDERDOB,POLICYHOLDERGENDER,POLICYHOLDERGROUPID,POLICYHOLDERGROUPNAME,POLICYHOLDERPOLICYNO,RELATIONSHIPCODE,TRANSPORTATIONPROVIDERNPI,TRANSPORTATIONPROVIDERMEDICAID,TRIPCREATEDATE,ADDLPASSENGERCOUNT,SCHEDULEPICKUPTIME,APPOINTMENTTIME "
									+ ",MEMBERMEDICAIDID,MEMBERMEDICAREID,MEMBERALTERNATIVEID,PICKUPTIME,DESTINATIONTIME,TRIPOUTCOME,TRANSPORTATIONPROVIDERZIP4,CLIENTAUTHCODE,CASE_EXPENSELINEITEMDETAILS_ID,DRIVERNAME,DRIVERLICENSENUMBER,DRIVERLICENSESTATE,A2CDRIVERID,PROVIDERNPI,PROVIDERTAXID,PROVIDERTAXONOMYCODE,PROVIDERSTATEPROVIDERID,EQUIPMENTANDSPECIALNEEDS,VEHICLEIDNUMBER,PERFORMINGPROVIDERNAME,PAYMENTSTATUS_ID,PLANPAIDDATE,CHECKNUMBER,REASONCODE,PAIDAMOUNT,REASONDESCRIPTION,RESUBMISSIONDATE,UPDATESTATUS,A2CPROCESSINGSTATUS,PLANSTATUSCODE,PROCESSEDDATE,SYSTEMCREATEDATE,CLAIMRESUBMISSIONPERIOD,CLIENTCLAIMNUMBER,ALTCLIENTCLAIMNUMBER,PROCESSINGSTATUS_ID,A2CCLAIMNUMBER,CLAIMTYPEID,ENCRYPTEDDRIVERSSN,CLEAREDDATE,NETSTOPATTRIBUTE_ARLINEITEM_ID,EXTRACTEDDATE2,EXTERNALFILEGENERATEDDATE2,ACKNOWLEDGEDATE2,COMPLETEDDATE2,PROCESSINGSTATUS_ID2,PROCESSEDDATE2,PLANSTATUSCODE2,REASONCODE2,REASONDESCRIPTION2,ENCRYPTEDTPFEDID,CHANGEDBYUSERNAME,CHANGEREASON,BillingRepository_ID,FLOWID"
									+ ") VALUES (?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
									+ "          ?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,?)");

					statement.setString(1, result1.getString("EMSCBilling837Interface_ID"));
					statement.setString(2, result1.getString("HealthPlanCode"));
					statement.setString(3, result1.getString("StopID"));
					statement.setString(4, result1.getString("AppointmentDate"));
					statement.setString(5, result1.getString("Mileage"));
					statement.setString(6, result1.getString("ActualPrice"));
					statement.setString(7, result1.getString("MemberID"));
					statement.setString(8, result1.getString("LastName"));
					statement.setString(9, result1.getString("FirstName"));
					statement.setString(10, result1.getString("MiddleInitial"));

					statement.setString(11, result1.getString("DOB"));
					statement.setString(12, result1.getString("EncryptedSSN"));
					statement.setString(13, result1.getString("GroupID"));
					statement.setString(14, result1.getString("Age"));
					statement.setString(15, result1.getString("MemberAddress1"));
					statement.setString(16, result1.getString("MemberAddress2"));
					statement.setString(17, result1.getString("MemberCity"));
					statement.setString(18, result1.getString("MemberCounty"));
					statement.setString(19, result1.getString("MemberState"));
					statement.setString(20, result1.getString("MemberZIPCode"));
					statement.setString(21, result1.getString("MemberPhoneNumber"));
					statement.setString(22, result1.getString("PUAddress1"));
					statement.setString(23, result1.getString("PUAddress2"));
					statement.setString(24, result1.getString("PUCity"));
					statement.setString(25, result1.getString("PUCounty"));
					statement.setString(26, result1.getString("PUState"));
					statement.setString(27, result1.getString("PUZIPCode"));
					statement.setString(28, result1.getString("DestinationAddress1"));
					statement.setString(29, result1.getString("DestinationAddress2"));
					statement.setString(30, result1.getString("DestinationCity"));
					statement.setString(31, result1.getString("DestinationCounty"));
					statement.setString(32, result1.getString("DestinationState"));
					statement.setString(33, result1.getString("DestinationZIPCode"));
					statement.setString(34, result1.getString("FinalLOS"));
					statement.setString(35, result1.getString("HCPC"));
					statement.setString(36, result1.getString("TransportationProvider"));
					statement.setString(37, result1.getString("APVenderID"));
					statement.setString(38, result1.getString("MRBTypeID"));
					statement.setString(39, result1.getString("MRBTypeName"));
					statement.setString(40, result1.getString("Status"));

					statement.setString(41, result1.getString("FINALSTATUSDATE"));
					statement.setString(42, result1.getString("CONFIRMATIONNUMBER"));
					statement.setString(43, result1.getString("DIAGNOSISCODE"));
					statement.setString(44, result1.getString("TRIPMODIFIER"));
					statement.setString(45, result1.getString("BILLINGMODIFIER"));
					statement.setString(46, result1.getString("COSTPLUSAMOUNT"));
					statement.setString(47, result1.getString("BILLAMOUNT"));
					statement.setString(48, result1.getString("POS"));
					statement.setString(49, result1.getString("NETSTOP_ID"));
					statement.setString(50, result1.getString("NETMEMBER_ID"));
					statement.setString(51, result1.getString("TRANSPORTATIONPROVIDER_ID"));
					statement.setString(52, result1.getString("ADDRESSMEMBER_ID"));
					statement.setString(53, result1.getString("ADDRESSPICKUP_ID"));
					statement.setString(54, result1.getString("ADDRESSDESTINATION_ID"));
					statement.setString(55, result1.getString("ACTUALLEGCOSTAMOUNT"));
					statement.setString(56, result1.getString("REPORTEDLEGCOSTAMOUNT"));
					statement.setString(57, result1.getString("PROVIDERPAYABLEAMOUNT"));
					statement.setString(58, result1.getString("PROVIDERRECEIVEDAMOUNT"));
					statement.setString(59, result1.getString("PROVIDERPAYDATE"));

					statement.setString(60, result1.getString("EMSCPAYABLEAMOUNT"));
					statement.setString(61, result1.getString("EMSCRECEIVEDAMOUNT"));
					statement.setString(62, result1.getString("CREATEDBYID"));
					statement.setString(63, result1.getString("MODIFIEDBYID"));
					statement.setString(64, result1.getString("CREATEDATE"));
					statement.setString(65, result1.getString("MODIFIEDDATE"));
					statement.setString(66, result1.getString("EXTERNALFILEGENERATEDDATE"));
					statement.setString(67, result1.getString("ISACTIVE"));
					statement.setString(68, result1.getString("GENDER"));
					statement.setString(69, result1.getString("ISEXTRACTED"));
					statement.setString(70, result1.getString("EXTRACTEDDATE"));
					statement.setString(71, result1.getString("TRIPNUMBER"));

					statement.setString(72, result1.getString("PROGRAMTYPE"));
					statement.setString(73, result1.getString("MEMBEREXTENDEDCODE1"));
					statement.setString(74, result1.getString("MEMBEREXTENDEDCODE2"));
					statement.setString(75, result1.getString("MEMBEREXTENDEDCODE3"));
					statement.setString(76, result1.getString("MEMBEREXTENDEDCODE4"));
					statement.setString(77, result1.getString("MEMBEREXTENDEDCODE5"));
					statement.setString(78, result1.getString("MEMBEREXTENDEDCODE6"));
					statement.setString(79, result1.getString("MEMBEREXTENDEDCODE7"));
					statement.setString(80, result1.getString("TRANSPORTATIONPROVIDERADDRESS1"));
					statement.setString(81, result1.getString("TRANSPORTATIONPROVIDERADDRESS2"));
					statement.setString(82, result1.getString("TRANSPORTATIONPROVIDERCITY"));
					statement.setString(83, result1.getString("TRANSPORTATIONPROVIDERSTATE"));
					statement.setString(84, result1.getString("TRANSPORTATIONPROVIDERZIP"));
					statement.setString(85, result1.getString("PUNAME"));
					statement.setString(86, result1.getString("DESTINATIONNAME"));
					statement.setString(87, result1.getString("CASENUMBER"));
					statement.setString(88, result1.getString("SERVICEDATETO"));
					statement.setString(89, result1.getString("SERVICEDATEFROM"));
					statement.setString(90, result1.getString("TRIPMODIFIER2"));
					statement.setString(91, result1.getString("SERVICEUNITS"));
					statement.setString(92, result1.getString("UNITPRICE"));
					statement.setString(93, result1.getString("CERTIFICATIONCODE"));
					statement.setString(94, result1.getString("ACKNOWLEDGEDATE"));
					statement.setString(95, result1.getString("COMPLETEDDATE"));
					statement.setString(96, result1.getString("TRANSPORTATIONPROVIDERFEDID"));
					statement.setString(97, result1.getString("SENTTOORACLE"));
					statement.setString(98, result1.getString("ORACLESENTDATE"));
					statement.setString(99, result1.getString("POLICYHOLDERNETMEMBERID"));
					statement.setString(100, result1.getString("POLICYHOLDERMEMBERID"));

					statement.setString(101, result1.getString("PolicyHolderFirstName"));
					statement.setString(102, result1.getString("PolicyHolderMiddleInitial"));
					statement.setString(103, result1.getString("PolicyHolderLastName"));
					statement.setString(104, result1.getString("PolicyHolderAddress1"));
					statement.setString(105, result1.getString("PolicyHolderAddress2"));
					statement.setString(106, result1.getString("PolicyHolderCity"));
					statement.setString(107, result1.getString("PolicyHolderState"));
					statement.setString(108, result1.getString("PolicyHolderZIPCode"));
					statement.setString(109, result1.getString("PolicyHolderDOB"));
					statement.setString(110, result1.getString("PolicyHolderGender"));

					statement.setString(111, result1.getString("PolicyHolderGroupID"));
					statement.setString(112, result1.getString("PolicyHolderGroupName"));
					statement.setString(113, result1.getString("PolicyHolderPolicyNo"));
					statement.setString(114, result1.getString("RelationshipCode"));
					statement.setString(115, result1.getString("TransportationProviderNPI"));
					statement.setString(116, result1.getString("TransportationProviderMedicaidID"));
					statement.setString(117, result1.getString("TripCreateDate"));
					statement.setString(118, result1.getString("AddlPassengerCount"));
					statement.setString(119, result1.getString("SchedulePickupTime"));
					statement.setString(120, result1.getString("AppointmentTime"));
					statement.setString(121, result1.getString("MemberMedicaidID"));
					statement.setString(122, result1.getString("MemberMedicareID"));
					statement.setString(123, result1.getString("MemberAlternativeID"));
					statement.setString(124, result1.getString("PickupTime"));
					statement.setString(125, result1.getString("DestinationTime"));
					statement.setString(126, result1.getString("TripOutcome"));
					statement.setString(127, result1.getString("TransportationProviderZip4"));
					statement.setString(128, result1.getString("ClientAuthCode"));
					statement.setString(129, result1.getString("Case_ExpenseLineItemDetails_ID"));
					statement.setString(130, result1.getString("DriverName"));
					statement.setString(131, result1.getString("DriverLicenseNumber"));
					statement.setString(132, result1.getString("DriverLicenseState"));
					statement.setString(133, result1.getString("A2CDriverID"));
					statement.setString(134, result1.getString("ProviderNPI"));
					statement.setString(135, result1.getString("ProviderTaxID"));
					statement.setString(136, result1.getString("ProviderTaxonomyCode"));
					statement.setString(137, result1.getString("ProviderStateProviderID"));
					statement.setString(138, result1.getString("EquipmentAndSpecialNeeds"));
					statement.setString(139, result1.getString("VehicleIDNumber"));
					statement.setString(140, result1.getString("PerformingProviderName"));

					statement.setString(141, result1.getString("PaymentStatus_ID"));
					statement.setString(142, result1.getString("PlanPaidDate"));
					statement.setString(143, result1.getString("CheckNumber"));
					statement.setString(144, result1.getString("ReasonCode"));
					statement.setString(145, result1.getString("PaidAmount"));
					statement.setString(146, result1.getString("ReasonDescription"));
					statement.setString(147, result1.getString("ReSubmissionDate"));
					statement.setString(148, result1.getString("UpdateStatus"));
					statement.setString(149, result1.getString("A2CProcessingStatus"));
					statement.setString(150, result1.getString("PlanStatusCode"));
					statement.setString(151, result1.getString("ProcessedDate"));
					statement.setString(152, result1.getString("SystemCreateDate"));
					statement.setString(153, result1.getString("ClaimResubmissionPeriod"));
					statement.setString(154, result1.getString("ClientClaimNumber"));
					statement.setString(155, result1.getString("AltClientClaimNumber"));
					statement.setString(156, result1.getString("ProcessingStatus_ID"));
					statement.setString(157, result1.getString("A2CClaimNumber"));
					statement.setString(158, result1.getString("ClaimTypeID"));
					statement.setString(159, result1.getString("EncryptedDriverSSN"));

					statement.setString(160, result1.getString("ClearedDate"));
					statement.setString(161, result1.getString("NetStopAttribute_ARLineItem_ID"));
					statement.setString(162, result1.getString("ExtractedDate2"));
					statement.setString(163, result1.getString("ExternalFileGeneratedDate2"));
					statement.setString(164, result1.getString("AcknowledgeDate2"));
					statement.setString(165, result1.getString("CompletedDate2"));
					statement.setString(166, result1.getString("ProcessingStatus_ID2"));
					statement.setString(167, result1.getString("ProcessedDate2"));
					statement.setString(168, result1.getString("PlanStatusCode2"));
					statement.setString(169, result1.getString("ReasonCode2"));
					statement.setString(170, result1.getString("ReasonDescription2"));
					statement.setString(171, result1.getString("EncryptedTPFedID"));

					statement.setString(172, result1.getString("ChangedbyUserName"));
					statement.setString(173, result1.getString("ChangeReason"));
					statement.setString(174, result1.getString("BillingRepository_ID"));
					statement.setString(175, tempFlowId);
					statement.executeUpdate();

				}
				SOACon.commit();
			} else {

				PreparedStatement statementUpdate = SOACon.prepareStatement(
						"INSERT INTO   Soareporting.Billing837interface( Emscbilling837interface_Id, Healthplancode, Stopid, Appointmentdate, Mileage, Actualprice,                                                 Memberid, Lastname, Firstname, Middleinitial, Dob, Encryptedssn, Groupid, Age, Memberaddress1,                                                 Memberaddress2, Membercity, Membercounty, Memberstate, Memberzipcode, Memberphonenumber,                                                 Puaddress1, Puaddress2, Pucity, Pucounty, Pustate, Puzipcode, Destinationaddress1,                                                 Destinationaddress2, Destinationcity, Destinationcounty, Destinationstate, Destinationzipcode,                                                 Finallos, Hcpc, Transportationprovider, Apvenderid, Mrbtypeid, Mrbtypename, Status,                                                 Finalstatusdate, Confirmationnumber, Diagnosiscode, Tripmodifier, Billingmodifier,                                                 Costplusamount, Billamount, Pos, Netstop_Id, Netmember_Id, Transportationprovider_Id,                                                 Addressmember_Id, Addresspickup_Id, Addressdestination_Id, Actuallegcostamount,                                                 Reportedlegcostamount, Providerpayableamount, Providerreceivedamount, Providerpaydate,                                                 Emscpayableamount, Emscreceivedamount, Createdbyid, Modifiedbyid, Createdate, Modifieddate,                                                 Externalfilegenerateddate, Isactive, Gender, Isextracted, Extracteddate, Tripnumber,                                                 Programtype, Memberextendedcode1, Memberextendedcode2, Memberextendedcode3, Memberextendedcode4,                                                 Memberextendedcode5, Memberextendedcode6, Memberextendedcode7, Transportationprovideraddress1,                                                 Transportationprovideraddress2, Transportationprovidercity, Transportationproviderstate,                                                 Transportationproviderzip, Puname, Destinationname, Casenumber, Servicedateto, Servicedatefrom,                                                 Tripmodifier2, Serviceunits, Unitprice, Certificationcode, Acknowledgedate, Completeddate,                                                 Transportationproviderfedid, Senttooracle, Oraclesentdate, Policyholdernetmemberid,                                                 Policyholdermemberid, Policyholderfirstname, Policyholdermiddleinitial, Policyholderlastname,                                                 Policyholderaddress1, Policyholderaddress2, Policyholdercity, Policyholderstate,                                                 Policyholderzipcode, Policyholderdob, Policyholdergender, Policyholdergroupid,                                                 Policyholdergroupname, Policyholderpolicyno, Relationshipcode, Transportationprovidernpi,                                                 Transportationprovidermedicaid, Tripcreatedate, Addlpassengercount, Schedulepickuptime,                                                 Appointmenttime, Membermedicaidid, Membermedicareid, Memberalternativeid, Pickuptime,                                                 Destinationtime, Tripoutcome, Transportationproviderzip4, Clientauthcode,                                                 Case_Expenselineitemdetails_Id, Drivername, Driverlicensenumber, Driverlicensestate,                                                 A2cdriverid, Providernpi, Providertaxid, Providertaxonomycode, Providerstateproviderid,                                                 Equipmentandspecialneeds, Vehicleidnumber, Performingprovidername, Paymentstatus_Id,                                                 Planpaiddate, Checknumber, Reasoncode, Paidamount, Reasondescription, Resubmissiondate,                                                 Updatestatus, A2cprocessingstatus, Planstatuscode, Processeddate, Systemcreatedate,                                                 Claimresubmissionperiod, Clientclaimnumber, Altclientclaimnumber, Processingstatus_Id,                                                 A2cclaimnumber, Claimtypeid, Encrypteddriverssn, Cleareddate, Netstopattribute_Arlineitem_Id,                                                 Extracteddate2, Externalfilegenerateddate2, Acknowledgedate2, Completeddate2,                                                 Processingstatus_Id2, Processeddate2, Planstatuscode2, Reasoncode2, Reasondescription2,                                                 Encryptedtpfedid, Changedbyusername, Changereason, Billingrepository_Id, Flowid )    SELECT   Emscbilling837interface_Id,             Healthplancode,             Stopid,             Appointmentdate,             Mileage,             Actualprice,             Memberid,             Lastname,             Firstname,             Middleinitial,             Dob,             Encryptedssn,             Groupid,             Age,             Memberaddress1,             Memberaddress2,             Membercity,             Membercounty,             Memberstate,             Memberzipcode,             Memberphonenumber,             Puaddress1,             Puaddress2,             Pucity,             Pucounty,             Pustate,             Puzipcode,             Destinationaddress1,             Destinationaddress2,             Destinationcity,             Destinationcounty,             Destinationstate,             Destinationzipcode,             Finallos,             Hcpc,             Transportationprovider,             Apvenderid,             Mrbtypeid,             Mrbtypename,             Status,             Finalstatusdate,             Confirmationnumber,             Diagnosiscode,             Tripmodifier,             Billingmodifier,             Costplusamount,             Billamount,             Pos,             Netstop_Id,             Netmember_Id,             Transportationprovider_Id,             Addressmember_Id,             Addresspickup_Id,             Addressdestination_Id,             Actuallegcostamount,             Reportedlegcostamount,             Providerpayableamount,             Providerreceivedamount,             Providerpaydate,             Emscpayableamount,             Emscreceivedamount,             Createdbyid,             Modifiedbyid,             Createdate,             Modifieddate,             Externalfilegenerateddate,             Isactive,             Gender,             Isextracted,             Extracteddate,             Tripnumber,             Programtype,             Memberextendedcode1,             Memberextendedcode2,             Memberextendedcode3,             Memberextendedcode4,             Memberextendedcode5,             Memberextendedcode6,             Memberextendedcode7,             Transportationprovideraddress1,             Transportationprovideraddress2,             Transportationprovidercity,             Transportationproviderstate,             Transportationproviderzip,             Puname,             Destinationname,             Casenumber,             Servicedateto,             Servicedatefrom,             Tripmodifier2,             Serviceunits,             Unitprice,             Certificationcode,             Acknowledgedate,             Completeddate,             Transportationproviderfedid,             Senttooracle,             Oraclesentdate,             Policyholdernetmemberid,             Policyholdermemberid,             Policyholderfirstname,             Policyholdermiddleinitial,             Policyholderlastname,             Policyholderaddress1,             Policyholderaddress2,             Policyholdercity,             Policyholderstate,             Policyholderzipcode,             Policyholderdob,             Policyholdergender,             Policyholdergroupid,             Policyholdergroupname,             Policyholderpolicyno,             Relationshipcode,             Transportationprovidernpi,             Transportationprovidermedicaid,             Tripcreatedate,             Addlpassengercount,             Schedulepickuptime,             Appointmenttime,             Membermedicaidid,             Membermedicareid,             Memberalternativeid,             Pickuptime,             Destinationtime,             Tripoutcome,             Transportationproviderzip4,             Clientauthcode,             Case_Expenselineitemdetails_Id,             Drivername,             Driverlicensenumber,             Driverlicensestate,             A2cdriverid,             Providernpi,             Providertaxid,             Providertaxonomycode,             Providerstateproviderid,             Equipmentandspecialneeds,             Vehicleidnumber,             Performingprovidername,             Paymentstatus_Id,             Planpaiddate,             Checknumber,             Reasoncode,             Paidamount,             Reasondescription,             Resubmissiondate,             Updatestatus,             A2cprocessingstatus,             Planstatuscode,             Processeddate,             Systemcreatedate,             Claimresubmissionperiod,             Clientclaimnumber,             Altclientclaimnumber,             Processingstatus_Id,             A2cclaimnumber,             Claimtypeid,             Encrypteddriverssn,             Cleareddate,             Netstopattribute_Arlineitem_Id,             Extracteddate2,             Externalfilegenerateddate2,             Acknowledgedate2,             Completeddate2,             Processingstatus_Id2,             Processeddate2,             Planstatuscode2,             Reasoncode2,             Reasondescription2,             Encryptedtpfedid,             Changedbyusername,             Changereason,             Billingrepository_Id,             '"
								+ tempFlowId + "'      FROM   Soareporting.Billing837interface     WHERE   Flowid = '"
								+ usecase + "'");

				statementUpdate.executeUpdate();

			}
			SOACon.commit();
			PreparedStatement statementUpdate = StatsCon.prepareStatement(
					"update [A2C].[dbo].[EMSCBilling837Interface] set ProcessingStatus_ID = 9 where HealthPlanCode in (Select bc.HealthPlanCode from [BillablePlanCode] bc where bc.[BillableEntity_ID]  = "
							+ billingID + ") and ProcessingStatus_ID= 6");

			statementUpdate.executeUpdate();
			StatsCon.commit();

			statementUpdate.close();

			PreparedStatement ps3 = SOACon.prepareStatement(
					"SELECT   Emscbilling837interface_Id,          Healthplancode,          Stopid,          Appointmentdate,          Mileage,          Actualprice,          Memberid,          Lastname,          Firstname,          Middleinitial,          Dob,          Encryptedssn,          Groupid,          Age,          Memberaddress1,          Memberaddress2,          Membercity,          Membercounty,          Memberstate,          Memberzipcode,          Memberphonenumber,          Puaddress1,          Puaddress2,          Pucity,          Pucounty,          Pustate,          Puzipcode,          Destinationaddress1,          Destinationaddress2,          Destinationcity,          Destinationcounty,          Destinationstate,          Destinationzipcode,          Finallos,          Hcpc,          Transportationprovider,          Apvenderid,          Mrbtypeid,          Mrbtypename,          Status,          Finalstatusdate,          Confirmationnumber,          Diagnosiscode,          Tripmodifier,          Billingmodifier,          Costplusamount,          Billamount,          Pos,          Netstop_Id,          Netmember_Id,          Transportationprovider_Id,          Addressmember_Id,          Addresspickup_Id,          Addressdestination_Id,          Actuallegcostamount,          Reportedlegcostamount,          Providerpayableamount,          Providerreceivedamount,          Providerpaydate,          Emscpayableamount,          Emscreceivedamount,          Createdbyid,          Modifiedbyid,          Createdate,          Modifieddate,          Externalfilegenerateddate,          Isactive,          Gender,          Isextracted,          Extracteddate,          Tripnumber,          Programtype,          Memberextendedcode1,          Memberextendedcode2,          Memberextendedcode3,          Memberextendedcode4,          Memberextendedcode5,          Memberextendedcode6,          Memberextendedcode7,          Transportationprovideraddress1,          Transportationprovideraddress2,          Transportationprovidercity,          Transportationproviderstate,          Transportationproviderzip,          Puname,          Destinationname,          Casenumber,          Servicedateto,          Servicedatefrom,          Tripmodifier2,          Serviceunits,          Unitprice,          Certificationcode,          Acknowledgedate,          Completeddate,          Transportationproviderfedid,          Senttooracle,          Oraclesentdate,          Policyholdernetmemberid,          Policyholdermemberid,          Policyholderfirstname,          Policyholdermiddleinitial,          Policyholderlastname,          Policyholderaddress1,          Policyholderaddress2,          Policyholdercity,          Policyholderstate,          Policyholderzipcode,          Policyholderdob,          Policyholdergender,          Policyholdergroupid,          Policyholdergroupname,          Policyholderpolicyno,          Relationshipcode,          Transportationprovidernpi,          Transportationprovidermedicaid,          Tripcreatedate,          Addlpassengercount,          Schedulepickuptime,          Appointmenttime,          Membermedicaidid,          Membermedicareid,          Memberalternativeid,          Pickuptime,          Destinationtime,          Tripoutcome,          Transportationproviderzip4,          Clientauthcode,          Case_Expenselineitemdetails_Id,          Drivername,          Driverlicensenumber,          Driverlicensestate,          A2cdriverid,          Providernpi,          Providertaxid,          Providertaxonomycode,          Providerstateproviderid,          Equipmentandspecialneeds,          Vehicleidnumber,          Performingprovidername,          Paymentstatus_Id,          Planpaiddate,          Checknumber,          Reasoncode,          Paidamount,          Reasondescription,          Resubmissiondate,          Updatestatus,          A2cprocessingstatus,          Planstatuscode,          Processeddate,          Systemcreatedate,          Claimresubmissionperiod,          Clientclaimnumber,          Altclientclaimnumber,          Processingstatus_Id,          A2cclaimnumber,          Claimtypeid,          Encrypteddriverssn,          Cleareddate,          Netstopattribute_Arlineitem_Id,          Extracteddate2,          Externalfilegenerateddate2,          Acknowledgedate2,          Completeddate2,          Processingstatus_Id2,          Processeddate2,          Planstatuscode2,          Reasoncode2,          Reasondescription2,          Encryptedtpfedid,          Changedbyusername,          Changereason,          Billingrepository_Id,          Flowid   FROM   Soareporting.Billing837interface  WHERE   flowid in ( '" + tempFlowId + "') order by Billingrepository_Id"

			);

			ResultSet result3 = ps3.executeQuery();
			System.out.println("In Restore usecase = " + usecase);
			while (result3.next()) {

				PreparedStatement statement3 = StatsCon.prepareStatement(
						"INSERT INTO [A2C].[dbo].[EMSCBilling837Interface]             (EMSCBilling837Interface_ID,  		 [HealthPlanCode]             ,[StopID]             ,[AppointmentDate]             ,[Mileage]             ,[ActualPrice]             ,[MemberID]             ,[LastName]             ,[FirstName]             ,[MiddleInitial]             ,[DOB]                          ,[GroupID]             ,[Age]             ,[MemberAddress1]             ,[MemberAddress2]             ,[MemberCity]             ,[MemberCounty]             ,[MemberState]             ,[MemberZIPCode]             ,[MemberPhoneNumber]             ,[PUAddress1]             ,[PUAddress2]             ,[PUCity]             ,[PUCounty]             ,[PUState]             ,[PUZIPCode]             ,[DestinationAddress1]             ,[DestinationAddress2]             ,[DestinationCity]             ,[DestinationCounty]             ,[DestinationState]             ,[DestinationZIPCode]             ,[FinalLOS]             ,[HCPC]             ,[TransportationProvider]             ,[APVenderID]             ,[MRBTypeID]             ,[MRBTypeName]             ,[Status]             ,[FinalStatusDate]             ,[ConfirmationNumber]             ,[DiagnosisCode]             ,[TripModifier]             ,[BillingModifier]             ,[CostPlusAmount]             ,[BillAmount]             ,[POS]             ,[NetStop_ID]             ,[NetMember_ID]             ,[TransportationProvider_ID]             ,[AddressMember_ID]             ,[AddressPickup_ID]             ,[AddressDestination_ID]             ,[ActualLegCostAmount]             ,[ReportedLegCostAmount]             ,[ProviderPayableAmount]             ,[ProviderReceivedAmount]             ,[ProviderPayDate]             ,[EMSCPayableAmount]             ,[EMSCReceivedAmount]             ,[CreatedByID]             ,[ModifiedByID]             ,[CreateDate]             ,[ModifiedDate]             ,[ExternalFileGeneratedDate]             ,[IsActive]             ,[Gender]             ,[IsExtracted]             ,[ExtractedDate]             ,[TripNumber]             ,[ProgramType]             ,[MemberExtendedCode1]             ,[MemberExtendedCode2]             ,[MemberExtendedCode3]             ,[MemberExtendedCode4]             ,[MemberExtendedCode5]             ,[MemberExtendedCode6]             ,[MemberExtendedCode7]             ,[TransportationProviderAddress1]             ,[TransportationProviderAddress2]             ,[TransportationProviderCity]             ,[TransportationProviderState]             ,[TransportationProviderZip]             ,[PUName]             ,[DestinationName]             ,[CaseNumber]             ,[ServiceDateTo]             ,[ServiceDateFrom]             ,[TripModifier2]             ,[ServiceUnits]             ,[UnitPrice]             ,[CertificationCode]             ,[AcknowledgeDate]             ,[CompletedDate]             ,[TransportationProviderFedID]             ,[SentToOracle]             ,[OracleSentDate]             ,[PolicyHolderNetMemberID]             ,[PolicyHolderMemberID]             ,[PolicyHolderFirstName]             ,[PolicyHolderMiddleInitial]             ,[PolicyHolderLastName]             ,[PolicyHolderAddress1]             ,[PolicyHolderAddress2]             ,[PolicyHolderCity]             ,[PolicyHolderState]             ,[PolicyHolderZIPCode]             ,[PolicyHolderDOB]             ,[PolicyHolderGender]             ,[PolicyHolderGroupID]             ,[PolicyHolderGroupName]             ,[PolicyHolderPolicyNo]             ,[RelationshipCode]             ,[TransportationProviderNPI]             ,[TransportationProviderMedicaidID]             ,[TripCreateDate]             ,[AddlPassengerCount]             ,[SchedulePickupTime]             ,[AppointmentTime]             ,[MemberMedicaidID]             ,[MemberMedicareID]             ,[MemberAlternativeID]             ,[PickupTime]             ,[DestinationTime]             ,[TripOutcome]             ,[TransportationProviderZip4]             ,[ClientAuthCode]             ,[Case_ExpenseLineItemDetails_ID]             ,[DriverName]             ,[DriverLicenseNumber]             ,[DriverLicenseState]             ,[A2CDriverID]             ,[ProviderNPI]             ,[ProviderTaxID]             ,[ProviderTaxonomyCode]             ,[ProviderStateProviderID]             ,[EquipmentAndSpecialNeeds]             ,[VehicleIDNumber]             ,[PerformingProviderName]             ,[PaymentStatus_ID]             ,[PlanPaidDate]             ,[CheckNumber]             ,[ReasonCode]             ,[PaidAmount]             ,[ReasonDescription]             ,[ReSubmissionDate]             ,[UpdateStatus]             ,[A2CProcessingStatus]             ,[PlanStatusCode]             ,[ProcessedDate]             ,[SystemCreateDate]             ,[ClaimResubmissionPeriod]             ,[ClientClaimNumber]             ,[AltClientClaimNumber]             ,[ProcessingStatus_ID]             ,[A2CClaimNumber]             ,[ClaimTypeID]                          ,[ClearedDate]             ,[NetStopAttribute_ARLineItem_ID]             ,[ExtractedDate2]             ,[ExternalFileGeneratedDate2]             ,[AcknowledgeDate2]             ,[CompletedDate2]             ,[ProcessingStatus_ID2]             ,[ProcessedDate2]             ,[PlanStatusCode2]             ,[ReasonCode2]             ,[ReasonDescription2]                          ,[ChangedbyUserName]             ,[ChangeReason]) "
								+ " VALUES (newid(),?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?,?,?,?,?,?,?," + "          ?,?,?,?,?,?,?,?,?,?,"
								+ "          ?,?,?,?," + "          ?,?,?,?,?,?,?,?,?)");

				statement3.setString(1, result3.getString("HealthPlanCode"));
				statement3.setString(2, result3.getString("StopID"));
				statement3.setString(3, result3.getString("AppointmentDate"));
				statement3.setString(4, result3.getString("Mileage"));
				statement3.setString(5, result3.getString("ActualPrice"));
				statement3.setString(6, result3.getString("MemberID"));
				statement3.setString(7, result3.getString("LastName"));
				statement3.setString(8, result3.getString("FirstName"));
				statement3.setString(9, result3.getString("MiddleInitial"));
				statement3.setString(10, result3.getString("DOB"));
				statement3.setString(11, result3.getString("GroupID"));
				statement3.setString(12, result3.getString("Age"));
				statement3.setString(13, result3.getString("MemberAddress1"));
				statement3.setString(14, result3.getString("MemberAddress2"));
				statement3.setString(15, result3.getString("MemberCity"));
				statement3.setString(16, result3.getString("MemberCounty"));
				statement3.setString(17, result3.getString("MemberState"));
				statement3.setString(18, result3.getString("MemberZIPCode"));
				statement3.setString(19, result3.getString("MemberPhoneNumber"));
				statement3.setString(20, result3.getString("PUAddress1"));
				statement3.setString(21, result3.getString("PUAddress2"));
				statement3.setString(22, result3.getString("PUCity"));
				statement3.setString(23, result3.getString("PUCounty"));
				statement3.setString(24, result3.getString("PUState"));
				statement3.setString(25, result3.getString("PUZIPCode"));
				statement3.setString(26, result3.getString("DestinationAddress1"));
				statement3.setString(27, result3.getString("DestinationAddress2"));
				statement3.setString(28, result3.getString("DestinationCity"));
				statement3.setString(29, result3.getString("DestinationCounty"));
				statement3.setString(30, result3.getString("DestinationState"));
				statement3.setString(31, result3.getString("DestinationZIPCode"));
				statement3.setString(32, result3.getString("FinalLOS"));
				statement3.setString(33, result3.getString("HCPC"));
				statement3.setString(34, result3.getString("TransportationProvider"));
				statement3.setString(35, result3.getString("APVenderID"));
				statement3.setString(36, result3.getString("MRBTypeID"));
				statement3.setString(37, result3.getString("MRBTypeName"));
				statement3.setString(38, result3.getString("Status"));

				statement3.setString(39, result3.getString("FINALSTATUSDATE"));
				statement3.setString(40, result3.getString("CONFIRMATIONNUMBER"));
				statement3.setString(41, result3.getString("DIAGNOSISCODE"));
				statement3.setString(42, result3.getString("TRIPMODIFIER"));
				statement3.setString(43, result3.getString("BILLINGMODIFIER"));
				statement3.setString(44, result3.getString("COSTPLUSAMOUNT"));
				statement3.setString(45, result3.getString("BILLAMOUNT"));
				statement3.setString(46, result3.getString("POS"));
				statement3.setString(47, result3.getString("NETSTOP_ID"));
				statement3.setString(48, result3.getString("NETMEMBER_ID"));
				statement3.setString(49, result3.getString("TRANSPORTATIONPROVIDER_ID"));
				statement3.setString(50, result3.getString("ADDRESSMEMBER_ID"));
				statement3.setString(51, result3.getString("ADDRESSPICKUP_ID"));
				statement3.setString(52, result3.getString("ADDRESSDESTINATION_ID"));
				statement3.setString(53, result3.getString("ACTUALLEGCOSTAMOUNT"));
				statement3.setString(54, result3.getString("REPORTEDLEGCOSTAMOUNT"));
				statement3.setString(55, result3.getString("PROVIDERPAYABLEAMOUNT"));
				statement3.setString(56, result3.getString("PROVIDERRECEIVEDAMOUNT"));
				statement3.setString(57, result3.getString("PROVIDERPAYDATE"));
				statement3.setString(58, result3.getString("EMSCPAYABLEAMOUNT"));
				statement3.setString(59, result3.getString("EMSCRECEIVEDAMOUNT"));
				statement3.setString(60, result3.getString("CREATEDBYID"));
				statement3.setString(61, result3.getString("MODIFIEDBYID"));
				statement3.setString(62, result3.getString("CREATEDATE"));
				statement3.setString(63, result3.getString("MODIFIEDDATE"));
				statement3.setString(64, result3.getString("EXTERNALFILEGENERATEDDATE"));
				statement3.setString(65, result3.getString("ISACTIVE"));
				statement3.setString(66, result3.getString("GENDER"));
				statement3.setString(67, result3.getString("ISEXTRACTED"));
				statement3.setString(68, result3.getString("EXTRACTEDDATE"));
				statement3.setString(69, result3.getString("TRIPNUMBER"));

				statement3.setString(70, result3.getString("PROGRAMTYPE"));
				statement3.setString(71, result3.getString("MEMBEREXTENDEDCODE1"));
				statement3.setString(72, result3.getString("MEMBEREXTENDEDCODE2"));
				statement3.setString(73, result3.getString("MEMBEREXTENDEDCODE3"));
				statement3.setString(74, result3.getString("MEMBEREXTENDEDCODE4"));
				statement3.setString(75, result3.getString("MEMBEREXTENDEDCODE5"));
				statement3.setString(76, result3.getString("MEMBEREXTENDEDCODE6"));
				statement3.setString(77, result3.getString("MEMBEREXTENDEDCODE7"));
				statement3.setString(78, result3.getString("TRANSPORTATIONPROVIDERADDRESS1"));
				statement3.setString(79, result3.getString("TRANSPORTATIONPROVIDERADDRESS2"));
				statement3.setString(80, result3.getString("TRANSPORTATIONPROVIDERCITY"));
				statement3.setString(81, result3.getString("TRANSPORTATIONPROVIDERSTATE"));
				statement3.setString(82, result3.getString("TRANSPORTATIONPROVIDERZIP"));
				statement3.setString(83, result3.getString("PUNAME"));
				statement3.setString(84, result3.getString("DESTINATIONNAME"));
				statement3.setString(85, result3.getString("CASENUMBER"));
				statement3.setString(86, result3.getString("SERVICEDATETO"));
				statement3.setString(87, result3.getString("SERVICEDATEFROM"));
				statement3.setString(88, result3.getString("TRIPMODIFIER2"));
				statement3.setString(89, result3.getString("SERVICEUNITS"));
				statement3.setString(90, result3.getString("UNITPRICE"));
				statement3.setString(91, result3.getString("CERTIFICATIONCODE"));
				statement3.setString(92, result3.getString("ACKNOWLEDGEDATE"));
				statement3.setString(93, result3.getString("COMPLETEDDATE"));
				statement3.setString(94, result3.getString("TRANSPORTATIONPROVIDERFEDID"));
				statement3.setString(95, result3.getString("SENTTOORACLE"));
				statement3.setString(96, result3.getString("ORACLESENTDATE"));
				statement3.setString(97, result3.getString("POLICYHOLDERNETMEMBERID"));
				statement3.setString(98, result3.getString("POLICYHOLDERMEMBERID"));

				statement3.setString(99, result3.getString("PolicyHolderFirstName"));
				statement3.setString(100, result3.getString("PolicyHolderMiddleInitial"));
				statement3.setString(101, result3.getString("PolicyHolderLastName"));
				statement3.setString(102, result3.getString("PolicyHolderAddress1"));
				statement3.setString(103, result3.getString("PolicyHolderAddress2"));
				statement3.setString(104, result3.getString("PolicyHolderCity"));
				statement3.setString(105, result3.getString("PolicyHolderState"));
				statement3.setString(106, result3.getString("PolicyHolderZIPCode"));
				statement3.setString(107, result3.getString("PolicyHolderDOB"));
				statement3.setString(108, result3.getString("PolicyHolderGender"));

				statement3.setString(109, result3.getString("PolicyHolderGroupID"));
				statement3.setString(110, result3.getString("PolicyHolderGroupName"));
				statement3.setString(111, result3.getString("PolicyHolderPolicyNo"));
				statement3.setString(112, result3.getString("RelationshipCode"));
				statement3.setString(113, result3.getString("TransportationProviderNPI"));
				statement3.setString(114, result3.getString("Transportationprovidermedicaid"));
				statement3.setString(115, result3.getString("TripCreateDate"));
				statement3.setString(116, result3.getString("AddlPassengerCount"));
				statement3.setString(117, result3.getString("SchedulePickupTime"));
				statement3.setString(118, result3.getString("AppointmentTime"));
				statement3.setString(119, result3.getString("MemberMedicaidID"));
				statement3.setString(120, result3.getString("MemberMedicareID"));
				statement3.setString(121, result3.getString("MemberAlternativeID"));
				statement3.setString(122, result3.getString("PickupTime"));
				statement3.setString(123, result3.getString("DestinationTime"));
				statement3.setString(124, result3.getString("TripOutcome"));
				statement3.setString(125, result3.getString("TransportationProviderZip4"));
				statement3.setString(126, result3.getString("ClientAuthCode"));
				statement3.setString(127, result3.getString("Case_ExpenseLineItemDetails_ID"));
				statement3.setString(128, result3.getString("DriverName"));
				statement3.setString(129, result3.getString("DriverLicenseNumber"));
				statement3.setString(130, result3.getString("DriverLicenseState"));
				statement3.setString(131, result3.getString("A2CDriverID"));
				statement3.setString(132, result3.getString("ProviderNPI"));
				statement3.setString(133, result3.getString("ProviderTaxID"));
				statement3.setString(134, result3.getString("ProviderTaxonomyCode"));
				statement3.setString(135, result3.getString("ProviderStateProviderID"));
				statement3.setString(136, result3.getString("EquipmentAndSpecialNeeds"));
				statement3.setString(137, result3.getString("VehicleIDNumber"));
				statement3.setString(138, result3.getString("PerformingProviderName"));

				statement3.setString(139, result3.getString("PaymentStatus_ID"));
				statement3.setString(140, result3.getString("PlanPaidDate"));
				statement3.setString(141, result3.getString("CheckNumber"));
				statement3.setString(142, result3.getString("ReasonCode"));
				statement3.setString(143, result3.getString("PaidAmount"));
				statement3.setString(144, result3.getString("ReasonDescription"));
				statement3.setString(145, result3.getString("ReSubmissionDate"));
				statement3.setString(146, result3.getString("UpdateStatus"));
				statement3.setString(147, result3.getString("A2CProcessingStatus"));
				statement3.setString(148, result3.getString("PlanStatusCode"));
				statement3.setString(149, result3.getString("ProcessedDate"));
				statement3.setString(150, result3.getString("SystemCreateDate"));
				statement3.setString(151, result3.getString("ClaimResubmissionPeriod"));
				statement3.setString(152, result3.getString("ClientClaimNumber"));
				statement3.setString(153, result3.getString("AltClientClaimNumber"));
				statement3.setString(154, "6");
				statement3.setString(155, result3.getString("A2CClaimNumber"));
				statement3.setString(156, result3.getString("ClaimTypeID"));

				statement3.setString(157, result3.getString("ClearedDate"));
				statement3.setString(158, result3.getString("NetStopAttribute_ARLineItem_ID"));
				statement3.setString(159, result3.getString("ExtractedDate2"));
				statement3.setString(160, result3.getString("ExternalFileGeneratedDate2"));
				statement3.setString(161, result3.getString("AcknowledgeDate2"));
				statement3.setString(162, result3.getString("CompletedDate2"));
				statement3.setString(163, result3.getString("ProcessingStatus_ID2"));
				statement3.setString(164, result3.getString("ProcessedDate2"));
				statement3.setString(165, "Testing Dashboard");
				statement3.setString(166, result3.getString("ReasonCode2"));
				statement3.setString(167, result3.getString("ReasonDescription2"));

				statement3.setString(168, result3.getString("Changedbyusername"));
				statement3.setString(169, result3.getString("ChangeReason"));

				statement3.executeUpdate();

			}
			SOACon.commit();
			result3.close();
			ps3.close();
			System.out.println("processname = " + processname);
			PreparedStatement Lastps = SOACon.prepareStatement(
					"Select max(flow_id)  flow_id from soareporting.soa_encounter_instances where replace(replace(replace(replace(replace(replace(replace(replace(composite,'_EncounterProcessing',''),'_EncounterService',''),'_EncounterProcess6400',''),'_EncounterProcess',''),'SyncSOAPreportingEncounterFileDataByEntity','Update Detail Data ' || initcap(title)),'SyncSOAReportingEncountersDataByEntity','Update Summary Data ' || initcap(title)),'SyncSOAReportingEncountersData','Update All Detail Data'),'SyncSOAPreportingEncounterFileData','Update All Summary Data') = '"
							+ processname + "'");

			ResultSet LastResult = Lastps.executeQuery();
			String flowid = null;
			while (LastResult.next()) {

				flowid = LastResult.getString("flow_id");

			}
			System.out.println("flowid = " + flowid);
			LastResult.close();
			Lastps.close();

			URL url = new URL(wsEndPoint);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://evhc.com/nemt/spice/schemas/Encounter/v1\" xmlns:v11=\"http://evhc.com/nemt/spice/schemas/common/ServiceHeader/v1\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <v1:EncounterRequest>\r\n"
					+ "         <v1:Header>\r\n"
					+ "            <v11:requestingApplication>?</v11:requestingApplication>\r\n"
					+ "            <v11:timestamp>?</v11:timestamp>\r\n" + "         </v1:Header>\r\n"
					+ "         <v1:Source>?</v1:Source>\r\n" + "      </v1:EncounterRequest>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>" + "";
			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();

			String SOAPAction = "sendEncounterDetails";
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			OutputStream out = httpConn.getOutputStream();
			out.write(b);
			out.close();
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
			BufferedReader in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}

			PreparedStatement currentPS = SOACon.prepareStatement("Select Soareporting.F_Get_Current_Flowid('"
					+ processname + "','" + flowid + "') flow_id from dual ");

			ResultSet currentResult = currentPS.executeQuery();
			String currentFlowId = null;
			while (currentResult.next()) {

				currentFlowId = currentResult.getString("flow_id");

			}
			System.out.println("currentFlowId = " + currentFlowId);
			currentResult.close();
			currentPS.close();
			// tempFlowId

			PreparedStatement updateStatement = SOACon.prepareStatement("Update BILLING837INTERFACE set flowid = '"
					+ currentFlowId + "' where flowid = '" + tempFlowId + "'");

			updateStatement.executeUpdate();
			SOACon.commit();
			updateStatement.close();
			System.out.println("Update currentFlowId = " + currentFlowId);
			PreparedStatement filesPS = SOACon.prepareStatement(
					"SELECT   distinct REPLACE( Url, 'file://localhost/', '' ) || '/' || PATH Filelocation,           PATH Filename,           REPLACE( Url, 'file://localhost/', '' ) Directory,   0              Claims_Count    FROM   Soareporting.Soa_Encounter_Wire Ew   WHERE   Flow_Id = '"
							+ currentFlowId + "'");

			ResultSet filesResult = filesPS.executeQuery();

			while (filesResult.next()) {

				PreparedStatement psCount = StatsCon.prepareStatement(
						"SELECT top 1 ClaimCount Claims_Count  FROM [A2CBilling].[dbo].[BillingFile] bf,[A2CBilling].[dbo].[BillableEntity] be       where  be.BillableEntity_ID = bf.BillableEntity_ID   and bf.[BillingFileName] = '"
								+ filesResult.getString("filename") + "' and bf.BillableEntity_ID = " + billingID
								+ " order by SystemCreateDate desc ");

				ResultSet countResult = psCount.executeQuery();
				String lncount = "0";
				while (countResult.next()) {
					lncount = countResult.getString("Claims_Count");
				}

				PreparedStatement statement = SOACon.prepareStatement(
						"INSERT INTO instance_flow_payload (flowid,x12payload,filename,directory,claimcount"
								+ ") VALUES (?,?,?,?,?)");
				String contents = new String(Files.readAllBytes(Paths.get(filesResult.getString("filelocation"))),
						StandardCharsets.UTF_8);
				statement.setString(1, currentFlowId);
				statement.setString(2, contents);
				statement.setString(3, filesResult.getString("filename"));
				statement.setString(4, filesResult.getString("directory"));
				statement.setString(5, lncount);

				statement.executeUpdate();
				SOACon.commit();
				statement.close();
			}
			filesResult.close();
			filesPS.close();
			System.out.println("1");
			PreparedStatement ps22 = StatsCon.prepareStatement(
					"SELECT  [A2C].[dbo].[EMSCBilling837Interface].HealthPlancode,   [A2C].[dbo].[EMSCBilling837Interface].STOPID,   [A2CBilling].[dbo].[BillingClaimError].BillingClaimErrorMessage,   [A2C].[dbo].[EMSCBilling837Interface].HCPC,   [A2C].[dbo].[EMSCBilling837Interface].DiagnosisCode,   [A2C].[dbo].[EMSCBilling837Interface].BillingModifier,   [A2C].[dbo].[EMSCBilling837Interface].Mileage,   [A2C].[dbo].[EMSCBilling837Interface].TransportationProvider,   [A2C].[dbo].[EMSCBilling837Interface].ProviderPayDate,   [A2C].[dbo].[EMSCBilling837Interface].FirstName,   [A2C].[dbo].[EMSCBilling837Interface].LastName,   [A2C].[dbo].[EMSCBilling837Interface].POS,   [A2C].[dbo].[EMSCBilling837Interface].TripModifier,   [A2C].[dbo].[EMSCBilling837Interface].TripModifier2,   [A2C].[dbo].[EMSCBilling837Interface].BillAmount,   [A2C].[dbo].[EMSCBilling837Interface].PaidAmount,   [A2C].[dbo].[EMSCBilling837Interface].AppointmentDate,   [A2C].[dbo].[EMSCBilling837Interface].ServiceDateFrom,   [A2C].[dbo].[EMSCBilling837Interface].ServiceDateTo,   [A2C].[dbo].[EMSCBilling837Interface].MemberID,     [A2C].[dbo].[EMSCBilling837Interface].PUName,   [A2C].[dbo].[EMSCBilling837Interface].PUAddress1,   [A2C].[dbo].[EMSCBilling837Interface].PUAddress2,   [A2C].[dbo].[EMSCBilling837Interface].PUCity,   [A2C].[dbo].[EMSCBilling837Interface].PUCounty,   [A2C].[dbo].[EMSCBilling837Interface].PUState,   [A2C].[dbo].[EMSCBilling837Interface].PUZIPCode,       [A2C].[dbo].[EMSCBilling837Interface].DestinationName,   [A2C].[dbo].[EMSCBilling837Interface].DestinationAddress1 ,   [A2C].[dbo].[EMSCBilling837Interface].DestinationAddress2,   [A2C].[dbo].[EMSCBilling837Interface].DestinationCity,   [A2C].[dbo].[EMSCBilling837Interface].DestinationCounty,   [A2C].[dbo].[EMSCBilling837Interface].DestinationState,   [A2C].[dbo].[EMSCBilling837Interface].DestinationZIPCode,   [A2C].[dbo].[EMSCBilling837Interface].MemberAddress1,   [A2C].[dbo].[EMSCBilling837Interface].MemberAddress2,   [A2C].[dbo].[EMSCBilling837Interface].MemberCity,   [A2C].[dbo].[EMSCBilling837Interface].MemberCounty,   [A2C].[dbo].[EMSCBilling837Interface].MemberState,   [A2C].[dbo].[EMSCBilling837Interface].MemberZIPCode FROM [A2CBilling].[dbo].[BillingClaimError]      INNER JOIN [A2CBilling].[dbo].[BillingClaimLine]        ON [A2CBilling].[dbo].[BillingClaimError].BillingClaim_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaim_ID      INNER JOIN [A2CBilling].[dbo].[BillingClaimLineSource]        ON [A2CBilling].[dbo].[BillingClaimLineSource].BillingClaimLine_ID = [A2CBilling].[dbo].[BillingClaimLine].BillingClaimLine_ID      INNER JOIN [A2C].[dbo].[EMSCBilling837Interface]        ON [A2C].[dbo].[EMSCBilling837Interface].BillingRepository_ID = [A2CBilling].[dbo].[BillingClaimLineSource].BillingRepository_ID,      [dbo].BillablePlanCode,      [dbo].BillableEntity WHERE ProcessingStatus_ID = '8' and  [dbo].BillablePlanCode.BillableEntity_ID = [dbo].BillableEntity.BillableEntity_ID AND [A2C].[dbo].[EMSCBilling837Interface].HealthPlancode = [dbo].BillablePlanCode.HealthPlanCode AND [dbo].BillableEntity.BillableEntity_ID = "
							+ billingID + " and PlanStatusCode2 = 'Testing Dashboard' ORDER BY 1, 2 "

			);

			ResultSet result22 = ps22.executeQuery();
			System.out.println("2");
			while (result22.next()) {

				PreparedStatement statement22 = SOACon.prepareStatement(
						"INSERT INTO validationmembers (Flowid,            Healthplancode,            Stopid,            Billingclaimerrormessage,            Hcpc,            Diagnosiscode,            Billingmodifier,            Mileage,            Transportationprovider,            Providerpaydate,            Firstname,            Lastname,            Pos,            Tripmodifier,            Tripmodifier2,            Billamount,            Paidamount,            Appointmentdate,            Servicedatefrom,            Servicedateto,            Memberid,            Puname,            Puaddress1,            Puaddress2,            Pucity,            Pucounty,            Pustate,            Puzipcode,            Destinationname,            Destinationaddress1,            Destinationaddress2,            Destinationcity,            Destinationcounty,            Destinationstate,            Destinationzipcode,            Memberaddress1,            Memberaddress2,            Membercity,            Membercounty,            Memberstate,            Memberzipcode,created"
								+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)");

				statement22.setString(1, currentFlowId);
				statement22.setString(2, result22.getString("Healthplancode"));
				statement22.setString(3, result22.getString("Stopid"));
				statement22.setString(4, result22.getString("Billingclaimerrormessage"));
				statement22.setString(5, result22.getString("Hcpc"));
				statement22.setString(6, result22.getString("Diagnosiscode"));
				statement22.setString(7, result22.getString("Billingmodifier"));
				statement22.setString(8, result22.getString("Mileage"));
				statement22.setString(9, result22.getString("Transportationprovider"));
				statement22.setString(10, result22.getString("Providerpaydate"));
				statement22.setString(11, result22.getString("Firstname"));
				statement22.setString(12, result22.getString("Lastname"));
				statement22.setString(13, result22.getString("Pos"));
				statement22.setString(14, result22.getString("Tripmodifier"));
				statement22.setString(15, result22.getString("Tripmodifier2"));
				statement22.setString(16, result22.getString("Billamount"));
				statement22.setString(17, result22.getString("Paidamount"));
				statement22.setString(18, result22.getString("Appointmentdate"));
				statement22.setString(19, result22.getString("Servicedatefrom"));
				statement22.setString(20, result22.getString("Servicedateto"));
				statement22.setString(21, result22.getString("Memberid"));
				statement22.setString(22, result22.getString("Puname"));
				statement22.setString(23, result22.getString("Puaddress1"));
				statement22.setString(24, result22.getString("Puaddress2"));
				statement22.setString(25, result22.getString("Pucity"));
				statement22.setString(26, result22.getString("Pucounty"));
				statement22.setString(27, result22.getString("Pustate"));
				statement22.setString(28, result22.getString("Puzipcode"));
				statement22.setString(29, result22.getString("Destinationname"));
				statement22.setString(30, result22.getString("Destinationaddress1"));
				statement22.setString(31, result22.getString("Destinationaddress2"));
				statement22.setString(32, result22.getString("Destinationcity"));
				statement22.setString(33, result22.getString("Destinationcounty"));
				statement22.setString(34, result22.getString("Destinationstate"));
				statement22.setString(35, result22.getString("Destinationzipcode"));
				statement22.setString(36, result22.getString("Memberaddress1"));
				statement22.setString(37, result22.getString("Memberaddress2"));
				statement22.setString(38, result22.getString("Membercity"));

				statement22.setString(39, result22.getString("Membercounty"));
				statement22.setString(40, result22.getString("Memberstate"));
				statement22.setString(41, result22.getString("Memberzipcode"));

				statement22.executeUpdate();

			}
			SOACon.commit();

			System.out.println("4");
			PreparedStatement update2Statement = SOACon.prepareStatement("Update INSTANCE_INFO set parentFlowid = '"
					+ usecase + "',kickoff_id = '" + kickoffID + "' where flowid = '" + currentFlowId + "'");

			update2Statement.executeUpdate();
			SOACon.commit();
			update2Statement.close();
			System.out.println("5");
			PreparedStatement statementPurge = StatsCon.prepareStatement(
					"delete from [A2C].[dbo].[EMSCBilling837Interface] where HealthPlanCode in (Select bc.HealthPlanCode from [BillablePlanCode] bc where bc.[BillableEntity_ID]  = "
							+ billingID + ") and  PlanStatusCode2 = 'Testing Dashboard'");

			statementPurge.executeUpdate();
			StatsCon.commit();

			statementPurge.close();

		} else {

		}
	}

	private StreamedContent download;

	public StreamedContent getDownload() {
		return download;
	}

	public void setDownload(StreamedContent download) {
		this.download = download;
	}

	public void prepDownload(String fileName) throws Exception {

		File file = new File(fileName);
		// File file = new
		// File("/opt/oracle/logs/spicedomaindev/AdminServer/AdminServer.log");
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
		FacesContext.getCurrentInstance().responseComplete();
		// System.out.println("PREP = " + download.getName());
	}

	public StreamedContent generateFile(String fileName) throws FileNotFoundException {
		// System.out.println("generateFile - fileName " + fileName);
		// System.out.println("generateFile - planName " + planName);

		File file = new File(fileName);
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		FacesContext.getCurrentInstance().responseComplete();
		return new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
	}

	public static void callEncounterProc2(String billingID, String entityid) throws Exception {

		try {

			openSoaInfraConnection();

			PreparedStatement statement = SOACon
					.prepareStatement("INSERT INTO Soa_Encounter_Update_Queue (EntityName,Entityid) VALUES (?,?)");
			statement.setString(1, billingID);
			statement.setString(2, entityid);

			statement.executeUpdate();
			SOACon.commit();
			statement.close();
			closeSoaInfraConnection();
		} catch (Exception e) {
			e.printStackTrace();
			// or handle more gracefully
		}

	}

	@PreDestroy
	public void cleanUp() throws Exception {
		// System.out.println("Clean Up!!!!");
	}
}