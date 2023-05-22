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
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyBookingRequestNoAvailability extends TestCore {
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	Navigation navigation = new Navigation();
	ArrayList<String> getTestSteps = new ArrayList<>();
	RatesGrid ratesGrid = new RatesGrid();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	VrboObjects vrboObject = new VrboObjects();
	HashMap<String, String> response = new HashMap<String, String>();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	ReservationSearch reservationSearch= new ReservationSearch();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Vrbo")
	public void bookingRequestNoAvailability(String beginDate, String noOfNights, String originationDate,
			String className, String channel, String message, String title, String firstName, String lastName,
			String email, String phoneNo, String address1, String address3, String address4, String country,
			String adult, String child, String externalId, String feeType, String chargeName, String amount,
			String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo,
			String cardCode, String cardType, String taskFors, String taskCategories, String taskTypes,
			String taskDetail, String taskRemark, String taskAssignees, String status, String state, String exCountry) {
		String testName = null, startDate = null, endDate = null, availibilityCount = null, expiryDate = null,
				error = null,classId = null, currency = null, advertiserId = null,
				startDate1 = null, endDate1 = null;
		try {
			testName = "Verify Booking Request No Availability ";
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}

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
			roomClassIds = roomClass.getRoomClassDetails(driver, className, testSteps);
			classId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);

			navigation.inventoryV2(driver);
			testSteps.add("Navigated to Inventory");
			navigation.ratesGrid(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnAvailability(driver);
			testSteps.addAll(getTestSteps);
			ratesGrid.expandRoomClass(driver, testSteps, className);
			int dateIndex = ratesGrid.getHeadingDateIndex(driver, startDate1, "dd/MM/yyyy");
			availibilityCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			app_logs.info(availibilityCount);
			testSteps.add("Availibility Count:-" + availibilityCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Check Availability", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Check Availability", testName,
					test_description, test_catagory, testSteps);
		}

		try {

			testSteps.add("====================Verify API End Point if No Availability======================");
			if (Integer.parseInt(availibilityCount) == 0) {
				expiryDate = Utility.getFutureMonthAndYearForMasterCard();
				File requestBody = bookingRequest.createBasicBookingRequest(testName,advertiserId, classId, channel, message,
						title, firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child,
						startDate, endDate, originationDate, externalId, feeType, chargeName, amount, productID,
						startDate, paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType,
						schemaFilesPath, currency);

				String myRequest = bookingRequest.getFileContent(requestBody);
				app_logs.info("Request--" + myRequest);
				response = vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"),
						VrboEndPoints.bookingRequest, myRequest);
				if (!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
					testSteps.add("API End Point-" + response.get("APIURL"));
					testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
							+ "Click here to open : URL</a><br>");
					vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
					File responseBody = bookingRequest.getResponseXmlFile(response.get("BODY"), schemaFilesPath,
							testName,"bookingResponse");
					testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
							+ "Click here to open : URL</a><br>");
					error = bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
					app_logs.info("Error Is -" + error);
					String errorType = "PROPERTY_NOT_AVAILABLE";
					Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error",
							testSteps, app_logs);
				} else {
					app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
					testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
				}
			} else {
				app_logs.info("Availibility Count found so unable to verify booking request without availibility");
				testSteps.add("Availibility Count found so unable to verify booking request without availibility");
			}

			
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Request Availibility", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request Availibility", testName,
					test_description, test_catagory, testSteps);
		}
		
		try {
			app_logs.info("====================Bulk Cancel Reservation======================");	
			navigation.navReservationFromRateGrid(driver);
			reservationSearch.clickAdvanceReservationButton(driver);
			reservationSearch.advanceSearchWithRoomClass(driver, className);
			reservationSearch.clickOnSearchButton(driver);
			Wait.waitforPageLoad(30, driver);
			reservationSearch.bulkActionCancellation(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Booking Request Availibility", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request Availibility", testName,
					test_description, test_catagory, testSteps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequest", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
