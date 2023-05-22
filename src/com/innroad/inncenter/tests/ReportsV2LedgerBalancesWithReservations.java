package com.innroad.inncenter.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.If;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_NewRoomClass;
import com.innroad.inncenter.webelements.Elements_Reports;

public class ReportsV2LedgerBalancesWithReservations extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	String reservationNumber = null, guestName = null, roomNumber = null, date = null;
	String guest1 = null, guest2 = null;
	String propertyName = null;

	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> getRoomClasses = new ArrayList<>();
	HashMap<String, String> itemDescription = new HashMap<>();

	ArrayList<String> availableTypes = new ArrayList<>();
	HashMap<String, String> beforeLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> beforeDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> beforeDetailsOfAllLedgerCategories = new HashMap<>();

	HashMap<String, String> afterLedgerCategoryDetails = new HashMap<>();
	HashMap<String, String> afterDetailsOfGivenCategory = new HashMap<>();
	HashMap<String, String> afterDetailsOfAllLedgerCategories = new HashMap<>();
	
	ArrayList<String> guestNames = new ArrayList<>();
	ArrayList<String> reservationNumbers = new ArrayList<>();
	ArrayList<String> arrivalDates = new ArrayList<>();
	ArrayList<String> dates = new ArrayList<>();
	ArrayList<String> itemDescriptions = new ArrayList<>();
	ArrayList<String> amountList = new ArrayList<>();
	

	ReportsV2 report = new ReportsV2();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
	CPReservationPage reservationPage = new CPReservationPage();

	Folio folio = new Folio();
	Groups group = new Groups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void ValidateLedgerBalancesReport(String Scenario, String DateRange, String StartDate, String EndDate,

			String Incidentals, String RoomCharges, String Taxes,
			String Transfers, String PaymentMethod, String Fees,
			String UnitExpenses, String UnitRevenues, String TravelAgentCommission, String DistributionMethod,
			String GiftCertificate, String GiftCertificateRedeemed,

			String accountType,

			String isMRB, String numberOfRoomsMRB, String CheckInDate, String CheckOutDate, String Adults, String Children, String Rateplan,
			String PromoCode, String IsSplitRes, String RoomClass, String email, String country, String state,
			String IsGuesProfile, String isPayment, String PaymentType, String CardNumber, String NameOnCard,
			String CardExpDate, String TravelAgent,
			String marketSegment, String referral,

			String accountTypeOptions, String itemStatuOptions, String IncludeDataFromUsers,String IncludeDataFromShiftTimeStartHours,
			String IncludeDataFromShiftTimeStartMinutes, String IncludeDataFromShiftTimeStartAmPm,
			String IncludeDataFromShiftTimeEndHours, String IncludeDataFromShiftTimeEndMinutes,
			String IncludeDataFromShiftTimeEndAmPm, String TaxExemptLedgerItemsOption, String marketSegmentOption,
			String reservationStatusOptions, String referralsOption, String dayTaxExempt) throws InterruptedException, IOException {

		test_name = Scenario;
		test_description = "Validate LedgerBalances Report<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/667714' target='_blank'>"
				+ "Click here to open TestRail: C667714</a>";
		test_category = "ReportsV2";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Double depositAmount = 0.0;
		Double paidDeposit = 0.0;
		String reservation = null;
		String status = null;
		String account = "";
		
		String AmountIncidentals = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountRoomCharges = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountTaxes = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		String AmountFees = Utility.generateRandomNumberWithGivenNumberOfDigits(2);
		
		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String salutation = "Mr.";
		String address1 = Utility.generateRandomString();
		String address2 = Utility.generateRandomString();
		String address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.GenerateRandomNumber(5);
		//String phoneNumber = Utility.GenerateRandomNumber(10);
		//String alternativePhone = Utility.GenerateRandomNumber(10);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		int numberOfRooms = 1;
		if (!numberOfRoomsMRB.isEmpty()) {
			numberOfRooms = Integer.parseInt(numberOfRoomsMRB);
		}
				
		String accountName = "Account" + Utility.generateRandomStringWithoutNumbers();

		//String TripSummaryRoomCharges = null, TripSummaryTaxes = null, TripSummaryIncidentals = null, ripSummaryTripTotal = null, TripSummaryPaid = null, TripSummaryBalance = null;
		double TripSummaryRoomCharges = 0, TripSummaryTaxes = 0, TripSummaryIncidentals = 0, TripSummaryTripTotal = 0, TripSummaryPaid = 0, TripSummaryBalance = 0;
		double roomChargeAmount=0.00;

		//String FolioRoomCharges = null, FolioTaxes = null, FolioFees = null, FolioIncidentals = null, FolioTripTotal = null, FolioPaid = null, FolioBalance = null;
		double FolioRoomCharges = 0, FolioTaxes = 0, FolioFees = 0, FolioIncidentals = 0, FolioTripTotal = 0, FolioPaid = 0, FolioBalance = 0, TripRoomCharge=0, TripTax=0;;
		String resNumberPayment = null;
		String accountNumber = null;
		ArrayList<String> unitOwnerItems = new ArrayList<>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			// Get CheckIn, CheckOut Date
			if (CheckInDate.equalsIgnoreCase("NA")) {
				
				if (!isMRB.equalsIgnoreCase("Yes")) {
					CheckInDate = Utility.getCurrentDate("dd/MM/yyyy");
					CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
				}else {
					
					// Get CheckIN and Checkout Date
					if (CheckInDate.equalsIgnoreCase("NA")) {
						checkInDates.clear();
						checkOutDates.clear();
						
						for (int i = 1; i <= numberOfRooms; i++) {
							
							app_logs.info("Loop"+i);
							checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
							app_logs.info(checkInDates);
							app_logs.info(checkOutDates);
							
						}
					} else {
						checkInDates = Utility.splitInputData(CheckInDate);
						checkOutDates = Utility.splitInputData(CheckOutDate);
					}
					//CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
					//CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
					
					app_logs.info(checkInDates);
					app_logs.info(checkOutDates);
					
					CheckInDate = checkInDates.get(0);
					CheckOutDate = checkOutDates.get(0);
					
					for (int i = 1; i < checkInDates.size(); i++) {
						CheckInDate = CheckInDate + "|"+ checkInDates.get(i);
						CheckOutDate = CheckOutDate + "|"+ checkOutDates.get(i);
					}
									
					app_logs.info(CheckInDate);
					app_logs.info(CheckOutDate);
					
				}
				
				
				
			}
			//TaskDueon = CheckOutDate;

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			//date = Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MM/dd/yy");
			//app_logs.info(date);
				

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Get CheckIn Date and Checkout Date", testName,
						"ChekIn and CheckOut date", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// login
		Login login = new Login();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				// login_CP(driver);
				loginReportsV2(driver);
				// login.login(driver, envURL, "bmi", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);

				actions.sendKeys(Keys.ENTER);
				loginReportsV2(driver);
				// login.login(driver, envURL, "bmi", "autouser", "Auto@123");
			}
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		}
		
		
		
		
		
		
/*		try {
			
			//Room Class creation if not 
            
            ArrayList<String> RoomClassNames = new ArrayList<>();
            Elements_NewRoomClass element = new Elements_NewRoomClass(driver);  
            nav.Setup(driver);
            nav.RoomClass(driver);
           
            char ch = RoomClass.charAt(0);
            String str = "" + ch;
            str = str.toUpperCase();
            driver.findElement(By.xpath("//*[text()='"+str+"']")).click();
            element.downarrow2.click();
            element.select100ItemsPerPage.click();
            
            List<WebElement> l = driver.findElements(By.xpath("//tr/td[3]/a"));
           
            for(WebElement e:l) {
                RoomClassNames.add(e.getText());
            }
            app_logs.info("Room Classes: "+RoomClassNames);
            if(RoomClassNames.contains(RoomClass)) {
                System.out.println("Room Class exists");
            }else {
                Utility.clickThroughAction(driver, element.newRoomClass);
                element.newRoomClassName.sendKeys(RoomClass);
                element.newRoomClassNameAbb.sendKeys("ABR");
                element.maxAdults.sendKeys("4");
                element.maxPersons.sendKeys("8");
                element.rooms.click();                   
                element.roomQuantity.sendKeys("150");
                Utility.clickThroughAction(driver, element.rightIcon);
                element.firstRoom.sendKeys("1");   
                Utility.clickThroughAction(driver, element.assignNumbers);
                Utility.clickThroughAction(driver, element.Done);
                Utility.clickThroughAction(driver, element.Publish);          
            }

            
            //RatePlan Creation if not exist
            
            RatesGrid rateGrid = new RatesGrid();
            NightlyRate nightlyRate = new NightlyRate();
            nav.Inventory(driver, test_steps);
            nav.ratesGrid(driver);
            boolean israteplanExist = rateGrid.isRatePlanExist(driver, Rateplan, test_steps);
            System.out.println("israteplanExist="+israteplanExist);



            if (!israteplanExist) {
                //Creating the Rate Plan
                //CREATE NEW NIGHTLY RATE PLAN
                rateGrid.clickRateGridAddRatePlan(driver);
                rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");        
                //ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION
                Rateplan = Rateplan + Utility.generateRandomString();
                String FolioDisplayName = Rateplan + Utility.generateRandomString()+Utility.generateRandomString();
                nightlyRate.enterRatePlanName(driver, Rateplan, test_steps);
                nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
                nightlyRate.enterRatePlanDescription(driver, Rateplan+"Description", test_steps);
                nightlyRate.clickNextButton(driver, test_steps);
                //SELECT DISTRIBUTED CHANNELS
                nightlyRate.selectChannels(driver, "inncenter", true, test_steps);
                nightlyRate.clickNextButton(driver, test_steps);
                //SELECT ROOM CLASSES
                nightlyRate.selectRoomClassesWithDeli(driver, Rateplan, true, test_steps);
                nightlyRate.clickNextButton(driver, test_steps);
                //nightlyRate.selectRestrictionsWithDeli(driver, false, "Promo code",
                //        false, "2", false, "2", false, "2",false, "2", "PromoCode", test_steps);
                nightlyRate.clickNextButton(driver, test_steps);
                //nightlyRate.selectPolicyWithDeli(driver, "Check-in type", "Check-in name", false, test_steps);
                nightlyRate.clickNextButton(driver, test_steps);
                //CREATE SEASON(directly clicking on create season)
                nightlyRate.clickCreateSeason(driver, test_steps);
                nightlyRate.clickCompleteChanges(driver, test_steps);
                nightlyRate.clickSaveAsActive(driver, test_steps);
                Wait.wait30Second();
                app_logs.info("=================== RATE PLAN CREATED ======================");
            }
		
			
			} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Include Data From", "Include Data From Validation", "ReportsV2", driver);
			} else {
				Assert.assertTrue(false);
			}
			
		}*/
		
		
		/*		try {
		
		//Taxes & Fees
		nav.setup(driver);
        report.navigateToTaxesAndFees(driver);
        boolean taxVal = report.checkIfTaxRowExists(driver);
        if(taxVal) {
            ArrayList<String> TaxItems = report.getTaxLedgerAccounts(driver);
            app_logs.info("Tax Items: "+TaxItems);
            String taxValue = report.getTaxAmountFromTaxLedgerAccount(driver); //20|USD or 20|percent
            app_logs.info("Tax Value: "+taxValue);
            String taxLedger = report.getTaxLedgerAccountSelectedOption(driver);
            app_logs.info("Tax Ledger: "+taxLedger);
        }
        //nav.setup(driver);
        //nav.Setup(driver);
        report.setup(driver);
        report.navigateToTaxesAndFees(driver);
        
        boolean feesval = report.checkIfFeesRowExists(driver);
        if(feesval) {
        	String feeValue = report.getFeeAmountFromFeeLedgerAccount(driver); //20|USD|per night/stay
        	app_logs.info("Fee Value: "+feeValue);
        	String feeLedger = report.getFeeLedgerAccountSelectedOption(driver); 
        	System.out.println("Fee Ledger: "+feeLedger);
        }
		
        //report.navigateToTaxesAndFees(driver);
        
		report.setup(driver);
		nav.Properties(driver);
		
		prop.SearchProperty_Click(driver, propertyName, test_steps);
		prop.PropertyOptions(driver, test_steps);
		propertyName = prop.getPropertyName(driver, test_steps);
		if (TaxExemptLedgerItemsOption.equals("Tax Exempt")) {
			prop.LongStay(driver, "Bucksport Motor Inn", dayTaxExempt, test_steps);
			
		}
				
		
		nav.Reservation_Backward(driver);
		
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Taxes & Fees details", testName, "Navigate to Taxes & fees", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get Taxes & Fees details", testName, "Navigate to Taxes & fees", driver);
			} else {

				Assert.assertTrue(false);
			}
		}*/
		
		
		
		
		try {
			
		// If payment method is Reservation - Creating a new Reservation
		if (PaymentMethod.equalsIgnoreCase("Reservation") || Transfers.equalsIgnoreCase("Reservation")) {

			reservationPage.click_NewReservation(driver, test_steps);
			reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
			reservationPage.enter_Adults(driver, test_steps, Adults);
			reservationPage.enter_Children(driver, test_steps, Children);
			// res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			reservationPage.clickOnFindRooms(driver, test_steps);
			//reservationPage.selectRoom(driver, test_steps, RoomClass, IsAssign, account);
			reservationPage.selectRoom(driver, test_steps, RoomClass, "Yes", account);
			depositAmount = reservationPage.deposit(driver, test_steps, "No", "");
			//depositAmount = reservationPage.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);

			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
					phoneNumber, alternativePhone, email, account, accountType, address1, address2, address3, city,
					country, state, postalCode, IsGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				reservationPage.enter_PaymentDetails(driver, test_steps, "MC", CardNumber, NameOnCard, CardExpDate);
			}
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
			//reservationPage.verify_NotesSections(driver, test_steps);
			//boolean falg = reservationPage.verify_TaskSections(driver, test_steps);
			//reservationPage.enter_Notes(driver, test_steps, IsAddNotes, NoteType, Subject, Description);
			// if (falg) {
			// res.enter_Task(driver, test_steps, IsTask, TaskCategory, TaskType,
			// TaskDetails, TaskRemarks, TaskDueon,
			// TaskAssignee, TaskStatus);
			// }
			reservationPage.clickBookNow(driver, test_steps);
			try {
				resNumberPayment = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				app_logs.info("Reservation Number for Payment: " + resNumberPayment);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			reservationPage.closeReservationTab(driver);

		}

		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create Reservation", testName, "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to create Reservation", testName, "Create Reservation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		
		String[] inputs = { "Incidentals", "Room Charges", "Taxes", "Transfers", "Payment Method", "Fees",
				"Unit Expenses", "Unit Revenues", "Travel Agent Commission", "Distribution Method", "Gift Certificate",
				"Gift Certificate Redeemed" };
		String[] options = { Incidentals, RoomCharges, Taxes, Transfers, PaymentMethod, Fees, UnitExpenses,
				UnitRevenues, TravelAgentCommission, DistributionMethod, GiftCertificate, GiftCertificateRedeemed };
		
		try {

			nav.ReportsV2(driver);
			report.navigateToLedgerBalancesReport(driver);
			// Utility.switchTab(driver, (new
			// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

			report.selectDateRange(driver, DateRange, test_steps);
			
			report.selectSelectInputsAll(driver, inputs, options, test_steps);

			report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
//			if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
//				report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, "Reservations");
//			}
			report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
			report.selectIncludeDataFromOptionsGiventhroughExcel(driver, test_steps, IncludeDataFromUsers,
					IncludeDataFromShiftTimeStartHours, IncludeDataFromShiftTimeStartMinutes,
					IncludeDataFromShiftTimeStartAmPm, IncludeDataFromShiftTimeEndHours,
					IncludeDataFromShiftTimeEndMinutes, IncludeDataFromShiftTimeEndAmPm);
			report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps, TaxExemptLedgerItemsOption);
			report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
			report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps, reservationStatusOptions);
			report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

			report.clickOnRunReport(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		
		Elements_Reports reportElements = new Elements_Reports(driver);
		
		if (reportElements.NoReportDataMessage.isDisplayed()) {
			
			
			
		}
		
		
		
		
		
		
		
		

		try {

			beforeLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

			beforeDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);
			
			HashMap<String,ArrayList<String>> TransactionDetails = new HashMap<>();
			
			driver.close();
			//Utility.switchTab(driver, 0);
			Utility.switchTab(driver, (new ArrayList<String>(driver.getWindowHandles()).size()) - 1);
			
			nav.Reservation_Backward_3(driver);
			app_logs.info("Back to reservation page");


		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get details after Run Report", testName, "Getting details after run Report", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to get details after Run Report", testName, "Getting details after run Report", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// New account creation
		if (accountType.equals("House Account")) {
			
			try {
				nav.Accounts(driver);
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				accountPage.ClickNewAccountbutton(driver, accountType);
				accountPage.AccountDetails(driver, accountType, accountName);
				accountNumber = Utility.GenerateRandomString15Digit();
				accountPage.ChangeAccountNumber(driver, accountNumber);

				report.AccountSave(driver, test, accountName, test_steps);

				accountPage.closeAccountTab(driver);
				nav.cpReservationBackward(driver);
				reservationPage.click_NewReservation(driver, test_steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create house account", testName, "HouseAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (accountType.equals("Gift Certificate")) {
			
			try {
				String AccountNumber = null;

				nav.Accounts(driver);
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				accountPage.ClickNewAccountbutton(driver, accountType);
				accountPage.AccountDetails(driver, accountType, accountName);
				AccountNumber = Utility.GenerateRandomString15Digit();
				accountPage.ChangeAccountNumber(driver, AccountNumber);

				report.AccountSave(driver, test, accountName, test_steps);
				accountPage.ClickFolio(driver);
				accountPage.addLineitem1(driver, "Bucksport Motor Inn", "Gift Certificate", "1000", test_steps);
				accountPage.Commit(driver);

				// report.AccountSave(driver, test, accountName, test_steps);
				// accountPage.Save(driver);
				accountPage.Save(driver, test, test_steps);

				accountPage.closeAccountTab(driver);
				nav.cpReservationBackward(driver);
				reservationPage.click_NewReservation(driver, test_steps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "Gift Certificate Account",
							driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {

				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Create Gift Certificate Account", testName, "Gift Certificate Account",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else if (accountType.equals("Travel Agent")) {
			ArrayList<String> TravelAgentItems = new ArrayList<>();
			int TravelAgetItemValue;

			nav.Accounts(driver);

			if (!TravelAgentCommission.isEmpty()) {

				accountPage.ClickTravelAgentItem(driver);

				if (accountPage.chekcTravelAgentItemAvailability(driver, test_steps)) {
					TravelAgentItems = accountPage.getAssociatedTravelAgentItems(driver, test_steps);
					TravelAgetItemValue = accountPage.getTravelAgentCommissionValue(driver, test_steps);

					app_logs.info("Travel agent items: " + TravelAgentItems);
					app_logs.info("Travel agent Value: " + TravelAgetItemValue);

				} else {
					accountPage.CreateNewTravelAgentItem(driver, "ItemName", "DisplayName", "Description", "10",
							"Category", "SelectTax");
				}
				
			}

			try {
				//nav.Accounts(driver);
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				// accountName = accountName + Utility.generateRandomString();

				//accountPage.clickOnNewAccountButton(driver, test_steps, accountType);
				accountPage.ClickNewAccountbutton(driver, accountType);
				app_logs.info("Clicked on new Account");
				accountPage.AccountDetails(driver, accountType, accountName);
				app_logs.info("Entered account details");
				accountNumber = Utility.GenerateRandomString15Digit();
				accountPage.ChangeAccountNumber(driver, accountNumber);

				accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
				accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, test_steps);
				accountPage.Billinginfo(driver, test, test_steps);
				report.AccountSave(driver, test, accountName, test_steps);
				test_steps.add("========== Account Created ==========");
				app_logs.info("========== Account Created ==========");

				accountPage.NewReservationButton(driver, test);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Travel agent account", testName, "TravelAgentAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Travel agent account", testName, "TravelAgentAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (accountType.equals("Unit Owners")) {

			// If Distribution method is given in Select inputs
			if (!DistributionMethod.isEmpty()) {

				nav.setup(driver);
				nav.LedgerAccounts(driver);

				la.NewAccountbutton(driver);
				String acName = "Unit" + Utility.fourDigitgenerateRandomString();
				String category;

				la.LedgerAccountDetails(driver, acName, "Test", "", "Unit Expenses", "Active");
				la.SaveLedgerAccount(driver);

				nav.Accounts(driver);
				nav.UnitownerAccount(driver);

				if (UnitRevenues.isEmpty()) {
					category = Utility.generateRandomString();
				}else {
					category = UnitRevenues;
				}
				unitOwnerItems = accountPage.getAssociatedUnitOwnerItemsListAndValue(driver, acName, category, test_steps);

			}

			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				accountName = accountName + Utility.generateRandomString();
				nav.Accounts(driver);
				accountPage.ClickNewAccountbutton(driver, accountType);
				accountPage.AccountDetails(driver, accountType, accountName);
				accountNumber = Utility.GenerateRandomString15Digit();
				accountPage.ChangeAccountNumber(driver, accountNumber);

				accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
				accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, test_steps);
				accountPage.Billinginfo(driver, test, test_steps);
				accountPage.associateRooms(driver, test_steps, RoomClass);
				report.AccountSave(driver, test, accountName, test_steps);

				accountPage.NewReservationButton(driver, test);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Unit Owner account", testName, "UnitOwnerAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Unit Owner account", testName, "UnitOwnerAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (accountType.equals("Corporate/Member Accounts")) {
			try {
				test_steps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
				// accountName = accountName + Utility.generateRandomString();
				nav.Accounts(driver);
				accountPage.ClickNewAccountbutton(driver, accountType);
				accountPage.AccountDetails(driver, accountType, accountName);
				accountNumber = Utility.GenerateRandomString15Digit();
				accountPage.ChangeAccountNumber(driver, accountNumber);

				accountPage.AccountAttributes(driver, test, marketSegment, referral, test_steps);
				accountPage.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, test_steps);
				accountPage.Billinginfo(driver, test, test_steps);
				report.AccountSave(driver, test, accountName, test_steps);

				accountPage.NewReservationButton(driver, test);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create corporate account", testName, "CorporateAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		else if (accountType.equals("Group")) {
			
			try {
				
			nav.groups(driver);
			// group.click_NewAccount(driver, test, test_steps);
			group.type_GroupName(driver, test, accountName, test_steps);
			group.type_AccountAttributes(driver, test, marketSegmentOption, referralsOption, test_steps);
			group.type_MailingAddrtess(driver, test, guestFirstName, guestLastName, phoneNumber, address1, city, state,
					country, postalCode, test_steps);
			group.Billinginfo(driver);
			group.Save(driver, test_steps);
			group.click_GroupNewReservation(driver, test_steps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create Group Account", testName, "GroupAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else if (accountType.equals("Reservations")) {

			try {
				reservationPage.click_NewReservation(driver, test_steps);

			} catch (Exception e) {

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click on new Reservation", testName, "NewReservation", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to click on new Reservation", testName, "NewReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		// Reservations

		if (isMRB.equalsIgnoreCase("Yes")) {

			// MRB - Reservation
			test_steps.add("===== Creting MRB Reservation =====");
			try {
				String mrbGuestFirstName = guestFirstName;
				String mrbGuestLastName = guestLastName;
				String mrbAdults = Adults;
				String mrbChildren = Children;
				String mrbRateplan = Rateplan;
				String mrbRoomClass = RoomClass;
				String mrbSalutation = salutation;
				String mrbPhoneNumber = phoneNumber;
				String mrbEmail = email;

				for (int i = 2; i <= numberOfRooms; i++) {
					
					
					mrbGuestFirstName = mrbGuestFirstName + "|" + Utility.generateRandomString();
					mrbGuestLastName = mrbGuestLastName + "|" + Utility.generateRandomString();
					mrbAdults = mrbAdults + "|" + Adults;
					mrbChildren = mrbChildren + "|" + Children;
					mrbRateplan = mrbRateplan + "|" + Rateplan;
					mrbRoomClass = mrbRoomClass + "|" + RoomClass;
					mrbSalutation = mrbSalutation + "|" + salutation;
					mrbPhoneNumber = mrbPhoneNumber + "|" + phoneNumber;
					mrbEmail = mrbEmail + "|" + email;
	
				}
				
				app_logs.info(mrbGuestFirstName);
				app_logs.info(mrbGuestLastName);
				app_logs.info(mrbAdults);
				app_logs.info(mrbChildren);
				app_logs.info(mrbRateplan);
				app_logs.info(mrbRoomClass);
				app_logs.info(mrbSalutation);
				app_logs.info(mrbPhoneNumber);
				app_logs.info(mrbEmail);
				
				for (int i = 0; i < numberOfRooms; i++) {
					String[] fNames = mrbGuestFirstName.split("\\|");
					String[] lNames = mrbGuestLastName.split("\\|");
					app_logs.info("Fanmes: "+fNames.length);
					app_logs.info("Lanmes: "+lNames.length);
					app_logs.info("Loop "+i);
					guestNames.add(fNames[i]+" "+lNames[i]);
				}
				
				app_logs.info("Guest Names: "+guestNames);

				// reservationPage.click_NewReservation(driver, test_steps);
				//guest1 = MrbGuestFirstName.split("|")[1] + MrbGuestLastName.split("|")[1];
				//guest2 = MrbGuestFirstName.split("|")[2] + MrbGuestLastName.split("|")[2];

				reservationPage.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, mrbAdults, mrbChildren, mrbRateplan,
						PromoCode, IsSplitRes);

				if (IsSplitRes.equalsIgnoreCase("Yes")) {

					reservationPage.enter_Adults(driver, test_steps, Adults.split("|")[0]);
					reservationPage.enter_Children(driver, test_steps, Children.split("|")[0]);
					reservationPage.select_Rateplan(driver, test_steps, Rateplan.split("|")[0], PromoCode);

				}

				reservationPage.clickOnFindRooms(driver, test_steps);

				//reservationPage.select_MRBRoomsRatePlan(driver, test_steps, mrbRoomClass, IsAssign, account, mrbAdults);
				roomCost = reservationPage.select_MRBRooms(driver, test_steps, mrbRoomClass, "Yes", account);
				// RoomCharges = reservationPage.select_MRBRooms(driver, test_steps, RoomClass,
				// IsAssign, account);
				reservationPage.deposit(driver, test_steps, "No", "");
				//reservationPage.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
				reservationPage.clickNext(driver, test_steps);
				// reservationPage.enter_MRB_MailingAddress(driver, test_steps, salutation,
				// guestFirstName, guestLastName, phoneNumber, alternativePhone, email, account,
				// accountType, address1, address2, address3, city, country, state, postalCode,
				// isGuesProfile, isAddMoreGuestInfo, isSplitRes,getRoomClasses);
				reservationPage.enter_MRB_MailingAddress(driver, test_steps, mrbSalutation, mrbGuestFirstName,
						mrbGuestLastName, mrbPhoneNumber, alternativePhone, mrbEmail, account, accountType, address1, address2,
						address3, city, country, state, postalCode, "No", "No", IsSplitRes, getRoomClasses);

				if ((account.equalsIgnoreCase("") || account.isEmpty())) {
					//reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
					
					if (PaymentType.equalsIgnoreCase("Reservation")) {
						reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
						//reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
					} else if (PaymentType.equalsIgnoreCase("MC") || PaymentType.equalsIgnoreCase("Visa")
							|| PaymentType.equalsIgnoreCase("Amex") || PaymentType.equalsIgnoreCase("Discover")) {

						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					} else if (PaymentType.equalsIgnoreCase("Account (House Account)")) {
						reservationPage.select_HouseAccoutAsPayment(driver, test_steps, accountName);
					} else if (PaymentType.equalsIgnoreCase("Gift Certificate")) {
						reservationPage.select_GiftCertificateAsPayment(driver, test_steps, accountName);
					} else {
						reservationPage.clickonPaymentMetod(driver, test_steps);
						reservationPage.selectPaymentMethod(driver, PaymentType, test_steps);
					}
				}
				System.out.println(getRoomClasses);

				reservationPage.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, marketSegment, referral);
				reservationPage.clickBookNow(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				
				for (int i = 1; i <= numberOfRooms; i++) {
					reservationNumbers.add(reservationNumber);
					arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));
				}

				status = reservationPage.get_ReservationStatus(driver, test_steps);

				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				
				app_logs.info("MRB Reservation created Successfully");
				test_steps.add("MRB Reservation created Successfully");
				
				//ArrayList<String> roomAbr = new ArrayList<>();
				//roomAbr.add("GBR");
				//roomAbr.add("GBR");
								
				//folio.folioTab(driver);
				//reservationPage.getMRBFolioBalance(driver, test_steps, roomAbr, IsAssign, getRoomClasses);
				
				//reservationPage.close_FirstOpenedReservation(driver, test_steps);
				//reservationSearch.basicSearch_WithReservationNumber(driver, reservationNumber);
				//reservationPage.verify_MR_ToolTip(driver, test_steps, reservationNumber);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create new MRB reservation", testName, "MRBReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create new MRB reservation", testName, "MRBReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		else {
			test_steps.add("==== Creating Single Reservation ====");
			try {
				guestName = guestFirstName + " " + guestLastName;
				date = Utility.getCurrentDate("MMM dd,yyyy");
				
				guestNames.add(guestName);
				arrivalDates.add(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"));

				reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
				reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
				reservationPage.enter_Adults(driver, test_steps, Adults);
				reservationPage.enter_Children(driver, test_steps, Children);
				reservationPage.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
				reservationPage.clickOnFindRooms(driver, test_steps);
				report.selectRoom(driver, test_steps, RoomClass, "Yes", account);
				depositAmount = report.deposit(driver, test_steps, "No", "");

				String s = "//p[contains(text(),'update the reservation to the new policies')]/../following-sibling::div[@class='modal-footer']/div/div/button[text()='Yes']";
				// WebElement e = driver.findElement(By.xpath(s));
				boolean value = Utility.isElementDisplayed(driver, By.xpath(s));
				if (value)
					driver.findElement(By.xpath(s)).click();

				reservationPage.clickNext(driver, test_steps);
				try {
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("(//button[text()='No'])[15]")));
				} catch (Exception e) {
					Wait.wait1Second();
				}
				reservationPage.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
						phoneNumber, alternativePhone, email, account, accountType, address1, address2, address3, city,
						country, state, postalCode, IsGuesProfile);
				if ((account.equalsIgnoreCase("") || account.isEmpty())) {

					if (PaymentType.equalsIgnoreCase("Reservation")) {
						reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
						//reservationPage.selectReservationPaymentType(driver, resNumberPayment, test_steps);
					} else if (PaymentType.equalsIgnoreCase("MC") || PaymentType.equalsIgnoreCase("Visa")
							|| PaymentType.equalsIgnoreCase("Amex") || PaymentType.equalsIgnoreCase("Discover")) {

						reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard,
								CardExpDate);
					} else if (PaymentType.equalsIgnoreCase("Account (House Account)")) {
						reservationPage.select_HouseAccoutAsPayment(driver, test_steps, accountName);
					} else if (PaymentType.equalsIgnoreCase("Gift Certificate")) {
						reservationPage.select_GiftCertificateAsPayment(driver, test_steps, accountName);
					} else {
						reservationPage.clickonPaymentMetod(driver, test_steps);
						reservationPage.selectPaymentMethod(driver, PaymentType, test_steps);
					}

				}
				reservationPage.enter_MarketSegmentDetails(driver, test_steps, account, marketSegment, referral);

				reservationPage.clickBookNow(driver, test_steps);
				// reservationPage.pay_DepositAmount(driver, test_steps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				reservationNumbers.add(reservationNumber);
				app_logs.info("Reservation Number: " + reservationNumber);
				test_steps.add("<b>Reservation Number: " + reservationNumber);
				status = reservationPage.get_ReservationStatus(driver, test_steps);
				reservationPage.clickCloseReservationSavePopup(driver, test_steps);
				roomNumber = reservationPage.get_RoomNumber(driver, test_steps, "Yes");
				
				// reservationPage.get_RoomNumber(driver, test_steps, IsAssign);
				
				app_logs.info("Reservation created Successfully");
				test_steps.add("Reservation created Successfully");
				
//				TripSummaryRoomCharges = reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
//				TripRoomCharge = report.getFolioAmountExcludingCurrency(driver, TripSummaryRoomCharges);
//				TripSummaryTaxes = reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps);
//				TripTax = report.getFolioAmountExcludingCurrency(driver, TripSummaryTaxes);
//				TripSummaryIncidentals = reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps);
//				TripSummaryTripTotal = reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps);
//				TripSummaryPaid = reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps);
//				TripSummaryBalance = reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps);
				
				
//				reservationPage.verify_StayInfo(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children,RoomClass, TripSummaryRoomCharges);
//				report.validate_GuestInfo(driver, test_steps, salutation, guestFirstName, guestLastName, phoneNumber,
//						alternativePhone, email, country, account, address1, address2, address3, state, city,
//						postalCode);
//				reservationPage.get_AssociatedPoliciesToReservation(driver, test_steps);

				reservationPage.click_Folio(driver, test_steps);
				
				roomChargeAmount = report.getFolioAmountExcludingCurrency(driver, driver.findElement(By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span")).getText());
				app_logs.info("Room Charge: "+roomChargeAmount);
				/*String payment = FolioPaid;
				FolioPaid = FolioPaid.trim();
				char ch = FolioPaid.charAt(0);
				FolioPaid = FolioPaid.replace("$", "");
				FolioPaid = FolioPaid.trim();
				paidDeposit = Double.parseDouble(FolioPaid);

				if (depositAmount > 0) {
					if (Double.compare(paidDeposit, depositAmount) == 0) {
						test_steps.("Deposit paid amount is validated : " + ch + " " + paidDeposit);
						app_logs.info("Deposit paid amount is validated : " + ch + " " + paidDeposit);
					}
				}*/

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create New reservation button", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to create New reservation button", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		if (isMRB.equalsIgnoreCase("Yes")) {
			
			FolioRoomCharges = 0;
			FolioIncidentals = 0;
			FolioTaxes = 0;
			FolioFees = 0;
			
			FolioTripTotal = 0;
			FolioPaid = 0;
			FolioBalance = 0;
			
			test_steps.add("===== Adding Folio items to MRB Reservation ======");
			try {
				test_steps.add("========== Adding Line Items ==========");
				app_logs.info("========== Adding Line Items ==========");

				folio.folioTab(driver);
				test_steps.add("Clicked Folio Tab");
				app_logs.info("Clicked Folio Tab");
				
				roomChargeAmount = report.getFolioAmountExcludingCurrency(driver, driver.findElement(By.xpath("(//span[contains(text(),'Room Charge')]//following::td)[4]/span")).getText());
				app_logs.info("Room Charge: "+roomChargeAmount);

				if (!Incidentals.isEmpty()) {
					if (Incidentals.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
						app_logs.info("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
						test_steps.add("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Incidentals);
						}
						
					} else {
						String[] inc = Incidentals.split(",");
						for (int i = 0; i < inc.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
							app_logs.info("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
							test_steps.add("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, inc[i]);
							}
						}
					}
				}

				if (!RoomCharges.isEmpty()) {
					
					if (RoomCharges.split(",").length == 1) {
						if(!RoomCharges.equalsIgnoreCase("Room Charge")) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
							//driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
							app_logs.info("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
							test_steps.add("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, RoomCharges);
							}
						}

					} else {
						String[] rc = RoomCharges.split(",");
						for (int i = 0; i < rc.length; i++) {
							
							if(!rc[i].equalsIgnoreCase("Room Charge")) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
								//driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
								app_logs.info("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
								test_steps.add("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, rc[i]);
								}
							}

						}
					}
				}

				if (!Taxes.isEmpty()) {
					if (Taxes.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Taxes, AmountTaxes);
						
						app_logs.info("Taxes - "+Taxes+" - "+AmountTaxes+" added");
						test_steps.add("Taxes - "+Taxes+" - "+AmountTaxes+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Taxes);
						}
						
						
					} else {
						String[] tax = Taxes.split(",");
						for (int i = 0; i < tax.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, tax[i], AmountTaxes);
							
							app_logs.info("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
							test_steps.add("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, tax[i]);
							}
						}
					}
				}

				if (!Fees.isEmpty()) {
					if (Fees.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Fees, AmountFees);
						
						app_logs.info("Fees - "+Fees+" - "+AmountFees+" added");
						test_steps.add("Fees - "+Fees+" - "+AmountFees+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Fees);
						}
					} else {
						String[] fee = Fees.split(",");
						for (int i = 0; i < fee.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, fee[i], AmountFees);
							
							app_logs.info("Fees - "+fee[i]+" - "+AmountFees+" added");
							test_steps.add("Fees - "+fee[i]+" - "+AmountFees+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, fee[i]);
							}
						}
					}
				}
				
				//folio.ClickSaveFolioButton(driver);
				
				String strItems = "//table[contains(@class,'table-foliogrid')]//tr";
				
				List<WebElement> items = driver.findElements(By.xpath(strItems));
				for (int i = 1; i < items.size(); i++) {
					List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
					itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(), cells.get(7).findElement(By.tagName("a")).getText());
				}
				
				FolioRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
				FolioIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
				try {
					FolioTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TaxesWithCurrency(driver, test_steps));
				}catch(Exception e) {
					app_logs.info("Taxes amount not available");
				}
				
				try {
					FolioFees = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_FeesWithCurrency(driver, test_steps));
				}catch(Exception e) {
					app_logs.info("Fees amount not available");
				}
				
				FolioTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
				FolioPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_PaymentsWithCurrency(driver, test_steps));
				FolioBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_BalanceWithCurrency(driver, test_steps));
				// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
				// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
				// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);
				
				app_logs.info("FolioRoomCharges "+FolioRoomCharges);
				app_logs.info("FolioIncidentals "+FolioIncidentals);
				app_logs.info("FolioTaxes "+FolioTaxes);
				app_logs.info("FolioFees "+FolioFees);
				
				
				
				

			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
			
			app_logs.info("Before folio loop try block");
			try {
				
				app_logs.info("Before folio loop");
				
				for (int k = 2; k <= numberOfRooms; k++) {
					app_logs.info("Entered into folio loop");
					reservationPage.mrbChangeFolio(driver, k, test_steps);
					test_steps.add("========== Adding Line Items ==========");
					app_logs.info("========== Adding Line Items ==========");
					
					Wait.wait5Second();

					if (!Incidentals.isEmpty()) {
						if (Incidentals.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
							app_logs.info("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
							test_steps.add("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, Incidentals);
							}
							
						} else {
							String[] inc = Incidentals.split(",");
							for (int i = 0; i < inc.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
								app_logs.info("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
								test_steps.add("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, inc[i]);
								}
							}
						}
					}

					if (!RoomCharges.isEmpty()) {
						
						if (RoomCharges.split(",").length == 1) {
							if(!RoomCharges.equalsIgnoreCase("Room Charge")) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
								//driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
								app_logs.info("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
								test_steps.add("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, RoomCharges);
								}
							}

						} else {
							String[] rc = RoomCharges.split(",");
							for (int i = 0; i < rc.length; i++) {
								
								if(!rc[i].equalsIgnoreCase("Room Charge")) {
									folio.clickAddLineItemButton(driver);
									folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
									//driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
									app_logs.info("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
									test_steps.add("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
									
									if (itemStatuOptions.equalsIgnoreCase("void")) {
										folio.VoidLineItem(driver, rc[i]);
									}
								}

							}
						}
					}

					if (!Taxes.isEmpty()) {
						if (Taxes.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, Taxes, AmountTaxes);
							
							app_logs.info("Taxes - "+Taxes+" - "+AmountTaxes+" added");
							test_steps.add("Taxes - "+Taxes+" - "+AmountTaxes+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, Taxes);
							}
							
							
						} else {
							String[] tax = Taxes.split(",");
							for (int i = 0; i < tax.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, tax[i], AmountTaxes);
								
								app_logs.info("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
								test_steps.add("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, tax[i]);
								}
							}
						}
					}

					if (!Fees.isEmpty()) {
						if (Fees.split(",").length == 1) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, Fees, AmountFees);
							
							app_logs.info("Fees - "+Fees+" - "+AmountFees+" added");
							test_steps.add("Fees - "+Fees+" - "+AmountFees+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, Fees);
							}
						} else {
							String[] fee = Fees.split(",");
							for (int i = 0; i < fee.length; i++) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, fee[i], AmountFees);
								
								app_logs.info("Fees - "+fee[i]+" - "+AmountFees+" added");
								test_steps.add("Fees - "+fee[i]+" - "+AmountFees+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, fee[i]);
								}
							}
						}
					}
					//folio.ClickSaveFolioButton(driver);
					
					FolioRoomCharges = FolioRoomCharges+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
					FolioIncidentals = FolioIncidentals+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
					try {
						FolioTaxes = FolioTaxes+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TaxesWithCurrency(driver, test_steps));
					}catch(Exception e) {
						app_logs.info("Taxes amount not available");
					}
					
					try {
						FolioFees = FolioFees+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_FeesWithCurrency(driver, test_steps));
					}catch(Exception e) {
						app_logs.info("Fees amount not available");
					}
					
					FolioTripTotal = FolioTripTotal+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
					FolioPaid = FolioPaid+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_PaymentsWithCurrency(driver, test_steps));
					FolioBalance = FolioBalance+report.getFolioAmountExcludingCurrency(driver, reservationPage.get_BalanceWithCurrency(driver, test_steps));
					// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
					// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
					// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);
					
					app_logs.info("FolioRoomCharges "+FolioRoomCharges);
					app_logs.info("FolioIncidentals "+FolioIncidentals);
					app_logs.info("FolioTaxes "+FolioTaxes);
					app_logs.info("FolioFees "+FolioFees);
					app_logs.info("FolioTripTotal "+FolioTripTotal);
					
					
				}
				

				
				
				
				
				

			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
			
			

			
			
			
		}else {
			
			try {
				test_steps.add("========== Adding Line Items ==========");
				app_logs.info("========== Adding Line Items ==========");

				folio.folioTab(driver);
				test_steps.add("Clicked Folio Tab");
				app_logs.info("Clicked Folio Tab");
				

				if (!Incidentals.isEmpty()) {
					if (Incidentals.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Incidentals, AmountIncidentals);
						app_logs.info("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
						test_steps.add("Incidentals - "+Incidentals+" - "+AmountIncidentals+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Incidentals);
						}
						
					} else {
						String[] inc = Incidentals.split(",");
						for (int i = 0; i < inc.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, inc[i], AmountIncidentals);
							app_logs.info("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
							test_steps.add("Incidentals - "+inc[i]+" - "+AmountIncidentals+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, inc[i]);
							}
						}
					}
				}

				if (!RoomCharges.isEmpty()) {
					
					if (RoomCharges.split(",").length == 1) {
						if(!RoomCharges.equalsIgnoreCase("Room Charge")) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, RoomCharges, AmountRoomCharges);
							//driver.findElement(By.xpath("//span[text()='"+RoomCharges+"']/../../td[contains(@class,'changestatus')]")).click();
							app_logs.info("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
							test_steps.add("Room Charge - "+RoomCharges+" - "+AmountRoomCharges+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, RoomCharges);
							}
						}

					} else {
						String[] rc = RoomCharges.split(",");
						for (int i = 0; i < rc.length; i++) {
							
							if(!rc[i].equalsIgnoreCase("Room Charge")) {
								folio.clickAddLineItemButton(driver);
								folio.AddFolioLineItem(driver, rc[i], AmountRoomCharges);
								//driver.findElement(By.xpath("//span[text()='"+rc[i]+"']/../../td[contains(@class,'changestatus')]")).click();
								app_logs.info("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
								test_steps.add("Room Charge - "+rc[i]+" - "+AmountRoomCharges+" added");
								
								if (itemStatuOptions.equalsIgnoreCase("void")) {
									folio.VoidLineItem(driver, rc[i]);
								}
							}

						}
					}
				}

				if (!Taxes.isEmpty()) {
					if (Taxes.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Taxes, AmountTaxes);
						
						app_logs.info("Taxes - "+Taxes+" - "+AmountTaxes+" added");
						test_steps.add("Taxes - "+Taxes+" - "+AmountTaxes+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Taxes);
						}
						
						
					} else {
						String[] tax = Taxes.split(",");
						for (int i = 0; i < tax.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, tax[i], AmountTaxes);
							
							app_logs.info("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
							test_steps.add("Taxes - "+tax[i]+" - "+AmountTaxes+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, tax[i]);
							}
						}
					}
				}

				if (!Fees.isEmpty()) {
					if (Fees.split(",").length == 1) {
						folio.clickAddLineItemButton(driver);
						folio.AddFolioLineItem(driver, Fees, AmountFees);
						
						app_logs.info("Fees - "+Fees+" - "+AmountFees+" added");
						test_steps.add("Fees - "+Fees+" - "+AmountFees+" added");
						
						if (itemStatuOptions.equalsIgnoreCase("void")) {
							folio.VoidLineItem(driver, Fees);
						}
					} else {
						String[] fee = Fees.split(",");
						for (int i = 0; i < fee.length; i++) {
							folio.clickAddLineItemButton(driver);
							folio.AddFolioLineItem(driver, fee[i], AmountFees);
							
							app_logs.info("Fees - "+fee[i]+" - "+AmountFees+" added");
							test_steps.add("Fees - "+fee[i]+" - "+AmountFees+" added");
							
							if (itemStatuOptions.equalsIgnoreCase("void")) {
								folio.VoidLineItem(driver, fee[i]);
							}
						}
					}
				}
				
				//folio.ClickSaveFolioButton(driver);
				
				
				String strItems = "//table[contains(@class,'table-foliogrid')]//tr";
				
				List<WebElement> items = driver.findElements(By.xpath(strItems));
				for (int i = 1; i < items.size(); i++) {
					List<WebElement> cells = items.get(i).findElements(By.tagName("td"));
					itemDescription.put(cells.get(6).findElement(By.tagName("span")).getText(), cells.get(7).findElement(By.tagName("a")).getText());
				}
				
