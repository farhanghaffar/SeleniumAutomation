package com.innroad.inncenter.webelements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reports;

public class Elements_Reports {
	

	WebDriver driver ;


	public static Logger app_logs = Logger.getLogger("devpinoyLogger");
		
		public Elements_Reports(WebDriver driver2)
		{
			this.driver=driver2;
			PageFactory.initElements(this.driver, this);
			
			 
		}

		@FindBy(xpath=OR.AccountsBalance)
		public WebElement AccountsBalance;
		
		@FindBy(xpath=OR.Select_LedgerType)
		public WebElement Select_LedgerType;
		
		@FindBy(xpath=OR.AccountStatus)
		public WebElement AccountStatus;
		
		@FindBy (xpath = OR.AccountBalance_EffectiveDate)
		public WebElement AccountBalance_EffectiveDate;
		
		@FindBy(xpath=OR.AccountBalanceGo_Button)
		public WebElement AccountBalanceGo_Button;
		
		@FindBy(xpath=OR.AccountBalance_Header)
		public WebElement AccountBalance_Header;
		
		@FindBy(xpath=OR.ReservationType_Current)
		public WebElement ReservationType_Current;
		
		@FindBy(xpath=OR.ReservationType_Past)
		public WebElement ReservationType_Past;
		
		@FindBy(xpath=OR.ReservationType_Future)
		public WebElement ReservationType_Future;
		
		@FindBy(xpath=OR.Reservation_Pending)
		public WebElement Reservation_Pending;
		
		@FindBy(xpath=OR.LedgerBalance_Tab)
		public WebElement LedgerBalance_Tab;
		
		@FindBy(xpath=OR.LedgerBalance_CheckIn)
		public WebElement LedgerBalance_CheckIn;
		
		@FindBy(xpath=OR.LedgerBalance_FromDate)
		public WebElement LedgerBalance_FromDate;
		
		@FindBy(xpath=OR.LedgerBalance_ToDate)
		public WebElement LedgerBalance_ToDate;
		
		@FindBy(xpath=OR.GoButton)
		public WebElement GoButton;
		
		@FindBy(xpath=OR.ActivDate)
		public WebElement ActivDate;
		
		@FindBy(xpath=OR.FirstOfTheMonth)
		public WebElement FirstOfTheMonth;
		
		@FindBy(xpath=OR.Incidentials_Checkbox)
		public WebElement Incidentials_Checkbox;
		
		@FindBy(xpath=OR.ShiftReport_RadioButton)
		public WebElement ShiftReport_RadioButton;
		
		@FindBy(xpath=OR.LagerBalacePage)
		public WebElement LagerBalacePage;
		
		@FindBy(xpath=OR.Detail_RadioButton)
		public WebElement Detail_RadioButton;
		
		@FindBy(xpath=OR.DepartmentReport_RadioButton)
		public WebElement DepartmentReport_RadioButton;
		
		@FindBy(xpath=OR.Summary_RadioButton)
		public WebElement Summary_RadioButton;
		
		@FindBy(xpath=OR.LedgerBalance_ItemStatus)
		public WebElement LedgerBalance_ItemStatus;
		
		@FindBy(xpath=OR.LedgerBalance_AccountType)
		public WebElement LedgerBalance_AccountType;
		
		@FindBy(xpath=OR.LedgerBalance_ReportType)
		public WebElement LedgerBalance_ReportType;
		
		@FindBy(xpath=OR.Select_PreMonth)
		public WebElement Select_PreMonth;
		
		@FindBy(css=OR.OldDate)
		public WebElement OldDate;
		
		@FindBy(css=OR.ActiveDate)
		public WebElement ActiveDate;
		
		@FindBy(xpath=OR.MerchantTrans_Tab)
		public WebElement MarchantTrans_Tab;
		
		@FindBy(xpath=OR.MerchantPage)
		public WebElement MarchantPage;
		
		@FindBy(xpath=OR.SelectMerchant_Date)
		public WebElement SelectMerchant_Date;
		
		@FindBy(xpath=OR.TranstionStatus)
		public WebElement TranstionStatus;
		
		@FindBy(xpath=OR.MerchantCapture_Checkbox)
		public WebElement MerchantCapture_Checkbox;
		@FindBy(xpath=OR.MerchantCancel_Checkbox)
		public WebElement MerchantCancel_Checkbox;
		
		@FindBy(xpath=OR.MerchantCredit_Checkbox)
		public WebElement MerchantCredit_Checkbox;
		
		@FindBy(xpath=OR.MerchantAuthorized_Checkbox)
		public WebElement MerchantAuthorized_Checkbox;
		
		@FindBy(xpath=OR.DailyFlash_Tab)
		public WebElement DailyFlash_Tab;
		
		@FindBy(xpath=OR.DailyFlashPage)
		public WebElement DailyFlashPage;
		
		@FindBy(xpath=OR.SelectDailyFlash_Date)
		public WebElement SelectDailyFlash_Date;
		
		@FindBy(xpath=OR.DailyFlash_SelectedDate)
		public WebElement DailyFlash_SelectedDate;
		
		@FindBy(xpath=OR.DailyFlash_RadioButton)
		public WebElement DailyFlash_RadioButton;
		
