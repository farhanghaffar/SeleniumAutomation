package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

import org.openqa.selenium.By;
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
import com.innroad.inncenter.properties.OR_Reports;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2DailyFlashAfterRunReportUI extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat, dFormat, dateAndTimeFormat, currentDateAndTime, currentDateAndTimeGuestCount, dateAndTimeForGuestCount;
	String propertyNameHomePage;
	String dateRangeSelected, expBreakOutTaxExempt;
	
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void verifyReportsV2DailyFlashAfterRunReportUI()throws InterruptedException, IOException, ParseException {

		test_name = "VerifyReportsV2DailyFlashAfterRunReportUI";
		test_description = "ReportsV2 - DailyFlashAfterRunReportUI<br>"
				+ "<a href='' target='_blank'>"
				+ "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Daily Flash report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		caseId.add("849310");
		statusCode.add("5");
		caseId.add("849313");
		statusCode.add("5");
		
		Navigation nav = new Navigation();
		ReportsV2 report = new ReportsV2();
		Admin admin = new Admin();
		Elements_Reports reportPage = new Elements_Reports(driver);
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginReportsV2(driver);
			//login.login(driver, envURL, clientId, userName, password);
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
		
		//Getting client details
		try {
			app_logs.info("==== Getting Client details =====");
			nav.admin(driver);
			nav.Clientinfo(driver);
			
			admin.clickClientName(driver);
			admin.clickClientOption(driver);
			
			startDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
			clientTimeZone = admin.getClientTimeZone(driver, test_steps);
			dFormat = admin.getClientDateFormat(driver);
			
			app_logs.info("Client Start day of the week: "+startDayOfWeek);
			app_logs.info("Client time Zone: "+clientTimeZone);
			app_logs.info("Client Date format: "+dFormat);
			
			switch (clientTimeZone) {
			case "(GMT-05:00) Eastern Time (US and Canada)":
				clientTimeZone = "US/Eastern";
				break;
				
			case "(GMT-06:00) Central Time (US and Canada":
				clientTimeZone = "US/Central";
				break;
				
			case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
				clientTimeZone = "Europe/London";
				break;

			default:
				break;
			}
			
			if (dFormat.equalsIgnoreCase("USA")) {
				dateFormat = "MMM dd, YYYY, EEEE";
				dateAndTimeFormat = "MMM dd, yyyy | hh:mm a";
				dateAndTimeForGuestCount = "MMM dd, yyyy - hh:mm a";
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, YYYY, EEEE";
				dateAndTimeFormat = "dd MMM, yyyy | hh:mm a";
				dateAndTimeForGuestCount = "dd MMM, yyyy - hh:mm a";
			}
			app_logs.info("Client Start day of the week: "+startDayOfWeek);
			app_logs.info("Client time Zone: "+clientTimeZone);
			app_logs.info("Client Date Format: "+dateFormat);
			
			nav.Reservation_Backward(driver);
					
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Client details", "Client Info",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
		//Navigate to Reports page
		try {
			test_steps.add("========= Navigating to Daily Flash Report =========");
			nav.ReportsV2(driver);
			propertyNameHomePage = report.getSelectedPropertyNameFromReportsV2HomePage(driver, test_steps);
			report.navigateToDailyFlashReport(driver);
			
			app_logs.info("Navigated to Daily Flash Report page");
			test_steps.add("Navigated to Daily Flash Report page");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigating to Daily Flash Report page", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Standard Header
		try {
			test_steps.add("========= Validating Standard Header =========");
			//report.selectDateRange(driver, "Today", test_steps);
			Wait.WaitForElement(driver, OR_Reports.dateDailyFlash);
			dateRangeSelected = report.getDailyFlashDay(driver) +" | "+ report.getDailyFlashDate(driver);
			//dateRangeSelected = driver.findElement(By.xpath("//input[contains(@placeholder,'Select date')]")).getAttribute("value");
			expBreakOutTaxExempt = "No";

			report.clickOnRunReport(driver);
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateAndTimeFormat);
			LocalDateTime now = LocalDateTime.now();

			currentDateAndTime = dtf.format(now);
			currentDateAndTimeGuestCount = dtf.format(now);
			
			report.validateStandardHeaderTitle(driver, "Daily Flash Report", propertyNameHomePage, test_steps);
			report.validateStandardHeaderDailyFlash(driver, dateRangeSelected, currentDateAndTime, expBreakOutTaxExempt, test_steps);
			statusCode.add(0, "1");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Standard Header - Daily Flash Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Standard Header", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Property Performance
		try {
			test_steps.add("========= Validating Property Performance =========");
			report.validatePropertyPerformanceHeader(driver, test_steps);
			report.validateRevenueTypesTableUI(driver, test_steps);
			report.validatePaymentsMethodTypesTableUI(driver, test_steps);
			report.validateNetChangesTableUI(driver, test_steps);
			report.validatePropertyStatisticsTableUI(driver, test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Property Performance UI - Daily Flash Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Property Performance UI", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Property Performance", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Guest Count Summary
		try {
			test_steps.add("========= Validating Guest Count Summary =========");
			report.validateGuestCountSummaryHeader(driver, test_steps);
			report.validateGuestCountStatisticsTableTitle(driver, currentDateAndTimeGuestCount, test_steps);
			report.validateGuestCountStatisticsTableUI(driver, test_steps);			
			statusCode.add(0, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Guest Count Summary UI - Daily Flash Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Guest Count Summary UI", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Guest Count Summary", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating All Tables Date Tooltips
		try {
			test_steps.add("========= Validating All Tables Date Tooltips =========");
			test_steps.add("========= Validating Revenue Type Table - Dates Tooltips =========");
			report.validateDailyFlashReportToolTips(driver, "Revenue Types", test_steps);			
			
			test_steps.add("========= Validating Payments Method Types Table - Dates Tooltips =========");
			report.clickOnRunReport(driver);
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			Wait.WaitForElement(driver, OR_Reports.textStandardViewHeader);
			report.validateDailyFlashReportToolTips(driver, "Payments Method Types", test_steps);
			
			test_steps.add("========= Validating Net Changes Table - Dates Tooltips =========");
			report.clickOnRunReport(driver);
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			Wait.WaitForElement(driver, OR_Reports.textStandardViewHeader);
			report.validateDailyFlashReportToolTips(driver, "Net Changes", test_steps);
			
			test_steps.add("========= Validating Property Statistics Table - Dates Tooltips =========");
			report.clickOnRunReport(driver);
			try {
				String noReport = "//*[contains(text(),'No Report Data Available')]";
				for (int i = 0; i < 10; i++) {
					if (driver.findElement(By.xpath(noReport)).isDisplayed()) {
						app_logs.info("Got No Report Data message");
						Wait.wait2Second();
						report.clickOnRunReportBottom(driver);
					}else {
						break;
					}
				}
			}catch (Exception e) {
				
			}
			Wait.WaitForElement(driver, OR_Reports.textStandardViewHeader);
			report.validateDailyFlashReportToolTips(driver, "Property Statistics", test_steps);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating All Tables Date Tooltips - Daily Flash Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating All Tables Date Tooltips", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to All Tables Date Tooltips", "ReportV2 - Daily Flash Report",
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
		return Utility.getData("", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
	
}
