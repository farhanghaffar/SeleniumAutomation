package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightAudit;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Rules;
import com.innroad.inncenter.pageobjects.Season;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyThatMostStrictRuleIsConsideredWhileCreatingSingleReservation extends TestCore{
	private WebDriver driver = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCatagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;


	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData")
	public void verifyThatMostStrictRuleIsConsideredWhileCreatingSingleReservation(String testCaseID, String ratePlanName,
			String roomClassName) throws  InterruptedException, IOException, Exception {
		
		if(!Utility.validateString(testCaseID)) {
			caseId.add("848695");
			statusCode.add("4");
		}else {
			String[] testcase=testCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		testName = "VerifyThatMostStrictRuleIsConsideredWhileCreatingSingleReservation";
		testDescription = "Verify that most strict rule is considered and it is shown in red if violated while creating a single room reservation from reservation listing page.</br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848695' target='_blank'>"
				+ "Click here to open TestRail: C848695</a>";
		testCatagory = "RulesVerification";
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		ReservationHomePage homePage = new ReservationHomePage();
		Rules rules = new Rules();
		Season seasonObj = new Season();
		String randomString = Utility.GenerateRandomString();
		
		String source = "innCenter";			
		String timeZone = "US/Eastren";
		String maxAdults = "1";
		String maxChildren = "0";
		
		ArrayList<String> checkInDates = new ArrayList<>();
		HashMap<String, Boolean> getDaysToCheck = new HashMap<>();
		boolean isRuleShowing = true;
		String season = "FirstRuleSeason";
		String minimumStay = "Minimum Stay";
		String ruleType = minimumStay;
		String ruleName1 = "first" + minimumStay + randomString;
		String misStayRule = "3";
		
		String ruleName2 = "second" +  minimumStay + randomString;
		String minStayRule2 = "4";
		String season2 = "secondRuleSeason";
		printString("getDaysToCheck : " + getDaysToCheck);
		int b=0;
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			String getDay = Utility.getCurrentDate("EEE");
			for(b=0; b < 7; b++) {
				getDay = Utility.parseDate(Utility.getDatePast_FutureDate(b), "MM/dd/yyyy", "EEE");				
				if(getDay.equalsIgnoreCase("Fri")) {
					break;
				}
			}
			printString("getDay : " + getDay);
			
			checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(b), "MM/dd/yyyy", "MM/dd/yyyy"));
			checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(b + 3), "MM/dd/yyyy", "MM/dd/yyyy"));
			printString("checkInDates : " + checkInDates);

			driver = getDriver();
			loginClinent3281(driver);
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
			getDaysToCheck.put("Mon", true);
			getDaysToCheck.put("Tue", true);
			getDaysToCheck.put("Wed", true);
			getDaysToCheck.put("Thu", true);
			getDaysToCheck.put("Fri", true);
			getDaysToCheck.put("Sat", false);
			getDaysToCheck.put("Sun", false);

			navigation.Inventory(driver, testSteps);
			testSteps.add("Deleting all rules");
			
			navigation.Seasons(driver);
			testSteps.add("Navigate to Seasons");
			
			seasonObj.NewSeasonButtonClick(driver);
			testSteps.add("Click New Season Button");

			seasonObj.enterSeasonName(driver, testSteps, season);			
			seasonObj.selectSeasonStatus(driver, testSteps, "Active");
			
			seasonObj.SelectStartDate(driver, 0);
			testSteps.add("Selecte start date : " + Utility.getDatePast_FutureDate(0));

			seasonObj.SelectEndDate(driver, 20);
			testSteps.add("Selecte end date : " + Utility.getDatePast_FutureDate(20));

			seasonObj.selectDays(driver, getDaysToCheck, testSteps);
			
			seasonObj.SaveSeason(driver);
			testSteps.add("Click on save button");

			rules.clickCloseTab(driver, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			getDaysToCheck.clear();
			getDaysToCheck.put("Mon", false);
			getDaysToCheck.put("Tue", false);
			getDaysToCheck.put("Wed", false);
			getDaysToCheck.put("Thu", false);
			getDaysToCheck.put("Fri", false);
			getDaysToCheck.put("Sat", true);
			getDaysToCheck.put("Sun", true);
			Utility.printString("getDaysToCheck : " + getDaysToCheck);
			navigation.Inventory(driver, testSteps);
			testSteps.add("Deleting all rules");
			
			navigation.Seasons(driver);
			testSteps.add("Navigate to Seasons");

			seasonObj.NewSeasonButtonClick(driver);
			testSteps.add("Click New Season Button");

			seasonObj.enterSeasonName(driver, testSteps, season2);			
			seasonObj.selectSeasonStatus(driver, testSteps, "Active");
			
			seasonObj.SelectStartDate(driver, 0);
			testSteps.add("Selecte start date : " + Utility.getDatePast_FutureDate(0));

			seasonObj.SelectEndDate(driver, 20);
			testSteps.add("Selecte end date : " + Utility.getDatePast_FutureDate(20));

			seasonObj.selectDays(driver, getDaysToCheck, testSteps);
			
			seasonObj.SaveSeason(driver);
			testSteps.add("Click on save button");
			
			rules.clickCloseTab(driver, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCatagory, testSteps);
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create season", "CreateSeason", "CreateSeason", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		//Case : 824566
		try {
			testSteps.add("===== <a href='https://innroad.testrail.io/index.php?/cases/view/686344' target='_blank'>"
					+ "<b>Display of rules</b> </a> =====");
		
			navigation.Inventory(driver, testSteps);
			testSteps.add("Clicked on inventory");
		
			rules.deleteNRules(driver);
			testSteps.add("All rules deleted successfully");
			
			testSteps.add("Creating new rule with name (" + ruleName1 + ") and type (" + ruleType + ").");
			rules.create_Rule(driver, ruleName1, ruleType, ruleName1, misStayRule, roomClassName, source, ratePlanName, season, "Active", getDaysToCheck);
			testSteps.add("New rule with name (" + ruleName1 + ") and type (" + ruleType + ") created successfully.");
			
			testSteps.add("Creating new rule with name (" + ruleName1 + ") and type (" + ruleType + ").");
			rules.create_Rule(driver, ruleName2, ruleType, ruleName2, minStayRule2, roomClassName, source, ratePlanName, season2, "Active", getDaysToCheck);
			testSteps.add("New rule with name (" + ruleName1 + ") and type (" + ruleType + ") created successfully.");

			navigation.cpReservationBackward(driver);
			testSteps.add("Back to reservations page.");
			
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
	
		String isAssign = "Yes";
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
				isAssign = "Yes";
				
				homePage.verifyRulesPopupWhileSelectingRoom(driver, testSteps, roomClassName, isAssign, "", ruleName2, isRuleShowing);
	
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
		comments = "Verified that most strict rule is considered and it is shown in red if violated while creating a single room reservation from reservation listing page.";
		statusCode.add(0, "1");
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
		return Utility.getData("VerifyHighestMiStayRulesApplied", excel_Swarna);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
