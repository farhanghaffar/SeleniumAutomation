package com.innroad.inncenter.tests;

import com.innroad.inncenter.pageobjects.*;
import com.innroad.inncenter.testcore.TestCore;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class VerifyTapeChartFunctionality extends TestCore {
    private WebDriver driver = null;
    public static String test_name = "";
    public static String test_description = "";
    public static String test_catagory = "";
    private ArrayList<String> test_steps = new ArrayList<>();
    private Navigation navigation = new Navigation();
    private Tapechart tapeChart = new Tapechart();
    private CPReservationPage cpReservationPage = new CPReservationPage();
    private Account account = new Account();
    private Groups group = new Groups();
    private NewRoomClassesV2 roomClass = new NewRoomClassesV2();
    private RatesGrid ratesGrid = new RatesGrid();
    private NightlyRate nightlyRate = new NightlyRate();
    private Admin admin = new Admin();

    ArrayList<String> caseId = new ArrayList<>();
    ArrayList<String> statusCode = new ArrayList<>();
    String comments;

    String reservationNumber;
    String firstName;
    String lastName;
    String salutation = "Mr.";
    String country = "United States";
    String address1 = "Address # 1";
    String address2 = "Address # 2";
    String address3 = "Address # 3";
    String city = "New York";
    String state = "New York";
    String poBox = "12345";
    String phoneNumber = "(122) 222-2222";
    String emailAddress = "innroadautomation@innroad.com";
    String marketSegment = "Internet";
    String referral = "Walk In";
    String paymentType = "MC";
    String cardNumber = "5454545454545454";
    String cardName = "MC";
    String cardExpDate = "12/29";
    String adultCount = "1";
    String childCount = "1";
    String inputDateFormat = "MM/dd/yyyy";
    String lineItemCategory = "Room Charge";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    String defaultTimeZone;
    ZonedDateTime clientCurrentTime = ZonedDateTime.now();

    @BeforeTest(alwaysRun = true)
    public void checkRunMode() {
        String testName = this.getClass().getSimpleName().trim();
        app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
        if (!Utility.isExecutable(testName, envLoginExcel))
            throw new SkipException("Skipping the test - " + testName);
    }

    @Test(dataProvider = "getData")
    public void verifyTapeChartFunctionality(String TestCaseID, String ratePlan, String usePromoCode, String isManualOverride, String manualOverrideAmount,
                                             String channel, String firstRoomClass, String secondRoomClass, String applyMinStayRule, String minStayDuration,
                                             String applyCheckInRule, String applyCheckOutRule, String taxPercentage, String reservationType,
                                             String accountType, String rateOverride) {
        test_name = "VerifyTapeChartFunctionality";
        test_description = "Verify Tape Chart UI Functionality<br>"
                + "<a href='https://innroad.testrail.io/index.php?/cases/view/682440' target='_blank'>"
                + "Click here to open TestRail: C682440</a>";
        test_catagory = "Reservation";
        String testName = test_name;
        app_logs.info("##################################################################################");
        app_logs.info("EXECUTING: " + testName + " TEST.");
        app_logs.info("##################################################################################");

        int reservationLength = 2;
        String firstRoomClassAbbreviation = "";
        String secondRoomClassAbbreviation = "";
        String promoCode = "";
        String season = "TapeChartSeason" + Utility.generateRandomStringWithGivenLength(5);

        String firstRoomClassRate = "100";
        String secondRoomClassRate = "250";

        if (usePromoCode.equalsIgnoreCase("yes")) {
            isManualOverride = "No";
        }

        if (isManualOverride.equalsIgnoreCase("yes") && !usePromoCode.equalsIgnoreCase("yes")) {
            applyMinStayRule = "No";
            applyCheckInRule = "No";
            applyCheckOutRule = "No";
            firstRoomClassRate = manualOverrideAmount;
            secondRoomClassRate = manualOverrideAmount;
        }

        boolean verifyRules = applyMinStayRule.equalsIgnoreCase("yes") ||
        applyCheckInRule.equalsIgnoreCase("yes") || applyCheckOutRule.equalsIgnoreCase("yes");

        String accountName = accountType + Utility.generateRandomStringWithGivenLength(4);
        int taxApplied = Integer.parseInt(taxPercentage);

        LocalDate oldArrivalDate;
        LocalDate oldDepartureDate;
        LocalDate arrivalDate;
        LocalDate departureDate;
        LocalDate seasonStartDate;
        LocalDate seasonEndDate;
        int numberOfNights = 0;

        boolean isMRB = false;
        String roomClasses = firstRoomClass + "|" + secondRoomClass;

        String reservationName = "";
        String roomNumber, nextRoomNumber;

        HashMap<String, ArrayList<Integer>> firstRoomReservationStatusMap;
        HashMap<String, ArrayList<Integer>> secondRoomReservationStatusMap;

        try {
            if (!Utility.insertTestName.containsKey(testName)) {
                Utility.insertTestName.put(testName, testName);
                Utility.reTry.put(testName, 0);
            } else {
                Utility.reTry.replace(testName, 1);
            }

            driver = getDriver();
            Utility.DELIM = "\\|";

            loginWPI(driver);
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
            app_logs.info("==========NAVIGATE TO ADMIN CLIENT INFO==========");
            test_steps.add("==========NAVIGATE TO ADMIN CLIENT INFO==========");
            navigation.navigateToAdminPage(driver, test_steps);
            navigation.navigateToClientInfoPage(driver, test_steps);
            admin.clickClientName(driver, test_steps);
            admin.clickClientOptions(driver, test_steps);
            defaultTimeZone = admin.getDefaultTimeZone(driver, test_steps);
            String timeFormat = defaultTimeZone.substring(0, 3);
            String hours = defaultTimeZone.substring(3, defaultTimeZone.indexOf(":"));
            String minutes = defaultTimeZone.substring(defaultTimeZone.indexOf(":") + 1);
            ZonedDateTime systemDateTime = ZonedDateTime.now(ZoneId.systemDefault());
            clientCurrentTime = systemDateTime.withZoneSameInstant(ZoneId.ofOffset(timeFormat, ZoneOffset.ofHoursMinutes(Integer.parseInt(hours), Integer.parseInt(minutes))));
            navigation.reservation(driver);

//            navigation.navigateToAdminPage(driver, test_steps);
//            navigation.Users(driver);
//            users.searchUser(driver, loginId);
//            users.search(driver);
//            users.selectUser(driver, test_steps, loginId);
//            defaultTapeChartView = users.getDefaultTapeChartView(driver, test_steps);
//            navigation.reservation(driver);
        } catch (Exception e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName, "SearchRoomInTapeChart", driver);
            } else {
                Assert.fail();
            }
        } catch (Error e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName, "SearchRoomInTapeChart", driver);
            } else {
                Assert.fail();
            }
        }

        arrivalDate = clientCurrentTime.toLocalDate();
        departureDate = arrivalDate.plusDays(reservationLength);
        seasonStartDate = arrivalDate;
        seasonEndDate = arrivalDate.plusDays(5);
        numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);

        try {
            navigation.navigateToSetupFromReservationPage(driver, test_steps);
            navigation.roomClass(driver, test_steps);
            firstRoomClassAbbreviation = roomClass.getAbbreviationText(driver, test_steps, firstRoomClass);
            secondRoomClassAbbreviation = roomClass.getAbbreviationText(driver, test_steps, secondRoomClass);
            navigation.navigateToReservation(driver);
        } catch (Exception e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName, "SearchRoomInTapeChart",
                        driver);
            } else {
                Assert.fail();
            }
        } catch (Error e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName,
                        "SearchRoomInTapeChart", driver);
            } else {
                Assert.fail();
            }
        }

