package com.amr.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;



@ManagedBean
@SessionScoped

public class LoginInfoController implements Serializable {
	private static final long serialVersionUID = 1L;
	public String userid;

	public String getUserid() {
		return userid;
	}

	public String logout()  {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "logoff.xhtml";
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@PostConstruct
	public void reset() throws Exception {
		userid = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	@PreDestroy
	public void cleanUp() throws Exception {

	}
}
