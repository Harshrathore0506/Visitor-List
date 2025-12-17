package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddVisitorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        // 🔐 Session & Role Check
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            res.sendRedirect("login");
            return;
        }

        String adminEmail = (String) session.getAttribute("email");

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                """
                INSERT INTO visitors
                (name, phone, purpose, visit_date, email, password, role)
                VALUES (?,?,?,?,?,?,?)
                """
            );

            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("phone"));
            ps.setString(3, req.getParameter("purpose"));
            ps.setDate(4, Date.valueOf(req.getParameter("date")));

            // visitor is added BY admin, not AS admin
            ps.setString(5, adminEmail);

            // temporary placeholder (better than hardcoded admin password)
            ps.setString(6, "visitor"); 

            ps.setString(7, "VISITOR");

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("view");
    }
}
