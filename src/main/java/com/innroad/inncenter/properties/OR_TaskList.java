package com.innroad.inncenter.properties;

public class OR_TaskList {
	
	public static final String toDo = "//span[contains(@class,'gs-statsValue color-red')]";
	public static final String inspection = "//span[contains(@class,'gs-statsValue color-orange')]";
	public static final String done = "//span[contains(@class,'gs-statsValue color-green')]";
	public static final String all = "//span[contains(@class,'gs-statsValue color-blue')]";
	public static final String taskreportToDo = "//span[@class='gs-statsName' and text()='To Do']/preceding-sibling::span";
	public static final String taskreportInspection = "//span[@class='gs-statsName' and text()='Inspection']/preceding-sibling::span";
	public static final String taskreportDone = "//span[@class='gs-statsName' and text()='Done']/preceding-sibling::span";
	public static final String taskreportAll = "//span[@class='gs-statsName' and text()='All']/preceding-sibling::span";
	public static final String tasklistHeader = "//div[contains(@class,'TLHeading')]/h2/span[text()='Task List']";
	public static final String allBar = "//li[@class='all']";

}
