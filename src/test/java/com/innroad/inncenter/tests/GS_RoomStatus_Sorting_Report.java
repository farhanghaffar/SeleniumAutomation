package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.WebElements_RoomStatus;

public class GS_RoomStatus_Sorting_Report extends TestCore {

	private WebDriver driver = null;
	 ArrayList<String> test_steps = new ArrayList<>();
	 ArrayList<String> TestName = new ArrayList<>();
	 ArrayList<String> TestCategory = new ArrayList<>();
	 ArrayList<String> TestDescription = new ArrayList<>();
	 String testName = "";
	 String test_description = "";
	 String test_catagory = "";
	public static String roomClassNames;
	public static String roomClassAbbrivations;
	boolean isFail = false;  
	HashMap<String, String> roomNumbers = new HashMap<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	String comment=null;
	
	Utility utility = new Utility();
	RoomStatus roomStatus = new RoomStatus();
	Navigation Nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	String  zoneName = null;
	HashMap<String, String> zoneList= new HashMap<String, String>();
	ArrayList<String> roomClassAndZone= new ArrayList<String>();
	ArrayList<String> sortedZoneNameList  = new ArrayList<String>();
	HashMap <String , String> roomClassAndZoneList= new HashMap<String, String>();
	String [] zonesList= null;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	
	@Test(dataProvider = "getData",groups = "GuestServices")
	public void verifyReportsRoomStatus_SortingByRoomNumber(String roomClassName, String roomClassAbbrivation, String maxAdults, String maxPersopns,
			String roomQuantity,String zone) throws InterruptedException, ParseException {
		String testCaseID="848761|848762|848371|848227|848201";
		if(Utility.getResultForCase(driver, testCaseID)) {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("848761|848762|848371|848227|848201", Utility.testId, Utility.statusCode,utility.comments,"");
		testName = "GS_RoomStatus_Sorting_Report";
		test_description = "GS_RoomStatus <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/16942' target='_blank'>"
				+ "Click here to open TestRail: C16942</a><br>";
		test_catagory = "GuestSercices";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		// Login
		try
		{
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			
			driver = getDriver();
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		
		}
		catch (Exception e) {
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
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "GS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		
		}
		
		// Click Guest Services
		try {
		
			Nav.navigateGuestservice(driver);
			test_steps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.verifyAllQuickStatColor(driver, test_steps);
	} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to click Guest Service Menu Item", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to click Guest Service Menu Item", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		try {			
			String testCase="Verified Search Text Box";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			roomstatus.Verify_SearchTestBox_Presented(driver, test_steps);
	RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		}
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search invalid criteria", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search invalid criteria", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {			
			String testCase="Room Status Search Invalid Data";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			roomstatus.searchInvalid(driver, "cxcxcxcxv", "No results found for the selected criteria and property", test_steps);
		 Nav.navigateToReservationFromGuestServices(driver, test_steps);
		 Nav.navigateGuestservice(driver);
		 roomstatus.verifyRoomStatusTabEnabled(driver,test_steps);
		 roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
     		RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		}
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search invalid criteria", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search invalid criteria", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Click Sort By Room and Select Room Status
		try {
			test_steps.add("======Start sorting by room number======");
			roomStatus.verifySortFuntionality(driver,test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Sort by Room Number", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Sort by Room Number", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			Wait.wait5Second();
			roomStatus.getSortingOrderINArray(driver, test_steps);

			test_steps.add("======Start Generating Report======");
			roomStatus.Reports(driver);
			test_steps.add("Generate Report");
			app_logs.info("Generate Report");
		
		}

		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try
		{
		
			roomStatus.reportRoomStatusVerification(driver,test_steps);
			test_steps.add("Verify sort order of room class in report");
			app_logs.info("Verify sort order of room class in report");
			roomStatus.roomStatusReport_Verification(driver, test_steps);
		
	/*	test_steps.add("GS-Room Status-Verification of quick stats and sort functionality"+
		"<a href='https://innroad.testrail.io/index.php?/cases/view/848371' target='_blank'>"
		+ "Click here to open TestRail: 848371</a><br>");	
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify the fields present in the report generated");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
		
		test_steps.add("Check the housekeeping report"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/848227' target='_blank'>"
					+ "Click here to open TestRail: 848227</a><br>");	
			
			Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify the fields present in the report generated");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
			
			test_steps.add("GS-Room Status-Verification of quick stats and sort functionality"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/848201' target='_blank'>"
					+ "Click here to open TestRail: 848201</a><br>");	
			
			Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify the fields present in the report generated");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
			
		*/	roomStatus.verifyandClickPrintButton(driver, test_steps);
			roomStatus.switchToParentWindowTab(driver);
			Nav.navigateToReservationFromGuestServices(driver, test_steps);
			/*test_steps.add("Report:Verify that if you click on print option , and navigate back to others tab must be functional."+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/848761' target='_blank'>"
					+ "Click here to open TestRail: 848761</a><br>");		   
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the fields present in the report generated");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		*/	Nav.navigateGuestservice(driver);
			test_steps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);		
			roomStatus.switchToNextTab(driver);
			roomStatus.cancelPrintPopup(driver, test_steps);
			roomStatus.switchToParentWindowTab(driver);
			Nav.navigateToReservationFromGuestServices(driver, test_steps);
			/*test_steps.add("Report:Verify that if you cancel that tab and navigate back , all the icon should be clickable."+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/848762' target='_blank'>"
					+ "Click here to open TestRail: 848762</a><br>");		   
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the fields present in the report generated");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		*/	 for(int i=0;i<Utility.statusCode.size();i++) {
			Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Rooms Status Sorting");
		}
			RetryFailedTestCases.count = Utility.reset_count;
			 Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		}
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				isFail = true;
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify sort order of room class in report ", "GS_RoomStatus", "RoomStatus", driver);			
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				isFail = true;
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify sort order of room class in report ", "GS_RoomStatus", "RoomStatus", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("GS_RoomStatus_Sorting_Report", gsexcel);

	}


	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
			
	}
}
