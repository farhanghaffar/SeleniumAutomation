package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGuestHistoryAccount extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	public static String timeZone = null;
	Properties properties = new Properties();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyGuestHistoryAccount(String TestCaseID, String guestFirstName, String guestLastName, String propertyName,
			String address, String phonenumber, String email, String city, String state, String cardNumber,
			String paymentMethod, String useMailingInfo, String isGuesProfile, String isModifyAccount,
			String isDeleteAccount, String scenario) throws Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {

		Utility.initializeTestCase(TestCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		if (scenario.equalsIgnoreCase("SearchInReservation") && isModifyAccount.equalsIgnoreCase("Yes")
				&& isDeleteAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "ModifyAndDelete";
		} else if (scenario.equalsIgnoreCase("SearchInReservation") && !isModifyAccount.equalsIgnoreCase("Yes")
				&& !isDeleteAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario;
		} else if (scenario.equalsIgnoreCase("SearchInReservation") && !isModifyAccount.equalsIgnoreCase("Yes")
				&& isDeleteAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "Delete";
		} else if (scenario.equalsIgnoreCase("SearchInReservation") && isModifyAccount.equalsIgnoreCase("Yes")
				&& !isDeleteAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "Modify";
		} else if (scenario.equalsIgnoreCase("AfterReservationCreated") && isGuesProfile.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "GuestProfileChecked";
		} else if (scenario.equalsIgnoreCase("AfterReservationCreated") && !isGuesProfile.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "GuestProfileNotChecked";
		} else if (scenario.equalsIgnoreCase("CreateReservationFromAccount")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "VerifyReservationTabName";
		} else if(scenario.equalsIgnoreCase("BackToGuestHistory")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario ;
		}else if (scenario.equalsIgnoreCase("AfterReservationCreated") && isGuesProfile.equalsIgnoreCase("Yes") &&  isModifyAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "ModifyWhenGuestProfileChecked";
		}else if (scenario.equalsIgnoreCase("AfterReservationCreated") && !isGuesProfile.equalsIgnoreCase("Yes") && isModifyAccount.equalsIgnoreCase("Yes")) {
			test_name = "VerifyGuestHistoryAccount" + " " + scenario + " " + "ModifyWhenGuestProfileNotChecked";
		}

		test_description = "Verify Guest History Account<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1984' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1984</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848254' target='_blank'>"
				+ "Click here to open Jira: 848254</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848300' target='_blank'>"
				+ "Click here to open Jira: 848300</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848387' target='_blank'>"
				+ "Click here to open Jira: 848387</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848312' target='_blank'>"
				+ "Click here to open Jira: 848312</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848141' target='_blank'>"
				+ "Click here to open Jira: 848141</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824796' target='_blank'>"
				+ "Click here to open Jira: 824796</a>";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Navigation nav = new Navigation();
		GuestHistory guestHistory = new GuestHistory();
		CPReservationPage cpRes = new CPReservationPage();
		ReservationSearch rsvSearch= new ReservationSearch();
		guestFirstName = guestFirstName + Utility.generateRandomString();
		guestLastName = guestLastName + Utility.generateRandomString();
		String billingNotes = "adding primary card";
		String marketSegment = "Internet";
		String referral = "Other";
		String accountType = "";
		String noteType = "Guest Note";
		String salutation = "Mr.";
		String billingSalutation = "Lord.";
		String travelAgent = "";
		String alternativeNumber = phonenumber;
		String country = "United States";
		String accountNo = "";
		String account = "";
		String subject = "Cleaning";
		String details = "Room is Dirty";
		String postalcode = Utility.fourDigitgenerateRandomString();
		String expDate = Utility.getFutureMonthAndYearForMasterCard();
		String guestName = guestFirstName + " " + guestLastName;
		String confirmationNum = "";
		String roomCount = "1";
		String resCount = "1";

		String billingFirstName = guestFirstName + Utility.generateRandomStringWithGivenLength(3);
		String billingLastName = guestLastName + Utility.generateRandomStringWithGivenLength(3);
		String baseFName = guestFirstName;
		String baseLName = guestLastName;
		String updatedGuestFName = "";
		String updatedGuestLName = "";
		updatedGuestFName = baseFName + Utility.generateRandomStringWithGivenLength(3);
		updatedGuestLName = baseLName + Utility.generateRandomStringWithGivenLength(3);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			//login_CP(driver);
			login_NONGS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			test_steps.add("=================GET THE PROPERTY TIMEZONE ======================");
			app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
			propertyName = properties.getProperty(driver, test_steps);
			nav.Setup(driver);
			nav.Properties(driver);
			nav.openProperty(driver, test_steps, propertyName);
			nav.clickPropertyOptions(driver, test_steps);
			timeZone = nav.get_Property_TimeZone(driver);
			test_steps.add("Property TimeZone: " + timeZone);
			nav.Reservation_Backward(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (scenario.equalsIgnoreCase("SearchInReservation")
					|| scenario.equalsIgnoreCase("CreateReservationFromAccount")
					|| scenario.equalsIgnoreCase("BackToGuestHistory")) {
				// Create Guest Account
				test_steps.add("=================CREATE GUEST HISTORY ACCOUNT======================");
				app_logs.info("=================CREATE GUEST HISTORY ACCOUNT======================");
				nav.GuestHistory(driver);
				guestHistory.clickGuestHistoryNewAccount(driver);
				test_steps.add("Open new guest history");
				// Making while loop(Due to issue NG-900) which check's when correct new guest
				// history page comes
				boolean flag = guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
						test_steps);
				while (!flag) {
					if (!flag) {
						//guestHistory.clickReset(driver);
						guestHistory.closeAccount(driver);
						test_steps.add("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB =======");
						app_logs.info("====== CLOSE OLDER TAB AND OPEN AGAIN NEW ACCOUNT TAB ======");
						guestHistory.clickGuestHistoryNewAccount(driver);
						test_steps.add("Open new guest history");
						flag = guestHistory.enterFirstName(driver, salutation, guestFirstName, guestLastName,
								test_steps);
					}
				}
				guestHistory.accountAttributes(driver, marketSegment, referral, test_steps);
				accountNo = guestHistory.getAccountNumberFromDetailsPage(driver);

				System.out.println(accountNo);
				guestHistory.mailinginfo(driver, guestFirstName, guestLastName, phonenumber, alternativeNumber, address,
						address, address, email, city, state, postalcode, test_steps);

				guestHistory.billinginfo(driver, paymentMethod, cardNumber, expDate, billingNotes, test_steps);
				if (!useMailingInfo.equalsIgnoreCase("Yes")) {
					guestHistory.useMailingInforMation(driver, useMailingInfo);
					System.out.println("Enter in checkIn condition");

					test_steps.add("============== ENTER DIFFERENT BILLING FIRST AND LAST NAME ==============");
					guestHistory.billingFirstAndLastName(driver, billingSalutation, billingFirstName, billingLastName,
							test_steps);
				}
				guestHistory.clickAddNotes(driver, test_steps);
				Wait.wait5Second();
				guestHistory.enterNoteDetails(driver, subject, details, noteType, test_steps);
				guestHistory.clickNoteSaveButton(driver, test_steps);
				// Verify note time issue NG-2233
				guestHistory.verifyNoteUpdatedOn(driver, timeZone, test_steps);
				guestHistory.clickSave(driver);
				test_steps.add("Sucess - Created Account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-2178'>" + "Click here to open JIRA: NG-2178</a>"); 
				if (scenario.equalsIgnoreCase("SearchInReservation")) {
					guestHistory.closeAccount(driver);
				}
					
				/*test_steps.add(
						"Create Guest History Account"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848141' target='_blank'>"
								+ "Click here to open TestRail: C848141</a><br>");			
				  Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Create Guest History Account"); 
				  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);		*/		
				  
			} else if (scenario.equalsIgnoreCase("AfterReservationCreated")) {
				//Utility.refreshPage(driver);
				Wait.wait25Second();
				cpRes.click_NewReservation(driver, test_steps);
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);
				cpRes.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName, phonenumber,
						alternativeNumber, email, account, accountType, address, address, address, city, country, state,
						postalcode, isGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {
					cpRes.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, guestFirstName, expDate);
				}
				if (!useMailingInfo.equalsIgnoreCase("Yes")) {
					cpRes.clickSameAsMailingCheckbox(driver);
					cpRes.clickSameAsMailingCheckbox(driver);
					cpRes.sameAsMailing(driver, useMailingInfo);
					test_steps.add("============== ENTER DIFFERENT BILLING FIRST AND LAST NAME ==============");
					cpRes.enterBillingInfo(driver, test_steps, billingFirstName, billingLastName);
				} else {

				}
				cpRes.enter_MarketSegmentDetails(driver, test_steps, travelAgent, marketSegment, referral);
				cpRes.clickBookNow(driver, test_steps);
				confirmationNum = cpRes.get_ReservationConfirmationNumber(driver, test_steps);
				cpRes.clickCloseReservationSavePopup(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (scenario.equalsIgnoreCase("SearchInReservation")) {
				test_steps
						.add("================= SEARCH CREATED ACCOUNT ON ACCOUNT SEARCH PAGE ======================");
				app_logs.info("================= SEARCH CREATED ACCOUNT ON ACCOUNT SEARCH PAGE ======================");

				test_steps.add(
						"Search with AccountFirstName and AccountLastName" + guestFirstName + ", " + guestLastName);
				guestHistory.searchAccount(driver, guestFirstName, guestLastName);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
			
				guestHistory.clickClear(driver);
				test_steps.add("Search with AccountFirstName only: " + guestFirstName);
				guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				guestHistory.clickClear(driver);
				test_steps.add("Search with AccountLastName only: " + guestLastName);
				guestHistory.searchWithLastName(driver, guestLastName, test_steps);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				guestHistory.clickClear(driver);
				
				test_steps.add("Sucess - Search with combination checked" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-6540'>" + "Click here to open JIRA: NG-6540</a>");
				guestHistory.searchAccountWithCombine(driver, guestFirstName, guestLastName, test_steps);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				guestHistory.clickClear(driver);
				test_steps.add("Search with AccountNumber only: " + accountNo);
				guestHistory.searchWithAccountNumber(driver, accountNo, test_steps);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				test_steps.add("Sucess - Verify account search with all combination" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-6284'>" + "Click here to open JIRA: NG-6284</a>");
				
				guestHistory.searchWithAccountNumberAndAccountName(driver, accountNo,guestFirstName,guestLastName,false);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				test_steps.add("Search with AccountNumber and Guest name without combine checkbox checked: " + accountNo + " and "+guestFirstName+ " "+guestLastName);
				System.out.println("Searched acc and guest name combined uncheck");
				
				
				guestHistory.searchWithAccountNumberAndAccountName(driver, accountNo,guestFirstName,guestLastName,true);
				guestHistory.verifyAccountDisplayedInSearchResult(driver, accountNo, test_steps);
				test_steps.add("Search with AccountNumber and Guest name with combine checkbox checked: " + accountNo + " and "+guestFirstName+ " "+guestLastName);
				System.out.println("Searched acc and guest name combined check");
				guestHistory.clickClear(driver);
			
			}
			
			if (scenario.equalsIgnoreCase("AfterReservationCreated") && isGuesProfile.equalsIgnoreCase("Yes")) {
				{
					test_steps.add(
							"================= VERIFY GUEST PROFILE CREATED FROM RESERVATION ======================");
					app_logs.info(
							"================= VERIFY GUEST PROFILE CREATED FROM RESERVATION ======================");
					nav.GuestHistory(driver);
					guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
					guestHistory.OpenSearchedGuestHistroyAccount(driver);
					test_steps.add("Open Guest " +guestFirstName);
					app_logs.info("Open Guest " + guestFirstName);
					/*test_steps.add("Verify the Guest profile should be created and the guest name should be hyperlinked after creating Guest Profile"
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848300' target='_blank'>"
									+ "Click here to open TestRail: 848300</a><br>");			
					  Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the Guest profile should be created and the guest name should be hyperlinked after creating Guest Profile"); 
					  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);					  
				*/	guestHistory.verifyGuestHistoryAccount(driver, guestFirstName, guestLastName, guestFirstName,
							guestLastName, email, phonenumber, alternativeNumber, address, city, country, state,
							postalcode, paymentMethod, cardNumber, guestName, expDate, billingFirstName,
							billingLastName, marketSegment, referral, useMailingInfo, test_steps);
					/*test_steps.add(
							"Verify Create / Update guest profile checkbox functionality."
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848254' target='_blank'>"
									+ "Click here to open TestRail: 848254</a><br>");			
					  Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Create / Update guest profile checkbox functionality."); 
					  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);				*/
				
				}
				test_steps.add("========= VERIFYING CREATED RESERVATION IN GUEST HISTORY ==========");
				guestHistory.OpenReservtionTab_GuestHistoryAccount(driver);
				guestHistory.searchReservation(driver, confirmationNum);
				guestHistory.verifyReservationExistInSearch(driver, confirmationNum, true, test_steps);
				guestHistory.closeAccount(driver);

			}
			if (scenario.equalsIgnoreCase("AfterReservationCreated") && !isGuesProfile.equalsIgnoreCase("Yes")) {
				// Verify when guest history not created from reservation and checkbox unchecked

				test_steps.add(
						"================= VERIFY GUEST PROFILE NOT CREATED FROM RESERVATION ======================");
				app_logs.info(
						"================= VERIFY GUEST PROFILE NOT CREATED FROM RESERVATION ======================");
				nav.GuestHistory(driver);
				boolean guestAccountExist = guestHistory.isGuestHistoryAccountExist(driver, guestFirstName, test_steps);
				Assert.assertEquals(guestAccountExist, false, "Failed to verify");
				test_steps.add("Verified that guest history not created: " + guestFirstName);
		
			}
			if (scenario.equalsIgnoreCase("AfterReservationCreated") && !isGuesProfile.equalsIgnoreCase("Yes")
					&& isModifyAccount.equalsIgnoreCase("Yes")) {
				test_steps.add(
						"================= CHECKED CREATE GUEST PROFILE CHECKBOX FOR EXISTING RESERVATION ======================");
				app_logs.info(
						"================= CHECKED CREATE GUEST PROFILE CHECKBOX FOR EXISTING RESERVATION ======================");
				nav.reservation(driver);
				test_steps.add("Navigate to reservation Tab");
				cpRes.SearchandClickgivenReservationNumber(driver, confirmationNum);
				cpRes.clickGuestInfo(driver, test_steps);
				test_steps.add("Open existing reservation: " + confirmationNum);
				cpRes.clickGuestProfileCheckbox(driver, test_steps);
				cpRes.clickSaveGuestInfo(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);
				nav.GuestHistory(driver);
				test_steps.add("Navigate to Guest History");
				guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
				guestHistory.OpenSearchedGuestHistroyAccount(driver);
				test_steps.add("Success - Able to open account" + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/NG-2178'>" + "Click here to open JIRA: NG-2178</a>");
				test_steps.add("Open Guest profile Created After modify reservation: " + confirmationNum);
				test_steps.add("================= VERIFY GUEST HISTORY DETAILS ======================");
				guestHistory.verifyGuestHistoryAccount(driver, guestFirstName, guestLastName, guestFirstName,
						guestLastName, email, phonenumber, alternativeNumber, address, city, country, state, postalcode,
						paymentMethod, cardNumber, guestName, expDate, billingFirstName, billingLastName, marketSegment,
						referral, useMailingInfo, test_steps);
			} else if (scenario.equalsIgnoreCase("AfterReservationCreated") && isGuesProfile.equalsIgnoreCase("Yes")
					&& isModifyAccount.equalsIgnoreCase("Yes")) {
				test_steps.add(
						"================= CHECKED UPDATE GUEST PROFILE CHECKBOX FOR EXISTING RESERVATION ======================");
				app_logs.info(
						"================= CHECKED UPDATE GUEST PROFILE CHECKBOX FOR EXISTING RESERVATION ======================");
				nav.reservation(driver);
				test_steps.add("Navigate to reservation Tab");
				app_logs.info("Navigate to reservation Tab");
				//cpRes.SearchandClickgivenReservationNumber(driver, confirmationNum);
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNum);
				test_steps.add("Open existing reservation: " + confirmationNum);
				cpRes.clickGuestInfo(driver, test_steps);
				updatedGuestFName = baseFName + Utility.generateRandomStringWithGivenLength(3);
				updatedGuestLName = baseLName + Utility.generateRandomStringWithGivenLength(3);
				test_steps.add("================= UPDATE GUEST FIRST AND LAST NAME ======================");

				cpRes.enter_GuestName(driver, test_steps, salutation, updatedGuestFName, updatedGuestLName);
				test_steps.add("updated first name and last name: " + updatedGuestFName + " " + updatedGuestLName);
				cpRes.clickUpdateGuestProfile(driver, test_steps);
				cpRes.clickSaveGuestInfo(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);

				nav.GuestHistory(driver);
				test_steps.add("Navigate to Guest History");
				guestHistory.searchWithFirstName(driver, updatedGuestFName, test_steps);
				guestHistory.OpenSearchedGuestHistroyAccount(driver);
				test_steps.add("Open Guest profile Created After modify reservation: " + confirmationNum);
				test_steps.add("================= VERIFY UPDATED GUEST HISTORY DETAILS ======================");
				guestHistory.verifyGuestHistoryAccount(driver, updatedGuestFName, updatedGuestLName, guestFirstName,
						guestLastName, email, phonenumber, alternativeNumber, address, city, country, state, postalcode,
						paymentMethod, cardNumber, guestName, expDate, billingFirstName, billingLastName, marketSegment,
						referral, useMailingInfo, test_steps);
		
			}
			if (scenario.equalsIgnoreCase("CreateReservationFromAccount")) {
				// Verify issue NG-900(After saving reservation created from guest account tab
				// was showing 'New tab' name)
				test_steps.add("================= CREATE RESERVATION FROM ACCOUNT NEW BUTTON ======================");
				guestHistory.newReservation(driver);
				test_steps.add("Click new reservation button on Guest History Account page");
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);
				cpRes.clickBookNow(driver, test_steps);
				confirmationNum = cpRes.get_ReservationConfirmationNumber(driver, test_steps);
				cpRes.clickCloseReservationSavePopup(driver, test_steps);
				cpRes.verifyAccountTabName(driver, guestName, test_steps);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (scenario.equalsIgnoreCase("SearchInReservation")) {

				nav.reservation(driver);
				test_steps.add("Navigate to Reservation Tab");
				cpRes.click_NewReservation(driver, test_steps);
				test_steps.add("Sucess - Verify after click new reservation find room button " + "<br>"
						+ "<a href='https://innroad.atlassian.net/browse/CTI-5275'>" + "Click here to open JIRA: CTI-5275</a>");
				cpRes.clickOnFindRooms(driver, test_steps);

				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);				
				test_steps.add("========= VERIFYING FIND GUEST PROFILE SUGGESTION ==========");
				boolean expectedResult = true;
				cpRes.verifyEnterFindGuestProfilewithGuestName(driver, guestName, expectedResult,
						test_steps);				
				cpRes.verifyEnterFindGuestProfilewithFistName(driver, guestFirstName, expectedResult, test_steps);
				cpRes.verifyEnterFindGuestProfilewithLastName(driver, guestLastName, expectedResult, test_steps);
				test_steps.add("========= GUEST PROFILE ASSOCIATED ==========");
				cpRes.clickAutoSuggestedGuest(driver, guestFirstName);
				test_steps.add("Guest associated to the reservation: " + guestFirstName);
			/*	test_steps.add(
						"Guest account data should auto fill, it should ask the pop-up questions for associating."
				+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848312' target='_blank'>"
				+ "Click here to open TestRail: 848312</a><br>");			
				Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Guest account data should auto fill, it should ask the pop-up questions for associating."); 
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
				test_steps.add("========= VERIFYING MAILING INFORMATION ==========");

				getTestSteps = cpRes.verifyMailingInformation(driver, address, address, address, city, country, state,
						postalcode);
				test_steps.addAll(getTestSteps);

				test_steps.add("========= VERIFYING CONTACT INFORMATION ==========");
				cpRes.verifyContactInfoAfterAssociateAccount(driver, guestFirstName, guestLastName, guestFirstName,
						guestLastName, email, phonenumber, alternativeNumber, test_steps);

				if (!useMailingInfo.equalsIgnoreCase("Yes")) {
					test_steps.add("========= VERIFYING PAYMENT DETAILS IN BILLING ==========");
					String billingGuestName = billingFirstName + " " + billingLastName;
					cpRes.verifyPaymentDetailAfterAssociateAccount(driver, paymentMethod, cardNumber, billingGuestName,
							expDate, test_steps);
				} else {
					test_steps.add("========= VERIFYING PAYMENT DETAILS IN BILLING ==========");
					cpRes.verifyPaymentDetailAfterAssociateAccount(driver, paymentMethod, cardNumber, guestName,
							expDate, test_steps);
				}
				if (!useMailingInfo.equalsIgnoreCase("Yes")) {
					boolean isChecked = cpRes.sameAsMailingAddressCheckBox(driver);
					Assert.assertEquals(isChecked, false, "Faled to verify");
					test_steps.add("Same is Mailing address not checked in Billing Section");

					test_steps.add("========= VERIFYING BILLING INFORMATION ==========");
					cpRes.verifyBillingFirstAndLastName(driver, billingSalutation, billingFirstName, billingLastName,
							test_steps);
					cpRes.verifyBillingInfo(driver, address, address, address, city, state, postalcode, country,
							test_steps);
				} else {
					test_steps.add("========= VERIFYING SAME AS MAILING CHECKBOX ==========");
					boolean isChecked = cpRes.sameAsMailingAddressCheckBox(driver);
					Assert.assertEquals(isChecked, true, "Faled to verify");
					test_steps.add("Same is Mailing address checked in Billing Section");

				}

				test_steps.add("========= VERIFYING MARKETING INFORMATION ==========");
				cpRes.verifyMarketingInformation(driver, marketSegment, referral);
				test_steps.add("Verified Marketing info Successfully");

				test_steps.add("========= VERIFYING NOTE ==========");
				cpRes.verifyNoteAddedFromAccount(driver, noteType, subject, details, test_steps);
				
				/*test_steps.add(
						"verify Copied Notes are not getting displayed in the reservation when user attaches a guest profile having notes to an existing reservation.."
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848387' target='_blank'>"
								+ "Click here to open TestRail: 848387</a><br>");			
				  Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "verify Copied Notes are not getting displayed in the reservation when user attaches a guest profile having notes to an existing reservation."); 
				  Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);								  
			*/	cpRes.clickBookNow(driver, test_steps);
				confirmationNum = cpRes.get_ReservationConfirmationNumber(driver, test_steps);
				cpRes.clickCloseReservationSavePopup(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);
				test_steps.add("Sucess - Able to asscoate guest account and create reservation" + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/NG-9569'>" + "Click here to open JIRA: NG-9569</a>");
				}
			if (scenario.equalsIgnoreCase("SearchInReservation") && isModifyAccount.equalsIgnoreCase("Yes")) {

				nav.GuestHistory(driver);
				test_steps.add("Navigate to Guest History");
				guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
				test_steps.add("============== VERIFY ROOM AND RESERVATION COUNT IN ACCOUNT PROFILE =============");
				guestHistory.verifyRoomAndReservation(driver, roomCount, resCount, test_steps);
					test_steps.add("============== MODIFY FIRST AND LAST NAME OF ACCOUNT =============");
				guestHistory.OpenSearchedGuestHistroyAccount(driver);
				test_steps.add("Open Guest History Account");

				//updatedGuestFName = baseFName + Utility.generateRandomStringWithGivenLength(3);
				//updatedGuestLName = baseLName + Utility.generateRandomStringWithGivenLength(3);
				guestName = guestFirstName + " " + guestLastName;
				guestHistory.enterFirstName(driver, salutation, updatedGuestFName, updatedGuestLName, test_steps);
				guestHistory.clickSave(driver);
				guestHistory.closeAccount(driver);
				test_steps.add("Save and Close Updated Account");
				test_steps.add("============== VERIFY MODIFY ACCOUNT SEARCHED IN RESERVATION =============");
				nav.reservation(driver);
				test_steps.add("Navigate to Reservation Tab");
				cpRes.click_NewReservation(driver, test_steps);
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);

				test_steps.add("========= VERIFYING FIND GUEST PROFILE SUGGESTION AFTER MODIFICATION ==========");
				boolean expectedResult = true;
				cpRes.verifyEnterFindGuestProfilewithFistName(driver, updatedGuestFName, expectedResult, test_steps);
				cpRes.verifyEnterFindGuestProfilewithLastName(driver, updatedGuestLName, expectedResult, test_steps);
				//newly added to satisfy existing reservation for guest name--
				cpRes.verifyEnterFindGuestProfilewithGuestName(driver, updatedGuestFName+ " "+ updatedGuestLName, expectedResult,
						test_steps);
				cpRes.clickAutoSuggestedGuest(driver, updatedGuestFName);
				cpRes.clickRemoveGuestProfile(driver, test_steps);
				cpRes.verifyEnterFindGuestProfilewithFistName(driver, updatedGuestFName, expectedResult, test_steps);
				System.out.println("Reached");
				cpRes.close_FirstOpenedReservation(driver, test_steps);
				test_steps.add("========= OPEN EXISTING RESERVATION =========: " + confirmationNum);
				//cpRes.SearchandClickgivenReservationNumber(driver, confirmationNum);
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNum);
				test_steps.add("========= VERIFYING RESERVATION CREATED ABOVE " + confirmationNum + " "
						+ "NOT IMPACTED BY MODIFYING GUEST PROFILE" + "==========");
				cpRes.verifyGuestNameAfterReservation(driver, salutation + " " + guestName);
				test_steps.add("Guest Name verified in Reservation: " + guestName);
				cpRes.clickGuestInfo(driver, test_steps);
				cpRes.clickRemoveGuestProfile(driver, test_steps);
				cpRes.clickAutoSuggestedGuest(driver, updatedGuestLName);
				//cpRes.verifyGuestNameAfterReservation(driver, salutation + " " + updatedGuestFName+ " "+ updatedGuestLName);
				cpRes.verifyGuestNameAfterReservation(driver,  updatedGuestFName+ " "+ updatedGuestLName);
				test_steps.add("Guest Name verified in Reservation after updated guest name selected in reservation: " + salutation + " " + updatedGuestFName+ " "+ updatedGuestLName);
				test_steps.add("New Guest associated to the reservation: " + updatedGuestFName);
				test_steps.add("========= VERIFYING RESERVATION HISTORY LOG ==========");
				cpRes.clickOnHistory(driver);
				test_steps.add("Click on History Tab");

				cpRes.verifyUnneceessaryHistoryLog(driver, test_steps);

				cpRes.clickOnDetails(driver);
				test_steps.add("========= VERIFYING REQUIRED RED BORDER ON PAYMENT METHOD SECTION ==========");
				cpRes.clickGuestInfo(driver, test_steps);
				cpRes.clickRemoveGuestProfile(driver, test_steps);
				cpRes.clickAutoSuggestedGuest(driver, updatedGuestFName);
				test_steps.add("Guest associated to the reservation: " + updatedGuestFName);
				cpRes.verifyPaymentRequiredBorder(driver, test_steps);
				cpRes.close_FirstOpenedReservation(driver, test_steps);

			}
			if (scenario.equalsIgnoreCase("SearchInReservation") && isDeleteAccount.equalsIgnoreCase("Yes")) {
				test_steps.add("========= DELETE GUEST ACCOUNT ==========");
				driver.navigate().refresh();
				if(isModifyAccount.equalsIgnoreCase("Yes")) {
					cpRes.deleteReservation(driver, confirmationNum, "Te", "Delete",
							updatedGuestFName+ " "+ updatedGuestLName,test_steps);
					nav.GuestHistory(driver);
					guestHistory.searchAccount(driver, updatedGuestFName, updatedGuestLName);
					guestHistory.deleteGuestHistoryAccount(driver);
					boolean guestAccountExist = guestHistory.isGuestHistoryAccountExist(driver, updatedGuestFName, test_steps);
					Assert.assertEquals(guestAccountExist, false, "Failed to verify");
					test_steps.add("Verified that guest history not exist anymore after delete: " + updatedGuestFName);
				}else {
					cpRes.deleteReservation(driver, confirmationNum, "Te", "Delete",
							guestFirstName+" "+guestLastName,test_steps);
					nav.GuestHistory(driver);
					guestHistory.searchAccount(driver, guestFirstName, guestLastName);
					guestHistory.deleteGuestHistoryAccount(driver);
					boolean guestAccountExist = guestHistory.isGuestHistoryAccountExist(driver, guestFirstName, test_steps);
					Assert.assertEquals(guestAccountExist, false, "Failed to verify");
					test_steps.add("Verified that guest history not exist anymore after delete: " + guestFirstName);
				}

				nav.reservation(driver);
				test_steps.add("Navigate to Reservation Tab");
				cpRes.click_NewReservation(driver, test_steps);
				cpRes.clickOnFindRooms(driver, test_steps);
				cpRes.selectRoomClass(driver, test_steps);
				cpRes.clickNext(driver, test_steps);

				test_steps.add("========= VERIFYING FIND GUEST PROFILE SUGGESTION ==========");
				boolean result = false;
				boolean getflag;
				if(isModifyAccount.equalsIgnoreCase("Yes")) {
					getflag = cpRes.verifyEnterFindGuestProfilewithFirstName(driver, updatedGuestFName, result, test_steps);
				
				}else {
				
					getflag = cpRes.verifyEnterFindGuestProfilewithFirstName(driver, guestFirstName, result, test_steps);
				}
							

			}
			if (scenario.equalsIgnoreCase("BackToGuestHistory")) {
				test_steps.add(
						"========= VERIFYING CREATE GUEST HISTORY AND TAKE BACK PAGE STILL ON GUEST HISTORY TAB ==========");
				driver.navigate().back();
				test_steps.add("Navigate to back page");
				boolean tab1 = guestHistory.verifyGuestHistoryTabSelected(driver, test_steps);
				test_steps.add(
						"========= VERIFYING CLICK NEW RES FROM GUEST HISTORY AND TAKE BACK PAGE STILL ON GUEST HISTORY TAB ==========");
				guestHistory.searchWithFirstName(driver, guestFirstName, test_steps);
				guestHistory.OpenSearchedGuestHistroyAccount(driver);
				test_steps.add("Search and Opened Extsing Guest Account with guest FirstName: " + guestFirstName);
				guestHistory.newReservation(driver);
				test_steps.add("Click new reservation button on Guest History Account page");
				driver.navigate().back();
				test_steps.add("Navigate to back page");
				boolean isGuestHistoryPage = guestHistory.verifyGuestHistoryTabSelected(driver, test_steps);
				test_steps.add(
						"========= VERIFYING TAKE BACK FROM GUEST HISTORY > GROUPS STILL ON GUEST HISTORY TAB ==========");
				if (isGuestHistoryPage) {

				} else {
					nav.GuestHistory(driver);
				}

				nav.groups(driver);
				test_steps.add("Click at Group Tab");
				driver.navigate().back();
				test_steps.add("Navigate to back page");
				boolean tab2 = guestHistory.verifyGuestHistoryTabSelected(driver, test_steps);
					
			}
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Guest History");
			}
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGuestHistoryAccount", gsexcel);
	}

	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
