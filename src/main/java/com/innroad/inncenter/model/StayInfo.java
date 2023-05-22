package com.innroad.inncenter.model;

public class StayInfo {

	private String SI_PROPERTY_NAME;
	private String SI_RATE_PLAN_NAME;
	private String SI_CHECK_IN_DATE;
	private String SI_CHECK_OUT_DATE;
	private String SI_NUMBER_OF_NIGHTS;
	private String SI_NUMBER_OF_ADULTS;
	private String SI_NUMBER_OF_CHILDS;
	private String SI_ROOMCLASS_NAME;
	private String SI_ROOM_NUMBER;
	private String SI_ROOM_TOTAL;
	
	
	@Override
	public String toString() {
		
		return "STAY INFO : "
				+ "\n Property Name : " + SI_PROPERTY_NAME 
				+ "\n Rate Plan Name : " + SI_RATE_PLAN_NAME
				+ "\n Check In Date : " + SI_CHECK_IN_DATE
				+ "\n Check Out Date : " + SI_CHECK_OUT_DATE
				+ "\n Number of Nights : " + SI_NUMBER_OF_NIGHTS
				+ "\n Number of Adults : " + SI_NUMBER_OF_ADULTS
				+ "\n Number of Childs : " + SI_NUMBER_OF_CHILDS
				+ "\n Room Class Name : " + SI_ROOMCLASS_NAME
				+ "\n Room Number : " + SI_ROOM_NUMBER
				+ "\n Room Total : " + SI_ROOM_TOTAL;
	}

	public StayInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StayInfo(String sI_PROPERTY_NAME, String sI_RATE_PLAN_NAME, String sI_CHECK_IN_DATE,
			String sI_CHECK_OUT_DATE, String sI_NUMBER_OF_NIGHTS, String sI_NUMBER_OF_ADULTS,
			String sI_NUMBER_OF_CHILDS, String sI_ROOMCLASS_NAME, String sI_ROOM_NUMBER, String sI_ROOM_TOTAL) {
		super();
		SI_PROPERTY_NAME = sI_PROPERTY_NAME;
		SI_RATE_PLAN_NAME = sI_RATE_PLAN_NAME;
		SI_CHECK_IN_DATE = sI_CHECK_IN_DATE;
		SI_CHECK_OUT_DATE = sI_CHECK_OUT_DATE;
		SI_NUMBER_OF_NIGHTS = sI_NUMBER_OF_NIGHTS;
		SI_NUMBER_OF_ADULTS = sI_NUMBER_OF_ADULTS;
		SI_NUMBER_OF_CHILDS = sI_NUMBER_OF_CHILDS;
		SI_ROOMCLASS_NAME = sI_ROOMCLASS_NAME;
		SI_ROOM_NUMBER = sI_ROOM_NUMBER;
		SI_ROOM_TOTAL = sI_ROOM_TOTAL;
	}

	public String getSI_PROPERTY_NAME() {
		return SI_PROPERTY_NAME;
	}

	public void setSI_PROPERTY_NAME(String sI_PROPERTY_NAME) {
		SI_PROPERTY_NAME = sI_PROPERTY_NAME;
	}

	public String getSI_RATE_PLAN_NAME() {
		return SI_RATE_PLAN_NAME;
	}

	public void setSI_RATE_PLAN_NAME(String sI_RATE_PLAN_NAME) {
		SI_RATE_PLAN_NAME = sI_RATE_PLAN_NAME;
	}

	public String getSI_CHECK_IN_DATE() {
		return SI_CHECK_IN_DATE;
	}

	public void setSI_CHECK_IN_DATE(String sI_CHECK_IN_DATE) {
		SI_CHECK_IN_DATE = sI_CHECK_IN_DATE;
	}

	public String getSI_CHECK_OUT_DATE() {
		return SI_CHECK_OUT_DATE;
	}

	public void setSI_CHECK_OUT_DATE(String sI_CHECK_OUT_DATE) {
		SI_CHECK_OUT_DATE = sI_CHECK_OUT_DATE;
	}

	public String getSI_NUMBER_OF_NIGHTS() {
		return SI_NUMBER_OF_NIGHTS;
	}

	public void setSI_NUMBER_OF_NIGHTS(String sI_NUMBER_OF_NIGHTS) {
		SI_NUMBER_OF_NIGHTS = sI_NUMBER_OF_NIGHTS;
	}

	public String getSI_NUMBER_OF_ADULTS() {
		return SI_NUMBER_OF_ADULTS;
	}

	public void setSI_NUMBER_OF_ADULTS(String sI_NUMBER_OF_ADULTS) {
		SI_NUMBER_OF_ADULTS = sI_NUMBER_OF_ADULTS;
	}

	public String getSI_NUMBER_OF_CHILDS() {
		return SI_NUMBER_OF_CHILDS;
	}

	public void setSI_NUMBER_OF_CHILDS(String sI_NUMBER_OF_CHILDS) {
		SI_NUMBER_OF_CHILDS = sI_NUMBER_OF_CHILDS;
	}

	public String getSI_ROOMCLASS_NAME() {
		return SI_ROOMCLASS_NAME;
	}

	public void setSI_ROOMCLASS_NAME(String sI_ROOMCLASS_NAME) {
		SI_ROOMCLASS_NAME = sI_ROOMCLASS_NAME;
	}

	public String getSI_ROOM_NUMBER() {
		return SI_ROOM_NUMBER;
	}

	public void setSI_ROOM_NUMBER(String sI_ROOM_NUMBER) {
		SI_ROOM_NUMBER = sI_ROOM_NUMBER;
	}

	public String getSI_ROOM_TOTAL() {
		return SI_ROOM_TOTAL;
	}

	public void setSI_ROOM_TOTAL(String sI_ROOM_TOTAL) {
		SI_ROOM_TOTAL = sI_ROOM_TOTAL;
	}
	
	
	
}
