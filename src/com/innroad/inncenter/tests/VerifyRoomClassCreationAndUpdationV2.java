package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pages.NewRoomClassPageObjectV2;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;

public class VerifyRoomClassCreationAndUpdationV2 extends TestCore {
	// Automation-1922

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}
	private WebDriver driver_VRAP = null;

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();


	// Create room class 
	@Test(dataProvider = "getData")
	public void verifyRoomClassCreationAndUpdationV2(String roomClassName, String roomClassAB, String policy, String sortOrder,
			String maxAdults, String maxPerson, String details, String roomQuant, String roomName,
			String statinId, String sortOrder1, String zone,String roomClass,String status) {

		String test_name = "VerifyRoomClassCreationAndUpdationV2";
		String test_description = "verify room classes creation and updation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554258' target='_blank'>"
				+ "Click here to open TestRail: C554258</a>";
		String test_catagory = "NewRoomClassV2";

		String testName = test_name;


		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		NewRoomClassesV2 obj1 = new NewRoomClassesV2();
		NewRoomClassPageObjectV2 obj2 = new NewRoomClassPageObjectV2();
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver_VRAP = getDriver();
			login_NONGS(driver_VRAP);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}
		//create new room class

		try {
			Elements_NewRoomClassPage roomClass1 = new Elements_NewRoomClassPage(driver_VRAP);
			Wait.WaitForElement(driver_VRAP, OR.setUP);
			roomClass1.setUP.click();
			roomClass1.roomClasses.click();
			test_steps.add("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
			app_logs.info("=================== CREATING NEW ROOM CLASS WITH DESCRIPTION ======================");
			roomClass1.createnewRoomClass.click();
			test_steps.add("Clicking On New Room Class");
			app_logs.info("Clicking On New Room Class");
			getTest_Steps.clear();
			getTest_Steps=obj1.createRoomClassWithDescriptionV2(driver_VRAP, getTest_Steps, roomClassName, roomClassAB, policy, sortOrder, maxAdults, maxPerson, details, roomQuant, roomName, statinId, sortOrder1, zone);
			test_steps.addAll(getTest_Steps);
			test_steps.add(" New Room Class Created");
			app_logs.info(" New Room Class Created");
		} 
		catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "create new room class", testName, "RoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new room class", testName, "RoomClass", driver_VRAP);
			}
		}

		//			//navigation to update new room class page and updation
		try {

			test_steps.add("=================== UPDATE ROOM CLASS ======================");
			app_logs.info("===================  UPDATE ROOM CLASS ======================");
			driver_VRAP.navigate().to("https://app.qainnroad.com/setup/roomclasses");
			getTest_Steps.clear();
			getTest_Steps=  obj1.updateRoomClassAbbrV2(driver_VRAP, getTest_Steps, roomClass, roomClassAB);
			test_steps.addAll(getTest_Steps);
			test_steps.add("  Room Class Abbrevation Updated ");
			app_logs.info("  Room Class Abbrevation Updated");
			driver_VRAP.navigate().to("https://app.qainnroad.com/setup/roomclasses");
			getTest_Steps.clear();
			getTest_Steps= obj1.updateRoomClassMaxAdutlsAndPersonsV2(driver_VRAP, getTest_Steps, roomClass, maxAdults, maxPerson);
			test_steps.addAll(getTest_Steps);
			test_steps.add("  Room Class Max Adult And Person Updated ");
			app_logs.info("  Room Class Max Adult And Person Updated");
			driver_VRAP.navigate().to("https://app.qainnroad.com/setup/roomclasses");
			getTest_Steps.clear();
			getTest_Steps= obj1.updateRoomClassSmokeingPolicyV2(driver_VRAP, getTest_Steps, roomClass, policy);
			test_steps.addAll(getTest_Steps);
			test_steps.add("  Room Class Policy Updated ");
			app_logs.info("  Room Class Policy Updated");
			driver_VRAP.navigate().to("https://app.qainnroad.com/setup/roomclasses");
			getTest_Steps.clear();
			getTest_Steps= obj1.updateRoomClassNameV2(driver_VRAP, getTest_Steps, roomClass, roomClassName);
			test_steps.addAll(getTest_Steps);
			test_steps.add("  Room Class Name Updated ");
			app_logs.info("  Room Class Name Updated");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, " update room class", testName, "RoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "failed to update room class", testName, "RoomClass", driver_VRAP);
			}
		}
		//getting room classes list and abb
		try {
			driver_VRAP.navigate().to("https://app.qainnroad.com/setup/roomclasses");

			obj1.getAllRoomClassesListV2(driver_VRAP, getTest_Steps);
			test_steps.add("=================== All Room Classes List Obtained Sucessfully ======================");
			app_logs.info("===================  All Room Classes List Obtained Sucessfully ======================");

			obj1.getAllActiveRoomClassesListV2(driver_VRAP, getTest_Steps, status);
			test_steps.add("=================== All Active Room Classes List Obtained Sucessfully ======================");
			app_logs.info("=================== All ActiveRoom Classes List Obtained Sucessfully ======================");

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "geeting room classes list", testName, "RoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "failed to get room classes list", testName, "RoomClass", driver_VRAP);
			}
		}
	}




	@DataProvider
	public Object[][] getData() {

		return Utility.getData("verifyRoomClassCreationUpdation", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closedriver_VRAP() {
		driver_VRAP.quit();
	}

}