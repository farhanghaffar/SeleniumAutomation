package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2ReservationDetailsReport extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String testName=null,propertyName=null;
	ReservationHomePage homePage = new ReservationHomePage();
	ReportsV2 report = new ReportsV2();
	Navigation navigation = new Navigation();
	ReservationV2 reservationPage=  new ReservationV2();
	CPReservationPage reservation = new CPReservationPage();
	Users user= new Users();
	FolioNew folio= new FolioNew();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	String confirmationNo=null,yearDate=null, status=null,  userName=null
			,nights=null,source=null;
	ArrayList<String> userList= new ArrayList<String>();
	ArrayList<String> totalCharge= new ArrayList<String>();
	ArrayList<String> totalPay= new ArrayList<String>();
	ArrayList<String> balance= new ArrayList<String>();
	ArrayList<String> adr= new ArrayList<String>();
	ArrayList<String> roomNo= new ArrayList<String>();
	
	private String createReservation(String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String address1, String address2,String city,String country, String state, String postalCode,
			String paymentType, String cardNumber,String nameOnCard,String noteType, String noteSubject, String noteDescription,
			String taskCategory, String taskType, String taskDetails, String taskRemarks,  
			String taskStatus,String marketSegment, String referral,String primaryAbb, String taskDue) throws Exception {
		String confirmNo=null;
		yearDate=Utility.getFutureMonthAndYearForMasterCard();
		HashMap<String, String> reservationData= new HashMap<String, String>();
		navigation.Reservation_Backward_3(driver);
		app_logs.info("Back to reservation page");
		reservationData=reservationPage.createReservation_RV2(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
				children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName,false, 
				"", "", "", address1, address2, "", city, country, state, postalCode,false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
				0, "",false, noteType,noteSubject, noteDescription,taskCategory, taskType,taskDetails, taskRemarks, taskDue, "",taskStatus, "", "","", "",false, "");
		
		
		reservation.click_Folio(driver, test_steps);
		double parimaryRoomAmt=0.00,SecondaryRoomAmt=0.00,adrparimaryRoomAmt=0.00,adrSecondaryRoomAmt=0.00;
		HashMap<String, String> getfolioCharges=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "");
		for(Map.Entry<String, String> entry:getfolioCharges.entrySet()) {
			if(entry.getKey().contains(primaryAbb)) {
				parimaryRoomAmt=parimaryRoomAmt+Double.parseDouble(entry.getValue());
			}else {
				SecondaryRoomAmt=SecondaryRoomAmt+Double.parseDouble(entry.getValue());
			}
		}
		HashMap<String, String> getfolioCharges1=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Room Charge");
		for(Map.Entry<String, String> entry:getfolioCharges1.entrySet()) {
			if(entry.getKey().contains(primaryAbb)) {
				adrparimaryRoomAmt=adrparimaryRoomAmt+Double.parseDouble(entry.getValue());
			}else {
				adrSecondaryRoomAmt=adrSecondaryRoomAmt+Double.parseDouble(entry.getValue());
			}
		}
		totalCharge.add(Utility.convertDecimalFormat(String.valueOf(parimaryRoomAmt)));
		totalCharge.add(Utility.convertDecimalFormat(String.valueOf(SecondaryRoomAmt)));
		totalPay.add(Utility.convertDecimalFormat(String.valueOf(parimaryRoomAmt)));
		totalPay.add("0.00");
		balance.add("0.00");
		balance.add(Utility.convertDecimalFormat(String.valueOf(SecondaryRoomAmt)));
		adr.add(Utility.convertDecimalFormat(String.valueOf(adrparimaryRoomAmt)));
		adr.add(Utility.convertDecimalFormat(String.valueOf(adrSecondaryRoomAmt)));
		
		reservation.click_DeatilsTab(driver, test_steps);
		Wait.wait10Second();
		reservationPage.takePayment(driver, test_steps, true, "", "", false);	
		
		app_logs.info("MarketingInfo details");
		MarketingInfo marketungInfo = reservationPage.getMarketingInfoDetail(driver);
		source=marketungInfo.getMI_MARKET_SOURCE();
		app_logs.info("source: "+source);
		status=reservationData.get("ReservationStatus");
		confirmNo=reservationData.get("ReservationNumber");
		roomNo.add(reservationData.get("RoomNumber1"));
		roomNo.add(reservationData.get("RoomNumber2"));
		nights=Utility.getNights(driver,checkInDate, checkOutDate);		
		
		reservation.closeReservationTab(driver);
		return confirmNo;
	}
	
	
	
	private void verifyReport(String dateOption,String propertyName,String confirmNo,ArrayList<String> guestName,String status, ArrayList<String> paidUnpaid,
			String checkInDate, String checkOutDate, String adults,
			String children, String rateplan,String roomClass, ArrayList<String> roomNo,String nights, 
			String address1, String address2, String city, String territory,String zipCode, String country,
			String userName,String marketSegment,String referral , String source, String paymentType, 
			String cardNumber,String yearDate, ArrayList<String> totalCharge,ArrayList<String> totalPay, ArrayList<String> balance, 
			 String notes, String task) throws InterruptedException, ParseException {
		navigation.ReportsV2(driver);
		test_steps.add("Successfully navigated to reports page.");
		app_logs.info("Successfully navigated to reports page.");
		report.navigateToReservationDetailsReport(driver, test_steps);
		test_steps.add("Navigated to Reservation Details reports page");					
		app_logs.info("Navigated to Reservation Details reports page");	
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		report.selectDateRange(driver, dateOption, test_steps);
		report.clickOnRunReport(driver);
		test_steps.add("Run Report");					
		app_logs.info("Run Report");		
		report.verifyLoadingReport(driver);		
		test_steps.add("========= Verify Report Title  =========");
		app_logs.info("========= Verify Report Title =========");
		report.validateStandardHeaderTitle(driver, "Reservation Details Report", propertyName, test_steps);				
		String date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");			
		String dateRange= ""+dateOption+" | "+date+" to "+date+"";
		HashMap<String, String> mainHeaderData=report.getMainHeaderReservationDetailsReport(driver, test_steps);
		HashMap<String, String> middleHeaderData=report.getMiddleHeaderReservationDetailsReport(driver, test_steps);	
		test_steps.add("========= Verify Main and Middle Header =========");
		app_logs.info("========= Verify Main and Middle Header =========");
		report.verifyMainHeaderReservationDetailsData(driver, test_steps, mainHeaderData, dateRange, "Arrivals & Departures", "Arrival Date and Time", "None","All");
		report.verifyMiddleHeaderReservationDetailsData(driver, test_steps, middleHeaderData, "Reservation Details Report", dateRange, "Arrivals & Departures", "Arrival Date and Time", date,"None", "All");
		test_steps.add("========= Verify Detailed View =========");
		app_logs.info("========= Verify Detailed View =========");
		String startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");		
		String endDate=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MMM dd, yyyy");
		for(int i=0;i<guestName.size();i++) {
		report.verifyReservationDetailsReport(driver, "Arrival Date and Time",propertyName, confirmNo, guestName.get(i), 
				status, paidUnpaid.get(i), Utility.splitInputData(roomClass).get(i), roomNo.get(i), startDate, endDate, 
				"--", nights, adults, children, address1, address2, city, territory, 
				zipCode, country, rateplan, userName, marketSegment, referral, source, 
				paymentType, cardNumber, yearDate, totalCharge.get(i), totalPay.get(i), balance.get(i), 
				"--", notes, task, test_steps);
		}
	}
	
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}	
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportsV2ReservationDetailsReport(String testCaseID,String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral,
			String dateOption,String address1, String address2, String city, String state,String postalCode, String country,
			String noteType, String noteSubject, String noteDescription,
			String taskCategory, String taskType, String taskDetails, String taskRemarks, 
			String taskStatus) throws ParseException {
		
		//boolean isExecutable=Utility.getResultForCase(driver, testCaseID);
		//if(isExecutable) {
			test_name = "ReportsV2 - VerifyReservationHistory Split Resrvation History";
			test_description = "ReportsV2 - VerifyReservationHistory"+testCaseID+"<br>" ;
			test_category = "ReportsV2 - Reservation History";
			testName = test_name + "--" + testCaseID;
			
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
					Utility.comments, "");	
			ArrayList<String> primaryRoomClassAbb= new ArrayList<String>();
			// Login
			try {	
				
				if (!(Utility.validateInput(checkInDate))) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
						checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
						checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);					
					checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
					checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				}
				
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				
				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}					
				driver = getDriver();
				loginReportsV2(driver);
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");	
				
				
					} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_category, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_category, test_steps);
			}	
			
			try {
				test_steps.add("<b>===== Getting Property =====</b>");
				app_logs.info("===== Getting Property=====");
				propertyName = homePage.getReportsProperty(driver, test_steps);
				test_steps.add("<b>Property Name : </b>" + propertyName);
				app_logs.info("Property Name : " + propertyName);
				
				test_steps.add("<b>===== Getting UserName =====</b>");
				app_logs.info("===== Getting UserName=====");
				navigation.admin(driver);
				navigation.Users(driver);
				userList=user.getAllUsersList(driver, test_steps);
				userName=userList.get(0);
				
				test_steps.add("<b>===== Getting Abb =====</b>");
				app_logs.info("===== Getting Abb=====");
				navigation.clickSetup(driver);
				navigation.roomClass(driver);
				primaryRoomClassAbb=newRoomClass.getAbbrivation(driver, Utility.DELIM, Utility.splitInputData(roomClass).get(0), test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Get Property", "Property", "Property", testName, test_description,
						test_category, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Get Property", "Property", "Property", testName, test_description,
						test_category, test_steps);
			}	
			
			try {
				/*guestFirstName=guestFirstName+Utility.fourDigitgenerateRandomString();
				guestLastName=guestLastName+Utility.fourDigitgenerateRandomString();
				String finalName= guestFirstName+" "+guestLastName;*/
				
				ArrayList<String> firstName=Utility.splitInputData(guestFirstName);
				ArrayList<String> lastName=Utility.splitInputData(guestLastName);
				guestFirstName=firstName.get(0)+Utility.fourDigitgenerateRandomString()+"|"+firstName.get(1)+Utility.fourDigitgenerateRandomString();
				guestLastName=lastName.get(0)+Utility.fourDigitgenerateRandomString()+"|"+lastName.get(1)+Utility.fourDigitgenerateRandomString();
				ArrayList<String> finaleName= new ArrayList<String>();
				finaleName.add(Utility.splitInputData(guestLastName).get(0)+" "+Utility.splitInputData(guestFirstName).get(0));
				finaleName.add(Utility.splitInputData(guestLastName).get(1)+" "+Utility.splitInputData(guestFirstName).get(1));
				
				confirmationNo=createReservation(checkInDate,  checkOutDate, adults,
						 children, rateplan, roomClass, salutation, guestFirstName,
						 guestLastName,address1, address2,city,country,state,postalCode,paymentType,  cardNumber, nameOnCard, noteType,
						 noteSubject,noteDescription,taskCategory,taskType,taskDetails,taskRemarks,taskStatus,marketSegment,  referral,
						 primaryRoomClassAbb.get(0),checkInDates.get(0));	
				ArrayList<String> paidUnpaid=new ArrayList<String>();
				/*ArrayList<String> notes=new ArrayList<String>();
				ArrayList<String> tasks=new ArrayList<String>();*/
				String notes=null,tasks=null;
				
				
				String fourDight = Utility.getCardNumberHidden(cardNumber).replace("XXXX", "").trim();
				notes=""+noteSubject+": "+noteDescription+"";;
				tasks=""+taskType+": "+taskDetails+"";
				yearDate=Utility.parseDate(yearDate, "MM/yy", "MM/yyyy");
				paidUnpaid.add(null);
				paidUnpaid.add("Unpaid");
				//for(int i=0;i<guestFirstName.split("\\|").length;i++) {
				verifyReport(dateOption,propertyName,confirmationNo,finaleName,status,paidUnpaid,Utility.splitInputData(checkInDate).get(0),Utility.splitInputData(checkOutDate).get(0),
						Utility.splitInputData(adults).get(0),Utility.splitInputData(children).get(0),Utility.splitInputData(rateplan).get(0),roomClass,
						roomNo, nights,address1,address2,city,state,postalCode,country, userName, marketSegment,  referral,
							source,paymentType, fourDight,yearDate,totalCharge, totalPay,  balance,notes,tasks);
				
				
				//	}
				for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Reservation Detail Report");
				}					
				Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
						Utility.comments, TestCore.TestRail_AssignToID);
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Navigate Report", "Report", "Report", testName, test_description,
						test_category, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Navigate Report", "Report", "Report", testName, test_description,
						test_category, test_steps);
			}	
		}

	//}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2ReservationDetailsRepo", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {		
		driver.quit();
		
	}
}
