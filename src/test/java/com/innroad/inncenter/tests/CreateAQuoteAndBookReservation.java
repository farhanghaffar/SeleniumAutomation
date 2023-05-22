package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateAQuoteAndBookReservation extends TestCore {
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

	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();ReservationHomePage homePage = new ReservationHomePage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();

	@Test(dataProvider = "getData")
	public void createAQuoteAndBookReservation(String TestCaseID, String CheckInDate, String CheckOutDate,
			String RatePlanName,String ReservationRoomClass) throws ParseException {
		test_name = "CreateAQuoteAndBookReservation";
		test_description = "Create a Quote and book Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824570'>"
				+ "Click here to open TestRail: C824570</a>"
				+ "<br>Verify by creating a quote from reservation tab, adding notes and try to book the reservation from the quote and then the notes should appear.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824906' target='_blank'>"
				+ "Click here to open TestRail: C824906</a>"
				+ "<br>Verify the Nightly Rate is showing with the Rate Plan in the Book Modal.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824907' target='_blank'>"
				+ "Click here to open TestRail: C824907</a><br>"
				
+ "<br>Create Assigned & Unassigned Reservation from quote.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848101' target='_blank'>"
+ "Click here to open TestRail: 848101</a><br>"

+ "<br>Verify that user is able to create a quote reservation and verify the availability.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848125' target='_blank'>"
+ "Click here to open TestRail: 848125</a><br>"

+ "<br>Verify the availability of SAVE Quote button on the guest reservation page.<br>"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848304' target='_blank'>"
+ "Click here to open TestRail: 848304</a><br>"
				;
		test_catagory = "Reservation";
		// String test_name = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + test_name + " TEST.");
		app_logs.info("##################################################################################");
		String channelName = "innCenter";
		if(Utility.getResultForCase(driver, TestCaseID)) {
		Utility.initializeTestCase(TestCaseID, caseId, statusCode, comments, "77200092");

		try {
			if (!Utility.insertTestName.containsKey(test_name)) {
				Utility.insertTestName.put(test_name, test_name);
				Utility.reTry.put(test_name, 0);
			} else {
				Utility.reTry.replace(test_name, Utility.reTry.get(test_name)+1); System.out.println(Utility.reTry.get(test_name));
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
			test_steps.add("=================== Getting Rate Plan Restrictions and Rules ======================");
			app_logs.info("=================== Getting Rate Plan Restrictions and Rules ======================");
			navigation.Inventory(driver, test_steps);
			navigation.ratesGrid(driver);
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, RatePlanName);

			
			int days  = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			Utility.app_logs.info("days : " + days);
			rateGrid.expandRoomClass(driver, test_steps, ReservationRoomClass);
			rateGrid.expandChannel(driver, test_steps, ReservationRoomClass, channelName);
			minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, ReservationRoomClass,
					channelName, days);
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

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRule,
					CheckInDate, CheckOutDate);

			Utility.app_logs.info("checkInColor : " + checkInColor);

			noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, ReservationRoomClass,
					channelName, days);

			Utility.app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRule,
					CheckInDate, CheckOutDate);
			Utility.app_logs.info("checkOutColor : " + checkOutColor);

		}catch (Exception e) {
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
		
		String IsAddNotes= "yes";
		String NoteType= "Internal";
		String Subject= "ensure wifi availibility";
		String Description= "wifi should be available on guest arrival";
		try {
			test_steps.add("=================== CREATE QUOTE ======================");

			app_logs.info("=================== CREATE QUOTE ======================");
			navigation.cpReservation_Backward(driver);
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
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
							ReservationRoomClass, minStayRuleValue);
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
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
							ReservationRoomClass, minStayRuleValue);
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
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							ReservationRoomClass);
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
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							ReservationRoomClass);
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
					noCheckinColor = reservationPage.verifyNoCheckinLabel(driver, test_steps,
							ReservationRoomClass);
					assertTrue(noCheckinColor.equalsIgnoreCase("no color"),
							"no check in rule label is displayed");
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
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							ReservationRoomClass);
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
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							ReservationRoomClass);
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
					noCheckoutColor = reservationPage.verifyNoCheckoutLabel(driver, test_steps,
							ReservationRoomClass);
					assertTrue(noCheckoutColor.equalsIgnoreCase("no color"),
							"no check out rule label is displayed");
					app_logs.info("Succesfully verified the no check out rule label not displayed");
					test_steps.add("Succesfully verified the no check out rule label not displayed");
				} catch (Error e) {
					test_steps.add(e.toString());
				} catch (Exception e) {
					test_steps.add(e.toString());
				}
			}

			
			reservationPage.select_RoomWithRatePlanRulesValidation(driver, test_steps,
					ReservationRoomClass, "No", "", noCheckinColor, noCheckoutColor, minStayColor,
					minStayRuleValue);
			double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
			reservationPage.clickNext(driver, test_steps);

			reservationPage.enter_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName,
					PhoneNumber, PhoneNumber, Email, "", "", Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MarketSegment, Referral);
			reservationPage.verify_NotesSections(driver, test_steps);
			reservationPage.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			reservationPage.clickSaveAsQuoteButton(driver);
			reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			String status = reservationPage.get_ReservationStatus(driver, test_steps);
			Utility.customAssert(status.toUpperCase(), "QUOTE", true, 
					"Successfully Verified QUOTE", "Failed to Verified QUOTE", true, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			reservationPage.get_RoomNumber(driver, test_steps, "Yes");
			String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);
			String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

			reservationPage.click_DeatilsTab(driver, test_steps);
			

			String expected = reservationPage.get_RoomNumber(driver, test_steps, "Yes");
			String acctual = reservationPage.getFristReservationRoomNumber(driver, test_steps);
			Utility.customAssert(acctual, expected, true, "Successfully Verified ROOM IS ASSIGNED ",
					"Failed to Verified ROOM IS UNASSIGNED ",true, test_steps);

			reservationPage.clickBook(driver, test_steps);
//			reservationPage.
			reservationPage.closeModalPopup(driver, test_steps);
			Wait.wait10Second();
			reservationPage.clickBookQuote(driver, RatePlanName, test_steps);
			

			homePage.assignNewRoomNumber(driver, test_steps);

			reservationPage.verify_Notes(driver, test_steps, NoteType, Subject, Description, "AUTO USER", 1);
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
			
			
ArrayList<String> list =  Utility.convertTokenToArrayList(TestCaseID, "\\|");
			
			for(int i=0; i<list.size(); i++) {
				statusCode.add(i,"1");
				comments.add(i,"Success");
			}
			
			System.out.println(statusCode);
			System.out.println(comments);
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
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
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
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CreateQuoteBookReservation", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
