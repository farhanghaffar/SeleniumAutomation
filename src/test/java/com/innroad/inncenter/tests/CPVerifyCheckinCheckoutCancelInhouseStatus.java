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
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CPVerifyCheckinCheckoutCancelInhouseStatus extends TestCore{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	CPReservationPage reservation = new CPReservationPage();
	Create_Reservation reservationPage = new Create_Reservation();
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	String seasonStartDate=null,seasonEndDate=null,confirmationNo=null,roomClassNameNew=null,roomCharge = null,guestName=null;
	List<String> roomCharges = new ArrayList<String>();
	 List<String> roomNos= new ArrayList<String>();
	 
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
	 
	 @BeforeTest(alwaysRun = true)
		public void checkRunMode() {
			String testName = this.getClass().getSimpleName().trim();
			app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
			if (!Utility.isExecutable(testName, gsexcel))
				throw new SkipException("Skipping the test - " + testName);
		}
	 
	 @Test(dataProvider = "getData", groups = "GuestServices")
		public void cPVerifyCheckinCheckoutCancelInhouseStatus(String roomClassName, String maxAdults, String maxPersopns,
				String roomQuantity, String checkInPolicyName, String typeOfFees, String percentage, String chargesTypes,
				String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
				String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
				String nameOnCard, String marketSegment, String referral) throws ParseException {
		 String testCaseID="848617|848618|848506|848508|848619";
			if(Utility.getResultForCase(driver, testCaseID)) {
		 test_name = "CPVerifyCheckinCheckoutCancelInhouseStatus";
			test_description = "CPVerifyCheckinCheckoutCancelInhouseStatus<br>"	
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848617' target='_blank'>"
					+ "Click here to open TestRail: 848617</a><br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848618' target='_blank'>"
					+ "Click here to open TestRail: 848618</a><br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848506' target='_blank'>"
					+ "Click here to open TestRail: 848506</a><br>"
					+"<a href='https://innroad.testrail.io/index.php?/cases/view/848508' target='_blank'>"
					+ "Click here to open TestRail: 848508</a><br>"	
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848619' target='_blank'>"
					+ "Click here to open TestRail: 848619</a><br>";				
			test_catagory = "Verification";
			testName = test_name;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			Utility.initializeTestCase("848617|848618|848506|848508|848619", Utility.testId, Utility.statusCode, Utility.comments, "");
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> checkOutDates = new ArrayList<>();
			ArrayList<String> sessionEndDate = new ArrayList<>();
			ArrayList<String> datesRangeList = new ArrayList<String>();
			 NightlyRate nightlyRate= new NightlyRate();
			 
			 
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
				
				// Create Season
							try {
								testSteps.add("<b>======Start Updating Rate Plan ======</b>");
								nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, rateplan.get(0),datesRangeList,
										seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
										"", "", "", true);
								Wait.waitUntilPageLoadNotCompleted(driver, 40);
							} catch (Exception e) {
								Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
										test_description, test_catagory, testSteps);
							} catch (Error e) {
								Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
										test_catagory, testSteps);
							}
							
							try {
								 ArrayList<String> roomNumbers= new ArrayList<String>();
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
								roomCharge = roomCharges.get(1).replace("$", "");
								app_logs.info(roomCharge);						
							}catch (Exception e) {
								Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
										test_description, test_catagory, testSteps);
							} catch (Error e) {
								Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
										test_catagory, testSteps);
							}
							
							try {
								ArrayList<String> sals= Utility.splitInputData(salutation);
								guestName = "" + sals.get(1) + " " + Utility.secondGuestFirstName + " " + Utility.secondGuestLastName + "";
								testSteps.add("<b>==========Start Check In Primary Guest==========</b>");
								app_logs.info("<b>==========Start Check In Primary Guest==========</b>");
								Wait.wait15Second();
								reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(0));
								reservation.verifySpinerLoading(driver);
								reservation.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));		
								reservation.clickOnConfirmCheckInButton(driver, testSteps);
								reservation.verifySpinerLoading(driver);
								testSteps.add("<b>======Verify Reservation Status As IN-HOUSE ======</b>");
								app_logs.info("<b>======Verify Reservation Status As IN-HOUSE ======</b>");
								reservation.verifyReservationStatusStatus(driver, testSteps, "IN-HOUSE");
							}catch (Exception e) {
								Utility.catchException(driver, e, "Checkin Primary Guest", "Reservation", "Reservation", testName,
										test_description, test_catagory, testSteps);
							} catch (Error e) {
								Utility.catchError(driver, e, "Checkin Primary Guest", "Reservation", "Reservation", testName, test_description,
										test_catagory, testSteps);
							}
							try {
								
								testSteps.add("<b>==========Start Check In Second Guest==========</b>");
								app_logs.info("<b>==========Start Check In Second Guest==========</b>");
								Wait.wait15Second();
								reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));
								reservation.verifySpinerLoading(driver);

								app_logs.info("<b>==========Verify Check In  Information of Secondary Guest for IN-HOUSE Status==========</b>");
								verifyCheckinWindow("Check In",salutation,Utility.secondGuestFirstName,Utility.secondGuestLastName,"RESERVED",confirmationNo,
										adults,children,checkInDates.get(1), checkOutDates.get(1),roomClassNames.get(1), rateplan.get(1),roomCharge);
							
															testSteps.add("<b>==========Verify Check In  Information of Secondary Guest for IN-HOUSE Status==========</b>");	reservation.clickCloseRollBackMsg(driver, testSteps);	
								testSteps.add("<b>==========Start Cancel Secondary Guest==========</b>");
								app_logs.info("<b>==========Start Cancel Secondary Guest==========</b>");
								Wait.wait5Second();
								reservation.clickStayInfoThreeDots(driver, testSteps, "Cancel", roomNos.get(1));
								reservation.verifySpinerLoading(driver);
								testSteps.add("<b>==========Verify Cancel Information of Secondary Guest for IN-HOUSE Status==========</b>");
								app_logs.info("<b>==========Verify Cancel Information of Secondary Guest for IN-HOUSE Status==========</b>");
								reservation.verifyReservationPopWindow(driver, "Cancel Room", guestName, "RESERVED", confirmationNo,
										testSteps, "");						
								reservation.clickCloseRollBackMsg(driver, testSteps);	
								testSteps.add("<b>==========Start Check In Second Guest==========</b>");
								app_logs.info("<b>==========Start Check In Second Guest==========</b>");
								Wait.wait15Second();
								reservation.clickStayInfoCheckIn(driver, testSteps, roomNos.get(1));
								reservation.verifySpinerLoading(driver);
								reservation.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));		
								reservation.clickOnConfirmCheckInButton(driver, testSteps);
								reservation.verifySpinerLoading(driver);
								testSteps.add("<b>==========Start Check Out Secondary Guest==========</b>");
								app_logs.info("<b>==========Start Check Out Secondary Guest==========</b>");
								Wait.wait5Second();
								reservation.clickStayInfoCheckOutRoom(driver, testSteps, roomNos.get(1));
								reservation.verifySettlementMsg(driver, testSteps, roomNos.get(1),
										roomClassNames.get(1));
								reservation.clickSettlementYesButton(driver, testSteps);
								reservation.verifySpinerLoading(driver);
								testSteps.add("<b>==========Verify Check Out Information of Secondary Guest for IN-HOUSE Status==========</b>");
								app_logs.info("<b>==========Verify Check Out Information of Secondary Guest for IN-HOUSE Status==========</b>");
								ArrayList<String> sals= Utility.splitInputData(salutation);
								 guestName = "" + sals.get(1) + " " + Utility.secondGuestFirstName + " " + Utility.secondGuestLastName + "";
								reservation.verifyReservationPopWindow(driver, "Check Out", guestName, "IN-HOUSE", confirmationNo,
										testSteps, "Check Out");	
								RetryFailedTestCases.count = Utility.reset_count;
								Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
							}catch (Exception e) {
								Utility.catchException(driver, e, "Verify Checkin Checkout Cancel for Inhouse Status", "Reservation", "Reservation", testName,
										test_description, test_catagory, testSteps);
							} catch (Error e) {
								Utility.catchError(driver, e, "Verify Checkin Checkout Cancel for Inhouse Status", "Reservation", "Reservation", testName, test_description,
										test_catagory, testSteps);
							}
							
							for(int i=0;i<Utility.testId.size();i++) {
									Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify second guest pay primary charges");
								}
								
								Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
										Utility.comments, TestCore.TestRail_AssignToID);
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
