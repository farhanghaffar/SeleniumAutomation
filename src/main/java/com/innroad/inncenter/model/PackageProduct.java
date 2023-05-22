package com.innroad.inncenter.model;

public class PackageProduct {

	private String productName;
	private String productAmount;
	private String productAppliedOn;
	private String productCalculateOn;
	public PackageProduct() {
		super();
	}
	
	public PackageProduct(String productName, String productAmount, String productAppliedOn,
			String productCalculateOn) {
		super();
		this.productName = productName;
		this.productAmount = productAmount;
		this.productAppliedOn = productAppliedOn;
		this.productCalculateOn = productCalculateOn;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}
	public String getProductAppliedOn() {
		return productAppliedOn;
	}
	public void setProductAppliedOn(String productAppliedOn) {
		this.productAppliedOn = productAppliedOn;
	}
	public String getProductCalculateOn() {
		return productCalculateOn;
	}
	public void setProductCalculateOn(String productCalculateOn) {
		this.productCalculateOn = productCalculateOn;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Product Name : " + this.productName + " \n"
				+ "Product Amount : " + this.productAmount + "\n"
						+ "Product Applied On : " + this.productAppliedOn + "\n"
								+ "Product Calculate On : " + this.productCalculateOn;
	}
	
	
	
}
