package com.amr.controllers;

import java.io.File;
import java.io.IOException;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.io.FileUtils;

import com.amr.models.Encounter;
import com.amr.models.EncounterCounts;
import com.amr.models.EncounterWeekly;

@ManagedBean
@SessionScoped
public class EncounterController implements Serializable {
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
	private String doNotRetrieve = "N";
	private String lastUpdatedString = "";


	public String getLastUpdatedString() {
		return lastUpdatedString;
	}

	public void setLastUpdatedString(String lastUpdatedString) {
		this.lastUpdatedString = lastUpdatedString;
	}

	public String getDoNotRetrieve() {
		return doNotRetrieve;
	}

	public void setDoNotRetrieve(String doNotRetrieve) {
		this.doNotRetrieve = doNotRetrieve;
	}

	public String getCurrentPartner() {
		return currentPartner;
	}

	public void setCurrentPartner(String currentPartner) {
		this.currentPartner = currentPartner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<Encounter> encounterMenu = null;
	private List<Encounter> partnerEncounterList = null;
	private List<EncounterWeekly> partnerEncounterWeeklyList = null;
	private List<EncounterWeekly> encounterWeeklyList = null;
	private List<EncounterWeekly> encounterGraphList = null;
	private List<EncounterWeekly> encounterGraphPartnerList = null;
	private String dateFormatDisplay = "YYYY-MM-DD";
	private String dateFormatCalc = "YYYYMMDD";
	private String dateFormatMethod = "To_Char";
	private String displayName = "Day";
	private String dateFormatID = "1";

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDateFormatID() {
		return dateFormatID;
	}

	public void setDateFormatID(String dateFormatID) {
		this.dateFormatID = dateFormatID;
	}

	public String getDateFormatDisplay() {
		return dateFormatDisplay;
	}

	public String getDateFormatMethod() {
		return dateFormatMethod;
	}

	public void setDateFormatMethod(String dateFormatMethod) {
		this.dateFormatMethod = dateFormatMethod;
	}

	public void setDateFormatDisplay(String dateFormatDisplay) {
		this.dateFormatDisplay = dateFormatDisplay;
	}

	public String getDateFormatCalc() {
		return dateFormatCalc;
	}

	public void setDateFormatCalc(String dateFormatCalc) {
		this.dateFormatCalc = dateFormatCalc;
	}

	public List<EncounterWeekly> getEncounterGraphPartnerList() {
		return encounterGraphPartnerList;
	}

	public void setEncounterGraphPartnerList(List<EncounterWeekly> encounterGraphPartnerList) {
		this.encounterGraphPartnerList = encounterGraphPartnerList;
	}

	public List<EncounterWeekly> getEncounterGraphList() {
		return encounterGraphList;
	}

	public void setEncounterGraphList(List<EncounterWeekly> encounterGraphList) {
		this.encounterGraphList = encounterGraphList;
	}

	public List<EncounterWeekly> getEncounterWeeklyList() {
		return encounterWeeklyList;
	}

	public void setEncounterWeeklyList(List<EncounterWeekly> encounterWeeklyList) {
		this.encounterWeeklyList = encounterWeeklyList;
	}

	public List<EncounterWeekly> getPartnerEncounterWeeklyList() {
		return partnerEncounterWeeklyList;
	}

	public void setPartnerEncounterWeeklyList(List<EncounterWeekly> partnerEncounterWeeklyList) {
		this.partnerEncounterWeeklyList = partnerEncounterWeeklyList;
	}

	public List<Encounter> getPartnerEncounterList() {
		return partnerEncounterList;
	}

	public void setPartnerEncounterList(List<Encounter> partnerEncounterList) {
		this.partnerEncounterList = partnerEncounterList;
	}

	private String previousPage = null;

	public Connection getSOACon() {
		return SOACon;
	}

	public void setSOACon(Connection sOACon) {
		SOACon = sOACon;
	}

	public List<Encounter> getEncounterMenu() {
		return encounterMenu;
	}

	public void setEncounterMenu(List<Encounter> encounterMenu) {
		this.encounterMenu = encounterMenu;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public void checkF5() throws Exception {
		// System.out.println("In checkF5");
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		String id = viewRoot.getViewId();
		if (previousPage != null && (previousPage.equals(id))) {
			if (doNotRetrieve.equalsIgnoreCase("Y")) {
				doNotRetrieve = "N";
			} else {
				openSoaInfraConnection();				
				getEncountersList();				
				getPartnerEncountersList();
				getPartnerWeeklyEncountersList();
				getWeeklyEncountersList();
				getWeeklyGraphEncountersList();
				getWeeklyParnterGraphEncountersList();
				closeSoaInfraConnection();
			}
		}
		previousPage = id;

	}
	
	public void getLastUpdated() throws SQLException {
		
		PreparedStatement ps = SOACon.prepareStatement(
				"Select Soareporting.F_Get_Last_UpdateName('" + currentPartner + "') Last_Updated from dual");
		ResultSet result = ps.executeQuery();
		
		while (result.next()) {
			lastUpdatedString = result.getString("Last_Updated");
					}
		ps.close();
		result.close();

	}
	
	public void setPartnerInfo() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String filter = (String) map.get("planName");
		currentPartner = filter;
		doNotRetrieve = "Y";
		openSoaInfraConnection();
		getEncountersList();		
		getPartnerEncountersList();
		getPartnerWeeklyEncountersList();
		getWeeklyEncountersList();
		getWeeklyGraphEncountersList();
		getWeeklyParnterGraphEncountersList();
		closeSoaInfraConnection();
		System.out.println("In SetPartner " + currentPartner);

	}

	public void setPartner() throws SQLException {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String filter = (String) map.get("planName");
		currentPartner = filter;
		

	}

	public void getPartnerEncountersList() throws SQLException {
		List<Encounter> list = new ArrayList<Encounter>();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select Entityname,round((nvl(F_Get_File_Counts(BILLINGFILEID,'Accepted'),0) + nvl(F_Get_File_Counts(BILLINGFILEID,'Non-Billable'),0)) / DECODE( Tripcountgrouped, 0, 1, Tripcountgrouped )  * 100,2)  percentage, decode(nvl(F_Get_File_Counts(BILLINGFILEID,'Accepted'),0) + nvl(F_Get_File_Counts(BILLINGFILEID,'Non-Billable'),0),Tripcountgrouped,'Complete','Incomplete' ) completed,F_Get_File_Counts(BILLINGFILEID,'Accepted') Accepted_cnt,F_Get_File_Counts(BILLINGFILEID,'Ready to Submit') Ready_cnt,F_Get_File_Counts(BILLINGFILEID,'Submitted') Submitted_cnt,F_Get_File_Counts(BILLINGFILEID,'SOA Error') SOAError_cnt,F_Get_File_Counts(BILLINGFILEID,'Rejected') Rejected_cnt,F_Get_File_Counts(BILLINGFILEID,'No Status Given') nostatus_cnt,F_Get_File_Counts(BILLINGFILEID,'Non-Billable') nonbilled_cnt,BILLINGFILEID,entityName planname ,to_char(batchdate,'YYYY-MM-DD') batchdate , billingfileid logid,filename ,  to_char(mindos,'YYYY-MM-DD') mindos , to_char(maxdos,'YYYY-MM-DD') maxdos , to_char(periodstart,'YYYY-MM-DD') periodstart , to_char(periodend,'YYYY-MM-DD') periodend , totalfare , tripcount ,totalunits ,tripcountgrouped ,batchtype from soareporting.soa_encounter_file where   (('"
						+ currentPartner + "' = 'All Plans' ) or (entityname = '" + currentPartner + "'))");
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			Encounter EncounterRec = new Encounter();
			EncounterRec.setPlanName(result.getString("planName"));
			EncounterRec.setLogID(result.getString("logID"));
			EncounterRec.setFileName(result.getString("fileName"));
			EncounterRec.setMinDOS(result.getString("MinDOS"));
			EncounterRec.setMaxDOS(result.getString("MaxDOS"));
			EncounterRec.setPeriodStart(result.getString("PeriodStart"));
			EncounterRec.setPeriodEnd(result.getString("PeriodEnd"));
			EncounterRec.setTotalFare(result.getString("TotalFare"));
			EncounterRec.setTripCount(result.getString("TripCount"));
			EncounterRec.setTotalUnits(result.getString("TotalUnits"));
			EncounterRec.setTripCountGrouped(result.getString("tripCountGrouped"));
			EncounterRec.setBatchDate(result.getString("BatchDate"));
			EncounterRec.setBatchType(result.getString("BatchType"));
			EncounterRec.setAcceptedCnt(result.getInt("Accepted_cnt"));
			EncounterRec.setReadyCnt(result.getInt("Ready_cnt"));
			EncounterRec.setSubmittedCnt(result.getInt("Submitted_cnt"));
			EncounterRec.setSoaerrorCnt(result.getInt("SOAError_cnt"));
			EncounterRec.setRejectedCnt(result.getInt("Rejected_cnt"));
			EncounterRec.setNostatusCnt(result.getInt("nostatus_cnt"));
			EncounterRec.setNonbilledCnt(result.getInt("nonbilled_cnt"));
			EncounterRec.setCompleted(result.getString("completed"));
			EncounterRec.setPercentage(result.getString("percentage"));
			EncounterRec.setEntityname(result.getString("Entityname"));

			// EncounterRec.setStatusCounts(getEncounterCounts(result.getString("logID")));
			list.add(EncounterRec);
		}
		ps.close();
		result.close();

		partnerEncounterList = list;
	}

	public void getWeeklyEncountersList() throws SQLException {
		List<EncounterWeekly> list = new ArrayList<EncounterWeekly>();

		PreparedStatement ps = SOACon.prepareStatement("SELECT     " + dateFormatMethod + "( Batchdate, '"
				+ dateFormatDisplay + "' ) Weekstart,\r\n" + "           NULL Weekend,\r\n"
				+ "           NULL Weeknumber,\r\n" + "           SUM( Cnt ) Total,\r\n"
				+ "           F_Get_Counts( NULL, 'Accepted', '" + dateFormatMethod + "', '" + dateFormatCalc + "', "
				+ dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Accepted_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'Ready to Submit', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Ready_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'Submitted', '" + dateFormatMethod + "', '" + dateFormatCalc + "', "
				+ dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Submitted_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'SOA Error', '" + dateFormatMethod + "', '" + dateFormatCalc + "', "
				+ dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Soaerror_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'Rejected', '" + dateFormatMethod + "', '" + dateFormatCalc + "', "
				+ dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Rejected_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'No Status Given', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Nostatus_Cnt,\r\n"
				+ "           F_Get_Counts( NULL, 'Non-Billable', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Nonbilled_Cnt           \r\n"
				+ "    FROM   Soa_Encounter_Summary\r\n" + "GROUP BY   " + dateFormatMethod + "( Batchdate, '"
				+ dateFormatDisplay + "' )," + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' )\r\n"
				+ "ORDER BY   " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) DESC");
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWeekly EncounterRec = new EncounterWeekly();
			EncounterRec.setWeekStart(result.getString("weekstart"));
			EncounterRec.setWeekEnd(result.getString("weekend"));
			EncounterRec.setTotal(result.getInt("total"));
			EncounterRec.setWeekNumber(result.getInt("weeknumber"));
			EncounterRec.setAcceptedCnt(result.getInt("Accepted_cnt"));
			EncounterRec.setReadyCnt(result.getInt("Ready_cnt"));
			EncounterRec.setSubmittedCnt(result.getInt("Submitted_cnt"));
			EncounterRec.setSoaerrorCnt(result.getInt("SOAError_cnt"));
			EncounterRec.setRejectedCnt(result.getInt("Rejected_cnt"));
			EncounterRec.setNostatusCnt(result.getInt("nostatus_cnt"));
			EncounterRec.setNonbilledCnt(result.getInt("nonbilled_cnt"));

			list.add(EncounterRec);
		}
		ps.close();
		result.close();

		encounterWeeklyList = list;
	}

