package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/delete")
public class DeleteVisitorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        // 🔐 Session & Role Check
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            res.sendRedirect("login");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null) {
            res.sendRedirect("view");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM visitors WHERE id = ?"
            );
            ps.setInt(1, Integer.parseInt(idParam));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        res.sendRedirect("view");
    }
}
