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
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyCheckinSecondaryGuest extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage reservation = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	String seasonStartDate=null,seasonEndDate=null,confirmationNo=null,roomClassNameNew=null,
			roomCharge = null,roomChargeAmount = null,guestName=null, totalRoomCharge=null;
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	RatesGrid rateGrid = new RatesGrid();
	Create_Reservation reservationPage = new Create_Reservation();
	HashMap<String, String> roomChangeFolioBased= new HashMap<String, String>();
	double charge = 0.00;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private void changeStatus(String changeStatus) throws InterruptedException {
		
		reservation.clickCloseRollBackMsg(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		reservation.reservationStatusPanelSelectStatus(driver, changeStatus,testSteps);
		reservation.verifySpinerLoading(driver);
	}
	private void verifyCheckinWindow(String windowName,String salutation, String firstName, String lastName,String status, String confirmationNo, 
			String adults, String children, String checkIn, String checkOut,
			String roomClass, String ratePlan, String roomCharge ) throws InterruptedException, ParseException {
				
		testSteps.add("<b>======Verify CheckIn Popup Detail======</b>");
		ArrayList<String> sals= Utility.splitInputData(salutation);
		 guestName = "" + sals.get(1) + " " + firstName + " " + lastName + "";
		reservation.verifyReservationPopWindow(driver, windowName, guestName, status, confirmationNo,
				testSteps, "");
		reservation.verifyGuestContactInfo(driver, testSteps, sals.get(1), firstName, lastName, "",
				"");
		ArrayList<String> ad= Utility.splitInputData(adults);
		ArrayList<String> ch= Utility.splitInputData(children);
		app_logs.info(ad);
		app_logs.info(ch);		
		reservationPage.verifyCheckInStayInfoSingleReservation(driver,  checkIn, checkOut, ad.get(1),
				ch.get(1), roomClass, ratePlan, Utility.convertDecimalFormat(roomCharge), testSteps);
		
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifyCheckinSecondaryGuest(String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String checkInPolicyName, String typeOfFees, String percentage, String chargesTypes,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral) throws ParseException {
		String testCaseID="848614|848589|848505|848615";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "CPVerifyCheckinSecondaryGuest";
		test_description = "CPVerifyCheckinSecondaryGuest<br>"				
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848614' target='_blank'>"
				+ "Click here to open TestRail: 848614</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848589' target='_blank'>"
				+ "Click here to open TestRail: 848589</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848505' target='_blank'>"
				+ "Click here to open TestRail: 848505</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848615' target='_blank'>"
				+ "Click here to open TestRail: 848615</a><br>";
				
		

		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848614|848589|848505|848615", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 Policies policies = new Policies();
		 
		 List<String> roomNos= new ArrayList<String>();
		 ArrayList<String> roomNumbers= new ArrayList<String>();
		 HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
			
			List<String> roomCharges = new ArrayList<String>();
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
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
				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");

			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, testSteps);
			}
			// Create Room Class
			try {
				roomClassNames.clear();
				roomClassAbb.clear();
				testSteps.add("<b>======Start Creating New Room Class======</b>");
				app_logs.info("======Start Creating New Room Class======");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				testSteps.add("Navigated to Room Class");
				ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
				for(int i=0;i<roomClassAre.size();i++) {
					String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
					String abbofRoomClass="RC"+i+1;
					roomClassNames.add(nameofRoomClass);
					roomClassAbb.add(abbofRoomClass);
					newRoomClass.createRoomClassV2(driver, testSteps, nameofRoomClass, abbofRoomClass, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
				}
				testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
				app_logs.info("Room Class Abb: " + roomClassAbb);
				roomClassNameNew=roomClassNames.get(0)+"|"+roomClassNames.get(1);
				app_logs.info("Room Class : " + roomClassNameNew);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
						test_catagory, testSteps);
			}

			try {
				testSteps.add("<b>======Start Creating Policy ======</b>");
				navigation.inventoryFromRoomClass(driver, testSteps);
				navigation.policies(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				checkInPolicyName = checkInPolicyName + Utility.generateRandomStringWithoutNumbers();
				polictiesNames = policies.createPolicies(driver, testSteps, "", "", "Check-in", "", "",
							checkInPolicyName, "", typeOfFees, percentage, chargesTypes, "", "", "No", "");
				app_logs.info(polictiesNames);
				app_logs.info(checkInPolicyName);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
						test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
						test_catagory, testSteps);
			}			
			// Create Season
			try {
				testSteps.add("<b>======Start Updating Rate Plan ======</b>");
				nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0),datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
						"", "", "", false);
				Utility.refreshPage(driver);
				nightlyRate.switchCalendarTab(driver, testSteps);
				nightlyRate.selectSeasonDates(driver, testSteps, datesRangeList.get(0));
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);
				nightlyRate.clickSeasonPolicies(driver, testSteps);
				nightlyRate.selectPolicy(driver, "Check-in", checkInPolicyName, true, testSteps);
				nightlyRate.clickSaveSason(driver, testSteps);
				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
				rateGrid.closeRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				testSteps.add("<b>======Start Creating Reservation ======</b>");
				app_logs.info("======Start Creating Reservation ======");
				confirmationNo=reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, 
						adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
						"No", roomNumbers, paymentType, cardNumber, nameOnCard, referral, 
						false, testSteps);
				roomNos= reservation.getStayInfoRoomNo(driver, testSteps);
				app_logs.info(roomNumbers);		
				app_logs.info(roomNos);		
				roomCharges = reservation.getStayInfoRoomCharges(driver, testSteps);
				testSteps.add("<b>======Get Folio Line Room Charges  ======</b>");
				app_logs.info("======Get Folio Line Room Charges  ======");
				reservation.click_Folio(driver, testSteps);
				Wait.wait5Second();
				reservation.includeTaxesinLineItems(driver, testSteps, true);
				roomChangeFolioBased=reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(0), checkOutDates.get(0));
				app_logs.info(roomChangeFolioBased);
				reservation.click_FolioDetail_DropDownBox(driver, testSteps);
				reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassAbb.get(1),
						roomNos.get(1));				
				roomChangeFolioBased=reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDates.get(1), checkOutDates.get(1));
				app_logs.info(roomChangeFolioBased);
				double charge = 0.00;
				for (Map.Entry<String, String> entry : roomChangeFolioBased.entrySet()) {
					charge = charge + Double.parseDouble(Utility.convertDecimalFormat(entry.getValue()));
				}
				roomChargeAmount = String.valueOf(charge);
				roomChargeAmount = reservation.calculationOfCheckInAmountToBePaidForRateV2(roomChargeAmount,
						percentage);
				app_logs.info(roomChargeAmount);
				reservation.click_DeatilsTab(driver, testSteps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				testSteps.add("<b>==========Start Check In Secondary Guest==========</b>");
				app_logs.info("<b>==========Start Check In Secondary Guest==========</b>");
				roomCharge = roomCharges.get(1).replace("$", "");
				app_logs.info(roomCharge);
				Wait.wait5Second();
				reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));	
				testSteps.add("<b>======Verify CheckIn  Window Verifying STAY INFO For Reserved Status======</b>");
				app_logs.info("<b>======Verify CheckIn  Window Verifying STAY INFO For Reserved Status======</b>");
				verifyCheckinWindow("Check In",salutation,Utility.secondGuestFirstName,Utility.secondGuestLastName,"RESERVED",confirmationNo,
						adults,children,checkInDates.get(1), checkOutDates.get(1),roomClassNames.get(1), rateplan.get(1),roomCharge);
				changeStatus("Guaranteed");
				testSteps.add("<b>======Verify CheckIn  Window Verifying STAY INFO For Guaranteed Status======</b>");
				app_logs.info("<b>======Verify CheckIn  Window Verifying STAY INFO For Guaranteed Status======</b>");
				Wait.wait5Second();
				reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));		
				verifyCheckinWindow("Check In",salutation,Utility.secondGuestFirstName,Utility.secondGuestLastName,"Guaranteed",confirmationNo,
						adults,children,checkInDates.get(1), checkOutDates.get(1),roomClassNames.get(1), rateplan.get(1),roomCharge);
				changeStatus("Confirmed");
				testSteps.add("<b>======Verify CheckIn  Window Verifying STAY INFO For Confirmed Status======</b>");
				app_logs.info("<b>======Verify CheckIn  Window Verifying STAY INFO For Confirmed Status======</b>");
				Wait.wait5Second();
				reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));
				verifyCheckinWindow("Check In",salutation,Utility.secondGuestFirstName,Utility.secondGuestLastName,"Confirmed",confirmationNo,
						adults,children,checkInDates.get(1), checkOutDates.get(1),roomClassNames.get(1), rateplan.get(1),roomCharge);
				
				/*testSteps.add("Verify Header bar in check in pop up while checking in Secondary guest when status of Primary guest is 'Reserved'/ 'Guarenteed'/'Confirmed'"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848614' target='_blank'>"
						+ "Click here to open TestRail: C848614</a><br>");
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Header bar in check in pop up while checking in Secondary guest when status of Primary guest is 'Reserved'/ 'Guarenteed'/'Confirmed'");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
						Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
	
				changeStatus("Reserved");
				
				/*testSteps.add("Verify Header bar in check in/checkout/cancel  pop up for  Secondary guest when status of Primary guest is 'Reserved'/ 'Guarenteed'/'Confirmed'"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/Verify Header bar in check in/checkout/cancel  pop up for  Secondary guest when status of Primary guest is 'Reserved'/ 'Guarenteed'/'Confirmed'' target='_blank'>"
						+ "Click here to open TestRail: C848505</a><br>");
				Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify Header bar in check in/checkout/cancel  pop up for  Secondary guest when status of Primary guest is 'Reserved'/ 'Guarenteed'/'Confirmed'");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5),
						Utility.comments.get(5), TestCore.TestRail_AssignToID);*/
				
				testSteps.add("<b>====== CheckIn Secondary Guest ======</b>");
				app_logs.info("<b>====== CheckIn Secondary Guest ======</b>");
				reservation.verifySpinerLoading(driver);
				Wait.wait5Second();
				reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));
				reservation.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
				reservation.clickOnProceedToCheckInPaymentButton(driver, testSteps);
				
				/*testSteps.add("Verify that user is able to check in the secondary guest of a multi-room reservation."
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848589' target='_blank'>"
						+ "Click here to open TestRail: C848589</a><br>");
				Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that user is able to check in the secondary guest of a multi-room reservation.");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
						Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
				
				testSteps.add("<b>======  Verify Policy and Checkin Amount ======</b>");
				app_logs.info("<b>====== Verify Policy and Checkin Amount ======</b>");
				Wait.wait30Second();
				reservation.verifySpinerLoading(driver);
				reservation.verifyReservationPopWindowPolicyName(driver, testSteps, checkInPolicyName, "Check In");
				String checkinAmount=reservation.getCheckInAmount(driver);
				Utility.verifyText(Utility.convertDecimalFormat(checkinAmount), roomChargeAmount, "Verified Checkin Amount", testSteps, app_logs);
				reservation.clickLogORPayAuthorizedButton(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				reservation.clickCloseButtonOfCheckInSuccessfully(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				testSteps.add("<b>======Verify Reservation Status After Checkin Second Guest ======</b>");
				app_logs.info("<b>======Verify Reservation Status After Checkin Second Guest ======</b>");
				reservation.verifyReservationStatusStatus(driver, testSteps, "RESERVED");
					
			}catch (Exception e) {
				Utility.catchException(driver, e, "Checkin Second Guest", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Checkin Second Guest", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
			
			try {
				
				testSteps.add("<b>==========Start Verify Checkout Window of Second Guest==========</b>");
				app_logs.info("<b>==========Start Verify Checkout Window of Second Guest==========</b>");
				reservation.clickStayInfoCheckOutRoom(driver, testSteps, roomNos.get(1));
				reservation.clickSettlementYesButton(driver, testSteps);
				reservation.verifySpinerLoading(driver);	
				ArrayList<String> sals= Utility.splitInputData(salutation);
				guestName = "" + sals.get(1) + " " + Utility.secondGuestFirstName + " " + Utility.secondGuestLastName + "";
				reservation.verifyReservationPopWindow(driver, "Check Out", guestName, "IN-HOUSE", confirmationNo,
						testSteps, "Check Out");	
				reservation.clickCloseRollBackMsg(driver, testSteps);
				reservation.verifySpinerLoading(driver);
				
				/*testSteps.add("Verify Header bar in check out pop up while checking out Secondary guest when status of Primary guest is 'Reserved'/ 'Guaranteed'/'Confirmed'"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848615' target='_blank'>"
						+ "Click here to open TestRail: 848615</a><br>");
				Utility.testCasePass(Utility.statusCode, 14, Utility.comments, "Verify Header bar in check out pop up while checking out Secondary guest when status of Primary guest is 'Reserved'/ 'Guaranteed'/'Confirmed'");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(14), Utility.statusCode.get(14),
						Utility.comments.get(14), TestCore.TestRail_AssignToID);*/
				}catch (Exception e) {
				Utility.catchException(driver, e, "Checkin Second Guest", "Reservation", "Reservation", testName,
						test_description, test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Checkin Second Guest", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}

				try {
				
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify second guest pay primary charges");
					}
					
					Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
							Utility.comments, TestCore.TestRail_AssignToID);
				testSteps.add("<b>======Delete Policy======</b>");
				app_logs.info("<b>======Delete Policy ======</b>");
				navigation.inventory(driver);
				testSteps.add("Navigated to Inventory");
				navigation.policies(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				ArrayList<String> policyDelete = new ArrayList<String>();
				policyDelete.add(checkInPolicyName);
				policies.deleteAllPolicies(driver, testSteps, policyDelete);
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Delete Policy", "Reservation", "Reservation", testName, test_description,
						test_catagory, testSteps);
			}
		}
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("CPVerifyCheckinSecondaryGuest", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {		
		driver.quit();
		
	}

}
