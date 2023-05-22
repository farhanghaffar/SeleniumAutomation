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
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import io.restassured.response.Response;

public class VerifyVrboReservationHavingChildAndAdultFee extends TestCore {
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
	Admin admin = new Admin();
	Response response;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "Vrbo")
	public void bookingRequest(String version, String roomClassName, String channel, String message, String title,
			String firstName, String lastName, String email, String phoneNo, String address1, String address2,
			String address3, String address4, String country, String adult, String child, String pet,
			String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, String cardCode,
			String cardType, String masterListingChannel, String clientIPAddress, String trackingUuid,
			String numberToken, String beginDate, String noOfNights, String originationDate, 
			String chargeName,String roomchargeAmount, String IsTaxAdd, String TaxNameInXml, String TaxAmountInXml, 
			String setTaxRate,String taxExternalId, String isFeeAdd, String feeNameInXml, String feeAmount, 
			String setFeeRate,String feeExternalId, String paymentSheduleItem, String isExtraAdultInXml, 
			String extraAdultAmount,String isExtraChildInXml, String extraChildAmount, String isInncenterTaxCreate, 
			String inTaxName,String type, String categoryTax, String intaxValues, String ledgerAccount, String isInncenterFeeCreate,
			String infeeName, String feeType, String infeeValues, String categoryFee) {
		String testName = null, startDate = null, endDate = null, originationDates = null, confirmationNo = null,
				expiryDate = null, startDate1 = null, endDate1 = null,advertiserId=null,classId=null,currency=null;

		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValue = Utility.convertTokenToArrayList(intaxValues, Utility.DELIMS);
		ArrayList<String> categoryTaxs = Utility.convertTokenToArrayList(categoryTax, Utility.DELIMS);
		ArrayList<String> taxesAmountInReq = Utility.convertTokenToArrayList(TaxAmountInXml, Utility.DELIMS);
		ArrayList<String> feeAmountOTAs = Utility.convertTokenToArrayList(feeAmount, Utility.DELIMS);
		ArrayList<String> catgoriesFee = Utility.convertTokenToArrayList(categoryFee, Utility.DELIMS);
		ArrayList<String> feeCategoriesValue = Utility.convertTokenToArrayList(infeeValues, Utility.DELIMS);
		ArrayList<String> feetype = Utility.convertTokenToArrayList(feeType, Utility.DELIMS);
		ArrayList<String> taxesAmountXML = Utility.convertTokenToArrayList(TaxAmountInXml, Utility.DELIMS);
		data.clear();
		try {
			if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")
					&& taxesAmountXML.size() == 1) {
				testName = "VerifyVrboReservation " + "AddingFeeAndTaxesIncludingExtraChildAndAdult";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")
					&& taxesAmountXML.size() > 1) {
				testName = "VerifyVrboReservation " + "AddingFeeAndMultipleTaxesIncludingExtraChildAndAdult";
			} else if (!isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingFeeAndTaxesIncludingExtraChild";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && !isExtraChildInXml.equalsIgnoreCase("Yes")
					&& IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingFeeAndTaxesIncludingExtraAdult";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& !IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingFeeIncludingExtraAdultAndChild";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& IsTaxAdd.equalsIgnoreCase("yes") && !isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingTaxesIncludingExtraAdultAndChild";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& !IsTaxAdd.equalsIgnoreCase("yes") && !isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingOnlyExtraAdultAndChild";
			} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && !isExtraChildInXml.equalsIgnoreCase("Yes")
					&& !IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingFeeAndIncludingExtraAdult";
			} else if (!isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")
					&& !IsTaxAdd.equalsIgnoreCase("yes") && isFeeAdd.equalsIgnoreCase("Yes")) {
				testName = "VerifyVrboReservation " + "AddingFeeAndIncludingExtraChild";
			}

			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");

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

			navigation.clickTaxesAndFeesAfterRoomClass(driver);
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
					taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
							taxType.get(i), categoryTaxs.get(i), intaxValue.get(i), "", ledgerAccount, false, "", "",
							"", testSteps);
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
					testSteps.add("===========================================================");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver,e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver,e, test_description, test_catagory, "Get Tax and Fee or Create Tax and fee", testName,
					test_description, test_catagory, testSteps);
		}
		try {

			testSteps.add("====================Verify API End Point======================");

			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDate("dd/MM/yyyy");
				if(!Utility.validateString(noOfNights)) {
			    endDate1= Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
				}else
				{
				endDate1= Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)), "MM/dd/yyyy", "dd/MM/yyyy");
				}
			}else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1=checkInDates.get(0);
				if(!Utility.validateString(noOfNights)) {
					endDate1=  Utility.addDays(startDate1, 2);
				}else {
					
					endDate1=  Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			}
				startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
				endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			
				if (!(Utility.validateInput(originationDate))) {
					originationDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss");
				}else {
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

			File requestBody = bookingRequest.createBookingRequest(testName,version,advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address2, address4, country, adult, child,
					startDate, endDate, originationDate, currency,chargeName, startDate, paymentType, postalCode, cvv,
					expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath, IsTaxAdd, TaxNameInXml,
					TaxAmountInXml, setTaxRate, isExtraAdultInXml, extraAdultAmount, isExtraChildInXml,
					extraChildAmount, isFeeAdd, feeNameInXml, feeAmount, setFeeRate, taxExternalId, feeExternalId,
					roomchargeAmount, paymentSheduleItem, masterListingChannel, clientIPAddress, trackingUuid,
					numberToken);
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
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath,testName,"bookingResponse");
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
				File responseBody = bookingRequest.getResponseXmlFile(data.get("BODY"), schemaFilesPath,testName,"bookingResponse");
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

			} else {
				app_logs.info("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
				testSteps.add("Failed Get Status Code: <b>" + data.get("StatusCode") + "</b>");
			}

		} catch (Exception e) {
			Utility.catchException(driver,e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver,e, test_description, test_catagory, "Booking Request", testName, test_description,
					test_catagory, testSteps);
		}

		try {
			if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE") && !data.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("====================Verify Response Schema======================");
				if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE")
						&& !data.get("BODY").contains("SERVER_ERROR")) {
					String roomClassLevelPolicy = "Moderate";
					vrboObject.validateBookingResponse(response, advertiserId, classId, classId, confirmationNo, adult,
							child, pet, startDate, endDate, IsTaxAdd, TaxNameInXml, TaxAmountInXml, setTaxRate,
							isExtraAdultInXml, extraAdultAmount, isExtraChildInXml, extraChildAmount, isFeeAdd,
							feeNameInXml, feeAmount, setFeeRate, taxExternalId, feeExternalId, roomchargeAmount,
							paymentSheduleItem, roomClassLevelPolicy, originationDate, testSteps);
				}
			}

		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Request Verified Task On UI",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request  Verified Task on UI",
					testName, test_description, test_catagory, testSteps);
		}
		try {
			if (!data.get("BODY").contains("PROPERTY_NOT_AVAILABLE") && !data.get("BODY").contains("SERVER_ERROR")) {

				navigation.reservationBackward3(driver);
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
				datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
				app_logs.info(roomchargeAmount);
				int days = datesRangeList.size();
				String oneDayRoomCharge = reservation.perDayRoomChargeIncludingExtraAdultChild(isExtraAdultInXml,
						isExtraChildInXml, roomchargeAmount, extraAdultAmount, extraChildAmount, days);

				HashMap<String, String> convertedValFromPercentage = reservation.calculateTaxes(oneDayRoomCharge,
						updatedTaxName, intaxValue, categoryTaxs, taxesAmountInReq, days);
				reservation.click_Folio(driver, testSteps);
				testSteps.add("=====================VERIFY TAX ADJUSTMENT===================");
				app_logs.info("=====================VERIFY TAX ADJUSTMENT===================");
				reservation.verifyVrBoTaxAdjustMent(driver, testSteps, ledgerAccount, convertedValFromPercentage,
						updatedTaxName, taxesAmountXML, datesRangeList, IsTaxAdd);

				ArrayList<String> feeAmt = new ArrayList<String>();

				for (int i = 0; i < datesRangeList.size(); i++) {
					testSteps.add("====================Verify Room Charges on Folio Screen for Date"
							+ datesRangeList.get(i) + "======================");
					reservation.includeTaxesinLineItems(driver, testSteps, false);
					reservation.verifyFolioLineItem(driver, datesRangeList.get(i), chargeName, "HomeAway Rate",
							oneDayRoomCharge, testSteps);

					if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
						testSteps.add("====================Verify Fee Adjustment on Folio Screen "
								+ datesRangeList.get(i) + "======================");

						for (int j = 0; j < updatedFeeName.size(); j++) {

							feeAmt.add(reservation.calculateAdjustmentFee(driver, catgoriesFee.get(j), oneDayRoomCharge,
									feeCategoriesValue.get(j)));
							reservation.includeTaxesinLineItems(driver, testSteps, false);
							reservation.verifyFolioLineItem(driver, datesRangeList.get(i), feetype.get(j),
									updatedFeeName.get(j), feeAmt.get(j), testSteps);
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

			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver,e, test_description, test_catagory, "Booking Request Verification UI Part",
					testName, test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver,e, test_description, test_catagory, "Booking Request Verification UI Part", testName,
					test_description, test_catagory, testSteps);
		}
	}

	/*@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyVrboReservationHavingChil", otaexcel);
	}
*/
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequestCommonData", otaexcel);
	}
	
	@DataProvider
	public Object[][] getDataOne() {

		return Utility.getData("VerifyVrboReservationHavingChil", otaexcel);

	}

		@DataProvider
		public Object[][] getFinalData()
		{
			 return Utility.combine(getData(),  getDataOne());
		} 
		
		
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
