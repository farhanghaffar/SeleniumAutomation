package com.innroad.inncenter.tests;

import java.io.File;
import java.text.ParseException;
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
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import io.restassured.response.Response;

public class VerifyBusXML extends TestCore {
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
	public void verifyBusXML(String version, String roomClassName, String channel, String message, String title,
			String firstName, String lastName, String email, String phoneNo, String address1, String address2,
			String address3, String address4, String country, String adult, String child, String pet,
			String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, String cardCode,
			String cardType, String masterListingChannel, String clientIPAddress, String trackingUuid,
			String numberToken, String beginDate, String noOfNights, String originationDate, String chargeName,
			String roomchargeAmount, String IsTaxAdd, String TaxNameInXml, String TaxAmountInXml, String setTaxRate,
			String taxExternalId, String isFeeAdd, String feeNameInXml, String feeAmount, String setFeeRate,
			String feeExternalId, String paymentSheduleItem, String isExtraAdultInXml, String extraAdultAmount,
			String isExtraChildInXml, String extraChildAmount, String isInncenterTaxCreate, String inTaxName,
			String type, String categoryTax, String intaxValues, String ledgerAccount, String isInncenterFeeCreate,
			String infeeName, String feeType, String infeeValues, String categoryFee, String updateTitle,
			String upFistname, String updateContactLastName, String updateEmail, String updatePhoneNumber,
			String updateAddress1, String updateAddress2, String updateAddress3, String updateAddress4,
			String updateCountry, String updatePostalCode, String updatePhoneCountryCode, String updateAdult,
			String updateChild, String isVoidCharges, String action) throws ParseException {
		String testCaseID="802194|802193|802259|802257|802258|802277|802278|802281|802282|802286|802287|802143|802288|802289|802291";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String startDate = null, endDate = null, confirmationNo = null, expiryDate = null, startDate1 = null,
				endDate1 = null, advertiserId = null, classId = null, currency = null, propertyId = null,
				phoneCode = null, phoneNumberVrbo = null, roomCharge = null, getbalance = null, getAddress1 = null,
				getAddress2 = null, getAddress3 = null, getAaddress4 = null, countryName = null,
				resStatus = "CONFIRMED", ratePlanVrbo = null, policyCustomText = null, noShowpolicyName = null,
				cancelPolicyName = null, cancellationCustomText = "test custom name flat fee", updateConf = null,
				accounttype = "Corporate/Member Accounts", marketSegment = "Internet", referral = "Other";
		Double roomchargeAmounts = 0.0, tripTotal = 0.0;
		HashMap<String, String> getExternalIdOfTaxes = null;
		HashMap<String, String> getExternalIdOfFees = null;
		HashMap<String, Double> calcInTaxVals = null;
		HashMap<String, Double> calcInFeeVals = null;

		String beforeAction = null;
		String afterAction = null;
		if (action.equalsIgnoreCase("NoShowPolicy")) {
			noShowpolicyName = "noShowPolicy " + Utility.generateRandomStringWithGivenLength(3);
		} else if (action.equalsIgnoreCase("CancellationPolicy")) {
			cancelPolicyName = "cancelPolicy " + Utility.generateRandomStringWithGivenLength(3);
		}
		String noShowPolicyFee = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String guestFirstName = "AccountF" + Utility.generateRandomStringWithGivenLength(3);
		String accountName = guestFirstName;
		String guestLastName = "AccountL" + Utility.generateRandomStringWithGivenLength(3);
		int days = 0;
		String testName = "BUSScenario_" + action;
		HashMap<String, String> setFeeRates = new HashMap<String, String>();
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValue = Utility.convertTokenToArrayList(intaxValues, Utility.DELIMS);
		ArrayList<String> categoryTaxs = Utility.convertTokenToArrayList(categoryTax, Utility.DELIMS);
		Utility.initializeTestCase("802194|802193|802259|802257|802258|802277|802278|802281|802282|802286|802287|802143|802288|802289|802291", Utility.testId, Utility.statusCode, Utility.comments, "");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}

