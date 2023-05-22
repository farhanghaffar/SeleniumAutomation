package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ChangeCountryStateInRoomingListAndVerifyForGroup extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
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

	@Test(dataProvider = "getData", groups = { "groups" })
	public void verifyCountryInRoomingList_GroupAcc(String TestCaseID,String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight, String RoomClass, String Amount, String City2, String Country2,
			String State2, String PaymentMethod, String AccountNumber, String ExpiryDate, String City3, String Country3,
			String State3,String RatePlan) throws InterruptedException, IOException, ParseException {

		String testName = "ChangeCountryStateInRoomingListAndVerifyForGroup";
		String test_description = "Verify selecting a diffrent country in the rooming list billing info and pickup.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554187' target='_blank'>"
				+ "Click here to open TestRail: C554187</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("786832");
			caseId.add("786834");
			caseId.add("786839");
			caseId.add("786846");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}

		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();

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
			ExpiryDate=Utility.getFutureMonthAndYearForMasterCard();
			app_logs.info(ExpiryDate);
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
			getTest_Steps = group.billinginfo(driver, PaymentMethod, AccountNumber, ExpiryDate, true, true);
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

		String resNo = null;
		try {
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
		//	getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight);
			getTest_Steps =group.createNewBlock1(driver, BlockName, RoomPerNight, RatePlan, RoomClass);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);
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
		try {
			String beforePickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_RoomPickup(driver, AccountFirstName, AccountLastName, RoomClass,
					Amount, Country2, State2, City2, PaymentMethod, AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.VerifyRoomingListPopup_RoomPickup(driver, Country2, State2, City2, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);
			
			String roomNo = group.getSelectedRoomNo(driver);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");
			
			/*
			 * getTest_Steps.clear(); getTest_Steps =
			 * group.verifyRoomingListSummaryInfo(driver, BlockName, "Reserved", "50");
			 * test_steps.addAll(getTest_Steps);
			 */
			
			resNo = group.pickUp_getResNo(driver);
			Utility.app_logs.info("Created Reservation No : " + resNo);
			test_steps.add("Created Reservation No : " + resNo + 
					"<br/><a href='https://innroad.atlassian.net/browse/NG-7215' target='_blank'>"
					+ "Verified : NG-7215</a><br/>");

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListPickUpSummary_Reservation(driver, RoomClass, roomNo, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpCloseClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			group.loadingImage(driver);

			String afterPickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			assertEquals(Integer.parseInt(afterPickupValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed PickUp Value Not increased");
			Utility.app_logs.info("Successfully Verified Pick Up Increased Value");
			test_steps.add("Successfully Verified Pick Up Increased Value");

			getTest_Steps.clear();
			getTest_Steps = group.verifyArriveDepartDate_RoomBlockDetail(driver);
			test_steps.addAll(getTest_Steps);

			String status = group.getStatus_RoomBlockDetatil(driver);
			assertEquals(status, "Status: Reserved", "Failed Status RoomBlock Detail");
			Utility.app_logs.info("Successfully Verified Room Block Detail Status  : " + status);
			test_steps.add("Successfully Verified Room Block Detail Status  : " + status);

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			assertEquals(pickUpPercentage, "50", "Failed PickUp Percentage RoomBlock Detail");
			Utility.app_logs.info("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify PickUp", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify PickUp", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
			
		
		String fourDigitCardNo=Utility.getCardNumberHidden(AccountNumber);
		String descText="Name: "+AccountFirstName+" "+AccountLastName+"Account #: "+fourDigitCardNo+"Exp. Date: "+ExpiryDate+"";
		String detail=fourDigitCardNo;
		app_logs.info(detail);
		
		try {

			Nav.Reservation_Backward(driver);
			test_steps.add("Navigate to Reservation");
			app_logs.info("Navigate to Reservation");

			reservationSearch.basicSearch_WithResNumber(driver, resNo);
			test_steps.add("Reservation Successfully Searched : " + resNo);
			app_logs.info("Reservation Successfully Searched : " + resNo);
			getTest_Steps.clear();
			getTest_Steps = res.verifyGroupinStayInfo(driver, AccountName, true);
			test_steps.addAll(getTest_Steps);

			res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);
			getTest_Steps.clear();
			getTest_Steps = res.verifyCountryState_BillingInfo(driver, city, State, Country);
			test_steps.addAll(getTest_Steps);
			reservationPage.VerifyPaymentMethod(driver, test_steps, PaymentMethod, AccountNumber, AccountFirstName+" "+AccountLastName, ExpiryDate);
			
			reservationPage.clickFolio(driver, test_steps);
			test_steps.add("Navigate to Folio Tab");
			app_logs.info("Navigate to Folio Tab");

			getTest_Steps.clear();
			getTest_Steps = res.verifyFolioPaymentLineCatDesc(driver, "Room Charge", "Group Rate");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyFolioPaymentLineCatDesc(driver, PaymentMethod, detail);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyPaymentInfoDetailInFolio(driver, AccountFirstName, AccountLastName, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Not displaying guest details when we create reservation from Group -> Rooming list.");
			test_steps.add("Not displaying guest details when we create reservation from Group -> Rooming list."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-8905' target='_blank'>"
					+ "Verified : NG-8905 </a><br/>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Contry in Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Contry in Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String resNo2 = null;
		try {
			group.navigateRoomBlock(driver, test);

			String beforePickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_RoomPickup(driver, AccountFirstName, AccountLastName, RoomClass,
					Amount, Country3, State3, City3, PaymentMethod, AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			
			getTest_Steps.clear();
			getTest_Steps = group.VerifyRoomingListPopup_RoomPickup(driver, Country3, State3, City3, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);
						
			String roomNo = group.getSelectedRoomNo(driver);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			
			driver.switchTo().frame("dialog-body1");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListSummaryInfo(driver, BlockName, "Reserved", "100");
			test_steps.addAll(getTest_Steps);

			resNo2 = group.pickUp_getResNo(driver);
			Utility.app_logs.info("Created Reservation No : " + resNo2);
			test_steps.add("Created Reservation No : " + resNo2);

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListPickUpSummary_Reservation(driver, RoomClass, roomNo, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpCloseClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			Wait.wait3Second();
			group.loadingImage(driver);
			
			String afterPickupValue = group.getPickUpValue(driver, RoomClass);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			assertEquals(Integer.parseInt(afterPickupValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed PickUp Value Not increased");
			Utility.app_logs.info("Successfully Verified Pick Up Increased Value");
			test_steps.add("Successfully Verified Pick Up Increased Value");

			getTest_Steps.clear();
			getTest_Steps = group.verifyArriveDepartDate_RoomBlockDetail(driver);
			test_steps.addAll(getTest_Steps);

			String status = group.getStatus_RoomBlockDetatil(driver);
			assertEquals(status, "Status: Reserved", "Failed Status RoomBlock Detail");
			Utility.app_logs.info("Successfully Verified Room Block Detail Status  : " + status);
			test_steps.add("Successfully Verified Room Block Detail Status  : " + status);

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			assertEquals(pickUpPercentage, "100", "Failed PickUp Percentage RoomBlock Detail");
			Utility.app_logs.info("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify PickUp Detail", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify PickUp Detail", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {
			Nav.Reservation_Backward(driver);
			test_steps.add("Navigate to Reservation");
			app_logs.info("Navigate to Reservation");

			reservationSearch.basicSearch_WithResNumber(driver, resNo2);
			test_steps.add("Reservation Successfully Searched : " + resNo2);
			app_logs.info("Reservation Successfully Searched : " + resNo2);

			getTest_Steps.clear();
			getTest_Steps = res.verifyGroupinStayInfo(driver, AccountName, true);
			test_steps.addAll(getTest_Steps);

			res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);

			getTest_Steps.clear();
			getTest_Steps = res.verifyCountryState_BillingInfo(driver, city, State, Country);
			test_steps.addAll(getTest_Steps);
			reservationPage.VerifyPaymentMethod(driver, test_steps, PaymentMethod, AccountNumber, AccountFirstName+" "+AccountLastName, ExpiryDate);
				
			reservationPage.clickFolio(driver, test_steps);
			test_steps.add("Navigate to Folio Tab");
			app_logs.info("Navigate to Folio Tab");

			getTest_Steps.clear();
			getTest_Steps=res.verifyFolioPaymentLineCatDesc(driver, "Room Charge", "Group Rate");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyFolioPaymentLineCatDesc(driver, PaymentMethod, detail);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyPaymentInfoDetailInFolio(driver, AccountFirstName, AccountLastName, PaymentMethod,
					AccountNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Detail in Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Detail in Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Close_Tab//
		try {
			reservationPage.closeReservationTab(driver);
			test_steps.add("Navigated back to Reservations page");
			app_logs.info("Navigated back to Reservations page");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed Navigate back to Reservations page", testName, "ReservationsPage",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed Navigate back to Reservations page", testName, "ReservationsPage",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		// Search Reservation With Number//
		try {

			//reservationSearch.basicSearch_WithResNumber(driver, resNo);
			reservationSearch.basicSearch_ResNumber(driver, resNo);
			test_steps.add("Validated Basic Search with Reservation number : " + resNo);
			app_logs.info("Validated Basic Search with Reservation number : " + resNo);
				
			reservationSearch.selectBulkCancel(driver);
			//reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel",test_steps);   
			test_steps.add(" Bulk Cancellation Successfull : " + resNo);
			app_logs.info(" Bulk Cancellation Successfull : " + resNo);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "System failed to validate Basic Search with Reservation number", testName,
						"BasicSearch", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "System failed to validate Basic Search with Reservation number", testName,
						"BasicSearch", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search Bulk Cancel//
		try {
			
			//reservationSearch.basicSearch_WithResNumber(driver, resNo2);
			reservationSearch.basicSearch_ResNumber(driver, resNo2);
			test_steps.add("Validated Basic Search with Reservation number : " + resNo2);
			app_logs.info("Validated Basic Search with Reservation number : " + resNo2);
			//reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel",test_steps);   
			reservationSearch.selectBulkCancel(driver);
			test_steps.add(" Bulk Cancellation Successfull : " + resNo2);
			app_logs.info(" Bulk Cancellation Successfull : " + resNo2);

		}

		catch (Exception e) {
			if (Utility.count == RetryFailedTestCases.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, " Fail to bulk cancel the Reservation ", testName, "BulkCancellation", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.count == RetryFailedTestCases.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, " Fail to bulk cancel the Reservation ", testName, "BulkCancellation", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		// account inActive
		try {
			Nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			comments="Created a group reservation from rooming list pop up with Reservation number: "+resNo+" ."
					+"Verified payment via Group Acount."
					+"Picked the selected rooms from Rooming list by making a payment.";
					  
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			statusCode.set(3, "1");
			statusCode.set(4, "1");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ChangeCountryStateInRoomingList", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
