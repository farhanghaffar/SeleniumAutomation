package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyLineItemsByAddingAndVoidingForGroupFolio extends TestCore {

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
		// String testName = "VerifyAddingLineItem_GroupAccFolio";
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
	public void verifyLineItemsByAddingAndVoidingForGroupFolio(String url, String clientcode, String username, String password,
			String accountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String LineCategory, String LineAmount, String PaymentType, String PaymentAmount)
			throws InterruptedException, IOException {

		String testName = "VerifyLineItemsByAddingAndVoidingForGroupFolio";
		String test_description = "Verify adding a line item in the account folio.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554173' target='_blank'>"
				+ "Click here to open TestRail: C554173</a><br/>"
				+ "Verify voiding a payment line item in group's folio.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554174' target='_blank'>"
				+ "Click here to open TestRail: C554174</a><br/>"
				+ "Verify voiding a added line item in groups folio.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554175' target='_blank'>"
				+ "Click here to open TestRail: C554175</a>";
		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		login(testName);
		Navigation navigationPage = new Navigation();
		Groups group = new Groups();

		// Navigate to Groups	
		try {
			navigationPage.Groups(driver);
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
		String accountNo = "0";	
		try {
			accountName = accountName + Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			group.type_GroupName(driver, test, accountName, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			accountNo = Utility.GenerateRandomString15Digit();
			getTest_Steps.clear();
			getTest_Steps = group.enterAccountNo(driver, accountNo);
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
		// Adding Folio Line Item
		try {
			getTest_Steps.clear();
			getTest_Steps = group.addLineItems(driver, LineCategory, LineAmount);
			test_steps.addAll(getTest_Steps);
			group.commit(driver, test);
			getTest_Steps.clear();
			
			

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

		// VerifyFolio line Item
		try {
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

			
			getTest_Steps.clear();
			getTest_Steps = group.modify_LineItem(driver, "Room Service", "10", "Detail Description", "0");
			test_steps.addAll(getTest_Steps);
			
			float f = Float.parseFloat(LineAmount) + Float.parseFloat("10");
			LineAmount = f+"";
			
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);

			test_steps.add("<a href='https://innroad.atlassian.net/browse/NG-6806' target='_blank'>"
					+ "Verified : NG-6806</a><br/>");
			
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

		// Add Pay
		try {
			String beforeAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Adding Payment Balance : " + beforeAddingPayment);
			Utility.app_logs.info("Before Adding Payment Balance : " + beforeAddingPayment);

			String beforAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Adding Ending Balance : " + beforAddingEndingBal);
			Utility.app_logs.info("Before Adding Ending Balance : " + beforAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.AddPayLineItems(driver, PaymentType, PaymentAmount, true);
			test_steps.addAll(getTest_Steps);

			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
			//assertEquals(acctualPayAmount, Float.parseFloat(afterAddingPayment));

			String afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			// afterAddingEndingBal = afterAddingEndingBal.split(" ");
			// afterAddingEndingBal = afterAddingEndingBal.split("(");
			// afterAddingEndingBal = afterAddingEndingBal.split(")");
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
	//		assertEquals(acctualEndingAmount, Float.parseFloat(afterAddingEndingBal));

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
		// VerifyFolio line Item
		try {

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, PaymentType, PaymentAmount, "1");
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("Getting Oops error when user tries to make a payment for line items under group folio."
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7316' target='_blank'>"
					+ "Verified : NG-7316</a><br/>");
			
			test_steps.add("Getting Oops error when user tries to make a payment for Group Folio."
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7342' target='_blank'>"
					+ "Verified : NG-7342</a><br/>");
			
			test_steps.add("Getting Oops error when user tries to make a payment for Group folio line item."
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7414' target='_blank'>"
					+ "Verified : NG-7414</a><br/>");
			

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
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, envURL);
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

			// getTest_Steps.clear();
			// getTest_Steps = group.selectLineItem(driver, "1");
			// test_steps.addAll(getTest_Steps);
			//
			// getTest_Steps.clear();
			// getTest_Steps = group.voidLineItem(driver, "deleting");
			// test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "1");
			test_steps.addAll(getTest_Steps);

			Wait.wait5Second();
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

			String afterVoidPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Void Payment Balance : " + afterVoidPayment);
			Utility.app_logs.info("After Void Payment Balance : " + afterVoidPayment);
			Float acctualAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforeVoidPayment);
			Utility.app_logs.info(acctualAmount);
		//	assertEquals(Float.parseFloat(afterVoidPayment), acctualAmount, "Failed Payment Amount not Matched");

			String afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			Float acctualEndingAmount = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforVoidEndingBal);
			Utility.app_logs.info(acctualEndingAmount);
		//	assertEquals(Float.parseFloat(afterVoidEndingBal), acctualEndingAmount, "Failed Ending Balance not Matched");
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

		// voiding folio line
		try {

			String beforVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Void Ending Balance : " + beforVoidEndingBal);
			Utility.app_logs.info("Before Void Ending Balance : " + beforVoidEndingBal);

			// getTest_Steps.clear();
			// getTest_Steps = group.selectLineItem(driver, "0");
			// test_steps.addAll(getTest_Steps);
			//
			// getTest_Steps.clear();
			// getTest_Steps = group.voidLineItem(driver, "deleting");
			// test_steps.addAll(getTest_Steps);

			String beforeVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("before Void Folio Lines Count : " + beforeVoidLineCount);
			Utility.app_logs.info("before Void Folio Lines Count : " + beforeVoidLineCount);

			getTest_Steps.clear();
			getTest_Steps = group.rollBack_Void_LineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			String afterVoidLineCount = group.getFolioLinesItemCount(driver);
			test_steps.add("after Void Folio Lines Count : " + afterVoidLineCount);
			Utility.app_logs.info("after Void Folio Lines Count : " + afterVoidLineCount);
			assertEquals(Integer.parseInt(afterVoidLineCount), Integer.parseInt(beforeVoidLineCount) - 1,
					"Failed To Match Folio Lines Count");

			getTest_Steps.clear();
			getTest_Steps = group.lineItemExist(driver, LineCategory, false);
			test_steps.addAll(getTest_Steps);

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

			String afterVoidEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Void Ending Balance : " + afterVoidEndingBal);
			Utility.app_logs.info("After Void Ending Balance : " + afterVoidEndingBal);

			// Float acctualEndingAmount = Float.parseFloat(beforVoidEndingBal)
			// - Float.parseFloat(afterVoidEndingBal);
			// Utility.app_logs.info(acctualEndingAmount);
			assertEquals(Float.parseFloat("0"), Float.parseFloat(afterVoidEndingBal),"Failed Endling Balance not Zero");
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
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyLineItemByAddVoidGrpFolio", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
