package com.innroad.inncenter.model;

public class ReservationPolicies {

	private String POLICY_TYPE;
	private String POLICY_TITLE;
	private String POLICY_DESC;
	public ReservationPolicies() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReservationPolicies(String pOLICY_TYPE, String pOLICY_TITLE, String pOLICY_DESC) {
		super();
		POLICY_TYPE = pOLICY_TYPE;
		POLICY_TITLE = pOLICY_TITLE;
		POLICY_DESC = pOLICY_DESC;
	}
	public String getPOLICY_TYPE() {
		return POLICY_TYPE;
	}
	public void setPOLICY_TYPE(String pOLICY_TYPE) {
		POLICY_TYPE = pOLICY_TYPE;
	}
	public String getPOLICY_TITLE() {
		return POLICY_TITLE;
	}
	public void setPOLICY_TITLE(String pOLICY_TITLE) {
		POLICY_TITLE = pOLICY_TITLE;
	}
	public String getPOLICY_DESC() {
		return POLICY_DESC;
	}
	public void setPOLICY_DESC(String pOLICY_DESC) {
		POLICY_DESC = pOLICY_DESC;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "POLICY :"
				+ "\n Policy Type : " + POLICY_TYPE
				+ "\n Policy Title : " + POLICY_TITLE
				+ "\n Policy Desc : " + POLICY_DESC;
	}
	
	
	
}
