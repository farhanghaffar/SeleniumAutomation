package com.innroad.inncenter.properties;

public class OR_Reports {
	public static final String ReportFrame = "//iframe[@id='MainContent_ucRptViewer_ifrmAccountStatement']";
	public static final String ReportType = "//select[@id='drpReportType']";
	public static final String ReportDisplay = "tdViewPDF";
	public static final String ViewPDF = "//*[@id='viewPDF']";
	public static final String NetSalesFromCalenderHeading = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
	public static final String NetSalesFromCalenderNext = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/i";
	public static final String NetSalesStayonFromDate = "//input[@id='MainContent_txtDateStart']";
	public static final String NetSalesStayonToDate = "//input[@id='MainContent_txtDateEnd']";
	
	
	// Reports-V2
	
		//Reports Main Page
		public static final String PerformanceFilter = "//span[text()='Performance']";
		public static final String AccountingFilter = "//span[text()='Accounting']";
		public static final String PerformanceReportsHeading = "//span[text()='Performance Reports']";
		public static final String AccountingReportsHeading = "//span[text()='Accounting Reports']";
		public static final String DailyFlashReport = "//a[text()='Daily Flash'][contains(@class,'ReportListItem')]";
		public static final String NetSalesReport = "//a[text()='Net Sales'][contains(@class,'ReportListItem')]";
		public static final String RoomForecastReport = "//div[contains(@class,'Reports_mainContainer')]//a[text()='Room Forecast']";
		public static final String AdvanceDepositReport = "//a[text()='Advance Deposit']";
		public static final String AccountBalancesReport = "//a[text()='Account Balances']";
		public static final String ReservationHistoryReport = "//a[text()='Reservation History'][contains(@class,'ReportListItem')]";

		public static final String LedgerBalancesReport = "//a[text()='Ledger Balances'][contains(@class,'ReportListItem')]";
		public static final String FolioBalancesReport = "//a[text()='Folio Balances']";

		public static final String Incidentals = "//a[text()='Incidentals']";
		public static final String PaymentMethod = "//a[text()='Payment Method']";
		public static final String Taxes = "//a[text()='Taxes']";
		public static final String TransactionsReport = "//div[contains(@class,'Reports_mainContainer')]//a[text()='Transactions']";
		public static final String Help = "//span[text()='Help']";
		public static final String DailyFlashHelp = "//a[text()='Daily Flash']/following-sibling::a[text()='Help']";
		public static final String NetSalesHelp = "//a[text()='Net Sales']/following-sibling::a[text()='Help']";
		public static final String RoomForecastHelp = "//a[text()='Room Forecast']/following-sibling::a[text()='Help']";
		public static final String AdvanceDepositHelp = "//a[text()='Advance Deposit']/following-sibling::a[text()='Help']";
		public static final String AccountBalancesHelp = "//a[text()='Account Balances']/following-sibling::a[text()='Help']";
		public static final String LedgerBalancesHelp = "//a[text()='Ledger Balances']/following-sibling::a[text()='Help']";
		public static final String TransactionsHelp = "//a[text()='Transactions']/following-sibling::a[text()='Help']";
		public static final String ReportsHelpPageHeader = "//h1[text()='Reports']";
		
		//Ledger Reports Page
		public static final String Reports = "//*[text()='Reports']";
		public static final String ReportsIcon = "//img[contains(@class,'nav-reports nav_icon')]";
		public static final String BrowseAllReports = "//*[text()='Browse All Reports']";
		public static final String LedgerBalancesReportHeader = "//h2[text()='Ledger Balances Report']";
		public static final String InnRoadImage = "//img[@alt='innRoad']";
		public static final String LedgerBalancesReportMainHeader = "//div[text()='Ledger Balances Report']";
		public static final String ExcelExport = "//span[text()='Excel']";
		public static final String PDFExport = "//span[text()='PDF']";
		public static final String Print = "//span[text()='Print']";
		public static final String RunReport = "//span[text()='Run Report']";
		public static final String Collapse = "//span[text()='Collapse']";
		public static final String Edit = "//span[text()='Edit ']";
		public static final String ChoseDateRange = "//*[text()='Choose Date Range']";
		public static final String SelectInputs = "//*[text()='Select Inputs']";
		public static final String CustomizeDetailedView = "//*[text()='Customize Detailed View']";
		public static final String ExcelExportToolTipBeforeRunReport = "//div[contains(@class,'ant-popover ReportHeader_toolTip_3J9AC')]//div[text()='Please click \"Run Report\" before exporting.']";
		public static final String PDFExportToolTipBeforeRunReport = "//div[contains(@class,'ant-popover ReportHeader_toolTip_3J9AC')]//div[text()='Please click \"Run Report\" before exporting.']";
		public static final String PrintToolTipBeforeRunReport = "//div[contains(@class,'ant-popover ReportHeader_toolTip_3J9AC')]//div[text()='Please click \"Run Report\" before exporting.']";
		public static final String ExcelExportToolTipAfterRunReport = "//span[text()='Excel']/..";
		public static final String PDFExportToolTipAfterRunReport = "//span[text()='PDF']/..";
		public static final String PrintToolTipAfterRunReport = "//span[text()='Print']/..";
		public static final String SortReportBy = "//*[text()='Sort Report By']";
		public static final String GroupRowsByToolTip = "(//div[@role='tooltip'])[2]";
		public static final String SortReportByOptionsExpand = "//input[@id='sortBy']";
		public static final String GroupRowsByOptionsExpand = "//input[@id='groupBy']";
		

		public static final String AdvancedInputs = "//*[text()='Advanced Inputs']";

		//public static final String AdvancedOptions = "//*[text()='Advanced Options']";
		public static final String ExpandAll = "//*[text()='Expand All']";
		public static final String CollapseAll = "//*[text()='Collapse All']";
		public static final String AccountType = "//*[text()='Account Type']";
		public static final String ItemStatus = "//*[text()='Item Status']";
		public static final String IncludeDataFrom = "(//*[text()='Include Data From'])[1]";
		public static final String TaxExemptLedgerItems = "//*[text()='Tax Exempt Ledger Items']";
		public static final String MarketSegment = "//*[text()='Market Segment']";
		public static final String ReservationStatus = "//*[text()='Reservation Status']";
		public static final String Referrals = "//*[text()='Referrals']";
		
		
		public static final String AdvancedOptionsToolTipIcon = "//*[text()='Advanced Options']/../following-sibling::span";
		public static final String AccountTypeToolTipIcon = "//*[text()='Account Type']/..//span[@role='img']";
		public static final String ItemStatusToolTipIcon = "//*[text()='Item Status']/..//span[@role='img']";
		public static final String IncludeDataFromToolTipIcon = "//*[text()='Include Data From']/..//span[@role='img']";
		public static final String TaxExemptLedgerItemsToolTipIcon = "//*[text()='Tax Exempt Ledger Items']/..//span[@role='img']";
		public static final String MarketSegmentToolTipIcon = "//*[text()='Market Segment']/..//span[@role='img']";
		public static final String ReservationStatusToolTipIcon = "//*[text()='Reservation Status']/..//span[@role='img']";
		public static final String ReferralsToolTipIcon = "//*[text()='Referrals']/..//span[@role='img']";
		
