package com.innroad.inncenter.model;

import java.util.HashMap;

public class FindRoomDetail {

	private String ROOM_CLASS_NAME;
	private String RATE_PLAN_NAME;
	private String AVAILABLE_ROOM;
	private String ROOM_TOTAL_AMOUNT;
	private HashMap<String, DateWiseRoomRate> dateWiseRoomRate;

	public FindRoomDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FindRoomDetail(String rOOM_CLASS_NAME, String rATE_PLAN_NAME, String aVAILABLE_ROOM,
			String rOOM_TOTAL_AMOUNT, HashMap<String, DateWiseRoomRate> dateWiseRoomRate) {
		super();
		ROOM_CLASS_NAME = rOOM_CLASS_NAME;
		RATE_PLAN_NAME = rATE_PLAN_NAME;
		AVAILABLE_ROOM = aVAILABLE_ROOM;
		ROOM_TOTAL_AMOUNT = rOOM_TOTAL_AMOUNT;
		this.dateWiseRoomRate = dateWiseRoomRate;
	}

	public String getROOM_CLASS_NAME() {
		return ROOM_CLASS_NAME;
	}

	public void setROOM_CLASS_NAME(String rOOM_CLASS_NAME) {
		ROOM_CLASS_NAME = rOOM_CLASS_NAME;
	}

	public String getRATE_PLAN_NAME() {
		return RATE_PLAN_NAME;
	}

	public void setRATE_PLAN_NAME(String rATE_PLAN_NAME) {
		RATE_PLAN_NAME = rATE_PLAN_NAME;
	}

	public String getAVAILABLE_ROOM() {
		return AVAILABLE_ROOM;
	}

	public void setAVAILABLE_ROOM(String aVAILABLE_ROOM) {
		AVAILABLE_ROOM = aVAILABLE_ROOM;
	}

	public String getROOM_TOTAL_AMOUNT() {
		return ROOM_TOTAL_AMOUNT;
	}

	public void setROOM_TOTAL_AMOUNT(String rOOM_TOTAL_AMOUNT) {
		ROOM_TOTAL_AMOUNT = rOOM_TOTAL_AMOUNT;
	}

	public HashMap<String, DateWiseRoomRate> getDateWiseRoomRate() {
		return dateWiseRoomRate;
	}

	public void setDateWiseRoomRate(HashMap<String, DateWiseRoomRate> dateWiseRoomRate) {
		this.dateWiseRoomRate = dateWiseRoomRate;
	}

	@Override
	public String toString() {
		return "FindRoomDetail [ROOM_CLASS_NAME=" + ROOM_CLASS_NAME + ", RATE_PLAN_NAME=" + RATE_PLAN_NAME
				+ ", AVAILABLE_ROOM=" + AVAILABLE_ROOM + ", ROOM_TOTAL_AMOUNT=" + ROOM_TOTAL_AMOUNT
				+ ", dateWiseRoomRate=" + dateWiseRoomRate + "]";
	}

	

}
