package com.example.hackathontest.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hackathontest.EmailService;
import com.example.hackathontest.StudentIDs;

@CrossOrigin(origins = "*")
@Controller
public class AttendanceController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/editAttendance/{Student_ID}/{date}/{ClassID}/{val}")
	@ResponseBody // Add this annotation to indicate that the return value should be serialized directly to the response body
	public String editSingleStudentAttendance(@PathVariable int Student_ID, @PathVariable String date,
	        @PathVariable String ClassID, @PathVariable String val) {
	    try {
	        java.sql.Date sqldate = java.sql.Date.valueOf(date);
	    	String updatepresent = "UPDATE xxxt_student_clas_dtls SET DY_PRSNT_NR = ? WHERE CLAS_ID = ? AND STUDENT_ID = ?";
			String updateabsent = "UPDATE xxxt_student_clas_dtls SET DY_ABSENT_NR = ? WHERE CLAS_ID = ? AND STUDENT_ID = ?";
			String sql1= "select * from xxxt_student_clas_dtls where CLAS_ID=? and STUDENT_ID=?" ;
			List<Map<String,Object>> data = jdbcTemplate.queryForList(sql1,ClassID,Student_ID);
	    	Map<String, Object> firstElement=data.get(0);
	    	int numabsent=(Integer)firstElement.get("DY_ABSENT_NR");
	    	int numpresent=(Integer)firstElement.get("DY_PRSNT_NR");
	        if (val.equals("true")) {
	            String sql = "DELETE FROM xxxT_ABS_MAP WHERE STUDENT_ID = ? AND CLAS_ID = ? AND ABS_DT = ?";
	            jdbcTemplate.update(sql, Student_ID, ClassID, sqldate);
	    		jdbcTemplate.update(updateabsent,numpresent+1, ClassID, Student_ID);
	    		jdbcTemplate.update(updatepresent,numabsent-1, ClassID, Student_ID);
	            return "marked present successfully";
	        } else {
	            String sql = "INSERT INTO xxxT_ABS_MAP (STUDENT_ID, CLAS_ID, ABS_DT) VALUES (?, ?, ?)";
	            jdbcTemplate.update(sql, Student_ID, ClassID, sqldate);
	        	jdbcTemplate.update(updateabsent,numpresent-1, ClassID, Student_ID);
	    		jdbcTemplate.update(updatepresent,numabsent+1, ClassID, Student_ID);
	            return "marked absent successfully";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "An error occurred during attendance processing";
	    }
	}
	@ResponseBody
	@PostMapping("/addAttendance/{date}/{classId}")
	public void addMultipleStudentAttendance(@RequestBody StudentIDs studentIDs, @PathVariable String date, @PathVariable String classId) {
	    java.sql.Date sqlDate = java.sql.Date.valueOf(date);

	    List<Integer> studentIds = studentIDs.getStudentId();
	    
	    String selectQuery = "SELECT * FROM xxxT_STUDENT_CLAS_DTLS WHERE CLAS_ID = ?";
	    List<Map<String, Object>> studentsData = jdbcTemplate.queryForList(selectQuery, classId);
	    
	    if (studentsData.isEmpty()) {
	        return;
	    }
	    
	    Set<Integer> presentStudents = new HashSet<>();
	    
	    for (Integer studentId : studentIds) {
	        String insertQuery = "INSERT INTO xxxT_ABS_MAP (STUDENT_ID, CLAS_ID, ABS_DT) VALUES (?, ?, ?)";
	        jdbcTemplate.update(insertQuery, studentId, classId, sqlDate);
	        presentStudents.add(studentId);
	    }
	    
	    for (Map<String, Object> studentData : studentsData) {
	        Integer studentId = (Integer) studentData.get("STUDENT_ID");
	        Integer presentCount = (Integer) studentData.get("DY_PRSNT_NR");
	        Integer absentCount = (Integer) studentData.get("DY_ABSENT_NR");
	        
	        String updatePresentQuery = "UPDATE xxxT_STUDENT_CLAS_DTLS SET DY_PRSNT_NR = ? WHERE CLAS_ID = ? AND STUDENT_ID = ?";
	        String updateAbsentQuery = "UPDATE xxxT_STUDENT_CLAS_DTLS SET DY_ABSENT_NR = ? WHERE CLAS_ID = ? AND STUDENT_ID = ?";
	        
	        if (presentStudents.contains(studentId)) {
	            jdbcTemplate.update(updateAbsentQuery, absentCount + 1, classId, studentId);
	            absentCount++;
	        } else {
	            jdbcTemplate.update(updatePresentQuery, presentCount + 1, classId, studentId);
	            presentCount++;
	        }
	        
	        int totalAttendancePercentage = (presentCount * 100) / (presentCount + absentCount);
	        
//	        System.out.println(totalAttendancePercentage);
	        
	        if (totalAttendancePercentage < 40) {
	            String teacherEmail = getEmailByClassId(classId); // Replace with your logic to get teacher's email based on classId
	            String studentFullName = getStudentFullName(studentId); // Replace with your logic to get student's full name based on studentId
	            int studentRollNo = getStudentRollNo(studentId); // Replace with your logic to get student's roll number based on studentId
	            
	            emailService.sendEmailToTeacherIfAttendanceFallsBelowThreshold(teacherEmail, studentFullName, studentRollNo, classId);
//	            System.out.println("DONE");
	        }
	    }
	}
	private String getEmailByClassId(String classId) {
	    String query = "SELECT sd.EMAIL_ID FROM xxxT_STAFF_DTLS sd " +
	                   "JOIN xxxT_CLAS_DTLS cd ON sd.STAFF_ID = cd.STAFF_ID " +
	                   "WHERE cd.CLAS_ID = ?";
	    
	    return jdbcTemplate.queryForObject(query, String.class, classId);
	}

	private String getStudentFullName(Integer studentId) {
	    String query = "SELECT CONCAT(STUDENT_FST_NM, ' ', STUDENT_MID_NM, ' ', STUDENT_LAST_NM) " +
	                   "FROM xxxT_STUDENT_DTLS " +
	                   "WHERE STUDENT_ID = ?";
	    
	    return jdbcTemplate.queryForObject(query, String.class, studentId);
	}

	private int getStudentRollNo(Integer studentId) {
	    String query = "SELECT CLAS_ROLL_NR FROM xxxT_STUDENT_CLAS_DTLS WHERE STUDENT_ID = ?";
	    
	    return jdbcTemplate.queryForObject(query, int.class, studentId);
	}
	
//	CREATE TABLE xxxT_ABS_MAP(
//			STUDENT_ID INTEGER,
//			CLAS_ID VARCHAR(4),
//			ABS_DT date,
//			foreign key(CLAS_ID)
//			REFERENCES xxxT_CLAS_DTLS(CLAS_ID),
//			foreign key(STUDENT_ID)
//			REFERENCES xxxT_STUDENT_DTLS(STUDENT_ID)
//			);
}