		@FindBy(xpath=OR.DailyFlachManagementTransfers_RadioButton)
		public WebElement DailyFlachManagementTransfers_RadioButton;
		
		@FindBy(xpath=OR.DailyFlashBreakoutTaxExemptRev_CheckBox)
		public WebElement DailyFlashBreakoutTaxExemptRev_CheckBox;
		
		@FindBy (xpath = OR.DailyFlash_ReportGenerated)
		public WebElement DailyFlash_GeneratedReport;
		
		@FindBy (xpath = OR.DailyFlash_ReportGenerated)
		public WebElement GeneratedReport;
		
		@FindBy (xpath = OR.NoDataAvailable_Message)
		public WebElement NoDataAvailable_Message;
		
		@FindBy(xpath=OR.RoomForecast_Tab)
		public WebElement RoomForecast_Tab;
		
		@FindBy(xpath=OR.RoomForecastPage)
		public WebElement RoomForecastPage;
		
		@FindBy(xpath=OR.SelectRoomForecast_FromDate)
		public WebElement SelectRoomForecast_FromDate;

		@FindBy(xpath=OR.SelectRoomForecast_ToDate)
		public WebElement SelectRoomForecast_ToDate;
		
		@FindBy(xpath=OR.RoomForecast_FromDate)
		public WebElement RoomForecast_FromDate;

		@FindBy(xpath=OR.RoomForecast_ToDate)
		public WebElement RoomForecast_ToDate;
		
		@FindBy (xpath = OR.RoomForecast_GeneratedReport)
		public WebElement RoomForecast_GeneratedReport;
		
		@FindBy(id=OR.NetSales_GroupBy)
		public WebElement NetSales_GroupBy;
		
		@FindBy(id=OR.NetSales_ClientType)
		public WebElement NetSales_ClientType;
		
		@FindBy(xpath=OR.NetSales_FromDate)
		public WebElement NetSales_FromDate;
		
		@FindBy(xpath=OR.NetSales_ToDate)
		public WebElement NetSales_ToDate;
		
		@FindBy (xpath = OR.Today_Day)
		public WebElement Today_Day;
		
		@FindBy(id=OR.NetSales_GoButton)
		public WebElement NetSales_GoButton;
		
		@FindBy(id=OR.NetSales_GenratedReport_GroupByRoom)
		public WebElement NetSales_GenratedReport_GroupByRoom;
		
		@FindBy (xpath = OR.Select_FromDate)
		public WebElement Select_FromDate;
		
		@FindBy (xpath = OR.Select_ToDate)
		public WebElement Select_ToDate;
		
		@FindBy(xpath=OR.ADSummary_RadioButton)
		public WebElement ADSummary_RadioButton;
		
		@FindBy(xpath=OR.ADDetail_RadioButton)
		public WebElement ADDetail_RadioButton;
		
		@FindBy(xpath=OR.ReportType_Inbound)
		public WebElement ReportType_Inbound;

		@FindBy(xpath=OR.ReportType_Outbound)
		public WebElement ReportType_Outbound;
		
		@FindBy(xpath=OR.ReportType_Net)
		public WebElement ReportType_Net;
		
		@FindBy(xpath=OR.GoButton)
		public WebElement AdvanceDeposite_GoButton;
				
		
		@FindBy(xpath=OR.Reports_DailyFlash)
		public WebElement Reports_DailyFlash;
		
		@FindBy(xpath=OR.Repoprts_DailyFlash_Calender)
		public WebElement Repoprts_DailyFlash_Calender;
		
		@FindBy(xpath=OR.Repoprts_DailyFlash_Calender_Right)
		public WebElement Repoprts_DailyFlash_Calender_Right;
		
		@FindBy(xpath=OR.Repoprts_DailyFlash_Go)
		public WebElement Repoprts_DailyFlash_Go;
		
		
		@FindBy(xpath=OR.NetSales_Stayon_FromDate)
		public WebElement NetSales_Stayon_FromDate;
		
		@FindBy(xpath=OR.NetSales_Stayon_ToDate)
		public WebElement NetSales_Stayon_ToDate;
		
		@FindBy(xpath=OR.LEDGERBALANCE_MARKETSEGMENT)
		public WebElement LEDGERBALANCE_MARKETSEGMENT;
		
		@FindBy(xpath=OR.ReportsDailyFlash)
		public WebElement ReportsDailyFlash;

		@FindBy(xpath=OR.DailyFlashRadioButton)
		public WebElement DailyFlashRadioButton;
		
		@FindBy(xpath=OR.DailyFlashBreakoutTaxExemptRevCheckBox)
		public WebElement DailyFlashBreakoutTaxExemptRevCheckBox;
		

		@FindBy(xpath=OR_Reports.ReportFrame)
		public WebElement ReportFrame;
		
		@FindBy(xpath=OR_Reports.ReportType)
		public WebElement ReportType;
		
		@FindBy(id=OR_Reports.ReportDisplay)
		public WebElement ReportDisplay;

		@FindBy(xpath=OR_Reports.ViewPDF)
		public WebElement ViewPDF;
		
		@FindBy(xpath=OR.NetSalesFromDate)
		public WebElement NetSalesFromDate;
		
