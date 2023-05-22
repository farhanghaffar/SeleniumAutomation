package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

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
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CopyOfNotesOfGroupAndReservationToCopiedToReservation extends TestCore  {


	// Automation-1463, 1364 and 1365
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		//C825286
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void copyOfNotesOfGroupAndReservationToCopiedToReservation(String TestCaseID, String rateName, String baseAmount,
			String addtionalAdult, String additionalChild, String displayName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber,
			String ratePlan, String rateType, String rateAttributes, String interval, String source, String adults,
			String child, String marketSegment, String groupReferral, String groupFirstName, String groupLastName,
			String groupPhn, String groupAddress, String groupCity, String groupCountry, String groupState,
			String groupPostalcode, String blockName, String roomPerNight, String firstName, String lastName,
			String updatedBlockedCount, String roomBlockCount, String lineItemDescription, String roomCharge,
			String postedState, String itemRow, String spanTag, String guestFolio, String pendingState,
			String blueBookClass,String checkInDate,String checkOutDate,
			String	adultsForRes,String	childrenForRes	,String salutation,String	guestFirstName,String	guestLastName,
			String phoneNumber,String	altenativePhone,String	email,String	account	,String accountType,String	address1,String	address2,String	address3,String	city,
			String country,String	state,String	postalCode	,String isGuesProfile,String	paymentMethod,String	cardNumber,String	cardExpDate,
			String referral,String	postedIconSrc,String	NoteType,String	Subject,String	Description,String Res_NoteType,String Res_Subject,String Res_Description) throws Exception {

            
		String testName = this.getClass().getSimpleName().trim();
		
		String testcaseId="848625|848624|848528";
		
	    Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848625");
	
	    test_description = testName + "<br>"
			+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848625' target='_blank'>"
			+ "Click here to open TestRail: C848625</a><br>";
			
		
		
		test_catagory = "Groups";
		ScriptName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		CPReservationPage reservation = new CPReservationPage();
		
		String randomNumber = Utility.GenerateRandomNumber();
		//roomClassName = roomClassName;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		//rateName = rateName;
		displayName = rateName;
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		lastName = lastName + randomNumber;
		String blueResFirstName = "Blue" + firstName;
		String accountNumber = null;
		String saluation = "Mr.";
		String reservationNumber = null;
		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		blockName = blockName + randomNumber;
		
		
		String reservationConfirmationNo;
		guestLastName=guestLastName+Utility.generateRandomNumber();
		String guestFullName=guestFirstName+" "+guestLastName;
		String status;
		String guestFirstName_Copy=guestFirstName;
		String guestLastName_copy=guestLastName+"_Copy";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Autoota(driver);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		
		if ( !(Utility.validateInput(checkInDate)) ) {
			for (int i = 0; i < firstName.split("\\|").length; i++) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
			}
		}else {
			checkInDates = Utility.splitInputData(checkInDate);
			checkOutDates = Utility.splitInputData(checkOutDate);
		}

		// Clicking on Groups
		try {
			//navigation.navigateToReservations(driver);
			testSteps.add("Navigate  Groups");
			app_logs.info("Navigate  Groups");

			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Click New Account and Enter Account Name
		try {
			app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
			testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.enterrGroupName(driver, accountName);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", testName,
						"EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Number
		try {
			accountNumber = Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, accountNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Attributes
		try {
			getTestSteps.clear();
			getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Mailing Address
		try {
			getTestSteps.clear();
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, groupPhn, groupAddress,
					groupCity, groupState, groupCountry, groupPostalcode);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Check Mailing Info CheckBox
		try {
			getTestSteps.clear();
			getTestSteps = group.Billinginfo(driver);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Save Account
		try {
			getTestSteps.clear();
			getTestSteps = group.clickOnSave(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		try {
			app_logs.info("==========CREATE NEW BLOCK==========");
			testSteps.add("==========CREATE NEW BLOCK==========");

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickNewBlock(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.EnterBlockName(driver, blockName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickOkay_CreateNewBlock(driver);
			testSteps.addAll(getTestSteps);

			app_logs.info("==========SEARCH ROOMS==========");
			testSteps.add("==========SEARCH ROOMS==========");

			getTestSteps.clear();
			getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("dd/MM/yyyy"),
					Utility.GetNextDate(1, "dd/MM/yyyy"));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectRatePlan(driver, ratePlan);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectAdults(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectChilds(driver, child);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.EnterNights(driver, roomPerNight);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.ClickSearchGroup(driver);
			testSteps.addAll(getTestSteps);

			advgroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
			advgroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);
			getTestSteps.clear();

			advgroup.ClickCreateBlock(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			
			advgroup.clickOnAcountTab(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			advgroup.addNotesInGroupAccount(driver, getTestSteps, "yes", NoteType, Subject, Description,"swarna");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = group.clickOnSave(driver);
			testSteps.addAll(getTestSteps);
			
		
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		

		try {
			testSteps.add("========== Creating new Reservation ==========");
			account=accountName;
			accountType="Group";
			navigation.Reservation_Backward(driver);
			Wait.wait10Second();
		
			reservation.click_NewReservation(driver, testSteps);
			reservation.select_CheckInDate(driver, testSteps, checkInDates.get(0));
			reservation.select_CheckoutDate(driver, testSteps, checkOutDates.get(0));
			reservation.enter_Adults(driver, testSteps, adultsForRes);
			reservation.enter_Children(driver, testSteps, childrenForRes);
			reservation.select_Rateplan(driver, testSteps, ratePlan,"");
			reservation.clickOnFindRooms(driver, testSteps);
			reservation.selectRoom(driver, testSteps, roomClassName, Utility.RoomNo, "");
			//depositAmount=reservationPage.deposit(driver, testSteps, "", "");		
			reservation.clickNext(driver, testSteps);
			
			reservation.enter_MailingAddress(driver, testSteps, salutation, guestFirstName, guestLastName,
					phoneNumber, altenativePhone, email, account, accountType, address1, address2, address3, city,
					country, state, postalCode, isGuesProfile);
				if((account.equalsIgnoreCase("")||account.isEmpty())) {
					reservation.enter_PaymentDetails(driver, testSteps, paymentMethod, cardNumber, guestFullName, cardExpDate);
			}
			reservation.enter_MarketSegmentDetails(driver, testSteps, "", "", referral);
			reservation.clickBookNow(driver, testSteps);
			reservationConfirmationNo=reservation.get_ReservationConfirmationNumber(driver, testSteps);
			status=reservation.get_ReservationStatus(driver, testSteps);
			reservation.clickCloseReservationSavePopup(driver, testSteps);
			testSteps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			getTestSteps.clear();
			getTestSteps = reservation.verifyAssociatedAccount(driver, accountName);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			reservation.enter_Notes(driver, testSteps,"yes" , Res_NoteType, Res_Subject, Res_Description);
			reservation.verifyNotesDetails(driver,testSteps, Res_NoteType, Res_Subject, Res_Description,"swarna sunani");
			//reservation.closeAllOpenedReservations(driver);
			//reservation.SearchandClickgivenReservationNumber(driver, reservationConfirmationNo);
			getTestSteps.clear();
			reservation.verifyNotesCopiedOrNot(driver,getTestSteps,NoteType, Subject, Description,true);
			testSteps.addAll(getTestSteps);
			
		

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		

		// Create Reservation
		try {
			app_logs.info("==========COPY RESERVATION AND VERIFY THE NOTES==========");
			testSteps.add("==========COPY RESERVATION AND VERIFY THE NOTES==========");

			getTestSteps.clear();
			reservation.closeAllOpenedReservations(driver);
			reservation.copyReservation(driver, testSteps, guestFullName, guestFirstName_Copy, guestLastName_copy);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservation.closeAllOpenedReservations(driver);
			reservation.guestUnlimitedSearchAndOpen(driver, getTestSteps, guestFirstName_Copy+" "+guestLastName_copy, null, true, 12);
			
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservation.verifyNotesDetails(driver,testSteps, Res_NoteType, Res_Subject, Res_Description,"swarna sunani");
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservation.verifyNotesCopiedOrNot(driver,getTestSteps,NoteType, Subject, Description,true);
			testSteps.addAll(getTestSteps);
			
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		
		try {

			
			statusCode.add(0, "1");
			comments.add(0, "Group account and reservation notes are showing in copied reservation");
			statusCode.add(1, "1");
			comments.add(1, "Group account and reservation notes are showing in copied reservation");
			statusCode.add(2, "1");
			comments.add(2, "Group account and reservation notes are showing in copied reservation");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Pay Via Card", testName, "CardPayment", driver);
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		
		return Utility.getData("CopyOfNotesOfGroupAndReservati", excel_Swarna);
	}
	

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}


}
