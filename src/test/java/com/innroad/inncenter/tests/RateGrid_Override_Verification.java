package com.innroad.inncenter.tests;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;



public class RateGrid_Override_Verification extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> channelNames = new ArrayList<>();
	String testName= this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid= new RatesGrid();
	Admin admin = new Admin();
	Properties properties = new Properties();
	NightlyRate nightltRate= new NightlyRate();
	ArrayList<String> ratePlanNames = new ArrayList<>();
	ArrayList<String> ruleLabels= new ArrayList<>();
	ArrayList<String> ruleImages= new ArrayList<>();
	List<Date> dates = new ArrayList<Date>();
	String [] roomClass=null;	String [] rates=null;
	String dateIs=null, seasonStartDate=null,seasonEndDate=null,timeZone = null,propertyName = null, dateTimeZoneBasis=null,
			oldRateIs=null, newRateIs=null, defaultCurrency=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	
		@Test(dataProvider = "getData", groups = "RateGrid")
		public void rateGridOverrideVerification(String delim,String planType, String ratePlanName, String folioDisplayName,
				String description, String channels, String roomClasses,
				String isRatePlanRistrictionReq, String ristrictionType, String isMinStay, String minNights,
				String isMaxStay, String maxNights, String isMoreThanDaysReq, String moreThanDaysCount,
				String isWithInDaysReq, String withInDaysCount, String promoCode,
				 String seasonName, String isMonDay, String isTueDay, String isWednesDay,
				String isThursDay, String isFriday, String isSaturDay, String isSunDay,
				String isAdditionalChargesForChildrenAdults, String ratePerNight, String maxAdults, String maxPersons,
				String additionalAdultsPerNight, String additionalChildPerNight, String isAddRoomClassInSeason,
				String extraRoomClassesInSeason, String extraRoomClassRatePerNight, String extraRoomClassMaxAdults,
				String extraRoomClassMaxPersons, String extraRoomClassAdditionalAdultsPerNight,
				String extraRoomClassAdditionalChildPerNight, String isSerasonLevelRules,String isAssignRulesByRoomClass,
				String seasonRuleSpecificRoomClasses, String seasonRuleType, String seasonRuleMinStayValue,
				String isSeasonRuleOnMonday, String isSeasonRuleOnTuesday, String isSeasonRuleOnWednesday,
				String isSeasonRuleOnThursday, String isSeasonRuleOnFriday, String isSeasonRuleOnSaturday,
				String isSeasonRuleOnSunday, String newNight, String rate, String newAdult,String seasonPolicyType, String seasonPolicyValues,String isSeasonPolicies, String manualRatePLanName)
		{
			test_description = testName + "<br>"
					+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1699' target='_blank'>"
					+ "Click here to open Jira: AUTOMATION-1699</a>";
			test_catagory = "RateGrid";
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			
			boolean isExists=false;
		//	Utility.rateplanName="TestRateZZOqLru2Ra";
			try
			{
			    seasonStartDate=Utility.getCurrentDate("dd/MM/yyyy");
				seasonEndDate=Utility.parseDate(Utility.getDatePast_FutureDate(6), "MM/dd/yyyy", "dd/MM/yyyy");
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
			

			}catch (Exception e) {			
				Utility.catchException(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description, test_catagory, testSteps);
				
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
				//loginWPI(driver);
				loginRateV2(driver);
				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");
						
			} catch (Exception e) {			
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, testSteps);
				
			}
			
			try
			{
				propertyName = properties.getRateV2Property(driver, testSteps);
				navigation.setup(driver);
				navigation.properties(driver);
				navigation.open_Property(driver, testSteps, propertyName);
				navigation.click_PropertyOptions(driver, testSteps);
				timeZone = navigation.get_Property_TimeZone(driver);
				testSteps.add("Get TimeZone: <b> "+timeZone+ " </b>");			
				dateTimeZoneBasis=Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat"), timeZone);
				app_logs.info(dateTimeZoneBasis);				
				String weekDay=Utility.parseDate(dateTimeZoneBasis, ratesConfig.getProperty("defaultDateFormat"), ratesConfig.getProperty("weekDayFormat"));
				String monthDate=Utility.parseDate(dateTimeZoneBasis, ratesConfig.getProperty("defaultDateFormat"), ratesConfig.getProperty("monthDateFormat"));
				dateIs=weekDay+", "+monthDate;
				app_logs.info(dateIs);
		
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Get Time Zone and Date on Basis", "Get Date on Basis timeZone", "Get Date", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Get Time Zone and Date on Basis","Get Date on Basis timeZone", "Get Date", testName, test_description, test_catagory, testSteps);
				
			}
			
			try
			{
				navigation.admin(driver);
				navigation.navigateToClientInfoPage(driver, testSteps);
				navigation.open_Property(driver, testSteps, propertyName);
				navigation.openClientDetailsOptionsTab(driver, testSteps);
				defaultCurrency=admin.getDefaultCurrency(driver, testSteps);
				app_logs.info(defaultCurrency);
			}
			catch (Exception e) {			
				Utility.catchException(driver, e, "Get Default Currency ", "Get Default Currency", "Get Default Currency", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Get Default Currency","Get Default Currency", "Get Default Currency", testName, test_description, test_catagory, testSteps);
				
			}
			
			//Navigate to Rate Grid
			try
				{
					//navigation.inventory(driver);
					navigation.inventoryBackwardAdmin(driver);
				//	testSteps.add("Navigated to Inventory");
					navigation.ratesGrid(driver,testSteps);	
					 
				}
				catch (Exception e) {			
						Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
						
				}
			
			//Create Rate Plan
			try
			{
				if(!Utility.validateString(Utility.rateplanName)) {
				testSteps.add("<b>============Create new Rate Plan============</b>");				
				Utility.DELIM = "\\"+delim;
				nightltRate.createNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne, description, channels, 
						roomClasses, isRatePlanRistrictionReq, ristrictionType, isMinStay, 
						minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq, withInDaysCount, 
						promoCode, seasonName,seasonStartDate,seasonEndDate ,isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, 
						isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, 
						additionalChildPerNight, isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight, 
						extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight, 
						extraRoomClassAdditionalChildPerNight, isSerasonLevelRules,isAssignRulesByRoomClass, seasonRuleSpecificRoomClasses, 
						seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday, isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, 
						isSeasonRuleOnThursday, isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday,seasonPolicyType,seasonPolicyValues,isSeasonPolicies);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				testSteps.add("Rate Plan Created Successfully : <b>"+Utility.rateplanName+" </b>");	
				isExists=true;
				}
				else {
					testSteps.add("Rate Plan Already Exist : <b>"+Utility.rateplanName+" </b>");
				}
			}
			catch (Exception e) {			
				Utility.catchException(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Nightly Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			
			try
			{
				testSteps.add("<b>============Get Color of Room Class Square Box and Color of Availibility============</b>");				
				rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
				rateGrid.getColorOfSquareBox(driver, testSteps);
				rateGrid.getColorOfAvailabilityBox(driver, testSteps);				
				testSteps.add("<b>============Select Rate Plan from Drop Down Box============</b>");				
				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, Utility.rateplanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				roomClass= roomClasses.split("\\|");
				app_logs.info(roomClass[0]);
				app_logs.info(roomClass[1]);				
				rates=ratePerNight.split("\\|");
				app_logs.info(rates[0]);
				app_logs.info(rates[1]);		
									
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Select RatePlan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Select RatePlan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
		
			try
			{
				rateGrid.expandRoomClass(driver, testSteps, roomClass[0]);
				channelNames=rateGrid.getChannelofSpecificRoomClass(driver, roomClass[0]);
				app_logs.info(channelNames);
				testSteps.add("====Updated Rule For Room Class <b> "+roomClass[0]+" </b> Channel<b> "+channelNames.get(0)+"</b>====");				
				rateGrid.expandChannel(driver, testSteps, roomClass[0], channelNames.get(0));
				rateGrid.updateRuleForMinStay(driver, testSteps, roomClass[0], channelNames.get(0), ratesConfig.getProperty("minStay"),  newNight,1);				
				rateGrid.updateRuleForNoCheckInAndOut(driver, testSteps,  roomClass[0], channelNames.get(0), ratesConfig.getProperty("checkinRule"),1);
				rateGrid.updateRuleForNoCheckInAndOut(driver, testSteps, roomClass[0], channelNames.get(0), ratesConfig.getProperty("checkoutRule"),1);
				}
			catch (Exception e) {			
				Utility.catchException(driver, e, "Update Rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Rule", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			try
			{
				testSteps.add("====Verify Applied Rule On Class and Channel Level<b> "+roomClass[0]+"--"+channelNames.get(0)+"</b>====");
				rateGrid.hoverRuleIndicationOfClass(driver, roomClass[0],testSteps);
				rateGrid.verifyRuleDate(driver, testSteps, dateIs);
				ruleLabels=rateGrid.getRulesLabels(driver);
				ruleImages=rateGrid.getRuleImages(driver);
				 for(int i=0;i< ruleImages.size();i++)
				 {
					 String  label=rateGrid.verifyRulesLabels(driver, ruleLabels.get(i));
					 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+" <b> "+label+" </b>");
				 }
			    
			}
			catch (Exception e) {			
				Utility.catchException(driver, e, "Verify Rule for Class", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Verify Rule for Class", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			try
			{
				
				 rateGrid.hoverRuleIndicationOfChannel(driver, roomClass[0], channelNames.get(0), testSteps);
				 rateGrid.verifyRuleDate(driver, testSteps, dateIs);
				 ruleLabels=rateGrid.getRulesLabels(driver);
				 ruleImages=rateGrid.getRuleImages(driver);
				 for(int i=0;i< ruleImages.size();i++)
					 {
						 String  label=rateGrid.verifyRulesLabels(driver, ruleLabels.get(i));
						 testSteps.add("<img src='"+ruleImages.get(i)+"' width='15' height='15'>"+" <b> "+label+" </b>");
					 }
						
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Verify Rule for Class of Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Verify Rule for Class of Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			try
			{
				
				testSteps.add("====Removed Rule and Verify On Class and Channel Level<b> "+roomClass[0]+"--"+channelNames.get(0)+"</b>====");
				rateGrid.removeRuleForMinStay(driver, testSteps, roomClass[0], channelNames.get(0), ratesConfig.getProperty("minStay"), 1);
				rateGrid.removeRuleForNoCheckInAndOut(driver, testSteps, roomClass[0], channelNames.get(0), ratesConfig.getProperty("checkinRule"), 1);
				rateGrid.removeRuleForNoCheckInAndOut(driver, testSteps, roomClass[0], channelNames.get(0), ratesConfig.getProperty("checkoutRule"), 1);
			//	rateGrid.verifyRulesRemovedForClass(driver, testSteps, roomClass[0], 1);
				rateGrid.verifyRulesRemovedForChannel(driver, testSteps, roomClass[0], channelNames.get(0), 1);
				rateGrid.collapseChannel(driver, testSteps, roomClass[0], channelNames.get(0));	
				rateGrid.collapseRoomClass(driver, testSteps,  roomClass[0]);
	
				
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Delete Rule and Verify Rule for Class and  Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Delete Rule and Verify Rule for Class and  Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			try
			{
				String oldMinStayRule=null, oldMinStayRuleForChannel1=null,oldMinStayRuleForChannel2=null;
				ArrayList<String> ruleLabels= new ArrayList<String>();
				ArrayList<String> ruleLabelsOne= new ArrayList<String>();
				ArrayList<String> ruleLabelsTwo= new ArrayList<String>();			
				rateGrid.expandRoomClass(driver, testSteps, extraRoomClassesInSeason);
				rateGrid.expandChannel(driver, testSteps, extraRoomClassesInSeason, channelNames.get(0));
				testSteps.add("====Update Min Stay Rule For Channel<b> "+extraRoomClassesInSeason+ "--" +channelNames.get(0)+" </b>====");
				ArrayList<String> updateValuesAre= new ArrayList<String>();
				updateValuesAre=	rateGrid.updateRuleForMinStay(driver, testSteps, extraRoomClassesInSeason, channelNames.get(0), ratesConfig.getProperty("minStay"));
				app_logs.info(updateValuesAre);
				ruleLabels.removeAll(ruleLabels);
				ruleLabelsOne.removeAll(ruleLabelsOne);
				ruleLabelsTwo.removeAll(ruleLabelsTwo);
	/*			testSteps.add("====Get Min Stay Rule For All Channel of Room Class <b> "+extraRoomClassesInSeason+"</b> After Update Min Stay====");
				rateGrid.hoverRuleIndicationOfChannel(driver, extraRoomClassesInSeason, channelNames.get(0), testSteps);
				ruleLabelsOne=rateGrid.getRulesLabels(driver);
				for(String str: ruleLabelsOne)
				{
					if(str.contains(ratesConfig.getProperty("nightMinLabel")))
					{
						oldMinStayRuleForChannel1=str;
						testSteps.add("Room Class:  <b> "+extraRoomClassesInSeason+" </b>Channel: <b>"+channelNames.get(0)+" </b>Min Stay Value is: <b> "+oldMinStayRuleForChannel1+"</b>");
						app_logs.info(oldMinStayRuleForChannel1);
						break;
					}
				}
				
				rateGrid.hoverRuleIndicationOfChannel(driver, extraRoomClassesInSeason, channelNames.get(1), testSteps);
			//	rateGrid.hoverRuleIndicationOfChannel(driver, extraRoomClassesInSeason, channelNames.get(0), testSteps);
				ruleLabelsTwo=rateGrid.getRulesLabels(driver);
				for(String str: ruleLabelsTwo)
				{
					if(str.contains(ratesConfig.getProperty("nightMinLabel")))
					{
						oldMinStayRuleForChannel2=str;
						testSteps.add("Room Class:  <b> "+extraRoomClassesInSeason+" </b>Channel: <b>"+channelNames.get(1)+" </b>Min Stay Value is: <b> "+oldMinStayRuleForChannel2+"</b>");					
						app_logs.info(oldMinStayRuleForChannel2);
						break;
					}
				}
	*/			testSteps.add("====Verify Max Value of Min Stay Rule For Room Class<b>"+extraRoomClassesInSeason+"</b>====");				
				rateGrid.hoverRuleIndicationOfClass(driver, extraRoomClassesInSeason, testSteps);
				ruleLabels=rateGrid.getRulesLabels(driver);
				for(String str: ruleLabels)
				{
					if(str.contains(ratesConfig.getProperty("nightMinLabel")))
					{
						oldMinStayRule=str;
					//	rateGrid.verifyRulesLabels(driver, oldMinStayRuleForChannel1);
						rateGrid.verifyRulesLabels(driver, updateValuesAre.get(1));
						testSteps.add("Room Class:  <b> "+extraRoomClassesInSeason+" </b>Min Stay Value is: <b> "+oldMinStayRule+"</b>");
						app_logs.info(oldMinStayRule);
						break;
					}
				}
				
				rateGrid.collapseChannel(driver, testSteps, extraRoomClassesInSeason, channelNames.get(0));
				rateGrid.collapseRoomClass(driver, testSteps, extraRoomClassesInSeason);
				
				
			
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Verify Max Min Stay Value for Room Class", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Verify Max Min Stay Value for Room Class", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			try
			{
				testSteps.add("====Update Rule If Rate Has <b>--</b> of  Class and Channel <b> "+roomClass[1]+"--"+channelNames.get(0)+"</b>====");
				rateGrid.expandRoomClass(driver, testSteps, roomClass[1]);
				rateGrid.expandChannel(driver, testSteps, roomClass[1], channelNames.get(0));
				rateGrid.updateRuleForMinStayForBlankRate(driver, testSteps, roomClass[1], channelNames.get(0), ratesConfig.getProperty("minStay"), newNight);
				rateGrid.updateRuleForNoCheckInAndOutForBlankRate(driver, testSteps, roomClass[1], channelNames.get(0), ratesConfig.getProperty("checkinRule"));
				rateGrid.updateRuleForNoCheckInAndOutForBlankRate(driver, testSteps, roomClass[1], channelNames.get(0), ratesConfig.getProperty("checkoutRule"));
				testSteps.add("====Verify Rate of  Class and Channel <b> "+roomClass[1]+"--"+channelNames.get(0)+"</b>====");
				rateGrid.hoverRuleIndicationOfClassOfBlankRate(driver, roomClass[1], testSteps);
				rateGrid.verifyRuleRate(driver, testSteps, "");
				rateGrid.hoverRuleIndicationOfChannelOfBlankRate(driver,  roomClass[1], channelNames.get(0), testSteps);
				rateGrid.verifyRuleRate(driver, testSteps, "");
				testSteps.add("====Verify Rate On Best Available====");
				rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
				rateGrid.hoverBestAvailableRateForBlankRate(driver, testSteps, 0);
				rateGrid.verifyRuleRate(driver, testSteps, "");
				rateGrid.hoverOnBestAvailableRateLabel(driver);
				rateGrid.clickExpandIconOfRatePlan(driver, testSteps);
				
				
				
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Update Rule For Blank Rate of Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Rule For Blank Rate of Channel", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
			
		try
			{
				testSteps.add("<b>======Update Rate At Class Level and Verify Override at  Class and Channel Level========</b>");
				ArrayList<String> updateRate=new ArrayList<String>();
				rateGrid.expandRoomClass(driver, testSteps, roomClass[0]);
				updateRate=rateGrid.updateRate(driver, testSteps, roomClass[0]);
				oldRateIs=updateRate.get(0);
				newRateIs=updateRate.get(1);
				app_logs.info(oldRateIs);	
				app_logs.info(newRateIs);
				rateGrid.verifyOverRideRateAtChannellevel(driver, testSteps, roomClass[0], channelNames.get(0), newRateIs);
				rateGrid.verifyOverRideRateAtChannellevel(driver, testSteps, roomClass[0], channelNames.get(1), newRateIs);
				testSteps.add("<b>======Verify Override Rate Color at  Class and Channel Level========</b>");
				String colorName=rateGrid.getOverrideRateColor(driver, testSteps, roomClass[0], newRateIs);
				rateGrid.verifyOverrideRateColor(driver, testSteps, roomClass[0], newRateIs,colorName);
				rateGrid.verifyOverrideRateColorAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), newRateIs, colorName);
				rateGrid.verifyOverrideRateColorAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(1), newRateIs, colorName);				
				testSteps.add("<b>======Verify Previous Rate and Image at  Class and Channel Level========</b>");
				rateGrid.hoverRuleRate(driver, roomClass[0], newRateIs,testSteps);
				rateGrid.verifyPreviousPrice(driver, ratesConfig.getProperty("previousPrice"), oldRateIs,testSteps);
				rateGrid.verifyImagesOnRulePopup(driver, testSteps, ratesConfig.getProperty("previousPrice"), ratesConfig.getProperty("greenTrangle"));
				rateGrid.hoverOverRideRateAtChannelLevel(driver, roomClass[0], channelNames.get(0), newRateIs, testSteps);
				rateGrid.verifyPreviousPrice(driver, ratesConfig.getProperty("previousPrice"), oldRateIs,testSteps);
				rateGrid.verifyImagesOnRulePopup(driver, testSteps, ratesConfig.getProperty("previousPrice"), ratesConfig.getProperty("greenTrangle"));
				rateGrid.hoverOverRideRateAtChannelLevel(driver, roomClass[0], channelNames.get(1), newRateIs, testSteps);
				rateGrid.verifyPreviousPrice(driver, ratesConfig.getProperty("previousPrice"), oldRateIs,testSteps);
				rateGrid.verifyImagesOnRulePopup(driver, testSteps, ratesConfig.getProperty("previousPrice"), ratesConfig.getProperty("greenTrangle"));
				testSteps.add("<b>======Verify Previous Rate , Image and OverRide Color at Best Available Rates Level ========</b>");
				rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
				rateGrid.verifyOverrideRateColorAtBestVailableRates(driver, testSteps, newRateIs, colorName);				
				rateGrid.hoverBestAvailableRatesOfOverRide(driver, updateRate, 0);
				rateGrid.verifyPreviousPrice(driver, ratesConfig.getProperty("previousPrice"), oldRateIs,testSteps);
				rateGrid.verifyImagesOnRulePopup(driver, testSteps, ratesConfig.getProperty("previousPrice"), ratesConfig.getProperty("greenTrangle"));								
				rateGrid.hoverOnBestAvailableRateLabel(driver);
				rateGrid.clickExpandIconOfRatePlan(driver,  testSteps);
				testSteps.add("<b>======Removed OverRide at Class Level and Verify Color At Class and Channel Level and Best Available Rates========</b>");
				 rateGrid.removeOverRide(driver, testSteps, roomClass[0], newRateIs, oldRateIs);
				 rateGrid.expandRoomClass(driver, testSteps, roomClass[0]);					
				 String colorNameOld=rateGrid.getRegularRateColor(driver, testSteps, roomClass[0], oldRateIs);
				 rateGrid.verifyRagularRateColor(driver,  testSteps, roomClass[0], oldRateIs, colorNameOld);
				 rateGrid.verifyRegularRateColorAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(0), oldRateIs, colorNameOld);
				rateGrid.verifyRegularRateColorAtChannelLevel(driver, testSteps, roomClass[0], channelNames.get(1), oldRateIs, colorNameOld);				
				rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
				rateGrid.verifyRegularRateColorAtBestVailableRates(driver, testSteps, oldRateIs, colorNameOld);	
				rateGrid.clickExpandIconOfRatePlan(driver,  testSteps);
					
				 
				 
				
			}catch (Exception e) {			
				Utility.catchException(driver, e, "Override Rate and Verify Color of OverRide", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Override Rate and Verify Color of OverRide", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
				
		}
		
	try
	{
		rateGrid.expandRoomClass(driver, testSteps, roomClass[0]);
		testSteps.add("<b>======Verified Changes Discarded On Click Cross Icon In the Override popup ========</b>");
		rateGrid.verifyCancelToUpdateRateAtClassLevel(driver, testSteps, roomClass[0], rates[0], 0);
		testSteps.add("<b>======Update Rate At Channel Level and Verify at Class Level, Channel Level========</b>");
		ArrayList<String> rateIs= new ArrayList<String>();
		rateIs=rateGrid.updateRateForChannel(driver, testSteps, roomClass[0], channelNames.get(0), rates[0], 0, Integer.parseInt(rate),ratesConfig.getProperty("decreaseText"));
		app_logs.info(rateIs);
		String oldRate=rateIs.get(0);
		String newRate=rateIs.get(1);
		rateGrid.verifyOverideRateAtClasslevel(driver, testSteps, roomClass[0], newRate);
		rateGrid.verifyOverRideRateAtChannellevel(driver, testSteps, roomClass[0], channelNames.get(0), newRate);
		rateGrid.verifyRegularRateAtChannelLevel(driver, testSteps,roomClass[0], channelNames.get(1), rates[0]);		
		testSteps.add("<b>======Verify Red Traingle if Channels has Different Rate========</b>");
		rateGrid.verifyRedTrainingAtClassLevel(driver, testSteps, roomClass[0]);
		testSteps.add("<b>======Verify Source Has Different Price at Class Level========</b>");
		rateGrid.hoverRuleRate(driver, roomClass[0], newRate);
		rateGrid.verifySourceHaveDifferentPrice(driver, testSteps);
		rateGrid.verifyPreviousPrice(driver, ratesConfig.getProperty("previousPrice"), oldRate,testSteps);
		rateGrid.verifyImagesOnRulePopup(driver, testSteps, ratesConfig.getProperty("previousPrice"), ratesConfig.getProperty("redTraingle"));
		testSteps.add("<b>======Verify Best Available Rates If Source Has Different Price========</b>");
		rateGrid.clickCollapseIconOfRatePlan(driver, testSteps);
		rateGrid.verifyOverrideRateAtBestVailableRates(driver, testSteps, newRate);
		rateGrid.clickExpandIconOfRatePlan(driver,  testSteps);
		testSteps.add("<b>======Verify <b>No Arrow</b> For Previous Price Once Update Extra Adult/Extra Child for Override========</b>");
		rateGrid.expandRoomClass(driver, testSteps, roomClass[0]);
		rateGrid.updateExtraAdultOfOverRideForChannel(driver, testSteps, roomClass[0], channelNames.get(0), newRate, Integer.parseInt(newAdult), ratesConfig.getProperty("increaseText"));
		rateGrid.hoverRuleRate(driver, roomClass[0], newRate);
		rateGrid.verifyImageRemovedAtTooltip(driver, testSteps, ratesConfig.getProperty("previousPrice"));
		testSteps.add("<b>======Verify Default Currency at ToolTip========</b>");
		rateGrid.verifyDefaultCurrenyAtToolTip(driver, testSteps, defaultCurrency);
		testSteps.add("<b>======Removed OverRide at Channel Level and Verify At Class and Channel Level and Best Available Rates========</b>");
		rateGrid.removeOverRide(driver, testSteps, roomClass[0], channelNames.get(0), newRate, oldRate);
		rateGrid.verifyRegularRateAtClassLevel(driver,testSteps, roomClass[0], oldRate);
		rateGrid.verifyRegularRateAtChannelLevel(driver,testSteps, roomClass[0], channelNames.get(0), oldRate);
		
 	}catch (Exception e) {			
		Utility.catchException(driver, e, "Override Rate and Verify Color of OverRide", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
	} catch (Error e) {
		Utility.catchError(driver, e, "Override Rate and Verify Color of OverRide", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		
}
	
	try
	{
		if(isExists) {
		 testSteps.add("<b>======Delete Rate Plan========</b>");
		 rateGrid.clickDeleteIcon(driver, testSteps);
		 rateGrid.verifyDeletedMsg(driver, testSteps, ratesConfig.getProperty("deleteRatePlanMsg"));
		 rateGrid.clickDeleteButton(driver, testSteps);
		 rateGrid.clickRatePlanArrow(driver, testSteps);
		 ratePlanNames=rateGrid.getRatePlanNames(driver);
		 rateGrid.verifyDeletedRatePlan(driver, testSteps, Utility.rateplanName, ratePlanNames);
		}
		 RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		
	}catch (Exception e) {			
		Utility.catchException(driver, e, "Delete Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
	} catch (Error e) {
		Utility.catchError(driver, e, "Delete Rate Plan", "RateGrid", "RateGrid", testName, test_description, test_catagory, testSteps);
		
}
		}
		
		
		
	
		@DataProvider
		public Object[][] getData() {

			return Utility.getData("RateGridOverrideVerification", envLoginExcel);

		}

				
		@AfterClass(alwaysRun = true)
		public void closeDriver() {
			driver.quit();
		}

}
