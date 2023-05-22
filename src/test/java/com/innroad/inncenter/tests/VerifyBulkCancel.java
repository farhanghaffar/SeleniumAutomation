package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class VerifyBulkCancel extends TestCore {

	private WebDriver driver = null;

	static ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> totalNights = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

	static ExtentTest test;
	static ExtentReports report;
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	// @BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyBulkCancell(String TestCaseID, String displayName, String Policy_T,
			String ratePlan, String source, String firstName, String lastName, String roomChargePercentage,
			String roomCharge, String IsAssign, String isChecked, String checkInPolicType, String checkInPolicyName,
			 String paymentType, String cardNumber, String nameOnCard, String cardExpDate,
			String PolicyBalanceOption, String PolicyPercentAmount, String Action, String PolicyApplicable, String mRBReservation,
			String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode,
			String IsSplitRes, String RoomClass, String IsDepositOverride, String DepositOverrideAmount,
			String IsAddMoreGuestInfo, String Salutation, String GuestFirstName, String GuestLastName,
			String PhoneNumber, String AltenativePhone, String Email, String Account, String AccountType,
			String Address1, String Address2, String Address3, String City, String Country, String State,
			String PostalCode, String IsGuesProfile, String TravelAgent, String MarketSegment, String Referral,
			String Description, String TaskDueon, String GroupedMBR, String HighestCHeckInPolicyCheck)
			throws ParseException, Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		if (!Utility.validateString(TestCaseID)) {
			caseId.add("848133");
			statusCode.add("4");
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}

		}
		String[] roomClass = RoomClass.replace("|", " ").split(" ");
		printString(String.valueOf(roomClass.length));

		String testName = "Verify Check-In/CheckOut/RollBack MRB";
		test_description = "Verify Check-In/CheckOut/RollBack MRB<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682431' target='_blank'>"
				+ "Click here to open TestRail: C682431</a>";
		test_catagory = "Reservation";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");

		// MBR Reservation
		Double depositAmount = 0.0;
		Double paidDeposit = 0.0;
		String reservation = null;
		String status = null;
		ArrayList<String> Rooms = new ArrayList<String>();
		ArrayList<String> roomCost = new ArrayList<String>();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