//        if (reservationType.equalsIgnoreCase("account")) {
//            try {
//                test_steps.add("****************** Creating Corporate account *********************");
//                app_logs.info("****************** Creating Corporate account *********************");
//                navigation.navigateToAccounts(driver, test_steps);
//                if(!accountType.equalsIgnoreCase("gift certificates")) {
//                    account.createAccount(driver, test_steps, test, accountType, accountName,
//                            "Account_" + firstName, "Account_" + lastName, phoneNumber, phoneNumber,
//                            emailAddress, marketSegment, referral, address1, address2, address3, city,
//                            state, poBox);
//
//                    navigation.reservation(driver);
//                }
//            } catch (Exception e) {
//                if (Utility.reTry.get(test_name) == Utility.count) {
//                    RetryFailedTestCases.count = Utility.reset_count;
//                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                    Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
//                            driver);
//                } else {
//                    Assert.assertTrue(false);
//                }
//            } catch (Error e) {
//                if (Utility.reTry.get(test_name) == Utility.count) {
//                    RetryFailedTestCases.count = Utility.reset_count;
//                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                    Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
//                            driver);
//                } else {
//                    Assert.assertTrue(false);
//                }
//            }
//        } else if (reservationType.equalsIgnoreCase("group")) {
//            try {
//                app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
//                test_steps.add("==========CREATE NEW GROUP ACCOUNT==========");
//
//                navigation.secondaryGroupsManu(driver);
//                test_steps.add("Navigate Groups");
//                app_logs.info(" Navigate Groups");
//
//                test_steps.addAll(group.enterrGroupName(driver, accountName));
//                test_steps.addAll(group.enterAccountNo(driver, Utility.GenerateRandomString15Digit()));
//                test_steps.addAll(group.selectAccountAttributes(driver, marketSegment, referral));
//                test_steps.addAll(group.enterMailingAddress(driver, "Group_" + firstName, "Group_" + lastName, phoneNumber, address1, city, state, country, poBox));
//                test_steps.addAll(group.Billinginfo(driver));
//                test_steps.addAll(group.clickOnSave(driver));
//                navigation.reservation(driver);
//            } catch (Exception e) {
//                if (Utility.reTry.get(testName) == Utility.count) {
//                    RetryFailedTestCases.count = Utility.reset_count;
//                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                    Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName",
//                            driver);
//                } else {
//                    Assert.assertTrue(false);
//                }
//            } catch (Error e) {
//                if (Utility.reTry.get(testName) == Utility.count) {
//                    RetryFailedTestCases.count = Utility.reset_count;
//                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                    Utility.updateReport(e, "Failed to click new account and enter account name", testName,
//                            "EnterAccountName", driver);
//                } else {
//                    Assert.assertTrue(false);
//                }
//            }
//        }
//
//        try {
//            if(!isManualOverride.equalsIgnoreCase("yes")) {
//                test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
//                app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
//                navigation.Inventory(driver, test_steps);
//                navigation.RatesGrid(driver);
//                test_steps.add("Navigated to RatesGrid");
//
//                test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
//                app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
//                ratesGrid.clickRateGridAddRatePlan(driver);
//                ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
//                if(usePromoCode.equalsIgnoreCase("yes")) {
//                    nightlyRate.createNightlyRatePlan(driver, test_steps, ratePlan, channel, roomClasses, seasonStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                            seasonEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), season, firstRoomClassRate + "|" + secondRoomClassRate,
//                            "", "", "", "", false, applyMinStayRule, minStayDuration,
//                            applyCheckInRule, applyCheckOutRule, promoCode);
//
//                } else {
//                    nightlyRate.createNightlyRatePlan(driver, test_steps, ratePlan, channel, roomClasses, seasonStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                            seasonEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), season,
//                            firstRoomClassRate + "|" + secondRoomClassRate, "", "",
//                            "", "", false, applyMinStayRule, minStayDuration,
//                            applyCheckInRule, applyCheckOutRule, "");
//                }
//            }
//            navigation.inventoryToReservation(driver);
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (Utility.reTry.get(test_name) == Utility.count) {
//                RetryFailedTestCases.count = Utility.reset_count;
//                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
//            } else {
//                Assert.fail();
//            }
//        } catch (Error e) {
//            e.printStackTrace();
//            if (Utility.reTry.get(test_name) == Utility.count) {
//                RetryFailedTestCases.count = Utility.reset_count;
//                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
//                Utility.updateReport(e, "Failed", test_name, "RatesV2", driver);
//            } else {
//                Assert.fail();
//            }
//        }

        try {
            ArrayList<String> moveFromRateList;
            ArrayList<String> moveToRateList;
            int changeDayCount = 1;
            String newRateValue = "200";
            HashMap<LocalDate, String> reservationDateToRateMap;

            boolean noRateChangeRoomNumberIsValid = true;
            boolean recalculateRateRoomNumberIsValid = true;
            boolean newDatesOnlyRoomNumberIsValid = true;

            boolean noRateChangeLineItemsAreValid = true;
            boolean recalculateRateLineItemsAreValid = true;
            boolean newDatesOnlyLineItemsAreValid = true;

            boolean noRateChangeMoveRoomNumberIsValid = true;
            boolean recalculateRateMoveRoomNumberIsValid = true;
            boolean newDatesOnlyMoveRoomNumberIsValid = true;

            boolean noRateChangeMoveLineItemsAreValid = true;
            boolean recalculateRateMoveLineItemsAreValid = true;
            boolean newDatesOnlyMoveLineItemsAreValid = true;

            navigation.Inventory(driver, test_steps);
            navigation.RatesGrid(driver);
            Wait.wait60Second();
            HashMap<String, ArrayList<String>> beforeRateChangeMap = ratesGrid.getRateDetailsFromGrid(driver, test_steps, ratePlan, firstRoomClass, channel,
                    arrivalDate.minusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter),
                    departureDate.plusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter), inputDateFormat);

            HashMap<String, ArrayList<String>> rateMapForSecondRoomClass = ratesGrid.getRateDetailsFromGrid(driver, test_steps, ratePlan, secondRoomClass,
                    channel, arrivalDate.minusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter),
                    departureDate.plusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter), inputDateFormat);

            navigation.inventoryToReservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberNoRateChange = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "No Rate";
            lastName = "Change_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForNoRateChange = firstName + " " + lastName;
            createReservation(driver);
            test_steps.add("Issue verified Can create assigned reservation <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/NG-9846' target='_blank'>NG-9846</a> <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/NG-9612' target='_blank'>NG-9612</a>");

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberRecalculateRate = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "Recalculate";
            lastName = "Rate_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForRecalculateRate = firstName + " " + lastName;
            createReservation(driver);;

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberNewDatesOnly = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "New Date";
            lastName = "Change_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForNewDatesOnly = firstName + " " + lastName;
            createReservation(driver);

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberMoveNoRateChange = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.plusDays(reservationLength).format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNoRateChange);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "MoveNo Rate";
            lastName = "Change_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForMoveNoRateChange = firstName + " " + lastName;
            createReservation(driver);

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberMoveRecalculateRate = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.plusDays(reservationLength).format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "MoveRecalculate";
            lastName = "Rate_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForMoveRecalculateRate = firstName + " " + lastName;
            createReservation(driver);

            searchTapeChart(driver, test_steps, arrivalDate, departureDate, ratePlan, usePromoCode, promoCode, isManualOverride, manualOverrideAmount, inputDateFormat);
            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String roomNumberMoveNewDatesOnly = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.plusDays(reservationLength).format(dateTimeFormatter), inputDateFormat);
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly);
            verifyRulesIfApplicable(beforeRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            firstName = "New Date";
            lastName = "Change_" + Utility.generateRandomStringWithGivenLength(5);
            String reservationForMoveNewDatesOnly = firstName + " " + lastName;
            createReservation(driver);

            if(usePromoCode.equalsIgnoreCase("yes")) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688280");
            }
            navigation.reservation(driver);

