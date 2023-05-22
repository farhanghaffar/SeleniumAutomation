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

	// New Room Class Room Number
	public static final String New_RoomClass_RoomNumbers = "//table[contains(@class,'flip-content')]//tbody[@data-bind='foreach: Rooms']/tr/td[2]//input";


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
	public static final String  NewPolicies_DepositPolicyHeader="//div[contains(text(),'New deposit policy')]";
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
	
	public static final String downarrow2 = "(//i[@aria-label='icon: down'])[2]";
	public static final String select100ItemsPerPage = "//li[text()='100']";
	public static final String newRoomClass = "//button/span[text()='New Room Class']";
	public static final String newRoomClassName = "//label[text()='ROOM CLASS NAME']/../following-sibling::div/div/span/input";
	public static final String newRoomClassNameAbb = "//label[text()='ROOM CLASS ABBREVIATION']/../following-sibling::div/div/span/input";
	public static final String maxAdults = "//label[text()='MAX ADULTS']/../following-sibling::div//input";
	public static final String maxPersons = "//label[text()='MAX PERSONS']/../following-sibling::div//input";
	public static final String rooms = "//div[text()='Rooms']";
	public static final String roomQuantity = "//label[text()='ROOM QUANTITY']/../following-sibling::div//input";
	public static final String rightIcon = "//button/i[@class='anticon anticon-caret-right']";
	public static final String firstRoom = "//input[@id='rooms[0].roomName']";	
	public static final String assignNumbers = "//button/span[text()='Assign Numbers']";
	public static final String Done = "//span[text()='Done']";
	public static final String Publish = "//span[text()='PUBLISH']";
	
}
