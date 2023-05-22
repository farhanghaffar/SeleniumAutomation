package com.innroad.inncenter.tests;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.LedgerAccount;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class ReportsV2RoomNetSalesReportValidationForReservationV2 extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "", test_description = "", test_category = "";
	boolean isQuote;
	int numberOfNights, additionalGuests, roomQuantity;

	
	ReportsV2 report = new ReportsV2();
	
	Login login = new Login();
	Navigation nav = new Navigation();
	Account accountPage = new Account();
	ReservationV2 reservationV2Page = new ReservationV2();
	Reservation res = new Reservation();
	Folio folio = new Folio();
	Groups group = new Groups();
	AdvGroups advgrp = new AdvGroups();
	LedgerAccount la = new LedgerAccount();
	ReservationSearch reservationSearch = new ReservationSearch();
	Properties prop = new Properties();
	Tapechart tc = new Tapechart();
	Admin admin = new Admin();
	NewRoomClassesV2 rc2 = new NewRoomClassesV2();
	RoomClass rc = new RoomClass();
	RoomMaintenance rm = new RoomMaintenance();
	RatesGrid ratesGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();

	String reservationNumber = null, guestFirstName = null, guestLastName, phoneNumber = "1234567890", userBooked = "",
			consumptionDay = "", email = "innRoadTestEmail@innroad.com", address1 = "10th Building",
			address2 = "Block C", address3 = "Street 10", city = "NewYork", country = "United States", state = "Alaska",
			postalCode = "12345", referral = "Walk In", paymentMethod = "MC", cardNumber = "5454545454545454",
			currency = null, clientTimeZone, dFormat, sourceOfRes, propertyName, accountFirstName, accountLastName,
			reportsTab, applicationTab, currentDay, accountName = "Acc" + Utility.generateRandomNumber(), roomNumber,
			cardExpDate, updateStartDate, updateEndDate, newRoomClassName, roomCharge, guestFullName, promoCode,
			roomClassAbb = "KS", noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
			taskRemarks, taskDueOn, taskAssignee, taskStatus, changeOption;

	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	ArrayList<String> allTabs = new ArrayList<>();
	ArrayList<String> updateDates = new ArrayList<>();
	ArrayList<String> guestNames = new ArrayList<>();

	ArrayList<String> reservationDates = new ArrayList<>();
	HashMap<String, Double> expChanges = new HashMap<>();
	ArrayList<String> startAndEndDates = new ArrayList<>();
	String dateFormat;
	int loopCount = 0;
	String todayDate = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excelReservationV2))
			throw new SkipException("Skipping the test - " + testName);
	}

	@BeforeClass()
	public void getTimeZone() throws Exception {
		driver = getDriver();
		loginReportsV2(driver);
		nav.admin(driver);
		nav.navigateToClientinfo(driver);
		admin.clickClientName(driver);
		admin.clickClientOption(driver);
		propertyName = admin.getPropertyName(driver);
		currency = admin.getDefaultClientCurrency(driver);
		if (currency.equalsIgnoreCase("USD ( $ ) ")) {
			propertyCurrency = "$";
		} else if (currency.equalsIgnoreCase("GBP ( £ ) ")) {
			propertyCurrency = "£";
		}

		propertyFirstDayOfWeek = admin.getClientStartDayOfTheWeek(driver, test_steps);
		clientTimeZone = admin.getClientTimeZone(driver, test_steps);
		dFormat = admin.getClientDateFormat(driver);

		switch (clientTimeZone) {
		case "(GMT-05:00) Eastern Time (US and Canada)":
			propertyTimeZone = "US/Eastern";
			break;
		case "(GMT-06:00) Central Time (US and Canada)":
			propertyTimeZone = "US/Central";
			break;
		case "(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London":
			propertyTimeZone = "Europe/London";
			break;
		default:
			break;
		}

		if (dFormat.equalsIgnoreCase("USA")) {
			propertyDateFormat = "MMM dd, YYYY";
		} else if (dFormat.equalsIgnoreCase("International")) {
			propertyDateFormat = "dd MMM, YYYY";
		}
		nav.Reservation_Backward(driver);
		cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
	}

	@Test(dataProvider = "getData", groups = "ReportsV2")
	public void validateNetSalesReport(String Scenario, String DateRange, String CheckInDate, String CheckOutDate,
			String breakOutBy, String accountType, String associateAccount, String resStatus, String ResType,
			String numberOfRooms, String Rateplan, String RoomClass, String marketSegment, String adults,
			String children, String roomsAction, String stayDateRangeOption, String groupNetSalesByOption,
			String sortReportByOption, String groupRowsByOption, String channelName) throws Exception {
		
		ArrayList<String> getActiveRoomNumber = new ArrayList<String>();
		HashMap<String, String> summaryViewReportBeforeAction = new LinkedHashMap();
		HashMap<String, String> summaryViewReportAfterAction = new LinkedHashMap();
		HashMap<String, String> expectedSummaryViewReportAfterAction2 = new LinkedHashMap();

		HashMap<String, HashMap<String, String>> detailedViewTotalReportBeforeActionForAllTheDates = new LinkedHashMap();
		HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDates = new LinkedHashMap();
		HashMap<String, HashMap<String, String>> detailedViewTotalReportAfterActionForAllTheDatesForVerifyData = new LinkedHashMap();
		HashMap<String, String> totalViewTotalReportBeforeAction = new LinkedHashMap();
		HashMap<String, String> totalViewTotalReportAfterAction = new LinkedHashMap();

		HashMap<String, String> reservationData = new LinkedHashMap();
		String totalRevenueValue = "";
		String totalRevenueValueForDetailedView = "";
		int totalReservationsForSummaryView = 0;
		int totalRoomNightsForSummaryView = 0;
		int totalReservationsForDetailedView = 0;
		int totalRoomNightsForDetailedView = 0;

		app_logs.info("groupRowsByOption: " + groupRowsByOption);
		test_name = Scenario;
		test_description = Scenario;
		test_category = "ReportsV2 - Net Sales Report";
		String testName = Scenario;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		String commonGroupNetByOption = "";
		switch (groupNetSalesByOption) {
		case "Channel":
			commonGroupNetByOption = channelName;
			break;
		case "Referral":
			commonGroupNetByOption = referral;
			break;
		case "ZipCode":
			commonGroupNetByOption = postalCode;
			break;
		case "GuestProfile":
			commonGroupNetByOption = guestFullName;
			break;
		case "State":
			commonGroupNetByOption = state;
			break;
		case "Room Class":
			commonGroupNetByOption = RoomClass;
			break;
		case "Rate Plan":
			commonGroupNetByOption = Rateplan;
			break;
		case "Market Segment":
			commonGroupNetByOption = marketSegment;
			break;
		case "Guest Country":
			commonGroupNetByOption = country;
			break;
		case "Travel Agent":
		case "Corporate/Member Account":
		case "Group":
			commonGroupNetByOption = accountName;
			break;

		case "Consumption Day":
			String date = ESTTimeZone.DateFormateForLineItem(0, "MMM dd, yyyy", propertyTimeZone);
			date = date + " - " + ESTTimeZone.DateFormateForLineItem(0, "MMM dd, yyyy", propertyTimeZone);
			commonGroupNetByOption = date;
			break;

		}

		test_steps.add(
				"=============== Verify data in Net Sales Report by selecting "
						+ groupNetSalesByOption + " from Group Net Sales By Drop Down ============");

		Utility.closeTabsExcept(driver, 1);
		test_steps.add("Logged into Application");
		loginReportsV2(driver);
		String BlockName = "Block" + Utility.generateRandomStringWithoutNumbers();
		int noOfRooms = 1;
		if (Utility.validateString(numberOfRooms)) {
			if (!(numberOfRooms.split("\\|").length > 1)) {
				noOfRooms = Integer.parseInt(numberOfRooms);
			} else {
				String[] rn = numberOfRooms.split("\\|");
				noOfRooms = 0;
				for (int i = 0; i < rn.length; i++) {
					noOfRooms = noOfRooms + Integer.parseInt(rn[i]);
				}
			}
		} else {
			numberOfRooms = "1";
		}

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			if (groupNetSalesByOption.equalsIgnoreCase("User Booked")) {
				String getGuestName = "";
				test_steps.add("Get user name from admin");
				nav.admin(driver);
				test_steps.add("Click on admin");
				admin.clickOnUserTab(driver);
				test_steps.add("Click on users");
				admin.clickOnSearchButton(driver);
				test_steps.add("Click on search button");
				getGuestName = admin.getUserName(driver, Utility.loginReportsV2.get(2));
				test_steps.add("User name: " + getGuestName);
				nav.navigateToReservationsFromAdmin(driver);
				commonGroupNetByOption = getGuestName;
			}

			test_steps.add("Get room active room number");
			nav.Setup(driver);
			test_steps.add("Click on steup");
			if ( groupNetSalesByOption.equalsIgnoreCase("Room Class")) {
				nav.RoomClass(driver);
				NewRoomClassesV2 classesV2 = new NewRoomClassesV2();
				getActiveRoomNumber = classesV2.getActiveRoomNumber(driver, "|", RoomClass, test_steps);
				test_steps.add("Active room number: " + getActiveRoomNumber.get(0));
				nav.Reservation_Backward_3(driver);
				}
			else {
				if (groupNetSalesByOption.equalsIgnoreCase("Room Number")) {
					getActiveRoomNumber.add("1");
				}

				nav.clickOnProperties(driver);
				getActiveRoomNumber.add(prop.getRoomNumber(driver, propertyName));
				test_steps.add("Active room number: " + getActiveRoomNumber.get(0));
				nav.reservationBackedFromProperty(driver);
			}

			if (!ResType.equalsIgnoreCase("MRB")) {
				if (!(Utility.validateDate(CheckInDate))) {
					CheckInDate = Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone),
							"MM/dd/yyyy", "MM/dd/yyyy");
					if (Scenario.contains("multi")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "dd/MM/yyyy");
					} else if (Scenario.contains("single")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "MM/dd/yyyy");
						;
					} else if (Scenario.contains("extend")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "dd/MM/yyyy");
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, 2);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
					} else if (Scenario.contains("reduce")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "dd/MM/yyyy");
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, -1);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(CheckInDate, updateEndDate);
					} else if (Scenario.contains("update") && Scenario.contains("dates")) {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(2, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "dd/MM/yyyy");
						updateStartDate = "";
						updateStartDate = Utility.addDays(CheckOutDate, 3);
						updateEndDate = "";
						updateEndDate = Utility.addDays(CheckOutDate, 3);
						updateDates = Utility.getAllDatesBetweenCheckInOutDates(updateStartDate, updateEndDate);
					} else {
						CheckOutDate = Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "dd/MM/yyyy");
					}
				}
			} else {
				checkInDates.clear();
				checkOutDates.clear();
				if (!(Utility.validateDate(CheckInDate))) {
					for (int i = 0; i < noOfRooms; i++) {
						checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(0, TestCore.propertyTimeZone),
								"MM/dd/yyyy", "MM/dd/yyyy"));
						if (Scenario.contains("multi")) {
							checkOutDates
									.add(Utility.parseDate(Utility.getDatePast_FutureDate(3, TestCore.propertyTimeZone),
											"MM/dd/yyyy", "MM/dd/yyyy"));
						} else {
							checkOutDates
									.add(Utility.parseDate(Utility.getDatePast_FutureDate(1, TestCore.propertyTimeZone),
											"MM/dd/yyyy", "MM/dd/yyyy"));
						}

					}
					CheckInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
					CheckOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
				} else {
					checkInDates = Utility.splitInputData(CheckInDate);
					checkOutDates = Utility.splitInputData(CheckOutDate);
				}

			}
			if (!ResType.equalsIgnoreCase("MRB")) {
				if (!Utility.validateString(adults)) {
					adults = "2";
				}
				if (!Utility.validateString(children)) {
					children = "0";
				}
			}
			guestFirstName = "";
			guestFirstName = "Auto";
			guestLastName = "";
			guestLastName = "User" + Utility.generateRandomStringWithoutNumbers();
			guestFullName = "";
			guestFullName = guestFirstName + " " + guestLastName;
			expChanges = report.setDefaultDataForRoomForeCastReport();
			currentDay = Utility.getCurrentDate("dd/MM/yyyy", TestCore.propertyTimeZone);
			additionalGuests = 0;
			roomNumber = null;
			isQuote = false;

			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);

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

		if (Scenario.contains("Super User") || Scenario.contains("super user")) {
			admin.logout(driver);
			loginSuperUser(driver);
			prop.selectPropertyWithSuperUser(driver, propertyName);
		}

		ArrayList<String> RoomClasses = new ArrayList<>();
		String[] rc = RoomClass.split("\\|");
		if (rc.length > 1) {
			for (int i = 0; i < rc.length; i++) {
				RoomClasses.add(rc[i]);
			}
		} else {
			RoomClasses.add(RoomClass);
		}

		if (stayDateRangeOption.equals("Today")) {
			if (groupNetSalesByOption.equals("Channel") || groupNetSalesByOption.equals("Guest Country")
					|| groupNetSalesByOption.equals("Market Segment") || groupNetSalesByOption.equals("Referral")
					|| groupNetSalesByOption.equalsIgnoreCase("State")
					|| groupNetSalesByOption.equalsIgnoreCase("zipcode")
					|| groupNetSalesByOption.equalsIgnoreCase("Room Class")
					|| groupNetSalesByOption.equalsIgnoreCase("Rate Plan")
					|| groupNetSalesByOption.equalsIgnoreCase("Travel Agent")
					|| groupNetSalesByOption.equalsIgnoreCase("Group")
					|| groupNetSalesByOption.equalsIgnoreCase("Consumption Day")
					|| groupNetSalesByOption.equalsIgnoreCase("User Booked")||
					groupNetSalesByOption.equals("Corporate/Member Account")||
					groupNetSalesByOption.equals("Travel Agent")) {
				try {
					test_steps.add("Clicked on reports icon");
					nav.ReportsV2(driver);
					test_steps.add("Successfully navigated to reports page.");
					report.navigateToNetSalesReport(driver, test_steps);
					test_steps.add("Navigated to Net Sales reports page");

					report.selectDateRange(driver, CheckInDate, CheckOutDate, stayDateRangeOption, test_steps);
					test_steps.add("Select Group Net Sales By Option : " + groupNetSalesByOption);
					report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesByOption);
					app_logs.info("before selecting groupNetSalesByOption");
					report.selectSortReportByOption(driver, test_steps, sortReportByOption);
					report.selectGroupRowsByOption(driver, test_steps, groupRowsByOption);
					report.clickOnRunReport(driver);

					test_steps.add("========== Get <b>" + groupNetSalesByOption
							+ "</b> Data Before Creating Reservation ===========");

					if (report.runReportToasterMessageDisplays(driver)
							|| report.getSelectedGroupNetSalesByRecord(driver, commonGroupNetByOption,
									groupNetSalesByOption) == false) {
						summaryViewReportBeforeAction = report.setDefaultDataForSummaryViewNetSalesReport();

						reservationDates = Utility.getAllDatesBetweenCheckInOutDates(
								Utility.parseDate(CheckInDate, "MM/dd/yyyy", "dd/MM/yyyy"),
								Utility.parseDate(CheckOutDate, "MM/dd/yyyy", "dd/MM/yyyy"));
						for (String day : reservationDates) {

							detailedViewTotalReportBeforeActionForAllTheDates.put(day,
									report.setDefaultDataForDetailedViewNetSalesReport());
						}

					} else {
						/*summaryViewReportBeforeAction = report.getSummaryViewDataForNetSaleReport(driver,
								commonGroupNetByOption, groupNetSalesByOption);*/
						summaryViewReportBeforeAction = report.getSummaryViewDataForNetSaleReport(driver,
								commonGroupNetByOption);
						reservationDates = Utility.getAllDatesBetweenCheckInOutDates(
								Utility.parseDate(CheckInDate, "MM/dd/yyyy", "dd/MM/yyyy"),
								Utility.parseDate(CheckOutDate, "MM/dd/yyyy", "dd/MM/yyyy"));
						for (String day : reservationDates) {

							detailedViewTotalReportBeforeActionForAllTheDates.put(day,
									report.getDetailedViewTotalDataForNetSaleReport(driver,
											Utility.parseDate(day, "dd/MM/yyyy", "MMM dd, yyyy, EEEE"),
											commonGroupNetByOption));
						}
					}

					app_logs.info("Summary view Details values : " + summaryViewReportBeforeAction);
					test_steps.add("Summary view Details values: " + summaryViewReportBeforeAction);
					app_logs.info("Detailed view values: " + detailedViewTotalReportBeforeActionForAllTheDates);
					test_steps.add("Detailed view values: " + detailedViewTotalReportBeforeActionForAllTheDates);
					if (!groupNetSalesByOption.equals("Consumption Day")) {
						if (!groupNetSalesByOption.equals("Booking Day")) {
							totalViewTotalReportBeforeAction = report.getSummaryViewDataForTotalNetSaleReport(driver);
						}
					}
					test_steps.add("Summary view Total values: " + totalViewTotalReportBeforeAction);
					allTabs = new ArrayList<>(driver.getWindowHandles());
					reportsTab = allTabs.get(1);
					applicationTab = allTabs.get(0);

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
						Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			} else if (groupNetSalesByOption.equalsIgnoreCase("Room Number")) {
				test_steps.add("Clicked on reports icon");
				nav.ReportsV2(driver);
				test_steps.add("Successfully navigated to reports page.");
				report.navigateToNetSalesReport(driver, test_steps);
				test_steps.add("Navigated to Net Sales reports page");

				report.selectDateRange(driver, CheckInDate, CheckOutDate, stayDateRangeOption, test_steps);
				test_steps.add("Select Group Net Sales By Option : " + groupNetSalesByOption);
				report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesByOption);
				app_logs.info("before selecting groupNetSalesByOption");
				report.selectSortReportByOption(driver, test_steps, sortReportByOption);
				report.selectGroupRowsByOption(driver, test_steps, groupRowsByOption);

				summaryViewReportBeforeAction = report.setDefaultDataForSummaryViewNetSalesReport();
				reservationDates = Utility.getAllDatesBetweenCheckInOutDates(
						Utility.parseDate(CheckInDate, "MM/dd/yyyy", "dd/MM/yyyy"),
						Utility.parseDate(CheckOutDate, "MM/dd/yyyy", "dd/MM/yyyy"));
				for (String day : reservationDates) {
					detailedViewTotalReportBeforeActionForAllTheDates.put(day,
							report.setDefaultDataForDetailedViewNetSalesReport());
				}

				allTabs = new ArrayList<>(driver.getWindowHandles());
				reportsTab = allTabs.get(1);
				applicationTab = allTabs.get(0);

			}

			// Action
			isQuote = false;
			if (resStatus.equalsIgnoreCase("Quote")) {
				isQuote = true;
			}
			roomNumber = "";
			if (Scenario.contains("unassigned") || Scenario.contains("Unassigned")) {
				roomNumber = "Unassigned";
			}
			additionalGuests = 0;
			if (Scenario.contains("multiple guests") || Scenario.contains("multipleGuests")
					|| Scenario.contains("additional guest")) {
				additionalGuests = 1;
			}

			if (CheckInDate.split("\\|").length > 1) {
				numberOfNights = Utility.getNumberofDays(CheckInDate.split("\\|")[0], CheckOutDate.split("\\|")[0]);
			} else {
				numberOfNights = Utility.getNumberofDays(CheckInDate, CheckOutDate);
			}

			app_logs.info("Number of nights: " + numberOfNights);
			Utility.switchTab(driver, applicationTab);
			nav.Reservation_Backward_3(driver);

			try {
				if (stayDateRangeOption.equals("Today")) {
					System.out.println(associateAccount);
					System.out.println(Utility.validateString(associateAccount));
					if (Utility.validateString(associateAccount)) {
						if (associateAccount.equalsIgnoreCase("Associate Account")) {
							sourceOfRes = "Associate Account";
						} else if (associateAccount.equalsIgnoreCase("Account Reservation")) {
							sourceOfRes = "Account Reservation";
						} else if (associateAccount.equals("Group")) {
							sourceOfRes = "Group";
						}
					} else if (ResType.equals("TapeChart")) {
						sourceOfRes = "TapeChart";
					} else {
						sourceOfRes = "From Reservations page";
					}
					if (ResType.equalsIgnoreCase("MRB")) {
						reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes,
								CheckInDate, CheckOutDate, adults, children, Rateplan, null, RoomClass, roomClassAbb,
								"Mr.|Mr.", guestFirstName + "|" + guestFirstName, guestLastName + "|" + guestLastName,
								phoneNumber + "|" + phoneNumber, phoneNumber, email + "|" + email, address1, address2,
								address3, city, country, state, postalCode, false, marketSegment, referral,
								paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber,
								isQuote, noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
								taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
								accountFirstName, accountLastName, false, "1235");
						roomCharge = reservationData.get("Room Charges1");
					}
					else if (ResType.equalsIgnoreCase("GroupPickBlueIcon")||
							ResType.equalsIgnoreCase("GroupPickRoomingList")) {
						nav.groups(driver);
						accountFirstName = Utility.generateRandomStringWithoutNumbers();
						accountLastName = Utility.generateRandomStringWithoutNumbers();
						ArrayList<String> accountNumbers = new ArrayList<>();

						group.createGroupAccount(driver, test_steps, accountName, true, null, marketSegment, referral,
								accountFirstName, 
								accountLastName, phoneNumber, address1, city, state, country, postalCode,accountNumbers );
						group.navigateRoomBlock(driver, test);													
						group.createNewBlockWithMultiRoomClasses(driver, BlockName, CheckInDate, CheckOutDate, Rateplan, "1", RoomClass, test_steps);
						group.navigateRoomBlock(driver, test);													
						if (ResType.equalsIgnoreCase("GroupPickRoomingList")) {
							test_steps.addAll(group.roomingListClick(driver));
							advgrp.enter_RoomPickupdetails(driver, test_steps);
							reservationNumber = group.pickUp_getResNo(driver);
							app_logs.info("Group pick Reservation number: "+reservationNumber);			
							group.pickUpCloseClick(driver);
							Wait.wait10Second();
							driver.findElement(By.xpath("//li[@id='head_reservations']")).click();
							reservationSearch.SearchAndOpenRes(driver, reservationNumber);
							Wait.wait5Second();
						}
						if (ResType.equalsIgnoreCase("GroupPickBlueIcon")) {
							advgrp.clickBlueBookIcon(driver);
							reservationV2Page.clickNext(driver, test_steps);
							reservationV2Page.enter_GuestName(driver, test_steps, "Mr.", guestFirstName, guestLastName);
							reservationV2Page.clickBookNow(driver, test_steps);
							reservationV2Page.clickCloseReservationSavePopup(driver, test_steps);
														
						}


					}
					else {
						reservationData = reservationV2Page.createReservation(driver, test_steps, sourceOfRes,
								CheckInDate, CheckOutDate, adults, children, Rateplan, null, RoomClass, roomClassAbb,
								"Mr.", guestFirstName, guestLastName, phoneNumber, phoneNumber, email, address1,
								address2, address3, city, country, state, postalCode, false, marketSegment, referral,
								paymentMethod, cardNumber, guestFullName, cardExpDate, additionalGuests, roomNumber,
								isQuote, noteType, noteSubject, noteDescription, taskCategory, taskType, taskDetails,
								taskRemarks, taskDueOn, taskAssignee, taskStatus, accountName, accountType,
								accountFirstName, accountLastName, false, "1235");
					}
					
					

					if (!resStatus.isEmpty()) {
						ReservationStatusBar statusBar = reservationV2Page.getStatusBarDetail(driver);
						if (!statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase(resStatus)) {
							reservationV2Page.changeReservationStatus(driver, test_steps, resStatus);
						} else {
							test_steps.add("By default selected status: " + statusBar);
						}

					}
					if (groupNetSalesByOption.equalsIgnoreCase("Room Number")) {
						commonGroupNetByOption = RoomClass + ": " + reservationData.get("Room Number");
					}
					if (ResType.equals("MRB")) {
						roomCharge = reservationV2Page.getRoomChargesTotalForMRB(driver);
					} else {
						roomCharge = reservationV2Page.getRoomChargesTotal(driver);
					}
					roomNumber = reservationData.get("Room Number");
				}

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
					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				app_logs.info("========== Action completed at " + java.time.LocalTime.now() + "==========");
				Wait.wait60Second();
				Wait.wait60Second();
				//Wait.wait60Second();
				//Wait.wait60Second();
				//Wait.wait60Second();
				test_steps.add(
						"========== Validating Reports after 2 minutes at " + java.time.LocalTime.now() + "==========");
				app_logs.info(
						"========== Validating Reports after 2 minutes at " + java.time.LocalTime.now() + "==========");

				test_steps.add("Switched to Net Sale Report Page");
				Utility.switchTab(driver, reportsTab);
				report.clickOnRunReport(driver);

				test_steps.add("========== Get <b>" + groupNetSalesByOption
						+ "</b> Data after reservation Creation ===========");
				/*summaryViewReportAfterAction = report.getSummaryViewDataForNetSaleReport(driver, commonGroupNetByOption,
						groupNetSalesByOption);*/
				summaryViewReportAfterAction = report.getSummaryViewDataForNetSaleReport(driver, commonGroupNetByOption);

				reservationDates = Utility.getAllDatesBetweenCheckInOutDates(
						Utility.parseDate(CheckInDate, "MM/dd/yyyy", "dd/MM/yyyy"),
						Utility.parseDate(CheckOutDate, "MM/dd/yyyy", "dd/MM/yyyy"));
				for (String day : reservationDates) {
					if (groupNetSalesByOption.equals("Consumption Day")
							|| groupNetSalesByOption.equals("Booking Day")) {
						detailedViewTotalReportAfterActionForAllTheDates.put(day,
								report.getDetailedViewTotalDataForNetSaleReport(driver,
										Utility.parseDate(day, "dd/MM/yyyy", "MMM dd, yyyy, EEEE"),
										commonGroupNetByOption));
					} else {
						
						if (associateAccount.equalsIgnoreCase("Associate Account")||
								associateAccount.equalsIgnoreCase("Group")||groupNetSalesByOption.equalsIgnoreCase("Corporate/Member Account")
								||groupNetSalesByOption.equalsIgnoreCase("Travel Agent")) {
							detailedViewTotalReportAfterActionForAllTheDates.put(day,
									report.getDetailedViewTotalDataForNetSaleReport(driver,
											Utility.parseDate(day, "dd/MM/yyyy", "MMM dd, yyyy, EEEE"),
											groupNetSalesByOption+" - "+accountName));

						}
						else {
						detailedViewTotalReportAfterActionForAllTheDates.put(day,
								report.getDetailedViewTotalDataForNetSaleReport(driver,
										Utility.parseDate(day, "dd/MM/yyyy", "MMM dd, yyyy, EEEE"),
										groupNetSalesByOption));
						}

					}
				}

				if (!groupNetSalesByOption.equals("Consumption Day")) {
					if (!groupNetSalesByOption.equals("Booking Day")) {
						totalViewTotalReportAfterAction = report.getSummaryViewDataForTotalNetSaleReport(driver);
					}
				}
				app_logs.info("After reservation creation Summary view data for <b>" + groupNetSalesByOption + "</b> : "
						+ summaryViewReportAfterAction);
				test_steps.add("After reservation creation Summary view data for <b>" + groupNetSalesByOption
						+ "</b> : " + summaryViewReportAfterAction);
				app_logs.info("After reservation creation Detailed view data for <b>" + groupNetSalesByOption
						+ "</b> : " + detailedViewTotalReportAfterActionForAllTheDates);
				test_steps.add("After reservation creation Detailed view data for <b>" + groupNetSalesByOption
						+ "</b> : " + detailedViewTotalReportAfterActionForAllTheDates);
				test_steps.add(
						"After reservation creation Summary view date for Total: " + totalViewTotalReportAfterAction);

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
					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report",
							driver);
				} else {
					Assert.assertTrue(false);
				}

			}

			try {

				test_steps.add(" ===========<b> Validating summary View Report</b> =============");

				for (Map.Entry<String, String> entry : summaryViewReportBeforeAction.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (key.equals("Net Reservations") || key.equals("Net Room Nights")) {
						int intValue = 0;
						if (key.equals("Net Room Nights") && ResType.equals("MRB")) {
							intValue = Integer.parseInt(value) + 2;
						} else {
							intValue = Integer.parseInt(value) + 1;
						}
						value = String.valueOf(intValue);
						if (key.equals("Net Room Nights")) {
							totalRoomNightsForSummaryView = Integer.parseInt(value);
						} else if (key.equals("Net Reservations")) {
							totalReservationsForSummaryView = Integer.parseInt(value);
						}
					} else if (key.equals("Booking Nights %")) {
						String bookingNights = "";
						if (groupNetSalesByOption.equals("Consumption Day")
								|| groupNetSalesByOption.equals("Booking Day")) {
							bookingNights = String.format("%.2f",
									(Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView))
											/ Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView))) * 100)
									+ "%";
						} else {
							bookingNights = String.format("%.2f",
									(Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView)) / Double
											.parseDouble(String.valueOf(report.getTotalNoOfReservation(driver)))) * 100)
									+ "%";
						}
						value = bookingNights;

					} else if (key.equals("Room Revenue") || key.equals("Total Revenue")) {
						value = value.split("\\$")[1];
						value = value.replaceAll(",", "");
						value = "$" + String.format("%.2f", (Double.valueOf(value) + Double.valueOf(roomCharge)));
						if (key.equals("Total Revenue")) {
							totalRevenueValue = value.split("\\$")[1].replaceAll(",", "");
						}
					} else if (key.equals("Rev PAR")) {
						double revPARDblvalue = (Double.parseDouble(totalRevenueValue)
								/ Double.parseDouble(getActiveRoomNumber.get(0)));
						value = String.valueOf(revPARDblvalue);
						value = report.getValueAfterTrailingAndRoundUp(value);
					} else if (key.equals("Avg Daily Rate")) {
						double AvgDailyRate = Double.parseDouble(totalRevenueValue)
								/ Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView));
						value = String.valueOf(AvgDailyRate);
						value = report.getValueAfterTrailingAndRoundUp(value);

					} else if (key.equals("Avg Stay")) {
						double avgStayValue = Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView))
								/ (Double.parseDouble(String.valueOf(totalReservationsForSummaryView)));
						value = String.valueOf(avgStayValue);
						value = report.getValueAfterTrailingAndRoundUp(value);

					}
					expectedSummaryViewReportAfterAction2.put(key, value);
				}

				for (Map.Entry<String, String> expectedSummaryViewEntry : expectedSummaryViewReportAfterAction2
						.entrySet()) {
					String expectedKey = expectedSummaryViewEntry.getKey();
					String expectedValue = expectedSummaryViewEntry.getValue();
					test_steps.add("Expected " + expectedKey + " : " + expectedValue);
					app_logs.info("Expected " + expectedKey + " : " + expectedValue);
					for (Map.Entry<String, String> summaryViewReportEntry : summaryViewReportAfterAction.entrySet()) {
						String headerKey = summaryViewReportEntry.getKey();
						String headerValue = summaryViewReportEntry.getValue();
						if (headerKey.equalsIgnoreCase(expectedKey)) {
							test_steps.add("Found " + headerKey + " : " + headerValue);
							app_logs.info("Found " + headerKey + " : " + headerValue);
							if (headerValue.equals(expectedValue)) {
								test_steps.add("Successfully verified " + headerKey + " values are matching.");
							} else {
								test_steps.add("Failed : " + headerKey + " values are mismatching.");
							}
							break;
						}
					}

				}
				test_steps.add("Successfully verified summary view details values are matching for <b>: "
						+ commonGroupNetByOption);

				if (!groupNetSalesByOption.equals("Consumption Day")) {
					if (!groupNetSalesByOption.equals("Booking Day")) {
						HashMap<String, HashMap<String, String>> getChannelData = new LinkedHashMap();
						int netReservation = 0;
						int netRoomNights = 0;
						double bookingNights = 0.00;
						double roomRevenue = 0.00;
						double otherRevenue = 0.00;
						double totalRevenue = 0.00;
						double cancel = 0.00;

						if (groupRowsByOption.equalsIgnoreCase("day")) {
							ArrayList<String> numberOfChannel = report.getNumberOfChanelFromSummaryViewPanel(driver);
							for (int i = 0; i < numberOfChannel.size(); i++) {
								/*getChannelData.put(numberOfChannel.get(i), report.getSummaryViewDataForNetSaleReport(
										driver, numberOfChannel.get(i), groupNetSalesByOption));*/
								getChannelData.put(numberOfChannel.get(i), report.getSummaryViewDataForNetSaleReport(
										driver, numberOfChannel.get(i)));
							}
							for (int i = 0; i < numberOfChannel.size() - 1; i++) {
								test_steps.add("Verify summary view data for " + numberOfChannel.get(i));
								for (Map.Entry<String, String> entry : getChannelData.get(numberOfChannel.get(i))
										.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									if (key.equals("Net Reservations")) {
										netReservation = netReservation + (Integer.parseInt(value));
									}
									if (key.equals("Net Room Nights")) {
										netRoomNights = netRoomNights + (Integer.parseInt(value));

									} else if (key.equals("Booking Nights %")) {
										value = value.replace("%", "");
										bookingNights = bookingNights + (Double.parseDouble(value));

									} else if (key.equals("Total Revenue")) {
										value = value.replace(",", "").replace("$", "").replace(" ", "");
										totalRevenue = totalRevenue + (Double.parseDouble(value));

									} else if (key.equals("Room Revenue")) {
										value = value.replace(",", "").replace("$", "").replace(" ", "");
										roomRevenue = roomRevenue + (Double.parseDouble(value));
									} else if (key.equals("Other Revenue")) {
										value = value.replace(",", "").replace("$", "").replace(" ", "");
										otherRevenue = otherRevenue + (Double.parseDouble(value));
									} else if (key.equals("Cancel %")) {
										value = value.replace("%", "").replace(" ", "");
										cancel = cancel + (Double.parseDouble(value));
										cancel = Double.parseDouble(String.format("%.2f", cancel));
									}
								}
							}

							test_steps.add(" ===========<b> Validating Total in Summary view report</b> =============");
							for (Map.Entry<String, String> entry : report
									.getSummaryViewDataForTotalNetSaleReport(driver).entrySet()) {
								String key = entry.getKey();
								String value = entry.getValue();
								if (key.equals("Net Reservations")) {
									test_steps.add("Expected net reservation: " + netReservation);
									test_steps.add("Found: " + value);
									if (value.equals(String.valueOf(netReservation))) {
										test_steps.add("Successfully verified net reservation");
									} else {
										test_steps.add("Failed: net reservation are mismatching!");
									}

								}
								if (key.equals("Net Room Nights")) {
									test_steps.add("Expected net room nights: " + netRoomNights);
									test_steps.add("Found: " + value);
									if (value.equals(String.valueOf(netRoomNights))) {
										test_steps.add("Successfully verified net room nights");
									} else {
										test_steps.add("Failed: net room nights are mismatching!");
									}
								}

								else if (key.equals("Booking Nights %")) {
									test_steps.add(
											"Expected booking nights: " + String.format("%.2f", bookingNights) + "%");
									test_steps.add("Found: " + value);
									if (value.equals(String.format("%.2f", bookingNights) + "%")) {
										test_steps.add("Successfully verified booking nights");
									} else {
										test_steps.add("Failed: booking nights are mismatching!");
									}
								} else if (key.equals("Room Revenue")) {
									value = value.replaceAll(",", "");
									test_steps.add(
											"Expected room revenue: " + ("$" + String.format("%.2f", roomRevenue)));
									test_steps.add("Found: " + value);
									if (value.equals("$" + String.format("%.2f", roomRevenue))) {
										test_steps.add("Successfully verified room revenue");
									} else {
										test_steps.add("Failed: room revenue is mismatching!");
									}
								} else if (key.equals("Other Revenue")) {
									value = value.replaceAll(",", "");
									test_steps.add("Expected other room revenue: "
											+ ("$" + String.format("%.2f", otherRevenue)));
									test_steps.add("Found: " + value);
									if (value.equals("$" + String.format("%.2f", otherRevenue))) {
										test_steps.add("Successfully verified other room revenue");
									} else {
										test_steps.add("Failed: other room revenue is mismatching!");
									}
								} else if (key.equals("Total Revenue")) {
									value = value.replaceAll(",", "");
									test_steps.add(
											"Expected total revenue: " + ("$" + String.format("%.2f", totalRevenue)));
									test_steps.add("Found: " + value);
									if (value.equals("$" + String.format("%.2f", totalRevenue))) {
										test_steps.add("Successfully verified total revenue");
									} else {
										test_steps.add("Failed: total revenue is mismatching!");
									}
								}

								else if (key.equals("Rev PAR")) {
									double revPARDblvalue = 0.00;
									if (groupNetSalesByOption.equalsIgnoreCase("Room Number")) {
										revPARDblvalue = (totalRevenue)
												/ Double.parseDouble(getActiveRoomNumber.get(1));

									}
									else {
										revPARDblvalue = (totalRevenue)
												/ Double.parseDouble(getActiveRoomNumber.get(0));
									}
									String value1 = String.valueOf(revPARDblvalue);
									value1 = report.getValueAfterTrailingAndRoundUp(value1);
										test_steps.add("Expected REV PAR: " + value1);
										test_steps.add("Found: " + value);
										if (value.equals(value1)) {
											test_steps.add("Successfully verified REV PAR");
										} else {
											test_steps.add("Failed: REV PAR is mismatching");
										}
									
								} else if (key.equals("Avg Daily Rate")) {
									double AvgDailyRate = totalRevenue / netRoomNights;
									String value1 = String.valueOf(AvgDailyRate);
									value1 = report.getValueAfterTrailingAndRoundUp(value1);

									test_steps.add("Expected avg daily rate: " + value1);
									test_steps.add("Found: " + value);
									if (value.equals(value1)) {
										test_steps.add("Successfully verified avg daily rate");
									} else {
										test_steps.add("Failed: avg daily rate is mismatching");
									}

								} else if (key.equals("Avg Stay")) {
									double avgStayValue = Double.parseDouble(String.valueOf(netRoomNights))
											/ (Double.parseDouble(String.valueOf(netReservation)));
									String value1 = String.valueOf(avgStayValue);
									value1 = report.getValueAfterTrailingAndRoundUp(value1);
										test_steps.add("Expected Avg Stay: " + value1);
										test_steps.add("Found: " + value);
										if (value.equals(value1)) {
											test_steps.add("Successfully Verified Avg Stay");
										} else {
											test_steps.add("Failed: Avg Stay is mismatching");
										}
									
								}else if (key.equals("Cancel %")) {
										value = value.replace(" ", "");
										String expectedvalue = String.format("%.2f", cancel) + "%";
										test_steps.add("Expected cancel%: " + expectedvalue);
										test_steps.add("Found: " + value);
										if (value.equals(expectedvalue)) {
											test_steps.add("Successfully verified cancel %");
										} else {
											test_steps.add("Failed: cancel % is mismatching!");

										}

								}
							}
						}
					}
				}
				HashMap<String, String> detailedViewValuesMapForVerification = new LinkedHashMap<>();
				if (DateRange.equalsIgnoreCase("Today")) {
					test_steps.add(" ===========<b> Validating Detailed view report</b> =============");
					for (Entry<String, HashMap<String, String>> detailViewDateEntry : detailedViewTotalReportBeforeActionForAllTheDates
							.entrySet()) {
						String dayEntry = detailViewDateEntry.getKey();
						HashMap<String, String> details = detailViewDateEntry.getValue();
						for (Entry<String, String> summaryViewData : details.entrySet()) {
							String headerKey = summaryViewData.getKey();
							String detailViewValue = summaryViewData.getValue();
							if (headerKey.equals("Net Reservation") || headerKey.equals("Net Room Nights")) {
								int intValue = 0;
								if (headerKey.equals("Net Room Nights") && ResType.equals("MRB")) {
									intValue = Integer.parseInt(detailViewValue) + 2;
								} else {
									intValue = Integer.parseInt(detailViewValue) + 1;
								}
								detailViewValue = String.valueOf(intValue);
								if (headerKey.equals("Net Room Nights")) {
									totalRoomNightsForDetailedView = Integer.parseInt(detailViewValue);
								} else if (headerKey.equals("Net Reservation")) {
									totalReservationsForDetailedView = Integer.parseInt(detailViewValue);
								}
							} else if (headerKey.equals("Booking Nights")) {
								String bookingNights1 = "";
								if (groupNetSalesByOption.equals("Consumption Day")
										|| groupNetSalesByOption.equals("Booking Day")) {
									bookingNights1 = String.format("%.2f",
											(Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView))
													/ Double.parseDouble(String.valueOf(totalRoomNightsForSummaryView)))
													* 100)
											+ "%";
									detailViewValue = bookingNights1;
								}

								else {
									bookingNights1 = String.format("%.2f",
											(Double.parseDouble(String.valueOf(totalRoomNightsForDetailedView)) / Double
													.parseDouble(String.valueOf(totalRoomNightsForDetailedView))) * 100)
											+ "%";
									detailViewValue = bookingNights1;
								}

							} else if (headerKey.equals("Room Revenue") || headerKey.equals("Total Revenue")) {
								detailViewValue = detailViewValue.split("\\$")[1].replaceAll(",", "");
								detailViewValue = "$" + String.format("%.2f",
										(Double.valueOf(detailViewValue) + Double.valueOf(roomCharge)));
								if (headerKey.equals("Total Revenue")) {
									totalRevenueValueForDetailedView = detailViewValue.split("\\$")[1].replaceAll(",",
											"");
								}
							} else if (headerKey.equals("Rev PAR")) {
								double revPARDblvalue = (Double.parseDouble(totalRevenueValueForDetailedView)
										/ Double.parseDouble(getActiveRoomNumber.get(0)));
								detailViewValue = String.valueOf(revPARDblvalue);
								detailViewValue = report.getValueAfterTrailingAndRoundUp(detailViewValue);

							} else if (headerKey.equals("Avg Daily Rate")) {
								double AvgDailyRate = Double.parseDouble(totalRevenueValueForDetailedView)
										/ Double.parseDouble(String.valueOf(totalRoomNightsForDetailedView));
								detailViewValue = String.valueOf(AvgDailyRate);
								detailViewValue = report.getValueAfterTrailingAndRoundUp(detailViewValue);

							} else if (headerKey.equals("Avg Stay")) {
								double avgStayValue = Double.parseDouble(String.valueOf(totalRoomNightsForDetailedView))
										/ (Double.parseDouble(String.valueOf(totalReservationsForDetailedView)));
								detailViewValue = String.valueOf(avgStayValue);
								detailViewValue = report.getValueAfterTrailingAndRoundUp(detailViewValue);
							}
							detailedViewValuesMapForVerification.put(headerKey, detailViewValue);
						}
						detailedViewTotalReportAfterActionForAllTheDatesForVerifyData.put(dayEntry,
								detailedViewValuesMapForVerification);
					}

					app_logs.info("Expected Detail View Details : "
							+ detailedViewTotalReportAfterActionForAllTheDatesForVerifyData);
					app_logs.info("Found Detail View Details : " + detailedViewTotalReportAfterActionForAllTheDates);
					for (Entry<String, HashMap<String, String>> detailViewData : detailedViewTotalReportAfterActionForAllTheDatesForVerifyData
							.entrySet()) {
						String key = detailViewData.getKey();
						HashMap<String, String> value = detailViewData.getValue();
						test_steps.add("Verify Detail View Data For Day : " + key);
						for (Entry<String, HashMap<String, String>> detailedViewReportEntry : detailedViewTotalReportAfterActionForAllTheDates
								.entrySet()) {
							String headerKey = detailedViewReportEntry.getKey();
							HashMap<String, String> headerValue = detailedViewReportEntry.getValue();
							if (key.equals(headerKey)) {
								for (Map.Entry<String, String> entry1 : value.entrySet()) {
									String expectedKey = entry1.getKey();
									String expectedHeadervalue = entry1.getValue();
									test_steps.add("Expected " + expectedKey + " : " + expectedHeadervalue);
									app_logs.info("Expected " + expectedKey + " : " + expectedHeadervalue);
									for (Map.Entry<String, String> detailViewReportEntry : headerValue.entrySet()) {
										String foundKey = detailViewReportEntry.getKey();
										String foundHeaderValue = detailViewReportEntry.getValue();
										if (expectedKey.equalsIgnoreCase(foundKey)) {
											test_steps.add("Found " + foundKey + " : " + foundHeaderValue);
											app_logs.info("Found " + foundKey + " : " + foundHeaderValue);
											if (foundHeaderValue.equals(expectedHeadervalue)) {
												test_steps.add(
														"Successfully verified " + foundKey + " values are matching.");
											} else {
												test_steps.add("Failed : " + foundKey + " values are mismatching.");
											}
											break;
										}
									}
								}
							}
						}

					}
					test_steps.add("Successfully verified detailed view data values are matching for : <b>"
							+ commonGroupNetByOption);
				}

			} catch (Exception e) {
				Utility.count = 0;
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Run Report", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				Utility.count = 0;

				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
					Utility.updateReport(e, "Failed to run Report", testName, "Getting details after run Report",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		else if (stayDateRangeOption.equalsIgnoreCase("Last Month") || stayDateRangeOption.equalsIgnoreCase("Last Week")
				|| stayDateRangeOption.equalsIgnoreCase("Month To Date")|| stayDateRangeOption.equalsIgnoreCase("Year To Date")
				|| stayDateRangeOption.equalsIgnoreCase("Last Year")) {
			
			double activeRoomNumber = 0;
			String getLastMonth = "";
			switch (stayDateRangeOption) {
			case"Last Week":
				activeRoomNumber = 7*(Double.parseDouble(getActiveRoomNumber.get(0)));
				break;
			case"Last Month":
				 getLastMonth = ESTTimeZone.getLastMonth("MM", propertyTimeZone,-1);
				int days = ESTTimeZone.getDaysInYearAndMonth(getLastMonth,"MM" ,propertyTimeZone);
				activeRoomNumber = days*(Double.parseDouble(getActiveRoomNumber.get(0)));
				break;
				
			case"Month To Date":
				 getLastMonth = ESTTimeZone.getLastMonth("MM", propertyTimeZone,0);
				 activeRoomNumber = (Integer.parseInt(ESTTimeZone.DateFormateForLineItem(0,"dd",propertyTimeZone)))*(Double.parseDouble(getActiveRoomNumber.get(0)));
				break;
			case"Year To Date":
				 activeRoomNumber = (ESTTimeZone.getDaysInYearTillToday("dd",propertyTimeZone))*(Double.parseDouble(getActiveRoomNumber.get(0)));
				break;

			case"Last Year":
				String getLastYear = ESTTimeZone.getLastYear("yyyy", propertyTimeZone);
				days = ESTTimeZone.getDaysInYear(getLastYear,"dd" ,propertyTimeZone);
				activeRoomNumber = days*(Double.parseDouble(getActiveRoomNumber.get(0)));
				break;
			}

			test_steps.add("Clicked on reports icon");
			nav.ReportsV2(driver);
			test_steps.add("Successfully navigated to reports page.");
			report.navigateToNetSalesReport(driver, test_steps);
			test_steps.add("Navigated to Net Sales reports page");
			allTabs = new ArrayList<>(driver.getWindowHandles());
			reportsTab = allTabs.get(1);
			applicationTab = allTabs.get(0);
			Utility.switchTab(driver, reportsTab);

			report.selectDateRange(driver, CheckInDate, CheckOutDate, stayDateRangeOption, test_steps);
			test_steps.add("Select Group Net Sales By Option : " + groupNetSalesByOption);
			report.selectGroupNetSalesByOption(driver, test_steps, groupNetSalesByOption);
			app_logs.info("before selecting groupNetSalesByOption");
			report.selectSortReportByOption(driver, test_steps, sortReportByOption);
			report.selectGroupRowsByOption(driver, test_steps, groupRowsByOption);
			report.clickOnRunReport(driver);
			Wait.wait15Second();
			
			
			HashMap<String, HashMap<String, String>> getChannelData = new LinkedHashMap();
			ArrayList<String> numberOfChannel = report.getNumberOfChanelFromSummaryViewPanel(driver);
			for (int i = 0; i < numberOfChannel.size(); i++) {
				/*getChannelData.put(numberOfChannel.get(i), report.getSummaryViewDataForNetSaleReport(driver,
						numberOfChannel.get(i), groupNetSalesByOption));*/
				getChannelData.put(numberOfChannel.get(i), report.getSummaryViewDataForNetSaleReport(driver,
						numberOfChannel.get(i)));

			}
			HashMap<String, String> getTotalData = getChannelData.get("Total");
			String getTotalNetRoomNights = getTotalData.get("Net Room Nights");
			HashMap<String, Integer> netRoomNightsInSummaryView = new HashMap<String, Integer>();
			String netReservation = "";
			for (int i = 0; i < numberOfChannel.size() - 1; i++) {
				test_steps.add("======Verify summary view data for " + numberOfChannel.get(i)+"======");
				for (Map.Entry<String, String> entry : getChannelData.get(numberOfChannel.get(i)).entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (key.equals("Net Reservations")) {
						netReservation = value;
						test_steps.add("Net reservation: " + netReservation);
					}
					if (key.equals("Net Room Nights")) {
						totalReservationsForSummaryView = Integer.parseInt(value);
						test_steps.add("Net room nights: " + totalReservationsForSummaryView);
					} else if (key.equals("Booking Nights %")) {
						String expectedBookingNights = String
								.format("%.2f",
										(Double.parseDouble(String.valueOf(totalReservationsForSummaryView))
												/ Double.parseDouble(String.valueOf(getTotalNetRoomNights))) * 100)
								+ "%";
						String getBookingNights = entry.getValue();
						test_steps.add("Expected booking night %: " + expectedBookingNights);
						test_steps.add("Found: " + getBookingNights);
						if (getBookingNights.equals(expectedBookingNights)) {
							test_steps.add("Verified booking night %");
						} else {
							test_steps.add("Failed: Booking night % is mismatching");

						}

					} else if (key.equals("Total Revenue")) {
						totalRevenueValue = value.replaceAll(",", "").replace("$", "").replace("", "");
						test_steps.add(value);
					} else if (key.equals("Room Revenue")) {
						value = "$" + value;
						test_steps.add("Room revenue: " + value);
					} else if (key.equals("Rev PAR")) {
						double revPARDblvalue = (Double.parseDouble(totalRevenueValue)
								/ activeRoomNumber);
						value = String.valueOf(revPARDblvalue);
						value = report.getValueAfterTrailingAndRoundUp(value);
						test_steps.add("Expected Rev PAR:" + value);
						test_steps.add("Found: " + entry.getValue());
						if (entry.getValue().equals(value)) {
							test_steps.add("Successfully verified Rev PAR");
						} else {
							test_steps.add("Failed: Rev PAR is mismatching!");
						}
					} else if (key.equals("Avg Daily Rate")) {
						double AvgDailyRate = Double.parseDouble(totalRevenueValue)
								/ Double.parseDouble(String.valueOf(totalReservationsForSummaryView));
						value = String.valueOf(AvgDailyRate);
						value = report.getValueAfterTrailingAndRoundUp(value);
						test_steps.add("Expected Avg Daily Rate: " + value);
						test_steps.add("Found: " + entry.getValue());
						if (entry.getValue().equals(value)) {
							test_steps.add("Successfully verified Avg Daily Rate");
						} else {
							test_steps.add("Failed: Avg Daily Rate is mismatching!");
						}
					} else if (key.equals("Avg Stay")) {
						double avgStayValue = Double.parseDouble(String.valueOf(totalReservationsForSummaryView))
								/ (Double.parseDouble(String.valueOf(netReservation)));
						String value1 = String.valueOf(avgStayValue);
						value1 = report.getValueAfterTrailingAndRoundUp(value1);
							test_steps.add("Expected Avg Stay: " + value1);
							test_steps.add("Found: " + value);
							if (entry.getValue().equals(value1)) {
								test_steps.add("Verified Avg Stay");
							} else {
								test_steps.add("Failed: Avg Stay is mismatching");
							}
						
					}
					expectedSummaryViewReportAfterAction2.put(key, value);

				}
				netRoomNightsInSummaryView.put(numberOfChannel.get(i), totalReservationsForSummaryView);
			}
			for (int i = 0; i < numberOfChannel.size() - 1; i++) {
				int netRes = 0;
				int netRoomsNights = 0;
				double totalRevenue = 0;
				double cancelRes = 0;
				double otherRevenue = 0;
				double roomRevenue = 0;

				ArrayList<String> getDaysBaseOnChannel = report.getNumberOfChanelFromDetailsView(driver,
						numberOfChannel.get(i));
				test_steps.add("======= Validating details view data for " + numberOfChannel.get(i)+" =======");
				app_logs.info("======= Validating details view data for " + numberOfChannel.get(i)+" =======");
				Wait.wait1Second();
				for (String day : getDaysBaseOnChannel) {
					test_steps.add("======= Validating details view data for " + day+" =======");

					for (Map.Entry<String, String> entry : report
							.getDetailedViewTotalDataForNetSaleReportForRowBy(driver, day, numberOfChannel.get(i)).entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						if (key.equals("Net Reservation")) {
							netRes = Integer.parseInt(value);
							test_steps.add("Net Reservation: " + value);
						}
						else if (key.equals("Net Room Nights")) {
							netRoomsNights = Integer.parseInt(value);
							test_steps.add("Net room nights: " + netRoomsNights);
						} else if (key.equals("Cancel")) {
							value = value.replace("%", "");
							test_steps.add("Cancel %: " + value);
							cancelRes = cancelRes + (Double.parseDouble(value));
						} else if (key.equals("Booking Nights")) {
							String expectedBookingNights = String.format("%.2f",
									(Double.parseDouble(String.valueOf(netRoomsNights))
											/ netRoomNightsInSummaryView.get(numberOfChannel.get(i))) * 100)
									+ "%";
							test_steps.add("Expected booking night %: " + expectedBookingNights);
							test_steps.add("Found: " + entry.getValue());
							if (entry.getValue().equals(expectedBookingNights)) {
								test_steps.add("Verified booking night %");
							} else {
								test_steps.add("Failed: Booking night % is mismatching");

							}

						} else if (key.equals("Total Revenue")) {
							value = value.split("\\$")[1].replace(",", "").replace(" ", "");
							value = value.replaceAll(",", "");
							value = "$" + value;
							test_steps.add(value);
							totalRevenueValue = value.split("\\$")[1].replaceAll(",", "");
							totalRevenue = totalRevenue + (Double.parseDouble(totalRevenueValue));
						} else if (key.equals("Other Revenue")) {
							value = value.split("\\$")[1];
							value = value.replaceAll(",", "");
							value = "$" + value;
							test_steps.add(value);
							totalRevenueValue = value.split("\\$")[1].replaceAll(",", "");
							otherRevenue = otherRevenue + (Double.parseDouble(totalRevenueValue));
						} else if (key.equals("Room Revenue")) {
							test_steps.add("Room revenue: " + value);
							value = value.split("\\$")[1];
							value = value.replaceAll(",", "");
							roomRevenue = roomRevenue + (Double.parseDouble(value));

						} else if (key.equals("Rev PAR")) {
							double activeRoom = 0;
							if (stayDateRangeOption.equalsIgnoreCase("Last Week")&&groupRowsByOption.equalsIgnoreCase("Week")) {
								activeRoom = activeRoomNumber;
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Last Week")&&groupRowsByOption.equalsIgnoreCase("Day")) {
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Last Month")&&groupRowsByOption.equalsIgnoreCase("Day")) {
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Last Month")&&groupRowsByOption.equalsIgnoreCase("Week")) {
								//activeRoom = 7*(Double.parseDouble(getActiveRoomNumber.get(0)));
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));

							}
							else if(stayDateRangeOption.equalsIgnoreCase("Last Month")&&groupRowsByOption.equalsIgnoreCase("Month")) {
								activeRoom = activeRoomNumber;
							}
							

							else if(stayDateRangeOption.equalsIgnoreCase("Month To Date")&&groupRowsByOption.equalsIgnoreCase("Day")) {
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Month To Date")&&groupRowsByOption.equalsIgnoreCase("Week")) {
								//activeRoom = 7*(Double.parseDouble(getActiveRoomNumber.get(0)));
								//activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
								activeRoom = activeRoomNumber;
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Month To Date")&&groupRowsByOption.equalsIgnoreCase("Month")) {
								activeRoom = activeRoomNumber;
							}

							
							else if(stayDateRangeOption.equalsIgnoreCase("Year To Date")&&groupRowsByOption.equalsIgnoreCase("Day")) {
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
							}
							else if(stayDateRangeOption.equalsIgnoreCase("Year To Date")&&groupRowsByOption.equalsIgnoreCase("Week")) {
								activeRoom = 7*(Double.parseDouble(getActiveRoomNumber.get(0)));
								//activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));

							}
							else if(stayDateRangeOption.equalsIgnoreCase("Last Year")&&groupRowsByOption.equalsIgnoreCase("Day")) {
								activeRoom = Double.parseDouble(getActiveRoomNumber.get(0));
							}

							
							double revPARDblvalue = (Double.parseDouble(totalRevenueValue)
									/ activeRoom);
							value = String.valueOf(revPARDblvalue);
							value = report.getValueAfterTrailingAndRoundUp(value);
							test_steps.add("Active room number:" + activeRoom);
							test_steps.add("Expected Rev PAR:" + value);
							test_steps.add("Found: " + entry.getValue());
							if (entry.getValue().equals(value)) {
								test_steps.add("Verified Rev PAR");
							} else {
								test_steps.add("Failed: Rev PAR is mismatching!");
							}
						} else if (key.equals("Avg Daily Rate")) {
							double AvgDailyRate = Double.parseDouble(totalRevenueValue)
									/ Double.parseDouble(String.valueOf(netRoomsNights));
							value = String.valueOf(AvgDailyRate);
							value = report.getValueAfterTrailingAndRoundUp(value);
							test_steps.add("Expected Avg Daily Rate: " + value);
							test_steps.add("Found: " + entry.getValue());
							if (entry.getValue().equals(value)) {
								test_steps.add("Verified Avg Daily Rate");
							} else {
								test_steps.add("Failed: Avg Daily Rate is mismatching!");
							}
						} else if (key.equals("Avg Stay")) {
							double avgStayValue = Double.parseDouble(String.valueOf(netRoomsNights))
									/ (Double.parseDouble(String.valueOf(netRes)));
							value = String.valueOf(avgStayValue);
							   value = report.getValueAfterTrailingAndRoundUp(value);
								test_steps.add("Expected Avg Stay: " + value);
								test_steps.add("Found: " + value);
								if (entry.getValue().equals(value)) {
									test_steps.add("Verified Avg Stay");
								} else {
									test_steps.add("Failed: Avg Stay is mismatching");
								}
							
						}
					}
				}
			}
		}
		// Generate Report
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("NetSalesReportValidation", excelReservationV2);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
