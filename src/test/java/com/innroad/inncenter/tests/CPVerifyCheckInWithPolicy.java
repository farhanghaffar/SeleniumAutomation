package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyCheckInWithPolicy extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	String abbri = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null, getAmount = null,
			roomClassNames = null;
	NightlyRate nightlyRate = new NightlyRate();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	Policies policies = new Policies();
	Folio folio= new Folio();
	String testName = null;
	ArrayList<String> comments = new ArrayList<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyCheckInWithPolicy(String testCaseID,String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String checkInPolicyName, String typeOfFees, String percentage, String chargesTypes,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String noOfPolicy) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		String getcardNo = null, roomCharge = null, highestPolicyName = null, highestPolicyValue = null, roomChargeAmount = null;
		if (noOfPolicy.equalsIgnoreCase("Single")) {
			test_name = "CPVerifyCheckInWithSinglePolicy";
		} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
			test_name = "CPVerifyCheckInWithMultiplePolicy";
		}
		test_description = "Verify CheckIn Policies <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848442' target='_blank'>"
				+ "Click here to open TestRail: 848442</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848444' target='_blank'>"
				+ "Click here to open TestRail: 848444</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848215' target='_blank'>"
				+ "Click here to open TestRail: 848215</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848445' target='_blank'>"
				+ "Click here to open TestRail: 848445</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848421' target='_blank'>"
				+ "Click here to open TestRail: 848421</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848424' target='_blank'>"
				+ "Click here to open TestRail: 848424</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848509' target='_blank'>"
				+ "Click here to open TestRail: 848509</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848560' target='_blank'>"
				+ "Click here to open TestRail: 848560</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848651' target='_blank'>"
				+ "Click here to open TestRail: 848651</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/854563' target='_blank'>"
				+ "Click here to open TestRail: 854563</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848418' target='_blank'>"
				+ "Click here to open TestRail: 848418</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848772' target='_blank'>"
				+ "Click here to open TestRail: 848772</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> roomNumber = new ArrayList<String>();
		List<String> roomNumbers = new ArrayList<String>();
		List<String> roomCharges = new ArrayList<String>();
		Create_Reservation reservationPage = new Create_Reservation();
		RatesGrid rateGrid = new RatesGrid();
		HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
		HashMap<String, String> getCheckinCheckoutDate = new HashMap<String, String>();
		ArrayList<String> policiesNames = new ArrayList<String>();
		ArrayList<String> policiesPercentage = new ArrayList<String>();
		ArrayList<String> roomClassesNames = new ArrayList<String>();
		ArrayList<String> roomClassOneRoomNo= new ArrayList<String>();
		ArrayList<String> roomClassTwoRoomNo= new ArrayList<String>();
		ArrayList<String> roomClassOneRoomNos= new ArrayList<String>();
		ArrayList<String> roomClassTwoRoomNos= new ArrayList<String>();
		ArrayList<String> roomClassfinalRoomNos= new ArrayList<String>();
		HashMap<String, ArrayList<String>> roomsMap1= new HashMap<String, ArrayList<String>>();
		
		
		// Login
		try {
			roomClassNames=null;
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			} else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");	
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}
		// Create Room Class
		try {
			testSteps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			testSteps.add("Navigated to Room Class");
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
				newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults,
						maxPersopns, roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
				testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				ArrayList<String> roomClassAre = Utility.splitInputData(roomClassName);
				ArrayList<String> rooms= new ArrayList<String> ();
				for (int i = 0; i < roomClassAre.size(); i++) {
					roomClassNames = roomClassAre.get(i) + Utility.threeDigitgenerateRandomString();
					 rooms=newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults,
							maxPersopns, roomQuantity);
					newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
					roomClassesNames.add(roomClassNames);
					roomClassfinalRoomNos.addAll(rooms);

					
				}
				for(int i=0;i<4;i++) {
				roomClassOneRoomNo.add(roomClassfinalRoomNos.get(i));
				}
				for(int i=4;i<8;i++) {
					roomClassTwoRoomNo.add(roomClassfinalRoomNos.get(i));
				}
				app_logs.info(roomClassOneRoomNo);
				app_logs.info(roomClassTwoRoomNo);								
				roomClassNames = roomClassesNames.get(0) + "|" + roomClassesNames.get(1);
				testSteps.add("Room Class Created: <b>" + roomClassesNames + "</>");
				app_logs.info("Room Class Created: " + roomClassesNames);
				app_logs.info("Room Class Created: " + roomClassNames);
			}

		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			HashMap<String, String> policiesHashmap = new HashMap<String, String>();
			testSteps.add("<b>======Start Creating Policy ======</b>");
			navigation.inventoryFromRoomClass(driver, testSteps);
			navigation.policies(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				checkInPolicyName = checkInPolicyName + Utility.generateRandomStringWithoutNumbers();
				polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Check-in", "", "",
						checkInPolicyName, "", typeOfFees, percentage, chargesTypes, "", "", "No", "");
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				ArrayList<String> policiesAre = Utility.splitInputData(checkInPolicyName);
				ArrayList<String> policiesPercentageAre = Utility.splitInputData(percentage);
				for (int i = 0; i < policiesAre.size(); i++) {
					String policy = policiesAre.get(i) + Utility.generateRandomStringWithoutNumbers();
					String percentageOfPolicy = policiesPercentageAre.get(i);
					policiesNames.add(policy);
					policiesPercentage.add(percentageOfPolicy);
					polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Check-in", "", "", policy, "",
							typeOfFees, percentageOfPolicy, chargesTypes, "", "", "No", "");
					policiesHashmap.put(policy, percentageOfPolicy);
				}
				checkInPolicyName = policiesNames.get(0) + "|" + policiesNames.get(1);
				String value = null;
				value = Utility.getMaxValuefromHashmap(policiesHashmap);
				highestPolicyName = Utility.getKeyfromHashmap(policiesHashmap, value);
				highestPolicyValue = value;
				app_logs.info(highestPolicyName + " -- " + highestPolicyValue);
			}
			app_logs.info(polictiesNames);
			app_logs.info(policiesNames);
			app_logs.info(policiesPercentage);
			app_logs.info(policiesHashmap);
			app_logs.info(checkInPolicyName);

		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
					test_catagory, testSteps);
		}
		// Create Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				ArrayList<String> rateplanes = Utility.splitInputData(ratePlanName);
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplanes.get(0), datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
			}
			Utility.refreshPage(driver);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			nightlyRate.clickSeasonPolicies(driver, testSteps);
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				nightlyRate.selectPolicy(driver, "Check-in", checkInPolicyName, true, testSteps);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				ArrayList<String> roomClassList = Utility.splitInputData(roomClassNames);
				ArrayList<String> policiesList = Utility.splitInputData(checkInPolicyName);
				HashMap<String, String> policiesListOne = new HashMap<String, String>();
				HashMap<String, String> policiesListTwo = new HashMap<String, String>();
				HashMap<String, HashMap<String, String>> roomClassPolicy = new HashMap<String, HashMap<String, String>>();
				policiesListOne.put("Check-in", policiesList.get(0));
				policiesListTwo.put("Check-in", policiesList.get(1));
				roomClassPolicy.put(roomClassList.get(0), policiesListOne);
				roomClassPolicy.put(roomClassList.get(1), policiesListTwo);
				nightlyRate.selectPolicyAsPerRoomClass(driver, roomClassNames, roomClassPolicy, testSteps);
			}
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		// Create Reservation
		try {
			testSteps.add("<b>======Start Creating Reservation ======</b>");
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
				guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
				confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adults, children,
						ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber,
						nameOnCard, marketSegment, referral, roomClassNames, true, false);
				testSteps.add("Reservation Created: <b>" + confirmationNo + "</>");
				app_logs.info("Reservation Created: " + confirmationNo);
				app_logs.info(roomNumber);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				confirmationNo = reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, adults,
						children, ratePlanName, roomClassNames, salutation, guestFirstName, guestLastName, "No",
						roomNumber, paymentType, cardNumber, nameOnCard, referral, false, testSteps);
			}
			roomNumbers = reservation.getStayInfoRoomNo(driver, testSteps);
			app_logs.info(roomNumbers);
			getCheckinCheckoutDate = reservation.getStayInfoCheckInCheckOutDate(driver);
			app_logs.info(getCheckinCheckoutDate);
			roomCharges = reservation.getStayInfoRoomCharges(driver, testSteps);
			roomCharge = roomCharges.get(0).replace("$", "");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Verify Policy on Detail Page ======</b>");
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				reservation.verifyCheckInPolicy(driver, testSteps, checkInPolicyName, "");
				testSteps.add("<b>======Start CheckIn ======</b>");
				reservation.clickCheckInButton(driver, testSteps);

			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				reservation.verifyCheckInPolicy(driver, testSteps, highestPolicyName, "");
				testSteps.add("<b>======Start CheckIn ======</b>");
				reservation.verifyStayInforCheckInDate(driver, testSteps, checkInDate);
				reservation.verifyCheckInAllButton(driver, testSteps);
				
			/*	testSteps.add("Verify the Functionality of CHECK IN ALL button in case of same check In Date"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848412' target='_blank'>"
						+ "Click here to open TestRail: C848412</a><br>");
				Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify the Functionality of CHECK IN ALL button in case of same check In Date");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4),
						Utility.comments.get(4), TestCore.TestRail_AssignToID);
				*/
				reservation.click_Folio(driver, testSteps);
				for (int i = 0; i < roomClassesNames.size(); i++) {
					reservation.click_FolioDetail_DropDownBox(driver, testSteps);
					reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(i),
							roomNumbers.get(i));
					folio.verifyPostPendingStatus(driver, testSteps, datesRangeList.get(0), "Pending");
				}
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(0), roomNumbers.get(0));	
				reservation.click_DeatilsTab(driver, testSteps);
				reservation.clickCheckInAllButton(driver, testSteps);
			}
			reservation.verifySpinerLoading(driver);
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				testSteps.add("<b>======Verify CheckIn Popup Detail======</b>");
				String guestName = "" + salutation + " " + guestFirstName + " " + guestLastName + "";
				reservation.verifyReservationPopWindow(driver, "Check In", guestName, "RESERVED", confirmationNo,
						testSteps, "");
				reservation.verifyGuestContactInfo(driver, testSteps, salutation, guestFirstName, guestLastName, "",
						"");
				testSteps.add("<b>======CheckIn  Window Verifying STAY INFO======</b>");
				reservationPage.verifyCheckInStayInfoSingleReservation(driver, checkInDate, checkOutDate, adults,
						children, roomClassNames, ratePlanName, Utility.convertDecimalFormat(roomCharge), testSteps);
				
				/*testSteps.add("Verify that when user click on check-in button"
						+ " \"Check-in\" pop-up model should open & same guest details should appear on pop-up modal"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848442' target='_blank'>"
						+ "Click here to open TestRail: C848442</a><br>");
				
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify that when user click on"
						+ " check-in button \"Check-in\" pop-up model should open & same guest details should appear on pop-up modal");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
						Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
				comments.add("Verify that when user click on check-in button Check-in pop-up model should open & same guest details should appear on pop-up modal");

			}
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			if (noOfPolicy.equalsIgnoreCase("Multiple"))
			{
				reservation.selectRoomAtPaymentCheckInPopupMRB(driver, testSteps, roomClassOneRoomNo.get(1), roomClassesNames.get(0));
				reservation.selectRoomAtPaymentCheckInPopupMRB(driver, testSteps, roomClassTwoRoomNo.get(1), roomClassesNames.get(1));
			
				/*testSteps.add("Verify check in functionality by changing room in the check in pop-up for a guest in  MRB reservation"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848424' target='_blank'>"
					+ "Click here to open TestRail: 848424</a><br>");
				Utility.testCasePass(Utility.statusCode, 6, Utility.comments, "Verify check in functionality by changing room in the check in pop-up for a guest in  MRB reservation");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);*/
				comments.add("Verify check in functionality by changing room in the check in pop-up for a guest in  MRB reservation");
			}
			Wait.wait5Second();
			reservation.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				reservation.verifyReservationPopWindowPolicyName(driver, testSteps, checkInPolicyName, "Check In");
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				Wait.wait60Second();
				reservation.verifyReservationPopWindowPolicyName(driver, testSteps, highestPolicyName, "Check In");
				/*testSteps.add("Verify capture checkin policy while checkin the reservation"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848651' target='_blank'>"
						+ "Click here to open TestRail: 848651</a><br>");
				Utility.testCasePass(Utility.statusCode, 9, Utility.comments, "Verify capture checkin policy while checkin the reservation");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(9), Utility.statusCode.get(9),
						Utility.comments.get(9), TestCore.TestRail_AssignToID);*/
				comments.add("Verify capture checkin policy while checkin the reservation");
		
			}
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			reservation.clickCloseButtonOfCheckInSuccessfully(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			
			if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				reservation.verifySpinerLoading(driver);
				roomNumbers.clear();
				roomNumbers = reservation.getStayInfoRoomNo(driver, testSteps);
				app_logs.info(roomNumbers);
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Checkin", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkin", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======CheckIn Policy Amount verification======</b>");
			app_logs.info("======CheckIn Policy Amount verification======");
			
			double charge = 0.00;
			HashMap<String, String> getRoomCharge = new HashMap<String, String>();
			reservation.click_Folio(driver, testSteps);
			reservation.includeTaxesinLineItems(driver, testSteps, true);
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				getRoomCharge = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDate,
						checkOutDate);
				for (Map.Entry<String, String> entry : getRoomCharge.entrySet()) {
					charge = charge + Double.parseDouble(Utility.convertDecimalFormat(entry.getValue()));
				}
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				for (int i = 0; i < roomClassesNames.size(); i++) {
					reservation.click_FolioDetail_DropDownBox(driver, testSteps);
					reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(i),
							roomNumbers.get(i));				
					HashMap<String, String> getRoomChargeForMRB = new HashMap<String, String>();
					getRoomChargeForMRB = reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps,
							checkInDate, checkOutDate);
					app_logs.info(getRoomChargeForMRB);
					for (Map.Entry<String, String> entry : getRoomChargeForMRB.entrySet()) {
						charge = charge + Double.parseDouble(Utility.convertDecimalFormat(entry.getValue()));
					}
					folio.verifyPostPendingStatus(driver, testSteps, datesRangeList.get(0), "Post");
					folio.verifyPostPendingStatus(driver, testSteps, datesRangeList.get(1), "Pending");
				}
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(0), roomNumbers.get(0));
				
				/*testSteps.add("Verify current date line items status while checking in guest in MRB reservation"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848421' target='_blank'>"
						+ "Click here to open TestRail: C848421</a><br>");
				Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify current date line items status while checking in guest in MRB reservation");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5),
						Utility.comments.get(5), TestCore.TestRail_AssignToID);*/
				comments.add("Verify current date line items status while checking in guest in MRB reservation");
				}
			roomChargeAmount = String.valueOf(charge);
			app_logs.info(roomChargeAmount);
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				roomChargeAmount = reservation.calculationOfCheckInAmountToBePaidForRateV2(roomChargeAmount,
						percentage);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				roomChargeAmount = reservation.calculationOfCheckInAmountToBePaidForRateV2(roomChargeAmount,
						highestPolicyValue);
			}
			app_logs.info(roomChargeAmount);
			getcardNo = Utility.getCardNumberHidden(cardNumber);
			String description = getcardNo;
			reservation.verifyFolioLineItem(driver, checkInDate, paymentType, description, roomChargeAmount, testSteps);
