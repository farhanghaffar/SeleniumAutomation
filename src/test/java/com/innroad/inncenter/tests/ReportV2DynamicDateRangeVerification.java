package com.innroad.inncenter.tests;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportV2DynamicDateRangeVerification extends TestCore {
	
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	String seasonStartDate =null, seasonEndDate=null, confirmationNo=null, testName=null,startDate=null,endDate=null;
	List<String> reservationDate= new ArrayList<String>();
	Navigation nav = new Navigation();
	ReportsV2 report = new ReportsV2();
	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage=  new ReservationV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, reportV2excel))
			throw new SkipException("Skipping the test - " + testName);
	}	
	private boolean verifyDecending(double [] array) {	 
		 for (int i = 0; i < array.length-1; i++) {
			    if (array[i] < array[i+1]) {
			        return false;
			    }
			}
			return true;
	}
	
	private void verifyDynamicDateRange(String dateOption, String checkInDate, String checkOutDate,String groupNetSalesBy, String sortReportBy) {
		try {
			test_steps.add("========= Select Date Range=========");
			app_logs.info("========= Select Date Range =========");
			if(dateOption.equalsIgnoreCase("Today")) {
			 startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
			app_logs.info(startDate);
			report.verifyNetSalesDateRangeOption(driver, test_steps);
			report.validateDateRange(driver, dateOption,startDate, startDate, test_steps);
			}else if(dateOption.equalsIgnoreCase("Year To Date")) {
				Calendar calendarStart=Calendar.getInstance();
			    int year=calendarStart.get(Calendar.YEAR);
			    calendarStart.set(Calendar.YEAR,year);
			    calendarStart.set(Calendar.MONTH,0);
			    calendarStart.set(Calendar.DAY_OF_MONTH,1);
			    // returning the first date
			    Date date=calendarStart.getTime();
			    startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
				app_logs.info(startDate);
			     endDate=Utility.parseDate(Utility.convertDateFormattoString( "dd/MM/yyyy", date), "dd/MM/yyyy", "MMM dd, yyyy");
			    app_logs.info(endDate);
			    report.verifyNetSalesDateRangeOption(driver, test_steps);
			    report.validateDateRange(driver, dateOption, endDate,startDate, test_steps);
			}else if(dateOption.equalsIgnoreCase("Month To Date")) {
				 Calendar cal = Calendar.getInstance();
				  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				  Date date1= cal.getTime();
				  startDate=Utility.parseDate(Utility.convertDateFormattoString( "dd/MM/yyyy", date1), "dd/MM/yyyy", "MMM dd, yyyy");
				  endDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
				  app_logs.info(startDate);
				  app_logs.info(endDate);
				  report.verifyNetSalesDateRangeOption(driver, test_steps);
				  report.validateDateRange(driver, dateOption,startDate, endDate, test_steps);
			}else if(groupNetSalesBy.equalsIgnoreCase("Room Class") && !Utility.validateString(sortReportBy)) {
				report.selectDateRange(driver, dateOption, test_steps);
				report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesBy);
			}else if(groupNetSalesBy.equalsIgnoreCase("Average Stay")) {
				report.selectDateRange(driver, dateOption, test_steps);
				report.selectSortReportByOption(driver, test_steps, sortReportBy);
			}else if(groupNetSalesBy.equalsIgnoreCase("Booking Day")) {
				report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesBy);
			}else if(groupNetSalesBy.equalsIgnoreCase("Room Class") && sortReportBy.equalsIgnoreCase("RevPAR")) {
				report.selectDateRange(driver, checkInDate, checkOutDate, "", test_steps);
				report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesBy);
				Wait.wait15Second();
				report.selectSortReportByOption(driver, test_steps, sortReportBy);
				Wait.wait15Second();
			}else if(groupNetSalesBy.equalsIgnoreCase("Room Number") && sortReportBy.equalsIgnoreCase("Total Revenue")) {
				report.selectDateRange(driver, checkInDate, checkOutDate, "", test_steps);
				report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesBy);
				Wait.wait15Second();
				report.selectSortReportByOption(driver, test_steps, sortReportBy);
				Wait.wait15Second();
			}
			report.clickOnRunReport(driver);
			test_steps.add("Run Report");					
			app_logs.info("Run Report");		
			report.verifyLoadingReport(driver);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Navigating to Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Navigating to Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
					test_category, test_steps);
		}
	}
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reportV2DynamicDateRangeVerification(String testCaseID, String checkInDate, String checkOutDate,String adults,String children,String rateplan,String roomClass,String salutation,String guestFirstName,
			String guestLastName,String paymentType, String cardNumber,String nameOnCard,String marketSegment, String referral,
			String dateOption, String groupNetSalesBy, String sortReportBy)throws InterruptedException, IOException, ParseException {

		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "ReportsV2 - VerifyNetSalesReportDynamicDateRange";
		test_description = "ReportsV2 - VerifyNetSalesReportDynamicDateRange<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/849267' target='_blank'>"
				+ "Click here to open TestRail: 849267</a><br>";
		test_category = "ReportsV2 - Net Sales report";
		 testName = test_name + "--" + testCaseID;
		
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
					if(groupNetSalesBy.equalsIgnoreCase("Booking Day")) {
						test_steps.add("========= Get data of Reservation Booked From and To =========");
						app_logs.info("========= Get data of Reservation Booked From and To  =========");
						reservationSearch.clickOnAdvance(driver);
						Wait.wait5Second();
						 Calendar cal = Calendar.getInstance();
						  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
						  Date date1= cal.getTime();
						  startDate=Utility.convertDateFormattoString( "dd/MM/yyyy", date1);
						app_logs.info(startDate);	
						reservationSearch.advanceSearchWithBookFrom(driver, startDate);
						Wait.wait5Second();
						reservationSearch.advanceSearchWithBookTo(driver, checkInDate);
						Wait.wait5Second();
						reservationSearch.clickOnSearchButton(driver);
						Wait.wait10Second();
						
						String totalReservation=reservationSearch.getTotalNumberofRows(driver);
						if(totalReservation!="0") {
							reservationSearch.selectPerPage(driver, Integer.parseInt(totalReservation));
							Utility.ScrollToUp(driver);
							String path="(//ul[@class='pagination']/li[not(contains(@class,'disabled'))]/a)[last()-1]";
							String path1="//span[contains(@data-bind,'text: ConfirmationNumber')]";
							boolean isEnabled= Utility.isElementEnabled(driver, By.xpath(path));
							if(isEnabled) {
								String size= driver.findElement(By.xpath(path)).getText();								
								for(int i=0;i<Integer.parseInt(size);i++) {	
								int totalsize= driver.findElements(By.xpath(path1)).size();
								for(int j=0;j<totalsize;j++) {		
									reservationDate.add(reservationSearch.getArrivalDateAfterSearch(driver, j+1));									
								}
								reservationSearch.clickOnRightPagination(driver);
								}
							}else {
								for(int i=0;i<Integer.parseInt(totalReservation);i++) {
									reservationDate.add(reservationSearch.getArrivalDateAfterSearch(driver, i+1));	
								}
							}
							app_logs.info(reservationDate);
						}
						String startDate=reservationDate.get(reservationDate.size()-1);
						 String enddate=reservationDate.get(0);
						 app_logs.info(startDate);
						 app_logs.info(enddate);
					}else if(sortReportBy.equalsIgnoreCase("RevPAR") || sortReportBy.equalsIgnoreCase("Total Revenue")) {
						test_steps.add("========= Get data of Reservation Booked From and To =========");
						app_logs.info("========= Get data of Reservation Booked From and To  =========");
						reservationSearch.clickOnAdvance(driver);
						Wait.wait5Second();
						app_logs.info("==================");
						 Calendar cal = Calendar.getInstance();
						  cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
						  Date date1= cal.getTime();
						  startDate=Utility.convertDateFormattoString( "dd/MM/yyyy", date1);
						  app_logs.info(startDate);	
						  
							  reservationSearch.advanceSearchWithBookFrom(driver, startDate);
							Wait.wait5Second();
							reservationSearch.advanceSearchWithBookTo(driver, checkInDate);
						Wait.wait5Second();						
						reservationSearch.clickOnSearchButton(driver);
						Wait.wait10Second();
						String totalReservation=reservationSearch.getTotalNumberofRows(driver);
						if(totalReservation!="0") {
							reservationSearch.selectPerPage(driver, Integer.parseInt(totalReservation));
							Utility.ScrollToUp(driver);
							String path="(//ul[@class='pagination']/li[not(contains(@class,'disabled'))]/a)[last()-1]";
							String path1="//span[contains(@data-bind,'text: ConfirmationNumber')]";
							boolean isEnabled= Utility.isElementEnabled(driver, By.xpath(path));
							if(isEnabled) {
								String size= driver.findElement(By.xpath(path)).getText();								
								for(int i=0;i<Integer.parseInt(size);i++) {	
								int totalsize= driver.findElements(By.xpath(path1)).size();
								for(int j=0;j<totalsize;j++) {		
									reservationDate.add(reservationSearch.getArrivalDateAfterSearch(driver, j+1));									
								}
								reservationSearch.clickOnRightPagination(driver);
								}
							}else {
								for(int i=0;i<Integer.parseInt(totalReservation);i++) {
									reservationDate.add(reservationSearch.getArrivalDateAfterSearch(driver, i+1));	
								}
							}
							app_logs.info(reservationDate);
						}
						
						 startDate=reservationDate.get(reservationDate.size()-1);
						 app_logs.info(startDate);
						 startDate= Utility.parseDate(startDate, "MMM dd, yyyy", "dd/MM/yyyy");
						 app_logs.info(startDate);	
					}
					test_steps.add("========= Navigating to Net Sales Report =========");
					app_logs.info("========= Navigating to Net Sales Report =========");
					nav.ReportsV2(driver);
					test_steps.add("Successfully navigated to reports page.");
					app_logs.info("Successfully navigated to reports page.");
					report.navigateToNetSalesReport(driver, test_steps);
					test_steps.add("Navigated to Net Sales reports page");					
					app_logs.info("Navigated to Net Sales reports page");	
					report.validateNetSalesReportInMainHeader(driver, test_steps);
					if(sortReportBy.equalsIgnoreCase("RevPAR")|| sortReportBy.equalsIgnoreCase("Total Revenue")) {						
						verifyDynamicDateRange(dateOption,  startDate , checkInDate, groupNetSalesBy,sortReportBy);
					}else {
					verifyDynamicDateRange(dateOption,  checkInDate, "", groupNetSalesBy,sortReportBy);
					}
				}catch (Exception e) {
					Utility.catchException(driver, e, "Navigating to Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Navigating to Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				}
				try {
					if (report.runReportToasterMessageDisplays(driver)){
						test_steps.add("========= Report Data not Available, Create New Reservation=========");
						app_logs.info("========= Report Data not Available, Create New Reservation =========");
						driver.close();
						Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
						report.verifyLoadingReport(driver);
						nav.Reservation_Backward_3(driver);
						app_logs.info("Back to reservation page");
							String yearDate=Utility.getFutureMonthAndYearForMasterCard();
						HashMap<String, String> reservationData= new HashMap<String, String>();
						reservationData=reservationPage.createReservation(driver, test_steps, "From Reservations page", checkInDate, checkOutDate, adults,
								children, rateplan, "", roomClass, "", salutation, guestFirstName, guestLastName, 
								"", "", "", "", "", "", "", "", "", "",false, marketSegment, referral, paymentType, cardNumber, nameOnCard, yearDate,
								0, "",false, "","", "","", "","", "", "", "","", "", "","", "",false, "");
						confirmationNo=reservationData.get("ReservationNumber");
						nav.ReportsV2(driver);
						test_steps.add("Successfully navigated to reports page.");
						app_logs.info("Successfully navigated to reports page.");
						report.navigateToNetSalesReport(driver, test_steps);
						test_steps.add("Navigated to Net Sales reports page");					
						app_logs.info("Navigated to Net Sales reports page");	
						if(sortReportBy.equalsIgnoreCase("RevPAR")||sortReportBy.equalsIgnoreCase("Total Revenue")) {
							verifyDynamicDateRange(dateOption,  startDate , checkInDate,groupNetSalesBy,sortReportBy);
						}else {
						verifyDynamicDateRange(dateOption,  checkInDate, "", groupNetSalesBy,sortReportBy);
						}
						}
				}catch (Exception e) {
					Utility.catchException(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				} catch (Error e) {
					Utility.catchError(driver, e, "Net Sales Report", "ReportsV2", "ReportsV2", testName, test_description,
							test_category, test_steps);
				}								
				try {
					test_steps.add("========= Verify  Report Generated=========");
					app_logs.info("========= Verify  Report Generated=========");
					Utility.switchTab(driver, 1);
					report.validateNetSalesReportInMainHeader(driver, test_steps);					
					List<String> reportData= new ArrayList<String>();
					List<String> beforeReverse= new ArrayList<String>();
				   if(groupNetSalesBy.equalsIgnoreCase("Average Stay")) {
					   Wait.wait5Second();
					   reportData=report.getNetSalesReportViewWise(driver, "Detailed View", "Net Sales Report", 9);
					   for(String str:reportData) {
						   beforeReverse.add(str);
					   }
					   Collections.sort(reportData, Collections.reverseOrder());
					   for(int i=0;i< reportData.size();i++){
							Utility.verifyEquals(beforeReverse.get(i), reportData.get(i), test_steps);
						}
					}else if(groupNetSalesBy.equalsIgnoreCase("Booking Day")) {
						 reportData=report.getNetSalesReportViewWise(driver, "Summary View", "Net Sales Report", 1);
						 app_logs.info(reportData);						
						 String startDate=reservationDate.get(reservationDate.size()-1);
						 startDate= Utility.parseDate(startDate, "MMM dd, yyyy", "MMM d, yyyy");
						 app_logs.info(startDate);	
						 String enddate=reservationDate.get(0);
						 enddate=Utility.parseDate(enddate, "MMM dd, yyyy", "MMM d, yyyy");
						 String expected= ""+startDate+" - "+enddate+"";
						 app_logs.info(expected);	
						// Utility.verifyEquals(expected, reportData.get(0), test_steps);
						 Utility.verifyEquals(reportData.get(0), expected, test_steps);
					}else if(sortReportBy.equalsIgnoreCase("RevPAR") || sortReportBy.equalsIgnoreCase("Total Revenue")) {
						     Wait.wait5Second();
						    if(sortReportBy.equalsIgnoreCase("RevPAR")){
							reportData=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 11);
							}else if(sortReportBy.equalsIgnoreCase("Total Revenue")) {
								reportData=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 7);
							}
							 app_logs.info(reportData);	
							 List<Double> al = new ArrayList<Double>();						    
							 for(int i=0;i<reportData.size()-1;i++) {
								 String value=null;
								 if(reportData.get(i).contains(",")) {
									 value=reportData.get(i).replace("$", "").replace(",", "").trim(); 
								 }else  {
									 value=reportData.get(i).replace("$", "").trim();
								 }
								 double str = Double.parseDouble(String.valueOf(value));
								   al.add(str);
							   }					 
							 double[] array = new  double[al.size()];
							 for(int i=0;i<al.size();i++) {
								 array[i]=al.get(i);
							 }				
							 verifyDecending(array);
						}
				   
				   	driver.close();
					Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
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
		return Utility.getData("DynamicDateRangeNetSales", reportV2excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}

}
