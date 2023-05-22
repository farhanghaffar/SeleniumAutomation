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
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import io.restassured.response.Response;

public class VerifyBUSChangeRatePlan extends TestCore{

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
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	TaxesAndFee taxFee = new TaxesAndFee();
	Response response;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getFinalData", groups = "Vrbo")
	public void verifyBUSChangeRatePlan(String version, String roomClassName, String channel, String message,
			String title, String firstName, String lastName, String email, String phoneNo, String address1,
			String address2, String address3, String address4, String country, String adult, String child, String pet,
			String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, String cardCode,
			String cardType, String masterListingChannel, String clientIPAddress, String trackingUuid,
			String numberToken, String testCaseName, String beginDate, String noOfNights, String originationDate,
			String chargeName, String roomchargeAmount, String IsTaxAdd, String taxNameInXml, String taxAmountInXml,
			String setTaxRate, String taxExternalId, String isFeeAdd, String feeNameInXml, String feeAmount,
			String setFeeRate, String feeExternalId, String paymentSheduleItem, String isExtraAdultInXml,
			String extraAdultAmount, String isExtraChildInXml, String extraChildAmount, String isInncenterTaxCreate,
			String taxName, String type, String categoryTax, String intaxValues, String ledgerAccount,
			String isInncenterFeeCreate, String feeName, String feeType, String feeCategoryValue, String categoryFee,
			String updatedRatePlan) {
		 
		String testName = testCaseName;
		String startDate = null, endDate = null,  confirmationNo = null, expiryDate = null,
				startDate1 = null, endDate1 = null, advertiserId = null, classId = null, currency = null,propertyId = null,
						phoneCode = null, phoneNumberVrbo = null, getAddress1 = null, getAddress2 = null, getAddress3 = null,
						getAaddress4 = null,countryName=null;
		String resStatus = "CONFIRMED";
		Double tripTotal = 0.0;
		boolean isVoidCharges = false;
		int days=0;
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> categoryTaxs = Utility.convertTokenToArrayList(categoryTax, Utility.DELIMS);
		ArrayList<String> intaxValue = Utility.convertTokenToArrayList(intaxValues, Utility.DELIMS);
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		HashMap<String, String> setFeeRates = new HashMap<String, String>();
		ArrayList<String> intaxname= new ArrayList<String>();
		ArrayList<String> infeeNames= new ArrayList<String>();
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
			admin.clickAutoMaskPhone(driver, false, testSteps);
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
			
			testSteps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);
			app_logs.info("Getting PropertyId ID" + propertyId);
			navigation.clickTaxesAndFees(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Create new Tax ======================");
				 intaxname = Utility.convertTokenToArrayList(taxName, Utility.DELIMS);

				for (int i = 0; i < intaxname.size(); i++) {
					String upTaxName = intaxname.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedTaxName.add(upTaxName);
				}

				ArrayList<String> taxType = Utility.convertTokenToArrayList(type, Utility.DELIMS);

				for (int i = 0; i < intaxname.size(); i++) {
					taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
							taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "", ledgerAccount, false, "", "",
							"", testSteps);
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
				 infeeNames = Utility.convertTokenToArrayList(feeName, Utility.DELIMS);
				for (int i = 0; i < infeeNames.size(); i++) {
					String upfeeName = infeeNames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedFeeName.add(upfeeName);
				}

				ArrayList<String> infeeValue = Utility.convertTokenToArrayList(feeCategoryValue, Utility.DELIMS);
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
		}catch (Exception e) {
			
			Utility.catchException(driver,e,  "Get Data",  "Get Data", "Get Data",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			
			Utility.catchError(driver,e, "Get Data",  "Get Data", "Get Data", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			testSteps.add("====================Verify API End Point======================");
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDate("dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)),
							"MM/dd/yyyy", "dd/MM/yyyy");
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
			File requestBody = bookingRequest.createBookingRequest(testName, version, advertiserId, classId, channel,
					message, title, firstName, lastName, email, phoneNo, address1, address2, address3, address4,
					country, adult, child, startDate, endDate, originationDate, currency, chargeName, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					IsTaxAdd, taxNameInXml, taxAmountInXml, setTaxRate, isExtraAdultInXml, extraAdultAmount,
					isExtraChildInXml, extraChildAmount, isFeeAdd, feeNameInXml, feeAmount, setFeeRate, taxExternalId,
					feeExternalId, roomchargeAmount, paymentSheduleItem, masterListingChannel, clientIPAddress,
					trackingUuid, numberToken);
			
			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--" + myRequest);
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
			responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestToken"),
					VrboEndPoints.bookingRequest, myRequest);
			}else {
				responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestTokenQA3"),
						VrboEndPoints.bookingRequest, myRequest);
			}
			
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
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath, testName,"BusXml");

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
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath, testName,"BusXml");
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
			} else {
				app_logs.info("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
				testSteps.add("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
			}
		}catch (Exception e) {
			Utility.catchException(driver,e, "Booking Request", "Booking Request", "Booking Request", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver,e, "Booking Request", "Booking Request", "Booking Request", testName, test_description,
					test_catagory, testSteps);
		}

		
		try {
			testSteps.add("====================Verify BUS AFTER RESERVATION CREATION======================");
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			 days = datesRangeList.size();
			navigation.reservationBackward3(driver);
			rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
			String roomCharge = reservation.getRoomCharge_TripSummary(driver);
			Double roomchargeAmounts = Double.parseDouble(roomCharge);
			app_logs.info(roomchargeAmounts);
			String getbalance=reservation.getTripSummaryBalance(driver,  testSteps);
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
			Response responseBody;
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
			 responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
			}else {
				 responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoproperties.getProperty("BookingRequestTokenQA3"));
			}
			int statusCode = responseBody.getStatusCode();
			app_logs.info("rate statusCode" + statusCode);
			HashMap<String, String> getExternalIdOfTax = vrboObject.getExternalIdOfTax(responseBody, updatedTaxName);
			app_logs.info(getExternalIdOfTax);
			HashMap<String, String> getExternalIdOfFee = vrboObject.getExternalIdOfFee(responseBody, updatedFeeName);
			app_logs.info(getExternalIdOfFee);

			HashMap<String, Double> calcInTaxVal = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName,
					updatedTaxName);
			HashMap<String, Double> calcInFeeVal = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
			app_logs.info("got tax" + calcInTaxVal);
			app_logs.info("calcValFee" + calcInFeeVal);
			HashMap<String, Response> responses= new HashMap<String, Response>();
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
			responses = vrboObject.getBookingUpdateRequest(vrvoBookingUpdateToken,
					advertiserId, confirmationNo);
			}else {
				responses = vrboObject.getBookingUpdateRequest(vrvoproperties.getProperty("BookingRequestTokenQA3"),
						advertiserId, confirmationNo);
			}
			app_logs.info(responses);
			response = responses.get("BODY");
			int statusCode1 = response.getStatusCode();
			app_logs.info("statusCode" + statusCode1);
			
			String dueDates = "";
			String payStatus = vrboObject.getVrBoPaymentStatus(getbalance);
			if(payStatus.equalsIgnoreCase("PAID")) {
				dueDates = startDate;
			}else {
				dueDates = endDate;
			}
			vrboObject.validateBookingUpdateXML(response, advertiserId, classId, classId, confirmationNo, "en_US",
					calcInTaxVal, calcInFeeVal, roomchargeAmounts, getExternalIdOfTax, getExternalIdOfFee, setTaxRates,
					setFeeRates, payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo, getAddress1,
					getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child, startDate, endDate,
					startDate,  resStatus, tripTotal, isVoidCharges,dueDates,testSteps);
		} catch (Exception e) {
			
			Utility.catchException(driver,e, "Booking Request", "Booking Request", "Booking Request", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			
			Utility.catchError(driver,e, "Booking Request", "Booking Request", "Booking Request", testName, test_description,
					test_catagory, testSteps);
		}
		
		String beforeAction = null;
		String afterAction = null;
		try {
			beforeAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
			app_logs.info(beforeAction);
			testSteps.add("Time Before Update: " +beforeAction );
			testSteps.add("====================CHANGE RATE PLAN======================");	
			reservation.clickOnDetails(driver);
			reservation.ClickEditStayInfo(driver, testSteps);
			reservation.ClickStayInfo_ChangeDetails(driver, testSteps);
			reservation.clickVrboPopupYes(driver);
			reservation.select_Rateplan(driver, testSteps, updatedRatePlan, "");
			reservation.clickFindRooms(driver);
			Wait.waitforPageLoad(20, driver);
			reservation.selectRoom(driver, testSteps, roomClassName, "","");
			reservation.clickNewPolicesApplicablePopupYesNo(driver, true, testSteps);
			reservation.clickSaveAfterEditStayInfo(driver);
			Wait.waitforPageLoad(30, driver);
			
		}catch (Exception e) {
			
			Utility.catchException(driver,e, "Change Rate Plan", "Change Rate Plan", "Change Rate Plan", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			
			Utility.catchError(driver,e, "Change Rate Plan", "Change Rate Plan", "Change Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		
		
		try {
			afterAction = Utility.getCurrentDatTimeInUTC("yyyy-MM-dd'T'HH:mm:ss'Z'");
			app_logs.info(afterAction);
			testSteps.add("Time After Update: " +afterAction );
			testSteps.add("====================VERIFY BOOKING UPDATE======================");	
			reservation.clickOnDetails(driver);
			File requestBody = bookingRequest.createBookingUpdateIndexRequest(advertiserId,schemaFilesPath,beforeAction,afterAction);
			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--" + myRequest);
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
				responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequest);
			}else {
			responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestTokenQA3"),
					VrboEndPoints.generateVrboBusdIndexEndPoint(advertiserId), myRequest);
			}
			
			response = responses.get("BODY");
			int statusCode = response.getStatusCode();
			app_logs.info("statusCode "+ statusCode);
			vrboObject.validateBusIndex(response,version, confirmationNo,advertiserId,testSteps);
			HashMap<String, Response> responses= new HashMap<String, Response>();
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
			 responses = vrboObject.getBookingUpdateRequest(vrvoBookingUpdateToken,
					advertiserId, confirmationNo);
			}else {
				 responses = vrboObject.getBookingUpdateRequest(vrvoproperties.getProperty("BookingRequestTokenQA3"),
						advertiserId, confirmationNo);
			}
			app_logs.info(responses);
			response = responses.get("BODY");
			int statusCode1 = response.getStatusCode();
			app_logs.info("statusCode" + statusCode1);
			
			HashMap<String, String> dates= reservation.getStayInfoCheckInCheckOutDate(driver);
			
			String SDate=Utility.parseDate(dates.get("CheckIN"), "MMM dd, yyyy", "YYYY-MM-dd");
			String EDate=Utility.parseDate(dates.get("ChecOUT"), "MMM dd, yyyy", "YYYY-MM-dd");
			
			 app_logs.info(SDate);
			 app_logs.info(EDate);
		
			String roomCharge = reservation.getRoomCharge_TripSummary(driver);
			Double roomchargeAmounts = Double.parseDouble(roomCharge);
			app_logs.info(roomchargeAmounts);			
			String getbalance1=reservation.getTripSummaryBalance(driver,  testSteps);
			app_logs.info(getbalance1);
			String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
			tripTotal = Double.parseDouble(tripTotals);
			reservation.click_Folio(driver, testSteps);
			Response responseBody;
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
				 responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
				}else {
					 responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoproperties.getProperty("BookingRequestTokenQA3"));
				}
			
			HashMap<String, String> getExternalIdOfTax = vrboObject.getExternalIdOfTax(responseBody, updatedTaxName);
			app_logs.info(getExternalIdOfTax);
			HashMap<String, String> getExternalIdOfFee = vrboObject.getExternalIdOfFee(responseBody, updatedFeeName);
			app_logs.info(getExternalIdOfFee);

			HashMap<String, Double> calcInTaxVal = reservation.getVrboTaxesInRoomChargeFolio(driver, chargeName,
					updatedTaxName);			
			HashMap<String, Double> calcInFeeVal = reservation.getVrboFeeInFolio(driver, updatedFeeName, days);
			app_logs.info("got tax" + calcInTaxVal);
			app_logs.info("calcValFee" + calcInFeeVal);
			HashMap<String, Response> responses1= new HashMap<String, Response> ();
			if(vrvoproperties.getProperty("Environment").equalsIgnoreCase("QA")) {
			 responses1 = vrboObject.getBookingUpdateRequest(vrvoBookingUpdateToken,
					advertiserId, confirmationNo);
			}else {
				 responses1 = vrboObject.getBookingUpdateRequest(vrvoproperties.getProperty("BookingRequestTokenQA3"),
						advertiserId, confirmationNo);
			}
			File responseBodyForBus = bookingRequest.getResponseXmlFile(responses1.get("BODY").asString(), schemaFilesPath, testName,"BusUpdate");
            testSteps.add("Bus Update Response Body-" + "<a href='" + responseBodyForBus + "' target='_blank'>"
                    + "Click here to open : URL</a><br>");
			app_logs.info(responses1);
			response = responses1.get("BODY");
			app_logs.info(response);
			
			String dueDates = "";
			String payStatus = vrboObject.getVrBoPaymentStatus(getbalance1);
			if(payStatus.equalsIgnoreCase("PAID")) {
				dueDates = startDate;
			}else {
				dueDates = endDate;
			}
			
			vrboObject.validateBookingUpdateXML(response, advertiserId, classId, classId, confirmationNo, "en_US",
					calcInTaxVal, calcInFeeVal, roomchargeAmounts, getExternalIdOfTax, getExternalIdOfFee, setTaxRates,
					setFeeRates,payStatus, title, nameONCard, email, phoneCode, phoneNumberVrbo, getAddress1,
					getAddress2, getAddress3, countryName, getAaddress4, postalCode, adult, child, SDate, EDate,SDate,
					 resStatus, tripTotal, isVoidCharges,dueDates,testSteps);
		}catch (Exception e) {
			
			Utility.catchException(driver,e, "VERIFY BOOKING UPDATE", "VERIFY BOOKING UPDATE", "VERIFY BOOKING UPDATE", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			
			Utility.catchError(driver,e, "VERIFY BOOKING UPDATE", "VERIFY BOOKING UPDATE", "VERIFY BOOKING UPDATE", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			navigation.navigateToSetupFromReservationPage(driver, testSteps);
			navigation.clickTaxesAndFees(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");

			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Delete Tax======================");
				for (int i = 0; i < intaxname.size(); i++) {
					taxFee.deleteTaxAndFee(driver, intaxname.get(i), testSteps);
				}
			}
			if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Delete Fee======================");
				for (int i = 0; i < infeeNames.size(); i++) {
					taxFee.deleteTaxAndFee(driver, infeeNames.get(i), testSteps);
				}
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			
			Utility.catchException(driver,e, "Delete Tax","Delete Tax", "Delete Tax", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			
			Utility.catchError(driver,e, "Delete Tax","Delete Tax", "Delete Tax", testName, test_description,
					test_catagory, testSteps);
		}
		
		
	}

	
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequestCommonData", otaexcel);
	}

	@DataProvider
	public Object[][] getDataOne() {

		return Utility.getData("BUSChangeRatePlan", otaexcel);

	}

	@DataProvider
	public Object[][] getFinalData() {
		return Utility.combine(getData(), getDataOne());
	}

	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
