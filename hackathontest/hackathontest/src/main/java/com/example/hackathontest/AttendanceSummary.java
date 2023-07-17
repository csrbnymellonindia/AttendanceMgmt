package com.example.hackathontest;

public class AttendanceSummary {
    private int totalStudents;
    private int presentStudents;
    private int absentStudents;
    private double presentPercentage;
    private double absentPercentage;
    
    public AttendanceSummary() {
    }
    
    public AttendanceSummary(int totalStudents, int presentStudents, int absentStudents, double presentPercentage, double absentPercentage) {
        this.totalStudents = totalStudents;
        this.presentStudents = presentStudents;
        this.absentStudents = absentStudents;
        this.presentPercentage = presentPercentage;
        this.absentPercentage = absentPercentage;
    }
    
    public int getTotalStudents() {
        return totalStudents;
    }
    
    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
    
    public int getPresentStudents() {
        return presentStudents;
    }
    
    public void setPresentStudents(int presentStudents) {
        this.presentStudents = presentStudents;
    }
    
    public int getAbsentStudents() {
        return absentStudents;
    }
    
    public void setAbsentStudents(int absentStudents) {
        this.absentStudents = absentStudents;
    }
    
    public double getPresentPercentage() {
        return presentPercentage;
    }
    
    public void setPresentPercentage(double presentPercentage) {
        this.presentPercentage = presentPercentage;
    }
    
    public double getAbsentPercentage() {
        return absentPercentage;
    }
    
    public void setAbsentPercentage(double absentPercentage) {
        this.absentPercentage = absentPercentage;
    }
}
