package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

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
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyUnassignedReservation extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, HS_EXCEL))
			throw new SkipException("Skipping the test - " + test_name);
	}

	ReservationSearch reservationSearch = new ReservationSearch();
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationHomePage homePage = new ReservationHomePage();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	DerivedRate derivedRate = new DerivedRate();

	@Test(dataProvider = "getData")
	public void verifyUnassignedReservation(String TestCaseID, String CheckInDate, String CheckOutDate,
			String RatePlanName, String ReservationRoomClass) throws ParseException {
		test_name = "Verify whether user is able to create unassigned reservations";
		test_description = "Verify whether user is able to create unassigned reservations<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824685'>"
				+ "Click here to open TestRail: C824685</a>" + "<br>"
				
				+ "Verify auto suggestion in the reservation detail page<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824685'>"
				+ "Click here to open TestRail: C824685</a>"
				
				+ "<br>Verify the Calendar is same in all places in innCenter that have a calendar date picker.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824806'>"
				+ "Click here to open TestRail: C824806</a>"
				
				+ "<br>Verify user able to select any address from list of matching address.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824808'>"
				+ "Click here to open TestRail: C824808</a>"

				+ "<br>Verify if user select checkbox 'Same as Contact Info' for billing information, address should copied.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824809'>"
				+ "Click here to open TestRail: C824809</a>"
				
				+ "<br>Verify auto suggest work for account name and number.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824812'>"
				+ "Click here to open TestRail: C824812</a>" 
				
				+ "<br>Add optional billing address fields<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824813'>"
				+ "Click here to open TestRail: C824813</a>" 
				
				+ "<br>Verify Same as mailing address check box.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824814'>"
				+ "Click here to open TestRail: C824814</a>"
				
				+ "<br>Verify the information replace in Guest Info if user select YES in message shown.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824819'>"
				+ "Click here to open TestRail: C824819</a>"
				
				+ "<br>Verify the message after selection of agent from list.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824820'>"
				+ "Click here to open TestRail: C824820</a>"
				
				+ "<br>Verify auto suggestion box is not based on case sensitive characters.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824825'>"
				+ "Click here to open TestRail: C824825</a>"

				+ "<br>Verify that if the characters entered in the suggestion box match the characters in the displayed columns then the characters in the suggestion are displayed in bold..<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824837'>"
				+ "Click here to open TestRail: C824837</a>"

				+ "<br>Verify that the search results are sorted in the mentioned order of relevance for matches found.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824838'>"
				+ "Click here to open TestRail: C824838</a>"

				+ "<br>Verify that on hover the row of the search results highlights and clicking on the row selects that account as the payment method.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824839'>"
				+ "Click here to open TestRail: C824839</a>"

				+ "<br>Verify the HTML code should not show in the email body.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824896'>"
				+ "Click here to open TestRail: C824896</a>"

				+ "<br>Verify converting unassigned reservation into assigned room.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825335'>"
				+ "Click here to open TestRail: C825335</a>"
				
				+ "<br>Verify associating guest profile to a reservation with notes and tasks.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825332'>"
				+ "Click here to open TestRail: C825332</a>"
				
				+ "<br>Verify associating a guest profile to a reservation.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825331'>"
				+ "Click here to open TestRail: C825331</a>"
				
+ "<br>Change Unassigned Reservation to Assigned .<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848159'>"
+ "Click here to open TestRail: 848159</a>"

+ "<br>Verify Billing Address section fields and functionality.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848259'>"
+ "Click here to open TestRail: 848259</a>"

+ "<br>Verify popup comes when selecting data from auto suggestionbox.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848328'>"
+ "Click here to open TestRail: 848328</a>"

+ "<br>Book a reservation with Guest profile.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848373'>"
+ "Click here to open TestRail: 848373</a>"
				;
		test_catagory = "Reservation";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");
		String channelName = "innCenter";
		if(Utility.getResultForCase(driver, TestCaseID)) {
		Utility.initializeTestCase(TestCaseID, caseId, statusCode, comments, "785572");

		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name)+1);
				System.out.println(Utility.reTry.get(test_name));
			}

			// Login
			try {

				driver = getDriver();
//				loginRateV2(driver);
				HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		boolean isMinStayRule = false;
		boolean isMinStayRuleBrokenPopComeOrNot = false;
		ArrayList<String> minStayRule = null;
		ArrayList<String> minrule = null;
		ArrayList<String> noCheckInRule = null;
		ArrayList<String> noCheckOutRule = null;
		String checkInColor = null;
		String checkOutColor = null;
		int minStayRuleValue = 0;

		try {
			test_steps.add("=================== GETTING RATE PLAN RULES ======================");
			app_logs.info("=================== GETTING RATE PLAN RULES ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);
			try {
				rateGrid.expandParentRateGrid(driver, "plus");
			} catch (Exception e) {
				System.out.println("already expanded");
			}
			int days = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			Utility.app_logs.info("days : " + days);
			rateGrid.expandRoomClass(driver, test_steps, ReservationRoomClass);
			rateGrid.expandChannel(driver, test_steps, ReservationRoomClass, channelName);
			minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationRoomClass, channelName,
					days);
			minrule = minStayRule;

			Collections.sort(minrule);
			Utility.app_logs.info("minrule : " + minrule);

			minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

			if (minStayRuleValue > 0) {
				isMinStayRule = true;
				isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
						minStayRuleValue, days);
			}

			noCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, ReservationRoomClass,
					channelName, days);

			Utility.app_logs.info("noCheckInRule : " + noCheckInRule);

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRule, CheckInDate,
					CheckOutDate);

			Utility.app_logs.info("checkInColor : " + checkInColor);

			noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationRoomClass,
					channelName, days);

			Utility.app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRule,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColor : " + checkOutColor);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String Salutation = "Mr.";
		String GuestFirstName = "Test Res";
		String GuestLastName = Utility.generateRandomString();
		String PhoneNumber = "8790321567";
		String AltenativePhone = "8790321577";
		String Email = "innroadautomation@innroad.com";
		String Account = "";
		String Address1 = "test1";
		String Address2 = "test2";
		String Address3 = "test3";
		String City = "test";
		String Country = "United States";
		String PostalCode = "12345";
		String IsGuesProfile = "Yes";
		String PaymentType = "Cash";
		String CardNumber = "5454545454545454";
		String NameOnCard = "Test card";
		String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
		String IsChangeInPayAmount = "No";
		String ChangedAmountValue = "";
		String TravelAgent = "";
		String MarketSegment = "GDS";
		String State = "New York";
		String IsSplitRes = "No";
		String Referral = "Other";
		String AccountType = "Corporate/Member Accounts";
		String AccountName = "AccountName_";
		String MargetSegment = "GDS";
		String BlockName = "Test Block";
		String RoomPerNight = "1";
		String PromoCode = "";
		String reservationNo = null;

		// Reservation
		try {
			navigation.reservationBackward3(driver);
			test_steps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

			navigation.Accounts(driver);
			test_steps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to navigate Accounts", test_name, "NavigateAccounts", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to navigate Accounts", test_name, "NavigateAccounts", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		String accountNumber = null;
		// New account

		String TAAccountName = "TravalAgent" + Utility.generateRandomString();
		;
		String TAAccounttype = "Travel Agent";
		String GuestTAAccFirstName = "TA Guest";
		String GuestTAAccLastName = Utility.generateRandomString();
		try {
			test_steps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			TAAccountName = TAAccountName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, TAAccounttype);
			CreateTA.AccountDetails(driver, TAAccounttype, TAAccountName);
			accountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, accountNumber);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountAttributes(driver, test, MarketSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Mailinginfo(driver, test, GuestTAAccFirstName, GuestTAAccLastName, PhoneNumber,
					AltenativePhone, Address1, Address2, Address3, Email, City, State, PostalCode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountSave(driver, test, TAAccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			CreateTA.closeAccount(driver);
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create travel agent account", test_name, "TravelAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create travel agent account", test_name, "TravelAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String CorpAccountNumber = null;
		String CorpAccountName = "CorpAcc" + Utility.generateRandomString();
		;
		String CorpAccounttype = "Corporate/Member Accounts";
		String GuestCorpAccFirstName = "CorpAcc Guest";
		String GuestCorpAccLastName = Utility.generateRandomString();
		// New account
		try {
			test_steps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");
			CorpAccountName = CorpAccountName + Utility.generateRandomString();
			CreateTA.ClickNewAccountbutton(driver, CorpAccounttype);
			CreateTA.AccountDetails(driver, CorpAccounttype, CorpAccountName);
			CorpAccountNumber = Utility.GenerateRandomString15Digit();
			CreateTA.ChangeAccountNumber(driver, CorpAccountNumber);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountAttributes(driver, test, MarketSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Mailinginfo(driver, test, GuestCorpAccFirstName, GuestCorpAccLastName, PhoneNumber,
					AltenativePhone, Address1, Address2, Address3, Email, City, State, PostalCode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.Billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = CreateTA.AccountSave(driver, test, CorpAccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			CreateTA.closeAccount(driver);
		} catch (Exception e) {

			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create corporate account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Clicking on New Reservation
		try {
//			CreateTA.NewReservationButton(driver, test);
			navigation.accountToReservation(driver);

			reservationSearch.clickAdvanceReservationButton(driver);
			reservationSearch.verifyAdvanceSearchCalender(driver, test_steps, Utility.getCurrentDate("MM/dd/yyyy"),
					"MM/dd/yyyy");
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click new reservation", test_name, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click new reservation", test_name, "NavigateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add(
					"=================== CREATE RESERVATION AND VERIFY UNASSIGNED TO ASSIGNED RESERVATION ======================");

			app_logs.info("=================== CREATE RESERVATION AND UNASSIGNED RESERVATION ======================");
//			navigation.cpReservation_Backward(driver);
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, "10");
			reservationPage.enter_Children(driver, test_steps, "0");
			if (PromoCode.isEmpty()) {
				reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			} else {
				String rateplan = "Promo Code";
				reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
			}
			reservationPage.clickOnFindRooms(driver, test_steps);
			String minStayColor = "";
			if (isMinStayRule) {
				if (!isMinStayRuleBrokenPopComeOrNot) {
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps, ReservationRoomClass,
							minStayRuleValue);
					Utility.app_logs.info("minStayColor : " + minStayColor);
					try {
						assertTrue(minStayColor.equalsIgnoreCase("Red"),
								"Red color lable for minstay rule is not found");
						app_logs.info("Succesfully veried the red color lable for min stay rule");
						test_steps.add("Succesfully veried the red color lable for min stay rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps, ReservationRoomClass,
							minStayRuleValue);
					try {
						assertTrue(minStayColor.equalsIgnoreCase("green"),
								"green color lable for minstay rule is not found");
						app_logs.info("Succesfully veried the green color lable for min stay rule");
						test_steps.add("Succesfully veried the green color lable for min stay rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}
			}

			String noCheckinColor = "";
			if (checkInColor.equalsIgnoreCase("Red")) {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					Utility.app_logs.info("noCheckinColor");
					assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
							"red color lable for no check in rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check in rule");
					test_steps.add("Succesfully verified the red color lable for no check in rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkInColor.equalsIgnoreCase("Green")) {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckinColor.equalsIgnoreCase("green"),
							"green color lable for no check in rule is not found");
					app_logs.info("Succesfully verified the green color lable for no check in rule");
					test_steps.add("Succesfully verified the green color lable for no check in rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckinColor.equalsIgnoreCase("no color"), "no check in rule label is displayed");
					app_logs.info("Succesfully verified the no check in rule label not displayed");
					test_steps.add("Succesfully verified the no check in rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			String noCheckoutColor = "";
			if (checkOutColor.equalsIgnoreCase("Red")) {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
							"red color lable for no check out rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check out rule");
					test_steps.add("Succesfully verified the red color lable for no check out rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkOutColor.equalsIgnoreCase("Green")) {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
							"green color lable for no check out rule is not found");
					app_logs.info("Succesfully verified the green color lable for no check out rule");
					test_steps.add("Succesfully verified the green color lable for no check out rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("no color"), "no check out rule label is displayed");
					app_logs.info("Succesfully verified the no check out rule label not displayed");
					test_steps.add("Succesfully verified the no check out rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps, ReservationRoomClass, "No", "",
					noCheckinColor, noCheckoutColor, minStayColor, minStayRuleValue);
			double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
			reservationPage.clickNext(driver, test_steps);

			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			reservationPage.uncheck_BillingAddresSameAsMailingAddress(driver, test_steps, "false");
			reservationPage.uncheck_BillingAddresSameAsMailingAddress(driver, test_steps, "Yes");

			reservationPage.verifyBillingAddress(driver, Salutation, true, true, GuestFirstName, true, true,
					GuestLastName, true, true, Address1, true, true, Address2, true, true, Address3, true, true, City,
					true, true, Country, true, true, State, true, true, PostalCode, true, true, test_steps);

			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
			}

			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			String status = "RESERVED";
			reservationPage.clickBookNow(driver, test_steps);
			reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			String statusAfterRes = reservationPage.get_ReservationStatus(driver, test_steps);
			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
					"Successfully Verified Status After Reservation : " + status,
					"Failed to Verified Status After Reservation : " + statusAfterRes, true, test_steps);

			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			String acctual = reservationPage.get_RoomNumber(driver, test_steps, "No");
			Utility.customAssert(acctual.toUpperCase(), "UNASSIGNED", true, "Successfully Verified ROOM IS UNASSIGNED ",
					"Failed to Verified ROOM IS UNASSIGNED ", true, test_steps);

			String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);
			String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

			reservationPage.click_DeatilsTab(driver, test_steps);
			String FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			String FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
			String FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
			reservationPage.verify_BannerDetails(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, Email, FilioTripTotal, FilioBalance, reservationNo, statusAfterRes, CheckInDate,
					CheckOutDate, Country);

			homePage.assignNewRoomNumber(driver, test_steps);

			String expected = reservationPage.get_RoomNumber(driver, test_steps, "Yes");
			acctual = reservationPage.getFristReservationRoomNumber(driver, test_steps);
			Utility.customAssert(acctual, expected, true, "Successfully Verified ROOM IS ASSIGNED ",
					"Failed to Verified ROOM IS UNASSIGNED ",true, test_steps);

			reservationPage.verifyHTMLBodyInEmailPopup(driver, test_steps);

			reservationPage.closeAllOpenedReservations(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================== CREATE RESERVATION AND VERIFY AUTO SUGGESTIONS ======================");

			app_logs.info("=================== CREATE RESERVATION AND VERIFY AUTO SUGGESTIONS ======================");
//			navigation.cpReservation_Backward(driver);
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, "2");
			reservationPage.enter_Children(driver, test_steps, "0");
			if (PromoCode.isEmpty()) {
				reservationPage.select_Rateplan(driver, test_steps, RatePlanName, PromoCode);
			} else {
				String rateplan = "Promo Code";
				reservationPage.select_Rateplan(driver, test_steps, rateplan, PromoCode);
			}
			reservationPage.clickOnFindRooms(driver, test_steps);
			String minStayColor = "";
			if (isMinStayRule) {
				if (!isMinStayRuleBrokenPopComeOrNot) {
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps, ReservationRoomClass,
							minStayRuleValue);
					Utility.app_logs.info("minStayColor : " + minStayColor);
					try {
						assertTrue(minStayColor.equalsIgnoreCase("Red"),
								"Red color lable for minstay rule is not found");
						app_logs.info("Succesfully veried the red color lable for min stay rule");
						test_steps.add("Succesfully veried the red color lable for min stay rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps, ReservationRoomClass,
							minStayRuleValue);
					try {
						assertTrue(minStayColor.equalsIgnoreCase("green"),
								"green color lable for minstay rule is not found");
						app_logs.info("Succesfully veried the green color lable for min stay rule");
						test_steps.add("Succesfully veried the green color lable for min stay rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				}
			}

			String noCheckinColor = "";
			if (checkInColor.equalsIgnoreCase("Red")) {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					Utility.app_logs.info("noCheckinColor");
					assertTrue(noCheckinColor.equalsIgnoreCase("Red"),
							"red color lable for no check in rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check in rule");
					test_steps.add("Succesfully verified the red color lable for no check in rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkInColor.equalsIgnoreCase("Green")) {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckinColor.equalsIgnoreCase("green"),
							"green color lable for no check in rule is not found");
					app_logs.info("Succesfully verified the green color lable for no check in rule");
					test_steps.add("Succesfully verified the green color lable for no check in rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else {
				try {
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckinColor.equalsIgnoreCase("no color"), "no check in rule label is displayed");
					app_logs.info("Succesfully verified the no check in rule label not displayed");
					test_steps.add("Succesfully verified the no check in rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			String noCheckoutColor = "";
			if (checkOutColor.equalsIgnoreCase("Red")) {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("Red"),
							"red color lable for no check out rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check out rule");
					test_steps.add("Succesfully verified the red color lable for no check out rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkOutColor.equalsIgnoreCase("Green")) {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("green"),
							"green color lable for no check out rule is not found");
					app_logs.info("Succesfully verified the green color lable for no check out rule");
					test_steps.add("Succesfully verified the green color lable for no check out rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else {
				try {
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps, ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("no color"), "no check out rule label is displayed");
					app_logs.info("Succesfully verified the no check out rule label not displayed");
					test_steps.add("Succesfully verified the no check out rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps, ReservationRoomClass, "No", "",
					noCheckinColor, noCheckoutColor, minStayColor, minStayRuleValue);
			double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
			reservationPage.clickNext(driver, test_steps);

			reservationPage.verifySearchedGuestProfile(driver, test_steps, GuestFirstName + " " + GuestLastName, true,
					true);
		try {	reservationPage.verifyAddressSearch(driver, test_steps,
					"151 Union St", true, false);//, Sun Prairie, WI 53590 (United States)
		}catch (Exception e) {
			reservationPage.verifyAddressSearch(driver, test_steps,
					"151 Union St", true, false);//, Sun Prairie, WI 53590 (United States)
		}	
		reservationPage.verifyAccountSearch(driver, test_steps, CorpAccountNumber, false, false);
			reservationPage.verifyAccountSearch(driver, test_steps, CorpAccountName, true, true);
			reservationPage.verifyTravelAgentAccountSearch(driver, test_steps, TAAccountName.toUpperCase(), false,
					false);
			reservationPage.verifyTravelAgentAccountSearch(driver, test_steps, TAAccountName.toLowerCase(), false,
					false);
			reservationPage.verifyTravelAgentAccountSearch(driver, test_steps, TAAccountName, true, true);

//			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
//					PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
//					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
			}

			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			String status = "RESERVED";
			reservationPage.clickBookNow(driver, test_steps);
			reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			String statusAfterRes = reservationPage.get_ReservationStatus(driver, test_steps);
			Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true,
					"Successfully Verified Status After Reservation : " + status,
					"Failed to Verified Status After Reservation : " + statusAfterRes, true, test_steps);

			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			reservationPage.get_RoomNumber(driver, test_steps, "Yes");

			String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);
			String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

			reservationPage.click_DeatilsTab(driver, test_steps);
			String FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			String FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
			String FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
			reservationPage.verify_BannerDetails(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, Email, FilioTripTotal, FilioBalance, reservationNo, statusAfterRes, CheckInDate,
					CheckOutDate, Country);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseID, "\\|");

			for (int i = 0; i < list.size(); i++) {
				statusCode.add(i, "1");
				comments.add(i, "Success");
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyUnassignedReservation", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
