package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyBookingWindowRatesCombinationsNew extends TestCore{



	private WebDriver driver = null;
	String test_catagory, test_description, roomClassAbbrivation;
	ArrayList<String> test_steps = new ArrayList<>();
	HashMap<String, String> bookingWindowRestrictions = new HashMap<>();
	String testName = this.getClass().getSimpleName().trim();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Rates V2")
	public void verifyBookingWindowRatesCombinations(String checkInDate, String	checkOutDate, String roomClassName, 
			String adults, String children, String ratePlanName, String accountName, String accountType,
			String reservationType) throws Exception {
	
		test_description = "verifyBookingWindowRatesCombinations";
		test_catagory = "Rates V2 Validation with booking window restrictions";
		CPReservationPage reservationPage = new CPReservationPage();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
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
			test_steps.add("========== Navigating to Edit rate plan page for <b>"+ratePlanName.split("\\|")[0]+"</b> ==========");
			if (reservationType.contains("tapechart")) {
				RoomClass roomClass = new RoomClass();
				Navigation navigation = new Navigation();
				navigation.Setup(driver, test_steps);
				navigation.RoomClass(driver, test_steps);
				roomClassAbbrivation = roomClass.openRoomClassAndGetAbbr(driver, test_steps, roomClassName);			
			}
			ratesGrid.searchAndEditRatePlan(driver, test_steps, ratePlanName.split("\\|")[0]);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to navigate to edit rate plan screen", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to navigate to edit rate plan screen", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);	      
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to navigate to Restrictions & Policies tab", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to navigate to Restrictions & Policies tab", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			test_steps.add("========== Getting booking window default restrictions for rate plan "
					+ "<b>"+ratePlanName.split("\\|")[0]+"</b> ==========");
			bookingWindowRestrictions = ratesGrid.getBookingWindowRestrictions(driver, test_steps, ratePlanName.split("\\|")[0]);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to capture Restrictions of booking window", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to capture Restrictions of booking window", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Navigating to new reservation screen from <b>"+reservationType+"</b> ==========");
			reservationPage.navigateToNewReservationScreen(driver, test_steps, reservationType, accountName, accountType);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to capture Restrictions of booking window", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to capture Restrictions of booking window", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Verifying booking window restrictions through reservation ==========");
			reservationPage.verifyBookingWindowOnReservation(driver, test_steps, roomClassName, checkInDate, checkOutDate, 
					adults, children, ratePlanName, bookingWindowRestrictions, reservationType, roomClassAbbrivation);
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
		return Utility.getData("VerifyBookingWindowRates", excel);
	}
	
	@AfterClass(alwaysRun=true)
    public void closeDriver() {
        driver.quit();
    }
}
