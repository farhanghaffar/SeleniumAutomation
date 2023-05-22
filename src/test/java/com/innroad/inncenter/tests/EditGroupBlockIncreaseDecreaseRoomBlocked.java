package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class EditGroupBlockIncreaseDecreaseRoomBlocked extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;


	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	public void login(String testName) {

		try {
			if (!Utility.insertTestName.containsKey(testName)) {

				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Group(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@Test(dataProvider = "getData", groups = { "groups" })
	public void editGroupBlockIncreaseDecreaseRoomBlocked(String TestCaseID,String url, String clientcode, String username,
			String password, String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String Address1, String city, String Country, String State,
			String Postalcode, String BlockName, String RoomPerNight, String RoomClassName, String RoomClassAbb)
			throws InterruptedException, IOException, ParseException {

		String testName = "EditGroupBlockIncreaseDecreaseRoomBlocked";
		String test_description = "Edit a group block and decrease count of rooms blocked.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554165' target='_blank'>"
				+ "Click here to open TestRail: C554165</a><br/>"
				+ "Edit a group block and increase the count of rooms blocked.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554166' target='_blank'>"
				+ "Click here to open TestRail: C554166</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);

		Navigation nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		Tapechart tc = new Tapechart();
		int decreaseCount=1;
		int increaseCount=1;
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("786849");
			caseId.add("786850");
			caseId.add("786879");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}

		// Navigate to Groups
		try {
			nav.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			test_steps.add("================Create New Group================");
			app_logs.info("=================Create New Group=================");
			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomBlock

		try {
			test_steps.add("================Create RoomBlock================");
			app_logs.info("=================Create RoomBlock=================");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClassName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			String blocked = group.getBlocked(driver, RoomClassName);
			assertEquals(Integer.parseInt(blocked), Integer.parseInt(RoomPerNight), "Failed Room Blocked Not Matched");
			Utility.app_logs.info("Successfully Verified Room Blocked  : " + blocked);
			test_steps.add("Successfully Verified Room Blocked  : " + blocked + 
					"<br/><a href='https://innroad.atlassian.net/browse/NG-7365' target='_blank'>"
					+ "Verified : NG-7365</a><br/>");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String beforeIncreasingTapechartRoomAvailable = null;
		String beforeIncreasingtapechartOccupancy = null;
		try {
			test_steps.add("=======Verify Rooms availability in Tape Chart before increasing=======");
			app_logs.info("=======Verify Rooms availability in Tape Chart before increasing=======");
			nav.Reservation(driver);
			nav.TapeChart(driver);
			driver.navigate().refresh();
			beforeIncreasingTapechartRoomAvailable = tc.getFooter2ndCellValue(driver, RoomClassAbb,
					"# Rooms Available");
			Utility.app_logs.info("Before Increasing Blocked Count TapeChart Available Rooms : "
					+ beforeIncreasingTapechartRoomAvailable);
			test_steps.add("Before Increasing Blocked Count TapeChart Available Rooms : "
					+ beforeIncreasingTapechartRoomAvailable);
			// assertEquals(beforeIncreasingTapechartRoomAvailable, "-1","Failed
			// to Verify Available Room in TapeChart");

			beforeIncreasingtapechartOccupancy = tc.getFooter2ndCellValue(driver, RoomClassAbb, "% Occupancy");
			Utility.app_logs.info(
					"Before Increasing Blocked Count TapeChart Occupancy % : " + beforeIncreasingtapechartOccupancy);
			test_steps.add(
					"Before Increasing Blocked Count TapeChart Occupancy % : " + beforeIncreasingtapechartOccupancy);
			beforeIncreasingtapechartOccupancy = beforeIncreasingtapechartOccupancy.replace("%", "");
			// assertEquals(beforeIncreasingtapechartOccupancy, "200%","Failed
			// to Verify Occupancy % in TapeChart");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("================Navigate to Groups================");
			app_logs.info("=================Navigate to Groups=================");
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// increasing count
		try {
			test_steps.add("================increasing count================");
			app_logs.info("=================increasing count=================");
			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");
			
			getTest_Steps.clear();
			getTest_Steps = group.changeAriveDepartDate(driver, true, 1, true, 2);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogSave(driver, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.errorMsg(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.errorMsg(driver);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Adv Groups: Date changes allowed without search.");
			test_steps.add("Adv Groups: Date changes allowed without search."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7120' target='_blank'>"
					+ "Verified : NG-7120 </a><br/>");
			
			getTest_Steps.clear();
			getTest_Steps = group.clickBlockSearch(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogSave(driver, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.errorMsg(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, RoomPerNight);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			
			BlockName = BlockName + "# Check";
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogName(driver, BlockName);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogSave(driver, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Save button is disabled even after making changes to block.");
			test_steps.add("Save button is disabled even after making changes to block."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7302' target='_blank'>"
					+ "Verified : NG-7302 </a><br/>");
			
			Utility.app_logs.info("Unable to create group blocks with special characters.");
			test_steps.add("Unable to create group blocks with special characters."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-6277' target='_blank'>"
					+ "Verified : NG-6277 </a><br/>");
			
			getTest_Steps.clear();
			getTest_Steps = group.changeAriveDepartDate(driver, true, 0, true, 1);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.clickBlockSearch(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, RoomPerNight);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogSave(driver, true);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogSave(driver, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyEditDialogDone(driver, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("After clicking on SAVE button once after stay details are changed, both save and done buttons are disabled.");
			test_steps.add("After clicking on SAVE button once after stay details are changed, both save and done buttons are disabled."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7312' target='_blank'>"
					+ "Verified : NG-7312 </a><br/>");
			
			Utility.app_logs.info("Unable to save edited block");
			test_steps.add("Unable to save edited block"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7375' target='_blank'>"
					+ "Verified : NG-7375 </a><br/>");
			
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			
				test_steps.add(e.toString());
			
		}

			
			// increasing count
		try {
			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");
				
			getTest_Steps.clear();
			getTest_Steps = group.checkVaryRoomsByDate(driver, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.increaseRoomBlockedCount(driver, RoomClassName, increaseCount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.editBlockRoomNightDialog(driver);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			group.loadingImage(driver);
			driver.switchTo().defaultContent();
			group.loadingImage(driver);
			
			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			assertEquals(Integer.parseInt(RoomBlocked), Integer.parseInt(RoomPerNight) + 1,
					"Failed Room Blocked Not Matched");
			Utility.app_logs.info("Successfully Verified Room Block Detail Room Blocked Increased : " + RoomBlocked);
			test_steps.add("Successfully Verified Room Block Detail Room Blocked Increased : " + RoomBlocked);

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			assertEquals(Integer.parseInt(totalRoomNight), Integer.parseInt(RoomPerNight) + 1,
					"Failed Room Blocked Not Matched");
			Utility.app_logs
					.info("Successfully Verified Room Block Detail Total Room Nights Increased : " + totalRoomNight);
			test_steps.add("Successfully Verified Room Block Detail Total Room Nights Increased : " + totalRoomNight);

			String blocked = group.getBlocked(driver, RoomClassName);
			assertEquals(Integer.parseInt(blocked), Integer.parseInt(RoomPerNight) + 1,
					"Failed Room Blocked Not Matched");
			Utility.app_logs.info("Successfully Verified Room Blocked Increased : " + blocked);
			test_steps.add("Successfully Verified Room Blocked Increased : " + blocked + 
					"<br/><a href='https://innroad.atlassian.net/browse/NG-7375' target='_blank'>"
					+ "Verified : NG-7375</a><br/>");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Increase Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Increase Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String afterIncreasingTapechartRoomAvailable = null;
		String afterIncreasingtapechartOccupancy = null;
		try {
			test_steps.add("=======Verify Rooms availability in Tape Chart after increasing=======");
			app_logs.info("=======Verify Rooms availability in Tape Chart after increasing=======");
			nav.Reservation(driver);
			nav.TapeChart(driver);
			driver.navigate().refresh();

			afterIncreasingtapechartOccupancy = tc.getFooter2ndCellValue(driver, RoomClassAbb, "% Occupancy");
			Utility.app_logs.info(
					"After Increasing Blocked Count TapeChart Occupancy % : " + afterIncreasingtapechartOccupancy);
			test_steps
					.add("After Increasing Blocked Count TapeChart Occupancy % : " + afterIncreasingtapechartOccupancy);
			afterIncreasingtapechartOccupancy = afterIncreasingtapechartOccupancy.replace("%", "");
//			assertEquals(Integer.parseInt(afterIncreasingtapechartOccupancy),
//					Integer.parseInt(beforeIncreasingtapechartOccupancy) + 1,
//					"Failed to Verify Occupancy % in TapeChart");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			
				test_steps.add(e.toString());
			
		}

		try {

			afterIncreasingTapechartRoomAvailable = tc.getFooter2ndCellValue(driver, RoomClassAbb, "# Rooms Available");
			Utility.app_logs.info("After Increasing Blocked Count TapeChart Available Rooms : "
					+ afterIncreasingTapechartRoomAvailable);
			test_steps.add("After Increasing Blocked Count TapeChart Available Rooms : "
					+ afterIncreasingTapechartRoomAvailable);
//			assertEquals(Integer.parseInt(afterIncreasingTapechartRoomAvailable),
//					Integer.parseInt(beforeIncreasingTapechartRoomAvailable),
//					"Failed to Verify Available Room in TapeChart");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			
				test_steps.add(e.toString());
		
		}

		try {
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// decreasing count
		try {
			test_steps.add("============decreasing count============");
			app_logs.info("============decreasing count=============");
			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");
			
			getTest_Steps.clear();
			getTest_Steps = group.checkVaryRoomsByDate(driver, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.decreaseRoomBlockedCount(driver, RoomClassName, decreaseCount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.editDialogSave(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.editBlockRoomNightDialog(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			
			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			assertEquals(Integer.parseInt(RoomBlocked), Integer.parseInt(RoomPerNight),
					"Failed Room Blocked Not Matched");
			Utility.app_logs.info("Successfully Verified Room Block Detail Room Blocked Decreased : " + RoomBlocked);
			test_steps.add("Successfully Verified Room Block Detail Room Blocked Decreased : " + RoomBlocked);

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			assertEquals(Integer.parseInt(totalRoomNight), Integer.parseInt(RoomPerNight),
					"Failed Room Blocked Not Matched");
			Utility.app_logs
					.info("Successfully Verified Room Block Detail Total Room Nights Decreased : " + totalRoomNight);
			test_steps.add("Successfully Verified Room Block Detail Total Room Nights Decreased : " + totalRoomNight);

			String blocked = group.getBlocked(driver, RoomClassName);
			assertEquals(Integer.parseInt(blocked), Integer.parseInt(RoomPerNight), "Failed Room Blocked Not Matched");
			Utility.app_logs.info("Successfully Verified Room Blocked Decreased : " + blocked);
			test_steps.add("Successfully Verified Room Blocked Decreased : " + blocked + 
					"<br/><a href='https://innroad.atlassian.net/browse/NG-7375' target='_blank'>"
					+ "Verified : NG-7375</a><br/>");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Increase Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Increase Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String afterDecreasingTapechartRoomAvailable = null;
		String afterDecreasingtapechartOccupancy = null;
		try {
			test_steps.add("=======Verify Rooms availability in Tape Chart after decreasing=======");
			app_logs.info("=======Verify Rooms availability in Tape Chart after decreasing=======");
			nav.Reservation(driver);
			nav.TapeChart(driver);
			driver.navigate().refresh();

			afterDecreasingtapechartOccupancy = tc.getFooter2ndCellValue(driver, RoomClassAbb, "% Occupancy");
			Utility.app_logs.info(
					"After Decreasing Blocked Count TapeChart Occupancy % : " + afterDecreasingtapechartOccupancy);
			test_steps
					.add("After Decreasing Blocked Count TapeChart Occupancy % : " + afterDecreasingtapechartOccupancy);
			afterDecreasingtapechartOccupancy = afterDecreasingtapechartOccupancy.replace("%", "");
//			assertEquals(afterDecreasingtapechartOccupancy, beforeIncreasingtapechartOccupancy,
//					"Failed to Verify Occupancy % in TapeChart");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			
				test_steps.add(e.toString());
			
		}

		try {

			afterDecreasingTapechartRoomAvailable = tc.getFooter2ndCellValue(driver, RoomClassAbb, "# Rooms Available");
			Utility.app_logs.info("After Decreasing Blocked Count TapeChart Available Rooms : "
					+ afterDecreasingTapechartRoomAvailable);
			test_steps.add("After Decreasing Blocked Count TapeChart Available Rooms : "
					+ afterDecreasingTapechartRoomAvailable);
//			assertEquals(afterDecreasingTapechartRoomAvailable, beforeIncreasingTapechartRoomAvailable,
//					"Failed to Verify Available Room in TapeChart");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			
				test_steps.add(e.toString());
			
		}

		try {
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			// group.navigateRoomBlock(driver, test);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// account inActive
		try {
			// getTest_Steps.clear();
			// group.Save(driver, test, getTest_Steps);
			// test_steps.addAll(getTest_Steps);
			test_steps.add("================account inActive================");
			app_logs.info("=================account inActive=================");
			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			
			comments="Verified availability and oocupany percent by increasing the room block count as:" +increaseCount+ " . "
					+ "Verified availability and oocupany percent by decreasing the room block count as:" +decreaseCount+ " . ";
			
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("EditIncreaseDecreaseRoomBlocked", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
