package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;

public class VerifyOverbookingMsgSplitRes extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, SanityExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyOverbookingOnCopyingReservation(String TestCaseID,String url, String ClientCode, String Username, String Password,
			String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan, String PromoCode,
			String IsAssign, String IsDepositOverride, String DepositOverrideAmount, String Salutation,
			String AssignedFirstName, String AssignedLastName, String UnassignedFirstName, String UnassignedLastName,
			String PhoneNumber, String AlternativePhone, String Email, String Account, String AccountType,
			String Address1, String Address2, String Address3, String City, String Country, String State,
			String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber, String NameOnCard,
			String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue, String TravelAgent,
			String MarketSegment, String Referral, String RoomClassAbb, String RoomClassName, String bedsCount,
			String maxAdults, String maxPersons, String roomQuantity, String Season, String RatePlan, String RateName,
			String BaseAmount, String AddtionalAdult, String AdditionalChild, String DisplayName,
			String AssociateSession, String RatePolicy, String RateDescription,String policy,String sortOrder ,String maxPerson, String details,String roomQuant,
			String roomName,String statinId,String zone)
					throws InterruptedException, IOException {
		 TestCaseID="848115|848198|848250";
		 boolean isExecute=Utility.getResultForCase(driver, TestCaseID);
		 if(isExecute) {
		String TripSummaryRoomCharges=null,TripSummaryTaxes=null,TripSummaryIncidentals=null,TripSummaryTripTotal=null,TripSummaryPaid=null,TripSummaryBalance=null;
		String FilioRoomCharges=null,FilioTaxes=null,FilioIncidentals=null,FilioTripTotal=null,FilioPaid=null,FilioBalance=null;

	
		
		String[] testcase=TestCaseID.split("\\|");
		for(int i=0;i<testcase.length;i++) {
			caseId.add(testcase[i]);
			statusCode.add("4");
		}

		test_name = "VerifyOverbookingSplitReservation";
		testDescription = "Verify Overbooking On Copying A Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682525' target='_blank'><br>"
				+ "Click here to open TestRail: C682525</a>";

		testCategory = "Overbooking";
		String testName = test_name;

		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Navigation nav = new Navigation();
		NewRoomClassesV2 roomClass = new NewRoomClassesV2();
		Rate rate = new Rate();
		ReservationSearch reser_search = new ReservationSearch();
		CPReservationPage res = new CPReservationPage();
		Login login=new Login();
		Double depositAmount = 0.0;
		String reservationNumber = null;
		AssignedLastName = AssignedLastName + Utility.GenerateRandomNumber();
		UnassignedLastName = UnassignedLastName + Utility.GenerateRandomNumber();
		RateName = RateName + Utility.GenerateRandomNumber();
		RoomClassName = RoomClassName + Utility.GenerateRandomNumber();
		String tempraryRoomClassName  = RoomClassName;

		try {

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			if ( !(Utility.validateDate(CheckInDate)) ) {
				CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
				CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			}

			//login.login(driver, envURL, "client3281", "autouser", "Auto@123");
			loginClinent3281(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);

			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			app_logs.info("======Create RoomClass=======");
			test_steps.add("======Create RoomClass=======");
			nav.Setup(driver);
			app_logs.info("Navigate Setup");
			test_steps.add("Navigate Setup");
			nav.RoomClass(driver);
			app_logs.info("Navigate RoomClass");
			test_steps.add("Navigate RoomClass");
			Elements_NewRoomClassPage roomClassEle = new Elements_NewRoomClassPage(driver);
			NewRoomClassesV2 roomClassObj = new NewRoomClassesV2();
			ArrayList<String> getTest_Steps = new ArrayList<>();
			

			roomClassEle.createnewRoomClass.click();
			test_steps.add("Clicking on New Room Class");
			app_logs.info("Clicking On New Room Class");

			getTest_Steps.clear();
			getTest_Steps = roomClassObj.createRoomClassWithDescriptionV2(driver, getTest_Steps, RoomClassName, RoomClassAbb,
			policy, sortOrder, maxAdults, maxPerson, details, roomQuant, roomName, statinId, sortOrder, zone);
			test_steps.addAll(getTest_Steps);
			test_steps.add("New Room Class Created");
			app_logs.info("New Room Class Created");
			nav.RoomClass(driver, test_steps);

					/*	nav.NewRoomClass(driver);
			app_logs.info("NewRoomClass Button Clicked");
			test_steps.add("NewRoomClass Button Clicked");
			getTest_Steps.clear();
			/*getTest_Steps = roomClass.Create_RoomClass(driver, RoomClassName, RoomClassAbb, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			roomClass.CloseOpenedRoomClass(driver, RoomClassName, getTest_Steps);*/

			 	//	roomQuantity="1";
			// roomClass.createRoomClassV2(driver, test_steps, RoomClassName, RoomClassAbb, maxAdults, maxPersons, roomQuantity);
			//roomClass.closeRoomClassTabV2(driver, RoomClassName);
			// test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
			// app_logs.info("Sccessfully Created New RoomClass" + RoomClassName + " Abb : " + RoomClassAbb);

			 test_steps.add("Sccessfully Created New RoomClass  : " + RoomClassName);
			 app_logs.info("Sccessfully Created New RoomClass :  " + RoomClassName); 
		} 
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			app_logs.info("======Create Rate=======");
			test_steps.add("======Create Rate=======");
			//			nav.Reservation_Backward_1(driver);
			nav.InventoryV2(driver);
			app_logs.info("Navigate Inventory");
			test_steps.add("Navigate Inventory");
			nav.Rates1(driver);
			app_logs.info("Navigate Rate");
			test_steps.add("Navigate Rate");
			DisplayName = RateName;
			getTest_Steps.clear();
			getTest_Steps = rate.CreateRate_Season(driver, RateName, maxAdults, maxPersons, BaseAmount, AddtionalAdult,
					AdditionalChild, DisplayName, RatePolicy, RateDescription, RoomClassName, Season, RatePlan,
					getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Enter all require details and save rate");
			app_logs.info("Enter all require details and save rate");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create Reservation

		try {
			CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
			
			app_logs.info("======CP Assinged Reservation Creation=======");
			test_steps.add("======CP Assinged Reservation Creation=======");
			nav.navigateToReservations(driver);
			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.select_Room(driver, test_steps, RoomClassName, "Yes", Account);
			//res.selectRoom1(driver, test_steps, RoomClassName, "Yes", Account);
			depositAmount = res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			res.enter_ContactName(driver, getTest_Steps, "", AssignedFirstName, AssignedLastName);
			res.enter_MailingAddress(driver, test_steps, Salutation, AssignedFirstName, AssignedLastName, PhoneNumber,
					AlternativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			res.selectReferral(driver, Referral);
			res.clickBookNow(driver, test_steps);
			reservationNumber = res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.close_FirstOpenedReservation(driver, getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation ", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
			CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");

			app_logs.info("=====Split Reservation Creation=======");
			test_steps.add("======Split Reservation Creation=======");
			res.click_NewReservation(driver, test_steps);
			driver.navigate().refresh();
			res.click_NewReservation(driver, test_steps);
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			
			res.clickAddARoom(driver);
			res.clickOnSplitResButton(driver, test_steps);
			CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
			CheckOutDate= Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			res.select_CheckInDate(driver, test_steps, CheckInDate);
			res.select_CheckoutDate(driver, test_steps, CheckOutDate);
			
			CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
			CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
			res.select_CheckInDateSplit(driver, test_steps, CheckInDate);
			res.select_CheckoutDateSplit(driver, test_steps, CheckOutDate);
			
			res.clickOnFindRooms(driver, test_steps);
			boolean flag1=res.verify_OverBookPopupForSplitRes(driver, test_steps, RoomClassName);


			if(flag1) {
				String[] testcase1 = TestCaseID.split(Utility.DELIM);
				for (int i = 0; i < testcase1.length; i++) {
					statusCode.add(i, "1");
				}
				comments="successfully verified overbooking popup in chnage stay details page";
			}else {
				comments="Assertion Error failed to verify overbooking popup in chnage stay details page";	
			}



		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation ", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			nav.Inventory(driver);
			test_steps.add("Navigate to Inventory");
			app_logs.info("Navigate to Inventory");

			// create new method 
			getTest_Steps.clear();
			getTest_Steps = nav.secondaryRatesMenuItem(driver);
			test_steps.addAll(getTest_Steps);

			// start new method for delete rate
			getTest_Steps.clear();
			getTest_Steps = rate.deleteRates(driver, RateName);
			getTest_Steps.addAll(getTest_Steps);

			test_steps.add("New Rate has been Deleted successfully");
			app_logs.info("New Rate has been Deleted successfully");

			rate.verifyDeleteRate(driver, RateName);
			test_steps.add("Verify the Deleted Rate : " + RateName);
			app_logs.info("Verify the Deleted Rate " + RateName);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete a rate ", testName, "DeleteRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete a rate ", testName, "DeleteRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try{
			app_logs.info("======Delete RoomClass=======");
			test_steps.add("======Delete RoomClass=======");
			nav.Setup(driver);
			nav.RoomClass(driver);


			/*	roomClass.searchClass(driver, tempraryRoomClassName);
			app_logs.info("Search");
			roomClass.deleteRoomClass(driver, tempraryRoomClassName);
			 */	
			roomClass.deleteRoomClassIfExist(driver, test_steps, tempraryRoomClassName);

			test_steps.add("Sccessfully Deleted RoomClass  : " + RoomClassName);
			app_logs.info("Sccessfully Deleted RoomClass :  " + RoomClassName);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class ", testName, "RoomClass Delete", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class successfully", testName, "RoomClass Delete",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		 }
	}

	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided

		return Utility.getData("VerifyOverbookingOnCopyingReser", SanityExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.close();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
