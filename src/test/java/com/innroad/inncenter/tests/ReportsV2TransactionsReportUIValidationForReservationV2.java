package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class ReportsV2TransactionsReportUIValidationForReservationV2 extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	ReservationV2 reservationV2Page = new ReservationV2();
	Admin admin = new Admin();

	String clientTimeZone, checkInDate, startDayOfWeek, dFormat, dateFormat, propertyName ="Groups Property";
	
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> sortReportByOptions = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(groups = "ReportsV2")
	public void validateTransactionsReport() throws Throwable {
		
		test_description = "Validate Transactions Report UI Validation<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - Transactions Report UI Validation";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");
		driver = getDriver();
		
		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, 1);
			}
			sortReportByOptions.add("Reservation Name");
			sortReportByOptions.add("Time Processed");
			sortReportByOptions.add("Payment Method");
			sortReportByOptions.add("User Processed");
			sortReportByOptions.add("Amount");
			
			loginReportsV2ReservationV2(driver, test_steps);	
			nav.admin(driver);
			nav.navigateToClientinfo(driver);		
			admin.clickClientName(driver);
			admin.clickClientOption(driver);		
			clientTimeZone = admin.getClientTimeZone(driver, test_steps);
			
			startDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
			clientTimeZone = admin.getClientTimeZone(driver, test_steps);
			dFormat = admin.getClientDateFormat(driver);

			switch (clientTimeZone) {
			case "(GMT-05:00) Eastern Time (US and Canada)":
				propertyTimeZone = "US/Eastern";
				break;			
			case "(GMT-06:00) Central Time (US and Canada)":
				propertyTimeZone = "US/Central";
				break;		
			case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
				propertyTimeZone = "Europe/London";
				break;
			default:
				break;
			}
			if (dFormat.equalsIgnoreCase("USA")) {
				dateFormat = "MMM dd, yyyy";
			}else if (dFormat.equalsIgnoreCase("International")) {
				dateFormat = "dd MMM, yyyy";
			}
			
			nav.ReservationV2_Backward(driver);
			checkInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");

			nav.ReportsV2(driver, test_steps);
			report.navigateToTransactionsReport(driver, test_steps);
			report.verifyDefaultValuesForTransactionReport(driver, test_steps, checkInDate);
			report.validatePropertyNameForTransactionsReport(driver, test_steps, propertyName);
			report.validateReturnToDefaultAvailabilityRoomForecast(driver, test_steps);
			report.validateReturnToDefaultForTransactionReport(driver, test_steps, "Last Week");
			report.validateDateRangesForTransactionsReport(driver, test_steps, "Last Week", clientTimeZone, dateFormat, startDayOfWeek);
			report.validateDateRangesForTransactionsReport(driver, test_steps, "Last Month", clientTimeZone, dateFormat, startDayOfWeek);
			report.validateSortReportByForTransactionsReport(driver, test_steps, sortReportByOptions);
			report.validateAllToolTipsForTransactionsReport(driver, test_steps);
			report.validateTooltipOfInnRoadImage(driver, test_steps);
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
	
			
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", test_name,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", test_name,
						"ChekIn and CheckOut date", driver);
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
