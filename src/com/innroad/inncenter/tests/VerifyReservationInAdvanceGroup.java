package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.ReservationFolio;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyReservationInAdvanceGroup extends TestCore {

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
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = { "groups" })
	public void verifyReservationInAdvanceGroup(String url, String clientcode, String username, String password,
			String AccountName, String MargetSegment, String Referral, String AccountFirstName, String AccountLastName,
			String Phonenumber, String Address1, String city, String Country, String State, String Postalcode,
			String RoomClassName, String PayAmount,String checkInDate, String checkOutDate,String adults, String children, String rateplan, String Salutation, String CardNumber, String NameOnCard, String PaymentType, String PayType) throws InterruptedException, IOException {

		String testName = "VerifyReservationInAdvanceGroup";
		String test_description = "Verify by clicking on Reservation link under reservation tab for advance groups.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554167' target='_blank'>"
				+ "Click here to open TestRail: C554167</a><br/>"
				+ "Verify by clicking on reservation link under folio from Advance Groups.<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/554168' target='_blank'>"
				+ "Click here to open TestRail: C554168</a><br/>";
		String test_catagory = "Group";
		TestName.add(testName);
		TestDescription.add(test_description);
		TestCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation Nav = new Navigation();
		Groups group = new Groups();
		Reservation res = new Reservation();
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
		
		// Login to InnCenter
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
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to login", testName, "Login", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
						Utility.updateReport(e, "Failed to login", testName, "Login", driver);
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

		// Create Reservation
		String resNo = null;
		try {
			getTest_Steps.clear();
			getTest_Steps = group.newReservation(driver);
			test_steps.addAll(getTest_Steps);
			getTest_Steps.clear();
			/*
			 * getTest_Steps = res.verifyAccountAttached(driver, AccountName);
			 * test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear(); getTest_Steps = res.verifyMarketingInfo(driver,
			 * MargetSegment, Referral); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear(); getTest_Steps = res.verifyContactInformation(driver,
			 * AccountFirstName, AccountLastName, Address1, city, Country, State,
			 * Postalcode, Phonenumber); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear(); res.ClickGuestProfileCheckBox(driver, true,
			 * getTest_Steps); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear(); getTest_Steps = res.contactInfoFirstLastName(driver,
			 * AccountFirstName, AccountLastName); test_steps.addAll(getTest_Steps);
			 * 
			 * getTest_Steps.clear(); res.RoomAssignedReservation(driver, test,
			 * RoomClassName, getTest_Steps); test_steps.addAll(getTest_Steps);
			 * getTest_Steps.clear(); res.SaveRes_Updated(driver);
			 * test_steps.addAll(getTest_Steps);
			 */
			String expiryDate=Utility.getFutureMonthAndYearForMasterCard();

            reservationPage.select_CheckInDate(driver, test_steps, checkInDate);

            reservationPage.select_CheckoutDate(driver, test_steps, checkOutDate);

            reservationPage.enter_Adults(driver, test_steps, adults);

            reservationPage.enter_Children(driver, test_steps, children);

            reservationPage.select_Rateplan(driver, test_steps, rateplan,"");

            reservationPage.clickOnFindRooms(driver, test_steps);

            reservationPage.selectRoom1(driver, test_steps, RoomClassName, "Yes","");

            reservationPage.clickNext(driver, test_steps);

            reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, Salutation, AccountFirstName, AccountLastName, config.getProperty("flagOff"));

            reservationPage.enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, expiryDate);

            reservationPage.enter_MarketSegmentDetails(driver, test_steps, "", MargetSegment, Referral);

            reservationPage.clickBookNow(driver, test_steps);

            resNo =reservationPage.get_ReservationConfirmationNumber(driver, test_steps);

            reservationPage.get_ReservationStatus(driver, test_steps);

            reservationPage.clickCloseReservationSavePopup(driver, test_steps);
			test_steps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");

			test_steps.add("Successfully Created Reservation : " + resNo);
			app_logs.info("Successfully Created Reservation : " + resNo);
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
			double Total = 0;
			try {
				reservationPage.clickFolio(driver, getTest_Steps);
				Total = res.get_FolioBalance(driver);
			} catch (Exception e) {
				Total = res.GetFolioBalance(driver);
			}

			ReservationFolio folio = new ReservationFolio();
			Folio foilo=new Folio();
			getTest_Steps.clear();
			//getTest_Steps = folio.TravelAccount(driver, AccountName, AccountNo, PayAmount);
			foilo.makePaymentAdvance(driver, PayType, PayAmount, "Account - "+AccountName);
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
			Utility.app_logs.info("Navigate to Group Folio");
			test_steps.add("Navigate to Group Folio");
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
			group.click_GroupsReservationTab(driver, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyReservationCount(driver, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyReservationInResTab(driver, resNo, 1);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.openResDialogFromResTab(driver, 1);
			test_steps.addAll(getTest_Steps);

			Wait.explicit_wait_visibilityof_webelement_600(driver.findElement(By.xpath("//*[@id='dialog-body0']")),
					driver);
			driver.switchTo().frame("dialog-body0");

			String dialogeResNo = reservationPage.getReservationNumberOnDetailSection(driver);
			test_steps.add("Successfully Verified Reservation Number in Res Tab Dialog " + resNo);
			Utility.app_logs.info("Successfully Verified Reservation Number in Res Tab Dialog " + resNo);
			assertEquals(dialogeResNo, resNo, "Failed Reservation Not Matched");

			Utility.app_logs.info("Unable to create reservation from reservations tab.");
			test_steps.add("Unable to create reservation from reservations tab."
					+"<br/><a href='https://innroad.atlassian.net/browse/NG-8066' target='_blank'>"
					+ "Verified : NG-8066 </a><br/>");
			
			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.closeDialoge(driver, "dialog-close0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			group.navigateFolio(driver, test, getTest_Steps);
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.verifyLineItems(driver, "Reservation", resNo, PayAmount, "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.openItemDetail_FolioLineItem(driver, "0");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.openResDialogFromFolioItemDetail(driver, resNo);
			test_steps.addAll(getTest_Steps);

			Wait.explicit_wait_visibilityof_webelement_600(driver.findElement(By.xpath("//*[@id='dialog-body1']")),
					driver);
			driver.switchTo().frame("dialog-body1");

			String dialogeResNo2 = reservationPage.getReservationNumberOnDetailSection(driver);
			test_steps.add("Successfully Verified Reservation Number in Folio Tab Dialog " + resNo);
			Utility.app_logs.info("Successfully Verified Reservation Number in Folio Tab Dialog " + resNo);
			assertEquals(dialogeResNo2, resNo, "Failed Reservation Not Matched");

			driver.switchTo().defaultContent();

			getTest_Steps.clear();
			getTest_Steps = group.closeDialoge(driver, "dialog-close1");
			test_steps.addAll(getTest_Steps);

			getTest_Steps.clear();
			getTest_Steps = group.closeDialoge(driver, "dialog-close0");
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

		// account inActive
		try {
			driver.navigate().refresh();
			Nav.Reservation(driver);
			Nav.Groups(driver);
			Utility.app_logs.info("Navigate to Group Folio");
			test_steps.add("Navigate to Group Folio");
			getTest_Steps.clear();
			getTest_Steps = group.Search_Account(driver, AccountName, AccountNo, true, true);
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
		return Utility.getData("VerifyReservationInAdvanceGroup", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
