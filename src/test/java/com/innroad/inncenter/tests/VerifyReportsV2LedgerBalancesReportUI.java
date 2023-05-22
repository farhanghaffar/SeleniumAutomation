package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyReportsV2LedgerBalancesReportUI extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	//ArrayList<String> reportsAccess = new ArrayList<>();
	//ArrayList<String> reports = new ArrayList<>();
	
	ArrayList<String> AllLedgerAccountTypes = new ArrayList<>();
	
	HashSet<String> typesUnique = new HashSet<>();
	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> types = new ArrayList<>();
	ArrayList<String> status = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyReportsV2LedgerBalancesReportSelectInputs(String url, String clientId, String userName,
			String password, String tooltipMessageIncludedLedgerAccounts, String tooltipMessageExcludeZeroBalance, 
			String tooltipMessageDisplayCustomGeneral, String SortByOption, String GroupByOption)throws InterruptedException, IOException, ParseException {

		test_name = "VerifyReportsV2LedgerBalancesReportSelectInputs";
		test_description = "ReportsV2 - Ledger Balances Reports<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682418' target='_blank'>"
				+ "Click here to open TestRail: C682418</a>";
		test_category = "ReportsV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		Admin admin = new Admin();
		ReportsV2 report = new ReportsV2();
		LedgerAccount la = new LedgerAccount();
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver, envURL, clientId, userName, password);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
		} catch (Exception e) {
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		
		} catch (Error e) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
		}
		
		//Getting account details from Ledger Account page
		test_steps.add("========= Getting account details from Ledger Account page =========");
		try {
			
			nav.setup(driver);
			nav.LedgerAccounts(driver);
			la.PropertyTab(driver);
			
			names = la.getAllNamesFromLedgerAccount(driver, test_steps);
			types = la.getAllTypesFromLedgerAccount(driver, test_steps);
			status = la.getAllAccountsStatusFromLedgerAccount(driver, test_steps);
			typesUnique = la.getAllTypesUnique(driver, test_steps);
			
			test_steps.add("Names- Count "+names.size()+", List: "+names);
			test_steps.add("Types- Count "+types.size()+", List: "+names);
			test_steps.add("Status- Count "+status.size()+", List: "+names);
			test_steps.add("Unique type- Count "+typesUnique.size()+", List: "+names);
			
			app_logs.info("Names: "+names.size()+" Details: "+names);
			app_logs.info("Types: "+types.size()+" Details: "+types);
			app_logs.info("Status: "+status.size()+" Details: "+status);
			app_logs.info("Status: "+typesUnique.size()+" Details: "+typesUnique);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Reading Ledger Account details", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Reading Ledger Account details", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validate help button on Reports V2 home page
		test_steps.add("========= Validating help button on Reports V2 home page =========");
		try {
			nav.ReportsV2(driver);
			//reports.navigateToReports(driver, test_steps);
			report.clickHelpButton(driver, test_steps);
			driver.close();
			Utility.switchTab(driver, 0);

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Verify Help button on ReportsV2 home page", test_name, "Validating Help button on ReportsV2 home page", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Performance filter on ReportV2 home page
		test_steps.add("========= Validating Performance filter on ReportV2 home page =========");
		try {
			nav.ReportsV2(driver);
			//reports.navigateToReports(driver, test_steps);
			report.validatePerformanceFilter(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Performance filter", "Performance filter", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Accounting filter on ReportV2 home page
		test_steps.add("========= Validating Accounting filter on ReportV2 home page =========");
		try {
			nav.ReportsV2(driver);
			//reports.navigateToReports(driver, test_steps);
			report.validateAccountingFilter(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Accounting filter", "Accounting filter", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Ledger Balances Report header
		test_steps.add("========= Validating Ledger Balances Report header =========");
		try {
			//report.navigateToReports(driver, test_steps);
			report.navigateToLedgerBalancesReport(driver);
			report.validateInRoadImageinLedgerBalanceReport(driver, test_steps);
			report.validateTooltipOfInnRoadImage(driver, test_steps);
			report.validateMainHeaderinLedgerBalanceReport(driver, test_steps);
			report.validateMainHeaderToolTipinLedgerBalanceReport(driver, test_steps);
			report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
			report.validateExcelExportToolTipBeforeRunReport(driver, test_steps);
			report.validatePDFExportToolTipBeforeRunReport(driver, test_steps);
			report.validatePrintToolTipBeforeRunReport(driver, test_steps);
			report.clickOnRunReport(driver);
			report.validateExcelExportIsEnabledAfterRunReport(driver, test_steps);
			report.validatePDFExportIsEnabledAfterRunReport(driver, test_steps);
			report.validatePrinttIsEnabledAfterRunReport(driver, test_steps);
			report.validateExcelExportToolTipAfterRunReport(driver, test_steps);
			report.validatePDFExportToolTipAfterRunReport(driver, test_steps);
			report.validatePrintToolTipAfterRunReport(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Ledger Header details", "Ledger Report Header details",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validate Collapse and Edit functionality on Ledger Balances Report page
		test_steps.add("========= Validating Collapse and Edit functionality on Ledger Balances Report page =========");
		try {
			report.validateCollapseFunctionalityinLedgerBalancesReport(driver, test_steps);
			report.validateEditFunctionalityinLedgerBalancesReport(driver, test_steps);
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Collapse/Edit Functionality",
						"Ledger Report validate Collapse/Edit Functionality", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Customize Detailed View
		test_steps.add("========= Validating Customize Detailed View =========");
		try {
			report.validateSortReportbyToolTip(driver, test_steps);
			report.validateSortReportbyOptions(driver, test_steps);
			report.validateGroupRowsbyToolTip(driver, test_steps);
			report.validateGroupRowsbyOptions(driver, test_steps);
			report.ValidateSelectionOfGivenSortReportByOption(driver, test_steps, SortByOption);
			report.ValidateSelectionOfGivenGroupRowsByOption(driver, test_steps, GroupByOption);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Customized detailed view", "Customized detailed view",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		// Validating all the items availability and tooltip message message validation
		test_steps.add("========= Validating all the items availability and tooltip message message validation =========");
		try {
			
			Assert.assertTrue(report.verifySelectInputAvailability(driver, test_steps), "Failed, Select Input option is not available on Ledger Balances Report page");
			Assert.assertTrue(report.verifyIncludeLedgerAccountAvailability(driver, test_steps), "Failed, Included Ledger Accounts option is not available on Ledger Balances Report page");

			report.validateIncludeLedgerAccountsToolTip(driver, test_steps);
			
			report.validateExcludeZeroBalanceLedgerAccountsAvailability(driver, test_steps);
			report.validateExcludeZeroBalanceLedgerAccountsToolTip(driver, test_steps);
			//report.validateTooltipExcludeZeroBalanceLedgerAccounts(driver, "", test_steps);
			
			report.validateDisplayCustomGeneralLedgerAccountAvailability(driver, test_steps);
			report.validateDisplayCustomGeneralLedgerAccountToolTip(driver, test_steps);
			//report.validateTooltipDisplayCustomGeneralLedgerAccount(driver, "", test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating all itrms availability", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating all itrms availability", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		/*//Validating all Select Input options availability
		test_steps.add("========= Validating all Select Input options availability =========");
		try {
			report.navigateToLedgerBalancesReport(driver);
			for (String type : typesUnique) {
				try {
					report.verifySelectInputOptionAvailability(driver, type, test_steps);
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(test_name) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances eport page UI", test_steps);
						Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					test_steps.add(e.toString());
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}*/
		
		//Validating Select Input options availability and Count
		try {
			report.validateLedgerAccountWithReport(driver, typesUnique, names, types, status, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances eport page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating selecting input options from popup
		test_steps.add("========= Validating selecting input options from popup =========");
		try {
			
			report.validateSelectInputsPopup(driver, "Incidentals", test_steps);
			report.validateSelectInputsPopup(driver, "Payment Method", test_steps);
			report.validateSelectInputsPopup(driver, "Room Charges", test_steps);
			report.validateSelectInputsPopup(driver, "Fees", test_steps);
			report.validateSelectInputsPopup(driver, "Taxes", test_steps);
			report.validateSelectInputsPopup(driver, "Transfers", test_steps);
			
			report.verifySelectInputOption(driver, "Incidentals", "Laundry", test_steps);
			report.verifySelectInputOption(driver, "Incidentals", "Parking Parking", test_steps);
			report.verifySelectInputOption(driver, "Payment Method", "Cash", test_steps);
			report.verifySelectInputOption(driver, "Taxes", "Service Charge", test_steps);
			report.verifySelectInputOption(driver, "Room Charges", "Room Discount", test_steps);
			report.verifySelectInputOption(driver, "Fees", "Fee Adjustment", test_steps);
			
			report.selectAllInputOptions(driver, "Incidentals", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			report.selectAllInputOptions(driver, "Payment Method", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			report.selectAllInputOptions(driver, "Room Charges", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			report.selectAllInputOptions(driver, "Fees", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			report.selectAllInputOptions(driver, "Taxes", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			report.selectAllInputOptions(driver, "Transfers", test_steps);
			report.closePopupSelectInputs(driver, test_steps);
			
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Incidentals", test_steps);
			Wait.wait2Second();
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Payment Method", test_steps);
			Wait.wait2Second();
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Room Charges", test_steps);
			Wait.wait2Second();
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Fees", test_steps);
			Wait.wait2Second();
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Taxes", test_steps);
			Wait.wait2Second();
			report.validatePlusAndCloseIconsOnSelectInputOptionsPopup(driver, "Transfers", test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "Validating Select Input options", "Ledgder Balances report page UI", test_steps);
				Utility.updateReport(e, "Failed to Verify Ledger Balances Report page UI", test_name, "Validating Select Input options", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validating Advanced Options
		test_steps.add("========= Validating Advanced Options =========");
		try {

			report.validateAdvancedOptionsToolTip(driver, test_steps);
			report.validateExpandAllfunctionalityofAdvancedOptions(driver, test_steps);
			report.validateCollapseAllfunctionalityofAdvancedOptions(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Advanced options Heading details",
						"Advanced Options Heading details validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Account Type
		test_steps.add("========= Validating Advanced Options - Account Type =========");
		try {
			
			report.validateAccountTypeToolTip(driver, test_steps);
			report.validateAccountTypeExpandAndCollapseFunctionality(driver, test_steps);
			report.validateAccountTypeClearAllfuntionality(driver, test_steps);
			report.validateAccountTypeSelectAllFunctionality(driver, test_steps);
			report.validateAccountTypeOptions(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Account Type", "Account Type validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Item Status
		test_steps.add("========= Validating Advanced Options - Item Status =========");
		try {
			report.validateItemStatusToolTip(driver, test_steps);
			report.validateItemStatusExpandAndCollapseFunctionality(driver, test_steps);
			report.validateItemStatusSelectAllFunctionality(driver, test_steps);
			report.validateItemStatusClearAllfuntionality(driver, test_steps);
			report.validateItemStatusOptions(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Item Status", "Item Status Validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Include Data From
		test_steps.add("========= Validating Advanced Options - Include Data From =========");
		try {
			report.validateIncludeDataFromToolTip(driver, test_steps);
			report.validateIncludeDataFromExpandAndCollapseFunctionality(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Tax Exempt Ledger Items
		test_steps.add("========= Validating Advanced Options - Tax Exempt Ledger Items =========");
		try {
			report.validateTaxExemptLedgerItemsToolTip(driver, test_steps);
			report.validateTaxExemptLedgerItemsExpandAndCollapseFunctionality(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Tax Exempt Ledger Items",
						"Tax Exempt Ledger Items Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Market Segment
		test_steps.add("========= Validating Advanced Options - Market Segment =========");
		try {
			report.validateMarketSegmentToolTip(driver, test_steps);
			report.validateMarketSegmentExpandAndCollapseFunctionality(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Market Segment", "Market Segment Validation", "ReportsV2",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Reservation Status
		test_steps.add("========= Validating Advanced Options - Reservation Status =========");
		try {
			report.validateReservationStatusToolTip(driver, test_steps);
			report.validateReservationStatusExpandAndCollapseFunctionality(driver, test_steps);
			report.validateReservationStatusClearAllfuntionality(driver, test_steps);
			report.validateReservationStatusSelectAllFunctionality(driver, test_steps);
			report.validateReservationStatusOptions(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Referrals
		test_steps.add("========= Validating Advanced Options - Referrals =========");
		try {
			report.validateReferralsToolTip(driver, test_steps);
			report.validateReferralsExpandAndCollapseFunctionality(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation Status", "Reservation Status Validation",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
	
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyReportsV2LedgerBalances", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
