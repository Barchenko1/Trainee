<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/table.tld" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
</head>
<body>
<div align="right">
    <h3><c:out value="${firstName}" /> <c:out value="${lastName}"/>(<a href="/logout">Logout</a>)</h3>
</div>
<div align = "left">
    <h3><a href="/create">Add new user</a></h3>
</div>
<form class= "center">
    <myTag:table users="${users}" />
</form>
<div class="center">
    <c:out value="${error}"/>
</div>
</body>
</html>