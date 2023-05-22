package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.mongodb.operation.GroupOperation;

public class VerifyBilingInfoInRoomingListAndReservation extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
    ArrayList<String> checkOutDates = new ArrayList<>();
    HashMap<String, String> groupNameNo=new HashMap<>();

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
	public void verifyBilingInfoInRoomingListAndReservation(String url, String clientcode, String username,
			String password,String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String Address1, String city, String Country, String State,
			String Postalcode, String PaymentMethod, String AccountNumber, String ExpiryDate, String BlockName,
			String RoomPerNight, String RoomClassName, String Amount, String SwipeCard,String checkInDate, String checkOutDate,String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard, String PaymentType)
			throws InterruptedException, IOException {

		String testName = "VerifyBilingInfoInRoomingListAndReservation";
		String test_description = "Verify all the billing info details are getting copied to rooming list billing info.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554190' target='_blank'>"
				+ "Click here to open TestRail: C554190</a><br/>"
				+ "Verify billing info details are getting copied to pickup reservations.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554191' target='_blank'>"
				+ "Click here to open TestRail: C554191</a><br/>"
				+ "Verify the billing info of the pickup resrvation when the 'copy to pickup reservation' is unchecked.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554192' target='_blank'>"
				+ "Click here to open TestRail: C554192</a><br/>"
				+ "Verify rooming list billing info when the 'copy to pickup reservation' is unchecked.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554193' target='_blank'>"
				+ "Click here to open TestRail: C554193</a><br/>";

		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		//String AccountName="";
		//String AccountNo="";
		
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


		// Navigate to Groups
		try {
			
			Nav.Groups(driver);
			/*
			 * groupNameNo=group.searchAccount(driver);
			 * AccountName=groupNameNo.get("GroupName");
			 * AccountNo=groupNameNo.get("AccountNo");
			 */
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
		  test_steps.add("===============Create New Group=========");
		  app_logs.info("===============Create New Group=========");
		  AccountName = AccountName+ Utility.GenerateRandomString15Digit(); getTest_Steps.clear();
		  group.type_GroupName(driver, test, AccountName, getTest_Steps);
		  test_steps.addAll(getTest_Steps);
		  
		  AccountNo = Utility.GenerateRandomString15Digit(); getTest_Steps.clear();
		  getTest_Steps = group.enterAccountNo(driver, AccountNo);
		  test_steps.addAll(getTest_Steps);
		  
		  getTest_Steps.clear(); 
		  group.type_AccountAttributes(driver, test,
		  MargetSegment, Referral, getTest_Steps); 
		  test_steps.addAll(getTest_Steps);
		  getTest_Steps.clear(); 
		  group.type_MailingAddrtess(driver, test,
		  AccountFirstName, AccountLastName, Phonenumber, Address1, city, State,
		  Country, Postalcode, getTest_Steps); 
		  test_steps.addAll(getTest_Steps);
		  getTest_Steps.clear(); 
		  group.billinginfo(driver, PaymentMethod,
		  AccountNumber, ExpiryDate, true, true); 
		  test_steps.addAll(getTest_Steps);
		  getTest_Steps.clear(); 
		  group.save(driver, test, getTest_Steps);
		  test_steps.addAll(getTest_Steps); 
		  getTest_Steps.clear(); 
		  getTest_Steps =
		  group.verifyCopyToPickUpReservation(driver, true);
		  test_steps.addAll(getTest_Steps);
		  
		  } catch (Exception e) { 
			  if (Utility.reTry.get(testName) == Utility.count) {
		  RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		  test_steps); Utility.updateReport(e, "Failed to Create Group", testName,
		  "Group", driver); } else { Assert.assertTrue(false); }
		  
		  } catch (Error e) {
			  if (Utility.reTry.get(testName) == Utility.count) {
		  RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory,
		  test_steps); Utility.updateReport(e, "Failed to Create Group", testName,
		  "Group", driver); } else { Assert.assertTrue(false); } }
		 

		// Create RoomBlock

		try {
			test_steps.add("===============Create Room Block=========");
			app_logs.info("===============Create Room Block=========");
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

			Utility.app_logs.info("Advance groups:Unable to create roomblock in EG /NGT/CP");
			test_steps.add("Advance groups:Unable to create roomblock in EG /NGT/CP"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7647' target='_blank'>"
					+ "Verified : NG-7647 </a><br/>");
			
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

		// Verify PickUp billing info
		String resNo = null;
		try {
			test_steps.add("===============Verify Pick Up Billing info=========");
			app_logs.info("===============Verify Pick Up Billing info=========");
			String beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethod(driver, "Account (Advanced Deposit)", false);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Advance deposit- Payment method in rooming list : code fix");
			test_steps.add("Advance deposit- Payment method in rooming list : code fix"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7006' target='_blank'>"
					+ "Verified : NG-7006 </a><br/>");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListBillingInfo(driver, AccountFirstName, AccountLastName, Phonenumber,
					Address1, city, Country, State, Postalcode, PaymentMethod, AccountNumber, ExpiryDate, true);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Successfully Verified 'Copy To Pickup Reservation' in RoomList Billing info");
			test_steps.add("Successfully Verified 'Copy To Pickup Reservation' in RoomList Billing info");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoSaveClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_RoomPickup(driver, AccountFirstName, AccountLastName, RoomClassName,
					Amount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");

			resNo = group.pickUp_getResNo(driver);
			Utility.app_logs.info("Created Reservation No : " + resNo);
			test_steps.add("Created Reservation No : " + resNo);

			Utility.app_logs.info("Getting oops error while creating group reservation from rooming list");
			test_steps.add("Getting oops error while creating group reservation from rooming list"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7215' target='_blank'>"
					+ "Verified : NG-7215 </a><br/>");
			
			Utility.app_logs.info("User is not able to create a new reservation from Rooming Listing pop-up of Group account.");
			test_steps.add("User is not able to create a new reservation from Rooming Listing pop-up of Group account."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7348' target='_blank'>"
					+ "Verified : NG-7348 </a><br/>");
			
			getTest_Steps.clear();
			getTest_Steps = group.pickUpCloseClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			group.loadingImage(driver);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			group.click_GroupsReservationTab(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyReservationCount(driver, 1);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyReservationInResTab(driver, resNo, 1);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("Group reservation w/o country info-unable to load reservations : Code Fix.");
			test_steps.add("Group reservation w/o country info-unable to load reservations : Code Fix."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7423' target='_blank'>"
					+ "Verified : NG-7423 </a><br/>");
			
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			String afterPickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			assertEquals(Integer.parseInt(afterPickupValue), Integer.parseInt(beforePickupValue) + 1,
					"Failed PickUp Value Not increased");
			Utility.app_logs.info("Successfully Verified Pick Up Increased Value");
			test_steps.add("Successfully Verified Pick Up Increased Value");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Reservation billing info from book icon
		try {
			test_steps.add("===============Create Reservation=========");
			app_logs.info("===============Create Reservation=========");
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);
			 
			reservationPage.clickNext(driver, test_steps); 
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver,test_steps, Salutation, AccountFirstName, AccountLastName,
			config.getProperty("flagOff"));
            reservationPage.clickBookNow(driver, test_steps);
            reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
            reservationPage.get_ReservationStatus(driver, test_steps);
            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

           test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
            
			getTest_Steps.clear();
			//getTest_Steps = res.verifyBillingInformation(driver, PaymentMethod, AccountNumber, ExpiryDate, true);
			test_steps.add("===============Verify Reservation Billing info=========");
			app_logs.info("===============Verify Reservation Billing info=========");
			reservationPage.VerifyPaymentMethod(driver, getTest_Steps, PaymentMethod, AccountNumber, NameOnCard, ExpiryDate);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Reservation Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Reservation Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Groups and Search Account
		try {
			test_steps.add("===============Navigate to Groups and Search Account=========");
			app_logs.info("===============Navigate to Groups and Search Account=========");
			Nav.Groups(driver);
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

		try {
			test_steps.add("===============Uncheck Copy to Pick Up Reservaion=========");
			app_logs.info("===============Uncheck Copy to Pick Up Reservaion=========");
			getTest_Steps.clear();
			getTest_Steps = group.checkCopyToPickUpReservation(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyCopyToPickUpReservation(driver, false);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Copy Pick Up Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Copy Pick Up Reservation", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify PickUp billing info

		try {
			test_steps.add("===============Verify PickUp billing info=========");
			app_logs.info("===============Verify PickUp billing info=========");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			String beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListBillingInfo(driver, AccountFirstName, AccountLastName, Phonenumber,
					Address1, city, Country, State, Postalcode, PaymentMethod, AccountNumber, ExpiryDate, false);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs
					.info("Successfully Verified not Copied Pickup Reservation Billing info in RoomList Billing info");
			test_steps.add("Successfully Verified not Copied Pickup Reservation Billing info in RoomList Billing info");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoCancel(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPickUpCancel(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Reservation billing info from book icon
		try {
			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("===============Create Reservation=========");
			app_logs.info("===============Create Reservation=========");
			reservationPage.clickNext(driver, test_steps); 
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver,test_steps, Salutation, AccountFirstName, AccountLastName,
			config.getProperty("flagOff"));
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, ExpiryDate);
            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
            reservationPage.clickBookNow(driver, test_steps);
            reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
            reservationPage.get_ReservationStatus(driver, test_steps);
            reservationPage.clickCloseReservationSavePopup(driver, test_steps);

           test_steps.add("Successfully Associated Account to  Reservation");

            app_logs.info("Successfully Associated Account to Reservation");
            
			getTest_Steps.clear();
			//getTest_Steps = res.verifyBillingInformation(driver, PaymentMethod, AccountNumber, ExpiryDate, true);
			/*
			 * reservationPage.VerifyPaymentMethod(driver, getTest_Steps, PaymentMethod,
			 * AccountNumber, NameOnCard, ExpiryDate); test_steps.addAll(getTest_Steps);
			 * 
			 * 
			 * 
			 * getTest_Steps.clear(); getTest_Steps = res.verifyBillingInformation(driver,
			 * PaymentMethod, AccountNumber, ExpiryDate, false);
			 * test_steps.addAll(getTest_Steps);
			 */

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Reservation Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Reservation Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Groups and Search Account
		try {
			test_steps.add("===============Navigate to Groups and Search Account=========");
			app_logs.info("===============Navigate to Groups and Search Account=========");
			Nav.Groups(driver);
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

		// Verify PickUp billing info

		try {
			test_steps.add("===============Verify PickUp billing info with Swipe Card=========");
			app_logs.info("================Verify PickUp billing info with Swipe Cardt=========");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");
//
//			getTest_Steps.clear();
//			getTest_Steps = group.verifyRoomingListBillingInfo(driver, AccountFirstName, AccountLastName, Phonenumber,
//					Address1, city, Country, State, Postalcode, PaymentMethod, AccountNumber, ExpiryDate, false);
//			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_BillingInfoPayments(driver, PaymentMethod);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickSwipeCardIcon(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body2");
			getTest_Steps.clear();
			getTest_Steps = group.swipeCard(driver, SwipeCard);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");
			
			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_BillingInfo(driver, AccountFirstName, AccountLastName, Phonenumber, Address1, Country, State, city);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.roomingListPopup_BillingInfoExpiry(driver, ExpiryDate);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoSaveClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoClick(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");

			getTest_Steps.clear();
			getTest_Steps = group.verifyRoomingListBillingInfo(driver, AccountFirstName, AccountLastName, Phonenumber,
					Address1, city, Country, State, Postalcode, PaymentMethod, AccountNumber, ExpiryDate, true);
			test_steps.addAll(getTest_Steps);

			Utility.app_logs.info("CC Swipe is not working in Groups - Code Fix");
			test_steps.add("CC Swipe is working in Groups - Code Fix"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-5071' target='_blank'>"
					+ "Verified : NG-5071 </a><br/>");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListBillingInfoCancel(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.roomingListPickUpCancel(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Pick Up Billing Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// account inActive
		try {
			// Nav.Groups(driver);
			// getTest_Steps.clear();
			// getTest_Steps = group.Search_Account(driver, AccountName,
			// AccountNo, true, true);
			// test_steps.addAll(getTest_Steps);
			test_steps.add("===================account inActive=============");
			app_logs.info("====================account inActive=============");
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

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
		return Utility.getData("VerifyBillInfoRoomingListAndRes", excel);
	} //

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