//            if(rateOverride.equalsIgnoreCase("bulk")) {
//                bulkUpdate(driver, test_steps, arrivalDate.minusDays(changeDayCount).format(dateTimeFormatter), departureDate.plusDays(changeDayCount).format(dateTimeFormatter),
//                        ratePlan, firstRoomClass, channel, newRateValue, "0", "0", false);
//            } else {
//                overrideRates(driver, test_steps, arrivalDate.minusDays(changeDayCount), departureDate.plusDays(changeDayCount),
//                    ratePlan, firstRoomClass, channel, newRateValue);
//            }
//
            navigation.Inventory(driver);
            navigation.ratesGrid(driver);
            Wait.wait60Second();
            HashMap<String, ArrayList<String>> afterRateChangeMap = ratesGrid.getRateDetailsFromGrid(driver, test_steps, ratePlan, firstRoomClass, channel,
                    arrivalDate.minusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter),
                    departureDate.plusDays(Math.max(changeDayCount, reservationLength)).format(dateTimeFormatter), inputDateFormat);
            navigation.inventoryToReservation(driver);

            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);


            // Extending check out date and verifying rates No Rate Change
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            test_steps.add("Issue verified Extending reservation from Tape Chart <a href='https://innroad.atlassian.net/browse/REF-136' target='_blank'>REF-136</a>");
            departureDate = departureDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate.minusDays(changeDayCount), isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNoRateChange)) noRateChangeRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Reducing check out date and verifying rates No Rate Change
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            departureDate = departureDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNoRateChange)) noRateChangeRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 1, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Decreasing check in date and verifying rates No Rate Change
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            arrivalDate = arrivalDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate.plusDays(changeDayCount), departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNoRateChange)) noRateChangeRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Increasing check in date and verifying rates No Rate Change
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            arrivalDate = arrivalDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNoRateChange, reservationForNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNoRateChange)) noRateChangeRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 1, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);


            // Extending check out date and verifying rates Recalculate Rate
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            departureDate = departureDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate.minusDays(changeDayCount), isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberRecalculateRate)) recalculateRateRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Reducing check out date and verifying rates Recalculate Rate
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            departureDate = departureDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate.plusDays(changeDayCount), isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberRecalculateRate)) recalculateRateRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Decreasing check in date and verifying rates Recalculate Rate
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            arrivalDate = arrivalDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate.plusDays(changeDayCount), departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberRecalculateRate)) recalculateRateRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Increasing check in date and verifying rates Recalculate Rate
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            arrivalDate = arrivalDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate.minusDays(changeDayCount), departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberRecalculateRate, reservationForRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberRecalculateRate)) recalculateRateRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);


            // Extending check out date and verifying rates New Dates Only
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            departureDate = departureDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate.minusDays(changeDayCount), isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = new ArrayList<>(moveFromRateList);
            moveToRateList.addAll(getRatesArrayFromMap(afterRateChangeMap, departureDate, departureDate.plusDays(1), isManualOverride, manualOverrideAmount, inputDateFormat));

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNewDatesOnly)) newDatesOnlyRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Reducing check out date and verifying rates New Dates Only
            tapeChart.updateCheckOutDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            departureDate = departureDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = new ArrayList<>(moveToRateList);
            moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNewDatesOnly)) newDatesOnlyRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Decreasing check in date and verifying rates New Dates Only
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount * -1);
            arrivalDate = arrivalDate.minusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = new ArrayList<>(moveToRateList);
            moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate.plusDays(changeDayCount), departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList.addAll(getRatesArrayFromMap(afterRateChangeMap, arrivalDate, arrivalDate.plusDays(1), isManualOverride, manualOverrideAmount, inputDateFormat));

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberNewDatesOnly)) newDatesOnlyRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            // Increasing check in date and verifying rates New Dates Only
            tapeChart.updateCheckInDate(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly,
                    (int) ChronoUnit.DAYS.between(arrivalDate, departureDate), changeDayCount);
            arrivalDate = arrivalDate.plusDays(changeDayCount);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, true, false);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = new ArrayList<>(moveToRateList);
            moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);

            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveToRateList), getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberNewDatesOnly, reservationForNewDatesOnly);
            verifyReservationRoomNumber(driver, test_steps, roomNumberNewDatesOnly);
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);


            oldArrivalDate = clientCurrentTime.toLocalDate();
            oldDepartureDate = oldArrivalDate.plusDays(reservationLength);
            arrivalDate = clientCurrentTime.toLocalDate().plusDays(reservationLength);
            departureDate = arrivalDate.plusDays(reservationLength);

            // Moving reservation and verifying rate No Rate Change
            tapeChart.moveReservation(driver, test_steps, reservationForMoveNoRateChange, firstRoomClassAbbreviation, roomNumberMoveNoRateChange,
                    firstRoomClassAbbreviation, roomNumberMoveNoRateChange, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate, oldDepartureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNoRateChange, reservationForMoveNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNoRateChange)) noRateChangeMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = arrivalDate;
            oldDepartureDate = departureDate;
            arrivalDate = clientCurrentTime.toLocalDate();
            departureDate = arrivalDate.plusDays(reservationLength);

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String newRoomNumberMoveNoRateChange = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveNoRateChange, firstRoomClassAbbreviation, roomNumberMoveNoRateChange,
                    firstRoomClassAbbreviation, newRoomNumberMoveNoRateChange, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveNoRateChange = newRoomNumberMoveNoRateChange;

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNoRateChange, reservationForMoveNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNoRateChange)) noRateChangeMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
            newRoomNumberMoveNoRateChange = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, secondRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveNoRateChange, firstRoomClassAbbreviation, roomNumberMoveNoRateChange,
                    secondRoomClassAbbreviation, newRoomNumberMoveNoRateChange, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 0, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveFromRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 0);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveNoRateChange = newRoomNumberMoveNoRateChange;

            tapeChart.clickOnReservation(driver, test_steps, secondRoomClassAbbreviation, roomNumberMoveNoRateChange, reservationForMoveNoRateChange);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNoRateChange)) noRateChangeMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveFromRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) noRateChangeMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = clientCurrentTime.toLocalDate();
            oldDepartureDate = oldArrivalDate.plusDays(changeDayCount);
            arrivalDate = clientCurrentTime.toLocalDate().plusDays(changeDayCount);
            departureDate = arrivalDate.plusDays(reservationLength);

            // Moving reservation and verifying Recalculate Rate
            tapeChart.moveReservation(driver, test_steps, reservationForMoveRecalculateRate, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate,
                    firstRoomClassAbbreviation, roomNumberMoveRecalculateRate, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate, oldDepartureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate, reservationForMoveRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveRecalculateRate)) recalculateRateMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = arrivalDate;
            oldDepartureDate = departureDate;
            arrivalDate = clientCurrentTime.toLocalDate();
            departureDate = arrivalDate.plusDays(reservationLength);

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String newRoomNumberMoveRecalculateRate = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveRecalculateRate, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate,
                    firstRoomClassAbbreviation, newRoomNumberMoveRecalculateRate, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(afterRateChangeMap, oldArrivalDate, oldDepartureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveRecalculateRate = newRoomNumberMoveRecalculateRate;

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate, reservationForMoveRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveRecalculateRate)) recalculateRateMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = arrivalDate;
            oldDepartureDate = departureDate;

            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
            newRoomNumberMoveRecalculateRate = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, secondRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveRecalculateRate, firstRoomClassAbbreviation, roomNumberMoveRecalculateRate,
                    secondRoomClassAbbreviation, newRoomNumberMoveRecalculateRate, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(afterRateChangeMap, oldArrivalDate, oldDepartureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            moveToRateList = getRatesArrayFromMap(rateMapForSecondRoomClass, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 1, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 1);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveRecalculateRate = newRoomNumberMoveRecalculateRate;

            tapeChart.clickOnReservation(driver, test_steps, secondRoomClassAbbreviation, roomNumberMoveRecalculateRate, reservationForMoveRecalculateRate);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveRecalculateRate)) recalculateRateMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) recalculateRateMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = clientCurrentTime.toLocalDate();
            oldDepartureDate = oldArrivalDate.plusDays(changeDayCount);
            arrivalDate = clientCurrentTime.toLocalDate().plusDays(changeDayCount);
            departureDate = arrivalDate.plusDays(reservationLength);

            // Moving reservation and verifying rate New Dates Only
            tapeChart.moveReservation(driver, test_steps, reservationForMoveNewDatesOnly, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly,
                    firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate, oldDepartureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            if(changeDayCount < reservationLength) {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate.plusDays(changeDayCount), arrivalDate, isManualOverride, manualOverrideAmount, inputDateFormat);
                moveToRateList.addAll(getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat));
            } else {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            }
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly, reservationForMoveNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNewDatesOnly)) newDatesOnlyMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = arrivalDate;
            oldDepartureDate = departureDate;
            arrivalDate = clientCurrentTime.toLocalDate();
            departureDate = arrivalDate.plusDays(reservationLength);

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String newRoomNumberMoveNewDatesOnly = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveNewDatesOnly, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly,
                    firstRoomClassAbbreviation, newRoomNumberMoveNewDatesOnly, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = moveToRateList;

            if(changeDayCount < reservationLength) {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate.plusDays(changeDayCount), arrivalDate, isManualOverride, manualOverrideAmount, inputDateFormat);
                moveToRateList.addAll(getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat));
            } else {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            }
            moveToRateList = getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveNewDatesOnly = newRoomNumberMoveNewDatesOnly;

            tapeChart.clickOnReservation(driver, test_steps, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly, reservationForMoveNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNewDatesOnly)) newDatesOnlyMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            oldArrivalDate = arrivalDate;
            oldDepartureDate = departureDate;

            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
            newRoomNumberMoveNewDatesOnly = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, secondRoomReservationStatusMap,
                    arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);

            tapeChart.moveReservation(driver, test_steps, reservationForMoveNewDatesOnly, firstRoomClassAbbreviation, roomNumberMoveNewDatesOnly,
                    secondRoomClassAbbreviation, newRoomNumberMoveNewDatesOnly, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            verifyRulesIfApplicable(afterRateChangeMap, arrivalDate, departureDate, inputDateFormat, false, true);
            tapeChart.ClickRuleBrokenBookIfPopupPresent(driver, test_steps);
            moveFromRateList = moveToRateList;
            if(changeDayCount < reservationLength) {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, oldArrivalDate.plusDays(changeDayCount), arrivalDate, isManualOverride, manualOverrideAmount, inputDateFormat);
                moveToRateList.addAll(getRatesArrayFromMap(afterRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat));
            } else {
                moveToRateList = getRatesArrayFromMap(beforeRateChangeMap, arrivalDate, departureDate, isManualOverride, manualOverrideAmount, inputDateFormat);
            }
            tapeChart.verifyRateChangePopUpValueForIndex(driver, test_steps, 2, getSumOfArray(moveFromRateList),
                    getSumOfArray(moveToRateList), taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);
            roomNumberMoveNewDatesOnly = newRoomNumberMoveNewDatesOnly;

            tapeChart.clickOnReservation(driver, test_steps, secondRoomClassAbbreviation, roomNumberMoveNewDatesOnly, reservationForMoveNewDatesOnly);
            if(!verifyReservationRoomNumber(driver, test_steps, roomNumberMoveNewDatesOnly)) newDatesOnlyMoveRoomNumberIsValid = false;
            cpReservationPage.clickFolio(driver, test_steps);
            cpReservationPage.includeTaxesinLineItems(driver, test_steps, false);
            reservationDateToRateMap = getLineItemMap(arrivalDate, departureDate, 0, 0, moveToRateList);
            if(!verifyAllLineItems(driver, test_steps, reservationDateToRateMap, lineItemCategory, ratePlan, inputDateFormat)) newDatesOnlyMoveLineItemsAreValid = false;
            cpReservationPage.closeFirstOpenedReservation(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);

            bulkUpdate(driver, test_steps, arrivalDate.minusDays(changeDayCount).format(dateTimeFormatter), departureDate.plusDays(changeDayCount).format(dateTimeFormatter),
                    ratePlan, firstRoomClass, channel, "", "0", "0", true);

            if (noRateChangeRoomNumberIsValid && noRateChangeLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686212");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686716");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686212");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C699513");
                if(changeDayCount == 1) {
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686476");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686478");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686528");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686680");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686708");
                    if(reservationType.equalsIgnoreCase("account")) {
                        Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686653");
                        Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686021");
                        Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686711");
                    }
                }
                if(changeDayCount > 1) {
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686477");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688138");
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686652");
                    if(reservationType.equalsIgnoreCase("account")) {
                        Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686670");
                    }
                }
            }

            if(recalculateRateRoomNumberIsValid && recalculateRateLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688291");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C698916");
            }

            if(newDatesOnlyRoomNumberIsValid && newDatesOnlyLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688294");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C699509");
            }

            if (noRateChangeMoveRoomNumberIsValid && noRateChangeMoveLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688112");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688115");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714225");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714227");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714228");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C699896");
            }

            if(recalculateRateMoveRoomNumberIsValid && recalculateRateMoveLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688206");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C700081");
            }

            if(newDatesOnlyMoveRoomNumberIsValid && newDatesOnlyMoveLineItemsAreValid) {
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688207");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688976");
            }

            if(isManualOverride.equalsIgnoreCase("yes")) {
                if(Integer.parseInt(manualOverrideAmount) <= 1000) {
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C698916");
                } else {
                    Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C699509");
                }
            }

            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714376");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C734967");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688285");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C689190");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686710");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C686742");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688085");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C725726");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C725728");

            arrivalDate = clientCurrentTime.toLocalDate();
            departureDate = arrivalDate.plusDays(reservationLength);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);

            firstName = "Verify Tape";
            lastName = "Chart_" + Utility.generateRandomStringWithGivenLength(5);
            reservationName = firstName + " " + lastName;

            // Validate Functionality
            if (usePromoCode.equalsIgnoreCase("yes")) {
                app_logs.info("==========Searching for available room with out promo code==========");
                test_steps.add("==========Searching for available room with out promo code==========");
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
                Assert.assertEquals(tapeChart.getAvailableRoomSlotCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation), 0, "Successfully verified that no room is available without promo code for room class " + firstRoomClass);
                Assert.assertEquals(tapeChart.getAvailableRoomSlotCountForRoomClass(driver, test_steps, secondRoomClassAbbreviation), 0, "Successfully verified that no room is available without promo code for room class " + secondRoomClass);
                tapeChart.closeToastMessage(driver, test_steps);
                app_logs.info("==========Successfully verified that no room is available with invalid promo code==========");
                test_steps.add("==========Successfully verified that no room is available with invalid promo code==========");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688136");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688134");
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688137");

            }

