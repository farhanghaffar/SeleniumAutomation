package com.innroad.inncenter.endpoints;


public class Test {
	
	public static Boolean tem(int v1, int v2) {
		
		if((v1 > 100 && v2<0) || (v1<0 && v2>100))
		return true;
		return false;
	}

	public static void main(String[] args) {

		System.out.println(tem(120, -1));
		System.out.println(tem(-1, 120));
		System.out.println(tem(2, 100));

	}

}
