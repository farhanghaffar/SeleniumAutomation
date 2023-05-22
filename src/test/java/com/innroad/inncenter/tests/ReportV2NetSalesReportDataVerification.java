package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.ReservationV2Search;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportV2NetSalesReportDataVerification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	String  confirmationNo=null, testName=null,startDate=null,endDate=null, date=null,rooms=null,propertyName=null,
			totalRoomOutOFOrder=null, netRes=null,netRoomNights=null, cancelReservation=null, 
			totalReservation=null,  detailHeader=null, roomClass1NetNights=null,roomClass2NetNights=null;
	ArrayList<String> datesRangeList = new ArrayList<String>();
	Navigation nav = new Navigation();
	ReportsV2 report = new ReportsV2();
	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage=  new ReservationV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop= new Properties();
	RoomStatus roomstatus = new RoomStatus();
	RoomMaintenance roommaintenance = new RoomMaintenance();
	TaxesAndFee taxFee = new TaxesAndFee();
	Users user= new Users();
	FolioNew folio= new FolioNew();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	GetNetSalesDataUserBooked netSalesData= new GetNetSalesDataUserBooked();
	ArrayList<String> userlist= new ArrayList<String>();
	List<String> summaryViewUserBooked= new ArrayList<String>();
	List<String> summaryViewNetRes= new ArrayList<String>();
	List<String> summaryViewRoomNights= new ArrayList<String>();
	List<String> summaryViewBookingNights= new ArrayList<String>();
	List<String> summaryViewRoomRevenue= new ArrayList<String>();
	List<String> summaryViewOtherRevenue= new ArrayList<String>();
	List<String> summaryViewTotalRevenue= new ArrayList<String>();
	List<String> summaryViewCancelPer= new ArrayList<String>();
	List<String> summaryViewAvgStay= new ArrayList<String>();
	List<String> summaryViewAvgDailyRate= new ArrayList<String>();
	List<String> summaryViewRavPar= new ArrayList<String>();
	List<String> detailedViewUserBooked= new ArrayList<String>();
	List<String> detailedViewNetRes= new ArrayList<String>();
	List<String> detailedViewRoomNights= new ArrayList<String>();
	List<String> detailedViewBookingNights= new ArrayList<String>();
	List<String> detailedViewRoomRevenue= new ArrayList<String>();
	List<String> detailedViewOtherRevenue= new ArrayList<String>();
	List<String> detailedViewTotalRevenue= new ArrayList<String>();
	List<String> detailedViewCancelPer= new ArrayList<String>();
	List<String> detailedViewAvgStay= new ArrayList<String>();
	List<String> detailedViewAvgDailyRate= new ArrayList<String>();
	List<String> detailedViewRavPar= new ArrayList<String>();
	List<String> roomsofRoomClass= new ArrayList<String>();
	List<String> abbs= new ArrayList<String>();
	List<String> roomNumbers= new ArrayList<String>();
	HashMap<String, String> roomClassAbb= new HashMap<String, String>();
	HashMap<String, String> roomClassOneData= new HashMap<String, String>();
	HashMap<String, String> roomClassTwoData= new HashMap<String, String>();
	HashMap<String, String> detailViewroomClassOneData= new HashMap<String, String>();
	HashMap<String, String> detailViewroomClassTwoData= new HashMap<String, String>();
	double roomCharge=0.00, otherCharges=0.00, otherRevenue1=0.00,otherRevenue2=0.00, roomRevenueR1=0.00, roomRevenueR2=0.00;
	
	
	private HashMap<String, String>refinePrice(String revenuePrice, String otherRevenuePrice) {
		HashMap<String, String> data= new HashMap<String, String>();
		String roomRev, otherRev;
		if(revenuePrice.toString().contains(",")) {
			 roomRev=revenuePrice.toString().replace("$", "").replace(",", "");
		}else {
			 roomRev=revenuePrice.toString().replace("$", "");
		}
		if(otherRevenuePrice.toString().contains(",")) {
			otherRev=otherRevenuePrice.toString().replace("$", "").replace(",", "");
		}else {
			otherRev=otherRevenuePrice.toString().replace("$", "");
		}
		data.put("roomRev", roomRev);
		data.put("otherRev", otherRev);
		return data;
		

	}
	private void runRunWithDifferentParameter(String dateOption, String groupNetSalesBY, int i) throws InterruptedException, ParseException {
		Wait.wait5Second();
		report.editButtonClick(driver, test_steps);
		Wait.wait5Second();
		report.selectDateRange(driver, dateOption, test_steps);
		report.selectGroupNetSalesByOption(driver, test_steps, Utility.splitInputData(groupNetSalesBY).get(i));
		report.clickOnRunReport(driver);
		test_steps.add("Run Report");					
		app_logs.info("Run Report");		
		report.verifyLoadingReport(driver);	
		Wait.wait10Second();
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}	
	
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportV2NetSalesReportDataVerification(String testCaseID, String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral,
			String category, String amount,String dateOption, String groupRowBy, String feeName, String feeType,
			String categoryFees, String infeeValue, String groupNetSalesBY)throws InterruptedException, IOException, ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "ReportsV2 - VerifyNetSalesReport  -  "+testCaseID;
		test_description = "ReportsV2 - VerifyNetSalesReport<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/"+testCaseID+"' target='_blank'>"
				+ "Click here to open TestRail: "+testCaseID+"</a><br>";
		test_category = "ReportsV2 - Net Sales report";
		testName = test_name;		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");	
		
		HashMap<String, List<String>> userBookedData= new HashMap<String, List<String>>();
		HashMap<String, List<String>> ratePlanData= new HashMap<String, List<String>>();
		HashMap<String, HashMap<String, String>> roomClassData= new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> RoomNumberData= new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> roomClassDataAll= new HashMap<String, HashMap<String, String>>();
		HashMap<String, String>  folioUserBookedRatePlanData= new HashMap<String, String>();
		HashMap<String, List<String>>  folioRoomClassRoomNumberData= new HashMap<String, List<String>>();
		HashMap<String, List<String>>  folioRoomClassRoomNumberAllData= new HashMap<String, List<String>>();
		boolean notExist =false;
		String totalNightsForRoomNumber=null;
		int nights=0;
		// Login
		try {	
				
				if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			} 
			startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(startDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}					
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
			app_logs.info(datesRangeList);
			
			String[] days= checkInDates.get(0).split("\\/");
			if(Character.toString(days[0].charAt(0)).equals("0")) {
				date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy");
			}else {
				date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
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
			propertyName = prop.getProperty(driver, test_steps);		
			test_steps.add("========= Get User Name =========");
			app_logs.info("========= Get User Name =========");
			nav.admin(driver);
			test_steps.add("Navigated to Admin.");
			app_logs.info("Navigated to Admin.");
			nav.Users(driver);
			test_steps.add("Click User.");
			app_logs.info("Click User.");
			user.search(driver);
			test_steps.add("Click Search.");
			app_logs.info("Click Search.");
			userlist=user.getAllUsersList(driver, test_steps);
			app_logs.info(userlist);
			nav.clickSetup(driver);
			test_steps.add("=========Get Rooms=========");
			app_logs.info("=========Get Rooms=========");
			nav.properties(driver);
			rooms=prop.getRoomNumber(driver, propertyName);		
			app_logs.info(rooms);
			test_steps.add("=========Get Rooms on Room Class Basis=========");
			app_logs.info("==========Get Rooms on Room Class Basis=========");
			nav.roomClass(driver, test_steps);
			roomsofRoomClass=newRoomClass.getRoom(driver, Utility.DELIM, roomClass, test_steps);
			abbs=newRoomClass.getAbbrivation(driver, Utility.DELIM, roomClass, test_steps);
			roomClassAbb.put( abbs.get(0),Utility.splitInputData(roomClass).get(0) );
			roomClassAbb.put( abbs.get(1),Utility.splitInputData(roomClass).get(1));
			app_logs.info(roomClassAbb);
			nav.navigateGuestservicesAfterrateGrid(driver);
			test_steps.add("=========Check Out of Order=========");
			app_logs.info("=========Check Out of Order=========");			
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			nav.RoomMaintenance(driver);
			test_steps.add("Navigated to RoomMaintenance");			
			roommaintenance.searchByFromAndToDate(driver, test_steps, "", startDate, "dd/MM/yyyy", "Out of Order");
			Wait.wait15Second();
			totalRoomOutOFOrder= roommaintenance.getroomNumberCountFromMaintainanceofCurrentDate(driver, startDate, "", test_steps);
			app_logs.info(totalRoomOutOFOrder);
			nav.inventoryToSetup(driver,test_steps);
			nav.clickTaxesAndFees(driver);
			test_steps.add("Click Tax and Fee");
			app_logs.info("Click Tax and Fee");
			boolean isExist = taxFee.isTaxOrFeeItemExist(driver);
			if (isExist) {
				taxFee.makingInactiveORActiveOnAllExistingItem(driver, "inactive", test_steps);				
			}
			feeName= feeName+Utility.threeDigitgenerateRandomString();
			taxFee.createFee(driver, test_steps, feeName, feeName, feeType,feeName, categoryFees, infeeValue, false, "", "", "");
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Data from Report", "Get Data", "Get Data", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Data from Report", "Get Data", "Get Data", testName, test_description,
					test_category, test_steps);
		}	
		try {			
			test_steps.add("========= Navigating to Net Sales Report =========");
			app_logs.info("========= Navigating to Net Sales Report =========");
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			app_logs.info("Successfully navigated to reports page.");
			report.navigateToNetSalesReport(driver, test_steps);
			test_steps.add("Navigated to Net Sales reports page");					
			app_logs.info("Navigated to Net Sales reports page");	
			Wait.wait10Second();
			report.selectDateRange(driver, dateOption, test_steps);
				report.selectGroupNetSalesByOption(driver, test_steps, Utility.splitInputData(groupNetSalesBY).get(0));
				report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);
			notExist =report.runReportNoDataAvailableDisplayed(driver);
			if (notExist){
				test_steps.add("========= Report Data not Available, Create New Reservation=========");
				app_logs.info("========= Report Data not Available, Create New Reservation =========");
				driver.close();
				Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
				report.verifyLoadingReport(driver);
				nav.Reservation_Backward_3(driver);
				app_logs.info("Back to reservation page");
				String yearDate=Utility.getFutureMonthAndYearForMasterCard();
				HashMap<String, String> reservationData= new HashMap<String, String>();

					reservationData=reservationPage.createReservation_RV2(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
							children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, true,
						"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
						0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");
				confirmationNo=reservationData.get("ReservationNumber");
				reservationPage.click_Folio(driver, test_steps);
				Wait.wait5Second();    
				folio.addFolioLineItemMRB(driver, test_steps, category, amount); 
				Wait.wait5Second();  

				folioUserBookedRatePlanData=netSalesData.getFolioChargesForUserBookedAndRatePlan(driver,date, checkInDates.get(0), checkOutDates.get(0),test_steps);							
				folioRoomClassRoomNumberData=netSalesData.getFolioChargesForRoomClassAndRoomNumber(driver,abbs, roomClassAbb, date, checkInDates.get(0), checkOutDates.get(0), confirmationNo,test_steps);
				app_logs.info("User Booked-- "+folioUserBookedRatePlanData);	
				app_logs.info("Room Number"+folioRoomClassRoomNumberData);
				}else {		
				test_steps.add("===================Get User Booked Data==================");					
				app_logs.info("===================Get User Booked Data==================");	
				userBookedData= netSalesData.netSalesReportDataUserBookedRatePlan(driver);
				app_logs.info(userBookedData);
				test_steps.add("===================Get Rate Plan Data==================");					
				app_logs.info("===================Get Rate Plan Data==================");	
				runRunWithDifferentParameter(dateOption, groupNetSalesBY, 1);
				ratePlanData= netSalesData.netSalesReportDataUserBookedRatePlan(driver);
				app_logs.info(ratePlanData);
				test_steps.add("===================Get Room Class Data==================");					
				app_logs.info("===================Get Room Class Data==================");	
				runRunWithDifferentParameter(dateOption, groupNetSalesBY, 2);
				roomClassData=netSalesData.netSalesReportDataRoomClassRoomNumber(driver,roomClass);
				app_logs.info(roomClassData);
				test_steps.add("===================Get Room Number Data==================");					
				app_logs.info("===================Get Room Number Data==================");	
				runRunWithDifferentParameter(dateOption, groupNetSalesBY, 3);
				RoomNumberData=netSalesData.netSalesReportDataRoomClassRoomNumber(driver,roomClass);
				app_logs.info(RoomNumberData);
				test_steps.add("===================Get Room Class Data With All Option==================");					
				app_logs.info("====================Get Room Class Data With All Option==================");	
				runRunWithDifferentParameter("All", groupNetSalesBY, 2);
				roomClassDataAll=netSalesData.netSalesReportDataRoomClassRoomNumber(driver,roomClass);			
				app_logs.info(roomClassDataAll);
				driver.close();
				Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
				report.verifyLoadingReport(driver);
				nav.Reservation_Backward_3(driver);
				app_logs.info("Back to reservation page");							
				String yearDate=Utility.getFutureMonthAndYearForMasterCard();
				HashMap<String, String> reservationData= new HashMap<String, String>();
					reservationData=reservationPage.createReservation_RV2(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
						children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, true,
						"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
						0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");
				
				confirmationNo=reservationData.get("ReservationNumber");				
				reservationPage.click_Folio(driver, test_steps);
				Wait.wait5Second();
				folio.addFolioLineItemMRB(driver, test_steps, category, amount); 
				Wait.wait5Second();
				folioUserBookedRatePlanData=netSalesData.getFolioChargesForUserBookedAndRatePlan(driver,date, checkInDates.get(0), checkOutDates.get(0),test_steps);
				app_logs.info(folioUserBookedRatePlanData);	
				ReservationSearch reservationSearch= new ReservationSearch();
				reservationSearch.clickCloseSearch(driver);
				ReservationV2Search rsvSearch= new ReservationV2Search();
				rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
				Wait.wait5Second();
				reservationPage.click_Folio(driver, test_steps);
				Wait.wait5Second();
				folioRoomClassRoomNumberAllData=netSalesData.getFolioChargesofAllDates(driver,abbs, roomClassAbb, checkInDates.get(0), checkOutDates.get(0), confirmationNo,test_steps);
				 app_logs.info(folioRoomClassRoomNumberAllData);	
				folioRoomClassRoomNumberData=netSalesData.getFolioChargesForRoomClassAndRoomNumber(driver,abbs, roomClassAbb, date, checkInDates.get(0), checkOutDates.get(0), confirmationNo,test_steps);				
				app_logs.info(folioRoomClassRoomNumberData);
				
				
				}
			  
				nav.ReportsV2(driver);
				test_steps.add("Successfully navigated to reports page.");
				app_logs.info("Successfully navigated to reports page.");
				report.navigateToNetSalesReport(driver, test_steps);
				test_steps.add("Navigated to Net Sales reports page");					
				app_logs.info("Navigated to Net Sales reports page");	
				report.clickOnRunReport(driver);
				test_steps.add("Run Report");					
				app_logs.info("Run Report");		
				report.verifyLoadingReport(driver);			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Data from Report", "Get Data", "Get Data", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Data from Report", "Get Data", "Get Data", testName, test_description,
					test_category, test_steps);
		}		
		try {
			test_steps.add("========= Verify Net Sales Report =========");
			app_logs.info("========= Verify Net Sales Report =========");
			if (notExist){	
				String roomRevenue=null,otherRevenue=null,totalRevenue=null,cancelPercentage=null,avgStay=null,avgDailyRate=null,ravPar=null;
				String date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");			
				String dateRange= ""+dateOption+" | "+date+" to "+date+"";
			for(int i=0;i<Utility.splitInputData(groupNetSalesBY).size();i++) {	
				if((Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked")&& i==0)||(Utility.splitInputData(groupNetSalesBY).get(1).equalsIgnoreCase("Rate Plan")&& i==1)) {		
				Wait.wait60Second();
				runRunWithDifferentParameter(dateOption, groupNetSalesBY, i);
				HashMap<String, String> mainHeaderData=report.getMainHeaderNetSalesReport(driver, test_steps);
				HashMap<String, String> middleHeaderData=report.getMiddleHeaderNetSalesReport(driver, test_steps);				
				totalRoomOutOFOrder=report.calculateOutofOderAsperDays(driver, checkInDate, checkInDate, totalRoomOutOFOrder);
				app_logs.info(totalRoomOutOFOrder);	
				app_logs.info("Date Range is: "+ dateRange);	
				app_logs.info("Main Header Data is: "+ mainHeaderData);
				app_logs.info("Middle Header Data is: "+ middleHeaderData);
				test_steps.add("========= Verify Main and Middle Header =========");
				 app_logs.info("========= Verify Main and Middle Header =========");
				report.verifyMainHeaderData(driver, test_steps, mainHeaderData, dateRange, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "Day", "All");
				report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", date, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "", "All", groupRowBy, "No");
				test_steps.add("========= Verify net Sales Report Summary Wise =========");
				app_logs.info("========= Verify net Sales Report Summary Wise =========");
				
				if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked")&& i==0) {
				report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 1, userlist.get(0), test_steps);	
				}else if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("Rate Plan")&& i==1) {
				report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 1, Utility.splitInputData(rateplan).get(0), test_steps);		
				}
				 totalRevenue=report.calculateTotalRevenue(driver, String.valueOf(folioUserBookedRatePlanData.get("roomCharge")), String.valueOf(folioUserBookedRatePlanData.get("incidentals")));
				 avgStay=report.calculateAVGStay(driver, folioUserBookedRatePlanData.get("NetNights"), folioUserBookedRatePlanData.get("NetReservation"));
				 avgDailyRate=report.calculateAvgDailyRate(driver, String.valueOf(folioUserBookedRatePlanData.get("roomCharge")), folioUserBookedRatePlanData.get("NetNights"));
				 ravPar=report.calcualteRevPAR(driver, String.valueOf(folioUserBookedRatePlanData.get("roomCharge")), rooms, totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
				 netSalesData.verifySummaryViewNetSalesUserBookedRatePlan(driver, folioUserBookedRatePlanData.get("NetReservation"), folioUserBookedRatePlanData.get("NetNights"),  String.valueOf(folioUserBookedRatePlanData.get("roomCharge")), String.valueOf(folioUserBookedRatePlanData.get("incidentals")),
						 totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);				 
				 test_steps.add("========= Verify Net Sales Detailed View =========");
				 app_logs.info("========= Verify Net Sales Detailed View =========");
				 if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked") && i==0) {
				 detailHeader=Utility.splitInputData(groupNetSalesBY).get(0) +" - "+ userlist.get(0);}
				 else if(Utility.splitInputData(groupNetSalesBY).get(1).equalsIgnoreCase("Rate Plan") && i==1) {
				 detailHeader=Utility.splitInputData(groupNetSalesBY).get(1) +" - "+ Utility.splitInputData(rateplan).get(0);
				 }
				 app_logs.info(detailHeader);
				String dateDetail=Utility.parseDate(checkInDates.get(0), "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
				app_logs.info("Date-" +dateDetail);				
				 netSalesData.verifyDetailedNetSalesUserBookedRatePlan(driver,detailHeader, dateDetail, folioUserBookedRatePlanData.get("NetReservation"), folioUserBookedRatePlanData.get("NetNights"),  String.valueOf(folioUserBookedRatePlanData.get("roomCharge")), String.valueOf(folioUserBookedRatePlanData.get("incidentals")),
						 totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);	
				 
			}else if((Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2)||(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")&& i==3)){
				String netNights1=String.valueOf(Integer.parseInt(folioRoomClassRoomNumberData.get("revenuesandnights").get(4))+1);
				String netNights2=String.valueOf(Integer.parseInt(folioRoomClassRoomNumberData.get("revenuesandnights").get(5))+1);
				int totalNights=Integer.parseInt(netNights1)+Integer.parseInt(netNights2);
				HashMap<String, String> mainHeaderData=report.getMainHeaderNetSalesReport(driver, test_steps);
				HashMap<String, String> middleHeaderData=report.getMiddleHeaderNetSalesReport(driver, test_steps);		
				report.verifyMainHeaderData(driver, test_steps, mainHeaderData, dateRange, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "Day", "All");
				report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", date, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "", "All", groupRowBy, "No");

				if(Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2) {
					for(String str:Utility.splitInputData(roomClass)) {
					report.verifyNetSalesReportSummaryViewCategoryWise(driver, "Summary View", "Net Sales Report", 1, str, test_steps);
					}}else if(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")  && i==3) {
						for(String str:folioRoomClassRoomNumberData.get("roomNumbers")) {
							report.verifyNetSalesReportSummaryViewCategoryWise(driver, "Summary View", "Net Sales Report", 1, str, test_steps);
							}
					}
				if(Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2) {
					test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
					app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
					String netRes1=String.valueOf(Integer.parseInt(folioRoomClassRoomNumberData.get("revenuesandnights").get(4)));						
					String bookingNights1=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights1);
					String roomRevenue1=String.valueOf(Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(0)));
					String otherRevenues1=String.valueOf(Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(2)));
					String totalRevenue1=String.valueOf(Double.parseDouble(roomRevenue1)+Double.parseDouble(otherRevenues1));
					String avgStay1=report.calculateAVGStay(driver, netNights1, netRes1);
					String avgDailyRate1=report.calculateAvgDailyRate(driver, roomRevenue1, netNights1);
					String ravPar1=report.calcualteRevPAR(driver, roomRevenue1, roomsofRoomClass.get(0), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
					netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(0), netRes1, netNights1, bookingNights1, roomRevenue1, 
							otherRevenues1, totalRevenue1, avgStay1, avgDailyRate1, ravPar1,test_steps);
	
					test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
					app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
					String netRes2=String.valueOf(Integer.parseInt(folioRoomClassRoomNumberData.get("revenuesandnights").get(5)));
					String bookingNights2=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights2);
					String roomRevenue2=String.valueOf(Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(1)));
					String otherRevenues2=String.valueOf(Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(3)));
					String totalRevenue2=String.valueOf(Double.parseDouble(roomRevenue2)+Double.parseDouble(otherRevenues2));
					String avgStay2=report.calculateAVGStay(driver, netNights2, netRes2);
					String  avgDailyRate2=report.calculateAvgDailyRate(driver, roomRevenue2, netNights2);
					String ravPar2=report.calcualteRevPAR(driver, roomRevenue2, roomsofRoomClass.get(1), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
					netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(1), netRes2, netNights2, bookingNights2, roomRevenue2, 
							otherRevenues2, totalRevenue2, avgStay2, avgDailyRate2, ravPar2,test_steps);
					String roomClassName1="Room Class - "+Utility.splitInputData(roomClass).get(0)+"";
					String roomClassName2="Room Class - "+Utility.splitInputData(roomClass).get(1)+"";
					test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
					app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");										
					String date1= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy, EEEE");
					netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomClassName1, date1, netRes1, netNights1, roomRevenue1, otherRevenues1, totalRevenue1, 
							avgStay1, avgDailyRate1, ravPar1,test_steps);
					
					test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
					app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");	
					
					netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomClassName2, date1, netRes2, netNights2, roomRevenue2, otherRevenues2, totalRevenue2, 
							avgStay2, avgDailyRate2, ravPar2,test_steps);
				}else if(Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Number")&& i==3) {
						test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+folioRoomClassRoomNumberData.get("roomNumbers").get(0)+"=========");
						app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+folioRoomClassRoomNumberData.get("roomNumbers").get(0)+"=========");
						String netRes3=String.valueOf(1);
						String netNights3=String.valueOf(1);
						String bookingNights3=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights3);
						String roomRevenue3=String.valueOf(folioRoomClassRoomNumberData.get("revenuesandnights").get(0));
						String otherRevenues3=String.valueOf(folioRoomClassRoomNumberData.get("revenuesandnights").get(2));
						String totalRevenue3=String.valueOf(Double.parseDouble(roomRevenue3)+Double.parseDouble(otherRevenues3));
						String avgStay3=report.calculateAVGStay(driver, netNights3, netRes3);
						String avgDailyRate3=report.calculateAvgDailyRate(driver, roomRevenue3, netNights3);
						String ravPar3=report.calcualteRevPAR(driver, roomRevenue3, "1", totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");	
						
						netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,folioRoomClassRoomNumberData.get("roomNumbers").get(0), netRes3, netNights3, bookingNights3, roomRevenue3, 
								otherRevenues3, totalRevenue3, avgStay3, avgDailyRate3, ravPar3,test_steps);
						
						test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+folioRoomClassRoomNumberData.get("roomNumbers").get(1)+"=========");
						app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+folioRoomClassRoomNumberData.get("roomNumbers").get(1)+"=========");
						String roomRevenue4=String.valueOf(folioRoomClassRoomNumberData.get("revenuesandnights").get(1));
						String otherRevenues4=String.valueOf(folioRoomClassRoomNumberData.get("revenuesandnights").get(3));
						String totalRevenue4=String.valueOf(Double.parseDouble(roomRevenue4)+Double.parseDouble(otherRevenues4));
						String avgStay4=report.calculateAVGStay(driver, netNights3, netRes3);
						String avgDailyRate4=report.calculateAvgDailyRate(driver, roomRevenue4, netNights3);
						String ravPar4=report.calcualteRevPAR(driver, roomRevenue4, "1", totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
						
						netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,folioRoomClassRoomNumberData.get("roomNumbers").get(1), netRes3, netNights3, bookingNights3, roomRevenue4, 
								otherRevenues4, totalRevenue4, avgStay4, avgDailyRate4, ravPar4,test_steps);
						
						String roomNumber1="Room Number - "+folioRoomClassRoomNumberData.get("roomNumbers").get(0)+"";
						String roomNumber2="Room Number - "+folioRoomClassRoomNumberData.get("roomNumbers").get(1)+"";
						
						test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
						app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");										
						String date1= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy, EEEE");
						netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomNumber1, date1, netRes3, netNights3, roomRevenue3, otherRevenues3, totalRevenue3, 
								avgStay3, avgDailyRate3, ravPar3,test_steps);
						
						test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
						app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");	
						
						netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomNumber2, date1, netRes3, netNights3, roomRevenue4, otherRevenues4, totalRevenue4, 
								avgStay4, avgDailyRate4, ravPar4,test_steps);
					}
				}
			}
			}else {
				for(int i=0;i<Utility.splitInputData(groupNetSalesBY).size();i++) {				
					String roomRev=null ,otherRev=null;
					 nights=Utility.getNumberofDays(checkInDate, checkOutDate);
					app_logs.info(nights);
					String date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");					
					String dateRange= ""+dateOption+" | "+date+" to "+date+"";
					String dateDetail=Utility.parseDate(checkInDates.get(0), "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
					app_logs.info("Date-" +dateDetail);			
					if((Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked") && i==0)||(Utility.splitInputData(groupNetSalesBY).get(1).equalsIgnoreCase("Rate Plan")&& i==1)) {		
						Wait.wait60Second();
						runRunWithDifferentParameter(dateOption, groupNetSalesBY, i);
						test_steps.add("========= Verify Main and Middle Header =========");
						 app_logs.info("========= Verify Main and Middle Header =========");
						HashMap<String, String> mainHeaderData=report.getMainHeaderNetSalesReport(driver, test_steps);
						HashMap<String, String> middleHeaderData=report.getMiddleHeaderNetSalesReport(driver, test_steps);
						report.verifyMainHeaderData(driver, test_steps, mainHeaderData, dateRange, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "Day", "All");
						report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", date, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "", "All", groupRowBy, "No");

						if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked")&& i==0) {
							report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 1, userlist.get(0), test_steps);	
							}else if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("Rate Plan")&& i==1) {
							report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 1, Utility.splitInputData(rateplan).get(0), test_steps);		
							}
						if(Utility.splitInputData(groupNetSalesBY).get(0).equalsIgnoreCase("User Booked") && i==0) {
								netRes=String.valueOf(1+Integer.parseInt(userBookedData.get("summaryViewNetRes").get(0)));
								netRoomNights=String.valueOf(nights+Integer.parseInt(userBookedData.get("summaryViewRoomNights").get(0)));
								roomRev =refinePrice(userBookedData.get("summaryViewRoomRevenue").get(0),userBookedData.get("summaryViewOtherRevenue").get(0)).get("roomRev");
								otherRev=refinePrice(userBookedData.get("summaryViewRoomRevenue").get(0),userBookedData.get("summaryViewOtherRevenue").get(0)).get("otherRev");
								detailHeader=Utility.splitInputData(groupNetSalesBY).get(0) +" - "+ userlist.get(0);
							}else if(Utility.splitInputData(groupNetSalesBY).get(1).equalsIgnoreCase("Rate Plan") && i==1) {
								netRes=String.valueOf(1+Integer.parseInt(ratePlanData.get("summaryViewNetRes").get(0)));
								netRoomNights=String.valueOf(nights+Integer.parseInt(ratePlanData.get("summaryViewRoomNights").get(0)));
								roomRev =refinePrice(ratePlanData.get("summaryViewRoomRevenue").get(0),ratePlanData.get("summaryViewOtherRevenue").get(0)).get("roomRev");
								otherRev=refinePrice(ratePlanData.get("summaryViewRoomRevenue").get(0),ratePlanData.get("summaryViewOtherRevenue").get(0)).get("otherRev");
								detailHeader=Utility.splitInputData(groupNetSalesBY).get(1) +" - "+ Utility.splitInputData(rateplan).get(0);
							}
							app_logs.info(netRes);					
							app_logs.info(netRoomNights);
							app_logs.info(roomRev);
							app_logs.info(otherRev);
							app_logs.info(detailHeader);
							totalNightsForRoomNumber=netRoomNights;
							String roomRevenue=String.valueOf(Double.parseDouble(folioUserBookedRatePlanData.get("roomCharge"))+Double.parseDouble(roomRev));
						String otherRevenue=String.valueOf(Double.parseDouble(folioUserBookedRatePlanData.get("incidentals"))+Double.parseDouble(otherRev));
						String avgStay=report.calculateAVGStay(driver, netRoomNights, netRes);
						String avgDailyRate=report.calculateAvgDailyRate(driver, roomRevenue, netRoomNights);			
						String totalRevenue=String.valueOf(Double.parseDouble(roomRevenue)+Double.parseDouble(otherRevenue));
					    String ravPar=report.calcualteRevPAR(driver, roomRevenue, rooms, totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
						app_logs.info(roomRevenue);
						app_logs.info(otherRevenue);
						app_logs.info(avgStay);
						app_logs.info(avgDailyRate);
						app_logs.info(totalRevenue);
						app_logs.info(ravPar);						
						netSalesData.verifySummaryViewNetSalesUserBookedRatePlan(driver,netRes, netRoomNights, roomRevenue, otherRevenue, 
								totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);	
						
						netSalesData.verifyDetailedNetSalesUserBookedRatePlan(driver,detailHeader, dateDetail, netRes, netRoomNights, 
								roomRevenue, otherRevenue,totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);
						
					}	else if((Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2)||(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")&& i==3)||(Utility.splitInputData(groupNetSalesBY).get(4).equalsIgnoreCase("All") && i==4)){
						Wait.wait5Second();
						if((Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2)||(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")&& i==3)) {
						runRunWithDifferentParameter(dateOption, groupNetSalesBY, i);
						}else if((Utility.splitInputData(groupNetSalesBY).get(4).equalsIgnoreCase("All") && i==4)) {
							runRunWithDifferentParameter("All", groupNetSalesBY, 2);
						}
						HashMap<String, String> mainHeaderData=report.getMainHeaderNetSalesReport(driver, test_steps);
						HashMap<String, String> middleHeaderData=report.getMiddleHeaderNetSalesReport(driver, test_steps);					
						String netNights1=String.valueOf(Integer.parseInt(roomClassData.get("roomClassOneData").get("Net Room Nights"))+1);
						String netNights2=String.valueOf(Integer.parseInt(roomClassData.get("roomClassTwoData").get("Net Room Nights"))+1);
						int totalNights=Integer.parseInt(netNights1)+Integer.parseInt(netNights2);
						app_logs.info(netNights1);
						app_logs.info(netNights2);
						app_logs.info(totalNights);
						if((Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2)||(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")&& i==3)){							
						report.verifyMainHeaderData(driver, test_steps, mainHeaderData, dateRange, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "Day", "All");
						report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", date, Utility.splitInputData(groupNetSalesBY).get(i), "Total Revenue", "", "All", groupRowBy, "No");
						}else if((Utility.splitInputData(groupNetSalesBY).get(4).equalsIgnoreCase("All") && i==4)) {
							report.verifyMainHeaderData(driver, test_steps, mainHeaderData, "All", Utility.splitInputData(groupNetSalesBY).get(2), "Total Revenue", "Day", "All");
							report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", "All", Utility.splitInputData(groupNetSalesBY).get(2), "Total Revenue", "", "All", groupRowBy, "No");	
						}
						if((Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2)||(Utility.splitInputData(groupNetSalesBY).get(4).equalsIgnoreCase("All") && i==4)) {
							for(String str:Utility.splitInputData(roomClass)) {
								report.verifyNetSalesReportSummaryViewCategoryWise(driver, "Summary View", "Net Sales Report", 1, str, test_steps);
								}
							}else if(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")  && i==3) {
								for(String str:folioRoomClassRoomNumberData.get("roomNumbers")) {
									report.verifyNetSalesReportSummaryViewCategoryWise(driver, "Summary View", "Net Sales Report", 1, str, test_steps);
									}
							}
						if(Utility.splitInputData(groupNetSalesBY).get(2).equalsIgnoreCase("Room Class") && i==2) {
						test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
						app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
						String netRes1=String.valueOf(Integer.parseInt(roomClassData.get("roomClassOneData").get("Net Res"))+1);						
						String bookingNights1=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights1);
						String roomRevenue1=String.valueOf(Double.parseDouble(roomClassData.get("roomClassOneData").get("Room Revenue"))+ Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(0)));
						String otherRevenues1=String.valueOf(Double.parseDouble(roomClassData.get("roomClassOneData").get("Other Revenue"))+Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(2)));
						String totalRevenue1=String.valueOf(Double.parseDouble(roomRevenue1)+Double.parseDouble(otherRevenues1));
						String avgStay1=report.calculateAVGStay(driver, netNights1, netRes1);
						String avgDailyRate1=report.calculateAvgDailyRate(driver, roomRevenue1, netNights1);
						String ravPar1=report.calcualteRevPAR(driver, roomRevenue1, roomsofRoomClass.get(0), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
						netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(0), netRes1, netNights1, bookingNights1, roomRevenue1, 
								otherRevenues1, totalRevenue1, avgStay1, avgDailyRate1, ravPar1,test_steps);		
						test_steps.add("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
						app_logs.info("========= Verify Net Sales Summary View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
						String netRes2=String.valueOf(Integer.parseInt(roomClassData.get("roomClassTwoData").get("Net Res"))+1);
						String bookingNights2=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights2);
						String roomRevenue2=String.valueOf(Double.parseDouble(roomClassData.get("roomClassTwoData").get("Room Revenue"))+Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(1)));
						String otherRevenues2=String.valueOf(Double.parseDouble(roomClassData.get("roomClassTwoData").get("Other Revenue"))+Double.parseDouble(folioRoomClassRoomNumberData.get("revenuesandnights").get(3)));
						String totalRevenue2=String.valueOf(Double.parseDouble(roomRevenue2)+Double.parseDouble(otherRevenues2));
						String avgStay2=report.calculateAVGStay(driver, netNights2, netRes2);
						String  avgDailyRate2=report.calculateAvgDailyRate(driver, roomRevenue2, netNights2);
						String ravPar2=report.calcualteRevPAR(driver, roomRevenue2, roomsofRoomClass.get(1), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
						netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(1), netRes2, netNights2, bookingNights2, roomRevenue2, 
								otherRevenues2, totalRevenue2, avgStay2, avgDailyRate2, ravPar2,test_steps);
						String roomClassName1="Room Class - "+Utility.splitInputData(roomClass).get(0)+"";
						String roomClassName2="Room Class - "+Utility.splitInputData(roomClass).get(1)+"";
						test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");
						app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(0)+"=========");										
						String date1= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
						netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomClassName1, date1, netRes1, netNights1, roomRevenue1, otherRevenues1, totalRevenue1, 
								avgStay1, avgDailyRate1, ravPar1,test_steps);						
						test_steps.add("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");
						app_logs.info("========= Verify Net Sales Detailed View Data Of "+Utility.splitInputData(roomClass).get(1)+"=========");							
						netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomClassName2, date1, netRes2, netNights2, roomRevenue2, otherRevenues2, totalRevenue2, 
								avgStay2, avgDailyRate2, ravPar2,test_steps);
					}
						else if(Utility.splitInputData(groupNetSalesBY).get(3).equalsIgnoreCase("Room Number")  && i==3) {
							String netRes3=String.valueOf(1);
							String netNights3=String.valueOf(1);
							String bookingNights3=report.calculateBokingNights(driver, String.valueOf(totalNights), netNights3);
							String date1= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
							for(String str: folioRoomClassRoomNumberData.get("roomNumbers")) {
								String classOneRoomRevenue=folioRoomClassRoomNumberData.get("roomClassNamesWithRoomRevenue").get(0).split(":")[0];
								String classTwoRoomRevenue=folioRoomClassRoomNumberData.get("roomClassNamesWithRoomRevenue").get(1).split(":")[0];
								String classOneOtherRevenue=folioRoomClassRoomNumberData.get("roomClassNamesWithOtherRevenue").get(0).split(":")[0];
								String classTwoOtherRevenue=folioRoomClassRoomNumberData.get("roomClassNamesWithOtherRevenue").get(1).split(":")[0];
								test_steps.add("========= Verify Net Sales Summary View Data Of "+str+"=========");
								app_logs.info("========= Verify Net Sales Summary View Data Of "+str+"=========");
								String revenueRoom=null, revenueOther=null, roomNumber=null;
								if((str.split(":")[0]).equalsIgnoreCase(classOneRoomRevenue) && (str.split(":")[0]).equalsIgnoreCase(classOneOtherRevenue)) {
									 revenueRoom=String.valueOf(folioRoomClassRoomNumberData.get("roomClassNamesWithRoomRevenue").get(0).split(":")[1]);
									 revenueOther=String.valueOf(folioRoomClassRoomNumberData.get("roomClassNamesWithOtherRevenue").get(0).split(":")[1]);
									 roomNumber="Room Number - "+str+"";
								}else if((str.split(":")[0]).equalsIgnoreCase(classTwoRoomRevenue) && (str.split(":")[0]).equalsIgnoreCase(classTwoOtherRevenue)) {
									 revenueRoom=String.valueOf(folioRoomClassRoomNumberData.get("roomClassNamesWithRoomRevenue").get(1).split(":")[1]);
									 revenueOther=String.valueOf(folioRoomClassRoomNumberData.get("roomClassNamesWithOtherRevenue").get(1).split(":")[1]);
									 roomNumber="Room Number - "+str+"";
								}								
								String totalRevenue=String.valueOf(Double.parseDouble(revenueRoom)+Double.parseDouble(revenueOther));
								String avgStay=report.calculateAVGStay(driver, netNights3, netRes3);
								String avgDailyRate=report.calculateAvgDailyRate(driver, revenueRoom, netNights3);
								String ravPar=report.calcualteRevPAR(driver, revenueRoom, "1", totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");									
								netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,str, netRes3, netNights3, bookingNights3, revenueRoom, 
										revenueOther, totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);
								test_steps.add("========= Verify Net Sales Detailed View Data Of "+ str+"=========");
								app_logs.info("========= Verify Net Sales Detailed View Data Of "+ str+"=========");										
								
								netSalesData.verifyNetSalesDetailViewDataClassWiseRowWise(driver,roomNumber, date1, netRes3, netNights3, revenueRoom, revenueOther, totalRevenue, 
										avgStay, avgDailyRate, ravPar,test_steps);
							}
						}
						else if(Utility.splitInputData(groupNetSalesBY).get(4).equalsIgnoreCase("All")  && i==4) {						
							test_steps.add("========= Verify Net Sales Summary View Data  With All Option"+Utility.splitInputData(roomClass).get(0)+"=========");
							app_logs.info("========= Verify Net Sales Summary View Data  With All Option "+Utility.splitInputData(roomClass).get(0)+"=========");
							String totalRoomNight=report.getTotalOfNetSalesSummaryViewDataRowWise(driver, "Summary View", "Net Sales Report", 2);
							String netRes5=String.valueOf(Integer.parseInt(roomClassDataAll.get("roomClassOneData").get("Net Res"))+1);
							String netNights5=String.valueOf(Integer.parseInt(roomClassDataAll.get("roomClassOneData").get("Net Room Nights"))+nights);
							String bookingNights5=report.calculateBokingNights(driver,totalRoomNight, netNights5);
							String roomRevenue5=String.valueOf(Double.parseDouble(roomClassDataAll.get("roomClassOneData").get("Room Revenue"))+ Double.parseDouble(folioRoomClassRoomNumberAllData.get("revenuesandnights").get(0)));
							String otherRevenues5=String.valueOf(Double.parseDouble(roomClassDataAll.get("roomClassOneData").get("Other Revenue"))+Double.parseDouble(folioRoomClassRoomNumberAllData.get("revenuesandnights").get(2)));
							String totalRevenue5=String.valueOf(Double.parseDouble(roomRevenue5)+Double.parseDouble(otherRevenues5));
							String avgStay5=report.calculateAVGStay(driver, netNights5, netRes5);
							String avgDailyRate5=report.calculateAvgDailyRate(driver, roomRevenue5, netNights5);
						//	String ravPar5=report.calcualteRevPAR(driver, roomRevenue5, roomsofRoomClass.get(0), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
							netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(0), netRes5, netNights5, bookingNights5, roomRevenue5, 
							otherRevenues5, totalRevenue5, avgStay5, avgDailyRate5, "",test_steps);
							test_steps.add("========= Verify Net Sales Summary View Data  With All Option"+Utility.splitInputData(roomClass).get(1)+"=========");
							app_logs.info("========= Verify Net Sales Summary View Data  With All Option "+Utility.splitInputData(roomClass).get(1)+"=========");						
							String netRes6=String.valueOf(Integer.parseInt(roomClassDataAll.get("roomClassTwoData").get("Net Res"))+1);
							String netNights6=String.valueOf(Integer.parseInt(roomClassDataAll.get("roomClassTwoData").get("Net Room Nights"))+nights);
							String bookingNights6=report.calculateBokingNights(driver, String.valueOf(totalRoomNight), netNights6);
							String roomRevenue6=String.valueOf(Double.parseDouble(roomClassDataAll.get("roomClassTwoData").get("Room Revenue"))+Double.parseDouble(folioRoomClassRoomNumberAllData.get("revenuesandnights").get(1)));
							String otherRevenues6=String.valueOf(Double.parseDouble(roomClassDataAll.get("roomClassTwoData").get("Other Revenue"))+Double.parseDouble(folioRoomClassRoomNumberAllData.get("revenuesandnights").get(3)));
							String totalRevenue6=String.valueOf(Double.parseDouble(roomRevenue6)+Double.parseDouble(otherRevenues6));
							String avgStay6=report.calculateAVGStay(driver, netNights6, netRes6);
							String avgDailyRate6=report.calculateAvgDailyRate(driver, roomRevenue6, netNights6);
							//String ravPar6=report.calcualteRevPAR(driver, roomRevenue6, roomsofRoomClass.get(1), totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
							netSalesData.verifyNetSalesSummaryViewDataClassWiseRowWise(driver,Utility.splitInputData(roomClass).get(1), netRes6, netNights6, bookingNights6, roomRevenue6, 
															otherRevenues6, totalRevenue6, avgStay6, avgDailyRate6, "",test_steps);
							String roomClassName1="Room Class - "+Utility.splitInputData(roomClass).get(0)+"";
							String roomClassName2="Room Class - "+Utility.splitInputData(roomClass).get(1)+"";
							test_steps.add("========= Verify Net Sales Detailed View Data With All Option "+Utility.splitInputData(roomClass).get(0)+"=========");
							app_logs.info("========= Verify Net Sales Detailed View Data With All Option "+Utility.splitInputData(roomClass).get(0)+"=========");										
							String date1= Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
							netSalesData.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver,roomClassName1, date1, netRes5, netNights5, roomRevenue5, otherRevenues5, totalRevenue5, 
							avgStay5, avgDailyRate5, "",test_steps);													
							test_steps.add("========= Verify Net Sales Detailed View Data With All Option "+Utility.splitInputData(roomClass).get(1)+"=========");
							app_logs.info("========= Verify Net Sales Detailed View Data With All Option "+Utility.splitInputData(roomClass).get(1)+"=========");														
							netSalesData.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver,roomClassName2, date1, netRes6, netNights6, roomRevenue6, otherRevenues6, totalRevenue6, 
															avgStay6, avgDailyRate6, "",test_steps);
						}
				}
			}
				
				 for(int i=0;i<Utility.testId.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Net Sales Report");
					}	
				}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}catch (Exception e) {
		Utility.catchException(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
				test_category, test_steps);
	} catch (Error e) {
		Utility.catchError(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
				test_category, test_steps);
	}	
	}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyNetSalesReportData", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
		
	}


}
