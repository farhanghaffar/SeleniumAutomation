package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

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
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationFolio;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationSearchCopy;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GroupReservationWithTapeChart extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
    ArrayList<String> departDates = new ArrayList<>();
    
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
	public void groupReservationWithTapeChart(String TestCaseID,String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String RoomClassName, String AvgRate, String BlockName, String RoomPerNight, String RoomClassAbv,String  arriveDate, String departDate, String ratePlan,String salutation, String guestFirstName, String guestLastName,
			String paymentMethod, String cardNumber, String nameOnCard, String isGroupPolicyApplicable,  String reservationType)
			throws InterruptedException, IOException, ParseException {

		String testName = "GroupReservationWithTapeChart";
		String test_description = "Group Reservation With TapeChart.<br>";
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554181'
		// target='_blank'>"
		// + "Click here to open TestRail: C554181</a><br/>";

		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("787937");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}

		login(testName);

		Navigation nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		AdvGroups advGrp = new AdvGroups();
		RoomClass room_class = new RoomClass();
		Rate rate = new Rate();
		Policies Create_New_Policy = new Policies();
		ReservationFolio resFolio = new ReservationFolio();
		Tapechart tc = new Tapechart();
		ReservationSearch reservationSearch = new ReservationSearch();
		CPReservationPage reservationPage = new CPReservationPage();
		//HashMap<String,String> groupNameAndAccount=new HashMap<String,String>();
		ArrayList<String> rooms= new ArrayList<String>();
		
		try {
		    if (!(Utility.validateInput(arriveDate))&&!(Utility.validateInput(departDate))){
                if (guestFirstName.split("\\|").length>1) {
                    for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
                       arrivalDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
                        departDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
                                ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
                    }
                }else{
                    arrivalDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
                    departDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
                     ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
                }
            } if (guestFirstName.split("\\|").length>1) {
            	arriveDate = arrivalDates.get(0) + "|" + arrivalDates.get(1);
            	departDate = departDates.get(0) + "|" + departDates.get(1);
            }else {

            	arriveDate = arrivalDates.get(0);
            	departDate = departDates.get(0);
            }

     
			app_logs.info(arriveDate);
			app_logs.info(departDate);	
      
		}catch(Exception e)
		{
			
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			nav.Groups(driver);

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
			//test_steps.add("========== Search Group ==========");
			//groupNameAndAccount=group.searchAccount(driver,test_steps);
			//app_logs.info(groupNameAndAccount);

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

		String roomNo1 = null, roomNo2 = null, roomNo3 = null,roomNO=null;
		try {
			
			test_steps.add("========== Create Room Block ==========");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			
			getTest_Steps.clear();
			getTest_Steps=group.createNewBlock(driver, BlockName+Utility.fourDigitgenerateRandomString(), arriveDate, departDate, ratePlan, RoomPerNight, RoomClassName);								
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			
			
		/*	getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClassName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.enterCustomRate(driver, RoomClassName, AvgRate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);  */
			

			advGrp.click_Select_Rooms(driver, test_steps);

			driver.switchTo().frame("MainContent_Iframe_AssignRooms");
			test_steps.add("=================Select room number================");
			app_logs.info("==================Select room number=================");

			roomNo1 = advGrp.select_Room_Number(driver, 2, test_steps);
			advGrp.select_Rooms_save(driver, test_steps);
			Wait.wait10Second();

			roomNo2 = advGrp.select_Room_Number(driver, 3, test_steps);
			advGrp.select_Rooms_save(driver, test_steps);
			Wait.wait10Second();

			roomNo3 = advGrp.select_Room_Number(driver, 4, test_steps);
			advGrp.select_Rooms_save(driver, test_steps);
			advGrp.Select_Rooms_done(driver, test_steps);

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
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

		try {
			test_steps.add("=================Navigate to TapeChart================");
			app_logs.info("==================Navigate to TapeChart=================");
			nav.Reservation(driver);
			Utility.app_logs.info("Navigate to Reservation Tab");
			test_steps.add("Navigate to Reservatiion Tab");

			nav.TapeChart(driver);
			Utility.app_logs.info("Navigate to TapChart Tab");
			test_steps.add("Navigate to TapChart Tab");

			test_steps.add("=================Verify Selected room in TapeChart================");
			app_logs.info("==================Verify Selected room in TapeChart=================");
			

			getTest_Steps.clear();
			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, AccountName + " : TBD");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2, AccountName + " : TBD");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3, AccountName + " : TBD");
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Groups

