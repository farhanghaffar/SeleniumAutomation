package com.innroad.inncenter.tests;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
public class VerifyArrivalDueONTapeChart extends TestCore{	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	Tapechart tapeChart= new Tapechart();
	CPReservationPage reservationPage = new CPReservationPage();
	RoomStatus roomstatus = new RoomStatus();
	ReservationSearch revSearch = new ReservationSearch();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);		
	}	
	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyArrivalDueOnTapeChart(String checkInDate, String checkOutDate, String roomClassName, String  adults,
			String children,String ratePlanName,String salutation,String guestFirstName, String guestLastName, 
			 String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral) throws ParseException {		
		String testCaseID="850181";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "Verify Arrival Due On Tape Chart";
		String testName = test_name;
		test_description = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850181' target='_blank'>"
				+ "Click here to open TestRail: 850181</a><br>";
		test_catagory = "verifyArrivalDueOnTapeChart";
		Utility.initializeTestCase("850181", Utility.testId, Utility.statusCode,Utility.comments,"");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String  roomNo=null,yearDate=null,randomNum = null,reservation=null,room=null;
		boolean isExist=false,isExists=false;		
		 ArrayList<String> abbrivation= new ArrayList<String>();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}		
			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}	
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
						app_logs.info(checkInDate);
			app_logs.info(checkOutDate);			
		} catch (Exception e) {
			Utility.catchException(driver,e, "Get Date", "Get Date", "Get Date", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver,e, "Get Date", "Get Date", "Get Date", testName, test_description,
					test_catagory, test_steps);
		}		
		// Login
				try {
					driver = getDriver();
					//login_CP(driver);
					loginRateV2(driver);
					test_steps.add("Logged into the application");
					app_logs.info("Logged into the application");	
				} catch (Exception e) {
					Utility.catchException(driver,e, "Login", "Login", "Login", testName, test_description,
							test_catagory, test_steps);
				} catch (Error e) {
					Utility.catchError(driver,e, "Login", "Login", "Login", testName, test_description,
							test_catagory, test_steps);
				}
		try {
			test_steps.add("========== Get Room Class Abbrivation ==========");
			app_logs.info("========== Get Room Class Abbrivation==========");			
			navigation.setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.roomClass(driver);
			abbrivation=newRoomclass.getAbbrivation(driver, Utility.DELIM, roomClassName,test_steps);	
		}catch (Exception e) {
			Utility.catchException(driver,e, "Get Abbrivation", "Get Abbrivation", "Get Abbrivation", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver,e, "Get Abbrivation", "Get Abbrivation", "Get Abbrivation", testName, test_description,
					test_catagory, test_steps);
		}		
		try {
			test_steps.add("========== Checking Availibility On Tape Chart ==========");
			app_logs.info("========== Checking Availibility On Tape Chart==========");	
			navigation.navigateToReservation(driver);
			navigation.navigateToTapeChartFromReservations(driver);
			tapeChart.searchRoomClassInTapeChart(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlanName);
			isExist=tapeChart.isRoomClassAvailableInTapeChart(driver, roomClassName);
		
		}catch (Exception e) {
			Utility.catchException(driver,e, "Check Availibility in Tape Chart", "Check Availibility in Tape Chart", "Check Availibility in Tape Chart", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver,e, "Check Availibility in Tape Chart", "Check Availibility in Tape Chart", "Check Availibility in Tape Chart", testName, test_description,
					test_catagory, test_steps);
		}		
		try {
			yearDate = Utility.getFutureMonthAndYearForMasterCard();
			randomNum = Utility.fourDigitgenerateRandomString();
			guestFirstName=guestFirstName+ randomNum;
			guestLastName=guestLastName+ randomNum;
			if(isExist) {
				test_steps.add("Rooms Available On Tape Chart");
				app_logs.info("Rooms Available On Tape Chart");	
				test_steps.add("========== Create Reservation from Tape Chart ==========");
				app_logs.info("========== Create Reservation from Tape Chart==========");	
				roomNo=tapeChart.clickAvailableSlot(driver, roomClassName, test_steps);
				List<String> rooms= new ArrayList<String>();
				rooms.add(roomNo);
				app_logs.info(rooms);
				reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation,
						guestFirstName+ randomNum, guestLastName+ randomNum, "No");
				reservationPage.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
				reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
				reservationPage.clickBookNow(driver, test_steps);
				reservation = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				List<String> currentRoom=reservationPage.getStayInfoRoomNo(driver, test_steps);
				app_logs.info(currentRoom);
				List<String> currentRoom1= new ArrayList<String> ();
				currentRoom1.add(currentRoom.get(0));
				revSearch.closeReservation(driver);	
				test_steps.add("========== Verify Reservation On Tape Chart ==========");
				app_logs.info("========== Verify Reservation On Tape Chart==========");	
				tapeChart.verifyReservationOnTapeChart(driver, roomClassName, roomNo, guestFirstName, test_steps);
				test_steps.add("========== Verify Arrival Due On GS for Room Number "+roomNo+"==========");
				app_logs.info("========== Verify Arrival Due On GS for Room Number "+roomNo+"==========");	
				navigation.clickOnGuestServices(driver, test_steps);
				test_steps.add("Navigated to Guest Services");
				roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
				roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
				roomstatus.verifyArrivalDueofSpecificRoomNoAndRoomClass(driver, roomNo, roomClassName, test_steps);
				test_steps.add("========== Checking Availibility On Tape Chart ==========");
				app_logs.info("========== Checking Availibility On Tape Chart==========");	
				navigation.navigateToReservationFromGuestServices(driver, test_steps);
				navigation.navigateToTapeChartFromReservations(driver);	
				tapeChart.searchRoomClassInTapeChart(driver, abbrivation, checkInDate, checkOutDate, adults, children, ratePlanName);				
				isExists=tapeChart.isRoomClassAvailableInTapeChart(driver, roomClassName);
					if(isExists) {
					room=tapeChart.getkAvailableSlotRoomNo(driver, roomClassName, test_steps);
					test_steps.add("========== Move Revervation from Room No " +roomNo+" to "+room+"==========");
					app_logs.info("========== Move Revervation from Room No " +roomNo+" to "+room+"==========");	
					tapeChart.moveReservationfromOneRoomtoAnotherRoomforSameRoomClass(driver, roomClassName, roomNo, room,test_steps);
					test_steps.add("========== Verify Reservation On Tape Chart ==========");
					app_logs.info("========== Verify Reservation On Tape Chart==========");	
					tapeChart.verifyReservationOnTapeChart(driver, roomClassName, room, guestFirstName, test_steps);
					test_steps.add("========== Verify Arrival Due On GS for Room Number "+room+"==========");
					app_logs.info("========== Verify Arrival Due On GS for Room Number "+room+"==========");	
					navigation.clickOnGuestServices(driver, test_steps);
					test_steps.add("Navigated to Guest Services");
					roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
					roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
					roomstatus.verifyArrivalDueofSpecificRoomNoAndRoomClass(driver, room, roomClassName, test_steps);
				}else {
					test_steps.add("========== No Room Availble On Tape Chart To Move Reservation==========");
					app_logs.info("========== No Room Availble On Tape Chart To Move Reservation ==========");	
				}
			}else {
				test_steps.add("========== No Room Availble On Tape Chart ==========");
				app_logs.info("========== No Room Availble On Tape Chart ==========");	
			}			
			
			
			/*test_steps.add("Verify Arrival Due label on the Room Status tile ->Tape Chart"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850181' target='_blank'>"
					+ "Click here to open TestRail: 850181</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Arrival Due label on the Room Status tile ->Tape Chart");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
	*/
			
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver,e, "Create Reservation", "Create Reservation", "Create Reservation", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver,e, "Create Reservation", "Create Reservation", "Create Reservation", testName, test_description,
					test_catagory, test_steps);
		}
		}
	}	
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyArrivalDueONTapeChart", gsexcel);
	}
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
