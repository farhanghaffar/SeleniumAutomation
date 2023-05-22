package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyNewRoomClassV2CreationAndDeletion extends TestCore {

	// Before Test
	private WebDriver driver_VRAP = null;

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	// Create room class if not exist
	@Test(dataProvider = "getData", groups = "RoomClasses")
	public void verifyNewRoomClassV2CreationAndDeletion( String roomClassName, 
			String roomClassAbbreviation, 
			String maxAdults, 
			String maxPersons, 
			String roomQuantity) {

		String test_name = "VerifyNewRoomClassV2CreationAndDeletion";
		String test_description = "Create new room classes";
		String test_catagory = "Room Classes";

		String testName = test_name;
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation nav = new Navigation();
		NewRoomClassesV2 roomClass = new NewRoomClassesV2();
		String randomNumber = Utility.GenerateRandomNumber();
		roomClassName = roomClassName + randomNumber;

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			//Login
			driver_VRAP = getDriver();
			login_CP(driver_VRAP);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "login_CP", "Login", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "login_CP", "Login", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create a new room class
		try {
			app_logs.info("******************************** CREATE A NEW ROOM CLASS ******************************** ");
			test_steps.add("******************************** CREATE A NEW ROOM CLASS ******************************** ");
			
			nav.mainSetupManu(driver_VRAP);
			app_logs.info("Navigated Setup menu");
			test_steps.add("Navigated Setup menu");

			nav.navigateRoomClass(driver_VRAP);
			app_logs.info("Navigated to Room Class page");
			test_steps.add("Navigated to Room Class page");

			getTest_Steps.clear();
			getTest_Steps = roomClass.createRoomClassV2(driver_VRAP, getTest_Steps, roomClassName, roomClassAbbreviation, maxAdults, maxPersons, roomQuantity);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Successfully Created New RoomClass  : "+roomClassName);
			app_logs.info("Successfully Created New RoomClass :  "+roomClassName);

			Utility.ClassesName.add(roomClassName);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Close room class tab / window
		try {			
			app_logs.info("******************************** CLOSE ROOM CLASS TAB ******************************** ");
			test_steps.add("******************************** CLOSE ROOM CLASS TAB ******************************** ");
			
			getTest_Steps.clear();
			getTest_Steps = roomClass.closeRoomClassTabV2(driver_VRAP, roomClassName);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Successfully closed the room class tab  : "+roomClassName);
			app_logs.info("Successfully Created New RoomClass :  "+roomClassName);
		}  catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to close the room class tab ", testName, "RoomClassTab",driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to close the room class tab ", testName, "RoomClassTab",driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete room class
		try {
			app_logs.info("******************************** DELETE ROOM CLASS ******************************** ");
			test_steps.add("******************************** DELETE ROOM CLASS ******************************** ");
			
			nav.InventoryV2(driver_VRAP);
			test_steps.add("Navigated Inventory");
			app_logs.info("Navigated Inventory");

			nav.mainSetupManu(driver_VRAP);
			app_logs.info("Navigated Setup");
			test_steps.add("Navigated Setup");

			nav.navigateRoomClass(driver_VRAP);
			app_logs.info("Navigated Room Class");
			test_steps.add("Navigated Room Class");

			getTest_Steps.clear();
			getTest_Steps = roomClass.deleteRoomClassV2(driver_VRAP, roomClassName);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Deleted RoomClass  : "+roomClassName);
			app_logs.info("Successfully Deleted RoomClass :  "+roomClassName);
			Utility.ClassesName.add(roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class successfully", testName, "NewRoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class successfully", testName, "NewRoomClass", driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Generate reports
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		}  catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to generate reports", testName, "reprots",driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to generate reports", testName, "reprots",driver_VRAP);
			} else {
				Assert.assertTrue(false);
			}
		}
	}


	@DataProvider
	public Object[][] getData() {
		return Utility.getData("NewRoomClassV2CreationDelete", envLoginExcel);
	}


	@AfterClass(alwaysRun = true)
	public void closedriver_VRAP() {
		driver_VRAP.quit();
	}

}
