package com.innroad.inncenter.model;

public class RefundInfo 
{
	private String refundPopupHeader;
	private String refundPopupAmount;
	//private String RI_PAY_OR_REFUND_TYPE;
	private String REFUND_PAYMENT_METHOD;
	private String REFUND_ADD_NOTES_BUTTON;
	private String REFUND_NOTES;
	private String refundPopupRefundButton;
	
	@Override
	public String toString() {
		
		return "REFUND INFO : "
				+ "\n Refund Header : " + refundPopupHeader
				+ "\n Refund Amount : " + refundPopupAmount
				+ "\n Payment Method : " + REFUND_PAYMENT_METHOD
				+ "\n Add Notes : " + REFUND_ADD_NOTES_BUTTON
				+ "\n Notes Text : " + REFUND_NOTES;
	}
	
	public RefundInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RefundInfo(String rI_REFUND_HEADER, String rI_REFUND_AMOUNT,
			String rI_PAY_METHOD, String rI_ADD_NOTES, String rI_NOTES) {
		super();
		refundPopupHeader = rI_REFUND_HEADER;
		refundPopupAmount = rI_REFUND_AMOUNT;
		REFUND_PAYMENT_METHOD = rI_PAY_METHOD;
		REFUND_ADD_NOTES_BUTTON = rI_ADD_NOTES;
		REFUND_NOTES = rI_NOTES;
	}

	public String getrefundPopupHeader() {
		return refundPopupHeader;
	}

	public void setrefundPopupHeader(String rI_REFUND_HEADER) {
		refundPopupHeader = rI_REFUND_HEADER;
	}

	public String getrefundPopupAmount() {
		return refundPopupAmount;
	}

	public void setrefundPopupAmount(String rI_REFUND_AMOUNT) {
		refundPopupAmount = rI_REFUND_AMOUNT;
	}
	
	public String getREFUND_PAYMENT_METHOD() {
		return REFUND_PAYMENT_METHOD;
	}

	public void setREFUND_PAYMENT_METHOD(String rI_PAY_METHOD) {
		REFUND_PAYMENT_METHOD = rI_PAY_METHOD;
	}

	public String getREFUND_ADD_NOTES_BUTTON() {
		return REFUND_ADD_NOTES_BUTTON;
	}

	public void setREFUND_ADD_NOTES_BUTTON(String rI_ADD_NOTES) {
		REFUND_ADD_NOTES_BUTTON = rI_ADD_NOTES;
	}
	
	public String getREFUND_NOTES() {
		return REFUND_NOTES;
	}

	public void setREFUND_NOTES(String rI_NOTES) {
		REFUND_NOTES = rI_NOTES;
	}
}