	public void getWeeklyParnterGraphEncountersList() throws SQLException {
		List<EncounterWeekly> list = new ArrayList<EncounterWeekly>();

		PreparedStatement ps = SOACon.prepareStatement("SELECT        '{ \"category\": \"'" + "           || "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay + "' )"
				+ "           || '\" , \"Total_cnt\" :'" + "           || SUM( Cnt )" + "           || ','"
				+ "           || ( SELECT       LISTAGG(" + "                                         CASE Status"
				+ "                                            WHEN 'SOA Error' THEN '\"SOAerror_cnt\"'"
				+ "                                            WHEN 'Ready to Submit' THEN '\"Ready_cnt\"'"
				+ "                                            WHEN 'Non Billable' THEN '\"NonBillable_cnt\"'"
				+ "                                            WHEN 'No Status Given' THEN '\"NoStatusGiven_cnt\"'"
				+ "                                            ELSE '\"' || Status || '_cnt\"'"
				+ "                                         END" + "                                      || ' :'"
				+ "                                      || SUM( Cnt )," + "                                      ','"
				+ "                             )" + "                             WITHIN GROUP (ORDER BY Status)"
				+ "                          || '}'" + "                   FROM   Soa_Encounter_Summary M2"
				+ "                  WHERE   " + dateFormatMethod + "( M2.Batchdate, '" + dateFormatDisplay + "' ) = "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay + "' ) AND Entityname = '" + currentPartner
				+ "'" + "               GROUP BY   Status )" + "              AS Assignments,"
				+ "           LEAD( ',', 1, ' ' ) OVER (ORDER BY " + dateFormatMethod + "( M.Batchdate, '"
				+ dateFormatCalc + "' ) ASC) Space" + "    FROM   Soa_Encounter_Summary M" + "   WHERE   Entityname = '"
				+ currentPartner + "' " + " GROUP BY   " + dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay
				+ "' ), " + dateFormatMethod + "( M.Batchdate, '" + dateFormatCalc + "' )" + " ORDER BY   "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatCalc + "' )");
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWeekly EncounterRec = new EncounterWeekly();
			EncounterRec.setWeekStart(result.getString("assignments"));
			EncounterRec.setWeekEnd(result.getString("Space"));

			list.add(EncounterRec);
		}
		ps.close();
		result.close();

