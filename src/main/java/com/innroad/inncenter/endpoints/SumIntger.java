package com.innroad.inncenter.endpoints;

public class SumIntger {

	public double calculateArea(Double lenth, Double widht) {
		
		
		return (lenth*widht);
	}
	
	public static void main(String[] args) {
		
		
		SumIntger obj = new SumIntger();

		System.out.println("Calculate area: "+obj.calculateArea(2.0, 2.5));
	}

}
