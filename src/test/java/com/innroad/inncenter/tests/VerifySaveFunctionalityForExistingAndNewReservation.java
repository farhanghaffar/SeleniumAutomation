package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.By;
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
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifySaveFunctionalityForExistingAndNewReservation extends TestCore {
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

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	DerivedRate derivedRate = new DerivedRate();

	@Test(dataProvider = "getData")
	public void verifySaveFunctionalityForExistingAndNewReservation(String TestCaseID, String CheckInDate,
			String CheckOutDate, String RatePlanName, String ReservationRoomClass, String isTask, String TaskCategory,
			String TaskType, String TaskDetails, String TaskRemarks, String TaskDueon, String TaskAssignee,
			String TaskStatus, String IsAddNotes, String NoteType, String Subject, String Description,
			String isChangeRatePlan, String ChangeRatePlaneName, String ChangeRoomClass) throws ParseException {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		test_name = "VerifySaveFunctionalityForExistingAndNewReservation";
		test_description = "Verify Save Functionality For Existing And New Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824586'>"
				+ "Click here to open TestRail: C824586</a>" + "<br>"
				+ "Verify that user is able to select the number of adults and children on create reservation page.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824748'>"
				+ "Click here to open TestRail: C824748</a>" + "<br>" + "Verify Created reservation.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824766'>"
				+ "Click here to open TestRail: C824766</a>" + "<br>" + "Verify user able to update MARKETING INFO<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824824'>"
				+ "Click here to open TestRail: C824824</a>" + "<br>"
				+ "Edit item in Referrel Custom items in Property tab<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824689'>"
				+ "Click here to open TestRail: C824689</a>" + "<br>"
				+ "Verify that 'Add Tasks' section is present on the create reservation page..<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824769'>"
				+ "Click here to open TestRail: C824769</a>" + "<br>"
				+ "Verify that user is able to delete an existing task.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824772'>"
				+ "Click here to open TestRail: C824772</a>" + "<br>"
				+ "Verify that all the information are displayed in the room search result.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824753'>"
				+ "Click here to open TestRail: C824753</a>" + "<br>"
				+ "Verify the RED outline around section that are missing info. required.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824802'>"
				+ "Click here to open TestRail: C824802</a>" + "<br>"

				+ "Verify the functionality of \"Change Stay Details\" option.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824937'>"
				+ "Click here to open TestRail: C824937</a>" + "<br>"

				+ "Verify the Marketing Info section with all fields present<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824821'>"
				+ "Click here to open TestRail: C824821</a>" + "<br>"

				+ "Verify the Marketing Info section when some fields are blank<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824822'>"
				+ "Click here to open TestRail: C824822</a>" + "<br>"

				+ "Verify that user can manually switch to another rate plan<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825017'>"
				+ "Click here to open TestRail: C825017</a>" + "<br>"

				+ "Verify that Save button is not enabled until all the required fields are filled with required info.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824816'>"
				+ "Click here to open TestRail: C824816</a>" + "<br>"

				+ "Verify the External Reservation Number field in Marketing Info section.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824823'>"
				+ "Click here to open TestRail: C824823</a>" + "<br>"

				+ "Verify check-in button is dissabled when reservation is made for future date.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825243'>"
				+ "Click here to open TestRail: C825243</a>" + "<br>"

				+ "Verify \"Updated By\" field in Note Section for any of the single reservation.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825411'>"
				+ "Click here to open TestRail: C825411</a>" + "<br>"

				+ "Verify extending/decreasing stay dates via RA picker & + Icon.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824554'>"
				+ "Click here to open TestRail: C824554</a>" + "<br>"

				+ "Verify back button hide on below section.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824781'>"
				+ "Click here to open TestRail: C824781</a>" + "<br>"

				+ "Verify \"Source\" Option in Source Drop-Down on advance search page.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825321'>"
				+ "Click here to open TestRail: C825321</a>" + "<br>"

				+ "Verify search results by any Option in on advance search page.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825326'>"
				+ "Click here to open TestRail: C825326</a>" + "<br>"
				
				+ "Recalculate Folio<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848131'>"
				+ "Click here to open TestRail: 848131</a>" + "<br>"
				
+ "CP: Verify booking a reservation with adults more than 10<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848580'>"
+ "Click here to open TestRail: 848580</a>" + "<br>"

+ "Verify that delete icon is present for an entitled user.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848281'>"
+ "Click here to open TestRail: 848281</a>" + "<br>"
				;
		test_catagory = "Reservation";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");
		String channelName = "innCenter";
		
		Utility.initializeTestCase(TestCaseID, caseId, statusCode, comments, "785572");

		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
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
//		

		boolean isMinStayRuleChange = false;
		boolean isMinStayRuleBrokenPopComeOrNotChange = false;
		ArrayList<String> minStayRuleChange = null;
		ArrayList<String> minruleChange = null;
		ArrayList<String> noCheckInRuleChange = null;
		ArrayList<String> noCheckOutRuleChange = null;
		String checkInColorChange = null;
		String checkOutColorChange = null;
		int minStayRuleValueChange = 0;
		String CheckInDateChange = CheckOutDate;
		String CheckOutDateChange = Utility.getCustomDate(CheckOutDate, "dd/MM/yyyy", "dd/MM/yyyy", 2);
		try {
			test_steps.add("=================== Getting Rate Plan Restrictions and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restrictions and Rules ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			driver.navigate().refresh();
			rateGrid.clickForRateGridCalender(driver, test_steps);
			rateGrid.selectDateFromDatePicker(driver, CheckInDate, ratesConfig.getProperty("defaultDateFormat"),
					test_steps);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);

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

			// change

			String changeRatePlanName = RatePlanName;
			String changeReservationRoomClass = ReservationRoomClass;
			if (isChangeRatePlan.equalsIgnoreCase("yes")) {
				changeRatePlanName = ChangeRatePlaneName;
				changeReservationRoomClass = ChangeRoomClass;
			}
			rateGrid.clickForRateGridCalender(driver, test_steps);
			rateGrid.selectDateFromDatePicker(driver, CheckInDateChange, ratesConfig.getProperty("defaultDateFormat"),
					test_steps);
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, changeRatePlanName);

			int daysChange = Utility.getNumberofDays(CheckInDateChange, CheckOutDateChange);
			Utility.app_logs.info("days : " + daysChange);
			rateGrid.expandRoomClass(driver, test_steps, changeReservationRoomClass);
			rateGrid.expandChannel(driver, test_steps, changeReservationRoomClass, channelName);
			minStayRuleChange = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, changeReservationRoomClass,
					channelName, daysChange);
			minruleChange = minStayRuleChange;

			Collections.sort(minruleChange);
			Utility.app_logs.info("minrule : " + minruleChange);

			minStayRuleValueChange = Integer.parseInt((String) minruleChange.get(minruleChange.size() - 1));

			if (minStayRuleValueChange > 0) {
				isMinStayRuleChange = true;
				isMinStayRuleBrokenPopComeOrNotChange = reservationPage.verifyMinStayPopupComeOrNot(driver,
						minruleChange, minStayRuleValueChange, daysChange);
			}

			noCheckInRuleChange = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, changeReservationRoomClass,
					channelName, daysChange);

			Utility.app_logs.info("noCheckInRule : " + noCheckInRuleChange);

			checkInColorChange = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRuleChange,
					CheckInDateChange, CheckOutDateChange);

			Utility.app_logs.info("checkInColor : " + checkInColorChange);

			noCheckOutRuleChange = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps,
					changeReservationRoomClass, channelName, daysChange);

			Utility.app_logs.info("noCheckOutRule : " + noCheckOutRuleChange);

			checkOutColorChange = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps,
					noCheckOutRuleChange, CheckInDateChange, CheckOutDateChange);
			Utility.app_logs.info("checkOutColor : " + checkOutColorChange);
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
		String IsGuesProfile = "No";
		String PaymentType = "MC";
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
		String Adults = "10";
		String Children = "0";
		try {
			test_steps.add("=================== CREATE RESERVATION AND VERIFY RULES ======================");

			app_logs.info("=================== CREATE RESERVATION AND VERIFY RULESs ======================");
			navigation.cpReservation_Backward(driver);
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, Adults);
			reservationPage.enter_Children(driver, test_steps, Children);
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

			reservationPage.verifyRoomClassSearchDetails(driver, test_steps);
			reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps, ReservationRoomClass, "Yes", "",
					noCheckinColor, noCheckoutColor, minStayColor, minStayRuleValue);
			double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
			reservationPage.clickNext(driver, test_steps);

			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			// if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			// }
			reservationPage.verify_MarketSegmantFeilds(driver, test_steps);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			reservationPage.verifyBookNow(driver, test_steps);
			reservationPage.clickBookNow(driver, test_steps);
			reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			String status = reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			reservationPage.get_RoomNumber(driver, test_steps, "Yes");

			String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			String TripSummaryTaxes = reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps);
			String TripSummaryIncidentals = reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps);
			String TripSummaryTripTotal = reservationPage.getTripSummaryTripTotal(driver, test_steps);
			String TripSummaryPaid = reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps);
			String TripSummaryBalance = reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps);
			reservationPage.verify_StayInfo(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children,
					ReservationRoomClass, TripSummaryRoomCharges);
			reservationPage.validate_GuestInfo(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, AltenativePhone, Email, Country, Account, Address1, Address2, Address3, State, City,
					PostalCode);
			reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);
			reservationPage.click_History(driver, test_steps);
			reservationPage.click_Folio(driver, test_steps);
			String FilioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
			String FilioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
			String FilioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
			String FilioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			String FilioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
			String FilioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
			reservationPage.verify_BannerDetails(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, Email, FilioTripTotal, FilioBalance, reservationNo, status, CheckInDate, CheckOutDate,
					Country);

			String payment = FilioPaid;
			FilioPaid = FilioPaid.trim();
			char ch = FilioPaid.charAt(0);
			FilioPaid = FilioPaid.replace("$", "");
			FilioPaid = FilioPaid.trim();
			Double paidDeposit = Double.parseDouble(FilioPaid);

			if (depositAmount > 0) {
				if (Double.compare(paidDeposit, depositAmount) == 0) {
					test_steps.add("Deposit paid amount is validated : " + ch + " " + paidDeposit);
					app_logs.info("Deposit paid amount is validated : " + ch + " " + paidDeposit);
				}
			}
			reservationPage.click_History(driver, test_steps);
			reservationPage.verify_ReservationInHistoryTab(driver, test_steps, reservationNo);
			if (depositAmount > 0) {
				reservationPage.verifyDepositPaymentInHistoryTab(driver, test_steps, depositAmount);
			}
			reservationPage.velidate_TripSummaryAndFolio(driver, test_steps, FilioRoomCharges, FilioTaxes,
					FilioIncidentals, FilioTripTotal, payment, FilioBalance, TripSummaryRoomCharges, TripSummaryTaxes,
					TripSummaryIncidentals, TripSummaryTripTotal, TripSummaryPaid, TripSummaryBalance);
			reservationPage.verify_GuestReportLabelsValidations(driver, test_steps);

			reservationPage.close_FirstOpenedReservation(driver, test_steps);

			try {
				reservationSearch.clickAdvanceReservationButton(driver);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.advanceSearchWithGuestName(driver, GuestLastName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.clickOnSearchButton(driver);
				test_steps.addAll(getTest_Steps);

				reservationSearch.verifyReservationStatusWithName(driver, GuestLastName, status, test_steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.advanceSearchWithMarketingSegment(driver, MarketSegment);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.advanceSearchWithSource(driver, channelName);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.advanceSearchWithReferral(driver, Referral);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.clickOnSearchButton(driver);
				test_steps.addAll(getTest_Steps);

				reservationSearch.verifyReservationStatusWithName(driver, GuestLastName, status, test_steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.advanceSearchWithReservationNumber(driver, reservationNo);
				test_steps.addAll(getTest_Steps);

				getTest_Steps.clear();
				getTest_Steps = reservationSearch.clickOnSearchButton(driver);
				test_steps.addAll(getTest_Steps);

				reservationSearch.verifyReservationStatusWithName(driver, GuestLastName, status, test_steps);

				reservationSearch.clickGoBasicLink(driver);
			} catch (Exception e) {
				try {
					reservationSearch.clickGoBasicLink(driver);
				} catch (Exception ce) {
					driver.navigate().refresh();
				}
			}

			reservationPage.Search_ResNumber_And_Click(driver, reservationNo);

			reservationPage.ClickEditStayInfo(driver, test_steps);
			reservationPage.ClickStayInfo_ChangeDetails(driver, test_steps);

			reservationPage.verifySpinerLoading(driver);

			reservationPage.select_CheckInDate(driver, test_steps, CheckInDateChange);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDateChange);
			reservationPage.enter_Adults(driver, test_steps, Adults);
			reservationPage.enter_Children(driver, test_steps, Children);
			String changeRatePlanName = RatePlanName;
			String changeReservationRoomClass = ReservationRoomClass;
			if (isChangeRatePlan.equalsIgnoreCase("yes")) {
				changeRatePlanName = ChangeRatePlaneName;
				changeReservationRoomClass = ChangeRoomClass;
			}

			reservationPage.select_Rateplan(driver, test_steps, changeRatePlanName, PromoCode);
			reservationPage.VerifyFoliocalculateRate(driver, test_steps, "Recalculate Rate");
			reservationPage.clickOnFindRooms(driver, test_steps);

			String minStayColorChange = "";

			if (isMinStayRuleChange) {
				if (!isMinStayRuleBrokenPopComeOrNotChange) {
					minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
							changeReservationRoomClass, minStayRuleValueChange);
					Utility.app_logs.info("minStayColor : " + minStayColorChange);
					try {
						assertTrue(minStayColorChange.equalsIgnoreCase("Red"),
								"Red color lable for minstay rule is not found");
						app_logs.info("Succesfully veried the red color lable for min stay rule");
						test_steps.add("Succesfully veried the red color lable for min stay rule");
					} catch (Error e) {
						test_steps.add(e.toString());
					} catch (Exception e) {
						test_steps.add(e.toString());
					}
				} else {
					minStayColorChange = reservationPage.verifyMinStayLabel(driver, test_steps,
							changeReservationRoomClass, minStayRuleValueChange);
					try {
						assertTrue(minStayColorChange.equalsIgnoreCase("green"),
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

			String noCheckinColorChange = "";
			if (checkInColorChange.equalsIgnoreCase("Red")) {
				try {
					noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							changeReservationRoomClass);
					Utility.app_logs.info("noCheckinColor");
					assertTrue(noCheckinColorChange.equalsIgnoreCase("Red"),
							"red color lable for no check in rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check in rule");
					test_steps.add("Succesfully verified the red color lable for no check in rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkInColorChange.equalsIgnoreCase("Green")) {
				try {
					noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							changeReservationRoomClass);
					assertTrue(noCheckinColorChange.equalsIgnoreCase("green"),
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
					noCheckinColorChange = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							changeReservationRoomClass);
					assertTrue(noCheckinColorChange.equalsIgnoreCase("no color"),
							"no check in rule label is displayed");
					app_logs.info("Succesfully verified the no check in rule label not displayed");
					test_steps.add("Succesfully verified the no check in rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			String noCheckoutColorChange = "";
			if (checkOutColorChange.equalsIgnoreCase("Red")) {
				try {
					noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							changeReservationRoomClass);
					assertTrue(noCheckoutColorChange.equalsIgnoreCase("Red"),
							"red color lable for no check out rule is not found");
					app_logs.info("Succesfully verified the red color lable for no check out rule");
					test_steps.add("Succesfully verified the red color lable for no check out rule");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			} else if (checkOutColorChange.equalsIgnoreCase("Green")) {
				try {
					noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							changeReservationRoomClass);
					assertTrue(noCheckoutColorChange.equalsIgnoreCase("green"),
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
					noCheckoutColorChange = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							changeReservationRoomClass);
					assertTrue(noCheckoutColorChange.equalsIgnoreCase("no color"),
							"no check out rule label is displayed");
					app_logs.info("Succesfully verified the no check out rule label not displayed");
					test_steps.add("Succesfully verified the no check out rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps, changeReservationRoomClass,
					"Yes", "AccountName", noCheckinColorChange, noCheckoutColorChange, minStayColorChange,
					minStayRuleValueChange);

			reservationPage.verifySaveButton(driver, test_steps);
			reservationPage.clickSaveButton(driver);
			reservationPage.verifyPopupChangeInCost1(driver, test_steps);
			reservationPage.click_Folio(driver, test_steps);

			HashMap<String, String> roomChargesFolio1 = reservationPage.getRoomChargesFromFolioBasedOnDates(driver,
					test_steps, CheckInDateChange, CheckOutDateChange);
			String RoomChagres1 = reservationPage.get_RoomCharge(driver, test_steps);
			reservationPage.checkFolioLineItemsWithDates(driver, test_steps, CheckInDateChange, CheckOutDateChange);

			reservationPage.click_DeatilsTab(driver, test_steps);
			reservationPage.clickGuestInfoEditDetailsButton(driver, test_steps);

			reservationPage.verifyGuestInfoRedBoarderOnRequiredFeildError(driver, test_steps);
			reservationPage.clickGuestInfoEditDetailsButton(driver, test_steps);
			reservationPage.enter_GuestName(driver, test_steps, "Dr.", GuestFirstName + "Update", GuestLastName);
			reservationPage.clickGuestInfoSaveDetailsButton(driver, test_steps);

			reservationPage.clickEditPaymentInfo(driver, test_steps);
			reservationPage.verifyPaymentInfoSaveButton(driver, test_steps, false);
			reservationPage.enter_PaymentDetails(driver, test_steps, "Cash", CardNumber, NameOnCard, CardExpDate);
			reservationPage.verifyPaymentInfoSaveButton(driver, test_steps, true);
			reservationPage.clickPaymentInfoSaveButton(driver, test_steps);

			reservationPage.clickOnEditMarketingInfoIcon(driver);
			test_steps.add("Click on marketing edit icon");

			getTest_Steps.clear();
			getTest_Steps = reservationPage.selectReferral(driver, "Other");
			test_steps.addAll(getTest_Steps);

			reservationPage.verifySaveMarketingInfoButton(driver, test_steps);
			reservationPage.clickOnSaveMarketingInfoIcon(driver);
			test_steps.add("Click on save marketing info");

			reservationPage.verify_TaskSections(driver, test_steps);
			reservationPage.verifyBackButtonInTaskPopup(driver, test_steps);
			reservationPage.AddTask(driver, test_steps, isTask, TaskCategory, TaskType, TaskDetails, TaskRemarks,
					TaskDueon, TaskAssignee, TaskStatus);
			reservationPage.updateTask(driver, 1, "Done");
			reservationPage.DeleteTask(driver, TaskDetails, test_steps);

			reservationPage.verify_NotesSections(driver, test_steps);
			reservationPage.verifyBackButtonInNotesPopup(driver, test_steps);
			reservationPage.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			reservationPage.verify_Notes(driver, test_steps, NoteType, Subject, Description, "auto user", 1);

			reservationPage.verifyBackButtonInADDONPopup(driver, test_steps);
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

		try {

			ArrayList<String> list = Utility.convertTokenToArrayList(TestCaseID, "\\|");

			caseId = new ArrayList<String>();
			statusCode = new ArrayList<String>();
			comments = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				caseId.add(list.get(i));
				statusCode.add("1");
				comments.add("PASS : " + this.getClass().getSimpleName().trim());
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
		return Utility.getData("VerifyResSaveFunctionality", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
