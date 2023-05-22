package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyHomePageAndAdvanceSearch extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyHomePageAndAdvanceSearch(String testCases, String delim,
			//Search Parameter
			String columnsToSearch, String numberOfReservation) throws Exception {
		Utility.DELIM = "\\" + delim;
		String name = "";
		String[] columnArr = columnsToSearch.split(Utility.DELIM);
		ArrayList<String> columnsList = new ArrayList<>();
		if(columnArr == null) {
			app_logs.info("No columns avaialble");
		}else{
			for(int i=0;  i < columnArr.length; i++) {		
				columnsList.add(columnArr[i].trim());		
				if(i == 0) {
					name = columnArr[i].trim(); 					
				}
				else if(i == columnArr.length -1) {
					name = name+" and " + columnArr[i].trim();
					
				}else{
					name = name+", " + columnArr[i].trim();
					
				}				
			}
		}
		app_logs.info("name : " + name);
		app_logs.info("columnsList : "+ columnsList.toString() + " : " +  columnsList.size());
	
		testName = "";
		testName = "VerifyHomePage - " + testCases;
		testDescription = "Validate HomePage and Advance search";
		if(testCases.equalsIgnoreCase("VerifyAdvanceSearch")) {
			testName = "";
			testName = "Advance search- Verify advance search using " + name;
			testDescription = "Validate Advance search fields based on different combination of fields present in advance search";
			
		}
		testCatagory = "HomePageValidation";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		ReservationHomePage homePage = new ReservationHomePage();
		Navigation navigation = new Navigation();
		ReservationSearch reservationSearch =new ReservationSearch();	
		Account CreateTA = new Account();
		Folio folio = new Folio();
		RatesGrid ratesGrid = new RatesGrid();
		DerivedRate derivedRate = new DerivedRate();
		NightlyRate nightlyRate = new NightlyRate();

		String randomString = Utility.GenerateRandomString();
		String randomNumber = Utility.GenerateRandomNumber(10);
		String timeZone = "US/Eastren";
		String defaultDateFormat = "MM/dd/yyyy";
		String checkInDate=Utility.getCurrentDate(defaultDateFormat, timeZone);
		String checkOutDate=Utility.getNextDate(1, defaultDateFormat);
		app_logs.info("checkInDate : "  + checkInDate);
		app_logs.info("checkOutDate : " + checkOutDate);
		int nights = Utility.getNumberofDays(checkInDate, checkOutDate);
		app_logs.info("getNights :  "  +nights);

		ArrayList<String> getDateRange = Utility.getAllDatesBetweenCheckInOutDates(ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"), ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
		for(int i=0; i < getDateRange.size(); i++) {
			app_logs.info(getDateRange.get(i));
		}

		String promoCode = Utility.GenerateRandomNumber(5);
		String salutation = "Mr."; 
		String guestFirstName = "VerifyHomePage" + randomString; 
		String guestLastName = "Res" + randomString;
		
		String guestName = guestFirstName + " " + guestLastName;
		String accountNumber = "";
		String account = "CorporateAccount" + Utility.generateRandomString(); 
		accountNumber = Utility.GenerateRandomString15Digit();
		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com"; 
		String accountType = "Corporate/Member Accounts"; 
		String address1 = "test1"; 
		String address2 = "test2";
		String address3 = "test3"; 
		String city = "New york";
		String paymentType = "MC"; 
		//paymentType = "Cash";
		String cardNumber = ""; 
		String nameOnCard = guestName; 
		String cardExpDate = ""; 
		cardNumber = "5454545454545454";
		cardExpDate = "12/23";
		String marketSegment = "GDS"; 
		String referral = "Other";
		String postalCode = "12345"; 
		String isGuesProfile = "No";
		String taxExapmt = "--YES--";
		String source = "innCenter";
		String roomNumber = "";
		String maxAdults = "1";
		String maxChildren = "0"; 
		String ratePlanName = "Rack Rate123"; 
		String roomClassName = "BulkAction123"; 
		String roomClassAbbreviation = "BA123"; 
		String country = "United States"; 
		String state = "Alaska";
		String getReservationPhoneNumber = "";
		String TripSummaryRoomCharges = "";
		String TripSummaryTaxandServices = "";
		String IsTask = "Yes";
		String TaskCategory = "Front Desk";
		String TaskType = "Internal";	
		String TaskDetails = "Task Details";
		String TaskRemarks = "Task Remarks";	
		String TaskDueon = ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"); 
		String TaskAssignee = "Auto";
		String TaskStatus = "To Do";
		String taskStatusDone = "Done";
		String taskStatusCancelled = "Cancelled";

		//reports params
		String guestStatement = "Guest Statement"; 
		String guestRegistration = "Guest Registration"; 
		String listReport = "List Report"; 
		String mailingDetails = "Mailing Details"; 
		String detailedReservationList = "Detailed Reservation List"; 
		String reportSource = "data"; 
		String reportId = "Object1"; 
		String listReportHeading = "Reservation List With Total Revenue"; 
		String mailingDetailsHeading = "Reservation Mailing List"; 
		String detailedReservationListHeading = "Detailed Reservation List"; 
		String reportType = null;
		String reportHeading = null;
		String folioName = null;
		String fileName = null;
		String lines[] = null;
		String expectedFileName = null;
		String guestFolio = "Guest Folio";
		String property = "AutoRates";
		String clientName = "Client Type";
		String taxExemptId = "123";
		if(testCases.equalsIgnoreCase("VerifyReportsPrintedAsPDF")) {
			clientName = "AutoRates"; 			
		}
			
		String lastFourDigitsOfCreditCard = "";
		if(cardNumber.length() > 4) {
			lastFourDigitsOfCreditCard = cardNumber.substring(cardNumber.length()-4);
		}
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginRateV2(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
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

		if(testCases.equalsIgnoreCase("VerifyReportsPrintedAsPDF")){
				// Get Time Zone
			try {
				testSteps.add("===== GET PROPERTY NAME =====");
				app_logs.info("===== GET PROPERTY NAME =====");			
				property = homePage.getReportsProperty(driver, testSteps);
				
				testSteps.add("===== GET CLIENT NAME =====");
				app_logs.info("===== GET CLIENT NAME =====");			
				
				navigation.Admin(driver);
				testSteps.add("Navigate to admin");
				app_logs.info("Navigate to admin");
				
				navigation.Clientinfo(driver);
				testSteps.add("Click on client info");
				app_logs.info("Click on client info");
				clientName = homePage.getClientName(driver, testSteps);
				
				navigation.Reservation_Backward(driver);
				testSteps.add("Backward to reservation");
				app_logs.info("Backward to reservation");
				
				testSteps.add("===== GET PROPERTY TIME ZONE =====");
				app_logs.info("===== GET PROPERTY TIME ZONE =====");

				navigation.Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				
				navigation.Properties(driver);
				testSteps.add("Navigat Properties");
				app_logs.info("Navigat Properties");
				
				navigation.open_Property(driver, testSteps, property);
				testSteps.add("Open Property : " + property);
				app_logs.info("Open Property : " + property);
				
				navigation.click_PropertyOptions(driver, testSteps);
				String tempTimeZone = navigation.get_Property_TimeZone(driver);
				app_logs.info("Time Zone " + tempTimeZone);
				navigation.Reservation_Backward(driver);
				testSteps.add("Property Time Zone " + tempTimeZone);
				app_logs.info("Property Time Zone " + tempTimeZone);
				
				app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", tempTimeZone));
				testSteps.add("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", tempTimeZone));
				timeZone = Utility.getTimeZone(tempTimeZone);
				
				testSteps.add("Time Zone " + timeZone);
				app_logs.info("Time Zone " + timeZone);
				
				app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));
				testSteps.add("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get Time Zone", testName, "getTimeZone", driver);

				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get Time Zone", testName, "getTimeZone", driver);

				} else {
					Assert.assertTrue(false);
				}
			}


		}
		
	if(testCases.equalsIgnoreCase("VerifyBasicUIForHomePage")) {
			
		try {

			testSteps.add("===== " + "Verifying home page tabs are displaying or not".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page tabs are displaying or not".toUpperCase() + " =====");
			try {
				
				getTestSteps.clear();
				getTestSteps = homePage.verifyReservationTab(driver);
				testSteps.addAll(getTestSteps);

			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyTapeChartTab(driver);
				testSteps.addAll(getTestSteps);
		
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			testSteps.add("=== " + "Clicking on 'Tape chart' tab".toUpperCase() + " ===");
			app_logs.info("=== " + "Clicking on 'Tape chart' tab".toUpperCase() + " ===");
			try {
				navigation.TapeChart(driver);
				
				testSteps.add("Verified 'Tape Chart' tab is clickable");
				app_logs.info("Verified 'Tape Chart' tab is clickable");		
	
				navigation.cpReservationBackward(driver);
				testSteps.add("Click on reservation tab");
				app_logs.info("Click on reservation tab");
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			
			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyGuestHistoryTab(driver);
				testSteps.addAll(getTestSteps);
				
				navigation.GuestHistory(driver);
				testSteps.add("Verified 'Guest History' tab is clickable");
				app_logs.info("Verified 'Guest History' tab is clickable");		
	
				navigation.cpReservationBackward(driver);
				testSteps.add("Click on reservation tab");
				app_logs.info("Click on reservation tab");
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			

			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyGroupsTab(driver);
				testSteps.addAll(getTestSteps);
				
				navigation.Groups(driver);
				testSteps.add("Verified 'Groups' tab is clickable");
				app_logs.info("Verified 'Groups' tab is clickable");		
	
				navigation.Reservation_Backward(driver);
				testSteps.add("Click on reservation tab");
				app_logs.info("Click on reservation tab");
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			try {
				
				getTestSteps.clear();
				getTestSteps = homePage.verifyNewReservationTab(driver);
				testSteps.addAll(getTestSteps);	
	
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened page");
				app_logs.info("Closed opened page");		
				
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify new reservation button is displaying or not", "VerifyNewReservationButton", "VerifyNewReservationButton", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify new reservation button is displaying or not", "VerifyNewReservationButton", "VerifyNewReservationButton", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("===== " + "Verifying home page search boxes".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page search boxes".toUpperCase() + " =====");

			try {
				
				getTestSteps.clear();
				getTestSteps = homePage.verifyGuestNameinput(driver);
				testSteps.addAll(getTestSteps);
				
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			try {
				
			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationNumberinput(driver);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			try {

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationSearchIcon(driver);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyAdvanceButton(driver);
				testSteps.addAll(getTestSteps);
		
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page search options is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify home page search options is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {

			testSteps.add("===== " + "Verifying home page dashboard".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page dashboard".toUpperCase() + " =====");

			try {
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "In House", false);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			try {
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "All Arrivals", false);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			
			try {
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "All Departures", false);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			try {

			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "Unassigned", false);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			
			try {

			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "New Reservations", false);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page dashboard is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify home page dashboard is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		//Before Calendar date is default

		try {
		
			testSteps.add("===== " + "Verified that dashboard calendar has current date as default date".toUpperCase() + " =====");
			app_logs.info("===== " + "Verified that dashboard calendar has current date as default date".toUpperCase() + " =====");
			String getCurrentDate = Utility.getCurrentDate(defaultDateFormat, timeZone);
			try {
					
				getTestSteps.clear();
				getTestSteps = homePage.verifyCalendar(driver, getCurrentDate);
				testSteps.addAll(getTestSteps);
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}


			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyMonthsFromDashboardCalendar(driver);
				testSteps.addAll(getTestSteps);
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
	

						
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page calnedar date", testName, "VerifyCalendarTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page calnedar date", testName, "VerifyCalendarTab", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {

			testSteps.add("===== " + "Verifying home page print icon".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page print icon".toUpperCase() + " =====");

			try {
				getTestSteps.clear();
				getTestSteps = homePage.verifyPrintIcon(driver);
				testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page dashboard is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify home page dashboard is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("===== " + "Verifying home page go top icon".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page go top icon".toUpperCase() + " =====");

			try {
			getTestSteps.clear();
			getTestSteps = homePage.verifyGoTopIcon(driver);
			testSteps.addAll(getTestSteps);
			
			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page go top icon", "VerifyHomePageGoTopIcon", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify home page go top icon", "VerifyHomePageGoTopIcon", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("===== " + "Verifying home page reservation tabel columns is displaying".toUpperCase() + " =====");
			app_logs.info("===== " + "Verifying home page reservation tabel columns is displaying".toUpperCase() + " =====");

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Property");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Guest Name");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Account Name");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Res#");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Adults");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Child");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Status");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Room");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Arrive");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Depart");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyReservationCloumns(driver, "Nights");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyTaskCloumns(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyRecordFoundLabel(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.verifyItemsPerPage(driver);
			testSteps.addAll(getTestSteps);
						
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page record found label is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify home page record found label is displaying", "VerifySearchOption", "VerifySearchOption", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(testCases.equalsIgnoreCase("VerifyPropertyColumnForReservations")) {

		try {

			testSteps.add("===== " + "Verifying upon clicking on column 'Property' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Property' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

			property = homePage.getReportsProperty(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = homePage.verifyPropertyColumn(driver, property);
			testSteps.addAll(getTestSteps);
					
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on property column", "VerifyPropertyColumnSort", "VerifyPropertyColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on property column", "VerifyPropertyColumnSort", "VerifyPropertyColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnGuestName")) {

		try {

			testSteps.add("===== " + "Verifying upon clicking on column 'Guest Name' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Guest Name' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Guest Name");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Guest Name column", "VerifyGuestNameColumnSort", "VerifyGuestNameColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Guest Name column", "VerifyGuestNameColumnSort", "VerifyGuestNameColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnAccountName")) {

		try {

			testSteps.add("===== " + "Verifying upon clicking on column 'Account Name' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Account Name' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Account Name");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Account Name column", "VerifyAccountNameColumnSort", "VerifyAccountNameColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Account Name column", "VerifyAccountNameColumnSort", "VerifyAccountNameColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnReservationNumber")) {

		try {

			testSteps.add("===== " + "Verifying upon clicking on column 'Res#' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Res#' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Res#");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Res# column", "VerifyRes#ColumnSort", "VerifyRes#ColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Res# column", "VerifyRes#ColumnSort", "VerifyRes#ColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	
	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnAdults")) {

		try {

			testSteps.add("===== " + "Verifying upon clicking on column 'Adults' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Adults' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Adults");
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Adults column", "VerifyAdultsColumnSort", "VerifyAdultsColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Adults column", "VerifyAdultsColumnSort", "VerifyAdultsColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnChild")) {

	try {

		testSteps.add("===== " + "Verifying upon clicking on column 'Child' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
		app_logs.info("===== " + "Verifying upon clicking on column 'Child' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");

		getTestSteps.clear();
		getTestSteps = homePage.verifyCloumnSorted(driver, "Child");
		testSteps.addAll(getTestSteps);

	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to verify reservation list is sorted based on Child column", "VerifyChildColumnSort", "VerifyChildColumnSort", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.updateReport(e, "Failed to verify reservation list is sorted based on Child column", "VerifyChildColumnSort", "VerifyChildColumnSort", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
}


	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnStatus")) {
	
		try {
	
			testSteps.add("===== " + "Verifying upon clicking on column 'Status' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Status' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
	
			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Status");
			testSteps.addAll(getTestSteps);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Status column", "VerifyStatusColumnSort", "VerifyStatusColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Status column", "VerifyStatusColumnSort", "VerifyStatusColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}


	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnRoom")) {
	
		try {
	
			testSteps.add("===== " + "Verifying upon clicking on column 'Room' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Room' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
	
			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Room");
			testSteps.addAll(getTestSteps);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Room column", "VerifyRoomColumnSort", "VerifyRoomColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Room column", "VerifyRoomColumnSort", "VerifyRoomColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnArrive")) {
	
		try {
	
			testSteps.add("===== " + "Verifying upon clicking on column 'Arrive' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Arrive' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
	
			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Arrive");
			testSteps.addAll(getTestSteps);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Arrive column", "VerifyArriveColumnSort", "VerifyArriveColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Arrive column", "VerifyArriveColumnSort", "VerifyArriveColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnDepart")) {
		
		try {
	
			testSteps.add("===== " + "Verifying upon clicking on column 'Depart' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Depart' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
	
			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Depart");
			testSteps.addAll(getTestSteps);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Depart column", "VerifyDepartColumnSort", "VerifyDepartColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Depart column", "VerifyDepartColumnSort", "VerifyDepartColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	if(testCases.equalsIgnoreCase("VerifyReservatonsSortedBasedOnNights")) {
		
		try {
	
			testSteps.add("===== " + "Verifying upon clicking on column 'Nights' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
			app_logs.info("===== " + "Verifying upon clicking on column 'Nights' data shown in sorted manner corresponding to the column".toUpperCase() +  " =====");
	
			getTestSteps.clear();
			getTestSteps = homePage.verifyCloumnSorted(driver, "Nights");
			testSteps.addAll(getTestSteps);
	
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Nights column", "VerifyNightsColumnSort", "VerifyNightsColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify reservation list is sorted based on Nights column", "VerifyDepartColumnSort", "VerifyDepartColumnSort", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

		//Before reservation creation All Stats
		int beforePendingDepartureCount = 0;
		int beforeAllArrivalCount = 0;
		int beforeNewReservationCount = 0;
		int beforeInHouseCount = 0;
		
		if(testCases.equalsIgnoreCase("VerifyQuickStatWithAssignedReservation")) {

			try {
				
				testSteps.add("===== " + "Verified that new reservation count is equal to total record found".toUpperCase() + " =====");
				app_logs.info("===== " + "Verified that new reservation count is equal to total record found".toUpperCase() + " =====");

				getTestSteps.clear();
				getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
				testSteps.addAll(getTestSteps);
							
				getTestSteps.clear();
				getTestSteps = homePage.clickDashBoardOption(driver, "New Reservations", true);
				testSteps.addAll(getTestSteps);
				
				int getReservationsCount = homePage.getDashBoardOptionValue(driver, "New Reservations");
				beforeNewReservationCount = getReservationsCount;
				testSteps.add("New Reservations count : " + getReservationsCount);
				app_logs.info("New Reservations count : " + getReservationsCount);
			
				int totalRecords = 0; 
				if(getReservationsCount > 0) {
					
				totalRecords = homePage.getTotalRecordFound(driver);
				testSteps.add("Total Records : " + totalRecords);
				app_logs.info("Total Records : " + totalRecords);

				assertEquals(getReservationsCount, totalRecords, "Failed : new reservation count didn't match wiht total records");
			
				testSteps.add("Verified that new reservation count is equal to total record found");
				app_logs.info("Verified that new reservation count is equal to total record found");			
				}else {
					testSteps.add("Total records lable is not showing because new reservation count is zero");
					app_logs.info("Total records lable is not showing because new reservation count is zero");
									
				}
				
				testSteps.add("===== " + "Verified that All Arrivals reservation count is equal to total record found".toUpperCase() + " =====");
				app_logs.info("===== " + "Verified that All Arrivals reservation count is equal to total record found".toUpperCase() + " =====");
							
				getTestSteps.clear();
				getTestSteps = homePage.clickDashBoardOption(driver, "All Arrivals", true);
				testSteps.addAll(getTestSteps);
					
				getReservationsCount = homePage.getDashBoardOptionValue(driver, "All Arrivals");
				beforeAllArrivalCount = getReservationsCount;
				testSteps.add("All Arrivals count : " + getReservationsCount);
				app_logs.info("All Arrivals count : " + getReservationsCount);
			
				if(getReservationsCount > 0) {
						
							
					totalRecords = homePage.getTotalRecordFound(driver);
					testSteps.add("Total Records : " + totalRecords);
					app_logs.info("Total Records : " + totalRecords);
				
					assertEquals(getReservationsCount, totalRecords, "Failed : All Arrivals count didn't match wiht total records");
					testSteps.add("Verified that All Arrivals count is equal to total record found");
					app_logs.info("Verified that All Arrivals count is equal to total record found");

				}else {
					testSteps.add("Total records lable is not showing because All Arrivals count is zero");
					app_logs.info("Total records lable is not showing because All Arrivals count is zero");
									
				}
				
				testSteps.add("===== " + "Verified that In House reservation count is equal to total record found".toUpperCase() + " =====");
				app_logs.info("===== " + "Verified that In House reservation count is equal to total record found".toUpperCase() + " =====");
							
				getTestSteps.clear();
				getTestSteps = homePage.clickDashBoardOption(driver, "In House", true);
				testSteps.addAll(getTestSteps);
					
				getReservationsCount = homePage.getDashBoardOptionValue(driver, "In House");
				beforeInHouseCount = getReservationsCount;
				testSteps.add("In House count : " + getReservationsCount);
				app_logs.info("In House count : " + getReservationsCount);
				if(getReservationsCount > 0) {
					
						
					totalRecords = homePage.getTotalRecordFound(driver);
					testSteps.add("Total Records : " + totalRecords);
					app_logs.info("Total Records : " + totalRecords);
				
					assertEquals(getReservationsCount, totalRecords, "Failed : In House count didn't match wiht total records");
					testSteps.add("Verified that In House count is equal to total record found");
					app_logs.info("Verified that In House count is equal to total record found");
				}else {
					testSteps.add("Total records lable is not showing because all In House count is zero");
					app_logs.info("Total records lable is not showing because all In House count is zero");
									
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}


			try {
			
				testSteps.add("===== " + "Verified that All Departures reservation count is equal to total record found".toUpperCase() + " =====");
				app_logs.info("===== " + "Verified that All Departures reservation count is equal to total record found".toUpperCase() + " =====");
	
				getTestSteps.clear();
				getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkOutDate);
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = homePage.clickDashBoardOption(driver, "All Departures", true);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = homePage.clickOnAllArivalsDropDown(driver, 3);
				testSteps.addAll(getTestSteps);

				int getAllArrivalsAndDepartures = homePage.getAllArivalsAndDepartureDropDown(driver, 2);
				testSteps.add("All Arrivals and Departures : " + getAllArrivalsAndDepartures);
				
				beforePendingDepartureCount = homePage.getPendingDepartures(driver);
				testSteps.add("Pending Departures : " + beforePendingDepartureCount);
				
				int getReservationsCount = homePage.getDashBoardOptionValue(driver, "All Departures");
				//beforePendingDepartureCount = getReservationsCount;
				testSteps.add("All Departures count : " + getReservationsCount);
				app_logs.info("All Departures count : " + getReservationsCount);
				if(getReservationsCount > 0) {
				
					int totalRecords = homePage.getTotalRecordFound(driver);
					testSteps.add("Total Records : " + totalRecords);
					app_logs.info("Total Records : " + totalRecords);
				
					assertEquals(getReservationsCount, totalRecords, "Failed : All Departures count didn't match wiht total records");
					testSteps.add("Verified that All Departures count is equal to total record found");
					app_logs.info("Verified that All Departures count is equal to total record found");
				}else {
					testSteps.add("Total records lable is not showing because all departure count is zero");
					app_logs.info("Total records lable is not showing because all departure count is zero");
				
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			

		}

		int beforeUnassignedResCount = 0;
		if(testCases.equalsIgnoreCase("VerifyQuickStatWithUnAssignedReservation")) {

			try {
			
				testSteps.add("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
				app_logs.info("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
							
				getTestSteps.clear();
				getTestSteps = homePage.clickDashBoardOption(driver, "Unassigned", true);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
				testSteps.addAll(getTestSteps);
									
				int getReservationsCount = homePage.getDashBoardOptionValue(driver, "Unassigned");
				beforeUnassignedResCount = getReservationsCount;
				testSteps.add("Unassigned count : " + getReservationsCount);
				app_logs.info("Unassigned count : " + getReservationsCount);
			
				if(getReservationsCount > 0) {
					int totalRecords = homePage.getTotalRecordFound(driver);
					testSteps.add("Total Records : " + totalRecords);
					app_logs.info("Total Records : " + totalRecords);
				
					assertEquals(getReservationsCount, totalRecords, "Failed : Unassigned count didn't match wiht total records");
					testSteps.add("Verified that unassigned count is equal to total record found");
					app_logs.info("Verified that unassigned count is equal to total record found");
					
				}else {
					testSteps.add("Total records lable is not showing because all unassigned count is zero");
					app_logs.info("Total records lable is not showing because all unassigned count is zero");
									
				}

				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify unassigned reservation count", testName, "VerifyUnassignedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify unassigned reservation count", testName, "VerifyUnassignedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			

		}
		
		String reservationNumber = "17850280";
		String reservationStatus = "";
		
	if(testCases.equalsIgnoreCase("VerifyQuickStatWithUnAssignedReservation")
		|| testCases.equalsIgnoreCase("TaskIconColor")
		|| testCases.equalsIgnoreCase("VerifyReportsPrintedAsPDF")
		|| testCases.equalsIgnoreCase("VerifyBasicSearchWithGuestNameAndResNumber")
		|| testCases.equalsIgnoreCase("VerifyQuickStatWithAssignedReservation")
		|| testCases.equalsIgnoreCase("VerifyAdvanceSearchUsingTaxExempt")
		){
			try {
					reservationPage.click_NewReservation(driver, testSteps);						
					testSteps.add("=================== CREATE RESERVATION ======================");
					app_logs.info("=================== CREATE RESERVATION ======================");
					reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"));
					reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
					reservationPage.enter_Adults(driver, testSteps, maxAdults);
					reservationPage.enter_Children(driver, testSteps, maxChildren);

					reservationPage.clickOnFindRooms(driver, testSteps);
					ratePlanName = homePage.getRatePlan(driver, testSteps, 1); 
					app_logs.info(ratePlanName);
					roomClassName = homePage.getRoomClass(driver, testSteps, ratePlanName, 1);
					app_logs.info(roomClassName);						
					String isAssign = "Yes";
					if(testCases.equalsIgnoreCase("VerifyQuickStatWithUnAssignedReservation")) {
						isAssign = "No";
					}
					reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					reservationPage.clickNext(driver, testSteps);									
					reservationPage.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,phoneNumber,phoneNumber,email,"","",address1,address2,address3,city,country,state,postalCode,isGuesProfile);
					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
					if(testCases.equalsIgnoreCase("VerifyAdvanceSearchUsingTaxExempt")) {
						reservationPage.createTaxExempt(driver, taxExemptId, testSteps);
					}

					reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);
					if(testCases.equalsIgnoreCase("TaskIconColor")) {
						getTestSteps.clear();
						getTestSteps = reservationPage.enterTask(driver, IsTask, TaskCategory, TaskType, TaskDetails, TaskRemarks, TaskDueon, TaskAssignee, TaskStatus);
						testSteps.addAll(getTestSteps);
					}
				
					reservationPage.clickBookNow(driver, testSteps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);
					reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
					getReservationPhoneNumber = reservationSearch.getReservationPhoneNumber(driver);
							
					TripSummaryRoomCharges = reservationPage.getRoomChargesInTripSummary(driver);
					app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
					testSteps.add("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					
					TripSummaryTaxandServices = reservationPage.getTaxandServicesInTripSummary(driver);
					app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					
					TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
					TripSummaryTaxandServices=TripSummaryTaxandServices.substring(1, TripSummaryTaxandServices.length());
					TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
					
					testSteps.add("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
			
		}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {
			
				testSteps.add("==========GET ROOMCLASS ABBREVIATION=========");
				app_logs.info("==========GET ROOMCLASS ABBREVIATION=========");
				navigation.Setup(driver);
				testSteps.add("Navigate Setup");
				navigation.RoomClass(driver);
				testSteps.add("Navigate RoomClass");
				roomClassAbbreviation = homePage.getRoomClassAbbreviation(driver, testSteps, roomClassName);
				app_logs.info("RoomClass Abbreviation:" + roomClassAbbreviation);
		
				navigation.cpReservation_Backward(driver);
				app_logs.info("Navigate back to reservation");
				testSteps.add("Navigate back to reservation");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbreviation", testName, "GetRoomClassAbbreviation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get room class abbreviation", testName, "GetRoomClassAbbreviation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
		}
	
	//After quick stat
	int afterDepartureCount = 0;
	int afterAllArrivalCount = 0;
	int afterNewReservationCount = 0;
	int afterInHouseCount = 0;

	if(testCases.equalsIgnoreCase("VerifyQuickStatWithAssignedReservation")) {
		

		
		try {
			
			testSteps.add("===== " + "Verifying that new reservation count is incremented after creating new reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Verifying that new reservation count is incremented after creating new reservation".toUpperCase() +" =====");
		
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "New Reservations", true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Previous Reservations count : " + beforeNewReservationCount);
			app_logs.info("Previous Reservations count : " + beforeNewReservationCount);
			
			afterNewReservationCount = homePage.getDashBoardOptionValue(driver, "New Reservations");
			testSteps.add("New Reservations count : " + afterNewReservationCount);
			app_logs.info("New Reservations count : " + afterNewReservationCount);
			
			assertTrue(afterNewReservationCount > beforeNewReservationCount, "Failed : New Reservation count didn't increased");
			testSteps.add("Verified that new reservation count is incremented");
			app_logs.info("Verified that new reservation count is incremented");
			
			getTestSteps.clear();
			getTestSteps = homePage.selectItemsPerPage(driver, "100");
			testSteps.addAll(getTestSteps);

			testSteps.add("===== " + "Verified that new reservation count is equal to total record found".toUpperCase() + " =====");
			app_logs.info("===== " + "Verified that new reservation count is equal to total record found".toUpperCase() + " =====");
						
			testSteps.add("New Reservations count : " + afterNewReservationCount);
			app_logs.info("New Reservations count : " + afterNewReservationCount);
				
			int totalRecords = homePage.getTotalRecordFound(driver);
			testSteps.add("Total Records : " + totalRecords);
			app_logs.info("Total Records : " + totalRecords);
		
			assertEquals(afterNewReservationCount, totalRecords, "Failed : New reservation count didn't match wiht total records");				
			testSteps.add("Verified that new reservation count is equal to total record found");
			app_logs.info("Verified that new reservation count is equal to total record found");
							
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page new reservation count is correct", testName, "VerifyNewReservationCount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify home page new reservation count is correct", testName, "VerifyNewReservationCount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("===== " + "Verifying that All Arrivals reservation count is incremented after creating new reservation for current date".toUpperCase() +" =====");
			app_logs.info("===== " + "Verifying that All Arrivals reservation count is incremented after creating new reservation for current date".toUpperCase() +" =====");
		
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "All Arrivals", true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("Previous All Arrivals Reservations count : " + beforeAllArrivalCount);
			app_logs.info("Previous All Arrivals Reservations count : " + beforeAllArrivalCount);
			afterAllArrivalCount = homePage.getDashBoardOptionValue(driver, "All Arrivals");
			
			assertTrue(afterAllArrivalCount > beforeAllArrivalCount, "Failed : All Arrivals Reservation count didn't increased");
			testSteps.add("Verified that All Arrivals reservation count is incremented");
			app_logs.info("Verified that All Arrivals reservation count is incremented");
							
			testSteps.add("===== " + "Verified that All Arrivals reservation count is equal to total record found".toUpperCase() + " =====");
			app_logs.info("===== " + "Verified that All Arrivals reservation count is equal to total record found".toUpperCase() + " =====");
						
			testSteps.add("All Arrivals count : " + afterAllArrivalCount);
			app_logs.info("All Arrivals count : " + afterAllArrivalCount);
		
			int totalRecords = homePage.getTotalRecordFound(driver);
			testSteps.add("Total Records : " + totalRecords);
			app_logs.info("Total Records : " + totalRecords);
		
			assertEquals(afterAllArrivalCount, totalRecords, "Failed : All Arrivals count didn't match wiht total records");
			testSteps.add("Verified that All Arrivals count is equal to total record found");
			app_logs.info("Verified that All Arrivals count is equal to total record found");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify All Arrivals reservation count", testName, "VerifyAllArrivalsReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify All Arrivals reservation count", testName, "VerifyAllArrivalsReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("===== " + "Verifying that In House reservation count is incremented after creating and checking in new reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Verifying that In House reservation count is incremented after creating and checking in new reservation".toUpperCase() +" =====");
		
			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);
			
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			testSteps.add("Searched and opened reservation with number : "  + reservationNumber);
			app_logs.info("Searched and opened reservation with number : "  + reservationNumber);

			testSteps.add("===== " + "Checking in reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Checking in reservation".toUpperCase() +" =====");
		
			reservationPage.Click_CheckInButton(driver, testSteps);
			reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = homePage.clickConfirmChekInButton(driver);
			testSteps.addAll(getTestSteps);

			try {
				getTestSteps.clear();
				getTestSteps = homePage.clickPayButton(driver);
				testSteps.addAll(getTestSteps);

			}catch(Exception e) {
			}

			try {
				getTestSteps.clear();
				getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
				testSteps.addAll(getTestSteps);
			}
			catch(Exception e) 
			{
				
			}
			testSteps.add("Reservation checked in");
			app_logs.info("Reservation checked in");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			
			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);
			
			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "In House", true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
			testSteps.addAll(getTestSteps);

			testSteps.add("Previous In House Reservations count : " + beforeInHouseCount);
			app_logs.info("Previous In House Reservations count : " + beforeInHouseCount);
			
			afterInHouseCount = homePage.getDashBoardOptionValue(driver, "In House");
			testSteps.add("New In House Reservations count : " + afterInHouseCount);
			app_logs.info("New In House Reservations count : " + afterInHouseCount);
			
			assertTrue(afterInHouseCount > beforeInHouseCount, "Failed : In House Reservation count didn't increased");
			testSteps.add("Verified that In House reservation count is incremented");
			app_logs.info("Verified that In House reservation count is incremented");
			
		
			testSteps.add("===== " + "Verified that In House reservation count is equal to total record found".toUpperCase() + " =====");
			app_logs.info("===== " + "Verified that In House reservation count is equal to total record found".toUpperCase() + " =====");
						
			testSteps.add("In House count : " + afterInHouseCount);
			app_logs.info("In House count : " + afterInHouseCount);
		
			int totalRecords = homePage.getTotalRecordFound(driver);
			testSteps.add("Total Records : " + totalRecords);
			app_logs.info("Total Records : " + totalRecords);
		
			assertEquals(afterInHouseCount, totalRecords, "Failed : In House count didn't match wiht total records");
			testSteps.add("Verified that In House count is equal to total record found");
			app_logs.info("Verified that In House count is equal to total record found");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify In House reservation count", testName, "VerifyInHouseReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify In House reservation count", testName, "VerifyInHouseReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		try {
			
			testSteps.add("===== " + "Verifying that Pending Departures reservation count is incremented after checking in reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Verifying that Pending Departures reservation count is incremented after checking in reservation".toUpperCase() +" =====");
		
			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);
			
			getTestSteps.clear();
			getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkOutDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "All Departures", true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.clickOnAllArivalsDropDown(driver, 3);
			testSteps.addAll(getTestSteps);

			int getAllArrivalsAndDepartures = homePage.getAllArivalsAndDepartureDropDown(driver, 2);
			testSteps.add("All Arrivals and Departures : " + getAllArrivalsAndDepartures);

			testSteps.add("Previous Pending Departures : " + beforePendingDepartureCount);
			app_logs.info("Previous Pending Departures : " + beforePendingDepartureCount);
						
			int getPendingDeparturesValue = homePage.getPendingDepartures(driver);					
			testSteps.add("Pending Departures Reservations count after checkin : " + getPendingDeparturesValue);
			app_logs.info("Pending Departures Reservations count after checkin : " + getPendingDeparturesValue);
			
			
			assertTrue(getPendingDeparturesValue > beforePendingDepartureCount, "Failed : Pending departures didn't match");
			testSteps.add("Verified that Pending Departures reservation count is incremented");
			app_logs.info("Verified that Pending Departures reservation count is incremented");

			
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			testSteps.add("Searched and opened reservation with number : "  + reservationNumber);
			app_logs.info("Searched and opened reservation with number : "  + reservationNumber);

			testSteps.add("===== " + "Checking out reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Checking out reservation".toUpperCase() +" =====");
		
			reservationPage.clickCheckOutButton(driver, testSteps);
			reservationPage.generatGuestReportToggle(driver, testSteps, "No");
			reservationPage.proceedToCheckOutPayment(driver, testSteps);
			try{
				  getTestSteps.clear(); getTestSteps = homePage.clickPayButton(driver);
				  testSteps.addAll(getTestSteps);
				
			}
			catch(Exception e) {
				  
			 }
			try{
					
				getTestSteps.clear(); 
				getTestSteps =
				homePage.clickCloseCheckoutSuccessfullPopup(driver);
				testSteps.addAll(getTestSteps);
			}
			catch(Exception e) {
				  
			}
			 
			testSteps.add("Reservation checked out");
			app_logs.info("Reservation checked out");
			String loading = "(//div[@class='ir-loader-in'])";
			Wait.explicit_wait_absenceofelement(loading, driver);

			testSteps.add("<b>==========Start Verifying Roll Back Button ==========</b>");
			reservationPage.verifyRollBackButton(driver, testSteps);
			testSteps.add("<b>==========Start Verifying DEPARTED Status==========</b>");
			reservationPage.verifyReservationStatusStatus(driver, testSteps, "DEPARTED");
							
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			
			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);

			testSteps.add("===== " + "Verifying that Pending Departures reservation count is decreased after checking out reservation".toUpperCase() +" =====");
			app_logs.info("===== " + "Verifying that Pending Departures reservation count is decreased after checking out reservation".toUpperCase() +" =====");
		
			getTestSteps.clear();
			getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkOutDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.clickDashBoardOption(driver, "All Departures", true);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = homePage.clickOnAllArivalsDropDown(driver, 3);
			testSteps.addAll(getTestSteps);

			getAllArrivalsAndDepartures = homePage.getAllArivalsAndDepartureDropDown(driver, 2);
			testSteps.add("All Arrivals and Departures : " + getAllArrivalsAndDepartures);
			
			testSteps.add("Previous Pending Departures Reservations count : " + beforePendingDepartureCount);
			app_logs.info("Previous Pending Departures Reservations count : " + beforePendingDepartureCount);
			
			afterDepartureCount = homePage.getPendingDepartures(driver);
			testSteps.add("New Pending Departures after checkout : " + getPendingDeparturesValue);
			app_logs.info("New Pending Departures after checkout : " + getPendingDeparturesValue);
						
			assertTrue(afterDepartureCount < getPendingDeparturesValue, "Failed : Pending departures didn't match");
			testSteps.add("Verified that Pending Departures reservation count is decreased");
			app_logs.info("Verified that Pending Departures reservation count is decreased");
			
			testSteps.add("Previous All Departures Reservations count : " + beforePendingDepartureCount);
			app_logs.info("Previous All Departures Reservations count : " + beforePendingDepartureCount);
			
			int getReservationsCount = homePage.getDashBoardOptionValue(driver, "All Departures");
			testSteps.add("New All Departures Reservations count : " + getReservationsCount);
			app_logs.info("New All Departures Reservations count : " + getReservationsCount);
			
			assertTrue(getReservationsCount > beforePendingDepartureCount, "Failed : All Departures Reservation count didn't increased");
			testSteps.add("Verified that All Departures reservation count is incremented");
			app_logs.info("Verified that All Departures reservation count is incremented");
							
			testSteps.add("===== " + "Verified that All Departures reservation count is equal to total record found".toUpperCase() + " =====");
			app_logs.info("===== " + "Verified that All Departures reservation count is equal to total record found".toUpperCase() + " =====");
						
			testSteps.add("All Departures count : " + getReservationsCount);
			app_logs.info("All Departures count : " + getReservationsCount);
		
			int totalRecords = homePage.getTotalRecordFound(driver);
			testSteps.add("Total Records : " + totalRecords);
			app_logs.info("Total Records : " + totalRecords);
		
			assertEquals(getReservationsCount, totalRecords, "Failed : All Departures count didn't match with total records");
			testSteps.add("Verified that All Departures count is equal to total record found");
			app_logs.info("Verified that All Departures count is equal to total record found");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify All Departures reservation count", testName, "VerifyAllDeparturesReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		

		
	}

//First Report
	if(testCases.equalsIgnoreCase("VerifyReportsPrintedAsPDF")) {
		int i = 0;
		
		try {
			
			app_logs.info("==========SEARCH RESERVATION==========");
			testSteps.add("==========SEARCH RESERVATION==========");
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, false);

			testSteps.add("Reservation Successfully Searched : " + reservationNumber);
			app_logs.info("Reservation Successfully Searched : " + reservationNumber);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to search reservation", testName, "SearchReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to search reservation", testName, "SearchReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		reportType = listReport;
		reportHeading = listReportHeading;
		try {
			testSteps.add(
					"===================== DOWNLOAD <b>" + reportType.toUpperCase() + "</b> =====================");
			app_logs.info(
					"===================== DOWNLOAD <b>" + reportType.toUpperCase() + "</b> =====================");
			reservationPage.clickOnPrintIcon(driver);
			testSteps.add("Click Print Icon");
			app_logs.info("Click Print Icon");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectReportTypeButton(driver, reportType, "radio", true, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationPage.clickPrintReservationReports(driver);
			testSteps.add("Click Print Reservation Report");
			app_logs.info("Click Print Reservation Report");
			reservationPage.waitTillNumberOfTabsExist(driver, 2);
			testSteps.add("New tab opened");
			app_logs.info("New tab opened");
			Utility.switchTab(driver, 1);
			testSteps.add("Switch tab");
			app_logs.info("Switch tab");
			folioName = "Folio Name : " + guestFolio;
			if (reservationPage.reportDisplayed(driver, reportId, testSteps)) {
				fileName = reservationPage.downloadReport(driver, reportSource, reportId);
				testSteps.add("Download Report");
				app_logs.info("Download Report");
				testSteps.add("=====================VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				testSteps.add("Actual File Name '" + fileName + "'");
				app_logs.info("Actual File Name '" + fileName + "'");
				expectedFileName = clientName + "_" + reportHeading.replace(" ", "") + "_"
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				testSteps.add("Expect File Name '" + expectedFileName + "'");
				app_logs.info("Expect File Name '" + expectedFileName + "'");
				Assert.assertTrue(fileName.contains(expectedFileName),
						"Failed : '" + reportType + "' File Name missmatched");
				testSteps.add("Verified Report Name");
				app_logs.info("Verified Report Name");

				lines = Utility.checkPDFArray(fileName);
				System.out.println("line size : " + lines.length);
				String getReportsData = "";
				i = 0;
				for (String line : lines) {
					Utility.app_logs.info("line : " + i + " '" + line + "'");
					i++;
					getReportsData = getReportsData + line;
					
				}				
				app_logs.info("getReportsData : " +getReportsData);
				reservationPage.verifyDataExistInReport(driver, lines,
						clientName.replaceAll("_", " ") + " " + reportHeading, true);
				testSteps.add("Verified Report Heading '" + reportHeading + "'");
				app_logs.info("Verified Report Heading '" + reportHeading + "'");
				Utility.closeTabsExcept(driver, 1);
				testSteps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");

				testSteps.add("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationNumber);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				
				String tempGuestName = guestFirstName;
				if(guestFirstName.length() > 14) {
					tempGuestName = guestFirstName.substring(0, 12);
				}
				app_logs.info("tempGuestName : "  +tempGuestName);
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, tempGuestName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestLastName);
				
				testSteps.addAll(getTestSteps);
				testSteps.add("===== " + "Verifying report contains room class '".toUpperCase() + roomClassAbbreviation + "' =====");
				app_logs.info("===== " + "Verifying report contains room class '".toUpperCase() + roomClassAbbreviation + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, roomClassAbbreviation);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains reservation Status '".toUpperCase() + reservationStatus + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation Status '".toUpperCase() + reservationStatus + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationStatus);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "MMM dd, yyyy"));
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "MMM dd, yyyy"));
				testSteps.addAll(getTestSteps);
				
			} else {
				testSteps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		//Second Report
		reportType = guestStatement;
		reportHeading = guestStatement;
		try {
			testSteps.add("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			app_logs.info("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			reservationPage.clickOnPrintIcon(driver);
			testSteps.add("Click Print Icon");
			app_logs.info("Click Print Icon");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectReportTypeButton(driver, reportType, "radio", true, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationPage.clickPrintReservationReports(driver);
			testSteps.add("Click Print Reservation Report");
			app_logs.info("Click Print Reservation Report");
			reservationPage.waitTillNumberOfTabsExist(driver, 2);
			testSteps.add("New tab opened");
			app_logs.info("New tab opened");
			Utility.switchTab(driver, 1);
			testSteps.add("Switch tab");
			app_logs.info("Switch tab");
			folioName = "Folio Name : " + guestFolio;
			if (reservationPage.reportDisplayed(driver, reportId, testSteps)) {
				fileName = reservationPage.downloadReport(driver, reportSource, reportId);
				testSteps.add("Download Report");
				app_logs.info("Download Report");
				testSteps.add("=====================VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				testSteps.add("Actual File Name '" + fileName + "'");
				app_logs.info("Actual File Name '" + fileName + "'");
				expectedFileName = clientName + "_" + reportHeading.replace(" ", "") +"WithTaxes_"
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				testSteps.add("Expect File Name '" + expectedFileName + "'");
				app_logs.info("Expect File Name '" + expectedFileName + "'");
				Assert.assertTrue(fileName.contains(expectedFileName),
						"Failed : '" + reportType + "' File Name missmatched");
				testSteps.add("Verified Report Name");
				app_logs.info("Verified Report Name");

				lines = Utility.checkPDFArray(fileName);
				System.out.println("line size : " + lines.length);
				String getReportsData = "";
				i = 0;
				for (String line : lines) {
					Utility.app_logs.info("line : " + i + " '" + line + "'");
					getReportsData = getReportsData + line;
					i++;
				}
				app_logs.info("getReportsData : "+ getReportsData);
				reservationPage.verifyDataExistInReport(driver, lines,
						 property + " " + reportHeading.replaceAll(" ", "  "), false);
				testSteps.add("Verified Report Heading '" + reportHeading + "'");
				app_logs.info("Verified Report Heading '" + reportHeading + "'");
				Utility.closeTabsExcept(driver, 1);
				testSteps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");

				testSteps.add("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationNumber);
				testSteps.addAll(getTestSteps);


				testSteps.add("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestFirstName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestLastName);

				testSteps.add("===== " + "Verifying report contains room class '".toUpperCase() + roomClassName + "' =====");
				app_logs.info("===== " + "Verifying report contains room class '".toUpperCase() + roomClassName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, roomClassName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "EEE MMM dd,yyyy"));
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "EEE MMM dd,yyyy"));
				testSteps.addAll(getTestSteps);
				
				testSteps.add("===== " + "Verifying report contains phone number '".toUpperCase() + getReservationPhoneNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains phone number '".toUpperCase() + getReservationPhoneNumber + "' =====");
				String[] tempNumberArr = getReservationPhoneNumber.split("-");
				app_logs.info("tempNumberArr : " +tempNumberArr.length);
				for(String s : tempNumberArr) {
					app_logs.info(s);
					
				}
				String tempPhoneNumber = tempNumberArr[0] + " " +  tempNumberArr[1] + "-" + tempNumberArr[2];
				app_logs.warn("tempPhoneNumber : "+ tempPhoneNumber);
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, tempPhoneNumber);
				testSteps.addAll(getTestSteps);
	
				testSteps.add("===== " + "Verifying report contains email '".toUpperCase() + email + "' =====");
				app_logs.info("===== " + "Verifying report contains email '".toUpperCase() + email + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, email);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains city '".toUpperCase() + city + "' =====");
				app_logs.info("===== " + "Verifying report contains city '".toUpperCase() + city + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, city);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address1 '".toUpperCase() + address1 + "' =====");
				app_logs.info("===== " + "Verifying report contains address1 '".toUpperCase() + address1 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address1);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address2 '".toUpperCase() + address2 + "' =====");
				app_logs.info("===== " + "Verifying report contains address2 '".toUpperCase() + address2 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address2);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address3 '".toUpperCase() + address3 + "' =====");
				app_logs.info("===== " + "Verifying report contains address3 '".toUpperCase() + address3 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address3);
				testSteps.addAll(getTestSteps);

			} else {
				testSteps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Third Report
		reportType = guestRegistration;
		reportHeading = guestRegistration;
		try {
			testSteps.add("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			app_logs.info("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			reservationPage.clickOnPrintIcon(driver);
			testSteps.add("Click Print Icon");
			app_logs.info("Click Print Icon");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectReportTypeButton(driver, reportType, "radio", true, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationPage.clickPrintReservationReports(driver);
			testSteps.add("Click Print Reservation Report");
			app_logs.info("Click Print Reservation Report");
			reservationPage.waitTillNumberOfTabsExist(driver, 2);
			testSteps.add("New tab opened");
			app_logs.info("New tab opened");
			Utility.switchTab(driver, 1);
			testSteps.add("Switch tab");
			app_logs.info("Switch tab");
			folioName = "Folio Name : " + guestFolio;
			if (reservationPage.reportDisplayed(driver, reportId, testSteps)) {
				fileName = reservationPage.downloadReport(driver, reportSource, reportId);
				testSteps.add("Download Report");
				app_logs.info("Download Report");
				testSteps.add("=====================VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				testSteps.add("Actual File Name '" + fileName + "'");
				app_logs.info("Actual File Name '" + fileName + "'");
				expectedFileName = clientName + "_" + reportHeading.replace(" ", "") + "FormWithTaxes_"
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				testSteps.add("Expect File Name '" + expectedFileName + "'");
				app_logs.info("Expect File Name '" + expectedFileName + "'");
				Assert.assertTrue(fileName.contains(expectedFileName),
						"Failed : '" + reportType + "' File Name missmatched");
				testSteps.add("Verified Report Name");
				app_logs.info("Verified Report Name");

				lines = Utility.checkPDFArray(fileName);
				System.out.println("line size : " + lines.length);
				i = 0;
				String  getReportsData = "";
				for (String line : lines) {
					Utility.app_logs.info("line : " + i + " '" + line + "'");
					getReportsData = getReportsData + line;
					i++;
				}
				app_logs.info("getReportsData : "+ getReportsData);

				reservationPage.verifyDataExistInReport(driver, lines,
						 property + " " + reportHeading, false);
				testSteps.add("Verified Report Heading '" + reportHeading + "'");
				app_logs.info("Verified Report Heading '" + reportHeading + "'");
				Utility.closeTabsExcept(driver, 1);
				testSteps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");

				testSteps.add("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationNumber);
				testSteps.addAll(getTestSteps);


				testSteps.add("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestFirstName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestLastName);
				

				testSteps.add("===== " + "Verifying report contains room class '".toUpperCase() + roomClassName + "' =====");
				app_logs.info("===== " + "Verifying report contains room class '".toUpperCase() + roomClassName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, roomClassName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "EEE MMM dd,yyyy"));
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "EEE MMM dd,yyyy"));
				testSteps.addAll(getTestSteps);
	
			} else {
				testSteps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Forth Report
		reportType = mailingDetails;
		reportHeading = mailingDetailsHeading;
		try {
			testSteps.add("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			app_logs.info("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			reservationPage.clickOnPrintIcon(driver);
			testSteps.add("Click Print Icon");
			app_logs.info("Click Print Icon");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectReportTypeButton(driver, reportType, "radio", true, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationPage.clickPrintReservationReports(driver);
			testSteps.add("Click Print Reservation Report");
			app_logs.info("Click Print Reservation Report");
			reservationPage.waitTillNumberOfTabsExist(driver, 2);
			testSteps.add("New tab opened");
			app_logs.info("New tab opened");
			Utility.switchTab(driver, 1);
			testSteps.add("Switch tab");
			app_logs.info("Switch tab");
			folioName = "Folio Name : " + guestFolio;
			if (reservationPage.reportDisplayed(driver, reportId, testSteps)) {
				fileName = reservationPage.downloadReport(driver, reportSource, reportId);
				testSteps.add("Download Report");
				app_logs.info("Download Report");
				testSteps.add("=====================VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				testSteps.add("Actual File Name '" + fileName + "'");
				app_logs.info("Actual File Name '" + fileName + "'");
				expectedFileName = clientName + "_" + reportHeading.replace(" ", "") + "_"
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				testSteps.add("Expect File Name '" + expectedFileName + "'");
				app_logs.info("Expect File Name '" + expectedFileName + "'");
				Assert.assertTrue(fileName.contains(expectedFileName),
						"Failed : '" + reportType + "' File Name missmatched");
				testSteps.add("Verified Report Name");
				app_logs.info("Verified Report Name");

				lines = Utility.checkPDFArray(fileName);
				System.out.println("line size : " + lines.length);
				i = 0;
				String getReportsData = "";
				for (String line : lines) {
					Utility.app_logs.info("line : " + i + " '" + line + "'");
					getReportsData = getReportsData +line;
					i++;
				}
				app_logs.info("getReportsData : "+ getReportsData);
				reservationPage.verifyDataExistInReport(driver, lines,
						 reportHeading + clientName.replaceAll("_", " "), true);
				testSteps.add("Verified Report Heading '" + reportHeading + "'");
				app_logs.info("Verified Report Heading '" + reportHeading + "'");
				Utility.closeTabsExcept(driver, 1);
				testSteps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");

				testSteps.add("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationNumber);
				testSteps.addAll(getTestSteps);


				testSteps.add("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestFirstName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestLastName + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestLastName);
				
				testSteps.add("===== " + "Verifying report contains reservation email '".toUpperCase() + email + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation email '".toUpperCase() + email + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, email);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains phoneNumber '".toUpperCase() + getReservationPhoneNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains phoneNumber '".toUpperCase() + getReservationPhoneNumber + "' =====");
				String[] tempNumberArr = getReservationPhoneNumber.split("-");
				app_logs.info("tempNumberArr : " +tempNumberArr.length);
				for(String s : tempNumberArr) {
					app_logs.info(s);
					
				}
				String tempPhoneNumber = tempNumberArr[0] + " " +  tempNumberArr[1] + "-" + tempNumberArr[2];
				app_logs.warn("tempPhoneNumber : "+ tempPhoneNumber);

				//reformat phone
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, tempPhoneNumber);
				testSteps.addAll(getTestSteps);

			} else {
				testSteps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		//Fifth Report
		reportType = detailedReservationList;
		reportHeading = detailedReservationListHeading;
		try {
			testSteps.add("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			app_logs.info("===================== DOWNLOAD <b>" + reportType.toUpperCase()
					+ "</b> REPORT =====================");
			reservationPage.clickOnPrintIcon(driver);
			testSteps.add("Click Print Icon");
			app_logs.info("Click Print Icon");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectReportTypeButton(driver, reportType, "radio", true, getTestSteps);
			testSteps.addAll(getTestSteps);
			reservationPage.clickPrintReservationReports(driver);
			testSteps.add("Click Print Reservation Report");
			app_logs.info("Click Print Reservation Report");
			reservationPage.waitTillNumberOfTabsExist(driver, 2);
			testSteps.add("New tab opened");
			app_logs.info("New tab opened");
			Utility.switchTab(driver, 1);
			testSteps.add("Switch tab");
			app_logs.info("Switch tab");
			folioName = "Folio Name : " + guestFolio;
			if (reservationPage.reportDisplayed(driver, reportId, testSteps)) {
				fileName = reservationPage.downloadReport(driver, reportSource, reportId);
				testSteps.add("Download Report");
				app_logs.info("Download Report");
				testSteps.add("=====================VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				app_logs.info("===================== VERIFY <b>" + reportType.toUpperCase()
						+ "</b> REPORT =====================");
				testSteps.add("Actual File Name '" + fileName + "'");
				app_logs.info("Actual File Name '" + fileName + "'");
				expectedFileName =  "ReportingService_" + reportHeading.replace(" ", "") + "_"
						+ Utility.getCurrentDate("yyyyMMdd", timeZone);
				testSteps.add("Expect File Name '" + expectedFileName + "'");
				app_logs.info("Expect File Name '" + expectedFileName + "'");
				Assert.assertTrue(fileName.contains(expectedFileName),
						"Failed : '" + reportType + "' File Name missmatched");
				testSteps.add("Verified Report Name");
				app_logs.info("Verified Report Name");

				lines = Utility.checkPDFArray(fileName);
				System.out.println("line size : " + lines.length);
				i = 0;
				String getReportsData = "";
				for (String line : lines) {
					Utility.app_logs.info("line : " + i + " '" + line + "'");
					getReportsData = getReportsData + line;
					i++;
				}
				app_logs.info("getReportsData : "+ getReportsData);

				reservationPage.verifyDataExistInReport(driver, lines,
						 reportHeading , false);
				testSteps.add("Verified Report Heading '" + reportHeading + "'");
				app_logs.info("Verified Report Heading '" + reportHeading + "'");
				Utility.closeTabsExcept(driver, 1);
				testSteps.add("Close report's tab and switch to first tab");
				app_logs.info("Close report's tab and switch to first tab");

				testSteps.add("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation number '".toUpperCase() + reservationNumber + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationNumber);
				testSteps.addAll(getTestSteps);


				testSteps.add("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				app_logs.info("===== " + "Verifying report contains guestFirstName '".toUpperCase() + guestFirstName + "' =====");
				
				String tempGuestName = guestFirstName;
				if(guestFirstName.length() > 14) {
					tempGuestName = guestFirstName.substring(0, 12);
				}
				app_logs.info("tempGuestName : "  +tempGuestName);
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, tempGuestName);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestFirstName.split(randomNumber)[0] + "' =====");
				app_logs.info("===== " + "Verifying report contains guestLastName '".toUpperCase() + guestFirstName.split(randomNumber)[0] + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, guestLastName);
				

				testSteps.add("===== " + "Verifying report contains room class abbreviation '".toUpperCase() + roomClassAbbreviation + "' =====");
				app_logs.info("===== " + "Verifying report contains room class abbreviation '".toUpperCase() + roomClassAbbreviation + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, roomClassAbbreviation);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains reservation Status '".toUpperCase() + reservationStatus + "' =====");
				app_logs.info("===== " + "Verifying report contains reservation Status '".toUpperCase() + reservationStatus + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, reservationStatus);
				testSteps.addAll(getTestSteps);


				testSteps.add("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkInDate '".toUpperCase() + checkInDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "MMM dd, yyyy"));
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				app_logs.info("===== " + "Verifying report contains checkOutDate '".toUpperCase() + checkOutDate + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "MMM dd, yyyy"));
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address1 '".toUpperCase() + address1 + "' =====");
				app_logs.info("===== " + "Verifying report contains address1 '".toUpperCase() + address1 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address1);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address2 '".toUpperCase() + address2 + "' =====");
				app_logs.info("===== " + "Verifying report contains address2 '".toUpperCase() + address2 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address2);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains address3 '".toUpperCase() + address3 + "' =====");
				app_logs.info("===== " + "Verifying report contains address3 '".toUpperCase() + address3 + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, address3);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains city '".toUpperCase() + city + "' =====");
				app_logs.info("===== " + "Verifying report contains city '".toUpperCase() + city + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, city);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "Verifying report contains country '".toUpperCase() + country + "' =====");
				app_logs.info("===== " + "Verifying report contains country '".toUpperCase() + country + "' =====");
				
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReportContent(getReportsData, country);
				testSteps.addAll(getTestSteps);

			} else {
				testSteps.add("Failed: <b>" + reportType.toUpperCase() + "</b> Report is not displaying");
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Verify <b>" + reportType.toUpperCase() + "</b> Report", testName,
						"PDFReading", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		
		
	}
	
	//Task color verification
	if(	testCases.equalsIgnoreCase("TaskIconColor")) {
		try {

			testSteps.add("==== " + "Verify Task Icon Colour".toUpperCase() + " =====" );
			app_logs.info("==== " + "Verify Task Icon Colour".toUpperCase() + " =====" );
			
			getTestSteps.clear();
			getTestSteps = reservationSearch.verifyBasicSearchWithReservationNumber(driver, reservationNumber, false);
			testSteps.addAll(getTestSteps);

			try {
				
				if(TaskStatus.equalsIgnoreCase(taskStatusDone) || TaskStatus.equalsIgnoreCase(taskStatusCancelled)) {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, false);
					testSteps.addAll(getTestSteps);
					
				}else {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, true);
					testSteps.addAll(getTestSteps);
					
				}

			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}
			
			TaskStatus = taskStatusDone;
			testSteps.add("===== " + "Updating Task Status To ".toUpperCase() + "'"+ TaskStatus +"' =====");
			app_logs.info("===== " + "Updating Task Status To".toUpperCase() + "'"+ TaskStatus +"' =====");

			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			getTestSteps.clear();
			getTestSteps = reservationPage.updateTask(driver, 1, TaskStatus);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			getTestSteps = reservationSearch.verifyBasicSearchWithReservationNumber(driver, reservationNumber, false);
			testSteps.addAll(getTestSteps);

			try{
				if(TaskStatus.equalsIgnoreCase(taskStatusDone) || TaskStatus.equalsIgnoreCase(taskStatusCancelled)) {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, false);
					testSteps.addAll(getTestSteps);
					
				}else {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, true);
					testSteps.addAll(getTestSteps);
					
				}

			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}

			TaskStatus = taskStatusCancelled;
			testSteps.add("===== " + "Updating Task Status To ".toUpperCase() + "'"+ TaskStatus +"' =====");
			app_logs.info("===== " + "Updating Task Status To ".toUpperCase() + "'"+ TaskStatus +"' =====");

			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);

			getTestSteps.clear();
			getTestSteps = reservationPage.updateTask(driver, 1, taskStatusCancelled);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			getTestSteps = reservationSearch.verifyBasicSearchWithReservationNumber(driver, reservationNumber, false);
			testSteps.addAll(getTestSteps);

			try {
				if(TaskStatus.equalsIgnoreCase(taskStatusDone) || TaskStatus.equalsIgnoreCase(taskStatusCancelled)) {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, false);
					testSteps.addAll(getTestSteps);				
				}else {
					getTestSteps.clear();
					getTestSteps = reservationPage.verifyTaskIconColour(driver, true);
					testSteps.addAll(getTestSteps);				
				}

			}catch(Exception|AssertionError e ) {
				app_logs.info("In UI catch");
				testSteps.add(e.toString());				
			}


		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify task icon", testName, "TaskIconVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify task icon", testName, "TaskIconVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	ArrayList<String> reservationNumberList = new ArrayList<>();
	String allReservation = "";
	if(testCases.equalsIgnoreCase("BulkCheckinCheckout")
			|| testCases.equalsIgnoreCase("BulkNoShow")
			|| testCases.equalsIgnoreCase("BulkDelete")
			|| testCases.equalsIgnoreCase("BulkCancel")){
		try {

			assertTrue(Integer.parseInt(numberOfReservation) <= 20, "Failed : Script can only create 20 reservation");
			for(int i=0; i < Integer.parseInt(numberOfReservation); i++) {
				
				reservationPage.click_NewReservation(driver, testSteps);						
				testSteps.add("=================== CREATE RESERVATION ======================");
				app_logs.info("=================== CREATE RESERVATION ======================");
				reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"));
				reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);

				reservationPage.clickOnFindRooms(driver, testSteps);
				ratePlanName = homePage.getRatePlan(driver, testSteps, 1); 
				app_logs.info(ratePlanName);
				roomClassName = homePage.getRoomClass(driver, testSteps, ratePlanName, 1);
				app_logs.info(roomClassName);						
				String isAssign = "Yes";
				reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
				reservationPage.clickNext(driver, testSteps);									
				reservationPage.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,phoneNumber,phoneNumber,
						email,"","",address1,address2,address3,city,country,state,postalCode,isGuesProfile);
				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
				reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);			
				reservationPage.clickBookNow(driver, testSteps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
				reservationNumberList.add(reservationNumber);
				reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
				getReservationPhoneNumber = reservationSearch.getReservationPhoneNumber(driver);
						
				TripSummaryRoomCharges = reservationPage.getRoomChargesInTripSummary(driver);
				app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
				TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
				TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
				TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
				testSteps.add("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
				app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
				
				TripSummaryTaxandServices = reservationPage.getTaxandServicesInTripSummary(driver);
				app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
				
				TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
				TripSummaryTaxandServices=TripSummaryTaxandServices.substring(1, TripSummaryTaxandServices.length());
				TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
				
				testSteps.add("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
				app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");					
			
				Utility.refreshPage(driver);
				Wait.waitforPageLoad(30, driver);
			}
			app_logs.info("reservationNumberList : " + reservationNumberList.size());					
			
			for(String s : reservationNumberList) {
				if(allReservation.isEmpty() || allReservation.equalsIgnoreCase("")) {
					allReservation = s;					
				}else {
					allReservation = allReservation + "," + s;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	//Bulk cancel
	if(	testCases.equalsIgnoreCase("BulkCancel")) {
		try {

			testSteps.add("==== " + "Bulk Cancellation reservation".toUpperCase() + " =====" );
			app_logs.info("==== " + "Bulk Cancellation reservation".toUpperCase() + " =====" );
			
			app_logs.info("allReservation : " + allReservation);

			getTestSteps.clear();
			getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Search with Multiple Reservation Numbers");
			app_logs.info("Successfully Search with Multiple Reservation Numbers");

			getTestSteps.clear();
			getTestSteps = reservationSearch.bulkActionCancellation(driver);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK CANCELATION".toUpperCase() + " =====");
			for(int i=0; i < reservationNumberList.size(); i++) {
					
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumberList.get(i), "Cancelled");
				testSteps.addAll(getTestSteps);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk cancel new reservation", testName, "BulkCancellationReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk cancel new reservation", testName, "BulkCancellationReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}


	//Bulk cancel
	if(	testCases.equalsIgnoreCase("BulkDelete")) {
		try {

			testSteps.add("==== " + "Bulk Delete reservation".toUpperCase() + " =====" );
			app_logs.info("==== " + "Bulk Delete reservation".toUpperCase() + " =====" );

			for(int i=0; i < reservationNumberList.size(); i++) {
				
				reservationSearch.basicSearchWithResNumber(driver, reservationNumberList.get(0), true);				
				reservationPage.clickFolio(driver, testSteps);				
				homePage.cancelPaymentInFolio(driver, testSteps, paymentType, "Void Payment", "Cancel");
				
				getTestSteps.clear();
				getTestSteps = folio.voidAllLineItem(driver, "Void to bulk delete");
				testSteps.addAll(getTestSteps);
	
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
				
				Utility.refreshPage(driver);
				Wait.waitforPageLoad(30, driver);
				
			}
			
			getTestSteps.clear();
			getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Search with Multiple Reservation Numbers");
			app_logs.info("Successfully Search with Multiple Reservation Numbers");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.deleteResevation(driver);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("===== " + "VERIFICATION OF RESERVATION HAS BEEN DELETED AFTER BULK DELETION".toUpperCase() + " =====");

			for(int i=0; i < reservationNumberList.size(); i++) {
	
				getTestSteps.clear();
				getTestSteps = reservationSearch.verifyBasicSearchWithReservationNumber(driver, reservationNumberList.get(i), true);
				testSteps.addAll(getTestSteps);
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk Deleted new reservation", testName, "BulkDeletedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk Deleted new reservation", testName, "BulkDeletedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}
	
	//Bulk no show
	if(	testCases.equalsIgnoreCase("BulkNoShow")) {
		
		try {

			testSteps.add("==== " + "Bulk NoShow reservation".toUpperCase() + " =====" );
			app_logs.info("==== " + "Bulk NoShow reservation".toUpperCase() + " =====" );

			app_logs.info("allReservation : " + allReservation);

			getTestSteps.clear();
			getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Search with Multiple Reservation Numbers");
			app_logs.info("Successfully Search with Multiple Reservation Numbers");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.makeReservationBulkNoShow(driver);
			testSteps.addAll(getTestSteps);				


			testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK No-Show" + " =====");
			
			for(int i=0; i < reservationNumberList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumberList.get(i), "No-Show");
				testSteps.addAll(getTestSteps);			
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk noshow new reservation", testName, "BulkNoshowReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk noshow new reservation", testName, "BulkNoshowReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}
	
	//bulk checkin and checkout reservation	
	if(	testCases.equalsIgnoreCase("BulkCheckinCheckout")) {
		try {

			testSteps.add("==== " + "Bulk Checkin reservation".toUpperCase() + " =====" );
			app_logs.info("==== " + "Bulk Checkin reservation".toUpperCase() + " =====" );
			
			app_logs.info("allReservation : " + allReservation);

			getTestSteps.clear();
			getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Search with Multiple Reservation Numbers");
			app_logs.info("Successfully Search with Multiple Reservation Numbers");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.clickBulkOptionCheckInAndVerifyPopUp(driver);
			testSteps.addAll(getTestSteps);
		
			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnBulkResservationCanUpdated(driver, "1");
			testSteps.addAll(getTestSteps);				

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnProcessButtonInBulkCheckInPopUp(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.closeBulkActionPopUp(driver);
			testSteps.addAll(getTestSteps);
						
			testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK CHECKIN" + " =====");
			
			for(int i=0; i < reservationNumberList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumberList.get(i), "In-House");
				testSteps.addAll(getTestSteps);			
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk checkin new reservation", testName, "BulkCheckinReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk checkin new reservation", testName, "BulkCheckinReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("==== " + "Bulk Checkout reservation".toUpperCase() + " =====" );
			app_logs.info("==== " + "Bulk Checkout reservation".toUpperCase() + " =====" );

			Utility.refreshPage(driver);
			Wait.waitforPageLoad(30, driver);

			app_logs.info("allReservation : " + allReservation);

			getTestSteps.clear();
			getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, allReservation);
			testSteps.addAll(getTestSteps);

			testSteps.add("Successfully Search with Multiple Reservation Numbers");
			app_logs.info("Successfully Search with Multiple Reservation Numbers");

			getTestSteps.clear();
			getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationSearch.selectBulkCheckOut(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnBulkResservationCanUpdated(driver, "1");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnProcessInBulkCheckOutPopUp(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.closeBulkActionPopUp(driver);
			testSteps.addAll(getTestSteps);

			testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK CHECKOUT".toUpperCase() + " =====");
			
			for(int i=0; i < reservationNumberList.size(); i++) {
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumberList.get(i), "Departed");
				testSteps.addAll(getTestSteps);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk checkout new reservation", testName, "BulkCheckoutReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to bulk checkout new reservation", testName, "BulkCheckoutReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	}
	
		//Basic search with reservation number
		if(testCases.equalsIgnoreCase("VerifyBasicSearchWithGuestNameAndResNumber")) {

			try {
			
				testSteps.add("<b>===== " + "Verified basic search with number".toUpperCase() + " =====</b>");
				app_logs.info("===== " + "Verified basic search with number".toUpperCase() + " =====");

				Utility.refreshPage(driver);
				
				boolean isRecoudFound = reservationSearch.verifyBasicSearchWithReservationNumber(driver, testSteps, reservationNumber);
				assertTrue(isRecoudFound, "Failed : No record found");
				app_logs.info("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is not displaying");
				testSteps.add("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is not displaying");
		
				testSteps.add("<b>===== " + "Verified basic search with wrong reservation number display no record found message".toUpperCase() + " =====</b>");
				app_logs.info("===== " + "Verified basic search with wrong reservation number display no record found message".toUpperCase() + " =====");

				isRecoudFound = reservationSearch.verifyBasicSearchWithReservationNumber(driver, testSteps, reservationNumber + randomNumber);
				assertTrue(!isRecoudFound, "Failed : record found");
				app_logs.info("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");
				testSteps.add("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");
		
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search reservation with number", testName, "SearchReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search reservation with number", testName, "SearchReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
			
				testSteps.add("<b>===== " + "Verified basic search with guest name".toUpperCase() + " =====</b>");
				app_logs.info("===== " + "Verified basic search with guest name".toUpperCase() + " =====");

				Utility.refreshPage(driver);
				
				boolean isRecoudFound = reservationSearch.verifyBasicSearchWithGuestName(driver, testSteps, guestName);
				assertTrue(isRecoudFound, "Failed : No record found");
				app_logs.info("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is not displaying");
				testSteps.add("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is not displaying");

				testSteps.add("<b>===== " + "Verified basic search with wrong guest name display no record found message".toUpperCase() + " =====</b>");
				app_logs.info("===== " + "Verified basic search with wrong guest name display no record found message".toUpperCase() + " =====");
		
				isRecoudFound = reservationSearch.verifyBasicSearchWithGuestName(driver, testSteps, guestName + randomString);
				assertTrue(!isRecoudFound, "Failed : Record found");
				app_logs.info("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");
				testSteps.add("Verified that error message ' No records meet your criteria. Please change your criteria and search again.' is displaying");

			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search reservation with guest name", testName, "SearchReservationWithGuestName", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search reservation with guest name", testName, "SearchReservationWithGuestName", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
				
		}
		
		
		//advance search with promo code
		if(testCases.equalsIgnoreCase("VerifyPromoCodeInAdvanceSearch")) {
			
			try {
					reservationPage.click_NewReservation(driver, testSteps);						
					testSteps.add("=================== CREATE RESERVATION ======================");
					app_logs.info("=================== CREATE RESERVATION ======================");
					reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"));
					reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
					reservationPage.enter_Adults(driver, testSteps, maxAdults);
					reservationPage.enter_Children(driver, testSteps, maxChildren);

					reservationPage.clickOnFindRooms(driver, testSteps);
					ratePlanName = homePage.getRatePlan(driver, testSteps, 1); 
					app_logs.info(ratePlanName);
					roomClassName = homePage.getRoomClass(driver, testSteps, ratePlanName, 1);
					app_logs.info(roomClassName);						
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
				
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get first rate plan and room class", testName, "GetRatePlanAndRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to get first rate plan and room class", testName, "GetRatePlanAndRoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, testSteps);
				navigation.RatesGrid(driver);
				testSteps.add("Navigated to RatesGrid");
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("=================== SEARCH RATE PLAN ======================");
				app_logs.info("=================== SEARCH RATE PLAN ======================");

				ratesGrid.clickRatePlanArrow(driver, getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.searchRatePlan(driver, ratePlanName);
				testSteps.addAll(getTestSteps);

				String getRatPlanName = ratesGrid.selectedRatePlan(driver);
				app_logs.info("getRatPlanName: " + getRatPlanName);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search and verify created plan", testName, "SearchPackagePlan",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to search and verify created plan", testName, "SearchPackagePlan",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {

				testSteps.add("===== UPDATING PACKAGE RATE PLAN =====");
				app_logs.info("===== UPDATING PACKAGE RATE PLAN =====");

				ratesGrid.clickOnEditRatePlan(driver);
				testSteps.add("Click on edit rate plan");
				app_logs.info("Click on edit rate plan");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to click on edit rate plan", testName, "UpdatePackagePlan", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to click on edit rate plan", testName, "UpdatePackagePlan", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				
				getTestSteps.clear();
				getTestSteps = homePage.clickRestrictionsAndPolicyTab(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = nightlyRate.promoCode(driver, "Yes", promoCode);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to add promo code in rate plan", testName, "UpdateRatePlan", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to add promo code in rate plan", testName, "UpdateRatePlan", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			
			try {

				testSteps.add("=================== SAVE UPDATED RATE PLAN ======================");
				app_logs.info("=================== SAVE UPDATED RATE PLAN ======================");
				
				getTestSteps.clear();
				getTestSteps =  ratesGrid.clickOnSaveratePlan(driver);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Updated rate successfully");
				app_logs.info("Updated rate successfully");
				derivedRate.closeOpenedRatePlanTab(driver, testSteps);
				try{
					derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
					ratesGrid.clickOnSaveratePlan(driver);
					derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
				}catch(Exception f){
					
				}
				try{
					navigation.Rates_Grid(driver);
				}catch(Exception e){
					derivedRate.ratePlanUnSaveDataPopupAppear(driver, "Cancel", getTestSteps);
					ratesGrid.clickOnSaveratePlan(driver);
					derivedRate.closeOpenedRatePlanTab(driver, getTestSteps);
				}

				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");


			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.updateReport(e, "Failed to add promo code in rate plan", testName, "UpdateRatePlan", driver);
					Utility.updateReport(e, "Failed to save updated rate plan", testName, "SaveUpdatedRateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.updateReport(e, "Failed to add promo code in rate plan", testName, "UpdateRatePlan", driver);
					Utility.updateReport(e, "Failed to save updated rate plan", testName, "SaveUpdatedRateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
					reservationPage.click_NewReservation(driver, testSteps);						
					testSteps.add("=================== CREATE RESERVATION ======================");
					app_logs.info("=================== CREATE RESERVATION ======================");
					reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"));
					reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));				
					reservationPage.enter_Adults(driver, testSteps, maxAdults);
					reservationPage.enter_Children(driver, testSteps, maxChildren);
					reservationPage.select_Rateplan(driver, testSteps, "Promo Code",promoCode);
					reservationPage.clickOnFindRooms(driver, testSteps);
					String isAssign = "Yes";
					reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					reservationPage.clickNext(driver, testSteps);									
					reservationPage.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,phoneNumber,phoneNumber,
							email,"","",address1,address2,address3,city,country,state,postalCode,isGuesProfile);
					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
					reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);			
					reservationPage.clickBookNow(driver, testSteps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);
					Utility.reservationNumber = reservationNumber;
					reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
					getReservationPhoneNumber = reservationSearch.getReservationPhoneNumber(driver);
							
					TripSummaryRoomCharges = reservationPage.getRoomChargesInTripSummary(driver);
					app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
					TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
					TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
					testSteps.add("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
					
					TripSummaryTaxandServices = reservationPage.getTaxandServicesInTripSummary(driver);
					app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					
					TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
					TripSummaryTaxandServices=TripSummaryTaxandServices.substring(1, TripSummaryTaxandServices.length());
					TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
					
					testSteps.add("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");					
				
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
			
				testSteps.add("===== " + "Verify Promo code advance search".toUpperCase() + " =====");
				app_logs.info("===== " + "Verify Promo code advance search".toUpperCase() + " =====");

				Utility.refreshPage(driver);
				Wait.waitforPageLoad(30, driver);
				
				reservationSearch.clickOnAdvance(driver);
				testSteps.add("Click on advance search");
				app_logs.info("Click on advance search");

				//PromoCode
				getTestSteps.clear();
				getTestSteps = reservationSearch.advanceSearchWithPromoCode(driver, promoCode);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationSearch.clickOnSearchButton(driver);
				testSteps.addAll(getTestSteps);
			
				getTestSteps.clear();
				getTestSteps = homePage.verifyNoResultsFound(driver, name, false);
				testSteps.addAll(getTestSteps);

				homePage.verifyReservationExistInAdvanceSearch(driver, testSteps, Utility.reservationNumber);
			
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for promocode field", testName, "VerifyAdvanceSearchWithPromoCode", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for promocode field", testName, "VerifyAdvanceSearchWithPromoCode", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

	
		try {
		
			testSteps.add("====================VERIFY SEARCHED RESERVATION ==============");
			app_logs.info("====================VERIFY SEARCHED RESERVATION ==============");
			
				homePage.getGuestNameAfterSearch(driver, testSteps, Utility.reservationNumber, true);
				//RatePlan
				String getRatePlan = reservationSearch.getRatePlanAfterSearch(driver);
				testSteps.add("Expected rate plan : " + "Promo Code");
				testSteps.add("Found : " + "Promo Code");
				assertEquals(getRatePlan, "Promo Code", "Failed : RatePlan didn't matched");
				testSteps.add("Verified rate plan");

				//PromoCode
				String getPromoCode = reservationSearch.getPromoCodeAfterSearch(driver);
				testSteps.add("Expected promo code : " + promoCode);
				testSteps.add("Found : " + getPromoCode);
				assertEquals(getPromoCode, promoCode, "Failed : Promo code didn't matched");
				testSteps.add("Verified promo code");						

				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results for promo code", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results for promo code", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
	}		
		

		
		//advance search with tax exmapt
		if(testCases.equalsIgnoreCase("VerifyAdvanceSearchUsingTaxExempt")) {
			try {
			
				testSteps.add("===== " + "Verify advance search".toUpperCase() + " =====");
				app_logs.info("===== " + "Verify advance search".toUpperCase() + " =====");

				Utility.refreshPage(driver);
				
				reservationSearch.clickOnAdvance(driver);
				testSteps.add("Click on advance search");
				app_logs.info("Click on advance search");
				
				//ReservationNumber
				getTestSteps.clear();
				getTestSteps = reservationSearch.advanceSearchWithReservationNumber(driver, reservationNumber);
				testSteps.addAll(getTestSteps);

				//TaxExampt
				getTestSteps.clear();
				getTestSteps = reservationSearch.advanceSearchWithTaxExampt(driver, taxExapmt);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = reservationSearch.clickOnSearchButton(driver);
				testSteps.addAll(getTestSteps);
			
				getTestSteps.clear();
				getTestSteps = homePage.verifyNoResultsFound(driver, name, false);
				testSteps.addAll(getTestSteps);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for tax exampt field combinations", testName, "VerifyAdvanceSearchWithTaxExampt", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for tax exampt field combinations", testName, "VerifyAdvanceSearchWithTaxExampt", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

	
		try {
		
			testSteps.add("====================VERIFY SEARCHED RESERVATION ==============");
			app_logs.info("====================VERIFY SEARCHED RESERVATION ==============");
			
			getTestSteps.clear();
			getTestSteps = reservationSearch.clickGuestNameToOpenRes(driver, 1);
			testSteps.addAll(getTestSteps);
				//TaxExampt
				String getTax  = reservationPage.getTripSummaryTaxesWithCurrency(driver, testSteps);
				assertEquals(getTax, "$ 0.00", "Failed : taxes are not zero after tax examption");
				app_logs.info("Verified that taxes are zero after tax examption");
				testSteps.add("Verified that taxes are zero after tax examption");
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results for tax exampt", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results for tax exampt", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
	}		
		
		//Advance Search
	if(testCases.equalsIgnoreCase("VerifyAdvanceSearch")) {
		
		if(!Utility.isReservationCreated) {

		try {
						Utility.guestName = guestName;
						Utility.accountName = account;
						Utility.accountNumber = accountNumber;			

						testSteps.add("========== Creating account ==========");
						app_logs.info("========== Creating account ==========");

						navigation.Accounts(driver);
						testSteps.add("Navigate to Accounts");
						app_logs.info("Navigate to Accounts");

						CreateTA.ClickNewAccountbutton(driver, accountType);
						CreateTA.AccountDetails(driver, accountType, account);
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
						getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
						testSteps.addAll(getTestSteps);
						CreateTA.NewReservationButton(driver, test);
					
						testSteps.add("=================== CREATE RESERVATION ======================");
						app_logs.info("=================== CREATE RESERVATION ======================");
						reservationPage.enterCheckInDate(driver, ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"));
						reservationPage.enterCheckOutDate(driver, ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
						reservationPage.enter_Adults(driver, testSteps, maxAdults);
						reservationPage.enter_Children(driver, testSteps, maxChildren);

						reservationPage.clickOnFindRooms(driver, testSteps);
						ratePlanName = homePage.getRatePlan(driver, testSteps, 1); 
						app_logs.info(ratePlanName);
						Utility.rateplanName = ratePlanName;
						app_logs.info("Utility.rateplanName : "+ Utility.rateplanName);

						roomClassName = homePage.getRoomClass(driver, testSteps, ratePlanName, 1);
						app_logs.info(roomClassName);		
						Utility.roomClassName = roomClassName;
						app_logs.info("Utility.roomClassName : "+ Utility.roomClassName);
						String isAssign = "Yes";
						reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, account);
						reservationPage.clickNext(driver, testSteps);									
						reservationPage.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,phoneNumber,phoneNumber,email,"","",address1,address2,address3,city,country,state,postalCode,isGuesProfile);
						reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);
						reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);
						reservationPage.clickBookNow(driver, testSteps);
						reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
						Utility.reservationNumber = reservationNumber;
						app_logs.info("Utility.reservationNumber : "+ Utility.reservationNumber);
						app_logs.info("reservationNumber : " + reservationNumber);
						reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
						reservationPage.clickCloseReservationSavePopup(driver, testSteps);
						roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");
						Utility.RoomNo = roomNumber;
						app_logs.info("Utility.RoomNo : "+ Utility.RoomNo);
	
						getReservationPhoneNumber = reservationSearch.getReservationPhoneNumber(driver);
								
						TripSummaryRoomCharges = reservationPage.getRoomChargesInTripSummary(driver);
						app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
						TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
						TripSummaryRoomCharges=TripSummaryRoomCharges.substring(1, TripSummaryRoomCharges.length());
						TripSummaryRoomCharges=TripSummaryRoomCharges.trim();
						testSteps.add("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
						app_logs.info("TripSummaryRoomCharges : " + TripSummaryRoomCharges);
						
						TripSummaryTaxandServices = reservationPage.getTaxandServicesInTripSummary(driver);
						app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
						
						TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
						TripSummaryTaxandServices=TripSummaryTaxandServices.substring(1, TripSummaryTaxandServices.length());
						TripSummaryTaxandServices=TripSummaryTaxandServices.trim();
						
						testSteps.add("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
						app_logs.info("TripSummaryTaxandServices : " + TripSummaryTaxandServices);
						
						reservationPage.closeReservationTab(driver);
						testSteps.add("Closed opened reservation");
						app_logs.info("Closed opened reservation");

			}catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
						
				try {			
						testSteps.add("==========GET ROOMCLASS ABBREVIATION=========");
						app_logs.info("==========GET ROOMCLASS ABBREVIATION=========");
						navigation.Setup(driver);
						testSteps.add("Navigate Setup");
						navigation.RoomClass(driver);
						testSteps.add("Navigate RoomClass");
						roomClassAbbreviation = homePage.getRoomClassAbbreviation(driver, testSteps, roomClassName);
						app_logs.info("RoomClass Abbreviation:" + roomClassAbbreviation);
				
						navigation.cpReservation_Backward(driver);
						app_logs.info("Navigate back to reservation");
						testSteps.add("Navigate back to reservation");
						Utility.isReservationCreated = true;
						
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to get room class abbreviation", testName, "GetRoomClassAbbreviation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to get room class abbreviation", testName, "GetRoomClassAbbreviation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
	}
				
				
			try {
			
				testSteps.add("===== " + "Verify advance search".toUpperCase() + " =====");
				app_logs.info("===== " + "Verify advance search".toUpperCase() + " =====");

				Utility.refreshPage(driver);
				
				reservationSearch.clickOnAdvance(driver);
				testSteps.add("Click on advance search");
				app_logs.info("Click on advance search");
				
				if(columnsList.contains("GuestName")) {						
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithGuestName(driver, Utility.guestName);
					testSteps.addAll(getTestSteps);
				}

				if(columnsList.contains("Email")) {						
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithEmail(driver, email);
					testSteps.addAll(getTestSteps);

				}

				if(columnsList.contains("PhoneNumber")) {						
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithPhoneNumber(driver, phoneNumber);
					testSteps.addAll(getTestSteps);
				}
				
				if(columnsList.contains("Country")) {						
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithCountry(driver, country);
					testSteps.addAll(getTestSteps);
					if(columnsList.contains("State")) {						
						getTestSteps.clear();
						getTestSteps = reservationSearch.advanceSearchWithState(driver, state);
						testSteps.addAll(getTestSteps);
					
					}

				}
				
				if(columnsList.contains("AccountName")) {
					//AccountName
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithAccountName(driver, Utility.accountName);
					testSteps.addAll(getTestSteps);					
				}
				
				if(columnsList.contains("AccountNumber")) {
					//AccountNumber
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithAccountNumber(driver, Utility.accountNumber);
					testSteps.addAll(getTestSteps);
				}
				
				if(columnsList.contains("ClientType")) {
					//ClientType
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithClientType(driver, clientName);
					testSteps.addAll(getTestSteps);

				}
				if(columnsList.contains("ReservationNumber")) {
					//ReservationNumber
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithReservationNumber(driver, Utility.reservationNumber);
					testSteps.addAll(getTestSteps);

				}
				
				if(columnsList.contains("ReservationStatus")) {
					//ReservationStatus
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithStatus(driver, "Reserved");
					testSteps.addAll(getTestSteps);

				}
				if(columnsList.contains("StayFrom")) {
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithCheckinDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);
				}	
				if(columnsList.contains("StayTo")) {
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithCheckoutDate(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

				}
				if(columnsList.contains("BookFrom") || columnsList.contains("BookTo")) {
					//BookFrom
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithBookFrom(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					//BookTo
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithBookTo(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

				}				
				
				if(columnsList.contains("RatePlan")) {
					//RatePlan
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithRatePlan(driver, Utility.rateplanName);
					testSteps.addAll(getTestSteps);
				}
				if(columnsList.contains("RoomClass")) {
					//RoomClass
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithRoomClass(driver, Utility.roomClassName);
					testSteps.addAll(getTestSteps);
					if(columnsList.contains("RoomNumber")) {
						//RoomNumber
						getTestSteps.clear();
						getTestSteps = reservationSearch.advanceSearchWithRoomNumber(driver, Utility.RoomNo);
						testSteps.addAll(getTestSteps);
					}

				}
				if(columnsList.contains("CreditCard")) {
					//CreditCard
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithCreditCard(driver, lastFourDigitsOfCreditCard);
					testSteps.addAll(getTestSteps);
					
				}
				if(columnsList.contains("Source")) {
					//Source
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithSource(driver, source);
					testSteps.addAll(getTestSteps);
					
				}
				if(columnsList.contains("MarketSegment")) {
					//MarketSegment
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithMarketingSegment(driver, marketSegment);
					testSteps.addAll(getTestSteps);
		
				}
				if(columnsList.contains("Referral")) {
					//Referral					
					getTestSteps.clear();
					getTestSteps = reservationSearch.advanceSearchWithReferral(driver, referral);
					testSteps.addAll(getTestSteps);
				}
				
				getTestSteps.clear();
				getTestSteps = reservationSearch.clickOnSearchButton(driver);
				testSteps.addAll(getTestSteps);
			
				getTestSteps.clear();
				getTestSteps = homePage.verifyNoResultsFound(driver, name, false);
				testSteps.addAll(getTestSteps);
				
				
				//reservationSearch.clickCloseAdvanceSearchButton(driver);
				
				homePage.verifyReservationExistInAdvanceSearch(driver, testSteps, Utility.reservationNumber);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for different fields combinations", testName, "VerifyAdvanceReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advacne search for different fields combinations", testName, "VerifyAdvanceReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {

				testSteps.add("====================VERIFY SEARCHED RESERVATION ==============");
				app_logs.info("====================VERIFY SEARCHED RESERVATION ==============");
				
				if(columnsList.contains("GuestName")) {						
					//GuestName
					String getGuestName = homePage.getGuestNameAfterSearch(driver, testSteps, Utility.reservationNumber, false);
					testSteps.add("Expected guest name: " + Utility.guestName);
					testSteps.add("Found: " + getGuestName);
					assertTrue(getGuestName.contains(Utility.guestName), "Failed : guest name didn't matched");
					testSteps.add("Verified guest name");

				}
				
				
				if(columnsList.contains("AccountName")) {
					//AccountName
					String getAccountName = homePage.getAccountNameAfterSearch(driver, Utility.reservationNumber);
					testSteps.add("Expected AccountName: " + Utility.accountName);
					testSteps.add("Found : " + getAccountName);
					if(account.isEmpty() || account.equals("")) {
						assertEquals(getAccountName, "-", "Failed : account name didn't matched");
						testSteps.add("Verified account name");
						
					}else {
						assertEquals(getAccountName, Utility.accountName, "Failed : account name didn't matched");						
						testSteps.add("Verified account name");
					}
					
				}
				if(columnsList.contains("ReservationNumber")) {
					//ReservationNumber
					String getReservationNumber = homePage.getReservationNumberAfterSearch(driver, Utility.reservationNumber);
					testSteps.add("Expected reservation number: " + Utility.reservationNumber);
					testSteps.add("Found : " + getReservationNumber);
					assertEquals(getReservationNumber, Utility.reservationNumber, "Failed : reservation number didn't matched");
					testSteps.add("Verified reservation number");

				}
				if(columnsList.contains("ReservationStatus")) {
					//ReservationStatus
					String getreservationStatus = homePage.getReservationStatusAfterSearch(driver, Utility.reservationNumber);
					testSteps.add("Expected status: " + "Reserved");
					testSteps.add("Found : " + getreservationStatus);
					assertEquals(getreservationStatus, "Reserved", "Failed : reservation status didn't matched");
					testSteps.add("Verified status");

				}
				
				if(columnsList.contains("StayFrom") || columnsList.contains("BookFrom")) {
					//stay from book from
					String reformattedCheckin = ESTTimeZone.reformatDate(checkInDate, "MM/dd/yyyy", "dd MMM, yyyy");
					
					String getArrivalDate = homePage.getArrivalDateAfterSearch(driver, Utility.reservationNumber);
					testSteps.add("Expected arrival date: " + reformattedCheckin);
					testSteps.add("Found : " + getArrivalDate);
					assertEquals(getArrivalDate, reformattedCheckin, "Failed : checkin didn't matched");
					testSteps.add("Verified arrival date");
				}	
				if(columnsList.contains("StayTo") || columnsList.contains("BookTo")) {
					//stay to book to
					String reformattedCheckOut = ESTTimeZone.reformatDate(checkOutDate, "MM/dd/yyyy", "dd MMM, yyyy");
					
					String getDepartDate = homePage.getDepartDateAfterSearch(driver, Utility.reservationNumber);
					testSteps.add("Expected depart date: " + reformattedCheckOut);
					testSteps.add("Found : " + getDepartDate);
					assertEquals(getDepartDate, reformattedCheckOut, "Failed : checkout didn't matched");
					testSteps.add("Verified depart date");

				}
		
				homePage.getGuestNameAfterSearch(driver, testSteps, Utility.reservationNumber, true);

				if(columnsList.contains("RatePlan")) {

						//RatePlan
						String getRatePlan = reservationSearch.getRatePlanAfterSearch(driver);
						testSteps.add("Expected rate plan : " + Utility.rateplanName);
						testSteps.add("Found : " + getRatePlan.toUpperCase());
						assertEquals(getRatePlan.toUpperCase(), Utility.rateplanName, "Failed : RatePlan didn't matched");
						testSteps.add("Verified rate plan");

				}

				if(columnsList.contains("Email")) {						
					String getEmail = reservationSearch.getEmailAfterSearch(driver);
					testSteps.add("Expected email: " + email);
					testSteps.add("Found : " + getEmail);
					assertEquals(getEmail, email, "Failed : Email didn't matched");
					testSteps.add("Verified email");

				}

				if(columnsList.contains("Country")) {						
					String getCountry = reservationSearch.getCountryAfterSearch(driver);
					testSteps.add("Expected country: " + country);
					testSteps.add("Found : " + getCountry);
					assertEquals(getCountry, country, "Failed : Country didn't matched");
					testSteps.add("Verified country");
					if(columnsList.contains("State")) {						
						String getState = reservationSearch.getStateAfterSearch(driver);
						testSteps.add("Expected state: " + state);
						testSteps.add("Found : " + getState);
						assertEquals(getState, state, "Failed : State didn't matched");
						testSteps.add("Verified state");
					}

				}
				

				if(columnsList.contains("RoomClass")) {
					//RoomClass
					String getRoomClass = reservationSearch.getRoomAfterSearch(driver,1);
					testSteps.add("Expected room class: " + Utility.roomClassName);
					testSteps.add("Found : " + getRoomClass);
					assertEquals(getRoomClass, Utility.roomClassName, "Failed : RoomClass didn't matched");
					testSteps.add("Verified room class");
					
					if(columnsList.contains("RoomNumber")) {
						//RoomNumber
						String getRoom = reservationSearch.getRoomNumberAfterSearch(driver);
						testSteps.add("Expected room: " + Utility.RoomNo.trim());
						testSteps.add("Found : " + getRoom.trim());
						assertEquals(getRoom.trim(), Utility.RoomNo.trim(), "Failed : room number didn't matched");
						testSteps.add("Verified room");

					}

				}
				
				if(columnsList.contains("PhoneNumber")) {						
					String getPhone = reservationSearch.getPhoneNumberAfterSearch(driver);
					testSteps.add("Expected phoneNumber: " + phoneNumber);
					testSteps.add("Found : " + getPhone);
					assertEquals(getPhone, phoneNumber, "Failed : PhoneNumber didn't matched");
					testSteps.add("Verified phone number");			
				}
				
				if(columnsList.contains("Source")) {
					//Source
					String getSource = reservationSearch.getSourceAfterSearch(driver);
					testSteps.add("Expected source: " + source);
					testSteps.add("Found : " + getSource);
					assertEquals(getSource, source, "Failed : Source didn't matched");
					testSteps.add("Verified Source");
					
				}
				
				if(columnsList.contains("MarketSegment")) {
					//MarketSegment
					String getMarketSegment = reservationSearch.getMarketSegmentAfterSearch(driver);
					testSteps.add("Expected marketSegment: " + marketSegment);
					testSteps.add("Found : " + getMarketSegment);
					assertEquals(getMarketSegment, marketSegment, "Failed : MarketSegment didn't matched");
					testSteps.add("Verified MarketSegment");
		
				}
				
				if(columnsList.contains("Referral")) {
					//Referral					
					String getRefferal = reservationSearch.getReferralAfterSearch(driver);
					testSteps.add("Expected referral: " + referral);
					testSteps.add("Found : " + getRefferal);
					assertEquals(getRefferal, referral, "Failed : Referral didn't matched");
					testSteps.add("Verified referral");
				}

				if(columnsList.contains("CreditCard")) {
					//CreditCard
					if(!cardNumber.isEmpty() || !cardNumber.equals("")) {
						String getCardNumber = reservationSearch.getCreditCardAfterSearch(driver);
						testSteps.add("Expected state: " + lastFourDigitsOfCreditCard);
						testSteps.add("Found : " + getCardNumber);
						
						assertEquals(getCardNumber, lastFourDigitsOfCreditCard, "Failed : CardNumber didn't matched");
						testSteps.add("Verified card number");
						
					}
					
				}

				//resevation.verifyTaxExempt(driver, isTaxExempt, TaxExemptId);

				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify advance search results", testName, "VerifySearchedReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
					
			//After reservation creation Unassigned Count
			if(testCases.equalsIgnoreCase("VerifyQuickStatWithUnAssignedReservation")) {
				
				try {

					testSteps.add("===== " + "Verifying that newly created reservation has unassigned room number".toUpperCase() +" =====");
					app_logs.info("===== " + "Verifying that newly created reservation has unassigned room number".toUpperCase() +" =====");
				
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
					
					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : "  + reservationNumber);
					app_logs.info("Searched and opened reservation with number : "  + reservationNumber);
														
					testSteps.add("===== " + "Verifying that reservation has unassigned room" + " =====");
					app_logs.info("===== " + "Verifying that reservation has unassigned room" + " =====");
					
					String getRoomNumber = homePage.verifyReservationRoomNumber(driver);
					
					testSteps.add("Expeced room number : Unassigned");
					app_logs.info("Expeced room number : Unassigned");

					testSteps.add("Found : " + getRoomNumber);
					app_logs.info("Found : " + getRoomNumber);

					assertEquals(getRoomNumber, "Unassigned", "Failed : Room number is not unassigned");
					testSteps.add("Verified room number is unassigned");
					app_logs.info("Verified room number is unassigned");

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed reservation tab");
					app_logs.info("Closed reservation tab");
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned room number  in reservation", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned room number  in reservation", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				try {

					testSteps.add("===== " + "Verifying that unassigned reservation count is incremented after creating new reservation".toUpperCase() +" =====");
					app_logs.info("===== " + "Verifying that unassigned reservation count is incremented after creating new reservation".toUpperCase() +" =====");
				
					Utility.refreshPage(driver);
					
					getTestSteps.clear();
					getTestSteps = homePage.clickDashBoardOption(driver, "Unassigned", true);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					testSteps.add("Previous Unassigned Reservations count : " + beforeUnassignedResCount);
					app_logs.info("Previous Unassigned Reservations count : " + beforeUnassignedResCount);
					
					int getReservationsCount = homePage.getDashBoardOptionValue(driver, "Unassigned");
					testSteps.add("New Unassigned Reservations count : " + getReservationsCount);
					app_logs.info("New Unassigned Reservations count : " + getReservationsCount);
					
					assertTrue(getReservationsCount > beforeUnassignedResCount, "Failed : Unassigned Reservation count didn't increased");
					testSteps.add("Verified that Unassigned reservation count is incremented");
					app_logs.info("Verified that Unassigned reservation count is incremented");
					
				
					testSteps.add("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
					app_logs.info("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
														
					testSteps.add("Unassigned count : " + getReservationsCount);
					app_logs.info("Unassigned count : " + getReservationsCount);
				
					if(getReservationsCount > 0) {
						int totalRecords = homePage.getTotalRecordFound(driver);
						testSteps.add("Total Records : " + totalRecords);
						app_logs.info("Total Records : " + totalRecords);
					
						assertEquals(getReservationsCount, totalRecords, "Failed : Unassigned count didn't match with total records");
						testSteps.add("Verified that unassigned count is equal to total record found");
						app_logs.info("Verified that unassigned count is equal to total record found");
						
					}else {
						testSteps.add("Total records lable is not showing because all unassigned count is zero");
						app_logs.info("Total records lable is not showing because all unassigned count is zero");
										
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned reservation count", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned reservation count", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				try {

					testSteps.add("===== " + "Verifying that unassigned count decreases after assigning room number to unassigned reservation".toUpperCase() +" =====");
					app_logs.info("===== " + "Verifying that unassigned count decreases after assigning room number to unassigned reservation".toUpperCase() +" =====");
									
					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : "  + reservationNumber);
					app_logs.info("Searched and opened reservation with number : "  + reservationNumber);
										
					reservationPage.AssignNewRoomNumber(driver);

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed reservation tab");
					app_logs.info("Closed reservation tab");

					Utility.refreshPage(driver);

					getTestSteps.clear();
					getTestSteps = homePage.clickDashBoardOption(driver, "Unassigned", true);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = homePage.selectDateFromDashboardCalendar(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					testSteps.add("Previous Unassigned Reservations count : " + beforeUnassignedResCount);
					app_logs.info("Previous Unassigned Reservations count : " + beforeUnassignedResCount);
					
					int getReservationsCount = homePage.getDashBoardOptionValue(driver, "Unassigned");
					testSteps.add("New Unassigned Reservations count : " + getReservationsCount);
					app_logs.info("New Unassigned Reservations count : " + getReservationsCount);
					
					assertEquals(getReservationsCount, beforeUnassignedResCount, "Failed : Unassigned Reservation count didn't decreased");
					testSteps.add("Verified that Unassigned reservation count decreased");
					app_logs.info("Verified that Unassigned reservation count decreased");
					
				
					testSteps.add("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
					app_logs.info("===== " + "Verified that unassigned reservation count is equal to total record found".toUpperCase() + " =====");
														
					testSteps.add("Unassigned count : " + getReservationsCount);
					app_logs.info("Unassigned count : " + getReservationsCount);

					if(getReservationsCount > 0) {
						int totalRecords = homePage.getTotalRecordFound(driver);
						testSteps.add("Total Records : " + totalRecords);
						app_logs.info("Total Records : " + totalRecords);
					
						assertEquals(getReservationsCount, totalRecords, "Failed : Unassigned count didn't match with total records");
						testSteps.add("Verified that unassigned count is equal to total record found");
						app_logs.info("Verified that unassigned count is equal to total record found");
						
					}else {
						testSteps.add("Total records lable is not showing because all unassigned count is zero");
						app_logs.info("Total records lable is not showing because all unassigned count is zero");
										
					}
				
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned count", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
						Utility.updateReport(e, "Failed to verify unassigned count", testName, "VerifyUnassignedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}
			
	try {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("uiValidationForHomePage", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
