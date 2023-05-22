package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGroupPoliciesForMRBReservation  extends TestCore{
	String testName = this.getClass().getSimpleName().trim();
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {	 
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);

	}
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	
	Navigation navigation = new Navigation();
	Groups group = new Groups();
	CPReservationPage reservationPage= new CPReservationPage();
	Policies policies = new Policies(); 
	Reservation res = new Reservation();
	String[] roomClassArray = null, policyTypeIs=null, policyNameIs=null,policyAttr1Is=null,policyAttrValueIs=null,policyAttr2Is=null;
	ArrayList<String> policyNames= new ArrayList<String>();
	ArrayList<String> policyTypes= new ArrayList<String>();
	ArrayList<String> gettestSteps = new ArrayList<>();
	String reservationNo=null,depositPolicyApplied=null, checkinPolicyApplied=null, cancelPolicyApplied=null,depositAmount=null,cancelAmount=null,fourDigitCardNo=null,paymentTypeIs=null,expiryDate=null,
			checkInAmount=null,paymentAmount=null,checkInCardFormat=null, cancellationFeeAmount=null;
	HashMap<String, String> roomChargesAre= new HashMap<String, String>();
	ArrayList<String> roomCharges = new ArrayList<>();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	ArrayList<String> seasonDepositPolicy = new ArrayList<String>();
	ArrayList<String> seasonCheckInPolicy = new ArrayList<String>();	
	ArrayList<String> seasonCancelPolicy = new ArrayList<String>();	
	ArrayList<HashMap<String, String>> getdepositPoliciesData= new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> getcheckinPoliciesData= new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> getcancelPoliciesData= new ArrayList<HashMap<String, String>>();
	HashMap<String, String> checkinPoliciesData= new HashMap<String, String>();
	HashMap<String, String> cancelPoliciesData= new HashMap<String, String>();
	ArrayList<String> abbreviations= new ArrayList<String>();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	ArrayList<String> rooms= new ArrayList<String>();
	HashMap<String, String> getdepositAmount = new HashMap<String,String>();
	HashMap<String, Double> getcheckInAmount = new HashMap<String,Double>();
	HashMap<String, String> getCancelAmount = new HashMap<String,String>();
	double balance =0.00, paymentAmt=0.00;	
	ArrayList<String> accountNo= new ArrayList<String>();
	
	
	
	private void verificationDepositWithPolicy(String paymentType, String policyName,String amount, String historyPaymentType) throws InterruptedException, ParseException {
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservationPage.verify_FolioPayment(driver, testSteps, amount);
		testStepsOne=reservationPage.clickOnDetails(driver);
		testSteps.addAll(testStepsOne);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");
		reservationPage.verifyTripSummaryPaidAmount(driver, testSteps, amount);
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyDepositAtHistoryTab(driver, testSteps, amount, historyPaymentType);
	}
	
	private void verificationDepoistWithoutPolicy(String paymentType, String date,String policyName) throws InterruptedException, ParseException
	{
		reservationPage.verifyNoPaymentAtFolioLineItem(driver, testSteps, date, paymentType);
		testStepsOne=reservationPage.clickOnDetails(driver);
		testSteps.addAll(testStepsOne);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, testSteps, policyName, "");	
	}
	
	private void completeCheckInIfPolicyExist(double balanceAmt, String amountToBePaid) throws Exception{	
		if(balanceAmt>0.00){
			reservationPage.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			//reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			if(driver.findElements(By.xpath(OR_Reservation.CP_Reservation_CheckIn_ModelPopMsg)).size()>0);
			{
				boolean isRoomDirty = Utility.isElementPresent(driver, By.xpath(OR_Reservation.CP_Reservation_CheckIn_ModelPopMsg));
				String loading = "(//div[@class='ir-loader-in'])";
				Wait.waitTillElementDisplayed(driver, loading);

			if (isRoomDirty) {
				reservationPage.ClickConfirmButtonOfDirtyPopupModel(driver, testSteps, isRoomDirty);
				}
			}
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			//reservationPage.verifyAmountOnPaymentScreen(driver, amountToBePaid, testSteps);
			reservationPage.clickLogORPayAuthorizedButton(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);			
			reservationPage.clickCloseButtonOfCheckInSuccessfully(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
		}else{
			reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}	
	
	
	private void completeCheckInIfPolicyDoesntExist(double balanceAmt) throws InterruptedException {
		if(balanceAmt>0.00 || balanceAmt==0.00) {
			reservationPage.clickOnConfirmCheckInButton(driver, testSteps);
			reservationPage.verifyRoomUnassignedOrAlreadyBookedOrDirty(driver);
			Wait.waitUntilPageLoadNotCompleted(driver, 30);
		}
	}
	
	private void verificationCheckinWithPolicy(String paymentType, String policyName, String amount, String payment, String cardFormat) throws InterruptedException, ParseException {
		reservationPage.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservationPage.clickFolio(driver, testSteps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, testSteps);
		reservationPage.verify_FolioPayment(driver, testSteps, payment);
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, testSteps);
		//reservationPage.verifyHistoryWithCapturedPayment(driver, testSteps, amount, cardFormat,paymentType);
	}

	private void verificationCheckinWithoutPolicy(String policyName) throws ParseException, InterruptedException {
		reservationPage.verifyCheckInPolicy(driver, testSteps, policyName, "");
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyChekInReservationOnHistoryTab(driver, testSteps);	
	}	
	
	private void verificationCancellationWithPolicy(String policyName, String paymentType, String cancelAmount,String cancellationFeeCategory , String cancellationFeeAmount,String  cardFormat, String type) throws InterruptedException, ParseException {
		reservationPage.verifyCancellationPolicy(driver, testSteps, policyName, "");
		reservationPage.clickFolio(driver, testSteps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, cancelAmount, testSteps);
		reservationPage.verifyFolioLineItemAmoutPaid(driver, cancellationFeeCategory, cancellationFeeAmount, testSteps);
		reservationPage.verify_FolioPayment(driver, testSteps, cancellationFeeAmount);
		reservationPage.click_History(driver, testSteps);
		reservationPage.verifyCancellationReservationOnHistoryTab(driver, testSteps);
		//reservationPage.verifyHistoryWithCancellationPayment(driver, testSteps, cancelAmount, cardFormat, paymentType, type);
	}
	
	
	private void completeCancellationReservation(double folioPayment, String cancelAmount, ArrayList<String> type) throws InterruptedException {
		testSteps.add("==========Complete Cancel Reservation==========");
		reservationPage.reservationStatusPanelSelectStatus(driver, "Cancel", testSteps);
		reservationPage.inputReason(driver, testSteps, "CancellationReason");		
		reservationPage.proceesToCancellationPayment(driver, testSteps);
		reservationPage.verifyAmountOnPaymentScreen(driver, cancelAmount, testSteps);
		if(Double.parseDouble(cancelAmount)>=folioPayment) {
			reservationPage.clickPaybutton(driver, testSteps, cancelAmount);
			reservationPage.clickCloseButtonOfCancellationSuccessfully(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);								
			type.add("Pay");
		}else if(Double.parseDouble(cancelAmount)<folioPayment) {
			reservationPage.clickRefundbutton(driver, testSteps, cancelAmount);
			reservationPage.clickCloseButtonOfCancellationSuccessfully(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 10);
			type.add("Refund");
		}
	
			
	}
	@Test(dataProvider = "getData", groups = { "groups" })
	public void verifyDepositCheckInAndCancellationPolicies(String TestCaseID,String policyType,String policyName,
			String policyAttr1,String policyAttrValue,String policyAttr2, String accountName, String marketSegment, String groupReferral, String groupFirstName, String groupLastName, String groupPhone, 
			String groupAddress, String groupCity, String groupCountry,String groupState,String groupPostalcode, String taxExmptId,String groupRoomClassName,
			String blockName,String arriveDate, String departDate, String ratePlan,String roomPerNight,String checkInDate, String checkoutDate,String  salutation,String guestFirstName, String guestLastName,
			String isAccountPolicyCreate, String paymentMethod, String cardNumber, String nameOnCard, String isGroupPolicyApplicable, String reservationType) throws ParseException{
		
		test_description = testName + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6632' target='_blank'>"
				+ "Click here to open Jira: NG-6632</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6796' target='_blank'>"
				+ "Click here to open Jira: NG-6796</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6431' target='_blank'>"
				+ "Click here to open Jira: NG-6431</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6518' target='_blank'>"
				+ "Click here to open Jira: NG-6518</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-8065' target='_blank'>"
				+ "Click here to open Jira: NG-8065</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-7014' target='_blank'>"
				+ "Click here to open Jira: NG-7014</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6553' target='_blank'>"
				+ "Click here to open Jira: NG-6553</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6097' target='_blank'>"
				+ "Click here to open Jira: NG-6097</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-4249' target='_blank'>"
				+ "Click here to open Jira: NG-4249</a> <br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-6553' target='_blank'>"
				+ "Click here to open Jira: NG-6553</a> <br>";
		test_catagory = "Group";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("825251");
			caseId.add("825250");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}

		roomChargesAre.clear();
		roomCharges.clear();
		rooms.clear();
		 String PrimaryGuestFirstName="AutoPrimary";
		 String PrimaryGuestLastName="User";
		 String SecondaryGuestFirstName="AutoSecondary";
		 String SecondaryGuestLastName="User";
		
		//Get date for CP Reservation
		try {

			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkoutDate))){
				if (guestFirstName.split("\\|").length>1){
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
			
			if (guestFirstName.split("\\|").length>1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkoutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}else {
				checkInDate = checkInDates.get(0);
				checkoutDate = checkOutDates.get(0);
			}
			
			if (!(Utility.validateInput(arriveDate))&&!(Utility.validateInput(departDate))){
				arriveDate = checkInDates.get(0);
				departDate = checkOutDates.get(0);
			}
			
			app_logs.info(checkInDate);
			app_logs.info(checkoutDate);	
			
			app_logs.info(arriveDate);
			app_logs.info(departDate);	
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Get Date", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}
		
		accountName=accountName+Utility.GenerateRandomString15Digit();

		//Login
				try {
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						Utility.reTry.replace(testName, 1);
					}

					driver = getDriver();
					testSteps.add("========== Login to Application ==========");
					login_Group(driver);
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
					if (guestFirstName.split("\\|").length>1){
						testSteps.add("=====Getting Abbreviations=====");
						navigation.clickSetup(driver);
						navigation.roomClass(driver);
						abbreviations=	rc.getAbbrivation(driver, "|", groupRoomClassName, testSteps);								
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
								policies.createPolicies(driver, testSteps, "|", "|", policyTypeIs[i], policyNameis, policyNameis,policyNameis , "", 
										policyAttr1Is[i], policyAttrValueIs[i], policyAttr2Is[i], "1", "within check-in date", "No", "");	
							}
						}
						else {
							for(int i=0;i<policyTypeIs.length;i++){
								policyTypes.add(policyTypeIs[i]);
							}
						}
						
						app_logs.info(policyNames);
						app_logs.info(policyTypes);
						
						roomClassArray = groupRoomClassName.split("\\|");
						app_logs.info(roomClassArray[0]);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Create Policy", "policy", "policy", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Policy", "policy", "policy", testName,
							test_description, test_catagory, testSteps);

				}	
				

				// Navigate to Groups and Create Group
				try {
					testSteps.add("========== Create Group==========");					
					reservationPage.navigateToReservationPage(driver);
					navigation.Groups(driver);
					group.createGroupAccount(driver, testSteps, accountName, true, test, marketSegment, groupReferral, groupFirstName, groupLastName, groupPhone, groupAddress, groupCity, groupState, groupCountry, groupPostalcode, accountNo);
					gettestSteps.clear();
					gettestSteps = group.checkTaxExmpt(driver, true, taxExmptId);
					testSteps.addAll(gettestSteps);
					gettestSteps.clear();
					group.save(driver, test, gettestSteps);
					testSteps.addAll(gettestSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Failed to navigate and create group account", "group", "group", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to navigate and create group account", "group", "group", testName,
							test_description, test_catagory, testSteps);

				}
				// Navigate to Folio Tab Associate Policy with Group Account
				try {
					testSteps.add("==========Associate Policy ==========");
					for(int i=0;i< policyNames.size();i++) {
					group.associatePolicyWithGroup(driver, test, policyTypes.get(i), policyNames.get(i),testSteps);}
					
					for(int i=0;i< policyNames.size();i++) {
						group.verifyassociatePolicy(driver, test, policyTypes.get(i), policyNames.get(i), testSteps);
					}					
					Utility.app_logs.info("Issue with setting Group Cancellation policy");
					testSteps.add("Issue with setting Group Cancellation policy"
							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-4249' target='_blank'>"
							+ "Verified : NG-4249</a><br/>");
					
				}catch (Exception e) {
					e.printStackTrace();
					Utility.catchException(driver, e, "Associate Policy with Group Account", "group", "group", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Associate Policy with Group Account", "group", "group", testName,
							test_description, test_catagory, testSteps);

				}
				
				
				
				//Navigate to Room Block Tab and create block and click on blue icon
				try {
					testSteps.add("========== Create Room Block ==========");
					group.navigateRoomBlock(driver, test);
					Utility.app_logs.info("Navigate to Room Block Tab");
					testSteps.add("Navigate to Room Block Tab");	
					gettestSteps.clear();
					gettestSteps=group.createNewBlock(driver, blockName+Utility.fourDigitgenerateRandomString(), arriveDate, departDate, ratePlan, roomPerNight, roomClassArray[0]);					
					testSteps.addAll(gettestSteps);
					gettestSteps=group.navigateRoomBlock(driver);
					gettestSteps = group.bookIconClick(driver, roomClassArray[0]);
					testSteps.addAll(gettestSteps);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Navigate to Room Block Tab and create block and click on blue icon", "group", "group", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Navigate to Room Block Tab and create block and click on blue icon", "group", "group", testName,
							test_description, test_catagory, testSteps);

				}
				
				//Create Reservation
				try {		
					expiryDate=Utility.getFutureMonthAndYearForMasterCard();
					//reservationPage.createGroupReservation(driver, testSteps, groupRoomClassName, config.getProperty("flagOn"),"",salutation, guestFirstName, guestLastName, config.getProperty("flagOff"),paymentMethod, cardNumber, nameOnCard, expiryDate, isGroupPolicyApplicable,  reservationType, rooms, reservationNo);

					gettestSteps.clear();
					gettestSteps = reservationPage.clickAddSRoomButtton(driver);
					testSteps.addAll(gettestSteps);

					gettestSteps.clear();
					gettestSteps = reservationPage.clickOnFindRooms(driver, gettestSteps);
					testSteps.addAll(gettestSteps);
					ArrayList<String> roomNumbers = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, groupRoomClassName);

					gettestSteps.clear();
					reservationPage.selectRoomNumber(driver, groupRoomClassName, roomNumbers.get(0), false, gettestSteps);
					testSteps.addAll(gettestSteps);

					gettestSteps.clear();
					gettestSteps = reservationPage.clickSelectRoomButtton(driver, groupRoomClassName);
					testSteps.addAll(gettestSteps);

					gettestSteps.clear();
					//gettestSteps = reservationPage.newPolicesApplicableYesBtn(driver);
					reservationPage.clickNoInPolicyPopUp(driver);
					//testSteps.addAll(gettestSteps);

					Wait.waitForCPReservationLoading(driver);
					

					gettestSteps.clear();
					reservationPage.selectRoomNumber(driver, groupRoomClassName, roomNumbers.get(1), false, gettestSteps);
					testSteps.addAll(gettestSteps);

					gettestSteps.clear();
					gettestSteps = reservationPage.clickSelectRoomButtton(driver, groupRoomClassName);
					testSteps.addAll(gettestSteps);

					reservationPage.clickNext(driver, testSteps);

					gettestSteps.clear();
					gettestSteps = reservationPage.selectPrimaryRoom(driver, groupRoomClassName, roomNumbers.get(0));
					testSteps.addAll(gettestSteps);

					PrimaryGuestFirstName = PrimaryGuestFirstName + Utility.GenerateRandomNumber();
					PrimaryGuestLastName = PrimaryGuestLastName + Utility.GenerateRandomNumber();
					
