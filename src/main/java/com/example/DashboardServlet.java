package com.example;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html;charset=UTF-8");

        res.getWriter().println("""
<html>
<head>
    <title>Access Denied</title>
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>

<div class="error-container">
    <div class="error-card">

        <h1>Access Denied</h1>

        <p class="message">
            Sorry, you do not have permission to access the Admin Dashboard.
        </p>

        <p class="sub-message">
            This area is restricted to <strong>ADMIN users only</strong>.
        </p>

        <div class="actions">
            <a href="login">
                <button class="btn primary">Go to Login</button>
            </a>
        </div>

    </div>
</div>

</body>
</html>

        """);
    }
}
