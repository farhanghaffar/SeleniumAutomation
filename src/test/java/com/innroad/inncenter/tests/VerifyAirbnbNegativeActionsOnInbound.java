package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AirbnbInObjects;
import com.innroad.inncenter.pageobjects.AirbnbObjects;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

@Test(dataProvider = "getData", groups = "Airbnb")
public class VerifyAirbnbNegativeActionsOnInbound extends TestCore {

	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	private WebDriver driver = null;
	CPReservationPage reservation = new CPReservationPage();
	Navigation navigation = new Navigation();
	AirbnbInObjects airbnbIn = new AirbnbInObjects();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	AirbnbObjects airbnb = new AirbnbObjects();
	Admin admin = new Admin();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	TaxesAndFee taxFee = new TaxesAndFee();
	Account createCA = new Account();
	AirbnbInObjects airIn = new AirbnbInObjects();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	public void verifyAirbnbNegativeActionsOnInbound(String beginDate, String noOfNights, String roomClassName,
			String firstName, String lastName, String emails, String adultCount, String childCount, String phoneNum,
			String baseprice, String payoutamount, String actionTaken, String extendOrResduceDays,
		
			String action) {
		String testName = null, startDate = null, endDate = null,  startDate1 = null,
				endDate1 = null, listingid = null, currency = null, night = "",
				updatedCheckoutDate = "", updatedCheckoutDate1 = "", updatedNight = "",
				guestFirstName = firstName + Utility.generateRandomStringWithGivenLength(3), guestName = "";

		HashMap<String, String> getAirbnbDetails = null;
		String externalConfirmation = Utility.generateRandomStringWithGivenLength(5);

		ArrayList<String> stayDates = new ArrayList<String>();
		ArrayList<String> updatestayDates = new ArrayList<String>();
		ArrayList<String> checkInDates = new ArrayList<>();
		try {
			testName = "VerifyAirbnbReservation" + " " + action;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("#################################################################################");

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			loginAirbnb(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			test_steps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, test_steps);
			test_steps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);

			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			test_steps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			test_steps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);

