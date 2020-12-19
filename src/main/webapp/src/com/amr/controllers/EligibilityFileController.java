package com.amr.controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.amr.models.EligibilityFileRunRecord;
import com.amr.models.EligibilityValidationInvalids;
import com.amr.models.EncounterFileDetails;
import com.amr.models.EncounterWSDLs;

@ManagedBean
@SessionScoped
public class EligibilityFileController implements Serializable {
	private List<EligibilityFileRunRecord> eligibilityFileRunRecords = null;
	private List<EligibilityFileRunRecord> eligibilityFileRunRecordsSQLServer = null;

	public List<EligibilityFileRunRecord> getEligibilityFileRunRecordsSQLServer() {
		return eligibilityFileRunRecordsSQLServer;
	}

	public void setEligibilityFileRunRecordsSQLServer(
			List<EligibilityFileRunRecord> eligibilityFileRunRecordsSQLServer) {
		this.eligibilityFileRunRecordsSQLServer = eligibilityFileRunRecordsSQLServer;
	}

	public List<EligibilityFileRunRecord> getEligibilityFileRunRecords() {
		return eligibilityFileRunRecords;
	}

	public void setEligibilityFileRunRecords(List<EligibilityFileRunRecord> eligibilityFileRunRecords) {
		this.eligibilityFileRunRecords = eligibilityFileRunRecords;
	}

	private String previousPage = null;

	public String getPreviousPage() {
		return previousPage;
	}

