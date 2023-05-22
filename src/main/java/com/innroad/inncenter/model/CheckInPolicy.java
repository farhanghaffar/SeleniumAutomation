package com.innroad.inncenter.model;

public class CheckInPolicy {

	private String policyName;
	private String clauseName;
	private String uponCheckIn;
	private String authType;
	private String policyDesc;
	public CheckInPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CheckInPolicy(String policyName, String clauseName, String uponCheckIn, String authType, String policyDesc) {
		super();
		this.policyName = policyName;
		this.clauseName = clauseName;
		this.uponCheckIn = uponCheckIn;
		this.authType = authType;
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
	public String getUponCheckIn() {
		return uponCheckIn;
	}
	public void setUponCheckIn(String uponCheckIn) {
		this.uponCheckIn = uponCheckIn;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getPolicyDesc() {
		return policyDesc;
	}
	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}
	@Override
	public String toString() {
		return "CHECK I POLICY : "
				+ "\n Policy name : " + policyName
				+ "\n Clause Name : " + clauseName
				+ "\n Upon Check In : " + uponCheckIn
				+ "\n Auth Type : " + authType
				+ "\n Policy Desc : " + policyDesc;
	}
	
	
}
