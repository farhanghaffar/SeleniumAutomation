package com.innroad.inncenter.model;

public class TripSummary {

	private String TS_ROOM_CHARGE;
	private String TS_STAY_DATE_RANGE;
	private String TS_OCCUPANTS;
	private String TS_ROOM_DETAIL;
	private String TS_INCIDENTALS;
	private String TS_FEES;
	private String TS_TAXES;
	private String TS_TRIP_TOTAL;
	private String TS_PAID;
	private String TS_BALANCE;

	@Override
	public String toString() {

		return "TRIP SUMMARY : " + "\n Room Charge : " + TS_ROOM_CHARGE + "\n Incidentals : " + TS_INCIDENTALS
				+ "\n Fees : " + TS_FEES + "\n Taxes : " + TS_TAXES + "\n Trip Total : " + TS_TRIP_TOTAL + "\n Paid : "
				+ TS_PAID + "\n Balance : " + TS_BALANCE;
	}

	public TripSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public TripSummary(String tS_ROOM_CHARGE, String tS_STAY_DATE_RANGE, String tS_OCCUPANTS, String tS_ROOM_DETAIL,
			String tS_INCIDENTALS, String tS_FEES, String tS_TAXES, String tS_TRIP_TOTAL, String tS_PAID,
			String tS_BALANCE) {
		super();
		TS_ROOM_CHARGE = tS_ROOM_CHARGE;
		TS_STAY_DATE_RANGE = tS_STAY_DATE_RANGE;
		TS_OCCUPANTS = tS_OCCUPANTS;
		TS_ROOM_DETAIL = tS_ROOM_DETAIL;
		TS_INCIDENTALS = tS_INCIDENTALS;
		TS_FEES = tS_FEES;
		TS_TAXES = tS_TAXES;
		TS_TRIP_TOTAL = tS_TRIP_TOTAL;
		TS_PAID = tS_PAID;
		TS_BALANCE = tS_BALANCE;
	}

	public String getTS_ROOM_CHARGE() {
		return TS_ROOM_CHARGE;
	}

	public void setTS_ROOM_CHARGE(String tS_ROOM_CHARGE) {
		TS_ROOM_CHARGE = tS_ROOM_CHARGE;
	}

	public String getTS_STAY_DATE_RANGE() {
		return TS_STAY_DATE_RANGE;
	}

	public void setTS_STAY_DATE_RANGE(String tS_STAY_DATE_RANGE) {
		TS_STAY_DATE_RANGE = tS_STAY_DATE_RANGE;
	}

	public String getTS_OCCUPANTS() {
		return TS_OCCUPANTS;
	}

	public void setTS_OCCUPANTS(String tS_OCCUPANTS) {
		TS_OCCUPANTS = tS_OCCUPANTS;
	}

	public String getTS_ROOM_DETAIL() {
		return TS_ROOM_DETAIL;
	}

	public void setTS_ROOM_DETAIL(String tS_ROOM_DETAIL) {
		TS_ROOM_DETAIL = tS_ROOM_DETAIL;
	}

	public String getTS_INCIDENTALS() {
		return TS_INCIDENTALS;
	}

	public void setTS_INCIDENTALS(String tS_INCIDENTALS) {
		TS_INCIDENTALS = tS_INCIDENTALS;
	}

	public String getTS_FEES() {
		return TS_FEES;
	}

	public void setTS_FEES(String tS_FEES) {
		TS_FEES = tS_FEES;
	}

	public String getTS_TAXES() {
		return TS_TAXES;
	}

	public void setTS_TAXES(String tS_TAXES) {
		TS_TAXES = tS_TAXES;
	}

	public String getTS_TRIP_TOTAL() {
		return TS_TRIP_TOTAL;
	}

	public void setTS_TRIP_TOTAL(String tS_TRIP_TOTAL) {
		TS_TRIP_TOTAL = tS_TRIP_TOTAL;
	}

	public String getTS_PAID() {
		return TS_PAID;
	}

	public void setTS_PAID(String tS_PAID) {
		TS_PAID = tS_PAID;
	}

	public String getTS_BALANCE() {
		return TS_BALANCE;
	}

	public void setTS_BALANCE(String tS_BALANCE) {
		TS_BALANCE = tS_BALANCE;
	}

}