//		ArrayList<String> testSteps = new ArrayList<>();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		CPReservationPage cpRes = new CPReservationPage();
		Navigation navigation = new Navigation();
		Policies policies = new Policies();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		Groups group = new Groups();
		String tempraryRoomClassName = roomClass[0];
		String randomNumber = Utility.GenerateRandomNumber();
		roomClass[0] = roomClass[0] + randomNumber;
		displayName = roomClass[0];
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Double expectedAmount = 0.0;
		String policyText = Policy_T + "_Text";
		String policyDesc = Policy_T + "_Description";
		Policy_T = Policy_T + randomNumber;
		checkInPolicyName = checkInPolicyName + randomNumber;
		//cancelationPolicyName = cancelationPolicyName + randomNumber;
		String randomNum = Utility.GenerateRandomNumber(3);
		String adult = "2";
		String children = "1";
		String reservationNumber1 = "";

		// Login

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			loginRateV2(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

//			assertTrue(false, "Failed delibrately");
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		//////////////////////////// Verify Highest check in policy is picked with MRB
		//////////////////////////// Reservation////////////////////////////

		if (HighestCHeckInPolicyCheck.equalsIgnoreCase("yes")) {
			try {

				// Get CheckIN and Checkout Date
				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				TaskDueon = checkOutDates.get(0);
/*
				app_logs.info("Navigating to Inventory");
				navigation.Inventory(driver, testSteps);

				testSteps.add("Navigating to policy");
				app_logs.info("Navigating to policy");
				navigation.policies(driver);
				testSteps.add("Searching For All policies");
				app_logs.info("Searching For All policies");

				// testSteps.clear();
				policies.Search_All_Policy_U(driver, testSteps);
				// testSteps.addAll(testSteps);
				app_logs.info("Check In policies Search Complete");

				int size = policies.Check_Size_Of_Policies_Rows(driver);
				if (size > 0) {
					testSteps.add("Deleting policies");
					app_logs.info("Deleting policies");
					policies.DeleteAllPolicies(driver, testSteps);
				} else {
					testSteps.add("No policies Found For Deletion");
					app_logs.info("No policies Found For Deletion");
				}

				String checkInAttributeName = "authorize";
				String checkInAttributeValue = "20";
				String checkInAttributeValue2 = "30";
				Thread.sleep(1000);
				policies.createPolicyCheckIn_U(driver, testSteps,
						checkInPolicyName + " with" + checkInAttributeName + " " + checkInAttributeValue,
						checkInPolicType, checkInAttributeName, checkInAttributeValue, policyAttr2, policyAttrValue2,
						source, checkInPolicType, "KingSuite", ratePlan, policyText, policyDesc);

				app_logs.info("Navigating to Inventory");
				navigation.Inventory(driver, testSteps);

				testSteps.add("Navigating to policy");
				app_logs.info("Navigating to policy");
				navigation.policies(driver);
				Wait.wait3Second();

				// testSteps.clear();
				policies.createPolicyCheckIn_U(driver, testSteps,
						checkInPolicyName + " with" + checkInAttributeName + " " + checkInAttributeValue2,
						checkInPolicType, checkInAttributeName, checkInAttributeValue2, policyAttr2, policyAttrValue2,
						source, checkInPolicType, roomClass[1], ratePlan, policyText, policyDesc);
				// testSteps.addAll(testSteps);

		*/		
				
				app_logs.info("========== Creating a new policy ==========");
				testSteps.add("========== Creating a new policy ==========");

				navigation.Inventory(driver, testSteps);
				navigation.policies(driver, testSteps);
				//policies.deleteAllPoliciesNew(driver, checkInPolicType, testSteps);
				HashMap<String, ArrayList<String>> policyDetails = policies.createPolicies(driver, testSteps, "//|", "//|", checkInPolicType, 
						"", "", checkInPolicyName,"",
						"% of balance on check-in", PolicyPercentAmount, PolicyBalanceOption, "", "", "No", "");
				reservationPage.navigateToReservationPage(driver);
				policyText = policyDetails.get(checkInPolicyName).toString().replace("[", "").replace("]", "").trim();
				app_logs.info("Policy Text: "+policyText);
				
				navigation.Inventory(driver, testSteps);
				navigation.ratesGrid(driver);
				testSteps.add("Navigated to rateGrid");
				
				rateGrid.selectRatePlan(driver, ratePlan, testSteps);
				rateGrid.clickOnEditRatePlan(driver);
				nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);
//				rateGrid.clickOnGivenPolicy(driver, "Check-in", true);
//				nightlyRate.selectPolicy(driver, checkInPolicType, true, testSteps);
				nightlyRate.selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", "", false, testSteps);
				nightlyRate.selectPolicy(driver, checkInPolicType, checkInPolicyName, true, testSteps);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
				
				rateGrid.closeRatePlan(driver, ratePlan, testSteps);
				driver.navigate().refresh();
				
				rateGrid.selectRatePlan(driver, ratePlan, testSteps);
				rateGrid.clickOnEditRatePlan(driver);
				nightlyRate.switchCalendarTab(driver, testSteps);
				nightlyRate.selectSeasonDates(driver, testSteps, checkInDates.get(0));
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);

				nightlyRate.clickSeasonPolicies(driver, testSteps);
				nightlyRate.selectPolicy(driver, "Cancellation|Deposit|Check-in|No Show", "", false, testSteps);
				nightlyRate.selectPolicy(driver, checkInPolicType, checkInPolicyName, true, testSteps);
//				nightlyRate.selectPolicy(driver, checkInPolicType, true, testSteps);
				//nightlyRate.selectPolicy(driver, policyName, true, test_steps);
				nightlyRate.clickSaveSason(driver, testSteps);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
				
//Reservation Started
				
				try {
					navigation.Reservation_Backward_3(driver);				
				} catch (Exception e) {
					navigation.Reservation_Backward_1(driver);
				}
				// testSteps.clear();
				Thread.sleep(2000);
				cpRes.click_NewReservation(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,
						IsSplitRes);
				// testSteps.addAll(testSteps);

				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					// testSteps.clear();
					cpRes.enter_Adults(driver, testSteps, Adults);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.enter_Children(driver, testSteps, Children);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.select_Rateplan(driver, testSteps, Rateplan, PromoCode);
					// testSteps.addAll(testSteps);
				}
				// testSteps.clear();
				cpRes.clickOnFindRooms(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				roomCost = cpRes.select_MRBRooms(driver, testSteps, RoomClass, IsAssign, Account);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				depositAmount = cpRes.deposit(driver, testSteps, IsDepositOverride, DepositOverrideAmount);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.clickNext(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.enter_MRB_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName,
						PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City,
						Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes, Rooms);
				// testSteps.addAll(testSteps);

				if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
					// testSteps.clear();
					cpRes.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
					// testSteps.addAll(testSteps);
				}
				System.out.println(Rooms);

				// testSteps.clear();
				cpRes.enter_MarketSegmentDetails(driver, testSteps, TravelAgent, MarketSegment, Referral);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.clickBookNow(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				reservation = cpRes.get_ReservationConfirmationNumber(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				status = cpRes.get_ReservationStatus(driver, testSteps);
				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.clickCloseReservationSavePopup(driver, testSteps);
				// testSteps.addAll(testSteps);

				Double balanceString = Double.valueOf(cpRes.getBalance_TripSummary(driver));
//				Double checkInDouble_Percentage_AmountDouble = (balanceString * Double.valueOf(checkInAttributeValue2))
//						/ 100;

				// testSteps.clear();
				cpRes.Click_CheckInAllButton(driver, testSteps);
				// testSteps.addAll(testSteps);
				cpRes.generatGuestReportToggle(driver, testSteps, "No");
				cpRes.completeCheckInProcess(driver, testSteps);
//				// testSteps.clear();
//				cpRes.verifyCheckInConfirmDetailsPaymentPopupIsAppeared_U(driver,
//						String.valueOf(checkInDouble_Percentage_AmountDouble),
//						checkInPolicyName + " with" + checkInAttributeName + " " + checkInAttributeValue2, testSteps);
//				// testSteps.addAll(testSteps);

				// testSteps.clear();
				cpRes.VerifyinHouseStatus(driver, testSteps);
				// testSteps.addAll(testSteps);
//				testSteps.add("Verified: highest check in policy is picked ");
				statusCode.add(0, "1");
				Wait.wait15Second();
				testSteps.add("Verify Bulk Checkout Flow for CHECK OUT ALL in case of multiple folios");
				cpRes.departedReservation2(driver, testSteps);
				cpRes.rollBackAll(driver, testSteps);
				statusCode.add(1, "1");
			} catch (Exception e) {
				e.printStackTrace();

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Pick Highest Percentage policy", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Pick Highest Percentage policy", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else {

			if (mRBReservation.equalsIgnoreCase("Yes")) {

				if (!(Utility.validateInput(CheckInDate))) {
					for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				TaskDueon = checkOutDates.get(0);

				if (GroupedMBR.equalsIgnoreCase("yes")) {
					// Create New Groups
					String AccountNo = "0";
					try {
						navigation.Groups(driver);

						String groupName = "TestGroup" + randomNum;
						// testSteps.clear();
						group.type_GroupName(driver, test, groupName, testSteps);
						// testSteps.addAll(testSteps);

						AccountNo = Utility.GenerateRandomString15Digit();
						// testSteps.clear();
						testSteps = group.enterAccountNo(driver, AccountNo);
						// testSteps.addAll(testSteps);

						// testSteps.clear();
						group.type_AccountAttributes(driver, test, "Internet", Referral, testSteps);
						// testSteps.addAll(testSteps);
						// testSteps.clear();
						group.type_MailingAddrtess(driver, test, "Account" + firstName, "Account" + lastName,
								"1234567899", Address1, City, State, Country, PostalCode, testSteps);
						// testSteps.addAll(testSteps);
						// testSteps.clear();
						group.billinginfo(driver, test, testSteps);
						// testSteps.addAll(testSteps);
						// testSteps.clear();
						group.save(driver, test, testSteps);
						// testSteps.addAll(testSteps);
						testSteps.add(groupName + " Group Created");

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}

					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

				}

				// Reservation
				try {

					navigation.reservation(driver);
					Utility.refreshPage(driver);
					Thread.sleep(4000);

					// testSteps.clear();
					cpRes.click_NewReservation(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan,
							PromoCode, IsSplitRes);
					// testSteps.addAll(testSteps);

					if (IsSplitRes.equalsIgnoreCase("Yes")) {
						// testSteps.clear();
						cpRes.enter_Adults(driver, testSteps, Adults);
						// testSteps.addAll(testSteps);

						// testSteps.clear();
						cpRes.enter_Children(driver, testSteps, Children);
						// testSteps.addAll(testSteps);

						// testSteps.clear();
						cpRes.select_Rateplan(driver, testSteps, Rateplan, PromoCode);
						// testSteps.addAll(testSteps);
					}
					// testSteps.clear();
					cpRes.clickOnFindRooms(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					roomCost = cpRes.select_MRBRooms(driver, testSteps, RoomClass, IsAssign, Account);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					depositAmount = cpRes.deposit(driver, testSteps, IsDepositOverride, DepositOverrideAmount);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.clickNext(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.enter_MRB_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName,
							PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3,
							City, Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes, Rooms);
					// testSteps.addAll(testSteps);

					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						// testSteps.clear();
						cpRes.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
						// testSteps.addAll(testSteps);
					}
					System.out.println(Rooms);

					// testSteps.clear();
					cpRes.enter_MarketSegmentDetails(driver, testSteps, TravelAgent, MarketSegment, Referral);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.clickBookNow(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					reservation = cpRes.get_ReservationConfirmationNumber(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					status = cpRes.get_ReservationStatus(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					cpRes.clickCloseReservationSavePopup(driver, testSteps);
					// testSteps.addAll(testSteps);

					testSteps.add("Navigating to Reservation");
					app_logs.info("Navigating to Reservation");
					navigation.reservation(driver);
					Thread.sleep(1000);

					testSteps.add("==========BULK ACTION VERIFICATION==========");
					app_logs.info("==========BULK ACTION VERIFICATION==========");

					Utility.refreshPage(driver);

					app_logs.info("Searching Reservation By Created Reservation Number");
					testSteps.add("Searching Reservation By Created Reservation Number");

					reservationPage.closeAllOpenedReservations(driver);

					driver.navigate().refresh();

					// testSteps.clear();
					reservationSearch.basicSearchWithResNumber(driver, testSteps, reservation, false);
					// testSteps.addAll(testSteps);

					String expectedString = "Bulk action cannot be performed on Multi Room Reservations.";
					// testSteps.clear();

					String actualTextTeString = cpRes.getText(driver);
					printString(actualTextTeString);
					try {
						String actual_String = cpRes.MBR_Reservation_CheckBox_HoverText(driver, testSteps);

						Assert.assertEquals(actual_String, expectedString.toLowerCase(),
								"Failed: Hover Text Verification");
						// testSteps.addAll(testSteps);
						testSteps.add("Expected Text: " + expectedString);
						testSteps.add("Bulk Action Is Verified");
					} catch (Exception e) {

					}
					statusCode.add(0, "1");

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Verify Bluk Hover Text", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Verify Bluk Hover Text", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			} else {

				// Bulk Action Verification With and Without Policy

				try {

					// Policy Deletion According To Action
					app_logs.info("Navigating to Inventory");
					navigation.Inventory(driver, testSteps);

					testSteps.add("Navigating to policy");
					app_logs.info("Navigating to policy");
					navigation.policies(driver);
					testSteps.add("Searching " + Action + " policies");
					app_logs.info("Searching " + Action + " policies");
					if (Action.equalsIgnoreCase("No Show")) {
						policies.Search_No_Show_Policy_U(driver, testSteps);
						app_logs.info("No Show policies Search Complete");
					} else if (Action.equalsIgnoreCase("Cancellation")) {
						policies.Search_Cancellation_Policy(driver, testSteps);
						app_logs.info("Cancellation policies Search Complete");
					} else if (Action.equalsIgnoreCase("All")) {
						policies.Search_All_Policy_U(driver, testSteps);
						app_logs.info("All policies Search Complete");
					} else if (Action.equalsIgnoreCase("Deposit")) {
						policies.Search_Deposit_Policy_U(driver, testSteps);
						app_logs.info("Deposit policies Search Complete");
					} else if (Action.equalsIgnoreCase("Check In")) {
						policies.Search_CheckIn_Policy_U(driver, testSteps);
						app_logs.info("Check In policies Search Complete");
					}

					Thread.sleep(1000);

					int size1 = policies.Check_Size_Of_Policies_Rows(driver);
					printString("\n\n\n\n\n" + size1 + "\n\n\n\n");
					if (size1 > 0) {
						testSteps.add("Deleting " + Action + " policies");
						app_logs.info("Deleting " + Action + " policies");
						policies.DeleteAllPolicies(driver, testSteps);
					} else {
						testSteps.add("No (" + Action + ") policies For Deletion");
						app_logs.info("No (" + Action + ") policies For Deletion");
					}

//					if (PolicyApplicable.equalsIgnoreCase("yes")) {
//
//						// testSteps.clear();
//						policies.createPolicy_U(driver, testSteps, Action + " Policy" + randomNum, Action, roomCharge,
//								roomChargePercentage, policyAttr2, policyAttrValue2, source, roomCharge,
//								tempraryRoomClassName, ratePlan, policyText, policyDesc);
//						// testSteps.addAll(testSteps);
//
//					}

					testSteps.add("Navigate To Reservation");
					app_logs.info("Navigate To Reservation");
					navigation.Reservation(driver);
					testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
					app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");

					getTestSteps.clear();
					Thread.sleep(4000);
					app_logs.info("Clicking on New Reservation");
					getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering Check In Date");
					app_logs.info("Entering Check In Date");
					getTestSteps = reservationPage.checkInDate(driver, 0);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering Check Out Date");
					app_logs.info("Entering Check Out Date");
					getTestSteps = reservationPage.checkOutDate(driver, +1);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering Adult Value");
					app_logs.info("Entering Adult Value");
					getTestSteps = reservationPage.enterAdult(driver, adult);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					testSteps.add("Entering Children Value");
					app_logs.info("Entering Children Value");
					getTestSteps = reservationPage.enterChildren(driver, children);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					app_logs.info("Clicking On Find Room");
					getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
					testSteps.addAll(getTestSteps);

					testSteps.add("Filling All Fullfil Values");
					app_logs.info("Filling All Fullfil Values");
					getTestSteps.clear();
					getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.clickNext(driver, getTestSteps);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver,
							Boolean.parseBoolean(isChecked));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.", firstName, lastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer",
							cardExpDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.selectReferral(driver, "GDS");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
					testSteps.add("Successfully click Book Now");

					getTestSteps.clear();
					reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
					testSteps.add("Reservation confirmation number: " + reservationNumber1);
					app_logs.info("Reservation confirmation number: " + reservationNumber1);

					getTestSteps.clear();
					reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
					testSteps.add("Sucessfully close save quote popup");

					if (Action.equalsIgnoreCase("Cancellation")) {
						Double RoomChargeAmount = Double.parseDouble(cpRes.get_RoomCharge_U(driver, testSteps));
						printString("Room Charge: " + RoomChargeAmount);
						expectedAmount = (RoomChargeAmount * 10) / 100;
						printString("Expected Amount: " + expectedAmount);
						// testSteps.addAll(testSteps);
					}

					testSteps.add("Navigating to Reservation");
					app_logs.info("Navigating to Reservation");
					navigation.reservation(driver);
					Thread.sleep(1000);

					testSteps.add("==========BULK ACTION==========");
					app_logs.info("==========BULK ACTION==========");

					Utility.refreshPage(driver);

					getTestSteps.clear();
					reservationSearch.basicSearchWithResNumber(driver, testSteps, reservationNumber1, false);
					testSteps.addAll(getTestSteps);
					app_logs.info("Searching Reservation By Created Reservation Number");

					if (Action.equalsIgnoreCase("No Show")) {

						getTestSteps.clear();
						getTestSteps = reservationSearch.bulkActionNo_Show_U(driver);
						testSteps.addAll(getTestSteps);
					} else if (Action.equalsIgnoreCase("Cancellation")) {
						getTestSteps.clear();
						getTestSteps = reservationSearch.bulkActionCancellation_U(driver);
						testSteps.addAll(getTestSteps);
					}

					// GuestName Verification
					String guestNameString = firstName.trim() + " " + lastName.trim();
					printString(guestNameString);
					Assert.assertEquals(reservationSearch.getGuestName(driver), guestNameString,
							"Failed : Guest name didn't match");
					testSteps.add("Verified Bulk " + Action + " GuestName=> " + guestNameString);
					app_logs.info("Verified Bulk " + Action + " GuestName=> " + guestNameString);

//						//Reservation Number Verification

					getTestSteps.clear();
					Assert.assertEquals(reservationSearch.getReservationNo(driver), reservationNumber1,
							"Failed : Reservation Number didn't match");
					testSteps.add("Verified Bulk " + Action + " Number =>  " + reservationNumber1);
					app_logs.info("Verified Bulk " + Action + " Number =>  " + reservationNumber1);

					if (PolicyApplicable.equalsIgnoreCase("yes") && Action.equalsIgnoreCase("Cancellation")) {
						// Amount Verification
						// testSteps.clear();
						Assert.assertEquals(reservationSearch.getFixedAmount(driver), "0");
						testSteps.add("Verified Bulk Cancellation Amount => 0");// +expectedAmount);
						app_logs.info("Verified Bulk Cancellation Amount => 0");// +expectedAmount);

					}

					// testSteps.clear();
					reservationSearch.clickClose(driver, testSteps);
					// testSteps.addAll(testSteps);

					// testSteps.clear();
					reservationSearch.basicSearchWithResNumber(driver, testSteps, reservationNumber1, false);
					// testSteps.addAll(testSteps);
					app_logs.info("Searching Reservation By Created Reservation Number");

					if (Action.equalsIgnoreCase("No Show")) {

						// testSteps.clear();
						reservationSearch.ReservationNoShowStatus_U(driver, reservationNumber1, testSteps);
						// testSteps.addAll(testSteps);
					} else if (Action.equalsIgnoreCase("Cancellation")) {
						// testSteps.clear();
						reservationSearch.ReservationCancelStatus_U(driver, reservationNumber1, testSteps);
						// testSteps.addAll(testSteps);
					}
					statusCode.add(0, "1");

				} catch (Exception e) {
					e.printStackTrace();

					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify Bulk Status", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify Bulk Status", testName, "InventoryNavigation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}

		}
		comments = "Verified policies through bulk actions";
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("bulknoshow", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}
}