		@FindBy(xpath=OR.NetSalesToDate)
		public WebElement NetSalesToDate;
		
		@FindBy(xpath = OR_Reports.NetSalesFromCalenderHeading)
		public WebElement NetSalesFromCalenderHeading;
		
		@FindBy(xpath= OR_Reports.NetSalesStayonFromDate)
		public WebElement NetSalesStayonFromDate;
		
		@FindBy(xpath = OR_Reports.NetSalesFromCalenderNext)
		public WebElement NetSalesFromCalenderNext;
		
		@FindBy(xpath=OR_Reports.NetSalesStayonToDate)
		public WebElement NetSalesStayonToDate;
		
		@FindBy(id=OR.NetSalesGroupBy)
		public WebElement NetSalesGroupBy;
		
		//Reports V2
		@FindBy(xpath = OR_Reports.linkReservations)
		public WebElement linkReservations;
		
		@FindBy(xpath = OR_Reports.linkAccounts)
		public WebElement linkAccounts;
		
		@FindBy(xpath = OR_Reports.linkGuestServices)
		public WebElement linkGuestServices;
		
		@FindBy(xpath = OR_Reports.linkInventory)
		public WebElement linkInventory;
		
		@FindBy(xpath = OR_Reports.linkSetup)
		public WebElement linkSetup;
		
		@FindBy(xpath = OR_Reports.linkAdmin)
		public WebElement linkAdmin;
		
		@FindBy(xpath = OR_Reports.linkNightAudit)
		public WebElement linkNightAudit;
		
		@FindBy(xpath = OR_Reports.Reports2)
		public WebElement linkReports;
		
		@FindBy(xpath = OR_Reports.SearchReports)
		public WebElement SearchReports;
		
		
		//Naresh
		//Reports V2 - Ledger Balances eport
		@FindBy(xpath = OR_Reports.txtSelectInputs)
		public WebElement txtSelectInputs;
		
		@FindBy(xpath = OR_Reports.txtIncludeLedgerAccount)
		public WebElement txtIncludeLedgerAccount;
		
		@FindBy(xpath = OR_Reports.infoIncludeLedgerAccount)
		public WebElement infoIncludeLedgerAccount;
		
		@FindBy(xpath = OR_Reports.tooltip)
		public WebElement tooltip;
		
		@FindBy(xpath = OR_Reports.buttonClearAll)
		public WebElement buttonClearAll;
		
		@FindBy(xpath = OR_Reports.buttonSeeAll)
		public WebElement buttonSeeAll;
		
		@FindBy(xpath = OR_Reports.selectIncidentals)
		public WebElement selectIncidentals;
		
		@FindBy(xpath = OR_Reports.selectCountIncidentals)
		public WebElement selectCountIncidentals;
		
		@FindBy(xpath = OR_Reports.checkboxIncidentals)
		public WebElement checkboxIncidentals;
		
		@FindBy(xpath = OR_Reports.checkboxTaxes)
		public WebElement checkboxTaxes;
		
		@FindBy(xpath = OR_Reports.selectCountRoomCharges)
		public WebElement selectCountRoomCharges;
		
		@FindBy(xpath = OR_Reports.selectCountTaxes)
		public WebElement selectCountTaxes;
		
		@FindBy(xpath = OR_Reports.selectCountTransfers)
		public WebElement selectCountTransfers;
		
		@FindBy(xpath = OR_Reports.selectCountPaymentMethod)
		public WebElement selectCountPaymentMethod;
		
		@FindBy(xpath = OR_Reports.selectCountFees)
		public WebElement selectCountFees;
		
		@FindBy(xpath = OR_Reports.selectCountOther)
		public WebElement selectCountOther;
		
		@FindBy(xpath = OR_Reports.popupMessage)
		public WebElement popupMessage;
		
		@FindBy(xpath = OR_Reports.buttonClosePopup)
		public WebElement buttonClosePopup;
		
		@FindBy(xpath = OR_Reports.buttonSavePopup)
		public WebElement buttonSavePopup;
		
		@FindBy(xpath = OR_Reports.buttonCancelPopup)
		public WebElement buttonCancelPopup;
		
		@FindBy(xpath = OR_Reports.inputAllCategories)
		public WebElement inputAllCategories;
		
		@FindBy(xpath = OR_Reports.inputIncidentals)
		public WebElement inputIncidentals;
		
		@FindBy(xpath = OR_Reports.inputPaymentMethod)
		public WebElement inputPaymentMethod;
		
		@FindBy(xpath = OR_Reports.inputTaxes)
		public WebElement inputTaxes;
		
		@FindBy(xpath = OR_Reports.inputRoomCharges)
		public WebElement inputRoomCharges;
		
		@FindBy(xpath = OR_Reports.inputFees)
		public WebElement inputFees;
		
		@FindBy(xpath = OR_Reports.inputTransfers)
		public WebElement inputTransfers;
		
		@FindBy(xpath = OR_Reports.inputOther)
		public WebElement inputOther;
		
		@FindBy(xpath = OR_Reports.clickSelectAll)
		public WebElement clickSelectAll;
		
