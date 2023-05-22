package com.innroad.inncenter.properties;

public class OR_Admin {

	public static final String UserIcon = "//div[@class='user_des']";
	// public static final String LogoutBtn = "//a[@id='logoutLinkMobile']";
	public static final String LogoutBtn = "//a[@id='logoutlink']|//a[@id='logoutLinkMobile']";
	public static final String PropertyName = "//span[@class='select2-chosen']";

	public static final String rateV2UserIcon = "//span[@class='ant-avatar-string']";
	public static final String rateV2LogOut = "//a[contains(text(),'Logout')]";
	public static final String ClientOptions = "MainContent_btnClientOption";
	public static final String SelectCurrency = "MainContent_drpDefaultCurrency";

	public static final String CLICK_CLIENT = "//div[@id='MainContent_Clients_DIV']//following-sibling::tr//a";
	public static final String CLICK_CLIENT_OPTION = "//input[@id='MainContent_btnClientOption']";

	public static final String GET_DEFAUL_CURRENCY = "//select[@id='MainContent_drpDefaultCurrency']//following-sibling::option[@selected='selected']";
	public static final String GET_DATE_FORMAT = "//select[@id='MainContent_drpDateFormat']//following-sibling::option[@selected='selected']";
	public static final String SELECT_DATE_FORMAT = "//select[@id='MainContent_drpDateFormat']";
	public static final String ADMIN_SAVE_BUTTON = "//input[@id='MainContent_btnSave']";
	public static final String ADMIN_DONE_BUTTON = "//input[@id='MainContent_btnDone']";

	public static final String rateV2PropertyName = "//p[@class='propertySelectName']";

	public static final String AdminIcon = "//img[@class='nav-admin nav_icon']";
	public static final String linkRoles = "//a[@id='MainContent_rptrMenu_lbtnMenuItem_2']";
	public static final String buttonRoles = "//span[@class='sn_span3' and text()='Roles']";
	public static final String linkAllRoles = "//a[@data-bind='text: alphabet, click: $parent.AssignAlphabet' and contains(text(),'All')]";
	public static final String txtRoleList = "//div[@class='caption' and text()='Role List']";
	public static final String linkAdministrator = "//a[contains(text(),'Administrator')]";

	// Standard Functions
	public static final String checkboxReservationsView = "//span[contains(@data-bind,'EntityName')][text()='Reservations']/../../td[2]/input";
	public static final String checkboxReservationsAdd = "//span[contains(@data-bind,'EntityName')][text()='Reservations']/../../td[3]/input";
	public static final String checkboxAccountView = "//span[contains(@data-bind,'EntityName')][text()='Account']/../../td[2]/input";
	public static final String checkboxFolioView = "//span[contains(@data-bind,'EntityName')][text()='Folio']/../../td[2]/input";

	// Reports Access
	public static final String tableReportAcess = "//div[text()='Reports Access']//following::table[1]";
	public static final String checkboxLedgerBalancesReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Ledger Balances Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxAdvanceDepositReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Advance Deposit Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxTransactionsReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Transactions Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxFolioBalancesReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Folio Balances Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxDailyFlashReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Daily Flash Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxRoomForecastReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Room Forecast Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";
	public static final String checkboxNetSalesReport = "//div[text()='Reports Access']//following::table//tr//td//span[text()='Net Sales Report']//ancestor::tr//td[@class='text-center']//input[@type='checkbox']";

	// Special Functions
	public static final String checkboxRunReportsAsOtherUsers = "//div[text()='Special Functions']//following::table//tr//td//span[text()='Run reports as other users']//ancestor::tr//td[@class='text-center']//input";
	
	public static final String buttonConfirmPopup = "//button[@type='button' and text()='Confirm']";
	public static final String buttonCancelPopup = "//button[@type='button' and text()='Confirm']//preceding-sibling::button";

	// Roles
	public static final String enterRoleName = "//input[@type= 'text' and @placeholder = 'Role Name']";
	public static final String enterRoleDescription = "//input[@class = 'form-control' and @placeholder = 'Description']";
	public static final String buttonSaveRole = "//button[text()='Save']";
	public static final String buttonResetRole = "//button[text()='Reset']";
	public static final String checkboxSystem = "//span[text()='System']//preceding-sibling::input";
	public static final String checkboxClients = "//span[text()='Clients']//preceding-sibling::input";
	public static final String checkboxProperties = "//span[text()='Properties']//preceding-sibling::input";
	public static final String checkboxRoomClasses = "//span[text()='Room Classes']//preceding-sibling::input";
	public static final String checkboxUsers = "//span[text()='Users']//preceding-sibling::input";
	public static final String checkboxReservations = "//span[text()='Reservations']//preceding-sibling::input";

    //User
    public static final String buttonDiscardAll = "//button[@data-bind='click: DiscardAll']";
    public static final String buttonAssignAll = "//button[@data-bind='click: AssignAll']";
    public static final String linkAllUsers = "//a[@data-bind='text: alphabet, click: $parent.AssignAlphabet' and text()='All']";
    public static final String txtUsersList = "//div[@class='caption' and text()='Users List']";

	    	 
}
