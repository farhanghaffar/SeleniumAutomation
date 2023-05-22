package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationFolio;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_On_All_Navigation;

public class VerifyGroupAccountWithTaxExempt extends TestCore {
	private WebDriver driver = null;

	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
    ArrayList<String> checkOutDates = new ArrayList<>();

	// Before Test
	@BeforeTest
	public void checkRunMode() {
		Utility.getData("VerifyGroupAccountWithTaxExempt", excel);
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
	public void verifyGroupAccountWithTaxExempt(String url, String clientcode, String username, String password,
			String RoomClassAbb, String RoomClassName, String maxAdults, String maxPersons, String roomQuantity,
			String RateName, String bedsCount, String BaseAmount, String AddtionalAdult, String AdditionalChild,
			String AssociateSession, String RatePolicy, String RateDescription, String AccountName,
			String MargetSegment, String Referral, String AccountFirstName, String AccountLastName, String Phonenumber,
			String Address1, String city, String Country, String State, String Postalcode, String ChargeRoutingAllItem,
			String ChargeRoutingRoomChargeOnly, String BlockName, String RoomPerNight, String DepositPolicyName,
			String DepositPolicytype, String DepositAttribute1, String NumAttribute1, String PolicyText,
			String PolicyDesc, String RackRate, String TaxName, String DisplayName, String Description, String value,
			String Category, String LedgerAccount, String TaxExmptId, String PaymentMethod, String CardNumber,
			String ExpiryDate, String LineCategory, String LineAmount,String checkInDate, String checkOutDate,String adults, String children, String rateplan, String Salutation, 
			String NameOnCard, String PaymentType) throws InterruptedException, IOException {

		String testName = "VerifyGroupAccountWithTaxExempt";
		String test_description = "Verify Group Account With Tax Exempt.<br>";
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

		login(testName);

		Navigation nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
		RoomClass room_class = new RoomClass();
		Rate rate = new Rate();
		Policies Create_New_Policy = new Policies();
		Tax tax = new Tax();
		
CPReservationPage reservationPage = new CPReservationPage();
		
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
		}catch(Exception e)
		{
			
		}
		//// Room Class
		try {
			nav.Setup(driver);
			nav.RoomClass(driver);
			RoomClassName = (RoomClassName + Utility.getTimeStamp()).replaceAll("_", "");
			RoomClassAbb = (RoomClassAbb + Utility.getTimeStamp()).replaceAll("_", "");

			app_logs.info("try");
			nav.NewRoomClass(driver);

			getTest_Steps.clear();
			getTest_Steps = room_class.CreateRoomClass(driver, RoomClassName, RoomClassAbb, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			test_steps.add("Sccessfully Created New RoomClass " + RoomClassName + " Abb : " + RoomClassAbb);
			app_logs.info("Sccessfully Created New RoomClass" + RoomClassName + " Abb : " + RoomClassAbb);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create room class ", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create New Rate and Attach RoomClass
		try {

			nav.Inventory(driver);
			nav.Rates1(driver);
			RateName = (RateName + Utility.getTimeStamp()).replaceAll("_", "");
			String RateDisplayName = RateName;

			getTest_Steps = rate.CreateRate_Season(driver, RateName, maxAdults, maxPersons, BaseAmount, AddtionalAdult,
					AdditionalChild, RateDisplayName, RatePolicy, RateDescription, RoomClassName, AssociateSession,
					"Rack Rate", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Enter all require details and save");
			app_logs.info("Enter all require details and save");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			nav.Inventory(driver);
			test_steps.add("Navigated to Inventory");
			app_logs.info("Navigated to Inventory");
			Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
			Navigate.Menuitem_Policy_Text.click();
			Wait.explicit_wait_xpath(OR.Policy_Button, driver);
			test_steps.add("Navigated to Policies");
			app_logs.info("Navigated to Policies");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to navigate to Policies page", testName, "PoliciesNavigation", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to navigate to Policies page", testName, "PoliciesNavigation", driver);

			} else {
				Assert.assertTrue(false);
			}
		}
		// Create Deposit Policy

		try {

			DepositPolicyName = (DepositPolicyName + Utility.getTimeStamp()).replaceAll("_", "");
			getTest_Steps.clear();
			getTest_Steps = Create_New_Policy.NewPolicybutton(driver, DepositPolicytype, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			Create_New_Policy.Enter_Policy_Name(driver, DepositPolicyName);
			Create_New_Policy.Deposit_Policy_Attributes(driver, DepositAttribute1, NumAttribute1, getTest_Steps);
			Create_New_Policy.Enter_Policy_Desc(driver, PolicyText, PolicyDesc);
			Create_New_Policy.Associate_Sources(driver);
			Create_New_Policy.AssociateSingle_Seasons(driver, AssociateSession);
			Create_New_Policy.AssociateSingle_RoomClass(driver, RoomClassName);
			Create_New_Policy.AssociateSingle_RatePlan(driver, RackRate);
			Create_New_Policy.Save_Policy(driver);
			Create_New_Policy.Close_Policy_Tab(driver);

			test_steps.add("Successfully Created " + DepositPolicytype + "Policy : " + DepositPolicyName);
			app_logs.info("Successfully Created " + DepositPolicytype + "Policy : " + DepositPolicyName);
			Create_New_Policy.Verify_Policy(driver, DepositPolicyName);

			test_steps.add("Successfully Verify Policy " + DepositPolicyName);
			app_logs.info("Successfully Verify Policy " + DepositPolicyName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the Deposit policy", testName, "CreateDepositPolicy", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to save the  Deposit policy", testName, "CreateDepositPolicy", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Tax-1
		try {
			nav.Setup(driver);
			app_logs.info("Navigate To Setup");
			test_steps.add("Navigate To Setup");
			getTest_Steps.clear();
			getTest_Steps = tax.ClickNewTextItemButton(driver, getTest_Steps);
			test_steps.add(
					"Navigate To Tax For Creating New Tax Item With 'TaxExempt When Exclude' CheckBox Is Checked ");
			test_steps.addAll(getTest_Steps);
			app_logs.info("Click New Item Button");
			
			TaxName = (TaxName + Utility.getTimeStamp()).replaceAll("_", "");
			// displayName = taxName;
			getTest_Steps.clear();
			getTest_Steps = tax.createTaxExemptCheck(driver, test, TaxName, TaxName, Description, value, Category,
					LedgerAccount, true);
			test_steps.addAll(getTest_Steps);
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

		// Navigate to Groups
		try {
			nav.Reservation(driver);
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

		// Navigate to Folio Tab
		try {
			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
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

		try {
			getTest_Steps.clear();
			getTest_Steps = group.selectDepositPolicy(driver, DepositPolicyName);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolioOption(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyDepositPolicy(driver, DepositPolicyName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select policy", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Select policy", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomBlock

		try {
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

			String RoomBlocked = group.getRoomBlocked_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			test_steps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, RoomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNights_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			test_steps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, RoomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenue_RoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			test_steps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenue_GroupInfo(driver);
			Utility.app_logs.info("Before Group Info Expected Revenue  : " + expectedRevenueInfo);
			test_steps.add("Before Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			String pickUpPercentage = group.getPickUpPercentage_RoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			test_steps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);

			String blocked = group.getBlocked(driver, RoomClassName);
			assertEquals(Integer.parseInt(blocked), Integer.parseInt(RoomPerNight), "Failed Room Blocked Not Matched");

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

		String beforePickupValue = null;
		String beforeAvailableRoom = null;
		String beforeBookIconClass = null;
		try {
			beforePickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("Before Pickup Value : " + beforePickupValue);
			test_steps.add("Before Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("Before Available Rooms : " + beforeAvailableRoom);
			test_steps.add("Before Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("Before BookIcon Class : " + beforeBookIconClass);
			test_steps.add("Before BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = res.verifyTaxExempt(driver, true, TaxExmptId);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyTaxExamptFolioBalance(driver);
			test_steps.addAll(getTest_Steps);
			res.FolioTab(driver);
			double Total = 0;
			try {
				Total = res.get_FolioBalance(driver);
			} catch (Exception e) {
				Total = res.GetFolioBalance(driver);
			}
			
			String balance = res.get_FilioBalance(driver, test);
			
			assertEquals(Float.parseFloat(Total+""), Float.parseFloat(balance),"Failed to Match Folio Balance Amount and Balance Amount");
			
			Utility.app_logs.info("Successfully Verified Folio Balance and Balance Amount Matched : " + balance);
			test_steps.add("Successfully Verified Folio Balance and Balance Amount Matched : " + balance);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify TaxExampt", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify TaxExampt", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Utility.app_logs.info("Before Creation of Reservation :-");
			test_steps.add("Before Creation of Reservation :-"); 
			
			//res.FolioTab(driver);
			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges)>Float.parseFloat("0"),
					"Failed: Tax And Service Charges Should not Zero expected[0] but found ["+taxAndServiceCharges+"]");
			test_steps.add("Successfully Veified Service Charges not Zero : " + taxAndServiceCharges);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		Utility.app_logs.info("CP-Tax Exempt - creating res from group : CODE FIX.");
		test_steps.add("CP-Tax Exempt - creating res from group : CODE FIX."
				+"<br/><a href='https://innroad.atlassian.net/browse/NG-6431' target='_blank'>"
				+ "Please Verify : NG-6431 </a><br/>");
		
		Utility.app_logs.info("Taxes collected for deposit from the BOOK NOW for tax exempt reservations : Code Fix.");
		test_steps.add("Taxes collected for deposit from the BOOK NOW for tax exempt reservations : Code Fix."
				+"<br/><a href='https://innroad.atlassian.net/browse/NG-6518' target='_blank'>"
				+ "Please Verify : NG-6518 </a><br/>");
		
		// Create Reservation
		try {
			/*
			 * getTest_Steps.clear(); res.clickGuestInfo_1(driver, getTest_Steps);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear(); getTest_Steps =
			 * res.verifyAccountAttached(driver, AccountName);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			 * test_steps.addAll(getTest_Steps); getTest_Steps.clear();
			 * res.contactInformation(driver, "", AccountFirstName, AccountLastName,
			 * Address1, Address1, "", "", city, Country, State, Postalcode, Phonenumber,
			 * "", "", "", "", "", getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate,
			 * "Billing Notes");
			 * 
			 * getTest_Steps.clear(); res.RoomAssignedReservation(driver, true,
			 * getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * res.clickGuestInfo_1(driver);
			 * 
			 * getTest_Steps.clear();
			 */
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();

            reservationPage.select_CheckInDate(driver, test_steps, checkInDate);

            reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);

            reservationPage.enter_Adults(driver, test_steps, adults);

            reservationPage.enter_Children(driver, test_steps, children);

            reservationPage.select_Rateplan(driver, test_steps, rateplan,"");

            reservationPage.clickOnFindRooms(driver, test_steps);

            reservationPage.selectRoom(driver, test_steps, RoomClassName, "Yes","");

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
            reservationPage.getRoomNo_ResDetail(driver);
			
			res.UnCheckTaxExemptInReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.FolioTab(driver);

			// String bal = res.getStayInfoBalance(driver);
			//
			// test_steps.add("Stay Info Balance Amount : " + bal);
			// app_logs.info("Stay Info Balance Amount : " + bal);

			getTest_Steps.clear();
			getTest_Steps = res.SaveRes_CountinueDepositPolicy(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Saved  Reservation");
			app_logs.info("Successfully Saved Reservation");
			
			Utility.app_logs.info("Tax is not included in deposit amount for group block reservation when tax exempt is unchecked in reservation.");
			test_steps.add("Tax is not included in deposit amount for group block reservation when tax exempt is unchecked in reservation."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-6796' target='_blank'>"
					+ "Verified : NG-6796 </a><br/>");
			
			Utility.app_logs.info("Tax is not included in deposit for group reservation when tax exempt is unchecked.");
			test_steps.add("Tax is not included in deposit for group reservation when tax exempt is unchecked."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-7014' target='_blank'>"
					+ "Verified : NG-7014 </a><br/>");

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
			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-");
			
			//res.FolioTab(driver);
			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges)>Float.parseFloat("0"),
					"Failed: Tax And Servce Charges is not Zero");
			
			test_steps.add("Successfully verified Tax Applied : " + taxAndServiceCharges);
			Utility.app_logs.info("Successfully verified Tax Applied : " + taxAndServiceCharges);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			nav.Reservation(driver);
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			group.navigateRoomBlock(driver, test);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		String afterPickupValue = null;
		String afterAvailableRoom = null;
		String afterBookIconClass = null;
		try {
			afterPickupValue = group.getPickUpValue(driver, RoomClassName);
			Utility.app_logs.info("After Pickup Value : " + afterPickupValue);
			test_steps.add("After Pickup Value : " + afterPickupValue);

			afterAvailableRoom = group.getAvailableRooms(driver, RoomClassName);
			Utility.app_logs.info("After Available Rooms : " + afterAvailableRoom);
			test_steps.add("After Available Rooms : " + afterAvailableRoom);

			afterBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info("After BookIcon Class : " + afterBookIconClass);
			test_steps.add("After BookIcon Class : " + afterBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = res.verifyTaxExempt(driver, true, TaxExmptId);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.verifyTaxExamptFolioBalance(driver);
			test_steps.addAll(getTest_Steps);
			res.FolioTab(driver);
			double Total = 0;
			try {
				Total = res.get_FolioBalance(driver);
			} catch (Exception e) {
				Total = res.GetFolioBalance(driver);
			}
			
			String balance = res.get_FilioBalance(driver, test);
			
			assertEquals(Float.parseFloat(Total+""), Float.parseFloat(balance),"Failed to Match Folio Balance Amount and Balance Amount");
			
			Utility.app_logs.info("Successfully Verified Folio Balance and Balance Amount Matched : " + balance);
			test_steps.add("Successfully Verified Folio Balance and Balance Amount Matched : " + balance);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify TaxExampt", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify TaxExampt", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			Utility.app_logs.info("Before Creation of Reservation :-");
			test_steps.add("Before Creation of Reservation :-");
			
			//res.FolioTab(driver);
			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges)>Float.parseFloat("0"),
					"Failed: Tax And Service Charges Should not Zero expected[0] but found ["+taxAndServiceCharges+"]");
			test_steps.add("Successfully Veified Service Charges not Zero : " + taxAndServiceCharges);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}
		
		Utility.app_logs.info("Tax is collected for group block reservation created through yellow/red book icon.");
		test_steps.add("Tax is collected for group block reservation created through yellow/red book icon."
				+"<br/><a href='https://innroad.atlassian.net/browse/NG-6632' target='_blank'>"
				+ "Please Verify : NG-6632 </a><br/>");

		// Create Reservation
		try {
			getTest_Steps.clear();
			res.clickGuestInfo_1(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate, "Billing Notes");

			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, true, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.clickGuestInfo_1(driver);

			getTest_Steps.clear();
			res.UnCheckTaxExemptInReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.FolioTab(driver);

			// String bal = res.getStayInfoBalance(driver);
			//
			// test_steps.add("Stay Info Balance Amount : " + bal);
			// app_logs.info("Stay Info Balance Amount : " + bal);

			getTest_Steps.clear();
			getTest_Steps = res.SaveRes_CountinueDepositPolicy(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Saved  Reservation");
			app_logs.info("Successfully Saved Reservation");

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
			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-");
			
			//res.FolioTab(driver);
			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges)>Float.parseFloat("0"),
					"Failed: Tax And Servce Charges is not Zero");
			
			test_steps.add("Successfully verified Tax Applied : " + taxAndServiceCharges);
			Utility.app_logs.info("Successfully verified Tax Applied : " + taxAndServiceCharges);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Verify In TapChart", testName, "TapChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {
			nav.Reservation(driver);
			nav.Groups(driver);

			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.checkTaxExmpt(driver, false, TaxExmptId);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.save(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			group.navigateRoomBlock(driver, test);

			getTest_Steps.clear();
			getTest_Steps = group.clickRoomBlockEdit(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().frame("MainContent_Iframe_accountpicker");

			getTest_Steps.clear();
			getTest_Steps = group.setMaxBlock(driver, RoomClassName, RoomPerNight);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickSave_PreviewFolio(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickPreviewFolio_EditBlock(driver);
			test_steps.addAll(getTest_Steps);

			String qty = "1";
			String lineNo = "1";
			getTest_Steps.clear();
			getTest_Steps = group.addLineItem_PreviewFolio(driver, LineCategory, qty, LineAmount, lineNo);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickSave_PreviewFolio(driver);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.clickDone_PreviewFolio(driver);
			test_steps.addAll(getTest_Steps);

			driver.switchTo().defaultContent();

			beforeBookIconClass = group.getBookIconClass(driver, RoomClassName);
			Utility.app_logs.info(" BookIcon Class : " + beforeBookIconClass);
			test_steps.add(" BookIcon Class : " + beforeBookIconClass);

			getTest_Steps.clear();
			getTest_Steps = group.bookIconClick(driver, RoomClassName);
			test_steps.addAll(getTest_Steps);
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

		// before Creation of Reservation
		try {

			Utility.app_logs.info("Before Creation of Reservation :-");
			test_steps.add("Before Creation of Reservation :-");

			res.FolioTab(driver);
			Utility.app_logs.info("Folio Tab Clicked");
			test_steps.add("Folio Tab Clicked");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 2);
			test_steps.addAll(getTest_Steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {

			test_steps.add(e.toString());

		}

		try {

			
			getTest_Steps.clear();
			getTest_Steps = res.VerifyFolioLineItem(driver, "Room Charge", "250.00", "1");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.VerifyFolioLineItem(driver, LineCategory, LineAmount, "2");
			test_steps.addAll(getTest_Steps);

			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges) > Float.parseFloat("0"),
					"Failed: Tax And Servce Charges is Zero");

			test_steps.add("Successfully verified Tax Applied : " + taxAndServiceCharges);
			Utility.app_logs.info("Successfully verified Tax Applied : " + taxAndServiceCharges);
			
			Utility.app_logs.info("Tax is not added in group reservation.");
			test_steps.add("Tax is not added in group reservation."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-8065' target='_blank'>"
					+ "Please Verify : NG-8065 </a><br/>");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			getTest_Steps.clear();
			res.clickGuestInfo_1(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.marketingInfo(driver, test, MargetSegment, Referral, getTest_Steps);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			res.contactInformation(driver, "", AccountFirstName, AccountLastName, Address1, Address1, "", "", city,
					Country, State, Postalcode, Phonenumber, "", "", "", "", "", getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.billingInformation(driver, PaymentMethod, CardNumber, ExpiryDate, "Billing Notes");

			getTest_Steps.clear();
			res.RoomAssignedReservation(driver, true, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.clickGuestInfo_1(driver);

			getTest_Steps.clear();
			res.UnCheckTaxExemptInReservation(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			res.FolioTab(driver);

			getTest_Steps.clear();
			res.SaveRes_Updated(driver);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Saved  Reservation");
			app_logs.info("Successfully Saved Reservation");

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

			Utility.app_logs.info("After Creation of Reservation :-");
			test_steps.add("After Creation of Reservation :-"); 
			
			getTest_Steps.clear();
			getTest_Steps = res.verifyLineitemsExist(driver, true, 2);
			test_steps.addAll(getTest_Steps);

			
			getTest_Steps.clear();
			getTest_Steps = res.VerifyFolioLineItem(driver, "Room Charge", "250.00", "1");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = res.VerifyFolioLineItem(driver, LineCategory, LineAmount, "2");
			test_steps.addAll(getTest_Steps);

			String taxAndServiceCharges = res.get_Taxes(driver);
			assertTrue(Float.parseFloat(taxAndServiceCharges) > Float.parseFloat("0"),
					"Failed: Tax And Servce Charges is Zero");

			test_steps.add("Successfully verified Tax Applied : " + taxAndServiceCharges);
			Utility.app_logs.info("Successfully verified Tax Applied : " + taxAndServiceCharges);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Folio", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}


		// inActive Group
		try {
			nav.Reservation(driver);
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
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Search Account", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete rate
		try {
			try {
				nav.Inventory(driver);
				nav.Rates1(driver);
				rate.SearchRate(driver, RateName, false);

				test_steps.add("New Rate has been Searched successfully");
				app_logs.info("New Rate has been Searched successfully");

				rate.delete_rate_1(driver, RateName);

				test_steps.add("New Rate has been Deleted successfully");
				app_logs.info("New Rate has been Deleted successfully");
			} catch (Exception e) {
				System.out.println("Tried To Delete Rate");
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to Delete rate", testName, "Rates", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Delete Room Class
		try {
			try {
				nav.Setup(driver);
				nav.RoomClass(driver);

				try {
					room_class.SearchRoomClass(driver, RoomClassName, false);
					room_class.Delete_RoomClass(driver, RoomClassName);
				} catch (Exception b) {
					room_class.SelectItemsPerPage(driver);
					room_class.Search_Delete_RoomClass(driver, RoomClassName);
				}

				test_steps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} catch (Exception e) {
				System.out.println("Tried to Delete RoomClass");
			}

			try {
				nav.Inventory(driver);
				Policies policy = new Policies();
				test_steps.add("Navigated Inventory");
				app_logs.info("Navigated Inventory");
				Elements_On_All_Navigation Navigate = new Elements_On_All_Navigation(driver);
				Navigate.Menuitem_Policy_Text.click();
				test_steps.add("Navigated Policy");
				app_logs.info("Navigated Policy");
				policy.Delete_Policy(driver, DepositPolicyName);
				test_steps.add("Successfully Delete All Created Policies ");
				app_logs.info(" Successfully Delete All Created Policies ");
			} catch (Exception e) {
				System.out.println("Tried to Delete Policy");
			}
			
			try{
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
			}

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {

				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, test_steps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGroupAccountWithTaxExempt", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
