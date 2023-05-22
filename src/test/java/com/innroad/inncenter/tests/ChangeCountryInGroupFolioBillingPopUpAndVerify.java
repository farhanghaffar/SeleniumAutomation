package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.cache.LoadingCache;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class ChangeCountryInGroupFolioBillingPopUpAndVerify extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	public void login(String testName) {

		try {
			if (!Utility.insertTestName.containsKey(testName)) {

				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Group(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@Test(dataProvider = "getData", groups = { "groups" })
	public void changeCountryInGroupFolioBillingPopUpAndVerify(String TestCaseID,String url, String clientcode, String username,
			String password, String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String Address1, String city, String Country, String State,
			String Postalcode, String City2, String Country2, String State2, String PaymentMethod, String CardNumber,
			String ExpiryDate, String PaymentMethod2, String CardNumber2, String ExpiryDate2, String LineCategory,
			String LineAmount, String City3, String Country3, String State3) throws InterruptedException, IOException, ParseException {

		String testName = "ChangeCountryInGroupFolioBillingPopUpAndVerify";
		String test_description = "Verify selecting a different country in the folio billing info and pickup.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554188' target='_blank'>"
				+ "Click here to open TestRail: C554188</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("786844");
			caseId.add("786858");
			caseId.add("786859");
			caseId.add("786871");
			caseId.add("786872");
			caseId.add("786873");
			caseId.add("786874");
			caseId.add("786875");
			caseId.add("786877");
			caseId.add("786878");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}

		// Navigate to Groups
		try {
			Nav.Groups(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Groups
		String AccountNo = "0";
		try {
			test_steps.add("=================Create New Group================");
			app_logs.info("==================Create New Group=================");
			AccountName = AccountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, AccountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			AccountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, AccountNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.type_AccountAttributes(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.type_MailingAddrtess(driver, test, AccountFirstName, AccountLastName, Phonenumber, Address1, city,
					State, Country, Postalcode, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = group.billinginfo(driver, PaymentMethod, CardNumber, ExpiryDate, true, true);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Folio Tab
		try {
			test_steps.add("=================Navigate to Folio Tab================");
			app_logs.info("==================Navigate to Folio Tab=================");
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Payment Infp
		try {
			test_steps.add("=================Entering payment info================");
			app_logs.info("==================Entering payment info=================");
			getTest_Steps.clear();
			getTest_Steps = group.paymentInfoButtonClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.paymentInfo(driver, AccountFirstName, AccountLastName, Country2, State2, City2,
					Postalcode, PaymentMethod2, CardNumber2, ExpiryDate2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Enter Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Enter Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Folio Tab
		try {
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Payment Info
		String stateCode = null;
		try {
			test_steps.add("=================Verify payment info================");
			app_logs.info("==================Verify payment info=================");
			getTest_Steps.clear();
			getTest_Steps = group.paymentInfoButtonClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentInfoCountry(driver, Country2, State2, City2, Postalcode, PaymentMethod2,
					CardNumber2, ExpiryDate2);
			test_steps.addAll(getTest_Steps);

			stateCode = group.getStateCodeFromPaymentInfo(driver);
			Utility.app_logs.info("State : " + State2 + " Code : " + stateCode);
			test_steps.add("State : " + State2 + " Code : " + stateCode);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================Advance deposit and verfifcation================");
			app_logs.info("==================Advance deposit and verfifcation=================");
			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.setPaymentAmount(driver, "10");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickProcess_PaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.loadingImage(driver);
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod2, "10", "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.verifyCurrentBalance(driver, "-10");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			//getTest_Steps = group.verifyAuthorizationType(driver, "Credit");
			getTest_Steps = group.verifyAuthorizationType(driver, "Refund");
			test_steps.addAll(getTest_Steps);

			// getTest_Steps.clear();
			// getTest_Steps = group.switchToTransactionTab(driver);
			// test_steps.addAll(getTest_Steps);

			group.payDetailCloseBtn(driver);

			group.loadingImage(driver);
			driver.switchTo().defaultContent();

			group.loadingImage(driver);

			test_steps.add("Payment method for refund is still populated as 'capture' in group folio : Code Fix"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-6738' target='_blank'>"
					+ "Verified : NG-6738</a><br/>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Adding Folio Line Item and Verify
		try {
			test_steps.add("=================Adding Folio Line Item and Verify================");
			app_logs.info("==================Adding Folio Line Item and Verify=================");
			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.AddLineItems(driver, LineCategory, LineAmount, "1");
			test_steps.addAll(getTest_Steps);
			group.commit(driver, test);
			getTest_Steps.clear();

			/*
			 * getTest_Steps.clear(); getTest_Steps = group.verifyLineItems(driver,
			 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
			 */

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			/*
			 * getTest_Steps.clear(); getTest_Steps = group.verifyLineItems(driver,
			 * LineCategory, LineAmount, "1"); test_steps.addAll(getTest_Steps);
			 */

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// pay verify
		try {
			test_steps.add("=================Verification of Payment details================");
			app_logs.info("==================Verification of Payment details=================");
			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethodInPaymentDetailPopup(driver, PaymentMethod2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickButtonCardInfo_PaymentDetailPopup(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentDetail_CardInfo(driver, AccountFirstName + " " + AccountLastName,
					CardNumber2, ExpiryDate2, City2, stateCode, Postalcode);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.setPaymentAmount(driver, "50");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, LineCategory);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickProcess_PaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod2, "50", "2");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod2, "50", "2");
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			getTest_Steps.clear();
			getTest_Steps = group.checkSetMainPaymentMethodForAccount_PaymentInfo(driver);
			test_steps.addAll(getTest_Steps);

			test_steps
					.add("Oops Something Went Wrong error is displayed when Set as main method is checked for transaction"
							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-6801' target='_blank'>"
							+ "Verified : NG-6801</a><br/>");

			getTest_Steps.clear();
			getTest_Steps = group.navigateAccount(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfo(driver, PaymentMethod2, CardNumber2, ExpiryDate2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Set Payment Method ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Set Payment Method ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Payment Info
		try {
			test_steps.add("=================Change in Payment Info================");
			app_logs.info("==================Change in Payment Info=================");
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.paymentInfoButtonClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.paymentInfo(driver, AccountFirstName, AccountLastName, Country3, State3, City3,
					Postalcode, PaymentMethod, CardNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Enter Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Enter Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================Verifying Payment Info================");
			app_logs.info("==================Verifying Payment Info=================");
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.paymentInfoButtonClick(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentInfoCountry(driver, Country3, State3, City3, Postalcode, PaymentMethod,
					CardNumber, ExpiryDate);
			test_steps.addAll(getTest_Steps);
			group.loadingImage(driver);
			stateCode = group.getStateCodeFromPaymentInfo(driver);
			Utility.app_logs.info("State : " + State3 + " Code : " + stateCode);
			test_steps.add("State : " + State3 + " Code : " + stateCode);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Payment Info", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// pay verify
		try {
			test_steps.add("=================Verifying Payment through Card Swipe================");
			app_logs.info("==================Verifying Payment through Card Swipe=================");
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			//driver.navigate().refresh();
			try{
				getTest_Steps.clear();
				getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
				test_steps.addAll(getTest_Steps);
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethodInPaymentDetailPopup(driver, PaymentMethod);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickButtonCardInfo_PaymentDetailPopup(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentDetail_CardInfo(driver, AccountFirstName + " " + AccountLastName,
					CardNumber, ExpiryDate, City3, stateCode, Postalcode);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.selectAuthorizationType(driver, "Authorization Only");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyAuthorizationType(driver, "Authorization Only");
			test_steps.addAll(getTest_Steps);

			try{
				getTest_Steps.clear();
				getTest_Steps = group.clickAutoApply(driver);
				test_steps.addAll(getTest_Steps);
			}catch (Exception e) {
				Utility.app_logs.info("Auto Apply not Clickable");
			}
			// getTest_Steps.clear();
			// getTest_Steps = group.setPaymentAmount(driver, "50");
			// test_steps.addAll(getTest_Steps);
			//
			// getTest_Steps.clear();
			// getTest_Steps = group.checkOutStandingItemsCheckBox(driver,
			// LineCategory);
			// test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickProcess_PaymentDetail(driver);
			test_steps.addAll(getTest_Steps);
			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.verifyCardInfoInPaymentDetailTransaction(driver, CardNumber);
			test_steps.addAll(getTest_Steps);

			test_steps
					.add("Last 4 digits in encripted form are displaying wrong when user opens credit line item in Groups folio"
							+ "<br/><a href='https://innroad.atlassian.net/browse/NG-6838' target='_blank'>"
							+ "Verified : NG-6838</a><br/>");

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			
			getTest_Steps.clear();
			test_steps.add("=================Verifying Folio Line Items================");
			app_logs.info("==================Verifying Folio Line Items=================");
			getTest_Steps = group.verifyLineItemsCategory(driver, PaymentMethod, "3");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItemsCategory(driver, PaymentMethod, "3");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.openItemDetail_FolioLineItem(driver, "3");
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethodInPaymentDetailPopup(driver, PaymentMethod);
			test_steps.addAll(getTest_Steps);

			group.payDetailCloseBtn(driver);

			group.loadingImage(driver);
			driver.switchTo().defaultContent();

			group.loadingImage(driver);

			test_steps.add("Existing: OOPs error Page on clicking Folio line item if Payment is made 'Authorization Only' for Group Account"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7074' target='_blank'>"
					+ "Verified : NG-7074</a><br/>");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Pay Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.navigateAccount(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			test_steps.add("=================Verifying Billing info================");
			app_logs.info("==================Verifying Billing info=================");
			getTest_Steps = group.verifyBillingInfo(driver, PaymentMethod2, CardNumber2, ExpiryDate2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Billing", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Billing", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================Account inactive================");
			app_logs.info("==================Account inactive=================");
			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.done(driver);
			test_steps.addAll(getTest_Steps);
			
			comments="Made payment in group folio account with payment method: "+PaymentMethod2+ " ."
					+"Verified billing info while changing the country from " +Country2+ "to "+Country3+ " ."
					+"Verified the amount of $10 in advacne deposit payment pop up"
					+"Verified paying line item in Account folio with payment method as "+PaymentMethod2+ " ."
					+"Verified the country code while changing the country from " +Country2+ "to "+Country3+ " ."
					+"Verified default country in country drop down."
					+"Verified payment through credit card";
					  
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			statusCode.set(3, "1");
			statusCode.set(4, "1");
			statusCode.set(5, "1");
			statusCode.set(6, "1");
			statusCode.set(7, "1");
			statusCode.set(8, "1");
			statusCode.set(9, "1");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to InActive Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("ChangeCountryInGrpFolioBilling", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
