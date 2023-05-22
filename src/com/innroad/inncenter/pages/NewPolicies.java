package com.innroad.inncenter.pages;

public class NewPolicies {

	public static final String Select_Cancellation_Beyond_Radiobtn  = "(//*[@id='cancellation']//input[@type='radio'])[2]";
	public static final String Select_Cancellation_Within_Radiobtn  = "(//*[@id='cancellation']//input[@type='radio'])[4]";
	public static final String Cancellation_Attribute_NumberOfDays  = "//div[@id='policyDetail']//input[contains(@data-bind, 'CancelDuration') and @type='text']";
	public static final String Select_Cancellation_Roomcharges_Radiobtn = "(//*[@id='cancellation']//input[@type='radio'])[1]";
	public static final String Select_Cancellation_Fixedamount_Radiobtn = "(//*[@id='cancellation']//input[@type='radio'])[3]";
	public static final String Select_Cancellation_Firstnightrc_Radiobtn = "(//*[@id='cancellation']//input[@type='radio'])[5]";
	
	public static final String Select_Cancellation_Beyond_NumberOfDays  = "(//*[@id='cancellation']//input[@type='text'])[3]";
	public static final String Select_Cancellation_Within_NumberOfDays  = "(//*[@id='cancellation']//input[@type='text'])[7]";
	public static final String Select_Cancellation_Roomcharges_Percentage = "(//*[@id='cancellation']//input[@type='text'])[1]";
	public static final String Select_Cancellation_Fixedamount_Amount = "(//*[@id='cancellation']//input[@type='text'])[5]";
	public static final String Select_Cancellation_Firstnightrc_NumberOfNights = "(//*[@id='cancellation']//input[@type='text'])[9]";
	
	//validations
	public static final String PolicyName_Validation = "//div[contains(@data-bind,'PolicyName')]//following-sibling::span[@class='validationMessage']";
	public static final String PolicyAttributes_Validation = "(//div[contains(@data-bind,'PolicyTypeId')]//following-sibling::span[@class='validationMessage'])[18]";
	public static final String PolicyText_Validation = "//div[contains(@data-bind,'PolicyStatement')]//following-sibling::span[@class='validationMessage']";
	public static final String PolicyAssociateSource_Validation = "//span[@data-bind='validationMessage: PolicySources']";
	public static final String PolicyAssociateRoomClass_Validation = "//span[@data-bind='validationMessage: PolicyRoomClasses']";
	public static final String PolicyAssociateSeasons_Validation = "//span[@data-bind='validationMessage: PolicySeasons']";
	public static final String PolicyAssociateRatePlan_Validation = "//span[@data-bind='validationMessage: PolicyRatePlans']";

	public static final String AddButton  = "//button[@data-bind='click: AddNew']";
	public static final String Selec_Attribute = "//select[contains(@data-bind,'selectedOptions: chosenObjectsToAssign')]";
	public static final String PolicyCheckbox = "//input[@data-bind='checked: deletePolicy']";
	public static final String NumberofRecordPerPage = "//select[contains(@data-bind,'options: recordsPerPage')]";
	public static final String Select_PolicyType = "(//select[contains(@data-bind,'options: policyType')])[2]";
	
	public static final String Select_NoShow_Roomcharges_Percentage = "(//*[@id='noShow']//input[@type='text'])[1]";
	
	public static final String CANCELLATION_POLICY_CREATE_NEW = "//*[contains(text(),'Cancellation')]//div[normalize-space(text())='Create new']";
	public static final String DEPOSIT_POLICY_CREATE_NEW = "//*[contains(text(),'Deposit')]//div[normalize-space(text())='Create new']";
	public static final String CHECKIN_POLICY_CREATE_NEW = "//*[contains(text(),'Check-in')]//div[normalize-space(text())='Create new']";
	public static final String NOSHOW_POLICY_CREATE_NEW = "//*[contains(text(),'No Show')]//div[normalize-space(text())='Create new']";
	public static final String ENTER_POLICY_NAME = "//input[@name='policyTitle']";
	public static final String CUSTOM_TEXT_TOGGLE = "//div[text()='Custom text']/button";
	public static final String CUSTOM_TEXT_AREA = "//textarea[@placeholder='Custom text goes here']";
	public static final String ADD_POLICY_BUTTON = "//span[text()='Add Policy']/..";
	public static final String ADD_NEW_CLAUSE = "//div[contains(text(),'cancellation policy clause')]";
	public static final String UPDATE_POLICY_BUTTON = "//span[text()='Update']/..";
	public static final String All_POLICY_USES = "//ul[@class='policy-uses']//li";
	public static final String DELETE_POLICY_BUTTON = "//span[text()='Delete']/..";
	public static final String POLCIY_NAME_ALREADY_EXIST = "//input[@name='policyTitle']/../following-sibling::span";
}