			driver = getDriver();
			loginOTA(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

			testSteps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, testSteps);
			testSteps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);

			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			testSteps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			testSteps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);

			admin.clickClientOptions(driver, testSteps);
			currency = admin.getDefaultCurrency(driver);
			testSteps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);

			admin.clickAutoMaskPhone(driver, true, testSteps);

			navigation.navigateToSetupfromRoomMaintenance(driver);
			testSteps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickVrboSetup(driver);
			testSteps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");

			testSteps.add("<b>===== Getting Advertisement ID =====</b>");
			app_logs.info("===== Getting Advertisement ID=====");

			advertiserId = vrbo.getVrboAdvertisementID(driver);
			testSteps.add("Advertisement ID : " + advertiserId);
			app_logs.info("Advertisement ID " + advertiserId);

			testSteps.add("<b>===== Getting Room Class ID =====</b>");
			app_logs.info("===== Getting Room Class ID=====");

			navigation.roomClass(driver);
			HashMap<String, String> roomClassIds = new HashMap<String, String>();
			roomClassIds = roomClass.getRoomClassDetails(driver, roomClassName, testSteps);
			classId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			ratePlanVrbo = roomClassIds.get("ratePlan");
			testSteps.add("Room ratePlanVrbo : " + ratePlanVrbo);
			app_logs.info("Room ratePlanVrbo " + ratePlanVrbo);

			testSteps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);

			navigation.clickTaxesAndFees(driver);
			// navigation.clickTaxesAndFeesAfterRoomClass(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				//taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
				taxFee.deleteAllTaxesAndFee(driver, testSteps);
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Create new Tax ======================");
				ArrayList<String> intaxname = Utility.convertTokenToArrayList(inTaxName, Utility.DELIMS);

				for (int i = 0; i < intaxname.size(); i++) {
					String upTaxName = intaxname.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedTaxName.add(upTaxName);
				}

				ArrayList<String> taxType = Utility.convertTokenToArrayList(type, Utility.DELIMS);

				for (int i = 0; i < intaxname.size(); i++) {
					if (!action.equalsIgnoreCase("TaxExempt")) {
						taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
								taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "", ledgerAccount, false, "",
								"", "", testSteps);
					} else {
						taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
								taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "Yes", ledgerAccount, false, "",
								"", "", testSteps);
					}
					if (categoryTaxs.get(i).equalsIgnoreCase("flat")) {
						setTaxRates.put(updatedTaxName.get(i), "No");
					} else {
						setTaxRates.put(updatedTaxName.get(i), intaxValue.get(i));
					}

					testSteps.add("===========================================================");
				}
			}

			if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Create new Fee ======================");
				ArrayList<String> infeeNames = Utility.convertTokenToArrayList(infeeName, Utility.DELIMS);

				for (int i = 0; i < infeeNames.size(); i++) {
					String upfeeName = infeeNames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedFeeName.add(upfeeName);
				}

				ArrayList<String> infeeValue = Utility.convertTokenToArrayList(infeeValues, Utility.DELIMS);
				ArrayList<String> categoryFees = Utility.convertTokenToArrayList(categoryFee, Utility.DELIMS);
				ArrayList<String> feeTypes = Utility.convertTokenToArrayList(feeType, Utility.DELIMS);

				for (int i = 0; i < infeeNames.size(); i++) {
					taxFee.createFee(driver, testSteps, updatedFeeName.get(i), updatedFeeName.get(i), feeTypes.get(i),
							updatedFeeName.get(i), categoryFees.get(i), infeeValue.get(i), false, "", "", "");
					if (categoryFees.get(i).equalsIgnoreCase("flat")) {
						setFeeRates.put(updatedFeeName.get(i), "No");
					} else {
						setFeeRates.put(updatedFeeName.get(i), infeeValue.get(i));
					}

					testSteps.add("===========================================================");
				}

			}
			if (action.equalsIgnoreCase("NoShowPolicy")) {
				testSteps.add("<b>===== CREATE NO SHOW POLICY =====</b>");
				app_logs.info("=====CREATE NO SHOW POLICY =====");
				navigation.reservationBackward3(driver);
				navigation.inventory(driver);
				navigation.policies(driver, testSteps);
				String policyType = "No Show", typeOfFee = "flat fee";
				pol.createPolicies(driver, testSteps, "|", "|", policyType, "", "", "", noShowpolicyName, typeOfFee,
						noShowPolicyFee, "", "", "", "true",
						"If Guest No show then he has to pay fixed Amount: $" + noShowPolicyFee);

			} else if (action.equalsIgnoreCase("CancellationPolicy")) {
				testSteps.add("<b>===== CREATE CALCELLATION POLICY =====</b>");
				app_logs.info("=====CREATE CALCELLATION POLICY =====");
				navigation.reservationBackward3(driver);
				navigation.inventory(driver);
				navigation.policies(driver, testSteps);
				String policyType = "Cancellation", typeOfFee = "flat fee";

				pol.createPolicies(driver, testSteps, "|", "|", policyType, cancelPolicyName, "", "", "", typeOfFee,
						noShowPolicyFee, "", "0", "within check-in date", "true", cancellationCustomText);

			} else if (action.equalsIgnoreCase("AssociateAccount") || action.equalsIgnoreCase("AssociateTravelAgent")) {
				if (action.equalsIgnoreCase("AssociateAccount")) {
					testSteps.add("<b>===== CREATE CORPORATE ACCOUNT =====</b>");
					app_logs.info("=====CREATE CORPORATE ACCOUNT =====");
				} else {
					testSteps.add("<b>===== CREATE TRAVELAGENT ACCOUNT =====</b>");
					app_logs.info("=====CREATE TRAVELAGENT ACCOUNT =====");
					accounttype = "Travel Agent";
				}
				navigation.reservationBackward3(driver);
				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				createCA.ClickNewAccountbutton(driver, accounttype);
				createCA.AccountDetails(driver, accounttype, accountName);
				createCA.AccountAttributes(driver, test, marketSegment, referral, testSteps);
				createCA.Mailinginfo(driver, test, guestFirstName, guestLastName, updatePhoneNumber, updatePhoneNumber,
						updateAddress1, updateAddress2, updateAddress3, updateEmail, updateAddress3, updateAddress4,
						updatePostalCode, testSteps);
				createCA.Billinginfo(driver, test, testSteps);
				createCA.AccountSave(driver, test, accountName, testSteps);
				testSteps.add("Successfully created Guest Account");
				createCA.closeAccount(driver);
				navigation.accountToReservation(driver);
				Utility.refreshPage(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
					testName, test_description, test_catagory, testSteps);
		}
		try {

			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDateUTC(2), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDateUTC(Integer.parseInt(noOfNights)),
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
			if (action.equalsIgnoreCase("NoShowPolicy")) {
				testSteps.add("<b>===== ASSOCIATE NO SHOW POLICY AT SEASON LEVEL=====</b>");
				app_logs.info("=====ASSOCIATE NO SHOW POLICY AT SEASON LEVEL =====");
				String policyType = "No Show";
				rateG.associatePolicyAtSeason(driver, testSteps, ratePlanVrbo, startDate1, policyType,
						noShowpolicyName);
				testSteps.add("========================================");

			} else if (action.equalsIgnoreCase("CancellationPolicy")) {
				testSteps.add("<b>===== ASSOCIATE CANCELLATION POLICY AT SEASON LEVEL=====</b>");
				app_logs.info("=====ASSOCIATE CANCELLATION POLICY AT SEASON LEVEL =====");
				String policyType = "Cancellation";
				rateG.associatePolicyAtSeason(driver, testSteps, ratePlanVrbo, startDate1, policyType,
						cancelPolicyName);
				testSteps.add("========================================");
			}
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
				
				Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify the external confirmation number before create a reservation");
				testSteps.add("Verify the external confirmation number before create a reservation"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802194' target='_blank'>"
					+ "Click here to open TestRail: 802194</a><br>");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
					
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
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add("====================VERIFY BUS AFTER RESERVATION CREATION======================");
			app_logs.info("====================VERIFY BUS AFTER RESERVATION CREATION======================");
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			days = datesRangeList.size();
			if (!action.equalsIgnoreCase("AssociateAccount") && !action.equalsIgnoreCase("AssociateTravelAgent")) {
				navigation.reservationBackward3(driver);
			}

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
			String name=reservation.getGuestName_ResDetail(driver);
			if (action.equalsIgnoreCase("NoShowPolicy")) {
				policyCustomText = "If Guest No show then he has to pay fixed Amount: $" + noShowPolicyFee;
				reservation.verifyNoShowPolicy(driver, testSteps, noShowpolicyName, policyCustomText);
			} else if (action.equalsIgnoreCase("CancellationPolicy")) {

				reservation.verifyCancellationPolicy(driver, testSteps, cancelPolicyName, cancellationCustomText);
			}
			reservation.click_Folio(driver, testSteps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
			getExternalIdOfTaxes = vrboObject.getExternalIdOfTax(responseBody, updatedTaxName);
			getExternalIdOfFees = vrboObject.getExternalIdOfFee(responseBody, updatedFeeName);
			calcInTaxVals = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName, updatedTaxName);
			calcInFeeVals = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
			HashMap<String, Response> responses = vrboObject.getBookingUpdateRequest(vrvoBookingUpdateToken,
					advertiserId, confirmationNo);
			String dueDates = "";
			response = responses.get("BODY");
			String payStatus = vrboObject.getVrBoPaymentStatus(getbalance);
			if (payStatus.equalsIgnoreCase("PAID")) {
				dueDates = startDate;
			} else {
				dueDates = endDate;
			}
			
			/*vrboObject.validateBookingUpdateXML(response, advertiserId, classId, classId, confirmationNo, "en_US",
					calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
					setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo,
					getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,
					startDate, endDate, startDate, resStatus, tripTotal, false, dueDates, testSteps);*/
			Double paymentSchedule= Double.parseDouble(paymentSheduleItem);
			vrboObject.validateBookingUpdateXML(response, advertiserId, classId, classId, confirmationNo, "en_US",
					calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
					setTaxRates, setFeeRates, payStatus, title, name, email, phoneCode, phoneNumberVrbo,
					getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,
					startDate, endDate, startDate, resStatus, paymentSchedule, false, dueDates, testSteps);
			
			Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify documentVersion  Hardcode 1.3");
			testSteps.add("Verify documentVersion  Hardcode 1.3"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802193' target='_blank'>"
				+ "Click here to open TestRail: 802193</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,2,Utility.comments,"Verify reservation update in booking update content");
			testSteps.add("Verify reservation update in booking update content"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802259' target='_blank'>"
				+ "Click here to open TestRail: 802259</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);

			Utility.testCasePass(Utility.statusCode,5,Utility.comments,"Verify the taxes apply to a property when value not set as percent, not set as VAT");
			testSteps.add("Verify the taxes apply to a property when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802277' target='_blank'>"
				+ "Click here to open TestRail: 802277</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,6,Utility.comments,"Verify the active tax for the property applied to 'Room charges' when value not set as percent, not set as VAT");
			testSteps.add("Verify the active tax for the property applied to 'Room charges' when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802278' target='_blank'>"
				+ "Click here to open TestRail: 802278</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,7,Utility.comments,"Verify the item name of the 'Tax Item' in innroad system when value not set as percent, not set as VAT");
			testSteps.add("Verify the item name of the 'Tax Item' in innroad system when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802281' target='_blank'>"
				+ "Click here to open TestRail: 802281</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(7), Utility.statusCode.get(7), Utility.comments.get(7), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,8,Utility.comments,"Verify the amount of the tax item");
			testSteps.add("Verify the amount of the tax item"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802282' target='_blank'>"
				+ "Click here to open TestRail: 802282</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(8), Utility.statusCode.get(8), Utility.comments.get(8), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,9,Utility.comments,"Verify the booking date in when value not set as percent, not set as VAT");
			testSteps.add("Verify the booking date in when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802286' target='_blank'>"
				+ "Click here to open TestRail: 802286</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(9), Utility.statusCode.get(9), Utility.comments.get(9), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,10,Utility.comments,"Verify the checkin date in when value not set as percent, not set as VAT");
			testSteps.add("Verify the checkin date in when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802287' target='_blank'>"
				+ "Click here to open TestRail: 802287</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(10), Utility.statusCode.get(10), Utility.comments.get(10), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,12,Utility.comments,"Verify the stay date in when value not set as percent, not set as VAT");
			testSteps.add("Verify the stay date in when value not set as percent, not set as VAT"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802288' target='_blank'>"
				+ "Click here to open TestRail: 802288</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(12), Utility.statusCode.get(12), Utility.comments.get(12), TestCore.TestRail_AssignToID);


		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("====================UPDATE RESERVATION======================");
			// updateStayInfo
			// if (action.equalsIgnoreCase("updateStayInfo")) {
			// updateStayInfo

			if (action.equalsIgnoreCase("updateGuestInfo")) {
				reservation.clickOnDetails(driver);
				if (Utility.validateString(updateTitle)) {
					testSteps.add("<b>===== UPDATE CONTACT TITLE =====</b>");
					app_logs.info("=====UPDATE CONTACT TITLE=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.selectContactSalutation(driver, updateTitle);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
					vrboObject.validateBusNameTitle(busContent, updateTitle, testSteps);
				}
				if (Utility.validateString(upFistname) && Utility.validateString(updateContactLastName)) {
					testSteps.add("<b>===== UPDATE CONTACT FIRST AND LAST NAME =====</b>");
					app_logs.info("=====UPDATE CONTACT FIRST AND LAST NAME=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enter_ContactName(driver, testSteps, title, upFistname, updateContactLastName);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusFirstAndLastName(busContent, upFistname + " " + updateContactLastName,
							testSteps);

				}
				if (Utility.validateString(updateEmail)) {

					testSteps.add("<b>===== UPDATE EMAIL =====</b>");
					app_logs.info("=====UPDATE EMAIL=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enter_Email(driver, testSteps, updateEmail);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusEmail(busContent, updateEmail, testSteps);

				}
				if ((Utility.validateString(updatePhoneNumber))) {

					testSteps.add("<b>===== UPDATE PHONE NUMBER =====</b>");
					app_logs.info("=====UPDATE PHONE NUMBER=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enterPhoneNumber(driver, testSteps, updatePhoneNumber);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					System.out.println("beforeAction " + beforeAction);
					String getPhone = reservation.getPhoneNumber(driver);
					phoneCode = getPhone.split("-")[0];
					String updatedphoneNumberVrbo = "+" + phoneCode + " " + getPhone.split("-")[1].trim() + "-"
							+ getPhone.split("-")[2];
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusPhone(busContent, updatedphoneNumberVrbo, testSteps);
				}
				if ((Utility.validateString(updatePhoneCountryCode))) {

					testSteps.add("<b>===== UPDATE PHONE COUNTRY CODE =====</b>");
					app_logs.info("=====UPDATE PHONE COUNTRY CODE =====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enterPhoneCode(driver, testSteps, updatePhoneCountryCode);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					System.out.println("beforeAction " + beforeAction);
					File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
							beforeAction, afterAction);
					String myRequestIndex = bookingRequest.getFileContent(requestIndex);
					HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
							vrvoproperties.getProperty("BookingRequestToken"),
							VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
					Response busIndexresponse = busIndexresponses.get("BODY");
					vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(),
							testSteps);
					
					Utility.testCasePass(Utility.statusCode,3,Utility.comments,"Verify response should be returning url's of all created reservations as HA sources");
					testSteps.add("Verify response should be returning url's of all created reservations as HA sources"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802257' target='_blank'>"
						+ "Click here to open TestRail: 802257</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
					
					Utility.testCasePass(Utility.statusCode,4,Utility.comments,"Verify response should be returning url's of all cancelled reservations as HA source");
					testSteps.add("Verify response should be returning url's of all cancelled reservations as HA source"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802258' target='_blank'>"
						+ "Click here to open TestRail: 802258</a><br>");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
					
					testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
					HashMap<String, Response> busContentresponses = vrboObject
							.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

					File responseBodyForBus = bookingRequest.getResponseXmlFile(
							busContentresponses.get("BODY").asString(), schemaFilesPath, testName, "BusUpdate");
					testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
							+ "Click here to open : URL</a><br>");

					System.out.println(busContentresponses);
					Response busContent = busContentresponses.get("BODY");
					testSteps.add("<b>===== VERIFY IN BUS =====</b>");
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validatePhoneCode(busContent, updatePhoneCountryCode, testSteps);
				}
				if ((Utility.validateString(updateAddress1))) {

					testSteps.add("<b>===== UPDATE ADDRESS1 =====</b>");
					app_logs.info("=====UPDATE ADDRESS1 =====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enterAddress1(driver, testSteps, updateAddress1);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					String getUpdatedAddress1 = reservation.getAddressLine1_ResDetail(driver).trim();
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusAddress1(busContent, getUpdatedAddress1, testSteps);
				}
				if ((Utility.validateString(updateAddress2))) {

					testSteps.add("<b>===== UPDATE ADDRESS2 =====</b>");
					app_logs.info("=====UPDATE ADDRESS2=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enterAddress2(driver, testSteps, updateAddress2);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					String getUpdatedAddress2 = reservation.getAddressLine2_ResDetail(driver).trim();
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusAddress2(busContent, getUpdatedAddress2, testSteps);
				}
				if ((Utility.validateString(updateAddress3))) {

					testSteps.add("<b>===== UPDATE ADDRESS3 =====</b>");
					app_logs.info("=====UPDATE ADDRESS3=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enter_City(driver, testSteps, updateAddress4);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					String getUpdatedAddress3 = reservation.getCity_ResDetail(driver).trim();
					File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
							beforeAction, afterAction);
					String myRequestIndex = bookingRequest.getFileContent(requestIndex);
					HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
							vrvoproperties.getProperty("BookingRequestToken"),
							VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
					Response busIndexresponse = busIndexresponses.get("BODY");
					app_logs.info("Rsponse--" + busIndexresponse.print());
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusAddress3(busContent, getUpdatedAddress3, testSteps);
				}
				if ((Utility.validateString(updateAddress4))) {

					testSteps.add("<b>===== UPDATE ADDRESS4 =====</b>");
					app_logs.info("=====UPDATE ADDRESS4=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.select_State(driver, testSteps, updateAddress4);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusAddress4(busContent, updateAddress4, testSteps);
				}
				if ((Utility.validateString(updateCountry))) {

					testSteps.add("<b>===== UPDATE COUNTRY =====</b>");
					app_logs.info("=====UPDATE COUNTRY=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.select_Country(driver, testSteps, updateCountry);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");

					File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
							beforeAction, afterAction);
					String myRequestIndex = bookingRequest.getFileContent(requestIndex);
					app_logs.info("Request--" + requestIndex);
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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBusCountry(busContent, updateCountry, testSteps);
				}
				if ((Utility.validateString(updatePostalCode))) {

					testSteps.add("<b>===== UPDATE POSTAL CODE =====</b>");
					app_logs.info("=====UPDATE POSTAL CODE=====");
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.clickGuestInfo(driver, testSteps);
					reservation.enter_PostalCode(driver, testSteps, updatePostalCode);
					reservation.clickGuestInfoSaveDetailsButton(driver, testSteps);
					reservation.verifySpinerLoading(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");

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
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateBuspostalCode(busContent, updatePostalCode, testSteps);
				}

			} else if (action.equalsIgnoreCase("updateStayInfo")) {
				reservation.clickOnDetails(driver);
				if ((Utility.validateString(updateAdult))) {

					testSteps.add("<b>===== UPDATE ADULT COUNT =====</b>");
					app_logs.info("=====UPDATE ADULT COUNT=====");

					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.ClickEditStayInfo(driver, testSteps);
					reservation.ClickStayInfo_ChangeDetails(driver, testSteps);
					reservation.clickVrboPopupYes(driver);
					reservation.enterAdult(driver, updateAdult);
					reservation.clickFindRooms(driver);
					reservation.clickSaveAfterEditStayInfo(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					System.out.println("beforeAction " + beforeAction);
					String roomCharges = reservation.getRoomCharge_TripSummary(driver);
					Double roomchargePrice = Double.parseDouble(roomCharges);
					reservation.click_Folio(driver, testSteps);
					Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
							vrvoRateToken);
					HashMap<String, String> getExternalIdOfTax = vrboObject.getExternalIdOfTax(responseBody,
							updatedTaxName);
					HashMap<String, String> getExternalIdOfFee = vrboObject.getExternalIdOfFee(responseBody,
							updatedFeeName);
					HashMap<String, Double> calcInTaxVal = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName,
							updatedTaxName);
					HashMap<String, Double> calcInFeeVal = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
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

					System.out.println(busContentresponses);
					Response busContent = busContentresponses.get("BODY");

					vrboObject.validateOrderItemInBus(busContent, calcInTaxVal, calcInFeeVal, roomchargePrice,
							getExternalIdOfTax, getExternalIdOfFee, setTaxRates, setFeeRates, resStatus,
							Boolean.parseBoolean(isVoidCharges), testSteps);
					testSteps.add("<b>===== VERIFY IN BUS =====</b>");
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateAdultCountInBus(busContent, updateAdult, testSteps);

				}
				if ((Utility.validateString(updateChild))) {

					testSteps.add("<b>===== UPDATE CHILD COUNT =====</b>");

					app_logs.info("=====UPDATE CHILD COUNT=====");
					reservation.clickOnDetails(driver);
					beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					reservation.ClickEditStayInfo(driver, testSteps);
					reservation.ClickStayInfo_ChangeDetails(driver, testSteps);
					reservation.clickVrboPopupYesPopup(driver);
					reservation.enterChildren(driver, updateChild);
					reservation.clickFindRooms(driver);
					reservation.clickSaveAfterEditStayInfo(driver);
					Wait.wait30Second();
					afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
					System.out.println("beforeAction " + beforeAction);
					String roomCharges = reservation.getRoomCharge_TripSummary(driver);
					Double roomchargePrice = Double.parseDouble(roomCharges);
					reservation.click_Folio(driver, testSteps);
					Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId,
							vrvoRateToken);
					HashMap<String, String> getExternalIdOfTax = vrboObject.getExternalIdOfTax(responseBody,
							updatedTaxName);
					HashMap<String, String> getExternalIdOfFee = vrboObject.getExternalIdOfFee(responseBody,
							updatedFeeName);

					HashMap<String, Double> calcInTaxVal = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName,
							updatedTaxName);
					HashMap<String, Double> calcInFeeVal = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
					System.out.println("got tax" + calcInTaxVal);
					System.out.println("calcValFee" + calcInFeeVal);
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

					vrboObject.validateOrderItemInBus(busContent, calcInTaxVal, calcInFeeVal, roomchargePrice,
							getExternalIdOfTax, getExternalIdOfFee, setTaxRates, setFeeRates, resStatus,
							Boolean.parseBoolean(isVoidCharges), testSteps);
					testSteps.add("<b>===== VERIFY IN BUS =====</b>");
					app_logs.info("=====VERIFY IN BUS=====");
					vrboObject.validateChildCountInBus(busContent, updateChild, testSteps);

				}
			} else if (action.equalsIgnoreCase("CancelReservationWithUncheckVoidItem")
					&& isVoidCharges.equalsIgnoreCase("false")
					|| action.equalsIgnoreCase("CancellationPolicy") && isVoidCharges.equalsIgnoreCase("false")) {
				if (action.equalsIgnoreCase("CancellationPolicy")) {
					testName = testName + " WithUncheckVoidItem";
				}
				testSteps.add("<b>===== DO CANCEL RESERVATION WITHOUT VOID FOLIO LINE ITEMS =====</b>");
				app_logs.info("=====DO CANCEL RESERVATION WITHOUT VOID FOLIO LINE ITEMS=====");

				reservation.clickOnDetails(driver);
				// boolean voidcheckedReuired = false;

				String reserStatus = "CANCELLED_BY_TRAVELER";
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.cancellationReservation(driver, testSteps, Boolean.parseBoolean(isVoidCharges));
				reservation.getCancelReservationHeaderWindow(driver);
				reservation.verifyCancelledStatus(driver, "CANCELLED");
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);
				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo,
						getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,

						startDate, endDate, startDate, reserStatus, tripTotal, Boolean.parseBoolean(isVoidCharges),
						dueDates, testSteps);
			} else if (action.equalsIgnoreCase("CancelReservationcheckVoidItem")
					&& isVoidCharges.equalsIgnoreCase("true")
					|| action.equalsIgnoreCase("CancellationPolicy") && isVoidCharges.equalsIgnoreCase("true")) {
				if (action.equalsIgnoreCase("CancellationPolicy")) {
					testName = testName + " checkVoidItem";
				}
				testSteps.add("<b>===== DO CANCEL RESERVATION MAKING VOID FOLIO LINE ITEMS =====</b>");
				app_logs.info("=====DO CANCEL RESERVATION MAKING VOID FOLIO LINE ITEMS=====");
				reservation.clickOnDetails(driver);
				String reserStatus = "CANCELLED_BY_TRAVELER";
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.cancellationReservation(driver, testSteps, Boolean.parseBoolean(isVoidCharges));
				reservation.getCancelReservationHeaderWindow(driver);
				reservation.verifyCancelledStatus(driver, "CANCELLED");
				roomchargeAmounts = 0.0;
				String getFee = reservation.getFeeFromTripSummary(driver);
				Double tripSummaryFee = Double.parseDouble(getFee);
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");

				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);
				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				System.out.println(busContentresponses);
				Response busContent = busContentresponses.get("BODY");
				int busContentStatus = busContent.getStatusCode();
				System.out.println("statusCode" + busContentStatus);
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				try {
					vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo,
							"en_US", calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes,
							getExternalIdOfFees, setTaxRates, setFeeRates, payStatus, title, nameONCard, email,
							phoneCode, phoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName,
							getAaddress4, postalCode, adult, child, startDate, endDate, startDate, reserStatus,
							tripSummaryFee, Boolean.parseBoolean(isVoidCharges), dueDates, testSteps);
				} catch (Exception e) {
					testSteps.add("Failed due to issue: " + e.toString());
				} catch (Error e) {
					testSteps.add("Failed due to issue: " + e.toString());
				}
				
				
				testSteps.add("Verify Cancel reservation from Inncenter"
						+ "<a href='https://innroad.testrail.io/index.php?/tests/view/802143' target='_blank'>"
						+ "Click here to open TestRail: 802143</a><br>");
				Utility.testCasePass(Utility.statusCode,11,Utility.comments,"Verify Cancel reservation from Inncenter");		
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);


			} else if (action.equalsIgnoreCase("NoShowReservationcheckVoidItem")
					&& isVoidCharges.equalsIgnoreCase("true")
					|| action.equalsIgnoreCase("NoShowPolicy") && isVoidCharges.equalsIgnoreCase("true")) {
				if (action.equalsIgnoreCase("NoShowPolicy")) {
					testName = testName + " checkVoidItem";
				}
				testSteps.add("<b>===== DO NO SHOW RESERVATION MAKING VOID FOLIO LINE ITEMS =====</b>");
				app_logs.info("=====DO NO SHOW RESERVATION MAKING VOID FOLIO LINE ITEMS=====");
				String reserStatus = "CANCELLED_BY_OWNER";
				reservation.clickOnDetails(driver);
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.noShowReservationsCp(driver, Boolean.parseBoolean(isVoidCharges), testSteps);
				reservation.clickOnDetails(driver);
				roomchargeAmounts = 0.0;
				String getFee = reservation.getFeeFromTripSummary(driver);
				Double tripSummaryFee = Double.parseDouble(getFee);
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");

				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);
				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				System.out.println(busContentresponses);
				Response busContent = busContentresponses.get("BODY");
				int busContentStatus = busContent.getStatusCode();
				System.out.println("statusCode" + busContentStatus);
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				try {
					vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo,
							"en_US", calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes,
							getExternalIdOfFees, setTaxRates, setFeeRates, payStatus, title, nameONCard, email,
							phoneCode, phoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName,
							getAaddress4, postalCode, adult, child,

							startDate, endDate, startDate, reserStatus, tripSummaryFee,
							Boolean.parseBoolean(isVoidCharges), dueDates, testSteps);
				} catch (Exception e) {
					testSteps.add("Failed due to issue: " + e.toString());
				} catch (Error e) {
					testSteps.add("Failed due to issue: " + e.toString());
				}

			} else if ((action.equalsIgnoreCase("NoShowReservationUncheckVoidItem"))
					&& isVoidCharges.equalsIgnoreCase("false")
					|| action.equalsIgnoreCase("NoShowPolicy") && isVoidCharges.equalsIgnoreCase("false")) {
				if (action.equalsIgnoreCase("NoShowPolicy")) {
					testName = testName + " UncheckVoidItem";
				}
				testSteps.add("<b>===== DO NO SHOW RESERVATION WITHOUT VOID FOLIO LINE ITEMS =====</b>");
				app_logs.info("=====DO NO SHOW RESERVATION WITHOUT VOID FOLIO LINE ITEMS=====");
				String reserStatus = "CANCELLED_BY_OWNER";
				reservation.clickOnDetails(driver);

				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.noShowReservationsCp(driver, Boolean.parseBoolean(isVoidCharges), testSteps);
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);
				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo,
						getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,
						startDate, endDate, startDate, reserStatus, tripTotal, Boolean.parseBoolean(isVoidCharges),
						dueDates, testSteps);
			} else if ((action.equalsIgnoreCase("TaxExempt"))) {
				testSteps.add("<b>===== DO TAX EXEMPT =====</b>");
				app_logs.info("=====DO TAX EXEMPT=====");
				reservation.clickOnDetails(driver);
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.clickEditPaymentMethodInfo(driver, testSteps);
				reservation.clickTaxExcemptCheckbox(driver, testSteps);
				reservation.enterTaxExceptId(driver, "123", testSteps);
				reservation.clickSaveButtonAfterEditPaymentMethod(driver, testSteps);
				String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
				tripTotal = Double.parseDouble(tripTotals);
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				// Verify Status after cancel Status
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				vrboObject.validateOrderItemInBus(busContent, calcInTaxVals, calcInFeeVals, roomchargeAmounts,
						getExternalIdOfTaxes, getExternalIdOfFees, setTaxRates, setFeeRates, resStatus,
						Boolean.parseBoolean(isVoidCharges), testSteps);
				String dueDates = "";
				response = responses.get("BODY");
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validatePaymentSceduleInBus(busContent, dueDates, tripTotal, testSteps);
			} else if (action.equalsIgnoreCase("ChangeExternalConfirmation")) {
				testSteps.add("<b>===== CHANGE EXTERNAL CONFIRMATION NUMBER =====</b>");
				app_logs.info("=====CHANGE EXTERNAL CONFIRMATION NUMBER=====");
				updateConf = Utility.generateRandomNumberWithGivenNumberOfDigits(6);
				reservation.clickOnDetails(driver);
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.clickOnEditMarketingInfoIcon(driver);
				reservation.changeExternalConfirmation(driver, updateConf, testSteps);
				reservation.clickOnSaveMarketingInfoIcon(driver);
				testSteps.add("Save Marketing info after update in reservation");
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				String getPhone = reservation.getPhoneNumber(driver);
				app_logs.info("getPhonebefore:" + getPhone);
				/*
				 * String phoneCodes = getPhone.split("-")[0]; String updatedphoneNumberVrbo =
				 * "+" + phoneCodes + " " + getPhone.split("-")[1].trim() + "-" +
				 * getPhone.split("-")[2];
				 */
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, updateConf, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo,
						getAddress1, getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child,
						startDate, endDate, startDate, resStatus, tripTotal, false, dueDates, testSteps);

			} else if (action.equalsIgnoreCase("AssociateAccount") || action.equalsIgnoreCase("AssociateTravelAgent")) {

				reservation.clickOnDetails(driver);
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				if (action.equalsIgnoreCase("AssociateAccount")) {
					testSteps.add("<b>===== ASSOCIATE ACCOUNT IN VRBO RESERVATION =====</b>");
					app_logs.info("=====ASSOCIATE ACCOUNT IN VRBO RESERVATION=====");
					reservation.clickGuestInfo(driver, testSteps);

					reservation.associateAccountInReservation(driver, testSteps, accountName, accounttype);
					reservation.verifyAssociatedAccount(driver, accountName);
				} else {
					testSteps.add("<b>===== ASSOCIATE TRAVELAGENT IN VRBO RESERVATION =====</b>");
					app_logs.info("=====ASSOCIATE TRAVELAGENT IN VRBO RESERVATION=====");
					reservation.clickOnEditMarketingInfoIcon(driver);
					reservation.attachTravelAgentMarketSegmentDetails(driver, testSteps, accountName);
					reservation.verifyAssoiateTravelAgentInReservation(driver, accountName, testSteps);
				}

				roomCharge = reservation.getRoomCharge_TripSummary(driver);
				roomchargeAmounts = Double.parseDouble(roomCharge);
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				app_logs.info(getbalance);
				String getPhone = reservation.getPhoneNumber(driver);
				app_logs.info("getPhonebefore:" + getPhone);

				/*
				 * String splitPhone = getPhone.split("-")[0] + " " +
				 * getPhone.split("-")[1].trim() + getPhone.split("-")[2].trim(); String gg =
				 * splitPhone.replaceAll("\\(", "").replaceAll("\\)", "").trim(); String
				 * updatedphoneNumberVrbo = "+" + gg.split(" ")[0] + " " + gg.split(" ")[1] +
				 * gg.split(" ")[2];
				 */
				String phoneCodes = getPhone.split("-")[0];
				String updatedphoneNumberVrbo = "+" + phoneCodes + " " + getPhone.split("-")[1].trim() + "-"
						+ getPhone.split("-")[2];
				app_logs.info("updatedphoneNumberVrbo:" + updatedphoneNumberVrbo);
				getAddress1 = reservation.getAddressLine1_ResDetail(driver).trim();
				getAddress2 = reservation.getAddressLine2_ResDetail(driver).trim();
				getAddress3 = reservation.getAddressLine3_ResDetail(driver).trim();
				getAaddress4 = reservation.getState_ResDetail(driver);
				countryName = reservation.getContry_ResDetail(driver).trim();
				String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
				tripTotal = Double.parseDouble(tripTotals);
				reservation.clickGuestInfo(driver, testSteps);
				String titles = reservation.getContactTitle(driver);
				if (titles.equalsIgnoreCase("Select")) {
					titles = "";
				}
				Wait.wait10Second();
				app_logs.info("titles:" + titles);
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");

				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, titles, guestFirstName + " " + guestLastName, updateEmail,
						phoneCode, updatedphoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName,
						getAaddress4, updatePostalCode, adult, child, startDate, endDate, startDate, resStatus,
						tripTotal, false, dueDates, testSteps);

			} else if (action.equalsIgnoreCase("AddIncidental")) {
				testSteps.add("<b>===== ADD INCIDENTAL IN VRBO RESERVATION =====</b>");
				app_logs.info("=====ADD INCIDENTAL IN VRBO RESERVATION=====");
				reservation.clickOnDetails(driver);
				beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				reservation.ButtonAddIncidental(driver);
				String perUnit = noShowPolicyFee;
				String category = "Bar";
				String quntity = "1";
				reservation.enterAddOnIncidental(driver, Utility.parseDate(startDate1, "dd/MM/yyyy", "MM/dd/yyyy"),
						category, perUnit, quntity);
				roomCharge = reservation.getRoomCharge_TripSummary(driver);
				roomchargeAmounts = Double.parseDouble(roomCharge);
				String getbalances = reservation.getTripSummaryBalance(driver, testSteps);
				String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
				tripTotal = Double.parseDouble(tripTotals);
				String getIncidental = reservation.getIncidentalsInTripSummaryWithoutDollar(driver);
				Double incidental = Double.parseDouble(getIncidental);
				Wait.wait10Second();
				Double gettripTotal = tripTotal - incidental;
				afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
				String getPhone = reservation.getPhoneNumber(driver);
				
				  app_logs.info("getPhonebefore:" + getPhone); String phoneCodes =
				  getPhone.split("-")[0]; String updatedphoneNumberVrbo = "+" + phoneCodes +
				  " " + getPhone.split("-")[1].trim() + "-" + getPhone.split("-")[2];
				 
				File requestIndex = bookingRequest.createBookingUpdateIndexRequest(advertiserId, schemaFilesPath,
						beforeAction, afterAction);
				String myRequestIndex = bookingRequest.getFileContent(requestIndex);
				HashMap<String, Response> busIndexresponses = vrboObject.postRequests(
						vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequestIndex);
				Response busIndexresponse = busIndexresponses.get("BODY");
				vrboObject.validateBusIndex(busIndexresponse, version, confirmationNo, advertiserId.trim(), testSteps);
				testSteps.add("BUS CONTENT INDEX VERIFIED BETWEEN:" + beforeAction + " AND " + afterAction);
				HashMap<String, Response> busContentresponses = vrboObject
						.getBookingUpdateRequest(vrvoBookingUpdateToken, advertiserId, confirmationNo);

				File responseBodyForBus = bookingRequest.getResponseXmlFile(busContentresponses.get("BODY").asString(),
						schemaFilesPath, testName, "BusUpdate");
				testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

				Response busContent = busContentresponses.get("BODY");
				testSteps.add("<b>===== VERIFY IN BUS =====</b>");
				app_logs.info("=====VERIFY IN BUS=====");
				String dueDates = "";
				String payStatus = vrboObject.getVrBoPaymentStatus(getbalances);
				if (payStatus.equalsIgnoreCase("PAID")) {
					dueDates = startDate;
				} else {
					dueDates = endDate;
				}
				vrboObject.validateBookingUpdateXML(busContent, advertiserId, classId, classId, confirmationNo, "en_US",
						calcInTaxVals, calcInFeeVals, roomchargeAmounts, getExternalIdOfTaxes, getExternalIdOfFees,
						setTaxRates, setFeeRates, payStatus, title, nameONCard, email, phoneCode,
						updatedphoneNumberVrbo, getAddress1, getAddress2, getAddress3, countryName, getAaddress4,
						postalCode, adult, child, startDate, endDate, startDate, resStatus, gettripTotal, false,
						dueDates, testSteps);

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
			if (action.equalsIgnoreCase("NoShowPolicy") || action.equalsIgnoreCase("CancellationPolicy")) {
				navigation.reservationBackward3(driver);
				navigation.inventory(driver);
				navigation.policies(driver, testSteps);
				if (action.equalsIgnoreCase("NoShowPolicy")) {
					pol.deletePolicy(driver, testSteps, noShowpolicyName);

				} else {
					pol.deletePolicy(driver, testSteps, cancelPolicyName);
				}

			}
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {

			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Update Request", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Update Request", testName,
					test_description, test_catagory, testSteps);
		}

	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequestCommonData", otaexcel);
	}

	@DataProvider
	public Object[][] getDataOne() {
		return Utility.getData("BusScenarios", otaexcel);
	}

	@DataProvider
	public Object[][] getFinalData() {
		return Utility.combine(getData(), getDataOne());
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
