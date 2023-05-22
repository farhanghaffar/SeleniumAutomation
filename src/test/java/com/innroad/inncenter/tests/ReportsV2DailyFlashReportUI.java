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

public class ReportsV2DailyFlashReportUI extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat, dFormat;
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

	@Test(groups = "ReportsV2")
	public void verifyReportsV2DailyFlashUI()throws InterruptedException, IOException, ParseException {

		test_name = "VerifyReportsV2DailyFlashUI";
		test_description = "ReportsV2 - VerifyReportsV2DailyFlashUI<br>"
				+ "<a href='' target='_blank'>"
				+ "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Daily Flash report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		 String testcaseId="849308|849309|849307|849313|849306|849310";
			
			Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "849308");
			
			
					
		Navigation nav = new Navigation();
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
			loginAutoReportsV2(driver);
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
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, YYYY, EEEE";
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
		
		
		//Validating Default inputs
		try {
			test_steps.add("========= Validating Default inputs =========");
			report.validateDefaultValuesDailyFlashReport(driver, test_steps);
			statusCode.add(0, "1");
			statusCode.add(1, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Default inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Default inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		
		//Validating Effective date
		try {
			test_steps.add("========= Validating Effective date =========");
			report.validateDailyFlashEffectiveDateAvailability(driver, test_steps);
			report.validateDailyFlashEffectiveDateToolTip_RV2(driver, test_steps);
			
			report.validateDailyFlashDefaultDateSelection_RV2(driver, clientTimeZone, dateFormat, test_steps);
			report.validateDailyFlashTomorrowDateSelection_RV2(driver, clientTimeZone, dateFormat, test_steps);
			report.validateDailyFlashYesterdayDateSelection_RV2(driver, clientTimeZone, dateFormat, test_steps);
			report.validateDailyFlashCustomDateSelection_RV2(driver, "29/11/2021", dateFormat, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Effective date", test_description, test_category, test_steps);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Effective date", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Effective date", "ReportV2 - Daily Flash Report",
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
			report.validateDailyFlashAdvancedInputsAvailability(driver, test_steps);
			report.validateDailyFlashAdvancedInputsToolTip_RV2(driver, test_steps);
			Wait.wait1Second();
			report.validateDailyFlashBreakOutTaxExemptRevenueToolTip_RV2(driver, test_steps);
			Wait.wait1Second();
			report.validateDailyFlashExpandAllAdvancedOptions_RV2(driver, test_steps);
			report.validateDailyFlashBreakOutTaxExemptRevenueAvailability(driver, test_steps);
			report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickNoBreakOutTaxExemptRevenue(driver, test_steps);
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Advanced Inputs", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Advanced Inputs", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Advanced Inputs", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		
		//Validating Return to Defaults
		try {
			test_steps.add("========= Validating Return to Defaults =========");
			
			report.validateReturnToDefaultAvailabilityDailyFlash(driver, test_steps);
			report.validateToastMessageReturnToDefaultDailyFlash(driver, test_steps);
			report.validateReturnToDefaultDailyFlash(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Return to Defaults", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Return to Defaults", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Return to Defaults", "ReportV2 - Daily Flash Report",
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
			test_steps.add("========= Validating Header and Run Report button =========");

			report.validateDailyFlashReportInMainHeader(driver, test_steps);
			
//			report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
//			report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
//			report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
			
			report.validateRunReporExistenceinMainHeader(driver, test_steps);
			report.validateRunReporExistenceAtPageBottom(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Header and Run Report button", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Header and Run Report button", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Header and Run Report button", "ReportV2 - Daily Flash Report",
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
			test_steps.add("========= Validating Collapse and Edit =========");

			report.validateCollapseFunctionalityDailyFlash(driver, test_steps);
			report.validateEditFunctionalityDailyFlash(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Collapse and Edit", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Collapse and Edit", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Collapse and Edit", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		
		
		//Validating Grey bar after clicking Run Report
		try {
			test_steps.add("========= Validating Grey bar after clicking Run Report =========");

			report.validateGreyBarAfterClickingRunReportDailyFlash(driver, test_steps);
			
			statusCode.add(0, "1");
			comments.add(0, "UI is verified");
			statusCode.add(1, "1");
			comments.add(1, "UI is verified");
			statusCode.add(2, "1");
			comments.add(2, "UI of daily flash is verified");
			statusCode.add(3, "1");
			comments.add(3, "UI of daily flash is verified");
			statusCode.add(4, "1");
			comments.add(4, "UI of daily flash is verified");
						
			statusCode.add(5, "1");
			comments.add(5, "UI of daily flash is verified");
						
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Grey bar after clicking Run Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Grey bar after clicking Run Report", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Grey bar after clicking Run Report", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
/*		//Validating Loading state after clicking on Run Report
		try {
			test_steps.add("========= Validating Loading state after clicking on Run Report =========");

			report.validateLoadingStateDailyFlash(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Loading state after clicking on Run Report", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Loading state after clicking on Run Report", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Loading state after clicking on Run Report", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
		
		
		//Validating Export Loading message and Export Success/Failure Toast Messages
		try {
			test_steps.add("========= Validating Export Loading message and Export Success/Failure Toast Messages =========");

			report.validateExportLoadingMessageDailyFlash(driver, test_steps);
			report.validateExportSuccessFailureMessageDailyFlash(driver, test_steps);
						
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Validating Export Loading message and Export Success/Failure Toast Messages", test_description, test_category, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Export Loading message and Export Success/Failure Toast Messages", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Validating Export Loading message and Export Success/Failure Toast Messages", "ReportV2 - Daily Flash Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}
*/		
	
	}
	

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}
	
}
