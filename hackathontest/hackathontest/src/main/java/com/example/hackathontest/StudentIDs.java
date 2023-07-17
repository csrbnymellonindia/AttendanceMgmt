package com.example.hackathontest;

import java.util.List;

public class StudentIDs {
    private List<Integer> studentId;

    public List<Integer> getStudentId() {
        return studentId;
    }

    public void setStudentId(List<Integer> studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StudentIDs [studentId=" + studentId + "]";
    }
}
