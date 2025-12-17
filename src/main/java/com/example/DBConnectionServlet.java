package com.example;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DBConnectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/plain");

        try (Connection con = DBConnection.getConnection()) {
            res.getWriter().println("✅ PostgreSQL (Neon) DB Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("❌ DB Connection Failed!");
            res.getWriter().println(e.getMessage());
        }
    }
}
