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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyTaxExamptFunctionality extends TestCore {

	private WebDriver driver;

	public static String test_description = "";
	public static String test_category = "";
	public static String test_name = "";
	public static ArrayList<String> test_steps = new ArrayList<>();
	public static ArrayList<String> getTest_Steps = new ArrayList<>();
	
	
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

String reservation=null;	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyTaxExemptInCPReservation(String TestCaseID,
			String Adults, String Children, String Rateplan, String PromoCode, String RoomClass, String IsAssign,
			String IsDepositOverride, String DepositOverrideAmount, String Salutation, String GuestFirstName,
			String GuestLastName, String PhoneNumber, String AltenativePhone, String Email, String Account,
			String AccountType, String Address1, String Address2, String Address3, String City, String Country,
			String State, String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue,
			String TravelAgent, String MarketSegment, String Referral, String IsAddNotes, String NoteType,
			String Subject, String Description, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus, String TaxName,
			String TaxDescription, String value, String category, String TaxDisplayName, String Vat, String TaxExcempt,
			String Percent, String taxLedgerAccount, String bedsCount, String maxAdults, String maxPersopns,
			String roomQuantity, String ratename, String maxadults, String maxpersopns, String baseAmount,
			String additionalAdult, String additionalChild, String taxExcemptId) throws Exception {
      
		
		String testcaseId="848224|848172";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848224");
		test_name = this.getClass().getSimpleName().trim();;
		test_description =  test_name
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848224' target='_blank'>"
				+ "Click here to open TestRail: 848224</a>"
				+"<a href='https://innroad.testrail.io/index.php?/cases/view/848172' target='_blank'>"
				+ "Click here to open TestRail: 848172</a>";
		test_category = "TaxExemptInCPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		RoomClass roomClass = new RoomClass();
		TaxesAndFee taxNFee=new TaxesAndFee();
		Rate rate = new Rate();
		Tax tax = new Tax();
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
			login_Autoota(driver);
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
			// Create Tax
			test_steps.add("<b>************** Making Tax Setup*******************</b>");
			nav.Setup(driver);
			test_steps.add("Navigate to setup");
			nav.clickTaxesAndFees(driver);			
			test_steps.add("Navigated to TaxesAndFees");
			// create new tax------------------
			test_steps.add("<b>********** Creating New percent Tax****************</b>");
			String deleteTaxname = TaxName;
			
			taxNFee.deleteAllTaxesAndFee(driver, test_steps);
			randomString = Utility.GenerateRandomNumber();
			taxName = TaxName.replace("XXX", randomString);
			
			boolean restrictToggle=false;
			taxNFee.createTaxes(driver, taxName, taxName, taxName, "Tax Adjustment", "percent", "10",
					"Yes", "Room Charge", restrictToggle, "", "", "", test_steps);
			
	

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "create tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "create tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add("<b>*****************Creating CP reservation</b>***************************");
			nav.cpReservation_Backward(driver);
			test_steps.add("Click at resrevation menue link ");

			res.click_NewReservation(driver, test_steps);
			getTest_Steps.clear();
			getTest_Steps = res.checkInDate(driver, 0);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.checkOutDate(driver, +2);
			test_steps.addAll(getTest_Steps);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, RoomClass, "", "");
			res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			randomString = Utility.GenerateRandomNumber();
			String gLastName = GuestLastName.replace("XXX", randomString);
			res.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, gLastName, PhoneNumber,
					AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			res.createTaxExempt(driver, taxExcemptId, test_steps);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);

			
			res.clickBookNow(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
		} catch (Exception e) {

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
			boolean isTaxExist = res.isTaxPresentAfterTaxExempt(driver, test_steps, taxLedgerAccount, taxName);
			Assert.assertEquals(isTaxExist, false);
			
			statusCode.add(0, "1");
			comments.add(0, "Taxexampt is verified");
			
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
		
		
		try {
			test_steps.add(
					"<b>*****************Verify tax get exempted in reservation folio</b>***************************");
			res.click_DeatilsTab(driver, test_steps);
			res.clickEditPaymentMethodInfo(driver, test_steps);
			res.clickTaxExcemptCheckbox(driver, test_steps);
			res.savePaymentMethod(driver);
			Wait.wait15Second();			
			res.click_Folio(driver, test_steps);			
			boolean isTaxExist = res.isTaxPresentAfterTaxExempt(driver, test_steps, taxLedgerAccount, taxName);
			Assert.assertEquals(isTaxExist, true);
			test_steps.add("Tax is showing for reservation after tax exampt option is unchecked");
			statusCode.add(1, "1");
			comments.add(1, "Tax is showing for reservation");
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
		
		
		try {
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider()
	public Object[][] getData() {
		return Utility.getData("VerifyTaxExamptFunctionality", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {

	Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode,
				comments, TestCore.TestRail_AssignToID);
	}
	

}
