package com.innroad.inncenter.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyPrimaryGuestPaySecondaryGuestChargesWhileCheckout extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> gstest_steps = new ArrayList<>();
	String testName = null;
	RoomClass rc = new RoomClass();
	Navigation nav = new Navigation();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage res = new CPReservationPage();
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> newRooms = new ArrayList<String>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	TaskManagement task_mang = new TaskManagement();
	List<String> roomCharges = new ArrayList<String>();
	List<String> guestNames = new ArrayList<String>();
	List<String> roomNos = new ArrayList<String>();
	Properties properties = new Properties();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	String reportContant = null;

	Reports report = new Reports();
	Tax tax = new Tax();
	Double depositAmount = 0.0;
	String reservation = null, panelGuestName = null, status = null, getTripTotalAmount = null, paymentMethod = null,
			taxAmount = null, checkInAmount = null, buttonName = null, checkOutAmount = null, yearDate = null,
			policyName = null, timeZone = null, propertyName = null, paidAmount = null, balance_Amount = null,seasonStartDate=null,seasonEndDate=null,
			roomClassNameNew=null,getAmount = null,roomChargeAmount = null;
	int checkoutBalance = 0;
	boolean generateGuestStatementStatus = false;
	Date currentDate = null, previousDate = null;

	double checkout_Balance = 0.00, halfAmount_Paid = 0.00, checkIn_Amount = 0.00;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyPrimaryGuestPaySecondaryGuestChargesWhileCheckout(String RoomClass,  String maxAdults,String maxPersopns,
			String roomQuantity,
			String CheckInDate, String CheckOutDate, String Adults, String Children,
			String Rateplan,String rate,  String Salutation, String GuestFirstName,
			String GuestLastName,String PaymentType, String CardNumber, String NameOnCard, 
			String Referral) throws InterruptedException, ParseException {
		String testCaseID="848449|848603|848456|848584|848757|848588";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "VerifyPrimaryGuestPaySecondaryGuestChargesWhileCheckout";
		test_description = "Verify CP MRB Check-Out Room Reservation <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682471' target='_blank'>"
				+ "Click here to open TestRail: C682471</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682487' target='_blank'>"
				+ "Click here to open TestRail: C682487</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682506' target='_blank'>"
				+ "Click here to open TestRail: C682506</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848449' target='_blank'>"
				+ "Click here to open TestRail: 848449</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848603' target='_blank'>"
				+ "Click here to open TestRail: 848603</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848456' target='_blank'>"
				+ "Click here to open TestRail: 848456</a><br>";
		test_catagory = "CP";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848449|848603|848456|848584|848757|848588", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 ArrayList<String> rateplan = new ArrayList<>();
		 ArrayList<String> roomClassNames = new ArrayList<>();
			ArrayList<String> roomClassAbb = new ArrayList<>();
			NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
			HashMap<String, String> roomChangeFolioBased= new HashMap<String, String>();
			String secondGuestAmount=null;
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			login_CP(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			seasonStartDate=checkInDates.get(0);
			seasonEndDate=sessionEndDate.get(0);
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
			rateplan=Utility.splitInputData(Rateplan);
			app_logs.info(rateplan);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		// Create Room Class
					try {
						test_steps.add("<b>======Start Creating New Room Class======</b>");
						app_logs.info("======Start Creating New Room Class======");
						nav.clickSetup(driver);
						nav.RoomClass(driver);
						test_steps.add("Navigated to Room Class");
						ArrayList<String>roomClassAre = Utility.splitInputData(RoomClass);
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
						ArrayList<String> roomNumbers= new ArrayList<String>();
						reservation=res.createBasicMRBReservation(driver, true, CheckInDate, CheckOutDate, 
								Adults, Children, Rateplan, roomClassNameNew, Salutation, GuestFirstName, GuestLastName, 
								"No", roomNumbers, PaymentType, CardNumber, NameOnCard, Referral, 
								false, test_steps);
						roomNos= res.getStayInfoRoomNo(driver, test_steps);
						app_logs.info(roomNumbers);		
						app_logs.info(roomNos);		
						roomCharges = res.getStayInfoRoomCharges(driver, test_steps);
						guestNames = res.getStayInfoGuestName(driver, test_steps);
						app_logs.info(guestNames);
						test_steps.add("<b>======Get Folio Line Room Charges  ======</b>");
						app_logs.info("======Get Folio Line Room Charges  ======");
						res.click_Folio(driver, test_steps);
						Wait.wait5Second();
						
						double charge = 0.00;
							for (int i = 0; i < roomClassNames.size(); i++) {
							res.click_FolioDetail_DropDownBox(driver, test_steps);
							res.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(i),
									roomNos.get(i));				
							HashMap<String, String> getRoomChargeForMRB = new HashMap<String, String>();
							getRoomChargeForMRB = res.getRoomChargesFromFolioBasedOnDates(driver, test_steps,
									CheckInDate, CheckOutDate);
							app_logs.info(getRoomChargeForMRB);
							for (Map.Entry<String, String> entry : getRoomChargeForMRB.entrySet()) {
								secondGuestAmount=Utility.convertDecimalFormat(entry.getValue());
								charge = charge + Double.parseDouble(Utility.convertDecimalFormat(entry.getValue()));
							}
						}
						roomChargeAmount = String.valueOf(Utility.convertDecimalFormat(String.valueOf(charge)));
						app_logs.info(roomChargeAmount);
						res.click_DeatilsTab(driver, test_steps);
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
						res.clickCheckInAllButton(driver, test_steps);
						res.generatGuestReportToggle(driver, test_steps, "No");
						res.verifySpinerLoading(driver);
						gstest_steps=res.clickOnConfirmCheckInButton(driver);
						test_steps.addAll(gstest_steps);
						res.verifySpinerLoading(driver);
						test_steps.add("<b>****Start Verifying In-House Status****</b>");
						res.verifyReservationStatusStatus(driver, test_steps, "In-House");
						test_steps.add("<b>****Start Verifying Check-Out All Button ****</b>");
						res.verifyCheckOutAllButton(driver, test_steps);
						res.click_DeatilsTab(driver, test_steps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName,
								test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Checkin Reservation MRB", "Reservation", "Reservation", testName, test_description,
								test_catagory, test_steps);
					}
					
					try {
						test_steps.add("<b>======Start CheckOut Primary Guest ======</b>");
						app_logs.info("======Start CheckOut Primary Guest ======");
						res.clickStayInfoCheckOutRoom(driver, test_steps, roomNos.get(0));
						res.verifySettlementMsgPrimaryGuest(driver, test_steps);
						res.clickSettlementYesButton(driver, test_steps);
						res.verifySpinerLoading(driver);
						res.generatGuestReportToggle(driver, test_steps, "Yes");
						res.proceedToCheckOutPayment(driver, test_steps);
						res.verifySpinerLoading(driver);
						test_steps.add("<b>======Verify Amount ======</b>");
						app_logs.info("======Verify Amount ======");
						Wait.wait10Second();
						Utility.switchTab(driver, 0);
						res.verifyAmountOnPaymentScreen(driver, roomChargeAmount, test_steps);
						res.clickLogORPayAuthorizedButton(driver, test_steps);
						res.verifySpinerLoading(driver);
						res.clickCloseButtonOfCheckoutSuccessfully(driver, test_steps);
						res.verifySpinerLoading(driver);
						res.verifyStayInforRollBackButton(driver, test_steps, roomClassNames.get(0));
						/*test_steps.add("Verify that Primary guest should able to pay secondary guest charges when primary guest select Yes while checking-out"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848603' target='_blank'>"
								+ "Click here to open TestRail: 848603</a><br>");
						Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that Primary guest should able to pay secondary guest charges when primary guest select Yes while checking-out ");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
								Utility.comments.get(1), TestCore.TestRail_AssignToID);	*/
						Wait.wait5Second();
						res.click_Folio(driver, test_steps);
						String getcardNo = Utility.getCardNumberHidden(CardNumber);
						String parimryAmount=null;
						HashMap<String, String> getRoomChargeForMRB = new HashMap<String, String>();
						getRoomChargeForMRB = res.getRoomChargesFromFolioBasedOnDates(driver, test_steps,
								CheckInDate, CheckOutDate);
						app_logs.info(getRoomChargeForMRB);
						for (Map.Entry<String, String> entry : getRoomChargeForMRB.entrySet()) {
							parimryAmount = Utility.convertDecimalFormat(entry.getValue());
						}
						
						parimryAmount=  String.valueOf(Double.parseDouble(Utility.convertDecimalFormat(roomChargeAmount))-Double.parseDouble(Utility.convertDecimalFormat(parimryAmount)));
						res.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", reservation, Utility.convertDecimalFormat(parimryAmount), test_steps);							
						res.verifyFolioLineItem(driver, checkInDates.get(0), PaymentType, getcardNo, Utility.convertDecimalFormat(roomChargeAmount), test_steps);	
						
						
						res.click_FolioDetail_DropDownBox(driver, test_steps);
						res.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),
								roomNos.get(1));	
						
						res.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", reservation, Utility.convertDecimalFormat(secondGuestAmount), test_steps);	
						/*test_steps.add("Verify the folio when primary guest pays the charges of the secondary guest & select \"Yes\" while checking-out"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848456' target='_blank'>"
								+ "Click here to open TestRail: 848456</a><br>");
						Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify the folio when primary guest pays the charges of the secondary guest & select \"Yes\" while checking-out");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
								Utility.comments.get(2), TestCore.TestRail_AssignToID);
						
						test_steps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848584' target='_blank'>"
								+ "Click here to open TestRail: 848584</a><br>");
						Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
								Utility.comments.get(3), TestCore.TestRail_AssignToID);
						
						test_steps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848757' target='_blank'>"
								+ "Click here to open TestRail: 848757</a><br>");
						Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4),
								Utility.comments.get(4), TestCore.TestRail_AssignToID);
						
						test_steps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out"
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848588' target='_blank'>"
								+ "Click here to open TestRail: 848588</a><br>");
						Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out");
						Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5),
								Utility.comments.get(5), TestCore.TestRail_AssignToID);*/
						
					}catch (Exception e) {
						Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
								test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
								test_catagory, test_steps);
					}
					try {
						test_steps.add("<b>======Start Verify Guest Statement Report ======</b>");
						app_logs.info("======Start Verify Guest Statement Report  ======");
						Wait.wait10Second();
						Utility.switchTab(driver, 1);
						
						reportContant = report.verifyGuestStatementReport(driver, test_steps);
							DateFormat dateFormat = new SimpleDateFormat("E, MMM dd, yyyy");
							String myDate = Utility.parseDate(CheckInDate, "dd/MM/yyyy", "E, MMM dd, yyyy");
							String[] name = guestNames.get(0).split(" ");
							String actualName = name[1] + " " + name[2];
							app_logs.info(actualName);
     						report.Report_Verify_ReportContent(driver, reportContant, "Guest Statement", test_steps);
							test_steps.add("Report Name: <b>" + "Guest Statement" + "</b>");
							Utility.app_logs.info("Report Name: " + "Guest Statement");
							report.Report_Verify_ReportContent(driver, reportContant, "Guest Folio", test_steps);
							test_steps.add("Folio Name: <b>" + "Guest Folio" + "</b>");
							Utility.app_logs.info("Folio Name: " + "Guest Folio");
							report.Report_Verify_ReportContent(driver, reportContant, myDate, test_steps);
							test_steps.add("Date :  <b>" + myDate + "</b>");
							Utility.app_logs.info("Date :  " + myDate);
							report.Report_Verify_ReportContent(driver, reportContant, actualName, test_steps);
							test_steps.add("Primary Gust Name :  <b>" + actualName + "</b>");
							Utility.app_logs.info("Primary Gust Name  :  " + actualName);
							report.Report_Verify_ReportContent(driver, reportContant, roomClassNames.get(0), test_steps);
							test_steps.add("Room :  <b>" + roomClassNames.get(0) + "</b>");
							Utility.app_logs.info("Room  :  " + roomClassNames.get(0));
							report.Report_Verify_ReportContent(driver, reportContant, roomNos.get(0), test_steps);
							test_steps.add("Room No:  <b>" + roomNos.get(0) + "</b>");
							Utility.app_logs.info("Room No:  " + roomNos.get(0));
							report.Report_Verify_ReportContent(driver, reportContant, reservation, test_steps);
							test_steps.add("Reservation #  :  <b>" + reservation + "</b>");
							Utility.app_logs.info("Reservation #  :  " + reservation);
							report.Report_Verify_ReportContent(driver, reportContant, roomChargeAmount, test_steps);
							test_steps.add("Room Charges  :  <b>" + roomChargeAmount + "</b>");
							Utility.app_logs.info("Room Charges   :  " + roomChargeAmount);
							report.Report_Verify_ReportContent(driver, reportContant, PaymentType, test_steps);
							test_steps.add("Payment Method :  <b>" + PaymentType + "</b>");
							Utility.app_logs.info("Payment Method   :  " + PaymentType);

							/*test_steps.add("Verified the Generate Guest Statement Report Successfully"
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/682479' target='_blank'>"
									+ "Click here to open TestRail: C682479</a><br>");
							
							test_steps.add("Verify that if user generate the Guest statement at the time of check-out then same details should display on that"
									+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848449' target='_blank'>"
									+ "Click here to open TestRail: 848449</a><br>");
							Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify that if user generate the Guest statement at the time of check-out then same details should display on that");
							Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
									Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
						Utility.deleteFile(Utility.fileName);
						test_steps.add("Deleted File  :  <b>" + Utility.fileName + "</b>");
						Utility.app_logs.info("Deleted File   :  " + Utility.fileName);

						for(int i=0;i<Utility.testId.size();i++) {
							Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Primary guest pary second guest charges while checkout");
						}
						Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
								Utility.comments, TestCore.TestRail_AssignToID);
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
					}catch (Exception e) {
						Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
								test_description, test_catagory, test_steps);
					} catch (Error e) {
						Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
								test_catagory, test_steps);
					}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyPrimaryGuestPaySecondaryG", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}

}
