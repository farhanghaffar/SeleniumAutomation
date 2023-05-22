package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAdvanceDepositAmount extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();

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
	public void verifyAdvanceDepositAmount(String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String PaymentMethod, String AuthorizationType, String PaymentAmount, String CardName, String CardNo,
			String ExpiryDate, String CVVCode, String LineCategory, String LineAmount)
			throws InterruptedException, IOException {

		String testName = "VerifyAdvanceDepositAmount";
		String test_description = "Verify amount in the advance deposit payment pop-up.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554194' target='_blank'>"
				+ "Click here to open TestRail: C554194</a>";

		String test_catagory = "Groups";
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
			group.loadingImage(driver);
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
			
			getTest_Steps.clear();
			group.done(driver);
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
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);
			
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

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			getTest_Steps.clear();
			getTest_Steps = group.clickPay(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);
			
			//Wait.explicit_wait_xpath("//*[@id='dialog-body0']", driver);
			driver.switchTo().frame("dialog-body0");

			getTest_Steps.clear();
			getTest_Steps = group.selectPaymentMethodAndAmount(driver, PaymentMethod, PaymentAmount);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.selectAuthorizationType(driver, AuthorizationType);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickButtonCardInfo_PaymentDetailPopup(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.fillPaymentDetail(driver, CardName, CardNo, ExpiryDate, CVVCode);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.clickProcess_PaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.applyAdvanceDeposit_YesClick(driver, PaymentAmount);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			test_steps.add("Transaction City Ledger Selected.....");
			Utility.app_logs.info("Transaction City Ledger Selected.....");

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, PaymentMethod, PaymentAmount, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Account (Advanced Deposit)",
					"-" + PaymentAmount, 2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdvDepLedgerLink_TransactionPaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Account", "-" + PaymentAmount, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("PE | CC Refund is not Processing  in Group Folio"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-6737' target='_blank'>"
					+ "Verified : NG-6737 </a><br/>");

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

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

		// Verifing Folio Line Item
		try {

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod, PaymentAmount, "0");
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			String endingBal = group.getEndingBalance(driver);
			if (endingBal.contains("(")) {
				endingBal = endingBal.substring(1, endingBal.length() - 1);
			}
			if (endingBal.contains(",")) {
				endingBal = endingBal.replace(",", "");
			}
			test_steps.add("After Adding Payment Ending Balance : " + endingBal);
			Utility.app_logs.info("After Adding Payment Ending Balance : " + endingBal);

			assertEquals(Float.parseFloat(endingBal), Float.parseFloat(PaymentAmount),
					"Failed Ending Balance not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ending Balance", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ending Balance", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(PaymentAmount),
					"Failed Advance Deposit Not Matched as Payment Amount");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Advance Deposit", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				test_steps.add(e.toString());
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.openItemDetail_FolioLineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);
			driver.switchTo().frame("dialog-body0");
			group.loadingImage(driver);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyUpdatedUser(driver, Utility.login_Group.get(2));
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("'Updated By' field for payment line item in group account folio is showing AuditTrail : SP Fix"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-6190' target='_blank'>"
					+ "Verified : NG-6190 </a><br/>");
			
			getTest_Steps.clear();
			getTest_Steps = group.switchToTransactionTab(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			test_steps.add("Transaction City Ledger Selected.....");
			Utility.app_logs.info("Transaction City Ledger Selected.....");

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, PaymentMethod, PaymentAmount, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Account (Advanced Deposit)",
					"-" + PaymentAmount, 2);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdvDepLedgerLink_TransactionPaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Acc (Group)-Transfer", "-" + PaymentAmount,
					1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.pickUpCloseClick(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String lineItemTax = "0";
		try {
			getTest_Steps.clear();
			getTest_Steps = group.AddLineItems(driver, LineCategory, LineAmount, "1");
			test_steps.addAll(getTest_Steps);
			group.commit(driver, test);
			
			boolean isIncludeTax = group.isIncludeTaxInLineAmount(driver);
			test_steps.add("Include Tax In Line Items Check : " + isIncludeTax);
			Utility.app_logs.info("Include Tax In Line Items Check : " + isIncludeTax);
			
			lineItemTax = group.getLineItemTax(driver, 1);
			test_steps.add("Line Item Applied Tax : " + lineItemTax);
			Utility.app_logs.info("Line Item Applied Tax : " + lineItemTax);
			
//			PaymentAmount = (Float.parseFloat(lineItemTax)+Float.parseFloat(PaymentAmount))+"";
//			
//			test_steps.add("Adding Tax Amount into Payment Amount, New Payment Amount is : " + PaymentAmount);
//			Utility.app_logs.info("Adding Tax Amount into Payment Amount, New Payment Amount is : " + PaymentAmount);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod, PaymentAmount, "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "1");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentMethod, PaymentAmount, "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "1");
			test_steps.addAll(getTest_Steps);

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

		String calculateEndingBal = "0";
		try {
			String endingBal = group.getEndingBalance(driver);
			if (endingBal.contains("(")) {
				endingBal = endingBal.substring(1, endingBal.length() - 1);
			}
			if (endingBal.contains(",")) {
				endingBal = endingBal.replace(",", "");
			}
			test_steps.add("After Adding Payment Ending Balance : " + endingBal);
			Utility.app_logs.info("After Adding Payment Ending Balance : " + endingBal);

			String taxesAndServices = group.getTaxesAndServiceCharges(driver);
			if (taxesAndServices.contains("(")) {
				taxesAndServices = taxesAndServices.substring(1, taxesAndServices.length() - 1);
			}
			test_steps.add("After Adding Payment Taxes and Service Charges : " + taxesAndServices);
			Utility.app_logs.info("After Adding Payment Taxes and Service Charges : " + taxesAndServices);
			String cal = (Float.parseFloat(PaymentAmount) - Float.parseFloat(LineAmount)) + "";
			if (cal.contains("(")) {
				cal = cal.substring(1, cal.length() - 1);
			}

			Utility.app_logs.info("Line Amont - Taxes and Service Charges : " + cal);

			calculateEndingBal = (Float.parseFloat(cal) - Float.parseFloat(taxesAndServices)) + "";
			if (calculateEndingBal.contains("(")) {
				calculateEndingBal = calculateEndingBal.substring(1, calculateEndingBal.length() - 1);
			}
			if (calculateEndingBal.contains(",")) {
				calculateEndingBal = calculateEndingBal.replace(",", "");
			}
			assertEquals(Float.parseFloat(endingBal), Float.parseFloat(calculateEndingBal), "Failed Ending Balance not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ending Balance", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Ending Balance", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(PaymentAmount),
					"Failed Advance Deposit Not Matched as Payment Amount");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Advance Deposit", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				test_steps.add(e.toString());
			} else {
				Assert.assertTrue(false);
			}
		}

		String addPayedAmount = "0";
		
		try {
			getTest_Steps.clear();
			getTest_Steps = group.clickAdvanceDepositLink(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().frame("dialog-body0");

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethodInPaymentDetailPopup(driver, "Account (Advanced Deposit)");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAutoApply(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdd_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			//addPayedAmount = group.getAddPayingAmount(driver);
			
			getTest_Steps.clear();
			getTest_Steps = group.advanceDepositBalancePopup(driver, calculateEndingBal + "");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Advance Deposit", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				test_steps.add(e.toString());
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify that Amount is Paid ( Full paid tick mark symbol ) for line
		
		try {
			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, envURL);
			test_steps.addAll(getTest_Steps);
			
			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(calculateEndingBal),
					"Failed Advance Deposit Not Matched as Payment Amount");

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

		try {
			getTest_Steps.clear();
			getTest_Steps = group.openItemDetail_FolioLineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);
			driver.switchTo().frame("dialog-body0");
			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.switchToTransactionTab(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			test_steps.add("Transaction City Ledger Selected.....");
			Utility.app_logs.info("Transaction City Ledger Selected.....");
			
			
			
			//Utility.app_logs.info("Added Paying Amount is : " + addPayedAmount);
			//if(addPayedAmount.equals("0")){
				DecimalFormat df2 = new DecimalFormat("#.##");
//				 df2.setRoundingMode(RoundingMode.UP);
				addPayedAmount = df2.format((Float.parseFloat(PaymentAmount) - Float.parseFloat(calculateEndingBal)))+"";
			//}
			Utility.app_logs.info("Added Paying Amount is : " + addPayedAmount);
			
//			diff = "103.3";
			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Account (Advanced Deposit)", addPayedAmount, 3);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdvDepLedgerLink_TransactionPaymentDetail(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyTransactionPaymentDetail(driver, "Acc (Group)-Transfer", addPayedAmount,
					2);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				test_steps.add(e.toString());
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.pickUpCloseClick(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().defaultContent();

			group.loadingImage(driver);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Folio Line", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = group.clickAdvanceDepositLink(driver);
			test_steps.addAll(getTest_Steps);

			group.loadingImage(driver);

			driver.switchTo().frame("dialog-body0");

			group.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentMethodInPaymentDetailPopup(driver, "Account (Advanced Deposit)");
			test_steps.addAll(getTest_Steps);
			

			getTest_Steps.clear();
			getTest_Steps = group.verifyPaymentAmount(driver, calculateEndingBal);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.paymentDetailCancelClicked(driver);
			test_steps.addAll(getTest_Steps);
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify Advance Deposit", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				test_steps.add(e.toString());
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

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to change status InActive ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyAdvanceDepositAmount", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
	 driver.quit();
	}
}
