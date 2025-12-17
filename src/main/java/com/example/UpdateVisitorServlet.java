package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class UpdateVisitorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        // 🔐 Session + Role Check (ADMIN or STAFF allowed)
        if (session == null) {
            res.sendRedirect("login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            res.sendRedirect("dashboard");
            return;
        }

        String idParam = req.getParameter("id");
        String purpose = req.getParameter("purpose");

        if (idParam == null || purpose == null || purpose.isBlank()) {
            res.sendRedirect("view");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "UPDATE visitors SET purpose = ? WHERE id = ?"
            );

            ps.setString(1, purpose.trim());
            ps.setInt(2, Integer.parseInt(idParam));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("view");
    }
}