		@FindBy(xpath = OR_Reports.clickRemoveAll)
		public WebElement clickRemoveAll;
		
		@FindBy(xpath = OR_Reports.txtExcludeZeroBalanceLedgerAccounts)
		public WebElement txtExcludeZeroBalanceLedgerAccounts;
		
		@FindBy(xpath = OR_Reports.tooltipExcludeZeroBalanceLedgerAccounts)
		public WebElement tooltipExcludeZeroBalanceLedgerAccounts;
		
		@FindBy(xpath = OR_Reports.rdoYesDisplayCustomGeneral)
		public WebElement rdoYesDisplayCustomGeneral;
		
		@FindBy(xpath = OR_Reports.rdoNoDisplayCustomGeneral)
		public WebElement rdoNoDisplayCustomGeneral;
		
		@FindBy(xpath = OR_Reports.txtDisplayCustomGeneralLedgerAccount)
		public WebElement txtDisplayCustomGeneralLedgerAccount;
		
		@FindBy(xpath = OR_Reports.tooltipDisplayCustomGeneralLedgerAccount)
		public WebElement tooltipDisplayCustomGeneralLedgerAccount;
		
		@FindBy(xpath = OR_Reports.rdoYesExcludeZero)
		public WebElement rdoYesExcludeZero;
		
		@FindBy(xpath = OR_Reports.rdoNoExcludeZero)
		public WebElement rdoNoExcludeZero;
		
		@FindBy(xpath = OR_Reports.searchAvailable)
		public WebElement searchAvailable;
		
		@FindBy(xpath = OR_Reports.searchSelected)
		public WebElement searchSelected;
		
	    @FindBy(xpath = OR_Reports.IncludedLedgerAccounts)
	    public WebElement IncludedLedgerAccounts;
	   
	    @FindBy(xpath = OR_Reports.ExcludeZeroBalanceLedgerAccounts)
	    public WebElement ExcludeZeroBalanceLedgerAccounts;
	   
	    @FindBy(xpath = OR_Reports.DisplayCustomGeneralLedgerAccount)
	    public WebElement DisplayCustomGeneralLedgerAccount;
	   
	    @FindBy(xpath = OR_Reports.IncludedLedgerAccountsToolTipIcon)
	    public WebElement IncludedLedgerAccountsToolTipIcon;
	   
	    @FindBy(xpath = OR_Reports.ExcludeZeroBalanceLedgerAccountsToolTipIcon)
	    public WebElement ExcludeZeroBalanceLedgerAccountsToolTipIcon;
	   
	    @FindBy(xpath = OR_Reports.DisplayCustomGeneralLedgerAccountToolTipIcon)
	    public WebElement DisplayCustomGeneralLedgerAccountToolTipIcon;
		
		
		
		// Reports-v2

		@FindBy(xpath = OR_Reports.Help)
		public WebElement Help;

		@FindBy(xpath = OR_Reports.PerformanceFilter)
		public WebElement PerformanceFilter;

		@FindBy(xpath = OR_Reports.AccountingFilter)
		public WebElement AccountingFilter;

		@FindBy(xpath = OR_Reports.PerformanceReportsHeading)
		public WebElement PerformanceReportsHeading;

		@FindBy(xpath = OR_Reports.AccountingReportsHeading)
		public WebElement AccountingReportsHeading;

		@FindBy(xpath = OR_Reports.DailyFlashReport)
		public WebElement DailyFlashReport;

		@FindBy(xpath = OR_Reports.NetSalesReport)
		public WebElement NetSalesReport;

		@FindBy(xpath = OR_Reports.RoomForecastReport)
		public WebElement RoomForecastReport;

		@FindBy(xpath = OR_Reports.AdvanceDepositReport)
		public WebElement AdvanceDepositReport;

		@FindBy(xpath = OR_Reports.AccountBalancesReport)
		public WebElement AccountBalancesReport;

		@FindBy(xpath = OR_Reports.LedgerBalancesReport)
		public WebElement LedgerBalancesReport;

		@FindBy(xpath = OR_Reports.Incidentals)
		public WebElement Incidentals;

		@FindBy(xpath = OR_Reports.PaymentMethod)
		public WebElement PaymentMethod;

		@FindBy(xpath = OR_Reports.Taxes)
		public WebElement Taxes;

		@FindBy(xpath = OR_Reports.TransactionsReport)
		public WebElement TransactionsReport;

		@FindBy(xpath = OR_Reports.DailyFlashHelp)
		public WebElement DailyFlashHelp;

		@FindBy(xpath = OR_Reports.NetSalesHelp)
		public WebElement NetSalesHelp;

		@FindBy(xpath = OR_Reports.RoomForecastHelp)
		public WebElement RoomForecastHelp;

		@FindBy(xpath = OR_Reports.AdvanceDepositHelp)
		public WebElement AdvanceDepositHelp;

		@FindBy(xpath = OR_Reports.AccountBalancesHelp)
		public WebElement AccountBalancesHelp;

		@FindBy(xpath = OR_Reports.LedgerBalancesHelp)
		public WebElement LedgerBalancesHelp;

		@FindBy(xpath = OR_Reports.TransactionsHelp)
		public WebElement TransactionsHelp;