			admin.clickClientOptions(driver, test_steps);
			currency = admin.getDefaultCurrency(driver);
			test_steps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);

			navigation.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickAirbnbSetup(driver);
			test_steps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");

			test_steps.add("<b>===== Getting Listing ID =====</b>");
			app_logs.info("===== Getting Listing ID=====");

			getAirbnbDetails = airbnbIn.getAirbnbListingId(driver, roomClassName, property);
			listingid = getAirbnbDetails.get("listingId");
			test_steps.add("ListingId ID : " + getAirbnbDetails.get("listingId"));
			app_logs.info("Listing ID " + getAirbnbDetails.get("listingId"));
			getAirbnbDetails.get("ratePlan");
			test_steps.add("RatePlan ID : " + getAirbnbDetails.get("ratePlan"));
			app_logs.info("RatePlan ID " + getAirbnbDetails.get("ratePlan"));

			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDate("dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)),
							"MM/dd/yyyy", "dd/MM/yyyy");
				}
			} else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1 = checkInDates.get(0);
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.addDays(startDate1, 2);
				} else {

					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			}
			if (action.equalsIgnoreCase("extendReservationNegative")) {
				if (!(Utility.validateString(extendOrResduceDays))) {
					if (action.equalsIgnoreCase("extendReservationNegative")) {
						updatedCheckoutDate1 = Utility.addDays(endDate1, 1);
					} else {
						updatedCheckoutDate1 = Utility.addDays(endDate1, -1);
					}
				} else {
					updatedCheckoutDate1 = Utility.addDays(endDate1, Integer.parseInt(extendOrResduceDays));
				}
			}

			startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");

			if (action.equalsIgnoreCase("extendReservationNegative")) {
				updatedCheckoutDate = Utility.parseDate(updatedCheckoutDate1, "dd/MM/yyyy", "YYYY-MM-dd");
				updatestayDates = Utility.getAllDatesBetweenCheckInOutDates(startDate1, updatedCheckoutDate1);
				updatedNight = String.valueOf(updatestayDates.size());
			}
			stayDates = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);

			night = String.valueOf(stayDates.size());

			app_logs.info(startDate);
			app_logs.info(endDate);
			app_logs.info("updatedCheckoutDate : " + updatedCheckoutDate);

			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);

		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Tax And fee", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create Tax And fee", testName, test_description,
					test_catagory, test_steps);
		}
		try {

			if (action.equalsIgnoreCase("extendReservationNegative")) {

				test_steps.add(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);
				app_logs.info(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);

				airbnb.verifyUpdateReservationCheck(listingid, adultCount, childCount, updatedNight, startDate,
						externalConfirmation, vrvoproperties.getProperty("BookingRequestToken"), "false", test_steps);

				airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, updatedCheckoutDate,
						guestFirstName, lastName, listingid, updatedNight, emails, adultCount, childCount, phoneNum,
						baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
				boolean isReservationExist = airbnb.isReservationExistForModification(externalConfirmation);
				assertEquals(isReservationExist, false);
				test_steps.add("Verify resrevation not found to update in Get booking response");
				app_logs.info("Verify resrevation not found to update in Get booking response");
				navigation.reservationBackward3(driver);
				guestName = guestFirstName + " " + lastName;
				boolean isReservationPresent = airIn.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true,
						2);
				test_steps.add("Verified reservation not existing in inncenter");
				assertEquals(isReservationPresent, false);
				test_steps.add("Verify updated reservation not found on UI: " + actionTaken);
				app_logs.info("Verified");

			} else if (action.equalsIgnoreCase("payoutWithoutReservation")) {
				test_steps.add(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);
				app_logs.info(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);
				airbnb.verifyUpdateReservationCheck(listingid, adultCount, childCount, night, startDate,
						externalConfirmation, vrvoproperties.getProperty("BookingRequestToken"), "false", test_steps);

				airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, updatedCheckoutDate,
						guestFirstName, lastName, listingid, updatedNight, emails, adultCount, childCount, phoneNum,
						baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
				boolean isReservationExist = airbnb.isReservationExistForModification(externalConfirmation);
				assertEquals(isReservationExist, false);
				test_steps.add("Verify resrevation not found to update in Get booking response");
				app_logs.info("Verify resrevation not found to update in Get booking response");
				navigation.reservationBackward3(driver);
				guestName = guestFirstName + " " + lastName;
				boolean isReservationPresent = airIn.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true,
						2);
				test_steps.add("Verified reservation not existing in inncenter");
				assertEquals(isReservationPresent, false);
				test_steps.add("Verify updated reservation not found on UI: " + actionTaken);
				app_logs.info("Verified");

			} else if (action.equalsIgnoreCase("cancelWithoutReservation")) {
				test_steps.add(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);
				app_logs.info(
						"===================== UPDATE AIRBNB RESERVATION WHEN NO RESERVATION CREATED========================= "
								+ actionTaken);
				airbnb.verifyUpdateReservationCheck(listingid, adultCount, childCount, night, startDate,
						externalConfirmation, vrvoproperties.getProperty("BookingRequestToken"), "false", test_steps);

				airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, updatedCheckoutDate,
						guestFirstName, lastName, listingid, updatedNight, emails, adultCount, childCount, phoneNum,
						baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));

				boolean isReservationExist = airbnb.isReservationExistForModification(externalConfirmation);
				assertEquals(isReservationExist, false);
				test_steps.add("Verify resrevation not found to update in Get booking response");
				app_logs.info("Verify resrevation not found to update in Get booking response");
				navigation.reservationBackward3(driver);
				guestName = guestFirstName + " " + lastName;
				boolean isReservationPresent = airIn.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true,
						2);
				test_steps.add("Verified reservation not existing in inncenter");
				assertEquals(isReservationPresent, false);
				test_steps.add("Verify updated reservation not found on UI: " + actionTaken);
				app_logs.info("Verified");

			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Tax And fee", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create Tax And fee", testName, test_description,
					test_catagory, test_steps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAirbnbNegative", airbnbexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
