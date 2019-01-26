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
<div class="alert " role="alert">
    <c:out value="${errorMessage}"/>
</div>

<h1 align="center">Add User</h1>
<br/>
<form name="create" method="post">
<table align="center">
    <tr>
        <td align="right">Login</td>
        <td>
            <input name="login" type="login" required>
        </td>
    </tr>
    <tr>
        <td align="right">Password</td>
        <td>
            <input name="password" type="password" required>
        </td>
    </tr>
    <tr>
         <td align="right">Password again</td>
         <td>
             <input name="passwordAgain" type="password" required>
         </td>
    </tr>
    <tr>
        <td align="right">First Name</td>
        <td>
            <input name="firstName" type="text" required>
        </td>
    </tr>
    <tr>
        <td align="right">Last Name</td>
        <td>
            <input name="lastName" type="text" required>
        </td>
    </tr>
    <tr>
        <td align="right">Email</td>
        <td>
            <input name="email" type="email" required>
        </td>
    </tr>
    <tr>
        <td align="right">Birthday</td>
        <td>
            <input name="date" type="date" required>
        </td>
    </tr>
    <tr>
        <td align="right">Role</td>
        <td>
        <select name="role" class="selectpicker">
            <c:forEach items="${roles}" var="role">
                <option value="${role.getName()}"><c:out value="${role.getName()}"/></option>
            </c:forEach>
        </select>
        </td>
   </tr>
    <tr>
        <td><p> <input class="btn btn-success" method="post" type="submit" name="Submit" value="OK"> <a href="adminAccount.jsp" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></p></td>
    </tr>
</table>
</form>

</body>
</html>
