package com.innroad.inncenter.tests;

import com.innroad.inncenter.pageobjects.*;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.time.LocalDate;
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
    private RoomClass roomClass = new RoomClass();
    private RatesGrid ratesGrid = new RatesGrid();
    private NightlyRate nightlyRate = new NightlyRate();
    String reservationNumber;

    @BeforeTest(alwaysRun = true)
    public void checkRunMode() {
        String testName = this.getClass().getSimpleName().trim();
        app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
        if (!Utility.isExecutable(testName, excel))
            throw new SkipException("Skipping the test - " + testName);
    }

    @Test(dataProvider = "getData")
    public void verifyTapeChartFunctionality(String usePromoCode, String isManualOverride, String manualOverrideAmount, String channel,
                                             String firstRoomClass, String firstRoomClassAbbreviation, String secondRoomClass,
                                             String secondRoomClassAbbreviation, String applyMinStayRule, String minStayDuration,
                                             String applyCheckInRule, String applyCheckOutRule, String taxPercentage, String reservationType,
                                             String accountType, String loginId) {
        test_name = "VerifyTapeChartFunctionality";
        test_description = "Verify Tape Chart UI Functionality<br>"
                + "<a href='https://innroad.testrail.io/index.php?/cases/view/682440' target='_blank'>"
                + "Click here to open TestRail: C682440</a>";
        test_catagory = "Reservation";
        String testName = test_name;
        app_logs.info("##################################################################################");
        app_logs.info("EXECUTING: " + testName + " TEST.");
        app_logs.info("##################################################################################");

        String ratePlan = "TapeChartPlan" + Utility.generateRandomStringWithGivenLength(5);
        String promoCode = "PromoCode" + Utility.generateRandomStringWithGivenLength(5);
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

        String firstName = "Tape Chart";
        String lastName = "Func" + Utility.generateRandomString();
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
        String accountName = accountType + Utility.generateRandomStringWithGivenLength(4);
        int taxApplied = Integer.parseInt(taxPercentage);

        //TODO Get timezone from properties / client
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(1);

        LocalDate seasonStartDate = LocalDate.now();
        LocalDate seasonEndDate = departureDate.plusDays(5);

        int numberOfNights = (int) ChronoUnit.DAYS.between(arrivalDate, departureDate);
        String inputDateFormat = "MM/dd/yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
        boolean isMRB = false;
        String roomClasses = firstRoomClass + "|" + secondRoomClass;

        String reservationName = firstName + " " + lastName;

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

        if (reservationType.equalsIgnoreCase("account")) {
            try {
                test_steps.add("****************** Creating Corporate account *********************");
                app_logs.info("****************** Creating Corporate account *********************");
                navigation.navigateToAccounts(driver, test_steps);
                if(!accountType.equalsIgnoreCase("gift certificates")) {
                    account.createAccount(driver, test_steps, test, accountType, accountName,
                            "Account_" + firstName, "Account_" + lastName, phoneNumber, phoneNumber,
                            emailAddress, marketSegment, referral, address1, address2, address3, city,
                            state, poBox);

                    navigation.reservation(driver);
                }
            } catch (Exception e) {
                if (Utility.reTry.get(test_name) == Utility.count) {
                    RetryFailedTestCases.count = Utility.reset_count;
                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                    Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
                            driver);
                } else {
                    Assert.assertTrue(false);
                }
            } catch (Error e) {
                if (Utility.reTry.get(test_name) == Utility.count) {
                    RetryFailedTestCases.count = Utility.reset_count;
                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                    Utility.updateReport(e, "Failed to Create Corporate Account", test_name, "CorporateAccount",
                            driver);
                } else {
                    Assert.assertTrue(false);
                }
            }
        } else if (reservationType.equalsIgnoreCase("group")) {
            try {
                app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
                test_steps.add("==========CREATE NEW GROUP ACCOUNT==========");

                navigation.secondaryGroupsManu(driver);
                test_steps.add("Navigate Groups");
                app_logs.info(" Navigate Groups");

                test_steps.addAll(group.enterrGroupName(driver, accountName));
                test_steps.addAll(group.enterAccountNo(driver, Utility.GenerateRandomString15Digit()));
                test_steps.addAll(group.selectAccountAttributes(driver, marketSegment, referral));
                test_steps.addAll(group.enterMailingAddress(driver, "Group_" + firstName, "Group_" + lastName, phoneNumber, address1, city, state, country, poBox));
                test_steps.addAll(group.Billinginfo(driver));
                test_steps.addAll(group.clickOnSave(driver));
                navigation.reservation(driver);
            } catch (Exception e) {
                if (Utility.reTry.get(testName) == Utility.count) {
                    RetryFailedTestCases.count = Utility.reset_count;
                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                    Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName",
                            driver);
                } else {
                    Assert.assertTrue(false);
                }
            } catch (Error e) {
                if (Utility.reTry.get(testName) == Utility.count) {
                    RetryFailedTestCases.count = Utility.reset_count;
                    Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
                    Utility.updateReport(e, "Failed to click new account and enter account name", testName,
                            "EnterAccountName", driver);
                } else {
                    Assert.assertTrue(false);
                }
            }
        }

        try {
            if(!isManualOverride.equalsIgnoreCase("yes")) {
                test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
                app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
                navigation.Inventory(driver, test_steps);
                navigation.RatesGrid(driver);
                test_steps.add("Navigated to RatesGrid");

                test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
                app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
                ratesGrid.clickRateGridAddRatePlan(driver);
                ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
                if(usePromoCode.equalsIgnoreCase("yes")) {
                    nightlyRate.createNightlyRatePlan(driver, test_steps, ratePlan, channel, roomClasses, seasonStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            seasonEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), season, firstRoomClassRate + "|" + secondRoomClassRate,
                            "", "", "", "", false, applyMinStayRule, minStayDuration,
                            applyCheckInRule, applyCheckOutRule, promoCode);

                } else {
                    nightlyRate.createNightlyRatePlan(driver, test_steps, ratePlan, channel, roomClasses, seasonStartDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            seasonEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), season,
                            firstRoomClassRate + "|" + secondRoomClassRate, "", "",
                            "", "", false, applyMinStayRule, minStayDuration,
                            applyCheckInRule, applyCheckOutRule, "");
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
//            HashMap<String, ArrayList<String>> beforeChangeMap = ratesGrid.getRateDetailsFromGrid(driver, test_steps, "ZIZRatePlan1", "N Room1", "innCenter", "11/02/2020", "11/20/2020", inputDateFormat);
//            String reservationForNoRateChange = createReservation();
//            String reservationRecalculateRate = createReservation();;
//            String reservationNewDatesOnly = createReservation();;
//
//            navigation.Inventory(driver, test_steps);
//            navigation.RatesGrid(driver);
//            // TODO Modify Rate
//            HashMap<String, ArrayList<String>> afterChangeMap = ratesGrid.getRateDetailsFromGrid(driver, test_steps, "ZIZRatePlan1", "N Room1", "innCenter", "11/02/2020", "11/20/2020", inputDateFormat);
//
//            navigation.inventoryToReservation(driver);
//            navigation.navigateToTapeChartFromReservations(driver);
//            tapeChart.waitForReservationToLoad(driver, test_steps);
//            tapeChart.updateCheckOutDate(driver, test_steps, "NR", "8", reservationForNoRateChange, 2, 1);

            HashMap<String, ArrayList<Integer>> firstRoomReservationStatusMap;
            HashMap<String, ArrayList<Integer>> secondRoomReservationStatusMap;

            // Validate Functionality
            if (usePromoCode.equalsIgnoreCase("yes")) {
                app_logs.info("==========Searching for available room with out promo code==========");
                test_steps.add("==========Searching for available room with out promo code==========");
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
                Assert.assertEquals(tapeChart.getAvailableRoomSlotCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation), 0, "Successfully verified that no room is available without promo code for room class " + firstRoomClass);
                Assert.assertEquals(tapeChart.getAvailableRoomSlotCountForRoomClass(driver, test_steps, secondRoomClassAbbreviation), 0, "Successfully verified that no room is available without promo code for room class " + secondRoomClass);
                tapeChart.closeToastMessage(driver, test_steps);
                app_logs.info("==========Successfully verified that no room is available without promo code==========");
                test_steps.add("==========Successfully verified that no room is available without promo code==========");
            }

            app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
            test_steps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");


            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
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
            String roomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.clickAvailableSlotWithOutRulePopupHandlingForRoomNumber(driver, test_steps, firstRoomClassAbbreviation, roomNumber);
            if (verifyRules) {
                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
                tapeChart.clickBookIcon(driver);
            }

            cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
            cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
            if ((reservationType.equalsIgnoreCase("account") && !accountType.equalsIgnoreCase("gift certificates")) || reservationType.equalsIgnoreCase("group")) {
                cpReservationPage.selectAccount(driver, test_steps, accountName, "No");
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
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            String nextRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, firstRoomClassAbbreviation, nextRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            if (verifyRules) {
                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
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
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            roomNumber = nextRoomNumber;
            secondRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, secondRoomClassAbbreviation, 7);
            nextRoomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, secondRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, secondRoomClassAbbreviation, nextRoomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            if (verifyRules) {
                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
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

            cpReservationPage.closeAllOpenedReservations(driver);

            app_logs.info("==========CREATING UNASSIGNED RESERVATION==========");
            test_steps.add("==========CREATING UNASSIGNED RESERVATION==========");
            firstName = "Unasgn";
            lastName = Utility.generateRandomStringWithGivenLength(6);
            reservationName = firstName + " " + lastName;

            int beforeUnassignedCount = tapeChart.getUnassignedReservationCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation);

            if (usePromoCode.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
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

            app_logs.info("==========VALIDATING CREATED UNASSIGNED RESERVATION==========");
            test_steps.add("==========VALIDATING CREATED UNASSIGNED RESERVATION==========");
            int afterUnassignedCount = tapeChart.getUnassignedReservationCountForRoomClass(driver, test_steps, firstRoomClassAbbreviation);
            Assert.assertEquals(beforeUnassignedCount + 1, afterUnassignedCount, "Unassigned reservations count did not incremented by 1 for room class " + firstRoomClass);
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
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            } else if (isManualOverride.equalsIgnoreCase("yes")) {
                tapeChart.searchInTapeChartManualOverride(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, manualOverrideAmount);
            } else {
                tapeChart.searchInTapechart(driver, arrivalDate.format(dateTimeFormatter), departureDate.format(dateTimeFormatter), adultCount, childCount, ratePlan, "");
            }

            firstRoomReservationStatusMap = tapeChart.getRoomAvailabilityMap(driver, test_steps, firstRoomClassAbbreviation, 7);
            roomNumber = tapeChart.getRoomNumberForFirstAvailableSlot(driver, test_steps, firstRoomReservationStatusMap, arrivalDate.format(dateTimeFormatter),
                    departureDate.format(dateTimeFormatter), inputDateFormat);
            tapeChart.moveUnassignedReservationToRoomClass(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber, arrivalDate.format(dateTimeFormatter), inputDateFormat);
            if (verifyRules) {
                tapeChart.verifyTapeChartBrokenRulesPopupUpdate(driver, applyMinStayRule, minStayDuration, applyCheckInRule, applyCheckOutRule);
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

            tapeChart.clickOnUIUnassignedButton(driver, test_steps);
            tapeChart.moveAssignedReservationToUnassignedReservation(driver, test_steps, reservationName, firstRoomClassAbbreviation, roomNumber);
            tapeChart.verifyReservationPresentInUnassignedBox(driver, test_steps, reservationName);

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

    private void validateToolTip(WebDriver driver, ArrayList<String> test_steps, String roomClass, String classNameAbbreviation,
                                 String roomNumber, String reservationName, String guestName, String numberOfNights, String adultCount,
                                 String childCount, LocalDate arrivalDate, LocalDate departureDate,
                                 int index, boolean isMRB) throws InterruptedException {
        tapeChart.waitForReservationToLoad(driver, test_steps);
        app_logs.info("==========VALIDATING TOOLTIP==========");
        test_steps.add("==========VALIDATING TOOLTIP==========");
        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);

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

//        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
//        cpReservationPage.change_ReservationStatus(driver, test_steps, "Reserved");
//        cpReservationPage.clickCheckInButton(driver, test_steps);
//        cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
//        cpReservationPage.clickOnConfirmCheckInButton(driver, test_steps);
//        navigation.navigateToTapeChartFromReservations(driver);
//        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "In House");
//
//        tapeChart.clickOnReservation(driver, test_steps, classNameAbbreviation, roomNumber, reservationName);
//        cpReservationPage.clickCheckOutButton(driver, test_steps);
//        cpReservationPage.disableGenerateGuestReportToggle(driver, test_steps);
//        cpReservationPage.proceedToCheckOutPayment(driver, test_steps);
//        cpReservationPage.clickOnCheckOutPay(driver, test_steps);
//        cpReservationPage.clickOnCheckOutSuccessfulCloseButton(driver, test_steps);
//        navigation.navigateToTapeChartFromReservations(driver);
//        tapeChart.verifyReservationStatusColor(driver, test_steps, classNameAbbreviation, roomNumber, reservationName, "Departed");
    }

    @DataProvider
    public Object[][] getData() {
        return Utility.getData("VerifyTapeChartFunc", excel);
    }

    @AfterClass(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }
}