//				FolioRoomCharges = reservationPage.get_RoomChargeWithCurrency(driver, test_steps);
//				FolioIncidentals = reservationPage.get_InceidentalsWithCurrency(driver, test_steps);
//				FolioTaxes = reservationPage.get_TaxesWithCurrency(driver, test_steps);
//				FolioFees = reservationPage.get_FeesWithCurrency(driver, test_steps);
//				
//				FolioTripTotal = reservationPage.get_TotalChargesWithCurrency(driver, test_steps);
//				FolioPaid = reservationPage.get_PaymentsWithCurrency(driver, test_steps);
//				FolioBalance = reservationPage.get_BalanceWithCurrency(driver, test_steps);
				
				FolioRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_RoomChargeWithCurrency(driver, test_steps));
				FolioIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_InceidentalsWithCurrency(driver, test_steps));
				try {
					FolioTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TaxesWithCurrency(driver, test_steps));
				}catch(Exception e) {
					app_logs.info("Taxes amount not available");
				}
				
				try {
					FolioFees = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_FeesWithCurrency(driver, test_steps));
				}catch(Exception e) {
					app_logs.info("Fees amount not available");
				}
				
				FolioTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TotalChargesWithCurrency(driver, test_steps));
				FolioPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_PaymentsWithCurrency(driver, test_steps));
				FolioBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_BalanceWithCurrency(driver, test_steps));
				
				// reservationPage.verify_BannerDetails(driver, test_steps, salutation,
				// GuestFirstName, GuestLastName, phoneNumber, email, FilioTripTotal,
				// FilioBalance, reservation, status, CheckInDate, CheckOutDate, country);
				
				app_logs.info("FolioRoomCharges "+FolioRoomCharges);
				app_logs.info("FolioIncidentals "+FolioIncidentals);
				app_logs.info("FolioTaxes "+FolioTaxes);
				app_logs.info("FolioFees "+FolioFees);
				app_logs.info("FolioTripTotal "+FolioTripTotal);

			} catch (Exception e) {
				if (Utility.reTry.get(test_name) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to add Folio Line Items", testName, "FolioLineItems", driver);
				} else {

					Assert.assertTrue(false);
				}
			} catch (Error e) {
				test_steps.add(e.toString());
			}
	
		}

		

		// Item status
		try {

			if (itemStatuOptions.split(",").length == 1) {
				if (itemStatuOptions.equals("Pending")) {
					//folio.clickAddLineItemButton(driver);
					//folio.AddFolioLineItem(driver, "Laundry", "50");
				}else if (itemStatuOptions.equals("Posted")) {
					//folio.clickAddLineItemButton(driver);
					//folio.AddFolioLineItem(driver, "Spa", "70");
					//driver.findElement(By.xpath("//span[text()='Spa']/../../td[contains(@class,'changestatus')]")).click();
					//reservationPage.checkinReservation(driver, test_steps);
					reservationPage.CheckInButton(driver);
					reservationPage.generatGuestReportToggle(driver, test_steps, "No");
					reservationPage.CheckInConfrimButton(driver);
					
				}
				//driver.findElement(By.xpath("(//button[text()='Save'])[9]")).click();
			} else {
				System.out.println("Enter only one option for Item status");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to change Status", testName, "ItemsStatus", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}

		//Payment
		try {
			
//			TripSummaryRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps)); 
//			//TripRoomCharge = report.getFolioAmountExcludingCurrency(driver, TripSummaryRoomCharges);
//			TripSummaryTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps));
//			//TripTax = report.getFolioAmountExcludingCurrency(driver, TripSummaryTaxes);
//			TripSummaryIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps));
//			TripSummaryTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps));
//			TripSummaryPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps));
//			TripSummaryBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps));

			if (isPayment.equalsIgnoreCase("Yes")) {
				driver.navigate().refresh();
				try {
					driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]")).click();
				}catch(Exception e) {
					Wait.wait5Second();
					driver.findElement(By.xpath("(//button[contains(text(),'TAKE PAYMENT')])[1]")).click();
				}
				
				app_logs.info("Clicked on Take Payment");
				//reservationPage.takePayment(driver, unitOwnerItems, PaymentType, CardNumber, NameOnCard, CardExpDate, TakePaymentType, IsChangeInPayAmount, ChangedAmountValue, IsSetAsMainPaymentMethod, AddPaymentNotes)
				reservationPage.payButtonClickInTakePayment(driver, test_steps, ""+FolioTripTotal+"", true, true);
			}

			
			
//			TripSummaryRoomCharges = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryRoomChargesWithCurrency(driver, test_steps)); 
//			//TripRoomCharge = report.getFolioAmountExcludingCurrency(driver, TripSummaryRoomCharges);
//			TripSummaryTaxes = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTaxesWithCurrency(driver, test_steps));
//			//TripTax = report.getFolioAmountExcludingCurrency(driver, TripSummaryTaxes);
//			TripSummaryIncidentals = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryInceidentalsWithCurrency(driver, test_steps));
//			TripSummaryTripTotal = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryTripTotalChargesWithCurrency(driver, test_steps));
//			TripSummaryPaid = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryPaidWithCurrency(driver, test_steps));
//			TripSummaryBalance = report.getFolioAmountExcludingCurrency(driver, reservationPage.get_TripSummaryBalanceWithCurrency(driver, test_steps));
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to change Status", testName, "ItemsStatus", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		
		// to change the Reservation status
		try {
			
			
			if (reservationStatusOptions.split(",").length == 1) {
                CPReservationPage cppage = new CPReservationPage();
                switch(reservationStatusOptions){
                case "Confirmed":
                    cppage.confirmReservation(driver);
                    break;
                case "Guaranteed":
                    cppage.guaranteeReservation(driver);
                    break;
                case "On Hold":
                    cppage.onHoldReservation(driver);
                    break;
                case "Cancelled":
                    cppage.cancelReservation(driver);
                    break;
                case "No Show":
                case "NoShow":
                    cppage.noShowReservation(driver);
                    break;
                case "In-House":
                    cppage.inHouseReservation(driver);
                    break;
                case "Departed":
                    cppage.departedReservation(driver);
                    break;
                }
               
            } else {
                System.out.println("Enter only one option for Reservation status");
            }
			
			

/*			if (reservationStatusOptions.split(",").length == 1) {
				//Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
				switch (reservationStatusOptions) {
				case "Confirmed":
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
					driver.findElement(By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Confirmed']")).click();
					Wait.wait5Second();
					String s1 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s1.equals("CONFIRMED")) {
						app_logs.info("Successfully changed Reservation status to confirmed");
					} else {
						app_logs.info("Unable to change Reservation status to confirmed");
					}
					break;
				case "Guaranteed":
					driver.findElement(
							By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Guaranteed']"))
							.click();
					Wait.wait5Second();
					String s2 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s2.equals("GUARANTEED")) {
						app_logs.info("Successfully changed Reservation status to Guaranteed");
					} else {
						app_logs.info("Unable to change Reservation status to Guaranteed");
					}
					break;
				case "On Hold":
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
					driver.findElement(
							By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='On Hold']"))
							.click();
					Wait.wait5Second();
					String s3 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s3.equals("ON HOLD")) {
						app_logs.info("Successfully changed Reservation status to On Hold");
					} else {
						app_logs.info("Unable to change Reservation status to On Hold");
					}
					break;
				case "Cancelled":
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
					driver.findElement(
							By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='Cancel']"))
							.click();
					driver.findElement(
							By.xpath("//label[text()='ADD CANCELLATION REASON']/following-sibling::textarea"))
							.sendKeys("Cancel");
					driver.findElement(By.xpath("//button[text()='PROCEED TO CANCELLATION PAYMENT']")).click();
					driver.findElement(By.xpath("//button[contains(text(),'Log ')]")).click();
					driver.findElement(By.xpath("//div[@class='col-md-12 text-right']/button[text()='CLOSE']")).click();
					Wait.wait5Second();
					String s4 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s4.equals("CANCELLED")) {
						app_logs.info("Successfully changed Reservation status to Cancelled");
					} else {
						app_logs.info("Unable to change Reservation status to Cancelled");
					}
					break;
				case "No Show":
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
					driver.findElement(
							By.xpath("//div[@class='ng-statusChnage ir-statusMenu ul']//span[text()='No Show']"))
							.click();
					driver.findElement(By.xpath("//button[text()='CONFIRM NO SHOW']")).click();
					driver.findElement(By.xpath("//button[@data-bind='visible:noShow.isFinalStep' and text()='Close']"))
							.click();
					Wait.wait5Second();
					String s5 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s5.equals("NO SHOW")) {
						app_logs.info("Successfully changed Reservation status to No Show");
					} else {
						app_logs.info("Unable to change Reservation status to No Show");
					}
					break;
				case "InHouse":
					
					reservationPage.CheckInButton(driver);
					reservationPage.generatGuestReportToggle(driver, test_steps, "No");
					reservationPage.CheckInConfrimButton(driver);
//					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
//					String currentStatus = driver.findElement(By.xpath("//span[@class='ng-status']")).getAttribute("textContent");
//					if (!currentStatus.equals("In-House")) {
//						driver.findElement(By.xpath("//button[text()='Check In']")).click();
//						Utility.clickThroughJavaScript(driver,
//								driver.findElement(By.xpath("//button[contains(text(),'CONFIRM CHECK IN')]")));
//						// Wait.wait5Second();
//						Wait.waitForNewWindow(driver, 10000);
//						Utility.switchTab(driver, 1);
//						driver.close();
//						Utility.switchTab(driver, 0);
//						String s6 = driver.findElement(By.xpath("//span[@class='ng-status']")).getAttribute("textContent");
//						System.out.println("s6=" + s6);
//						if (s6.equals("In-House")) {
//							app_logs.info("Successfully changed Reservation status to In-House");
//						} else {
//							app_logs.info("Unable to change Reservation status to In-House");
//						}
//						break;
//					}
					break;

				case "Departed":
					Utility.clickThroughAction(driver, driver.findElement(By.xpath("//i[@data-toggle='dropdown']")));
					driver.findElement(By.xpath("//button[text()='Check In']")).click();
					Utility.clickThroughJavaScript(driver,
							driver.findElement(By.xpath("//button[contains(text(),'CONFIRM CHECK IN')]")));
					Wait.waitForNewWindow(driver, 10000);
					Utility.switchTab(driver, 1);
					driver.close();
					Utility.switchTab(driver, 0);
					driver.findElement(By.xpath("//button[text()='Check Out']")).click();
					Utility.clickThroughJavaScript(driver, driver.findElement(
							By.xpath("//span[text()='Generate Guest Statement']/following-sibling::button[1]")));
					driver.findElement(By.xpath("//button[contains(text(),'Log ')]")).click();
					Wait.waitForNewWindow(driver, 10000);
					Utility.switchTab(driver, 1);
					driver.close();
					Utility.switchTab(driver, 0);
					driver.findElement(By.xpath("(//button[contains(text(),'CLOSE')])[2]")).click();

					String s7 = driver.findElement(By.xpath("//span[@class='ng-status']")).getText();
					if (s7.equals("DEPARTED")) {
						app_logs.info("Successfully changed Reservation status to DEPARTED");
					} else {
						app_logs.info("Unable to change Reservation status to DEPARTED");
					}
					break;

				}
			} else {
				System.out.println("Enter only one option for Reservation status");
			}*/

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to change Reservation status", testName, "ReservationStatus", driver);
			} else {

				Assert.assertTrue(false);
			}
		} catch (Error e) {
			test_steps.add(e.toString());
		}
		
		

		// reservationPage.click_History(driver, test_steps);
		// reservationPage.verify_ReservationInHistoryTab(driver, test_steps,
		// reservation);
		// if (depositAmount > 0) {
		// reservationPage.verifyDepositPaymentInHistoryTab(driver, test_steps,
		// depositAmount);
		// }
		// reservationPage.velidate_TripSummaryAndFolio(driver, test_steps,
		// FilioRoomCharges, FilioTaxes,
		// FilioIncidentals, FilioTripTotal, payment, FilioBalance,
		// TripSummaryRoomCharges,
		// TripSummaryTaxes, TripSummaryIncidentals, TripSummaryTripTotal,
		// TripSummaryPaid,
		// TripSummaryBalance);
		// reservationPage.verify_GuestReportLabelsValidations(driver, test_steps);
		// RetryFailedTestCases.count = Utility.reset_count;
		// Utility.AddTest_IntoReport(testName, test_description, test_category,
		// test_steps);

		
		Wait.wait60Second();
		Wait.wait60Second();
		Wait.wait60Second();
		
		// Select inputs
		try {

			try {

				nav.ReportsV2(driver);
				report.navigateToLedgerBalancesReport(driver);
				availableTypes = report.getAllAvailableTypes(driver, test_steps);
				report.selectSelectInputsAll(driver, inputs, options, test_steps);
				// Utility.switchTab(driver, (new
				// ArrayList<String>(driver.getWindowHandles()).size()) - 1);

				report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, accountTypeOptions);
				if (!accountTypeOptions.equalsIgnoreCase("Reservations")) {
					report.selectAccountTyepOptionsGiventhroughExcel(driver, test_steps, "Reservations");
				}
				report.selectItemStatusOptionsGiventhroughExcel(driver, test_steps, itemStatuOptions);
				report.selectIncludeDataFromOptionsGiventhroughExcel(driver, test_steps, IncludeDataFromUsers,
						IncludeDataFromShiftTimeStartHours,
						IncludeDataFromShiftTimeStartMinutes, IncludeDataFromShiftTimeStartAmPm,
						IncludeDataFromShiftTimeEndHours, IncludeDataFromShiftTimeEndMinutes,
						IncludeDataFromShiftTimeEndAmPm);
				report.selectTaxExemptLedgerItemsGiventhroughExcel(driver, test_steps, TaxExemptLedgerItemsOption);
				report.selectMarketSegmentOptionGiventhroughExcel(driver, test_steps, marketSegmentOption);
				report.selectReservationStatusOptionsGiventhroughExcel(driver, test_steps, reservationStatusOptions);
				report.selectReferralsOptionGiventhroughExcel(driver, test_steps, referralsOption);

				report.clickOnRunReport(driver);
				
				
							} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to Run Report after Reservation", testName, "RunReport", driver);
				} else {

					Assert.assertTrue(false);
				}
			}
			
			
			try{
				
				test_steps.add("====  Ledger Balances Report Validation =====");
				test_steps.add("====  <b>Summary View Validation</b> =====");
				
				afterLedgerCategoryDetails = report.getLedgerCategoryDetails(driver, test_steps);

				// afterDetailsOfGivenCategory = report.getDetailsOfGivenCategory(driver, "Room
				// Charge", test_steps);
				// report.getDetailsOfGivenCategory(driver, "Tax", test_steps);
				afterDetailsOfAllLedgerCategories = report.getDetailsOfAllLedgerCategories(driver, test_steps);
				
				app_logs.info("Before: "+beforeLedgerCategoryDetails);
				test_steps.add("Before: "+beforeLedgerCategoryDetails);
				app_logs.info("After: "+afterLedgerCategoryDetails);
				test_steps.add("After: "+afterLedgerCategoryDetails);
				
				app_logs.info("Before Individual: "+beforeDetailsOfAllLedgerCategories);
				test_steps.add("Before Individual: "+beforeDetailsOfAllLedgerCategories);
				app_logs.info("After Individual: "+afterDetailsOfAllLedgerCategories);
				test_steps.add("After Individual: "+afterDetailsOfAllLedgerCategories);

				if (!Incidentals.isEmpty()) {

					//Summary view
					if (beforeLedgerCategoryDetails.containsKey("Incidental")) {
						double inc = Double.parseDouble(afterLedgerCategoryDetails.get("Incidental").substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get("Incidental").substring(1));
						app_logs.info("Incidentals: " + inc);
						
						if (inc == FolioIncidentals) {
							app_logs.info("Incidentals amount validated successfully in Summary View");
							test_steps.add("Incidentals amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Incidentals amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Incidentals amount in Summary View validation failed. Expected: "+FolioIncidentals+" But found: "+inc);
						}
						
					} else {
						double inc = Double.parseDouble(afterLedgerCategoryDetails.get("Incidental").substring(1));
						app_logs.info("Incidentals: " + inc);
						
						if (inc == FolioIncidentals) {
							app_logs.info("Incidentals amount validated successfully in Summary View");
							test_steps.add("Incidentals amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Incidentals amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Incidentals amount in Summary View validation failed");
						}
						
					}

					String[] strInc = Incidentals.split(",");

					if (strInc.length == 1) {
						if (beforeDetailsOfAllLedgerCategories.containsKey(Incidentals)) {
							double inc = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Incidentals).substring(1))
									- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(Incidentals).substring(1));
							app_logs.info("Incidentals - " + Incidentals + ": " + inc);
							
							if (inc == (Double.parseDouble(AmountIncidentals)*numberOfRooms)) {
								app_logs.info("Incidentals - "+Incidentals+" amount validated successfully in Summary View");
								test_steps.add("Incidentals - "+Incidentals+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Incidentals amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Incidentals amount in Summary View validation failed. Expected: "+(Double.parseDouble(AmountIncidentals)*numberOfRooms)+" But found: "+inc);
							}
							
						} else {
							double inc = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Incidentals).substring(1));
							app_logs.info("Incidentals - " + Incidentals + ": " + inc);
							
							if (inc == (Double.parseDouble(AmountIncidentals)*numberOfRooms)) {
								app_logs.info("Incidentals - "+Incidentals+" amount validated successfully in Summary View");
								test_steps.add("Incidentals - "+Incidentals+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Incidentals - "+Incidentals+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Incidentals - "+Incidentals+" amount in Summary View validation failed: "+(Double.parseDouble(AmountIncidentals)*numberOfRooms)+" But found: "+inc);
							}
							
						}
					} else {
						for (int i = 0; i < strInc.length; i++) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(strInc[i])) {
								double inc = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strInc[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strInc[i]).substring(1));
								app_logs.info("Incidentals - " + strInc[i] + ": " + inc);
								
								if (inc == (Double.parseDouble(AmountIncidentals)*numberOfRooms)) {
									app_logs.info("Incidentals - "+strInc[i]+" amount validated successfully in Summary View");
									test_steps.add("Incidentals - "+strInc[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Incidentals - "+strInc[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Incidentals - "+strInc[i]+" amount in Summary View validation failed: "+(Double.parseDouble(AmountIncidentals)*numberOfRooms)+" But found: "+inc);
								}
								
							} else {
								double inc = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strInc[i]).substring(1));
								app_logs.info("Incidentals - " + strInc[i] + ": " + inc);
								
								if (inc == (Double.parseDouble(AmountIncidentals)*numberOfRooms)) {
									app_logs.info("Incidentals - "+strInc[i]+" amount validated successfully in Summary View");
									test_steps.add("Incidentals - "+strInc[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Incidentals - "+strInc[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Incidentals - "+strInc[i]+" amount in Summary View validation failed: "+(Double.parseDouble(AmountIncidentals)*numberOfRooms)+" But found: "+inc);
								}
								
							}
						}
					}

				}

				if (!RoomCharges.isEmpty()) {

					app_logs.info("Before: "+beforeLedgerCategoryDetails);
					app_logs.info("After: "+afterLedgerCategoryDetails);
					
					app_logs.info("Before: "+beforeDetailsOfAllLedgerCategories);
					app_logs.info("After: "+afterDetailsOfAllLedgerCategories);
					
					
					//Summary view
					if (beforeLedgerCategoryDetails.containsKey("Room Charge")) {
						double rm = Double.parseDouble(afterLedgerCategoryDetails.get("Room Charge").substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get("Room Charge").substring(1));
						app_logs.info("Room Charges: " + rm);
						
						if (rm == FolioRoomCharges) {
							app_logs.info("Room Charges amount validated successfully in Summary View");
							test_steps.add("Room Charges amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Room Charges amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Room Charges amount in Summary View validation failed. Expected: "+FolioRoomCharges+" But found: "+rm);
						}
						
						
					} else {
						double rm = Double.parseDouble(afterLedgerCategoryDetails.get("Room Charge").substring(1));
						app_logs.info("Room Charges: " + rm);
						
						if (rm == FolioRoomCharges) {
							app_logs.info("Room Charges amount validated successfully in Summary View");
							test_steps.add("Room Charges amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Room Charges amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Room Charges amount in Summary View validation failed");
						}
					}

					String[] strRm = RoomCharges.split(",");

					if (strRm.length == 1) {
						
						if (RoomCharges.equalsIgnoreCase("Room Charge")) {
							
							if (beforeDetailsOfAllLedgerCategories.containsKey(RoomCharges)) {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(RoomCharges).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(RoomCharges).substring(1));
								app_logs.info("Room Charges - " + RoomCharges + ": " + rm);
								
								if (rm == (roomChargeAmount*numberOfRooms)) {
									app_logs.info("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
									test_steps.add("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed. Expected: "+(roomChargeAmount*numberOfRooms)+" But found: "+rm);
								}
								
							} else {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(RoomCharges).substring(1));
								app_logs.info("Room Charges - " + RoomCharges + ": " + rm);
								
								if (rm == (roomChargeAmount*numberOfRooms)) {
									app_logs.info("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
									test_steps.add("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
								}
								
							}
							
						}else {
							if (beforeDetailsOfAllLedgerCategories.containsKey(RoomCharges)) {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(RoomCharges).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(RoomCharges).substring(1));
								app_logs.info("Room Charges - " + RoomCharges + ": " + rm);
								
								if (rm == (Double.parseDouble(AmountRoomCharges)*numberOfRooms)) {
									app_logs.info("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
									test_steps.add("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
								}
								
							} else {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(RoomCharges).substring(1));
								app_logs.info("Room Charges - " + RoomCharges + ": " + rm);
								
								if (rm == (Double.parseDouble(AmountRoomCharges)*numberOfRooms)) {
									app_logs.info("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
									test_steps.add("Room Charges - "+RoomCharges+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Room Charges  - "+RoomCharges+" amount in Summary View validation failed");
								}
								
							}
						}
						
						
						
					} else {
						for (int i = 0; i < strRm.length; i++) {
							app_logs.info("Item: "+strRm[i]);
							if (beforeDetailsOfAllLedgerCategories.containsKey(strRm[i])) {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strRm[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strRm[i]).substring(1));
								app_logs.info("Room CHarges - " + strRm[i] + ": " + rm);
						
								if (strRm[i].equalsIgnoreCase("Room Charge")) {
									if (rm == (roomChargeAmount*numberOfRooms)) {
										app_logs.info("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
										test_steps.add("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
									}else {
										app_logs.info("Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
									}
								}else {
									if (rm == (Double.parseDouble(AmountRoomCharges)*numberOfRooms)) {
										app_logs.info("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
										test_steps.add("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
									}else {
										app_logs.info("Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
									}
								}
	
							} else {
								double rm = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strRm[i]).substring(1));
								app_logs.info("Room Charges - " + strRm[i] + ": " + rm);
								
								if (strRm[i].equalsIgnoreCase("Room Charge")) {
									if (rm == (roomChargeAmount*numberOfRooms)) {
										app_logs.info("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
										test_steps.add("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
									}else {
										app_logs.info("Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
									}
								}else {
									if (rm == (Double.parseDouble(AmountRoomCharges)*numberOfRooms)) {
										app_logs.info("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
										test_steps.add("Room Charges - "+strRm[i]+" amount validated successfully in Summary View");
									}else {
										app_logs.info("Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
										test_steps.add("AssertionError : Failed - Room Charges  - "+strRm[i]+" amount in Summary View validation failed");
									}
								}
								
							}
						}
					}

				}
				
				if (FolioTaxes != 0) {
					
				}

				if (!Taxes.isEmpty()) {

					//Summary view
					if (beforeLedgerCategoryDetails.containsKey("Tax")) {
						double tax = Double.parseDouble(afterLedgerCategoryDetails.get("Tax").substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get("Tax").substring(1));
						app_logs.info("Taxes : " + tax);
						
						if (tax == FolioTaxes) {
							app_logs.info("Taxes amount validated successfully in Summary View");
							test_steps.add("Taxes amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Taxes amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Taxes amount in Summary View validation failed. Expected: "+FolioTaxes+" but Found: "+tax);
						}
						
					} else {
						double tax = Double.parseDouble(afterLedgerCategoryDetails.get("Tax").substring(1)); // need to add more code
						app_logs.info("Taxes : " + tax);
						
						if (tax == FolioTaxes) {
							app_logs.info("Taxes amount validated successfully in Summary View");
							test_steps.add("Taxes amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Taxes amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Taxes amount in Summary View validation failed. Expected: "+FolioTaxes+" but Found: "+tax);
						}
						
					}

					
					String[] strTax = Taxes.split(",");

					if (strTax.length == 1) {
						if (beforeDetailsOfAllLedgerCategories.containsKey(Taxes)) {
							double tax = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Taxes).substring(1))
									- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(Taxes).substring(1));
							app_logs.info("Taxes - " + RoomCharges + ": " + tax);
							
							if (tax == (Double.parseDouble(AmountTaxes)*numberOfRooms)) {
								app_logs.info("Taxes - "+Taxes+" amount validated successfully in Summary View");
								test_steps.add("Taxes - "+Taxes+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Taxes - "+Taxes+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Taxes - "+Taxes+" amount in Summary View validation failed");
							}
							
						} else {
							double tax = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Taxes).substring(1));
							app_logs.info("Taxes - " + RoomCharges + ": " + tax);
							
							if (tax == (Double.parseDouble(AmountTaxes)*numberOfRooms)) {
								app_logs.info("Taxes - "+Taxes+" amount validated successfully in Summary View");
								test_steps.add("Taxes - "+Taxes+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Taxes - "+Taxes+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Taxes - "+Taxes+" amount in Summary View validation failed");
							}
							
						}
					} else {
						for (int i = 0; i < strTax.length; i++) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(strTax[i])) {
								double tax = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strTax[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strTax[i]).substring(1));
								app_logs.info("Taxes - " + strTax[i] + ": " + tax);
								
								if (tax == (Double.parseDouble(AmountTaxes)*numberOfRooms)) {
									app_logs.info("Taxes - "+strTax[i]+" amount validated successfully in Summary View");
									test_steps.add("Taxes - "+strTax[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Taxes - "+strTax[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Taxes - "+strTax[i]+" amount in Summary View validation failed");
								}
								
							} else {
								double tax = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strTax[i]).substring(1));
								app_logs.info("Taxes - " + strTax[i] + ": " + tax);
								
								if (tax == (Double.parseDouble(AmountTaxes)*numberOfRooms)) {
									app_logs.info("Taxes - "+strTax[i]+" amount validated successfully in Summary View");
									test_steps.add("Taxes - "+strTax[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Taxes - "+strTax[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Taxes - "+strTax[i]+" amount in Summary View validation failed");
								}
								
							}
						}
					}

				}

				if (!Fees.isEmpty()) {

					//Summary view
					if (beforeLedgerCategoryDetails.containsKey("Fee")) {
						double fee = Double.parseDouble(afterLedgerCategoryDetails.get("Fee").substring(1))
								- Double.parseDouble(beforeLedgerCategoryDetails.get("Fee").substring(1));
						app_logs.info("Fees : " + fee);
						
						if (fee == FolioFees) {
							app_logs.info("Fees amount validated successfully in Summary View");
							test_steps.add("Fees amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Fees amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Fees amount in Summary View validation failed. Expected: "+FolioFees+" But Found: "+fee);
						}
						
					} else {
						double fee = Double.parseDouble(afterLedgerCategoryDetails.get("Fee").substring(1)); // need to add more code
						app_logs.info("Fees : " + fee);
						
						if (fee == FolioFees) {
							app_logs.info("Fees amount validated successfully in Summary View");
							test_steps.add("Fees amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Fees amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Fees amount in Summary View validation failed. Expected: "+FolioFees+" But Found: "+fee);
						}
						
					}

					String[] strFee = Fees.split(",");

					if (strFee.length == 1) {
						if (beforeDetailsOfAllLedgerCategories.containsKey(Fees)) {
							double fee = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Fees).substring(1))
									- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(Fees).substring(1));
							app_logs.info("Fees - " + Fees + ": " + fee);
							
							if (fee == (Double.parseDouble(AmountFees)*numberOfRooms)) {
								app_logs.info("Fees "+Fees+" amount validated successfully in Summary View");
								test_steps.add("Fees "+Fees+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Fees "+Fees+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Fees "+Fees+" amount in Summary View validation failed");
							}
							
						} else {
							double fee = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(Fees).substring(1));
							app_logs.info("Fees - " + Fees + ": " + fee);
							
							if (fee == (Double.parseDouble(AmountFees)*numberOfRooms)) {
								app_logs.info("Fees "+Fees+" amount validated successfully in Summary View");
								test_steps.add("Fees "+Fees+" amount validated successfully in Summary View");
							}else {
								app_logs.info("Failed - Fees "+Fees+" amount in Summary View validation failed");
								test_steps.add("AssertionError : Failed - Fees "+Fees+" amount in Summary View validation failed");
							}
							
						}
					} else {
						for (int i = 0; i < strFee.length; i++) {
							if (beforeDetailsOfAllLedgerCategories.containsKey(strFee[i])) {
								double fee = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strFee[i]).substring(1))
										- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(strFee[i]).substring(1));
								app_logs.info("Fees - " + strFee[i] + ": " + fee);
								
								if (fee == (Double.parseDouble(AmountFees)*numberOfRooms)) {
									app_logs.info("Fees "+strFee[i]+" amount validated successfully in Summary View");
									test_steps.add("Fees "+strFee[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Fees "+strFee[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Fees "+strFee[i]+" amount in Summary View validation failed");
								}
								
							} else {
								double fee = Double.parseDouble(afterDetailsOfAllLedgerCategories.get(strFee[i]).substring(1));
								app_logs.info("Fees - " + strFee[i] + ": " + fee);
								
								if (fee == (Double.parseDouble(AmountFees)*numberOfRooms)) {
									app_logs.info("Fees "+strFee[i]+" amount validated successfully in Summary View");
									test_steps.add("Fees "+strFee[i]+" amount validated successfully in Summary View");
								}else {
									app_logs.info("Failed - Fees "+strFee[i]+" amount in Summary View validation failed");
									test_steps.add("AssertionError : Failed - Fees "+strFee[i]+" amount in Summary View validation failed");
								}
								
							}
						}
					}

				}
				
