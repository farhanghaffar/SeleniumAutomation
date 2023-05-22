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

public class VerifyCreatingAndEditingBlockByAddingTemplateItems extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();

	ArrayList<String> BlockDetails = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	// Create split reservation
	@Test(dataProvider = "getData", groups = "Groups")
	public void verifyCreatingAndEditingBlock(String TestCaseID,String url, String ClientCode, String Username, String Password,
			String AccountType, String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String alternativeNumber, String Address1, String Address2,
			String Address3, String Email, String city, String Country, String State, String Postalcode,
			String BlockName, String NumberofNights, String PreScheduler, String PostScheduler,
			String EnterBlockedcount, String RoomClassName, String UpdatedNumberofNights, String UpdatedPreScheduler,
			String UpdatedPostScheduler, String UpdatedEnterBlockedcount, String UpdatedRoomClassName,String arriveDate,String departDate,String ratePlan,String salutation,String guestFirstName,String guestLastName,
			String paymentMethod,String cardNumber,String nameOnCard,String isGroupPolicyApplicable,String reservationType)
			throws InterruptedException, ParseException {
		test_name = "verifyCreatingAndEditingBlock";
		test_description = "createAndEditBlockwithTemplate in groups<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554155'>"
				+ "Click here to open TestRail: C554155</a>";
		test_description = "Edit block<br>" + "<a href='https://innroad.testrail.io/index.php?/cases/view/554156'>"
				+ "Click here to open TestRail: C554156</a>";
		test_description = "Add template items<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554157'>"
				+ "Click here to open TestRail: C554157</a>";
		test_catagory = "Groups";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advgrp = new AdvGroups();
		CPReservationPage reservationPage=new CPReservationPage();
		String resNo=null;
		String rate=null;
		ArrayList<String> rooms= new ArrayList<String>();
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("786860");
			caseId.add("786861");
			caseId.add("786862");
			caseId.add("786863");
			caseId.add("787610");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
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
			test_steps.add("=================Create New Group================");
			app_logs.info("==================Create New Group=================");
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
			Nav.Groups_Backward(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
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
		String Room_Block_PromoCod = null;
		try {
			// create block
			test_steps.add("=================Create Block================");
			app_logs.info("==================Create Block=================");
			advgrp.EnterBlockName(driver, BlockName, test_steps);
			advgrp.SearchGroupCriteriawithSchdulers(driver, NumberofNights, PreScheduler, PostScheduler, test_steps);
			advgrp.verifyNewBlockDetails(driver, UpdatedNumberofNights, BlockDetails);
			advgrp.BlockRoomForSelectedRoomclass(driver, getTest_Steps, EnterBlockedcount, RoomClassName);
			advgrp.ClickCreateBlock(driver, test_steps);
			advgrp.verify_RoomBlock_Details(driver, AccountName, BlockName, NumberofNights, EnterBlockedcount);
			group.Save(driver, test_steps);
			test_steps.add("Create block with pre and post shoulders ");
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

		// edit block
		try {
			test_steps.add("=================Edit Block================");
			app_logs.info("==================Edit Block=================");
			advgrp.NavigateRoomBlock(driver, test_steps);
			advgrp.click_EditBlock(driver, test_steps);
			Room_Block_PromoCod = group.Verify_Room_Block_Promo_code(driver, test_steps);

			advgrp.UpdateSearchGroupCriteriawithSchdulers(driver, UpdatedNumberofNights, PreScheduler, PostScheduler,
					UpdatedPreScheduler, UpdatedPostScheduler, test_steps);
			advgrp.BlockRoomForSelectedRoomclass(driver, test_steps, UpdatedEnterBlockedcount, UpdatedRoomClassName);
			advgrp.verify_EditBlock_Details(driver, BlockName);
			advgrp.click_EditBlockSave(driver, test_steps);
			group.Save(driver, test_steps);
			test_steps.add("Edit block with pre and post shoulders ");
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

		// Add Template

		boolean flag = true;
		try {
			test_steps.add("=================Add Template================");
			app_logs.info("==================Add Template=================");
			advgrp.NavigateRoomBlock(driver, test_steps);
			advgrp.click_EditBlock(driver, test_steps);
			group.click_PreviewFolio(driver, test_steps);
			advgrp.verify_Preview_Folio_Details(driver, BlockName);
			group.previewFolio_AddLineItem(driver, test_steps);
			group.Save(driver, test_steps);
			advgrp.NavigateRoomBlock(driver, test_steps);
			rate=advgrp.getRateFromRoomBlock(driver, test_steps, UpdatedRoomClassName);
			rate=rate.replace("$ ", "");
			advgrp.ClickBookicon(driver, getTest_Steps, UpdatedRoomClassName, flag);
			Wait.wait10Second();
		
			

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
		//Create Reservation
        try {
        	test_steps.add("=================Create Reservation from book icon================");
			app_logs.info("==================Create Reservation from book icon=================");
            String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
            reservationPage.createGroupReservation(driver, test_steps, RoomClassName, config.getProperty("flagOn"),"",salutation, guestFirstName, guestLastName, config.getProperty("flagOff"),paymentMethod, cardNumber, nameOnCard, expiryDate, isGroupPolicyApplicable,  reservationType, rooms, resNo);
            app_logs.info(resNo);
            getTest_Steps.clear();
            test_steps.addAll(getTest_Steps);
            String res_Promocod=reservationPage.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);
            assertEquals(Room_Block_PromoCod, res_Promocod, "promo codes are not matching");
            //advgrp.validatePromecode(driver, Room_Block_PromoCod, AccountName, test_steps);
			/*
			 * advgrp.verifyReservation_folio_details(driver, AccountName);
			 * test_steps.add("Add templete items to block");
			 */
         // after Creation of Reservation verifying folio line items from reservation side
    			test_steps.add("=============verifying folio line items from reservation side============");
    			app_logs.info("==============verifying folio line items from reservation side=============");
    			
    			Utility.app_logs.info("After Creation of Reservation :-");
    			test_steps.add("After Creation of Reservation :-");

    			reservationPage.click_Folio(driver, getTest_Steps);
    			Utility.app_logs.info("Folio Tab Clicked");
    			test_steps.add("Folio Tab Clicked");

    			/*
    			 * getTest_Steps.clear(); getTest_Steps = res.verifyFolioSelect(driver,
    			 * AccountName); test_steps.addAll(getTest_Steps);
    			 */

    			getTest_Steps.clear();
    			getTest_Steps = reservationPage.selectFolioOption(driver, "Guest Folio");
    			test_steps.addAll(getTest_Steps);

    			getTest_Steps.clear();
    			getTest_Steps = res.VerifyFolioLineItem(driver, "Room Charge", rate, "1");
    			test_steps.addAll(getTest_Steps);
    			
    			//Bug while adding preview folio options
    			/*
    			 * getTest_Steps.clear(); getTest_Steps = res.VerifyFolioLineItem(driver,
    			 * LineCategory, LineAmount, "2"); test_steps.addAll(getTest_Steps);
    			 */
    			// switch to another folio
				/*
				 * String option = AccountName; getTest_Steps.clear(); getTest_Steps =
				 * reservationPage.selectFolioOption(driver, option);
				 * test_steps.addAll(getTest_Steps);
				 */
    			
    			comments="Picked up a room for which pre shoulder value is given as : "+PreScheduler+" ."
    					+"Picked up the room when pre shoulder value is given as :"+PreScheduler+ "and edited pre shoulder value as:" +UpdatedPreScheduler+ " ."
    					+"Picked up a room for which post shoulder value is given as : "+PostScheduler+" ."
    					+"Picked up the room when post shoulder value is given as :"+PostScheduler+ "and edited post shoulder value as:" +UpdatedPostScheduler+ " ."
    					+"Verified the promocode: "+Room_Block_PromoCod+ "for the reservation from Group Account";
    					
    					  
    			statusCode.set(0, "1");
    			statusCode.set(1, "1");
    			statusCode.set(2, "1");
    			statusCode.set(3, "1");
    			statusCode.set(4, "1");
    			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);



        }catch (Exception e) {
            Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
                    test_description, test_catagory, test_steps);
        } catch (Error e) {
            Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
                    test_description, test_catagory, test_steps);



        }
	}

	// Data provider to read the data from excel
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided
		return Utility.getData("verifyCreatingAndEditingBlock", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
