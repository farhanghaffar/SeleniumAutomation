package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.FolioNew;
import com.innroad.inncenter.pageobjects.ReportsV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.ReservationV2;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GetNetSalesDataUserBooked  {
	
	public static Logger app_logs = Logger.getLogger("ReportsV2");
	ReportsV2 report = new ReportsV2();
	FolioNew folio= new FolioNew();

	CPReservationPage reservation = new CPReservationPage();
	ReservationV2 reservationPage=  new ReservationV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	
	public HashMap<String, List<String>> netSalesReportDataUserBookedRatePlan(WebDriver driver) {		
		HashMap<String, List<String>> userBookedData= new HashMap<String,List<String>>();		
		List<String> summaryViewUserBooked= new ArrayList<String>();
		List<String> summaryViewNetRes= new ArrayList<String>();
		List<String> summaryViewRoomNights= new ArrayList<String>();
		List<String> summaryViewBookingNights= new ArrayList<String>();
		List<String> summaryViewRoomRevenue= new ArrayList<String>();
		List<String> summaryViewOtherRevenue= new ArrayList<String>();
		List<String> summaryViewTotalRevenue= new ArrayList<String>();
		List<String> summaryViewCancelPer= new ArrayList<String>();
		List<String> summaryViewAvgStay= new ArrayList<String>();
		List<String> summaryViewAvgDailyRate= new ArrayList<String>();
		List<String> summaryViewRavPar= new ArrayList<String>();
		List<String> detailedViewUserBooked= new ArrayList<String>();
		List<String> detailedViewNetRes= new ArrayList<String>();
		List<String> detailedViewRoomNights= new ArrayList<String>();
		List<String> detailedViewBookingNights= new ArrayList<String>();
		List<String> detailedViewRoomRevenue= new ArrayList<String>();
		List<String> detailedViewOtherRevenue= new ArrayList<String>();
		List<String> detailedViewTotalRevenue= new ArrayList<String>();
		List<String> detailedViewCancelPer= new ArrayList<String>();
		List<String> detailedViewAvgStay= new ArrayList<String>();
		List<String> detailedViewAvgDailyRate= new ArrayList<String>();
		List<String> detailedViewRavPar= new ArrayList<String>();
		summaryViewUserBooked=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 1);
		app_logs.info(summaryViewUserBooked);
		summaryViewNetRes=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 2);
		app_logs.info(summaryViewNetRes);	
		summaryViewRoomNights=report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 3);
		app_logs.info(summaryViewRoomNights);	
		summaryViewBookingNights= report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 4);
		app_logs.info(summaryViewBookingNights);
		summaryViewRoomRevenue=  report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 5);
		app_logs.info(summaryViewRoomRevenue );	
		summaryViewOtherRevenue=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 6);
		app_logs.info(summaryViewOtherRevenue );	
		summaryViewTotalRevenue=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 7);
		app_logs.info(summaryViewTotalRevenue );
		summaryViewCancelPer=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 8);
		app_logs.info(summaryViewCancelPer );	
		summaryViewAvgStay=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 9);
		app_logs.info(summaryViewAvgStay );	
		summaryViewAvgDailyRate=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 10);
		app_logs.info(summaryViewAvgDailyRate );
		summaryViewRavPar=   report.getReportsSummaryViewWise(driver, "Summary View", "Net Sales Report", 11);
		app_logs.info(summaryViewRavPar );	
		detailedViewUserBooked=report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 1);
		app_logs.info(detailedViewUserBooked);
		detailedViewNetRes=report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 2);
		app_logs.info(detailedViewNetRes);	
		detailedViewRoomNights=report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 3);
		app_logs.info(detailedViewRoomNights);	
		detailedViewBookingNights= report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 4);
		app_logs.info(detailedViewBookingNights);
		detailedViewRoomRevenue=  report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 5);
		app_logs.info(detailedViewRoomRevenue );	
		detailedViewOtherRevenue=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 6);
		app_logs.info(detailedViewOtherRevenue );	
		detailedViewTotalRevenue=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 7);
		app_logs.info(detailedViewTotalRevenue );
		detailedViewCancelPer=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 8);
		app_logs.info(detailedViewCancelPer );	
		detailedViewAvgStay=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 9);
		app_logs.info(detailedViewAvgStay );	
		detailedViewAvgDailyRate=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 10);
		app_logs.info(detailedViewAvgDailyRate );
		detailedViewRavPar=   report.getReportsSummaryViewWise(driver, "Detailed View", "Net Sales Report", 11);
		app_logs.info(detailedViewRavPar );		
		userBookedData.put("summaryViewUserBooked", summaryViewUserBooked);
		userBookedData.put("summaryViewNetRes", summaryViewNetRes);
		userBookedData.put("summaryViewRoomNights", summaryViewRoomNights);
		userBookedData.put("summaryViewBookingNights", summaryViewBookingNights);
		userBookedData.put("summaryViewRoomRevenue", summaryViewRoomRevenue);
		userBookedData.put("summaryViewOtherRevenue", summaryViewOtherRevenue);
		userBookedData.put("summaryViewTotalRevenue", summaryViewTotalRevenue);
		userBookedData.put("summaryViewCancelPer", summaryViewCancelPer);
		userBookedData.put("summaryViewAvgStay", summaryViewAvgStay);
		userBookedData.put("summaryViewAvgDailyRate", summaryViewAvgDailyRate);
		userBookedData.put("summaryViewRavPar", summaryViewRavPar);		
		userBookedData.put("detailedViewUserBooked", detailedViewUserBooked);
		userBookedData.put("detailedViewNetRes", detailedViewNetRes);
		userBookedData.put("detailedViewRoomNights", detailedViewRoomNights);
		userBookedData.put("detailedViewBookingNights", detailedViewBookingNights);
		userBookedData.put("detailedViewRoomRevenue", detailedViewRoomRevenue);
		userBookedData.put("detailedViewOtherRevenue", detailedViewOtherRevenue);
		userBookedData.put("detailedViewTotalRevenue", detailedViewTotalRevenue);
		userBookedData.put("detailedViewCancelPer", detailedViewCancelPer);
		userBookedData.put("detailedViewAvgStay", detailedViewAvgStay);
		userBookedData.put("detailedViewAvgDailyRate", detailedViewAvgDailyRate);
		userBookedData.put("detailedViewRavPar", detailedViewRavPar);
		
		return userBookedData;	
	}
	

	public HashMap<String, HashMap<String, String>> netSalesReportDataRoomClassRoomNumber(WebDriver driver,String roomClass) throws ParseException {		
		HashMap<String, HashMap<String, String>> roomClassRoomNumberData= new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> roomClassOneData= new HashMap<String, String>();
		HashMap<String, String> roomClassTwoData= new HashMap<String, String>();
		HashMap<String, String> detailViewroomClassOneData= new HashMap<String, String>();
		HashMap<String, String> detailViewroomClassTwoData= new HashMap<String, String>();
		
		roomClassOneData=report.getNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", Utility.splitInputData(roomClass).get(0));
		roomClassTwoData=report.getNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", Utility.splitInputData(roomClass).get(1));
		detailViewroomClassOneData=report.getNetSalesDetailViewDataClassWiseRowWise(driver, Utility.splitInputData(roomClass).get(0));
		detailViewroomClassTwoData=report.getNetSalesDetailViewDataClassWiseRowWise(driver, Utility.splitInputData(roomClass).get(1));
		
		roomClassRoomNumberData.put("roomClassOneData", roomClassOneData);
		roomClassRoomNumberData.put("roomClassTwoData", roomClassTwoData);
		roomClassRoomNumberData.put("detailViewroomClassOneData", detailViewroomClassOneData);
		roomClassRoomNumberData.put("detailViewroomClassTwoData", detailViewroomClassTwoData);
		return roomClassRoomNumberData;

		
	}
	
	public HashMap<String, String> getFolioChargesForUserBookedAndRatePlan(WebDriver driver,String date, String checkInDates, String checkOutDates,
			ArrayList<String> test_steps) throws Exception {
		HashMap<String, String> userBookedRatePLanFolioData= new HashMap<String, String>();
		String netRes=null,cancelReservation=null, netRoomNights=null;
		double roomCharge=0.00, incidentals=0.00,fee=0.00;
		Multimap<String, String> feeAdjustment= ArrayListMultimap.create();
		Collection<String> feeAdjust= new ArrayList<String>();
		Multimap<String, String> roomCharges= ArrayListMultimap.create();	
		Collection<String> roomChargesSum= new ArrayList<String>();
		incidentals=incidentals+folio.getFolioRoomCharges(driver, test_steps, "Spa");		
		feeAdjustment=folio.getFolioRoomChargesDateWise(driver, test_steps, "Fee Adjustment");
		roomCharges=folio.getFolioRoomChargesDateWise(driver, test_steps, "Room Charge");
		app_logs.info("Data- " + feeAdjustment);					
		feeAdjust=feeAdjustment.get(date);
			for (String str : feeAdjust)  {
			fee=fee+Double.parseDouble(str);}
			incidentals=incidentals+fee;
			app_logs.info(incidentals);
			roomChargesSum=roomCharges.get(date);
			app_logs.info(roomChargesSum);
			for (String str : roomChargesSum)  {
			roomCharge=roomCharge+Double.parseDouble(str);}
			app_logs.info(roomCharge);
			app_logs.info(incidentals);					
		reservation.closeReservationTab(driver);	
		test_steps.add("=========Get Data from Reservation=========");
		app_logs.info("========= Get Data from Reservation =========");
		reservationSearch.clickOnAdvance(driver);
		Wait.wait1Second();
		reservationSearch.advanceSearchWithBookFrom(driver, checkInDates);
		Wait.wait1Second();
		reservationSearch.advanceSearchWithBookTo(driver, checkOutDates);
		Wait.wait1Second();
		reservationSearch.clickOnSearchButton(driver);
		Wait.wait25Second();
		List<String> reservationStatus= new ArrayList<String>();
		List<String> reservationNights= new ArrayList<String>();
		List<String> reservationNos= new ArrayList<String>();
		List<String> reservationNo= new ArrayList<String>();
		List<String> cancelReservations= new ArrayList<String>();
		int nights=0;
		String totalReservation=reservationSearch.getTotalNumberofRows(driver);
		if(totalReservation!="0") {
			for(int i=0;i<Integer.parseInt(totalReservation);i++) {
				reservationStatus.add(reservationSearch.getReservationStatusAfterSearch(driver, i+1));
				reservationNights.add(reservationSearch.getNightsAfterSearch(driver, i+1));
				reservationNos.add(reservationSearch.getReservationNumberAfterSearch(driver, i+1));
			}
			totalReservation=String.valueOf(reservationNos.size());
			app_logs.info(reservationStatus);
			app_logs.info(reservationNights);
		}			
		for(int i=0; i< reservationStatus.size();i++) {
			if(!reservationStatus.get(i).contains("Cancelled")) {					
				nights=nights + Integer.valueOf(reservationNights.get(i));
				reservationNo.add(reservationNos.get(i));						
			}else {
				cancelReservations.add(reservationNos.get(i));
			}
		}
		Set<String> s = new LinkedHashSet<String>(reservationNo);						 
		 netRes= String.valueOf(s.size());		
		 cancelReservation=String.valueOf(cancelReservations.size());
		 netRoomNights=String.valueOf(nights);	
		 app_logs.info("Net Reservation= "+netRes);
		app_logs.info("Net Nights= "+netRoomNights);
		app_logs.info("Cancel Reservation= "+cancelReservation);
		userBookedRatePLanFolioData.put("roomCharge", String.valueOf(roomCharge));
		userBookedRatePLanFolioData.put("incidentals", String.valueOf(incidentals));
		userBookedRatePLanFolioData.put("NetReservation", netRes);
		userBookedRatePLanFolioData.put("NetNights", netRoomNights);
		userBookedRatePLanFolioData.put("CancelReservation", cancelReservation);
		return userBookedRatePLanFolioData;
	}
	
	public HashMap<String, List<String>> getFolioChargesForRoomClassAndRoomNumber(WebDriver driver,List<String> abbs,HashMap<String, String> roomClassAbb, String date,
			String checkInDates, String checkOutDates, String confirmationNo,ArrayList<String> test_steps) throws Exception {
		double otherRevenue1=0.00,otherRevenue2=0.00, roomRevenueR1=0.00, roomRevenueR2=0.00;
		String roomClass1NetNights=null,roomClass2NetNights=null;
		List<String> roomNumbers= new ArrayList<String>();
		List<String> roomClassNameswithRoomRevenue= new ArrayList<String>();
		List<String> roomClassNamesWithOtherRevenue= new ArrayList<String>();
		HashMap<String, String> incidentail= new HashMap<String, String>();
		HashMap<String, String> feeAdjustCharges=  new HashMap<String, String>();
		HashMap<String, String> roomCharges=  new HashMap<String, String>();
		HashMap<String, List<String>> roomClassRoomNumberData= new HashMap<String, List<String>>();
		incidentail=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Spa");
		feeAdjustCharges=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Fee Adjustment");					
		roomCharges=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Room Charge");	
		for (Map.Entry<String, String> entry : incidentail.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
				otherRevenue1=otherRevenue1+Double.parseDouble(entry.getValue());  
				roomNumbers.add(roomClassAbb.get(abbs.get(0))+":"+splitsAbberation[1]);
			}else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
				otherRevenue2=otherRevenue2+Double.parseDouble(entry.getValue());
				roomNumbers.add(roomClassAbb.get(abbs.get(1))+":"+splitsAbberation[1]);
			}
		}
    	for (Map.Entry<String, String> entry : feeAdjustCharges.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
				if(splitsIncidential[1].equals(date)) {
					otherRevenue1=otherRevenue1+Double.parseDouble(entry.getValue());
					roomClassNamesWithOtherRevenue.add(roomClassAbb.get(abbs.get(0)) +":"+otherRevenue1);
				}	}	
			else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
				if(splitsIncidential[1].equalsIgnoreCase(date)) {
					otherRevenue2=otherRevenue2+Double.parseDouble(entry.getValue());
					roomClassNamesWithOtherRevenue.add(roomClassAbb.get(abbs.get(1)) +":"+otherRevenue2);
				}}}
		for (Map.Entry<String, String> entry : roomCharges.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
					if(splitsIncidential[1].equals(date)) {
						roomRevenueR1=roomRevenueR1+Double.parseDouble(entry.getValue());
						roomClassNameswithRoomRevenue.add(roomClassAbb.get(abbs.get(0)) +":"+roomRevenueR1);
					}}
			else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
				if(splitsIncidential[1].equalsIgnoreCase(date)) {
							roomRevenueR2=roomRevenueR2+Double.parseDouble(entry.getValue());
							roomClassNameswithRoomRevenue.add(roomClassAbb.get(abbs.get(1))+":"+roomRevenueR2);}						
			} }
		app_logs.info(roomNumbers);
		app_logs.info(roomRevenueR1);
		app_logs.info(roomRevenueR2);	
		app_logs.info(otherRevenue1);
		app_logs.info(otherRevenue2);
		Wait.wait1Second();
		reservation.closeReservationTab(driver);	
		test_steps.add("=========Get Data from Reservation=========");
		app_logs.info("========= Get Data from Reservation =========");

		reservationSearch.clickOnAdvance(driver);
		Wait.wait1Second();
		reservationSearch.advanceSearchWithBookFrom(driver, checkInDates);
		Wait.wait1Second();
		reservationSearch.advanceSearchWithBookTo(driver, checkOutDates);
		Wait.wait1Second();
		reservationSearch.clickOnSearchButton(driver);
		Wait.wait10Second();
		roomClass1NetNights=reservationSearch.getNightsAfterSearchAsPerRoomAndStatus(driver, confirmationNo, "Cancelled", abbs.get(0));
		roomClass2NetNights=reservationSearch.getNightsAfterSearchAsPerRoomAndStatus(driver, confirmationNo, "Cancelled", abbs.get(1));
		app_logs.info(roomClass1NetNights);
		app_logs.info(roomClass2NetNights);
		List<String> revenue1= new ArrayList<String>();
		revenue1.add(String.valueOf(roomRevenueR1));
		revenue1.add(String.valueOf(roomRevenueR2));
		revenue1.add(String.valueOf(otherRevenue1));
		revenue1.add(String.valueOf(otherRevenue2));
		revenue1.add(String.valueOf(roomClass1NetNights));
		revenue1.add(String.valueOf(roomClass2NetNights));
		roomClassRoomNumberData.put("roomNumbers", roomNumbers);
		roomClassRoomNumberData.put("revenuesandnights", revenue1);
		roomClassRoomNumberData.put("roomClassNamesWithRoomRevenue", roomClassNameswithRoomRevenue);
		roomClassRoomNumberData.put("roomClassNamesWithOtherRevenue", roomClassNamesWithOtherRevenue);
		return roomClassRoomNumberData;
	}
	
	public void verifySummaryViewNetSalesUserBookedRatePlan(WebDriver driver,String netRes, String netRoomNights , String roomRevenue, String otherRevenue, String totalRevenue,
			String avgStay, String avgDailyRate, String ravPar, ArrayList<String> test_steps) {
		
		 test_steps.add("========= Verify Net Sales Summary View =========");
		 app_logs.info("========= Verify Net Sales Summary View =========");
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 2, String.valueOf(netRes), test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 3, String.valueOf(netRoomNights), test_steps);
		//report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 4, "100.00%", test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 5, Utility.convertDecimalFormat(String.valueOf(roomRevenue)), test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 6, Utility.convertDecimalFormat(String.valueOf(otherRevenue)), test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 7, Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesReportSummaryViewWiseAvgStay(driver, "Summary View", "Net Sales Report", 9,  Utility.convertDecimalFormat(avgStay), test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 10,  Utility.convertDecimalFormat(avgDailyRate), test_steps);
		report.verifyNetSalesReportSummaryViewWise(driver, "Summary View", "Net Sales Report", 11,  Utility.convertDecimalFormat(ravPar), test_steps);				

	}
	
	public void verifyDetailedNetSalesUserBookedRatePlan(WebDriver driver,String detailHeader , String date,String netRes, String netRoomNights , String roomRevenue, 
			String otherRevenue, String totalRevenue,String avgStay, String avgDailyRate, String ravPar,ArrayList<String> test_steps) {
		
		 test_steps.add("========= Verify Net Sales Detailed View =========");
		 app_logs.info("========= Verify Net Sales Detailed View =========");
		report.verifyNetSalesReportDetailViewHeader(driver, "Detailed View", "Net Sales Report", detailHeader, test_steps);
		ArrayList<String> data= new ArrayList<String>();
		data.add(date); 
		String userName=detailHeader.split("-")[1];
		app_logs.info(userName);
		//report.verifyNetSalesReportDetailedViewWiseDate(driver, "Detailed View", "Net Sales Report", 1, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 1, userName,data, test_steps);
		data.clear();
		data.add(String.valueOf(netRes));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 2, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 2, userName,data, test_steps);
		data.clear();
		data.add(String.valueOf(netRoomNights));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 3, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 3, userName,data, test_steps);
		data.clear();
		data.add("100.00%");
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 4, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 4, userName,data, test_steps);
		data.clear();
		if(roomRevenue.toString().contains(",") && roomRevenue.toString().contains("$")) {
			roomRevenue=Utility.convertDecimalFormat(roomRevenue.replace("$", "").replace(",", ""));
			}else if(roomRevenue.toString().contains("$")){
				roomRevenue=Utility.convertDecimalFormat(roomRevenue.replace("$", ""));}
		
		data.add(Utility.convertDecimalFormat(String.valueOf(roomRevenue)));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 5, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 5, userName,data, test_steps);
		data.clear();
		data.add(Utility.convertDecimalFormat(String.valueOf(otherRevenue)));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 6, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 6, userName,data, test_steps);
		data.clear();
		data.add(Utility.convertDecimalFormat(totalRevenue));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 7, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 7, userName,data, test_steps);
		data.clear();
		data.add(Utility.convertDecimalFormat(avgStay));
		//report.verifyNetSalesReportDetailedViewWiseAvgStay(driver, "Detailed View", "Net Sales Report", 9, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWiseAvgStay(driver, "Detailed View", "Net Sales Report", 9, userName,data, test_steps);
		data.clear();
		data.add(Utility.convertDecimalFormat(avgDailyRate));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 10, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 10, userName,data, test_steps);
		data.clear();
		data.add(Utility.convertDecimalFormat(ravPar));
		//report.verifyNetSalesReportDetailedViewWise(driver, "Detailed View", "Net Sales Report", 11, data, test_steps);
		report.verifyNetSalesReportDetailedViewUserWise(driver, "Detailed View", "Net Sales Report", 11, userName,data, test_steps);
	}
	
	public void verifyNetSalesSummaryViewDataClassWiseRowWise(WebDriver driver,String roomClass,String netRes, String netRoomNights, String bookingNights,String roomRevenue, 
			String otherRevenue, String totalRevenue,String avgStay, String avgDailyRate, String ravPar,ArrayList<String> test_steps) {
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 1,netRes, test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 2,netRoomNights, test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 3,Utility.convertDecimalFormat(bookingNights), test_steps);					
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 4,Utility.convertDecimalFormat(roomRevenue), test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 5,Utility.convertDecimalFormat(otherRevenue), test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 6,Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWiseAvgStay(driver, "Summary View", "Net Sales Report", roomClass, 8,avgStay, test_steps);
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 9,Utility.convertDecimalFormat(avgDailyRate), test_steps);
		ravPar=null;
		if(Utility.validateString(ravPar)) {
		report.verifyNetSalesSummaryViewDataClassWiseRowWise(driver, "Summary View", "Net Sales Report", roomClass, 10,Utility.convertDecimalFormat(ravPar), test_steps);
		}
	}
	
	public void verifyNetSalesDetailViewDataClassWiseRowWise(WebDriver driver,String roomClass, String date,String netRes, String netNights,String roomRevenue, 
			String otherRevenue, String totalRevenue,String avgStay, String avgDailyRate, String ravPar,ArrayList<String> test_steps) {
		
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  1, date, test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  2, netRes, test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  3,netNights, test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  4, "100.00", test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  5, Utility.convertDecimalFormat(roomRevenue), test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  6, Utility.convertDecimalFormat(otherRevenue), test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  7, Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWiseAvgStay(driver, roomClass,  9, avgStay, test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  10, Utility.convertDecimalFormat(avgDailyRate), test_steps);
		report.verifyNetSalesDetailViewDataClassWiseRowWise(driver, roomClass,  11, Utility.convertDecimalFormat(ravPar), test_steps);
		
	}
	
	
	public HashMap<String, List<String>> getFolioChargesofAllDates(WebDriver driver,List<String> abbs,HashMap<String, String> roomClassAbb,
			String checkInDates, String checkOutDates, String confirmationNo,ArrayList<String> test_steps) throws Exception {
		double otherRevenue1=0.00,otherRevenue2=0.00, roomRevenueR1=0.00, roomRevenueR2=0.00;
		List<String> roomNumbers= new ArrayList<String>();
		List<String> roomClassNameswithRoomRevenue= new ArrayList<String>();
		List<String> roomClassNamesWithOtherRevenue= new ArrayList<String>();
		HashMap<String, String> incidentail= new HashMap<String, String>();
		HashMap<String, String> feeAdjustCharges=  new HashMap<String, String>();
		HashMap<String, String> roomCharges=  new HashMap<String, String>();
		HashMap<String, List<String>> roomClassRoomNumberData= new HashMap<String, List<String>>();
		incidentail=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Spa");
		feeAdjustCharges=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Fee Adjustment");					
		roomCharges=folio.getFolioChargesDateWiseAndRoomsWise(driver, test_steps, "Room Charge");	
		for (Map.Entry<String, String> entry : incidentail.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
				otherRevenue1=otherRevenue1+Double.parseDouble(entry.getValue());  
				roomNumbers.add(roomClassAbb.get(abbs.get(0))+":"+splitsAbberation[1]);
			}else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
				otherRevenue2=otherRevenue2+Double.parseDouble(entry.getValue());
				roomNumbers.add(roomClassAbb.get(abbs.get(1))+":"+splitsAbberation[1]);
			}
		}
    	for (Map.Entry<String, String> entry : feeAdjustCharges.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
					otherRevenue1=otherRevenue1+Double.parseDouble(entry.getValue());
					roomClassNamesWithOtherRevenue.add(roomClassAbb.get(abbs.get(0)) +":"+otherRevenue1);
					}	
			else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
					otherRevenue2=otherRevenue2+Double.parseDouble(entry.getValue());
					roomClassNamesWithOtherRevenue.add(roomClassAbb.get(abbs.get(1)) +":"+otherRevenue2);
				}}
		for (Map.Entry<String, String> entry : roomCharges.entrySet()) {
			String[] splitsIncidential= entry.getKey().split("=");
			String[] splitsAbberation=splitsIncidential[0].split(":");
			if(abbs.get(0).trim().equals(splitsAbberation[0].trim())) {
						roomRevenueR1=roomRevenueR1+Double.parseDouble(entry.getValue());
						roomClassNameswithRoomRevenue.add(roomClassAbb.get(abbs.get(0)) +":"+roomRevenueR1);
					}
			else if(abbs.get(1).trim().equals(splitsAbberation[0].trim())) {
							roomRevenueR2=roomRevenueR2+Double.parseDouble(entry.getValue());
							roomClassNameswithRoomRevenue.add(roomClassAbb.get(abbs.get(1))+":"+roomRevenueR2);}						
			 }
		app_logs.info(roomNumbers);
		app_logs.info(roomRevenueR1);
		app_logs.info(roomRevenueR2);	
		app_logs.info(otherRevenue1);
		app_logs.info(otherRevenue2);
		Wait.wait1Second();
		List<String> revenue1= new ArrayList<String>();
		revenue1.add(String.valueOf(roomRevenueR1));
		revenue1.add(String.valueOf(roomRevenueR2));
		revenue1.add(String.valueOf(otherRevenue1));
		revenue1.add(String.valueOf(otherRevenue2));
		roomClassRoomNumberData.put("roomNumbers", roomNumbers);
		roomClassRoomNumberData.put("revenuesandnights", revenue1);
		roomClassRoomNumberData.put("roomClassNamesWithRoomRevenue", roomClassNameswithRoomRevenue);
		roomClassRoomNumberData.put("roomClassNamesWithOtherRevenue", roomClassNamesWithOtherRevenue);
		return roomClassRoomNumberData;
	}
	
	public void verifyNetSalesDetailViewAllDataClassWiseRowWise(WebDriver driver,String roomClass, String date,String netRes, String netNights,String roomRevenue, 
			String otherRevenue, String totalRevenue,String avgStay, String avgDailyRate, String ravPar,ArrayList<String> test_steps) {
		
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  1, date, test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  2, netRes, test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  3,netNights, test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  4, "100.00", test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  5, Utility.convertDecimalFormat(roomRevenue), test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  6, Utility.convertDecimalFormat(otherRevenue), test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  7, Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  9, avgStay, test_steps);
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  10, Utility.convertDecimalFormat(avgDailyRate), test_steps);
		if(Utility.validateString(ravPar)) {
		report.verifyNetSalesDetailViewAllDataClassWiseRowWise(driver, roomClass,  11, Utility.convertDecimalFormat(ravPar), test_steps);
		}
		
	}
	
	public HashMap<String, List<String>> netSalesReportSummaryDataCorporateOrGroupAccount(WebDriver driver, String accountName) {		
		HashMap<String, List<String>> userBookedData= new HashMap<String,List<String>>();		
		List<String> summaryViewNetRes= new ArrayList<String>();
		List<String> summaryViewRoomNights= new ArrayList<String>();
		List<String> summaryViewBookingNights= new ArrayList<String>();
		List<String> summaryViewRoomRevenue= new ArrayList<String>();
		List<String> summaryViewOtherRevenue= new ArrayList<String>();
		List<String> summaryViewTotalRevenue= new ArrayList<String>();
		List<String> summaryViewCancelPer= new ArrayList<String>();
		List<String> summaryViewAvgStay= new ArrayList<String>();
		List<String> summaryViewAvgDailyRate= new ArrayList<String>();
		List<String> summaryViewRavPar= new ArrayList<String>();
		summaryViewNetRes=report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 1);
		app_logs.info(summaryViewNetRes);	
		summaryViewRoomNights=report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 2);
		app_logs.info(summaryViewRoomNights);	
		summaryViewBookingNights= report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 3);
		app_logs.info(summaryViewBookingNights);
		summaryViewRoomRevenue=  report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 4);
		app_logs.info(summaryViewRoomRevenue );	
		summaryViewOtherRevenue=   report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 5);
		app_logs.info(summaryViewOtherRevenue );	
		summaryViewTotalRevenue=   report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 6);
		app_logs.info(summaryViewTotalRevenue );
		summaryViewCancelPer=   report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 7);
		app_logs.info(summaryViewCancelPer );	
		summaryViewAvgStay=  report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 8);
		app_logs.info(summaryViewAvgStay );	
		summaryViewAvgDailyRate=   report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 9);
		app_logs.info(summaryViewAvgDailyRate );
		summaryViewRavPar=   report.getNetSalesReportSummaryViewCorporateAccountData(driver, "Summary View", "Net Sales Report",accountName, 10);
		app_logs.info(summaryViewRavPar );	
		userBookedData.put("summaryViewNetRes", summaryViewNetRes);
		userBookedData.put("summaryViewRoomNights", summaryViewRoomNights);
		userBookedData.put("summaryViewBookingNights", summaryViewBookingNights);
		userBookedData.put("summaryViewRoomRevenue", summaryViewRoomRevenue);
		userBookedData.put("summaryViewOtherRevenue", summaryViewOtherRevenue);
		userBookedData.put("summaryViewTotalRevenue", summaryViewTotalRevenue);
		userBookedData.put("summaryViewCancelPer", summaryViewCancelPer);
		userBookedData.put("summaryViewAvgStay", summaryViewAvgStay);
		userBookedData.put("summaryViewAvgDailyRate", summaryViewAvgDailyRate);
		userBookedData.put("summaryViewRavPar", summaryViewRavPar);		
		return userBookedData;	
	}
	
	public void verifySummaryViewNetSalesCorporateAccount(WebDriver driver,String accountName,String netRes, String netRoomNights ,String bookingNight, String roomRevenue, String otherRevenue, String totalRevenue,
			String avgStay, String avgDailyRate, String ravPar, ArrayList<String> test_steps) {
		
		 test_steps.add("========= Verify Net Sales Summary View =========");
		 app_logs.info("========= Verify Net Sales Summary View =========");
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,1, netRes, test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,2, netRoomNights, test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,3, bookingNight, test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,4, Utility.convertDecimalFormat(String.valueOf(roomRevenue)), test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,5, Utility.convertDecimalFormat(String.valueOf(otherRevenue)), test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,6, Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesSummaryViewDataCorporateAvgStay(driver, "Summary View", "Net Sales Report", accountName,8,  Utility.convertDecimalFormat(avgStay), test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,9,  Utility.convertDecimalFormat(avgDailyRate), test_steps);
		report.verifyCorporateAccountDataNetSalesReportSummaryView(driver, "Summary View", "Net Sales Report", accountName,10,  Utility.convertDecimalFormat(ravPar), test_steps);				

	}
	
	public void verifyDetailViewNetSalesCorporateAccount(WebDriver driver,String accountName,String date,String netRes, String netRoomNights ,String bookingNight, String roomRevenue, String otherRevenue, String totalRevenue,
			String avgStay, String avgDailyRate, String ravPar, ArrayList<String> test_steps) {		
		 test_steps.add("========= Verify Net Sales Detail View =========");
		 app_logs.info("========= Verify Net Sales Detail View =========");
		 report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,1, date, test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,2, netRes, test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,3, netRoomNights, test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,4, bookingNight, test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,5, Utility.convertDecimalFormat(String.valueOf(roomRevenue)), test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,6, Utility.convertDecimalFormat(String.valueOf(otherRevenue)), test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,7, Utility.convertDecimalFormat(totalRevenue), test_steps);
		report.verifyNetSalesDetailViewDataCorporateAccountAvgStay(driver,  accountName,9,  Utility.convertDecimalFormat(avgStay), test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,10,  Utility.convertDecimalFormat(avgDailyRate), test_steps);
		report.verifyCorporateAccountDataNetSalesReportDetailView(driver,  accountName,11,  Utility.convertDecimalFormat(ravPar), test_steps);				

	}

}