	public String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public static void callApprovalService(String planName, String FileName, String Approver)
			throws IOException, SAXException {

		String responseString = "";
		String outputString = "";
		String wsEndPoint = "https://a2c-spice-test.evhc.net:443/soa-infra/services/default/EligibilityServices/OverrideThresholdService";
		URL url = new URL(wsEndPoint);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = "<soapenv:Envelope xmlns:over=\"http://evhc.com/nemt/spice/services/OverrideThresholdService/v1/schemas/overrideThreshold\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://evhc.com/nemt/spice/schemas/common/ServiceHeader/v1\">\r\n"
				+ "   <soapenv:Header>\r\n"
				+ "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\r\n"
				+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-7DE451EB654B0288CC15397284805064\">\r\n"
				+ "            <wsse:Username>weblogic</wsse:Username>\r\n"
				+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">3YxoQB0FCFJFeo</wsse:Password>\r\n"
				+ "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">rsjJf5TQNapZieau8bLxiQ==</wsse:Nonce>\r\n"
				+ "            <wsu:Created>2018-10-16T22:21:20.506Z</wsu:Created>\r\n"
				+ "         </wsse:UsernameToken>\r\n"
				+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-7DE451EB654B0288CC15397281567693\">\r\n"
				+ "            <wsse:Username>20528337</wsse:Username>\r\n"
				+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Pithapuram!29</wsse:Password>\r\n"
				+ "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">7pieNd786CzqCC138m8Wqg==</wsse:Nonce>\r\n"
				+ "            <wsu:Created>2018-10-16T22:15:56.768Z</wsu:Created>\r\n"
				+ "         </wsse:UsernameToken>\r\n"
				+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-8E359BA9975EAE65BB15386063547551\">\r\n"
				+ "            <wsse:Username>" + Approver + "</wsse:Username>\r\n"
				+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Pithapuram!29</wsse:Password>\r\n"
				+ "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">smmU8DCajUWHaxEOKIQTSQ==</wsse:Nonce>\r\n"
				+ "            <wsu:Created>2018-10-03T22:39:14.754Z</wsu:Created>\r\n"
				+ "         </wsse:UsernameToken>\r\n" + "      </wsse:Security>\r\n" + "   </soapenv:Header>\r\n"
				+ "   <soapenv:Body>\r\n" + "      <over:overrideThresholdRequest>\r\n"
				+ "         <v1:requestHeader>\r\n" + "            <v1:requestingApplication>" + planName
				+ "</v1:requestingApplication>\r\n" + "            <v1:timestamp>2018-06-16T14:58</v1:timestamp>\r\n"
				+ "         </v1:requestHeader>\r\n" + "         <over:requestBlock>\r\n"
				+ "            <over:approver>" + Approver + "</over:approver>\r\n"
				+ "            <over:isApproved>true</over:isApproved>\r\n"
				+ "            <over:eligibilityCorrelation>\r\n" + "               <over:planName>" + planName
				+ "</over:planName>\r\n" + "               <over:originalFileName>" + FileName
				+ "</over:originalFileName>\r\n" + "            </over:eligibilityCorrelation>\r\n"
				+ "         </over:requestBlock>\r\n" + "      </over:overrideThresholdRequest>\r\n"
				+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>" + "";
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "getUserDetails";
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

		String formattedSOAPResponse = formatXML(outputString);

	}

//	public static void callApprovalService(String planName, String FileName, String Approver)
//			throws IOException, SAXException {
//
//		String responseString = "";
//		String outputString = "";
//		String wsEndPoint = "https://a2c-spice.evhc.net:443/soa-infra/services/default/EligibilityServices/OverrideThresholdService";
//		URL url = new URL(wsEndPoint);
//		URLConnection connection = url.openConnection();
//		HttpURLConnection httpConn = (HttpURLConnection) connection;
//		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		String xmlInput = "<soapenv:Envelope xmlns:over=\"http://evhc.com/nemt/spice/services/OverrideThresholdService/v1/schemas/overrideThreshold\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://evhc.com/nemt/spice/schemas/common/ServiceHeader/v1\">\r\n"
//				+ "   <soapenv:Header>\r\n"
//				+ "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\r\n"
//				+ "         <wsse:UsernameToken wsu:Id=\"UsernameToken-E8110FAFE4780D87F815392815139762\">\r\n"
//				+ "            <wsse:Username>weblogic</wsse:Username>\r\n"
//				+ "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">p5YKeRJVr2Wfyb</wsse:Password>\r\n"
//				+ "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">CaHSRVTCAFd/D7v6UJHj2w==</wsse:Nonce>\r\n"
//				+ "            <wsu:Created>2018-10-11T18:11:53.976Z</wsu:Created>\r\n"
//				+ "         </wsse:UsernameToken>\r\n" + "      </wsse:Security>\r\n" + "   </soapenv:Header>\r\n"
//				+ "   <soapenv:Body>\r\n" + "      <over:overrideThresholdRequest>\r\n"
//				+ "         <v1:requestHeader>\r\n" + "            <v1:requestingApplication>" + planName
//				+ "</v1:requestingApplication>\r\n" + "            <v1:timestamp>2017-06-16T14:58</v1:timestamp>\r\n"
//				+ "         </v1:requestHeader>\r\n" + "         <over:requestBlock>\r\n"
//				+ "            <over:approver>" + Approver + "</over:approver>\r\n"
//				+ "            <over:isApproved>true</over:isApproved>\r\n"
//				+ "            <over:eligibilityCorrelation>\r\n" + "               <over:planName>" + planName
//				+ "</over:planName>\r\n" + "               <over:originalFileName>" + FileName
//				+ "</over:originalFileName>\r\n" + "            </over:eligibilityCorrelation>\r\n"
//				+ "         </over:requestBlock>\r\n" + "      </over:overrideThresholdRequest>\r\n"
//				+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>\r\n" + "";
//		byte[] buffer = new byte[xmlInput.length()];
//		buffer = xmlInput.getBytes();
//		bout.write(buffer);
//		byte[] b = bout.toByteArray();
//		String SOAPAction = "getUserDetails";
//		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
//		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//		httpConn.setRequestProperty("SOAPAction", SOAPAction);
//		httpConn.setRequestMethod("POST");
//		httpConn.setDoOutput(true);
//		httpConn.setDoInput(true);
//
//		OutputStream out = httpConn.getOutputStream();
//
//		out.write(b);
//		out.close();
//
//		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
//		BufferedReader in = new BufferedReader(isr);
//
//		while ((responseString = in.readLine()) != null) {
//			outputString = outputString + responseString;
//		}
//
//		String formattedSOAPResponse = formatXML(outputString);
//
//	}

//	public static void callApprovalService(String planName, String FileName, String Approver)
//			throws IOException, SAXException {
//
//		String responseString = "";
//		String outputString = "";
//		String wsEndPoint = "https://a2c-spice-dev.evhc.net:8443/soa-infra/services/default/EligibilityServices/OverrideThresholdService";
//		URL url = new URL(wsEndPoint);
//		URLConnection connection = url.openConnection();
//		HttpURLConnection httpConn = (HttpURLConnection) connection;
//		ByteArrayOutputStream bout = new ByteArrayOutputStream();
//		String xmlInput = "<soapenv:Envelope xmlns:over=\"http://evhc.com/nemt/spice/services/OverrideThresholdService/v1/schemas/overrideThreshold\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://evhc.com/nemt/spice/schemas/common/ServiceHeader/v1\">\r\n" + 
//				"   <soapenv:Header>\r\n" + 
//				"      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\r\n" + 
//				"         <wsse:UsernameToken wsu:Id=\"UsernameToken-7DE451EB654B0288CC15397284805064\">\r\n" + 
//				"            <wsse:Username>weblogic</wsse:Username>\r\n" + 
//				"            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">3YxoQB0FCFJFeo</wsse:Password>\r\n" + 
//				"            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">rsjJf5TQNapZieau8bLxiQ==</wsse:Nonce>\r\n" + 
//				"            <wsu:Created>2018-10-16T22:21:20.506Z</wsu:Created>\r\n" + 
//				"         </wsse:UsernameToken>\r\n" + 
//				"         <wsse:UsernameToken wsu:Id=\"UsernameToken-7DE451EB654B0288CC15397281567693\">\r\n" + 
//				"            <wsse:Username>20528337</wsse:Username>\r\n" + 
//				"            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Pithapuram!29</wsse:Password>\r\n" + 
//				"            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">7pieNd786CzqCC138m8Wqg==</wsse:Nonce>\r\n" + 
//				"            <wsu:Created>2018-10-16T22:15:56.768Z</wsu:Created>\r\n" + 
//				"         </wsse:UsernameToken>\r\n" + 
//				"         <wsse:UsernameToken wsu:Id=\"UsernameToken-8E359BA9975EAE65BB15386063547551\">\r\n" + 
//				"            <wsse:Username>20528337</wsse:Username>\r\n" + 
//				"            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Pithapuram!29</wsse:Password>\r\n" + 
//				"            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">smmU8DCajUWHaxEOKIQTSQ==</wsse:Nonce>\r\n" + 
//				"            <wsu:Created>2018-10-03T22:39:14.754Z</wsu:Created>\r\n" + 
//				"         </wsse:UsernameToken>\r\n" + 
//				"      </wsse:Security>\r\n" + 
//				" </soapenv:Header>\r\n"
//				+ "   <soapenv:Body>\r\n" + "      <over:overrideThresholdRequest>\r\n"
//				+ "         <v1:requestHeader>\r\n" + "            <v1:requestingApplication>" + planName
//				+ "</v1:requestingApplication>\r\n" + "            <v1:timestamp>2018-06-16T14:58</v1:timestamp>\r\n"
//				+ "         </v1:requestHeader>\r\n" + "         <over:requestBlock>\r\n"
//				+ "            <over:approver>" + Approver + "</over:approver>\r\n"
//				+ "            <over:isApproved>true</over:isApproved>\r\n"
//				+ "            <over:eligibilityCorrelation>\r\n" + "               <over:planName>" + planName
//				+ "</over:planName>\r\n" + "               <over:originalFileName>" + FileName
//				+ "</over:originalFileName>\r\n" + "            </over:eligibilityCorrelation>\r\n"
//				+ "         </over:requestBlock>\r\n" + "      </over:overrideThresholdRequest>\r\n"
//				+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";
//		byte[] buffer = new byte[xmlInput.length()];
//		buffer = xmlInput.getBytes();
//		bout.write(buffer);
//		byte[] b = bout.toByteArray();
//		String SOAPAction = "getUserDetails";
//		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
//		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//		httpConn.setRequestProperty("SOAPAction", SOAPAction);
//		httpConn.setRequestMethod("POST");
//		httpConn.setDoOutput(true);
//		httpConn.setDoInput(true);
//
//		OutputStream out = httpConn.getOutputStream();
//		// Write the content of the request to the outputstream of the HTTP
//		// Connection.
//		out.write(b);
//		out.close();
//		// Ready with sending the request.
//		// Read the response.
//		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
//		BufferedReader in = new BufferedReader(isr);
//		// Write the SOAP message response to a String.
//		while ((responseString = in.readLine()) != null) {
//			outputString = outputString + responseString;
//		}
//		// Write the SOAP message formatted to the console.
//		String formattedSOAPResponse = formatXML(outputString);
//		System.out.println(formattedSOAPResponse);
//	}
	// format the XML in pretty String
	private static String formatXML(String unformattedXml) throws SAXException, IOException {
		try {
			Document document = parseXmlFile(unformattedXml);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", 3);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult xmlOutput = new StreamResult(new StringWriter());
			transformer.transform(source, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	private static Document parseXmlFile(String in) throws SAXException, IOException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	private static final long serialVersionUID = 1L;
	private Connection StatsCon = null;
	private Connection SOAInfraCon = null;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void checkF5() throws Exception {

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		String id = viewRoot.getViewId();
		if (previousPage != null && (previousPage.equals(id))) {
			getEligibilityData();
		}
		previousPage = id;

	}

	public void openSoaInfraConnection() throws Exception {

		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SOAReporting");
		SOAInfraCon = dataSourceSOA.getConnection();

	}

	public void reprocessTransaction() throws SQLException, IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

		String fileName = params.get("fileName");
		String planName = params.get("planName");

		String timeStamp = new SimpleDateFormat("HHmmss").format(new java.util.Date());
		File destFile = new File("/mnt/edi/Eligibility/" + planName + "/" + timeStamp + "_" + fileName);
		File srcFile = FileUtils.getFile("/mnt//edi//Eligibility/" + planName + "/staging/" + fileName);

		FileUtils.copyFile(srcFile, destFile);

	}

	private StreamedContent download;

	public StreamedContent getDownload() {
		return download;
	}

	public void setDownload(StreamedContent download) {
		this.download = download;
	}

	public void prepDownload(String fileName, String planName) throws Exception {
		
		File file = new File("/mnt//edi//Eligibility/" + planName + "/staging/" + fileName);
		//File file = new File("/opt/oracle/logs/spicedomaindev/AdminServer/AdminServer.log");
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
		FacesContext.getCurrentInstance().responseComplete();
		// System.out.println("PREP = " + download.getName());
	}

	public StreamedContent generateFile(String fileName, String planName) throws FileNotFoundException {
		// System.out.println("generateFile - fileName " + fileName);
		// System.out.println("generateFile - planName " + planName);

		File file = new File("/mnt//edi//Eligibility/" + planName + "/staging/" + fileName);
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		FacesContext.getCurrentInstance().responseComplete();
		return new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
	}

	public void openSQLServerConnection() throws Exception {

		javax.naming.Context initialContextSOA = new javax.naming.InitialContext();
		javax.sql.DataSource dataSourceSOA = (javax.sql.DataSource) initialContextSOA.lookup("jdbc/SpiceApps");
		StatsCon = dataSourceSOA.getConnection();

	}

	public void closeSoaInfraConnection() throws Exception {

		SOAInfraCon.close();
	}

	public void closeSQLServerConnection() throws Exception {

		StatsCon.close();
	}

	public List<EligibilityValidationInvalids> getFileList() throws Exception {
		System.out.println("Start getFileList");
		List<EligibilityValidationInvalids> list = new ArrayList<EligibilityValidationInvalids>();
		System.out.println("Open Connection getFileList");
		openSoaInfraConnection();
		System.out.println("Done Open Connection getFileList");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		params.get("instanceid");
		String instanceid = params.get("instanceid");

		PreparedStatement ps = SOAInfraCon.prepareStatement("SELECT             Planname,\r\n"
				+ "                   Validationname,\r\n" + "                   Validationdescription,\r\n"
				+ "                   Memberid,\r\n" + "                   Memberfirstname,\r\n"
				+ "                   Memberfirstlast,\r\n" + "                   Payload,\r\n"
				+ "                   Instanceid,\r\n" + "                   Firstname,\r\n"
				+ "                   Lastname,\r\n" + "                   Middlename\r\n"
//				+ "                   Dateofbirth,\r\n" + "                   Street,\r\n"
//				+ "                   City,\r\n" + "                   State,\r\n" + "                   Zip,\r\n"
//				+ "                   Zip4,\r\n" + "                   Phone,\r\n"
//				+ "                   Medicareid,\r\n" + "                   Medicaidid,\r\n"
//				+ "                   Planregioncode,\r\n" + "                   Primaryplancode,\r\n"
//				+ "                   Member_Id,\r\n" + "                   Startdate,\r\n"
//				+ "                   Enddate,\r\n" + "                   gender,\r\n"
//				+ "                   Groupidentifier1,\r\n" + "                   Extendedcode1,\r\n"
//				+ "                   Extendedcode2,\r\n" + "                   Extendedcode3,\r\n"
//				+ "                   Extendedcode4,\r\n" + "                   Extendedcode5,\r\n"
//				+ "                   Extendedcode6,\r\n" + "                   Extendedcode7\r\n"
				+ "            FROM   Soa_Validations T,\r\n" + "                   XMLTABLE(\r\n"
				+ "                             '/*:member'\r\n"
				+ "                             PASSING Xmltype( T.Payload )\r\n"
				+ "                             COLUMNS Firstname VARCHAR2( 200 ) PATH '/*:member/*:person/*:lastName',\r\n"
				+ "                                     Lastname VARCHAR2( 200 ) PATH '/*:member/*:person/*:firstName',\r\n"
				+ "                                     gender VARCHAR2( 200 ) PATH '/*:member/*:person/*:gender',\r\n"
				+ "                                     Middlename VARCHAR2( 200 ) PATH '/*:member/*:person/*:middleName',\r\n"
				+ "                                     Dateofbirth VARCHAR2( 200 ) PATH '/*:member/*:person/*:dateOfBirth',\r\n"
				+ "                                     Street VARCHAR2( 200 ) PATH '/*:member/*:addressList/*:address/*:street',\r\n"
				+ "                                     City VARCHAR2( 200 ) PATH '/*:member/*:addressList/*:address/*:city',\r\n"
				+ "                                     State VARCHAR2( 200 ) PATH '/*:member/*:addressList/*:address/*:state',\r\n"
				+ "                                     Zip VARCHAR2( 200 ) PATH '/*:member/*:addressList/*:address/*:zip',\r\n"
				+ "                                     Zip4 VARCHAR2( 200 ) PATH '/*:member/*:addressList/*:address/*:zip4',\r\n"
				+ "                                     Phone VARCHAR2( 200 ) PATH '/*:member/*:contactMethodList/*:contactMethod/*:value',\r\n"
				+ "                                     Medicareid VARCHAR2( 200 ) PATH '/*:member/*:medicareId', Medicaidid VARCHAR2( 200 ) PATH '/*:member/*:medicaidId',\r\n"
				+ "                                     Primaryplancode VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:primaryPlanCode',\r\n"
				+ "                                     Planregioncode VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:planRegionCode',\r\n"
				+ "                                     Member_Id VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:memberId',\r\n"
				+ "                                     Startdate VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:coverageDates/*:startDate',\r\n"
				+ "                                     Enddate VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:coverageDates/*:endDate',\r\n"
				+ "                                     Groupidentifier1 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:groupIdentifier1',\r\n"
				+ "                                     Extendedcode1 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"1\"]/*:code',\r\n"
				+ "                                     Extendedcode2 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"2\"]/*:code',\r\n"
				+ "                                     Extendedcode3 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"3\"]/*:code',\r\n"
				+ "                                     Extendedcode4 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"4\"]/*:code',\r\n"
				+ "                                     Extendedcode5 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"5\"]/*:code',\r\n"
				+ "                                     Extendedcode6 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"6\"]/*:code',\r\n"
				+ "                                     Extendedcode7 VARCHAR2( 200 ) PATH '/*:member/*:coverageDetailList/*:coverageDetail/*:extendedCodeList/*:extendedCode[@number=\"7\"]/*:code'\r\n"
				+ "                   ) Warehouse2\r\n" + "           WHERE   Instanceid = " + instanceid);

		ResultSet result = ps.executeQuery();
		System.out.println("After execute  getFileList");
		while (result.next()) {
			System.out.println("In Loop getFileList");
			EligibilityValidationInvalids EncounterFileRec = new EligibilityValidationInvalids();
			EncounterFileRec.setPlanname(result.getString("planname"));
			EncounterFileRec.setMemberValidationName(result.getString("validationname"));
			EncounterFileRec.setMemberFirstName(result.getString("Memberfirstname"));
			EncounterFileRec.setMemberLastName(result.getString("Memberfirstlast"));
			EncounterFileRec.setMemberId(result.getString("Memberid"));
			EncounterFileRec.setMemberValidationDescription(result.getString("validationdescription"));
			EncounterFileRec.setMiddlename(result.getString("middlename"));
//			EncounterFileRec.setGender(result.getString("gender"));
//			EncounterFileRec.setStreet(result.getString("street"));
//			EncounterFileRec.setCity(result.getString("city"));
//			EncounterFileRec.setState(result.getString("state"));
//			EncounterFileRec.setZip(result.getString("zip"));
//			EncounterFileRec.setZip4(result.getString("zip4"));
//			EncounterFileRec.setMedicareId(result.getString("medicareId"));
//			EncounterFileRec.setMedicaidId(result.getString("medicaidId"));
//			EncounterFileRec.setMember_Id(result.getString("member_Id"));
//			EncounterFileRec.setPlanRegionCode(result.getString("planRegionCode"));
//			EncounterFileRec.setStartDate(result.getString("startDate"));
//			EncounterFileRec.setEndDate(result.getString("endDate"));
//			EncounterFileRec.setGroupIdentifier1(result.getString("groupIdentifier1"));
//			EncounterFileRec.setExtendedCode1(result.getString("extendedCode1"));
//			EncounterFileRec.setExtendedCode2(result.getString("extendedCode2"));
//			EncounterFileRec.setExtendedCode3(result.getString("extendedCode3"));
//			EncounterFileRec.setExtendedCode4(result.getString("extendedCode4"));
//			EncounterFileRec.setExtendedCode5(result.getString("extendedCode5"));
//			EncounterFileRec.setExtendedCode6(result.getString("extendedCode6"));
//			EncounterFileRec.setExtendedCode7(result.getString("extendedCode7"));
			list.add(EncounterFileRec);
		}
		ps.close();
		result.close();
		closeSoaInfraConnection();
		System.out.println("Close getFileList");
		return list;
	}

	public void getEligibilityDataSQLServer() throws Exception {
		List<EligibilityFileRunRecord> list = new ArrayList<EligibilityFileRunRecord>();
		openSQLServerConnection();

		PreparedStatement ps = StatsCon.prepareStatement(
				"use A2CEligibility Select top 75 [SubContract_ID] subCoontractorId,[PlanRegion] planName ,FORMAT (FileReady, 'yyyyMMddHHmmss ') as searchdate ,[FileReady] creationDate,[FilePushed] modifyDate,[OldRecordCount] oldCount,[NewRecordCount] newCount,[ChangePercent] changePercentage,[Successful] successfulInd,[ApprovedBy] approver,[MembersAdded] added,[MembersUpdated] updated,[MembersRemoved] removed,[FileRecordCount] fileMemberCount,FileName fileName From dbo.MemberFileStageQueue where FileName is not null  Order BY MemberFileStageQueue_ID desc");

		ResultSet result = ps.executeQuery();

		while (result.next()) {
			// System.out.println("In row " + result.getString("creationDate"));
			EligibilityFileRunRecord EligibilityRec = new EligibilityFileRunRecord();
			EligibilityRec.setCreationDate(result.getString("creationDate"));
			EligibilityRec.setSearchDate(result.getLong("searchdate"));
			EligibilityRec.setModifyDate(result.getString("modifyDate"));
			EligibilityRec.setFileName(result.getString("fileName"));
			EligibilityRec.setPlanName(result.getString("planName"));
			EligibilityRec.setNewCount(result.getInt("newCount"));
			EligibilityRec.setOldCount(result.getInt("oldCount"));
			EligibilityRec.setChangePercentage(result.getInt("changePercentage"));
			EligibilityRec.setSuccessfulInd(result.getInt("successfulInd"));
			EligibilityRec.setApprover(result.getString("approver"));
			EligibilityRec.setAdded(result.getInt("added"));
			EligibilityRec.setRemoved(result.getInt("removed"));
			EligibilityRec.setUpdated(result.getInt("updated"));
			EligibilityRec.setFileMemberCount(result.getInt("fileMemberCount"));

			list.add(EligibilityRec);
		}

		ps.close();
		result.close();
		closeSQLServerConnection();
		eligibilityFileRunRecordsSQLServer = list;
	}

	public void flagforretrieval() throws Exception {

		getEligibilityData();

	}

	public void getEligibilityData() throws Exception {
		List<EligibilityFileRunRecord> list = new ArrayList<EligibilityFileRunRecord>();
		openSoaInfraConnection();
		PreparedStatement ps = SOAInfraCon.prepareStatement("SELECT    Sev.Creation_Date Creationdate,              Sev.Fromcreatetime,              Sev.Tocreatetime,              Sev.Modify_Date Modifydate,              Sev.Tried_Times Triedtimes,              DECODE( Sev.Fault_Text, NULL, Sev.State_Text, 'Closed' ) State,              Processing_Time Processingtime,              DECODE(                      LENGTH( B2berror ),                      NULL, DECODE(                                    Sev.Fault_Text,                                    NULL, DECODE(                                                  Sev.State_Text,                                                  'Aborted', 'Aborted',                                                  DECODE(                                                          Load_Status,                                                          'Exceeded Size Threshold - Wait', DECODE( Sev.State_Text,                                                                                                    'Stale', 'Stale',                                                                                                    'Needs Approval' ),                                                          DECODE(                                                                  Sev.State_Text,                                                                  'Running', DECODE( Load_Status,                                                                                     'Exceeded Size Threshold - Time', 'Needs Approval',                                                                                     Sev.State_Text ),                                                                  Sev.State_Text                                                          )                                                  )                                          ),                                    'Faulted'                            ),                      'B2B Error'              )                 Statetext,              Sev.File_Name Filename,              Sev.Plan_Name Planname,              Sev.Load_Status Loadstatus,              Sev.Member_Count Membercount,              Sev.Threshold_Value Threshholdvalue,              Sev.Instance_Key Instancekey,              Sev.Flow_Id Flowid,              Sev.Composite_Name Compositename,              Sev.Last_Action Lastaction,              Sev.Fault_Text Faulttext,              B2berror B2berror,              ( SELECT   COUNT( distinct memberid )                  FROM   soareporting.Soa_Validations                 WHERE   Instanceid = Sev.Flow_Id )                 Validations_Count,URL       FROM   soareporting.Soa_Eligibility_V Sev   ORDER BY   Sev.Creation_Date DESC");

		ResultSet result = ps.executeQuery();
		
		getEligibilityDataSQLServer();

		while (result.next()) {
			EligibilityFileRunRecord EligibilityRec = new EligibilityFileRunRecord();
			EligibilityRec.setCreationDate(result.getString("creationDate"));
			EligibilityRec.setModifyDate(result.getString("modifyDate"));
			EligibilityRec.setTriedTimes(result.getString("triedTimes"));
			EligibilityRec.setState(result.getString("state"));
			EligibilityRec.setStateText(result.getString("stateText"));
			EligibilityRec.setFileName(result.getString("fileName"));
			EligibilityRec.setPlanName(result.getString("planName"));
			EligibilityRec.setLoadStatus(result.getString("loadStatus"));
			EligibilityRec.setFaultText(result.getString("faultText"));
			EligibilityRec.setMemberCount(result.getString("memberCount"));
			EligibilityRec.setThreshHoldvalue(result.getString("threshHoldvalue"));
			EligibilityRec.setInstanceKey(result.getString("instanceKey"));
			EligibilityRec.setFlowId(result.getString("flowId"));
			EligibilityRec.setProcessingTime(result.getString("processingTime"));
			EligibilityRec.setB2bError(result.getString("b2bError"));
			EligibilityRec.setCompositeName(result.getString("compositeName"));
			EligibilityRec.setLastAction(result.getString("lastAction"));
			EligibilityRec.setValidationsCount(result.getString("validations_count"));
			EligibilityRec.setUrlLink(result.getString("URL"));
			


			for (EligibilityFileRunRecord temp : eligibilityFileRunRecordsSQLServer) {
				
				if (temp.fileName.equalsIgnoreCase(result.getString("fileName")) 					) {
					if (result.getString("stateText").equalsIgnoreCase("Needs Approval")
							&& (temp.getAdded().intValue() > 0 || temp.getRemoved().intValue() > 0
									|| temp.getUpdated().intValue() > 0)) {
						EligibilityRec.setStateText("Running");
					}
					// System.out.println("Found row " + temp.getOldCount());
					EligibilityRec.setModifyDate(temp.getModifyDate());
					// EligibilityRec.setFileName(temp.getFileName());
					EligibilityRec.setPlanName(temp.getPlanName());
					EligibilityRec.setNewCount(temp.getNewCount());
					EligibilityRec.setOldCount(temp.getOldCount());
					EligibilityRec.setChangePercentage(temp.getChangePercentage());
					EligibilityRec.setSuccessfulInd(temp.getSuccessfulInd());
					EligibilityRec.setApprover(temp.getApprover());
					EligibilityRec.setAdded(temp.getAdded());
					EligibilityRec.setRemoved(temp.getRemoved());
					EligibilityRec.setUpdated(temp.getUpdated());
					EligibilityRec.setFileMemberCount(temp.getFileMemberCount());
					eligibilityFileRunRecordsSQLServer.remove(temp);
					break;
				}
			}

			list.add(EligibilityRec);
		}

		ps.close();
		result.close();
		closeSoaInfraConnection();
		eligibilityFileRunRecords = list;
	}

	@PostConstruct
	public void reset() throws Exception {
		try {
			userid = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			getEligibilityData();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@PreDestroy
	public void cleanUp() throws Exception {
		SOAInfraCon.close();

	}

}