		public static final String AccountTypeClearAll = "//*[text()='Account Type']/..//span[text()='Clear all' or text()='Clear All']";	
		public static final String ItemStatusClearAll = "//*[text()='Item Status']/..//span[text()='Clear all' or text()='Clear All']";	
		public static final String ReservationStatusClearAll = "//*[text()='Reservation Status']/..//span[text()='Clear all' or text()='Clear All']";
		public static final String AccountTypeSelectAll = "//*[text()='Account Type']/..//span[text()='Select all' or text()='Select All']";	
		public static final String ItemStatusSelectAll = "//*[text()='Item Status']/..//span[text()='Select all' or text()='Select All']";	
		public static final String ReservationStatusSelectAll = "//*[text()='Reservation Status']/..//span[text()='Select all' or text()='Select All']";
		
		public static final String AccountTypesOptions = "//span[text()='Account Type']/../../../following-sibling::div//div//div//div/div/div/div/div//span[@class='ant-tree-title']"; //"//div[@id='accountTypes']/label";
		public static final String ItemStatusOptions = "//span[text()='Item Status']/../../../following-sibling::div//div//div//div/div/div/div/div//span[@class='ant-tree-title']"; //"//div[@id='itemStatus']/label";		
		public static final String ReservationStatusOptions = "//span[text()='Reservation Status']/../../../following-sibling::div//div//div//div/div/div/div/div//span[@class='ant-tree-title']"; //"//div[@id='reservationStatus']/label";
		public static final String AdvancedDeposits="//span[text()='Advanced Deposits']";
		
		
		
		
		public static final String Pending="//*[text()='Pending']";
		public static final String Users="//*[text()='Users']";
		public static final String Properties="//*[text()='Properties']";
		public static final String ShiftTime="//*[text()='Shift Time']";
		public static final String TaxExempt="//input[@id='taxExempt']";
		public static final String MarktSegmnt="//input[@id='marketSegment']";
		public static final String OnHold="//*[text()='On Hold']";
		public static final String referrals="//input[@id='referrals']";
		public static final String SummaryViewHeader = "//*[contains(text(),'Room Forecast Report - ')]";
		
		//pages not yet developed need to be verified after pages developed
		public static final String DailyFlashReportHeader = "//h2[text()='Daily Flash Report']";
		public static final String NetSalesReportHeader = "//h2[text()='Net Sales Report']";
		public static final String RoomForecastReportHeader = "//h2[text()='Room Forecast Report']";
		public static final String AdvanceDepositReportHeader = "//h2[text()='Advance Deposit Report']";
		public static final String AccountBalancesReportHeader = "//h2[text()='Account Balances Report']";
		public static final String TransactionsReportHeader = "//h2[text()='Transactions Report']";
		public static final String FolioBalanceReportHeader = "//h2[text()='Folio Balances Report - Guest Ledger']";
		public static final String ReservationHistoryReportHeader = "//h2[text()='Reservation History Report']";
		
		public static final String LedgerBalancesSubTitle = "//p[text()='Detailed transactions for all ledger accounts selected']";
		public static final String RunReportAtBottom = "(//span[text()='Run Report'])[2]";
		public static final String TaxExemptListExpand = "//input[@id='taxExempt']";
		public static final String MarketSegmentListExpand = "//input[@id='marketSegment']";
		public static final String ReferralstListExpand = "//input[@id='referrals']";
		
	
	//Reports V2
	public static final String linkLedgerBalances = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Ledger Balances']";
	public static final String linkDailyFlash = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Daily Flash']";
	public static final String linkNetSales = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Net Sales']";
	public static final String linkRoomForecast = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Room Forecast']";
	public static final String linkAdvanceDeposit = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Advance Deposit']";
	public static final String linkAccountBalances = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Account Balances']";
	public static final String linkTransactions = "//a[@class='ReportListItem_reportLink_1xqwF' and text()='Transactions']";
	
	//Reports V2 - Ledger Balances Report - Select Inputs
	public static final String txtSelectInputs = "//*[contains(text(),'Select Inputs')]";
	public static final String txtIncludeLedgerAccount = "//span[@class='LabelWithToolTip_title_2AT-P']/b[text()='Included Ledger Accounts']";
	public static final String tooltip = "//div[@class='ant-tooltip-inner' and @role='tooltip']";
	public static final String infoIncludeLedgerAccount = "//span[text()='Include Ledger Account']//following-sibling::span[@class='anticon anticon-info-circle LabelWithToolTip_infoIcon_39Pyt']";
	public static final String buttonClearAll = "//span[text()='Clear All' or text()='Clear all']//parent::button"; ////span[text()='Clear All']
	public static final String buttonSeeAll = "//span[text()='See All']";
	public static final String buttonSelectAll = "//span[text()='Select All' or text()='Select all']//parent::button";
	public static final String selectIncidentals = "//span[contains(text(),'Incidental')]//ancestor::button";
	public static final String selectCountIncidentals = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Incidentals']//following-sibling::span";
	public static final String checkboxIncidentals = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Incidental')]//preceding-sibling::label//input";
	
	public static final String selectRoomCharges = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Room Charge')]//parent::button";
	public static final String selectCountRoomCharges = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Room Charges']//following-sibling::span";
	public static final String checkboxRoomCharges = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Room Charges']//preceding-sibling::label//input";
	
	public static final String selectTaxes = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Tax')]//parent::button";
	public static final String selectCountTaxes = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Taxes']//following-sibling::span";
	public static final String checkboxTaxes = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Tax')]//preceding-sibling::label//input";
	
	public static final String selectTransfers = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Transfer')]//parent::button";
	public static final String selectCountTransfers = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Transfers']//following-sibling::span";
	public static final String checkboxTransfers = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Transfers']//preceding-sibling::label//input";
	
	public static final String selectPaymentMethod = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Payment Method')]//parent::button";
	public static final String selectCountPaymentMethod = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Payment Method']//following-sibling::span";
	public static final String checkboxPaymentMethod = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Payment Method']//preceding-sibling::label//input";
	
	public static final String selectFees = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Fee')]//parent::button";
	public static final String selectCountFees = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Fees']//following-sibling::span";
	public static final String checkboxFees = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Fees']//preceding-sibling::label//input";
	
	public static final String selectOther = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Other']";
	public static final String selectCountOther = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Other']//following-sibling::span";
	public static final String checkboxOther = "//span[@class='MultiSelectControls_underLine_38yLw' and text()='Other']//preceding-sibling::label//input";
	public static final String checkboxSelectInputs = "//button[contains(@class,'MultiSelectControls')]//input[@type='checkbox']";
	
	public static final String popupMessage = "//div[contains(@id,'rcDialogTitle') and @class='ant-modal-title']";	
	public static final String buttonClosePopup = "//button[@aria-label='Close']";
	//public static final String buttonSavePopup = "//button[@class='ant-btn ant-btn-success']";
	//public static final String buttonSavePopup = "(//button[contains(@class,'ant-btn-success')])[3]";
	public static final String buttonSavePopup = "//button[contains(@class,'ant-btn') or contains(@class,'saveButton')]/span[contains(text(),'Save')]"; //"//button[contains(@class,'ant-btn-success')]/span[contains(text(),'Save')]";
	public static final String buttonCancelPopup = "//button[@class='ant-btn ant-btn-cancel']";
	
