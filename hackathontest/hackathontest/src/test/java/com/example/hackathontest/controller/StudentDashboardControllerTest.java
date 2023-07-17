package com.example.hackathontest.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.hackathontest.StudentDetails;
import com.example.hackathontest.service.StudentService;


public class StudentDashboardControllerTest {
	
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentDashboardController controller;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testSumma()
    {
    	String a=null;
    	assertNull(a);
    }

    @Test
    public void testGetStudentByIdentifier() {
        // Mocking
        String userIdentifier = "123";
        StudentDetails mockStudent = new StudentDetails();
        when(studentService.getStudentDetails(userIdentifier)).thenReturn(mockStudent);

        // Test
        StudentDetails result = controller.getStudentByIdentifier(userIdentifier);

        // Verification
        assertSame(mockStudent, result);
    }

    @Test
    public void testGetAttendance() {
        // Mocking
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        String userId = "123";
        List<Date> mockAttendance = new ArrayList<>();
        when(studentService.getAttendance(Date.valueOf(startDate), Date.valueOf(endDate), userId))
                .thenReturn(mockAttendance);

        // Test
        List<Date> result = controller.getAttendance(startDate, endDate, userId);

        // Verification
        assertSame(mockAttendance, result);
    }
}
