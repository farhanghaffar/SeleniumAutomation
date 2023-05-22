package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.PaymentInfo;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class FolioBalanceReportForSplitReservation extends TestCore{

	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;
	ReportsV2 report = new ReportsV2();

	Navigation nav = new Navigation();
	FolioNew folioNew=new FolioNew();
	ReservationV2 reservationV2Page = new ReservationV2();
	GuestFolio guestFolio = new GuestFolio();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	//GuestFolio guestfolio=new GuestFolio();
	ReservationSearch reservationSearch = new ReservationSearch();
	ArrayList<String> test_steps = new ArrayList<>();
	OR_ReservationV2 or_res=new OR_ReservationV2();
	String sourceOfRes="From Reservations page", reservationNumber = null, guestFirstName = null, guestLastName,guestFirstNameCopied = null, guestLastNameCopied		 , reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), roomCharge , guestFullName,guestFullName2,guestFullNameCopied,reservation_Status;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();;
	Account account = new Account();	
	HashMap<String, String> reservationDetails = new HashMap<>();
	HashMap<String, String>beforeRecivableBalance=new HashMap<>();
	HashMap<String, String>afterRecivableBalance=new HashMap<>();
	HashMap<String, String>beforePayableBalance=new HashMap<>();
	HashMap<String, String>afterPayableBalance=new HashMap<>();
	HashMap<String, String>beforeNetTotal=new HashMap<>();
	HashMap<String, String>afterNetTotal=new HashMap<>();	
	String accountSubtotal=null;
	 double folioBalance=0.00;
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginAutoReportsV2(driver, test_steps);
		
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void folioBalanceReportForSplitReservation(String TestCaseID, String Scenario, String effectiveDate, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber, String adults, String children,
			String phoneNumber,String email,String address1,String address2,String address3,String city,String country,String state,
			String postalCode,String marketSegment,String referral,String sortReservationsBy,
			String propertyName,String reservationType,	String includeBalances,String displayAccountName,
			String includePendingFolioItem,String includeAuthorization,String status,String accountName,String accountType) throws Throwable {

		if(Utility.getResultForCase(driver, TestCaseID))
	     {
		test_name = Scenario;
		test_description = "Validate foliobalance  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - foliobalance Report";
		String testName = test_name;		
		String roomNumber=null;			
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		
		Utility.initializeTestCase(TestCaseID, Utility.testId, Utility.statusCode, Utility.comments, TestCaseID);
		checkInDate=Utility.getCurrentDate("dd/MM/yyyy");
		checkOutDate=Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
		app_logs.info(checkInDate);
		app_logs.info(checkOutDate);
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
		
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			
			int noOfRooms = 1;
			if (Utility.validateString(numberOfRooms)) {
				if (!(numberOfRooms.split("\\|").length > 1)) {
					noOfRooms = Integer.parseInt(numberOfRooms);
					}else {
						
						String[] rn = numberOfRooms.split("\\|");			
			        				noOfRooms = 0;
			        				for (int i = 0; i < rn.length; i++) {
			        					noOfRooms = noOfRooms + Integer.parseInt(rn[i]);
			        				}
			        			}
				}else {
			        			if (roomClass.split("\\|").length > 1) {
			        				noOfRooms = roomClass.split("\\|").length;
			        			}else {
			        				noOfRooms = 1;
			        				numberOfRooms = "1";
			        				}	      			
			        		}			
	
			checkInDates.clear();
			checkOutDates.clear();
			int j=0;
			for (int i = 0; i < noOfRooms; i++) {			
				
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1+i), "MM/dd/yyyy", "dd/MM/yyyy"));
				
				if(true && j<i)
						{
					checkInDates.add(checkOutDates.get(j));					
					}else if (j==i)
					{checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					}
					j=i;
					checkInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
					checkOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
									
					}
							
					for (int i = 2; i <= roomClass.split("\\|").length; i++) {
						guestFirstName = guestFirstName+"|"+Utility.generateRandomString();
						guestLastName = guestLastName+"|"+Utility.generateRandomString();
						if (!(adults.split("\\|").length > 1)) {
								adults = adults + "|2";
								children = children + "|1";
							}
						}
					
					nav.ReportsV2(driver);
					test_steps.add("Click on Reports");		
					report.clickOnNavigateFolioBalances(driver,test_steps);
					report.clearAllButtonClick(driver,test_steps);
					report.clickInHouseCheckBox(driver, test_steps);
					report.selectSortReportOption(driver, test_steps, sortReservationsBy);
					test_steps.add("Click on Folio Balances Report");
					report.clickrunReportButton(driver, test_steps);
					Wait.wait10Second();
					beforeRecivableBalance =report.getRecivableBalance(driver, test_steps,reservationType);
					beforePayableBalance=report.getPayableBalances(driver, test_steps,reservationType);
					beforeNetTotal=report.netTotal(driver, test_steps);					
					allTabs = new ArrayList<>(driver.getWindowHandles());			
					reportsTab = allTabs.get(1);
					applicationTab = allTabs.get(0);	
					Utility.switchTab(driver, applicationTab);
					app_logs.info("==========Create Reservation  "+java.time.LocalTime.now()+"==========");	
					nav.Reservation_Backward_3(driver);					
					test_steps.add("==========Create Reservation  "+java.time.LocalTime.now()+"==========");
					reservationDetails=reservationV2Page.createReservation_WithDetails(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
					children, ratePlan, null, roomClass, "", "Mr.|Mr.", guestFirstName, guestLastName, phoneNumber,
					phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
					referral, paymentMethod, cardNumber, guestFullName, cardExpDate, 0, "", isQuote, 
					"", "", "", "", "", "", "", "", "", "", "", "", "", "test", false, "1235","", true,true);					
					reservationV2Page.checkIn_MrbPrimary(driver, test_steps);
					reservationV2Page.switchFolioTab(driver, test_steps);
					folioBalance=folioNew.getFolioBalanceOfFirstFolio(driver, test_steps);					
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			Wait.wait60Second();
			Wait.wait60Second();
		Utility.switchTab(driver, reportsTab);				
		report.editButtonClick(driver, test_steps);
		report.clickrunReportButton(driver, test_steps);
		Wait.wait10Second();		
		afterRecivableBalance=report.getRecivableBalance(driver, test_steps,reservationType);
		afterPayableBalance=report.getPayableBalances(driver, test_steps,reservationType);
		afterNetTotal=report.netTotal(driver, test_steps);	
		app_logs.info("==========Verify Testcase id:849108==========");	
		test_steps.add("==========Verify Testcase id:849108==========");		
		app_logs.info("==========Verify Folio balance report summary view==========");	
		test_steps.add("==========Verify Folio balance report summary view==========");
		report.validateFoliobalanceReportSummaryView(driver, beforeRecivableBalance, afterRecivableBalance, beforePayableBalance, afterPayableBalance,
				reservationType,  String.format("%.2f",folioBalance),beforeNetTotal,afterNetTotal,true,true, test_steps);	
       Wait.wait10Second();		
   	  app_logs.info("==========Verify Folio balance report detail view==========");	
	  test_steps.add("==========Verify Folio balance report detail view==========");
		 accountSubtotal=report.getFolioBalanceDeatilViewAccountSubtotal(driver, test_steps);
		report.VerifyDetailViewFolioBalanceReportFor(driver, test_steps, reservationNumber, false, false,  roomNumber, roomClass,checkInDate, checkOutDate,
				   guestFullName,"", String.format("%.2f",folioBalance), "",  accountSubtotal);	

		app_logs.info("==========Verify Folio balance report header==========");	
		test_steps.add("==========Verify Folio balance report header==========");
		report.verifyFolioBalanceReportSubHeader(driver,test_steps,propertyName,effectiveDate,reservationType,includeBalances,
				checkInDate,sortReservationsBy, displayAccountName,includePendingFolioItem,includeAuthorization);
		
		report.reservationTableSortedByGuestName(driver, test_steps);
		app_logs.info("Verified sort order");
		
        } catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		try {
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify reservation card Report");
			}					
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}

	@DataProvider
	public Object[][] getData() {
		
		return Utility.getData("FolioBal_Split_Res", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode, Utility.comments, TestCore.TestRail_AssignToID);

	}





}
