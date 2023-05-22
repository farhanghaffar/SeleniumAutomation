package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyMRBReservationInGroups extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
/*	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}*/

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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			e.printStackTrace();
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
	public void verifyAdvanceGroupMRBReservation(String TestCaseID,String checkInDate, String checkOutDate,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String BlockName, String RoomPerNight, String roomClassName, String RoomClassAbb, String bedsCount,
			String maxAdults, String maxPersons, String roomQuantity, String RateName, String BaseAmount,
			String AddtionalAdult, String AdditionalChild, String DisplayName, String AssociateSession,
			String RatePolicy, String RateDescription, String PrimaryGuestFirstName, String PrimaryGuestLastName,
			String SecondaryGuestFirstName, String SecondaryGuestLastName) throws Exception {

		if(!Utility.validateString(TestCaseID)) {
			caseId.add("785601");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
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

		String testName = "VerifyAdvanceGroupMRBReservation";
		String test_description = "Verify workflow for creating MRB from Group Block.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682475' target='_blank'>"
				+ "Click here to open TestRail: C682475</a><br/>";
		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);
		String backupBaseRate = BaseAmount;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		app_logs.info(Utility.getCurrentDate("MMM d") + " " + Utility.GetNextDate(1, "MMM d"));
		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		CPReservationPage res = new CPReservationPage();
		Navigation navigation = new Navigation();
		RoomClass roomClass = new RoomClass();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Rate rate = new Rate();
		Folio folio = new Folio();
		AdvGroups advGrp = new AdvGroups();
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();		

		// roomClassName = "GroupMRBSuite2342020104820";
		Account acc = new Account();
	
		try {
			app_logs.info("==========ROOM CLASS CREATION==========");
			test_steps.add("==========ROOM CLASS CREATION==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			test_steps.add("Navigate Setup");
			
			navigation.RoomClass(driver);
			test_steps.add("Navigate to Room Class");
			app_logs.info("Navigate to Room Class");
			roomClassName = (roomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");

			try {
				newRoomClass.createRoomClassV2(driver, test_steps, roomClassName, RoomClassAbb, maxAdults, maxPersons, roomQuantity);
				getTestSteps.clear();
				getTestSteps = newRoomClass.closeRoomClassTabV2(driver, roomClassName);
				test_steps.addAll(getTestSteps);
			}
			catch(Exception e) {				
					
				navigation.clickOnNewRoomClass(driver);
				
				getTestSteps.clear();
				getTestSteps = roomClass.createRoomClass(driver, roomClassName, RoomClassAbb, 
						bedsCount, maxAdults,
						maxPersons, roomQuantity, test, getTestSteps);
				test_steps.addAll(getTestSteps);

				roomClass.closeRoomClassTab(driver);
				test_steps.add("Close created room class tab");
			}
									
			test_steps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + RoomClassAbb);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + RoomClassAbb);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass
		try {
			app_logs.info("==========ASSOCIATE ROOM CLASS TO  RATE==========");
			test_steps.add("==========ASSOCIATE ROOM CLASS TO  RATE==========");
			try {
				navigation.cpReservationBackward(driver);
			} catch (Exception e) {
				navigation.Reservation_Backward_3(driver);
			}
			navigation.Inventory(driver);
			test_steps.add("Navigate to Inventory");
			app_logs.info("Navigate to Inventory");
			Nav.Inventory_Ratesgrid_Tab(driver, test_steps);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RateName);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickEditIcon(driver, test_steps);
			rateGrid.verifyRatePlaninEditMode(driver, test_steps, RateName);
			nightlyRate.switchCalendarTab(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, checkInDates.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, test_steps);
			nightlyRate.addExtraRoomClassInSeason1(driver, test_steps, "Yes", roomClassName,
					"No", BaseAmount, "0", "0", "0", "0",
					"0");
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);			
			test_steps.add("Enter all require details and save");
			app_logs.info("Enter all require details and save");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			app_logs.info("==========CREATE GROUP ACCOUNT==========");
			test_steps.add("==========CREATE GROUP ACCOUNT==========");
			Nav.cpReservation_Backward(driver);
			Nav.Groups(driver);

			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			group.type_GroupName(driver, test, AccountName, getTestSteps);
			test_steps.addAll(getTestSteps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTestSteps);
			test_steps.addAll(getTestSteps);
			
			AccountFirstName = AccountFirstName + Utility.GenerateRandomNumber();
			AccountLastName = AccountLastName + Utility.GenerateRandomNumber();
			
			getTestSteps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTestSteps);
			test_steps.addAll(getTestSteps);
			getTestSteps.clear();
			group.billinginfo(driver, test, getTestSteps);
			test_steps.addAll(getTestSteps);
			getTestSteps.clear();
			group.save(driver, test, getTestSteps);
			test_steps.addAll(getTestSteps);

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
			app_logs.info("==========CREATE ROOM BLOCK==========");
			test_steps.add("==========CREATE ROOM BLOCK==========");
			
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			
			BlockName = BlockName + Utility.GenerateRandomNumber();
			
			getTestSteps.clear();
			getTestSteps = group.createNewBlock(driver, BlockName, RoomPerNight, roomClassName);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.clickCreateBlock(driver);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			group.save(driver, test, getTestSteps);
			test_steps.addAll(getTestSteps);
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========VERIFY BLOCK DETAIL==========");
			test_steps.add("==========VERIFY BLOCK DETAIL==========");

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Total Room Nights Not Matched");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			assertEquals(pickUpPercentage, "0","Failed To Verify PickUp Percentage");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}
		
		try {
			String BookIconClass = group.getBookIconClass(driver, roomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + BookIconClass);
			test_steps.add("Before BookIcon Class : " + BookIconClass);
			assertEquals(BookIconClass, "book", "Book Icon Color Not Matched");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}
	

		// navigate Group reservation tab
		try {
			getTestSteps.clear();
			advGrp.click_GroupsReservationTab(driver, getTestSteps);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.verifyReservationCount(driver, 0);
			test_steps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========BOOK ICON CLICK==========");
			test_steps.add("==========BOOK ICON CLICK==========");
			
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			getTestSteps.clear();
			getTestSteps = group.bookIconClick(driver, roomClassName);
			test_steps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String resNo = null, status = null;
		try {
			app_logs.info("==========CREATE RESERVATION==========");
			test_steps.add("==========CREATE RESERVATION==========");
			
			getTestSteps.clear();
			getTestSteps = res.verifyAssociatedAccount_ResHeader(driver, AccountName);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.clickPencilIcon_GoBackToStayInfo(driver);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.clickAddSRoomButtton(driver);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.clickOnFindRooms(driver, getTestSteps);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			res.selectRoomNumber(driver, roomClassName, "1", false, getTestSteps);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.clickSelectRoomButtton(driver, roomClassName);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.newPolicesApplicableYesBtn(driver);
			test_steps.addAll(getTestSteps);

			Wait.waitForCPReservationLoading(driver);

			getTestSteps.clear();
			res.selectRoomNumber(driver, roomClassName, "2", false, getTestSteps);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.clickSelectRoomButtton(driver, roomClassName);
			test_steps.addAll(getTestSteps);

			res.clickNext(driver, test_steps);

			getTestSteps.clear();
			getTestSteps = res.selectPrimaryRoom(driver, roomClassName, "1");
			test_steps.addAll(getTestSteps);

			PrimaryGuestFirstName = PrimaryGuestFirstName + Utility.GenerateRandomNumber();
			PrimaryGuestLastName = PrimaryGuestLastName + Utility.GenerateRandomNumber();
			
//			getTest_Steps.clear();
//			getTest_Steps = res.selectSalutation(driver, "Mr.");
//			test_steps.addAll(getTest_Steps);
			
			getTestSteps.clear();
			getTestSteps = res.enterPrimaryGuestName(driver, PrimaryGuestFirstName, PrimaryGuestLastName);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = res.selectAdditionalRoom(driver, roomClassName, "2");
			test_steps.addAll(getTestSteps);

			SecondaryGuestFirstName = SecondaryGuestFirstName + Utility.GenerateRandomNumber();
			SecondaryGuestLastName = SecondaryGuestLastName + Utility.GenerateRandomNumber();
			
			getTestSteps.clear();
			getTestSteps = res.enterAdditionalGuestName(driver, SecondaryGuestFirstName, SecondaryGuestLastName);
			test_steps.addAll(getTestSteps);

			res.clickBookNow(driver, test_steps);
			resNo = res.get_ReservationConfirmationNumber(driver, test_steps);
			status = res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);

			getTestSteps.clear();
			getTestSteps = res.verifyAccountName(driver, AccountName,false);
			test_steps.addAll(getTestSteps);

			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			app_logs.info("==========VERIFY GUEST DETAIL SAME AS ACCOUNT==========");
			test_steps.add("==========VERIFY GUEST DETAIL SAME AS ACCOUNT==========");
			
			getTestSteps.clear();
			getTestSteps = res.verfifyGuestinfo_ResDetail(driver, PrimaryGuestFirstName + " " + PrimaryGuestLastName, Address1, city, State, Country, Postalcode, "-", "1" + Phonenumber);
			test_steps.addAll(getTestSteps);
			
			String foundContactName = res.getContactName_ResDetail(driver);
			assertEquals(foundContactName, AccountFirstName + " " + AccountLastName,"Failed to Verrify Contact Name ");
			app_logs.info("Successfully Verified Contact Name in Guest Detail same as Accounts : " + foundContactName);
			test_steps.add("Successfully Verified Contact Name in Guest Detail same as Accounts : " + foundContactName);
			
			String foundAccount = res.getAccount_ResDetail(driver);
			assertEquals(foundAccount, AccountName,"Failed To Verify Account Name");
			app_logs.info("Successfully Verified Account Name in Guest Detail : " + foundAccount);
			test_steps.add("Successfully Verified Account Name in Guest Detail : " + foundAccount);
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			res.click_Folio(driver, test_steps);

			String folioCount = res.getFolioOptionCount(driver);
			assertEquals(folioCount, "3", "Failed to Vrify Total Folio Options Count");
			test_steps.add("Successfully Verified Folio Option Count : " + folioCount);
			Utility.app_logs.info("Successfully Verified Folio Option Count : " + folioCount);

			app_logs.info("==========VERIFY ACCOUNT FOLIO LINE ITEM==========");
			test_steps.add("==========VERIFY ACCOUNT FOLIO LINE ITEM==========");

			getTestSteps.clear();
			getTestSteps = res.selectFolioOption(driver, AccountName);
			test_steps.addAll(getTestSteps);

			int folioLineitemCount = folio.verifyLineItemCount(driver);
			assertEquals(folioLineitemCount, 0, "Failed to Vrify Total Folio Line Item Count");
			test_steps.add("Successfully Verified Group Account Folio Line Item Count : " + folioLineitemCount);
			Utility.app_logs.info("Successfully Verified Group Account Folio Line Item Count : " + folioLineitemCount);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String foundPaymentMethod = "";
		String lineCat = "Room Charge";
		try {
			getTestSteps.clear();
			getTestSteps = res.selectFolioOption(driver, 1);
			test_steps.addAll(getTestSteps);

			app_logs.info("==========VERIFY FOLIO LINE ITEM==========");
			test_steps.add("==========VERIFY FOLIO LINE ITEM==========");

			int folioLineitemCount = folio.verifyLineItemCount(driver);
			assertEquals(folioLineitemCount, 1, "Failed to Vrify Total Folio Line Item Count");
			test_steps.add("Successfully Verified Guest Folio Line Item Count : " + folioLineitemCount);
			Utility.app_logs.info("Successfully Verified Guest Folio Line Item Count : " + folioLineitemCount);

			getTestSteps.clear();
			foundPaymentMethod = folio.verifyLineItemCategory(driver, lineCat, getTestSteps);
			test_steps.addAll(getTestSteps);

			String tax = folio.getLineTaxAmount(driver, foundPaymentMethod);
			BaseAmount = (Float.parseFloat(BaseAmount) + Float.parseFloat(tax)) + "";
			getTestSteps.clear();
			getTestSteps = folio.verifyLineItemDate(driver, foundPaymentMethod);
			test_steps.addAll(getTestSteps);

//			getTest_Steps.clear();
//			getTest_Steps = folio.verifyLineItemDesc(driver, foundPaymentMethod,DisplayName);
//			test_steps.addAll(getTest_Steps);
//			
			getTestSteps.clear();
			getTestSteps = folio.verifyLineItemAmount(driver, foundPaymentMethod, BaseAmount);
			test_steps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTestSteps.clear();
			getTestSteps = res.selectFolioOption(driver, 2);
			test_steps.addAll(getTestSteps);

			app_logs.info("==========VERIFY FOLIO LINE ITEM==========");
			test_steps.add("==========VERIFY FOLIO LINE ITEM==========");

			int folioLineitemCount = folio.verifyLineItemCount(driver);
			assertEquals(folioLineitemCount, 1, "Failed to Vrify Total Folio Line Item Count");
			test_steps.add("Successfully Verified Guest Folio Line Item Count : " + folioLineitemCount);
			Utility.app_logs.info("Successfully Verified Guest Folio Line Item Count : " + folioLineitemCount);

			getTestSteps.clear();
			foundPaymentMethod = folio.verifyLineItemCategory(driver, lineCat, getTestSteps);
			test_steps.addAll(getTestSteps);

			// String tax = folio.getLineTaxAmount(driver, foundPaymentMethod);
			// BaseAmount = (Float.parseFloat(BaseAmount)+Float.parseFloat(tax))+"";
			getTestSteps.clear();
			getTestSteps = folio.verifyLineItemDate(driver, foundPaymentMethod);
			test_steps.addAll(getTestSteps);

//			getTest_Steps.clear();
//			getTest_Steps = folio.verifyLineItemDesc(driver, foundPaymentMethod,DisplayName);
//			test_steps.addAll(getTest_Steps);

			getTestSteps.clear();
			getTestSteps = folio.verifyLineItemAmount(driver, foundPaymentMethod, BaseAmount);
			test_steps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			 	res.click_DeatilsTab(driver, test_steps);
			 	app_logs.info("==========VERIFY RESERVATION HEADER DETAIL==========");
				test_steps.add("==========VERIFY RESERVATION HEADER DETAIL==========");
				
				getTestSteps.clear();
				getTestSteps = res.verifyGuestNameAfterReservation(driver, PrimaryGuestFirstName + " " + PrimaryGuestLastName);
				test_steps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = res.verifyAccountName(driver, AccountName,false);
				test_steps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = res.verifyStatusAfterReservation(driver, status);
				test_steps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = res.verifyConfirmationNoAfterReservation(driver, resNo);
				test_steps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = res.verifyStayDateAfterReservation(driver, Utility.getCurrentDate("MMM d"), Utility.GetNextDate(1, "MMM d"));
				test_steps.addAll(getTestSteps);
				
				String foundTripTotalHeader = res.getTripTotal_Header(driver);
				assertEquals(Float.parseFloat(foundTripTotalHeader), (Float.parseFloat(BaseAmount)*2), "Failed TripTotal Missmatched");
				Utility.app_logs.info("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
				test_steps.add("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
				
				String foundBalanceHeader = res.getBalance_Header(driver);
				assertEquals(Float.parseFloat(foundBalanceHeader), (Float.parseFloat(BaseAmount)*2), "Failed Balance Missmatched");
				Utility.app_logs.info("Successfully Verified Header Balance : " + foundBalanceHeader);
				test_steps.add("Successfully Verified Header Balance : " + foundBalanceHeader);
				
				app_logs.info("==========VERIFY RESERVATION TRIP SUMMARY==========");
				test_steps.add("==========VERIFY RESERVATION TRIP SUMMARY==========");
				
				//String foundRoomCharge = res.getRoomCharge_TripSummary(driver);
				String foundRoomCharge = res.getRoomChargesInTripSummary(driver);
				foundRoomCharge = Utility.RemoveDollarandSpaces(driver, foundRoomCharge);
				app_logs.info("foundRoomCharge : " + foundRoomCharge);
				assertEquals(Float.parseFloat(foundRoomCharge), (Float.parseFloat(backupBaseRate)*2), "Failed RoomCahrge Missmatched");
				Utility.app_logs.info("Successfully Verified RoomCharge : " + foundRoomCharge);
				test_steps.add("Successfully Verified RoomCharge : " + foundRoomCharge);
				
			//	String foundTripTotal = res.getTripTotal_TripSummary(driver);
				String foundTripTotal = res.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
				assertEquals(Float.parseFloat(foundTripTotal.trim()), (Float.parseFloat(BaseAmount)*2), "Failed TripTotal Missmatched");
				Utility.app_logs.info("Successfully Verified TripTotal : " + foundTripTotal);
				test_steps.add("Successfully Verified TripTotal : " + foundTripTotal);
				
				//String foundBalance = res.getBalance_TripSummary(driver);
				String foundBalance = res.getBalanceInTripSummary(driver);
				foundBalance = Utility.RemoveDollarandSpaces(driver, foundBalance);				
				assertEquals(Float.parseFloat(foundBalance), (Float.parseFloat(BaseAmount)*2), "Failed Balance Missmatched");
				Utility.app_logs.info("Successfully Verified Balance : " + foundBalance);
				test_steps.add("Successfully Verified Balance : " + foundBalance);
				
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed  ", testName, "GroupCPReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "GroupCPReservation", driver);
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

		// searching and navigate to Room Block
		try {
			app_logs.info("==========GROUP SEARCHING==========");
			test_steps.add("==========GROUP SEARCHING==========");
			
			getTestSteps.clear();
			getTestSteps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTestSteps);

			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String PickupValue = null;
		String AvailableRoom = null;
		String BookIconClass = null;
		try {

			app_logs.info("==========VERIFING BLOCK DETAIL AFTER RESERVATION==========");
			test_steps.add("==========VERIFING BLOCK DETAIL AFTER RESERVATION==========");
			
			PickupValue = group.getPickUpValue(driver, roomClassName);
			Utility.app_logs.info("After Pickup Value : " + PickupValue);
			test_steps.add("After Pickup Value : " + PickupValue);
			assertEquals(Integer.parseInt(PickupValue), 2, "Failed PickUp Value Not increased");

			BookIconClass = group.getBookIconClass(driver, roomClassName);
			Utility.app_logs.info("After BookIcon Class : " + BookIconClass);
			test_steps.add("After BookIcon Class : " + BookIconClass);
			assertEquals(BookIconClass, "bookred", "Book Icon Color Not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			AvailableRoom = group.getAvailableRooms(driver, roomClassName);
			Utility.app_logs.info("After Available Rooms : " + AvailableRoom);
			test_steps.add("After Available Rooms : " + AvailableRoom);
			assertEquals(Integer.parseInt(AvailableRoom), 0, "Failed to verfy Availalbe Rooms not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}


		// navigate Group reservation tab
		try {
			app_logs.info("==========VERIFING RESERVATION RECORD==========");
			test_steps.add("==========VERIFING RESERVATION RECORD==========");
			
			getTestSteps.clear();
			advGrp.click_GroupsReservationTab(driver, getTestSteps);
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.verifyReservationCount(driver, 2);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = group.verifyReservationLine(driver, resNo, PrimaryGuestFirstName + " " + PrimaryGuestLastName, BlockName, "1", "0", status, RoomClassAbb + " : 1", Utility.getCurrentDate("MMM d, yyyy"), Utility.GetNextDate(1, "MMM d, yyyy"), "1");
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = group.verifyReservationLine(driver, resNo, SecondaryGuestFirstName + " " + SecondaryGuestLastName, BlockName, "1", "0", status, RoomClassAbb + " : 2", Utility.getCurrentDate("MMM d, yyyy"), Utility.GetNextDate(1, "MMM d, yyyy"), "1");
			test_steps.addAll(getTestSteps);
			
			app_logs.info("==========OPEN FIRST RESERVATION RECORD==========");
			test_steps.add("==========OPEN FIRST RESERVATION RECORD==========");
			
			getTestSteps.clear();
			getTestSteps = group.openResDialogFromResTab(driver, 1);
			test_steps.addAll(getTestSteps);

			Wait.explicit_wait_visibilityof_webelement_600(driver.findElement(By.xpath("//*[@id='dialog-body0']")),
					driver);
			driver.switchTo().frame("dialog-body0");
			
			app_logs.info("==========VERIFY RESERVATION HEADER DETAIL==========");
			test_steps.add("==========VERIFY RESERVATION HEADER DETAIL==========");
			
			getTestSteps.clear();
			getTestSteps = res.verifyGuestNameAfterReservation(driver, PrimaryGuestFirstName + " " + PrimaryGuestLastName);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyAccountName(driver, AccountName,false);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyStatusAfterReservation(driver, status);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyConfirmationNoAfterReservation(driver, resNo);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyStayDateAfterReservation(driver, Utility.getCurrentDate("MMM d"), Utility.GetNextDate(1, "MMM d"));
			test_steps.addAll(getTestSteps);
			
			String foundTripTotalHeader = res.getTripTotal_Header(driver);
			assertEquals(Float.parseFloat(foundTripTotalHeader), (Float.parseFloat(BaseAmount)*2), "Failed TripTotal Missmatched");
			Utility.app_logs.info("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
			test_steps.add("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
			
			String foundBalanceHeader = res.getBalance_Header(driver);
			assertEquals(Float.parseFloat(foundBalanceHeader), (Float.parseFloat(BaseAmount)*2), "Failed Balance Missmatched");
			Utility.app_logs.info("Successfully Verified Header Balance : " + foundBalanceHeader);
			test_steps.add("Successfully Verified Header Balance : " + foundBalanceHeader);
			
			app_logs.info("==========VERIFY RESERVATION TRIP SUMMARY==========");
			test_steps.add("==========VERIFY RESERVATION TRIP SUMMARY==========");
			
			String foundRoomCharge = res.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			assertEquals(Float.parseFloat(foundRoomCharge), (Float.parseFloat(backupBaseRate)*2), "Failed RoomCahrge Missmatched");
			Utility.app_logs.info("Successfully Verified RoomCharge : " + foundRoomCharge);
			test_steps.add("Successfully Verified RoomCharge : " + foundRoomCharge);
			
			String foundTripTotal = res.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
			foundTripTotal = Utility.removeDollarBracketsAndSpaces(foundTripTotal);
			assertEquals(Float.parseFloat(foundTripTotal), (Float.parseFloat(BaseAmount)*2), "Failed TripTotal Missmatched");
			Utility.app_logs.info("Successfully Verified TripTotal : " + foundTripTotal);
			test_steps.add("Successfully Verified TripTotal : " + foundTripTotal);
			
			String foundBalance = res.getBalanceInTripSummary(driver);
			foundBalance = Utility.removeDollarBracketsAndSpaces(foundBalance);
			assertEquals(Float.parseFloat(foundBalance), (Float.parseFloat(BaseAmount)*2), "Failed Balance Missmatched");
			Utility.app_logs.info("Successfully Verified Balance : " + foundBalance);
			test_steps.add("Successfully Verified Balance : " + foundBalance);
			
			driver.switchTo().defaultContent();

			getTestSteps.clear();
			getTestSteps = group.closeDialoge(driver, "dialog-close0");
			test_steps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			app_logs.info("==========OPEN SECOND RESERVATION RECORD==========");
			test_steps.add("==========OPEN SECOND RESERVATION RECORD==========");
			
			getTestSteps.clear();
			getTestSteps = group.openResDialogFromResTab(driver, 2);
			test_steps.addAll(getTestSteps);

			Wait.explicit_wait_visibilityof_webelement_600(driver.findElement(By.xpath("//*[@id='dialog-body0']")),
					driver);
			driver.switchTo().frame("dialog-body0");
			
			app_logs.info("==========VERIFY RESERVATION HEADER DETAIL==========");
			test_steps.add("==========VERIFY RESERVATION HEADER DETAIL==========");
			
			getTestSteps.clear();
			getTestSteps = res.verifyGuestNameAfterReservation(driver, PrimaryGuestFirstName + " " + PrimaryGuestLastName);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyAccountName(driver, AccountName,false);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyStatusAfterReservation(driver, status);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyConfirmationNoAfterReservation(driver, resNo);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = res.verifyStayDateAfterReservation(driver, Utility.getCurrentDate("MMM d"), Utility.GetNextDate(1, "MMM d"));
			test_steps.addAll(getTestSteps);
			
			String foundTripTotalHeader = res.getTripTotal_Header(driver);
			assertEquals(Float.parseFloat(foundTripTotalHeader), (Float.parseFloat(BaseAmount)), "Failed TripTotal Missmatched");
			Utility.app_logs.info("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
			test_steps.add("Successfully Verified Header TripTotal : " + foundTripTotalHeader);
			
			String foundBalanceHeader = res.getBalance_Header(driver);
			assertEquals(Float.parseFloat(foundBalanceHeader), (Float.parseFloat(BaseAmount)), "Failed Balance Missmatched");
			Utility.app_logs.info("Successfully Verified Header Balance : " + foundBalanceHeader);
			test_steps.add("Successfully Verified Header Balance : " + foundBalanceHeader);
			
			app_logs.info("==========VERIFY RESERVATION TRIP SUMMARY==========");
			test_steps.add("==========VERIFY RESERVATION TRIP SUMMARY==========");
			
			String foundRoomCharge = res.getRoomChargesInTripSummary(driver);
			foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
			assertEquals(Float.parseFloat(foundRoomCharge), (Float.parseFloat(backupBaseRate)), "Failed RoomCahrge Missmatched");
			Utility.app_logs.info("Successfully Verified RoomCharge : " + foundRoomCharge);
			test_steps.add("Successfully Verified RoomCharge : " + foundRoomCharge);
			
			String foundTripTotal = res.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
			foundTripTotal = Utility.removeDollarBracketsAndSpaces(foundTripTotal);
			assertEquals(Float.parseFloat(foundTripTotal), (Float.parseFloat(BaseAmount)), "Failed TripTotal Missmatched");
			Utility.app_logs.info("Successfully Verified TripTotal : " + foundTripTotal);
			test_steps.add("Successfully Verified TripTotal : " + foundTripTotal);
			
			String foundBalance = res.getBalanceInTripSummary(driver);
			foundBalance = Utility.removeDollarBracketsAndSpaces(foundBalance);
			assertEquals(Float.parseFloat(foundBalance), (Float.parseFloat(BaseAmount)), "Failed Balance Missmatched");
			Utility.app_logs.info("Successfully Verified Balance : " + foundBalance);
			test_steps.add("Successfully Verified Balance : " + foundBalance);
			
			app_logs.info("==========VERIFY ACCOUNT LINK==========");
			test_steps.add("==========VERIFY ACCOUNT LINK==========");
			
			getTestSteps.clear();
			getTestSteps = res.verifyAccountName(driver, AccountName,true);
			test_steps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps =acc.verifyAccountDetails(driver, "Group", AccountName, AccountNo, "Active");
			test_steps.addAll(getTestSteps);
			
			driver.switchTo().defaultContent();

			getTestSteps.clear();
			getTestSteps = group.closeDialoge(driver, "dialog-close0");
			test_steps.addAll(getTestSteps);

			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Resrvation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// account inActive
		try {
			getTestSteps.clear();
			group.save(driver, test, getTestSteps);
			test_steps.addAll(getTestSteps);
			
			app_logs.info("==========DELETING TEMP GROUPS, RATES AND ROOMCLASSES==========");
			test_steps.add("==========DELETING TEMP GROUPS, RATES AND ROOMCLASSES==========");
			
			getTestSteps.clear();
			getTestSteps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTestSteps);

			getTestSteps.clear();
			group.save(driver, test, getTestSteps);
			test_steps.addAll(getTestSteps);

			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete rate
		try {
			app_logs.info("==========DELETE RATE==========");
			test_steps.add("==========DELETE RATE==========");
			try {
				navigation.Inventory(driver);
				app_logs.info("Navigate Inventory");
				test_steps.add("Navigate Inventory");
			
			}catch(Exception e) {
				navigation.inventoryBackwardAdmin(driver);
				app_logs.info("Navigate Inventory");
				test_steps.add("Navigate Inventory");					
			}
			
			navigation.secondaryRatesMenuItem(driver);
			app_logs.info("Navigate Rate");
			test_steps.add("Navigate Rate");

			rate.deleteRates(driver, RateName);
			test_steps.add("New Rate has been Deleted successfully");
			app_logs.info("New Rate has been Deleted successfully");

			rate.verifyDeleteRate(driver, RateName);
			test_steps.add("Verify the Deleted Rate : " + RateName);
			app_logs.info("Verify the Deleted Rate " + RateName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Room Class
		try {

			app_logs.info("==========DELETE ROOM CLASS==========");
			test_steps.add("==========DELETE ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			test_steps.add("Navigate Setup");

			navigation.RoomClass(driver);
			app_logs.info("Navigate Room Class");
			test_steps.add("Navigate Room Class");

			getTestSteps.clear();
			getTestSteps = newRoomClass.deleteRoomClassV2(driver, roomClassName);
			test_steps.addAll(getTestSteps);

			/*comments = " Created mrb reservation from group with number (" + resNo + ")";
			statusCode.add(0, "1");*/

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);				
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyMRBReserinGroups", envLoginExcel);
		
	}


	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
	
}
