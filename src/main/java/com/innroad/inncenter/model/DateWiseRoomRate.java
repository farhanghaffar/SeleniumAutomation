package com.innroad.inncenter.model;

public class DateWiseRoomRate {
	private String DESC_DATE;
	private String DESC_RATE_PLAN_NAME;
	private String DESC_AMOUNT;

	public DateWiseRoomRate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DateWiseRoomRate(String dESC_DATE, String dESC_RATE_PLAN_NAME, String dESC_AMOUNT) {
		super();
		DESC_DATE = dESC_DATE;
		DESC_RATE_PLAN_NAME = dESC_RATE_PLAN_NAME;
		DESC_AMOUNT = dESC_AMOUNT;
	}

	public String getDESC_DATE() {
		return DESC_DATE;
	}

	public void setDESC_DATE(String dESC_DATE) {
		DESC_DATE = dESC_DATE;
	}

	public String getDESC_RATE_PLAN_NAME() {
		return DESC_RATE_PLAN_NAME;
	}

	public void setDESC_RATE_PLAN_NAME(String dESC_RATE_PLAN_NAME) {
		DESC_RATE_PLAN_NAME = dESC_RATE_PLAN_NAME;
	}

	public String getDESC_AMOUNT() {
		return DESC_AMOUNT;
	}

	public void setDESC_AMOUNT(String dESC_AMOUNT) {
		DESC_AMOUNT = dESC_AMOUNT;
	}

	@Override
	public String toString() {
		return "DateWiseRoomRate [DESC_DATE=" + DESC_DATE + ", DESC_RATE_PLAN_NAME=" + DESC_RATE_PLAN_NAME
				+ ", DESC_AMOUNT=" + DESC_AMOUNT + "]";
	}

}
