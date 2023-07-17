package com.example.hackathontest;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

@Entity
public class StudentDetails {
	
	@Id
	private String USER_IDENTIFIER;
	private int STUDENT_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String CLASS_ID;
	private int class_roll_no;
	private int days_present;
	private int days_absent;
	private List<Date> AbsentDates;
	private float percentage;
	private String teacherName;
	
	
	public String getUSER_IDENTIFIER() {
		return USER_IDENTIFIER;
	}
	public void setUSER_IDENTIFIER(String uSER_IDENTIFIER) {
		USER_IDENTIFIER = uSER_IDENTIFIER;
	}
	public int getSTUDENT_id() {
		return STUDENT_id;
	}
	public void setSTUDENT_id(int sTUDENT_id) {
		STUDENT_id = sTUDENT_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getCLASS_ID() {
		return CLASS_ID;
	}
	public void setCLASS_ID(String cLASS_ID) {
		CLASS_ID = cLASS_ID;
	}
	public int getClass_roll_no() {
		return class_roll_no;
	}
	public void setClass_roll_no(int class_roll_no) {
		this.class_roll_no = class_roll_no;
	}
	public int getDays_present() {
		return days_present;
	}
	public void setDays_present(int days_present) {
		this.days_present = days_present;
	}
	public int getDays_absent() {
		return days_absent;
	}
	public void setDays_absent(int days_absent) {
		this.days_absent = days_absent;
	}
	public List<Date> getAbsentDates() {
		return AbsentDates;
	}
	public void setAbsentDates(List<Date> absentDates) {
		AbsentDates = absentDates;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public StudentDetails(String uSER_IDENTIFIER, int sTUDENT_id, String first_name, String middle_name,
			String last_name, String cLASS_ID, int class_roll_no, int days_present, int days_absent,
			List<Date> absentDates, float percentage, String teacherName) {
		super();
		USER_IDENTIFIER = uSER_IDENTIFIER;
		STUDENT_id = sTUDENT_id;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		CLASS_ID = cLASS_ID;
		this.class_roll_no = class_roll_no;
		this.days_present = days_present;
		this.days_absent = days_absent;
		AbsentDates = absentDates;
		this.percentage = percentage;
		this.teacherName = teacherName;
	}
	
	public StudentDetails()
	{
		super();
	}
}
