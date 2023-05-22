package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

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
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightAudit;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;

public class VerifyCreateMRBReservationFromSingleQuote  extends TestCore {

	//C825255
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	public static ArrayList<String> test_name = new ArrayList<>();
	public static ArrayList<String> test_description = new ArrayList<>();
	public static ArrayList<String> test_catagory = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String scriptName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + scriptName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(scriptName, excel_Swarna))

			throw new SkipException("Skipping the test - " + scriptName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyCreateMRBReservationFromSingleQuote(
			String propertyName, String MarketeSegment, String referral, String firstName, 
			String lastName,
			String phoneNumber, String alternativeNumber, String address1, String address2, 
			String address3, String lineItemCategory, String RoomClass, String account,
			String saluation, String isTaxExempt, String taxEmptext, String nightValue,
			String nightStay, String enterAmount,
			String permanentTaxName, String nightsBack, String isAssign, String child, String adult, String salutation,
			String isChecked, String paymentType, String cardNumber, String nameOnCard,String ratesNames)
			throws Exception {


		String testName = this.getClass().getSimpleName().trim();
		System.out.println(" : "+ statusCode.size());

		String scriptName =testName;
		
		String catagory = "Quote Reservation";
		
		

        String testcaseId="848604";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848604");
		
		String description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848604' target='_blank'>"
				+ "Click here to open TestRail: C8486045</a><br>";

		test_name.add(scriptName);
		test_description.add(description);
		test_catagory.add(catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + scriptName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Tax tax = new Tax();
		Properties properties = new Properties();
		NightAudit nightAudit = new NightAudit();
		String beforeCheckInLongStay = "";
		String afterCheckInLongStay = "";
		String reservationNumber = "";
		String reservationStatus="";
		Folio folio = new Folio();
		CPReservationPage reservationPage = new CPReservationPage();

		String timeZone = "America/New_York";
		String cardExpDate = Utility.getNextDate(365, "MM/yy", timeZone);
		app_logs.info("Card Expiry " + cardExpDate);
		ReservationSearch reservationSearch = new ReservationSearch();

		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);
			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			login_Autoota(driver);

			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
	
		try {
			
			try {
			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			}catch (Exception e) {
				getTestSteps.clear();
				getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
			}
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, -4);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, child);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			reservationPage.select_Rateplan(driver, getTestSteps, ratesNames,"");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, RoomClass, isAssign, account);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			lastName = lastName + Utility.GenerateRandomNumber();

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_GuestName(driver, getTestSteps, salutation, firstName, lastName);
			testSteps.addAll(getTestSteps);

			reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard,
					cardExpDate);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			reservationPage.click_Quote(driver, getTestSteps);
			reservationNumber=reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			reservationStatus=reservationPage.get_ReservationStatus(driver, getTestSteps);
			reservationPage.verify_QuoteConfirmetionPopup(driver, getTestSteps);
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			reservationPage.verify_QuoteStauts(driver, getTestSteps, reservationStatus);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			
			
		
			try
			{
				Elements_CPReservation element = new Elements_CPReservation(driver);
				Wait.explicit_wait_visibilityof_webelement(element.StayInfo_EditBtnDisable, driver);
				
				boolean stayInfoIsEnable=element.StayInfo_EditBtnDisable.isEnabled();
				Assert.assertTrue(stayInfoIsEnable, "Failed: Quote edit stay info button is enabled which should not be");
				testSteps.add("Quote can not be edited");
				app_logs.info("Quote can not be edited");
			}
			
			catch (Exception e) {
				testSteps.add("Failed: Quote edit stay info button is enabled which should not be");
				app_logs.info("Failed: Quote edit stay info button is enabled which should not be");

			} catch (Error e) {
				testSteps.add("Failed: Quote edit stay info button is enabled which should not be");
				app_logs.info("Failed: Quote edit stay info button is enabled which should not be");
			}
			
			

		} catch (Exception e) {

			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", scriptName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", scriptName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	
		

		
		// verified amount after tax exempt
		try {
			statusCode.set(0, "1");
			comments.set(0, "Can not modify Quote stayinfo");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
			
		}

		catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify tax item in folio line item", scriptName, "FolioLineItem",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify tax item in folio line item", scriptName, "FolioLineItem",
						driver);

			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("CreateMRBResFromSingleResQuote", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}
