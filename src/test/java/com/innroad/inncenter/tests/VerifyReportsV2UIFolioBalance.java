package com.innroad.inncenter.tests;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyReportsV2UIFolioBalance extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ReportsV2 report= new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
	CPReservationPage reservationPage = new CPReservationPage();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	Tapechart tc = new Tapechart();
	Admin admin = new Admin();
	NewRoomClassesV2 rc2 = new NewRoomClassesV2();
	RoomClass rc = new RoomClass();
	RoomMaintenance rm = new RoomMaintenance();
	RatesGrid ratesGrid = new RatesGrid();	
	
	String  currency = null, clientTimeZone, dFormat, propertyName ;
	
	
//	@BeforeTest(alwaysRun = true)
//	public void checkRunMode() {
//		String testName = this.getClass().getSimpleName().trim();
//		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
//		if (!Utility.isExecutable(testName, excel))
//			throw new SkipException("Skipping the test - " + testName);
//	}
	
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginReportsV2ReservationV2(driver);		
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.equalsIgnoreCase("USD ( $ ) ")) {
			TestCore.propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			TestCore.propertyCurrency = "£";
		}			
	
		TestCore.propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);
		
		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Eastern";
			break;			
		case "(GMT-06:00) Central Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Central";
			break;		
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			TestCore.propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}
		
		if (dFormat.equalsIgnoreCase("USA")) {
			TestCore.propertyDateFormat = "MMM dd, YYYY";
		}else if (dFormat.equalsIgnoreCase("International")) {
			TestCore.propertyDateFormat = "dd MMM, YYYY";
		}
	}

	
	@Test(priority=1)
	public void reportUIValidation() throws InterruptedException, IOException {
		test_name = "UI Validation Of Report";
		test_description = "Validate LedgerBalances Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Ledger Balances Report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		try {
			
			String getCurrentDate = Utility.getCurrentDate("MMM dd, YYYY, EEEE", TestCore.propertyTimeZone).trim();

		driver = getDriver();
		loginReportsV2ReservationV2(driver);
		test_steps.add("Logged into the application");
		nav.ReportsV2(driver);
		test_steps.add("Click on Reports");
		report.clickOnNavigateFolioBalances(driver,test_steps);
		test_steps.add("Click on Folio Balances Report");
		Wait.wait10Second();
		/************************************Section Right*****************************************/
		report.iconRightIsDiplaying(driver, test_steps);
		report.AdvancedInputsTextIsDiplaying(driver, test_steps);
		report.AdvancedInputsIconIsDiplaying(driver, test_steps);
		report.expandAllButtonIsDiplaying(driver, test_steps);
		report.displayAccountCompanyNameIsDiplaying(driver, test_steps);
		report.displayAccountIconIsDiplaying(driver, test_steps);
		report.displayAccountTextIsDiplaying(driver, test_steps);
		report.clickdisplayAccountIcon(driver, test_steps);
		Wait.wait10Second();
		report.displayAccountNoButtonIsDiplaying(driver, test_steps);
		report.displayAccountNoButtonClickable(driver, test_steps);
		report.displayAccountYesButtonIsDiplaying(driver, test_steps);
		report.displayAccountYesButtonClickable(driver, test_steps);
		report.clickDisplayAccountYesButton(driver, test_steps);
		report.clickDisplayAccountNoButton(driver, test_steps);
		report.clickdisplayAccountIcon(driver, test_steps);
		report.includePendingFolioItemsIsDiplaying(driver, test_steps);
		report.includePendingNoTextIsDiplaying(driver, test_steps);
		report.includePendingCollapseExpandIconIsDiplaying(driver, test_steps);
		report.clickincludePendingIcon(driver, test_steps);
		Wait.wait10Second();
		report.includePendingNoButtonIsDiplaying(driver, test_steps);
		report.includePendingNoButtonClickable(driver, test_steps);
		report.includePendingYesButtonIsDiplaying(driver, test_steps);
		report.includePendingYesButtonClickable(driver, test_steps);
		report.clickIncludePendingYesButton(driver, test_steps);
		report.clickIncludePendingNoButton(driver, test_steps);
		report.clickincludePendingIcon(driver, test_steps);
		report.includeAuthorizationIsDiplaying(driver, test_steps);
		report.includeAuthorizationTextIsDiplaying(driver, test_steps);
		report.includeAuthorizationExpandIconIsDiplaying(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		Wait.wait10Second();
		report.includeAuthorizationNoButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationNoButtonClickable(driver, test_steps);
		report.includeAuthorizationYesButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationYesButtonClickable(driver, test_steps);
		report.clickIncludeAuthorizationYesButton(driver, test_steps);
		report.clickIncludeAuthorizationNoButton(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		/************************************Section 1********************************************/
		report.folioBalancesReportIsDiplaying(driver,test_steps);
		report.allreceivablepayableIsDiplaying(driver,test_steps);
		report.returnToDefaultTextIsDiplaying(driver,test_steps);
		report.returnToDefaultButtonIsDiplaying(driver,test_steps);
		report.collapseButtonIsDiplaying(driver, test_steps);
		report.collapseTextReportIsDiplaying(driver, test_steps);
		report.iconOneIsDiplaying(driver, test_steps);
		report.effectiveDateIsDiplaying(driver, test_steps);
		report.todayDropdownIsDiplaying(driver, test_steps);
		report.effectiveDateDropdownClickable(driver, test_steps);
		report.clickeffectiveDateDropdown(driver, test_steps);
		Wait.wait5Second();
		report.effectiveDateDropDownOptions(driver, test_steps);
		report.dateFieldButtonIsDiplaying(driver, test_steps);
		report.dateFieldInputIsDiplaying(driver, test_steps);
		report.returnToDefaultClickable(driver, test_steps);
		report.collapseClickable(driver, test_steps);
		report.getTextEffectiveDateDropdown(driver, test_steps,"Today");
		report.getTextDateFieldInput(driver, test_steps,getCurrentDate);
		/************************************Section 2********************************************/
		report.iconTwoIsDiplaying(driver, test_steps);
		report.selectInputsIsDiplaying(driver, test_steps);
		report.includeReservationTypeTextIsDiplaying(driver, test_steps);
		report.includeReservationTypeIconIsDiplaying(driver, test_steps);
		report.clearAllButtonIsDiplaying(driver, test_steps);
		report.inHouseCheckBoxIsDiplaying(driver, test_steps);
		report.inHouseTextIsDiplaying(driver, test_steps);
		report.departedCheckBoxIsDiplaying(driver, test_steps);
		report.departedTextIsDiplaying(driver, test_steps);
		report.cancelledCheckBoxIsDiplaying(driver, test_steps);
		report.cancelledTextIsDiplaying(driver, test_steps);
		report.reservedCheckBoxIsDiplaying(driver, test_steps);
		report.reservedTextIsDiplaying(driver, test_steps);
		report.noShowCheckBoxIsDiplaying(driver, test_steps);
		report.noShowTextIsDiplaying(driver, test_steps);
		report.confirmedCheckBoxIsDiplaying(driver, test_steps);
		report.confirmedTextIsDiplaying(driver, test_steps);
		report.onHoldCheckBoxIsDiplaying(driver, test_steps);
		report.onHoldTextIsDiplaying(driver, test_steps);
		report.guaranteedCheckBoxIsDiplaying(driver, test_steps);
		report.guaranteedTextIconIsDiplaying(driver, test_steps);
		HashMap<String, Boolean> getStatus = report.includeReservationTypeCheckBoxesStatus(driver);
		for(String i :getStatus.keySet()) {
			test_steps.add("--- Verifying '"+i+"' is By Default Checked ---");
			Utility.printString("getStatus.get(i) : "  + getStatus.get(i));
			assertTrue(getStatus.get(i), "Failed to check By Default Checked");
			test_steps.add("Verified '"+i+"' is By Default Checked");
			}
		report.includedBalancesTextIsDiplaying(driver, test_steps);
		report.includedBalancesIconIsDiplaying(driver, test_steps);
		report.allDropdownIsDiplaying(driver, test_steps);
		report.includedBalancesDropdownClickable(driver, test_steps);
		report.clickincludedBalancesDropdown(driver, test_steps);
		report.includedBalancesDropDownOptions(driver, test_steps);
		report.getTextIncludedBalancesDropdown(driver, test_steps);
		
	
		/************************************Check Box Click**************************************/
//		report.clickInHouseCheckBox(driver, test_steps);
//		report.clickDepartedCheckBox(driver, test_steps);
//		report.clickCancelledCheckBox(driver, test_steps);
//		report.clickReservedCheckBox1(driver, test_steps);
//		report.clickNoShowCheckBox(driver, test_steps);
//		report.clickConfirmedCheckBox(driver, test_steps);
//		report.clickOnHoldCheckBox(driver, test_steps);
//		report.clickGuaranteedCheckBox(driver, test_steps);
	/************************************Section 3**************************************************/
		report.iconThreeIsDiplaying(driver, test_steps);
		report.customizeDetailedViewIsDiplaying(driver, test_steps);
		report.sortReportByTextIsDiplaying(driver, test_steps);
		report.sortReportByIconIsDiplaying(driver, test_steps);
		report.customizeDetailedViewDropdownIsDiplaying(driver, test_steps);
		report.guestNameDropdownClickable(driver, test_steps);
		report.getTextCustomizeDetailedViewDropdown(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownOptions(driver, test_steps);
		
		/************************************Run Report Button*************************************/
		report.runReportButtonIsDiplaying(driver, test_steps);
		report.runReportButtonClickable(driver, test_steps);
		
		String reportTypeValue = "Folio Balances Report - Guest Ledger";
		String getTextCustomizeDetailedViewDropdown = report.getTextCustomizeDetailedViewDropdown(driver);
		String getTextEffectiveDateDropdown = report.getTextEffectiveDateDropdown(driver)+" | "+getCurrentDate;
		String noValue ="No";
		String allBalance = "Receivable balances | Payable balances";
		String selectedCheckBox ="In-House | Departed | Cancelled | Reserved | No Show | Confirmed | On Hold | Guaranteed";
		
		report.clickrunReportButton(driver, test_steps);
		Wait.wait10Second();
		/************************************Section After Run Report Button**********************/
		report.reportTypeHeadingIsDiplaying(driver, test_steps);
		report.reportTypeValue(driver, test_steps, reportTypeValue);
		report.effectiveDateHeadingIsDiplaying(driver, test_steps);
		report.effectiveDateValue(driver, test_steps,getTextEffectiveDateDropdown);
		report.reservationTypeHeadingIsDiplaying(driver, test_steps);
		report.reservationTypeValue(driver, test_steps, selectedCheckBox);
		report.includedBalancesHeadingIsDiplaying(driver, test_steps);
		report.includedBalancesValue(driver, test_steps, allBalance);
		report.generatedOnHeadingIsDiplaying(driver, test_steps);
		report.reportSortByHeadingIsDiplaying(driver, test_steps);
		report.reportSortByValue(driver, test_steps, getTextCustomizeDetailedViewDropdown);
		report.displayAccountHeadingIsDiplaying(driver, test_steps);
		report.displayAccountValue(driver, test_steps,noValue);
		report.includePendingHeadingIsDiplaying(driver, test_steps);
		report.includePendingValue(driver, test_steps,noValue);
		report.includeAuthorizationsHeadingIsDiplaying(driver, test_steps);
		report.includeAuthorizationsValue(driver, test_steps,noValue);
		report.folioBalancesLabelIsDiplaying(driver, test_steps);
		report.receivableBalancesLabelIsDiplaying(driver, test_steps);
		report.payableBalancesLabelIsDiplaying(driver, test_steps);
		report.folioBalancesReservationTable(driver, test_steps);
     	report.receivableBalance(driver, test_steps);
		report.payableBalances(driver, test_steps);
		report.netReceiveablePayableBalances(driver, test_steps);
		report.guestNameReservationTableSortedByAscending(driver, test_steps);
		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Reservation Number");
		report.clickOnRunReport(driver);
		report.reservationNumberReservationTableSortedByAscending(driver, test_steps);

		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Arrival Date");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByArrivalDateColumn(driver, test_steps);
		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Room Number");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByRoomNoColumn(driver, test_steps);
		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Folio Balance");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByFolioBalanceColumn(driver, test_steps);

		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Departure Date");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByDepartureDateColumn(driver, test_steps);
	
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport("Validating UI of Folio Balances Report", test_description, test_category, test_steps);
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating All Tables Date Tooltips", test_description, test_category, test_steps);
				Utility.updateReport(e, "Validating All Tables Date Tooltips", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		
	}

	

}
