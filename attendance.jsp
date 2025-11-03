<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Attendance Portal</title>
</head>
<body>
    <h2>Mark Student Attendance</h2>
    
    <form action="markAttendance" method="post">
        Student ID: <input type="text" name="studentId" required><br/><br/>
        
        Date: <input type="date" name="attnDate" required><br/><br/>
        
        Status:
        <input type="radio" name="status" value="Present" checked> Present
        <input type="radio" name="status" value="Absent"> Absent
        <br/><br/>
        
        <input type="submit" value="Submit Attendance">
    </form>
    
    <br/>
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <p style="color:green;"><%= message %></p>
    <%
        }
    %>
</body>
</html>
