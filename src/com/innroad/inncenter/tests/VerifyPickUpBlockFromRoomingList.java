package com.innroad.inncenter.tests;
import java.io.IOException;
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

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
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
		//	login_Group(driver);
			login_CP(driver);
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
	public void pickupBlockFromRoomingList(String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight, String RoomClass, String EnterBlockedcount, String Amount,
<<<<<<< HEAD
			String City2, String Country2, String State2, String PaymentMethod, String Salutation, String channelName, String checkInDate, String checkOutDate, String paymentMethod, String cardNo)
=======
			String City2, String Country2, String State2, String PaymentMethod, String Salutation, String channelName, String checkInDate, String checkOutDate,
			String paymentMethod, String cardNo)
>>>>>>> develop
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

			group.navigateRoomBlock(driver, test);
			
			BlockName = BlockName + Utility.GenerateRandomString15Digit();
			
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight);
			test_steps.addAll(getTest_Steps);
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

			Wait.wait3Second();
			String afterPickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			getTest_Steps.clear();
			getTest_Steps = advgrp.verifyCreatedReservation(driver, resNumber, AccountName, AccountFirstName,AccountLastName);

			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();

			advgrp.clickBlueBookIcon(driver);
			
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));	
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);			
			
			getTest_Steps.clear();
			getTest_Steps = res.verifyGroupinStayInfo(driver, AccountName, true);
			test_steps.addAll(getTest_Steps);
			res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);
   
			
			DecimalFormat df = new DecimalFormat("0.00");
			df.setMaximumFractionDigits(2);
			Amount=df.format(Double.parseDouble(Amount));
			app_logs.info(Amount);
			app_logs.info(rates.get(checkInDate));
			String roomCharges=df.format(Double.valueOf(rates.get(checkInDate)));
			app_logs.info(roomCharges);
			reservationPage.clickFolio(driver, test_steps);
			reservationPage.verifyFolioLineItem(driver, checkInDate, "Room Charge", "Group Rate", rates.get(checkInDate), test_steps);
			reservationPage.verifyRoomChargesAtFolio(driver, test_steps, roomCharges);
			reservationPage.verifyBalanceAmountAtFolio(driver, test_steps, roomCharges);
			
			reservationPage.click_FolioDetail_DropDownBox(test_steps, driver);
			reservationPage.verifyFolioDetailOptions(driver, test_steps, AccountName);
			reservationPage.clickFolioDetailOptions(driver, test_steps, AccountName);
			
			
			reservationPage.closeReservationTab(driver);
			Nav.Groups(driver);
			group.Search_Account(driver, AccountName, AccountNo);
			group.navigateRoomBlock(driver, test);

			String afterPickupValue1 = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue1);
			test_steps.add("After Pickup Value : " + afterPickupValue1);
			Assert.assertEquals(Integer.parseInt(afterPickupValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed PickUp Value Not increased");

			Utility.app_logs.info("Successfully Verified Pick a block from RoomingList");
			test_steps.add("Successfully Verified Pick a block from RoomingList");

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
		return Utility.getData("pickupBlockFromRoomingList", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
