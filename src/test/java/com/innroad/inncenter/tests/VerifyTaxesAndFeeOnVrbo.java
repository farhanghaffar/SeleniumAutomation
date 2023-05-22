package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import io.restassured.response.Response;

public class VerifyTaxesAndFeeOnVrbo extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> gettest_steps = new ArrayList<>();
	RatesGrid rateG = new RatesGrid();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Season seaso = new Season();
	Response response;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData")
	public void verifyTaxesAndFeeOnVrbo(String testCaseID,String roomClassame, String startdate, String enddate,
			String isAdditionalChargesForChildrenAdults, String updateRate, String extraAdult, String extraChild,
			String extraAdultAmount, String extraChildAmount, String intaxname, String taxType, String categoryTaxs,
			String intaxValue, String ledgerAccount, String infeeName, String feeType, String infeeValues,
			String categoryFee, String nightOrStayFee, String isInncenterTaxCreate, String isInncenterFeeCreate,
			String action, String isInactiveTax, String isInactiveFee, String isRateThere) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		String advertiserId = null, classId = null, currency = null, propertyId = null, ratePlanVrbo = null;
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		if(isInactiveTax.equalsIgnoreCase("Yes")) {
			test_name = "VerifyTaxesAndFeeOnVrbo_" + action + "And Verify Inactive Tax";
		}else if(isInactiveFee.equalsIgnoreCase("Yes")) {
			test_name = "VerifyTaxesAndFeeOnVrbo_" + action + "And Verify Inactive Fee";
		}else {
		test_name = "VerifyTaxesAndFeeOnVrbo_" + action;}
		test_description = "Verify Vrvo Rate <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804336' target='_blank'>"
				+ "Click here to open TestRail: 804336</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804337' target='_blank'>"
				+ "Click here to open TestRail: 804337</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804338' target='_blank'>"
				+ "Click here to open TestRail: 804338</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802296' target='_blank'>"
				+ "Click here to open TestRail: 802296</a>";
		test_catagory = "VRBO";
		String testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		RatesGrid rateGrid = new RatesGrid();
		Navigation navigation = new Navigation();
		NightlyRate nightlyRate = new NightlyRate();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		HashMap<String, ArrayList<HashMap<String, String>>> inRate = null;
		VrboObjects vrboObject = new VrboObjects();
		TaxesAndFee taxFee = new TaxesAndFee();
		HashMap<String, String> setTaxRates = new HashMap<String, String>();
		String channelName = "Vrbo";
		ArrayList<String> updatedTaxName = new ArrayList<String>();
		ArrayList<String> intaxValues = Utility.convertTokenToArrayList(intaxValue, Utility.DELIMS);
		ArrayList<String> categoryTaxes = Utility.convertTokenToArrayList(categoryTaxs, Utility.DELIMS);
		ArrayList<String> intaxnames = Utility.convertTokenToArrayList(intaxname, Utility.DELIMS);
		ArrayList<String> taxTypes = Utility.convertTokenToArrayList(taxType, Utility.DELIMS);
		ArrayList<String> updatedFeeName = new ArrayList<String>();
		ArrayList<String> infeeValue = Utility.convertTokenToArrayList(infeeValues, Utility.DELIMS);
		ArrayList<String> categoryFees = Utility.convertTokenToArrayList(categoryFee, Utility.DELIMS);
		ArrayList<String> feeTypes = Utility.convertTokenToArrayList(feeType, Utility.DELIMS);
		ArrayList<String> nightOrStayFees = Utility.convertTokenToArrayList(nightOrStayFee, Utility.DELIMS);
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			try {
				loginOTA(driver);
					
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
			}
			System.out.println(vrvoRateToken);
			System.out.println("Startday" + startdate);
			System.out.println("Startday" + enddate);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			test_steps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, test_steps);
			test_steps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);
			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			test_steps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			test_steps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);
			admin.clickClientOptions(driver, test_steps);
			currency = admin.getDefaultCurrency(driver);
			test_steps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);
			navigation.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickVrboSetup(driver);
			test_steps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");
			test_steps.add("<b>===== Getting Advertisement ID =====</b>");
			app_logs.info("===== Getting Advertisement ID=====");
			advertiserId = vrbo.getVrboAdvertisementID(driver);
			test_steps.add("Advertisement ID : " + advertiserId);
			app_logs.info("Advertisement ID " + advertiserId);
			test_steps.add("<b>===== Getting Room Class ID =====</b>");
			app_logs.info("===== Getting Room Class ID=====");
			navigation.roomClass(driver);
			HashMap<String, String> roomClassIds = new HashMap<String, String>();
			roomClassIds = roomClass.getRoomClassDetails(driver, roomClassame, test_steps);
			classId = roomClassIds.get("roomClassId");
			test_steps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			ratePlanVrbo = roomClassIds.get("ratePlan");
			test_steps.add("Room ratePlanVrbo : " + ratePlanVrbo);
			app_logs.info("Room ratePlanVrbo " + ratePlanVrbo);
			test_steps.add("<b>===== Getting PropertyId ID =====</b>");
			app_logs.info("===== Getting PropertyId ID=====");
			navigation.properties(driver);
			propertyId = vrbo.getPropertyId(driver, property);
			app_logs.info("propertyId Class ID " + propertyId);
			navigation.Reservation_Backward_4(driver);
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
		try {
			if (!(Utility.validateInput(startdate))) {
				startdate = Utility.getCurrentDatTimeInUTC("dd/MM/yyyy");
				enddate = Utility.parseDate(Utility.getDatePast_FutureDateUTC(3), "MM/dd/yyyy", "dd/MM/yyyy");
			}
			test_steps.add("==================UPDATE SEASON IN RATE==================");
			app_logs.info("==================UPDATE SEASON IN RATE==================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanVrbo);
			rateGrid.verifyRatesGridLoaded(driver);
			rateGrid.clickEditIcon(driver, test_steps);
			nightlyRate.switchCalendarTab(driver, test_steps);
			datesRangeList = Utility.getAllDatesStartAndEndDates(startdate, enddate);
			try {
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList);
				nightlyRate.deleteSeasonIcon(driver, test_steps);
			} catch (Exception e) {
			}
			nightlyRate.selectSeasonDates(driver, test_steps, startdate, enddate);
			String seasonName = test + Utility.generateRandomString();
			nightlyRate.enterSeasonName(driver, test_steps, seasonName);
			nightlyRate.clickCreateSeason(driver, test_steps);
			nightlyRate.selectSeasonColor(driver, test_steps);
			if (isAdditionalChargesForChildrenAdults.equalsIgnoreCase("Yes")) {
				nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
						isAdditionalChargesForChildrenAdults);
			}
			nightlyRate.unSelectSeasonLevelExceptAdditionalRoomClass(driver, roomClassame, test_steps);
			nightlyRate.enterRate(driver, test_steps, updateRate, isAdditionalChargesForChildrenAdults, extraAdult,
					extraChild, extraAdultAmount, extraChildAmount);
			nightlyRate.clickSaveSason(driver, test_steps);
			nightlyRate.clickSaveRatePlanButton(driver, test_steps);
			test_steps.add("==================RATE SUCCESSFULLY UPDATED==================");
			app_logs.info("==================RATE SUCCESSFULLY UPDATED==================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
			test_steps.add("==================GET RATE FROM RATE GRID==================");
			app_logs.info("===================GET RATE FROM RATE GRID==================");
			inRate = rateGrid.getRatesWithAdultChildFromChannel(driver, test_steps, roomClassame, channelName,
					datesRangeList, true);
			test_steps.add("Get Inncenter Rate from rate grid: " + inRate);
			app_logs.info("Get Inncenter Rate from rate grid: " + inRate);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Get Inncenter rate", testName,
					test_description, test_catagory, test_steps);
			Utility.catchException(driver, e, test_description, test_catagory, "Get Inncenter rate", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Verify on vrbo", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			navigation.navSetupFromRateGrid(driver, test_steps);
			navigation.clickTaxesAndFees(driver);
			test_steps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				//taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", test_steps);
				taxFee.deleteAllTaxesAndFee(driver, test_steps);			
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("==================CREATE TAX IN INNCENTER==================");
				app_logs.info("==================CREATE TAX IN INNCENTER==================");
				for (int i = 0; i < intaxnames.size(); i++) {
					String upTaxName = intaxnames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedTaxName.add(upTaxName);
				}
				for (int i = 0; i < intaxnames.size(); i++) {
					taxFee.createTaxes(driver, updatedTaxName.get(i), updatedTaxName.get(i), updatedTaxName.get(i),
							taxTypes.get(i), categoryTaxes.get(i), intaxValues.get(i), "", ledgerAccount, false, "", "",
							"", test_steps);
					test_steps.add("===========================================================");
					if (categoryTaxes.get(i).equalsIgnoreCase("flat")) {
						setTaxRates.put(updatedTaxName.get(i), "No");
					} else {
						setTaxRates.put(updatedTaxName.get(i), intaxValues.get(i));
					}
				}
				System.out.println("setTaxRates: " + setTaxRates);
			}
			if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("==================CREATE FEE IN INNCENTER==================");
				app_logs.info("==================CREATE FEE IN INNCENTER==================");
				ArrayList<String> infeeNames = Utility.convertTokenToArrayList(infeeName, Utility.DELIMS);

				for (int i = 0; i < infeeNames.size(); i++) {
					String upfeeName = infeeNames.get(i) + " " + Utility.generateRandomStringWithGivenLength(3);
					updatedFeeName.add(upfeeName);
				}
				for (int i = 0; i < infeeNames.size(); i++) {
					taxFee.createFeeWithNightOrStay(driver, test_steps, updatedFeeName.get(i), updatedFeeName.get(i),
							feeTypes.get(i), updatedFeeName.get(i), categoryFees.get(i), infeeValue.get(i), false, "",
							"", "", nightOrStayFees.get(i));
					test_steps.add("===========================================================");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Verify on vrbo",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Verify on vrbo", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
			app_logs.info(statusCode);
			vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
			Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);

			test_steps.add("==================GET TAX ON VRBO==================");
			app_logs.info("==================GET TAX ON VRBO==================");
			HashMap<String, String> vrboTaxes = null;
			HashMap<String, String> vrboFees = null;
			if (action.equalsIgnoreCase("CreatePercentTaxes")) {
				vrboTaxes = vrboObject.getPercentageTax(driver, responseBody);
				app_logs.info("Vrbo Tax: " + vrboTaxes);
				test_steps.add("Vrbo Tax: " + vrboTaxes);
			} else if (action.equalsIgnoreCase("CreateFlatTax")) {
				vrboTaxes = vrboObject.getFlatTaxDetails(driver, responseBody);
				app_logs.info("Vrbo Tax: " + vrboTaxes);
				test_steps.add("Vrbo Tax: " + vrboTaxes);
			} else if (action.equalsIgnoreCase("CreateFlatFee")) {
				vrboFees = vrboObject.getFlatFeeDetailsFromTaxesAndFee(driver, responseBody);
				app_logs.info("Vrbo Fees: " + vrboFees);
				test_steps.add("Vrbo Fees: " + vrboFees);
			} else if (action.equalsIgnoreCase("CreatePercentFee")) {
				vrboFees = vrboObject.getPercentFeeDetailsFromTaxesAndFee(driver, responseBody);
				app_logs.info("Vrbo Fees: " + vrboFees);
				test_steps.add("Vrbo Fees: " + vrboFees);
			}
			if (action.equalsIgnoreCase("CreatePercentTaxes") || action.equalsIgnoreCase("CreateFlatTax")) {
				test_steps.add("==================VERIFY TAX ON VRBO==================");
				app_logs.info("==================VERIFY TAX ON VRBO==================");
				vrboObject.verifyTaxesOnVrbo(driver, updatedTaxName, intaxValues, vrboTaxes, test_steps);
				/*
				test_steps.add("Verify when we have tax setup as percent tax for room charge when fee per stay"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804338' target='_blank'>"
						+ "Click here to open TestRail: 804338</a><br>");
				Utility.testCasePass(Utility.statusCode,2,Utility.comments,"Verify when we have tax setup as percent tax for room charge when fee per stay");			
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
				
				test_steps.add("Verify the applies per night when value  set as percent, not set as VAT"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/802296' target='_blank'>"
						+ "Click here to open TestRail: C802296</a><br>");
				Utility.testCasePass(Utility.statusCode,3,Utility.comments,"Verify the applies per night when value  set as percent, not set as VAT");			
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
				
			} else if (action.equalsIgnoreCase("CreateFlatFee") || action.equalsIgnoreCase("CreatePercentFee")) {
				test_steps.add("==================VERIFY FEE ON VRBO==================");
				app_logs.info("==================VERIFY FEE ON VRBO==================");
				vrboObject.verifyFeesOnVrbo(driver, updatedFeeName, infeeValue, vrboFees, test_steps);
				vrboObject.verifyNightOrStayInPropertyFeeOnVrbo(driver,responseBody,updatedFeeName,nightOrStayFees,categoryFees, test_steps);
				/*test_steps.add("Verify when we have tax setup as flat tax for room charge per night"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804336' target='_blank'>"
						+ "Click here to open TestRail: 804336</a><br>");
				Utility.testCasePass(Utility.statusCode,0,Utility.comments,"Verify when we have tax setup as flat tax for room charge per night");			
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
				
				test_steps.add("Verify when we have flat tax for room charge fee per stay"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/804337' target='_blank'>"
						+ "Click here to open TestRail: 804337</a><br>");
				Utility.testCasePass(Utility.statusCode,1,Utility.comments,"Verify when we have flat tax for room charge fee per stay");			
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
			}	
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Verify on vrbo",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Verify on vrbo", testName,
					test_description, test_catagory, test_steps);
		}		
		try {
			if(isInactiveTax.equalsIgnoreCase("Yes"))  {
				test_steps.add("==================INACTIVE TAX ON INNCENTER==================");
				app_logs.info("==================INACTIVE TAX ON INNCENTER==================");
				for(int i=0;i<updatedTaxName.size();i++) {
					taxFee.inactiveTax(driver, updatedTaxName.get(i), test_steps);
				}					
			}						
			
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Inactive Tax",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Inactive Tax", testName,
					test_description, test_catagory, test_steps);
		}
		
		try {
			if(isInactiveTax.equalsIgnoreCase("Yes") ||isInactiveFee.equalsIgnoreCase("Yes"))  {
				int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCode);
				vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);
				test_steps.add("==================VERIFY TAX/FEE ON VRBO AFTER INACTIVE==================");
				app_logs.info("==================VERIFY TAX/FEE ON VRBO AFTER INACTIVE==================");
				boolean flagValue=vrboObject.getTaxFlagValueforPercentage(driver, responseBody);
				assertEquals(false, flagValue, "Failed: To verify Tax");
				test_steps.add("Verified Tax/Fee"+ " : <b>" + flagValue + "</b>");
				app_logs.info("Verified Tax/Fee" +" :" + flagValue);
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Verify on vrbo",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Verify on vrbo", testName,
					test_description, test_catagory, test_steps);
		}
		
		try {
			
			if(isRateThere.equalsIgnoreCase("No")) {
				for(int i=0;i<updatedTaxName.size();i++) {
					test_steps.add("==================ACTIVE TAX ON INNCENTER==================");
					app_logs.info("==================ACTIVE TAX ON INNCENTER==================");
					taxFee.activeTax(driver, updatedTaxName.get(i), test_steps);
				}		
				
				test_steps.add("==================DELETE SEASON IN RATE==================");
				app_logs.info("==================DELETE SEASON IN RATE==================");
				navigation.inventoryFromRoomClass(driver, test_steps);
				navigation.ratesGrid(driver);
				test_steps.add("Navigated to rateGrid");
				rateGrid.verifyRatesGridLoaded(driver);
				rateGrid.clickRatePlanArrow(driver, test_steps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlanVrbo);
				rateGrid.verifyRatesGridLoaded(driver);
				rateGrid.clickEditIcon(driver, test_steps);
				nightlyRate.switchCalendarTab(driver, test_steps);
				try {
					nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList);
					nightlyRate.deleteSeasonIcon(driver, test_steps);
				} catch (Exception e) {
				}				
					nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				
				int statusCode = vrboObject.getVrboRateEndpointStatus(propertyId, classId, vrvoRateToken);
				app_logs.info(statusCode);
				vrboObject.veifyVrboClientStatusCode(statusCode, test_steps);
				Response responseBody = vrboObject.getResponseBodyOfVrboRateEndPoint(propertyId, classId, vrvoRateToken);

				test_steps.add("==================GET TAX ON VRBO==================");
				app_logs.info("==================GET TAX ON VRBO==================");
				HashMap<String, String> vrboTaxes = null;
				HashMap<String, String> vrboFees = null;
				if (action.equalsIgnoreCase("CreatePercentTaxes")) {
					vrboTaxes = vrboObject.getPercentageTax(driver, responseBody);
					app_logs.info("Vrbo Tax: " + vrboTaxes);
					test_steps.add("Vrbo Tax: " + vrboTaxes);
				}  else if (action.equalsIgnoreCase("CreatePercentFee")) {
					vrboFees = vrboObject.getPercentFeeDetailsFromTaxesAndFee(driver, responseBody);
					app_logs.info("Vrbo Fees: " + vrboFees);
					test_steps.add("Vrbo Fees: " + vrboFees);
				}
				if (action.equalsIgnoreCase("CreatePercentTaxes")) {
					test_steps.add("==================VERIFY TAX ON VRBO==================");
					app_logs.info("==================VERIFY TAX ON VRBO==================");
					vrboObject.verifyTaxesOnVrbo(driver, updatedTaxName, intaxValues, vrboTaxes, test_steps);
				} else if (action.equalsIgnoreCase("CreatePercentFee")) {
					test_steps.add("==================VERIFY FEE ON VRBO==================");
					app_logs.info("==================VERIFY FEE ON VRBO==================");
					vrboObject.verifyFeesOnVrbo(driver, updatedFeeName, infeeValue, vrboFees, test_steps);
					vrboObject.verifyNightOrStayInPropertyFeeOnVrbo(driver,responseBody,updatedFeeName,nightOrStayFees,categoryFees, test_steps);
				}	
				
				
				navigation.navSetupFromRateGrid(driver, test_steps);
				navigation.clickTaxesAndFees(driver);
				test_steps.add("Click Tax and Fee");
				app_logs.info("Click Tax and Fee");
			}
			if (isInncenterTaxCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("====================Delete Tax======================");
				for (int i = 0; i < updatedTaxName.size(); i++) {
					taxFee.deleteTaxAndFee(driver, updatedTaxName.get(i), test_steps);
				}
			}
			if (isInncenterFeeCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("====================Delete Fee======================");
				for (int i = 0; i < updatedFeeName.size(); i++) {
					taxFee.deleteTaxAndFee(driver, updatedFeeName.get(i), test_steps);
				}
			}
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, test_description, test_catagory, "Active Tax and Delete Rates ",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, test_description, test_catagory, "Active Tax and Delete Rates ", testName,
					test_description, test_catagory, test_steps);
		}
	}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyTaxesAndFeeOnVrbo", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
