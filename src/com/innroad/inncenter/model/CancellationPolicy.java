package com.innroad.inncenter.model;

public class CancellationPolicy {

	/**
	 * Create by Muhammad Haider
	 * Model For Cancellation Policy
	 */
	
	private String policyName;
	private String clauseName;
	private String guestsWillIncurAFee;
	private String chargesType;
	private String noOfDays;
	private String cancelWithInType;
	private String calculatedAmount;
	
	

	public CancellationPolicy(String policyName, String clauseName, String guestsWillIncurAFee, String chargesType,
			String noOfDays, String cancelWithInType, String calculatedAmount) {
		
		this.policyName = policyName;
		this.clauseName = clauseName;
		this.guestsWillIncurAFee = guestsWillIncurAFee;
		this.chargesType = chargesType;
		this.noOfDays = noOfDays;
		this.cancelWithInType = cancelWithInType;
		this.calculatedAmount = calculatedAmount;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getClauseName() {
		return clauseName;
	}

	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}

	public String getGuestsWillIncurAFee() {
		return guestsWillIncurAFee;
	}

	public void setGuestsWillIncurAFee(String guestsWillIncurAFee) {
		this.guestsWillIncurAFee = guestsWillIncurAFee;
	}

	public String getChargesType() {
		return chargesType;
	}

	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getCancelWithInType() {
		return cancelWithInType;
	}

	public void setCancelWithInType(String cancelWithInType) {
		this.cancelWithInType = cancelWithInType;
	}

	public String getCalculatedAmount() {
		return calculatedAmount;
	}

	public void setCalculatedAmount(String calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}

	@Override
	public String toString() {
		return " [policyName=" + policyName + ", clauseName=" + clauseName + ", guestsWillIncurAFee="
				+ guestsWillIncurAFee + ", chargesType=" + chargesType + ", noOfDays=" + noOfDays
				+ ", cancelWithInType=" + cancelWithInType + ", calculatedAmount=" + calculatedAmount + "]";
	}
	
	
	
}
