package com.example.hackathontest.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathontest.StudentDetails;
import com.example.hackathontest.service.StudentService;

@RestController
public class StudentDashboardController {
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/students/{USER_IDENTIFIER}")
    public StudentDetails getStudentByIdentifier(@PathVariable("USER_IDENTIFIER") String userIdentifier) {
		return studentService.getStudentDetails(userIdentifier);
    }
	
	@GetMapping("/attendance")
    public List<Date> getAttendance(
    	       @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
               @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
               @RequestParam("userId") String userId
       ) {
           java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
           java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
           return studentService.getAttendance(sqlStartDate, sqlEndDate, userId);
       }
	
}
