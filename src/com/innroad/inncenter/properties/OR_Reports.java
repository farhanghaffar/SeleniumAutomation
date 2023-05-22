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

	// Reports Main Page
	public static final String PerformanceFilter = "//span[text()='Performance']";
	public static final String AccountingFilter = "//span[text()='Accounting']";
	public static final String PerformanceReportsHeading = "//span[text()='Performance Reports']";
	public static final String AccountingReportsHeading = "//span[text()='Accounting Reports']";
	public static final String DailyFlashReport = "//a[text()='Daily Flash']";
	public static final String NetSalesReport = "//a[text()='Net Sales']";
	public static final String RoomForecastReport = "//a[text()='Room Forecast']";
	public static final String AdvanceDepositReport = "//a[text()='Advance Deposit']";
	public static final String AccountBalancesReport = "//a[text()='Account Balances']";
	public static final String LedgerBalancesReport = "//a[text()='Ledger Balances']";
	public static final String Incidentals = "//a[text()='Incidentals']";
	public static final String PaymentMethod = "//a[text()='Payment Method']";
	public static final String Taxes = "//a[text()='Taxes']";
	public static final String TransactionsReport = "//a[text()='Transactions']";
	public static final String Help = "//span[text()='Help']";
	public static final String DailyFlashHelp = "//a[text()='Daily Flash']/following-sibling::a[text()='Help']";
	public static final String NetSalesHelp = "//a[text()='Net Sales']/following-sibling::a[text()='Help']";
	public static final String RoomForecastHelp = "//a[text()='Room Forecast']/following-sibling::a[text()='Help']";
	public static final String AdvanceDepositHelp = "//a[text()='Advance Deposit']/following-sibling::a[text()='Help']";
	public static final String AccountBalancesHelp = "//a[text()='Account Balances']/following-sibling::a[text()='Help']";
	public static final String LedgerBalancesHelp = "//a[text()='Ledger Balances']/following-sibling::a[text()='Help']";
	public static final String TransactionsHelp = "//a[text()='Transactions']/following-sibling::a[text()='Help']";
	public static final String ReportsHelpPageHeader = "//h1[text()='Reports']";

	// Ledger Reports Page
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
	public static final String NoReportDataMessage = "//div[contains(text(),'No Report Data Available')]";
	public static final String Collapse = "//span[text()='Collapse']";
	public static final String Edit = "//span[text()='Edit ']";
	public static final String ChoseDateRange = "//*[text()='Choose Date Range']";
	public static final String SelectInputs = "//*[text()='Select Inputs']";
	public static final String CustomizeDetailedView = "//*[text()='Customize Detailed View']";
	public static final String ExcelExportToolTipBeforeRunReport = "//span[text()='Excel']/../../..//div[@class='ant-popover-title']";
	public static final String PDFExportToolTipBeforeRunReport = "//span[text()='PDF']/../../..//div[@class='ant-popover-title']";
	public static final String PrintToolTipBeforeRunReport = "//span[text()='Print']/../../..//div[@class='ant-popover-title']";
	public static final String ExcelExportToolTipAfterRunReport = "//span[text()='Excel']/..";
	public static final String PDFExportToolTipAfterRunReport = "//span[text()='PDF']/..";
	public static final String PrintToolTipAfterRunReport = "//span[text()='Print']/..";
	public static final String SortReportBy = "//*[text()='Sort Report By']";
	public static final String GroupRowsByToolTip = "(//div[@role='tooltip'])[2]";
	public static final String SortReportByOptionsExpand = "//input[@id='sortBy']";
	public static final String GroupRowsByOptionsExpand = "//input[@id='groupBy']";

	public static final String AdvancedInputs = "//*[text()='Advanced Inputs']";

	// public static final String AdvancedOptions = "//*[text()='Advanced
	// Options']";
	public static final String ExpandAll = "//*[text()='Expand All']";
	public static final String CollapseAll = "//*[text()='Collapse All']";
	public static final String AccountType = "//*[text()='Account Type']";
	public static final String ItemStatus = "//*[text()='Item Status']";
	public static final String IncludeDataFrom = "//*[text()='Include Data From']";
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

	public static final String AccountTypeClearAll = "//*[text()='Account Type']/..//span[text()='Clear all']";
	public static final String ItemStatusClearAll = "//*[text()='Item Status']/..//span[text()='Clear all']";
	public static final String ReservationStatusClearAll = "//*[text()='Reservation Status']/..//span[text()='Clear all']";
	public static final String AccountTypeSelectAll = "//*[text()='Account Type']/..//span[text()='Select all']";
	public static final String ItemStatusSelectAll = "//*[text()='Item Status']/..//span[text()='Select all']";
	public static final String ReservationStatusSelectAll = "//*[text()='Reservation Status']/..//span[text()='Select all']";

	public static final String AccountTypesOptions = "//div[@id='accountTypes']/label";
	public static final String ItemStatusOptions = "//div[@id='itemStatus']/label";
	public static final String ReservationStatusOptions = "//div[@id='reservationStatus']/label";
	public static final String AdvancedDeposits = "//span[text()='Advanced Deposits']";

	public static final String Pending = "//*[text()='Pending']";
	public static final String Users = "//*[text()='Users']";
	public static final String Properties = "//*[text()='Properties']";
	public static final String ShiftTime = "//*[text()='Shift Time']";
	public static final String TaxExempt = "//input[@id='taxExempt']";
	public static final String MarktSegmnt = "//input[@id='marketSegment']";
	public static final String OnHold = "//*[text()='On Hold']";
	public static final String referrals = "//input[@id='referrals']";

	// pages not yet developed need to be verified after pages developed
	public static final String DailyFlashReportHeader = "//h2[text()='Daily Flash Report']";
	public static final String NetSalesReportHeader = "//h2[text()='Net Sales Report']";
	public static final String RoomForecastReportHeader = "//h2[text()='Room Forecast Report']";
	public static final String AdvanceDepositReportHeader = "//h2[text()='Advance Deposit Report']";
	public static final String AccountBalancesReportHeader = "//h2[text()='Account Balances Report']";
	public static final String TransactionsReportHeader = "//h2[text()='Transactions Report']";

	public static final String LedgerBalancesSubTitle = "//p[text()='Detailed transactions for all ledger accounts selected']";
	public static final String RunReportAtBottom = "(//span[text()='Run Report'])[2]";
	public static final String TaxExemptListExpand = "//input[@id='taxExempt']";
	public static final String MarketSegmentListExpand = "//input[@id='marketSegment']";
	public static final String ReferralstListExpand = "//input[@id='referrals']";

	// Reports V2
	
		//Reports Main Page
		public static final String PerformanceFilter = "//span[text()='Performance']";
		public static final String AccountingFilter = "//span[text()='Accounting']";
		public static final String PerformanceReportsHeading = "//span[text()='Performance Reports']";
		public static final String AccountingReportsHeading = "//span[text()='Accounting Reports']";
		public static final String DailyFlashReport = "//a[text()='Daily Flash']";
		public static final String NetSalesReport = "//a[text()='Net Sales']";
		public static final String RoomForecastReport = "//a[text()='Room Forecast']";
		public static final String AdvanceDepositReport = "//a[text()='Advance Deposit']";
		public static final String AccountBalancesReport = "//a[text()='Account Balances']";
		public static final String LedgerBalancesReport = "//a[text()='Ledger Balances']";
		public static final String Incidentals = "//a[text()='Incidentals']";
		public static final String PaymentMethod = "//a[text()='Payment Method']";
		public static final String Taxes = "//a[text()='Taxes']";
		public static final String TransactionsReport = "//a[text()='Transactions']";
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
		
		public static final String AccountTypeClearAll = "//*[text()='Account Type']/..//span[text()='Clear all']";	
		public static final String ItemStatusClearAll = "//*[text()='Item Status']/..//span[text()='Clear all']";	
		public static final String ReservationStatusClearAll = "//*[text()='Reservation Status']/..//span[text()='Clear all']";
		public static final String AccountTypeSelectAll = "//*[text()='Account Type']/..//span[text()='Select all']";	
		public static final String ItemStatusSelectAll = "//*[text()='Item Status']/..//span[text()='Select all']";	
		public static final String ReservationStatusSelectAll = "//*[text()='Reservation Status']/..//span[text()='Select all']";
		
		public static final String AccountTypesOptions = "//div[@id='accountTypes']/label";
		public static final String ItemStatusOptions = "//div[@id='itemStatus']/label";		
		public static final String ReservationStatusOptions = "//div[@id='reservationStatus']/label";
		public static final String AdvancedDeposits="//span[text()='Advanced Deposits']";
		
		
		
		
		public static final String Pending="//*[text()='Pending']";
		public static final String Users="//*[text()='Users']";
		public static final String Properties="//*[text()='Properties']";
		public static final String ShiftTime="//*[text()='Shift Time']";
		public static final String TaxExempt="//input[@id='taxExempt']";
		public static final String MarktSegmnt="//input[@id='marketSegment']";
		public static final String OnHold="//*[text()='On Hold']";
		public static final String referrals="//input[@id='referrals']";
		
		//pages not yet developed need to be verified after pages developed
		public static final String DailyFlashReportHeader = "//h2[text()='Daily Flash Report']";
		public static final String NetSalesReportHeader = "//h2[text()='Net Sales Report']";
		public static final String RoomForecastReportHeader = "//h2[text()='Room Forecast Report']";
		public static final String AdvanceDepositReportHeader = "//h2[text()='Advance Deposit Report']";
		public static final String AccountBalancesReportHeader = "//h2[text()='Account Balances Report']";
		public static final String TransactionsReportHeader = "//h2[text()='Transactions Report']";
	
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

	// Reports V2 - Ledger Balances Report - Select Inputs
	// public static final String txtSelectInputs = "//span[@class='ant-typography
	// NumericListItem_title_3MeuE']/b[text()='Select Inputs']";
	public static final String txtSelectInputs = "//*[contains(text(),'Select Inputs')]";
	public static final String txtIncludeLedgerAccount = "//span[@class='LabelWithToolTip_title_2AT-P']/b[text()='Included Ledger Accounts']";
	public static final String tooltip = "//div[@class='ant-tooltip-inner' and @role='tooltip']";
	public static final String infoIncludeLedgerAccount = "//span[text()='Include Ledger Account']//following-sibling::span[@class='anticon anticon-info-circle LabelWithToolTip_infoIcon_39Pyt']";
	public static final String buttonClearAll = "//span[text()='Clear All']//parent::button"; //// span[text()='Clear
																								//// All']
	public static final String buttonSeeAll = "//span[text()='See All']";
	public static final String selectIncidentals = "//span[contains(@class,'MultiSelectControls') and contains(text(),'Incidental')]//parent::button";
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

	public static final String popupMessage = "//div[contains(@id,'rcDialogTitle0') and @class='ant-modal-title']";
	public static final String buttonClosePopup = "//button[@aria-label='Close']";
	// public static final String buttonSavePopup = "//button[@class='ant-btn
	// ant-btn-success']";
	// public static final String buttonSavePopup =
	// "(//button[contains(@class,'ant-btn-success')])[3]";
	public static final String buttonSavePopup = "//button[contains(@class,'ant-btn-success')]/span[contains(text(),'Save')]";
	public static final String buttonCancelPopup = "//button[@class='ant-btn ant-btn-cancel']";

	public static final String inputAllCategories = "//li[text()='All Categories']";
	public static final String inputIncidentals = "//li[text()='Incidentals']";
	public static final String inputPaymentMethod = "//li[text()='Payment Method']";
	public static final String inputTaxes = "//li[text()='Taxes']";
	public static final String inputRoomCharges = "//li[text()='Room Charges']";
	public static final String inputFees = "//li[text()='Fees']";
	public static final String inputTransfers = "//li[text()='Transfers']";
	public static final String inputOther = "//li[text()='Other']";

	//public static final String clickSelectAll = "//span[@type='double-right']";
	//public static final String clickRemoveAll = "//span[@type='double-left']";
	public static final String clickSelectAll = "//a[@role='button']/span[contains(text(),'Add All')]";
	public static final String clickRemoveAll = "//a[@role='button']/span[contains(text(),'Remove all')]";

	public static final String txtExcludeZeroBalanceLedgerAccounts = "//*[text()='Exclude Zero Balance Ledger Accounts']";
	public static final String tooltipExcludeZeroBalanceLedgerAccounts = "//span/b[text()='Exclude Zero Balance Ledger Accounts']//parent::span//following-sibling::span";
	public static final String rdoYesDisplayCustomGeneral = "//div[@id='displayLedgerAccountNumber']/label/span/input[@value='1']";
	public static final String rdoNoDisplayCustomGeneral = "//div[@id='displayLedgerAccountNumber']/label/span/input[@value='0']";

	public static final String txtDisplayCustomGeneralLedgerAccount = "//*[text()='Display Custom General Ledger Account #']";
	public static final String tooltipDisplayCustomGeneralLedgerAccount = "//span/b[text()='Display Custom General Ledger Account #']//parent::span//following-sibling::span";
	public static final String rdoYesExcludeZero = "//div[contains(@id,'excludeZero')]/label/span/input[@value='1']";
	public static final String rdoNoExcludeZero = "//div[contains(@id,'excludeZero')]/label/span/input[@value='0']";

	public static final String searchAvailable = "//h3[text()='Available']//following-sibling::span/input[@name='searchKey']";
	public static final String searchSelected = "//h3[text()='Selected']//following-sibling::span/input[@name='searchKey']";

	public static final String IncludedLedgerAccounts = "//*[text()='Included Ledger Accounts']";
	public static final String ExcludeZeroBalanceLedgerAccounts = "//*[text()='Exclude Zero Balance Ledger Accounts']";
	public static final String DisplayCustomGeneralLedgerAccount = "//*[text()='Display Custom General Ledger Account #']";

	public static final String IncludedLedgerAccountsToolTipIcon = "//*[text()='Included Ledger Accounts']/../..//span[@aria-label='info-circle']";
	public static final String ExcludeZeroBalanceLedgerAccountsToolTipIcon = "//*[text()='Exclude Zero Balance Ledger Accounts']/../..//span[@aria-label='info-circle']";
	public static final String DisplayCustomGeneralLedgerAccountToolTipIcon = "//*[text()='Display Custom General Ledger Account #']/../..//span[@aria-label='info-circle']";

	// Reports V2
	public static final String linkBrowseAllReports = "//a[text()='Browse All Reports']";
	public static final String Reports2 = "//a[text()='Reports']";
	public static final String Report2 = "(//a[text()='Reports'])[2]";
	public static final String ReportNew = "(//span[text()='Reports'])[2]";

	public static final String SearchReports = "//input[@class='ant-select-selection-search-input' and @role='combobox']";
	public static final String SearchElements = "//div[@class='ant-select-item-option-content']";

	// Navigate to other modules
	public static final String linkReservations = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='reservation']";
	public static final String linkAccounts = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='accounts']";
	public static final String linkGuestServices = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='gustServices']";
	public static final String linkInventory = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='inventory']";
	public static final String linkSetup = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='setup']";
	public static final String linkAdmin = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='admin']";
	public static final String linkNightAudit = "//i[@class='MainMenu_menuIcon__X5T_6' and @icon='nightAudit']";

	// Advanced Options - Include Data From
	public static final String usersExpand = "//input[@id='users']";
	public static final String buttonShiftTime = "//button[@role='switch']";
	public static final String inputStartShiftTime = "(//input[@placeholder='Select time'])[1]";
	public static final String inputEndShiftTime = "(//input[@placeholder='Select time'])[2]";

	public static final String buttonNowShiftTimeStart = "(//a[@class='ant-picker-now-btn'])[1]";
	public static final String buttonNowShiftTimeEnd = "(//a[@class='ant-picker-now-btn'])[2]";
	public static final String buttonOkShiftTimeStart = "(//button[@class='ant-btn ant-btn-primary ant-btn-sm'])[1]";
	public static final String buttonOkShiftTimeEnd = "(//button[@class='ant-btn ant-btn-primary ant-btn-sm'])[2]";

	// Ledger Balances - Choose Date Range
	public static final String date = "//div[@class='ant-picker ant-picker-range DateRange_dateInput__3FRhb']";
	public static final String dateDropDown = "//input[@id='rc_select_0']";
	public static final String dateStart = "//input[@placeholder='Start date']";
	public static final String dateEnd = "//input[@placeholder='End date']";
	public static final String dateOptions = "//div[@class='ant-select-item-option-content']";
	// public static final String dateOptions = "//span[@class='ant-tag
	// ant-tag-blue']";
	public static final String dateSeparator = "//span[@class='ant-picker-separator']";
	public static final String dateCustomRange = "//div[text()='Custom Range']";

	public static final String dateTab = "//div[@class='ant-picker-panels']";
	public static final String daySelected = "//td[@class='ant-picker-cell ant-picker-cell-in-view ant-picker-cell-range-start ant-picker-cell-range-end ant-picker-cell-today ant-picker-cell-selected']/div";

	public static final String buttonYear = "(//button[@class='ant-picker-month-btn'])[1]";
	public static final String buttonMonth = "(//button[@class='ant-picker-year-btn'])[1]";

	public static final String buttonReturnToDefault = "//span[contains(text(),'Return to Default')]";
	public static final String symbolReturnToDefault = "//span[@aria-label='rollback']";

	public static final String ReturnToDefaultSeccess = "//div[contains(text(),'Success')]";
	public static final String ReturnToDefaultFailure = "//div[contains(text(),'No Report Data Available')]";
	public static final String ReturnToDefaultDescription = "//div[contains(text(),'Report input')]";

	// Ledger Run Report

	public static final String GRHeaderTitle = "//h1[text()='Ledger Balances Report - All']";

	public static final String GRReportTypeCaption = "//span[text()='Report type: ']";
	public static final String GRReportTypeValue = "////span[text()='Report type: ']/following-sibling::span";

	//Ledger Run Report
	
	public static final String GRHeaderTitle = "//h1[contains(text(),'Ledger Balances Report -')]";
	
	public static final String GRReportTypeCaption = "//span[text()='Report type: ']";
	public static final String GRReportTypeValue = "//span[text()='Report type: ']/following-sibling::span";
	
	public static final String GRDateRangeCaption = "//span[text()='Date Range: ']";
	public static final String GRDateRangeValue = "//span[text()='Date Range: ']/following-sibling::span";

	public static final String GRIncludedLedgerAccountsCaption = "//span[text()='Included Ledger Accounts: ']";
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

	public static final String GRReservationStatusCaption = "//span[text()='Reservation Status: ']";
	public static final String GRReservationStatusValue = "//span[text()='Reservation Status: ']/following-sibling::span";

	public static final String GRAccountTypeCaption = "//span[text()='Account Type: ']";
	public static final String GRAccountTypeValue = "//span[text()='Account Type: ']/following-sibling::span";

	public static final String GRIncludeDataFromCaption = "//span[text()='Include Data From: ']";
	public static final String GRIncludeDataFromValue = "//span[text()='Include Data From: ']/following-sibling::span";

	
	public static final String GRIncludeDataFromCaption = "//span[text()='Include Data From: ']";
	public static final String GRIncludeDataFromValue = "//span[text()='Include Data From: ']/following-sibling::span";
			
	public static final String GRGeneratedOnCaption = "//span[text()='Generated On: ']";
	public static final String GRGeneratedOnValue= "//span[text()='Generated On: ']/following-sibling::span";
	
	public static final String GRSortReportByCaption = "//span[text()='Sort Report by: ']";
	public static final String GRSortReportByValue = "//span[text()='Sort Report by: ']/following-sibling::span";

	public static final String GRGroupRowsByCaption = "//span[text()='Group Rows by: ']";
	public static final String GRGroupRowsByValue = "//span[text()='Group Rows by: ']/following-sibling::span";

	public static final String GRItemStatusCaption = "//span[text()='Item Status: ']";
	public static final String GRItemStatusValue = "//span[text()='Item Status: ']/following-sibling::span";

	public static final String GRTaxExemptCaption = "//span[text()='Tax Exempt: ']";
	public static final String GRTaxExemptValue = "//span[text()='Tax Exempt: ']/following-sibling::span";

	public static final String GRMarketSegmentCaption = "//span[text()='Market Segment: ']";
	public static final String GRMarketSegmentValue = "//span[text()='Market Segment: ']/following-sibling::span";

	public static final String GRReferralsCaption = "//span[text()='Referrals: ']";
	public static final String GRReferralsValue = "//span[text()='Referrals: ']/following-sibling::span";

	public static final String GRExcludeZeroBalanceLedgerAccountsCaption = "//span[text()='Exclude Zero Balance Ledger Accounts: ']";
	public static final String GRExcludeZeroBalanceLedgerAccountsValue = "//span[text()='Exclude Zero Balance Ledger Accounts: ']/following-sibling::span";

	public static final String GRSummaryViewHeader = "//h1[text()='Summary View - Ledger Balances Report']";
	
	
	public static final String GRSummaryViewHeader = "//h2[text()='Summary View | Ledger Balances Report']";
	public static final String GRLedgerAccountCategory = "//div[text()='Ledger Account Category']";
	public static final String GRLedgerAccountCategoryBalance = "//div[text()='Ledger Account Category']/../..//th[text()='Balance']";
	public static final String GRLedgerAccountCategoryToolTipIcon = "//div[text()='Ledger Account Category']/span/span";
	public static final String GRLedgerAccountCategoryToolTipHeader = "//div[@class='ant-popover-title']/b[text()='Ledger Account Category']";
	public static final String GRLedgerAccountCategoryToolTipContent = "//div[@class='ant-popover-inner-content']/p[contains(text(),'Each ledger account is divided into different categories')]";
	public static final String GRLedgerAccountCategoryTotalCaption = "//td[text()='Ledger Account Category Total']";
	
	//After Run Report - Ledger Balances
	public static final String tableLedgerAccountCategory = "//div[contains(text(),'Ledger Account Category')]//ancestor::table";
	
	
	
	
	public static final String ScrollToTop = "//span[text()='Top']";
	
	public static final String FilterHeaderDateRangeCaption = "//div[contains(@class,'filterBoxHeader')]//p[text()='Date Range']";
	public static final String FilterHeaderIncludedLedgerAccountsCaption = "//div[contains(@class,'filterBoxHeader')]//p[text()='Included Ledger Accounts']";
	public static final String FilterHeaderTaxExemptCaption = "//div[contains(@class,'filterBoxHeader')]//p[text()='Tax Exempt']";
	public static final String FilterHeaderSortReportByCaption = "//div[contains(@class,'filterBoxHeader')]//p[text()='Sort Report by']";
	public static final String FilterHeaderGroupByCaption = "//div[contains(@class,'filterBoxHeader')]//p[text()='Group by']";

	public static final String FilterHeaderDateRangeValue = "//div[contains(@class,'filterBoxHeader')]//p[text()='Date Range']/following-sibling::p";
	public static final String FilterHeaderIncludedLedgerAccountsValue = "//div[contains(@class,'filterBoxHeader')]//p[text()='Included Ledger Accounts']/following-sibling::p";
	public static final String FilterHeaderTaxExemptValue = "//div[contains(@class,'filterBoxHeader')]//p[text()='Tax Exempt']/following-sibling::p";
	public static final String FilterHeaderSortReportByValue = "//div[contains(@class,'filterBoxHeader')]//p[text()='Sort Report by']/following-sibling::p";
	public static final String FilterHeaderGroupByValue = "//div[contains(@class,'filterBoxHeader')]//p[text()='Group by']/following-sibling::p";
	public static final String NoReportDataAvailableMessageHeading = "//div[text()='No Report Data Available']";
	public static final String NoReportDataAvailableMessageContent = "//div[text()='Please update report inputs and try running the report again.']";

	public static final String LoadingReportMessage = "//*[text()='Loading Report']";
	public static final String LoadingReportCancelButton = "//*[text()='Cancel']";

}
