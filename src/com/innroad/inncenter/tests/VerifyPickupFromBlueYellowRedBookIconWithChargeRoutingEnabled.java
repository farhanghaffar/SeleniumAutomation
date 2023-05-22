package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.LogStatus;

public class VerifyPickupFromBlueYellowRedBookIconWithChargeRoutingEnabled extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

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
	public void verifyPickupFromBlueBookIconWithChargeRoutingEnabled(String url, String clientcode, String username,
			String password, String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String Address1, String city, String Country, String State,
			String Postalcode, String ChargeRoutingAllItem, String ChargeRoutingRoomChargeOnly, String BlockName,
			String RoomPerNight, String RoomClassAbb, String RoomClassName, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String LineCategory, String LineAmount, String checkInDate,
			String checkOutDate, String adults, String children, String rateplan, String Salutation, String CardNumber,
			String NameOnCard, String PaymentType, String RateName, String BaseAmount, String AddtionalAdult,
			String AdditionalChild, String AssociateSession, String RatePolicy, String RateDescription, String ratePlan,
			String isAddRoomClassInSeason, String isAdditionalChargesForChildrenAdults)
			throws InterruptedException, IOException {

		String testName = "VerifyPickupFromBlueYellowRedBookIconWithChargeRoutingEnabled";
		String test_description = "Create Group Account with Charge Routing Enabled.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554169' target='_blank'>"
				+ "Click here to open TestRail: C554169</a><br/>"
				+ "Pickup from Blue book button when CHarge Routing is enabled.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554170' target='_blank'>"
				+ "Click here to open TestRail: C554170</a><br/>"
				+ "Pickup from Yellow/Red book button.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554163' target='_blank'>"
				+ "Click here to open TestRail: C554163</a><br/>"
				+ "Pickup from Yellow/Red book button when Charge Routing is enabled.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554164' target='_blank'>"
				+ "Click here to open TestRail: C554164</a><br/>"
				+ "Verify pick up from red book icon when there are no rooms available for the roomclass.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554189' target='_blank'>"
				+ "Click here to open TestRail: C554189</a><br/>";
		String test_catagory = "Groups";
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
		CPReservationPage reservationPage = new CPReservationPage();
		RoomClass room_class = new RoomClass();
		Rate rate = new Rate();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();

		try {
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

			if (AccountFirstName.split("\\|").length > 1) {

				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);

				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

			} else {

				checkInDate = checkInDates.get(0);

				checkOutDate = checkOutDates.get(0);

			}

			app_logs.info(checkInDate);

			app_logs.info(checkOutDate);
		} catch (Exception e) {

		}

		// Create new Room Class
		try {
			test_steps.add("=====================Create new Room Class=======================");
			app_logs.info("=====================Create new Room Class=======================" );
			Nav.Setup(driver);
			Nav.RoomClass(driver);
			RoomClassName = (RoomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");

			app_logs.info("try");
			Nav.NewRoomClass1(driver);

			getTest_Steps.clear();
			room_class.roomClassInfoNewPage1(driver, RoomClassName, RoomClassAbb, bedsCount, maxAdults, maxPersons,
					roomQuantity, test);
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
			app_logs.info("=====================Attach RoomClass to a Rate plan=======================" );
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
			nightlyRate.addExtraRoomClassInSeason1(driver, getTest_Steps, isAddRoomClassInSeason, RoomClassName,
					isAdditionalChargesForChildrenAdults, BaseAmount, BaseAmount, maxAdults, maxPersons, AddtionalAdult,
					AdditionalChild);
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

		// Create New Groups
		String AccountNo = "0";
		try {
			test_steps.add("=====================Create New Group=======================");
			app_logs.info("=====================Create New Group=======================" );
			Nav.cpReservation_Backward(driver);
			Nav.Groups(driver);

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
		// Navigate to Folio Tab and FolioOption
		try {
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// select charge Routing to all items and verify
		try {
			test_steps.add("=================select charge Routing to all items and verify============");
			app_logs.info("=================select charge Routing to all items and verify=============" );
			System.out.println(ChargeRoutingAllItem);
			getTest_Steps.clear();
			getTest_Steps = group.selectChargeRouting(driver, ChargeRoutingAllItem);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickYesApplyCharge(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifySelectedChargeRouting(driver, ChargeRoutingAllItem);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select Charge Routing ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select Charge Routing ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomBlock

		try {
			test_steps.add("=====================Create RoomBlock================");
			app_logs.info("=====================Create RoomBlock=================" );
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, ratePlan, RoomClassName);
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

		/*
		 * // Adding Folio Line Item and Verify try { group.navigateFolio(driver, test,
		 * getTest_Steps); group.loadingImage(driver);
		 * 
		 * getTest_Steps.clear(); getTest_Steps = group.AddLineItems(driver,
		 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
		 * group.commit(driver, test); getTest_Steps.clear();
		 * 
		 * 
		 * getTest_Steps.clear(); getTest_Steps = group.verifyLineItems(driver,
		 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
		 * 
		 * 
		 * getTest_Steps.clear(); group.save(driver, test, getTest_Steps);
		 * test_steps.addAll(getTest_Steps);
		 * 
		 * getTest_Steps.clear(); group.navigateFolio(driver, test, getTest_Steps);
		 * test_steps.addAll(getTest_Steps);
		 * 
		 * getTest_Steps.clear(); getTest_Steps = group.verifyLineItems(driver,
		 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
		 * 
		 * } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Add Folio Line", testName,
		 * "Group", driver); } else { Assert.assertTrue(false); }
		 * 
		 * } catch (Error e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Add Folio Line", testName,
		 * "Group", driver); } else { Assert.assertTrue(false); } }
		 */

		try {

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

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
			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Total Room Nights Not Matched");
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
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}
		
		//add folio items in preview folio

		try {
			test_steps.add("=====================add folio items in preview folio================");
			app_logs.info("=====================add folio items in preview folio=================" );
			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			
			  getTest_Steps.clear(); getTest_Steps = group.clickRoomBlockEdit(driver);
			  test_steps.addAll(getTest_Steps);
			  
			  driver.switchTo().frame("MainContent_Iframe_accountpicker");
			  
			  getTest_Steps.clear(); getTest_Steps =
			  group.clickPreviewFolio_EditBlock(driver); test_steps.addAll(getTest_Steps);
			  
			  String qty = "1"; String lineNo = "1"; getTest_Steps.clear(); getTest_Steps =
			  group.addLineItem_PreviewFolio(driver, LineCategory, qty, LineAmount,
			  lineNo); test_steps.addAll(getTest_Steps);
			  
			  getTest_Steps.clear(); getTest_Steps = group.clickSave_PreviewFolio(driver);
			  test_steps.addAll(getTest_Steps);
			  
			  String totalCharges = group.getTotalCharges_PreviewFolio(driver);
			  Utility.app_logs.info("Preview Folio Total Charges  : " + totalCharges);
			  test_steps.add("Preview Folio Total Charges  : " + totalCharges);
			  
			  getTest_Steps.clear(); getTest_Steps = group.clickDone_PreviewFolio(driver);
			  test_steps.addAll(getTest_Steps);
			  
			  driver.switchTo().defaultContent();
			  
			  String afterExpectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			  Utility.app_logs.info("After Group Info Expected Revenue  : " +
			  afterExpectedRevenueInfo);
			  test_steps.add("After Group Info ExpectedRevenue  : " +
			  afterExpectedRevenueInfo);
			 
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

		// verify blue color icon with charge routing for all items
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			test_steps.add("=============verify blue color icon with charge routing for all items============");
			app_logs.info("==============verify blue color icon with charge routing for all items=============");
			
			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);
			assertEquals(beforeBookIconClass, "book",
					  "Book Icon Color Not Matched");

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

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


		// Create Reservation

		try {
			test_steps.add("=================Create Reservation================");
			app_logs.info("==================Create Reservation=================");
			getTest_Steps.clear();
			String expiryDate = Utility.getFutureMonthAndYearForMasterCard();
			/*
			 * reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			 * reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			 * reservationPage.enter_Adults(driver, test_steps, adults);
			 * reservationPage.enter_Children(driver, test_steps, children);
			 * reservationPage.select_Rateplan(driver, test_steps, rateplan, "");
			 * reservationPage.clickOnFindRooms(driver, test_steps);
			 * reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes", "");
			 */
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation,
					AccountFirstName, AccountLastName, config.getProperty("flagOff"));
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
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

		// after Creation of Reservation verifying folio line items from reservation side
		try {
			test_steps.add("=============verifying folio line items from reservation side============");
			app_logs.info("==============verifying folio line items from reservation side=============");
			
			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-");

			reservationPage.click_Folio(driver, getTest_Steps);
			Utility.app_logs.info("Folio Tab Clicked");
			test_steps.add("Folio Tab Clicked");

			/*
			 * getTest_Steps.clear(); getTest_Steps = res.verifyFolioSelect(driver,
			 * AccountName); test_steps.addAll(getTest_Steps);
			 */

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectFolioOption(driver, "Guest Folio");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, false, 0);
			test_steps.addAll(getTest_Steps);

			String option = AccountName;
			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectFolioOption(driver, option);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			DecimalFormat df = new DecimalFormat("0.00");
	        df.setMaximumFractionDigits(2);
	        String amount=df.format(Double.parseDouble(BaseAmount));
	        Utility.app_logs.info(amount);
	        
			getTest_Steps = res.VerifyFolioLineItem(driver, "Room Charge", amount, "1");
			test_steps.addAll(getTest_Steps);
			//Bug while adding preview folio options
			/*
			 * getTest_Steps.clear(); getTest_Steps = res.VerifyFolioLineItem(driver,
			 * LineCategory, LineAmount, "2"); test_steps.addAll(getTest_Steps);
			 */

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// searching and navigate to Room Block
		try {
			test_steps.add("=============navigate to Room Block============");
			app_logs.info("==============navigate to Room Block=============");
			Nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

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

		String afterAvailableRoom = null;
		  /*String afterPickupValue = null; String afterAvailableRoom = null; String
		  afterBookIconClass = null; try {*/
		  
	/*	  afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
		  Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
		  test_steps.add("After BookIcon Class : " + afterBookIconClass);
		  assertEquals(afterBookIconClass, beforeBookIconClass,
		  "Book Icon Color Not Matched");
		  
		  } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
		  RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		  test_steps); Utility.updateReport(e, "Failed to Process Book Icon", testName,
		  "Group", driver); } else { Assert.assertTrue(false); }
		  
		  } catch (Error e) { if (Utility.reTry.get(testName) == Utility.count) {
		  RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		  test_steps); Utility.updateReport(e, "Failed to Process Book Icon", testName,
		  "Group", driver); } else { Assert.assertTrue(false); } }*/
		 
		//verify yellow color icon with charge routing for all items
		try {
			test_steps.add("=========verify yellow color icon with charge routing for all items========");
			app_logs.info("==========verify yellow color icon with charge routing for all items=========");
			afterAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + afterAvailableRoom);
			test_steps.add("After Available Rooms : " + afterAvailableRoom);
			assertEquals(Integer.parseInt(afterAvailableRoom), Integer.parseInt(beforeAvailableRoom),
					"Failed to verfy Availalbe Rooms not Matched");
			
			String BookIcon = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + BookIcon);
			test_steps.add("After BookIcon Class : " + BookIcon);
			assertEquals(BookIcon, "bookyellow", "Book Icon Color Not Matched");

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

		// Navigate to Folio Tab and FolioOption
		try {
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// select charge Routing as room charge only and verify
		try {
			test_steps.add("=========select charge Routing as room charge only and verify========");
			app_logs.info("==========select charge Routing as room charge only and verify=========");
			System.out.println(ChargeRoutingRoomChargeOnly);
			getTest_Steps.clear();
			getTest_Steps = group.selectChargeRouting(driver, ChargeRoutingRoomChargeOnly);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickYesApplyCharge(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifySelectedChargeRouting(driver, ChargeRoutingRoomChargeOnly);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select Charge Routing ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select Charge Routing ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		//// before Creation of Reservation
		/*
		 * try { Utility.app_logs.info("Before Creation of Reservation :-");
		 * test_steps.add("Before Creation of Reservation :-");
		 * 
		 * res.FolioTab(driver); Utility.app_logs.info("Folio Tab Clicked");
		 * test_steps.add("Folio Tab Clicked");
		 * 
		 * getTest_Steps.clear(); getTest_Steps = res.verifyFolioSelect(driver,
		 * AccountName); test_steps.addAll(getTest_Steps);
		 * 
		 * getTest_Steps.clear(); getTest_Steps = res.selectFolioOption(driver,
		 * "Guest Folio"); test_steps.addAll(getTest_Steps);
		 * 
		 * } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Folio", testName,
		 * "Reservation", driver); } else { Assert.assertTrue(false); } } catch (Error
		 * e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Folio", testName,
		 * "Reservation", driver); } else { Assert.assertTrue(false); } }
		 */
		/*
		 * try { getTest_Steps.clear(); getTest_Steps = res.verifyLineitemsExist(driver,
		 * true, 1); test_steps.addAll(getTest_Steps);
		 * 
		 * } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Create Block", testName,
		 * "Group", driver); } else { Assert.assertTrue(false); }
		 * 
		 * } catch (Error e) {
		 * 
		 * test_steps.add(e.toString());
		 * 
		 * } try { getTest_Steps.clear(); getTest_Steps =
		 * res.VerifyFolioLineItem(driver, LineCategory, LineAmount, "1");
		 * test_steps.addAll(getTest_Steps);
		 * 
		 * String option = AccountName + " (" + AccountNo + ")"; getTest_Steps.clear();
		 * getTest_Steps = res.selectFolioOption(driver, option);
		 * test_steps.addAll(getTest_Steps);
		 * 
		 * getTest_Steps.clear(); getTest_Steps = res.verifyLineitemsExist(driver, true,
		 * 1); test_steps.addAll(getTest_Steps);
		 * 
		 * getTest_Steps.clear(); getTest_Steps = res.VerifyFolioLineItem(driver,
		 * "Room Charge", "250.00", "1"); test_steps.addAll(getTest_Steps);
		 * 
		 * } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Folio", testName,
		 * "Reservation", driver); } else { Assert.assertTrue(false); } } catch (Error
		 * e) { if (Utility.reTry.get(testName) == Utility.count) {
		 * RetryFailedTestCases.count = Utility.reset_count;
		 * Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		 * test_steps); Utility.updateReport(e, "Failed to Folio", testName,
		 * "Reservation", driver); } else { Assert.assertTrue(false); } }
		 */

		// Create Reservation
		try {
			test_steps.add("=============Create Reservation============");
			app_logs.info("==============Create Reservation=============");
			getTest_Steps.clear();

			String expiryDate = Utility.getFutureMonthAndYearForMasterCard();
			/*
			 * reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			 * reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			 * reservationPage.enter_Adults(driver, test_steps, adults);
			 * reservationPage.enter_Children(driver, test_steps, children);
			 * reservationPage.select_Rateplan(driver, test_steps, rateplan, "");
			 * reservationPage.clickOnFindRooms(driver, test_steps);
			 * reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes", "");
			 */
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation,
					AccountFirstName, AccountLastName, config.getProperty("flagOff"));
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
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

		//// after Creation of Reservation verifying folio line items from reservation side
		try {
			test_steps.add("=========verifying folio line items from reservation side========");
			app_logs.info("==========verifying folio line items from reservation side=========");
			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-");

			reservationPage.click_Folio(driver, getTest_Steps);
			Utility.app_logs.info("Folio Tab Clicked");
			test_steps.add("Folio Tab Clicked");

			/*
			 * getTest_Steps.clear(); getTest_Steps = res.verifyFolioSelect(driver,
			 * AccountName); test_steps.addAll(getTest_Steps);
			 */

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectFolioOption(driver, "Guest Folio");
			test_steps.addAll(getTest_Steps);

			/*
			 * getTest_Steps.clear(); getTest_Steps = res.verifyLineitemsExist(driver, true,
			 * 1); test_steps.addAll(getTest_Steps);
			 * 
			 * 
			 * getTest_Steps.clear(); getTest_Steps = res.VerifyFolioLineItem(driver,
			 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
			 */

			String option = AccountName;
			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectFolioOption(driver, option);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 1);
			test_steps.addAll(getTest_Steps);
			DecimalFormat df = new DecimalFormat("0.00");
	        df.setMaximumFractionDigits(2);
	        String amount=df.format(Double.parseDouble(BaseAmount));
	        Utility.app_logs.info(amount);

			getTest_Steps.clear();
			getTest_Steps = res.VerifyFolioLineItem(driver, "Room Charge", amount, "1");
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
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
			test_steps.add("=============navigate to Room Block============");
			app_logs.info("==============navigate to Room Block=============");
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

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

		String AvailableRoom = null;
		String BookIconClass = null;
		//Verfiy red book icon
		try {

			/*
			 * PickupValue = group.getPickUpValue(driver, RoomClassName);
			 * Utility.app_logs.info("After Pickup Value : " + PickupValue);
			 * test_steps.add("After Pickup Value : " + PickupValue);
			 * assertEquals(Integer.parseInt(PickupValue),
			 * Integer.parseInt(afterPickupValue) + 1, "Failed PickUp Value Not increased");
			 */
			test_steps.add("=============Verfiy red book icon============");
			app_logs.info("==============Verfiy red book icon=============");
			BookIconClass = group.getBookIconClass(driver, RoomClassName);
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
			AvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + AvailableRoom);
			test_steps.add("After Available Rooms : " + AvailableRoom);
			/*
			 * assertEquals(Integer.parseInt(AvailableRoom),
			 * Integer.parseInt(afterAvailableRoom),
			 * "Failed to verfy Availalbe Rooms not Matched");
			 */
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

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
		
		// Create Reservation with zero room

				try {
					test_steps.add("=================Create Reservation================");
					app_logs.info("==================Create Reservation=================");
					String expiryDate = Utility.getFutureMonthAndYearForMasterCard();
				
					 //reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes", "");
					 
					reservationPage.clickNext(driver, test_steps);
					reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation,
							AccountFirstName, AccountLastName, config.getProperty("flagOff"));
					reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
					reservationPage.clickBookNow(driver, test_steps);
					reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					reservationPage.get_ReservationStatus(driver, test_steps);
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					test_steps.add("Successfully Associated Account to  Reservation");
					app_logs.info("Successfully Associated Account to Reservation");
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
				
				// Navigate to Groups and Search Account
				try {
					Nav.Groups(driver);
					getTest_Steps.clear();
					getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
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

		AdvGroups advGrp = new AdvGroups();

		// navigate Group reservation tab
		try {
			test_steps.add("============= verifying reservation count from Group side============");
			app_logs.info("============== verifying reservation count from Group side============");
			getTest_Steps.clear();
			advGrp.click_GroupsReservationTab(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyReservationCount(driver, 3);
			test_steps.addAll(getTest_Steps);

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
			test_steps.add("=============account inActive============");
			app_logs.info("==============account inActive=============");
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

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
		return Utility.getData("VerifyPikupBluBookChargeRouting", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
