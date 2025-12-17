package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html;charset=UTF-8");

        res.getWriter().println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Login</title>
                <link rel="stylesheet" href="css/style.css">
            </head>
            <body>

            <div class="container">
                <div class="card">
                    <h2>Login</h2>

                    <form method="post" action="login">
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="password" name="password" placeholder="Password" required>
                        <button type="submit">Login</button>
                    </form>
                </div>
            </div>

            </body>
            </html>
        """);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String username = null;
        String role = null;

        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                SELECT name, role
                FROM visitors
                WHERE email = ?
                AND password = ?
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                username = rs.getString("name");
                role = rs.getString("role");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (username != null) {

            HttpSession session = req.getSession();
            session.setAttribute("name", username);
            session.setAttribute("email", email);
            session.setAttribute("role", role);

            if ("ADMIN".equalsIgnoreCase(role)) {
                res.sendRedirect("view");       // Admin
            } else {
                res.sendRedirect("dashboard");  // Staff / Visitor
            }

        } else {
            // Login failed → back to login
            res.sendRedirect("login?error=invalid");
        }
    }
}
