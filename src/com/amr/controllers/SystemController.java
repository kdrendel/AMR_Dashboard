package com.amr.controllers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.xml.sax.SAXException;

import com.amr.models.EligibilityFileRunRecord;
import com.amr.models.Encounter;
import com.amr.models.EncounterBAMObject;
import com.amr.models.EncounterCounts;
import com.amr.models.EncounterReadyCounts;
import com.amr.models.EncounterSOAInstances;
import com.amr.models.EncounterWSDLs;
import com.amr.models.EncounterWeekly;

@ManagedBean
@SessionScoped
public class SystemController implements Serializable {
	public String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	private static final long serialVersionUID = 1L;
	private Connection SOACon = null;
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
	private List<EncounterWSDLs> encounterExecuteList = null;
	private List<EncounterReadyCounts> encounterRecordsSQLServer = null;
	private List<EncounterSOAInstances> encounterCurrentSOAInstances = null;
	private List<EncounterBAMObject> encounterBAMObjectInstances = null;

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
				System.out.println("Check connection Closed");
			}
			{
				System.out.println("Check connection Open");
				closeSoaInfraConnection();
			}
			openSoaInfraConnection();
			getWSDLList();
			getInstanceList();
			closeSoaInfraConnection();
		}
		previousPage = id;

	}

	public void openSoaInfraConnection() throws Exception {
		System.out.println("Open SOA Connection");
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SOAReporting");
		SOACon = dataSourceSOA.getConnection();

	}

	public void closeSQLServerConnection() throws Exception {
		System.out.println("Close SQL Server Connection");
		StatsCon.close();
	}

	public void openSQLServerConnection() throws Exception {
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SpiceApps");
		StatsCon = dataSourceSOA.getConnection();
		System.out.println("Open SQL Server Connection");
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
		closeSQLServerConnection();
		encounterRecordsSQLServer = list;
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
				"Select Error_Message,to_char(CREATED_TIME,'MM-DD HH24:MI:SS') CREATED_TIME,updated_time,flow_id,replace(replace(replace(replace(composite,'_EncounterProcessing',''),'_EncounterService',''),'_EncounterProcess6400',''),'_EncounterProcess','') composite,state from soa_encounter_instances");

		ResultSet result = ps.executeQuery();

		while (result.next()) {

			EncounterSOAInstances EncounterFileRec = new EncounterSOAInstances();
			EncounterFileRec.setCreated(result.getString("CREATED_TIME"));
			EncounterFileRec.setUpdated(result.getString("updated_time"));
			EncounterFileRec.setFlowid(result.getString("flow_id"));
			EncounterFileRec.setComposite(result.getString("composite"));
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

		PreparedStatement ps = SOACon.prepareStatement( 
				"SELECT   to_char(A.Created_Date,'MM-DD HH24:MI:SS')  Created_Date,           A.Claims_Count,           decode( B.State,'MSG_ERROR',a.file_name,B.Protocol_Message_Id) file_name,            B.Url,           decode(A.Claims_Count,0,'No Claims', B.State) File_Status,           A.Plan_Name,                                B.ERROR_TEXT                                FROM    (SELECT     GREATEST( Dataobject_Created, Dataobject_Modified ) Created_Date, Claims_Count, File_Name, File_Status, Plan_Name                FROM   Spiceprod_Soainfra.Beam_View_57            ORDER BY   GREATEST( Dataobject_Created, Dataobject_Modified ) DESC) A,           ( SELECT     B.Receiver_Name,                        B.Payload_Name,                        B.State,                        TO_CHAR( E.Error_Text_Clob ) Error_Text_1,                        NVL( E.ERROR_TEXT, W.ERROR_TEXT ) ERROR_TEXT,                        NVL( E.Error_Description, W.Error_Description ) Error_Description,                        W.Protocol_Message_Id,                        Url                 FROM   Spiceprod_Soainfra.B2b_Wire_Message W,                        Spiceprod_Soainfra.B2b_Business_Message B,                        Spiceprod_Soainfra.B2b_Ext_Business_Message E                WHERE   E.Id(+) = B.Ext_Business_Message AND W.Id(+) = B.Wire_Message AND B.Payload_Name IS NOT NULL             ORDER BY   W.Created DESC ) B   WHERE   B.Payload_Name(+) = A.File_Name AND B.Receiver_Name(+) = A.Plan_Name and rownum < 100");
				//"SELECT   to_char(A.Created_Date,'MM-DD HH24:MI:SS')  Created_Date,           A.Claims_Count,           decode( B.State,'MSG_ERROR',a.file_name,B.Protocol_Message_Id) file_name,            B.Url,           decode(A.Claims_Count,0,'No Claims', B.State) File_Status,           A.Plan_Name,                                B.ERROR_TEXT                                FROM    (SELECT     GREATEST( Dataobject_Created, Dataobject_Modified ) Created_Date, Claims_Count, File_Name, File_Status, Plan_Name                FROM   Spicetest_Soainfra.Beam_View_67            ORDER BY   GREATEST( Dataobject_Created, Dataobject_Modified ) DESC) A,           ( SELECT     B.Receiver_Name,                        B.Payload_Name,                        B.State,                        TO_CHAR( E.Error_Text_Clob ) Error_Text_1,                        NVL( E.ERROR_TEXT, W.ERROR_TEXT ) ERROR_TEXT,                        NVL( E.Error_Description, W.Error_Description ) Error_Description,                        W.Protocol_Message_Id,                        Url                 FROM   Spicetest_Soainfra.B2b_Wire_Message W,                        Spicetest_Soainfra.B2b_Business_Message B,                        Spicetest_Soainfra.B2b_Ext_Business_Message E                WHERE   E.Id(+) = B.Ext_Business_Message AND W.Id(+) = B.Wire_Message AND B.Payload_Name IS NOT NULL             ORDER BY   W.Created DESC ) B   WHERE   B.Payload_Name(+) = A.File_Name AND B.Receiver_Name(+) = A.Plan_Name and rownum < 100");
	
	
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterBAMObject EncounterFileRec = new EncounterBAMObject();
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

		encounterBAMObjectInstances = list;
	}

	public void getWSDLList() throws Exception {

		List<EncounterWSDLs> list = new ArrayList<EncounterWSDLs>();
		getEncounterDataSQLServer();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select id,encounter_name, Substr(substr(encounter_wsdl,instr(encounter_wsdl,'default/') + 8),1,instr(substr(encounter_wsdl,instr(encounter_wsdl,'default/') + 8),'_') - 1 ) wsdlDisplay, encounter_wsdl,Encounter_Entityid, Comments from Soa_Encounter_Wsdls");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWSDLs EncounterFileRec = new EncounterWSDLs();
			EncounterFileRec.setId(result.getString("id"));

			EncounterFileRec.setWsdl(result.getString("encounter_wsdl"));
			EncounterFileRec.setBillingid(result.getString("Encounter_Entityid"));
			EncounterFileRec.setComments(result.getString("Comments"));
			EncounterFileRec.setWsdlDisplay(result.getString("wsdlDisplay"));
			EncounterFileRec.setReadyitems(getBillingLookup(result.getString("Encounter_Entityid")));
			EncounterFileRec.setName(getTPName(result.getString("Encounter_Entityid")));
			list.add(EncounterFileRec);
		}
		result.close();
		ps.close();

		encounterExecuteList = list;
	}

	public void closeSoaInfraConnection() throws Exception {
		SOACon.close();
		System.out.println("Close SOA Connection");
	}

	public void flagforretrieval() throws Exception {
		if (SOACon.isClosed()) {
			System.out.println("Check connection Closed");
		}
		{
			System.out.println("Check connection Open");
			closeSoaInfraConnection();
		}
		openSoaInfraConnection();
		getInstanceList();
		getBAMLogs();

		closeSoaInfraConnection();
		// System.out.println("Out flagforretrieval");
	}

	@PostConstruct
	public void reset() throws Exception {
		try {
			userid = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			openSoaInfraConnection();
			getWSDLList();
			getInstanceList();
			getBAMLogs();
			closeSoaInfraConnection();
		} catch (Exception e) {
			e.printStackTrace();
			// or handle more gracefully
		}

	}

	public static void callEncounterProc(String wsdl) throws IOException, SAXException {

		String responseString = "";
		String outputString = "";
		String wsEndPoint = wsdl;
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

	}

	@PreDestroy
	public void cleanUp() throws Exception {
		System.out.println("Clean Up!!!!");
	}
}