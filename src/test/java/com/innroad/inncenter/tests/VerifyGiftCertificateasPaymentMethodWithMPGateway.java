package com.innroad.inncenter.tests;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
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
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyGiftCertificateasPaymentMethodWithMPGateway extends TestCore {
	
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
	String seasonStartDate = null, seasonEndDate = null;
	String reservationNo;
	Users user = new Users();
	MerchantServices msObj = new MerchantServices();
    String AccNo = "965761310120822";
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, SNExcel))
			throw new SkipException("Skiping the test - " + test_name);
	}

	@Test(dataProvider = "getData")
	public void verifyGiftCertificateasPaymentMethodWithMPGateway(String TestCaseId, String TestCaseName,
			String payGatewayAccountName, String accountStatus, String isCardPresent, String isCardNotPresent,
			String isEcommerce, String isRequireCVVForAll, String paymentGateway, String mode,String accountID,String subAccountId,String merchantPin,String storeId,String url, String tokenId, String username, String password, String restAPIKey, String hituserId, String hitKey, String transactionKeyCode, String associateProperties, String checkInDate,
			String checkOutDate, String adults, String children, String rateplan, String roomClass, String rate,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String amount, String TakePaymentType,
			String IsChangeInPayAmount, String IsAddNotes,String NoteType,String NoteSubject,String NoteDescription) throws Exception {
		 //if(Utility.getResultForCase(driver, TestCaseId)) {
		Utility.initializeTestCase(TestCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = this.getClass().getSimpleName().trim();
		test_description = TestCaseId + " : " + TestCaseName;
		test_catagory = "ReservationV2";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
		app_logs.info("##################################################################################");
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> userList = new ArrayList<>();

		
		String last4Digit = Utility.getCardNumberHidden(cardNumber);
		String CardExpDate1 = Utility.getFutureMonthAndYearForMasterCard();
		ArrayList<String> typeList = new ArrayList<String>();
		typeList.add("Authorization Only");
		typeList.add("Capture");
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
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
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
			navigation.setup(driver);
			msObj.navigatetoMerchantServices(driver, test_steps);
			test_steps.add("Creating Payment Gateway : " + Utility.splitInputData(paymentGateway).get(0));
			app_logs.info("Creating Payment Gateway : " + Utility.splitInputData(paymentGateway).get(0));
			// Verify whether any Active Gateway exists or not, if it exists then delete and
			// create new one
			msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
			msObj.createNewMerchantService(driver, test_steps,Utility.splitInputData(payGatewayAccountName).get(0) , "", "", accountStatus,
					isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, Utility.splitInputData(paymentGateway).get(0),
					mode, accountID, subAccountId, merchantPin,
					storeId, url, tokenId, "", "",
					"", "", "", transactionKeyCode, associateProperties);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "ReservationV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		HashMap<String, String> data = null;
		try {
			 navigation.admin(driver);
			    navigation.Users(driver);
			    userList=  user.getAllUsersList(driver, test_steps);
			navigation.ReservationV2_Backward(driver);
			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
			data = res.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate,
					adults, children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, "", "",
					"", "", "", "", "", "", "", "", false, marketSegment, referral, "", "", "",
					"", 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "");
			reservationNo = data.get("ReservationNumber");
			res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, NoteSubject, NoteDescription);
			reservationPage.verify_Notes(driver, test_steps, NoteType, NoteSubject, NoteDescription, userList.get(0), 1);
			res.deleteNote(driver, test_steps, NoteType);
			res.click_History(driver, test_steps);
			String str = "	Removed note "+ NoteSubject ;
			test_steps.add(str);
			app_logs.info(str);
			res.enterTextToSearchHistory(driver, str, true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			app_logs.info("******************* Successfully Verified History For Deleting Note **********************");
			
        
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}
 
		//GC
		try {
			String Desc=" Gift-SanaGift #1695314195(AccNo)";
			res.click_Folio(driver, test_steps);
			String amount1 = Utility.convertDecimalFormat(amount);
			amount1 = "$" + amount1 + "";
			guestFolio.takePaymentFromGuestFolio_RV2(driver, test_steps,
			TakePaymentType, amount,  Utility.splitInputData(paymentType).get(0), Utility.splitInputData(cardNumber).get(0), nameOnCard);
			folioNew.verifyFolioLineItems(driver, test_steps,checkInDate , 
					 Utility.splitInputData(paymentType).get(0),Desc, amount, "", "", "", "","");
			app_logs.info("******************* Successfully Verified Folio **********************");
			res.click_History(driver, test_steps);
			String paymentStr = "" + Utility.splitInputData(paymentType).get(0) + "-" + last4Digit + " "
					+ Utility.parseDate(yearDate, "MM/yy", "MM/yy");
			String str = "Captured payment for " + amount1 + " using (" + paymentStr + ")";
			test_steps.add(str);
			app_logs.info(str);
			res.enterTextToSearchHistory(driver, str, true, ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			app_logs.info("******************* Successfully Verified History **********************");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
					test_catagory, test_steps);
		}
		
		try {
			navigation.setup(driver);
			msObj.navigatetoMerchantServices(driver, test_steps);
			test_steps.add("Creating Payment Gateway : " + Utility.splitInputData(paymentGateway).get(1));
			app_logs.info("Creating Payment Gateway : " + Utility.splitInputData(paymentGateway).get(1));
			// Verify whether any Active Gateway exists or not, if it exists then delete and
			// create new one
			msObj.deleteActivePaymentGatewayifExists(driver, test_steps);
			msObj.createNewMerchantService(driver, test_steps, Utility.splitInputData(payGatewayAccountName).get(1), "", "", accountStatus,
					isCardPresent, isCardNotPresent, isEcommerce, isRequireCVVForAll, Utility.splitInputData(paymentGateway).get(1), mode, "", "",
					"", "", "", "", username, password, restAPIKey, hituserId, hitKey, transactionKeyCode,
					associateProperties);
			navigation.ReservationV2_Backward(driver);
			HashMap<String, String> data1 = null;
			
				String roomClassForReservation =roomClass.split("\\|")[0];
				roomClassForReservation=roomClassForReservation+"|"+roomClassForReservation;
				guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
				guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
				
				String checkInDate1 = Utility.getCurrentDate("dd/MM/yyyy")+"|"+Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1);
				String checkOutDate1 = Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 1)+"|"+Utility.getCustomDate(seasonStartDate, "dd/MM/yyyy", "dd/MM/yyyy", 3);
		
				data1 = res.createReservation(driver, test_steps, "From Reservations page", checkInDate1, checkOutDate1,
						adults+"|"+adults, children+"|"+children, rateplan+"|"+rateplan, "", roomClass+"|"+roomClass, "", salutation, guestFirstName, guestLastName, "", "",
						"", "", "", "", "", "", "", "", false, marketSegment, referral, Utility.splitInputData(paymentType).get(1), Utility.splitInputData(cardNumber).get(1), nameOnCard,
						yearDate, 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, ""); 
				reservationNo=data1.get("ReservationNumber");
				res.click_Folio(driver, test_steps);
				folioNew.clickRefundButtonFromFolioTab(driver, test_steps);
				folioNew.ClickManualCardCCButton(driver, test_steps);
				folioNew.refundPaymentinGuestFolio(driver, test_steps, amount,Utility.splitInputData(paymentType).get(1), Utility.splitInputData(cardNumber).get(1), nameOnCard,
						yearDate, "");
				String last4Digit1 = Utility.getCardNumberHidden(Utility.splitInputData(cardNumber).get(1));
				amount = Utility.convertDecimalFormat(amount);
				folioNew.verifyFolioLineItems(driver, test_steps, checkInDate, Utility.splitInputData(paymentType).get(1), amount, nameOnCard, last4Digit1,
						yearDate);
				app_logs.info("******************* Successfully Verified Folio **********************");
				res.click_History(driver, test_steps);
				String amount1 = Utility.convertDecimalFormat(amount);
				String paymentStr=""+Utility.splitInputData(paymentType).get(1)+"-"+last4Digit+" "+Utility.parseDate(yearDate, "MM/yy", "MM/yyyy");
				String str = "Refunded payment for $" + amount1 + " using (" + paymentStr + ")";
				test_steps.add(str);
				app_logs.info(str);
				res.enterTextToSearchHistory(driver, str, true,ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
				app_logs.info("******************* Successfully Verified History **********************");

		}catch (Error e) {
			Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
					test_catagory, test_steps);
	 }
			 try {
					for (int i = 0; i < Utility.testId.size(); i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "MPgatewayGCPayment");
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


	@DataProvider
	public Object[][] getData() {
		return Utility.getData("MPgatewayGCPayment", SNExcel);
}
}