		@FindBy(xpath = OR_Reports.ReportsHelpPageHeader)
		public WebElement ReportsHelpPageHeader;

		@FindBy(xpath = OR_Reports.DailyFlashReportHeader)
		public WebElement DailyFlashReportHeader;

		@FindBy(xpath = OR_Reports.NetSalesReportHeader)
		public WebElement NetSalesReportHeader;

		@FindBy(xpath = OR_Reports.RoomForecastReportHeader)
		public WebElement RoomForecastReportHeader;

		@FindBy(xpath = OR_Reports.AdvanceDepositReportHeader)
		public WebElement AdvanceDepositReportHeader;

		@FindBy(xpath = OR_Reports.AccountBalancesReportHeader)
		public WebElement AccountBalancesReportHeader;

		@FindBy(xpath = OR_Reports.TransactionsReportHeader)
		public WebElement TransactionsReportHeader;

		@FindBy(xpath = OR_Reports.LedgerBalancesReportHeader)
		public WebElement LedgerBalancesReportHeader;

		@FindBy(xpath = OR_Reports.InnRoadImage)
		public WebElement InnRoadImage;

		@FindBy(xpath = OR_Reports.LedgerBalancesReportMainHeader)
		public WebElement LedgerBalancesReportMainHeader;

		@FindBy(xpath = OR_Reports.ExcelExport)
		public WebElement ExcelExport;

		@FindBy(xpath = OR_Reports.PDFExport)
		public WebElement PDFExport;

		@FindBy(xpath = OR_Reports.Print)
		public WebElement Print;

		@FindBy(xpath = OR_Reports.RunReport)
		public WebElement RunReport;
		
		@FindBy(xpath = OR_Reports.NoReportDataMessage)
		public WebElement NoReportDataMessage;

		@FindBy(xpath = OR_Reports.Collapse)
		public WebElement Collapse;

		@FindBy(xpath = OR_Reports.Edit)
		public WebElement Edit;

		@FindBy(xpath = OR_Reports.ChoseDateRange)
		public WebElement ChoseDateRange;

		@FindBy(xpath = OR_Reports.SelectInputs)
		public WebElement SelectInputs;

		@FindBy(xpath = OR_Reports.CustomizeDetailedView)
		public WebElement CustomizeDetailedView;

		@FindBy(xpath = OR_Reports.ExcelExportToolTipBeforeRunReport)
		public WebElement ExcelExportToolTipBeforeRunReport;

		@FindBy(xpath = OR_Reports.PDFExportToolTipBeforeRunReport)
		public WebElement PDFExportToolTipBeforeRunReport;

		@FindBy(xpath = OR_Reports.PrintToolTipBeforeRunReport)
		public WebElement PrintToolTipBeforeRunReport;

		@FindBy(xpath = OR_Reports.ExcelExportToolTipAfterRunReport)
		public WebElement ExcelExportToolTipAfterRunReport;

		@FindBy(xpath = OR_Reports.PDFExportToolTipAfterRunReport)
		public WebElement PDFExportToolTipAfterRunReport;

		@FindBy(xpath = OR_Reports.PrintToolTipAfterRunReport)
		public WebElement PrintToolTipAfterRunReport;
		
		@FindBy(xpath = OR_Reports.SortReportBy)
		public WebElement SortReportBy;

		@FindBy(xpath = OR_Reports.GroupRowsByToolTip)
		public WebElement GroupRowsByToolTip;
		
		@FindBy(xpath = OR_Reports.SortReportByOptionsExpand)
		public WebElement SortReportByOptionsExpand;
		
		@FindBy(xpath = OR_Reports.GroupRowsByOptionsExpand)
		public WebElement GroupRowsByOptionsExpand;
		
		@FindBy(xpath = OR_Reports.Reports)
		public WebElement Reports;
		
		@FindBy(xpath = OR.ReportsIcon)
		public WebElement ReportsIcon;
		
		@FindBy(xpath = OR_Reports.BrowseAllReports)
		public WebElement BrowseAllReports;
		
		@FindBy(xpath = OR_Reports.AdvancedInputs)
		public WebElement AdvancedInputs;
		
		@FindBy(xpath = OR_Reports.ExpandAll)
		public WebElement ExpandAll;
		
		@FindBy(xpath = OR_Reports.CollapseAll)
		public WebElement CollapseAll;
		
		@FindBy(xpath = OR_Reports.AccountType)
		public WebElement AccountType;
		
		@FindBy(xpath = OR_Reports.ItemStatus)
		public WebElement ItemStatus;
		
		@FindBy(xpath = OR_Reports.IncludeDataFrom)
		public WebElement IncludeDataFrom;
		
		@FindBy(xpath = OR_Reports.TaxExemptLedgerItems)
		public WebElement TaxExemptLedgerItems;
		
		@FindBy(xpath = OR_Reports.MarketSegment)
		public WebElement MarketSegment;
		
		@FindBy(xpath = OR_Reports.ReservationStatus)
		public WebElement ReservationStatus;
		
		@FindBy(xpath = OR_Reports.Referrals)
		public WebElement Referrals;
		
		@FindBy(xpath = OR_Reports.AdvancedOptionsToolTipIcon)
		public WebElement AdvancedOptionsToolTipIcon;
		
