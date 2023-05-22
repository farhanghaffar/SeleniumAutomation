package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.PaymentInfo;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.GuestFolio;
import com.innroad.inncenter.pageobjects.Navigation;
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

public class ReservationCardReportWithUnassignedReservation extends TestCore  {


	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity ;

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	ReservationV2 reservationV2Page = new ReservationV2();
	GuestFolio guestFolio = new GuestFolio();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	ReservationSearch reservationSearch = new ReservationSearch();
	ArrayList<String> test_steps = new ArrayList<>();
	OR_ReservationV2 or_res=new OR_ReservationV2();
	FolioNew folioNew=new FolioNew();

	String sourceOfRes="From Reservations page", reservationNumber = null, guestFirstName = null, guestLastName	 , reportsTab, applicationTab,
	cardExpDate = Utility.getFutureMonthAndYearForMasterCard(driver,test_steps), roomCharge , guestFullName,guestFullName2,reservation_Status;
	
	HashMap<String, String> ledgerAccounts = new HashMap<>();
	HashMap<String, String> ledgerAmounts = new HashMap<>();
	HashMap<String, String> folioItemValues = new HashMap<>();
	HashMap<String, Double> folioBalances = new HashMap<>();
	HashMap<String, String> folioPaymentItemValues= new HashMap<>();
	HashMap<String, String> reservationDetails = new HashMap<>();
	String stayInfo = null;
	
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> allTabs = new ArrayList<>();


	HashMap<String, Double> expChanges2 = new HashMap<>();
	HashMap<String, String> paymentData = new HashMap<>();	
	HashMap<String, String> paymentTransactionTypeAndAmount = new HashMap<>();

	
	HashMap<String, ArrayList<String>> beforeCreditCardTransactionTypeTotal2=new HashMap<>();
	HashMap<String, ArrayList<String>> afterCreditCardTransactionTypeTotal2=new HashMap<>();

	HashMap<String, String> itemDescription = new HashMap<>();
	HashMap<String, String> reservationData = new HashMap<>();	

