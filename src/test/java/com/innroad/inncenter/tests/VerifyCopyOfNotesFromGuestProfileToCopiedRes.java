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
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;

import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyCopyOfNotesFromGuestProfileToCopiedRes extends TestCore {
	//C825137
	


	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	
	ArrayList<String> statusCode=new ArrayList<String>();

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	String testName = this.getClass().getSimpleName().trim();
	
	//String statusCode="5";
	//String caseId="785704";
	String reservationNumber = null;
	String copiedReservationNumber=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyAssociatingGuestProfileToReservationWithNotesTasks(String TestCaseID,
			String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan,
			String PromoCode, String IsAssign, String IsDepositOverride, String DepositOverrideAmount,
			String Salutation, String GuestFirstName, String GuestLastName, String FirstName, String LastName,
			String PhoneNumber, String AlternativePhone, String Email, String Account, String AccountType,
			String Address1, String Address2, String Address3, String City, String Country, String State,
			String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber, String NameOnCard,
			String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue, String TravelAgent,
			String MarketSegment, String Referral, String IsAddNotes, String NoteType, String Subject,
			String Description, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus, String ReplaceTheGuestInfo,
			String guestProfileName, String GuestPhoneNumber, String GuestAlternativePhone, String GuestEmail,
			String GuestAddressLine1, String GuestAddressLine2, String GuestAddressLine3, String GuestCity,
			String GuestCountry, String GuestState, String GuestPostalCode, String GuestMarketSegment,
			String GuestReferral, String RoomClassAbb, String RoomClassName, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String Season, String RatePlan, String RateName, String BaseAmount,
			String AddtionalAdult, String AdditionalChild, String DisplayName, String AssociateSource,
			String RatePolicy, String RateDescription) throws Exception {
		
		String testcaseId="848530";
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848530");
		test_name = "VerifyAssociatingGuestProfileToReservationWithNotesTasks";
		testDescription = "Verify Associating GuestProfile To Reservation With Notes  & Tasks<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848530' target='_blank'><br>"
				+ "Click here to open TestRail: C848530</a>";

		testCategory = "VerifyGuestProfileBehavior";
		String testName = test_name;

		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		ReservationSearch reser_search = new ReservationSearch();
		
		CPReservationPage res = new CPReservationPage();
		Navigation navigation = new Navigation();
		GuestHistory guestHistory = new GuestHistory();

		
		
		String reservationStatus = null;
		GuestLastName = GuestLastName + Utility.GenerateRandomNumber();
		
		//String Date = Utility.getCurrentDate("MM/dd/ YY");
		
		 String GuestFirstNameCopy=GuestFirstName+"copy", GuestLastNameCopy=GuestLastName+"copy";

		try {

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
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);

			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		
		// Create Reservation

		try {
			app_logs.info("======CP Reservation Creation=======");
			test_steps.add("======CP Reservation Creation=======");
			//navigation.Click_ReservationMenuFromTaxPage(driver);
			res.click_NewReservation(driver, test_steps);
			res.CheckInDate(driver, 1);
			res.CheckOutDate(driver, 1);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			System.out.print("ISAssigne:" + IsAssign);
			res.selectRoom(driver, test_steps, RoomClassName, Utility.RoomNo, "");
			//res.selectRoom(driver, RoomClassName, IsAssign);
			
			res.clickNext(driver, test_steps);
			res.enter_ContactName(driver, getTest_Steps, "", FirstName, LastName);
			res.enterMailingAddressWithEmail(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber,
					AlternativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			//res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			res.clickBookNow(driver, test_steps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, test_steps);
			reservationStatus = res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
		
			res.closeFirstOpenedReservationUnSavedData(driver, test_steps);

		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation ", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		GuestFirstName = GuestFirstName + Utility.GenerateRandomNumber();
		GuestLastName = GuestLastName + Utility.GenerateRandomNumber();
		guestProfileName = GuestFirstName + " " + GuestLastName;
		String accountNumber;
		try {
			app_logs.info("======Guest Profile Creation=======");
			test_steps.add("======Guest Profile Creation=======");
			navigation.GuestHistory(driver);
			app_logs.info("Navigate To GuestProfile");
			test_steps.add("Navigate To GuestProfile");
			guestHistory.clickGuestHistoryNewAccount(driver);
			app_logs.info("Click To New Account Button");
			test_steps.add("Click To New Account Button");
			//guestHistory.enterFirstLastName(driver, GuestFirstName, GuestLastName, test_steps);
			boolean flag = guestHistory.enterFirstName(driver, "Mr.", GuestFirstName, GuestLastName,
					test_steps);
			while (!flag) {
				if (!flag) {
					guestHistory.closeAccount(driver);
					test_steps.add("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB =======");
					app_logs.info("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB ======");
					guestHistory.clickGuestHistoryNewAccount(driver);
					test_steps.add("Open new guest history");
					flag = guestHistory.enterFirstName(driver, "Mr.", GuestFirstName, GuestLastName,
							test_steps);
				}
			}
			//guestHistory.EnterFirstLastNameSeperately(driver, GuestFirstName, GuestLastName, test_steps);
			guestHistory.accountAttributes(driver, GuestMarketSegment, GuestReferral, test_steps);
			getTest_Steps.clear();
			getTest_Steps = guestHistory.Mailinginfo(driver, GuestFirstName, GuestLastName, GuestPhoneNumber,
					GuestAlternativePhone, GuestAddressLine1, GuestAddressLine2, GuestAddressLine3, GuestEmail,
					GuestCity, GuestState, GuestPostalCode);
			test_steps.addAll(getTest_Steps);
			guestHistory.Billinginfo(driver, test_steps);
			guestHistory.clickAddNotes(driver, test_steps);
			guestHistory.enterNoteDetails(driver, Subject, Description, NoteType, test_steps);
			guestHistory.clickNoteSaveButton(driver, test_steps);
			guestHistory.Save(driver);
			app_logs.info("Save Button Clicked");
			test_steps.add("Save Button Clicked");
			accountNumber = guestHistory.GetAccountNumber(driver);
			app_logs.info("Successfully Created Guest History Account " + accountNumber);
			test_steps.add("Successfully Created Guest History Account " + accountNumber);
			guestHistory.closeAccount(driver);

		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Guest Histroy Account ", testName, "GuestHistoryAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Guest Histroy Account", testName, "GuestHistoryAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {
			app_logs.info("======Attach Guest Profile=======");
			test_steps.add("=====Attach Guest Profile=======");
			navigation.cpReservationBackward(driver);
			Wait.wait10Second();
			res.Search_ResNumber_And_Click(driver, reservationNumber);
			//reser_search.SearchAndOpenRes(driver, reservationNumber);
			app_logs.info("Successfully Searched & Opened Reservation " + reservationNumber);
			test_steps.add("Successfully Searched &Opened Reservation " + reservationNumber);
			res.clickGuestInfoEditDetailsButton(driver, test_steps);
			res.attachGuestProfile(driver, test_steps, guestProfileName, ReplaceTheGuestInfo);
			getTest_Steps.clear();
			getTest_Steps = res.verfifyGuestProfileAttachedInfo(driver, "Mr. "+guestProfileName, "Mr. "+guestProfileName,
					GuestAddressLine1, GuestAddressLine2, GuestAddressLine3, GuestEmail, GuestPhoneNumber,
					GuestAlternativePhone, GuestCity, GuestCountry, GuestState, GuestPostalCode, GuestMarketSegment,
					GuestReferral);
			test_steps.addAll(getTest_Steps);
			app_logs.info("Successfully Verified Attched GuestProfile: " + guestProfileName);
			test_steps.add("Successfully Verified Attched GuestProfile: " + guestProfileName);
			res.closeFirstOpenedReservationUnSavedData(driver, test_steps);
			reser_search.SearchAndOpenRes(driver, reservationNumber);
			app_logs.info("Successfully Searched & Opened Reservation " + reservationNumber);
			test_steps.add("Successfully Searched & Opened Reservation " + reservationNumber);
			
			getTest_Steps.clear();
			getTest_Steps = res.verifyNotes(driver, NoteType, Subject, Description);
			test_steps.addAll(getTest_Steps);
			//res.VerifyCreatedTask(driver, TaskType, TaskDetails, TaskStatus, test_steps);
			res.closeFirstOpenedReservationUnSavedData(driver, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Notes & Task To Reservation ", testName, "GuestProfile",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Notes & Task To Reservation", testName, "GuestProfile",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {
			
		
			res.guestUnlimitedSearchAndOpen(driver, test_steps,  GuestFirstName+" "+GuestLastName, null, true, 12);
			res.clickCopyButton(driver, GuestFirstName+" "+GuestLastName);
			res.enterGuestNameWhileCopy(driver, test_steps, GuestFirstNameCopy);
			res.enterGuestLastNameWhileCopy(driver, test_steps, GuestLastNameCopy);
			res.clickBookNow(driver, test_steps);
			copiedReservationNumber=res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.closeAllOpenedReservations(driver);
			res.Search_ResNumber_And_Click(driver, copiedReservationNumber);
			
			
			res.verifyNotes(driver, NoteType, Subject, Description);
			
			comments.add(0,  "Notes from guest profile is copied to a copied reservation");
			statusCode.add(0, "1");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, test_steps);
		
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Notes & Task To Reservation ", testName, "GuestProfile",
						driver);
			} else {
				Assert.assertTrue(false);
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Notes & Task To Reservation ", testName, "GuestProfile",
						driver);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Notes & Task To Reservation", testName, "GuestProfile",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		

	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided

		return Utility.getData("CopyOfNotesFromGuestProfile", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

}

	
}
