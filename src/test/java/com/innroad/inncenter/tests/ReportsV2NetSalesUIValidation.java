package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2NetSalesUIValidation extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	String startDayOfWeek;
	String clientTimeZone;
	String dateFormat, dFormat;

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test
	public void validateReportsV2() throws Throwable {

		test_name = "ReportsV2NetSalesUIValidation";
		test_description = "Net Sales -VerifyNetSalesReportV2UI <br>";
		test_category = "ReportsV2 - Net Sales report";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

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

		// Navigate to Reports page
		try {
			test_steps.add("Navigation to Reports Page ");
			nav.ReportsV2(driver);
			report.navigateToNetSalesReport(driver,test_steps);

			app_logs.info("Navigated to Net Sales Report page");
			test_steps.add("Navigated to Net Sales Report page");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigating to Net Sales Report page", "ReportV2 - Net Sales Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}

		// Validating default input and run to default

		try {
			test_steps.add("========= Validating default input before run report =========");

			report.validateDefaultInput(driver, clientTimeZone, dateFormat, "Month To Date", "Channel", 
					"Total Revenue", "Day", "All", test_steps);
			test_steps.add("========= Selecting others value for verifying return to defult input =========");

			test_steps.add("Select different value and click on run report");
			report.selectDateRange(driver, "", "", "Last Month", test_steps);
			test_steps.add("Select Group Net Sales By Option : "+"Last Month");
			report.selectGroupNetSalesByOption(driver, test_steps, "State");
			report.selectSortReportByOption(driver, test_steps, "Average Stay");
			report.selectGroupRowsByOption(driver, test_steps, "Week");
			report.selectBookedOnDateRange(driver, test_steps, "Today");
			report.radioYesBtnIncludeInactive(driver, test_steps);
			report.clickOnRunReport(driver);
			test_steps.add("Click on run report");
			Wait.wait15Second();
			report.clickOnReturnToDefaultButton(driver);
			test_steps.add("Click on Return to default");
			test_steps.add("========= Validating default input after clicked on return to default input =========");
			report.validateDefaultInput(driver, clientTimeZone, dateFormat, "Month To Date", "Channel", 
					"Total Revenue", "Day", "All", test_steps);



		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Navigating to Net Sales Report page", "ReportV2 - Net Sales Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}

		// Validating Header and Run Report button
		try {
			test_steps.add("========= Validating Header and Run Report button =========");
			report.validateInRoadImageinLedgerBalanceReport(driver, test_steps);
			report.validateTooltipOfInnRoadImage(driver, test_steps);
			report.validateNetSalesReportInMainHeader(driver, test_steps);
			report.validateMainHeaderToolTipinNetSaleReport(driver, test_steps);
			report.validateExcelExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePDFExportIsDisabledBeforeRunReport(driver, test_steps);
			report.validatePrinttIsDisabledBeforeRunReport(driver, test_steps);
			report.validateRunReporExistenceinMainHeader(driver, test_steps);
			report.validateRunReporExistenceAtPageBottom(driver, test_steps);
			Wait.wait3Second();
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating Header and Run Report button", test_description, test_category,
						test_steps);
				Utility.updateReport(e, "Failed to Validating Header and Run Report button",
						"ReportV2 - Net Sales Report", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			Utility.updateReport(e, "Failed to Validating Header and Run Report button",
					"ReportV2 - Net Sales Report", "ReportsV2", driver);
			}
			else {
				Assert.assertTrue(false);
			}
		}

		// Validate Collapse and Edit functionality on Ledger Balances Report page
		test_steps.add("========= Validating Collapse and Edit functionality on Net Sales Report page =========");
		try {
			report.validateCollapseFunctionalityinLedgerBalancesReport(driver, test_steps);
			report.validateEditFunctionalityinLedgerBalancesReport(driver, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Collapse/Edit Functionality",
						"Net Sales Report validate Collapse/Edit Functionality", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Validating Advanced Inputs
		try {
			test_steps.add("========= Validating Advanced Inputs =========");
			report.validateNetSalesAdvancedInputsAvailability(driver, test_steps);
			report.validateNetSalesAdvancedInputsToolTip(driver, test_steps);
			Wait.wait1Second();
			report.validateNetSalesBookedOnDateRangeToolTip(driver, test_steps);

			Wait.wait1Second();
			report.validateIncludeInactiveObsoleteRoomsToolTip(driver, test_steps);
			Wait.wait1Second();
			report.validateNetSalesExpandAllAdvancedOptionsForIncludeInactiveObsoleteRooms(driver, test_steps);
			report.clickYesBreakOutTaxExemptRevenue(driver, test_steps);
			report.clickNoBreakOutTaxExemptRevenue(driver, test_steps);

		} catch (Exception e) {
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

		test_steps.add("========= Validate choose date range ===================");
		try {

			report.validateChooseDateRangeAvailabilityNSR(driver, test_steps);
			report.validateAllDateRangesForNetSaleReport(driver, startDayOfWeek, clientTimeZone, dateFormat, dFormat, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport("Validating choose date range", test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Validating choose date range", "Net Sales Report",
						"ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Validating Select Inputs
		test_steps.add("========= Validating Select Inputs =========");
		try {
			
			test_steps.add("Verify Group Net Sales by Option ToolTip Text");
			report.validateGroupNetSalesByToolTipInNetSalesReport(driver, test_steps);
			test_steps.add("Verify Group Net Sales by Option DropDown Items");
			report.validateGroupNetSalesByOptionsForNetSalesReport(driver, test_steps);
			test_steps.add("Verify Selection of Group Net Sales by Option");
			report.ValidateSelectionOfGivenGroupNetSalesByOption(driver, test_steps, "Guest Profile Account");

			test_steps.add("Verify Sort Report by Option ToolTip Text");
			report.validateSortReportByToolTipInNetSalesReport(driver, test_steps);
			test_steps.add("Verify Sort Report by Option DropDown Options");
			report.validateSortReportbyOptionsForNetSalesReport(driver, test_steps);
			test_steps.add("Verify Selection of Sort Report by Option");
			report.ValidateSelectionOfGivenSortReportByOption(driver, test_steps, "Average Stay");

			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Select Inputs", "Select Inputs", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		// Validating Customize Detailed View
		test_steps.add("========= Validating Customize Detailed View =========");
		try {
			
			test_steps.add("Verify Group Rows by Option ToolTip Text");
			report.validateGroupRowsbyToolTipInNetSalesReport(driver, test_steps);
			test_steps.add("Verify Group Rows by Drop Down Options");
			report.validateGroupRowsbyOptionsInNetSalesReport(driver, test_steps);
			test_steps.add("Verify Selection of Group Rows by Option");
			report.ValidateSelectionOfGivenGroupRowsByOption(driver, test_steps, "Month");

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
		
		// Generate Report
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	

	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
