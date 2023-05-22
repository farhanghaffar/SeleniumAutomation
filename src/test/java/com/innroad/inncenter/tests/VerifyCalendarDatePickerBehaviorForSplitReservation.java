package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyCalendarDatePickerBehaviorForSplitReservation extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> Rooms = new ArrayList<String>();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "RegressoionCases_bakar")
	public void verifyCalendarDatePickerBehaviorForSplitReservation(String testCaseId, String Rateplan, String RoomClass) throws ParseException {
		test_name = "VerifyCalendarDatePickerBehaviorForSplitReservation";
		testDescription = "Verify Regression Cases for Split Reservation <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848251' target='_blank'>"
				+ "Click here to open TestRail: C848251</a>";
		testCategory = "VerifyCalendarBehaviourForSplitRes";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage reservationPage = new CPReservationPage();
		ReservationHomePage homePage = new ReservationHomePage();
		ReservationSearch reservationSearch = new ReservationSearch();

		if(!Utility.validateString(testCaseId)) {
			caseId.add("848251");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseId.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		String randomString = Utility.GenerateRandomNumber(3);

		String CheckInDate = "NA";
		String CheckOutDate = "NA";
		String Account = "";
		String CardExpDate = "12/23";
		String CardNumber = "5454545454545454";
		String Description = "Note Description";
		String IsAddNotes = "Yes";
		String IsSplitRes = "";
		String IsTask = "Yes";
		String NameOnCard = "Auto User";
		String NoteType = "Internal";
		String PaymentType = "MC";
		String Subject = "TestSubject" + randomString;
		String PromoCode = "";
		String Referral = "Other";		
		String Adults = "2|2";
		String Children = "1|2";
		String salutation = "Mr.";
		String guestFirstName ="Split" + randomString;
		String guestLastName = "Res" + randomString;	
		String[] roomClassArr = RoomClass.split("\\|");
		
	
		String TripSummaryRoomCharges=null,TripSummaryBalance=null, reservation = null;
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < roomClassArr.length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkInDates.get(1) + "|" + checkInDates.get(2);
			}
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "GetDatesForSplitReservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
			e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "GetDatesForSplitReservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}
		// Login
			try {
				driver = getDriver();
				loginClinent3281(driver);
				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Reservation
			try {
				app_logs.info(CheckInDate + CheckOutDate + Adults + Children + Rateplan + PromoCode + IsSplitRes);
//824739
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824739' target='_blank'>"
						+ "<b> ===== Verifying that Checkin date of next split by default set to checkout date of previous split ===== </b> </a>");
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				
				reservationPage.clickOnSplitResButton(driver, testSteps);
				
				reservationPage.clickOnAddARoomButton(driver, testSteps);
				app_logs.info("Clicking on add room");

				reservationPage.clickOnAddARoomButton(driver, testSteps);
				app_logs.info("Clicking on add room");
				
				ArrayList<String> getCheckInDates = new ArrayList<>();
				ArrayList<String> getCheckOutDates = new ArrayList<>();
				
				for (int i = 0; i < roomClassArr.length + 2; i++) {
					getCheckInDates.add(reservationPage.getCheckInDates(driver, i));
					getCheckOutDates.add(reservationPage.getCheckOutDates(driver, i));
				}
				
				for( int i=0; i < getCheckOutDates.size(); i++) {
					if( i >= 1 && (i != getCheckOutDates.size() - 1)) {
						printString("i : "  +  i);
						Utility.verifyEquals(getCheckInDates.get(i + 1), getCheckOutDates.get(i), testSteps);
						printString("true : " + getCheckInDates.get(i + 1) + " : " + getCheckOutDates.get(i));
					}else {
						printString("i else : " +  i);
					}
				}
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				testSteps.add("===== Verifying Error Message 'Please ensure that the splits for the reservation are contiguous and do not overlap' is displaying when checkout date of both splits are same. =====");

				reservationPage.click_NewReservation(driver, testSteps);	
				reservationPage.clickOnAddARoomButton(driver, testSteps);
				app_logs.info("Clicking on add room");

				reservationPage.clickOnSplitResButton(driver, testSteps);				
			 	assertTrue(reservationPage.isSplitResErrorDiplayed(driver), "Failed : Error Message 'Please ensure that the splits for the reservation are contiguous and do not overlap' is not displaying");

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");

				testSteps.add("===== Verifying Error Message 'Please ensure that the splits for the reservation are contiguous and do not overlap' is displaying when checkout date of first split is greater than of checkin date of next split. =====");

				reservationPage.click_NewReservation(driver, testSteps);
				String CheckInDate1 = Utility.getCurrentDate("dd/MM/yyyy") + "|" + Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
				String CheckOutDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy") + "|" + Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy");
	
				reservationPage.select_Dates(driver, testSteps, CheckInDate1, CheckOutDate1, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				
				reservationPage.clickOnSplitResButton(driver, testSteps);				
			 	assertTrue(reservationPage.isSplitResErrorDiplayed(driver), "Failed : Error Message 'Please ensure that the splits for the reservation are contiguous and do not overlap' is not displaying");

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				statusCode.add(0, "1");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
 					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that Checkin date of next split by default set to checkout date of previous split", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that Checkin date of next split by default set to checkout date of previous split", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {
//825001
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825001' target='_blank'>"
						+ "<b> ===== Verifying that clicking on red 'X' will remove a room from the multi or split room reservation. ===== </b> </a>");

				reservationPage.click_NewReservation(driver, testSteps);
				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkInDates.get(1) + "|" + checkInDates.get(2);
	
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);				
			 	homePage.clickRemoveMultiRoomSearchIcon(driver, testSteps, 1);
				
				assertFalse(homePage.isSplitResCheckboxDisplayed(driver), "Failed to verify that clicking on red 'X' will remove a room from the multi or split room reservation.");
				testSteps.add("Verified that clicking on red 'X' will remove a room from the multi or split room reservation and reservation into single-room search mode.");
				
				testSteps.add("===== Verifying that on adding a second room to the reservation in the same manner it would on a brand new search and displays checkbox to specify split-room. =====");
				int b = homePage.getNumberOfCheckInDatesInputs(driver);
				reservationPage.clickOnAddARoomButton(driver, testSteps);
				app_logs.info("Clicking on add room");
				
				int c = homePage.getNumberOfCheckInDatesInputs(driver);
				assertEquals(c, b+1, "Failed to verify that new search for second room is displaying on clicking add room button.");
				testSteps.add("Verified that new search for second room is displaying on clicking add room button.");		
				
				assertTrue(homePage.isSplitResCheckboxDisplayed(driver), "Failed to verify that on adding a second room to the reservation in the same manner it would on a brand new search and displays checkbox to specify split-room.");
			 	testSteps.add("Verified that on adding a second room to the reservation in the same manner it would on a brand new search and displays checkbox to specify split-room.");
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				statusCode.add(1, "1");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
 					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that clicking on red 'X' will remove a room from the multi or split room reservation.", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
			        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that clicking on red 'X' will remove a room from the multi or split room reservation.", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Reservation
			try 
			{
//825401				
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825401' target='_blank'>"
						+ "===== <b> Verify that Both the note and the reservation should have the same time stamp as both were saved at the same time. </b> ===== </a>");

				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkInDates.get(1) + "|" + checkInDates.get(2);

				IsSplitRes = "Yes";
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, testSteps, "1");
					reservationPage.enter_Children(driver, testSteps, "0");
					reservationPage.select_Rateplan(driver, testSteps, Rateplan.split("\\|")[0], PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[0]);
				reservationPage.selectRoomToReserve(driver, testSteps, roomClassArr[0], rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        app_logs.info("rooms : " + rooms);
		        rooms.clear();
		        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[1]);
		        app_logs.info("rooms : " + rooms);
		        reservationPage.selectRoomToReserve(driver, testSteps, roomClassArr[1], rooms.get(1));
		        reservationPage.verifySpinerLoading(driver);
		        
				reservationPage.click_Next(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				}
				reservationPage.SelectReferral(driver, Referral);
				
				printString("" + Rooms);				
				reservationPage.clickBookNow(driver, testSteps);
				reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);

				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824619' target='_blank'>"
						+ "===== <b> Verify whether room move task is created for the split room reservations for GS enabled clients </b> ===== </a>");
				reservationPage.closeReservationTab(driver);
				reservationPage.Search_ResNumber_And_Click(driver, reservation);
				
			//	homePage.verifyRoomMoveTaskIsCreated(driver, testSteps);					
				statusCode.add(2, "1");
				
				reservationPage.enter_Notes(driver, testSteps, IsAddNotes, NoteType, Subject,Description);
				
				testSteps.add("===== <b> Verify that Both the note and the reservation should have the same time stamp as both were saved at the same time. </b> =====");
				String getText = homePage.getUpdatedByText(driver);				
				String getNotesUpdateByDate = homePage.getNotesUpdatedOnText(driver);
				
				Utility.verifyEquals(getText, getNotesUpdateByDate, testSteps);
				testSteps.add("Verified that Both the note and the reservation should have the same time stamp as both were saved at the same time.");

				testSteps.add("===== <b> Verify that Both the updated note and the reservation should have the same time stamp as both were saved at the same time after notes updation. </b> =====");
				
				homePage.updateNotes(driver, testSteps, NoteType, Subject+randomString,Description + randomString);				
				getText = homePage.getUpdatedByText(driver);				
				getNotesUpdateByDate = homePage.getNotesUpdatedOnText(driver);
				
				Utility.verifyEquals(getText, getNotesUpdateByDate, testSteps);
				testSteps.add("Verified that Both the note and the reservation should have the same time stamp as both were saved at the same time after notes updation.");

				statusCode.add(3, "1");
				statusCode.add(4, "1");
				statusCode.add(5, "1");
				statusCode.add(6, "1");
				statusCode.add(7, "1");


			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that both the note and the reservation should have the same time stamp as both were saved at the same time", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that both the note and the reservation should have the same time stamp as both were saved at the same time", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
			try {
//825380
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825380' target='_blank'>"
						+ "===== <b> Verify room no for the split room is updating properly in reservation page. </b> ===== </a>");
				
				ArrayList<String> getRoomNumbers = new ArrayList<>();
				
				for(int b=0; b < roomClassArr.length; b++) {
					getRoomNumbers.add(homePage.getReservationRoomNumber(driver, b));
				}
				
				reservationPage.click_Folio(driver, testSteps);
				
				ArrayList<String> getFolioRoomNumbers = new ArrayList<>();
				ArrayList<String> getFolioDates = new ArrayList<>();
				for(int b=0; b < roomClassArr.length; b++) {
					getFolioRoomNumbers.add(homePage.getFolioRoomNumber(driver, b));
					getFolioDates.add(homePage.getFolioDates(driver, b));
				}
				
				reservationPage.click_DeatilsTab(driver, testSteps);				
				homePage.assignNewRoomNumber(driver, testSteps);
				printString("Stopeed to debug");
				ArrayList<String> getRoomNumbersAfterReassigning = new ArrayList<>();
				
				for(int b=0; b < roomClassArr.length; b++) {
					getRoomNumbersAfterReassigning.add(homePage.getReservationRoomNumber(driver, b));
				}
				
				reservationPage.click_Folio(driver, testSteps);
				
				ArrayList<String> getFolioRoomNumbersAfterReassigning = new ArrayList<>();
				ArrayList<String> getFolioDatesAfterReassigning = new ArrayList<>();
				for(int b=0; b < roomClassArr.length; b++) {
					getFolioRoomNumbersAfterReassigning.add(homePage.getFolioRoomNumber(driver, b));
					getFolioDatesAfterReassigning.add(homePage.getFolioDates(driver, b));
				}
				
				app_logs.info("getFolioRoomNumbers : " + getFolioRoomNumbers);
				app_logs.info("getFolioDates : " + getFolioDates);
				app_logs.info("getFolioRoomNumbersAfterReassigning : " + getFolioRoomNumbersAfterReassigning);
				app_logs.info("getFolioDatesAfterReassigning : " + getFolioDatesAfterReassigning);
				for(int b=0; b < roomClassArr.length; b++) {
					Utility.verifyEquals(getFolioRoomNumbers.get(b), getRoomNumbers.get(b), testSteps);
					Utility.verifyEquals(getFolioRoomNumbersAfterReassigning.get(b), getFolioRoomNumbersAfterReassigning.get(b), testSteps);
					
					//Utility.verifyEquals(checkInDates.get(b), ESTTimeZone.reformatDate(getFolioDates.get(b), "E MMM dd, yyyy", "dd/MM/yyyy") , testSteps);
				}
				testSteps.add("Verified that room no for the split room is updating properly in reservation page.");
			
				statusCode.add(8, "1");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify room no for the split room is updating properly in reservation page.", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify room no for the split room is updating properly in reservation page.", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {
//825186			
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825186' target='_blank'>"
						+ "===== <b> Verify that popup message 'Are you sure you wish to change the total cost of the stay from $XX.XX to $0.00?' is not showing </b> ===== </a>");

				reservationPage.click_DeatilsTab(driver, testSteps);

				reservationPage.ClickEditStayInfo(driver, testSteps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

				reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);

				try {
				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);
				}catch (Exception e) {
					e.printStackTrace();
				}
				String getRoomClass = reservationPage.getRoomClass(driver, RoomClass.split("\\|")[0]);
				app_logs.info("getRoomClass : " + getRoomClass);

				reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", "");

				reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", "");

				// add select method to do
				testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo2(driver));
				

				assertFalse(homePage.isChangeRateMessageDisplayed(driver), "Failed to verify that popup message 'Are you sure you wish to change the total cost of the stay from $XX.XX to $0.00?' is not showing");
			 	testSteps.add("Verified that popup message 'Are you sure you wish to change the total cost of the stay from $XX.XX to $0.00?' is not showing");
			 	
			 	reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
				statusCode.add(9, "1");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that popup message 'Are you sure you wish to change the total cost of the stay from $XX.XX to $0.00?' is not showing", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify that popup message 'Are you sure you wish to change the total cost of the stay from $XX.XX to $0.00?' is not showing", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			try {
//825057		
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/825057' target='_blank'>"
						+ "===== <b> Verify the void charges for unused night for split room reservation </b> ===== </a>");

				reservationSearch.basicSearchWithResNumber(driver, reservation, true);
				testSteps.add("Searched and opened reservation with number : " + reservation);
				app_logs.info("Searched and opened reservation with number : " + reservation);

				homePage.changeResStatus(driver, testSteps, "Cancel");
				
				TripSummaryRoomCharges=reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, testSteps);
				TripSummaryBalance=reservationPage.get_TripSummaryBalanceWithCurrency(driver, testSteps);
				
				assertEquals(TripSummaryRoomCharges.trim(), "$ 0.00", "Failed : Trip summary room charges didn't changed to zero after cancellation.");
				assertEquals(TripSummaryBalance.trim(), "$ 0.00", "Failed : Trip summary balance didn't changed to zero after cancellation.");
				testSteps.add("Verified the void charges for unused night for split room reservation.");
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close Reservation tab");
				statusCode.add(10, "1");
				

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify the void charges for unused night for split room reservation", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify the void charges for unused night for split room reservation", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
		//77200986		

			try 
			{
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/77200986' target='_blank'>"
						+ "===== <b> Verify checkin for the split reservation from reservation page </b> ===== </a>");

				CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				CheckOutDate = checkInDates.get(1) + "|" + checkInDates.get(2);

				IsSplitRes = "Yes";
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, testSteps, "1");
					reservationPage.enter_Children(driver, testSteps, "0");
					reservationPage.select_Rateplan(driver, testSteps, Rateplan.split("\\|")[0], PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[0]);
				reservationPage.selectRoomToReserve(driver, testSteps, roomClassArr[0], rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        app_logs.info("rooms : " + rooms);
		        rooms.clear();
		        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[1]);
		        app_logs.info("rooms : " + rooms);
		        reservationPage.selectRoomToReserve(driver, testSteps, roomClassArr[1], rooms.get(1));
		        reservationPage.verifySpinerLoading(driver);
		        
				reservationPage.clickNext(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				}
				reservationPage.SelectReferral(driver, Referral);
				
				printString("" + Rooms);				
				reservationPage.clickBookNow(driver, testSteps);
				reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				String status=reservationPage.get_ReservationStatus(driver, testSteps);
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);

				reservationPage.Click_CheckInButton(driver, testSteps);
				reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
				getTestSteps.clear();
				getTestSteps = homePage.clickConfirmChekInButton(driver);
				testSteps.addAll(getTestSteps);

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickPayButton(driver);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
				}

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {

				}
				testSteps.add("Reservation checked in");
				app_logs.info("Reservation checked in");

				testSteps.add("<b> ===== Verify Default status for reservation is set to 'In-House' ===== </b>");

				String reservationStatus = homePage.getReservationStatusFromHeader(driver);

				Utility.verifyEquals(reservationStatus, "IN-HOUSE", getTestSteps);
				testSteps.add("Verified Default status for reservation is set to 'In-House'");

				
			 	reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
				

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify checkin for the split reservation from reservation page", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify checkin for the split reservation from reservation page", testName, "SplitReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			String isAssign = "Yes";
			String checkInDate = Utility.getCurrentDate("MM/dd/yyyy");
			String checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy");
			String maxAdult = "1";
			String maxPerson = "0";
			String salutation1 = "Mr.";
			String guestFirstName1 = "VerifyDefaultStatus" + randomString;
			String guestLastName1 = "Res" + randomString;
			String referral = "Other";
			try {
				testSteps.add("<b> ===== Verify checkin for the single reservation from reservation page ===== </b>");
				app_logs.info("===== Verify checkin for the split reservation from reservation page =====");
				reservationPage.click_NewReservation(driver, testSteps);
				
				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, checkInDate);
				testSteps.addAll(getTestSteps);
				app_logs.info(getTestSteps);
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver, checkOutDate);
				testSteps.addAll(getTestSteps);

				reservationPage.enter_Adults(driver, testSteps, maxAdult);
				reservationPage.enter_Children(driver, testSteps, maxPerson);
				reservationPage.select_Rateplan(driver, testSteps, Rateplan.split("\\|")[0], "");
				reservationPage.clickOnFindRooms(driver, testSteps);
				reservationPage.select_Room(driver, testSteps, roomClassArr[0], isAssign, "");
				reservationPage.clickNext(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation1, guestFirstName1,
						guestLastName1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				reservationPage.SelectReferral(driver, referral);
				
				reservationPage.clickBookNow(driver, testSteps);
				reservationPage.get_ReservationConfirmationNumber(driver, testSteps);

				reservationPage.clickCloseReservationSavePopup(driver, testSteps);

				reservationPage.Click_CheckInButton(driver, testSteps);
				reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
				getTestSteps.clear();
				getTestSteps = homePage.clickConfirmChekInButton(driver);
				testSteps.addAll(getTestSteps);

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickPayButton(driver);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
				}

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {

				}
				testSteps.add("Reservation checked in");
				app_logs.info("Reservation checked in");


				testSteps.add("<b> ===== Verify Default status for reservation is set to 'In-House' ===== </b>");

				String reservationStatus = homePage.getReservationStatusFromHeader(driver);

				Utility.verifyEquals(reservationStatus, "IN-HOUSE", getTestSteps);
				testSteps.add("Verified Default status for reservation is set to 'In-House'");

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				statusCode.add(11, "1");
				
			
				RetryFailedTestCases.count = Utility.reset_count;
			    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify checkin for the single reservation from reservation page", testName, "SingleReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify checkin for the single reservation from reservation page", testName, "SingleReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}


