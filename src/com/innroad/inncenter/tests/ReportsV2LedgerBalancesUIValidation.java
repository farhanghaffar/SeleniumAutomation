package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerBalancesUIValidation extends TestCore{

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	@Test(dataProvider = "getData", groups= "ReportsV2")
	public void validateReportsV2(String url, String clientId, String userName, String password)
			throws Throwable {

		test_name = "ReportsV2LedgerBalancesUIValidation";
		test_description = "Ledger Balances -VerifyLedgerBalancesReportV2UI <br>";
		test_category = "ReportsV2 - Ledger Balances report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		ReportsV2 report = new ReportsV2();
		
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
				//login.login(driver, envURL, clientId, userName, password);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "riverrock", "manogna", "Innroad@123");
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
		
		//Validating Ledger Balances Report header
		test_steps.add("========= Validating Ledger Balances Report header =========");
		try {
			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			//report.validateInRoadImageinLedgerBalanceReport(driver, test_steps);
			//report.validateTooltipOfInnRoadImage(driver, test_steps);
			report.validateMainHeaderinLedgerBalanceReport(driver, test_steps);
			report.validateMainHeaderToolTipinLedgerBalanceReport(driver, test_steps);
			report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
			//report.validateExcelExportToolTipBeforeRunReport(driver, test_steps);
			//report.validatePDFExportToolTipBeforeRunReport(driver, test_steps);
			//report.validatePrintToolTipBeforeRunReport(driver, test_steps);
			report.validateRunReporExistenceinMainHeader(driver, test_steps);
			report.validateRunReporExistenceAtPageBottom(driver, test_steps);
			report.validateNameAndDescriptioninSecondaryHeader(driver, test_steps);
			report.clickOnRunReport(driver);
			Wait.wait2Second();
			if(Utility.isElementDisplayed(driver, By.xpath("//div[@class='ant-notification-notice-description']"))) 
			{
				report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
				report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
				report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
				//report.validateExcelExportToolTipBeforeRunReport(driver, test_steps);
				//report.validatePDFExportToolTipBeforeRunReport(driver, test_steps);
				//report.validatePrintToolTipBeforeRunReport(driver, test_steps);	
			}
			else 
			{   
				report.validateExcelExportIsEnabledAfterRunReport(driver, test_steps);
				report.validatePDFExportIsEnabledAfterRunReport(driver, test_steps);
				report.validatePrinttIsEnabledAfterRunReport(driver, test_steps);
				//report.validateExcelExportToolTipAfterRunReport(driver, test_steps);
				//report.validatePDFExportToolTipAfterRunReport(driver, test_steps);
				//report.validatePrintToolTipAfterRunReport(driver, test_steps);
			}

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
			Elements_Reports res = new Elements_Reports(driver);
			Wait.explicit_wait_elementToBeClickable(res.Edit, driver);
			Utility.clickThroughAction(driver, res.Edit);
			
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
			//report.validateSortReportbyToolTip(driver, test_steps);
			report.validateSortReportbyOptions(driver, test_steps);
			report.validateGroupRowsbyToolTip(driver, test_steps);
			report.validateGroupRowsbyOptions(driver, test_steps);
			report.ValidateSelectionOfGivenSortReportByOption(driver, test_steps, "Date");
			report.ValidateSelectionOfGivenGroupRowsByOption(driver, test_steps, "Day");
			
			//RetryFailedTestCases.count = Utility.reset_count;
			//Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			
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
		
		
		test_steps.add("========= Validate choose date range ===================");
		try {
			
			driver.navigate().refresh();
			Wait.wait10Second();
			report.validateAllDateRanges(driver, test_steps);
			driver.navigate().refresh();
			report.validateCustomRangeDateDefault(driver, test_steps);
			driver.navigate().refresh();
			Wait.wait10Second();
			report.validateDifferentDateFormat(driver, test_steps);
			
			driver.navigate().refresh();
			Wait.wait5Second();
			report.validateCustomDateRnge(driver, "15/09/2020", "25/09/2020", test_steps);

					
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Shift Time in Include Data Form", "Include Data Form",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		test_steps.add("=========   Return to Default ===================");
		try {
			
			driver.navigate().refresh();
			
			report.validateReturnToDefaultAvailability(driver, test_steps);
			report.validateReturnToDefault(driver, test_steps);
			report.validateReturnToDefaultAll(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Shift Time in Include Data Form", "Include Data Form",
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
		return Utility.getData("ReportsV2LedgerBalancesUI", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}


}