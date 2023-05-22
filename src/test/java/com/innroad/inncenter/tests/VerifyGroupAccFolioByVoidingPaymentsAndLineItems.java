package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
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
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationFolio;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGroupAccFolioByVoidingPaymentsAndLineItems extends TestCore {

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
	public void verifyGroupAccFolioByVoidingPaymentsAndLineItems(String TestCaseID,String url, String clientcode, String username,
			String password, String AccountName, String MargetSegment, String Referral, String AccountFirstName,
			String AccountLastName, String Phonenumber, String Address1, String city, String Country, String State,
			String Postalcode, String LineCategory, String LineAmount, String PaymentType, String PaymentAmount,
			String RoomClassName, String PayAmount,String PaymentType2,String SwipeCard,String checkInDate, String checkOutDate,
			String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard) throws InterruptedException, IOException, ParseException {

		String testName = "VerifyGroupAccFolioByVoidingPaymentsAndLineItems";
		String test_description = "Verify account folio voiding a payment for a line item.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554181' target='_blank'>"
				+ "Click here to open TestRail: C554181</a><br/>"
				+ "Verify account folio voiding a payment for a reservation line item.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554182' target='_blank'>"
				+ "Click here to open TestRail: C554182</a><br/>"
				+ "Verify account folio voiding a line item after completing the payment .<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554183' target='_blank'>"
				+ "Click here to open TestRail: C554183</a><br/>"
				+ "Verify account folio voiding a reservation line item after completing the payment.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554184' target='_blank'>"
				+ "Click here to open TestRail: C554184</a><br/>";

		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);
		//HashMap<String,String> groupNameAndAccount=new HashMap<String,String>();

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("786824");
			caseId.add("786833");
			caseId.add("786845");
			caseId.add("786853");
			caseId.add("786854");
			caseId.add("786856");
			caseId.add("786857");
			statusCode.add("5");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
			
		}

		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		CPReservationPage reservationPage = new CPReservationPage();
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();	
		
		try {

			if (!(Utility.validateInput(checkInDate))&&!(Utility.validateInput(checkOutDate))){
				if (AccountFirstName.split("\\|").length>1) {
					for (int i = 0; i < AccountFirstName.split("\\|").length; i++) {
						checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
						checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
								ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
					}
				}else
				{
					checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),
							ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
				}
			}
			
			if (AccountFirstName.split("\\|").length>1) {
				checkInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
				checkOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			}else {
				checkInDate = checkInDates.get(0);
				checkOutDate = checkOutDates.get(0);
			}



			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
		}catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to CheckINOut Date", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
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
			group.billinginfo(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps); 
			/*groupNameAndAccount=group.searchAccount(driver,test_steps);
			app_logs.info(groupNameAndAccount);*/

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
		// Adding & Verifing Folio Line Item
		try {
			getTest_Steps.clear();
			getTest_Steps = group.AddLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);
			group.commit(driver, test);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);
			
			boolean isIncludeTax = group.isIncludeTaxInLineAmount(driver);
			test_steps.add("Include Tax In Line Items Check : " + isIncludeTax);
			Utility.app_logs.info("Include Tax In Line Items Check : " + isIncludeTax);
			
			String lineItemTax = group.getLineItemTax(driver, 0);
			test_steps.add("Line Item Applied Tax : " + lineItemTax);
			Utility.app_logs.info("Line Item Applied Tax : " + lineItemTax);
			
			PaymentAmount = (Float.parseFloat(lineItemTax)+Float.parseFloat(PaymentAmount))+"";
			
			test_steps.add("Adding Tax Amount into Payment Amount, New Payment Amount is : " + PaymentAmount);
			Utility.app_logs.info("Adding Tax Amount into Payment Amount, New Payment Amount is : " + PaymentAmount);

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

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Adding Ending Balance : " + beforAddingEndingBal);
			Utility.app_logs.info("Before Adding Ending Balance : " + beforAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentType, PaymentAmount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, LineCategory);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAddContinue_Payment(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			
			group.loadingImage(driver);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
			String afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Save Adding Payment Balance : " + afterAddingPayment);

			acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
	
			afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Save Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Save Adding Ending Balance : " + afterAddingEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify that Amount is Paid ( Full paid tick mark symbol ) for line
		// item and item is posted or not? ( arrow symbol )
		try {
			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, envURL);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPostedIcon(driver, LineCategory, true);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// voiding pay line
		try {
			String beforeVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("before Void Payment Balance : " + beforeVoidPayment);
			Utility.app_logs.info("before Void Payment Balance : " + beforeVoidPayment);

			String beforVoidEndingBal = group.getEndingBalance(driver);
			if (beforVoidEndingBal.contains("(")) {
				beforVoidEndingBal = beforVoidEndingBal.substring(1, beforVoidEndingBal.length() - 1);
			}
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "1");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);

			Float acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);
	
			String afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount2 = group.getFolioLinesItemCount(driver);
			test_steps.add("after save Void Folio Lines Count : " + afterVoidLineCount2);
			Utility.app_logs.info("after save Void Folio Lines Count : " + afterVoidLineCount2);
			assertEquals(Integer.parseInt(afterVoidLineCount2), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, false);
			test_steps.addAll(getTest_Steps);

			afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Save Void Payment Balance : " + afterVoidPayment);
			acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);

			afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Save Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Save Void Ending Balance : " + afterVoidEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Adding Ending Balance : " + beforAddingEndingBal);
			Utility.app_logs.info("Before Adding Ending Balance : " + beforAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentType, PaymentAmount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, LineCategory);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAddContinue_Payment(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);
			driver.switchTo().defaultContent();
			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
	
			String afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Save Adding Payment Balance : " + afterAddingPayment);

			acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);

			afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Save Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Save Adding Ending Balance : " + afterAddingEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify that Amount is Paid ( Full paid tick mark symbol ) for line
		// item and item is posted or not? ( arrow symbol )
		try {
			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, envURL);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPostedIcon(driver, LineCategory, true);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// voiding folio line
		try {

			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, LineCategory, false);
			test_steps.addAll(getTest_Steps);

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);
			assertEquals(Float.parseFloat(PaymentAmount), Float.parseFloat(afterVoidPayment),
					"Failed Payment not matched");

			String afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			assertEquals(Float.parseFloat(PaymentAmount), Float.parseFloat(afterVoidEndingBal),
					"Failed Endling Balance not Matched");

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Advance Deposit Balance : " + advanceDepositBal);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount2 = group.getFolioLinesItemCount(driver);
			test_steps.add("after save Void Folio Lines Count : " + afterVoidLineCount2);
			Utility.app_logs.info("after save Void Folio Lines Count : " + afterVoidLineCount2);
			assertEquals(Integer.parseInt(afterVoidLineCount2), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, LineCategory, false);
			test_steps.addAll(getTest_Steps);

			afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);
			assertEquals(Float.parseFloat(PaymentAmount), Float.parseFloat(afterVoidPayment),
					"Failed Payment not matched");

			afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);
			assertEquals(Float.parseFloat(afterVoidEndingBal), Float.parseFloat(PaymentAmount),
					"Failed Ending Balance Not Matched as Payment");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(PaymentAmount),
					"Failed Advance Deposit Not Matched as Payment");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());
			//
		}

		// voiding pay line
		try {
			String beforeVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("before Void Payment Balance : " + beforeVoidPayment);
			Utility.app_logs.info("before Void Payment Balance : " + beforeVoidPayment);

			String beforVoidEndingBal = group.getEndingBalance(driver);
			if (beforVoidEndingBal.contains("(")) {
				beforVoidEndingBal = beforVoidEndingBal.substring(1, beforVoidEndingBal.length() - 1);
			}
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);

			Float acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);

			String afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
	
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount2 = group.getFolioLinesItemCount(driver);
			test_steps.add("after save Void Folio Lines Count : " + afterVoidLineCount2);
			Utility.app_logs.info("after save Void Folio Lines Count : " + afterVoidLineCount2);
			assertEquals(Integer.parseInt(afterVoidLineCount2), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Save Void Payment Balance : " + afterVoidPayment);
			acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);
	
			afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Save Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Save Void Ending Balance : " + afterVoidEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		String resNo = "";
		try {
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.newReservation(driver);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			//getTest_Steps = res.verifyAccountAttached(driver, groupNameAndAccount.get("GroupName");
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			
			

			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, rateplan,"");
			reservationPage.clickOnFindRooms(driver, test_steps);
		//	reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes","");
			//reservationPage.select_Room(driver, test_steps, RoomClassName, "Yes", "");
			ArrayList<String> rooms = reservationPage.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClassName);
			 reservationPage.selectRoomToReserve(driver, RoomClassName, rooms.get(0),test_steps);
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
			reservationPage.clickBookNow(driver, test_steps);
			resNo=reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
			reservationPage.get_ReservationStatus(driver, test_steps);
			reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			reservationPage.clickFolio(driver, test_steps);
			double Total = 0;
			try {
				Total = res.get_FolioBalance(driver);
			} catch (Exception e) {
				Total = res.GetFolioBalance(driver);
			}

			reservationPage.click_FolioDetail_DropDownBox(test_steps,driver);
			reservationPage.clickFolioDetailOptions(driver, test_steps, AccountName);
			//reservationPage.clickFolioDetailOptions(driver, test_steps, groupNameAndAccount.get("GroupName"));
			getTest_Steps.clear();
			getTest_Steps = reservationPage.takePayment(driver, AccountName, PayAmount, "");
			//getTest_Steps = reservationPage.takePayment(driver, groupNameAndAccount.get("GroupName"), PayAmount, "");
			test_steps.addAll(getTest_Steps);
					
			double afterAccAdd = 0;
			try {
				afterAccAdd = res.get_FolioBalanceValue(driver);
			} catch (Exception e) {
				afterAccAdd = res.GetFolioBalance(driver);
			}
			
			reservationPage.verifyFolioLineItemAmoutPaidAsperDescription(driver, AccountName, PayAmount, test_steps);		
			//reservationPage.verifyFolioLineItemAmoutPaidAsperDescription(driver, groupNameAndAccount.get("GroupName"), PayAmount, test_steps);		
			reservationPage.saveReservation(driver, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
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

		// searching and navigate to folio and verify Reservation Line Item
		try {
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			//getTest_Steps = group.Search_Account(driver, groupNameAndAccount.get("GroupName"), groupNameAndAccount.get("AccountNo"), true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			group.verifyLineItems(driver, checkInDate, resNo, PayAmount, test_steps);
	
			String endingBal = group.getEndingBalance(driver);
			if (endingBal.contains("(")) {
				endingBal = endingBal.substring(1, endingBal.length() - 1);
			}
			test_steps.add("After Reservation Added Ending Balance : " + endingBal);
			Utility.app_logs.info("After Reservation Added Ending Balance : " + endingBal);
			assertEquals(Float.parseFloat(endingBal), Float.parseFloat(PayAmount), "Failed Ending Balance not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforAddingEndingBal = group.getEndingBalance(driver);
			if (beforAddingEndingBal.contains("(")) {
				beforAddingEndingBal = beforAddingEndingBal.substring(1, beforAddingEndingBal.length() - 1);
			}
			test_steps.add("Before Adding Ending Balance : " + beforAddingEndingBal);
			Utility.app_logs.info("Before Adding Ending Balance : " + beforAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

	
			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentType, PaymentAmount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, resNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAddContinue_Payment(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			group.verifyLineItems(driver, checkInDate, PaymentType, PaymentAmount, test_steps);
			
			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
	
			String afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			group.verifyLineItems(driver, checkInDate, PaymentType, PaymentAmount, test_steps);

			afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Save Adding Payment Balance : " + afterAddingPayment);

			acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);

			afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Save Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Save Adding Ending Balance : " + afterAddingEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify that Amount is Paid ( Full paid tick mark symbol ) for line
		// item and item is posted or not? ( arrow symbol )
		try {
			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, "Reservation", envURL);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPostedIcon(driver, "Reservation", true);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// voiding pay line
		try {
			String beforeVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("before Void Payment Balance : " + beforeVoidPayment);
			Utility.app_logs.info("before Void Payment Balance : " + beforeVoidPayment);

			String beforVoidEndingBal = group.getEndingBalance(driver);
			if (beforVoidEndingBal.contains("(")) {
				beforVoidEndingBal = beforVoidEndingBal.substring(1, beforVoidEndingBal.length() - 1);
			}
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "1");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);

			Float acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);

			String afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount2 = group.getFolioLinesItemCount(driver);
			test_steps.add("after save Void Folio Lines Count : " + afterVoidLineCount2);
			Utility.app_logs.info("after save Void Folio Lines Count : " + afterVoidLineCount2);
			assertEquals(Integer.parseInt(afterVoidLineCount2), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, PaymentType, false);
			test_steps.addAll(getTest_Steps);

			afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Save Void Payment Balance : " + afterVoidPayment);
			acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);
	
			afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Save Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Save Void Ending Balance : " + afterVoidEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Adding Ending Balance : " + beforAddingEndingBal);
			Utility.app_logs.info("Before Adding Ending Balance : " + beforAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, false);
			test_steps.addAll(getTest_Steps);

	
			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentType, PaymentAmount);
			test_steps.addAll(getTest_Steps);
 
			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, resNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAddContinue_Payment(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);

			String afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Save Adding Payment Balance : " + afterAddingPayment);

			acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
	
			afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Save Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Save Adding Ending Balance : " + afterAddingEndingBal);

			acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
	
			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingEndingBal),
					"Failed Advance Deposit Not Matched as Ending Balance");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Add Pay ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify that Amount is Paid ( Full paid tick mark symbol ) for line
		// item and item is posted or not? ( arrow symbol )
		try {
			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, "Reservation", envURL);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPostedIcon(driver, "Reservation", true);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// voiding REs line
		try {
			String beforeVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("before Void Payment Balance : " + beforeVoidPayment);
			Utility.app_logs.info("before Void Payment Balance : " + beforeVoidPayment);

			String beforVoidEndingBal = group.getEndingBalance(driver);
			if (beforVoidEndingBal.contains("(")) {
				beforVoidEndingBal = beforVoidEndingBal.substring(1, beforVoidEndingBal.length() - 1);
			}
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

				group.loadingImage(driver);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, "Reservation", false);
			test_steps.addAll(getTest_Steps);

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);

			assertEquals(Float.parseFloat(afterVoidPayment), Float.parseFloat(PaymentAmount),
					"Failed Payment Amount not Matched");

			String afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			assertEquals(Float.parseFloat(afterVoidEndingBal), Float.parseFloat(PaymentAmount),
					"Failed Ending Balance not Matched");

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeadvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Before Save Advance Deposit Balance : " + beforeadvanceDepositBal);
			Utility.app_logs.info("Before Save Advance Deposit Balance : " + beforeadvanceDepositBal);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount2 = group.getFolioLinesItemCount(driver);
			test_steps.add("after save Void Folio Lines Count : " + afterVoidLineCount2);
			Utility.app_logs.info("after save Void Folio Lines Count : " + afterVoidLineCount2);
			assertEquals(Integer.parseInt(afterVoidLineCount2), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, "Reservation", false);
			test_steps.addAll(getTest_Steps);

			afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Save Void Payment Balance : " + afterVoidPayment);

			assertEquals(Float.parseFloat(afterVoidPayment), Float.parseFloat(PaymentAmount),
					"Failed Payment Amount not Matched");

			afterVoidEndingBal = group.getEndingBalance(driver);
			if (afterVoidEndingBal.contains("(")) {
				afterVoidEndingBal = afterVoidEndingBal.substring(1, afterVoidEndingBal.length() - 1);
			}
			test_steps.add("After Save Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Save Void Ending Balance : " + afterVoidEndingBal);

			assertEquals(Float.parseFloat(afterVoidEndingBal), Float.parseFloat(PaymentAmount),
					"Failed Ending Balance not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;

				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);

			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(PaymentAmount),
					"Failed Advance Deposit Not Matched as Payment");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());
		}
		
		try {
			
			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentType2, PaymentAmount);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.clickSwipeCardIcon(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");
			getTest_Steps.clear();
			getTest_Steps = group.swipeCard(driver, SwipeCard);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyRbtManualOrForced(driver, "Forced", false);
			test_steps.addAll(getTest_Steps);
			
			Utility.app_logs.info("Auth force is displayed for Authorization type in Group account..");
			test_steps.add("Auth force is displayed for Authorization type in Group account.."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-8799' target='_blank'>"
					+ "Verified : NG-8799 </a><br/>");
			
			group.pickUpCloseClick(driver);
			
			driver.switchTo().defaultContent();
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;

				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Void Pay Folio Line ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// account inActive
		try {
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			Utility.app_logs.info("Tried to Change Account Status");
		}

		try {
		/*	comments="Verified 'New Reservation' button in Groups page. "
					+"User is redirected to create reservation page after cliking on New Reservation button. "
					+"Verfied the payment amounts reflecting in Group Account. "
					+"Verified voiding a payment line item in Group's folio. "
					+"Verified Reservation folio is updated as per Account payment and Advanced deposit. ";
					
					  
			statusCode.set(0, "1");
			statusCode.set(1, "1");
			statusCode.set(2, "1");
			statusCode.set(3, "1");
			statusCode.set(4, "1");
			statusCode.set(5, "1");
			statusCode.set(6, "1");*/
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGrpFolioByVoidPayLineItm", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
