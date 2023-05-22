package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class Ratev2VerifyIntervalRatePlanInRatesgrid extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	HashMap<String, String> getLengthOfStayMinMax = new HashMap<String, String>();
	HashMap<String, String> getBookingWindow = new HashMap<String, String>();
	HashMap<String, String> getRatesOfRoomClass = new HashMap<String, String>();
	HashMap<String, String> getRatesOfChannel = new HashMap<String, String>();
	HashMap<String, String> getMinStay = new HashMap<String, String>();
	HashMap<String, String> getcheckIN = new HashMap<String, String>();
	HashMap<String, String> getCheckout = new HashMap<String, String>();
	HashMap<String, Boolean> isLengthOfStay= new HashMap<String, Boolean>();
	HashMap<String, Boolean> bookingWindow= new HashMap<String, Boolean>();
	HashMap<String, Boolean> promoCodeCheck= new HashMap<String, Boolean>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservation = new CPReservationPage();
	RoomClass roomClass = new RoomClass();
	String promoCode=null,checkInDates=null,checkOutDates=null;
	
	
	String [] roomClassArray=null,channelNameArray=null;
	private void getData(HashMap<String, String> data)
	{
		Set set = data.entrySet();
		  Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         testSteps.add("key is: "+ mentry.getKey() + " & Value is: " +mentry.getValue().toString());
		      }
	}
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {	 
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);

	}
	
	@Test(dataProvider = "getData", groups = "RateGrid")
	public void VerifyIntervalRatePlanInRatesgrid(String checkInDate, String checkOutDate, String ratePlanName, String roomClassName, String channelName,String bulkUpdateType, String checkDays,
			String mininumStayValue, String totalOccupancyType, String totalOccupancyValue) throws ParseException {

		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1809' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1809</a>";
		test_catagory = "RateGrid";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		try {
			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){
				checkInDates=Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat"));
					checkOutDates=Utility.parseDate(Utility.getDatePast_FutureDate(2),
							ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));				
			}
			else{				
					checkInDates=Utility.parseDate(checkInDate, ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
					checkOutDates=Utility.parseDate(checkOutDate,ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat"));
			}
			
			app_logs.info(checkInDates);
			app_logs.info(checkOutDates);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}
		
		//Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						Utility.reTry.replace(testName, 1);
					}

					driver = getDriver();
					loginRateV2(driver);
					testSteps.add("Logged into the application");
					app_logs.info("Logged into the application");

					 roomClassArray=roomClassName.split("\\|");
					 channelNameArray=channelName.split("\\|");
					 
				} catch (Exception e) {
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
							test_catagory, testSteps);
				}
				
				try
				{
					testSteps.add("<b>============Get Active Room Classes============</b>");
					navigation.mainSetupManu(driver);
					app_logs.info("Navigate Setup");
					testSteps.add("Navigate Setup");
					navigation.roomClass(driver);
					testSteps.add("Navigate Room Class");
					roomClass.selectRoomClassStatus(driver, "Active");
					getRoomClasses = roomClass.getAllActiveRoomClasse(driver);
					testSteps.add((getRoomClasses.toString().replace("[","").replace("]","")));
					app_logs.info(getRoomClasses);
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Failed to Get Room Classes", "Room Class", "Room Class", testName, test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to Get Room Classes", "Room Class", "Room Class", testName, test_description, test_catagory, testSteps);
					
				}
				
				try {
					//navigation.inventory(driver);	
					navigation.inventory_Backward_1(driver);
					testSteps.add("Navigated to Inventory");
					navigation.ratesGrid(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				// get value from rate plan
				try {
					rateGrid.clickRatePlanArrow(driver, testSteps);
					rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);	
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					rateGrid.clickEditIcon(driver, testSteps);
					rateGrid.verifyRatePlaninEditMode(driver, testSteps, ratePlanName);
					nightlyRate.switchRestrictionAndPoliciesTab(driver, testSteps);
					getLengthOfStayMinMax=rateGrid.getLengthOfStayMinAndMaxValue(driver, ratesConfig.getProperty("lengthOfStay"), ratesConfig.getProperty("min"), ratesConfig.getProperty("max"),isLengthOfStay);					
					getBookingWindow=rateGrid.getBookingWindowValue(driver, ratesConfig.getProperty("bookingWindow"),  ratesConfig.getProperty("moreThan"), ratesConfig.getProperty("winthIn"), testSteps,bookingWindow);
					promoCode=rateGrid.getPromocodeValue(driver, "Promo code",testSteps,promoCodeCheck);
					
					app_logs.info(getLengthOfStayMinMax);
					app_logs.info(getBookingWindow);
					app_logs.info(promoCode);
					rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
					
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "get value from rate plan", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "get value from rate plan", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				
				
				
				// Verify Condition and Description
				try {
					
					if(isLengthOfStay.get(ratesConfig.getProperty("lengthOfStay"))) {
						if(Utility.validateString(getLengthOfStayMinMax.get("Min"))) {
						   rateGrid.verifyRatePlanDescription(driver, "Interval rate plan", testSteps, getLengthOfStayMinMax.get("Min"));}
						else if(Utility.validateString(getLengthOfStayMinMax.get("Max"))) {
							rateGrid.verifyRatePlanDescription(driver, "Interval rate plan", testSteps, getLengthOfStayMinMax.get("Max"));
						}
					   }
				   if(isLengthOfStay.get(ratesConfig.getProperty("lengthOfStay"))) {
					   rateGrid.verifiedConditionsStayNight(driver, testSteps, getLengthOfStayMinMax.get("Min"), getLengthOfStayMinMax.get("Max"),isLengthOfStay.get(ratesConfig.getProperty("min")),  isLengthOfStay.get(ratesConfig.getProperty("max")));		   
				   }
				   if(bookingWindow.get(ratesConfig.getProperty("bookingWindow"))) {
					   rateGrid.verifiedConditionsBookAdvanceDay(driver, testSteps, getBookingWindow.get("More than"), getBookingWindow.get("Within"), bookingWindow.get(ratesConfig.getProperty("moreThan")), bookingWindow.get(ratesConfig.getProperty("winthIn")));
				   }
				   if(promoCodeCheck.get("PromoCode")) {
					   rateGrid.verifiedConditionsPromoCode(driver, testSteps, promoCode);
				   }
		
				   
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Condition and Description ", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Condition and Description", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				//Verify rate Plan Room Class
				try {
					testSteps.add("<b>======Verify All Active Room Classes on Rate Plan ========</b>");
					rateGrid.verifyRoomClass(driver, getRoomClasses, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Rate Plan Room Class", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Rate Plan Room Class", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Get Rate on basis of check in and checkout date
				try {
					 roomClassArray=roomClassName.split("\\|");
					 channelNameArray=channelName.split("\\|");
						for(int i=0;i<roomClassArray.length;i++) {
						getRatesOfRoomClass=rateGrid.getRatesOfRoomClass(driver, checkInDates, checkOutDates, roomClassArray[i]);
						testSteps.add("<b>======Get Rate of Room Classes "+roomClassArray[i]+"========</b>");
						getData(getRatesOfRoomClass);
						rateGrid.expandRoomClass(driver, testSteps, roomClassArray[i]);
						for(int j=0;j<channelNameArray.length;j++) {
						getRatesOfChannel=rateGrid.getRatesOfChannel(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j]);
						testSteps.add("<b>======Get Rate of Channel "+channelNameArray[j]+"of Room Class"+roomClassArray[i]+"========</b>");
						getData(getRatesOfChannel);
						rateGrid.expandChannel(driver, testSteps, roomClassArray[i], channelNameArray[j]);
						getMinStay=rateGrid.getMinStayRulesOfChannel(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("minStay"));
						testSteps.add("<b>======Get Min Stay Rule of Room Class"+roomClassArray[i]+"========</b>");
						getData(getMinStay);
						getcheckIN=rateGrid.getCheckInCheckOutRulesOfChannel(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("checkinRule"));
						testSteps.add("<b>======Get No Check In Rule of Room Class"+roomClassArray[i]+"========</b>");
						getData(getcheckIN);
						getCheckout=rateGrid.getCheckInCheckOutRulesOfChannel(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("checkoutRule"));
						testSteps.add("<b>======Get No Check Out Rule of Room Class"+roomClassArray[i]+"========</b>");
						getData(getCheckout);
						}
						rateGrid.collapseRoomClass(driver, testStepsOne, roomClassArray[i]);
					}

				}catch (Exception e) {
					Utility.catchException(driver, e, "Get Rate on basis of checkin and checkout date", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Get Rate on basis of checkin and checkout date", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Verify Extra Adult and Child should not be editable
				try {
					testSteps.add("<b>======Verify Rate,Extra Adult and Extra Child on Rate Plan ========</b>");
					for(int i=0;i<roomClassArray.length;i++) {
					rateGrid.clickRateOfRoomClass(driver, roomClassArray[i], checkInDates, checkOutDates);
					rateGrid.verifyReadOnlyRateExtraAdultAndChild(driver, testSteps);
					rateGrid.clickCloseRateWindow(driver);
					rateGrid.expandRoomClass(driver, testSteps, roomClassArray[i]);
					for(int j=0;j<channelNameArray.length;j++) {
						rateGrid.clickRateOfChannel(driver, roomClassArray[i], channelNameArray[j],checkInDates, checkOutDates);
						rateGrid.verifyReadOnlyRateExtraAdultAndChild(driver, testSteps);
						rateGrid.clickCloseRateWindow(driver);
					}
					}
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Extra Adult and Child should not be editable", "RateGrid", "RateGrid", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Extra Adult and Child should not be editable", "RateGrid", "RateGrid", testName, test_description,
							test_catagory, testSteps);
				}
				
				try
				{
					testSteps.add("<b>============Verify Show room availability Disabled ============</b>");
					testStepsOne=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.showroomavailability(driver, false);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsOne);					
					rateGrid.verifyAvailibilityDisabled(driver, testSteps);
					testSteps.add("<b>============Verify Show room availability Enabled============</b>");
					testStepsOne=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.showroomavailability(driver, true);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsOne);	
					rateGrid.verifyAvailibilityEnabled(driver, testSteps);
			
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);

				}
				
				try
				{
					testSteps.add("<b>============Verify Show additional adult and child Disabled ============</b>");
					testStepsOne=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.changeStateShowAdditionalAdultAdditionalChilToggalSettingContainer(driver, false);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsOne);	
					rateGrid.clickRateOfRoomClass(driver, roomClassArray[0], checkInDates, checkOutDates);
					rateGrid.verifyRateExAdExChDisabledOrEnabled(driver, testSteps, false);
					testSteps.add("<b>============Verify Show additional adult and child Enabled============</b>");
					testStepsOne=rateGrid.clickSettingButton(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.changeStateShowAdditionalAdultAdditionalChilToggalSettingContainer(driver, true);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.clickSettingButtonAgain(driver);
					testSteps.addAll(testStepsOne);	
					rateGrid.clickRateOfRoomClass(driver, roomClassArray[0], checkInDates, checkOutDates);
					rateGrid.verifyRateExAdExChDisabledOrEnabled(driver, testSteps, true);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Show room availability", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);

				}
				
				// Collapse and Expand RatePlan and Get Best Available
				try
				{
					testSteps.add("<b>============Collapse Rate Plan ============</b>");
					rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
					testSteps.add("<b>============Get Best Available Rates And Total Room Class ============</b>");
					rateGrid.getRegularRates(driver, testSteps);
					rateGrid.getBestAvailableRoomClass(driver, testSteps);
					testSteps.add("<b>============Expand Rate Plan ============</b>");
					rateGrid.clickExpandIconOfRatePlan(driver, testSteps);
					}
				catch (Exception e) {			
					Utility.catchException(driver, e, "Collapse and Expand RatePlan and Get Best Available", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Collapse and Expand RatePlan and Get Best Available", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);					
			}
				
				try {
					testSteps.add("<b>============Get Rules ============</b>");
					ArrayList<String> ruleLabels= new ArrayList<>();
					ArrayList<String> ruleImages= new ArrayList<>();
					rateGrid.hoverRuleIndicationOfClass(driver, roomClassArray[0], testSteps);
					rateGrid.getRuleDate(driver, testSteps) ; 
					rateGrid.getRuleRate(driver, testSteps);
					ruleLabels=rateGrid.getRulesLabels(driver);
					ruleImages=rateGrid.getRuleImages(driver);
					for(int i=0;i< ruleImages.size();i++)
					 {
						testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+" <b> "+ruleLabels.get(i)+" </b>");
					 }
					 
				
		
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Get rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Get rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);					
			}
				
				try {
					HashMap<String, String> totalOccupancy = new HashMap<String, String>();
					totalOccupancy=rateGrid.getTotalOccupancy(driver, checkInDates, checkOutDates);
					app_logs.info(totalOccupancy);
					
					
					testSteps.add("<b>============Bulk Update Rule============</b>");					
					testStepsOne=rateGrid.clickOnBulkUpdate(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne=rateGrid.selectBulkUpdateOption(driver, bulkUpdateType);
					testSteps.addAll(testStepsOne);
					String checkin= Utility.parseDate(checkInDates, ratesConfig.getProperty("defaultDateFormat"),ratesConfig.getProperty("monthDateYearFormat"));
					String checkout=Utility.parseDate(checkOutDates, ratesConfig.getProperty("defaultDateFormat"),ratesConfig.getProperty("monthDateYearFormat"));
					app_logs.info(checkin);
					app_logs.info(checkout);
					rateGrid.selectStartDate(driver, checkin);					
					rateGrid.selectEndDate(driver, checkout);
					String day[]= checkDays.split("\\|");
					for(int i=0;i<day.length;i++) {
						testStepsOne = rateGrid.bulkUpdatePoppupDayCheck(driver,  day[i], config.getProperty("flagOn"));
						testSteps.addAll(testStepsOne);
					}
							
							testStepsOne = rateGrid.clickTotalOccupancy(driver, config.getProperty("flagOn"));
							testSteps.addAll(testStepsOne);
							for (Map.Entry<String, String> entry : totalOccupancy.entrySet()) {
								String [] OccupancyType=totalOccupancyType.split("\\|");
								if(Integer.valueOf(entry.getValue())>Integer.valueOf(totalOccupancyValue)) {												
							testStepsOne = rateGrid.selectTotalOccupancyType(driver, OccupancyType[0]);
							testSteps.addAll(testStepsOne);
								break;
								}else if(Integer.valueOf(entry.getValue())<Integer.valueOf(totalOccupancyValue)){
									testStepsOne = rateGrid.selectTotalOccupancyType(driver, OccupancyType[1]);
									testSteps.addAll(testStepsOne);
									break;
								}
							}
							testStepsOne = rateGrid.enterOccupancyValue(driver, totalOccupancyValue);
							testSteps.addAll(testStepsOne);	
							rateGrid.selectBulkUpdateRatePlan(driver,  ratePlanName, testSteps);
					    	rateGrid.selectBulkUpdateRoomClass(driver,  roomClassName, testSteps);					
							rateGrid.selectBulkUpdateSource(driver,  channelName, testSteps);
					testStepsOne = rateGrid.clickMinimumStay(driver, config.getProperty("flagOn"));
					testSteps.addAll(testStepsOne);
					testStepsOne =rateGrid.enterMinimumStayValue(driver, mininumStayValue);
					testSteps.addAll(testStepsOne);
					testStepsOne = rateGrid.clickCheckin(driver, config.getProperty("flagOn"));
					testSteps.addAll(testStepsOne);
					testStepsOne =rateGrid.clickNoCheckInCheckbox(driver, config.getProperty("flagOn"));
					testSteps.addAll(testStepsOne);
					testStepsOne = rateGrid.clickCheckOut(driver, config.getProperty("flagOn"));
					testSteps.addAll(testStepsOne);
					testStepsOne =rateGrid.clickNoCheckOutCheckbox(driver, config.getProperty("flagOn"));
					testSteps.addAll(testStepsOne);					
					testStepsOne =rateGrid.clickUpdateButton(driver);
					testSteps.addAll(testStepsOne);
					testStepsOne =rateGrid.clickYesUpdateButton(driver);
					testSteps.addAll(testStepsOne);
					Wait.waitUntilPageLoadNotCompleted(driver, 50);
					HashMap<String, String> minStay = new HashMap<String, String>();
					HashMap<String, String> checkIn = new HashMap<String, String>();
					HashMap<String, String> checkOut = new HashMap<String, String>();
					for(int i=0;i<roomClassArray.length;i++) {
						rateGrid.expandRoomClass(driver, testSteps, roomClassArray[i]);
						for(int j=0;j<channelNameArray.length;j++) {
							rateGrid.expandChannel(driver, testSteps, roomClassArray[i], channelNameArray[j]);
							testSteps.add("<b>======Verify Rules After Bulk Update"+roomClassArray[i]+"========</b>");
							minStay=	rateGrid.verifyBulkUpdateRuleForMinStay(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("minStay"), mininumStayValue);
							checkIn=	rateGrid.verifyBulkUpdateRuleForCheckInCheckout(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("checkinRule"));
							checkOut=	rateGrid.verifyBulkUpdateRuleForCheckInCheckout(driver, checkInDates, checkOutDates, roomClassArray[i], channelNameArray[j], ratesConfig.getProperty("checkoutRule"));
							rateGrid.collapseChannel(driver, testSteps, roomClassArray[i], channelNameArray[j]);
							getData(minStay);
							getData(checkIn);
							getData(checkOut);
							
						}
						rateGrid.collapseRoomClass(driver, testSteps, roomClassArray[i]);
					}
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

				}catch (Exception e) {			
					Utility.catchException(driver, e, "Bulk Update", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Bulk Update", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);					
			}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyIntervalRatePlan", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}



}