//			reservation.verify_FolioPayment(driver, testSteps, roomChargeAmount);
			if (noOfPolicy.equalsIgnoreCase("Single")) {

				/*testSteps.add("Verify the folio when Check-in policy applied for individual check-in"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848444' target='_blank'>"
						+ "Click here to open TestRail: C848444</a><br>");
				Utility.testCasePass(Utility.statusCode, 1, Utility.comments,
						"Verify the folio when Check-in policy applied for individual check-in");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
						Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
				comments.add("Verify the folio when Check-in policy applied for individual check-in");
				//reservation.closeReservationTab(driver);
			}
			
			String paymentMethod = "MC-" + getcardNo + " " + Utility.expiryDate + "";
			testSteps.add("<b>======Start Verifying History======</b>");
			// Click History Tab
			reservation.click_History(driver, testSteps);
			reservation.verifyChekInReservationOnHistoryTab(driver, testSteps);
			
			/*testSteps.add("Verify that user is able to navigate to history tab and history should be present"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848772' target='_blank'>"
					+ "Click here to open TestRail: C848772</a><br>");
			Utility.testCasePass(Utility.statusCode, 12, Utility.comments, "Verify that user is able to navigate to history tab and history should be present");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(12), Utility.statusCode.get(12),
					Utility.comments.get(12), TestCore.TestRail_AssignToID);*/			
			comments.add("Verify that user is able to navigate to history tab and history should be present");
			
			reservation.verifyHistoryWithCapturedPayment(driver, testSteps,
			Utility.convertDecimalFormat(roomChargeAmount), paymentMethod, paymentType);
			if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				/*testSteps.add("Verify the behavior when more than one Check-in policy is applied at the time of check-in"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848445' target='_blank'>"
						+ "Click here to open TestRail: C848445</a><br>");
				Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify the behavior when more than one Check-in policy is applied at the time of check-in");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
						Utility.comments.get(3), TestCore.TestRail_AssignToID);				
		
				
				testSteps.add("Verify History tab after paying Room charges of any guest in a multi room reservation from Folio tab"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848509' target='_blank'>"
						+ "Click here to open TestRail: 848509</a><br>");
				Utility.testCasePass(Utility.statusCode, 7, Utility.comments, "Verify History tab after paying Room charges of any guest in a multi room reservation from Folio tab"
						+ "of any guest in a multi room reservation from Folio tab");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(7), Utility.statusCode.get(7),
						Utility.comments.get(7), TestCore.TestRail_AssignToID);

				testSteps.add("Verify Check in policy calculation for MRB when each room has different percentages of Policy Attributes"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848560' target='_blank'>"
						+ "Click here to open TestRail: 848560</a><br>");
				Utility.testCasePass(Utility.statusCode, 8, Utility.comments, "Verify Check in policy calculation for MRB when each room has different percentages of Policy Attributes");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(8), Utility.statusCode.get(8),
						Utility.comments.get(8), TestCore.TestRail_AssignToID);
				
				testSteps.add("Verify check-in functionality when there is check in policy different for each room class."
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/854563' target='_blank'>"
						+ "Click here to open TestRail: 854563</a><br>");
				Utility.testCasePass(Utility.statusCode, 10, Utility.comments, "Verify check-in functionality when there is check in policy different for each room class.");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(10), Utility.statusCode.get(10),
						Utility.comments.get(10), TestCore.TestRail_AssignToID);*/
				comments.add("Verify the behavior when more than one Check-in policy is applied at the time of check-in");
				comments.add("Verify History tab after paying Room charges of any guest in a multi room reservation from Folio tab");
				comments.add("Verify Check in policy calculation for MRB when each room has different percentages of Policy Attributes");
				comments.add("Verify check-in functionality when there is check in policy different for each room class.");
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Checkin policy verification folio line item", "Reservation",
					"Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkin policy verification folio line item", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		}
		try {
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				testSteps.add("<b>======Start Checkout Reservation======</b>");
				reservation.clickCheckOutButton(driver, testSteps);
				reservation.generatGuestReportToggle(driver, testSteps, "No");
				reservation.proceedToCheckOutPayment(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				reservation.clickLogORPayAuthorizedButton(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				reservation.verifyRollBackButton(driver, testSteps);
				ArrayList<String> steps = reservation.verifyStatusAfterReservation(driver, "DEPARTED");
				testSteps.addAll(steps);
				
				/*testSteps.add("To create assigned reservation from New Reservation tabÃ‚Â and verify Check in Checkout functionality."
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848215' target='_blank'>"
						+ "Click here to open TestRail: C848215</a><br>");
				Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "To create assigned reservation from New Reservation tabÃ‚Â and verify Check in Checkout functionality.");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
						Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
				comments.add("To create assigned reservation from New Reservation tabÃ‚Â and verify Check in Checkout functionality.");
	
			}			

		} catch (Exception e) {
			Utility.catchException(driver, e, "Checkout Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Checkout Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		try {
			if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				reservation.click_Folio(driver, testSteps);
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassesNames.get(0), roomNumbers.get(0));
				folio.openFolioLineItem(driver, getcardNo, testSteps);
				reservation.verifySpinerLoading(driver);
				folio.ClickOnAdvanceLedgerTab(driver, testSteps);
				String advanceDepositAmount=folio.getAdv_Dep_LedgeAmount(driver, testSteps);
				advanceDepositAmount=Utility.convertDecimalFormat(advanceDepositAmount);
				Utility.verifyText(advanceDepositAmount, Utility.convertDecimalFormat(roomChargeAmount), "Advance Payment for Reservation", testSteps, app_logs);
				
				/*testSteps.add("Verify advance deposit transactions when MRB reservation is checked in with check in policy"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848418' target='_blank'>"
						+ "Click here to open TestRail: 848418</a><br>");
				Utility.testCasePass(Utility.statusCode, 11, Utility.comments, "Verify advance deposit transactions when MRB reservation is checked in with check in policy");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(11), Utility.statusCode.get(11),
						Utility.comments.get(11), TestCore.TestRail_AssignToID);*/
				comments.add("Verify advance deposit transactions when MRB reservation is checked in with check in policy");
				
			}
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Checkin Policy");
			}		
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);

		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify advance deposit transactions for MRB Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify advance deposit transactions for MRB Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Delete Policy======</b>");
			navigation.inventory(driver);
			testSteps.add("Navigated to Inventory");
			navigation.policies(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
			ArrayList<String> policyDelete = new ArrayList<String>();
			if (noOfPolicy.equalsIgnoreCase("Single")) {
				policyDelete.add(checkInPolicyName);
				policies.deleteAllPolicies(driver, testSteps, policyDelete);
			} else if (noOfPolicy.equalsIgnoreCase("Multiple")) {
				policyDelete = Utility.splitInputData(checkInPolicyName);
				policies.deleteAllPolicies(driver, testSteps, policyDelete);
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyCheckInWithPolicy", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
