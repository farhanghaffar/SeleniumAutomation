package com.innroad.inncenter.model;

public class NoShowPolicy {

	private String policyName;
	private String clauseName;
	private String guestsMustPay;
	private String chargesType;
	private String policyDesc;
	public NoShowPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NoShowPolicy(String policyName, String clauseName, String guestsMustPay, String chargesType,
			String policyDesc) {
		super();
		this.policyName = policyName;
		this.clauseName = clauseName;
		this.guestsMustPay = guestsMustPay;
		this.chargesType = chargesType;
		this.policyDesc = policyDesc;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getClauseName() {
		return clauseName;
	}
	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}
	public String getGuestsMustPay() {
		return guestsMustPay;
	}
	public void setGuestsMustPay(String guestsMustPay) {
		this.guestsMustPay = guestsMustPay;
	}
	public String getChargesType() {
		return chargesType;
	}
	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}
	public String getPolicyDesc() {
		return policyDesc;
	}
	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}
	@Override
	public String toString() {
		
		return "NO SHOW POLICY : "
				+ "\n Policy name : " + policyName
				+ "\n Clause Name : " + clauseName
				+ "\n Guest Must Pay : " + guestsMustPay
				+ "\n Charges Type : " +chargesType
				+ "\n Policy Desc : " + policyDesc;
	}
	
	
}
