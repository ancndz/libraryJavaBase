<%--
  Created by IntelliJ IDEA.
  User: ytkae
  Date: 17.05.2020
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Clients</title>
</head>
<body>
<div align="center">
    <h2>Clients</h2>
<%--    <form method="get" action="search">--%>
<%--        <input type="text" name="keyword" />--%>
<%--        <input type="submit" value="Search" />--%>
<%--    </form>--%>
    <h3><a href="${pageContext.request.contextPath}/users/new">New Customer</a></h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>F. Name</th>
            <th>S. Name</th>
            <th>E-mail</th>
            <th>Status</th>
            <th>Registration date</th>
            <th>Action</th>
        </tr>
        <jsp:useBean id="clientList" scope="page" type="java.util.List"/>
        <c:forEach items="${clientList}" var="client">
            <tr>
                <td>${client.id}</td>
                <td>${client.firstName}</td>
                <td>${client.secondName}</td>
                <td>${client.email}</td>
                <td>${client.status}</td>
                <td>${client.dateReg}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/edit-user?id=${client.id}">Edit</a>
                    <a href="${pageContext.request.contextPath}/users/delete?id=${client.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
