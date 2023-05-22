package com.innroad.inncenter.model;

public class ReservationNotes {

	private String NOTES_TYPE;
	private String NOTES_SUBJECT;
	private String NOTES_DESCRIPTION;
	private String NOTES_UPDATED_BY;
	private String NOTES_UPDATED_ON;
	public ReservationNotes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReservationNotes(String nOTES_TYPE, String nOTES_SUBJECT, String nOTES_DESCRIPTION, String nOTES_UPDATED_BY,
			String nOTES_UPDATED_ON) {
		super();
		NOTES_TYPE = nOTES_TYPE;
		NOTES_SUBJECT = nOTES_SUBJECT;
		NOTES_DESCRIPTION = nOTES_DESCRIPTION;
		NOTES_UPDATED_BY = nOTES_UPDATED_BY;
		NOTES_UPDATED_ON = nOTES_UPDATED_ON;
	}
	public String getNOTES_TYPE() {
		return NOTES_TYPE;
	}
	public void setNOTES_TYPE(String nOTES_TYPE) {
		NOTES_TYPE = nOTES_TYPE;
	}
	public String getNOTES_SUBJECT() {
		return NOTES_SUBJECT;
	}
	public void setNOTES_SUBJECT(String nOTES_SUBJECT) {
		NOTES_SUBJECT = nOTES_SUBJECT;
	}
	public String getNOTES_DESCRIPTION() {
		return NOTES_DESCRIPTION;
	}
	public void setNOTES_DESCRIPTION(String nOTES_DESCRIPTION) {
		NOTES_DESCRIPTION = nOTES_DESCRIPTION;
	}
	public String getNOTES_UPDATED_BY() {
		return NOTES_UPDATED_BY;
	}
	public void setNOTES_UPDATED_BY(String nOTES_UPDATED_BY) {
		NOTES_UPDATED_BY = nOTES_UPDATED_BY;
	}
	public String getNOTES_UPDATED_ON() {
		return NOTES_UPDATED_ON;
	}
	public void setNOTES_UPDATED_ON(String nOTES_UPDATED_ON) {
		NOTES_UPDATED_ON = nOTES_UPDATED_ON;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "RESERVATION NOTES : "
				+ "\n Notes Type : " + NOTES_TYPE
				+ "\n Notes Subject : " + NOTES_SUBJECT
				+ "\n Notes Description : " + NOTES_DESCRIPTION
				+ "\n Notes Updated By : " + NOTES_UPDATED_BY
				+ "\n Notes Updated On : " + NOTES_UPDATED_ON; 
	}
	
	
}
