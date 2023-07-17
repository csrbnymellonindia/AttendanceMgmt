package com.example.hackathontest;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

public class NewAttendance {
	
	private String StudentID;
	private LocalDate date;
	private String ClassID;
	
}

