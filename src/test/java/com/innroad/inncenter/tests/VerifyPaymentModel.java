package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Driver;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Admin;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_GuestHistory;
import com.innroad.inncenter.webelements.Elements_Inventory;
import com.innroad.inncenter.webelements.Elements_Reservation;

public class VerifyPaymentModel extends TestCore {

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
	CPReservationPage res = new CPReservationPage();

	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Account accountPage = new Account();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "PaymentModel")
	public void verifyPaymentModel(String testCaseID, String Scenario, String resType, String ratePlan,
			String roomClassName, String PaymentType, String CardNumber, String NameOnCard, String CardExpDate,
			String CheckInDate, String CheckOutDate, String Adult, String Children, String Salutation,
			String GuestFirstName, String GuestLastName, String Email, String Referral) throws Exception {
		test_name = Scenario;
		test_description = "VerifyPaymentModel"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848533' target='_blank'>"
				+ "Click here to open TestRail: C848533</a><br>";
		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		ReservationHomePage homePage = new ReservationHomePage();
		String testName = null;

		if (!Utility.validateString(testCaseID)) {
			caseId.add("848533");
			statusCode.add("4");
		} else {
			String[] testcase = testCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}

		}
		String randomString = Utility.GenerateRandomString();
		String salutation = "Mr.";
		String guestFirstName = "VerifyRes" + randomString;
		String guestLastName = "Realization" + randomString;
		String paymentTypeMethod = "MC";
		String referral = "Other";
		String guaranteedStatus = "Guaranteed";

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

		ArrayList<String> roomNos = new ArrayList<String>();
		String reservation = null;
		test_catagory = "Verification";
		testName = test_name;

		String timeZone = "US/Eastren";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.getNextDate(1, "MM/dd/yyyy", timeZone);
		String maxAdult = "1";
		String maxPerson = "0";
		String property = "Reports Property";
		Elements_Accounts accountEle = new Elements_Accounts(driver);
		String reservationNumber = "";
		Elements_CPReservation resElement = new Elements_CPReservation(driver);
		Elements_GuestHistory guest_his = new Elements_GuestHistory(driver);
		String city = "New york";
		Policies poli = new Policies();

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

		try {

			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					if (!resType.equalsIgnoreCase("Split")) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					} else {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkInDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						checkOutDates
								.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
						break;
					}
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			if (resType.equalsIgnoreCase("MRB") || resType.equalsIgnoreCase("Split")) {
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				app_logs.info(CheckInDate);
				app_logs.info(CheckOutDate);
			} else {
				CheckInDate = checkInDates.get(0);
				CheckOutDate = checkOutDates.get(0);
				app_logs.info(CheckInDate);
				app_logs.info(CheckOutDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			// After login
			testSteps.add("=================== NAVIGATE TO INVENTORY ======================");
			app_logs.info("=================== NAVIGATE TO INVENTORY ======================");
			navigation.Inventory(driver, testSteps);
			navigation.policies(driver);
			testSteps.add("Navigated to policies");
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
		if (Scenario.contains("Desktop : Verify 'Payment Info' section functionality on model content pop up.")) {
			try {

				app_logs.info("************** Creatin a Cancellation policy *******************");
				testSteps.add("************** Creatin a Cancellation policy *******************");
				poli.DeleteAllPolicies(driver, testSteps);
				poli.ClickNewPolicybutton(driver);
				poli.Enter_Policy_Name(driver, "CancellationPloicy", testSteps);
				WebElement selectType = driver.findElement(By.xpath(OR.SelectPolicyType));
				Select select = new Select(selectType);
				select.selectByVisibleText("Cancellation");
				poli.Cancellation_policy_Attributes(driver, "Room Charges", "20", "Beyond", "0", testSteps);
				poli.Enter_Policy_Desc(driver, "Cancellation Policy ", "Cancellation Policy");
				poli.Associate_Sources(driver);
				poli.Associate_Seasons(driver);
				poli.Associate_RoomClasses(driver, roomClassName);
				poli.Associate_RatePlan(driver, ratePlan);
				poli.Save_Policy(driver);
				navigation.cpReservationBackward(driver);
				statusCode.add(0, "1");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, testName, driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "DepositPolicy", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		if (Scenario.contains("Desktop : Verify 'Payment Info' section functionality on model content pop up.")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825141'>"
					+ "Click here to open TestRail: </a>";
			try {
				res.click_NewReservation(driver, testSteps);
				app_logs.info("CheckOut Date : " + checkOutDates.get(0));
				res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
				res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
				testSteps.addAll(res.enterAdult(driver, Adult));
				testSteps.addAll(res.enterChildren(driver, Children));
				testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
				res.clickOnFindRooms(driver, testSteps);
				res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
				res.clickNext(driver, testSteps);

				res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
						phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

				res.enterEmail(driver, testSteps, Email);
				//
				testSteps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, testSteps);
				roomNos = res.getStayInfoRoomNo(driver, testSteps);
				reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
				res.clickCloseReservationSavePopup(driver, testSteps);
				Elements_Reservation elementReservation = new Elements_Reservation(driver);
				Wait.wait1Second();
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_Reservation.ReservationStatusDropDown)));
				elementReservation.ReservationStatusCancel.click();
				try {
					Wait.WaitForElement(driver, OR_Reservation.ModelHeader);
					String verifyPopupHeader = driver.findElement(By.xpath(OR_Reservation.ModelHeader)).getText();
					testSteps.add("==========Verified <b>" + verifyPopupHeader + "</b> model is Displayed   ==========");

				} catch (Exception e) {

				}
				elementReservation.CancellationReason.sendKeys("Cancel");

				try {
					elementReservation.ProceedToCancellationPayment.click();
				} catch (Exception e) {
					elementReservation.ProceedToPaymentButton2.click();
				}

				WebElement element = driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount));
				Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount), driver);
				assertEquals(element.isEnabled(), true);
				testSteps.add("Verified override amount field is enabled");
				assertEquals(elementReservation.Log.isEnabled(), true);
				testSteps.add("Verified pay button is enabled");
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (testCaseID.contains("848660")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825360'>"
					+ "Click here to open TestRail: </a>";
			try {
				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, testSteps);
				app_logs.info("CheckOut Date : " + checkOutDates.get(0));
				res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
				res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
				testSteps.addAll(res.enterAdult(driver, Adult));
				testSteps.addAll(res.enterChildren(driver, Children));
				testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
				res.clickOnFindRooms(driver, testSteps);
				res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
				res.clickNext(driver, testSteps);

				res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
						phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

				res.enterEmail(driver, testSteps, Email);
				//
				testSteps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, testSteps);
				roomNos = res.getStayInfoRoomNo(driver, testSteps);
				reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
				res.clickCloseReservationSavePopup(driver, testSteps);
				res.click_Folio(driver, testSteps);
				reservationPage.click_Pay(driver, testSteps);
				res.takePayment(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "", "",
						"No", "");
				homePage.LineItemDescritionContainSpecialCharacter(driver, testSteps);
				homePage.CheckPanelGuestNameContainSpecialCharter(driver, testSteps);
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

		else if (Scenario
				.contains("Verify reservation folio CC payment when cardholder name contains special characters")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825361'>"
					+ "Click here to open TestRail: </a>";
			try {
				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, testSteps);
				app_logs.info("CheckOut Date : " + checkOutDates.get(0));
				res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
				res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
				testSteps.addAll(res.enterAdult(driver, Adult));
				testSteps.addAll(res.enterChildren(driver, Children));
				testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
				res.clickOnFindRooms(driver, testSteps);
				res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
				res.clickNext(driver, testSteps);

				res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
						phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

				res.enterEmail(driver, testSteps, Email);
				//
				testSteps.addAll(res.selectReferral(driver, Referral));
				res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

				res.clickBookNow(driver, testSteps);
				roomNos = res.getStayInfoRoomNo(driver, testSteps);
				reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
				res.clickCloseReservationSavePopup(driver, testSteps);
				res.click_Folio(driver, testSteps);
				reservationPage.click_Pay(driver, testSteps);
				res.takePayment(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "", "",
						"No", "");
				homePage.LineItemDescritionContainSpecialCharacter(driver, testSteps);
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} else if (Scenario.contains("Verify Interval rate with Old Rates.")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/824925'>"
					+ "Click here to open TestRail: </a>";
			try {
				String rateName = "Test Rate";
				Rate ratePage = new Rate();
				Elements_Inventory rate = new Elements_Inventory(driver);
				driver.findElement(By.xpath(OR.NavInventoryFromGroupBlock)).click();
				rate.inventory_rate.click();
				Wait.waitForElementToBeClickable(By.xpath(OR.newRate), driver);
				rate.newRate.click();
				Wait.waitForElementToBeVisibile(By.xpath(OR.rateName), driver);
				rate.rateName.sendKeys(rateName);
				testSteps.add("Provided Rate Name as <b> " + rateName + "</b> ");
				String ratePlanSelected = new Select(rate.ratePlan).getFirstSelectedOption().getText();
				if (!(ratePlanSelected.equalsIgnoreCase(ratePlan))) {
					new Select(rate.ratePlan).selectByVisibleText(ratePlan);

					testSteps.add("Selected Rate Plan  : <b>" + ratePlan + "</b>");
				} else {
					testSteps.add("Rate Plan is already selected as : <b>" + ratePlan + "</b>");
				}
				String rateType = rate.getRateType.getText();
				testSteps.add("Selected Rate Type : <b>" + rateType + "</b>");
				rate.maxAdults.sendKeys(Adult);
				testSteps.add("Provided max adults as : <b>" + Adult + "</b>");
				rate.maxPersons.sendKeys(Adult);
				testSteps.add("Provided max persons as : <b>" + Adult + "</b>");
				rate.baseAmount.sendKeys(Adult);
				testSteps.add("Provided base amount as : <b>" + Adult + "</b>");
				Wait.waitForElementToBeVisibile(By.xpath(OR.additionalAdult), driver);
				rate.additionalAdult.sendKeys(Adult);
				testSteps.add("Provided additional adult as : <b>" + Adult + "</b>");
				Wait.waitForElementToBeVisibile(By.xpath(OR.additionalChild), driver);
				rate.additionalChild.sendKeys(Children);
				testSteps.add("Provided additional children as : <b>" + Children + "</b>");
				String conditionalRate = "//input[@id='MainContent_rbtnRackRateYes']";
				driver.findElement(By.xpath(conditionalRate)).click();
				testSteps.add("Click on conditional yes radio button");
				String proRatePlan = "//input[@id='MainContent_rbtnProratedYes']";
				driver.findElement(By.xpath(proRatePlan)).click();
				testSteps.add("Click on pro rate plan yes radio button");
				Wait.waitForElementToBeVisibile(By.xpath(OR.rate_displayName), driver);
				rate.rate_displayName.sendKeys("Test Rate");
				testSteps.add("Provided rate display name as : <b>" + "Test Rate" + "</b>");
				Wait.waitForElementToBeVisibile(By.xpath(OR.rate_policy), driver);
				rate.rate_policy.sendKeys(rateName);
				testSteps.add("Provided rate policy as : <b>" + rateName + "</b>");
				Wait.waitForElementToBeVisibile(By.xpath(OR.rate_description), driver);
				rate.rate_description.sendKeys(rateName);
				testSteps.add("Provided rate description as : <b>" + rateName + "</b>");

				ratePage.AssociateSeason(driver, "All Year Season", testSteps);
				ratePage.AssociateRoomClass(driver, roomClassName, testSteps);
				ratePage.AssociateSource(driver, "innCenter", testSteps);

				Wait.waitForElementToBeClickable(By.xpath(OR.rate_Save_Button), driver);
				rate.rate_Save_Button.click();
				testSteps.add("Saved the Details");
				try {
					WebDriverWait wait = new WebDriverWait(driver, 90);
					wait.until(ExpectedConditions.elementToBeClickable(rate.rate_done_button));
					rate.rate_done_button.click();
					testSteps.add("Clicked on Done");
				} catch (Exception e) {
					driver.navigate().back();
					Wait.explicit_wait_visibilityof_webelement(rate.rate_Save_Button, driver);
					rate.rate_Save_Button.click();
					Wait.explicit_wait_visibilityof_webelement(rate.rate_done_button, driver);
					WebDriverWait wait = new WebDriverWait(driver, 90);
					wait.until(ExpectedConditions.elementToBeClickable(rate.rate_done_button));
					rate.rate_done_button.click();
				}
				driver.navigate().refresh();
				testSteps.add("Successfully Created <b>" + rateName + "</b>' Rate for <b>" + ratePlan + "</b> Rate Plan");
				driver.findElement(By.xpath(OR.Reservation_Backward_4)).click();
				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, testSteps);
				app_logs.info("CheckOut Date : " + checkOutDates.get(0));
				res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
				res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
				testSteps.addAll(res.enterAdult(driver, Adult));
				testSteps.addAll(res.enterChildren(driver, Children));
				testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
				res.clickOnFindRooms(driver, testSteps);
				String rulessize = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"

						+ roomClassName.trim() + "')]/following-sibling::span";
				Wait.waitForElementToBeVisibile(By.xpath(rulessize), driver);
				driver.findElement(By.xpath(rulessize)).click();
				String ruleCharges = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"

						+ roomClassName.trim()
						+ "')]//..//..//..//..//following-sibling::div//td[contains(@data-bind,'showInnroadCurrency: { value: roomTotal ')]";
				String value = driver.findElement(By.xpath(ruleCharges)).getText();
				testSteps.add("Get amount after applying rule on room charges <b>" + value + "</b>");
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		else if (Scenario.contains("Verify that each new room defaults to search GBR promo code")) {
			
			test_name = Scenario;
			test_description = Scenario + "<br>" + "https://innroad.testrail.io/index.php?/cases/view/825016";
			test_catagory = Scenario;
			testName = test_name;

			try {
				testSteps.add("<b>****Start Creating New MRB Reservation****</b>");
				Wait.wait10Second();
				navigation.cpReservationBackward(driver);
				res.click_NewReservation(driver, testSteps);
				res.createMRBReservations(driver, testSteps, CheckInDate, CheckOutDate, Adult, Children, "Promo Code",
						"Last minute", roomClassName, "Yes", "", "", Salutation, GuestFirstName, GuestLastName,
						phoneNumber, phoneNumber, Email, accountType, address1, address2, address3, city, country,
						state, postalcode, "", Referral, PaymentType, CardNumber, NameOnCard, CardExpDate, "", "",
						roomNos);
				res.clickBookNow(driver, testSteps);
				reservation = res.get_ReservationConfirmationNumber(driver, testSteps);
				roomNos = res.getStayInfoRoomNo(driver, testSteps);
				res.clickCloseReservationSavePopup(driver, testSteps);
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		} else if (Scenario.contains(
				"Verify No rate combination found when Departure date is extended when reservation is created with conditional rate")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825526'>"
					+ "Click here to open TestRail: </a>";
			
			/*
			String rateName = "Test Rate";
			Admin admin = new Admin();
			navigation.navigateToAdminPage(driver, testSteps);
			driver.findElement(By.xpath(OR.Client_info)).click();
			admin.clickClientName(driver);
			driver.findElement(By.xpath(OR_Admin.CLICK_CLIENT_OPTION)).click();
			String inventorySubSystem = "//input[@id='MainContent_rbtnInventory']";
			Wait.waitForElementByXpath(driver,inventorySubSystem);
			WebElement invSubSystem=driver.findElement(By.xpath(inventorySubSystem));
			Utility.clickThroughJavaScript(driver,invSubSystem);
			//driver.findElement(By.xpath(inventorySubSystem)).click();
			testSteps.add("Click on inventory sub system radio button");
			admin.adminSaveButton(driver);
			Rate ratePage = new Rate();
			Elements_Inventory rate = new Elements_Inventory(driver);
			driver.findElement(By.xpath(OR.NavInventoryFromGroupBlock)).click();
			rate.inventory_rate.click();
			Wait.waitForElementToBeClickable(By.xpath(OR.newRate), driver);
			rate.newRate.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR.rateName), driver);
			rate.rateName.sendKeys(rateName);
			testSteps.add("Provided Rate Name as <b> " + rateName + "</b> ");
			String ratePlanSelected = new Select(rate.ratePlan).getFirstSelectedOption().getText();
			if (!(ratePlanSelected.equalsIgnoreCase(ratePlan))) {
				new Select(rate.ratePlan).selectByVisibleText(ratePlan);

				testSteps.add("Selected Rate Plan  : <b>" + ratePlan + "</b>");
			} else {
				testSteps.add("Rate Plan is already selected as : <b>" + ratePlan + "</b>");
			}
			String rateType = rate.getRateType.getText();
			testSteps.add("Selected Rate Type : <b>" + rateType + "</b>");
			rate.maxAdults.sendKeys(Adult);
			testSteps.add("Provided max adults as : <b>" + Adult + "</b>");
			rate.maxPersons.sendKeys(Adult);
			testSteps.add("Provided max persons as : <b>" + Adult + "</b>");
			rate.baseAmount.sendKeys(Adult);
			testSteps.add("Provided base amount as : <b>" + Adult + "</b>");
			Wait.waitForElementToBeVisibile(By.xpath(OR.additionalAdult), driver);
			rate.additionalAdult.sendKeys(Adult);
			testSteps.add("Provided additional adult as : <b>" + Adult + "</b>");
			Wait.waitForElementToBeVisibile(By.xpath(OR.additionalChild), driver);
			rate.additionalChild.sendKeys(Children);
			testSteps.add("Provided additional children as : <b>" + Children + "</b>");
			String conditionalRate = "//input[@id='MainContent_rbtnRackRateYes']";
			driver.findElement(By.xpath(conditionalRate)).click();
			testSteps.add("Click on conditional yes radio button");
			String stayThroughYesButton = "//input[@id='MainContent_rbtnStayThroughyes']";
			driver.findElement(By.xpath(stayThroughYesButton)).click();
			testSteps.add("Click on stay through yes radio button");

			String minStay = "//input[@id='MainContent_txtminStay']";
			driver.findElement(By.xpath(stayThroughYesButton)).sendKeys("3");
			String maxStay = "//input[@id='MainContent_txtmaxStay']";
			driver.findElement(By.xpath(stayThroughYesButton)).sendKeys("7");

			Wait.waitForElementToBeVisibile(By.xpath(OR.rate_displayName), driver);
			rate.rate_displayName.sendKeys("Test Rate");
			testSteps.add("Provided rate display name as : <b>" + "Test Rate" + "</b>");
			Wait.waitForElementToBeVisibile(By.xpath(OR.rate_policy), driver);
			rate.rate_policy.sendKeys(rateName);
			testSteps.add("Provided rate policy as : <b>" + rateName + "</b>");
			Wait.waitForElementToBeVisibile(By.xpath(OR.rate_description), driver);
			rate.rate_description.sendKeys(rateName);
			testSteps.add("Provided rate description as : <b>" + rateName + "</b>");

			ratePage.AssociateSeason(driver, "All Year Season", testSteps);
			ratePage.AssociateRoomClass(driver, roomClassName, testSteps);
			ratePage.AssociateSource(driver, "innCenter", testSteps);

			Wait.waitForElementToBeClickable(By.xpath(OR.rate_Save_Button), driver);
			rate.rate_Save_Button.click();
			testSteps.add("Saved the Details");
			try {
				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions.elementToBeClickable(rate.rate_done_button));
				rate.rate_done_button.click();
				testSteps.add("Clicked on Done");
			} catch (Exception e) {
				driver.navigate().back();
				Wait.explicit_wait_visibilityof_webelement(rate.rate_Save_Button, driver);
				rate.rate_Save_Button.click();
				Wait.explicit_wait_visibilityof_webelement(rate.rate_done_button, driver);
				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions.elementToBeClickable(rate.rate_done_button));
				rate.rate_done_button.click();
			}
			driver.navigate().refresh();
			testSteps.add("Successfully Created <b>" + rateName + "</b>' Rate for <b>" + ratePlan + "</b> Rate Plan");
			
			driver.findElement(By.xpath(OR.Reservation_Backward_4)).click();
			*/
			navigation.cpReservationBackward(driver);
			res.click_NewReservation(driver, testSteps);
			app_logs.info("CheckOut Date : " + checkOutDates.get(0));
			res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
			res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
			testSteps.addAll(res.enterAdult(driver, Adult));
			testSteps.addAll(res.enterChildren(driver, Children));
			testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
			res.clickOnFindRooms(driver, testSteps);
			res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
			res.clickNext(driver, testSteps);

			res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
					phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

			res.enterEmail(driver, testSteps, Email);
			//
			testSteps.addAll(res.selectReferral(driver, Referral));
			res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

			res.clickBookNow(driver, testSteps);
			roomNos = res.getStayInfoRoomNo(driver, testSteps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
			res.clickCloseReservationSavePopup(driver, testSteps);

			WebDriverWait wait = new WebDriverWait(driver, 25);
			res.closeAllOpenedReservations(driver);
			res.Search_ResNumber_And_Click(driver, reservationNumber);

			try {
				Wait.wait10Second();
				String EditReservation = "//button[contains(@class,'stay-info-edit')]/i";
				Wait.WaitForElement(driver,EditReservation);
				//Utility.clickThroughJavaScript(driver, resElement.EDITReservation);
				driver.findElement(By.xpath(EditReservation)).click();
				testSteps.add("Click Edit Stay Info");

				String changeStayInfo = "//ul//li[contains(text(),'Change Stay Details')]";
				//By locator = By.xpath(changeStayInfo);
				Wait.WaitForElement(driver, changeStayInfo);
				//List<WebElement> changeStayInfoEl = driver.findElements(By.xpath(changeStayInfo));
				driver.findElement(By.xpath(changeStayInfo)).click();				
				testSteps.add("Click on Change Stay Info");
				CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
				res.select_CheckoutDate(driver, testSteps, CheckOutDate);
				res.clickFindRooms(driver);
				Wait.wait10Second();
				String saveBtn="//button[contains(@class,'btn btn-success text-uppercase')]";
				Wait.WaitForElement(driver, saveBtn);
				//Wait.explicit_wait_visibilityof_webelement(resElement.CP_SaveRoomNo, driver);
				//Utility.ScrollToElement(resElement.CP_SaveRoomNo, driver);
				driver.findElement(By.xpath(saveBtn)).click();
				String YesBtn="//div[contains(text(),'Are you sure you wish to change the total cost ')]/..//button[contains(@class,'confirmation-yes')]";
				Wait.WaitForElement(driver, YesBtn);
				driver.findElement(By.xpath(YesBtn)).click();
				testSteps.add("Click on Save Room Number Button");
				statusCode.add(0, "1");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (Scenario
				.contains("Desktop : Verify 'Payment Info' section functionality on model content pop up.")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825141'>"
					+ "Click here to open TestRail: </a>";
			navigation.cpReservationBackward(driver);
			res.click_NewReservation(driver, testSteps);
			app_logs.info("CheckOut Date : " + checkOutDates.get(0));
			res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
			res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
			testSteps.addAll(res.enterAdult(driver, Adult));
			testSteps.addAll(res.enterChildren(driver, Children));
			testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
			res.clickOnFindRooms(driver, testSteps);
			res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
			res.clickNext(driver, testSteps);

			res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
					phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

			res.enterEmail(driver, testSteps, Email);
			//
			testSteps.addAll(res.selectReferral(driver, Referral));
			res.enter_PaymentDetails(driver, testSteps, PaymentType, invalidCard, NameOnCard, CardExpDate);

			res.clickBookNow(driver, testSteps);
			roomNos = res.getStayInfoRoomNo(driver, testSteps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
			res.clickCloseReservationSavePopup(driver, testSteps);
			Elements_Reservation elementReservation = new Elements_Reservation(driver);
			Wait.wait1Second();
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(OR_Reservation.ReservationStatusDropDown)));
			elementReservation.ReservationStatusCancel.click();
			try {
				Wait.WaitForElement(driver, OR_Reservation.ModelHeader);
				String verifyPopupHeader = driver.findElement(By.xpath(OR_Reservation.ModelHeader)).getText();
				testSteps.add("==========Verified <b>" + verifyPopupHeader + "</b> model is Displayed   ==========");

			} catch (Exception e) {

			}
			elementReservation.CancellationReason.sendKeys("Cancel");

			try {
				elementReservation.ProceedToCancellationPayment.click();
			} catch (Exception e) {
				elementReservation.ProceedToPaymentButton2.click();
			}

			WebElement element = driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount));
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount), driver);
			assertEquals(element.isEnabled(), true);
			testSteps.add("Verified override amount field is enabled");
			assertEquals(elementReservation.Log.isEnabled(), true);
			testSteps.add("Verified pay button is enabled");
			statusCode.add(0, "1");

		}

		else if (Scenario.contains(
				"Verify Payment pop-up displaying after changing status from Reserved/Confirmed to Guaranteed.")) {
			test_description = "<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/825202'>"
					+ "Click here to open TestRail: </a>";
			navigation.cpReservationBackward(driver);
			res.click_NewReservation(driver, testSteps);
			app_logs.info("CheckOut Date : " + checkOutDates.get(0));
			res.select_CheckInDate(driver, testSteps, checkInDates.get(0));
			res.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
			testSteps.addAll(res.enterAdult(driver, Adult));
			testSteps.addAll(res.enterChildren(driver, Children));
			testSteps.addAll(res.selectRateplan(driver, ratePlan, "", 1));
			res.clickOnFindRooms(driver, testSteps);
			res.selectRoom(driver, testSteps, roomClassName, "Yes", "");
			res.clickNext(driver, testSteps);

			res.enter_MailingAddress(driver, testSteps, Salutation, GuestFirstName, GuestLastName, phoneNumber,
					phoneNumber, Email, "", "", address, address2, address3, city, country, state, postalCode, "No");

			res.enterEmail(driver, testSteps, Email);
			testSteps.addAll(res.selectReferral(driver, Referral));
			res.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);

			res.clickBookNow(driver, testSteps);
			roomNos = res.getStayInfoRoomNo(driver, testSteps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, testSteps);
			res.clickCloseReservationSavePopup(driver, testSteps);
			res.changeReservationStatus(driver, "Confirmed", "", testSteps);
			res.click_Folio(driver, testSteps);
			reservationPage.click_Pay(driver, testSteps);
			res.takePayment(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate, "Capture", "", "",
					"No", "");
			statusCode.add(0, "1");

		}

		try {
			comments = "Verified reset option";
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport("Verify Reset Option across the screens", test_description,
					"Verify Reset Option across the screens", testSteps);
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

		return Utility.getData("VerifyPaymentModel", excel_Swarna);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}