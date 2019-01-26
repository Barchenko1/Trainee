<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.ArrayList"%>
<html>
    <head>
       <meta charset="utf-8">
       <%@ include file="/WEB-INF/jspf/head.jspf" %>
       <link rel="stylesheet" href="style/css/bootstrap.css">
       <link rel="stylesheet" href="style/css/st4.css">
       <script type='text/javascript' src='https://code.jquery.com/jquery-latest.min.js'></script>
       <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
<body>
<h1 align="center">Add User</h1>
<br/>
  <form:form method="post" action="/registration" name="userform" modelAttribute="user" class="needs-validation">
<table>
    <tr>
        <td align="right">Login</td>
        <td>
            <input name="login" value="${user.login}" type="login" id="exampleLogin"  required>
        </td>
    </tr>
    <tr>
        <td align="right">Password</td>
        <td>
            <input name="password" type="password" id="exampleLogin" required>
        </td>
     </tr>
     <tr>
        <td align="right">Password again</td>
        <td>
             <input name="passwordAgain"  type="password" id="exampleLogin" required>
        </td>
    </tr>

  <tr>
      <td align="right">First Name</td>
      <td>
          <input name="firstName" value="${user.firstName}" type="text" required>
      </td>
  </tr>
<tr>
    <td align="right">Last Name</td>
    <td>
          <input name="lastName" value="${user.lastName}" type="text" required>
    </td>
  </tr>
  <tr>
          <td align="right">Email</td>
          <td>
                <input name="email" value="${user.email}" type="email" required>
          </td>
    </tr>
 <tr>
       <td align="right">Birthday</td>
       <td>
            <input name="birthday" type = "date" value="${user.birthday}" required>
       </td>
  </tr>
  <tr>
    </tr>

    <tr>
    <td align="right">Captcha</td>
    <td><div class="g-recaptcha" data-sitekey="6LdQXn8UAAAAAAZXH-kntqORD1z62x-6m0kCW0NF"></div></td>
    </tr>

    <tr>
        <td><p><input id="button1" class="btn btn-success" method="post" type="submit" name="Submit" value="Save"> <a href="/login" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></p></td>
    </tr>

</table>
</form:form>
<div class="center">
    <c:out value="${error}"/>
</div>
</body>
</html>