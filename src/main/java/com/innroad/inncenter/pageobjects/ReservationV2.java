package com.innroad.inncenter.pageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.cluster.ClusterState.Custom;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import com.innroad.inncenter.model.CancellationPaymentPopup;
import com.innroad.inncenter.model.CheckInPaymentPopup;
import com.innroad.inncenter.model.CheckOutSuccessPaymentPopup;
import com.innroad.inncenter.model.DateWiseRoomRate;
import com.innroad.inncenter.model.FindRoomDetail;
import com.innroad.inncenter.model.FolioLineItem;
import com.innroad.inncenter.model.GuestInfo;
import com.innroad.inncenter.model.HistoryInfo;
import com.innroad.inncenter.model.MarketingInfo;
import com.innroad.inncenter.model.PaymentInfo;
import com.innroad.inncenter.model.ReservationNotes;
import com.innroad.inncenter.model.ReservationPolicies;
import com.innroad.inncenter.model.ReservationRatesDetail;
import com.innroad.inncenter.model.ReservationStatusBar;
import com.innroad.inncenter.model.StayInfo;
import com.innroad.inncenter.model.TripSummary;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.properties.OR_ReservationV2;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_Accounts;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import com.innroad.inncenter.webelements.Elements_Reservation;
import com.innroad.inncenter.webelements.Elements_ReservationV2;
import com.innroad.inncenter.webelements.Elements_SetUp_Properties;

public class ReservationV2 {
	public static Logger logger = Logger.getLogger("ReservationV2");

	public ReservationStatusBar getStatusBarDetail(WebDriver driver) throws InterruptedException {
		ReservationStatusBar statusBar;
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait5Second();
		statusBar = new ReservationStatusBar(Utility.getELementText(ele.SB_GUEST_NAME, false, ""),
				Utility.getELementText(ele.SB_RESERVATION_STATUS, false, ""),
				Utility.getELementText(ele.SB_CONFIRMATION_NO, false, ""),
				Utility.getELementText(ele.SB_STAY_DURATION, false, ""),
				Utility.getELementText(ele.SB_PHONE_NO, false, ""), Utility.getELementText(ele.SB_EMAIL, false, ""),
				Utility.getELementText(ele.SB_TRIP_SUMMARAY, false, ""),
				Utility.getELementText(ele.SB_BALANCE, false, ""));
		return statusBar;
	}

	public void verifyStatusBarDetail(ReservationStatusBar acctualStatusBarDetail, String expectedGuestName,
			boolean verifyGuestName, boolean continueExeGuestName, String expectedReservationStatus,
			boolean verifyReservationStatus, boolean continueExeReservationStatus, String expectedConfirmationNo,
			boolean verifyConfirmationNo, boolean continueExeConfirmationNo, String expectedStayDuration,
			boolean verifyStayDuration, boolean continueExeStayDuration, String expectedPhoneNo, boolean verifyPhoneNo,
			boolean continueExePhoneNo, String expectedEmail, boolean verifyEmail, boolean continueExeEmail,
			String expectedTripSummary, boolean verifyTripSummary, boolean continueExeTripSummary,
			String expectedBalance, boolean verifyBalance, boolean continueExeBalance, ArrayList<String> test_steps) {

		Utility.customAssert(acctualStatusBarDetail.getSB_GUEST_NAME(), expectedGuestName, verifyGuestName,
				"Successfully Verified Status Bar Guest Name : " + expectedGuestName,
				"Failed To Verify Status Bar GuestName", continueExeGuestName, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_RESERVATION_STATUS(), expectedReservationStatus,
				verifyReservationStatus,
				"Successfully Verified Status Bar Reservation Status : " + expectedReservationStatus,
				"Failed To Verify Status Bar Reservation Status", continueExeReservationStatus, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_CONFIRMATION_NO(), expectedConfirmationNo,
				verifyConfirmationNo, "Successfully Verified Status Bar ConfirmationNo : " + expectedConfirmationNo,
				"Failed To Verify Status Bar ConfirmationNo", continueExeConfirmationNo, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_STAY_DURATION(), expectedStayDuration, verifyStayDuration,
				"Successfully Verified Status Bar StayDuration : " + expectedStayDuration,
				"Failed To Verify Status Bar StayDuration", continueExeStayDuration, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_PHONE_NO(), expectedPhoneNo, verifyPhoneNo,
				"Successfully Verified Status Bar PhoneNo : " + expectedPhoneNo, "Failed To Verify Status Bar PhoneNo",
				continueExePhoneNo, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_EMAIL(), expectedEmail, verifyEmail,
				"Successfully Verified Status Bar Email : " + expectedEmail, "Failed To Verify Status Bar Email",
				continueExeEmail, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_TRIP_SUMMARAY(), expectedTripSummary, verifyTripSummary,
				"Successfully Verified Status Bar TripSummary : " + expectedTripSummary,
				"Failed To Verify Status Bar TripSummary", continueExeTripSummary, test_steps);

		Utility.customAssert(acctualStatusBarDetail.getSB_BALANCE(), expectedBalance, verifyBalance,
				"Successfully Verified Status Bar Balance : " + expectedBalance, "Failed To Verify Status Bar Balance",
				continueExeBalance, test_steps);
	}

	public StayInfo getStayInfoDetail(WebDriver driver) throws InterruptedException {
		StayInfo statusInfo;
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		statusInfo = new StayInfo(Utility.getELementText(ele.SI_PROPERTY_NAME, false, ""),
				Utility.getELementText(ele.SI_RATE_PLAN_NAME, false, ""),
				Utility.getELementText(ele.SI_CHECK_IN_DATE, false, ""),
				Utility.getELementText(ele.SI_CHECK_OUT_DATE, false, ""),
				Utility.getELementText(ele.SI_NUMBER_OF_NIGHTS, false, ""),
				Utility.getELementText(ele.SI_NUMBER_OF_ADULTS, false, ""),
				Utility.getELementText(ele.SI_NUMBER_OF_CHILDS, false, ""),
				Utility.getELementText(ele.SI_ROOMCLASS_NAME, false, ""),
				Utility.getELementText(ele.SI_ROOM_NUMBER, false, ""),
				Utility.getELementText(ele.SI_ROOM_TOTAL, false, ""));
		return statusInfo;
	}

	public void verifyStayInfoDetail(StayInfo acctualStayInfoDetail, String expectedPropertyName,
			boolean verifyPropertyName, boolean continueExePropertyName, String expectedRatePlanName,
			boolean verifyRatePlanName, boolean continueExeRatePlanName, String expectedCheckInDate,
			boolean verifyCheckInDate, boolean continueExeCheckInDate, String expectedCheckOutDate,
			boolean verifyCheckOutDate, boolean continueExeCheckOutDate, String expectedNumberOfNights,
			boolean verifyNumberOfNights, boolean continueExeNumberOfNights, String expectedNumberOfAdults,
			boolean verifyNumberOfAdults, boolean continueExeNumberOfAdults, String expectedNumberOfChilds,
			boolean verifyNumberOfChilds, boolean continueExeNumberOfChilds, String expectedRoomClassName,
			boolean verifyRoomClassName, boolean continueExeRoomClassName, String expectedRoomNumber,
			boolean verifyRoomNumber, boolean continueExeRoomNumber, String expectedRoomTotal, boolean verifyRoomTotal,
			boolean continueExeRoomTotal, ArrayList<String> test_steps) {

		Utility.customAssert(acctualStayInfoDetail.getSI_PROPERTY_NAME(), expectedPropertyName, verifyPropertyName,
				"Successfully Verified Stay Info Property Name : " + expectedPropertyName,
				"Failed To Verify Stay Info Property Name", continueExePropertyName, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_RATE_PLAN_NAME(), expectedRatePlanName, verifyRatePlanName,
				"Successfully Verified Stay Info RatePlanName : " + expectedRatePlanName,
				"Failed To Verify Stay Info RatePlanName", continueExeRatePlanName, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_CHECK_IN_DATE(), expectedCheckInDate, verifyCheckInDate,
				"Successfully Verified Stay Info CheckInDate : " + expectedCheckInDate,
				"Failed To Verify Stay Info CheckInDate", continueExeCheckInDate, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_CHECK_OUT_DATE(), expectedCheckOutDate, verifyCheckOutDate,
				"Successfully Verified Stay Info CheckOutDate : " + expectedCheckOutDate,
				"Failed To Verify Stay Info CheckOutDate", continueExeCheckOutDate, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_NUMBER_OF_NIGHTS(), expectedNumberOfNights,
				verifyNumberOfNights, true,
				"Successfully Verified Stay Info NumberOfNights : " + expectedNumberOfNights,
				"Failed To Verify Stay Info NumberOfNights", continueExeNumberOfNights, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_NUMBER_OF_ADULTS(), expectedNumberOfAdults,
				verifyNumberOfAdults, "Successfully Verified Stay Info NumberOfAdults : " + expectedNumberOfAdults,
				"Failed To Verify Stay Info NumberOfAdults", continueExeNumberOfAdults, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_NUMBER_OF_CHILDS(), expectedNumberOfChilds,
				verifyNumberOfChilds, "Successfully Verified Stay Info NumberOfChilds : " + expectedNumberOfChilds,
				"Failed To Verify Stay Info NumberOfChilds", continueExeNumberOfChilds, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_ROOMCLASS_NAME(), expectedRoomClassName, verifyRoomClassName,
				"Successfully Verified Stay Info RoomClassName : " + expectedRoomClassName,
				"Failed To Verify Stay Info RoomClassName", continueExeRoomClassName, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_ROOM_NUMBER(), expectedRoomNumber, verifyRoomNumber,
				"Successfully Verified Stay Info RoomNumber : " + expectedRoomNumber,
				"Failed To Verify Stay Info RoomNumber", continueExeRoomNumber, test_steps);

		Utility.customAssert(acctualStayInfoDetail.getSI_ROOM_TOTAL(), expectedRoomTotal, verifyRoomTotal,
				"Successfully Verified Stay Info RoomTotal : " + expectedRoomTotal,
				"Failed To Verify Stay Info RoomTotal", continueExeRoomTotal, test_steps);
	}

	public GuestInfo getGuestInfoDetail(WebDriver driver) throws InterruptedException {

		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		GuestInfo guestInfo = new GuestInfo(Utility.getELementText(ele.GI_GUEST_NAME, false, ""),
				Utility.getELementText(ele.GI_CONTACT_NAME, false, ""), Utility.getELementText(ele.GI_EMAIL, false, ""),
				Utility.getELementText(ele.GI_PHONE_NO, false, ""),
				Utility.getELementText(ele.GI_ALTERNATE_PHONE, false, ""),
				Utility.getELementText(ele.GI_ADDRESS, false, ""), Utility.getELementText(ele.GI_CITY, false, ""),
				Utility.getELementText(ele.GI_STATE, false, ""), Utility.getELementText(ele.GI_POSTAL_CODE, false, ""),
				Utility.getELementText(ele.GI_COUNTRY, false, ""),
//				Utility.getELementText(ele.GI_FAX, false, ""), 
				Utility.getELementText(ele.GI_ACCOUNT, false, ""));
		return guestInfo;
	}

	public void verifyGuestInfoDetail(GuestInfo acctualGuestInfoDetail, String expectedGuestName,
			boolean verifyGuestName, boolean continueExeGuestName, String expectedContactName,
			boolean verifyContactName, boolean continueExeContactName, String expectedEmail, boolean verifyEmail,
			boolean continueExeEmail, String expectedPhoneNo, boolean verifyPhoneNo, boolean continueExePhoneNo,
			String expectedAlternatePhone, boolean verifyAlternatePhone, boolean continueExeAlternatePhone,
			String expectedAddress, boolean verifyAddress, boolean continueExeAddress, String expectedCity,
			boolean verifyCity, boolean continueExeCity, String expectedState, boolean verifyState,
			boolean continueExeState, String expectedPostalCode, boolean verifyPostalCode,
			boolean continueExePostalCode, String expectedCountry, boolean verifyCountry, boolean continueExeCountry,
			String expectedFax, boolean verifyFax, boolean continueExeFax, String expectedAccount,
			boolean verifyAccount, boolean continueExeAccount, ArrayList<String> test_steps) {

		Utility.customAssert(acctualGuestInfoDetail.getGI_GUEST_NAME(), expectedGuestName, verifyGuestName,
				"Successfully Verified Guest Info GuestName : " + expectedGuestName,
				"Failed To Verify Guest Info GuestName", continueExeGuestName, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_CONTACT_NAME(), expectedContactName, verifyContactName,
				"Successfully Verified Guest Info ContactName : " + expectedContactName,
				"Failed To Verify Guest Info ContactName", continueExeContactName, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_EMAIL(), expectedEmail, verifyEmail,
				"Successfully Verified Guest Info Email : " + expectedEmail, "Failed To Verify Guest Info Email",
				continueExeEmail, test_steps);
		// 091-(123) 456-7899 ( ext 3357 )
		Utility.customAssert(
				acctualGuestInfoDetail.getGI_PHONE_NO().replace("ext", "").replace(" ", "").replace("(", "")
						.replace(")", "").replace("-", ""),
				expectedPhoneNo, verifyPhoneNo, "Successfully Verified Guest Info PhoneNo : " + expectedPhoneNo,
				"Failed To Verify Guest Info PhoneNo", continueExePhoneNo, test_steps);

		Utility.customAssert(
				acctualGuestInfoDetail.getGI_ALTERNATE_PHONE().replace("ext", "").replace(" ", "").replace("(", "")
						.replace(")", "").replace("-", ""),
				expectedAlternatePhone, verifyAlternatePhone,
				"Successfully Verified Guest Info AlternatePhone : " + expectedAlternatePhone,
				"Failed To Verify Guest Info AlternatePhone", continueExeAlternatePhone, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_ADDRESS(), expectedAddress, verifyAddress,
				"Successfully Verified Guest Info Address : " + expectedAddress, "Failed To Verify Guest Info Address",
				continueExeAddress, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_CITY(), expectedCity, verifyCity,
				"Successfully Verified Guest Info City : " + expectedCity, "Failed To Verify Guest Info City",
				continueExeCity, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_STATE(), expectedState, verifyState,
				"Successfully Verified Guest Info State : " + expectedState, "Failed To Verify Guest Info State",
				continueExeState, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_POSTAL_CODE(), expectedPostalCode, verifyPostalCode,
				"Successfully Verified Guest Info PostalCode : " + expectedPostalCode,
				"Failed To Verify Guest Info PostalCode", continueExePostalCode, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_COUNTRY(), expectedCountry, verifyCountry,
				"Successfully Verified Guest Info Country : " + expectedCountry, "Failed To Verify Guest Info Country",
				continueExeCountry, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_FAX(), expectedFax, verifyFax,
				"Successfully Verified Guest Info Fax : " + expectedFax, "Failed To Verify Guest Info Fax",
				continueExeFax, test_steps);

		Utility.customAssert(acctualGuestInfoDetail.getGI_ACCOUNT(), expectedAccount, verifyAccount,
				"Successfully Verified Guest Info Account : " + expectedAccount, "Failed To Verify Guest Info Account",
				continueExeAccount, test_steps);
	}

	public TripSummary getTripSummaryDetail(WebDriver driver) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		TripSummary tripSummary = new TripSummary(Utility.getELementText(ele.TS_ROOM_CHARGE, false, ""),
				Utility.getELementText(ele.TS_STAY_DATE_RANGE, false, ""),
				Utility.getELementText(ele.TS_OCCUPANTS, false, ""),
				Utility.getELementText(ele.TS_ROOM_DETAIL, false, ""),
				Utility.getELementText(ele.TS_INCIDENTALS, false, ""), Utility.getELementText(ele.TS_FEES, false, ""),
				Utility.getELementText(ele.TS_TAXES, false, ""), Utility.getELementText(ele.TS_TRIP_TOTAL, false, ""),
				Utility.getELementText(ele.TS_PAID, false, ""), Utility.getELementText(ele.TS_BALANCE, false, ""));
		return tripSummary;
	}

	public void verifyTripSummaryDetail(TripSummary acctualTripSummaryDetail, String expectedRoomCharge,
			boolean verifyRoomCharge, boolean continueExeRoomCharge, String expectedStayDateRange,
			boolean verifyStayDateRange, boolean continueExeStayDateRange, String expectedOccupants,
			boolean verifyOccupants, boolean continueExwOccupants, String expectedRoomDetail, boolean verifyRoomDetail,
			boolean continueExeRoomDetail, String expectedIncidentals, boolean verifyIncidentals,
			boolean continueExeIncidentals, String expectedFees, boolean verifyFees, boolean continueExeFees,
			String expectedTaxes, boolean verifyTaxes, boolean continueExeTaxes, String expectedTripTotal,
			boolean verifyTripTotal, boolean continueExeTripTotal, String expectedPaid, boolean verifyPaid,
			boolean continueExePaid, String expectedBalance, boolean verifyBalance, boolean continueExeBalance,

			ArrayList<String> test_steps) {

		Utility.customAssert(
				Double.parseDouble(
						Utility.removeCurrencySignBracketsAndSpaces(acctualTripSummaryDetail.getTS_ROOM_CHARGE())) + "",
				Double.parseDouble(expectedRoomCharge) + "", verifyRoomCharge,
				"Successfully Verified Trip Summary RoomCharge : " + expectedRoomCharge,
				"Failed To Verify Trip Summary RoomCharge", continueExeRoomCharge, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_STAY_DATE_RANGE(), expectedStayDateRange,
				verifyStayDateRange, "Successfully Verified Trip Summary DateRange : " + expectedStayDateRange,
				"Failed To Verify Trip Summary DateRange", continueExeStayDateRange, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_OCCUPANTS(), expectedOccupants, verifyOccupants,
				"Successfully Verified Trip Summary Occupants : " + expectedOccupants,
				"Failed To Verify Trip Summary Occupants", continueExwOccupants, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_ROOM_DETAIL(), expectedRoomDetail, verifyRoomDetail,
				"Successfully Verified Trip Summary RoomDetail : " + expectedRoomDetail,
				"Failed To Verify Trip Summary RoomDetail", continueExeRoomDetail, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_INCIDENTALS(), expectedIncidentals, verifyIncidentals,
				"Successfully Verified Trip Summary Incidentals : " + expectedIncidentals,
				"Failed To Verify Trip Summary Incidentals", continueExeIncidentals, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_FEES(), expectedFees, verifyFees,
				"Successfully Verified Trip Summary Fees : " + expectedFees, "Failed To Verify Trip Summary Fees",
				continueExeFees, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_TAXES(), expectedTaxes, verifyTaxes,
				"Successfully Verified Trip Summary Taxes : " + expectedTaxes, "Failed To Verify Trip Summary Taxes",
				continueExeTaxes, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_TRIP_TOTAL(), expectedTripTotal, verifyTripTotal,
				"Successfully Verified Trip Summary TripTotal : " + expectedTripTotal,
				"Failed To Verify Trip Summary TripTotal", continueExeTripTotal, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_PAID(), expectedPaid, verifyPaid,
				"Successfully Verified Trip Summary Paid : " + expectedPaid, "Failed To Verify Trip Summary Paid",
				continueExePaid, test_steps);

		Utility.customAssert(acctualTripSummaryDetail.getTS_BALANCE(), expectedBalance, verifyBalance,
				"Successfully Verified Trip Summary Balance : " + expectedBalance,
				"Failed To Verify Trip Summary Balance", continueExeBalance, test_steps);

	}

	public PaymentInfo getPaymentInfo(WebDriver driver) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		PaymentInfo paymentInfo = new PaymentInfo(Utility.getELementText(ele.PI_BILLING_TYPE_NAME, false, ""),
				Utility.getELementText(ele.PI_LAST_FOUR_DIGITS, false, ""),
				Utility.getELementText(ele.PI_NAME_ON_CARD, false, ""),
				Utility.getELementText(ele.PI_EXPIRY_DATE, false, ""));
		return paymentInfo;
	}

	public void verifyPaymentInfoDetail(PaymentInfo acctualPaymentInfoDetail, String expectedBillingTypeName,
			boolean verifyBillingTypeName, boolean continueExeBillingTypeName, String expectedLastFourDigits,
			boolean verifyLastFourDigits, boolean continueExeLastFourDigits, String expectedNameOnCard,
			boolean verifyNameOnCard, boolean continueExeNameOnCard, String expectedExpiryDate,
			boolean verifyExpiryDate, boolean continueExeExpiryDate,

			ArrayList<String> test_steps) {

		Utility.customAssert(acctualPaymentInfoDetail.getPI_BILLING_TYPE_NAME(), expectedBillingTypeName,
				verifyBillingTypeName,
				"Successfully Verified Payment Info BillingTypeName : " + expectedBillingTypeName,
				"Failed To Verify Payment Info BillingTypeName", continueExeBillingTypeName, test_steps);

		Utility.customAssert(acctualPaymentInfoDetail.getPI_LAST_FOUR_DIGITS(),
				expectedLastFourDigits.substring(expectedLastFourDigits.length() - 4), verifyLastFourDigits,
				"Successfully Verified Payment Info LastFourDigits : " + expectedLastFourDigits,
				"Failed To Verify Payment Info LastFourDigits", continueExeLastFourDigits, test_steps);

		Utility.customAssert(acctualPaymentInfoDetail.getPI_NAME_ON_CARD(), expectedNameOnCard, verifyNameOnCard,
				"Successfully Verified Payment Info NameOnCard : " + expectedNameOnCard,
				"Failed To Verify Payment Info NameOnCard", continueExeNameOnCard, test_steps);

		Utility.customAssert(acctualPaymentInfoDetail.getPI_EXPIRY_DATE(), expectedExpiryDate, verifyExpiryDate,
				"Successfully Verified Payment Info ExpiryDate : " + expectedExpiryDate,
				"Failed To Verify Payment Info ExpiryDate", continueExeExpiryDate, test_steps);

	}

	public MarketingInfo getMarketingInfoDetail(WebDriver driver) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		MarketingInfo paymentInfo = new MarketingInfo(Utility.getELementText(ele.MI_MARKET_SEGMENT, false, ""),
				Utility.getELementText(ele.MI_MARKET_REFERRAL, false, ""),
				Utility.getELementText(ele.MI_EXTERNAL_CONFIRMATION_NUMBER, false, ""),
				Utility.getELementText(ele.MI_MARKET_SOURCE, false, ""),
				Utility.getELementText(ele.MI_MARKET_SUB_SOURCE, false, ""));
		return paymentInfo;
	}

	public void verifyMarketingInfoDetail(MarketingInfo acctualMarketingInfoDetail, String expectedMarketSegment,
			boolean verifyMarketSegment, boolean continueExeMarketSegment, String expectedMarketReferal,
			boolean verifyMarketReferal, boolean continueExeMarketReferal, String expectedExternalConfirmationNumber,
			boolean verifyExternalConfirmationNumber, boolean continueExeExternalConfirmationNumber,
			String expectedMarketSource, boolean verifyMarketSource, boolean continueExeMarketSource,
			String expectedMarketSubSource, boolean verifyMarketSubSource, boolean continueExeMarketSubSource,

			ArrayList<String> test_steps) {

		Utility.customAssert(acctualMarketingInfoDetail.getMI_MARKET_SEGMENT(), expectedMarketSegment,
				verifyMarketSegment, "Successfully Verified Marketing Info MarketSegment : " + expectedMarketSegment,
				"Failed To Verify Marketing Info MarketSegment", continueExeMarketSegment, test_steps);

		Utility.customAssert(acctualMarketingInfoDetail.getMI_MARKET_REFERRAL(), expectedMarketReferal,
				verifyMarketReferal, "Successfully Verified Marketing Info MarketReferal : " + expectedMarketReferal,
				"Failed To Verify Marketing Info MarketReferal", continueExeMarketReferal, test_steps);

		Utility.customAssert(acctualMarketingInfoDetail.getMI_EXTERNAL_CONFIRMATION_NUMBER(),
				expectedExternalConfirmationNumber, verifyExternalConfirmationNumber,
				"Successfully Verified Marketing Info ExternalConfirmationNumber : "
						+ expectedExternalConfirmationNumber,
				"Failed To Verify Marketing Info ExternalConfirmationNumber", continueExeExternalConfirmationNumber,
				test_steps);

		Utility.customAssert(acctualMarketingInfoDetail.getMI_MARKET_SOURCE(), expectedMarketSource, verifyMarketSource,
				"Successfully Verified Marketing Info MarketSource : " + expectedMarketSource,
				"Failed To Verify Marketing Info MarketSource", continueExeMarketSource, test_steps);

		Utility.customAssert(acctualMarketingInfoDetail.getMI_MARKET_SUB_SOURCE(), expectedMarketSubSource,
				verifyMarketSubSource,
				"Successfully Verified Marketing Info MarketSubSource : " + expectedMarketSubSource,
				"Failed To Verify Marketing Info MarketSubSource", continueExeMarketSubSource, test_steps);

	}

	public ArrayList<ReservationPolicies> getAllPolices(WebDriver driver) {
		ArrayList<ReservationPolicies> list = new ArrayList<>();
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);

		Utility.clickThroughJavaScript(driver, ele.DEPOSIT_POLICY_TOGGLE);
		waitForSweetLoading(driver);
		list.add(new ReservationPolicies("DEPOSIT", Utility.getELementText(ele.DEPOSIT_POLICY_TITLE, true, "innerHTML"),
				Utility.getELementText(ele.DEPOSIT_POLICY_STATEMENT, true, "innerHTML")));

		Utility.clickThroughJavaScript(driver, ele.CHECK_IN_POLICY_TOGGLE);
		waitForSweetLoading(driver);
		list.add(new ReservationPolicies("CHECK IN",
				Utility.getELementText(ele.CHECK_IN_POLICY_TITLE, true, "innerHTML"),
				Utility.getELementText(ele.CHECK_IN_POLICY_STATEMENT, true, "innerHTML")));

		Utility.clickThroughJavaScript(driver, ele.NO_SHOW_POLICY_TOGGLE);
		waitForSweetLoading(driver);
		list.add(new ReservationPolicies("NO SHOW", Utility.getELementText(ele.NO_SHOW_POLICY_TITLE, true, "innerHTML"),
				Utility.getELementText(ele.NO_SHOW_POLICY_STATEMENT, true, "innerHTML")));

		List<WebElement> eleList = driver.findElements(By.xpath(OR_ReservationV2.CANCELLATION_POLICY_TOGGLE));

		for (WebElement e : eleList) {
			Utility.clickThroughJavaScript(driver, e);
			waitForSweetLoading(driver);
			list.add(new ReservationPolicies(Utility.getELementText(e, false, ""),
					Utility.getELementText(ele.CANCELLATION_POLICY_TITLE, true, "innerHTML"),
					Utility.getELementText(ele.CANCELLATION_POLICY_STATEMENT, true, "innerHTML")));
			Utility.clickThroughJavaScript(driver, e);
		}
		return list;
	}

	public void verifyPolicyDetail(ReservationPolicies acctualPolicyDetail, String expectedPolicyTitle,
			boolean verifyPolicyTitle, boolean continueExePolicyTitle, String expectedPolicyStatement,
			boolean verifyPolicyStatement, boolean continueExePolicyStatement, ArrayList<String> test_steps) {

		Utility.customAssert(acctualPolicyDetail.getPOLICY_TITLE(), expectedPolicyTitle, verifyPolicyTitle,
				"Successfully Verified Policy Title : " + expectedPolicyTitle, "Failed To Verify Policy Title",
				continueExePolicyTitle, test_steps);

		Utility.customAssert(acctualPolicyDetail.getPOLICY_DESC(), expectedPolicyStatement, verifyPolicyStatement,
				"Successfully Verified Policy Desc : " + expectedPolicyStatement, "Failed To Verify Policy Desc",
				continueExePolicyStatement, test_steps);
	}

	public ArrayList<ReservationNotes> getAllNotes(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		ArrayList<ReservationNotes> notesList = new ArrayList<ReservationNotes>();
		for (WebElement el : ele.NOTES_LIST) {
			ReservationNotes notes = new ReservationNotes(
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.NOTES_TYPE)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.NOTES_SUBJECT)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.NOTES_DESCRIPTION)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.NOTES_UPDATED_BY)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.NOTES_UPDATED_ON)), false, ""));
			notesList.add(notes);
		}

		return notesList;
	}

	public void verifyReservationNotesDetail(ReservationNotes acctualReservationNotesDetail, String expectedType,
			boolean verifyType, boolean continueExeType, String expectedNotesSubject, boolean verifyNotesSubject,
			boolean continueExeNotesSubject, String expectedNotesDesc, boolean verifyNotesDesc,
			boolean continueExeNotesDesc, String expectedNotesUpdatedBy, boolean verifyNotesUpdatedBy,
			boolean continueExeNotesUpdatedBy, String expectedNotesUpdatedOn, boolean verifyNotesUpdatedOn,
			boolean continueExeNotesUpdatedOn,

			ArrayList<String> test_steps) {

		Utility.customAssert(acctualReservationNotesDetail.getNOTES_TYPE(), expectedType, verifyType,
				"Successfully Verified Notes Type : " + expectedType, "Failed To Verify Notes Type", continueExeType,
				test_steps);

		Utility.customAssert(acctualReservationNotesDetail.getNOTES_SUBJECT(), expectedNotesSubject, verifyType,
				"Successfully Verified Notes Subject : " + expectedNotesSubject, "Failed To Verify Notes Subject",
				continueExeNotesSubject, test_steps);

		Utility.customAssert(acctualReservationNotesDetail.getNOTES_DESCRIPTION(), expectedNotesDesc, verifyType,
				"Successfully Verified Notes Desc : " + expectedNotesDesc, "Failed To Verify Notes Desc",
				continueExeNotesDesc, test_steps);

		Utility.customAssert(acctualReservationNotesDetail.getNOTES_UPDATED_BY(), expectedNotesUpdatedBy, verifyType,
				"Successfully Verified Notes Updated By : " + expectedNotesUpdatedBy,
				"Failed To Verify Notes Updated By", continueExeNotesUpdatedBy, test_steps);

		Utility.customAssert(acctualReservationNotesDetail.getNOTES_UPDATED_ON(), expectedNotesUpdatedOn, verifyType,
				"Successfully Verified Notes Updated On : " + expectedNotesUpdatedOn,
				"Failed To Verify Notes Updated On", continueExeNotesUpdatedOn, test_steps);

	}

	public ArrayList<FolioLineItem> getAllFolioLineItems(WebDriver driver) throws InterruptedException {
		Wait.wait5Second();
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		ArrayList<FolioLineItem> lineItemList = new ArrayList<FolioLineItem>();
		for (WebElement el : ele.FOLIO_TABLE_RECORD) {
			FolioLineItem notes = new FolioLineItem(
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_STATUS)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_DATE)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_CATEGORY)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_DESCRIPTION)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_QTY)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_AMOUNT)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_TAX)), false, ""),
					Utility.getELementText(el.findElement(By.xpath(OR_ReservationV2.ITEM_TOTAL)), false, ""));
			lineItemList.add(notes);
		}

		return lineItemList;
	}

	public void verifyFolioLineItem(FolioLineItem acctualFolioLineItem, String expectedStatus, boolean verifyStatus,
			boolean continueExeStatus, String expectedDate, boolean verifyDate, boolean continueExeDate,
			String expectedCategory, boolean verifyCategory, boolean continueExeCategory, String expectedDesc,
			boolean verifyDesc, boolean continueExeDesc, String expectedQty, boolean verifyQty, boolean continueExeQty,
			String expectedAmount, boolean verifyAmount, boolean continueExeAmount, String expectedTax,
			boolean verifyTax, boolean continueExeTax, String expectedTotal, boolean verifyTotal,
			boolean continueExeTotal, ArrayList<String> test_steps) {

		Utility.customAssert(acctualFolioLineItem.getITEM_STATUS(), expectedStatus, verifyStatus,
				"Successfully Verified Item Status : " + expectedStatus, "Failed To Verify Item Status",
				continueExeStatus, test_steps);

		Utility.customAssert(acctualFolioLineItem.getITEM_DATE(), expectedDate, verifyDate,
				"Successfully Verified Item Date : " + expectedDate, "Failed To Verify Item Date", continueExeDate,
				test_steps);

		Utility.customAssert(acctualFolioLineItem.getITEM_CATEGORY(), expectedCategory, verifyCategory,
				"Successfully Verified Item Category : " + expectedCategory, "Failed To Verify Item Category",
				continueExeCategory, test_steps);

		Utility.customAssert(acctualFolioLineItem.getITEM_DESCRIPTION(), expectedDesc, verifyDesc,
				"Successfully Verified Item Description : " + expectedDesc, "Failed To Verify Item Description",
				continueExeDesc, test_steps);

		Utility.customAssert(acctualFolioLineItem.getITEM_QTY(), expectedQty, verifyQty,
				"Successfully Verified Item QTY : " + expectedQty, "Failed To Verify Item QTY", continueExeQty,
				test_steps);

		Utility.customAssert(
				Double.parseDouble(Utility.removeCurrencySignBracketsAndSpaces(acctualFolioLineItem.getITEM_AMOUNT()))
						+ "",
				"" + Double.parseDouble(expectedAmount), verifyAmount,
				"Successfully Verified Item Amount : " + expectedAmount, "Failed To Verify Item Amount",
				continueExeAmount, test_steps);

		Utility.customAssert(
				Double.parseDouble(Utility.removeCurrencySignBracketsAndSpaces(acctualFolioLineItem.getITEM_TAX()))
						+ "",
				"" + Double.parseDouble(expectedTax), verifyTax, "Successfully Verified Item Tax : " + expectedTax,
				"Failed To Verify Item Tax", continueExeTax, test_steps);

		Utility.customAssert(
				Double.parseDouble(Utility.removeCurrencySignBracketsAndSpaces(acctualFolioLineItem.getITEM_TOTAL()))
						+ "",
				"" + Double.parseDouble(expectedTotal), verifyTotal,
				"Successfully Verified Item Total : " + expectedTotal, "Failed To Verify Item Total", continueExeTotal,
				test_steps);

	}

	public void switchDetailTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.DETAIL_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.DETAIL_TAB, driver);
			elements.DETAIL_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.DETAIL_TAB);
		}
		test_steps.add("Detail Tab Clicked");
		logger.info("Detail Tab Clicked");

	}

	public void switchFolioTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.FOLIO_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.FOLIO_TAB, driver);
			elements.FOLIO_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.FOLIO_TAB);
		}
		test_steps.add("Folio Tab Clicked");
		logger.info("Folio Tab Clicked");

	}

	public void switchHistoryTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.HISTORY_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.HISTORY_TAB, driver);
			elements.HISTORY_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.HISTORY_TAB);
		}
		test_steps.add("History Tab Clicked");
		logger.info("History Tab Clicked");

	}

	public void switchDocumentsTab(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(elements.DOCUMENT_TAB, driver);
			Wait.explicit_wait_elementToBeClickable(elements.DOCUMENT_TAB, driver);
			elements.DOCUMENT_TAB.click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, elements.DOCUMENT_TAB);
		}
		test_steps.add("Documents Tab Clicked");
		logger.info("Documents Tab Clicked");

	}

	public String basicSearch_WithGuestName(WebDriver driver, String GuestName, boolean isOpen)
			throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		ele.GUEST_NAME_SEARCH.sendKeys(GuestName);
		logger.info("Entered the Guest Name for basic search");
		Utility.clickThroughJavaScript(driver, ele.RES_SEARCH);
		logger.info("Clicked on Search Button");
		waitForSweetLoading(driver);
		Wait.wait2Second();
		if (isOpen) {
			Utility.clickThroughJavaScript(driver, ele.FIRST_SEARCHED_RES);
			logger.info("Clicked on Searched Reservaton");
		}
		return Utility.getELementText(ele.FIRST_SEARCHED_RES, false, "");
	}

	public void basicSearch_WithReservationNo(WebDriver driver, String reservationNo, boolean isOpen)
			throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		ele.GUEST_RES_NO_SEARCH.clear();
		ele.GUEST_RES_NO_SEARCH.sendKeys(reservationNo);
		logger.info("Entered the reservation number for basic search");
		Utility.clickThroughJavaScript(driver, ele.RES_SEARCH);
		logger.info("Clicked on Search Button");
		waitForSweetLoading(driver);
		Wait.wait2Second();
		if (isOpen) {
			Utility.clickThroughJavaScript(driver, ele.FIRST_SEARCHED_RES);
			logger.info("Clicked on Searched Reservaton");
		}

	}

	public void waitForSweetLoading(WebDriver driver) {
		List<WebElement> eleList = driver.findElements(By.xpath("//*[contains(@class,'stage-dot-pulse')]"));
		for (WebElement ele : eleList) {
			try {
				if (ele.isDisplayed()) {
					try {
						Wait.waitForElementToBeGone(driver, ele, 240);
						break;
					} catch (Exception e) {
						break;
					}

				}
			} catch (Exception e) {
				logger.info(e.toString());
			}
		}

		/*
		 * do{ Wait.staicWait1(); }while(ele.isDisplayed)
		 */
	}

	/*
	 * public String clickOnGuestName(WebDriver driver, ArrayList<String>
	 * test_steps, String guestName, boolean guestClick) { Elements_Expedia we = new
	 * Elements_Expedia(driver);Elements_ReservationV2 ele = new
	 * Elements_ReservationV2(driver); Wait.explicit_wait_10sec(we.Get_Res_Number,
	 * driver); String reservationConfirmNum = we.Get_Res_Number.getText();
	 * test_steps.add("Displaying inncenter reservation confirmation number is : " +
	 * reservationConfirmNum);
	 * logger.info("Displaying inncenter reservation confirmation number is : " +
	 * reservationConfirmNum); if (guestClick) { ele.FIRST_SEARCHED_RES.click(); try
	 * { //Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.
	 * CP_Reservation_PanelGuestProfileName), driver); } catch (Exception e) {
	 * //clickOnOpenedReservationTab(driver, guestName); }
	 * test_steps.add("Clicking on guest name");
	 * logger.info("Clicking on guest name"); } return reservationConfirmNum; }
	 * 
	 * 
	 * public String guestUnlimitedSearchAndOpen(WebDriver driver, ArrayList<String>
	 * test_steps, String guestName, boolean openGuestProfile, int searchCount)
	 * throws InterruptedException { String reservationConfirmNum = null; String
	 * getGuestName = null; int searchedCount = 0; int refreshCount = 0; do {
	 * Wait.wait2Second(); if (refreshCount == 2) { driver.navigate().refresh();
	 * Wait.wait2Second(); } getGuestName = basicSearch_WithGuestName(driver,
	 * guestName,false); searchedCount++; refreshCount++;
	 * 
	 * } while (getGuestName == null && searchedCount < searchCount);
	 * 
	 * 
	 * if (getGuestName.contains(guestName)) {
	 * 
	 * //if (getGuestName.equals(guestName)) { reservationConfirmNum =
	 * clickOnGuestName(driver, test_steps, guestName, openGuestProfile); } else {
	 * for (int i = 0; i < 5; i++) { getGuestName =
	 * basicSearch_WithGuestName(driver, guestName,false); if
	 * (getGuestName.equals(guestName)) { reservationConfirmNum =
	 * clickOnGuestName(driver, test_steps, guestName, openGuestProfile); break; } }
	 * } driver.findElement(By.xpath("//ul[@class='sec_nav']//li[5]//i")); return
	 * reservationConfirmNum; }
	 */

	public void Click_CheckInButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		try {
			Wait.explicit_wait_visibilityof_webelement_120(res.RESERVATION_CHECK_IN_BUTTON, driver);
			Utility.ScrollToElement(res.RESERVATION_CHECK_IN_BUTTON, driver);
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, res.RESERVATION_CHECK_IN_BUTTON);
		}
		res.RESERVATION_CHECK_IN_BUTTON.click();
		test_steps.add("Click Check In  Button");
		logger.info("Click Check In  Button");

	}

	public void disableGenerateGuestReportToggle(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(
				By.xpath(OR_ReservationV2.RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE), driver);
		Utility.clickThroughJavaScript(driver, res.RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE);
		test_steps.add("Enabled Generate Guest Registration Toggle");
		logger.info("Enabled Generate Guest Registration Toggle");
		Wait.waitForElementToBeClickable(
				By.xpath(OR_ReservationV2.RESERVATION_CHECKIN_GUEST_REGISTRATION_REPORT_TOGGLE_OFF), driver);
	}

	public void clickOnProceedToCheckInPaymentButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON),
				driver);
		Utility.ScrollToElement(res.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON, driver);
		res.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON.click();
		test_steps.add("Clicking on  Proceed to Check-In Payment Button");
		logger.info("Clicking on Proceed to Check-In Payment Button");
	}

	public void clickOnConfirmCheckInButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON),
				driver);
		Utility.ScrollToElement(res.RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON, driver);
		res.RESERVATION_CONFIRM_CHECK_IN_PAYMENT_BUTTON.click();
		test_steps.add("Clicking on Confirm Check-In Button");
		logger.info("Clicking on Confirm Check-In Button");
	}

	public void roomStatusDirty(WebDriver driver, ArrayList<String> test_steps) {
		String dirtyRoom = "//div[text()='The room status is dirty. Do you wish to proceed with check in?' or "
				+ "text()='One or more of the selected room(s) is Dirty. Would you like to continue to check-in the guest?']";
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 3);
			if (driver.findElements(By.xpath(dirtyRoom)).size() > 0) {
				Wait.wait1Second();
				driver.findElement(By.xpath("//button[text()='Confirm']")).click();
				test_steps.add("Clicking on  roomClass Dirty confirmation");
				logger.info("Clicking on  roomClass Dirty confirmation");
			}
		} catch (Exception e) {
			test_steps.add("Room Class dirty confirmation popup not came");
			logger.info("Room Class dirty confirmation popup not came");
		}
	}

	public void selectCashOnPaymentPopup(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		try {
			Wait.waitForElementToBeVisibile(By.xpath(
					"//h4[text()='Check In Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"),
					driver, 30);
			driver.findElement(By.xpath(
					"//h4[text()='Check In Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"))
					.click();
			Wait.waitForElementToBeVisibile(
					By.xpath("//h4[text()='Check In Payment']/../..//span[@class='text' and text()='Cash']"), driver,
					30);
			Utility.clickThroughJavaScript(driver, driver.findElement(
					By.xpath("//h4[text()='Check In Payment']/../..//span[@class='text' and text()='Cash']")));
			String amount = driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount)).getText().trim();
			test_steps.add("Successfully verified paid amount is <b>" + amount + "</b>");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void clickOnPayButtonOnPaymentPopup(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Utility.clickThroughJavaScript(driver, res.RESERVATION_CHECK_IN_PAYMENT_PROCESS_BUTTON);
		test_steps.add("Click On Pay Button On Payment Popup");
		logger.info("Click On Pay Button On Payment Popup");
		Wait.wait5Second();
	}

	public void checkInPaymentSuccessPopupClose(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_CHECK_IN_POPUP_CLOSE), driver);
		res.RESERVATION_CHECK_IN_POPUP_CLOSE.click();
		test_steps.add("Closing check-in payment successful popup");
		logger.info("Closing check-in payment successful popup");
	}

	public void clickEditGuestInfo(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.RESERVATION_GUEST_INFO_EDIT);
		Utility.ScrollToViewElementINMiddle(driver, res.RESERVATION_GUEST_INFO_EDIT);
		res.RESERVATION_GUEST_INFO_EDIT.click();
		test_steps.add("Click on Edit Guest Info");
		logger.info("Click on Edit Guest Info");
	}

	public void enterGuestInfoPhoneNo(WebDriver driver, String phoneNo, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		res.RESERVATION_GUEST_INFO_PHONE_NO.clear();
		res.RESERVATION_GUEST_INFO_PHONE_NO.sendKeys(Keys.TAB);
		res.RESERVATION_GUEST_INFO_PHONE_NO.sendKeys(phoneNo);
		res.RESERVATION_GUEST_INFO_PHONE_NO.sendKeys(Keys.TAB);
		test_steps.add("Guest Info Phone No Entered : " + phoneNo);
		logger.info("Guest Info Phone No Entered : " + phoneNo);
	}

	public void clickGuestInfoSave(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		res.RESERVATION_GUEST_INFO_SAVE_BUTTON.click();
		test_steps.add("Click on SAVE Guest Info");
		logger.info("Click on SAVE Guest Info");
	}

	public CheckInPaymentPopup getCheckInPopupDetails(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		CheckInPaymentPopup paymentPopup = new CheckInPaymentPopup(
				Utility.getELementText(ele.CHECK_IN_PAYMENT_POLICY_NAME, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_POLICY_NAME, true, "data-content"),
				Utility.getELementText(ele.CHECK_IN_PAYPMENT_BALANCE, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_AMOUNT, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_TYPE, false, ""));
		return paymentPopup;
	}

	public void verfiyCheckInPopupDetail(CheckInPaymentPopup acctualDetail, String expectedPolicyName,
			boolean verifyPolicyName, boolean continueExePolicyName, String expectedPolicyDesc,
			boolean verifyPolicyDesc, boolean continueExePolicyDesc, String expectedBalance, boolean verifyBalance,
			boolean continueExeBalance, String expectedAmount, boolean verifyAmount, boolean continueExeAmount,
			String expectedType, boolean verifyType, boolean continueExeType, ArrayList<String> test_steps) {

		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_POLICY_NAME(), expectedPolicyName, verifyPolicyName,
				"Successfully Verified Check In Popup Policy Name : " + expectedPolicyName,
				"Failed To Verify Check In Popup Policy Name", continueExePolicyName, test_steps);

		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_POLICY_DESC(), expectedPolicyDesc, verifyPolicyDesc,
				"Successfully Verified Check In Popup Policy Desc : " + expectedPolicyDesc,
				"Failed To Verify Check In Popup Policy Desc", continueExePolicyDesc, test_steps);
		try {
			Utility.customAssert(
					Float.parseFloat(
							Utility.removeDollarBracketsAndSpaces(acctualDetail.getCHECK_IN_PAYPMENT_BALANCE())) + "",
					"" + Float.parseFloat(Utility.removeDollarBracketsAndSpaces(expectedBalance)), verifyBalance,
					"Successfully Verified Check In Popup Policy Balance : " + expectedBalance,
					"Failed To Verify Check In Popup Policy Balance", continueExeBalance, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Utility.customAssert(
					Float.parseFloat(Utility.removeDollarBracketsAndSpaces(acctualDetail.getCHECK_IN_PAYMENT_AMOUNT()))
							+ "",
					"" + Float.parseFloat(Utility.removeDollarBracketsAndSpaces(expectedAmount)), verifyAmount,
					"Successfully Verified Check In Popup Policy Amount : " + expectedAmount,
					"Failed To Verify Check In Popup Policy Amount", continueExeAmount, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_TYPE(), expectedType, verifyType,
				"Successfully Verified Check In Popup Auth Type : " + expectedType,
				"Failed To Verify Check In Popup Auth Type", continueExeType, test_steps);
	}

	public CheckInPaymentPopup getCheckInSuccessPopupDetails(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		CheckInPaymentPopup paymentPopup = new CheckInPaymentPopup(
				Utility.getELementText(ele.CHECK_IN_PAYMENT_SUCCESS_POLICY_NAME, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_SUCCESS_POLICY_NAME, true, "data-content"),
				Utility.getELementText(ele.CHECK_IN_PAYPMENT_SUCCESS_BALANCE, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_SUCCESS_AMOUNT, false, ""),
				Utility.getELementText(ele.CHECK_IN_PAYMENT_SUCCESS_TYPE, false, ""));
		return paymentPopup;
	}

	public void verfiyCheckInSuccessPopupDetail(CheckInPaymentPopup acctualDetail, String expectedPolicyName,
			boolean verifyPolicyName, boolean continueExePolicyName, String expectedPolicyDesc,
			boolean verifyPolicyDesc, boolean continueExePolicyDesc, String expectedBalance, boolean verifyBalance,
			boolean continueExeBalance, String expectedAmount, boolean verifyAmount, boolean continueExeAmount,
			String expectedType, boolean verifyType, boolean continueExeType, ArrayList<String> test_steps) {

		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_POLICY_NAME(), expectedPolicyName, verifyPolicyName,
				"Successfully Verified Check In Success Popup Policy Name : " + expectedPolicyName,
				"Failed To Verify Check In Success Popup Policy Name", continueExePolicyName, test_steps);

		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_POLICY_DESC(), expectedPolicyDesc, verifyPolicyDesc,
				"Successfully Verified Check In Success Popup Policy Desc : " + expectedPolicyDesc,
				"Failed To Verify Check In Success Popup Policy Desc", continueExePolicyDesc, test_steps);
		try {
			Utility.customAssert(
					Float.parseFloat(
							Utility.removeDollarBracketsAndSpaces(acctualDetail.getCHECK_IN_PAYPMENT_BALANCE())) + "",
					"" + Float.parseFloat(Utility.removeDollarBracketsAndSpaces(expectedBalance)), verifyBalance,
					"Successfully Verified Check In Success Popup Policy Balance : " + expectedBalance,
					"Failed To Verify Check In Success Popup Policy Balance", continueExeBalance, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Utility.customAssert(
					Float.parseFloat(Utility.removeDollarBracketsAndSpaces(acctualDetail.getCHECK_IN_PAYMENT_AMOUNT()))
							+ "",
					"" + Float.parseFloat(Utility.removeDollarBracketsAndSpaces(expectedAmount)), verifyAmount,
					"Successfully Verified Check In Success Popup Policy Amount : " + expectedAmount,
					"Failed To Verify Check In Success Popup Policy Amount", continueExeAmount, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Utility.customAssert(acctualDetail.getCHECK_IN_PAYMENT_TYPE(), expectedType, verifyType,
				"Successfully Verified Check In Success Popup Auth Type : " + expectedType,
				"Failed To Verify Check In Success Popup Auth Type", continueExeType, test_steps);
	}

	public void changeReservationStatus(WebDriver driver, String statusChange, ArrayList<String> test_steps)
			throws InterruptedException {
		String expand = "//reservation-statusbar//span[contains(@data-bind,'ReservationUpdate')]";
		Wait.waitForElementToBeClickable(By.xpath(expand), driver);
		driver.findElement(By.xpath(expand)).click();
		String status = "//reservation-statusbar//span[contains(@data-bind,'reservationStatusItem.StatusName')][text()='"
				+ statusChange + "']";
		Wait.waitForElementToBeClickable(By.xpath(status), driver);
		driver.findElement(By.xpath(status)).click();
		test_steps.add("Clicking on " + statusChange + " reservation button");
		logger.info("Clicking on " + statusChange + " reservation button");
		Wait.wait5Second();
	}

	public void enableVoidRoomCharge(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharges) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		boolean voidRoomChargesSelected;
		try {
			voidRoomChargesSelected = res.CANCEL_VOID_ROOM_CHARGES.isSelected();
		} catch (Exception e) {
			voidRoomChargesSelected = res.NO_SHOW_VOID_ROOM_CHARGES.isSelected();
		}
		if (voidRoomCharges) {
			if (!(voidRoomChargesSelected)) {
				try {
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CANCEL_VOID_ROOM_CHARGES), driver);
					res.CANCEL_VOID_ROOM_CHARGES.click();
				} catch (Exception e) {
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.NO_SHOW_VOID_ROOM_CHARGES), driver);
					res.NO_SHOW_VOID_ROOM_CHARGES.click();
				}
				test_steps.add("Enabling Void Room Charges check box");
			} else {
				test_steps.add("Void Room Charges check box is already selected");
			}
		} else {
			if (voidRoomChargesSelected) {
				try {
					res.CANCEL_VOID_ROOM_CHARGES.click();
				} catch (Exception e) {
					res.NO_SHOW_VOID_ROOM_CHARGES.click();
				}
				test_steps.add("Unselecting Void Room Charges check box");
			} else {
				test_steps.add("Void Room Charges check box is already unselected");
			}
		}
	}

	public void inputReason(WebDriver driver, ArrayList<String> test_steps, String reason) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			res.ADD_CANCELATION_REASON.clear();
			res.ADD_CANCELATION_REASON.sendKeys(reason);
			res.ADD_CANCELATION_REASON.sendKeys(Keys.TAB);
			test_steps.add("Providing Cancellation Reason as : <b>" + reason + " </b>");
			logger.info("Providing Cancellation Reason as : " + reason);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void provideReasonForCancelAndClickOnProceedToPay(WebDriver driver, ArrayList<String> test_steps,
			String reason, boolean voidRoomCharges) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		inputReason(driver, test_steps, reason);
		enableVoidRoomCharge(driver, test_steps, voidRoomCharges);
		Wait.WaitForElement(driver, OR_ReservationV2.PROCEED_TO_CANCELLATION_BUTTON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.PROCEED_TO_CANCELLATION_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PROCEED_TO_CANCELLATION_BUTTON), driver);
		Utility.ScrollToElement_NoWait(res.PROCEED_TO_CANCELLATION_BUTTON, driver);
		res.PROCEED_TO_CANCELLATION_BUTTON.click();
		test_steps.add("Clicking on '<b>PROCEED TO CANCELLATION PAYMENT</b>' Button");
		logger.info("Click 'PROCEED TO CANCELLATION PAYMENT' Button");
		Wait.wait10Second();
	}

	public void provideReasonForCancelAndClickOnConfirmCancel(WebDriver driver, ArrayList<String> test_steps,
			String reason, boolean voidRoomCharges) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		inputReason(driver, test_steps, reason);
		enableVoidRoomCharge(driver, test_steps, voidRoomCharges);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.ON_CONFIRM_CANCELLATION_BUTTON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ON_CONFIRM_CANCELLATION_BUTTON), driver);
		Utility.ScrollToElement_NoWait(res.ON_CONFIRM_CANCELLATION_BUTTON, driver);
		res.ON_CONFIRM_CANCELLATION_BUTTON.click();
		test_steps.add("Clicking on '<b>PROCEED TO CANCELLATION PAYMENT</b>' Button");
		logger.info("Click 'PROCEED TO CANCELLATION PAYMENT' Button");

	}

	public void selectCashOnCancellationPaymentPopup(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		try {
			Wait.waitForElementToBeVisibile(By.xpath(
					"//h4[text()='Cancellation Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"),
					driver, 30);
			driver.findElement(By.xpath(
					"//h4[text()='Cancellation Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"))
					.click();
			Wait.waitForElementToBeVisibile(
					By.xpath("//h4[text()='Cancellation Payment']/../..//span[@class='text' and text()='Cash']"),
					driver, 30);
			Utility.clickThroughJavaScript(driver,
					driver.findElement(By.xpath("//span[@class='text' and text()='Cash']")));
			String amount = driver.findElement(By.xpath(OR_Reservation.NoShowPaymentPopup_Amount)).getText().trim();
			test_steps.add("Successfully verified paid amount is <b>" + amount + "</b>");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public CancellationPaymentPopup getCancellationPopupDetails(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		CancellationPaymentPopup paymentPopup = new CancellationPaymentPopup(

				Utility.getELementText(ele.CANCEL_PAYPMENT_BALANCE, false, ""),
				Utility.getELementText(ele.CANCEL_PAYMENT_AMOUNT, false, ""),
				Utility.getELementText(ele.CANCEL_PAYMENT_TYPE, false, ""));
		return paymentPopup;
	}

	public void verfiyCancellationPopupDetail(CancellationPaymentPopup acctualDetail,

			String expectedBalance, boolean verifyBalance, boolean continueExeBalance, String expectedAmount,
			boolean verifyAmount, boolean continueExeAmount, String expectedType, boolean verifyType,
			boolean continueExeType, ArrayList<String> test_steps) {

		try {
			Utility.customAssert(Float.parseFloat(acctualDetail.getCANCEL_PAYPMENT_BALANCE()) + "",
					"" + Float.parseFloat(expectedBalance), verifyBalance,
					"Successfully Verified Cancel Popup Policy Balance : " + expectedBalance,
					"Failed To Verify Cancel Popup Policy Balance", continueExeBalance, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Utility.customAssert(Float.parseFloat(acctualDetail.getCANCEL_PAYMENT_AMOUNT()) + "",
					"" + Float.parseFloat(expectedAmount), verifyAmount,
					"Successfully Verified Cancel Popup Policy Amount : " + expectedAmount,
					"Failed To Verify Cancel Popup Policy Amount", continueExeAmount, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Utility.customAssert(acctualDetail.getCANCEL_PAYMENT_TYPE(), expectedType, verifyType,
					"Successfully Verified Cancel Popup Auth Type : " + expectedType,
					"Failed To Verify Cancel Popup Auth Type", continueExeType, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public CancellationPaymentPopup getCancellationSuccessPopupDetails(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		CancellationPaymentPopup paymentPopup = new CancellationPaymentPopup(
				Utility.getELementText(ele.CANCEL_PAYMENT_SUCCESS_POLICY_NAME, false, ""),
				Utility.getELementText(ele.CANCEL_PAYMENT_SUCCESS_POLICY_NAME, true, "data-content"),
				Utility.getELementText(ele.CANCEL_PAYPMENT_SUCCESS_BALANCE, false, ""),
				Utility.getELementText(ele.CANCEL_PAYMENT_SUCCESS_AMOUNT, false, ""),
				Utility.getELementText(ele.CANCEL_PAYMENT_SUCCESS_TYPE, false, ""));
		return paymentPopup;
	}

	public void verfiyCancellationSuccessPopupDetail(CancellationPaymentPopup acctualDetail, String expectedPolicyName,
			boolean verifyPolicyName, boolean continueExePolicyName, String expectedPolicyDesc,
			boolean verifyPolicyDesc, boolean continueExePolicyDesc, String expectedBalance, boolean verifyBalance,
			boolean continueExeBalance, String expectedAmount, boolean verifyAmount, boolean continueExeAmount,
			String expectedType, boolean verifyType, boolean continueExeType, ArrayList<String> test_steps) {

		Utility.customAssert(acctualDetail.getCANCEL_PAYMENT_POLICY_NAME(), expectedPolicyName, verifyPolicyName,
				"Successfully Verified CANCEL Success Popup Policy Name : " + expectedPolicyName,
				"Failed To Verify CANCEL Success Popup Policy Name", continueExePolicyName, test_steps);

		Utility.customAssert(acctualDetail.getCANCEL_PAYMENT_POLICY_DESC(), expectedPolicyDesc, verifyPolicyDesc,
				"Successfully Verified CANCEL Success Popup Policy Desc : " + expectedPolicyDesc,
				"Failed To Verify CANCEL Success Popup Policy Desc", continueExePolicyDesc, test_steps);
		try {
			Utility.customAssert(Float.parseFloat(acctualDetail.getCANCEL_PAYPMENT_BALANCE()) + "",
					"" + Float.parseFloat(expectedBalance), verifyBalance,
					"Successfully Verified CANCEL Success Popup Policy Balance : " + expectedBalance,
					"Failed To Verify CANCEL Success Popup Policy Balance", continueExeBalance, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Utility.customAssert(Float.parseFloat(acctualDetail.getCANCEL_PAYMENT_AMOUNT()) + "",
					"" + Float.parseFloat(expectedAmount), verifyAmount,
					"Successfully Verified CANCEL Success Popup Policy Amount : " + expectedAmount,
					"Failed To Verify CANCEL Success Popup Policy Amount", continueExeAmount, test_steps);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Utility.customAssert(acctualDetail.getCANCEL_PAYMENT_TYPE(), expectedType, verifyType,
				"Successfully Verified CANCEL Success Popup Auth Type : " + expectedType,
				"Failed To Verify CANCEL Success Popup Auth Type", continueExeType, test_steps);
	}

	public HashMap<String, String> getCancellationClauseValue(String delim, String typeOfFees,
			String guestsWillIncurAFee) {
		HashMap<String, String> valuesAgainstClauses = new HashMap<String, String>();

		ArrayList<String> typeOfFeesList = Utility.convertTokenToArrayList(typeOfFees, delim);
		ArrayList<String> percentageList = Utility.convertTokenToArrayList(guestsWillIncurAFee, delim);
		int index = 0;
		for (String type : typeOfFeesList) {
			if (type.equalsIgnoreCase("flat fee")) {
				valuesAgainstClauses.put("flat fee", "$" + percentageList.get(index));
			} else if (type.equalsIgnoreCase("number of nights")) {
				valuesAgainstClauses.put("number of nights", percentageList.get(index) + " Night(s)");
			} else if (type.equalsIgnoreCase("percent of stay")) {
				valuesAgainstClauses.put("percent of stay", percentageList.get(index));
			}

			index++;
		}
		return valuesAgainstClauses;
	}

	public void cancellationPaymentSuccessPopupClose(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CANCEL_PAYMENT_POPUP_CLOSE), driver);
		res.CANCEL_PAYMENT_POPUP_CLOSE.click();
		test_steps.add("Closing Cancellation payment successful popup");
		logger.info("Closing Cancellation payment successful popup");
	}

	public void checkInReservation(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Click_CheckInButton(driver, test_steps);
		disableGenerateGuestReportToggle(driver, test_steps);

		if (res.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON.isDisplayed()) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON),
					driver);
			clickOnProceedToCheckInPaymentButton(driver, test_steps);
			roomStatusDirty(driver, test_steps);
			selectCashOnPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			checkInPaymentSuccessPopupClose(driver, test_steps);
		} else {
			clickOnConfirmCheckInButton(driver, test_steps);
			waitForSweetLoading(driver);
			roomStatusDirty(driver, test_steps);
		}
	}

	public void click_CheckInMrbPrimary(WebDriver driver, ArrayList<String> test_steps) {
		String checkInButton = "//span[text()='Check In' and contains(@data-bind,'statusAction')]";
		Wait.WaitForElement(driver, checkInButton);
		List<WebElement> checkIn = driver.findElements(By.xpath(checkInButton));
		checkIn.get(0).click();
	}

	public void click_CheckInMrbSecondary(WebDriver driver, ArrayList<String> test_steps) {
		String checkInButton = "//span[text()='Check In' and contains(@data-bind,'statusAction')]";
		Wait.WaitForElement(driver, checkInButton);
		List<WebElement> checkIn = driver.findElements(By.xpath(checkInButton));
		checkIn.get(checkIn.size() - 1).click();
	}

	public void checkIn_MrbPrimary(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		click_CheckInMrbPrimary(driver, test_steps);
		disableGenerateGuestReportToggle(driver, test_steps);

		if (res.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON.isDisplayed()) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON),
					driver);
			clickOnProceedToCheckInPaymentButton(driver, test_steps);
			roomStatusDirty(driver, test_steps);
			selectCashOnPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			checkInPaymentSuccessPopupClose(driver, test_steps);
		} else {
			clickOnConfirmCheckInButton(driver, test_steps);
			waitForSweetLoading(driver);
			roomStatusDirty(driver, test_steps);
		}
	}

	public void checkIn_MrbSecondary(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		click_CheckInMrbSecondary(driver, test_steps);
		disableGenerateGuestReportToggle(driver, test_steps);

		if (res.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON.isDisplayed()) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.RESERVATION_PROCEED_TO_CHECK_IN_PAYMENT_BUTTON),
					driver);
			clickOnProceedToCheckInPaymentButton(driver, test_steps);
			roomStatusDirty(driver, test_steps);
			selectCashOnPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			checkInPaymentSuccessPopupClose(driver, test_steps);
		} else {
			clickOnConfirmCheckInButton(driver, test_steps);
			waitForSweetLoading(driver);
			roomStatusDirty(driver, test_steps);
		}
	}

	public void cancelReservation(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharges,
			String reason) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		changeReservationStatus(driver, "Cancel", test_steps);

		if (res.PROCEED_TO_CANCELLATION_BUTTON.isDisplayed()) {
			provideReasonForCancelAndClickOnProceedToPay(driver, test_steps, reason, voidRoomCharges);
			selectCashOnCancellationPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
		} else {
			provideReasonForCancelAndClickOnConfirmCancel(driver, test_steps, reason, voidRoomCharges);
		}
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CANCEL_PAYMENT_POPUP_CLOSE), driver, 3);
			cancellationPaymentSuccessPopupClose(driver, test_steps);
		} catch (Exception e) {
			logger.info(e.toString());
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SB_GUEST_NAME), driver);
		ReservationStatusBar statusBar = getStatusBarDetail(driver);
		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase("Cancelled")) {
			logger.info("Successfully Cancelled the reservation");
			test_steps.add("Successfully Cancelled the reservation");
		} else {
			logger.info("Failed to Cancelled the reservation");
			test_steps.add("Failed to Cancelled the reservation");
		}
	}

	public void click_CancelMrbSecondary(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.STAY_INFO_THREE_DOTS);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		threeDots.get(threeDots.size() - 1).click();
		logger.info("Clicked on Secondary room Stay Info options");
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CANCEL_MRB_SECONDARY), driver);
		Utility.ScrollToElement(res.CANCEL_MRB_SECONDARY, driver);
		res.CANCEL_MRB_SECONDARY.click();
		logger.info("Clicked on Secondary room Cancel button");
		test_steps.add("Clicked on Secondary room Cancel button");
	}

	public void cancel_MrbSecondary(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharges,
			String reason) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		click_CancelMrbSecondary(driver, test_steps);
		waitForSweetLoading(driver);
		if (res.PROCEED_TO_CANCELLATION_BUTTON.isDisplayed()) {
			provideReasonForCancelAndClickOnProceedToPay(driver, test_steps, reason, voidRoomCharges);
			selectCashOnCancellationPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			cancellationPaymentSuccessPopupClose(driver, test_steps);
		} else {
			provideReasonForCancelAndClickOnConfirmCancel(driver, test_steps, reason, voidRoomCharges);
		}
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SB_GUEST_NAME), driver);
	}

	public void selectCheckInDate(WebDriver driver, ArrayList<String> test_steps, String checkInDate, int count) {
		checkInDate = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MM/dd/yyyy");
		try {
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_CheckInDate + ")[" + count + "]"))
					.clear();
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_CheckInDate + ")[" + count + "]"))
					.sendKeys(checkInDate);
			test_steps.add("Entered check-in date as : <b>" + checkInDate + "</b>");
			logger.info("Entered check-in date as : " + checkInDate);
		} catch (Exception e) {
			logger.info(e);
		}
	}

	public void selectCheckInDate(WebDriver driver, ArrayList<String> test_steps, String checkInDate) {
		selectCheckInDate(driver, test_steps, checkInDate, 1);
	}

	public void selectCheckOutDate(WebDriver driver, ArrayList<String> test_steps, String checkOutDate, int count) {
		checkOutDate = Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MM/dd/yyyy");
		try {
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_CheckOutDate + ")[" + count + "]"))
					.clear();
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_CheckOutDate + ")[" + count + "]"))
					.sendKeys(checkOutDate);
			test_steps.add("Entered check-out date as : <b>" + checkOutDate + "</b>");
			logger.info("Entered check-out date as : " + checkOutDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectCheckOutDate(WebDriver driver, ArrayList<String> test_steps, String checkOutDate) {
		selectCheckOutDate(driver, test_steps, checkOutDate, 1);
	}

	public void enterAdults(WebDriver driver, ArrayList<String> test_steps, String adults) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			elements.CP_NewReservation_Adults.clear();
			elements.CP_NewReservation_Adults.sendKeys(adults);
		} catch (Exception e) {
			logger.info(e);
		}
	}

	public void enterChildren(WebDriver driver, ArrayList<String> test_steps, String children)
			throws InterruptedException {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			elements.CP_NewReservation_Children.clear();
			elements.CP_NewReservation_Children.sendKeys(children);
		} catch (Exception e) {
			logger.info(e);
		}
	}

	// Modifications
	public void clickEditStayInfo(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			Utility.ScrollToElement(elements.EDIT_STAY_INFO, driver);
			elements.EDIT_STAY_INFO.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS), driver, 5);
			logger.info("Clicked on Edit Stay Info");
		} catch (Exception e) {
			try {
				Utility.clickThroughAction(driver, elements.EDIT_STAY_INFO);
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS), driver, 5);
				logger.info("Clicked on Edit Stay Info");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	public void clickChangeStayDetails(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS), driver);
			Utility.clickThroughAction(driver, elements.CHANGE_STAY_DETAILS);
			logger.info("Clicked on Change Stay Details");
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.STAY_INFO_CHECKIN_DATE), driver);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void selectStayInfoOption(WebDriver driver, String option) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		try {
			if (option.equalsIgnoreCase("Recalculate Rate")) {
				elements.RECALCULATE_RATE.click();
			} else if (option.equalsIgnoreCase("Change only for added/removed dates")) {
				elements.CHAGNE_ONLY_FOR_DATES.click();
			} else if (option.equalsIgnoreCase("No Rate Change")) {
				elements.NO_RATE_CHANGE.click();
			}
			logger.info("Clicked on " + option);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void clickFindRoomsStayInfo(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			// Wait.wait5Second();
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.STAY_INFO_FIND_ROOMS), driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_FIND_ROOMS), driver);
			elements.STAY_INFO_FIND_ROOMS.click();
			logger.info("Clicked on find rooms");
			waitForSweetLoading(driver);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void clickSaveStayInfo(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_SAVE), driver);
			elements.STAY_INFO_SAVE.click();

		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public ArrayList<String> getAllRoomNumbersFromRoomClassDropDown(WebDriver driver, ArrayList<String> test_steps,
			String roomClass) throws InterruptedException {
		ArrayList<String> returnNumbers = new ArrayList<>();
		String expand = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + roomClass
				+ "']/../../../following-sibling::div/div/select/following-sibling::div/button";
		Wait.waitForElementToBeClickable(By.xpath(expand), driver, 30);
		Utility.ScrollToElement(driver.findElement(By.xpath(expand)), driver);
		driver.findElement(By.xpath(expand)).click();

		String list = "//span[text()='" + roomClass
				+ "']/../../../following-sibling::div/div/select/following-sibling::div//ul"
				+ "//span[@class='clean']/following-sibling::span[@class='room-number']";

		/*
		 * String list = "//span[text()='" + roomClass +
		 * "']/../../../following-sibling::div/div" +
		 * "/select/following-sibling::div//ul//span[@class='room-number'] | //span[text()='"
		 * + roomClass + "']/../../../following-sibling::div/div" +
		 * "/select/following-sibling::div//ul//span[@class='text']";//room-number
		 */ System.out.println(list);
		logger.info(list);

		List<WebElement> roomNumbers = driver.findElements(By.xpath(list));
		for (WebElement webElement : roomNumbers) {
			// Wait.wait2Second();
			String roomNumber = webElement.getText();
			if (!(roomNumber.equalsIgnoreCase("Unassigned")) && !roomNumber.equalsIgnoreCase("VirtualRoom")) {
				returnNumbers.add(roomNumber);
			}
		}
		try {
			driver.findElement(By.xpath(expand)).click();
		} catch (Exception e) {
			Utility.scrollAndClick(driver, By.xpath(expand));
		}
		System.out.println(returnNumbers);
		logger.info(returnNumbers);
		return returnNumbers;
	}

	public void selectRoomToReserve(WebDriver driver, ArrayList<String> test_steps, String roomClass, String roomNumber)
			throws Exception {
		String expand = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + roomClass
				+ "']/../../../following-sibling::div/div/select/following-sibling::div/button";
		String availability = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + roomClass
				+ "']/../../div[3]/span";

		Wait.waitForElementToBeVisibile(By.xpath(availability), driver);
		int availableRooms = Integer
				.parseInt(driver.findElement(By.xpath(availability)).getText().split(" ")[0].trim());
		if (availableRooms > 0) {
			String assignRoomNo = "(//section[@class='ir-roomClassDetails manual-override']//span[text()='" + roomClass
					+ "']/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='"
					+ roomNumber + "'])";
			try {
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				driver.findElement(By.xpath(expand)).click();
				driver.findElement(By.xpath(assignRoomNo)).click();
				test_steps.add("Selecting room <b>" + roomNumber + "</b> from <b>" + roomClass + "</b> room class");
				logger.info("Selecting room " + roomNumber + " from " + roomClass + " room class");
			} catch (Exception e) {
				Wait.waitForElementToBeClickable(By.xpath(expand), driver, 20);
				driver.findElement(By.xpath(expand)).click();
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				try {
					driver.findElement(By.xpath(assignRoomNo)).click();
				} catch (Exception e1) {
					Utility.scrollAndClick(driver, By.xpath(assignRoomNo));
				}
				test_steps.add("Selecting room <b>" + roomNumber + "</b> from <b>" + roomClass + "</b> room class");
				logger.info("Selecting room " + roomNumber + " from " + roomClass + " room class");
			}

			String selectButton = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + roomClass
					+ "']/../../../../div[2]/div[3]/a";
			logger.info("Select found");
			Wait.waitForElementToBeVisibile(By.xpath(selectButton), driver);
			Wait.waitForElementToBeClickable(By.xpath(selectButton), driver, 20);
			driver.findElement(By.xpath(selectButton)).click();

			try {
				logger.info("is Select Button is Displayed : "
						+ driver.findElement(By.xpath(selectButton + "//span[contains(@class,'stage-dot-pulse')]"))
								.isDisplayed());
				Utility.customAssert(
						driver.findElement(By.xpath(selectButton + "//span[contains(@class,'stage-dot-pulse')]"))
								.isDisplayed() + "",
						true + "", false, "Successfully Verified Three Dots on Select Button",
						"Failed to Verify Three Dots on Select Button", true, test_steps);
			} catch (Exception e) {
				logger.info("Select button not verified");
				e.printStackTrace();
			}

			logger.info("Clicked on Select button");
			Wait.wait10Second();
		} else {
			test_steps.add("Rooms are not available to select for <b>" + roomClass + "</b>");
			throw new SkipException("Rooms are not available to select for <b>" + roomClass + "</b>");
		}
	}

	public void selectRoomToReserveRandom(WebDriver driver, ArrayList<String> test_steps, String roomClass,
			String isAssign) throws InterruptedException {
		String availability = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
				+ roomClass + "')]/../../div[3]/span";
		ArrayList<String> returnNumbers = new ArrayList<>();
		int randomNumber;

		Wait.waitForElementToBeVisibile(By.xpath(availability), driver);
		int availableRooms = Integer
				.parseInt(driver.findElement(By.xpath(availability)).getText().split(" ")[0].trim());
		if (availableRooms > 0) {
			String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
					+ roomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
			Wait.waitForElementToBeClickable(By.xpath(expand), driver, 20);
			driver.findElement(By.xpath(expand)).click();

			String list = "//span[contains(text(),'" + roomClass + "')]/../../../following-sibling::div/div"
					+ "/select/following-sibling::div//ul//span[@class='room-number']";
			List<WebElement> roomNumbers = driver.findElements(By.xpath(list));
			for (WebElement webElement : roomNumbers) {
				String roomNumber = webElement.getText();
				if (!(roomNumber.equalsIgnoreCase("Unassigned"))) {
					returnNumbers.add(roomNumber);
				} else if (roomNumber.equalsIgnoreCase("VirtualRoom")) {
					returnNumbers.add(roomNumber);
				}
			}

			int count1 = returnNumbers.size();
			Random random = new Random();
			randomNumber = random.nextInt(count1 - 1) + 1;

			if (isAssign.equalsIgnoreCase("Yes")) {
				String assignRoomNo = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ roomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div//ul//a)["
						+ randomNumber + "]";
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				driver.findElement(By.xpath(assignRoomNo)).click();
				test_steps.add("Selecting room <b>" + randomNumber + "</b> from <b>" + roomClass + "</b> room class");
				logger.info("Selecting room " + randomNumber + " from " + roomClass + " room class");
			} else if (isAssign.equalsIgnoreCase("No")) {
				String assignRoomNo = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ roomClass
						+ "')]/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.waitForElementToBeClickable(By.xpath(assignRoomNo), driver, 10);
				driver.findElement(By.xpath(assignRoomNo)).click();
				test_steps.add("Selecting room <b> Unassigned </b> from <b>" + roomClass + "</b> room class");
				logger.info("Selecting room Unassigned from " + roomClass + " room class");
			}

			String selectButton = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
					+ roomClass + "')]/../../../../div[2]/div[3]/a";
			logger.info("Select found");
			Wait.WaitForElement(driver, selectButton);
			List<WebElement> selectButtons = driver.findElements(By.xpath(selectButton));
			int size = selectButtons.size();
			logger.info(size);
			if (size == 1) {
				logger.info("Select found");
				driver.findElement(By.xpath(selectButton)).click();
			}
		} else {
			test_steps.add("Rooms are not available to select for <b>" + roomClass + "</b>");
		}
	}

	public void clickChangeStayDetailsConfirmPopup(WebDriver driver, String option) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
			if (option.equalsIgnoreCase("Yes")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_CONFIRM_YES), driver, 5);
				elements.STAY_INFO_CONFIRM_YES.click();
			} else if (option.equalsIgnoreCase("No")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_CONFIRM_NO), driver, 5);
				elements.STAY_INFO_CONFIRM_NO.click();
			}

		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void extendReservation(WebDriver driver, String checkOut, String changeOption, boolean isRoomChange,
			String roomClass, ArrayList<String> test_steps) throws Exception {
		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);
		// selectCheckOutDate(driver, test_steps, checkOut);
		selectCheckOutDate(driver, test_steps, checkOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		if (isRoomChange) {
			ArrayList<String> rooms = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
			selectRoomToReserve(driver, test_steps, roomClass, rooms.get(0));

		}
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
			clickChangeStayDetailsConfirmPopup(driver, "Yes");
			logger.info("Clicked on Yes on Confirmation popup");
		} catch (Exception e) {
			logger.info("Confirmation popup not displayed");
		}
	}

	// This method is to extend or reduce reservation
	public void extendOrReduceReservation(WebDriver driver, String checkOut, String changeOption,
			ArrayList<String> test_steps) throws Exception {
		// String loading = "//span[contains(@class,'stage-dot-pulse')]";
		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);
		// selectCheckOutDate(driver, test_steps, checkOut);
		selectCheckOutDate(driver, test_steps, checkOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the checkout date");
			test_steps.add("Successfully updated the checkout date");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the checkout date");
				test_steps.add("Successfully updated the checkout date");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to verify Extend or Reduce MRB primary reservation
	public void extendOrReduceMRBPrimary(WebDriver driver, String newCheckOut, String changeOption,
			ArrayList<String> test_steps) throws Exception {
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> changeStay = driver.findElements(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS));
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		Utility.ScrollToElement(threeDots.get(0), driver);
		threeDots.get(0).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[1]"), driver);
		changeStay.get(0).click();

		waitForSweetLoading(driver);
		// selectCheckOutDate(driver, test_steps, newCheckOut);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the checkout date");
			test_steps.add("Successfully updated the checkout date");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the checkout date");
				test_steps.add("Successfully updated the checkout date");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to verify Extend or Reduce MRB secondary reservation
	public void extendOrReduceMRBSecondary(WebDriver driver, String newCheckOut, String changeOption,
			ArrayList<String> test_steps) throws Exception {
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> changeStay = driver.findElements(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS));
		int size = threeDots.size();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + size + "]"),
				driver);
		Utility.ScrollToElement(threeDots.get(size - 1), driver);
		threeDots.get(size - 1).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[" + size + "]"),
				driver);
		changeStay.get(size - 1).click();

		waitForSweetLoading(driver);
		// selectCheckOutDate(driver, test_steps, newCheckOut);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the checkout date");
			test_steps.add("Successfully updated the checkout date");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the checkout date");
				test_steps.add("Successfully updated the checkout date");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to update reservation dates(moving the reservation) - Single
	// Reservation
	public void modifyReservationDates(WebDriver driver, String newCheckIn, String newCheckOut, String changeOption,
			String roomClass, ArrayList<String> test_steps) throws Exception {

		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);
		selectCheckInDate(driver, test_steps, newCheckIn);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		ArrayList<String> roomNumbers = new ArrayList<>();
		roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);

		selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the reservation dates dates (CheckIn and CheckOut dates)");
			test_steps.add("Successfully updated the reservation dates dates (CheckIn and CheckOut dates)");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the reservation dates (CheckIn and CheckOut dates)");
				test_steps.add("Successfully updated the reservation dates (CheckIn and CheckOut dates)");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	public void modifyReservationDatesForMRB(WebDriver driver, ArrayList<String> test_steps, String newCheckIn,
			String newCheckOut, String changeOption, int guest, String roomClass) throws Exception {
		String threeDotsXpath = "(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + guest + "]";
		String changeStayDatesXpath = "(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[" + guest + "]";
		Wait.waitForElementToBeClickable(By.xpath(threeDotsXpath), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(threeDotsXpath)), driver);
		driver.findElement(By.xpath(threeDotsXpath)).click();
		Wait.waitForElementToBeClickable(By.xpath(changeStayDatesXpath), driver);
		driver.findElement(By.xpath(changeStayDatesXpath)).click();

		waitForSweetLoading(driver);
		// selectCheckInDate(driver, test_steps, newCheckIn);
		// selectCheckOutDate(driver, test_steps, newCheckOut);
		selectCheckInDate(driver, test_steps, newCheckIn);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);

		ArrayList<String> roomNumbers = new ArrayList<>();
		roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);

		selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));

		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info(
					"Successfully updated the reservation dates (CheckIn and CheckOut dates) for MRB Primary guest");
			test_steps.add(
					"Successfully updated the reservation dates (CheckIn and CheckOut dates) for MRB Primary guest");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info(
						"Successfully updated the reservation dates (CheckIn and CheckOut dates) for MRB Primary guest");
				test_steps.add(
						"Successfully updated the reservation dates (CheckIn and CheckOut dates) for MRB Primary guest");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to update Guest count(Increase or decrease) - Single
	// Reservation
	public void modifyGuestCount(WebDriver driver, String guestType, int count, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);

		if (guestType.equalsIgnoreCase("Adults")) {
			elements.STAY_INFO_ADULTS_DROPDOWN.click();
			int existingAults = Integer.parseInt(elements.STAY_INFO_ADULTS_SELECTED.getText());
			int newAdults = existingAults + count;
			elements.STAY_INFO_ADULTS.clear();
			elements.STAY_INFO_ADULTS.sendKeys(Integer.toString(newAdults));
		} else if (guestType.equalsIgnoreCase("Children")) {
			elements.STAY_INFO_CHILDREN_DROPDOWN.click();
			int existingChildren = Integer.parseInt(elements.STAY_INFO_CHILDREN_SELECTED.getText());
			int newChildren = existingChildren + count;
			elements.STAY_INFO_CHILDREN.clear();
			elements.STAY_INFO_CHILDREN.sendKeys(Integer.toString(newChildren));
		}

		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the guest count for single reservation");
			test_steps.add("Successfully updated the guest count for single reservation");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the guest count for single reservation");
				test_steps.add("Successfully updated the guest count for single reservation");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to update Guest count(Increase or decrease) - Single
	// Reservation
	public void modifyGuestCount(WebDriver driver, String adults, String children, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);

		elements.STAY_INFO_ADULTS.clear();
		elements.STAY_INFO_ADULTS.sendKeys(adults);
		elements.STAY_INFO_CHILDREN.clear();
		elements.STAY_INFO_CHILDREN.sendKeys(children);

		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the guest count for single reservation");
			test_steps.add("Successfully updated the guest count for single reservation");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the guest count for single reservation");
				test_steps.add("Successfully updated the guest count for single reservation");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to update Guest count(Increase or decrease) - MRB Primary
	public void modifyGuestCountMRBPrimary(WebDriver driver, String adults, String children,
			ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> changeStay = driver.findElements(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS));
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		Utility.ScrollToElement(threeDots.get(0), driver);
		threeDots.get(0).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[1]"), driver);
		changeStay.get(0).click();
		waitForSweetLoading(driver);

		elements.STAY_INFO_ADULTS.clear();
		elements.STAY_INFO_ADULTS.sendKeys(adults);
		elements.STAY_INFO_CHILDREN.clear();
		elements.STAY_INFO_CHILDREN.sendKeys(children);

		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the guest count for MRB primary reservation");
			test_steps.add("Successfully updated the guest count for MRB primary reservation");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the guest count for MRB primary reservation");
				test_steps.add("Successfully updated the guest count for MRB primary reservation");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to update Guest count(Increase or decrease) - MRB Secondary
	public void modifyGuestCountMRBSecondary(WebDriver driver, String adults, String children,
			ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> changeStay = driver.findElements(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS));
		int size = threeDots.size();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + size + "]"),
				driver);
		Utility.ScrollToElement(threeDots.get(size - 1), driver);
		threeDots.get(size - 1).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[" + size + "]"),
				driver);
		changeStay.get(size - 1).click();
		waitForSweetLoading(driver);

		elements.STAY_INFO_ADULTS.clear();
		elements.STAY_INFO_ADULTS.sendKeys(adults);
		elements.STAY_INFO_CHILDREN.clear();
		elements.STAY_INFO_CHILDREN.sendKeys(children);

		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully updated the guest count for MRB Secondary reservation");
			test_steps.add("Successfully updated the guest count for MRB Secondary reservation");
		} catch (Exception e) {
			try {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
				clickChangeStayDetailsConfirmPopup(driver, "Yes");
				logger.info("Clicked on Yes on Confirmation popup");
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
				logger.info("Successfully updated the guest count for MRB Secondary reservation");
				test_steps.add("Successfully updated the guest count for MRB Secondary reservation");
			} catch (Exception e1) {
				logger.info(e1.toString());
			}
		}
	}

	// This method is to click Guest Info edit
	public void clickGuestInfo(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.EDIT_GUEST_INFO), driver);
		Utility.clickThroughJavaScript(driver, elements.EDIT_GUEST_INFO);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CONTACT_INFO), driver);
			logger.info("Clicked on Guest Info Edit button");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	// This method is to add additional guest from Guest info
	public void addMoreGuestInfo(WebDriver driver, String guestFirstName, String guestLastName, String email,
			String phone, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		clickGuestInfo(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADD_MORE_GUEST_INFO), driver);
		Utility.clickThroughJavaScript(driver, elements.ADD_MORE_GUEST_INFO);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 5);
			clickChangeStayDetailsConfirmPopup(driver, "Yes");
			logger.info("Clicked on Yes on Confirmation popup");
		} catch (Exception e) {
			logger.info("Confirmation popup not displayed");
		}

		elements.ADDITIONAL_GUEST_FIRST_NAME.sendKeys(guestFirstName);
		logger.info("Entered additional guest first name");
		elements.ADDITIONAL_GUEST_LAST_NAME.sendKeys(guestLastName);
		logger.info("Entered additional guest last name");
		elements.ADDITIONAL_GUEST_EMAIL.sendKeys(email);
		logger.info("Entered additional guest email");
		elements.ADDITIONAL_GUEST_PHONE.sendKeys(phone);
		logger.info("Entered additional guest phone number");
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully added additional guest");
			test_steps.add("Successfully added additional guest");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}


	public void modifyGuestInfo(WebDriver driver, String salutation, String guestFirstName, String guestLastName,
			String phoneNumber, String alternativePhone, String email, String address1, String address2,
			String address3, String city, String country, String state, String postalCode, boolean isGuesProfile,
			ArrayList<String> test_steps, String accountName, String accountType) throws Exception {

		clickGuestInfo(driver);

		enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName, phoneNumber,
				alternativePhone, email, accountName, accountType, address1, address2, address3, city, country, state,
				postalCode);

		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully Modified Guest Info");
			test_steps.add("Successfully Modified Guest Info");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	// This method is to assign room number for Unassigned reservation - Single
	// reservation
	public void assignRoomNumberFromStayInfo(WebDriver driver, String roomClass, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		String roomsDropDown = "//span[text()='" + roomClass + "']/..//button";
		String roomsAll = "//span[text()='" + roomClass + "']/..//button//following-sibling::div/ul/li/a/span[1]";

		clickEditStayInfo(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ASSIGN_ROOM_NUMBER), driver);
		elements.ASSIGN_ROOM_NUMBER.click();
		waitForSweetLoading(driver);
		driver.findElement(By.xpath(roomsDropDown)).click();
		List<WebElement> roomsList = driver.findElements(By.xpath(roomsAll));
		roomsList.get(1).click();
		logger.info("Clicked on first available room");
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully assigned room number for Single reservation");
			test_steps.add("Successfully assigned room number for Single reservation");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	// This method is to assign room number for Unassigned reservation - MRB Primary
	// reservation
	public void assignRoomNumberFromStayInfoMRBPrimary(WebDriver driver, String roomClass, ArrayList<String> test_steps)
			throws Exception {
		// String roomsDropDown = "(//span[text()='"+roomClass+": '])[1]/..//select";
		String roomsDropDown = "(//span[text()='" + roomClass + ": '])[1]/..//button";
		String roomsAll = "(//span[text()='" + roomClass
				+ ": '])[1]/..//button//following-sibling::div/ul/li/a/span[1]";

		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> assignRoom = driver.findElements(By.xpath(OR_ReservationV2.ASSIGN_ROOM_NUMBER));
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		Utility.ScrollToElement(threeDots.get(0), driver);
		threeDots.get(0).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.ASSIGN_ROOM_NUMBER + ")[1]"), driver);
		assignRoom.get(0).click();
		waitForSweetLoading(driver);

		driver.findElement(By.xpath(roomsDropDown)).click();
		List<WebElement> roomsList = driver.findElements(By.xpath(roomsAll));
		roomsList.get(1).click();
		logger.info("Clicked on first available room");
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully assigned room number for MRB Primary reservation");
			test_steps.add("Successfully assigned room number for MRB Primary reservation");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	// This method is to assign room number for Unassigned reservation - MRB
	// Secondary reservation
	public void assignRoomNumberFromStayInfoMRBSecondary(WebDriver driver, String roomClass,
			ArrayList<String> test_steps) throws Exception {

		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[1]"), driver);
		List<WebElement> threeDots = driver.findElements(By.xpath(OR_ReservationV2.STAY_INFO_THREE_DOTS));
		List<WebElement> roomassign = driver.findElements(By.xpath(OR_ReservationV2.ASSIGN_ROOM_NUMBER));
		int size = threeDots.size();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + size + "]"),
				driver);
		Utility.ScrollToElement(threeDots.get(size - 1), driver);
		threeDots.get(size - 1).click();
		Wait.waitForElementToBeClickable(By.xpath("(" + OR_ReservationV2.ASSIGN_ROOM_NUMBER + ")[" + size + "]"),
				driver);
		roomassign.get(size - 1).click();
		waitForSweetLoading(driver);

		String roomsDropDown = "(//span[text()='" + roomClass + ": '])[" + size + "]/..//button";
		String roomsAll = "(//span[text()='" + roomClass + ": '])[" + size
				+ "]/..//button//following-sibling::div/ul/li/a/span[1]";

		driver.findElement(By.xpath(roomsDropDown)).click();
		List<WebElement> roomsList = driver.findElements(By.xpath(roomsAll));
		roomsList.get(1).click();
		logger.info("Clicked on first available room");
		try {
			clickSaveStayInfo(driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver, 10);
			logger.info("Successfully assigned room number for MRB Secondary reservation");
			test_steps.add("Successfully assigned room number for MRB Secondary reservation");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	// Modify by Gangotri
	// This method is to split into separate reservation - MRB Secondary reservation
	public void splitIntoSeparateReservationMRBSecondary(WebDriver driver, ArrayList<String> test_steps, int index)
			throws Exception {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.STAY_INFO_THREE_DOTS);
		String path = "(//span[contains(@class,'icon custom-icon-more')]/..)[" + index + "]";
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).click();
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_SPLIT_INTO_SEPARATE_RESERVATION), driver);
		elements.STAY_INFO_SPLIT_INTO_SEPARATE_RESERVATION.click();
		try {
			Wait.WaitForElement(driver, OR_ReservationV2.dialog);
			clickChangeStayDetailsConfirmPopup(driver, "Yes");
			logger.info("Clicked on Yes on Confirmation popup");
		} catch (Exception e) {
			logger.info("Confirmation popup not displayed");
		}
		waitForSweetLoading(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
		logger.info("Separate reservation created from MRB");
		test_steps.add("Separate reservation created from MRB");
	}

	public void click_NewReservation(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
//		Wait.waitForSweetLoading(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservationButton), driver);
			res.CP_NewReservationButton.click();
			logger.info("Clicked on New Reservation button");
			test_steps.add("Clicked on New Reservation button");
//		waitForSweetLoading(driver);
		} catch (Exception e) {
			try {
				Elements_Accounts CreateAccount = new Elements_Accounts(driver);
				Wait.WaitForElement(driver, OR.NewReservation_Button);
				Wait.waitForElementToBeClickable(By.xpath(OR.NewReservation_Button), driver);
				CreateAccount.NewReservation_Button.click();
				test_steps.add("Clicking on new reservation button");
				logger.info("Clicked on New Reservation button");
			} catch (Exception e1) {
				Wait.WaitForElement(driver, OR.Verify_New_Reservation_Enabled);
				Wait.waitForElementToBeClickable(By.xpath(OR.Verify_New_Reservation_Enabled), driver);
				Utility.ScrollToElement(driver.findElement(By.xpath(OR.Verify_New_Reservation_Enabled)), driver);
				driver.findElement(By.xpath(OR.Verify_New_Reservation_Enabled)).click();
				test_steps.add("Clicking on new reservation button");
				logger.info("Clicked on New Reservation button");
			}
		}
	}

//	public void selectDate(WebDriver driver, ArrayList<String> test_steps, String Date)
//			throws InterruptedException {
//		String monthYear = Utility.parseDate(Date, "dd/MM/yyyy", "MMMM yyyy");
//		String header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
//		Wait.waitForElementToBeVisibile(By.xpath(header), driver);
//		String headerText = null, next = null, date;
//		if (Utility.checkFirstDateOrMonthOrYearIsGreaterOrEqualToSecond(monthYear, header, "MMMM yyyy")) {
//			
//		} else {
//
//		}
//		
//		
//		
//		
////		String day = Utility.getDay(Date);
////		logger.info(monthYear);
//		for (int i = 1; i <= 12; i++) {
//			header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
//			headerText = driver.findElement(By.xpath(header)).getText();
//			if (headerText.equalsIgnoreCase(monthYear)) {
//				date = "//td[contains(@class,'day') and text()='" + day + "']";
//				Wait.WaitForElement(driver, date);
//				int size = driver.findElements(By.xpath(date)).size();
//				for (int k = 1; k <= size; k++) {
//					date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + k + "]";
//					String classText = driver.findElement(By.xpath(date)).getAttribute("class");
//					if (!classText.contains("old")) {
//						driver.findElement(By.xpath(date)).click();
//						test_steps.add("Selecting checkin date : " + Date);
//						logger.info("Selecting checkin date : " + Date);
//						break;
//					}
//				}
//				break;
//			} else {
//				next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
//				Wait.WaitForElement(driver, next);
//				driver.findElement(By.xpath(next)).click();
//				Wait.wait2Second();
//			}
//		}
//	}
//	
//	public void selectCheckInDate(WebDriver driver, ArrayList<String> test_steps, String checkInDate)
//			throws InterruptedException {
//		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
//		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_CheckInDate), driver);
//		res.CP_NewReservation_CheckInDate.click();
//		selectDate(driver, test_steps, checkInDate);
//	}
//	
//	public void selectCheckOutDate(WebDriver driver, ArrayList test_steps, String checkOutDate)
//			throws InterruptedException {
//		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
//		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_CheckOutDate);
//		res.CP_NewReservation_CheckOutDate.click();
//		selectDate(driver, test_steps, checkOutDate);
//	}

	public void enter_Adults(WebDriver driver, ArrayList<String> test_steps, String adults, int count) {
		driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_Adults + ")[" + count + "]")).clear();
		driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_Adults + ")[" + count + "]"))
				.sendKeys(adults);
		test_steps.add("Entered No of adults as : <b>" + adults + "</b>");
		logger.info("Enter No of adults : " + adults);
	}

	public void enter_Adults(WebDriver driver, ArrayList<String> test_steps, String adults) {
		enter_Adults(driver, test_steps, adults, 1);
	}

	public void enter_Children(WebDriver driver, ArrayList<String> test_steps, String children, int count) {
		driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_Children + ")[" + count + "]")).clear();
		driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_Children + ")[" + count + "]"))
				.sendKeys(children);
		test_steps.add("Entered No of children as : <b>" + children + "</b>");
		logger.info("Enter No of children : " + children);
	}

	public void enter_Children(WebDriver driver, ArrayList<String> test_steps, String children) {
		enter_Children(driver, test_steps, children, 1);
	}

	public void select_Rateplan(WebDriver driver, ArrayList<String> test_steps, String ratePlan, String promoCode,
			int count) throws Exception {
		logger.info(OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN);
		Wait.WaitForElement(driver, "(" + OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN + ")[" + count + "]");
		driver.findElement(By.xpath("(" + OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN + ")[" + count + "]"));
		driver.findElement(By.xpath("(" + OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN + ")[" + count + "]")).click();
		driver.findElement(By.xpath("(" + OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN + ")[" + count + "]"))
				.sendKeys(ratePlan);
		driver.findElement(By.xpath("(//b[contains(text(),'" + ratePlan + "')]/../../../..)[" + count + "]")).click(); // for
																														// mrb

		test_steps.add("Selecting the rateplan as : <b>" + ratePlan + "</b>");
		logger.info("Selecting the rateplan : " + ratePlan);
		Wait.wait5Second();

		test_steps.add("Promo Code : <b>" + promoCode + "</b>");
		logger.info("Promo Code : <b>" + promoCode + "</b>");
		Wait.wait5Second();
		if (Utility.validateDate(promoCode)) {
			Wait.WaitForElement(driver, "(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]");
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]"))
					.clear();
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]"))
					.sendKeys(promoCode);
			test_steps.add("Enter promocode as : <b>" + promoCode + "</b>");
			logger.info("Enter promocode : " + promoCode);
		}
	}

	public void select_Rateplan(WebDriver driver, ArrayList<String> test_steps, String ratePlan, String promoCode) {
		try {
			select_Rateplan(driver, test_steps, ratePlan, promoCode, 1);
		} catch (Exception e) {
			logger.info(e);
		}
	}

	public void clickOnFindRooms(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("Clicking on FindRooms........");
		logger.info("Clicking on FindRooms........");

		try {
			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_FindRooms);
			res.CP_NewReservation_FindRooms.click();

		} catch (Exception e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", res.CP_NewReservation_FindRooms);
		}
		test_steps.add("Clicked on FindRooms");
		logger.info("Clicked on FindRooms");
		waitForSweetLoading(driver);
	}

	public void enterAccount(WebDriver driver, ArrayList<String> test_steps, String Account, String AccountType)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_INFO_ACCOUNT), driver);
		if (Utility.validateString(Account)) {
			res.GUEST_INFO_ACCOUNT.clear();
			res.GUEST_INFO_ACCOUNT.sendKeys(Account);
			test_steps.add("Enter Account : <b>" + Account + "</b>");
			logger.info("Enter Account : " + Account);
			Wait.wait2Second();
			Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + Account.trim() + "')]/../../.."),
					driver);
			driver.findElement(By.xpath("//b[contains(text(),'" + Account.trim() + "')]/../../..")).click();
			clickOnYesButtonOnGuestInfoReplaceForAccount(driver, test_steps);
			clickOnYesButtonOnPoliciesApplicableForAccount(driver, test_steps, "");
			if (Utility.validateString(AccountType)) {
				setPaymentMethodAsAccount(driver, AccountType);
			}
			verifyAccountIsAssociatedInReservation(driver, test_steps, Account);
		} else {
			logger.info("No Account");
		}
	}

	public void verifyAccountIsAssociatedInReservation(WebDriver driver, ArrayList<String> test_steps, String Account) {
		// String acc = "//label[text()='Account Name ']/preceding-sibling::input";
		String acc = "//a/span[contains(@data-bind,'accountInfo.name')]";
		Wait.waitForElementToBeVisibile(By.xpath(acc), driver);
		String accName = driver.findElement(By.xpath(acc)).getText();
		if (accName.contains(Account)) {
			test_steps.add("Account successfully associated : " + Account);
			logger.info("Account successfully associated : " + Account);
		}
	}

	public void setPaymentMethodAsAccount(WebDriver driver, String AccountType) {
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_PaymentMethod), driver, 30);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		res.CP_NewReservation_PaymentMethod.click();
		String type = null;
		if (AccountType.contentEquals("Corp") || AccountType.contains("corp") || AccountType.contains("Corp")) {
			type = "Account (Corp/Member)";
		} else if (AccountType.contentEquals("Unit Owner") || AccountType.contains("Unit")
				|| AccountType.contains("unit owner")) {
			type = "Account (Unit Owner)";
		}
		String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
				+ type + "')]";
		Wait.waitForElementToBeVisibile(By.xpath(paymentMethod), driver, 30);
		driver.findElement(By.xpath(paymentMethod)).click();
	}

	public void clickOnYesButtonOnPoliciesApplicableForAccount(WebDriver driver, ArrayList<String> test_steps,
			String isVisible) {
		String policyYes = "//div[contains(text(),'Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation?')]/following-sibling::div//button[contains(text(),'Yes')]";
		try {
			Wait.waitForElementToBeVisibile(By.xpath(policyYes), driver, 30);
			if (isVisible.equalsIgnoreCase("yes")) {
				assertTrue(driver.findElement(By.xpath(policyYes)).isDisplayed());
			}
			driver.findElement(By.xpath(policyYes)).click();
			test_steps.add(
					"Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation? : Yes");
			logger.info(
					"Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation? : Yes");
			Wait.wait2Second();
		} catch (Exception e) {
			if (isVisible.equalsIgnoreCase("no")) {
				assertTrue(true);
				logger.info(
						"Replace guest info popup is not displayed as new policies are not applicable on account associated");
				test_steps.add(
						"Replace guest info popup is not displayed as new policies are not applicable on account associated");
			}
		}
	}

	public void clickOnYesButtonOnGuestInfoReplaceForAccount(WebDriver driver, ArrayList<String> test_steps) {
		try {
			String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'Yes')]";
			Wait.waitForElementToBeVisibile(By.xpath(dataYes), driver, 30);
			try {
				driver.findElement(By.xpath(dataYes)).click();
			} catch (Exception e) {
				Utility.scrollAndClick(driver, By.xpath(dataYes));
			}

			test_steps.add(
					"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
			logger.info(
					"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
			Wait.wait2Second();
		} catch (Exception e) {
			logger.info("Replace guest info popup is not displayed");
		}
	}

	public void clickNext(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Next);
		Utility.ScrollToElement(res.CP_NewReservation_Next, driver);
		try {
			Utility.clickThroughJavaScript(driver, res.CP_NewReservation_Next);
		} catch (Exception e) {
			res.CP_NewReservation_Next.click();
		}

		test_steps.add("Clicking on next button");
		logger.info("Clicking on next button");
		int count = 0;
		while (true) {
			if (driver.findElement(By.xpath(OR_ReservationV2.NEW_RESERVATION_GUEST_INFO_SECTION)).isDisplayed()) {
				break;
			} else if (count == 20) {
				break;
			} else {
				count++;
				Wait.wait2Second();
			}
		}

		/*
		 * while (true) { if
		 * (driver.findElement(By.xpath("//span[contains(text(),'Contact Info')]")).
		 * isDisplayed()) { break; } else if (count == 20) { break; } else { count++;
		 * Wait.wait2Second(); } }
		 */
		logger.info("Waited to loading symbol");
	}

	
	public void clickOnGuestProfilePopupSaveButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_PROFILE_SAVE);
		try {
			Utility.ScrollToElement(res.GUEST_PROFILE_SAVE, driver);
			res.GUEST_PROFILE_SAVE.click();
			Wait.wait5Second();
		} catch (Exception e) {
			Utility.scrollAndClick(driver, By.xpath(OR_ReservationV2.GUEST_PROFILE_SAVE));
		}
		test_steps.add("====== Clicking on Save Button on Guest Profile Popup on New Reservation Page======");
		logger.info("====== Clicking on Save Button on Guest Profile Popup on New Reservation Page======");
	}
	

	
	public void enter_GuestName(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName, boolean isPrimaryInfoCopy) throws InterruptedException {
		try {
			if (guestFirstName.split("\\|").length > 1) {
				// enter guest info for MRB reservation
				enter_Salutation(driver, test_steps, salutation.split("\\|")[0]);
				enter_GuestName(driver, test_steps, guestFirstName.split("\\|")[0], guestLastName.split("\\|")[0]);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);
				Wait.wait1Second();
				if (isPrimaryInfoCopy) {
					// if copy flag is on, then copy guestinfo from primary room to other rooms
					clickCopyGuestFromPrimaryRoom(driver, test_steps);
				} else {
					clickOnCreateGuestButton(driver, test_steps);
					turnOnOffcreateGuestProfileToggle(driver, test_steps, false);
					enter_Salutation(driver, test_steps, salutation.split("\\|")[1]);
					enter_GuestName(driver, test_steps, guestFirstName.split("\\|")[1], guestLastName.split("\\|")[1]);
					clickOnGuestProfilePopupSaveButton(driver, test_steps);
					Wait.wait1Second();
				}
			} else {
				// Enter data for single reservation
				enter_Salutation(driver, test_steps, salutation);
				enter_GuestName(driver, test_steps, guestFirstName, guestLastName);
			}
		} catch (Exception e) {
			logger.info(e);
		} catch (Error e) {
			e.printStackTrace();
			logger.info(e);
		}
	}

	public void enter_GuestName(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName) throws InterruptedException {
		enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
	}

	public void enter_AdditionalGuestNamesForMRB(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName) {
		String[] salu = salutation.split("\\|");
		String[] guestFName = guestFirstName.split("\\|");
		String[] guestLName = guestLastName.split("\\|");
		int size = guestFName.length;
		HashMap<String, String> data = new HashMap<>();
		int j = 0;
		for (int i = 2; i <= size; i++) {
			j = i - 1;
			String saluta = "(//span[text()='Additional Guest'])[" + (i - 1)
					+ "]/../../../../../..//span[text()='Select']/..";
			String slaut = "(//span[text()='Additional Guest'])[" + (i - 1)
					+ "]/../../../../../..//span[text()='Select']/../" + "following-sibling::div//li//span[text()='"
					+ salu[i - 1] + "']";
			driver.findElement(By.xpath(saluta)).click();
			Wait.waitForElementToBeVisibile(By.xpath(slaut), driver);
			driver.findElement(By.xpath(slaut)).click();

			String fname = "(//label[text()='Guest ']/../input)[" + (i - 1) + "]";
			driver.findElement(By.xpath(fname)).clear();
			driver.findElement(By.xpath(fname)).sendKeys(guestFName[i - 1]);
			test_steps.add("Guest First Name : " + guestFName[i - 1]);
			logger.info("Guest First Name : " + guestFName[i - 1]);

			String lname = "(//span[text()='Additional Guest'])[" + (i - 1)
					+ "]/../../../../../..//label[text()='Last Name']/../input";
			Wait.WaitForElement(driver, lname);
			driver.findElement(By.xpath(lname)).clear();
			driver.findElement(By.xpath(lname)).sendKeys(guestLName[i - 1]);
			test_steps.add("Guest Last Name : " + guestLName[i - 1]);
			logger.info("Guest Last Name : " + guestLName[i - 1]);
			data.put("Add_Guest_FirstName" + j, guestFName[i - 1]);
			data.put("Add_Guest_LastName" + j, guestLName[i - 1]);

		}

	}

	public void enter_Salutation(WebDriver driver, ArrayList<String> test_steps, String salutation) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Utility.ScrollToElement(res.CP_NewReservation_GuestSalutation, driver);
			res.CP_NewReservation_GuestSalutation.click();
			String sal = null;
			sal = "(//div[contains(@data-bind,'isCreateGuestProfileModal')]//span[contains(text(),'" + salutation
					+ "')])[1]";
			Utility.app_logs.info("sal: " + sal);
			Wait.WaitForElement(driver, sal);
			try {
				driver.findElement(By.xpath(sal)).click();
			} catch (Exception e1) {
				Utility.scrollAndClick(driver, By.xpath(sal));
			}
		} catch (Exception ex) {
			logger.info(ex);
		}
	}

	public void enter_GuestName(WebDriver driver, ArrayList<String> test_steps, String guestFirstName,
			String guestLastName) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_GuestFirstName), driver);
		res.CP_NewReservation_GuestFirstName.clear();
		res.CP_NewReservation_GuestFirstName.sendKeys(guestFirstName);
		test_steps.add("Guest First Name : <b>" + guestFirstName + "</b>");
		logger.info("Guest First Name : <b>" + guestFirstName + "</b>");

		res.CP_NewReservation_GuestLastName.clear();
		res.CP_NewReservation_GuestLastName.sendKeys(guestLastName);
		test_steps.add("Guest Last Name : <b>" + guestLastName + "</b>");
		logger.info("Guest Last Name : <b>" + guestLastName + "</b>");
		res.CP_NewReservation_GuestLastName.sendKeys(Keys.TAB);
		Wait.wait5Second();
	}

	public void enter_Phone(WebDriver driver, ArrayList<String> test_steps, String phoneNumber,
			String alternativePhone) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Phone);

			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Phone);
			try {
				res.CP_NewReservation_Phone.clear();
				res.CP_NewReservation_Phone.sendKeys(phoneNumber);
			} catch (Exception e) {
				res.CP_NewReservation_Phone2.clear();
				res.CP_NewReservation_Phone2.sendKeys(phoneNumber);
			}
			test_steps.add("Enter Phone Number : " + phoneNumber);
			logger.info("Enter Phone Number : " + phoneNumber);

			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_AltenativePhone);
			res.CP_NewReservation_AltenativePhone.clear();
			res.CP_NewReservation_AltenativePhone.sendKeys(alternativePhone);
			test_steps.add("Enter AltenativePhone Number : " + alternativePhone);
			logger.info("Enter AltenativePhone Number : " + alternativePhone);
		} catch (Exception e) {

		}
	}

	public void enter_Email(WebDriver driver, ArrayList<String> test_steps, String email) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Email);
		res.CP_NewReservation_Email.click();
		res.CP_NewReservation_Email.sendKeys(email);
		test_steps.add("Enter Email : " + email);
		logger.info("Enter Email : " + email);
	}

	public void enter_Address(WebDriver driver, ArrayList<String> test_steps, String address1, String address2,
			String address3) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Address1);
		res.CP_NewReservation_Address1.click();
		res.CP_NewReservation_Address1.sendKeys(address1);
		res.CP_NewReservation_Address1.sendKeys(Keys.ENTER);
		res.CP_NewReservation_Address1.sendKeys(Keys.ENTER);
		test_steps.add("Enter Address1 : " + address1);
		logger.info("Enter Address1 : " + address1);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Address2);
		res.CP_NewReservation_Address2.clear();
		res.CP_NewReservation_Address2.sendKeys(address2);
		// res.CP_NewReservation_Address2.sendKeys(Keys.ESCAPE);
		test_steps.add("Enter Address2 : " + address2);
		logger.info("Enter Address1 : " + address2);

//		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Address3);
//		res.CP_NewReservation_Address3.clear();
//		res.CP_NewReservation_Address3.sendKeys(address3);
//		test_steps.add("Enter Address3 : " + address3);
//		logger.info("Enter Address3 : " + address3);
	}

	public void enter_City(WebDriver driver, ArrayList<String> test_steps, String city) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_City);
		res.CP_NewReservation_City.click();
		res.CP_NewReservation_City.sendKeys(city);
		test_steps.add("Enter City : " + city);
		logger.info("Enter City : " + city);
	}

	public void select_Country(WebDriver driver, ArrayList<String> test_steps, String country) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Country);
		res.CP_NewReservation_Country.click();

		String cntry = "//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[@name='country']/following-sibling::div//ul/li//span[contains(text(),'"
				+ country.trim() + "')]";
		Wait.WaitForElement(driver, cntry);
		driver.findElement(By.xpath(cntry)).click();
		test_steps.add("Selected Country : " + country);
		logger.info("Selected Country : " + country);
	}

	public void select_State(WebDriver driver, ArrayList<String> test_steps, String state) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_State);

		if (Utility.validateString(state)) {
			if (driver.findElements(By.xpath("(//select[@name='state'])[2]/option")).size() > 1) {
				res.CP_NewReservation_State.click();
				String stateSelect = "//div[contains(@data-bind,'isCreateGuestProfileModal')]//select[@name='state']/following-sibling::div//ul/li//span[contains(text(),'"
						+ state.trim() + "')]";
				Wait.WaitForElement(driver, stateSelect);
				driver.findElement(By.xpath(stateSelect)).click();
				test_steps.add("Selected City : " + state);
				logger.info("Selected City : " + state);
			} else {
				test_steps.add("State field disabled");
				logger.info("State field disabled");
			}
		}

	}

	public void enter_PostalCode(WebDriver driver, ArrayList<String> test_steps, String postalCode) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_PostalCode);
		res.CP_NewReservation_PostalCode.clear();
		res.CP_NewReservation_PostalCode.sendKeys(postalCode);
		test_steps.add("Enter PostalCode : " + postalCode);
		logger.info("Enter PostalCode : " + postalCode);
	}

	public void uncheck_CreateGuestProfile(WebDriver driver, ArrayList<String> test_steps, boolean isGuestProfile) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_CreateGuestProfile);
		if (isGuestProfile) {
			if (!res.CP_NewReservation_CreateGuestProfileCheckbox.isSelected()) {
				res.CP_NewReservation_CreateGuestProfile.click();
				test_steps.add("Create Guest Profile selected");
				logger.info("Create Guest Profile selected");
			} else {
				test_steps.add("Create Guest Profile selected");
				logger.info("Create Guest Profile selected");
			}
		} else {
			if (res.CP_NewReservation_CreateGuestProfileCheckbox.isSelected()) {
				res.CP_NewReservation_CreateGuestProfile.click();
				test_steps.add("Create Guest Profile unselected");
				logger.info("Create Guest Profile unselected");
			} else {
				test_steps.add("Create Guest Profile unselected");
				logger.info("Create Guest Profile unselected");
			}
		}
	}

	public void enter_MailingAddress(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone, String email,
			String account, String accountType, String address1, String address2, String address3, String city,
			String country, String state, String postalCode) throws InterruptedException {
		enter_Salutation(driver, test_steps, salutation);
		enter_GuestName(driver, test_steps, guestFirstName, guestLastName);
		if (Utility.validateString(address1)) {
			enter_Address(driver, test_steps, address1, address2, address3);
		}
		if (Utility.validateString(city)) {
			enter_City(driver, test_steps, city);
		}
		if (Utility.validateString(country)) {
			select_Country(driver, test_steps, country);
		}
		if (Utility.validateString(state)) {
			select_State(driver, test_steps, state);
		}
		if (Utility.validateString(postalCode)) {
			enter_PostalCode(driver, test_steps, postalCode);
		}
		if (Utility.validateString(phoneNumber)) {
			enter_Phone(driver, test_steps, phoneNumber, altenativePhone);
		}
		if (Utility.validateString(email)) {
			enter_Email(driver, test_steps, email);
		}
		if (Utility.validateString(account)) {
			enterAccount(driver, test_steps, account, accountType);
		}
	}

	public void enter_MailingAddress_OnUnchecked_CreateGuestProfile(WebDriver driver, ArrayList<String> test_steps,
			String salutation, String guestFirstName, String guestLastName) throws InterruptedException {
		enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName);
		uncheck_CreateGuestProfile(driver, test_steps, false);
	}

	public void enter_PaymentDetails(WebDriver driver, ArrayList<String> test_steps, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String accountName) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_PaymentMethod, driver);
		res.CP_NewReservation_PaymentMethod.click();

		if (paymentType.equalsIgnoreCase("Swipe")) {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'MC')]";
			Wait.WaitForElement(driver, paymentMethod);
			driver.findElement(By.xpath(paymentMethod)).click();
			res.CP_NewReservation_Swipe.click();
			test_steps.add("Clicking in Swipe");
			logger.info("Clicking in Swipe");
			Wait.wait1Second();
			String CC = "(//label[text()='Credit Card Number']/preceding-sibling::input)[2]";
			Wait.WaitForElement(driver, CC);
			driver.findElement(By.xpath(CC)).sendKeys(cardNumber);
			test_steps.add("Enter CardNumber : " + cardNumber);
			logger.info("Enter CardNumber : " + cardNumber);
			res.CP_NewReservation_CardNumber.sendKeys(Keys.TAB);
			Wait.wait2Second();
		} else {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
					+ paymentType.trim() + "')]";
			Wait.WaitForElement(driver, paymentMethod);
			driver.findElement(By.xpath(paymentMethod)).click();
			test_steps.add("Selected PaymentType : " + paymentType);
			logger.info("Selected PaymentType : " + paymentType);
			if (paymentType.trim().equalsIgnoreCase("MC") || paymentType.trim().equalsIgnoreCase("Visa")
					|| paymentType.trim().equalsIgnoreCase("Amex") || paymentType.trim().equalsIgnoreCase("Discover")
					|| paymentType.trim().equalsIgnoreCase("Masterrr")) {
				res.CP_NewReservation_CardNumber.sendKeys(cardNumber);
				test_steps.add("Enter CardNumber : " + cardNumber);
				logger.info("Enter CardNumber : " + cardNumber);
				res.CP_NewReservation_NameOnCard.sendKeys(nameOnCard);
				test_steps.add("Enter Name On Card : " + nameOnCard);
				logger.info("Enter Name On Card : " + nameOnCard);
				res.CP_NewReservation_CardExpDate.sendKeys(cardExpDate);
				test_steps.add("Enter Card ExpDate : " + cardExpDate);
				logger.info("Enter Card ExpDate : " + cardExpDate);
			} else if (paymentType.trim().equalsIgnoreCase("Gift Certificate")) {
				res.CP_NewReservation_GiftCertNumber.click();
				res.CP_NewReservation_GiftCertNumber.sendKeys(cardNumber);
				test_steps.add("Enter Gift Card Number : " + cardNumber);
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]"), driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]")).click();
			} else if (paymentType.trim().equalsIgnoreCase("Account (House Account)")) {
				try {
					res.CP_NewReservation_HouseAccountName.sendKeys(accountName);
				} catch (Exception e) {
					String path = "(//label[text()='Account Name']/preceding-sibling::input)[2]";
					driver.findElement(By.xpath(path)).sendKeys(accountName);
				}
				test_steps.add("Enter House Account name : " + accountName);
				Wait.wait2Second();
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + accountName.trim() + "')]/../../.."),
						driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + accountName.trim() + "')]/../../..")).click();
			} else if (paymentType.trim().equalsIgnoreCase("Reservation")) {
				res.CP_NewReservation_ResNumberPayment.sendKeys(cardNumber);
				test_steps.add("Enter Reservation number as Payment : " + cardNumber);
				Wait.wait2Second();
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../.."),
						driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../..")).click();
			}
		}
		// This webelement - 'Create Guest Profile' checkbox have been removed from
		// Guest Info section , so this line is not needed
		// Wait.WaitForElement(driver,
		// OR_ReservationV2.CP_NewReservation_CreateGuestProfile);

		if (!res.CP_NewReservation_SameAsMailingAddress.isSelected()) {
			driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div/label"))
					.click();
		}
	}

	public void enter_TaxExemptDetails(WebDriver driver, ArrayList<String> test_steps, boolean isTaxExempt,
			String taxExemptID) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_TaxExemptionOption);
		if (isTaxExempt) {
			res.CP_NewReservation_TaxExemptionOption.click();
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_TaxExempt), driver);
			res.CP_NewReservation_TaxExempt.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_TaxExemptID), driver);
			res.CP_NewReservation_TaxExemptID.sendKeys(taxExemptID);
			logger.info("Entered Tax Exempt details");
			test_steps.add("Entered Tax Exempt details");
		}
	}

	public void enter_MarketingInfoDetails(WebDriver driver, ArrayList<String> test_steps, String travelAgent,
			String marketSegment, String referral, String useTravelAgentInfo) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_TravelAgent);

		if (!(travelAgent.equalsIgnoreCase("") || travelAgent.isEmpty()) || travelAgent == null) {
			res.CP_NewReservation_TravelAgent.sendKeys(travelAgent);
			Wait.wait2Second();
			String account = "//b[contains(text(),'" + travelAgent.trim() + "')]/../../..";
			Wait.WaitForElement(driver, account);
			driver.findElement(By.xpath(account)).click();
			if (useTravelAgentInfo.equalsIgnoreCase("Yes")) {
				String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'Yes')]";
				Wait.WaitForElement(driver, dataYes);
				driver.findElement(By.xpath(dataYes)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				waitForSweetLoading(driver);
			} else {
				String dataNo = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'No')]";
				Wait.WaitForElement(driver, dataNo);
				driver.findElement(By.xpath(dataNo)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				waitForSweetLoading(driver);
			}

		}
		if (!referral.isEmpty()) {
			try {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Referral), driver, 2);
				Utility.ScrollToElement(res.CP_NewReservation_Referral, driver);
				res.CP_NewReservation_Referral.click();
			} catch (Exception e) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Referral2), driver, 2);
				Utility.ScrollToElement(res.CP_NewReservation_Referral2, driver);
				res.CP_NewReservation_Referral2.click();
			}
			String ref = "(//label[text()='Referral']/preceding-sibling::div//ul/li//span[text()='" + referral.trim()
					+ "'])";
			logger.info(ref);
			Wait.waitForElementToBeClickable(By.xpath(ref), 10, driver);
			Utility.ScrollToElement(driver.findElement(By.xpath(ref)), driver);
			logger.info(ref);
			driver.findElement(By.xpath(ref)).click();
			test_steps.add("Selected Referral as : " + referral);
			logger.info("Selected Referral as : " + referral);
		}
		if (!marketSegment.isEmpty()) {
			try {
				Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Market);
				Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
				res.CP_NewReservation_Market.click();
				/*
				 * String market =
				 * "//label[text()='Market']/preceding-sibling::div//ul/li//span[contains(text(),'"
				 * + marketSegment.trim() + "')]";
				 */
			} catch (Exception e) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Market), driver, 2);
				Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
				res.CP_NewReservation_Market.click();
			}
			String market = "(//label[text()='Market']/preceding-sibling::div//ul/li//span[text()='"
					+ marketSegment.trim() + "'])";
			if (marketSegment != "") {
				Wait.WaitForElement(driver, market);
				driver.findElement(By.xpath(market)).click();
				test_steps.add("Selected MarketSegment as : " + marketSegment);
				logger.info("Selected MarketSegment as : " + marketSegment);

				logger.info("Entered Marketing Info details");
				test_steps.add("Entered Marketing Info details");
			}
		}
	}

	// This method is to add additional guest while creating new reservation
	public void enter_AdditionalGuestInfo(WebDriver driver, String guestFirstName, String guestLastName, String email,
			String phone, ArrayList<String> test_steps) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADD_MORE_GUEST_INFO), driver);
		Utility.clickThroughJavaScript(driver, elements.ADD_MORE_GUEST_INFO);

		elements.ADDITIONAL_GUEST_FIRST_NAME.sendKeys(guestFirstName);
		logger.info("Entered additional guest first name");
		elements.ADDITIONAL_GUEST_LAST_NAME.sendKeys(guestLastName);
		logger.info("Entered additional guest last name");
		elements.ADDITIONAL_GUEST_EMAIL.sendKeys(email);
		logger.info("Entered additional guest email");
		elements.ADDITIONAL_GUEST_PHONE.sendKeys(phone);
		logger.info("Entered additional guest phone number");
	}

	public void enterNotes(WebDriver driver, ArrayList<String> test_steps, String noteType, String subject,
			String description) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_AddNotes), driver);
//		if (isAddNotes.equalsIgnoreCase("Yes")) {
		test_steps.add("******************* Adding Notes **********************");
		logger.info("******************* Adding Notes **********************");
		res.CP_AddNotes.click();
		test_steps.add("Click on Add Notes");
		logger.info("Click on Add Notes");
		Wait.wait5Second();
		if (res.CP_NoteFor.isDisplayed()) {
			res.CP_NoteFor.click();
			driver.findElement(By.xpath("//label[text()='CREATE NOTES FOR']/../../..//span[text()='All']")).click();
		}
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NoteType);
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", res.CP_NoteType);

		} catch (Exception e) {
			res.CP_NoteType.click();
		}

		String strNoteType = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button/..//div//li/a/span[text()='"
				+ noteType.trim() + "']";
		Wait.WaitForElement(driver, strNoteType);
		driver.findElement(By.xpath(strNoteType)).click();
		test_steps.add("Select Note Type : " + noteType);
		logger.info("Select Note Type : " + noteType);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NoteSubject);
		res.CP_NoteSubject.sendKeys(subject);
		test_steps.add("Enter subject : " + subject);
		logger.info("Enter subject : " + subject);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NoteDescription);
		res.CP_NoteDescription.sendKeys(description);
		test_steps.add("Enter Description : " + description);
		logger.info("Enter Description : " + description);

		String user = "//input[starts-with(@data-bind,'value: updatedBy')]";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		user = (String) js.executeScript("return arguments[0].value", driver.findElement(By.xpath(user)));

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NoteAddButton);
		res.CP_NoteAddButton.click();
		test_steps.add("Click on Add");
		logger.info("Click on Add");

		String noteverify = "//td[text()='" + noteType.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Sucessfully added Note : " + subject);
		logger.info("Sucessfully added Note : " + subject);

		test_steps.add("Verified Note Type : " + noteType);
		logger.info("Verified Note Type : " + noteType);

		noteverify = "//td[text()='" + subject.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note Subject : " + subject);
		logger.info("Verified Note Subject : " + subject);

		noteverify = "//td[text()='" + description.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note Description : " + description);
		logger.info("Verified Note Description : " + description);

		noteverify = "//td[text()='" + user.trim() + "']";
		Wait.WaitForElement(driver, noteverify);
		test_steps.add("Verified Note updated by : " + user);
		logger.info("Verified Note updated by : " + user);
//		}
	}

	public boolean verifyTaskSection(WebDriver driver, ArrayList<String> test_steps) {
		test_steps.add("******************* Verify Task section **********************");
		String task = "//button[text()='ADD TASK']";
		if (driver.findElement(By.xpath(task)).isDisplayed()) {
			test_steps.add("Task Section is displayed");
			logger.info("Task Section is displayed");
			return true;
		} else {
			test_steps.add("Task Section is not displayed");
			logger.info("Task Section is not displayed");
			return false;
		}
	}

	public void enterTask(WebDriver driver, ArrayList<String> test_steps, String taskCategory, String taskType,
			String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee, String taskStatus)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_AddTask);
//		if (isTask.equalsIgnoreCase("Yes")) {
		test_steps.add("******************* Adding Task **********************");
		logger.info("******************* Adding Task **********************");
		res.CP_AddTask.click();
		test_steps.add("Click on Add Task");
		logger.info("Click on Add Task");
		Wait.wait5Second();
		try {
			String taskFor = "//label[text()='Create Task For']/..//button";
			Wait.waitForElementToBeVisibile(By.xpath(taskFor), driver, 3);
			WebElement createTaskFor = driver.findElement(By.xpath(taskFor));
			createTaskFor.click();
			driver.findElement(By.xpath("//span[text()='ALL']")).click();// label[text()='Create Task
																			// For']/../../..//span[text()='ALL']
		} catch (Exception e) {

		}
		String taskCategoryArrow = "//label[text()='Category']/..//button";
		Wait.WaitForElement(driver, taskCategoryArrow);
		driver.findElement(By.xpath(taskCategoryArrow)).click();

		String strTaskCategory = "//label[text()='Category']/..//div/ul//span[text()='" + taskCategory.trim() + "']";
		Wait.WaitForElement(driver, strTaskCategory);
		driver.findElement(By.xpath(strTaskCategory)).click();
		test_steps.add("Select Task Category : " + taskCategory);
		logger.info("Select Task Category : " + taskCategory);

		String strTaskType = "(//div[contains(@class,'reservation-task-modal')]//label[text()='Type']//..//div//button)";
		Wait.WaitForElement(driver, strTaskType);
		driver.findElement(By.xpath(strTaskType)).click();
		Wait.wait5Second();
		strTaskType = "//h4[text()='Add Task']/../following-sibling::div//label[text()='Type']/..//div/ul//span[text()='"
				+ taskType.trim() + "']";
		logger.info(strTaskType);
		Wait.WaitForElement(driver, strTaskType);
		driver.findElement(By.xpath(strTaskType)).click();
		test_steps.add("Select Task Type : " + taskType);
		logger.info("Select Task Type : " + taskType);

		String strTaskDetails = "//label[text()='Details']/preceding-sibling::textarea";
		Wait.WaitForElement(driver, strTaskDetails);
		driver.findElement(By.xpath(strTaskDetails)).sendKeys(taskDetails);
		test_steps.add("Select Task Details : " + taskDetails);
		logger.info("Select Task Details : " + taskDetails);

		String strTaskRemarks = "//label[text()='Remarks']/preceding-sibling::textarea";
		Wait.WaitForElement(driver, strTaskRemarks);
		driver.findElement(By.xpath(strTaskRemarks)).sendKeys(taskRemarks);
		test_steps.add("Enter Task Remarks : " + taskRemarks);
		logger.info("Enter Task Remarks : " + taskRemarks);

		String dueCalender = "//label[text()='Due On']/..//i";
		Wait.WaitForElement(driver, dueCalender);
		driver.findElement(By.xpath(dueCalender)).click();
		if (!Utility.validateString(taskDueOn)) {
			taskDueOn = Utility.getCurrentDate("dd/MM/yyyy");
		}
		String monthYear = Utility.get_MonthYear(taskDueOn);
		String day = Utility.getDay(taskDueOn);
		logger.info(monthYear);
		String header = null, headerText = null, next = null, date;
		for (int i = 1; i <= 12; i++) {
			header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
			headerText = driver.findElement(By.xpath(header)).getText();
			if (headerText.equalsIgnoreCase(monthYear)) {
				date = "//td[contains(@class,'day') and text()='" + day + "']";
				Wait.WaitForElement(driver, date);
				int size = driver.findElements(By.xpath(date)).size();
				for (int k = 1; k <= size; k++) {
					date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + k + "]";
					String classText = driver.findElement(By.xpath(date)).getAttribute("class");
					if (!classText.contains("old")) {
						driver.findElement(By.xpath(date)).click();
						test_steps.add("Selecting due on date : " + taskDueOn);
						logger.info("Selecting due on date : " + taskDueOn);
						break;
					}
				}
				break;
			} else {
				next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
				Wait.WaitForElement(driver, next);
				driver.findElement(By.xpath(next)).click();
				Wait.wait2Second();
			}
		}
		if (!taskAssignee.isEmpty()) {
			String strTaskAssignee = "//label[text()='Assignee']/..//input";
			Wait.WaitForElement(driver, strTaskAssignee);
			driver.findElement(By.xpath(strTaskAssignee)).sendKeys(taskAssignee);
			Wait.wait2Second();
			String taskassgin = "//label[text()='Assignee']/..//div/ul/li/div";
			Wait.WaitForElement(driver, taskassgin);
			driver.findElement(By.xpath(taskassgin)).click();
			test_steps.add("Enter Task Assignee : " + taskAssignee);
			logger.info("Enter Task Assignee : " + taskAssignee);
		}
		if (Utility.validateString(taskStatus)) {
			String strTaskStatus = "(//label[text()='Status']/..//button)[2]";
			Wait.WaitForElement(driver, strTaskStatus);
			driver.findElement(By.xpath(strTaskStatus)).click();
			String status = "(//label[text()='Status']/..//button)[2]/following-sibling::div//li//span[text()='"
					+ taskStatus.trim() + "']";
			Wait.WaitForElement(driver, status);
			driver.findElement(By.xpath(status)).click();
			test_steps.add("Select Task Status : " + taskStatus);
			logger.info("Select Task Status : " + taskStatus);
		}
		String save = "//h4[text()='Add Task']/../..//button[text()='Save']";
		Wait.WaitForElement(driver, save);
		driver.findElement(By.xpath(save)).click();
		test_steps.add("Click on Save");
		logger.info("Click on Save");

		String taskVerify = "//span[text()='Tasks']/../..//td//span[text()='" + taskType.trim() + "']";
		Wait.WaitForElement(driver, taskVerify);
		test_steps.add("Sucessfully added Task : " + taskType);
		logger.info("Sucessfully added Task : " + taskType);

		test_steps.add("Verified Task Type : " + taskType);
		logger.info("Verified Task Type : " + taskType);

		test_steps.add("Verified Task Type : " + taskType);
		logger.info("Verified Task Type : " + taskType);

		strTaskDetails = "//span[text()='Tasks']/../..//td[text()='" + taskDetails.trim() + "']";
		Wait.WaitForElement(driver, strTaskDetails);
		test_steps.add("Verified Task details : " + taskDetails);
		logger.info("Verified Task details : " + taskDetails);

		String[] taskdue = taskDueOn.split("/");
		String due = "//span[text()='Tasks']/../..//td[contains(text(),'" + taskdue[1] + "/" + taskdue[0] + "')]";
		Wait.WaitForElement(driver, due);
		test_steps.add("Verified Task due on : " + taskDueOn);
		logger.info("Verified Task due on : " + taskDueOn);
		if (Utility.validateString(taskStatus)) {
			String stat = "//span[text()='Tasks']/../..//td[contains(text(),'" + taskStatus.trim() + "')]";
			Wait.WaitForElement(driver, stat);
			test_steps.add("Verified Tsak Status : " + taskStatus);
			logger.info("Verified Task Status : " + taskStatus);
		}

//		}
	}

	public void clickBookNow(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Book), driver);
		Utility.ScrollToElement(res.CP_NewReservation_Book, driver);
		res.CP_NewReservation_Book.click();
		logger.info("Clicked on Book Now");
		test_steps.add("Clicked on Book Now button");
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
			logger.info(res.TOASTER_MESSAGE.getText());
			test_steps.add("Toaster message: " + res.TOASTER_MESSAGE.getText());
			logger.info("Reservation successfully created");
			test_steps.add("Reservation successfully created");
		} catch (Exception e) {
			logger.info(e.toString());
		}

	}

	public void clickBookNow(WebDriver driver, ArrayList<String> test_steps, boolean missingMandatoryFields)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Book), driver);
		Utility.ScrollToElement(res.CP_NewReservation_Book, driver);		
		//Wait.wait30Second();		
		try
		{
			res.CP_NewReservation_Book.click();
		}
		catch(Exception e)
		{
			try {
				Utility.clickThroughAction(driver, res.CP_NewReservation_Book);
			} catch (Exception e1) {
				Utility.clickThroughJavaScript(driver, res.CP_NewReservation_Book);
			}
			
			//Wait.wait30Second();
			//Utility.ScrollToElement(res.CP_NewReservation_Book, driver);
			//res.CP_NewReservation_Book.click();
		}
		logger.info("Clicked on Book Now");
		test_steps.add("Clicked on Book Now button");

		try {
			if (!missingMandatoryFields) {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
				logger.info(res.TOASTER_MESSAGE.getText());
				test_steps.add("Toaster message: " + res.TOASTER_MESSAGE.getText());
				logger.info("Reservation successfully created");
				test_steps.add("Reservation successfully created");
			} else {
				Utility.ScrollToUp(driver);
				Wait.waitForElementToBeVisibile(
						By.xpath(OR_ReservationV2.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE), driver);
				logger.info("Validation message For Missing Fields: "
						+ res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
				test_steps.add("Validation message For Missing Fields: "
						+ res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
				logger.info("Reservation can not created due to missing mandatory fields");
				test_steps.add("Reservation can not created due to missing mandatory fields");
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void clickSaveQuote(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Quote), driver);
		Utility.ScrollToElement(res.CP_NewReservation_Quote, driver);
		res.CP_NewReservation_Quote.click();
		logger.info("Clicked on Save Quote");
		test_steps.add("Clicked on Save Quote button");

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
			logger.info(res.TOASTER_MESSAGE.getText());
			test_steps.add("Toaster message: " + res.TOASTER_MESSAGE.getText());
			logger.info("Quote Reservation successfully created");
			test_steps.add("Quote Reservation successfully created");
		} catch (Exception e) {
			logger.info(e.toString());
		}

	}

	public void clickSaveQuote(WebDriver driver, ArrayList<String> test_steps, boolean missingMandatoryFields)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Quote), driver);
		Utility.ScrollToElement(res.CP_NewReservation_Quote, driver);
		res.CP_NewReservation_Quote.click();
		logger.info("Clicked on Save Quote");
		test_steps.add("Clicked on Save Quote button");

		try {
			if (!missingMandatoryFields) {
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
				logger.info(res.TOASTER_MESSAGE.getText());
				test_steps.add("Toaster message: " + res.TOASTER_MESSAGE.getText());
				logger.info("Quote Reservation successfully created");
				test_steps.add("Quote Reservation successfully created");
			} else {
				Utility.ScrollToUp(driver);
				Wait.waitForElementToBeVisibile(
						By.xpath(OR_ReservationV2.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE), driver);
				logger.info("Validation message For Missing Fields: "
						+ res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
				test_steps.add("Validation message For Missing Fields: "
						+ res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
				logger.info("Quote can not created due to missing mandatory fields");
				test_steps.add("Quote can not created due to missing mandatory fields");
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public String get_ReservationConfirmationNumber(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_ConfirmationNumber);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_ConfirmationNumber), driver, 120);
		String confirmation = res.CP_NewReservation_ConfirmationNumber.getText();
		test_steps.add("Captured reservation confirmation number is : <b>" + confirmation + "</b>");
		logger.info("Confirmation Number : <b>" + confirmation + "</b>");
		return confirmation;
	}

	public String get_ReservationStatus(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_ReservationStatus);
		String status = res.CP_NewReservation_ReservationStatus.getText();
		test_steps.add("Captured reservation status on success popup is  : <b>" + status + "</b>");
		logger.info("Reservation Status : " + status);
		return status;
	}

	public void verify_QuoteConfirmetionPopup(WebDriver driver, ArrayList<String> test_steps) {
		String quote = "//h4[contains(text(),'Quote Confirmation')]";
		Wait.WaitForElement(driver, quote);
		if (driver.findElement(By.xpath(quote)).isDisplayed()) {
			test_steps.add("Quote Confirmation pop is displayed");
			logger.info("Quote Confirmation pop is displayed");
		} else {
			test_steps.add("Quote Confirmation pop not is displayed");
			logger.info("Quote Confirmation pop not is displayed");
		}
	}

	public void clickCloseReservationSavePopup(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_ClosePopUp), driver);
		Utility.ScrollToElement(res.CP_NewReservation_ClosePopUp, driver);
		try {
			res.CP_NewReservation_ClosePopUp.click();
		} catch (Exception e) {
			try {
				Utility.clickThroughAction(driver, res.CP_NewReservation_ClosePopUp);
			} catch (Exception e1) {
				Utility.clickThroughJavaScript(driver, res.CP_NewReservation_ClosePopUp);
			}
		}
//		Wait.wait5Second();
		test_steps.add("Clicking on Close");
		logger.info("Clicking on Close");

		try {
			Wait.waitForElementToBeInVisibile(By.xpath(OR_Reservation.CP_NewReservation_ClosePopUp), driver, 3);
		} catch (Exception e) {
			logger.info(e);
			logger.info("Confirmation popup is already closed");
		}
		waitForSweetLoading(driver);
	}

	public void enter_DataForNewReservation(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String checkOutDate, String adults, String children, String ratePlan, String promoCode, String roomClass,
			String isAssign, String guestSalutation, String guestFirstName, String guestLastName, String phoneNumber,
			String alternativePhone, String email, String address1, String address2, String address3, String city,
			String country, String state, String postalCode, boolean isGuestProfile, String paymentMethod,
			String cardNumber, String nameOnCard, String cardExpDate, boolean isTaxExempt, String taxExemptID,
			String travelAgent, String marketSegment, String referral, String useTravelAgentInfo, String account)
			throws Exception {

		searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);
		selectRoomToReserveRandom(driver, test_steps, roomClass, isAssign);
		clickNext(driver, test_steps);
		enter_GuestName(driver, test_steps, guestSalutation, guestFirstName, guestLastName);
		enter_Phone(driver, test_steps, phoneNumber, alternativePhone);
		enter_Email(driver, test_steps, email);
		enter_Address(driver, test_steps, address1, address2, address3);
		enter_City(driver, test_steps, city);
		select_Country(driver, test_steps, country);
		select_State(driver, test_steps, state);
		enter_PostalCode(driver, test_steps, postalCode);
		uncheck_CreateGuestProfile(driver, test_steps, isGuestProfile);
		if (!Utility.validateDate(account)) {
			enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpDate, account);
		}
		enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
		enter_MarketingInfoDetails(driver, test_steps, travelAgent, marketSegment, referral, useTravelAgentInfo);

	}

	public void bookReservationFromQuote(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBook), driver);
		Utility.ScrollToElement(res.CP_NewReservation_QuoteBook, driver);
		res.CP_NewReservation_QuoteBook.click();
		logger.info("Clicked on Book");
		test_steps.add("Clicked on Book");

		try {
			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_QuoteBookConfirm2);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBookConfirm2), driver,
					10);
			Utility.clickThroughJavaScript(driver, res.CP_NewReservation_QuoteBookConfirm2);
			for (int i = 0; i < 10; i++) {
				try {
					Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_ProceedPay), driver);
					Utility.clickThroughJavaScript(driver, res.CP_NewReservation_ProceedPay);
					break;
				} catch (Exception e1) {
					logger.info(e1.toString());
					Wait.wait1Second();
				}
			}

		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBookConfirm), driver);
			res.CP_NewReservation_QuoteBookConfirm.click();
		}

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservationConfirmBookingClose), driver);
		res.CP_NewReservationConfirmBookingClose.click();

		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.STAY_INFO), driver);
		logger.info("Reservation Successfully booked from Quote");
		test_steps.add("Reservation Successfully booked from Quote");

	}

	public void clickCopyButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.WaitForElement(driver, OR_ReservationV2.CopyButton);
		Utility.ScrollToElement(res.CopyButton, driver);
		res.CopyButton.click();
		logger.info("Copy Button Clicked");
		test_steps.add("Copy Button Clicked");
		// Wait.wait10Second();
		// Wait.explicit_wait_visibilityof_webelement(res.Copy_Reservation_Trimname,
		// driver);
		Wait.WaitForElement(driver, OR_ReservationV2.Copy_Reservation_Trimname);

	}

	public HashMap<String, String> copyReservation(WebDriver driver, ArrayList<String> test_steps, String firstName,
			String lastName) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		clickCopyButton(driver, test_steps);
		clickEditGuestInfoOFCopyRes(driver, test_steps);
		enterGuestNameWhileCopy(driver, test_steps, firstName);
		enterGuestLastNameWhileCopy(driver, test_steps, lastName);
		clickOnGuestProfilePopupSaveButtonWhileCopy(driver, test_steps);
		clickBookNow(driver, test_steps);
		data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
		data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));

		clickCloseReservationSavePopup(driver, test_steps);
		logger.info("Successfully created copy Reservation");
		test_steps.add("Successfully created copy Reservation");
		return data;
	}

	public void clickOnAddARoomButton(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_AddRoom);
		res.CP_NewReservation_AddRoom.click();
		logger.info("Clicking on add room");
		test_steps.add("Clicking on add room");

	}

	public void searchDataForFindRoomsForMRB(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String checkOutDate, String adults, String children, String ratePlan, String promoCode) throws Exception {

		String[] checkin = checkInDate.split("\\|");
		String[] checkout = checkOutDate.split("\\|");
		String[] adu = adults.split("\\|");
		String[] child = children.split("\\|");
		String[] rate = ratePlan.split("\\|");
		logger.info(checkInDate);
		int size = checkin.length;

		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_AddRoom), driver, 25);
		test_steps.add("MRB Size : " + size);
		logger.info("MRB size : " + size);

		for (int i = 1; i <= size; i++) {
			selectCheckInDate(driver, test_steps, checkin[i - 1], i);
			selectCheckOutDate(driver, test_steps, checkout[i - 1], i);
			enter_Adults(driver, test_steps, adu[i - 1], i);
			enter_Children(driver, test_steps, child[i - 1], i);
			if (Utility.validateString(promoCode) && promoCode.split("\\|").length == size) {
				String[] promo = promoCode.split("\\|");
				// select_Rateplan1(driver, test_steps, rate[i-1], promo[i-1], i);
				select_Rateplan(driver, test_steps, rate[i - 1], promo[i - 1], i);
			} else {
				try {
					// select_Rateplan1(driver, test_steps, rate[i-1], null, i);
					select_Rateplan(driver, test_steps, rate[i - 1], null, i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			test_steps.add("selecting rate plan....." + rate[i - 1]);
			logger.info("selecting rate plan......" + rate[i - 1]);

			if (!(i == size)) {
				clickOnAddARoomButton(driver, test_steps);
			}
		}
	}

	public void select_MRBRooms(WebDriver driver, ArrayList<String> test_steps, String roomClass, String isAssign)
			throws InterruptedException {

		String[] RoomClass = roomClass.split("\\|");
		logger.info("Room class: " + roomClass);
		int size = RoomClass.length;
		logger.info("Size: " + size + " , Rooms: " + RoomClass);
		for (int i = 0; i < size; i++) {
			selectRoomToReserveRandom(driver, test_steps, RoomClass[i], isAssign);
			Wait.wait5Second();
		}
	}

	public void add_PrimaryRoom(WebDriver driver, ArrayList<String> test_steps) {
		String primary = null;
		;
		String primaryArrow = "//span[text()='Primary Room']/../../following-sibling::div//button";
		Wait.WaitForElement(driver, primaryArrow);
		try {
			driver.findElement(By.xpath(primaryArrow)).click();
		} catch (Exception e) {
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(primaryArrow)));
		}

		String primaryRoom = "//span[text()='Primary Room']/../../following-sibling::div//li[2]//span";
		primary = driver.findElement(By.xpath(primaryRoom)).getText();
		Wait.WaitForElement(driver, primaryRoom);
		driver.findElement(By.xpath(primaryRoom)).click();
		test_steps.add("Selected Primary Room as : " + primary);
		logger.info("Selected Primary Room as : " + primary);

	}

	public void add_AdditionalRoom(WebDriver driver, ArrayList<String> test_steps) {
		String additionalRoomNo = null;
		String additional = "(//span[text()='Additional Room'])";
		int count = driver.findElements(By.xpath(additional)).size();
		for (int i = 1; i <= count; i++) {
			String additionalArrow = "(//span[text()='Additional Room'])[" + i
					+ "]/../../following-sibling::div//button";
			Wait.WaitForElement(driver, additionalArrow);
			driver.findElement(By.xpath(additionalArrow)).click();
			String additionalRoom = "(//span[text()='Additional Room'])[" + i + "]/../../following-sibling::div//li["
					+ (i + 1) + "]//span";
			additionalRoomNo = driver.findElement(By.xpath(additionalRoom)).getText();
			Wait.WaitForElement(driver, additionalRoom);
			driver.findElement(By.xpath(additionalRoom)).click();
			test_steps.add("Selected Addtinal  Room as : " + additionalRoomNo);
			logger.info("Selected Addtinal Room as : " + additionalRoomNo);
		}
	}

	public void clickCopyGuestFromPrimaryRoom(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CopyGuest_FromPrimaryRoom);
		res.CopyGuest_FromPrimaryRoom.click();
		if (res.CopyGuest_FromPrimaryRoomCheckbox.isSelected()) {
			logger.info("Copy Guest from Primary room checkbox selected");
		} else {
			Utility.clickThroughJavaScript(driver, res.CopyGuest_FromPrimaryRoom);
			logger.info("clicked on Copy Guest from Primary room checkbox");
		}
	}

	public void click_Folio(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Folio);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Folio), driver);
		try {
			Wait.wait3Second();
			res.CP_NewReservation_Folio.click();
			test_steps.add("Clicking on Folio");
			logger.info("Clicking on Folio");
			String path = "//button/span[text()=' Add ']";
			Wait.WaitForElement(driver, path);
			Wait.waitForElementToBeVisibile(By.xpath(path), driver);
			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(), "Failed: to Displayed Folio Detail");
			test_steps.add("Folio Detail Tab is Displayed");
			logger.info("Folio Detail Tab is Displayed");
		} catch (Exception e) {
			Utility.ScrollToElement(res.CP_NewReservation_Folio, driver);
			Utility.clickThroughJavaScript(driver, res.CP_NewReservation_Folio);
			test_steps.add("Clicking on Folio");
			logger.info("Clicking on Folio");
			String path = "//button/span[text()=' Add ']";
			Wait.WaitForElement(driver, path);
			Wait.waitForElementToBeVisibile(By.xpath(path), driver);
			assertTrue(driver.findElement(By.xpath(path)).isDisplayed(), "Failed: to Displayed Folio Detail");
			test_steps.add("Folio Detail Tab is Displayed");
			logger.info("Folio Detail Tab is Displayed");
		}
	}

	public void click_History(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_History);
		try {
			Utility.ScrollToElement(res.CP_NewReservation_History, driver);
			res.CP_NewReservation_History.click();
		} catch (Exception e) {
			Utility.scrollAndClick(driver, By.xpath(OR_Reservation.CP_NewReservation_History));
		}
		test_steps.add("Clicking on History");
		logger.info("Clicking on History");
	}

	public WebElement findElement(WebDriver driver, String path) {
		WebElement element = driver.findElement(By.xpath(path));
		return element;
	}

	public ArrayList<StayInfo> getStayInfoDetailMRB(WebDriver driver) {
		ArrayList<StayInfo> stayInfo = new ArrayList<StayInfo>();
		int numberOfRooms = driver.findElements(By.xpath(OR_ReservationV2.SI_CHECK_IN_DATE)).size();

		for (int i = 1; i <= numberOfRooms; i++) {
			StayInfo info = new StayInfo(
					Utility.getELementText(findElement(driver, OR_ReservationV2.SI_PROPERTY_NAME), false, ""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_RATE_PLAN_NAME + ")[" + i + "]"), false, ""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_CHECK_IN_DATE + ")[" + i + "]"), false, ""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_CHECK_OUT_DATE + ")[" + i + "]"), false, ""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_NUMBER_OF_NIGHTS + ")[" + i + "]"), false,
							""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_NUMBER_OF_ADULTS + ")[" + i + "]"), false,
							""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_NUMBER_OF_CHILDS + ")[" + i + "]"), false,
							""),
					Utility.getELementText(
							findElement(driver, "(" + OR_ReservationV2.SI_ROOMCLASS_NAME + ")[" + i + "]"), false, ""),
					Utility.getELementText(findElement(driver, "(" + OR_ReservationV2.SI_ROOM_NUMBER + ")[" + i + "]"),
							false, ""),
					Utility.getELementText(findElement(driver, "(" + OR_ReservationV2.SI_ROOM_TOTAL + ")[" + i + "]"),
							false, ""));
			stayInfo.add(info);
		}
		return stayInfo;
	}

	public void clickCheckOut(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutButton), driver);
		Utility.ScrollToElement(res.checkOutButton, driver);
		res.checkOutButton.click();
		logger.info("Clicked on CheckOut button");
		test_steps.add("Clicked on CheckOut button");
	}

	public void clickYesNoCheckOutAll(WebDriver driver, ArrayList<String> test_steps, String isYes) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 3);
			if (isYes.equalsIgnoreCase("Yes")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutAllYesButton), driver);
				res.checkOutAllYesButton.click();
				logger.info("Clicked Yes on Checkout all popup");
			} else if (isYes.equalsIgnoreCase("No")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutAllNoButton), driver);
				res.checkOutAllNoButton.click();
				logger.info("Clicked No on Checkout all popup");
			}
		} catch (Exception e) {
			logger.info(e.toString());
			logger.info("CheckOut all popup not displayed");
		}
	}

	public void generateGuestReportToggle(WebDriver driver, ArrayList<String> test_steps, String isGenerateStatement) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOut_GenerateGuestStatement), driver);
		if (isGenerateStatement.equalsIgnoreCase("Yes")) {
			if (res.checkOut_GenerateGuestStatement.getAttribute("class").contains("off")) {
				res.checkOut_GenerateGuestStatement.click();
				logger.info("Clicked on Generate Guest Statement");
			} else {
				logger.info("Generate Guest Statement is already enabled");
			}
		} else if (isGenerateStatement.equalsIgnoreCase("No")) {
			if (!res.checkOut_GenerateGuestStatement.getAttribute("class").contains("off")) {
				res.checkOut_GenerateGuestStatement.click();
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOut_GenerateGuestStatementOff), driver);
				logger.info("Disabled Generate Guest Statement");
			} else {
				logger.info("Generate Guest Statement is already disabled");
			}
		}
	}

	public void clickOnConfirmCheckOut(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutConfirm), driver);
		Utility.ScrollToElement(res.checkOutConfirm, driver);
		res.checkOutConfirm.click();
		test_steps.add("Clicking on  Confirm Check-Out Button");
		logger.info("Clicking on Confirm Check-Out Button");
	}

	public void clickOnProceedToCheckOutPaymentButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutProceedToCheckOutPayment), driver);
		Utility.ScrollToElement(res.checkOutProceedToCheckOutPayment, driver);
		res.checkOutProceedToCheckOutPayment.click();
		test_steps.add("Clicking on  Proceed to Check-Out Payment Button");
		logger.info("Clicking on Proceed to Check-Out Payment Button");
	}

	public void checkOutSelectPaymentMethod(WebDriver driver, ArrayList<String> test_steps, String paymentMethod)
			throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.checkOutPaymentMethod), driver, 30);
			res.checkOutPaymentMethod.click();
			String pay = "//h4[text()='Check Out Payment']/../..//span[@class='text' and text()='" + paymentMethod
					+ "']";
			Wait.waitForElementToBeVisibile(By.xpath(pay), driver, 30);
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(pay)));
			String amount = res.checkOutPaymentPopupAmount.getText().trim();
			logger.info("Successfully verified paid amount is " + amount);
			test_steps.add("Successfully verified paid amount is <b>" + amount + "</b>");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void selectCashOnChackOutPaymentPopup(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.wait5Second();
			Wait.waitForElementToBeVisibile(By.xpath(
					"//h4[text()='Check Out Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"),
					driver, 5);
			driver.findElement(By.xpath(
					"//h4[text()='Check Out Payment']/../..//button[@class='btn dropdown-toggle btn-default' and @title='--Select--']"))
					.click();
			Wait.waitForElementToBeVisibile(
					By.xpath("//h4[text()='Check Out Payment']/../..//span[@class='text' and text()='Cash']"), driver,
					10);
			Utility.clickThroughJavaScript(driver, driver.findElement(
					By.xpath("//h4[text()='Check Out Payment']/../..//span[@class='text' and text()='Cash']")));
			String amount = res.checkOutPaymentPopupAmount.getText().trim();
			logger.info("Successfully verified paid amount is " + amount);
			test_steps.add("Successfully verified paid amount is <b>" + amount + "</b>");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String getCheckOutPaymentAmount(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		String amount = res.checkOutPaymentPopupAmount.getText().trim();
		test_steps.add("Successfully verified paid amount is <b>" + amount + "</b>");
		logger.info("Successfully verified paid amount is " + amount);
		return amount;
	}

	public void checkOutPaymentSetAsMainPaymentMethod(WebDriver driver, ArrayList<String> test_steps, String isSet) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		if (isSet.equalsIgnoreCase("Yes")) {
			if (!res.checkOutPaymentSetAsMainPaymentMethodCheckBox.isSelected()) {
				res.checkOutPaymentSetAsMainPaymentMethodCheckBoxClick.click();
				logger.info("Selected Set As Main Payment Method");
			}
		} else if (isSet.equalsIgnoreCase("No")) {
			if (res.checkOutPaymentSetAsMainPaymentMethodCheckBox.isSelected()) {
				res.checkOutPaymentSetAsMainPaymentMethodCheckBoxClick.click();
				logger.info("Removed Set As Main Payment Method");
			}
		}
	}

	public void checkOutPaymentLogAsExternalPayment(WebDriver driver, ArrayList<String> test_steps, String isSet) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.checkOutPaymentLogAsExternalPaymentCheckBox),
					driver, 5);
			if (isSet.equalsIgnoreCase("Yes")) {
				if (!res.checkOutPaymentLogAsExternalPaymentCheckBox.isSelected()) {
					res.checkOutPaymentLogAsExternalPaymentCheckBox.click();
					logger.info("Selected Log as External Payment");
				}
			} else if (isSet.equalsIgnoreCase("No")) {
				if (res.checkOutPaymentLogAsExternalPaymentCheckBox.isSelected()) {
					res.checkOutPaymentLogAsExternalPaymentCheckBox.click();
					logger.info("Removed Log as External Payment");
				}
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void checkOutPaymentAddNotes(WebDriver driver, String notes) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		if (Utility.validateString(notes)) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutPaymentAddNotes), driver);
			res.checkOutPaymentAddNotes.click();
			res.checkOutPaymentNotesInput.sendKeys(notes);
		}
	}

	public void clickOnPayButtonOnCheckOutPaymentPopup(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.checkOutPaymentPayButton);
		Wait.wait5Second();
		res.checkOutPaymentPayButton.click();
		Wait.wait5Second();
		// Utility.clickThroughJavaScript(driver, res.checkOutPaymentPayButton);
		test_steps.add("Click On Pay Button On Payment Popup");
		logger.info("Click On Pay Button On Payment Popup");
		Wait.WaitForElement(driver, "//h4[contains(text(),'Successful')]");
	}

	public void checkOutPaymentSuccessPopupClose(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutPaymentSuccessfulClose), driver);
		res.checkOutPaymentSuccessfulClose.click();
		test_steps.add("Closing check-out payment successful popup");
		logger.info("Closing check-out payment successful popup");
	}

	public CheckOutSuccessPaymentPopup getCheckOutSuccessPopupDetails(WebDriver driver) {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath("//h4[contains(text(),'Check out Successful')]"), driver);
		CheckOutSuccessPaymentPopup paymentPopup = new CheckOutSuccessPaymentPopup(
				Utility.getELementText(ele.checkOutSuccessPaymentDate, false, ""),
				Utility.getELementText(ele.checkOutSuccessBalance, false, ""),
				Utility.getELementText(ele.checkOutSuccessPaymentAmount, false, ""),
				Utility.getELementText(ele.checkOutSuccessType, false, ""),
				Utility.getELementText(ele.checkOutSuccessPaymentMethod, false, ""),
				Utility.getELementText(ele.checkOutSuccessPaymentStatus, false, ""));
		return paymentPopup;
	}

	public void verifyHistoryForCheckOut(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		String checkout = "//span[text()='Checked out this reservation']";
		if (driver.findElement(By.xpath(checkout)).isDisplayed()) {
			logger.info("Check-out verified in History tab");
			test_steps.add("Check-out verified in History tab");
		} else {
			logger.info("Failed - Check-out history not found in History tab");
			test_steps.add("AssertionError - Failed - Check-out history not found in History tab");
		}
	}

	public void verifyCheckOutPaymentAmount(WebDriver driver, ArrayList<String> test_steps, String amount,
			CheckOutSuccessPaymentPopup paymentPopup) {

		if (amount.replace("$", "").replace("£", "").trim().equalsIgnoreCase(
				paymentPopup.getCHECK_OUT_PAYPMENT_AMOUNT().replace("$", "").replace("£", "").trim())) {
			logger.info("Check out Payment amount successfully validated");
			test_steps.add("Check out Payment amount successfully validated");
		} else {
			logger.info("Failed - Check out Payment amount validation failed");
			test_steps.add("AssertionError Failed - Check out Payment amount validation failed");
		}
	}

	public void verifyBalanceAfterCheckOutPayment(WebDriver driver, ArrayList<String> test_steps,
			CheckOutSuccessPaymentPopup paymentPopup) {

		if (paymentPopup.getCHECK_OUT_PAYMENT_BALANCE().replace("$", "").replace("£", "").trim()
				.equalsIgnoreCase("0.00")) {
			logger.info("Verified Balance amount is 0.00 after checkout payment");
			test_steps.add("Verified Balance amount is 0.00 after checkout payment");
		} else {
			logger.info("Failed - Balance amount is not 0.00 after checkout payment");
			test_steps.add("AssertionError Failed - Balance amount is not 0.00 after checkout payment");
		}
	}

	public void verifyCheckOutPaymentStatus(WebDriver driver, ArrayList<String> test_steps,
			CheckOutSuccessPaymentPopup paymentPopup) {

		if (paymentPopup.getCHECK_OUT_PAYMENT_STATUS().equalsIgnoreCase("Approved")) {
			logger.info("Verified Checkout payment status is Approved");
			test_steps.add("Verified Checkout payment status is Approved");
		} else {
			logger.info("Failed - Checkout payment status is not in Approved status after checkout payment");
			test_steps.add(
					"AssertionError Failed - Checkout payment status is not in Approved status after checkout payment");
		}
	}

	public void verifyCheckOutSuccessPaymentPopup(WebDriver driver, ArrayList<String> test_steps, String amount) {
		CheckOutSuccessPaymentPopup paymentPopup = getCheckOutSuccessPopupDetails(driver);
		verifyCheckOutPaymentAmount(driver, test_steps, amount, paymentPopup);
		verifyBalanceAfterCheckOutPayment(driver, test_steps, paymentPopup);
		verifyCheckOutPaymentStatus(driver, test_steps, paymentPopup);
	}

	public CheckOutSuccessPaymentPopup checkOutReservation(WebDriver driver, ArrayList<String> test_steps,
			String isGenerateStatement, String isSetAsMainPayment, String checkOutNotes) throws Exception {
		CheckOutSuccessPaymentPopup paymentPopup = null;
		clickCheckOut(driver, test_steps);
		clickYesNoCheckOutAll(driver, test_steps, "Yes");
		generateGuestReportToggle(driver, test_steps, isGenerateStatement);
		clickOnProceedToCheckOutPaymentButton(driver, test_steps);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.checkOutPaymentHeader), driver, 5);
			if (driver.findElement(
					By.xpath("//h4[text()='Check Out Payment']/../..//button[@class='btn dropdown-toggle btn-default' "
							+ "and @title='--Select--']"))
					.isDisplayed()) {
				selectCashOnChackOutPaymentPopup(driver, test_steps);
			}
			checkOutPaymentSetAsMainPaymentMethod(driver, test_steps, isSetAsMainPayment);
			checkOutPaymentAddNotes(driver, checkOutNotes);
			String amount = getCheckOutPaymentAmount(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			paymentPopup = getCheckOutSuccessPopupDetails(driver);
			logger.info("Paymeny popup details: " + paymentPopup.toString());
			verifyCheckOutSuccessPaymentPopup(driver, test_steps, amount);
			checkOutPaymentSuccessPopupClose(driver, test_steps);
		} catch (Exception e) {
			logger.info(e.toString());
		}

		ReservationStatusBar statusBar = getStatusBarDetail(driver);
		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase("Departed")) {
			logger.info("Successfully checkout the reservation");
			test_steps.add("Successfully checkout the reservation");
		} else {
			logger.info("Failed to checkout the reservation");
			test_steps.add("Failed to checkout the reservation");
		}
		return paymentPopup;
	}

	public void clickCheckOutMrbPrimary(WebDriver driver, ArrayList<String> test_steps) {
		String checkOutButton = "//span[text()='Check Out' and contains(@data-bind,'statusAction')]";
		Wait.WaitForElement(driver, checkOutButton);
		List<WebElement> checkOut = driver.findElements(By.xpath(checkOutButton));
		checkOut.get(0).click();
	}

	public void clickCheckOutMrbSecondary(WebDriver driver, ArrayList<String> test_steps) {
		String checkOutButton = "//span[text()='Check Out' and contains(@data-bind,'statusAction')]";
		Wait.WaitForElement(driver, checkOutButton);
		List<WebElement> checkOut = driver.findElements(By.xpath(checkOutButton));
		checkOut.get(checkOut.size() - 1).click();
	}

	public void checkOutMRBPrimary(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		clickCheckOutMrbPrimary(driver, test_steps);
		clickYesNoCheckOutAll(driver, test_steps, "No");
		generateGuestReportToggle(driver, test_steps, "No");
		clickOnProceedToCheckOutPaymentButton(driver, test_steps);

		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.checkOutPaymentHeader), driver, 5);
			selectCashOnChackOutPaymentPopup(driver, test_steps);
			checkOutPaymentSetAsMainPaymentMethod(driver, test_steps, "No");
			checkOutPaymentAddNotes(driver, "Test notess");
			String amount = getCheckOutPaymentAmount(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			CheckOutSuccessPaymentPopup paymentPopup = getCheckOutSuccessPopupDetails(driver);
			verifyCheckOutSuccessPaymentPopup(driver, test_steps, amount);
			checkOutPaymentSuccessPopupClose(driver, test_steps);
		} catch (Exception e) {
			logger.info(e.toString());
		}

		ReservationStatusBar statusBar = getStatusBarDetail(driver);
		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase("Departed")) {
			logger.info("Successfully checkout the reservation");
			test_steps.add("Successfully checkout the reservation");
		} else {
			logger.info("Failed to checkout the reservation");
			test_steps.add("Failed to checkout the reservation");
		}
	}

	public void checkOutMRBSecondary(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		clickCheckOutMrbSecondary(driver, test_steps);
		clickYesNoCheckOutAll(driver, test_steps, "No");
		generateGuestReportToggle(driver, test_steps, "No");
		clickOnProceedToCheckOutPaymentButton(driver, test_steps);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.checkOutPaymentHeader), driver, 5);
			selectCashOnChackOutPaymentPopup(driver, test_steps);
			checkOutPaymentSetAsMainPaymentMethod(driver, test_steps, "No");
			checkOutPaymentAddNotes(driver, "Test notess");
			String amount = getCheckOutPaymentAmount(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
			CheckOutSuccessPaymentPopup paymentPopup = getCheckOutSuccessPopupDetails(driver);
			verifyCheckOutSuccessPaymentPopup(driver, test_steps, amount);
			checkOutPaymentSuccessPopupClose(driver, test_steps);
		} catch (Exception e) {
			logger.info(e.toString());
		}

	}

	public void clickReservationStatusDropdown(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.reservationStatusDropdown), driver);
		res.reservationStatusDropdown.click();
		logger.info("Clicked on Reservation status dropdown");
	}

	public void changeReservationStatusFromStatusDropdown(WebDriver driver, ArrayList<String> test_steps, String status)
			throws InterruptedException {
		String strStatus = "//span[text()='" + status + "' and contains(@data-bind,'reservationStatusItem')]";
		clickReservationStatusDropdown(driver, test_steps);
		Wait.waitForElementToBeClickable(By.xpath(strStatus), driver);
		driver.findElement(By.xpath(strStatus)).click();
		logger.info("Clicked on " + status + " status");
		test_steps.add("Clicked on " + status + " status");
		waitForSweetLoading(driver);
		ReservationStatusBar statusBar = getStatusBarDetail(driver);

		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase(status)) {
			logger.info("Successfully changed the status to " + status);
			test_steps.add("Successfully changed the status to " + status);
		} else {
			logger.info("Failed to change the status to " + status);
			test_steps.add("Failed to change the status to " + status);
		}
	}

	public void verifyOnHoldStatusInHistory(WebDriver driver, ArrayList<String> test_steps) {
		String statusOnHold = "//span[text()='Changed reservation status from Reserved to On Hold']";

		if (driver.findElement(By.xpath(statusOnHold)).isDisplayed()) {
			logger.info("Verified On-hold in History tab");
			test_steps.add("Verified On-hold in History tab");
		} else {
			logger.info("Failed - On-hold history not found in History tab");
			test_steps.add("AssertionError - Failed - On-hold history not found in History tab");
		}
	}

	public void click_RollBackButton(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.rollBackButton);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.rollBackButton), driver);
		res.rollBackButton.click();
		test_steps.add("Click Roll Back Button");
		logger.info("Click Roll Back Button");
	}

	public void clickYesOrNoOnConfirmationPopup(WebDriver driver, ArrayList<String> test_steps, String isYes)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.dialog), driver, 3);
			if (isYes.equalsIgnoreCase("Yes")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutAllYesButton), driver);
				res.checkOutAllYesButton.click();
				logger.info("Clicked Yes on Confirmation popup");
			} else if (isYes.equalsIgnoreCase("No")) {
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutAllNoButton), driver);
				res.checkOutAllNoButton.click();
				logger.info("Clicked No on Confirmation popup");
			}
		} catch (Exception e) {
			logger.info(e.toString());
			logger.info("Confirmation popup not displayed");
		}
	}

	public void rollBackReservationStatus(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("============ Rollback the reservation =================");
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.rollBackButton), driver, 5);
			click_RollBackButton(driver, test_steps);
		} catch (Exception e) {
			clickReservationStatusDropdown(driver, test_steps);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.rollBackButtonInDropdown), driver);
			res.rollBackButtonInDropdown.click();
			logger.info("Clicked on Roll back button from dropdown");
		}
		try {
			Wait.WaitForElement(driver, OR_ReservationV2.dialog);
			clickYesOrNoOnConfirmationPopup(driver, test_steps, "Yes");
		} catch (Exception e) {
			logger.info(e.toString());
		}
		waitForSweetLoading(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SB_RESERVATION_STATUS), driver);
		ReservationStatusBar statusBar = getStatusBarDetail(driver);
		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase("RESERVED")) {
			logger.info("Successfully Roll back the reservation and current status is Reserved");
			test_steps.add("Successfully Roll back the reservation and current status is Reserved");
		}
	}

	public void uncheck_CreateGuestProfile(WebDriver driver, ArrayList<String> test_steps, String isGuestProfile) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_CreateGuestProfile);
		if (isGuestProfile.equalsIgnoreCase("Yes")) {
			if (!res.CP_NewReservation_CreateGuestProfileCheckbox.isSelected()) {
				res.CP_NewReservation_CreateGuestProfile.click();
				test_steps.add("Create Guest Profile selected");
				logger.info("Create Guest Profile selected");
			} else {
				test_steps.add("Create Guest Profile selected");
				logger.info("Create Guest Profile selected");
			}
		} else {
			if (res.CP_NewReservation_CreateGuestProfileCheckbox.isSelected()) {
				res.CP_NewReservation_CreateGuestProfile.click();
				test_steps.add("Create Guest Profile unselected");
				logger.info("Create Guest Profile unselected");
			} else {
				test_steps.add("Create Guest Profile unselected");
				logger.info("Create Guest Profile unselected");
			}
		}
	}

	public HashMap<String, String> OpenNewReservationPage(WebDriver driver, ArrayList<String> test_steps,
			String sourceOfRes, String checkInDate, String checkOutDate, String adults, String children,
			String ratePlan, String promoCode, String roomClass, String roomClassAbb, String salutation,
			String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone, String email,
			String address1, String address2, String address3, String city, String country, String state,
			String postalCode, boolean isGuesProfile, String marketSegment, String referral, String paymentMethod,
			String cardNumber, String nameOnCard, String cardExpMonthAndYear, int additionalGuests, String roomNumber,
			boolean quote, String noteType, String noteSubject, String noteDescription, String taskCategory,
			String taskType, String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee,
			String taskStatus, String accountName, String accountType, String accountFirstName, String accountLastName,
			boolean isTaxExempt, String taxExemptID) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Account account = new Account();
		Tapechart tc = new Tapechart();
		Navigation nav = new Navigation();
		Groups group = new Groups();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		int noOfRooms = guestFirstName.split("\\|").length;
		boolean missingMandatoryFields = false;

		if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
			test_steps.add(
					"<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
			click_NewReservation(driver, test_steps);
		}

		test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
		logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);

		if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")

				|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group")
				|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate")) {
			if (noOfRooms > 1) {
				searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);
				for (int i = 0; i < roomClass.split("\\|").length; i++) {

					int j = i + 1;
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
					try {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));
						data.put("RoomNumber" + j, roomNumbersProvided.get(i));
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
						data.put("RoomNumber" + j, roomNumbers.get(i));
					}
				}
			} else {
				searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);
				roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
				try {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
					data.put("RoomNumber", roomNumber);
				} catch (Exception e) {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
					data.put("RoomNumber", roomNumbers.get(0));
				}
			}
			clickNext(driver, test_steps);
		}

		return data;

	}

	/*
	 * public HashMap<String, String> getMarketValuesFromUI(WebDriver driver,
	 * ArrayList<String> test_steps, String sourceOfRes, String checkInDate, String
	 * checkOutDate, String adults, String children, String ratePlan, String
	 * promoCode, String roomClass, String roomClassAbb, String salutation, String
	 * guestFirstName, String guestLastName, String phoneNumber, String
	 * altenativePhone, String email, String address1, String address2, String
	 * address3, String city, String country, String state, String postalCode,
	 * boolean isGuesProfile, String marketSegment, String referral, String
	 * paymentMethod, String cardNumber, String nameOnCard, String
	 * cardExpMonthAndYear, int additionalGuests, String roomNumber, boolean quote,
	 * String noteType, String noteSubject, String noteDescription, String
	 * taskCategory, String taskType, String taskDetails, String taskRemarks, String
	 * taskDueOn, String taskAssignee, String taskStatus, String accountName, String
	 * accountType, String accountFirstName, String accountLastName, boolean
	 * isTaxExempt, String taxExemptID) throws Exception
	 */
	public ArrayList<String> getMarketValuesFromUI(WebDriver driver, ArrayList<String> test_steps, String sourceOfRes,
			int noOfRooms, String checkInDate, String checkOutDate, String adults, String children, String ratePlan,
			String promoCode, String roomClass, String roomNumber) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Account account = new Account();
		Tapechart tc = new Tapechart();
		Navigation nav = new Navigation();
		Groups group = new Groups();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		// int noOfRooms = guestFirstName.split("\\|").length;
		boolean missingMandatoryFields = false;

		if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
			test_steps.add(
					"<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
			click_NewReservation(driver, test_steps);
		}

		/*
		 * test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
		 * logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);
		 */
		/*
		 * if (sourceOfRes.equalsIgnoreCase("Associate Account") ||
		 * sourceOfRes.equalsIgnoreCase("From Reservations page")
		 * 
		 * || sourceOfRes.equalsIgnoreCase("Account Reservation") ||
		 * sourceOfRes.equalsIgnoreCase("Group") ||
		 * sourceOfRes.equalsIgnoreCase("TapeChart Syndicate") ) {
		 */
		if (noOfRooms > 1) {
			searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
					promoCode);
			clickOnFindRooms(driver, test_steps);
			for (int i = 0; i < roomClass.split("\\|").length; i++) {

				int j = i + 1;
				roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
				try {
					selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));
					data.put("RoomNumber" + j, roomNumbersProvided.get(i));
				} catch (Exception e) {
					selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
					data.put("RoomNumber" + j, roomNumbers.get(i));
				}
			}
		} else {
			searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
					promoCode);
			clickOnFindRooms(driver, test_steps);
			roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
			try {
				selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
				data.put("RoomNumber", roomNumber);
			} catch (Exception e) {
				selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
				data.put("RoomNumber", roomNumbers.get(0));
			}
		}
		clickNext(driver, test_steps);

		try {
			Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Market);
			Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
			res.CP_NewReservation_Market.click();

		} catch (Exception e) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_Market), driver, 2);
			Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
			res.CP_NewReservation_Market.click();

		}

		// String marketXpath =
		// "(//label[text()='Market']/preceding-sibling::div//ul/li//span[text()='" +
		// marketSegment.trim() + "'])";
		String marketXpath = "//label[text()='Market']/preceding-sibling::div//ul/li//span[1]";
		ArrayList<String> marketSegListfromUI = new ArrayList<>();

		List<WebElement> allMarketSegmentsfromUI = new ArrayList<WebElement>();

		allMarketSegmentsfromUI = driver.findElements(By.xpath(marketXpath));
		for (WebElement webElement : allMarketSegmentsfromUI) {
			String marketS = webElement.getText();
			if (!(marketS.equalsIgnoreCase("--Select--"))) {
				marketSegListfromUI.add(marketS);
			}
		}
		return marketSegListfromUI;
	}

	public HashMap<String, String> createReservation(WebDriver driver, ArrayList<String> test_steps, String sourceOfRes,
			String checkInDate, String checkOutDate, String adults, String children, String ratePlan, String promoCode,
			String roomClass, String roomClassAbb, String salutation, String guestFirstName, String guestLastName,
			String phoneNumber, String altenativePhone, String email, String address1, String address2, String address3,
			String city, String country, String state, String postalCode, boolean isGuesProfile, String marketSegment,
			String referral, String paymentMethod, String cardNumber, String nameOnCard, String cardExpMonthAndYear,
			int additionalGuests, String roomNumber, boolean quote, String noteType, String noteSubject,
			String noteDescription, String taskCategory, String taskType, String taskDetails, String taskRemarks,
			String taskDueOn, String taskAssignee, String taskStatus, String accountName, String accountType,
			String accountFirstName, String accountLastName, boolean isTaxExempt, String taxExemptID) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		int noOfRooms = guestFirstName.split("\\|").length;
		boolean missingMandatoryFields = false;

		if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
			test_steps.add(
					"<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
			click_NewReservation(driver, test_steps);
		}

		test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
		logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);

		if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")

				|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group")
				|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate")) {
			if (noOfRooms > 1) {
				searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);
				for (int i = 0; i < roomClass.split("\\|").length; i++) {

					int j = i + 1;
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
					try {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));
						data.put("RoomNumber" + j, roomNumbersProvided.get(i));
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
						data.put("RoomNumber" + j, roomNumbers.get(i));
					}
				}
			} else {
				searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);
				roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
				try {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
					data.put("RoomNumber", roomNumber);
				} catch (Exception e) {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
					data.put("RoomNumber", roomNumbers.get(0));
				}
			}
			clickNext(driver, test_steps);
			clickOnCreateGuestButton(driver, test_steps);
			turnOnOffcreateGuestProfileToggle(driver, test_steps, isGuesProfile);

			if (noOfRooms > 1) {
				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, true);
			} else {
				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
				enter_City(driver, test_steps, city);
				select_Country(driver, test_steps, country);
				select_State(driver, test_steps, state);
				enter_PostalCode(driver, test_steps, postalCode);
				enter_Phone(driver, test_steps, phoneNumber, altenativePhone);
				enter_Email(driver, test_steps, email);
				enter_Address(driver, test_steps, address1, address2, address3);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);
			}

			if (Utility.validateString(accountName) && sourceOfRes.equalsIgnoreCase("Associate Account")) {
				clickEditGuestInfo(driver, test_steps);
				enterAccount(driver, test_steps, accountName, accountType);
				if (Utility.validateString(paymentMethod)) {
					enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
							accountName);
				}
			} else {

				if (Utility.validateString(paymentMethod)) {
					// cardNumber variable can be used as Gift Card number (If Payment method: Gift
					// Certificate, Reservation number if Payment Method: Reservation
					enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
							accountName);
				}

			}
			enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
		}
		if (additionalGuests > 0) {
			ArrayList<String> firstNames = new ArrayList<>();
			ArrayList<String> lastNames = new ArrayList<>();
			for (int i = 0; i < additionalGuests; i++) {
				firstNames.add(guestFirstName + i);
				lastNames.add(guestLastName + i);
			}
			enterAddMoreGuestInfoDetails(driver, test_steps, additionalGuests, firstNames, lastNames);
		}
		enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
		if (Utility.validateString(noteSubject)) {
			enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);
		}
		if (Utility.validateString(taskCategory) && Utility.validateString(taskType)) {
			if (verifyTaskSection(driver, test_steps)) {
				enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee,
						taskStatus);
			}
		}

		/*
		 * Check Whether all mandatory fields are empty or not
		 */

		if (guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (marketSegment.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (referral.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else {
			missingMandatoryFields = false;
		}

		if (quote) {
			clickSaveQuote(driver, test_steps, missingMandatoryFields);
		} else {
			clickBookNow(driver, test_steps, missingMandatoryFields);
		}
		if (missingMandatoryFields) {
			data.put("requiredFieldValidation", res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
		} else {
			data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
			data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
			clickCloseReservationSavePopup(driver, test_steps);
			TripSummary tripSummary = getTripSummaryDetail(driver);
			data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
			data.put("Taxes", tripSummary.getTS_TAXES());
			data.put("requiredFieldValidation", "");
		}

		return data;
	}

	public void clickOnCreateGuestButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CREATE_GUEST_BUTTON);
		Utility.ScrollToElement(res.CREATE_GUEST_BUTTON, driver);
		try {
			Utility.clickThroughJavaScript(driver, res.CREATE_GUEST_BUTTON);
		} catch (Exception e) {
			res.CREATE_GUEST_BUTTON.click();
		}

		test_steps.add("====== Clicking on Create Guest Button ======");
		logger.info("====== Clicking on Create Guest Button ======");
	}

	public void turnOnOffcreateGuestProfileToggle(WebDriver driver, ArrayList<String> test_steps,
			boolean guestProfileToggle) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.wait3Second();
		Wait.WaitForElement(driver, OR_ReservationV2.CREATE_GUEST_PROFILE_TOGGLE);
		Utility.ScrollToElement(res.CREATE_GUEST_PROFILE_TOGGLE, driver);
		test_steps.add("====== Turning off 'Create Guest Profile' Toggle ====== get classname attribute");
		logger.info("====== Turning off 'Create Guest Profile' Toggle ======");
		res.CREATE_GUEST_PROFILE_TOGGLE.click();
		Wait.wait10Second();

	}

	
	public void clickOnCopyGuestInfoCheckboxforMRB(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CopyGuest_FromPrimaryRoomCheckbox);
		Utility.ScrollToElement(res.CopyGuest_FromPrimaryRoomCheckbox, driver);
		res.CopyGuest_FromPrimaryRoomCheckbox.click();

		test_steps.add("====== Clicking on 'Copy Guest Info From Primary Room' Checkbox ======");
		logger.info("====== Clicking on 'Copy Guest Info From Primary Room' Checkbox ======");

	}

	public String getRoomChargesFromGuestDetailsTab(WebDriver driver, String checkInDate, String checkOutDate) {
		String roomChargeXpath = "(//div[@title='Room Charges']/following-sibling::div[contains(@data-bind,'showInnroadCurrency')])[1]";
		Wait.waitForElementToBeVisibile(By.xpath(roomChargeXpath), driver);
		String roomCharges = driver.findElement(By.xpath(roomChargeXpath)).getText();
		roomCharges = roomCharges.replace("$", "");
		roomCharges = roomCharges.replaceAll(" ", "");
		String roomChargesPerNight = Double.toString((Double.parseDouble(roomCharges))
				/ (Double.parseDouble(Utility.differenceBetweenDates(checkInDate, checkOutDate))));
		return roomChargesPerNight;
	}

	public void searchDataForFindRooms(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String checkOutDate, String adultsForRes, String childrenForRes, String ratePlan, String promoCode)
			throws Exception {
		selectCheckInDate(driver, test_steps, checkInDate);
		selectCheckOutDate(driver, test_steps, checkOutDate);
		enter_Adults(driver, test_steps, adultsForRes);
		enter_Children(driver, test_steps, childrenForRes);
		select_Rateplan(driver, test_steps, ratePlan, promoCode);
//		clickOnFindRooms(driver, test_steps);
	}

	public void navigateToReservationPage(WebDriver driver) throws Exception {
		Navigation navigation = new Navigation();
		List<WebElement> newReservation = driver.findElements(By.xpath(OR_ReservationV2.CP_NewReservationButton));
		if (newReservation.size() == 0) {
			try {
				try {
					navigation.Reservation_Backward_3(driver);
				} catch (Exception e) {
					navigation.Reservation_Backward_1(driver);
				}
			} catch (Exception e) {
				navigation.reservation(driver);
			}
		}
		closeAllOpenedReservations(driver);
	}

	public void closeAllOpenedReservations(WebDriver driver) throws InterruptedException {
		String close = "//ul[@class='sec_nav']//li[5]//i";
		int size = driver.findElements(By.xpath(close)).size();
		if (size > 0) {
			do {
				try {
					// Wait.waitForElementToBeClickable(By.xpath(close), driver);
					Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
					driver.findElement(By.xpath(close)).click();
					size = driver.findElements(By.xpath(close)).size();
				} catch (Exception e) {
					close = "(//ul[@class='sec_nav']//li[5]//i)[2]";
					Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
					driver.findElement(By.xpath(close)).click();
					size = driver.findElements(By.xpath(close)).size();
				}
			} while (size > 0);

		}
	}

	public void closeReservationsWithUnSavedData(WebDriver driver, boolean unSaveData) throws InterruptedException {
		String close = "//ul[@class='sec_nav']//li[5]//i";
		String unsavedDataNo = "button[@data-bind='click: No, text: cancelText']";
		String unSavedDataYes = "button[@data-bind='click: Yes']//span";

		int size = driver.findElements(By.xpath(close)).size();
		if (size > 0) {
			do {
				try {
					Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
					driver.findElement(By.xpath(close)).click();
					size = driver.findElements(By.xpath(close)).size();

					Utility.ScrollToElement(driver.findElement(By.xpath(unSavedDataYes)), driver);
					driver.findElement(By.xpath(unSavedDataYes)).click();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (size > 0);
		}
	}

	public void closeAllOpenedReservationsExceptCurrent(WebDriver driver) throws InterruptedException {
		String close = "//ul[@class='sec_nav']//li[5]//i";
		int size = driver.findElements(By.xpath(close)).size();
		if (size > 0) {
			do {
				try {
					// Wait.waitForElementToBeClickable(By.xpath(close), driver);
					Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
					driver.findElement(By.xpath(close)).click();
					size = driver.findElements(By.xpath(close)).size();
				} catch (Exception e) {
					close = "(//ul[@class='sec_nav']//li[5]//i)[2]";
					Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
					driver.findElement(By.xpath(close)).click();
					size = driver.findElements(By.xpath(close)).size();
				}
			} while (size > 1);
		}
	}

	public void enterAddMoreGuestInfoDetails(WebDriver driver, ArrayList<String> test_steps, int guests,
			ArrayList<String> firstNames, ArrayList<String> lastNames) {
		for (int i = 0; i < guests; i++) {
			int j = i + 1;
			clickOnAddMoreGuestInfo(driver, test_steps);
			List<WebElement> firstNameFields = driver
					.findElements(By.xpath("//input[contains(@data-bind,'value:firstName, valueUpdate')]"));
			List<WebElement> lastNameFields = driver
					.findElements(By.xpath("//input[contains(@data-bind,'value:lastName, valueUpdate')]"));
			firstNameFields.get(firstNameFields.size() - 1).sendKeys(firstNames.get(i));
			lastNameFields.get(lastNameFields.size() - 1).sendKeys(lastNames.get(i));
			test_steps.add("Entering additional guest" + j + " first name as : " + firstNames.get(i));
			test_steps.add("Entering additional guest" + j + " last name as : " + lastNames.get(i));
		}
	}

	public void clickOnAddMoreGuestInfo(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADD_MORE_GUESTS), driver);
		Utility.clickThroughJavaScript(driver, res.ADD_MORE_GUESTS);
		test_steps.add("Clicking on Add More Guest Info button");
		logger.info("Clicking on Add More Guest Info");
		String warn = "//div[contains(text(),'The number of guest names added surpasses the number of"
				+ " guests booked in the room.')]/..//button[text()='Yes']";
		try {
			Wait.waitForElementToBeClickable(By.xpath(warn), driver);
			driver.findElement(By.xpath(warn)).click();
		} catch (Exception e) {

		}
	}

	public void select_HouseAccoutAsPayment(WebDriver driver, ArrayList<String> test_steps, String AccountName) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		res.CP_NewReservation_PaymentMethod.click();
		String houseAcc = "//span[text()='Account (House Account)']/..";
		Wait.WaitForElement(driver, houseAcc);
		driver.findElement(By.xpath(houseAcc)).click();
		test_steps.add("Selecting Account (House Account) as payment method");
		logger.info("Selecting Account (House Account) as payment method");

		String input = "//input[@name='houseaccount-autocomplete']";

		Wait.WaitForElement(driver, input);
		driver.findElement(By.xpath(input)).sendKeys(AccountName);
		String acc = "//b[text()='" + AccountName.trim() + "']";
		Wait.WaitForElement(driver, acc);
		driver.findElement(By.xpath(acc)).click();
		test_steps.add("Selecting Account (House Account) as payment method and the house account is : " + AccountName);
		logger.info("Selecting Account (House Account) as payment method and the house account is : " + AccountName);

	}

	public void selectReservation(WebDriver driver, String reservationNumber, ArrayList<String> test_steps)
			throws InterruptedException {
		String checkBoxPath = "//span[text()='" + reservationNumber
				+ "']//..//..//td[contains(@class,'bulkActionCheckbox')]//child::div[@data-bind='css: { checked: DeleteReservation }']";
		Wait.WaitForElement(driver, checkBoxPath);
		Wait.waitForElementToBeVisibile(By.xpath(checkBoxPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(checkBoxPath), driver);
		WebElement checkElement = driver.findElement(By.xpath(checkBoxPath));
		Utility.ScrollToElement(checkElement, driver);
		checkElement.click();
		test_steps.add("Selected reservation with number (" + reservationNumber + ")");
		logger.info("Searched reservation with number (" + reservationNumber + ")");

	}

	public void clickOnProcessButtonInBulkCheckInPopUp(WebDriver driver, ArrayList<String> test_steps)
			throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.explicit_wait_visibilityof_webelement_120(res.ClickProcessButtonBulkCheckIN, driver);
		res.ClickProcessButtonBulkCheckIN.click();
		logger.info("Process Button is Pressed Successfully");
		test_steps.add("Process Button is Pressed Successfully");
	}

	public void closeBulkActionPopUp(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			Wait.WaitForElement(driver, OR_ReservationV2.Close_BulkActionPopup);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			Utility.ScrollToElement_NoWait(res.Close_BulkActionPopup, driver);
			res.Close_BulkActionPopup.click();
		} catch (Exception e) {
			Utility.ScrollToElement_NoWait(res.CloseBulkActionPopUp, driver);
			res.CloseBulkActionPopUp.click();
		}
		test_steps.add("Bulk Action PopUp Closed Successfully");
		logger.info("Bulk Action PopUp Closed Successfully");

	}

	public void verifyReservationStatus(WebDriver driver, String reservationNumber, String expectedStatus,
			ArrayList<String> test_steps) throws Exception {

		String path = "//span[contains(text(),'" + reservationNumber
				+ "')]//..//following-sibling::td//span[contains(@data-bind,'text: StatusName.replace')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);

		WebElement element = driver.findElement(By.xpath(path));
		test_steps.add("Expected status: " + expectedStatus);
		test_steps.add("Found: " + element.getText());
		assertEquals(element.getText(), expectedStatus, "Failed: reservation status is mismatching!");
		test_steps.add("Verified resevrtaion number " + reservationNumber + " status");

	}

	public void clickOnProcessInBulkCheckOutPopUp(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ClickProcessButtonBulkCheckOut), driver);
		res.clickProcessButtonBulkCheckOut.click();
		logger.info("Process Button is Pressed Successfully");
		test_steps.add("Process Button is Pressed Successfully");

	}

	public void closeBulkCheckoutActionPopUp(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.closeBulkCheckoutPopUp);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.closeBulkCheckoutPopUp), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.closeBulkCheckoutPopUp), driver);
		res.closeBulkCheckoutPopUp.click();
		test_steps.add("Bulk Action PopUp Closed Successfully");
		logger.info("Bulk Action PopUp Closed Successfully");
	}

	public void verifyBulkCheckOutSuccessHeadng(WebDriver driver, ArrayList<String> test_steps) {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.bulkCheckOutSuccessHeading);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.bulkCheckOutSuccessHeading), driver);

		assertTrue(res.bulkCheckOutSuccessHeading.isDisplayed(), "Checkout Popup succes is not displaying");
		test_steps.add("Bulk Checkout success heading verified");
		logger.info("Bulk Checkout success heading verified");

	}

	// SearchandClickgivenReservationNumber
	public void searchAndSelectGivenReservationNumber(WebDriver driver, String reservation,
			ArrayList<String> test_steps) throws Exception {
		basicSearch_WithReservationNo(driver, reservation, false);
		selectReservation(driver, reservation, test_steps);
	}

	/*
	 * public void deleteReservation(WebDriver driver, String confirmationNum,
	 * String voidNote, String actionCategory, String guestName, ArrayList<String>
	 * test_steps) throws Exception { searchAndSelectGivenReservationNumber(driver,
	 * confirmationNum, test_steps); click_Folio(driver, test_steps); // String
	 * voidNote = "Testing void note"; voidAllLineItemsInReservation(driver,
	 * "Room Charge", voidNote); test_steps.add("Add void note: " + voidNote);
	 * test_steps.add("Void all Room Charge line item of reservation");
	 * Wait.wait5Second(); close_FirstOpenedReservation(driver, test_steps);
	 * searchWithGuestName(driver, test_steps, guestName);
	 * test_steps.add("searched resrevation created from tape chart: " + guestName);
	 * selectAllSearchedReservationCheckBox(driver, test_steps);
	 * test_steps.add("Selected reservation: " + guestName);
	 * clickBulkOptionCancelAction(driver, actionCategory, test_steps);
	 * clickOnProcessButtonInBulkCheckInPopUp(driver, test_steps);
	 * closeBulkActionPopUp(driver, test_steps);
	 * 
	 * }
	 */

	public void clickBulkActionOption(WebDriver driver, String option, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitUntilPresenceOfElementLocated(OR_ReservationV2.Click_Bulk_Action, driver);
		res.Click_Bulk_Action.click();
		test_steps.add("Click Bulk Action");
		String action = "//a/span[.='" + option + "']";

		Wait.waitUntilPresenceOfElementLocated(action, driver);
		driver.findElement(By.xpath(action)).click();
		test_steps.add("Clicked on " + option);
		logger.info("Clicked on " + option);
		Wait.explicit_wait_visibilityof_webelement_120(res.bulkActionPopup, driver);

	}

	public void selectAllSearchedReservationCheckBox(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.WaitForElement(driver, OR_ReservationV2.ClickOnCheckBoxProperty);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.ClickOnCheckBoxProperty), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ClickOnCheckBoxProperty), driver);
		res.clickOnCheckBoxProperty.click();
		test_steps.add("Successfully all reservations are selected ");

	}

	public ArrayList<String> getReservationNumbersOnBulkUpdatePopup(WebDriver driver, ArrayList<String> test_steps) {
		String res = "(//th[text()='Res#'])[4]/../../..//td[2]/span";
		ArrayList<String> resList = new ArrayList<>();
		Wait.waitForElementToBeVisibile(By.xpath("//h4[contains(text(),'Completed')]"), driver);
		Wait.WaitForElement(driver, res);
		List<WebElement> list = driver.findElements(By.xpath(res));
		for (int i = 0; i < list.size(); i++) {
			resList.add(Utility.getELementText(list.get(i), false, ""));
		}
		logger.info("Reservation numbers: " + resList);
		return resList;
	}

	public void bulkCheckIn(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		selectAllSearchedReservationCheckBox(driver, test_steps);
		clickBulkActionOption(driver, "Check In", test_steps);
		List<WebElement> eleList = driver
				.findElements(By.xpath("//a[text()='THE SELECTED RESERVATIONS CANNOT BE UPDATED']"));
		List<WebElement> eleBulk = driver.findElements(By.xpath("//h4[text()='Bulk Action Summary']"));
		if (eleList.size() > 0 && eleBulk.size() > 0) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			String reason = driver
					.findElement(By.xpath(
							"(//th[text()='Comments'])[1]/../../..//span[contains(@data-bind,'ErrorDescription ')]"))
					.getText();
			logger.info("Given Reservations can not be update. Reason: " + reason);
			test_steps.add("Given Reservations can not be update. Reason: <b>" + reason + "</b>");
			closeBulkActionPopUp(driver, test_steps);
		} else {
			// clickOnProcessButtonInBulkCheckInPopUp(driver, test_steps);
			clickOnProcessBulkAction(driver, test_steps);
			ArrayList<String> checkInList = getReservationNumbersOnBulkUpdatePopup(driver, test_steps);
			logger.info("Reservation Numbers: " + checkInList);
			closeBulkActionPopUp(driver, test_steps);
			for (int i = 0; i < checkInList.size(); i++) {
				logger.info("Reservation Number: " + checkInList.get(i));
				basicSearch_WithReservationNo(driver, checkInList.get(i), true);
				if (getStatusBarDetail(driver).getSB_RESERVATION_STATUS().equalsIgnoreCase("In-House")) {
					logger.info("Given Reservation number " + checkInList.get(i)
							+ " successfully CheckIn using Bulk CheckIn");
					test_steps.add("Given Reservation number " + checkInList.get(i)
							+ " successfully CheckIn using Bulk CheckIn");
				} else {
					logger.info("Failed to CheckIn the reservation using Bulk CheckIn for reservation number: "
							+ checkInList.get(i));
					test_steps.add("Failed to CheckIn the reservation using Bulk CheckIn for reservation number: "
							+ checkInList.get(i));
				}
				closeAllOpenedReservations(driver);
			}
		}

	}

	public void bulkCheckOut(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		selectAllSearchedReservationCheckBox(driver, test_steps);
		clickBulkActionOption(driver, "Check Out", test_steps);
		List<WebElement> eleList = driver
				.findElements(By.xpath("//a[text()='THE SELECTED RESERVATIONS CANNOT BE UPDATED']"));
		List<WebElement> eleBulk = driver.findElements(By.xpath("//h4[text()='Bulk Action Summary']"));
		if (eleList.size() > 0 && eleBulk.size() > 0) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			String reason = driver
					.findElement(By.xpath(
							"(//th[text()='Comments'])[1]/../../..//span[contains(@data-bind,'ErrorDescription ')]"))
					.getText();
			logger.info("Given Reservations can not be update. Reason: " + reason);
			test_steps.add("Given Reservations can not be update. Reason: <b>" + reason + "</b>");
			closeBulkActionPopUp(driver, test_steps);
		} else {
			// clickOnProcessInBulkCheckOutPopUp(driver, test_steps);
			clickOnProcessBulkAction(driver, test_steps);
			ArrayList<String> checkOutList = getReservationNumbersOnBulkUpdatePopup(driver, test_steps);
			closeBulkActionPopUp(driver, test_steps);
			for (int i = 0; i < checkOutList.size(); i++) {
				basicSearch_WithReservationNo(driver, checkOutList.get(i), true);
				if (getStatusBarDetail(driver).getSB_RESERVATION_STATUS().equalsIgnoreCase("Departed")) {
					logger.info("Given Reservation number " + checkOutList.get(i)
							+ " successfully CheckOut using Bulk CheckOut");
					test_steps.add("Given Reservation number " + checkOutList.get(i)
							+ " successfully CheckOut using Bulk CheckOut");

				} else {
					logger.info("Failed to CheckOut the reservation using Bulk CheckOut for reservation number: "
							+ checkOutList.get(i));
					test_steps.add("Failed to CheckOut the reservation using Bulk CheckOut for reservation number: "
							+ checkOutList.get(i));
				}
				closeAllOpenedReservations(driver);
			}
		}
	}

	public void enterBulkCancelReason(WebDriver driver, ArrayList<String> test_steps, String reason) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.bulkCancelReasonInput), driver);
		res.bulkCancelReasonInput.sendKeys(reason);
		logger.info("Enter Cancel reason");
		test_steps.add("Enter Cancel reason");
	}

	public void enableVoidRoomChargeBulkCancel(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharge) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		if (voidRoomCharge) {
			if (!res.bulkCancelVoidRoomCharges.isSelected()) {
				res.bulkCancelVoidRoomCharges.click();
				logger.info("Clicked on Void Roomcharge");
				test_steps.add("Clicked on Void Roomcharge");
			}
		} else {
			if (res.bulkCancelVoidRoomCharges.isSelected()) {
				res.bulkCancelVoidRoomCharges.click();
				logger.info("Unchecked Void Roomcharge checkbox");
				test_steps.add("Unchecked Void Roomcharge checkbox");
			}
		}
	}

	public void enablePostFeeBulkAction(WebDriver driver, ArrayList<String> test_steps, boolean postFee) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {
			// Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.bulkActionPostFee),
			// driver, 2);
			// Wait.WaitForElement(driver, OR_ReservationV2.bulkActionPostFee);
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.bulkActionPostFee), driver, 4);
			if (postFee) {
				if (!res.bulkActionPostFee.isSelected()) {
					res.bulkActionPostFee.click();
					logger.info("Enabled Post Cancellation/NoShow Fee on Bulk Action popup");
					test_steps.add("Enabled Post Cancellation/NoShow Fee on Bulk Action popup");
				}
			} else {
				if (res.bulkActionPostFee.isSelected()) {
					res.bulkActionPostFee.click();
					res.bulkActionPostFee.click();
					logger.info("Disabled Post Cancellation/NoShow Fee on Bulk Action popup");
					test_steps.add("Disabled Post Cancellation/NoShow Fee on Bulk Action popup");
				}
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public void clickOnProcessBulkAction(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.explicit_wait_visibilityof_webelement_120(res.clickProcessBulkAction, driver);
		res.clickProcessBulkAction.click();
		logger.info("Process Button is Pressed Successfully");
		test_steps.add("Process Button is Pressed Successfully");
	}

	public void bulkCancel(WebDriver driver, ArrayList<String> test_steps, String cancelReason, boolean voidRoomCharge,
			boolean postCancellationFee) throws Exception {
		selectAllSearchedReservationCheckBox(driver, test_steps);
		clickBulkActionOption(driver, "Cancel", test_steps);
		List<WebElement> eleList = driver
				.findElements(By.xpath("//a[text()='THE SELECTED RESERVATIONS CANNOT BE UPDATED']"));
		List<WebElement> eleBulk = driver.findElements(By.xpath("//h4[text()='Bulk Action Summary']"));
		if (eleList.size() > 0 && eleBulk.size() > 0) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			String reason = driver
					.findElement(By.xpath(
							"(//th[text()='Comments'])[1]/../../..//span[contains(@data-bind,'ErrorDescription ')]"))
					.getText();
			logger.info("Given Reservations can not be update. Reason: " + reason);
			test_steps.add("Given Reservations can not be update. Reason: <b>" + reason + "</b>");
			closeBulkActionPopUp(driver, test_steps);
		} else {
			enterBulkCancelReason(driver, test_steps, cancelReason);
			enableVoidRoomChargeBulkCancel(driver, test_steps, voidRoomCharge);
			enablePostFeeBulkAction(driver, test_steps, postCancellationFee);
			clickOnProcessBulkAction(driver, test_steps);
			ArrayList<String> cancelList = getReservationNumbersOnBulkUpdatePopup(driver, test_steps);
			closeBulkActionPopUp(driver, test_steps);
			for (int i = 0; i < cancelList.size(); i++) {
				basicSearch_WithReservationNo(driver, cancelList.get(i), true);
				if (getStatusBarDetail(driver).getSB_RESERVATION_STATUS().equalsIgnoreCase("Cancelled")) {
					logger.info("Given Reservation number " + cancelList.get(i)
							+ " successfully Cancelled using Bulk Cancel");
					test_steps.add("Given Reservation number " + cancelList.get(i)
							+ " successfully Cancelled using Bulk Cancel");

				} else {
					logger.info("Failed to Cancel the reservation using Bulk Cancel for reservation number: "
							+ cancelList.get(i));
					test_steps.add("Failed to Cancel the reservation using Bulk Cancel for reservation number: "
							+ cancelList.get(i));
				}
				closeAllOpenedReservations(driver);
			}
		}
	}

	public void bulkNoShow(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharge,
			boolean postNoShowFee) throws Exception {
		selectAllSearchedReservationCheckBox(driver, test_steps);
		clickBulkActionOption(driver, "No Show", test_steps);
		List<WebElement> eleList = driver
				.findElements(By.xpath("//a[text()='THE SELECTED RESERVATIONS CANNOT BE UPDATED']"));
		List<WebElement> eleBulk = driver.findElements(By.xpath("//h4[text()='Bulk Action Summary']"));
		if (eleList.size() > 0 && eleBulk.size() > 0) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			String reason = driver
					.findElement(By.xpath(
							"(//th[text()='Comments'])[1]/../../..//span[contains(@data-bind,'ErrorDescription ')]"))
					.getText();
			logger.info("Given Reservations can not be update. Reason: " + reason);
			test_steps.add("Given Reservations can not be update. Reason: <b>" + reason + "</b>");
			closeBulkActionPopUp(driver, test_steps);
		} else {
			enableVoidRoomChargeBulkCancel(driver, test_steps, voidRoomCharge);
			enablePostFeeBulkAction(driver, test_steps, postNoShowFee);
			clickOnProcessBulkAction(driver, test_steps);
			ArrayList<String> noShowList = getReservationNumbersOnBulkUpdatePopup(driver, test_steps);
			closeBulkActionPopUp(driver, test_steps);
			for (int i = 0; i < noShowList.size(); i++) {
				basicSearch_WithReservationNo(driver, noShowList.get(i), true);
				if (getStatusBarDetail(driver).getSB_RESERVATION_STATUS().equalsIgnoreCase("No Show")) {
					logger.info(
							"Given Reservation number " + noShowList.get(i) + " successfully Noshow using Bulk NoShow");
					test_steps.add(
							"Given Reservation number " + noShowList.get(i) + " successfully Noshow using Bulk NoShow");

				} else {
					logger.info("Failed to NoShow the reservation using Bulk NoShow for reservation number: "
							+ noShowList.get(i));
					test_steps.add("Failed to NoShow the reservation using Bulk NoShow for reservation number: "
							+ noShowList.get(i));
				}
				closeAllOpenedReservations(driver);
			}
		}
	}

	public void deleteReservation(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		selectAllSearchedReservationCheckBox(driver, test_steps);
		clickBulkActionOption(driver, "Delete", test_steps);
		List<WebElement> eleList = driver
				.findElements(By.xpath("//a[text()='THE SELECTED RESERVATIONS CANNOT BE UPDATED']"));
		List<WebElement> eleBulk = driver.findElements(By.xpath("//h4[text()='Bulk Action Summary']"));
		if (eleList.size() > 0 && eleBulk.size() > 0) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.Close_BulkActionPopup), driver);
			String reason = driver
					.findElement(By.xpath(
							"(//th[text()='Comments'])[1]/../../..//span[contains(@data-bind,'ErrorDescription ')]"))
					.getText();
			logger.info("Given Reservations can not be update. Reason: " + reason);
			test_steps.add("Given Reservations can not be update. Reason: <b>" + reason + "</b>");
			closeBulkActionPopUp(driver, test_steps);
		} else {
			clickOnProcessBulkAction(driver, test_steps);
			closeBulkActionPopUp(driver, test_steps);
		}
	}

	public void changeReservationStatus(WebDriver driver, ArrayList<String> test_steps, String status,
			boolean voidRoomCharge) throws Exception {
		// status = status.toUpperCase();
		switch (status) {
		case "In-House":
		case "CheckIn":
			checkInReservation(driver, test_steps);
			break;
		case "PrimaryCheckIn":
			checkIn_MrbPrimary(driver, test_steps);
			break;
		case "SecondaryCheckIn":
			checkIn_MrbSecondary(driver, test_steps);
			break;
		case "Departed":
		case "CheckOut":
			checkInReservation(driver, test_steps);
			checkOutReservation(driver, test_steps, "No", "No", "Test");
			break;
		case "PrimaryCheckOut":
			checkInReservation(driver, test_steps);
			checkOutMRBPrimary(driver, test_steps);
			break;
		case "SecondaryCheckOut":
			checkInReservation(driver, test_steps);
			checkOutMRBSecondary(driver, test_steps);
			break;
		case "Cancelled":
		case "Cancel":
			cancelReservation(driver, test_steps, voidRoomCharge, "Cancel");
			break;
		case "SecondaryCancel":
			cancel_MrbSecondary(driver, test_steps, voidRoomCharge, "Cancel");
			break;
		case "No Show":
		case "NoShow":
			noShowReservation(driver, test_steps, voidRoomCharge);
			break;
		case "Confirmed":
		case "Guaranteed":
		case "On Hold":
		case "OnHold":
			changeReservationStatusFromStatusDropdown(driver, test_steps, status);
			break;

		default:
			break;
		}
	}

	public void clickAddIncidentals(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.clickIncidentals), driver);
		res.clickIncidentals.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.EnterAddOn_IncidenalDate), driver);
	}

	public ArrayList<String> addIncidental(WebDriver driver, String getDate, String category, String perUnit,
			String quentity) throws Exception {
		Elements_ReservationV2 element = new Elements_ReservationV2(driver);
		ArrayList<String> test_steps = new ArrayList<>();
		clickAddIncidentals(driver, test_steps);
		Wait.WaitForElement(driver, OR_Reservation.EnterAddOn_IncidenalDate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Utility.ScrollToElement(element.EnterAddOn_IncidenalDate, driver);
		getDate = Utility.parseDate(getDate, "dd/MM/yyyy", "MM/dd/yyyy");
		element.EnterAddOn_IncidenalDate.click();
		element.EnterAddOn_IncidenalDate.clear();
		element.EnterAddOn_IncidenalDate.sendKeys(getDate);
		test_steps.add("Enter Date: " + getDate);

		element.ButtonSelectIncidental.click();
		test_steps.add("Click on select button");
		Wait.wait1Second();
		String pathSelectIncidental = "//div[text()='ADD-ONS & INCIDENTALS']//..//ul//li//span[text()='" + category
				+ "']";
		WebElement elementSelect = driver.findElement(By.xpath(pathSelectIncidental));
		elementSelect.click();
		test_steps.add("Select Category: " + category);

		element.EnterPerUnit.clear();
		element.EnterPerUnit.sendKeys(perUnit);
		test_steps.add("Enter Per Unit: " + perUnit);

		element.EnterQuentity.clear();
		element.EnterQuentity.sendKeys(quentity);
		test_steps.add("Enter Quentity: " + quentity);

		element.IncidentalSave.click();
		test_steps.add("Click on Save button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);
		return test_steps;
	}

	public ArrayList<String> EnterAddOn_Incidental(WebDriver driver, int days, String category, String perUnit,
			String quentity) throws Exception {
		Elements_CPReservation element = new Elements_CPReservation(driver);
		ArrayList<String> test_steps = new ArrayList<>();

		Wait.WaitForElement(driver, OR_Reservation.EnterAddOn_IncidenalDate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Utility.ScrollToElement(element.EnterAddOn_IncidenalDate, driver);
		String getDate = ESTTimeZone.DateFormateForLineItem(days);
		element.EnterAddOn_IncidenalDate.click();
		element.EnterAddOn_IncidenalDate.clear();
		element.EnterAddOn_IncidenalDate.sendKeys(getDate);
		test_steps.add("Enter Date: " + getDate);

		element.ButtonSelectIncidental.click();
		test_steps.add("Click on select button");
		Wait.wait1Second();
		String pathSelectIncidental = "//div[text()='ADD-ONS & INCIDENTALS']//..//ul//li//span[text()='" + category
				+ "']";
		WebElement elementSelect = driver.findElement(By.xpath(pathSelectIncidental));
		elementSelect.click();
		test_steps.add("Select Category: " + category);

		element.EnterPerUnit.clear();
		element.EnterPerUnit.sendKeys(perUnit);
		test_steps.add("Enter Per Unit: " + perUnit);

		element.EnterQuentity.clear();
		element.EnterQuentity.sendKeys(quentity);
		test_steps.add("Enter Quentity: " + quentity);

		element.IncidentalSave.click();
		test_steps.add("Click on Save button");
		Wait.WaitForElementUsingClassName(driver, OR.Toaster_Title);
		Wait.waitForElementToBeVisibile(By.className(OR.Toaster_Title), driver);
		Wait.waitForElementToBeClickable(By.className(OR.Toaster_Title), driver);
		return test_steps;
	}

	public void clickTakePayment(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.takePaymentButton), driver);
		// res.takePaymentButton.click();
		Utility.ScrollToElement(res.takePaymentButton, driver);
		Utility.clickThroughJavaScript(driver, res.takePaymentButton);
		logger.info("Clicked on Take Payment button");
		test_steps.add("Clicked on Take Payment button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentHeader), driver);
	}

	public void enterAmountTakePayment(WebDriver driver, ArrayList<String> test_steps, boolean isChangePayAmount,
			String changedAmountValue) throws InterruptedException {

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.takePaymentHeader);
		Wait.WaitForElement(driver, OR_ReservationV2.takePaymentAmountInput);
		String amount = res.takePaymentAmountInput.getText().trim();
		Utility.app_logs.info("amount : " + amount);
		test_steps.add("Amount before Pay : " + amount);
		logger.info("Amount before Pay : " + amount);

		if (isChangePayAmount) {
			test_steps.add("Change the pay amount value : Yes");
			logger.info("Change the pay amount value : Yes");
			res.takePaymentAmountInput.clear();
			res.takePaymentAmountInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			res.takePaymentAmountInput.sendKeys(changedAmountValue);
			res.takePaymentAmountInput.sendKeys(Keys.TAB);
			test_steps.add("Enter the Change Amount Value : " + changedAmountValue);
			logger.info("Enter the Change Amount Value : " + changedAmountValue);
			amount = changedAmountValue;
			Wait.wait10Second();
		} else {
			test_steps.add("paying the amount : " + amount);
			logger.info("paying the amount : " + amount);
		}
	}

	public void clickPayTakePayment(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.takePaymentPayButton), driver);
		try {
			Utility.ScrollToElement(res.takePaymentPayButton, driver);
			Utility.clickThroughJavaScript(driver, res.takePaymentPayButton);
		} catch (Exception e) {
			Utility.ScrollToElement(res.takePaymentPayButton, driver);
			res.takePaymentPayButton.click();
		}
		Wait.wait5Second();
		logger.info("Clicked on Pay button");
		test_steps.add("Clicked on Pay button");
	}

	public void addNotesPayment(WebDriver driver, String notes) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		if (Utility.validateString(notes)) {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.checkOutPaymentAddNotes), driver);
			res.checkOutPaymentAddNotes.click();
			res.checkOutPaymentNotesInput.sendKeys(notes);
		}
	}

	public void closePaymentSuccessfullPopup(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.closePaymentSuccessPopup), driver);
		Utility.ScrollToElement(res.closePaymentSuccessPopup, driver);
		res.closePaymentSuccessPopup.click();
		test_steps.add("Closing payment successful popup");
		logger.info("Closing payment successful popup");
	}

	public void takePayment(WebDriver driver, ArrayList<String> test_steps, boolean isChangePayAmount,
			String changedAmountValue, String notes, boolean isSetPaymentMethod) throws InterruptedException {

		clickTakePayment(driver, test_steps);
		if (Utility.validateString(changedAmountValue)) {
			enterAmountTakePayment(driver, test_steps, isChangePayAmount, changedAmountValue);
		}
		if (Utility.validateString(notes)) {
			addNotesPayment(driver, notes);
		}
		if (isSetPaymentMethod) {
			checkOutPaymentSetAsMainPaymentMethod(driver, test_steps, "No");
		}
		clickPayTakePayment(driver, test_steps);
		closePaymentSuccessfullPopup(driver, test_steps);
	}

	public void clickConfirmNoShowButton(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CONFIRM_NOSHOW_BUTTON), driver);
		res.CONFIRM_NOSHOW_BUTTON.click();
	}

	public void noShowReservation(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharges)
			throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		changeReservationStatus(driver, "No Show", test_steps);
		enableVoidRoomCharge(driver, test_steps, voidRoomCharges);
		if (res.PROCEED_TO_NO_SHOW_PAYMENT_BUTTON.isDisplayed()) {
			clickOnProceedToNoShowPayment(driver, test_steps);
			selectCashOnCancellationPaymentPopup(driver, test_steps);
			clickOnPayButtonOnPaymentPopup(driver, test_steps);
		} else {
			System.out.println("----------");
			clickConfirmNoShowButton(driver, test_steps);
		}
		try {
			noShowSuccessPopupClose(driver, test_steps);
		} catch (Exception e) {
			try {
				noShowPaymentSuccessPopupClose(driver, test_steps);
			} catch (Exception e1) {
				logger.info(e.toString());
			}
		}

		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SB_GUEST_NAME), driver);
		ReservationStatusBar statusBar = getStatusBarDetail(driver);
		if (statusBar.getSB_RESERVATION_STATUS().equalsIgnoreCase("No Show")) {
			logger.info("Successfully No Show the reservation");
			test_steps.add("Successfully No Show the reservation");
		} else {
			logger.info("Failed to No Show the reservation");
			test_steps.add("Failed to No Show the reservation");
		}
	}

	public void noShowPaymentSuccessPopupClose(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.NO_SHOW_SUCCESSFULL_POPUP_CLOSE), driver, 10);
		Utility.clickThroughJavaScript(driver, res.NO_SHOW_SUCCESSFULL_POPUP_CLOSE);
		test_steps.add("Closing No Show successful popup");
		logger.info("Closing No Show successful popup");
	}

	public void noShowSuccessPopupClose(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.NO_SHOW_PAYMENT_POPUP_CLOSE), driver);
		Utility.clickThroughJavaScript(driver, res.NO_SHOW_PAYMENT_POPUP_CLOSE);
		test_steps.add("Closing No Show payment successful popup");
		logger.info("Closing No Show payment successful popup");
	}

	public void clickOnProceedToNoShowPayment(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PROCEED_TO_NO_SHOW_PAYMENT_BUTTON), driver);
		res.PROCEED_TO_NO_SHOW_PAYMENT_BUTTON.click();
		test_steps.add("Clicking on Proceed To No Show Payment button");
		logger.info("Clicking on Proceed To No Show Payment button");
	}

	public double getAmountExcludingCurrency(String amount) {
		double money = Double.parseDouble(amount.replaceAll("[$£ ]", "").trim());
		return money;
	}

	public ReservationRatesDetail getOverallRatesDetail(WebDriver driver, String roomClass) {
		String roomClassPath = "//div[contains(@data-bind,'click: expandRoomClassRateInfo')]//span[text()='" + roomClass
				+ "']";
		String avgRatePath = roomClassPath + "/../../../..//span[contains(@data-bind,'Avg per night')]";
		String availableRoomPath = roomClassPath + "/../../../..//span[contains(@data-bind,'availabilityCount')]";
		String totalRatePath = roomClassPath + "/../../../..//span[contains(@data-bind,'total.toFixed(2)')]";

		ReservationRatesDetail ratesDetail = new ReservationRatesDetail(roomClass,
				Utility.getELementText(driver.findElement(By.xpath(avgRatePath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(availableRoomPath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(totalRatePath)), false, ""));
		return ratesDetail;
	}

	public ReservationRatesDetail getFullRatesDetail(WebDriver driver, String roomClass) {
		String roomClassPath = "//div[contains(@data-bind,'click: expandRoomClassRateInfo')]//span[text()='" + roomClass
				+ "']";
		String avgRatePath = roomClassPath + "/../../../..//span[contains(@data-bind,'Avg per night')]";
		String availableRoomPath = roomClassPath + "/../../../..//span[contains(@data-bind,'availabilityCount')]";
		String totalRatePath = roomClassPath + "/../../../..//span[contains(@data-bind,'total.toFixed(2)')]";

		ReservationRatesDetail ratesDetail = new ReservationRatesDetail(roomClass,
				Utility.getELementText(driver.findElement(By.xpath(avgRatePath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(availableRoomPath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(totalRatePath)), false, ""),
				getPerNightRateList(driver, roomClass));
		return ratesDetail;
	}

	public HashMap<String, String> getPerNightRateList(WebDriver driver, String roomClass) {

		String roomClassPath = "//div[contains(@data-bind,'click: expandRoomClassRateInfo')]//span[text()='" + roomClass
				+ "']";
		String perNightTablePath = roomClassPath + "/../../../../..//tbody[contains(@data-bind,'nightlyRateItems')]/tr";

		expandCollapseRateDetail(driver, roomClass, true);
		waitForSweetLoading(driver);
		waitForSweetLoading(driver);
		waitForSweetLoading(driver);
		int size = driver.findElements(By.xpath(perNightTablePath)).size();

		HashMap<String, String> map = new HashMap<>();

		for (int i = 1; i <= size; i++) {
			logger.info(perNightTablePath + "[" + i + "]/td[1]");
			logger.info(perNightTablePath + "[" + i + "]/td[3]");
			String date = Utility.getELementText(driver.findElement(By.xpath(perNightTablePath + "[" + i + "]/td[1]")),
					false, "");
			String rate = Utility.getELementText(driver.findElement(By.xpath(perNightTablePath + "[" + i + "]/td[3]")),
					false, "");
			map.put(date, rate);
		}
		System.out.println(map);
		return map;
	}

	public void expandCollapseRateDetail(WebDriver driver, String roomClass, boolean isExpand) {
		String roomClassPath = "//div[contains(@data-bind,'click: expandRoomClassRateInfo')]//span[text()='" + roomClass
				+ "']";
		String expandTablePath = roomClassPath
				+ "/following-sibling::div[contains(@data-bind,'showNightlyRateDetails')]";
		String expandedPath = roomClassPath
				+ "/following-sibling::div[contains(@class,'toggle-roomClassDetails-open')]";

		if (isExpand) {
			if (!(driver.findElements(By.xpath(expandedPath)).size() > 0)) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(expandTablePath)));
				logger.info("Nightly Rate Detail Table Expanded");
			} else {
				logger.info("Nightly Rate Detail Table Already Expanded");
			}
		} else {
			if ((driver.findElements(By.xpath(expandedPath)).size() > 0)) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(expandTablePath)));
				logger.info("Nightly Rate Detail Table Collapsed");
			} else {
				logger.info("Nightly Rate Detail Table Already Collapsed");
			}
		}
		waitForSweetLoading(driver);
	}

	public ReservationRatesDetail getFullRatesDetail_ChangeStay_MRB(WebDriver driver, ArrayList<String> test_steps,
			String roomClass, String newCheckIn, String newCheckOut, String changeOption, int guest) throws Exception {
		String threeDotsXpath = "(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + guest + "]";
		String changeStayDatesXpath = "(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[" + guest + "]";
		Wait.waitForElementToBeClickable(By.xpath(threeDotsXpath), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(threeDotsXpath)), driver);
		driver.findElement(By.xpath(threeDotsXpath)).click();
		Wait.waitForElementToBeClickable(By.xpath(changeStayDatesXpath), driver);
		driver.findElement(By.xpath(changeStayDatesXpath)).click();

		waitForSweetLoading(driver);
		// selectCheckInDate(driver, test_steps, newCheckIn);
		// selectCheckOutDate(driver, test_steps, newCheckOut);
		selectCheckInDate(driver, test_steps, newCheckIn);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);

		ReservationRatesDetail obj = getFullRatesDetail(driver, roomClass);

		clickCloseStayInfo(driver);

		return obj;
	}

	public ReservationRatesDetail getFullRatesDetail_ChangeStay(WebDriver driver, ArrayList<String> test_steps,
			String roomClass, String newCheckIn, String newCheckOut, String changeOption) throws Exception {
		clickEditStayInfo(driver);
		clickChangeStayDetails(driver);
		waitForSweetLoading(driver);
		waitForSweetLoading(driver);
		// selectCheckInDate(driver, test_steps, newCheckIn);
		// selectCheckOutDate(driver, test_steps, newCheckOut);
		selectCheckInDate(driver, test_steps, newCheckIn);
		selectCheckOutDate(driver, test_steps, newCheckOut);
		selectStayInfoOption(driver, changeOption);
		clickFindRoomsStayInfo(driver);
		waitForSweetLoading(driver);

		ReservationRatesDetail obj = getFullRatesDetail(driver, roomClass);

		clickCloseStayInfo(driver);

		return obj;
	}

	public HashMap<String, ReservationRatesDetail> getFullRatesDetail_Creation(WebDriver driver,
			ArrayList<String> test_steps, String roomClass, String checkInDate, String checkOutDate, String adults,
			String children, String ratePlan, String promoCode) throws Exception {
		click_NewReservation(driver, test_steps);

		searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
				promoCode);
		clickOnFindRooms(driver, test_steps);

		waitForSweetLoading(driver);
//		selectCheckInDate(driver, test_steps, newCheckIn);
//		selectCheckOutDate(driver, test_steps, newCheckOut);
//		selectStayInfoOption(driver, changeOption);
//		clickFindRoomsStayInfo(driver);
//		waitForSweetLoading(driver);
		HashMap<String, ReservationRatesDetail> map = new HashMap<String, ReservationRatesDetail>();
		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClass, "|");
		for (String room : roomClassList) {
			ReservationRatesDetail obj = getFullRatesDetail(driver, room);
			map.put(room, obj);
			selectRoomToReserveRandom(driver, test_steps, room, "Yes");
		}

		closeAllOpenedReservations(driver);

		return map;
	}

	public void clickCloseStayInfo(WebDriver driver) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.STAY_INFO_CLOSE_ICON), driver);
			elements.STAY_INFO_CLOSE_ICON.click();
			logger.info("Clicked on Close button");
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	public HashMap<String, String> calculateRatesAsPerAdultsAndChildCapacity(HashMap<String, String> ratePerNight,
			String ratePlanAdult, String ratePlanChild, HashMap<String, String> addAdultPerNight,
			HashMap<String, String> addChildPerNight, String roomClassPersons, String roomClassAdult,
			String reservationAdult, String reservationChild, List<Date> dates, String format)
			throws InterruptedException, NumberFormatException, ParseException {

		double sum = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		ArrayList<Double> rateAdultChild = new ArrayList<Double>();
		HashMap<String, String> rate = new HashMap<String, String>();

		int reservationTotalAdultAndChild = Integer.parseInt(reservationAdult) + Integer.parseInt(reservationChild);
		if (!reservationAdult.equals("0") && !reservationChild.equals("0")) {
			if (reservationTotalAdultAndChild == Integer.parseInt(roomClassPersons)
					|| reservationTotalAdultAndChild < Integer.parseInt(roomClassPersons)) {
				if (Integer.parseInt(reservationAdult) == Integer.parseInt(ratePlanAdult)
						|| Integer.parseInt(reservationAdult) < Integer.parseInt(ratePlanAdult)) {

					for (int i = 0; i < dates.size() - 1; i++) {
						logger.info(ratePerNight);
						rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
								format);

						logger.info(rateAdultChild);
						int childs = reservationTotalAdultAndChild - Integer.parseInt(ratePlanChild);
						if (childs <= 0) {
							childs = 0;
						}
						logger.info(childs);
						double child = rateAdultChild.get(0) + (childs * rateAdultChild.get(2));
						logger.info(child);
						sum = sum + child;
						logger.info(sum);
						rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));

					}
					logger.info(rate);

				} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(ratePlanAdult)
						&& !(Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult))) {
					for (int i = 0; i < dates.size() - 1; i++) {
						rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
								format);
						int couldBeChildOrAdult = reservationTotalAdultAndChild - Integer.parseInt(ratePlanChild);
						int adult = Integer.parseInt(reservationAdult) - Integer.parseInt(ratePlanAdult);
						int child = couldBeChildOrAdult - adult;
						double totalRate = rateAdultChild.get(0)
								+ ((adult * rateAdultChild.get(1)) + (child * rateAdultChild.get(2)));
						sum = sum + totalRate;
						rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));

					}
					logger.info(rate);

				} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult)) {
					// error
				}
			} else if (reservationTotalAdultAndChild > Integer.parseInt(roomClassPersons)) {
				// error
			}

		} else if (reservationAdult.equals("0") && Integer.parseInt(reservationChild) > Integer.parseInt(ratePlanChild)
				&& !(Integer.parseInt(reservationChild) > Integer.parseInt(roomClassPersons))) {
			for (int i = 0; i < dates.size() - 1; i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
						format);
				int alwaysChild = Integer.parseInt(reservationChild) - Integer.parseInt(ratePlanChild);
				double child = rateAdultChild.get(0) + (alwaysChild * rateAdultChild.get(2));
				sum = sum + child;
				rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));

			}
			logger.info(rate);

		} else if (reservationChild.equals("0") && Integer.parseInt(reservationAdult) > Integer.parseInt(ratePlanAdult)
				&& !(Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult))) {
			for (int i = 0; i < dates.size() - 1; i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
						format);
				int alwaysAdult = Integer.parseInt(reservationAdult) - Integer.parseInt(ratePlanAdult);
				double child = rateAdultChild.get(0) + (alwaysAdult * rateAdultChild.get(1));
				sum = sum + child;
				rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));
			}

			logger.info(rate);

		} else if (reservationAdult.equals("0")
				&& Integer.parseInt(reservationChild) > Integer.parseInt(roomClassPersons)) {
			// error
		} else if (reservationChild.equals("0")
				&& Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult)) {
			// error
		}

		else if (reservationAdult.equals("0") && reservationChild.equals("0")) {
			// error
		} else if (Integer.parseInt(reservationAdult) > Integer.parseInt(roomClassAdult)
				&& Integer.parseInt(reservationChild) > Integer.parseInt(roomClassPersons)) {
			// error
		} else if (reservationAdult.equals("0")
				&& Integer.parseInt(reservationChild) <= Integer.parseInt(ratePlanChild)) {
			for (int i = 0; i < dates.size() - 1; i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
						format);
				sum = sum + rateAdultChild.get(0);
				rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));
			}
			logger.info(rate);

		} else if (reservationChild.equals("0")
				&& Integer.parseInt(reservationAdult) <= Integer.parseInt(ratePlanAdult)) {
			for (int i = 0; i < dates.size() - 1; i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
						format);
				sum = sum + rateAdultChild.get(0);
				rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));
			}
			logger.info(rate);

		} else if (Integer.parseInt(reservationAdult) < Integer.parseInt(ratePlanAdult)
				&& Integer.parseInt(reservationChild) < Integer.parseInt(ratePlanChild)) {
			for (int i = 0; i < dates.size() - 1; i++) {
				rateAdultChild = calculationDateWise(ratePerNight, addAdultPerNight, addChildPerNight, dates, i,
						format);
				sum = sum + rateAdultChild.get(0);
				rate.put(Utility.convertDateFormattoString(format, dates.get(i)), df.format(sum));
			}
			logger.info(rate);

		}

		return rate;

	}

	private ArrayList<Double> calculationDateWise(HashMap<String, String> ratePerNight,
			HashMap<String, String> addAdultPerNight, HashMap<String, String> addChildPerNight, List<Date> dates,
			int index, String format) throws NumberFormatException, ParseException {
		ArrayList<Double> rateAdultChild = new ArrayList<Double>();
		double ratePerNights = 0.00, adultPerNight = 0.00, childPerNight = 0.00;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		if (StringUtils.isNumericSpace(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double
					.parseDouble(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			ratePerNights = Double.parseDouble(doubleValue);
			rateAdultChild.add(ratePerNights);
		} else if (!(StringUtils
				.isNumericSpace(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			ratePerNights = Double
					.parseDouble(ratePerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(ratePerNights);
		}
		if (StringUtils
				.isNumericSpace(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double
					.parseDouble(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			adultPerNight = Double.parseDouble(doubleValue);
			rateAdultChild.add(adultPerNight);
		} else if (!(StringUtils
				.isNumericSpace(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			adultPerNight = Double
					.parseDouble(addAdultPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(adultPerNight);
		}
		if (StringUtils
				.isNumericSpace(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))))) {
			double a = Double
					.parseDouble(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			String doubleValue = df.format(a);
			childPerNight = Double.parseDouble(doubleValue);
			rateAdultChild.add(childPerNight);
		} else if (!(StringUtils
				.isNumericSpace(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index)))))) {
			childPerNight = Double
					.parseDouble(addChildPerNight.get(Utility.convertDateFormattoString(format, dates.get(index))));
			rateAdultChild.add(childPerNight);
		}

		return rateAdultChild;
	}

	public void verifyRates(HashMap<String, ReservationRatesDetail> acctual,
			HashMap<String, HashMap<String, String>> expected, String accutalFormat, String expectedFormat,
			String roomClasses, String checkInDate, ArrayList<String> test_steps) {

		ArrayList<String> roomClassList = Utility.convertTokenToArrayList(roomClasses, "|");
		ArrayList<String> checkInList = Utility.convertTokenToArrayList(checkInDate, "|");

		for (String roomClass : roomClassList) {
			ReservationRatesDetail ratesDetail = acctual.get(roomClass);
			HashMap<String, String> acctualDateWiseRates = ratesDetail.getPerNightRates();
			HashMap<String, String> expectedDateWiseRates = expected.get(roomClass);

//			for(String date : checkInList) {
			String date = checkInList.get(roomClassList.indexOf(roomClass));
			logger.info(acctualDateWiseRates);
			logger.info(expectedDateWiseRates);

			Utility.customAssert(
					Utility.removeDollarBracketsAndSpaces(
							acctualDateWiseRates.get(Utility.parseDate(date, accutalFormat, expectedFormat))),
					Utility.removeDollarBracketsAndSpaces(expectedDateWiseRates.get(date)), true,
					"Successfully Verified Rate " + expectedDateWiseRates.get(date) + " Where Date is : " + date,
					"Failled To Verify Rate Where Date is : " + date, true, test_steps);

//			}

		}

	}

	public ArrayList<ArrayList<String>> intervalRateVerification(WebDriver driver, String dateFormat,
			String checkInDate, String checkOutDate, String adults, String child, String roomClass,
			String intervalLength,

			String baseAmount, String adultCapacity, String personCapacity, boolean isAdditionalChargesApplied,
			String maxAdult, String maxPerson, String additionalAdultCharges, String additionalChildCharges,
			boolean isProRateApplied, String proRatePerNight, String proRateAdditionalAdultCharges,
			String proRateAdditionalChildCharges, String folioName, ArrayList<String> testSteps)
			throws InterruptedException, NumberFormatException, ParseException {

		Elements_CPReservation reservationElement = new Elements_CPReservation(driver);
		ArrayList<String> rateList = new ArrayList<>();
		ArrayList<String> dates = new ArrayList<>();
		String isRoomChargesEqual = "no";

		double pro_rate = Double.parseDouble(proRatePerNight);
		double base_amount = Double.parseDouble(baseAmount);

		if (pro_rate > base_amount)
			pro_rate = base_amount;

		// Number of Days
		int numberofDays = Integer
				.parseInt(Utility.differenceBetweenDates(Utility.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
						Utility.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy")));

		String rateApplied = "";

		int fullIntervals = 0;
		int halfIntervals = 0;
		logger.info("numberofDays: " + numberofDays);
		if (numberofDays >= (Integer.parseInt(intervalLength))) {
			fullIntervals = numberofDays / Integer.parseInt(intervalLength);
			halfIntervals = numberofDays % Integer.parseInt(intervalLength);
		}
		logger.info("Full Interval:" + fullIntervals);
		logger.info("Half Interval:" + halfIntervals);

		testSteps.add("Interval lenght: " + intervalLength);
		testSteps.add("Number of nights: " + numberofDays);
		testSteps.add("Full interval: " + fullIntervals);
		testSteps.add("Half interval: " + halfIntervals);

		int interval = Integer.parseInt(intervalLength);
		testSteps.add("");

//		String totalRateValue = "//div[contains(@class,'room-search-result-container')]//span[text()='" + roomClass
//				+ "']/ancestor::div[contains(@data-bind,'click: expandRoomClassRateInfo')]/div/following-sibling::div/div/span[contains(@data-bind,'text: $root.GetCurrencySymbol')]";

//		String avgPerNight = "//div[contains(@class,'room-search-result-container')]//span[text()='" + roomClass
//				+ "']/ancestor::div[contains(@data-bind,'click: expandRoomClassRateInfo')]/div/div[2]/div/span[contains(text(),'Avg per night')]";
		String totalRateValueMRB = "//div[@id='rcDtails']//span[text()='" + roomClass
				+ "']/ancestor::div[contains(@data-bind,'click: expandRoomClassRateInfo')]/div/following-sibling::div/div/span[contains(@data-bind,'text: $root.GetCurrency')]";

		String roomClassMRBExpand = "//div[@id='rcDtails']//span[text()='" + roomClass
				+ "']/ancestor::div[contains(@data-bind,'click: expandRoomClassRateInfo')]";
//		String roomClassExpand = "//div[contains(@class='room-search-result-container']//span[text()='" + roomClass
//				+ "']/ancestor::div[contains(@data-bind,'click: expandRoomClassRateInfo')]";

		String roomClassPath = "//div[contains(@data-bind,'click: expandRoomClassRateInfo')]//span[text()='" + roomClass
				+ "']";
		String avgPerNight = roomClassPath + "/../../../..//span[contains(@data-bind,'Avg per night')]";
		String availableRoomPath = roomClassPath + "/../../../..//span[contains(@data-bind,'availabilityCount')]";
		String totalRateValue = roomClassPath + "/../../../..//span[contains(@data-bind,'total.toFixed(2)')]";

		String roomClassExpand = roomClassPath
				+ "/following-sibling::div[contains(@data-bind,'showNightlyRateDetails')]";

		WebElement totalRateValueElement = null;
		WebElement roomClassExpandElement = null;
		WebElement averagePerNightElement = null;
		Wait.wait10Second();
		Wait.WaitForElement(driver, avgPerNight);
//		Wait.waitForElementToBeInVisibile(By.xpath(avgPerNight), driver);
		Wait.waitForElementToBeClickable(By.xpath(avgPerNight), driver);
		averagePerNightElement = driver.findElement(By.xpath(avgPerNight));

		try {
			totalRateValueElement = driver.findElement(By.xpath(totalRateValue));
			roomClassExpandElement = driver.findElement(By.xpath(roomClassExpand));
		} catch (Exception e) {
			totalRateValueElement = driver.findElement(By.xpath(totalRateValueMRB));
			roomClassExpandElement = driver.findElement(By.xpath(roomClassMRBExpand));

		}
		// IF Pro-Rate/Addtional Charges off
		String averagePerNight = "";

		logger.info("baseAmount: " + baseAmount);
		double intial = 0;

		if ((Integer.parseInt(adults) + Integer.parseInt(child)) <= Integer.parseInt(personCapacity)) {
			if (!isProRateApplied && !isAdditionalChargesApplied) {
				if (fullIntervals == 0) {

					intial = base_amount;

				}

				else if (fullIntervals > 0 && halfIntervals == 0) {

					intial = base_amount * fullIntervals;

				} else {

					int total = fullIntervals + 1;
					intial = base_amount * total;

				}
				logger.info("Calculated Rate:" + intial);

			}
			if (isProRateApplied && !isAdditionalChargesApplied) {

				logger.info("======Pro stay only =======");

				if (fullIntervals == 0) {

					intial = base_amount;

				} else if (fullIntervals > 0 && halfIntervals == 0) {

					intial = base_amount * fullIntervals;
				}

				else {

					double total_fullAmount = base_amount * fullIntervals;
					logger.info("total_fullAmount: " + total_fullAmount);

					double total_halfAmount = (Double.parseDouble(proRatePerNight) * halfIntervals);
					logger.info("total_halfAmount: " + total_halfAmount);
					intial = total_fullAmount + total_halfAmount;

				}

				logger.info("Calculated Rate:" + intial);

			}

			int adultDifference = 0;
			int childDifference = 0;
			int personDifference = 0;

			logger.info("child: " + child);
			logger.info("adults: " + adults);
			int child_adults = Integer.parseInt(child) + Integer.parseInt(adults);
			logger.info("child_adults" + child_adults);

			// if Additional Charges Applied ANd ProRate False
			if (isAdditionalChargesApplied && !isProRateApplied) {
				logger.info("**********************************Third if:");

				if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)) {
					if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
						adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));

					child_adults = child_adults - adultDifference;
					int count = 0;
					boolean isTrue = false;
					int intMaxPerson = Integer.parseInt(maxPerson);
					if (child_adults > intMaxPerson) {
						for (int i = 1; i < child_adults; i++) {
							child_adults = child_adults - 1;
							if (child_adults == intMaxPerson) {
								count = i;
								isTrue = true;
								break;
							}
						}

					}

					if (isTrue) {
						childDifference = count;
					}
					logger.info("Additonal Adult:" + adultDifference);
					logger.info("Additonal Child:" + childDifference);

					if (fullIntervals == 0) {

						logger.info("adult: " + adultDifference * (Double.parseDouble(additionalAdultCharges)));
						logger.info("child: " + childDifference * (Double.parseDouble(additionalChildCharges)));
						logger.info("base_amount: " + base_amount);
						logger.info("fullIntervals: 0");

						intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));

					}

					else if (fullIntervals > 0 && halfIntervals == 0) {

						double one = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));

						logger.info("one: " + one);
						intial = one * fullIntervals;

					} else {

						int total = fullIntervals + 1;
						double one = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));

						logger.info("one: " + one);
						intial = one * total;
					}
					logger.info("expected rate: " + intial);

				}

			}
			// if Additional Charges Applied And ProRate True
			if (isAdditionalChargesApplied && isProRateApplied) {
				System.out.println("**********************************4th if:");

				if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)
						|| numberofDays < (Integer.parseInt(intervalLength))) {

					if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
						adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));

					child_adults = child_adults - adultDifference;
					// 5
					int count = 0;
					boolean isTrue = false;
					int intMaxPerson = Integer.parseInt(maxPerson);
					if (child_adults > intMaxPerson) {
						for (int i = 1; i < child_adults; i++) {
							child_adults = child_adults - 1;
							System.out.println("total: " + child_adults);
							System.out.println("max person: " + intMaxPerson);
							if (child_adults == intMaxPerson) {
								System.out.println("in total = max person");
								count = i;
								isTrue = true;
								break;
							}
						}

					}

					if (isTrue) {
						childDifference = count;
					}

					logger.info("Additonal Adult:" + adultDifference);
					logger.info("Additonal Child:" + childDifference);

					testSteps.add("Additonal Adult:" + adultDifference);
					testSteps.add("Additonal Child:" + childDifference);

					if (adultDifference > 0 || childDifference > 0) {

						if (fullIntervals > 0 && halfIntervals > 0) {
							//
							logger.info("apply half and ful both");
							double fullamount = base_amount
									+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));

							fullamount = fullIntervals * fullamount;

							double half_amount = Double.parseDouble(proRatePerNight)
									+ (adultDifference * Double.parseDouble(proRateAdditionalAdultCharges))
									+ (childDifference * (Double.parseDouble(proRateAdditionalChildCharges)));

							half_amount = half_amount * halfIntervals;
							logger.info("fullamount: " + fullamount);
							logger.info("half_amount: " + half_amount);

							intial = fullamount + half_amount;
							logger.info(intial);

						}

						if (fullIntervals > 0 && halfIntervals == 0) {
							logger.info("apply full both");

							intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));

							intial = intial * fullIntervals;
							logger.info(intial);

						}

						if (fullIntervals == 0) {

							logger.info("adultDifference: " + adultDifference);
							logger.info("additionalAdultCharges: " + additionalAdultCharges);

							logger.info("childDifference: " + childDifference);
							logger.info("additionalChildCharges: " + additionalChildCharges);

							intial = base_amount + (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));

							logger.info(intial);

						}
					} else {
						if (fullIntervals == 0) {
							intial = base_amount;

						}
						if (fullIntervals > 0 && halfIntervals > 0) {

							double full = fullIntervals * base_amount;
							logger.info(full);

							double half = halfIntervals * Double.parseDouble(proRatePerNight);
							logger.info(half);
							intial = full + half;
							logger.info(intial);

						}

						if (fullIntervals > 0 && halfIntervals == 0) {
							intial = fullIntervals * base_amount;
							logger.info(intial);

						}

					}

				}

			}
			String getValue = Utility.RemoveDollarandSpaces(driver, totalRateValueElement.getText()).trim();
			rateApplied = String.format("%.2f", intial);
			logger.info("convert: " + getValue);
			logger.info("rateApplied: " + rateApplied);

			testSteps.add("Expected rates: " + rateApplied);
			testSteps.add("Found: " + intial);
			if (getValue.equals(rateApplied)) {
				isRoomChargesEqual = "yes";
				testSteps.add("Verified interval rate value is matching");

			} else {
				isRoomChargesEqual = "no";
				testSteps.add("Verified interval rate value is not matching");

			}

			averagePerNight = String.format("%.1f",
					(Double.parseDouble(Utility.RemoveDollarandSpaces(driver, totalRateValueElement.getText())))
							/ numberofDays);

			assertEquals(
					String.format("%.1f",
							(Double.parseDouble(Utility.RemoveDollarandSpaces(driver,
									averagePerNightElement.getText().split(" ")[3])))),
					averagePerNight, "Failed: rate didn't match");

			testSteps.add("Verified total Amount:" + baseAmount + " Avg Per Night:" + averagePerNight);
			logger.info("Verified total Amount:" + baseAmount + " Avg Per Night:" + averagePerNight);
			Utility.clickThroughJavaScript(driver, roomClassExpandElement);
			String requiredDateFormat = "MMM dd yyyy";

			// getting amounts for each date
			Double amountInDouble = 0.0;
			int fullIntervalCount = 0;
			String timeZone = "US/Eastern";
			int temp = 0;
			int incrementCounter = 0;
			int rateListLength = driver
					.findElements(By.xpath("//td[contains(@data-bind, 'showInnroadCurrency: { value:')]")).size();

			ArrayList<Double> getRates = intervalRateCalculation(dateFormat, checkInDate, checkOutDate, adults, child,
					roomClassExpand, intervalLength, baseAmount, adultCapacity, personCapacity,
					isAdditionalChargesApplied, maxAdult, maxPerson, additionalAdultCharges, additionalChildCharges,
					isProRateApplied, proRatePerNight, proRateAdditionalAdultCharges, proRateAdditionalChildCharges,
					testSteps);
			System.out.println(getRates);
			for (int i = 0; i < rateListLength; i++) {

				String getDate = ESTTimeZone.getDateonBasedOfDate(dateFormat, checkInDate, incrementCounter, timeZone);
				dates.add(getDate);

				String dateInRequiredFormat = ESTTimeZone.reformatDate(getDate, dateFormat, requiredDateFormat);
				logger.info(dateInRequiredFormat);
				Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.RATEPLAN_NAME_RATE_DETAIL_RES_V2), driver);
				String startDate = reservationElement.DATE_RATE_DETAIL_RES_V2.get(i).getText();
				String rateName = reservationElement.RATEPLAN_NAME_RATE_DETAIL_RES_V2.get(i).getText();

				assertEquals(startDate, dateInRequiredFormat, "Failed: interval date is mismatching!");
				assertEquals(rateName, folioName, "Failed: rate name is mismatching!");

				String getAmount = reservationElement.RATEVALUE_RATE_DETAIL_RES_V2.get(i).getText();
				amountInDouble = Double.parseDouble(Utility.RemoveDollarandSpaces(driver, getAmount));

				logger.info("Amount for date(" + dateInRequiredFormat + ") is " + getAmount);
				testSteps.add("Amount for date(" + dateInRequiredFormat + ") is " + getAmount);
				temp++;
				double total = 0.00;

				if (isRoomChargesEqual.equalsIgnoreCase("yes")) {
					if (temp > fullIntervals) {

						incrementCounter = interval * fullIntervals + 1;
						logger.info(getRates.get(0));
						logger.info(getRates.get(1));

						if (isProRateApplied) {
							if (halfIntervals == 0) {
								total = getRates.get(0);
							} else {
								total = getRates.get(1);
							}
						} else {
							total = getRates.get(0);
						}
						logger.info("total: " + total);
						assertEquals(amountInDouble, total, "Failed: interval amount is mismatching!");

					}

					else {

						incrementCounter = incrementCounter + Integer.parseInt(intervalLength);
						logger.info(getRates.get(0));
						logger.info(getRates.get(1));
						total = getRates.get(0);
						logger.info("total: " + total);
						assertEquals(amountInDouble, total, "Failed: interval amount is mismatching!");

					}
				} else {
					if (temp > fullIntervals) {

						incrementCounter = interval * fullIntervals + 1;
						logger.info(getRates.get(0));
						logger.info(getRates.get(1));

						if (isProRateApplied) {
							if (halfIntervals == 0) {
								total = getRates.get(0);
							} else {
								total = getRates.get(1);
							}
						} else {
							total = getRates.get(0);
						}
						logger.info("total: " + total);
					}

					else {

						incrementCounter = incrementCounter + Integer.parseInt(intervalLength);
						logger.info(getRates.get(0));
						logger.info(getRates.get(1));
						total = getRates.get(0);
						logger.info("total: " + total);

					}

				}

				// rateList.add(rateApplied);
				String amount = String.format("%.2f", total);
				rateList.add("" + total);
			}

		}

		ArrayList<ArrayList<String>> ratesAlongWithDate = new ArrayList<>();
		ratesAlongWithDate.add(rateList);
		ratesAlongWithDate.add(dates);
		ArrayList<String> roomCharges = new ArrayList<>();

		roomCharges.add(isRoomChargesEqual);
		ratesAlongWithDate.add(roomCharges);
		return ratesAlongWithDate;

	}

	public ArrayList<Double> intervalRateCalculation(String dateFormat, String checkInDate, String checkOutDate,
			String adults, String child, String roomClass, String intervalLength, String baseAmount,
			String adultCapacity, String personCapacity, boolean isAdditionalChargesApplied, String maxAdult,
			String maxPerson, String additionalAdultCharges, String additionalChildCharges, boolean isProRateApplied,
			String proRatePerNight, String proRateAdditionalAdultCharges, String proRateAdditionalChildCharges,
			ArrayList<String> testSteps) throws InterruptedException, NumberFormatException, ParseException {

//		Elements_CPReservation res = new Elements_CPReservation(driver);
		ArrayList<Double> rateList = new ArrayList<>();

		double pro_rate = Double.parseDouble(proRatePerNight);
		double base_amount = Double.parseDouble(baseAmount);

		int numberofDays = Integer
				.parseInt(Utility.differenceBetweenDates(Utility.parseDate(checkInDate, dateFormat, "dd/MM/yyyy"),
						Utility.parseDate(checkOutDate, dateFormat, "dd/MM/yyyy")));

		int fullIntervals = 1;
		int halfIntervals = 0;
		if (numberofDays >= (Integer.parseInt(intervalLength))) {
			fullIntervals = numberofDays / Integer.parseInt(intervalLength);
			halfIntervals = numberofDays % Integer.parseInt(intervalLength);
		}

		testSteps.add("Interval lenght: " + intervalLength);
		testSteps.add("Number of nights: " + numberofDays);
		testSteps.add("Full interval: " + fullIntervals);
		testSteps.add("Half interval: " + halfIntervals);

		int interval = Integer.parseInt(intervalLength);

		double intial = 0;
		double fullIntervalAmount = 0.00;
		double perNightAmount = 0.00;

		int adultDifference = 0;
		int childDifference = 0;
		int personDifference = 0;

		if (Integer.parseInt(adults) > Integer.parseInt(maxAdult))
			adultDifference = Math.abs(Integer.parseInt(maxAdult) - Integer.parseInt(adults));
		int child_adults = Integer.parseInt(child) + Integer.parseInt(adults);
		child_adults = child_adults - adultDifference;
		int count = 0;
		boolean isTrue = false;
		int intMaxPerson = Integer.parseInt(maxPerson);
		if (child_adults > intMaxPerson) {
			System.out.println("child is also diff");
			for (int i = 1; i < child_adults; i++) {
				child_adults = child_adults - 1;
				if (child_adults == intMaxPerson) {
					count = i;
					isTrue = true;
					break;
				}
			}

		}

		if (isTrue) {
			childDifference = count;
		}
		System.out.println("Additonal Adult:" + adultDifference);
		System.out.println("Additonal Child:" + childDifference);

		if ((Integer.parseInt(adults) + Integer.parseInt(child)) <= Integer.parseInt(personCapacity)) {
			if (!isProRateApplied && !isAdditionalChargesApplied) {

				if (fullIntervals == 0) {

					fullIntervalAmount = base_amount;
				}

				else if (fullIntervals > 0 && halfIntervals == 0) {

					fullIntervalAmount = base_amount;

				} else {
					fullIntervalAmount = base_amount;
				}

			}
			if (isProRateApplied && !isAdditionalChargesApplied) {

				if (fullIntervals == 0) {

					fullIntervalAmount = base_amount;

				} else if (fullIntervals > 0 && halfIntervals == 0) {

					fullIntervalAmount = base_amount;
				}

				else {

					fullIntervalAmount = base_amount;
					perNightAmount = Double.parseDouble(proRatePerNight);
				}
			}

			// if Additional Charges Applied ANd ProRate False
			if (isAdditionalChargesApplied && !isProRateApplied) {

				if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)) {

					if (fullIntervals == 0) {

						fullIntervalAmount = base_amount
								+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));
					}

					else if (fullIntervals > 0 && halfIntervals == 0) {

						fullIntervalAmount = base_amount
								+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));

					} else {

						fullIntervalAmount = base_amount
								+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
								+ (childDifference * (Double.parseDouble(additionalChildCharges)));

					}
					logger.info("expected rate: " + intial);

				}

			}
			if (isAdditionalChargesApplied && isProRateApplied) {

				if (Integer.parseInt(adults) <= Integer.parseInt(adultCapacity)
						|| numberofDays < (Integer.parseInt(intervalLength))) {

					if (adultDifference > 0 || childDifference > 0) {

						if (fullIntervals > 0 && halfIntervals > 0) {
							fullIntervalAmount = base_amount
									+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));

							perNightAmount = Double.parseDouble(proRatePerNight)
									+ (adultDifference * Double.parseDouble(proRateAdditionalAdultCharges))
									+ (childDifference * (Double.parseDouble(proRateAdditionalChildCharges)));

						}

						if (fullIntervals > 0 && halfIntervals == 0) {

							fullIntervalAmount = base_amount
									+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));
						}

						if (fullIntervals == 0) {

							fullIntervalAmount = base_amount
									+ (adultDifference * (Double.parseDouble(additionalAdultCharges)))
									+ (childDifference * (Double.parseDouble(additionalChildCharges)));

						}
					} else {
						if (fullIntervals == 0) {
							fullIntervalAmount = base_amount;
						}
						if (fullIntervals > 0 && halfIntervals > 0) {

							fullIntervalAmount = base_amount;
							perNightAmount = Double.parseDouble(proRatePerNight);
						}

						if (fullIntervals > 0 && halfIntervals == 0) {
							// fullIntervalAmount = fullIntervals * base_amount;
							fullIntervalAmount = base_amount;
						}

					}

				}

			}
		}
		rateList.add(fullIntervalAmount);
		rateList.add(perNightAmount);
		return rateList;
	}

	public void clickChangeStayDetails(WebDriver driver, int guest) throws InterruptedException {
		String threeDotsXpath = "(" + OR_ReservationV2.STAY_INFO_THREE_DOTS + ")[" + guest + "]";
		String changeStayDatesXpath = "(" + OR_ReservationV2.CHANGE_STAY_DETAILS + ")[" + guest + "]";
		Wait.waitForElementToBeClickable(By.xpath(threeDotsXpath), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(threeDotsXpath)), driver);
		driver.findElement(By.xpath(threeDotsXpath)).click();
		Wait.waitForElementToBeClickable(By.xpath(changeStayDatesXpath), driver);
		driver.findElement(By.xpath(changeStayDatesXpath)).click();

	}

	public void waitForLoadingSymbol(WebDriver driver) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		int counter = 0;
		while (true) {
			if (!Utility.isElementDisplay(res.reservationV2Loading)) {
				break;
			} else if (counter == 40) {
				break;
			} else {
				counter++;
				Wait.wait2Second();
			}
		}
		logger.info("Waited to loading symbol");

	}

	public String getRoomCharge_TripSummary(WebDriver driver) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		return Utility.convertDollarToNormalAmount(driver, res.TripSummary_RoomCharge.getText());

	}

	public ArrayList<String> getRoomBalances(WebDriver driver) {
		ArrayList<String> roomBalances = new ArrayList<>();
		String guestBalance = "(//span[contains(@data-bind,'value: roomTotal')])";
		List<WebElement> element = driver.findElements(By.xpath(guestBalance));
		for (int i = 1; i <= element.size(); i++) {

			String guestRoomBalance = "(//span[contains(@data-bind,'value: roomTotal')])[" + i + "]";
			WebElement element1 = driver.findElement(By.xpath(guestRoomBalance));
			roomBalances.add(Utility.convertDollarToNormalAmount(driver, element1.getText()));
		}

		return roomBalances;

	}

	public String getGuestName(WebDriver driver) {
		String guestName = "(//span[@class='ir-multiroom-stayInfo-guestname ir-textTrim'])[2]";
		WebElement element = driver.findElement(By.xpath(guestName));

		return element.getText();

	}

	public ArrayList<String> changingReservationStatus(WebDriver driver, String itemStatus) throws Exception {
		ArrayList<String> test_steps = new ArrayList<>();
		Navigation nav = new Navigation();
		switch (itemStatus) {
		case "Confirmed":
			changeReservationStatusFromStatusDropdown(driver, test_steps, itemStatus);
			break;
		case "Guaranteed":
			changeReservationStatusFromStatusDropdown(driver, test_steps, itemStatus);
			break;
		case "On Hold":
			changeReservationStatusFromStatusDropdown(driver, test_steps, itemStatus);
			break;
		case "Cancelled":
			cancelReservation(driver, test_steps, true, "Cancel");
			break;
		case "No Show":
			noShowReservation(driver, test_steps, true);
			break;

		}
		return test_steps;
	}

	public HistoryInfo getHistoryInfo(WebDriver driver, int rowNo) throws InterruptedException {
		HistoryInfo hist;
		Wait.wait5Second();
		hist = new HistoryInfo(
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'categoryId')])[" + rowNo + "]")),
						false, ""),
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'formatDate')])[" + rowNo + "]")),
						false, ""),
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'formatTime')])[" + rowNo + "]")),
						false, ""),
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'firstName')])[" + rowNo + "]")),
						false, ""),
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'lastName')])[" + rowNo + "]")),
						false, ""),
				Utility.getELementText(
						driver.findElement(By.xpath(
								"(//history-info//table//td/span[contains(@data-bind,'description')])[" + rowNo + "]")),
						false, ""));
		return hist;
	}

	public String getRoomChargesTotalForMRB(WebDriver driver) throws InterruptedException {
		String roomChargeXpath = "(//div[@title='Room Charges']//following-sibling::div)[1]";
		Wait.waitForElementToBeVisibile(By.xpath(roomChargeXpath), driver);
		Wait.wait5Second();
		String roomCharges = driver.findElement(By.xpath(roomChargeXpath)).getText();
		roomCharges = roomCharges.replace("$", "");
		roomCharges = roomCharges.replaceAll(" ", "").replaceAll(",", "");
		return roomCharges;
	}

	public void verifyHistoryInfo(HistoryInfo acctualHistoryInfo, String expectedCategoryId, boolean verifyCategoryId,
			boolean continueExeCategoryId, String expectedDate, boolean verifyDate, boolean continueExeDate,
			String expectedTime, boolean verifyTime, boolean continueExeTime, String expectedUserName,
			boolean verifyUserName, boolean continueExeUserName, String expectedDescription, boolean verifyDescription,
			boolean isContainsDescription, boolean continueExeDescription, ArrayList<String> test_steps) {

		Utility.customAssert(acctualHistoryInfo.getCATEGORY(), expectedCategoryId.toUpperCase(), verifyCategoryId,
				"Successfully Verified History Info Category : " + expectedCategoryId,
				"Failed To Verify History Info Category", continueExeCategoryId, test_steps);

		Utility.customAssert(acctualHistoryInfo.getDATE(), expectedDate, verifyDate,
				"Successfully Verified History Info Date : " + expectedDate, "Failed To Verify History Info Date",
				continueExeDate, test_steps);

		Utility.customAssert(acctualHistoryInfo.getTIME(), expectedTime, verifyTime,
				"Successfully Verified History Info Time : " + expectedTime, "Failed To Verify History Info Time",
				continueExeTime, test_steps);

		Utility.customAssert(acctualHistoryInfo.getUSERNAME(), expectedUserName, verifyUserName,
				"Successfully Verified History Info UserName : " + expectedUserName,
				"Failed To Verify History Info UserName", continueExeUserName, test_steps);

		Utility.customAssert(acctualHistoryInfo.getDESCRIPTION(), expectedDescription, verifyDescription,
				isContainsDescription, "Successfully Verified History Info Description : " + expectedDescription,
				"Failed To Verify History Info Description", continueExeDescription, test_steps);

	}

	public void changeReservationStatus(WebDriver driver, ArrayList<String> test_steps, String status)
			throws Exception {
		// status = status.toUpperCase();
		switch (status) {
		case "CheckIn":
			checkInReservation(driver, test_steps);
			break;
		case "PriaryCheckIn":
			checkIn_MrbPrimary(driver, test_steps);
			break;
		case "SecondaryCheckIn":
			checkIn_MrbSecondary(driver, test_steps);
			break;
		case "CheckOut":
			checkOutReservation(driver, test_steps, "No", "No", "Test");
			break;
		case "PrimaryCheckOut":
			checkOutMRBPrimary(driver, test_steps);
			break;
		case "SecondaryCheckOut":
			checkOutMRBSecondary(driver, test_steps);
			break;
		case "Cancel":
			cancelReservation(driver, test_steps, false, "Cancel");
			break;
		case "SecondaryCancel":
			cancel_MrbSecondary(driver, test_steps, false, "Cancel");
			break;
		case "No Show":
		case "NoShow":

			break;
		case "Confirmed":
		case "Guaranteed":
		case "On Hold":
		case "Reserved":			
		case "OnHold":
			changeReservationStatusFromStatusDropdown(driver, test_steps, status);
			break;

		default:
			break;
		}

	}

	public String getRoomChargesTotal(WebDriver driver) throws InterruptedException {
		String roomChargeXpath = "//span[text()='TOTAL']//following-sibling::span";
		Wait.waitForElementToBeVisibile(By.xpath(roomChargeXpath), driver);
		Wait.waitForElementToBeClickable(By.xpath(roomChargeXpath), driver);
		Wait.wait5Second();
		String roomCharges = driver.findElement(By.xpath(roomChargeXpath)).getText();
		roomCharges = roomCharges.replace("$", "");
		roomCharges = roomCharges.replaceAll(" ", "").replaceAll(",", "");
		return roomCharges;
	}

	public ArrayList<String> enter_PaymentDetails(WebDriver driver, ArrayList<String> test_steps, String PaymentType,
			String CardNumber, String NameOnCard, String CardExpDate) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
		try {
			res.CP_NewReservation_PaymentMethod.click();
		} catch (Exception e) {
			driver.findElement(By.xpath(OR_Reservation.CP_NewReservation_PaymentMethod1)).click();
		}

		if (PaymentType.equalsIgnoreCase("Swipe")) {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'MC')]";
			Wait.WaitForElement(driver, paymentMethod);
			driver.findElement(By.xpath(paymentMethod)).click();
			res.CP_NewReservation_Swipe.click();
			test_steps.add("Clicking in Swipe");
			logger.info("Clicking in Swipe");
			Wait.wait1Second();
			String CC = "(//label[text()='Credit Card Number']/preceding-sibling::input)[2]";
			Wait.WaitForElement(driver, CC);
			driver.findElement(By.xpath(CC)).sendKeys(CardNumber);
			test_steps.add("Enter CardNumber : " + CardNumber);
			logger.info("Enter CardNumber : " + CardNumber);
			res.CP_NewReservation_CardNumber.sendKeys(Keys.TAB);
			Wait.wait2Second();
		} else {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
					+ PaymentType.trim() + "')]";
			Wait.WaitForElement(driver, paymentMethod);
			// driver.findElement(By.xpath(paymentMethod)).click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath(paymentMethod)));
			test_steps.add("Selected PaymentType : " + PaymentType);
			logger.info("Selected PaymentType : " + PaymentType);
			if (PaymentType.trim().equalsIgnoreCase("MC") || PaymentType.trim().equalsIgnoreCase("Visa")
					|| PaymentType.trim().equalsIgnoreCase("Amex") || PaymentType.trim().equalsIgnoreCase("Discover")) {
				res.CP_NewReservation_CardNumber.clear();
				res.CP_NewReservation_CardNumber.sendKeys(CardNumber);
				test_steps.add("Enter CardNumber : " + CardNumber);
				logger.info("Enter CardNumber : " + CardNumber);
				res.CP_NewReservation_NameOnCard.clear();
				res.CP_NewReservation_NameOnCard.sendKeys(NameOnCard);
				test_steps.add("Enter Name On Card : " + NameOnCard);
				logger.info("Enter Name On Card : " + NameOnCard);
				String path = "//label[text()='Exp Date']/preceding-sibling::input|(//label[text()='Exp Date']/preceding-sibling::input)[2]";
				driver.findElement(By.xpath(path)).clear();
				driver.findElement(By.xpath(path)).sendKeys(CardExpDate);
				test_steps.add("Enter Card ExpDate : " + CardExpDate);
				logger.info("Enter Card ExpDate : " + CardExpDate);
			}
		}
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_CreateGuestProfile);
		if (!driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div//input"))
				.isSelected()) {
			driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div/label"))
					.click();
		}
		return test_steps;
	}

	public void enter_MarketSegmentDetails(WebDriver driver, ArrayList<String> test_steps, String TravelAgent,
			String MarketSegment, String Referral, boolean useTravelAgentInfo) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_TravelAgent);

		if (!(TravelAgent.equalsIgnoreCase("") || TravelAgent.isEmpty())) {
			res.CP_NewReservation_TravelAgent.sendKeys(TravelAgent);
			Wait.wait2Second();
			String account = "//b[contains(text(),'" + TravelAgent.trim() + "')]/../../..";
			Wait.WaitForElement(driver, account);
			driver.findElement(By.xpath(account)).click();
			if (useTravelAgentInfo) {
				String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'Yes')]";
				Wait.WaitForElement(driver, dataYes);
				driver.findElement(By.xpath(dataYes)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				Wait.wait5Second();
			} else {
				String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'No')]";
				Wait.WaitForElement(driver, dataYes);
				driver.findElement(By.xpath(dataYes)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				Wait.wait5Second();
			}

		}

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Referral);
		Wait.wait3Second();
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.CP_NewReservation_Referral), driver);

		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.CP_NewReservation_Referral), driver);
		Utility.ScrollToElement(res.CP_NewReservation_Referral, driver);

		res.CP_NewReservation_Referral.click();
		// Wait.wait1Second();

		String ref = "//label[text()='Referral']/preceding-sibling::div//ul/li//span[contains(text(),'"
				+ Referral.trim() + "')]";

		logger.info(ref);
		Wait.waitForElementToBeClickable(By.xpath(ref), 10, driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(ref)), driver);

		Utility.app_logs.info(ref);
		// Wait.WaitForElement(driver, ref);

		driver.findElement(By.xpath(ref)).click();
		test_steps.add("Selected Referral as : " + Referral);
		logger.info("Selected Referral as : " + Referral);

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Market);
		Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
		Wait.wait2Second();
		res.CP_NewReservation_Market.click();
		String market = "//label[text()='Market']/preceding-sibling::div//ul/li//span[contains(text(),'"
				+ MarketSegment.trim() + "')]";
		Wait.WaitForElement(driver, market);

		driver.findElement(By.xpath(market)).click();
		test_steps.add("Selected MarketSegment as : " + MarketSegment);
		logger.info("Selected MarketSegment as : " + MarketSegment);
	}

	// This method is to create Single room Reservation
	public String singleReservation(WebDriver driver, String CheckInDate, String CheckOutDate, String Adults,
			String Children, String Rateplan, String RoomClass, String salutation, String guestFirstName,
			String guestLastName, String PaymentType, String CardNumber, String NameOnCard, String marketSegment,
			String referral, ArrayList<String> test_steps) throws Exception {

		CPReservationPage reservationPage = new CPReservationPage();
		reservationPage.click_NewReservation(driver, test_steps);
		reservationPage.select_CheckInDate(driver, test_steps, CheckInDate);
		reservationPage.select_CheckoutDate(driver, test_steps, CheckOutDate);
		reservationPage.enter_Adults(driver, test_steps, Adults);
		reservationPage.enter_Children(driver, test_steps, Children);
		reservationPage.select_Rateplan(driver, test_steps, Rateplan, "");
		reservationPage.clickOnFindRooms(driver, test_steps);
		ArrayList<String> rooms = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass);
		selectRoomToReserve(driver, test_steps, RoomClass, rooms.get(0));
		reservationPage.clickNext(driver, test_steps);
		reservationPage.enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName);
		reservationPage.uncheck_CreateGuestProfile(driver, test_steps, "No");
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		Utility.expiryDateReservationV2 = yearDate;
		enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, yearDate);
		enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral, true);
		reservationPage.clickBookNow(driver, test_steps);
		Wait.wait10Second();
		String reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, test_steps);
		logger.info("Reservation Number: " + reservationNumber);
		test_steps.add("<b>Reservation Number: " + reservationNumber);
		reservationPage.clickCloseReservationSavePopup(driver, test_steps);
		logger.info("Reservation created Successfully");
		test_steps.add("Reservation created Successfully");
		return reservationNumber;

	}

	public void displayVoidItems(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		clickOnFolioSettingBtn(driver, test_steps);

		String displayVoidFolioItem = "//span[contains(text(),'Display Voided Items')]/preceding::label[1]";
		Wait.WaitForElement(driver, displayVoidFolioItem);
		driver.findElement(By.xpath(displayVoidFolioItem)).click();
		String saveButton = "(//button[contains(@data-bind,'isSaveEnabled')])[2]";
		Wait.WaitForElement(driver, saveButton);
		driver.findElement(By.xpath(saveButton)).click();

	}

	public void clickOnFolioSettingBtn(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String folioSetting = "//button[contains(@data-bind,'FolioSettings')]/img";
		Wait.WaitForElement(driver, folioSetting);
		driver.findElement(By.xpath(folioSetting)).click();
		String chargeRouting = "//div[contains(@class,'col-md-12')]/h4[contains(text(),'Charge Routing')]";
		Wait.WaitForElement(driver, chargeRouting);

	}

	public enum HistoryTable {
		CATEGORY, DATE, TIME, USER, DESCRIPTION
	}

	public String primary = null;

	public void Add_PrimaryRoom(WebDriver driver, ArrayList test_steps, ArrayList al) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.wait3Second();
		String primaryArrow = "//span[text()='Primary Room']/../../following-sibling::div//button";
		Wait.WaitForElement(driver, primaryArrow);
		Wait.waitForElementToBeClickable(By.xpath(primaryArrow), driver);
		try {
			driver.findElement(By.xpath(primaryArrow)).click();
		} catch (Exception e) {
			Utility.clickThroughAction(driver, driver.findElement(By.xpath(primaryArrow)));
		}
		Wait.wait3Second();
		String primaryRoom = "//span[text()='Primary Room']/../../following-sibling::div//li[2]//span";
		primary = driver.findElement(By.xpath(primaryRoom)).getText();
		Wait.WaitForElement(driver, primaryRoom);
		driver.findElement(By.xpath(primaryRoom)).click();
		test_steps.add("Selected Primary Room as : " + primary);
		logger.info("Selected Primary Room as : " + primary);
		al.add(primary);

	}

	public String additionalRoomNo = null;

	public void Add_AdditionalRoom(WebDriver driver, ArrayList test_steps, ArrayList al) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String additional = "(//span[text()='Additional Room'])";
		int count = driver.findElements(By.xpath(additional)).size();
		for (int i = 1; i <= count; i++) {
			String additionalArrow = "(//span[text()='Additional Room'])[" + i
					+ "]/../../following-sibling::div//button";
			Wait.WaitForElement(driver, additionalArrow);
			driver.findElement(By.xpath(additionalArrow)).click();
			String additionalRoom = "((//span[text()='Additional Room'])[" + i + "]/../../following-sibling::div//li["
					+ (i + 1) + "]//span)[1]";
			/*
			 * String additionalRoom = "((//span[text()='Additional Room'])[" + i +
			 * "]/../../following-sibling::div//li[" + (i + 2) + "]//span)[1]";
			 */
			additionalRoomNo = driver.findElement(By.xpath(additionalRoom)).getText();
			Wait.WaitForElement(driver, additionalRoom);
			driver.findElement(By.xpath(additionalRoom)).click();
			test_steps.add("Selected Addtinal  Room as : " + additionalRoomNo);
			logger.info("Selected Addtinal Room as : " + additionalRoomNo);
			al.add(additionalRoomNo);
		}
	}

	public void enter_GuestDetailsForMRB(WebDriver driver, ArrayList test_steps, String Salutation,
			String GuestFirstName, String GuestLastName, String Email, String PhoneNumber, String AltenativePhone,
			String IsSplit, String IsAddMoreGuestInfo) throws ParseException {

		Elements_CPReservation res = new Elements_CPReservation(driver);

		String[] salu = Salutation.split("\\|");
		String[] guestFName = GuestFirstName.split("\\|");
		String[] guestLName = GuestLastName.split("\\|");
		String[] mail = Email.split("\\|");
		String[] phone = PhoneNumber.split("\\|");

		int size = salu.length;

		for (int i = 1; i <= size; i++) {
			if (i == 1) {
				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
				res.CP_NewReservation_GuestSalutation.click();
				String sal = "//span[contains(text(),'" + salu[i - 1] + "')]/../..";
				Wait.WaitForElement(driver, sal);
				driver.findElement(By.xpath(sal)).click();

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestFirstName);
				res.CP_NewReservation_GuestFirstName.clear();

				Random random = new Random();
				int randomNumber = random.nextInt(900);
				logger.info("randomNumber : " + randomNumber);

				String FirstName = guestFName[i - 1];
				// String FirstName = guestFName[i - 1] + randomNumber;
				Utility.guestFirstName = FirstName;
				res.CP_NewReservation_GuestFirstName.sendKeys(FirstName);
				test_steps.add("Guest First Name : " + FirstName);
				logger.info("Guest First Name : " + FirstName);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestLastName);
				res.CP_NewReservation_GuestLastName.clear();

				// String LastName = guestLName[i - 1] + randomNumber;
				String LastName = guestLName[i - 1];
				Utility.guestLastName = LastName;
				res.CP_NewReservation_GuestLastName.sendKeys(LastName);
				test_steps.add("Guest Last Name : " + LastName);
				logger.info("Guest Last Name : " + LastName);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Phone);
				res.CP_NewReservation_Phone.clear();
				res.CP_NewReservation_Phone.sendKeys(phone[i - 1]);
				test_steps.add("Enter Phone Number : " + phone[i - 1]);
				logger.info("Enter Phone Number : " + phone[i - 1]);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_AltenativePhone);
				res.CP_NewReservation_AltenativePhone.clear();
				res.CP_NewReservation_AltenativePhone.sendKeys(AltenativePhone);
				test_steps.add("Enter AltenativePhone Number : " + AltenativePhone);
				logger.info("Enter AltenativePhone Number : " + AltenativePhone);

				/*
				 * Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Email); <<<<<<<
				 * HEAD ======= res.CP_NewReservation_Email.clear();
				 * res.CP_NewReservation_Email.sendKeys(mail[i - 1]);
				 * test_steps.add("Enter Email : " + mail[i - 1]); logger.info("Enter Email : "
				 * + mail[i - 1]);
				 */
			} else {

				String additional = "(//span[text()='Additional Room'])";
				int count = driver.findElements(By.xpath(additional)).size();

				String saluta = "(//span[text()='Additional Room'])[" + (i - 1)
						+ "]/../../../../../..//span[text()='Select']/..";
				String slaut = "(//span[text()='Additional Room'])[" + (i - 1)
						+ "]/../../../../../..//span[text()='Select']/../following-sibling::div//li//span[text()='"
						+ salu[i - 1] + "']";
				Wait.WaitForElement(driver, saluta);
				driver.findElement(By.xpath(saluta)).click();
				logger.info(slaut);
				Wait.WaitForElement(driver, slaut);
				driver.findElement(By.xpath(slaut)).click();

				count = driver.findElements(By.xpath("//label[text()='Guest']/../input")).size();
				String fname = "(//label[text()='Guest']/../input)[" + (i - 1) + "]";
				Wait.WaitForElement(driver, fname);
				driver.findElement(By.xpath(fname)).clear();
				String FirstName = guestFName[i - 1];
				// String FirstName = guestFName[i - 1] + randomNumber;
				driver.findElement(By.xpath(fname)).sendKeys(FirstName);
				test_steps.add("Guest First Name : " + FirstName);
				logger.info("Guest First Name : " + FirstName);
				Utility.secondGuestFirstName = FirstName;
				count = driver.findElements(By.xpath(
						"(//span[text()='Additional Room'])/../../../../../..//label[text()='Last Name']/../input"))
						.size();
				String lname = "(//span[text()='Additional Room'])[" + (i - 1)
						+ "]/../../../../../..//label[text()='Last Name']/../input";
				Wait.WaitForElement(driver, lname);
				driver.findElement(By.xpath(lname)).clear();
				String lastName = guestLName[i - 1];
				driver.findElement(By.xpath(lname)).sendKeys(lastName);
				test_steps.add("Guest Last Name : " + lastName);
				logger.info("Guest Last Name : " + lastName);
				Utility.secondGuestLastName = lastName;
				count = driver.findElements(By.xpath("(//label[text()='Phone']/preceding-sibling::input)")).size();
				String ph = "(//span[text()='Additional Room'])[" + (i - 1)
						+ "]/../../../../../..//label[text()='Phone']/preceding-sibling::input";
				driver.findElement(By.xpath(ph)).clear();
				if (Utility.validateString(phone[0])) {
					driver.findElement(By.xpath(ph)).sendKeys(phone[i - 1]);
					test_steps.add("Enter Phone Number : " + phone[i - 1]);
					logger.info("Enter Phone Number : " + phone[i - 1]);
				}
				count = driver.findElements(By.xpath("(//label[text()='E-mail']/preceding-sibling::input)")).size();
				String email = "(//span[text()='Additional Room'])[" + (i - 1)
						+ "]/../../../../../..//label[text()='E-mail']/preceding-sibling::input";
				driver.findElement(By.xpath(email)).clear();

				if (Utility.validateString(mail[0])) {
					driver.findElement(By.xpath(email)).sendKeys(mail[i - 1]);
					test_steps.add("Enter Altenative Email : " + mail[i - 1]);
					logger.info("Enter Altenative Email : " + mail[i - 1]);

				}

			}
			if (!(i == size) && IsAddMoreGuestInfo.equalsIgnoreCase("Yes") && IsSplit.equalsIgnoreCase("Yes")) {
				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_AddMoreGuestInfo);
				res.CP_NewReservation_AddMoreGuestInfo.click();
				test_steps.add("Clicking on Add More Guest Info");
				logger.info("Clicking on Add More Guest Info");
			}
		}

	}

	public void enter_Account(WebDriver driver, ArrayList<String> test_steps, String Account, String AccountType)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		try {
			Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Account);
			String accountdisplay = "//label[contains(text(),'Account')]/..//i[starts-with(@class,'rightIcon demo-icon icon-pencil')]";
			WebElement ele = driver.findElement(By.xpath(accountdisplay));
			if (!ele.isDisplayed()) {
				if (Account.equalsIgnoreCase("") || Account.isEmpty()) {
					logger.info("No Account");
				} else {
					res.CP_NewReservation_Account.sendKeys(Account);
					test_steps.add("Enter Account : " + Account);
					logger.info("Enter Account : " + Account);
					Wait.wait2Second();
					String account = "//b[contains(text(),'" + Account.trim() + "')]/../../..";
					Wait.WaitForElement(driver, account);
					driver.findElement(By.xpath(account)).click();
					String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'Yes')]";
					Wait.WaitForElement(driver, dataYes);
					driver.findElement(By.xpath(dataYes)).click();
					test_steps.add(
							"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
					logger.info(
							"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
					Wait.wait2Second();
					String policyYes = "//div[contains(text(),'Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation?')]/following-sibling::div//button[contains(text(),'Yes')]";
					if (driver.findElements(By.xpath(policyYes)).size() > 0) {
						Wait.WaitForElement(driver, policyYes);
						driver.findElement(By.xpath(policyYes)).click();
						test_steps.add(
								"Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation? : Yes");
						logger.info(
								"Based on the account chosen, new policies are applicable. Would you like to apply these policies to the reservation? : Yes");
					}
					Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
					res.CP_NewReservation_PaymentMethod.click();
					String type = null;

					if (AccountType.contentEquals("Corp") || AccountType.contains("corp")
							|| AccountType.contains("Corp")) {
						type = "Account (Corp/Member)";
					}

					if (AccountType.contentEquals("Unit Owners") || AccountType.contains("unit")
							|| AccountType.contains("Unit")) {
						type = "Account (Unit Owner)";
					}

					if (AccountType.contentEquals("Group") || AccountType.contains("group")
							|| AccountType.contains("GROUP")) {
						type = "Account (Group)";
					}

					String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
							+ type + "')]";

					System.out.println(paymentMethod);
					Wait.WaitForElement(driver, paymentMethod);
					driver.findElement(By.xpath(paymentMethod)).click();
					String acc = "//label[text()='Account Name ']/preceding-sibling::input";
					Wait.WaitForElement(driver, acc);
					String accName = driver.findElement(By.xpath(acc)).getText();
					if (accName.contains(Account)) {
						test_steps.add("Account successfully associated : " + Account);
						logger.info("Account successfully associated : " + Account);
					}
				}
			} else {
				test_steps.add("Account Aleady exists");
				logger.info("Account Aleady exists");

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_PaymentMethod);
				res.CP_NewReservation_PaymentMethod.click();
				String type = null;

				if (AccountType.contentEquals("Corp") || AccountType.contains("corp") || AccountType.contains("Corp")) {
					type = "Account (Corp/Member)";
				}

				if (AccountType.contentEquals("Unit Owner") || AccountType.contains("Unit")
						|| AccountType.contains("unit owner")) {
					type = "Account (Unit Owner)";
				}

				if (AccountType.contentEquals("Group") || AccountType.contains("group")
						|| AccountType.contains("GROUP")) {
					type = "Account (Group)";
				}

				String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
						+ type + "')]";
				Wait.WaitForElement(driver, paymentMethod);
				driver.findElement(By.xpath(paymentMethod)).click();
				String acc = "(//label[text()='Account Name '])[1]/preceding-sibling::input";
				Wait.wait2Second();
				Wait.WaitForElement(driver, acc);
				String accName = driver.findElement(By.xpath(acc)).getText();
				if (accName.contains(Account)) {
					test_steps.add("Account successfully associated : " + Account);
					logger.info("Account successfully associated : " + Account);
				}
			}
		} catch (Exception e) {

			test_steps.add(" Account exist");
			logger.info(" Account exist");
		}
	}

	public void enter_Split_GuestName(WebDriver driver, ArrayList test_steps, String Salutation, String GuestFirstName,
			String GuestLastName, String Email, String PhoneNumber, String AltenativePhone) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String[] salu = Salutation.split("\\|");
		String[] guestFName = GuestFirstName.split("\\|");
		String[] guestLName = GuestLastName.split("\\|");
		String[] mail = Email.split("\\|");
		String[] phone = PhoneNumber.split("\\|");

		int size = salu.length;

		for (int i = 1; i <= size; i++) {
			if (i == 1) {
				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestSalutation);
				res.CP_NewReservation_GuestSalutation.click();
				String sal = "//span[contains(text(),'" + salu[i - 1] + "')]/../..";
				Wait.WaitForElement(driver, sal);
				driver.findElement(By.xpath(sal)).click();

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestFirstName);
				res.CP_NewReservation_GuestFirstName.sendKeys(guestFName[i - 1]);
				test_steps.add("Guest First Name : " + guestFName[i - 1]);
				logger.info("Guest First Name : " + guestFName[i - 1]);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_GuestLastName);
				res.CP_NewReservation_GuestLastName.sendKeys(guestLName[i - 1]);
				test_steps.add("Guest Last Name : " + guestLName[i - 1]);
				logger.info("Guest Last Name : " + guestLName[i - 1]);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Phone);
				res.CP_NewReservation_Phone.clear();
				res.CP_NewReservation_Phone.sendKeys(phone[i - 1]);
				test_steps.add("Enter Phone Number : " + phone[i - 1]);
				logger.info("Enter Phone Number : " + phone[i - 1]);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_AltenativePhone);
				res.CP_NewReservation_AltenativePhone.clear();
				res.CP_NewReservation_AltenativePhone.sendKeys(AltenativePhone);
				test_steps.add("Enter AltenativePhone Number : " + AltenativePhone);
				logger.info("Enter AltenativePhone Number : " + AltenativePhone);

				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Email);
				res.CP_NewReservation_Email.clear();
				res.CP_NewReservation_Email.sendKeys(mail[i - 1]);
				test_steps.add("Enter Email : " + mail[i - 1]);
				logger.info("Enter Email : " + mail[i - 1]);

			} else {
				int count = driver.findElements(By.xpath("//button[contains(@class,'salutationBtn')]")).size();
				String saluta = "(//button[contains(@class,'salutationBtn')])[" + count + "]";
				String slaut = "(//button[contains(@class,'salutationBtn')])[" + count
						+ "]/following-sibling::div//span[contains(text(),'" + salu[i - 1] + "')]";
				Wait.WaitForElement(driver, saluta);
				driver.findElement(By.xpath(saluta)).click();

				Wait.WaitForElement(driver, slaut);
				driver.findElement(By.xpath(slaut)).click();

				count = driver.findElements(By.xpath("//label[text()='Guest Name']/../input")).size();
				String fname = "(//label[text()='Guest Name']/../input)[" + count + "]";
				Wait.WaitForElement(driver, fname);
				driver.findElement(By.xpath(fname)).sendKeys(guestFName[i - 1]);
				test_steps.add("Guest First Name : " + guestFName[i - 1]);
				logger.info("Guest First Name : " + guestFName[i - 1]);

				count = driver.findElements(By.xpath("//label[text()='Last Name']/../input")).size();
				String lname = "(//label[text()='Last Name']/../input)[" + (count - 1) + "]";
				Wait.WaitForElement(driver, lname);
				driver.findElement(By.xpath(lname)).sendKeys(guestLName[i - 1]);
				test_steps.add("Guest Last Name : " + guestLName[i - 1]);
				logger.info("Guest Last Name : " + guestLName[i - 1]);

				count = driver.findElements(By.xpath("(//label[text()='Phone']/preceding-sibling::input)")).size();
				String ph = "(//label[text()='Phone']/preceding-sibling::input)[" + count + "]";
				driver.findElement(By.xpath(ph)).sendKeys(phone[i - 1]);
				test_steps.add("Enter Phone Number : " + phone[i - 1]);
				logger.info("Enter Phone Number : " + phone[i - 1]);

				count = driver.findElements(By.xpath("(//label[text()='E-mail']/preceding-sibling::input)")).size();
				String email = "(//label[text()='E-mail']/preceding-sibling::input)[" + count + "]";
				driver.findElement(By.xpath(email)).sendKeys(mail[i - 1]);
				test_steps.add("Enter Phone Number : " + mail[i - 1]);
				logger.info("Enter Phone Number : " + mail[i - 1]);
			}
			if (!(i == size)) {
				Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_AddMoreGuestInfo);
				res.CP_NewReservation_AddMoreGuestInfo.click();
				test_steps.add("Clicking on Add More Guest Info");
				logger.info("Clicking on Add More Guest Info");
				String warn = "//div[contains(text(),'The number of guest names added surpasses the number of guests booked in the room.')]/..//button[text()='Yes']";
				Wait.WaitForElement(driver, warn);
				driver.findElement(By.xpath(warn)).click();
			}
		}
	}

	public void enter_MRB_MailingAddressForMRB(WebDriver driver, ArrayList test_steps, String Salutation,
			String GuestFirstName, String GuestLastName, String PhoneNumber, String AltenativePhone, String Email,
			String Account, String AccountType, String Address1, String Address2, String Address3, String City,
			String Country, String State, String PostalCode, String IsGuesProfile, String IsAddMoreGuestInfo,
			String IsSplit, ArrayList al) throws InterruptedException, ParseException {
		if (!IsSplit.equalsIgnoreCase("Yes")) {
			Add_PrimaryRoom(driver, test_steps, al);
			Add_AdditionalRoom(driver, test_steps, al);
			enter_GuestDetailsForMRB(driver, test_steps, Salutation, GuestFirstName, GuestLastName, Email, PhoneNumber,
					AltenativePhone, IsSplit, IsAddMoreGuestInfo);
			enter_Address(driver, test_steps, Address1, Address2, Address3);
			enter_City(driver, test_steps, City);
			select_Country(driver, test_steps, Country);
			select_State(driver, test_steps, State);
			enter_PostalCode(driver, test_steps, PostalCode);
			uncheck_CreateGuestProfile(driver, test_steps, IsGuesProfile);
			enter_Account(driver, test_steps, Account, AccountType);
		}
		if (IsAddMoreGuestInfo.equalsIgnoreCase("Yes") && IsSplit.equalsIgnoreCase("Yes")) {
			enter_Split_GuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName, Email, PhoneNumber,
					AltenativePhone);
			enter_Address(driver, test_steps, Address1, Address2, Address3);
			enter_City(driver, test_steps, City);
			select_Country(driver, test_steps, Country);
			select_State(driver, test_steps, State);
			enter_PostalCode(driver, test_steps, PostalCode);
			uncheck_CreateGuestProfile(driver, test_steps, IsGuesProfile);
			enter_Account(driver, test_steps, Account, AccountType);
		} else if (IsSplit.equalsIgnoreCase("Yes")) {
			enter_GuestName(driver, test_steps, Salutation, GuestFirstName, GuestLastName);
			enter_Phone(driver, test_steps, PhoneNumber, AltenativePhone);
			enter_Email(driver, test_steps, Email);
			enter_Address(driver, test_steps, Address1, Address2, Address3);
			enter_City(driver, test_steps, City);
			select_Country(driver, test_steps, Country);
			select_State(driver, test_steps, State);
			enter_PostalCode(driver, test_steps, PostalCode);
			uncheck_CreateGuestProfile(driver, test_steps, IsGuesProfile);
			enter_Account(driver, test_steps, Account, AccountType);
		}
	}

	public void enter_MarketSegmentDetails(WebDriver driver, ArrayList<String> test_steps, String TravelAgent,
			String MarketSegment, String Referral) throws InterruptedException {
		enter_MarketSegmentDetails(driver, test_steps, TravelAgent, MarketSegment, Referral, true);
	}

	// This method is to create MRB room Reservation
	public String MRBReservation(WebDriver driver, String CheckInDate, String CheckOutDate, String Adults,
			String Children, String Rateplan, String RoomClass, String salutation, String guestFirstName,
			String guestLastName, String PaymentType, String CardNumber, String NameOnCard, String marketSegment,
			String referral, ArrayList<String> roomNumber, boolean closeReservation, ArrayList<String> test_steps)
			throws Exception {

		CPReservationPage reservationPage = new CPReservationPage();
		reservationPage.click_NewReservation(driver, test_steps);
		select_Dates(driver, test_steps, CheckInDate, CheckOutDate, Adults, Children, Rateplan, "", "");
		clickOnFindRooms(driver, test_steps);
		reservationPage.verifySpinerLoading(driver);
		ArrayList<String> rooms = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass.split("\\|")[0]);
		selectRoomToReserve(driver, test_steps, RoomClass.split("\\|")[0], rooms.get(0));
		rooms = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, RoomClass.split("\\|")[1]);
		selectRoomToReserve(driver, test_steps, RoomClass.split("\\|")[1], rooms.get(1));
		Wait.wait10Second();
		Utility.ScrollToUp(driver);
		Utility.ScrollToTillEndOfPage(driver);
		reservationPage.clickNext(driver, test_steps);
		enter_MRB_MailingAddressForMRB(driver, test_steps, salutation, guestFirstName, guestLastName, "", "", "", "",
				"", "", "", "", "", "", "", "", "No", "", "", roomNumber);
		String yearDate = Utility.getFutureMonthAndYearForMasterCard();
		Utility.expiryDate = yearDate;
		enter_PaymentDetails(driver, test_steps, PaymentType, CardNumber, NameOnCard, yearDate);
		enter_MarketSegmentDetails(driver, test_steps, "", "", referral);
		reservationPage.clickBookNow(driver, test_steps);
		String reservationNumber = get_ReservationConfirmationNumber(driver, test_steps);
		reservationPage.clickCloseReservationSavePopup(driver, test_steps);
		if (closeReservation) {
			reservationPage.closeReservationTab(driver);
			logger.info("Close Reservation Tab");
			test_steps.add("Close Reservation Tab");
		}
		return reservationNumber;

	}

	public void clickOnLogButton(WebDriver driver, ArrayList<String> test_steps) throws Exception {

		String str = "//a[contains(@data-bind,'PaymentProcessButtonClick')]/span[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'LOG')]";
		Wait.waitForElementToBeClickable(By.xpath(str), driver);
		Utility.ScrollToElement(driver.findElement(By.xpath(str)), driver);
		driver.findElement(By.xpath(str)).click();
		test_steps.add("Clicking on LOG Button");
		logger.info("Clicking on LOG Button");
	}

	public void reservationStatusPanelSelectStatus(WebDriver driver, String status, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation CPReservationPage = new Elements_CPReservation(driver);
		String path1 = "//div[contains(@class,'ir-statusChange')]//span[contains(@data-bind,'ReservationUpdate')]";
		Wait.WaitForElement(driver, path1);
		Utility.ScrollToElement(driver.findElement(By.xpath(path1)), driver);
		driver.findElement(By.xpath(path1)).click();
		test_steps.add("Click on Drop Down box of Reservation Status Panel");
		logger.info("Click on Drop Down box of Reservation Status Panel");
		String path = "//span[contains(@class,'dropdown-toggle open')]//ul[contains(@class,'dropdown-menu')]/li/a/span[contains(text(),'"
				+ status + "')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		WebElement element = driver.findElement(By.xpath(path));
		Utility.ScrollToElement(element, driver);
		String getStatus = Utility.getELementText(element, false, "");
		Utility.customAssert(getStatus.toUpperCase(), status.toUpperCase(), true,
				"Successfully Verified Status Option : " + status, "Failed to Verify Status Option : " + status, true,
				test_steps);
		element.click();
		test_steps.add("Select Status : <b>" + status + "</b>");
		logger.info("Select Status : " + status);
		// verifySpinerLoading(driver);
		Wait.wait10Second();
	}

	public void verifySpinerLoading(WebDriver driver) throws InterruptedException {
		Elements_CPReservation reservation = new Elements_CPReservation(driver);
		Wait.wait1Second();
		int count = 0;

		try {
			logger.info("in try");
			while (count < 20) {
				logger.info(count);
				if (!reservation.SpinerLoading.isDisplayed()) {
					break;
				}
				count = count + 1;
				Wait.wait2Second();
			}
		} catch (Exception e) {
			logger.info("in cathc");
		}
	}

	public void clickProceedToCancelPaymentButton(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		verifySpinerLoading(driver);
		String path = "//span[contains(text(),'PROCEED TO CANCELLATION PAYMENT')]";
		Wait.WaitForElement(driver, path);
		Utility.ScrollToElement(driver.findElement(By.xpath(path)), driver);
		driver.findElement(By.xpath(path)).click();
		test_steps.add("Clicking on '<b>PROCEED TO CANCELLATION PAYMENT</b>' Button");
		logger.info("Click 'PROCEED TO CANCELLATION PAYMENT' Button");
	}

	public void clickPayButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String path = "//a[contains(@data-bind,'PaymentProcessButtonClick')]/span[contains(text(),'Pay')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		Utility.scrollAndClick(driver, By.xpath(path));
		try {
			driver.findElement(By.xpath(path)).click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(path)));
		}

		test_steps.add("Clicking on Pay Button ");
		logger.info("Clicking on Pay Button ");
	}

	public void enterCancellationReasons(WebDriver driver, ArrayList<String> test_steps, String reason) {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		String path = "//textarea[contains(@data-bind,'cancellationInfo.cancelReason')]";
		Wait.WaitForElement(driver, path);
		Wait.waitForElementToBeVisibile(By.xpath(path), driver);
		Wait.waitForElementToBeClickable(By.xpath(path), driver);
		driver.findElement(By.xpath(path)).clear();
		driver.findElement(By.xpath(path)).sendKeys(reason);
		driver.findElement(By.xpath(path)).sendKeys(Keys.TAB);
		test_steps.add("Providing Cancellation Reason as : <b>" + reason + " </b>");
		logger.info("Providing Cancellation Reason as : " + reason);
	}

	public void clickCloseButtonOfSuccessModelPopup(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.wait5Second();
		Wait.WaitForElement(driver, OR_Reservation.NoShowClosePopupButton);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.NoShowClosePopupButton), driver, 10);
		// res.CP_Reservation_NoShowSuccessCloseButton.click();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", res.NoShowClosePopupButton);
		test_steps.add("Click Close Button ");
		logger.info("Click Close Button ");
		Wait.wait3Second();
	}

	public void select_Dates(WebDriver driver, ArrayList test_steps, String CheckInDate, String CheckoutDate,
			String Adults, String Children, String RatePlan, String PromoCode, String IsSplitRes)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		String loading = "(//div[@class='ir-loader-in'])[2]";
		int counter = 0;
		while (true) {
			Boolean load = Utility.isElementDisplayed(driver, By.xpath(loading));
			if (load) {
				if (counter == 40) {
					break;
				} else {
					counter++;
					Wait.wait2Second();
				}
				logger.info("Waited to loading symbol");
			} else {
				break;
			}
		}
		String[] checkin = CheckInDate.split("\\|");
		String[] checkout = CheckoutDate.split("\\|");
		String[] adu = Adults.split("\\|");
		String[] child = Children.split("\\|");
		String[] rate = RatePlan.split("\\|");
		String[] promoCode = PromoCode.split("\\|");
		Boolean flag = false;
		logger.info(CheckInDate);
		int size = checkin.length;
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_AddRoom);
		logger.info("MRB size : " + size);
		if (size > 1) {
			for (int i = 1; i <= size; i++) {
				String checkInCal = "(//label[text()='check In']/following-sibling::i)[" + i + "]";
				logger.info(checkInCal);
				Wait.WaitForElement(driver, checkInCal);
				driver.findElement(By.xpath(checkInCal)).click();
				logger.info("Check in: " + checkin[i - 1]);
				String monthYear = Utility.get_MonthYear(checkin[i - 1]);
				String day = Utility.getDay(checkin[i - 1]);
				logger.info(monthYear);
				String header = null, headerText = null, next = null, date;
				for (int k = 1; k <= 12; k++) {
					header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
					headerText = driver.findElement(By.xpath(header)).getText();
					if (headerText.equalsIgnoreCase(monthYear)) {
						date = "//td[contains(@class,'day') and text()='" + day + "']";
						Wait.WaitForElement(driver, date);
						int size1 = driver.findElements(By.xpath(date)).size();
						for (int l = 1; l <= size; l++) {
							date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + l + "]";
							String classText = driver.findElement(By.xpath(date)).getAttribute("class");
							if (!classText.contains("old")) {
								driver.findElement(By.xpath(date)).click();
								test_steps.add("Selecting checkin date : " + checkin[+(i - 1)]);
								logger.info("Selecting checkin date : " + checkin[(i - 1)]);
								break;
							}
						}
						break;
					} else {
						next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
						Wait.WaitForElement(driver, next);
						driver.findElement(By.xpath(next)).click();
						Wait.wait2Second();
					}
				}
				String checkOutCal = "(//label[text()='check Out']/following-sibling::i)[" + i + "]";
				logger.info(checkOutCal);
				Wait.WaitForElement(driver, checkOutCal);
				driver.findElement(By.xpath(checkOutCal)).click();
				monthYear = Utility.get_MonthYear(checkout[i - 1]);
				day = Utility.getDay(checkout[i - 1]);
				logger.info(monthYear);
				for (int k = 1; k <= 12; k++) {
					header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
					headerText = driver.findElement(By.xpath(header)).getText();
					if (headerText.equalsIgnoreCase(monthYear)) {
						date = "//td[contains(@class,'day') and text()='" + day + "']";
						Wait.WaitForElement(driver, date);
						int size1 = driver.findElements(By.xpath(date)).size();
						for (int l = 1; l <= size; l++) {
							date = "(//td[contains(@class,'day') and text()='" + day + "'])[" + l + "]";
							String classText = driver.findElement(By.xpath(date)).getAttribute("class");
							if (!classText.contains("old")) {
								driver.findElement(By.xpath(date)).click();
								test_steps.add("Selecting checkout date : " + checkout[(i - 1)]);
								logger.info("Selecting checkout date : " + checkout[(i - 1)]);
								break;
							}
						}
						break;
					} else {
						next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
						Wait.WaitForElement(driver, next);
						driver.findElement(By.xpath(next)).click();
						Wait.wait2Second();
					}
				}
				if (!IsSplitRes.equalsIgnoreCase("Yes")) {
					String adults = "(//label[text()='Adults']/preceding-sibling::input)[" + i + "]";
					Wait.WaitForElement(driver, adults);
					driver.findElement(By.xpath(adults)).clear();
					driver.findElement(By.xpath(adults)).sendKeys(adu[i - 1]);
					test_steps.add("Enter Number of Adults : " + adu[(i - 1)]);
					logger.info("Enter Number of Adults : " + adu[(i - 1)]);

					String children = "(//label[text()='Children']/preceding-sibling::input)[" + i + "]";
					Wait.WaitForElement(driver, children);
					driver.findElement(By.xpath(children)).clear();
					driver.findElement(By.xpath(children)).sendKeys(child[i - 1]);
					test_steps.add("Enter Number of Children : " + child[(i - 1)]);
					logger.info("Enter Number of Children : " + child[(i - 1)]);
					// res.CP_NewReservation_Rateplan.click();
					String ratebutton = "(//label[contains(text(),'Rate Plan / Promo')]//..//preceding-sibling::div/button)["
							+ i + "]";
					Wait.WaitForElement(driver, ratebutton);
					driver.findElement(By.xpath(ratebutton)).click();
					logger.info(
							"(//label[contains(text(),'Rate Plan / Promo')]//..//preceding-sibling::div//span[text()='"
									+ rate[1].trim() + "'])");
					int rateCount = driver.findElements(By.xpath(
							"(//label[contains(text(),'Rate Plan / Promo')]//..//preceding-sibling::div//span[text()='"
									+ rate[1].trim() + "'])"))
							.size();
					logger.info(rateCount);
					String rateLoc = null;
					if (rateCount == 1) {
						rateLoc = "(//label[contains(text(),'Rate Plan / Promo')]//..//preceding-sibling::div//span[text()='"
								+ rate[i - 1].trim() + "'])[" + i + "]";
					} else {
						rateLoc = "(//label[contains(text(),'Rate Plan / Promo')]//..//preceding-sibling::div//span[text()='"
								+ rate[i - 1].trim() + "'])[" + rateCount + "]";
					}
					logger.info(rateLoc);
					Wait.WaitForElement(driver, rateLoc);
					driver.findElement(By.xpath(rateLoc)).click();
					test_steps.add("Selecting the rateplan : " + rate[i - 1]);
					logger.info("Selecting the rateplan : " + rate[i - 1]);
					if (RatePlan.contains("Promo")) {
						String promo = "(//label[contains(text(),'Enter Code')]//..//preceding-sibling::input)[" + i
								+ "]";
						Wait.WaitForElement(driver, promo);
						res.CP_NewReservation_PromoCode.clear();
						res.CP_NewReservation_PromoCode.sendKeys(promoCode[i - 1]);
						test_steps.add("Enter promocode : " + promoCode[i]);
						logger.info("Enter promocode : " + promoCode[i]);
					}
				}
				if (!(i == size)) {
					clickOnAddARoomButton(driver, test_steps);
					logger.info("Clicking on add room");
					if (IsSplitRes.equalsIgnoreCase("Yes") && !flag) {
						res.CP_NewReservation_SplitReservation.click();
						test_steps.add("Clicking on split reservation");
						logger.info("Clicking on split reservation");
					}
					flag = true;
				}
			}
		} else {
			Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_CheckinCal);
			res.CP_NewReservation_CheckinCal.click();
			String monthYear = Utility.get_MonthYear(CheckInDate);
			String day = Utility.getDay(CheckInDate);
			logger.info(monthYear);
			String header = null, headerText = null, next = null, date;
			for (int i = 1; i <= 12; i++) {
				header = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[2]";
				headerText = driver.findElement(By.xpath(header)).getText();
				if (headerText.equalsIgnoreCase(monthYear)) {
					date = "//td[contains(@class,'day') and text()='" + day + "']";
					Wait.WaitForElement(driver, date);
					driver.findElement(By.xpath(date)).click();
					test_steps.add("Selecting checkin date : " + CheckInDate);
					logger.info("Selecting checkin date : " + CheckInDate);
					break;
				} else {
					next = "//table[@class='datepicker-table-condensed table-condensed']/thead/tr/th[3]/div";
					Wait.WaitForElement(driver, next);
					driver.findElement(By.xpath(next)).click();
					Wait.wait2Second();
				}
			}
		}
	}

	public void enterPhone(WebDriver driver, ArrayList<String> test_steps, String countryCode, String phoneNumber,
			String Ext) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_INFO_PHONE_COUNTRY_CODE);
		res.GUEST_INFO_PHONE_COUNTRY_CODE.clear();
		res.GUEST_INFO_PHONE_COUNTRY_CODE.sendKeys(countryCode);
		test_steps.add("Enter country Code Number : " + countryCode);
		logger.info("Enter country Code Number : " + countryCode);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_Phone);
		try {
			res.CP_NewReservation_Phone.clear();
			res.CP_NewReservation_Phone.sendKeys(phoneNumber);
		} catch (Exception e) {
			res.CP_NewReservation_Phone2.clear();
			res.CP_NewReservation_Phone2.sendKeys(phoneNumber);
		}
		test_steps.add("Enter Phone Number : " + phoneNumber);
		logger.info("Enter Phone Number : " + phoneNumber);

		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_INFO_PHONE_EXTENSION);
		res.GUEST_INFO_PHONE_EXTENSION.clear();
		res.GUEST_INFO_PHONE_EXTENSION.sendKeys(Ext);
		test_steps.add("Enter country Code Number : " + Ext);
		logger.info("Enter country Code Number : " + Ext);

	}

	public void enterAlternatePhone(WebDriver driver, ArrayList<String> test_steps, String countryCode,
			String phoneNumber, String Ext) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE);
		res.GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE.clear();
		res.GUEST_INFO_ALTERNATEPHONE_COUNTRY_CODE.sendKeys(countryCode);
		test_steps.add("Enter Altenative Phone country Code Number : " + countryCode);
		logger.info("Enter Altenative Phone country Code Number : " + countryCode);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_AltenativePhone);

		res.CP_NewReservation_AltenativePhone.clear();
		res.CP_NewReservation_AltenativePhone.sendKeys(phoneNumber);

		test_steps.add("Enter Altenative Phone Number : " + phoneNumber);
		logger.info("Enter Altenative Phone Number : " + phoneNumber);

		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_INFO_ALTERNATEPHONE_EXTENSION);
		res.GUEST_INFO_ALTERNATEPHONE_EXTENSION.clear();
		res.GUEST_INFO_ALTERNATEPHONE_EXTENSION.sendKeys(Ext);
		test_steps.add("Enter Altenative Phone country Code Number : " + Ext);
		logger.info("Enter Altenative Phone country Code Number : " + Ext);

	}

	public void AddTask(WebDriver driver, ArrayList test_steps, String IsTask, String TaskCategory, String TaskType,
			String TaskDetails, String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_AddTask);
		test_steps.add("******************* Adding Task **********************");
		logger.info("******************* Adding Task **********************");
		if (IsTask.equalsIgnoreCase("Yes")) {
			Utility.ScrollToElement(res.CP_AddTask, driver);
			res.CP_AddTask.click();
			test_steps.add("Click on Add Task");
			logger.info("Click on Add Task");

			String taskCategoryArrow = "//label[text()='Category']/..//button";
			Wait.WaitForElement(driver, taskCategoryArrow);
			driver.findElement(By.xpath(taskCategoryArrow)).click();

			String taskCategory = "//label[text()='Category']/..//div/ul//span[text()='" + TaskCategory.trim() + "']";
			Wait.WaitForElement(driver, taskCategory);
			driver.findElement(By.xpath(taskCategory)).click();
			test_steps.add("Select Task Category : " + TaskCategory);
			logger.info("Select Task Category : " + TaskCategory);
			Wait.wait2Second();

			String taskType = "//div[contains(@class,'reservation-task-modal')]//label[text()='Type']//..//div//button";// "//label[text()='Type']//..//div//button";
			logger.info(taskType);
			Wait.WaitForElement(driver, taskType);
			driver.findElement(By.xpath(taskType)).click();
			Wait.wait2Second();
			taskType = "//label[text()='Type']//..//div/ul//span[text()='" + TaskType.trim() + "']";
			Wait.WaitForElement(driver, taskType);
			driver.findElement(By.xpath(taskType)).click();
			test_steps.add("Select Task Type : " + TaskType);
			logger.info("Select Task Type : " + TaskType);

			String taskDetails = "//label[text()='Details']/preceding-sibling::textarea";
			Wait.WaitForElement(driver, taskDetails);
			driver.findElement(By.xpath(taskDetails)).sendKeys(TaskDetails);
			test_steps.add("Select Task Details : " + TaskDetails);
			logger.info("Select Task Details : " + TaskDetails);

			String taskRemarks = "//label[text()='Remarks']/preceding-sibling::textarea";
			Wait.WaitForElement(driver, taskRemarks);
			driver.findElement(By.xpath(taskRemarks)).sendKeys(TaskRemarks);
			test_steps.add("Enter Task Remarks : " + TaskRemarks);
			logger.info("Enter Task Remarks : " + TaskRemarks);

			if (!TaskAssignee.isEmpty()) {
				String taskAssignee = "//label[text()='Assignee']/..//input";
				WebElement elementAssigneeInput = driver.findElement(By.xpath(taskAssignee));

				elementAssigneeInput.sendKeys(TaskAssignee);
				//TaskAssignee = "auto";
				String[] splitString = TaskAssignee.split(" ");
				TaskAssignee = splitString[0];
				String noDataFound = "//label[text()='Assignee']//..//div//div";
				for (int i = 0; i < 5; i++) {
					elementAssigneeInput.click();
					elementAssigneeInput.clear();
					for (int j = 0; j < TaskAssignee.length(); j++) {
						elementAssigneeInput.sendKeys(String.valueOf(TaskAssignee.charAt(j)));
						Wait.wait1Second();
					}
					try {
						logger.info("in try");
						Wait.wait1Second();
						String taskassgin = "(//label[text()='Assignee']/..//div//ul//li//div/span)[2]";
						// Wait.WaitForElement(driver, taskassgin);
						WebElement elementTaskAssign = driver.findElement(By.xpath(taskassgin));
						elementTaskAssign.click();
						break;
					} catch (Exception e) {
						logger.info("in catch");
					}

				}
			}

			String taskStatus = "(//label[text()='Status']/..//button)[2]";
			Wait.WaitForElement(driver, taskStatus);
			driver.findElement(By.xpath(taskStatus)).click();
			String status = "(//label[text()='Status']/..//button)[2]/following-sibling::div//li//span[text()='"
					+ TaskStatus.trim() + "']";
			Wait.WaitForElement(driver, status);
			driver.findElement(By.xpath(status)).click();
			test_steps.add("Select Task Status : " + TaskStatus);
			logger.info("Select Task Status : " + TaskStatus);

			String save = "//h4[text()='Add Task']/../..//button[text()='Save']";
			Wait.WaitForElement(driver, save);
			driver.findElement(By.xpath(save)).click();
			test_steps.add("Click on Save");
			logger.info("Click on Save");
		}
	}

	

	public boolean verifyDeleteTaskIsDisabled(WebDriver driver, ArrayList<String> test_steps, String taskType)
			throws InterruptedException {
		String deleteTask = "//span[text()='Tasks ']/../..//div//tbody/tr//span[contains(text(),'" + taskType
				+ "')]/../..//following-sibling::td[4]//button";
		Wait.WaitForElement(driver, deleteTask);
		if (!driver.findElement(By.xpath(deleteTask)).isEnabled()) {
			test_steps.add("Verified delete task button is disabled");
			logger.info("Verified delete task button is disabled");
			return true;
		} else {
			test_steps.add("Failed to verify delete task button is disabled");
			logger.info("Failed to verify delete task button is disabled");
			return false;
		}
	}

	public void verify_NotesSections(WebDriver driver, ArrayList test_steps) {
		test_steps.add("******************* Verify Notes section **********************");
		logger.info("******************* Verify Notes section **********************");
		String note = "//a[text()='ADD NOTE ']";
		if (driver.findElement(By.xpath(note)).isDisplayed()) {
			test_steps.add("Notes Section is displayed");
			logger.info("Notes Section is displayed");
		} else {
			test_steps.add("Notes Section is not displayed");
			logger.info("Notes Section is not displayed");
		}
	}

	public void enter_Notes(WebDriver driver, ArrayList test_steps, String IsAddNotes, String NoteType, String Subject,
			String Description) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_AddNotes);
		test_steps.add("******************* Adding Notes **********************");
		logger.info("******************* Adding Notes **********************");
		if (IsAddNotes.equalsIgnoreCase("Yes")) {
			try {
				Utility.scrollAndClick(driver, By.xpath(OR_Reservation.CP_AddNotes));
			} catch (Exception e) {
				Utility.clickThroughJavaScript(driver, res.CP_AddNotes);
			}
			test_steps.add("Click on Add Notes");
			logger.info("Click on Add Notes");
			Wait.wait5Second();
			String noteTypeArrow = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button";
			Wait.WaitForElement(driver, noteTypeArrow);
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(noteTypeArrow)));

			} catch (Exception e) {
				driver.findElement(By.xpath(noteTypeArrow)).click();
			}

			String noteType = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button/..//div//li/a/span[text()='"
					+ NoteType.trim() + "']";
			Wait.WaitForElement(driver, noteType);
			driver.findElement(By.xpath(noteType)).click();
			test_steps.add("Select Note Type : " + NoteType);
			logger.info("Select Note Type : " + NoteType);

			String subject = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Subject']/..//input";
			Wait.WaitForElement(driver, subject);
			driver.findElement(By.xpath(subject)).sendKeys(Subject);
			test_steps.add("Enter subject : " + Subject);
			logger.info("Enter subject : " + Subject);

			String description = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Description']/preceding-sibling::textarea";
			Wait.WaitForElement(driver, description);
			driver.findElement(By.xpath(description)).sendKeys(Description);
			test_steps.add("Enter Description : " + Description);
			logger.info("Enter Description : " + Description);

			String user = "//input[starts-with(@data-bind,'value: updatedBy')]";

			JavascriptExecutor js = (JavascriptExecutor) driver;
			user = (String) js.executeScript("return arguments[0].value", driver.findElement(By.xpath(user)));

			String add = "//h4[text()='Add Notes']/../..//button[text()='ADD']";
			Wait.WaitForElement(driver, add);
			Utility.customAssert(driver.findElement(By.xpath(add)).isEnabled() + "", "true", true,
					"Successfully Verified Add Note Button", "Successfully Verified Add Note Button", true, test_steps);
			driver.findElement(By.xpath(add)).click();
			test_steps.add("Click on Add");
			logger.info("Click on Add");

			String noteverify = "//td[text()='" + NoteType.trim() + "']";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Sucessfully added Note : " + Subject);
			logger.info("Sucessfully added Note : " + Subject);

			test_steps.add("Verified Note Type : " + NoteType);
			logger.info("Verified Note Type : " + NoteType);

			noteverify = "//td[text()='" + Subject.trim() + "']";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note Subject : " + Subject);
			logger.info("Verified Note Subject : " + Subject);

			noteverify = "//td[text()='" + Description.trim() + "']";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note Description : " + Description);
			logger.info("Verified Note Description : " + Description);

			noteverify = "//td[text()='" + user.trim() + "']";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note updated by : " + user);
			logger.info("Verified Note updated by : " + user);
		}
	}

	public void verify_Notes(WebDriver driver, ArrayList test_steps, String NoteType, String Subject,
			String Description, String user, ArrayList RoomAbbri, ArrayList Rooms) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_AddNotes);
		test_steps.add("******************* Verify  Notes **********************");
		logger.info("******************* Verify Notes **********************");
		int count = RoomAbbri.size();
		for (int i = 0; i < count; i++) {
			String noteverify = "(//td[text()='" + NoteType.trim() + "'])[" + (i + 1) + "]";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Sucessfully added Note : " + Subject);
			logger.info("Sucessfully added Note : " + Subject);

			test_steps.add("Verified Note Type : " + NoteType);
			logger.info("Verified Note Type : " + NoteType);

			noteverify = "(//td[text()='" + Subject.trim() + "'])[" + (i + 1) + "]";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note Subject : " + Subject);
			logger.info("Verified Note Subject : " + Subject);

			noteverify = "(//td[text()='" + Description.trim() + "'])[" + (i + 1) + "]";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note Description : " + Description);
			logger.info("Verified Note Description : " + Description);

			noteverify = "(//td[text()='" + user.trim() + "'])[" + (i + 1) + "]";
			Wait.WaitForElement(driver, noteverify);
			test_steps.add("Verified Note updated by : " + user);
			logger.info("Verified Note updated by : " + user);

			String[] room = Rooms.get(i).toString().trim().split(":");
			String abbr = RoomAbbri.get(i).toString().trim();

			String[] abbri = abbr.trim().split(":");

			abbr = abbri[1] + ":" + room[1];
			logger.info(abbr);
			String roomnum = "//td[contains(text(),'" + abbr + "')]";
			logger.info(roomnum);
			Wait.WaitForElement(driver, roomnum);
			test_steps.add("Verified Note Room Number : " + abbr);
			logger.info("Verified Note Room Number : " + abbr);
		}
	}

	public void verifyBookButton(WebDriver driver, ArrayList<String> test_steps) {
		String book = "//reservation-statusbar//button[contains(@data-bind,'isBookEnable')]";
		Wait.WaitForElement(driver, book);
		Utility.customAssert(driver.findElement(By.xpath(book)).isDisplayed() + "", "true", true,
				"Successfully Verified BOOK Button Visible", "Failed to Verified BOOK Button Visible", true,
				test_steps);
//					driver.findElement(By.xpath(book)).click();
//					logger.info("Clicking on book");
//					test_steps.add("Clicking on book");
	}

	public void clickAddTaskButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Utility.ScrollToElement(res.CP_AddTask, driver);
		res.CP_AddTask.click();
	       
		test_steps.add("Click on Add Task");
		logger.info("Click on Add Task");
	}

	public void clickTaskSaveButton(WebDriver driver, ArrayList<String> test_steps) {
		String save = "//h4[text()='Add Task']/../..//button[text()='Save']";
		Wait.WaitForElement(driver, save);
		driver.findElement(By.xpath(save)).click();
		test_steps.add("Click on Save");
		logger.info("Click on Save");
	}

	public void verifyBackButtonInTaskPopup(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Utility.ScrollToElement(res.CP_AddTask, driver);
		res.CP_AddTask.click();
		test_steps.add("Click on Add Task");
		logger.info("Click on Add Task");

		try {
			String backBtnPath = "//h4[text()='Add Task']/../..//button[text()='Back']";
			Wait.WaitForElement(driver, backBtnPath);
		} catch (Exception e) {
			test_steps.add("Successfuly Verified Back Button Not Available in Task Popup");
			logger.info("Successfuly Verified Back Button Not Available in Task Popup");
		}

		driver.findElement(By.xpath(
				"//h4[text()='Add Task']/../..//div[@class='modal-header']//button[@data-bind='click: OnCloseTaskModalClick']"))
				.click();
	}

	public ArrayList<String> verifyCreatedTask(WebDriver driver, String TaskType, String Details, String Status,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);

		WebElement TaskTypeCheck = driver
				.findElement(By.xpath("//span[text()='Tasks']/../..//td//span[text()='" + TaskType.trim() + "']"));
		Utility.ScrollToElement(TaskTypeCheck, driver);
		TaskTypeCheck.getText();
		assertEquals(TaskTypeCheck.getText(), TaskType, "Failed : TaskType missmatched");
		logger.info("Verified Task  Type :" + TaskType);
		test_steps.add("Verified Task  Type :" + TaskType);

		WebElement DetailsCheck = driver.findElement(By.xpath(
				"//td[contains(@data-bind,'text: description')  and contains(text(),'" + Details.trim() + "')]"));
		DetailsCheck.getText();
		assertEquals(DetailsCheck.getText(), Details, "Failed : Detail missmatched");
		logger.info("Verified Task  Detail :" + Details);

		test_steps.add("Verified Task  Detail :" + Details);

		WebElement StatusCheck = driver
				.findElement(By.xpath("//span[text()='Tasks']/../..//td[contains(text(),'" + Status + "')]"));
		StatusCheck.getText();
		assertEquals(StatusCheck.getText(), Status, "Failed : Status missmatched");
		logger.info("Verified Task  Status :" + Status);
		test_steps.add("Verified Task  Status :" + Status);
		return test_steps;

	}

	public FindRoomDetail getFindRooDetail(WebDriver driver, String RatePlan, String RoomClass,
			ArrayList<String> test_steps) throws InterruptedException {

		FindRoomDetail findRoomDetail;

		String sectionXpath = "//section[@class='ir-roomClassDetails manual-override']//div[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='"
				+ RatePlan.toUpperCase() + "']";
		String roomClassXpath = sectionXpath
				+ "/../following-sibling::div//span[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='"
				+ RoomClass.toUpperCase() + "']";
		String ratePlanXpath = roomClassXpath + "/../following-sibling::div/span";
		String availableRoomsXpath = roomClassXpath + "/../../div[3]/span";
		String totalAmountXpath = roomClassXpath + "/../../../../div[2]/div[1]/span";

		String tableRecordXpath = roomClassXpath + "/../../../../following-sibling::div//table/tbody/tr";

		String descDateXpath = "//td[contains(@data-bind,'dateEffective')]";
		String descRateXpath = "//td[contains(@data-bind,'name')]";
		String descAmountXpath = "//td[contains(@data-bind,'amount')]";

		expandRoomClassDetailOnFindRoom(driver, RatePlan, RoomClass, true, test_steps);
		int descTableSize = driver.findElements(By.xpath(tableRecordXpath)).size();

		HashMap<String, DateWiseRoomRate> dateWiseRoomRate = new HashMap<String, DateWiseRoomRate>();

		for (int i = 1; i <= descTableSize; i++) {

			DateWiseRoomRate d = new DateWiseRoomRate(
					Utility.getELementText(
							driver.findElement(By.xpath(tableRecordXpath + "[" + i + "]" + descDateXpath)), false, ""),
					Utility.getELementText(
							driver.findElement(By.xpath(tableRecordXpath + "[" + i + "]" + descRateXpath)), false, ""),
					Utility.getELementText(
							driver.findElement(By.xpath(tableRecordXpath + "[" + i + "]" + descAmountXpath)), false,
							""));

			dateWiseRoomRate.put(
					Utility.getELementText(
							driver.findElement(By.xpath(tableRecordXpath + "[" + i + "]" + descDateXpath)), false, ""),
					d);
		}

		findRoomDetail = new FindRoomDetail(
				Utility.getELementText(driver.findElement(By.xpath(roomClassXpath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(ratePlanXpath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(availableRoomsXpath)), false, ""),
				Utility.getELementText(driver.findElement(By.xpath(totalAmountXpath)), false, ""), dateWiseRoomRate);

		return findRoomDetail;
	}

	public void expandRoomClassDetailOnFindRoom(WebDriver driver, String RatePlan, String RoomClass, boolean isExpand,
			ArrayList<String> test_steps) {
		String sectionXpath = "//section[@class='ir-roomClassDetails manual-override']//div[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='"
				+ RatePlan.toUpperCase() + "']";
		String roomClassXpath = sectionXpath
				+ "/../following-sibling::div//span[(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='"
				+ RoomClass.toUpperCase() + "']";
		String showDetailXpath = roomClassXpath + "/../../../..";

		String roomClassDetailToggleXpath = roomClassXpath + "//following-sibling::div";

		String toggleClassName = Utility.getELementText(driver.findElement(By.xpath(roomClassDetailToggleXpath)), true,
				"class");

		if (isExpand) {
			if (!toggleClassName.contains("open")) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(showDetailXpath)));
				logger.info("RoomClass Detail Expanded");
				test_steps.add("RoomClass Detail Expanded");
			} else {
				logger.info("RoomClass Detail already Expanded");
				test_steps.add("RoomClass Detail already Expanded");
			}
		} else {
			if (toggleClassName.contains("open")) {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(showDetailXpath)));
				logger.info("RoomClass Detail Collapsed");
				test_steps.add("RoomClass Detail Collapsed");
			} else {
				logger.info("RoomClass Detail already Collapsed");
				test_steps.add("RoomClass Detail already Collapsed");
			}
		}
	}

	public void verifyRoomClassDetail(FindRoomDetail expectedfindRoomDetail, String expectedRoomClassName,
			boolean isVerifyRoomClassName, boolean isContinueExeRoomClassName, String expectedRatePlanName,
			boolean isVerifyRatePlanName, boolean isContinueExeRatePlanName, String expectedAvailableRooms,
			boolean isVerifyAvailableRooms, boolean isContinueExeAvailableRooms, String expectedTotalAmount,
			boolean isVerifyTotalAmount, boolean isContinueExeTotalAmount, ArrayList<String> test_steps) {

		Utility.customAssert(expectedfindRoomDetail.getROOM_CLASS_NAME(), expectedRoomClassName, isVerifyRoomClassName,
				"Successfully Verified RoomClass Name : " + expectedRoomClassName, "Failed To Verify ",
				isContinueExeRoomClassName, test_steps);

		Utility.customAssert(expectedfindRoomDetail.getRATE_PLAN_NAME(), expectedRatePlanName, isVerifyRatePlanName,
				"Successfully Verified RatePlan Name : " + expectedRatePlanName, "Failed To Verify ",
				isContinueExeRatePlanName, test_steps);

		Utility.customAssert(expectedfindRoomDetail.getAVAILABLE_ROOM(), expectedAvailableRooms, isVerifyAvailableRooms,
				true, "Successfully Verified Available Room : " + expectedAvailableRooms, "Failed To Verify ",
				isContinueExeAvailableRooms, test_steps);
		if (isVerifyTotalAmount) {
			Utility.customAssert(
					Double.parseDouble(
							Utility.removeCurrencySignBracketsAndSpaces(expectedfindRoomDetail.getROOM_TOTAL_AMOUNT()))
							+ "",
					Double.parseDouble(expectedTotalAmount) + "", isVerifyTotalAmount,
					"Successfully Verified Total Amount : " + expectedTotalAmount, "Failed To Verify ",
					isContinueExeTotalAmount, test_steps);
		}
	}

	public void select_RoomWithRatePlanRulesValidation(WebDriver driver, ArrayList test_steps, String RoomClass,
			String IsAssign, String Account, String checkinColor, String checkoutColor, String minStaycolor,
			int minNights) throws InterruptedException {

		logger.info(RoomClass);
		String room = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
				+ "')]/../../div[3]/span";

		logger.info(room);
		Wait.WaitForElement(driver, room);

		String rooms = driver.findElement(By.xpath(room)).getText();

		logger.info(rooms);
		String[] roomsCount = rooms.split(" ");
		int count = Integer.parseInt(roomsCount[0].trim());
		if (count > 0) {
			String sel = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'" + RoomClass
					+ "')]/../../../following-sibling::div/div/select";

			String rulessize = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
					+ RoomClass.trim() + "')]/following-sibling::span";
			logger.info(rulessize);
			int ruleCount = driver.findElements(By.xpath(rulessize)).size();
//				WebElement ele = driver.findElement(By.xpath(sel));
			test_steps.add("Selected room class : " + RoomClass);
			logger.info("Selected room class : " + RoomClass);
			if (IsAssign.equalsIgnoreCase("No")) {
				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String unAssign = "(//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass
						+ "')]/../../../following-sibling::div/div/select/following-sibling::div//ul//span[text()='Unassigned'])";
				Wait.WaitForElement(driver, unAssign);
				driver.findElement(By.xpath(unAssign)).click();
				test_steps.add("Selected room number : Unassigned");
				logger.info("Selected room number : Unassigned");
			} else {
				String roomnum = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/option";
				logger.info(roomnum);
				int count1 = driver.findElements(By.xpath(roomnum)).size();
				Random random = new Random();
				int randomNumber = random.nextInt(count1 - 1) + 1;
				logger.info("count : " + count1);
				logger.info("randomNumber : " + randomNumber);
				Wait.WaitForElement(driver, sel);

				String expand = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div/button";
				Wait.WaitForElement(driver, expand);
				driver.findElement(By.xpath(expand)).click();

				String roomNumber = "//section[@class='ir-roomClassDetails manual-override']//span[contains(text(),'"
						+ RoomClass + "')]/../../../following-sibling::div/div/select/following-sibling::div//ul/li["
						+ randomNumber + "]/a/span";
				Wait.WaitForElement(driver, roomNumber);
				driver.findElement(By.xpath(roomNumber)).click();
			}
//
//				String select = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + RoomClass
//						+ "']/../../../following-sibling::div//span[contains(text(),'SELECT')]";
			String select = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + RoomClass
					+ "']/../../../../div[2]/div[3]/a";
			if (driver.findElements(By.xpath(select)).size() > 0) {
				Wait.WaitForElement(driver, select);
				driver.findElement(By.xpath(select)).click();
			} else {
				String selected = "//section[@class='ir-roomClassDetails manual-override']//span[text()='" + RoomClass
						+ "']/../../../following-sibling::div/div//span[contains(@data-bind,'SELECT')]/..";
				Wait.WaitForElement(driver, selected);
				driver.findElement(By.xpath(selected)).click();
			}

//				String loading = "(//div[@class='ir-loader-in'])[2]";
//				int counter = 0;
//				while (true) {
//					if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
//						break;
//					} else if (counter == 10) {
//						break;
//					} else {
//						counter++;
//						Wait.wait2Second();
//					}
//				}
			waitForLoadingSymbol(driver);
			logger.info("Waited to loading symbol");

			logger.info("Rule Count : " + ruleCount);
			if (ruleCount > 1) {
				Wait.wait5Second();
				String rules = "//p[text()='Selecting this room will violate the following rules']/../..//button[text()='Yes']";

				if (minStaycolor.equalsIgnoreCase("Red")) {
					String min = "//p[text()='Selecting this room will violate the following rules']/..//div[contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
							+ minNights + " NIGHT')]";
					Wait.WaitForElement(driver, min);
					test_steps.add("Verified min stay rule in the rules broken pop up as : " + minNights + " night");
					logger.info("Verified min stay rule in the rules broken pop up as : " + minNights + " night");
				}

				if (checkinColor.equalsIgnoreCase("Red")) {
					String min = "//p[text()='Selecting this room will violate the following rules']/..//div";
					Wait.WaitForElement(driver, min);
					String nocheckin = driver.findElement(By.xpath(min)).getText();
					if (nocheckin.contains("No Check In")) {
						test_steps.add("Verified no check in rule in the rules broken pop up");
						logger.info("Verified no check in rule in the rules broken pop up");
					}
				}

				if (checkoutColor.equalsIgnoreCase("Red")) {
					String min = "//p[text()='Selecting this room will violate the following rules']/..//div";
					Wait.WaitForElement(driver, min);
					String nocheckin = driver.findElement(By.xpath(min)).getText();
					if (nocheckin.contains("No Check out")) {
						test_steps.add("Verified no check in rule in the rules broken pop up");
						logger.info("Verified no check in rule in the rules broken pop up");
					}
				}

				if (driver.findElements(By.xpath(rules)).size() > 0) {
					Wait.wait2Second();
					driver.findElement(By.xpath(rules)).click();
					test_steps.add(
							"Selecting this room will violate the following rules : Are you sure you want to proceed? : yes");
					logger.info(
							"Selecting this room will violate the following rules : Are you sure you want to proceed? : yes");
					waitForLoadingSymbol(driver);
//						loading = "(//div[@class='ir-loader-in'])[2]";
//						counter = 0;
//						while (true) {
//							if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
//								break;
//							} else if (counter == 4) {
//								break;
//							} else {
//								counter++;
//								Wait.wait2Second();
//							}
//						}
				}
			}
			if (!(Account.isEmpty() || Account.equalsIgnoreCase(""))) {
				try {
					String policy = "//p[contains(text(),'Based on the changes made')]/../..//button[text()='Yes']";
					Wait.WaitForElement(driver, policy);
					driver.findElement(By.xpath(policy)).click();
					test_steps.add("Based on the changes made, new policies are applicable.? : yes");
					logger.info("Based on the changes made, new policies are applicable.? : yes");
				} catch (Exception e) {
					test_steps
							.add("No Popup Appers with Based on the changes made, new policies are applicable.? : yes");
					logger.info("No Popup Appers with Based on the changes made, new policies are applicable.? : yes");
				}
				waitForLoadingSymbol(driver);
//					loading = "(//div[@class='ir-loader-in'])[2]";
//					counter = 0;
//					while (true) {
//						if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
//							break;
//						} else if (counter == 3) {
//							break;
//						} else {
//							counter++;
//							Wait.wait2Second();
//						}
//					}
			}

		} else {
			test_steps.add("Rooms Count <=0 for " + RoomClass + " for specified date");
			logger.info("Rooms Count <=0 for " + RoomClass + " for specified date");
		}

	}

	public void verifyHistoryTabNextToFolioTab(WebDriver driver, ArrayList<String> test_steps) {
		String path = "(//ul[@class='nav nav-tabs']//a[text()='History']/../preceding-sibling::li/a)[2]";
		Utility.customAssert(driver.findElement(By.xpath(path)).getText().toUpperCase(), "Folio(s)".toUpperCase(), true,
				"Successfully Verified History Tab Next To Folio Tab", "Failed To Verify History Tab Location", true,
				test_steps);
	}

	public void enterTextToSearchHistory(WebDriver driver, String searchText, boolean isFound, HistoryTable ht, int row,
			ArrayList<String> test_steps) {
		Utility.customAssert(driver.findElement(By.id("txtHistorySearch")).isDisplayed() + "", true + "", true,
				"Successfully Verified History Search Feild is Displayed", "Failed To Verify Search Feild ", true,
				test_steps);
		driver.findElement(By.id("txtHistorySearch")).clear();
		driver.findElement(By.id("txtHistorySearch")).sendKeys(searchText);

		String tableCol = "";

		switch (ht) {
		case CATEGORY:
			tableCol = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'categoryId')]";
			break;
		case DATE:
			tableCol = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'formatDate')]";
			break;
		case TIME:
			tableCol = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'formatTime')]";
			break;
		case USER:
			tableCol = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'firstName')]";
			break;

		case DESCRIPTION:
			tableCol = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'description')]";
			break;
		default:
			tableCol = "";
		}

		if (isFound) {
			String path = "//td[contains(text(),'No history log')]";
			try {

				if (ht == HistoryTable.USER) {
					Utility.customAssert(
							driver.findElement(By.xpath(tableCol)).getText().toUpperCase() + " "
									+ driver.findElement(By.xpath("//tbody[@data-bind='foreach:historyLogs']/tr[" + row
											+ "]/td/span[contains(@data-bind,'lastName')]")).getText().toUpperCase(),
							searchText.toUpperCase(), true, true, "Successfully Verified Searched Text ",
							"Failed To Verify Searched Text", true, test_steps);
				} else {
					Utility.customAssert(driver.findElement(By.xpath(tableCol)).getText().toUpperCase(),
							searchText.toUpperCase(), true, true, "Successfully Verified Searched Text ",
							"Failed To Verify Searched Text", true, test_steps);
				}

				assertFalse(driver.findElement(By.xpath(path)).isDisplayed());
			} catch (Exception e) {
//					assertTrue();
//					test_steps.add("Successfully Verified Searched Text : " + searchText);
//					logger.info("Successfully Verified Searched Text"+ searchText);
			}
		} else {
			String path = "//td[contains(text(),'No history log')]";
			try {
				driver.findElement(By.xpath(path)).isDisplayed();
				assertTrue(true);
				test_steps.add("Successfully Verified Searched Text : No history log");
				logger.info("Successfully Verified Searched Text : No history log");
			} catch (Exception e) {
				assertTrue(false);
			}
		}
	}

	public void verifyHistoryTable(WebDriver driver, ArrayList<String> test_steps) {
		Utility.customAssert(driver.findElement(By.xpath("//th[contains(text(),'CATEGORY')]")).isDisplayed() + "",
				true + "", true, "Successfully Verified History Table CATEGORY Heading is Displayed",
				"Failed To Verify History Table CATEGORY Heading is not Displayed ", true, test_steps);

		Utility.customAssert(driver.findElement(By.xpath("//th[contains(text(),'DATE')]")).isDisplayed() + "",
				true + "", true, "Successfully Verified History Table DATE Heading is Displayed",
				"Failed To Verify History Table DATE Heading is not Displayed ", true, test_steps);

		Utility.customAssert(driver.findElement(By.xpath("//th[contains(text(),'TIME')]")).isDisplayed() + "",
				true + "", true, "Successfully Verified History Table TIME Heading is Displayed",
				"Failed To Verify History Table TIME Heading is not Displayed ", true, test_steps);

		Utility.customAssert(driver.findElement(By.xpath("//th[contains(text(),'USER')]")).isDisplayed() + "",
				true + "", true, "Successfully Verified History Table USER Heading is Displayed",
				"Failed To Verify History Table USER Heading is not Displayed ", true, test_steps);

		Utility.customAssert(driver.findElement(By.xpath("//th[contains(text(),'DESCRIPTION')]")).isDisplayed() + "",
				true + "", true, "Successfully Verified History Table DESCRIPTION Heading is Displayed",
				"Failed To Verify History Table DESCRIPTION Heading is not Displayed ", true, test_steps);
	}

	public void verifyReservationInHistoryTab(WebDriver driver, ArrayList<String> test_steps, String reservation) {
		String res = "//span[contains(text(),'Created reservation with Confirmation Number: " + reservation.trim()
				+ "')]";

		try {
			String loading = "//history-info//div[contains(@data-bind,'showLoading')]//div";
			int counter = 0;
			while (true) {
				if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
					break;
				} else if (counter == 3) {
					break;
				} else {
					counter++;
					Wait.wait2Second();
				}
			}
			logger.info("Waited to loading symbol");
		} catch (Exception e) {
			logger.info("no loading");
		}
		if (driver.findElement(By.xpath(res)).isDisplayed()) {
			test_steps.add("Reservation creation verified in History tab : " + reservation);
			logger.info("Reservation creation verified in History tab : " + reservation);
		} else {
			test_steps.add("Reservation creation not found in History tab : " + reservation);
			logger.info("Reservation creation not found in History tab : " + reservation);
		}
	}

	// Added By Riddhi
	public void clickOnEditPaymentInfoIcon(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.EDIT_PAYMENT_INFO_ICON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.EDIT_PAYMENT_INFO_ICON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.EDIT_PAYMENT_INFO_ICON), driver);
		Utility.ScrollToElement_NoWait(resV2.EDIT_PAYMENT_INFO_ICON, driver);
		resV2.EDIT_PAYMENT_INFO_ICON.click();

		logger.info("========================Clicking on Edit Payment Info Pencil Icon========================");
		test_steps.add("========================Clicking on Edit Payment Info Pencil Icon========================");
	}

	// Added by Riddhi - Modify Payment methods, billing address info based on
	// passed parameters
	public void modifyPaymentInfo(WebDriver driver, String paymentType, String cardNumber, String nameOnCard,
			String cardExpDate, String accountName, ArrayList<String> test_steps) throws InterruptedException {
		logger.info("========================Modifying Payment Method========================");
		test_steps.add("========================Modifying Payment Method========================");

		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_PaymentMethod, driver);
		res.CP_NewReservation_PaymentMethod.click();

		if (paymentType.equalsIgnoreCase("Swipe")) {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'MC')]";
			Wait.WaitForElement(driver, paymentMethod);
			driver.findElement(By.xpath(paymentMethod)).click();
			res.CP_NewReservation_Swipe.click();
			test_steps.add("Clicking in Swipe");
			logger.info("Clicking in Swipe");
			Wait.wait1Second();
			String CC = "(//label[text()='Credit Card Number']/preceding-sibling::input)[2]";
			Wait.WaitForElement(driver, CC);
			driver.findElement(By.xpath(CC)).sendKeys(cardNumber);
			test_steps.add("Enter CardNumber : " + cardNumber);
			logger.info("Enter CardNumber : " + cardNumber);
			res.CP_NewReservation_CardNumber.sendKeys(Keys.TAB);
			Wait.wait2Second();
		} else {
			String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
					+ paymentType.trim() + "')]";
			Wait.WaitForElement(driver, paymentMethod);
			driver.findElement(By.xpath(paymentMethod)).click();
			test_steps.add("Selected PaymentType : " + paymentType);
			logger.info("Selected PaymentType : " + paymentType);
			if (paymentType.trim().equalsIgnoreCase("MC") || paymentType.trim().equalsIgnoreCase("Visa")
					|| paymentType.trim().equalsIgnoreCase("Amex") || paymentType.trim().equalsIgnoreCase("Discover")
					|| paymentType.trim().equalsIgnoreCase("Masterrr")) {
				res.CP_NewReservation_CardNumber.sendKeys(cardNumber);
				test_steps.add("Enter CardNumber : " + cardNumber);
				logger.info("Enter CardNumber : " + cardNumber);
				res.CP_NewReservation_NameOnCard.sendKeys(nameOnCard);
				test_steps.add("Enter Name On Card : " + nameOnCard);
				logger.info("Enter Name On Card : " + nameOnCard);
				res.CP_NewReservation_CardExpDate.sendKeys(cardExpDate);
				test_steps.add("Enter Card ExpDate : " + cardExpDate);
				logger.info("Enter Card ExpDate : " + cardExpDate);
			} else if (paymentType.trim().equalsIgnoreCase("Gift Certificate")) {
				res.CP_NewReservation_GiftCertNumber.sendKeys(cardNumber);
				test_steps.add("Enter Gift Card Number : " + cardNumber);
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../.."),
						driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../..")).click();
			} else if (paymentType.trim().equalsIgnoreCase("Account (House Account)")) {
				res.CP_NewReservation_HouseAccountName.sendKeys(accountName);
				test_steps.add("Enter House Account name : " + accountName);
				Wait.wait2Second();
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + accountName.trim() + "')]/../../.."),
						driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + accountName.trim() + "')]/../../..")).click();
			} else if (paymentType.trim().equalsIgnoreCase("Reservation")) {
				res.CP_NewReservation_ResNumberPayment.sendKeys(cardNumber);
				test_steps.add("Enter Reservation number as Payment : " + cardNumber);
				Wait.wait2Second();
				Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../.."),
						driver);
				driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../..")).click();
			}
		}
		if (!res.CP_NewReservation_SameAsMailingAddress.isSelected()) {
			driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div/label"))
					.click();
		}
	}

	// Added By Riddhi
	public void modifyBillingAddressDetailsinPaymentInfo(WebDriver driver, ArrayList<String> test_steps,
			String modifyBillingAddress1, String modifyBillingAddress2, String modifyBillingAddress3, String modifyCity,
			String modifyCountry, String modifyState, String modifyPostalCode) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		test_steps.add("========== Uncheck Same As Billing Address Checkbox ==============");
		logger.info("========== Uncheck Same As Billing Address Checkbox ==============");
		if (res.CP_NewReservation_SameAsMailingAddress.isSelected()) {
			driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div/label"))
					.click();

			enter_BillingAddress2(driver, test_steps, modifyBillingAddress2);
			enter_BillingAddress3(driver, test_steps, modifyBillingAddress3);
			enter_BillingCity(driver, test_steps, modifyCity);
			enter_BillingCountry(driver, test_steps, modifyCountry);
			enter_BillingState(driver, test_steps, modifyState);
			enter_BillingPostalCode(driver, test_steps, modifyPostalCode);
			enter_BillingAddress1(driver, test_steps, modifyBillingAddress1);

			/*
			 * test_steps.add("========== Enter Billing Address2 Value ==============");
			 * logger.info("========== Enter Billing Address2 Value ==============");
			 * 
			 * 
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.
			 * PI_BILLING_ADDRESS2), driver); res.PI_BILLING_ADDRESS2.click();
			 * res.PI_BILLING_ADDRESS2.sendKeys(modifyBillingAddress2);
			 * res.PI_BILLING_ADDRESS2.sendKeys(Keys.TAB);
			 * 
			 * test_steps.add("========== Enter Billing Address3 Value ==============");
			 * logger.info("========== Enter Billing Address3 Value ==============");
			 * 
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.
			 * PI_BILLING_ADDRESS3), driver); res.PI_BILLING_ADDRESS3.click();
			 * res.PI_BILLING_ADDRESS3.sendKeys(modifyBillingAddress3);
			 * res.PI_BILLING_ADDRESS3.sendKeys(Keys.TAB); Wait.wait5Second();
			 * 
			 * 
			 * test_steps.add("========== Enter Billing Address City ==============");
			 * logger.info("========== Enter Billing Address City ==============");
			 * 
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_CITY),
			 * driver); res.PI_BILLING_CITY.click();
			 * res.PI_BILLING_CITY.sendKeys(modifyCity);
			 * res.PI_BILLING_CITY.sendKeys(Keys.TAB); Wait.wait5Second();
			 * 
			 * 
			 * test_steps.add("========== Select Billing Address Country==============");
			 * logger.info("========== Select Billing Address Country ==============");
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_COUNTRY
			 * ), driver); res.PI_BILLING_COUNTRY.click();
			 * 
			 * //(//select[@name='country'])[2]/following-sibling::div/button String cntry =
			 * "(//select[@name='country'])[2]/following-sibling::div//ul/li//span[contains(text(),'"
			 * + modifyCountry.trim() + "')]"; Wait.WaitForElement(driver, cntry);
			 * driver.findElement(By.xpath(cntry)).click();
			 * 
			 * test_steps.add("========== Select Billing Address State ==============");
			 * logger.info("========== Select Billing Address State ==============");
			 * Wait.WaitForElement(driver, OR_ReservationV2.PI_BILLING_STATE);
			 * 
			 * if
			 * (driver.findElements(By.xpath("(//select[@name='state'])[2]/option")).size()
			 * > 1) { res.PI_BILLING_STATE.click(); String stateSelect =
			 * "(//select[@name='state'])[2]/following-sibling::div//ul/li//span[contains(text(),'"
			 * + modifyState.trim() + "')]"; Wait.WaitForElement(driver, stateSelect);
			 * driver.findElement(By.xpath(stateSelect)).click();
			 * test_steps.add("Selected City : " + modifyState);
			 * logger.info("Selected City : " + modifyState); } else {
			 * test_steps.add("State field disabled"); logger.info("State field disabled");
			 * }
			 * 
			 * test_steps.
			 * add("========== Enter Billing Address - Postal Code ==============");
			 * logger.info("========== Enter Billing Address - Postal Code ==============");
			 * 
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.
			 * PI_BILLING_POSTALCODE), driver); res.PI_BILLING_POSTALCODE.click();
			 * res.PI_BILLING_POSTALCODE.sendKeys(modifyPostalCode);
			 * res.PI_BILLING_POSTALCODE.sendKeys(Keys.TAB); Wait.wait5Second();
			 * 
			 * test_steps.add("========== Enter Billing Address1 Value ==============");
			 * logger.info("========== Enter Billing Address1 Value ==============");
			 * 
			 * Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.
			 * PI_BILLING_ADDRESS1), driver); res.PI_BILLING_ADDRESS1.click();
			 * res.PI_BILLING_ADDRESS1.sendKeys(modifyBillingAddress1);
			 * res.PI_BILLING_ADDRESS1.sendKeys(Keys.ESCAPE);
			 * res.PI_BILLING_ADDRESS1.sendKeys(Keys.ESCAPE); Wait.wait2Second();
			 * res.PI_BILLING_ADDRESS1.sendKeys(Keys.TAB);
			 * res.PI_BILLING_ADDRESS1.sendKeys(Keys.TAB);
			 * 
			 * Wait.wait10Second();
			 */
		}

	}

	// Modify Billing Address Method
	public void enter_BillingAddress2(WebDriver driver, ArrayList<String> test_steps, String modifyBillingAddress2)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("========== Enter Billing Address2 Value ==============");
		logger.info("========== Enter Billing Address2 Value ==============");

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_ADDRESS2), driver);
		res.PI_BILLING_ADDRESS2.click();
		res.PI_BILLING_ADDRESS2.clear();
		res.PI_BILLING_ADDRESS2.sendKeys(modifyBillingAddress2);
		res.PI_BILLING_ADDRESS2.sendKeys(Keys.TAB);

	}

	public void enter_BillingAddress3(WebDriver driver, ArrayList<String> test_steps, String modifyBillingAddress3)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("========== Enter Billing Address3 Value ==============");
		logger.info("========== Enter Billing Address3 Value ==============");

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_ADDRESS3), driver);
		res.PI_BILLING_ADDRESS3.click();
		res.PI_BILLING_ADDRESS3.clear();
		res.PI_BILLING_ADDRESS3.sendKeys(modifyBillingAddress3);
		res.PI_BILLING_ADDRESS3.sendKeys(Keys.TAB);
		Wait.wait5Second();
	}

	public void enter_BillingCity(WebDriver driver, ArrayList<String> test_steps, String modifyCity)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("========== Enter Billing Address City ==============");
		logger.info("========== Enter Billing Address City ==============");

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_CITY), driver);
		res.PI_BILLING_CITY.click();
		res.PI_BILLING_CITY.clear();
		res.PI_BILLING_CITY.sendKeys(modifyCity);
		res.PI_BILLING_CITY.sendKeys(Keys.TAB);
		Wait.wait5Second();
	}

	public void enter_BillingCountry(WebDriver driver, ArrayList<String> test_steps, String modifyCountry)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		test_steps.add("========== Select Billing Address Country==============");
		logger.info("========== Select Billing Address Country ==============");
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_COUNTRY), driver);
		res.PI_BILLING_COUNTRY.click();

		// (//select[@name='country'])[2]/following-sibling::div/button
		String cntry = "(//select[@name='country'])[2]/following-sibling::div//ul/li//span[contains(text(),'"
				+ modifyCountry.trim() + "')]";
		Wait.WaitForElement(driver, cntry);
		driver.findElement(By.xpath(cntry)).click();
	}

	public void enter_BillingState(WebDriver driver, ArrayList<String> test_steps, String modifyState)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		test_steps.add("========== Select Billing Address State ==============");
		logger.info("========== Select Billing Address State ==============");
		Wait.WaitForElement(driver, OR_ReservationV2.PI_BILLING_STATE);

		if (driver.findElements(By.xpath("(//select[@name='state'])[2]/option")).size() > 1) {
			res.PI_BILLING_STATE.click();
			String stateSelect = "(//select[@name='state'])[2]/following-sibling::div//ul/li//span[contains(text(),'"
					+ modifyState.trim() + "')]";
			Wait.WaitForElement(driver, stateSelect);
			driver.findElement(By.xpath(stateSelect)).click();
			test_steps.add("Selected City : " + modifyState);
			logger.info("Selected City : " + modifyState);
		} else {
			test_steps.add("State field disabled");
			logger.info("State field disabled");
		}
	}

	public void enter_BillingPostalCode(WebDriver driver, ArrayList<String> test_steps, String modifyPostalCode)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("========== Enter Billing Address - Postal Code ==============");
		logger.info("========== Enter Billing Address - Postal Code ==============");

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_POSTALCODE), driver);
		res.PI_BILLING_POSTALCODE.click();
		res.PI_BILLING_POSTALCODE.clear();
		res.PI_BILLING_POSTALCODE.sendKeys(modifyPostalCode);
		res.PI_BILLING_POSTALCODE.sendKeys(Keys.TAB);
	}

	public void enter_BillingAddress1(WebDriver driver, ArrayList<String> test_steps, String modifyBillingAddress1)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		test_steps.add("========== Enter Billing Address1 Value ==============");
		logger.info("========== Enter Billing Address1 Value ==============");

		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PI_BILLING_ADDRESS1), driver);
		res.PI_BILLING_ADDRESS1.click();
		res.PI_BILLING_ADDRESS1.clear();
		res.PI_BILLING_ADDRESS1.sendKeys(modifyBillingAddress1);
		res.PI_BILLING_ADDRESS1.sendKeys(Keys.ESCAPE);
		res.PI_BILLING_ADDRESS1.sendKeys(Keys.ESCAPE);
		Wait.wait2Second();
		// res.PI_BILLING_ADDRESS1.sendKeys(Keys.TAB);
		res.PI_BILLING_ADDRESS1.sendKeys(Keys.TAB);

		Wait.wait10Second();

	}

	// Added By Riddhi
	public void clickOnSavePaymentInfo(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_ReservationV2 element = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.PAYMENT_INFO_SAVE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.PAYMENT_INFO_SAVE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.PAYMENT_INFO_SAVE), driver);
		element.PAYMENT_INFO_SAVE.click();

		test_steps.add("========================Saving Payment Info========================");
		logger.info("========================Saving Payment Info========================");
	}

	// Added By Riddhi
	public void clickOnEditMarketingInfoIcon(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {

		Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.EDIT_MARKETING_INFO_ICON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.EDIT_MARKETING_INFO_ICON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.EDIT_MARKETING_INFO_ICON), driver);
		Utility.ScrollToElement_NoWait(resV2.EDIT_MARKETING_INFO_ICON, driver);
		resV2.EDIT_MARKETING_INFO_ICON.click();

		logger.info("========================Clicking on Edit Marketing Info Pencil Icon========================");
		test_steps.add("========================Clicking on Edit Marketing Info Pencil Icon========================");
	}

	// Modify MarketField Value for existing reservation - Added By Riddhi
	public void modifyMarketFieldinMarketSegment(WebDriver driver, String modifyMarketFieldinMarketSegment,
			ArrayList<String> test_steps) throws InterruptedException {
		// Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
		Elements_CPReservation res = new Elements_CPReservation(driver);
		// Modify Market Field for existing reservation
		logger.info("========================Modifying Market Field Value========================");
		test_steps.add("========================Modifying Market Field Value========================");

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Market);
		Utility.ScrollToElement(res.CP_NewReservation_Market, driver);
		Wait.wait2Second();
		res.CP_NewReservation_Market.click();
		logger.info("========================Click on Market Field Value========================");
		test_steps.add("========================Click on Market Field Value========================");
		String market = "(//label[text()='Market']/preceding-sibling::div//ul/li//span[text()='"
				+ modifyMarketFieldinMarketSegment.trim() + "'])";

		Wait.WaitForElement(driver, market);
		driver.findElement(By.xpath(market)).click();

		logger.info("========================Modified Market Field Value========================");
		test_steps.add("========================Modified Market Field Value========================");
	}

	/*
	 * Modify Referral for existing reservation Added By Riddhi
	 */
	public void modifyReferralFieldinMarketSegment(WebDriver driver, String modifyReferralFieldinMarketSegment,
			ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
		Elements_CPReservation res = new Elements_CPReservation(driver);

		logger.info("========================Modifying Referral Field Value========================");
		test_steps.add("========================Modifying Referral Field Value========================");

		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Referral);
		Utility.ScrollToElement(res.CP_NewReservation_Referral, driver);
		Wait.wait2Second();
		res.CP_NewReservation_Referral.click();

		/*
		 * String referral =
		 * "//label[text()='Referral']/preceding-sibling::div//ul/li//span[contains(text(),'"
		 * + modifyReferralFieldinMarketSegment.trim() + "')]";
		 */
		String referral = "(//label[text()='Referral']/preceding-sibling::div//ul/li//span[text()='"
				+ modifyReferralFieldinMarketSegment.trim() + "'])";

		Wait.WaitForElement(driver, referral);
		driver.findElement(By.xpath(referral)).click();

		logger.info("========================Modified Referral Field Value========================");
		test_steps.add("========================Modified Referral Field Value========================");

		test_steps.add("Selected Referral as : " + modifyReferralFieldinMarketSegment);
		logger.info("Selected Referral as : " + modifyReferralFieldinMarketSegment);
	}

	// Added By Riddhi
	public void clickOnSaveMarketingInfo(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {

		Elements_ReservationV2 element = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.MARKETING_INFO_SAVE);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.MARKETING_INFO_SAVE), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.MARKETING_INFO_SAVE), driver);
		element.MARKETING_INFO_SAVE.click();

		test_steps.add("========================Saving Marketing Info========================");
		logger.info("========================Saving Marketing Info========================");
	}

	// Added by Riddhi
	public String getReservationNumber(WebDriver driver, ArrayList<String> test_steps) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		Wait.WaitForElement(driver, OR_ReservationV2.RESERVATION_NUMBER);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.RESERVATION_NUMBER), driver, 120);
		String confirmation = res.RESERVATION_NUMBER.getText();

		test_steps.add("Captured reservation confirmation number is : <b>" + confirmation + "</b>");
		logger.info("Confirmation Number : <b>" + confirmation + "</b>");
		return confirmation;
	}

	// Click on Setup >> Properties >> Options >> check/uncheck 'Required Market
	// Segment' check box -
	// Added By Riddhi
	public void checkUncheckRequireMarketSegmentOption(WebDriver driver, ArrayList<String> test_steps,
			String requiredMarketSegment) throws InterruptedException {
		Elements_SetUp_Properties eleSetup = new Elements_SetUp_Properties(driver);
		WebElement requiredMarketSegmentele = driver.findElement(By.xpath(OR.requiredMarketSegment));

		Wait.WaitForElement(driver, OR.requiredMarketSegment);
		Wait.waitForElementToBeClickable(By.xpath(OR.requiredMarketSegment), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.requiredMarketSegment), driver, 120);

		if (requiredMarketSegment == "check") {
			if (!requiredMarketSegmentele.isSelected()) {
				requiredMarketSegmentele.click();
			}
		}
		if (requiredMarketSegment == "uncheck") {
			if (requiredMarketSegmentele.isSelected()) {
				requiredMarketSegmentele.click();
			}
		}
		Wait.wait30Second();
	}

	// Click on Setup >> Properties >> Options >> check/uncheck 'Required Referrals'
	// check box
	// Added By Riddhi
	public void checkUncheckRequireReferralsOption(WebDriver driver, ArrayList<String> test_steps,
			String requiredMarketSegment) throws InterruptedException {
		Elements_SetUp_Properties eleSetup = new Elements_SetUp_Properties(driver);
		WebElement requiredReferralsEle = driver.findElement(By.xpath(OR.requiredReferrals));
		// Boolean isRequiredMarketSegment = requiredReferralsele.isSelected();

		Wait.WaitForElement(driver, OR.requiredReferrals);
		Wait.waitForElementToBeClickable(By.xpath(OR.requiredReferrals), driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR.requiredReferrals), driver, 120);

		if (requiredMarketSegment == "check") {
			if (!requiredReferralsEle.isSelected()) {
				requiredReferralsEle.click();
			}
		}
		if (requiredMarketSegment == "uncheck") {
			if (requiredReferralsEle.isSelected()) {
				requiredReferralsEle.click();
			}
			test_steps.add("=================== Uncheck the '/Required Referrals/' Checkbox ======================");
			logger.info("=================== UnCheck the '/Required Referrals/' Checkbox ======================");

		}
		Wait.wait30Second();
	}

	public HashMap<String, String> createReservationwithACF(WebDriver driver, ArrayList<String> test_steps,
			String sourceOfRes, String checkInDate, String checkOutDate, String adults, String children,
			String ratePlan, String promoCode, String roomClass, String roomClassAbb, String salutation,
			String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone, String email,
			String address1, String address2, String address3, String city, String country, String state,
			String postalCode, boolean isGuesProfile, String marketSegment, String referral, String paymentMethod,
			String cardNumber, String nameOnCard, String cardExpMonthAndYear, int additionalGuests, String roomNumber,
			boolean quote, String noteType, String noteSubject, String noteDescription, String taskCategory,
			String taskType, String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee,
			String taskStatus, String accountName, String accountType, String accountFirstName, String accountLastName,
			boolean isTaxExempt, String taxExemptID, boolean isSplit, boolean isRequiredMarketSegment,
			boolean isRequiredReferral, boolean isRequiredEmail) throws Exception {
		HashMap<String, String> data = new HashMap<>();
		Account account = new Account();
		Tapechart tc = new Tapechart();
		Navigation nav = new Navigation();
		Groups group = new Groups();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		int noOfRooms = guestFirstName.split("\\|").length;
		boolean missingMandatoryFields = false;

		if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
			test_steps.add(
					"<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
			click_NewReservation(driver, test_steps);
		}

		test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
		logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);

		if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")

				|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group")
				|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate")) {

			if (noOfRooms > 1) {
				if (isSplit) {
					searchDataForFindRoomsForSplitMRB(driver, test_steps, checkInDate, checkOutDate, adults, children,
							ratePlan, promoCode);
				} else {
					searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children,
							ratePlan, promoCode);
				}
				clickOnFindRooms(driver, test_steps);
				for (int i = 0; i < roomClass.split("\\|").length; i++) {

					int j = i + 1;
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
					try {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));
						data.put("RoomNumber" + j, roomNumbersProvided.get(i));
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
						data.put("RoomNumber" + j, roomNumbers.get(i));
					}
				}
			} else {
				searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);
				roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
				try {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
					data.put("RoomNumber", roomNumber);
				} catch (Exception e) {
					selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
					data.put("RoomNumber", roomNumbers.get(0));
				}
			}
			clickNext(driver, test_steps);
			/*
			 * if (guestFirstName.split("\\|").length > 1) {
			 * add_PrimaryRoom(driver,test_steps); add_AdditionalRoom(driver, test_steps); }
			 */
			clickOnCreateGuestButton(driver, test_steps);
			turnOnOffcreateGuestProfileToggle(driver, test_steps, isGuesProfile);

			if (noOfRooms > 1) {
				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, true);
				// clickOnGuestProfilePopupSaveButton(driver, test_steps);
				// clickOnCopyGuestInfoCheckboxforMRB(driver,test_steps);
				clickCopyGuestFromPrimaryRoom(driver, test_steps);
			} else {
				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
				enter_City(driver, test_steps, city);
				select_Country(driver, test_steps, country);
				select_State(driver, test_steps, state);
				enter_PostalCode(driver, test_steps, postalCode);
				enter_Phone(driver, test_steps, phoneNumber, altenativePhone);
				enter_Email(driver, test_steps, email);
				enter_Address(driver, test_steps, address1, address2, address3);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);

				// enter_GuestName(driver, test_steps, salutation, guestFirstName,
				// guestLastName, false);
				// clickOnGuestProfilePopupSaveButton(driver, test_steps);
			}

			if (Utility.validateString(accountName) && sourceOfRes.equalsIgnoreCase("Associate Account")) {
				enterAccount(driver, test_steps, accountName, accountType);
				if (Utility.validateString(paymentMethod)) {
					enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
							accountName);
				}
			} else {
				/*
				 * enter_City(driver, test_steps, city); select_Country(driver, test_steps,
				 * country); select_State(driver, test_steps,state); enter_PostalCode(driver,
				 * test_steps, postalCode); enter_Phone(driver, test_steps, phoneNumber,
				 * altenativePhone); enter_Email(driver, test_steps, email);
				 * enter_Address(driver, test_steps,address1, address2, address3);
				 * clickOnGuestProfilePopupSaveButton(driver, test_steps);
				 */
				if (Utility.validateString(paymentMethod)) {
					// cardNumber variable can be used as Gift Card number (If Payment method: Gift
					// Certificate, Reservation number if Payment Method: Reservation
					enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
							accountName);
				}
				enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
			}

			enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
			if (Utility.validateString(noteSubject)) {
				enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);			
			}
			if (Utility.validateString(taskCategory) && Utility.validateString(taskType) ) {
				if (verifyTaskSection(driver, test_steps)) {
					enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus);
				}
			}
			if( guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase(""))
			{	
				missingMandatoryFields = true;
			}
			else if( isRequiredMarketSegment && marketSegment.equalsIgnoreCase(""))
			{
				missingMandatoryFields = true;
			}
			else if( isRequiredReferral && referral.equalsIgnoreCase(""))
			{
				missingMandatoryFields = true;
			}
			else
			{
				missingMandatoryFields = false;
			}
			logger.info("Missing Mandatory Fields :"+missingMandatoryFields);
			Wait.wait25Second();
			if (quote) 
			{
				clickSaveQuote(driver, test_steps,missingMandatoryFields);
			} 
			else 
			{
				clickBookNow(driver, test_steps, missingMandatoryFields);
			}
			if(missingMandatoryFields)
			{
				data.put("requiredFieldValidation",res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
			}
			else
			{
				data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
				data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
				clickCloseReservationSavePopup(driver, test_steps);
				TripSummary tripSummary = getTripSummaryDetail(driver);
				data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
				data.put("Taxes", tripSummary.getTS_TAXES());
				data.put("requiredFieldValidation","");
			}
			
			return data;
		}

		if (additionalGuests > 0) {
			ArrayList<String> firstNames = new ArrayList<>();
			ArrayList<String> lastNames = new ArrayList<>();
			for (int i = 0; i < additionalGuests; i++) {
				firstNames.add(guestFirstName + i);
				lastNames.add(guestLastName + i);
			}
			enterAddMoreGuestInfoDetails(driver, test_steps, additionalGuests, firstNames, lastNames);
		}
		enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
		if (Utility.validateString(noteSubject)) {
			enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);
		}
		if (Utility.validateString(taskCategory) && Utility.validateString(taskType)) {
			if (verifyTaskSection(driver, test_steps)) {
				enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee,
						taskStatus);
			}
		}
		if (guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (isRequiredMarketSegment && marketSegment.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (isRequiredReferral && referral.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else {
			missingMandatoryFields = false;
		}
		if (quote) {
			clickSaveQuote(driver, test_steps, missingMandatoryFields);
		} else {
			clickBookNow(driver, test_steps, missingMandatoryFields);
		}
		if (missingMandatoryFields) {
			data.put("requiredFieldValidation", res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
		} else {
			data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
			data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
			clickCloseReservationSavePopup(driver, test_steps);
			TripSummary tripSummary = getTripSummaryDetail(driver);
			data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
			data.put("Taxes", tripSummary.getTS_TAXES());
			data.put("requiredFieldValidation", "");
		}

		return data;
	}

	
		
		public HashMap<String, String> createReservation_RV2(WebDriver driver, ArrayList<String> test_steps, String sourceOfRes,
				String checkInDate, String checkOutDate, String adults, String children, String ratePlan, String promoCode, 
				String roomClass, String roomClassAbb, String salutation, String guestFirstName, String guestLastName, 
				boolean isCopyGuest, String phoneNumber,
				String altenativePhone, String email, String address1, String address2, String address3, String city, 
				String country, String state, String postalCode, boolean isGuesProfile, String marketSegment, String referral, 
				String paymentMethod, String cardNumber, String nameOnCard, String cardExpMonthAndYear, int additionalGuests,
				String roomNumber, boolean quote, String noteType, String noteSubject, String noteDescription, String taskCategory, 
				String taskType, String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee, String taskStatus,
				String accountName, String accountType, String accountFirstName, String accountLastName, boolean isTaxExempt, 
				String taxExemptID) throws Exception 
		{
				
			HashMap<String, String> data = new HashMap<>();
			Account account = new Account();
			Tapechart tc = new Tapechart();
			Navigation nav = new Navigation();
			Groups group = new Groups();
			ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
			ArrayList<String> roomNumbers = new ArrayList<>();
			ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			int noOfRooms = guestFirstName.split("\\|").length;
			boolean missingMandatoryFields = false;
			if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
				test_steps.add("<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
				click_NewReservation(driver, test_steps);
			}			
			test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
			logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);			
			if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")
					|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group") 
					|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate") ) 				
			{
				if (sourceOfRes.equalsIgnoreCase("Associate Account")) {
					if (!account.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, false)) {
						account.createNewAccount(driver, test_steps, accountType, accountName, marketSegment, referral, accountFirstName,
								accountLastName, phoneNumber, altenativePhone, address1, address2, address3, email, city, state, postalCode);
					}else {
						test_steps.add("Account is already existing with <b>"+accountName+"</b> name");
						logger.info("Account is already existing with "+accountName+" name");
					}
					nav.ReservationV2_Backward(driver);
					click_NewReservation(driver, test_steps);
				}else if (sourceOfRes.equalsIgnoreCase("Account Reservation")) {
					test_steps.add("<b>******************* CREATING RESERVATION FROM ACCOUNT **********************</b>");
					account.clickNewReservationFromAccount(driver, test_steps, accountType, accountName, marketSegment,
							referral, accountFirstName, accountLastName, phoneNumber, altenativePhone, address1, address2,
							address3, email, city, state, postalCode, null);
				} else if (sourceOfRes.equalsIgnoreCase("Group")) {
					test_steps.add("<b>******************* CREATING RESERVATION FROM GROUP **********************</b>");
					nav.groups(driver);
					ArrayList<String> accountNumbers = new ArrayList<>();
					group.createGroupAccount(driver, test_steps, accountName, true, null, marketSegment, referral, accountFirstName, 
							accountLastName, phoneNumber, address1, city, state, country, postalCode, accountNumbers);
					group.click_GroupNewReservation(driver, test_steps);
				}
			if (noOfRooms > 1) 
			{
					searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);
					clickOnFindRooms(driver, test_steps);
					for (int i = 0; i < roomClass.split("\\|").length; i++) 
					{

						int j = i+1;
						roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
						try 
						{
							selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));						
							data.put("RoomNumber"+j, roomNumbersProvided.get(i));
						} catch (Exception e) 
						{
							selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
							data.put("RoomNumber"+j, roomNumbers.get(i));
						}
					}
				} 
				else 
				{
					searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);				
					clickOnFindRooms(driver, test_steps);
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
					try {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
						data.put("RoomNumber", roomNumber);					
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));				
						data.put("RoomNumber", roomNumbers.get(0));
					}
				}
				clickNext(driver, test_steps);
				if (!sourceOfRes.equalsIgnoreCase("Account Reservation") && !sourceOfRes.equalsIgnoreCase("Group") ) 
				{
					clickOnCreateGuestButton(driver, test_steps);
				}
			
				turnOnOffcreateGuestProfileToggle(driver, test_steps,isGuesProfile);
				
				if(noOfRooms > 1)
				{
					if (guestFirstName.split("\\|").length>1) 
					{
					enter_MailingAddress(driver, test_steps, salutation.split("\\|")[0], guestFirstName.split("\\|")[0], guestLastName.split("\\|")[0],
							phoneNumber,altenativePhone,email, accountName,accountType,  address1,  address2,  address3,  city,
							 country,  state,  postalCode);
					if (!sourceOfRes.equalsIgnoreCase("Account Reservation") && !sourceOfRes.equalsIgnoreCase("Group") ) 
					{
					clickOnGuestProfilePopupSaveButton(driver, test_steps);
					}
					if (isCopyGuest) 
					{
						//if copy flag is on, then copy guestinfo from primary room to other rooms
						clickCopyGuestFromPrimaryRoom(driver, test_steps);						
					} 
					else 
					{
					clickOnCreateGuestButton(driver, test_steps);
						turnOnOffcreateGuestProfileToggle(driver, test_steps, isGuesProfile);
						enter_Salutation(driver, test_steps, salutation.split("\\|")[1]);
						enter_GuestName(driver, test_steps, guestFirstName.split("\\|")[1],
								guestLastName.split("\\|")[1]);
						clickOnGuestProfilePopupSaveButton(driver, test_steps);
						Wait.wait1Second();
					}
				}
			} else {
				enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName, phoneNumber,
						altenativePhone, email, accountName, accountType, address1, address2, address3, city, country,
						state, postalCode);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);
				Wait.wait1Second();

				/*
				 * if(Utility.validateString(accountName)) { enter_GuestName_RV2(driver,
				 * test_steps, salutation, guestFirstName, guestLastName,
				 * false,accountName,sourceOfRes,accountType); }else { }
				 */
			}
			if (Utility.validateString(paymentMethod)) {
				// cardNumber variable can be used as Gift Card number (If Payment method: Gift
				// Certificate, Reservation number if Payment Method: Reservation
				enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
						accountName);
			}
			enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");

		}
		if (additionalGuests > 0) {
			ArrayList<String> firstNames = new ArrayList<>();
			ArrayList<String> lastNames = new ArrayList<>();
			for (int i = 0; i < additionalGuests; i++) {
				firstNames.add(guestFirstName + i);
				lastNames.add(guestLastName + i);
			}
			enterAddMoreGuestInfoDetails(driver, test_steps, additionalGuests, firstNames, lastNames);
		}
		enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
		if (Utility.validateString(noteSubject)) {
			enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);
		}
		if (Utility.validateString(taskCategory) && Utility.validateString(taskType)) {
			if (verifyTaskSection(driver, test_steps)) {
				enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee,
						taskStatus);
			}
		}

		/*
		 * Check Whether all mandatory fields are empty or not
		 */

		if (guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (marketSegment.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (referral.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else {
			missingMandatoryFields = false;
		}

		if (quote) {
			clickSaveQuote(driver, test_steps, missingMandatoryFields);
		} else {
			clickBookNow(driver, test_steps, missingMandatoryFields);
		}
		if (missingMandatoryFields) {
			data.put("requiredFieldValidation", res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
		} else {
			data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
			data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
			clickCloseReservationSavePopup(driver, test_steps);
			TripSummary tripSummary = getTripSummaryDetail(driver);
			data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
			data.put("Taxes", tripSummary.getTS_TAXES());
			data.put("requiredFieldValidation", "");
		}
		return data;
	}

	public void enter_GuestName_RV2(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName, boolean isPrimaryInfoCopy, String accountName,
			String sourceOfRes, String accountType) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		try {
			if (guestFirstName.split("\\|").length > 1) {
				// enter guest info for MRB reservation
				enter_Salutation(driver, test_steps, salutation.split("\\|")[0]);
				enter_GuestName(driver, test_steps, guestFirstName.split("\\|")[0], guestLastName.split("\\|")[0]);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);
				if (isPrimaryInfoCopy) {
					// if copy flag is on, then copy guestinfo from primary room to other rooms
					clickCopyGuestFromPrimaryRoom(driver, test_steps);
				} else {
					enter_AdditionalGuestNamesForMRB(driver, test_steps, salutation, guestFirstName, guestLastName);
				}
			} else {
				// Enter data for single reservation
				enter_Salutation(driver, test_steps, salutation);
				enter_GuestName(driver, test_steps, guestFirstName, guestLastName);
				if (Utility.validateString(accountName) && sourceOfRes.equalsIgnoreCase("Associate Account")) {
					enterAccount(driver, test_steps, accountName, accountType);
				}
				if (!sourceOfRes.equalsIgnoreCase("Associate Account")) {
					clickOnGuestProfilePopupSaveButton(driver, test_steps);
				}
			}

		} catch (Exception e) {
			logger.info(e);
		} catch (Error e) {
			e.printStackTrace();
			logger.info(e);
		}
	}

	public void takePayment_RV2(WebDriver driver, ArrayList<String> test_steps, boolean isChangePayAmount,
			String changedAmountValue, String notes, String paymentType, String cardNumber)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		clickTakePayment(driver, test_steps);
		enterAmountTakePayment(driver, test_steps, isChangePayAmount, changedAmountValue);
		if (paymentType.trim().equalsIgnoreCase("Gift Certificate")) {
			res.CP_NewReservation_GiftCertNumber.sendKeys(cardNumber);
			test_steps.add("Enter Gift Card Number : " + cardNumber);
			Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../.."),
					driver);
			driver.findElement(By.xpath("//b[contains(text(),'" + cardNumber.trim() + "')]/../../..")).click();
		}
		addNotesPayment(driver, notes);
		checkOutPaymentSetAsMainPaymentMethod(driver, test_steps, "No");
		clickPayTakePayment(driver, test_steps);
		closePaymentSuccessfullPopup(driver, test_steps);
	}

	private void setPaymentMethodAsAccount(WebDriver driver, String AccountType, Elements_CPReservation cpReservation) {
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_PaymentMethod), driver, 30);
		cpReservation.CP_NewReservation_PaymentMethod.click();
		String type = null;
		if (AccountType.contentEquals("Corp") || AccountType.contains("corp") || AccountType.contains("Corp")) {
			type = "Account (Corp/Member)";
		} else if (AccountType.contentEquals("Unit Owner") || AccountType.contains("Unit")
				|| AccountType.contains("unit owner")) {
			type = "Account (Unit Owner)";
		}
		String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'"
				+ type + "')]";
		Wait.waitForElementToBeVisibile(By.xpath(paymentMethod), driver, 30);
		driver.findElement(By.xpath(paymentMethod)).click();
	}

	public void searchAndSelectAccount(WebDriver driver, ArrayList<String> test_steps, String Account)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.GUEST_INFO_ACCOUNT), driver);
		res.GUEST_INFO_ACCOUNT.clear();
		res.GUEST_INFO_ACCOUNT.sendKeys(Account);
		test_steps.add("Enter Account : <b>" + Account + "</b>");
		logger.info("Enter Account : " + Account);
		Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + Account.trim() + "')]/../../.."), driver);
		driver.findElement(By.xpath("//b[contains(text(),'" + Account.trim() + "')]/../../..")).click();
	}

	public void verifyAssociatedAccountInGuestInfoAndStatusBar(WebDriver driver, ArrayList<String> test_steps,
			String accountName) {
		String guestInfoPath = "//span[@data-bind='text: accountInfo.name']";
		String statusBarPath = "//a[contains(@data-bind,'statusbarModel.account().name')]";

		Utility.customAssert(driver.findElement(By.xpath(statusBarPath)).getText().toUpperCase(),
				"(" + accountName.toUpperCase() + ")", true, "Successfully Verified Associated Account in Status Bar",
				"Failed to verify Associated Account in Status Bar", true, test_steps);
		Utility.customAssert(driver.findElement(By.xpath(guestInfoPath)).getText().toUpperCase(),
				accountName.toUpperCase(), true, "Successfully Verified Associated Account in Guest Info",
				"Failed to verify Associated Account in Guest Info", true, test_steps);
	}

	public void verifyTravelAgentAccountInMarketingInfo(WebDriver driver, ArrayList<String> test_steps,
			String accountName) {
		String guestInfoPath = "//a[contains(@data-bind,'marketTravelAgent().name')]";

		Utility.customAssert(driver.findElement(By.xpath(guestInfoPath)).getText().toUpperCase(),
				"(" + accountName.toUpperCase() + ")", true, "Successfully Verified Travel Agent Account",
				"Failed to verify Associated Travel Agent Account", true, test_steps);

	}

	public void enterTravelAgent(WebDriver driver, ArrayList<String> test_steps, String TravelAgent,
			boolean useTravelAgentInfo) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_TravelAgent);

		if (!(TravelAgent.equalsIgnoreCase("") || TravelAgent.isEmpty())) {
			res.CP_NewReservation_TravelAgent.sendKeys(TravelAgent);
			Wait.wait2Second();
			String account = "//b[contains(text(),'" + TravelAgent.trim() + "')]/../../..";
			Wait.WaitForElement(driver, account);
			driver.findElement(By.xpath(account)).click();
			if (useTravelAgentInfo) {
				String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'Yes')]";
				Wait.WaitForElement(driver, dataYes);
				driver.findElement(By.xpath(dataYes)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : Yes");
				Wait.wait5Second();
			} else {
				String dataYes = "//div[contains(text(),'Do you want to replace the guest info')]/following-sibling::div//button[contains(text(),'No')]";
				Wait.WaitForElement(driver, dataYes);
				driver.findElement(By.xpath(dataYes)).click();
				test_steps.add(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				logger.info(
						"Do you want to replace the guest info, payment method, marketing info and notes in this reservation with the information from the account? Clicking yes will save all the account info to the reservation. : No");
				Wait.wait5Second();
			}

		}
	}

	// Added By Riddhi
	public void searchDataForFindRoomsForSplitMRB(WebDriver driver, ArrayList<String> test_steps, String checkInDate,
			String checkOutDate, String adults, String children, String ratePlan, String promoCode) throws Exception {
		String[] checkin = checkInDate.split("\\|");
		String[] checkout = checkOutDate.split("\\|");
		String[] adu = adults.split("\\|");
		String[] child = children.split("\\|");
		String[] rate = ratePlan.split("\\|");
		logger.info(checkInDate);
		int size = adu.length;

		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CP_NewReservation_AddRoom), driver, 25);
		test_steps.add("MRB Size : " + size);
		logger.info("MRB size : " + size);

		for (int i = 1; i <= size; i++) {
			selectCheckInDate(driver, test_steps, checkin[i - 1], i);
			selectCheckOutDate(driver, test_steps, checkout[i - 1], i);
			enter_Adults(driver, test_steps, adu[i - 1], i);
			enter_Children(driver, test_steps, child[i - 1], i);

			// select_Rateplan(driver, test_steps, ratePlan, promoCode);
			if (Utility.validateString(promoCode) && promoCode.split("\\|").length == size) {
				String[] promo = promoCode.split("\\|");
				select_Rateplan1(driver, test_steps, rate[i - 1], promo[i - 1], i);
			} else {
				test_steps.add("selecting rate plan....." + rate[i - 1]);
				logger.info("selecting rate plan......" + rate[i - 1]);

				select_Rateplan1(driver, test_steps, rate[i - 1], null, i);
			}
			if (!(i == size)) {
				logger.info("i < size");
				clickOnAddARoomButton(driver, test_steps);
			}
			/*
			 * if(i == size) { logger.info("i == size"); select_Rateplan1(driver,
			 * test_steps, rate[i-1], null, i); }
			 */
		}
		splitRoomCheckbox(driver);

	}

	// Added By Riddhi
	public ArrayList<String> splitRoomCheckbox(WebDriver driver) throws InterruptedException {
		Elements_ReservationV2 element = new Elements_ReservationV2(driver);
		ArrayList<String> testSteps = new ArrayList<>();
		Wait.WaitForElement(driver, OR_ReservationV2.SPLIT_ROOM_CHKBOX);
		// Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SPLIT_ROOM_CHKBOX),
		// driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.SPLIT_ROOM_CHKBOX), driver);
		// Utility.ScrollToElement(element.SPLIT_ROOM_CHKBOX, driver);

		if (element.SPLIT_ROOM_CHKBOX.isSelected()) {
			testSteps.add("Verified 'Split-Room Reservation' checkbox already checked");
		} else {
			element.SPLIT_ROOM_CHKBOX.click();
			testSteps.add("Verified 'Split-Room Reservation' checkbox is checked");
		}

		return testSteps;
	}

	// Added By Riddhi
	public void clickEditStayInfoForMRBResV2(WebDriver driver, String roomIndex) {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		try {
			String StayInfo3DotxPath = OR_ReservationV2.STAY_INFO_THREE_DOTS + "[" + roomIndex + "]";
			WebElement webEleStayInfo3Dot = driver.findElement(By.xpath(StayInfo3DotxPath));

			Wait.waitForElementToBeClickable(By.xpath(StayInfo3DotxPath), driver);
			webEleStayInfo3Dot.click();
			Wait.wait2Second();

			Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS), driver, 5);
			logger.info("Clicked on Edit Stay Info - 3 Dot Icon");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Utility.clickThroughAction(driver, elements.EDIT_STAY_INFO);
				Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.CHANGE_STAY_DETAILS), driver, 5);
				logger.info("Clicked on Edit Stay Info");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	// Added By Riddhi
	// Find Room - Rate Plan Selection Drop Down - Select Rate plan
	public void select_Rateplan1(WebDriver driver, ArrayList<String> test_steps, String ratePlan, String promoCode,
			int count) throws Exception {
		String ratePlanxPath = OR_ReservationV2.RV2_RATE_PLAN_DROPDOWN + "[" + count + "]";
		Wait.WaitForElement(driver, ratePlanxPath);
		driver.findElement(By.xpath(ratePlanxPath));
		driver.findElement(By.xpath(ratePlanxPath)).click();
		driver.findElement(By.xpath(ratePlanxPath)).sendKeys(ratePlan);
		// driver.findElement(By.xpath(ratePlanxPath)).click();
		driver.findElement(By.xpath("(//b[contains(text(),'" + ratePlan + "')]/../../../..)[" + count + "]")).click(); // for
																														// mrb

		// driver.findElement(By.xpath("//label[contains(text(),'Rate Plan /
		// Promo')]/..//span/b[text()='Test Rate']")).click();

		test_steps.add("Selecting the rateplan as : <b>" + ratePlan + "</b>");
		logger.info("Selecting the rateplan : " + ratePlan);
		Wait.wait5Second();

		if (Utility.validateDate(promoCode)) {
			Wait.WaitForElement(driver, "(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]");
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]"))
					.clear();
			driver.findElement(By.xpath("(" + OR_ReservationV2.CP_NewReservation_PromoCode + ")[" + count + "]"))
					.sendKeys(promoCode);
			test_steps.add("Enter promocode as : <b>" + promoCode + "</b>");
			logger.info("Enter promocode : " + promoCode);
		}

	}

	// Added By Riddhi - Verify History Log for Refunded Payment
	public void verifyHistoryWithRefundedPayment(WebDriver driver, ArrayList<String> test_steps, String refundAmount,
			String CardNumber, String paymentMethod, String ExpiryDate, String NameOnCard)
			throws InterruptedException, ParseException {
		String str = null;
		String refundText = "Refunded payment for $";
		refundAmount = Utility.convertDecimalFormat(refundAmount);
		String fourDigitCardNo = "";
		String cardFormat = null;
		if (Utility.validateString(CardNumber)) {
			fourDigitCardNo = Utility.getCardNumberHidden(CardNumber);
			if (paymentMethod.equals("MC")) {
				cardFormat = paymentMethod + "-" + fourDigitCardNo + " " + ExpiryDate + "";
			} else if (paymentMethod.equals("Cash")) {
				cardFormat = paymentMethod;
			}
			if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa")) {
				str = refundText + refundAmount + " using (" + cardFormat + ")";
			} else if (paymentMethod.equalsIgnoreCase("Cash") || paymentMethod.equalsIgnoreCase("Cheque")) {
				str = refundText + refundAmount + " using " + cardFormat + "";
			}
		}
		if (paymentMethod.contains("Account")) {
			str = refundText + refundAmount + " using ";
		}
		String path = "//span[contains(text(),'" + str + "')]";
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed: To verify History Tab  Description");
		test_steps.add("'History Tab' Verified Description: <b>" + element.getText() + "</b>");
		logger.info("'History Tab' Verified Description : " + element.getText());
		Wait.wait1Second();
	}

	// Added By Riddhi
	// This method returns History Description based on History Search
	public String searchHistoryLogbyDesc(WebDriver driver, String searchText, int row, ArrayList<String> test_steps) {
		String searchDesc = "";

		try {
			Utility.customAssert(driver.findElement(By.id("txtHistorySearch")).isDisplayed() + "", true + "", true,
					"Successfully Verified History Search Feild is Displayed", "Failed To Verify Search Feild ", true,
					test_steps);
			driver.findElement(By.id("txtHistorySearch")).clear();
			driver.findElement(By.id("txtHistorySearch")).sendKeys(searchText);

			String tableColDesc = "//tbody[@data-bind='foreach:historyLogs']/tr[" + row
					+ "]/td/span[contains(@data-bind,'description')]";
			searchDesc = driver.findElement(By.xpath(tableColDesc)).getText();

		} catch (Exception e) {
			String path = "//td[contains(text(),'No history log')]";
			driver.findElement(By.xpath(path)).isDisplayed();
			assertTrue(true);
			test_steps.add("Successfully Verified Searched Text : No history log");
			logger.info("Successfully Verified Searched Text : No history log");
			e.printStackTrace();
		}
		return searchDesc;
	}

	public List<String> getHitoryDescription(WebDriver driver, String category) {
		List<String> historyDesc = new ArrayList<String>();
		// String path="//span[contains(@data-bind,'text:$data.categoryId') and
		// text()='"+category+"']/..//following-sibling::td//span[contains(@data-bind,'text:$data.description')]";
		String path = "//span[contains(@data-bind,'text:$data.categoryId') and contains(translate(normalize-space(text()),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"
				+ category.toUpperCase().trim()
				+ "')]/..//following-sibling::td//span[contains(@data-bind,'text:$data.description')]";
		if (Utility.isElementPresent(driver, By.xpath(path))) {
			List<WebElement> elements = driver.findElements(By.xpath(path));
			for (WebElement ele : elements) {
				historyDesc.add(ele.getText());
			}
		} else {
			logger.info("No Data ");
		}
		return historyDesc;
	}

	public void deleteAssociatedAccount(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		try {
			Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_Account_V2Delete);
			Utility.ScrollToViewElementINMiddle(driver, res.CP_NewReservation_Account_V2Delete);
			res.CP_NewReservation_Account_V2Delete.click();
		} catch (Exception e) {
			Utility.scrollAndClick(driver, By.xpath(OR_Reservation.CP_NewReservation_Account_V2Delete));
		}
		test_steps.add("Delete Account ");
		logger.info("Delete Account ");
	}

	private void verifyTravelAgentIsAssociatedInReservation(WebDriver driver, ArrayList<String> test_steps,
			String Account) {
		String acc = "//a[contains(@data-bind,'marketTravelAgent')]";
		Wait.waitForElementToBeVisibile(By.xpath(acc), driver);
		String accName = driver.findElement(By.xpath(acc)).getText();
		if (accName.contains(Account)) {
			test_steps.add("Travel Agent successfully associated : " + Account);
			logger.info("Travel Agent successfully associated : " + Account);
		}
	}

	public void selectTravelAgent_RV2(WebDriver driver, ArrayList<String> test_steps, String Account)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		if (Utility.validateString(Account)) {
			res.CP_NewReservation_TravelAgent_V2.sendKeys(Account);
			test_steps.add("Enter Travel Agent : <b>" + Account + "</b>");
			logger.info("Enter Travel Agent : " + Account);
			Wait.wait2Second();
			Wait.waitForElementToBeVisibile(By.xpath("//b[contains(text(),'" + Account.trim() + "')]/../../.."),
					driver);
			driver.findElement(By.xpath("//b[contains(text(),'" + Account.trim() + "')]")).click();
			clickOnYesButtonOnGuestInfoReplaceForAccount(driver, test_steps);
			verifyTravelAgentIsAssociatedInReservation(driver, test_steps, Account);
		} else {
			logger.info("No Account");
		}

	}

	public void deleteAssociatedTravelAgent(WebDriver driver, ArrayList<String> test_steps)
			throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_NewReservation_TravelAgentDelete_V2);
		Utility.ScrollToViewElementINMiddle(driver, res.CP_NewReservation_TravelAgentDelete_V2);
		res.CP_NewReservation_TravelAgentDelete_V2.click();
		test_steps.add("Delete Travel Agent Account ");
		logger.info("Delete Travel Agent Account ");
	}

	public void clickEditPaymentMethod(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Utility.ScrollToViewElementINMiddle(driver, res.RESERVATIONV2_PAYMENTMETHOD_EDIT);
		res.RESERVATIONV2_PAYMENTMETHOD_EDIT.click();
		test_steps.add("Click on Edit Payment Method Icon");
		logger.info("Click on Edit Payment Method Icon");
	}

	public void clickDeletePaymentMethod(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Utility.ScrollToViewElementINMiddle(driver, res.RESERVATIONV2_PAYMENTMETHOD_Delete);
		res.RESERVATIONV2_PAYMENTMETHOD_Delete.click();
		test_steps.add("Click on Delete Payment Method ");
		logger.info("Click on Delete Payment Method ");
	}

	public void associatAccount_RV2(WebDriver driver, ArrayList<String> test_steps, String Account, String AccountType,
			String accountNo) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);

		if (Utility.validateString(Account)) {
			Utility.ScrollToViewElementINMiddle(driver, res.CP_NewReservation_Account_V2);
			res.CP_NewReservation_Account_V2.click();
			if (AccountType.equals("Corp")) {
				res.CP_NewReservation_Account_V2.sendKeys(Account);
				test_steps.add("Enter Account : <b>" + Account + "</b>");
				logger.info("Enter Account : " + Account);
				Wait.wait2Second();
				Utility.clickThroughAction(driver, driver.findElement(
						By.xpath("//ul[contains(@class,'autocomplete')]//span[contains(text(),'" + accountNo + "')]")));
			} else if (AccountType.equals("Unit")) {
				String fourCharcter = Account.substring(0, 5);
				res.CP_NewReservation_Account_V2.sendKeys(fourCharcter);
				test_steps.add("Enter Account : <b>" + Account + "</b>");
				logger.info("Enter Account : " + Account);
				Wait.wait2Second();
				Utility.clickThroughAction(driver, driver.findElement(
						By.xpath("//ul[contains(@class,'autocomplete')]//span[contains(text(),'" + accountNo + "')]")));
			}
			clickOnYesButtonOnGuestInfoReplaceForAccount(driver, test_steps);
			clickOnYesButtonOnPoliciesApplicableForAccount(driver, test_steps, "");
			verifyAccountIsAssociatedInReservation(driver, test_steps, Account);
		} else {
			logger.info("No Account");
		}
	}

	public HashMap<String, String> createReservation_WithDetails(WebDriver driver, ArrayList<String> test_steps,
			String sourceOfRes, String checkInDate, String checkOutDate, String adults, String children,
			String ratePlan, String promoCode, String roomClass, String roomClassAbb, String salutation,
			String guestFirstName, String guestLastName, String phoneNumber, String altenativePhone, String email,
			String address1, String address2, String address3, String city, String country, String state,
			String postalCode, boolean isGuesProfile, String marketSegment, String referral, String paymentMethod,
			String cardNumber, String nameOnCard, String cardExpMonthAndYear, int additionalGuests, String roomNumber,
			boolean quote, String noteType, String noteSubject, String noteDescription, String taskCategory,
			String taskType, String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee,
			String taskStatus, String accountName, String accountType, String accountFirstName, String accountLastName,
			boolean isTaxExempt, String taxExemptID, String roomNO, boolean isSplit, boolean copyPrimaryGuest)
			throws Exception {

		HashMap<String, String> data = new HashMap<>();
		Account account = new Account();
		Tapechart tc = new Tapechart();
		Navigation nav = new Navigation();
		Groups group = new Groups();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		int noOfRooms = guestFirstName.split("\\|").length;
		boolean missingMandatoryFields = false;
		if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
			test_steps.add(
					"<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
			click_NewReservation(driver, test_steps);
		}
		test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
		logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);
		if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")
				|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group")
				|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate")) {
			if (sourceOfRes.equalsIgnoreCase("Associate Account")) {
				if (!account.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, false)) {
					account.createNewAccount(driver, test_steps, accountType, accountName, marketSegment, referral,
							accountFirstName, accountLastName, phoneNumber, altenativePhone, address1, address2,
							address3, email, city, state, postalCode);
				} else {
					test_steps.add("Account is already existing with <b>" + accountName + "</b> name");
					logger.info("Account is already existing with " + accountName + " name");
				}
				nav.ReservationV2_Backward(driver);
				click_NewReservation(driver, test_steps);
			} else if (sourceOfRes.equalsIgnoreCase("Account Reservation")) {
				test_steps.add("<b>******************* CREATING RESERVATION FROM ACCOUNT **********************</b>");
				account.clickNewReservationFromAccount(driver, test_steps, accountType, accountName, marketSegment,
						referral, accountFirstName, accountLastName, phoneNumber, altenativePhone, address1, address2,
						address3, email, city, state, postalCode, null);
			} else if (sourceOfRes.equalsIgnoreCase("Group")) {
				test_steps.add("<b>******************* CREATING RESERVATION FROM GROUP **********************</b>");
				nav.groups(driver);
				ArrayList<String> accountNumbers = new ArrayList<>();
				group.createGroupAccount(driver, test_steps, accountName, true, null, marketSegment, referral,
						accountFirstName, accountLastName, phoneNumber, address1, city, state, country, postalCode,
						accountNumbers);
				group.click_GroupNewReservation(driver, test_steps);
			}
			if (noOfRooms > 1) {
				searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				if (isSplit == true) {
					splitRoomCheckbox(driver);
				}
				clickOnFindRooms(driver, test_steps);

				for (int i = 0; i < roomClass.split("\\|").length; i++) {

					int j = i + 1;
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
					try {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));
						data.put("RoomNumber" + j, roomNumbersProvided.get(i));
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
						data.put("RoomNumber" + j, roomNumbers.get(i));
					}
				}
			} else {
				searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan,
						promoCode);
				clickOnFindRooms(driver, test_steps);

				roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
				try {
					if (roomNO.equalsIgnoreCase("Unassigned")) {
						selectRoomToReserve(driver, test_steps, roomClass, "Unassigned");
						data.put("RoomNumber", "Unassigned");
					} else {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
						data.put("RoomNumber", roomNumber);
					}
				} catch (Exception e) {
					if (roomNO.equalsIgnoreCase("Unassigned")) {
						selectRoomToReserve(driver, test_steps, roomClass, "Unassigned");
						data.put("RoomNumber", "Unassigned");
					} else {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
						data.put("RoomNumber", roomNumbers.get(0));
					}

				}
			}
			clickNext(driver, test_steps);
			/*
			 * if (guestFirstName.split("\\|").length > 1) {
			 * add_PrimaryRoom(driver,test_steps); add_AdditionalRoom(driver, test_steps); }
			 */
			if (!sourceOfRes.equalsIgnoreCase("Account Reservation") && !sourceOfRes.equalsIgnoreCase("Group")) {
				clickOnCreateGuestButton(driver, test_steps);
			}

			turnOnOffcreateGuestProfileToggle(driver, test_steps, isGuesProfile);

			if (noOfRooms > 1) {
				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, copyPrimaryGuest);
				// clickOnCopyGuestInfoCheckboxforMRB(driver,test_steps);
				if (copyPrimaryGuest) {
					clickCopyGuestFromPrimaryRoom(driver, test_steps);
				}
			} else {

				enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
				enter_City(driver, test_steps, city);
				select_Country(driver, test_steps, country);
				select_State(driver, test_steps, state);
				enter_PostalCode(driver, test_steps, postalCode);
				enter_Phone(driver, test_steps, phoneNumber, altenativePhone);
				enter_Email(driver, test_steps, email);
				enter_Address(driver, test_steps, address1, address2, address3);
				clickOnGuestProfilePopupSaveButton(driver, test_steps);

			}

			if (Utility.validateString(paymentMethod)) {
				// cardNumber variable can be used as Gift Card number (If Payment method: Gift
				// Certificate, Reservation number if Payment Method: Reservation
				enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear,
						accountName);
			}
			if (!sourceOfRes.equalsIgnoreCase("Account Reservation") && !sourceOfRes.equalsIgnoreCase("Group")) {
				enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
			}

		}
		if (additionalGuests > 0) {
			ArrayList<String> firstNames = new ArrayList<>();
			ArrayList<String> lastNames = new ArrayList<>();
			for (int i = 0; i < additionalGuests; i++) {
				firstNames.add(guestFirstName + i);
				lastNames.add(guestLastName + i);
			}
			enterAddMoreGuestInfoDetails(driver, test_steps, additionalGuests, firstNames, lastNames);
		}
		enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
		if (Utility.validateString(noteSubject)) {
			enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);
		}
		if (Utility.validateString(taskCategory) && Utility.validateString(taskType)) {
			if (verifyTaskSection(driver, test_steps)) {
				enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee,
						taskStatus);
			}
		}

		/*
		 * Check Whether all mandatory fields are empty or not
		 */

		if (guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (marketSegment.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else if (referral.equalsIgnoreCase("")) {
			missingMandatoryFields = true;
		} else {
			missingMandatoryFields = false;
		}

		if (quote) {
			clickSaveQuote(driver, test_steps, missingMandatoryFields);
		} else {
			clickBookNow(driver, test_steps, missingMandatoryFields);
		}
		if (missingMandatoryFields) {
			data.put("requiredFieldValidation", res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
		} else {
			data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
			data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
			clickCloseReservationSavePopup(driver, test_steps);
			TripSummary tripSummary = getTripSummaryDetail(driver);
			data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
			data.put("Taxes", tripSummary.getTS_TAXES());
			data.put("requiredFieldValidation", "");
		}

		/*
		 * if(!guestFirstName.equalsIgnoreCase("") &&
		 * !guestLastName.equalsIgnoreCase("") && !marketSegment.equalsIgnoreCase("") &&
		 * !referral.equalsIgnoreCase("")) { if (quote) { clickSaveQuote(driver,
		 * test_steps); } else { clickBookNow(driver, test_steps); }
		 * data.put("ReservationNumber", get_ReservationConfirmationNumber(driver,
		 * test_steps)); data.put("ReservationStatus", get_ReservationStatus(driver,
		 * test_steps)); clickCloseReservationSavePopup(driver, test_steps); TripSummary
		 * tripSummary = getTripSummaryDetail(driver); data.put("RoomCharges",
		 * tripSummary.getTS_ROOM_CHARGE()); data.put("Taxes",
		 * tripSummary.getTS_TAXES()); data.put("requiredFieldValidation",""); } else {
		 * if (quote) { clickSaveQuotewithMissingMandatoryFields(driver, test_steps); }
		 * else { clickSaveReservationwithMissingMandatoryFields(driver, test_steps); }
		 * 
		 * data.put("requiredFieldValidation",res.
		 * VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText()); }
		 */

		return data;
	}

	public void remove_PaymentMethod(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);

		clickEditPaymentMethod(driver, test_steps);

		Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_PaymentMethod);
		Utility.ScrollToElement(res.CP_NewReservation_PaymentMethod, driver);
		res.CP_NewReservation_PaymentMethod.click();

		String paymentMethod = "//label[text()='Payment Method']/preceding-sibling::div//ul/li//span[contains(text(),'--Select--')]";
		Wait.WaitForElement(driver, paymentMethod);
		driver.findElement(By.xpath(paymentMethod)).click();
		test_steps.add("Removed PaymentType : ");
		logger.info("Removed PaymentType : ");

		if (!res.CP_NewReservation_SameAsMailingAddress.isSelected()) {
			driver.findElement(By.xpath("//small[contains(text(),'Address')]/../../following-sibling::div/label"))
					.click();
		}
		clickOnSavePaymentInfo(driver, test_steps);

	}

	public HashMap<String, String> calculateTaxes(String oneDayRoomCharge, ArrayList<String> updatedTaxName,
			ArrayList<String> intaxValue, ArrayList<String> categoryTaxs) {
		ArrayList<String> calcTaxes = new ArrayList<String>();
		HashMap<String, String> taxVal = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < intaxValue.size(); i++) {
			if (categoryTaxs.get(i).equalsIgnoreCase("percent")) {
				Double result = (double) Math
						.round(Double.parseDouble(oneDayRoomCharge) * Double.parseDouble(intaxValue.get(i))) / 100;

				calcTaxes.add(df.format(result));
			} else {
				calcTaxes.add(intaxValue.get(i));
			}
			taxVal.put(updatedTaxName.get(i), calcTaxes.get(i));
		}
		return taxVal;
	}

	public String calculateAdjustmentFee(WebDriver driver, String feeCategory, String roomCharge,
			String feeCategoryValue) {
		String fee = null;
		if (feeCategory.equalsIgnoreCase("percent")) {
			Double result = (double) Math.round(Double.parseDouble(roomCharge) * Double.parseDouble(feeCategoryValue))
					/ 100;
			fee = String.valueOf(result);
		} else if (feeCategory.equalsIgnoreCase("flat")) {
			fee = String.valueOf(feeCategoryValue);
			}
			return fee;
			}

	

	// Added By Riddhi - Verify History Log For Different Payment methods
	public void verifyHistoryLogForDifferentPayments(WebDriver driver, ArrayList<String> test_steps, String amount,
			String paymentType, String paymentMethod) {
		String str = null;
		amount = Utility.convertDecimalFormat(amount);
		// amount = amount+"";
		if (paymentMethod.equalsIgnoreCase("MC") || paymentMethod.equalsIgnoreCase("Visa")
				|| paymentMethod.equalsIgnoreCase("Discover")) {
			str = "Captured payment for $" + amount + " using (" + paymentType + ")";
		} else if (paymentMethod.equalsIgnoreCase("Cash") || paymentMethod.equalsIgnoreCase("Check")
				|| paymentMethod.equalsIgnoreCase("Reservation")) {
			str = "Made a payment $" + amount + " using " + paymentType + "";
		} else if (paymentMethod.equalsIgnoreCase("Deposit")) {
			str = "Made a deposit payment of $" + amount + " using " + paymentType + "";
		} else if (paymentMethod.equalsIgnoreCase("Log As External Payment")) {
			str = "Made a payment $" + amount + " using (" + paymentType + ")";
		}

		String path = "//span[contains(text(),'" + str + "')]";
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed: To verify History Tab  Description");
		test_steps.add("'History Tab' Verified Description: <b>" + element.getText() + "</b>");
		logger.info("'History Tab' Verified Description : " + element.getText());
	}

	public void clickFolioPayButton(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 elements = new Elements_ReservationV2(driver);
		ReservationV2 reservationPage = new ReservationV2();
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.guestFolioPayButton), driver);
		Utility.ScrollToElement(elements.guestFolioPayButton, driver);
		elements.guestFolioPayButton.click();
		logger.info("Clicked on pay button");
		test_steps.add("Clicked on pay button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.takePaymentAmountInput), driver);
	}

	public void enterGuestLastNameWhileCopy(WebDriver driver, ArrayList<String> test_steps, String GuestName) {
		String guest = "(//label[text()='Guest ']/../../../../following-sibling::div//input)[2]";
		Wait.WaitForElement(driver, guest);
		driver.findElement(By.xpath(guest)).sendKeys(GuestName);
		test_steps.add("Guest First Name : <b>" + GuestName + "</b>");
		logger.info("Guest First Name : <b>" + GuestName + "</b>");
	}

	public void enterGuestNameWhileCopy(WebDriver driver, ArrayList<String> test_steps, String GuestName) {
		String guest = "(//label[text()='Guest ']/preceding-sibling::input)[2]";
		Wait.WaitForElement(driver, guest);
		driver.findElement(By.xpath(guest)).sendKeys(GuestName);
		test_steps.add("Guest First Name : <b>" + GuestName + "</b>");
		logger.info("Guest First Name : <b>" + GuestName + "</b>");
	}

	public ArrayList<String> clickBookNow_CopyRes(WebDriver driver, ArrayList<String> testSteps)
			throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		String CP_NewReservation_Book = "//button//span[text()='Book Now']";
		try {
			Utility.ScrollToElement(driver.findElement(By.xpath(CP_NewReservation_Book)), driver);
			driver.findElement(By.xpath(CP_NewReservation_Book)).click();
		} catch (Exception e) {
			CP_NewReservation_Book = "//button//span[text()='Book Now']";
			Utility.ScrollToElement(driver.findElement(By.xpath(CP_NewReservation_Book)), driver);
			driver.findElement(By.xpath(CP_NewReservation_Book)).click();
		}
		logger.info("Click on Book Now");

		try {
			// Wait.WaitForElementUsingClassName(driver,
			// OR_ReservationV2.Toaster_Message_Copy);
			// Utility.app_logs.info(res.Toaster_Message_Copy.getText());
			// testSteps.add("Toaster message: " + res.Toaster_Message_Copy.getText());
			Wait.waitForElementToBeClickable(By.className(OR_ReservationV2.Toaster_Message_Close_Copy), driver);
			res.Toaster_Message_Close_Copy.click();

		} catch (Exception e) {
			// TODO: handle exception
		}

		testSteps.add("Click on Book Now");
		logger.info("Click on Book Now");
		String loading = "(//div[@class='ir-add-on-modal-reference-div'])[2]";
		int count = 0;
		try {
			while (true) {
				if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
					break;
				} else if (count == 60) {
					break;
				} else {
					count++;
					Wait.wait1Second();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		logger.info("Waited to loading symbol");
		return testSteps;

	}

	public void clickOnGuestProfilePopupSaveButtonWhileCopy(WebDriver driver, ArrayList<String> test_steps)
			throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_PROFILE_SAVE_COPY);
		try {
			Utility.ScrollToElement(res.GUEST_PROFILE_SAVE_COPY, driver);
			res.GUEST_PROFILE_SAVE.click();
			Wait.wait5Second();
		} catch (Exception e) {
			Utility.scrollAndClick(driver, By.xpath(OR_ReservationV2.GUEST_PROFILE_SAVE_COPY));
		}

		try {
			// Wait.WaitForElementUsingClassName(driver,
			// OR_ReservationV2.Toaster_Message_Copy);
			// logger.info(res.Toaster_Message_Copy.getText());
			// test_steps.add("Toaster message: " + res.Toaster_Message_Copy.getText());
			Wait.waitForElementByXpath(driver, OR_ReservationV2.Toaster_Message_Close_Copy);
			res.Toaster_Message_Close_Copy.click();

		} catch (Exception e) {
			// TODO: handle exception
			Utility.scrollAndClick(driver, By.xpath(OR_ReservationV2.Toaster_Message_Close_Copy));
		}

		Wait.wait5Second();
		test_steps.add("====== Clicking on Save Button on Guest Profile Popup on New Reservation Page======");
		logger.info("====== Clicking on Save Button on Guest Profile Popup on New Reservation Page======");

	}

	public void enterGuestDetailsWhileCopy(WebDriver driver, ArrayList<String> test_steps, String salutation,
			String guestFirstName, String guestLastName) throws Exception {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		try {

			Utility.ScrollToElement(res.CP_NewReservation_GuestSalutation_COPY, driver);
			res.CP_NewReservation_GuestSalutation_COPY.click();
			String sal = null;
			sal = "(//div[contains(@data-bind,'isCreateGuestProfileModal')]//span[contains(text(),'" + salutation
					+ "')])[3]";// "//span[contains(text(),'" + salutation + "')]/../..";
			Wait.wait2Second();
			Utility.app_logs.info("sal: " + sal);
			Wait.WaitForElement(driver, sal);
			driver.findElement(By.xpath(sal)).click();
		} catch (Exception ex) {
			logger.info(ex);
		}
		enterGuestNameWhileCopy(driver, test_steps, guestFirstName);
		enterGuestLastNameWhileCopy(driver, test_steps, guestLastName);
		clickOnGuestProfilePopupSaveButtonWhileCopy(driver, test_steps);
		clickBookNow_CopyRes(driver, test_steps);
		clickCloseReservationSavePopup(driver, test_steps);
	}

	public HashMap<String, String> createReservation_WithGuestProfile(WebDriver driver, ArrayList<String> test_steps,
			String checkInDate, String checkOutDate, String adults, String children, String ratePlan, String promoCode,
			String roomClass, String roomClassAbb, String roomNumber, String roomNO, String guestProfileName,
			boolean isSelectProfile, boolean isReplaceDetail) throws Exception {

		HashMap<String, String> data = new HashMap<>();
		Account account = new Account();
		Tapechart tc = new Tapechart();
		Navigation nav = new Navigation();
		Groups group = new Groups();
		ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
		ArrayList<String> roomNumbers = new ArrayList<>();
		ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		click_NewReservation(driver, test_steps);
		searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);
		clickOnFindRooms(driver, test_steps);
		roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
		try {
			if (roomNO.equalsIgnoreCase("Unassigned")) {
				selectRoomToReserve(driver, test_steps, roomClass, "Unassigned");
				data.put("RoomNumber", "Unassigned");
			} else {
				selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
				data.put("RoomNumber", roomNumber);
			}
		} catch (Exception e) {
			if (roomNO.equalsIgnoreCase("Unassigned")) {
				selectRoomToReserve(driver, test_steps, roomClass, "Unassigned");
				data.put("RoomNumber", "Unassigned");
			} else {
				selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));
				data.put("RoomNumber", roomNumbers.get(0));
			}
		}
		clickNext(driver, test_steps);
		search_GuestProfile(driver, guestProfileName, test_steps, isSelectProfile, isReplaceDetail);
		clickOnGuestProfilePopupSaveButton(driver, test_steps);
		clickBookNow(driver, test_steps, false);
		data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
		data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
		clickCloseReservationSavePopup(driver, test_steps);
		TripSummary tripSummary = getTripSummaryDetail(driver);
		data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
		data.put("Taxes", tripSummary.getTS_TAXES());
		data.put("requiredFieldValidation", "");
		return data;

	}

	public void search_GuestProfile(WebDriver driver, String guestProfileName, ArrayList<String> test_steps,
			boolean isSelectProfile, boolean isReplaceDetail) throws InterruptedException {
		Elements_ReservationV2 ele = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.GUEST_PROFILE_SEARCH);
		ele.GUEST_PROFILE_SEARCH.sendKeys(guestProfileName);
		logger.info("Entered the Guest Name for basic search");
		// Utility.clickThroughJavaScript(driver, ele.GUEST_PROFILE_SEARCH);
		// logger.info("Clicked on Search Button");
		String suggestionPath = "//span/b[contains(text(),'" + guestProfileName + "')]";
		Wait.WaitForElement(driver, suggestionPath);
		if (driver.findElements(By.xpath(suggestionPath)).size() > 0) {
			Utility.customAssert(driver.findElement(By.xpath(suggestionPath)).isDisplayed() + "", "true", true,
					"Successfully Verified Accout Suggestion Displayed on Search",
					"Failed to Verify Accout Suggestion not Displayed on Search", true, test_steps);
			driver.findElement(By.xpath(suggestionPath)).click();
		}

		if (isReplaceDetail) {
			try {

				String policy = "//div[contains(text(),'Would you also like to replace the guest info')]/../..//button[text()='Yes']";
				if (driver.findElements(By.xpath(policy)).size() > 0) {
					Wait.WaitForElement(driver, policy);
					driver.findElement(By.xpath(policy)).click();
					test_steps.add("Based on the changes made, new policies are applicable.? : yes");
					logger.info("Based on the changes made, new policies are applicable.? : yes");
					String loading = "(//div[@class='ir-loader-in'])[2]";
					int counter = 0;
					while (true) {
						if (!driver.findElement(By.xpath(loading)).isDisplayed()) {
							break;
						} else if (counter == 3) {
							break;
						} else {
							counter++;
							Wait.wait2Second();
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			try {
				Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(
						"//div[contains(text(),'Would you also like to replace the guest info')]/../..//button[text()='No']")));
				test_steps.add("Replace Guest Info Detail No Click");
				logger.info("Replace Guest Info Detail No Click");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		waitForSweetLoading(driver);
		Wait.wait2Second();
		String primaryGuestPopUp = "//h4/span[contains(text(),'Primary Guest')]";
		Wait.WaitForElement(driver, primaryGuestPopUp);
	}

	public void verifyHistoryWithCapturedPayment(WebDriver driver, ArrayList<String> test_steps, String amount,
			String paymentType, String paymentMethod) {
		String str = null;
		if (paymentMethod.equalsIgnoreCase("MC")) {
			str = "Captured payment for $" + amount + " using (" + paymentType + ")";
		} else if (paymentMethod.equalsIgnoreCase("Cash")) {
			str = "Made a payment $" + amount + " using " + paymentType + "";
		}
		// String path = "//span[contains(text(),'" + str + "')]";
		String path = "//span[contains(@data-bind,'text:$data.description')]";
		WebElement element = driver.findElement(By.xpath(path));
		assertTrue(element.isDisplayed(), "Failed: To verify History Tab  Description");
		test_steps.add("'History Tab' Verified Description: <b>" + element.getText() + "</b>");
		logger.info("'History Tab' Verified Description : " + element.getText());

	}

	public String takePayment(WebDriver driver, ArrayList test_steps, String PaymentType, String CardNumber,
			String NameOnCard, String CardExpDate, String TakePaymentType, String IsChangeInPayAmount,
			String ChangedAmountValue, String IsSetAsMainPaymentMethod, String AddPaymentNotes,
			boolean ispaymentTypeVerify, ArrayList<String> typeList, boolean isSuccessfullVerified)
			throws InterruptedException {
		test_steps.add("******************* Making payment **********************");
		logger.info("******************* Making payment **********************");
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment);

		Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_Amount);
		// String amount =getAmountFromPaymentScreen(driver);
		String amount = res.CP_Reservation_Folio_TakePayment_Amount.getText().trim();
		logger.info("amount : " + amount);
		test_steps.add("Amount before Pay : " + amount);
		logger.info("Amount before Pay : " + amount);

		if (Utility.validateString(PaymentType)) {
			Utility.ScrollToElement(res.CP_Reservation_Folio_TakePayment_PaymentButton, driver);
			Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_PaymentButton);
			Wait.wait10Second();
			res.CP_Reservation_Folio_TakePayment_PaymentButton.click();

			String CCNumber = "//h4[text()='Take Payment']/../..//label[text()='CARD NUMBER']/following-sibling::div/input";
			String name = "//h4[text()='Take Payment']/../..//label[text()='NAME ON CARD']/following-sibling::div/input";
			String exp = "//h4[text()='Take Payment']/../..//label[contains(text(),'EXPIRY')]/following-sibling::div/input";

			if (PaymentType.equalsIgnoreCase("Swipe")) {
				String CCCard = "(//h4[text()='Take Payment']/../..//label[text()='CARD NUMBER']/following-sibling::div/input)[2]";
				String swipe = "//h4[text()='Take Payment']/../..//button[text()='SWIPE']";
				Wait.WaitForElement(driver, swipe);
				driver.findElement(By.xpath(swipe)).click();
				test_steps.add("Clicking on Swipe");
				logger.info("Clicking on Swipe");
				Wait.WaitForElement(driver, CCCard);
				driver.findElement(By.xpath(CCCard)).sendKeys(CardNumber);
				test_steps.add("Enter Card Number : " + CardNumber);
				logger.info("Enter Card Number : " + CardNumber);
			} else {
				String payment = "//h4[text()='Take Payment']/../..//label[text()='PAYMENT METHOD']/..//div/div//span[text()='"
						+ PaymentType.trim() + "']";
				Wait.WaitForElement(driver, payment);
				driver.findElement(By.xpath(payment)).click();
				test_steps.add("Select Payment Type as : " + PaymentType);
				logger.info("Select Payment Type as : " + PaymentType);
				if (PaymentType.equalsIgnoreCase("MC") || PaymentType.equalsIgnoreCase("Visa")
						|| PaymentType.equalsIgnoreCase("Amex") || PaymentType.equalsIgnoreCase("Discover")) {

					Wait.WaitForElement(driver, CCNumber);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					String CC = (String) js.executeScript("return arguments[0].value",
							driver.findElement(By.xpath(CCNumber)));
					logger.info(CC);
					if (CC.isEmpty() || CC.equalsIgnoreCase("")) {
						Wait.WaitForElement(driver, CCNumber);
						driver.findElement(By.xpath(CCNumber)).sendKeys(CardNumber);
						test_steps.add("Enter Card Number : " + CardNumber);
						logger.info("Enter Card Number : " + CardNumber);
						Wait.WaitForElement(driver, name);
						driver.findElement(By.xpath(name)).sendKeys(NameOnCard);
						test_steps.add("Enter Card Name : " + NameOnCard);
						logger.info("Enter Card Name : " + NameOnCard);
						Wait.WaitForElement(driver, exp);
						driver.findElement(By.xpath(exp)).sendKeys(CardExpDate);
						test_steps.add("Enter Card Exp Date : " + CardExpDate);
						logger.info("Enter Card Exp Date : " + CardExpDate);
					}
				}
			}
		}
		if (IsChangeInPayAmount.equalsIgnoreCase("Yes")) {
			test_steps.add("Change the pay amount value : Yes");
			logger.info("Change the pay amount value : Yes");
			res.CP_Reservation_Folio_TakePayment_Amount.clear();
			res.CP_Reservation_Folio_TakePayment_Amount.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			res.CP_Reservation_Folio_TakePayment_Amount.sendKeys(ChangedAmountValue);
			test_steps.add("Enter the Change Amount Value : " + ChangedAmountValue);
			logger.info("Enter the Change Amount Value : " + ChangedAmountValue);
			amount = ChangedAmountValue;
		} else {
			test_steps.add("paying the amount : " + amount);
			logger.info("paying the amount : " + amount);
		}

		if (Utility.validateString(TakePaymentType)) {
			Wait.WaitForElement(driver, OR_Reservation.CP_Reservation_Folio_TakePayment_TypeButton);
			res.CP_Reservation_Folio_TakePayment_TypeButton.click();
			String type = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//div/div//span[text()='"
					+ TakePaymentType.trim() + "']";

			if (ispaymentTypeVerify) {
				for (int i = 0; i < typeList.size(); i++) {
					String type1 = "//h4[text()='Take Payment']/../..//label[text()='TYPE']/..//div/div//span[text()='"
							+ typeList.get(i).trim() + "']";
					WebElement element = driver.findElement(By.xpath(type1));
					Utility.verifyTrue(element, "Verify Payment Type", test_steps);
				}
			}

			Wait.WaitForElement(driver, type);
			driver.findElement(By.xpath(type)).click();
			test_steps.add("Select Take Payment Type : " + TakePaymentType);
			logger.info("Select Take Payment Type : " + TakePaymentType);

		}
		String setAsMainPaymentMethod = "//span[text()='Set As Main Payment Method']/../input";
		if (IsSetAsMainPaymentMethod.equalsIgnoreCase("Yes")) {
			if (!driver.findElement(By.xpath(setAsMainPaymentMethod)).isSelected()) {
				Wait.WaitForElement(driver, setAsMainPaymentMethod);
				driver.findElement(By.xpath("//span[text()='Set As Main Payment Method']")).click();
				test_steps.add("Select set As Main Payment Method");
				logger.info("Select set As Main Payment Method");
			} else {
				test_steps.add("Already Selected set As Main Payment Method");
				logger.info("Already Selected set As Main Payment Method");
			}
		}

		String addPaymentNotes = "//button[text()='ADD NOTES']";
		String notes = "//input[@placeholder='Add Notes']";

		if (!(AddPaymentNotes.isEmpty() || AddPaymentNotes.equalsIgnoreCase(""))) {
			Wait.WaitForElement(driver, addPaymentNotes);
			Utility.ScrollToElement(driver.findElement(By.xpath(addPaymentNotes)), driver);
			driver.findElement(By.xpath(addPaymentNotes)).click();
			test_steps.add("Clicking on Add Payment Notes");
			logger.info("Clicking on Add Payment Notes");
			Wait.WaitForElement(driver, notes);
			driver.findElement(By.xpath(notes)).sendKeys(AddPaymentNotes);
			test_steps.add("Adding payment notes : " + AddPaymentNotes);
			logger.info("Adding payment notes : " + AddPaymentNotes);
		}
		String button = "//h4[text()='Take Payment']/../..//a[contains(text(),'" + amount.trim()
				+ "')]|//h4[text()='Take Payment']/../..//a[contains(text(),'" + ChangedAmountValue.trim() + "')]";
		String button_1 = "//a[contains(@data-bind,'ready-to-process')]";
		Wait.wait10Second();
		try {
			Utility.ScrollToElement(driver.findElement(By.xpath(button)), driver);
			driver.findElement(By.xpath(button)).click();
		} catch (Exception e) {
			Utility.clickThroughJavaScript(driver, driver.findElement(By.xpath(button_1)));
		}
		test_steps.add("Click on Pay");
		logger.info("Click on Pay");

		if (isSuccessfullVerified) {
			if (Utility.validateString(TakePaymentType)) {
				String takePayType = "//h4[contains(text(),'Successful')]/../..//label[text()='TYPE']/following-sibling::span";

				Wait.WaitForElement(driver, takePayType);
				String paytype = driver.findElement(By.xpath(takePayType)).getText().trim();
				assertEquals(paytype, TakePaymentType.trim());
				test_steps.add("Take Payment Type validated after payment : " + TakePaymentType);
				logger.info("Take Payment Type validated after payment : " + TakePaymentType);
			}

			if (Utility.validateString(PaymentType)) {
				String paymentType = "//h4[contains(text(),'Successful')]/../..//label[text()='PAYMENT METHOD']/following-sibling::label/span[contains(text(),'"
						+ PaymentType.trim() + "')]";

				if (PaymentType.equalsIgnoreCase("Swipe")) {
					paymentType = "//h4[contains(text(),'Successful')]/../..//label[text()='PAYMENT METHOD']/following-sibling::label/span[contains(text(),'MC')]";
				}
				Wait.WaitForElement(driver, paymentType);
				if (driver.findElement(By.xpath(paymentType)).isDisplayed()) {
					test_steps.add("Payment Type validated after payment : " + PaymentType);
					logger.info("Payment Type validated after payment : " + PaymentType);
				} else {
					test_steps.add("Payment Type not displayed after payment : " + PaymentType);
					logger.info("Payment Type not displayed after payment : " + PaymentType);
				}
			}

			String status = "//h4[contains(text(),'Successful')]/../..//label[text()='Approved']";
			Wait.WaitForElement(driver, status);
			if (driver.findElement(By.xpath(status)).isDisplayed()) {
				test_steps.add("Transaction status is : Approved");
				logger.info("Transaction status is : Approved");
			} else {
				test_steps.add("Transaction status is not : Approved");
				logger.info("Transaction status is not : Approved");
			}

			if (Utility.validateString(AddPaymentNotes)) {
				String payNotes = "//h4[contains(text(),'Successful')]/../..//span[text()='" + AddPaymentNotes + "']";
				if (!(AddPaymentNotes.isEmpty() || AddPaymentNotes.equalsIgnoreCase(""))) {
					if (driver.findElement(By.xpath(payNotes)).isDisplayed()) {
						test_steps.add("Paymant notes displayed " + AddPaymentNotes);
						logger.info("Paymant notes displayed " + AddPaymentNotes);
					} else {
						test_steps.add("Paymant notes not displayed " + AddPaymentNotes);
						logger.info("Paymant notes not displayed " + AddPaymentNotes);
					}
				}
			}
		}
		String close = "//h4[contains(text(),'Successful')]/../..//button[text()='Close']";
		Wait.WaitForElement(driver, close);
		/*
		 * Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
		 * driver.findElement(By.xpath(close)).click();
		 */
		Utility.scrollAndClick(driver, By.xpath(close));
		test_steps.add("Click on Close");
		logger.info("Click on Close");
		return amount;
	}

	public ArrayList<String> verifyAccountAttached(WebDriver driver, String AccountName) {

		ArrayList<String> test_steps = new ArrayList<String>();

		Wait.explicit_wait_xpath("//a[contains(@data-bind,'text:accountName')]", driver);
		Wait.explicit_wait_visibilityof_webelement_600(
				driver.findElement(By.xpath("//a[contains(@data-bind,'text:accountName')]")), driver);
		String accName = driver.findElement(By.xpath("//a[contains(@data-bind,'text:accountName')]")).getText();
		assertEquals(accName, AccountName, "Failed Account Not Attached");
		logger.info("Successfully Verified Associated Account in New Reservation : " + AccountName);
		test_steps.add("Successfully Verified Associated Account in New Reservation : " + AccountName);
		return test_steps;
	}

	// Added By Riddhi - Turn Off Override Deposit Due Toggle
	public void DepositOverrideToggle(WebDriver driver) throws InterruptedException {
		Wait.WaitForElement(driver, OR_ReservationV2.OVERIDE_DEPOSIT_DUE_TOGGLE);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.OVERIDE_DEPOSIT_DUE_TOGGLE), driver);
		driver.findElement(By.xpath(OR_ReservationV2.OVERIDE_DEPOSIT_DUE_TOGGLE)).click();
	}


	//Added By Riddhi
	public void verifyDepositPolicyDetailsAtPoliciesAndDisclaimersRV2(WebDriver driver, ArrayList<String> test_steps,
			String policyName, String policyText) 
	{
		try {
			Elements_CPReservation res = new Elements_CPReservation(driver);
			Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
			Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.depositPolicyCollapseIcon), driver);
			Utility.ScrollToViewElementINMiddle(driver, res.depositPolicyCollapseIcon);
			res.depositPolicyCollapseIcon.click();
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.depositPolicyNameAtDisclaimers), driver);
			String policyNameDisplayed = res.depositPolicyNameAtDisclaimers.getText();

			String policyNameAtToolTipDisplayed = res.depositPolicyToolTipTextAtDisclaimers.getAttribute("title");

			assertEquals(policyNameDisplayed, policyName, "Failed - Verify deposit policy name");
			test_steps.add("Successfully verified deposit policy name as <b>" + policyNameDisplayed + "</b>");
			logger.info("Successfully verified deposit policy name as <b>" + policyNameDisplayed + "</b>");

			assertEquals(policyNameAtToolTipDisplayed, policyName, "Failed - Verify deposit policy name at tooltip");
			test_steps.add("Successfully verified deposit policy name at tooltip as <b>" + policyNameAtToolTipDisplayed
					+ "</b>");
			logger.info("Successfully verified deposit policy name at tooltip as <b>" + policyNameAtToolTipDisplayed
					+ "</b>");
			if (Utility.validateString(policyText)) 
			{
				String policyTextDisplayed = resV2.depositPolicyTextAtDisclaimersV2.getText();
				assertEquals(policyTextDisplayed, policyText, "Failed - Verify deposit policy text");
				test_steps.add("Successfully verified deposit policy text as <b>" + policyTextDisplayed + "</b>");
				logger.info("Successfully verified deposit policy text as <b>" + policyTextDisplayed + "</b>");
			}

		} catch (Exception e) {

		}
	}
	//Added By Riddhi - Calculate Total Room Charges based on RatePerNight value
	//Use room charge/category - dynamic method
	public ArrayList<String> getTotalChargesBasedOnDates(WebDriver driver, ArrayList<String> test_steps,
			String checkInDate, String checkOutDate, boolean isChargeNeededforrange, String category) throws Exception {
		ArrayList<String> roomCharges = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		ArrayList<String> dates = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
		if( isChargeNeededforrange )
		{
			for (String date : dates) {
				//String convertedDate = Utility.parseDate(date, "dd/MM/yyyy", "EE MMM dd, yyyy");
				String convertedDate = Utility.parseDate(date, "dd/MM/yyyy", "MMM d, yyyy");				
				String roomChargeXpath = "//span[text()='" + convertedDate + "']/../..//span[text()='"+category+"']/../.."
						+ "//td[@class='lineitem-total']";			
				String roomCharge = driver.findElement(By.xpath(roomChargeXpath)).getText();
				roomCharge = roomCharge.replace("$ ", "");
				test_steps.add(category + " for the day <b>" + date + "</b> is captured as <b>" + roomCharge + "</b>");
				roomCharges.add(roomCharge);	}	
		}
		else
		{
			String convertedDate = Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM d, yyyy");
			String chargeXpath = "//span[text()='" + convertedDate + "']/../..//span[text()='"+category+"']/../.."
					+ "//td[@class='lineitem-total']";
			String charge = driver.findElement(By.xpath(chargeXpath)).getText();		
			charge = charge.replace("$ ","");
			test_steps.add(category + " for the day <b>" + checkInDate + "</b> is captured as <b>" + charge + "</b>");
			roomCharges.add(charge);		
		}
		return roomCharges;
	}
	//Added By Riddhi - Create Reservation with Addon/incidentals
		public HashMap<String, String> createReservation_withAddonIncidental(WebDriver driver, ArrayList<String> test_steps, String sourceOfRes,
				String checkInDate, String checkOutDate, String adults, String children, String ratePlan, String promoCode, 
				String roomClass, String roomClassAbb, String salutation, String guestFirstName, String guestLastName, boolean isCopyGuest,String phoneNumber,
				String altenativePhone, String email, String address1, String address2, String address3, String city, 
				String country, String state, String postalCode, boolean isGuesProfile, String marketSegment, String referral, 
				String paymentMethod, String cardNumber, String nameOnCard, String cardExpMonthAndYear, int additionalGuests,
				String roomNumber, boolean quote, String noteType, String noteSubject, String noteDescription, String taskCategory, 
				String taskType, String taskDetails, String taskRemarks, String taskDueOn, String taskAssignee, String taskStatus,
				String accountName, String accountType, String accountFirstName, String accountLastName, boolean isTaxExempt, 
				String taxExemptID, boolean isSplit,boolean isRequiredMarketSegment, boolean isRequiredReferral, boolean isRequiredEmail,
				boolean isAddIncidental, boolean isAddAddon, String category, String perUnit, String quentity) throws Exception 
		{
				
			HashMap<String, String> data = new HashMap<>();
			Account account = new Account();
			Tapechart tc = new Tapechart();
			Navigation nav = new Navigation();
			Groups group = new Groups();
			ArrayList<String> roomNumbersProvided = Utility.splitInputData(roomNumber);
			ArrayList<String> roomNumbers = new ArrayList<>();
			ArrayList<String> roomClasses = Utility.splitInputData(roomClass);
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			int noOfRooms = guestFirstName.split("\\|").length;
			boolean missingMandatoryFields = false;
			if (sourceOfRes.equalsIgnoreCase("From Reservations page")) {
				test_steps.add("<b>******************* CREATING RESERVATION FROM RESERVATION PAGE **********************</b>");
				click_NewReservation(driver, test_steps);
			}			
			test_steps.add("No. Of Rooms : " + guestFirstName.split("\\|").length);
			logger.info("No. Of Rooms : " + guestFirstName.split("\\|").length);			
			if (sourceOfRes.equalsIgnoreCase("Associate Account") || sourceOfRes.equalsIgnoreCase("From Reservations page")
					|| sourceOfRes.equalsIgnoreCase("Account Reservation") || sourceOfRes.equalsIgnoreCase("Group") 
					|| sourceOfRes.equalsIgnoreCase("TapeChart Syndicate") ) 				
			{
				if (sourceOfRes.equalsIgnoreCase("Associate Account")) {
					if (!account.checkForAccountExistsAndOpen(driver, test_steps, accountName, accountType, false)) {
						account.createNewAccount(driver, test_steps, accountType, accountName, marketSegment, referral, accountFirstName,
								accountLastName, phoneNumber, altenativePhone, address1, address2, address3, email, city, state, postalCode);
					}else {
						test_steps.add("Account is already existing with <b>"+accountName+"</b> name");
						logger.info("Account is already existing with "+accountName+" name");
					}
					nav.ReservationV2_Backward(driver);
					click_NewReservation(driver, test_steps);
				}else if (sourceOfRes.equalsIgnoreCase("Account Reservation")) {
					test_steps.add("<b>******************* CREATING RESERVATION FROM ACCOUNT **********************</b>");
					account.clickNewReservationFromAccount(driver, test_steps, accountType, accountName, marketSegment,
							referral, accountFirstName, accountLastName, phoneNumber, altenativePhone, address1, address2,
							address3, email, city, state, postalCode, null);
				} else if (sourceOfRes.equalsIgnoreCase("Group")) {
					test_steps.add("<b>******************* CREATING RESERVATION FROM GROUP **********************</b>");
					nav.groups(driver);
					ArrayList<String> accountNumbers = new ArrayList<>();
					group.createGroupAccount(driver, test_steps, accountName, true, null, marketSegment, referral, accountFirstName, 
							accountLastName, phoneNumber, address1, city, state, country, postalCode, accountNumbers);
					group.click_GroupNewReservation(driver, test_steps);
			}
			if (noOfRooms > 1) 
			{
					searchDataForFindRoomsForMRB(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);
					clickOnFindRooms(driver, test_steps);
					for (int i = 0; i < roomClass.split("\\|").length; i++) 
					{
						int j = i+1;
						roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClasses.get(i));
						try 
						{
							selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbersProvided.get(i));						
							data.put("RoomNumber"+j, roomNumbersProvided.get(i));
						} catch (Exception e) 
						{
							selectRoomToReserve(driver, test_steps, roomClasses.get(i), roomNumbers.get(i));
							data.put("RoomNumber"+j, roomNumbers.get(i));
						}
					}
				} 
				else 
				{
					searchDataForFindRooms(driver, test_steps, checkInDate, checkOutDate, adults, children, ratePlan, promoCode);				
					clickOnFindRooms(driver, test_steps);
					roomNumbers = getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClass);
					try {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumber);
						data.put("RoomNumber", roomNumber);					
					} catch (Exception e) {
						selectRoomToReserve(driver, test_steps, roomClass, roomNumbers.get(0));				
						data.put("RoomNumber", roomNumbers.get(0));
					}
				}
				clickNext(driver, test_steps);
				if (!sourceOfRes.equalsIgnoreCase("Account Reservation") && !sourceOfRes.equalsIgnoreCase("Group") ) 
				{
					clickOnCreateGuestButton(driver, test_steps);
				}
			
				turnOnOffcreateGuestProfileToggle(driver, test_steps,isGuesProfile);
				
				if(noOfRooms > 1)
				{
					if (guestFirstName.split("\\|").length>1) 
					{
						enter_MailingAddress(driver, test_steps, salutation.split("\\|")[0], guestFirstName.split("\\|")[0], guestLastName.split("\\|")[0],
								phoneNumber,altenativePhone,email, accountName,accountType,  address1,  address2,  address3,  city,
								 country,  state,  postalCode);
						clickOnGuestProfilePopupSaveButton(driver, test_steps);
						if (isCopyGuest) 
						{
							//if copy flag is on, then copy guestinfo from primary room to other rooms
							clickCopyGuestFromPrimaryRoom(driver, test_steps);						
						} 
						else 
						{
							clickOnCreateGuestButton(driver, test_steps);
							turnOnOffcreateGuestProfileToggle(driver, test_steps,isGuesProfile);
							enter_Salutation(driver,test_steps,salutation.split("\\|")[1]);
							enter_GuestName(driver, test_steps, guestFirstName.split("\\|")[1], guestLastName.split("\\|")[1]);
							clickOnGuestProfilePopupSaveButton(driver, test_steps);Wait.wait1Second();
						}
					}
				}
				else 
				{
					enter_GuestName(driver, test_steps, salutation, guestFirstName, guestLastName, false);
					enter_City(driver, test_steps, city);
					select_Country(driver, test_steps, country); select_State(driver, test_steps,state); 
					enter_PostalCode(driver, test_steps, postalCode);
					enter_Phone(driver, test_steps, phoneNumber, altenativePhone);
					enter_Email(driver, test_steps, email); 
					enter_Address(driver, test_steps,address1, address2, address3); 
					clickOnGuestProfilePopupSaveButton(driver, test_steps);
					/*enter_MailingAddress(driver, test_steps, salutation, guestFirstName, guestLastName,
							phoneNumber,altenativePhone,email, accountName,accountType,  address1,  address2,  address3,  city,
							 country,  state,  postalCode);	*/				
				}
				if (Utility.validateString(accountName) && sourceOfRes.equalsIgnoreCase("Associate Account")) {
					clickEditGuestInfo(driver, test_steps);
					enterAccount(driver, test_steps, accountName, accountType);
					if (Utility.validateString(paymentMethod)) 
					{
						enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, accountName);				
					}
				} 
				else 
				{

					if (Utility.validateString(paymentMethod)) 
					{
						// cardNumber variable can be used as Gift Card number (If Payment method: Gift Certificate, Reservation number if Payment Method: Reservation
						enter_PaymentDetails(driver, test_steps, paymentMethod, cardNumber, nameOnCard, cardExpMonthAndYear, accountName);
					}

				}
				enter_MarketingInfoDetails(driver, test_steps, "", marketSegment, referral, "Yes");
			}
			if (additionalGuests > 0) 
			{
				ArrayList<String> firstNames = new ArrayList<>();
				ArrayList<String> lastNames = new ArrayList<>();			
				for (int i = 0; i < additionalGuests; i++) 
				{
					firstNames.add(guestFirstName+i);
					lastNames.add(guestLastName+i);
				}
				enterAddMoreGuestInfoDetails(driver, test_steps, additionalGuests, firstNames, lastNames);
			}
			enter_TaxExemptDetails(driver, test_steps, isTaxExempt, taxExemptID);
			if (Utility.validateString(noteSubject)) {
				enterNotes(driver, test_steps, noteType, noteSubject, noteDescription);			
			}
			if (Utility.validateString(taskCategory) && Utility.validateString(taskType) ) {
				if (verifyTaskSection(driver, test_steps)) {
					enterTask(driver, test_steps, taskCategory, taskType, taskDetails, taskRemarks, taskDueOn, taskAssignee, taskStatus);
				}
			}
			if( isAddIncidental)
			{
				addIncidental(driver, checkInDate, category, perUnit, quentity);
			}
			if( guestFirstName.equalsIgnoreCase("") || guestLastName.equalsIgnoreCase(""))
			{	
				missingMandatoryFields = true;
			}
			else if(  marketSegment.equalsIgnoreCase(""))
			{
				missingMandatoryFields = true;
			}
			else if(  referral.equalsIgnoreCase(""))
			{
				missingMandatoryFields = true;
			}
			else
			{
				missingMandatoryFields = false;
			}
			
			if (quote) 
			{
				clickSaveQuote(driver, test_steps,missingMandatoryFields);
			} 
			else 
			{
				clickBookNow(driver, test_steps, missingMandatoryFields);
			}
			if(missingMandatoryFields)
			{
				data.put("requiredFieldValidation",res.VALIDATION_MESSAGE_FOR_MISSING_FIELDS_ON_RES_PAGE.getText());
			}
			else
			{
				data.put("ReservationNumber", get_ReservationConfirmationNumber(driver, test_steps));
				data.put("ReservationStatus", get_ReservationStatus(driver, test_steps));
				clickCloseReservationSavePopup(driver, test_steps);
				TripSummary tripSummary = getTripSummaryDetail(driver);
				data.put("RoomCharges", tripSummary.getTS_ROOM_CHARGE());
				data.put("Taxes", tripSummary.getTS_TAXES());
				data.put("requiredFieldValidation","");
			}
			return data;
		}

		public void enableVoidRoomChargeForCheckout(WebDriver driver, ArrayList<String> test_steps, boolean voidRoomCharges) {
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			boolean voidRoomChargesSelected;
				voidRoomChargesSelected = res.CHECKOUT_VOID_ROOM_CHARGES.isSelected();		
			
			if (voidRoomCharges) {
				if (!(voidRoomChargesSelected)) {	
					Wait.WaitForElement(driver, OR_ReservationV2.CHECKOUT_VOID_ROOM_CHARGES);
					Utility.clickThroughJavaScript(driver, res.CHECKOUT_VOID_ROOM_CHARGES);
					//res.CHECKOUT_VOID_ROOM_CHARGES.click();		
					test_steps.add("Enabling Void Room Charges check box");
				} else {
					test_steps.add("Void Room Charges check box is already selected");
				}
			} else {
				if (voidRoomChargesSelected) {				
					res.CHECKOUT_VOID_ROOM_CHARGES.click();	
					test_steps.add("Unselecting Void Room Charges check box");
				} else {
					test_steps.add("Void Room Charges check box is already unselected");
				}
			}
		}

	//Added By Riddhi
	public String calculationOfDepositAmountToBePaidForRateV2(ArrayList<String> roomCharges, String type,
			String attrValue, String roomClassSize) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		String amountToBePaid = null;
		double paidAmount = 0.00;
		double noOfNightCharges = 0.00;
		double totalRoomCharges = 0.00;
		if (type.equalsIgnoreCase("number of nights")) {
			if (Integer.parseInt(attrValue) < roomCharges.size()) {
				for (int i = 0; i <= Integer.parseInt(attrValue) - 1; i++) {
					noOfNightCharges = noOfNightCharges + Double.valueOf(roomCharges.get(i).replace("$", ""));
					paidAmount = noOfNightCharges;
				}
			} else if (Integer.parseInt(attrValue) > roomCharges.size()) {
				for (String str : roomCharges) {
					str = str.replace("$", "");
					totalRoomCharges = totalRoomCharges + Double.parseDouble(str);
				}
				paidAmount = totalRoomCharges;
			}
		} else if (type.equalsIgnoreCase("flat fee")) {
			paidAmount = Double.parseDouble(attrValue) * Double.valueOf(roomClassSize);
		} else if (type.equalsIgnoreCase("percent of stay")) {
			for (String str : roomCharges) {
				str = str.replace("$", "");
				totalRoomCharges = totalRoomCharges + Double.parseDouble(str);
			}
			paidAmount = (Double.parseDouble(attrValue) * totalRoomCharges) / 100;
		}
		amountToBePaid = df.format(paidAmount);
		return amountToBePaid;
	}
	//Added By Riddhi
	public void closeDepositPaymentPopup(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException
	{
		Elements_ReservationV2 eleResV2 = new Elements_ReservationV2(driver);		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.DEPOSIT_PAYMENT_CLOSE_POPUP), driver);
		eleResV2.DEPOSIT_PAYMENT_CLOSE_POPUP.click();
		Wait.wait10Second();
		//Utility.ScrollToElement(res.closePaymentSuccessPopup, driver);
		//res.closePaymentSuccessPopup.click();
		test_steps.add("Closing Deposit Payment Popup without making payment");
		logger.info("Closing Deposit Payment Popup without making payment");
	}
	//Added By Riddhi
	public void VerifyInvalidCardMessageOnDepositPolicyPopup(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException
	{
		String invalidCCErrorMessageXpath = "//span[contains(@data-bind,'TransactionDeclinedMessage')]";
		logger.info(invalidCCErrorMessageXpath);
		try
		{
			Wait.waitForElementToBeClickable(By.xpath(invalidCCErrorMessageXpath), driver);
			if(driver.findElement(By.xpath(invalidCCErrorMessageXpath)).isDisplayed())
			{
				String invalidCCText= driver.findElement(By.xpath(invalidCCErrorMessageXpath)).getAttribute("data-content");
				test_steps.add("Invalid CC Error Message : " + invalidCCText);
				logger.info("Invalid CC Error Message : " + invalidCCText);
				assertTrue(driver.findElement(By.xpath(invalidCCErrorMessageXpath)).isDisplayed(), "Invalid CC Message is not displayed on Deposit Payment Popup");		
			}
			else
			{
				assertTrue(false);
			}
		}
		catch(Exception e)
		{
			invalidCCErrorMessageXpath = "(//span[contains(@data-bind,'TransactionDeclinedMessage')])[2]";
			//Wait.waitForElementToBeClickable(By.xpath(invalidCCErrorMessageXpath), driver);
			if(driver.findElement(By.xpath(invalidCCErrorMessageXpath)).isDisplayed())
			{
				String invalidCCText= driver.findElement(By.xpath(invalidCCErrorMessageXpath)).getAttribute("data-content");
				test_steps.add("Invalid CC Error Message : " + invalidCCText);
				logger.info("Invalid CC Error Message : " + invalidCCText);
				assertTrue(driver.findElement(By.xpath(invalidCCErrorMessageXpath)).isDisplayed(), "Invalid CC Message is not displayed on Deposit Payment Popup");		
			}
			else
			{
				assertTrue(false);
			}
		}
					
	}
	//Added By Riddhi - Click on 'Save As Quote' Button From Deposit Payment Popup WHile creating reservation
	public void saveAsQuoteFromDepositPaymentPopup(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException
	{
		String saveAsQuoteXPath = "//button[normalize-space()='Save as Quote']";
		WebElement eleQuoteBtn = driver.findElement(By.xpath(saveAsQuoteXPath));
		Wait.waitForElementToBeClickable(By.xpath(saveAsQuoteXPath), driver);		
		//Utility.ScrollToElement(driver.findElement(By.xpath(close)), driver);
		driver.findElement(By.xpath(saveAsQuoteXPath)).click();
		Wait.wait15Second();
	}
	//Added By Riddhi - Whether Refund Button is visible in the folio or not
	public boolean verifyIfRefundBtnIsVisibleInFolio(WebDriver driver, ArrayList<String> test_steps)
	{
		boolean isRefundBtnVisible = false;
		Elements_ReservationV2 eleResV2 = new Elements_ReservationV2(driver);
		if( eleResV2.FOLIO_REFUND_BTN.isDisplayed() )
			isRefundBtnVisible = true;		
		return isRefundBtnVisible;
	}
	//Added By Riddhi -  Whether Pay Button is visible in the folio or not
	public boolean verifyIfPayBtnIsVisibleInFolio(WebDriver driver, ArrayList<String> test_steps)
	{
		boolean isPayBtnVisible = false;
		Elements_ReservationV2 eleResV2 = new Elements_ReservationV2(driver);
		if( eleResV2.FOLIO_PAY_BTN.isDisplayed() )
			isPayBtnVisible  = true;		
		return isPayBtnVisible;
	}
	//Added By Riddhi - Click on Book Button From Quote
	public void convertToReservationFromQuote(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException
	{
		Elements_ReservationV2 eleResV2  = new Elements_ReservationV2(driver);		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBook), driver);
		Utility.ScrollToElement(eleResV2.CP_NewReservation_QuoteBook, driver);
		eleResV2.CP_NewReservation_QuoteBook.click();
		logger.info("Clicked on Book");
		test_steps.add("Clicked on Book");
		try
		{
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.QUOTE_PROCEED_TO_PAYMENT_BTN), driver);
			Utility.ScrollToElement(eleResV2.QUOTE_PROCEED_TO_PAYMENT_BTN, driver);
			eleResV2.QUOTE_PROCEED_TO_PAYMENT_BTN.click();
			try
			{
				Wait.WaitForElement(driver, OR_ReservationV2.CP_NewReservation_QuoteBookConfirm2);
				Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBookConfirm2), driver, 10);
				Utility.clickThroughJavaScript(driver, eleResV2.CP_NewReservation_QuoteBookConfirm2);
			}
			catch(Exception e)
			{
				logger.info(e.toString());				
			}
		}
		catch(Exception e)
		{
			Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBookConfirm), driver);
			eleResV2.CP_NewReservation_QuoteBookConfirm.click();					
		}		
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.STAY_INFO), driver);
		logger.info("Reservation Successfully booked from Quote");
		test_steps.add("Reservation Successfully booked from Quote");
	}
	//Added By Riddhi - Click on Book Button From Quote
	public void convertToReservationFromQuotewithoutPayment(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException
	{
		Elements_ReservationV2 eleResV2  = new Elements_ReservationV2(driver);		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CP_NewReservation_QuoteBook), driver);
		Utility.ScrollToElement(eleResV2.CP_NewReservation_QuoteBook, driver);
		eleResV2.CP_NewReservation_QuoteBook.click();
		logger.info("Clicked on Book");
		test_steps.add("Clicked on Book");
	}
	//Added By Riddhi - 
	public void OverrideDepositDueAmountInReservation(WebDriver driver, ArrayList<String> test_steps, String overrideDepositDueAmount) throws InterruptedException
	{
		Elements_ReservationV2 eleResV2  = new Elements_ReservationV2(driver);		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.DEPOSIT_DUE_AMOUNT), driver);
		eleResV2.DEPOSIT_DUE_AMOUNT.click();
		eleResV2.DEPOSIT_DUE_AMOUNT.clear();
		eleResV2.DEPOSIT_DUE_AMOUNT.sendKeys(overrideDepositDueAmount);
		test_steps.add("Override Deposit Due Amount : " + overrideDepositDueAmount);
		logger.info("Override Deposit Due Amount : " + overrideDepositDueAmount);
	}

	//Added By Riddhi - Add task in Single/MRB Reservation
	public void AddTaskForRooms(WebDriver driver, ArrayList test_steps, String IsTask, String TaskCategory, String TaskType,
            String TaskDetails, String TaskRemarks, String TaskDueon, String TaskAssignee, String TaskStatus, boolean isMRB, String selectRoom)
            throws InterruptedException {	
        Elements_CPReservation res = new Elements_CPReservation(driver);
        Elements_ReservationV2 resV2 = new Elements_ReservationV2(driver);
        try
        {
        	 Wait.WaitForElement(driver, OR_ReservationV2.CP_AddTask);
             Utility.ScrollToElement(res.CP_AddTask, driver);
             test_steps.add("******************* Adding Task **********************");
             logger.info("******************* Adding Task **********************");             
             res.CP_AddTask.click();
             test_steps.add("Click on Add Task in Try");
             logger.info("Click on Add Task in Try");             
        }
        catch(Exception e)
        {
            test_steps.add("******************* Adding Task **********************");
            logger.info("******************* Adding Task **********************");             
            String xpathAddtask = "//button[contains(@data-bind,'click:taskInfoModelForEdit.addTask')]";
            Wait.WaitForElement(driver, xpathAddtask);
            Utility.ScrollToElement(driver.findElement(By.xpath(xpathAddtask)), driver);
            driver.findElement(By.xpath(xpathAddtask)).click();
            test_steps.add("Click on Add Task in catch");
            logger.info("Click on Add Task in catch");
        }
        if(isMRB)
        {
        	String roomArrow = "//label[text()='Create Task For']/..//button";
        	Wait.WaitForElement(driver, roomArrow);
        	driver.findElement(By.xpath(roomArrow)).click();
        	 
            Wait.wait2Second();
            String taskRoom = "//label[text()='Create Task For']/..//div//li/a/span[contains(text(),'" + selectRoom + "')]";            
            //label[text()='Create Task For']/..//div//li/a/span[contains(text(),'R_ Room For Automation: 4453')]
            logger.info("Path For Room Selection : "+taskRoom);
            Wait.WaitForElement(driver, taskRoom);
            driver.findElement(By.xpath(taskRoom)).click();
            test_steps.add("Selected Room : " + selectRoom);
            logger.info("Selected Room : " + selectRoom);
        }
        if (IsTask.equalsIgnoreCase("Yes")) {
			/*
			 * Utility.ScrollToElement(res.CP_AddTask, driver); 
			 * res.CP_AddTask.click();
			 * test_steps.add("Click on Add Task"); logger.info("Click on Add Task");
			 */
            String taskCategoryArrow = "//label[text()='Category']/..//button";
            Wait.WaitForElement(driver, taskCategoryArrow);
            driver.findElement(By.xpath(taskCategoryArrow)).click();

            String taskCategory = "//label[text()='Category']/..//div/ul//span[text()='" + TaskCategory.trim() + "']";
            Wait.WaitForElement(driver, taskCategory);
            driver.findElement(By.xpath(taskCategory)).click();
            test_steps.add("Select Task Category : " + TaskCategory);
            logger.info("Select Task Category : " + TaskCategory);
            Wait.wait2Second();

            String taskType = "//div[contains(@class,'reservation-task-modal')]//label[text()='Type']//..//div//button";//"//label[text()='Type']//..//div//button";
            logger.info(taskType);
            Wait.WaitForElement(driver, taskType);
            driver.findElement(By.xpath(taskType)).click();
            Wait.wait2Second();
            taskType = "//label[text()='Type']//..//div/ul//span[text()='" + TaskType.trim() + "']";
            Wait.WaitForElement(driver, taskType);
            driver.findElement(By.xpath(taskType)).click();
            test_steps.add("Select Task Type : " + TaskType);
            logger.info("Select Task Type : " + TaskType);

            String taskDetails = "//label[text()='Details']/preceding-sibling::textarea";
            Wait.WaitForElement(driver, taskDetails);
            driver.findElement(By.xpath(taskDetails)).sendKeys(TaskDetails);
            test_steps.add("Select Task Details : " + TaskDetails);
            logger.info("Select Task Details : " + TaskDetails);

            String taskRemarks = "//label[text()='Remarks']/preceding-sibling::textarea";
            Wait.WaitForElement(driver, taskRemarks);
            driver.findElement(By.xpath(taskRemarks)).sendKeys(TaskRemarks);
            test_steps.add("Enter Task Remarks : " + TaskRemarks);
            logger.info("Enter Task Remarks : " + TaskRemarks);

            if (!TaskAssignee.isEmpty()) {
                String taskAssignee = "//label[text()='Assignee']/..//input";
                WebElement elementAssigneeInput = driver.findElement(By.xpath(taskAssignee));
                // elementAssigneeInput.sendKeys(TaskAssignee);
                TaskAssignee = "auto";
                String[] splitString = TaskAssignee.split(" ");
                TaskAssignee = splitString[0];
                String noDataFound = "//label[text()='Assignee']//..//div//div";
                for (int i = 0; i < 5; i++) {
                    elementAssigneeInput.click();
                    elementAssigneeInput.clear();
                    for (int j = 0; j < TaskAssignee.length(); j++) {
                        elementAssigneeInput.sendKeys(String.valueOf(TaskAssignee.charAt(j)));
                        Wait.wait1Second();
                    }
                    try {
                    	logger.info("in try");
                        Wait.wait1Second();
                        String taskassgin = "(//label[text()='Assignee']/..//div//ul//li//div/span)[2]";
                        // Wait.WaitForElement(driver, taskassgin);
                        WebElement elementTaskAssign = driver.findElement(By.xpath(taskassgin));
                        elementTaskAssign.click();
                        break;
                    } catch (Exception e) {
                        logger.info("in catch");
                    }
                }
            }
            try
            {
	            String taskStatus = "(//label[text()='Status']/..//button)[2]";
	            Wait.WaitForElement(driver, taskStatus);
	            driver.findElement(By.xpath(taskStatus)).click();
	            String status = "(//label[text()='Status']/..//button)[2]/following-sibling::div//li//span[text()='"
	                    + TaskStatus.trim() + "']";
	            Wait.WaitForElement(driver, status);
	            driver.findElement(By.xpath(status)).click();
	            test_steps.add("Select Task Status : " + TaskStatus);
	            logger.info("Select Task Status : " + TaskStatus);
            }
            catch(Exception e)
            {
            	String taskStatus = "(//label[text()='Status']/..//button)";
            	Wait.WaitForElement(driver, taskStatus);
	            driver.findElement(By.xpath(taskStatus)).click();
	            String status = "(//label[text()='Status']/..//button)/following-sibling::div//li//span[text()='"
	                    + TaskStatus.trim() + "']";
	            Wait.WaitForElement(driver, status);
	            driver.findElement(By.xpath(status)).click();
	            test_steps.add("Select Task Status : " + TaskStatus);
	            logger.info("Select Task Status : " + TaskStatus);            
            }
            //Save Task
            try
            {
	            Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.SAVE_TASK_BTN), driver);
	    		Utility.ScrollToElement(resV2.SAVE_TASK_BTN, driver);    		            
	            driver.findElement(By.xpath(OR_ReservationV2.SAVE_TASK_BTN)).click();
	            Wait.wait10Second();
	            test_steps.add("Clicked on Save Task Button");
	            logger.info("Clicked on Save Task Button");
            }
            catch(Exception e)
            {
            	logger.info("Inside catch block, save task failed");
            	e.printStackTrace();
            }
        }
    }
	//Added By Riddhi - Verify Room FOR MRB Tasks
	public ArrayList<String> verifyRoomForCreatedTask(WebDriver driver, String room, ArrayList<String> test_steps) throws InterruptedException 
	{
		Elements_Reservation ReservationPage = new Elements_Reservation(driver);
		WebElement roomType = driver.findElement(By.xpath("//td[@data-bind='text: roomName'][text()='"+ room +"']"));
		Utility.ScrollToElement(roomType,driver);
		roomType.getText();
		assertEquals(roomType.getText(), room, "Failed : roomType missmatched in Added Task");
		logger.info("Verified Task Room :" + roomType);
		test_steps.add("Verified Task Room:" + roomType);		
		return test_steps;
	}
	//Added By Riddhi - Click on Add-On Button - Add Addon Button
	public void clickAddAddon(WebDriver driver, ArrayList<String> test_steps) {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.CLICK_ADDON), driver);
		res.CLICK_ADDON.click();
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.ADDON_DATE_PICKER), driver);
	}
	public void addAddon(WebDriver driver, ArrayList<String> test_steps, String getDate, 
			String productName, int addOnQty) throws Exception {
		Elements_ReservationV2 eleResV2 = new Elements_ReservationV2(driver);
		clickAddAddon(driver, test_steps);
		Wait.WaitForElement(driver, OR_ReservationV2.ADDON_DATE_PICKER);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.ADDON_DATE_PICKER), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADDON_DATE_PICKER), driver);
		Utility.ScrollToElement(eleResV2.ADDON_DATE_PICKER, driver);
		getDate = Utility.parseDate(getDate, "dd/MM/yyyy", "MM/dd/yyyy");
		test_steps.add("Selected Date: " + getDate);
		logger.info("Selected Date: " + getDate);
		
		Wait.WaitForElement(driver, OR_ReservationV2.SEARCH_ADDON);
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.SEARCH_ADDON), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.SEARCH_ADDON), driver);
		eleResV2.SEARCH_ADDON.click();
		eleResV2.SEARCH_ADDON.clear();
		eleResV2.SEARCH_ADDON.sendKeys(productName);		
		test_steps.add("Searched for Addon Product : "+ productName);
		logger.info("Searched for Addon Product : "+productName);
		
		String addonQuantity = "//span[@class='addOn-details']/..//..//..//span[@data-bind='event: { mousedown: $parent.incrementAddOn }']";
		Wait.WaitForElement(driver, OR_ReservationV2.ADDON_QUANTITY_INCREASE);
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADDON_QUANTITY_INCREASE), driver);
		for(int i=0;i<addOnQty;i++)
		{
			eleResV2.ADDON_QUANTITY_INCREASE.click();
		}
		Wait.wait10Second();
		Wait.WaitForElement(driver, OR_ReservationV2.ADDON_SAVE);		
		Wait.waitForElementToBeClickable(By.xpath(OR_ReservationV2.ADDON_SAVE), driver);
		eleResV2.ADDON_SAVE.click();
		Wait.wait10Second();
				
		/*clickAddIncidentals(driver, test_steps);
		Wait.WaitForElement(driver, OR_Reservation.EnterAddOn_IncidenalDate);
		Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Wait.waitForElementToBeClickable(By.xpath(OR_Reservation.EnterAddOn_IncidenalDate), driver);
		Utility.ScrollToElement(element.EnterAddOn_IncidenalDate, driver);
		getDate = Utility.parseDate(getDate, "dd/MM/yyyy", "MM/dd/yyyy");
		element.EnterAddOn_IncidenalDate.click();
		element.EnterAddOn_IncidenalDate.clear();
		element.EnterAddOn_IncidenalDate.sendKeys(getDate);
		test_steps.add("Enter Date: " + getDate);

		element.ButtonSelectIncidental.click();
		test_steps.add("Click on select button");
		Wait.wait1Second();
	//	String pathSelectIncidental = "//div[text()='ADD-ONS & INCIDENTALS']//..//ul//li//span[text()='" + category
	//			+ "']";
//		WebElement elementSelect = driver.findElement(By.xpath(pathSelectIncidental));
	//	elementSelect.click();
	//	test_steps.add("Select Category: " + category);

		element.EnterPerUnit.clear();
		element.EnterPerUnit.sendKeys(perUnit);
		test_steps.add("Enter Per Unit: " + perUnit);

		element.EnterQuentity.clear();
		element.EnterQuentity.sendKeys(quentity);
		test_steps.add("Enter Quentity: " + quentity);

		element.IncidentalSave.click();
		test_steps.add("Click on Save button");
		Wait.waitForElementToBeVisibile(By.xpath(OR_ReservationV2.TOASTER_MESSAGE), driver);*/
	}

	// MRB Notes
	public void enter_NotesMRB(WebDriver driver, ArrayList test_steps, String IsAddNotes, String NoteType,
			String Subject, String Description, boolean isMRB, String selectRoom) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Wait.WaitForElement(driver, OR_Reservation.CP_AddNotes);
		test_steps.add("******************* Adding Notes **********************");
		logger.info("******************* Adding Notes **********************");
		if (IsAddNotes.equalsIgnoreCase("Yes")) {
			try {
				Utility.scrollAndClick(driver, By.xpath(OR_Reservation.CP_AddNotes));
			} catch (Exception e) {
				Utility.clickThroughJavaScript(driver, res.CP_AddNotes);
			}
			test_steps.add("Click on Add Notes");
			logger.info("Click on Add Notes");
			Wait.wait5Second();
			String noteTypeArrow = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button";
			Wait.WaitForElement(driver, noteTypeArrow);
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(noteTypeArrow)));

			} catch (Exception e) {
				driver.findElement(By.xpath(noteTypeArrow)).click();
			}
		if (isMRB) {
			String roomArrow = "//label[text()='CREATE NOTES FOR']/..//button";
			Wait.WaitForElement(driver, roomArrow);
			driver.findElement(By.xpath(roomArrow)).click();

			Wait.wait2Second();
			String taskRoom = "//label[text()='CREATE NOTES FOR']/..//div//li/a/span[contains(text(),'" + selectRoom
					+ "')]";
            //label[text()='Create Task For']/..//div//li/a/span[contains(text(),'R_ Room For Automation: 4453')]
			logger.info("Path For Room Selection : " + taskRoom);
			Wait.WaitForElement(driver, taskRoom);
			driver.findElement(By.xpath(taskRoom)).click();
			test_steps.add("Selected Room : " + selectRoom);
			logger.info("Selected Room : " + selectRoom);

		}
		     String noteTypeArrow1 = "//div[contains(@class,'form-group ir-frm-grp ir-select-floating-lbl focused')]/..//label[text()='Type']/..//button";
		      Wait.WaitForElement(driver, noteTypeArrow1);
		      driver.findElement(By.xpath(noteTypeArrow1)).click();
			String noteType = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Type']/..//button/..//div//li/a/span[text()='"
					+ NoteType.trim() + "']";
			Wait.WaitForElement(driver, noteType);
			driver.findElement(By.xpath(noteType)).click();
			test_steps.add("Select Note Type : " + NoteType);
			logger.info("Select Note Type : " + NoteType);
   
			 
			String subject = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Subject']/..//input";
			Wait.WaitForElement(driver, subject);
			driver.findElement(By.xpath(subject)).sendKeys(Subject);
			test_steps.add("Enter subject : " + Subject);
			logger.info("Enter subject : " + Subject);

			String description = "//h4[text()='Add Notes']/../..//div/div//div//label[text()='Description']/preceding-sibling::textarea";
			Wait.WaitForElement(driver, description);
			driver.findElement(By.xpath(description)).sendKeys(Description);
			test_steps.add("Enter Description : " + Description);
			logger.info("Enter Description : " + Description);

			String user = "//input[starts-with(@data-bind,'value: updatedBy')]";

			JavascriptExecutor js = (JavascriptExecutor) driver;
			user = (String) js.executeScript("return arguments[0].value", driver.findElement(By.xpath(user)));

			String add = "//h4[text()='Add Notes']/../..//button[text()='ADD']";
			Wait.WaitForElement(driver, add);
			Utility.customAssert(driver.findElement(By.xpath(add)).isEnabled() + "", "true", true,
					"Successfully Verified Add Note Button", "Successfully Verified Add Note Button", true, test_steps);
			driver.findElement(By.xpath(add)).click();
			test_steps.add("Click on Add");
			logger.info("Click on Add");

			/*
			 * String noteverify = "//td[text()='" + NoteType.trim() + "']";
			 * Wait.WaitForElement(driver, noteverify);
			 * test_steps.add("Sucessfully added Note : " + Subject);
			 * logger.info("Sucessfully added Note : " + Subject);
			 * 
			 * test_steps.add("Verified Note Type : " + NoteType);
			 * logger.info("Verified Note Type : " + NoteType);
			 
			 * noteverify = "//td[text()='" + Subject.trim() + "']";
			 * Wait.WaitForElement(driver, noteverify);
			 * test_steps.add("Verified Note Subject : " + Subject);
			 * logger.info("Verified Note Subject : " + Subject);
			 * 
			 * noteverify = "//td[text()='" + Description.trim() + "']";
			 * Wait.WaitForElement(driver, noteverify);
			 * test_steps.add("Verified Note Description : " + Description);
			 * logger.info("Verified Note Description : " + Description);
			 * 
			 * noteverify = "//td[text()='" + user.trim() + "']";
			 * Wait.WaitForElement(driver, noteverify);
			 * test_steps.add("Verified Note updated by : " + user);
			 * logger.info("Verified Note updated by : " + user);
			 */
		}
	}
	public void clickAddTaskButtonEnabledorDisabled(WebDriver driver, ArrayList<String> test_steps,boolean message) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Utility.ScrollToElement(res.CP_AddTask, driver);
		res.CP_AddTask.click();
		boolean message1;
		message1 =!res.CP_AddTask.isSelected();

	    if (message){
	       if(!message1) {
	    	   Wait.WaitForElement(driver, OR_ReservationV2.CP_AddTask);
				Utility.clickThroughJavaScript(driver, res.CP_AddTask);
	            test_steps.add("Add Task Button is Enable");
		 		logger.info("Add Task Button is Enable");
	       }else {
	    	   test_steps.add("Add Task Button is Disable");
		 		logger.info("Add Task Button is Disable");
	    	   
	       }
	       }
	}
	public void editTaskEnabledorDisabled(WebDriver driver, ArrayList<String> test_steps,boolean message) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Utility.ScrollToElement(res.EDIT_TASK, driver);
		res.EDIT_TASK.click();
		boolean message1;
		message1 =!res.EDIT_TASK.isSelected();

	    if (message){
	       if(!message1) {
	    	   Wait.WaitForElement(driver, OR_ReservationV2.EDIT_TASK);
				Utility.clickThroughJavaScript(driver, res.EDIT_TASK);
	            test_steps.add("Edit Task  is Enable");
		 		logger.info("Edit Task  is Enable");
	       }else {
	    	   test_steps.add("Edit Task  is Disable");
		 		logger.info("Edit Task  is Disable");
	    	   
	       }
	       }
		
	}
	public void DeleteTaskEnabledorDisabled(WebDriver driver, ArrayList<String> test_steps,boolean message) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		Utility.ScrollToElement(res.DELETE_TASK, driver);
		res.DELETE_TASK.click();
		boolean message1;
		message1 =!res.DELETE_TASK.isSelected();

	    if (message){
	       if(!message1) {
	    	   Wait.WaitForElement(driver, OR_ReservationV2.DELETE_TASK);
				Utility.clickThroughJavaScript(driver, res.DELETE_TASK);
	            test_steps.add("Delete Task  is Enable");
		 		logger.info("Delete Task  is Enable");
	       }else {
	    	   test_steps.add("Delete Task  is Disable");
		 		logger.info("Delete Task  is Disable");
	    	   
	       }
	       }
		
	}
	
	public void verifyNotesDetails(WebDriver driver, ArrayList<String> test_steps, String noteType,String noteRoom, String noteSubject,
			String noteDesc, String updatedBy) {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		try {
			Wait.waitForElementToBeVisibile(By.xpath(OR_Reservation.notesTabHeader), driver);
			Utility.ScrollToElement(res.notesTabHeader, driver);
			List<WebElement> noteTypesDisplayed = driver.findElements(
					By.xpath("//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: type')]"));
			List<WebElement> noteRoomsDisplayed = driver.findElements(
					By.xpath("//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: room')]"));
			List<WebElement> noteDescriptionsDisplayed = driver.findElements(
					By.xpath("//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: details')]"));
			List<WebElement> noteSubjectsDisplayed = driver.findElements(
					By.xpath("//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: subject')]"));
			List<WebElement> noteUpdatedBysDisplayed = driver.findElements(
					By.xpath("//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: updatedBy')]"));
			List<WebElement> noteUpdatedOnsDisplayed = driver.findElements(By.xpath(
					"//td[text()='" + noteSubject + "']/..//td[contains(@data-bind,'text: moment(updatedOn())')]"));
			if (noteType.equalsIgnoreCase("No Show")) {
				noteType = "Internal";
			}
			if (Utility.validateString(noteType)) {
				verifyNotesDetails(test_steps, noteTypesDisplayed, noteType, "type");
			}
			if (Utility.validateString(noteRoom)) {
				verifyNotesDetails(test_steps, noteRoomsDisplayed, noteRoom, "room");
			}
			if (Utility.validateString(noteDesc)) {
				verifyNotesDetails(test_steps, noteDescriptionsDisplayed, noteDesc, "description");
			}
			if (Utility.validateString(updatedBy)) {
				verifyNotesDetails(test_steps, noteUpdatedBysDisplayed, updatedBy, "updated by");
			}
			if (Utility.validateString(noteSubject)) {
				verifyNotesDetails(test_steps, noteSubjectsDisplayed, noteSubject, "subject");
			}
			int i = 1;
			for (WebElement webElement : noteUpdatedOnsDisplayed) {
				String actString = webElement.getText();
				actString = Utility.parseDate(actString, "MM/dd/ yy HH:mm aa", "MM/dd/yyyy");
				String expString = Utility.getCurrentDate("MM/dd/yyyy");
				// assertEquals(actString, expString, "Failed - Verify note
				// updated on date of cancel note");
				if (noteUpdatedOnsDisplayed.size() > 1) {
					logger.info("Successfully verified note updated on date as <b>" + actString + "</b> at row " + i);
					i++;
				} else {
					logger.info("Successfully verified note updated on date as <b>" + actString + "</b> ");
				}
			}
		} catch (Exception e) {
			logger.info(e);
		}
	}
	public void verifyNotesDetails(ArrayList<String> test_steps, List<WebElement> element, String expString,
			String type) {
		int i = 1;
		for (WebElement webElement : element) {
			String actString = webElement.getText();
			assertEquals(actString, expString, "Failed - Verify " + type + " of note");
			if (element.size() > 1) {
				test_steps.add("Successfully verified " + type + " of note as <b>" + actString + "</b> at row " + i);
				logger.info("Successfully verified " + type + " of note as <b>" + actString + "</b> at row " + i);
				i++;
			} else {
				logger.info("Successfully verified " + type + " of note as <b>" + actString + "</b>");
			}
		}
	}
	
	
	//Added by reshma - Verifying Checkinall button disability
		public void verifyCheckInAllButtonDisability(WebDriver driver, ArrayList<String> test_steps , String errorMessage)
				throws InterruptedException {
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			Wait.WaitForElement(driver, OR_ReservationV2.CP_Reservation_CheckInAllButton);
			assertTrue(res.CP_Reservation_CheckInAllButton.isEnabled(), errorMessage);
			test_steps.add(" Check In ALL Button is Disabled");
			logger.info(" Check In ALL Button is Disabled");
		}
		
		public void get_AssociatedPoliciesToReservation(WebDriver driver, ArrayList test_steps)
				throws InterruptedException {
			test_steps.add("******************* Policies associated to reservation **********************");
			logger.info("******************* Policies associated to reservation **********************");
			String policies = "(//div[text()='Policies And Disclaimers']/..//h4)";

			int count = driver.findElements(By.xpath(policies)).size();
			for (int i = 1; i < count; i++) {
				policies = "(//div[text()='Policies And Disclaimers']/..//h4)[" + i + "]/a";
				driver.findElement(By.xpath(policies)).click();
				Wait.wait2Second();
				if (i == 1) {
					String depositPolicy = "//div[text()='Policies And Disclaimers']/..//label[text()='Deposit Policy']/following-sibling::div//button/span";
					depositPolicy = driver.findElement(By.xpath(depositPolicy)).getText();
					test_steps.add("Associated deposit policy to reservation : " + depositPolicy);
					logger.info("Associated deposit policy to reservation : " + depositPolicy);
				}
				if (i == 2) {
					String checkinPolicy = "//div[text()='Policies And Disclaimers']/..//label[text()='Check-in Policy']/following-sibling::div//button/span";
					checkinPolicy = driver.findElement(By.xpath(checkinPolicy)).getText();
					test_steps.add("Associated checkin policy to reservation : " + checkinPolicy);
					logger.info("Associated checkin policy to reservation : " + checkinPolicy);
				}
				if (i == 3) {
					String noShow = "//div[text()='Policies And Disclaimers']/..//label[text()='No Show Policy']/following-sibling::div//button/span";
					noShow = driver.findElement(By.xpath(noShow)).getText();
					test_steps.add("Associated noShow policy to reservation : " + noShow);
					logger.info("Associated noShow policy to reservation : " + noShow);
				}
			}
		}
		
		//Added by Reshma - To Lock the reservation
		public void roomLock(WebDriver driver, ArrayList<String> test_steps) throws Exception {
			Elements_ReservationV2 res = new Elements_ReservationV2(driver);
			Wait.WaitForElement(driver, OR_ReservationV2.ROOMLOCK);

			int lockStatus = driver.findElements(By.xpath("//i[contains(@class,'fa fa-unlock ng-color-green')]")).size();
			if (lockStatus > 0) {
				res.ROOMLOCK.click();
				test_steps.add("Room status is locked");
				logger.info("Room status is locked");
			} else {
				test_steps.add("Room status is already locked");
			    logger.info("Room status is already locked");
			}

		}
		
		//Added by Reshma - To get room total before clicking on select button
		  public String getRoomTotal(WebDriver driver) throws Exception {
				Elements_ReservationV2 res = new Elements_ReservationV2(driver);
				Wait.waitForElementByXpath(driver,OR_ReservationV2.GetTotalAmount);		
				return Utility.getElementText(res.GetTotalAmount);
				
				}
		//Added By Sucharitha- Deleting particular note
			public void deleteNote(WebDriver driver,ArrayList<String> test_steps,String NoteType) throws InterruptedException {
				String deleteNotes = "//td[contains(@class,'ir-delete')]";
				Wait.WaitForElement(driver, deleteNotes);

				driver.findElement(By.xpath(deleteNotes)).click();

				test_steps.add("clicking on delete task : " + NoteType);
				logger.info("clicking on delete task : " + NoteType);

				String ClickyesPopup = " //div[text()='Are you sure you want to delete this Note?']/parent::div//button[text()='Yes']";
				Wait.WaitForElement(driver, ClickyesPopup);

				driver.findElement(By.xpath(ClickyesPopup)).click();

				logger.info("Successfully Deleted Note");
				test_steps.add("Successfully Deleted Note");
			}

	
	public void clickEditGuestInfoOFCopyRes(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		Elements_ReservationV2 res = new Elements_ReservationV2(driver);
		Wait.WaitForElement(driver, OR_ReservationV2.RESERVATION_GUEST_INFO_EDIT2);
		Utility.ScrollToViewElementINMiddle(driver, res.RESERVATION_GUEST_INFO_EDIT2);
		res.RESERVATION_GUEST_INFO_EDIT2.click();
		test_steps.add("Click on Edit Guest Info");
		logger.info("Click on Edit Guest Info");
	}
	
	public String editTask(WebDriver driver, ArrayList<String> test_steps) throws InterruptedException {
		String task = "//tbody[contains(@data-bind,'tasks')]/tr";
		Wait.WaitForElement(driver, task);
		driver.findElement(By.xpath(task)).click();
		test_steps.add("Clicking on task to edit");
		logger.info("Clicking on task to edit");
		Wait.wait10Second();
		String expandTask = "//h4[text()='Edit Task']/../..//select[contains(@data-bind,'selectedCategory')]/..//button/..";

		driver.findElement(By.xpath(expandTask)).click();
		test_steps.add("Open task category drop down");
		logger.info("Open task category drop down");

		String taskty = null;

		Wait.wait5Second();
		String taskCategory = "//h4[text()='Edit Task']/../..//select[contains(@data-bind,'selectedCategory')]/..//ul/li";
		Wait.WaitForElement(driver, taskCategory);
		int count = driver.findElements(By.xpath(taskCategory)).size();
		if (count > 2) {
			taskCategory = "//h4[text()='Edit Task']/../..//select[contains(@data-bind,'selectedCategory')]/..//ul/li[3]";

			String taskcat = driver.findElement(By.xpath(taskCategory)).getText();
			driver.findElement(By.xpath(taskCategory)).click();
			test_steps.add("selected task category : " + taskcat);
			logger.info("selected task category : " + taskcat);

			Wait.wait5Second();
			String taskType = "//h4[text()='Edit Task']/../..//select[contains(@data-bind,'selectedType')]/..//button/..";
			driver.findElement(By.xpath(taskType)).click();
			test_steps.add("Open task type drop down");
			logger.info("Open task type drop down");

			taskType = " //h4[text()='Edit Task']/../..//select[contains(@data-bind,'selectedType')]/..//ul/li[2]";
			taskty = driver.findElement(By.xpath(taskType)).getText();
			driver.findElement(By.xpath(taskType)).click();
			test_steps.add("selected task type : " + taskty);
			logger.info("selected task type : " + taskty);

			String details = "//h4[text()='Edit Task']/../..//label[text()='Details']/preceding-sibling::textarea";

			driver.findElement(By.xpath(details)).clear();
			driver.findElement(By.xpath(details)).sendKeys("Sample task deatils");

			test_steps.add("Entered task details as : Sample task deatils");
			logger.info("Entered task details as : Sample task deatils");

			String save = "//h4[text()='Edit Task']/../..//button[text()='Save']";
			driver.findElement(By.xpath(save)).click();
			test_steps.add("clicking on save");
			logger.info("clicking on save");

			String taskVerify = "//span[text()='Tasks ']/../..//div//tbody/tr//span[contains(text(),'" + taskty.trim()
					+ "')]";
			try {
				Wait.WaitForElement(driver, taskVerify);
				test_steps.add("suscessfully verified task type in reservation");
				logger.info("suscessfully verified task type in reservation");

				String taskDetails = "//span[text()='Tasks ']/../..//div//tbody/tr//td[contains(text(),'Sample task deatils')]";
				Wait.WaitForElement(driver, taskVerify);
				test_steps.add("suscessfully verified task details in reservation");
				logger.info("suscessfully verified task details in reservation");
			} catch (Exception e) {
				test_steps.add("Failed to verify task details in reservation");
				logger.info("Failed to verify task details in reservation");
			}

		}
		return taskty;

	}
	
	public ArrayList<String> updateTask(WebDriver driver, int taskIndex, String TaskStatus)
			throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();

		String taskPath = "(//tr[contains(@data-bind, 'UpdateTaskDetails')])[" + taskIndex + "]";

		Wait.WaitForElement(driver, taskPath);
		Wait.waitForElementToBeVisibile(By.xpath(taskPath), driver);
		Wait.waitForElementToBeClickable(By.xpath(taskPath), driver);
		WebElement taskToEdit = driver.findElement(By.xpath(taskPath));
		Utility.ScrollToElement(taskToEdit, driver);
		taskToEdit.click();
		testSteps.add("Opend Task at " + taskIndex + " index");
		logger.info("Opend Task at " + taskIndex + " index");

		String taskStatus = "(//label[text()='Status']/..//button)[2]";
		Wait.WaitForElement(driver, taskStatus);
		driver.findElement(By.xpath(taskStatus)).click();
		String status = "(//label[text()='Status']/..//button)[2]/following-sibling::div//li//span[text()='"
				+ TaskStatus.trim() + "']";
		Wait.WaitForElement(driver, status);
		driver.findElement(By.xpath(status)).click();
		testSteps.add("Select Task Status : " + TaskStatus);
		logger.info("Select Task Status : " + TaskStatus);

		String save = "//h4[text()='Edit Task']/../..//button[text()='Save']";
		Wait.WaitForElement(driver, save);
		driver.findElement(By.xpath(save)).click();
		testSteps.add("Click on Save");
		logger.info("Click on Save");
		Wait.wait5Second();
		String stat = "//span[text()='Tasks']/../..//td[contains(text(),'" + TaskStatus.trim() + "')]";
		Wait.WaitForElement(driver, stat);
		testSteps.add("Verified task status updated : " + TaskStatus);
		logger.info("Verified task status updated : " + TaskStatus);

		return testSteps;
	}
	
	public void deleteTask(WebDriver driver, ArrayList<String> test_steps, String taskType)
			throws InterruptedException {
		String deleteTask = "//span[text()='Tasks ']/../..//div//tbody/tr//span[contains(text(),'" + taskType
				+ "')]/../..//following-sibling::td[4]//button";
		Wait.WaitForElement(driver, deleteTask);

		driver.findElement(By.xpath(deleteTask)).click();
		test_steps.add("clicking on delete task : " + taskType);
		logger.info("clicking on delete task : " + taskType);

		Wait.wait5Second();
		String yes = "//div[text()='Are you sure you want to delete this task?']/..//button[text()='Yes']";
		Wait.WaitForElement(driver, deleteTask);

		driver.findElement(By.xpath(yes)).click();
		test_steps.add("Are you sure you want to delete this task? : Yes");
		logger.info("Are you sure you want to delete this task? : Yes");

		Wait.wait5Second();
		deleteTask = "//span[text()='Tasks ']/../..//div//tbody/tr//span[contains(text(),'" + taskType + "')]";
		if (driver.findElements(By.xpath(deleteTask)).size() <= 0) {
			test_steps.add("Veriffied task deleted successfully : " + taskType);
			logger.info("Veriffied task deleted successfully : " + taskType);
		} else {
			test_steps.add("Failed to delete the task : " + taskType);
			logger.info("Failed to delete the task : " + taskType);
		}

	}
	
}
