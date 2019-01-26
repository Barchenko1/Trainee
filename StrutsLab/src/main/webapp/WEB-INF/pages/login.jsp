<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="../../style/css/bootstrap.css">
    <link rel="stylesheet" href="../../style/css/st4.css">
</head>
<body>

    <s:form action="login" method="post" class="center">
        <h2>Login Page</h2>
    	<s:textfield name="login" label="Login" />
    	<s:textfield name="password" label="Password" type="password" />
    	<s:submit class="btn btn-success" value="Login"></s:submit>
    </s:form>

<div class="center">
   <p><a href="/registration" class="btn btn-success" role="button" aria-pressed="true">Registration</a></p>
</div>

</body>
</html>