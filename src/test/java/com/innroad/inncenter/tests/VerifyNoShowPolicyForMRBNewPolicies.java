package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyNoShowPolicyForMRBNewPolicies extends TestCore {

	private WebDriver driver = null;
	ArrayList<String> test_catagory = new ArrayList<>();
	ArrayList<String> test_description = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> test_name = new ArrayList<>();
	ArrayList<String> totalNights = new ArrayList<>();
	ArrayList<String> roomBaseAmount = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();
	ArrayList<String> getRoomNumbers = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	Double depositAmount = 0.0;
	Double paidDeposit = 0.0;
	String testName = this.getClass().getSimpleName().trim();

	String roomClassNameWithoutNum, rateNameWithoutNum, policyNameWithoutNum, typeToValidate, policyDesc, policyText,
			taxes, reservation = null, status = null, tripTotal = null, roomNumber1 = null, tripRoomCharges, tripTaxes,
			tripPaid, tripBalance, nameOnCard, roomChargeAmount1 = null, roomChargeAmount2 = null, roomChargeAmount = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Policy")
	public void verifyNoShowPolicyForMRB(String TestCaseID, String ClientCode, String Username, String Password, String resType,
			String roomClassName, String ratePlan,
			String policyType, String policyName, String policyFeeType, String policyFee, String policyChargesType,
			String checkInDate, String checkOutDate, String accountType, String accountName, String paymentMethod,
			String cardNumber) throws Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		String testDescription = "Verify Cancellation policy for "+policyChargesType+" for MRB reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+TestCaseID+"' target='_blank'>"
				+ "Click here to open TestRail: "+TestCaseID+"</a><br/>";
		String testCatagory = "Policies";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName +" with "+policyChargesType+ " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}			
		}

		Policies policies = new Policies();
		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Login login = new Login();
		Account account = new Account();
		
		String roomClass1 = roomClassName.split("\\|")[0];
		String roomClass2 = roomClassName.split("\\|")[1];
		String ratePlan1 = ratePlan.split("\\|")[0];
		String ratePlan2 = ratePlan.split("\\|")[1];
		
		String adults = "2", children = "1";
		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String accountFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String accountLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String salutation = "Mr.";
		String address1 = Utility.generateRandomString();
		String address2 = Utility.generateRandomString();
		String address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String referral = "Walk In";
		String marketSegment = "Internet";
		String email = "innRoadTestEmail@innroad.com";
		String isGuesProfile = "No";
		String country = "United States", state = "Alaska";
		String expPolicyFee = null;
		
		HashMap<String, String> policyNames = new HashMap<>();
		HashMap<String, String> policyTextList = new HashMap<>();
		HashMap<String, String> policyFeeTypeList = new HashMap<>();
		HashMap<String, String> policyFeeList = new HashMap<>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			
			// Get CheckIn, CheckOut Date
			if (checkInDate.equalsIgnoreCase("NA") || checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("")) {

				if (!resType.equalsIgnoreCase("MRB1")) {
					checkInDate = Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern");
					checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					// Get CheckIN and Checkout Date
					if (checkInDate.equalsIgnoreCase("NA") || checkInDate.isEmpty()
							|| checkInDate.equalsIgnoreCase("")) {
						checkInDates.clear();
						checkOutDates.clear();
						for (int i = 1; i <= roomClassName.split("|").length; i++) {
							app_logs.info("Loop" + i);
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy", "US/Eastern"));
							checkOutDates.add(
									Utility.parseDate(Utility.getDatePast_FutureDate(1, "US/Eastern"), "MM/dd/yyyy", "dd/MM/yyyy"));
							app_logs.info(checkInDates);
							app_logs.info(checkOutDates);
						}
					} else {
						checkInDates = Utility.splitInputData(checkInDate);
						checkOutDates = Utility.splitInputData(checkOutDate);
					}
					app_logs.info(checkInDates);
					app_logs.info(checkOutDates);

					checkInDate = checkInDates.get(0);
					checkOutDate = checkOutDates.get(0);

					for (int i = 1; i < checkInDates.size(); i++) {
						checkInDate = checkInDate + "|" + checkInDates.get(i);
						checkOutDate = checkOutDate + "|" + checkOutDates.get(i);
					}
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
				}
			}
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			
			String randomNum = Utility.GenerateRandomNumber();
			policyNameWithoutNum = policyName;
			//policyName = policyName + randomNum;
			policyText = policyName + "_Text";
			policyDesc = policyName + "_Description";
			policyNames.put("policy1", policyName + Utility.GenerateRandomNumber());
			policyNames.put("policy2", policyName + Utility.GenerateRandomNumber());
			
			policyFeeTypeList.put("policy1", policyFeeType.split("\\|")[0]);
			policyFeeTypeList.put("policy2", policyFeeType.split("\\|")[1]);
			
			policyFeeList.put("policy1", policyFee.split("\\|")[0]);
			policyFeeList.put("policy2", policyFee.split("\\|")[1]);

			guestLastName = guestLastName + randomNum;
			nameOnCard = guestFirstName + " " + guestLastName;

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			for (int i = 0; i < checkInDates.size(); i++) {
				totalNights.add(Utility.differenceBetweenDates(checkInDates.get(i), checkOutDates.get(i)));
			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before executing test scripts", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before executing test scripts", testName,
						"Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			driver = getDriver();
			//login_CP(driver, test_steps);
//			login.login(driver, envURL, ClientCode, Username, Password);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to create a new room class", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to Login", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("========== Creating a new policy ==========");
			test_steps.add("========== Creating a new policy ==========");

			navigation.Inventory(driver, test_steps);
			navigation.policies(driver, test_steps);
			policies.deleteAllPoliciesNew(driver, policyType, test_steps);
			HashMap<String, ArrayList<String>> policyDetails = new HashMap<>();
			
			policyDetails = policies.createPolicies(driver, test_steps, "|", "", policyType, "", "", "",
					policyNames.get("policy1"), policyFeeTypeList.get("policy1"), policyFeeList.get("policy1"), policyChargesType, "", "", "No", "");
			policyTextList.put("policy1", policyDetails.get(policyNames.get("policy1")).toString().replace("[", "").replace("]", "").trim());
			Wait.wait3Second();
			
			policyDetails = policies.createPolicies(driver, test_steps, "|", "", policyType, "", "", "",
					policyNames.get("policy2"), policyFeeTypeList.get("policy2"), policyFeeList.get("policy2"), policyChargesType, "", "", "No", "");
			policyTextList.put("policy2", policyDetails.get(policyNames.get("policy2")).toString().replace("[", "").replace("]", "").trim());
						
			app_logs.info("Poliy details: "+policyTextList);
			reservationPage.navigateToReservationPage(driver);
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			
			rateGrid.selectRatePlan(driver, ratePlan1, test_steps);
			rateGrid.clickOnEditRatePlan(driver);
//			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
//			rateGrid.clickOnGivenPolicy(driver, policyType, true);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);
			nightlyRate.selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", "", false, test_steps);
			nightlyRate.selectPolicy(driver, policyType, policyNames.get("policy1"), true, test_steps);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			rateGrid.selectRatePlan(driver, ratePlan2, test_steps);
			rateGrid.clickOnEditRatePlan(driver);
//			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
//			rateGrid.clickOnGivenPolicy(driver, policyType, true);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);
			nightlyRate.selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", "", false, test_steps);
			nightlyRate.selectPolicy(driver, policyType, policyNames.get("policy2"), true, test_steps);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new policy", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new policy", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver, test_steps);
		try {
			try {
				navigation.Reservation_Backward_3(driver);				
			} catch (Exception e) {
				navigation.Reservation_Backward_1(driver);
			}
			if (Utility.validateString(accountType)) {
				accountName = "Test Policy";
				test_steps.add("<b>******************* CREATING RESERVATION FROM ACCOUNT **********************</b>");
				account.clickNewReservationFromAccount(driver, test_steps, accountType, accountName, marketSegment,
						referral, accountFirstName, accountLastName, phoneNumber, alternativePhone, address1, address2,
						address3, email, city, state, postalCode, null);
			}else {
				test_steps.add("========== Creating new Reservation ==========");
				reservationPage.click_NewReservation(driver, test_steps);
			}

			if (resType.equalsIgnoreCase("MRB")) {
				reservationPage.select_Dates(driver, test_steps, checkInDate+"|"+checkInDate, checkOutDate+"|"+checkOutDate, adults+"|"+adults, children+"|"+children, ratePlan+"|"+ratePlan, "", "");
				reservationPage.clickOnFindRooms(driver, test_steps);
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
						roomClass1);
				reservationPage.selectRoomToReserve(driver, test_steps, roomClass1, rooms.get(0));
				Wait.wait10Second();
				rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass2);
				reservationPage.selectRoomToReserve(driver, test_steps, roomClass2, rooms.get(1));
				reservationPage.click_Next(driver, test_steps);
				ArrayList<String> roomNumber = new ArrayList<>();
				reservationPage.enter_MRB_MailingAddress(driver, test_steps, salutation+"|"+salutation, guestFirstName+"|"+guestFirstName, guestLastName+"|"+guestLastName, phoneNumber+"|"+phoneNumber,
						alternativePhone, email, "", accountType, address1, address2, address3, city, country, state,
						postalCode, isGuesProfile, "", "", roomNumber);
			}else if (resType.equalsIgnoreCase("Single")) {
				reservationPage.select_CheckInDate(driver, test_steps, checkInDates.get(0));
				reservationPage.select_CheckoutDate(driver, test_steps, checkOutDates.get(0));
				reservationPage.enter_Adults(driver, test_steps, adults);
				reservationPage.enter_Children(driver, test_steps, children);
				reservationPage.select_Rateplan(driver, test_steps, ratePlan, "");
				reservationPage.clickOnFindRooms(driver, test_steps);
				reservationPage.selectRoom(driver, test_steps, roomClassName, Utility.RoomNo, "");
				if (Utility.validateString(accountType)) {
					reservationPage.clickYesOrNoOnPolicyChangePopup(driver, test_steps, "No");
				}
				depositAmount = reservationPage.deposit(driver, test_steps, "", "");
				reservationPage.clickNext(driver, test_steps);
				taxes = reservationPage.getTaxesFromTripSummary(driver);
				test_steps.add("Taxes and service charges captured during reservation creation is <b>" + taxes + "</b>");
				reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
						phoneNumber, alternativePhone, email, accountName, accountType, address1, address2, address3, city,
						country, state, postalCode, isGuesProfile);
			}
			
			reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard,
					cardExpDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", "", referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			status = reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			roomNumber1 = reservationPage.get_RoomNumber(driver, test_steps, "");
			roomNumber.add(roomNumber1);
			roomChargeAmount = reservationPage.get_TripSummaryRoomChargesWithoutCurrency(driver, test_steps).replace(".00", "");
			
			roomChargeAmount1 = reservationPage.getFristReservationRoomCharge(driver, test_steps).replace(".00", "").replaceAll("[$£ ]", "");
			roomChargeAmount2 = reservationPage.getSecondReservationRoomCharge(driver, test_steps).replace(".00", "").replaceAll("[$£ ]", "");
			
			app_logs.info("Room Charge 1: "+roomChargeAmount1);
			app_logs.info("Room Charge 2: "+roomChargeAmount2);
			tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps).replace(".00", "");
			
			test_steps.add("========== Displaying amount details on trip summary ========");
			test_steps.add("Total nights for reservation is captured as <b>"+totalNights.get(0)+" </b>based on "
					+ "input check-in/out dates provided for reservation");
			tripRoomCharges = reservationPage.getRoomChargeUnderTripSummary(driver, test_steps);
			tripTaxes = reservationPage.getTaxesFromTripSummary(driver);
			test_steps.add("Taxes and service charges captured at trip summary is : <b>"+tripTaxes+"</b>");			
			tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
			tripPaid = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);
			tripBalance = reservationPage.get_TripSummaryBalance_Amount(driver, test_steps);
			if (policyChargesType.equalsIgnoreCase("room charges")) {
				roomBaseAmount.add(roomChargeAmount1);
			}else if (policyChargesType.equalsIgnoreCase("total charges")) {
				roomBaseAmount.add(tripTotal);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {			
			if (policyFeeTypeList.get("policy1").equalsIgnoreCase(policyFeeTypeList.get("policy2"))) {
				if (policyFeeType.split("\\|").length > 1) {
					String[] fees = policyFee.split("\\|");
					ArrayList<Double> feeDouble = new ArrayList<>();
					for (int i = 0; i < fees.length; i++) {
						feeDouble.add(Double.parseDouble(fees[i]));
					}
					double max = 0.0;
					int count = 0;
					for (int i = 1; i < feeDouble.size(); i++) {
						if (feeDouble.get(i-1) > feeDouble.get(i)) {
							max = feeDouble.get(i-1);
							count = i-1;
						}else {
							max = feeDouble.get(i);
							count = i;
						}
					}
					app_logs.info("Fee double: "+max);
					app_logs.info("Count: "+count);
					policyName = policyNames.get("policy"+(count+1));
					policyText = policyTextList.get("policy"+(count+1));
					policyFee = policyFeeList.get("policy"+(count+1));

				}
				
				if (policyFeeTypeList.get("policy1").equalsIgnoreCase("percent of stay")) {
					if (policyChargesType.equalsIgnoreCase("room charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(roomChargeAmount, policyFee).replace(".00", "").trim();
					}else if (policyChargesType.equalsIgnoreCase("total charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(tripTotal, policyFee).replace(".00", "").trim();
					}
				}else {
					expPolicyFee = String.format("%.2f", Double.parseDouble(policyFee) * 2).replace(".00", "").trim();
				}
			}else {
				double fee1 = 0.0, fee2 = 0.0;
				
				if (policyFeeTypeList.get("policy1").equalsIgnoreCase("percent of stay")) {
					if (policyChargesType.equalsIgnoreCase("room charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(roomChargeAmount, policyFeeList.get("policy1")).replace(".00", "").trim();
					}else if (policyChargesType.equalsIgnoreCase("total charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(tripTotal, policyFeeList.get("policy1")).replace(".00", "").trim();
					}
				}else {
					expPolicyFee = String.format("%.2f", Double.parseDouble(policyFeeList.get("policy1")) * 2).replace(".00", "").trim();
				}		
				fee1 = Double.parseDouble(expPolicyFee);
				
				if (policyFeeTypeList.get("policy2").equalsIgnoreCase("percent of stay")) {
					//policyFee = Double.toString((( Double.parseDouble(roomChargeAmount) * Double.parseDouble(policyFee) )/100))
					if (policyChargesType.equalsIgnoreCase("room charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(roomChargeAmount, policyFeeList.get("policy2")).replace(".00", "").trim();
					}else if (policyChargesType.equalsIgnoreCase("total charges")) {
						expPolicyFee = reservationPage.calculateDepositePercenatge(tripTotal, policyFeeList.get("policy2")).replace(".00", "").trim();
					}
				}else {
					expPolicyFee = String.format("%.2f", Double.parseDouble(policyFeeList.get("policy2")) * 2).replace(".00", "").trim();
				}
				fee2 = Double.parseDouble(expPolicyFee);
				
				if (fee1 > fee2) {
					expPolicyFee = String.format("%.2f", fee1).replace(".00", "").trim();
					policyName = policyNames.get("policy1");
					policyText = policyTextList.get("policy1");
				}else {
					expPolicyFee = String.format("%.2f", fee2).replace(".00", "").trim();
					policyName = policyNames.get("policy2");
					policyText = policyTextList.get("policy2");
				}				
			}
			
			app_logs.info("Details: "+policyFee);
			app_logs.info("Details: "+policyChargesType);
			app_logs.info("policyName: "+policyName);
			app_logs.info("policyText: "+policyText);
			app_logs.info("Expected fee: "+expPolicyFee);
						
			test_steps.add(
					"===================== Verifying associated No Show policy details at Policies And Disclaimers tab =====================");
			reservationPage.verifyNoShowPolicy(driver, test_steps, policyName, policyText);
			test_steps.add("===================== Make  Reservation as No Show =====================");
			reservationPage.makeReservationNoShow(driver, test_steps);
			test_steps.add("===================== verifying NoShowReservation PopUp =====================");
			reservationPage.verifyNoShowReservationPopUp(driver, policyName, test_steps);
			test_steps.add("===================== Verifying No Show payment popup =====================");
			ArrayList<String> noShowPaymentWithTaxes = new ArrayList<>();

			reservationPage.verifyNoShowPaymentPopUp(driver, "No Show Payment", expPolicyFee, cardExpDate,
					nameOnCard, "XXXX " + cardNumber.substring(cardNumber.length() - 4), paymentMethod, "Capture",
					test_steps);
			
			String abc = paymentMethod + " - " + Utility.getCardNumberHidden(cardNumber) + " (" + nameOnCard
					+ ") (Exp. " + cardExpDate + ")";
			test_steps.add(
					"===================== Verifying guest and payment details in Noshow payment success popup =====================");
			reservationPage.verifyNoShowPaymentSuccessPopupVerify(driver, noShowPaymentWithTaxes, "No Show Successful",
					"Processed", abc, "Capture", "0.00");
			reservationPage.clickCloseButtonOfNoShowSuccessfully(driver, test_steps);
			reservationPage.verifyReservationStatusStatus(driver, test_steps, "No Show");
			
			comments = "Verify No Show policy calculation for MRB when each room has different policy fee";
			statusCode = new ArrayList<String>();
			caseId = new ArrayList<String>();
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("1");
				
			}
			closeDriver();
		}catch (Exception e) {
			e.printStackTrace();
			app_logs.info(e.toString());
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to cancel reservation and verify associated cancellation and other "
						+ "payment details", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to cancel reservation and verify associated cancellation and other "  
					+"payment details", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
			
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("NoShowPolicyMRBNewPolicies", HS_EXCEL);
	}

//	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
