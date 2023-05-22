package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyBookingWindowRatesCombinations extends TestCore{



	private WebDriver driver = null;
	String test_catagory, test_description;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> test_name = new ArrayList<>();


	String testName = this.getClass().getSimpleName().trim();

	String seasonName = "BookingWindowSeason";
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Rates V2")
	public void verifyBookingWindowRatesCombinations(String roomClassName, String roomClassAbb, String roomsMaxAdults, 
			String roomsMaxPersons, String roomsQuantity, String ratePlanName, String moreThanDaysCount, 
			String withInDaysCount, String action, String seasonStartDate, String seasonEndDate, String ratePerNight) throws Exception {

		if (action.equalsIgnoreCase("verifyValidation")) {
			if ( (Utility.validateString(moreThanDaysCount)) &&
					!(Utility.validateString(withInDaysCount))) {
				testName = "VerifyValidationMessageForBeforeCheckInDateAs<b>"+moreThanDaysCount+"</b>";								
			}else if ( !(Utility.validateString(moreThanDaysCount)) &&
					(Utility.validateString(withInDaysCount))) {
				testName = "VerifyValidationMessageForAfterCheckInDateAs<b>"+withInDaysCount+"</b>";												
			}else if ( !(Utility.validateString(moreThanDaysCount)) &&
					(Utility.validateString(withInDaysCount))) {
				testName = "VerifyValidationMessageForCheckInDateInBetween<b>"+moreThanDaysCount+"</b>And<b>"+withInDaysCount+"</b>";												
			}
		}else if (action.equalsIgnoreCase("verifyRateAmount")) {
			if ( (Utility.validateString(moreThanDaysCount)) &&
					!(Utility.validateString(withInDaysCount))) {
				testName = "VerifyRatePlanAmountForBeforeCheckInDateAs<b>"+moreThanDaysCount+"</b>";								
			}else if ( !(Utility.validateString(moreThanDaysCount)) &&
					(Utility.validateString(withInDaysCount))) {
				testName = "VerifyRatePlanAmountForAfterCheckInDateAs<b>"+withInDaysCount+"</b>";												
			}else if ( !(Utility.validateString(moreThanDaysCount)) &&
					(Utility.validateString(withInDaysCount))) {
				testName = "VerifyRatePlanAmountForCheckInDateInBetween<b>"+moreThanDaysCount+"</b>And<b>"+withInDaysCount+"</b>";												
			}				
		}
		test_description = "None (TestRail link is not available)";
		test_catagory = "Rates V2";

		CPReservationPage reservationPage = new CPReservationPage();
		RatesGrid ratesGrid = new RatesGrid();
		RoomClass roomClass = new RoomClass();

		if ( !(Utility.validateInput(seasonStartDate))) {
			seasonStartDate = Utility.getDateFromCurrentDate(0);
			seasonEndDate = Utility.getDateFromCurrentDate(30);
		}
		seasonName = seasonName+Utility.GenerateRandomNumber();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}			
			
			driver = getDriver();
			Login login = new Login();
			login.login(driver, envURL, "autorate", "autouser", "Auto@123");
			test_steps.add("========== Creating a new room class if not exists with given name "
					+ "as <b>"+roomClassName+"</b> ==========");
			roomClass.createRoomClassIfNotExists(driver, test_steps, roomClassName, roomClassAbb,
					roomsMaxAdults, roomsMaxPersons, roomsQuantity);      
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new room class", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create a new room class", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Updating the booking window details as per input for rate plan "
					+ "<b>"+ratePlanName+"</b> ==========");
			ratesGrid.updateBookingWindowForNightlyRate(driver, test_steps, ratePlanName, 
					"Nightly rate plan", "innCenter", roomClassName, moreThanDaysCount, withInDaysCount,
					seasonName, seasonStartDate, seasonEndDate, ratePerNight);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to update the booking window details", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to update the booking window details", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			test_steps.add("========== Verifying booking window restrictions through reservation ==========");
			if (action.equalsIgnoreCase("verifyValidation")) {
				reservationPage.verifyBookingWindowOnReservation(driver, test_steps, roomClassName, "2", "2", ratePlanName, 
						ratePerNight, moreThanDaysCount, withInDaysCount, true);				
			}else if (action.equalsIgnoreCase("verifyRateAmount")) {
				reservationPage.verifyBookingWindowOnReservation(driver, test_steps, roomClassName, "2", "2", ratePlanName, 
						ratePerNight, moreThanDaysCount, withInDaysCount, false);				
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify validation message/rate plan amount", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify validation message/rate plan amount", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyBookingWindowRates", envLoginExcel);
	}
	
	@AfterClass(alwaysRun=true)
    public void closeDriver() {
        driver.quit();
    }
}
