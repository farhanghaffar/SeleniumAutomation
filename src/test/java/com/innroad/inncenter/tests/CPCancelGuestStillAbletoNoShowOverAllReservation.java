package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
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

public class CPCancelGuestStillAbletoNoShowOverAllReservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_nameOne = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation nav = new Navigation();
	CPReservationPage reservation = new CPReservationPage();
	String testName = null,confirmationNo=null;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPCancelGuestStillAbletoNoShowOverAllReservation(String roomClassName,String checkInDate, String checkOutDate,
			String adults, String children,String ratePlanName,String salutation, String guestFirstName, 
			String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String referral) throws ParseException {
		String testCaseID="848462";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_description = "Verify CheckIn Policies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848462' target='_blank'>"
				+ "Click here to open TestRail: 848462</a><br>";
		test_name="CPCancelGuestStillAbletoNoShowOverAllReservation";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848462", Utility.testId, Utility.statusCode,
				Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		 List<String> roomNos= new ArrayList<String>();
		// Login
				try {
					if (!(Utility.validateInput(checkInDate))) {
						for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}
					if (guestFirstName.split("\\|").length > 1) {
						checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
						checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
					} else {
						checkInDate = checkInDates.get(0);
						checkOutDate = checkOutDates.get(0);
					}
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
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
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				}
	
				try {
					testSteps.add("<b>======Start Creating Reservation ======</b>");
					app_logs.info("<b>======Start Creating Reservation ======</b>");
					ArrayList<String> roomNumber= new ArrayList<String>();
					confirmationNo = reservation.createBasicMRBReservation(driver, false, checkInDate, checkOutDate, adults,
								children, ratePlanName, roomClassName, salutation, guestFirstName, guestLastName, "No",
								roomNumber, paymentType, cardNumber, nameOnCard, referral, false, testSteps);
					roomNos= reservation.getStayInfoRoomNo(driver, testSteps);
					app_logs.info(roomNos);		
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				}
				
				try {					
					ArrayList<String> roomClassNames= Utility.splitInputData(roomClassName);
					testSteps.add("<b>======Cancelling Second Reservation ======</b>");
					app_logs.info("<b>======Cancelling Second  Reservation ======</b>");
					reservation.clickStayInfoThreeDots(driver, testSteps, "Cancel", roomNos.get(1));
					reservation.enterCancellationReasons(driver, testSteps, "Cancel Second Guest");
					reservation.clickConfirmCancelButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.verifyStayInforRollBackButton(driver, testSteps, roomClassNames.get(1));
				}catch (Exception e) {
					Utility.catchException(driver, e, "Cancel Second Guest Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Cancel Second Guest  Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				}
				
				try {
					
					testSteps.add("<b>======Verify Able to do No Show Reservation ======</b>");
					app_logs.info("<b>======Verify Able to do No Show  Reservation ======</b>");					
					reservation.reservationStatusPanelSelectStatus(driver, "No Show",testSteps);
					reservation.clickConfirmCancelButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.clickCloseButtonOfNoShowSuccessfully(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					reservation.VerifyReservationStatus_Status(driver, "NO SHOW ");
					
					/*testSteps.add(
							"Verify if individual rooms have been cancelled, whether user can still mark the entire reservation as no-show or not."
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848462' target='_blank'>"
									+ "Click here to open TestRail: 848462</a><br>");
					Utility.testCasePass(Utility.statusCode, 0, Utility.comments,
							"Verify if individual rooms have been cancelled, whether user can still mark the entire reservation as no-show or not.");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
							Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify if individual rooms have been cancelled, whether user can still mark the entire reservation as no-show or not");
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "No Show Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "No Show Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				}
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPCancelGuestStillAbletoNoShowO", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