	public static final String inputAllCategories = "//li[text()='All Categories']";
	public static final String inputIncidentals = "//li[text()='Incidentals']";
	public static final String inputPaymentMethod = "//li[text()='Payment Method']";
	public static final String inputTaxes = "//li[text()='Taxes']";
	public static final String inputRoomCharges = "//li[text()='Room Charges']";
	public static final String inputFees = "//li[text()='Fees']";
	public static final String inputTransfers = "//li[text()='Transfers']";
	public static final String inputOther = "//li[text()='Other']";
	
	public static final String clickSelectAll = "//span[@type='double-right']";
	public static final String clickRemoveAll = "//span[@type='double-left']";
	
	public static final String txtExcludeZeroBalanceLedgerAccounts = "//*[text()='Exclude Zero Balance Ledger Accounts']";
	public static final String tooltipExcludeZeroBalanceLedgerAccounts = "//span/b[text()='Exclude Zero Balance Ledger Accounts']//parent::span//following-sibling::span";
	public static final String rdoYesDisplayCustomGeneral = "//div[@id='displayLedgerAccountNumber']/label/span/input[@value='1']";
	public static final String rdoNoDisplayCustomGeneral = "//div[@id='displayLedgerAccountNumber']/label/span/input[@value='0']";

	
	public static final String txtDisplayCustomGeneralLedgerAccount= "//*[text()='Display Custom General Ledger Account #']";
	public static final String tooltipDisplayCustomGeneralLedgerAccount= "//span/b[text()='Display Custom General Ledger Account #']//parent::span//following-sibling::span";
	public static final String rdoYesExcludeZero= "//div[contains(@id,'excludeZero')]/label/span/input[@value='1']";
	public static final String rdoNoExcludeZero= "//div[contains(@id,'excludeZero')]/label/span/input[@value='0']";
	
	public static final String searchAvailable = "//h3[text()='Available']//following-sibling::span/input[@name='searchKey']";
	public static final String searchSelected = "//h3[text()='Selected']//following-sibling::span/input[@name='searchKey']";
	
	
	public static final String IncludedLedgerAccounts = "//*[text()='Included Ledger Accounts']";
    public static final String ExcludeZeroBalanceLedgerAccounts = "//*[text()='Exclude Zero Balance Ledger Accounts']";
    public static final String DisplayCustomGeneralLedgerAccount = "//*[text()='Display Custom General Ledger Account #']";
   
    public static final String IncludedLedgerAccountsToolTipIcon = "//*[text()='Included Ledger Accounts']/../..//span[@aria-label='info-circle']";
    public static final String ExcludeZeroBalanceLedgerAccountsToolTipIcon = "//*[text()='Exclude Zero Balance Ledger Accounts']/../..//span[@aria-label='info-circle']";
    public static final String DisplayCustomGeneralLedgerAccountToolTipIcon = "//*[text()='Display Custom General Ledger Account #']/../..//span[@aria-label='info-circle']";
	

	//Reports V2
	public static final String linkBrowseAllReports = "//a[text()='Browse All Reports']";
	public static final String Reports2 = "//*[text()='Reports']";
	public static final String Report2 = "(//a[text()='Reports'])[2]";
	public static final String ReportNew = "(//span[text()='Reports'])[2]";
	
	public static final String SearchReports = "//div[contains(@class,'ReportsDropDown_searchInput')]//input"; //"//input[@class='ant-select-selection-search-input' and @role='combobox']";
	public static final String SearchElements = "//div[@class='ant-select-item-option-content']";
	