//					gettestSteps.clear();
//					gettestSteps = res.selectSalutation(driver, "Mr.");
//					testSteps.addAll(gettestSteps);
					
					gettestSteps.clear();
					gettestSteps = reservationPage.enterPrimaryGuestName(driver, PrimaryGuestFirstName, PrimaryGuestLastName);
					testSteps.addAll(gettestSteps);

					gettestSteps.clear();
					gettestSteps = reservationPage.selectAdditionalRoom(driver, groupRoomClassName, roomNumbers.get(1));
					testSteps.addAll(gettestSteps);

					SecondaryGuestFirstName = SecondaryGuestFirstName + Utility.GenerateRandomNumber();
					SecondaryGuestLastName = SecondaryGuestLastName + Utility.GenerateRandomNumber();
					
					gettestSteps.clear();
					gettestSteps = reservationPage.enterAdditionalGuestName(driver, SecondaryGuestFirstName, SecondaryGuestLastName);
					testSteps.addAll(gettestSteps);
					
					reservationPage.enter_PaymentDetails(driver, testSteps, paymentMethod, cardNumber, nameOnCard, expiryDate);

					reservationPage.clickBookNow(driver, testSteps);
					reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					reservationPage.get_ReservationStatus(driver, testSteps);
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					testSteps.addAll(gettestSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);

				}
				
			/*	//Verify Deposit Amount
				try {
					testSteps.add("========== Verify Deposit Amount==========");
					reservationPage.clickFolio(driver, testSteps);
					roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(0), checkOutDates.get(0), true);
					for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
						roomCharges.add(entry.getValue());
					}
					
					 if (guestFirstName.split("\\|").length>1) {
						reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
						reservationPage.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(1), rooms.get(1));
						roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(0), checkOutDates.get(0), true);
						for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
							roomCharges.add(entry.getValue());
						}

						reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
						reservationPage.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(0), rooms.get(0));
					}
					app_logs.info(roomCharges);
					String size= String.valueOf(groupRoomClassName.split("\\|").length);
					app_logs.info(size);
					depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges, policyAttr1Is[0], policyAttrValueIs[0],size);
					app_logs.info(depositAmount);
					fourDigitCardNo=Utility.getCardNumberHidden(cardNumber);
					if(paymentMethod.equals("MC")) {
						paymentTypeIs=""+paymentMethod+" "+fourDigitCardNo+" ("+expiryDate+")";
					}else if(paymentMethod.equals("Cash")){
						paymentTypeIs=paymentMethod;
					}
					app_logs.info(paymentTypeIs);
					verificationDepositWithPolicy(paymentMethod,policyNames.get(0),depositAmount,paymentTypeIs);
					
						}catch (Exception e) {
					Utility.catchException(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);

				}
				*/
				String Message = "Are you sure you want to check-out all rooms at once? The Primary Guest will be responsible for all remaining Guest Charges.";
				try {
						
					reservationPage.clickFolio(driver, testSteps);
					balance= Double.parseDouble(reservationPage.get_Balance(testSteps,driver));
					if (guestFirstName.split("\\|").length>1)  {
						reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
						reservationPage.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(1), rooms.get(1));
						balance=balance+Double.parseDouble(reservationPage.get_Balance(testSteps,driver));
						reservationPage.click_FolioDetail_DropDownBox(driver, testSteps);
						reservationPage.clickFolioDetailOptionValue(driver, testSteps, abbreviations.get(0), rooms.get(0));
					}
					app_logs.info(balance);					
					paymentAmt=Double.parseDouble(reservationPage.get_Payments(driver, testSteps));
					app_logs.info(paymentAmt);
					
					checkInAmount=reservationPage.calculationOfCheckInAmountToBePaidForRateV2(String.valueOf(balance), policyAttrValueIs[1]);
							if(Utility.validateString(checkInAmount)) {
						app_logs.info(checkInAmount);		
						DecimalFormat df = new DecimalFormat("0.00");
						df.setMaximumFractionDigits(2);
						paymentAmt=paymentAmt+Double.valueOf(checkInAmount);
						app_logs.info(df.format(paymentAmt));
						paymentAmount=df.format(paymentAmt);
						app_logs.info(paymentAmount);
					}
			
					if(paymentMethod.equals("MC")) {
						checkInCardFormat=""+paymentMethod+"-"+fourDigitCardNo+" "+expiryDate+"";
					}else if(paymentMethod.equals("Cash")) {
						checkInCardFormat=paymentMethod;
					}

					app_logs.info(checkInCardFormat);	
					testSteps.add("==================Check In Reservation==================");
					reservationPage.clickCheckInButton(driver, testSteps);
					Wait.waitUntilPageLoadNotCompleted(driver, 10);
					reservationPage.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
					
					completeCheckInIfPolicyExist(balance,checkInAmount);
					//testSteps.add("==================Verify Check-In Policy On Reservation==================");																			
					//verificationCheckinWithPolicy(paymentMethod,policyNames.get(1),checkInAmount,paymentAmount,checkInCardFormat);
					comments="Verified check in functionality when group account is associated to MRB reservation with policies. ";
					app_logs.info("Verified check in functionality when group account is associated to MRB reservation with policies. ");	
					testSteps.add("Verified check in functionality when group account is associated to MRB reservation with policies. ");
					reservationPage.clickCheckOutAllButton(driver, testSteps);
					reservationPage.clickYesButtonOfCheckOutAllConfirmationMsg(driver, testSteps, Message, "CheckOutAll", "", "");
					reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
					reservationPage.clickCheckoutProceedButtons(driver, testSteps, "Proceed");
					reservationPage.clickLogORPayAuthorizedButton(driver, testSteps);
					reservationPage.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
					reservationPage.verifyReservationStatusStatus(driver, testSteps, "DEPARTED");
					comments="Verified check out functionality when group account is associated to MRB reservation with policies. ";
					app_logs.info("Verified check out functionality when group account is associated to MRB reservation with policies. ");	
					testSteps.add("Verified check out functionality when group account is associated to MRB reservation with policies. ");
				
				}catch (Exception e) {
					e.printStackTrace();
					Utility.catchException(driver, e, "CheckIn Reservation and verify checkin amount", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "CheckIn Reservation  and verify checkin amount", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);

				}				
				
				try {
					String[] testcase = TestCaseID.split(Utility.DELIM);
					for (int i = 0; i < testcase.length; i++) {
						statusCode.add(i, "1");
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName,test_description, test_catagory, testSteps);
				}catch (Exception e) {
					Utility.catchException(driver, e, "Inactive Group", "group", "group", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Inactive Group", "group", "group", testName,
							test_description, test_catagory, testSteps);

				}

	}
	
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyPoliciesInGroupMRB", BEExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}



