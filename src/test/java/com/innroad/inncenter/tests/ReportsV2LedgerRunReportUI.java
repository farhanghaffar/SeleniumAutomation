package com.innroad.inncenter.tests;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerRunReportUI extends TestCore {
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	@Test(dataProvider = "getData", groups= "ReportsV2")
	public void validateReportsV2()
			throws InterruptedException, IOException {

		

		test_name = "LedgerRunReport";
		test_description = "LedgerRunReport <br>";
		test_category = "ReportsV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");


		Login login = new Login();
		ReportsV2 report = new ReportsV2();
		Navigation nav = new Navigation();
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				loginReportsV2(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginReportsV2(driver);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		Elements_Reports res = new Elements_Reports(driver);
		
		try {
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			
			res.buttonSeeAll.click();
			Wait.wait1Second();
			res.clickSelectAll.click();
			Wait.wait1Second();
			res.buttonSavePopup.click();
			Wait.wait1Second();
			res.RunReport.click();
			
			test_steps.add("==================  Validating Cancel Report Functionality ==================");
			report.validateCancelReportFunctionality(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Cancel Report Functionality", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Cancel Report Functionality", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		try {
			
			Wait.wait3Second();
			test_steps.add("==================  Validating No Report Toaster Message ==================");
			res.buttonClearAll.click();
			Wait.wait1Second();
			res.RunReport.click();
			report.validateNoReportDataMessage(driver, test_steps);
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating No Report Toaster Message", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating No Report Toaster Message", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {
			Wait.wait5Second();
			
			test_steps.add("==================  Validating Filter Header Grey Ribbon ==================");
			Utility.clickThroughJavaScript(driver, res.buttonSeeAll);
			Wait.wait5Second();
			Utility.clickThroughAction(driver, res.clickSelectAll);
			Utility.clickThroughAction(driver, res.buttonSavePopup);
			//res.buttonSavePopup.click();
			Wait.wait5Second();
			//report.selectDateRange(driver, "Last Week", test_steps);
			Wait.wait2Second();
			res.RunReport.click();

			report.validateFilterHeader(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Filter Header Grey Ribbon", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Filter Header Grey Ribbon", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {

			test_steps.add("==================  Validating Standard View ==================");
			
			report.validateStandardReportHeaderExistence(driver, test_steps);
			
			report.validateReportTypeCaptionExistence(driver, test_steps);
			report.validateReportTypeValue(driver, test_steps);
			
			report.validateDateRangeCaptionExistence(driver, test_steps);
			report.validateDateRangeValue(driver, test_steps);
			
			report.validateIncludedLedgerAccountsCaptionExistence(driver, test_steps);
			report.validateLedgerAccountsInStandardView(driver, test_steps);

			report.validateReservationStatusCaptionexistence(driver, test_steps);
			report.validateReservationStatusValue(driver, test_steps);
			
			report.validateAccountTypeCaptionexistence(driver, test_steps);
			report.validateAccountTypeValue(driver, test_steps);
			
			report.validateGeneratedOnCaptionExistence(driver, test_steps);
			report.validateGeneratedOnValue(driver, test_steps);
							
			report.validateIncludeDataFromCaptionExistence(driver, test_steps);
			report.validateIncludeDataFromValue(driver, test_steps);
			
			report.validateSortReportByCaptionExistence(driver, test_steps);
			report.validateSortReportByValue(driver, test_steps);
			
			report.validateGroupRowstByCaptionExistence(driver, test_steps);
			report.validateGroupsByValue(driver, test_steps);
			
			report.validateItemStatusCaptionexistence(driver, test_steps);
			report.validateItemStatusValue(driver, test_steps);
			
			report.validateTaxExemptCaptionExistence(driver, test_steps);
			report.validateTaxExemptmValue(driver, test_steps);
					
			report.validateMarketSegmentCaptionExistence(driver, test_steps);	
			report.validateMarketSegmentValue(driver, test_steps);
			
			report.validateReferralsCaptionExistence(driver, test_steps);
			report.validateReferralsValue(driver, test_steps);
			
			report.validateExcludeZeroBalanceLedgerAccountsCaptionExistence(driver, test_steps);
			report.validateExcludeZeroBalanceLedgerAccountsValue(driver, test_steps);
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Standard View", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Standard View", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {
			
			test_steps.add("==================  Validating Summary View ==================");
			report.validateSummaryViewHeaderExistence(driver, test_steps);
			report.validateLedgerAccountCategoryExistence(driver, test_steps);
			report.validateLedgerAccountCategoryBalanceExistence(driver, test_steps);
			report.validateLedgerAccountCategoryTotalCaptionExistence(driver, test_steps);
			report.validateLedgerAccountCategoryToolTip(driver, test_steps);
			report.validateLedgerScrollTopFunctionality(driver, test_steps);
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Summary View", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Summary View", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {
			
			test_steps.add("================== Validating All Reservations tooltips ==================");
			report.ValidateAllReservationstooltips(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating All Reservations tooltips", test_description, test_category, test_steps);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating All Reservations tooltips", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {
			
			test_steps.add("================== Validating All Transactions tooltips ==================");
			report.ValidateAllTransactionstooltips(driver, test_steps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating All Transactions tooltips", test_description, test_category, test_steps);
			
			} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating All Transactions tooltips", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		try {
/*			
			test_steps.add("================== Validating Sort functionality ==================");
			try {
				res.Edit.click();
			}catch(Exception e) {
				
			}
			report.validateReportSortFunctionality(driver, test_steps);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Sort functionality", test_description, test_category, test_steps);
*/			
			} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Sort functionality", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Sort functionality", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate standard view report", "standard view report Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}
		
		
		
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("LedgerRunReport", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}

	

}