	//Navigate to other modules
	public static final String linkReservations = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='reservation']";
	public static final String linkAccounts = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='accounts']";
	public static final String linkGuestServices = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='gustServices']";
	public static final String linkInventory = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='inventory']";
	public static final String linkSetup = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='setup']";
	public static final String linkAdmin = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='admin']";
	public static final String linkNightAudit = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='nightAudit']";
	
	//Advanced Options - Include Data From
	public static final String usersExpand = "//input[@id='users']";
	public static final String buttonShiftTime = "//button[@role='switch']";
	public static final String inputStartShiftTime = "(//input[@placeholder='Select time'])[1]";
	public static final String inputEndShiftTime = "(//input[@placeholder='Select time'])[2]";
	
	public static final String buttonNowShiftTimeStart = "(//a[@class='ant-picker-now-btn'])[1]";
	public static final String buttonNowShiftTimeEnd = "(//a[@class='ant-picker-now-btn'])[2]";
	public static final String buttonOkShiftTimeStart = "(//button[@class='ant-btn ant-btn-primary ant-btn-sm'])[1]";
	public static final String buttonOkShiftTimeEnd = "(//button[@class='ant-btn ant-btn-primary ant-btn-sm'])[2]";
	
	
	//Ledger Balances - Choose Date Range
	public static final String date = "//div[@class='ant-picker ant-picker-range DateRange_dateInput__3FRhb']";
	public static final String dateDropDown = "//div[contains(@class,'DateRange_select') or contains(@class,'DatePicker_select')]//span[1]/input"; //"//input[contains(@id,'rc_select')]";
	public static final String dateDropDown2 = "(//input[contains(@id,'rc_select')])[2]";
	public static final String dateStart = "//input[@placeholder='Start date']";
	public static final String dateEnd = "//input[@placeholder='End date']";
	public static final String dateOptions = "//div[@class='ant-select-item-option-content']";
	//public static final String dateOptions = "//span[@class='ant-tag ant-tag-blue']";
	public static final String dateSeparator = "//span[@class='ant-picker-separator']";
	public static final String dateCustomRange = "//div[text()='Custom Range']";
	
	public static final String dateTab = "//div[@class='ant-picker-panels']";
	public static final String daySelected = "//td[contains(@class,'ant-picker-cell-selected')]/div"; //"//td[@class='ant-picker-cell ant-picker-cell-in-view ant-picker-cell-range-start ant-picker-cell-range-end ant-picker-cell-today ant-picker-cell-selected']/div";

	public static final String buttonYear = "(//button[@class='ant-picker-year-btn'])[1]";
	public static final String buttonMonth = "(//button[@class='ant-picker-month-btn'])[1]";
	
	public static final String buttonReturnToDefault = "//span[contains(text(),'Return to Default')]";
	public static final String symbolReturnToDefault = "//span[@aria-label='rollback']";

	public static final String ReturnToDefaultSeccess = "//div[contains(text(),'Success')]";
	public static final String ReturnToDefaultFailure = "//div[contains(text(),'No Report Data Available')]";
	public static final String ReturnToDefaultDescription = "//div[contains(text(),'Report input')]";

	//Ledger Run Report
	
	public static final String GRHeaderTitle = "//h1[contains(text(),'Ledger Balances Report -')]";
	
	public static final String GRReportTypeCaption = "//span[contains(text(),'Report type')]";
	public static final String GRReportTypeValue = "//span[contains(text(),'Report type')]/following-sibling::span";
	
	public static final String GRDateRangeCaption = "//span[contains(text(),'Date Range')]"; //"//span[text()='Date Range: ']";
	public static final String GRDateRangeValue = "//span[contains(text(),'Date Range')]/following-sibling::span";
	
	public static final String GRIncludedLedgerAccountsCaption = "//span[text()='Included Ledger Accounts: ' or contains(text(),'Include Ledger Accounts')]";
	public static final String GRIncidentalsCaption = "//span[text()=' Incidentals']";
	public static final String GRIncidentalsValue = "//span[text()=' Incidentals']/following::text()[2]";
	public static final String GRRoomChargesCaption = "//span[text()=' Room  Charges']";
	public static final String GRRoomChargesValue = "//span[text()=' Room  Charges']/following::text()[2]";
	public static final String GRTaxesCaption = "//span[text()=' Taxes']";
	public static final String GRTaxesValue = "//span[text()=' Taxes']/following::text()[2]";
	public static final String GRTransfersCaption = "//span[text()=' Transfers']";
	public static final String GRTransfersValue = "//span[text()=' Transfers']/following::text()[2]";
	public static final String GRPaymentMethodCaption = "//span[text()=' Payment  Method']";
	public static final String GRPaymentMethodValue = "//span[text()=' Payment  Method']/following::text()[2]";
	public static final String GRTravelAgentCommissionCaption = "//span[text()=' Travel  Agent  Commission']";
	public static final String GRAgentCommissionValue = "//span[text()=' Travel  Agent  Commission']/following::text()[2]";
	public static final String GRDistributionMethodCaption = "//span[text()=' Distribution  Method']";
	public static final String GRDistributionMethodValue = "//span[text()=' Distribution  Method']/following::text()[2]";
	public static final String GRGiftCertificateCaption = "//span[text()=' Gift  Certificate']";
	public static final String GRGiftCertificateValue = "//span[text()=' Gift  Certificate']/following::text()[2]";
	public static final String GRGiftCertificateRedeemedCaption = "//span[text()=' Gift  Certificate  Redeemed']";
	public static final String GRGiftCertificateRedeemedValue = "///span[text()=' Gift  Certificate  Redeemed']/following::text()[2]";
	
	public static final String GRReservationStatusCaption = "//span[text()='Reservation Status']";
	public static final String GRReservationStatusValue = "//span[text()='Reservation Status']/following-sibling::span";
	
	public static final String GRAccountTypeCaption = "//span[text()='Account Type']";
	public static final String GRAccountTypeValue = "//span[text()='Account Type']/following-sibling::span";
	
	public static final String GRIncludeDataFromCaption = "//span[text()='Include Data From']";
	public static final String GRIncludeDataFromValue = "//span[text()='Include Data From']/following-sibling::span";
			
	public static final String GRGeneratedOnCaption = "//span[text()='Generated On']";
	public static final String GRGeneratedOnValue= "//span[text()='Generated On']/following-sibling::span";
	
	public static final String GRSortReportByCaption = "//span[text()='Sort Report by']";
	public static final String GRSortReportByValue = "//span[text()='Sort Report by']/following-sibling::span";
	
	public static final String GRGroupRowsByCaption = "//span[text()='Group Rows by']";
	public static final String GRGroupRowsByValue = "//span[text()='Group Rows by']/following-sibling::span";
	
	public static final String GRItemStatusCaption = "//span[text()='Item Status']";
	public static final String GRItemStatusValue = "//span[text()='Item Status']/following-sibling::span";
	
	public static final String GRTaxExemptCaption = "//span[text()='Tax Exempt']";
	public static final String GRTaxExemptValue = "//span[text()='Tax Exempt']/following-sibling::span";
	
	public static final String GRMarketSegmentCaption = "//span[text()='Market Segment']";
	public static final String GRMarketSegmentValue = "//span[text()='Market Segment']/following-sibling::span";
	
	public static final String GRReferralsCaption = "//span[text()='Referrals']";
	public static final String GRReferralsValue = "//span[text()='Referrals']/following-sibling::span";
	
	public static final String GRExcludeZeroBalanceLedgerAccountsCaption = "//span[text()='Exclude Zero Balance Ledger Accounts']";
	public static final String GRExcludeZeroBalanceLedgerAccountsValue = "//span[text()='Exclude Zero Balance Ledger Accounts']/following-sibling::span";
	
	
	public static final String GRSummaryViewHeader = "//h2[text()='Summary View | Ledger Balances Report']";
	public static final String GRLedgerAccountCategory = "//div[text()='Ledger Account Category']";
	public static final String GRLedgerAccountCategoryBalance = "//div[text()='Ledger Account Category']/../..//th[text()='Balance']";
	public static final String GRLedgerAccountCategoryToolTipIcon = "//div[text()='Ledger Account Category']/span/span";
	public static final String GRLedgerAccountCategoryToolTipHeader = "//div[@class='ant-popover-title']/b[text()='Ledger Account Category']";
	public static final String GRLedgerAccountCategoryToolTipContent = "//div[@class='ant-popover-inner-content']/p[contains(text(),'Each ledger account is divided into different categories')]";
	public static final String GRLedgerAccountCategoryTotalCaption = "//td[text()='Ledger Account Category Total']";
	public static final String ScrollToTop = "//span[text()='Top']";
	
	public static final String FilterHeaderDateRangeCaption = "//div[text()='Date Range']"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Date Range']";
	public static final String FilterHeaderIncludedLedgerAccountsCaption = "//div[text()='Included Ledger Accounts' or text()='Include Ledger Accounts']"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Included Ledger Accounts' or text()='Include Ledger Accounts']";
	public static final String FilterHeaderTaxExemptCaption = "//div[text()='Tax Exempt']"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Tax Exempt']";
	public static final String FilterHeaderSortReportByCaption = "//div[text()='Sort Report by']"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Sort Report by']";
	public static final String FilterHeaderGroupByCaption = "//div[text()='Group by']"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Group by']";

	public static final String FilterHeaderDateRangeValue = "//div[text()='Date Range']/p"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Date Range']/following-sibling::p";
	public static final String FilterHeaderIncludedLedgerAccountsValue = "//div[text()='Included Ledger Accounts' or text()='Include Ledger Accounts']/p"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Included Ledger Accounts' or text()='Include Ledger Accounts']/following-sibling::p";
	public static final String FilterHeaderTaxExemptValue = "//div[text()='Tax Exempt']/p"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Tax Exempt']/following-sibling::p";
	public static final String FilterHeaderSortReportByValue = "//div[text()='Sort Report by']/p"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Sort Report by']/following-sibling::p";
	public static final String FilterHeaderGroupByValue = "//div[text()='Group by']/p"; //"//div[contains(@class,'filterBoxHeader')]//p[text()='Group by']/following-sibling::p";
	public static final String NoReportDataAvailableMessageHeading = "//div[text()='No Report Data Available']";
	public static final String NoReportDataAvailableMessageContent = "//div[text()='Please update report inputs and try running the report again.']";

	public static final String LoadingReportMessage = "//*[text()='Loading Report']";
	public static final String LoadingReportCancelButton = "//*[text()='Cancel']";
	
	//Daily Flash Report
	public static final String dayCurrentSelected = "//div[contains(@class,'DateRange_select') or contains(@class,'DatePicker_select')]//span[2]"; 
	//"(//span[contains(@class,'ant-select-selection-item')])[2]";
	public static final String dateDailyFlash = "//input[contains(@placeholder,'Select date')]";
	public static final String dateSelectDailyFlash = "//input[@placeholder='Select date']";
	public static final String txtDailyFlashDate = "//*[contains(text(),'Effective date')]";
	public static final String txtDailyAdvancedInputs = "//*[contains(text(),'Advanced Inputs')]";
	
	public static final String txtBreakOutTaxExemptRevenue = "//span[contains(text(),'Break Out Tax-Exempt Revenue')]";
	public static final String rdoYesBreakOutTaxExemptRevenue = "//span[contains(text(),'Yes')]//preceding-sibling::span/input";
	public static final String rdoNoBreakOutTaxExemptRevenue = "//span[contains(text(),'No')]//preceding-sibling::span/input";
	
	public static final String textOverflowBreakOutTaxExemptRevenue = "//div[contains(@class,'textOverflow')]";
	
	public static final String textStandardViewHeader = "//h1[contains(@class,'StandardView_title')]";
	public static final String textGuestCountSummary = "////h2[contains(text(),'Guest Count Summary')]";
	
	
	
	
	
	//Room Forecast Report
	public static final String txtChooseDateRange = "//span[contains(text(),'Choose Date Range')]"; //"//strong/b[contains(text(),'Choose Date Range')]";
	public static final String txtStayonDateRange = "//span/b[contains(text(),'Stay on Date Range')]";
	//public static final String txtSelectInputs = "//strong[contains(text(),'Select Inputs')]";
	public static final String txtBreakOutBy = "//span/b[contains(text(),'Break Out by')]";
	
	public static final String txtBreakOutByNone = "//span[contains(text(),'None')]";
	public static final String txtBreakOutByRoomClass = "//span[contains(text(),'Room Class')]";
	public static final String txtBreakOutByMarketSegment = "//span[contains(text(),'Market Segment')]";
	
	public static final String rdoBreakOutByNone = "//span[contains(text(),'None')]//preceding-sibling::span";
	public static final String rdoBreakOutByRoomClass = "//span[contains(text(),'Room Class')]//preceding-sibling::span";
	public static final String rdoBreakOutByMarketSegment = "//span[contains(text(),'Market Segment')]//preceding-sibling::span";
	
	public static final String groupRowsBySelected = "//*[contains(text(),'Group Rows By')]/../../..//following::span[contains(@class,'ant-select-selection-item')]";
