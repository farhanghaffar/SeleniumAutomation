package com.innroad.inncenter.tests;
import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
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
public class GSVerifyRoomClass extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyRoomClass(String roomClassName, String maxAdults,
			String maxPersopns, String roomQuantity) throws ParseException {
		String testCaseID="850174|848223";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GSVerifyRoomClass";
		test_description = "GSVerifyRoomClass<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850174' target='_blank'>"
				+ "Click here to open TestRail: 850174</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848223' target='_blank'>"
				+ "Click here to open TestRail: 848223</a><br>";
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850174|848223", Utility.testId, Utility.statusCode, Utility.comments, "");
		Navigation navigation = new Navigation();
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		RoomStatus roomstatus = new RoomStatus();
		String roomClassNames = null;
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			
				driver = getDriver();
			login_GS(driver);
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
			newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults,
					maxPersopns, roomQuantity);
			testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
			app_logs.info("Room Class Created: " + roomClassNames);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		}

		// Search Room Class
		try {
			testSteps.add("<b>======Start Searching By RoomClass and Verify Room Class Status ======</b>");
			navigation.navigateGuestservicesAfterrateGrid(driver);
			testSteps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
		
			testSteps.add("Verify House Keeping Status tab"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848223' target='_blank'>"
					+ "Click here to open TestRail: 848223</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify House Keeping Status tab");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
			roomstatus.searchByRoom(driver, roomClassNames, testSteps);
			testSteps.add("Successfull Dispalyed RoomClassName: <b>" + roomClassNames + " On GS</b>");
			app_logs.info("Successfull Dispalyed RoomClassName: " + roomClassNames + " On GS");
			roomstatus.verifyRoomTileStatus(driver, Utility.RoomNo, roomClassNames, "Clean", testSteps);
			
			testSteps.add("Create a room class in innCenter and verify the housekeeping status"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850174' target='_blank'>"
					+ "Click here to open TestRail: 850174</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Create a room class in innCenter and verify the housekeeping status");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Room Class and Verify Status", "GS", "GS", testName, test_description, test_catagory,
					testSteps);
		}		
		// Delete Room Class
				try {
					testSteps.add("<b>======Delete Room Class======</b>");
					navigation.navigateToSetupfromTaskManagement(driver);
					testSteps.add("Navigated to Setup");
					navigation.RoomClass(driver);
					newRoomClass.searchRoomClassV2(driver, roomClassName);
					testSteps.add("Click on Search Button");
					app_logs.info("Click on Search Button");
					newRoomClass.deleteAllRoomClassV2(driver, roomClassName);
					testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
					app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Delete Room Class", "GS", "GS", testName, test_description,
							test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Delete Room Class", "GS", "GS", testName, test_description, test_catagory,
							testSteps);
				}
	}}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyRoomClass", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
