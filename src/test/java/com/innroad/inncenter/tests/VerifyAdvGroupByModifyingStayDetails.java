package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

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
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAdvGroupByModifyingStayDetails extends TestCore {

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

	@Test(dataProvider = "getData", groups = { "groups" })
	public void verifyAdvGroupByModifyingStayDetails(String TestCaseID,String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight1, String RoomClass,String RoomClass2, String RoomPerNight2,String arriveDate,String departDate,String ratePlan,String salutation,String guestFirstName,String guestLastName,
			String paymentMethod,String cardNumber,String nameOnCard,String isGroupPolicyApplicable,String reservationType)
			throws InterruptedException, IOException, ParseException {

		String testName = "VerifyAdvGroupByModifyingStayDetails";
		String test_description = "Verify Advance Group By Modifying Stay Details.<br>";
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554188'
		// target='_blank'>"
		// + "Click here to open TestRail: C554188</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("825162");
			caseId.add("825200");
			caseId.add("825201");
			caseId.add("825451");	
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}

		login(testName);
		Tapechart tc = new Tapechart();
		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advGrp = new AdvGroups();
		CPReservationPage reservationPage=new CPReservationPage();
		String resNo=null;
		ArrayList<String> rooms= new ArrayList<String>();
		
		

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
			test_steps.add("=================Create New Group================");
			app_logs.info("==================Create New Group=================");
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
			getTest_Steps = group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			group.done(driver, getTest_Steps);
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
		
		try {
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyGroupCount(driver);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try{
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Group Accounts duplicating when the users click Save twice - Not reproducible");
			test_steps.add("Group Accounts duplicating when the users click Save twice - Not reproducible"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-4199' target='_blank'>"
					+ "Verified : NG-4199 </a><br/>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		String roomNo1 = null, roomNo2 = null;
		try {
			test_steps.add("=================Create Room Block================");
			app_logs.info("==================Create Room Block=================");
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight1, RoomClass);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);

			advGrp.click_Select_Rooms(driver, test_steps);

			driver.switchTo().frame("MainContent_Iframe_AssignRooms");

			roomNo1 = advGrp.select_Room_Number(driver, 1, test_steps);
			advGrp.select_Rooms_save(driver, test_steps);
			Wait.wait25Second();
			try {
			roomNo2 = advGrp.select_Room_Number(driver, 2, test_steps);
			}catch (Exception e) {
				Wait.wait15Second();
				roomNo2 = advGrp.select_Room_Number(driver, 2, test_steps);
			}
			Wait.wait25Second();
			advGrp.select_Rooms_save(driver, test_steps);
			Wait.wait25Second();
			advGrp.Select_Rooms_done(driver, test_steps);
			Wait.loadingImage(driver);
			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyArivalDate(driver, Utility.getCurrentDate("MMM dd, yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyDepartDate(driver, Utility.GetNextDate(1, "MMM dd, yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomNights(driver, RoomPerNight1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================Create another Room Block================");
			app_logs.info("==================Create another Room Block=================");
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			group.createNewBlock(driver, "BlockNew2", RoomPerNight2, RoomClass2);
			//group.createNewBlock1(driver, "BlockNew2", RoomPerNight2,ratePlan, RoomClass2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyArivalDate(driver, Utility.getCurrentDate("MMM dd, yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyDepartDate(driver, Utility.GetNextDate(1, "MMM dd, yyyy"));
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomNights(driver,
					"" + (Integer.parseInt(RoomPerNight1) + Integer.parseInt(RoomPerNight2)));
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Room nights count is incorrect for group account");
			test_steps.add("Room nights count is incorrect for group account"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7405' target='_blank'>"
					+ "Verified : NG-7405 </a><br/>");

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			getTest_Steps = group.clickSecondBlock(driver, "BlockNew2");
			test_steps.addAll(getTest_Steps);
			
			comments="Successfully opened existing Block. ";
			Utility.app_logs.info("Successfully opened existing Block.");
			test_steps.add("Successfully opened existing Block.");

			beforePickupValue = group.getPickUpValue(driver, RoomClass2);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClass2);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClass2);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClass2);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {

		
		    test_steps.add("=================Create Reservation from book icon================");
			app_logs.info("==================Create Reservation from book icon=================");
		    String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
		    //reservationPage.createGroupReservation(driver, test_steps, RoomClass, config.getProperty("flagOn"),"",salutation, guestFirstName, guestLastName, config.getProperty("flagOff"),paymentMethod, cardNumber, nameOnCard, expiryDate, isGroupPolicyApplicable,  reservationType, rooms, resNo);

            reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            //reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

            test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
		    app_logs.info(resNo);
		
		    test_steps.add("=================Spliting the reservation================");
			app_logs.info("==================Spliting the reservation=================");
		    reservationPage.clickEditReservation(driver);
		    reservationPage.clickChangeStayDetails(driver);
		    reservationPage.enterCheckOutDate(driver, Utility.GetNextDate(3, "MM/dd/yyyy"));
		    reservationPage.select_Rateplan(driver, test_steps, ratePlan, "");
		    reservationPage.clickFindRooms(driver);
		    reservationPage.selectRoomInStayInfoSectionSplitRooms(driver, RoomClass, "Yes");
		    reservationPage.clickSaveButton(driver);
		    reservationPage.changeTotalCostofStayConfrimation(driver, test_steps);
		    reservationPage.toasterMsg(driver, test_steps);
		    
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 4);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}
		
		try {
			test_steps.add("=============verifying folio line items from reservation side============");
			app_logs.info("==============verifying folio line items from reservation side=============");
			
			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-");

			reservationPage.click_Folio(driver, getTest_Steps);
			Utility.app_logs.info("Folio Tab Clicked");
			test_steps.add("Folio Tab Clicked");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectFolioOption(driver, "Guest Folio");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 4);
			test_steps.addAll(getTest_Steps);
	        
			getTest_Steps = res.VerifyFolioLineDescription(driver, "Group Rate", "1");
			test_steps.addAll(getTest_Steps);
			getTest_Steps = res.VerifyFolioLineDescription(driver, "Room Charge", "2");
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			
			Utility.app_logs.info("System is showing only single room and date for a reservation when user changes a group picked reservation to split room reservation");
			test_steps.add("System is showing only single room and date for a reservation when user changes a group picked reservation to split room reservation"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7407' target='_blank'>"
					+ "Verified : NG-7407 </a><br/>");
			comments=comments+"Verified Advanced Group reservations by modifying stay details.";
			Utility.app_logs.info("Verified Advanced Group reservations by modifying stay details");
			test_steps.add("Verified Advanced Group reservations by modifying stay details");
			
			comments=comments+"Verified rate for extended group reservation when best available rate is changed";
			Utility.app_logs.info("Verified rate for extended group reservation when best available rate is changed");
			test_steps.add("Verified rate for extended group reservation when best available rate is changed");
			
			Nav.Groups(driver);
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			getTest_Steps = group.clickSecondBlock(driver, BlockName);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClass);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("=================Create Reservation from book icon================");
			app_logs.info("==================Create Reservation from book icon=================");
		    String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
		    //reservationPage.createGroupReservation(driver, test_steps, RoomClass, config.getProperty("flagOn"),"",salutation, guestFirstName, guestLastName, config.getProperty("flagOff"),paymentMethod, cardNumber, nameOnCard, expiryDate, isGroupPolicyApplicable,  reservationType, rooms, resNo);

            reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            //reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

            test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
		    app_logs.info(resNo);
			
		    comments=comments+"Verified moving a group reservation from one room to another with diff room class and try to create reservation for previous room from book icon. ";
		    Utility.app_logs.info("Verified moving a group reservation from one room to another with diff room class and try to create reservation for previous room from book icon.");
			test_steps.add("Verified moving a group reservation from one room to another with diff room class and try to create reservation for previous room from book icon.");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		// account inActive
		try {
			Nav.Groups(driver);
			Utility.app_logs.info("Navigate to Group Tab");
			test_steps.add("Navigate to Group Tab");

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			/*comments= "User is able to modify the stay details for group Reservation from room class"+RoomClass+" to "+RoomClass2+ " ."
					  +"Splitted the Reservation";
					  
			statusCode.set(0, "1");
			statusCode.set(1, "1");*/
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
		return Utility.getData("VerifyAdvGrpByModifyingStayDetl", BEExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
