package com.innroad.inncenter.model;

public class CheckOutSuccessPaymentPopup {

	private String CHECK_OUT_PAYMENT_DATE;
	private String CHECK_OUT_PAYMENT_BALANCE;
	private String CHECK_OUT_PAYPMENT_AMOUNT;
	private String CHECK_OUT_PAYMENT_TYPE;
	private String CHECK_OUT_PAYMENT_METHOD;
	private String CHECK_OUT_PAYMENT_STATUS;
	public CheckOutSuccessPaymentPopup(String cHECK_OUT_PAYMENT_DATE,String cHECK_OUT_PAYMENT_BALANCE, String cHECK_OUT_PAYPMENT_AMOUNT,
			String cHECK_OUT_PAYMENT_TYPE, String cHECK_OUT_PAYMENT_METHOD, String cHECK_OUT_PAYMENT_STATUS) {
		super();
		CHECK_OUT_PAYMENT_DATE = cHECK_OUT_PAYMENT_DATE;
		CHECK_OUT_PAYMENT_BALANCE = cHECK_OUT_PAYMENT_BALANCE;
		CHECK_OUT_PAYPMENT_AMOUNT = cHECK_OUT_PAYPMENT_AMOUNT;
		CHECK_OUT_PAYMENT_TYPE = cHECK_OUT_PAYMENT_TYPE;
		CHECK_OUT_PAYMENT_METHOD = cHECK_OUT_PAYMENT_METHOD;
		CHECK_OUT_PAYMENT_STATUS = cHECK_OUT_PAYMENT_STATUS;
	}
	public CheckOutSuccessPaymentPopup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCHECK_OUT_PAYMENT_DATE() {
		return CHECK_OUT_PAYMENT_DATE;
	}
	public void setCHECK_OUT_PAYMENT_DATE(String cHECK_OUT_PAYMENT_DATE) {
		CHECK_OUT_PAYMENT_DATE = cHECK_OUT_PAYMENT_DATE;
	}
	public String getCHECK_OUT_PAYMENT_BALANCE() {
		return CHECK_OUT_PAYMENT_BALANCE;
	}
	public void setCHECK_OUT_PAYMENT_BALANCE(String cHECK_OUT_PAYMENT_BALANCE) {
		CHECK_OUT_PAYMENT_BALANCE = cHECK_OUT_PAYMENT_BALANCE;
	}
	public String getCHECK_OUT_PAYPMENT_AMOUNT() {
		return CHECK_OUT_PAYPMENT_AMOUNT;
	}
	public void setCHECK_OUT_PAYPMENT_AMOUT(String cHECK_OUT_PAYPMENT_AMOUNT) {
		CHECK_OUT_PAYPMENT_AMOUNT = cHECK_OUT_PAYPMENT_AMOUNT;
	}
	public String getCHECK_OUT_PAYMENT_TYPE() {
		return CHECK_OUT_PAYMENT_TYPE;
	}
	public void setCHECK_OUT_PAYMENT_TYPE(String cHECK_OUT_PAYMENT_TYPE) {
		CHECK_OUT_PAYMENT_TYPE = cHECK_OUT_PAYMENT_TYPE;
	}
	public String getCHECK_OUT_PAYMENT_METHOD() {
		return CHECK_OUT_PAYMENT_METHOD;
	}
	public void setCHECK_OUT_PAYMENT_METHOD(String cHECK_OUT_PAYMENT_METHOD) {
		CHECK_OUT_PAYMENT_METHOD = cHECK_OUT_PAYMENT_METHOD;
	}	
	public String getCHECK_OUT_PAYMENT_STATUS() {
		return CHECK_OUT_PAYMENT_STATUS;
	}
	public void setCHECK_OUT_PAYMENT_STATUS(String cHECK_OUT_PAYMENT_STATUS) {
		CHECK_OUT_PAYMENT_STATUS = cHECK_OUT_PAYMENT_STATUS;
	}
	
	@Override
	public String toString() {
		
		return "Check Out Payment Popup : \n"
				+ "Payment Date : " + CHECK_OUT_PAYMENT_DATE
				+ "\n Payment Balance : " + CHECK_OUT_PAYMENT_BALANCE
				+ "\n Payment Amount : " + CHECK_OUT_PAYPMENT_AMOUNT
				+ "\n Payment Type : " + CHECK_OUT_PAYMENT_TYPE
				+ "\n Payment Method : + CHECK_OUT_PAYMENT_METHOD"
				+ "\n Payment Status : +CHECK_OUT_PAYMENT_STATUS";
	}
	
	
}