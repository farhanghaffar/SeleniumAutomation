package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyMRBDetailsInTapeChart extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void verifyRulesDisplay(String testCaseID,String delim,String ratePlanName,
			String roomClassName, String roomClassAbb) throws  InterruptedException, IOException, Exception {
		
		Utility.DELIM = "\\" + delim;

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848579");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		//CustomFolio
		testName = "VerifyMRBDetailsInTapeChart";
		testDescription = "VerifyMRBDetailsInTapeChart</br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848579' target='_blank'>"
				+ "Click here to open TestRail: C848579</a>";
		testCatagory = "MRBVerificationInTapeChart";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		ReservationHomePage homePage = new ReservationHomePage();
		Tapechart tapeChart = new Tapechart();
		
		String randomString = Utility.GenerateRandomString();
		String randomString2 = Utility.GenerateRandomString();
		String salutation = "Mr."; 
		String guestFirstName = "VerifyMRB"; 
		String guestLastName = "ResTapChert" + randomString;
		
		String nameOnCard = guestFirstName + " " + guestLastName; 
		String allRoomNumbers = "";

		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com"; 
		String accountType = ""; 
		String account = "CorporateAccount" + Utility.generateRandomString(); 
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1"; 
		String address2 = "test2";
		String address3 = "test3"; 
		String city = "New york";
		String paymentType = "MC"; 
		
		String cardNumber = "5454545454545454"; 
		String cardExpDate = "12/23";
		String marketSegment = "GDS"; 
		String referral = "Other";
		String postalCode = "12345"; 
		String isGuesProfile = "No";
		String maxAdults = "1";
		String maxChildren = "0"; 
		String country = "United States"; 
		String state = "Alaska";
		String reservationNumber = "";
		String reservationStatus = "";
		String isAssign = "Yes";
		
		
		String defaultDateFormat = "dd/MM/yyyy";
		String timeZone = "US/Eastren";
		String checkInDate=Utility.getCurrentDate(defaultDateFormat, timeZone);
		String checkOutDate=Utility.getNextDate(2, defaultDateFormat);
		app_logs.info("checkInDate : "  + checkInDate);
		app_logs.info("checkOutDate : " + checkOutDate);
		
		ArrayList<String> checkInDates = new ArrayList<>();
		checkInDates.add(Utility.getCurrentDate(defaultDateFormat));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", defaultDateFormat));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", defaultDateFormat));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", defaultDateFormat));
				try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginClinent3281(driver);
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
		
		checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		String overrideAmount = "1000.15";
		
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848579' target='_blank'>"
					+ "<b>Verify reservation is created with manual override rate greater than 1000 from Tape chart for single reservation in CP.</a> =====");
		
			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			
			reservationPage.selectCheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone, testSteps);
			tapeChart.enterAdult(driver, maxAdults, testSteps);
			tapeChart.enterChildren(driver, maxChildren, testSteps);
			tapeChart.selectRatePlan(driver, "Manual Override", testSteps);	
			tapeChart.enterManualOverrideAMount(driver,testSteps, overrideAmount);
			Wait.wait10Second();
			tapeChart.clickSearchButton(driver, testSteps);
			tapeChart.clickSearchBtnInTapeChart(driver, testSteps);
			
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
			
			testSteps.add("Verify that amount '"+ overrideAmount +"' is showing in stay info section in reservation");

			String getTotalAmount = homePage.getReservationAmount(driver);
			getTotalAmount = Utility.removeDollarBracketsAndSpaces(getTotalAmount);
			Utility.verifyEquals(getTotalAmount, overrideAmount, testSteps);
			testSteps.add("Verified that amount '"+ overrideAmount +"' is showing in stay info section in reservation");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");					
			statusCode.add(0, "1");
	
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

		String tempCheckin = checkInDates.get(0) + "|" + checkInDates.get(1);
		String tempCheckOut = checkInDates.get(1) + "|" + checkInDates.get(2);
		String tempRatePlan = ratePlanName + "|" + ratePlanName;
		String tempMaxAdults = maxAdults + "|" + maxAdults;
		String tempMaxChildren = maxChildren + "|" + maxChildren;
		String tempRoomClassName = roomClassName + "|" + roomClassName;
		String tempFirstName = guestFirstName + randomString + "|" + guestFirstName + randomString2;
		String tempLastName = guestLastName + "|" + guestLastName;
		String tempSalutation = salutation + "|" + salutation;
		String isSplitRes = "No";
		String tempPhoneNumber = phoneNumber + "|" + phoneNumber;
		String tempRoomClassAbb = roomClassAbb + "|" + roomClassAbb;
		//824567
		
		String tripSummaryTripTotal = "";
		String tripSummaryBalance = "";
		String roomsAmount = "";
		String source = "innCenter";
				try {
				testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/848472' target='_blank'>"
					+ "<b>Verify details of Multi room reservation in Tape chart</a> =====");

				navigation.ReservationV2_Backward(driver);
				testSteps.add("Back to reservation page");
				app_logs.info("Back to reservation page");
				
				testSteps.add("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");

				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.createMRBReservations(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren, tempRatePlan, "",
						tempRoomClassName, "Yes", "", "", tempSalutation, tempFirstName, tempLastName, tempPhoneNumber, alternativePhone,
						"", accountType, address1, address2, address3, city, country, state, postalCode,
						isGuesProfile, referral, paymentType, cardNumber, nameOnCard, cardExpDate, "", "", getTestSteps);
				reservationPage.clickBookNow(driver, testSteps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				
				
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				tripSummaryTripTotal=reservationPage.getTripSummaryTripTotal(driver, testSteps);
				tripSummaryBalance=reservationPage.get_TripSummaryBalanceWithCurrency(driver, testSteps);
			
				String roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
				String roomNumber2 = reservationPage.getSecondRoomNumber(driver, testSteps, isAssign);
				allRoomNumbers = roomNumber + "|" + roomNumber2;
				for( int b=0; b < tempRoomClassName.split("\\|").length; b++) {
					String temp = homePage.getRoomTotal(driver, b);
					if(!roomsAmount.isEmpty()) {
						roomsAmount = roomsAmount + "|" +  temp.replace(" ", "");						
					}else {
						roomsAmount = temp.replace(" ", "");						
					}
				}
				Utility.printString("roomsAmount" + roomsAmount);

				
					
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
				
				String guestName = guestFirstName + randomString + " " + guestLastName + "|" + guestFirstName + randomString2 + " " + guestLastName;
		try {

			navigation.TapeChart(driver);
			testSteps.add("Navigate to tape chart");
			app_logs.info("Navigate to tape chart");
			testSteps.addAll(tapeChart.verifyReservtion(driver, tempRoomClassAbb, tempRoomClassName, allRoomNumbers, roomsAmount,
					reservationNumber, guestName, tempRatePlan, source, 
					tempMaxAdults, tempMaxChildren, tempCheckin, tempCheckOut, 
					tripSummaryTripTotal, tripSummaryBalance));
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation in tapechart", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation in tapechart", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	try {
		comments = "Verify details of Multi room reservation in Tape chart";
		statusCode.add(1, "1");
		statusCode.add(2, "1");
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
		return Utility.getData("VerifyMRBDetailsInTapeChart", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
