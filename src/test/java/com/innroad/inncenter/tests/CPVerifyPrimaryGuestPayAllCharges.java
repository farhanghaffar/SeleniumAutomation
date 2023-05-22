package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyPrimaryGuestPayAllCharges extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> gstest_steps = new ArrayList<>();
	Navigation navigation = new Navigation();
	Rate rate = new Rate();
	Policies policies = new Policies();
	Folio folio = new Folio();
	CPReservationPage reservationPage = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> roomNumber = new ArrayList<>();	
	List<String> roomNos = new ArrayList<String>();
	RoomClass roomClass = new RoomClass();
	Create_Reservation cpReservation= new Create_Reservation();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	String    roomClassAbbs = null,
			 rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null, policyNameWithoutNum=null,policyname=null,
					balanceAmount=null,testName=null;			
	
	String seasonStartDate=null,seasonEndDate=null,confirmationNo=null,roomClassNameNew=null;
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	RatesGrid rateGrid = new RatesGrid();
	HashMap<String, String> roomChangeFolioBased= new HashMap<String, String>();
			
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifyPrimaryGuestPayAllCharges(String testCaseID,String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String action) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyPrimaryGuestPayAllCharges";
		test_description = "CPVerifyPrimaryGuestPayAllCharges<br>"				
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848585' target='_blank'>"
				+ "Click here to open TestRail: 848585</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848586' target='_blank'>"
				+ "Click here to open TestRail: 848586</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848587' target='_blank'>"
				+ "Click here to open TestRail: 848587</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		 List<String> roomNos= new ArrayList<String>();
		 ArrayList<String> roomNumbers= new ArrayList<String>();
			ArrayList<String> primaryRoomCharge = new ArrayList<String>();
			ArrayList<String> secondRoomCharge = new ArrayList<String>();
			double  primaryCharge=0.00, secondCharge=0.00;
			String totalCharge=null,secondGuestCharge=null;
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				seasonStartDate=checkInDates.get(0);
				seasonEndDate=sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
				rateplan=Utility.splitInputData(ratePlanName);
				app_logs.info(rateplan);

				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					//Utility.reTry.replace(testName, 1);
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}
				datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDates.get(0), checkOutDates.get(0));
				app_logs.info(datesRangeList);
				driver = getDriver();
				login_CP(driver);
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, test_steps);
			}
			// Create Room Class
			try {
				test_steps.add("<b>======Start Creating New Room Class======</b>");
				app_logs.info("======Start Creating New Room Class======");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				test_steps.add("Navigated to Room Class");
				ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
				for(int i=0;i<roomClassAre.size();i++) {
					String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
					String abbofRoomClass="RC"+i+1;
					roomClassNames.add(nameofRoomClass);
					roomClassAbb.add(abbofRoomClass);
					newRoomClass.createRoomClassV2(driver, test_steps, nameofRoomClass, abbofRoomClass, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
				}
				test_steps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
				app_logs.info("Room Class Abb: " + roomClassAbb);
				roomClassNameNew=roomClassNames.get(0)+"|"+roomClassNames.get(1);
				app_logs.info("Room Class : " + roomClassNameNew);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
						test_catagory, test_steps);
			}
			// Create Season
			try {
				test_steps.add("<b>======Start Updating Rate Plan ======</b>");
				nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, rateplan.get(0),datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
						"", "", "", false);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, test_steps);
			}
			
			try {
				test_steps.add("<b>======Start Creating Reservation ======</b>");
				app_logs.info("======Start Creating Reservation ======");
				confirmationNo=reservationPage.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, 
						adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
						"No", roomNumbers, paymentType, cardNumber, nameOnCard, referral, 
						false, test_steps);
				roomNos= reservationPage.getStayInfoRoomNo(driver, test_steps);
				app_logs.info(roomNumbers);		
				app_logs.info(roomNos);		
				test_steps.add("<b>======Get Folio Line Room Charges  ======</b>");
				app_logs.info("======Get Folio Line Room Charges  ======");
				reservationPage.click_Folio(driver, test_steps);
				Wait.wait5Second();				
				roomChangeFolioBased=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0),true);
				for (Map.Entry<String, String> entry : roomChangeFolioBased.entrySet()) {
					primaryRoomCharge.add(entry.getValue());
					primaryCharge=primaryCharge+Double.parseDouble(entry.getValue());
				}
				app_logs.info(roomChangeFolioBased);
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),
						roomNos.get(1));
				
				roomChangeFolioBased=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0),true);
				app_logs.info(roomChangeFolioBased);
				for (Map.Entry<String, String> entry : roomChangeFolioBased.entrySet()) {
					secondRoomCharge.add(entry.getValue());
					secondCharge=secondCharge+Double.parseDouble(entry.getValue());
				}
				totalCharge= Utility.convertDecimalFormat(String.valueOf(primaryCharge+secondCharge));
				secondGuestCharge=Utility.convertDecimalFormat(String.valueOf(secondCharge));
				app_logs.info(totalCharge);
				app_logs.info(secondGuestCharge);
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(0), roomNos.get(0));
				reservationPage.click_DeatilsTab(driver, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, test_steps);
			}
			
			try {
				test_steps.add("<b>======Start Checkin Reservation ======</b>");
				app_logs.info("======Start Checkin PReservation ======");
				reservationPage.clickCheckInAllButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, test_steps, "No");				
				gstest_steps=reservationPage.clickOnConfirmCheckInButton(driver);
				test_steps.addAll(gstest_steps);
				reservationPage.verifySpinerLoading(driver);
				test_steps.add("<b>****Start Verifying In-House Status****</b>");
				reservationPage.verifyReservationStatusStatus(driver, test_steps, "In-House");
				test_steps.add("<b>****Start Verifying Check-Out All Button ****</b>");
				reservationPage.verifyCheckOutAllButton(driver, test_steps);
				
				if(action.equalsIgnoreCase("takePayment")) {	
				for(int i=0;i<roomNos.size();i++) {
				reservationPage.click_DeatilsTab(driver, test_steps);
				Wait.waitforPageLoad(20, driver);
				cpReservation.clickTakePaymentofDeatilsPage(driver, test_steps);
				Wait.wait5Second();
				cpReservation.clickTakePaymentPayButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.closePaymentSuccessfullModal(driver);
				reservationPage.verifySpinerLoading(driver);
				}
				}
				test_steps.add("<b>======Start CheckOutAll Reservation ======</b>");
				app_logs.info("======Start CheckOutAll PReservation ======");
				reservationPage.clickCheckOutAllButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				if(!action.equalsIgnoreCase("takePayment")) {	
				String message="Are you sure you want to check-out all rooms at once? The Primary Guest will be responsible for all remaining Guest Charges.";
				reservationPage.verifyRollBackMsg(driver, test_steps, message);
				reservationPage.clickSettlementYesButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, test_steps, "No");
				reservationPage.proceedToCheckOutPayment(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				test_steps.add("<b>======Verify Amount ======</b>");
				app_logs.info("======Verify Amount ======");
				Wait.wait10Second();
				reservationPage.verifyAmountOnPaymentScreen(driver, totalCharge, test_steps);
				reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.clickCloseButtonOfCheckoutSuccessfully(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				}else if(action.equalsIgnoreCase("takePayment")) {	
					reservationPage.clickOnConfirmCheckOutButton(driver, test_steps);
					reservationPage.verifySpinerLoading(driver);
				}
				
			}catch (Exception e) {
				Utility.catchException(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, test_steps);
			}
			
			try {
				reservationPage.click_Folio(driver, test_steps);
				String getcardNo = Utility.getCardNumberHidden(cardNumber);
				if(!action.equalsIgnoreCase("takePayment")) {	
				test_steps.add("<b>======Verify Primary Guest All Charges======</b>");
				app_logs.info("======Verify Primary Guest Pay All Charges======");
				
				
				reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, getcardNo, Utility.convertDecimalFormat(totalCharge), test_steps);	
				reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", confirmationNo, "-"+Utility.convertDecimalFormat(secondGuestCharge), test_steps);	
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),
						roomNos.get(1));
				reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", confirmationNo, Utility.convertDecimalFormat(secondGuestCharge), test_steps);	
			
				/*test_steps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Whole Multi Reservation (MRB) is CHECKOUT ALL"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848585' target='_blank'>"
						+ "Click here to open TestRail: 848585</a><br>");
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Whole Multi Reservation (MRB) is CHECKOUT ALL");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
						Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
				} else if(action.equalsIgnoreCase("takePayment")) {						
					test_steps.add("<b>======Verify Primary Guest Amount Should be Zero and settlement of folio charges======</b>");
					app_logs.info("======Verify Primary Guest Amount Should be Zero  and settlement of folio charges======");
					reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, getcardNo, Utility.convertDecimalFormat(String.valueOf(primaryCharge)), test_steps);	
					reservationPage.verifyBalanceAmountAtFolio(driver, test_steps, "0.00");
					test_steps.add("<b>======Verify Second Guest Amount Should be Zero  and settlement of folio charges======</b>");
					app_logs.info("======Verify Second Guest Amount Should be Zero  and settlement of folio charges======");
					reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
					reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),
							roomNos.get(1));
					reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, getcardNo, Utility.convertDecimalFormat(String.valueOf(secondCharge)), test_steps);						
					reservationPage.verifyBalanceAmountAtFolio(driver, test_steps, "0.00");
					/*test_steps.add("Verify when user checkout All with balance ZERO & check the folio of secondary guest"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848586' target='_blank'>"
							+ "Click here to open TestRail: 848586</a><br>");
					Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify when user checkout All with balance ZERO & check the folio of secondary guest");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
							Utility.comments.get(1), TestCore.TestRail_AssignToID);
					
					test_steps.add("Verify Primary guest settling folios without a payment & full reservation balance must be paid before checkout"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848587' target='_blank'>"
							+ "Click here to open TestRail: 848587</a><br>");
					Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify Primary guest settling folios without a payment & full reservation balance must be paid before checkout");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
							Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
				}
				for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Parimary guest pay all charges");
				}
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, test_steps);
			}
		}
	}
	
	

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPVerifyPrimaryGuestPayAllCharg", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
