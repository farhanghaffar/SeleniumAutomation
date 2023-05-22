package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
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
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class AssociateAccountsToMRBReservation  extends TestCore {
	


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
	ArrayList<String> comments=new ArrayList<String>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");

		if (!Utility.isExecutable(testName, excel_Swarna))

			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void associateAccountsToMRBReservation(String testcaseId,String CheckInDate,String CheckOutDate,String propertyName,String adult,String children,String promoCode,

			String salutation,String guestFirstName,String guestLastName,String phoneNumber,String alternativePhone,String email,
			String address1,String address2,String address3,String city,String country,String state,String postalCode,String paymentType,String cardNumber,String nameOnCard,String cardExpDate,
			String marketSegment,String referral,
			String accountName,String accountType,
			String dateFormat,String roomClassName,String ratePlanName,
			String res_Adults,String res_Children,String res_Rateplan,String res_PromoCode,String res_IsSplitRes,
			String res_RoomClass,String res_IsAssign,String res_IsDepositOverride,String res_DepositOverrideAmount,
			String res_IsAddMoreGuestInfo,String res_Salutation,String res_GuestFirstName,String res_GuestLastName,String res_PhoneNumber,String res_AltenativePhone,String res_Email,String res_Account,String res_AccountType,
			String res_Address1,String res_Address2,String res_Address3,String res_City,String res_Country,String res_State,String res_PostalCode,
			String res_IsGuesProfile,String res_PaymentType,String res_CardNumber,String res_NameOnCard,String res_CardExpDate,String res_IsChangeInPayAmount,
			String res_ChangedAmountValue,String res_TravelAgent,String res_MarketSegment,String res_Referral) throws ParseException, Exception {
		

		String testName = this.getClass().getSimpleName().trim();
		
	  //  String testcaseId="848400|848401";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, testcaseId);
		
		testDescription = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848400' target='_blank'>"
				+ "Click here to open TestRail: C848400</a><br>"
				+"<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848401' target='_blank'>"
				+ "Click here to open TestRail: C848401</a><br>";
		
		testCategory = "Account Reservation";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		

		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage res = new CPReservationPage();
		Folio folio = new Folio();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		Account CreateTA = new Account();
		ReservationSearch reservationSearch = new ReservationSearch();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		GuestHistory guestHistory = new GuestHistory();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		String calendarTodayDate = null;
		
		accountName=accountName+Utility.generateRandomString();
		String randomNumber = Utility.GenerateRandomNumber();
		String reservationNumber=null;
		String status=null;
		String roomCost=null;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		int totalRoomsExtend = 2;
		ArrayList<String> Rooms = new ArrayList<String>();

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
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < res_GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}

			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

			//TaskDueon = checkOutDates.get(0);
			
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}
			

			

		String accountNumber = null;
		// New account
		if(!accountType.equalsIgnoreCase("Group"))
		{
		try {
			
			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			
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
			getTestSteps.clear();
			if(accountType.equalsIgnoreCase("Unit Owners"))
			{
			CreateTA.associateRooms(driver, getTestSteps, "Junior Suites");
			getTestSteps = CreateTA.AccountSave(driver, test, accountName, getTestSteps);
			testSteps.addAll(getTestSteps);
			}
			

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
		
		}
		
		if(accountType.equalsIgnoreCase("Group"))
		{
		// Clicking on Groups
				try {
					
					navigation.secondaryGroupsManu(driver);
					testSteps.add("Navigate Groups");
					app_logs.info(" Navigate Groups");
				
					app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
					testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
					getTestSteps.clear();
					getTestSteps = group.enterrGroupName(driver, accountName);
					testSteps.addAll(getTestSteps);

				
					accountNumber = Utility.GenerateRandomString15Digit();
					getTestSteps.clear();
					getTestSteps = group.enterAccountNo(driver, accountNumber);
					testSteps.addAll(getTestSteps);
				
					getTestSteps.clear();
					getTestSteps = group.selectAccountAttributes(driver, marketSegment,referral);
					testSteps.addAll(getTestSteps);
				
					getTestSteps.clear();
					
					getTestSteps = group.enterMailingAddress(driver, guestFirstName, guestLastName, phoneNumber,
							address1, city, state,country, postalCode);
					testSteps.addAll(getTestSteps);

				
					getTestSteps.clear();
					getTestSteps = group.Billinginfo(driver);
					testSteps.addAll(getTestSteps);
				
					getTestSteps.clear();
					getTestSteps = group.clickOnSave(driver);
					testSteps.addAll(getTestSteps);

				
			
					app_logs.info("==========CREATE NEW BLOCK==========");
					testSteps.add("==========CREATE NEW BLOCK==========");

					getTestSteps.clear();
					getTestSteps = group.navigateRoomBlock(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickNewBlock(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.EnterBlockName(driver, "Block_Name");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickOkay_CreateNewBlock(driver);
					testSteps.addAll(getTestSteps);

					app_logs.info("==========SEARCH ROOMS==========");
					testSteps.add("==========SEARCH ROOMS==========");

					getTestSteps.clear();
					
					/*getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("MMM dd, yyyy"),
							Utility.GetNextDate(1, "MMM dd, yyyy"));
					*/
					getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("dd/MM/yyyy"),
							Utility.GetNextDate(1, "dd/MM/yyyy"));
					
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectRatePlan(driver, ratePlanName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectAdults(driver, adult);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectChilds(driver, children);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.EnterNights(driver, "1");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickSearchGroup(driver);
					testSteps.addAll(getTestSteps);

					advanceGroup.updatedAutomaticallyAssignedRooms(driver, "1");
					advanceGroup.BlockRoomForSelectedRoomclass(driver, "1", roomClassName);

					getTestSteps.clear();
					getTestSteps = advanceGroup.ClickCreateBlock(driver, getTestSteps);
					testSteps.addAll(getTestSteps);

					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
					
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
		}


		// Reservation
		//IsSplitRes,IsAssign,IsGuesProfile,IsAddMoreGuestInfo,Rooms,TravelAgent
				try {
					Wait.wait10Second();
					try
					{
						navigation.ReservationV2_Backward(driver);
					}
					catch(Exception e)
					{
					navigation.cpReservationBackward(driver);
					}
					catch(Error e)
					{
						navigation.cpReservationBackward(driver);
					}
					res.click_NewReservation(driver, getTestSteps);
					res.select_Dates(driver, getTestSteps, CheckInDate, CheckOutDate, res_Adults, res_Children, res_Rateplan, res_PromoCode,res_IsSplitRes);
					if(res_IsSplitRes.equalsIgnoreCase("Yes")) {
						res.enter_Adults(driver, getTestSteps, res_Adults);
						res.enter_Children(driver, getTestSteps, res_Children);
						res.select_Rateplan(driver, getTestSteps, res_Rateplan, res_PromoCode);
					}
					res.clickOnFindRooms(driver, getTestSteps);
					res.select_MRBRooms(driver, getTestSteps, res_RoomClass, res_IsAssign,accountName);
					//depositAmount=res.deposit(driver, getTestSteps, IsDepositOverride, DepositOverrideAmount);
					res.clickNext(driver, getTestSteps);
					if(accountType.equalsIgnoreCase("Travel Agent"))
					{
						res_TravelAgent=accountName;
						accountName="";	
					}
					res.enter_MRB_MailingAddress(driver, getTestSteps, res_Salutation, res_GuestFirstName, res_GuestLastName, res_PhoneNumber, res_AltenativePhone, res_Email, 
							accountName, accountType, res_Address1, res_Address2, res_Address3, res_City, res_Country, res_State, res_PostalCode, res_IsGuesProfile, res_IsAddMoreGuestInfo, res_IsSplitRes,Rooms);
					if((accountName.equalsIgnoreCase("")||accountName.isEmpty())) {
						res.enter_PaymentDetails(driver, getTestSteps, res_PaymentType, res_CardNumber, res_NameOnCard, res_CardExpDate);
					}
					System.out.println(Rooms);
					res.enter_MarketSegmentDetails(driver, getTestSteps, res_TravelAgent, marketSegment, referral);
					res.clickBookNow(driver, getTestSteps);
					reservationNumber=res.get_ReservationConfirmationNumber(driver, getTestSteps);
					status=res.get_ReservationStatus(driver, getTestSteps);
					res.clickCloseReservationSavePopup(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
					
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				
				try {
					
					if(!accountType.equalsIgnoreCase("Group"))
					{

						/*
					navigation.Accounts(driver);
					CreateTA.searchForAnAccountAndOpen(driver, testSteps,accountName, accountType);
					CreateTA.click_reservationTab(driver, testSteps);
					Wait.wait10Second();
					CreateTA.verify_reservationinAccpountResewrvation(driver, testSteps, reservationNumber);
					Wait.wait10Second();
					*/
					statusCode.add(0, "1");

					comments.add(0, "Verified account reservation");
					}
					else
					{

						/*
						navigation.secondaryGroupsManu(driver);
						group.searchGroupAccount(driver, accountName, true, testSteps);
						group.clickOnGroupsReservationTab(driver);
						Wait.wait10Second();
						group.verifyReservationInResTab(driver, reservationNumber, 1);
						Wait.wait10Second();
						*/
						statusCode.add(1, "1");

						comments.add(1, "Verified group reservation");
					}

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

				
		try {
			
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
		return Utility.getData("AssociateAccountsToMRBRes", excel_Swarna);

	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}


}
