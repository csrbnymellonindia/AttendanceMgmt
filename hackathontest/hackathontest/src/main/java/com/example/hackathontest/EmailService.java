package com.example.hackathontest;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Component
public class EmailService {
    // private final JavaMailSender javaMailSender;

    // public EmailService(JavaMailSender javaMailSender) {
    //     this.javaMailSender = javaMailSender;
    // }

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailToTeacherIfAttendanceFallsBelowThreshold(String teacherEmail, String studentName, int rollNo, String classId) {
        // Construct the email content
        String subject = "Attendance Alert: Student Below 40% Attendance";
        String message = "<html>\n" +
        "<head>\n" +
        "<style>\n" +
        "body {\n" +
        "  font-family: Arial, sans-serif;\n" +
        "}\n" +
        ".header {\n" +
        "  background-color: #f0f0f0;\n" +
        "  padding: 10px;\n" +
        "}\n" +
        ".content {\n" +
        "  padding: 20px;\n" +
        "}\n" +
        ".student-details {\n" +
        "  font-weight: bold;\n" +
        "}\n" +
        "</style>\n" +
        "</head>\n" +
        "<body>\n" +
        "<div class=\"header\">\n" +
        "<h2>Dear Teacher,</h2>\n" +
        "</div>\n" +
        "<div class=\"content\">\n" +
        "<p>This is to inform you that the following student in Class " + classId + " has attendance below 40%:</p>\n" +
        "<div class=\"student-details\">\n" +
        "<p>Student Name: " + studentName + "</p>\n" +
        "<p>Roll No: " + rollNo + "</p>\n" +
        "</div>\n" +
        "<p>Please take necessary actions to address this.</p>\n" +
        "<p>Regards,<br>Your School</p>\n" +
        "</div>\n" +
        "</body>\n" +
        "</html>";


        // Create a MimeMessage
        MimeMessage email = javaMailSender.createMimeMessage();

        try {
            // Set the properties of the email
            MimeMessageHelper helper = new MimeMessageHelper(email, true);
            helper.setTo(teacherEmail);
            helper.setSubject(subject);
            helper.setText(message, true);

            // Send the email
            javaMailSender.send(email);
        } catch (MessagingException e) {
            // Handle any exceptions that occur during email sending
            e.printStackTrace();
        }
    }
    
}
