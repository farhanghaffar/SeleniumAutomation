package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyCheckInButtonOfFutureDateReservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	CPReservationPage reservation = new CPReservationPage();
	String testName = null,yearDate=null,reservationNo=null;

	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyCheckInButtonOfFutureDateReservation(String roomClass, String checkInDate, String checkOutDate, String adults, String children,String rateplan, 
			String salutation,String guestFirstName, String guestLastName, String isGuesProfile, String paymentType,
			String cardNumber, String nameOnCard, String travelAgent, String marketSegment, String referral) throws ParseException {
		String testCaseID="848417";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "VerifyCheckInButtonOfFutureDateReservation";
		test_description = "CP Sanity - Verify check-in button is disabled when reservation is made for future date<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848417' target='_blank'>"
				+ "Click here to open TestRail: 848417</a><br>";
		
		test_catagory = "cPReservation_CheckIn";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848417",Utility.testId, Utility.statusCode, Utility.comments,"");
		// Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						//Utility.reTry.replace(testName, 1);
						Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
					}

					driver = getDriver();
					login_CP(driver);
					testSteps.add("Logged into the application");
					app_logs.info("Logged into the application");

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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
				// Get checkIN and Checkout Date
				try {
					if (!(Utility.validateInput(checkInDate))) {
						for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
							checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}
					checkInDate = checkInDates.get(0);
					checkOutDate = checkOutDates.get(0);
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				//Start Creating Reservation 
				
				try
				{
						testSteps.add("<b>======Start Creating New Reservation======</b>");
						reservationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adults, children,
								rateplan, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber,
								nameOnCard, marketSegment, referral, roomClass, false, false);	
				}catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e,  "Failed to Create New reservation and Check In", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.updateReport(e,  "Failed to  Create New reservation and Check In ", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}				
				//Verify CheckInButton				
				try
				{
					testSteps.add("<b>======Start Verifying CheckIn button======</b>");
					String message ="CheckIn button is enabled for future date reservation which is incorrect.";
					reservation.verifyCheckInButtonDisability(driver, testSteps,message);
					
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Checkin Button for Future Date");
					}
					 RetryFailedTestCases.count = Utility.reset_count;
				     Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);	
				}
				catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e,  "Failed to Verify Check In Button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.updateReport(e,  "Failed to Verify Check In Button ", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}		
	}
	}
	
	
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("verifyCheckInButtonOfFutureDate", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
