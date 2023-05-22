/*
 * Created By Riddhi
 * 907931|907863|907941|907861|907867|907868|907844|907982|907980|907869|908010
 */
package com.innroad.inncenter.tests;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.cluster.ClusterState.Custom;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;


public class VerifyDepositPolicyScenariosQuoteResV2 extends TestCore
{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Folio oldFolioObj = new Folio();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account accObj = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 resV2 = new ReservationV2();
	ReservationV2Search resSearch = new ReservationV2Search();
	Properties properties = new Properties();
	RoomClass roomClass = new RoomClass();
	Reports report = new Reports();
	Rate rate1 = new Rate();
	Policies policies = new Policies();
	MerchantServices msObj = new MerchantServices();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	CPReservationPage resV1 = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
	HashMap<String, String> policyNames = new HashMap<>();
	HashMap<String, String> policyTextList = new HashMap<>();
	//HashMap<String, String> policyFeeTypeList = new HashMap<>();
	HashMap<String, String> policyFeeList = new HashMap<>();
	
	ArrayList<String> policyFeeTypeList = new ArrayList<String>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	ArrayList<String> policiesNames = new ArrayList<String>();
	ArrayList<String> policiesAttrsValue = new ArrayList<String>();
	ArrayList<String> policiesTypes = new ArrayList<String>();
	ArrayList<String> policiesAre = new ArrayList<String>();
	ArrayList<String> ratePlanNames = new ArrayList<String>();
	ArrayList<String> roomClassesNames = new ArrayList<String>();
	ArrayList<String> roomClassOneRoomNo= new ArrayList<String>();
	ArrayList<String> roomClassTwoRoomNo= new ArrayList<String>();
	ArrayList<String> roomClassfinalRoomNos= new ArrayList<String>();
	ArrayList<String> policyValues = new ArrayList<String>();
	ArrayList<String> roomsCharge = new ArrayList<>();
	ArrayList<String> rates = new ArrayList<String>();
	ArrayList<String> rateplans = new ArrayList<String>();
	ArrayList<String> paymentTypes = new ArrayList<String>();
	String HAAccName  ="";
	String GCAccName ="";
	
	String checkInDate="", checkOutDate = "", guestFirstName="", guestLastName="", seasonStartDate="",seasonEndDate="",policyDesc ="";

	String  propertyName = null, roomClassNames = null, roomClassAbbs = null,
			roomClassNameWithoutNum = null, roomClassAvvWithoutNum = null, rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null, balance = null,policyname=null,policyNameWithoutNum=null,
			tripTaxes = null, tripTotal = null, depositAmount = null, date=null,expPolicyFee="", roomChargeAmount="", gcNumber =null;
	String source = "innCenter";
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() 
	{
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if(!Utility.isExecutable(test_name, excelRiddhi)) throw new
			SkipException("Skiping the test - " + test_name);
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyDPScenariosQuoteResV2", excelRiddhi);
	}
	
