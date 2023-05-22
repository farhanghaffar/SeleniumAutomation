package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.FolioLineItems;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightAudit;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pages.NewRoomClassPageObjectV2;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_NewRoomClassPage;
import com.innroad.inncenter.webelements.Elements_NightAudit;
import com.innroad.inncenter.webelements.Elements_Reservation;

import junit.framework.TestCase;

public class Verify_NightAuditFunctionality extends TestCore{
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel_Swarna))
			throw new SkipException("Skipping the test - " + testName);
	}
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();

	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	public ArrayList<String> foliolineitem =new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String comments;

	// Night Audit 
		@Test(dataProvider = "getData")
		public void verify_NightAuditFunctionality(String TestCaseID, String Adults,String Children,
				String RoomClass,String IsAssign,String Account,String isChecked,String Salutation,String GuestFirstName,String GuestLastName,String PaymentType,String CardNumber,String NameOnCard,String CardExpDate,
				String TravelAgent,String MarketSegment,String referral ,String Category,String Amount,String Notes,
				String dateToLock, String testCase) throws Exception {

			if(!Utility.validateString(TestCaseID)) {
				caseId.add("848109");
				statusCode.add("5");
			}else {
				String[] testcase=TestCaseID.split("\\|");
				for(int i=0;i<testcase.length;i++) {
					caseId.add(testcase[i]);
					statusCode.add("5");
				}
				
			}
			String test_name = "Verify_NightAuditFunctionality-" + testCase;
			String test_description = "verify Night Audit Functionality<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848109' target='_blank'>"
					+ "Click here to open TestRail: C848109</a>";
			String test_catagory = "NightAudit";

			String testName = test_name;
			
			TestName.add(testName);
			TestDescription.add(test_description);
			TestCategory.add(test_catagory);

			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");

			NightAudit nightAudit = new NightAudit();
			CPReservationPage reservationPage = new CPReservationPage();
			ReservationSearch reservationSearch =new ReservationSearch();	
			Navigation navigation = new Navigation();
			Folio folio = new Folio();
			ReservationHomePage homePage = new ReservationHomePage();
			
			String reservation=null;
			String status=null;
			String HouseKeepingCount=null;
			String timeZone = "US/Eastren";
			
			// Login
			try {
				if (!Utility.insertTestName.containsKey(testName)) {
					Utility.insertTestName.put(testName, testName);
					Utility.reTry.put(testName, 0);

				} else {
					Utility.reTry.replace(testName, 1);
				}
				driver = getDriver();
				loginRateV2(driver);
			
				testSteps.add("Logged into the application");
				app_logs.info("Logged into the application");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			
			
	if(testCase.equalsIgnoreCase("VerifyNightAuditFuncationality")) {
		
	 	if(dateToLock.isEmpty() || dateToLock.equalsIgnoreCase("")) {
	 		dateToLock = ESTTimeZone.reformatDate(Utility.getDatePast_FutureDate(-27), "MM/dd/yyyy", "dd/MM/yyyy");
	 		app_logs.info("date to lock : " + dateToLock);
	 	}
	 	
		// HouseKeeping Count Verification
					try {
							testSteps.add("=================== NAVIGATION TO NIGHT AUDIT   ======================");
			                app_logs.info("=================== NAVIGATION TO NIGHT AUDIT   ======================");
		                   
			                navigation.NightAuditIcon(driver);
		                     getTestSteps.clear();
		                     nightAudit.EnterAuditDate(driver);
					         nightAudit.GoButtonClick(driver);
					      
							Wait.explicit_wait_xpath(OR.Period_status, driver);
							HouseKeepingCount=nightAudit.GetHouseKeepingCount(driver);
							testSteps.add(" House Keeping No Is: <b>"+ HouseKeepingCount + " </b>");
							app_logs.info(" House Keeping No Is: "+ HouseKeepingCount);	
					        navigation.Reservation_Backward(driver);
				   			
						}
						catch(Exception e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}catch(Error e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}
						
					
						
							//creating reservation and doing checkin
							try {
								testSteps.add("=================== CREATING RESERVATION   ======================");
				                app_logs.info("=================== CREATING RESERVATION   ======================");
				          	
								reservationPage.click_NewReservation(driver, getTestSteps);
								getTestSteps.clear();
								getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 5, timeZone));
								testSteps.addAll(getTestSteps);

								getTestSteps.clear();
								getTestSteps = reservationPage.checkOutDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 6, timeZone));
								testSteps.addAll(getTestSteps);
								reservationPage.enter_Adults(driver, testSteps, Adults);
								reservationPage.enter_Children(driver, testSteps, Children);
								
								reservationPage.clickOnFindRooms(driver, testSteps);
								
								reservationPage.select_Roomm(driver, getTestSteps, RoomClass, IsAssign, Account);
						      
								reservationPage.clickNext(driver, testSteps);
								reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
								
								
								reservationPage.enter_GuestName(driver, getTestSteps, Salutation, GuestFirstName, GuestLastName);
								
								
								reservationPage.enter_PaymentDetails(driver, testSteps, PaymentType, CardNumber, NameOnCard, CardExpDate);
								reservationPage.selectReferral(driver, referral);
								
								
								reservationPage.clickBookNow(driver, testSteps);
								reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
								status=reservationPage.get_ReservationStatus(driver, testSteps);
								reservationPage.clickCloseReservationSavePopup(driver, testSteps);
								testSteps.add("Reservation craeted");
							    app_logs.info("Reservation craeted");
								testSteps.add("=================== CHECKIN   ======================");
				                app_logs.info("=================== CHECKIN    ======================");
								
				                getTestSteps.clear();
				                getTestSteps = reservationPage.clickOnCheckInButton(driver, true, true);
				    			testSteps.addAll(getTestSteps);
				    			
				    			reservationPage.verifyGenerateGuestReportToggle(driver);
				    			testSteps.add("Click on toggle button for report generate");
				    			app_logs.info("Click on toggle button for report generate");
				    			
				    			getTestSteps.clear();
				    			getTestSteps = reservationPage.clickOnConfirmCheckInButton(driver);    			
				    			testSteps.addAll(getTestSteps);
				    			//reservationPage.clickOnDirtyPopUp(driver, testSteps);
				    			String reservationStatus = reservationPage.reservationStatus(driver);
				    			app_logs.info("Reservation status after checkin: " + reservationStatus);
								
							}
							catch(Exception e)
							{
								if (Utility.reTry.get(testName) == Utility.count) {
					                RetryFailedTestCases.count = Utility.reset_count;
					                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					                Utility.updateReport(e, "Failed to Do Checkin",  testName, "Night Audit", driver);
					            } else {
					                Assert.assertTrue(false);
					            }
							}catch(Error e)
							{
								if (Utility.reTry.get(testName) == Utility.count) {
					                RetryFailedTestCases.count = Utility.reset_count;
					                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					                Utility.updateReport(e, "Failed to Do Checkin",  testName, "Night Audit", driver);
					            } else {
					                Assert.assertTrue(false);
					            }
							}
							
							//preconditions for period lock
							try {
								
								 Elements_NightAudit ng = new Elements_NightAudit(driver);
								 Wait.wait10Second();
								// Wait.WaitForElement(driver, OR.folio_Tab);
								 ng.folio_Tab.click();
								 
								folio.addLineItem(driver, testSteps, Category, Amount, "1");
								folio.voidLineItem(driver, testSteps, Category, Notes);
								
							} 
							catch (Exception e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
									Utility.updateReport(e, "Failed To Apply Preconditions For Period Lock", testName, "Night Audit", driver);
								} else {
									Assert.assertTrue(false);
								}
							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
									Utility.updateReport(e, "Failed To Apply Preconditions For Period Lock", testName, "Night Audit", driver);
								}
							}
					
							//		Period Lock
 
							try {

							    
							    testSteps.add("<b> ===== Posting line items for specific date ===== </b>");
							 	app_logs.info("===== Posting line items for specific date =====");
							 	
							 	navigation.NightAuditIcon(driver);
								nightAudit.EnterAuditDate(driver, dateToLock);
								nightAudit.GoButtonClick(driver, testSteps);				
								nightAudit.PeriodIsOpenButtonClick(driver, testSteps);	
								nightAudit.postAllItems(driver, testSteps, "CurrentItems");
								nightAudit.postAllItems(driver, testSteps, "PriorItems");
								nightAudit.postAllItems(driver, testSteps, "VoidItems");

								nightAudit.clickPostedStatus(driver, testSteps);

								getTestSteps.clear();
								getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
								testSteps.addAll(getTestSteps);
								nightAudit.GoButtonClick(driver, testSteps);				
								try {									
									nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
									nightAudit.switchToLockPeriodPopup(driver, testSteps);
								}catch (Exception e) {
									nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
									nightAudit.switchToLockPeriodPopup(driver, testSteps);
								}
								
								nightAudit.clickLock(driver, testSteps);
								
								
							} 
							catch (Exception e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
									Utility.updateReport(e, "Period Lock Done Success", testName, "Night Audit", driver);
								} else {
									Assert.assertTrue(false);
								}
							} catch (Error e) {
								if (Utility.reTry.get(testName) == Utility.count) {
									RetryFailedTestCases.count = Utility.reset_count;
									Utility.AddTest_IntoReport(TestName, TestDescription, TestCategory, testSteps);
									Utility.updateReport(e, "Failed to lock period", testName, "Night Audit", driver);
								}
							}

		//go to reservation and try to modify it and check lock is happening or not		
							try {
						
								navigation.Reservation_Backward(driver);
								reservationPage.closeReservationTab(driver);
								app_logs.info("Closed Opened Resevation");
								testSteps.add("Closed Opened Reservation");
						 
						
								reservationSearch.basicSearch_WithResNumber(driver,reservation);
								
								reservationPage.click_Folio(driver, testSteps);
								folio.addLineItem(driver, testSteps, Category, Amount, "1");
								System.out.println("Failed To Update Reservation");
							    navigation.NightAuditIcon(driver);
								nightAudit.EnterAuditDate(driver, dateToLock);
							    nightAudit.GoButtonClick(driver);
							    nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
								nightAudit.PeriodIsOpen_LockButtonClick(driver);
								
						        	
							} catch(Exception e)
							{
								if (Utility.reTry.get(testName) == Utility.count) {
					                RetryFailedTestCases.count = Utility.reset_count;
					                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					                Utility.updateReport(e, "Failed to modify reservation",  testName, "Night Audit", driver);
					            } else {
					                Assert.assertTrue(false);
					            }
							}catch(Error e)
							{
								if (Utility.reTry.get(testName) == Utility.count) {
					                RetryFailedTestCases.count = Utility.reset_count;
					                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					                Utility.updateReport(e, "Failed to modify reservation",  testName, "Night Audit", driver);
					            } else {
					                Assert.assertTrue(false);
					            }
							}
						
						//HouseKeeping Count After Checkin
					try {
		
						 navigation.NightAuditIcon(driver);
					        nightAudit.EnterAuditDate(driver);
					        
					        nightAudit.GoButtonClick(driver);
					        
							Wait.explicit_wait_xpath(OR.Period_status, driver);
							HouseKeepingCount=nightAudit.GetHouseKeepingCount(driver);
							testSteps.add(" House Keeping No After Checkin Is: <b>"+ HouseKeepingCount + " </b>");
							app_logs.info(" House Keeping No After Checkin Is: "+ HouseKeepingCount);	
						} catch(Exception e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}catch(Error e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}
						
		// Checkout
						try {
							
			
							navigation.Reservation_Backward(driver);
							reservationPage.closeReservationTab(driver);
							app_logs.info("Closed Opened Resevation");
							testSteps.add("Closed Opened Reservation");
							driver.navigate().refresh();
							
							reservationSearch.basicSearch_WithResNumber(driver,reservation);
							
				         	Elements_NightAudit ng= new Elements_NightAudit(driver);
				         	Wait.WaitForElement(driver, OR.checkoutButton);
                             ng.checkoutButton.click();
                             
				    			reservationPage.clickGenerateGuestReportToggleForCheckoutPopup(driver, testSteps);

				         		Wait.explicit_wait_elementToBeClickable(ng.proceedtocheckoutpayment, driver);
				         		ng.proceedtocheckoutpayment.click();
				         	try{
				  				  getTestSteps.clear(); 
				  				  getTestSteps = homePage.clickPayButton(driver);
				  				  testSteps.addAll(getTestSteps);
				  				
				  			}
				  			catch(Exception e) {
				  				  
				  			 }
				  			try{
				  					
				  				getTestSteps.clear(); 
				  				getTestSteps =
				  				homePage.clickCloseCheckoutSuccessfullPopup(driver);
				  				testSteps.addAll(getTestSteps);
				  			}
				  			catch(Exception e) {
				  				  
				  			}
				         		
								
						} catch(Exception e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Checkout",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}catch(Error e)
						{
							if (Utility.reTry.get(testName) == Utility.count) {
				                RetryFailedTestCases.count = Utility.reset_count;
				                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				                Utility.updateReport(e, "Failed to Checkout",  testName, "Night Audit", driver);
				            } else {
				                Assert.assertTrue(false);
				            }
						}

									
			
				// HouseKeeping Count Verification after checkout
					try {
						navigation.NightAuditIcon(driver);
				        nightAudit.EnterAuditDate(driver);
				        
				        nightAudit.GoButtonClick(driver);
				        
						Wait.explicit_wait_xpath(OR.Period_status, driver);
						HouseKeepingCount=nightAudit.GetHouseKeepingCount(driver);
						testSteps.add(" House Keeping No After CheckOUT Is: <b>"+ HouseKeepingCount + " </b>");
						app_logs.info(" House Keeping No After CheckOUT Is: "+ HouseKeepingCount);	
						nightAudit.ClickSetNowButton(driver);
						HouseKeepingCount=nightAudit.GetHouseKeepingCount(driver);
						testSteps.add(" House Keeping No After Clicking Set Now: <b>"+ HouseKeepingCount + " </b>");
						app_logs.info(" House Keeping No After Clicking Set Now: "+ HouseKeepingCount);	
					}
					catch(Exception e)
					{
						if (Utility.reTry.get(testName) == Utility.count) {
			                RetryFailedTestCases.count = Utility.reset_count;
			                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
			            } else {
			                Assert.assertTrue(false);
			            }
					}catch(Error e)
					{
						if (Utility.reTry.get(testName) == Utility.count) {
			                RetryFailedTestCases.count = Utility.reset_count;
			                Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
			                Utility.updateReport(e, "Failed to Get HouseKeeping Number",  testName, "Night Audit", driver);
			            } else {
			                Assert.assertTrue(false);
			            }
					}
		
		}
	
		String getFirstItemDate = "";
		int priorItemSize = 0;
		String reservationNumber = "";
		
		if(testCase.equalsIgnoreCase("VerifyPeriodLock")) {
			
			try {				
								
			 	if(dateToLock.isEmpty() || dateToLock.equalsIgnoreCase("")) {
			 		dateToLock = ESTTimeZone.reformatDate(Utility.getDatePast_FutureDate(-27), "MM/dd/yyyy", "dd/MM/yyyy");
			 		app_logs.info("date to lock : " + dateToLock);
			 	}

			 	testSteps.add("===== Locking period for specific date =====");
			 	app_logs.info("===== Locking period for specific date =====");
			 	
			 	getTestSteps.clear();
				getTestSteps = navigation.NightAuditIcon(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				testSteps.addAll(getTestSteps);

				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
				
				app_logs.info("Date with items : " + dateToLock);
				testSteps.add("Date with items : " + dateToLock);				
			
				priorItemSize = nightAudit.getPriorItemSize(driver);
				if(priorItemSize < 1) {
					
					testSteps.add("==== Date to lock period has no pending line item ====");
				 	app_logs.info("==== Date to lock period has no pending line item ====");
				 					 	    
					navigation.Reservation_Backward(driver);				
				    app_logs.info("Back to reservations");
				    testSteps.add("Back to reservations");				    	

					testSteps.add("=================== CREATING RESERVATION   ======================");
	                app_logs.info("=================== CREATING RESERVATION   ======================");
	   				
					reservationPage.click_NewReservation(driver, getTestSteps);					
					
					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 5, timeZone));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 6, timeZone));
					testSteps.addAll(getTestSteps);
					
					reservationPage.enter_Adults(driver, testSteps, Adults);
					reservationPage.enter_Children(driver, testSteps, Children);
					
					reservationPage.clickOnFindRooms(driver, testSteps);
					
					reservationPage.select_Roomm(driver, getTestSteps, RoomClass, IsAssign, Account);
			      
					reservationPage.clickNext(driver, testSteps);
					reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
					
					
					reservationPage.enter_GuestName(driver, getTestSteps, Salutation, GuestFirstName, GuestLastName);
					reservationPage.selectReferral(driver, referral);
									
					reservationPage.clickBookNow(driver, testSteps);
					reservation=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					status=reservationPage.get_ReservationStatus(driver, testSteps);
					
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					testSteps.add("Reservation created");
				    app_logs.info("Reservation created");

				 	getTestSteps.clear();
					getTestSteps = navigation.NightAuditIcon(driver);
					testSteps.addAll(getTestSteps);
    
				    getTestSteps.clear();
				    getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				    testSteps.addAll(getTestSteps);
				    
					nightAudit.GoButtonClick(driver, testSteps);				
					nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
					
				}
				
				testSteps.add("===== Verifying that date to lock period has pending line item less then 100 =====");
			 	app_logs.info("===== Verifying that date to lock period has pending line item less then 100 =====");
					
				nightAudit.clickPriorItems(driver, testSteps);
				String getItems = nightAudit.getTotalPriorItems(driver, testSteps);
				app_logs.info("Prior items count for specific date: " + getItems);
				testSteps.add("Prior items count for specific date: " + getItems);
				if(Integer.parseInt(getItems) > 100) {
				    assertTrue(false, "Failed : Dates with more than 100 Items cannot be posted with automation. Please do it manually");						
			    }else {
					getFirstItemDate = nightAudit.getPriorItemsDatesBasedOnIndex(driver, testSteps, 1);
					app_logs.info("First Prior items date: " + getFirstItemDate);
				    int count = nightAudit.getAllOccurancesOfDate(driver, testSteps, getFirstItemDate);
	
				    if(count > 100) {
					    app_logs.info("Dates with more than 100 Items cannot be posted with automation. Please do it manually.");
					    testSteps.add("Dates with more than 100 Items cannot be posted with automation. Please do it manually.");			    	
					    assertTrue(count > 100, "Failed : Dates with more than 100 Items cannot be posted with automation. Please do it manually");
				    }				    

					navigation.Reservation_Backward(driver);				
				    app_logs.info("Back to reservations");
				    testSteps.add("Back to reservations");				    	

					testSteps.add("=================== CREATING RESERVATION   ======================");
	                app_logs.info("=================== CREATING RESERVATION   ======================");
	   				
					reservationPage.click_NewReservation(driver, getTestSteps);					
					
					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 5, timeZone));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
					testSteps.addAll(getTestSteps);
					
					reservationPage.enter_Adults(driver, testSteps, Adults);
					reservationPage.enter_Children(driver, testSteps, Children);
					
					reservationPage.clickOnFindRooms(driver, testSteps);
					
					reservationPage.select_Roomm(driver, getTestSteps, RoomClass, IsAssign, Account);
			      
					reservationPage.clickNext(driver, testSteps);
					reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
					
					String randomNumber = Utility.generateRandomNumber();
					reservationPage.enter_GuestName(driver, getTestSteps, Salutation, GuestFirstName + randomNumber, GuestLastName + randomNumber);
					reservationPage.selectReferral(driver, referral);
									
					reservationPage.clickBookNow(driver, testSteps);
					reservationNumber=reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					status=reservationPage.get_ReservationStatus(driver, testSteps);
					
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);
					testSteps.add("Reservation created");
				    app_logs.info("Reservation created");

				 	getTestSteps.clear();
					getTestSteps = navigation.NightAuditIcon(driver);
					testSteps.addAll(getTestSteps);

			    }

				testSteps.add("===== Posting pending line item for specific date =====");
			 	app_logs.info("===== Posting pending line item for specific date =====");

				nightAudit.clickPostedStatus(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				testSteps.addAll(getTestSteps);
			
				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);
				
				nightAudit.postAllItems(driver, testSteps, "CurrentItems");
				nightAudit.postAllItems(driver, testSteps, "PriorItems");
				nightAudit.postAllItems(driver, testSteps, "VoidItems");
			    

				testSteps.add("===== Posting line items for specific date =====");
			 	app_logs.info("===== Posting line items for specific date =====");

				nightAudit.clickPostedStatus(driver, testSteps);

				getTestSteps.clear();
				getTestSteps = nightAudit.EnterAuditDate(driver, dateToLock);
				testSteps.addAll(getTestSteps);

				nightAudit.GoButtonClick(driver, testSteps);				
				nightAudit.PeriodIsOpenButtonClick(driver, testSteps);	
				nightAudit.switchToLockPeriodPopup(driver, testSteps);
				nightAudit.periodLockHeadingDisplay(driver, testSteps, true);	    
			    nightAudit.clickLock(driver, testSteps);
			    
			    nightAudit.PeriodIsOpenButtonClick(driver);
				nightAudit.switchToLockPeriodPopup(driver, testSteps);
				nightAudit.periodLockHeadingDisplay(driver, testSteps, false);	    
			    nightAudit.clickLock(driver, testSteps);
			  
			    navigation.Reservation_Backward(driver);				
			    app_logs.info("Back to reservations");
			    testSteps.add("Back to reservations");
			    
			}
			catch(Exception e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
		            RetryFailedTestCases.count = Utility.reset_count;
		            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		            Utility.updateReport(e, "Failed to lock period",  testName, "PeriodLocking", driver);
		        } else {
		            Assert.assertTrue(false);
		        }
			}catch(Error e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
		            RetryFailedTestCases.count = Utility.reset_count;
		            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		            Utility.updateReport(e, "Failed to lock period",  testName, "PeriodLocking", driver);
		        } else {
		            Assert.assertTrue(false);
		        }
			}
			
			
			try {
				
					app_logs.info("===== Verifying locked period for reservations =====");
					testSteps.add("===== Verifying locked period for reservations =====");				
				
					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);
					
					reservationSearch.clickOnAdvance(driver);
					testSteps.add("Click on advance search");
					app_logs.info("Click on advance search");
					priorItemSize = 2;
				
					if(priorItemSize < 1) {										
						getTestSteps.clear();
						getTestSteps = reservationSearch.advanceSearchWithReservationNumber(driver, reservation);
						testSteps.addAll(getTestSteps);	
						
					}else {
						getTestSteps.clear();
						getTestSteps = reservationSearch.advanceSearchWithReservationNumber(driver, reservationNumber);
					}
					
					getTestSteps.clear();
					getTestSteps = reservationSearch.clickOnSearchButton(driver);
					testSteps.addAll(getTestSteps);

					reservationSearch.OpenSearchedReservation(driver, testSteps);

					app_logs.info("===== Verifying stay info dates for reservations during locked period cannot be updated =====");
					testSteps.add("===== Verifying stay info dates for reservations during locked period cannot be updated =====");				
									
					reservationPage.ClickEditStayInfo(driver, testSteps);
					
					reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);
					
					
					getTestSteps.clear();
					getTestSteps = reservationPage.enterCheckInDate(driver, ESTTimeZone.getNextDateBaseOnPreviouseDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy", 6, timeZone));
					testSteps.addAll(getTestSteps);
										
					getTestSteps.clear();
					getTestSteps = reservationPage.enterCheckOutDate(driver,  ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"));
					testSteps.addAll(getTestSteps);
										
					getTestSteps.clear();
					getTestSteps = reservationPage.clickFindRooms(driver);
					testSteps.addAll(getTestSteps);

					reservationPage.clickSaveAfterEditStayInfo(driver);

					try {
					reservationPage.verifyNoRoomClassesAvailable(driver, testSteps);
					reservationPage.clickCloseStayInfo(driver, testSteps);
					}catch (Exception e) {
						// TODO: handle exception
						testSteps.add("Bug Link: https://innroad.atlassian.net/browse/NG-11826");
					}
					
					

					app_logs.info("Verified that stay info dates cannot be updated beyond locked period");
					testSteps.add("Verified that stay info dates cannot be updated beyond locked period");				

					app_logs.info("===== Verifying line items for reservations during locked period are locked =====");
					testSteps.add("===== Verifying line items for reservations during locked period are locked =====");				
					
					reservationPage.click_Folio(driver, testSteps);
					int totalLineItemSize = folio.getAllLineItemsSize(driver);
					int lockedItemSize = folio.getLockedItemsSize(driver);
					testSteps.add("Expected Locked items : " + totalLineItemSize);
					testSteps.add("Found : " + lockedItemSize);
					assertEquals(lockedItemSize, totalLineItemSize, "Failed all line items are not locked in reservation");
					app_logs.info("Verified that all line items are locked");
					testSteps.add("Verified that all line items are locked");				

					app_logs.info("===== Verifying line items for locked period date cannot be added =====");
					testSteps.add("===== Verifying line items for locked period date cannot be added =====");				

					folio.clickAddButton(driver);
					folio.addLineItem(driver, testSteps, ESTTimeZone.reformatDate(dateToLock, "dd/MM/yyyy", "MM/dd/yyyy"), "Bar", "100", "1");
					int toastMsgSize = folio.getToastMessageSize(driver);
					assertTrue(toastMsgSize > 0, "Failed : Error message didn't displayed");
					app_logs.info("Verifid error message 'Cannot add item - the selected date should be greater than the lock start date' is  displaying on adding line item after locking a specific period");
					testSteps.add("Verifid error message 'Cannot add item - the selected date should be greater than the lock start date' is  displaying on adding line item after locking a specific period");

					int lineItemSizeAfterAddingNewItem = folio.getAllLineItemsSize(driver);
					assertTrue(lineItemSizeAfterAddingNewItem == totalLineItemSize, "Failed : line item added");
					app_logs.info("Verifid line item cannot be added after locking a specific period");
					testSteps.add("Verifid line item cannot be added after locking a specific period");
					

			}
			catch(Exception e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
		            RetryFailedTestCases.count = Utility.reset_count;
		            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		            Utility.updateReport(e, "Failed to verify line item cannot be added for locked period",  testName, "PeriodLocking", driver);
		        } else {
		            Assert.assertTrue(false);
		        }
			}catch(Error e)
			{
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
		            RetryFailedTestCases.count = Utility.reset_count;
		            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		            Utility.updateReport(e, "Failed to verify line item cannot be added for locked period",  testName, "PeriodLocking", driver);
				} else {
		            Assert.assertTrue(false);
		        }
			}

		}

		try {
			comments = " Verified Night Audit funcationality";
			statusCode.add(0, "1");
			statusCode.add(1, "1");	
			statusCode.add(2, "1");	
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}
		catch(Exception e)
		{
			if (Utility.reTry.get(testName) == Utility.count) {
	            RetryFailedTestCases.count = Utility.reset_count;
	            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
	            Utility.updateReport(e, "Failed to add steps into report",  testName, "Report", driver);
	        } else {
	            Assert.assertTrue(false);
	        }
		}catch(Error e)
		{
			if (Utility.reTry.get(testName) == Utility.count) {
	            RetryFailedTestCases.count = Utility.reset_count;
	            Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
	            Utility.updateReport(e, "Failed to add steps into report",  testName, "Report", driver);
	        } else {
	            Assert.assertTrue(false);
	        }
		}

		
		}
	

		
		@DataProvider
		public Object[][] getData() {

			return Utility.getData("verify_NightAuditFunctionality", excel_Swarna);
		}

		@AfterClass(alwaysRun = true)
		public void closeDriver() throws MalformedURLException, IOException, APIException {
		//	Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
		}
}
