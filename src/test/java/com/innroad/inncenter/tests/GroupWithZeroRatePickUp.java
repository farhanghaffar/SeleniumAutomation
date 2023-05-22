package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GroupWithZeroRatePickUp extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	//HashMap<String,String> groupNameAndAccount=new HashMap<String,String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	
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
	public void groupWithZeroRatePickUp(String TestCaseID,String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight, String ratePlan, String Amount, String salutation, String guestFirstName, String guestLastName,
			String paymentMethod, String cardNumber, String nameOnCard, String isGroupPolicyApplicable,  String reservationType,
			String checkInDate,String checkOutDate,String RoomClassName, String RoomClassAbb)
			throws Exception {

		String testName = "GroupWithZeroRatePickUp";
		String test_description = "Groups with $0 rate pick up.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554195' target='_blank'>"
				+ "Click here to open TestRail: C554195</a><br/>";
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
		CPReservationPage reservationPage= new CPReservationPage();
		RoomClass room_class = new RoomClass();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> rooms= new ArrayList<String>();
		String reservationNo=null;
		BlockName=BlockName+ Utility.GenerateRandomString15Digit();
		String AccountNo = "0";
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("825371");
			caseId.add("824754");
			caseId.add("825018");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}

			if (!(Utility.validateInput(checkInDate)) && !(Utility.validateInput(checkOutDate))) {

				if (AccountFirstName.split("\\|").length > 1) {

					for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {

						checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

								ratesConfig.getProperty("monthDateYearFormat"),
								ratesConfig.getProperty("defaultDateFormat")));

					}

				} else

				{

					checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

							ratesConfig.getProperty("monthDateYearFormat"),
							ratesConfig.getProperty("defaultDateFormat")));

				}

			}
		
		try {
			test_steps.add("=====================Create new Room Class=======================");
			app_logs.info("=====================Create new Room Class=======================");
			Nav.Setup(driver);
			Nav.RoomClass(driver);
			RoomClassName = (RoomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");

			app_logs.info("try");
			Nav.NewRoomClass1(driver);

			getTest_Steps.clear();
			room_class.roomClassInfoNewPage1(driver, RoomClassName, RoomClassAbb, "2", "4", "6",
					"5", test);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
			app_logs.info("Sccessfully Created New RoomClass" + RoomClassName + " Abb : " + RoomClassAbb);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Attach RoomClass to a Rate plan

		try {
			test_steps.add("=====================Attach RoomClass to a Rate plan=======================");
			app_logs.info("=====================Attach RoomClass to a Rate plan=======================");
			Nav.inventory_Backward_1(driver);
			Nav.Inventory_Ratesgrid_Tab(driver, getTest_Steps);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlan);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickEditIcon(driver, test_steps);
			rateGrid.verifyRatePlaninEditMode(driver, test_steps, ratePlan);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, getTest_Steps, checkInDates.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.addExtraRoomClassInSeason1(driver, getTest_Steps, "Yes", RoomClassName,
					"No", "0", "0", "0", "0", "0",
					"0");
			nightlyRate.clickSaveSason(driver, getTest_Steps);
			nightlyRate.clickSaveRatePlanButton(driver, getTest_Steps);

			test_steps.addAll(getTest_Steps);
			test_steps.add("Enter all require details and save");
			app_logs.info("Enter all require details and save");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		// Navigate to Groups
		try {
			Nav.cpReservation_Backward(driver);
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

		// Search GRoup
	try {			
			//groupNameAndAccount=group.searchAccount(driver,test_steps);
			//app_logs.info(groupNameAndAccount);
		
			test_steps.add("================Create New Group================");
			app_logs.info("=================Create New Group=================");
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

		// Create RoomBlock

		try {
			test_steps.add("================Create Room Block================");
			app_logs.info("=================Create Room Block=================");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
			getTest_Steps = group.createBlockWithRate(driver, BlockName, RoomPerNight, ratePlan);
			test_steps.addAll(getTest_Steps);
			
			/*getTest_Steps.clear();
			getTest_Steps = group.verifyRoomQGRAndTotal(driver, "0", "0");
			test_steps.addAll(getTest_Steps);*/
			
			getTest_Steps.clear();
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, RoomPerNight);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			
			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);
			assertEquals(expectedRevenueDetail, "0.00","Failed Expected Revenue not Matched");

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			String foundBeforeNightlyRate = group.getNightlyRateValue(driver, RoomClassName);
			Utility.app_logs.info("Nightly Rate Before Edit  : " + foundBeforeNightlyRate);
			test_steps.add("Nightly Rate Before Edit  : " + foundBeforeNightlyRate);
			assertEquals(Float.parseFloat(foundBeforeNightlyRate), Float.parseFloat("0"), "Failed Nightly Rate Not Matched");
			
			String foundBeforeTotalRate = group.getTotalRateValue(driver, RoomClassName);
			Utility.app_logs.info("Nightly Rate Before Edit  : " + foundBeforeTotalRate);
			test_steps.add("Nightly Rate Before Edit  : " + foundBeforeTotalRate);
			assertEquals(Float.parseFloat(foundBeforeTotalRate), Float.parseFloat("0"), "Failed Total Rate Not Matched");
			
			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");
			
			getTest_Steps.clear();
			getTest_Steps = group.changeRaeBlockedRoom(driver,RoomClassName,Amount);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			
			String foundNightlyRate = group.getNightlyRateValue(driver, RoomClassName);
			Utility.app_logs.info("Nightly Rate After Edit  : " + foundNightlyRate);
			test_steps.add("Nightly Rate After Edit  : " + foundNightlyRate);
			assertEquals(Float.parseFloat(foundNightlyRate), Float.parseFloat(Amount), "Failed Nightly Rate Not Matched");
			
			String foundTotalRate = group.getTotalRateValue(driver, RoomClassName);
			Utility.app_logs.info("Nightly Rate After Edit  : " + foundTotalRate);
			test_steps.add("Nightly Rate After Edit  : " + foundTotalRate);
			assertEquals(Float.parseFloat(foundTotalRate), Float.parseFloat(Amount), "Failed Total Rate Not Matched");

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

		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			
			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		//Create Reservation
		try {	
			test_steps.add("================Creating Reservation from Book Icon================");
			app_logs.info("=================Creating Reservation from Book Icon=================");
			reservationPage.verifyGroupName_ReservationHeader(driver, AccountName,test_steps);
			comments="Verified Group name is showing in Reservation header: "+AccountName;
			test_steps.add("Verified Group name is showing in Reservation header: "+AccountName);
			Utility.app_logs.info("Verified Group name is showing in Reservation header: "+AccountName);
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			reservationPage.createGroupReservation(driver, test_steps, RoomClassName, config.getProperty("flagOn"),"",salutation, guestFirstName, guestLastName, config.getProperty("flagOff"),paymentMethod, cardNumber, nameOnCard, expiryDate, isGroupPolicyApplicable,  reservationType, rooms, reservationNo);
			getTest_Steps.clear();
			getTest_Steps = res.verifyGroupinStayInfo(driver,AccountName , true);
			test_steps.addAll(getTest_Steps);
			res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);

		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	
		// Navigate to Groups and Search Account
		try {
			Nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName,  AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
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
		String afterAvailableRoom=null;
		// Room Block
		try {
			group.navigateRoomBlock(driver, test);
			String afterPickUpValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickUpValue);
			test_steps.add("After Pickup Value : " + afterPickUpValue);
			assertEquals(Integer.parseInt(afterPickUpValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed : PickUpValue not Increased");

			afterAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + afterAvailableRoom);
			test_steps.add("After Available Rooms : " + afterAvailableRoom);
			assertEquals(Integer.parseInt(afterAvailableRoom), Integer.parseInt(beforeAvailableRoom) ,
					"Failed : Available room count not Decreased");
			comments=comments+". Verified that Group block available counts are adjusted to account for the booked room";
			Utility.app_logs.info("Verified that Group block available counts are adjusted to account for the booked room");
			test_steps.add("Verified that Group block available counts are adjusted to account for the booked room");
			String afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
			test_steps.add("After BookIcon Class : " + afterBookIconClass);
			assertEquals(afterBookIconClass, "bookyellow","Failed : Book Color Not Matched");
			
			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			assertEquals(pickUpPercentage, "100","Failed PickUp Percentage Not Matched");
			
			Utility.app_logs.info("Pick up percentage is incorrect when reservation is picked up from group block");
			test_steps.add("Pick up percentage is incorrect when reservation is picked up from group block"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7399' target='_blank'>"
					+ "Verified : NG-7006 </a><br/>");
			comments=comments+". Verified Groups with $0 rate pick up.";
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		
		try {

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("================Change Account to inactive================");
			app_logs.info("=================Change Account to inactive=================");
			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);
			
		/*	comments="Verified group name:"+groupNameAndAccount.get("GroupName")+ "in reservation header. "
					 +"Opended an existing Group and Block. "
					 +"Guest Info is prepopulated as per group account name. "
					 +"Verified the availability when the reservation is picked up from group search page. "
					 +"Verified group with zero rate pick up. "
					 +"Verified the reservation screen when the user clicked on Group pick up icon.";
			
			
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			statusCode.set(3, "1");
			statusCode.set(4, "1");
			statusCode.set(5, "1");
			statusCode.set(6, "1");
			statusCode.set(7, "1");
			statusCode.set(8, "1");*/
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

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("GroupWithZeroRatePickUp", BEExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
