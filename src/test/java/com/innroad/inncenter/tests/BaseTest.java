package com.innroad.inncenter.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import io.restassured.response.Response;

public class BaseTest extends TestCore{

	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	VrboObjects vrboObject = new VrboObjects();
	ReservationSearch rsvSearch = new ReservationSearch();
	CPReservationPage reservation = new CPReservationPage();
	HashMap<String, String> data = new HashMap<String, String>();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	HashMap<String, Response> responses = new HashMap<String, Response>();
	Navigation navigation = new Navigation();
	RoomStatus roomStatus = new RoomStatus();
	TaxesAndFee taxFee = new TaxesAndFee();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	TaskList tasklist = new TaskList();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Policies pol = new Policies();
	RatesGrid rateG = new RatesGrid();
	Admin admin = new Admin();
	Account createCA = new Account();
	Response response;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "Vrbo")
	public void verifyBusAfterChangePayment(String version, String roomClassName, String channel, String message,
			String title, String firstName, String lastName, String email, String phoneNo, String address1,
			String address2, String address3, String address4, String country, String adult, String child, String pet,
			String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, String cardCode,
			String cardType, String masterListingChannel, String clientIPAddress, String trackingUuid,
			String numberToken, String beginDate, String noOfNights, String originationDate, String chargeName,
			String roomchargeAmount, String IsTaxAdd, String TaxNameInXml, String TaxAmountInXml, String setTaxRate,
			String taxExternalId, String isFeeAdd, String feeNameInXml, String feeAmount, String setFeeRate,
			String feeExternalId, String paymentSheduleItem, String isExtraAdultInXml, String extraAdultAmount,
			String isExtraChildInXml, String extraChildAmount, String isInncenterTaxCreate, String inTaxName,
			String type, String categoryTax, String intaxValues, String ledgerAccount, String isInncenterFeeCreate,
			String infeeName, String feeType, String infeeValues, String categoryFee, String ItemAddedDate, String action) {

		String startDate = null, endDate = null, confirmationNo = null, expiryDate = null, startDate1 = null,
				endDate1 = null, advertiserId = null, classId = null, currency = null, propertyId = null,
				phoneCode = null, phoneNumberVrbo = null, roomCharge = null, getbalance = null, getAddress1 = null,
				getAddress2 = null, getAddress3 = null, getAaddress4 = null, countryName = null,
				resStatus = "CONFIRMED", ratePlanVrbo = "";
		Double roomchargeAmounts = 0.0, tripTotal = 0.0;
		HashMap<String, String> getExternalIdOfTaxes = null;
		HashMap<String, String> getExternalIdOfFees = null;
		HashMap<String, Double> calcInTaxVals = null;
		HashMap<String, Double> calcInFeeVals = null;

		String beforeAction = null;
		String afterAction = null;
		String addedRoomChargeAmount = Utility.generateRandomNumberWithGivenNumberOfDigits(2);

		int days = 0;
		String testName = "BUSScenario_" + action;
		HashMap<String, String> setFeeRates = new HashMap<String, String>();
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValue = Utility.convertTokenToArrayList(intaxValues, Utility.DELIMS);
		ArrayList<String> categoryTaxs = Utility.convertTokenToArrayList(categoryTax, Utility.DELIMS);
		LedgerAccount ledger = new LedgerAccount();
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}

				
			driver = getDriver();
			Vrbo vr = new Vrbo();
			String eventId = "5809";
		//	vr.getConnectionKibana(eventId);
			loginOTA(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

			/*
			 * navigation.setup(driver); navigation.LedgerAccounts(driver); String
			 * ledgeraccountName = "CxGoz"; ledger.ChangeStatus(driver, ledgeraccountName,
			 * "InActive");
			 * 
			 * testSteps.add("<b>===== Getting Property =====</b>");
			 * app_logs.info("===== Getting Property====="); String property =
			 * homePage.getReportsProperty(driver, testSteps);
			 * testSteps.add("<b>Property Name : </b>" + property);
			 * app_logs.info("Property Name : " + property);
			 * 
			 * navigation.admin(driver); navigation.clickonClientinfo(driver);
			 * testSteps.add("<b>===== Getting Currency =====</b>");
			 * app_logs.info("===== Getting Currency====="); admin.clickOnClient(driver,
			 * property); testSteps.add("Open Property : " + property);
			 * app_logs.info("Open Property : " + property);
			 * 
			 * admin.clickClientOptions(driver, testSteps); currency =
			 * admin.getDefaultCurrency(driver); testSteps.add("Default Currency : " +
			 * currency); app_logs.info("Default Currency : " + currency);
			 * 
			 * admin.clickAutoMaskPhone(driver, false, testSteps);
			 * 
			 * navigation.navigateToSetupfromRoomMaintenance(driver);
			 * testSteps.add("Click Setup"); app_logs.info("Click Setup");
			 * navigation.clickVrboSetup(driver); testSteps.add("Click Vrbo Setup");
			 * app_logs.info("Click Vrbo Setup");
			 * 
			 * testSteps.add("<b>===== Getting Advertisement ID =====</b>");
			 * app_logs.info("===== Getting Advertisement ID=====");
			 * 
			 * advertiserId = vrbo.getVrboAdvertisementID(driver);
			 * testSteps.add("Advertisement ID : " + advertiserId);
			 * app_logs.info("Advertisement ID " + advertiserId);
			 * 
			 * testSteps.add("<b>===== Getting Room Class ID =====</b>");
			 * app_logs.info("===== Getting Room Class ID=====");
			 * 
			 * navigation.roomClass(driver); HashMap<String, String> roomClassIds = new
			 * HashMap<String, String>(); roomClassIds =
			 * roomClass.getRoomClassDetails(driver, roomClassName, testSteps); classId =
			 * roomClassIds.get("roomClassId"); testSteps.add("Room Class ID : " + classId);
			 * app_logs.info("Room Class ID " + classId); ratePlanVrbo =
			 * roomClassIds.get("ratePlan"); testSteps.add("Room ratePlanVrbo : " +
			 * ratePlanVrbo); app_logs.info("Room ratePlanVrbo " + ratePlanVrbo);
			 * 
			 * testSteps.add("<b>===== Getting PropertyId ID =====</b>");
			 * app_logs.info("===== Getting PropertyId ID=====");
			 * navigation.properties(driver); propertyId = vrbo.getPropertyId(driver,
			 * property);
			 * 
			 * navigation.clickTaxesAndFees(driver); //
			 * navigation.clickTaxesAndFeesAfterRoomClass(driver);
			 * testSteps.add("Click Tax and Fee"); app_logs.info("Click Tax and Fee");
			 * boolean isExist = taxFee.isTaxOrFeeItemExist(driver); if (isExist) {
			 * taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive",
			 * testSteps); } if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
			 * testSteps.add("====================Create new Tax ======================");
			 * ArrayList<String> intaxname = Utility.convertTokenToArrayList(inTaxName,
			 * Utility.DELIMS);
			 * 
			 * for (int i = 0; i < intaxname.size(); i++) { String upTaxName =
			 * intaxname.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
			 * updatedTaxName.add(upTaxName); }
			 * 
			 * ArrayList<String> taxType = Utility.convertTokenToArrayList(type,
			 * Utility.DELIMS);
			 * 
			 * for (int i = 0; i < intaxname.size(); i++) { if
			 * (!action.equalsIgnoreCase("TaxExempt")) { taxFee.createTaxes(driver,
			 * updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
			 * taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "", ledgerAccount,
			 * false, "", "", "", testSteps); } else { taxFee.createTaxes(driver,
			 * updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
			 * taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "Yes", ledgerAccount,
			 * false, "", "", "", testSteps); } if
			 * (categoryTaxs.get(i).equalsIgnoreCase("flat")) {
			 * setTaxRates.put(updatedTaxName.get(i), "No"); } else {
			 * setTaxRates.put(updatedTaxName.get(i), intaxValue.get(i)); }
			 * 
			 * testSteps.add("===========================================================");
			 * } }
			 * 
			 * if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
			 * testSteps.add("====================Create new Fee ======================");
			 * ArrayList<String> infeeNames = Utility.convertTokenToArrayList(infeeName,
			 * Utility.DELIMS);
			 * 
			 * for (int i = 0; i < infeeNames.size(); i++) { String upfeeName =
			 * infeeNames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
			 * updatedFeeName.add(upfeeName); }
			 * 
			 * ArrayList<String> infeeValue = Utility.convertTokenToArrayList(infeeValues,
			 * Utility.DELIMS); ArrayList<String> categoryFees =
			 * Utility.convertTokenToArrayList(categoryFee, Utility.DELIMS);
			 * ArrayList<String> feeTypes = Utility.convertTokenToArrayList(feeType,
			 * Utility.DELIMS);
			 * 
			 * for (int i = 0; i < infeeNames.size(); i++) { taxFee.createFee(driver,
			 * testSteps, updatedFeeName.get(i), updatedFeeName.get(i), feeTypes.get(i),
			 * updatedFeeName.get(i), categoryFees.get(i), infeeValue.get(i), false, "", "",
			 * ""); if (categoryFees.get(i).equalsIgnoreCase("flat")) {
			 * setFeeRates.put(updatedFeeName.get(i), "No"); } else {
			 * setFeeRates.put(updatedFeeName.get(i), infeeValue.get(i)); }
			 * 
			 * testSteps.add("===========================================================");
			 * }
			 * 
			 * }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchErrorOTA(e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee", testName,
					test_description, test_catagory, testSteps);
		}
		try {

			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
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
			if (!(Utility.validateFirstGreater(ItemAddedDate, endDate1))) {
				ItemAddedDate = Utility.addDays(endDate1, 1);
			} else {
				if (ItemAddedDate.equals(endDate1)) {
					ItemAddedDate = Utility.addDays(endDate1, 1);
				} else {

				}
			}
			startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");

			if (!(Utility.validateInput(originationDate))) {
				originationDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss");
			} else {
				oDates = Utility.splitInputData(originationDate);
				originationDate = Utility.parseDate(oDates.get(0), "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss");
			}

			originationDate = originationDate + "+00:00";
			app_logs.info(startDate);
			app_logs.info(endDate);
			app_logs.info(originationDate);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			app_logs.info(datesRangeList);

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

			testSteps.add("====================Verify API End Point======================");
			File requestBody = bookingRequest.createBookingRequest(testName, version, advertiserId, classId, channel,
					message, title, firstName, lastName, email, phoneNo, address1, address2, address3, address4,
					country, adult, child, startDate, endDate, originationDate, currency, chargeName, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					IsTaxAdd, TaxNameInXml, TaxAmountInXml, setTaxRate, isExtraAdultInXml, extraAdultAmount,
					isExtraChildInXml, extraChildAmount, isFeeAdd, feeNameInXml, feeAmount, setFeeRate, taxExternalId,
					feeExternalId, roomchargeAmount, paymentSheduleItem, masterListingChannel, clientIPAddress,
					trackingUuid, numberToken);
			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--" + myRequest);

			responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestToken"),
					VrboEndPoints.bookingRequest, myRequest);
			response = responses.get("BODY");
			int statusCode = response.getStatusCode();
			data.put("StatusCode", String.valueOf(statusCode));
			data.put("APIURL", VrboEndPoints.bookingRequest);
			data.put("BODY", response.asString());
			if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE") && !data.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("API End Point-" + data.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(data.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath, testName,
						"BusXml");

				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				confirmationNo = bookingRequest.getValueOfXmlTags(data.get("BODY"), "externalId");
				testSteps.add("Confirmation No -" + confirmationNo);
				app_logs.info("Confirmation No -" + confirmationNo);
			} else if (data.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + data.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(data.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath, testName,
						"BusXml");

				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

			} else {
				app_logs.info("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
				testSteps.add("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
			}

		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE") && !data.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("====================VERIFY BUS AFTER RESERVATION CREATION======================");

				datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
				days = datesRangeList.size();
				navigation.reservationBackward3(driver);
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
				roomCharge = reservation.getRoomCharge_TripSummary(driver);
				roomchargeAmounts = Double.parseDouble(roomCharge);
				getbalance = reservation.getTripSummaryBalance(driver, testSteps);
				app_logs.info(getbalance);
				String getPhone = reservation.getPhoneNumber(driver);
				phoneCode = getPhone.split("-")[0];
				phoneNumberVrbo = "+" + phoneCode + " " + getPhone.split("-")[1].trim();
				getAddress1 = reservation.getAddressLine1_ResDetail(driver).trim();
				getAddress2 = reservation.getAddressLine2_ResDetail(driver).trim();
				getAddress3 = reservation.getAddressLine3_ResDetail(driver).trim();
				getAaddress4 = reservation.getState_ResDetail(driver);
				countryName = reservation.getContry_ResDetail(driver).trim();
				String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
				tripTotal = Double.parseDouble(tripTotals);
				reservation.click_Folio(driver, testSteps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
						vrvoRateToken);
				getExternalIdOfTaxes = vrboObject.getExternalIdOfTax(responseBody, updatedTaxName);
				getExternalIdOfFees = vrboObject.getExternalIdOfFee(responseBody, updatedFeeName);
				calcInTaxVals = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName, updatedTaxName);
				calcInFeeVals = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
				HashMap<String, Response> responses = vrboObject.getBookingUpdateRequest(vrvoBookingUpdateToken,
						advertiserId, confirmationNo);
				response = responses.get("BODY");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalance);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(response, advertiserId, classId, classId, confirmationNo, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo,
						getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,

						startDate, endDate, startDate, resStatus, tripTotal, false, dueDates, testSteps);

			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE") && !data.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("====================UPDATE RESERVATION======================");

				

				if (action.equalsIgnoreCase("CreateNewPayment")) {
					testSteps.add("<b>===== CREATE NEW PAYMENT LINE ITEM =====</b>");
					app_logs.info("=====CREATE NEW PAYMENT LINE ITEM =====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.click_Pay(driver, testSteps);
					String addPaymentAmount = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
					reservation.enterAmountTakePayment(driver, testSteps, "Yes", addPaymentAmount);
					reservation.clickPaybutton(driver, testSteps, addPaymentAmount);
					reservation.closePaymentSuccessfullModal(driver);
					reservation.clickSaveButtonUnderFolioTab(driver);
					Wait.wait10Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickOnDetails(driver);
					roomCharge = reservation.getRoomCharge_TripSummary(driver);
					roomchargeAmounts = Double.parseDouble(roomCharge);
					String getbalances = reservation.getTripSummaryBalance(driver, testSteps);

					String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
					tripTotal = Double.parseDouble(tripTotals);
					String getPhone = reservation.getPhoneNumber(driver);

					phoneCode = getPhone.split("-")[0];
					phoneNumberVrbo = "+" + phoneCode + " " + getPhone.split("-")[1].trim();

					File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
							beforeAction, afterAction);
					String myRequestIndex = bookingRequest.getFileContent(requestIndex);
					HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
							vrvoproperties.getProperty("BookingRequestToken"),
							VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
					Response busIndexresponse = busIndexresponses.get("BODY");

					vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(),
							testSteps);
					testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
					HashMap<String, Response> busContentresponses = vrboObject
							.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

					File responseBodyForBus = bookingRequest.getResponseXmlFile(
							busContentresponses.get("BODY").asString(), schemaFilesPath, testName, "BusUpdate");
					testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
							+ "Click here to open : URL</a><br>");

					Response busContent = busContentresponses.get("BODY");
					testSteps.add("<b>===== VERIFY IN BUS =====</b>");
					app_logs.info("===== VERIFY IN BUS=====");
					String dueDates = "";
					String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
					if (payStatus.equalsIgnoreCase("PAID")) {
						dueDates = startDate;
					} else {
						dueDates = endDate;
					}
					vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo,
							"en_US", calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes,
							getExternalIdOfFees, setTaxRates, setFeeRates, payStatus, title, nameONCard, email,
							phoneCode, phoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName,
							getAaddress4, postalCode, adult, child, startDate, endDate, startDate, resStatus, tripTotal,
							false, dueDates, testSteps);
				}else if(action.equalsIgnoreCase("AddRoomChargeOutSideStay")) {
					testSteps.add("<b>===== CREATE NEW PAYMENT LINE ITEM =====</b>");
					app_logs.info("=====CREATE NEW PAYMENT LINE ITEM =====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					
					reservation.AddLineItemWithDate(driver, testSteps, "Room Charge", addedRoomChargeAmount, Utility.parseDate(ItemAddedDate, "dd/MM/yyyy", "MM/dd/yyyy"));
					Wait.wait10Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickOnDetails(driver);
					roomCharge = reservation.getRoomCharge_TripSummary(driver);
					roomchargeAmounts = Double.parseDouble(roomCharge);
					String getbalances = reservation.getTripSummaryBalance(driver, testSteps);

					String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
					tripTotal = Double.parseDouble(tripTotals);
					String getPhone = reservation.getPhoneNumber(driver);

					phoneCode = getPhone.split("-")[0];
					phoneNumberVrbo = "+" + phoneCode + " " + getPhone.split("-")[1].trim();

					File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
							beforeAction, afterAction);
					String myRequestIndex = bookingRequest.getFileContent(requestIndex);
					HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
							vrvoproperties.getProperty("BookingRequestToken"),
							VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
					Response busIndexresponse = busIndexresponses.get("BODY");

					vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(),
							testSteps);
					testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
					HashMap<String, Response> busContentresponses = vrboObject
							.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

					File responseBodyForBus = bookingRequest.getResponseXmlFile(
							busContentresponses.get("BODY").asString(), schemaFilesPath, testName, "BusUpdate");
					testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
							+ "Click here to open : URL</a><br>");

					Response busContent = busContentresponses.get("BODY");
					testSteps.add("<b>===== VERIFY IN BUS =====</b>");
					app_logs.info("===== VERIFY IN BUS=====");
					String dueDates = "";
					String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
					if (payStatus.equalsIgnoreCase("PAID")) {
						dueDates = startDate;
					} else {
						dueDates = endDate;
					}
					vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo,
							"en_US", calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes,
							getExternalIdOfFees, setTaxRates, setFeeRates, payStatus, title, nameONCard, email,
							phoneCode, phoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName,
							getAaddress4, postalCode, adult, child, startDate, endDate, startDate, resStatus, tripTotal,
							false, dueDates, testSteps);
				}
				navigation.navigateToSetupFromReservationPage(driver, testSteps);
				navigation.clickTaxesAndFees(driver);
				testSteps.add("Click Tax and Fee");
				app_logs.info("Click Tax and Fee");

				if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
					testSteps.add("====================Delete Tax======================");
					for (int i = 0; i < updatedTaxName.size(); i++) {
						taxFee.deleteTaxAndFee(driver, updatedTaxName.get(i), testSteps);
					}
				}
				if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
					testSteps.add("====================Delete Fee======================");
					for (int i = 0; i < updatedFeeName.size(); i++) {
						taxFee.deleteTaxAndFee(driver, updatedFeeName.get(i), testSteps);
					}
				}

			} else {
				testSteps.add("Unable to create resrevation due to room class availability");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {

			e.printStackTrace();
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Update Request", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Update Request", testName,
					test_description, test_catagory, testSteps);
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequestCommonData", otaexcel);
	}

	@DataProvider
	public Object[][] getDataOne() {

		return Utility.getData("testSheet", otaexcel);

	}

	@DataProvider
	public Object[][] getFinalData() {
		return Utility.combine(getData(), getDataOne());
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}

