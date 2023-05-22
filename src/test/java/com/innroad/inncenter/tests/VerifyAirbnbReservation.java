package com.innroad.inncenter.tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.text.ParseException;
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
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAirbnbReservation extends TestCore {
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

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Airbnb")
	public void verifyAirbnbReservation(String beginDate, String noOfNights, String roomClassName, String firstName,
			String lastName, String emails, String adultCount, String childCount, String phoneNum, String baseprice,
			String payoutamount, String actionTaken, String isInncenterTaxCreate, String intaxname, String intaxValue,
			String categoryTaxs, String taxType, String ledgerAccount, String extendOrResduceDays,
			String updateFirstName, String updateLastName, String updateAdultCount, String updateChildCount,
			String action) throws ParseException, InterruptedException {
		String TestCaseID ="802141|802143|802300|802301|802302|802303";
		if(Utility.getResultForCase(driver, TestCaseID)) {
		String testName = null, startDate = null, endDate = null, confirmationNo = null, startDate1 = null,
				endDate1 = null, listingid = null, ratePlanName = "", upGuestName = "", currency = null, night = "",
				nightsCalculated = "", updatedCheckoutDate = "", updatedCheckoutDate1 = "", updatedNight = "",
				guestFirstName = firstName + Utility.generateRandomStringWithGivenLength(3), guestName = "",
				upFName = "", availibilityCount = null;
		Response checkAvailabilityresponse = null;
		Response makeReservationResponse = null;
		Response updateReservationResponse = null;
		HashMap<String, String> getAirbnbDetails = null;
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
		boolean isBlackedOut = false;
		int dateIndex = 0;
		String accountName = "Airbnb Corporate Account", source = "Airbnb";
		Utility.initializeTestCase("802141|802143|802300|802301|802302|802303", Utility.testId, Utility.statusCode, Utility.comments, "");
		try {
			testName = "VerifyAirbnbReservation"+ " " + action;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("#################################################################################");

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
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

			navigation.navigateToSetupfromRateGrid(driver);
			navigation.clickTaxesAndFees(driver);
			test_steps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");

			if (!(Utility.validateInput(beginDate))) {
			/*	startDate1 = Utility.getCurrentDate("dd/MM/yyyy");*/
				startDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					/*endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");*/
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					/*endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)),
							"MM/dd/yyyy", "dd/MM/yyyy");*/
					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			} else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1 = checkInDates.get(0);
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.addDays(startDate1, 3);
				} else {

					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			}
			if (action.equalsIgnoreCase("extendReservation") || action.equalsIgnoreCase("reduceResrevation")
					|| action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
				/*if (!(Utility.validateString(extendOrResduceDays))) {*/
				if (!Utility.validateString(extendOrResduceDays)) {
					if (action.equalsIgnoreCase("extendReservation")
							|| action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
						updatedCheckoutDate1 = Utility.addDays(endDate1, 1);
					} else {
						updatedCheckoutDate1 = Utility.addDays(endDate1, -1);
					}
				} else {
					if (action.equalsIgnoreCase("extendReservation")
							|| action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
						updatedCheckoutDate1 = Utility.addDays(endDate1, Integer.parseInt(extendOrResduceDays));
					} else {
						updatedCheckoutDate1 = Utility.addDays(endDate1, -Integer.parseInt(extendOrResduceDays));
					}
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
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Tax And fee", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Create Tax And fee", testName, test_description,
					test_catagory, test_steps);
		}
		if (action.equalsIgnoreCase("checkAvailabilityWhenBlackout")) {
			navigation.reservationBackward3(driver);
			navigation.inventory(driver);
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
			availibilityCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			app_logs.info(availibilityCount);
			test_steps.add("Availibility Count is:-" + availibilityCount);
			ratesGrid.activeOrBlackoutChannel(driver, datesRangeList.get(datesRangeList.size() - 1), "dd/MM/yyyy",
					roomClassName, "Airbnb", "");
			isBlackedOut = ratesGrid.getBlackoutStatus(driver, dateIndex, "Airbnb", roomClassName);
			app_logs.info("Mark  Blacked Out Successfully ");
			test_steps.add("Mark  Blacked Out Successfully ");
		}
		checkAvailabilityresponse = airbnb.checkAvailability(listingid, startDate, night,
				vrvoproperties.getProperty("BookingRequestToken"));
		if (action.equalsIgnoreCase("checkAvailabilityWhenBlackout")) {
			assertTrue(!checkAvailabilityresponse.asString().contains("true"));
			app_logs.info(
					"Verified no availability over required stay on: AIRBNB CHECK AVAILABILITY RESERVATION ENDPOINT");
			test_steps.add(
					"Verified no availability over required stay on: AIRBNB CHECK AVAILABILITY RESERVATION ENDPOINT");
			Utility.testCasePass(Utility.statusCode,1,Utility.comments,"verify room availability by Rates V2 blackout for room class");
			test_steps.add("verify room availability by Rates V2 blackout for room class"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802142' target='_blank'>"
				+ "Click here to open TestRail: 802142</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);

		}
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
					Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify create reservation");
					test_steps.add("Verify create reservation"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802141' target='_blank'>"
						+ "Click here to open TestRail: 802141</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
					
					Utility.testCasePass(Utility.statusCode,3,Utility.comments,"Verify create reservation");
					test_steps.add("Verify create reservation"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802300' target='_blank'>"
						+ "Click here to open TestRail: 802300</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
				}

			} catch (Exception e) {
				Utility.catchExceptionOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName,
						test_description, test_catagory, test_steps);

			} catch (Error e) {
				Utility.catchErrorOTA(e, test_description, test_catagory, "Create Airbnb Reservation", testName, test_description,
						test_catagory, test_steps);
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
						if(i>1) {
							Double perDayRoomCharge1=perDayRoomCharge-0.01;
							reservation.verifyFolioLineItem(driver, datesRangeList.get(i), ledgerAccount, "Airbnb Rate",
									String.valueOf(perDayRoomCharge1), test_steps);
						}else {
						reservation.verifyFolioLineItem(driver, datesRangeList.get(i), ledgerAccount, "Airbnb Rate",
								String.valueOf(perDayRoomCharge), test_steps);}
					}
					reservation.clickOnHistory(driver);
					reservation.verifyResInHistory(driver, confirmationNo);
					test_steps.add("Verify history tab have reservation creation log");
					if (action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
						int datesSize = updatestayDates.size();
						test_steps.add("===============DO BLACKOUT ON AVAILABILITY GRID ON DATE============: "
								+ updatestayDates.get(datesSize - 1));

						navigation.inventory(driver);
						test_steps.add("Navigated to Inventory");
						navigation.ratesGrid(driver, test_steps);
						ratesGrid.verifyRatesGridLoaded(driver);
						getTest_Steps.clear();
						getTest_Steps = ratesGrid.clickOnAvailability(driver);
						ratesGrid.verifyRatesGridLoaded(driver);
						test_steps.addAll(getTest_Steps);
						ratesGrid.expandRoomClass(driver, test_steps, roomClassName);

						dateIndex = ratesGrid.getHeadingDateIndex(driver, updatestayDates.get(datesSize - 1),
								"dd/MM/yyyy");
						availibilityCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
						app_logs.info(availibilityCount);
						test_steps.add("Availibility Count is:-" + availibilityCount);
						ratesGrid.activeOrBlackoutChannel(driver, updatestayDates.get(datesSize - 1), "dd/MM/yyyy",
								roomClassName, "Airbnb", "");
						isBlackedOut = ratesGrid.getBlackoutStatus(driver, dateIndex, "Airbnb", roomClassName);
						app_logs.info("Mark  Blacked Out Successfully ");
						test_steps.add("Mark  Blacked Out Successfully ");
					}
				} catch (Exception e) {
					Utility.catchExceptionOTA(e, test_description, test_catagory, "Verify Airbnb Reservation In Inncenrer", testName,
							test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchErrorOTA(e, test_description, test_catagory, "Verify Airbnb Reservation In Inncenrer", testName,
							test_description, test_catagory, test_steps);
				}

				try {

					if (action.equalsIgnoreCase("extendReservation") || action.equalsIgnoreCase("reduceResrevation")
							|| action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {

						updateReservationResponse = airbnb.updateReservationCheck(listingid, adultCount, childCount,
								updatedNight, startDate, externalConfirmation,
								vrvoproperties.getProperty("BookingRequestToken"));
						if (action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
							assertTrue(!updateReservationResponse.asString().contains("true"));
							app_logs.info(
									"Verified no availability over extended stay on: AIRBNB UPDATE RESERVATION ENDPOINT");
							test_steps.add(
									"Verified no availability over extended stay on: AIRBNB UPDATE RESERVATION ENDPOINT");

						}

						if (updateReservationResponse.asString().contains("true")) {
							test_steps.add("===================== UPDATE AIRBNB RESERVATION ========================= "
									+ actionTaken);
							app_logs.info("===================== UPDATE AIRBNB RESERVATION ========================= "
									+ actionTaken);
							app_logs.info("Update Reservation return true");
							test_steps.add("Update Reservation return true");

							airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate,
									updatedCheckoutDate, guestFirstName, lastName, listingid, updatedNight, emails,
									adultCount, childCount, phoneNum, baseprice, payoutamount,
									vrvoproperties.getProperty("BookingRequestToken"));

							test_steps.add("updated reservation: " + actionTaken);
						} else {
							app_logs.info("Update Reservation return as false response hence can't update Reservation");
							test_steps
									.add("Update Reservation return false as response hence can't update Reservation ");
						}
					} else if (action.equalsIgnoreCase("updateGuestDetails")) {
						test_steps.add("===================== UPDATE AIRBNB RESERVATION ========================= "
								+ actionTaken);
						app_logs.info("===================== UPDATE AIRBNB RESERVATION ========================= "
								+ actionTaken);
						upFName = updateFirstName + Utility.generateRandomStringWithGivenLength(3);
						airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, endDate, upFName,
								updateLastName, listingid, night, emails, updateAdultCount, updateChildCount, phoneNum,
								baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
					} else {
						airbnb.airbnbReservationActions(actionTaken, externalConfirmation, startDate, endDate,
								guestFirstName, lastName, listingid, night, emails, adultCount, childCount, phoneNum,
								baseprice, payoutamount, vrvoproperties.getProperty("BookingRequestToken"));
					}

					Wait.wait30Second();
					if (!action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
						Utility.refreshPage(driver);
					} else {
						test_steps.add("<b>===== VERIFY RESERVATION DETAILS IN INNCENTER AFTER=====</b>" + actionTaken);
						app_logs.info("=====VERIFY RESERVATION DETAILS IN INNCENTER AFTER=====" + actionTaken);
					}

					if (actionTaken.equalsIgnoreCase("payment")) {
						reservation.clickFolio(driver, test_steps);
						airbnbIn.selectAirbnbAccout(driver, test_steps, accountName);
						test_steps.add(
								"===================== VERIFY PAYMENT LINE ITEM IN AIRBNB RESERVATION =========================");
						app_logs.info(
								"===================== VERIFY PAYMENT LINE ITEM IN AIRBNB RESERVATION =========================");
						String accountType = "Corporate/Member Accounts";
						airbnbIn.verifyAirbnbPaymentLineItem(driver, test_steps, baseprice, "Account - " + accountName);
						createCA.searchForAnAccountAndOpen(driver, test_steps, accountName, accountType);
						createCA.navigateFolios(driver);
						test_steps.add(
								"===================== VERIFY LINE ITEM IN AIRBNB ACCOUNT =========================");
						app_logs.info(
								"===================== VERIFY LINE ITEM IN AIRBNB ACCOUNT =========================");
						airbnbIn.verifyAirbnbAccountFolioLineItem(driver, test_steps, payoutamount, baseprice,
								confirmationNo);
						
						Utility.testCasePass(Utility.statusCode,5,Utility.comments,"Payout reservation ");
						test_steps.add("Payout reservation  "
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802302' target='_blank'>"
							+ "Click here to open TestRail: 802302</a><br>");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
					

					} else if (actionTaken.equalsIgnoreCase("cancel")) {
						test_steps.add("===================== UPDATE AIRBNB RESERVATION ========================= "
								+ actionTaken);
						app_logs.info("===================== UPDATE AIRBNB RESERVATION ========================= "
								+ actionTaken);
						reservation.verifyCancelledStatus(driver, "CANCELLED");
						test_steps.add("Verified reservation status: " + "Cancelled");
						app_logs.info("Verified reservation status: " + "Cancelled");

						reservation.clickFolio(driver, test_steps);
						boolean islineItemPresent = airbnbIn.isFolioLineItemPresentAfterCancelRes(driver);
						assertEquals(islineItemPresent, false, "Failed to verify");
						test_steps.add("Verified line item voided from guest folio");
						app_logs.info("Verified line item voided from guest folio");

						airbnbIn.selectAirbnbAccout(driver, test_steps, accountName);

						boolean isLineItemExist = airbnbIn.isFolioLineItemPresentAfterCancelRes(driver);
						assertEquals(isLineItemExist, false, "Failed to verify");
						test_steps.add("Verified line item voided from folio: " + accountName);
						app_logs.info("Verified line item voided from folio: " + accountName);

						reservation.clickOnHistory(driver);
						airbnbIn.verifyResInHistory(driver, confirmationNo, test_steps);
						
						Utility.testCasePass(Utility.statusCode,2,Utility.comments,"Verify Cancel reservation from Inncenter");
						test_steps.add("Verify Cancel reservation from Inncenter"
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802143' target='_blank'>"
							+ "Click here to open TestRail: 802143</a><br>");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
						
						Utility.testCasePass(Utility.statusCode,4,Utility.comments,"Verify Cancel reservation ");
						test_steps.add("Verify Cancel reservation "
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802301' target='_blank'>"
							+ "Click here to open TestRail: 802301</a><br>");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
						
					} else if (action.equalsIgnoreCase("extendReservation")
							|| action.equalsIgnoreCase("reduceResrevation")) {
						if (updateReservationResponse.asString().contains("true")) {
							test_steps.add("===================== UPDATE AIRBNB RESERVATION ========================= "
									+ actionTaken);
							app_logs.info("===================== UPDATE AIRBNB RESERVATION ========================= "
									+ actionTaken);
							test_steps.add("Update Reservation endpoint returns true response");
							app_logs.info("Update Reservation endpoint returns true response");
							getTest_Steps.clear();
							getTest_Steps = reservation.verifyStayDateAfterReservation(driver,
									Utility.parseDate(startDate1, "dd/MM/yyyy", "MMM d"),
									Utility.parseDate(updatedCheckoutDate1, "dd/MM/yyyy", "MMM d"));
							test_steps.addAll(getTest_Steps);
							getTest_Steps.clear();
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
									Utility.parseDate(updatedCheckoutDate1, "dd/MM/yyyy", "dd MMM, yyyy"),
									"Failed Depart Date Missmatched");
							test_steps.add("Successfully Verified Depart Date : " + foundDepartDate);
							app_logs.info("Successfully Verified Depart Date : " + foundDepartDate);
							nightsCalculated = Utility.differenceBetweenDates(startDate1, updatedCheckoutDate1);
							String foundNoOfNights = reservation.getNoOfNights_ResDetail(driver);
							assertEquals(foundNoOfNights, nightsCalculated + "N", "Failed No Of Nights Missmatched");
							test_steps.add("Successfully Verified No Of Nights : " + foundNoOfNights);
							app_logs.info("Successfully Verified No Of Nights : " + foundNoOfNights);

							reservation.clickFolio(driver, test_steps);

							if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
								test_steps.add(
										"===================== VERIFY TAXES IN AIRBNB RESERVATION =========================");
								app_logs.info(
										"===================== VERIFY TAXES IN AIRBNB RESERVATION =========================");
								airbnbIn.verifyChildLineItemTaxes(driver, test_steps, ledgerAccount, updatedTaxName,
										updatesetTaxRates, updatestayDates);
								if (action.equalsIgnoreCase("reduceResrevation")) {
									test_steps.add("===VERIFY VOID LINE ITEM IN GUEST FOLIO===");
									app_logs.info("VERIFY VOID LINE ITEM IN GUEST FOLIO");
									airbnbIn.clickVoidCheckBox(driver, test_steps);
									for (int i = 0; i < datesRangeList.size(); i++) {
										airbnbIn.verifyAirbnbVoidLineItem(driver, datesRangeList.get(i), test_steps);
									}
								}
							}
							airbnbIn.selectAirbnbAccout(driver, test_steps, accountName);
							Double perDayRoomCharge = Double.parseDouble(baseprice) / Integer.parseInt(updatedNight);
							test_steps.add(
									"===================== VERIFY FOLIO LINE ITEM IN RESERVATION =========================");
							app_logs.info(
									"===================== VERIFY FOLIO LINE ITEM IN RESERVATION =========================");
							for (int i = 0; i < updatestayDates.size(); i++) {
								System.out.println(Utility.capitalizeWord(ratePlanName));
								if(i>1) {
									Double perDayRoomCharge1=perDayRoomCharge-0.01;
									reservation.verifyFolioLineItem(driver, updatestayDates.get(i), ledgerAccount, "Airbnb Rate",
											String.valueOf(perDayRoomCharge1), test_steps);
								}else {
								reservation.verifyFolioLineItem(driver, updatestayDates.get(i), ledgerAccount,
										"Airbnb Rate", String.valueOf(perDayRoomCharge), test_steps);
								}
							}

							if (action.equalsIgnoreCase("reduceResrevation")) {
								test_steps.add("=====VERIFY VOID LINE ITEM IN AIRBNB CORPORATE FOLIO====");
								app_logs.info("VERIFY VOID LINE ITEM IN AIRBNB CORPORATE FOLIO");
								airbnbIn.clickVoidCheckBox(driver, test_steps);
								for (int i = 0; i < datesRangeList.size(); i++) {
									airbnbIn.verifyAirbnbVoidLineItem(driver, datesRangeList.get(i), test_steps);
								}
							}
							
							
					/*		Utility.testCasePass(Utility.statusCode,6,Utility.comments,"Modify the reservation ");
							test_steps.add("Modify the reservation  "
								+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802303' target='_blank'>"
								+ "Click here to open TestRail: 802303</a><br>");
							Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
						
*/
						} else {
							app_logs.info("Update reservation has False response ");
							test_steps.add("Update reservation has False response");
						}

					} else if (action.equalsIgnoreCase("updateGuestDetails")) {
						getTest_Steps.clear();
						upGuestName = upFName + " " + updateLastName;
						getTest_Steps = reservation.verifyGuestNameAfterReservation(driver, upGuestName);
						test_steps.addAll(getTest_Steps);
						getTest_Steps.clear();
						String foundAdultsCount = reservation.getNoOfAdults_ResDetail(driver);
						assertEquals(foundAdultsCount, updateAdultCount, "Failed No Of Adults Missmatched");
						test_steps.add("Successfully Verified No Of Adults : " + foundAdultsCount);
						app_logs.info("Successfully Verified No Of Adults : " + foundAdultsCount);
						String foundNoOfChild = reservation.getNoOfChilds_ResDetail(driver);
						assertEquals(foundNoOfChild, updateChildCount, "Failed No Of Childs Missmatched");
						test_steps.add("Successfully Verified No Of Childs : " + foundNoOfChild);
						app_logs.info("Successfully Verified No Of Childs : " + foundNoOfChild);
					}
					if (action.equalsIgnoreCase("extendReservationWhenPartialAvailability")) {
						test_steps.add("<b>===== Remove Blacked Out=====</b>");
						app_logs.info("=====Remove Blacked Out=====");
						int datesSize = updatestayDates.size();

						ratesGrid.activeOrBlackoutChannel(driver, updatestayDates.get(datesSize - 1), "dd/MM/yyyy",
								roomClassName, "Airbnb", "");
						isBlackedOut = ratesGrid.getBlackoutStatus(driver, dateIndex, "Airbnb", roomClassName);
						if (!isBlackedOut) {
							app_logs.info("Removed  Blacked Out Successfully ");
							test_steps.add("Removed  Blacked Out Successfully ");
						}
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				} catch (Exception e) {
					Utility.catchExceptionOTA(e, test_description, test_catagory, "Update Airbnb Res And Verify In Inncenter", testName,
							test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchErrorOTA(e, test_description, test_catagory, "Update Airbnb Res And Verify In Inncenter", testName,
							test_description, test_catagory, test_steps);
				}

			} else {
				app_logs.info("Make reservation has False response ");
				test_steps.add("Make reservation has False response");

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			}
		} else {

			app_logs.info("There is no availability for the stay reservation looking for As Check availability False ");
			test_steps
					.add("There is no availability for the stay reservation looking for As Check availability False ");

			if (action.equalsIgnoreCase("checkAvailabilityWhenBlackout")) {
				test_steps.add("<b>===== Remove Blacked Out=====</b>");
				app_logs.info("=====Remove Blacked Out=====");
				int datesSize = datesRangeList.size();

				ratesGrid.activeOrBlackoutChannel(driver, datesRangeList.get(datesSize - 1), "dd/MM/yyyy",
						roomClassName, "Airbnb", "");
				isBlackedOut = ratesGrid.getBlackoutStatus(driver, dateIndex, "Airbnb", roomClassName);
				if (!isBlackedOut) {
					app_logs.info("Removed  Blacked Out Successfully ");
					test_steps.add("Removed  Blacked Out Successfully ");
				}
			}
			
			
		
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}
		
		for(int i=0;i<Utility.testId.size();i++) {
			Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify room status for MRB");
		}
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAirbnbReservation", airbnbexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
	}
}
