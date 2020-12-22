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
import com.amr.models.EncounterBAMObject;
import com.amr.models.EncounterCounts;
import com.amr.models.EncounterFile;
import com.amr.models.EncounterFileDetails;
import com.amr.models.EncounterMemberDataObject;
import com.amr.models.EncounterSOAInstances;
import com.amr.models.EncounterValidationObject;
import com.amr.models.EncounterValidationRule;
import com.amr.models.EncounterWSDLs;

@ManagedBean
@SessionScoped
public class TestingLogController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Connection SOACon = null;
	private List<EncounterFileDetails> encounterFileFilter;
	private String invalidcount = "";
	private String invalidcountCompare = "";
	public List<EncounterFile> encounterLogFiles = new ArrayList<EncounterFile>();
	public List<EncounterValidationRule> encounterValidationRules = new ArrayList<EncounterValidationRule>();
	public List<EncounterValidationObject> encounterValidationInvalids = new ArrayList<EncounterValidationObject>();
	public List<EncounterMemberDataObject> encounterMemberData = new ArrayList<EncounterMemberDataObject>();
	public List<EncounterValidationObject> encounterValidationInvalidsFilter = new ArrayList<EncounterValidationObject>();
	public List<EncounterSOAInstances> EncounterSOAInstancesCompare = new ArrayList<EncounterSOAInstances>();

	public List<EncounterMemberDataObject> getEncounterMemberData() {
		return encounterMemberData;
	}

	public void setEncounterMemberData(List<EncounterMemberDataObject> encounterMemberData) {
		this.encounterMemberData = encounterMemberData;
	}

	public String getInvalidcountCompare() {
		return invalidcountCompare;
	}

	public void setInvalidcountCompare(String invalidcountCompare) {
		this.invalidcountCompare = invalidcountCompare;
	}

	public List<EncounterSOAInstances> getEncounterSOAInstancesCompare() {
		return EncounterSOAInstancesCompare;
	}

	public void setEncounterSOAInstancesCompare(List<EncounterSOAInstances> encounterSOAInstancesCompare) {
		EncounterSOAInstancesCompare = encounterSOAInstancesCompare;
	}

	public String getInvalidcount() {
		return invalidcount;
	}

	public void setInvalidcount(String invalidcount) {
		this.invalidcount = invalidcount;
	}

	public List<EncounterValidationObject> getEncounterValidationInvalidsFilter() {
		return encounterValidationInvalidsFilter;
	}

	public void setEncounterValidationInvalidsFilter(
			List<EncounterValidationObject> encounterValidationInvalidsFilter) {
		this.encounterValidationInvalidsFilter = encounterValidationInvalidsFilter;
	}
	public String flowidCompare;
	public String billingidCompare;
	public String partnercodeCompare;
	public String pullcountCompare;
	public String claimcountCompare;
	public String filecountcountCompare;
	public String createdCompare;
	public String validaitoncountCompare;
	
	public String flowid;
	public String billingid;
	public String partnercode;
	public String pullcount;
	public String claimcount;
	public String filecountcount;
	public String created;
	public String validaitoncount;
	public String kickoff;

	public String getFlowidCompare() {
		return flowidCompare;
	}

	public void setFlowidCompare(String flowidCompare) {
		this.flowidCompare = flowidCompare;
	}

	public String getBillingidCompare() {
		return billingidCompare;
	}

	public void setBillingidCompare(String billingidCompare) {
		this.billingidCompare = billingidCompare;
	}

	public String getPartnercodeCompare() {
		return partnercodeCompare;
	}

	public void setPartnercodeCompare(String partnercodeCompare) {
		this.partnercodeCompare = partnercodeCompare;
	}

	public String getPullcountCompare() {
		return pullcountCompare;
	}

	public void setPullcountCompare(String pullcountCompare) {
		this.pullcountCompare = pullcountCompare;
	}

	public String getClaimcountCompare() {
		return claimcountCompare;
	}

	public void setClaimcountCompare(String claimcountCompare) {
		this.claimcountCompare = claimcountCompare;
	}

	public String getFilecountcountCompare() {
		return filecountcountCompare;
	}

	public void setFilecountcountCompare(String filecountcountCompare) {
		this.filecountcountCompare = filecountcountCompare;
	}

	public String getCreatedCompare() {
		return createdCompare;
	}

	public void setCreatedCompare(String createdCompare) {
		this.createdCompare = createdCompare;
	}

	public String getValidaitoncountCompare() {
		return validaitoncountCompare;
	}

	public void setValidaitoncountCompare(String validaitoncountCompare) {
		this.validaitoncountCompare = validaitoncountCompare;
	}

	public String getKickoff() {
		return kickoff;
	}

	public void setKickoff(String kickoff) {
		this.kickoff = kickoff;
	}

	public List<EncounterValidationObject> getEncounterValidationInvalids() {
		return encounterValidationInvalids;
	}

	public void setEncounterValidationInvalids(List<EncounterValidationObject> encounterValidationInvalids) {
		this.encounterValidationInvalids = encounterValidationInvalids;
	}

	public List<EncounterValidationRule> getEncounterValidationRules() {
		return encounterValidationRules;
	}

	public void setEncounterValidationRules(List<EncounterValidationRule> encounterValidationRules) {
		this.encounterValidationRules = encounterValidationRules;
	}

	public String getValidaitoncount() {
		return validaitoncount;
	}

	public void setValidaitoncount(String validaitoncount) {
		this.validaitoncount = validaitoncount;
	}

	public List<EncounterFile> getEncounterLogFiles() {
		return encounterLogFiles;
	}

	public void setEncounterLogFiles(List<EncounterFile> encounterLogFiles) {
		this.encounterLogFiles = encounterLogFiles;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public static Connection getSOACon() {
		return SOACon;
	}

	public static void setSOACon(Connection sOACon) {
		SOACon = sOACon;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getBillingid() {
		return billingid;
	}

	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}

	public String getPartnercode() {
		return partnercode;
	}

	public void setPartnercode(String partnercode) {
		this.partnercode = partnercode;
	}

	public String getPullcount() {
		return pullcount;
	}

	public void setPullcount(String pullcount) {
		this.pullcount = pullcount;
	}

	public String getClaimcount() {
		return claimcount;
	}

	public void setClaimcount(String claimcount) {
		this.claimcount = claimcount;
	}

	public String getFilecountcount() {
		return filecountcount;
	}

	public void setFilecountcount(String filecountcount) {
		this.filecountcount = filecountcount;
	}

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

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public void openSQLServerConnection() throws Exception {
		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SOAReporting");
		SOACon = dataSourceSOA.getConnection();

	}

	public void checkF5() throws Exception {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		String id = viewRoot.getViewId();
		if (previousPage != null && (previousPage.equals(id))) {
			of_GetData();
		}
		previousPage = id;

	}

	public void closeSQLServerConnection() throws Exception {
		SOACon.close();
	}

	public void of_GetData() throws Exception {
		List<EncounterFile> list = new ArrayList<EncounterFile>();
		openSQLServerConnection();
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		params.get("logID");
		String logID = params.get("logID");
		String compareID = params.get("compareID");

		PreparedStatement ps = SOACon.prepareStatement(
				"SELECT   ROWNUM,           X12payload,           Filename,           Directory,           Claimcount,           Flowid,           ( SELECT   X12payload               FROM   (SELECT     REPLACE( X12payload, '~', '~' || CHR( 13 ) || CHR( 10 ) ) X12payload, Filename, Directory, Claimcount, Flowid                           FROM   Soareporting.Instance_Flow_Payload                          WHERE   Flowid = '"
						+ compareID
						+ "'                       ORDER BY   Filename) )              Comparepayload    FROM   (SELECT     REPLACE( X12payload, '~', '~' || CHR( 13 ) || CHR( 10 ) ) X12payload, Filename, Directory, Claimcount, Flowid                FROM   Soareporting.Instance_Flow_Payload               WHERE   Flowid = "
						+ logID + "            ORDER BY   Filename)");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterFile EncounterFileRec = new EncounterFile();

			EncounterFileRec.setFlowid(result.getString("flowid"));
			EncounterFileRec.setFilename(result.getString("filename"));
			EncounterFileRec.setDirectory(result.getString("directory"));
			EncounterFileRec.setClaimcount(result.getString("claimcount"));
			EncounterFileRec.setPayload(result.getString("X12payload"));
			EncounterFileRec.setRownum(result.getString("rownum"));
			EncounterFileRec.setComparepayload(result.getString("Comparepayload"));

			list.add(EncounterFileRec);
			encounterLogFiles = list;
		}

		result.close();
		ps.close();

		PreparedStatement ps2 = SOACon.prepareStatement(
				"SELECT   KICKOFF_ID,LN.Flowid, LN.Billingid,              LN.Partnername,              LN.Instance,              LN.Pullcount,              LN.Created,              ( SELECT   SUM( Claimcount )                  FROM   Soareporting.Instance_Flow_Payload Ip                 WHERE   Ip.Flowid = LN.Flowid )                 Claimcount,              ( SELECT   COUNT( distinct filename )                  FROM   Soareporting.Instance_Flow_Payload Ip                 WHERE   Ip.Flowid = LN.Flowid )                 Filecount,              ( SELECT          COUNT( 1 )                         FROM   Soareporting.Instance_Info T,                                XMLTABLE( '/*:buildEncounterDetails/*:arg3/*:validation'                                          PASSING Xmltype( T.Validations )                                          COLUMNS Fieldname VARCHAR2( 200 ) PATH '/*:validation/*:fieldName/text()',                                                  Validationtype VARCHAR2( 200 ) PATH '/*:validationType/*:validationType',                                                  Validvalues VARCHAR2( 200 ) PATH '/*:validation/*:validValues' )                        WHERE   T.Flowid = LN.Flowid )                 Validaitoncount,              ( SELECT   COUNT( 1 )                  FROM   Soareporting.Validationmembers T                 WHERE   T.Flowid = LN.Flowid )                 Invalidcount       FROM   Soareporting.Instance_Info LN      WHERE   LN.Flowid = "
						+ logID);

		ResultSet result2 = ps2.executeQuery();

		while (result2.next()) {

			flowid = result2.getString("Flowid");
			billingid = result2.getString("Billingid");
			partnercode = result2.getString("Partnername");
			pullcount = result2.getString("Pullcount");
			claimcount = result2.getString("claimcount");
			filecountcount = result2.getString("Filecount");
			created = result2.getString("Created");
			validaitoncount = result2.getString("validaitoncount");
			invalidcount = result2.getString("Invalidcount");
			kickoff = result2.getString("KICKOFF_ID");
		}

		result2.close();
		ps2.close();

		PreparedStatement psCompare = SOACon.prepareStatement(
				"SELECT   KICKOFF_ID,LN.Flowid, LN.Billingid,              LN.Partnername,              LN.Instance,              LN.Pullcount,              LN.Created,              ( SELECT   SUM( Claimcount )                  FROM   Soareporting.Instance_Flow_Payload Ip                 WHERE   Ip.Flowid = LN.Flowid )                 Claimcount,              ( SELECT   COUNT( distinct filename )                  FROM   Soareporting.Instance_Flow_Payload Ip                 WHERE   Ip.Flowid = LN.Flowid )                 Filecount,              ( SELECT          COUNT( 1 )                         FROM   Soareporting.Instance_Info T,                                XMLTABLE( '/*:buildEncounterDetails/*:arg3/*:validation'                                          PASSING Xmltype( T.Validations )                                          COLUMNS Fieldname VARCHAR2( 200 ) PATH '/*:validation/*:fieldName/text()',                                                  Validationtype VARCHAR2( 200 ) PATH '/*:validationType/*:validationType',                                                  Validvalues VARCHAR2( 200 ) PATH '/*:validation/*:validValues' )                        WHERE   T.Flowid = LN.Flowid )                 Validaitoncount,              ( SELECT   COUNT( 1 )                  FROM   Soareporting.Validationmembers T                 WHERE   T.Flowid = LN.Flowid )                 Invalidcount       FROM   Soareporting.Instance_Info LN      WHERE   LN.Flowid = "
						+ compareID);
		flowidCompare = "";
		billingidCompare = "";
		partnercodeCompare = "";
		pullcountCompare = "";
		claimcountCompare = "";
		filecountcountCompare = "";
		createdCompare = "";
		validaitoncountCompare = "";
		invalidcountCompare = "";
		ResultSet resultCompare = psCompare.executeQuery();

		while (resultCompare.next()) {

			flowidCompare = resultCompare.getString("Flowid");
			billingidCompare = resultCompare.getString("Billingid");
			partnercodeCompare = resultCompare.getString("Partnername");
			pullcountCompare = resultCompare.getString("Pullcount");
			claimcountCompare = resultCompare.getString("claimcount");
			filecountcountCompare = resultCompare.getString("Filecount");
			createdCompare = resultCompare.getString("Created");
			validaitoncountCompare = resultCompare.getString("validaitoncount");
			invalidcountCompare = resultCompare.getString("Invalidcount");
			
		}

		resultCompare.close();
		psCompare.close();
		List<EncounterValidationRule> validationlist = new ArrayList<EncounterValidationRule>();

		PreparedStatement psValidations = SOACon.prepareStatement(
				"SELECT           Fieldname, Validationtype, Validvalues           FROM   Soareporting.Instance_Info T,                  XMLTABLE( '/*:buildEncounterDetails/*:arg3/*:validation'                            PASSING Xmltype( T.Validations )                            COLUMNS Fieldname VARCHAR2( 200 ) PATH '/*:validation/*:fieldName/text()',                                    Validationtype VARCHAR2( 200 ) PATH '/*:validationType/*:validationType',                                    Validvalues VARCHAR2( 200 ) PATH '/*:validation/*:validValues' )          WHERE   T.Flowid = "
						+ logID + " order by 1,2 asc");

		ResultSet validationResult = psValidations.executeQuery();

		while (validationResult.next()) {
			EncounterValidationRule EncounterFileRec = new EncounterValidationRule();

			EncounterFileRec.setName(validationResult.getString("Fieldname"));
			EncounterFileRec.setType(validationResult.getString("Validationtype"));
			EncounterFileRec.setValues(validationResult.getString("Validvalues"));

			validationlist.add(EncounterFileRec);
			encounterValidationRules = validationlist;
		}

		validationResult.close();
		psValidations.close();

		List<EncounterValidationObject> validationInvalidsList = new ArrayList<EncounterValidationObject>();

		PreparedStatement psValidationsInvalids = SOACon.prepareStatement(
				"SELECT           FLOWID, HEALTHPLANCODE, STOPID, BILLINGCLAIMERRORMESSAGE, HCPC,         DIAGNOSISCODE, BILLINGMODIFIER, MILEAGE, TRANSPORTATIONPROVIDER, PROVIDERPAYDATE,         FIRSTNAME, LASTNAME, POS, TRIPMODIFIER, TRIPMODIFIER2,         BILLAMOUNT, PAIDAMOUNT, APPOINTMENTDATE, SERVICEDATEFROM, SERVICEDATETO,         MEMBERID, PUNAME, PUADDRESS1, PUADDRESS2, PUCITY,         PUCOUNTY, PUSTATE, PUZIPCODE, DESTINATIONNAME, DESTINATIONADDRESS1,         DESTINATIONADDRESS2, DESTINATIONCITY, DESTINATIONCOUNTY, DESTINATIONSTATE, DESTINATIONZIPCODE,         MEMBERADDRESS1, MEMBERADDRESS2, MEMBERCITY, MEMBERCOUNTY, MEMBERSTATE,         MEMBERZIPCODE, CREATED  from soareporting.Validationmembers  where flowid = "
						+ logID + " order by 1,2 asc");

		ResultSet validationInvalidsResult = psValidationsInvalids.executeQuery();

		while (validationInvalidsResult.next()) {
			EncounterValidationObject EncounterFileRec = new EncounterValidationObject();
			EncounterFileRec.setHealthPlancode(validationInvalidsResult.getString("HEALTHPLANCODE"));
			EncounterFileRec.setStopid(validationInvalidsResult.getString("STOPID"));
			EncounterFileRec.sethCPC(validationInvalidsResult.getString("hCPC"));
			EncounterFileRec.setDiagnosisCode(validationInvalidsResult.getString("diagnosisCode"));
			EncounterFileRec.setBillingModifier(validationInvalidsResult.getString("billingModifier"));
			EncounterFileRec.setMileage(validationInvalidsResult.getString("mileage"));
			EncounterFileRec.setTransportationProvider(validationInvalidsResult.getString("transportationProvider"));
			EncounterFileRec.setProviderPayDate(validationInvalidsResult.getString("providerPayDate"));
			EncounterFileRec.setFirstName(validationInvalidsResult.getString("firstName"));
			EncounterFileRec.setLastName(validationInvalidsResult.getString("lastName"));
			EncounterFileRec.setpOS(validationInvalidsResult.getString("pOS"));
			EncounterFileRec.setTripModifier(validationInvalidsResult.getString("tripModifier"));
			EncounterFileRec.setTripModifier2(validationInvalidsResult.getString("tripModifier2"));
			EncounterFileRec.setBillAmount(validationInvalidsResult.getString("billAmount"));
			EncounterFileRec.setPaidAmount(validationInvalidsResult.getString("paidAmount"));
			EncounterFileRec.setAppointmentDate(validationInvalidsResult.getString("appointmentDate"));
			EncounterFileRec.setServiceDateFrom(validationInvalidsResult.getString("serviceDateFrom"));
			EncounterFileRec.setServiceDateTo(validationInvalidsResult.getString("serviceDateTo"));
			EncounterFileRec.setMemberID(validationInvalidsResult.getString("memberID"));
			EncounterFileRec.setpUName(validationInvalidsResult.getString("pUName"));
			EncounterFileRec.setpUAddress1(validationInvalidsResult.getString("pUAddress1"));
			EncounterFileRec.setpUAddress2(validationInvalidsResult.getString("pUAddress2"));
			EncounterFileRec.setpUCity(validationInvalidsResult.getString("pUCity"));
			EncounterFileRec.setpUCounty(validationInvalidsResult.getString("pUCounty"));
			EncounterFileRec.setpUState(validationInvalidsResult.getString("pUState"));
			EncounterFileRec.setpUZIPCode(validationInvalidsResult.getString("pUZIPCode"));
			EncounterFileRec.setDestinationName(validationInvalidsResult.getString("destinationName"));
			EncounterFileRec.setDestinationAddress1(validationInvalidsResult.getString("destinationAddress1"));
			EncounterFileRec.setDestinationAddress2(validationInvalidsResult.getString("destinationAddress2"));
			EncounterFileRec.setDestinationCity(validationInvalidsResult.getString("destinationCity"));
			EncounterFileRec.setDestinationCounty(validationInvalidsResult.getString("destinationCounty"));
			EncounterFileRec.setDestinationState(validationInvalidsResult.getString("destinationState"));
			EncounterFileRec.setDestinationZIPCode(validationInvalidsResult.getString("destinationZIPCode"));
			EncounterFileRec.setMemberAddress1(validationInvalidsResult.getString("memberAddress1"));
			EncounterFileRec.setMemberAddress2(validationInvalidsResult.getString("memberAddress2"));
			EncounterFileRec.setMemberCity(validationInvalidsResult.getString("memberCity"));
			EncounterFileRec.setMemberCounty(validationInvalidsResult.getString("memberCounty"));
			EncounterFileRec.setMemberState(validationInvalidsResult.getString("memberState"));
			EncounterFileRec.setMemberZIPCode(validationInvalidsResult.getString("memberZIPCode"));
			EncounterFileRec
					.setBillingClaimErrorMessage(validationInvalidsResult.getString("billingClaimErrorMessage"));

			validationInvalidsList.add(EncounterFileRec);
			encounterValidationInvalids = validationInvalidsList;
		}

		validationInvalidsResult.close();
		psValidationsInvalids.close();

		List<EncounterMemberDataObject> validationMembersDataList = new ArrayList<EncounterMemberDataObject>();

		PreparedStatement psMemberData = SOACon.prepareStatement(
				"SELECT   Emscbilling837interface_Id,          Healthplancode,          Stopid,          Appointmentdate,          Mileage,          Actualprice,          Memberid,          Lastname,          Firstname,          Middleinitial,          Dob,          Encryptedssn,          Groupid,          Age,          Memberaddress1,          Memberaddress2,          Membercity,          Membercounty,          Memberstate,          Memberzipcode,          Memberphonenumber,          Puaddress1,          Puaddress2,          Pucity,          Pucounty,          Pustate,          Puzipcode,          Destinationaddress1,          Destinationaddress2,          Destinationcity,          Destinationcounty,          Destinationstate,          Destinationzipcode,          Finallos,          Hcpc,          Transportationprovider,          Apvenderid,          Mrbtypeid,          Mrbtypename,          Status,          Finalstatusdate,          Confirmationnumber,          Diagnosiscode,          Tripmodifier,          Billingmodifier,          Costplusamount,          Billamount,          Pos,          Netstop_Id,          Netmember_Id,          Transportationprovider_Id,          Addressmember_Id,          Addresspickup_Id,          Addressdestination_Id,          Actuallegcostamount,          Reportedlegcostamount,          Providerpayableamount,          Providerreceivedamount,          Providerpaydate,          Emscpayableamount,          Emscreceivedamount,          Createdbyid,          Modifiedbyid,          Createdate,          Modifieddate,          Externalfilegenerateddate,          Isactive,          Gender,          Isextracted,          Extracteddate,          Tripnumber,          Programtype,          Memberextendedcode1,          Memberextendedcode2,          Memberextendedcode3,          Memberextendedcode4,          Memberextendedcode5,          Memberextendedcode6,          Memberextendedcode7,          Transportationprovideraddress1,          Transportationprovideraddress2,          Transportationprovidercity,          Transportationproviderstate,          Transportationproviderzip,          Puname,          Destinationname,          Casenumber,          Servicedateto,          Servicedatefrom,          Tripmodifier2,          Serviceunits,          Unitprice,          Certificationcode,          Acknowledgedate,          Completeddate,          Transportationproviderfedid,          Senttooracle,          Oraclesentdate,          Policyholdernetmemberid,          Policyholdermemberid,          Policyholderfirstname,          Policyholdermiddleinitial,          Policyholderlastname,          Policyholderaddress1,          Policyholderaddress2,          Policyholdercity,          Policyholderstate,          Policyholderzipcode,          Policyholderdob,          Policyholdergender,          Policyholdergroupid,          Policyholdergroupname,          Policyholderpolicyno,          Relationshipcode,          Transportationprovidernpi,          Transportationprovidermedicaid,          Tripcreatedate,          Addlpassengercount,          Schedulepickuptime,          Appointmenttime,          Membermedicaidid,          Membermedicareid,          Memberalternativeid,          Pickuptime,          Destinationtime,          Tripoutcome,          Transportationproviderzip4,          Clientauthcode,          Case_Expenselineitemdetails_Id,          Drivername,          Driverlicensenumber,          Driverlicensestate,          A2cdriverid,          Providernpi,          Providertaxid,          Providertaxonomycode,          Providerstateproviderid,          Equipmentandspecialneeds,          Vehicleidnumber,          Performingprovidername,          Paymentstatus_Id,          Planpaiddate,          Checknumber,          Reasoncode,          Paidamount,          Reasondescription,          Resubmissiondate,          Updatestatus,          A2cprocessingstatus,          Planstatuscode,          Processeddate,          Systemcreatedate,          Claimresubmissionperiod,          Clientclaimnumber,          Altclientclaimnumber,          Processingstatus_Id,          A2cclaimnumber,          Claimtypeid,          Encrypteddriverssn,          Cleareddate,          Netstopattribute_Arlineitem_Id,          Extracteddate2,          Externalfilegenerateddate2,          Acknowledgedate2,          Completeddate2,          Processingstatus_Id2,          Processeddate2,          Planstatuscode2,          Reasoncode2,          Reasondescription2,          Encryptedtpfedid,          Changedbyusername,          Changereason,          Billingrepository_Id,          Flowid   FROM   Soareporting.Billing837interface  WHERE   flowid in "
						+ logID);

		ResultSet memberDataResult = psMemberData.executeQuery();

		while (memberDataResult.next()) {
			EncounterMemberDataObject EncounterMemberRec = new EncounterMemberDataObject();
			EncounterMemberRec.setHealthPlancode(memberDataResult.getString("HEALTHPLANCODE"));
			EncounterMemberRec.setStopid(memberDataResult.getString("STOPID"));
			EncounterMemberRec.sethCPC(memberDataResult.getString("hCPC"));
			EncounterMemberRec.setDiagnosisCode(memberDataResult.getString("diagnosisCode"));
			EncounterMemberRec.setBillingModifier(memberDataResult.getString("billingModifier"));
			EncounterMemberRec.setMileage(memberDataResult.getString("mileage"));
			EncounterMemberRec.setTransportationProvider(memberDataResult.getString("transportationProvider"));
			EncounterMemberRec.setProviderPayDate(memberDataResult.getString("providerPayDate"));
			EncounterMemberRec.setFirstName(memberDataResult.getString("firstName"));
			EncounterMemberRec.setLastName(memberDataResult.getString("lastName"));
			EncounterMemberRec.setpOS(memberDataResult.getString("pOS"));
			EncounterMemberRec.setTripModifier(memberDataResult.getString("tripModifier"));
			EncounterMemberRec.setTripModifier2(memberDataResult.getString("tripModifier2"));
			EncounterMemberRec.setBillAmount(memberDataResult.getString("billAmount"));
			EncounterMemberRec.setPaidAmount(memberDataResult.getString("paidAmount"));
			EncounterMemberRec.setAppointmentDate(memberDataResult.getString("appointmentDate"));
			EncounterMemberRec.setServiceDateFrom(memberDataResult.getString("serviceDateFrom"));
			EncounterMemberRec.setServiceDateTo(memberDataResult.getString("serviceDateTo"));
			EncounterMemberRec.setMemberID(memberDataResult.getString("memberID"));
			EncounterMemberRec.setpUName(memberDataResult.getString("pUName"));
			EncounterMemberRec.setpUAddress1(memberDataResult.getString("pUAddress1"));
			EncounterMemberRec.setpUAddress2(memberDataResult.getString("pUAddress2"));
			EncounterMemberRec.setpUCity(memberDataResult.getString("pUCity"));
			EncounterMemberRec.setpUCounty(memberDataResult.getString("pUCounty"));
			EncounterMemberRec.setpUState(memberDataResult.getString("pUState"));
			EncounterMemberRec.setpUZIPCode(memberDataResult.getString("pUZIPCode"));
			EncounterMemberRec.setDestinationName(memberDataResult.getString("destinationName"));
			EncounterMemberRec.setDestinationAddress1(memberDataResult.getString("destinationAddress1"));
			EncounterMemberRec.setDestinationAddress2(memberDataResult.getString("destinationAddress2"));
			EncounterMemberRec.setDestinationCity(memberDataResult.getString("destinationCity"));
			EncounterMemberRec.setDestinationCounty(memberDataResult.getString("destinationCounty"));
			EncounterMemberRec.setDestinationState(memberDataResult.getString("destinationState"));
			EncounterMemberRec.setDestinationZIPCode(memberDataResult.getString("destinationZIPCode"));
			EncounterMemberRec.setMemberAddress1(memberDataResult.getString("memberAddress1"));
			EncounterMemberRec.setMemberAddress2(memberDataResult.getString("memberAddress2"));
			EncounterMemberRec.setMemberCity(memberDataResult.getString("memberCity"));
			EncounterMemberRec.setMemberCounty(memberDataResult.getString("memberCounty"));
			EncounterMemberRec.setMemberState(memberDataResult.getString("memberState"));
			EncounterMemberRec.setMemberZIPCode(memberDataResult.getString("memberZIPCode"));

			validationMembersDataList.add(EncounterMemberRec);
			encounterMemberData = validationMembersDataList;
		}

		memberDataResult.close();
		psMemberData.close();

		List<EncounterSOAInstances> listInstances = new ArrayList<EncounterSOAInstances>();

		PreparedStatement psInstances = SOACon.prepareStatement(
				"SELECT     ln.name,ln.KICKOFF_ID,LN.Flowid,            LN.Billingid,            LN.Partnername composite,            LN.Instance,            LN.Pullcount,            'Complated' state,             LN.Created Created_Time,            ( SELECT   SUM( Claimcount )                FROM   Soareporting.Instance_Flow_Payload Ip               WHERE   Ip.Flowid = LN.Flowid )               claimcount,            ( SELECT   COUNT( distinct filename )                FROM   Soareporting.Instance_Flow_Payload Ip               WHERE   Ip.Flowid = LN.Flowid )               Filecount     FROM   Soareporting.Instance_Info LN WHERE   Kickoff_Id = "
						+ kickoff + " and flowid != " + logID + " ORDER BY   1,3 DESC");

		ResultSet instancesresult = psInstances.executeQuery();

		while (instancesresult.next()) {

			EncounterSOAInstances EncounterFileRec = new EncounterSOAInstances();

			EncounterFileRec.setCreated(instancesresult.getString("CREATED_TIME"));
			EncounterFileRec.setFlowid(instancesresult.getString("flowid"));
			EncounterFileRec.setComposite(instancesresult.getString("composite"));
			EncounterFileRec.setState(instancesresult.getString("state"));
			EncounterFileRec.setClaimcount(instancesresult.getString("claimcount"));
			EncounterFileRec.setFilecount(instancesresult.getString("Filecount"));
			EncounterFileRec.setPullcount(instancesresult.getString("pullcount"));
			EncounterFileRec.setKick_off(instancesresult.getString("KICKOFF_ID"));
			EncounterFileRec.setName(instancesresult.getString("name"));

			listInstances.add(EncounterFileRec);
		}
		EncounterSOAInstancesCompare = listInstances;
		instancesresult.close();
		psInstances.close();

		closeSQLServerConnection();
	}

	@PostConstruct
	public void reset() throws Exception {
		of_GetData();
	}

	@PreDestroy
	public void cleanUp() throws Exception {

	}

}