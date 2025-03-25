package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UserServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/vamsi";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Vamsi$97";

    // Method to display the list of users
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("add")) {
            // Redirect to the add user form
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addUser.jsp");
            dispatcher.forward(request, response);
        } else {
            // List users
            listUsers(request, response);
        }
    }

    // List users from the database
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            // Set the users as an attribute to be accessed by the JSP
            request.setAttribute("users", rs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listUsers.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

    // Method to handle POST requests for adding a new user
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                String query = "INSERT INTO users (name, email) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Redirect to list page after adding the user
                        response.sendRedirect("users");
                    } else {
                        response.getWriter().println("<p>Error adding user.</p>");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
            }
        } else {
            response.getWriter().println("<p>Both name and email are required.</p>");
        }
    }
}
