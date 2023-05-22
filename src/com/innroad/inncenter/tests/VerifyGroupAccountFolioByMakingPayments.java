package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

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
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGroupAccountFolioByMakingPayments extends TestCore {

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
		if (!Utility.isExecutable(testName, excel))
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
			//login_Group(driver);
			login_CP(driver);
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
	public void verifyGroupAccountFolioByMakingPayments(String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String LineCategory, String LineCategory2, String LineAmount, String PaymentType, String PaymentType2,
			String PaymentAmount, String RoomClassName, String PayAmount, String checkInDate, String checkOutDate,
			String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard) throws ParseException, Exception {

		String testName = "VerifyGroupAccountFolioByMakingPayments";
		String test_description = "Verify Advance deposited check amount to the reservation transfers, through Auto Apply.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554176' target='_blank'>"
				+ "Click here to open TestRail: C554176</a><br/>"
				+ "Verify adding a roomcharge line item in the acc folio.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554177' target='_blank'>"
				+ "Click here to open TestRail: C554177</a><br/>"
				+ "Verify account folio making a payment for a reservation payment line item.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554178' target='_blank'>"
				+ "Click here to open TestRail: C554178</a><br/>"
				+ "Verify account folio making a payment for a reservation payment line item through advance deposit.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554179' target='_blank'>"
				+ "Click here to open TestRail: C554179</a><br/>"
				+ "Verify account folio making a payment for a line item through advance deposit.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554180' target='_blank'>"
				+ "Click here to open TestRail: C554180</a>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();	
		
		
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
		login(testName);

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		CPReservationPage reservationPage = new CPReservationPage();
		ReservationFolio folio = new ReservationFolio();
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
		// Adding first line & Verifing Folio Line Item
		try {

			boolean isIncludeTax = group.isIncludeTaxInLineAmount(driver);
			test_steps.add("Include Tax In Line Items Check : " + isIncludeTax);
			Utility.app_logs.info("Include Tax In Line Items Check : " + isIncludeTax);
			
			String beforeAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("Before Adding line Ending Balance : " + beforeAddingEndingBal);
			Utility.app_logs.info("Before Adding line Ending Balance : " + beforeAddingEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.AddLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);
			group.commit(driver, test);

			String lineAmount1=LineAmount;
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "0");
			test_steps.addAll(getTest_Steps);

			//Added BY Gangotri Sikheria
			 getTest_Steps.clear();
			 getTest_Steps = group.AddLineItems(driver, LineCategory2,LineAmount, "1");
			 test_steps.addAll(getTest_Steps);
			 group.commit(driver, test);
			
			 getTest_Steps.clear();
			 getTest_Steps = group.verifyLineItems(driver, LineCategory2,LineAmount, "1");
			 test_steps.addAll(getTest_Steps);
			 
			 String lineAmount2=LineAmount;
			 DecimalFormat df = new DecimalFormat("0.00");
			 df.setMaximumFractionDigits(2);
			 
				String lineItemTax = group.getLineItemTax(driver, 0);
				test_steps.add("Line Item Applied Tax : " + lineItemTax);
				Utility.app_logs.info("Line Item Applied Tax : " + lineItemTax);
				
				String lineItemTax1 = group.getLineItemTax(driver, 1);
				test_steps.add("Line Item Applied Tax : " + lineItemTax);
				Utility.app_logs.info("Line Item Applied Tax : " + lineItemTax);
				
			 String beforeSaveEndingBal= df.format((Double.parseDouble(lineAmount1)+Double.parseDouble(lineItemTax))+
					 (Double.parseDouble(lineAmount2)+Double.parseDouble(lineItemTax1)));
			 app_logs.info(beforeSaveEndingBal);
			test_steps.add("Before Save Ending Balance : " + beforeSaveEndingBal);
			Utility.app_logs.info("Before Save Ending Balance : " + beforeSaveEndingBal);
			assertTrue(Float.parseFloat(beforeSaveEndingBal) >= Float.parseFloat(beforeAddingEndingBal),
					"Failed Ending Balance not added after Adding Line Item");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterSaveEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Save Ending Balance : " + afterSaveEndingBal);
			Utility.app_logs.info("After Save Ending Balance : " + afterSaveEndingBal);
	
			String expectedValue=df.format(Double.parseDouble(afterSaveEndingBal));
		    String actialValue=df.format(Double.parseDouble(beforeSaveEndingBal));
		    app_logs.info(expectedValue);
		    app_logs.info(actialValue);
			assertEquals(expectedValue, actialValue,
					"Failed Ending Balance not Matched After Save Button Clicked");

				PaymentAmount = (Float.parseFloat(lineItemTax)+Float.parseFloat(lineItemTax1)+Float.parseFloat(PaymentAmount))+"";
			
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

		// Add Pay 1
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
			getTest_Steps = group.AddPayLineItems(driver, PaymentType, PaymentAmount, false);
			test_steps.addAll(getTest_Steps);

			String afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Adding Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Adding Payment Balance : " + afterAddingPayment);

			Float acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);

			String afterAddingEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Adding Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Adding Ending Balance : " + afterAddingEndingBal);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			Float acctualEndingBal = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingPayment),
					"Failed Advance Deposit Not Matched as Payment");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			group.verifyLineItems(driver, checkInDate,PaymentType, PaymentAmount, test_steps);
			
			afterAddingPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Payment Balance : " + afterAddingPayment);
			Utility.app_logs.info("After Save Payment Balance : " + afterAddingPayment);

			acctualPayAmount = Float.parseFloat(PaymentAmount) + Float.parseFloat(beforeAddingPayment);
			Utility.app_logs.info(acctualPayAmount);
			assertEquals(acctualPayAmount, Float.parseFloat(afterAddingPayment),
					"Failed to Match Payment Balance After Save");

			afterAddingEndingBal = group.getEndingBalance(driver);
			if (afterAddingEndingBal.contains("(")) {
				afterAddingEndingBal = afterAddingEndingBal.substring(1, afterAddingEndingBal.length() - 1);
			}
			test_steps.add("After Save Ending Balance : " + afterAddingEndingBal);
			Utility.app_logs.info("After Save Ending Balance : " + afterAddingEndingBal);

			acctualEndingBal = Float.parseFloat(PaymentAmount) - Float.parseFloat(beforAddingEndingBal);
			Utility.app_logs.info(acctualEndingBal);
			assertEquals(acctualEndingBal, Float.parseFloat(afterAddingEndingBal),
					"Failed to Match Payment Balance After Save");

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			advanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + advanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + advanceDepositBal);
			assertEquals(Float.parseFloat(advanceDepositBal), Float.parseFloat(afterAddingPayment),
					"Failed Advance Deposit Not Matched as Payment After Save");

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

		// Process the Payment For Line Item
		try {

			String beforeProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Process Payment Balance : " + beforeProcessPayment);
			Utility.app_logs.info("Before Process Payment Balance : " + beforeProcessPayment);

			String beforProcessEndingBal = group.getEndingBalance(driver);
			if (beforProcessEndingBal.contains("(")) {
				beforProcessEndingBal = beforProcessEndingBal.substring(1, beforProcessEndingBal.length() - 1);
			}
			test_steps.add("Before Process Ending Balance : " + beforProcessEndingBal);
			Utility.app_logs.info("Before Process Ending Balance : " + beforProcessEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Before Process Advance Deposit Balance : " + beforeAdvanceDepositBal);
			Utility.app_logs.info("Before Process Advance Deposit Balance : " + beforeAdvanceDepositBal);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdvanceDepositLink(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.checkOutStandingItemsCheckBox(driver, LineCategory);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("Group Folio > Applying Payment > Zoom In : Code Fix"
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-6159' target='_blank'>"
					+ "Verified : NG-6159 </a><br/>");

			getTest_Steps.clear();
			getTest_Steps = group.clickAdd_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();

			String afterProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Process Payment Balance : " + afterProcessPayment);
			Utility.app_logs.info("After Process Payment Balance : " + afterProcessPayment);

			String afterProcessEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Process Ending Balance : " + afterProcessEndingBal);
			Utility.app_logs.info("After Process Ending Balance : " + afterProcessEndingBal);
			if (afterProcessEndingBal.contains("(")) {
				afterProcessEndingBal = afterProcessEndingBal.substring(1, afterProcessEndingBal.length() - 1);
			}

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String afterProcessAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Process Advance Deposit Balance : " + afterProcessAdvanceDepositBal);
			Utility.app_logs.info("After Process Advance Deposit Balance : " + afterProcessAdvanceDepositBal);

			Float c = Float.parseFloat(beforeAdvanceDepositBal) - Float.parseFloat(afterProcessAdvanceDepositBal);
			Float acctualPayAmount = Float.parseFloat(beforeAdvanceDepositBal) - c;
			Utility.app_logs.info(acctualPayAmount);
			assertEquals(Float.parseFloat(afterProcessAdvanceDepositBal), acctualPayAmount,
					"Failed Advance Deposit not Matched");

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyFullyPaidIcon(driver, LineCategory, envURL);
			test_steps.addAll(getTest_Steps);

			afterProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Process Payment Balance : " + afterProcessPayment);
			Utility.app_logs.info("After Save Process Payment Balance : " + afterProcessPayment);

			 assertEquals(Float.parseFloat(beforeProcessPayment),
			 Float.parseFloat(afterProcessPayment),"Failed to Match Payment Balance");

			afterProcessEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Save Process Ending Balance : " + afterProcessEndingBal);
			Utility.app_logs.info("After Save Process Ending Balance : " + afterProcessEndingBal);
			if (afterProcessEndingBal.contains("(")) {
				afterProcessEndingBal = afterProcessEndingBal.substring(1, afterProcessEndingBal.length() - 1);
			}

			 assertEquals(Float.parseFloat(beforProcessEndingBal),Float.parseFloat(afterProcessEndingBal),"Failed to Match Payment Balance");

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String afterSaveAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + afterSaveAdvanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + afterSaveAdvanceDepositBal);

			Float c1 = Float.parseFloat(beforeAdvanceDepositBal) - Float.parseFloat(afterSaveAdvanceDepositBal);
			Float acctualPayAmount1 = Float.parseFloat(beforeAdvanceDepositBal) - c1;
			Utility.app_logs.info(acctualPayAmount1);
			assertEquals(Float.parseFloat(afterSaveAdvanceDepositBal), acctualPayAmount1,
					"Failed Advance Deposit not Matched");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Payment ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Payment ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	

		// save clicked
		try {
			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Save Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Save Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			getTest_Steps.clear();
			getTest_Steps = group.newReservation(driver);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			
			
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			reservationPage.select_CheckInDate(driver, test_steps, checkInDate);
			reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);
			reservationPage.enter_Adults(driver, test_steps, adults);
			reservationPage.enter_Children(driver, test_steps, children);
			reservationPage.select_Rateplan(driver, test_steps, rateplan,"");
			reservationPage.clickOnFindRooms(driver, test_steps);
			//reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes","");
			reservationPage.select_Room(driver, test_steps, RoomClassName, "Yes", "");
			reservationPage.clickNext(driver, test_steps);
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));
			reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);
			reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
			reservationPage.clickBookNow(driver, test_steps);
			reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
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
				app_logs.info(Total);
			} catch (Exception e) {
				Total = res.GetFolioBalance(driver);
			}

			
			getTest_Steps.clear();
			getTest_Steps = folio.TravelAccount(driver, PaymentType, PayAmount);
					test_steps.addAll(getTest_Steps);

			double afterAccAdd = 0;
			try {
				afterAccAdd = res.get_FolioBalance(driver);
			} catch (Exception e) {
				afterAccAdd = res.GetFolioBalance(driver);
			}
			getTest_Steps.clear();
			getTest_Steps = folio.verifyFolioItemsAmount(driver, Total, afterAccAdd, PayAmount, AccountName);
			test_steps.addAll(getTest_Steps);

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

		// searching and navigate to folio
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

		try{
			String beforeProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("Before Process Payment Balance : " + beforeProcessPayment);
			Utility.app_logs.info("Before Process Payment Balance : " + beforeProcessPayment);

			String beforProcessEndingBal = group.getEndingBalance(driver);
			if (beforProcessEndingBal.contains("(")) {
				beforProcessEndingBal = beforProcessEndingBal.substring(1, beforProcessEndingBal.length() - 1);
			}
			test_steps.add("Before Process Ending Balance : " + beforProcessEndingBal);
			Utility.app_logs.info("Before Process Ending Balance : " + beforProcessEndingBal);

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String beforeAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("Before Process Advance Deposit Balance : " + beforeAdvanceDepositBal);
			Utility.app_logs.info("Before Process Advance Deposit Balance : " + beforeAdvanceDepositBal);

			getTest_Steps.clear();
			getTest_Steps = group.clickAdvanceDepositLink(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.clickAutoApply(driver);
			test_steps.addAll(getTest_Steps);
			ArrayList<String> autoApplyItem= new ArrayList<String>();
			autoApplyItem=group.getAutoApplyItems(driver);
			app_logs.info(autoApplyItem);
			
			getTest_Steps.clear();
			getTest_Steps = group.clickAdd_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);
			
			
			getTest_Steps.clear();
			getTest_Steps = group.clickContinue_AdvanceDeposit(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();

			String afterProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Process Payment Balance : " + afterProcessPayment);
			Utility.app_logs.info("After Process Payment Balance : " + afterProcessPayment);

	
			String afterProcessEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Process Ending Balance : " + afterProcessEndingBal);
			Utility.app_logs.info("After Process Ending Balance : " + afterProcessEndingBal);
			if (afterProcessEndingBal.contains("(")) {
				afterProcessEndingBal = afterProcessEndingBal.substring(1, afterProcessEndingBal.length() - 1);
			}

	
			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String afterProcessAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Process Advance Deposit Balance : " + afterProcessAdvanceDepositBal);
			Utility.app_logs.info("After Process Advance Deposit Balance : " + afterProcessAdvanceDepositBal);

			Float c = Float.parseFloat(beforeAdvanceDepositBal) - Float.parseFloat(afterProcessAdvanceDepositBal);
			Float acctualPayAmount = Float.parseFloat(beforeAdvanceDepositBal) - c;
			Utility.app_logs.info(acctualPayAmount);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		    group.verifyFullyPaidIcon(driver, autoApplyItem.get(0), test_steps);
			
			afterProcessPayment = group.getPaymentsAmount(driver);
			test_steps.add("After Save Process Payment Balance : " + afterProcessPayment);
			Utility.app_logs.info("After Save Process Payment Balance : " + afterProcessPayment);

			 assertEquals(Float.parseFloat(beforeProcessPayment),
			 Float.parseFloat(afterProcessPayment),"Failed to Match Payment Balance");

			afterProcessEndingBal = group.getEndingBalance(driver);
			test_steps.add("After Save Process Ending Balance : " + afterProcessEndingBal);
			Utility.app_logs.info("After Save Process Ending Balance : " + afterProcessEndingBal);
			if (afterProcessEndingBal.contains("(")) {
				afterProcessEndingBal = afterProcessEndingBal.substring(1, afterProcessEndingBal.length() - 1);
			}

			 assertEquals(Float.parseFloat(beforProcessEndingBal),Float.parseFloat(afterProcessEndingBal),"Failed to Match Payment Balance");

			getTest_Steps.clear();
			getTest_Steps = group.checkAdvanceDepositVisibility(driver, true);
			test_steps.addAll(getTest_Steps);

			String afterSaveAdvanceDepositBal = group.getAdvanceDepositBalance(driver);
			test_steps.add("After Save Advance Deposit Balance : " + afterSaveAdvanceDepositBal);
			Utility.app_logs.info("After Save Advance Deposit Balance : " + afterSaveAdvanceDepositBal);

			Float c1 = Float.parseFloat(beforeAdvanceDepositBal) - Float.parseFloat(afterSaveAdvanceDepositBal);
			Float acctualPayAmount1 = Float.parseFloat(beforeAdvanceDepositBal) - c1;
			Utility.app_logs.info(acctualPayAmount1);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Payment ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Payment ", testName, "Group", driver);
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
		return Utility.getData("VerifyGrpAccFolioByMakingPay", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
