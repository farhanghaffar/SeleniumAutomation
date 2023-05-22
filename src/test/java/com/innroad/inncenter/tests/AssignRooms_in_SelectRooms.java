package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class AssignRooms_in_SelectRooms extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	ArrayList<String> BlockDetails = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
    ArrayList<String> checkOutDates = new ArrayList<>();
    
    ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		System.out.println("hjkfDFj"+BEExcel);
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	// Create split reservation
	@Test(dataProvider = "getData", groups = "Groups")
	public void assignRoomsInSelectRooms(String TestCaseID,String ClientCode, String Username, String Password, String AccountType,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String alternativeNumber, String Address1, String Address2, String Address3,
			String Email, String city, String Country, String State, String Postalcode, String BlockName,
			String NumberofNights, String PreScheduler, String PostScheduler, String EnterBlockedcount,
			String RoomClassName,String checkInDate, String checkOutDate,String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard, String PaymentType) throws Exception {
		test_name = "assignRoomsInSelectRooms";
		test_description = "createAndEditBlockwithTemplate in groups<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554158'>"
				+ "Click here to open TestRail: C554158</a>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554159'>"
				+ "Click here to open TestRail: C554159</a>";
		test_catagory = "Groups";
		String testName = test_name;
		String ReservationNo="";

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("824525");
			caseId.add("824615");
			caseId.add("824756");
			caseId.add("824760");
			caseId.add("825630");
			caseId.add("825668");
			caseId.add("824655");
			caseId.add("825394");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advgrp = new AdvGroups();
		CPReservationPage reservationPage = new CPReservationPage();
		
		try {
		    if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){

                if (AccountFirstName.split("\\|").length>1) {

                    for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {

                        checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

                        checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

                                ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));

                    }

                }else

                {

                    checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));

                    checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),

                            ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));

                }

            }


            if (AccountFirstName.split("\\|").length>1) {

                checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);

                checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);

            }else {

                checkInDate = checkInDates.get(0);

                checkOutDate = checkOutDates.get(0);

            }


           app_logs.info(checkInDate);

            app_logs.info(checkOutDate);
		}catch(Exception e)
		{
			
		}

		// Login to InnCenter
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", testName, "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Nav.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate to groups", testName, "NavigateGroups", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate to groups", testName, "NavigateGroups", driver);
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
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			test_steps.add("================Search the created group================");
			app_logs.info("=================Search the created group=================");
			Nav.Groups_Backward(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			comments=comments+ ". Opened the existing Group with Account Name: "+AccountName;
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// create block
			test_steps.add("================Create New Block================");
			app_logs.info("=================Create New Block=================");
			advgrp.EnterBlockName(driver, BlockName, test_steps);
			advgrp.SearchGroupCriteriawithSchdulers(driver, NumberofNights, PreScheduler, PostScheduler, test_steps);
			advgrp.BlockRoomForSelectedRoomclass(driver, getTest_Steps, EnterBlockedcount, RoomClassName);
			int i = Integer.parseInt(EnterBlockedcount) + 1;
			EnterBlockedcount = i + "";
			getTest_Steps.clear();
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, EnterBlockedcount);
			test_steps.addAll(getTest_Steps);
			advgrp.ClickCreateBlock(driver, test_steps);
			advgrp.verify_RoomBlock_Details(driver, AccountName, BlockName, NumberofNights, EnterBlockedcount);
			group.Save(driver, test_steps);
			test_steps.add("Create block with pre and post shoulders ");
			comments="Sucessfully created new block with name:"+BlockName;
			

			advgrp.NavigateRoomBlock(driver, test_steps);
			advgrp.click_Select_Rooms(driver, test_steps);
			Wait.wait10Second();
			advgrp.select_Room_Number(driver, test_steps);
			Wait.wait10Second();
			comments=comments+ ". Selected a Room number from Select Room pop-up";
			advgrp.select_Rooms_save(driver, test_steps);
			Wait.wait25Second();
			try {
			advgrp.Select_Rooms_done(driver, test_steps);
			}catch (Exception e) {
				Wait.wait15Second();
				advgrp.Select_Rooms_done(driver, test_steps);
			}
			Wait.loadingImage(driver);
			driver.switchTo().defaultContent();
			
			// test_steps.add(" Groups: Unable to select rooms : Code Fix" +
			// "<br/><a href='https://innroad.atlassian.net/browse/NG-6556'
			// target='_blank'>"
			// + "Verified : NG-6556</a><br/>");
			// advgrp.NavigateRoomBlock(driver, test_steps);
			// advgrp.click_Select_Rooms(driver, test_steps);
			// advgrp.verifyRoom_selected(driver, test_steps);
			// test_steps.add("User is able to select rooms even after picking
			// up reservations for the block" +
			// "<br/><a href='https://innroad.atlassian.net/browse/NG-7416'
			// target='_blank'>"
			// + "Verified : NG-7416</a><br/>");
			// advgrp.Remove_Selected_Room(driver, test_steps);

			// test_steps.add("Create block with pre and post shoulders ");
			// RetryFailedTestCases.count = Utility.reset_count;
			// Utility.AddTest_IntoReport(testName, test_description,
			// test_catagory, test_steps);
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create group", testName, "CreateGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create group", testName, "CreateGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create RoomBlock
		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			
			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);
			

			test_steps.add("================Click On Book Icon================");
			app_logs.info("=================Click On Book Icon=================");
			
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create Reservation
		try {
			test_steps.add("================Create Reservation================");
			app_logs.info("=================Create Reservation=================");
			/*
			 * getTest_Steps.clear(); res.clickGuestInfo_1(driver, getTest_Steps);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * 
			 * res.EnterFirst_LastName(driver, AccountFirstName, AccountLastName);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * 
			 * getTest_Steps.clear(); res.SaveRes_Updated(driver);
			 * test_steps.addAll(getTest_Steps);
			 * test_steps.add("Successfully Associated Account to  Reservation");
			 * app_logs.info("Successfully Associated Account to Reservation");
			 */
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();

			/*
			 * reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			 * 
			 * reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			 * 
			 * reservationPage.enter_Adults(driver, test_steps, adults);
			 * 
			 * reservationPage.enter_Children(driver, test_steps, children);
			 * 
			 * reservationPage.select_Rateplan(driver, test_steps, rateplan,"");
			 * 
			 * reservationPage.clickOnFindRooms(driver, test_steps);
			 * 
			 * reservationPage.selectRoom(driver, test_steps, RoomClassName,
			 * "Yes",AccountName);
			 */
			comments=comments+ ". Verified the page is navigated to group reservation screen by clicking the group pick up button";
            
			reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            ReservationNo=reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

           test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
            comments=comments+ ". User able to create group reservation from select room pop up";
            
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups
		try {
			test_steps.add("================Navigate to Groups================");
			app_logs.info("=================Navigate to Groups=================");
			Nav.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// searching and navigate to Room Block
		try {
			test_steps.add("================Verify Block details pick up percentage================");
			app_logs.info("=================Verify Block details pick up percentage=================");
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			String afterPickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			float checkPercentage = (100 / Float.parseFloat(EnterBlockedcount)) * Float.parseFloat(afterPickupValue);
			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			assertEquals(pickUpPercentage, new DecimalFormat("##.##").format(checkPercentage) + "",
					"Failed PickUp Percentage RoomBlock Detail");
			Utility.app_logs.info("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Successfully Verified Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			Utility.app_logs.info("Pick up percentage is incorrect when reservation is picked up from group block");
			test_steps.add("Pick up percentage is incorrect when reservation is picked up from group block" + 
					"<br/><a href='https://innroad.atlassian.net/browse/NG-7399' target='_blank'>"
					+ "Verified : NG-7399</a><br/>");
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// account inActive
		try {
			test_steps.add("================account inActive================");
			app_logs.info("=================account inActive=================");
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			/*comments= "Verified Groups page search by giving the Group name: "+AccountName+ "and Account no: "+AccountNo+ ". "
					  +"Created a block with pre shoulder "+PreScheduler+" and post shoulder "+PostScheduler+ " . "
					  +"Assigned Room No to the block:"+BlockName+" ."
					  +"Group Reservation is created with Reservation No: "+ReservationNo+". "
					  +"User is able to create Group reservation by assigning room no to the block:"+BlockName+" .";
					  
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			statusCode.set(3, "1");
			statusCode.set(4, "1");*/
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			app_logs.info(comments);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	// Data provider to read the data from excel
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided
		return Utility.getData("assignRoomsInSelectRooms", BEExcel);
	}

	
}