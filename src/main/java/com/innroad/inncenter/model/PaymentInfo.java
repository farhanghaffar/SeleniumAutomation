package com.innroad.inncenter.model;

public class PaymentInfo {

	private String PI_BILLING_TYPE_NAME;
	private String PI_LAST_FOUR_DIGITS;
	private String PI_NAME_ON_CARD;
	private String PI_EXPIRY_DATE;

	public PaymentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentInfo(String pI_BILLING_TYPE_NAME, String pI_LAST_FOUR_DIGITS, String pI_NAME_ON_CARD,
			String pI_EXPIRY_DATE) {
		super();
		PI_BILLING_TYPE_NAME = pI_BILLING_TYPE_NAME;
		PI_LAST_FOUR_DIGITS = pI_LAST_FOUR_DIGITS;
		PI_NAME_ON_CARD = pI_NAME_ON_CARD;
		PI_EXPIRY_DATE = pI_EXPIRY_DATE;
	}

	public String getPI_BILLING_TYPE_NAME() {
		return PI_BILLING_TYPE_NAME;
	}

	public void setPI_BILLING_TYPE_NAME(String pI_BILLING_TYPE_NAME) {
		PI_BILLING_TYPE_NAME = pI_BILLING_TYPE_NAME;
	}

	public String getPI_LAST_FOUR_DIGITS() {
		return PI_LAST_FOUR_DIGITS;
	}

	public void setPI_LAST_FOUR_DIGITS(String pI_LAST_FOUR_DIGITS) {
		PI_LAST_FOUR_DIGITS = pI_LAST_FOUR_DIGITS;
	}

	public String getPI_NAME_ON_CARD() {
		return PI_NAME_ON_CARD;
	}

	public void setPI_NAME_ON_CARD(String pI_NAME_ON_CARD) {
		PI_NAME_ON_CARD = pI_NAME_ON_CARD;
	}

	public String getPI_EXPIRY_DATE() {
		return PI_EXPIRY_DATE;
	}

	public void setPI_EXPIRY_DATE(String pI_EXPIRY_DATE) {
		PI_EXPIRY_DATE = pI_EXPIRY_DATE;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PAYMENT INFO : " + "\n Billing Type Name : " + PI_BILLING_TYPE_NAME + "\n Last Four Digits : "
				+ PI_LAST_FOUR_DIGITS + "\n Name ON Card : " + PI_NAME_ON_CARD + "\n Expiry Date : " + PI_EXPIRY_DATE;
	}

}
