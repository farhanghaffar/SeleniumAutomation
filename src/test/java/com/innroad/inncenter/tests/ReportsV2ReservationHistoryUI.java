package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.Users;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2ReservationHistoryUI extends TestCore{
	
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
	ReservationV2 reservationPage=  new ReservationV2();
	String testName=null,propertyName=null;
	Properties prop= new Properties();
	Users user= new Users();
	boolean isSorted(List<String> list){
		 boolean sorted = true;
		      for (int i = 0; i <list.size()-1; i++) {
		         if (Integer.parseInt(list.get(i)) <=Integer.parseInt(list.get(i+1)) ) {
		             sorted = false;
		             } 
		          } 
		      return sorted;
		}
	
	public void isSortedDateTime(List<String> date, List<String> time, List<String> confirmationNo) throws ParseException
	{
		List<String> confirmation= new ArrayList<String>();
		boolean issort=true;
		for(int i = 0; i < date.size()-1;i++) {
			String date1=Utility.parseDate(date.get(i), "MMM d,yyyy", "MMM dd,yyyy");
			String date2=Utility.parseDate(date.get(i+1), "MMM d,yyyy", "MMM dd,yyyy");
			if(date1.compareTo(date2)==0) {
				confirmation.add(confirmationNo.get(i));
			}else if(date1.compareTo(date2)<0) {
				if(!confirmation.isEmpty()) {
				confirmation.add(confirmationNo.get(i));
				issort=isSorted(confirmation);
				Utility.verifyBooleanEquals(false, issort, test_steps);
				confirmation.clear();}			
				 issort=false;
				Utility.verifyBooleanEquals(false, issort, test_steps);
				}else {
					confirmation.add(confirmationNo.get(i));
					issort=isSorted(confirmation);
					Utility.verifyBooleanEquals(false, issort, test_steps);
					confirmation.clear();
				}			
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
	public void reportsV2ReservationHistoryUI(String testCaseID, String dateRange) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "ReportsV2 - VerifyReservationHistory";
		test_description = "ReportsV2 - VerifyReservationHistoryUI<br>";
		test_category = "ReportsV2 - Reservation History";
		 testName = test_name + "--" + testCaseID;
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode,
				Utility.comments, "");
		ArrayList<String> userlist= new ArrayList<String>();
		// Login
		try {		
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
			test_steps.add("========= Navigating to Reservation Hisory Report =========");
			app_logs.info("========= Navigating to Reservation Hisory Report =========");
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			app_logs.info("Successfully navigated to reports page.");
			report.navigateToReservationHistoryReport(driver,test_steps);
			
			test_steps.add("========= Verify Reservation History Default Value =========");
			app_logs.info("========= Verify Reservation History Default Value =========");
			Elements_Reports reports= new Elements_Reports(driver);
			test_steps.add("========= Verify Reservation History Choose Date Range =========");
			app_logs.info("========= Verify Reservation History Choose Date Range =========");
			String startDate=Utility.parseDate(Utility.getCurrentDate("dd/MM/yyyy"), "dd/MM/yyyy", "MMM dd, yyyy");
			Utility.verifyTrue(reports.reservationHistoryDefaultDateRange,reports.reservationHistoryDefaultDateRange.getText(), test_steps);
			Utility.verifyEquals(Utility.getAttribtue(reports.reservationHistoryStartDate, "value"), startDate, test_steps);
			Utility.verifyEquals(Utility.getAttribtue(reports.reservationHistoryEndDate, "value"), startDate, test_steps);
			test_steps.add("========= Verify Reservation History Included Reservation History Categories =========");
			app_logs.info("========= Verify Reservation History Included Reservation History Categories =========");
			Utility.verifyTrue(reports.reservationHistorySelectedInputCheckBoxs,"All Check Box  Checked", test_steps);
			
			test_steps.add("========= Verify Reservation History Customize Detailed View =========");
			app_logs.info("========= Verify Reservation History Customize Detailed View =========");
			
			Utility.verifyTrue(reports.reservationHistoryShortReportBy,reports.reservationHistoryShortReportBy.getText(), test_steps);
			
			test_steps.add("========= Verify Reservation History Advanced Input");
			app_logs.info("========= Verify Reservation History Advanced Input =========");
			report.clickAdvancedInputExpandAllOfReservationHistoryReport(driver, test_steps);
			Utility.verifyTrue(reports.reservationHistoryAdvanceInputUser,reports.reservationHistoryAdvanceInputUser.getText(), test_steps);
			Utility.verifyTrue(reports.reservationHistoryShiftTime,"Shift Time Coming Checked", test_steps);
            
			test_steps.add("========= Verify Reservation History Advanced Input User List");
			app_logs.info("========= Verify Reservation History Advanced Input User List =========");
			report.verifyReservationHistoryUserOption(driver, test_steps, userlist.get(0));
			
			test_steps.add("========= Verify Reservation History Shift time toggle is in Enabled=========");
			app_logs.info("========= Verify Reservation History Shift time toggle is in Enabled =========");
			report.selectDateRange(driver, dateRange, test_steps);
			report.OnOROfShiftTimeToggleOfReservationHistoryReport(driver, test_steps,true);
			ArrayList<String> dateTime= new ArrayList<String>();
			dateTime=report.getShiftTimeOfReservationHistory(driver);
			String incluseDataForm=report.getIncludeDataFormOfReservationHistory(driver);
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);	
			String expectedValue= ""+incluseDataForm+" | "+dateTime.get(0)+" to "+dateTime.get(1)+"";
			report.verifyMainHeaderOfReservationHistory(driver, test_steps, 4, expectedValue);
			
			test_steps.add("========= Verify Reservation History Sort Report By Guest Name=========");
			app_logs.info("========= Verify Reservation History Sort Report By Guest Name=========");
			Wait.wait5Second();
			report.editButtonClick(driver, test_steps);
			report.clickAdvancedInputExpandAllOfReservationHistoryReport(driver, test_steps);
			report.OnOROfShiftTimeToggleOfReservationHistoryReport(driver, test_steps,false);
			report.verifyReservationHistorySortReportByOption(driver, test_steps);
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);	
			List<String> confirmationList= new ArrayList<String>();
			confirmationList=report.getReportsSummaryViewWise(driver, "Detailed View", "Reservation History Report", 2);
			app_logs.info(confirmationList);	
			boolean isSort=isSorted(confirmationList);
			Utility.verifyBooleanEquals(false, isSort, test_steps);
			
			test_steps.add("========= Verify Reservation History Sort Report By Date & Time=========");
			app_logs.info("========= Verify Reservation History Sort Report By Date & Time=========");
			report.editButtonClick(driver, test_steps);
			report.selectGivenSortReportByOption(driver, "Date and Time", test_steps);
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);	
			confirmationList.clear();
			confirmationList=report.getReportsSummaryViewWise(driver, "Detailed View", "Reservation History Report", 2);
			List<String> dateList= new ArrayList<String>();
			List<String> timeList= new ArrayList<String>();
			dateList=report.getReportsSummaryViewWise(driver, "Detailed View", "Reservation History Report", 4);
			timeList=report.getReportsSummaryViewWise(driver, "Detailed View", "Reservation History Report", 5);
			app_logs.info(dateList);	
			app_logs.info(timeList);				
			boolean isSort1=isSorted(confirmationList);
			Utility.verifyBooleanEquals(false, isSort1, test_steps);
			isSortedDateTime(dateList,timeList,confirmationList);
			
			driver.close();
			Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Reservation History ");
			}	
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2ReservationHistoryUI", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		
		driver.quit();
		
	}

}
