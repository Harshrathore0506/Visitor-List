<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Visitor List</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<!-- 🔹 Top Bar -->

<div class="card">
    <h2>Visitor List</h2>
    <div class="top-bar">
        <a href="login">
            <button>Logout</button>
        </a>
    </div>

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
        %>
        <tr>
            <td><%= v[1] %></td>
            <td><%= v[2] %></td>
            <td>
                <form method="post" action="update">
                    <input type="hidden" name="id" value="<%= v[0] %>">
                    <input type="text" name="purpose" value="<%= v[3] %>" required>
                    <button type="submit">Update</button>
                </form>
            </td>
            <td><%= v[4] %></td>
            <td>
                <a href="delete?id=<%= v[0] %>"
                   onclick="return confirm('Delete this visitor?')">
                   Delete
                </a>
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
