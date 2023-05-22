package com.innroad.inncenter.model;

public class GuestInfo {

	private String GI_GUEST_NAME;
	private String GI_CONTACT_NAME;
	private String GI_EMAIL;
	private String GI_PHONE_NO;
	private String GI_ALTERNATE_PHONE;
	private String GI_ADDRESS;
	private String GI_CITY;
	private String GI_STATE;
	private String GI_POSTAL_CODE;
	private String GI_COUNTRY;
	private String GI_FAX = "";
	private String GI_ACCOUNT;

	@Override
	public String toString() {
		return "GUEST INFO : " + "\n Guest Name : " + GI_GUEST_NAME + "\n Contact Name : " + GI_CONTACT_NAME
				+ "\n Email : " + GI_EMAIL + "\n Phone No : " + GI_PHONE_NO + "\n Alternate Phone : "
				+ GI_ALTERNATE_PHONE + "\n Address : " + GI_ADDRESS + "\n City : " + GI_CITY + "\n State : " + GI_STATE
				+ "\n Postal Code : " + GI_POSTAL_CODE + "\n Country : " + GI_COUNTRY + "\n Fax : " + GI_FAX
				+ "\n Account : " + GI_ACCOUNT;
	}

	public GuestInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GuestInfo(String gI_GUEST_NAME, String gI_CONTACT_NAME, String gI_EMAIL, String gI_PHONE_NO,
			String gI_ALTERNATE_PHONE, String gI_ADDRESS, String gI_CITY, String gI_STATE, String gI_POSTAL_CODE,
			String gI_COUNTRY, String gI_FAX, String gI_ACCOUNT) {
		super();
		GI_GUEST_NAME = gI_GUEST_NAME;
		GI_CONTACT_NAME = gI_CONTACT_NAME;
		GI_EMAIL = gI_EMAIL;
		GI_PHONE_NO = gI_PHONE_NO;
		GI_ALTERNATE_PHONE = gI_ALTERNATE_PHONE;
		GI_ADDRESS = gI_ADDRESS;
		GI_CITY = gI_CITY;
		GI_STATE = gI_STATE;
		GI_POSTAL_CODE = gI_POSTAL_CODE;
		GI_COUNTRY = gI_COUNTRY;
		GI_FAX = gI_FAX;
		GI_ACCOUNT = gI_ACCOUNT;
	}

	public GuestInfo(String gI_GUEST_NAME, String gI_CONTACT_NAME, String gI_EMAIL, String gI_PHONE_NO,
			String gI_ALTERNATE_PHONE, String gI_ADDRESS, String gI_CITY, String gI_STATE, String gI_POSTAL_CODE,
			String gI_COUNTRY,  String gI_ACCOUNT) {
		super();
		GI_GUEST_NAME = gI_GUEST_NAME;
		GI_CONTACT_NAME = gI_CONTACT_NAME;
		GI_EMAIL = gI_EMAIL;
		GI_PHONE_NO = gI_PHONE_NO;
		GI_ALTERNATE_PHONE = gI_ALTERNATE_PHONE;
		GI_ADDRESS = gI_ADDRESS;
		GI_CITY = gI_CITY;
		GI_STATE = gI_STATE;
		GI_POSTAL_CODE = gI_POSTAL_CODE;
		GI_COUNTRY = gI_COUNTRY;
//		GI_FAX = gI_FAX;
		GI_ACCOUNT = gI_ACCOUNT;
	}
	
	public String getGI_GUEST_NAME() {
		return GI_GUEST_NAME;
	}

	public void setGI_GUEST_NAME(String gI_GUEST_NAME) {
		GI_GUEST_NAME = gI_GUEST_NAME;
	}

	public String getGI_CONTACT_NAME() {
		return GI_CONTACT_NAME;
	}

	public void setGI_CONTACT_NAME(String gI_CONTACT_NAME) {
		GI_CONTACT_NAME = gI_CONTACT_NAME;
	}

	public String getGI_EMAIL() {
		return GI_EMAIL;
	}

	public void setGI_EMAIL(String gI_EMAIL) {
		GI_EMAIL = gI_EMAIL;
	}

	public String getGI_PHONE_NO() {
		return GI_PHONE_NO;
	}

	public void setGI_PHONE_NO(String gI_PHONE_NO) {
		GI_PHONE_NO = gI_PHONE_NO;
	}

	public String getGI_ALTERNATE_PHONE() {
		return GI_ALTERNATE_PHONE;
	}

	public void setGI_ALTERNATE_PHONE(String gI_ALTERNATE_PHONE) {
		GI_ALTERNATE_PHONE = gI_ALTERNATE_PHONE;
	}

	public String getGI_ADDRESS() {
		return GI_ADDRESS;
	}

	public void setGI_ADDRESS(String gI_ADDRESS) {
		GI_ADDRESS = gI_ADDRESS;
	}

	public String getGI_CITY() {
		return GI_CITY;
	}

	public void setGI_CITY(String gI_CITY) {
		GI_CITY = gI_CITY;
	}

	public String getGI_STATE() {
		return GI_STATE;
	}

	public void setGI_STATE(String gI_STATE) {
		GI_STATE = gI_STATE;
	}

	public String getGI_POSTAL_CODE() {
		return GI_POSTAL_CODE;
	}

	public void setGI_POSTAL_CODE(String gI_POSTAL_CODE) {
		GI_POSTAL_CODE = gI_POSTAL_CODE;
	}

	public String getGI_COUNTRY() {
		return GI_COUNTRY;
	}

	public void setGI_COUNTRY(String gI_COUNTRY) {
		GI_COUNTRY = gI_COUNTRY;
	}

	public String getGI_FAX() {
		return GI_FAX;
	}

	public void setGI_FAX(String gI_FAX) {
		GI_FAX = gI_FAX;
	}

	public String getGI_ACCOUNT() {
		return GI_ACCOUNT;
	}

	public void setGI_ACCOUNT(String gI_ACCOUNT) {
		GI_ACCOUNT = gI_ACCOUNT;
	}

}
