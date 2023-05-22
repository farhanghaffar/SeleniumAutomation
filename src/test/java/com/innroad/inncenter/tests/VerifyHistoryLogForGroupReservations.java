package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.MerchantServices;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_ReservationV2;

public class VerifyHistoryLogForGroupReservations extends TestCore {

	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	Users user = new Users();

	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();
	ReservationSearch searchReservation = new ReservationSearch();
	GuestFolio guestFolio = new GuestFolio();
	FolioNew folioNew = new FolioNew();
	MerchantServices msObj = new MerchantServices();
	String seasonStartDate = null, seasonEndDate = null;
	ArrayList<String> typeList = new ArrayList<String>();
	boolean isMovedToFolio = false;
	String groupAccountNo;
	String reservationNo;
	Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
	NewRoomClassesV2 rc1 = new NewRoomClassesV2();
	String Abbr;
	String noteRoom;
	String noteRoom1;
	ArrayList<String> userList = new ArrayList<>();
	String updatedBy;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, SNExcel))
			throw new SkipException("Skiping the test - " + test_name);
	}

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();

	@Test(dataProvider = "getData")
	public void verifyHistoryLogForGroupReservations(String TestCaseId, String TestCaseName, String AccountName,
			String MargetSegment, String Referral, String AccountFirstName, String AccountLastName, String Phonenumber,
			String Address1, String city, String Country, String State, String Postalcode, String PaymentMethod,
			String RoomClassName, String PayAmount, String checkInDate, String checkOutDate, String guestFirstName,
			String guestLastName, String adults, String children, String rateplan, String Salutation, String CardNumber,
			String NameOnCard, String marketSegment, String referral, String TakePaymentType,
			String IsChangeInPayAmount, String amount, String guestFirstName1, String guestLastName1,
			String guestFirstName2, String guestLastName2, String AccountType, String IsAddNotes, String NoteType,
			String NoteSubject, String NoteDescription, String selectRoom,String Abbr) throws Exception {
		//if (Utility.getResultForCase(driver, TestCaseId)) {

			Utility.initializeTestCase(TestCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
			test_name = this.getClass().getSimpleName().trim();
			test_description = TestCaseId + " : " + TestCaseName;
			test_catagory = "ReservationV2";

			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + test_name + " TEST.");
			app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
			app_logs.info("##################################################################################");
			String yearDate = Utility.getFutureMonthAndYearForMasterCard();
			String CardExpDate = Utility.getFutureMonthAndYearForMasterCard();
			String last4Digit = Utility.getCardNumberHidden(CardNumber);
			// String groupAccountNo = "";
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> checkOutDates = new ArrayList<>();
			ArrayList<String> datesRangeList = new ArrayList<String>();
			ArrayList<String> sessionEndDate = new ArrayList<>();
			String paymentMethods[] = PaymentMethod.split("\\|");

			try {

				if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate
							.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
					int day = Utility.getStayDays(checkInDate, checkOutDate);
					sessionEndDate.add(
							Utility.parseDate(Utility.getDatePast_FutureDate(day + 2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}

				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
				seasonStartDate = checkInDates.get(0);
				seasonEndDate = sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
				datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
				app_logs.info(datesRangeList);
				if (!Utility.insertTestName.containsKey(test_name)) {
					Utility.insertTestName.put(test_name, test_name);
					Utility.reTry.put(test_name, 0);

				} else {
					// Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
					Utility.reTry.replace(test_name, 1);
					System.out.println(Utility.reTry.get(test_name));
				}
				driver = getDriver();
				HS_login(driver, envURL, Utility.generateLoginCreds(SNExcel, "ResV2_Login"));
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");

			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			}
			
			  //Payment from Group Reservation
			try {
			 
			  ArrayList<String> accountNumbers = new ArrayList<>();
			  test_steps.
			  add("======================Open if Group Exsist or Creating Group ==================");
			  app_logs. info("====================== Open if Group Exsist or Creating Group  =================="
			  );
			  navigation.Groups(driver); group.createGroupAccount(driver, test_steps,
			  AccountName, true, null, marketSegment, Referral, AccountFirstName,
			 AccountLastName, Phonenumber, Address1, city, State, Country, Postalcode,
			  accountNumbers); group.clickOnSave(driver); groupAccountNo =
			  group.getAccountNo(driver);
			  
			 } catch (Exception e) {
				 Utility.catchException(driver, e, "Failed to login",
			  "Login", "Login", test_name, test_description, test_catagory, test_steps); }
			  catch (Error e){
				  Utility.catchError(driver, e, "Failed to login", "Login",
			  "Login", test_name, test_description, test_catagory, test_steps); }
			  
			 try {
				 group.click_GroupNewReservation(driver, test_steps);
			  
			  test_steps.add("====================== Group Reservation ==================");
			  
			  app_logs.info("====================== Group Reservation==================");
			 res.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate,
			  adults, children, rateplan, ""); res.clickOnFindRooms(driver, test_steps);
			  ArrayList<String> roomNumbers = new ArrayList<>(); roomNumbers =
			  res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
			  RoomClassName); res.selectRoomToReserve(driver, test_steps, RoomClassName,
			  roomNumbers.get(0)); res.clickNext(driver, test_steps); //
			  //resV2.clickOnCreateGuestButton(driver, test_steps);
			  res.turnOnOffcreateGuestProfileToggle(driver, test_steps, false);
			  res.enter_GuestName(driver, test_steps, Salutation, guestFirstName,
			  guestLastName, false); 
			  //res.clickOnGuestProfilePopupSaveButton(drivertest_steps);
			  res.enter_MarketingInfoDetails(driver, test_steps, "",
			  marketSegment, Referral, "Yes");
			  res.clickBookNow(driver, test_steps, false);
			  res.clickCloseReservationSavePopup(driver, test_steps);
			 
			  
			Wait.wait10Second();
			  
			  } catch (Exception e) { Utility.catchException(driver, e, "Failed to login",
			 "Login", "Login", test_name, test_description, test_catagory, test_steps);
			  
			  } catch (Error e) { Utility.catchError(driver, e, "Failed to login", "Login",
			  "Login", test_name, test_description, test_catagory, test_steps); }
			  
			  try { res.clickTakePayment(driver, test_steps); res.takePayment(driver,
			  test_steps, PaymentMethod, CardNumber, yearDate, CardExpDate,
			  TakePaymentType, IsChangeInPayAmount, amount, "", "", true, typeList, true);
			  res.click_Folio(driver, test_steps); folioNew.verifyFolioLineItems(driver,
			 test_steps, checkInDate, PaymentMethod, amount, yearDate, last4Digit,
			  CardExpDate); res.click_History(driver, test_steps);
			  
			  String amount1 = Utility.convertDecimalFormat(amount); String paymentStr = ""
			  + PaymentMethod + "-" + last4Digit + " " + CardExpDate; String str =
			  "Captured payment for $" + amount1 + " using (" + paymentStr + ")";
			  test_steps.add(str); app_logs.info(str); res.enterTextToSearchHistory(driver,
			  "Captured payment for $" + amount1, true,
			 ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			  } catch (Exception e)
			  { Utility.catchException(driver, e, "Failed", "ReservationV2",
			 "ReservationV2", test_name, test_description, test_catagory, test_steps); }
			  catch (Error e) { Utility.catchError(driver, e, "Failed", "RegservationV2",
			  "ReservationV2", test_name, test_description, test_catagory, test_steps); }
			  
			  //add and edit Guests 
			  try { res.navigateToReservationPage(driver);
			  res.click_NewReservation(driver,test_steps );
			  res.searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate,
			  adults, children, rateplan, ""); res.clickOnFindRooms(driver, test_steps);
			  ArrayList<String> roomNumbers = new ArrayList<>(); roomNumbers =
			  res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps,
			  RoomClassName); res.selectRoomToReserve(driver, test_steps, RoomClassName,
			  roomNumbers.get(0)); res.clickNext(driver, test_steps);
			  res.clickOnCreateGuestButton(driver, test_steps);
			  res.turnOnOffcreateGuestProfileToggle(driver, test_steps, false);
			res.enter_GuestName(driver, test_steps, Salutation, guestFirstName,
			  guestLastName, false); reservationPage.associateAccountInReservation(driver,
			  test_steps, AccountName, AccountType);
			  //res.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			  res.enter_MarketingInfoDetails(driver, test_steps, "", marketSegment,
			Referral, "Yes"); res.clickBookNow(driver, test_steps, false);
			  res.clickCloseReservationSavePopup(driver, test_steps);
			  app_logs.info("======================Adding a Guest==================");
			  
			  res.clickOnAddMoreGuestInfo(driver, test_steps);
			  res.clickOnCreateGuestButton(driver, test_steps);
			 res.turnOnOffcreateGuestProfileToggle(driver, test_steps, false);
			  res.enter_GuestName(driver, test_steps, Salutation, guestFirstName1,
			  guestLastName1, false); res.clickOnGuestProfilePopupSaveButton(driver,
			  test_steps); 
			  // res.search_GuestProfile(driver, guestProfileName, test_steps,true, false);
			  res.click_History(driver, test_steps); String str =
			  "Added additional guest"+ guestFirstName1+guestLastName1
			  +" "+"to the reservation "; test_steps.add(str); app_logs.info(str);
			  res.enterTextToSearchHistory(driver, str, true,
			  ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			  app_logs.info("====================== Editing the Guest==================");
			  
			  
			  res.switchDetailTab(driver, test_steps); res.clickEditGuestInfo(driver,
			  test_steps); reservationPage.enter_GuestName(driver, guestFirstName2,
			  guestLastName2); res.clickOnGuestProfilePopupSaveButton(driver, test_steps);
			  res.click_History(driver, test_steps);
			  
			  //String str1 = "Changed guest first name from Primary to Primary2";
			  String str1 =
			  "Changed guest first name from "+Utility.splitInputData(guestFirstName).get(0
			  )+" to "+guestFirstName2; test_steps.add(str1);
			  app_logs.info(str1);
			 res.enterTextToSearchHistory(driver, str1, true,
			  ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			 String str2 =
			  "Changed guest first name from "+Utility.splitInputData(guestLastName).get(0)
			  +" to "+guestLastName2;
			 
			  test_steps.add(str2); app_logs.info(str2);
			  res.enterTextToSearchHistory(driver, str2, true,
			  ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
			 
			  }catch (Error e) { Utility.catchError(driver, e, "Failed", "ReservationV2",
			  "ReservationV2", test_name, test_description, test_catagory, test_steps);
			  }
			  catch (Exception e) { Utility.catchException(driver, e, "Failed",
			  "ReservationV2", "ReservationV2", test_name, test_description, test_catagory,
			  test_steps);
			  
			 }
			 
			// MRB Notesnavigation.admin(driver);
			navigation.admin(driver);
			navigation.Users(driver);
			userList = user.getAllUsersList(driver, test_steps);
          // navigation.setup(driver);
          // navigation.RoomClass(driver, test_steps);
			//Abbr = rc1.getAbbreviation(driver, test_steps, RoomClassName);
			res.navigateToReservationPage(driver);
			HashMap<String, String> data1 = null;
			try {
				
				guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
				guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
				data1 = res.createReservation(driver, test_steps, "From Reservations page",
						checkInDate + "|" + checkInDate, checkOutDate + "|" + checkOutDate, adults + "|" + adults,
						children + "|" + children, rateplan + "|" + rateplan, "", RoomClassName + "|" + RoomClassName,
						"", Salutation, guestFirstName, guestLastName, "", "", "", "", "", "", "", "", "", "", false,
						marketSegment, referral, PaymentMethod, CardNumber, NameOnCard, yearDate, 0, "", false, "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", false, "");
						
				res.enter_NotesMRB(driver, test_steps, IsAddNotes, NoteType, NoteSubject, NoteDescription, true,
						selectRoom);
				
		    ArrayList<String> rooms = new ArrayList<String>();
		    /*
			for(int i=0; i<2;i++) {
				rooms.add(data1.get("RoomNumber"+(i+1)));
				noteRoom = Abbr+":"+ " " +rooms.get(i);
				res.verifyNotesDetails(driver, test_steps, NoteType, noteRoom, NoteSubject, NoteDescription, userList.get(0) );

			}
			*/
			rooms.add(data1.get("RoomNumber2"));
	        noteRoom = Abbr+":"+" " +rooms.get(1);
				
				
				res.verifyNotesDetails(driver, test_steps, NoteType, noteRoom, NoteSubject, NoteDescription, userList.get(0) );

				
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
						test_catagory, test_steps);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);

			}

			try {
				for (int i = 0; i < Utility.testId.size(); i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "VerifyHistoryLogForGroup");
				}
				Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
						Utility.comments, TestCore.TestRail_AssignToID);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
						test_catagory, test_steps);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name,
						test_description, test_catagory, test_steps);

			}
		}

	

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyHistoryLogForGroup", SNExcel);
	}
}