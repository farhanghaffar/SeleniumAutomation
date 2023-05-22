package com.innroad.inncenter.model;

public class MarketingInfo {
	private String MI_MARKET_SEGMENT;
	private String MI_MARKET_REFERRAL;
	private String MI_EXTERNAL_CONFIRMATION_NUMBER;
	private String MI_MARKET_SOURCE;
	private String MI_MARKET_SUB_SOURCE;
	public MarketingInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MarketingInfo(String mI_MARKET_SEGMENT, String mI_MARKET_REFERRAL, String mI_EXTERNAL_CONFIRMATION_NUMBER,
			String mI_MARKET_SOURCE, String mI_MARKET_SUB_SOURCE) {
		super();
		MI_MARKET_SEGMENT = mI_MARKET_SEGMENT;
		MI_MARKET_REFERRAL = mI_MARKET_REFERRAL;
		MI_EXTERNAL_CONFIRMATION_NUMBER = mI_EXTERNAL_CONFIRMATION_NUMBER;
		MI_MARKET_SOURCE = mI_MARKET_SOURCE;
		MI_MARKET_SUB_SOURCE = mI_MARKET_SUB_SOURCE;
	}
	public String getMI_MARKET_SEGMENT() {
		return MI_MARKET_SEGMENT;
	}
	public void setMI_MARKET_SEGMENT(String mI_MARKET_SEGMENT) {
		MI_MARKET_SEGMENT = mI_MARKET_SEGMENT;
	}
	public String getMI_MARKET_REFERRAL() {
		return MI_MARKET_REFERRAL;
	}
	public void setMI_MARKET_REFERRAL(String mI_MARKET_REFERRAL) {
		MI_MARKET_REFERRAL = mI_MARKET_REFERRAL;
	}
	public String getMI_EXTERNAL_CONFIRMATION_NUMBER() {
		return MI_EXTERNAL_CONFIRMATION_NUMBER;
	}
	public void setMI_EXTERNAL_CONFIRMATION_NUMBER(String mI_EXTERNAL_CONFIRMATION_NUMBER) {
		MI_EXTERNAL_CONFIRMATION_NUMBER = mI_EXTERNAL_CONFIRMATION_NUMBER;
	}
	public String getMI_MARKET_SOURCE() {
		return MI_MARKET_SOURCE;
	}
	public void setMI_MARKET_SOURCE(String mI_MARKET_SOURCE) {
		MI_MARKET_SOURCE = mI_MARKET_SOURCE;
	}
	public String getMI_MARKET_SUB_SOURCE() {
		return MI_MARKET_SUB_SOURCE;
	}
	public void setMI_MARKET_SUB_SOURCE(String mI_MARKET_SUB_SOURCE) {
		MI_MARKET_SUB_SOURCE = mI_MARKET_SUB_SOURCE;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MARKETING INFO : "
				+ "\n Market Segment : " + MI_MARKET_SEGMENT
				+ "\n Market REferral : " + MI_MARKET_REFERRAL
				+ "\n External Confirmation No : " + MI_EXTERNAL_CONFIRMATION_NUMBER
				+"\n Market Source : " + MI_MARKET_SOURCE
				+ "\n Market Sub Source : " + MI_MARKET_SUB_SOURCE;
	}

	
}
