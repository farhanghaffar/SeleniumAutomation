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

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateReservationWithGiftCertificateAccount1698 extends TestCore{
	private WebDriver driver = null;
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void createReservationWithGiftCertificateAccount1698(String url, String ClientCode, String Username, String Password,String PropertyName,String CheckInDate,String CheckOutDate,String adults,String children,String Rateplan,String PromoCode,String IsSplitRes,
			String roomClassName,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String IsAddMoreGuestInfo,String salutation,String guestFirstName,String guestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String paymentType,String cardNumber,String nameOnCard,String cardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus,
			String AccountName,String Accounttype,String TakePaymentType,String LineItemCategory,String LineItemCategoryAmount) throws InterruptedException, IOException {

		String testName = "CreateReservationWithGiftCertificateAccount1698";
		test_description = "Create_GiftAccAnd <br>";
		test_catagory = "Create_GiftAccAnd";
		ScriptName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation nav = new Navigation();
		String reservationNumber=null;
		String status=null;
	
		Account CreateTA = new Account();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginWPI(driver);
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


		// navigate to accounts
		try {

			nav.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String AccountNumber = null;
		// New account
		try {
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			AccountName=AccountName+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, Accounttype);
			CreateTA.AccountDetails(driver, Accounttype, AccountName);
			AccountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, AccountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, AccountName, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on New Reservation
		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click New Reservation", testName, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		Account=AccountName;
		String bal=null;
		// Reservation
		try {
			testSteps.add("========== Creating reservation ==========");
			app_logs.info("========== Creating reservation ==========");

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, 1);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, roomClassName, "Yes", "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
			reservationPage.clickBookNow(driver, testSteps);
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			reservationPage.get_ReservationStatus(driver, testSteps);
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			testSteps.add("****************** verifying payment in account *********************");
			app_logs.info("****************** verifying payment in account *********************");
				nav.Accounts(driver);
				CreateTA.close_Account(driver);
				CreateTA.selectAccountType(driver, Accounttype);
				CreateTA.account_Search(driver, testSteps, AccountName);
				CreateTA.navigateFolio(driver);
				CreateTA.verify_AccountResservationPayment(driver, testSteps, reservationNumber);
				String accBal=CreateTA.get_FilioBalance(driver, test);
				CreateTA.click_reservationTab(driver, testSteps);
				CreateTA.verify_reservationinAccpountResewrvationTab(driver, testSteps, reservationNumber);
				CreateTA.verifyAccountAndResBalance(driver, testSteps, bal, accBal);
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
				} else {

					Assert.assertTrue(false);
				}
			}	
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateResWithGiftAcc1698", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		driver.quit();
	}
}
