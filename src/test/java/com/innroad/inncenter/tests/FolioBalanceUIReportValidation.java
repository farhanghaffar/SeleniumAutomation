package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.PaymentInfo;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class FolioBalanceUIReportValidation extends TestCore{

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;
	ReportsV2 report = new ReportsV2();
	Properties prop=new Properties();
	GuestHistory guestHistory=new GuestHistory();
	Navigation nav = new Navigation();
	FolioNew folioNew=new FolioNew();
	ReservationV2 reservationV2Page = new ReservationV2();
	GuestFolio guestFolio = new GuestFolio();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	//GuestFolio guestfolio=new GuestFolio();
	ReservationSearch reservationSearch = new ReservationSearch();
	ArrayList<String> test_steps = new ArrayList<>();
	OR_ReservationV2 or_res=new OR_ReservationV2();
	String sourceOfRes="Account Reservation", reservationNumber = null, guestFirstName = null, guestLastName,guestFirstNameCopied = null, guestLastNameCopied		 , reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), roomCharge , guestFullName,guestFullName2,guestFullNameCopied,reservation_Status;
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, String> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	HashMap<String, String> reservationDetails = new HashMap<>();
	HashMap<String, String> copiedReservationDetails = new HashMap<>();
	String stayInfo = null;	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();
	HashMap<String, Double> expChanges2 = new HashMap<>();
	HashMap<String, String> paymentData = new HashMap<>();	
	HashMap<String, String> paymentTransactionTypeAndAmount = new HashMap<>();	
	HashMap<String, ArrayList<String>> beforeCreditCardTransactionTypeTotal2=new HashMap<>();
	HashMap<String, ArrayList<String>> afterCreditCardTransactionTypeTotal2=new HashMap<>();
	HashMap<String, String> itemDescription = new HashMap<>();
	HashMap<String, String> reservationData = new HashMap<>();
	Account account = new Account();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginAutoReportsV2(driver, test_steps);
		
	}

	@Test(dataProvider = "getData",groups = "ReportsV2")
	public void reportsV2ReservationCardReportForLongStayRes(String TestCaseID, String Scenario) throws Throwable {

		if(Utility.getResultForCase(driver, TestCaseID))
		{
		test_name = Scenario;
		test_description = "Validate ReservationCard  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - ReservationCard Report";
		String testName = test_name;		
		
		Utility.initializeTestCase(TestCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			nav.ReportsV2(driver);
			test_steps.add("Click on Reports");		
			report.clickOnNavigateFolioBalances(driver,test_steps);
			
			test_steps.add("Click on Folio Balances Report");
			Wait.wait10Second();		
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
			report.clickEffectiveDateDropdown(driver, test_steps);
			Wait.wait5Second();
			report.selectEffectiveDateDropdown(driver, test_steps, "Yesterday");
			Wait.wait5Second();
			report.clickEffectiveDateDropdown(driver, test_steps);
			
			Wait.wait5Second();
			report.selectEffectiveDateDropdown(driver, test_steps, "Today");
			Wait.wait5Second();
			
			
			report.getTextDateFieldInput(driver, test_steps,Utility.getCurrentDate("MMM dd, YYYY, EEEE"));
			Utility.ScrollToUp(driver);
			Wait.wait5Second();
			
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
			
			Utility.ScrollToEnd(driver);
			
			report.includedBalancesTextIsDiplaying(driver, test_steps);
			report.includedBalancesIconIsDiplaying(driver, test_steps);
			report.allDropdownIsDiplaying(driver, test_steps);
			report.includedBalancesDropdownClickable(driver, test_steps);
			report.clickincludedBalancesDropdown(driver, test_steps);
			
			report.includedBalancesDropDownOptions(driver, test_steps);
			report.getTextIncludedBalancesDropdown(driver, test_steps);
			Wait.wait5Second();
			report.selectIncludedBalancesDropDownOptions(driver, test_steps,"Payable balances");
			Wait.wait5Second();
			report.clickincludedBalancesDropdown(driver, test_steps);
			report.selectIncludedBalancesDropDownOptions(driver, test_steps,"Receivable balances");
			Wait.wait10Second();
			report.clickincludedBalancesDropdown(driver, test_steps);
			report.selectIncludedBalancesDropDownOptions(driver, test_steps,"All");
			Wait.wait10Second();
			
			/************************************Section 3**************************************************/
			
			report.iconThreeIsDiplaying(driver, test_steps);
			report.customizeDetailedViewIsDiplaying(driver, test_steps);
			report.sortReportByTextIsDiplaying(driver, test_steps);
			report.sortReportByIconIsDiplaying(driver, test_steps);
			report.customizeDetailedViewDropdownIsDiplaying(driver, test_steps);
			report.guestNameDropdownClickable(driver, test_steps);
			report.getTextCustomizeDetailedViewDropdown(driver, test_steps);
			Wait.wait5Second();
			Utility.ScrollToUp(driver);
			Wait.wait10Second();
			report.clearAllButtonClick(driver, test_steps);	
			report.clickInHouseCheckBox(driver, test_steps);
			report.clickDepartedCheckBox(driver, test_steps);
			report.clickCancelledCheckBox(driver, test_steps);
			report.clickReservedCheckBox1(driver, test_steps);
			report.clickNoShowCheckBox(driver, test_steps);
			report.clickConfirmedCheckBox(driver, test_steps);
			report.clickOnHoldCheckBox(driver, test_steps);
			report.clickGuaranteedCheckBox(driver, test_steps);
			
			
			/************************************Run Report Button*************************************/
		
			report.runReportButtonIsDiplaying(driver, test_steps);
			report.runReportButtonClickable(driver, test_steps);
			
			String reportTypeValue = "Folio Balances Report - Guest Ledger";
			String getTextCustomizeDetailedViewDropdown = report.getTextCustomizeDetailedViewDropdown(driver);
			String getTextEffectiveDateDropdown = report.getTextEffectiveDateDropdown(driver)+" | "+Utility.getCurrentDate("MMM dd, YYYY, EEEE");
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
	     	//report.receivableBalance(driver, test_steps);
			//report.payableBalances(driver, test_steps);
			//report.netReceiveablePayableBalances(driver, test_steps);
			report.guestNameReservationTableSortedByAscending(driver, test_steps);
			report.editButtonClick(driver, test_steps);
			report.clickcustomizeDetailedViewDropdown(driver, test_steps);
			report.customizedDetailedViewDropDownSelect(driver, test_steps, "Reservation Number");
			report.clickOnRunReport(driver);
			app_logs.info("--- reservationNumberReservationTableSortedByAscending ---");
			report.reservationNumberReservationTableSortedByAscending(driver, test_steps);

			report.editButtonClick(driver, test_steps);
			report.clickcustomizeDetailedViewDropdown(driver, test_steps);
			report.customizedDetailedViewDropDownSelect(driver, test_steps, "Arrival Date");
			report.clickOnRunReport(driver);
			app_logs.info("--- reservationTableSortedByArrivalDateColumn ---");
			report.reservationTableSortedByArrivalDateColumn(driver, test_steps);
			
			report.editButtonClick(driver, test_steps);
			report.clickcustomizeDetailedViewDropdown(driver, test_steps);
			report.customizedDetailedViewDropDownSelect(driver, test_steps, "Room Number");
			report.clickOnRunReport(driver);
			app_logs.info("--- reservationTableSortedByRoomNoColumn ---");
			report.reservationTableSortedByRoomNoColumn(driver, test_steps);
			app_logs.info("--- select propert option All---");
			report.selectProperty(driver, "All");
			Wait.wait10Second();
			report.clickrunReportButton(driver, test_steps);
			Wait.wait10Second();
			
			test_steps.add("--- Verify Header Section ---");
			test_steps.add("--- Verify Effective date in header section ---");
			app_logs.info("--- Verify Header Section ---");
			String getEffectiveDate1 = report.getEffectiveDateHeader(driver);
			Utility.verifyEquals(getEffectiveDate1, getTextEffectiveDateDropdown, test_steps);
			
			test_steps.add("--- Verify Reservation Types in header section ---");
			app_logs.info("--- Verify Reservation Types in header section ---");
			String getReservationType1 = report.getReservationTypeHeader(driver);
			Utility.verifyEquals(getReservationType1, "In-House | Departed | Cancelled | + 5 more", test_steps);
			
			test_steps.add("--- Verify Include Balances in header section ---");
			app_logs.info("--- Verify Include Balances in header section ---");
			String getIncludeBalances1 = report.getIncludeBalancesHeader(driver);
			Utility.verifyEquals(getIncludeBalances1, allBalance, test_steps);
			
			test_steps.add("--- Verify Sort by in header section ---");
			app_logs.info("--- Verify Sort by in header section ---");
			String getSortBy1 = report.getSortReportByHeader(driver);
			Utility.verifyEquals(getSortBy1, "Guest Name", test_steps);
			
			
			test_steps.add("--- Verify custom date selection---");
			app_logs.info("--- Verify custom date selection----");
			report.editButtonClick(driver, test_steps);
			Elements_Reports element=new Elements_Reports(driver);
			Wait.waitForElementByXpath(driver, OR_Reports.customDate);
			element.customDate.click();
			Wait.waitForElementByXpath(driver, OR_Reports.customDateInput);
			element.customDateInput.click();
			String effectiveDate=element.effectiveDateDropdown.getText();
			Utility.verifyEquals(effectiveDate,"Custom", test_steps);
			test_steps.add("--- Verified custom date selection---");
			app_logs.info("--- Verified custom date selection----");
			
		


		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify foliobalance Report");
			}					
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	@DataProvider
	public Object[][] getData() {
		
		return Utility.getData("FolioBalance", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode, Utility.comments, TestCore.TestRail_AssignToID);

	}




	

}
