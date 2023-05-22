package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CopyOfNotesFromCorpAccToSplitReservation  extends TestCore {

	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void copyOfNotesFromCorpAccToSplitReservation(String url, String clientCode, String username, String password,String propertyName,String adult,String children,String rateplan,String promoCode,String isSplitRes,
			String isAssign,String isDepositOverride,String depositOverrideAmount,String isAddMoreGuestInfo,String salutation,String guestFirstName,String guestLastName,String phoneNumber,String alternativePhone,String email,String account,String accountType,
			String address1,String address2,String address3,String city,String country,String state,String postalCode,String isGuesProfile,String paymentType,String cardNumber,String nameOnCard,String cardExpDate,String isChangeInPayAmount,String changedAmountValue,
			String travelAgent,String marketSegment,String referral,String isAddNotes,String noteType,String noteSubject,String description,
			String isTask, String taskCategory, String taskType,String taskDetails,String taskRemarks,String taskDueon,String taskAssignee,String taskStatus,
			String accountName,String accounttype,String takePaymentType,String lineItemCategory,String lineItemCategoryAmount,
			String channels,String SeasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String specificDate, String dateFormat,String calendarTodayDay,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,String reason, String	subject,
			String ratePlanName, String baseAmount,
			String addtionalAdult, String additionalChild, String folioName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber,
			String NotesSubject, String NotesDescription,String  NoteType) throws InterruptedException, IOException, Exception {

		
		String testName =  this.getClass().getSimpleName().trim();
		String testcaseId="848529";
		String guestFullName;
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848529");
		
		testDescription = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848529' target='_blank'>"
				+ "Click here to open TestRail: C848529</a><br>";
		
		
		testCategory = "Create_CorpAcc";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
	
		Account CreateTA = new Account();
		
		

		String randomNumber = Utility.GenerateRandomNumber();
		
		
		
		folioName = ratePlanName;
		String reservationNumber=null;
		String status=null;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		


		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Autoota(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
				
		try {
			
			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", testName, "NavigateAccounts", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate Accounts", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String accountNumber = null;
		// New account
		try {
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			accountName=accountName+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, accounttype);
			CreateTA.AccountDetails(driver, accounttype, accountName);
			accountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, accountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
					address1, address2, address3, email, city, state, postalCode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, accountName, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			Wait.wait10Second();
			CreateTA.Cp_AccAddNotes( driver,  NotesSubject,  NotesDescription,  NoteType);
			getTestSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, accountName, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {

			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		account=accountName;
		// Reservation
		try {

			app_logs.info("==========CREATE SPLIT ROOM RESERVATION==========");
			testSteps.add("==========CREATE SPLIT ROOM RESERVATION==========");

			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");

			getTestSteps.clear();
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickAddARoom(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========ENTER STAY INFO AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER STAY INFO AND SEARCH ROOMS==========");
			String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
			String checkoutDate = Utility.GetNextDate(1, "MM/dd/yyyy");
			reservationPage.enterDate(driver, "start", checkInDate, "1");
			testSteps.add("Select First Room CheckIn Date : " + checkInDate);
			app_logs.info("Select First Room CheckIn Date : " + checkInDate);
			reservationPage.enterDate(driver, "end", checkoutDate, "1");
			testSteps.add("Select First Room CheckOut Date : " + checkoutDate);
			app_logs.info("Select First Room CheckOut Date : " + checkoutDate);

			checkInDate = Utility.GetNextDate(1, "MM/dd/yyyy");
			checkoutDate = Utility.GetNextDate(2, "MM/dd/yyyy");
			reservationPage.enterDate(driver, "start", checkInDate, "2");
			testSteps.add("Select Second Room CheckIn Date : " + checkInDate);
			app_logs.info("Select Second Room CheckIn Date : " + checkInDate);
			reservationPage.enterDate(driver, "end", checkoutDate, "2");
			testSteps.add("Select Second Room CheckOut Date : " + checkoutDate);
			app_logs.info("Select Second Room CheckOut Date : " + checkoutDate);

			getTestSteps.clear();
			getTestSteps = reservationPage.splitRoomCheckbox(driver, true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_Adults(driver, getTestSteps, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_Children(driver, getTestSteps, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.select_Rateplan(driver, getTestSteps,rateplan , "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			app_logs.info("room class: " + roomClassName);
			app_logs.info("==========SELECT SPLIT ROOM : A==========");
			testSteps.add("==========SELECT SPLIT ROOM : A==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectSplitRoom(driver, roomClassName, 2, 1);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SELECT SPLIT ROOM : B==========");
			testSteps.add("==========SELECT SPLIT ROOM : B==========");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectSplitRoom(driver, roomClassName, 3, 2);
			testSteps.addAll(getTestSteps);

			reservationPage.moveToAddARoom(driver);
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			Wait.wait5Second();

			reservationPage.enterMailingAddressWithEmail(driver, getTestSteps, salutation, guestFirstName, guestLastName, phoneNumber,
					alternativePhone, email, account, accountType, address1, address2, address3, city, country, state,
					postalCode, isGuesProfile);
			reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
			reservationPage.enter_MarketSegmentDetails(driver, getTestSteps, travelAgent, marketSegment, referral);
			//res.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			reservationPage.clickBookNow(driver, getTestSteps);
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			status = reservationPage.get_ReservationStatus(driver, getTestSteps);
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			
			reservationPage.closeFirstOpenedReservationUnSavedData(driver, getTestSteps);
			reservationPage.Search_ResNumber_And_Click(driver, reservationNumber);
			
			
			reservationPage.verifyNotesDetails(driver,getTestSteps, NoteType, NotesSubject, NotesDescription,"swarna sunani");
			testSteps.addAll(getTestSteps);
	
		
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Guest Histroy Account ", testName, "GuestHistoryAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Guest Histroy Account", testName, "GuestHistoryAccount",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			comments.add(0,  "Notes from Corporate account is copied to a reservation");
			statusCode.add(0, "1");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CopyOfNotesFromCorpAcc", excel_Swarna);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}


}
