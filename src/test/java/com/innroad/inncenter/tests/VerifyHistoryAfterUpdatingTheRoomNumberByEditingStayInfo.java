package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyHistoryAfterUpdatingTheRoomNumberByEditingStayInfo extends TestCore{
	

	public WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	
	Navigation navigation = new Navigation();
	RatesGrid rateGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	CPReservationPage reservationPage = new CPReservationPage();
	Groups group = new Groups();
	AdvGroups advgroup = new AdvGroups();
	Account CreateTA = new Account();
	NewRoomClassesV2 rc = new NewRoomClassesV2();
	Policies policy = new Policies();
	ReservationV2 res = new ReservationV2();
	ReservationSearch searchReservation = new ReservationSearch();
	GuestFolio guestFolio= new GuestFolio();
	FolioNew folioNew =new FolioNew();
	StayInfo stayinfo = new StayInfo();
	MerchantServices msObj = new MerchantServices();
	String seasonStartDate = null, seasonEndDate = null;
	String reservationNo;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String test_name = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + test_name.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(test_name, SanityExcel))
			throw new SkipException("Skiping the test - " + test_name);
	}
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	
	@Test(dataProvider = "getData")
	public void verifyHistoryAfterUpdatingTheRoomNumberByEditingStayInfo(String TestCaseId, String TestCaseName,String checkInDate,
		String checkOutDate, String adults, String children,String rateplan, String roomClass,String rate, String salutation, 
		String guestFirstName, String guestLastName,String marketSegment, String referral,String customFolioName,String category, String amount) throws Exception {
			boolean isExecutable= Utility.getResultForCase(driver, TestCaseId);
			if(isExecutable) {
			Utility.initializeTestCase(TestCaseId, Utility.testId, Utility.statusCode, Utility.comments, "");
			test_name = this.getClass().getSimpleName().trim();
			test_description = TestCaseId + " : " + TestCaseName;
			test_catagory = "ReservationV2";

			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + test_name + " TEST.");
			app_logs.info("EXECUTING: TEST Id's : " + TestCaseId);
			app_logs.info("##################################################################################");
			
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> checkOutDates = new ArrayList<>();
			ArrayList<String> datesRangeList = new ArrayList<String>();
			ArrayList<String> sessionEndDate = new ArrayList<>();
			
			try {

				if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
					int day = Utility.getStayDays(checkInDate, checkOutDate);
					sessionEndDate
							.add(Utility.parseDate(Utility.getDatePast_FutureDate(day + 2), "MM/dd/yyyy", "dd/MM/yyyy"));
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
					Utility.reTry.replace(test_name, Utility.reTry.get(test_name) + 1);
					System.out.println(Utility.reTry.get(test_name));
				}
				driver = getDriver();
				HS_login(driver, envURL, Utility.generateLoginCreds(SanityExcel, "ResV2_Login"));
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");

			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", test_name, test_description,
						test_catagory, test_steps);
			}
			//Create reservation and updating room number by editing stay info
			 HashMap<String, String> data = null;
				try {
					guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
					guestLastName = guestLastName + Utility.fourDigitgenerateRandomString();
					data = res.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate,
							adults, children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, "", "",
							"", "", "", "", "", "", "", "", false, marketSegment, referral,"", "", "",
							"", 0, "", false, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, ""); 
					reservationNo=data.get("ReservationNumber");
					reservationPage.closeReservationTab(driver);
					res.basicSearch_WithReservationNo(driver,reservationNo, true);
					StayInfo stayInfo = res.getStayInfoDetail(driver);
					res.clickEditStayInfo(driver);
					test_steps.add("clicked edit stay info");
					res.clickChangeStayDetails(driver);
					test_steps.add("clicked change stay details");
					res.selectStayInfoOption(driver, "Recalculate Rate");
					ArrayList<String> rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
					res.selectRoomToReserve(driver, sessionEndDate, roomClass, rooms.get(2));
					test_steps.add("updating the room number");
					res.clickSaveStayInfo(driver);
					test_steps.add("clicked on save");
					Wait.wait10Second();
					StayInfo stayInfo2 = res.getStayInfoDetail(driver);
					res.switchHistoryTab(driver, test_steps);
					res.enterTextToSearchHistory(driver, "Changed room assignment from "+stayInfo.getSI_ROOM_NUMBER() + " to " + stayInfo2.getSI_ROOM_NUMBER(), true,
							ReservationV2.HistoryTable.DESCRIPTION, 1, test_steps);
				
				}catch (Exception e) {
					Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
							test_catagory, test_steps);
				}catch (Error e) {
					Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
							test_catagory, test_steps);
				}
				
				//creating custom folio and adding charge and verifying amount
				try {
					
					res.click_Folio(driver, test_steps);
					folioNew.createCustomFolio(driver,test_steps, customFolioName);
					folioNew.addFolioLineItem(driver, test_steps, category, amount);
					ArrayList<FolioLineItem> folioLineItems = res.getAllFolioLineItems(driver);
					res.verifyFolioLineItem(folioLineItems.get(0), "Pending", true, true, Utility.getCustomDate(checkInDate,"dd/MM/yyyy","MMM d, yyyy",0), true, true,
							category, true, true,category, true, true, "1", true, true,amount, true,
							true, "0", false, false,amount,true, true, test_steps);  
				}catch (Exception e) {
					Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
							test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
							test_catagory, test_steps);
				}
				 try {
						for(int i=0;i<Utility.testId.size();i++) {
							Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify History After Updating Room Number");
						}	
						Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
								Utility.comments, TestCore.TestRail_AssignToID);
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
					} catch (Exception e) {
						Utility.catchException(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
								test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Failed", "ReservationV2", "ReservationV2", test_name, test_description,
								test_catagory, test_steps);
					}
	}
	}
	@DataProvider
	   public Object[][] getData() {
		return Utility.getData("VerifyHistoryByUpdatingRoomNum", SanityExcel);
	}
			

}
