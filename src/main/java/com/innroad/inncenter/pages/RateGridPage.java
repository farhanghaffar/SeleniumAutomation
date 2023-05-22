package com.innroad.inncenter.pages;

import java.util.ArrayList;

import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_RatesGrid;

public class RateGridPage {

	
	public static final String RATE_GRID_CALENDAR_ICON = "//*[@id='daterange-calendar']";
	public static final String DATE_FROM_GRID_HEADER = "//*[@id='daterange-calendar']/../../../following-sibling::div/div[1]/div/div";
	public static final String TOTAL_OCCUPANCY_TABLE_DATE_COLUMNS = "//div[contains(@class,'MonthDateCell')]";
	public static final String ROOMCLASSES_TABLE_DATE_COLUMNS = "//div[@class='DatesTable'][1]//div[contains(@class,'RateCellStyle')]";
	
	public static final String ADD_RATE_PLAN = "//button[text()='Add Rate Plan']";
	// interval rate plan
	public static final String GetTitile = "//span[@class='stepTitle']";
	public static final String EnterInterval = "//input[@class='ant-input-number-input']";
	public static final String CheckboxProrateForEachSeasonbyDefault = "//span[text()='Pro-rate each season by default']";
	public static final String TextAfterCheckedProRateCheckbox = "//span[@class='subText']";
	public static final String ChargeForAdditionalAdultsAndChildern = "//button[contains(@class,'ant-switch  inrd-switch')]";
	public static final String CheckboxProrate = "(//span[text()='Pro-rate stay'])[1]";
	public static final String totalNumberofRoomClasses = "//li[@class='IntervalRatePlan line']";
	public static final String VerifyDefaultCheckbox = "//span[text()='Pro-rate each season by default']//parent::label";
	public static final String AddMoreRoomClassesButton = "//div[@class='addRoomClass']//i[@aria-label='icon: plus-circle']";
	public static final String CloseExpandRoomClassesList = "closeLink";
/*	public static final String OpenRateDropdown = "//div[@class='rateplan-arrow open']";
	public static final String SearchRatPlan = "//div[@class='Select-input']/input";
	public static final String SelectRatePlan = "//div[@class='Select-menu-outer']";
	public static final String SelectedRatePlan = "//span[@class='rate-plan-picker-label']";*/

/*	public static final String OpenRateDropdown = "//div[@class='rateplan-arrow open']";
	public static final String SearchRatPlan = "//div[@class='Select-input']/input";
	public static final String SelectRatePlan = "//div[@class='Select-menu-outer']";
	public static final String SelectedRatePlan = "//span[@class='rate-plan-picker-label']";*/

	public static final String SearchInput  = "//div[@class='Select-input']//input";

	//public static final String SelectedRatePlan  = "//div[@class='Select-value']//span";

//	public static final String SelectedRatePlan  = "//div[@class='Select-value']//span";

	public static final String ClickOnEditRatePlan = "//i[contains(@class,'icon-edit')]";
	public static final String ratePlanOverView = "//h2[text()='Overview']";
	public static final String ProRateCheckbox = "(//span[text()='Pro-rate each season by default']//..//span[contains(@class,'ant-checkbox')])[1]";


	public static final String RestricationsAndPoliciesTab = "//div[text()='Restrictions & Policies']";
	public static final String SeasonTab = "//div[text()='Calendar']";
	public static final String SaveRatePlan = "//span[contains(@class,'save-plan-btn')]";
	public static final String ClickOnCapcity = "(//span[@class='small'])[1]";

	public static final String LengthOfStay = "//span[text()='Length of stay']//parent::label";
	public static final String ClickOnRightArrowOfDate = "(//span[@class='right-arrow'])[1]";

	public static final String OpenRateDropdown = "//div[@class='rateplan-arrow open']";
	public static final String SearchRatPlan = "//div[@class='Select-input']/input";
	public static final String SelectRatePlan = "//div[@class='Select-menu-outer']";
	public static final String SelectedRatePlan = "//span[@class='rate-plan-picker-label']";
	
	
	public static final String checkboxMon = "//span[text()='Mon']//parent::label/span[2]";
	public static final String checkboxTue = "//span[text()='Tue']//parent::label/span[2]";
	public static final String checkboxWed = "//span[text()='Wed']//parent::label/span[2]";
	public static final String checkboxThu = "//span[text()='Thu']//parent::label/span[2]";
	public static final String checkboxFri = "//span[text()='Fri']//parent::label/span[2]";
	public static final String checkboxSat = "//span[text()='Sat']//parent::label/span[2]";
	public static final String checkboxSun = "//span[text()='Sun']//parent::label/span[2]";
	public static final String ProRateCheckboxMessage = "//span[text()='Pro-rate each season by default']//parent::label//following-sibling::div/span[@class='subText']";

	public static final String SaveRatePlanButton = "//span[contains(@class,'save-plan-btn')]/button";	
	public static final String ProductTab = "//div[text()='Products']";
	public static final String productValue ="//input[@class='ant-input-number-input' and @name='price']";
	public static final String firstCalculationMethodDropDownPath = "(//i[@class='anticon anticon-down ant-select-arrow-icon'])[2]";
	public static final String secondCalculationMethodDropDownPath = "(//i[@class='anticon anticon-down ant-select-arrow-icon'])[3]";
	public static final String ToasterMessage = "//div[@class='Toastify']//div//div[contains(@class,'Toastify__toast-body')]";
}
