package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
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

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class TaxExemptInDepositAmountInRes extends TestCore {

	private WebDriver driver;

	public static String test_description = "";
	public static String test_category = "";
	public static String test_name = "";
	public static ArrayList<String> test_steps = new ArrayList<>();
	public static ArrayList<String> getTest_Steps = new ArrayList<>();

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;
	
	//String statusCode="5";
	//String caseId="786728";
	String reservation=null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void taxExemptInDepositAmountInRes(String TestCaseID,
			String adults, String children, String ratePlan, String promoCode, String isSplitRes, String roomClass,
			String isAssign, String isDepositOverride, String depositOverrideAmount, String isAddMoreGuestInfo,
			String salutation, String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone,
			String email, String account, String accountType, String address1, String address2, String address3,
			String city, String country, String state, String postalCode, String isGuesProfile, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String isChangeInPayAmount,
			String changedAmountValue, String travelAgent, String marketSegment, String referral, String isAddNotes,
			String noteType, String subject, String description, String isTask, String taskCategory, String taskType,
			String taskDetails, String taskRemarks, String taskDueon, String taskAssignee, String taskStatus,
			String taxName, String taxDescription, String value, String category, String taxDisplayName, String vat,
			String taxExcempt, String percent, String taxLedgerAccount, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String ratename, String maxadults, String maxpersopns,
			String baseAmount, String additionalAdult, String additionalChild, String taxExcemptId, String policyName,
			String policyType, String chargestype, String number, String policyText, String policyDesc, String season) throws Exception {

		if(!Utility.validateInput(TestCaseID)) {
			caseId.add("848671");
			statusCode.add("4");
		}else {
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
			
		}
		test_name = "TaxExcemptInDepositAmountInRes";
		test_description = "Verify Tax Exempt In CP Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682449' target='_blank'>"
				+ "Click here to open TestRail: 682449</a>";
		test_category = "TaxExemptInCPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage res = new CPReservationPage();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		Navigation nav = new Navigation();
		RoomClass roomClas = new RoomClass();
		Rate rate = new Rate();
		Tax tax = new Tax();
		TaxesAndFee taxFees=new TaxesAndFee();
		Login login=new Login();
		String randomString = "";
		String roomClassName = "";
		String rcDelete = "";
		String rateDelete = "";
		String rateName = "";
		double totaltax = 0.0;
		boolean isdefaultTaxAvailable = false;
		String deleteTaxesName = "";

		Policies pol = new Policies();

		String policyNames = "";

		String tripSummaryRoomCharges = "";
		String tripSummaryTaxes = "";
		String depositDue = "";
		int stayDays = 0;
		try {
			test_steps.add("<b> ************Logging in to the Application</b>****************");
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//login.login(driver, envURL, "client3281", "autouser", "Auto@123");
			loginClinent3281(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Create Room Class
			test_steps.add("<b> ************Create Room Class</b>****************");
			// create room class
			nav.Setup(driver);
			test_steps.add("Navigate to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigate to RoomClasses tab");
			String rcNaming = roomClass;
			rcDelete = rcNaming.replace("XXX", "");
			randomString = Utility.GenerateRandomNumber();
		//	nav.NewRoomClass(driver);
			roomClassName = roomClass.replace("XXX", randomString);
			/*
			 * roomClas.Create_RoomClass(driver, roomClassName, roomClassName, bedsCount,
			 * maxAdults, maxPersons, roomQuantity, test, test_steps);
			 */
			
			newRcPage.createRoomClassV2( driver, roomClassName, roomClassName,
					maxAdults, maxPersons, roomQuantity,
					test, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Create Roomclass", "NONGS_Login", "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Create Roomclass", "NONGS_Login", "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Create Rate
			test_steps.add("<b> ************Create Rate/b>****************");
			// ceate rate
			String rateNaming = ratename;
			rateDelete = rateNaming.replace("XXX", "");
			randomString = Utility.GenerateRandomNumber();
			rateName = ratename.replace("XXX", randomString);
			nav.InventoryV2(driver);
			test_steps.add("Navigate to Inventory");
			nav.Rate(driver);
			test_steps.add("Navigate to Rate");

			rate.new_Rate(driver, rateName, maxadults, maxpersopns, baseAmount, additionalAdult, additionalChild,
					rateName, rateName, rateName, roomClassName);
			test_steps.add("Rate name: " + "<b>" + rateName + "</b>");
			test_steps.add("Room class name selected in the rate: " + "<b>" + roomClassName + "</b>");
			test_steps.add("Rate created");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create rate", "NONGS_Login", "create rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add("******************* Creating policy **********************");
			app_logs.info("******************* Creating policy **********************");
			// nav.Inventory(driver);
			nav.policies(driver);
			pol.delete_Policies(driver, policyType);
			app_logs.info("deleted all exisitng deposit policies");
			test_steps.add("deleted all exisitng deposit policies");
			pol.ClickNewPolicybutton(driver);
			randomString = Utility.GenerateRandomNumber();
			policyNames = policyName.replace("XXX", randomString);
			pol.Enter_Policy_Name(driver, policyNames, test_steps);
			pol.Enter_Policy_Type(driver, policyType);
			pol.Deposit_Policy_Attributes(driver, chargestype, number, test_steps);
			pol.Enter_Policy_Desc(driver, policyNames, policyNames);
			pol.Associate_Sources(driver);
			pol.Associate_Seasons(driver, season);

			pol.Associate_RoomClasses(driver, roomClassName);
			app_logs.info("Associating Room Class to policy : " + roomClassName);
			test_steps.add("Associating Room Class to policy : " + roomClassName);
			pol.Associate_RatePlan(driver, ratePlan);
			app_logs.info("Associating Rateplan to policy : " + ratePlan);
			test_steps.add("Associating Rateplan to policy : " + ratePlan);
			pol.Save_Policy(driver);
			pol.Close_Policy_Tab(driver);
			test_steps.add("Created Policy : " + policyNames);
			app_logs.info("Created Policy : " + policyNames);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create policy", "NONGS_Login", "Policy", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create policy", "NONGS_Login", "Policy", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Create Tax

			test_steps.add("<b>************** Making Tax Setup*******************</b>");
			nav.Setup(driver);
			test_steps.add("Navigate to setup");
			tax.clickOnTaxButton(driver);
			test_steps.add("Navigate to taxes page");
			taxFees.deleteAllTaxesAndFee(driver, test_steps);
			// create new tax------------------
			test_steps.add("<b>********** Creating New percent Tax****************</b>");
			boolean Percentage = true;
			boolean excludeTaxExempt = true;
			boolean Vat = false;
			test_steps.add("percent Tax name is: " + "<b>" + taxName + " </b>");
			tax.clickOnCreateTaxButton(driver);
			tax.createNewTax(driver, taxName, taxName, taxName, value, category, taxLedgerAccount, excludeTaxExempt, Percentage, false, false, "", "", "");
			test_steps.add("Click at vat checkbox");
			test_steps.add("Tax Category: " + "<b>" + category + "</b>");
			test_steps.add("Created tax with percent amount: " + "<b>" + value + "</b>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "create tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create tax", "NONGS_Login", "create tax", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add("<b>*****************Creating CP reservation</b>***************************");
			nav.cpReservation_Backward(driver);
			test_steps.add("Click at resrevation menue link ");

			res.click_NewReservation(driver, test_steps);
			getTest_Steps.clear();
			getTest_Steps = res.checkInDate(driver, 0);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			stayDays = 2;
			getTest_Steps = res.checkOutDate(driver, +stayDays);
			test_steps.addAll(getTest_Steps);
			res.enter_Adults(driver, test_steps, adults);
			res.enter_Children(driver, test_steps, children);
			res.select_Rateplan(driver, test_steps, ratePlan, promoCode);
			res.click_FindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, roomClassName, isAssign, account);
			res.deposit(driver, test_steps, isDepositOverride, depositOverrideAmount);
			res.clickNext(driver, test_steps);
			randomString = Utility.GenerateRandomNumber();
			String gLastName = guestLastName.replace("XXX", randomString);
			res.enter_MailingAddress(driver, test_steps, salutation, guestFirstName, gLastName, phoneNumber,
					altenativePhone, email, account, accountType, address1, address2, address3, city, country, state,
					postalCode, isGuesProfile);
			if ((account.equalsIgnoreCase("") || account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, cardExpDate);
			}
			res.createTaxExempt(driver, taxExcemptId, test_steps);

			res.enter_MarketSegmentDetails(driver, test_steps, travelAgent, marketSegment, referral);
			depositDue = res.getDepositDueAmount(driver);
			res.clickBookNow(driver, test_steps);
			res.get_ReservationConfirmationNumber(driver, test_steps);
			res.get_ReservationStatus(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			res.get_RoomNumber(driver, test_steps, isAssign);
			tripSummaryRoomCharges = res.get_TripSummaryRoomChargesWithCurrency(driver, test_steps);
			tripSummaryTaxes = res.get_TripSummaryTaxesWithCurrency(driver, test_steps);
			res.getTripSummaryTripTotal(driver, test_steps);
			res.get_TripSummaryPaidWithCurrency(driver, test_steps);
			res.get_TripSummaryBalanceWithCurrency(driver, test_steps);
			res.get_AssociatedPoliciesToReservation(driver, test_steps);
			res.verify_DepositPolicyAssociated(driver, test_steps, policyNames);
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create Reservation", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create Reservation", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Verify DepositDue calculation and assertion
			test_steps.add("<b>*****************Verifying DepositDue Amount</b>***************************");
			String calculatedDepositDue = res.getPercentcalcvalueSingleItem(driver, number,
					tripSummaryRoomCharges.replace("$", "").trim(), stayDays);
			double calcDeposit = Double.parseDouble(calculatedDepositDue) * stayDays;
			Assert.assertEquals(Double.parseDouble(depositDue), calcDeposit);
			test_steps.add("Verified DepositDue Amount over calculated: " + "</b>" + calcDeposit + "</b>");

			test_steps.add("<b>*****************Verifying Paid Amount</b>***************************");
			String paidAmount = res.get_TripSummaryPaid(driver, test_steps);
			Assert.assertEquals(Double.parseDouble(paidAmount), calcDeposit);
			test_steps.add("Verified paid Amount over calculated: " + "</b>" + calcDeposit + "</b>");

			test_steps.add(
					"<b>*****************Verify Total Tax in TripSummary</b>***************************");
			Assert.assertEquals(Double.parseDouble(tripSummaryTaxes.replace("$", "").trim()), totaltax);
			test_steps.add("Verified Total tax Amount over calculated: " + "</b>" + totaltax + "</b>");
			test_steps.add(
					"<b>*****************Verify tax get exempted in reservation folio</b>***************************");
			res.click_Folio(driver, test_steps);
			boolean isTaxExist = res.isTaxPresentAfterTaxExempt(driver, test_steps, taxLedgerAccount, taxName);
			Assert.assertEquals(isTaxExist, false);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify DepositDue/Paid", "NONGS_Login", "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify DepositDue/Paid", "NONGS_Login", "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			// Delete created records for the script
			test_steps.add("<b>*****************Deleting created Records for Script********************</b>");
			nav.Inventory(driver);
			test_steps.add("Navigate to Inventory");
			nav.Rate(driver);
			test_steps.add("Navigate to Rate");
			rate.deleteRates(driver, rateDelete);
			test_steps.add("Rates Deleted starting with name: " + "<b>" + rateDelete + "</b>");
			nav.Setup(driver);
			test_steps.add("Navigate to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigate to Room Classes");
			//roomClas.SearchRoomClass(driver, roomClassName, test_steps);
			//test_steps.add("Searched room class: " + "<b>" + roomClassName + "</b>");
			//roomClas.Delete_RoomClass(driver, rcDelete);
			newRcPage.deleteRoomClassV2(driver, roomClassName);
			test_steps.add("Room Class deleted starting from: "+ "<b>"+roomClassName+"</b>");
			comments = "Created rservation with "+reservation+ ""+" and verify the applicable tax on created reservation";
			statusCode.set(0,"1");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(test_name, test_description, test_category, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to delete room class and rate", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider()
	public Object[][] getData() {
		return Utility.getData("TaxExemptInDepositAmountInRes", BEExcel);

	}
	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
			//driver.close();
		   Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
}
}