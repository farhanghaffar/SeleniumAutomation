package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class VerifyPolicyEffectsOnReservations extends TestCore {

	private WebDriver driver = null;

	static ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	ArrayList<String> totalNights = new ArrayList<>();
	ArrayList<String> roomNumber = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";
	
	static ExtentTest test;
	static ExtentReports report;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "Inventory")
	public void VerifyPolicyEffectsOnreservations(
			String TestCaseID,String caseName, String displayName, String Policy_T,
			 String ratePlan,String source,String firstName,String lastName,String roomChargePercentage , String roomCharge,
			 String IsAssign, String isChecked, String checkInPolicType,String checkInPolicyName,
			 String cancelationPolicyName,  String paymentType, String cardNumber, String nameOnCard,
			 String cardExpDate, String policyAttr2, String policyAttrValue2,String Action,
			String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,
			String RoomClass,String Salutation,String Account,
			String Referral,String Description
			) throws ParseException, Exception {
		if(Utility.getResultForCase(driver, TestCaseID)) {
		{
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
				comments="retest";
			}
			
		}

		String [] roomClass = RoomClass.split("\\|");
		
		String testName = caseName;
		test_description = "Verify the policies from Account/groups<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+TestCaseID+"' target='_blank'>"
				+ "Click here to open TestRail: C"+TestCaseID+"</a>";
		test_catagory = "Policy";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("EXECUTING: TEST Id's : " + TestCaseID);
		app_logs.info("##################################################################################");

		//MBR Reservation
		Double depositAmount=0.0;
		Double paidDeposit=0.0;
		String reservation=null;
		String status=null;
		ArrayList<String> getTest_steps = new ArrayList<>();
		
		CPReservationPage cpRes = new CPReservationPage();
		Navigation navigation = new Navigation();
		Policies policies = new Policies();
		Folio folio = new Folio();
		ReservationHomePage reservationHomePage = new ReservationHomePage();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		String tempraryRoomClassName = roomClass[0];
		String randomNumber = Utility.GenerateRandomNumber();
		roomClass[0] = roomClass[0] + randomNumber;
		displayName = roomClass[0];
		lastName = lastName + randomNumber;
		firstName = firstName + randomNumber;
		Double expectedAmount = 0.0 ;
		String policyText = Policy_T + "_Text";
		String policyDesc = Policy_T + "_Description";
		Policy_T = Policy_T + randomNumber;
		checkInPolicyName = checkInPolicyName + randomNumber;
		cancelationPolicyName = cancelationPolicyName + randomNumber;
		String randomNum = Utility.GenerateRandomNumber(3);
		String adult = "2";
		String children = "1";
		String reservationNumber1 = "";
		String checkInAttributeName = "authorize";
		String checkInAttributeValue = "20";
		
		
				
	
		//Login
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			driver = getDriver();
//			login_CP(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "CP_Login"));
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {	e.printStackTrace();
		
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {	e.printStackTrace();
		
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		
		
		//Policy Deletion
		try {
		app_logs.info("Navigating to Inventory");
		navigation.Inventory(driver, testSteps);
		
		testSteps.add("Navigating to policy");
		app_logs.info("Navigating to policy");
		navigation.policies(driver);
		testSteps.add("Searching For All policies");
		app_logs.info("Searching For All policies");
		
		
		getTest_steps.clear();
		policies.Search_All_Policy_U(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		app_logs.info("All policies Search Complete");
		
			
		int size = policies.Check_Size_Of_Policies_Rows(driver);
		if(size>0) {
			testSteps.add("Deleting policies");
			app_logs.info("Deleting policies");
			policies.DeleteAllPolicies(driver,getTest_steps);
		}
		else {
			testSteps.add("No policies Found For Deletion");
			app_logs.info("No policies Found For Deletion");
		}
	} catch (Exception e) {

		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Delete Policy", testName, "Property", driver);
		} else {
			Assert.assertTrue(false);
		}

	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Failed to Delete Policy", testName, "Property", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

		//Policy Creation
		
		try {
			if(TestCaseID.equalsIgnoreCase("848733")) {
				getTest_steps.clear();
				//policies.Cancellation_policy_Attributes(driver, "Fixed Amount", roomChargePercentage, policyAttr2, policyAttrValue2, getTest_steps);
				policies.createPolicy_U(driver, getTest_steps, Action+" Policy"+randomNum, Action, "Fixed Amount", roomChargePercentage, policyAttr2, policyAttrValue2, source, roomCharge, tempraryRoomClassName, ratePlan, policyText, policyDesc);
				testSteps.addAll(getTest_steps);
			}
			else if (TestCaseID.equalsIgnoreCase("848731")) {
				getTest_steps.clear();
				policies.createPolicy_U(driver, getTest_steps, Action+" Policy"+randomNum, Action, "Total Charges", roomChargePercentage, policyAttr2, policyAttrValue2, source, roomCharge, tempraryRoomClassName, ratePlan, policyText, policyDesc);
				testSteps.addAll(getTest_steps);
			}
			else if (TestCaseID.equalsIgnoreCase("848699")||TestCaseID.equalsIgnoreCase("848690")) {
				getTest_steps.clear();
				policies.createPolicyCheckIn_U(driver, getTest_steps, checkInPolicyName+" with"+checkInAttributeName+" "+checkInAttributeValue, checkInPolicType, checkInAttributeName, checkInAttributeValue, policyAttr2, policyAttrValue2, source, checkInPolicType, tempraryRoomClassName, ratePlan, policyText, policyDesc);
				testSteps.addAll(getTest_steps);				
			}
			else  {
				getTest_steps.clear();
				policies.createPolicy_U(driver, getTest_steps, Action+" Policy"+randomNum, Action, roomCharge, roomChargePercentage, policyAttr2, policyAttrValue2, source, roomCharge, tempraryRoomClassName, ratePlan, policyText, policyDesc);
				testSteps.addAll(getTest_steps);
			}
			
	} catch (Exception e) {

		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Create policy", testName, "Property", driver);
		} else {
			Assert.assertTrue(false);
		}

	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			Utility.updateReport(e, "Failed to Create policy", testName, "Property", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
		
		
		try {
			navigation.Reservation(driver);
			
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to reservation", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate to reservation", testName, "Property", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		
		//reservation
		try {
			Wait.waitUntilPageLoadNotCompleted(driver, statusCode200);
			testSteps.add("==========CREATE NEW RESERVED RESERVATION==========");
			app_logs.info("==========CREATE NEW RESERVED RESERVATION==========");

			getTestSteps.clear();
			Thread.sleep(4000);
			app_logs.info("Clicking on New Reservation");
			getTestSteps = reservationPage.click_NewReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Check In Date");
			app_logs.info("Entering Check In Date");
			getTestSteps = reservationPage.checkInDate(driver, 0);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Check Out Date");
			app_logs.info("Entering Check Out Date");
			getTestSteps = reservationPage.checkOutDate(driver, +1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Adult Value");
			app_logs.info("Entering Adult Value");
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			testSteps.add("Entering Children Value");
			app_logs.info("Entering Children Value");
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			
			getTestSteps.clear();
			testSteps.add("Entering Rateplan");
			app_logs.info("Entering Rateplan");
			getTestSteps = reservationPage.select_Rateplan(driver, getTest_steps, ratePlan, PromoCode);
			testSteps.addAll(getTestSteps);
			
			
			getTestSteps.clear();
			app_logs.info("Clicking On Find Room");
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			

			testSteps.add("Filling All Fullfil Values");
			app_logs.info("Filling All Fullfil Values");
			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, tempraryRoomClassName, IsAssign);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);					
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, "Mr.",firstName, lastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			reservationPage.enterPaymentDetails(driver, getTestSteps, paymentType, cardNumber, "Test Useer", cardExpDate);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, "GDS");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.add("Successfully click Book Now");
			
			getTestSteps.clear();
			reservationNumber1 = reservationPage.getReservationConfirmationNumber(driver, getTestSteps);
			testSteps.add("Reservation confirmation number: " + reservationNumber1);
			app_logs.info("Reservation confirmation number: " + reservationNumber1);
			
			getTestSteps.clear();
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			testSteps.add("Sucessfully close save quote popup");
		} catch (Exception e) {
			e.printStackTrace();
			
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create A Reservation", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create A Reservation", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		////////////////////
		//848690
		///////////////////
		if(TestCaseID.equalsIgnoreCase("848690")) {
		
		try {
			
			
			
		Thread.sleep(1000);
		getTest_steps.clear();
		folio.click_Folio(driver, getTest_steps);
		testSteps.addAll(getTest_steps);	
			
		Thread.sleep(1000);
		getTest_steps.clear();
		String totalBalance = folio.getTotalBalance(driver);
		testSteps.addAll(getTest_steps);
		
		
		String ExpectedAmount = String.valueOf((Double.valueOf(totalBalance) *  Double.valueOf(checkInAttributeValue))/100); 
		
			
		Thread.sleep(1000);
		getTest_steps.clear();
		reservationPage.clickCheckInButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		
		getTest_steps.clear();
		reservationPage.disableGenerateGuestReportToggle(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationPage.clickOnProceedToCheckInPaymentButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationHomePage.clickOnDirtyPopUp(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		
		getTest_steps.clear();
		reservationHomePage.VerifyChechInPaymentPopup(driver, getTest_steps,ExpectedAmount);
		testSteps.addAll(getTest_steps);
		
		
		testSteps.add("Verify while creating the single/split reservation, for the check-in policy if the policy attributes are been applied, the \"Credit card\" is required error is not thrown in subsequent, when user is swiping the card");
		} catch (Exception e) {
		e.printStackTrace();
		
		if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		Utility.updateReport(e, "Failed to Verify while creating the single/split reservation, for the check-in policy if the policy attributes are been applied, the \"Credit card\" is required error is not thrown in subsequent, when user is swiping the card", testName, "InventoryNavigation",
				driver);
		} else {
		Assert.assertTrue(false);
		}
		} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		Utility.updateReport(e, "Failed to Verify while creating the single/split reservation, for the check-in policy if the policy attributes are been applied, the \"Credit card\" is required error is not thrown in subsequent, when user is swiping the card", testName, "InventoryNavigation",
				driver);
		} else {
		Assert.assertTrue(false);
		}
		}
		}
		
		
		
		
	////////////////////
	//848699
	///////////////////
	if(TestCaseID.equalsIgnoreCase("848699")) {
	
	try {
		
		Thread.sleep(1000);
		getTest_steps.clear();
		folio.click_Folio(driver, getTest_steps);
		testSteps.addAll(getTest_steps);	
			
		Thread.sleep(1000);
		getTest_steps.clear();
		String totalBalance = folio.getTotalBalance(driver);
		testSteps.addAll(getTest_steps);
		
		
		String ExpectedAmount = String.valueOf((Double.valueOf(totalBalance) *  Double.valueOf(checkInAttributeValue))/100); 
		
		
		Thread.sleep(1000);
		getTest_steps.clear();
		reservationPage.clickCheckInButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		
		getTest_steps.clear();
		reservationPage.disableGenerateGuestReportToggle(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationPage.clickOnProceedToCheckInPaymentButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationHomePage.clickOnDirtyPopUp(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		
		getTest_steps.clear();
		reservationHomePage.VerifyChechInPaymentPopup(driver, getTest_steps,ExpectedAmount);
		testSteps.addAll(getTest_steps);
		
		
		
		Thread.sleep(1000);
		getTest_steps.clear();
		reservationPage.clickCheckOutButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationPage.disableGenerateGuestReportToggle(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		getTest_steps.clear();
		reservationPage.clickOnProceedToCheckOutPaymentButton(driver, getTest_steps);
		testSteps.addAll(getTest_steps);
		
		
		
		getTest_steps.clear();
		reservationHomePage.VerifyChechOutPaymentPopup(driver, getTest_steps,String.valueOf(Double.valueOf(totalBalance)) );
		testSteps.addAll(getTest_steps);

	
	testSteps.add("Verify Authorization is considered in Check out and Captured the same when it comes from Check In Policy");
	} catch (Exception e) {
	e.printStackTrace();
	
	if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		Utility.updateReport(e, "Failed to Verify Authorization is considered in Check out and Captured the same when it comes from Check In Policy", testName, "InventoryNavigation",
				driver);
	} else {
		Assert.assertTrue(false);
	}
	} catch (Error e) {
	e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		Utility.updateReport(e, "Failed to Verify Authorization is considered in Check out and Captured the same when it comes from Check In Policy", testName, "InventoryNavigation",
				driver);
	} else {
		Assert.assertTrue(false);
	}
	}
	}

	
	
		
		
	////////////////////
	//825499
	///////////////////
	if(TestCaseID.equalsIgnoreCase("825499")) {
		String folioAmount = "200.00"; 
		try {
			getTest_steps.clear();
			folio.click_Folio(driver, getTest_steps);
			testSteps.addAll(getTest_steps);
			
			getTest_steps.clear();
			folio.clickAddButton(driver);
			testSteps.addAll(getTest_steps);
			
			getTest_steps.clear();
			folio.AddFolioLineItem(driver, "Parking", folioAmount);
			testSteps.addAll(getTest_steps);
			
			getTest_steps.clear();
			folio.ClickSaveFolioButton(driver);
			testSteps.addAll(getTest_steps);
			
			getTest_steps.clear();
			folio.CancelReservation(driver, getTest_steps, folioAmount);
			testSteps.addAll(getTest_steps);
			
			testSteps.add("Verify cancelling a reservation with high folio line items with cancellation policy");
		} catch (Exception e) {
			e.printStackTrace();
			
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify cancelling a reservation with high folio line items with cancellation policy", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify cancelling a reservation with high folio line items with cancellation policy", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
		
		
		
		
		
		////////////////////
		//848733
		///////////////////
		if(TestCaseID.equalsIgnoreCase("848733")) {
			try {
				if(Action.equalsIgnoreCase("Cancellation")) {
					Double RoomChargeAmount = Double.parseDouble(cpRes.get_RoomCharge_U(driver, getTest_steps));
					printString("Room Charge: "+RoomChargeAmount);
					expectedAmount = (RoomChargeAmount*10)/100;
					printString("Expected Amount: "+expectedAmount);
					testSteps.addAll(getTest_steps);
				}
				
				
				testSteps.add("Navigating to Reservation");
				app_logs.info("Navigating to Reservation");
				navigation.reservation(driver);
		        Thread.sleep(1000);
		        
		        testSteps.add("==========BULK ACTION==========");
				app_logs.info("==========BULK ACTION==========");
				
				Utility.refreshPage(driver);
				
				getTestSteps.clear();
				reservationSearch.basicSearchWithResNumber(driver,getTest_steps, reservationNumber1, false);
				testSteps.addAll(getTestSteps);
				app_logs.info("Searching Reservation By Created Reservation Number");
				
				 if(Action.equalsIgnoreCase("Cancellation")) {
					getTestSteps.clear();
					getTestSteps = reservationSearch.bulkActionCancellation_U(driver);
					testSteps.addAll(getTestSteps);
				}
				
				//GuestName Verification
				String guestNameString = firstName.trim()+" "+lastName.trim();
				printString(guestNameString);
				Assert.assertEquals(reservationSearch.getGuestName(driver), guestNameString, "Failed : Guest name didn't match");
				testSteps.add("Verified Bulk "+Action+" GuestName=> "+guestNameString);
				app_logs.info("Verified Bulk "+Action+" GuestName=> "+guestNameString);
				
				getTestSteps.clear();
				reservationPage.close_FirstOpenedReservation(driver, getTestSteps);
				testSteps.addAll(getTest_steps);
				
				Utility.refreshPage(driver);
				
				getTestSteps.clear();
				reservationSearch.basicSearchWithResNumber(driver,getTestSteps, reservationNumber1, true);
				testSteps.addAll(getTestSteps);
				app_logs.info("Searching Reservation By Created Reservation Number");
				
				getTestSteps.clear();
				reservationPage.click_Folio(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
				app_logs.info("Searching Reservation By Created Reservation Number");
				
				int NumberOfRowsAfterCancellation =  folio.getRowCount(driver, testSteps);
				testSteps.add("Number Of Line Item After Bulk Cancellation: <b>"+NumberOfRowsAfterCancellation);
				
				String TotalBalance =  folio.getTotalBalance(driver);
				testSteps.add("Total Balance After Bulk Cancellation Action: "+TotalBalance);
				
				testSteps.add("Verify Bulk Cancel policy when cancellation policy is created with fixed amount <b>"+roomChargePercentage+" along with Beyond <b>"+policyAttrValue2+"<b> days of reservation");
			} catch (Exception e) {
				e.printStackTrace();
				
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Bulk Cancel", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Bulk Cancel", testName, "InventoryNavigation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		
		////////////////////////////////
	////////////////////
	//848731
	///////////////////
	if(TestCaseID.equalsIgnoreCase("848731")) {
		try {
			if(Action.equalsIgnoreCase("Cancellation")) {
				Double RoomChargeAmount = Double.parseDouble(cpRes.get_RoomCharge_U(driver, getTest_steps));
				printString("Room Charge: "+RoomChargeAmount);
				expectedAmount = (RoomChargeAmount*10)/100;
				printString("Expected Amount: "+expectedAmount);
				testSteps.addAll(getTest_steps);
			}
			
			
			testSteps.add("Navigating to Reservation");
			app_logs.info("Navigating to Reservation");
			navigation.reservation(driver);
	      Thread.sleep(1000);
	      
	      testSteps.add("==========BULK ACTION==========");
			app_logs.info("==========BULK ACTION==========");
			
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			reservationSearch.basicSearchWithResNumber(driver,getTest_steps, reservationNumber1, false);
			testSteps.addAll(getTestSteps);
			app_logs.info("Searching Reservation By Created Reservation Number");
			
			 
			getTestSteps.clear();
			getTestSteps = reservationSearch.bulkActionCancellation_U(driver);
			testSteps.addAll(getTestSteps);
			
			
			//GuestName Verification
			String guestNameString = firstName.trim()+" "+lastName.trim();
			printString(guestNameString);
			Assert.assertEquals(reservationSearch.getGuestName(driver), guestNameString, "Failed : Guest name didn't match");
			testSteps.add("Verified Bulk "+Action+" GuestName=> "+guestNameString);
			app_logs.info("Verified Bulk "+Action+" GuestName=> "+guestNameString);
			
			getTestSteps.clear();
			reservationPage.close_FirstOpenedReservation(driver, getTestSteps);
			testSteps.addAll(getTest_steps);
			
			Utility.refreshPage(driver);
			
			getTestSteps.clear();
			reservationSearch.basicSearchWithResNumber(driver,getTestSteps, reservationNumber1, true);
			testSteps.addAll(getTestSteps);
			app_logs.info("Searching Reservation By Created Reservation Number");
			
			getTestSteps.clear();
			reservationPage.click_Folio(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			app_logs.info("Searching Reservation By Created Reservation Number");
			
			int NumberOfRowsAfterCancellation =  folio.getRowCount(driver, testSteps);
			testSteps.add("Number Of Line Item After Bulk Cancellation: <b>"+NumberOfRowsAfterCancellation);
			
			String TotalBalance =  folio.getTotalBalance(driver);
			testSteps.add("Total Balance After Bulk Cancellation Action: "+TotalBalance);
			
			testSteps.add("Verify Bulk Cancel policy when cancellation policy is created with room charges/Total charges along with Beyond <b>"+policyAttrValue2+"<b> days of reservation");
		} catch (Exception e) {
			e.printStackTrace();
			
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Bulk Cancel policy when cancellation policy is created with room charges/Total charges along with beyond days", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Bulk Cancel policy when cancellation policy is created with room charges/Total charges along with beyond days", testName, "InventoryNavigation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	statusCode = new ArrayList<String>();
	caseId = new ArrayList<String>();
	String[] testcase = TestCaseID.split("\\|");
	for (int i = 0; i < testcase.length; i++) {
		caseId.add(testcase[i]);
		statusCode.add("1");
		comments = "pass";
	}
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("verifypolicyeffects", HS_EXCEL);
	}


	@AfterMethod(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}


