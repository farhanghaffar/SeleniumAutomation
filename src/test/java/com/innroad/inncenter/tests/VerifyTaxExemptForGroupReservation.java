package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyTaxExemptForGroupReservation extends TestCore{
	private WebDriver driver;

	public static String test_description = "";
	public static String test_category = "";
	public static String test_name = "";
	public static ArrayList<String> test_steps = new ArrayList<>();
	public static ArrayList<String> getTest_Steps = new ArrayList<>();
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

String reservation=null;	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, SNExcel))
			throw new SkipException("Skipping the test - " + testName);

	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyTaxExemptInCPReservation(String TestCaseID,
			String Adults, String Children, String Rateplan, String PromoCode, String RoomClass, String IsAssign,
			String IsDepositOverride, String DepositOverrideAmount, String Salutation, String GuestFirstName,
			String GuestLastName, String PhoneNumber, String AltenativePhone, String Email, String Account,
			String AccountType, String Address1, String Address2, String Address3, String City, String Country,
			String State, String PostalCode, String IsGuesProfile, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String IsChangeInPayAmount, String ChangedAmountValue,
			String TravelAgent, String MarketSegment, String Referral, String IsAddNotes, String NoteType,
			String Subject, String Description, String IsTask, String TaskCategory, String TaskType, String TaskDetails,
			String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus, String TaxName,
			String TaxDescription, String value, String category, String TaxDisplayName, String Vat, String TaxExcempt,
			String Percent, String taxLedgerAccount, String bedsCount, String maxAdults, String maxPersopns,
			String roomQuantity, String ratename, String maxadults, String maxpersopns, String baseAmount,
			String additionalAdult, String additionalChild, String taxExcemptId) throws Exception {
		 boolean isExcecutable= Utility.getResultForCase(driver, TestCaseID);
         if(isExcecutable) {
		//TestCaseID="848737";
		
			caseId.add(TestCaseID);
			statusCode.add("4");
			String[] testcase=TestCaseID.split("\\|");
			for(int i=0;i<testcase.length;i++) {
				caseId.add(testcase[i]);
				statusCode.add("4");
			}
		test_name = "VerifyTaxExemptInCPReservation";
		test_description = "Verify Tax Exempt In CP Reservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682449' target='_blank'>"
				+ "Click here to open TestRail: 682449</a>";
		test_category = "TaxExemptInCPReservation";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		CPReservationPage res = new CPReservationPage();
		Navigation nav = new Navigation();
		NewRoomClassesV2 newRcPage = new NewRoomClassesV2();
		RoomClass roomClass = new RoomClass();
		Login login=new Login();
		
		AdvGroups grps= new AdvGroups();
		
		Rate rate = new Rate();
		Tax tax = new Tax();
		TaxesAndFee taxFees=new TaxesAndFee();
		String randomString = "";
		String taxName = "";
		String roomClassName = "";
		String rcDelete = "";
		String rateDelete = "";
		String rateName = "";
		String deleteTaxesName = "";
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
			String RcNaming = RoomClass;
			rcDelete = RcNaming.replace("XXX", "");
			randomString = Utility.GenerateRandomNumber();
			//nav.NewRoomClass(driver);
			roomClassName = RoomClass.replace("XXX", randomString);
			/*
			 * roomClass.Create_RoomClass(driver, roomClassName, roomClassName, bedsCount,
			 * maxAdults, maxPersopns, roomQuantity, test, test_steps);
			 */
			
			newRcPage.createRoomClassV2( driver, roomClassName, roomClassName,
					maxAdults, maxPersopns, roomQuantity,
					test, test_steps);
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
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
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
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Create Rate", "NONGS_Login", "Rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_category, test_steps);
				Utility.updateReport(e, "Failed to Create Rate", "NONGS_Login", "Rate", driver);
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
			boolean percentage = true;
			boolean excludeTaxExempts = true;
			tax.clickOnCreateTaxButton(driver);
			tax.createNewTax(driver, TaxName, TaxName, Description, value, category, taxLedgerAccount, excludeTaxExempts, percentage, false, false, "", "", "");
			app_logs.info("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);
			test_steps.add("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);


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
			test_steps.add("<b>*****************Creating group reservation</b>***************************");
			nav.cpReservation_Backward(driver);
			test_steps.add("Click at resrevation menue link ");
			nav.groups(driver);
			String accName= "AutoGrp"+Utility.generateRandomString();
			grps.createGroupaccount(driver, "Internet", "Other", "AutoGrp"+Utility.generateRandomString(), "N Test Group", "N Last Name", "9872145670", "Test", "test", "Alaska", "98765", "United States", test_steps);
			grps.select_TaxExempt(driver, test_steps, "1234");
			
			grps.SaveAdvGroup(driver);
			grps.click_GroupNewReservation(driver, test_steps);
							
			
			//res.click_NewReservation(driver, test_steps);
			getTest_Steps.clear();
			getTest_Steps = res.checkInDate(driver, 0);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.checkOutDate(driver, +2);
			test_steps.addAll(getTest_Steps);
			res.enter_Adults(driver, test_steps, Adults);
			res.enter_Children(driver, test_steps, Children);
			res.select_Rateplan(driver, test_steps, Rateplan, PromoCode);
			res.clickOnFindRooms(driver, test_steps);
			res.selectRoom(driver, test_steps, roomClassName, IsAssign, accName);
			res.deposit(driver, test_steps, IsDepositOverride, DepositOverrideAmount);
			res.clickNext(driver, test_steps);
			randomString = Utility.GenerateRandomNumber();
			String gLastName = GuestLastName.replace("XXX", randomString);
			res.enter_MailingAddressWithoutAccpayment(driver, test_steps, Salutation, GuestFirstName, gLastName, PhoneNumber,
					AltenativePhone, Email, Account, AccountType, Address1, Address2, Address3, City, Country, State,
					PostalCode, IsGuesProfile);
			if ((Account.equalsIgnoreCase("") || Account.isEmpty())) {
				res.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, CardExpDate);
			}
			//res.createTaxExempt(driver, taxExcemptId, test_steps);
			res.enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral);

			res.clickBookNow(driver, test_steps);
			reservation=res.get_ReservationConfirmationNumber(driver, test_steps);
			res.clickCloseReservationSavePopup(driver, test_steps);
			
			String taxes=res.get_TripSummaryTaxesWithoutCurrency(driver, test_steps);
			
			if(taxes.trim().equalsIgnoreCase("0.00")) {
				app_logs.info("Tax amount verified as 0.00 in trip summary");
				test_steps.add("Tax amount verified as 0.00 in trip summary");	
			}else {
				app_logs.info("Assertion Error : failed to verify tax amount as 0.00 in trip summary");
				test_steps.add("Assertion Error : failed to verify tax amount as 0.00 in trip summary");	
			}
			
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
			test_steps.add(
					"<b>*****************Verify tax get exempted in reservation folio</b>***************************");
			res.click_Folio(driver, test_steps);
			String taxes=res.get_Taxes(driver, test_steps);
			if(taxes.trim().equalsIgnoreCase("0.00")) {
				app_logs.info("Tax amount verified as 0.00 in folio");
				test_steps.add("Tax amount verified as 0.00 in folio");	
			}else {
				app_logs.info("Assertion Error : failed to verify tax amount as 0.00 in folio");
				test_steps.add("Assertion Error : failed to verify tax amount as 0.00 in folio");	
			}
			
			
			
			boolean isTaxExist = res.isTaxPresentAfterTaxExempt(driver, test_steps, taxLedgerAccount, taxName);
			Assert.assertEquals(isTaxExist, false);
		} catch (Exception e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify Tax Exempt", "NONGS_Login", "Create Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {

			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Verify Tax Exempt", "NONGS_Login", "Create Reservation", driver);
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
			rate.DeleteRate(driver, rateDelete);
			test_steps.add("Rates Deleted starting with name: " + "<b>" + rateDelete + "</b>");
			nav.Setup(driver);
			test_steps.add("Navigate to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigate to Room Classes");
			//roomClass.SearchRoomClass(driver, roomClassName, test_steps);
			//test_steps.add("Searched room class: " + "<b>" + roomClassName + "</b>");
			newRcPage.deleteRoomClassV2(driver, roomClassName);
			test_steps.add("Room Class deleted starting from: "+ "<b>"+roomClassName+"</b>");
			comments = "Created rservation with "+reservation+ ""+" and verify the applicable tax on created reservation";
		     statusCode.set(0, "1");
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
         

	}

	@DataProvider()
	public Object[][] getData() {
		return Utility.getData("VerifyTaxExemptInCPReservation", SNExcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.close();
		Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	}
}
