package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyDepositPolicyWithFirstNightsOfRoomCharges extends TestCore{

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void VerifyDepositPolicyWithFirstNightsOfRoomCharges(String url, String ClientCode, String Username, String Password,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus,
			String PolicyName,String PolicyType,String Chargestype,String Number,String PolicyText,String PolicyDesc,String Season) throws InterruptedException, IOException {

		test_name = "VerifyDepositPolicyWithFirstNightsOfRoomCharges";
		test_description = "DepositPolicyFor1stNightNightsRoomCharges<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682502' target='_blank'>"
				+ "Click here to open TestRail: C682502</a>";
		test_catagory = "VerifyDepositPolicyWithFirstNightsOfRoomCharges";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage res = new CPReservationPage();
		Policies pol = new Policies();
		Navigation nav = new Navigation();

		Double depositAmount=0.0;
		Double paidDeposit=0.0;
		String reservation=null;
		String status=null;

		String TripSummaryRoomCharges=null,TripSummaryTaxes=null,TripSummaryIncidentals=null,TripSummaryTripTotal=null,TripSummaryPaid=null,TripSummaryBalance=null;
		String FilioRoomCharges=null,FilioTaxes=null,FilioIncidentals=null,FilioTripTotal=null,FilioPaid=null,FilioBalance=null;
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
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

		// Reservation
		try {
			test_steps.add("******************* Creating policy **********************");
			app_logs.info("******************* Creating policy **********************");
			nav.Inventory(driver);
			nav.policies(driver);
			pol.delete_Policies(driver, PolicyType);
			app_logs.info("deleted all exisitng deposit policies");
			test_steps.add("deleted all exisitng deposit policies");
			pol.ClickNewPolicybutton(driver);
			PolicyName=PolicyName+"_"+Utility.generateRandomString();
			pol.Enter_Policy_Name(driver, PolicyName,test_steps);
			pol.Enter_Policy_Type(driver, PolicyType);
			pol.Deposit_Policy_Attributes(driver, Chargestype, Number, test_steps);
			pol.Enter_Policy_Desc(driver, PolicyText, PolicyDesc);
			pol.Associate_Sources(driver);
			pol.Associate_Seasons(driver, Season);

			pol.Associate_RoomClasses(driver, RoomClass);
			app_logs.info("Associating Room Class to policy : "+RoomClass);
			test_steps.add("Associating Room Class to policy : "+RoomClass);
			pol.Associate_RatePlan(driver, Rateplan);
			app_logs.info("Associating Rateplan to policy : "+Rateplan);
			test_steps.add("Associating Rateplan to policy : "+Rateplan);
			pol.Save_Policy(driver);
			pol.Close_Policy_Tab(driver);
			test_steps.add("Created Policy : "+PolicyName);
			app_logs.info("Created Policy : "+PolicyName);
			nav.Reservation_Backward_1(driver);
			test_steps.add("******************* Creating reservation **********************");
			app_logs.info("******************* Creating reservation **********************");
			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan,PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, IsAssign,Account);
			depositAmount=res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

			res.verifyDepositAmountTripSummary(driver, test_steps, CheckInDate, CheckOutDate, Number, depositAmount);
			res.clickNext(driver, test_steps);

			res.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,PhoneNumber,AltenativePhone,Email,Account,AccountType,Address1,Address2,Address3,City,Country,State,PostalCode,IsGuesProfile);
			if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.verify_NotesSections(driver, test_steps);
			boolean falg=res.verify_TaskSections(driver, test_steps);
			res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject,Description);
			if(falg) {
				res.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType, TaskDetails, TaskRemarks, TaskDueon, TaskAssignee, TaskStatus);
			}
			res.clickBookNow(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			status=res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.get_RoomNumber(driver, test_steps, IsAssign);	
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			TripSummaryTaxes=res.get_TripSummaryTaxesWithCurrency(driver, test_steps);
			TripSummaryIncidentals=res.get_TripSummaryInceidentalsWithCurrency(driver, test_steps);
			TripSummaryTripTotal=res.getTripSummaryTripTotal(driver, test_steps);
			TripSummaryPaid=res.get_TripSummaryPaidWithCurrency(driver, test_steps);
			TripSummaryBalance=res.get_TripSummaryBalanceWithCurrency(driver, test_steps);
			res.verify_StayInfo(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, RoomClass, TripSummaryRoomCharges);
			res.validate_GuestInfo(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, AltenativePhone, Email, Country, Account, Address1, Address2, Address3, State, City, PostalCode);
			res.get_AssociatedPoliciesToReservation(driver, test_steps);
			res.verify_DepositPolicyAssociated(driver, test_steps, PolicyName);
			res.click_Folio(driver, test_steps);
			Double RC=Double.parseDouble(res.get_RoomCharge(driver, test_steps));
			FilioRoomCharges=res.get_RoomChargeWithCurrency(driver, test_steps);
			FilioIncidentals=res.get_InceidentalsWithCurrency(driver, test_steps);
			FilioTaxes=res.get_TaxesWithCurrency(driver, test_steps);
			FilioTripTotal= res.get_TotalChargesWithCurrency(driver, test_steps);
			FilioPaid=res.get_PaymentsWithCurrency(driver, test_steps);
			FilioBalance=res.get_BalanceWithCurrency(driver, test_steps);
			res.verify_BannerDetails(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, Email, FilioTripTotal, FilioBalance, reservation, status, CheckInDate, CheckOutDate, Country);
			String payment=FilioPaid;
			FilioPaid=FilioPaid.trim();
			char ch=FilioPaid.charAt(0);
			FilioPaid=FilioPaid.replace("$", "");
			FilioPaid=FilioPaid.trim();
			paidDeposit = Double.parseDouble(FilioPaid);
			res.validate_DepositAmount(driver, test_steps, Number, paidDeposit, RC);
			if(depositAmount>0) {
				if(Double.compare(paidDeposit, depositAmount)==0) {
					test_steps.add("Deposit paid amount is validated : "+ch+" "+paidDeposit);
					app_logs.info("Deposit paid amount is validated : "+ch+" "+paidDeposit);
				}
			}
			res.verify_DepositPaymentLineItem(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate, FilioPaid, "");
			res.click_History(driver, test_steps);
			res.verify_ReservationInHistoryTab(driver, test_steps, reservation);
			if(depositAmount>0) {
				res.verifyDepositPaymentInHistoryTab(driver, test_steps, depositAmount);
			}	
			res.velidate_TripSummaryAndFolio(driver, test_steps, FilioRoomCharges, FilioTaxes, FilioIncidentals, FilioTripTotal, payment, FilioBalance, TripSummaryRoomCharges, TripSummaryTaxes, TripSummaryIncidentals, TripSummaryTripTotal, TripSummaryPaid, TripSummaryBalance);
			res.verify_GuestReportLabelsValidations(driver, test_steps);
			res.click_Folio(driver, test_steps);
			res.verify_DepositPolicyValidation(driver, test_steps, CheckInDate, CheckOutDate,Number);
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Verify_DepPolicyWithRoomCharges", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
	}

}