	/*
	 * ArrayList<String> statusCode=new ArrayList<String>(); ArrayList<String>
	 * caseId=new ArrayList<String>(); ArrayList<String> comments=new
	 * ArrayList<String>();
	 */
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
	public void reservationCardReportOrderbyArrivalDate(String TestCaseID, String Scenario, String dateRange, 
			String resType, String numberOfRooms, String checkInDate, String checkOutDate,			
			String ratePlan, String roomClass, String isPayment, String paymentMethod, String cardNumber, String adults, String children
			,String phoneNumber,String email,String address1,String address2,String address3,String city,String country,String state,
			String postalCode,String marketSegment,String referral,String includeReservationTypes,String sortReservationsBy,
			String onlyIncludeReservationsWith,String onlyIncludeReservationsWithBooked ,String propertyName) throws Throwable {
       
		if(Utility.getResultForCase(driver, TestCaseID))
		{
			
		
	
		test_name = Scenario;
		test_description = "Validate ReservationCard  Report<br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - ReservationCard Report";
		String testName = test_name;
		
		String roomNumber=null;
		

		String source=null;
		String createdDate=null;
		String createdBy=null;
		String cardLastFourNumber=null;
		String stayon_Date_Range=null;
		String roomCharges2=null;
		Double incidental=null;
		
		//tax
		String randomString = Utility.generateRandomStringWithoutNumbers();
		String taxName = "Tax_"+randomString;
		String feeName = "Fee_"+randomString;
		TaxesAndFee taxesAndFee=new TaxesAndFee();
		
		String taxType="percent";
		String taxValue= Utility.GenerateRandomNumber(10, 20);
		String Fees="Fee Adjustment";
		String feeType="percent";
		String feecategoryValue= Utility.GenerateRandomNumber(10, 20);
		ArrayList<String> tax_Amount=new ArrayList<>();
		String feeValue=null;
		HashMap<String, String> calculateTax=new HashMap();
		String paid_Amount="0.00";
		String includePayment="Capture";
		String accountName="";
		String accountType="";
		ArrayList<String>updatedTaxName=new ArrayList<>();
		ArrayList<String> categoryTaxs=new ArrayList<>();
		
		guestFirstName = "Auto";
		guestLastName = "User"+Utility.generateRandomStringWithoutNumbers();
		guestFullName = guestFirstName+" "+guestLastName;
		guestFullName2=guestLastName+" "+guestFirstName;
		
		//GuestFolio guestFolio = new GuestFolio();

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		/*
		 * comments.add("Test got failed"); if(!Utility.validateString(TestCaseID)) {
		 * caseId.add(TestCaseID); statusCode.add("4"); }else { String[]
		 * testcase=TestCaseID.split("\\|"); for(int i=0;i<testcase.length;i++) {
		 * caseId.add(testcase[i]); statusCode.add("4"); } }
		 */

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
			test_steps.add("Create Tax and Fee");
			app_logs.info("Create Tax and Fee");
			nav.Setup(driver, test_steps);
    		nav.clickTaxesAndFees(driver);
			app_logs.info("Create Tax and Fee");
		
			
			taxesAndFee.deleteAllTaxesAndFee(driver, test_steps);
			taxesAndFee.createFee(driver, test_steps, feeName,feeName, Fees, feeName,feeType, feecategoryValue, false, null, null, null);
			
			taxesAndFee.createTaxes(driver, taxName, taxName, "Test Tax", "Room Tax",taxType,taxValue, "No", 
    				"Room Charge", false, "", "", "", test_steps);
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}
		
		


		try {

	
			
			app_logs.info("==========Create Reservation  "+java.time.LocalTime.now()+"==========");	
			nav.Reservation_Backward_3(driver);
			test_steps.add("==========Create Reservation  "+java.time.LocalTime.now()+"==========");
			reservationDetails=reservationV2Page.createReservation_WithDetails(driver, test_steps, sourceOfRes, checkInDate, checkOutDate, adults, 
					children, ratePlan, null, roomClass, "", "Mr.", guestFirstName, guestLastName, phoneNumber,
					phoneNumber, email, address1, address2, address3, city, country, state, postalCode, false, marketSegment, 
					referral, paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, "", isQuote, 
					"", "", "", "", "", "", "", "", 
					"", "", "", "", "", "", false, "1235","Unassigned",false,false);
			
			reservationNumber=reservationDetails.get("ReservationNumber");
			reservation_Status=reservationDetails.get("ReservationStatus");			
			roomNumber=reservationDetails.get("RoomNumber");
			cardLastFourNumber=cardNumber.substring(12, 16);
			app_logs.info("roomNumber: "+roomNumber);
			app_logs.info("reservationNumber: "+reservationNumber);
			app_logs.info("reservation_Status: "+reservation_Status);			
			app_logs.info("cardLastFourNumber: "+cardLastFourNumber);
			
	        app_logs.info("==========Created Reservation Successfully  "+java.time.LocalTime.now()+"==========");	
			
			test_steps.add("==========Created Reservation Successfully "+java.time.LocalTime.now()+"==========");
			
			reservationV2Page.switchFolioTab(driver, test_steps);
			//String categoryIncidental="Bar";
			//String incidnetalAmount="10.00";
			//folio.AddfolioLineItem(driver,categoryIncidental,incidnetalAmount, test_steps);
			
			 roomCharges2=Utility.RemoveDollarandSpaces(driver,folio.getFolioLineItemAmountWithCategory(driver, "Room Charge", test_steps));
			 
			 app_logs.info("==========Created Reservation Successfully ==========");	
				
			test_steps.add("==========Add folio line item ==========");				
				 
			 
			 folioNew.addFolioLineItem(driver, test_steps, "Bar", taxValue);
			 
			incidental=Double.parseDouble(taxValue);
			 app_logs.info("==========Created Reservation Successfully  "+java.time.LocalTime.now()+"==========");	
				
			test_steps.add("==========Created Reservation Successfully "+java.time.LocalTime.now()+"==========");
				
				
			
			tax_Amount.add(taxValue);
			updatedTaxName.add(taxName);
			categoryTaxs.add(taxType);
			
			 calculateTax=reservationV2Page.calculateTaxes(roomCharges2, updatedTaxName, tax_Amount, categoryTaxs);
			 double expectedTax=Double.parseDouble(calculateTax.get(taxName));
			 feeValue=reservationV2Page.calculateAdjustmentFee(driver,feeType , roomCharges2, feecategoryValue);
			 
			 double expectedFee=Double.parseDouble(feeValue);
			 
         double total=Double.parseDouble(roomCharges2)+expectedTax+expectedFee+incidental;
         app_logs.info("total"+total);
         app_logs.info("feeValue"+expectedFee);
         app_logs.info("calculateTax"+expectedTax);
         app_logs.info("incidental"+incidental);
         
         
         Wait.wait3Second();
     	test_steps.add("**************<b> TAKING PAYMENT FROM GUEST FOLIO </b>**************");
      	paymentData = guestFolio.takePaymentFromGuestFolio(driver, test_steps, includePayment);
 		paid_Amount = paymentData.get("Amount");
 		
 		Wait.wait3Second();
     	app_logs.info("==========Get trip summary details==========");
     	reservationV2Page.switchDetailTab(driver, test_steps);
     	
     	
		
		ledgerAmounts.put("Room Charges", roomCharges2);
		ledgerAmounts.put("Incidentals",String.format("%.2f",incidental));
		ledgerAmounts.put("Taxes", String.format("%.2f",expectedTax));
		ledgerAmounts.put("Fees", String.format("%.2f",expectedFee));
		ledgerAmounts.put("Total Charges", String.format("%.2f",total));
		ledgerAmounts.put("Payments",  paid_Amount);			
		double balance=total-Double.parseDouble(paid_Amount);
		ledgerAmounts.put("Balance", String.format("%.2f",balance));	
		app_logs.info("TripSummary details: "+ledgerAmounts);
					
			app_logs.info("MarketingInfo details");
			MarketingInfo marketungInfo = reservationV2Page.getMarketingInfoDetail(driver);
			source=marketungInfo.getMI_MARKET_SOURCE();
			app_logs.info("source: "+source);
			
			
			app_logs.info("Payment Details :");
			PaymentInfo paymentInfo = reservationV2Page.getPaymentInfo(driver);
			cardExpDate=paymentInfo.getPI_EXPIRY_DATE();
			app_logs.info("cardExpDate: "+cardExpDate);

			createdDate=Utility.getDifferentDateFormat(checkInDate, "MMM dd, yyyy");
			app_logs.info("createdDate: "+createdDate);
			
			Wait.wait10Second();
			reservationV2Page.switchHistoryTab(driver, test_steps);
			createdBy=driver.findElement(By.xpath(or_res.User_FIRST_NAME)).getText()+" "+driver.findElement(By.xpath(or_res.User_LAST_NAME)).getText();
			app_logs.info("createdBy: "+createdBy);
			
			app_logs.info("========== CloseOpen reservations==========");	
			reservationV2Page.closeAllOpenedReservations(driver);
			
			app_logs.info("========== Action started at "+java.time.LocalTime.now()+"==========");
			Wait.wait60Second();
			Wait.wait60Second();			
			Wait.wait60Second();
		
			
			nav.ReportsV2(driver, test_steps);
			report.navigateToReservatioCardReport(driver, test_steps);
			report.selectDateRange(driver, checkInDate, checkOutDate, dateRange, test_steps);
			stayon_Date_Range=report.selectDateRange_startAndEndDate(driver,test_steps);
			app_logs.info("stayon_Date_Range:"+stayon_Date_Range);
			report.sortReportBy(driver, sortReservationsBy, test_steps);
			report.includeReservationTypes(driver, includeReservationTypes, test_steps);
			report.onlyIncludeReservationsWith(driver, onlyIncludeReservationsWith, test_steps);			
			report.clickOnRunReportUntill(driver);
			
			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);	
			
			app_logs.info("==========Verify Reservationcard report for inhouse reservation==========");	
			test_steps.add("==========Verify Reservationcard report for inhouse reservation==========");
			
			
			
			app_logs.info("==========Verify sorting order of reservation==========");	
			report.verifyReservationCardReport_Reservation_SortingOrder(driver, test_steps,sortReservationsBy);
			
			app_logs.info("========== Verify the header of the report");	
			report.verifyHeaderOfReservationCardReport(driver, test_steps,dateRange, includeReservationTypes, sortReservationsBy, onlyIncludeReservationsWith, onlyIncludeReservationsWithBooked, stayon_Date_Range );
			
			app_logs.info("========== Verify advanced selected options==========");	
			report.verifyReservationCardReportAdvanceOption(driver, test_steps,dateRange, includeReservationTypes, sortReservationsBy, onlyIncludeReservationsWith, onlyIncludeReservationsWithBooked,propertyName, stayon_Date_Range);
			
			app_logs.info("==========Verify reservation card details==========");	
			report.verifyCardViewDetails( driver, reservationNumber,reservation_Status,guestFullName2 ,adults,children, roomClass, roomNumber, ratePlan,
					ledgerAmounts, marketSegment, phoneNumber, address1,address2,address3,city,postalCode,state,country,source,createdDate,createdBy,
				 referral, "MC",cardLastFourNumber,cardExpDate, test_steps,checkInDate,checkOutDate,accountName,accountType,"",false);
		
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Navigate to report", "Report", "Report", testName, test_description,
					test_category, test_steps);
		}


		try {
			
			
          Utility.switchTab(driver, applicationTab);
          nav.ReservationV2_Backward(driver);
          
          reservationV2Page.basicSearch_WithReservationNo(driver, reservationNumber, true);
          Wait.wait5Second();
		  reservationV2Page.assignRoomNumberFromStayInfo(driver, roomClass, test_steps);
		  StayInfo stayInfo = reservationV2Page.getStayInfoDetail(driver);
		  roomNumber=stayInfo.getSI_ROOM_NUMBER();
		  app_logs.info("roomNumber: "+roomNumber);
		  
		  reservationV2Page.remove_PaymentMethod(driver, test_steps);
		  Wait.wait5Second();

			
			Utility.switchTab(driver, reportsTab);
			app_logs.info("========== Action started at "+java.time.LocalTime.now()+"==========");
			Wait.wait60Second();
			Wait.wait60Second();
			Wait.wait60Second();
			
			report.editButtonClick(driver, test_steps);
			report.clickOnReturnToDefaultButton(driver);
			includeReservationTypes="All Reservations";
			onlyIncludeReservationsWith="No Payment Method";
			sortReservationsBy="Reservation Number";
			report.selectDateRange(driver, checkInDate, checkOutDate, dateRange, test_steps);
			stayon_Date_Range=report.selectDateRange_startAndEndDate(driver,test_steps);
			report.includeReservationTypes(driver, includeReservationTypes, test_steps);
			report.onlyIncludeReservationsWith(driver,onlyIncludeReservationsWith, test_steps);
			report.sortReportBy(driver, sortReservationsBy, test_steps);
			Wait.wait10Second();
			report.clickOnRunReportUntill(driver);
			

			
			app_logs.info("==========Verify Reservationcard report for inhouse reservation==========");	
			test_steps.add("==========Verify Reservationcard report for inhouse reservation==========");
			
			
			
			app_logs.info("==========Verify sorting order of reservation==========");	
			report.verifyReservationCardReport_Reservation_SortingOrder(driver, test_steps,sortReservationsBy);
			
			app_logs.info("========== Verify the header of the report");	
			report.verifyHeaderOfReservationCardReport(driver, test_steps,dateRange, includeReservationTypes, sortReservationsBy, onlyIncludeReservationsWith, onlyIncludeReservationsWithBooked,stayon_Date_Range );
			
			app_logs.info("========== Verify advanced selected options==========");	
			report.verifyReservationCardReportAdvanceOption(driver, test_steps,dateRange, includeReservationTypes, sortReservationsBy, onlyIncludeReservationsWith, onlyIncludeReservationsWithBooked,propertyName,stayon_Date_Range);
			
			app_logs.info("==========Verify reservation card details==========");	
			report.verifyCardViewDetails( driver, reservationNumber,reservation_Status,guestFullName2 ,adults,children, roomClass, roomNumber, ratePlan,
					ledgerAmounts, marketSegment, phoneNumber, address1,address2,address3,city,postalCode,state,country,source,createdDate,createdBy,
				 referral, "-","-","-", test_steps,checkInDate,checkOutDate,accountName,accountType,"",false);
		
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify  Reservation cardreport ");
			}	

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(Scenario, test_description, test_category, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReservationCardReportUnassigned", excelReservationV2);
	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode, Utility.comments, TestCore.TestRail_AssignToID);

	}
	

}
