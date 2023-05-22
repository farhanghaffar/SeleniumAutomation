package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2FolioBalanceUIValidation extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_category = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ReportsV2 report= new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
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
	String  currency = null, testName=null, clientTimeZone, dFormat, propertyName ;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginAutoReportsV2(driver, test_steps);		
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
		admin.logout(driver, test_steps);
		
	}

	@Test(groups = "ReportsV2")
	public void reportsV2FolioBalanceUIValidation() throws InterruptedException, IOException, ParseException {
		test_name = "ReportsV2FolioBalanceUIValidation";
		test_description = "Validate UI of Folio Balance Report<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/849234' target='_blank'>"
				+ "Click here to open TestRail: 849234</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/849237' target='_blank'>"
				+ "Click here to open TestRail: 849237</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/849235' target='_blank'>"
				+ "Click here to open TestRail: 849235</a><br>";
		test_category = "ReportsV2 - Folio Balance Report";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("825295|849237|849235", Utility.testId, Utility.statusCode,
				Utility.comments, "");
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
		getTimeZone();
		loginAutoReportsV2(driver, test_steps);
		test_steps.add("Logged into the application");
		app_logs.info("Logged into the application");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_category, test_steps);
		}
		String getCurrentDate = Utility.getCurrentDate("MMM dd, YYYY, EEEE", TestCore.propertyTimeZone).trim();
		String getDate = Utility.getYesturdayDate("dd/MM/yyyy");
		String getPastDate=ESTTimeZone.reformatDate(getDate, "dd/MM/yyyy", "MMM dd, YYYY, EEEE");
		String futureDate= Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
		app_logs.info(getCurrentDate + " "+ getDate +" "+ getPastDate+" "+futureDate );
		try {
		propertyName=prop.getProperty(driver, test_steps);
		nav.ReportsV2(driver);
		test_steps.add("Click on Reports");		
		report.clickOnNavigateFolioBalances(driver,test_steps);
		test_steps.add("Click on Folio Balances Report");
		Wait.wait10Second();		
		/************************************Section Right*****************************************/
		
		
		String homePagePropertyName= report.getSelectedPropertyNameFromReportPage(driver, test_steps);
		Utility.verifyEquals(propertyName.toLowerCase().trim(), homePagePropertyName.toLowerCase().trim(), test_steps);
		app_logs.info("Start Execution");
		report.iconRightIsDiplaying(driver, test_steps);
		report.AdvancedInputsTextIsDiplaying(driver, test_steps);
		report.AdvancedInputsIconIsDiplaying(driver, test_steps);
		report.expandAllButtonIsDiplaying(driver, test_steps);
		report.displayAccountCompanyNameIsDiplaying(driver, test_steps);
		report.displayAccountNoIsDiplaying(driver, test_steps);
		report.displayAccountIconIsDiplaying(driver, test_steps);
		report.displayAccountTextIsDiplaying(driver, test_steps);
		report.clickdisplayAccountIcon(driver, test_steps);
	
		Wait.wait1Second();
		report.displayAccountNoButtonIsDiplaying(driver, test_steps);
		report.displayAccountNoButtonClickable(driver, test_steps);
		report.displayAccountYesButtonIsDiplaying(driver, test_steps);
		report.displayAccountYesButtonClickable(driver, test_steps);
		report.VerifyAccountButtonisClicked(driver, test_steps);
		report.clickdisplayAccountIcon(driver, test_steps);
		report.includePendingFolioItemsIsDiplaying(driver, test_steps);
		report.includePendingNoTextIsDiplaying(driver, test_steps);
		report.includePendingYesTextIsDiplaying(driver, test_steps);
		report.includePendingCollapseExpandIconIsDiplaying(driver, test_steps);
		report.clickincludePendingIcon(driver, test_steps);
		Wait.wait1Second();
		report.includePendingNoButtonIsDiplaying(driver, test_steps);
		report.includePendingNoButtonClickable(driver, test_steps);
		report.includePendingYesButtonIsDiplaying(driver, test_steps);
		report.includePendingYesButtonClickable(driver, test_steps);
		report.VerifyIncludePendingFolioItemsisClicked(driver, test_steps);		
		report.clickincludePendingIcon(driver, test_steps);
		report.includeAuthorizationIsDiplaying(driver, test_steps);
		report.includeAuthorizationTextIsDiplaying(driver, test_steps);
		report.includeAuthorizationNoLabelIsDiplaying(driver, test_steps);
		report.includeAuthorizationExpandIconIsDiplaying(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		Wait.wait10Second();
		report.includeAuthorizationNoButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationNoButtonClickable(driver, test_steps);
		report.includeAuthorizationYesButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationYesButtonClickable(driver, test_steps);
		report.VerifyIncludeAuthorizationisClicked(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		
		/************************************Section 1********************************************/
		report.folioBalancesReportIsDiplaying(driver,test_steps);
		report.allreceivablepayableIsDiplaying(driver,test_steps);
		report.returnToDefaultTextIsDiplaying(driver,test_steps);
		report.returnToDefaultClickable(driver, test_steps);
		report.returnToDefaultButtonIsDiplaying(driver,test_steps);
		report.collapseClickable(driver, test_steps);
		report.collapseButtonIsDiplaying(driver, test_steps);
		report.collapseTextReportIsDiplaying(driver, test_steps);
		report.iconOneIsDiplaying(driver, test_steps);
		report.effectiveDateIsDiplaying(driver, test_steps);
		report.todayDropdownIsDiplaying(driver, test_steps);
		report.effectiveDateDropdownClickable(driver, test_steps);
		Utility.ScrollToUp(driver);
		Wait.wait5Second();
		
		report.clickeffectiveDateDropdown(driver, test_steps);
		Wait.wait10Second();
		report.effectiveDateDropDownOptions(driver, test_steps);
		report.dateFieldButtonIsDiplaying(driver, test_steps);
		report.dateFieldInputIsDiplaying(driver, test_steps);
		report.getTextEffectiveDateDropdown(driver, test_steps, "Today");
		report.getTextDateFieldInput(driver, test_steps,getCurrentDate);
		report.clickeffectiveDateDropdown(driver, test_steps);
		
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
		/*
		HashMap<String, Boolean> getStatus = report.includeReservationTypeCheckBoxesStatus(driver);
		for(String i :getStatus.keySet()) {
			test_steps.add("--- Verifying '"+i+"' is By Default Checked ---");
			Utility.printString("getStatus.get(i) : "  + getStatus.get(i));
			assertTrue(getStatus.get(i), "Failed to check By Default Checked");
			test_steps.add("Verified '"+i+"' is By Default Checked");
			}
			*/
		report.includedBalancesTextIsDiplaying(driver, test_steps);
		report.includedBalancesIconIsDiplaying(driver, test_steps);
		report.allDropdownIsDiplaying(driver, test_steps);
		report.includedBalancesDropdownClickable(driver, test_steps);
		report.clickincludedBalancesDropdown(driver, test_steps);
		
		report.includedBalancesDropDownOptions(driver, test_steps);
		report.getTextIncludedBalancesDropdown(driver, test_steps);
		
	

		
		/************************************Section 3**************************************************/
		report.iconThreeIsDiplaying(driver, test_steps);
		report.customizeDetailedViewIsDiplaying(driver, test_steps);
		report.sortReportByTextIsDiplaying(driver, test_steps);
		report.sortReportByIconIsDiplaying(driver, test_steps);
		report.customizeDetailedViewDropdownIsDiplaying(driver, test_steps);
		report.guestNameDropdownClickable(driver, test_steps);
		report.getTextCustomizeDetailedViewDropdown(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		Wait.wait2Second();
		/*
		report.customizedDetailedViewDropDownOptions(driver, test_steps);
		*/
	
		/****************************Run To Default**********************/
		/*Wait.wait5Second();
		report.clickrunReportButton(driver, test_steps);
		Wait.wait2Second();
		report.editButtonClick(driver, test_steps);
		Wait.wait2Second();*/
		report.selectDateRange(driver, "Yesterday", test_steps);
		Wait.wait2Second();
		report.getTextDateFieldInput(driver, test_steps,getPastDate);
		Wait.wait2Second();
		report.clickdisplayAccountIcon(driver, test_steps);
		Wait.wait2Second();
		report.clickDisplayAccountYesButton(driver, test_steps);
		Wait.wait2Second();
		report.clickincludePendingIcon(driver, test_steps);
		Wait.wait2Second();
		//report.clickIncludePendingYesButton(driver, test_steps);
		report.clickIncludePendingNoButton(driver, test_steps);
		Wait.wait2Second();
		report.clickincludeAuthorizationIcon(driver, test_steps);
		Wait.wait2Second();
		report.clickIncludeAuthorizationYesButton(driver, test_steps);
		Wait.wait2Second();
		
		
		/************************************Check Box Click**************************************/
		report.clickInHouseCheckBox(driver, test_steps);
		report.clickDepartedCheckBox(driver, test_steps);
		report.clickCancelledCheckBox(driver, test_steps);
		report.clickReservedCheckBox1(driver, test_steps);
		report.clickNoShowCheckBox(driver, test_steps);
		report.clickConfirmedCheckBox(driver, test_steps);
		report.clickOnHoldCheckBox(driver, test_steps);
		report.clickGuaranteedCheckBox(driver, test_steps);
		
		test_steps.add("<b>========= Data Befote click on return to default button=========");
		report.VerifyAccountButtonisClicked(driver, test_steps);
		report.VerifyIncludePendingFolioItemsisClicked(driver, test_steps);
		report.VerifyIncludeAuthorizationisClicked(driver, test_steps);
		
		report.inHouseCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.cancelledCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.departedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.reservedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.noShowCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.confirmedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.onHoldCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.guaranteedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		
		test_steps.add("<b>=========Verify data after click on return to default button========");
		Wait.wait2Second();
		report.clickreturnToDefaultButton(driver, test_steps);
		Wait.wait2Second();
	/*	report.iconRightIsDiplaying(driver, test_steps);
		Wait.wait2Second();
		report.AdvancedInputsTextIsDiplaying(driver, test_steps);
		Wait.wait2Second();
		report.AdvancedInputsIconIsDiplaying(driver, test_steps);
		Wait.wait2Second();
		report.expandAllButtonIsDiplaying(driver, test_steps);
		Wait.wait2Second();
		report.displayAccountCompanyNameIsDiplaying(driver, test_steps);
		Wait.wait2Second();
		report.displayAccountIconIsDiplaying(driver, test_steps);
		Wait.wait10Second();
		report.displayAccountNoButtonIsDiplaying(driver, test_steps);
		report.displayAccountNoButtonClickable(driver, test_steps);
		report.displayAccountYesButtonIsDiplaying(driver, test_steps);
		report.displayAccountYesButtonClickable(driver, test_steps);
		report.VerifyAccountButtonisClicked(driver, test_steps);
		report.includePendingFolioItemsIsDiplaying(driver, test_steps);
		report.includePendingCollapseExpandIconIsDiplaying(driver, test_steps);
		Wait.wait10Second();

		report.includePendingNoButtonIsDiplaying(driver, test_steps);
		report.includePendingNoButtonClickable(driver, test_steps);
		report.includePendingYesButtonIsDiplaying(driver, test_steps);
		report.includePendingYesButtonClickable(driver, test_steps);
		report.VerifyIncludePendingFolioItemsisClicked(driver, test_steps);		
		report.includeAuthorizationIsDiplaying(driver, test_steps);
		report.includeAuthorizationExpandIconIsDiplaying(driver, test_steps);
		Wait.wait10Second();
		report.includeAuthorizationNoButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationNoButtonClickable(driver, test_steps);
		report.includeAuthorizationYesButtonIsDiplaying(driver, test_steps);
		report.includeAuthorizationYesButtonClickable(driver, test_steps);
		report.VerifyIncludeAuthorizationisClicked(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		*/
		
		report.getTextEffectiveDateDropdown(driver, test_steps, "Today");
		report.getTextDateFieldInput(driver, test_steps,getCurrentDate);
		report.clickdisplayAccountIcon(driver, test_steps);
		report.VerifyAccountButtonisClicked(driver, test_steps);
		report.clickincludePendingIcon(driver, test_steps);
		report.VerifyIncludePendingFolioItemsisClicked(driver, test_steps);
		report.clickincludeAuthorizationIcon(driver, test_steps);
		report.VerifyIncludeAuthorizationisClicked(driver, test_steps);
		/*
		report.inHouseCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.cancelledCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.departedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.reservedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.noShowCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.confirmedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.onHoldCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		report.guaranteedCheckBoxIsCheckedOrUnchecked(driver, test_steps);
		*/
		test_steps.add("To verify inputs default in folio balances report"
				+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/849234' target='_blank'>"
				+ "Click here to open TestRail: 849234</a><br>");
		Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "To verify inputs default in folio balances report");
		Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
				Utility.comments.get(0), TestCore.TestRail_AssignToID);
		
		
		test_steps.add("========Verify Custom Date Selection========");
		app_logs.info("========Verify Custom Date Selection========");
		report.clickdateFieldButton(driver, test_steps);
		Utility.selectStartDate(driver, test_steps, futureDate);
		report.getTextEffectiveDateDropdown(driver, test_steps, "Custom");
		
		test_steps.add("To verify date selector inputs section with custom date"
				+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/849237' target='_blank'>"
				+ "Click here to open TestRail: 849237</a><br>");
		Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "To verify date selector inputs section with custom date");
		Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
				Utility.comments.get(1), TestCore.TestRail_AssignToID);
		
