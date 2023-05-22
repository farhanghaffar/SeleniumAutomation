package com.innroad.inncenter.tests;

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
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.pageobjects.Distribution;

public class VerifyInventoryScenarios_2429 extends TestCore {
	private WebDriver driver = null;
	private static String test_name = "";
	private static String testDescription = "";
	private static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> Rooms = new ArrayList<String>();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	private Properties properties;
	private Distribution distribution;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "RegressoionCases_bakar")
	public void verifyInventoryScenarios_2429(String testCaseID,String ratePlanName, String roomClassName, String property) throws ParseException {
		test_name = "VerifyInventoryScenarios_2429";
		testDescription = "Verify Inventory Rate <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848734' target='_blank'>"
				+ "Click here to open TestRail: C848734</a>";
		testCategory = "VerifyInventoryScenarios";
		String testName = test_name;
	
		if(!Utility.validateString(testCaseID)) {
			caseId.add("848734");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		printString("caseId : " + caseId.size());
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		Season season = new Season();
		properties = new Properties();
		distribution = new Distribution();
		String randomString = Utility.GenerateRandomNumber(3);

		String source = "innCenter";
		String isAssign = "Yes";
		String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
		String checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy");
		String maxAdult = "1";
		String maxPerson = "0";
		String salutation = "Mr.";
		String guestFirstName = "VerifyDefaultStatus" + randomString;
		String guestLastName = "Res" + randomString;
		String referral = "Other";
		String defaultStatus = "Confirmed";
		
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
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		try {
			//825548
			testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825548' target='_blank'>"
					+ "<b> ===== Verify creating season with start and end on same day. ===== </b> </a>");
			
			navigation.Inventory(driver);
			testSteps.add("Navigate to inventory");
			
			navigation.Seasons(driver);
			testSteps.add("Navigate to Seasons");

			season.NewSeasonButtonClick(driver);
			testSteps.add("Click New Season Button");

			season.SelectStartDate(driver, 2);
			testSteps.add("Selecte start date : " + Utility.getDatePast_FutureDate(2));

			season.SelectEndDate(driver, 1);
			testSteps.add("Selecte end date : " + Utility.getDatePast_FutureDate(1));

			season.SaveSeason(driver);
			testSteps.add("Click on save button");

			testSteps.add("Verify Error message 'Start Date cannot be later than End Date' is showing.");
			String getText = season.getDateErrorMsg(driver);
			Utility.verifyEquals(getText, "Start Date cannot be later than End Date", testSteps);
			testSteps.add("Verified creating season with start greater than end date shows error message 'Start Date cannot be later than End Date'.");
			
			Utility.logoutToInnCenter(driver, testSteps);
			statusCode.add(0, "1");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify creating season with start greater than end date shows error message 'Start Date cannot be later than End Date'.", "SeasonDates", "Season", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to verify creating season with start greater than end date shows error message 'Start Date cannot be later than End Date'.", "SeasonDates", "Season", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
		//824621
			testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824621' target='_blank'>"
					+ "<b> ===== Verify that user is able to distribute the sources. ===== </b> </a>");

			loginSuperUser(driver);
			properties.selectPropertyWithSuperUser(driver, property, testSteps);
			testSteps.add("Logged into the application by super user");
			app_logs.info("Logged into the application by super user");
			
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			
			navigation.Distribution(driver);
			testSteps.add("Navigate distribution");
			app_logs.info("Navigate distribution");
			
			distribution.selectDefaultStatusOfChannel(driver, testSteps, source, defaultStatus);
			
			navigation.cpReservationBackward(driver);
			testSteps.add("Navigate back to reservation");
			app_logs.info("Navigate back to reservation");
		
		} catch (Exception e) {
			e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login by super user", "SuperUser_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login by super user", "SuperUser_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		
		try {
			testSteps.add("<b> ===== CREATING RESERVATION ===== </b>");
			app_logs.info("===== CREATING RESERVATION =====");
			reservationPage.click_NewReservation(driver, testSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDate);
			testSteps.addAll(getTestSteps);
			app_logs.info(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver, checkOutDate);
			testSteps.addAll(getTestSteps);
			
			reservationPage.enter_Adults(driver, testSteps, maxAdult);
			reservationPage.enter_Children(driver, testSteps, maxPerson);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName, "");
			reservationPage.clickOnFindRooms(driver, testSteps);
			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.SelectReferral(driver, referral);
			
			reservationPage.clickBookNow(driver, testSteps);
			reservationPage.get_ReservationConfirmationNumber(driver, testSteps);

			String reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);

			testSteps.add("<b> ===== Verify Default status for reservation is set to '"+ defaultStatus +"' ===== </b>");
			Utility.verifyEquals(reservationStatus, defaultStatus, getTestSteps);
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			testSteps.add("Verified Default status for reservation is set to '"+ defaultStatus +"'");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");

			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			
			navigation.Distribution(driver);
			
			distribution.selectDefaultStatusOfChannel(driver, testSteps, source, "Reserved");

			comments = " Verified Inventory Scenarios";
			statusCode.add(1, "1");

			RetryFailedTestCases.count = Utility.reset_count;
		    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		}


	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyInventoryScenarios_2429", excel_Swarna);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
