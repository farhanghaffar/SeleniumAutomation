package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import com.innroad.inncenter.waits.Wait;
import io.restassured.response.Response;
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
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyChangeAirbnbRoomClassInRes extends TestCore {

	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
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

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Airbnb")

	public void verifyChangeAirbnbRoomClassInRes(String beginDate, String noOfNights, String roomClassName,
			String updatedRoomClass, String firstName, String lastName, String emails, String adultCount,
			String childCount, String phoneNum, String baseprice, String payoutamount, String actionTaken,
			String isInncenterTaxCreate, String intaxname, String intaxValue, String categoryTaxs, String taxType,
			String ledgerAccount, String action) {
		String testName = null, startDate = null, endDate = null, confirmationNo = null, startDate1 = null,
				endDate1 = null, listingid = null, updatedListingId = null, ratePlanName = "", currency = null,
				night = "", nightsCalculated = "", updatedCheckoutDate = "", updatedCheckoutDate1 = "",
				updatedNight = "", guestFirstName = firstName + Utility.generateRandomStringWithGivenLength(3),
				guestName = "";
		Response checkAvailabilityresponse = null;
		Response makeReservationResponse = null;
		Response updateReservationResponse = null;
		HashMap<String, String> getAirbnbDetails = null;
		HashMap<String, String> getUpdatedAirbnbDetails = null;
		String externalConfirmation = Utility.generateRandomStringWithGivenLength(5);
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValues = Utility.convertTokenToArrayList(intaxValue, Utility.DELIMS);
		ArrayList<String> categoryTaxes = Utility.convertTokenToArrayList(categoryTaxs, Utility.DELIMS);
		ArrayList<String> intaxnames = Utility.convertTokenToArrayList(intaxname, Utility.DELIMS);
		ArrayList<String> taxTypes = Utility.convertTokenToArrayList(taxType, Utility.DELIMS);
		ArrayList<String> stayDates = new ArrayList<String>();
		ArrayList<String> updatestayDates = new ArrayList<String>();
		ArrayList<String> checkInDates = new ArrayList<>();
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		HashMap<String, String> updatesetTaxRates = new HashMap<String, String>();
		RatesGrid ratesGrid = new RatesGrid();
		String calendarTodayDate = "";
		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeReservedCountListForUpdateRc = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableCountListForUpdateRc = new ArrayList<>();
		String beforeReservedCount = null;
		String beforeAvailableRooms = null;

		String beforeReservedCountUpdateRc = null;
		String beforeAvailableRoomsUpdateRc = null;

		ArrayList<Integer> afterReservedCountList = new ArrayList<>();
		ArrayList<Integer> afterReservedCountListForUpdateRc = new ArrayList<>();
		ArrayList<Integer> afterAvailableRoomsCountList = new ArrayList<>();
		ArrayList<Integer> afterAvailableCountListForUpdateRc = new ArrayList<>();
		String afterReservedCount = null;
		String afterAvailableRooms = null;

		String afterReservedCountUpdateRc = null;
		String afterAvailableRoomsUpdateRc = null;
		String defaultDateFormat = "dd/MM/yyyy";
		String dateFormatForRateGrid = "MMM/d/yyyy";
		String accountName = "Airbnb Corporate Account", source = "Airbnb";
		try {
			testName = "verifyChangeAirbnbRoomClassInRes" + " " + action;
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
			Utility.refreshPage(driver);
			getUpdatedAirbnbDetails = airbnbIn.getAirbnbListingId(driver, updatedRoomClass, property);
			updatedListingId = getUpdatedAirbnbDetails.get("listingId");
			app_logs.info("Listing ID " + getUpdatedAirbnbDetails.get("listingId"));
			navigation.navigateToSetupfromRateGrid(driver);
			navigation.clickTaxesAndFees(driver);
			test_steps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");

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

			if (action.equalsIgnoreCase("extendReservation") || action.equalsIgnoreCase("reduceResrevation")
					|| action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
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

			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", test_steps);
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("==================CREATE TAX IN INNCENTER==================");
				app_logs.info("==================CREATE TAX IN INNCENTER==================");
				for (int i = 0; i < intaxnames.size(); i++) {
					String upTaxName = intaxnames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedTaxName.add(upTaxName);
				}
				for (int i = 0; i < intaxnames.size(); i++) {
					taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
							taxTypes.get(i), categoryTaxes.get(i), intaxValues.get(i), "", ledgerAccount, false, "", "",
							"", test_steps);

					test_steps.add("===========================================================");
					if (categoryTaxes.get(i).equalsIgnoreCase("flat")) {
						setTaxRates.put(updatedTaxName.get(i), intaxValues.get(i));
						if (action.equalsIgnoreCase("extendReservation")
								|| action.equalsIgnoreCase("reduceResrevation")) {
							updatesetTaxRates.put(updatedTaxName.get(i), intaxValues.get(i));
						}
					} else {
						String calcValOfPercent = reservation.getPercentcalcvalueSingleItem(driver, intaxValues.get(i),
								baseprice, Integer.parseInt(night));
						setTaxRates.put(updatedTaxName.get(i), calcValOfPercent);

						if (action.equalsIgnoreCase("extendReservation")
								|| action.equalsIgnoreCase("reduceResrevation")) {
							String calcValOfPercentup = reservation.getPercentcalcvalueSingleItem(driver,
									intaxValues.get(i), baseprice, Integer.parseInt(updatedNight));
							updatesetTaxRates.put(updatedTaxName.get(i), calcValOfPercentup);
						}
					}
				}
				System.out.println("setTaxRates: " + setTaxRates);
			}
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Tax And fee", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create Tax And fee", testName, test_description,
					test_catagory, test_steps);
		}

		checkAvailabilityresponse = airbnb.checkAvailability(listingid, startDate, night,
				vrvoproperties.getProperty("BookingRequestToken"));

		if (checkAvailabilityresponse.asString().contains("true")) {
			test_steps.add("Check Availability Endpoints returns True");
			app_logs.info("Check Availability Endpoints returns True");
			try {
				makeReservationResponse = airbnb.makeReservationCheck(listingid, adultCount, childCount, night,
						startDate, externalConfirmation, vrvoproperties.getProperty("BookingRequestToken"));

				if (makeReservationResponse.asString().contains("true")) {
					test_steps.add("Make Reservation Endpoint returns True");
					app_logs.info("Make Reservation Endpoint returns True");
					guestName = guestFirstName + " " + lastName;
					test_steps.add("<b>===== CREATE AIRBNB RESERVATION =====</b>");
					app_logs.info("=====CREATE AIRBNB RESERVATION=====");

					airbnb.airbnbReservationActions("create", externalConfirmation, startDate, endDate, guestFirstName,
							lastName, listingid, night, emails, adultCount, childCount, phoneNum, baseprice,
							payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
					test_steps.add("Created Airbnb reservation successfully: ");
				}

			} catch (Exception e) {
				Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);

			} catch (Error e) {
				Utility.catchErrorOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);
			}
			if (makeReservationResponse.asString().contains("true")) {
				try {
					test_steps.add("<b>===== VERIFY RESERVATION DETAILS IN INNCENTER=====</b>");
					app_logs.info("=====VERIFY RESERVATION DETAILS IN INNCENTER=====");
					navigation.reservationBackward3(driver);
					confirmationNo = reservation.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true, 12);
					test_steps.add("Verified reservation existing in inncenter");
					app_logs.info("Confirmation No : " + confirmationNo);
					test_steps.add("Confirmation No : " + confirmationNo);

					reservation.verifyAssociatedAccount(driver, accountName);
					test_steps.add("Verified account name Displayed in reservation: " + accountName);
					test_steps.add("===================== Source validation in InnCenter =========================");
					app_logs.info("===================== Source validation in InnCenter =========================");
					String foundsource = reservation.get_Reservationsource(driver, test_steps);
					assertEquals(foundsource, source, "Failed verify source");
					test_steps.add("Verify source: " + foundsource);
					test_steps.add(
							"===================== Banner fields validation in InnCenter =========================");
					app_logs.info(
							"===================== Banner fields validation in InnCenter =========================");
					getTest_Steps.clear();
					getTest_Steps = reservation.verifyGuestNameAfterReservation(driver, guestName);
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					getTest_Steps = reservation.verifyStatusAfterReservation(driver, "Reserved");
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					getTest_Steps = reservation.verifyStayDateAfterReservation(driver,
							Utility.parseDate(startDate1, "dd/MM/yyyy", "MMM d"),
							Utility.parseDate(endDate1, "dd/MM/yyyy", "MMM d"));
					test_steps.addAll(getTest_Steps);
					getTest_Steps.clear();
					String foundContactName = reservation.getContactName_ResDetail(driver);
					assertEquals(foundContactName, guestName, "Failed Contact Name Missmatched");
					test_steps.add("Successfully Verified Contact Name : " + foundContactName);
					app_logs.info("Successfully Verified Contact Name : " + foundContactName);

					String foundEmail = reservation.getEmail_ResDetail(driver);
					assertEquals(foundEmail, emails, "Failed Email Missmatched");
					test_steps.add("Found Email : " + foundEmail);
					app_logs.info("Found Email : " + foundEmail);
					String foundPhoneNo = reservation.getPhoneNO_ResDetail(driver);
					assertEquals(foundPhoneNo, "0" + phoneNum.replace("-", "").trim(), "Failed PhoneNo Missmatched");
					test_steps.add("Successfully Verified PhoneNo : " + foundPhoneNo);
					app_logs.info("Successfully Verified PhoneNo : " + foundPhoneNo);
					test_steps.add(
							"===================== Stay Info fields validation in InnCenter =========================");
					app_logs.info(
							"===================== Stay Info fields validation in InnCenter =========================");
					String foundArrivalDate = reservation.getArrivalDate_ResDetail(driver);
					assertEquals(Utility.parseDate(foundArrivalDate, "dd MMM, yyyy", "dd MMM, yyyy"),
							Utility.parseDate(startDate1, "dd/MM/yyyy", "dd MMM, yyyy"),
							"Failed Arrival Date Missmatched");
					test_steps.add("Successfully Verified Arrival Date : " + foundArrivalDate);
					app_logs.info("Successfully Verified Arrival Date : " + foundArrivalDate);
					String foundDepartDate = reservation.getDepartDate_ResDetail(driver);
					assertEquals(Utility.parseDate(foundDepartDate, "dd MMM, yyyy", "dd MMM, yyyy"),
							Utility.parseDate(endDate1, "dd/MM/yyyy", "dd MMM, yyyy"),
							"Failed Depart Date Missmatched");
					test_steps.add("Successfully Verified Depart Date : " + foundDepartDate);
					app_logs.info("Successfully Verified Depart Date : " + foundDepartDate);
					nightsCalculated = Utility.differenceBetweenDates(startDate1, endDate1);
					String foundNoOfNights = reservation.getNoOfNights_ResDetail(driver);
					assertEquals(foundNoOfNights, nightsCalculated + "N", "Failed No Of Nights Missmatched");
					test_steps.add("Successfully Verified No Of Nights : " + foundNoOfNights);
					app_logs.info("Successfully Verified No Of Nights : " + foundNoOfNights);
					String foundAdultsCount = reservation.getNoOfAdults_ResDetail(driver);
					assertEquals(foundAdultsCount, adultCount, "Failed No Of Adults Missmatched");
					test_steps.add("Successfully Verified No Of Adults : " + foundAdultsCount);
					app_logs.info("Successfully Verified No Of Adults : " + foundAdultsCount);
					String foundNoOfChild = reservation.getNoOfChilds_ResDetail(driver);
					assertEquals(foundNoOfChild, childCount, "Failed No Of Childs Missmatched");
					test_steps.add("Successfully Verified No Of Childs : " + foundNoOfChild);
					app_logs.info("Successfully Verified No Of Childs : " + foundNoOfChild);
					String foundRoomClass = reservation.getRoomClass_ResDetail(driver);
					assertEquals(foundRoomClass, roomClassName, "Failed Room Class Missmatched");
					test_steps.add("Successfully Verified Room Class : " + foundRoomClass);
					app_logs.info("Successfully Verified Room Class : " + foundRoomClass);

					String getRoomCharges = reservation.getRoomChargesInTripSummary(driver);
					assertEquals(baseprice, Utility.convertDollarToNormalAmount(driver, getRoomCharges),
							"Failed to verify");
					test_steps.add("Verify Room Charge : " + getRoomCharges);
					app_logs.info("Verify Room Charge : " + getRoomCharges);
					reservation.clickFolio(driver, test_steps);
					if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
						test_steps.add(
								"===================== VERIFY TAXES IN AIRBNB RESERVATION =========================");
						app_logs.info(
								"===================== VERIFY TAXES IN AIRBNB RESERVATION =========================");
						airbnbIn.verifyChildLineItemTaxes(driver, test_steps, ledgerAccount, updatedTaxName,
								setTaxRates, datesRangeList);
					}
					airbnbIn.selectAirbnbAccout(driver, test_steps, accountName);
					Double perDayRoomCharge = Double.parseDouble(baseprice) / Integer.parseInt(night);
					test_steps.add(
							"===================== VERIFY FOLIO LINE ITEM IN RESERVATION =========================");
					app_logs.info(
							"===================== VERIFY FOLIO LINE ITEM IN RESERVATION =========================");
					for (int i = 0; i < datesRangeList.size(); i++) {
						System.out.println(Utility.capitalizeWord(ratePlanName));
						reservation.verifyFolioLineItem(driver, datesRangeList.get(i), ledgerAccount, "Airbnb Rate",
								String.valueOf(perDayRoomCharge), test_steps);
					}
					reservation.clickOnHistory(driver);
					reservation.verifyResInHistory(driver, confirmationNo);
					test_steps.add("Verify history tab have reservation creation log");

				} catch (Exception e) {
					Utility.catchExceptionOTA(e, test_description, test_catagory,
							"Verify Airbnb Reservation In Inncenrer", testName, test_description, test_catagory,
							test_steps);
				} catch (Error e) {
					Utility.catchErrorOTA(e, test_description, test_catagory, "Verify Airbnb Reservation In Inncenrer",
							testName, test_description, test_catagory, test_steps);
				}
			}
			try {
				test_steps.add(
						"===================== GET AVAILABILITY OF ROOM CLASS IN INNCENTER BEFORE UPDATE RESERVATION =========================");
				app_logs.info(
						"===================== GET AVAILABILITY OF ROOM CLASS IN INNCENTER BEFORE UPDATE RESERVATION =========================");
				navigation.Inventory(driver);
				test_steps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
				navigation.Rates_Grid(driver);
				test_steps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");
				ratesGrid.verifyRatesGridLoaded(driver);
				for (String getDate : datesRangeList) {

					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					ratesGrid.clickOnAvailabilityTab(driver);
					ratesGrid.clickCalendar(driver);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, test_steps);
					int dateIndexs = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

					ratesGrid.expandRoomClass(driver, roomClassName, test_steps);
					beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Reserved");
					app_logs.info("Before Reserved Count : " + beforeReservedCount);
					beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
					beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Available");
					app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
					beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

					ratesGrid.reduceRoomClass(driver, roomClassName, test_steps);
					test_steps.add("Before Reserved Count : " + roomClassName + " " + beforeReservedCountList);
					test_steps.add("Before Available Rooms : " + roomClassName + " " + beforeAvailableRoomsCountList);

					test_steps.add("==========================================");
					app_logs.info("===========================================");
					ratesGrid.expandRoomClass(driver, updatedRoomClass, test_steps);
					beforeReservedCountUpdateRc = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Reserved");
					app_logs.info("Before Reserved Count : " + beforeReservedCountUpdateRc);
					beforeReservedCountListForUpdateRc.add(Integer.parseInt(beforeReservedCountUpdateRc));
					beforeAvailableRoomsUpdateRc = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Available");
					app_logs.info("Before Available Rooms : " + beforeAvailableRoomsUpdateRc);
					beforeAvailableCountListForUpdateRc.add(Integer.parseInt(beforeAvailableRoomsUpdateRc));

					ratesGrid.reduceRoomClass(driver, updatedRoomClass, test_steps);

					test_steps.add("Before Reserved Count for : " + updatedRoomClass + " "
							+ beforeReservedCountListForUpdateRc);
					test_steps.add("Before Available Rooms for : " + updatedRoomClass + " "
							+ beforeAvailableCountListForUpdateRc);

				}

			} catch (Exception e) {
				Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Tax And fee", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchErrorOTA(e, test_description, test_catagory, "Create Tax And fee", testName,
						test_description, test_catagory, test_steps);
			}

			try {
				updateReservationResponse = airbnb.updateReservationCheck(updatedListingId, adultCount, childCount,
						night, startDate, externalConfirmation, vrvoproperties.getProperty("BookingRequestToken"));
				if (updateReservationResponse.asString().contains("true")) {
					test_steps.add(
							"===================== UPDATE AIRBNB RESERVATION ========================= " + actionTaken);
					app_logs.info(
							"===================== UPDATE AIRBNB RESERVATION ========================= " + actionTaken);
					app_logs.info("Update Reservation return true");
					test_steps.add("Update Reservation return true");

					airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, endDate,
							guestFirstName, lastName, updatedListingId, night, emails, adultCount, childCount, phoneNum,
							baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));

					test_steps.add("updated reservation: " + action);

					Wait.wait30Second();
					navigation.reservationBackward3(driver);
					reservation.guestUnlimitedSearchAndOpen(driver, test_steps, guestName, true, 12);
					test_steps.add("================VERIFY UPDATED RESERVATION IN INNCENTER=====================");
					app_logs.info("================VERIFY UPDATED RESERVATION IN INNCENTER=====================");
					String foundRoomClass = reservation.getRoomClass_ResDetail(driver);
					assertEquals(foundRoomClass, updatedRoomClass, "Failed Room Class Missmatched");
					test_steps.add("Successfully Verified Room Class : " + foundRoomClass);
					app_logs.info("Successfully Verified Room Class :" + foundRoomClass);
				}

			} catch (Exception e) {
				Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchErrorOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);
			}
			try {
				if (updateReservationResponse.asString().contains("true")) {
					test_steps.add(
							"===================== GET AVAILABILITY OF ROOM CLASS IN INNCENTER AFTER UPDATE RESERVATION =========================");
					app_logs.info(
							"===================== GET AVAILABILITY OF ROOM CLASS IN INNCENTER AFTER UPDATE RESERVATION =========================");
					navigation.Inventory(driver);
					test_steps.add("Navigate Inventory");
					app_logs.info("Navigate Inventory");
					navigation.Rates_Grid(driver);
					test_steps.add("Navigate Rates Grid");
					app_logs.info("Navigate Rates Grid");
					ratesGrid.verifyRatesGridLoaded(driver);
					for (String getDate : datesRangeList) {

						calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
						app_logs.info(calendarTodayDate);
						ratesGrid.clickOnAvailabilityTab(driver);
						ratesGrid.clickCalendar(driver);

						ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid,
								test_steps);
						int dateIndexs = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate,
								dateFormatForRateGrid);

						ratesGrid.expandRoomClass(driver, roomClassName, test_steps);
						afterReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Reserved");
						app_logs.info("Before Reserved Count : " + afterReservedCount);
						afterReservedCountList.add(Integer.parseInt(afterReservedCount));
						afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Available");
						app_logs.info("Before Available Rooms : " + afterAvailableRooms);
						afterAvailableRoomsCountList.add(Integer.parseInt(afterAvailableRooms));

						ratesGrid.reduceRoomClass(driver, roomClassName, test_steps);
						test_steps.add("Before Reserved Count : " + roomClassName + " " + afterReservedCountList);
						test_steps
								.add("Before Available Rooms : " + roomClassName + " " + afterAvailableRoomsCountList);

						test_steps.add("==========================================");
						app_logs.info("===========================================");
						ratesGrid.expandRoomClass(driver, updatedRoomClass, test_steps);
						afterReservedCountUpdateRc = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Reserved");
						app_logs.info("Before Reserved Count : " + afterReservedCountUpdateRc);
						afterReservedCountListForUpdateRc.add(Integer.parseInt(afterReservedCountUpdateRc));
						afterAvailableRoomsUpdateRc = ratesGrid.getRoomClassDataValue(driver, dateIndexs, "Available");
						app_logs.info("Before Available Rooms : " + afterAvailableRoomsUpdateRc);
						afterAvailableCountListForUpdateRc.add(Integer.parseInt(afterAvailableRoomsUpdateRc));

						ratesGrid.reduceRoomClass(driver, updatedRoomClass, test_steps);

						test_steps.add("Before Reserved Count for : " + updatedRoomClass + " "
								+ afterReservedCountListForUpdateRc);
						test_steps.add("Before Available Rooms for : " + updatedRoomClass + " "
								+ afterAvailableCountListForUpdateRc);

					}
				}
				test_steps.add(
						"====================== VERIFY AVAILABILITY OF ROOM CLASS IN INNCENTER AFTER UPDATE RESERVATION ====================");
				app_logs.info(
						"=================== VERIFY AVAILABILITY OF ROOM CLASS IN INNCENTER AFTER UPDATE RESERVATIO========================");
				for (int i = 0; i < datesRangeList.size(); i++) {
					int beforeAvailable = 0, afteravailable = 0, beforeReserved = 0, afterReserved = 0;
					beforeAvailable = (beforeAvailableRoomsCountList.get(i) + 1);
					afteravailable = afterAvailableRoomsCountList.get(i);
					assertEquals(afteravailable, beforeAvailable, "Failed to verify");
					test_steps.add("Verified availability room class: " + roomClassName + " for Date "
							+ datesRangeList.get(i));
					app_logs.info("Verified availability room class: " + roomClassName + " for Date "
							+ datesRangeList.get(i));

					afterReserved = afterReservedCountList.get(i) + 1;
					beforeReserved = beforeReservedCountList.get(i);
					assertEquals(afterReserved, beforeReserved, "Failed to verify");
					test_steps.add("Verified Reserved count room class: " + roomClassName + " for Date "
							+ datesRangeList.get(i));
					app_logs.info("Verified Reserved count room class: " + roomClassName + " for Date "
							+ datesRangeList.get(i));

				}
				for (int i = 0; i < datesRangeList.size(); i++) {
					int beforeAvailableUp = 0, afteravailableUp = 0, beforeReservedUp = 0, afterReservedUp = 0;
					afteravailableUp = afterAvailableCountListForUpdateRc.get(i) + 1;
					beforeAvailableUp = beforeAvailableCountListForUpdateRc.get(i);
					assertEquals(afteravailableUp, beforeAvailableUp, "Failed to verify");
					test_steps.add("Verified availability room class: " + updatedRoomClass + " for Date "
							+ datesRangeList.get(i));
					app_logs.info("Verified availability room class: " + updatedRoomClass + " for Date"
							+ datesRangeList.get(i));

					beforeReservedUp = beforeReservedCountListForUpdateRc.get(i) + 1;
					afterReservedUp = afterReservedCountListForUpdateRc.get(i);
					assertEquals(afterReservedUp, beforeReservedUp);
					test_steps.add("Verified Reserved count room class: " + updatedRoomClass + " for Date "
							+ datesRangeList.get(i));
					app_logs.info("Verified Reserved count room class: " + updatedRoomClass + " for Date "
							+ datesRangeList.get(i));
				}

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			} catch (Exception e) {
				Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchErrorOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);
			}
		} else {
			app_logs.info("There is no availability for the stay reservation looking for As Check availability False ");
			test_steps
					.add("There is no availability for the stay reservation looking for As Check availability False ");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyChangeAirbnbRoomClassInRe", airbnbexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
