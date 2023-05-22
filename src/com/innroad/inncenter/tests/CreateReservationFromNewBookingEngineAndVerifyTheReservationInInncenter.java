package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateReservationFromNewBookingEngineAndVerifyTheReservationInInncenter extends TestCore {

	// Automation-1968
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private ArrayList<String> getDataOfHash(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		ArrayList<String> values = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			testSteps.add("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
			app_logs.info("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
			values.add(mentry.getValue().toString());

		}
		return values;
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void createReservationFromNewBookingEngineAndVerifyTheReservationInInncenter(String rateName, String baseAmount,
			String addtionalAdult, String additionalChild, String displayName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String ratePlan,
			String rateType, String rateAttributes, String source, String adults, String child, String seasonName,
			String marketSegment, String referral, String phone, String address, String city, String country,
			String state, String postalCode, String firstName, String lastName, String paymentType, String cardNumber,
			String cardName, String cardExpDate, String emailAddress, String cvv, String reservationStatus)
			throws InterruptedException, IOException, NumberFormatException, ParseException {

		String testName = "VerificationOfAvailabilityInRatesV2ExpediaReservationFromBE";
		test_description = "Create reservation from new booking engine nd verify the reservation in Inncenter<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1986' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-1986</a>";
		test_catagory = "BE_Rates_Expedia";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Rate rate = new Rate();
		Navigation navigation = new Navigation();
		CPReservationPage reservation = new CPReservationPage();
		Folio folio = new Folio();
		RoomClass roomClass = new RoomClass();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Login login = new Login();

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		rateName = rateName + randomNumber;
		displayName = rateName;
		lastName = lastName + randomNumber;
		String saluation = "Mr.";
		String reservationNumber = null;

		String seasonStartDate = "";
		String seasonEndDate = "";
		String inncenterHeaderTripTotal = "";
		String dateFormat = "MM/dd/yyyy";
		String checkInDate = Utility.getCurrentDate(dateFormat);
		String checkOutDate = Utility.GetNextDate(1, dateFormat);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			login.login(driver, "https://www.app.qainnroad.com", "autogs", "autouser", "Auto@123");
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {

			app_logs.info("==========CREATE A NEW ROOM CLASS==========");
			testSteps.add("==========CREATE A NEW ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

			navigation.clickOnNewRoomClass(driver);
			getTestSteps.clear();
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbbreviation, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbbreviation);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbbreviation);

			roomClass.closeRoomClassTab(driver);
			testSteps.add("Close created room class tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass

		try {
			testSteps.add("==========CREATE A NEW RATE==========");
			app_logs.info("==========CREATE A NEW RATE==========");

			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.RatesGrid(driver);
			testSteps.add("Navigated to RatesGrid");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlan);
			nightlyRate.enterRatePlanName(driver, rateName, getTestSteps);
			nightlyRate.enterRateFolioDisplayName(driver, displayName, getTestSteps);
			nightlyRate.enterRatePlanDescription(driver, rateDescription, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectChannels(driver, source, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.selectRoomClasses(driver, roomClassName, true, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickNextButton(driver, getTestSteps);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			seasonStartDate = Utility.getDateFromCurrentDate(0);
			seasonEndDate = Utility.getDateFromCurrentDate(4);
			nightlyRate.selectSeasonDates(driver, getTestSteps, seasonStartDate, seasonEndDate);
			nightlyRate.enterSeasonName(driver, getTestSteps, seasonName);
			nightlyRate.clickCreateSeason(driver, getTestSteps);
			nightlyRate.selectSeasonColor(driver, getTestSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, getTestSteps, "No");
			nightlyRate.enterRate(driver, getTestSteps, baseAmount, addtionalAdult, maxAdults, maxPersons,
					addtionalAdult, additionalChild);

			nightlyRate.clickRulesRestrictionOnSeason(driver, getTestSteps);
			nightlyRate.clickSaveSason(driver, getTestSteps);
			nightlyRate.clickCompleteChanges(driver, getTestSteps);
			nightlyRate.clickSaveAsActive(driver, getTestSteps);

			testSteps.add("New Rate '" + rateName + "' Created & Verified ");
			app_logs.info("New Rate '" + rateName + "' Created & Verified");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability
		ArrayList<String> availabilityInRatesBefore = new ArrayList<String>();
		ArrayList<String> availabilityInRatesAfter = new ArrayList<String>();

		String roomReservedValueBefore = "";
		String roomAvailableValueBefore = "";
		String roomReservedValueAfter = "";
		String roomAvailableValueAfter = "";
		String roomTotalValueAfter = "";
		String roomTotalValueBefore = "";
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, getTestSteps);

			ratesGrid.searchRateAndSelectRate(driver, testSteps, rateName);

			availabilityInRatesBefore = getDataOfHash(
					ratesGrid.getAvailabilityOfRoomClass(driver, dateFormat, checkInDate, checkOutDate, roomClassName));

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			}

			ratesGrid.expandRoomClass(driver, getTestSteps, roomClassName);
			ratesGrid.getRoomClassIndex(driver, roomClassName);
			roomReservedValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Reserved");
			roomAvailableValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Available");
			roomTotalValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Total");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String subTotalBE = "";
		String taxAppliedBE = "";
		try {
			driver.navigate().to("https://automationqa.client.qainnroad.com");
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
			getTestSteps.clear();
			getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterChildrenBE(driver, child);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = bookingEngine.clickSearchOfRooms(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = bookingEngine.roomDetailsVerification(driver, roomClassName, baseAmount, maxPersons);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.selectRoomClass(driver, roomClassName);

			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.ratePlanDetailsVerification(driver, rateName, baseAmount);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickBookRoom(driver, rateName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterGuestInfo(driver, firstName, lastName, emailAddress, phone, address,
					address, postalCode, city, state);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
			testSteps.addAll(getTestSteps);
			subTotalBE = bookingEngine.getSubTotal(driver);
			testSteps.add("Sub Total in Booking Engine:" + subTotalBE);
			app_logs.info("Sub Total in Booking Engine:" + subTotalBE);
			taxAppliedBE = bookingEngine.getTaxApplied(driver);
			testSteps.add("Tax Applied in Booking Engine:" + taxAppliedBE);
			app_logs.info("Tax Applied in Booking Engine:" + taxAppliedBE);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickBookStay(driver);
			testSteps.addAll(getTestSteps);
			Wait.wait5Second();
			reservationNumber = bookingEngine.getReservationNumber(driver);
			testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
			app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on Groups
		try {
			login.login(driver, "https://www.app.qainnroad.com", "autogs", "autouser", "Auto@123");
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
			reservation.Search_ResNumber_And_Click(driver, reservationNumber);
			String guestName = firstName + " " + lastName;

			testSteps.add("=================== BANNER FIELDS VERIFICATION ======================");
			app_logs.info("=================== BANNER FIELDS VERIFICATION ======================");
			getTestSteps.clear();
			getTestSteps = reservation.verifyGuestNameAfterReservation(driver, guestName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.verifyStatusAfterReservation(driver, "Reserved");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.verifyStayDateAfterReservation(driver,
					Utility.parseDate(checkInDate, dateFormat, "MMM d"),
					Utility.parseDate(checkOutDate, dateFormat, "MMM d"));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservation.verifyPhoneNoBanner(driver, "1" + phone);
			testSteps.addAll(getTestSteps);

			inncenterHeaderTripTotal = reservation.getTripTotal_Header(driver);
			testSteps.add("Inncenter Trip Total : " + inncenterHeaderTripTotal);
			app_logs.info("Inncenter Trip Total : " + inncenterHeaderTripTotal);
			assertEquals(inncenterHeaderTripTotal, subTotalBE, "Failed Trip Total Mismateched");
			testSteps.add("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);
			app_logs.info("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);

			reservation.Verify_ReservationStatus_Status(driver, testSteps, reservationStatus);

			testSteps.add("=================== STAY INFO FIELDS VERIFICATION=================== ");
			app_logs.info("=================== STAY INFO FIELDS VERIFICATION=================== ");

			String foundArrivalDate = reservation.getArrivalDate_ResDetail(driver);
			assertEquals(Utility.parseDate(foundArrivalDate, "MMM d, yyyy", "dd MMM, yyyy"),
					Utility.parseDate(checkInDate, dateFormat, "dd MMM, yyyy"), "Failed Arrival Date Missmatched");
			testSteps.add("Successfully Verified Arrival Date : " + foundArrivalDate);
			app_logs.info("Successfully Verified Arrival Date : " + foundArrivalDate);

			String foundDepartDate = reservation.getDepartDate_ResDetail(driver);
			assertEquals(Utility.parseDate(foundDepartDate, "MMM d, yyyy", "dd MMM, yyyy"),
					Utility.parseDate(checkOutDate, dateFormat, "dd MMM, yyyy"), "Failed Depart Date Missmatched");
			testSteps.add("Successfully Verified Depart Date : " + foundDepartDate);
			app_logs.info("Successfully Verified Depart Date : " + foundDepartDate);

			String foundAdultsCount = reservation.getNoOfAdults_ResDetail(driver);
			assertEquals(foundAdultsCount, adults, "Failed No Of Adults Missmatched");
			testSteps.add("Successfully Verified No Of Adults : " + foundAdultsCount);
			app_logs.info("Successfully Verified No Of Adults : " + foundAdultsCount);

			String foundNoOfChild = reservation.getNoOfChilds_ResDetail(driver);
			assertEquals(foundNoOfChild, child, "Failed No Of Childs Missmatched");
			testSteps.add("Successfully Verified No Of Childs : " + foundNoOfChild);
			app_logs.info("Successfully Verified No Of Childs : " + foundNoOfChild);

			String foundRoomClass = reservation.getRoomClass_ResDetail(driver);
			assertEquals(foundRoomClass, roomClassName, "Failed Room Class Missmatched");
			testSteps.add("Successfully Verified Room Class : " + foundRoomClass);
			app_logs.info("Successfully Verified Room Class : " + foundRoomClass);

			testSteps.add("=================== GUEST INFO VERIFICATION ======================");
			app_logs.info("=================== GUEST INFO VERIFICATION ======================");

			String foundGuestName = reservation.getGuestName_ResDetail(driver);
			assertEquals(foundGuestName, guestName, "Failed Guest Name Missmatched");
			testSteps.add("Successfully Verified Guest Name : " + foundGuestName);
			app_logs.info("Successfully Verified Guest Name : " + foundGuestName);

			String foundContactName = reservation.getContactName_ResDetail(driver);
			assertEquals(foundContactName, paymentType, "Failed Contact Name	 Missmatched");
			testSteps.add("Successfully Verified Contact Name : " + foundContactName);
			app_logs.info("Successfully Verified Contact Name : " + foundContactName);

			String foundEmail = reservation.getEmail_ResDetail(driver);

			testSteps.add("Found Email : " + foundEmail);
			app_logs.info("Found Email : " + foundEmail);

			String foundPhoneNo = reservation.getPhoneNO_ResDetail(driver);
			assertEquals(foundPhoneNo, "1" + phone, "Failed PhoneNo Missmatched");
			testSteps.add("Successfully Verified PhoneNo : " + foundPhoneNo);
			app_logs.info("Successfully Verified PhoneNo : " + foundPhoneNo);

			testSteps.add("=================== PAYMENT INFO VERIFICATION ======================");
			app_logs.info("=================== PAYMENT INFO VERIFICATION ======================");

			getTestSteps.clear();
			getTestSteps = reservation.verifyPaymentDetail_ResDetail(driver, paymentType, cardNumber.substring(12, 16),
					cardName, cardExpDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("=================== MARKETING INFO VERIFICATION ======================");
			app_logs.info("=================== MARKETING INFO VERIFICATION ======================");

			String foundSource = reservation.get_Reservationsource(driver, testSteps);
			assertEquals(foundSource, source, "Failed Source Missmatched");
			testSteps.add("Successfully Verified Source : " + foundSource);
			app_logs.info("Successfully Verified Source : " + foundSource);

			String foundMarketSegment = reservation.get_MarketSegemnt(driver, testSteps);
			assertEquals(foundMarketSegment, marketSegment, "Failed Market Segment Missmatched");
			testSteps.add("Successfully Verified Market Segment : " + foundMarketSegment);
			app_logs.info("Successfully Verified Market Segment : " + foundMarketSegment);

			String foundReferal = reservation.get_Refferal(driver, testSteps);
			assertEquals(foundReferal, referral, "Failed Referal Missmatched");
			testSteps.add("Successfully Verified Referal : " + foundReferal);
			app_logs.info("Successfully Verified Referal : " + foundReferal);

			testSteps.add("=================== VERIFY RATE PLAN ======================");
			app_logs.info("=================== VERIFY RATE PLAN ======================");
			String foundRate = reservation.get_RatePlan(driver, testSteps);
			assertEquals(foundRate, rateName, "Failed RatePlan Missmatched");

			testSteps.add("Verified Rate in InnCenter:" + foundRate);
			app_logs.info("Verified Rate in InnCenter:" + foundRate);
			reservation.close_FirstOpenedReservation(driver, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability
		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, getTestSteps);
			ratesGrid.searchRateAndSelectRate(driver, testSteps, rateName);

			availabilityInRatesAfter = getDataOfHash(
					ratesGrid.getAvailabilityOfRoomClass(driver, dateFormat, checkInDate, checkOutDate, roomClassName));
			int difference = Integer
					.parseInt(Utility.differenceBetweenDates(Utility.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
							Utility.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy")));

			for (int i = 0; i < difference; i++) {

				String found = Integer.toString((Integer.parseInt(availabilityInRatesBefore.get(i)) - 1));
				assertEquals(availabilityInRatesAfter.get(i), found, "Failed:rooms Available didn't verify");
				testSteps.add("Available Room In Rates Grid Before:" + availabilityInRatesBefore.get(i));
				app_logs.info("Available Room In Rates Grid Before:" + availabilityInRatesBefore.get(i));
				testSteps.add("Available Room In Rates Grid After:" + found);
				app_logs.info("Available Room In Rates Grid After:" + found);

			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify ", testName, "Verification", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			// Verify Total
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
			}
			ratesGrid.expandRoomClass(driver, getTestSteps, roomClassName);
			roomReservedValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Reserved");
			roomAvailableValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Available");
			roomTotalValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Total");
			assertEquals(roomTotalValueBefore, roomTotalValueAfter, "Failed:rooms Total didn't verify");
			testSteps.add("Total Room In Availability Grid Before:" + roomTotalValueBefore);
			app_logs.info("Total Room In Availability Grid Before:" + roomTotalValueBefore);
			testSteps.add("Total Room In Availability Grid After:" + roomTotalValueAfter);
			app_logs.info("Total Room In Availability Grid After:" + roomTotalValueAfter);
			String found = Integer.toString((Integer.parseInt(roomAvailableValueBefore) - 1));
			assertEquals(roomAvailableValueAfter, found, "Failed:rooms Available didn't verify");
			testSteps.add("Available Room In Availability Grid Before:" + roomAvailableValueBefore);
			app_logs.info("Available Room In Availability Grid Before:" + roomAvailableValueBefore);
			testSteps.add("Available Room In Availability Grid After:" + found);
			app_logs.info("Available Room In Availability Grid After:" + found);
			found = Integer.toString((Integer.parseInt(roomReservedValueBefore) + 1));
			assertEquals(roomReservedValueAfter, found, "Failed:rooms Reserved didn't verify");
			testSteps.add("Reserved Room In Availability Grid Before:" + roomReservedValueBefore);
			app_logs.info("Reserved Room In Availability Grid Before:" + roomReservedValueBefore);
			testSteps.add("Reserved Room In Availability Grid After:" + found);
			app_logs.info("Reserved Room In Availability Grid After:" + found);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAvailability_ResFromBE", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
