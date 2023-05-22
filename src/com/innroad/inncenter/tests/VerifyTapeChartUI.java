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

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VerifyTapeChartUI extends TestCore {
    private WebDriver driver = null;
    public static String test_name = "";
    public static String test_description = "";
    public static String test_catagory = "";
    private ArrayList<String> test_steps = new ArrayList<>();
    private Navigation navigation = new Navigation();
    private Tapechart tapeChart = new Tapechart();
    private CPReservationPage cpReservationPage = new CPReservationPage();
    private RatesGrid ratesGrid = new RatesGrid();
    private Admin admin = new Admin();
    private Users users = new Users();
    private NewRoomClassesV2 roomClass = new NewRoomClassesV2();
    private RoomStatus roomStatus = new RoomStatus();
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
        String promoCode = "";
        String season = "TapeChartSeason" + Utility.generateRandomStringWithGivenLength(5);
        String firstRoomClassRate = "100";

        boolean verifyRules = applyMinStayRule.equalsIgnoreCase("yes") ||
                applyCheckInRule.equalsIgnoreCase("yes") || applyCheckOutRule.equalsIgnoreCase("yes");

        String firstName = "Tape Chart";
        String lastName = "UI " + Utility.generateRandomString();
        String salutation = "Mr.";
//        String country = "United States";
//        String address1 = "Address # 1";
//        String address2 = "Address # 2";
//        String address3 = "Address # 3";
//        String city = "New York";
//        String state = "New York";
//        String poBox = "12345";
//        String phoneNumber = "(122) 222-2222";
//        String emailAddress = "innroadautomation@innroad.com";
        String marketSegment = "Internet";
        String referral = "Walk In";
//        String paymentType = "MC";
//        String cardNumber = "5454545454545454";
//        String cardName = "MC";
//        String cardExpDate = "12/29";
        String adultCount = "1";
        String childCount = "1";

        String inputDateFormat = "MM/dd/yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
        String defaultTimeZone;
        ZonedDateTime clientCurrentTime = ZonedDateTime.now();

        String reservationName = firstName + " " + lastName;
        int defaultTapeChartView = 7;

        ArrayList<String> sortedClassNameList;
        HashMap<String, ArrayList<String>> roomClassDetails;
        ArrayList<String> sortOrderList;
        HashMap<String, ArrayList<String>> groupedSortOrderMap;
        HashMap<String, String> nameAbbreviationMap = new HashMap<>();
        ArrayList<String> sortedAbbreviationList = new ArrayList<>();

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
            testName = "Verifying Tape Chart Start Date based on Client Time Zone";
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

            navigation.navigateToAdminPage(driver, test_steps);
            navigation.Users(driver);
            users.searchUser(driver, loginId);
            users.search(driver);
            users.selectUser(driver, test_steps, loginId);
            defaultTapeChartView = users.getDefaultTapeChartView(driver, test_steps);
            navigation.reservation(driver);

            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyTapeChartStartDateAndEndDate(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), inputDateFormat, defaultTapeChartView);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            test_steps.add("=================== NAVIGATE TO RATE GRID ======================");
            app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
            navigation.Inventory(driver, test_steps);
            navigation.RatesGrid(driver);
            test_steps.add("Navigated to RatesGrid");

            test_steps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
            app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
            ratesGrid.clickRateGridAddRatePlan(driver);
            ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
            nightlyRate.createNightlyRatePlan(driver, test_steps, ratePlan, channel, firstRoomClass, clientCurrentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    clientCurrentTime.plusDays(10).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), season,
                    firstRoomClassRate, "", "",
                    "", "", false, applyMinStayRule, minStayDuration,
                    applyCheckInRule, applyCheckOutRule, "");
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
            testName = "Verify Search UI";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.verifySearchUIFunctionality(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), inputDateFormat);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Refresh Button Functionality";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.searchInTapechart(driver, clientCurrentTime.format(dateTimeFormatter), clientCurrentTime.plusDays(1).format(dateTimeFormatter), adultCount, childCount, ratePlan, promoCode);
            tapeChart.verifyRefreshButtonFunctionality(driver, test_steps);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Unassigned Button Functionality";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.clickOnUIUnassignedButton(driver, test_steps);
            tapeChart.verifyUnassignedBox(driver, test_steps, true);
            tapeChart.clickOnUIUnassignedButton(driver, test_steps);
            tapeChart.verifyUnassignedBox(driver, test_steps, false);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Rate Plan Dropdown";
            navigation.Inventory(driver, test_steps);
            navigation.RatesGrid(driver);
            ratesGrid.clickRatePlanArrow(driver, test_steps);
            ArrayList<String> activeRatePlanList = ratesGrid.getRatePlanNames(driver, "active");
            navigation.inventoryToReservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.validateRatePlanDropDown(driver, test_steps, activeRatePlanList);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Date Range Functionality";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.verifyDateRangeFunctionality(driver, test_steps);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verifying Go To Date on Chart";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyGoToDateOnChartLink(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), inputDateFormat, defaultTapeChartView);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verifying Tape Chart default view on load";
            navigation.navigateToAdminPage(driver, test_steps);
            navigation.Users(driver);
            users.searchUser(driver, loginId);
            users.search(driver);
            users.selectUser(driver, test_steps, loginId);
            defaultTapeChartView = 30;
            users.setDefaultTapeChartView(driver, test_steps, defaultTapeChartView);
            users.saveUser(driver, test_steps);
            navigation.reservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyDefaultView(driver, test_steps, defaultTapeChartView);
            navigation.reservation(driver);
            navigation.navigateToAdminPage(driver, test_steps);
            navigation.Users(driver);
            users.searchUser(driver, loginId);
            users.search(driver);
            users.selectUser(driver, test_steps, loginId);
            defaultTapeChartView = 7;
            users.setDefaultTapeChartView(driver, test_steps, defaultTapeChartView);
            users.saveUser(driver, test_steps);
            navigation.reservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyDefaultView(driver, test_steps, defaultTapeChartView);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Tape Char Views";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.verifyPeriodDaysSelectionFunctionality(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), inputDateFormat);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify clicking on view triggers search";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.verifySearchIsTriggeredByClickingOnView(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), adultCount, ratePlan);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verifying all Option attributes are inherited";
            navigation.navigateToAdminPage(driver, test_steps);
            navigation.Users(driver);
            users.searchUser(driver, loginId);
            users.search(driver);
            users.selectUser(driver, test_steps, loginId);
            users.showFooter(driver, test_steps, false);
            users.showLegend(driver, test_steps, false);
            users.showRules(driver, test_steps, false);
            users.showRoomClassData(driver, test_steps, false);
            users.groupByRoomClass(driver, test_steps, false);
            users.showRoomCondition(driver, test_steps, false);
            users.saveUser(driver, test_steps);
            navigation.reservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.searchInTapechart(driver, clientCurrentTime.format(dateTimeFormatter), clientCurrentTime.plusDays(1).format(dateTimeFormatter),
                    adultCount, childCount, ratePlan, promoCode);
            tapeChart.validateTapeChartUIOptionsFunctionality(driver, test_steps, false, false, false,
                    false, false, false, verifyRules);
            navigation.reservation(driver);

            navigation.navigateToAdminPage(driver, test_steps);
            navigation.Users(driver);
            users.searchUser(driver, loginId);
            users.search(driver);
            users.selectUser(driver, test_steps, loginId);
            users.showFooter(driver, test_steps, true);
            users.showLegend(driver, test_steps, true);
            users.showRules(driver, test_steps, true);
            users.showRoomClassData(driver, test_steps, true);
            users.groupByRoomClass(driver, test_steps, true);
            users.showRoomCondition(driver, test_steps, true);
            users.saveUser(driver, test_steps);
            navigation.reservation(driver);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.searchInTapechart(driver, clientCurrentTime.format(dateTimeFormatter), clientCurrentTime.plusDays(1).format(dateTimeFormatter),
                    adultCount, childCount, ratePlan, promoCode);
            tapeChart.validateTapeChartUIOptionsFunctionality(driver, test_steps, true, true, true, true,
                    true, true, verifyRules);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
                Utility.updateReport(e, "Failed to click on available room", testName,
                        "SearchRoomInTapeChart", driver);
            } else {
                Assert.fail();
            }
        }

        try {
            testName = "Verify sort order of Room Classes with Group By enabled";
            navigation.navigateToSetupFromReservationPage(driver, test_steps);
            navigation.roomClass(driver, test_steps);

            sortedClassNameList = roomClass.getAllActiveRoomClassesListV2(driver, test_steps, "Active");
            roomClassDetails = roomClass.getRoomClassDetails(driver, test_steps);
            sortOrderList = roomClass.getSortOrderMap(driver, test_steps);
            groupedSortOrderMap = roomClass.getGroupBySortOrderMap(driver, test_steps);

            for(String key : sortedClassNameList) {
                nameAbbreviationMap.put(key, roomClassDetails.get(key).get(0));
                sortedAbbreviationList.add(roomClassDetails.get(key).get(0));
            }
            firstRoomClassAbbreviation = nameAbbreviationMap.get(firstRoomClass);

            navigation.navigateToReservation(driver);

            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.selectGroupByClass(driver, test_steps, true);
            tapeChart.verifyGroupedClassSortOrder(driver, test_steps, sortedClassNameList, nameAbbreviationMap, groupedSortOrderMap);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);

            testName = "Verify sort order of Room Classes with Group By disabled";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.selectGroupByClass(driver, test_steps, false);
            tapeChart.verifyClassSortOrder(driver, test_steps, sortOrderList);
            tapeChart.selectGroupByClass(driver, test_steps, true);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify clicking on Room Class Functionality";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyClickOnRoomClassFunctionality(driver, test_steps, firstRoomClassAbbreviation);
            navigation.navigateToReservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify room to be in Clean/Dirty/inspected states";
            navigation.clickOnGuestServices(driver, test_steps);
            ArrayList<String> dirtyRoomList = roomStatus.getDirtyRoomList(driver, test_steps);
            navigation.navigateToReservationFromGuestServices(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.verifyDirtyRoomStatus(driver, test_steps, nameAbbreviationMap, dirtyRoomList);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Room Class level statistics";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyRoomClassFooterValues(driver, test_steps, firstRoomClassAbbreviation, defaultTapeChartView);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify Property level statistics";
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.waitForReservationToLoad(driver, test_steps);
            tapeChart.verifyRoomAvailableTotalCount(driver, test_steps,  defaultTapeChartView);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify clicking on Unassigned text functionality";
            navigation.navigateToTapeChartFromReservations(driver);
            String checkInDate = clientCurrentTime.format(dateTimeFormatter);
            String checkOutDate = clientCurrentTime.plusDays(1).format(dateTimeFormatter);
            tapeChart.searchInTapechart(driver, checkInDate, checkOutDate, adultCount, childCount, ratePlan, promoCode);
            tapeChart.clickOnUnassignedTextForClassAbbreviation(driver, test_steps, firstRoomClassAbbreviation);
            tapeChart.verifyClickingOnUnassignedText(driver, test_steps, checkInDate, checkOutDate, inputDateFormat, firstRoomClass);
            cpReservationPage.closeAllOpenedReservations(driver);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify clicking on Rate text takes us to Rates Grid";
            navigation.navigateToTapeChartFromReservations(driver);
            String checkInDate = clientCurrentTime.format(dateTimeFormatter);
            String checkOutDate = clientCurrentTime.plusDays(1).format(dateTimeFormatter);
            tapeChart.searchInTapechart(driver, checkInDate, checkOutDate, adultCount, childCount, ratePlan, promoCode);
            if(tapeChart.verifyClickOnRateTextFunctionality(driver, test_steps, ratePlan)) {
                navigation.inventoryToReservation(driver);
            } else {
                navigation.reservation(driver);
            }
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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

        try {
            testName = "Verify clicking on unassigned reservation count functionality";
            firstName = "Unasgn";
            lastName = Utility.generateRandomStringWithGivenLength(6);
            reservationName = firstName + " " + lastName;
            navigation.navigateToTapeChartFromReservations(driver);
            tapeChart.searchInTapechart(driver, clientCurrentTime.format(dateTimeFormatter), clientCurrentTime.plusDays(1).format(dateTimeFormatter),
                    adultCount, childCount, ratePlan, promoCode);

            tapeChart.clickUnassignedButtonForRoomClass(driver, test_steps, firstRoomClassAbbreviation);
            cpReservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
            cpReservationPage.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
            cpReservationPage.enter_GuestName(driver, test_steps, salutation, firstName, lastName);
//            cpReservationPage.select_Country(driver, test_steps, country);
//            cpReservationPage.enter_Address(driver, test_steps, address1, address2, address3);
//            cpReservationPage.enter_City(driver, test_steps, city);
//            cpReservationPage.select_State(driver, test_steps, state);
//            cpReservationPage.enter_PostalCode(driver, test_steps, poBox);
//            cpReservationPage.enter_Phone(driver, test_steps, phoneNumber, phoneNumber);
//            cpReservationPage.enterEmail(driver, test_steps, emailAddress);
//            cpReservationPage.enterPaymentDetails(driver, test_steps, paymentType, cardNumber, cardName, cardExpDate);
            cpReservationPage.clickBookNow(driver, test_steps);
            reservationNumber = cpReservationPage.get_ReservationConfirmationNumber(driver, test_steps);
            cpReservationPage.clickCloseReservationSavePopup(driver, test_steps);
            navigation.navigateToTapeChartFromReservations(driver);
            driver.navigate().refresh();
            tapeChart.waitForReservationToLoad(driver, test_steps);

            tapeChart.searchInTapechart(driver, clientCurrentTime.format(dateTimeFormatter), clientCurrentTime.plusDays(1).format(dateTimeFormatter),
                    adultCount, childCount, ratePlan, promoCode);

            int unassignedCount = tapeChart.getUnassignedReservationCountForIndex(driver, test_steps, firstRoomClassAbbreviation, 2);
            tapeChart.clickOnUnassignedReservationCountForIndex(driver, test_steps, firstRoomClassAbbreviation, 2);
            tapeChart.verifyClickOnUnassignedReservationCountFunctionality(driver, test_steps, clientCurrentTime.format(dateTimeFormatter), inputDateFormat, unassignedCount);
            navigation.reservation(driver);
            Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
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
    }

    @DataProvider
    public Object[][] getData() {
        return Utility.getData("VerifyTapeChartFunc", excel);
    }

    @AfterClass(alwaysRun = true)
    public void closeDriver() {
//        driver.quit();
    }
}