		@FindBy(xpath = OR_Reports.AccountTypeToolTipIcon)
		public WebElement AccountTypeToolTipIcon;
		
		@FindBy(xpath = OR_Reports.ItemStatusToolTipIcon)
		public WebElement ItemStatusToolTipIcon;
		
		@FindBy(xpath = OR_Reports.IncludeDataFromToolTipIcon)
		public WebElement IncludeDataFromToolTipIcon;
		
		@FindBy(xpath = OR_Reports.TaxExemptLedgerItemsToolTipIcon)
		public WebElement TaxExemptLedgerItemsToolTipIcon;
		
		@FindBy(xpath = OR_Reports.MarketSegmentToolTipIcon)
		public WebElement MarketSegmentToolTipIcon;
		
		@FindBy(xpath = OR_Reports.ReservationStatusToolTipIcon)
		public WebElement ReservationStatusToolTipIcon;
		
		@FindBy(xpath = OR_Reports.ReferralsToolTipIcon)
		public WebElement ReferralsToolTipIcon;
		
		@FindBy(xpath = OR_Reports.AccountTypeClearAll)
		public WebElement AccountTypeClearAll;
		
		@FindBy(xpath = OR_Reports.ItemStatusClearAll)
		public WebElement ItemStatusClearAll;
		
		@FindBy(xpath = OR_Reports.ReservationStatusClearAll)
		public WebElement ReservationStatusClearAll;
		
		@FindBy(xpath = OR_Reports.AccountTypeSelectAll)
		public WebElement AccountTypeSelectAll;
		
		@FindBy(xpath = OR_Reports.ItemStatusSelectAll)
		public WebElement ItemStatusSelectAll;
		
		@FindBy(xpath = OR_Reports.ReservationStatusSelectAll)
		public WebElement ReservationStatusSelectAll;
		
		@FindBy(xpath = OR_Reports.AccountTypesOptions)
		public WebElement AccountTypesOptions;
		
		@FindBy(xpath = OR_Reports.ItemStatusOptions)
		public WebElement ItemStatusOptions;
		
		@FindBy(xpath = OR_Reports.ReservationStatusOptions)
		public WebElement ReservationStatusOptions;
		
		@FindBy(xpath = OR_Reports.AdvancedDeposits)
		public WebElement AdvancedDeposits;
		
		@FindBy(xpath = OR_Reports.Pending)
		public WebElement Pending;
		
		@FindBy(xpath = OR_Reports.Users)
		public WebElement Users;
		
		@FindBy(xpath = OR_Reports.Properties)
		public WebElement Properties;
		
		@FindBy(xpath = OR_Reports.ShiftTime)
		public WebElement ShiftTime;
		
		@FindBy(xpath = OR_Reports.TaxExempt)
		public WebElement TaxExempt;
		
		@FindBy(xpath = OR_Reports.MarktSegmnt)
		public WebElement MarktSegmnt;
		
		@FindBy(xpath = OR_Reports.OnHold)
		public WebElement OnHold;
		
		@FindBy(xpath = OR_Reports.referrals)
		public WebElement referrals;
		
		@FindBy(xpath = OR_Reports.LedgerBalancesSubTitle)
		public WebElement LedgerBalancesSubTitle;
		
		@FindBy(xpath = OR_Reports.TaxExemptListExpand)
		public WebElement TaxExemptListExpand;
		
		@FindBy(xpath = OR_Reports.MarketSegmentListExpand)
		public WebElement MarketSegmentListExpand;
		
		@FindBy(xpath = OR_Reports.ReferralstListExpand)
		public WebElement ReferralstListExpand;
		
		
		//Shift Time - Include Data Form
		@FindBy(xpath = OR_Reports.usersExpand)
		public WebElement usersExpand;
		
		@FindBy(xpath = OR_Reports.buttonShiftTime)
		public WebElement buttonShiftTime;
		
		@FindBy(xpath = OR_Reports.inputStartShiftTime)
		public WebElement inputStartShiftTime;
		
		@FindBy(xpath = OR_Reports.inputEndShiftTime)
		public WebElement inputEndShiftTime;
		
		@FindBy(xpath = OR_Reports.buttonNowShiftTimeStart)
		public WebElement buttonNowShiftTimeStart;
		
		@FindBy(xpath = OR_Reports.buttonNowShiftTimeEnd)
		public WebElement buttonNowShiftTimeEnd;
		
		@FindBy(xpath = OR_Reports.buttonOkShiftTimeStart)
		public WebElement buttonOkShiftTimeStart;
		
		@FindBy(xpath = OR_Reports.buttonOkShiftTimeEnd)
		public WebElement buttonOkShiftTimeEnd;
		
		
		
		//ReportsV2 - Ledger Balances - Date Range
		@FindBy(xpath = OR_Reports.dateDropDown)
		public WebElement dateDropDown;
		
		@FindBy(xpath = OR_Reports.dateStart)
		public WebElement dateStart;
		
		@FindBy(xpath = OR_Reports.dateEnd)
		public WebElement dateEnd;
		
		@FindBy(xpath = OR_Reports.dateCustomRange)
		public WebElement dateCustomRange;

