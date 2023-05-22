package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class SplitReservationAddRoomFunctionality extends TestCore {
	

	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> Rooms = new ArrayList<String>();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");

		if (!Utility.isExecutable(testName, excel_Swarna))

			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "RegressoionCases_bakar")
	public void splitReservationAddRoomFunctionality(String testCaseId, String Rateplan, String RoomClass) throws ParseException {
		test_name = this.getClass().getSimpleName().trim();
		
		String testName = test_name;
		testCategory="Split Reservation";
		
		 String testcaseId="848457";
			
			Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848457");
			
			testDescription = testName + "<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848457' target='_blank'>"
					+ "Click here to open TestRail: C848457</a><br>";

					

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage reservationPage = new CPReservationPage();
		ReservationHomePage homePage = new ReservationHomePage();
		ReservationSearch reservationSearch = new ReservationSearch();	

		String CheckInDate = "NA";
		String CheckOutDate = "NA";
	
		String IsSplitRes = "";
	
		String PromoCode = "";
			
		String Adults = "2|2";
		String Children = "1|2";
			
		String[] roomClassArr = RoomClass.split("\\|");
		
	
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < roomClassArr.length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					//checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					//checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkInDates.get(1) + "|" + checkInDates.get(2);
			}
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "GetDatesForSplitReservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
			e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "GetDatesForSplitReservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}
		// Login
			try {
				driver = getDriver();
				login_Autoota(driver);

				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Reservation
			try {
				app_logs.info(CheckInDate + CheckOutDate + Adults + Children + Rateplan + PromoCode + IsSplitRes);
//824739
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824739' target='_blank'>"
						+ "<b> ===== Verifying that Checkin date of next split by default set to checkout date of previous split ===== </b> </a>");
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				

				reservationPage.verifyNoOfRecordAvailableaBeforeandAfterSplitCheckboxISChecked(driver, testSteps);
				
				
				
				reservationPage.clickOnSplitResButton(driver, testSteps);
				reservationPage.verifyNoOfRecordAvailableaBeforeandAfterSplitCheckboxISChecked(driver, testSteps);
				
				testSteps.add("uncheck split reservation");
				app_logs.info("uncheck split reservation");
				reservationPage.clickOnSplitResButton(driver, testSteps);
				

				reservationPage.verifyNoOfRecordAvailableaBeforeandAfterSplitCheckboxISChecked(driver, testSteps);
				
				
				
			}
			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
				
				
				
				
		try {
			statusCode.set(0, "1");
			comments.add(0, "Verified split room checkbox functionality");
				
				RetryFailedTestCases.count = Utility.reset_count;
			    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			
		}catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);
				} else {

					 Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);

						Assert.assertTrue(false);
				}	
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);
				} else {
					 Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);

					Assert.assertTrue(false);
				}
			}	
	

	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("SplitAddRoomFunctionality", excel_Swarna);

	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}


}
