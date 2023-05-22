package com.innroad.inncenter.tests;

import java.util.ArrayList;

import org.apache.poi.hssf.record.formula.functions.Today;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.WebConsole.Logger;
import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pages.NewRoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2ReservationV2 extends TestCore{
	
	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ReservationV2 res = new ReservationV2();
	Navigation nav = new Navigation();
	RoomClass rc = new RoomClass();
	NewRoomClass newRoomclass = new NewRoomClass();
	NewRoomClassesV2 roomclassV2 = new NewRoomClassesV2();
	RatesGrid ratesGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	Admin admin = new Admin();
	Properties prop = new Properties();
	FolioNew folioNew = new FolioNew();
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();
	
	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890",  
	email = "innRoadTestEmail@innroad.com",	address1 = "10th Building", address2 = "Block C", address3 = "Street 10",
	city = "NewYork", country = "United States", state = "Alaska", postalCode = "12345", referral = "Walk In",
	paymentMethod = "MC", cardNumber = "5454545454545454", currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, 
	accountFirstName, accountLastName, reportsTab, applicationTab, currentDay, accountName = "AccountEQSVVD", roomNumber,
	cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge , guestFullName, roomAbbr;
	
	String todayDate = Utility.getCurrentDate("dd/MM/yyyy");
		
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}
	
//	@BeforeClass()
	public void getTimeZone() throws Exception{
		driver = getDriver();
		loginReportsV2ReservationV2(driver);
		
		nav.admin(driver);
		nav.navigateToClientinfo(driver);		
		admin.clickClientName(driver);
		admin.clickClientOption(driver);		
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.equalsIgnoreCase("USD ( $ ) ")) {
			TestCore.propertyCurrency = "$";
		}else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			TestCore.propertyCurrency = "£";
		}			
	
		TestCore.propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);
		
		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Eastern";
			break;			
		case "(GMT-06:00) Central Time (US and Canada)":
			TestCore.propertyTimeZone = "US/Central";
			break;		
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			TestCore.propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}
		
		if (dFormat.equalsIgnoreCase("USA")) {
			TestCore.propertyDateFormat = "MMM dd, YYYY";
		}else if (dFormat.equalsIgnoreCase("International")) {
			TestCore.propertyDateFormat = "dd MMM, YYYY";
		}

		nav.ReservationV2_Backward(driver);
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	
	}
	
	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void reservationV2Actions(String scenario, String checkInDate, String checkOutDate,		
			String accountType, String associateAccount, String resStatus, String resType,
			String ratePlan, String roomClass, String marketSegment, String adults, String children, String paymentMethod, 
			String cardNumber, String isTaxExempt, String taxExemptID, String changeOption) throws Exception {
		
		String guestFirstName = "First" + Utility.generateRandomStringWithoutNumbers();
		String guestLastName = "Last" + Utility.generateRandomStringWithoutNumbers();
		String guestSalutation = "Mr.";
		String address1 = Utility.generateRandomString(), address2 = Utility.generateRandomString(), address3 = Utility.generateRandomString();
		String city = Utility.generateRandomString();
		String postalCode = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		String phoneNumber = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String alternativePhone = "9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
		String email = "innroadautomation@innroad.com";
		String country = "United States";
		String state = "Alaska";
		String nameOnCard = "test", cardExpDate= Utility.getFutureMonthAndYearForMasterCard();
		String travelAgent = "", referral = "GDS", useTravelAgentInfo = "No";
		String promoCode = "";
		String ratePlanType = "";
		String isCopyGuest = "Yes", isAddMoreGuest = "No", isAssign = "Yes", isGuestProfile = "No", isQuote = "No";
		String isAddNotes = "No", isTask = "No";

		
		if (resStatus.equalsIgnoreCase("Quote")) {
			isQuote = "Yes";
		}
		
		if (scenario.contains("unassigned") || scenario.contains("Unassigned")) {
			isAssign = "No";
		}
		
		if (scenario.contains("multiple guests") || scenario.contains("multipleGuests") || scenario.contains("additional guest")) {
			isAddMoreGuest = "Yes";
		}

		test_description = "Validate <br>" + "<a href=''>" + "Click here to open TestRail: </a>";
		test_category = "ReportsV2 - ";
		String testName = scenario;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

//		Utility.closeTabsExcept(driver, 1);		
		if (Utility.validateString(accountType)) {
			accountName = "AccountEQSVVD";
		}else {
			accountName = "";
		}

		try {
			TestCore.propertyTimeZone = "US/Eastern";
			if (!resType.equalsIgnoreCase("MRB")) {
				if ( !(Utility.validateDate(checkInDate)) ) {
	            	checkInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
	            	if (scenario.contains("multi")) {
	            		checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (scenario.contains("single")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					} else if (scenario.contains("extend")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, 2);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, updateEndDate);
					} else if (scenario.contains("reduce")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, -1);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, updateEndDate);
					} else if (scenario.contains("update") && scenario.contains("dates")) {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");						
						updateStartDate = "";
						updateStartDate = Utility.addDays(checkOutDate, 3);
						updateEndDate = "";
						updateEndDate = Utility.addDays(checkOutDate, 3);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(updateStartDate, updateEndDate);
					}else {
						checkOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy");
					}
	    		}
			} else {
				checkInDates.clear();
				checkOutDates.clear();
				if ( !(Utility.validateDate(checkInDate)) ) {
					for (int i = 0; i < roomClass.split("\\|").length; i++) {
						checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						if (scenario.contains("multi")) {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						}else {
							checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone), "MM/dd/yyyy", "dd/MM/yyyy"));
						}
						
					}
					checkInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
					checkOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
				} else {
					checkInDates = Utility.splitInputData(checkInDate);
					checkOutDates = Utility.splitInputData(checkOutDate);
				}

			}
			if (!Utility.validateString(adults)) {
				adults = "2";
			}
			if (!Utility.validateString(children)) {
				children = "0";
			}
			
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);


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
		
		driver = getDriver();
		loginReportsV2ReservationV2(driver);
		