		@FindBy(xpath = OR_Reports.dateTab)
		public WebElement dateTab;
		
		@FindBy(xpath = OR_Reports.daySelected)
		public WebElement daySelected;
		
		@FindBy(xpath = OR_Reports.buttonYear)
		public WebElement buttonYear;
		
		@FindBy(xpath = OR_Reports.buttonMonth)
		public WebElement buttonMonth;
		
		//Return to Default
		@FindBy(xpath = OR_Reports.buttonReturnToDefault)
		public WebElement buttonReturnToDefault;

		@FindBy(xpath = OR_Reports.symbolReturnToDefault)
		public WebElement symbolReturnToDefault;
		
		@FindBy(xpath = OR_Reports.ReturnToDefaultSeccess)
		public WebElement ReturnToDefaultSeccess;
		
		@FindBy(xpath = OR_Reports.ReturnToDefaultFailure)
		public WebElement ReturnToDefaultFailure;
		
		@FindBy(xpath = OR_Reports.ReturnToDefaultDescription)
		public WebElement ReturnToDefaultDescription;
		
		//Ledger Run Report
		
		@FindBy(xpath = OR_Reports.GRHeaderTitle)
		public WebElement GRHeaderTitle;
		
		@FindBy(xpath = OR_Reports.GRReportTypeCaption)
		public WebElement GRReportTypeCaption;
		
		@FindBy(xpath = OR_Reports.GRReportTypeValue)
		public WebElement GRReportTypeValue;
		
		@FindBy(xpath = OR_Reports.GRDateRangeCaption)
		public WebElement GRDateRangeCaption;
		
		@FindBy(xpath = OR_Reports.GRDateRangeValue)
		public WebElement GRDateRangeValue;
		
		@FindBy(xpath = OR_Reports.GRIncludedLedgerAccountsCaption)
		public WebElement GRIncludedLedgerAccountsCaption;
		
		@FindBy(xpath = OR_Reports.GRIncidentalsCaption)
		public WebElement GRIncidentalsCaption;
		
		@FindBy(xpath = OR_Reports.GRIncidentalsValue)
		public WebElement GRIncidentalsValue;
		
		@FindBy(xpath = OR_Reports.GRRoomChargesCaption)
		public WebElement GRRoomChargesCaption;
		
		@FindBy(xpath = OR_Reports.GRRoomChargesValue)
		public WebElement GRRoomChargesValue;
		
		@FindBy(xpath = OR_Reports.GRTaxesCaption)
		public WebElement GRTaxesCaption;
		
		@FindBy(xpath = OR_Reports.GRTaxesValue)
		public WebElement GRTaxesValue;
		
		@FindBy(xpath = OR_Reports.GRTransfersCaption)
		public WebElement GRTransfersCaption;
		
		@FindBy(xpath = OR_Reports.GRTransfersValue)
		public WebElement GRTransfersValue;
		
		@FindBy(xpath = OR_Reports.GRPaymentMethodCaption)
		public WebElement GRPaymentMethodCaption;
		
		@FindBy(xpath = OR_Reports.GRPaymentMethodValue)
		public WebElement GRPaymentMethodValue;
		
		@FindBy(xpath = OR_Reports.GRTravelAgentCommissionCaption)
		public WebElement GRTravelAgentCommissionCaption;
		
		@FindBy(xpath = OR_Reports.GRAgentCommissionValue)
		public WebElement GRAgentCommissionValue;
		
		@FindBy(xpath = OR_Reports.GRDistributionMethodCaption)
		public WebElement GRDistributionMethodCaption;
		
		@FindBy(xpath = OR_Reports.GRDistributionMethodValue)
		public WebElement GRDistributionMethodValue;
		
		@FindBy(xpath = OR_Reports.GRGiftCertificateCaption)
		public WebElement GRGiftCertificateCaption;
		
		@FindBy(xpath = OR_Reports.GRGiftCertificateValue)
		public WebElement GRGiftCertificateValue;
		
		@FindBy(xpath = OR_Reports.GRGiftCertificateRedeemedCaption)
		public WebElement GRGiftCertificateRedeemedCaption;
		
		@FindBy(xpath = OR_Reports.GRGiftCertificateRedeemedValue)
		public WebElement GRGiftCertificateRedeemedValue;
		
		@FindBy(xpath = OR_Reports.GRReservationStatusCaption)
		public WebElement GRReservationStatusCaption;
		
		@FindBy(xpath = OR_Reports.GRReservationStatusValue)
		public WebElement GRReservationStatusValue;
		
		@FindBy(xpath = OR_Reports.GRAccountTypeCaption)
		public WebElement GRAccountTypeCaption;
		
		@FindBy(xpath = OR_Reports.GRAccountTypeValue)
		public WebElement GRAccountTypeValue;
		
		@FindBy(xpath = OR_Reports.GRIncludeDataFromCaption)
		public WebElement GRIncludeDataFromCaption;
		
		@FindBy(xpath = OR_Reports.GRIncludeDataFromValue)
		public WebElement GRIncludeDataFromValue;
		
		@FindBy(xpath = OR_Reports.GRGeneratedOnCaption)
		public WebElement GRGeneratedOnCaption;
		
