package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.db.DatabaseUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/markAttendance")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String studentId = request.getParameter("studentId");
        String attnDateStr = request.getParameter("attnDate");
        String status = request.getParameter("status");
        
        String sql = "INSERT INTO Attendance (StudentID, AttnDate, Status) VALUES (?, ?, ?)";
        String message = "";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            pstmt.setDate(2, Date.valueOf(attnDateStr));
            pstmt.setString(3, status);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                message = "Attendance marked successfully for " + studentId;
            } else {
                message = "Failed to mark attendance.";
            }
            
        } catch (SQLException e) {
            message = "Database Error: " + e.getMessage();
            e.printStackTrace();
        }
        
        request.setAttribute("message", message);
        RequestDispatcher rd = request.getRequestDispatcher("attendance.jsp");
        rd.forward(request, response);
    }
}
