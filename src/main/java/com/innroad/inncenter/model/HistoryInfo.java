package com.innroad.inncenter.model;

public class HistoryInfo {

	private String CATEGORY;
	private String DATE;
	private String TIME;
	private String USERNAME_FIRSTNAME;
	private String USERNAME_LASTNAME;
	private String USERNAME;
	private String DESCRIPTION;

	public HistoryInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HistoryInfo(String cATEGORY, String dATE, String tIME, String uSERNAME_FIRSTNAME, String uSERNAME_LASTNAME,
			String dESCRIPTION) {
		super();
		CATEGORY = cATEGORY;
		DATE = dATE;
		TIME = tIME;
		USERNAME_FIRSTNAME = uSERNAME_FIRSTNAME;
		USERNAME_LASTNAME = uSERNAME_LASTNAME;
		USERNAME = USERNAME_FIRSTNAME + " " + USERNAME_LASTNAME;
		DESCRIPTION = dESCRIPTION;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public String getUSERNAME_FIRSTNAME() {
		return USERNAME_FIRSTNAME;
	}

	public void setUSERNAME_FIRSTNAME(String uSERNAME_FIRSTNAME) {
		USERNAME_FIRSTNAME = uSERNAME_FIRSTNAME;
	}

	public String getUSERNAME_LASTNAME() {
		return USERNAME_LASTNAME;
	}

	public void setUSERNAME_LASTNAME(String uSERNAME_LASTNAME) {
		USERNAME_LASTNAME = uSERNAME_LASTNAME;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

}
