/*
 * AUTHOR - Riddhi
 * '907872|907942|907871|907944
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
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
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

public class VerifyDepositPolicyScenariosV2 extends TestCore
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
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account objAcc = new Account();
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
	String checkInDate="", checkOutDate = "", guestFirstName="", guestLastName="", seasonStartDate="",seasonEndDate="",policyDesc ="";

	String  propertyName = null, roomClassNames = null, roomClassAbbs = null,
			roomClassNameWithoutNum = null, roomClassAvvWithoutNum = null, rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null, balance = null,policyname=null,policyNameWithoutNum=null,
			tripTaxes = null, tripTotal = null, depositAmount = null, date=null,expPolicyFee="", roomChargeAmount="";
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
		return Utility.getData("VerifyDepositPolicyScenariosV2", excelRiddhi);
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
	public void verifyDepositPolicyScenariosV2(String TestCaseId, String TestCaseName, 
			String paymentGateway, String accountNumber, String accountDesc, String accountStatus, 
			String isCardPresent, String isCardNotPresent, String isEcommerce, String isRequireCVVForAll,
			String mode, String accountID, String subAccountId, String merchantPin,
			String storeId, String url, String tokenId,
			String roomClassName, String roomClassAbb, String maxAdults, String maxPersons, String roomQuantity, 
			String policyType,String PolicyFeeType, String policyName,String polictAttr, String policyValue,
			String chargesTypes,String ratePlanName,String rate,
			String paymentType, String cardNumber, String nameOnCard, String expiryDate,
			String marketSegment, String referral,
			String category, String perUnit, String quantity) throws ParseException 
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
		
		ArrayList<String> paymentGateways = Utility.splitInputData(paymentGateway);
		ArrayList<String> accountIDs = Utility.splitInputData(accountID);
		ArrayList<String> merchantPins = Utility.splitInputData(merchantPin);
		ArrayList<String> storeIds = Utility.splitInputData(storeId);
		ArrayList<String> urls = Utility.splitInputData(url);
		ArrayList<String> tokenIds = Utility.splitInputData(tokenId);
		ArrayList<String> paymentTypes = Utility.splitInputData(paymentType);
		
		// Select Allow non-zero balance at the time of check-out from Properties
		/*try 
		{
			test_steps.add("<b>========== Set Properties========</b>");
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.properties(driver);
			test_steps.add("Navigated to Properties");
			navigation.open_Property(driver, test_steps, propertyName);
			navigation.click_PropertyOptions(driver, test_steps);
			properties.depositRequiredForSaveGaurenteedReservationCheckbox(driver, true, test_steps);
			properties.uncheck_GuaranteedCheckBoxProperties(driver, test_steps, config.getProperty("flagOn"));
			properties.PublishButton(driver);
			test_steps.add("Click Publish Button");
		} 
		catch (Exception e)
		{
			handelingCatchException(e, "Failed to change Property Level Settings", test_name, "Properties");			
		} 
		catch (Error e) 
		{	handelingCatchError(e, "Failed to change Property Level Settings", test_name, "Properties");	
		}*/
		
		// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			app_logs.info("Navigated to Room Class");		
		
			for (int i = 0; i < roomClassNames.size(); i++) {
				//roomClassNames = roomClassAre.get(i) + Utility.threeDigitgenerateRandomString();
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
			}
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
				else	{
					polictiesNames = policies.createPolicies(driver, test_steps, "", "", policiesTypes.get(i), "", policiesAre.get(i),
							"", "",policyFeeTypeList.get(i), policyValues.get(i), chargesTypes, "", "", "No", "");
					test_steps.add("<b>======Policy Created : ======</b>"+polictiesNames);
					app_logs.info("<b>====== Policy Created :========</b>"+polictiesNames);					
				}
				//policyDesc = polictiesNames.get("Descriptions").get(i);
				app_logs.info(polictiesNames);
				app_logs.info(policiesNames);
				app_logs.info(policiesAttrsValue);
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
		//edit season & add policy for existing rate plan
		try
		{	rateplans = Utility.splitInputData(ratePlanName);		
			rates = Utility.splitInputData(rate);			
			policiesTypes.add("Deposit");
			policiesTypes.add("Deposit");
			policiesTypes.add("Deposit");
			policiesAre.add("DP_PercentageOfTotalCharge");
			policiesAre.add("DP_FlatFee");						
			/*for(int i =0;i<rateplans.size();i++)
			{
				//app_logs.info(policiesAre.get(i));
				app_logs.info("Date Range " + datesRangeList);
				app_logs.info("Rate Plans " + rateplans.get(i));
				app_logs.info("Rates " + rates.get(i));
				app_logs.info("room class Names " + roomClassNames.get(i));
				
				
				nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, rateplans.get(i), datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNames.get(i), rates.get(i), "", "", "", "", false);
				//nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, "RatePlanwithDP1", datesRangeList,
					//	seasonStartDate, seasonEndDate, "", "RC6_used4DepositPolicy", "150", "", "", "", "", false);
				Utility.refreshPage(driver);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList.get(0));
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);
				nightlyRate.clickSeasonPolicies(driver, test_steps);
				nightlyRate.selectPolicy(driver, policiesTypes.get(i), policiesAre.get(i), true, test_steps);				
				nightlyRate.clickSaveSason(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				rateGrid.closeRatePlan(driver, test_steps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				/*rateGrid.selectRatePlan(driver, rateplans.get(i), test_steps);
				rateGrid.clickOnEditRatePlan(driver);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList.get(0));				
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);				
				nightlyRate.clickSeasonPolicies(driver, test_steps);
				nightlyRate.selectPolicy(driver, policiesTypes.get(i), policiesAre.get(i), true, test_steps);
				nightlyRate.clickSaveSason(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				//nightlyRate.closeSeason(driver, test_steps);
				 
				//navigation.Inventory(driver, test_steps);
				//navigation.ratesGrid(driver);				
			}
			*/
		}
		catch(Exception e)
		{
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", test_name, test_description,
					test_catagory, test_steps);
		}
		catch(Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", test_name, test_description,
					test_catagory, test_steps);	
		}
		try
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
		}
		//Create Reservation With Deposit Policy
		String salutation = "Ms.";	
		guestFirstName = "DP_percentageOfCharge" + Utility.threeDigitgenerateRandomString();
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
		String children = "1";HashMap<String, String> data = null;
		String reservationNo="";
		FolioNew folioNewobj = new FolioNew();		
		HashMap<String, String> getRoomCharge = new HashMap<String, String>();
		
		try
		{
			//navigation.ReservationV2_Backward(driver);
			navigation.navigateToReservations(driver);
			
			data = resV2.createReservation_withAddonIncidental(driver, test_steps, "From Reservations page", 
					checkInDate, checkOutDate, adult, children, rateplans.get(0), "", roomClassNames.get(0), 
					roomClassAbbs.get(0), salutation, guestFirstName, guestLastName, 
					false, phoneNumber, altenativePhone, email, address1, address2, address3, city, country, state, 
					postalCode, isGuesProfile, marketSegment, referral, paymentTypes.get(0), "", "", "", 
					additionalGuests, roomNumber, quote, 
					noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, 
					taskStatus, accountName, accountType, accountFirstName, accountLastName, 
					isTaxExempt, taxExemptID, false, false, false, false, true, false, category, perUnit, quantity);
		
			test_steps.add("Reservation Created: <b>" + reservationNo + "</>");
			app_logs.info("Reservation Created: " + reservationNo);
			//resV2.basicSearch_WithReservationNo(driver, "20317118", true);
			TripSummary tripSummary = resV2.getTripSummaryDetail(driver);								
			test_steps.add("========== Verifying associated Deposit policy details at Policies And Disclaimers tab ==========");
			resV2.verifyDepositPolicyDetailsAtPoliciesAndDisclaimersRV2(driver, test_steps, policiesAre.get(0), policyDesc);						
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Create Reservation", "Failed to Create Reservation with Percentage of Total Charge Deposit Policy ", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Create Reservation", "Failed to Create Reservation with Percentage of Total Charge Deposit Policy ", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Folio Line items for Deposit Policy - Percentage of Total Charge Policy
		try
		{	
			//Navigate to Folio & History Log and verify Advance Deposit Payment
			resV2.click_Folio(driver, test_steps);
			resV2.verifySpinerLoading(driver);
			test_steps.add("<b>======Deposit Policy Amount verification======</b>");
			app_logs.info("======Deposit Policy Amount verification======");
			
			ArrayList<String> totalCharges = new ArrayList<String>();
			ArrayList<String> actualTotalCharges = new ArrayList<String>();
			totalCharges = resV2.getTotalChargesBasedOnDates(driver, test_steps, checkInDate, checkOutDate, true, "Room Charge");
			for(int i=0;i<totalCharges.size();i++)	{
				actualTotalCharges.add(totalCharges.get(i));
			}
			totalCharges = resV2.getTotalChargesBasedOnDates(driver, test_steps, checkInDate, checkOutDate, false, category);
			actualTotalCharges.add(totalCharges.get(0));
			depositAmount = resV2.calculationOfDepositAmountToBePaidForRateV2(actualTotalCharges, policyFeeTypeList.get(0), policyValues.get(0),"1");
			String amount = Utility.convertDecimalFormat(depositAmount);
			amount = "$" + depositAmount + "";			
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), paymentTypes.get(0), "", amount,
					paymentTypes.get(0), "", "", "", "");					
			test_steps.add("<b>======Start Verifying History======</b>");
			test_steps.add("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			app_logs.info("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Made a deposit payment of "+amount+" using "+paymentTypes.get(0), 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);
			
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Failed to Verify Deposit Policy in Folio", "Failed to Verify Deposit Policy in Folio", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Failed to Verify Deposit Policy in Folio", "Failed to Verify Deposit Policy in Folio", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//https://innroad.testrail.io/index.php?/cases/view/907944
		//Moneris : Verify the card payment, when Deposit Policy amount is paid for single reservation
		try
		{
			String payGatewayAccountName = paymentGateways.get(1) + "_Gateway";
		  	navigation.setup(driver);
		  	msObj.navigatetoMerchantServices(driver, test_steps);
		  	test_steps.add("Creating Payment Gateway : " + payGatewayAccountName);
		  	app_logs.info("Creating Payment Gateway : " + payGatewayAccountName);
		  	msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
			msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, accountNumber, accountDesc, accountStatus, 
		  			isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, paymentGateways.get(1),
		  			mode, accountIDs.get(1), subAccountId, merchantPins.get(1),
		  			storeIds.get(1), urls.get(1), tokenIds.get(1), "", "",
		  			"", "", "", "", propertyName);
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Failed to Create Moneris Gateway", "Failed to Create Moneris Gateway", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Failed to Create Moneris Gateway", "Failed to Create Moneris Gateway", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Case Id : 907944 - Create Reservation >> Make Advance Deposit Payment via CC - CaseId - Fixed Pay Deposit Policy 
		try
		{
			guestFirstName = "DP_withCCPayment";
			guestLastName="FixedPay_Policy"+Utility.generateRandomNumber();
			navigation.ReservationV2_Backward(driver);
			data = resV2.createReservationwithACF(driver, test_steps, "From Reservations page",
					  checkInDate, checkOutDate, adult, children, rateplans.get(1), "", roomClassNames.get(1),
					  roomClassAbbs.get(1), salutation, guestFirstName, guestLastName, phoneNumber,
					  altenativePhone, email, address1, address2, address3, city, country, state,
					  postalCode, isGuesProfile, marketSegment, referral, paymentTypes.get(1),
					  cardNumber, nameOnCard, expiryDate, additionalGuests, roomNumber, quote,
					  noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
					  taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
					  accountFirstName, accountLastName, isTaxExempt, taxExemptID,false,false,false,false);
			test_steps.add("Reservation Created: <b>" + reservationNo + "</>");
			app_logs.info("Reservation Created: " + reservationNo);
			TripSummary tripSummary = resV2.getTripSummaryDetail(driver);						
			test_steps.add("========== Verifying associated Deposit policy - Fixed Pay details at Policies And Disclaimers tab ==========");
			resV2.verifyDepositPolicyDetailsAtPoliciesAndDisclaimersRV2(driver, test_steps, policiesAre.get(1), policyDesc);						
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Create Reservation", "Failed to Create Reservation with Percentage of Total Charge Deposit Policy ", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Create Reservation", "Failed to Create Reservation with Percentage of Total Charge Deposit Policy ", 
					"ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		//Verify Folio Line items for Deposit Policy - Flat Fee Deposit Policy
		try
		{									
			ArrayList<String> roomCharges = new ArrayList<>();
			ArrayList<String> totalCharges = new ArrayList<String>();
			ArrayList<String> actualTotalCharges = new ArrayList<String>();
			
			resV2.click_Folio(driver, test_steps);
			resV2.verifySpinerLoading(driver);
			test_steps.add("<b>======Fixed Pay Deposit Policy Amount verification======</b>");
			app_logs.info("======Fixed Pay Deposit Policy Amount verification======");
			
			totalCharges = resV2.getTotalChargesBasedOnDates(driver, test_steps, checkInDate, checkOutDate, true, "Room Charge");
			for(int i=0;i<totalCharges.size();i++)	{
				actualTotalCharges.add(totalCharges.get(i));
			}
			//totalCharges = resV2.getTotalChargesBasedOnDates(driver, test_steps, checkInDate, checkOutDate, false, category);
			//actualTotalCharges.add(totalCharges.get(0));
			depositAmount = resV2.calculationOfDepositAmountToBePaidForRateV2(actualTotalCharges, policyFeeTypeList.get(1), policyValues.get(1),"1");						
			String amount = Utility.convertDecimalFormat(depositAmount);
			amount = "$" + depositAmount + "";
			String last4Digit=Utility.getCardNumberHidden(cardNumber);
			folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), paymentTypes.get(1), "", amount,
					paymentTypes.get(1), cardNumber, nameOnCard, last4Digit, expiryDate);
			paymentType=""+paymentTypes.get(1)+"-"+last4Digit+" "+expiryDate;			
			//folioNewobj.verifyFolioLineItems(driver, test_steps, Utility.getCurrentDate("dd/MM/yyyy"), paymentTypes.get(1), "", amount,
				//	paymentTypes.get(1), "", "", "", "");						
			//String paymentMethod= ""+paymentType+" "+getcardNo+" ("+Utility.expiryDate+")";
			test_steps.add("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			app_logs.info("========================Navigate to History Log & Verify History Log For Advance Deposit Payment========================");
			resV2.switchHistoryTab(driver, test_steps);
			resV2.enterTextToSearchHistory(driver, "Made a deposit payment of "+amount+" using "+paymentType, 
					true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			resV2.closeAllOpenedReservations(driver);

			//resV2.verifyHistoryLogForDifferentPayments(driver, test_steps, amount, paymentType, paymentTypes.get(1));						
		}
		catch(Exception e)	{
			Utility.catchException(driver, e, "Failed to Verify Deposit Policy in Folio", "Failed to Verify Deposit Policy in Folio", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}
		catch(Error e)	{
			Utility.catchError(driver, e, "Failed to Verify Deposit Policy in Folio", "Failed to Verify Deposit Policy in Folio", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		}				
		//Update Test Rail
		try 
		{
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
		 } catch (Exception e) 
		 {
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
