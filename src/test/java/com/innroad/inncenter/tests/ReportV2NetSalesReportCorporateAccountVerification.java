package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportV2NetSalesReportCorporateAccountVerification extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation nav = new Navigation();
	ReportsV2 report = new ReportsV2();
	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage = new ReservationV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	RoomStatus roomstatus = new RoomStatus();
	RoomMaintenance roommaintenance = new RoomMaintenance();
	TaxesAndFee taxFee = new TaxesAndFee();
	Users user = new Users();
	FolioNew folio = new FolioNew();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	GetNetSalesDataUserBooked netSalesData= new GetNetSalesDataUserBooked();
	Account account= new Account();
	Groups group= new Groups();
	String confirmationNo = null, testName = null, startDate = null, date = null,propertyName=null,rooms=null,totalRoomOutOFOrder=null,night=null;;
	double roomRevenue=0.00, otherRevenue=0.00;
	
	private void runRunWithDifferentParameter(String dateOption, String groupNetSalesBY) throws InterruptedException, ParseException {
		Wait.wait5Second();
		report.editButtonClick(driver, test_steps);
		Wait.wait5Second();
		report.selectDateRange(driver, dateOption, test_steps);
		report.selectGroupNetSalesByOption(driver, test_steps,groupNetSalesBY);
		report.clickOnRunReport(driver);
		test_steps.add("Run Report");					
		app_logs.info("Run Report");		
		report.verifyLoadingReport(driver);	
		Wait.wait10Second();
	}
	
	private void createReservationAndGetData(String checkInDate,
			String checkOutDate, String adults, String children, String rateplan, String roomClass, String salutation,
			String guestFirstName, String guestLastName, String paymentType, String cardNumber, String nameOnCard,String marketSegment, String referral,
			String category, String amount, String  accountName, String groupNetSalesBY, String action) throws Exception {
		driver.close();
		Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
		report.verifyLoadingReport(driver);
		if(action.equalsIgnoreCase("Group")) {
			nav.Reservation_Backward_3(driver);
			app_logs.info("Back to reservation page");	}							
		String yearDate=Utility.getFutureMonthAndYearForMasterCard();
		HashMap<String, String> reservationData= new HashMap<String, String>();		
		reservationPage.createReservation_RV2(driver, test_steps, action, checkInDate, checkOutDate, adults,
				children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, true,
				"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
				0, "",false, "","", "","", "","", "", "", "","", accountName, "Corporate/Member Accounts","", "",false, "");
		confirmationNo=reservationData.get("ReservationNumber");				
		reservationPage.click_Folio(driver, test_steps);
		Wait.wait5Second();
		folio.addFolioLineItemMRB(driver, test_steps, category, amount); 
		Wait.wait5Second();
		double fee=0.00;
		Multimap<String, String> feeAdjustment= ArrayListMultimap.create();
		Collection<String> feeAdjust= new ArrayList<String>();
		Multimap<String, String> roomCharges= ArrayListMultimap.create();	
		Collection<String> roomChargesSum= new ArrayList<String>();
		otherRevenue=otherRevenue+folio.getFolioRoomCharges(driver, test_steps, "Spa");		
		feeAdjustment=folio.getFolioRoomChargesDateWise(driver, test_steps, "Fee Adjustment");
		roomCharges=folio.getFolioRoomChargesDateWise(driver, test_steps, "Room Charge");
		app_logs.info("Data- " + feeAdjustment);					
		feeAdjust=feeAdjustment.get(date);
			for (String str : feeAdjust)  {
			fee=fee+Double.parseDouble(str);}
			otherRevenue=otherRevenue+fee;
			app_logs.info(otherRevenue);
			roomChargesSum=roomCharges.get(date);
			app_logs.info(roomChargesSum);
			for (String str : roomChargesSum)  {
			roomRevenue=roomRevenue+Double.parseDouble(str);}
			app_logs.info(roomRevenue);
			app_logs.info(otherRevenue);					
		reservation.closeReservationTab(driver);	
	}
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportV2NetSalesReportCorporateAccountVerification(String testCaseID, String checkInDate,
			String checkOutDate, String adults, String children, String rateplan, String roomClass, String salutation,
			String guestFirstName, String guestLastName, String paymentType, String cardNumber, String nameOnCard,
			String marketSegment, String referral, String category, String amount, String dateOption, String groupRowBy,
			String feeName, String feeType, String categoryFees, String infeeValue, String groupNetSalesBY, String accountAndGroupName,String action)
			throws InterruptedException, IOException, ParseException {

		boolean isExecutable=Utility.getResultForCase(driver, testCaseID);
		if(isExecutable) {		
		test_name = "ReportsV2 - VerifyNetSalesReport  -  " + testCaseID;
		test_description = "ReportsV2 - VerifyNetSalesReport<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/" + testCaseID + "' target='_blank'>"
				+ "Click here to open TestRail: " + testCaseID + "</a><br>";
		test_category = "ReportsV2 - Net Sales report";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		boolean isCorporateOrGroupExist=false;	
		HashMap<String, List<String>> corporateOrGroupAccountSummaryData= new HashMap<String, List<String>>();
		HashMap<String, String>  corporateAccountDetailData= new HashMap<String, String> ();
		
		// Login
		try {

			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			if (guestFirstName.split("\\|").length > 1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}
			startDate = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(startDate);
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName) + 1);
			}

			String[] days = checkInDates.get(0).split("\\/");
			if (Character.toString(days[0].charAt(0)).equals("0")) {
				date = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy");
			} else {
				date = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
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

		try
		{
			propertyName = prop.getProperty(driver, test_steps);
			nav.clickSetup(driver);
			test_steps.add("=========Get Rooms=========");
			app_logs.info("=========Get Rooms=========");
			nav.properties(driver);
			rooms=prop.getRoomNumber(driver, propertyName);		
			app_logs.info(rooms);
			nav.guestservices_Click(driver, test_steps);
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
				taxFee.deleteAllTaxesAndFee(driver, test_steps);
			}
			feeName= feeName+Utility.threeDigitgenerateRandomString();
			taxFee.createFee(driver, test_steps, feeName, feeName, feeType,feeName, categoryFees, infeeValue, false, "", "", "");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
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
			report.selectGroupRowsByOption(driver, test_steps, groupRowBy);
			report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesBY);
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);
			
			isCorporateOrGroupExist=report.verifyCorporateAccountDataExistOrNot(driver, "Summary View", "Net Sales Report", accountAndGroupName);
			if(!isCorporateOrGroupExist) {				
				test_steps.add("========= Report Data not Available, Create New Reservation=========");
				app_logs.info("========= Report Data not Available, Create New Reservation =========");
				createReservationAndGetData(checkInDate, checkOutDate, adults,
						children, rateplan,  roomClass,  salutation, guestFirstName, guestLastName,paymentType,cardNumber,nameOnCard,marketSegment, referral, category,amount,
						accountAndGroupName, groupNetSalesBY,action);		
			}else {
				test_steps.add("===================Get Corporate Account Data from Report==================");					
				app_logs.info("===================Get Corporate Account Data from Report==================");	
				corporateOrGroupAccountSummaryData=netSalesData.netSalesReportSummaryDataCorporateOrGroupAccount(driver, accountAndGroupName);
				app_logs.info(corporateOrGroupAccountSummaryData);
				createReservationAndGetData(checkInDate, checkOutDate, adults,
						children, rateplan,  roomClass,  salutation, guestFirstName, guestLastName,paymentType,cardNumber,nameOnCard,marketSegment, referral, category,amount,
						accountAndGroupName, groupNetSalesBY,action);
			}			
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			app_logs.info("Successfully navigated to reports page.");
			report.navigateToNetSalesReport(driver, test_steps);
			test_steps.add("Navigated to Net Sales reports page");					
			app_logs.info("Navigated to Net Sales reports page");	
			Wait.wait5Second();
			Wait.wait60Second();
			Wait.wait60Second();
			report.selectDateRange(driver, dateOption, test_steps);
			report.selectGroupNetSalesByOption(driver, test_steps,groupNetSalesBY);
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
			String totalRevenue=null,avgStay=null,avgDailyRate=null,ravPar=null,detailHeader=null, netRes=null, roomRevenues=null, otherRevenues=null,dateDetail=null,
					bookingNights=null;
			test_steps.add("========= Verify Net Sales Report =========");
			app_logs.info("========= Verify Net Sales Report =========");
			String date=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");			
			String dateRange= ""+dateOption+" | "+date+" to "+date+"";
			dateDetail=Utility.parseDate(checkInDates.get(0), "dd/MM/yyyy", "MMM dd, yyyy, EEEE");
			app_logs.info("Date-" +dateDetail);	
			
			netRes="1";
			night="2";
			if(!isCorporateOrGroupExist) {						 				 
				 roomRevenues=String.valueOf(roomRevenue);
				 otherRevenues=String.valueOf(otherRevenue);
					}else {
					netRes=String.valueOf(Integer.parseInt(netRes)+Integer.parseInt(corporateOrGroupAccountSummaryData.get("summaryViewNetRes").get(0)));
					night=String.valueOf(Integer.parseInt(night)+Integer.parseInt(corporateOrGroupAccountSummaryData.get("summaryViewRoomNights").get(0)));
					roomRevenues=String.valueOf(roomRevenue+Double.parseDouble(corporateOrGroupAccountSummaryData.get("summaryViewRoomRevenue").get(0)));
					otherRevenues=String.valueOf(otherRevenue+Double.parseDouble(corporateOrGroupAccountSummaryData.get("summaryViewOtherRevenue").get(0)));
					}	
			
			 totalRevenue=report.calculateTotalRevenue(driver,roomRevenues, otherRevenues);
			 avgStay=report.calculateAVGStay(driver, night, netRes);
			 avgDailyRate=report.calculateAvgDailyRate(driver, roomRevenues, night);
			 ravPar=report.calcualteRevPAR(driver, roomRevenues, rooms, totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");		
					String totalNights=report.getTotalOfNetSalesSummaryViewDataRowWise(driver, "Summary View", "Net Sales Report", 2);
					 bookingNights=report.calculateBokingNights(driver, String.valueOf(totalNights), night);
					HashMap<String, String> mainHeaderData=report.getMainHeaderNetSalesReport(driver, test_steps);
					HashMap<String, String> middleHeaderData=report.getMiddleHeaderNetSalesReport(driver, test_steps);				
					totalRoomOutOFOrder=report.calculateOutofOderAsperDays(driver, checkInDate, checkInDate, totalRoomOutOFOrder);
					app_logs.info(totalRoomOutOFOrder);	
					app_logs.info("Date Range is: "+ dateRange);	
					app_logs.info("Main Header Data is: "+ mainHeaderData);
					app_logs.info("Middle Header Data is: "+ middleHeaderData);
					test_steps.add("========= Verify Main and Middle Header =========");
					 app_logs.info("========= Verify Main and Middle Header =========");
					report.verifyMainHeaderData(driver, test_steps, mainHeaderData, dateRange, groupNetSalesBY, "Total Revenue", "Day", "All");
					report.verifyMiddleHeader(driver, test_steps, middleHeaderData, "Net Sales Report", date, groupNetSalesBY, "Total Revenue", "", "All", groupRowBy, "No");
					test_steps.add("========= Verify net Sales Report Summary Wise =========");
					app_logs.info("========= Verify net Sales Report Summary Wise =========");
					report.verifyCorporateAccountNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", 1, accountAndGroupName, test_steps);	
					detailHeader=groupNetSalesBY +" - "+ accountAndGroupName;
					app_logs.info(detailHeader);
						 		
					 netSalesData.verifySummaryViewNetSalesCorporateAccount(driver,accountAndGroupName,netRes, night,bookingNights, roomRevenues, otherRevenues,
							 totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);				 
					 test_steps.add("========= Verify Net Sales Detailed View =========");
					 app_logs.info("========= Verify Net Sales Detailed View =========");
					 netSalesData.verifyDetailViewNetSalesCorporateAccount(driver,detailHeader, dateDetail, netRes, night, "100.00",  roomRevenues, otherRevenues,
							 totalRevenue, avgStay, avgDailyRate, ravPar,test_steps);	
					 for(int i=0;i<Utility.testId.size();i++) {
							Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Net Sales Report");
						}	
					
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
					test_category, test_steps);
		}	
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyNetSalesCorporateAccount", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
		driver.quit();

		
	}


}
