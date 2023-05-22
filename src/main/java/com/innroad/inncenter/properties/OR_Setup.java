package com.innroad.inncenter.properties;

public class OR_Setup {


	public static final String EnterSeasonStartDate = "//input[@placeholder='Start Date']";
	public static final String EnterSeasonEndDate = "//input[@placeholder='End Date']";
	public static final String SeasonPagination = "//div[contains(@data-bind,'TotalRecords: totalNumberOfRows')]//ul//li/a";
	public static final String SearchSeason_Btn = "//button[contains(@data-bind,'click: goSearchSeasons')]";
	public static final String CloseSeasonTab = "(//i[contains(@data-bind,'click: $parent.closeTab')])[7]";

	public static final String Roomclass_Pagenation="//small[text()='Items Per Page']/following-sibling::select";
	public static final String Setup_PropertiesGuestStatementOption="//input[@id='MainContent_chkDefaultGenerateGuestStatementOnCheckOut']";
	public static final String Setup_PropertiesGuestRegistrationOption="//input[@id='MainContent_chkDefaultGenerateGuestRegistrationOnCheckIn']";
	public static final String Setup_PropertiesGuestRegistrationOptionCheckout="//input[@id='MainContent_chkDefaultGenerateGuestStatementOnCheckOut']";

	// New Room Class Room Number
	public static final String New_RoomClass_RoomNumbers = "//table[contains(@class,'flip-content')]//tbody[@data-bind='foreach: Rooms']/tr/td[2]//input";

	public static final String PROPERTY_SET_GAURANTEED = "//input[@id='MainContent_chkCreditCardForGuarantee']";
	public static final String Setup_PropertiesGuaranteedOption="//input[@id='MainContent_chkSetReservationToGuaranteed']";
	public static final String PROPERTY_DEPOSITREQUIRED_SAVEGAURANTEEDRESERVATION = "//input[@id='MainContent_chkRequiredDepositForGuaranteedReservation']";
	public static final String PROPERTY_DEPOSITRECORDEDAUTOMATICALLY_SETGAURANTEEDRESERVATION = "//input[@id='MainContent_chkSetReservationToGuaranteed']";


	//Documents templetas
	public static final String FUNCTION_ADD_ATTACHMENTS = "//input[contains(@id,'MainContent_dgScheduleFunctions_txtSources')]";
	public static final String FUNCTION_AVAILABLE_ATTACHEMENTS_LIST= "//select[@id='MainContent_lstSources']";
	public static final String FUNCTION_ADDED_ATTACHEMENTS_LIST= "//select[@id='MainContent_lstPickedSources']";
	public static final String PICK_ONE_BUTTON = "//input[@id='btnPickOne']";
	public static final String DONE_BUTTON = "//input[@value='Done']";

	public static final String NonZeroBalanceWhileCheckOut="//input[@id='MainContent_chkAllowNonZeroBalance']";
	
	
	public static final String  NewPolicies_CreateDepositPolocy="//div[contains(text(),'Deposit')]/div[contains(text(),' Create new')]";
	public static final String  NewPolicies_DepositPolicyHeader="//div[contains(text(),'New deposit policy')]|//div[contains(text(),'Policy Details')]";
	public static final String  NewPolicies_DepositPolicyName="//input[@name='policyTitle']";
	public static final String  NewPolicies_DepositPolicyGuestPayDropDown="//div[@class='ant-select-selection__rendered']";
	public static final String  NewPolicies_DepositPolicyGuestPayPercentage="//div[contains(text(),'When guest books reservation, they must pay')]//input";
	public static final String  NewPolicies_DepositPolicyGuestPayTypeDropDown="//div[contains(text(),'When guest books reservation, they must pay')]//span[contains(@class,'select line')]";
	public static final String  NewPolicies_DepositPolicyCustomTextButton="//div[contains(text(),'Custom text')]/button";
	public static final String  NewPolicies_DepositPolicyCustomText="//textarea[contains(@placeholder,'Custom text goes here')]";
	public static final String  NewPolicies_DepositPolicyAddPolicy="//span[text()='Add Policy']/..";
	public static final String  NewPolicies_DepositPolicyUpdatePolicy="//span[text()='Update']/..";
	public static final String  NewPolicies_DepositPolicyEditHeader="//div[contains(text(),'Edit deposit policy')]";
	public static final String  NewPolicies_DepositPolicyDeleteHeader="//div[contains(text(),'Delete policy')]";
	public static final String  NewPolicies_DepositPolicyDelete="//span[contains(text(),'Delete')]/..";
	
	public static final String CHECK_IN_POLICY_PERCENTAGE = "//div[contains(text(),'Upon check in')]//input";
	
	
	
	public static final String  taxExemptCheckBox = "//input[@id='MainContent_chkLongerthan']";
	public static final String  taxExemptTextField = "//input[@id='MainContent_txttaxexempt']";
	public static final String  taxExemptAllNights = "//input[@id='MainContent_rdAllNight']";
	public static final String  taxExemptAfterLimitIsMet = "//input[@id='MainContent_rdAfterLimit']";
	
	public static final String seasonDateErrorMsg = "//span[text()='Start Date cannot be later than End Date']";
	public static final String selectTimeZone = "MainContent_drpTimeZone";
	
	public static final String requireCreditCardForGuarantee = "//label[text()='Require credit card for guarantee']/preceding-sibling::input";
	public static final String forGuaranteedReservation = "//label[text()='For Guaranteed Reservations']/preceding-sibling::input";
	public static final String always = "//label[text()='Always']/preceding-sibling::input";
	public static final String depositIsRecordedAutomatically = "//label[text()='If deposit is recorded automatically set reservation to Guaranteed']/preceding-sibling::input";
	public static final String requireDepositForGuarantee = "//label[text()='Deposit is required to save Guaranteed reservation']/preceding-sibling::input";

}
