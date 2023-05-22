package com.innroad.inncenter.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyWithoutTheAvailabilityRulesPermission extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	Navigation nav = new Navigation();
	Admin admin = new Admin();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void verifyRulesBreakForQuoteGroupAndAccount(String testCaseID,String delim,String reservationType, String ratePlanName,
			String roomClassName, String roomClassAbb) throws  InterruptedException, IOException, Exception {
		
		Utility.DELIM = delim;

		if(!Utility.validateString(testCaseID)) {
			caseId.add("848246");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		//CustomFolio
		testName = "Verify rules from groups/account/quote";
		testDescription = "Verify rules from groups/account/quote</br>";
		testCatagory = "RulesVerification";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		Account CreateTA = new Account();
		ReservationHomePage homePage = new ReservationHomePage();
		Groups group = new Groups();
		RatesGrid rateGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		ReservationSearch reservationSearch = new ReservationSearch();
		
		String randomString = Utility.GenerateRandomString();
		HashMap<String, String> ratePlanData;
		try {
			ratePlanData = Utility.getExcelData(
					testDataPath + File.separator + "CentralparkSanityTestData.xlsx",
					"CreateNightlyRatePlanV2");
		} catch (Exception e) {
			System.out.println("Exception came");
			ratePlanData = Utility.getExcelData(".\\test-data\\CentralparkSanityTestData.xlsx",
					"CreateNightlyRatePlanV2");
		}

		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		
		String isRatePlanRistrictionReq = "false";
		String RistrictionType = "Length of stay|Booking window|Promo code";
		String isMinStay = ratePlanData.get("isMinStay");
		String MinNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String MaxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String MoreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String WithInDaysCount = ratePlanData.get("WithInDaysCount");
		String PromoCode = "abc123";
		
		String isPolicesReq = "false";
		String PoliciesType = "";
		String PoliciesName = "";

		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight = ratePlanData.get("RatePerNight");
		String MaxAdults = ratePlanData.get("MaxAdults");
		String MaxPerson = ratePlanData.get("MaxPerson");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		

		String salutation = "Mr."; 
		String guestFirstName = "VerifyRes" + randomString; 
		String guestLastName = "Realization" + randomString;
		
		String guestName = guestFirstName + " " + guestLastName;
		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com"; 
		String accountType = "Corporate/Member Accounts"; 
		String account = "CorporateAccount" + Utility.generateRandomString(); 
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1"; 
		String address2 = "test2";
		String address3 = "test3"; 
		String city = "New york";
		String paymentType = "MC"; 
		
		String cardNumber = "5454545454545454"; 
		String nameOnCard = guestName; 
		String cardExpDate = "12/23";
		String marketSegment = "GDS"; 
		String referral = "Other";
		String postalCode = "12345"; 
		String isGuesProfile = "No";
		String roomNumber = "";
		String maxAdults = "1";
		String maxChildren = "0"; 
		String country = "United States"; 
		String state = "Alaska";
		
		String reservationNumber = "";
		String reservationStatus = "";
		
		String groupReferral = "Walk In"; 
		String groupFirstName = "Bluebook" + randomString; String groupLastName = "Group" + randomString;
		String accountName = groupFirstName + groupLastName;
		
		String ruleName = null,ruleType = null;
		HashMap<String, String> reservationNumberMap = new HashMap<>();
		boolean isRuleShowing = false;
		HashMap<String, Boolean> getDaysToCheck = new HashMap<>();
		
		String isSerasonLevelRules = "Yes";
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses = roomClassName;
		String isSeasonPolicies = "false";
		String SeasonPolicyType = "";
		String SeasonPolicyValues = "";
		String MaxPersons = ratePlanData.get("MaxPersons");
		String SeasonRuleType = ratePlanData.get("SeasonRuleType");
		String RoomClasses = roomClassName;
		
		String SeasonRuleMinStayValue = "3";
		String isSeasonRuleOnMonday = "Yes";
		String isSeasonRuleOnTuesday = "Yes";
		String isSeasonRuleOnWednesday = "Yes";
		String isSeasonRuleOnThursday = "Yes";
		String isSeasonRuleOnFriday = "Yes";
		String isSeasonRuleOnSaturday = "Yes";
		String isSeasonRuleOnSunday = "Yes";


		ArrayList<String> checkInDates = new ArrayList<>();
		checkInDates.add(Utility.getCurrentDate("MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "MM/dd/yyyy"));
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "MM/dd/yyyy"));

		String minimumStay = SeasonRuleMinStayValue + " night minimum";
		String noCheckIn = "No check-in";
		String noCheckOut = "No check-out";
		SeasonRuleType = "Min nights|No check-in|No check-out";
		isRuleShowing = true;
		ruleName = minimumStay + "," + noCheckIn + "," +  noCheckOut;
		printString("getDaysToCheck : " + getDaysToCheck);
		String SeasonName = "RulesSeason" + randomString;
		String SeasonStartDate = Utility.getCurrentDate("dd/MM/yyyy");
		String SeasonEndDate = Utility.getNextDate(10, "dd/MM/yyyy");
		ratePlanName = ratePlanName + randomString;
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			loginWPI(driver);
			//loginNightAudit(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
			try {
				testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824734' target='_blank'>"
						+ "<b> ===== Verify Without the Availability Rules Permission ===== </b> </a>");

				nav.Admin(driver);
				nav.Roles(driver);
				admin.selectAdminstatorRole(driver, testSteps);
				admin.makeAvailabilityRulesEnableOrDisable(driver, testSteps,false);
				testSteps.add("Availability Rules entitlement is removed for the current user");
				app_logs.info("Availability Rules entitlement is removed for the current user");
				Login login = new Login();
				login.logout(driver);
				loginWPI(driver);
				//loginNightAudit(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to remove Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);					
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to remove Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);					
				} else {
					Assert.assertTrue(false);
				}
			}
		//Case : 824566
	
		try {
			// After login
			testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, testSteps);
			navigation.ratesGrid(driver);
			testSteps.add("Navigated to rateGrid");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		testSteps.add("=================== Verify Rate plan Exist or Not ======================");
		app_logs.info("=================== Verify Rate plan Exist or Not ======================");
		boolean israteplanExist = false;
		try {
			Utility.app_logs.info("RatePlanName : " + ratePlanName);
			israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, testSteps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
			if(israteplanExist) {

				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				
				testSteps.add("=================== " + "DELETE RATE PLAN".toUpperCase() + " ======================");
				app_logs.info("=================== " + "DELETE RATE PLAN".toUpperCase() + " ======================");
				
				getTestSteps.clear();
				getTestSteps = nightlyRate.deleteNightlyRatePlan(driver, ratePlanName, "Delete", getTestSteps);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("Successfully Deleted rate plan '" + ratePlanName + "'");
				app_logs.info("Successfully Deleted rate plan '" + ratePlanName + "'");

				israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, testSteps);
				Utility.app_logs.info("israteplanExist : " + israteplanExist);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delet nightly rate", testName, "DeleteNightlyRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to delet nightly rate", testName, "DeleteNightlyRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {

			testSteps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			
			try {

				testSteps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", testSteps);

				testSteps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				FolioDisplayName = FolioDisplayName + randomString;

				nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, Description, testSteps);

				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, testSteps);

				testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, testSteps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

				testSteps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, testSteps);

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						testSteps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), testSteps);
				nightlyRate.clickNextButton(driver, testSteps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), testSteps);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, testSteps);

				nightlyRate.createSeason(driver, testSteps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);

				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.selectAsDefaultRatePlan(driver, testSteps, "Yes");
				nightlyRate.clickSaveAsActive(driver, testSteps);
				Wait.wait30Second();
				testSteps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
				navigation.ReservationV2_Backward(driver);
				testSteps.add("Back to reservation page");
				app_logs.info("Back to reservation page");
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to verify mrb reservation cannot be created for past date", testName, "Reservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}
		
		try {

				testSteps.add("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
			
				reservationPage.click_NewReservation(driver, testSteps);									

				getTestSteps.clear();
				getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
				testSteps.addAll(getTestSteps);
	
				getTestSteps.clear();
				getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
				testSteps.addAll(getTestSteps);

	
				reservationPage.enter_Adults(driver, testSteps, maxAdults);
				reservationPage.enter_Children(driver, testSteps, maxChildren);
				reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
				reservationPage.clickOnFindRooms(driver, testSteps);
				String isAssign = "Yes";
				
				homePage.verifyRulesPopupWhenAvailabilityRulesDisabled(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
				homePage.VerifyNextButtonIsNotDisplaying(driver, testSteps);
				
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed reservation tab");
				app_logs.info("Closed reservation tab");
				
				statusCode.add(0, "1");
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		try {
			testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824735' target='_blank'>"
					+ "<b> ===== Verifying rules from groups/account/quote ===== </b> </a>");

				testSteps.add("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
			
				testSteps.add("========== Creating account ==========");
				app_logs.info("========== Creating account ==========");
	
				navigation.Accounts(driver);
				testSteps.add("Navigate to Accounts");
				app_logs.info("Navigate to Accounts");
				
				CreateTA.ClickNewAccountbutton(driver, accountType);
				CreateTA.AccountDetails(driver, accountType, account);
				CreateTA.ChangeAccountNumber(driver, accountNumber);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
						address1, address2, address3, email, city, state, postalCode, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
				testSteps.addAll(getTestSteps);
				
			}catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
					Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);

				} else {
					Assert.assertTrue(false);
				}
			}
	
		try {
				CreateTA.NewReservationButton(driver, test);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
				
		try {

			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			
			homePage.verifyRulesPopupWhenAvailabilityRulesDisabled(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
			homePage.VerifyNextButtonIsNotDisplaying(driver, testSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Click New Account and Enter Account Name
	try {
			app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
			testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");

			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");

			getTestSteps.clear();
			getTestSteps = group.enterrGroupName(driver, accountName);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", testName,
						"EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Number
		try {
			accountNumber = Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, accountNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Attributes
		try {
			getTestSteps.clear();
			getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Mailing Address
		try {
			getTestSteps.clear();
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
					city, state, country, postalCode);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Check Mailing Info CheckBox
		try {
			getTestSteps.clear();
			getTestSteps = group.Billinginfo(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Save Account
		try {
			getTestSteps.clear();
			getTestSteps = group.clickOnSave(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("<b> ===== CREATING 'GROUP' RESERVATION ===== </b>");
			app_logs.info("<b> ===== CREATING 'GORUP' RESERVATION ===== </b>");
		
			group.clickOnNewReservationButtonFromGroup(driver, testSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);


			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			

			homePage.verifyRulesPopupWhenAvailabilityRulesDisabled(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
			homePage.VerifyNextButtonIsNotDisplaying(driver, testSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed reservation tab");
			app_logs.info("Closed reservation tab");
						
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			nav.Admin(driver);
			nav.Roles(driver);
			admin.selectAdminstatorRole(driver, testSteps);
			admin.makeAvailabilityRulesEnableOrDisable(driver, testSteps,true);
			testSteps.add("Availability Rules entitlement is applied for the current user");
			app_logs.info("Availability Rules entitlement is applied for the current user");
			Login login = new Login();
			
			login.logout(driver);
			loginWPI(driver);
			//loginNightAudit(driver);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to apply Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to apply Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("<b> ===== VERIFYING BROKEN RULE POPOUP AFTER APPLY AVAILABILITY RULES ENTITLEMENT FOR THE CURRENT USER ===== </b>");

			testSteps.add("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
			app_logs.info("<b> ===== CREATING 'ACCOUNT' RESERVATION ===== </b>");
		
			testSteps.add("========== Creating account ==========");
			app_logs.info("========== Creating account ==========");

			navigation.Accounts(driver);
			testSteps.add("Navigate to Accounts");
			app_logs.info("Navigate to Accounts");
			
			CreateTA.ClickNewAccountbutton(driver, accountType);
			CreateTA.AccountDetails(driver, accountType, account);
			//CreateTA.ChangeAccountNumber(driver, accountNumber);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Mailinginfo(driver, test, guestFirstName, guestLastName, phoneNumber, alternativePhone,
					address1, address2, address3, email, city, state, postalCode, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.Billinginfo(driver, test, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = CreateTA.AccountSave(driver, test, account, getTestSteps);
			testSteps.addAll(getTestSteps);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new account", testName, "CreateReservation", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

	try {
			CreateTA.NewReservationButton(driver, test);
		
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to click new reservation button", testName, "CreateReservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	try {
		
			getTestSteps.clear();
			getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
			testSteps.addAll(getTestSteps);

			reservationPage.enter_Adults(driver, testSteps, maxAdults);
			reservationPage.enter_Children(driver, testSteps, maxChildren);
			reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
			reservationPage.clickOnFindRooms(driver, testSteps);
			String isAssign = "Yes";
			
			homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, "", ruleName, isRuleShowing);
			
			reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
			reservationPage.clickNext(driver, testSteps);									

			try {
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
				
			}catch (Exception e) {
			}
			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);

			reservationPage.SelectReferral(driver, referral);
			
			if(reservationType.equalsIgnoreCase("quote")) {
				reservationPage.click_Quote(driver, testSteps);					
			}else {
				reservationPage.clickBookNow(driver, testSteps);
			}
			reservationNumber = "";
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			app_logs.info("reservationNumber : " + reservationNumber);
			reservationNumberMap.put("account",reservationNumber);
			
			reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
			
			
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			
			roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
			statusCode.add(1, "1");

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	try {
		testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824744' target='_blank'>"
				+ "<b> ===== Verifying rules from groups/account/quote ===== </b> </a>");

		testSteps.add(
				"===== " + "Changing roomclass and verify rules".toUpperCase() + " =====");
		app_logs.info(
				"===== " + "Changing roomclass and verify rules".toUpperCase() + " =====");

		reservationPage.ClickEditStayInfo(driver, testSteps);
		reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

		reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
		try {
			testSteps.addAll(reservationPage.clickFindRooms(driver));
		} catch (Exception e) {
			app_logs.info("In catch");
			testSteps.add(e.toString());
		}
		
		homePage.verifyRulesWhileRoomClassUpdation(driver, testSteps, roomClassName, "Yes", account, ruleName, true, true);
		
		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed opened reservation");
		app_logs.info("Closed opened reservation");
	} catch (Exception e) {
		e.printStackTrace();
		Utility.catchException(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	} catch (Error e) {
		e.printStackTrace();
		Utility.catchError(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	}
	
	// Click New Account and Enter Account Name
	try {
		app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
		testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");

		navigation.secondaryGroupsManu(driver);
		testSteps.add("Navigate Groups");
		app_logs.info(" Navigate Groups");

		getTestSteps.clear();
		getTestSteps = group.enterrGroupName(driver, accountName);
		testSteps.addAll(getTestSteps);

	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to click new account and enter account name", testName,
					"EnterAccountName", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	// Enter Account Number
	try {
		accountNumber = Utility.GenerateRandomString15Digit();
		getTestSteps.clear();
		getTestSteps = group.enterAccountNo(driver, accountNumber);
		testSteps.addAll(getTestSteps);
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	// Enter Account Attributes
	try {
		getTestSteps.clear();
		getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
		testSteps.addAll(getTestSteps);
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	// Enter Account Mailing Address
	try {
		getTestSteps.clear();
		getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
				city, state, country, postalCode);
		testSteps.addAll(getTestSteps);
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
					"EnterAccountMailingAddress", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
					"EnterAccountMailingAddress", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	// Check Mailing Info CheckBox
	try {
		getTestSteps.clear();
		getTestSteps = group.Billinginfo(driver);
		testSteps.addAll(getTestSteps);
	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
					driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
					driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	// Save Account
	try {
		getTestSteps.clear();
		getTestSteps = group.clickOnSave(driver);
		testSteps.addAll(getTestSteps);

	} catch (Exception e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	try {

		testSteps.add("<b> ===== CREATING 'GROUP' RESERVATION ===== </b>");
		app_logs.info("<b> ===== CREATING 'GORUP' RESERVATION ===== </b>");
	
		group.clickOnNewReservationButtonFromGroup(driver, testSteps);
		
		getTestSteps.clear();
		getTestSteps = reservationPage.checkInDate(driver, checkInDates.get(0));
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.checkOutDate(driver,  checkInDates.get(1));
		testSteps.addAll(getTestSteps);


		reservationPage.enter_Adults(driver, testSteps, maxAdults);
		reservationPage.enter_Children(driver, testSteps, maxChildren);
		reservationPage.select_Rateplan(driver, testSteps, ratePlanName,"");
		reservationPage.clickOnFindRooms(driver, testSteps);
		String isAssign = "Yes";
		
		homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, accountName, ruleName, isRuleShowing);
		reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
		reservationPage.clickNext(driver, testSteps);									

		getTestSteps.clear();
		getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
				guestLastName);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
		testSteps.addAll(getTestSteps);

		reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber,nameOnCard, cardExpDate);

		reservationPage.SelectReferral(driver, referral);
		if(reservationType.equalsIgnoreCase("quote")) {
			reservationPage.click_Quote(driver, testSteps);					
		}else {
			reservationPage.clickBookNow(driver, testSteps);
		}
		reservationNumber = "";
		reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
		app_logs.info("reservationNumber : " + reservationNumber);
		reservationNumberMap.put("group",reservationNumber);
		
		reservationStatus=reservationPage.get_ReservationStatus(driver, testSteps);
		
		if(reservationType.equalsIgnoreCase("quote")) {
			reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
		}
		
		reservationPage.clickCloseReservationSavePopup(driver, testSteps);
		
		roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");	
		
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
		} else {
			Assert.assertTrue(false);
		}
	}

	try {

		testSteps.add(
				"===== " + "Changing roomclass and verify rules popup with rules permissions enabled".toUpperCase() + " =====");
		app_logs.info(
				"===== " + "Changing roomclass and verify rules popup with rules permissions enabled".toUpperCase() + " =====");

		reservationPage.ClickEditStayInfo(driver, testSteps);
		reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

		reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
		try {
			testSteps.addAll(reservationPage.clickFindRooms(driver));
		} catch (Exception e) {
			app_logs.info("In catch");
			testSteps.add(e.toString());
		}
		
		homePage.verifyRulesWhileRoomClassUpdation(driver, testSteps, roomClassName, "Yes", account, ruleName, true, true);	
				
		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed opened reservation");
		app_logs.info("Closed opened reservation");
	} catch (Exception e) {
		e.printStackTrace();
		Utility.catchException(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	} catch (Error e) {
		e.printStackTrace();
		Utility.catchError(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	}
		
	try {
		testSteps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/824734' target='_blank'>"
				+ "<b> ===== Verify Without the Availability Rules Permission ===== </b> </a>");

		nav.Admin(driver);
		nav.Roles(driver);
		admin.selectAdminstatorRole(driver, testSteps);
		admin.makeAvailabilityRulesEnableOrDisable(driver, testSteps,false);
		testSteps.add("Availability Rules entitlement is removed for the current user");
		app_logs.info("Availability Rules entitlement is removed for the current user");
		Login login = new Login();
		login.logout(driver);
		loginWPI(driver);
		//loginNightAudit(driver);
	} catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to remove Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);					
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed to remove Availability Rules entitlement for the current user", testName, "AvailabilityRulesEntitlement", driver);					
		} else {
			Assert.assertTrue(false);
		}
	}
	try {
		
		testSteps.add(
				"===== " + "Changing roomclass and verify rules with rules permissions disabled".toUpperCase() + " =====");
		app_logs.info(
				"===== " + "Changing roomclass and verify rules with rules permissions disabled".toUpperCase() + " =====");

		reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get("account"), true);
		testSteps.add("Searched and opened account reservation with number : " + reservationNumberMap.get("account"));
		app_logs.info("Searched and opened account reservation with number : " + reservationNumberMap.get("account"));

		reservationPage.ClickEditStayInfo(driver, testSteps);
		reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

		reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
		try {
			testSteps.addAll(reservationPage.clickFindRooms(driver));
		} catch (Exception e) {
			app_logs.info("In catch");
			testSteps.add(e.toString());
		}
		
		homePage.verifyRulesWhileRoomClassUpdation(driver, testSteps, roomClassName, "Yes", account, ruleName, true,false);
		
		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed opened reservation");
		app_logs.info("Closed opened reservation");
	} catch (Exception e) {
		e.printStackTrace();
		Utility.catchException(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	} catch (Error e) {
		e.printStackTrace();
		Utility.catchError(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	}
	
	try {

		testSteps.add(
				"===== " + "Changing roomclass and verify rules with rules permissions disabled".toUpperCase() + " =====");
		app_logs.info(
				"===== " + "Changing roomclass and verify rules with rules permissions disabled".toUpperCase() + " =====");

		reservationSearch.basicSearchWithResNumber(driver, reservationNumberMap.get("group"), true);
		testSteps.add("Searched and opened group reservation with number : " + reservationNumberMap.get("group"));
		app_logs.info("Searched and opened group reservation with number : " + reservationNumberMap.get("group"));

		reservationPage.ClickEditStayInfo(driver, testSteps);
		reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

		reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
		try {
			testSteps.addAll(reservationPage.clickFindRooms(driver));
		} catch (Exception e) {
			app_logs.info("In catch");
			testSteps.add(e.toString());
		}
		
		homePage.verifyRulesWhileRoomClassUpdation(driver, testSteps, roomClassName, "Yes", account, ruleName, true,false);
		
		
		reservationPage.closeReservationTab(driver);
		testSteps.add("Closed opened reservation");
		app_logs.info("Closed opened reservation");
	} catch (Exception e) {
		e.printStackTrace();
		Utility.catchException(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	} catch (Error e) {
		e.printStackTrace();
		Utility.catchError(driver, e, testDescription, testCatagory,
				"Failed to verify availability after changing roomclass", testName, testDescription,
				testCatagory, testSteps);
	}


	try {
		comments = "Verified reservation creation with and without rules availability entitlement ";
		statusCode.add(2, "1");
		comments = "Verified reservation creation with and without rules availability entitlement ";
		statusCode.add(3, "1");
		statusCode.add(4, "1");
		statusCode.add(5, "1");
		
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
	}catch (Exception e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	} catch (Error e) {
		e.printStackTrace();
		if (Utility.reTry.get(testName) == Utility.count) {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
			Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyRulesWithPermissions", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
