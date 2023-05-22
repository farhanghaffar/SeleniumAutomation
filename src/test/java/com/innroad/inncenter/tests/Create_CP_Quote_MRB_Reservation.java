package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class Create_CP_Quote_MRB_Reservation extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
	
	
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, HS_EXCEL))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void create_CP_Quote_MRB_Reservation(String TestCaseID, String ClientCode, String Username, String Password,String PropertyName,String CheckInDate,String CheckOutDate,String Adults,String Children,String Rateplan,String PromoCode,String IsSplitRes,
			String RoomClass,String IsAssign,String IsDepositOverride,String DepositOverrideAmount,String IsAddMoreGuestInfo,String Salutation,String GuestFirstName,String GuestLastName,String PhoneNumber,String AltenativePhone,String Email,String Account,String AccountType,
			String Address1,String Address2,String Address3,String City,String Country,String State,String PostalCode,String IsGuesProfile,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,String IsChangeInPayAmount,String ChangedAmountValue,
			String TravelAgent,String MarketSegment,String Referral,String IsAddNotes,String NoteType,String Subject,String Description,
			String IsTask, String TaskCategory, String TaskType,String TaskDetails,String TaskRemarks,String TaskDueon,String TaskAssignee,String TaskStatus,String RoomClassEditSearched) throws InterruptedException, IOException {
String backupRoomClass = RoomClass;
		test_name = "CP MRB Quote Reservation Creation";
		test_description = "Verify the behavior of room details and guest details when editing an existing selected room for multi-room reservation"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824932' target='_blank'>"
				+ "Click here to open TestRail: C824932</a><br/>"
				
				+"Verify that a user can access room search, where they can redo search to search for multi-room reservation after pressed edit button in Trip Summary when reservation created"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825015' target='_blank'>"
				+ "Click here to open TestRail: C825015</a><br/>"
				
				+ "Verify when there are Unavailable Room Classes and Rooms while Booking a Quote"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825027' target='_blank'>"
				+ "Click here to open TestRail: C825027</a><br/>"
				
				+ "Verify user does not have an option to copy a multi room booking reservation from an existing multi room booking reservation"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/825138' target='_blank'>"
				+ "Click here to open TestRail: C825138</a><br/>"
				
				+ "Verify that user is able to access an existing multi-room reservation from tapechart."
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/824929' target='_blank'>"
				+ "Click here to open TestRail: C824929</a><br/>"
				
+ "Verify that Multi Room Booking for quote should be initiated by BOOK button."
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848392' target='_blank'>"
+ "Click here to open TestRail: 848392</a><br/>"

+ "Create a multi-room reservation quote from a new reservation."
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848403' target='_blank'>"
+ "Click here to open TestRail: 848403</a><br/>"

+ "Verify booking of multi room quote."
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848470' target='_blank'>"
+ "Click here to open TestRail: 848470</a><br/>"

+ "Create Quote reservation"
+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848124' target='_blank'>"
+ "Click here to open TestRail: 848124</a><br/>"
				;
		
		test_catagory = "CP_MRB_Quote_Reservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		if(Utility.getResultForCase(driver, TestCaseID)) {
		Tapechart tapechart = new Tapechart();
		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		//RoomClass rc = new RoomClass();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		Double depositAmount=0.0;
		Double paidDeposit=0.0;
		String reservation=null;
		String status=null;
		String timeZone=null;
		String TripSummaryRoomCharges=null,TripSummaryTaxes=null,TripSummaryIncidentals=null,TripSummaryTripTotal=null,TripSummaryPaid=null,TripSummaryBalance=null;
		String FilioRoomCharges=null,FilioTaxes=null,FilioIncidentals=null,FilioTripTotal=null,FilioPaid=null,FilioBalance=null;
		ArrayList<String> Rooms = new ArrayList<String>();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		ArrayList<String> roomCost = new ArrayList<String>();
		
		

		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1); System.out.println(Utility.reTry.get(testName));
			}
			Utility.initializeTestCase(TestCaseID, caseId, statusCode, comments, "785572");
			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			
			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1) ;
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			TaskDueon = checkOutDates.get(0);
			
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}

		// Login
		try {
			driver = getDriver();
//			loginRateV2(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "RateV2_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
		
		try {
			if(IsAddNotes.equalsIgnoreCase("Yes")) {
			nav.Setup(driver);
			nav.Properties(driver);
			System.out.println(PropertyName);
			nav.open_Property(driver, test_steps, PropertyName);
			nav.click_PropertyOptions(driver, test_steps);
			timeZone=nav.get_Property_TimeZone(driver);
			nav.Reservation_Backward(driver);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nav.Setup(driver);
			nav.RoomClass(driver);
			RoomAbbri1 = rc.getAbbrivation(driver, "|", RoomClass, test_steps);
			String[] Room= (RoomClass).split("\\|");
			
			
			for(int i=0;i<Room.length;i++) {
			RoomAbbri.add(i, Room[i]+":"+RoomAbbri1.get(i));
			
			}
			
			Utility.app_logs.info(RoomAbbri);
			System.out.println(RoomAbbri);
			nav.cpReservation_Backward(driver);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to get room class abbrivations", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		// Reservation
		try {
			res.click_NewReservation(driver, test_steps);
			res.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
			if(IsSplitRes.equalsIgnoreCase("Yes")) {
				res.enter_Adults(driver, test_steps, Adults);
				res.enter_Children(driver, test_steps, Children);
				res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			}
			res.clickOnFindRooms(driver, test_steps);
			roomCost=res.select_MRBRooms(driver, test_steps, RoomClass, IsAssign,Account);
			Utility.ScrollToUp(driver);
			
			res.editSelectedRoomClick(driver, 1, test_steps);
			res.verifyFindRoomHeader(driver, "Room 1 of 2", test_steps);
			roomCost=res.select_MRBRooms(driver, test_steps, RoomClassEditSearched, IsAssign,Account);
			res.verifyOverbookingTab(driver, RoomClassEditSearched.split("\\|")[1]);
			RoomClass = RoomClassEditSearched;
			depositAmount=res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			ArrayList<String> GuestFirstNameList = Utility.convertTokenToArrayList(GuestFirstName, "\\|");
			ArrayList<String> GuestLastNameList = Utility.convertTokenToArrayList(GuestLastName, "\\|");
			String tempGuestFirstName = "";
			String tempGuestLastName = "";
			for(int i=0; i<GuestFirstNameList.size(); i++) {
				tempGuestFirstName += GuestFirstNameList.get(i) + Utility.generateRandomString();
				if((i+1)!=GuestFirstNameList.size()) {
					tempGuestFirstName +="|";
				}
				tempGuestLastName += GuestLastNameList.get(i) + Utility.generateRandomString();
				if((i+1)!=GuestLastNameList.size()) {
					tempGuestLastName +="|";
				}
			}
			
			String GuestFirstNametmp = tempGuestFirstName;
			String GuestLastNametmp = tempGuestLastName;
			res.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstNametmp, GuestLastNametmp, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes,Rooms);
			if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			System.out.println(Rooms);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
			res.click_Quote(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			status=res.get_ReservationStatus(driver, test_steps);
			res.verify_QuoteConfirmetionPopup(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.verify_QuoteStauts(driver, test_steps, status);
			res.get_RoomNumber(driver, test_steps, IsAssign);	
			
			getTest_Steps.clear();
			getTest_Steps = res.verifyCopyButtonState(driver, "MRB Reservation");
			test_steps.addAll(getTest_Steps);
			
			TripSummaryRoomCharges=res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			TripSummaryTaxes=res.get_TripSummaryTaxesWithCurrency(driver, test_steps);
			TripSummaryIncidentals=res.get_TripSummaryInceidentalsWithCurrency(driver, test_steps);
			TripSummaryTripTotal=res.getTripSummaryTripTotal(driver, test_steps);
			TripSummaryPaid=res.get_TripSummaryPaidWithCurrency(driver, test_steps);
			TripSummaryBalance=res.get_TripSummaryBalanceWithCurrency(driver, test_steps);
			res.verify_MRB_StayInfo(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, RoomClass, TripSummaryRoomCharges,roomCost,IsAssign,Rateplan);
			res.validate_MRB_GuestInfo(driver, test_steps, Salutation, GuestFirstNametmp, GuestLastNametmp, PhoneNumber, AltenativePhone, Email, Country, Account, Address1, Address2, Address3, State, City, PostalCode);
			res.validate_MRB_AdditionalGuestInfo(driver, test_steps, Salutation, GuestFirstNametmp, GuestLastNametmp, PhoneNumber, Email, Country);
			res.verify_UpdatedByUser(driver, test_steps, Utility.login_CP.get(2));
			res.verify_UpdatedByUserTime(driver, test_steps, timeZone);
			res.click_Folio(driver, test_steps);
			FilioRoomCharges=res.get_RoomChargeWithCurrency(driver, test_steps);
			FilioIncidentals=res.get_InceidentalsWithCurrency(driver, test_steps);
			FilioTaxes=res.get_TaxesWithCurrency(driver, test_steps);
			FilioTripTotal= res.get_TotalChargesWithCurrency(driver, test_steps);
			FilioPaid=res.get_PaymentsWithCurrency(driver, test_steps);
			FilioBalance=res.get_BalanceWithCurrency(driver, test_steps);
			res.verify_MRB_BannerDetails(driver, test_steps, Salutation, GuestFirstNametmp, GuestLastNametmp, PhoneNumber, Email, TripSummaryTripTotal, TripSummaryBalance, reservation, status, CheckInDate, CheckOutDate, Country);
			String payment=FilioPaid;
			FilioPaid=FilioPaid.trim();
			char ch=FilioPaid.charAt(0);
			FilioPaid=FilioPaid.replace("$", "");
			FilioPaid=FilioPaid.trim();
			paidDeposit = Double.parseDouble(FilioPaid);

			/*if(depositAmount>0) {
				if(Double.compare(paidDeposit, depositAmount)==0) {
					test_steps.add("Deposit paid amount is validated : "+ch+" "+paidDeposit);
					app_logs.info("Deposit paid amount is validated : "+ch+" "+paidDeposit);
				}
			}*/
			
			int size=RoomClass.split("\\|").length;
			if(!IsSplitRes.equalsIgnoreCase("Yes")&&size>1) {
				res.verify_MRB_Folio(driver, test_steps, RoomAbbri, IsAssign, Rooms);
			}
			res.click_History(driver, test_steps);
			res.verify_QuoteInHistoryTab(driver, test_steps, reservation,timeZone);
			/*if(depositAmount>0) {
				res.verify_DepositPaymentInHistoryTab(driver, test_steps, depositAmount);
			}*/	
			//res.velidate_TripSummaryAndFolio(driver, test_steps, FilioRoomCharges, FilioTaxes, FilioIncidentals, FilioTripTotal, payment, FilioBalance, TripSummaryRoomCharges, TripSummaryTaxes, TripSummaryIncidentals, TripSummaryTripTotal, TripSummaryPaid, TripSummaryBalance);
//			statusCode="1";
			res.verify_GuestReportLabelsValidations(driver, test_steps);
			
			res.close_FirstOpenedReservation(driver, test_steps);
			
			res.Search_ResNumber_And_Click(driver, reservation);
			try {
			res.clickBook(driver, test_steps);
			}catch (Exception e) {
				driver.navigate().refresh();
				res.clickBook(driver, test_steps);
			}
//			reservationPage.
			res.closeModalPopup(driver, test_steps);
			Wait.wait10Second();
			Wait.wait10Second();
			res.clickBookQuote(driver, Rateplan, test_steps);
			Wait.wait10Second();
			Wait.wait10Second();
			 status = res.get_ReservationStatus(driver, test_steps);
			Utility.customAssert(status.toUpperCase(), "RESERVED", true, 
					"Successfully Verified RESERVED", "Failed to Verified RESERVED", true, test_steps);
			
			res.close_FirstOpenedReservation(driver, test_steps);
//			nav.TapeChart(driver);
//			ArrayList<String> checkInDateList = Utility.convertTokenToArrayList(CheckInDate, "\\|");
//			ArrayList<String> checkOutDateList = Utility.convertTokenToArrayList(CheckOutDate, "\\|");
//			ArrayList<String> AdultsList = Utility.convertTokenToArrayList(Adults, "\\|");
//			ArrayList<String> ChildrenList = Utility.convertTokenToArrayList(Children, "\\|");
//			ArrayList<String> RateplanList = Utility.convertTokenToArrayList(Rateplan, "\\|");
//			ArrayList<String> PromoCodeList = Utility.convertTokenToArrayList(PromoCode, "\\|");
//			GuestFirstNameList = Utility.convertTokenToArrayList(GuestFirstName, "\\|");
//			
//			for(int i=0; i<checkInDateList.size(); i++) {
//			
//			tapechart.searchInTapechart(driver, test_steps, checkInDateList.get(i), checkOutDateList.get(i), AdultsList.get(i), ChildrenList.get(i), RateplanList.get(i), PromoCode);
//			tapechart.verifyBookedReservationWithName(driver, GuestFirstNameList.get(i), false, test_steps);
//			}
//			
//ArrayList<String> list =  Utility.convertTokenToArrayList(TestCaseID, "\\|");
//			
//			for(int i=0; i<list.size(); i++) {
//				statusCode.add(i,"1");
//				comments.add(i,"Success");
//			}
//			
//			RetryFailedTestCases.count = Utility.reset_count;
//			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		// Reservation
				try {
					RoomClass = backupRoomClass;
					res.click_NewReservation(driver, test_steps);
					res.select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, PromoCode,IsSplitRes);
					if(IsSplitRes.equalsIgnoreCase("Yes")) {
						res.enter_Adults(driver, test_steps, Adults);
						res.enter_Children(driver, test_steps, Children);
						res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
					}
					res.clickOnFindRooms(driver, test_steps);
					roomCost=res.select_MRBRooms(driver, test_steps, RoomClass, IsAssign,Account);
					
				
					depositAmount=res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
					res.clickNext(driver, test_steps);
					ArrayList<String> GuestFirstNameList = Utility.convertTokenToArrayList(GuestFirstName, "\\|");
					ArrayList<String> GuestLastNameList = Utility.convertTokenToArrayList(GuestLastName, "\\|");
					String tempGuestFirstName = "";
					String tempGuestLastName = "";
					for(int i=0; i<GuestFirstNameList.size(); i++) {
						tempGuestFirstName += GuestFirstNameList.get(i) + Utility.generateRandomString();
						if((i+1)!=GuestFirstNameList.size()) {
							tempGuestFirstName +="|";
						}
						tempGuestLastName += GuestLastNameList.get(i) + Utility.generateRandomString();
						if((i+1)!=GuestLastNameList.size()) {
							tempGuestLastName +="|";
						}
					}
					
					GuestFirstName = tempGuestFirstName;
					GuestLastName = tempGuestLastName;
					res.enter_MRB_MailingAddress(driver, test_steps, Salutation, GuestFirstName, GuestLastName, PhoneNumber, AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State, PostalCode, IsGuesProfile, IsAddMoreGuestInfo, IsSplitRes,Rooms);
					if((Account.equalsIgnoreCase("")||Account.isEmpty())) {
						res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
					}
					System.out.println(Rooms);
					res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);
					res.clickBookNow(driver, test_steps);
					reservation = res.get_ReservationConfirmationNumber(driver, test_steps);
					res.clickCloseReservationSavePopup(driver, test_steps);
					String statusAfterRes = res.get_ReservationStatus(driver, test_steps);
					Utility.customAssert(statusAfterRes.toUpperCase(), status.toUpperCase(), true, 
							"Successfully Verified Status After Reservation : " + status, "Failed to Verified Status After Reservation : " + statusAfterRes, true, test_steps);
					
					
					res.close_FirstOpenedReservation(driver, test_steps);
					
					nav.navTapeChart(driver, test);
					ArrayList<String> checkInDateList = Utility.convertTokenToArrayList(CheckInDate, "\\|");
					ArrayList<String> checkOutDateList = Utility.convertTokenToArrayList(CheckOutDate, "\\|");
					ArrayList<String> AdultsList = Utility.convertTokenToArrayList(Adults, "\\|");
					ArrayList<String> ChildrenList = Utility.convertTokenToArrayList(Children, "\\|");
					ArrayList<String> RateplanList = Utility.convertTokenToArrayList(Rateplan, "\\|");
					ArrayList<String> PromoCodeList = Utility.convertTokenToArrayList(PromoCode, "\\|");
					GuestFirstNameList = Utility.convertTokenToArrayList(GuestFirstName, "\\|");
					
					for(int i=0; i<checkInDateList.size(); i++) {
					
					tapechart.searchInTapechart(driver, test_steps, checkInDateList.get(i), checkOutDateList.get(i), AdultsList.get(i), ChildrenList.get(i), RateplanList.get(i), PromoCode);
					tapechart.verifyBookedReservationWithName(driver, GuestFirstNameList.get(i), false, test_steps);
					}
					
		ArrayList<String> list =  Utility.convertTokenToArrayList(TestCaseID, "\\|");
					
		caseId = new ArrayList<String>();
		statusCode = new ArrayList<String>();
		comments = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			caseId.add(list.get(i));
			statusCode.add("1");
			comments.add("PASS : " + this.getClass().getSimpleName().trim());
		}
					
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("Create_CP_Quote_MRB_Reservation", HS_EXCEL);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
//		Map data = new HashMap();
//		data.put("status_id",statusCode);
//		client.sendPost("add_result_for_case/"+TestCore.suite_id+"/"+caseId,data);	
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
		//driver.quit();
	}

}
