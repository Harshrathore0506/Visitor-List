<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Visitor List</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<%
    // 🔐 Session validation
    if (session == null || session.getAttribute("name") == null) {
        response.sendRedirect("login");
        return;
    }
%>

<div class="card">

    <!-- 🔹 Top Bar -->
    <div class="top-bar">
        <form action="logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>

    <h2>Visitor List</h2>

    <table border="1" cellpadding="8" cellspacing="0" width="100%">
        <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Purpose</th>
            <th>Visit Date</th>
            <th>Action</th>
        </tr>

        <%
            List<String[]> visitors =
                (List<String[]>) request.getAttribute("visitors");

            if (visitors != null && !visitors.isEmpty()) {
                for (String[] v : visitors) {
                    String visitorName = v[1]; // 👈 IMPORTANT
        %>
        <tr>
            <td><%= visitorName %></td>
            <td><%= v[2] %></td>

            <!-- UPDATE PURPOSE -->
            <td>
                <form method="post" action="update">
                    <input type="hidden" name="id" value="<%= v[0] %>">
                    <input type="text" name="purpose" value="<%= v[3] %>" required>
                    <button type="submit">Update</button>
                </form>
            </td>

            <td><%= v[4] %></td>

            <!-- DELETE (Hidden ONLY if visitor name is ADMIN) -->
            <td>
                <% if (!"ADMIN".equalsIgnoreCase(visitorName)) { %>
                    <form method="post" action="delete" style="display:inline;">
                        <input type="hidden" name="id" value="<%= v[0] %>">
                        <button type="submit"
                                onclick="return confirm('Delete this visitor?')">
                            Delete
                        </button>
                    </form>
                <% } else { %>
                    <span style="color:gray;">Protected</span>
                <% } %>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5" style="text-align:center;">No visitors found</td>
        </tr>
        <%
            }
        %>

    </table>

    <br>
    <a href="addVisitor.html">Add New Visitor</a>

</div>

</body>
</html>