/*
			try {

				testSteps.add("===== Verify the void charges for unused night for split room reservation =====");
				IsSplitRes = "Yes";
						
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					Adults = "1";
					Children = "0";
					reservationPage.enter_Adults(driver, testSteps, "1");
					reservationPage.enter_Children(driver, testSteps, "0");
					reservationPage.select_Rateplan(driver, testSteps, Rateplan.split("\\|")[0], PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[0]);
				reservationPage.selectRoomToReserve(driver, testSteps, RoomClass.split("\\|")[0], rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        rooms.clear();
		        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[1]);
		        reservationPage.selectRoomToReserve(driver, testSteps, RoomClass.split("\\|")[1], rooms.get(2));
		        reservationPage.verifySpinerLoading(driver);
		        
				reservationPage.clickNext(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				}
				reservationPage.SelectReferral(driver, Referral);
				
				printString("" + Rooms);				
				reservationPage.clickBookNow(driver, testSteps);
				String reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				String status=reservationPage.get_ReservationStatus(driver, testSteps);
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);

						
				String reservation = "18539563";
			 	reservationSearch.basicSearchWithResNumber(driver, reservation, true);
				testSteps.add("Searched and opened reservation with number : " + reservation);
				app_logs.info("Searched and opened reservation with number : " + reservation);

				reservationPage.click_Folio(driver, testSteps);
				
				reservationPage.makePayment(driver, Rooms, IsChangeInPayAmount);
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			String roomClassNameWithoutNum, rateNameWithoutNum;
			String roomClassName = "SplitRoomClass";
			String roomClassAbb = "SRC";
			String rateName = "SplitRate";
			String maxAdults = "5";
			String maxPersons = "5";
			String roomQuantity = "1";
			String additionalAdult = "1";
			String additionalChild = "1";
//18539533
			roomClassNameWithoutNum = roomClassName;
			roomClassName = roomClassName + randomString;
			roomClassAbb = "SRC"+ randomString;
			
			rateNameWithoutNum =rateName;
			rateName = "TestRate" + randomString;

			String displayName = rateName;
			String ratePolicy = rateName;
			String rateDescription = rateName;
			String associateSeason = rateName;
			String ratePlan = Rateplan.split("\\|")[0];
	
			try {
			
				testSteps.add("========== Remove Existing Rates And RoomClasses ==========");
				navigation.Inventory(driver);
				navigation.Rate(driver);
				rate.deleteRates(driver, rateNameWithoutNum);
				navigation.Setup(driver, testSteps);
				navigation.RoomClass(driver, testSteps);
				roomClass.SearchClasses(driver, roomClassNameWithoutNum);
				roomClass.deleteRoomClass(driver, roomClassNameWithoutNum);
				
				testSteps.add("========== Creating 1st room class ==========");
				
				navigation.clickOnNewRoomClassButton(driver, testSteps);
				roomClass.CreateRoomClass(driver, roomClassName, roomClassAbb, null, maxAdults, maxPersons, roomQuantity,
						test, testSteps);
				roomNumberAssigned.add(Utility.RoomNo);
				roomClass.CloseOpenedRoomClass(driver, roomClassName, testSteps);
				navigation.NewRoomClass(driver);
				
				testSteps.add("========== Creating 1st rate plan and Associating with 1st room class ==========");
				try {
					navigation.Reservation_Backward_1(driver);
				} catch (Exception e) {
					navigation.Reservation_Backward_3(driver);
				}
				navigation.Inventory(driver);
				navigation.Rates1(driver);
				rate.new_Rate(driver, rateName, ratePlan, maxAdults, maxPersons, "10", additionalAdult,
						additionalChild, displayName, associateSeason, ratePolicy, rateDescription, roomClassName,
						testSteps);
	
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
	
			try {

				testSteps.add("===== Verify that user is not able to book a split reservation with partial assignment of room class. =====");
				IsSplitRes = "Yes";
				navigation.Reservation_Backward(driver);
				
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, ratePlan, PromoCode,IsSplitRes);
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, testSteps, "1");
					reservationPage.enter_Children(driver, testSteps, "0");
					reservationPage.select_Rateplan(driver, testSteps, ratePlan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				
				ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[0]);
				reservationPage.selectRoomToReserve(driver, testSteps, RoomClass.split("\\|")[0], rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        rooms.clear();
		        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassName.split("\\|")[1]);
		        reservationPage.selectRoomToReserve(driver, testSteps, roomClassName, rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        
				reservationPage.clickNext(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				}
				reservationPage.SelectReferral(driver, Referral);
				
				printString("" + Rooms);				
				reservationPage.click_Quote(driver, testSteps);
				
				String reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				String status=reservationPage.get_ReservationStatus(driver, testSteps);
				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

				
				reservationPage.click_NewReservation(driver, testSteps);
				reservationPage.select_Dates(driver, testSteps, CheckInDate, CheckOutDate, Adults, Children, ratePlan, PromoCode,IsSplitRes);
				if(IsSplitRes.equalsIgnoreCase("Yes")) {
					reservationPage.enter_Adults(driver, testSteps, "1");
					reservationPage.enter_Children(driver, testSteps, "0");
					reservationPage.select_Rateplan(driver, testSteps, ratePlan, PromoCode);
				}
				reservationPage.clickOnFindRooms(driver, testSteps);
				
				rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, RoomClass.split("\\|")[0]);
				reservationPage.selectRoomToReserve(driver, testSteps, RoomClass.split("\\|")[0], rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        rooms.clear();
		        rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, testSteps, roomClassName.split("\\|")[1]);
		        reservationPage.selectRoomToReserve(driver, testSteps, roomClassName, rooms.get(0));
		        reservationPage.verifySpinerLoading(driver);
		        
				reservationPage.clickNext(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
						guestLastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard,
						CardExpDate);
				}
				reservationPage.SelectReferral(driver, Referral);
				
				printString("" + Rooms);				
				reservationPage.clickBookNow(driver, testSteps);
				
				String reservation2=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				String status2=reservationPage.get_ReservationStatus(driver, testSteps);

				reservationPage.clickCloseReservationSavePopup(driver, testSteps);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
		
			 	reservationSearch.basicSearchWithResNumber(driver, reservation, true);
				testSteps.add("Searched and opened reservation with number : " + reservation);
				app_logs.info("Searched and opened reservation with number : " + reservation);
				
				
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
			    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
			    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
				
				testSteps.add("===================== Deleting all rate plans created during test run =====================");
				navigation.Inventory(driver);
				navigation.Rates1(driver);			
				rate.deleteAllRates(driver, testSteps, rateName);

				testSteps.add("===================== Deleting all room classes created during test run =====================");
				navigation.Setup(driver, testSteps);
				navigation.RoomClass(driver, testSteps);
				roomClass.deleteAllRoomClasses(driver, roomClassName, testSteps);

				statusCode="1";				
				RetryFailedTestCases.count = Utility.reset_count;
			    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
			
		}catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}	
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
				    Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to delete test data created during execution", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}	
	*/

	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyCalendarForSplitRes", excel_Swarna);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
