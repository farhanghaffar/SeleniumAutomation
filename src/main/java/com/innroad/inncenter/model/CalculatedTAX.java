package com.innroad.inncenter.model;

public class CalculatedTAX {

	private String roomCharge;
	private String tax;
	private String totalAmount;
	public CalculatedTAX() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CalculatedTAX(String roomCharge, String tax, String totalAmount) {
		super();
		this.roomCharge = roomCharge;
		this.tax = tax;
		this.totalAmount = totalAmount;
	}
	public String getRoomCharge() {
		return roomCharge;
	}
	public void setRoomCharge(String roomCharge) {
		this.roomCharge = roomCharge;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
