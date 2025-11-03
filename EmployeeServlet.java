package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.db.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String searchId = request.getParameter("searchId");
        
        out.println("<html><head><title>Employee Records</title></head><body>");
        out.println("<h2>Employee Management</h2>");
        
        out.println("<form action='employees' method='get'>");
        out.println("Search by Employee ID: <input type='text' name='searchId'>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form><br>");
        out.println("<a href='employees'>View All Employees</a><hr>");

        String sql;
        if (searchId != null && !searchId.isEmpty()) {
            sql = "SELECT * FROM Employee WHERE EmpID = ?";
        } else {
            sql = "SELECT * FROM Employee";
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (searchId != null && !searchId.isEmpty()) {
                pstmt.setInt(1, Integer.parseInt(searchId));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                out.println("<table border='1'>");
                out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
                
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("EmpID") + "</td>");
                    out.println("<td>" + rs.getString("Name") + "</td>");
                    out.println("<td>" + rs.getBigDecimal("Salary") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                
                if (!found) {
                    out.println("<p>No records found.</p>");
                }
            }
        } catch (SQLException e) {
            out.println("<h3>Error retrieving data: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
        
        out.println("</body></html>");
        out.close();
    }
}
