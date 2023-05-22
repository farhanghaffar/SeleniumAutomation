package com.innroad.inncenter.model;

import java.util.HashMap;

public class ReservationRatesDetail {

	private String roomClassName;
	private String avgPerNight;
	private String availableRoomQty;
	private String totalRate;
	HashMap<String, String> perNightRates;
	public ReservationRatesDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReservationRatesDetail(String roomClassName, String avgPerNight, String availableRoomQty, String totalRate,
			HashMap<String, String> perNightRates) {
		super();
		this.roomClassName = roomClassName;
		this.avgPerNight = avgPerNight;
		this.availableRoomQty = availableRoomQty;
		this.totalRate = totalRate;
		this.perNightRates = perNightRates;
	}
	public ReservationRatesDetail(String roomClassName, String avgPerNight, String availableRoomQty, String totalRate) {
		super();
		this.roomClassName = roomClassName;
		this.avgPerNight = avgPerNight;
		this.availableRoomQty = availableRoomQty;
		this.totalRate = totalRate;
	}
	public String getRoomClassName() {
		return roomClassName;
	}
	public void setRoomClassName(String roomClassName) {
		this.roomClassName = roomClassName;
	}
	public String getAvgPerNight() {
		return avgPerNight;
	}
	public void setAvgPerNight(String avgPerNight) {
		this.avgPerNight = avgPerNight;
	}
	public String getAvailableRoomQty() {
		return availableRoomQty;
	}
	public void setAvailableRoomQty(String availableRoomQty) {
		this.availableRoomQty = availableRoomQty;
	}
	public String getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}
	public HashMap<String, String> getPerNightRates() {
		return perNightRates;
	}
	public void setPerNightRates(HashMap<String, String> perNightRates) {
		this.perNightRates = perNightRates;
	}
	@Override
	public String toString() {
		return "ReservationRatesDetail [roomClassName=" + roomClassName + ", avgPerNight=" + avgPerNight
				+ ", availableRoomQty=" + availableRoomQty + ", totalRate=" + totalRate + ", perNightRates="
				+ perNightRates + "]";
	}
	
	
	
}

