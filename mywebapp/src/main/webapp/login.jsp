<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../style/css/bootstrap.css">
    <link rel="stylesheet" href="../style/css/st4.css">
</head>
<body>
<h2 class="center">Login Page</h2>
<form class="center" id="login_form" action="loginService" method="post">
    <fieldset>
        <legend>Login</legend>
        <input name="login"/>
    </fieldset>
    <br>
    <fieldset>
        <legend>Password</legend>
        <input type="password" name="password"/>
    </fieldset>
    <br/>
    <p><button class="btn btn-success" type="submit">Log In</button></p>
</form>

</body>
</html>