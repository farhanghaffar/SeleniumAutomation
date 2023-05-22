package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2HomePageValidation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> reportsAccess = new ArrayList<>();
	ArrayList<String> reports = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyUserWithReportsV2Entilements(String url, String clientId, String userName, String password,
			String inputReportSearch) throws InterruptedException, IOException, ParseException {

		test_name = "ReportsV2HomePageValidation";
		test_description = "ReportsV2 - Home Page Validation<br>"
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

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//login.login(driver, envURL, clientId, userName, password);
			loginReportsV2(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
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
		
		// Validating Reports V2 Search option
		test_steps.add("======= Validating Reports search option in Reports V2 page ========");

		try {
			nav.ReportsV2(driver);
			ArrayList<String> ReportsActual;
			ArrayList<String> ReportsExpected;
			ReportsActual = ReportsExpected = admin.gettingResultsFromReportsV2Search(driver, inputReportSearch,
					test_steps);
			Collections.sort(ReportsExpected);

			test_steps.add("Before Sort" + ReportsActual);
			app_logs.info("Before Sort" + ReportsActual);

			test_steps.add("After Sort" + ReportsExpected);
			app_logs.info("After Sort" + ReportsExpected);

			if (ReportsActual.equals(ReportsActual)) {
				test_steps.add("Successfully validated Search result options and they are showing in ascending order");
				app_logs.info("Successfully validated Search result options and they are showing in ascending order");
			} else {
				Assert.assertTrue(false, "Search result options are not showing in ascending order");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify Search functionality in Reports V2 page", test_name,
						"New Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Validating Reports V2 Search option with spaces

		try {
			ArrayList<String> ReportsActual;
			ArrayList<String> ReportsExpected;
			nav.ReportsV2(driver);
			ReportsActual = ReportsExpected = admin.gettingResultsFromReportsV2Search(driver, "   Daily Flash   ",
					test_steps);
			Collections.sort(ReportsExpected);

			test_steps.add("Before Sort" + ReportsActual);
			app_logs.info("Before Sort" + ReportsActual);

			test_steps.add("After Sort" + ReportsExpected);
			app_logs.info("After Sort" + ReportsExpected);

			if (ReportsActual.equals(ReportsActual)) {
				test_steps.add(
						"Successfully validated Search result options with spaces and they are showing in ascending order"
								+ "<br>" + "<a href='https://innroad.atlassian.net/browse/RPT-175'>"
								+ "Click here to open JIRA: RPT-175</a>");
				app_logs.info(
						"Successfully validated Search result options with spaces and they are showing in ascending order");
			} else {
				Assert.assertTrue(false,
						"Search result options with spaces are not showing in ascending order" + "<br>"
								+ "<a href='https://innroad.atlassian.net/browse/RPT-175'>"
								+ "Click here to open JIRA: RPT-175</a>");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating ReportsV2 Home Page Search", test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating ReportsV2 Home Page Search", "ReportV2 Home page", "Reports V2 Home page", test_steps);
				Utility.updateReport(e, "Failed to Verify Search functionality in Reports V2 page", test_name,
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		// validating not duplicate Report names
		try {
			report.validateNoDuplicateofReportsNames(driver, test_steps);
			//report.validateAllAdvancedpOptionsdefaultCollapseText(driver, test_steps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating ReportsV2 Home Page Duplicate names availabilty", test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verif duplicate report names", test_name, "New Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		//Validate help button on Reports V2 home page
		test_steps.add("========= Validating help button on Reports V2 home page =========");
		try {
			//nav.ReportsV2(driver);
			//reports.navigateToReports(driver, test_steps);
			report.clickHelpButton(driver, test_steps);
			driver.close();
			Utility.switchTab(driver, 0);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating ReportsV2 Home Page Help button", test_description, test_category, test_steps);

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
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating ReportsV2 Home Page Filter functionality", test_description, test_category, test_steps);

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
		
		
		test_steps.add("=========   validate Help button- should not available in other react pages ===================");
		try {
			
			report.verifyHelpbuttonAvailabilityInAllNonAspxPages(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Help button should not be available other React pages", test_description, test_category, test_steps);
			
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
		
		// Validating Navigation from different modules to Reports V2 page
		test_steps.add("======= Navigating from different modules to Reports V2 page ========");

		try {
			// nav.Admin(driver);
			// admin.navigateToNewRole(driver, test_steps);
			admin.verifyNavigationToReportsV2(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Navigating to ReportV2 page from all other module pages", test_description, test_category, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, "New Role", "Reports V2 Entitlements", test_steps);
				Utility.updateReport(e, "Failed to Verify Navigating to Reports V2 page from other modules", test_name,
						"New Role", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2HomePageValidation", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}

}
