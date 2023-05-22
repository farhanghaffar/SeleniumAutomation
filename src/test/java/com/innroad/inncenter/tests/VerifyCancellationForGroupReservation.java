package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyCancellationForGroupReservation extends TestCore{
	private WebDriver driver;

	public static String test_description = "";
	public static String test_category = "";
	public static String test_name = "";
	public static ArrayList<String> test_steps = new ArrayList<>();
	public static ArrayList<String> getTest_Steps = new ArrayList<>();

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	String reservation=null;	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyTaxExemptInCPReservation(String TestCaseID,String url, String ClientCode, String Username, String Password,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus) throws Exception {

		TestCaseID="825148";

		caseId.add("825148");
		statusCode.add("5");

		test_name = "GroupReservationCancellation";
		test_description = "Verify Tax Exempt In CP Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682449' target='_blank'>"
				+ "Click here to open TestRail: 682449</a>";
		test_category = "TaxExemptInCPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		RoomClass roomClass = new RoomClass();

		AdvGroups grps= new AdvGroups();

		Rate rate = new Rate();
		Tax tax = new Tax();

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();

		String randomString = "";
		String taxName = "";
		String roomClassName = "";
		String rcDelete = "";
		String rateDelete = "";
		String rateName = "";
		String deleteTaxesName = "";
		try {
			test_steps.add("<b> ************Logging in to the Application</b>****************");
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
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
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

		try {
			test_steps.add("<b>*****************Creating group reservation</b>***************************");
			nav.groups(driver);
			String accName= "AutoGrp"+Utility.generateRandomString();
			grps.createGroupaccount(driver, "Internet", "Other", "AutoGrp"+Utility.generateRandomString(), "N Test Group", "N Last Name", "9872145670", "Test", "test", "Alaska", "98765", "United States", test_steps);

			grps.SaveAdvGroup(driver);
			grps.click_GroupNewReservation(driver, test_steps);

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

			//res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan,PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, IsAssign,accName);
			res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			randomString = Utility.GenerateRandomNumber();
			String gLastName = GuestLastName.replace("XXX", randomString);
			res.enter_MailingAddressWithoutAccpayment(driver, test_steps, Salutation, GuestFirstName, gLastName, PhoneNumber,
					AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			//res.createTaxExempt(driver, taxExcemptId, test_steps);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);

			res.clickBookNow(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);

			res.verify_CancelButtonInReservationStatusDropdown(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create Reservation", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create Reservation", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add(
					"<b>*****************Verify tax get exempted in reservation folio</b>***************************");
			res.click_Folio(driver, test_steps);

		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify Tax Exempt", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify Tax Exempt", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider()
	public Object[][] getData() {
		return Utility.getData("GroupResCancel", envLoginExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {

		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
