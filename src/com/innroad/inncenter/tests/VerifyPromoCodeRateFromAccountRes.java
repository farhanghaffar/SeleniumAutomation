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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyPromoCodeRateFromAccountRes extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> gettest_steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyPromoCodeRateFromAccountRes(String checkinDate, String checkoutdate, String roomClassName,
			String propertyName, String maxAdult, String maxPerson, String roomQuantity, String delim,
			String ratePlanName, String folioDisplayName, String description, String channels,
			String isRatePlanRistrictionReq, String ristrictionType, String promoCode, String seasonName,
			String seasonStartDate, String seasonEndDate, String secondSeasonStartDate, String secondSeasonEndDate,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
			String additionalAdultsPerNight, String additionalChildPerNight, String adults, String searchedPromoCode,
			String accountType, String accountName, String accountFirstName, String accountLastName, String phonenumber,
			String adddress, String email, String city, String state, String postalCode, String margetSegment,
			String referral, String adult, String children, String actionPerformed) throws ParseException {
		Utility.DELIM = delim;
		test_name = "VerifyPromoCodeRateOnTapeChart" + actionPerformed;
		test_description = "Verify PromoCode Rate On TapeChart <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "VerifyPromoCodeRateOnTapeChart";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomclass = new RoomClass();
		CPReservationPage cpRes = new CPReservationPage();
		Account acc = new Account();
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
		String message = "No rate combination can be found!";

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
			test_steps.add("===================GET PROPERTY TIME ZONE======================");
			app_logs.info("===================GET PROPERTY TIME ZONE======================");
			// nav.Setup(driver);
			nav.navSetup(driver);
			test_steps.add("Navigate Setup");
			app_logs.info("Navigate Setup");
			nav.Properties(driver);
			test_steps.add("Navigat Properties");
			app_logs.info("Navigat Properties");
			nav.openProperty(driver, test_steps, propertyName);
			test_steps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			nav.clickPropertyOptions(driver, test_steps);
			timeZone = nav.get_Property_TimeZone(driver);
			nav.Reservation_Backward(driver);
			test_steps.add("Time Zone " + timeZone);
			app_logs.info("Time Zone " + timeZone);
			app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

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
			test_steps.add("=================== CREATE NEW ROOM CLASS ======================");
			app_logs.info("=================== CREATE NEW ROOM CLASS ======================");
			// create room class
			// nav.Setup(driver);
			nav.navSetup(driver);
			test_steps.add("Navigate to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigate to RoomClasses tab");
			String randomString = Utility.generateRandomString();
			nav.NewRoomClass(driver);
			deleteRooms = roomClassName;
			roomClassName = roomClassName + randomString;
			roomclass.Create_RoomClass(driver, roomClassName, roomClassName, bedsCount, maxAdults, maxPersons,
					roomQuantity, test, test_steps);

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
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			//nav.Inventory(driver, test_steps);
			nav.navInventoryFromRoomClass(driver,test_steps);
			nav.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");

			try {
				ratesGrid.clickRateGridAddRatePlan(driver);
			} catch (Exception e) {
				driver.navigate().refresh();
				ratesGrid.clickRateGridAddRatePlan(driver);
			}
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

			test_steps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			ratePlanName = ratePlanName + Utility.generateRandomString();
			folioDisplayName = folioDisplayName + Utility.generateRandomString();

			nightlyRate.enterRatePlanName(driver, ratePlanName, test_steps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, test_steps);
			nightlyRate.enterRatePlanDescription(driver, description, test_steps);

			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, test_steps);

			test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, test_steps);
			String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);

			test_steps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");

			nightlyRate.selectRoomClasses(driver, roomClassName, true, test_steps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, test_steps);

			nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), ristrictionType,
					Boolean.parseBoolean(isMinStay), minNights, Boolean.parseBoolean(isMaxStay), maxNights,
					Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount, Boolean.parseBoolean(isWithInDaysReq),
					WithInDaysCount, promoCode, test_steps);

			String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
			System.out.println(restrictionsSummary);
			nightlyRate.clickNextButton(driver, test_steps);

			nightlyRate.clickNextButton(driver, test_steps);
			nightlyRate.clickCreateSeason(driver, test_steps);
			// First Season
			nightlyRate.selectSeasonDates(driver, test_steps, seasonStartDate, seasonEndDate);
			seasonName = seasonName + Utility.generateRandomString();
			nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			nightlyRate.enterRate(driver, test_steps, ratePerNight, isAdditionalChargesForChildrenAdults, maxAdults,
					maxPersons, additionalAdultsPerNight, additionalChildPerNight);
			nightlyRate.clickSaveSason(driver, test_steps);
			// Second Season
			if (Utility.validateString(secondSeasonStartDate) && Utility.validateString(secondSeasonEndDate)) {
				nightlyRate.selectSeasonDates(driver, test_steps, secondSeasonStartDate, secondSeasonEndDate);
				seasonName = seasonName + Utility.generateRandomString();
				nightlyRate.enterSeasonName(driver, test_steps, seasonName);
				nightlyRate.clickCreateSeason(driver, test_steps);
				nightlyRate.selectSeasonColor(driver, test_steps);
				nightlyRate.enterRate(driver, test_steps, ratePerNight, isAdditionalChargesForChildrenAdults, maxAdults,
						maxPersons, additionalAdultsPerNight, additionalChildPerNight);
				nightlyRate.clickSaveSason(driver, test_steps);
			}

			nightlyRate.clickCompleteChanges(driver, test_steps);
			nightlyRate.clickSaveAsActive(driver, test_steps);
			ratesGrid.verifySelectedRatePlan(driver, ratePlanName, test_steps);
			test_steps.add("Verify created rate plan exist in rate grid dropdown");
			rate = ratesGrid.getRatesOfRoomClass(driver, checkinDate, checkoutdate, roomClassName);
			System.out.println("hashmap rate" + rate);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		try {

			test_steps.add("=================== CREATE CORPORATE ACCOUNT ======================");
			app_logs.info("=================== CREATE CORPORATE ACCOUNT ======================");
			nav.navAccountFromRateGrid(driver);
			acc.ClickNewAccountbutton(driver, accountType);
			accountName = accountName + Utility.generateRandomString();
			acc.enterAccountName(driver, accountName);
			acc.AccountAttributes(driver, margetSegment, referral);
			accountLastName = accountLastName + Utility.generateRandomString();
			acc.Mailinginfo(driver, accountFirstName, accountLastName, phonenumber, phonenumber, adddress, adddress,
					adddress, email, city, state, postalCode);
			acc.Billinginfo(driver);
			acc.Save(driver);
			test_steps.add("Corporate Account created succesfully: " + accountName);
			acc.NewReservationButton(driver, test);
			test_steps.add("Click at New Reservation Button On Account: "+accountName);
			test_steps.add(
					"=================== SEARCH ROOM CLASS ON RESERVATION FIND ROOM CLASS PAGE ======================");
			app_logs.info(
					"===================  SEARCH ROOM CLASS ON RESERVATION FIND ROOM CLASS PAGE ======================");

			cpRes.checkInDate(driver, Utility.parseDate(checkinDate, "dd/MM/yyyy", "MM/dd/yyyy"));
            test_steps.add("Enter checkin Date: "+ checkinDate);
			cpRes.checkOutDate(driver, Utility.parseDate(checkoutdate, "dd/MM/yyyy", "MM/dd/yyyy"));
            test_steps.add("Enter checkout Date: "+checkoutdate);
			cpRes.enter_Adults(driver, test_steps, adult);
            test_steps.add("Enter Adult: "+ adult);

			cpRes.enter_Children(driver, test_steps, children);
            test_steps.add("Enter children: "+children);

			String ratePlan = "Promo Code";

			if (Utility.validateString(searchedPromoCode)) {
				cpRes.select_Rateplan(driver, test_steps, ratePlan, searchedPromoCode);
			
			} else {
				cpRes.select_Rateplan(driver, test_steps, ratePlan, promoCode);
			}
			 

			cpRes.clickOnFindRooms(driver, test_steps);

			boolean isRoomClassPresent = cpRes.clickRoomClassInSearchResultFindRoom(driver, roomClassName, test_steps);
			System.out.println("isRoomClassPresent" + isRoomClassPresent);
			if (isRoomClassPresent) {
				cpRes.verifySearchedRoomClassRateDetails(driver, roomClassName, checkinDate, checkoutdate, rate,
						folioDisplayName, test_steps);
			} else {
				cpRes.verifyNoRateFoundInSearch(driver, message, test_steps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify roomClass search", "Reservation",
						"Failed to verify roomClass search", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to verify roomClass search", "Reservation",
						"Failed to verify roomClass search", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Delete the created data like Rate, Room class
			test_steps.add("=================== RATE PLAN DELETED ======================");
			app_logs.info("=================== RATE PLAN DELETED ======================");
			//nav.Inventory(driver, test_steps);
			nav.navInventoryFromRoomClass(driver,test_steps);
			nav.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");
			ratesGrid.selectRatePlan(driver, ratePlanName, test_steps);
			ratesGrid.clickDeleteIcon(driver, test_steps);
			ratesGrid.clickDeleteButton(driver, test_steps);
			test_steps.add("=================== ROOM CLASS DELETED ======================");
			app_logs.info("=================== ROOM CLASS DELETED ======================");
			nav.navSetupFromRateGrid(driver, test_steps);
			nav.RoomClass(driver);
			test_steps.add("Navigate to Room Classes");
			roomclass.searchClass(driver, deleteRooms);
			test_steps.add("Searched room class: " + "<b>" + deleteRooms + "</b>");
			roomclass.deleteRoomClass(driver, deleteRooms);
			test_steps.add("Room Class deleted starting from: " + "<b>" + deleteRooms + "</b>");

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
		return Utility.getData("VerifyPromoCodeRateFromAccountR", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
