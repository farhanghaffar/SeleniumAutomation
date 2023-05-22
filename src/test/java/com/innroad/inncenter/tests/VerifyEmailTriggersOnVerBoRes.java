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
import com.innroad.inncenter.pageobjects.DocumnetTemplates;
import com.innroad.inncenter.pageobjects.Login;
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
import io.restassured.response.Response;

public class VerifyEmailTriggersOnVerBoRes extends TestCore {
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
	TaxesAndFee taxFee = new TaxesAndFee();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();
	Account createCA = new Account();
	Response response;
	private DocumnetTemplates documentTemplate = new DocumnetTemplates();
	private String emailSubject;

	private int emailCount = 0;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "Vrbo")
	public void verifyEmailTriggersOnVerBoRes(String version, String roomClassName, String channel, String message,
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
			String infeeName, String feeType, String infeeValues, String categoryFee, String emailSetup,
			String isDefaultTemplate, String templateDescription, String documentTemplateName,
			String confirmationEmailTriggers, String confirmationEmailEvents, String contentFields, String action) throws ParseException {
		String startDate = null, endDate = null, confirmationNo = null, expiryDate = null, startDate1 = null,
				endDate1 = null, advertiserId = null, classId = null, currency = null, propertyId = null,
				ratePlanVrbo = null, InncenterReferal = "GDS", InncenterMarket = "GDS", sourceName = "Vrbo";
		HashMap<String, String> emailContentMap = new HashMap<String, String>();
		Login login = new Login();
		String testName = "BUSScenario_" + action;
		HashMap<String, String> setFeeRates = new HashMap<String, String>();
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValue = Utility.convertTokenToArrayList(intaxValues, Utility.DELIMS);
		ArrayList<String> categoryTaxs = Utility.convertTokenToArrayList(categoryTax, Utility.DELIMS);
		Utility.initializeTestCase("77682265|77682266", Utility.testId, Utility.statusCode, Utility.comments, "");
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

			emailSubject = "EXTERNAL:Reservation #: " + confirmationNo;
			emailCount = login.getSubjectLineFromOTPMails(emailSubject, testSteps);
			
			driver = getDriver();
			loginOTA(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			emailContentMap.put("referral", InncenterReferal);
			emailContentMap.put("marketSegment", InncenterMarket);
			;
			emailContentMap.put("RoomClass", roomClassName);
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
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
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
			emailContentMap.put("ContactName", nameONCard);
			testSteps.add("==========SETUP EMAIL'S ON PROPERTY PAGE ==========");
			app_logs.info("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
			navigation.reservationBackward3(driver);
			navigation.navSetup(driver);
			navigation.Properties(driver);
			navigation.openProperty(driver, testSteps, property);
			navigation.emailSetupOnPropertyPage(driver, emailSetup, testSteps);
			navigation.clickPropertyOptions(driver, testSteps);
			navigation.setupManualCustomEmail(driver, emailSetup, testSteps);
			navigation.setupScheduleCustomEmail(driver, emailSetup, testSteps);
			navigation.setupCCEmail(driver, emailSetup, testSteps);
			navigation.PublishButton(driver);

			testSteps.add("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
			app_logs.info("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
			// navigation.setup(driver);
			navigation.navigateDocumentTemplate(driver);
			documentTemplate.deleteAllDocuments(driver);
			documentTemplate.clickNewDocument(driver, testSteps);
			documentTemplate.enterDocumnetName(driver, documentTemplateName);
			documentTemplate.enterDocumnetDescription(driver, templateDescription, testSteps);
			if (!isDefaultTemplate.equalsIgnoreCase("NO")) {
				documentTemplate.select_DefaultTemplate(driver, testSteps);
			}

			testSteps.add("=================== ASSOCIATING SOURCES ======================");
			app_logs.info("=================== ASSOCIATING SOURCES ======================");
			// documentTemplate.associateSources(driver, test_steps);
			documentTemplate.associateSource(driver, sourceName, testSteps);

			testSteps.add("=================== ASSOCIATING PROPERTIES ======================");
			app_logs.info("=================== ASSOCIATING PROPERTIES ======================");
			documentTemplate.associateProperties(driver, property, testSteps);

			testSteps.add("=================== ASSOCIATING FUNCTIONS ======================");
			app_logs.info("=================== ASSOCIATING FUNCTIONS ======================");

			String[] attachments = new String[0];
			if (action.equalsIgnoreCase("GuestEmail")) {
				documentTemplate.addOrAssociateConfirmationEmailFunction(driver, confirmationEmailTriggers,
						confirmationEmailEvents, attachments, testSteps);
			} else {
				documentTemplate.addOrAssociateConfirmationEmailFunctionForGDS(driver, confirmationEmailTriggers,
						confirmationEmailEvents, attachments, testSteps);
			}

			testSteps.add("=================== ADDING CONTENT ======================");
			app_logs.info("=================== ADDING CONTENT ======================");
			documentTemplate.click_Content(driver, testSteps);
			String[] contentFieldArray = contentFields.split("\\|");
			for (String field : contentFieldArray) {
				documentTemplate.enter_SpecificContentFields(driver, testSteps, field);
			}
			documentTemplate.saveDocument(driver, testSteps);
			navigation.navReservation(driver);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
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
					message, title, firstName, lastName, emailSetup, phoneNo, address1, address2, address3, address4,
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
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
				String tripTotals = reservation.getTripTotal_TripSummary(driver).trim();
				String roomCharge = reservation.getRoomCharge_TripSummary(driver);
				String totalTax = reservation.get_TripSummaryTaxesWithoutCurrency(driver, testSteps);
				emailContentMap.put("RoomCharges", roomCharge);
				emailContentMap.put("TotalTaxes", totalTax);
				emailContentMap.put("TotalCharges", tripTotals);

				testSteps.add("===================== EMAIL CONTENT VERIFICATION =========================");
				app_logs.info("================EMAIL CONTENT VERIFICATION ================");
				emailContentMap.put("ArrivalDate", Utility.parseDate(startDate1, "dd/MM/yyyy", "MMM dd, yyyy"));
				emailContentMap.put("DepartureDate", Utility.parseDate(endDate1, "dd/MM/yyyy", "MMM dd, yyyy"));
				emailContentMap.put("ConfirmationNumber", confirmationNo);
				if (action.equalsIgnoreCase("GuestEmail")) {
					emailSubject = "EXTERNAL:Reservation #: " + confirmationNo;
					emailCount = login.getSubjectLineFromOTPMails(emailSubject, testSteps);
					if (emailCount == 0) {
						emailSubject = "EXTERNAL:Reservation #: " + confirmationNo;
						emailCount = login.getSubjectLineFromOTPMails(emailSubject, testSteps);
					}
				} else {

					emailSubject = "EXTERNAL:Reservation #: " + confirmationNo;
					emailCount = login.getSubjectLineFromOTPMails(emailSubject, testSteps);
				}

				System.out.println("emailCount: " + emailCount);
				boolean validateConfirmationLetter = false;
				if (emailCount > 0) {
					documentTemplate.validateEmail(driver, emailSubject, confirmationEmailTriggers, contentFields,
							documentTemplateName, validateConfirmationLetter, 60, emailCount, emailContentMap,
							confirmationNo, testSteps);
				} else {
					testSteps.add("Email not recieved, please check the Inbox: ");
				}
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
			
			Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify scheduling email and trigger set as on for reserved status via HA/Vrbo reservation");
			testSteps.add("Verify scheduling email and trigger set as on for reserved status via HA/Vrbo reservation"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/77682265' target='_blank'>"
				+ "Click here to open TestRail: 77682265</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			
			Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify sending GDS Confirmation email on reservation creation for VRBO Source");
			testSteps.add("Verify sending GDS Confirmation email on reservation creation for VRBO Source"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/77682266' target='_blank'>"
				+ "Click here to open TestRail: 77682266</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			
			
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

		return Utility.getData("VerifyEmailTriggersOnVerBoRes", otaexcel);

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
