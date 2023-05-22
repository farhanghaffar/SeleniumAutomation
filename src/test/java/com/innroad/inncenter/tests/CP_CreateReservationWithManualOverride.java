package com.innroad.inncenter.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CP_CreateReservationWithManualOverride extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String date = null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	//785572	
	String reservation=null;	
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, SanityExcel))
			throw new SkipException("Skipping the test - " + testName);
	}



	@Test(dataProvider = "getData", groups = "Reservation")
	public void CP_CreateReservation(String TestCaseID,String url, String ClientCode, String Username, String Password,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String RateAmount,String PromoCode,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus) throws Exception {
		boolean isExecutable=Utility.getResultForCase(driver, TestCaseID);
		if(isExecutable) {
		//TestCaseID="848261|848652|848666";
		Utility.initializeTestCase(TestCaseID, caseId, statusCode, statusCode,"");


		test_name = "CP Reservation Creation with manual override";
		test_description = "CP Reservation Creation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/785572' target='_blank'>"
				+ "Click here to open TestRail: C785572</a>";
		test_catagory = "CPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage res = new CPReservationPage();

		Double depositAmount=0.0;
		Double paidDeposit=0.0;
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

			// Get CheckIn, CheckOut and TaskDueOn Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					try {
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					}catch(Exception e) {
						e.printStackTrace();
					}
					System.out.println(checkInDates);
					System.out.println(checkOutDates);
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			CheckInDate = checkInDates.get(0);
			CheckOutDate = checkOutDates.get(0);
			TaskDueon = CheckOutDate;
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			date=Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
			app_logs.info(date);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
			} else {
				e.printStackTrace();
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName, "ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Login
		try {

			driver = getDriver();
			login_Group(driver);
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
			Wait.wait10Second();
			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_RateplanWithManualOverRide(driver, test_steps, Rateplan,RateAmount,PromoCode);
			res.clickSearch(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, IsAssign,Account);
			depositAmount=res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			res.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,PhoneNumber,AltenativePhone,Email,Account,AccountType,Address1,Address2,Address3,City,Country,State,PostalCode,IsGuesProfile);
			if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.verify_NotesSections(driver, test_steps);
			boolean falg=res.verify_TaskSections(driver, test_steps);
			res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject,Description);
			//			if(falg) {
			//				res.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType, TaskDetails, TaskRemarks, TaskDueon, TaskAssignee, TaskStatus);
			//			}
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
			boolean rateplanflag=res.verifyManualOverrideRateplanInReservation(driver, test_steps, Rateplan);
			if(rateplanflag) {
				statusCode.add(2, "1");	
				comments=comments+" Verified Manual override Rate plan in details page";
			}else {
				comments=comments+" Failed to Verifiy Manual override Rate plan in details page";
			}
			
			
			res.click_Folio(driver, test_steps);
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

			if(depositAmount>0) {
				if(Double.compare(paidDeposit, depositAmount)==0) {
					test_steps.add("Deposit paid amount is validated : "+ch+" "+paidDeposit);
					app_logs.info("Deposit paid amount is validated : "+ch+" "+paidDeposit);
				}
			}
			comments="Created rservation with "+reservation+ " and verified reservation folio , trip summary , payments and history after reservation creation.";
			
			boolean flag=res.verifyManualOverrideAmountInReservationFolio(driver, test_steps, RateAmount);
			if(flag) {
				statusCode.add(1, "1");	
				comments=comments+" Verified Manual override amount in the folio room charges";
			}else {
				comments=comments+" Failed to Verifity Manual override amount in the folio room charges";
			}
			
			res.click_DeatilsTab(driver, test_steps);
			
			res.get_RatePlan(driver, test_steps);
			
			res.click_History(driver, test_steps);
			res.verify_ReservationInHistoryTab(driver, test_steps, reservation);
			if(depositAmount>0) {
				res.verifyDepositPaymentInHistoryTab(driver, test_steps, depositAmount);
			}	
			res.velidate_TripSummaryAndFolio(driver, test_steps, FilioRoomCharges, FilioTaxes, FilioIncidentals, FilioTripTotal, payment, FilioBalance, TripSummaryRoomCharges, TripSummaryTaxes, TripSummaryIncidentals, TripSummaryTripTotal, TripSummaryPaid, TripSummaryBalance);
			res.verify_GuestReportLabelsValidations(driver, test_steps);
			
			String[] testcase1 = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase1.length; i++) {
				statusCode.add(i, "1");
			}
			
			//Utility.testCasePass(statusCode,0);
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
	}


	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateReservation_ManualOverrid", SanityExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
	//	driver.close();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
