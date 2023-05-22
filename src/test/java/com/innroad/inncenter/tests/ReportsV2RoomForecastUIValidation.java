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

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2RoomForecastUIValidation extends TestCore{

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat, dFormat;
	String propertyNameHomePage, propertyNameRoomForecastPage;
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
		
	}
	
	@Test(dataProvider = "getData", groups= "ReportsV2")
	public void validateRoomForecastUI(String url, String clientId, String userName, String password)
			throws Throwable {

		test_name = "ReportsV2RoomForecastUIValidation";
		test_description = "Room Forecast Report -VerifyRoomForecastReportV2UI <br>";
		test_category = "ReportsV2 - RoomForecast UI report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		Login login = new Login();
		ReportsV2 report = new ReportsV2();
		Admin admin = new Admin();
		
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
				dateFormat = "MMM dd, yyyy";
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, yyyy";
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
			test_steps.add("========= Navigating to Room Forecast Report =========");
			nav.ReportsV2(driver);
			propertyNameHomePage = report.getSelectedPropertyNameFromReportsV2HomePage(driver, test_steps);
			report.navigateToRoomForecastReport(driver);
			
			app_logs.info("Navigated to Room Forecast Report page");
			test_steps.add("Navigated to Room Forecast Report page");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigating to Daily Flash Report page", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Input Defaults
		try {
			test_steps.add("========= Validating Input Defaults - Room Forecast =========");

			report.validateDefaultValuesRoomForecastReport(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Input Defaults - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Input Defaults", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Input Defaults", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Header and Run Report button
		try {
			test_steps.add("========= Validating Header and Run Report button - Top Sticky Nav=========");

			report.validateRoomForecastReportInMainHeader(driver, test_steps);
			
//			report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
//			report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
//			report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
			report.validateHelpButonInAllReportPages(driver, test_steps);
			
			report.validateRunReporExistenceinMainHeader(driver, test_steps);
			report.validateRunReporExistenceAtPageBottom(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Header and Run Report button - Top Sticky Nav", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Header and Run Report button", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Header and Run Report button", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Collapse and Edit
		try {
			test_steps.add("========= Validating Collapse and Edit - Sticky Input Bar=========");

			report.validateCollapseFunctionalityRoomForecast(driver, test_steps);
			report.validateEditFunctionalityRoomForecast(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Collapse and Edit - Sticky Input Bar", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Collapse and Edit", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Collapse and Edit", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Property Selector
		try {
			test_steps.add("========= Validating Property selector =========");

			propertyNameRoomForecastPage = report.getSelectedPropertyNameFromReportPage(driver, test_steps);
			
			if (propertyNameRoomForecastPage.equalsIgnoreCase(propertyNameHomePage)) {
				app_logs.info("Success - Property Selector validation");
				test_steps.add("Success - Property Selector validation");
			}else {
				app_logs.info("Failed - Property Selector validation");
				test_steps.add("AssertionError Failed - Property Selector validation");
			}
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Property selector", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Property selector", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Property selector", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Choose Date Range
		try {
			test_steps.add("========= Validating Choose Date Range =========");
			
			report.validateChooseDateRangeAvailability(driver, test_steps);
			report.validateStayOnDateRangeAvailabilityRoomForecast(driver, test_steps);
			report.validateRoomForecastChooseDateRangeToolTip(driver, test_steps);
			
			report.validateDefaultValueChooseDateRangeRoomForecast(driver, clientTimeZone, dateFormat, test_steps);
			report.validateAllDateRangesRoomForecast(driver, clientTimeZone, dateFormat, startDayOfWeek, test_steps);
			
			driver.navigate().refresh();
			report.validateCustomRangeDateDefault(driver, test_steps);
			driver.navigate().refresh();
			Wait.wait10Second();
			report.validateDifferentDateFormat(driver, test_steps);
			
			driver.navigate().refresh();
			Wait.wait5Second();
			report.validateCustomDateRnge(driver, "12/11/2020", "25/01/2021", dFormat, test_steps);
			
			
			
						
//			report.validateDailyFlashDefaultDateSelection(driver, clientTimeZone, dateFormat, test_steps);
//			report.validateDailyFlashTomorrowDateSelection(driver, clientTimeZone, dateFormat, test_steps);
//			report.validateDailyFlashYesterdayDateSelection(driver, clientTimeZone, dateFormat, test_steps);
//			report.validateDailyFlashCustomDateSelection(driver, "29/11/2021", dateFormat, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Choose Date Range", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Choose Date Range", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Choose Date Range", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Select Inputs
		try {
			test_steps.add("========= Validating Select Inputs =========");
			
			report.validateSelectInputsAvailabilityRoomForecast(driver, test_steps);
			report.validateBreakOutByAvailabilityRoomForecast(driver, test_steps);
			report.validateRoomForecastBreakOutByToolTip(driver, test_steps);
			report.validateBreakOutByOptionsAvailabilityRoomForecast(driver, test_steps);
			report.validateBreakOutByOptionSelectionRoomForecast(driver, "Market Segment", test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Select Inputs", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Customize Detailed View
		try {
			test_steps.add("========= Validating Customize Detailed View =========");
			
			report.validateCustomizeDetailedViewAvailabilityRoomForecast(driver, test_steps);
			report.validateGroupRowsByAvailabilityRoomForecast(driver, test_steps);
			report.validateRoomForecastGroupRowsByToolTip(driver, test_steps);
			report.validateGroupRowsbyOptionsRoomForecast(driver, test_steps);
			report.validateGroupRowsbyOptionSelectionRoomForecast(driver, "Month", test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Customize Detailed View", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Customize Detailed View", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Advanced Inputs
		try {
			test_steps.add("========= Validating Advanced Inputs =========");
			
			report.validateAdvancedInputsAvailabilityRoomForecast(driver, test_steps);
			report.validateAdvancedInputsToolTipRoomForecast(driver, test_steps);
			report.validateIncludePerformanceMetricsAvailabilityRoomForecast(driver, test_steps);
			report.validateIncludePerformanceMetricsToolTipRoomForecast(driver, test_steps);
			report.validateIncludeGroupReservationsAvailabilityRoomForecast(driver, test_steps);
			report.validateIncludeGroupReservationsToolTipRoomForecast(driver, test_steps);
			
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Advanced Inputs - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Advanced Inputs", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Return to Default - Room Forecast Report
		try {
			test_steps.add("========= Validating Return to Default - Room Forecast Report =========");
			
			report.validateReturnToDefaultAvailabilityRoomForecast(driver, test_steps);
			report.validateToastMessageReturnToDefaultRoomForecast(driver, test_steps);
			report.validateReturnToDefaultRoomForecast(driver, test_steps);
			
			
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Return to Default - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Return to Default", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Grey bar after clicking Run Report - Room Forecast Report
		try {
			test_steps.add("========= Validating Grey bar after clicking Run Report - Room Forecast Report =========");
			
			report.validateGreyBarAfterClickingRunReportRoomForecast(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Grey bar after clicking Run Report - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Grey bar after clicking Run Report", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		//Validating Loading state after clicking on Run Report - Room Forecast Report
		try {
			test_steps.add("========= Validating Loading state after clicking on Run Report - Room Forecast Report =========");
			
			report.validateLoadingStateRoomForecast(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Loading state after clicking on Run Report - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Loading state after clicking on Run Report", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
/*		//Validating Export Loading message and Export Success/Failure Toast Messages - Room Forecast Report
		try {
			test_steps.add("===== Validating Export Loading message and Export Success/Failure Toast Messages - Room Forecast Report =====");
			
			report.validateExportLoadingMessageRoomForecast(driver, test_steps);
			report.validateExportSuccessFailureMessageRoomForecast(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Export Loading message and Export Success/Failure Toast Messages - Room Forecast Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Select Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Export Loading message and Export Success/Failure Toast Messages", "ReportV2 - Room Forecast Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}*/
		
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2LedgerBalancesUI", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}


}