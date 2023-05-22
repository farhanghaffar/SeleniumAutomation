package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import io.restassured.response.Response;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AirbnbInObjects;
import com.innroad.inncenter.pageobjects.AirbnbObjects;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyVrboRate extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> gettest_steps = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, airbnbexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyVrboRate(String beginDate, String noOfNights, String roomClassName, String firstName,
			String lastName, String emails, String adultCount, String childCount, String phoneNum, String baseprice,
			String payoutamount, String actionTaken, String isInncenterTaxCreate, String intaxname, String intaxValue,
			String categoryTaxs, String taxType, String ledgerAccount, String extendOrResduceDays,
			String updateFirstName, String updateLastName, String updateAdultCount, String updateChildCount,
			String action) throws ParseException {
		test_name = "VerifyVrboRate";
		test_description = "Verify Vrvo Rate <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/' target='_blank'>"
				+ "Click here to open TestRail: C</a>";
		test_catagory = "VerifyPromoCodeRateFromNewRes";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		RatesGrid rateGrid = new RatesGrid();
		Navigation nav = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomclass = new RoomClass();
		CPReservationPage cpRes = new CPReservationPage();
		HashMap<String, String> roomClassWiseSourceBaseRatesRule = null;
		Account createCA = new Account();
		AirbnbInObjects asd = new AirbnbInObjects();
		String innCenterTab;
		String airbnb;

		/*
		 * String bedsCount = ""; String isMinStay = "false"; String isMaxStay =
		 * "false"; String minNights = ""; String maxNights = ""; String timeZone = "";
		 * String isMoreThanDaysReq = "false"; String MoreThanDaysCount = ""; String
		 * isWithInDaysReq = "false"; ArrayList<String> datesRangeList = new
		 * ArrayList<String>(); String WithInDaysCount = ""; HashMap<String, String>
		 * rate = new HashMap<String, String>(); String deleteRooms = ""; String message
		 * = "No rate combination can be found!"; VrboObjects vrboObject = new
		 * VrboObjects(); ArrayList<String> days = new ArrayList<String>(); String
		 * channelName = "Vrbo"; ArrayList<String> IsDays_RatesUpdateList =
		 * Utility.convertTokenToArrayList(isDays_RatesUpdate, delim); String
		 * isMon_RatesUpdate = IsDays_RatesUpdateList.get(0); String isTue_RatesUpdate =
		 * IsDays_RatesUpdateList.get(1); String isWed_RatesUpdate =
		 * IsDays_RatesUpdateList.get(2); String isThu_RatesUpdate =
		 * IsDays_RatesUpdateList.get(3); String isFri_RatesUpdate =
		 * IsDays_RatesUpdateList.get(4); String isSat_RatesUpdate =
		 * IsDays_RatesUpdateList.get(5); String isSun_RatesUpdate =
		 * IsDays_RatesUpdateList.get(6); ArrayList<String> AllBaseRateIncludingOverride
		 * = new ArrayList<String>(); ArrayList<String> allDatesBW =
		 * Utility.getAllDatesStartAndEndDates(startdate, enddate);
		 */
		driver = getDriver();
		allTabs = new ArrayList<>(driver.getWindowHandles());
		String accountName = "Airbnb Corporate Account";
		String  accountType= "Corporate/Member Accounts";
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			innCenterTab = allTabs.get(0);
			Utility.switchTab(driver, innCenterTab);
			try {
				loginAirbnb(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			AirbnbObjects ab = new AirbnbObjects();
			ArrayList<String> days = new ArrayList<String>();
		//	nav.Accounts(driver);
		//	test_steps.add("Navigate to Accounts");
			//app_logs.info("Navigate to Accounts");


	
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.open();");
			allTabs = new ArrayList<>(driver.getWindowHandles());
			airbnb = allTabs.get(1);
            Utility.switchTab(driver, airbnb);
            loginHostAirbnb(driver);
			System.out.println("Successfull created in message");
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
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	//	try {


			/*
			 * test_steps.
			 * add("=================== SEASON LEVEL RATES CHANGE=================== ");
			 * app_logs.
			 * info("=================== SEASON LEVEL RATES CHANGE=================== ");
			 * nav.Inventory(driver, test_steps); nav.ratesGrid(driver);
			 * 
			 * rateGrid.clickRatePlanArrow(driver, test_steps);
			 * rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanName);
			 * Wait.waitUntilPageLoadNotCompleted(driver, 40);
			 * 
			 * rateGrid.removeOverride(driver, roomClassname, allDatesBW, test_steps);
			 * rateGrid.removeOverrideChannelLevel( driver, roomClassname, allDatesBW,
			 * channelName, test_steps); rateGrid.clickEditIcon(driver, test_steps);
			 * nightlyRate.switchCalendarTab(driver, test_steps); ArrayList<String>
			 * allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
			 * Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
			 * test_steps.add("All Season Dates : " + allSeasonsDates);
			 * nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			 * nightlyRate.selectSeasonDates(driver, test_steps, allDatesBW);
			 * nightlyRate.deleteSeasonIcon(driver,test_steps);
			 * 
			 * nightlyRate.selectSeasonDates(driver, test_steps, startdate, enddate); String
			 * seasonName = test + Utility.generateRandomString();
			 * nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			 * nightlyRate.clickCreateSeason(driver, test_steps);
			 * nightlyRate.selectSeasonColor(driver, test_steps);
			 * 
			 * if(isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			 * nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
			 * isAdditionalChargesForChildrenAdults); } nightlyRate.enterRate(driver,
			 * test_steps, modifyRatePerNight, isAdditionalChargesForChildrenAdults,
			 * MaxAdults, maxPersons,additionalAdultsPerNight, additionalChildPerNight);
			 * 
			 * nightlyRate.clickSaveSason(driver, test_steps);
			 * 
			 * 
			 * nightlyRate.clickSaveRatePlanButton(driver, test_steps); ArrayList<String>
			 * extraAdults = new ArrayList<>(); ArrayList<String> extraChilds = new
			 * ArrayList<>();
			 * 
			 * nav.Inventory(driver, test_steps); nav.ratesGrid(driver);
			 * test_steps.add("Navigated to rateGrid");
			 * if(action.equalsIgnoreCase("Verify rates after bulk update")||(action.
			 * equalsIgnoreCase("Verify rates after override"))){
			 * rateGrid.bulkUpdateOverideRates(driver, delim, RatesUpdateType,
			 * overrideStartDate, overrideEndDate, isSun_RatesUpdate, isMon_RatesUpdate,
			 * isTue_RatesUpdate, isWed_RatesUpdate, isThu_RatesUpdate, isFri_RatesUpdate,
			 * isSat_RatesUpdate,ratePlanName, roomClassname, channelName, UpdateRatesType,
			 * isUpdateRateByRoomClass, Override_RatesUpdate, additionalAdults_Override,
			 * additionalChild_Override, test_steps); }else
			 * if(action.equalsIgnoreCase("Verify rates after override")) {
			 * //ratesGrid.overrideRateFromRatesGrid(driver, test_steps, roomClassname,
			 * datesRangeList, Override_RatesUpdate, extraAdults, extraChilds); }
			 * 
			 * test_steps.
			 * add("=================== GETTING RATES FROM INNCENTER======================"
			 * ); app_logs.
			 * info("=================== GETTING RATES FROM INNCENTER ======================"
			 * );
			 * 
			 * rateGrid.selectDateFromRatesGridCalendar(driver, test_steps, startdate);
			 * rateGrid.expandRoomClass(driver, roomClassname);
			 * 
			 * roomClassWiseSourceBaseRatesRule = rateGrid.getRatesOfChannel(driver,
			 * startdate, enddate, roomClassname, channelName);
			 * 
			 * 
			 * System.out.println("roomClassWiseSourceBaseRatesRule" +
			 * roomClassWiseSourceBaseRatesRule);
			 */
/*=======
			test_steps.add("=================== SEASON LEVEL RATES CHANGE=================== ");
			app_logs.info("=================== SEASON LEVEL RATES CHANGE=================== ");
			nav.Inventory(driver, test_steps);
			nav.ratesGrid(driver);

			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
				
			//rateGrid.removeOverride(driver, roomClassname, allDatesBW, test_steps);
			//rateGrid.removeOverrideChannelLevel( driver, roomClassname, allDatesBW, channelName, test_steps);			
			rateGrid.clickEditIcon(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);
			ArrayList<String> allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
			Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
			test_steps.add("All Season Dates : " + allSeasonsDates);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			nightlyRate.selectSeasonDates(driver, test_steps, allDatesBW);
			nightlyRate.deleteSeasonIcon(driver,test_steps);
	
			nightlyRate.selectSeasonDates(driver, test_steps, startdate, enddate);
			String seasonName = test + Utility.generateRandomString();
			nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			
			if(isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
					isAdditionalChargesForChildrenAdults);
			}
			//nightlyRate.getAllRoomClassesNames(driver);
			nightlyRate.enterRate(driver, test_steps, modifyRatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults,
					maxPersons,additionalAdultsPerNight, additionalChildPerNight);
		

			nightlyRate.clickSaveSason(driver, test_steps);

		
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				ArrayList<String> extraAdults = new ArrayList<>();
				ArrayList<String> extraChilds = new ArrayList<>();
				
				nav.Inventory(driver, test_steps);
				nav.ratesGrid(driver);
				test_steps.add("Navigated to rateGrid");	
				if(action.equalsIgnoreCase("Verify rates after bulk update")||(action.equalsIgnoreCase("Verify rates after override"))){
				  rateGrid.bulkUpdateOverideRates(driver, delim, RatesUpdateType, overrideStartDate,
						  overrideEndDate, isSun_RatesUpdate, isMon_RatesUpdate, isTue_RatesUpdate,
						  isWed_RatesUpdate, isThu_RatesUpdate, isFri_RatesUpdate,
						  isSat_RatesUpdate,ratePlanName, roomClassname, channelName,
						  UpdateRatesType, isUpdateRateByRoomClass, Override_RatesUpdate,
						  additionalAdults_Override, additionalChild_Override, test_steps);
				}else if(action.equalsIgnoreCase("Verify rates after override")) {
					//ratesGrid.overrideRateFromRatesGrid(driver, test_steps, roomClassname, datesRangeList, Override_RatesUpdate, extraAdults, extraChilds);	
				}

			test_steps.add("=================== GETTING RATES FROM INNCENTER======================");
			app_logs.info("=================== GETTING RATES FROM INNCENTER ======================");

			rateGrid.selectDateFromRatesGridCalendar(driver, test_steps, startdate);
			rateGrid.expandRoomClass(driver, roomClassname);

				roomClassWiseSourceBaseRatesRule = rateGrid.getRatesOfChannel(driver, startdate, enddate, roomClassname,
						channelName);
	
			
			System.out.println("roomClassWiseSourceBaseRatesRule" + roomClassWiseSourceBaseRatesRule);
>>>>>>> develop

		} catch (Exception e) {

		} catch (Error e) {

		}
<<<<<<< HEAD*/
		/*
		 * try { System.out.println(vrvoRateToken); int statusCode =
		 * vrboObject.getVrboRateEndpointStatus(propertyid, roomClassId, vrvoRateToken);
		 * app_logs.info(statusCode); vrboObject.veifyVrboClientStatusCode(statusCode,
		 * test_steps); Response responseBody =
		 * vrboObject.getResponseBodyOfVrboRateEndPoint(propertyid, roomClassId,
		 * vrvoRateToken);
		 * 
		 * //ArrayList<String> dates =
		 * vrboObject.getStartAndEndDateBaseRate(responseBody); //String getRate =
		 * vrboObject.getRateAmount(responseBody); //System.out.println("getRate" +
		 * getRate); //System.out.println("dates" + dates);
		 * 
		 * 
		 * if(isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
		 * //HashMap<String, String> getAdditionalRate =
		 * vrboObject.getAdditionalAdultChildRate(responseBody);
		 * //System.out.println("getRate" + getAdditionalRate); HashMap<String,
		 * ArrayList<ArrayList<String>>> record =
		 * vrboObject.getAllRatesBaseRateWithDateRange(driver,responseBody) ; ArrayList<
		 * HashMap<String, ArrayList<ArrayList<String>>>> recordsExtraAdultAndChild=
		 * vrboObject.getAllExtraAdultChildDateRange(driver,responseBody);
		 * HashMap<String, ArrayList<String>> dateRangeExtraAdultAndChild =
		 * vrboObject.getExtraAdultChildDateRangeBaseRate(responseBody);
		 * //System.out.println("dateRangeExtraAdultAndChild" +
		 * dateRangeExtraAdultAndChild); //System.out.println("dates" + dates); }else {
		 * HashMap<String, ArrayList<ArrayList<String>>> record =
		 * vrboObject.getAllRatesBaseRateWithDateRange(driver,responseBody) ;
		 * System.out.println("onlyBaserate" + record);
		 * AllBaseRateIncludingOverride.add(modifyRatePerNight);
		 * //vrboObject.verifyVrboRates(dates, startdate, enddate, getRate,
		 * AllBaseRateIncludingOverride,test_steps);
		 * if(action.equalsIgnoreCase("Verify rates after override")) {
		 * vrboObject.verifyVerboRatesWithDateRange(record,
		 * roomClassWiseSourceBaseRatesRule, AllBaseRateIncludingOverride, test_steps);
		 * }else { vrboObject.verifyVerboRatesWithDateRange(record,
		 * roomClassWiseSourceBaseRatesRule, AllBaseRateIncludingOverride, test_steps);
		 * }
		 * 
		 * }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
/*=======
		try {
			System.out.println(vrvoRateToken);
			int statusCode = vrboObject.getVrboRateEndpointStatus(propertyid, roomClassId, vrvoRateToken);
			app_logs.info(statusCode);
			vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyid, roomClassId,
					vrvoRateToken);

			//ArrayList<String> dates = vrboObject.getStartAndEndDateBaseRate(responseBody);
			//String getRate = vrboObject.getRateAmount(responseBody);
			//System.out.println("getRate" + getRate);
			//System.out.println("dates" + dates);
			

			if(isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
			//HashMap<String, String> getAdditionalRate = vrboObject.getAdditionalAdultChildRate(responseBody);
			//System.out.println("getRate" + getAdditionalRate);
			HashMap<String, ArrayList<ArrayList<String>>> record =	vrboObject.getAllRatesBaseRateWithDateRange(driver,responseBody) ;
			HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> recordsExtraAdultAndChild=	vrboObject.getAllExtraAdultChildDateRange(driver,responseBody);
			HashMap<String, ArrayList<String>> dateRangeExtraAdultAndChild =  vrboObject.getExtraAdultChildDateRangeBaseRate(responseBody);
			//System.out.println("dateRangeExtraAdultAndChild" + dateRangeExtraAdultAndChild);
			//System.out.println("dates" + dates);
			}else {
				HashMap<String, ArrayList<ArrayList<String>>> record =	vrboObject.getAllRatesBaseRateWithDateRange(driver,responseBody) ;
				System.out.println("onlyBaserate" + record);
				AllBaseRateIncludingOverride.add(modifyRatePerNight);
				//vrboObject.verifyVrboRates(dates, startdate, enddate, getRate, AllBaseRateIncludingOverride,test_steps);
				if(action.equalsIgnoreCase("Verify rates after override")) {
					vrboObject.verifyVerboRatesWithDateRange(record, roomClassWiseSourceBaseRatesRule, AllBaseRateIncludingOverride, test_steps);
				}else {
					vrboObject.verifyVerboRatesWithDateRange(record, roomClassWiseSourceBaseRatesRule, AllBaseRateIncludingOverride, test_steps);
				}
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
>>>>>>> develop*/
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAirbnbReservation", airbnbexcel);
	}
	/*
	 * @DataProvider public Object[][] getDataOne() {
	 * 
	 * return Utility.getData("BusScenarios", otaexcel);
	 * 
	 * }
	 */

	/*
	 * @DataProvider public Object[][] getFinalData() { return
	 * Utility.combine(getData(), getDataOne()); }
	 */

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
