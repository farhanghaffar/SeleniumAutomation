package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

public class VerifyCheckInPolicyNewPolicies extends TestCore {

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
			tripPaid, tripBalance, nameOnCard, roomChargeAmount, guestFullName, authtorizeIcon, authorizeIconSrc;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Policy")
	public void verifyCancellationPolicy(String TestCaseID, String Scenario, String ClientCode, String Username, String Password, String resType,
			String roomClassName, String ratePlan,
			String policyType, String policyName, String policyFeeType, String policyFee, String policyChargesType,
			String checkInDate, String checkOutDate, String accountType, String accountName, String paymentMethod,
			String cardNumber, String paymentType) throws Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		String testDescription = "Verify Cancellation policy for "+policyChargesType+" for single reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682501' target='_blank'>"
				+ "Click here to open TestRail: "+TestCaseID+"</a><br/>";
		String testCatagory = "Policies";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName +" with "+policyChargesType+ " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("824986");
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
		
		if (policyChargesType.equalsIgnoreCase("authorized")) {
			typeToValidate = "Authorization Only";
			authorizeIconSrc = "/Folio_Images/7.gif";
			authtorizeIcon = "A";
		}else if (policyChargesType.equalsIgnoreCase("captured")) {
			typeToValidate = "Capture";
			authorizeIconSrc = "/Folio_Images/8.gif";
			authtorizeIcon = "Capture";
		}

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
			policyName = policyName + randomNum;
			policyText = policyName + "_Text";
			policyDesc = policyName + "_Description";

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
			policyDetails = policies.createPolicies(driver, test_steps, "", "|", policyType, "", "", policyName,
					"", policyFeeType, policyFee, policyChargesType, "", "", "No", "");
			reservationPage.navigateToReservationPage(driver);
			policyText = policyDetails.get(policyName).toString().replace("[", "").replace("]", "").trim();
			app_logs.info("Policy Text: "+policyText);
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
				policyFee = Double.toString(max);
				policyName = policyName+"-Clause "+(count+1);
				policyText = policyText.replace(", ", "~").trim();
				//policyChargesType = policyChargesType.split("\\|")[count];
				app_logs.info("Details: "+policyFee);
				app_logs.info("Details: "+policyChargesType);
				app_logs.info("policyName: "+policyName);
				app_logs.info("policyText: "+policyText);
			}
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			
			rateGrid.selectRatePlan(driver, ratePlan, test_steps);
			rateGrid.clickOnEditRatePlan(driver);
//			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
//			rateGrid.clickOnGivenPolicy(driver, policyType, true);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);			
			nightlyRate.selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", "", false, test_steps);
			nightlyRate.selectPolicy(driver,policyType, policyName, true, test_steps);
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
						roomClassName);
				reservationPage.selectRoomToReserve(driver, test_steps, roomClassName, rooms.get(0));
				Wait.wait15Second();
				rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
				reservationPage.selectRoomToReserve(driver, test_steps, roomClassName, rooms.get(1));
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
			roomBaseAmount.clear();
			roomBaseAmount.add(roomChargeAmount);
			taxes = reservationPage.getTaxesFromTripSummary(driver);
			guestFullName = guestFirstName+" "+guestLastName;
