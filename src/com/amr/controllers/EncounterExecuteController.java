package com.amr.controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import com.amr.models.EncounterValidationObject;

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


	public List<EncounterValidationObject> gettest() throws Exception {


		List<EncounterValidationObject> list = new ArrayList<EncounterValidationObject>();
		EncounterValidationObject EncounterFileRec = new EncounterValidationObject();
		
		EncounterFileRec.setHealthPlancode("TestXXXXXXXXXXXXXXXXXXX");
		EncounterFileRec.setStopid("TestXXXXXXXXXXXXXXXXXXX");
		list.add(EncounterFileRec);
		
		return list;

	}
}