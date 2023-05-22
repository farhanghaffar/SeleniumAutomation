package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifySettlementPopupOnSecondGuestCheckOut extends TestCore
{
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	Rate rate = new Rate();
	Policies policies = new Policies();
	Folio folio = new Folio();
	CPReservationPage reservationPage = new CPReservationPage();
	ArrayList<String> roomNumberAssigned = new ArrayList<>();
	ArrayList<String> roomAbbri = new ArrayList<String>();
	ArrayList<String> roomNumber = new ArrayList<>();	
	List<String> roomNos = new ArrayList<String>();
	RoomClass roomClass = new RoomClass();
	NewRoomClassesV2 newRoomclass= new NewRoomClassesV2();
	String    roomClassAbbs = null,
			 rateNameWithoutNum = null, randomNum = null,
			rateName = null, yearDate = null, reservation = null, policyNameWithoutNum=null,policyname=null,
					balanceAmount=null,testName=null;			
	
	String seasonStartDate=null,seasonEndDate=null,confirmationNo=null,roomClassNameNew=null,roomChargeAmount = null,roomChargeAmountSecondGuest = null;
	ArrayList<String> roomClassNames = new ArrayList<>();
	ArrayList<String> roomClassAbb = new ArrayList<>();
	ArrayList<String> rateplan = new ArrayList<>();
	RatesGrid rateGrid = new RatesGrid();
	HashMap<String, String> roomChangeFolioBased= new HashMap<String, String>();
			
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void cPVerifyCheckinSecondaryGuest(String testCaseID,String roomClassName, String maxAdults, String maxPersopns,
			String roomQuantity, String policyName, String typeOfFees, String percentage, String chargesTypes,
			String ratePlanName, String rate, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, String paymentType, String cardNumber,
			String nameOnCard, String marketSegment, String referral, String action) throws ParseException {
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "VerifySettlementPopupOnSecondGuestCheckOut";
		test_description = "VerifySettlementPopupOnSecondGuestCheckOut<br>"				
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848563' target='_blank'>"
				+ "Click here to open TestRail: 848563</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848430' target='_blank'>"
				+ "Click here to open TestRail: 848430</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848455' target='_blank'>"
				+ "Click here to open TestRail: 848455</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848429' target='_blank'>"
				+ "Click here to open TestRail: 848429</a><br>";
		test_catagory = "CP";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase(testCaseID, Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		 NightlyRate nightlyRate= new NightlyRate();
		 Policies policies = new Policies();
		 NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
		 List<String> roomNos= new ArrayList<String>();
		 ArrayList<String> roomNumbers= new ArrayList<String>();
		 HashMap<String, ArrayList<String>> polictiesNames = new HashMap<String, ArrayList<String>>();
		 
		 HashMap<String, String> roomChargesOfSecondGuest= new HashMap<String, String>();
			List<String> roomCharges = new ArrayList<String>();
		// Login
			try {

				if (!(Utility.validateInput(checkInDate))) {
					for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
						sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
					}
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
				seasonStartDate=checkInDates.get(0);
				seasonEndDate=sessionEndDate.get(0);
				app_logs.info(checkInDate);
				app_logs.info(checkOutDate);
				app_logs.info(seasonStartDate);
				app_logs.info(seasonEndDate);
				rateplan=Utility.splitInputData(ratePlanName);
				app_logs.info(rateplan);

				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);
				} else {
					//Utility.reTry.replace(testName, 1);
					Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
				}
				datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDates.get(0), checkOutDates.get(0));
				app_logs.info(datesRangeList);
				driver = getDriver();
				login_CP(driver);
				test_steps.add("Logged into the application");
				app_logs.info("Logged into the application");
			} catch (Exception e) {
				Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
						test_catagory, test_steps);
			}
			// Create Room Class
			try {
				roomClassNames.clear();
				roomClassAbb.clear();
				test_steps.add("<b>======Start Creating New Room Class======</b>");
				app_logs.info("======Start Creating New Room Class======");
				navigation.clickSetup(driver);
				navigation.RoomClass(driver);
				test_steps.add("Navigated to Room Class");
				ArrayList<String>roomClassAre = Utility.splitInputData(roomClassName);
				for(int i=0;i<roomClassAre.size();i++) {
					String nameofRoomClass=roomClassAre.get(i)+Utility.threeDigitgenerateRandomString();
					String abbofRoomClass="RC"+i+1;
					roomClassNames.add(nameofRoomClass);
					roomClassAbb.add(abbofRoomClass);
					newRoomClass.createRoomClassV2(driver, test_steps, nameofRoomClass, abbofRoomClass, maxAdults, maxPersopns,
						roomQuantity);
				newRoomClass.closeRoomClassTabV2(driver, nameofRoomClass);
				}
				test_steps.add("Room Class Created: <b>" + roomClassNames + "</>");
				app_logs.info("Room Class Created: " + roomClassNames);
				app_logs.info("Room Class Abb: " + roomClassAbb);
				roomClassNameNew=roomClassNames.get(0)+"|"+roomClassNames.get(1);
				app_logs.info("Room Class : " + roomClassNameNew);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
						test_catagory, test_steps);
			}

			try {
				
					
				test_steps.add("<b>======Start Creating Policy ======</b>");
				navigation.inventoryFromRoomClass(driver, test_steps);
				navigation.policies(driver, test_steps);
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				policyName = policyName + Utility.generateRandomStringWithoutNumbers();
				polictiesNames = policies.createPolicies(driver, test_steps, "", "", "Deposit", "", policyName,
							"", "", typeOfFees, percentage, chargesTypes, "", "", "No", "");
				app_logs.info(polictiesNames);
				app_logs.info(policyName);
			} catch (Exception e) {		
				Utility.catchException(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
						test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Policy", "Inventory", "Inventory", testName, test_description,
						test_catagory, test_steps);
			}			
			// Create Season
			try {
				test_steps.add("<b>======Start Updating Rate Plan ======</b>");
				nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, rateplan.get(0),datesRangeList,
						seasonStartDate, seasonEndDate, "", roomClassNameNew, rate, "", 
						"", "", "", false);
				Utility.refreshPage(driver);
				nightlyRate.switchCalendarTab(driver, test_steps);
				nightlyRate.selectSeasonDates(driver, test_steps, datesRangeList.get(0));
				nightlyRate.clickEditThisSeasonButton(driver, test_steps);
				nightlyRate.clickSeasonPolicies(driver, test_steps);
				nightlyRate.selectPolicy(driver, "Deposit", policyName, true, test_steps);
				nightlyRate.clickSaveSason(driver, test_steps);
				nightlyRate.clickSaveRatePlanButton(driver, test_steps);
				rateGrid.closeRatePlan(driver, test_steps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
			} catch (Exception e) {
				Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
						test_catagory, test_steps);
			}
			
			try {
				test_steps.add("<b>======Start Creating Reservation ======</b>");
				app_logs.info("======Start Creating Reservation ======");
				confirmationNo=reservationPage.createBasicMRBReservation(driver, true, checkInDate, checkOutDate, 
						adults, children, ratePlanName, roomClassNameNew, salutation, guestFirstName, guestLastName, 
						"No", roomNumbers, paymentType, cardNumber, nameOnCard, referral, 
						false, test_steps);
				roomNos= reservationPage.getStayInfoRoomNo(driver, test_steps);
				app_logs.info(roomNumbers);		
				app_logs.info(roomNos);		
				roomCharges = reservationPage.getStayInfoRoomCharges(driver, test_steps);
				test_steps.add("<b>======Get Folio Line Room Charges  ======</b>");
				app_logs.info("======Get Folio Line Room Charges  ======");
				reservationPage.click_Folio(driver, test_steps);
				Wait.wait5Second();
				ArrayList<String> roomCharge = new ArrayList<String>();
				roomChangeFolioBased=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0),true);
				for (Map.Entry<String, String> entry : roomChangeFolioBased.entrySet()) {
					roomCharge.add(entry.getValue());
				}
				app_logs.info(roomChangeFolioBased);
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),
						roomNos.get(1));
				
				roomChangeFolioBased=reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0),true);
				app_logs.info(roomChangeFolioBased);
				for (Map.Entry<String, String> entry : roomChangeFolioBased.entrySet()) {
					roomCharge.add(entry.getValue());
				}
				app_logs.info(roomCharges);
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(0), roomNos.get(0));
				String size= String.valueOf(roomClassNames.size());
				roomChargeAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharge, "percent of stay", percentage,size);
				app_logs.info(roomChargeAmount);		
				reservationPage.click_DeatilsTab(driver, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, test_steps);
			}
			
			try {
				test_steps.add("<b>======Checkin Reservation ======</b>");
				app_logs.info("======Checkin Reservation ======");
				reservationPage.clickCheckInAllButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
				reservationPage.clickOnConfirmCheckInButton(driver);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.verifyCheckOutAllButton(driver, test_steps);
				test_steps.add("<b>========== Check Out Secondary Guest==========</b>");
				app_logs.info("<b>========== Check Out  Secondary Guest==========</b>");
				Wait.wait10Second();
				reservationPage.clickStayInfoCheckOutRoom(driver, test_steps, roomNos.get(1));
				Wait.wait5Second();
				reservationPage.verifySettlementMsg(driver, test_steps, roomNos.get(1),
						roomClassNames.get(1));
				if(action.equalsIgnoreCase("Yes")) {
				reservationPage.clickSettlementYesButton(driver, test_steps);
				}else if(action.equalsIgnoreCase("No")) {
					reservationPage.clickSettlementNoButton(driver, test_steps);
				}
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
				if(action.equalsIgnoreCase("Yes")) {
				reservationPage.proceedToCheckOutPayment(driver, test_steps);	
				}else if(action.equalsIgnoreCase("No")) {
					reservationPage.clickOnConfirmCheckOutButton(driver, test_steps);
				}
				reservationPage.verifySpinerLoading(driver);
				Wait.wait5Second();
				String secondGuestPay=null;
				if(action.equalsIgnoreCase("Yes")) {
				 secondGuestPay=reservationPage.getAmountFromPaymentScreen(driver);
				app_logs.info(secondGuestPay);		
				reservationPage.clickLogORPayAuthorizedButton(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				reservationPage.clickCloseButtonOfCheckoutSuccessfully(driver, test_steps);
				reservationPage.verifySpinerLoading(driver);
				}
				reservationPage.verifyStayInforRollBackButton(driver, test_steps, roomClassNames.get(1));
				reservationPage.verifyStayInfoCheckOutButton(driver, test_steps, roomNos.get(0));
				test_steps.add("<b>========== Verify Folio of Primary Guest==========</b>");
				app_logs.info("<b>========== Verify Folio of Primary Guest==========</b>");		
				reservationPage.click_Folio(driver, test_steps);
				String getcardNo = Utility.getCardNumberHidden(cardNumber);
				String description= "Name: "+nameOnCard+" Account #: "+getcardNo+" Exp. Date: "+Utility.expiryDate+"";
				reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, getcardNo, roomChargeAmount, test_steps);
				if(action.equalsIgnoreCase("Yes")) {
				test_steps.add("<b>========== Verify Folio of Secondary Guest==========</b>");
				app_logs.info("<b>========== Verify Folio of  Secondary Guest==========</b>");		
				reservationPage.click_FolioDetail_DropDownBox(driver, test_steps);
				reservationPage.clickFolioDetailOptionValue(driver, test_steps, roomClassAbb.get(1),roomNos.get(1));
				reservationPage.verifyFolioLineItem(driver, checkInDates.get(0), paymentType, getcardNo, Utility.convertDecimalFormat(secondGuestPay), test_steps);		
				}
			//	reservationPage.verifyBalanceAmountAtFolio(driver, test_steps, "0.00");
				/*if(action.equalsIgnoreCase("Yes")) {
				test_steps.add("Verify the settlement pop-up while 2nd guest is checking out with balance and primary guest is still checkin : without reservation transfers"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848563' target='_blank'>"
						+ "Click here to open TestRail: 848563</a><br>");
				Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the settlement pop-up while 2nd guest is "
						+ "checking out with balance and primary guest is still checkin : without reservation transfers");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
						Utility.comments.get(0), TestCore.TestRail_AssignToID);
				
				test_steps.add("Verify the Last Checkout should pay the balance and option of settlement should not be displayed"
						+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848430' target='_blank'>"
						+ "Click here to open TestRail: 848430</a><br>");
				Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the Last Checkout should pay the balance and option"
						+ " of settlement should not be displayed");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
						Utility.comments.get(1), TestCore.TestRail_AssignToID);
				}*/
				test_steps.add("<b>========== Check Out Primary Guest==========</b>");
				app_logs.info("<b>========== Check Out  Primary Guest==========</b>");
				Wait.wait5Second();
				reservationPage.click_DeatilsTab(driver, test_steps);
				reservationPage.clickStayInfoCheckOutRoom(driver, test_steps, roomNos.get(0));
				reservationPage.verifySpinerLoading(driver);
				reservationPage.generatGuestReportToggle(driver, test_steps, config.getProperty("flagOff"));
				if(action.equalsIgnoreCase("Yes")) {
				reservationPage.clickCheckoutProceedButton(driver, test_steps, "Proceed");
				reservationPage.clickConfirmCheckoutWithoutRefundButton(driver, test_steps);
				}else if(action.equalsIgnoreCase("No")) {
					reservationPage.clickOnConfirmCheckOutButton(driver, test_steps);
				}
				reservationPage.verifySpinerLoading(driver);
				Wait.wait5Second();
				reservationPage.verifyStayInforRollBackButton(driver, test_steps, roomClassNames.get(0));
				/* if(action.equalsIgnoreCase("No")) {
					test_steps.add("Verify that Secondary Guest should able to check-out & do not pay anything as their charges will be taken when primary guest checks out"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848455' target='_blank'>"
							+ "Click here to open TestRail: 848455</a><br>");
					Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify that Secondary Guest should able to check-out & do not pay anything as their charges will be taken when primary guest checks out"
							+ " of settlement should not be displayed");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
							Utility.comments.get(2), TestCore.TestRail_AssignToID);
					
					}else if(action.equalsIgnoreCase("Yes")) {
					
					test_steps.add("Verify the settlement pop-up while 2nd guest is checking out without balance and primary guest is still checking"
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848564' target='_blank'>"
							+ "Click here to open TestRail: 848564</a><br>");
					Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify the settlement pop-up while 2nd guest is checking out without balance and primary guest is still checking"
							+ " of settlement should not be displayed");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
							Utility.comments.get(3), TestCore.TestRail_AssignToID);
				}
				 */
				/* test_steps.add("Verify the behavior of individual Checkout if Single folio is present for checkout for a multi-room reservation."
							+ "<br><a href='https://innroad.testrail.io/index.php?/cases/view/848429' target='_blank'>"
							+ "Click here to open TestRail: 848429</a><br>");
					Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "Verify the behavior of individual Checkout if Single folio is present for checkout for a multi-room reservation."
							+ " of settlement should not be displayed");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4),
							Utility.comments.get(4), TestCore.TestRail_AssignToID);*/
					
				for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
				}
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
			}catch (Exception e) {
				Utility.catchException(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName,
						test_description, test_catagory, test_steps);
			} catch (Error e) {
				Utility.catchError(driver, e, "Create Reservation MRB", "Reservation", "Reservation", testName, test_description,
						test_catagory, test_steps);
			}
		}
	}
	
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifySettlementPopup", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
