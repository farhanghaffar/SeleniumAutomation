package com.innroad.inncenter.tests;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class Ratev2VerifyDepositAndCheckInPolicyInReservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> depositPoliciesName = new ArrayList<>();
	HashMap<String, String> roomChargesAre= new HashMap<String, String>();
	ArrayList<String> roomCharges = new ArrayList<>();
	HashMap<String, String> checkinPoliciesData= new HashMap<String, String>();
	ArrayList<String> policyNames= new ArrayList<String>();
	ArrayList<String> policyTypes= new ArrayList<String>();
	ArrayList<HashMap<String, String>> getAccountPoliciesData= new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> getdepositPoliciesData= new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> getcheckinPoliciesData= new ArrayList<HashMap<String, String>>();
	ArrayList<String> roomNumber= new ArrayList<String>();
	ArrayList<String> abbreviations= new ArrayList<String>();
	HashMap<String, String> selectedpolicyNames= new HashMap<String, String>();
	HashMap<String, String> confirmationNOAndStatus= new HashMap<String, String>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservation = new CPReservationPage();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policies = new Policies(); 
	RatesGrid rateGrid = new RatesGrid();
	Account accountPage = new Account();
	ReservationSearch reservationSearch= new ReservationSearch();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();	
	ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
	ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();	
	HashMap<String, String> getdepositAmount = new HashMap<String,String>();
	HashMap<String, Double> getcheckInAmount = new HashMap<String,Double>();

	String  checkInDatesIs=null,checkOutDatesIs=null,
			expiryDate=null,   depositAmount=null,paymentTypeIs=null,ratePlanCheckInPolicy=null, checkInAmount=null, paymentAmount=null
			,checkInCardFormat=null,fourDigitCardNo=null, depositPolicyApplied=null, checkinPolicyApplied=null;
	String[] roomClassArray = null, policyTypeIs=null, policyNameIs=null,policyAttr1Is=null,policyAttrValueIs=null,policyAttr2Is=null;
	double balance =0.00, paymentAmt=0.00;	
	private void completeCheckInIfPolicyExist(double balanceAmt, String amountToBePaid) throws Exception{	
		if(balanceAmt>0.00){
			reservation.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			reservation.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservation.verifyAmountOnPaymentScreen(driver, amountToBePaid, testSteps);
			reservation.clickLogORPayAuthorizedButton(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservation.clickCloseButtonOfSuccessModelPopup(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
		}else{
			reservation.clickOnConfirmCheckInButton(driver, testSteps);
			reservation.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}	
	private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
		if(balanceAmt>0.00 || balanceAmt==0.00) {
			reservation.clickOnConfirmCheckInButton(driver, testSteps);
			reservation.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}	

	private void verificationDepositWithPolicy(String paymentType, String policyName,String amount, String historyPaymentType) throws InterruptedException, ParseException {
		reservation.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservation.verify_FolioPayment(driver, testSteps, amount);
		testStepsOne=reservation.clickOnDetails(driver);
		testSteps.addAll(testStepsOne);
		reservation.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");
		reservation.verifyTripSummaryPaidAmount(driver, testSteps, amount);
		reservation.click_History(driver, testSteps);
		reservation.verifyDepositAtHistoryTab(driver, testSteps, amount, historyPaymentType);
	}

	private void verificationDepoistWithoutPolicy(String paymentType, String date,String policyName) throws InterruptedException, ParseException
	{
		reservation.verifyNoPaymentAtFolioLineItem(driver, testSteps, date, paymentType);
		testStepsOne=reservation.clickOnDetails(driver);
		testSteps.addAll(testStepsOne);
		reservation.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");	
	}

	private void verificationCheckinWithPolicy(String paymentType, String policyName, String amount, String payment, String cardFormat) throws InterruptedException, ParseException {
		reservation.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservation.clickFolio(driver, testSteps);
		reservation.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservation.verify_FolioPayment(driver, testSteps, payment);
		reservation.click_History(driver, testSteps);
		reservation.verifyChekInReservationOnHistoryTab(driver, testSteps);
		reservation.verifyHistoryWithCapturedPayment(driver, testSteps, amount, cardFormat,paymentType);
	}

	private void verificationCheckinWithoutPolicy(String policyName) throws ParseException, InterruptedException {
		reservation.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservation.click_History(driver, testSteps);
		reservation.verifyChekInReservationOnHistoryTab(driver, testSteps);	
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {	 
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);

	}
	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyDepositPolicyChewckInPolicyOnReservation(String checkInDate, String checkOutDate, 
			String adult, String child,String ratePlanName, String ratePlanNames,String roomClassName, String salutation,String guestFirstName, String guestLastName,
			String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral, String isAccountPolicyCreate,String policyType,String policyName,
			String policyAttr1,String policyAttrValue,String policyAttr2, String accountType, String accountName,
			String sourceOfRes,String accountFirstName,String  accountLastName,String phoneNumber, String address, String city, String country, String state,String postalCode, String isAccountPolicyApplicable ) throws ParseException {

		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1842' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1842</a>";
		test_catagory = "Reservation";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");


		policyNames.clear();
		policyTypes.clear();
		seasonDepositPolicy.clear();
		seasonCheckInPolicy.clear();
		getdepositAmount.clear();
		getcheckInAmount.clear();
		getAccountPoliciesData.clear();
		getdepositPoliciesData.clear();
		getcheckinPoliciesData.clear();
		roomNumber.clear();
		abbreviations.clear();
		selectedpolicyNames.clear();
		confirmationNOAndStatus.clear();
		checkinPoliciesData.clear();
		roomChargesAre.clear();
		roomCharges.clear();
		depositPoliciesName.clear();
		try {
			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){
				if (guestFirstName.split("\\|").length>1) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2),
								ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
					}
				}else
				{
					checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2),
							ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
				}
			}
			else{
				if (guestFirstName.split("\\|").length>1) {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}else {
					checkInDates.add(Utility.parseDate(checkInDate, ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
					checkOutDates.add(Utility.parseDate(checkOutDate,ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
				}

			}
			if (guestFirstName.split("\\|").length>1) {
				checkInDatesIs = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDatesIs = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}else {
				checkInDatesIs = checkInDates.get(0);
				checkOutDatesIs = checkOutDates.get(0);
			}



			app_logs.info(checkInDatesIs);
			app_logs.info(checkOutDatesIs);
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


		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);

		}

		//Get Abbreviations
		try
		{
			if (guestFirstName.split("\\|").length>1) {
				testSteps.add("=====Getting Abbreviations=====");
				navigation.clickSetup(driver);
				navigation.roomClass(driver);
				abbreviations=	rc.getAbbrivation(driver, "|", roomClassName, testSteps);								
				app_logs.info(abbreviations);
				navigation.inventoryV2(driver);
			}
			else {
				navigation.inventory(driver);		
			}
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);

		}
		try {

			testSteps.add("Navigated to Inventory");
			policyTypeIs=policyType.split("\\|");
			policyNameIs=policyName.split("\\|");
			policyAttr1Is=policyAttr1.split("\\|");
			policyAttrValueIs=policyAttrValue.split("\\|");
			policyAttr2Is=policyAttr2.split("\\|");		
			if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
				testSteps.add("========== Creating new policy for account ==========");
				navigation.policies(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);					
				for(int i=0;i<policyTypeIs.length;i++){
					String policyNameis=policyNameIs[i]+Utility.generateRandomNumber();
					app_logs.info(policyNameis);
					policyNames.add(policyNameis);
					policyTypes.add(policyTypeIs[i]);
					policies.createPolicies(driver, testSteps, "", "", policyTypeIs[i], "", policyNameis,policyNameis , "", 
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
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "policy", "policy", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "policy", "policy", testName,
					test_description, test_catagory, testSteps);

		}
		// Navigate to Rate Grid

		try {				

			navigation.ratesGrid(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.verifyAndClickExpandIconOfRatePlan(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Navigate to Rate Grid", "RateGrid", "RateGrid", testName, test_description,
					test_catagory, testSteps);
		}

		// Get Rate Plan Data
		try {

			roomClassArray = roomClassName.split("\\|");
			app_logs.info(roomClassArray[0]);
			app_logs.info(roomClassArray[1]);	
			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);	
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			rateGrid.clickEditIcon(driver, testSteps);
			rateGrid.verifyRatePlaninEditMode(driver, testSteps, ratePlanName);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, checkInDates.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			nightlyRate.clickSeasonPolicies(driver, testSteps);
			testSteps.add("==================Get Policy from  Season==================");
			ArrayList<String> deposit= new ArrayList<String>();
			ArrayList<String> checkin= new ArrayList<String>();
			if (guestFirstName.split("\\|").length>1) {
					for(int i=0;i<roomClassArray.length;i++) {
					deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),roomClassArray[i],testSteps));
					checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),roomClassArray[i],testSteps));											
				}
				

			}else {
				deposit.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("depositPolicyText"),roomClassArray[0],testSteps));
				checkin.add(nightlyRate.getSeasonLevelPolicyByRoomClass(driver, ratesConfig.getProperty("checkInPolicyText"),roomClassArray[0],testSteps));					
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
			if(!Utility.isEmptyStringArrayList(seasonDepositPolicy)){
				testSteps.add("No Deposit Policy Associated with Season");
			}
			if(!Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				testSteps.add("No Check-In Policy Associated with Season");
			}
			nightlyRate.closeSeason(driver, testSteps);
			rateGrid.closeRatePlan(driver, ratePlanName,testSteps );	
			nightlyRate.clickYesButton(driver, testSteps);
		}
		catch (Exception e) {
			Utility.catchException(driver, e, "get value from  Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "get value from Nightly Rate Plan", "RateGrid", "RateGrid", testName,
					test_description, test_catagory, testSteps);
		}

		try{
			if(Utility.isEmptyStringArrayList(seasonDepositPolicy)){
				testSteps.add("=========Get  Data from Deposit Policy if policy exist on Season level=========");
				navigation.clickPoliciesAfterRateGridTab(driver,testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);	
				if (guestFirstName.split("\\|").length>1) {
					for(String str: seasonDepositPolicy) {
						getdepositPoliciesData.add(policies.getpoliciesData(driver, testSteps, ratesConfig.getProperty("depositPolicyText"), str));							
					}
				}else {
					getdepositPoliciesData.add(policies.getpoliciesData(driver, testSteps, ratesConfig.getProperty("depositPolicyText"), seasonDepositPolicy.get(0)));}
			}
			app_logs.info(getdepositPoliciesData);
			if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				testSteps.add("=========Get  Data from Check-In Policy if policy exist on Season level=========");
				navigation.clickPoliciesAfterRateGridTab(driver,testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);		
				HashMap<String, String> valueHashmap= new HashMap<String, String>();
				if (guestFirstName.split("\\|").length>1) {
					for(int i=0;i<seasonCheckInPolicy.size();i++) {
						getcheckinPoliciesData.add(policies.getpoliciesData(driver, testSteps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(i)));
						valueHashmap.put(seasonCheckInPolicy.get(i), getcheckinPoliciesData.get(i).get("AttrValue"));								
					}
					String  value=null;
					value=Utility.maxUsingCollectionsMax(valueHashmap);
					checkinPolicyApplied=Utility.getKeyOfValue(valueHashmap, value);
					checkinPoliciesData.put("AttrValue", value);
					app_logs.info(checkinPoliciesData);
				}else {
					checkinPoliciesData=policies.getpoliciesData(driver, testSteps, ratesConfig.getProperty("checkInPolicyText"), seasonCheckInPolicy.get(0));
					checkinPolicyApplied=seasonCheckInPolicy.get(0);
				
				}
			}	
			app_logs.info(getcheckinPoliciesData);
		}catch (Exception e) {
			Utility.catchException(driver, e, "get value from  Policies", "Policies", "Policies", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "get value from Policies", "Policies", "Policies", testName,
					test_description, test_catagory, testSteps);

		}

		try{			
			testSteps.add("==================Creating a New Reservation==================");					
			expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			String randomNumber=Utility.generateRandomNumber();
			if (guestFirstName.split("\\|").length>1) {
				reservation.createReservations(driver, testSteps, sourceOfRes, accountFirstName, accountLastName, 
						checkInDatesIs, checkOutDatesIs, adult, child, ratePlanNames, "", roomClassName, "Yes","", accountName, 
						salutation, guestFirstName+randomNumber, guestLastName+randomNumber, phoneNumber, 
						"", "", accountType, address, "", "", city, country, state, postalCode, "", "", "", referral, 
						paymentType, cardNumber, nameOnCard, expiryDate, policyNames, policyTypes,isAccountPolicyApplicable,selectedpolicyNames,isAccountPolicyCreate,confirmationNOAndStatus,roomNumber);
			}
			else {
				reservation.createReservations(driver, testSteps, sourceOfRes, accountFirstName, accountLastName, 
						checkInDatesIs, checkOutDatesIs, adult, child, ratePlanNames, "", roomClassArray[0], "Yes","",accountName, 
						salutation, guestFirstName+randomNumber, guestLastName+randomNumber, phoneNumber, 
						"", "", accountType, address, "", "", city, country, state, postalCode, "", "", "", referral, 
						paymentType, cardNumber, nameOnCard, expiryDate, policyNames, policyTypes,isAccountPolicyApplicable,selectedpolicyNames,isAccountPolicyCreate,confirmationNOAndStatus,roomNumber);
			}

			if (isAccountPolicyCreate.equalsIgnoreCase("No")) {
				testSteps.add("==================Get Data of Policy Which is associated with account if policy not created==================");
				navigation.inventory(driver);
				testSteps.add("Navigated to Inventory");
				navigation.policies(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				for(int i=0;i<policyTypes.size();i++) {
					if(!((selectedpolicyNames.get(policyTypes.get(i)).equals(ratesConfig.getProperty("noPolicyText"))))) {
						getAccountPoliciesData.add(policies.getpoliciesData(driver, testSteps, policyTypes.get(i), selectedpolicyNames.get(policyTypes.get(i))));
					}
				}
				navigation.reservationBackward3(driver);
				reservation.closeReservationTab(driver);
				reservationSearch.basicSearch_WithResNumber(driver,confirmationNOAndStatus.get("ConfirmationNo"));					   
			}

			testSteps.add("==================Verify Deposit Policy On Reservation==================");
			reservation.clickFolio(driver, testSteps);
			roomChargesAre= reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(0), checkOutDates.get(0), true);
			for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
				roomCharges.add(entry.getValue());
			}
			if (guestFirstName.split("\\|").length>1) {
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(1), roomNumber.get(1));
				roomChargesAre= reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(0), checkOutDates.get(0), true);
				for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
					roomCharges.add(entry.getValue());
				}

				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(0), roomNumber.get(0));
			}
			app_logs.info(roomCharges);
			if(isAccountPolicyApplicable.equals("Yes") ){
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {							
					depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, policyAttr1Is[0], policyAttrValueIs[0]);}
				else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
					depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, getAccountPoliciesData.get(0).get("Type"), getAccountPoliciesData.get(0).get("AttrValue"));	
				}
			}
			else if(Utility.isEmptyStringArrayList(seasonDepositPolicy)){
				if (guestFirstName.split("\\|").length>1) {
					for(int i=0;i<seasonDepositPolicy.size();i++) {
						String size= String.valueOf(roomClassName.split("\\|").length);
						getdepositAmount.put(seasonDepositPolicy.get(i), reservation.calculationOfDepositAmountToBePaidForRateV2(roomCharges, getdepositPoliciesData.get(i).get("Type"), getdepositPoliciesData.get(i).get("AttrValue"),size));
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
					depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomChargesAre, getdepositPoliciesData.get(0).get("Type"), getdepositPoliciesData.get(0).get("AttrValue"));
					depositPolicyApplied=seasonDepositPolicy.get(0);
				}

			}
			app_logs.info(depositPolicyApplied);
			app_logs.info(depositAmount);
			fourDigitCardNo=Utility.getCardNumberHidden(cardNumber);
			if(paymentType.equals("MC")) {
				paymentTypeIs=""+paymentType+" "+fourDigitCardNo+" ("+expiryDate+")";
			}else if(paymentType.equals("Cash")){
				paymentTypeIs=paymentType;
			}
			app_logs.info(paymentTypeIs);
			if(isAccountPolicyApplicable.equals("Yes")) {
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					verificationDepositWithPolicy(paymentType,policyNames.get(0),depositAmount,paymentTypeIs);
				}
				else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(0)).equals(ratesConfig.getProperty("noPolicyText")))) {
					verificationDepositWithPolicy(paymentType,selectedpolicyNames.get(policyTypes.get(0)),depositAmount,paymentTypeIs);
				}else {
					verificationDepoistWithoutPolicy(paymentType,checkInDates.get(0),ratesConfig.getProperty("noPolicyText"));		
				}						
			}else {
				if(Utility.isEmptyStringArrayList(seasonDepositPolicy)){
					verificationDepositWithPolicy(paymentType,depositPolicyApplied,depositAmount,paymentTypeIs);
				}else {
					verificationDepoistWithoutPolicy(paymentType,checkInDates.get(0),ratesConfig.getProperty("noPolicyText"));
				}										
			}									
		}catch (Exception e) {
			Utility.catchException(driver, e, "verify Deposit on Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "verify Deposit on Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}

		try{
			reservation.clickFolio(driver, testSteps);
			balance= Double.parseDouble(reservation.get_Balance(testSteps,driver));
			if (guestFirstName.split("\\|").length>1) {
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(1), roomNumber.get(1));
				balance=balance+Double.parseDouble(reservation.get_Balance(testSteps,driver));
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(0), roomNumber.get(0));
			}
			app_logs.info(balance);
			if(isAccountPolicyApplicable.equals("Yes")){
				paymentAmt=Double.parseDouble(reservation.get_PaymentsForAccountReservation(driver, testSteps));
				app_logs.info(paymentAmt);
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					checkInAmount=reservation.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), policyAttrValueIs[1]);}
				else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText")))) {
					checkInAmount=reservation.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), getAccountPoliciesData.get(1).get("AttrValue"));
				}

			}
			else if(isAccountPolicyApplicable.equals("No") && Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				paymentAmt=Double.parseDouble(reservation.get_PaymentsForAccountReservation(driver, testSteps));
				app_logs.info(paymentAmt);
				checkInAmount=reservation.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), checkinPoliciesData.get("AttrValue"));
			}
			else if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)){
				paymentAmt=Double.parseDouble(reservation.get_Payments(driver, testSteps));
				app_logs.info(paymentAmt);
				checkInAmount=reservation.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), checkinPoliciesData.get("AttrValue"));

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

			if(paymentType.equals("MC")) {
				checkInCardFormat=""+paymentType+"-"+fourDigitCardNo+" "+expiryDate+"";
			}else if(paymentType.equals("Cash")) {
				checkInCardFormat=paymentType;
			}

			app_logs.info(checkInCardFormat);	
			testSteps.add("==================Check In Reservation==================");
			reservation.clickCheckInButton(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			reservation.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
			if(isAccountPolicyApplicable.equals("Yes")) {
				if(isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
					completeCheckInIfPolicyExist(balance,checkInAmount);
					testSteps.add("==================Verify Check-In Policy On Reservation==================");
					verificationCheckinWithPolicy(paymentType,policyNames.get(1),checkInAmount,paymentAmount,checkInCardFormat);
				}else if(isAccountPolicyCreate.equalsIgnoreCase("No") && !(selectedpolicyNames.get(policyTypes.get(1)).equals(ratesConfig.getProperty("noPolicyText"))))  {
					completeCheckInIfPolicyExist(balance,checkInAmount);
					verificationCheckinWithPolicy(paymentType,selectedpolicyNames.get(policyTypes.get(1)),checkInAmount,paymentAmount,checkInCardFormat);
				}						
				else {
					completeCheckInIfPolicyDoesntExist(balance);
					testSteps.add("==================Verify Check-In Policy On Reservation==================");
					verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
				}
			}else {
				if(Utility.isEmptyStringArrayList(seasonCheckInPolicy)) {
					completeCheckInIfPolicyExist(balance,checkInAmount);
					testSteps.add("==================Verify Check-In Policy On Reservation==================");
					verificationCheckinWithPolicy(paymentType,checkinPolicyApplied,checkInAmount,paymentAmount,checkInCardFormat);

				}else
				{
					completeCheckInIfPolicyDoesntExist(balance);
					testSteps.add("==================Verify Check-In Policy On Reservation==================");
					verificationCheckinWithoutPolicy(ratesConfig.getProperty("noPolicyText"));
				}
			}			

		}catch (Exception e) {
			Utility.catchException(driver, e, "verify checkIn on Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "verify checkIn on Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}		

		try {
			if(isAccountPolicyCreate.equals("Yes")){
				navigation.inventory(driver);
				testSteps.add("Navigated to Inventory");
				navigation.policies(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				policies.deleteAllPolicies(driver, testSteps, policyNames);						
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Policy", "Policy", "Policy", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Policy", "Policy", "Policy", testName,
					test_description, test_catagory, testSteps);
		}	
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyDPChPInReservation", excel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}


}
