package com.example.hackathontest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AttendanceSummary getAttendanceSummary(String userId, Date date) {
        // Check if the provided userId corresponds to an admin
        boolean isAdmin = isAdmin(userId);
        
        if (isAdmin) {
            int totalStudents = getTotalStudents();
            // int presentStudents = getPresentStudents(date);
            int absentStudents = getAbsentStudents(date);
            int presentStudents = totalStudents - absentStudents;
            
            double presentPercentage = calculatePercentage(presentStudents, totalStudents);
            double absentPercentage = calculatePercentage(absentStudents, totalStudents);
            
            AttendanceSummary attendanceSummary = new AttendanceSummary();
            attendanceSummary.setTotalStudents(totalStudents);
            attendanceSummary.setPresentStudents(presentStudents);
            attendanceSummary.setAbsentStudents(absentStudents);
            attendanceSummary.setPresentPercentage(presentPercentage);
            attendanceSummary.setAbsentPercentage(absentPercentage);
            
            return attendanceSummary;
        }
        return null;
    }
    
    public AttendanceSummary getAttendanceSummaryByGrade(String userId, String grade, Date date) {
        // Check if the provided userId corresponds to an admin
        boolean isAdmin = isAdmin(userId);
        
        if (isAdmin) {
            int totalStudents = getTotalStudentsByGrade(grade);
            // int presentStudents = getPresentStudentsByGrade(grade, date);
            int absentStudents = getAbsentStudentsByGrade(grade, date);
            int presentStudents = totalStudents - absentStudents;
            
            double presentPercentage = calculatePercentage(presentStudents, totalStudents);
            double absentPercentage = calculatePercentage(absentStudents, totalStudents);
            
            AttendanceSummary attendanceSummary = new AttendanceSummary();
            attendanceSummary.setTotalStudents(totalStudents);
            attendanceSummary.setPresentStudents(presentStudents);
            attendanceSummary.setAbsentStudents(absentStudents);
            attendanceSummary.setPresentPercentage(presentPercentage);
            attendanceSummary.setAbsentPercentage(absentPercentage);
            
            return attendanceSummary;
        }
        return null;
    }
    
    private int getTotalStudentsByGrade(String grade) {
        // Retrieve the total number of students in the specified grade from the database
        String query = "SELECT COUNT(*) FROM xxxT_STUDENT_CLAS_DTLS scd " +
                       "JOIN xxxT_CLAS_DTLS cd ON scd.CLAS_ID = cd.CLAS_ID " +
                       "WHERE cd.GRADE_TX = ?";
        
        return jdbcTemplate.queryForObject(query, Integer.class, grade);
    }

    private int getAbsentStudentsByGrade(String grade, Date date) {
        // Retrieve the number of absent students for the given grade and date from the xxxT_ABS_MAP table
        String query = "SELECT COUNT(DISTINCT scd.STUDENT_ID) FROM xxxT_STUDENT_CLAS_DTLS scd " +
                       "JOIN xxxT_CLAS_DTLS cd ON scd.CLAS_ID = cd.CLAS_ID " +
                       "LEFT JOIN xxxT_ABS_MAP am ON scd.STUDENT_ID = am.STUDENT_ID " +
                       "WHERE cd.GRADE_TX = ? AND am.ABS_DT = ?";
    
        return jdbcTemplate.queryForObject(query, Integer.class, grade, date);
    }
    
    private boolean isAdmin(String userId) {
        // Check if the user with the provided userId is an admin based on the schema
        String query = "SELECT COUNT(*) FROM xxxT_USR_LOGN_DTL uld " +
                       "JOIN xxxT_PRFL_FETR_MAP pfm ON uld.USR_PRFL_ID = pfm.USR_PRFL_ID " +
                       "WHERE uld.USR_ID = ? AND pfm.FETR_ID = 2"; // Assuming FETR_ID = 2 represents the admin role
        
        int count = jdbcTemplate.queryForObject(query, Integer.class, userId);
        
        return count > 0;
    }
    
    private int getTotalStudents() {
        // Retrieve the total number of students from the database
        String query = "SELECT COUNT(*) FROM xxxT_STUDENT_DTLS";
        
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
    
    // private int getPresentStudents(Date date) {
    //     // Retrieve the number of present students for the given date from the database
    //     String query = "SELECT COUNT(*) FROM xxxT_STUDENT_CLAS_DTLS WHERE DY_PRSNT_NR > 0 AND DATE_COLUMN = ?";
        
    //     return jdbcTemplate.queryForObject(query, Integer.class, date);
    // }
    
    // private int getAbsentStudents(Date date) {
    //     // Retrieve the number of absent students for the given date from the database
    //     String query = "SELECT COUNT(*) FROM xxxT_STUDENT_CLAS_DTLS WHERE DY_ABSENT_NR > 0 AND DATE_COLUMN = ?";
        
    //     return jdbcTemplate.queryForObject(query, Integer.class, date);
    // }

    private int getAbsentStudents(Date date) {
        // Retrieve the number of absent students for the given date from the xxxT_ABS_MAP table
        String query = "SELECT COUNT(DISTINCT scd.STUDENT_ID) FROM xxxT_STUDENT_CLAS_DTLS scd " +
                       "LEFT JOIN xxxT_STUDENT_DTLS sd ON scd.STUDENT_ID = sd.STUDENT_ID " +
                       "LEFT JOIN xxxT_ABS_MAP am ON sd.STUDENT_ID = am.STUDENT_ID " +
                       "WHERE am.ABS_DT = ?";
    
        return jdbcTemplate.queryForObject(query, Integer.class, date);
    }
    
    
    private double calculatePercentage(int count, int total) {
        // Calculate the percentage
        if (total > 0) {
            return ((double) count / total) * 100;
        } else {
            return 0.0;
        }
    }
    public List<StudentNameDetails> getStudentDetails(String userId) {
        // Check if the provided userId corresponds to an admin
        boolean isAdmin = isAdmin(userId);

        if (isAdmin) {
            List<StudentNameDetails> studentDetailsList = new ArrayList<>();

            String query = "SELECT usm.USR_ID, CONCAT(s.STUDENT_FST_NM, ' ', s.STUDENT_MID_NM, ' ', s.STUDENT_LAST_NM) AS full_name " +
                    "FROM xxxT_USR_STUDENT_MAP usm " +
                    "JOIN xxxT_STUDENT_DTLS s ON usm.STUDENT_ID = s.STUDENT_ID";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

            for (Map<String, Object> row : rows) {
                String studentUserId = (String) row.get("USR_ID");
                String fullName = (String) row.get("full_name");

                StudentNameDetails studentDetails = new StudentNameDetails();
                studentDetails.setUserId(studentUserId);
                studentDetails.setFullName(fullName);

                studentDetailsList.add(studentDetails);
            }

            return studentDetailsList;
        }

        return null;
    }
}
