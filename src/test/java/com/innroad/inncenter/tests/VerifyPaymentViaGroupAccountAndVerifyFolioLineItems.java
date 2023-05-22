package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPaymentViaGroupAccountAndVerifyFolioLineItems extends TestCore {

	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();
	ReservationSearch searchReservation = new ReservationSearch();
	GuestFolio guestFolio = new GuestFolio();
	FolioNew folioNew = new FolioNew();
	MerchantServices msObj = new MerchantServices();
	String seasonStartDate = null, seasonEndDate = null;
	ArrayList<String> typeList = new ArrayList<String>();
	boolean isMovedToFolio = false;
    String groupAccountNo;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, SNExcel))
			throw new SkipException("Skiping the test - " + test_name);
	}

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	
	@Test(dataProvider = "getData")
	public void verifyPaymentViaGroupAccountAndVerifyFolioLineItems(String TestCaseId, String TestCaseName,
			String payGatewayAccountName, String accountStatus, String isCardPresent, String isCardNotPresent,
			String isEcommerce, String isRequireCVVForAll, String paymentGateway, String mode, String accountID,
			String subAccountId, String merchantPin, String storeId, String url, String tokenId,
			String transactionKeyCode, String associateProperties, String AccountName, String MargetSegment,
			String Referral, String AccountFirstName, String AccountLastName, String Phonenumber, String Address1,
			String city, String Country, String State, String Postalcode, String PaymentMethod, String RoomClassName,
			String PayAmount, String checkInDate, String checkOutDate, String guestFirstName, String guestLastName,
			String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard,
			String marketSegment, String referral, String TakePaymentType, String IsChangeInPayAmount, String amount,String paymentType)
			throws Exception {
		 if(Utility.getResultForCase(driver, TestCaseId)) {

		Utility.initializeTestCase(TestCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
		String last4Digit= Utility.getCardNumberHidden(CardNumber);
		//String groupAccountNo = "";
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		String paymentMethods[] = PaymentMethod.split("\\|");

		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
				int day = Utility.getStayDays(checkInDate, checkOutDate);
				sessionEndDate
						.add(Utility.parseDate(Utility.getDatePast_FutureDate(day + 2), "MM/dd/yyyy", "dd/MM/yyyy"));
			}

			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);

			} else {
				// Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
				Utility.reTry.replace(test_name, 1);
				System.out.println(Utility.reTry.get(test_name));
			}
			driver = getDriver();
			HS_login(driver, envURL, Utility.generateLoginCreds(SNExcel, "ResV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		}
		
		  try {
		  
		  // String payGatewayAccountName = paymentGateway + "_Gateway";
		  navigation.setup(driver); msObj.navigatetoMerchantServices(driver,
		  test_steps); test_steps.add("Creating Payment Gateway : " + paymentGateway);
		  app_logs.info("Creating Payment Gateway : " + paymentGateway); 
		  msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
		  msObj.createNewMerchantService(driver, test_steps, payGatewayAccountName, "",
		  "", accountStatus, isCardPresent, isCardNotPresent, isEcommerce,
		  isRequireCVVForAll, paymentGateway, mode, accountID, subAccountId,
		  merchantPin, storeId, url, tokenId, "", "", "", "", "", transactionKeyCode,
		  associateProperties);
		  
		  }catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			}
		 
		

		try {
			 
			app_logs.info("====================== Creating Group  ==================");
			ArrayList<String> accountNumbers = new ArrayList<>();
			test_steps.add("====================== Creating Group ==================");
			app_logs.info("====================== Creating Group ==================");
			navigation.ReservationV2_Backward(driver);
			navigation.Groups(driver);
			group.createGroupAccount(driver, test_steps, AccountName, true, null, marketSegment, Referral,
					AccountFirstName, AccountLastName, Phonenumber, Address1, city, State, Country, Postalcode,
					accountNumbers);
			group.clickOnSave(driver);
			 groupAccountNo = group.getAccountNo(driver);

			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		}
		try {
			group.click_GroupNewReservation(driver, test_steps);
			test_steps.add("====================== Creating Group Reservation ==================");
			app_logs.info("====================== Creating Group Reservation ==================");
			res.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, rateplan, "");
			res.clickOnFindRooms(driver, test_steps);
			ArrayList<String> roomNumbers = new ArrayList<>();
			roomNumbers = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClassName);
			res.selectRoomToReserve(driver, test_steps, RoomClassName, roomNumbers.get(0));
			res.clickNext(driver, test_steps);
			// resV2.clickOnCreateGuestButton(driver, test_steps);
			res.turnOnOffcreateGuestProfileToggle(driver, test_steps, false);
			res.enter_GuestName(driver, test_steps, Salutation, guestFirstName, guestLastName, false);
			res.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			res.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, Referral, "Yes");
			res.clickBookNow(driver, test_steps, false);
			res.clickCloseReservationSavePopup(driver, test_steps);
            
			Wait.wait10Second();
             
		}catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
	 }
			

			try{
      
				test_steps.add("Different Payment Methods : "+PaymentMethod);
				for(int i=0;i<2;i++) 
				{
					test_steps.add("Payment Method : "+paymentMethods[i]);
			res.click_Folio(driver, test_steps);
			res.clickFolioPayButton(driver, test_steps);
			folioNew.makePayment(driver, test_steps, amount,paymentMethods[i],"" , "",  CardNumber,  NameOnCard,  CardExpDate, "");
			folioNew.verifyFolioLineItems(driver, test_steps, checkInDate, paymentMethods[i], amount, NameOnCard, "",
					"");
			res.click_History(driver, test_steps);
			String amount1 = Utility.convertDecimalFormat(amount);
			String paymentStr = paymentMethods[i] ;
			String str = "Made a payment" + amount1 + " using (" + paymentStr + ")";
			test_steps.add(str);
			app_logs.info(str);
			res.enterTextToSearchHistory(driver, "Captured payment for $" + amount1, true,
					ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			
				}
				String grpAccountNamenNumber =AccountName+" ("+groupAccountNo+")";

				res.click_Folio(driver, test_steps);
				res.clickFolioPayButton(driver, test_steps);
				folioNew.makePayment(driver, test_steps, amount,paymentType,grpAccountNamenNumber , "",  CardNumber,  NameOnCard,  CardExpDate, "");
				String amount1 = Utility.convertDecimalFormat(amount);
				amount1 = "$" + amount + "";
				folioNew.verifyFolioLineItems(driver, test_steps,checkInDate , 
						"Account (Group)",grpAccountNamenNumber, amount, "", "", "", "","");
				res.click_History(driver, test_steps);

				String paymentStr = "Account - "+AccountName+" ("+groupAccountNo+")" ;
				String str = "Made a payment" + amount1+"using("+ paymentStr +")";
				test_steps.add(str);
				app_logs.info(str);
				res.enterTextToSearchHistory(driver, str , true,
						ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		}
			try {
				for (int i = 0; i < Utility.testId.size(); i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "VerifyGroupAccPayment");
				}
				Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
						Utility.comments, TestCore.TestRail_AssignToID);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
						test_catagory, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
						test_catagory, test_steps);

			}

		 }
		}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyGroupAccPayment", SNExcel);
	}
}
