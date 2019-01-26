<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="en">
    <head>
       <%@ include file="/WEB-INF/jspf/head.jspf" %>
           <link rel="stylesheet" href="../../style/css/bootstrap.css">
           <link rel="stylesheet" href="../../style/css/st4.css">
    </head>
          <body>
          <h1 class="center">Hello <c:out value="${firstName} ${lastName}"/></h1>
          <h4 class="center">Click <a href="logout">Logout</a> to logout</h4>
          </body>
</html>