/*		*//************************************Section 1********************************************//*
		report.folioBalancesReportIsDiplaying(driver,test_steps);
		report.allreceivablepayableIsDiplaying(driver,test_steps);
		report.returnToDefaultTextIsDiplaying(driver,test_steps);
		report.returnToDefaultClickable(driver, test_steps);
		report.returnToDefaultButtonIsDiplaying(driver,test_steps);
		report.collapseClickable(driver, test_steps);
		report.collapseButtonIsDiplaying(driver, test_steps);
		report.collapseTextReportIsDiplaying(driver, test_steps);
		report.iconOneIsDiplaying(driver, test_steps);
		report.effectiveDateIsDiplaying(driver, test_steps);
		report.todayDropdownIsDiplaying(driver, test_steps);
		report.effectiveDateDropdownClickable(driver, test_steps);
		report.clickeffectiveDateDropdown(driver, test_steps);
		Wait.wait10Second();
		report.effectiveDateDropDownOptions(driver, test_steps);
		report.dateFieldButtonIsDiplaying(driver, test_steps);
		report.dateFieldInputIsDiplaying(driver, test_steps);
		report.getTextEffectiveDateDropdown(driver, test_steps);
		report.getTextDateFieldInput(driver, test_steps,getCurrentDate);
		report.clickeffectiveDateDropdown(driver, test_steps);
		
		*//************************************Section 2********************************************//*
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
		HashMap<String, Boolean> getStatusAfterReturnToDefault = report.includeReservationTypeCheckBoxesStatus(driver);
		for(String i :getStatusAfterReturnToDefault.keySet()) {
			test_steps.add("--- Verifying '"+i+"' is By Default Checked ---");
			Utility.printString("getStatus.get(i) : "  + getStatus.get(i));
			assertTrue(getStatusAfterReturnToDefault.get(i), "Failed to check By Default Checked");
			test_steps.add("Verified '"+i+"' is By Default Checked");
			}
		report.includedBalancesTextIsDiplaying(driver, test_steps);
		report.includedBalancesIconIsDiplaying(driver, test_steps);
		report.includedBalancesDropdownClickable(driver, test_steps);
		Wait.wait2Second();
		report.clickincludedBalancesDropdown(driver, test_steps);
		Wait.wait2Second();
		report.includedBalancesDropDownOptions(driver, test_steps);
		Wait.wait2Second();
		report.getTextIncludedBalancesDropdown(driver, test_steps);
		
	
		*//************************************Section 3**************************************************//*
		report.iconThreeIsDiplaying(driver, test_steps);
		report.customizeDetailedViewIsDiplaying(driver, test_steps);
		report.sortReportByTextIsDiplaying(driver, test_steps);
		report.sortReportByIconIsDiplaying(driver, test_steps);
		report.customizeDetailedViewDropdownIsDiplaying(driver, test_steps);
		report.guestNameDropdownClickable(driver, test_steps);
		report.getTextCustomizeDetailedViewDropdown(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownOptions(driver, test_steps);
		
		
		
*/		
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

		/************************************Header Section After Run Report Button**********************/
		test_steps.add("--- Verify Header Section ---");
		test_steps.add("--- Verify Effective date in header section ---");
		String getEffectiveDate = report.getEffectiveDateHeader(driver);
		Utility.verifyEquals(getEffectiveDate, getTextEffectiveDateDropdown, test_steps);
		
		test_steps.add("--- Verify Reservation Types in header section ---");
		String getReservationType = report.getReservationTypeHeader(driver);
		Utility.verifyEquals(getReservationType, "In-House | Departed | Cancelled | + 5 more", test_steps);
		
		test_steps.add("--- Verify Include Balances in header section ---");
		String getIncludeBalances = report.getIncludeBalancesHeader(driver);
		Utility.verifyEquals(getIncludeBalances, allBalance, test_steps);
		
		test_steps.add("--- Verify Sort by in header section ---");
		String getSortBy = report.getSortReportByHeader(driver);
		Utility.verifyEquals(getSortBy, "Guest Name", test_steps);

		/************************************Section After Run Report Button**********************/
		String propertyName=report.getProptyName(driver, test_steps);
		report.header(driver, test_steps, propertyName);
		report.reportTypeHeadingIsDiplaying(driver, test_steps);
		report.reportTypeValue(driver, test_steps, reportTypeValue);
		report.effectiveDateHeadingIsDiplaying(driver, test_steps);
		report.effectiveDateValue(driver, test_steps,getTextEffectiveDateDropdown);
		report.reservationTypeHeadingIsDiplaying(driver, test_steps);
		report.reservationTypeValue(driver, test_steps, selectedCheckBox);
		report.includedBalancesHeadingIsDiplaying(driver, test_steps);
		report.includedBalancesValue(driver, test_steps, allBalance);
		report.generatedOnHeadingIsDiplaying(driver, test_steps);
		String value=report.generatedOnValue(driver);
		printString(Utility.getCurrentDate("MMM dd, yyyy"));
		Assert.assertTrue(value.contains(Utility.getCurrentDate("MMM dd, yyyy")) ,"Generate On Date is not correct");
		test_steps.add("Verified value of 'generate on heading' " +value);
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
		
		report.selectProperty(driver, "All");
		Wait.wait10Second();
		report.clickrunReportButton(driver, test_steps);
		Wait.wait10Second();
		
		test_steps.add("--- Verify Header Section ---");
		test_steps.add("--- Verify Effective date in header section ---");
		String getEffectiveDate1 = report.getEffectiveDateHeader(driver);
		Utility.verifyEquals(getEffectiveDate1, getTextEffectiveDateDropdown, test_steps);
		
		test_steps.add("--- Verify Reservation Types in header section ---");
		String getReservationType1 = report.getReservationTypeHeader(driver);
		Utility.verifyEquals(getReservationType1, "In-House | Departed | Cancelled | + 5 more", test_steps);
		
		test_steps.add("--- Verify Include Balances in header section ---");
		String getIncludeBalances1 = report.getIncludeBalancesHeader(driver);
		Utility.verifyEquals(getIncludeBalances1, allBalance, test_steps);
		
		test_steps.add("--- Verify Sort by in header section ---");
		String getSortBy1 = report.getSortReportByHeader(driver);
		Utility.verifyEquals(getSortBy1, "Guest Name", test_steps);

		test_steps.add("To verify folio balance report for multi property client"
				+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/849235' target='_blank'>"
				+ "Click here to open TestRail: 849235</a><br>");
		Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "To verify folio balance report for multi property client");
		Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
				Utility.comments.get(2), TestCore.TestRail_AssignToID);
		
		/*report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Folio Balance");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByFolioBalanceColumn(driver, test_steps);
		
		Wait.wait2Second();
		report.editButtonClick(driver, test_steps);
		report.clickcustomizeDetailedViewDropdown(driver, test_steps);		
		report.customizedDetailedViewDropDownSelect(driver, test_steps, "Departed Date");
		report.clickOnRunReport(driver);
		report.reservationTableSortedByDepartureDateColumn(driver, test_steps);
	*/
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, "Failed to verify folio balances report UI", "Folio Balance Report",
					"Folio Balance Report", testName, test_description, test_category, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, "Failed to verify folio balances report UI", "Folio Balance Report", "Folio Balance Report",
					testName, test_description, test_category, test_steps);
		}
				
		
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
	//	driver.quit();
	}


}
