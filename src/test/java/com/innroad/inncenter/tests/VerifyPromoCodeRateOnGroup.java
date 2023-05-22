package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPromoCodeRateOnGroup extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyPromoCodeRateOnGroup(String TestCaseID, String checkinDate, String checkoutdate,
			String roomClassName, String propertyName, String maxAdult, String maxPerson, String roomQuantity,
			String delim, String ratePlanName, String folioDisplayName, String description, String channels,
			String isRatePlanRistrictionReq, String ristrictionType, String promoCode, String seasonName,
			String seasonStartDate, String seasonEndDate, String secondSeasonStartDate, String secondSeasonEndDate,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String adults, String searchedPromoCode,
			String accountName, String marketSegment, String groupReferral, String groupFirstName, String groupLastName,
			String groupPhone, String groupAddress, String groupCity, String groupState, String groupCountry,
			String groupPostalcode, String blockName, String adult, String child, String roomPerNight,
			String isRateComeFromSetup, String actionPerformed) throws ParseException {
		if (Utility.isAllDigit(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("5");
			comments.add("Failed");
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
		}

		Utility.DELIM = delim;
		test_name = "VerifyPromoCodeRateOnGroup" + actionPerformed;
		test_description = "Verify PromoCode Rate On TapeChart <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "VerifyPromoCodeRateOnGroup";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomclass = new RoomClass();
		Groups group = new Groups();
		String bedsCount = "";
		String isMinStay = "false";
		String isMaxStay = "false";
		String minNights = "";
		String maxNights = "";
		String timeZone = "";
		String isMoreThanDaysReq = "false";
		String MoreThanDaysCount = "";
		String isWithInDaysReq = "false";
		String WithInDaysCount = "";
		HashMap<String, String> rate = new HashMap<String, String>();
		String deleteRooms = "";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();

			try {
				loginWPI(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginWPI(driver);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Get Time Zone
		try {
			
			  test_steps.
			  add("===================GET PROPERTY TIME ZONE======================");
			  app_logs.
			  info("===================GET PROPERTY TIME ZONE======================"); //
			  nav.Setup(driver); nav.navSetup(driver); test_steps.add("Navigate Setup");
			  app_logs.info("Navigate Setup"); nav.Properties(driver);
			  test_steps.add("Navigat Properties"); app_logs.info("Navigat Properties");
			  nav.openProperty(driver, test_steps, propertyName);
			  test_steps.add("Open Property : " + propertyName);
			  app_logs.info("Open Property : " + propertyName);
			  nav.clickPropertyOptions(driver, test_steps); timeZone =
			  nav.get_Property_TimeZone(driver); nav.Reservation_Backward(driver);
			  test_steps.add("Time Zone " + timeZone); app_logs.info("Time Zone " +
			  timeZone); app_logs.info("Curret Time " +
			  Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));
			 

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Capture availability in inncenter", "Inventory",
						"Failed to Capture availability in inncenter", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Capture availability in inncenter", "Inventory",
						"Failed to Capture availability in inncenter", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			
			  test_steps.
			  add("=================== CREATE NEW ROOM CLASS ======================");
			  app_logs.
			  info("=================== CREATE NEW ROOM CLASS ======================");
			  test_steps.add("<b> ************Create Room Class</b>****************"); //
			//  create room class 
			  // nav.Setup(driver); 
			  nav.navSetup(driver);
			  test_steps.add("Navigate to Setup"); nav.RoomClass(driver);
			  test_steps.add("Navigate to RoomClasses tab"); String randomString =
			  Utility.generateRandomString(); //nav.NewRoomClass(driver); //deleteRooms =roomClassName; 
			  roomClassName = roomClassName + randomString;
			  
			  roomclass.Create_RoomClass(driver, roomClassName, roomClassName, bedsCount,
			  maxAdults, maxPersons, roomQuantity, test, test_steps);
			  
			  
			  newRcPage.createRoomClassV2( driver, roomClassName, roomClassName, maxAdults,
			  maxPersons, roomQuantity, test, test_steps);
			 

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create room class", "Room Class", "Failed to Create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create room class", "Room Class", "Failed to Create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// After login
			
			  test_steps.
			  add("=================== NAVIGATE TO RATE GRID ======================");
			  app_logs.
			  info("=================== NAVIGATE TO RATE GRID ======================");
			  //nav.Inventory(driver, test_steps); nav.InventoryV2(driver);
			  nav.RatesGrid(driver); test_steps.add("Navigated to RatesGrid");
			  
			  try { ratesGrid.clickRateGridAddRatePlan(driver); } catch (Exception e1) {
			  driver.navigate().refresh(); ratesGrid.clickRateGridAddRatePlan(driver); }
			  ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
			  
			  nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type",
			  "Nightly rate plan", test_steps);
			  
			  test_steps.add(
			  "=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================"
			  ); app_logs.info(
			  "=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================"
			  );
			  
			  ratePlanName = ratePlanName + Utility.generateRandomString();
			  folioDisplayName = folioDisplayName + Utility.generateRandomString();
			  
			  nightlyRate.enterRatePlanName(driver, ratePlanName, test_steps);
			  nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, test_steps);
			  nightlyRate.enterRatePlanDescription(driver, description, test_steps);
			  
			  nightlyRate.clickNextButton(driver, test_steps);
			  
			  nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName,
			  test_steps);
			  
			  test_steps.
			  add("=================== SELECT DISTRIBUTED CHANNELS ======================"
			  ); app_logs.
			  info("=================== SELECT DISTRIBUTED CHANNELS ======================"
			  );
			  
			  nightlyRate.selectChannels(driver, channels, true, test_steps); String
			  summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			  
			  nightlyRate.clickNextButton(driver, test_steps);
			  
			  //nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);
			  
			  test_steps.
			  add("=================== SELECT ROOM CLASSES ======================");
			  app_logs.
			  info("=================== SELECT ROOM CLASSES ======================");
			  
			  nightlyRate.selectRoomClasses(driver, roomClassName, true, test_steps);
			  String summaryRoomClasses =
			  nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			  nightlyRate.clickNextButton(driver, test_steps);
			  
			  nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses,
			  test_steps);
			  
			  nightlyRate.selectRestrictions(driver,
			  Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
			  Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay),
			  maxNights, Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
			  Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, promoCode,
			  test_steps);
			  
			  String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver,
			  test_steps); System.out.println(restrictionsSummary);
			  nightlyRate.clickNextButton(driver, test_steps);
			  
			  nightlyRate.clickNextButton(driver, test_steps);
			  nightlyRate.clickCreateSeason(driver, test_steps); // First Season
			  nightlyRate.selectSeasonDates(driver, test_steps, seasonStartDate,
			  seasonEndDate); seasonName = seasonName + Utility.generateRandomString();
			  nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			  nightlyRate.clickCreateSeason(driver, test_steps);
			  nightlyRate.selectSeasonColor(driver, test_steps);
			  nightlyRate.enterRate(driver, test_steps, ratePerNight,
			  isAdditionalChargesForChildrenAdults, maxAdults, maxPersons,
			  additionalAdultsPerNight, additionalChildPerNight);
			  nightlyRate.clickSaveSason(driver, test_steps); // Second Season 
			  if(Utility.validateString(secondSeasonStartDate) && Utility.validateString(secondSeasonEndDate)) {
			  nightlyRate.selectSeasonDates(driver, test_steps, secondSeasonStartDate,
			  secondSeasonEndDate); seasonName = seasonName +
			  Utility.generateRandomString(); nightlyRate.enterSeasonName(driver,
			  test_steps, seasonName); nightlyRate.clickCreateSeason(driver, test_steps);
			  nightlyRate.selectSeasonColor(driver, test_steps);
			  nightlyRate.enterRate(driver, test_steps, ratePerNight,
			  isAdditionalChargesForChildrenAdults, maxAdults, maxPersons,
			  additionalAdultsPerNight, additionalChildPerNight);
			  nightlyRate.clickSaveSason(driver, test_steps); }
			  
			  try { //sometime rate grid take much time load
			  nightlyRate.clickCompleteChanges(driver, test_steps);
			  nightlyRate.clickSaveAsActive(driver, test_steps); } catch (Exception e) {
			  nightlyRate.clickCompleteChanges(driver, test_steps);
			  nightlyRate.clickSaveAsActive(driver, test_steps); } Wait.wait15Second(); try
			  { rate = ratesGrid.getRatesOfRoomClass(driver, checkinDate, checkoutdate,
			  roomClassName); } catch (Exception e) { try { driver.navigate().refresh();
			  ratesGrid.selectRatePlan(driver, ratePlanName, test_steps); rate =
			  ratesGrid.getRatesOfRoomClass(driver, checkinDate, checkoutdate,
			  roomClassName); } catch (Exception e1) { try { driver.navigate().refresh();
			  ratesGrid.selectRatePlan(driver, ratePlanName, test_steps); rate =
			  ratesGrid.getRatesOfRoomClass(driver, checkinDate, checkoutdate,
			  roomClassName); } catch (Exception e2) { driver.navigate().refresh();
			  ratesGrid.selectRatePlan(driver, ratePlanName, test_steps); rate =
			  ratesGrid.getRatesOfRoomClass(driver, checkinDate, checkoutdate,
			  roomClassName); } } } System.out.println("hashmap rate" + rate);
			 
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		try {
			
			  test_steps.
			  add("=================== CREATING GROUP ACCOUNT ======================");
			  app_logs.
			  info("=================== CREATING GROUP ACCOUNT ======================");
			  nav.navReservationFromRateGrid(driver); nav.groups(driver);
			  test_steps.add("Navigate Groups"); app_logs.info(" Navigate Groups");
			  accountName = accountName + " " + Utility.generateRandomString();
			  group.createGroupAccount(driver, test, accountName, marketSegment,
			  groupReferral, groupFirstName, groupLastName, groupPhone, groupAddress,
			  groupCity, groupState, groupCountry, groupPostalcode, test_steps);
			  test_steps.add("Group acocunt created succesfully: " + accountName);
			  group.navigateRoomBlock(driver, test); group.ClickNewBlock(driver);
			  test_steps.add("Click at new Block link"); blockName = blockName + " " +
			  Utility.generateRandomString(); group.EnterBlockName(driver, blockName);
			  group.ClickOkay_CreateNewBlock(driver);
			  group.selectCheckinAndCheckoutDateForBlock(driver, checkinDate, checkoutdate,
			  timeZone, test_steps);
			  
			  group.SelectRatePlan(driver, ratePlanName, test_steps);
			  group.SelectAdults(driver, adult, test_steps); group.SelectChilds(driver,
			  child, test_steps); group.EnterNights(driver, roomPerNight, test_steps); if
			  (Utility.validateString(searchedPromoCode)) {
			  
			  group.enterPromoCode(driver, searchedPromoCode, test_steps); } else {
			  
			  group.enterPromoCode(driver, promoCode, test_steps); }
			  
			  group.ClickSearchGroup(driver, test_steps); boolean isAvail =
			  group.isBlockAvailable(driver, test_steps, roomClassName);
			  System.out.println(isAvail); test_steps.
			  add("=================== VERFYING SETUP RATE IN GROUP DISPLAYED ======================"
			  ); app_logs.
			  info("=================== VERFYING SETUP RATE IN GROUP DISPLAYED ======================"
			  ); boolean isCorrectRate = group.verifyAvgRateInGroupBlock(driver,
			  roomClassName, ratePerNight, test_steps); boolean actualRate =
			  Boolean.parseBoolean(isRateComeFromSetup); Assert.assertEquals(isCorrectRate,
			  actualRate, "Failed to verify");
			 
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify setup rate", "Group", "Failed to Verify setup rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify setup rate", "Group", "Failed to Verify setup rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Delete the created data like Rate, Room class
			
			  test_steps.add("=================== RATE PLAN DELETED ======================"
			  );
			  app_logs.info("=================== RATE PLAN DELETED ======================"
			  ); // nav.Inventory(driver, test_steps);
			  nav.navInventoryFromGroupBlock(driver); nav.RatesGrid(driver);
			  test_steps.add("Navigated to RatesGrid");
			  
			  try { //sometime rate grid take much time load
			  ratesGrid.selectRatePlan(driver, ratePlanName, test_steps);
			  ratesGrid.clickDeleteIcon(driver, test_steps);
			  ratesGrid.clickDeleteButton(driver, test_steps); } catch (Exception e) {
			  driver.navigate().refresh(); ratesGrid.selectRatePlan(driver, ratePlanName,
			  test_steps); ratesGrid.clickDeleteIcon(driver, test_steps);
			  ratesGrid.clickDeleteButton(driver, test_steps); } test_steps.
			  add("=================== ROOM CLASS DELETED ======================");
			  app_logs.info("=================== ROOM CLASS DELETED ======================"
			  ); nav.navSetupFromRateGrid(driver, test_steps); nav.RoomClass(driver);
			  test_steps.add("Navigate to Room Classes"); 
			  //roomclass.searchClass(driver, deleteRooms); //test_steps.add("Searched room class: " + "<b>" + deleteRooms + "</b>"); 
			  newRcPage.deleteRoomClassV2(driver, roomClassName);
			  test_steps.add("Room Class deleted starting from: "+
			  "<b>"+roomClassName+"</b>");
			 
			statusCode.set(0, "1");
			comments.set(0,"Verify the search in group block " + actionPerformed);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete", "Inventory", "Failed to Delete", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete", "Inventory", "Failed to Delete", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyPromoCodeRateOnGroup", envLoginExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
