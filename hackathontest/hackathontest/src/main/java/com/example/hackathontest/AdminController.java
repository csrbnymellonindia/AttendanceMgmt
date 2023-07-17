package com.example.hackathontest;

import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/attendance-summary")
    public AttendanceSummary getAttendanceSummary(@RequestParam("userId") String userId,
                                                 @RequestParam("date") String date) {
        // Parse the date string into a java.sql.Date object
        Date attendanceDate = Date.valueOf(date);

        return adminService.getAttendanceSummary(userId, attendanceDate);
    }
    
    @GetMapping("/attendance-summary-by-grade")
    public AttendanceSummary getAttendanceSummaryByGrade(@RequestParam("userId") String userId,
                                                        @RequestParam("grade") String grade,
                                                        @RequestParam("date") String date) {
        // Parse the date string into a java.sql.Date object
        Date attendanceDate = Date.valueOf(date);

        return adminService.getAttendanceSummaryByGrade(userId, grade, attendanceDate);
    }
    
    @GetMapping("/get-student-names")
    public List<StudentNameDetails> getStudentNames(@RequestParam("userId") String userId){
        return adminService.getStudentDetails(userId);
    }
}
