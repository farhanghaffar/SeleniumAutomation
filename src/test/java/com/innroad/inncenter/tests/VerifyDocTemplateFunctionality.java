package com.innroad.inncenter.tests;

import com.innroad.inncenter.pageobjects.*;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.mail.Message;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class VerifyDocTemplateFunctionality extends TestCore {
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	private ArrayList<String> test_steps = new ArrayList<>();
	private DocumnetTemplates documentTemplate = new DocumnetTemplates();
	private Navigation navigation = new Navigation();
	private RatesGrid ratesGrid = new RatesGrid();
	private NightlyRate nightlyRate = new NightlyRate();
	private CPReservationPage cpReservationPage = new CPReservationPage();
	private Properties properties = new Properties();
	private Account account = new Account();
	private EmailUtils emailUtils;
	private Policies policies = new Policies();
	private ReservationSearch reservationSearch = new ReservationSearch();
	private Folio folio = new Folio();
	private Groups group = new Groups();
	private AdvGroups advanceGroup = new AdvGroups();
	private Tapechart tapeChart = new Tapechart();
	private NewRoomClassesV2 roomClass = new NewRoomClassesV2();

	private String reservationNumber;
	private String emailSubject;
	private int emailCount = 0;
	private HashMap<String, String> emailContentMap;
	ReservationHomePage homePage = new ReservationHomePage();
	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyDocTemplateFunctionality(String firstRatePlan, String secondRatePlan, String channels,
			String firstRoomClass, String secondRoomClass, String isDefaultTemplate, String propertyName,
			String confirmationEmailTriggers, String confirmationEmailEvents, String confirmationEmailAttachments,
			String otherFunctions, String contentFields, String salutation, String country, String city, String state,
			String stateShort, String poBox, String phoneNumber, String referral, String marketSegment,
			String emailAddress, String paymentType, String cardNumber, String cardName, String cardExpDate,
			String noteType, String noteSubject, String noteDescription, String manualEmailAttachment,
			String manualEmailContentFields, String manualEmailSpecialCharacters, String isMRBStr,
			String otherEmailAddress, String userFirstName, String userLastName, String clientName,
			String incidentalCategory, String primarySource, String secondarySource, String largeImageUrl,
			String isGroupPickUp) {
		test_name = "VerifyDocumentTemplateAndManualEmailFunctionality - " + contentFields + " - "
				+ manualEmailContentFields;
		test_description = "Verify Document Template And Manual Email Functionality<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682440' target='_blank'>"
				+ "Click here to open TestRail: C682440</a>";
		test_catagory = "Reservation";
		String testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String roomClasses = firstRoomClass + "|" + secondRoomClass;
		String seasonStartDate = LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String seasonEndDate = LocalDate.now().plusDays(14).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String travelAgent = "TravelAgent" + Utility.generateRandomString();
		String corporateAccount = (isGroupPickUp.equalsIgnoreCase("yes") ? "GroupAccount" : "CorporateAccount")
				+ Utility.generateRandomString();
		String groupBlock = "GrpBlock" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
		String cancellationPolicyName = "CancellationPolicy" + Utility.generateRandomString();
		String depositPolicyName = "DepositPolicy" + Utility.generateRandomString();
		String checkInPolicyName = "CheckInPolicy" + Utility.generateRandomString();
		String noShowPolicyName = "NoShowPolicy" + Utility.generateRandomString();

		String seasonName = "season";
		String firstRoomRatePerNight = "100";
		String secondRoomRatePerNight = "150";
		String firstRoomClassAbbreviation = "";
		String secondRoomClassAbbreviation = "";
		String documentTemplateName = "DocTemplateTest" + Utility.generateRandomString();
		String templateDescription = "";
		String firstName = "Verify Doc";
		String lastName = "Temp " + Utility.generateRandomString();
		;
		String address1 = "Address # 1";
		String address2 = "Address # 2";
		String address3 = "Address # 3";

		String inputDateFormat = "MM/dd/yyyy";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
		LocalDate arrivalDate = LocalDate.now();
		LocalDate departureDate = LocalDate.now().plusDays(3);

//        firstRatePlan = firstRatePlan + Utility.generateRandomString();
//        secondRatePlan = secondRatePlan + Utility.generateRandomString();
//        seasonName = seasonName + Utility.generateRandomString();
		documentTemplateName = "DocTempTest";
//        templateDescription = templateDescription + Utility.generateRandomString();
//        lastName = lastName + Utility.GenerateRandomNumber();

		HashMap<String, ArrayList<String>> scenariosToValidateMap = new HashMap<>();
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			emailUtils = new EmailUtils(EmailUtils.EmailFolder.INBOX);
			reservationNumber = "";
			emailSubject = "";
			emailCount = 0;
			emailContentMap = new HashMap<>();

			Utility.DELIM = "|";

			emailContentMap.put("Address",
					address1 + "|" + address2 + "|" + address3 + "|" + city + "|" + stateShort + "|" + poBox);
			emailContentMap.put("CCExpDate", cardExpDate);
			emailContentMap.put("CCLast4digits", cardNumber.substring(cardNumber.length() - 4));
			emailContentMap.put("ContactName", firstName + " " + lastName);
			emailContentMap.put("Country", country);
			emailContentMap.put("guestemail", emailAddress);
			emailContentMap.put("GuestFirstName", firstName);
			emailContentMap.put("GuestLastName", lastName);
			emailContentMap.put("guestphonenumber", phoneNumber);
			emailContentMap.put("PaymentName", cardName);
			emailContentMap.put("referral", referral);
			emailContentMap.put("marketSegment", marketSegment);
			emailContentMap.put("Salutation", salutation);
			emailContentMap.put("Notes", noteDescription);
			emailContentMap.put("RoomClass", firstRoomClass);
			emailContentMap.put("Rates", "$ " + firstRoomRatePerNight);
			emailContentMap.put("KeyCode", "");
			emailContentMap.put("StationID", "");
			emailContentMap.put("SystemDate",
					LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			emailContentMap.put("sourceName", channels);
			emailContentMap.put("userBookedFirstName", userFirstName);
			emailContentMap.put("userBookedLastName", userLastName);
			emailContentMap.put("userEditedFirstName", userFirstName);
			emailContentMap.put("userEditedLastName", userLastName);

//			loginWPI(driver);
			HS_login(driver, envURL, Utility.generateLoginCreds(envLoginExcel, "Pay_Login"));
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			test_steps.add("Validating test data provided");
			app_logs.info("Validating test data provided");

			if (!confirmationEmailTriggers.contains("None")) {
				String[] triggerArray = confirmationEmailTriggers.equals("") ? new String[] {}
						: confirmationEmailTriggers.split("\\|", -1);
				String[] eventArray = confirmationEmailEvents.equals("") ? new String[] {}
						: confirmationEmailEvents.split("\\|", -1);
				String[] attachmentArray = confirmationEmailAttachments.equals("") ? new String[] { "" }
						: confirmationEmailAttachments.split("\\|", -1);

				if (triggerArray.length != eventArray.length || triggerArray.length != attachmentArray.length) {
					test_steps.add("Invalid test data provided");
					app_logs.info("Invalid test data provided");
					Assert.fail("Invalid test data provided");
				}

				for (int i = 0; i < triggerArray.length; i++) {
					String key = triggerArray[i].toUpperCase() + "_" + eventArray[i].toUpperCase();
					key = key.replace(" ", "_");
					if (scenariosToValidateMap.containsKey(key)) {
						test_steps.add(
								"Invalid test data provided - duplicate trigger for same event is provided in test data");
						app_logs.info(
								"Invalid test data provided - duplicate trigger for same event is provided in test data");
						Assert.fail(
								"Invalid test data provided - duplicate trigger for same event is provided in test data");
					}
					scenariosToValidateMap.put(key, new ArrayList<>());
					scenariosToValidateMap.get(key).add(triggerArray[i]);
					scenariosToValidateMap.get(key).add(eventArray[i]);
					scenariosToValidateMap.get(key).add(attachmentArray[i]);
				}
			} else {
				String key = "NONE";
				scenariosToValidateMap.put(key, new ArrayList<>());
				scenariosToValidateMap.get(key).add("None");
				scenariosToValidateMap.get(key).add("");
				scenariosToValidateMap.get(key).add("");
				otherFunctions = "";
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Error while validating test data", test_name, "Login", driver);
			} else {
				Assert.fail();
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Error while validating test data", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			if (contentFields.contains("Policies") || manualEmailContentFields.contains("Policies")) {
				String policyText = "";
				navigation.inventory(driver);
				navigation.policies(driver);

				HashMap<String, ArrayList<String>> foundCheckInPrintText = policies.createPolicies(driver, test_steps,
						"\\|", "\\*", "Cancellation|Deposit|Check-in|No Show", cancellationPolicyName,
						depositPolicyName, checkInPolicyName, noShowPolicyName,
						"flat fee|flat fee|% of balance on check-in|flat fee", "10|10|10|10",
						"captured|captured|captured|captured", "0", "after the reservation was made",
						"false|false|false|false", "");
				navigation.inventoryToReservation(driver);

				policyText = foundCheckInPrintText.get(cancellationPolicyName).get(0) + "|";
				policyText += foundCheckInPrintText.get(depositPolicyName).get(0) + "|";
				policyText += foundCheckInPrintText.get(depositPolicyName).get(0) + "|";
				policyText += foundCheckInPrintText.get(depositPolicyName).get(0);
				emailContentMap.put("Policies", policyText);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create policies", test_name, "Policy", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create policies", test_name, "Policy", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			if (contentFields.contains("travelAgentName") || manualEmailContentFields.contains("travelAgentName")) {
				test_steps.add("****************** Creating Travel Agent account *********************");
				app_logs.info("****************** Creating Travel Agent account *********************");
				navigation.navigateToAccounts(driver, test_steps);
				account.createAccount(driver, test_steps, test, "Travel Agent", travelAgent, "Account First Name",
						"Account First Name", "0987654321", "0987654321", "innroadguest@gmail.com", marketSegment,
						referral, address1, address2, address3, city, state, poBox);
				emailContentMap.put("travelAgentName", travelAgent);
				navigation.reservation(driver);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Travel Agent Account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Travel Agent Account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			if (isGroupPickUp.equalsIgnoreCase("yes")) {
				test_steps.add("****************** Creating Group account *********************");
				app_logs.info("****************** Creating Group account *********************");

				app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
				test_steps.add("==========CREATE NEW GROUP ACCOUNT==========");

				navigation.secondaryGroupsManu(driver);
				test_steps.add("Navigate Groups");
				app_logs.info(" Navigate Groups");
				test_steps.addAll(group.enterrGroupName(driver, corporateAccount));
				test_steps.addAll(group.enterAccountNo(driver, Utility.GenerateRandomString15Digit()));
				test_steps.addAll(group.selectAccountAttributes(driver, marketSegment, referral));
				test_steps.addAll(group.enterMailingAddress(driver, "Group_" + firstName, "Group_" + lastName,
						phoneNumber, address1, city, state, country, poBox));
				test_steps.addAll(group.Billinginfo(driver));
				test_steps.addAll(group.clickOnSave(driver));

				app_logs.info("==========CREATE NEW BLOCK==========");
				test_steps.add("==========CREATE NEW BLOCK==========");

				test_steps.addAll(group.navigateRoomBlock(driver));
				test_steps.addAll(group.ClickNewBlock(driver));
				test_steps.addAll(group.EnterBlockName(driver, groupBlock));
				test_steps.addAll(group.ClickOkay_CreateNewBlock(driver));

				app_logs.info("==========SEARCH ROOMS==========");
				test_steps.add("==========SEARCH ROOMS==========");
				test_steps.addAll(group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("MMM dd, yyyy"),
						Utility.GetNextDate(1, "MMM dd, yyyy")));
				test_steps.addAll(group.SelectRatePlan(driver, firstRatePlan));
				test_steps.addAll(group.SelectAdults(driver, "1"));
				test_steps.addAll(group.SelectChilds(driver, "0"));
				test_steps.addAll(group.EnterNights(driver, "1"));
				test_steps.addAll(group.ClickSearchGroup(driver));
				advanceGroup.updatedAutomaticallyAssignedRooms(driver, "1");
//                advanceGroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);
				advanceGroup.ClickCreateBlock(driver, test_steps);
				navigation.reservation(driver);
				emailContentMap.put("accountName", corporateAccount);
			} else if (contentFields.contains("accountName") || manualEmailContentFields.contains("accountName")) {
				test_steps.add("****************** Creating Account *********************");
				app_logs.info("****************** Creating Account *********************");
				navigation.navigateToAccounts(driver, test_steps);
				account.createAccount(driver, test_steps, test, "Corporate/Member Accounts", corporateAccount,
						"Account First Name", "Account First Name", "0987654321", "0987654321",
						"innroadguest@gmail.com", marketSegment, referral, address1, address2, address3, city, state,
						poBox);
				emailContentMap.put("accountName", corporateAccount);
				navigation.reservation(driver);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
			app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
			navigation.Inventory(driver, test_steps);
			navigation.RatesGrid(driver);
			test_steps.add("Navigated to RatesGrid");

			boolean israteplanExist = ratesGrid.isRatePlanExist(driver, firstRatePlan, test_steps);
			Utility.app_logs.info("israteplanExist : " + israteplanExist);
			if (!israteplanExist) {
			boolean attachPolicyToRatePlan = contentFields.contains("Policies")
					|| manualEmailContentFields.contains("Policies");

			test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
			nightlyRate.createNightlyRatePlan(driver, test_steps, firstRatePlan, channels, roomClasses, seasonStartDate,
					seasonEndDate, seasonName, firstRoomRatePerNight + "|" + secondRoomRatePerNight,
					cancellationPolicyName, depositPolicyName, checkInPolicyName, noShowPolicyName,
					attachPolicyToRatePlan);

			if (scenariosToValidateMap.containsKey("ON_RATE_CHANGE") || scenariosToValidateMap.containsKey("NONE")) {
				test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				ratesGrid.clickRateGridAddRatePlan(driver);
				ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
				nightlyRate.createNightlyRatePlan(driver, test_steps, secondRatePlan, channels, roomClasses,
						seasonStartDate, seasonEndDate, seasonName,
						firstRoomRatePerNight + "|" + secondRoomRatePerNight, cancellationPolicyName, depositPolicyName,
						checkInPolicyName, noShowPolicyName, attachPolicyToRatePlan);
			}
			}
			navigation.inventoryToReservation(driver);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			deleteAllDocumentTemplates(driver);
			createDocumentTemplate(driver, test_steps, documentTemplateName, templateDescription, isDefaultTemplate, "",
					propertyName, otherFunctions, "", contentFields, scenariosToValidateMap);
			boolean documentTemplateCreated = documentTemplate.isDocumentTemplateExists(driver, test_steps,
					documentTemplateName);

			if (documentTemplateCreated) {
				test_steps.add("Document template successfully created");
				app_logs.info("Document template successfully created");
				documentTemplate.clickOnTemplate(driver, test_steps, documentTemplateName);
				Set<String> keySet = scenariosToValidateMap.keySet();
				for (String key : keySet) {
					ArrayList<String> functionDateList = scenariosToValidateMap.get(key);
					if (!documentTemplate.documentTemplateHasFunctionAssociated(driver, test_steps,
							"Confirmation Email", functionDateList.get(0), functionDateList.get(1))) {
						test_steps.add(
								"Document template associated functions are not visible <a href='https://innroad.atlassian.net/browse/NG-1269' target='_blank'>NG-1269</a>");
						app_logs.info("Document template associated functions are not visible");
					}

					if (otherFunctions != null && !otherFunctions.equals("")) {
						String[] otherFunctionArray = otherFunctions.split("\\|");
						for (String otherFunction : otherFunctionArray) {
							if (!documentTemplate.documentTemplateHasFunctionAssociated(driver, test_steps,
									otherFunction, null, null)) {
								test_steps.add(
										"Document template associated functions are not visible <a href='https://innroad.atlassian.net/browse/NG-1269' target='_blank'>NG-1269</a>");
								app_logs.info("Document template associated functions are not visible");
							}
						}
					}
				}
			} else {
				test_steps.add(
						"Unable to create Document template <a href='https://innroad.atlassian.net/browse/NG-1993' target='_blank'>NG-1993</a>");
				app_logs.info("Unable to create Document template");
				Assert.assertTrue(documentTemplateCreated,
						"Unable to create Document template <a href='https://innroad.atlassian.net/browse/NG-1993' target='_blank'>NG-1993</a>");
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create document template", test_name, "Document Template", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create document template", test_name, "Document Template", driver);
			} else {
				Assert.fail();
			}
		}

		try {
//            test_steps.add("Fetching Email Content Details");
//            app_logs.info("Fetching Email Content Details");
//            navigation.setup(driver);
//            navigation.properties(driver);
//            properties.SearchProperty_Click(driver, propertyName, test_steps);
//            emailContentMap.put("PropertyLegalName", properties.getPropLegalName(driver));
//            emailContentMap.put("propertyEmail", properties.getPropEmail(driver));
//            emailContentMap.put("PropertyAddr", properties.get_PropAddress(driver));
//            emailContentMap.put("propertyPhone", properties.getPropPhone(driver));
//            navigation.admin(driver);
//            navigation.clickonClientinfo(driver);
//            Admin admin = new Admin();
//            admin.clickOnClient(driver, clientName);
//            admin.clickClientOption(driver);
//            emailContentMap.put("Currency", admin.getDefaultCurrency(driver, test_steps));
//            navigation.reservation(driver);

			emailContentMap.put("PropertyLegalName", "AutoRates");
			emailContentMap.put("propertyEmail", "QA-DG@innroad.com");
			emailContentMap.put("PropertyAddr", "Hotel Mailing Address/Your City/New York/11968");
			emailContentMap.put("propertyPhone", "123-456-7890");
			emailContentMap.put("Currency", "$");

		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to fetch data for email content", test_name, "Properties", driver);
			} else {
				Assert.fail();
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to fetch data for email content", test_name, "Properties", driver);
			} else {
				Assert.fail();
			}
		}

		boolean isMRB = isMRBStr.equalsIgnoreCase("yes");
		boolean validateConfirmationLetter = otherFunctions.contains("Confirmation Letter");
		test_steps.add("Creating new Reservation");
		app_logs.info("Creating new Reservation");
		try {
			navigation.reservation(driver);
			cpReservationPage.click_NewReservation(driver, test_steps);
//			cpReservationPage.click_NewReservation(driver, test_steps);
			test_steps.addAll(cpReservationPage.checkInDates(driver, +0, 0));
			test_steps.addAll(cpReservationPage.checkOutDates(driver, +3, 0));
			emailContentMap.put("NoOfRooms", "1");

			if (isMRB) {
				emailContentMap.put("NoOfRooms", "2");
				test_steps.addAll(cpReservationPage.clickAddARoom(driver));
				test_steps.addAll(cpReservationPage.checkInDates(driver, +0, 1));
				test_steps.addAll(cpReservationPage.checkOutDates(driver, +3, 1));
			}

			emailContentMap.put("ArrivalDate",
					LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			emailContentMap.put("BookedOn",
					LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			emailContentMap.put("DepartureDate",
					LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			emailContentMap.put("ReservationDetailsLink", "");
			emailContentMap.put("RoomCharges", "");
			emailContentMap.put("Incidentals", "");
			emailContentMap.put("TotalTaxes", "");
			emailContentMap.put("TotalCharges", "");
			emailContentMap.put("PaymentAmount", "");
			emailContentMap.put("FolioBalance", "");

			if (isMRB) {
				cpReservationPage.selectRateplanIndex(driver, firstRatePlan, "", 0, 0);
				cpReservationPage.selectRateplanIndex(driver, firstRatePlan, "", 1, 2);
			} else {
				cpReservationPage.select_Rateplan(driver, test_steps, firstRatePlan, "");
			}

			cpReservationPage.clickOnFindRooms(driver, test_steps);
			test_steps.addAll(cpReservationPage.selectRoom(driver, firstRoomClass, "Yes"));

			if (isMRB) {
				test_steps.addAll(cpReservationPage.selectRoom(driver, firstRoomClass, "Yes"));
			}

			cpReservationPage.clickNext(driver, test_steps);

			if (isMRB) {
				cpReservationPage.selectRoomNumberInMRB(driver, firstRoomClass, 0, 0);
				cpReservationPage.selectRoomNumberInMRB(driver, firstRoomClass, 0, 1);
				cpReservationPage.enterAdditionalGuestName(driver, firstName + "Add", lastName + "Add");
			}

			cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");

			if (contentFields.contains("accountName") || manualEmailContentFields.contains("accountName")) {
				// cpReservationPage.selectAccount(driver, test_steps, corporateAccount, "No");
				cpReservationPage.selectAccountOnReservation(driver, test_steps, corporateAccount, "No", "No");
			}

			if (contentFields.contains("travelAgentName") || manualEmailContentFields.contains("travelAgentName")) {
				cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, travelAgent, marketSegment, referral,
						false);
			} else {
				cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral, false);
			}

			cpReservationPage.enter_GuestName(driver, test_steps, salutation, firstName, lastName);
			cpReservationPage.select_Country(driver, test_steps, country);
			cpReservationPage.enter_Address(driver, test_steps, address1, address2, address3);
			cpReservationPage.enter_City(driver, test_steps, city);
			cpReservationPage.select_State(driver, test_steps, state);
			cpReservationPage.enter_PostalCode(driver, test_steps, poBox);
			cpReservationPage.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
			cpReservationPage.enterEmail(driver, test_steps, emailAddress);
			cpReservationPage.enterPaymentDetails(driver, test_steps, paymentType, cardNumber, cardName, cardExpDate);

			if (contentFields.contains("Notes") || manualEmailAttachment.contains("Notes")) {
				if (isMRB) {
					cpReservationPage.AddNotes(driver, test_steps, "All", noteType, noteSubject, noteDescription);
					cpReservationPage.enterAdditionalEmailAndPhoneNumber(driver, otherEmailAddress, "");
				} else {
					cpReservationPage.enter_Notes(driver, test_steps, "Yes", noteType, noteSubject, noteDescription);
				}
			}

			emailCount = 0;

			if (scenariosToValidateMap.containsKey("ON_QUOTE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Quote Functionality ========");
				test_steps.add("=========  Validating On Quote Functionality ========");
				cpReservationPage.clickSaveAsQuoteButton(driver);
				reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				emailContentMap.put("ConfirmationNumber", reservationNumber);
				emailSubject = "Reservation #: " + reservationNumber;
				emailContentMap.put("reservationStatus", "Quote");
				cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);

				if (scenariosToValidateMap.containsKey("ON_QUOTE")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_QUOTE"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_QUOTE", 300);
				}

				try {
					cpReservationPage.clickBookQuote(driver, test_steps);
				} catch (Exception e) {
					cpReservationPage.proceedToBookingPayment(driver, test_steps);
					cpReservationPage.clickOnCheckOutPay(driver, test_steps);
					cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				}
				cpReservationPage.waitForReservationStatusToChangeTo(driver, "Reserved");
			} else {
				cpReservationPage.clickBookNow(driver, test_steps);
				reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
				emailContentMap.put("ConfirmationNumber", reservationNumber);
				emailSubject = "Reservation #: " + reservationNumber;
				emailContentMap.put("reservationStatus", "Reserved");
				cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
			}

			emailContentMap.put("noOfnights", cpReservationPage.getNoOfNights_ResDetail(driver));
			emailContentMap.put("AdultsChildren", cpReservationPage.getNoOfAdults_ResDetail(driver) + "/"
					+ cpReservationPage.getNoOfChilds_ResDetail(driver));
			emailContentMap.put("Room", cpReservationPage.getRoomNo_ResDetail(driver));

//            cpReservationPage.clickFolio(driver, test_steps);
//            folio.AddLineItem(driver, incidentalCategory, "10", 0, "1");
//            folio.ClickSaveFolioButton(driver);
//            cpReservationPage.clickOnDetails(driver);

			updateFolioDetailsInEmailMap(driver, isMRB);

			emailCount = getEmailMessageCount(emailSubject);
			if (otherFunctions.contains("Confirmation Message")) {
				app_logs.info("========= Validating Reservation Confirmation Message Functionality ========");
				test_steps.add("=========  Validating Reservation Confirmation Message Functionality ========");
				if (emailUtils.newEmailReceived(test_steps, emailSubject, emailCount, 300)) {
					app_logs.info("Reservation Confirmation Message Email received");
					test_steps.add("Reservation Confirmation Message Email received");
				} else {
					app_logs.info("Reservation Confirmation Message Email not received");
					test_steps.add("Assertion Reservation Confirmation Message Email not received");
				}
			}

			emailCount = getEmailMessageCount(emailSubject);
			if (scenariosToValidateMap.containsKey("ON_RESERVED") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Reserved Functionality ========");
				test_steps.add("=========  Validating On Reserved Functionality ========");

				if (scenariosToValidateMap.containsKey("ON_RESERVED")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_RESERVED"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_RESERVED", 300);
				}
			}

			emailCount = getEmailMessageCount(emailSubject);
			if (scenariosToValidateMap.containsKey("ON_ARRIVAL_DATE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Arrival Date Functionality ========");
				test_steps.add("=========  Validating On Arrival Date Functionality ========");

				if (scenariosToValidateMap.containsKey("ON_ARRIVAL_DATE")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_ARRIVAL_DATE"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_ARRIVAL_DATE", 300);
				}
			}

			if (scenariosToValidateMap.containsKey("ON_CONFIRMED") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Confirmed Functionality ========");
				test_steps.add("=========  Validating On Confirmed Functionality ========");
				emailCount = getEmailMessageCount(emailSubject);
				cpReservationPage.change_ReservationStatus(driver, test_steps, "Confirmed");
				emailContentMap.put("reservationStatus", "Confirmed");
				if (scenariosToValidateMap.containsKey("ON_CONFIRMED")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_CONFIRMED"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_CONFIRMED", 300);
				}
			}

			if (scenariosToValidateMap.containsKey("ON_GUARANTEED") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Guaranteed Functionality ========");
				test_steps.add("=========  Validating On Guaranteed Functionality ========");
				emailCount = getEmailMessageCount(emailSubject);
				cpReservationPage.change_ReservationStatus(driver, test_steps, "Guaranteed");
				emailContentMap.put("reservationStatus", "Guaranteed");

				if (scenariosToValidateMap.containsKey("ON_GUARANTEED")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_GUARANTEED"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_GUARANTEED", 300);
				}
			}

			if (scenariosToValidateMap.containsKey("ON_ON_HOLD") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Hold Functionality ========");
				test_steps.add("=========  Validating On Hold Functionality ========");
				emailCount = getEmailMessageCount(emailSubject);
				cpReservationPage.change_ReservationStatus(driver, test_steps, "On Hold");
				emailContentMap.put("reservationStatus", "On Hold");

				if (scenariosToValidateMap.containsKey("ON_ON_HOLD")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_ON_HOLD"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_ON_HOLD", 300);
				}
				cpReservationPage.change_ReservationStatus(driver, test_steps, "Reserved");
				emailContentMap.put("reservationStatus", "Reserved");
			}

			if (scenariosToValidateMap.containsKey("ON_NO_SHOW") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On No Show Functionality ========");
				test_steps.add("=========  Validating On No Show Functionality ========");
				emailCount = getEmailMessageCount(emailSubject);
				cpReservationPage.change_ReservationStatus(driver, test_steps, "No Show");
				cpReservationPage.clickConfirmNoShowButton(driver, test_steps);

				if (cpReservationPage.proceedToNoShowPaymentButtonIsPresent(driver, test_steps)) {
					cpReservationPage.clickProceedToNoShowPaymentButton(driver, test_steps);
					cpReservationPage.clickOnCheckOutPay(driver, test_steps);
					cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				} else {
					cpReservationPage.clickCloseButtonOfSuccessModelPopup(driver, test_steps);
				}
				emailContentMap.put("reservationStatus", "No Show");
				updateFolioDetailsInEmailMap(driver, isMRB);

				if (scenariosToValidateMap.containsKey("ON_NO_SHOW")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_NO_SHOW"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_NO_SHOW", 300);
				}

				cpReservationPage.clickRollBackButton(driver, test_steps);
				cpReservationPage.clickYesButtonRollBackMsg(driver, test_steps);
				cpReservationPage.waitForReservationStatusToChangeTo(driver, "Reserved");
				emailContentMap.put("reservationStatus", "Reserved");
			}

			if (scenariosToValidateMap.containsKey("ON_DATE_CHANGE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Date Change Functionality ========");
				test_steps.add("=========  Validating On Date Change Functionality ========");
				LocalDate startDate = LocalDate.now().plusDays(0);
				LocalDate endDate = (LocalDate.now()).plusDays(2);

				emailContentMap.put("ArrivalDate", startDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
				emailContentMap.put("DepartureDate", endDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

				if (isMRB) {
					cpReservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
					test_steps.addAll(cpReservationPage.enterCheckInDate(driver,
							startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					test_steps.addAll(cpReservationPage.enterCheckOutDate(driver,
							endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					cpReservationPage.clickOnFindRooms(driver, test_steps);
					emailCount = getEmailMessageCount(emailSubject);
					test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_DATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_DATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_DATE_CHANGE", 300);
					}

					cpReservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
					test_steps.addAll(cpReservationPage.enterCheckInDate(driver,
							startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					test_steps.addAll(cpReservationPage.enterCheckOutDate(driver,
							endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					cpReservationPage.clickOnFindRooms(driver, test_steps);
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_DATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_DATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_DATE_CHANGE", 300);
					}
				} else {
					test_steps.addAll(cpReservationPage.clickEditReservation(driver));
					test_steps.addAll(cpReservationPage.clickChangeStayDetails(driver));
					test_steps.addAll(cpReservationPage.enterCheckInDate(driver,
							startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					test_steps.addAll(cpReservationPage.enterCheckOutDate(driver,
							endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
					cpReservationPage.clickOnFindRooms(driver, test_steps);
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_DATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_DATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_DATE_CHANGE", 300);
					}
				}
			}

			if (scenariosToValidateMap.containsKey("ON_RATE_CHANGE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Rate Change Functionality ========");
				test_steps.add("=========  Validating On Rate Change Functionality ========");

				if (isMRB) {
					cpReservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
					test_steps.addAll(cpReservationPage.select_Rateplan(driver, test_steps, secondRatePlan, ""));
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, firstRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_RATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_RATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_RATE_CHANGE", 300);
					}

					cpReservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
					test_steps.addAll(cpReservationPage.select_Rateplan(driver, test_steps, secondRatePlan, ""));
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, firstRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_RATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_RATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_RATE_CHANGE", 300);
					}
				} else {
					test_steps.addAll(cpReservationPage.clickEditReservation(driver));
					test_steps.addAll(cpReservationPage.clickChangeStayDetails(driver));
					test_steps.addAll(cpReservationPage.select_Rateplan(driver, test_steps, secondRatePlan, ""));
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, firstRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_RATE_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_RATE_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_RATE_CHANGE", 300);
					}
				}
			}

			if (scenariosToValidateMap.containsKey("ON_ROOM_CHANGE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Room Change Functionality ========");
				test_steps.add("=========  Validating On Room Change Functionality ========");

				emailCount = getEmailMessageCount(emailSubject);

				if (isMRB) {
					cpReservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, secondRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_ROOM_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_ROOM_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_ROOM_CHANGE", 300);
					}

					cpReservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, secondRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfoInMRB(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_ROOM_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_ROOM_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_ROOM_CHANGE", 300);
					}
				} else {
					test_steps.addAll(cpReservationPage.clickEditReservation(driver));
					test_steps.addAll(cpReservationPage.clickChangeStayDetails(driver));
					test_steps.addAll(cpReservationPage.clickFindRooms(driver));
					test_steps.addAll(
							cpReservationPage.selectRoomInStayInfoSectionUpdated(driver, secondRoomClass, "Yes"));
					emailCount = getEmailMessageCount(emailSubject);

					try {
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					} catch (Exception e) {
						cpReservationPage.verifyPopupChangeInPolicy(driver, test_steps);
						test_steps.addAll(cpReservationPage.clickSaveAfterEditStayInfo(driver));
					}
					updateFolioDetailsInEmailMap(driver, isMRB);

					if (scenariosToValidateMap.containsKey("ON_ROOM_CHANGE")) {
						validateEmail(emailSubject, scenariosToValidateMap.get("ON_ROOM_CHANGE"), contentFields,
								documentTemplateName, otherFunctions, 300);
					}

					if (scenariosToValidateMap.containsKey("NONE")) {
						validateNoEmailReceived(emailSubject, "NONE_ROOM_CHANGE", 300);
					}
				}

				emailContentMap.put("RoomClass", secondRoomClass);
				emailContentMap.put("Rates", "$ " + secondRoomRatePerNight);
			}

			if (scenariosToValidateMap.containsKey("ON_CANCELLED") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Cancelled Functionality ========");
				test_steps.add("=========  Validating On Cancelled Functionality ========");
				emailCount = getEmailMessageCount(emailSubject);
				cpReservationPage.cancelReservation(driver, test_steps);
				cpReservationPage.addResonOnCancelModelPopup(driver, test_steps, "Cancelled");
				cpReservationPage.CompleteCancelProcess(driver, test_steps);
				boolean isCancelReservationExist = cpReservationPage.getCancelReservationHeaderWindow(driver);
				if (isCancelReservationExist) {
					cpReservationPage.clickOnCheckOutPay(driver, test_steps);
					cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				}
				emailContentMap.put("reservationStatus", "Cancel");
				updateFolioDetailsInEmailMap(driver, isMRB);

				if (scenariosToValidateMap.containsKey("ON_CANCELLED")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_CANCELLED"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_CANCELLED", 300);
				}

				cpReservationPage.clickRollBackButton(driver, test_steps);
				cpReservationPage.clickYesButtonRollBackMsg(driver, test_steps);
				cpReservationPage.waitForReservationStatusToChangeTo(driver, "Reserved");
				emailContentMap.put("reservationStatus", "Reserved");
			}

			if (scenariosToValidateMap.containsKey("ON_IN-HOUSE") || scenariosToValidateMap.containsKey("ON_DEPARTED")
					|| scenariosToValidateMap.containsKey("NONE") || scenariosToValidateMap.containsKey("NONE")) {
				if (isMRB) {
					cpReservationPage.clickCheckInAllButton(driver, test_steps);
				} else {
					cpReservationPage.clickCheckInButton(driver, test_steps);
				}
				emailCount = getEmailMessageCount(emailSubject);

				try {
					cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
					cpReservationPage.checkInProcess(driver, test_steps);
					cpReservationPage.clickOnCheckOutPay(driver, test_steps);
					cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				} catch (Exception ignore) {
				}
				cpReservationPage.waitForReservationStatusToChangeTo(driver, "In-House");
			}

			if (scenariosToValidateMap.containsKey("ON_IN-HOUSE") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating In House Change Functionality ========");
				test_steps.add("=========  Validating In House Change Functionality ========");
				emailContentMap.put("reservationStatus", "In House");
				updateFolioDetailsInEmailMap(driver, isMRB);

				if (scenariosToValidateMap.containsKey("ON_IN-HOUSE")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_IN-HOUSE"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_IN-HOUSE", 300);
				}
			}

			if (scenariosToValidateMap.containsKey("ON_DEPARTED") || scenariosToValidateMap.containsKey("NONE")) {
				app_logs.info("========= Validating On Departed Change Functionality ========");
				test_steps.add("=========  Validating On Departed Change Functionality ========");
				if (isMRB) {
					cpReservationPage.clickCheckOutAllButton(driver, test_steps);
					cpReservationPage.clickYesButtonOfCheckOutAllConfirmationMsg(driver, test_steps, "", "", "", "");
				} else {
					cpReservationPage.clickCheckOutButton(driver, test_steps);
				}

				cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
				cpReservationPage.proceedToCheckOutPayment(driver, test_steps);
				cpReservationPage.clickOnCheckOutPay(driver, test_steps);
				cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				emailContentMap.put("reservationStatus", "Departed");
				updateFolioDetailsInEmailMap(driver, isMRB);

				if (scenariosToValidateMap.containsKey("ON_DEPARTED")) {
					validateEmail(emailSubject, scenariosToValidateMap.get("ON_DEPARTED"), contentFields,
							documentTemplateName, otherFunctions, 300);
				}

				if (scenariosToValidateMap.containsKey("NONE")) {
					validateNoEmailReceived(emailSubject, "NONE_DEPARTED", 300);
				}
			}

			String manualEmailSubject = emailSubject + " - Default document template";
			cpReservationPage.manualEmail(driver, emailAddress, manualEmailSubject, "", documentTemplateName, "", "");
			validateManualEmail(manualEmailSubject, "", "", "", 300);

			if (!manualEmailAttachment.equals("") || !manualEmailContentFields.equals("")) {
				manualEmailSubject = emailSubject + " - Manual email with attachment and custom field";
				cpReservationPage.manualEmail(driver, emailAddress, manualEmailSubject, manualEmailAttachment,
						"New Email", manualEmailContentFields, "");
				validateManualEmail(manualEmailSubject, manualEmailAttachment, manualEmailContentFields, "", 300);
			}

			if (!manualEmailSpecialCharacters.equals("")) {
				manualEmailSubject = emailSubject + " - Default document template with special characters";
				cpReservationPage.manualEmail(driver, emailAddress, manualEmailSubject, "", "New Email", "",
						manualEmailSpecialCharacters);
				validateManualEmail(manualEmailSubject, "", "", manualEmailSpecialCharacters, 300);
			}

			if (validateConfirmationLetter) {
				manualEmailSubject = emailSubject + " - Default document template with Confirmation Letter";
				cpReservationPage.manualEmail(driver, emailAddress, manualEmailSubject, documentTemplateName,
						documentTemplateName, "", "");
				validateManualEmail(manualEmailSubject, documentTemplateName, "", "", 300);
			}

			if (validateConfirmationLetter) {
				app_logs.info(
						"========= Validating if Confirmation Letter is attached to the Reservation Page ========");
				if (cpReservationPage.verifyIfDocumentIsAvailableOnReservationPage(driver, test_steps,
						documentTemplateName)) {
					app_logs.info("========= Confirmation Letter is attached to the Reservation Page ========");
					test_steps.add("=========  Confirmation Letter is attached to the Reservation Page ========");
				} else {
					app_logs.info(
							"========= Assertion Confirmation Letter is not attached to the Reservation Page ========");
					test_steps.add(
							"========= Assertion Confirmation Letter is not attached to the Reservation Page ========");
				}
			}

			if (!(scenariosToValidateMap.containsKey("ON_IN-HOUSE")
					|| scenariosToValidateMap.containsKey("ON_DEPARTED"))) {
				cpReservationPage.cancelReservation(driver, test_steps);
				cpReservationPage.addResonOnCancelModelPopup(driver, test_steps, "Cancelled");
				cpReservationPage.CompleteCancelProcess(driver, test_steps);
				boolean isCancelReservationExist = cpReservationPage.getCancelReservationHeaderWindow(driver);
				if (isCancelReservationExist) {
					cpReservationPage.clickOnCheckOutPay(driver, test_steps);
					cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
				}
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to create reservation or validate triggers", test_name, "Login",
						driver);
			} else {
				Assert.fail();
			}

		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to create reservation or validate triggers", test_name, "Login",
						driver);
			} else {
				Assert.fail();
			}
		}

		try {
			app_logs.info("Cleaning Up");
			test_steps.add("Cleaning Up");

			deleteAllDocumentTemplates(driver);

			if (contentFields.contains("Policies") || manualEmailContentFields.contains("Policies")) {
				ArrayList<String> policiesToDelete = new ArrayList<>();
				policiesToDelete.add(cancellationPolicyName);
				policiesToDelete.add(depositPolicyName);
				policiesToDelete.add(checkInPolicyName);
				policiesToDelete.add(noShowPolicyName);

				app_logs.info("Removing Policies");
				test_steps.add("Removing Policies");
				navigation.inventory(driver);
				navigation.policies(driver);
				policies.deleteAllPolicies(driver, test_steps, policiesToDelete);
				navigation.inventoryToReservation(driver);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			String reservationName01 = "";
			String reservationName02 = "";
			String reservationNumber01 = "";
			String reservationNumber02 = "";
			String emailSubject01 = "";
			String emailSubject02 = "";
			ArrayList<String> triggerDetailList = new ArrayList<>();
			triggerDetailList.add("On");
			triggerDetailList.add("");
			triggerDetailList.add("");

			ArrayList<String> events;
			ArrayList<String> attachments;
			HashMap<String, ArrayList<String>> map = null;
			events = new ArrayList<>();
			attachments = new ArrayList<>();
			events.add("In-House");
			events.add("Departed");
			events.add("No Show");
			events.add("Cancelled");
			events.add("Date Change");
			events.add("Rate Change");
			events.add("Room Change");

			deleteAllDocumentTemplates(driver);

			documentTemplateName = "BulkTestDocTemp" + Utility.generateRandomStringWithGivenLength(5);
			map = getDefaultScenariosToValidateMap(events, attachments);
			createDocumentTemplate(driver, test_steps, documentTemplateName, documentTemplateName, "Yes", "",
					propertyName, "", "", "", map);

			firstName = "BulkChkInOut" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName01 = firstName + " " + lastName;

			reservationNumber01 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject01 = "Reservation #: " + reservationNumber01;
			cpReservationPage.closeAllOpenedReservations(driver);

			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			;
			reservationName02 = firstName + " " + lastName;
			reservationNumber02 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject02 = "Reservation #: " + reservationNumber02;

			cpReservationPage.closeAllOpenedReservations(driver);
			navigation.reservation(driver);
			reservationSearch.basicSearch_WithGuestNameWithoutAssertion(driver, firstName);
			cpReservationPage.selectAllSearchedReservationCheckBox(driver);
			reservationSearch.Bulkcheckin(driver, firstName, test_steps, false);

			triggerDetailList.set(1, "In-House");
			emailCount = getEmailMessageCount(emailSubject01);
			emailContentMap.put("ConfirmationNumber", reservationNumber01);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			emailCount = getEmailMessageCount(emailSubject02);
			emailContentMap.put("ConfirmationNumber", reservationNumber02);
			validateEmail(emailSubject02, triggerDetailList, contentFields, documentTemplateName, "", 300);

			reservationSearch.basicSearch_WithGuestNameWithoutAssertion(driver, firstName);
			cpReservationPage.selectAllSearchedReservationCheckBox(driver);
			reservationSearch.selectBulkCheckOut(driver);
			cpReservationPage.clickOnProcessInBulkCheckOutPopUp(driver);
			cpReservationPage.closeBulkCheckoutActionPopUp(driver);

			triggerDetailList.set(1, "Departed");

			emailCount = getEmailMessageCount(emailSubject01);
			emailContentMap.put("ConfirmationNumber", reservationNumber01);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			emailCount = getEmailMessageCount(emailSubject02);
			emailContentMap.put("ConfirmationNumber", reservationNumber02);
			validateEmail(emailSubject02, triggerDetailList, contentFields, documentTemplateName, "", 300);

			firstName = "BulkNoShow" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName01 = firstName + " " + lastName;

			reservationNumber01 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject01 = "Reservation #: " + reservationNumber01;
			cpReservationPage.closeAllOpenedReservations(driver);

			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			;
			reservationName02 = firstName + " " + lastName;
			reservationNumber02 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject02 = "Reservation #: " + reservationNumber02;
			cpReservationPage.closeAllOpenedReservations(driver);

			navigation.reservation(driver);
			reservationSearch.basicSearch_WithGuestNameWithoutAssertion(driver, firstName);
			test_steps.addAll(reservationSearch.makeReservationBulkNoShow(driver));

			triggerDetailList.set(1, "No Show");
			emailCount = getEmailMessageCount(emailSubject01);
			emailContentMap.put("ConfirmationNumber", reservationNumber01);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			emailCount = getEmailMessageCount(emailSubject02);
			emailContentMap.put("ConfirmationNumber", reservationNumber02);
			validateEmail(emailSubject02, triggerDetailList, contentFields, documentTemplateName, "", 300);

			firstName = "BulkCancel" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName01 = firstName + " " + lastName;

			reservationNumber01 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject01 = "Reservation #: " + reservationNumber01;
			cpReservationPage.closeAllOpenedReservations(driver);

			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			;
			reservationName02 = firstName + " " + lastName;
			reservationNumber02 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject02 = "Reservation #: " + reservationNumber02;
			cpReservationPage.closeAllOpenedReservations(driver);

			navigation.reservation(driver);
			reservationSearch.basicSearch_WithGuestNameWithoutAssertion(driver, firstName);
			cpReservationPage.selectAllSearchedReservationCheckBox(driver);
			test_steps.addAll(reservationSearch.bulkActionCancellationForSelected(driver));

			triggerDetailList.set(1, "Cancel");
			emailCount = getEmailMessageCount(emailSubject01);
			emailContentMap.put("ConfirmationNumber", reservationNumber01);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			emailCount = getEmailMessageCount(emailSubject02);
			emailContentMap.put("ConfirmationNumber", reservationNumber02);
			validateEmail(emailSubject02, triggerDetailList, contentFields, documentTemplateName, "", 300);

			firstName = "DocTempTape" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = "Chart" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName01 = firstName + " " + lastName;

			reservationNumber01 = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, firstRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			String roomNumber = homePage.verifyReservationRoomNumber(driver);
			emailSubject01 = "Reservation #: " + reservationNumber01;
			cpReservationPage.closeAllOpenedReservations(driver);

			navigation.navigateToSetupFromReservationPage(driver, test_steps);
			navigation.roomClass(driver, test_steps);
			firstRoomClassAbbreviation = roomClass.getAbbreviationText(driver, test_steps, firstRoomClass);
			secondRoomClassAbbreviation = roomClass.getAbbreviationText(driver, test_steps, secondRoomClass);
			navigation.navigateToReservation(driver);
			navigation.navigateToTapeChartFromReservations(driver);

			HashMap<String, ArrayList<Integer>> firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver,
					test_steps, secondRoomClassAbbreviation, 7);
			String newRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps,
					firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter),
					departureDate.format(dateTimeFormatter), inputDateFormat);
