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
import java.sql.DriverManager;
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
public class EncounterExecuteController implements Serializable {
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
	private Connection theConnection  = null;
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
			getWSDLList();
			getInstanceList();
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

	public void getInstanceList() throws Exception {

		List<EncounterSOAInstances> list = new ArrayList<EncounterSOAInstances>();
		// System.out.println("In getInstanceList");
		FacesContext fc = FacesContext.getCurrentInstance();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select FLOW_CORRELATION_ID,Error_Message,to_char(CREATED_TIME,'Mon DD HH:MI:SS AM') CREATED_TIME,TO_CHAR( Updated_Time, 'Mon DD HH:MI:SS AM' ) Updated_Time,flow_id,replace(replace(replace(replace(replace(replace(replace(replace(composite,'_EncounterProcessing',''),'_EncounterService',''),'_EncounterProcess6400',''),'_EncounterProcess',''),'SyncSOAPreportingEncounterFileDataByEntity','Update Detail Data ' || initcap(title)),'SyncSOAReportingEncountersDataByEntity','Update Summary Data ' || initcap(title)),'SyncSOAReportingEncountersData','Update All Detail Data'),'SyncSOAPreportingEncounterFileData','Update All Summary Data') composite,state from soareporting.soa_encounter_instances");

		ResultSet result = ps.executeQuery();

		while (result.next()) {

			EncounterSOAInstances EncounterFileRec = new EncounterSOAInstances();
			EncounterFileRec.setCreated(result.getString("CREATED_TIME"));
			EncounterFileRec.setUpdated(result.getString("updated_time"));
			EncounterFileRec.setFlowid(result.getString("flow_id"));
			EncounterFileRec.setComposite(result.getString("composite"));
			EncounterFileRec.setFlow_correlation_id(result.getString("flow_correlation_id"));
			EncounterFileRec.setState(result.getString("state"));
			EncounterFileRec.setErrorMessage(result.getString("Error_Message"));

			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();
		encounterCurrentSOAInstances = list;
	}

	public void getBAMLogs() throws Exception {

		List<EncounterBAMObject> list = new ArrayList<EncounterBAMObject>();
		getEncounterDataSQLServer();
		openSQLServerConnection();
		PreparedStatement ps = StatsCon.prepareStatement( //
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
				"SELECT TOP (300)   'Encounter' type, bf.[SystemCreateDate] Created_Date       ,bf.[ClaimCount] Claims_Count       ,bf.[BillingFileName] file_name    , '' url       ,'Completed' file_status 	  ,be.BillableEntityName plan_name, 	  '' error_text,'Outbound' direction   FROM [A2CBilling].[dbo].[BillingFile] bf,[A2CBilling].[dbo].[BillableEntity] be       where  be.BillableEntity_ID = bf.BillableEntity_ID   order by SystemCreateDate desc");

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
			EncounterFileRec.setUrl(result.getString("url"));

			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();
		closeSQLServerConnection();
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
				errorMessage = errorMessage +"∙" +  result.getString("billingClaimErrorMessage");

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

	public static void callEncounterProc(String wsdl, String processname) throws Exception {

		String responseString = "";
		String outputString = "";
		String wsEndPoint = wsdl;
		String runningprocesses = "0";
		openSoaInfraConnection();
		PreparedStatement ps = SOACon.prepareStatement(
				"Select count(1) counter from soareporting.soa_encounter_instances where state = 'Running' and replace(replace(replace(replace(replace(replace(replace(replace(composite,'_EncounterProcessing',''),'_EncounterService',''),'_EncounterProcess6400',''),'_EncounterProcess',''),'SyncSOAPreportingEncounterFileDataByEntity','Update Detail Data ' || initcap(title)),'SyncSOAReportingEncountersDataByEntity','Update Summary Data ' || initcap(title)),'SyncSOAReportingEncountersData','Update All Detail Data'),'SyncSOAPreportingEncounterFileData','Update All Summary Data') = '"
						+ processname + "'");

		ResultSet result = ps.executeQuery();

		while (result.next()) {

			runningprocesses = result.getString("counter");

		}
		result.close();
		ps.close();
		closeSoaInfraConnection();
		if (runningprocesses.equalsIgnoreCase("0")) {

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
			// Write the SOAP message formatted to the console.
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
		public List<EncounterValidationObject> getTest() throws Exception {

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		params.get("logID");
		String logID = params.get("logID");

		List<EncounterValidationObject> list = new ArrayList<EncounterValidationObject>();
		String conStr = System.getenv("MYSQLCONNSTR_MyShuttleDb");
		theConnection = DriverManager.getConnection(conStr);
		
		PreparedStatement ps = theConnection.prepareStatement( 
				"Select col1,col2 from table1");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterValidationObject EncounterFileRec = new EncounterValidationObject();
			
			EncounterFileRec.setHealthPlancode(result.getString("col1"));
			EncounterFileRec.setStopid(result.getString("col1"));
			list.add(EncounterFileRec);
		}
		
		result.close();
		ps.close();
		theConnection.close();
		return list;

	}
}