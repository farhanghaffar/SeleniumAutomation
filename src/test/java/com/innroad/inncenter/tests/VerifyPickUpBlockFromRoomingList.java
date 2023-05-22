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

public class VerifyPickUpBlockFromRoomingList extends TestCore {

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
			String BlockName, String RoomPerNight, String RoomClass,String RoomClass2, String EnterBlockedcount, String Amount,
			String City2, String Country2, String State2, String PaymentMethod, String Salutation, String channelName, String checkInDate, String checkOutDate,
			String paymentMethod, String cardNo)
			throws InterruptedException, IOException {

		String testName = "pickupBlockFromRoomingList";
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
			caseId.add("824757");
			caseId.add("824761");
			caseId.add("824737");
			caseId.add("825235");
			caseId.add("824613");
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
		
		try {
			Nav.inventory(driver);
			Nav.ratesGrid(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
			defaultRatePlan= rateGrid.getDefaultRatePlan(driver, test_steps);
			app_logs.info(defaultRatePlan);
			rateGrid.expandRoomClass(driver, RoomClass);
			
			rates=rateGrid.getRatesOfChannel(driver, checkInDate, checkOutDate, RoomClass, channelName);
			app_logs.info(rates);
			Nav.navReservationFromRateGrid(driver);
		}
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Get Rate from Rate Grid", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Get Rate from Rate Grid", testName, "Group", driver);
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
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClass);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			group.navigateRoomBlock(driver, test);
			advgrp.verify_RoomBlock_Details(driver, AccountName, BlockName, RoomPerNight, EnterBlockedcount);

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

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();

			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			
			advgrp.verify_RoomingList_PickUp_Summary(driver, BlockName);

			comments="Verified the Reservation is made from the Rooming List with CC as payment method";
			Utility.app_logs.info("Verified the Reservation is made from the Rooming List with CC as payment method");
			test_steps.add("Verified the Reservation is made from the Rooming List with CC as payment method");

			Wait.wait3Second();
			String afterPickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			getTest_Steps.clear();
			getTest_Steps = advgrp.verifyCreatedReservation(driver, resNumber, AccountName, AccountFirstName,AccountLastName);
			comments=comments+". Verified the reservation created from rooming list pop up using select rooms";
			Utility.app_logs.info("Verified the reservation created from rooming list pop up using select rooms");
			test_steps.add("Verified the reservation created from rooming list pop up using select rooms");
			
			comments=comments+". Picked the selected rooms from Rooming list by making a payment";
			Utility.app_logs.info("Picked the selected rooms from Rooming list by making a payment");
			test_steps.add("Picked the selected rooms from Rooming list by making a payment");
			
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();

			//advgrp.clickBlueBookIcon(driver);
			advgrp.ClickYellowBookIcon(driver);
			boolean toogleExists=reservationPage.verifyOverideDepositToggle(driver);
			if(toogleExists) {
				comments=comments+". Override Deposit Due toggle is displayed for group.";
				Utility.app_logs.info("Override Deposit Due toggle is displayed for group.");
				test_steps.add("Override Deposit Due toggle is displayed for group.");
				
			}else {
				Utility.app_logs.info("Override Deposit Due toggle is not displayed for group.");
				test_steps.add("Override Deposit Due toggle is not displayed for group.");
			}
			comments=comments+" Picked up from yellow book icon.";
			Utility.app_logs.info("Picked up from yellow book icon");
			test_steps.add("Picked up from yellow book icon");
			 String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			//reservationPage.click_FindRooms(driver, test_steps);
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.selectRoom1(driver, test_steps, RoomClass2, "Yes", "");
			reservationPage.clickYesInPolicyPopUp(driver);
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));	
			reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNo, "Auto User", expiryDate);
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);	
			comments=comments+" Verified user is able to create a group reservation with yellow book icon.";
			Utility.app_logs.info("Verified user is able to create a group reservation with yellow book icon");
			test_steps.add("Verified user is able to create a group reservation with yellow book icon");
			
			getTest_Steps.clear();
			getTest_Steps = res.verifyGroupinStayInfo(driver, AccountName, true);
			test_steps.addAll(getTest_Steps);
			res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);
			test_steps.add("================Navigate to Groups================");
			app_logs.info("=================Navigate to Groups=================");
			Nav.Groups(driver);
			Utility.app_logs.info("Navigate to Group Tab");
			test_steps.add("Navigate to Group Tab");

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
			group.navigateRoomBlock(driver, test);
			String pickup=group.getPickUpValue(driver, RoomClass2);
			Assert.assertEquals(pickup, "1","Failed to verify pickup value");
			comments=comments+". verified the flow of picking up and creating a Group block Reservation via Group Pickup button from Dashboard - unblocked rooms. ";
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
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
		return Utility.getData("pickupBlockFromRoomingList", BEExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}
