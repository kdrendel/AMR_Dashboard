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
	public String userid = "Kdrendel";

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	private Connection theConnection  = null;


		public List<EncounterValidationObject> getTest() throws Exception {


		List<EncounterValidationObject> list = new ArrayList<EncounterValidationObject>();
		String conStr = System.getenv("MYSQLCONNSTR_MyShuttleDb");
		theConnection = DriverManager.getConnection(conStr);
		//theConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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