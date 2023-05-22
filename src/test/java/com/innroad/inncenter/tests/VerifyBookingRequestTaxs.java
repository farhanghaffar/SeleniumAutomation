package com.innroad.inncenter.tests;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import io.restassured.response.Response;

public class VerifyBookingRequestTaxs extends TestCore {

	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	VrboObjects vrboObject = new VrboObjects();
	ReservationSearch rsvSearch = new ReservationSearch();
	CPReservationPage reservation = new CPReservationPage();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	HashMap<String, Response> responses = new HashMap<String, Response>();
	HashMap<String, String> response = new HashMap<String, String>();
	Navigation navigation = new Navigation();
	RoomStatus roomStatus = new RoomStatus();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	TaxesAndFee taxFee = new TaxesAndFee();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "Vrbo")
	public void bookingRequestMultipleTax(String version, String roomClassName, String channel, String message,
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
			String isInncenterFeeCreate, String feeName, String feeType, String feeCategoryValue, String categoryFee)
			throws ParseException {
	String testCaseID="802296|804336";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = null, startDate = null, endDate = null, confirmationNo = null, expiryDate = null,
				startDate1 = null, endDate1 = null, roomClassId = null, advertiserId = null, currency = null;
		ArrayList<String> taxname = new ArrayList<String>();
		ArrayList<String> feename = new ArrayList<String>();
		ArrayList<String> feeCategoriesValue = new ArrayList<String>();
		ArrayList<String> feeAmountOTAs = new ArrayList<String>();
		ArrayList<String> feetype = new ArrayList<String>();
		ArrayList<String> catgoriesFee = new ArrayList<String>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> taxCategoriesValue = new ArrayList<String>();
		ArrayList<String> catgoriesTax = new ArrayList<String>();
		try {

			testName = testCaseName;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			Utility.initializeTestCase("802296|804336", Utility.testId, Utility.statusCode, Utility.comments, "");
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
			roomClassId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + roomClassId);
			app_logs.info("Room Class ID " + roomClassId);

			navigation.clickTaxesAndFeesAfterRoomClass(driver);
			testSteps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");

			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				//taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", testSteps);
				taxFee.deleteAllTaxesAndFee(driver, testSteps);
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Create new Tax ======================");
				ArrayList<String> taxNames = Utility.splitInputData(taxName);
				ArrayList<String> taxType = Utility.splitInputData(type);
				catgoriesTax = Utility.splitInputData(categoryTax);
				taxCategoriesValue = Utility.splitInputData(intaxValues);

				for (int i = 0; i < taxNames.size(); i++) {
					taxname.add(taxNames.get(i) + Utility.generateRandomNumber());
					taxFee.createTaxes(driver, taxname.get(i), taxname.get(i), taxname.get(i), taxType.get(i),
							catgoriesTax.get(i), taxCategoriesValue.get(i), "", ledgerAccount, false, "", "", "",
							testSteps);
				}
			}
			if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("====================Create new Fee ======================");

				ArrayList<String> feeNames = Utility.splitInputData(feeName);

				feeCategoriesValue = Utility.splitInputData(intaxValues);
				feetype = Utility.splitInputData(feeType);
				catgoriesFee = Utility.splitInputData(categoryFee);
				for (int i = 0; i < feeNames.size(); i++) {
					feename.add(feeNames.get(i) + Utility.generateRandomNumber());
					taxFee.createFee(driver, testSteps, feename.get(i), feename.get(i), feetype.get(i), feename.get(i),
							catgoriesFee.get(i), feeCategoriesValue.get(i), false, "", "", "");
				}
			}
			app_logs.info(feename);

		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee", testName,
					test_description, test_catagory, testSteps);
		}

		try {
 			ArrayList<String> feeXml = Utility.splitInputData(feeNameInXml);
			ArrayList<String> feeAmtXml = Utility.splitInputData(feeAmount);
			for (int i = 0; i < feeXml.size(); i++) {
				feeAmountOTAs.add(feeAmtXml.get(i));
			}
			app_logs.info(feeAmountOTAs);
			testSteps.add("====================Post Request End Point======================");
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
			File requestBody = bookingRequest.createBookingRequest(testName,version, advertiserId, roomClassId, channel, message,
					title, firstName, lastName, email, phoneNo, address1, address2, address3, address4, country, adult,
					child, startDate, endDate, originationDate, currency, chargeName, startDate, paymentType,
					postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath, IsTaxAdd,
					taxNameInXml, taxAmountInXml, setTaxRate, isExtraAdultInXml, extraAdultAmount, isExtraChildInXml,
					extraChildAmount, isFeeAdd, feeNameInXml, feeAmount, setFeeRate, taxExternalId, feeExternalId,
					roomchargeAmount, paymentSheduleItem, masterListingChannel, clientIPAddress, trackingUuid,
					numberToken);

			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--" + myRequest);

			responses = vrboObject.postRequests(vrvoproperties.getProperty("BookingRequestToken"),
					VrboEndPoints.bookingRequest, myRequest);
			response = vrboObject.postRequestResponseData(responses.get("BODY"), VrboEndPoints.bookingRequest);

			if (!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")
					&& !response.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(response.get("BODY"), schemaFilesPath,testName,"bookingResponse");
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				confirmationNo = bookingRequest.getValueOfXmlTags(response.get("BODY"), "externalId");
				testSteps.add("Confirmation No -" + confirmationNo);
				app_logs.info("Confirmation No -" + confirmationNo);
			} else if (response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(response.get("BODY"), schemaFilesPath,testName,"bookingResponse");
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

			} else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
			}

			testSteps.add("====================Validate End Point Response======================");

			vrboObject.validateBookingResponse(responses.get("BODY"), advertiserId, roomClassId, roomClassId,
					confirmationNo, adult, child, pet, startDate, endDate, IsTaxAdd, taxNameInXml, taxAmountInXml,
					setTaxRate, isExtraAdultInXml, extraAdultAmount, isExtraChildInXml, extraChildAmount, isFeeAdd,
					feeNameInXml, feeAmount, setFeeRate, taxExternalId, feeExternalId, roomchargeAmount,
					paymentSheduleItem, "Moderate", originationDate, testSteps);

		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Request Multiple Tax", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Request Multiple Tax", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			
			if (!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")
					&& !response.get("BODY").contains("SERVER_ERROR")) {
				navigation.reservationBackward3(driver);
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
				roomchargeAmount = Utility.convertDecimalFormat(roomchargeAmount);
				app_logs.info(roomchargeAmount);
				testSteps.add("====================Verify Folio Line Item ON Reservation======================");
				reservation.click_Folio(driver, testSteps);
				reservation.includeTaxesinLineItems(driver, testSteps, true);
				int days = datesRangeList.size();
				String oneDayRoomCharge = reservation.perDayRoomChargeIncludingExtraAdultChild(isExtraAdultInXml,
						isExtraChildInXml, roomchargeAmount, extraAdultAmount, extraChildAmount, days);
				ArrayList<String> taxesAmountXML = Utility.convertTokenToArrayList(taxAmountInXml, Utility.DELIMS);
	
				ArrayList<String> feeAmt = new ArrayList<String>();

				for (int i = 0; i < datesRangeList.size(); i++) {
					
				String oneDayRoomCharge1 = reservation.perDayRoomChargeIncludingExtraAdultChild(isExtraAdultInXml,
							isExtraChildInXml, roomchargeAmount, extraAdultAmount, extraChildAmount, days);
					HashMap<String, String> convertedValFromPercentage = reservation.calculateTaxes(oneDayRoomCharge1,
							taxname, taxCategoriesValue, catgoriesTax, taxesAmountXML, days);
						testSteps.add("====================Verify Tax Adjustment on Folio Screen======================");
						ArrayList<HashMap<String,HashMap<String, String>>> taxPerDayCal=reservation.verifyVrBoTaxAdjustMent(driver, testSteps, ledgerAccount, convertedValFromPercentage,
							taxname, taxesAmountXML, datesRangeList, IsTaxAdd);
					app_logs.info(taxPerDayCal);
					ArrayList<String> taxAmount=new ArrayList<String>();
					for(HashMap<String, HashMap<String, String>> entry: taxPerDayCal) {
						HashMap<String, String> t1=entry.get(datesRangeList.get(i));
						app_logs.info(t1);
					    if(t1!=null){
						for (Entry<String, String> pair : t1.entrySet()) { 
							taxAmount.add(pair.getValue());
						}
						}
				    }
					app_logs.info(taxAmount);
					
			String  perDayChargeWithTax =reservation.perDayChargeWithTax(driver, oneDayRoomCharge1,taxAmount);
			testSteps.add("====================Verify Room Charges on Folio Screen for Date "
					+ datesRangeList.get(i) + "======================");	
					reservation.verifyFolioLineItem(driver, datesRangeList.get(i), chargeName, "HomeAway Rate",
							perDayChargeWithTax, testSteps);
					
					if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
						testSteps.add("====================Verify Fee Adjustment on Folio Screen "
								+ datesRangeList.get(i) + "======================");
						for (int j = 0; j < feename.size(); j++) {
							feeAmt.add(reservation.calculateAdjustmentFee(driver, catgoriesFee.get(j), oneDayRoomCharge,
									feeCategoriesValue.get(j)));

							reservation.verifyFolioLineItem(driver, datesRangeList.get(i), feetype.get(j),
									feename.get(j), feeAmt.get(j), testSteps);
						}
					}
				}

				app_logs.info(feeAmt);
				String date = datesRangeList.get(days - 1);
				app_logs.info(date);
				if (isInncenterFeeCreate.equalsIgnoreCase("Yes") || isFeeAdd.equalsIgnoreCase("Yes")) {
					String feeAdjust = reservation.calculateFeeAdjustment(driver, feeAmt, feeAmountOTAs, isFeeAdd,
							isInncenterFeeCreate);
					reservation.verifyFolioLineItem(driver, date, "Fee Adjustment", "Fee Adjustment", feeAdjust,
							testSteps);
				}
				testSteps.add("====================Verify Payment Scheduled on Folio Screen======================");
				String desc = "Name: " + nameONCard;
				app_logs.info(desc);
				reservation.verifyFolioLineItem(driver, startDate1, "MC", desc, paymentSheduleItem, testSteps);

				/*Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify the applies per night when value set as percent, not set as VAT");
				testSteps.add("Verify the applies per night when value set as percent, not set as VAT"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802296' target='_blank'>"
					+ "Click here to open TestRail: 802296</a><br>");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
				
				Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify when we have tax setup as flat tax for room charge per night");
				testSteps.add("Verify when we have tax setup as flat tax for room charge per night"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804336' target='_blank'>"
					+ "Click here to open TestRail: 804336</a><br>");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/	
				navigation.navigateToSetupFromReservationPage(driver, testSteps);
				navigation.clickTaxesAndFees(driver);
				testSteps.add("Click Tax and Fee");
				app_logs.info("Click Tax and Fee");

				if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
					testSteps.add("====================Delete Tax======================");
					for (int i = 0; i < taxname.size(); i++) {
						taxFee.deleteTaxAndFee(driver, taxname.get(i), testSteps);
					}
				}
				if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
					testSteps.add("====================Delete Fee======================");
					for (int i = 0; i < feename.size(); i++) {
						taxFee.deleteTaxAndFee(driver, feename.get(i), testSteps);
					}
				}

			
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			}
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify tax on vrbo");
			}
			
		} catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Request Verification UI Part",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Request Verification UI Part", testName,
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

		return Utility.getData("BookingRequestMultipleTax", otaexcel);

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
