<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<html>
    <head>
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
        <link rel="stylesheet" href="../../style/css/bootstrap.css">
        <link rel="stylesheet" href="../../style/css/st4.css">
        <title>User Account</title>
    </head>
          <body>
                <form class="center">
                    <h1>Hello, <c:out value="${firstName}!"/></h1>
                    <br/>
                    <h4>Click <a href="/logout">here</a> to logout</h4>
                </form>
          </body>
</html>