package com.innroad.inncenter.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyEarlyCheckoutMRB extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	CPReservationPage reservation = new CPReservationPage();
	String abbri=null,seasonStartDate=null,seasonEndDate=null,confirmationNo=null,getAmount=null;
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	

	private void checkinAndCheckout(String roomClassName, String guestName, String confirmationNo, String voidRoomChanrge) throws InterruptedException {
		testSteps.add("<b>======Start Checkin Reservation======</b>");
		app_logs.info("<b>======Start Checkin Reservation======</b>");
		reservation.clickCheckInAllButton(driver, testSteps);
		reservation.generatGuestReportToggle(driver, testSteps, "No");
		reservation.completeCheckInProcess(driver, testSteps);		
		reservation.verifyCheckOutAllButton(driver, testSteps);
		reservation.verifyReservationStatusStatus(driver, testSteps, "In-House");
		testSteps.add("<b>======Checkout  Primary Guest and Settle Second Guest Charges=====</b>");
		app_logs.info("<b>======Checkout  Primary Guest and Settle Second Guest Charges======</b>");
		reservation.checkoutRoom(driver, roomClassName, testSteps);
		String message = "Do you wish to settle all guest's remaining charges against the primary guest's folio?";
		if(voidRoomChanrge.equalsIgnoreCase("Yes")) {
		reservation.clickYesButtonOfCheckOutAllConfirmationMsg(driver, testSteps, message, "CheckOutAll", "","");
		}else if(voidRoomChanrge.equalsIgnoreCase("No")) {
			reservation.clickSettlementNoButton(driver, testSteps);
		}
		reservation.verifyReservationPopWindow(driver, "Check Out", guestName, "In-House", confirmationNo, testSteps,
				"Check Out");		
		String checkOutMsg = "Are you sure you want to check out this reservation?";
		reservation.verifyMessageOfCheckOutWindow(driver, testSteps, checkOutMsg);
		reservation.Verify_VoidRoomCharge(driver, testSteps, "Check Out");
		reservation.checkedVoidRoomChargeCheckBox(driver, testSteps);	
		if(voidRoomChanrge.equalsIgnoreCase("Yes")) {
		reservation.generatGuestReportToggle(driver, testSteps, "Yes");
		}else if(voidRoomChanrge.equalsIgnoreCase("No")) {
			reservation.generatGuestReportToggle(driver, testSteps, "No");
		}
		reservation.proceedToCheckOutPayment(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		Wait.wait30Second();
		getAmount = reservation.getAmountFromPaymentVerificationPage(driver);
		reservation.clickLogORPayAuthorizedButton(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		reservation.clickCloseButtonOfCheckoutSuccessfully(driver, testSteps);
		reservation.verifySpinerLoading(driver);
		testSteps.add("<b>======Start Verifying Stay Info Roll Back Button======</b>");
		reservation.verifyStayInforRollBackButton(driver, testSteps, roomClassName);
		testSteps.add("<b>======Start Verifying DEPARTED Status======</b>");
		reservation.verifyReservationStatusStatus(driver, testSteps, "DEPARTED");
		testSteps.add("<b>======Start Verifying Roll Back All Button After Check-Out Room======</b>");
		reservation.verifyRollBackButton(driver, testSteps);

		}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void cPVerifyEarlyCheckoutMRB(String testCaseID,
			String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity,String ratePlanName, String rate,String checkInDate, String checkOutDate, 
			String adults, String children,String salutation,
			String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard,String referral,String voidCharge) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		String testName = null, getcardNo=null,roomClassNameNew=null;
		String reportContant = null,testCase=null;
		Reports report = new Reports();
		test_name = "VerifyEarlyCheckOut";
		test_description = "Verify Early CheckOut for Primary Guest<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848436' target='_blank'>"
				+ "Click here to open TestRail: 848436</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848449' target='_blank'>"
				+ "Click here to open TestRail: 848449</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848596' target='_blank'>"
				+ "Click here to open TestRail: 848596</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848433' target='_blank'>"
				+ "Click here to open TestRail: 848433</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848450' target='_blank'>"
				+ "Click here to open TestRail: 848450</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848756' target='_blank'>"
				+ "Click here to open TestRail: 848756</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848757' target='_blank'>"
				+ "Click here to open TestRail: 848757</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848129' target='_blank'>"
				+ "Click here to open TestRail: 848129</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848434' target='_blank'>"
				+ "Click here to open TestRail: C848434</a><br>";
		test_catagory = "CP";
		testName = test_name;
		HashMap<String, String> beforeCheckingetDate= new HashMap<String, String>();
		HashMap<String, String> getDate= new HashMap<String, String> ();
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		Utility.initializeTestCase(testCaseID,Utility.testId, Utility.statusCode, Utility.comments,"");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		NightlyRate nightlyRate= new NightlyRate();
		RoomClass roomClass = new RoomClass();
		Navigation navigation = new Navigation();
		
		NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		ArrayList<String> roomNumber= new ArrayList<String> ();
		List<String> roomNumbers= new ArrayList<String> ();
		List<String> guestNames = new ArrayList<String>();
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
			datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
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
					navigation.clickSetup(driver);
					navigation.RoomClass(driver);
					testSteps.add("Navigated to Room Class");
					ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
					for(int i=0;i<roomClassAre.size();i++) {
						String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
						String abbofRoomClass="ECHK"+i+1;
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
				
				// Create Season
				try {
					testSteps.add("<b>======Start Updating Rate Plan ======</b>");
					nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0),datesRangeList,
							seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
							"", "", "", true);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
							test_catagory, testSteps);
				}
				
				// Create Reservation
				try {				
					
					confirmationNo=reservation.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, 
							adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
							"No", roomNumber, paymentType, cardNumber, nameOnCard, referral, 
							false, testSteps);
					app_logs.info(roomNumber);
					roomNumbers= reservation.getStayInfoRoomNo(driver, testSteps);
					app_logs.info(roomNumbers);		
					 beforeCheckingetDate=reservation.getStayInfoCheckInCheckOutDate(driver);
					app_logs.info(beforeCheckingetDate);
					guestNames = reservation.getStayInfoGuestName(driver, testSteps);
					app_logs.info(guestNames);
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation and Verify Room Status of GS with Arrival Due", "Reservation", "Reservation", testName, test_description,
							test_catagory, testSteps);
				}
				// Checkin Reservation and Checkout Primary Guest
				try {
					ArrayList<String> sal=Utility.splitInputData(salutation);
					String guestName= ""+sal.get(0)+" "+Utility.guestFirstName+" "+Utility.guestLastName+"";
					app_logs.info(guestName);
					checkinAndCheckout(roomClassNames.get(0),guestName,confirmationNo,voidCharge);	
					getDate=reservation.getStayInfoCheckInCheckOutDate(driver);
					app_logs.info(getDate);
					String date=Utility.parseDate(getDate.get("ChecOUT"), "MMM dd, yyyy", "dd/MM/yyyy");
					testSteps.add("<b>======Verify Folio Line Item======</b>");
					Utility.switchTab(driver, 0);
					Wait.wait5Second();
					reservation.click_Folio(driver, testSteps);
					reservation.includeTaxesinLineItems(driver, testSteps, true);
					reservation.click_FolioDetail_DropDownBox(driver, testSteps);					
					reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassAbb.get(0), roomNumbers.get(0));
					HashMap<String, String>  getRoomCharge=reservation.getRoomChargesFromFolioBasedOnDates(driver, testSteps, checkInDate, date);
					String negativeAmt=null;
					for(Map.Entry<String, String> entry: getRoomCharge.entrySet()) {			
						String roomChargeAmount=entry.getValue();
						app_logs.info(roomChargeAmount);
					   negativeAmt=  String.valueOf(Double.parseDouble(Utility.convertDecimalFormat(getAmount))- Double.parseDouble(Utility.convertDecimalFormat(roomChargeAmount)));
						
					}
					negativeAmt=Utility.convertDecimalFormat(negativeAmt);
					app_logs.info(negativeAmt);
					getcardNo=Utility.getCardNumberHidden(cardNumber);
					String description =getcardNo;
					app_logs.info(description);
					reservation.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, description,
							getAmount,testSteps);		
					if(voidCharge.equalsIgnoreCase("Yes")) {
					testSteps.add("<b>====== Verifying Void room charges for unused nights ======</b>");
					testSteps.add("<b>====== Primary Guest ======</b>");
					String secondaryGuestName = Utility.secondGuestFirstName;
					 description="Res #"+confirmationNo+" - "+secondaryGuestName+"";
					System.out.println(description);
					reservation.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", description,
							negativeAmt,testSteps);		
					
					testSteps.add("<b>======Secondary Guest ======</b>");
					reservation.click_Folio(driver, testSteps);
					reservation.click_FolioDetail_DropDownBox(driver, testSteps);
					reservation.clickFolioDetailOptionValue(driver, testSteps, roomClassAbb.get(1), roomNumbers.get(1));
					description="Reservation "+confirmationNo+"";
					reservation.verifyFolioLineItem(driver, checkInDates.get(0), "Reservation", description,
							negativeAmt,testSteps);
					}
				} catch (Exception e) {
					Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName, test_description,test_catagory, testSteps);							
				}				
				try {
					testSteps.add("<b>======Start Verifying History======</b>");
					
					// Click History Tab
					reservation.click_History(driver, testSteps);
					String historyDesc = "Checked out this reservation";
					String paymentMethod="("+paymentType+"-"+getcardNo+" "+Utility.expiryDate+")";
					app_logs.info(paymentMethod);
					reservation.verifyHistoryForCheckin(driver, testSteps, historyDesc, roomClassAbb.get(0),roomNumbers.get(0),
							"CheckOutSingleRoom", roomClassAbb, roomClassAbb);						
					reservation.verifyHistoryDescriptionWithPayment(driver, testSteps, getAmount, "Check Out", roomClassAbb.get(0),
							roomNumbers.get(0), paymentMethod);
					
					if(voidCharge.equalsIgnoreCase("Yes")) {
					/*testSteps.add("Verify that in MRB if any guets is checked-out then entry should update in history log with room number"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848450' target='_blank'>"
							+ "Click here to open TestRail: 848450</a><br>");
					Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify that in MRB if any guets is checked-out then entry should update in history log with room number");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
				*/	}
					reservation.verifyChangedCheckOutDateOnHistoryTab(driver, testSteps, beforeCheckingetDate.get("ChecOUT"), getDate.get("ChecOUT"));
					if(voidCharge.equalsIgnoreCase("Yes")) {
						
						/*testSteps.add("Verify early Check out for Primary guest."
								+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848596' target='_blank'>"
								+ "Click here to open TestRail: 848596</a><br>"
								+  "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848434' target='_blank'>"
								+ "Click here to open TestRail: C848434</a><br>");
						
					Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify early Check out for Primary guest.");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
					
					Utility.testCasePass(Utility.statusCode, 8, Utility.comments, "Verify early Check out for Primary guest.");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(8), Utility.statusCode.get(8), Utility.comments.get(8), TestCore.TestRail_AssignToID);
				
					testSteps.add("Verify Early Check Out All for MRB reservation"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848436' target='_blank'>"
							+ "Click here to open TestRail: 848436</a><br>");
					
					Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Early Check Out All for MRB reservation");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
				
					
					testSteps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation early Check Out"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848756' target='_blank'>"
							+ "Click here to open TestRail: 848756</a><br>");
					Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation early Check Out");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
				
					testSteps.add("Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848757' target='_blank'>"
							+ "Click here to open TestRail: 848757</a><br>");
					Utility.testCasePass(Utility.statusCode, 6, Utility.comments, "Verify Secondary Folio should Paid with Primary Reservation and balance should be ZERO If Primary Reservation Check Out");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
					
					testSteps.add("Early Check out"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848129' target='_blank'>"
							+ "Click here to open TestRail: C848129</a><br>");
					Utility.testCasePass(Utility.statusCode, 7, Utility.comments, "Early Check out");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(7), Utility.statusCode.get(7), Utility.comments.get(7), TestCore.TestRail_AssignToID);*/
					
					}else if(voidCharge.equalsIgnoreCase("No")) {
					/*testSteps.add("Verify early Check out for individual room from MRB"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848433' target='_blank'>"
							+ "Click here to open TestRail: 848433</a><br>");
				
					Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify early Check out for individual room from MRB");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
				
			*/		
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Verify History", "Reservation", "Reservation", testName,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Verify History", "Reservation", "Reservation", testName, test_description,
							 test_catagory, testSteps);
							
				}
				
				try {
					
					if(voidCharge.equalsIgnoreCase("Yes")) {
					testCase="Verify that if user generate the  Guest statement at the time of check-out then same details should display on that";
					if (!Utility.insertTestName.containsKey(testCase)) {
						Utility.insertTestName.put(testCase, testCase);
						Utility.reTry.put(testCase, 0);
					} else {
						Utility.reTry.replace(testCase, 1);
					}
					testSteps.add("<b>======Start Verifying Guest Statement Report After Checkout Primary Guest======</b>");
					app_logs.info("<b>======Start Verifying Guest Statement Report After Checkout Primary Guest======</b>");					
					reservation.switchToTabTwo(driver, testSteps);
					String myDate=Utility.parseDate(checkInDates.get(0), "dd/MM/yyyy", "E, MMM dd, yyyy");
					app_logs.info(myDate);
					String[] name = guestNames.get(0).split(" ");
					String actualName = name[1] + " " + name[2];
					app_logs.info(actualName);
					String roomNo = roomNumbers.get(0).trim();
					String room_Class = roomClassNames.get(0).trim();
					app_logs.info(" Room No: " + roomNo);
					app_logs.info(" Room Class: " + room_Class);
					reportContant = report.verifyGuestStatementReport(driver, testSteps);
					report.Report_Verify_ReportContent(driver, reportContant, "Guest Statement", testSteps);
					testSteps.add("Report Name: <b>" + "Guest Statement" + "</b>");
					Utility.app_logs.info("Report Name: " + "Guest Statement");
					report.Report_Verify_ReportContent(driver, reportContant, "Guest Folio", testSteps);
					testSteps.add("Folio Name: <b>" + "Guest Folio" + "</b>");
					Utility.app_logs.info("Folio Name: " + "Guest Folio");
					report.Report_Verify_ReportContent(driver, reportContant, myDate, testSteps);
					testSteps.add("Date :  <b>" + myDate + "</b>");
					Utility.app_logs.info("Date :  " + myDate);
					report.Report_Verify_ReportContent(driver, reportContant, actualName, testSteps);
					testSteps.add("Primary Gust Name :  <b>" + actualName + "</b>");
					Utility.app_logs.info("Primary Gust Name  :  " + actualName);
					report.Report_Verify_ReportContent(driver, reportContant, room_Class, testSteps);
					testSteps.add("Room :  <b>" + room_Class + "</b>");
					Utility.app_logs.info("Room  :  " + room_Class);
					report.Report_Verify_ReportContent(driver, reportContant, roomNo, testSteps);
					testSteps.add("Room No:  <b>" + roomNo + "</b>");
					Utility.app_logs.info("Room No:  " + roomNo);
					report.Report_Verify_ReportContent(driver, reportContant, confirmationNo, testSteps);
					testSteps.add("Reservation #  :  <b>" + reservation + "</b>");
					Utility.app_logs.info("Reservation #  :  " + reservation);
					report.Report_Verify_ReportContent(driver, reportContant, getAmount, testSteps);
					testSteps.add("Room Charges  :  <b>" + getAmount + "</b>");
					Utility.app_logs.info("Room Charges   :  " + getAmount);
					report.Report_Verify_ReportContent(driver, reportContant, paymentType, testSteps);
					testSteps.add("Payment Method :  <b>" + paymentType + "</b>");
					Utility.app_logs.info("Payment Method   :  " + paymentType);

				/*	testSteps.add("Verify that if user generate the Guest statement at the time of check-out then same details should display on that"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848449' target='_blank'>"
							+ "Click here to open TestRail: 848449</a><br>");

					Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that if user generate the Guest statement at the time of check-out then same details should display on that");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		*/
				Utility.deleteFile(Utility.fileName);
				testSteps.add("Deleted File  :  <b>" + Utility.fileName + "</b>");
				Utility.app_logs.info("Deleted File   :  " + Utility.fileName);
				
			
				
					}
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);
					for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Early Checkout");
					}
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Start Verifying Guest Statement Report", "Reservation", "Reservation", testCase,
							test_description, test_catagory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Start Verifying Guest Statement Report", "Reservation", "Reservation", testCase, test_description,
							 test_catagory, testSteps);
							
				}
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyEarlyCheckOutforPrimaryGu", gsexcel);
		
	}
	

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);

	}


}
