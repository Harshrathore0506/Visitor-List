package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewVisitorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);

        // 🔐 Session check
        if (session == null) {
            res.sendRedirect("login");
            return;
        }

        String role = (String) session.getAttribute("role");
        String email = (String) session.getAttribute("email");

        // 🔐 Role-based access
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            res.sendRedirect("dashboard");
            return;
        }

        List<String[]> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql;

            // ADMIN → see own created visitors
            if ("ADMIN".equals(role)) {
                sql = "SELECT * FROM visitors WHERE email = ?";
            }
            // STAFF → see all visitors (or change as needed)
            else {
                sql = "SELECT * FROM visitors";
            }

            PreparedStatement ps = con.prepareStatement(sql);

            if ("ADMIN".equals(role)) {
                ps.setString(1, email);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("purpose"),
                    rs.getString("visit_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("visitors", list);
        req.getRequestDispatcher("visitors.jsp").forward(req, res);
    }
}
