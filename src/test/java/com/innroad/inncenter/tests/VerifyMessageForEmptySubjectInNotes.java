package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyMessageForEmptySubjectInNotes extends TestCore{




	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	// ArrayList<String> getTest_Steps = new ArrayList<>();
	
	ArrayList<String> totalNights = new ArrayList<>();
	ArrayList<String> roomBaseAmount = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();	
	ArrayList<String> getRoomNumbers = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	Double depositAmount=0.0;
	Double paidDeposit=0.0;
	
	String policyFor = "Capture", roomClassNameWithoutNum, rateNameWithoutNum, policyNameWithoutNum,
			typeToValidate, policyDesc, policyText, taxes, reservation=null, status=null, guestFullName, authtorizeIcon;
	String  propertyName = null, roomClassNames = null, roomClassAbbs = null,
			 roomClassAvvWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, balance = null,windowName=null;
	int tableSize = 0;
	String testName = this.getClass().getSimpleName().trim();


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		//String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyMessageForEmptySubjectInNotes(String roomClassName, String roomClassAbb, String maxAdults, 
			String maxPersons, String roomQuantity, String rateName, String ratePlan, String baseAmount,String additionalAdult, 
			String additionalChild, String displayName, String ratePolicy, String rateDescription, 
			String associateSeason, String policyName, String policyType, String policyAmount, String source, String policySeason,
			String checkInDate, String checkOutDate, String adultsForRes, String childrenForRes,
			String salutation, String guestFirstName,String guestLastName, String phoneNumber, String altenativePhone, String email,
			String account, String accountType, String address1, String address2, String address3, String city, String country,
			String state, String postalCode, String isGuesProfile, String paymentMethod, String cardNumber, String cardExpDate, 
			String referral, String authorizeIconSrc,String NoteType,String Subject,String Description) throws InterruptedException, IOException, ParseException {

		//String propertyName="sowmya test property";
		String testcaseId="848368";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848368");
		
		testDescription = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848368' target='_blank'>"
				+ "Click here to open TestRail: C848368</a><br>";
				
		testCatagory = "Reservation Task";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		

		Navigation navigation = new Navigation();
		//Reservation cPRes=new Reservation();
		CPReservationPage reservationPage = new CPReservationPage();
		MerchantServices Mservice=new MerchantServices();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();	
		Folio folio = new Folio();
		Reports report = new Reports();


		
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//Login login = new Login();

			try {
				login_Autoota(driver, test_steps);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				login_Autoota(driver, test_steps);
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
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
            if (!Utility.insertTestName.containsKey(testName)) {
                Utility.insertTestName.put(testName, testName);
                Utility.reTry.put(testName, 0);
            } else {
                Utility.reTry.replace(testName, 1);
            }
    		String randomNum = Utility.GenerateRandomNumber();
    		roomClassName = roomClassName.replace("ClassFor", policyFor);
    		roomClassNameWithoutNum = roomClassName.replace("RandomNum", "");
    		roomClassName = roomClassName.replace("RandomNum", randomNum);
    		roomClassAbb = roomClassAbb.replace("RandomNum", randomNum);
    		rateName = rateName.replace("ClassFor", policyFor);
    		rateNameWithoutNum = rateName.replace("RandomNum", "");
    		rateName = rateName.replace("RandomNum", randomNum);
    		
    	
    		guestLastName = policyFor+guestLastName+randomNum;
    		guestFullName = guestFirstName+" "+guestLastName;
    		roomBaseAmount.add(baseAmount);
    		if (policyFor.equalsIgnoreCase("Authorize")) {
    			typeToValidate = "Authorization Only";
    			authorizeIconSrc = "/Folio_Images/7.gif";
    			authtorizeIcon = "A";
    		}else if (policyFor.equalsIgnoreCase("Capture")) {
    			typeToValidate = "Capture";
    			authorizeIconSrc = "/Folio_Images/8.gif";
    			authtorizeIcon = "Capture";
    			}
    		if ( !(Utility.validateInput(checkInDate)) ) {
    			for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
    				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
    				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
    			}
    		}else {
    			checkInDates = Utility.splitInputData(checkInDate);
    			checkOutDates = Utility.splitInputData(checkOutDate);
    		}
    		for (int i = 0; i < checkInDates.size(); i++) {
    			totalNights.add(Utility.differenceBetweenDates(checkInDates.get(i), checkOutDates.get(i)));
    		}

		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before execute test scripts", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to update test data before execute test scripts", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
	
		

		
	
		try {
			test_steps.add("========== Creating new Reservation ==========");
			
		
			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, checkInDates.get(0));
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDates.get(0));
			reservationPage.enter_Adults(driver, test_steps, adultsForRes);
			reservationPage.enter_Children(driver, test_steps, childrenForRes);
			reservationPage.select_Rateplan(driver, test_steps, ratePlan,"");
			reservationPage.clickOnFindRooms(driver, test_steps);
			reservationPage.selectRoom(driver, test_steps, roomClassName, Utility.RoomNo, "");
			depositAmount=reservationPage.deposit(driver, test_steps, "", "");		
			reservationPage.clickNext(driver, test_steps);
			taxes = reservationPage.getTaxesFromTripSummary(driver);
			test_steps.add("Taxes and service charges captured during reservation creation is <b>"+taxes+"</b>");
			reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
					phoneNumber, altenativePhone, email, account, accountType, address1, address2, address3, city,
					country, state, postalCode, isGuesProfile);
				if((account.equalsIgnoreCase("")||account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, guestFullName, cardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", "", referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservation=reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			status=reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			String roomNumber1 = reservationPage.get_RoomNumber(driver, test_steps, "");
			roomNumber.add(roomNumber1);
			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {			 
			test_steps.add("========== Verifying associated cancellation policy details at Policies And Disclaimers tab ==========");
			reservationPage.emptySubjectWarningMessageInNotes(driver, test_steps, "Yes", NoteType, "", Description);
			
			
			
	        
		}catch (Exception e) {
			System.out.println(e);
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to cancel reservation and verify associated cancellation and other "
						+ "payment details", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed to cancel reservation and verify associated cancellation and other "  
					+"payment details", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		
		
		
		try {
			
			comments.add(0,  "Warning message is showing for empty subject when creating notes");
			statusCode.add(0, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, test_steps);
				Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("MessageForEmptySubjectInNotes", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {

		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}





}