/*		app_logs.info("Checking for Rate plan and Room class and removing all policies and restrictions at rate plan level and season level");
		try {
			nav.setup(driver);
			nav.roomClass(driver);
			if (roomclassV2.searchForRoomClassEistsOrNot(driver, test_steps, roomClass)) {			
				app_logs.info("Room class exists");
				roomAbbr = roomclassV2.getAbbreviation(driver, test_steps, roomClass);
				app_logs.info("Room Class: "+roomClass+" , Room Abbreviation: "+roomAbbr);
			}else {
				app_logs.info("Room class not exists. Creating new Room class");
				roomAbbr = "TRC";
				roomclassV2.createRoomClassV2(driver, test_steps, roomClass, roomAbbr, "4", "8", "100");
			}
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			nav.inventoryFromRoomClass(driver, test_steps);
			nav.ratesGrid(driver);
			
			if (ratesGrid.isRatePlanExist(driver, ratePlan, test_steps)) {
				app_logs.info("Rate Plan Exists");			
				ratesGrid.clickEditIcon(driver, test_steps);
				nightlyRate.switchCalendarTab(driver, test_steps);
				
				if (nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, test_steps, checkInDate, Utility.getDatePast_FutureDate(10))) {
					app_logs.info("Season is available");
					ratesGrid.removePoliciesAndRestrictionsFromRatePlanAndSeason(driver, ratePlan, checkInDate, test_steps);
					app_logs.info("Removed all Policies, Rules and Restrictions from Rate plan and season level");
				}else {
					app_logs.info("Season is not available");
					app_logs.info("Creating new Season");
					nightlyRate.createSeason(driver, test_steps, checkInDate, Utility.getDatePast_FutureDate(365), 
							"TestSeason", "Yes", "Yes", "Yes", "Yes", "Yes", "Yes", "Yes", "No", "60", "4", "8", null, null, 
							"Yes", null, null, null, null, null, null, "No", "No", null, null, null, "No", "No", "No", "No", "No", "No", "No", 
							null, null, "No");
					ratesGrid.removePoliciesAndRestrictionsFromRatePlanAndSeason(driver, ratePlan, checkInDate, test_steps);
				}
		
			}else {
				app_logs.info("Rate plan not exists");
				app_logs.info("Creating new Rate Plan");
				ratesGrid.clickRateGridAddRatePlan(driver);
				ratesGrid.clickRateGridAddRatePlanOption(driver, ratePlanType);
				nightlyRate.enterRatePlanName(driver, ratePlan, test_steps);
				String FolioDisplayName = ratePlan, Description = ratePlan, Channels = "InnCenter";
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, test_steps);
				nightlyRate.enterRatePlanDescription(driver, Description, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				
				nightlyRate.selectChannels(driver, Channels, true, test_steps);
//				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, test_steps);
				
				nightlyRate.selectRoomClasses(driver, roomClass, true, test_steps);
//				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, test_steps);
				
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.clickNextButton(driver, test_steps);
				nightlyRate.clickCreateSeason(driver, test_steps);
				
				nightlyRate.clickCompleteChanges(driver, test_steps);
				nightlyRate.clickSaveAsActive(driver, test_steps);
				
			}
			
			nav.ReservationV2_Backward(driver);
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to check Rate plan", testName,
						"Failed to check Rate plan", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to check Rate plan", testName,
						"Failed to check Rate plan", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
*/	

		res.click_NewReservation(driver, test_steps);
		if (resType.equalsIgnoreCase("Single") || resType.equalsIgnoreCase("Copy")) {
			app_logs.info("==== Creating Single Reservation ====");
			test_steps.add("==== <b> Creating Single Reservation <b> ====");
//			res.createReservation(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode, 
//					roomClass, isAssign, guestSalutation, guestFirstName, guestLastName, phoneNumber, alternativePhone, 
//					email, address1, address2, address3, city, country, state, postalCode, isGuestProfile, paymentMethod, 
//					cardNumber, nameOnCard, cardExpDate, isTaxExempt, taxExemptID, travelAgent, marketSegment, referral, 
//					useTravelAgentInfo, isQuote, isAddMoreGuest, isAddNotes, "Internal", "TestNotes Sub", "TestNotes Dec", 
//					isTask, "Front Desk", "Internal", "Test Task", "Test Remarks", "03/03/2021", "auto", "To Do", accountName);
		}else if (resType.equalsIgnoreCase("MRB")) {
			for (int i = 2; i <= roomClass.split("\\|").length; i++) {
				guestFirstName = guestFirstName+"|"+Utility.generateRandomString();
				guestLastName = guestLastName+"|"+Utility.generateRandomString();
				guestSalutation = guestSalutation+"|Mr.";
				email = email+"|innroadautomation@innroad.com";
				phoneNumber = phoneNumber+"|9" + Utility.generateRandomNumberWithGivenNumberOfDigits(9);
			}
			app_logs.info("==== Creating MRB Reservation ====");
			test_steps.add("==== <b> Creating MRB Reservation <b> ====");
//			res.createMRBReservation(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode, 
//					roomClass, isAssign, isGuestProfile, guestSalutation, guestFirstName, guestLastName, 
//					email, phoneNumber, alternativePhone, isCopyGuest, address1, address2, address3, 
//					city, country, state, postalCode, paymentMethod, cardNumber, nameOnCard, 
//					cardExpDate, isTaxExempt, taxExemptID, travelAgent, marketSegment, 
//					referral, useTravelAgentInfo, isAddMoreGuest, isQuote, isAddNotes, "Internal", "TestNotes Sub", "TestNotes Dec", 
//					isTask, "Front Desk", "Internal", "Test Task", "Test Remarks", "03/03/2021", "auto", "To Do", accountName);
		}

		app_logs.info("Reservation Number: "+res.get_ReservationConfirmationNumber(driver, test_steps));
		app_logs.info("Reservation status: "+res.get_ReservationStatus(driver, test_steps));
		res.clickCloseReservationSavePopup(driver, test_steps);
		
		if (scenario.contains("Quote Book") || scenario.contains("Quote book")) {
			res.bookReservationFromQuote(driver, test_steps);
		}
		
		if (resType.equalsIgnoreCase("Copy")) {
			app_logs.info("==== Creating Copy Reservation ====");
			test_steps.add("==== <b> Creating Copy Reservation <b> ====");
			res.copyReservation(driver, test_steps, "Copy"+guestFirstName, "Copy"+guestLastName);
			app_logs.info("Copied Reservation Number: "+res.get_ReservationConfirmationNumber(driver, test_steps));
			app_logs.info("Copied Reservation status: "+res.get_ReservationStatus(driver, test_steps));
			test_steps.add("Copied Reservation Number: "+res.get_ReservationConfirmationNumber(driver, test_steps));
			test_steps.add("Copied Reservation status: "+res.get_ReservationStatus(driver, test_steps));
			res.clickCloseReservationSavePopup(driver, test_steps);
		}
		
		if (scenario.contains("extend") || scenario.contains("reduce") || scenario.contains("update")) {
			String newCheckIn = checkInDate;
			String newCheckOut = checkOutDate;			
			
			if (resType.equalsIgnoreCase("Single")) {
				if (scenario.contains("extend")) {
					app_logs.info("==== Extend Single Reservation ====");
					test_steps.add("==== <b> Extend Single Reservation <b> ====");
					newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkOutDate, 2);
				}else if (scenario.contains("reduce")) {
					int days = Utility.getNumberofDays(checkInDate, checkOutDate);
					if (days>1) {
						app_logs.info("==== Reduce Single Reservation ====");
						test_steps.add("==== <b> Reduce Single Reservation <b> ====");
						newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkOutDate, -1);
					}else {
						app_logs.info("There is only one for reservation, so we can't reduce this reservation");
					}
					
				}else if (scenario.contains("update")) {
					app_logs.info("==== Update Single Reservation both CheckIn and CheckOut ====");
					test_steps.add("==== <b> Update Single Reservation both CheckIn and CheckOut <b> ====");
					newCheckIn = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkInDate, 1);
					newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", checkOutDate, 1);
				}
				res.modifyReservationDates(driver, newCheckIn, newCheckOut, changeOption,roomClass, test_steps);
				
			}else if (resType.equalsIgnoreCase("MRB")) {
				newCheckIn = checkInDate.split("\\|")[0];
				newCheckOut = checkOutDate.split("\\|")[0];
				
				if (scenario.contains("extend")) {
					app_logs.info("==== Extend MRB Reservation ====");
					test_steps.add("==== <b> Extend MRB Reservation <b> ====");
					newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", newCheckOut, 2);
				}else if (scenario.contains("reduce")) {
					int days = Utility.getNumberofDays(newCheckIn, newCheckOut);
					if (days>1) {
						app_logs.info("==== Reduce MRB Reservation ====");
						test_steps.add("==== <b> Reduce MRB Reservation <b> ====");
						newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", newCheckOut, -1);
					}else {
						app_logs.info("There is only one for reservation, so we can't reduce this reservation");
					}					
				}else if (scenario.contains("update")) {
					app_logs.info("==== Update MRB Reservation both CheckIn and CheckOut ====");
					test_steps.add("==== <b> Update MRB Reservation both CheckIn and CheckOut <b> ====");
					newCheckIn = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", newCheckIn, 2);
					newCheckOut = Utility.increaseDateAsPerGivenDate("dd/MM/yyyy", newCheckOut, 2);
				}
//				res.modifyReservationDatesMRBPrimary(driver, newCheckIn, newCheckOut, changeOption, test_steps);
//				Wait.wait3Second();
//				res.modifyReservationDatesMRBSecondary(driver, newCheckIn, newCheckOut, changeOption, test_steps);
			}
		}
		
		if (scenario.contains("modify guest") || scenario.contains("update guest")) {
			String newAdults = adults;
			String newChildren = children;
			
			if (resType.equalsIgnoreCase("Single")) {
				app_logs.info("==== Update Guest count ====");
				test_steps.add("==== <b> Update Guest count <b> ====");
				newAdults = Integer.toString((Integer.parseInt(adults) + 1));
				newChildren = Integer.toString((Integer.parseInt(children) + 1));
				res.modifyGuestCount(driver, newAdults, newChildren, test_steps);
			}else if (resType.equalsIgnoreCase("MRB")) {
				app_logs.info("==== Update Guest count ====");
				test_steps.add("==== <b> Update Guest count <b> ====");
				newAdults = Integer.toString((Integer.parseInt(adults.split("\\|")[0]) + 1));
				newChildren = Integer.toString((Integer.parseInt(children.split("\\|")[0]) + 1));
				res.modifyGuestCountMRBPrimary(driver, newAdults, newChildren, test_steps);
				Wait.wait3Second();
				res.modifyGuestCountMRBSecondary(driver, newAdults, newChildren, test_steps);
			}		
		}
		
		if (scenario.contains("modify guestinfo") || scenario.contains("update guestinfo")) {
//			res.modifyGuestInfo(driver, guestSalutation, guestFirstName, "Test", phoneNumber, 
//					alternativePhone, email, address1, address2, address3, city, country, state, 
//					postalCode, isGuestProfile, test_steps);
		}
		Wait.wait5Second();
		TripSummary tripSummary = res.getTripSummaryDetail(driver);
		app_logs.info("Trip Summary details: "+tripSummary.toString());
		app_logs.info("Trip Total: "+tripSummary.getTS_TRIP_TOTAL());
		app_logs.info("Balance: "+tripSummary.getTS_BALANCE());
		app_logs.info("Paid: "+tripSummary.getTS_PAID());
		double tripTax = Double.parseDouble(tripSummary.getTS_TAXES().replace("$", "").trim());
		app_logs.info("Trip tax: "+tripTax);
		
		ArrayList<StayInfo> stayInfo = res.getStayInfoDetailMRB(driver);
		
		
		res.switchFolioTab(driver, test_steps);
		
		app_logs.info("==== <b> Validating Folio <b> ====");
		test_steps.add("==== <b> Validating Folio <b> ====");
		folioNew.validateFolioTotals(driver, test_steps, tripSummary);
		//folioNew.validateFolioLineItemRoomCharge(driver, test_steps, stayInfo, folioItems);
		folioNew.validateFolioLineItemRoomCharge(driver, test_steps, stayInfo);
		
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(scenario, test_description, test_category, test_steps);
		
	}

	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("ReportsV2ReservationV2", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