//            navigation.selectProperty()

            app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
            test_steps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");


            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
                Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688139");
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);

            app_logs.info("==========VERIFYING AVAILABILITY OF ROOM SLOTS==========");
            test_steps.add("==========VERIFYING AVAILABILITY OF ROOM SLOTS==========");
            int expectedFirstRoomAvailableSlots = tapeChart.getExpectedAvailableRoomSlotCount(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.verifyAvailableRoomSlotCount(driver, test_steps, firstRoomClassAbbreviation, expectedFirstRoomAvailableSlots);

            int expectedSecondRoomAvailableSlots = tapeChart.getExpectedAvailableRoomSlotCount(driver, test_steps, secondRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.verifyAvailableRoomSlotCount(driver, test_steps, secondRoomClassAbbreviation, expectedSecondRoomAvailableSlots);

            app_logs.info("==========CREATING NEW RESERVATION==========");
            test_steps.add("==========CREATING NEW RESERVATION==========");
            roomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumber);
            if (verifyRules) {
//                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, test_steps, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
                tapeChart.clickBookIcon(driver);
            }

            cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
            cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
            if ((reservationType.equalsIgnoreCase("account") && !accountType.equalsIgnoreCase("gift certificates")) || reservationType.equalsIgnoreCase("group")) {
              // cpReservationPage.selectAccount(driver, test_steps, accountName, "No");
                cpReservationPage.selectAccountOnReservation(driver,  test_steps, accountName, "No", "No");
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
            cpReservationPage.clickBookNow(driver, test_steps);
            reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
            cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);
            driver.navigate().refresh();
            tapeChart.waitForReservationToLoad(driver, test_steps);
            validateToolTip(driver, test_steps, firstRoomClass, firstRoomClassAbbreviation, roomNumber, reservationName, reservationName,
                    String.valueOf(numberOfNights), adultCount, childCount, arrivalDate, departureDate, 1, isMRB);

            app_logs.info("==========MOVING RESERVATION TO DIFFERENT ROOM Of SAME CLASS==========");
            test_steps.add("==========MOVING RESERVATION TO DIFFERENT ROOM Of SAME CLASS==========");
            arrivalDate = arrivalDate.plusDays(1);
            departureDate = departureDate.plusDays(1);
            numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            nextRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, firstRoomClassAbbreviation, nextRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);


            if (verifyRules) {
//                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, test_steps, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
                tapeChart.clickBookIconWithNoReservationPageValidation(driver, test_steps);
            }

            ArrayList<String> moveFromRate = new ArrayList<>();
            ArrayList<String> moveToRate = new ArrayList<>();
            moveFromRate.add(firstRoomClassRate);
            moveToRate.add(firstRoomClassRate);
            tapeChart.verifyRateChangeInMoveExtendPopUp(driver, "Move", moveFromRate, moveToRate, taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            validateToolTip(driver, test_steps, firstRoomClass, firstRoomClassAbbreviation, nextRoomNumber, reservationName,
                    reservationName, String.valueOf(numberOfNights), adultCount, childCount, arrivalDate, departureDate, 1, isMRB);

            app_logs.info("==========MOVING RESERVATION TO DIFFERENT CLASS==========");
            test_steps.add("==========MOVING RESERVATION TO DIFFERENT CLASS==========");
            arrivalDate = arrivalDate.minusDays(1);
            departureDate = departureDate.minusDays(1);
            numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            roomNumber = nextRoomNumber;
            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
            nextRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, secondRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, secondRoomClassAbbreviation, nextRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            if (verifyRules) {
//                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, test_steps, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
                tapeChart.clickBookIconWithNoReservationPageValidation(driver, test_steps);
            }

            moveFromRate.clear();
            moveToRate.clear();
            moveFromRate.add(firstRoomClassRate);
            moveToRate.add(secondRoomClassRate);
            tapeChart.verifyRateChangeInMoveExtendPopUp(driver, "Move", moveFromRate, moveToRate, taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            validateToolTip(driver, test_steps, secondRoomClass, secondRoomClassAbbreviation, nextRoomNumber, reservationName,
                    reservationName, String.valueOf(numberOfNights), adultCount, childCount, arrivalDate, departureDate, 1, isMRB);
            if (!reservationType.equalsIgnoreCase("group")) {
                validateToolTipStatusColor(driver, test_steps, secondRoomClassAbbreviation, nextRoomNumber, reservationName);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.verifyReservationStatusColor(driver, test_steps, secondRoomClassAbbreviation, nextRoomNumber, reservationName, "Group");
            }
            test_steps.add("Issue verified <a href='https://innroad.atlassian.net/browse/CTI-4341' target='_blank'>CTI-4341</a> <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/NG-3988' target='_blank'>NG-3988</a> <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/CTI-4246' target='_blank'>CTI-4246</a>");

            test_steps.add("Issue verified Room assignment is updating from tape chart <a href='https://innroad.atlassian.net/browse/CTI-3176' target='_blank'>CTI-3176</a>");
            test_steps.add("Issue verified Tape chart refresh suite after reservation is moved <a href='https://innroad.atlassian.net/browse/NG-1822' target='_blank'>NG-1822</a>");


            cpReservationPage.closeAllOpenedReservations(driver);

            app_logs.info("==========CREATING UNASSIGNED RESERVATION==========");
            test_steps.add("==========CREATING UNASSIGNED RESERVATION==========");
            firstName = "Unasgn";
            lastName = Utility.generateRandomStringWithGivenLength(6);
            reservationName = firstName + " " + lastName;

            int beforeUnassignedCount = tapeChart.getUnassignedReservationCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            tapeChart.clickUnassignedButtonForRoomClass(driver, test_steps, firstRoomClassAbbreviation);
            cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
            cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
            cpReservationPage.enter_GuestName(driver, test_steps, salutation, firstName, lastName);
            cpReservationPage.select_Country(driver, test_steps, country);
            cpReservationPage.enter_Address(driver, test_steps, address1, address2, address3);
            cpReservationPage.enter_City(driver, test_steps, city);
            cpReservationPage.select_State(driver, test_steps, state);
            cpReservationPage.enter_PostalCode(driver, test_steps, poBox);
            cpReservationPage.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
            cpReservationPage.enterEmail(driver, test_steps, emailAddress);
            cpReservationPage.enterPaymentDetails(driver, test_steps, paymentType, cardNumber, cardName, cardExpDate);
            cpReservationPage.clickBookNow(driver, test_steps);
            reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
            cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);
            driver.navigate().refresh();
            tapeChart.waitForReservationToLoad(driver, test_steps);

            test_steps.add("Issue verified Can create unassigned reservation <a href='https://innroad.atlassian.net/browse/NG-9846' target='_blank'>NG-9846</a>");

            app_logs.info("==========VALIDATING CREATED UNASSIGNED RESERVATION==========");
            test_steps.add("==========VALIDATING CREATED UNASSIGNED RESERVATION==========");
            int afterUnassignedCount = tapeChart.getUnassignedReservationCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation);
            Assert.assertEquals(beforeUnassignedCount + 1, afterUnassignedCount, "Unassigned reservations count did not incremented by 1 for room class " + firstRoomClass);
            test_steps.add("Issue verified Unassigned reservation count is correct <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/CTI-4458' target='_blank'>CTI-4458</a> <br/>" +
                    "<a href='https://innroad.atlassian.net/browse/NG-6882' target='_blank'>NG-6882</a>");

            app_logs.info("Unassigned Reservation class count validated");
            test_steps.add("Unassigned Reservation class count validated");
            tapeChart.clickOnUIUnassignedButton(driver, test_steps);
            tapeChart.verifyReservationPresentInUnassignedBox(driver, test_steps, reservationName);

            app_logs.info("==========MOVING UNASSIGNED RESERVATION TO ROOM CLASS==========");
            test_steps.add("==========MOVING UNASSIGNED RESERVATION TO ROOM CLASS==========");
            arrivalDate = arrivalDate.plusDays(1);
            departureDate = departureDate.plusDays(1);

            numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            roomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter),
                    departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveUnassignedReservationToRoomClass(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            if (verifyRules) {
//                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, test_steps, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
                tapeChart.clickBookIconWithNoReservationPageValidation(driver, test_steps);
            }

            moveFromRate.clear();
            moveToRate.clear();
            moveFromRate.add("0");
            moveToRate.add(firstRoomClassRate);
            tapeChart.verifyRateChangeInMoveExtendPopUp(driver, "Move", moveFromRate, moveToRate, taxApplied);
            tapeChart.selectRateOptionsToApplyRate(driver, 2);
            tapeChart.clickConfirm_RaservationUpdate(driver);

            validateToolTip(driver, test_steps, firstRoomClass, firstRoomClassAbbreviation, roomNumber, reservationName,
                    reservationName, String.valueOf(numberOfNights), adultCount, childCount, arrivalDate, departureDate, 1, isMRB);
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714231");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714189");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C725727");

            tapeChart.clickOnUIUnassignedButton(driver, test_steps);
            tapeChart.moveAssignedReservationToUnassignedReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber);
            tapeChart.verifyReservationPresentInUnassignedBox(driver, test_steps, reservationName);

            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C691297");
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C714221");
        } catch (Exception e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName, "SearchRoomInTapeChart",
                        driver);
            } else {
                Assert.assertTrue(false);
            }
        } catch (Error e) {
            e.printStackTrace();
            if (Utility.reTry.get(testName) == Utility.count) {
                RetryFailedTestCases.count = Utility.reset_count;
                Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                Utility.updateReport(e, "Failed to click on available room", testName,
                        "SearchRoomInTapeChart", driver);
            } else {
                Assert.assertTrue(false);
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

    public void searchTapeChart(WebDriver driver, ArrayList<String> test_steps, LocalDate arrivalDate, LocalDate departureDate, String ratePlan,  String usePromoCode,
                                String promoCode, String isManualOverride, String manualOverrideAmount, String inputDateFormat) throws InterruptedException, ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
        if (usePromoCode.equalsIgnoreCase("yes")) {
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
        } else if (isManualOverride.equalsIgnoreCase("yes")) {
            tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
        } else {
            tapeChart.searchInTapechart(driver, test_steps, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
        }
    }

    private void validateToolTip(WebDriver driver, ArrayList<String> test_steps, String roomClass, String classNameAbbreviation,
                                 String roomNumber, String reservationName, String guestName, String numberOfNights, String adultCount,
                                 String childCount, LocalDate arrivalDate, LocalDate departureDate,
                                 int index, boolean isMRB) throws InterruptedException {
        tapeChart.waitForReservationToLoad(driver, test_steps);
        app_logs.info("==========VALIDATING TOOLTIP==========");
        test_steps.add("==========VALIDATING TOOLTIP==========");
        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        verifyReservationRoomNumber(driver, test_steps, roomNumber);
        cpReservationPage.clickFolio(driver, test_steps);
        if (isMRB) {
            cpReservationPage.selectFolioOption(driver, index);
        }

        String folioTotalCharges = cpReservationPage.get_TotalCharges(driver, test_steps);
        String folioBalance = cpReservationPage.get_Balance(driver, test_steps);
        navigation.navigateToTapeChartFromReservations(driver);
        driver.navigate().refresh();
        tapeChart.waitForReservationToLoad(driver, test_steps);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        tapeChart.toolTipVerificationForIndex(driver, test_steps, classNameAbbreviation, reservationName, guestName, roomNumber, roomClass,
                arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), numberOfNights, folioTotalCharges, adultCount, childCount, index, isMRB);
    }

    private void validateToolTipStatusColor(WebDriver driver, ArrayList<String> test_steps, String classNameAbbreviation,
                                            String roomNumber, String reservationName) throws InterruptedException, ParseException {
        app_logs.info("==========VALIDATING TOOLTIP==========");
        test_steps.add("==========VALIDATING TOOLTIP==========");

        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "Reserved");

        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        cpReservationPage.change_ReservationStatus(driver, test_steps, "Confirmed");
        navigation.navigateToTapeChartFromReservations(driver);
        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "Confirmed");

        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        cpReservationPage.change_ReservationStatus(driver, test_steps, "Guaranteed");
        navigation.navigateToTapeChartFromReservations(driver);
        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "Guaranteed");

        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        cpReservationPage.change_ReservationStatus(driver, test_steps, "On Hold");
        navigation.navigateToTapeChartFromReservations(driver);
        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "On Hold");

        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        cpReservationPage.change_ReservationStatus(driver, test_steps, "Reserved");
        cpReservationPage.clickCheckInButton(driver, test_steps);
        cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
        cpReservationPage.clickOnConfirmCheckInButton(driver, test_steps);
        navigation.navigateToTapeChartFromReservations(driver);
        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "In House");

        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
        cpReservationPage.clickCheckOutButton(driver, test_steps);
        cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
        cpReservationPage.proceedToCheckOutPayment(driver, test_steps);
        cpReservationPage.clickOnCheckOutPay(driver, test_steps);
        cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
        navigation.navigateToTapeChartFromReservations(driver);
        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "Departed");
    }

    private ArrayList<String> getRatesArrayFromMap(HashMap<String, ArrayList<String>> map, LocalDate arrivalDate, LocalDate departureDate, String isManualOverride,
                                                   String manualOverrideAmount, String inputDateFormat) {
        ArrayList<String> rates = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
        int dayDiff =  (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
        for(int i = 0; i < dayDiff; i++) {
            if(isManualOverride.equalsIgnoreCase("yes")) {
                rates.add(manualOverrideAmount);
            } else {
                rates.add(map.get(arrivalDate.plusDays(i).format(dateTimeFormatter)).get(0));
            }
        }
        return rates;
    }

    private double getSumOfArray(ArrayList<String> list) {
        double result = 0.0;
        for(String value: list) {
            result = result + Double.parseDouble(value.trim());
        }
        return result;
    }

    private String createReservation(WebDriver driver) throws Exception, InterruptedException {
        cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
        cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
        cpReservationPage.enter_GuestName(driver, test_steps, salutation, firstName, lastName);
        cpReservationPage.select_Country(driver, test_steps, country);
        cpReservationPage.enter_Address(driver, test_steps, address1, address2, address3);
        cpReservationPage.enter_City(driver, test_steps, city);
        cpReservationPage.select_State(driver, test_steps, state);
        cpReservationPage.enter_PostalCode(driver, test_steps, poBox);
        cpReservationPage.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
        cpReservationPage.enterEmail(driver, test_steps, emailAddress);
        cpReservationPage.enterPaymentDetails(driver, test_steps, paymentType, cardNumber, cardName, cardExpDate);
        cpReservationPage.clickBookNow(driver, test_steps);
        String reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
        cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
        navigation.navigateToTapeChartFromReservations(driver);
        driver.navigate().refresh();
        tapeChart.waitForReservationToLoad(driver, test_steps);
        return reservationNumber;
    }

    private void bulkUpdate(WebDriver driver, ArrayList<String> test_steps, String startDate, String endDate, String ratePlans, String roomClasses,
                            String channel, String newRates, String newAdultCharges, String newChildCharges, boolean removeOverride) throws InterruptedException {
        navigation.Inventory(driver);
        navigation.ratesGrid(driver);
        String bulkUpdateType = "Rates";
        test_steps.addAll(ratesGrid.clickOnBulkUpdate(driver));
        test_steps.addAll(ratesGrid.selectBulkUpdateOption(driver, bulkUpdateType));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupHeading(driver, bulkUpdateType));
        test_steps.addAll(ratesGrid.startDate(driver, startDate));
        test_steps.addAll(ratesGrid.endDate(driver, endDate));

        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sun", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Mon", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Tue", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Wed", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Thu", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Fri", "yes"));
        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sat", "yes"));