//Room Folio
	public static final String summaryViewReceivedSubTotal = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[2])[1]";
	public static final String summaryViewPayableSubTotal = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[2])[2]";
	
//	Room Forecast report
	
	public static final String summaryViewRoomsTotal = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[2]";
	public static final String summaryViewRoomsOutOfOrder = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[3]";
	public static final String summaryViewRoomsBookable = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[4]";
	public static final String summaryViewRoomsRoomsSold = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[5]";
	public static final String summaryViewRoomsOccupancy = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[6]";
	public static final String summaryViewRoomsAvailability = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[7]";
	
	public static final String summaryViewGuestsGuestsCount = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[8]";
	public static final String summaryViewGuestsArrival = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[9]";
	public static final String summaryViewGuestsStayOver = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[10]";
	public static final String summaryViewGuestsDepature = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[11]";
	
	public static final String summaryViewGroupBlocks = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[12]";
	public static final String summaryViewGroupPickUp = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[13]";
	public static final String summaryViewGroupPickUpPercentage = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[14]";
	public static final String summaryViewGroupGroupRevenue = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[15]";

	public static final String summaryViewPerformanceRevenue = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[16]";
	public static final String summaryViewPerformanceADR = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[17]";
	public static final String summaryViewPerformanceRevPAR = "(//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[18]";
	
	public static final String detailedViewFirstDate = "//h2[contains(text(),'Detailed View')]/../following-sibling::div//tr[1]/td[1]";
	public static final String includeGroupSelectedOption = "//div[text()='Include Group Reservations']/p"; //"//p[text()='Include Group Reservations']/following-sibling::p";
	public static final String includePerformanceSelectedOption = "//div[text()='Include Performance Metrics']/p"; //"//p[text()='Include Performance Metrics']/following-sibling::p";
	
	
	
	public static final String rdoYesIncludPerformanceMetrics = "//span[text()='Include Performance Metrics (Revenue, ADR, RevPAR)']/../../../following-sibling::div//span[text()='Yes']//preceding-sibling::span/input";
	public static final String rdoNoIncludPerformanceMetrics = "//span[text()='Include Performance Metrics (Revenue, ADR, RevPAR)']/../../../following-sibling::div//span[text()='No']//preceding-sibling::span/input";
	
	public static final String rdoYesIncludeGroupReservations = "//span[text()='Include Group Reservations']/../../../following-sibling::div//span[text()='Yes']//preceding-sibling::span/input";
	public static final String rdoNoIncludeGroupReservations = "//span[text()='Include Group Reservations']/../../../following-sibling::div//span[text()='No']//preceding-sibling::span/input";
	public static final String loadingReportIcon = "//span[text()='Loading Report']";
	
	public static final String navigateFolioBalances="//a[text()='Folio Balances']";
	public static final String folioBalancesReport ="//h2[@class='FilterHeader_title_3za9g']";
	public static final String allreceivablepayable ="//p[@class='FilterHeader_subTitle_1Kl5t']";
	public static final String returnToDefaultButton ="(//button[@class='ant-btn ant-btn-link FilterHeader_link_Qrk4s'])[1]";
	public static final String returnToDefaultText ="//span[text()='Return to Default']";
	public static final String collapseButton ="(//button[@class='ant-btn ant-btn-link FilterHeader_link_Qrk4s'])[2]";
	public static final String collapseText ="//span[text()='Collapse']";
	/************************************Section 1**************************************************/
	public static final String iconOne="(//span[@class='ant-avatar ant-avatar-circle NumericListItem_avatar_1C00B'])[1]";
	public static final String effectiveDate ="(//div[@class='SectionTitle_title_2sEBt NumericListItem_title_3MeuE'])[1]";
	//public static final String effectiveDateDropdown ="(//span[@class='ant-select-selection-item'])[1]";
	public static final String effectiveDateDropdown ="//div[@class='ComponentBuilder_datePicker_Gt0CI']//span[@class='ant-select-selection-item']";
	public static final String  dateFieldButton ="//div[@class='ant-picker DatePicker_dateInput__2OBOO DatePicker_datePicker__12vFR']";
	public static final String dateFieldInput ="//input[@placeholder='Select date']";
	/************************************Section 2**************************************************/
	public static final String iconTwo="(//span[@class='ant-avatar ant-avatar-circle NumericListItem_avatar_1C00B'])[2]";
	public static final String selectInputs ="//span[text()='Select Inputs']";
	public static final String includeReservationTypeText ="//span[text()='Include Reservation Type']";
	public static final String includeReservationTypeIcon ="(//span[@class='anticon anticon-info-circle'])[2]";
	public static final String clearAllButton ="//button[@class='ant-btn ant-btn-link ant-btn-sm CheckBoxMarker_buttonAction_3L6ab'][2]";
	public static final String inHouseCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[1]";
	public static final String inHouseText ="//span[text()='In-House']";
	public static final String departedCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[2]";
	public static final String departedText ="//span[text()='Departed']";
	public static final String cancelledCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[3]";
	public static final String cancelledText ="//span[text()='Cancelled']";
	public static final String reservedCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[4]";
	public static final String reservedText ="//span[text()='Reserved']";
	public static final String noShowCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[5]";
	public static final String noShowText ="//span[text()='No Show']";
	public static final String confirmedCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[6]";
	public static final String confirmedText ="//span[text()='Confirmed']";
	public static final String onHoldCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[7]";
	public static final String onHoldText ="//span[text()='On Hold']";
	public static final String guaranteedCheckBox ="(//span[@class='ant-tree-checkbox ant-tree-checkbox-checked'])[8]";
	public static final String guaranteedText ="//span[text()='Guaranteed']";
	public static final String includedBalancesIcon ="(//span[@class='anticon anticon-info-circle'])[3]";
	public static final String includedBalancesText ="//b[text()='Included Balances']";
	public static final String includedBalancesDropdown ="//b[text()='Included Balances']/../..//following-sibling::div//span[@class='ant-select-selection-item']";
	/************************************Section 3**************************************************/
	public static final String iconThree="(//span[@class='ant-avatar ant-avatar-circle NumericListItem_avatar_1C00B'])[3]";
	public static final String customizeDetailedView="//span[text()='Customize Detailed View']";
	public static final String sortReportByIcon ="(//span[@class='anticon anticon-info-circle'])[4]";
	public static final String sortReportByText ="//b[text()='Sort Report by']";
	public static final String customizeDetailedViewDropdown ="//b[contains(text(), 'Sort Report by')]/../..//following-sibling::div//span[2]";
	/************************************Section Run Report**************************************************/
	public static final String runReportButton ="//button[@class='ant-btn ant-btn-primary ReportDetail_runReportButton_oScAZ']";
	/************************************Section Right**************************************************/
	public static final String iconRight ="//span[@class='ant-avatar ant-avatar-circle NumericListItem_avatar_1C00B NumericListItem_default_2sLF2']";
	public static final String AdvancedInputsIcon ="(//span[@class='anticon anticon-info-circle'])[5]";
	public static final String AdvancedInputsText ="//span[text()='Advanced Inputs']";
	public static final String expandAllButton ="//button[@class='ant-btn ant-btn-link CollapseButton_collapseButton_c9o3a']";
	public static final String displayAccountCompanyName ="//span[text()='Display Account/Company Name']";
	public static final String displayAccountNoLabel ="(//div[@class='CollapseHeader_textOverflow_INIKj' and text()='No'])[1]";
	public static final String displayAccountNoText ="//span[text()='Display Account/Company Name']";
	public static final String displayAccountNoIcon="(//span[@class='anticon anticon-caret-right ant-collapse-arrow'])[1]";
	public static final String displayAccountNo="(//span[text()='No'])[1]";
	public static final String displayAccountYes="(//span[text()='Yes'])[1]";
	public static final String includePendingFolioItems ="//span[text()='Include Pending (Unposted) Folio Items']";
	public static final String includePendingNoText="//span[text()='Include Pending (Unposted) Folio Items']";
	public static final String includePendingYesText="//div[@class='CollapseHeader_textOverflow_INIKj' and text()='Yes']";
	public static final String includePendingNoIcon="(//span[@class='anticon anticon-caret-right ant-collapse-arrow'])[2]";
	public static final String includePendingNo="(//span[text()='No'])[2]";
	public static final String includePendingYes="(//span[text()='Yes'])[2]";
	public static final String includeAuthorization ="//span[text()='Include Authorizations']";
	public static final String includeAuthorizationNoText="//span[text()='Include Authorizations']";
	public static final String includeAuthorizationNoLabel="(//div[@class='CollapseHeader_textOverflow_INIKj' and text()='No'])[2]";
	public static final String includeAuthorizationNoIcon="(//span[@class='anticon anticon-caret-right ant-collapse-arrow'])[3]";
	public static final String includeAuthorizationNo="(//span[text()='No'])[3]";
	public static final String includeAuthorizationYes="(//span[text()='Yes'])[3]";
	/************************************Section After Run Report**************************************************/
	public static final String reportTypeHeading ="//span[text()='Report type']";
	public static final String reportTypeValue="(//span[@class='StackView_value_3U7p9'])[1]";
	public static final String effectiveDateHeading = "//span[text()='Effective Date']";
	public static final String effectiveDateValue ="(//span[@class='StackView_value_3U7p9'])[2]";
	public static final String reservationTypeHeading= "//span[text()='Reservation Type']";
	public static final String reservationTypeValue ="(//span[@class='StackView_value_3U7p9'])[3]";
	public static final String includedBalancesHeading= "//span[text()='Included Balances']";
	public static final String includedBalancesValue="(//span[@class='StackView_value_3U7p9'])[4]";
	public static final String generatedOnHeading= "//span[text()='Generated On']";
	public static final String generatedOnValue ="(//span[@class='StackView_value_3U7p9'])[5]";
	public static final String reportSortByHeading= "//span[text()='Sort Report by']";
	public static final String reportSortByValue="(//span[@class='StackView_value_3U7p9'])[6]";
	public static final String displayAccountHeading= "//span[text()='Display Account/Company Name']";
	public static final String displayAccountValue ="(//span[@class='StackView_value_3U7p9'])[7]";
	public static final String includePendingHeading= "//span[text()='Include Pending(Unposted) Folio Items']";
	public static final String includePendingValue ="(//span[@class='StackView_value_3U7p9'])[8]";
	public static final String includeAuthorizationsHeading= "//span[text()='Include Authorizations']";
	public static final String includeAuthorizationsValue ="(//span[@class='StackView_value_3U7p9'])[9]";
	
	public static final String searchForReportNameLable = "//span[@title='Search for Report Name']";
	public static final String netBalance = "//tfoot//td[text()='Net']//following-sibling::td";
	public static final String Folio = "//a[text()='Folio(s)']";
	public static final String FolioStatusPending = "//button[contains(@data-bind,'handleStatusPendingToPost')]";
	
	public static final String IncludeReservationType = "(//span[@class='ant-tree-checkbox-inner']//..)";
	public static final String IncludeReservationTypeText = "(//span[@class='ant-tree-checkbox-inner']//..//following-sibling::span)";

	public static final String balancesFolioBalancesTable="(//td[@class='ant-table-cell TableView_alignRight_2FUA8 TableView_darkGrey_1ZZNL ant-table-cell-ellipsis'])";
	public static final String balanceReservationTable="(//td[@class='ant-table-cell TableView_alignRight_2FUA8'])";
	public static final String receivableSubtotalValue="(//td[text()='Subtotal']//following-sibling::td[@class='ant-table-cell'])";
	public static final String payableSubtotalValue="(//td[text()='Subtotal']//following-sibling::td[@class='ant-table-cell'])[2]";
	public static final String netPayableReceiveableValue="(//td[@class='ant-table-cell'])[6]";
	public static final String folioBalancesLabel="//div[text()='Folio Balances']";
	public static final String receivableBalancesLabel ="//span[text()='Receivable Balances']";
	public static final String payableBalancesLabel ="//span[text()='Payable Balances']";
	public static final String editButton="//button[@class='ant-btn ant-btn-link FilterHeader_editButton_14he8']";
	public static final String guestNameSortedReservationTable="//td[@class='ant-table-cell']//a[@rel='opener']";
	public static final String accountSubtotalValue="//td[text()='Account Subtotal']//following-sibling::td//div";
	public static final String sortedReservationTable="//td[@class='ant-table-cell TableView_alignRight_2FUA8 TableView_greyTitle_3SYL8']";
	public static final String arrivalDateColumnList = "(//table)[3]//tbody//tr//td[4]";
	public static final String departureDateColumnList = "(//table)[3]//tbody//tr//td[5]";
	public static final String roomNoColumnList = "(//table)[3]//tbody//tr//td[3]";
	public static final String folioColumnList = "(//table)[3]//tbody//tr//td[6]";
	public static final String guestList="(//table)[3]//tbody//tr//td[1]";
	
	public static final String getReceivableBalances = "//span[text()='Receivable Balances']/../../..//following-sibling::tbody//tr//td[2]";
	public static final String getPayableBalances = "//span[text()='Payable Balances']/../../..//following-sibling::tbody//tr//td[2]";
	
