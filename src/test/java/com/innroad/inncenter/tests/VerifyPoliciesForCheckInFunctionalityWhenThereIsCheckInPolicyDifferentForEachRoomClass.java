package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPoliciesForCheckInFunctionalityWhenThereIsCheckInPolicyDifferentForEachRoomClass extends TestCore {

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
			tripPaid, tripBalance, nameOnCard, roomChargeAmount;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Policy")
	public void verifyPoliciesDropdownAfterChangingRoomclass(String TestCaseID, String Scenario, String ClientCode, String Username, String Password, String resType,
			String roomClassName, String ratePlan,
			String policyType, String policyName, String policyFeeType, String policyFee, String policyChargesType,
			String noOfDaysToCancel, String cancelWithInType, String chargesAttribute,
			String checkInDate, String checkOutDate, String accountType, String accountName, String paymentMethod,
			String cardNumber, String isChangePolicy) throws Exception {

		String testDescription = "Verify Cancellation policy for "+policyChargesType+" for single reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682501' target='_blank'>"
				+ "Click here to open TestRail: "+TestCaseID+"</a><br/>";
		String testCatagory = "Policies";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName +" with "+policyChargesType+ " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("824986");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}			
		}

		Policies policies = new Policies();
		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Login login = new Login();
		Account account = new Account();
		Admin admin = new Admin();
		Groups group = new Groups();
		AdvGroups advgrp = new AdvGroups();
		
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
		String cancelReason = "Cancel";
		
		HashMap<String, String> policyNames = new HashMap<>();
		HashMap<String, String> policyTextList = new HashMap<>();
		ArrayList<String> policyTypesList = Utility.convertTokenToArrayList(policyType, "|");

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			// Get CheckIn, CheckOut Date
			if (checkInDate.equalsIgnoreCase("NA") || checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("")) {

				if (!resType.equalsIgnoreCase("MRB")) {
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
			login.login(driver, envURL, ClientCode, Username, Password);
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
		
//		try {
//			navigation.admin(driver);
//			navigation.Roles(driver);
//			admin.navigateToAdminRole(driver, "Administrator", test_steps);
//			admin.enableSpecialFunctionsForRoleUsingType(driver, "Restrict Policy Changes", true, test_steps);
//			admin.saveRole(driver, test_steps);
//		}catch (Exception e) {
//			app_logs.info(e.toString());
//		}

		try {
			app_logs.info("========== Creating a new policy ==========");
			test_steps.add("========== Creating a new policy ==========");

			navigation.Inventory(driver, test_steps);
			navigation.policies(driver, test_steps);
			policies.deleteAllPoliciesNew(driver, policyType, test_steps);
			HashMap<String, ArrayList<String>> policyDetails = new HashMap<>();
			policyDetails = policies.createPolicies(driver, test_steps, "|", ",", policyType, "", "", policyNames.get("policy1"),
					"", policyFeeType, policyFee, policyChargesType, noOfDaysToCancel, cancelWithInType, "No", "");
			policyTextList.put("policy1", policyDetails.get(policyNames.get("policy1")).toString().replace("[", "").replace("]", "").trim());
			Wait.wait3Second();
			policyDetails = policies.createPolicies(driver, test_steps, "|", ",", policyType, "", "", policyNames.get("policy2"),
					"", policyFeeType, policyFee, policyChargesType, noOfDaysToCancel, cancelWithInType, "No", "");
			policyTextList.put("policy2", policyDetails.get(policyNames.get("policy2")).toString().replace("[", "").replace("]", "").trim());
			app_logs.info("Poliy details: "+policyTextList);
			reservationPage.navigateToReservationPage(driver);
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			
			rateGrid.selectRatePlan(driver, ratePlan1, test_steps);
			rateGrid.clickOnEditRatePlan(driver);
			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
			rateGrid.clickOnGivenPolicy(driver, policyType, true);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);
//			nightlyRate.selectPolicy(driver, policyType, true, test_steps);
			nightlyRate.selectPolicy(driver, policyType, policyNames.get("policy1"), true, test_steps);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			rateGrid.selectRatePlan(driver, ratePlan2, test_steps);
			rateGrid.clickOnEditRatePlan(driver);
			nightlyRate.switchRestrictionAndPoliciesTab(driver, test_steps);
			rateGrid.clickOnGivenPolicy(driver, policyType, true);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDate);
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.clickSeasonPolicies(driver, test_steps);
//			nightlyRate.selectPolicy(driver, policyType, true, test_steps);
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
			
			test_steps.add("========== Creating Group account ==========");
			app_logs.info("========== Creating Group account ==========");
			navigation.groups(driver);
			accountName = "Group"+Utility.generateRandomString();
			if (!group.checkForGrouptExistsAndOpen(driver, test_steps, accountName, true)) {
				group.type_GroupName(driver, test, accountName, test_steps);
				group.type_AccountAttributes(driver, test, marketSegment, referral, test_steps);
				group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber,
						address1, city, state, country, postalCode, test_steps);
				group.Billinginfo(driver);
				group.Save(driver, test_steps);				
			}
			app_logs.info("Romm Classes: "+roomClassName);
			
			String BlockName = "Block" + Utility.GenerateRandomString15Digit();
			group.navigateRoomBlock(driver, test);			
			test_steps.add("========== Creating Group block ==========");
			app_logs.info("========== Creating Group block ==========");
			group.createNewBlockWithMultiRoomClasses(driver, BlockName, checkInDate, checkOutDate, ratePlan1, "1|1", roomClassName, test_steps);
			//test_steps.addAll(group.createNewBlock(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, numberOfRooms, RoomClass));
			group.navigateRoomBlock(driver, test);	
			advgrp.clickBlueBookIcon(driver);
			reservationPage.selectRoom(driver, test_steps, roomClass1, Utility.RoomNo, "");
			reservationPage.clickYesOrNoOnPolicyChangePopup(driver, test_steps, "Yes");
//			reservationPage.selectRoomNumber(driver, roomClass1, "", true, test_steps);
//			reservationPage.clickSelectRoomButtton(driver, roomClass1);
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, "Mr", guestFirstName, guestLastName, config.getProperty("flagOff"));			
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			String roomClassRes = reservationPage.getRoomClass_ResDetail(driver);
			
			reservationPage.inHouseReservation(driver);
			
			if (roomClassRes.equalsIgnoreCase(roomClass1)) {
				test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
				reservationPage.verifyCheckInPolicy(driver, test_steps, policyNames.get("policy1"), policyTextList.get("policy1"));
			}else {
				test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
				reservationPage.verifyCheckInPolicy(driver, test_steps, policyNames.get("policy2"), policyTextList.get("policy2"));
			}
			
			navigation.Reservation_Backward_3(driver);
			navigation.groups(driver);
			group.searchGroupAccount(driver, accountName, true, test_steps);
			group.navigateRoomBlock(driver, test);
			
			advgrp.clickBlueBookIcon(driver);
			reservationPage.selectRoom(driver, test_steps, roomClass2, Utility.RoomNo, "");
			//reservationPage.selectRoomNumber(driver, roomClass1, "", true, test_steps);
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, "Mr", guestFirstName, guestLastName, config.getProperty("flagOff"));			
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			roomClassRes = reservationPage.getRoomClass_ResDetail(driver);
			
			reservationPage.inHouseReservation(driver);
			if (roomClassRes.equalsIgnoreCase(roomClass1)) {
				test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
				reservationPage.verifyCheckInPolicy(driver, test_steps, policyNames.get("policy1"), policyTextList.get("policy1"));
			}else {
				test_steps.add("===================== Verifying associated check-in policy details at Policies And Disclaimers tab =====================");
				reservationPage.verifyCheckInPolicy(driver, test_steps, policyNames.get("policy2"), policyTextList.get("policy2"));
			}
			
			
			
			//reservationNumber = driver.findElement(By.xpath(OR_Reservation.HeaderConfirmationNo)).getText().trim();
			reservationPage.click_Folio(driver, test_steps);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyCheckInPolicyGroupBlock", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