/*
			triggerDetailList.set(1, "Cancel");
			emailCount = getEmailMessageCount(emailSubject01);
			tapeChart.moveReservation(driver, test_steps, reservationName01, firstRoomClassAbbreviation, roomNumber,
					firstRoomClassAbbreviation, newRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
			tapeChart.selectRateOptionsToApplyRate(driver, 2);
			tapeChart.clickConfirm_RaservationUpdate(driver);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			roomNumber = newRoomNumber;
			HashMap<String, ArrayList<Integer>> secondRoomReservationStatusMap = tapeChart
					.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
			newRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps,
					secondRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter),
					departureDate.format(dateTimeFormatter), inputDateFormat);

			emailCount = getEmailMessageCount(emailSubject01);
			tapeChart.moveReservation(driver, test_steps, reservationName01, firstRoomClassAbbreviation, roomNumber,
					secondRoomClassAbbreviation, newRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
			tapeChart.selectRateOptionsToApplyRate(driver, 2);
			tapeChart.clickConfirm_RaservationUpdate(driver);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);

			emailCount = getEmailMessageCount(emailSubject01);
			tapeChart.updateCheckOutDate(driver, test_steps, null, null, reservationName01, 3, -1);
			validateEmail(emailSubject01, triggerDetailList, contentFields, documentTemplateName, "", 300);
*/
		} catch (Exception e) {e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			String reservationName = "";
			ArrayList<String> events;
			ArrayList<String> attachments;
			HashMap<String, ArrayList<String>> map = null;

			deleteAllDocumentTemplates(driver);

			String documentTemplateName01 = null;
			String documentTemplateName02 = null;
			String documentTemplateName03 = null;

			documentTemplateName01 = "ConfirmationLetter" + Utility.generateRandomStringWithGivenLength(5);
			documentTemplateName02 = "DefaultDocTemp" + Utility.generateRandomStringWithGivenLength(5);

			createDocumentTemplate(driver, test_steps, documentTemplateName01, documentTemplateName01, "No", "",
					propertyName, "Confirmation Letter", "", "", new HashMap<>());

			events = new ArrayList<>();
			attachments = new ArrayList<>();
			events.add("Reserved");
			attachments.add(documentTemplateName01);

			map = getDefaultScenariosToValidateMap(events, attachments);

			createDocumentTemplate(driver, test_steps, documentTemplateName02, documentTemplateName02, "Yes", "",
					propertyName, "", "", "", map);

			firstName = "NG11111_" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName = firstName + " " + lastName;

			reservationNumber = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject = "Reservation #: " + reservationNumber;

			emailCount = getEmailMessageCount(emailSubject);
			emailContentMap.put("ConfirmationNumber", reservationNumber);
			validateEmail(emailSubject, map.get("ON_RESERVED"), "", documentTemplateName, "", 300);
			navigation.reservation(driver);
			deleteAllDocumentTemplates(driver);

			// Issue NG-8812 verification
/*
			documentTemplateName01 = primarySource + "Doc" + Utility.generateRandomStringWithGivenLength(5);
			documentTemplateName02 = secondarySource + "Doc" + Utility.generateRandomStringWithGivenLength(5);
			documentTemplateName03 = primarySource + "Doc" + Utility.generateRandomStringWithGivenLength(5);

			events = new ArrayList<>();
			attachments = new ArrayList<>();
			events.add("Reserved");
			map = getDefaultScenariosToValidateMap(events, attachments);
			StringBuilder builder = new StringBuilder();
			builder.append(Utility.generateRandomStringWithGivenLength(20000));
			builder.append(String.format("<img style=\"width:100%\" src=\"%s\">", largeImageUrl));

			createDocumentTemplate(driver, test_steps, documentTemplateName01, documentTemplateName01, "Yes",
					primarySource, propertyName, "", builder.toString(), "", map);

			createDocumentTemplate(driver, test_steps, documentTemplateName02, documentTemplateName02, "No",
					secondarySource, propertyName, "", "", "", map);

			createDocumentTemplate(driver, test_steps, documentTemplateName03, documentTemplateName03, "No",
					primarySource, propertyName, "", "", "", map);

			firstName = "NG8812_" + Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			lastName = Utility.generateRandomNumberWithGivenNumberOfDigits(5);
			reservationName = firstName + " " + lastName;

			reservationNumber = createReservation(driver, test_steps, false, contentFields, manualEmailContentFields,
					firstRatePlan, secondRoomClass, firstName, lastName, corporateAccount, travelAgent, marketSegment,
					referral, salutation, country, address1, address2, address3, city, state, poBox, phoneNumber,
					emailAddress, paymentType, cardNumber, cardName, cardExpDate, noteType, noteSubject,
					noteDescription, false);
			emailSubject = "Reservation #: " + reservationNumber;

			ArrayList<String> documentTemplateList = new ArrayList<>();
			documentTemplateList.add(documentTemplateName01);
			documentTemplateList.add(documentTemplateName02);
			documentTemplateList.add(documentTemplateName03);
*/
//            cpReservationPage.clickManualEmail(driver, test_steps);
//            cpReservationPage.verifyManualEmailBodySortOrder(driver, test_steps, new ArrayList<>());
//            String manualEmailContentBody = cpReservationPage.getManualEmailEditorText(driver, test_steps);

//            if(manualEmailContentBody.contains(builder.toString())) {
//                app_logs.info("Manual email body show large text and image correctly");
//                test_steps.add("Manual email body show large text and image correctly");
//            } else {
//                app_logs.info("Manual email body does not show large text and image correctly");
//                test_steps.add("Assertion Manual email body does not show large text and image correctly<br>" +
//                        "<a href='https://innroad.atlassian.net/browse/NG-7340' target='_blank'>NG-7340</a><br>" +
//                        "<a href='https://innroad.atlassian.net/browse/NG-7319' target='_blank'>NG-7319</a>");
//            }
//
//            cpReservationPage.sendManualEmail(driver, test_steps);
			navigation.reservation(driver);
			deleteAllDocumentTemplates(driver);

		} catch (Exception e) {e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}

		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		} catch (Error e) {
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to complete test", test_name, "Login", driver);
			} else {
				Assert.fail();
			}
		}
	}

	private void updateFolioDetailsInEmailMap(WebDriver driver, boolean isMRB) throws InterruptedException {
		double roomCharges = 0.0;
		double incidentalCharge = 0.0;
		double taxCharges = 0.0;
		double totalCharges = 0.0;
		double paymentAmount = 0.0;
		double folioBalance = 0.0;

		cpReservationPage.clickFolio(driver, test_steps);
		roomCharges = Double.parseDouble(cpReservationPage.getFolioRoomCharges(driver));
		incidentalCharge = Double.parseDouble(cpReservationPage.get_Inceidentals(driver, test_steps));
		taxCharges = Double.parseDouble(cpReservationPage.getFolioTaxServices(driver));
		totalCharges = Double.parseDouble(cpReservationPage.get_TotalCharges(driver, test_steps));
		paymentAmount = Double.parseDouble(cpReservationPage.get_Payments(driver, test_steps));
		folioBalance = Double.parseDouble(cpReservationPage.get_Balance(driver, test_steps));

		if (isMRB) {
			cpReservationPage.selectFolioOption(driver, 2);
			roomCharges += Double.parseDouble(cpReservationPage.getFolioRoomCharges(driver));
			incidentalCharge += Double.parseDouble(cpReservationPage.get_Inceidentals(driver, test_steps));
			taxCharges += Double.parseDouble(cpReservationPage.getFolioTaxServices(driver));
			totalCharges += Double.parseDouble(cpReservationPage.get_TotalCharges(driver, test_steps));
			paymentAmount += Double.parseDouble(cpReservationPage.get_Payments(driver, test_steps));
			folioBalance += Double.parseDouble(cpReservationPage.get_Balance(driver, test_steps));
		}

		emailContentMap.put("RoomCharges", String.valueOf((int) roomCharges));
		emailContentMap.put("Incidentals", String.valueOf((int) incidentalCharge));
		emailContentMap.put("TotalTaxes", String.valueOf((int) taxCharges));
		emailContentMap.put("TotalCharges", String.valueOf((int) totalCharges));
		emailContentMap.put("PaymentAmount", String.valueOf((int) paymentAmount));
		emailContentMap.put("FolioBalance", String.valueOf((int) folioBalance));
		cpReservationPage.clickOnDetails(driver);

	}

	private int getEmailMessageCount(String emailSubject) {
		try {
			Message[] messages = emailUtils.getMessagesBySubject(emailSubject, false, 100);
			int messageCount = messages == null ? 0 : messages.length;
			app_logs.info("========= Message Count " + messageCount + " ========");
			return messageCount;
		} catch (Exception e) {
			app_logs.info("========= Message Count " + 0 + " ========");
			return 0;
		}
	}

	private void validateNoEmailReceived(String emailSubject, String trigger, long timeOutInSeconds) {
		boolean emailReceived = emailUtils.newEmailReceived(test_steps, emailSubject, emailCount, timeOutInSeconds);
		if (emailReceived) {
			app_logs.info("Email should be received on none trigger " + trigger);
			test_steps.add("Assertion Email should be received on none trigger " + trigger);
		} else {
			app_logs.info("No email received on none trigger " + trigger);
			test_steps.add("No email received on none trigger " + trigger);
		}
	}

	private void validateEmail(String emailSubject, ArrayList<String> scenarioDataArray, String contentFields,
			String documentTemplateName, String otherFunctions, long timeOutInSeconds) throws Exception {
		boolean emailReceived = emailUtils.newEmailReceived(test_steps, emailSubject, emailCount, timeOutInSeconds);
		String emailAttachments = "";
		String trigger = scenarioDataArray.get(0).toUpperCase() + "_"
				+ scenarioDataArray.get(1).toUpperCase().replace(" ", "_");

		if (emailReceived) {
			app_logs.info("Email received " + trigger);
			test_steps.add("Email received " + trigger);
		} else {
			app_logs.info("No email received " + trigger);
			test_steps.add("Assertion No email received " + trigger);
		}

		if (!emailReceived && trigger.equalsIgnoreCase("ON_NO_SHOW")) {
			test_steps.add("Document Template existing issue email is not received on No Show <br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-10281' target='_blank'>NG-10281</a>");
		}

		String[] attachments = scenarioDataArray.get(2).split("\\*");
		ArrayList<String> attachmentArrayList = new ArrayList<>();

		if (emailReceived && contentFields.contains("ReservationDetailsLink")
				&& emailContentMap.get("ReservationDetailsLink").equals("")) {
			emailContentMap.put("ReservationDetailsLink",
					emailUtils.getReservationDetailLink(driver, emailSubject, reservationNumber));
		}

		for (String attachment : attachments) {
			if (!attachment.equals("")) {
				attachmentArrayList.add(attachment);
			}
		}

		if (otherFunctions.contains("Confirmation Letter")) {
			attachmentArrayList.add(documentTemplateName);
		}

		if (otherFunctions.contains("Guest Registration Form")) {
			attachmentArrayList.add("GuestRegistration");
		}

		if (otherFunctions.contains("Guest Statement")) {
			attachmentArrayList.add("GuestStatement");
		}

		if (emailReceived) {
			if (attachmentArrayList.size() > 0) {
				try {
					emailAttachments = emailUtils.verifyLatestEmailAttachments(emailSubject, false, 100);
					app_logs.info("Attachments present in email " + emailAttachments);
					test_steps.add("Attachments present in email " + emailAttachments);
					for (String attachment : attachmentArrayList) {
						if (attachment.equalsIgnoreCase("Guest Registration Form")) {
							attachment = "GuestRegistration";
						}
						attachment = attachment.replace(" ", "");
						if (!emailAttachments.contains(attachment)) {
							app_logs.info("Attachment not found " + attachment);
							test_steps.add("Assertion Attachment not found " + attachment);
						} else {
							app_logs.info("Attachment found " + attachment);
							test_steps.add("Attachment found " + attachment);
						}
					}
				} catch (Exception ignored) {
				}
			} else {
				emailAttachments = emailUtils.verifyLatestEmailAttachments(emailSubject, false, 100);
				if (emailAttachments.length() > 0) {
					app_logs.info("Document Template issue email contains attachment when there should be none <br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-9634' target='_blank'>NG-9634</a>");
					test_steps.add("Document Template issue email contains attachment when there should be none <br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-9634' target='_blank'>NG-9634</a>");
				} else {
					app_logs.info("Document Template email does not contains attachment and there should be none <br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-9634' target='_blank'>NG-9634</a>");
					test_steps.add("Document Template email does not contains attachment and there should be none <br>"
							+ "<a href='https://innroad.atlassian.net/browse/NG-9634' target='_blank'>NG-9634</a>");
				}
			}
		}

		if (emailReceived && contentFields != null && !contentFields.equals("")) {
			try {
				documentTemplate.verifyDocumentTemplate(driver, test_steps, emailUtils, contentFields, emailSubject,
						emailContentMap, "\\|", true);
			} catch (Exception e) {
				app_logs.info("Error while verifying email contentFields " + trigger + e.getMessage());
				test_steps.add("Error while verifying email contentFields " + trigger + e.getMessage());
			}
		}
	}

	private void validateManualEmail(String emailSubject, String attachment, String contentFields,
			String specialCharacters, long timeOutInSeconds) throws Exception {
		boolean emailReceived = emailUtils.newEmailReceived(test_steps, emailSubject, 0, timeOutInSeconds);
		boolean attachmentFound = false;
		String attachments = "";

		if (emailReceived) {
			app_logs.info("Manual Email received " + emailSubject);
			test_steps.add("Manual Email received " + emailSubject);
		} else {
			app_logs.info("No Manual Email received " + emailSubject);
			test_steps.add("Assertion No Manual Email received " + emailSubject);
		}

		if (emailReceived && contentFields.contains("ReservationDetailsLink")
				&& emailContentMap.get("ReservationDetailsLink").equals("")) {
			emailContentMap.put("ReservationDetailsLink",
					emailUtils.getReservationDetailLink(driver, emailSubject, reservationNumber));
		}

		if (emailReceived) {
			if (!attachment.equals("")) {
				try {
					ArrayList<String> attachmentArrayList = new ArrayList<>();
					attachmentArrayList.add(attachment);
					attachments = emailUtils.verifyLatestEmailAttachments(emailSubject, false, 100);
					attachmentFound = attachments.contains(attachments.replace(" ", ""));
				} catch (Exception ignored) {
				}
			} else {
				attachmentFound = true;
			}
		}

		if (!attachment.equals("")) {
			if (attachmentFound) {
				app_logs.info("Attachments found " + attachment);
				test_steps.add("Attachments found " + attachment);
			} else {
				app_logs.info("No attachments found " + attachment + " is not present in " + attachments);
				test_steps.add("Assertion No attachments found " + attachment + " is not present in " + attachments);
			}
		}

		if (emailReceived && contentFields != null && !contentFields.equals("")) {
			try {
				documentTemplate.verifyDocumentTemplate(driver, test_steps, emailUtils, contentFields, emailSubject,
						emailContentMap, "\\|", true);
			} catch (Exception e) {
				app_logs.info("Error while verifying manual email content " + emailSubject);
				test_steps.add("Error while verifying manual email content " + emailSubject);
			}
		}

		if (emailReceived && specialCharacters != null && !specialCharacters.equals("")) {
			try {
				Message[] messages = emailUtils.getMessagesBySubject(emailSubject, false, 100);
				if (emailUtils.isTextInMessage(messages[messages.length - 1], specialCharacters)) {
					app_logs.info("Manual email contains specialCharacters " + specialCharacters);
					test_steps.add("Manual email contains specialCharacters " + specialCharacters);
				} else {
					app_logs.info("Manual email does not contains specialCharacters " + specialCharacters);
					test_steps.add("Assertion Manual email does not contains specialCharacters " + specialCharacters);
				}
			} catch (Exception e) {
				app_logs.info("Error while verifying manual email specialCharacters " + emailSubject);
				test_steps.add("Error while verifying manual email specialCharacters " + emailSubject);
			}
		}
	}

	private String createReservation(WebDriver driver, ArrayList<String> test_steps, boolean isMRB,
			String contentFields, String manualEmailContentFields, String firstRatePlan, String firstRoomClass,
			String firstName, String lastName, String corporateAccount, String travelAgent, String marketSegment,
			String referral, String salutation, String country, String address1, String address2, String address3,
			String city, String state, String poBox, String phoneNumber, String emailAddress, String paymentType,
			String cardNumber, String cardName, String cardExpDate, String noteType, String noteSubject,
			String noteDescription, boolean setAccount) throws InterruptedException, ParseException, Exception {
		navigation.reservation(driver);
		cpReservationPage.click_NewReservation(driver, test_steps);
		test_steps.addAll(cpReservationPage.checkInDates(driver, +0, 0));
		test_steps.addAll(cpReservationPage.checkOutDates(driver, +3, 0));
		emailContentMap.put("NoOfRooms", "1");

		if (isMRB) {
			emailContentMap.put("NoOfRooms", "2");
			test_steps.addAll(cpReservationPage.clickAddARoom(driver));
			test_steps.addAll(cpReservationPage.checkInDates(driver, +0, 1));
			test_steps.addAll(cpReservationPage.checkOutDates(driver, +3, 1));
		}

		emailContentMap.put("ArrivalDate",
				LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		emailContentMap.put("BookedOn",
				LocalDate.now().plusDays(0).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		emailContentMap.put("DepartureDate",
				LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		emailContentMap.put("ReservationDetailsLink", "");
		emailContentMap.put("RoomCharges", "");
		emailContentMap.put("Incidentals", "");
		emailContentMap.put("TotalTaxes", "");
		emailContentMap.put("TotalCharges", "");
		emailContentMap.put("PaymentAmount", "");
		emailContentMap.put("FolioBalance", "");

		if (isMRB) {
			cpReservationPage.selectRateplanIndex(driver, firstRatePlan, "", 0, 0);
			cpReservationPage.selectRateplanIndex(driver, firstRatePlan, "", 1, 2);
		} else {
			cpReservationPage.select_Rateplan(driver, test_steps, firstRatePlan, "");
		}

		cpReservationPage.clickOnFindRooms(driver, test_steps);
		test_steps.addAll(cpReservationPage.selectRoom(driver, firstRoomClass, "Yes"));

		if (isMRB) {
			test_steps.addAll(cpReservationPage.selectRoom(driver, firstRoomClass, "Yes"));
		}

		cpReservationPage.clickNext(driver, test_steps);

		if (isMRB) {
			cpReservationPage.selectRoomNumberInMRB(driver, firstRoomClass, 0, 0);
			cpReservationPage.selectRoomNumberInMRB(driver, firstRoomClass, 0, 1);
			cpReservationPage.enterAdditionalGuestName(driver, firstName + "Add", lastName + "Add");
		}

		cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");

		if (contentFields.contains("accountName") || manualEmailContentFields.contains("accountName") || setAccount) {
			// cpReservationPage.selectAccount(driver, test_steps, corporateAccount, "No");
			cpReservationPage.selectAccountOnReservation(driver, test_steps, corporateAccount, "No", "No");
		}

		if (contentFields.contains("travelAgentName") || manualEmailContentFields.contains("travelAgentName")) {
			cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, travelAgent, marketSegment, referral,
					false);
		} else {
			cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral, false);
		}

		cpReservationPage.enter_GuestName(driver, test_steps, salutation, firstName, lastName);
		cpReservationPage.select_Country(driver, test_steps, country);
		cpReservationPage.enter_Address(driver, test_steps, address1, address2, address3);
		cpReservationPage.enter_City(driver, test_steps, city);
		cpReservationPage.select_State(driver, test_steps, state);
		cpReservationPage.enter_PostalCode(driver, test_steps, poBox);
		cpReservationPage.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
		cpReservationPage.enterEmail(driver, test_steps, emailAddress);
		cpReservationPage.enterPaymentDetails(driver, test_steps, paymentType, cardNumber, cardName, cardExpDate);

//        if (isMRB) {
//            cpReservationPage.AddNotes(driver, test_steps, "All", noteType, noteSubject, noteDescription);
//        } else {
//            cpReservationPage.enter_Notes(driver, test_steps, "Yes", noteType, noteSubject, noteDescription);
//        }

		cpReservationPage.clickBookNow(driver, test_steps);
		Wait.wait10Second();
		String reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
		emailContentMap.put("reservationStatus", "Reserved");
		cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
		return reservationNumber;
	}

	public void createDocumentTemplate(WebDriver driver, ArrayList<String> test_steps, String documentTemplateName,
			String templateDescription, String isDefaultTemplate, String sources, String propertyName,
			String otherFunctions, String content, String contentFields,
			HashMap<String, ArrayList<String>> scenariosToValidateMap) throws InterruptedException, Exception {
		test_steps.add("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
		app_logs.info("=================== CREATE NEW DOCUMENT TEMPLATE ======================");
		navigation.setup(driver);
		Wait.wait10Second();
		navigation.navigateDocumentTemplate(driver);
		Wait.wait10Second();
		documentTemplate.clickNewDocument(driver, test_steps);
		documentTemplate.enterDocumnetName(driver, documentTemplateName);
		documentTemplate.enterDocumnetDescription(driver, templateDescription, test_steps);
		if (!isDefaultTemplate.equalsIgnoreCase("NO")) {
			documentTemplate.select_DefaultTemplate(driver, test_steps);
		}

		test_steps.add("=================== ASSOCIATING SOURCES ======================");
		app_logs.info("=================== ASSOCIATING SOURCES ======================");
		if (sources.equals("")) {
			documentTemplate.associateSources(driver, test_steps);
		} else {
			documentTemplate.associateSources(driver, test_steps, sources);
		}

		test_steps.add("=================== ASSOCIATING PROPERTIES ======================");
		app_logs.info("=================== ASSOCIATING PROPERTIES ======================");
		documentTemplate.associateProperties(driver, propertyName, test_steps);

		test_steps.add("=================== ASSOCIATING FUNCTIONS ======================");
		app_logs.info("=================== ASSOCIATING FUNCTIONS ======================");
		for (String key : scenariosToValidateMap.keySet()) {
			ArrayList<String> functionData = scenariosToValidateMap.get(key);
			documentTemplate.addOrAssociateConfirmationEmailFunction(driver, functionData.get(0), functionData.get(1),
					functionData.get(2).split("\\*"), test_steps);
		}

		if (otherFunctions != null && !otherFunctions.equals("")) {
			String[] otherFunctionArray = otherFunctions.split("\\|");
			for (String otherFunction : otherFunctionArray) {
				documentTemplate.associateFunction(driver, otherFunction, test_steps);
			}
		}

		test_steps.add("=================== ADDING CONTENT ======================");
		app_logs.info("=================== ADDING CONTENT ======================");
		documentTemplate.click_Content(driver, test_steps);
		documentTemplate.enterHeadingInContentEditor(driver, content);
		String[] contentFieldArray = contentFields.split("\\|");
		for (String field : contentFieldArray) {
			if (!field.equals("")) {
				documentTemplate.enter_SpecificContentFields(driver, test_steps, field);
			}
		}

		if (contentFieldArray.length > 0) {
			app_logs.info("Document Templates-Text Editor working in Document Templates <br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-727' target='_blank'>NG-727</a>");
			test_steps.add("Document Templates-Text Editor working in Document Templates <br>"
					+ "<a href='https://innroad.atlassian.net/browse/NG-727' target='_blank'>NG-727</a>");
		}

		documentTemplate.saveDocument(driver, test_steps);
//            documentTemplate.DocumnetName(driver, documentTemplateName);
//            test_steps.add("Verified Document Template created <br>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-7220' target='_blank'>NG-7220</a>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-724' target='_blank'>NG-724</a>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-1267' target='_blank'>NG-1267</a>");
//            app_logs.info("Verified Document Template created <br>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-7220' target='_blank'>NG-7220</a>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-724' target='_blank'>NG-724</a>" +
//                    "<a href='https://innroad.atlassian.net/browse/NG-1267' target='_blank'>NG-1267</a>");
//		navigation.reservation(driver);
	}

	public HashMap<String, ArrayList<String>> getDefaultScenariosToValidateMap(ArrayList<String> events,
			ArrayList<String> attachmentList) {
		HashMap<String, ArrayList<String>> map = new HashMap<>();

		StringBuilder attachments = new StringBuilder();
		for (String attachment : attachmentList) {
			if (attachments.length() > 0) {
				attachments.append("\\*");
			}
			attachments.append(attachment);
		}

		for (String event : events) {
			String key = "ON" + "_" + event.toUpperCase();
			map.put(key, new ArrayList<>());
			map.get(key).add("On");
			map.get(key).add(event);
			map.get(key).add(attachments.toString());
		}
System.out.println(map);
		return map;
	}

	public void deleteAllDocumentTemplates(WebDriver driver) throws InterruptedException {
        app_logs.info("Removing Document Templates");
        test_steps.add("Removing Document Templates");
        navigation.setup(driver);
        Wait.wait10Second();
        navigation.navigateDocumentTemplate(driver);
        Wait.wait10Second();
        documentTemplate.deleteAllDocuments(driver);
        navigation.reservation(driver);
	}

	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyDocTemplateFunc", HS_EXCEL);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
//        driver.quit();
	}
}
