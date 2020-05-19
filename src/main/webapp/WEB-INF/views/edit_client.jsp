<%--
  Created by IntelliJ IDEA.
  User: ytkae
  Date: 17.05.2020
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Edit Client</title>
</head>
<body>
<div align="center">
    <h2>Edit Client</h2>
    <form:form action="save" method="post" modelAttribute="client">
        <jsp:useBean id="client" scope="page" type="ru.ancndz.libraryBase.content.entity.Client"/>

        <table border="0" cellpadding="5">
            <tr>
                <td>ID: </td>
                <td>${client.id}
                    <form:hidden path="id"/>
                </td>
            </tr>
            <tr>
                <td>First name: </td>
                <td><form:input path="firstName" /></td>
            </tr>
            <tr>
                <td>Second name: </td>
                <td><form:input path="secondName" /></td>
            </tr>
            <tr>
                <td>Email: </td>
                <td><form:input path="email" /></td>
            </tr>
            <tr>
                <td>Status: </td>
                <td><form:input path="status" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
