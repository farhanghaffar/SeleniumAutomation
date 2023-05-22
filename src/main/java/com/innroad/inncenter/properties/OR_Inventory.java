package com.innroad.inncenter.properties;

public class OR_Inventory {
	
	public static final String NoRecordMeet = "//span[@id='MainContent_LblError']";
	public static final String rateDialogCloseLink = "dialog-close0";
	
	//Policies Search
	public static final String Select_PolicyType = "//select[contains(@data-bind,'options: policyType')]";
	public static final String Table_PolicyType_Column = "(//div[@class='table-responsive'])[1]//tbody//tr/td[2]/span";
	
	public static final String AvailabilityTab = "//div[@id='rates-and-availability-tabs']//a[.='Availability']";
	public static final String RatsTab = "//div[@id='rates-and-availability-tabs']//a[.='Rates']";
	
	public static final String linkDemandManagement = "//a[@id='MainContent_rptrMenu_lbtnAltMenuItem_1']";
	public static final String linkGoals = "//a[text()='Goals']";
	
	public static final String NO_CHECK_IN_RULES_CHECK_BOX="//input[@value='noCheckIn']";
	public static final String NO_CHECK_OUT_RULES_CHECK_BOX="//input[@value='noCheckOut']";
	
	public static final String NO_CHECK_IN_RULES_DAYSOFWEEK_CHECK_BOXES="//input[@value='noCheckIn']/ancestor::label/following-sibling::div//label//span[contains(@class,'checkmark')]";
	public static final String NO_CHECK_OUT_RULES_DAYSOFWEEK_CHECK_BOXES="//input[@value='noCheckOut']/ancestor::label/following-sibling::div//label//span[contains(@class,'checkmark')]";


	public static final String rulesDetails = "//div[text()='Rule Details']";
	public static final String rulesAttributes = "//div[text()='Rule Attributes']";
	public static final String rulesApplyTo = "//div[text()='Apply To']";
	
	public static final String minLengthOfStay = "MainContent_txtminStay";
	public static final String maxLengthOfStay = "MainContent_txtmaxStay";
}
