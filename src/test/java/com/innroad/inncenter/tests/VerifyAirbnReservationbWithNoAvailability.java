package com.innroad.inncenter.tests;
import static org.testng.Assert.assertTrue;
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
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import io.restassured.response.Response;
public class VerifyAirbnReservationbWithNoAvailability extends TestCore {
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
	RatesGrid ratesGrid = new RatesGrid();
	int dateIndex = 0;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Airbnb")
	public void verifyAirbnReservationbWithNoAvailability(String beginDate, String noOfNights, String roomClassName,
			String firstName, String lastName, String emails, String adultCount, String childCount, String phoneNum,
			String baseprice, String payoutamount) {
		String testName = null, currency = null, listingid = null, ratePlanName = "", startDate = null, endDate = null,
				startDate1 = null, endDate1 = null, night = "",guestFirstName = firstName +Utility.generateRandomStringWithGivenLength(3);
		boolean isBlackedOut = false;
		HashMap<String, String> getAirbnbDetails = null;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> stayDates = new ArrayList<String>();
		String externalConfirmation = Utility.generateRandomStringWithGivenLength(5);
		try {
			testName = "Creation of reservation when there is no availability";
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
			ratePlanName = getAirbnbDetails.get("ratePlan");
			test_steps.add("RatePlan ID : " + getAirbnbDetails.get("ratePlan"));
			app_logs.info("RatePlan ID " + getAirbnbDetails.get("ratePlan"));
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, test_steps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Get Data", testName, test_description,
					test_catagory, test_steps);
		}

		try {
			
			test_steps.add("<b>===== BLACKOUT SOURCE=====</b>");
			app_logs.info("=====BLACKOUT SOURCE=====");
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

			startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			app_logs.info(startDate);
			app_logs.info(endDate);
			stayDates = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			night = String.valueOf(stayDates.size());
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			navigation.InventoryV2(driver);
			test_steps.add("Navigated to Inventory");
			navigation.ratesGrid(driver, test_steps);
			ratesGrid.verifyRatesGridLoaded(driver);
			getTest_Steps.clear();
			getTest_Steps = ratesGrid.clickOnAvailability(driver);
			ratesGrid.verifyRatesGridLoaded(driver);
			test_steps.addAll(getTest_Steps);
			ratesGrid.expandRoomClass(driver, test_steps, roomClassName);
			dateIndex = ratesGrid.getHeadingDateIndex(driver, datesRangeList.get(datesRangeList.size() - 1),
					"dd/MM/yyyy");
			ratesGrid.activeOrBlackoutChannel(driver, datesRangeList.get(datesRangeList.size() - 1), "dd/MM/yyyy",
					roomClassName, "Airbnb", "");
			isBlackedOut = ratesGrid.getBlackoutStatus(driver, dateIndex, "Airbnb", roomClassName);
			app_logs.info("Mark  Blacked Out Successfully ");
			test_steps.add("Mark  Blacked Out Successfully ");
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Blackout Source", testName, test_description,
					test_catagory, test_steps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Blackout Source", testName, test_description,
					test_catagory, test_steps);
		}

		try {
			test_steps.add("<b>===== VERIFIED AVAILABILITY =====</b>");
			app_logs.info("=====VERIFIED AVAILABILITY =====");
			Response checkAvailabilityresponse = null;
			checkAvailabilityresponse = airbnb.checkAvailability(listingid, startDate, night,
					vrvoproperties.getProperty("BookingRequestToken"));
			assertTrue(!checkAvailabilityresponse.asString().contains("true"));
			app_logs.info(
					"Verified no availability over required stay on: AIRBNB CHECK AVAILABILITY RESERVATION ENDPOINT");
			test_steps.add(
					"Verified no availability over required stay on: AIRBNB CHECK AVAILABILITY RESERVATION ENDPOINT");
			
			test_steps.add("<b>===== CREATE AIRBNB RESERVATION=====</b>");
			app_logs.info("=====CREATE AIRBNB RESERVATION=====");
			airbnb.airbnbReservationActions("create", externalConfirmation, startDate, endDate, guestFirstName,
					lastName, listingid, night, emails, adultCount, childCount, phoneNum, baseprice, payoutamount,
					vrvoproperties.getProperty("BookingRequestToken"));
			test_steps.add("Created Airbnb reservation successfully: ");
			
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create AirBnb Reservation", testName,
					test_description, test_catagory, test_steps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create AirBnb Reservation", testName,
					test_description, test_catagory, test_steps);
		}
		
		try {
			test_steps.add("<b>===== SEARCH RESERVATION ON UI=====</b>");
			app_logs.info("=====SEARCH RESERVATION ON UI=====");
			navigation.reservationBackward3(driver);
			test_steps.add("Click On Reservation");
			app_logs.info("Click On Reservation");
			boolean isExist=airbnbIn.guestUnlimitedSearchAndOpen(driver, test_steps, guestFirstName, false, 1);
			app_logs.info(isExist);
			assertTrue(isExist);
			test_steps.add("<b>===== REMOVE BLACKOUT=====</b>");
			app_logs.info("=====REMOVE BLACKOUT=====");
			navigation.inventory(driver);
			test_steps.add("Navigated to Inventory");
			navigation.ratesGrid(driver, test_steps);
			ratesGrid.verifyRatesGridLoaded(driver);
			getTest_Steps.clear();
			getTest_Steps = ratesGrid.clickOnAvailability(driver);
			ratesGrid.verifyRatesGridLoaded(driver);
			test_steps.addAll(getTest_Steps);
			ratesGrid.expandRoomClass(driver, test_steps, roomClassName);
			ratesGrid.activeOrBlackoutChannel(driver, datesRangeList.get(datesRangeList.size() - 1), "dd/MM/yyyy",
					roomClassName, "Airbnb", "");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create AirBnb Reservation", testName,
					test_description, test_catagory, test_steps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create AirBnb Reservation", testName,
					test_description, test_catagory, test_steps);
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAirbnNoAvailability", airbnbexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