//			if (policyFeeType.equalsIgnoreCase("percent of stay")) {
//				//policyFee = Double.toString((( Double.parseDouble(roomChargeAmount) * Double.parseDouble(policyFee) )/100))
//				if (policyChargesType.equalsIgnoreCase("room charges")) {
//					policyFee = reservationPage.calculateDepositePercenatge(roomChargeAmount, policyFee).replace(".00", "").trim();
//				}else if (policyChargesType.equalsIgnoreCase("total charges")) {
//					policyFee = reservationPage.calculateDepositePercenatge(tripTotal, policyFee).replace(".00", "").trim();
//				}			
//			}
		} catch (Exception e) { e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "InventoryNavigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {			 
			test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
			reservationPage.verifyCheckInPolicy(driver, test_steps, policyName, policyText);
			test_steps.add("===================== Verifying guest and policy details on payment check-in popup =====================");
			reservationPage.Click_CheckInButton(driver, test_steps);
			//reservationPage.selectRoomAtPaymentCheckInPopup(driver, test_steps, getRoomNumbers);
			reservationPage.generatGuestReportToggle(driver, test_steps,"No");
			reservationPage.clickOnProceedToCheckInPaymentButton(driver, test_steps);
			if (Scenario.contains("override")) {
				test_steps.add("===================== Verifying override CheckIn policy amount on payment check-in popup =====================");
				policyFee = Utility.GenerateRandomNumber(10, 40);
				reservationPage.verifyCheckInPolicyAmountOverride(driver, policyFee, test_steps);
				reservationPage.clickOnPayButtonOnPaymentPopup(driver, test_steps);
				reservationPage.checkInPaymentSuccessPopupClose(driver, test_steps);	
				reservationPage.close_FirstOpenedReservation(driver, test_steps);
			}else {
				app_logs.info(roomBaseAmount);
				ArrayList<String> amountDetails = reservationPage.paymentCheckInPopupVerify(driver, test_steps, "Check In Payment", 
						cardExpDate, guestFullName, "XXXX "+cardNumber.substring(cardNumber.length()-4), paymentMethod, 
						typeToValidate, policyFee, typeToValidate, policyName, policyText, roomBaseAmount, totalNights, taxes, null, null);
				reservationPage.clickOnPayButtonOnPaymentPopup(driver, test_steps);
				String abc = paymentMethod+" - "+Utility.getCardNumberHidden(cardNumber)+" ("+guestFullName+") (Exp. "+cardExpDate+")";
				test_steps.add("===================== Verifying guest and policy details in payment success popup =====================");
				reservationPage.checkInPaymentSuccessPopupVerify(driver, test_steps, "Check-In Successful", "Approved", abc, 
						amountDetails.get(0), typeToValidate, amountDetails.get(1), policyName, typeToValidate, policyText);
				reservationPage.checkInPaymentSuccessPopupClose(driver, test_steps);	
				test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
				reservationPage.close_FirstOpenedReservation(driver, test_steps);
				reservationPage.searchWithGuestName(driver, test_steps, guestFullName);
				reservationPage.clickOnGuestName(driver, test_steps);
				
				test_steps.add("===================== Verifying trip amount details at trip summary =====================");
				reservationPage.verifyTripSummeryAmount(driver, test_steps, roomBaseAmount, totalNights, amountDetails.get(0), amountDetails.get(1), typeToValidate);
				test_steps.add("===================== Verifying trip amount details in trip summary header=====================");
				reservationPage.verifyTripAmountDetailsAtTop(driver, test_steps, amountDetails.get(0), amountDetails.get(1), typeToValidate);
				reservationPage.click_Folio(driver, test_steps);
				if (Scenario.contains("folio")) {
					test_steps.add("===================== Verifying folio line items and amount after check-in =====================");
					reservationPage.verifyFolioLineItem(driver, test_steps, roomBaseAmount, totalNights, amountDetails.get(0), paymentMethod, amountDetails.get(1), typeToValidate);			
				}
//				reservationPage.click_History(driver, test_steps);
//				String expDesc = " payment for $"+amountDetails.get(1)+" using ("+paymentMethod+"-"+
//				Utility.getCardNumberHidden(cardNumber)+" "+cardExpDate+")";
//				reservationPage.verifyAuthAmountInFolioHistory(driver, test_steps, expDesc, "PAYMENT", typeToValidate);
			}
			
			comments = Scenario+" Verified functionality of Check-In when there is Check-In policy with "+policyChargesType;
			statusCode = new ArrayList<String>();
			caseId = new ArrayList<String>();
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("1");
				
			}
	        
		}catch (Exception e) {
			System.out.println(e);
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to cancel reservation and verify associated cancellation and other "
						+ "payment details", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {e.printStackTrace();
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

			test_steps.add("===================== Deleting policy created during test run =====================");
			navigation.Inventory(driver, test_steps);
			navigation.policies(driver, test_steps);
			policies.deleteAllPolicies(driver, test_steps, policyType, policyName);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to delete policies after verification", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to delete policy after verification", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("CheckInPolicyNew", HS_EXCEL);
	}

	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
