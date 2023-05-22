package com.innroad.inncenter.model;

public class RatesGridChannelRatesRules {

	/**
	 * Create by Muhammad Haider
	 * Model For Rates Grid Value
	 */
	
	private String minRule;
	private boolean noCheckIn;
	private boolean noCheckOut;
	private String rooomRate;
	private String extraAdultRate;
	private String extraChildRate;
	
	private boolean isRatesOveriden;
	
	public RatesGridChannelRatesRules() {
		
	}
	public RatesGridChannelRatesRules(String rooomRate, String extraAdultRate, String extraChildRate) {
		
		this.rooomRate = rooomRate;
		this.extraAdultRate = extraAdultRate;
		this.extraChildRate = extraChildRate;
	}
	public RatesGridChannelRatesRules(String minRule, boolean noCheckIn, boolean noCheckOut) {
		
		this.minRule = minRule;
		this.noCheckIn = noCheckIn;
		this.noCheckOut = noCheckOut;
	}
	public RatesGridChannelRatesRules(String minRule, boolean noCheckIn, boolean noCheckOut, String rooomRate,
			String extraAdultRate, String extraChildRate) {
		
		this.minRule = minRule;
		this.noCheckIn = noCheckIn;
		this.noCheckOut = noCheckOut;
		this.rooomRate = rooomRate;
		this.extraAdultRate = extraAdultRate;
		this.extraChildRate = extraChildRate;	
	}
	
	
	
	public RatesGridChannelRatesRules(String minRule, boolean noCheckIn, boolean noCheckOut, String rooomRate,
			String extraAdultRate, String extraChildRate, boolean isRatesOveriden) {
		super();
		this.minRule = minRule;
		this.noCheckIn = noCheckIn;
		this.noCheckOut = noCheckOut;
		this.rooomRate = rooomRate;
		this.extraAdultRate = extraAdultRate;
		this.extraChildRate = extraChildRate;
		this.isRatesOveriden = isRatesOveriden;
	}
	public String getMinRule() {
		return minRule;
	}
	public void setMinRule(String minRule) {
		this.minRule = minRule;
	}
	public boolean isNoCheckIn() {
		return noCheckIn;
	}
	public void setNoCheckIn(boolean noCheckIn) {
		this.noCheckIn = noCheckIn;
	}
	public boolean isNoCheckOut() {
		return noCheckOut;
	}
	public void setNoCheckOut(boolean noCheckOut) {
		this.noCheckOut = noCheckOut;
	}
	public String getRooomRate() {
		return rooomRate;
	}
	public void setRooomRate(String rooomRate) {
		this.rooomRate = rooomRate;
	}
	public String getExtraAdultRate() {
		return extraAdultRate;
	}
	public void setExtraAdultRate(String extraAdultRate) {
		this.extraAdultRate = extraAdultRate;
	}
	public String getExtraChildRate() {
		return extraChildRate;
	}
	public void setExtraChildRate(String extraChildRate) {
		this.extraChildRate = extraChildRate;
	}
	
	
	
	public boolean isRatesOveriden() {
		return isRatesOveriden;
	}
	public void setRatesOveriden(boolean isRatesOveriden) {
		this.isRatesOveriden = isRatesOveriden;
	}
	@Override
	public String toString() {
		return " RatesGridChannelRatesRules [minRule=" + minRule + ", noCheckIn=" + noCheckIn + ", noCheckOut="
				+ noCheckOut + ", rooomRate=" + rooomRate + ", extraAdultRate=" + extraAdultRate + ", extraChildRate="
				+ extraChildRate + "] \n";
	}
	
	
}