	private void getMapData(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			test_steps.add("Date : " + mentry.getKey() + " & Value : " + mentry.getValue().toString());
		}
	}
	private void handelingCatchException(Exception e, String desc, String category, String module) {
		if (Utility.reTry.get(test_name) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	private void handelingCatchError(Error e, String desc, String category, String module) {
		if (Utility.reTry.get(test_name) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			Utility.updateReport(e, desc, category, module, driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	@Test(dataProvider = "getData")
	public void verifyDepositPolicyScenariosQuoteResV2(String TestCaseId, String TestCaseName, 
			String paymentGateway, String accountNumber, String accountDesc, String accountStatus, 
			String isCardPresent, String isCardNotPresent, String isEcommerce, String isRequireCVVForAll,
			String mode, String accountID, String subAccountId, String merchantPin,
			String storeId, String url, String tokenId,
			String roomClassName, String roomClassAbb, String maxAdults, String maxPersons, String roomQuantity, 
			String policyType,String PolicyFeeType, String policyName,String polictAttr, String policyValue,
			String chargesTypes,String ratePlanName,String rate,
			String paymentType, String cardNumber, String nameOnCard, String expiryDate,
			String marketSegment, String referral, String paymentAmount, String paymentMethod,
			String category, String perUnit, String quantity, String overrideDepositDueAmount) throws Exception 
	{
		Utility.initializeTestCase(TestCaseId, caseId, statusCode, comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		// String test_name = test_name;
	
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		ArrayList<String> roomClassNames = Utility.splitInputData(roomClassName);
		ArrayList<String> roomClassAbbs = Utility.splitInputData(roomClassAbb);
		ArrayList<String> rooms= new ArrayList<String> ();
		ratePlanNames = Utility.splitInputData(ratePlanName);		
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			} else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(excelRiddhi, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
			propertyName = properties.getProperty(driver, test_steps);
		} catch (Exception e) {
			handelingCatchException(e, "Failed to login", test_name, "Login");	}		

		 catch (Error e) {
				handelingCatchError(e, "Failed to login", test_name, "Login");	}
	
		String channelName = "innCenter";
		System.out.println("channelName : " + channelName);
		System.out.println(System.getProperty("user.dir"));
		HashMap<String, String> ratePlanData = null;
		HashMap<String, String> roomClassData = null;
				
		try 
		{
			roomClassData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNewRoomClass");
			ratePlanData = Utility.getExcelData(
					System.getProperty("user.dir") + "\\test-data\\ResV2_Riddhi.xlsx", "CreateNightlyRatePlanV2");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Get Room Class Fields from the excel sheet via roomclassData, and assign them to local variables
		String roomClass = roomClassData.get("roomClassName");
		String roomClassAB = roomClassData.get("roomClassAB");
		String policy = roomClassData.get("policy");
		String sortOrder = roomClassData.get("sortOrder");
		String maxAdult = roomClassData.get("maxAdults");
		String maxPerson = roomClassData.get("maxPerson");
		String details = roomClassData.get("details");
		String roomQuant = roomClassData.get("roomQuant");
		String roomName = roomClassData.get("roomName");
		String roomSortNo = roomClassData.get("roomSortNo");
		String statinId = roomClassData.get("statinId");
		String zone = roomClassData.get("zone");
		
		//Get Rate Plan Fields From excel sheet via Rateplandata arraylist and assign them to local variables
		//String ratePlanName = ratePlanData.get("ratePlanName");
		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		String RoomClasses = roomClassName;
		String isRatePlanRestrictionReq = ratePlanData.get("roomClassName");
		String RestrictionType = ratePlanData.get("RestrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String MinNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String MaxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String MoreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String WithInDaysCount = ratePlanData.get("WithInDaysCount");
		String PromoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String PoliciesType = ratePlanData.get("PoliciesType");
		String PoliciesName = ratePlanData.get("PoliciesName");
		String SeasonName = ratePlanData.get("SeasonName");
		String SeasonStartDate = Utility.getCurrentDate("dd/MM/yyyy");// ratePlanData.get("SeasonStartDate");
		String SeasonEndDate = Utility.getCustomDate(SeasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 60);// ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight = ratePlanData.get("RatePerNight");
		String MaxAdults = ratePlanData.get("MaxAdults");
		String MaxPerson = ratePlanData.get("MaxPerson");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType = ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String MaxPersons = ratePlanData.get("MaxPersons");
		ArrayList<String> availableReferrals = new ArrayList<String>();
		ArrayList<String> availableMarketSegments = new ArrayList<String>();
		paymentTypes = Utility.splitInputData(paymentType);
		// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			app_logs.info("Navigated to Room Class");		
		
			/*for (int i = 0; i < roomClassNames.size(); i++) {
				if( !newRoomClass.searchRoomClassV2(driver, roomClassNames.get(i)) )
				{
					newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames.get(i), roomClassAbbs.get(i), maxAdults,
						maxPersons, roomQuantity);
						newRoomClass.closeRoomClassTabV2(driver, roomClassNames.get(i));
					roomClassesNames.add(roomClassNames.get(i));
					roomClassfinalRoomNos.addAll(rooms);
					test_steps.add("Room Class Created:" + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
					app_logs.info("Room Class Created: " + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
				}
				else
				{
					app_logs.info("Room Class Exists: " + roomClassNames.get(i) + " Abbreviation : " + roomClassAbbs.get(i));
				}					
			}*/
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", test_name,
					test_description, test_catagory, test_steps);		
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", test_name, test_description,
					test_catagory, test_steps);
		}
		//Create Deposit Policy
		try
		{
			policiesAre = Utility.splitInputData(policyName);			
			policiesTypes = Utility.splitInputData(policyType);
			policyFeeTypeList = Utility.splitInputData(PolicyFeeType);
			policyValues = Utility.splitInputData(policyValue);
			
			navigation.inventoryFromRoomClass(driver, test_steps);
			navigation.policies(driver, test_steps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
						
			for (int i = 0; i < policiesAre.size(); i++) 
			{
				test_steps.add("<b>======Start Creating Policy ======</b>");			
				app_logs.info("policiesTypes "+i+"-"+policiesTypes.get(i));
				app_logs.info("policiesAre "+i+"-"+policiesAre.get(i));
				app_logs.info("policyFeeTypeList "+i+"-"+policyFeeTypeList.get(i));
				app_logs.info("policyValues "+i+"-"+policyValues.get(i));
				
				boolean isDepositPolicyNotExist = policies.VerifyPolicyNotExist(driver, test_steps, policiesAre.get(i));
				app_logs.info("isDepositPolicyNotExist " + isDepositPolicyNotExist);
				
				if( !isDepositPolicyNotExist )	{
					//test_steps.add("Delete Existing Policy");
					//app_logs.info("Delete Existing Policy");	
					polictiesNames = policies.getAllPoliciesDetails(driver, test_steps, policiesTypes.get(i), policiesAre.get(i));
				}
				else	
				{
					polictiesNames = policies.createPolicies(driver, test_steps, "", "", policiesTypes.get(i), "", policiesAre.get(i),
							"", "",policyFeeTypeList.get(i), policyValues.get(i), chargesTypes, "", "", "No", "");
					test_steps.add("<b>======Policy Created : ======</b>"+polictiesNames);
					app_logs.info("<b>====== Policy Created :========</b>"+polictiesNames);					
				}
				//policyDesc = polictiesNames.get("Descriptions").get(i);
				app_logs.info(polictiesNames);
				//app_logs.info(policiesNames);
				//app_logs.info(policiesAttrsValue);
				app_logs.info(policyDesc);
							
				String percentageOfPolicy = policyValues.get(i);
				policiesNames.add(policiesAre.get(i));
				policiesAttrsValue.add(percentageOfPolicy);	
				policiesTypes.add(policiesTypes.get(i));
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", test_name, test_description,
					test_catagory, test_steps);		
		}
		//Create Payment Gateway
		/*try
		{
			String payGatewayAccountName = paymentGateways.get(1) + "_Gateway";
		  	navigation.setup(driver);
		  	msObj.navigatetoMerchantServices(driver, test_steps);
		  	test_steps.add("Creating Payment Gateway : " + payGatewayAccountName);
		  	app_logs.info("Creating Payment Gateway : " + payGatewayAccountName);
		  	msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
			msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, accountNumber, accountDesc, accountStatus, 
		  			isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, paymentGateways.get(0),
		  			mode, accountIDs.get(0), subAccountId, merchantPins.get(0),
		  			"", "", "", "", "", "", "", "", "", propertyName);
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Failed to Create MP Gateway", "Failed to Create MP Gateway", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Failed to Create MP Gateway", "Failed to Create MP Gateway", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}*/
		String salutation = "Ms.";	
		guestFirstName = "DP_FixedPayPolicy" + Utility.threeDigitgenerateRandomString();
		guestLastName = "_S";
		String phoneNumber = "9876543444";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "Corporate/Member Accounts";
		String address1 = "Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		boolean isGuesProfile = false;
		int additionalGuests = 0;
		String roomNumber = "";
		boolean quote = false;
		String noteType = "";
		String noteSubject = "";
		String noteDescription = "";
		String taskCategory = "";
		String taskType = "";
		String taskDetails = "";
		String taskRemarks = "";
		String taskDueOn = "";
		String taskAssignee = "";
		String taskStatus = "";
		String accountName = "";
		String accountFirstName = "";
		String accountLastName = "";
		boolean isTaxExempt = false;
		String taxExemptID = "TAX_IT_0010";
		String adult = "1";
		String children = "1";
		HashMap<String, String> data = null;
		String reservationNo="";
		FolioNew folioNewobj = new FolioNew();		
		//HashMap<String, String> getRoomCharge = new HashMap<String, String>();		
		String GCAccNumber="";
		String paymentAmounts[] = paymentAmount.split("\\|");
		String paymentMethods[] = paymentMethod.split("\\|");
		
		for(int i=0;i<paymentMethods.length;i++)
		{
			try
			{
				if(paymentMethods[i].contains("GC")||paymentMethods[i].contains("Gift Card")||paymentMethods[i].contains("Gift Certificate"))
				{
					paymentMethods[i] = "Gift Certificate";
					String AccType = "Gift Certificate";
					GCAccName  = "GCAcc_Automated2";
					if (!accObj.checkForAccountExistsAndOpen(driver, test_steps, GCAccName, AccType, true)) 
					{
						navigation.ReservationV2_Backward(driver);
						test_steps.add("Navigate to Accounts");
						app_logs.info("Navigate to Accounts");							
						navigation.Accounts(driver);
						//navigation.accounts(driver);
						test_steps.add("Click on New Account Button");
						app_logs.info("Click on New Account Button");
						accObj.ClickNewAccountbutton(driver, AccType);
						accObj.AccountDetails(driver, AccType, GCAccName);
						Wait.wait5Second();
						test_steps.add("Creating Gift Certificate Account");
						app_logs.info("Creating Gift Certificate Account");
						accObj.Save(driver);
						test_steps.add("Gift Certificate Created Successfully");
						app_logs.info("Gift Certificate Created Successfully");
					}						
					GCAccNumber = accObj.getAccountNum(driver);
					test_steps.add("GC Account Number : "+GCAccNumber);
					app_logs.info("GC Account Number : "+GCAccNumber);
					app_logs.info("Navigate to GC Account Folio.....");
					
					accObj.ClickFolio(driver);
					accObj.addLineitem1(driver, "", "Gift Certificate", "1000", test_steps);
					accObj.Commit(driver);
					accObj.Save(driver, test, test_steps);
					Wait.wait5Second();
					
					gcNumber = accObj.getGCNumberFromFolio(driver);
					accObj.closeAccount(driver);
					test_steps.add("Gift Certificate Closed");
					app_logs.info("Gift Certificate Closed");
				}				
			}
			catch(Exception e)	{
				Utility.catchException(driver, e, "Failed to Create GC Account", "Failed to Create GC Account", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			}
			catch(Error e)	{
				Utility.catchError(driver, e, "Failed to Create GC Account", "Failed to Create GC Account", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			}
			try
			{
				if(paymentMethods[i].contains("HA")||paymentMethods[i].contains("House Account"))
				{
					paymentMethods[i] = "House Account";
					String AccType = "House Account";
					HAAccName = "HA_"+Utility.generateRandomNumber();
					if (!accObj.checkForAccountExistsAndOpen(driver, test_steps, HAAccName, AccType, true)) 
					{
						driver.navigate().refresh();
						test_steps.add("Click on New Account Button");
						app_logs.info("Click on New Account Button");
						accObj.ClickNewAccountbutton(driver, AccType);
						accObj.AccountDetails(driver, AccType, HAAccName);
						Wait.wait5Second();
						test_steps.add("Creating House Account");
						app_logs.info("Creating House Account");
						accObj.Save(driver);
						test_steps.add("House Account Created Successfully");
						app_logs.info("House Account Created Successfully");
					}
					String HAAccountNumber = accObj.getAccountNum(driver);						
					test_steps.add("House Account Number : "+HAAccountNumber);
					app_logs.info("House Account Number : "+HAAccountNumber);
					accObj.navigateFolio(driver);
					accObj.click_Pay(driver, test, test_steps);
					accObj.HouseAccount_CardPayment(driver, paymentTypes.get(i), paymentAmounts[i], nameOnCard, 
							cardNumber, expiryDate, "", "", "", "", "", "");
				}
			}
			catch(Exception e)	{
				Utility.catchException(driver, e, "Failed to Create HA Account", "Failed to Create HA Account & Verify CC Payment in House Account", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			}
			catch(Error e)	{
				Utility.catchError(driver, e, "Failed to Create HA Account", "Failed to Create HA Account & Verify CC Payment in House Account", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);
			}
		}		
		//Create MRB Reservation - 	Verify Deposit Policy in Folio & History Logs, for MRB Reservation - 907868
		try
		{			
			String CheckInDateMRB = Utility.getCurrentDate("dd/MM/yyyy")+"|"+Utility.getCurrentDate("dd/MM/yyyy");
			String CheckOutDateMRB = Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2)+"|"+Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 2);
			String RatePlanName4MRB = ratePlanNames.get(1) + "|" + ratePlanNames.get(1);
			String roomClassName4MRB = roomClassNames.get(0) +"|" + roomClassNames.get(0);
			guestFirstName = "MRB"+guestFirstName + "|" + guestFirstName;
			adult = adult + "|" + adult;
			children = children + "|"+children;
			//navigation.ReservationV2_Backward(driver);
			//navigation.reservation(driver);
			navigation.navigateToReservations(driver);
			data = resV2.createReservationwithACF(driver, test_steps,
					  "From Reservations page", CheckInDateMRB, CheckOutDateMRB, adult, children,
					  RatePlanName4MRB, "", roomClassName4MRB, roomClassAbb, salutation,
					  guestFirstName, guestLastName, phoneNumber, altenativePhone, email, address1,
					  address2, address3, city, country, state, postalCode, isGuesProfile,
					  marketSegment, referral, "Cash", "", "", "",
					  additionalGuests, roomNumber, quote, noteType, noteSubject, noteDescription,
					  taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee,
					  taskStatus, accountName, accountType, accountFirstName, accountLastName,
					  isTaxExempt, taxExemptID,false, false, false,false);
			
			test_steps.add("MRB Reservation With Deposit Policy Created: <b>" + reservationNo + "</>");
			app_logs.info("MRB Reservation With Deposit Policy Created: " + reservationNo);
			//TripSummary tripSummary = resV2.getTripSummaryDetail(driver);								
			test_steps.add("========== Verifying associated Deposit policy details at Policies And Disclaimers tab ==========");
			resV2.verifyDepositPolicyDetailsAtPoliciesAndDisclaimersRV2(driver, test_steps, policiesAre.get(0), policyDesc);			
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Create Reservation", "Failed to Create MRB Reservation With Deposit Policy", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Create Reservation", "Failed to Create MRB Reservation With Deposit Policy", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Deposit Policy in Folio & History Logs, for MRB Reservation - 907868
		try
		{
			ArrayList<String> totalCharges = new ArrayList<String>();
			ArrayList<String> actualTotalCharges = new ArrayList<String>();			
			resV2.click_Folio(driver, test_steps);
			resV2.verifySpinerLoading(driver);
			test_steps.add("<b>======Fixed Pay Deposit Policy Amount verification FOR MRB Reservation======</b>");
			app_logs.info("======Fixed Pay Deposit Policy Amount verification FOR MRB Reservation======");
			totalCharges = resV2.getTotalChargesBasedOnDates(driver, test_steps, checkInDate, checkOutDate, true, "Room Charge");			
			for(int i=0;i<totalCharges.size();i++)	{
				actualTotalCharges.add(totalCharges.get(i));
			}
			depositAmount = resV2.calculationOfDepositAmountToBePaidForRateV2(actualTotalCharges, policyFeeTypeList.get(0), policyValues.get(0),"2");						
			String amount = Utility.convertDecimalFormat(depositAmount);
			amount = "$" + depositAmount + "";
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), paymentTypes.get(1), "", amount,
					paymentTypes.get(1), "", "", "", "");
			test_steps.add("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			app_logs.info("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Made a deposit payment of "+amount+" using "+paymentTypes.get(1), 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);			
		}
		catch(Exception e)
		{
			Utility.catchException(driver, e, "Create Reservation", "Failed to Create MRB Reservation With Deposit Policy", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)
		{
			Utility.catchError(driver, e, "Create Reservation", "Failed to Create MRB Reservation With Deposit Policy", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Create reservation with deposit policy with invalid CC - 907844
		String invalidCardNumber="", invalidCardQuoteConfirmationNumber = "";
		try
		{
			guestFirstName = "invalidCC_Quote"+Utility.generateRandomNumber();
			guestLastName="test_s";
			adult = "1";
			children = "1";
			invalidCardNumber = "4141414141414141";
			nameOnCard = "invalid-card-auto";
			//navigation.navigateToReservations(driver);
			//navigation.navigateToReservation(driver);
			resV2.click_NewReservation(driver, test_steps);		
			resV2.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adult, children, ratePlanNames.get(1), "");				
			resV2.clickOnFindRooms(driver, test_steps);
			ArrayList<String> roomNumbers = new ArrayList<>();
			roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassNames.get(0));					
			resV2.selectRoomToReserve(driver, test_steps, roomClassNames.get(0), roomNumbers.get(0));
			resV2.clickNext(driver, test_steps);
			resV2.clickOnCreateGuestButton(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);		
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
			resV2.enter_PaymentDetails(driver, test_steps, paymentTypes.get(0), invalidCardNumber, nameOnCard, expiryDate, "");
			resV2.clickBookNow(driver, test_steps);
			///Add methods to close deposit popup & click on save as quote button
			resV2.VerifyInvalidCardMessageOnDepositPolicyPopup(driver, test_steps);
			resV2.closeDepositPaymentPopup(driver, test_steps);	
			resV2.saveAsQuoteFromDepositPaymentPopup(driver, test_steps);
			////////////////Verify Status Bar for Quote & History Log///////////////////////////////////////////////
			ReservationStatusBar statusBar = resV2.getStatusBarDetail(driver);
			resV2.verifyStatusBarDetail(statusBar,
					salutation + " " + guestFirstName + " " + guestLastName, true, true, 
					"QUOTE",  true, true, 
					invalidCardQuoteConfirmationNumber,  true, true, 
					"("+Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d")+" - "+Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MMM d")+")",  true, true, 
					zone,  false, true, 
					email,  true, true, 
					adult,  false, true, 
					children,  false, true,  test_steps);
			invalidCardQuoteConfirmationNumber = statusBar.getSB_CONFIRMATION_NO();
			app_logs.info("Quote Confirmation Number : " + invalidCardQuoteConfirmationNumber);
			resV2.verifyBookButton(driver, test_steps);			
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Create Quote When invalid CC is entered", "Failed to Create Quote from Deposit Policy Popup", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Create Quote When invalid CC is entered", "Failed to Create Quote from Deposit Policy Popup", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Folio For Invalid CC and History log for Quote Creation - 907844	
		try
		{
			String last4Digit=Utility.getCardNumberHidden(invalidCardNumber);
			//String desc =""+CCTypes[i]+"-"+last4Digit+" "+expiryDate;
			String desc = "Name: " + nameOnCard + " Account #: " + last4Digit + " Exp.Date:" + expiryDate + "";			
			resV2.click_Folio(driver, test_steps);
			ArrayList<FolioLineItem> folioLineItemsAfterInvalidCC = resV2.getAllFolioLineItems(driver);
			resV2.verifyFolioLineItem(folioLineItemsAfterInvalidCC.get(1), "Failed", true, false, 
					Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, false,
					"Visa", true, false, desc, true, false, "0", true, false, 
					"0", true, false, "0", true, false, "0", true, false, test_steps);
			String amount = Utility.convertDecimalFormat("0");
			amount = "$" + amount + "";
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), 
					"Visa",invalidCardNumber, amount, "", "", "", "", "");
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Saved quote with Confirmation Number: "+invalidCardQuoteConfirmationNumber, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();
			//resV2.closeAllOpenedReservations(driver);
			
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Verify Quote FOlio/History Log", "Failed to Verify Quote FOlio/History Log", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Verify Quote FOlio/History Log", "Failed to Verify Quote FOlio/History Log", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Refund Line Item using Cash/Cheque for Quote - 907982		
		//Verify by making payment using Reservation as Payment method for Single Quote Guest Folio - 907980
		try
		{
			test_steps.add("Verifying Payment & Refund Button in the FOlio....");
			app_logs.info("Verifying Payment & Refund Button in the FOlio....");
			resV2.click_Folio(driver, test_steps);
			if( !resV2.verifyIfRefundBtnIsVisibleInFolio(driver, test_steps) )
			{	app_logs.info("Refund Button is not visible in the Quote Folio");	}
			if( !resV2.verifyIfPayBtnIsVisibleInFolio(driver, test_steps) )
			{	app_logs.info("Pay Button is not visible in the Quote Folio");	}
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Verify Refund/Payment in Quote", "Failed to Verify Refund/Payment Buttons in Quote", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Verify Refund/Payment in Quote", "Failed to Verify Refund/Payment Buttons in Quote", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Convert Quote to Reservation and verify History Log - 908010
		//Verify that after booking quote, history log should update with "Converted quote to a reservation"
		try
		{
			resV2.switchDetailTab(driver, test_steps);
			resV2.DepositOverrideToggle(driver);
			resV2.convertToReservationFromQuote(driver, test_steps);
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Converted quote to a reservation", 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();
			Wait.wait15Second();
			resV2.closeAllOpenedReservations(driver);						
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Quote to Reservation", "Failed to Convert Quote to Reservation", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Quote to Reservation", "Failed to Convert Quote to Reservation", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify overriding Deposit Policy amount while creating Single room Reservation. - 907869
		try
		{
			guestFirstName = "OverrideDepositPolicy_"+Utility.generateRandomNumber();
			guestLastName="test_s";
			adult = "1";
			children = "1";
			nameOnCard = "auto-riddhi";
			
			//navigation.navigateToReservation(driver);
			driver.navigate().refresh();
			resV2.click_NewReservation(driver, test_steps);		
			resV2.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adult, children, ratePlanNames.get(1), "");				
			resV2.clickOnFindRooms(driver, test_steps);
			ArrayList<String> roomNumbers = new ArrayList<>();
			roomNumbers = resV2.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassNames.get(0));					
			resV2.selectRoomToReserve(driver, test_steps, roomClassNames.get(0), roomNumbers.get(0));
			resV2.clickNext(driver, test_steps);
			resV2.clickOnCreateGuestButton(driver, test_steps);
			resV2.turnOnOffcreateGuestProfileToggle(driver, test_steps,false);
			resV2.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);		
			resV2.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			resV2.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
			resV2.enter_PaymentDetails(driver, test_steps, paymentTypes.get(0), cardNumber, nameOnCard, expiryDate, "");
			resV2.DepositOverrideToggle(driver);
			resV2.OverrideDepositDueAmountInReservation(driver, test_steps, overrideDepositDueAmount);
			resV2.clickBookNow(driver, test_steps);
			reservationNo = resV2.get_ReservationConfirmationNumber(driver, test_steps);
			resV2.clickCloseReservationSavePopup(driver, test_steps);
			test_steps.add("Reservation created successfully, With overriding Deposit Due Amount : " + reservationNo);
			app_logs.info("Reservation created successfully, With overriding Deposit Due Amount : " + reservationNo);
			test_steps.add("========== Verifying associated Deposit policy details at Policies And Disclaimers tab ==========");
			resV2.verifyDepositPolicyDetailsAtPoliciesAndDisclaimersRV2(driver, test_steps, policiesAre.get(0), policyDesc);
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "OverrideDepositDue", "Failed to Create Reservation with Override Deposit Due Amount", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "OverrideDepositDue", "Failed to Create Reservation with Override Deposit Due Amount", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Override Deposit Due Amount
		try
		{
			ArrayList<String> totalCharges = new ArrayList<String>();
			ArrayList<String> actualTotalCharges = new ArrayList<String>();			
			resV2.click_Folio(driver, test_steps);
			resV2.verifySpinerLoading(driver);
			test_steps.add("<b>======Override Deposit Due Amount verification Started======</b>");
			app_logs.info("<b>======Override Deposit Due Amount verification Started======</b>");

			String amount = Utility.convertDecimalFormat(overrideDepositDueAmount);
			amount = "$" + amount + "";			
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), paymentTypes.get(0), "", amount,
					paymentTypes.get(0), "", "", "", "");
			test_steps.add("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			app_logs.info("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Made a deposit payment of "+overrideDepositDueAmount+" using "+amount, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			//resV2.closeAllOpenedReservations(driver);			
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "OverrideDepositDue", "Failed to Verify Reservation with Override Deposit Due Amount", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "OverrideDepositDue", "Failed to Verify Reservation with Override Deposit Due Amount", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//MP: Verify by Making payment using GC as payment method, for Single reservation Guest Folio - 907931
		//String GCAccNamenNo = GCAccName+" ("+GCAccNumber+")";
		try
		{
			driver.navigate().refresh();			
			resV2.click_Folio(driver, test_steps);
			Wait.wait10Second();
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);			
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], paymentMethods[1], gcNumber, 
				"", "", "", "", "");
		}
		catch(Exception e)
		{
			driver.navigate().refresh();						
			resV2.click_Folio(driver, test_steps);			
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], paymentMethods[1], gcNumber, 
					"", "", "", "", "");
		}
		String amount="";
		//Verify Folio Line Items & History Log for GC Payment
		try
		{
			amount = Utility.convertDecimalFormat(paymentAmounts[0]);
			amount = "$" + amount + "";			
			
			String GCAccNamenNo = GCAccName+" ("+GCAccNumber+")";
			//GCAccNamenNo = GCAccName
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), 
					"Gift Certificate",GCAccNamenNo, amount, "", "", "", "", "");
			String payByGC= GCAccName+" #"+gcNumber+" ("+GCAccNumber+")";
			test_steps.add("=============Navigate to History Log & Verify History Log For Payment using GC===========");
			app_logs.info("==========Navigate to History Log & Verify History Log For Payment using GC===========");
			resV2.switchHistoryTab(driver, test_steps);	
			resV2.enterTextToSearchHistory(driver, "Made a payment "+amount+" using Gift - "+payByGC, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			driver.navigate().refresh();
			Wait.wait15Second();
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "PaymentByGC", "Failed to Verify Payment By GC", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "PaymentByGC", "Failed to Verify Payment By GC", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		////////////////Navigate to Folio >> Make Cash Payment >> Remove Cash Payment Item - 907861
		try
		{
			driver.navigate().refresh();
			Wait.wait15Second();
			resV2.click_Folio(driver, test_steps);
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], "Cash", "", "", "", "", "", "");
		}
		catch(Exception e)
		{
			driver.navigate().refresh();
			resV2.click_Folio(driver, test_steps);
			folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			folioNewobj.makePayment(driver, test_steps, paymentAmounts[0], "Cash", "", "", "", "", "", "");
		}
		//folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), "Cash", amount, "", "", "");
		folioNewobj.deleteFolioLineItem(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), "Cash", amount, "", "", "");
		test_steps.add("<b> Successfully Deleted Cash Payment from Folio </b>");
		app_logs.info("<b> Successfully Deleted Cash Payment from Folio </b>");						
		resV2.switchHistoryTab(driver, test_steps);
		resV2.enterTextToSearchHistory(driver, "Removed Cash item Cash from Guest Folio", 
				true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
		app_logs.info("<b> Removed Cash Item Cash From Guest Folio - Message is displayed in History Log </b>");
		//Deleting Incidentals to the existing reservation Folio - 907867
		try
		{			
			resV2.click_Folio(driver, test_steps);					
			folioNewobj.addFolioLineItem(driver, test_steps, category, perUnit);
			Wait.wait10Second();
			amount = Utility.convertDecimalFormat(perUnit);
			amount = "$" + amount + "";						
			folioNewobj.deleteFolioLineItem(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), category, amount, "", "", "");
			//folioNewobj.clickPayButtonFromFolioTab(driver, test_steps);
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Removed "+category+" item ",
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Folio", "Failed to Add/Delete Folio Line Item", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);	
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Folio", "Failed to Add/Delete Folio Line Item", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Add result in testrail
		try {
			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseId, "\\|");

			 caseId = new ArrayList<String>();
			 statusCode = new ArrayList<String>();
			 comments = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				caseId.add(list.get(i));
				statusCode.add("1");
				comments.add("PASS : " + this.getClass().getSimpleName().trim());
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
					TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
	}	
}
