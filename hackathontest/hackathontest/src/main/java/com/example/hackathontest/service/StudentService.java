package com.example.hackathontest.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hackathontest.StudentDetails;

@Service
public class StudentService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public StudentDetails getStudentDetails(String USER_IDENTIFIER)
	{
		StudentDetails student = new StudentDetails();
		String sql = "select * from xxxT_USR_STUDENT_MAP where USR_ID=?" ;
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,USER_IDENTIFIER);
		if(data==null||data.size()==0) {
			return null;
		}
		else 
		{
		    Map<String, Object> firstElement = data.get(0); // Accessing the first element
		    student.setUSER_IDENTIFIER((String)firstElement.get("USR_ID"));
		    student.setSTUDENT_id((int)firstElement.get("STUDENT_ID"));		    
		}
	    String sql1 = "select * from xxxT_STUDENT_DTLS where STUDENT_ID=?" ;
	    List<Map<String,Object>> data1 = jdbcTemplate.queryForList(sql1,student.getSTUDENT_id());
	    if(data1==null||data1.size()==0) {
			return null;
		}
		else 
		{
		    Map<String, Object> firstElement = data1.get(0); // Accessing the first element
		    student.setFirst_name((String)firstElement.get("STUDENT_FST_NM"));
		    student.setMiddle_name((String)firstElement.get("STUDENT_MID_NM"));
		    student.setLast_name((String)firstElement.get("STUDENT_LAST_NM"));
		}
	    String sql2 = "select * from xxxT_STUDENT_CLAS_DTLS where STUDENT_ID=?" ;
	    List<Map<String,Object>> data2 = jdbcTemplate.queryForList(sql2,student.getSTUDENT_id());
	    if(data1==null||data1.size()==0) {
			return null;
		}
		else 
		{
		    Map<String, Object> firstElement = data2.get(0); // Accessing the first element
		    student.setCLASS_ID((String)firstElement.get("CLAS_ID"));
		    student.setClass_roll_no((int)firstElement.get("CLAS_ROLL_NR"));
		    student.setDays_present((int)firstElement.get("DY_PRSNT_NR"));
		    student.setDays_absent((int)firstElement.get("DY_ABSENT_NR"));
		    float present= (float)student.getDays_present();
		    float absent = (float)student.getDays_absent();
		    float percentage = (present/(present+absent))*100;
		    student.setPercentage(percentage);
		}		
	    List<Date> dates_absent = new ArrayList<Date>();
	    String sql3 = "select * from xxxT_ABS_MAP where STUDENT_ID=?" ;
	    List<Map<String,Object>> data3 = jdbcTemplate.queryForList(sql3,student.getSTUDENT_id());
	    if(data3==null||data3.size()==0) {
			dates_absent=null;
		}
		else 
		{
			for (Map<String, Object> row : data3) {
				Date absentDate = (Date) row.get("ABS_DT");
				dates_absent.add(absentDate);
			}
		}	
	    student.setAbsentDates(dates_absent);
	    String sql4 = "SELECT STAFF_NM\r\n"
	    		+ "FROM xxxT_STAFF_DTLS\r\n"
	    		+ "INNER JOIN xxxT_CLAS_DTLS ON xxxT_STAFF_DTLS.STAFF_ID = xxxT_CLAS_DTLS.STAFF_ID\r\n"
	    		+ "WHERE CLAS_ID = ?;\r\n";
	    List<Map<String,Object>> data4 = jdbcTemplate.queryForList(sql4,student.getCLASS_ID());
	    if(data4==null||data4.size()==0) {
			return null;
		}
		else 
		{
			Map<String, Object> firstElement = data4.get(0); // Accessing the first element
		    student.setTeacherName((String)firstElement.get("STAFF_NM"));    
		}		
		return student;
	}
	
	public List<Date> getAttendance(Date startDate,Date endDate,String userIdentifier)
	{
		List<Date> dates_absent = new ArrayList<Date>();
		String sql="SELECT ABS_DT\r\n"
				+ "FROM xxxT_ABS_MAP\r\n"
				+ "WHERE STUDENT_ID IN (\r\n"
				+ "    SELECT STUDENT_ID\r\n"
				+ "    FROM xxxT_USR_STUDENT_MAP\r\n"
				+ "    WHERE USR_ID = ?\r\n"
				+ ")\r\n"
				+ "AND ABS_DT BETWEEN ? AND ?;\r\n";
		List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,userIdentifier,startDate,endDate);
	    if(data==null||data.size()==0) {
			dates_absent=null;
		}
		else 
		{
			for (Map<String, Object> row : data) {
				Date absentDate = (Date) row.get("ABS_DT");
				dates_absent.add(absentDate);
			}
		}	
	    return dates_absent;
	}
}
