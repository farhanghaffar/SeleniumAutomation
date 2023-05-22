package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
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
import com.innroad.inncenter.pageobjects.GuestHistory;
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

public class Create_CorpAccAnd_Associate extends TestCore{
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
		if (!Utility.isExecutable(testName, SanityExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void createReservationWithCorpAccount(String TestCaseID,String CheckInDate,String CheckOutDate,String propertyName,String adult,String children,String promoCode,
			String salutation,String guestFirstName,String guestLastName,String phoneNumber,String alternativePhone,String email,
			String address1,String address2,String address3,String city,String country,String state,String postalCode,String paymentType,String cardNumber,String nameOnCard,String cardExpDate,
			String marketSegment,String referral,
			String accountName,String accountType,
			String dateFormat,String roomClassName,String ratePlanName) throws ParseException, Exception {
		boolean isExecutable=Utility.getResultForCase(driver, TestCaseID);
		if(isExecutable) {

		String testName = "CreateReservationWithCorpAccount";
		testDescription = "Create_CorpAcc <br>";
		testCategory = "Create_CorpAcc";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("824578");
			caseId.add("824639");
			caseId.add("824842");
			caseId.add("824895");
			caseId.add("824899");
			caseId.add("825298");
			caseId.add("825299");
			caseId.add("825327");
			caseId.add("825328");
			caseId.add("824956");
			caseId.add("825095");
			caseId.add("825329");
			caseId.add("825472");
			caseId.add("825473");
			
	
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
		GuestHistory guestHistory = new GuestHistory();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		
		String calendarTodayDate = null;
		
		
		String randomNumber = Utility.GenerateRandomNumber();
		String reservationNumber=null;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		int totalRoomsExtend = 2;

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
			accountName=accountName+Utility.generateRandomString();
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
		
			
			String policyYes = "//p[contains(text(),'Based on the changes made, new policies are applicable')]/../following-sibling::div//button[contains(text(),'Yes')]";

			try {
				Wait.waitForElementToBeVisibile(By.xpath(policyYes), driver,20);
				driver.findElement(By.xpath(policyYes)).click();
			} catch (Exception e) {
				// TODO: handle exception
			}

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);	
			
			//reservationPage.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,phoneNumber,alternativePhone,email,"","",address1,address2,address3,city,country,state,postalCode,"Yes");

			/*getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);*/

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			
			//if((accountName.equalsIgnoreCase("")||accountName.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
		//	}
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			
			getTestSteps.clear();
			reservationPage.get_ReservationStatus(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
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
		navigation.GuestHistory(driver);
		//guestHistory.searchAccount(driver, guestFirstName, guestFirstName);
		guestHistory.searchAccountWithCombine(driver, guestFirstName, guestFirstName, testSteps);
		guestHistory.verifyAccountDisplayedInSearchResult(driver, guestFirstName,guestLastName, testSteps);
		comments="Verified search for Guest profile with account name. ";
		comments=comments+"Verified search for Guest profile with account name by enable combine check box. ";
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
			testSteps.add("========== SEARCH ACCOUNT AND VERIFY RESERVATION EXISTS ==========");
			app_logs.info("========== SEARCH ACCOUNT AND VERIFY RESERVATION EXISTS ==========");
		//	CreateTA.checkForAccountExistsAndOpen(driver, testSteps, accountName, accountType, true);
			//CreateTA.searchForAnAccount(driver, testSteps, accountType, accountName);
			//CreateTA.searchForAnAccountAndOpen(driver, testSteps, accountName, accountType);
			CreateTA.selectAccountType(driver, accountType);
			CreateTA.account_Search(driver, testSteps, accountName);
			comments=comments+"Searched the account for the account name"+accountName+". ";
			CreateTA.click_reservationTab(driver, testSteps);
			CreateTA.verify_reservationinAccpountResewrvation(driver, testSteps, reservationNumber);
			comments=comments+"Verified the reservation is displayed  with Reservation NO: "+reservationNumber +"in the Accounts page for the Account: "+accountName+ ". ";
				
			
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
			testSteps.add(
					"******************** Select Charge Routing Type As: All Items*****************************");
			CreateTA.navigateFolio(driver);
			CreateTA.navigateFolioOptions(driver);
			testSteps.add("Navigate Folio Optiopns");
			CreateTA.select_ChargeRouting(driver, "All Items");
			testSteps.add("Select charge routing as All Items Successfully");
			CreateTA.Save(driver);
			testSteps.add("Save Account");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to save account", testName, "Failed to save account", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to save account", testName, "Failed to save account", driver);
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
			app_logs.info("========= EXTEND RESERVATION ========");
			testSteps.add("========= EXTEND RESERVATION ========");
			navigation.cpReservationBackward(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");
			checkInDates.clear();
			checkOutDates.clear();
			checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
	        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(totalRoomsExtend), "MM/dd/yyyy", "dd/MM/yyyy"));
	       // checkOutDates.add(Utility.GetNextDate(totalRoomsExtend, "MMM dd, yyyy"));
	        CheckInDate = checkInDates.get(0);
			CheckOutDate =checkOutDates.get(0);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MM/dd/yyyy"));
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");
			
			comments=comments+"Extended the corporate account reservation when charge routing is set to ALL. ";

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {
			app_logs.info("========= REDUCE RESERVATION ========");
			testSteps.add("========= REDUCE RESERVATION ========");
			//navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");
			totalRoomsExtend = 1;
			checkInDates.clear();
			checkOutDates.clear();
			checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
	        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(totalRoomsExtend), "MM/dd/yyyy", "dd/MM/yyyy"));
	        //checkOutDates.add(Utility.GetNextDate(totalRoomsExtend, "MMM dd, yyyy"));
	        CheckInDate = checkInDates.get(0);
			CheckOutDate =checkOutDates.get(0);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, Utility.parseDate(CheckOutDate, "dd/MM/yyyy", "MM/dd/yyyy"));
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");
			
