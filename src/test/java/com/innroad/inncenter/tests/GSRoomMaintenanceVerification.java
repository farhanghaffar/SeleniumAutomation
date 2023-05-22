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
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class GSRoomMaintenanceVerification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	public static String test_description = null;
	public static String test_catagory = null;
	String testName;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	String randomNO=null;
	public static String roomClassNames=null;
	public static String roomClassAbbrivations=null;
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	ArrayList<String> roomNumbers = new ArrayList<>();
	RoomStatus roomstatus = new RoomStatus();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	String testCase=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSRoomMaintenanceVerification(String startDate, String endDate,String roomClassName, String roomClassAbbrivation, String maxAdults,
			String maxPersopns, String roomQuantity, String subject,String detail, String reason) throws ParseException {
	
		test_name = testName;
		testDescription = "GS RoomMaintenance Verification <br>";
		testCategory = "Verification";
		String testName = test_name;
		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
			//Login
				try {
					driver = getDriver();
					login_GS(driver);
					test_steps.add("Logged into the application");
					app_logs.info("Logged into the application");	
					} catch (Exception e) {			
					Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, test_steps);					
				}				
				//Create Room Class
				try {					
					navigation.setup(driver);
					app_logs.info("Navigated to Setup");
					navigation.roomClass(driver);
					app_logs.info("Navigated to Room Class");
					randomNO=Utility.generateRandomNumber();
					roomClassNames = roomClassName + randomNO;
					roomClassAbbrivations = roomClassAbbrivation + randomNO;
					newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames, roomClassAbbrivations, maxAdults, maxPersopns, roomQuantity);	
					String quantity = roomQuantity;
					String roomNumber = Utility.RoomNo;
					roomNumbers.add(roomNumber);
					for (int i = 1; i < Integer.parseInt(quantity); i++) {
						roomNumber = String.valueOf(Integer.parseInt(roomNumber) + 1);
						roomNumbers.add(roomNumber);
					}
					app_logs.info("Room NO Are: " + roomNumbers);
					newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
					test_steps.add("Room Class created successfully " + roomClassNames);
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);
					
				}				
				try {
					 testCase="Creating Room Maintenance";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					test_steps.add("<b>============Start Creating Room Maintenance============</b>");
					navigation.navigateGuestservicesAfterrateGrid(driver);
					test_steps.add("Navigated to Guestservices");
					roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
					roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
					navigation.RoomMaintenance(driver);
					test_steps.add("Navigated to RoomMaintenance");
					subject=subject+Utility.generateRandomNumber();
					roomMaintance = room_maintenance.createNewRoomOutOfOrder(driver, subject,
							roomNumbers.get(0), roomClassNames, test_steps, detail);					
					app_logs.info(roomMaintance);
					startDate=roomMaintance.get(0);
					endDate=roomMaintance.get(1);
					app_logs.info(startDate);
					app_logs.info(endDate);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Failed to Create Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed to Create Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
				}
				// Search 
				try {
					testCase="Search Room Maintenance By Room #";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					test_steps.add("<b>============Search Room Maintenance By Room # and Verify Room Maintenance============</b>");
					room_maintenance.searchRooms(driver, reason, roomNumbers.get(0),test_steps);
					room_maintenance.roomRoomMaintance(driver, test_steps, subject, reason, roomClassNames,roomNumbers.get(0), startDate, endDate, "MMM dd, yyyy");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}
					
					catch (Exception e) {			
						Utility.catchException(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
					}
				try {
					testCase="Search Room Maintenance By Date";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					
					test_steps.add("<b>============Search Room Maintenance By Date and Verify Room Maintenance============</b>");
					room_maintenance.searchByFromAndToDate(driver, test_steps, startDate, endDate, "dd/MM/yyyy", reason);
					room_maintenance.roomRoomMaintance(driver, test_steps, subject, reason, roomClassNames,roomNumbers.get(0), startDate, endDate, "MMM dd, yyyy");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}
				catch (Exception e) {			
					Utility.catchException(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
				}
				try {
					testCase="Search Room Maintenance By Reason";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					
					test_steps.add("<b>============Search Room Maintenance By Reason and Verify Room Maintenance============</b>");
					room_maintenance.searchUsingReason(driver, test_steps, reason);
					room_maintenance.roomRoomMaintance(driver, test_steps, subject, reason,roomClassNames, roomNumbers.get(0), startDate, endDate, "MMM dd, yyyy");
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}
					catch (Exception e) {			
						Utility.catchException(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
					}
				try {
					testCase="Search Room Maintenance By using Invalid Data";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					test_steps.add("<b>============Search Room Maintenance using Invalid Data and Verify Room Maintenance============</b>");
					room_maintenance.searchRooms(driver, reason, "errtty",test_steps);
					room_maintenance.noRoomMaintanceFound(driver, test_steps);
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify Search Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
				}
				
				try {
					testCase="Delete Room Maintenance";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}	
					test_steps.add("<b>============Delete Room Maintenance============</b>");
					room_maintenance.searchRooms(driver, reason, roomNumbers.get(0),test_steps);
					room_maintenance.delete_RoomItem(driver, roomClassNames, roomNumbers.get(0));
					test_steps.add("Delete Room Maintenance " + subject);
					app_logs.info("Delete Room Maintenance " + subject);
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Delete Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Delete Room Maintenance", "Room Maintenance", "Room Maintenance", testCase, test_description, test_catagory, test_steps);					
				}
				
				//delete room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromRoomMaintenance(driver);
			navigation.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, roomClassNames);
			app_logs.info("Click on Search Button");
			newRoomClass.deleteRoomClassV2(driver, roomClassNames);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassNames + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNames);
			
				}catch (Exception e) {			
					Utility.catchException(driver, e, "Delete Room Class", "Room Class", "Room Class", testCase, test_description, test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Delete Room Class", "Room Class", "Room Class", testCase, test_description, test_catagory, test_steps);			
				}
	}
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided

		return Utility.getData("GSRoomMaintenanceVerification", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
