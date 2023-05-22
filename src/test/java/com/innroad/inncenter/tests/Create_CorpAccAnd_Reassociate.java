package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class Create_CorpAccAnd_Reassociate extends TestCore{
	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String date = null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, SNExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void createReservationWithCorpAccount(String TestCaseID,String CheckInDate,String CheckOutDate,String propertyName,String adult,String children,String promoCode,
			String salutation,String guestFirstName,String guestLastName,String phoneNumber,String alternativePhone,String email,
			String address1,String address2,String address3,String city,String country,String state,String postalCode,String paymentType,String cardNumber,String nameOnCard,String cardExpDate,
			String marketSegment,String referral,
			String accountName,String accountType,
			String dateFormat,String roomClassName,String ratePlanName) throws ParseException, Exception {
		 boolean isExcecutable= Utility.getResultForCase(driver, TestCaseID);
         if(isExcecutable) {

		String testName = "CreateReservationWithCorpAccountAssociate";
		testDescription = "Create_CorpAcc <br>";
		testCategory = "Create_CorpAcc";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add(TestCaseID);
			caseId.add(TestCaseID);
			caseId.add(TestCaseID);
			caseId.add(TestCaseID);
			caseId.add(TestCaseID);
			
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		Account CreateTA = new Account();
		ReservationSearch reservationSearch = new ReservationSearch();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		
		String AccountName2=null;
		
		
		String randomNumber = Utility.GenerateRandomNumber();
		String reservationNumber=null;
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
			login_Group(driver);
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
		 // Get CheckIn, CheckOut and TaskDueOn Date
			if (!(Utility.validateInput(CheckInDate))) {
				CheckInDate="";
				CheckOutDate="";
			    for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
			        checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
			        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
			        if(i==0) {
			        	 CheckInDate=CheckInDate+""+checkInDates.get(i);
					        CheckOutDate=CheckOutDate+""+checkOutDates.get(i);
			        }else {
			        CheckInDate=CheckInDate+"|"+checkInDates.get(i);
			        CheckOutDate=CheckOutDate+"|"+checkOutDates.get(i);
			        }
			        }
			} else {
			    checkInDates = Utility.splitInputData(CheckInDate);
			    checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			/*CheckInDate = checkInDates.get(0);
			CheckOutDate = checkOutDates.get(0);
			TaskDueon = CheckOutDate;*/

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			date=Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
			app_logs.info(date);

			try {
				testSteps.add("****************** Gettirng room class abbrivation *********************");
				app_logs.info("****************** Gettirng room class abbrivation *********************");
				navigation.Setup(driver);
				navigation.RoomClass(driver);
				RoomAbbri1 = rc.getAbbrivation(driver, "|", roomClassName, testSteps);
				String[] Room=roomClassName.split("\\|");
				for(int i=0;i<Room.length;i++) {
					RoomAbbri.add(i, Room[i]+":"+RoomAbbri1.get(i));
				}
				Utility.app_logs.info(RoomAbbri);
				System.out.println(RoomAbbri);
				navigation.cpReservation_Backward(driver);
				System.out.println(RoomAbbri);
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
				} else {
					Assert.assertTrue(false);
				}
			}



			// navigate to accounts
			try {

				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Accounts", testName, "NavigateAccounts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate " + "s", testName, "NavigateAccounts", driver);
				} else {

					Assert.assertTrue(false);
				}
			}


		String accountNumber = null;
		// New account
		try {
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			accountName="CorpAccOne"+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, accountType);
			CreateTA.AccountDetails(driver, accountType, accountName);
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

		// Clicking on New Reservation
		try {
			CreateTA.NewReservationButton(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation", testName, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Reservation
		try {
			testSteps.add("========== CREATING RESERVATION ==========");
			app_logs.info("========== CREATING RESERVATION ==========");
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, 1);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.selectRateplan(driver, ratePlanName, promoCode, 1);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, roomClassName, "Yes", "");
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			
			
			String policyYes = "//p[contains(text(),'Based on the changes made, new policies are applicable')]/../following-sibling::div//button[contains(text(),'Yes')]";

			try {
				Wait.waitForElementToBeVisibile(By.xpath(policyYes), driver,20);
				driver.findElement(By.xpath(policyYes)).click();
			} catch (Exception e) {
				// TODO: handle exception
			}
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
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			reservationPage.get_ReservationStatus(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			testSteps.add("========== CLOSE ACCOUNT ==========");
			app_logs.info("========== CLOSE ACCOUNT ==========");
				navigation.Accounts(driver);
				CreateTA.close_Account(driver);
			
			} catch (Exception e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to close account", testName, "CloseAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to close account", testName, "CloseAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
		
		
		try {
			testSteps.add("========== Creating another account ==========");
			app_logs.info("========== Creating another account ==========");
			AccountName2="CorpAcc2"+Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, accountType);
			CreateTA.AccountDetails(driver, accountType, AccountName2);
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
			getTestSteps = CreateTA.AccountSave(driver, test, AccountName2, getTestSteps);
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
		
		try {
			testSteps.add("========== CLOSE ACCOUNT ==========");
			app_logs.info("========== CLOSE ACCOUNT ==========");
				navigation.Accounts(driver);
				CreateTA.close_Account(driver);
			
			} catch (Exception e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to close account", testName, "CloseAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to close account", testName, "CloseAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
		try {
		navigation.cpReservationBackward(driver);
		testSteps.add("Navigate to Reservations");
		app_logs.info("Navigate to Reservations");
		reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
		reservationPage.clickGuestInfo(driver, testSteps);
		reservationPage.associateAccountInReservation(driver, testSteps, AccountName2, accountType);
		reservationPage.clickFolio(driver, testSteps);
		reservationPage.selectFolioOption(driver, AccountName2);
		folio.clickAddLineItemButton(driver);
		folio.AddFolioLineItem(driver, "Room Service", "10");
		folio.ClickSaveFolioItemsButton(driver);
		Wait.wait25Second();
		reservationPage.selectFolioOption(driver, AccountName2);
		folio.VoidLineItem(driver, "Room Service");
		reservationPage.verifyDisplayVoidLineItems(driver, testSteps, "Void It", "Room Service");
		comments="Impact: Verify deleted account folio item should be shown in the Accounts Folio on selecting checkbox Display Voided Items";
		
		reservationPage.closeReservationTab(driver);
		testSteps.add("Close Reservation");
		app_logs.info("Close Reservation");
		
		navigation.Accounts(driver);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			testSteps.add("========== SEARCH ACCOUNT AND VERIFY RESERVATION EXISTS ==========");
			app_logs.info("========== SEARCH ACCOUNT AND VERIFY RESERVATION EXISTS ==========");
			CreateTA.selectAccountType(driver, accountType);
			CreateTA.account_Search(driver, testSteps, accountName);
			CreateTA.click_reservationTab(driver, testSteps);
			CreateTA.verify_reservationNotInAccpountResewrvation(driver, testSteps, reservationNumber);
			comments="Verify that the reservation is removed from the reservation list of the account if the user associate the same reservation to any other account.";
			CreateTA.ClickFolio(driver);
			CreateTA.addLineItems(driver, test, testSteps);		
			CreateTA.ageingPaymentAdvanceDeposit_Pay(driver, test, "", paymentType, nameOnCard, cardNumber, cardExpDate, "123", "Capture", "No", "", testSteps);
			//CreateTA.Save(driver);
			comments=comments+"Verified Ageing functionality by making payment with Adv.Dep Link";
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to search account", testName, "SearchAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to search account", testName, "SearchAccount", driver);
				} else {

					Assert.assertTrue(false);
				}
			}

	
		
		try {
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
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
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Create_CorpDeAcc", SNExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
