package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
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
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPickUp_RedBookIcon extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
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
	public void verifyPickUp_RedBookIcon(String url, String clientcode, String username, String password,
			String RoomClassAbb, String RoomClassName, String maxAdults, String maxPersons, String roomQuantity,
			String RateName, String bedsCount, String BaseAmount, String AddtionalAdult, String AdditionalChild,
			String AssociateSession, String RatePolicy, String RateDescription, String AccountName,
			String MargetSegment, String Referral, String AccountFirstName, String AccountLastName, String Phonenumber,
			String Address1, String city, String Country, String State, String Postalcode, String BlockName,
			String RoomPerNight, String Amount) throws InterruptedException, IOException {

		String testName = "VerifyPickUp_RedBookIcon";
		String test_description = "Verify pick up from red book icon when there are no rooms available for the roomclass.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554189' target='_blank'>"
				+ "Click here to open TestRail: C554189</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);
		Reservation res = new Reservation();
		Navigation nav = new Navigation();
		Tapechart tapechart = new Tapechart();
		RoomClass room_class = new RoomClass();
		Rate rate = new Rate();

		//// Room Class
		try {
			nav.Setup(driver);
			nav.RoomClass(driver);
			RoomClassName = (RoomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");

			app_logs.info("try");
			nav.NewRoomClass1(driver);

			getTest_Steps.clear();
			/*
			 * getTest_Steps = room_class.CreateRoomClass(driver, RoomClassName,
			 * RoomClassAbb, bedsCount, maxAdults, maxPersons, roomQuantity, test,
			 * getTest_Steps);
			 */
			room_class.roomClassInfoNewPage(driver, RoomClassName, RoomClassAbb, bedsCount, maxAdults, maxPersons, roomQuantity, test);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
			app_logs.info("Sccessfully Created New RoomClass" + RoomClassName + " Abb : " + RoomClassAbb);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Rate and Attach RoomClass
		try {

			nav.Inventory(driver);
			nav.Rates1(driver);
			RateName = (RateName + Utility.getTimeStamp()).replaceAll("_", "");
			String DisplayName = RateName;

			getTest_Steps = rate.CreateRate_Season(driver, RateName, maxAdults, maxPersons, BaseAmount, AddtionalAdult,
					AdditionalChild, DisplayName, RatePolicy, RateDescription, RoomClassName, AssociateSession,
					"Rack Rate", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Enter all require details and save");
			app_logs.info("Enter all require details and save");
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

		Groups group = new Groups();

		// Navigate to Groups
		try {
			nav.Reservation(driver);
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
		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);

			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
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

		// Create Reservation
		try {

			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, true, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.SaveRes_Updated(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups and Search Account
		try {
			nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
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

		// Room Block
		try {
			group.navigateRoomBlock(driver, test);
			String afterPickUpValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickUpValue);
			test_steps.add("After Pickup Value : " + afterPickUpValue);
			assertEquals(Integer.parseInt(afterPickUpValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed : PickUpValue not Increased");

			String afterAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + afterAvailableRoom);
			test_steps.add("After Available Rooms : " + afterAvailableRoom);
			assertEquals(Integer.parseInt(afterAvailableRoom), Integer.parseInt(beforeAvailableRoom) - 1,
					"Failed : Available room count not Decreased");

			String afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
			test_steps.add("After BookIcon Class : " + afterBookIconClass);
			assertEquals(afterBookIconClass, "bookred","Failed : Book Color Not Matched");
			
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation with zero room
		try {

			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, false, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.SaveRes_Updated(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups and Search Account
		try {
			nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
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

		// Room Block
		try {

			group.navigateRoomBlock(driver, test);
			String afterPickUpValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickUpValue);
			test_steps.add("After Pickup Value : " + afterPickUpValue);
			assertEquals(Integer.parseInt(afterPickUpValue), Integer.parseInt(beforePickupValue) + 2,
					"Failed : PickUpValue not Increased");

			String afterAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + afterAvailableRoom);
			test_steps.add("After Available Rooms : " + afterAvailableRoom);
			assertEquals(Integer.parseInt(afterAvailableRoom), Integer.parseInt(beforeAvailableRoom) - 2,
					"Failed : Available room count not Decreased");

			String afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
			test_steps.add("After BookIcon Class : " + afterBookIconClass);
			assertEquals(afterBookIconClass, "bookred","Failed : Book Color Not Matched");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		Tapechart tc = new Tapechart();
		try{
			nav.Reservation(driver);
			nav.TapeChart(driver);
			
			String tapechartRoomAvailable = tc.getFooter2ndCellValue(driver, RoomClassAbb, "# Rooms Available");
			Utility.app_logs.info("TapeChart Available Rooms : " + tapechartRoomAvailable);
			test_steps.add("TapeChart Available Rooms : " + tapechartRoomAvailable);
			assertEquals(tapechartRoomAvailable, "-1","Failed to Verify Available Room in TapeChart");
			
			String tapechartOccupancy = tc.getFooter2ndCellValue(driver, RoomClassAbb, "% Occupancy");
			Utility.app_logs.info("TapeChart Occupancy % : " + tapechartOccupancy);
			test_steps.add("TapeChart Occupancy % : " + tapechartOccupancy);
			assertEquals(tapechartOccupancy, "200%","Failed to Verify Occupancy % in TapeChart");
			
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
		try{
			nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);
			
			
		}  catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Delete rate
		try {
			try {
				nav.Inventory(driver);
				nav.Rates1(driver);
				rate.SearchRate(driver, RateName, false);

				test_steps.add("New Rate has been Searched successfully");
				app_logs.info("New Rate has been Searched successfully");

				rate.delete_rate_1(driver, RateName);

				test_steps.add("New Rate has been Deleted successfully");
				app_logs.info("New Rate has been Deleted successfully");
			} catch (Exception e) {
				System.out.println("Tried To Delete Rate");
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Room Class
		try {
			try {
				nav.Setup(driver);
				nav.RoomClass(driver);

				try {
					room_class.SearchRoomClass(driver, RoomClassName, false);
					room_class.Delete_RoomClass(driver, RoomClassName);
				} catch (Exception b) {
					room_class.SelectItemsPerPage(driver);
					room_class.Search_Delete_RoomClass(driver, RoomClassName);
				}

				test_steps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} catch (Exception e) {
				System.out.println("Tried to Delete RoomClass");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyPickUp_RedBookIcon", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