//				for (int i = 0; i < availableTypes.size(); i++) {
//					
//				}
				
				//Payment Method
				if (!PaymentMethod.isEmpty()) {
					if (beforeDetailsOfAllLedgerCategories.containsKey(PaymentMethod)) {
						double pay = Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1))
								- Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
						app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);
						
						if (pay == FolioTripTotal) {
							app_logs.info("Payment Method "+PaymentMethod+" amount validated successfully in Summary View");
							test_steps.add("Payment Method "+PaymentMethod+" amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Payment Method "+PaymentMethod+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Payment Method "+PaymentMethod+" amount in Summary View validation failed. Expected: "+FolioTripTotal+" But found: "+pay);
						}
						
					} else {
						try {
						double pay = Double.parseDouble(beforeDetailsOfAllLedgerCategories.get(PaymentMethod).substring(1));
						app_logs.info("PaymentMethod - " + PaymentMethod + ": " + pay);
						
						if (pay == FolioTripTotal) {
							app_logs.info("Payment Method "+PaymentMethod+" amount validated successfully in Summary View");
							test_steps.add("Payment Method "+PaymentMethod+" amount validated successfully in Summary View");
						}else {
							app_logs.info("Failed - Payment Method "+PaymentMethod+" amount in Summary View validation failed");
							test_steps.add("AssertionError : Failed - Payment Method "+PaymentMethod+" amount in Summary View validation failed");
						}
						}catch(Exception e) {
							app_logs.info("Failed to get Payments Method details");
							test_steps.add("Failed to get Payments Method details");
						}
						
					}
				}
				

				// app_logs.info("Detailed View: "+report.getDetailedViewDetails(driver,
				// test_steps));
				// app_logs.info("Detailed View: "+report.getDetailedViewDetailsLatest(driver,
				// test_steps));
				app_logs.info("Detailed View: " + report.getDetailedViewDetailsLatestWithHeaders(driver, test_steps));

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to valdate Summary view", testName, "LedgerReportsSummaryView", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to valdate Summary view", testName, "LedgerReportsSummaryView", driver);
				} else {

					Assert.assertTrue(false);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation details in Summary view after after Run report", testName,
						"RunReport-SummaryView", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation details in Summary view after Run report", testName,
						"RunReport-SummaryView", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		test_steps.add("====  <b>Detailed View Validation</b> =====");
		//Detailed View
		try {
			
			HashMap<String,ArrayList<String>> TransactionDetails = new HashMap<>();
			
			if (!Incidentals.isEmpty()) {

				TransactionDetails.clear();
				String[] strInc = Incidentals.split(",");

				if (strInc.length == 1) {
					TransactionDetails = report.getTransactionDetailsList(driver, Incidentals, test_steps);
					
					
					if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
						app_logs.info("Reservation number "+reservationNumbers+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Reservation number "+reservationNumbers+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failed - Reservation number "+reservationNumbers+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+Incidentals+" in Detailed View");
					}
					
					if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
						app_logs.info("Guest Name "+guestNames+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Guest Name "+guestNames+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failed, Guest Name "+guestNames+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+Incidentals+" in Detailed View");
					}
					
					if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
						app_logs.info("Arrival Date "+arrivalDates+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Arrival Date "+arrivalDates+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failed, Arrival Date "+arrivalDates+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+Incidentals+" in Detailed View");
					}
					
					if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
						app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Incidentals+" in Detailed View");
					}
					
					if (TransactionDetails.get("Item Description").contains(itemDescription.get(Incidentals))) {
						app_logs.info("Item Description "+itemDescription.get(Incidentals)+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Item Description "+itemDescription.get(Incidentals)+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failed, Item Description "+itemDescription.get(Incidentals)+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(Incidentals)+" is not available under "+Incidentals+" in Detailed View");
					}
					
					if (TransactionDetails.get("Amount").contains("$"+AmountIncidentals+".00")) {
						app_logs.info("Amount "+AmountIncidentals+" is available under "+Incidentals+" in Detailed View");
						test_steps.add("Amount "+AmountIncidentals+" is available under "+Incidentals+" in Detailed View");
					}else {
						app_logs.info("Failes, Amount "+AmountIncidentals+" is not available under "+Incidentals+" in Detailed View");
						test_steps.add("AssertionError - Failed, Amount "+AmountIncidentals+" is not available under "+Incidentals+" in Detailed View");
					}
					
				} else {
					for (int i = 0; i < strInc.length; i++) {
						
						TransactionDetails.clear();
						TransactionDetails = report.getTransactionDetailsList(driver, strInc[i], test_steps);
						
						if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
							app_logs.info("Reservation number "+reservationNumbers+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Reservation number "+reservationNumbers+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failed - Reservation number "+reservationNumbers+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+strInc[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
							app_logs.info("Guest Name "+guestNames+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Guest Name "+guestNames+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Guest Name "+guestNames+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+strInc[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
							app_logs.info("Arrival Date "+arrivalDates+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Arrival Date "+arrivalDates+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Arrival Date "+arrivalDates+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+strInc[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
							app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strInc[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Item Description").contains(itemDescription.get(strInc[i]))) {
							app_logs.info("Item Description "+itemDescription.get(strInc[i])+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Item Description "+itemDescription.get(strInc[i])+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Item Description "+itemDescription.get(strInc[i])+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(strInc[i])+" is not available under "+strInc[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Amount").contains("$"+AmountIncidentals+".00")) {
							app_logs.info("Amount "+AmountIncidentals+" is available under "+strInc[i]+" in Detailed View");
							test_steps.add("Amount "+AmountIncidentals+" is available under "+strInc[i]+" in Detailed View");
						}else {
							app_logs.info("Failes, Amount "+AmountIncidentals+" is not available under "+strInc[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Amount "+AmountIncidentals+" is not available under "+strInc[i]+" in Detailed View");
						}
						
					}
				}

			}

			if (!RoomCharges.isEmpty()) {

				TransactionDetails.clear();
				String[] strRm = RoomCharges.split(",");

				if (strRm.length == 1) {
					TransactionDetails = report.getTransactionDetailsList(driver, RoomCharges, test_steps);
					app_logs.info("Room Charge Transaction Details: "+TransactionDetails);
					
					if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
						app_logs.info("Reservation number "+reservationNumbers+" is available under "+RoomCharges+" in Detailed View");
						test_steps.add("Reservation number "+reservationNumbers+" is available under "+RoomCharges+" in Detailed View");
					}else {
						app_logs.info("Failed, Reservation number "+reservationNumbers+" is not available under "+RoomCharges+" in Detailed View");
						test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+RoomCharges+" in Detailed View");
					}
					
					if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
						app_logs.info("Guest Name "+guestNames+" is available under "+RoomCharges+" in Detailed View");
						test_steps.add("Guest Name "+guestNames+" is available under "+RoomCharges+" in Detailed View");
					}else {
						app_logs.info("Failed, Guest Name "+guestNames+" is not available under "+RoomCharges+" in Detailed View");
						test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+RoomCharges+" in Detailed View");
					}
					
					if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
						app_logs.info("Arrival Date "+arrivalDates+" is available under "+RoomCharges+" in Detailed View");
						test_steps.add("Arrival Date "+arrivalDates+" is available under "+RoomCharges+" in Detailed View");
					}else {
						app_logs.info("Failed, Arrival Date "+arrivalDates+" is not available under "+RoomCharges+" in Detailed View");
						test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+RoomCharges+" in Detailed View");
					}
					
					if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
						app_logs.info("Date "+Utility.getCurrentDate("MMM dd,yyyy")+" is available under "+RoomCharges+" in Detailed View");
						test_steps.add("Date "+Utility.getCurrentDate("MMM dd,yyyy")+" is available under "+RoomCharges+" in Detailed View");
					}else {
						app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+RoomCharges+" in Detailed View");
						test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+RoomCharges+" in Detailed View");
					}
					
					if (TransactionDetails.get("Item Description").contains(itemDescription.get(RoomCharges))) {
						app_logs.info("Item Description "+itemDescription.get(RoomCharges)+" is available under "+RoomCharges+" in Detailed View");
						test_steps.add("Item Description "+itemDescription.get(RoomCharges)+" is available under "+RoomCharges+" in Detailed View");
					}else {
						app_logs.info("Failed, Item Description "+itemDescription.get(RoomCharges)+" is not available under "+RoomCharges+" in Detailed View");
						test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(RoomCharges)+" is not available under "+RoomCharges+" in Detailed View");
					}
					
					if (RoomCharges.equalsIgnoreCase("Room Charge")) {
						if (TransactionDetails.get("Amount").contains("$"+roomChargeAmount+"0")) {
							app_logs.info("Amount "+roomChargeAmount+" is available under "+RoomCharges+" in Detailed View");
							test_steps.add("Amount "+roomChargeAmount+" is available under "+RoomCharges+" in Detailed View");
						}else {
							app_logs.info("Failed, Amount "+"$"+roomChargeAmount+"0"+" is not available under "+RoomCharges+" in Detailed View");
							test_steps.add("AssertionError - Failed, Amount "+"$"+roomChargeAmount+"0"+" is not available under "+RoomCharges+" in Detailed View");
						}
					}else {
						if (TransactionDetails.get("Amount").contains("$"+AmountRoomCharges+".00")) {
							app_logs.info("Amount "+AmountRoomCharges+" is available under "+RoomCharges+" in Detailed View");
							test_steps.add("Amount "+AmountRoomCharges+" is available under "+RoomCharges+" in Detailed View");
						}else {
							app_logs.info("Failed, Amount "+"$"+AmountRoomCharges+".00"+" is not available under "+RoomCharges+" in Detailed View");
							test_steps.add("AssertionError - Failed, Amount "+"$"+AmountRoomCharges+".00"+" is not available under "+RoomCharges+" in Detailed View");
						}
					}
					
				} else {
					for (int i = 0; i < strRm.length; i++) {
						
						TransactionDetails.clear();
						TransactionDetails = report.getTransactionDetailsList(driver, strRm[i], test_steps);
						
						if (TransactionDetails.get("Reservation #").containsAll(reservationNumbers)) {
							app_logs.info("Reservation number "+reservationNumbers+" is available under "+strRm[i]+" in Detailed View");
							test_steps.add("Reservation number "+reservationNumbers+" is available under "+strRm[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Reservation number "+reservationNumbers+" is not available under "+strRm[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Reservation number "+reservationNumbers+" is not available under "+strRm[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Guest/Account Name").containsAll(guestNames)) {
							app_logs.info("Guest Name "+guestNames+" is available under "+strRm[i]+" in Detailed View");
							test_steps.add("Guest Name "+guestNames+" is available under "+strRm[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Guest Name "+guestNames+" is not available under "+strRm[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Guest Name "+guestNames+" is not available under "+strRm[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Arrival Date").containsAll(arrivalDates)) {
							app_logs.info("Arrival Date "+arrivalDates+" is available under "+strRm[i]+" in Detailed View");
							test_steps.add("Arrival Date "+arrivalDates+" is available under "+strRm[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Arrival Date "+arrivalDates+" is not available under "+strRm[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Arrival Date "+arrivalDates+" is not available under "+strRm[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
							app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strRm[i]+" in Detailed View");
							test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strRm[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strRm[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strRm[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Item Description").contains(itemDescription.get(strRm[i]))) {
							app_logs.info("Item Description "+itemDescription.get(strRm[i])+" is available under "+strRm[i]+" in Detailed View");
							test_steps.add("Item Description "+itemDescription.get(strRm[i])+" is available under "+strRm[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Item Description "+itemDescription.get(strRm[i])+" is not available under "+strRm[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(strRm[i])+" is not available under "+strRm[i]+" in Detailed View");
						}
						
						if (strRm[i].equalsIgnoreCase("Room Charge")) {
							if (TransactionDetails.get("Amount").contains("$"+roomChargeAmount+"0")) {
								app_logs.info("Amount "+roomChargeAmount+" is available under "+strRm[i]+" in Detailed View");
								test_steps.add("Amount "+roomChargeAmount+" is available under "+strRm[i]+" in Detailed View");
							}else {
								app_logs.info("Failed, Amount "+"$"+roomChargeAmount+"0"+" is not available under "+strRm[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Amount "+"$"+roomChargeAmount+"0"+" is not available under "+strRm[i]+" in Detailed View");
							}
						}else {
							if (TransactionDetails.get("Amount").contains("$"+AmountRoomCharges+".00")) {
								app_logs.info("Amount "+AmountRoomCharges+" is available under "+strRm[i]+" in Detailed View");
								test_steps.add("Amount "+AmountRoomCharges+" is available under "+strRm[i]+" in Detailed View");
							}else {
								app_logs.info("Failed, Amount "+"$"+AmountRoomCharges+".00"+" is not available under "+strRm[i]+" in Detailed View");
								test_steps.add("AssertionError - Failed, Amount "+"$"+AmountRoomCharges+".00"+" is not available under "+strRm[i]+" in Detailed View");
							}
						}

						
					}
				}

			}

			if (!Taxes.isEmpty()) {

				TransactionDetails.clear();
				String[] strTax = Taxes.split(",");

				if (strTax.length == 1) {
					TransactionDetails = report.getTransactionDetailsList(driver, Taxes, test_steps);
					
					if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
						app_logs.info("Reservation number "+reservationNumber+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Reservation number "+reservationNumber+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Reservation number "+reservationNumber+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError - Failed, Reservation number "+reservationNumber+" is not available under "+Taxes+" in Detailed View");
					}
					
					if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
						app_logs.info("Guest Name "+guestName+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Guest Name "+guestName+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Guest Name "+guestName+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError - Failed, Guest Name "+guestName+" is not available under "+Taxes+" in Detailed View");
					}
					
					if (TransactionDetails.get("Arrival Date").contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
						app_logs.info("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError -Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+Taxes+" in Detailed View");
					}
					
					if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
						app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError -Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Taxes+" in Detailed View");
					}
					
					if (TransactionDetails.get("Item Description").contains(itemDescription.get(Taxes))) {
						app_logs.info("Item Description "+itemDescription.get(Taxes)+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Item Description "+itemDescription.get(Taxes)+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Item Description "+itemDescription.get(Taxes)+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(Taxes)+" is not available under "+Taxes+" in Detailed View");
					}
					
					if (TransactionDetails.get("Amount").contains("$"+AmountTaxes+".00")) {
						app_logs.info("Amount "+AmountTaxes+" is available under "+Taxes+" in Detailed View");
						test_steps.add("Amount "+AmountTaxes+" is available under "+Taxes+" in Detailed View");
					}else {
						app_logs.info("Failed, Amount "+AmountTaxes+" is not available under "+Taxes+" in Detailed View");
						test_steps.add("AssertionError -Failed, Amount "+AmountTaxes+" is not available under "+Taxes+" in Detailed View");
					}
					
					
				} else {
					for (int i = 0; i < strTax.length; i++) {
						
						TransactionDetails.clear();
						TransactionDetails = report.getTransactionDetailsList(driver, strTax[i], test_steps);
						
						if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
							app_logs.info("Reservation number "+reservationNumber+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Reservation number "+reservationNumber+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Reservation number "+reservationNumber+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Reservation number "+reservationNumber+" is not available under "+strTax[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
							app_logs.info("Guest Name "+guestName+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Guest Name "+guestName+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Guest Name "+guestName+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Guest Name "+guestName+" is not available under "+strTax[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Arrival Date").contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
							app_logs.info("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+strTax[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
							app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strTax[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Item Description").contains(itemDescription.get(strTax[i]))) {
							app_logs.info("Item Description "+itemDescription.get(strTax[i])+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Guest Name "+itemDescription.get(strTax[i])+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Item Description "+itemDescription.get(strTax[i])+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(strTax[i])+" is not available under "+strTax[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Amount").contains("$"+AmountTaxes+".00")) {
							app_logs.info("Amount "+AmountTaxes+" is available under "+strTax[i]+" in Detailed View");
							test_steps.add("Amount "+AmountTaxes+" is available under "+strTax[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Amount "+AmountTaxes+" is not available under "+strTax[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Amount "+AmountTaxes+" is not available under "+strTax[i]+" in Detailed View");
						}
						
					}
				}

			}

			if (!Fees.isEmpty()) {

				TransactionDetails.clear();
				String[] strFee = Fees.split(",");

				if (strFee.length == 1) {
					TransactionDetails = report.getTransactionDetailsList(driver, Fees, test_steps);
					
					if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
						app_logs.info("Reservation number "+reservationNumber+" is available under "+Fees+" in Detailed View");
						test_steps.add("Reservation number "+reservationNumber+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Reservation number "+reservationNumber+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Reservation number "+reservationNumber+" is not available under "+Fees+" in Detailed View");
					}
					
					if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
						app_logs.info("Guest Name "+guestName+" is available under "+Fees+" in Detailed View");
						test_steps.add("Guest Name "+guestName+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Guest Name "+guestName+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Guest Name "+guestName+" is not available under "+Fees+" in Detailed View");
					}
					
					if (TransactionDetails.get("Arrival Date").contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
						app_logs.info("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+Fees+" in Detailed View");
						test_steps.add("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+Fees+" in Detailed View");
					}
					
					if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
						app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Fees+" in Detailed View");
						test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+Fees+" in Detailed View");
					}
					
					if (TransactionDetails.get("Item Description").contains(itemDescription.get(Fees))) {
						app_logs.info("Item Description "+itemDescription.get(Fees)+" is available under "+Fees+" in Detailed View");
						test_steps.add("Guest Name "+itemDescription.get(Fees)+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Item Description "+itemDescription.get(Fees)+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(Fees)+" is not available under "+Fees+" in Detailed View");
					}
					
					if (TransactionDetails.get("Amount").contains("$"+AmountFees+".00")) {
						app_logs.info("Amount "+AmountFees+" is available under "+Fees+" in Detailed View");
						test_steps.add("Amount "+AmountFees+" is available under "+Fees+" in Detailed View");
					}else {
						app_logs.info("Failed, Amount "+AmountFees+" is not available under "+Fees+" in Detailed View");
						test_steps.add("AssertionError - Failed, Amount "+AmountFees+" is not available under "+Fees+" in Detailed View");
					}
					
				} else {
					for (int i = 0; i < strFee.length; i++) {
						
						TransactionDetails.clear();
						TransactionDetails = report.getTransactionDetailsList(driver, strFee[i], test_steps);
						
						if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
							app_logs.info("Reservation number "+reservationNumber+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Reservation number "+reservationNumber+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Reservation number "+reservationNumber+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Reservation number "+reservationNumber+" is not available under "+strFee[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
							app_logs.info("Guest Name "+guestName+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Guest Name "+guestName+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Guest Name "+guestName+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Guest Name "+guestName+" is not available under "+strFee[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Arrival Date").contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
							app_logs.info("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+strFee[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
							app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+strFee[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Item Description").contains(itemDescription.get(strFee[i]))) {
							app_logs.info("Item Description "+itemDescription.get(strFee[i])+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Guest Name "+itemDescription.get(strFee[i])+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Item Description "+itemDescription.get(strFee[i])+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Item Description "+itemDescription.get(strFee[i])+" is not available under "+strFee[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get("Amount").contains("$"+AmountFees+".00")) {
							app_logs.info("Amount "+AmountFees+" is available under "+strFee[i]+" in Detailed View");
							test_steps.add("Amount "+AmountFees+" is available under "+strFee[i]+" in Detailed View");
						}else {
							app_logs.info("Failed, Amount "+AmountFees+" is not available under "+strFee[i]+" in Detailed View");
							test_steps.add("AssertionError - Failed, Amount "+AmountFees+" is not available under "+strFee[i]+" in Detailed View");
						}
						
					}
				}

			}
			if (!PaymentMethod.isEmpty()) {
				TransactionDetails.clear();
				TransactionDetails = report.getTransactionDetailsList(driver, PaymentMethod, test_steps);
				
				if (TransactionDetails.get("Reservation #").contains(reservationNumber)) {
					app_logs.info("Reservation number "+reservationNumber+" is available under "+PaymentMethod+" in Detailed View");
					test_steps.add("Reservation number "+reservationNumber+" is available under "+PaymentMethod+" in Detailed View");
				}else {
					app_logs.info("Failed, Reservation number "+reservationNumber+" is not available under "+PaymentMethod+" in Detailed View");
					test_steps.add("AssertionError - Failed, Reservation number "+reservationNumber+" is not available under "+PaymentMethod+" in Detailed View");
				}
				
				if (TransactionDetails.get("Guest/Account Name").contains(guestName)) {
					app_logs.info("Guest Name "+guestName+" is available under "+PaymentMethod+" in Detailed View");
					test_steps.add("Guest Name "+guestName+" is available under "+PaymentMethod+" in Detailed View");
				}else {
					app_logs.info("Failed, Guest Name "+guestName+" is not available under "+PaymentMethod+" in Detailed View");
					test_steps.add("AssertionError - Failed, Guest Name "+guestName+" is not available under "+PaymentMethod+" in Detailed View");
				}
				
				if (TransactionDetails.get("Arrival Date").contains(Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy"))) {
					app_logs.info("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+PaymentMethod+" in Detailed View");
					test_steps.add("Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is available under "+PaymentMethod+" in Detailed View");
				}else {
					app_logs.info("Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+PaymentMethod+" in Detailed View");
					test_steps.add("AssertionError - Failed, Arrival Date "+Utility.parseDate(CheckInDate, "dd/MM/yyyy", "MMM dd, yyyy")+" is not available under "+PaymentMethod+" in Detailed View");
				}
				
				if (TransactionDetails.get("Date").contains(Utility.getCurrentDate("MMM dd, yyyy"))) {
					app_logs.info("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+PaymentMethod+" in Detailed View");
					test_steps.add("Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is available under "+PaymentMethod+" in Detailed View");
				}else {
					app_logs.info("Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+PaymentMethod+" in Detailed View");
					test_steps.add("AssertionError - Failed, Date "+Utility.getCurrentDate("MMM dd, yyyy")+" is not available under "+PaymentMethod+" in Detailed View");
				}
				
				if (TransactionDetails.get("Amount").contains("$"+FolioTripTotal)) {
					app_logs.info("Amount "+FolioTripTotal+" is available under "+PaymentMethod+" in Detailed View");
					test_steps.add("Amount "+FolioTripTotal+" is available under "+PaymentMethod+" in Detailed View");
				}else {
					app_logs.info("Failed, Amount "+FolioTripTotal+" is not available under "+PaymentMethod+" in Detailed View");
					test_steps.add("AssertionError - Failed, Amount "+FolioTripTotal+" is not available under "+PaymentMethod+" in Detailed View");
				}
			}

			
			
			
			/*
			for (int i = 0; i < options.length; i++) {
				if (!options[i].isEmpty()) {
					if (options[i].split(",").length == 1) {
						TransactionDetails = report.getTransactionDetailsList(driver, inputs[i], unitOwnerItems);
						
						if (TransactionDetails.get(options[i]).contains(reservationNumber)) {
							app_logs.info("Reservation number "+reservationNumber+" is available under "+options[i]+" in Detailed View");
						}
						
						if (TransactionDetails.get(options[i]).contains(guestName)) {
							app_logs.info("Guest name "+guestName+" is available under "+options[i]+" in Detailed View");
						}
						
						
						
					}else {
						
						
					}
				}
			}*/
			

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
			
			//driver.close();
			//Utility.switchTab(driver, 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation details in Detailed view after after Run report", testName,
						"RunReport-DetailedView", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to validate Reservation details in Detailed view after after Run report", testName,
						"RunReport-DetailedView", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ReportsV2LedgerWithReservations", excel);
		// CP_CreateReservation
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}
