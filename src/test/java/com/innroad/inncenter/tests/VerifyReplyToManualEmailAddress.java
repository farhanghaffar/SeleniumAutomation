package com.innroad.inncenter.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DocumnetTemplates;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyReplyToManualEmailAddress extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> documentfields = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	String firstGuestName, propPhone, uFname, uLname, propAdd, docName, roomNumber1, roomNumber2, reservation, status,
			taxes;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyDocumentTemplateFieldsInEmail(String url, String ClientCode, String Username, String Password,
			String checkInDate, String checkOutDate, String Adults, String Children, String Rateplan, String PromoCode,
			String IsSplitRes, String RoomClass, String IsAssign, String IsDepositOverride,
			String DepositOverrideAmount, String IsAddMoreGuestInfo, String salutation, String guestFirstName,
			String guestLastName, String phoneNumber, String altenativePhone, String email, String account,
			String accountType, String address1, String address2, String address3, String city, String country,
			String state, String postalCode, String isGuesProfile, String paymentMethod, String cardNumber,
			String nameOnCard, String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue,
			String TravelAgent, String MarketSegment, String Referral, String IsAddNotes, String NoteType,
			String Subject, String Description, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus, String BillingNotes,
			String ContentFields, String ResStatus, String Function, String Attachment, String associateFunction)
			throws InterruptedException, IOException, Exception {
		test_name = "VerifyDocumentTemplateFieldsInEmail";
		test_description = "VerifyDocumentTemplateFields<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/702478' target='_blank'>"
				+ "Click here to open TestRail: C702478</a>";
		test_catagory = "Reservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		// testName=testName+"_"+ContentFields;
		DocumnetTemplates documentTemplate = new DocumnetTemplates();
		Navigation navigation = new Navigation();
		Properties properties = new Properties();
		// Login login = new Login();
		Admin admin = new Admin();
		Users user = new Users();
		CPReservationPage reservationPage = new CPReservationPage();
		RoomClass rc = new RoomClass();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		String randomNum = Utility.generateRandomNumber();
		guestLastName = guestLastName.replace("User", "User" + randomNum);
		ArrayList<String> totalNights = new ArrayList<>();
		System.out.println(guestFirstName.split("\\|").length);
		checkInDates.clear();
		checkOutDates.clear();
		if (!(Utility.validateInput(checkInDate))) {
			for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
			}
		} else {
			checkInDates = Utility.splitInputData(checkInDate);
			checkOutDates = Utility.splitInputData(checkOutDate);
		}
		for (int i = 0; i < checkInDates.size(); i++) {
			totalNights.add(Utility.differenceBetweenDates(checkInDates.get(i), checkOutDates.get(i)));
		}
		checkInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
		checkOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_NONGS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

		String masterCardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver, test_steps);
		try {
			test_steps.add("**************************  Get the User details ************************** ");
			navigation.Admin(driver);
			navigation.Users(driver);
			user.searchUser(driver, Username);
			user.search(driver);
			uFname = user.get_UserFirstName(driver, Username);
			uLname = user.get_UserLastName(driver, Username);
			navigation.Reservation_BackwardAdmin(driver);
			System.out.println(uFname);
			app_logs.info("User Frist Name:" + uFname);
			test_steps.add("User Frist Name:" + uFname);
			System.out.println(uLname);
			app_logs.info("User Frist Name:" + uLname);
			test_steps.add("User Frist Name:" + uLname);
			test_steps.add(
					"************************** Create new Document and add content fields ************************** ");
			docName = "Test Document" + Utility.generateRandomString();
			navigation.Setup(driver);
			navigation.Properties(driver);
			properties.openProperty(driver);
			properties.getPropLegalName(driver);
			propPhone = properties.getPropPhone(driver);
			properties.getPropEmail(driver);
			propAdd = properties.get_PropAddress(driver);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Document Template", testName,
						"Failed to Create Document Template", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Create Document Template", testName,
						"Failed to Create Document Template", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			navigation.ReservationV2_Backward(driver);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// New Reservation

		try {
			test_steps.add("**************************  Creating new Reservation ************************** ");

			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_Dates(driver, test_steps, checkInDate, checkOutDate, Adults, Children, Rateplan, "",
					"");
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.selectRoom(driver, test_steps, RoomClass.split("\\|")[0], Utility.RoomNo, "");
			reservationPage.selectRoom(driver, test_steps, RoomClass.split("\\|")[1], Utility.RoomNo, "");
			reservationPage.deposit(driver, test_steps, "", "");
			reservationPage.clickNext(driver, test_steps);
			taxes = reservationPage.getTaxesAndServiceCharges_TripSummary(driver);
			reservationPage.deposit(driver, test_steps, "", "");

			reservationPage.enter_MRB_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
					phoneNumber, altenativePhone, email, "", accountType, address1, address2, address3, city, country,
					state, postalCode, isGuesProfile, "", "", roomNumber);

			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard,
						masterCardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", "", Referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			status = reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			roomNumber1 = reservationPage.get_RoomNumber(driver, test_steps, "");
			roomNumber.add(roomNumber1);
			roomNumber2 = reservationPage.getSecondRoomNumber(driver, test_steps, "");
			roomNumber.add(roomNumber2);
			reservationPage.getFristReservationRoomNumber(driver, test_steps);
			String fristReservationRoomCharge = reservationPage.getFristReservationRoomCharge(driver, test_steps);
			reservationPage.getSecondReservationRoomNumber(driver, test_steps);
			String SecondReservationRoomCharge = reservationPage.getSecondReservationRoomCharge(driver, test_steps);
			reservationPage.click_Folio(driver, test_steps);

			reservationPage.click_Folio(driver, test_steps);

			String[] salu = salutation.split("\\|");
			guestFirstName.split("\\|");
			String[] guestLName = guestLastName.split("\\|");
			String[] mail = email.split("\\|");
			String[] phone = phoneNumber.split("\\|");
			String[] roomClass = RoomClass.split("\\|");
			String balance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
			taxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);

			String totalCharges = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
			String roomCharge = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);

			documentfields.add(propAdd);
			documentfields.add(account);
			documentfields.add(reservation);
			documentfields.add(status);
			documentfields.add(roomClass[0]);
			documentfields.add(guestLName[0]);
			documentfields.add(mail[0]);
			documentfields.add(salu[0]);
			documentfields.add(balance);
			documentfields.add(cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
			documentfields.add(country);
			documentfields.add("$");
			documentfields.add(masterCardExpDate);
			documentfields.add(MarketSegment);
			documentfields.add(Referral);
			documentfields.add(taxes);
			documentfields.add(roomCharge);
			documentfields.add("innCenter");
			documentfields.add(phone[0]);
			documentfields.add(paymentMethod);
			documentfields.add(propPhone);
			documentfields.add(uFname);
			String Add = address1 + "/" + address1 + "/" + address1 + "/" + city + "/" + state + "/" + postalCode + "/"
					+ country;
			documentfields.add(Add);
			documentfields.add(totalCharges);
			documentfields.add(uLname);
			documentfields.add(roomNumber1);
			documentfields.add(roomNumber2);
			documentfields.add(fristReservationRoomCharge);
			documentfields.add(SecondReservationRoomCharge);
			
			
			// Send Mail
			try {
				test_steps.add(
						"************************** DOCUMENT TEMPLATE FIELDS VALIDATION FROM MANUAL EMAIL *************************");
				app_logs.info(
						"**************************DOCUMENT TEMPLATE FIELDS VALIDATION FROM MANUAL EMAIL *************************");
				reservationPage.manualEmail(driver, mail[0], Attachment);
				test_steps.add("Successfully Sent Email ");
				app_logs.info("Successfully Sent Email");
				// res.SaveRes_Updated(driver);
				Wait.wait25Second();
				if (!(Attachment.equalsIgnoreCase("") || Attachment.equals(null))) {
					Attachment = Attachment + " - " + "Guest Registration - Reservation #:  " + reservation;
					app_logs.info("Reservation Attachment Name :" + Attachment);
					test_steps.add("Reservation Attachment Name :" + Attachment);
				} else {
					Attachment = Attachment + "Reservation #: " + reservation;
					app_logs.info("Reservation Attachment Name :" + Attachment);
					test_steps.add("Reservation Attachment Name :" + Attachment);
				}
				System.out.println("************** " + Attachment);
				EmailUtils emailUtils = new EmailUtils(EmailUtils.EmailFolder.INBOX);
				EmailUtils.setTargetDirectory(System.getProperty("user.dir") + File.separator + "externalFiles"
						+ File.separator + "downloadFiles" + File.separator + "GuestRegEmails");

				app_logs.info(
						"************************** DOWNLOAD MANUVAL EMAIL ATTACHMENT************************** ");
				test_steps
						.add("************************** DOWNLOAD MANUVAL EMAIL ATTACHMENT************************** ");
				emailUtils.downloadEmailAttachments("Guest Registration - Reservation #: " + reservation, true, 3);
				test_steps.add(
						"Verified MANUVAL Email Subject 'Guest Registration - Reservation #: " + reservation + "'");
				app_logs.info(
						"Verified MANUVAL Email Subject 'Guest Registration - Reservation #: " + reservation + "'");
				test_steps.add("Email Attachment Downloaded Successfully");
				app_logs.info("Email Attachment Downloaded Successfully");
				reservationPage.verifyEmailPDFDetails("GuestRegEmails", documentfields, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Send Email", testName, "Email", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					Utility.updateReport(e, "Failed to Send Email", testName, "Email", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("DocumentTemplateFieldsInEmail", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
