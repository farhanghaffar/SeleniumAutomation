package com.innroad.inncenter.model;

public class ReservationStatusBar {

	private String SB_GUEST_NAME;
	private String SB_RESERVATION_STATUS;
	private String SB_CONFIRMATION_NO;
	private String SB_STAY_DURATION;
	private String SB_PHONE_NO;
	private String SB_EMAIL;
	private String SB_TRIP_SUMMARAY;
	private String SB_BALANCE;

	@Override
	public String toString() {

		return "RESERVATION STATUS BAR \n" + "Guest Name : " + SB_GUEST_NAME + 
				"\n Reservation Status : " + SB_RESERVATION_STATUS + 
				"\n Confirmation No : " + SB_CONFIRMATION_NO +
				"\n Stay Duration : " + SB_STAY_DURATION + 
				"\n Phone No : " + SB_PHONE_NO + 
				"\n Email : " + SB_EMAIL + 
				"\n Trip Summary : " + SB_TRIP_SUMMARAY + 
				"\n Balance : " + SB_BALANCE;
	}

	public ReservationStatusBar() {
		super();
	}

	public ReservationStatusBar(String sB_GUEST_NAME, String sB_RESERVATION_STATUS, String sB_CONFIRMATION_NO,
			String sB_STAY_DURATION, String sB_PHONE_NO, String sB_EMAIL, String sB_TRIP_SUMMARAY, String sB_BALANCE) {
		super();
		SB_GUEST_NAME = sB_GUEST_NAME;
		SB_RESERVATION_STATUS = sB_RESERVATION_STATUS;
		SB_CONFIRMATION_NO = sB_CONFIRMATION_NO;
		SB_STAY_DURATION = sB_STAY_DURATION;
		SB_PHONE_NO = sB_PHONE_NO;
		SB_EMAIL = sB_EMAIL;
		SB_TRIP_SUMMARAY = sB_TRIP_SUMMARAY;
		SB_BALANCE = sB_BALANCE;
	}

	public String getSB_GUEST_NAME() {
		return SB_GUEST_NAME;
	}

	public void setSB_GUEST_NAME(String sB_GUEST_NAME) {
		SB_GUEST_NAME = sB_GUEST_NAME;
	}

	public String getSB_RESERVATION_STATUS() {
		return SB_RESERVATION_STATUS;
	}

	public void setSB_RESERVATION_STATUS(String sB_RESERVATION_STATUS) {
		SB_RESERVATION_STATUS = sB_RESERVATION_STATUS;
	}

	public String getSB_CONFIRMATION_NO() {
		return SB_CONFIRMATION_NO;
	}

	public void setSB_CONFIRMATION_NO(String sB_CONFIRMATION_NO) {
		SB_CONFIRMATION_NO = sB_CONFIRMATION_NO;
	}

	public String getSB_STAY_DURATION() {
		return SB_STAY_DURATION;
	}

	public void setSB_STAY_DURATION(String sB_STAY_DURATION) {
		SB_STAY_DURATION = sB_STAY_DURATION;
	}

	public String getSB_PHONE_NO() {
		return SB_PHONE_NO;
	}

	public void setSB_PHONE_NO(String sB_PHONE_NO) {
		SB_PHONE_NO = sB_PHONE_NO;
	}

	public String getSB_EMAIL() {
		return SB_EMAIL;
	}

	public void setSB_EMAIL(String sB_EMAIL) {
		SB_EMAIL = sB_EMAIL;
	}

	public String getSB_TRIP_SUMMARAY() {
		return SB_TRIP_SUMMARAY;
	}

	public void setSB_TRIP_SUMMARAY(String sB_TRIP_SUMMARAY) {
		SB_TRIP_SUMMARAY = sB_TRIP_SUMMARAY;
	}

	public String getSB_BALANCE() {
		return SB_BALANCE;
	}

	public void setSB_BALANCE(String sB_BALANCE) {
		SB_BALANCE = sB_BALANCE;
	}

}
