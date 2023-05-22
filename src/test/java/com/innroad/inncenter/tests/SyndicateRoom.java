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

import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class SyndicateRoom extends TestCore {

	private WebDriver driver = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	public static ArrayList<String> test_name = new ArrayList<>();
	public static ArrayList<String> test_description = new ArrayList<>();
	public static ArrayList<String> test_catagory = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();

	@Test(dataProvider = "getData", groups = "Inventory")
	public void syndicateRoom(String TestCaseID, String checkInDate, String checkOutDate, String adults,
			String children, String ratePlan, String RoomClassName, String RoomClassAbb)
			throws InterruptedException, IOException, ParseException {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		String testName = "SyndicateRoom";
		String testDescription = "Syndicate a room<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/13230' target='_blank'>"
				+ "Click here to open TestRail: C13230</a>" + "<br>" + "Syndicate a room<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824534' target='_blank'>"
				+ "Click here to open TestRail: C824534</a>";
		String testCatagory = "Reservation";

		Utility.initializeTestCase(TestCaseID, caseId, statusCode, comments, "824534");

		test_name.add(testName);
		test_catagory.add(testCatagory);
		test_description.add(testDescription);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage reservationPage = new CPReservationPage();
		Navigation nav = new Navigation();
		Distribution distribution = new Distribution();
		Tapechart tapechart = new Tapechart();
		String RoomNumber = "";
		Reservation reservation = new Reservation();
		ReservationV2 res = new ReservationV2();
		try {System.out.println("test");
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			loginRateV2(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) { e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		String salutation = "Mr.";
		String guestFirstName = "John Wick";
		String guestLastName = Utility.generateRandomNumberWithGivenNumberOfDigits(4);
		String phoneNumber = "1234567899";
		String altenativePhone = "9876543211";
		String email = "innroadautomation@innroad.com";
		String account = "";
		String accountType = "Corporate/Member Accounts";
		String address1 = "Lane1";
		String address2 = "Lane2";
		String address3 = "Lane3";
		String city = "New York";
		String country = "United States";
		String state = "New York";
		String postalCode = "5432";
		String isGuesProfile = "no";
		String paymentMethod = "Cash";
		String cardNumber = "";
		String nameOnCard = "";
		String cardExpMonthAndYear = "";
		String referral = "Walk In";
		String marketSegment = "GDS";
		int additionalGuests = 0;

		boolean quote = false;
		String noteType = "";
		String noteSubject = "";
		String noteDescription = "";
		String taskCategory = "";
		String taskType = "";
		String taskDetails = "";
		String taskRemarks = "";
		String taskDueOn = "";
		String taskAssignee = "";
		String taskStatus = "";
		String accountName = "";
		String accountFirstName = "";
		String accountLastName = "";
		boolean isTaxExempt = false;
		String taxExemptID = "";

		String promoCode = "";
		// Get Available Room Number
		try {
			
			// Get CheckIN and Checkout Date
//						if (!(Utility.validateInput(checkInDate))) {
//							for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
							checkInDate=Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
							checkOutDate=Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
//							}
//						} else {
//							checkInDate = Utility.splitInputData(CheckInDate);
//							checkOutDate = Utility.splitInputData(CheckOutDate);
//						}

			test_steps.add(
					"<b>*******************  Get Available Room Number FROM RESERVATION PAGE **********************</b>");
			res.click_NewReservation(driver, test_steps);

//			res.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children,
//					ratePlan, promoCode);
//			res.clickOnFindRooms(driver, test_steps);

//			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			
				reservationPage.select_Rateplan(driver, test_steps, ratePlan, promoCode);
			
			reservationPage.clickOnFindRooms(driver, test_steps);
			
			RoomNumber = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClassName).get(0);
			res.closeAllOpenedReservations(driver);
			test_steps.add("Get room number " + RoomNumber + " for syndicate");
			app_logs.info("Get room number " + RoomNumber + " for syndicate");

		} catch (Exception e) {e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to  Get Room Number", testName, "Get Room Number", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to  Get Room Number", testName, "Get Room Number", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Inventory
		RatesGrid rateGrid = new RatesGrid();String channelName = "innCenter";
		try {

			nav.Inventory(driver);
			test_steps.add("Click on inventroy");
			app_logs.info("Click on inventroy");

			nav.ratesGrid(driver);
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlan);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to go to distribution", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to go to distribution", testName, "Inventory", driver);
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
			test_steps.add("=================== Getting Rate Plan Rules ======================");
			app_logs.info("=================== Getting Rate Plan Rules ======================");
			nav.Inventory(driver, test_steps);
			nav.ratesGrid(driver);
			driver.navigate().refresh();
			rateGrid.clickRatePlanArrow(driver, test_steps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, test_steps, ratePlan);

			
			int days  = Utility.getNumberofDays(checkInDate, checkOutDate);
			Utility.app_logs.info("days : " + days);
			rateGrid.expandRoomClass(driver, test_steps, RoomClassName);
			rateGrid.expandChannel(driver, test_steps, RoomClassName, channelName);
			minStayRule = rateGrid.getRuleDataValuesForMinStay(driver, test_steps, RoomClassName,
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

			noCheckInRule = rateGrid.getRuleDataValuesForNoCheckIn(driver, test_steps, RoomClassName,
					channelName, days);

			Utility.app_logs.info("noCheckInRule : " + noCheckInRule);

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, test_steps, noCheckInRule,
					checkInDate, checkOutDate);

			Utility.app_logs.info("checkInColor : " + checkInColor);

			noCheckOutRule = rateGrid.getRuleDataValuesForNoCheckOut(driver, test_steps, RoomClassName,
					channelName, days);

			Utility.app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, test_steps, noCheckOutRule,
					checkInDate, checkOutDate);
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
		
		
		// Syndicate
		try {
			nav.Distribution(driver);
			test_steps.add("Click on distribution");
			app_logs.info("Click on distribution");
			
			nav.Distribution_syndication(driver);

			distribution.SyndicateRoom(driver, RoomClassName, RoomNumber, true);
			test_steps.add("Room number " + RoomNumber + " of room class " + RoomClassName + " has been syndicated");
			app_logs.info("Room number " + RoomNumber + " of room class " + RoomClassName + " has been syndicated");

		} catch (Exception e) { e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) { e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Reservation
		try {
			nav.ReservationV2_Backward(driver);
			test_steps.add("Click on reservation");
			app_logs.info("Click on reservation");

			nav.TapeChart(driver);

			getTest_Steps.clear();
			getTest_Steps = tapechart.verify_SyndicateRoom(driver, RoomClassAbb, RoomNumber, getTest_Steps);

			test_steps
					.add("Verified room number " + RoomNumber + " of room class " + RoomClassName + " had syndicated");
			app_logs.info("Verified room number " + RoomNumber + " of room class " + RoomClassName + " had syndicated");

			test_steps.addAll(getTest_Steps);
//resv2
//			res.createReservation(driver, test_steps, "TapeChart Syndicate", checkInDate, checkOutDate, adults,
//					children, ratePlan, "", RoomClassName, RoomClassAbb, salutation, guestFirstName, guestLastName,
//					phoneNumber, altenativePhone, email, address1, address2, address3, city, country, state, postalCode,
//					isGuesProfile, marketSegment, referral, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
//					additionalGuests, RoomNumber, quote, noteType, noteSubject, noteDescription, taskCategory, taskType,
//					taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
//					accountFirstName, accountLastName, isTaxExempt, taxExemptID);

//			StayInfo info = res.getStayInfoDetail(driver);
//
//			res.verifyStayInfoDetail(info, "propertyName", false, false, "rateName", false, false, "date", false, false,
//					"date", false, false, "noofnight", false, false, "noofAdults", false, false, cardNumber, false,
//					false, RoomClassName, false, false, RoomNumber, true, true, promoCode, false, false, test_steps);
		} catch (Exception e) { e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find syndicate room in tapchart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to find syndicate room in tapchart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
//		String Salutation = "Mr.";
//		String GuestFirstName = "Test Res";
//		String GuestLastName = Utility.generateRandomString();
//		String PhoneNumber = "8790321567";
//		String AltenativePhone = "8790321577";
//		String Email = "innroadautomation@innroad.com";
//		String Account = "";
//		String Address1 = "test1";
//		String Address2 = "test2";
//		String Address3 = "test3";
//		String City = "test";
//		String Country = "United States";
//		String PostalCode = "12345";
//		String IsGuesProfile = "No";
//		String PaymentType = "Cash";
//		String CardNumber = "5454545454545454";
//		String NameOnCard = "Test card";
//		String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
//		String IsChangeInPayAmount = "No";
//		String ChangedAmountValue = "";
//		String TravelAgent = "";
//		String MarketSegment = "GDS";
//		String State = "New York";
//		String IsSplitRes = "No";
//		String Referral = "Other";
//		String AccountType = "Corporate/Member Accounts";
//		String AccountName = "AccountName_";
//		String MargetSegment = "GDS";
//		String BlockName = "Test Block";
//		String RoomPerNight = "1";
//		String PromoCode = "";
//		String reservationNo = null;
		try {
			test_steps.add("=================== CREATE RESERVATION AND VERIFY RULES ======================");

			app_logs.info("=================== CREATE RESERVATION AND VERIFY RULESs ======================");
			nav.cpReservation_Backward(driver);
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			reservationPage.enter_Adults(driver, test_steps, "2");
			reservationPage.enter_Children(driver, test_steps, "0");
			if (promoCode.isEmpty()) {
				reservationPage.select_Rateplan(driver, test_steps, ratePlan, promoCode);
			} else {
				String rateplan = "Promo Code";
				reservationPage.select_Rateplan(driver, test_steps, rateplan, promoCode);
			}
			reservationPage.clickOnFindRooms(driver, test_steps);
			String minStayColor = "";
			if (isMinStayRule) {
				if (!isMinStayRuleBrokenPopComeOrNot) {
					minStayColor = reservationPage.verifyMinStayLabel(driver, test_steps,
							RoomClassName, minStayRuleValue);
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
							RoomClassName, minStayRuleValue);
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
							RoomClassName);
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
							RoomClassName);
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
							RoomClassName);
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
							RoomClassName);
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
							RoomClassName);
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
							RoomClassName);
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
					RoomClassName, "Yes", "", noCheckinColor, noCheckoutColor, minStayColor,
					minStayRuleValue);
			double depositAmount = reservationPage.deposit(driver, test_steps, "Yes", "10");
			reservationPage.clickNext(driver, test_steps);

			reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
					phoneNumber, phoneNumber, email, "", "", address1, address2, address3, city, country, state,
					postalCode, isGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard,
						cardExpMonthAndYear);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);

			reservationPage.clickBookNow(driver, test_steps);
			String reservationNo = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			String status = reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			reservationPage.get_RoomNumber(driver, test_steps, "Yes");
			
			String TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);
			String folioRoomCharges = reservationPage.get_RoomCharge(driver, test_steps);

			reservationPage.click_DeatilsTab(driver, test_steps);
			String FilioTripTotal= reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			String FilioPaid=reservationPage.get_PaymentsWithCurrency(driver, test_steps);
			String FilioBalance=reservationPage.get_BalanceWithCurrency(driver, test_steps);
			reservationPage.verify_BannerDetails(driver, test_steps, salutation, guestFirstName, guestLastName, phoneNumber, email, FilioTripTotal, FilioBalance, reservationNo, status, checkInDate, checkOutDate, country);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Inventory
		try {

			nav.Inventory(driver);
			test_steps.add("Click on inventroy");
			app_logs.info("Click on inventroy");

			nav.Distribution(driver);
			test_steps.add("Click on distribution");
			app_logs.info("Click on distribution");

			nav.Distribution_syndication(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click Inventory", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click Inventory", testName, "Inventory", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Syndicate
		try {
			distribution.SyndicateRoom(driver, RoomClassName, RoomNumber, false);
			test_steps.add("Uncheck syndicate room successfully");
			app_logs.info("Uncheck syndicate room successfully");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to uncheck syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to uncheck syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			statusCode = new ArrayList<String>();
			comments = new ArrayList<String>();
			comments.add("Successfully verified SyndicatedRoom : " + RoomNumber);
			statusCode.add(0, "1");
//			statusCode.add(1, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to uncheck syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to uncheck syndicate room", testName, "Syndicate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("SyndicateRoom", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}
}
