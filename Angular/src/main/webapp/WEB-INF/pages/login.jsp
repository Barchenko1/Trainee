<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
</head>
<body>
<form method="post" action="/login" class="center">
    <h2>Login Page</h2>
    <fieldset>
        <legend>Login</legend>
        <input name="login" type="text" required>
    </fieldset>
    <fieldset>
        <legend>Password</legend>
        <input name="password" type="password" required>
    </fieldset>
    <p><button class="btn btn-success" type="submit">Log In</button></p>
</form>
<div class="center">
   <p><a href="/registration" class="btn btn-success" role="button" aria-pressed="true">Registration</a></p>
    <c:out value="${error}"/>
</div>
</body>
</html>