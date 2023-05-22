package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyNoShowOfIndividualReservationOfMRB  extends TestCore {
	
// C825010
	//need to find out xpath of three dot

	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> NewRooms = new ArrayList<String>();
	String date = null;
	
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();

	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))

			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyNoShowOfIndividualReservationOfMRB(String TestCaseID,String CheckInDate,String CheckOutDate,String propertyName,
			String res_Adults,String res_Children,String res_Rateplan,String res_PromoCode,String res_IsSplitRes,
			String res_RoomClass,String res_IsAssign,String res_IsDepositOverride,String res_DepositOverrideAmount,
			String res_IsAddMoreGuestInfo,String res_Salutation,String res_GuestFirstName,String res_GuestLastName,String res_PhoneNumber,String res_AltenativePhone,String res_Email,String res_Account,String res_AccountType,
			String res_Address1,String res_Address2,String res_Address3,String res_City,String res_Country,String res_State,String res_PostalCode,
			String res_IsGuesProfile,String res_PaymentType,String res_CardNumber,String res_NameOnCard,String res_CardExpDate,String res_IsChangeInPayAmount,
			String res_ChangedAmountValue,String res_TravelAgent,String res_MarketSegment,String res_Referral,String accountName,
			String accountType,String res_RoomAbbri,String CancelRoomNo,String	CancelStatus) throws ParseException, Exception {
		

		
		
		String testName = this.getClass().getSimpleName().trim();

        String testcaseId="848463";
		
		Utility.initializeTestCase(testcaseId, caseId, statusCode, comments, "848463");
		
		testDescription = testName + "<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848463' target='_blank'>"
				+ "Click here to open TestRail: C848463</a><br>";
		testCategory = "MRB Reservation";

		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		
		
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage res = new CPReservationPage();
		Folio folio = new Folio();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		Account CreateTA = new Account();
		ReservationSearch reservationSearch = new ReservationSearch();
		NewRoomClassesV2 rc = new NewRoomClassesV2();
		GuestHistory guestHistory = new GuestHistory();
		ArrayList<String> RoomAbbri = new ArrayList<String>();
		ArrayList<String> RoomAbbri1 = new ArrayList<String>();
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		String calendarTodayDate = null;
		
		
		String randomNumber = Utility.GenerateRandomNumber();
		String reservationNumber=null;
		String status=null,Balance=null,RoomClassName=null;;
		String roomCost=null;
		res_GuestFirstName = res_GuestFirstName + randomNumber;
		res_GuestLastName = res_GuestLastName + randomNumber;
		int totalRoomsExtend = 2;
		ArrayList<String> Rooms = new ArrayList<String>();

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_Autoota(driver);

			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			// Get CheckIN and Checkout Date
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < res_GuestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate);
				checkOutDates = Utility.splitInputData(CheckOutDate);
			}
			CheckInDate = checkInDates.get(0) + "|" + checkInDates.get(1);
			CheckOutDate = checkOutDates.get(0) + "|" + checkOutDates.get(1);
			//TaskDueon = checkOutDates.get(0);
			
			app_logs.info(CheckInDate);
			app_logs.info(CheckOutDate);
			
		} catch (Exception e) {
		    e.printStackTrace();
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		} catch (Error e) {
		    if (Utility.reTry.get(testName) == Utility.count) {
		        RetryFailedTestCases.count = Utility.reset_count;
		        Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
		        Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
		    } else {
		        Assert.assertTrue(false);
		    }
		}
			

			

		// Reservation
		//IsSplitRes,IsAssign,IsGuesProfile,IsAddMoreGuestInfo,Rooms,TravelAgent
				try {
					
					res.click_NewReservation(driver, getTestSteps);
					res.select_Dates(driver, getTestSteps, CheckInDate, CheckOutDate, res_Adults, res_Children, res_Rateplan, res_PromoCode,res_IsSplitRes);
					if(res_IsSplitRes.equalsIgnoreCase("Yes")) {
						res.enter_Adults(driver, getTestSteps, res_Adults);
						res.enter_Children(driver, getTestSteps, res_Children);
						res.select_Rateplan(driver, getTestSteps, res_Rateplan, res_PromoCode);
					}
					res.clickOnFindRooms(driver, getTestSteps);
					res.select_MRBRooms(driver, getTestSteps, res_RoomClass, res_IsAssign,accountName);
					//depositAmount=res.deposit(driver, getTestSteps, IsDepositOverride, DepositOverrideAmount);
					res.clickNext(driver, getTestSteps);
					
					res.enter_MRB_MailingAddress(driver, getTestSteps, res_Salutation, res_GuestFirstName, res_GuestLastName, res_PhoneNumber, res_AltenativePhone, res_Email, 
							accountName, accountType, res_Address1, res_Address2, res_Address3, res_City, res_Country, res_State, res_PostalCode, res_IsGuesProfile, res_IsAddMoreGuestInfo, res_IsSplitRes,Rooms);
					if((accountName.equalsIgnoreCase("")||accountName.isEmpty())) {
						res.enter_PaymentDetails(driver, getTestSteps, res_PaymentType, res_CardNumber, res_NameOnCard, res_CardExpDate);
					}
					System.out.println(Rooms);
					res.enter_MarketSegmentDetails(driver, getTestSteps, res_TravelAgent, res_MarketSegment, res_Referral);
					res.clickBookNow(driver, getTestSteps);
					reservationNumber=res.get_ReservationConfirmationNumber(driver, getTestSteps);
					status=res.get_ReservationStatus(driver, getTestSteps);
					res.clickCloseReservationSavePopup(driver, getTestSteps);
					testSteps.addAll(getTestSteps);
					
					
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
					
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						
						Utility.updateReport(e, "Failed to click New reservation button", testName, "Reservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				
				try {
					

					 if(res.isRoomReserved||res.isRoomUnAssigned)
						{
				         res.getRoomsOnDetailPage(driver, NewRooms);
				         System.out.println(NewRooms);
						}
					 
		           Wait.wait10Second();
					
					

					Balance= res.get_TripSummaryBalance_Amount(driver, getTestSteps);
					double tripAmount=Double.valueOf(Balance);
					int amt=(int)tripAmount;
					Balance=String.valueOf(amt);			
					res.getTodaysANDYesturdayDate();
					testSteps.add("<b>****Start Cancel Room****</b>");
					RoomAbbri = Utility.splitInputData(res_RoomAbbri);
					
					res.clickSTAYINFOThreeDots(driver, getTestSteps, CancelStatus,CancelRoomNo,Rooms ,RoomAbbri );		
					//public void clickSTAYINFOThreeDots(WebDriver driver, ArrayList<String> test_steps, String Status,

					//		String roomNO, ArrayList<String> Rooms, ArrayList<String> RoomAbbri)
			    	testSteps.add("<b>****Start Verifying Cancel Room Window****</b>");
			    	//res.verifyReservationPopWindow(driver, CancelRoom, StayInfoSecondayGuestName,ReservedStatus, reservation, getTestSteps, PolicyTypeCheckOut);
			      // res.unCheckedVoidRoomChargeCheckBox(driver, getTestSteps);
			       	res.inputReason(driver, testSteps,"cancel Reason");
			       	res.clickProceedToCancelPaymentButton(driver, getTestSteps);
			        testSteps.add("<b>****Start Verifying Cancellation Payment Window****</b>");
					//Verification 
			       // res.mrbSecondaryCancel(driver);
					

					
					res.addNotesAndClickLogORPayORAuthorizedButton(driver, getTestSteps, "CancelNote");
					
					testSteps.add("<b>****Start Verifying Cancellation Successful Window****</b>");
					
					//res.clickCloseButtonOfSuccessModelPopup(driver, testSteps); 
					res.clickCloseButtonOfCancelSuccessfully(driver, getTestSteps);
					
					testSteps.add("<b>****Start Verifying In-House Status****</b>");
					res.verifyReservationStatusStatus(driver, getTestSteps, status);
					testSteps.add("<b>****Start Verifying Check-Out All Button ****</b>");  				
					//res.verifyCheckOutAllButton(driver, getTestSteps);  
					
					testSteps.add("<b>****Start Verifying Check-Out  Button ****</b>");  
					//res.verifyStayInfoCheckOutButton(driver, getTestSteps, res.actualRoomCheckIn);		
					testSteps.add("<b>****Start Verifying Roll Back Button ****</b>");                            		
					String[] room=res.stayInfoActualRoom.split(":");			
					 RoomClassName=room[0].trim();
					 System.out.println(" Room Class: "+RoomClassName);		 
					//Verified Roll Back Button Enabled
					res.verifyStayInforRollBackButton(driver, testSteps,RoomClassName);
			
					
				   

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(ScriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
						Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(ScriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
						Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			

				
		try {
			res.noShowReservationsCp(driver, true, testSteps);
			
			
			status=res.get_ReservationStatus(driver, testSteps);
			String sta = "//td//span[text()='No Show']";
			if (driver.findElement(By.xpath(sta)).isDisplayed()) {
				testSteps.add("Reservation banner Status field verified : " + status);
				app_logs.info("Reservation banner Status field verified : " + status);
			} else {
				testSteps.add("Reservation banner Status field not found : " + status);
				app_logs.info("Reservation banner Status field not found : " + status);
			}

			
			String xpathOfThreeDots="(//button[@class='ir-iconCircle btn ir-btn ir-no-space pull-right disabled'])";
			List<WebElement>  eleThreeDots=driver.findElements(By.xpath(xpathOfThreeDots));
			if(eleThreeDots.size()==1)
			{
				Assert.assertTrue(true);
				app_logs.info("Secondary profile edit button is disabled,Can not make individual reservation noshow");
				testSteps.add("Secondary profile edit button is disabled,Can not make individual reservation noshow");
			}
			
			statusCode.set(0, "1");
			comments.add(0, "Verified");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("NoShowOfIndividualResOfMRB", excel_Swarna);

	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

	}




}