		@FindBy(xpath = OR_Reports.GRGeneratedOnValue)
		public WebElement GRGeneratedOnValue;
		
		@FindBy(xpath = OR_Reports.GRSortReportByCaption)
		public WebElement GRSortReportByCaption;
		
		@FindBy(xpath = OR_Reports.GRSortReportByValue)
		public WebElement GRSortReportByValue;
		
		@FindBy(xpath = OR_Reports.GRGroupRowsByCaption)
		public WebElement GRGroupRowsByCaption;
		
		@FindBy(xpath = OR_Reports.GRGroupRowsByValue)
		public WebElement GRGroupRowsByValue;

		@FindBy(xpath = OR_Reports.GRItemStatusCaption)
		public WebElement GRItemStatusCaption;
		
		@FindBy(xpath = OR_Reports.GRItemStatusValue)
		public WebElement GRItemStatusValue;
		
		@FindBy(xpath = OR_Reports.GRTaxExemptCaption)
		public WebElement GRTaxExemptCaption;
		
		@FindBy(xpath = OR_Reports.GRTaxExemptValue)
		public WebElement GRTaxExemptValue;
		
		@FindBy(xpath = OR_Reports.GRMarketSegmentCaption)
		public WebElement GRMarketSegmentCaption;
		
		@FindBy(xpath = OR_Reports.GRMarketSegmentValue)
		public WebElement GRMarketSegmentValue;
		
		@FindBy(xpath = OR_Reports.GRReferralsCaption)
		public WebElement GRReferralsCaption;
		
		@FindBy(xpath = OR_Reports.GRReferralsValue)
		public WebElement GRReferralsValue;
		
		@FindBy(xpath = OR_Reports.GRExcludeZeroBalanceLedgerAccountsCaption)
		public WebElement GRExcludeZeroBalanceLedgerAccountsCaption;
		
		@FindBy(xpath = OR_Reports.GRExcludeZeroBalanceLedgerAccountsValue)
		public WebElement GRExcludeZeroBalanceLedgerAccountsValue;
		
		@FindBy(xpath = OR_Reports.GRSummaryViewHeader)
		public WebElement GRSummaryViewHeader;

		@FindBy(xpath = OR_Reports.tableLedgerAccountCategory)
		public WebElement tableLedgerAccountCategory;
		
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategory)
		public WebElement GRLedgerAccountCategory;
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategoryBalance)
		public WebElement GRLedgerAccountCategoryBalance;
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategoryToolTipIcon)
		public WebElement GRLedgerAccountCategoryToolTipIcon;
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategoryToolTipHeader)
		public WebElement GRLedgerAccountCategoryToolTipHeader;
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategoryToolTipContent)
		public WebElement GRLedgerAccountCategoryToolTipContent;
		
		@FindBy(xpath = OR_Reports.GRLedgerAccountCategoryTotalCaption)
		public WebElement GRLedgerAccountCategoryTotalCaption;
		
		@FindBy(xpath = OR_Reports.ScrollToTop)
		public WebElement ScrollToTop;
		
		@FindBy(xpath = OR_Reports.FilterHeaderDateRangeCaption)
		public WebElement FilterHeaderDateRangeCaption;
		
		@FindBy(xpath = OR_Reports.FilterHeaderIncludedLedgerAccountsCaption)
		public WebElement FilterHeaderIncludedLedgerAccountsCaption;
		
		@FindBy(xpath = OR_Reports.FilterHeaderTaxExemptCaption)
		public WebElement FilterHeaderTaxExemptCaption;
		
		@FindBy(xpath = OR_Reports.FilterHeaderSortReportByCaption)
		public WebElement FilterHeaderSortReportByCaption;
		
		@FindBy(xpath = OR_Reports.FilterHeaderGroupByCaption)
		public WebElement FilterHeaderGroupByCaption;
		
		@FindBy(xpath = OR_Reports.FilterHeaderDateRangeValue)
		public WebElement FilterHeaderDateRangeValue;
		
		@FindBy(xpath = OR_Reports.FilterHeaderIncludedLedgerAccountsValue)
		public WebElement FilterHeaderIncludedLedgerAccountsValue;
		
		@FindBy(xpath = OR_Reports.FilterHeaderTaxExemptValue)
		public WebElement FilterHeaderTaxExemptValue;
		
		@FindBy(xpath = OR_Reports.FilterHeaderSortReportByValue)
		public WebElement FilterHeaderSortReportByValue;
		
		@FindBy(xpath = OR_Reports.FilterHeaderGroupByValue)
		public WebElement FilterHeaderGroupByValue;
		
		@FindBy(xpath = OR_Reports.NoReportDataAvailableMessageHeading)
		public WebElement NoReportDataAvailableMessageHeading;
		
		@FindBy(xpath = OR_Reports.NoReportDataAvailableMessageContent)
		public WebElement NoReportDataAvailableMessageContent;
		
		@FindBy(xpath = OR_Reports.LoadingReportMessage)
		public WebElement LoadingReportMessage;
		
		@FindBy(xpath = OR_Reports.LoadingReportCancelButton)
		public WebElement LoadingReportCancelButton;


}