//		try {
//			test_steps.add("=================Navigate To Groups================");
//			app_logs.info("==================Navigate To Groups=================");
//			nav.Groups(driver);
//			Utility.app_logs.info("Navigate to Group Tab");
//			test_steps.add("Navigate to Group Tab");
//
//			getTest_Steps.clear();
//		//	getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
//			getTest_Steps = group.Search_Account(driver, groupNameAndAccount.get("GroupName"),  groupNameAndAccount.get("AccountNo"), true, true);
//			test_steps.addAll(getTest_Steps);
//			
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//		
//
//		try {
//			group.navigateRoomBlock(driver, test);
//			Utility.app_logs.info("Navigate to Room Block Tab");
//			test_steps.add("Navigate to Room Block Tab");
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		String beforePickupValue = null;
//		String beforeAvailableRoom = null;
//		String beforeBookIconClass = null;
//		try {
//			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
//			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
//			test_steps.add("Before Pickup Value : " + beforePickupValue);
//
//			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
//			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
//			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);
//
//			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
//			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
//			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);
//
//			getTest_Steps.clear();
//			getTest_Steps = group.bookIconClick(driver, RoomClassName);
//			test_steps.addAll(getTest_Steps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		String resRoomNo = null, resNo = null;
//		
//		//Create Reservation
//				try {		
//					test_steps.add("=================Create Reservation================");
//					app_logs.info("==================Create Reservation=================");
//
//					reservationPage.clickNext(driver, test_steps);
//					reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));	
//					reservationPage.clickBookNow(driver, test_steps);
//					resNo=reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
//					reservationPage.clickCloseReservationSavePopup(driver, test_steps);			
//				
//					test_steps.add("Successfully Associated Account to  Reservation");
//					app_logs.info("Successfully Associated Account to Reservation");
//					
//					/*
//					 * resRoomNo =reservationPage.getRoomNo_ResDetail(driver);
//					 * app_logs.info(resRoomNo);
//					 */
//					getTest_Steps = res.verifyGroupinStayInfo(driver, groupNameAndAccount.get("GroupName"), true);
//					test_steps.addAll(getTest_Steps);
//					res.verifyStayInfoPromoCodeIsDisplayed(driver, test_steps, true);		
//			
//					
//				}catch (Exception e) {
//					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
//							test_description, test_catagory, test_steps);
//				} catch (Error e) {
//					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
//							test_description, test_catagory, test_steps);
//
//				}
//		try {
//			test_steps.add("=================Navigate to TapeChart================");
//			app_logs.info("==================Navigate to TapeChart=================");
//			nav.Reservation(driver);
//			Utility.app_logs.info("Navigate to Reservation Tab");
//			test_steps.add("Navigate to Reservatiion Tab");
//
//			nav.TapeChart(driver);
//			Utility.app_logs.info("Navigate to TapChart Tab");
//			test_steps.add("Navigate to TapChart Tab");
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		try {
//			test_steps.add("=================Verify Selected room in TapeChart================");
//			app_logs.info("==================Verify Selected room in TapeChart=================");
//			getTest_Steps.clear();
//		/*	getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3,
//					AccountName + " : " + AccountFirstName + " " + AccountLastName);*/
//			
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3,
//					groupNameAndAccount.get("GroupName") + " : " + AccountFirstName + " " + AccountLastName);
//			
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		try {
//
//			getTest_Steps.clear();
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2, groupNameAndAccount.get("GroupName") + " : TBD");
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		try {
//			getTest_Steps.clear();
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, groupNameAndAccount.get("GroupName") + " : TBD");
//			test_steps.addAll(getTest_Steps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		try {
//
//			test_steps.add("=================Reservation room move in TapeChart================");
//			app_logs.info("==================Reservation room move in TapeChart=================");
//				getTest_Steps.clear();
//				//getTest_Steps = tc.reservationToMove(driver, roomNo3, roomNo2, RoomClassAbv, AccountName);
//				//getTest_Steps = tc.reservationToMove(driver, roomNo2, roomNo3, RoomClassAbv, groupNameAndAccount.get("GroupName"));
//				tc.moveFromToReservation(driver, roomNo2, roomNo3, RoomClassAbv);
//				test_steps.addAll(getTest_Steps);
//			} catch (Exception e) {
//				if (Utility.reTry.get(testName) == Utility.count) {
//					RetryFailedTestCases.count = Utility.reset_count;
//					Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//					Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//				} else {
//					Assert.assertTrue(false);
//				}
//
//
//			Utility.app_logs.info(
//					"Unable to move the reservation from one room to another room within the same room class - SP Fix.");
//			test_steps
//					.add("Unable to move the reservation from one room to another room within the same room class - SP Fix."
//							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-9075' target='_blank'>"
//							+ "Please Observe Manually (sometimes drag & drop functionality doesn't work ) : NG-9075 </a><br/>");
//			Utility.app_logs.info(
//					"Group block reservation is not moved to another room within the same group block.");
//			test_steps
//					.add("Group block reservation is not moved to another room within the same group block."
//							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-8053' target='_blank'>"
//							+ "Please Observe Manually (sometimes drag & drop functionality doesn't work ) : NG-8053 </a><br/>");
//		}
//		try {
//			getTest_Steps.clear();
//			test_steps.add("=================Verify Selected room in TapeChart================");
//			app_logs.info("==================Verify Selected room in TapeChart=================");
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, groupNameAndAccount.get("GroupName") + " : TBD");
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
////
//		try {
//
//			getTest_Steps.clear();
//
//			/*getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3,
//					AccountName + " : " + AccountFirstName + " " + AccountLastName);*/
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2,
//					groupNameAndAccount.get("GroupName") + " : " + AccountFirstName + " " + AccountLastName);
//
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		try {
//			getTest_Steps.clear();
//
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3, groupNameAndAccount.get("GroupName") + " : TBD");
//
//			test_steps.addAll(getTest_Steps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		Utility.app_logs.info("Inventory is getting released when room move from tapechart");
//		test_steps.add("Inventory is getting released when room move from tapechart"
//				+ "<br/><a href='https://innroad.atlassian.net/browse/NG-9013' target='_blank'>"
//				+ "Please Observe Manually, (drag & drop dependent) : NG-9013 </a><br/>");
//			
//		try {
////assign room nNO
//
//			nav.Reservation(driver);
//			test_steps.add("=================Assign room Number from Reservation================");
//			app_logs.info("==================Assign room Number from Reservation=================");
//			reservationSearch.basicSearch_WithResNumber(driver, resNo);
//			test_steps.add("Reservation Successfully Searched : " + resNo);
//			app_logs.info("Reservation Successfully Searched : " + resNo);
//
//			
//			getTest_Steps.clear();
//
//			reservationPage.clickEditReservation(driver);	
//			roomNO=reservationPage.AssignRoomNumber(driver);
//			reservationPage.clickSaveButton(driver);
//		    reservationPage.toasterMsg(driver, test_steps);
//
//			test_steps.addAll(getTest_Steps);
//
//			Utility.app_logs.info(
//					"Selected room(group:TBD) disappears in tapechart when assinging room number for picked up reservation.");
//			test_steps
//					.add("Selected room(group:TBD) disappears in tapechart when assinging room number for picked up reservation."
//							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7287' target='_blank'>"
//							+ "Verified : NG-7287 </a><br/>");
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		try {
//
//			nav.Reservation(driver);
//			Utility.app_logs.info("Navigate to Reservation Tab");
//			test_steps.add("Navigate to Reservatiion Tab");
//
//			nav.TapeChart(driver);
//			Utility.app_logs.info("Navigate to TapChart Tab");
//			test_steps.add("Navigate to TapChart Tab");
//
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//		try {
//			getTest_Steps.clear();
//
//			test_steps.add("=================Verify Selected room in TapeChart================");
//			app_logs.info("==================Verify Selected room in TapeChart=================");
//
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo1, groupNameAndAccount.get("GroupName") + " : TBD");
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
////
//		try {
//
//			getTest_Steps.clear();
//
//			/*getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo2,
//					AccountName + " : " + AccountFirstName + " " + AccountLastName);*/
//			
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNO,
//					groupNameAndAccount.get("GroupName") + " : " + AccountFirstName + " " + AccountLastName);
//			
//
//			test_steps.addAll(getTest_Steps);
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		try {
//			getTest_Steps.clear();
//
//			//getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3, AccountName + " : TBD");
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomNo3, groupNameAndAccount.get("GroupName") + " : TBD");
//
//			test_steps.addAll(getTest_Steps);
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Verify Tap Chart", testName, "Group", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//
//		} catch (Error e) {
//			test_steps.add(e.toString());
//		}
//
//		Utility.app_logs.info(
//				"Getting selected room number is no longer available error when trying to change stay details of pickedup reservation.");
//		test_steps
//				.add("Getting selected room number is no longer available error when trying to change stay details of pickedup reservation."
//						+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7288' target='_blank'>"
//						+ "Verified : NG-7288 </a><br/>");
//		
//
//		String resRoomNo2;
//		try {
//			test_steps.add("=================Unassign room for created reservation================");
//			app_logs.info("==================Unassign room for created reservation=================");
//			nav.Reservation(driver);
//			reservationSearch.basicSearch_WithResNumber(driver, resNo, true);
//			test_steps.add("Reservation Successfully Searched : " + resNo);
//			app_logs.info("Reservation Successfully Searched : " + resNo);
//
//			reservationPage.clickEditReservation(driver);	
//			resRoomNo2=reservationPage.unAssignRoomNumber(driver);
//			reservationPage.clickSaveButton(driver);
//		    reservationPage.toasterMsg(driver, test_steps);
//			test_steps.addAll(getTest_Steps);
//
//			assertEquals(resRoomNo2, "Unassigned", "Failed to Verify Reservation Room No Unassigned");
//			Utility.app_logs.info("Successfully Verified Room No Unassigned");
//			test_steps.add("Successfully Verified Room No Unassigned");
//
//			Utility.app_logs.info("Unable to change assigned group reservation to unassigned");
//			test_steps.add("Unable to change assigned group reservation to unassigned"
//					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-9084' target='_blank'>"
//					+ "Verified : NG-9084 </a><br/>");
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}
//
//
//		ReservationSearchCopy rsc = new ReservationSearchCopy();
//		try {
//			res.CopyButton(driver);
//			test_steps.add("Copy the new reservation successfully");
//			app_logs.info("Copy the new reservation successfully");
//
//			getTest_Steps.clear();
//			getTest_Steps = rsc.verifyContactInformation(driver, AccountFirstName, AccountLastName, Address1, city,
//					Country, State, Postalcode, Phonenumber);
//			test_steps.addAll(getTest_Steps);
//
//			// getTest_Steps.clear();
//			// res.clickGuestInfo_1(driver, getTest_Steps);
//			// test_steps.addAll(getTest_Steps);
//
//			getTest_Steps.clear();
//			rsc.contactInfoFirstLastName(driver, AccountFirstName, AccountLastName);
//			test_steps.addAll(getTest_Steps);
//
//			getTest_Steps.clear();
//			rsc.SaveRes_Updated(driver);
//			test_steps.addAll(getTest_Steps);
//
//			String roomClassRoomNo = rsc.getRoomNoRoomClass(driver);
//			test_steps.add("RoomClass and Room No Assigned  : " + roomClassRoomNo);
//			app_logs.info("RoomClass and Room No Assigned  : " + roomClassRoomNo);
//			System.out.println(roomClassRoomNo);
//			StringTokenizer str = new StringTokenizer(roomClassRoomNo, ":");
//			System.out.println(str.nextToken());
//			roomClassRoomNo = str.nextToken();
//			roomClassRoomNo = roomClassRoomNo.replace(" ", "");
//			System.out.println(roomClassRoomNo);
//
//			nav.Reservation(driver);
//			Utility.app_logs.info("Navigate to Reservation Tab");
//			test_steps.add("Navigate to Reservatiion Tab");
//
//			nav.TapeChart(driver);
//			Utility.app_logs.info("Navigate to TapChart Tab");
//			test_steps.add("Navigate to TapChart Tab");
//
//			getTest_Steps.clear();
//
//			test_steps.add("=================Verify Room Class color TapeChart================");
//			app_logs.info("==================Verify Room Class color TapeChart=================");
//
//			/*getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomClassRoomNo,
//					AccountName + " : " + AccountFirstName + " " + AccountLastName);*/
//			getTest_Steps = tc.verifySelectedRoom_Group(driver, RoomClassAbv, roomClassRoomNo,
//					groupNameAndAccount.get("GroupName") + " : " + AccountFirstName + " " + AccountLastName);
//			test_steps.addAll(getTest_Steps);
//
//			getTest_Steps.clear();
//			/*getTest_Steps = tc.verifyBackGroundColor_Group(driver, RoomClassAbv, roomClassRoomNo,
//					AccountName);*/
//			getTest_Steps = tc.verifyBackGroundColor_Group(driver, RoomClassAbv, roomClassRoomNo,
//					groupNameAndAccount.get("GroupName"));
//			test_steps.addAll(getTest_Steps);
//			
//			Utility.app_logs.info("Group Reservation which is copied is not showing light blue color in Tapechart");
//			test_steps.add("Group Reservation which is copied is not showing light blue color in Tapechart"
//					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-9119' target='_blank'>"
//					+ "Verified : NG-9119 </a><br/>");
//
//		} catch (Exception e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		} catch (Error e) {
//			if (Utility.reTry.get(testName) == Utility.count) {
//				RetryFailedTestCases.count = Utility.reset_count;
//				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
//				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
//			} else {
//				Assert.assertTrue(false);
//			}
//		}


		// Navigate to Groups
		try {
			test_steps.add("=================Navigate to Groups================");
			app_logs.info("==================Navigate to Groups=================");
			nav.Groups(driver);
			Utility.app_logs.info("Navigate to Group Tab");
			test_steps.add("Navigate to Group Tab");

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			//getTest_Steps = group.Search_Account(driver, groupNameAndAccount.get("GroupName"), groupNameAndAccount.get("AccountNo"), true, true);
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

		// account inActive
		try {
			test_steps.add("=================Account inactive================");
			app_logs.info("==================Account inactive=================");
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			comments= "Verified the Group Name: "+AccountName+ " is displayed on Tape chart";
					  
			statusCode.set(0, "1");

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
		return Utility.getData("GroupReservationWithTapeChart", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