//	Transactions report
	
	

	public static final String breakOutDailyTotalsYes = "//span/b[contains(text(),'Break Out Daily Totals')]/../../following-sibling::div//span[text()='Yes']/..//input";
	public static final String breakOutDailyTotalsNo = "//span/b[contains(text(),'Break Out Daily Totals')]/../../following-sibling::div//span[text()='No']/..//input";
	public static final String excludeZeroBalancePaymentMethods = "//span[contains(text(),'Exclude Zero Balance Payment Methods')]/../..";
	public static final String excludeZeroBalancePaymentMethodsYes = "//span[contains(text(),'Exclude Zero Balance Payment Methods')]/../../../following-sibling::div//span[text()='Yes']/..//input";
	public static final String excludeZeroBalancePaymentMethodsNo = "//span[contains(text(),'Exclude Zero Balance Payment Methods')]/../../../following-sibling::div//span[text()='No']/..//input";
	
	public static final String summaryViewCashAmount = "//h2[contains(text(),'Summary View')]/../following-sibling::div//td[@title='Cash']/following-sibling::td";
	public static final String summaryViewCheckAmount = "//h2[contains(text(),'Summary View')]/../following-sibling::div//td[@title='Check']/following-sibling::td";
	public static final String summaryViewCreditCardAmount = "//h2[contains(text(),'Summary View')]/../following-sibling::div//td[@title='Credit Card']/following-sibling::td";
	//public static final String summaryViewTotalAmount = "//h2[contains(text(),'Summary View')]/../following-sibling::div//td[text()='Payment Method Total']/following-sibling::td/div";
	public static final String summaryViewTotalAmount = "//h2[contains(text(),'Summary View')]/../following-sibling::div//tfoot//td[text()='Net Total']/following-sibling::td";
	
	public static final String sortReportBy = "//b[text()='Sort Report By']/../../following-sibling::div//span[@class='ant-select-selection-item']";
	
	public static final String includeDataFrom = "//span[text()='Include Data From']/../following-sibling::div//div[contains(@class,'CollapseHeader')]";
	public static final String includeAdditionalColumn = "//span[text()='Include Additional Column']/../following-sibling::div//div[contains(@class,'CollapseHeader')]";
	public static final String excludeZeroBalancePaymentMethodsSelected = "//span[text()='Exclude Zero Balance Payment Methods']/../following-sibling::div//div[contains(@class,'CollapseHeader')]";
	
	public static final String propertyName = "//div[contains(@class,'PropertySelect')]";
	
	public static final String ProptyName ="//div[contains(@class,'PropertySelect_property')]//span[@class='ant-select-selection-item']";
	public static final String Heading ="//h1[@class='StandardView_title_10KQ1']";
	public static final String effectiveDateHeader = "//div[contains(text(), 'Effective date')]//p";
	public static final String reservationTypeHeader = "//div[contains(text(), 'Reservation Type')]//p";
	public static final String includeBalancesHeader  = "//div[contains(text(), 'Included Balances')]//p";
	public static final String sortReportByHeader = "//div[contains(text(), 'Sort Report by')]//p";
	public static final String bookOnDateRangeExpand = "(//input[@type='search'])[5]";
	public static final String selectedGroupNetSalesBy = "//b[text()='Group Net Sales By']//..//..//following-sibling::div//span[@class='ant-select-selection-item']";
	public static final String selectedStayOnDateRange = "(//div[@class='ComponentBuilder_datePicker_Gt0CI']//span[@class='ant-select-selection-item'])[1]";
	public static final String selectedSortReportBy = "//b[text()='Sort Report By']//..//..//following-sibling::div//span[@class='ant-select-selection-item']";
	public static final String selectedGroupRowBy = "//b[text()='Group Rows By']//..//..//following-sibling::div//span[@class='ant-select-selection-item']";
	public static final String selectedBookOnDateRange = "//span[text()='Booked On Date Range']//..//..//..//following-sibling::div//span[@class='ant-select-selection-item']";
	public static final String radioNoBtnIncludeInactive = "(//span[text()='No']//..//span)[1]";
	public static final String radioYesBtnIncludeInactive = "//span[text()='Yes']";
	public static final String GroupNetSalesByOptionsExpand = "//input[@id='groupBy']";
	public static final String netSalesReportMainHeader = "//div[text()='Net Sales Report']";
	public static final String advancedInputs = "//span[text()='Advanced Inputs']";
	public static final String chooseDateRangeLabelIndex = "//b[text()='Choose Date Range']//..//..//preceding-sibling::span/span";
	public static final String chooseDateRange = "//b[text()='Choose Date Range']//parent::span";
	public static final String bookedDateRange = "//span[text()='Booked On Date Range']";
	public static final String includeInactiveObseleteRooms = "//span[text()='Include Inactive/ Obsolete  Rooms']";
	public static final String groupNetSaleBy = "//b[text()='Group Net Sales By']";
	public static final String rowsGroupByInNetSales = "//input[@id='rowsGroupBy']";
	public static final String groupRowsBy = "//b[text()='Group Rows By']";
	
	public static final String totalNetReservationForNetSaleReport = "//td[text()='Total']//..//../td[2]";
	public static final String totalNetRoomNightsForNetSaleReport = "//td[text()='Total']//..//../td[3]";
	public static final String channelNetRoomNightsForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[3])[1]";
	public static final String totalBookingNightsForNetSaleReport = "//td[text()='Total']//..//../td[4]";
	public static final String channelBookingNightsForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[4])[1]";
	public static final String channelRoomRevenueForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[5])[1]";
	public static final String totalRoomRevenueForNetSaleReport = "//td[text()='Total']//..//../td[5]";
	public static final String channelOtherRevenueForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[6])[1]";
	public static final String totalOtherRevenueForNetSaleReport = "//td[text()='Total']//..//../td[6]";
	public static final String channelRevenueForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[7])[1]";
	public static final String totalRevenueForNetSaleReport = "//td[text()='Total']//..//../td[7]";
	public static final String channelCancelForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[8])[1]";
	public static final String totalCancelForNetSaleReport = "//td[text()='Total']//..//../td[8]";
	public static final String channelAvgStayForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[9])[1]";
	public static final String totalAvgStayForNetSaleReport = "//td[text()='Total']//..//../td[9]";
	public static final String channelAvgDailyRateForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[10])[1]";
	public static final String totalAvgDailyRateForNetSaleReport = "//td[text()='Total']//..//../td[10]";
	public static final String channelRevPARForNetSaleReport = "((//h2[contains(text(),'Summary View')]/../following-sibling::div)[1]//tbody//td[11])[1]";
	public static final String totalRevPARForNetSaleReport = "//td[text()='Total']//..//../td[11]";
	public static final String reportLoading="//div[@class='DotsLoading_dots__2QvTg']";
	public static final String netSalesReportTopHeader="//div[@class='FilterHeader_inlineText_2I8eZ']/p";
	public static final String netSalesReportMiddleHeader="//span[@class='StackView_value_3U7p9']";
	public static final String netSalesDetailViewTableHeader="//h2[contains(text(),'Detailed View | Net Sales Report')]/parent::div//following-sibling::div//th";


    //Reservation History Objects
	
	public static final String reservationHistoryAdvancedInputsExpandAll="//div[@class='SectionTitle_title_2sEBt NumericListItem_title_3MeuE']//button[text()='Expand All']";
	public static final String reservationHistoryDefaultDateRange="//div[@class='ComponentBuilder_datePicker_Gt0CI']//span[@title='Today']";
	public static final String reservationHistoryStartDate="//div[@class='ant-picker ant-picker-range DatePicker_dateInput_3E0qB']//input[@placeholder='Start date']";
	public static final String reservationHistoryEndDate="//div[@class='ant-picker ant-picker-range DatePicker_dateInput_3E0qB']//input[@placeholder='End date']";
	public static final String reservationHistorySelectedInputCheckBoxs="//div[@class='ant-tree-list-holder-inner']//span[contains(@class,'ant-tree-checkbox-checked')]";
	public static final String reservationHistoryAdvanceInputUser="//div[@class='ComponentBuilder_root_1LG9G']//span[@title='All Users']";
	public static final String reservationHistoryShiftTime="//div[@class='ComponentBuilder_root_1LG9G']//button[@aria-checked='false']";
	public static final String reservationHistoryShortReportBy="//div[@class='ComponentBuilder_root_1LG9G']//span[@title='Guest Name']";
	public static final String reservationHistoryShiftTimeToggle="//button[@role='switch']";
	public static final String  reservationHistorygetShiftTime="//div[@class='TimePickerInput_timePickerInput_2tDPQ']//input";
	public static final String  reservationHistoryIncludeDataFrom="//span[text()='Include Data From']/../../../following-sibling::div//div[contains(@class,'SelectList_selectList_12ajo')]//span[2]";
	public static final String  reservationHistoryIncludeDataFromUser="(//div[@class='ComponentBuilder_root_1LG9G'])[4]//div[contains(@class,'SelectList_selectList_12ajo')]//input";
	public static final String  reservationHistoryIncludeReservationHistoryCat="//div[@class='SectionTitle_title_2sEBt CheckBoxList_sectionTitle_3fRXZ']/span[@class='ToolTip_popoverContainer_15yTL']";
	public static final String  reservationHistorytoolTip="//div[contains(@class,'ToolTip')]//p";

	
	
	public static final String summaryViewGiftCertificateAmount="//h2[contains(text(),'Summary View')]/../following-sibling::div//td[@title='Gift Certificate']/following-sibling::td";
	public static final String summaryCreditCardCaptureAmount="//span[text()='Credit Card Transaction Type']/../../../following-sibling::tbody/tr[2]/td[3]";

	//public static final String summaryCreditCardAuthorizedAmount="//span[text()='Credit Card Transaction Type']/../../../following-sibling::tbody/tr[2]/td[3]";

	public static final String summaryCreditCardRefundAmount="//span[text()='Credit Card Transaction Type']/../../../following-sibling::tbody/tr[3]/td[3]";
	public static final String summaryCreditCardAuthorizedAmount ="//span[text()='Credit Card Transaction Type']/../../../following-sibling::tbody/tr/td[3]";

	public static final String noDataAvailable = "//div[text()='No Report Data Available']";


	 //Reservation Details Objects
	public static final String ReservationDetailsReport = "//a[text()='Reservation Details'][contains(@class,'ReportListItem')]";
	public static final String ReservationDetailsReportHeader = "//h2[text()='Reservation Details Report']";
	


	public static final String reservationCardReportHeader="//h2[text()='Reservation Cards Report']";
	public static final String reservationCardReport="//a[text()='Reservation Cards'][contains(@class,'ReportListItem')]";
	public static final String effectiveDateDropdownOption="//div[@class='ComponentBuilder_datePicker_Gt0CI']//span[contains(@class,'ant-select-selection-item')]";
	public static final String sortReportListOfOption="//div[contains(@class,'ant-select-item ant-select-item')]";
	
	
	public static final String checkInHouseCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[1]";
	
	public static final String checkdepartedCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[2]";
	
	public static final String checkcancelledCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[3]";

	public static final String checkreservedCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[4]";
	
	public static final String checknoShowCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[5]";
	
	public static final String checkconfirmedCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[6]";
	
	public static final String checkonHoldCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[7]";
	
	public static final String checkguaranteedCheckBox ="(//span[@class='ant-tree-checkbox-inner'])[8]";
	
	public static final    String  reportname="//h1[contains(text(),'Folio Balances Report ')]";
	public static final     String reportType="//span[text()='Report type']/following-sibling::span";
	public static final    String  effectiveDate2="//span[text()='Effective Date']/following-sibling::span";
	public static final     String ReservationType="//span[text()='Reservation Type']/following-sibling::span";
	public static final     String IncludedBalanc="//span[text()='Included Balances']/following-sibling::span";
	public static final     String GeneratedOn="//span[text()='Generated On']/following-sibling::span";
	public static final     String SortReport_by="//span[text()='Sort Report by']/following-sibling::span";
	public static final     String DisplayAccount_CompanyName="//span[text()='Display Account/Company Name']/following-sibling::span";
	public static final     String IncludePending_Unposted_FolioItems="//span[text()='Include Pending(Unposted) Folio Items']/following-sibling::span";
	public static final    String IncludeAuthorizations="//span[text()='Include Authorizations']/following-sibling::span";
	public static final    String receivableCancelledAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='Cancelled']/following-sibling::td";
	public static final    String receivableReservedAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='Reserved']/following-sibling::td";
	public static final    String receivableConfirmedAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='Confirmed']/following-sibling::td";
	public static final    String receivableNoShowAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='No Show']/following-sibling::td";
	public static final    String receivableOnHoldAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='On Hold']/following-sibling::td";
	public static final    String receivableGuaranteedAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='Guaranteed']/following-sibling::td";
	public static final    String receivableDepartedAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='Departed']/following-sibling::td";
	public static final    String receivableInHouseAmount="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//td[@title='In-House']/following-sibling::td";

	public static final    String receivableSubTotal="//span[text()='Receivable Balances']/../../.././following-sibling::tbody//following-sibling::tfoot//td[text()='Subtotal']/following-sibling::td/div";
	
	public static final    String payableCancelledAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='Cancelled']/following-sibling::td";
	public static final    String payableReservedAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='Reserved']/following-sibling::td";
	public static final    String payableConfirmedAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='Confirmed']/following-sibling::td";
	public static final    String payableNoShowAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='No Show']/following-sibling::td";
	public static final    String payableOnHoldAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='On Hold']/following-sibling::td";
	public static final    String payableGuaranteedAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='Guaranteed']/following-sibling::td";
	public static final    String payableDepartedAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='Departed']/following-sibling::td";
	public static final    String payableInHouseAmount="//span[text()='Payable Balances']/../../.././following-sibling::tbody//td[@title='In-House']/following-sibling::td";

	public static final    String payableSubTotal="//span[text()='Payable Balances']/../../.././following-sibling::tbody//following-sibling::tfoot//td[text()='Subtotal']/following-sibling::td/div";
	public static final    String payableNetTotal="//tr[contains(@class,'TableView_summaryTable_26GOW')]//td[text()='Net']//following-sibling::td";
	public static final    String customDate  = "//div[@class='ant-picker DatePicker_dateInput_3E0qB DatePicker_datePicker_3iOhz']";
	public static final    String customDateInput="//td[contains(@class,'ant-picker-cell ant-picker-cell-in-view ant-picker-cell-today ant-picker-cell-selected')]/following-sibling::td/div";


}
