<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<html>
    <head>
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
        <link rel="stylesheet" href="../style/css/bootstrap.css">
        <link rel="stylesheet" href="../style/css/st4.css">
    </head>
        <body>
            <form class="center">
                <h1>Hello, <c:out value="${user.getFirstName()}!"/></h1>
                <br/>
                <h4>Click <a href="logoutService">here</a> to logout<h4>
            </form>
        </body>
</html>