		encounterGraphPartnerList = list;
	}

	public void getWeeklyGraphEncountersList() throws SQLException {
		List<EncounterWeekly> list = new ArrayList<EncounterWeekly>();
		// System.out.println("getPartnerWeeklyEncountersList " + currentPartner);
		PreparedStatement ps = SOACon.prepareStatement("SELECT        '{ \"category\": \"'" + "           || "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay + "' )"
				+ "           || '\" , \"Total_cnt\" :'" + "           || SUM( Cnt )" + "           || ','"
				+ "           || ( SELECT       LISTAGG(" + "                                         CASE Status"
				+ "                                            WHEN 'SOA Error' THEN '\"SOAerror_cnt\"'"
				+ "                                            WHEN 'Ready to Submit' THEN '\"Ready_cnt\"'"
				+ "                                            WHEN 'Non Billable' THEN '\"NonBillable_cnt\"'"
				+ "                                            WHEN 'No Status Given' THEN '\"NoStatusGiven_cnt\"'"
				+ "                                            ELSE '\"' || Status || '_cnt\"'"
				+ "                                         END" + "                                      || ' :'"
				+ "                                      || SUM( Cnt )," + "                                      ','"
				+ "                             )" + "                             WITHIN GROUP (ORDER BY Status)"
				+ "                          || '}'" + "                   FROM   Soa_Encounter_Summary M2"
				+ "                  WHERE   " + dateFormatMethod + "( M2.Batchdate, '" + dateFormatDisplay + "' ) = "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay + "' )"
				+ "               GROUP BY   Status )" + "              AS Assignments,"
				+ "           LEAD( ',', 1, ' ' ) OVER (ORDER BY " + dateFormatMethod + "( M.Batchdate, '"
				+ dateFormatCalc + "' ) ASC) Space" + "    FROM   Soa_Encounter_Summary M" + " GROUP BY   "
				+ dateFormatMethod + "( M.Batchdate, '" + dateFormatDisplay + "' ), " + dateFormatMethod
				+ "( M.Batchdate, '" + dateFormatCalc + "' )" + " ORDER BY   " + dateFormatMethod + "( M.Batchdate, '"
				+ dateFormatCalc + "' )");
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWeekly EncounterRec = new EncounterWeekly();
			EncounterRec.setWeekStart(result.getString("assignments"));
			EncounterRec.setWeekEnd(result.getString("Space"));

			list.add(EncounterRec);
		}
		ps.close();
		result.close();

		encounterGraphList = list;
	}

	public void getPartnerWeeklyEncountersList() throws SQLException {
		List<EncounterWeekly> list = new ArrayList<EncounterWeekly>();

		PreparedStatement ps = SOACon.prepareStatement("SELECT     " + dateFormatMethod + "( Batchdate, '"
				+ dateFormatDisplay + "' ) Weekstart,\r\n" + "           NULL Weekend,\r\n"
				+ "           NULL Weeknumber,\r\n" + "           SUM( Cnt ) Total,\r\n"
				+ "           F_Get_Counts( entityName, 'Accepted', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Accepted_Cnt,\r\n"
				+ "           F_Get_Counts( entityName, 'Ready to Submit', '" + dateFormatMethod + "', '"
				+ dateFormatCalc + "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Ready_Cnt,\r\n"
				+ "           F_Get_Counts( entityName, 'Submitted', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) ) Submitted_Cnt,\r\n"
				+ "           F_Get_Counts( entityName, 'SOA Error', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Soaerror_Cnt,\r\n"
				+ "           F_Get_Counts( entityName, 'Rejected', '" + dateFormatMethod + "', '" + dateFormatCalc
				+ "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc + "' ) )  Rejected_Cnt,\r\n"
				+ "           F_Get_Counts( entityName, 'No Status Given', '" + dateFormatMethod + "', '"
				+ dateFormatCalc + "', " + dateFormatMethod + "( Batchdate, '" + dateFormatCalc
				+ "' ) )  Nostatus_Cnt,\r\n" + "           F_Get_Counts( entityName, 'Non-Billable', '"
				+ dateFormatMethod + "', '" + dateFormatCalc + "', " + dateFormatMethod + "( Batchdate, '"
				+ dateFormatCalc + "' ) )  Nonbilled_Cnt           \r\n"
				+ "    FROM   Soa_Encounter_Summary where entityName = '" + currentPartner + "' \r\n" + "GROUP BY   "
				+ dateFormatMethod + "( Batchdate, '" + dateFormatDisplay + "' )," + dateFormatMethod + "( Batchdate, '"
				+ dateFormatCalc + "' ),entityName \r\n" + "ORDER BY   " + dateFormatMethod + "( Batchdate, '"
				+ dateFormatCalc + "' ) DESC");
		ResultSet result = ps.executeQuery();

		while (result.next()) {
			EncounterWeekly EncounterRec = new EncounterWeekly();

			EncounterRec.setWeekStart(result.getString("weekstart"));
			EncounterRec.setWeekEnd(result.getString("weekend"));
			EncounterRec.setTotal(result.getInt("total"));
			EncounterRec.setWeekNumber(result.getInt("weeknumber"));
			EncounterRec.setAcceptedCnt(result.getInt("Accepted_cnt"));
			EncounterRec.setReadyCnt(result.getInt("Ready_cnt"));
			EncounterRec.setSubmittedCnt(result.getInt("Submitted_cnt"));
			EncounterRec.setSoaerrorCnt(result.getInt("SOAError_cnt"));
			EncounterRec.setRejectedCnt(result.getInt("Rejected_cnt"));
			EncounterRec.setNostatusCnt(result.getInt("nostatus_cnt"));
			EncounterRec.setNonbilledCnt(result.getInt("nonbilled_cnt"));
			list.add(EncounterRec);
		}
		ps.close();
		result.close();

		partnerEncounterWeeklyList = list;
	}

	public void logUser() throws SQLException {

		PreparedStatement statement = SOACon.prepareStatement("INSERT INTO userlog (userid) VALUES (?)");
		statement.setString(1, userid);
		statement.executeUpdate();
		SOACon.commit();
		statement.close();
	}

	public void getEncountersList() throws SQLException {
		List<Encounter> list = new ArrayList<Encounter>();

		PreparedStatement ps = SOACon.prepareStatement(
				"Select planname, order_seq,rownum from (select distinct entityname planName,2 order_seq from Soa_Encounter_Summary union all  select  'All Plans',1 from dual order by 2,1)");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			Encounter EncounterRec = new Encounter();
			if (currentPartner.length() == 1 && result.getString("rownum").equalsIgnoreCase("2")) {
				currentPartner = result.getString("planName");
			}
			EncounterRec.setPlanName(result.getString("planName"));

			list.add(EncounterRec);
		}
		ps.close();
		result.close();
		getLastUpdated();
		encounterMenu = list;
	}

	public void changeType() throws Exception {

		switch (Integer.parseInt(dateFormatID)) {
		case 1:
			dateFormatDisplay = "YYYY-MM-DD";
			dateFormatCalc = "YYYYMMDD";
			dateFormatMethod = "To_Char";
			displayName = "Day";
			break;
		case 2:
			dateFormatDisplay = "IW";
			dateFormatCalc = "IW";
			dateFormatMethod = "Trunc";
			displayName = "Week";
			break;
		case 3:
			dateFormatDisplay = "YYYY MM (Mon)";
			dateFormatCalc = "YYYYMM";
			dateFormatMethod = "To_Char";
			displayName = "Month";
			break;
		case 4:
			dateFormatDisplay = "YYYY";
			dateFormatCalc = "YYYY";
			dateFormatMethod = "To_Char";
			displayName = "Year";
			break;

		}
		openSoaInfraConnection();
		getPartnerWeeklyEncountersList();
		getWeeklyEncountersList();
		getWeeklyGraphEncountersList();
		getWeeklyParnterGraphEncountersList();
		closeSoaInfraConnection();
	}

	public void openSoaInfraConnection() throws Exception {

		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SOAReporting");
		SOACon = dataSourceSOA.getConnection();

	}

	public void closeSoaInfraConnection() throws Exception {

		SOACon.close();
	}

	@PostConstruct
	public void reset() throws Exception {
		try {
			userid = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			openSoaInfraConnection();
			logUser();
			getEncountersList();
			getPartnerEncountersList();
			getPartnerWeeklyEncountersList();
			getWeeklyEncountersList();
			getWeeklyGraphEncountersList();
			getWeeklyParnterGraphEncountersList();
			closeSoaInfraConnection();
		} catch (Exception e) {
			e.printStackTrace();
			// or handle more gracefully
		}

	}

	@PreDestroy
	public void cleanUp() throws Exception {

	}
}