package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyDepositPolicyAppliedForAccount extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	NightlyRate nightlyRate = new NightlyRate();
	Policies policies = new Policies();
	RatesGrid rateGrid = new RatesGrid();
	Navigation navigation = new Navigation();
	ReservationHomePage reservationHomePage= new ReservationHomePage();
	String testName = null;
	String seasonStartDate = null, seasonEndDate = null, confirmationNo = null, roomClassNames = null,depositAmount=null;
	Account account = new Account();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyDepositPolicyAppliedForAccount(String checkInDate, String checkOutDate, String roomClassName,
			String maxAdults, String maxPersopns, String roomQuantity, String adults,String children,String ratePlanName, String rate,
			String depositPolicyName, String typeOfPolicy, String policyValue, String chargesTypes, String accountName,
			String accountType) throws ParseException {
		String testCaseID="848774";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyDepositPolicyAppliedForAccount";
		test_description = "CPVerifyDepositPolicyAppliedForAccount <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848774' target='_blank'>"
				+ "Click here to open TestRail: 848774</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848774", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> checkOutDatesForStayInfor = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		HashMap<String, ArrayList<String>> policiesNames = new HashMap<String, ArrayList<String>>();
		ArrayList<String> policiesNamesAre = new ArrayList<String>();
		ArrayList<String> policiesPercentage = new ArrayList<String>();
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				checkOutDatesForStayInfor
						.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate = checkInDate;
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}

		// Create Room Class
		try {
			testSteps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			testSteps.add("Navigated to Room Class");
			roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
			newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults, maxPersopns,
					roomQuantity);
			newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
			testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
			app_logs.info("Room Class Created: " + roomClassNames);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Create Policy======</b>");
			app_logs.info("======Create Policy======");
			navigation.inventoryFromRoomClass(driver, testSteps);
			navigation.policies(driver, testSteps);
			ArrayList<String> policiesAre = Utility.splitInputData(depositPolicyName);
			ArrayList<String> policiesPercentageAre = Utility.splitInputData(policyValue);
			for (int i = 0; i < policiesAre.size(); i++) {
			String policy = policiesAre.get(i) + Utility.generateRandomStringWithoutNumbers();
			String percentageOfPolicy = policiesPercentageAre.get(i);
			policiesNamesAre.add(policy);
			policiesPercentage.add(percentageOfPolicy);
			policiesNames = policies.createPolicies(driver, testSteps, "", "", "Deposit", "", policy, "", "",
					typeOfPolicy, percentageOfPolicy, chargesTypes, "", "", "No", "");
			}
			
			app_logs.info(policiesNamesAre);
			app_logs.info(policiesPercentage);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "policy", "policy", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "policy", "policy", testName, test_description,
					test_catagory, testSteps);

		}
		try {
			testSteps.add("<b>======Associated Policy with Existing Corporation Account  ======</b>");
			app_logs.info("======Associated Policy with Existing Corporation Account  ======");
			account.searchForAnAccountAndOpen(driver, testSteps, accountName, accountType);
			account.navigateFolio(driver);
			account.navigateFolioOptions(driver);
			HashMap<String, String> selectedPolicyNameS = new HashMap<String, String>();
			account.selectPolicyForAccount(driver, testSteps, policiesNamesAre.get(0), "Deposit", selectedPolicyNameS, "Yes");
			account.closeAccountTab(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Corporate Account Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Corporate Account Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		// Updating Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			app_logs.info("======Start Updating Rate Plan======");
			navigation.navigateSetup(driver);
			Wait.wait5Second();
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", false);
			Utility.refreshPage(driver);
			nightlyRate.switchCalendarTab(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
			nightlyRate.clickSeasonPolicies(driver, testSteps);
			nightlyRate.selectPolicy(driver, "Deposit", policiesNamesAre.get(1), true, testSteps);
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
			rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Start Creating Reservation ======</b>");
			app_logs.info("======Start Creating Reservation======");
			navigation.navReservationFromRateGrid(driver);
			reservation.click_NewReservation(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDate);
			reservation.select_CheckoutDate(driver, testSteps, checkOutDate);
			reservation.enter_Adults(driver, testSteps, adults);
			reservation.enter_Children(driver, testSteps, children);
			reservation.select_Rateplan(driver, testSteps, ratePlanName, "");
			reservation.clickOnFindRooms(driver, testSteps);
			ArrayList<String> rooms = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassNames);
			Wait.wait10Second();
			reservation.selectRoomToReserve(driver, testSteps, roomClassNames, rooms.get(0));
			Wait.wait10Second();
			reservation.ClickOnOverrideDepositAmount(driver, testSteps);
			reservation.clickNext(driver, testSteps);
			reservation.selectAccountOnReservation(driver, testSteps, accountName, "Yes", "No");
			testSteps.add("<b>======Verified Deposit Due Amount while Override Deposit Due On and Reservation Policy Applied======</b>");
			app_logs.info("======Verified Deposit Due Amount while Override Deposit Due On and Reservation Policy Applied======");
			reservation.verifyDepositDueAmount(driver, testSteps, "0.00");
			reservation.ClickOnOverrideDepositAmount(driver, testSteps);
			String tripTotal=reservation.getTripTotal_TripSummary(driver);
			ArrayList<String> roomCharges= new ArrayList<String>();
			roomCharges.add(tripTotal);
			depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomCharges, typeOfPolicy, policiesPercentage.get(1), "");
			testSteps.add("<b>======Verified Deposit Due Amount while Override Deposit Due Off and Reservation Policy Applied======</b>");
			app_logs.info("======Verified Deposit Due Amount while Override Deposit Due Off and Reservation Policy Applied======");			
			reservation.verifyDepositDueAmount(driver, testSteps, depositAmount);
			reservationHomePage.removeAssociatedAccount(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			reservation.selectAccountOnReservation(driver, testSteps, accountName, "Yes", "Yes");
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			depositAmount=null;
			depositAmount=reservation.calculationOfDepositAmountToBePaidForRateV2(roomCharges, typeOfPolicy, policiesPercentage.get(0), "");
			app_logs.info(depositAmount);
			testSteps.add("<b>======Verified Deposit Due Amount while Override Deposit Due Off and Account Policy Applied======</b>");
			app_logs.info("======Verified Deposit Due Amount while Override Deposit Due Off and Account Policy Applied======");			
			reservation.verifyDepositDueAmount(driver, testSteps, depositAmount);			
			/*testSteps.add("Verify the user is navigated back to current reservation page when user clicks NO on pop up to close the reservation without saving"
					+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848774' target='_blank'>"
					+ "Click here to open TestRail: 848774</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the user is navigated back to current reservation page when user clicks NO on pop up to close the reservation without saving");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Dirty room for Group Reservation and verify checkout functionality");
			}
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Checkin Successfully", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		// Delete Room Class
		try {
			testSteps.add("<b>======Delete Room Class======</b>");
			app_logs.info("======Delete Room Class ======");
			navigation.navigateToSetupFromReservationPage(driver, testSteps);
			testSteps.add("Navigated to Setup");	
			navigation.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, roomClassNames);
			testSteps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomClass.deleteAllRoomClassV2(driver, roomClassNames);
			testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassNames + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNames);
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Room Class", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Room Class", "GS", "GS", testName, test_description, test_catagory,
					testSteps);
		}
		
		try {
			testSteps.add("<b>======Delete Policy======</b>");
			navigation.inventoryFromRoomClass(driver, testSteps);
			testSteps.add("Navigated to Inventory");
			navigation.policies(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 5);
			policies.deleteAllPolicies(driver, testSteps, policiesNamesAre);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		
		}
	}

	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("CPVerifyDepositPolicyAppliedFor", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
