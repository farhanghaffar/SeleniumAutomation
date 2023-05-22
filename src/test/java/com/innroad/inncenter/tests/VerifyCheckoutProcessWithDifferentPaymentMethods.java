package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.reservationPage;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;

public class VerifyCheckoutProcessWithDifferentPaymentMethods extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	public static String roomClassAbbrivation;

	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;

	ArrayList<String> checkInDates = new ArrayList<>();

//	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifycheckoutProcessWithDifferentPaymentMethods(String testCaseID, String ratePlan, String roomClassName,
			String swipeCardString, String swipedCardNumber, String swipedCardName, String swipedCardExpiry)
			throws ParseException {
		test_name = "VerifyCheckoutProcessWithDifferentPaymentMethods";
		test_description = "VerifyCheckoutProcessWithDifferentPaymentMethods"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848522' target='_blank'>"
				+ "Click here to open TestRail: C848522</a><br>";
		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		reservationPage res=new reservationPage();
		Properties prop = new Properties();
		ReservationHomePage homePage = new ReservationHomePage();
		ReservationSearch reservationSearch = new ReservationSearch();
		Folio folio = new Folio();
		GuestHistory guestHistory = new GuestHistory();
		LedgerAccount ledgerAccount = new LedgerAccount();
		Account CreateTA = new Account();
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		ReservationHomePage reservationHomePage = new ReservationHomePage();
		String testName = null;

		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr.";
		String guestFirstName = "VerifyRes" + randomString;
		String guestLastName = "Realization" + randomString;
		String paymentTypeMethod = "MC";
		String marketSegment = "Internet";
		String referral = "Other";
		String guaranteedStatus = "Guaranteed";

		String cardNumber = "5454545454545454";
		String nameOnCard = guestFirstName;
		String cardExpDate = "12/23";
		String billingNotes = "adding primary card";
		String accountType = "";
		String billingSalutation = "Lord.";
		String country = "United States";
		String accountNo = "";
		String phoneNumber = "1234567890";
		String alternativeNumber = phoneNumber;
		String address = "Address123";
		String email = "innroadautomation@innroad.com";
		String city = "New york";
		String state = "Alaska";
		String postalcode = "12345";
		String guestName = guestFirstName + " " + guestLastName;
		String account = "CorporateAccount" + Utility.generateRandomString();
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1";
		String address2 = "test2";
		String address3 = "test3";
		String postalCode = "12345";
		String alternativePhone = "8790321567";
		String groupReferral = "Walk In";
		String groupFirstName = "Bluebook" + randomString;
		String groupLastName = "Group" + randomString;
		String accountName = groupFirstName + groupLastName;
		String invalidCard = "5151515151515151";
		String reservationNumber = "";
		String reservationtoPay = "";
		String updatedBlockedCount="2";
		String roomPerNight="2";
		String blockName="Block"+Utility.generateRandomString();
		

		if (!Utility.validateString(testCaseID)) {
			caseId.add("848522");
			statusCode.add("4");
		} else {
			String[] testcase = testCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}

		}
		test_catagory = "Verification";
		testName = test_name;

		String timeZone = "US/Eastren";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		String maxAdult = "1";
		String maxPerson = "0";
		String property = "Reports Property";
		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			loginClinent3281(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
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
		
		String amountToPay = "100";

		// removable
		accountName = "AutoGroup8916JVxNd6mPtV";
		accountNumber = "992842903765097";

		String tempCheckin = checkInDates.get(0) + "|" + checkInDates.get(1);
		String tempCheckOut = checkInDates.get(0) + "|" + checkInDates.get(1);
		String tempRatePlan = ratePlan + "|" + ratePlan;
		String tempMaxAdults = maxAdult + "|" + maxAdult;
		String tempMaxChildren = maxPerson + "|" + maxPerson;
		String tempRoomClassName = roomClassName + "|" + roomClassName;
		String tempFirstName = guestFirstName + "|" + guestFirstName + randomString;
		String tempLastName = guestLastName + "|" + guestLastName + randomString;
		String tempSalutation = salutation + "|" + salutation;
		String isSplitRes = "No";
		String tempPhoneNumber = phoneNumber + "|" + phoneNumber;
		paymentTypeMethod = "MC";
		
		// Click New Account and Enter Account Name
				try {
					Utility.refreshPage(driver);
					Wait.waitUntilPageIsLoaded(driver);

					testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/825123' target='_blank'>"
							+ "<b>verifying the checkout process when the payment type as refund for the group MRB reservations</b></a> =====");

					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);

					testSteps.add("<b> ===== SEARCHING 'GROUP' RESERVATION ===== </b>");
					app_logs.info("<b> ===== SEARCHING 'GORUP' RESERVATION ===== </b>");

					navigation.secondaryGroupsManu(driver);
					testSteps.add("Navigate Groups");
					app_logs.info(" Navigate Groups");
					
					
					app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
					testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
					getTestSteps.clear();
					getTestSteps = group.enterrGroupName(driver, accountName);
					testSteps.addAll(getTestSteps);

				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Number
				try {
					accountNumber = Utility.GenerateRandomString15Digit();
					getTestSteps.clear();
					getTestSteps = group.enterAccountNo(driver, accountNumber);
					testSteps.addAll(getTestSteps);
				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Attributes
				try {
					getTestSteps.clear();
					getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
					testSteps.addAll(getTestSteps);
				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Mailing Address
				try {
					getTestSteps.clear();
					getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
							city, state, country, postalcode);
					testSteps.addAll(getTestSteps);
				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Check Mailing Info CheckBox
				try {
					getTestSteps.clear();
					getTestSteps = group.Billinginfo(driver);
					testSteps.addAll(getTestSteps);
				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Save Account
				try {
					getTestSteps.clear();
					getTestSteps = group.clickOnSave(driver);
					testSteps.addAll(getTestSteps);

				}  catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}


				try {
					app_logs.info("==========CREATE NEW BLOCK==========");
					testSteps.add("==========CREATE NEW BLOCK==========");

					getTestSteps.clear();
					getTestSteps = group.navigateRoomBlock(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickNewBlock(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.EnterBlockName(driver, blockName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickOkay_CreateNewBlock(driver);
					testSteps.addAll(getTestSteps);

					app_logs.info("==========SEARCH ROOMS==========");
					testSteps.add("==========SEARCH ROOMS==========");

					getTestSteps.clear();
					getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("dd/MM/yyyy"),
							Utility.GetNextDate(1, "dd/MM/yyyy"));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectRatePlan(driver, ratePlan);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectAdults(driver, maxAdult);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.SelectChilds(driver, "1");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.EnterNights(driver, roomPerNight);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = group.ClickSearchGroup(driver);
					testSteps.addAll(getTestSteps);

					advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
					advgroup.BlockRoomForSelectedRoomclass(driver, "2", roomClassName);
					getTestSteps.clear();

					advgroup.ClickCreateBlock(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					
				
					getTestSteps.clear();
					getTestSteps = group.clickOnSave(driver);
					testSteps.addAll(getTestSteps);
					
				
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				

		try {
		

		//	group.searchGroupAccount(driver, accountName, accountNumber, true, true);
			group.clickOnNewReservationButtonFromGroup(driver, testSteps);
			reservationPage.selectDates(driver, testSteps, tempCheckin, tempCheckOut, tempMaxAdults, tempMaxChildren,
					tempRatePlan, "", isSplitRes);

			if (isSplitRes.equalsIgnoreCase("Yes")) {
				getTestSteps.clear();
				reservationPage.enter_Adults(driver, getTestSteps, maxAdult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.enter_Children(driver, getTestSteps, maxPerson);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				reservationPage.select_Rateplan(driver, getTestSteps, ratePlan, "");
				testSteps.addAll(getTestSteps);

			}

			reservationPage.clickOnFindRooms(driver, testSteps);

			reservationPage.select_MRBRooms(driver, testSteps, tempRoomClassName, "Yes", "");
			try {
				reservationPage.clickNext(driver, testSteps);

			} catch (Exception e) {
				reservationPage.clickSelectRoom(driver,
						tempRoomClassName.split("\\|")[tempRoomClassName.split("\\|").length - 1], testSteps);
				reservationPage.clickNext(driver, testSteps);

			}

			getTestSteps.clear();
			reservationPage.enter_MRB_MailingAddress(driver, testSteps, tempSalutation, tempFirstName, tempLastName,
					tempPhoneNumber, alternativePhone, email, "", accountType, address1, address2, address3, city,
					country, state, postalCode, "No", "", isSplitRes, getTestSteps);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentTypeMethod, cardNumber, nameOnCard,
					cardExpDate);

			reservationPage.SelectReferral(driver, referral);
			reservationPage.clickBookNow(driver, testSteps);

			reservationNumber = "";
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);

			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			String getTotalBalance = reservationPage.getTripSummaryBalance(driver, testSteps);
			Double totalBalanceInDouble = Double.parseDouble(getTotalBalance.trim());

			reservationPage.click_Folio(driver, testSteps);

			folio.makePayment(driver, "Capture", String.valueOf(totalBalanceInDouble + 100), testSteps);
			Wait.wait5Second();

			reservationPage.click_DeatilsTab(driver, testSteps);

			Wait.wait5Second();
			testSteps.add("<b>==========Start Check In==========</b>");
			reservationPage.clickCheckInAllButton(driver, testSteps);
			reservationPage.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
			reservationPage.checkInProcess(driver, testSteps);
			testSteps.add("<b>==========Start Verifying Check Out All Button ==========</b>");
			Wait.wait10Second();
			reservationPage.verifyCheckOutAllButton(driver, testSteps);
			testSteps.add("<b>==========Start Verifying IN-HOUSE Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, testSteps, config.getProperty("inHouse"));

			testSteps.add("Check Out reservation");
			app_logs.info("Check Out  reservation");
			res.verifyRefundPaymentPopupAtCheckoutPay(driver, testSteps, "Yes", "CheckOutAll");

			folio.makePayment(driver, "Refund");
			folio.ClickSaveFolioButton(driver);
			testSteps.add("Save button");

			homePage.departedReservation(driver, "CheckOutAll", "Yes", false, testSteps);
//			homePage.clickCloseCheckoutSuccessfullPopup(driver);
			String loading = "(//div[@class='ir-loader-in'])";
			Wait.explicit_wait_absenceofelement(loading, driver);

			testSteps.add("Verify reservation has been checked out and staus changes to DEPARTED");
			String expectedStatus = "DEPARTED";
			String getReservationStatus = reservationPage.getReservationStatusOnDetailSection(driver);
			Utility.verifyEquals(getReservationStatus, expectedStatus, testSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			statusCode.add(0, "1");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		Policies poli = new Policies();
		String PolicyDescription = "New Checkin policy creation" + randomString;

		try {
			Utility.refreshPage(driver);
			Wait.waitUntilPageIsLoaded(driver);
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/825425' target='_blank'>"
					+ "<b>Verify that user should able to check-in after CC is swiped successfully at the time of check-in</b></a> =====");

			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, testSteps);
			navigation.policies(driver);
			testSteps.add("Navigated to policies");
			app_logs.info("************** Creatin a CheckIn policy *******************");
			testSteps.add("************** Creatin a CheckIn policy *******************");
			poli.DeleteAllPolicies(driver, testSteps);
			poli.ClickNewPolicybutton(driver);
			poli.Enter_Policy_Name(driver, "CheckIn Ploicy", testSteps);
			WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
			Select select = new Select(selectType);
			select.selectByVisibleText("Check In");

			poli.Enter_Checkin_Policy_Attributes(driver, "authorize", "100");
			poli.Enter_Policy_Desc(driver, PolicyDescription, PolicyDescription);
			poli.Associate_Sources(driver);
			poli.Associate_Seasons(driver);
			poli.Associate_RoomClasses(driver, roomClassName);
			poli.Associate_RatePlan(driver, ratePlan);
			poli.Save_Policy(driver);
			navigation.cpReservationBackward(driver);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		paymentTypeMethod = "MC";
		try {

			reservationPage.click_NewReservation(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkInDates.get(1));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan, "");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, true);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentTypeMethod, cardNumber, nameOnCard,
					cardExpDate);

			reservationPage.SelectReferral(driver, referral);

			reservationPage.clickBookNow(driver, testSteps);

			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);

			reservationPage.Click_CheckInButton(driver, testSteps);
			reservationPage.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
			getTestSteps.clear();
			getTestSteps = homePage.clickConfirmChekInButton(driver);
			testSteps.addAll(getTestSteps);

			try {
				// reservationPage.clickOnProceedToCheckInPaymentButton(driver, testSteps);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				// homePage.clickConfirmCheckInButton(driver, testSteps);
			} catch (Exception e) {
				e.printStackTrace();
			}

			homePage.clickSwipeButtonInCheckInPopup(driver, testSteps);
			homePage.enterCardNumberInCheckInPopup(driver, testSteps, swipedCardNumber);
			homePage.clickAuthorizeInCheckInPopup(driver, testSteps);

			try {
				getTestSteps.clear();
				// getTestSteps = homePage.clickPayButton(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
			}

			try {
				getTestSteps.clear();
				getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {

			}
			String expectedStatus = "IN-HOUSE";
			testSteps.add("Verifying reservation status is '" + expectedStatus + "' after check in");

			String getReservationStatus = reservationPage.getReservationStatusOnDetailSection(driver);
			Utility.verifyEquals(getReservationStatus, expectedStatus, testSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

			testSteps.add("NAVIGATE TO Inventory");
			navigation.Inventory(driver, testSteps);
			navigation.policies(driver);
			testSteps.add("Navigated to policies");
			testSteps.add("Deleting all policies");
			poli.DeleteAllPolicies(driver, testSteps);

			navigation.cpReservationBackward(driver);

			statusCode.add(1, "1");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify checkin through swipe payment method", testName,
						"ChekcinPolicy", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify checkin through swipe payment method", testName,
						"ChekcinPolicy", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Utility.refreshPage(driver);
			Wait.waitUntilPageIsLoaded(driver);

			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/825468' target='_blank'>"
					+ "<b>Verify for the single room reservation, when \"Cancel\" reservation action is made, it should not throw error in \"Amount Due\" field with 0 value</b></a> =====");

			reservationPage.click_NewReservation(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkInDates.get(1));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlan, "");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";

			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, true);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentTypeMethod, cardNumber, nameOnCard,
					cardExpDate);

			reservationPage.SelectReferral(driver, referral);
			Wait.wait10Second();

			reservationPage.clickBookNow(driver, testSteps);

			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			homePage.cancelReservation(driver, testSteps, "test", "0");

			String expectedStatus = "CANCELLED";
			testSteps.add("Verifying reservation status is '" + expectedStatus + "'");

			String getReservationStatus = reservationPage.getReservationStatusOnDetailSection(driver);
			Utility.verifyEquals(getReservationStatus, expectedStatus, testSteps);
			statusCode.add(2, "1");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify checkin through swipe payment method", testName,
						"ChekcinPolicy", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify checkin through swipe payment method", testName,
						"ChekcinPolicy", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption",
						test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to change options in properties.", "ChangePropertiesOption",
						test_description, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyCheckoutProcessWithDiffer", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
