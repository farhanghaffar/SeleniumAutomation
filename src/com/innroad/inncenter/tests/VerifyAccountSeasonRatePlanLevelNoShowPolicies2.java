package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAccountSeasonRatePlanLevelNoShowPolicies2 extends TestCore{
	
	private WebDriver driver = null;
	String test_catagory, test_description, policyToValidate, policyAttrDisplayed, policyAttrValueDisplayed, 
	policyDesc, policyType ="No Show", noShowAmount, paymentsFromFolio, nameOnCard, cardExpDate, policyName;
	int resCount;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> roomClasses = new ArrayList<>();
	ArrayList<String> ratePlans = new ArrayList<>();
	ArrayList<String> ratePlanLevelPolicies = new ArrayList<>();
	ArrayList<String> seasonLevelPolicies = new ArrayList<>();

	HashMap<String, String> highestAmountOfPolicyDetails = new HashMap<>();
	HashMap<String, ArrayList<String>> allPoliciesDetails = new HashMap<>();
	HashMap<String, String> allChargesFromFolio = new HashMap<>();
	HashMap<String, ArrayList<String>> descWhileCreatePolicy = new HashMap<>();
	
	String testName = this.getClass().getSimpleName().trim();

//	@BeforeTest(alwaysRun=true)
//	public void checkRunMode() {
//		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
//		if (!Utility.isExecutable(testName, excel))
//			throw new SkipException("Skipping the test - " + testName);
//	}
	
	@Test(dataProvider = "getData", groups = "Rates V2")
	public void verifyAccountSeasonRatePlanLevelNoShowPolicies(String checkInDate, String checkOutDate, String roomClassName, 
			String adults, String children, String ratePlanName, String isAccountPolicyCreate, String isAccountPolicyApplicable,
			String policyAttr1, String policyAttr2, String policyAttrValue, String resType, String accountName, String accountType,  
			String accountFirstName, String accountLastName,  String salutation, 
			String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone, String email,
			String address1, String address2, String address3, String city,
			String country, String state, String postalCode, String isGuesProfile, String paymentMethod, 
			String cardNumber, String referral) throws Exception {
		test_description = "VerifyAccountOrSeasonOrRatePlanLevelNoShowPolicyAppliedOnReservation";
		test_catagory = "VerifyAccountOrSeasonOrRatePlanLevelNoShowPolicyAppliedOnReservation";
		CPReservationPage reservationPage = new CPReservationPage();
		NightlyRate nightlyRate = new NightlyRate();
		Navigation navigation = new Navigation();
		Policies policies = new Policies();
		RatesGrid ratesGrid = new RatesGrid();
		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}			
			String randomNum = Utility.GenerateRandomNumber();
			nameOnCard = guestFirstName.split("\\|")[0]+" "+guestLastName.split("\\|")[0];
			cardExpDate = Utility.getFutureMonthAndYearForMasterCard();
			guestLastName = guestLastName.replace("NoShow", "NoShow"+randomNum);
			policyName = "NoShowPolicy"+randomNum;
			resCount = guestFirstName.split("\\|").length;
			if ( !(Utility.validateInput(checkInDate)) ) {
				for (int i = 0; i < resCount; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(3), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			}else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = Utility.addInputDataToStringWithSeperator(checkInDates);
			checkOutDate = Utility.addInputDataToStringWithSeperator(checkOutDates);
			roomClasses = Utility.splitInputData(roomClassName);
			ratePlans = Utility.splitInputData(ratePlanName);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to update test data", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to update test data", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			driver = getDriver();
			Login login = new Login();
//			loginWPI(driver);
			login.login(driver, envURL, "autorate", "autouser", "Auto@123");
			reservationPage.searchWithGuestName(driver, test_steps, "Primary NoShow322177");
			reservationPage.clickOnGuestName(driver, test_steps);
			reservationPage.makeReservationNoShowWithPaymentProcess(driver, test_steps, policyType, "400", "100");

		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login to inncenter", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login to inncenter", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Capturing season level no show policies for all rate plans <b>"+ratePlans+"</b> ==========");
			seasonLevelPolicies = nightlyRate.getMultipleRatePlansSeasonLevelPolicies(driver, test_steps, 
					policyType, ratePlans, roomClasses, checkInDates);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to capture all season level no show policies", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to capture all season level no show policies", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			navigation.clickPoliciesAfterRateGridTab(driver,test_steps);
			if (isAccountPolicyCreate.equalsIgnoreCase("Yes")) {
				test_steps.add("========== Creating new policy for account ==========");
				Wait.waitUntilPageLoadNotCompleted(driver, 5);
				descWhileCreatePolicy = policies.createPolicies(driver, test_steps, "", "", policyType, "", "", "", policyName, 
						policyAttr1, policyAttrValue, policyAttr2, "", "", "No", "");				
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create new policy for account", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Capturing all policies attribute and attribute values ==========");
			allPoliciesDetails = policies.getAllPoliciesDetails(driver, test_steps, policyType, seasonLevelPolicies);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to capture no show policy attributes and values", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to capture no show policy attributes and values", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Creating a new reservation ==========");
			reservationPage.createReservation(driver, test_steps, resType, accountFirstName, accountLastName, 
					checkInDate, checkOutDate, adults, children, ratePlanName, "", roomClassName, accountName, 
					salutation, guestFirstName, guestLastName, phoneNumber, altenativePhone, email, accountType, 
					address1, address2, address3, city, country, state, postalCode, isGuesProfile, "", "", referral, 
					paymentMethod, cardNumber, nameOnCard, cardExpDate, policyName, isAccountPolicyApplicable);
			
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create a new reservation", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create a new reservation", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("========== Evaluating which policy to be applied on reservation ==========");
			if (isAccountPolicyApplicable.equalsIgnoreCase("Yes")) {
				policyToValidate = policyName;
				policyAttrDisplayed = policyAttr1;
				policyAttrValueDisplayed = policyAttrValue;
				policyDesc = descWhileCreatePolicy.get(policyName).get(0);
				test_steps.add("Season level policy should be applied  for check-in date as <b>"+checkInDate+"</b>");
			}else if (!(allPoliciesDetails.get("Names").isEmpty())) {
				reservationPage.click_Folio(driver, test_steps);
				highestAmountOfPolicyDetails = reservationPage.getHighestAmountOfPolicy(driver, test_steps, allPoliciesDetails, 
						checkInDates, checkOutDates);
				policyToValidate = highestAmountOfPolicyDetails.get("Name");
				policyAttrDisplayed = highestAmountOfPolicyDetails.get("Attribute");
				policyAttrValueDisplayed = highestAmountOfPolicyDetails.get("AttributeValue");
				policyDesc = highestAmountOfPolicyDetails.get("Description");
			}
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Evaluate which policy to be applied on reservation", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		try {
			test_steps.add("===================== Make  Reservation as No Show =====================");
			reservationPage.makeReservationNoShowWithPaymentProcess(driver, test_steps, policyType, paymentsFromFolio, noShowAmount);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			if (Utility.validateString(policyToValidate)) {
				test_steps.add("===================== Verifying associated No Show policy details at Policies "
						+ "And Disclaimers tab =====================");
				reservationPage.verifyNoShowPolicy(driver, test_steps, policyToValidate, policyDesc);				
			}else {
				test_steps.add("===================== Verifying associated No policy appllied for No Show at Policies "
						+ "And Disclaimers tab =====================");
				reservationPage.verifyNoShowPolicy(driver, test_steps, "No Policy", "");
			}
			test_steps.add("===================== Verifying No show amount at folio =====================");
			reservationPage.click_Folio(driver, test_steps);
//			if (Utility.validateString(policyToValidate)) {
//				reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, noShowAmount);
//			}else {
//				reservationPage.verifyNoShowAmountAtFolio(driver, test_steps, "");
//			}
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to make reservation as no show", testName, test_catagory, driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}
	
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyNoShowPolicy", excel);
	}
	
	@AfterClass(alwaysRun=true)
    public void closeDriver() {
        driver.quit();
    }


}
