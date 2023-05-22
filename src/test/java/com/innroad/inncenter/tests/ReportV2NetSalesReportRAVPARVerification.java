package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportV2NetSalesReportRAVPARVerification extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	String seasonStartDate =null, seasonEndDate=null, confirmationNo=null, testName=null,startDate=null,endDate=null, rooms=null,propertyName=null,
			totalRoomOutOFOrder=null;
	ArrayList<String> datesRangeList = new ArrayList<String>();
	Navigation nav = new Navigation();
	ReportsV2 report = new ReportsV2();
	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage=  new ReservationV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	
	RoomStatus roomstatus = new RoomStatus();
	Properties prop= new Properties();
	RoomMaintenance roommaintenance = new RoomMaintenance();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}	

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportV2NetSalesReportRAVPARVerification(String testCaseID, String checkInDate, String checkOutDate,String adults,
			String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral,
			String dateOption, String groupRowBy)throws InterruptedException, IOException, ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "ReportsV2 - VerifyNetSalesReportRavPAR";
		test_description = "ReportsV2 - VerifyNetSalesReportRavPAR<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/849288' target='_blank'>"
				+ "Click here to open TestRail: 849288</a><br>";
		test_category = "ReportsV2 - Net Sales report";
		 testName = test_name;
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");		
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
					} else {
						checkInDate = checkInDates.get(0);
						checkOutDate = checkOutDates.get(0);
					}
					seasonStartDate = checkInDates.get(0);
					seasonEndDate = sessionEndDate.get(0);
					app_logs.info(checkInDate);
					app_logs.info(checkOutDate);
					app_logs.info(seasonStartDate);
					app_logs.info(seasonEndDate);
					if (!Utility.insertTestName.containsKey(testName)) {
						Utility.insertTestName.put(testName, testName);
						Utility.reTry.put(testName, 0);
					} else {
						//Utility.reTry.replace(testName, 1);
						Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
					}
					datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
					app_logs.info(datesRangeList);
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
						test_steps.add("=========Create New Reservation=========");
						app_logs.info("========= Create New Reservation =========");
						
						propertyName = prop.getProperty(driver, test_steps);						
						String yearDate=Utility.getFutureMonthAndYearForMasterCard();
						HashMap<String, String> reservationData= new HashMap<String, String>();
						/*reservationData=reservationPage.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
								children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, 
								"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
								0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");*/
						reservationData=reservationPage.createReservation_RV2(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
								children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName,true, 
								"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
								0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");
						confirmationNo=reservationData.get("ReservationNumber");
						reservation.closeReservationTab(driver);
						app_logs.info("Close Reservation");
						test_steps.add("=========Get Rooms=========");
						app_logs.info("=========Get Rooms=========");
						nav.setup(driver);
						nav.properties(driver);
						rooms=prop.getRoomNumber(driver, propertyName);		
						app_logs.info(rooms);
						test_steps.add("=========Check Out of Order=========");
						app_logs.info("=========Check Out of Order=========");
						nav.guestservices_Click(driver, test_steps);
						roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
						nav.RoomMaintenance(driver);
						test_steps.add("Navigated to RoomMaintenance");
						String startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
						roommaintenance.searchByFromAndToDate(driver, test_steps, "", startDate, "dd/MM/yyyy", "Out of Order");
						Wait.wait15Second();
						totalRoomOutOFOrder= roommaintenance.getroomNumberCountFromMaintainanceofCurrentDate(driver, startDate, "", test_steps);
						app_logs.info(totalRoomOutOFOrder);
						test_steps.add("=========Generate Report=========");
						app_logs.info("========= Generate Report =========");
						nav.ReportsV2(driver);
						test_steps.add("Successfully navigated to reports page.");
						app_logs.info("Successfully navigated to reports page.");
						report.navigateToNetSalesReport(driver, test_steps);
						test_steps.add("Navigated to Net Sales reports page");					
						app_logs.info("Navigated to Net Sales reports page");	
					
				}catch (Exception e) {
					Utility.catchException(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				}	
				
				try {
					report.selectDateRange(driver, dateOption, test_steps);
					report.selectGroupRowsByOption(driver, test_steps, groupRowBy);
					report.clickOnRunReport(driver);
					test_steps.add("Run Report");					
					app_logs.info("Run Report");		
					report.verifyLoadingReport(driver);
					test_steps.add("=========Verify RevPAR =========");
					app_logs.info("========= Verify RevPAR =========");
					Utility.switchTab(driver, 1);
					report.validateNetSalesReportInMainHeader(driver, test_steps);											
					List<String> totalRevenue= new ArrayList<String>();
					List<String> ravPAR= new ArrayList<String>();
					totalRevenue=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 5);
					app_logs.info(totalRevenue);	
					List<Double> totalRevenues = new ArrayList<Double>();	
					String totalRev=null;
					for(int i=0;i<totalRevenue.size()-1;i++) {
						if(totalRevenue.get(i).contains(",") && totalRevenue.get(i).contains("$")) {
							totalRev=Utility.convertDecimalFormat(totalRevenue.get(i).replace("$", "").replace(",", ""));
							}else if(totalRevenue.get(i).contains("$")){
								totalRev=Utility.convertDecimalFormat(totalRevenue.get(i).replace("$", ""));}
							else if(totalRevenue.get(i).matches("^\\d+\\.\\d+") ){
								totalRev=Utility.convertDecimalFormat(totalRevenue.get(i));
							}else if(totalRevenue.get(i).contains("%")) {
								totalRev=Utility.convertDecimalFormat(totalRevenue.get(i).replace("%", ""));
							}
						 double str = Double.parseDouble(totalRev);
						   totalRevenues.add(str);
					   }
					totalRoomOutOFOrder=report.calculateOutofOderAsperDays(driver, checkInDate, checkInDate, totalRoomOutOFOrder);
					app_logs.info(totalRoomOutOFOrder);	
					String ravPARActual=report.calcualteRevPAR(driver, totalRevenues.get(0).toString(), rooms, totalRoomOutOFOrder, "", checkInDate, checkInDate, "", "");
					app_logs.info(ravPARActual);	
					ravPAR=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 11);
					 List<Double> ravVARS = new ArrayList<Double>();						    
					 for(int i=0;i<ravPAR.size()-1;i++) {
						 double str = Double.parseDouble(String.valueOf(ravPAR.get(i)).replace("$", " ").trim());
						   ravVARS.add(str);
					   }
					 Utility.verifyEquals(ravPARActual, ravVARS.get(0).toString(), test_steps);
					 test_steps.add("Verified RavPAR: " + ravPARActual);
					 app_logs.info("Verified RavPAR: " + ravPARActual);	
					 for(int i=0;i<Utility.testId.size();i++) {
							Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Checkin Policy");
						}	
						Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
								Utility.comments, TestCore.TestRail_AssignToID);
						
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
		return Utility.getData("VerifyNetSalesReportRAVPAR", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}

}