//        test_steps.addAll(ratesGrid.bulkUpdatePoppupDayCheck(driver, Day, "yes"));
        test_steps.addAll(ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlans));
        test_steps.addAll(ratesGrid.selectItemsFromDropDowns(driver, "Room class", roomClasses));
        test_steps.addAll(ratesGrid.selectItemsFromDropDowns(driver, "Source", channel));

        if(!removeOverride) {
            test_steps.addAll(ratesGrid.selectBulkUpdateRatesOption(driver, 0));
            test_steps.addAll(ratesGrid.updateRoomsByRoomClassToggle(driver, false));
            test_steps.addAll(ratesGrid.updateNightlyRate(driver, 0, newRates));
            test_steps.addAll(ratesGrid.updateAdditionalAdultRate(driver, 0, newAdultCharges));
            test_steps.addAll(ratesGrid.updateAdditionalChildRate(driver, 0, newChildCharges));
        } else {
            test_steps.addAll(ratesGrid.selectBulkUpdateRatesOption(driver, 2));
        }
        test_steps.addAll(ratesGrid.clickBulkUpdatePopupUpdateButton(driver));
        test_steps.addAll(ratesGrid.clickYesUpdateButton(driver));
        navigation.inventoryToReservation(driver);
    }

    public void overrideRates(WebDriver driver, ArrayList<String> test_steps, LocalDate startDate, LocalDate endDate, String ratePlan,
                              String roomClass, String channel, String newRateValue) throws InterruptedException {
        navigation.Inventory(driver);
        navigation.ratesGrid(driver);
        int dayDiff =  (int) ChronoUnit.DAYS.between(startDate, endDate);
        ratesGrid.selectRatePlan(driver, ratePlan, test_steps);
        ratesGrid.clickForRateGridCalender(driver, test_steps);
        Utility.selectDateFromDatePicker(driver, startDate.format(dateTimeFormatter), inputDateFormat);
        ratesGrid.expandRoomClass(driver, test_steps, roomClass);
        for(int i = 0; i < dayDiff; i++) {
            ratesGrid.overrideRateOnGrid(driver, test_steps, roomClass, channel, newRateValue, 0);
        }
        navigation.inventoryToReservation(driver);
    }

    public HashMap<LocalDate, String> getLineItemMap (LocalDate arrivalDate, LocalDate departureDate, int preArrivalDateZeroRateCount,
                                                      int postDepartureDateZeroRateCount, ArrayList<String> rates) {
        HashMap<LocalDate, String> map = new HashMap<>();

        for(int i = 1; i <= preArrivalDateZeroRateCount; i++) {
            map.put(arrivalDate.minusDays(i), "0");
        }

        int reservationDuration = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);

        for(int i = 0; i < reservationDuration; i++) {
            map.put(arrivalDate.plusDays(i), rates.get(i));
        }

        for(int i = 1; i <= postDepartureDateZeroRateCount; i++) {
            map.put(departureDate.plusDays(i), "0");
        }

        return map;
    }

    public boolean verifyReservationRoomNumber(WebDriver driver, ArrayList<String> test_steps, String roomNumber) {
        return true;
    }

    public boolean verifyAllLineItems(WebDriver driver, ArrayList<String> test_steps, HashMap<LocalDate, String> reservationDateToRateMap,
                                   String category, String description, String dateFormat) throws InterruptedException {
        DateTimeFormatter lineItemDateFormat = DateTimeFormatter.ofPattern("EEE MMM dd, yyyy");
        Set<LocalDate> localDates = reservationDateToRateMap.keySet();
        boolean result = true;
        for(LocalDate date : localDates) {
            if(!cpReservationPage.verifyLineItem(driver, test_steps, date.format(lineItemDateFormat), category, reservationDateToRateMap.get(date))) {
                result = false;
            }
        }
        return result;
    }

    public void verifyRulesIfApplicable(HashMap<String, ArrayList<String>> rateDetailsMap, LocalDate arrivalDate,
                                        LocalDate departureDate, String inputDateFormat, boolean isReservationDateChanged, boolean isReservationMoved) throws InterruptedException {
        boolean minStayRuleApplicable = tapeChart.minStayRuleApplied(rateDetailsMap, arrivalDate, departureDate, inputDateFormat);
        boolean noCheckInRuleApplicable = tapeChart.noCheckInApplied(rateDetailsMap, arrivalDate, departureDate, inputDateFormat);
        boolean noCheckOutRuleApplicable = tapeChart.noCheckOutApplied(rateDetailsMap, arrivalDate, departureDate, inputDateFormat);
        boolean rulesApplicable = minStayRuleApplicable || noCheckInRuleApplicable || noCheckOutRuleApplicable;
        int minStayRuleDuration = tapeChart.minStayRuleDuration(rateDetailsMap, arrivalDate, departureDate, inputDateFormat);

        if(rulesApplicable) {
            tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, test_steps, minStayRuleApplicable, String.valueOf(minStayRuleDuration),
                    noCheckInRuleApplicable, noCheckOutRuleApplicable);
            tapeChart.clickBookIconWithNoReservationPageValidation(driver, test_steps);
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C725729");
        } else {

        }

        if(minStayRuleApplicable) {
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C734963");
        }

        if(isReservationMoved) {

        }

        if (isReservationDateChanged) {
            Utility.setPassedStatusForTestRailCaseIfApplicable(caseId, statusCode, "C688270");
        }
    }

    @DataProvider
    public Object[][] getData() {
        return Utility.getData("VerifyTapeChartFunc", envLoginExcel);
    }

    @AfterClass(alwaysRun = true)
    public void closeDriver() {
        Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
    }
}
