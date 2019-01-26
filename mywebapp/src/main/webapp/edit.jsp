<!doctype html>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ page import="java.util.ArrayList"%>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../style/css/bootstrap.css">
    <link rel="stylesheet" href="../style/css/st4.css">
</head>
<body>

<div class="alert" role="alert">
    <c:out value="${errorMessage}"/>
</div>

<h1 align="center">Edit User</h1>
<br/>
<form name="update" method="post">
    <table align="center" >
        <tr>
            <td align="right">Login</td>
            <td>
                <input name="login" type="text" value="${editLogin}" readonly>
            </td>
        </tr>
        <tr>
            <td align="right">Password</td>
            <td>
                <input name="password" type="password" value="${editPassword}" required>
            </td>
        </tr>
        <tr>
            <td align="right">Password again</td>
            <td>
                <input name="passwordAgain" type="password" value="${editPassword}" required>
            </td>
        </tr>

        <tr>
            <td align="right">First Name</td>
            <td>
                <input name="firstName" type="text" value="${editFirstname}" required>
            </td>
        </tr>
        <tr>
            <td align="right">Last Name</td>
            <td>
                <input name="lastName" type="text" value="${editLastname}" required>
            </td>
        </tr>
        <tr>
            <td align="right">Email</td>
            <td>
                <input name="email" type="email" value="${editEmail}" required>
            </td>
        </tr>
        <tr>
            <td align="right">Birthday</td>
            <td>
                <input name="date" type="date" value="${editBirthday}" required>
            </td>
        </tr>

        <tr>
            <td align="right">Role</td>
            <td><select name="role" class="selectpicker">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.getRoleId()}"><c:out value="${role.getName()}"/></option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td><p> <input method="post" class="btn btn-success" type="submit" name="Submit" value="Save">  <a href="adminAccount.jsp" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></p></td>
        </tr>
    </table>
</form>
</body>
</html>

