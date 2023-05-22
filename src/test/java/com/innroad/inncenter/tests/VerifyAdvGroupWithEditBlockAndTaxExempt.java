package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_AdvanceGroups;

public class VerifyAdvGroupWithEditBlockAndTaxExempt extends TestCore {
	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	CPReservationPage reservationPage = new CPReservationPage();
	
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	HashMap<String, String> roomChargesAre= new HashMap<String, String>();
	ArrayList<String> roomCharges = new ArrayList<>();
	ArrayList<String> rooms= new ArrayList<String>();
	String reservationNo=null,depositPolicyApplied=null, checkinPolicyApplied=null, cancelPolicyApplied=null,depositAmount=null,cancelAmount=null,fourDigitCardNo=null,paymentTypeIs=null,expiryDate=null,
			checkInAmount=null,paymentAmount=null,checkInCardFormat=null, cancellationFeeAmount=null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		Utility.getData("VerifyGroupAccountWithTaxExempt", BEExcel);
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);

	}
	
	private void verificationDepositWithPolicy(String paymentType, String policyName,String amount, String historyPaymentType) throws InterruptedException, ParseException {
		reservationPage.verifyFolioLineItemAmoutPaid(driver, paymentType, amount, test_steps);
		reservationPage.verify_FolioPayment(driver, test_steps, amount);
		reservationPage.clickOnDetails(driver);
		reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyName, "");
		reservationPage.verifyTripSummaryPaidAmount(driver, test_steps, amount);
		reservationPage.click_History(driver, test_steps);
		reservationPage.verifyDepositAtHistoryTab(driver, test_steps, amount, historyPaymentType);
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
				RetryFailedTestCases.count = Utility.reset_count;
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
	public void verifyAdvGroupWithEditBlockAndTaxExempt(String TestCaseID,String policyType,String policyName,
			String policyAttr1,String policyAttrValue,String policyAttr2,String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String ChargeRoutingAllItem, String ChargeRoutingRoomChargeOnly, String BlockName, String RoomPerNight,
			String TaxName, String DisplayName, String Description, String value, String Category, String LedgerAccount,
			String TaxExmptId, String LineCategory, String LineAmount, String RoomClassName)
			throws Exception {

		String testName = "VerifyAdvGroupWithEditBlockAndTaxExempt";
		String test_description = "Verify Adv Group With Edit Block And TaxExempt.<br>";
		// + "<a href='https://innroad.testrail.io/index.php?/cases/view/554181'
		// target='_blank'>"
		// + "Click here to open TestRail: C554181</a><br/>";

		String test_catagory = "Groups";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		if(!Utility.validateString(TestCaseID)) {
			caseId.add("825397");
			caseId.add("825398");
			caseId.add("825392");
			caseId.add("825393");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		
		login(testName);

		Navigation nav = new Navigation();
		Groups group = new Groups();
		AdvGroups advgrp = new AdvGroups();
		Reservation res = new Reservation();
		Tax tax = new Tax();
		TaxesAndFee taxFees=new TaxesAndFee();
		ArrayList<String> abbreviations= new ArrayList<String>();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		Policies policies = new Policies(); 
		String[] roomClassArray = null, policyTypeIs=null, policyNameIs=null,policyAttr1Is=null,policyAttrValueIs=null,policyAttr2Is=null;
		ArrayList<String> policyNames= new ArrayList<String>();
		ArrayList<String> policyTypes= new ArrayList<String>();
		// Create Tax-1
		try {
			test_steps.add("================Create Tax-1================");
			app_logs.info("=================Create Tax-1=================");
			nav.Setup(driver);
			app_logs.info("Navigate To Setup");
			test_steps.add("Navigate To Setup");
			getTest_Steps.clear();
			tax.clickOnTaxButton(driver);
			test_steps.add("Navigate to taxes page");
			taxFees.deleteAllTaxesAndFee(driver, test_steps);
			tax.clickOnCreateTaxButton(driver);
			tax.createNewTax(driver, TaxName, DisplayName, Description, value, Category, LedgerAccount, true, false, false, false, "", "", "");
			app_logs.info("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);
			test_steps.add("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Tax", testName, "CreateTax", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create  Tax ", testName, "CreateTax", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		//Get Abbreviations
		try
		{
			test_steps.add("=====Getting Abbreviations=====");
				nav.roomClass(driver);
				abbreviations=	rc.getAbbrivation(driver, "|", RoomClassName, test_steps);								
				app_logs.info(abbreviations);
				nav.inventoryV2(driver);
			
		
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Abbreviations", "Room Class", "Room Class", testName, test_description,
					test_catagory, test_steps);

		}

		try {

			test_steps.add("Navigated to Inventory");
			policyTypeIs=policyType.split("\\|");
			policyNameIs=policyName.split("\\|");
			policyAttr1Is=policyAttr1.split("\\|");
			policyAttrValueIs=policyAttrValue.split("\\|");
			policyAttr2Is=policyAttr2.split("\\|");	
			
				test_steps.add("========== Creating new policy for account ==========");
					nav.policies(driver, test_steps);
					Wait.waitUntilPageLoadNotCompleted(driver, 5);					
					for(int i=0;i<policyTypeIs.length;i++){
						String policyNameis=policyNameIs[i]+Utility.generateRandomNumber();
						app_logs.info(policyNameis);
						policyNames.add(policyNameis);
						policyTypes.add(policyTypeIs[i]);
						policies.createPolicies(driver, test_steps, "|", "|", policyTypeIs[i], policyNameis, policyNameis,policyNameis , "", 
								policyAttr1Is[i], policyAttrValueIs[i], policyAttr2Is[i], "1", "within check-in date", "No", "");	
					}
				
				app_logs.info(policyNames);
				app_logs.info(policyTypes);
				
				roomClassArray = RoomClassName.split("\\|");
				app_logs.info(roomClassArray[0]);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Policy", "policy", "policy", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Policy", "policy", "policy", testName,
					test_description, test_catagory, test_steps);

		}	
		

		// Navigate to Groups
		try {
			test_steps.add("================Navigate to Groups================");
			app_logs.info("=================Navigate to Groups=================");
			nav.Reservation_Backward_3(driver);
			nav.Groups(driver);
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
			test_steps.add("================Create New Group================");
			app_logs.info("=================Create New Group=================");
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
			getTest_Steps = group.checkTaxExmpt(driver, true, TaxExmptId);
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
		
		// Navigate to Folio Tab Associate Policy with Group Account
		try {
			test_steps.add("==========Associate Policy ==========");
			for(int i=0;i< policyNames.size();i++) {
			group.associatePolicyWithGroup(driver, test, policyTypes.get(i), policyNames.get(i),test_steps);}
			
			for(int i=0;i< policyNames.size();i++) {
				group.verifyassociatePolicy(driver, test, policyTypes.get(i), policyNames.get(i), test_steps);
			}					
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Associate Policy with Group Account", "group", "group", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Associate Policy with Group Account", "group", "group", testName,
					test_description, test_catagory, test_steps);

		}

		// Navigate to Folio Tab
/*		try {
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
			 
			test_steps.add("================Adding Folio Line Item================");
			app_logs.info("=================Adding Folio Line Item=================");
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
			test_steps.add("================VerifyFolio line Item================");
			app_logs.info("=================VerifyFolio line Item=================");
			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, LineCategory, LineAmount, "0");
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

		try {
			String taxesAndServices = group.getTaxesAndServiceCharges(driver);
			if (taxesAndServices.contains("(")) {
				taxesAndServices = taxesAndServices.substring(1, taxesAndServices.length() - 1);
			}
			test_steps.add("After Adding Payment Taxes and Service Charges : " + taxesAndServices);
			Utility.app_logs.info("After Adding Payment Taxes and Service Charges : " + taxesAndServices);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			Wait.loadingImage(driver);

			getTest_Steps.clear();
			getTest_Steps = group.checkTaxExmpt(driver, true, TaxExmptId);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			String afterTaxesAndServices = group.getTaxesAndServiceCharges(driver);
			if (afterTaxesAndServices.contains("(")) {
				afterTaxesAndServices = afterTaxesAndServices.substring(1, afterTaxesAndServices.length() - 1);
			}
			assertEquals(afterTaxesAndServices, "0.00", "Failed to verify Taxes And Service Charges");
			test_steps.add("After Tax Exampt Checked Taxes and Service Charges : " + afterTaxesAndServices);
			Utility.app_logs.info("After Tax Exampt Checked Taxes and Service Charges : " + afterTaxesAndServices);

			test_steps.add("Tax exempt not working for group account folio "
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-8136' target='_blank'>"
					+ "Verified : NG-8136</a><br/>");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
*/
		// Create RoomBlock

		try {
			test_steps.add("================Create RoomBlock================");
			app_logs.info("=================Create RoomBlock=================");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName, RoomPerNight, RoomClassName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("================RoomBlock Edit================");
			app_logs.info("=================RoomBlock Edit=================");
			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");

			getTest_Steps.clear();
			getTest_Steps = group.changeAriveDepartDate(driver, true, 1, true, 2);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps = group.errorMsg(driver, true);
			getTest_Steps.clear();
			
			test_steps.add("Verified the block when stay dates are changed without clicking search button");
			app_logs.info("Verified the block when stay dates are changed without clicking search button");
			comments="Verified the block when stay dates are changed without clicking search button. ";
			
			
			getTest_Steps = group.clickBlockSearch(driver);
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			getTest_Steps.clear();
			getTest_Steps = group.errorMsg(driver, true);
			test_steps.addAll(getTest_Steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			test_steps.add(e.toString());
		}

		try {

			Utility.app_logs.info("Block is not saved until new rooms are blocked after clicking on search");
			test_steps.add("Block is not saved until new rooms are blocked after clicking on search"
					+ "<br/><a href='https://innroad.atlassian.net/browse/NG-7311' target='_blank'>"
					+ "Verify : NG-7311 </a>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-9930' target='_blank'>"
					+ " Verify : NG-9930 </a><br/>");

			getTest_Steps.clear();
			
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, RoomPerNight);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			
			getTest_Steps.clear();
			getTest_Steps = group.editDialogDone(driver);
			test_steps.addAll(getTest_Steps);
			
			test_steps.add("Verified the block after clicking search button when stay dates are changed");
			app_logs.info("Verified the block after clicking search button when stay dates are changed");
			comments=comments+"Verified the block after clicking search button when stay dates are changed.";
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
		getTest_Steps.clear();
		getTest_Steps = group.bookIconClick(driver, RoomClassName);
		test_steps.addAll(getTest_Steps);
		test_steps.add("=================Create Reservation================");
		app_logs.info("==================Create Reservation=================");
		getTest_Steps.clear();
		String expiryDate = Utility.getFutureMonthAndYearForMasterCard();
		reservationPage.clickNext(driver, test_steps);
		reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, "Mr.",
				AccountFirstName, AccountLastName, config.getProperty("flagOff"));
		reservationPage.enter_PaymentDetails(driver, test_steps, "Cash", "", "", expiryDate);
		reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);
		reservationPage.clickBookNow(driver, test_steps);
		reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
		reservationPage.get_ReservationStatus(driver, test_steps);
		reservationPage.clickCloseReservationSavePopup(driver, test_steps);
		test_steps.add("Successfully Associated Account to  Reservation");
		app_logs.info("Successfully Associated Account to Reservation");
		}catch (Exception e) {
		Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
				test_description, test_catagory, test_steps);
		} catch (Error e) {
		Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
				test_description, test_catagory, test_steps);

		}
		try {
		//verify taxes
		String taxValue=reservationPage.getTaxesAndServiceCharges_TripSummary(driver);
		Assert.assertEquals(taxValue, "0.00", "Failed to verify the taxes");
		
		checkInDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
		checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2),ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
			} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);

		}
		
		//Verify Deposit Amount
		try {
			test_steps.add("========== Verify Deposit Amount==========");
			reservationPage.clickFolio(driver, test_steps);
			roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0), true);
			for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
				roomCharges.add(entry.getValue());
			}
		
			app_logs.info(roomCharges);
			String size= String.valueOf(RoomClassName.split("\\|").length);
			app_logs.info(size);
			depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges, policyAttr1Is[0], policyAttrValueIs[0],size);
			app_logs.info(depositAmount);
			paymentTypeIs="Cash";

			verificationDepositWithPolicy("Cash",policyNames.get(0),depositAmount,paymentTypeIs);
			comments=comments+"verified deposit amount and taxes when reservation is made from group block with tax exempt check box enabled at account level";
			test_steps.add("verified deposit amount and taxes when reservation is made from group block with tax exempt check box enabled at account level");
			app_logs.info("verified deposit amount and taxes when reservation is made from group block with tax exempt check box enabled at account level");
				}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);

		}
		try {
		test_steps.add("=============navigate to Room Block============");
		app_logs.info("==============navigate to Room Block=============");
		nav.Groups(driver);

		getTest_Steps.clear();
		getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
		test_steps.addAll(getTest_Steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
			} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);

			}

		
		try {
			test_steps.add("================Create RoomBlock================");
			app_logs.info("=================Create RoomBlock=================");
			group.navigateRoomBlock(driver, test);
			Utility.app_logs.info("Navigate to Room Block Tab");
			test_steps.add("Navigate to Room Block Tab");
			getTest_Steps.clear();
			getTest_Steps = group.createNewBlock(driver, BlockName+"2", RoomPerNight, RoomClassName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickCreateBlock(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			group.navigateRoomBlock(driver, test);
			getTest_Steps.clear();
			getTest_Steps = group.clickSecondBlock(driver, BlockName+"2");
			test_steps.addAll(getTest_Steps);
			
			getTest_Steps = group.roomingListClick(driver);
			test_steps.addAll(getTest_Steps);
			advgrp.verify_RoomingList_details(driver, BlockName+"2");
			getTest_Steps.clear();
			String expriryDate=Utility.getFutureMonthAndYearForMasterCard();
			test_steps.add("=====================Room Pickup from Rooming list================");
			app_logs.info("=====================Room Pickup from Rooming list=================");
			getTest_Steps = advgrp.roomingListPopup_RoomPickup(driver, AccountFirstName, AccountLastName, RoomClassName,
					"", Phonenumber, Address1, city, Country, State, Postalcode,"MC",  "5454545454545454",expriryDate);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("dialog-body0");
			
			getTest_Steps.clear();
			getTest_Steps = group.verifyBillingInfoFullyPaid(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();

			getTest_Steps = group.pickUpClick(driver);
			test_steps.addAll(getTest_Steps);
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame("dialog-body1");	
			Elements_AdvanceGroups AdvGroup = new Elements_AdvanceGroups(driver);
			String resNumber = AdvGroup.GeneratedReservationNumber.getText();
			driver.switchTo().defaultContent();
			advgrp.verify_RoomingList_PickUp_Summary(driver, BlockName+"2");
			nav.ReservationV2_Backward(driver);
			reservationPage.Search_ResNumber_And_Click(driver, resNumber);
			String taxV=reservationPage.getTaxesAndServiceCharges_TripSummary(driver);
			Assert.assertEquals(taxV, "0.00", "Failed to verify the taxes");
			

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Verify Deposit Amount
		try {
			checkInDates.clear();
			checkOutDates.clear();
			checkInDates.add(Utility.getCurrentDate(ratesConfig.getProperty("defaultDateFormat")));
			checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1),ratesConfig.getProperty("monthDateYearFormat"), ratesConfig.getProperty("defaultDateFormat")));

			test_steps.add("========== Verify Deposit Amount==========");
			reservationPage.clickFolio(driver, test_steps);
			roomChargesAre.clear();
			roomCharges.clear();
			roomChargesAre= reservationPage.getRoomChargesFromFolioBasedOnDates(driver, test_steps, checkInDates.get(0), checkOutDates.get(0), true);
			for (Map.Entry<String, String> entry : roomChargesAre.entrySet()) {
				roomCharges.add(entry.getValue());
			}
		
			app_logs.info(roomCharges);
			String size= String.valueOf(RoomClassName.split("\\|").length);
			app_logs.info(size);
			depositAmount=reservationPage.calculationOfDepositAmountToBePaidForRateV2(roomCharges, policyAttr1Is[0], policyAttrValueIs[0],size);
			app_logs.info(depositAmount);
		//deposit policy verify
			reservationPage.verifyFolioLineItemAmoutPaid(driver, "MC", depositAmount, test_steps);
			reservationPage.verify_FolioPayment(driver, test_steps, depositAmount);
			reservationPage.clickOnDetails(driver);
			reservationPage.verifyDepositPolicyDetailsAtPoliciesAndDisclaimers(driver, test_steps, policyNames.get(0), "");
			reservationPage.verifyTripSummaryPaidAmount(driver, test_steps, depositAmount);
	
			comments=comments+"verified deposit amount and taxes when reservation is made from rooming list with tax exempt check box enabled";
			test_steps.add("verified deposit amount and taxes when reservation is made from rooming list with tax exempt check box enabled");
			app_logs.info("verified deposit amount and taxes when reservation is made from rooming list with tax exempt check box enabled");
				}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Deposit Amount", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);

		}


		// account inActive
		try {
			test_steps.add("================account inActive================");
			app_logs.info("=================account inActive=================");
			nav.Groups(driver);
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.changeActiveStatus(driver, "Inactive");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			/*try {
				nav.Setup(driver);
				test_steps.add("Navigate Setup");
				app_logs.info("Navigate to setup");
				tax.NavToTaxes(driver);
				test_steps.add("Navigate Taxes");
				app_logs.info("Navigate to Taxes");
				getTest_Steps = tax.delete_tax(driver, TaxName);
				test_steps.addAll(getTest_Steps);
			} catch (Exception e) {
				System.out.println("Tried to Delete Tax");
			}*/
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}

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
		return Utility.getData("VerifyAGrpWithEditBlockTaxExmpt", BEExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}

}