			comments=comments+"Reduced the corporate account reservation when charge routing is set to ALL. ";

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
		driver.navigate().refresh();
		reservationPage.click_NewReservation(driver, testSteps);
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
		getTestSteps = reservationPage.selectRoom1(driver, getTestSteps, roomClassName, "Yes", "");
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.clickNext(driver, getTestSteps);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, true);
		testSteps.addAll(getTestSteps);
		
		

		getTestSteps.clear();
		getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
				guestLastName);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.selectReferral(driver, referral);
		testSteps.addAll(getTestSteps);

	
		reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
			testSteps.addAll(getTestSteps);
			
		getTestSteps.clear();
		getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		String reservationNumber1 = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		reservationPage.get_ReservationStatus(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		reservationPage.clickGuestInfo(driver, testSteps);

		reservationPage.associateAccountInReservation(driver, testSteps, accountName, accountType);
		comments=comments+"Verified address auto suggestion in the reservation detail page for corpoarte account. ";
		reservationPage.verifyAssociatedAccount(driver, accountName);
		comments=comments+ "Associated Account to existing reservation with reservation NO: "+reservationNumber1+ " to Account: "+accountName+ ". ";
		reservationPage.editPaymentInfoAndMakeReservationAsPayment(driver, testSteps, reservationNumber);
		Wait.wait10Second();
		reservationPage.click_Folio(driver, testSteps);
	
		//String folioAccountName=accountName+" ("+accountNumber+")";
		String folioAccountName=accountName;
		reservationPage.verify_AccountFolio(driver, testSteps, folioAccountName);
		comments=comments+"Verified the Account Folio items showing for the reservation edited and associated with corp account.";
		
		String amountPaid=reservationPage.paywithRes(driver, testSteps, reservationNumber);
		comments=comments+"Verified reservation auto-suggestion in the reservation folio payment info pop-up";
		reservationPage.closeReservationTab(driver);
		testSteps.add("Close Reservation");
		app_logs.info("Close Reservation");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed Folio verification", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed Folio verification", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
		navigation.Accounts(driver);
		//CreateTA.close_Account(driver);
		driver.navigate().refresh();
	

		testSteps.add("========== SEARCH ACCOUNT  ==========");
		app_logs.info("========== SEARCH ACCOUNT  ==========");
		CreateTA.selectAccountType(driver, accountType);
		CreateTA.account_Search(driver, testSteps, accountName);
		
		CreateTA.NewReservationButton(driver, test);
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
		getTestSteps = reservationPage.selectRoom1(driver, getTestSteps, roomClassName, "Yes", "");
		testSteps.addAll(getTestSteps);
		
		String policyYes = "//p[contains(text(),'Based on the changes made, new policies are applicable')]/../following-sibling::div//button[contains(text(),'Yes')]";

		try {
			driver.findElement(By.xpath(policyYes)).click();
		} catch (Exception e) {
			// TODO: handle exception
		}


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
	
		reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
		testSteps.addAll(getTestSteps);
	
		
		getTestSteps.clear();
		getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		reservationPage.get_ReservationStatus(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		
		reservationPage.click_Folio(driver, testSteps);
		folio.ApplyRouting(driver, testSteps);
		reservationPage.inHouseReservation(driver);
		comments=comments+"verified the check in modal functionality when we click on cancel/confirm in the dirty tab pop up window button account(corporate) reservation";
		reservationPage.click_Folio(driver, testSteps);
		reservationPage.selectFolioOption(driver, accountName);
		getTestSteps.clear();
		getTestSteps =folio.verifyPostStatus(driver, getTestSteps);
		testSteps.addAll(getTestSteps);
		comments=comments+" Verified line item realization in other folio's (account) while checking in. ";
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed checkin functionality", testName, "CheckinFunctionality", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed checkin functionality", testName, "CheckinFunctionality", driver);
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
		return Utility.getData("Create_CorpAcc",SanityExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
