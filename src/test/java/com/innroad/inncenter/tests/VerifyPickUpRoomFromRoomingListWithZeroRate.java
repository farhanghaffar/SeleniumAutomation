package com.innroad.inncenter.tests;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPickUpRoomFromRoomingListWithZeroRate extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> BlockDetails = new ArrayList<>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	public void login(String testName) {

		try {
			if (!Utility.insertTestName.containsKey(testName)) {

				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
		    login_Group(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@Test(dataProvider = "getData")
	public void pickupBlockFromRoomingList(String TestCaseID,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight, String RoomClass, String EnterBlockedcount, String Amount,
			String City2, String Country2, String State2, String PaymentMethod, String Salutation, String channelName, String checkInDate, String checkOutDate,
			String paymentMethod, String cardNo)
			throws InterruptedException, IOException {

		String testName = "pickupBlockFromRoomingListWithZeroRate";
		String test_description = "Verify pickup block from rooming list<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554160' target='_blank'>"
				+ "Click here to open TestRail: C554160</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		if (!Utility.validateString(TestCaseID)) {
			caseId.add("825372");
			caseId.add("824948");
			caseId.add("825542");
			caseId.add("824947");
			statusCode.add("4");
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}

		}

		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advgrp = new AdvGroups();
		CPReservationPage reservationPage = new CPReservationPage();
		RatesGrid rateGrid = new RatesGrid();
		String defaultRatePlan=null ;
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		HashMap<String,String> rates= new HashMap<String, String>();

		
		try {

			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){
				if (AccountFirstName.split("\\|").length>1) {
					for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
								ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
					}
				}else
				{
					checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
							ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
				}
			}
			
			if (AccountFirstName.split("\\|").length>1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}



			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		// Navigate to Groups
				try {
					Nav.Groups(driver);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
						Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
						Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				
		// Create New Groups
		String AccountNo = "0";
		try {
			test_steps.add("=====================Create New Group================");
			app_logs.info("=====================Create New Group=================");
			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String resNumber = "0";
		String Room_Block_PromoCod = "null";
		try {
			test_steps.add("=====================Create RoomBlock================");
			app_logs.info("=====================Create RoomBlock=================");
			group.navigateRoomBlock(driver, test);
			
			BlockName = BlockName + Utility.GenerateRandomString15Digit();
			
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlockWithRate(driver, BlockName, "1", RoomClass,"0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			group.navigateRoomBlock(driver, test);

			String beforePickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			
			getTest_Steps.clear();
			group.Save(driver, test_steps);
			group.navigateRoomBlock(driver, test);

			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);
			advgrp.verify_RoomingList_details(driver, BlockName);
			getTest_Steps.clear();
			String expriryDate=Utility.getFutureMonthAndYearForMasterCard();
			test_steps.add("=====================Room Pickup from Rooming list================");
			app_logs.info("=====================Room Pickup from Rooming list=================");
			getTest_Steps = advgrp.roomingListPopup_RoomPickup(driver, AccountFirstName, AccountLastName, RoomClass,
					Amount, Phonenumber, Address1, city, Country, State, Postalcode,paymentMethod,  cardNo,expriryDate);
			test_steps.addAll(getTest_Steps);
			comments="Advanced Deposit payment method  is not available in Rooming List Billing Information pop-up. ";

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();

			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			
			advgrp.verify_RoomingList_PickUp_Summary1(driver, BlockName);


			comments=comments+"Sucessfully Picked up room from the rooming list with 0 rate. ";
			Utility.app_logs.info("Sucessfully Picked up room from the rooming list with 0 rate.");
			test_steps.add("Sucessfully Picked up room from the rooming list with 0 rate.");
			

			getTest_Steps.clear();
			getTest_Steps = advgrp.verifyCheckInButtonFromRoomingList(driver);
			test_steps.addAll(getTest_Steps);
			comments=comments+"Check-in button is displayed when reservation is picked from rooming list.";
			Utility.app_logs.info(comments);
		
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}			

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("pickupBlockZeroRate", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}
