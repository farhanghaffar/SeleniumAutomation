package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyReservationCreationFromTapechartForMultiPropertyClient extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;


	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void verifyReservationCreationForMultiPropertyClient(String testCaseID,String delim,String ratePlan,
			String roomClassName, String roomClassAbb) throws  InterruptedException, IOException, Exception {
		
		Utility.DELIM = "\\" + delim;

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848642");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		String ratePlanName = ratePlan.split("\\|")[0].trim();
		String autopayRatePlan = ratePlan.split("\\|")[1].trim();

		//CustomFolio
		testName = "VerifyReservationCreationFromTapechartForMultiPropertyClient";
		testDescription = "Verify creating reservation from tape chart in multi property client.</br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848642' target='_blank'>"
				+ "Click here to open TestRail: C848642</a>";
		testCatagory = "VerifyReservationCreationForMultiPropertyClient";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		Tapechart tapeChart = new Tapechart();
		Login login = new Login();
		
		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr."; 
		String guestFirstName = "VerifyMRB" + randomString; 
		String guestLastName = "ResTapChert" + randomString;
		
		String nameOnCard = guestFirstName + " " + guestLastName; 
		String paymentType = "MC"; 
		
		String cardNumber = "5454545454545454"; 
		String cardExpDate = "12/23";
		String referral = "Other";
		String postalCode = "12345"; 
		String maxAdults = "1";
		String maxChildren = "0"; 
		String reservationNumber = "";
		String reservationStatus = "";
		
		
		String defaultDateFormat = "MM/dd/yyyy";
		String timeZone = "US/Eastren";
		String checkInDate=Utility.getCurrentDate(defaultDateFormat, timeZone);
		String checkOutDate=Utility.getNextDate(1, defaultDateFormat);
		app_logs.info("checkInDate : "  + checkInDate);
		app_logs.info("checkOutDate : " + checkOutDate);
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver, envURL, "autopay", "autouser", "Auto@123");
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		//825325
		
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848642' target='_blank'>"
					+ "<b>Verify creating reservation from tape chart in multi property client.</a> =====");
		
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone, testSteps);
			tapeChart.enterAdult(driver, maxAdults, testSteps);
			tapeChart.enterChildren(driver, maxChildren, testSteps);
			tapeChart.selectRatePlan(driver, autopayRatePlan, testSteps);	
			tapeChart.clickSearchButton(driver, testSteps);
			
			app_logs.info("==========SELECT ROOM==========");
			testSteps.add("==========SELECT ROOM==========");
			tapeChart.clickAvailableSlot(driver, roomClassAbb);
			testSteps.add("Click available room of Room Class '" + roomClassAbb + "'");
			app_logs.info("Click on available room of Room Class '" + roomClassAbb + "'");
			Wait.wait10Second();
			testSteps.add("New Reservation page is opened");
			app_logs.info("New Reservation Page is Opened");

			String room = reservationPage.getRoomSelectedFromTapeChart(driver, testSteps);
			Assert.assertTrue(room.contains(roomClassName), "Failed: Room is not selected");
			testSteps.add("Verified Room Class is '" + roomClassName + "'");
		
			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);
	
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);
	
			reservationPage.SelectReferral(driver, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
			reservationPage.clickBookNow(driver, testSteps);
	
			reservationNumber = "";
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			
			reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
			
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					
			statusCode.add(0, "1");
	
			login.logout(driver);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848639' target='_blank'>"
					+ "<b>Verify the 'Unassigned' footer rows per room class.</a> =====");
		
			loginClinent3281(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");

			testSteps.add("Verify that roomclass '"+  roomClassName +"' with abbrreviation '"+ roomClassAbb +"' has footer with text 'Unassigned'.");
			tapeChart.verifyRoomClassUnassigned(driver, testSteps, roomClassAbb, "Unassigned");
			testSteps.add("Verified that roomclass '"+  roomClassName +"' with abbrreviation '"+ roomClassAbb +"' has footer with text 'Unassigned'.");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify the 'Unassigned' footer rows per room class.", testName, "VerifyUnasignedFooter", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify the 'Unassigned' footer rows per room class.", testName, "VerifyUnasignedFooter", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String isAssign = "No";
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848638' target='_blank'>"
					+ "<b>Verify the updates in the Unassigned.</a> =====");

			int count = tapeChart.getUnassignedButtonCount(driver);
			if(count > 2) {
				testSteps.add("Total '"+ count +"' unassigned reservations are available in tapechart");
			}else {

				navigation.ReservationV2_Backward(driver);
				testSteps.add("Back to reservation page");
				app_logs.info("Back to reservation page");
				int numberOfRes = 2;
				switch (count) {
				case 2:
					numberOfRes = 1;									
					break;
				case 1 :
					numberOfRes = 2;
					break;
				case 0 :
					numberOfRes = 2;
					break;
				default:
					numberOfRes = 2;
					break;
				}
				
				for(int i=1; i <= numberOfRes; i++) {
					
					testSteps.add("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
					app_logs.info("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
				
					reservationPage.click_NewReservation(driver, testSteps);									

					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver,  checkOutDate);
					testSteps.addAll(getTestSteps);


					reservationPage.enter_Adults(driver, testSteps, maxAdults);
					reservationPage.enter_Children(driver, testSteps, maxChildren);
					reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
					reservationPage.clickOnFindRooms(driver, testSteps);
					
					reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					reservationPage.clickNext(driver, testSteps);									

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);

					reservationPage.SelectReferral(driver, referral);
					
					reservationPage.clickBookNow(driver, testSteps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);
					
					reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
					
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
									
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
				}
				
				navigation.TapeChart(driver);
				testSteps.add("Navigate to tape chart");
				app_logs.info("Navigate to tape chart");

			}
			
			tapeChart.ClickUnassignedButton(driver);
			testSteps.add("Clicked unassigned button");
			app_logs.info("Clicked unassigned button");					

			testSteps.add("Verify that reservations are sorted in alphabetical order in unassigned section in tapechart");
			getTestSteps.clear();
			getTestSteps.addAll(tapeChart.getUnassigendeReservationsName(driver));
			ArrayList<String> sortedList = new ArrayList<>();
			sortedList = getTestSteps;
			Collections.sort(sortedList);
			testSteps.add("Expected List : " + sortedList);
			testSteps.add("Found : " + getTestSteps);
			assertEquals(sortedList, getTestSteps, "Failed : Guest name in unassigned tab are not sorted in alphabetical order");
			getTestSteps.clear();
			testSteps.add("Verified that reservations are sorted in alphabetical order in unassigned section in tapechart");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that reservations are sorted in alphabetical order in unassigned section in tapechart", testName, "VerifyUnasignedResOrder", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify that reservations are sorted in alphabetical order in unassigned section in tapechart", testName, "VerifyUnasignedResOrder", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
			
	try {
		statusCode.add(1, "1");
		statusCode.add(2, "1");
		comments = "Verify details of Multi room reservation in Tape chart";
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("tapeChartResWithMultiPropert", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
