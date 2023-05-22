package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGroupsInBookingEngine extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comments;
	String testName = "";
	Login login = new Login();
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	HashMap<String, String> groupDetails=new HashMap<>();
	Navigation navigation = new Navigation();
	BookingEngine bookingEngine = new BookingEngine();
	String accountNumber = "";
	String blockName = "";
	String accountName = "";
	String roomClassName = "";
	String maxAdults = "";
	String maxPersons = "";
	String timeZone = "";
	String AccountNo = "0";
	String Source="innRoad Booking Engine - autonbe.client.qainnroad.com";
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();

	// String
	// cases="36661043,36661440,36661016,36661629,36661625,36661102,36661097,36660715,36660903,36660904,36660905";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyGroupsInBookingEngine(String delim, String ratePlanName,
			String checkInDate, String checkOutDate, String adults, String child, String reservationRoomClassName, 
			String cases) throws Exception {
		
		String groupFirstName = "GrpFirst" + Utility.generateRandomStringWithGivenLength(5);
		String groupLastName = "Last" + Utility.generateRandomStringWithGivenLength(5);
		blockName = "Blk" + Utility.generateRandomStringWithGivenLength(5);
		String roomPerNight = "1";
		String firstName = "ResFirst" + Utility.generateRandomStringWithGivenLength(5);
		String lastName = "Last";
		String emailAddress = "innroadautomation@innroad.com";
		String phone = "1213445465";
		String address = "Add#1";
		String postalCode = "12345";
		String city = "New York";
		String state = "New York";
		String PaymentMethod="MC";
		String cardNumber = "5454545454545454";
		String cardName = "testName";
		String cardExpDate = "12/24";
		String cvv = "123";

		testSteps = new ArrayList<>();
		getTestSteps = new ArrayList<>();
		scriptName = new ArrayList<>();
		testCategory = new ArrayList<>();
		testDescription = new ArrayList<>();
		
		timeZone = "";
		String propertyName = "BEST SERVICE HOTEL";
		String dateFormat = "dd/MM/yyyy";
		//int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		//app_logs.info("days: " + days);

		Utility.DELIM = "\\" + delim;
		testName="";
		testName = "Group_Verification_In_BookingEngine";

		String test_description = "";
		String test_catagory = "";

		test_description = "Verify Groups in booking engine . <br>";
		test_catagory = "Group_Verification_In_BookingEngine";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		TaxesAndFee taxFees=new TaxesAndFee();
		Groups group = new Groups();
		CPReservationPage reservation = new CPReservationPage();
		Tax tax = new Tax();
		RatesGrid ratesGrid = new RatesGrid();
		Folio folio = new Folio();

		String randomNumber = Utility.GenerateRandomNumber(99);
		roomClassName = reservationRoomClassName;

		String marketSegment = "GDS";
		String referral = "Other";
		String PhoneNumber = "9876543210";
		String Address1 = "test1";
		String City = "test";
		String Country = "United States";
		String PostalCode = "12345";
		String State = "New York";

		String TaxName = "TestTax";
		String value = "10";
		String Category="Service Charge";
		String LedgerAccount="Room Charge";
		
		if (!(Utility.validateInput(checkInDate))) {
			checkInDate="";
			checkOutDate="";
		    for (int i = 0; i < groupFirstName.split("\\|").length; i++) {
		    	checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
		    	checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
		        if(i==0) {
		        	checkInDate=checkInDate+""+checkInDates.get(i);
		        	checkOutDate=checkOutDate+""+checkOutDates.get(i);
		        }else {
		        	checkInDate=checkInDate+"|"+checkInDates.get(i);
		        	checkOutDate=checkOutDate+"|"+checkOutDates.get(i);
		        }
		        }
		} else {
			checkInDates = Utility.splitInputData(checkInDate);
			checkOutDates = Utility.splitInputData(checkOutDate);
		}
		
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		app_logs.info("days: " + days);

		app_logs.info(checkInDate);
		app_logs.info(checkOutDate);

	

		try {
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Group(driver);

			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		HashMap<String, ArrayList<String>> getRatesAlongWithChildAndAdult = new HashMap<String, ArrayList<String>>();
		ArrayList<String> getRates = new ArrayList<String>();
		try {
			navigation.ratesGridInventory(driver);
			testSteps = ratesGrid.clickOnCalendarIcon(driver);
			Utility.selectDateFromDatePicker(driver, checkInDate, "dd/MM/yyyy");
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			app_logs.info("select rate plan: " + ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			testSteps.add("Select room class: " + roomClassName);
			ratesGrid.expandRoomClass1(driver, testSteps, roomClassName);
			ratesGrid.expandChannel(driver, testSteps, roomClassName, Source);
			getRatesAlongWithChildAndAdult = ratesGrid.getRateExAdExChForChannel(driver, roomClassName,Source, days);
			ratesGrid.clickMinusChannel(driver, roomClassName, Source);
			getRates.addAll(getRatesAlongWithChildAndAdult.get("rates"));
			ratesGrid.collapseRoomClass(driver, testSteps, roomClassName);
			app_logs.info("collapse roomClass");


		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates from rates grid", "ratesgrid", "ratesgrid", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Rates from rates grid", "ratesgrid", "ratesgrid", driver);

			}
		}
		
		// Create Tax
				try {
					testSteps.add("================Create Tax-1================");
					app_logs.info("=================Create Tax-1=================");
					navigation.navigateToSetupfromRateGrid(driver);
					app_logs.info("Navigate To Setup");
					testSteps.add("Navigate To Setup");
					getTestSteps.clear();
					tax.clickOnTaxButton(driver);
					testSteps.add("Navigate to taxes page");
					taxFees.deleteAllTaxesAndFee(driver, testSteps);
					tax.clickOnCreateTaxButton(driver);
					tax.createNewTax(driver, TaxName, TaxName, TaxName, value, Category, LedgerAccount, true, true, false, false, "", "", "");
					app_logs.info("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);
					testSteps.add("Successfully Created Tax-1 with 'TaxExempt When Exculde' Chekbox is checked : " + TaxName);
					Wait.wait5Second();
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Create Tax", testName, "CreateTax", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Create  Tax ", testName, "CreateTax", driver);
					} else {
						Assert.assertTrue(false);
					}
				}		
	
			// Clicking on Groups
			try {
				navigation.Reservation_Backward_3(driver);
				testSteps.add("Navigate Reservations");
				app_logs.info("Navigate Reservations");
				navigation.Groups(driver);
				// Create New Groups
				AccountNo = "0";
					testSteps.add("================Create New Group================");
					app_logs.info("=================Create New Group=================");
					accountName = groupFirstName + groupLastName;
					getTestSteps.clear();
					group.type_GroupName(driver, test, accountName, getTestSteps);
					testSteps.addAll(getTestSteps);

					AccountNo = Utility.GenerateRandomString15Digit();
					getTestSteps.clear();
					getTestSteps = group.enterAccountNo(driver, AccountNo);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					group.type_AccountAttributes(driver, test, marketSegment, referral, getTestSteps);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					group.type_MailingAddrtess(driver, test, groupFirstName, groupLastName, PhoneNumber, Address1, city,
							State, Country, PostalCode, getTestSteps);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					group.billinginfo(driver, test, getTestSteps);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					group.save(driver, test, getTestSteps);
					testSteps.addAll(getTestSteps);
					
					

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to Create Group", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			try {
				testSteps.add("=====================Create RoomBlock================");
				app_logs.info("=====================Create RoomBlock=================");
				group.navigateRoomBlock(driver, test);
				
				blockName = blockName + randomNumber;
				
				getTestSteps.clear();
				getTestSteps = group.createNewBlock(driver, blockName, roomPerNight, reservationRoomClassName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.clickCreateBlock(driver);
				testSteps.addAll(getTestSteps);

				group.save(driver, test, getTestSteps);
				testSteps.addAll(getTestSteps);
				group.navigateRoomBlock(driver, test);
				Utility.app_logs.info("Navigate to Room Block Tab");
				testSteps.add("Navigate to Room Block Tab");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Create Block", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
					try {
						logInBookingEngineConfigLink(driver, propertyName);
						comments="Verified Only when BE, have been deployed. ";
						bookingEngine.toggleGroupBookingsToggleButton(driver);

						//navigation.navigateToBookingEngine(driver);
						bookingEngine.clickWelcomePageLink(driver);
						testSteps.add("Opened the Booking Engine");
						app_logs.info("Opened the Booking Engine");

						getTestSteps.clear();
						getTestSteps = bookingEngine.clickOnAdvancedOption(driver);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = bookingEngine.clickOnHaveaGroupNumberLink(driver);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = bookingEngine.enterGroupNumberInBE(driver, AccountNo);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyGroupNumberIsShowingInBE(driver, AccountNo);
						testSteps.addAll(getTestSteps);
						
						getTestSteps.clear();
						getTestSteps = bookingEngine.selectRoomClass(driver, roomClassName);
						testSteps.addAll(getTestSteps);
						comments="Created a group and check the availability with the Group number. ";
							
					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}

					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}
					}
				
		
			try {
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
					testSteps.addAll(getTestSteps);
					comments="Verify skip this step in Addon Model page. ";

					testSteps.add("===== ENTER GUEST INFORMATION =====");
					app_logs.info("===== ENTER GUEST INFORMATION =====");

					getTestSteps.clear();
					getTestSteps = bookingEngine.enterGuestInfo(driver, firstName, lastName, emailAddress, phone,
							address, address, postalCode, city, state);
					testSteps.addAll(getTestSteps);

					testSteps.add("===== ENTER CARD DETAILS =====");
					app_logs.info("===== ENTER CARD DETAILS =====");

					getTestSteps.clear();
					getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
					testSteps.addAll(getTestSteps);
					double totalTax = 0.00;
					double rate = Double.parseDouble(getRates.get(0));
					double getPercentage = (Double.parseDouble(value)/ 100);
					double amount = rate*getPercentage;
					
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyTaxIsShowingOrNotInBE(driver,String.valueOf(amount),ratePlanName,"checkOutPage");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookStay(driver);
					testSteps.addAll(getTestSteps);
					comments="Checkbox Mandatory. ";

					String reservationNumber = bookingEngine.getReservationNumber(driver);
					comments="Created a group Reservation from the new booking engine. ";

					testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
					app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);
					
				
						testSteps.add("============ VERIFY CREATED RESERVATION DETAILS IN INNCENTER ===========");
						app_logs.info("===== VERIFY CREATED RESERVATION DETAILS IN INNCENTER =======");
						login_Group(driver);
						testSteps.add("Logged into the InnCenter ");
						app_logs.info("Logged into the InnCenter ");

						reservation.Search_ResNumber_And_Click(driver, reservationNumber);

						testSteps.add("Search Reservation : " + reservationNumber);
						app_logs.info("Search Reservation : " + reservationNumber);
						String actualSource=reservation.get_Reservationsource(driver, testSteps);
						Assert.assertEquals(actualSource, Source, "Failed to verify the source of reservation");
						comments="Verified the source for the reservation created from the new booking engine. ";
						
						String actualPayment=reservation.getPaymentMethodInExistingRes(driver);
						Assert.assertEquals(actualPayment, PaymentMethod, "Failed to verify the payment method for the reservation");
						comments="Verified Payment method for the reservations made from the new booking engine. ";
						folio.folioTab(driver);
						
						amount = Double.parseDouble(String.format("%.2f", amount));
						totalTax = totalTax + amount;
						
						String expectedTotalTax = String.format("%.2f", totalTax);
						String getTax = folio.getLineTaxAmount(driver, "Room Charge", 1);
						testSteps.add("Expected tax in folio: " + expectedTotalTax);
						testSteps.add("Found: " + getTax);
						Assert.assertEquals(amount, totalTax, "Failed: tax mismatching in folio!");
						testSteps.add("Verified tax in folio");
						comments="Verified how tax is showing in inncenter and try to book a reservation.";
						
						reservation.clickCheckInButton(driver, testSteps);
						reservation.generatGuestReportToggle(driver, testSteps, "No");
						reservation.completeInHouse(driver, testSteps);
						reservation.departedReservation2(driver,testSteps);
						
						
						
					} catch (Exception e) {
						e.printStackTrace();
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to search reservation", testName, "RoomClass", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to search reservation", testName, "RoomClass", driver);
						}
					}
		
		// Generate Report
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
				
	}
	public String logInBookingEngineConfigLink(WebDriver driver, String propertyName) throws InterruptedException {
		
		String getUrl = "";
		try {
			
			login_Group(driver);

			navigation.navSetup(driver);
			testSteps.add("Navigate Setup");
			app_logs.info("Navigate Setup");
			navigation.Properties(driver);
			testSteps.add("Navigate Properties");
			app_logs.info("Navigate Properties");
			navigation.openProperty(driver, testSteps, propertyName);
			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);

			getUrl = bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(0));
			driver.close();
			driver.switchTo().window(tabs2.get(1));

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickOnSettingsLink(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}
		
		return getUrl;
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("verifyGroupsInBookingEngine", BEExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void updateTestRailLink() throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,testName,TestCore.TestRail_AssignToID);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		// driver.close();
	}

}
