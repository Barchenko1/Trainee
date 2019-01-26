<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="en">
    <head>
        <%@ include file="/WEB-INF/jspf/head.jspf" %>
                   <link rel="stylesheet" href="../../style/css/bootstrap.css">
                   <link rel="stylesheet" href="../../style/css/st4.css">
         <script type='text/javascript' src='https://code.jquery.com/jquery-latest.min.js'></script>
    </head>
<body>
<div class="center"><h2>Edit User</h2></div>
<s:form action="editPost" class="center" >
<table>
    <tr>
        <td>
        <s:textfield name="user.login" label="Login" readonly="true" required="true"/>
        <s:fielderror fieldName="user.login" />
         </td>
    </tr>
    <tr>
            <td>
            <s:password name="user.password" label="Password" required="true"/>
             </td>
     </tr>
     <tr>
                <td>
            <s:password name="user.passwordAgain" label="Password again" required="true"/>
                  </td>
 </tr>

  <tr>
                  <td>
        <s:textfield name="user.firstName" label="First Name" required="true" />
                   </td>
        <s:fielderror fieldName="user.firstName" />
  </tr>
<tr>
                  <td>
        <s:textfield name="user.lastName" label="Last Name" required="true" />
                   </td>
        <s:fielderror fieldName="user.lastName" />
  </tr>
  <tr>
                    <td>
        <s:textfield name="user.email" label="Email" required="true" />
                     </td>
    </tr>
 <tr>

                  <td>
<s:textfield name="birthday" label="Birthday" type="date" required="true" />
</td>
  </tr>
  <tr>
  <td><s:select label="Select Role"
         list="#{'1':'ADMIN', '2':'USER'}" name="role"
         value="selectedSubject" /></td>
    </tr>

    <tr>
        <td></td>
        <td><br/><br/><input class="btn btn-success" method="post" type="submit" name="Submit" value="Save"> <a href="/admin" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></td>
    </tr>
</table>
</s:form>
<s:fielderror class="center" />
<s:actionerror class= "center" />
</body>
</html>