
package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.CancellationPolicy;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.ParseException;

public class VerifyRateplanInInncenter extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();

	private void verificationDepositWithPolicy(String paymentType, String policyName,String amount, String historyPaymentType) throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, test_steps);
		reservationPage.verify_FolioPayment(driver, test_steps, amount);
		getTest_Steps=reservationPage.clickOnDetails(driver);
		test_steps.addAll(getTest_Steps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyName, "");
		reservationPage.verifyTripSummaryPaidAmount(driver, test_steps, amount);
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyDepositAtHistoryTab(driver, test_steps, amount, historyPaymentType);
	}

	private void verificationDepoistWithoutPolicy(String paymentType, String date,String policyName) throws InterruptedException, ParseException, java.text.ParseException
	{
		reservationPage.verifyNoPaymentAtFolioLineItem(driver, test_steps, date, paymentType);
		getTest_Steps=reservationPage.clickOnDetails(driver);
		test_steps.addAll(getTest_Steps);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyName, "");    
	}

	private void verificationCheckinWithPolicy(String paymentType, String policyName, String amount, String payment, String cardFormat) throws InterruptedException, ParseException, java.text.ParseException {
		reservationPage.verifyCheckInPolicy(driver, test_steps, policyName, "");
		reservationPage.clickFolio(driver, test_steps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, test_steps);
		reservationPage.verify_FolioPayment(driver, test_steps, payment);
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, test_steps);
		reservationPage.verifyHistoryWithCapturedPayment(driver, test_steps, amount, cardFormat,paymentType);
	}

    private void verificationCheckinWithoutPolicy(String policyName) throws ParseException, InterruptedException, java.text.ParseException {
    	reservationPage.verifyCheckInPolicy(driver, test_steps, policyName, "");
    	reservationPage.click_History(driver, test_steps);
    	reservationPage.verifyChekInReservationOnHistoryTab(driver, test_steps);    
    }
	
	private void completeCheckInIfPolicyExist(double balanceAmt, String amountToBePaid) throws Exception{   
        if(balanceAmt>0.00){
            reservationPage.clickOnProceedToCheckInPaymentButton(driver, test_steps);
            reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
            Wait.waitUntilPageLoadNotCompleted(driver, 10);
            reservationPage.verifyAmountOnPaymentScreen(driver, amountToBePaid, test_steps);
            reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
            Wait.waitUntilPageLoadNotCompleted(driver, 10);
            reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
            Wait.waitUntilPageLoadNotCompleted(driver, 10);
        }else{
        	reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
        	reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
            Wait.waitUntilPageLoadNotCompleted(driver, 30);
        }
    }   
    private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
        if(balanceAmt>0.00 || balanceAmt==0.00) {
        	reservationPage.clickOnConfirmCheckInButton(driver, test_steps);
        	reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
            Wait.waitUntilPageLoadNotCompleted(driver, 30);
        }
    }   
	
	private void getData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}

	@Test(dataProvider = "getData")
	public void verifyRateplanInInncenter(String delim,String ReservationType, String CheckInDate, String CheckOutDate,String adult, String children, String ReservationRoomClasses, String ActionOnReservation,String ReservationChangeRoomClass,String ChangeResStartDate,String ChangeResEndDate,String RatePlanName,String isAccountPolicyApplicable,String isAccountPolicyCreate, 
			String FeesGuestMustPay,String PercentOfFee, String FeeChargesType,String RulesUpdateType,String RulesUpdateStartDate, String RulesUpdateEndDate, String isSun_RulesUpdate, String isMon_RulesUpdate,
			String isTue_RulesUpdate, String isWed_RulesUpdate, String isThu_RulesUpdate, String isFri_RulesUpdate,
			String isSat_RulesUpdate, String Type_RulesUpdate, String RuleMinStayValue_RulesUpdate,
			String RatesUpdateType, String checkInDate_RatesUpdate, String checkOutDate_RatesUpdate,
			String isSunday_RatesUpdate, String isMonday_RatesUpdate, String isTuesday_RatesUpdate,
			String isWednesday_RatesUpdate, String isThursday_RatesUpdate, String isFriday_RatesUpdate,
			String isSaturday_RatesUpdate, String updateRatesType, String isUpdateRateByRoomClass,
			String nightlyRate_RatesUpdate, String additionalAdults_RatesUpdate, String additionalChild_RatesUpdate)
			throws Exception {
		/**/
		Utility.DELIM = delim;

		test_name = "VerifyRateplanInInncenter_"+ReservationType+"_"+CheckInDate+"_"+CheckOutDate;
		test_description = "VerifyRateplanInInncenter";
		test_catagory = "VerifyRateplanInInncenter";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String channelName = "innCenter";
		
		HashMap<String,String>ratePlanData=Utility.getExcelData(System.getProperty("user.dir")+"\\test-data\\CentralparkSanityTestData.xlsx","CreateNightlyRatePlanV2");
		String FolioDisplayName=ratePlanData.get("FolioDisplayName");
		String Description=ratePlanData.get("Description");
		String Channels=ratePlanData.get("Channels");
		String RoomClasses=ratePlanData.get("RoomClasses");
		String isRatePlanRistrictionReq=ratePlanData.get("isRatePlanRistrictionReq");
		String RistrictionType=ratePlanData.get("RistrictionType");
		String isMinStay=ratePlanData.get("isMinStay");
		String MinNights=ratePlanData.get("MinNights"); 
		String isMaxStay=ratePlanData.get("isMaxStay"); 
		String MaxNights=ratePlanData.get("MaxNights"); 
		String isMoreThanDaysReq=ratePlanData.get("isMoreThanDaysReq"); 
		String MoreThanDaysCount=ratePlanData.get("MoreThanDaysCount"); 
		String isWithInDaysReq=ratePlanData.get("isWithInDaysReq"); 
		String WithInDaysCount=ratePlanData.get("WithInDaysCount"); 
		String PromoCode=ratePlanData.get("PromoCode"); 
		String isPolicesReq=ratePlanData.get("isPolicesReq"); 
		String PoliciesType=ratePlanData.get("PoliciesType"); 
		String PoliciesName=ratePlanData.get("PoliciesName");
		String SeasonName=ratePlanData.get("SeasonName");
		String SeasonStartDate=ratePlanData.get("SeasonStartDate"); 
		String SeasonEndDate=ratePlanData.get("SeasonEndDate");
		String isMonDay=ratePlanData.get("isMonDay");
		String isTueDay=ratePlanData.get("isTueDay");
		String isWednesDay=ratePlanData.get("isWednesDay");
		String isThursDay=ratePlanData.get("isThursDay");
		String isFriday=ratePlanData.get("isFriday");
		String isSaturDay=ratePlanData.get("isSaturDay");
		String isSunDay=ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults=ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight=ratePlanData.get("RatePerNight");
		String MaxAdults=ratePlanData.get("MaxAdults");
		String MaxPerson=ratePlanData.get("MaxPerson");
		String AdditionalAdultsPerNight=ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight=ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason=ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason=ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight=ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults=ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons=ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight=ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight=ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules=ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass=ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses=ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType=ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue=ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday=ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday=ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday=ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday=ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday=ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday=ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday=ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies=ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType=ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues=ratePlanData.get("SeasonPolicyValues");
		String MaxPersons=ratePlanData.get("MaxPersons");
		
		String reervationNoShowPolicy="No Policy";
		String reervationDepositPolicy="No Policy";
		String reervationCheckInPolicy="No Policy";

		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> Rooms = new ArrayList<String>();
		boolean israteplanExist = false;
		boolean isSeasonExist = true;
		boolean isRoomClassAvailable = true;
		boolean isRoomClassAvailableChange = true;
		String PolicyTypes = "";
		String PolicyNames = "";
		String TypeOfFees = "";
		String GuestsWillIncurAFee = "";
		String ChargesType = "";
		String NoOfDays = "";
		String CancelWithInType = "";
		boolean isPromocode = false;
		boolean isMinStayRule = false;
		boolean isMinStayRuleBrokenPopComeOrNot = false;
		ArrayList<String> minStayRule = null;
		ArrayList<String> minrule = null;
		ArrayList<String> noCheckInRule = null;
		ArrayList<String> noCheckOutRule = null;
		String checkInColor = null;
		String checkOutColor = null;
		boolean verifyLenthOfStayChecked = false;
		boolean verifyMinStayCondidtion = false;
		boolean verifyMaxStayCondition = false;
		HashMap<String, String> addStayofLength = new HashMap<>();
		boolean restricrionsBookingWindow = false;
		boolean restricrionsLengthOfStay = false;
		int minStayRuleValue = 0;
		boolean isBookinWindow = false;
		String folioName = "";
		String[] RoomClass = RoomClasses.split("\\|");
		HashMap<String, String> capacityAdult = new HashMap<String, String>(),
				capacityChild = new HashMap<String, String>(), maxAdult = new HashMap<String, String>(),
				maxChild = new HashMap<String, String>();
		HashMap<String, String> getRates = new HashMap<String, String>();
		HashMap<String, String> getExAdult = new HashMap<String, String>();
		HashMap<String, String> getExChild = new HashMap<String, String>();
		HashMap<String, String> getRatesPerNightChannels = new HashMap<String, String>();
		List<Date> dates = new ArrayList<Date>();
		List dates1 = new ArrayList();
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();
		boolean isVerifyPolicies = true;
		ArrayList<String> minStayRuleMRB = null;
		ArrayList<String> minruleMRB = null;
		ArrayList<Integer> minStayRuleValueMRB = new ArrayList<Integer>();
		ArrayList<Boolean> isMinStayRuleMRB = new ArrayList<Boolean>();
		ArrayList<Boolean> isMinStayRuleBrokenPopComeOrNotMRB = new ArrayList<Boolean>();
		ArrayList noCheckInRuleMRB = new ArrayList<String>();
		ArrayList noCheckOutRuleMRB = new ArrayList<String>();
		ArrayList<String> checkInColorMRB = new ArrayList<String>();
		ArrayList<String> checkOutColorMRB = new ArrayList<String>();
		ArrayList<Boolean> restricrionsLengthOfStayMRB = new ArrayList<Boolean>();
		ArrayList<Boolean> restricrionsBookingWindowMRB = new ArrayList<Boolean>();
		ArrayList<String> reservationRoomClassesList = new ArrayList<String>();
		ArrayList<String> capacityAdult1 = new ArrayList<String>();
		ArrayList<String> capacityChild1 = new ArrayList<String>();
		ArrayList<String> ratePlanAdults = new ArrayList<String>();
		ArrayList<String> ratePlanChilds = new ArrayList<String>();
		ArrayList<HashMap<String, String>> ratesList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exAdultList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> exChildList = new ArrayList<HashMap<String, String>>();
		ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
		ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();
		CancellationPolicy foundBestPolicyAmount = null;

		boolean isSeasonExistChange = true;					
		boolean isPromocodeChnage = false;
		boolean isMinStayRuleChnage = false;
		boolean isMinStayRuleBrokenPopComeOrNotChange = false;
		ArrayList<String> minStayRuleChnage = null;
		ArrayList<String> minruleChange = null;
		ArrayList<String> noCheckInRuleChnage = null;
		ArrayList<String> noCheckOutRuleChnage	 = null;
		String checkInColorChnage = null;
		int minStayRuleValueChange = 0;
		String checkOutColorChange = null;
		boolean verifyLenthOfStayCheckedChange = false;
		boolean verifyMinStayCondidtionChange = false;
		boolean verifyMaxStayConditionChange = false;
		HashMap<String, String> addStayofLengthChange = new HashMap<>();
		boolean restricrionsBookingWindowChange = false;
		boolean restricrionsLengthOfStayChange = false;
		boolean isBookinWindowChange = false;
		String PromoCodeChange="";
		HashMap<String, String> getRateLevelPolicyChange = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicyChange = new HashMap<String, String>();
		boolean isVerifyPoliciesChange = true;
		HashMap<String, String> capacityAdultChange = new HashMap<String, String>(),
				capacityChildChange = new HashMap<String, String>(), maxAdultChange = new HashMap<String, String>(),
				maxChildChange = new HashMap<String, String>();
		HashMap<String, String> getRatesChange = new HashMap<String, String>();
		HashMap<String, String> getExAdultChange = new HashMap<String, String>();
		HashMap<String, String> getExChildChange = new HashMap<String, String>();
		HashMap<String, String> getRatesPerNightChannelsChange = new HashMap<String, String>();
		List<Date> datesChnage = new ArrayList<Date>();
		
		ArrayList<String> seasonDepositPolicyChange = new ArrayList<String>();
		ArrayList<String> seasonCheckInPolicyChange = new ArrayList<String>();
		HashMap<String, ArrayList<String>> descWhileCreatePolicy = new HashMap<>();
		
	    ArrayList<String> policyNames= new ArrayList<String>();
	    ArrayList<String> policyTypes= new ArrayList<String>();
	    HashMap<String, String> selectedpolicyNames= new HashMap<String, String>();
	    ArrayList<HashMap<String, String>> getAccountPoliciesData= new ArrayList<HashMap<String, String>>();
	    String[] policyTypeIs;
        String[] policyNameIs;
        String[] policyAttr1Is = null;
        String[] policyAttrValueIs = null;
        String[] policyAttr2Is; 
		
		String policyName="";
		String policyType="";
		int days = 0;
		if (!ReservationType.equalsIgnoreCase("MRB")) {
			days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
		}

		int daysChnage=0;
		if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
			daysChnage=Utility.getNumberofDays(ChangeResStartDate, ChangeResEndDate);
		}
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			Login login = new Login();
			try {
				login.login(driver, envURL, "autorate", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				login.login(driver, envURL, "autorate", "autouser", "Auto@123");
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


		if (ReservationType.equalsIgnoreCase("MRB") || ReservationType.equalsIgnoreCase("tapechart") || ActionOnReservation.equalsIgnoreCase("Cancellation")) {

			test_steps.add("=================== Getting the Room Class Abbrivation ======================");
			app_logs.info("=================== Getting the Room Class Abbrivation ======================");
			try {
				navigation.Setup(driver);
				navigation.RoomClass(driver);
				RoomAbbri = rc.getAbbrivation(driver, delim, ReservationRoomClasses, test_steps);
				Utility.app_logs.info(RoomAbbri);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		try {
			if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
				navigation.Inventory(driver,test_steps);
				navigation.policies(driver, test_steps);
				
				policyName="policyName"+Utility.generateRandomString();
				if(ActionOnReservation.equalsIgnoreCase("no Show")) {
					policyType="No Show";
					test_steps.add("========== Creating new policy for account ==========");
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					descWhileCreatePolicy = policy.createPolicies(driver, test_steps, "", "", policyType, "", "", "", policyName, 
							FeesGuestMustPay, PercentOfFee, FeeChargesType, "", "", "No", "");	
					reservationPage.navigateToReservationPage(driver);
				}else if(ActionOnReservation.equalsIgnoreCase("Check in")) {
					policyType="Check In";
					Wait.waitUntilPageLoadNotCompleted(driver, 5);
					policyTypeIs=policyType.split("\\|");
		            policyNameIs=policyName.split("\\|");
		            policyAttr1Is=FeesGuestMustPay.split("\\|");
		            policyAttrValueIs=PercentOfFee.split("\\|");
		            policyAttr2Is=FeeChargesType.split("\\|");        
		            if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
		            	test_steps.add("========== Creating new policy for account ==========");
		                navigation.policies(driver, test_steps);
		                Wait.waitUntilPageLoadNotCompleted(driver, 5);                    
		                for(int i=0;i<policyTypeIs.length;i++){
		                  //  String policyNameis=policyNameIs[i]+Utility.generateRandomNumber();
		                	
		                    app_logs.info(policyName);
		                    policyNames.add(policyName);
		                    policyTypes.add(policyTypeIs[i]);
		                    policy.createPolicies(driver, test_steps, "", "", policyTypeIs[i], "", policyName,policyName , "", 
		                            policyAttr1Is[i], policyAttrValueIs[i], policyAttr2Is[i], "", "", "No", "");    
		                }
		            }
		            else {
		                for(int i=0;i<policyTypeIs.length;i++){
		                    policyTypes.add(policyTypeIs[i]);
		                }
		                app_logs.info(policyNames);
		                app_logs.info(policyTypes);
		            }
					reservationPage.navigateToReservationPage(driver);
			}else if(ActionOnReservation.equalsIgnoreCase("Cancellation")||ActionOnReservation.equalsIgnoreCase("Cancel")) {
					policyType="Cancellation";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			// After login
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			test_steps.add("Navigated to rateGrid");
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

		test_steps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		try {
			Utility.app_logs.info("RatePlanName : " + RatePlanName);
			israteplanExist = rateGrid.isRatePlanExist(driver, RatePlanName, test_steps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
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

		if (!israteplanExist) {
			test_steps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			try {

				test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", test_steps);

				test_steps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				RatePlanName = RatePlanName + Utility.generateRandomString();
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, RatePlanName, test_steps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);

				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", RatePlanName, test_steps);

				test_steps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, test_steps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, test_steps);

				test_steps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, RoomClasses, true, test_steps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, test_steps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, test_steps);

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, test_steps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						test_steps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), test_steps);
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
				test_steps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, test_steps);

				nightlyRate.createSeason(driver, test_steps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);

				nightlyRate.clickCompleteChanges(driver, test_steps);
				nightlyRate.clickSaveAsActive(driver, test_steps);
				Wait.wait30Second();
				test_steps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
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
		}

		try {
			test_steps.add("=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			app_logs.info("=================== Getting the Availability of room classes in between check in and checkout dates ======================");
			rateGrid.clickOnAvailability(driver);
			ArrayList MRBAvail = new ArrayList();
			ArrayList MRBBlockOut = new ArrayList();
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				rateGrid.clickForRateGridCalender(driver, test_steps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				rateGrid.clickExpendRooClass(driver, test_steps, ReservationRoomClasses);
				app_logs.info("==========Getting the availability count of the room class " + ReservationRoomClasses+ " in Availability tab==========");
				test_steps.add("==========Getting the availability count of the room class " + ReservationRoomClasses+ " in Availability tab==========");
				ArrayList<String> avail = rateGrid.getAvailability(driver, test_steps, ReservationRoomClasses, days,CheckInDate);

				app_logs.info("==========Getting the room class " + ReservationRoomClasses+ " as blocked out or not in Availability tab==========");
				test_steps.add("==========Getting the room class " + ReservationRoomClasses+ " as blocked out or not in Availability tab==========");
				ArrayList<Boolean> blockout = rateGrid.getBlockOutRoomClass(driver, test_steps, ReservationRoomClasses,days, channelName, CheckInDate);

				for (int i = 0; i < avail.size(); i++) {
					int available = Integer.parseInt(avail.get(i));
					boolean falg = blockout.get(i);
					if (available <= 0 || !falg) {
						isRoomClassAvailable = false;
						break;
					}
				}
				
				if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
					
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
					rateGrid.clickExpendRooClass(driver, test_steps, ReservationChangeRoomClass);
					app_logs.info("==========Getting the availability count of the room class " + ReservationChangeRoomClass+ " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class " + ReservationChangeRoomClass+ " in Availability tab==========");
					ArrayList<String> availChnage = rateGrid.getAvailability(driver, test_steps, ReservationChangeRoomClass, daysChnage,ChangeResStartDate);

					app_logs.info("==========Getting the room class " + ReservationChangeRoomClass+ " as blocked out or not in Availability tab==========");
					test_steps.add("==========Getting the room class " + ReservationChangeRoomClass+ " as blocked out or not in Availability tab==========");
					ArrayList<Boolean> blockoutChange = rateGrid.getBlockOutRoomClass(driver, test_steps, ReservationChangeRoomClass,daysChnage, channelName, ChangeResStartDate);

					for (int i = 0; i < availChnage.size(); i++) {
						int available = Integer.parseInt(availChnage.get(i));
						boolean falg = blockoutChange.get(i);
						if (available <= 0 || !falg) {
							isRoomClassAvailableChange = false;
							break;
						}
					}
				}
				
				
			} else {
				String[] rm = ReservationRoomClasses.split("\\|");
				String[] chkin = CheckInDate.split("\\|");
				String[] chkout = CheckOutDate.split("\\|");
				int MRBdays = 0;
				for (int i = 0; i < rm.length; i++) {
					MRBdays = Utility.getNumberofDays(chkin[i], chkout[i]);
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, chkin[i], "dd/MM/yyyy");
					rateGrid.clickExpendRooClass(driver, test_steps, rm[i]);

					app_logs.info("==========Getting the availability count of the room class " + rm[i]
							+ " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class " + rm[i]
							+ " in Availability tab==========");
					MRBAvail.add(rateGrid.getAvailability(driver, test_steps, rm[i], MRBdays, chkin[i]));

					app_logs.info("==========Getting the  the room class " + rm[i]+ "  is blocked are not in Availability tab==========");
					test_steps.add("==========Getting the  the room class " + rm[i]	+ "  is blocked are not in Availability tab==========");
					MRBBlockOut.add(rateGrid.getBlockOutRoomClass(driver, test_steps, rm[i], MRBdays, channelName, chkin[i]));
				}

				if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {

					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
					rateGrid.clickExpendRooClass(driver, test_steps, ReservationChangeRoomClass);
					app_logs.info("==========Getting the availability count of the room class " + ReservationChangeRoomClass+ " in Availability tab==========");
					test_steps.add("==========Getting the availability count of the room class " + ReservationChangeRoomClass+ " in Availability tab==========");
					ArrayList<String> availChnage = rateGrid.getAvailability(driver, test_steps, ReservationChangeRoomClass, daysChnage,ChangeResStartDate);

					app_logs.info("==========Getting the room class " + ReservationChangeRoomClass+ " as blocked out or not in Availability tab==========");
					test_steps.add("==========Getting the room class " + ReservationChangeRoomClass+ " as blocked out or not in Availability tab==========");
					ArrayList<Boolean> blockoutChange = rateGrid.getBlockOutRoomClass(driver, test_steps, ReservationChangeRoomClass,daysChnage, channelName, ChangeResStartDate);

					for (int i = 0; i < availChnage.size(); i++) {
						int available = Integer.parseInt(availChnage.get(i));
						boolean falg = blockoutChange.get(i);
						if (available <= 0 || !falg) {
							isRoomClassAvailableChange = false;
							break;
						}
					}
				}
				
				outer: for (int i = 0; i < MRBAvail.size(); i++) {
					ArrayList al = (ArrayList) MRBAvail.get(i);
					for (int k = 0; k < al.size(); k++) {
						int available = Integer.parseInt(al.get(k).toString());
						if (available <= 0) {
							isRoomClassAvailable = false;
							break outer;
						}
					}

					for (int k = 0; k < MRBBlockOut.size(); k++) {
						ArrayList al1 = (ArrayList) MRBBlockOut.get(i);
						for (int m = 0; m < al1.size(); m++) {
							boolean falg = (boolean) al1.get(m);
							if (!falg) {
								isRoomClassAvailable = false;
								break outer;
							}
						}
					}
				}
			}
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
			rateGrid.clickRatesTab(driver, test_steps);
			reservationRoomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
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
			test_steps.add("=================== Selecting the Rate Plan ======================");
			app_logs.info("=================== Selecting the Rate Plan ======================");
			rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
			if (!ReservationType.equalsIgnoreCase("MRB")) {
				rateGrid.clickForRateGridCalender(driver, test_steps);
				Utility.selectDateFromDatePicker(driver, CheckInDate, "dd/MM/yyyy");
				rateGrid.expandRoomClass(driver, test_steps, reservationRoomClassesList.get(0));
				rateGrid.expandChannel(driver, test_steps, reservationRoomClassesList.get(0), channelName);
			}
			if (RulesUpdateType.equalsIgnoreCase("BulkUpdate")) {
				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickOnBulkUpdate(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectBulkUpdateOption(driver, "Rules");
				test_steps.addAll(getTest_Steps);

				test_steps.add("==========SELECT START DATE==========");
				app_logs.info("==========SELECT START DATE==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectStartDate(driver,
						Utility.parseDate(RulesUpdateStartDate, "dd/MM/yyyy", "MM/dd/yyyy"));
				test_steps.addAll(getTest_Steps);

				test_steps.add("==========SELECT END DATE==========");
				app_logs.info("==========SELECT END DATE==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectEndDate(driver,
						Utility.parseDate(RulesUpdateEndDate, "dd/MM/yyyy", "MM/dd/yyyy"));
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
				test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Sun", isSun_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Mon", isMon_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Tue", isTue_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Wed", isWed_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Thu", isThu_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Fri", isFri_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.checkDays(driver, "Sat", isSat_RulesUpdate);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========SELECTING RATE PLAN==========");
				test_steps.add("==========SELECTING RATE PLAN==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========SELECTING ROOM CLASS==========");
				test_steps.add("==========SELECTING ROOM CLASS==========");

			//	ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
				//for (String roomClassName : roomClassesList) {
					getTest_Steps.clear();
					getTest_Steps = rateGrid.selectRoomClass(driver, ReservationRoomClasses,delim);
					test_steps.addAll(getTest_Steps);
				//}

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Source", channelName);
				test_steps.addAll(getTest_Steps);

				ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);

				for (String type : type_rulesUpdateList) {

					if (type.equalsIgnoreCase("min stay")) {
						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickMinimumStay(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.enterMinimumStayValue(driver, RuleMinStayValue_RulesUpdate);
						test_steps.addAll(getTest_Steps);

					} else if (type.equalsIgnoreCase("No Check In")) {

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickCheckin(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickNoCheckInCheckbox(driver, "Yes");
						test_steps.addAll(getTest_Steps);

					} else if (type.equalsIgnoreCase("No Check out")) {
						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickCheckOut(driver, "Yes");
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.clickNoCheckOutCheckbox(driver, "Yes");
						test_steps.addAll(getTest_Steps);

					}

				}
				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickUpdateButton(driver);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				test_steps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

				int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate) + 1;
				String expectedDays = "Rules will be updated for " + numberOfDays + " day(s) within this date range.";
				test_steps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);
				String totalDays = rateGrid.getTotalDaysText(driver, "Rules");
				test_steps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
				test_steps.add("Verified total number of days");
				app_logs.info("Verified total number of days");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickOnYesUpdateButton(driver);
				test_steps.addAll(getTest_Steps);
			} else if (RulesUpdateType.equalsIgnoreCase("Override") || RulesUpdateType.equalsIgnoreCase("Overide")) {
				ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
				ArrayList<String> type_rulesUpdateList = Utility.convertTokenToArrayList(Type_RulesUpdate, delim);
				ArrayList<String> getDates = Utility.checkDayAndReturnDates(RulesUpdateStartDate, RulesUpdateEndDate,
						"dd/MM/yyyy", isMon_RulesUpdate, isTue_RulesUpdate, isWed_RulesUpdate, isThu_RulesUpdate,
						isFri_RulesUpdate, isSat_RulesUpdate, isSun_RulesUpdate);
				int numberOfDays = Utility.getNumberofDays(RulesUpdateStartDate, RulesUpdateEndDate);
				String newDateDate = RulesUpdateStartDate;

				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < numberOfDays; i++) {
						newDateDate = Utility.getCustomDate(RulesUpdateStartDate, "dd/MM/yyyy", "dd/MM/yyyy", i);
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, newDateDate, "dd/MM/yyyy");
						rateGrid.overrideMinStayValue(driver, test_steps, roomClassName, channelName, "0");
						rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
								ratesConfig.getProperty("checkinRule"), false);
						rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
								ratesConfig.getProperty("checkoutRule"), false);

					}
				}
				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < getDates.size(); i++) {
						newDateDate = Utility.getCustomDate(RulesUpdateStartDate, "dd/MM/yyyy", "dd/MM/yyyy", i);
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, getDates.get(i), "dd/MM/yyyy");

						for (String type : type_rulesUpdateList) {

							if (type.equalsIgnoreCase("min stay")) {
								rateGrid.overrideMinStayValue(driver, test_steps, roomClassName, channelName,
										RuleMinStayValue_RulesUpdate);
							} else if (type.equalsIgnoreCase("No Check In")) {
								rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										ratesConfig.getProperty("checkinRule"), true);

							} else if (type.equalsIgnoreCase("No Check out")) {
								rateGrid.overrideRuleForNoCheckInAndOut(driver, test_steps, roomClassName, channelName,
										ratesConfig.getProperty("checkoutRule"), true);
							}
						}
					}
				}

			}

			if (RatesUpdateType.equalsIgnoreCase("BulkUpdate")) {
				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickOnBulkUpdate(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectBulkUpdateOption(driver, "Rates");
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.startDate(driver,
						Utility.parseDate(checkInDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.endDate(driver,
						Utility.parseDate(checkOutDate_RatesUpdate, "dd/MM/yyyy", "MM/dd/yyyy"));
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
				test_steps.add("==========CHECKING/UNCHECKING DAYS==========");

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Sun", isSunday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Mon", isMonday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Tue", isTuesday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Wed", isWednesday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Thu", isThursday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Fri", isFriday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.bulkUpdatePoppupDayCheck(driver, "Sat", isSaturday_RatesUpdate);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Rate Plan", RatePlanName);
				test_steps.addAll(getTest_Steps);

				//ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
				//for (String roomClassName : roomClassesList) {
					getTest_Steps.clear();
					getTest_Steps = rateGrid.selectRoomClass(driver, ReservationRoomClasses,delim);
					test_steps.addAll(getTest_Steps);
				//}

				getTest_Steps.clear();
				getTest_Steps = rateGrid.selectItemsFromDropDowns(driver, "Source", channelName);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========UPDATE RATES==========");
				test_steps.add("==========UPDATE RATES==========");

				// Checks Rate Update Type
				if (updateRatesType.equalsIgnoreCase("EnterNewRate")) {
					getTest_Steps.clear();
					getTest_Steps = rateGrid.selectBulkUpdateRatesOption(driver, 0);
					test_steps.addAll(getTest_Steps);

					getTest_Steps.clear();
					getTest_Steps = rateGrid.updateRoomsByRoomClassToggle(driver,
							Boolean.parseBoolean(isUpdateRateByRoomClass));
					test_steps.addAll(getTest_Steps);

					//
					// String[] roomClassArray = roomClassName.split("\\|");
					//ArrayList<String> roomClassList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
					//for (String str : roomClassList) {
						getTest_Steps.clear();
						getTest_Steps = rateGrid.selectRoomClass(driver, ReservationRoomClasses,delim);
						test_steps.addAll(getTest_Steps);
						test_steps.addAll(getTest_Steps);
					//}

					// String[] nightlyRateArray = nightlyRate_RatesUpdate.split("\\|");
					ArrayList<String> nightlyRateArray = Utility.convertTokenToArrayList(nightlyRate_RatesUpdate,
							delim);
					int nightArrayLength = 1;
					if (isUpdateRateByRoomClass.equalsIgnoreCase("True")) {
						nightArrayLength = nightlyRateArray.size();
					}
					ArrayList<String> additionalAdultArray = Utility
							.convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
					ArrayList<String> additionalChildArray = Utility
							.convertTokenToArrayList(additionalAdults_RatesUpdate, delim);
					// Check Length of NightlyRate List and Input Values
					for (int i = 0; i < nightArrayLength; i++) {

						getTest_Steps.clear();
						getTest_Steps = rateGrid.updateNightlyRate(driver, i, nightlyRateArray.get(i));
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.updateAdditionalAdultRate(driver, i, additionalAdultArray.get(i));
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = rateGrid.updateAdditionalChildRate(driver, i, additionalChildArray.get(i));
						test_steps.addAll(getTest_Steps);

					}
				}

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickBulkUpdatePopupUpdateButton(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = rateGrid.clickYesUpdateButton(driver);
				test_steps.addAll(getTest_Steps);
			} else if (RatesUpdateType.equalsIgnoreCase("Overide") || RatesUpdateType.equalsIgnoreCase("Override")) {
				ArrayList<String> roomClassesList = Utility.convertTokenToArrayList(ReservationRoomClasses, delim);
				ArrayList<String> getDates = Utility.checkDayAndReturnDates(checkInDate_RatesUpdate,
						checkOutDate_RatesUpdate, "dd/MM/yyyy", isMonday_RatesUpdate, isTuesday_RatesUpdate,
						isWednesday_RatesUpdate, isThursday_RatesUpdate, isFriday_RatesUpdate, isSaturday_RatesUpdate,
						isSunday_RatesUpdate);

				for (String roomClassName : roomClassesList) {
					for (int i = 0; i < getDates.size(); i++) {
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, getDates.get(i), "dd/MM/yyyy");
						rateGrid.overrideNightExtraAdultChildRate(driver, test_steps, roomClassName, channelName,
								nightlyRate_RatesUpdate, additionalAdults_RatesUpdate, additionalChild_RatesUpdate);
					}
				}
			}
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

		HashMap<String, String> names = null;
		HashMap<String, String> policyClauses = null;
		HashMap<String, String> guestsWillIncurAFee = null;
		HashMap<String, String> chargesType = new HashMap<String, String>();
		HashMap<String, String> cancelWithInType = null;
		HashMap<String, String> noOfDays = null;
		int MRBDays = 0;
		try {
			test_steps.add("=================== Getting Rate Plan Restrictions and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restrictions and Rules ======================");

			if (ReservationType.equalsIgnoreCase("MRB")) {
				String[] MRBCheckIn = CheckInDate.split("\\|");
				String[] MRBCheckOut = CheckOutDate.split("\\|");
				String[] roomClass = ReservationRoomClasses.split("\\|");

				for (int k = 0; k < roomClass.length; k++) {
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, MRBCheckIn[k], "dd/MM/yyyy");
					rateGrid.expandRoomClass(driver, test_steps, roomClass[k]);
					rateGrid.expandChannel(driver, test_steps, roomClass[k], channelName);

					MRBDays = Utility.getNumberofDays(MRBCheckIn[k], MRBCheckOut[k]);
					Utility.app_logs.info("MRBCheckIn[k] : " + MRBCheckIn[k]);
					Utility.app_logs.info("MRBCheckOut[k] : " + MRBCheckOut[k]);
					Utility.app_logs.info("MRBDays : " + MRBDays);

					minStayRuleMRB = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, roomClass[k], channelName,MRBDays);
					minruleMRB = minStayRuleMRB;
					Collections.sort(minruleMRB);
					Utility.app_logs.info("minrule : " + minruleMRB);
					int min = Integer.parseInt((String) minruleMRB.get(minruleMRB.size() - 1));
					Utility.app_logs.info(min);
					minStayRuleValueMRB.add(min);
					if (minStayRuleValueMRB.get(k) > 0) {
						isMinStayRuleMRB.add(true);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					} else {
						isMinStayRuleMRB.add(false);
						isMinStayRuleBrokenPopComeOrNotMRB.add(reservationPage.verifyMinStayPopupComeOrNot(driver,
								test_steps, minStayRuleValueMRB.get(k), MRBDays));
					}

					ArrayList<String> noCheckInRule1 = null;
					noCheckInRule1 = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, roomClass[k],
							channelName, MRBDays);

					noCheckInRuleMRB.add(noCheckInRule1);

					Utility.app_logs.info("noCheckInRule1 : " + noCheckInRule1);

					checkInColorMRB.add(reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule1,
							MRBCheckIn[k], MRBCheckOut[k]));

					Utility.app_logs.info("checkInColor : " + checkInColorMRB.get(k));

					ArrayList<String> noCheckOutRule1 = null;

					noCheckOutRule1 = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, roomClass[k],
							channelName, MRBDays);
					noCheckOutRuleMRB.add(noCheckOutRule1);

					Utility.app_logs.info("noCheckOutRule : " + noCheckOutRule1);

					checkOutColorMRB.add(reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
							noCheckOutRule1, MRBCheckIn[k], MRBCheckOut[k]));
					Utility.app_logs.info("checkOutColor : " + checkOutColorMRB.get(k));
					rateGrid.collapseRoomClass(driver, test_steps, roomClass[k]);

					app_logs.info("Min Stay Rule for room classes : " + minStayRuleValueMRB);
				}

				rateGrid.clickOnEditRatePlan(driver);
				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);
				Utility.app_logs.info("folioName : " + folioName);
				rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				verifyLenthOfStayChecked = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
				app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);
				if (verifyLenthOfStayChecked) {

					verifyMinStayCondidtion = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
					verifyMaxStayCondition = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
					if (verifyMinStayCondidtion) {
						String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
						addStayofLength.put("Min", getMin);
					}
					if (verifyMaxStayCondition) {
						String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
						addStayofLength.put("Max", getMax);
					}

				}

				isBookinWindow = rateGrid.isBookingWindowChecked(driver, test_steps);

				HashMap<String, String> bookingWindowRestrictions = new HashMap<>();

				test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ RatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);

				Utility.app_logs.info(bookingWindowRestrictions);

				for (int i = 1; i < roomClass.length; i++) {
					MRBDays = Utility.getNumberofDays(MRBCheckIn[i], MRBCheckOut[i]);
					restricrionsLengthOfStayMRB.add(
							reservationPage.verifylenthOfStayRestrictions(driver, test_steps, verifyLenthOfStayChecked,
									verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength, MRBDays));

					Utility.app_logs.info(addStayofLength);
					Utility.app_logs.info(restricrionsLengthOfStay);

					restricrionsBookingWindowMRB.add(reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
							isBookinWindow, MRBCheckIn[i], MRBCheckOut[i], bookingWindowRestrictions));

					Utility.app_logs.info("isBookinWindow : " + isBookinWindow);
					Utility.app_logs.info("restricrionsBookingWindow : " + restricrionsBookingWindow);
				}

				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);

				if (isPromocode) {
					PromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					PromoCode = "";
				}

				Utility.app_logs.info("promoCode : " + PromoCode);

				Utility.app_logs.info("isMinStayRuleBrokenPopComeOrNotMRB : " + isMinStayRuleBrokenPopComeOrNotMRB);
				app_logs.info("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				app_logs.info("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				app_logs.info("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);
				test_steps.add("Min Stay Rule values for room classes : " + minStayRuleValueMRB);
				test_steps.add("Check In ColorMRB Rule values for room classes : " + checkInColorMRB);
				test_steps.add("Check out ColorMRB Rule values for room classes : " + checkOutColorMRB);

				Utility.app_logs.info("minStayRuleValueMRB : " + minStayRuleValueMRB);
				Utility.app_logs.info("checkInColorMRB : " + checkInColorMRB);
				Utility.app_logs.info("checkOutColorMRB : " + checkOutColorMRB);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}

				String[] chkIn = CheckInDate.split("\\|");
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, chkIn[0]);
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);

				for (String str : roomClass) {
					capacityAdult1.add(nightlyRate.getAdultCapacity(driver, str));
					capacityChild1.add(nightlyRate.getChildCapacity(driver, str));
					ratePlanAdults.add(nightlyRate.getMaxAdult(driver, str));
					ratePlanChilds.add(nightlyRate.getMaxPersons(driver, str));
				}

				nightlyRate.closeSeason(driver, test_steps);
				rateGrid.clickOnSaveratePlan(driver);
				rateGrid.closeOpendTabInMainMenu(driver);

				if (isVerifyPolicies) {
					driver.navigate().refresh();
					rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
					rateGrid.clickOnEditRatePlan(driver);
					nightlyRate.switchCalendarTab(driver, test_steps);
					nightlyRate.selectSeasonDates(driver, test_steps, MRBCheckIn[0]);
					nightlyRate.clickEditThisSeasonButton(driver, test_steps);
					nightlyRate.clickSeasonPolicies(driver, test_steps);
					getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);

					test_steps.add("==================Get Policy from  Season==================");
					ArrayList<String> deposit= new ArrayList<String>();
					ArrayList<String> checkin= new ArrayList<String>();
					if (CheckInDate.split("\\|").length>1) {
							for(int i=0;i<roomClass.length;i++) {
							deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),roomClass[i],test_steps));
							checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),roomClass[i],test_steps));											
						}
					}else {
						deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),ReservationRoomClasses,test_steps));
						checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),ReservationRoomClasses,test_steps));					
					}
					
					for (String str : deposit) {
						if (str != null) {
							seasonDepositPolicy.add(str);
						}
					}
					for (String str : checkin) {
						if (str != null) {
							seasonCheckInPolicy.add(str);
						}
					}
					app_logs.info(seasonDepositPolicy);
					app_logs.info(seasonCheckInPolicy);
					
					nightlyRate.closeSeason(driver, test_steps);
					rateGrid.clickOnSaveratePlan(driver);
					Wait.wait5Second();
					rateGrid.closeOpendTabInMainMenu(driver);
				}

				String[] rm = ReservationRoomClasses.split("\\|");

				rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
				for (int k = 0; k < rm.length; k++) {

					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, MRBCheckIn[k], "dd/MM/yyyy");

					HashMap<String, String> getRates1 = new HashMap<String, String>();
					HashMap<String, String> getExAdult1 = new HashMap<String, String>();
					HashMap<String, String> getExChild1 = new HashMap<String, String>();

					List<Date> datesD = new ArrayList<Date>();

					rateGrid.expandRoomClass(driver, test_steps, rm[k]);
					test_steps.add("===Get Data From Rate Plan===");
					app_logs.info("===Get Data From Rate Plan===");
					getRatesPerNightChannels = rateGrid.getRoomRatesExAdExChOfChannel(driver, MRBCheckIn[k],
							MRBCheckOut[k], rm[k], channelName);
					app_logs.info(getRatesPerNightChannels);
					// rateGrid.collapseRoomClass(driver, test_steps, rm[k]);
					getRates1.clear();
					getExAdult1.clear();
					getExChild1.clear();
					getData(getRatesPerNightChannels);

					Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
							MRBCheckIn[k]);
					app_logs.info("Start Date: " + fromDate);
					Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
							MRBCheckOut[k]);
					app_logs.info("End Date: " + toDate);
					datesD = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
					app_logs.info("Dates Are: " + dates);
					dates1.add(datesD);
					// dates1.add(dates);
					Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);

					Utility.app_logs.info("dates : " + datesD.size());
					for (int i = 0; i < datesD.size()-1; i++) {
						String rateIs = StringUtils.substringBetween(
								getRatesPerNightChannels.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), datesD.get(i))),
								"RRate:", "ExCh:");
						app_logs.info("rateIs" + rateIs);
						String ch = StringUtils.substringBetween(
								getRatesPerNightChannels.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), datesD.get(i))),
								"ExCh:", "ExAd:");
						app_logs.info(ch);
						String ad = StringUtils
								.substringAfter(getRatesPerNightChannels.get(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), datesD.get(i))), "ExAd:");
						app_logs.info(ad);
						getRates1.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
								datesD.get(i)), rateIs);
						getExAdult1.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
								datesD.get(i)), ad);
						getExChild1.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
								datesD.get(i)), ch);
						Utility.app_logs.info("getRates1 : " + getRates1);
					}
					ratesList.add(getRates1);
					exAdultList.add(getExAdult1);
					exChildList.add(getExChild1);
					app_logs.info(ratesList);
					app_logs.info(getExAdult1);
					app_logs.info(getExChild1);
					
					
					if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
						
						rateGrid.searchRatePlan(driver, RatePlanName);
						rateGrid.clickForRateGridCalender(driver, test_steps);
						Utility.selectDateFromDatePicker(driver, ChangeResStartDate, "dd/MM/yyyy");
						rateGrid.expandRoomClass(driver, ReservationChangeRoomClass);
						rateGrid.expandChannel(driver, test_steps, ReservationChangeRoomClass, channelName);
						
						minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);
						minruleChange = minStayRuleChnage;

						Collections.sort(minruleChange);
						Utility.app_logs.info("minruleChnage : " + minruleChange);

						minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

						if (minStayRuleValueChange > 0) {
							isMinStayRuleChnage = true;
							isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver, minruleChange,minStayRuleValueChange, daysChnage);
						}

						noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);

						Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

						checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,ChangeResStartDate, ChangeResEndDate);

						Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

						noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);

						Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

						checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRuleChnage,ChangeResStartDate, ChangeResEndDate);
						Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

						rateGrid.clickOnEditRatePlan(driver);
						folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);

						rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

						verifyLenthOfStayCheckedChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
						app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayCheckedChange);
						if (verifyLenthOfStayCheckedChange) {

							verifyMinStayCondidtionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
							verifyMaxStayConditionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
							if (verifyMinStayCondidtionChange) {
								String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
								addStayofLengthChange.put("Min", getMin);
							}
							if (verifyMaxStayConditionChange) {
								String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
								addStayofLengthChange.put("Max", getMax);
							}
						}
						isBookinWindowChange = rateGrid.isBookingWindowChecked(driver, test_steps);
						HashMap<String, String> bookingWindowRestrictionsChange = new HashMap<>();
						test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"+ RatePlanName + "</b> ==========");
						bookingWindowRestrictionsChange = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);

						Utility.app_logs.info(bookingWindowRestrictionsChange);

						restricrionsLengthOfStayChange = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,verifyLenthOfStayCheckedChange, verifyMinStayCondidtionChange, verifyMaxStayConditionChange, addStayofLengthChange,daysChnage);

						Utility.app_logs.info(addStayofLengthChange);
						Utility.app_logs.info(restricrionsLengthOfStayChange);

						restricrionsBookingWindowChange = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,isBookinWindowChange, ChangeResStartDate, ChangeResEndDate, bookingWindowRestrictionsChange);

						Utility.app_logs.info("isBookinWindowChange : " + isBookinWindowChange);
						Utility.app_logs.info("restricrionsBookingWindowChange : " + restricrionsBookingWindowChange);

						isPromocodeChnage = rateGrid.isPromoCodeChecked(driver, test_steps);

						if (isPromocode) {
							PromoCodeChange = rateGrid.getPromoCode(driver, test_steps);
						} else {
							PromoCodeChange = "";
						}
						Utility.app_logs.info("PromoCodeChange : " + PromoCodeChange);

						if (isVerifyPoliciesChange) {
							getRateLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
						}

						nightlyRate.switchCalendarTab(driver, test_steps);

						if (ReservationType.equalsIgnoreCase("MRB")) {
							isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,ChangeResStartDate, ChangeResEndDate);
						} else {
							ArrayList<String> checkInListChnage = Utility.convertTokenToArrayList(ChangeResStartDate, delim);

							ArrayList<String> checkOutListChange = Utility.convertTokenToArrayList(ChangeResStartDate, delim);

							ArrayList<Boolean> isSeasonChange = new ArrayList<Boolean>();
							int index = 0;
							for (String checkIn : checkInListChnage) {
								isSeasonChange.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,	ChangeResStartDate, checkOutListChange.get(index++)));
							}

							for (Boolean season : isSeasonChange) {
								if (!season) {
									isSeasonExistChange = false;
									break;
								}
							}
						}
						
						
						if (isSeasonExistChange) {
							nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
							nightlyRate.clickEditThisSeasonButton(driver, test_steps);

							capacityAdultChange.put(ReservationChangeRoomClass, nightlyRate.getAdultCapacity(driver, ReservationChangeRoomClass));
							capacityChildChange.put(ReservationChangeRoomClass, nightlyRate.getChildCapacity(driver, ReservationChangeRoomClass));
							maxAdultChange.put(ReservationChangeRoomClass, nightlyRate.getMaxAdult(driver, ReservationChangeRoomClass));
							maxChildChange.put(ReservationChangeRoomClass, nightlyRate.getMaxPersons(driver,ReservationChangeRoomClass));
							test_steps.add("Room Class : " + ReservationChangeRoomClass + " Adult Capacity: <b>"
									+ capacityAdultChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Room Class : " + ReservationChangeRoomClass + " Person's Capacity: <b>"
									+ capacityChildChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Rate Plan Max. Adults: <b>" + maxAdultChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Rate Plan Max. Childs: <b>" + maxChildChange.get(ReservationChangeRoomClass) + "</b>");


							nightlyRate.closeSeason(driver, test_steps);
							rateGrid.clickOnSaveratePlan(driver);
							Wait.wait5Second();
							rateGrid.closeOpendTabInMainMenu(driver);

							if (isVerifyPolicies) {
								driver.navigate().refresh();

								rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
								rateGrid.clickOnEditRatePlan(driver);
								nightlyRate.switchCalendarTab(driver, test_steps);
								nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
								nightlyRate.clickEditThisSeasonButton(driver, test_steps);

								nightlyRate.clickSeasonPolicies(driver, test_steps);

								getSessionLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);

								test_steps.add("==================Get Policy from  Season==================");
								ArrayList<String> deposit= new ArrayList<String>();
								ArrayList<String> checkin= new ArrayList<String>();
								if (CheckInDate.split("\\|").length>1) {
									for(int i=0;i<RoomClass.length;i++) {
										deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[i],test_steps));
										checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[i],test_steps));											
									}
								}else {
									deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[0],test_steps));
									checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[0],test_steps));					
								}

								for (String str : deposit) {
									if (str != null) {
										seasonDepositPolicyChange.add(str);
									}
								}
								for (String str : checkin) {
									if (str != null) {
										seasonCheckInPolicyChange.add(str);
									}
								}
								app_logs.info(seasonDepositPolicyChange);
								app_logs.info(seasonCheckInPolicyChange);		

								nightlyRate.closeSeason(driver, test_steps);

								rateGrid.clickOnSaveratePlan(driver);
								Wait.wait5Second();
								rateGrid.closeOpendTabInMainMenu(driver);
							}

							rateGrid.clickForRateGridCalender(driver, test_steps);
							if (ReservationType.equalsIgnoreCase("MRB")) {

								rateGrid.selectDateFromDatePicker(driver, ChangeResStartDate,ratesConfig.getProperty("defaultDateFormat"), test_steps);
								Wait.waitUntilPageLoadNotCompleted(driver, 50);
								rateGrid.clickRatePlanArrow(driver, test_steps);
								rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
								Wait.waitUntilPageLoadNotCompleted(driver, 40);
								rateGrid.expandRoomClass(driver, test_steps, ReservationChangeRoomClass);
								test_steps.add("===Get Data From Rate Plan===");
								getRatesPerNightChannelsChange = rateGrid.getRoomRatesExAdExChOfChannel(driver, ChangeResStartDate,ChangeResEndDate, ReservationChangeRoomClass, channelName);
								app_logs.info(getRatesPerNightChannelsChange);
								getData(getRatesPerNightChannelsChange);

								fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
										ChangeResStartDate);
								app_logs.info("Start Date: " + fromDate);
								toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
										ChangeResEndDate);
								app_logs.info("End Date: " + toDate);
								datesChnage = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
								app_logs.info("Dates Are: " + dates);

								for (int i = 0; i < dates.size(); i++) {
									String rateIs = StringUtils.substringBetween(
											getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
													ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
											"RRate:", "ExCh:");
									app_logs.info(rateIs);
									String ch = StringUtils.substringBetween(
											getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
													ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
											"ExCh:", "ExAd:");
									app_logs.info(ch);
									String ad = StringUtils
											.substringAfter(
													getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
															ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
													"ExAd:");
									app_logs.info(ad);
									getRatesChange.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
											datesChnage.get(i)), rateIs);
									getExAdultChange.put(Utility.convertDateFormattoString(
											ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i)), ad);
									getExChildChange.put(Utility.convertDateFormattoString(
											ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i)), ch);
								}
							}

						} else {
							app_logs.info("No Season For Desired Date");
							nightlyRate.clickSaveRatePlan(driver, test_steps);
							Wait.wait5Second();
						}	
					}

					
				}

			} else {
				
				minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationRoomClasses,
						channelName, days);
				minrule = minStayRule;

				Collections.sort(minrule);
				Utility.app_logs.info("minrule : " + minrule);

				minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

				if (minStayRuleValue > 0) {
					isMinStayRule = true;
					isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
							minStayRuleValue, days);
				}

				noCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationRoomClasses,
						channelName, days);

				Utility.app_logs.info("noCheckInRule : " + noCheckInRule);

				checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRule,
						CheckInDate, CheckOutDate);

				Utility.app_logs.info("checkInColor : " + checkInColor);

				noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationRoomClasses,
						channelName, days);

				Utility.app_logs.info("noCheckOutRule : " + noCheckOutRule);

				checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRule,
						CheckInDate, CheckOutDate);
				Utility.app_logs.info("checkOutColor : " + checkOutColor);

				rateGrid.clickOnEditRatePlan(driver);
				folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);

				rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				verifyLenthOfStayChecked = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
				app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);
				if (verifyLenthOfStayChecked) {

					verifyMinStayCondidtion = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
					verifyMaxStayCondition = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
					if (verifyMinStayCondidtion) {
						String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
						addStayofLength.put("Min", getMin);
					}
					if (verifyMaxStayCondition) {
						String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
						addStayofLength.put("Max", getMax);
					}

				}

				isBookinWindow = rateGrid.isBookingWindowChecked(driver, test_steps);

				HashMap<String, String> bookingWindowRestrictions = new HashMap<>();

				test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"
						+ RatePlanName + "</b> ==========");
				bookingWindowRestrictions = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);

				Utility.app_logs.info(bookingWindowRestrictions);

				restricrionsLengthOfStay = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,
						verifyLenthOfStayChecked, verifyMinStayCondidtion, verifyMaxStayCondition, addStayofLength,
						days);

				Utility.app_logs.info(addStayofLength);
				Utility.app_logs.info(restricrionsLengthOfStay);

				restricrionsBookingWindow = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,
						isBookinWindow, CheckInDate, CheckOutDate, bookingWindowRestrictions);

				Utility.app_logs.info("isBookinWindow : " + isBookinWindow);
				Utility.app_logs.info("restricrionsBookingWindow : " + restricrionsBookingWindow);

				isPromocode = rateGrid.isPromoCodeChecked(driver, test_steps);

				if (isPromocode) {
					PromoCode = rateGrid.getPromoCode(driver, test_steps);
				} else {
					PromoCode = "";
				}
				Utility.app_logs.info("promoCode : " + PromoCode);

				if (isVerifyPolicies) {
					getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
				}

				nightlyRate.switchCalendarTab(driver, test_steps);

				if (!ReservationType.equalsIgnoreCase("MRB")) {
					isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
							CheckInDate, CheckOutDate);
				} else {
					ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);

					ArrayList<String> checkOutList = Utility.convertTokenToArrayList(CheckOutDate, delim);

					ArrayList<Boolean> isSeason = new ArrayList<Boolean>();
					int index = 0;
					for (String checkIn : checkInList) {
						isSeason.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,
								checkIn, checkOutList.get(index++)));
					}

					for (Boolean season : isSeason) {
						if (!season) {
							isSeasonExist = false;
							break;
						}
					}
				}
					
					if (isSeasonExist) {
						if (!ReservationType.equalsIgnoreCase("MRB")) {
							nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
							nightlyRate.clickEditThisSeasonButton(driver, test_steps);

							capacityAdult.put(ReservationRoomClasses, nightlyRate.getAdultCapacity(driver, ReservationRoomClasses));
							capacityChild.put(ReservationRoomClasses, nightlyRate.getChildCapacity(driver, ReservationRoomClasses));
							maxAdult.put(ReservationRoomClasses, nightlyRate.getMaxAdult(driver, ReservationRoomClasses));
							maxChild.put(ReservationRoomClasses, nightlyRate.getMaxPersons(driver,ReservationRoomClasses));
							test_steps.add("Room Class : " + ReservationRoomClasses + " Adult Capacity: <b>"
									+ capacityAdult.get(ReservationRoomClasses) + "</b>");
							test_steps.add("Room Class : " + ReservationRoomClasses + " Person's Capacity: <b>"
									+ capacityChild.get(ReservationRoomClasses) + "</b>");
							test_steps.add("Rate Plan Max. Adults: <b>" + maxAdult.get(ReservationRoomClasses) + "</b>");
							test_steps.add("Rate Plan Max. Childs: <b>" + maxChild.get(ReservationRoomClasses) + "</b>");
						}
						
						nightlyRate.closeSeason(driver, test_steps);
						rateGrid.clickOnSaveratePlan(driver);
						Wait.wait5Second();
						rateGrid.closeOpendTabInMainMenu(driver);

						if (isVerifyPolicies) {
							driver.navigate().refresh();
							rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
							rateGrid.clickOnEditRatePlan(driver);
							nightlyRate.switchCalendarTab(driver, test_steps);
							nightlyRate.selectSeasonDates(driver, test_steps, CheckInDate);
							nightlyRate.clickEditThisSeasonButton(driver, test_steps);

							nightlyRate.clickSeasonPolicies(driver, test_steps);
							getSessionLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
							test_steps.add("==================Get Policy from  Season==================");
							ArrayList<String> deposit= new ArrayList<String>();
							ArrayList<String> checkin= new ArrayList<String>();
							if (CheckInDate.split("\\|").length>1) {
									for(int i=0;i<RoomClass.length;i++) {
									deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[i],test_steps));
									checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[i],test_steps));											
								}
							}else {
								deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[0],test_steps));
								checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[0],test_steps));					
							}
							
							for (String str : deposit) {
								if (str != null) {
									seasonDepositPolicy.add(str);
								}
							}
							for (String str : checkin) {
								if (str != null) {
									seasonCheckInPolicy.add(str);
								}
							}
							app_logs.info(seasonDepositPolicy);
							app_logs.info(seasonCheckInPolicy);		

							nightlyRate.closeSeason(driver, test_steps);
							app_logs.info("Close Season");		
							rateGrid.clickOnSaveratePlan(driver);
							Wait.wait5Second();
							rateGrid.closeOpendTabInMainMenu(driver);
						}

						rateGrid.clickForRateGridCalender(driver, test_steps);
						if (!ReservationType.equalsIgnoreCase("MRB")) {

							rateGrid.selectDateFromDatePicker(driver, CheckInDate,
									ratesConfig.getProperty("defaultDateFormat"), test_steps);
							Wait.waitUntilPageLoadNotCompleted(driver, 50);
							rateGrid.clickRatePlanArrow(driver, test_steps);
							rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
							Wait.waitUntilPageLoadNotCompleted(driver, 40);
							rateGrid.expandRoomClass(driver, test_steps, ReservationRoomClasses);
							test_steps.add("===Get Data From Rate Plan===");
							getRatesPerNightChannels = rateGrid.getRoomRatesExAdExChOfChannel(driver, CheckInDate,
									CheckOutDate, ReservationRoomClasses, channelName);
							app_logs.info(getRatesPerNightChannels);
							getData(getRatesPerNightChannels);

							Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									CheckInDate);
							app_logs.info("Start Date: " + fromDate);
							Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									CheckOutDate);
							app_logs.info("End Date: " + toDate);
							dates = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
							app_logs.info("Dates Are: " + dates);

							for (int i = 0; i < dates.size(); i++) {
								String rateIs = StringUtils.substringBetween(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),"RRate:", "ExCh:");
								app_logs.info(rateIs);
								String ch = StringUtils.substringBetween(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),"ExCh:", "ExAd:");
								app_logs.info(ch);
								String ad = StringUtils.substringAfter(getRatesPerNightChannels.get(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i))),"ExAd:");
								app_logs.info(ad);
								getRates.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),dates.get(i)), rateIs);
								getExAdult.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ad);
								getExChild.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"), dates.get(i)), ch);
							}
						}
					} else {
						app_logs.info("No Season For Desired Date");
						nightlyRate.clickSaveRatePlan(driver, test_steps);
						Wait.wait5Second();
					}

				if(ActionOnReservation.equalsIgnoreCase("Recalculate")||ActionOnReservation.equalsIgnoreCase("No Change")||ActionOnReservation.equalsIgnoreCase("Changed Dates")) {
								
					rateGrid.searchRatePlan(driver, RatePlanName);
					rateGrid.clickForRateGridCalender(driver, test_steps);
					Utility.selectDateFromDatePicker(driver, ChangeResStartDate, "dd/MM/yyyy");
					rateGrid.expandRoomClass(driver, ReservationChangeRoomClass);
					rateGrid.expandChannel(driver, test_steps, ReservationChangeRoomClass, channelName);
					
					minStayRuleChnage = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);
					minruleChange = minStayRuleChnage;

					Collections.sort(minruleChange);
					Utility.app_logs.info("minruleChnage : " + minruleChange);

					minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

					if (minStayRuleValueChange > 0) {
						isMinStayRuleChnage = true;
						isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver, minruleChange,minStayRuleValueChange, daysChnage);
					}

					noCheckInRuleChnage = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);

					Utility.app_logs.info("noCheckInRuleChnage : " + noCheckInRuleChnage);

					checkInColorChnage = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChnage,ChangeResStartDate, ChangeResEndDate);

					Utility.app_logs.info("checkInColorChange : " + checkInColorChnage);

					noCheckOutRuleChnage = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationChangeRoomClass,channelName, daysChnage);

					Utility.app_logs.info("noCheckOutRuleChnage : " + noCheckOutRuleChnage);

					checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRuleChnage,ChangeResStartDate, ChangeResEndDate);
					Utility.app_logs.info("checkOutColorChange : " + checkOutColorChange);

					rateGrid.clickOnEditRatePlan(driver);
					folioName = rateGrid.getFolioNameOfRatePlan(driver, test_steps);

					rateGrid.clickOnRestrcitionSAndPoliciesTab(driver);

					verifyLenthOfStayCheckedChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Length of stay");
					app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayCheckedChange);
					if (verifyLenthOfStayCheckedChange) {

						verifyMinStayCondidtionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Min");
						verifyMaxStayConditionChange = rateGrid.verifyLenthOfStayCheckBox(driver, test_steps, "Max");
						if (verifyMinStayCondidtionChange) {
							String getMin = rateGrid.getMinAndMaxValue(driver, "Min");
							addStayofLengthChange.put("Min", getMin);
						}
						if (verifyMaxStayConditionChange) {
							String getMax = rateGrid.getMinAndMaxValue(driver, "Max");
							addStayofLengthChange.put("Max", getMax);
						}
					}
					isBookinWindowChange = rateGrid.isBookingWindowChecked(driver, test_steps);
					HashMap<String, String> bookingWindowRestrictionsChange = new HashMap<>();
					test_steps.add("========== Getting booking window default restrictions for rate plan " + "<b>"+ RatePlanName + "</b> ==========");
					bookingWindowRestrictionsChange = rateGrid.getBookingWindowRestrictions(driver, test_steps, RatePlanName);

					Utility.app_logs.info(bookingWindowRestrictionsChange);

					restricrionsLengthOfStayChange = reservationPage.verifylenthOfStayRestrictions(driver, test_steps,verifyLenthOfStayCheckedChange, verifyMinStayCondidtionChange, verifyMaxStayConditionChange, addStayofLengthChange,daysChnage);

					Utility.app_logs.info(addStayofLengthChange);
					Utility.app_logs.info(restricrionsLengthOfStayChange);

					restricrionsBookingWindowChange = reservationPage.verifyBookingWindowRestrictions(driver, test_steps,isBookinWindowChange, ChangeResStartDate, ChangeResEndDate, bookingWindowRestrictionsChange);

					Utility.app_logs.info("isBookinWindowChange : " + isBookinWindowChange);
					Utility.app_logs.info("restricrionsBookingWindowChange : " + restricrionsBookingWindowChange);

					isPromocodeChnage = rateGrid.isPromoCodeChecked(driver, test_steps);

					if (isPromocode) {
						PromoCodeChange = rateGrid.getPromoCode(driver, test_steps);
					} else {
						PromoCodeChange = "";
					}
					Utility.app_logs.info("PromoCodeChange : " + PromoCodeChange);

					if (isVerifyPoliciesChange) {
						getRateLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
					}

					nightlyRate.switchCalendarTab(driver, test_steps);

					if (!ReservationType.equalsIgnoreCase("MRB")) {
						isSeasonExistChange = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,ChangeResStartDate, ChangeResEndDate);
					} else {
						ArrayList<String> checkInListChnage = Utility.convertTokenToArrayList(ChangeResStartDate, delim);

						ArrayList<String> checkOutListChange = Utility.convertTokenToArrayList(ChangeResStartDate, delim);

						ArrayList<Boolean> isSeasonChange = new ArrayList<Boolean>();
						int index = 0;
						for (String checkIn : checkInListChnage) {
							isSeasonChange.add(nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps,	ChangeResStartDate, checkOutListChange.get(index++)));
						}

						for (Boolean season : isSeasonChange) {
							if (!season) {
								isSeasonExistChange = false;
								break;
							}
						}
					}
					
					
					if (isSeasonExistChange) {
						if (!ReservationType.equalsIgnoreCase("MRB")) {
							nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
							nightlyRate.clickEditThisSeasonButton(driver, test_steps);

							capacityAdultChange.put(ReservationChangeRoomClass, nightlyRate.getAdultCapacity(driver, ReservationChangeRoomClass));
							capacityChildChange.put(ReservationChangeRoomClass, nightlyRate.getChildCapacity(driver, ReservationChangeRoomClass));
							maxAdultChange.put(ReservationChangeRoomClass, nightlyRate.getMaxAdult(driver, ReservationChangeRoomClass));
							maxChildChange.put(ReservationChangeRoomClass, nightlyRate.getMaxPersons(driver,ReservationChangeRoomClass));
							test_steps.add("Room Class : " + ReservationChangeRoomClass + " Adult Capacity: <b>"
									+ capacityAdultChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Room Class : " + ReservationChangeRoomClass + " Person's Capacity: <b>"
									+ capacityChildChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Rate Plan Max. Adults: <b>" + maxAdultChange.get(ReservationChangeRoomClass) + "</b>");
							test_steps.add("Rate Plan Max. Childs: <b>" + maxChildChange.get(ReservationChangeRoomClass) + "</b>");
						}
						
						nightlyRate.closeSeason(driver, test_steps);
						rateGrid.clickOnSaveratePlan(driver);
						Wait.wait5Second();
						rateGrid.closeOpendTabInMainMenu(driver);

						if (isVerifyPolicies) {
							driver.navigate().refresh();

							rateGrid.selectRatePlan(driver, RatePlanName, test_steps);
							rateGrid.clickOnEditRatePlan(driver);
							nightlyRate.switchCalendarTab(driver, test_steps);
							nightlyRate.selectSeasonDates(driver, test_steps, ChangeResStartDate);
							nightlyRate.clickEditThisSeasonButton(driver, test_steps);

							nightlyRate.clickSeasonPolicies(driver, test_steps);

							getSessionLevelPolicyChange = nightlyRate.getAllTypeSelectedPolicy(driver, test_steps);
							
							test_steps.add("==================Get Policy from  Season==================");
							ArrayList<String> deposit= new ArrayList<String>();
							ArrayList<String> checkin= new ArrayList<String>();
							if (CheckInDate.split("\\|").length>1) {
									for(int i=0;i<RoomClass.length;i++) {
									deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[i],test_steps));
									checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[i],test_steps));											
								}
							}else {
								deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),RoomClass[0],test_steps));
								checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),RoomClass[0],test_steps));					
							}
							
							for (String str : deposit) {
								if (str != null) {
									seasonDepositPolicyChange.add(str);
								}
							}
							for (String str : checkin) {
								if (str != null) {
									seasonCheckInPolicyChange.add(str);
								}
							}
							app_logs.info(seasonDepositPolicyChange);
							app_logs.info(seasonCheckInPolicyChange);		

							nightlyRate.closeSeason(driver, test_steps);
							
							rateGrid.clickOnSaveratePlan(driver);
							Wait.wait5Second();
							rateGrid.closeOpendTabInMainMenu(driver);
						}

						rateGrid.clickForRateGridCalender(driver, test_steps);
						if (!ReservationType.equalsIgnoreCase("MRB")) {

							rateGrid.selectDateFromDatePicker(driver, ChangeResStartDate,ratesConfig.getProperty("defaultDateFormat"), test_steps);
							Wait.waitUntilPageLoadNotCompleted(driver, 50);
							rateGrid.clickRatePlanArrow(driver, test_steps);
							rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
							Wait.waitUntilPageLoadNotCompleted(driver, 40);
							rateGrid.expandRoomClass(driver, test_steps, ReservationChangeRoomClass);
							test_steps.add("===Get Data From Rate Plan===");
							getRatesPerNightChannelsChange = rateGrid.getRoomRatesExAdExChOfChannel(driver, ChangeResStartDate,ChangeResEndDate, ReservationChangeRoomClass, channelName);
							app_logs.info(getRatesPerNightChannelsChange);
							getData(getRatesPerNightChannelsChange);

							Date fromDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									ChangeResStartDate);
							app_logs.info("Start Date: " + fromDate);
							Date toDate = Utility.convertStringtoDateFormat(ratesConfig.getProperty("defaultDateFormat"),
									ChangeResEndDate);
							app_logs.info("End Date: " + toDate);
							datesChnage = Utility.getDateRangeBetweenfromAndToDate(fromDate, toDate);
							app_logs.info("Dates Are: " + dates);

							for (int i = 0; i < dates.size(); i++) {
								String rateIs = StringUtils.substringBetween(
										getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
												ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
										"RRate:", "ExCh:");
								app_logs.info(rateIs);
								String ch = StringUtils.substringBetween(
										getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
												ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
										"ExCh:", "ExAd:");
								app_logs.info(ch);
								String ad = StringUtils
										.substringAfter(
												getRatesPerNightChannelsChange.get(Utility.convertDateFormattoString(
														ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i))),
												"ExAd:");
								app_logs.info(ad);
								getRatesChange.put(Utility.convertDateFormattoString(ratesConfig.getProperty("defaultDateFormat"),
										datesChnage.get(i)), rateIs);
								getExAdultChange.put(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i)), ad);
								getExChildChange.put(Utility.convertDateFormattoString(
										ratesConfig.getProperty("defaultDateFormat"), datesChnage.get(i)), ch);
							}
						}

					} else {
						app_logs.info("No Season For Desired Date");
						nightlyRate.clickSaveRatePlan(driver, test_steps);
						Wait.wait5Second();
					}	
				}
			}

			
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
			navigation.cpReservation_Backward(driver);
		} catch (Exception e) {
			Actions actions = new Actions(driver);

			actions.sendKeys(Keys.ENTER);
		}

		String Salutation = "Mr.";
		String GuestFirstName = "Test Res";
		String GuestLastName = Utility.generateRandomString();
		String PhoneNumber = "8790321567";
		String AltenativePhone = "8790321577";
		String Email = "innroadautomation@innroad.com";
		String Account = "";
		String Address1 = "test1";
		String Address2 = "test2";
		String Address3 = "test3";
		String City = "test";
		String Country="United States";
		String PostalCode = "12345";
		String IsGuesProfile = "No";
		String PaymentType = "Cash";
		String CardNumber = "5454545454545454";
		String NameOnCard = "Test card";
		String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
		String IsChangeInPayAmount = "No";
		String ChangedAmountValue = "";
		String TravelAgent = "";
		String MarketSegment = "GDS";
		String State="New York";
		String IsSplitRes = "No";
		String Referral ="Other";
		String AccountType="Corporate/Member Accounts";
		String AccountName="AccountName_";
		String MargetSegment="GDS";
		String BlockName="Test Block";
		String RoomPerNight="1";
		String noOfNightsGroupBlock="1";
		
		if (ReservationType.equalsIgnoreCase("MRB")) {
			ArrayList<String> checkInList = Utility.convertTokenToArrayList(CheckInDate, delim);
			Salutation = "";
			PhoneNumber = "";
			GuestFirstName = "";
			GuestLastName = "";
			for (int i = 0; i < checkInList.size(); i++) {

				if (i == 0) {
					Salutation = "Mr.";
					PhoneNumber = "2314567890";
					GuestFirstName = "MRB Res";
					GuestLastName = "last Name";
				} else {
					Salutation = "Mr." + "|" + Salutation;
					PhoneNumber = PhoneNumber + "|" + "2314567890";
					GuestFirstName = GuestFirstName + "|" + "MRB Res";
					GuestLastName = GuestLastName + "|" + "last Name";
				}
			}
		}

		String reservation = null;
		String status = null;
		boolean isReservationCreated = false;
		if (ReservationType.equalsIgnoreCase("Single")) {
			try {
				Double depositAmount = 0.00;
				reservationPage.click_NewReservation(driver, test_steps);
				test_steps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0&& isRoomClassAvailable) {
				
				
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist && isSeasonExist) {
						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
								capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
								test_steps, ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);
						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						depositAmount = reservationPage.deposit(driver, test_steps, "No", "10");
						reservationPage.clickNext(driver, test_steps);

						reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}
						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						reservationPage.clickBookNow(driver, test_steps);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
						status = reservationPage.get_ReservationStatus(driver, test_steps);
						isReservationCreated = true;
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						reservationPage.get_RoomNumber(driver, test_steps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);

						String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

						String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
						foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
						String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
						foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
						Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + RatePlanName);
							app_logs.info("Rate plan folio name is matched : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy=reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy=reservationPage.getReservationCheckInPolicy(driver);

						String ratePlanCheckInPolicy=getRateLevelPolicy.get("Check-in");
						String seasonCheckInpolicy=getSessionLevelPolicy.get("Check-in");
						
						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);
						folioRoomCharges = folioRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}
						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							test_steps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					boolean flag=false;
					try {
						reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						flag=true;
					} catch (Error e) {
						
					} catch (Exception e) {
						
					}
					if(!flag) {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
					}
				}
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
		} else if (ReservationType.equalsIgnoreCase("MRB")) {
			try {
				String Rateplan = RatePlanName + "|" + RatePlanName;
				reservationPage.click_NewReservation(driver, test_steps);
				if (PromoCode.isEmpty()) {
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, PromoCode, IsSplitRes);
				} else {
					String rateplan = "Promo Code|Promo Code";
					PromoCode = PromoCode + "|" + PromoCode;
					reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, adult, children,
							Rateplan, PromoCode, IsSplitRes);
				}

				if (IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, test_steps, adult);
					reservationPage.enter_Children(driver, test_steps, children);
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				}

				reservationPage.clickOnFindRooms(driver, test_steps);
				String minStayColor = "";

				String[] roomClass = ReservationRoomClasses.split("\\|");

				ArrayList<String> minStayColorMRB = new ArrayList<String>();
				ArrayList<String> nocheckinColorMRB = new ArrayList<String>();
				ArrayList<String> nocheckoutColorMRB = new ArrayList<String>();

				boolean mrbFlag = true;

				for (int i = 0; i < roomClass.length; i++) {

					if (driver.findElements(By.xpath("(//span[text()='" + roomClass[i] + "'])[2]")).size() > 0
							&& isRoomClassAvailable) {

						boolean restriction = true;

						for (int m = 0; m < restricrionsLengthOfStayMRB.size(); m++) {
							if (!restricrionsLengthOfStayMRB.get(m) || !restricrionsBookingWindowMRB.get(m)) {
								restriction = false;
								break;
							}
						}

						if (restriction && isSeasonExist) {

						} else {
							try {
								reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
								mrbFlag = false;
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
					} else {
						boolean flag=false;
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
							flag=true;
						} catch (Error e) {
							
						} catch (Exception e) {
							
						}
						if(!flag) {
						test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
						app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
						}
						test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
						app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
						mrbFlag = false;
						break;
					}
				}

				if (mrbFlag) {
					ArrayList roomCost = reservationPage.select_MRBRoomsRatePlanValidation1(driver, test_steps,
							ReservationRoomClasses, "Yes", Account, adult, isMinStayRuleMRB, isMinStayRuleBrokenPopComeOrNotMRB,
							minStayRuleValueMRB,checkInColorMRB,checkOutColorMRB);
					Utility.app_logs.info("roomCost : " + roomCost);
					Double depositAmount = reservationPage.deposit(driver, test_steps, "No", "");
					reservationPage.clickNext(driver, test_steps);
					reservationPage.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
							GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1,
							Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, "No", IsSplitRes,
							Rooms);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					Utility.app_logs.info(Rooms);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment,
							Referral);
					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					status = reservationPage.get_ReservationStatus(driver, test_steps);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);

					String[] roomClass1 = ReservationRoomClasses.split("\\|");
					String[] adu = adult.split("\\|");
					String[] child = children.split("\\|");

					double str = 0.00;

					for (int i = 0; i < roomClass1.length; i++) {
						str = str + reservationPage.calculateRatesAsPerAdultsAndChildCapacityMRB(driver,
								ratesList.get(i), ratePlanAdults.get(i), ratePlanChilds.get(i), exAdultList.get(i),
								exChildList.get(i), capacityChild1.get(i), capacityAdult1.get(i), adu[i], child[i],
								test_steps, ratesConfig.getProperty("noCombination"), roomClass1[i],
								(List<Date>) dates1.get(i), ratesConfig.getProperty("defaultDateFormat"));
						app_logs.info(str);
					}

					test_steps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					ArrayList<String> ratePlanName = reservationPage.verifyRatePlanForMRB(driver, test_steps, Rateplan);

					String[] ratePlan = Rateplan.split("\\|");
					for (int i = 0; i < ratePlanName.size(); i++) {
						try {
							assertTrue(ratePlan[i].trim().equalsIgnoreCase(ratePlanName.get(i)),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + ratePlanName.get(i));
							app_logs.info("Rate plan is matched for room class for : " + ratePlanName.get(i));
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}

					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							test_steps);

					
					reservationPage.click_Folio(driver, test_steps);
					ArrayList<String> roomChnrges = reservationPage.getMRBFolioBalance(driver, test_steps, RoomAbbri,
							"Yes", Rooms);

					test_steps.add("=================== Verify Folio Name In Reservation ======================");
					app_logs.info("=================== Verify Folio Name In Reservation ======================");
					boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
					try {
						assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
						test_steps.add("Rate plan folio name is matched : " + RatePlanName);
						app_logs.info("Rate plan folio name is matched : " + RatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					double expectedRoomCharges = str;
					double folioRoomCharges = 0.00;
					for (int i = 0; i < roomChnrges.size(); i++) {
						folioRoomCharges = folioRoomCharges + Double.parseDouble(roomChnrges.get(i).toString());
					}
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
					try {
						assertTrue(Double.compare(expectedRoomCharges, folioRoomCharges) == 0,
								"Expeced room charges and folio room charges are not matched");
						test_steps
								.add("Expected room charges and folio room charges are same : " + expectedRoomCharges);
						app_logs.info("Expected room charges and folio room charges are same : " + expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}

					try {
						assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
								"Expeced room charges and trip summary room charges are not matched");
						test_steps.add("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
						app_logs.info("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}
					reservationPage.click_DeatilsTab(driver, test_steps);
					reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
					reervationDepositPolicy=reservationPage.getReservationDepositPolicy(driver);
					
					test_steps.add("Getting reservation no show policy : "+reervationNoShowPolicy);
					app_logs.info("Getting reservation no show policy : "+reervationNoShowPolicy);
					
					test_steps.add("Getting reservation deposit policy : "+reervationDepositPolicy);
					app_logs.info("Getting reservation deposit policy : "+reervationDepositPolicy);
					
				}
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

		} else if (ReservationType.equalsIgnoreCase("Group")) {

			// Create New Groups
			String AccountNo = "0";
			try {
				AccountName = AccountName + Utility.GenerateRandomString15Digit();
				navigation.Groups(driver);
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
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Billinginfo(driver);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver,  getTest_Steps);
				test_steps.addAll(getTest_Steps);
				Account=AccountName;
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;
			try {
				getTest_Steps.clear();
				getTest_Steps = group.newReservation(driver);
				test_steps.addAll(getTest_Steps);
				test_steps.add("=================== CREATE Reservation ======================");
				app_logs.info("=================== CREATE Reservation ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, "2");
				reservationPage.enter_Children(driver, test_steps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
							&& isRoomClassAvailable) {

						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
								capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
								test_steps, ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);

						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
						

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes",Account, noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", GuestFirstName,
								GuestLastName);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, Email);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null,
								null);
						test_steps.addAll(getTest_Steps);

						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						isReservationCreated = true;

						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + RatePlanName);
							app_logs.info("Rate plan folio name is matched : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy=reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy=reservationPage.getReservationCheckInPolicy(driver);

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							test_steps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					boolean flag=false;
					try {
						reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						flag=true;
					} catch (Error e) {
						
					} catch (Exception e) {
						
					}
					if(!flag) {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Group block")) {
			// Create New Groups
			String AccountNo = "0";
			try {
				isReservationCreated = false;
				navigation.Groups(driver);
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
				group.type_MailingAddrtess(driver, test, GuestFirstName, GuestLastName, PhoneNumber, Address1, City,
						State, Country, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Billinginfo(driver);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				group.Save(driver, getTest_Steps);
				test_steps.addAll(getTest_Steps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Create RoomBlock

			try {

				group.navigateRoomBlock(driver, test);
				Utility.app_logs.info("Navigate to Room Block Tab");
				test_steps.add("Navigate to Room Block Tab");
				app_logs.info("==========CREATE NEW BLOCK==========");
				test_steps.add("==========CREATE NEW BLOCK==========");

				getTest_Steps.clear();
				getTest_Steps = group.navigateRoomBlock(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.ClickNewBlock(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.EnterBlockName(driver, BlockName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.ClickOkay_CreateNewBlock(driver);
				test_steps.addAll(getTest_Steps);

				app_logs.info("==========SEARCH ROOMS==========");
				test_steps.add("==========SEARCH ROOMS==========");

				getTest_Steps.clear();
				getTest_Steps = group.searchRoomsForBlock(driver, CheckInDate, CheckOutDate, RoomPerNight);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.SelectRatePlan(driver, RatePlanName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = group.ClickSearchGroup(driver);
				test_steps.addAll(getTest_Steps);

				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					advgroup.verifyAvgRate(driver, delim, ReservationRoomClasses, RatePerNight, test_steps);
				} else {
					advgroup.verifyAvgRate(driver, delim, ReservationRoomClasses, "0", test_steps);
				}

				advgroup.updatedAutomaticallyAssignedRooms(driver, "0");
				advgroup.BlockRoomForSelectedRoomclass(driver, RoomPerNight, ReservationRoomClasses);

				getTest_Steps.clear();
				getTest_Steps = group.clickCreateBlock(driver);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				group.Save(driver,  getTest_Steps);
				test_steps.addAll(getTest_Steps);
				group.navigateRoomBlock(driver, test);
				Utility.app_logs.info("Navigate to Room Block Tab");
				test_steps.add("Navigate to Room Block Tab");

				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					group.verifyTotalAndNightlyRate(driver, delim, ReservationRoomClasses, RatePerNight, test_steps);
				} else {
					group.verifyTotalAndNightlyRate(driver, delim, ReservationRoomClasses, "0", test_steps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (ReservationType.equalsIgnoreCase("Account")) {

			// navigate to accounts
			try {

				navigation.Accounts(driver);
				test_steps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
			String AccountNumber = null;
			// New account
			try {
		        

				test_steps.add("****************** Creating account *********************");
				app_logs.info("****************** Creating account *********************");
				AccountName = AccountName + Utility.generateRandomString();
				CreateTA.ClickNewAccountbutton(driver, AccountType);
				CreateTA.AccountDetails(driver, AccountType, AccountName);
				AccountNumber = Utility.GenerateRandomString15Digit();
				CreateTA.ChangeAccountNumber(driver, AccountNumber);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.AccountAttributes(driver, test, MarketSegment, Referral, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.Mailinginfo(driver, test, GuestFirstName, GuestLastName, PhoneNumber,
						PhoneNumber, Address1, Address2, Address3, Email, City, State, PostalCode, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				getTest_Steps.clear();
				getTest_Steps = CreateTA.Billinginfo(driver, test, getTest_Steps);
				test_steps.addAll(getTest_Steps);
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					if(policyType.equalsIgnoreCase("No Show")){
						CreateTA.selectNoShowPolicyForAccount(driver, test_steps, PoliciesName);
					}else if(policyType.equalsIgnoreCase("Check In")) {
						CreateTA.selectPolicyForAccount(driver, policyTypes, policyName, policyType, selectedpolicyNames, isAccountPolicyCreate);
					}
				}else {
					getTest_Steps.clear();
					getTest_Steps = CreateTA.AccountSave(driver, test, AccountName, getTest_Steps);
					test_steps.addAll(getTest_Steps);
				}
				
				Account=AccountName;
			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Corporate Account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Clicking on New Reservation
			try {
				CreateTA.NewReservationButton(driver, test);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			String reservationNumber = null;
			try {

				test_steps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, "2");
				reservationPage.enter_Children(driver, test_steps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						&& isRoomClassAvailable) {

					
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
								capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
								test_steps, ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);
						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidationAccount(driver, test_steps,
								ReservationRoomClasses, "Yes", Account,isAccountPolicyApplicable, noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickNext(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterGuestName(driver, getTest_Steps, "Mr.", GuestFirstName,
								GuestLastName);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enterEmail(driver, getTest_Steps, Email);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.enter_PaymentDetails(driver, getTest_Steps, "Cash", null, null,
								null);
						test_steps.addAll(getTest_Steps);

						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						getTest_Steps.clear();
						getTest_Steps = reservationPage.clickBookNow(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);

						getTest_Steps.clear();
						String foundStatus = reservationPage.get_ReservationStatus(driver, getTest_Steps);
						test_steps.addAll(getTest_Steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						isReservationCreated = true;
						
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + RatePlanName);
							app_logs.info("Rate plan folio name is matched : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

				
						reservationPage.click_DeatilsTab(driver, test_steps);
						reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
						reervationDepositPolicy=reservationPage.getReservationDepositPolicy(driver);
						reervationCheckInPolicy=reservationPage.getReservationCheckInPolicy(driver);

						String ratePlanCheckInPolicy=getRateLevelPolicy.get("Check-in");
						String seasonCheckInpolicy=getSessionLevelPolicy.get("Check-in");
						

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						Double folioRCharges = Double.parseDouble(folioRoomCharges);
						Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							test_steps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					boolean flag=false;
					try {
						reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						flag=true;
					} catch (Error e) {
						
					} catch (Exception e) {
						
					}
					if(!flag) {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (ReservationType.equalsIgnoreCase("Quote")) {
			try {
				isReservationCreated = false;
				test_steps.add("=================== CREATE QUOTE ======================");

				app_logs.info("=================== CREATE QUOTE ======================");
				reservationPage.click_NewReservation(driver, test_steps);
				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, "2");
				reservationPage.enter_Children(driver, test_steps, "0");
				if (PromoCode.isEmpty()) {
					reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
				} else {
					String rateplan = "Promo Code";
					reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, test_steps);
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0
						&& isRoomClassAvailable) {

					
					String minStayColor = "";
					if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
						String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
								capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
								test_steps, ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
								ratesConfig.getProperty("defaultDateFormat"));
						Utility.app_logs.info(rateIs);
						if (isMinStayRule) {
							if (!isMinStayRuleBrokenPopComeOrNot) {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								Utility.app_logs.info("minStayColor : " + minStayColor);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationRoomClasses, minStayRuleValue);
								try {
									assertTrue(minStayColor.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColor = "";
						if (checkInColor.equalsIgnoreCase("Red")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColor.equalsIgnoreCase("Green")) {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColor = "";
						if (checkOutColor.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColor.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationRoomClasses);
								assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationRoomClasses, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
								minStayRuleValue);
						double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
						reservationPage.clickNext(driver, test_steps);

						reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName,
								GuestLastName, PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3,
								City, Country, State, PostalCode, IsGuesProfile);
						if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
							reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber,
									NameOnCard, CardExpDate);
						}
						reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);

						reservationPage.clickSaveAsQuoteButton(driver);
						reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
						status = reservationPage.get_ReservationStatus(driver, test_steps);
						reservationPage.clickCloseReservationSavePopup(driver, test_steps);
						reservationPage.get_RoomNumber(driver, test_steps, "Yes");
						String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);

						reservationPage.click_Folio(driver, test_steps);
						String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						test_steps.add("=================== Verify Folio Name In Reservation ======================");
						app_logs.info("=================== Verify Folio Name In Reservation ======================");
						boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
						try {
							assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
							test_steps.add("Rate plan folio name is matched : " + RatePlanName);
							app_logs.info("Rate plan folio name is matched : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						reservationPage.click_DeatilsTab(driver, test_steps);

						test_steps.add("=================== Verify Rate Plan In Reservation ======================");
						app_logs.info("=================== Verify Rate Plan In Reservation ======================");
						String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

						try {
							assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
									"Rate plan is not matched for room class");
							test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
							app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}

						double expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						double folioRCharges = Double.parseDouble(folioRoomCharges);
						double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							test_steps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						reservationPage.clickBookQuote(driver, test_steps);

						Wait.wait10Second();
						TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
								test_steps);
						reservationPage.click_Folio(driver, test_steps);
						folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

						reservationPage.click_DeatilsTab(driver, test_steps);
						days = Utility.getNumberofDays(CheckInDate, CheckOutDate);

						expectedRoomCharges = Double.parseDouble(rateIs);

						folioRoomCharges = folioRoomCharges.trim();

						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

						folioRCharges = Double.parseDouble(folioRoomCharges);
						tripRoom = Double.parseDouble(TripSummaryRoomCharges);
						try {
							assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
									"Expeced room charges and folio room charges are not matched");
							test_steps.add(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
							app_logs.info(
									"Expected room charges and folio room charges are same : " + expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}

						try {
							assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
									"Expeced room charges and trip summary room charges are not matched");
							test_steps.add("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
							app_logs.info("Expected room charges and trip summary room charges are same : "
									+ expectedRoomCharges);
						} catch (Error e) {
							test_steps.add(e.toString());
						}
					} else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				} else {
					boolean flag=false;
					try {
						reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						flag=true;
					} catch (Error e) {
						
					} catch (Exception e) {
						
					}
					if(!flag) {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (ReservationType.equalsIgnoreCase("tapechart")) {
			try {

				Tapechart tapechart = new Tapechart();
				test_steps.add("=================== Create Reservation from Tape Chart ======================");
				app_logs.info("=================== Create Reservation from Tape Chart ======================");
				navigation.navigateTapeChart(driver, test);
				test_steps.add("Navigate TapeChart");
				app_logs.info("Navigate TapeChart");
				app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
				test_steps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");

				if (PromoCode.isEmpty()) {
					tapechart.searchInTapechart(driver, CheckInDate, CheckOutDate, adult, children, RatePlanName,
							PromoCode);
				} else {
					tapechart.searchInTapechart(driver, CheckInDate, CheckOutDate, adult, children, RatePlanName,
							PromoCode);
				}

				String minStayColor = "";
				if (restricrionsLengthOfStay && restricrionsBookingWindow && isSeasonExist) {
					String rateIs = "";
					try {
						rateIs = tapechart.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
								maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
								capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
								test_steps, ratesConfig.getProperty("tapeChartMsg"), ReservationRoomClasses, CheckInDate,
								CheckOutDate, ratesConfig.getProperty("defaultDateFormat"), RoomAbbri.get(0));
					} catch (Exception e) {
						Utility.catchException(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart",
								testName, test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Verify Rates On TapeChart Page", "Tape Chart", "Tape Chart",
								testName, test_description, test_catagory, test_steps);
					}
					
					app_logs.info("==========SELECT ROOM==========");
					test_steps.add("==========SELECT ROOM==========");
					tapechart.clickAvailableSlotWithRatePalnValidation(driver, RoomAbbri.get(0), isMinStayRule,
							isMinStayRuleBrokenPopComeOrNot, minStayRuleValue, checkInColor, checkOutColor);
					test_steps.add("Click available room of Room Class '" + RoomAbbri.get(0) + "'");
					app_logs.info("Click on available room");
					test_steps.add("New Reservation page is opened");
					app_logs.info("New Reservation Page is Opened");
					reservationPage.verifyTripSummaryRoomChargesAfterCreateReservationFromTapeChart(driver, test_steps,
							rateIs);
					reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
							PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
							PostalCode, IsGuesProfile);
					if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					}
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
					reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment,
							Referral);

					reservationPage.clickBookNow(driver, test_steps);
					reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
					status = reservationPage.get_ReservationStatus(driver, test_steps);
					isReservationCreated = true;
					reservationPage.clickCloseReservationSavePopup(driver, test_steps);
					reservationPage.get_RoomNumber(driver, test_steps, "Yes");
					String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver,
							test_steps);


					test_steps.add("=================== Verify Rate Plan In Reservation ======================");
					app_logs.info("=================== Verify Rate Plan In Reservation ======================");
					String ratePlan = reservationPage.verifyRatePlan(driver, test_steps, RatePlanName);

					try {
						assertTrue(ratePlan.trim().equalsIgnoreCase(RatePlanName),
								"Rate plan is not matched for room class");
						test_steps.add("Rate plan is matched for room class for : " + RatePlanName);
						app_logs.info("Rate plan is matched for room class for : " + RatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					reservationPage.click_Folio(driver, test_steps);
					String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

					test_steps.add("=================== Verify Folio Name In Reservation ======================");
					app_logs.info("=================== Verify Folio Name In Reservation ======================");

					boolean folioFlag = reservationPage.verifyFolioName(driver, test_steps, folioName);
					try {
						assertTrue(folioFlag == true, "Rate plan folio name is not matched ");
						test_steps.add("Rate plan folio name is matched : " + RatePlanName);
						app_logs.info("Rate plan folio name is matched : " + RatePlanName);
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}

					reservationPage.click_DeatilsTab(driver, test_steps);

					double expectedRoomCharges = Double.parseDouble(rateIs);

					folioRoomCharges = folioRoomCharges.trim();

					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges = TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges = TripSummaryRoomCharges.trim();

					Double folioRCharges = Double.parseDouble(folioRoomCharges);
					Double tripRoom = Double.parseDouble(TripSummaryRoomCharges);
					try {
						assertTrue(Double.compare(expectedRoomCharges, folioRCharges) == 0,
								"Expeced room charges and folio room charges are not matched");
						test_steps
								.add("Expected room charges and folio room charges are same : " + expectedRoomCharges);
						app_logs.info("Expected room charges and folio room charges are same : " + expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}

					try {
						assertTrue(Double.compare(expectedRoomCharges, tripRoom) == 0,
								"Expeced room charges and trip summary room charges are not matched");
						test_steps.add("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
						app_logs.info("Expected room charges and trip summary room charges are same : "
								+ expectedRoomCharges);
					} catch (Error e) {
						test_steps.add(e.toString());
					}

				} else {
					tapechart.verifyNoResultsmatchedInTapechart(driver, test_steps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed", testName, "Tapchart", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if(isReservationCreated) {
		try {
			HashMap<String, String> getdepositAmount = new HashMap<String,String>();
			HashMap<String, Double> getcheckInAmount = new HashMap<String,Double>();
			ArrayList<HashMap<String, String>> getdepositPoliciesData= new ArrayList<HashMap<String, String>>();
			String depositAmount = null;
			String depositPolicyApplied="";
			reervationNoShowPolicy = reservationPage.getReservationNoShowPolicy(driver);
			reervationDepositPolicy=reservationPage.getReservationDepositPolicy(driver);
			reervationCheckInPolicy=reservationPage.getReservationCheckInPolicy(driver);
			
			String rateDepositPlanPolicy=getRateLevelPolicy.get("Deposit");
			String seasonDepositpolicy=getSessionLevelPolicy.get("Deposit");
			
			app_logs.info("seasonDepositpolicy : "+seasonDepositpolicy);
			app_logs.info("reervationDepositPolicy : "+reervationDepositPolicy);
			if(seasonDepositpolicy.equalsIgnoreCase("NA")){
				seasonDepositpolicy="No Policy";
			}
			test_steps.add("=================== Verify the deposit policy in reservation ======================");
			app_logs.info("=================== Verify the deposit policy in reservation ======================");
			assertTrue(seasonDepositpolicy.equalsIgnoreCase(reervationDepositPolicy),"Reservation deposit policy is not matched");
			test_steps.add("Verified Reservation deposit policy is reservation policies : "+seasonDepositpolicy);
			app_logs.info("Verified Reservation deposit policy is reservation policies : "+seasonDepositpolicy);
			
			reservationPage.close_FirstOpenedReservation(driver, test_steps);
			
			if(!reervationDepositPolicy.equalsIgnoreCase("No Policy")) {
			navigation.inventory(driver);		
			
			navigation.policies(driver, test_steps);
			
			if (CheckInDate.split("\\|").length>1) {
				for(String str: seasonDepositPolicy) {
				getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps, ratesConfig.getProperty("depositPolicyText"), str));
				}
				}else {
				getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps, ratesConfig.getProperty("depositPolicyText"), seasonDepositPolicy.get(0)));}
				
			//getdepositPoliciesData.add(policy.getpoliciesData(driver, test_steps, ratesConfig.getProperty("depositPolicyText"),reervationDepositPolicy ));
			
			reservationPage.navigateToReservationPage(driver);
			}
			reservationPage.Search_ResNumber_And_Click(driver, reservation);
			reservationPage.clickFolio(driver, test_steps);
			HashMap<String, String> roomChargesAre= new HashMap<String, String>();
			ArrayList<String> roomCharges = new ArrayList<>();
			
			String[] chkin=CheckInDate.split("\\|");
			String[] chkout=CheckOutDate.split("\\|");
			test_steps.add("==================Verify Deposit Policy On Reservation==================");
			
			roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps,chkin[0],chkout[0], true);
			for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
				roomCharges.add(entry.getValue());
			}
			if (GuestFirstName.split("\\|").length>1) {
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1), Rooms.get(1));
				roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps,chkin[1],chkout[1], true);
				for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
					roomCharges.add(entry.getValue());
				}
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(0), Rooms.get(0));
			}
			app_logs.info(roomCharges);

			if(isAccountPolicyApplicable.equals("Yes") ){
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {							
					depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, policyAttr1Is[0], policyAttrValueIs[0]);}
				else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
					depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, getAccountPoliciesData.get(0).get("Type"), getAccountPoliciesData.get(0).get("AttrValue"));	
				}
			}
			else if(!seasonDepositpolicy.equalsIgnoreCase("No Policy")){
				if (CheckInDate.split("\\|").length>1) {
				for(int i=0;i<seasonDepositPolicy.size();i++) {
						String size= String.valueOf(ReservationRoomClasses.split("\\|").length);
						getdepositAmount.put(seasonDepositPolicy.get(i), reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges, getdepositPoliciesData.get(i).get("Type"), getdepositPoliciesData.get(i).get("AttrValue"),size));
					}
					ArrayList<Double> dbr= new ArrayList<Double>();
					for (Map.Entry<String, String> entry : getdepositAmount.entrySet()) {
						dbr.add(Double.valueOf(entry.getValue()));
					}
					DecimalFormat df = new DecimalFormat("0.00");
					df.setMaximumFractionDigits(2);
					depositAmount=df.format(Collections.max(dbr));
					app_logs.info(depositAmount);
					depositPolicyApplied=Utility.getKeyOfValue(getdepositAmount, depositAmount);
						
				}else {
					depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, getdepositPoliciesData.get(0).get("Type"), getdepositPoliciesData.get(0).get("AttrValue"));
					//depositPolicyApplied=seasonDepositPolicy.get(0);
				}

			}
			app_logs.info(depositPolicyApplied);
			app_logs.info(depositAmount);
			String fourDigitCardNo=Utility.getCardNumberHidden(CardNumber);
			String paymentTypeIs="";
            if(PaymentType.equals("MC")) {
                paymentTypeIs=""+PaymentType+" "+fourDigitCardNo+" ("+CardExpDate+")";
            }else if(PaymentType.equals("Cash")){
                paymentTypeIs=PaymentType;
            }
            app_logs.info(paymentTypeIs);
            if(isAccountPolicyApplicable.equals("Yes")) {
                if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
                    verificationDepositWithPolicy(PaymentType,policyNames.get(0),depositAmount,paymentTypeIs);
                }
                else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
                    verificationDepositWithPolicy(PaymentType,selectedpolicyNames.get(policyTypes.get(0)),depositAmount,paymentTypeIs);
                }else {
                    verificationDepoistWithoutPolicy(PaymentType,CheckInDate,ratesConfig.getProperty("noPolicyText"));       
                }                     
            }else {
                if(Utility.isEmptyStringArrayList(seasonDepositPolicy)){
                    verificationDepositWithPolicy(PaymentType,reervationDepositPolicy,depositAmount,paymentTypeIs);
                }else {
                    verificationDepoistWithoutPolicy(PaymentType,Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")),ratesConfig.getProperty("noPolicyText"));
                }                                       
            }
            reservationPage.click_DeatilsTab(driver, test_steps);
			if(ActionOnReservation.equalsIgnoreCase("No Show")) {
				String rateNoShowPlanPolicy=getRateLevelPolicy.get("No Show");
				String seasonNoShowpolicy=getSessionLevelPolicy.get("No Show");
				
				if(seasonNoShowpolicy.equalsIgnoreCase("NA")){
					seasonNoShowpolicy="No Policy";
				}
				String policyToValidate = "No Policy",policyAttrDisplayed,policyAttrValueDisplayed,policyDesc = null,paymentsFromFolio = null;
				
				HashMap<String, String> highestAmountOfPolicyDetails = new HashMap<>();
				HashMap<String, ArrayList<String>> allPoliciesDetails = new HashMap<>();
				HashMap<String, String> allChargesFromFolio = new HashMap<>();
				ArrayList<String> noShowAmounts = new ArrayList<>();
				String noShowAmount="";
				
				if(!seasonNoShowpolicy.equalsIgnoreCase("No Policy")) {
					reservationPage.close_FirstOpenedReservation(driver, test_steps);
					navigation.Inventory(driver);
					navigation.clickPoliciesAfterRateGridTab(driver,test_steps);
					
					ArrayList<String> checkInDates = new ArrayList<>();
					ArrayList<String> checkOutDates = new ArrayList<>();
					
					try {
						test_steps.add("========== Capturing all policies attribute and attribute values ==========");
						allPoliciesDetails = policy.getAllPoliciesDetails(driver, test_steps, "No Show", seasonNoShowpolicy);
					}catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
							Utility.updateReport(e, "Failed to capture no show policy attributes and values", testName, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.updateReport(e, "Failed to capture no show policy attributes and values", testName, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					}
					reservationPage.navigateToReservationPage(driver);
					reservationPage.Search_ResNumber_And_Click(driver, reservation);
					int resCount = GuestFirstName.split("\\|").length;
					
					if ( !(Utility.validateInput(CheckInDate)) ) {
						for (int i = 0; i < resCount; i++) {
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
					}else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					try {
						test_steps.add("========== Evaluating which policy to be applied on reservation ==========");
						if (isAccountPolicyApplicable.equalsIgnoreCase("Yes")) {
							policyToValidate = policyName;
							policyAttrDisplayed = FeesGuestMustPay;
							policyAttrValueDisplayed = PercentOfFee;
							policyDesc = descWhileCreatePolicy.get(policyName).get(0);
						//	test_steps.add("Season level policy should be applied  for check-in date as <b>"+checkInDate+"</b>");
						}else if (!(allPoliciesDetails.get("Names").isEmpty())) {
							reservationPage.click_Folio(driver, test_steps);
							highestAmountOfPolicyDetails = reservationPage.getHighestAmountOfPolicy(driver, test_steps, allPoliciesDetails, 
									checkInDates, checkOutDates);
							policyToValidate = highestAmountOfPolicyDetails.get("Name");
							Utility.app_logs.info("policyToValidate : "+policyToValidate);
							policyAttrDisplayed = highestAmountOfPolicyDetails.get("Attribute");
							policyAttrValueDisplayed = highestAmountOfPolicyDetails.get("AttributeValue");
							policyDesc = highestAmountOfPolicyDetails.get("Description");
							paymentsFromFolio = highestAmountOfPolicyDetails.get("Payments");
							test_steps.add("========== Calculating no show amounts for different guests based on rate plan ==========");
							noShowAmounts = reservationPage.getNoShowPaymentsForAllGuests(driver, test_steps, checkInDates, checkOutDates, 
									policyAttrDisplayed, policyAttrValueDisplayed);
							noShowAmount = reservationPage.getTotalOfNoShowAmounts(noShowAmounts);
							
						}
					}catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
							Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation", testName, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation", testName, test_catagory, driver);
						} else {
							Assert.assertTrue(false);
						}
					}
				}
								
				test_steps.add("=================== Verify the no show policy in reservation ======================");
				app_logs.info("=================== Verify the no show policy in reservation ======================");
				Utility.app_logs.info(policyToValidate);
				Utility.app_logs.info(reervationNoShowPolicy);
				assertTrue(policyToValidate.equalsIgnoreCase(reervationNoShowPolicy),"No Show policy is not matched");
				test_steps.add("Verified no show policy is reservation policies : "+policyToValidate);
				app_logs.info("Verified no show policy is reservation policies : "+policyToValidate);
				
				test_steps.add("=================== No Show reservation ======================");
				app_logs.info("=================== No Show reservation ======================");

				reservationPage.makeReservationNoShowWithPaymentProcess(driver, test_steps, "No Show", paymentsFromFolio, noShowAmount);
				
				try {
					if (Utility.validateString(policyToValidate)) {
						test_steps.add("===================== Verifying associated No Show policy details at Policies "
								+ "And Disclaimers tab =====================");
						reservationPage.verifyNoShowPolicy(driver, test_steps, policyToValidate, policyDesc);				
					}else {
						test_steps.add("===================== Verifying associated No policy appllied for No Show at Policies "
								+ "And Disclaimers tab =====================");
						reservationPage.verifyNoShowPolicy(driver, test_steps, "No Policy", "");
					}
					test_steps.add("===================== Verifying No show amount at folio =====================");
					reservationPage.click_Folio(driver, test_steps);
					if (Utility.validateString(policyToValidate)) {
						reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
					}else {
						noShowAmounts.clear();
						reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmounts);
					}
					
				}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}else if(ActionOnReservation.equalsIgnoreCase("Check In")) {
				Double paymentAmt=0.00;
				String checkInAmount="";
				String paymentAmount="";
				String checkinPolicyApplied="";
				Double balance=0.00;
				HashMap<String, String> checkinPoliciesData= new HashMap<String, String>();
				ArrayList<HashMap<String, String>> getcheckinPoliciesData= new ArrayList<HashMap<String, String>>();
				String ratePlanCheckInPolicy=getRateLevelPolicy.get("Check-in");
				String seasonCheckInpolicy=getSessionLevelPolicy.get("Check-in");
				if(seasonCheckInpolicy.equalsIgnoreCase("NA")){
					seasonCheckInpolicy="No Policy";
				}
				app_logs.info(seasonCheckInpolicy);
				app_logs.info(reervationCheckInPolicy);
				test_steps.add("=================== Verify the check in policy in reservation ======================");
				app_logs.info("=================== Verify the check in policy in reservation ======================");
				assertTrue(seasonCheckInpolicy.equalsIgnoreCase(reervationCheckInPolicy),"Reservation check in policy is not matched");
				test_steps.add("Verified Reservation check in policy is reservation policies : "+reervationCheckInPolicy);
				app_logs.info("Verified Reservation check in policy is reservation policies : "+reervationCheckInPolicy);

				if(!seasonCheckInpolicy.equalsIgnoreCase("No Policy")) {
					navigation.inventory(driver);		
					navigation.policies(driver, test_steps);

					test_steps.add("=========Get  Data from Check-In Policy if policy exist on Season level=========");
					navigation.clickPoliciesAfterRateGridTab(driver,test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 5);       
					HashMap<String, String> valueHashmap= new HashMap<String, String>();
					if (CheckInDate.split("\\|").length>1) {
						for(int i=0;i<seasonCheckInPolicy.size();i++) {
							getcheckinPoliciesData.add(policy.getpoliciesData(driver, test_steps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(i)));
							valueHashmap.put(seasonCheckInPolicy.get(i), getcheckinPoliciesData.get(i).get("AttrValue"));                               
						}
						String  value=null;
						value=Utility.maxUsingCollectionsMax(valueHashmap);
						checkinPolicyApplied=Utility.getKeyOfValue(valueHashmap, value);
						checkinPoliciesData.put("AttrValue", value);
						app_logs.info(checkinPoliciesData);
					}else {
						checkinPoliciesData=policy.getpoliciesData(driver, test_steps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(0));
						checkinPolicyApplied=seasonCheckInPolicy.get(0);

					}
					app_logs.info(getcheckinPoliciesData);
					reservationPage.navigateToReservationPage(driver);
					reservationPage.Search_ResNumber_And_Click(driver, reservation);
					
					reservationPage.clickFolio(driver, test_steps);
					balance= Double.parseDouble(reservationPage.get_Balance(driver,test_steps));
					if (CheckInDate.split("\\|").length>1) {
						reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
						reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(1), Rooms.get(1));
						balance=balance+Double.parseDouble(reservationPage.get_Balance(driver,test_steps));
						reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
						reservationPage.clickFolioDetailOptionValue(driver, test_steps, RoomAbbri.get(0), Rooms.get(0));
					}
					app_logs.info(balance);
					if(isAccountPolicyApplicable.equals("Yes")){
						 paymentAmt=Double.parseDouble(reservationPage.get_PaymentsForAccountReservation(driver, test_steps));
				                app_logs.info(paymentAmt);
				                if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
				                    checkInAmount=reservationPage.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), policyAttrValueIs[1]);}
				                else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText")))) {
				                    checkInAmount=reservationPage.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), getAccountPoliciesData.get(1).get("AttrValue"));
				                }
						 

					}
					else if(isAccountPolicyApplicable.equals("No") && Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
						paymentAmt=Double.parseDouble(reservationPage.get_PaymentsForAccountReservation(driver, test_steps));
						app_logs.info(paymentAmt);
						checkInAmount=reservationPage.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), checkinPoliciesData.get("AttrValue"));
					}
					else if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
						paymentAmt=Double.parseDouble(reservationPage.get_Payments(driver, test_steps));
						app_logs.info(paymentAmt);
						checkInAmount=reservationPage.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), checkinPoliciesData.get("AttrValue"));


					}
					if(Utility.validateString(checkInAmount)) {
						app_logs.info(checkInAmount);        
						DecimalFormat df = new DecimalFormat("0.00");
						df.setMaximumFractionDigits(2);
						paymentAmt=paymentAmt+Double.valueOf(checkInAmount);
						app_logs.info(df.format(paymentAmt));
						paymentAmount=df.format(paymentAmt);
						app_logs.info(paymentAmount);
					}	
				}
				String checkInCardFormat="";
				if(PaymentType.equals("MC")) {
	                checkInCardFormat=""+PaymentType+"-"+fourDigitCardNo+" "+CardExpDate+"";
	            }else if(PaymentType.equals("Cash")) {
	                checkInCardFormat=PaymentType;
	            }
				
				reservationPage.clickCheckInButton(driver, test_steps);
	            Wait.waitUntilPageLoadNotCompleted(driver, 10);
	            reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));

	            if(isAccountPolicyApplicable.equals("Yes")) {
	                if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
	                    completeCheckInIfPolicyExist(balance,checkInAmount);
	                    test_steps.add("==================Verify Check-In Policy On Reservation==================");
	                    verificationCheckinWithPolicy(PaymentType,policyNames.get(1),checkInAmount,paymentAmount,checkInCardFormat);
	                }else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText"))))  {
	                    completeCheckInIfPolicyExist(balance,checkInAmount);
	                    verificationCheckinWithPolicy(PaymentType,selectedpolicyNames.get(policyTypes.get(1)),checkInAmount,paymentAmount,checkInCardFormat);
	                }                        
	                else {
	                    completeCheckInIfPolicyDoesntExist(balance);
	                    test_steps.add("==================Verify Check-In Policy On Reservation==================");
	                    verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
	                }
	            }else {
	                if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)) {
	                    completeCheckInIfPolicyExist(balance,checkInAmount);
	                    test_steps.add("==================Verify Check-In Policy On Reservation==================");
	                    verificationCheckinWithPolicy(PaymentType,checkinPolicyApplied,checkInAmount,paymentAmount,checkInCardFormat);
	                }else
	                {
	                    completeCheckInIfPolicyDoesntExist(balance);
	                    test_steps.add("==================Verify Check-In Policy On Reservation==================");
	                    verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
	                }
	            }    
			}else if(ActionOnReservation.equalsIgnoreCase("no change")) {
				reservationPage.click_Folio(driver, test_steps);
				HashMap<String, String> roomChargesFolio;
				if(ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				roomChargesFolio=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, CheckInDate, CheckOutDate);
				Utility.app_logs.info(roomChargesFolio);
				String RoomChagres=reservationPage.get_RoomCharge(driver, test_steps);
				reservationPage.click_DeatilsTab(driver, test_steps);
				if(!ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.ClickEditStayInfo(driver, test_steps);
					reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
				}else {
					adult="2";
					children="1";
					reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
				}
				Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
				reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);		
				reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				reservationPage.select_Rateplan(driver, test_steps, RatePlanName,PromoCode);
				reservationPage.VerifyFoliocalculateRate(driver, test_steps, "No Rate Change");
				reservationPage.clickOnFindRooms(driver, test_steps);	
								
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0&& isRoomClassAvailableChange) {
					
					/*String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRates,
							maxAdult.get(ReservationRoomClasses), maxChild.get(ReservationRoomClasses), getExAdult, getExChild,
							capacityChild.get(ReservationRoomClasses), capacityAdult.get(ReservationRoomClasses), adult, children,
							test_steps, ratesConfig.getProperty("noCombination"), ReservationRoomClasses, dates,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info(rateIs);*/
					String minStayColorChange = "";
					if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange && isSeasonExistChange) {

						String noCheckinColorChange = "";
						if (checkInColorChnage.equalsIgnoreCase("Red")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColorChange = "";
						if (checkOutColorChange.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
								minStayRuleValueChange);

						reservationPage.clickSaveButton(driver);
						reservationPage.verifyPopupChangeInCost1(driver, test_steps);
						reservationPage.click_Folio(driver, test_steps);
						if(ReservationType.equalsIgnoreCase("MRB")) {
							reservationPage.selectMRBLastFolio(driver, test_steps);
						}
						HashMap<String, String> roomChargesFolio1=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						String RoomChagres1=reservationPage.get_RoomCharge(driver, test_steps);
						reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						Utility.app_logs.info(roomCharges);	
						try {
							assertTrue(RoomChagres1.equalsIgnoreCase(RoomChagres), "Room Chares are not matched");
							test_steps.add("Verified room charges are not changes even after change stay details in the reservation");
							app_logs.info("Verified room charges are not changes even after change stay details in the reservation");
						}catch(Error e) {
							test_steps.add(e.toString());
						}
					}else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}	
			}else if(ActionOnReservation.equalsIgnoreCase("recalculate")) {

				reservationPage.click_Folio(driver, test_steps);
				if(ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				HashMap<String, String> roomChargesFolio=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, CheckInDate, CheckOutDate);
				Utility.app_logs.info(roomChargesFolio);
				String RoomChagres=reservationPage.get_RoomCharge(driver, test_steps);
				if(!ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.ClickEditStayInfo(driver, test_steps);
					reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
				}else {
					adult="2";
					children="1";
					reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
				}
				reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
				Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
				reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);		
				reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				reservationPage.select_Rateplan(driver, test_steps, RatePlanName,PromoCode);
				reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
				reservationPage.clickOnFindRooms(driver, test_steps);	
								
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0&& isRoomClassAvailableChange) {
					String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRatesChange,
							maxAdultChange.get(ReservationChangeRoomClass), maxChildChange.get(ReservationChangeRoomClass), getExAdultChange, getExChildChange,
							capacityChildChange.get(ReservationChangeRoomClass), capacityAdultChange.get(ReservationChangeRoomClass), adult, children,
							test_steps, ratesConfig.getProperty("noCombination"), ReservationChangeRoomClass, datesChnage,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info("rateIs : "+rateIs);
					String minStayColorChange = "";
					if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange && isSeasonExistChange) {

						if (isMinStayRuleChnage) {
							if (!isMinStayRuleBrokenPopComeOrNotChange) {
								minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationChangeRoomClass, minStayRuleValueChange);
								Utility.app_logs.info("minStayColor : " + minStayColorChange);
								try {
									assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationChangeRoomClass, minStayRuleValueChange);
								try {
									assertTrue(minStayColorChange.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColorChange = "";
						if (checkInColorChnage.equalsIgnoreCase("Red")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColorChange = "";
						if (checkOutColorChange.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
								minStayRuleValueChange);

						reservationPage.clickSaveButton(driver);
						reservationPage.verifyPopupChangeInCost1(driver, test_steps);
						reservationPage.click_Folio(driver, test_steps);
						if(ReservationType.equalsIgnoreCase("MRB")) {
							reservationPage.selectMRBLastFolio(driver, test_steps);
						}
						HashMap<String, String> roomChargesFolio1=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						String RoomChagres1=reservationPage.get_RoomCharge(driver, test_steps);
						reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						Utility.app_logs.info("roomChargesFolio1 : "+roomChargesFolio1);	
						Utility.app_logs.info("roomChargesFolio : "+roomChargesFolio);
					}else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}	
			
			}else if(ActionOnReservation.equalsIgnoreCase("Changed Dates")) {

				reservationPage.click_Folio(driver, test_steps);
				if(ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.selectMRBLastFolio(driver, test_steps);
				}
				HashMap<String, String> roomChargesFolio=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, CheckInDate, CheckOutDate);
				Utility.app_logs.info(roomChargesFolio);
				String RoomChagres=reservationPage.get_RoomCharge(driver, test_steps);
				reservationPage.click_DeatilsTab(driver, test_steps);
				if(!ReservationType.equalsIgnoreCase("MRB")) {
					reservationPage.ClickEditStayInfo(driver, test_steps);
					reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);
				}else {
					adult="2";
					children="1";
					reservationPage.ClickEditStayInfoMRBLastLineItem(driver, test_steps);
				}
				Wait.waitForElementToBeVisibile(By.xpath("//div[contains(@id,'rcDtails')]"), driver);
				reservationPage.select_CheckInDate(driver, test_steps, ChangeResStartDate);		
				reservationPage.select_CheckoutDate(driver, test_steps, ChangeResEndDate);
				reservationPage.enter_Adults(driver, test_steps, adult);
				reservationPage.enter_Children(driver, test_steps, children);
				reservationPage.select_Rateplan(driver, test_steps, RatePlanName,PromoCode);
				reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Change only for added/removed dates");
				reservationPage.clickOnFindRooms(driver, test_steps);	
								
				if (driver.findElements(By.xpath("(//span[text()='" + ReservationRoomClasses + "'])[2]")).size() > 0&& isRoomClassAvailableChange) {
					/*String rateIs = reservationPage.calculateRatesAsPerAdultsAndChildCapacity(driver, getRatesChange,
							maxAdultChange.get(ReservationChangeRoomClass), maxChildChange.get(ReservationChangeRoomClass), getExAdultChange, getExChildChange,
							capacityChildChange.get(ReservationChangeRoomClass), capacityAdultChange.get(ReservationChangeRoomClass), adult, children,
							test_steps, ratesConfig.getProperty("noCombination"), ReservationChangeRoomClass, datesChnage,
							ratesConfig.getProperty("defaultDateFormat"));
					Utility.app_logs.info("rateIs : "+rateIs);*/
					String minStayColorChange = "";
					if (restricrionsLengthOfStayChange && restricrionsBookingWindowChange && isSeasonExistChange && isSeasonExistChange) {

						if (isMinStayRuleChnage) {
							if (!isMinStayRuleBrokenPopComeOrNotChange) {
								minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationChangeRoomClass, minStayRuleValueChange);
								Utility.app_logs.info("minStayColor : " + minStayColorChange);
								try {
									assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
											"Red color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the red color lable for min stay rule");
									test_steps.add("Succesfully veried the red color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							} else {
								minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
										ReservationChangeRoomClass, minStayRuleValueChange);
								try {
									assertTrue(minStayColorChange.equalsIgnoreCase("green"),
											"green color lable for minstay rule is not found");
									app_logs.info("Succesfully veried the green color lable for min stay rule");
									test_steps.add("Succesfully veried the green color lable for min stay rule");
								} catch (Error e) {
									test_steps.add(e.toString());
								} catch (Exception e) {
									test_steps.add(e.toString());
								}
							}
						}

						String noCheckinColorChange = "";
						if (checkInColorChnage.equalsIgnoreCase("Red")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								Utility.app_logs.info("noCheckinColor");
								assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check in rule");
								test_steps.add("Succesfully verified the red color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkInColorChnage.equalsIgnoreCase("Green")) {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
										"green color lable for no check in rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check in rule");
								test_steps.add("Succesfully verified the green color lable for no check in rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
										"no check in rule label is displayed");
								app_logs.info("Succesfully verified the no check in rule label not displayed");
								test_steps.add("Succesfully verified the no check in rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}

						String noCheckoutColorChange = "";
						if (checkOutColorChange.equalsIgnoreCase("Red")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
										"red color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the red color lable for no check out rule");
								test_steps.add("Succesfully verified the red color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
										"green color lable for no check out rule is not found");
								app_logs.info("Succesfully verified the green color lable for no check out rule");
								test_steps.add("Succesfully verified the green color lable for no check out rule");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						} else {
							try {
								noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
										ReservationChangeRoomClass);
								assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
										"no check out rule label is displayed");
								app_logs.info("Succesfully verified the no check out rule label not displayed");
								test_steps.add("Succesfully verified the no check out rule label not displayed");
							} catch (Error e) {
								test_steps.add(e.toString());
							} catch (Exception e) {
								test_steps.add(e.toString());
							}
						}
						reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
								ReservationChangeRoomClass, "Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
								minStayRuleValueChange);
						reservationPage.clickSaveButton(driver);
						reservationPage.verifyPopupChangeInCost1(driver, test_steps);
						reservationPage.click_Folio(driver, test_steps);
						if(ReservationType.equalsIgnoreCase("MRB")) {
							reservationPage.selectMRBLastFolio(driver, test_steps);
						}
						HashMap<String, String> roomChargesFolio1=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						reservationPage.checkFolioLineItemsWithDates(driver, test_steps, ChangeResStartDate, ChangeResEndDate);
						String RoomChagres1=reservationPage.get_RoomCharge(driver, test_steps);
						Utility.app_logs.info("roomChargesFolio1 : "+roomChargesFolio1);	
						Utility.app_logs.info("roomChargesFolio : "+roomChargesFolio);
					}else {
						try {
							reservationPage.verifyNoRateCombinationMsg(driver, test_steps);
						} catch (Error e) {
							test_steps.add(e.toString());
						} catch (Exception e) {
							test_steps.add(e.toString());
						}
					}
				}else {
					test_steps.add("Room Class is not displayed in the search : " + ReservationRoomClasses);
					app_logs.info("Room Class is not displayed in the search : " + ReservationRoomClasses);
				}	
			}else if(ActionOnReservation.equalsIgnoreCase("Cancellation")) {
				
				
				
				String cancellationPolicy = "";
				String cancellationDelim = ",";
				if (isVerifyPolicies) {
				
					boolean delimFlag = false;
					
					 cancellationPolicy = getSessionLevelPolicy.get("Cancellation"); 
					
					 if(isAccountPolicyApplicable.equals("Yes")) {
						//to-do
					 }
					 
					if (!cancellationPolicy.equalsIgnoreCase("NA")) {
						
						 test_steps.add("==================VERIFY CANCELLATION POLICY ON RESERVATION==================");
						
						reservationPage.close_FirstOpenedReservation(driver, test_steps);
						navigation.inventory(driver);		
						navigation.policies(driver, test_steps);

						PolicyTypes += "Cancellation";
						PolicyNames += cancellationPolicy;
						policy.clickEditIcon(driver, "Cancellation", cancellationPolicy, test_steps);
						int noOfClauses = policy.getNoOfClauses(driver);
						TypeOfFees += policy.getSelectedTypeOfFees(driver, cancellationDelim, noOfClauses, test_steps);
						GuestsWillIncurAFee += policy.getGuestWillIncurAFee(driver, cancellationDelim, noOfClauses,
								test_steps);
						ChargesType += policy.getSelectedChargeType(driver, cancellationDelim, TypeOfFees, test_steps);
						NoOfDays += policy.getNoOfDays(driver, cancellationDelim, noOfClauses, test_steps);
						CancelWithInType += policy.getSelectedCancelType(driver, cancellationDelim, noOfClauses,
								test_steps);
						policy.closePolicyPopup(driver, test_steps);
						app_logs.info("Policies Types : " + PolicyTypes);
						app_logs.info("Type Of Fees : " + TypeOfFees);
						app_logs.info("Guests Will Incur A fee : " + GuestsWillIncurAFee);
						app_logs.info("Charge Type : " + ChargesType);
						app_logs.info("No Of Days : " + NoOfDays);
						app_logs.info("Cancel With In Type : " + CancelWithInType);
						// converting to hashmap
						names = policy.getPolicyNames(delim, PolicyTypes, PolicyNames);
						policyClauses = policy.getPolicyClauses(delim, PolicyTypes, TypeOfFees);
						guestsWillIncurAFee = policy.getPolicyClauses(delim, PolicyTypes, GuestsWillIncurAFee);
						if(TypeOfFees.toLowerCase().contains("percent of stay")) {
							chargesType = policy.getPolicyClauses(delim, PolicyTypes, ChargesType);
						}else {
							chargesType.put("Cancellation", "");
						}
						cancelWithInType = policy.getPolicyClauses(delim, PolicyTypes, CancelWithInType);
						noOfDays = policy.getPolicyClauses(delim, PolicyTypes, NoOfDays);
						delimFlag = true;
					} else {
						delimFlag = false;
					}

					app_logs.info("Type Of Fees : " + policyClauses);
					app_logs.info("Guests Will Incur A fee : " + guestsWillIncurAFee);
					app_logs.info("Charge Type : " + chargesType);
					app_logs.info("No Of Days : " + noOfDays);
					app_logs.info("Cancel With In Type : " + cancelWithInType);
				}
				
				try {
					navigation.cpReservation_Backward(driver);
				} catch (Exception e) {
					Actions actions = new Actions(driver);

					actions.sendKeys(Keys.ENTER);
				}
				reservationPage.Search_ResNumber_And_Click(driver, reservation);
				
//				reservationSearch.multipleSearchReservationNumber(driver, allReservation);
//				
//				getTestSteps.clear();
//				getTestSteps = reservationPage.clickOnGuestNameInSearchReaservation(driver, reservationNumber1);
//				testSteps.addAll(getTestSteps);
				
//				String statydiff = Utility.differenceBetweenDates(CheckInDate, CheckOutDate);

				String foundRoomCharge = reservationPage.getRoomChargesInTripSummary(driver);
				foundRoomCharge = Utility.removeDollarBracketsAndSpaces(foundRoomCharge);
				String foundTotalCharge = reservationPage.getBalanceInTripSummary(driver);
				foundTotalCharge = Utility.removeDollarBracketsAndSpaces(foundTotalCharge);
				//Float calRoomCharge = (Float.parseFloat(foundRoomCharge) / Integer.parseInt(statydiff));

				reservationPage.click_Folio(driver, test_steps);
				reservationPage.includeTaxesinLineItems(driver, test_steps, false);

				HashMap<String, ArrayList<String>> roomClassWiseDates = reservationPage
						.getRoomClassFolioWiseRoomChargeDates(driver, test_steps, reservationRoomClassesList,
								RoomAbbri, "Yes", Rooms, "dd/MM/yyyy");
				HashMap<String, HashMap<String, String>> roomClassWiseRoomCharge = reservationPage
						.getRoomClassWiseFolioRoomChargeBasedOnDates(driver, reservationRoomClassesList,
								roomClassWiseDates, "dd/MM/yyyy", RoomAbbri, "Yes", Rooms);
				
				app_logs.info(roomClassWiseDates);
				app_logs.info(roomClassWiseRoomCharge);
				
				if (isVerifyPolicies) {
					if (!cancellationPolicy.equalsIgnoreCase("NA")) {
						foundBestPolicyAmount = policy.findBestCancellationPolicy(",",names.get("Cancellation"),
								policyClauses.get("Cancellation"), guestsWillIncurAFee.get("Cancellation"),
								chargesType.get("Cancellation"), cancelWithInType.get("Cancellation"),
								noOfDays.get("Cancellation"),
								foundRoomCharge + "", foundTotalCharge,
								ReservationRoomClasses, CheckInDate, CheckOutDate, roomClassWiseDates, roomClassWiseRoomCharge
								);

						app_logs.info("Best Clause : " + foundBestPolicyAmount);
						app_logs.info("Clauses : " + policyClauses.get("Cancellation"));
						app_logs.info("Guest will incur a fee : " + guestsWillIncurAFee.get("Cancellation"));
						HashMap<String, String> cancellationClauseValues = reservationPage
								.getCancellationClauseValue(",", policyClauses.get("Cancellation"),
										guestsWillIncurAFee.get("Cancellation"));
						app_logs.info(cancellationClauseValues);
						reservationPage.verifyAssociatedCancellationPolicy(driver, ",", PolicyTypes, PolicyNames,
								Utility.convertTokenToArrayList(policyClauses.get("Cancellation"), ","),
								cancellationClauseValues, test_steps);
					}
				}

				reservationPage.click_DeatilsTab(driver, test_steps);

				String tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver,
						test_steps);
				String paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);

				boolean isCancellationPolicyApplicable = policy.isCancellationPolicyApplicable(foundBestPolicyAmount.getNoOfDays(), Utility.convertTokenToArrayList(CheckInDate, delim).get(0));
				
				reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", test_steps);
				
				if(isCancellationPolicyApplicable) {
					reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, names.get("Cancellation"),
							"Cancellation");
					reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");

					String[] calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount, foundBestPolicyAmount.getCalculatedAmount());
					reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", calCancelPolicy[1],
							calCancelPolicy[0]);
					reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);

					if(!calCancelPolicy[0].equalsIgnoreCase("alreadyPaid")) {
					reservationPage.verifyCancellationSuccessuful(driver, test_steps, "0.00", calCancelPolicy[1],
							calCancelPolicy[0], "Processed");
					reservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
					}
					reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");

					tripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, test_steps);
					assertEquals(tripTotal, foundBestPolicyAmount.getCalculatedAmount(), "Failed to Verify Trip Total After Reservation");
					test_steps.add("Successfully Verified Trip Total After Reservation : " + tripTotal);

					paidAmount = reservationPage.get_TripSummaryPaidAmount(driver, test_steps);
					assertEquals(paidAmount, tripTotal, "Failed to Verify Paid Amount After Reservation");
					test_steps.add("Successfully Verified Paid Amount After Reservation : " + tripTotal);
				} else {
					try {
						reservationPage.verifyReservationPopWindowPolicyName(driver, test_steps, "",
								"Cancellation");
					}catch (Exception e) {
						// TODO: handle exception
					}
					
					reservationPage.addCancelationReson(driver, test_steps, "Cancellation", "This field is required.");
					
					if(Float.parseFloat(paidAmount)==Float.parseFloat("0")) {
						reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
						reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");
					}else {
						String[] calCancelPolicy = reservationPage.calculateCancellationFee(paidAmount, "0");
						reservationPage.verifyAppliedPolicyAmount(driver, "Cancellation", calCancelPolicy[1],
								calCancelPolicy[0]);
						reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
						reservationPage.verifyStatusAfterReservation(driver, "CANCELLED");
					}
				}
			}
			
				
		}catch (Exception e) {
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
		
		}
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyRateplanInReservation", excel);
	}
}

