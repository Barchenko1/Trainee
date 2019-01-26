<!doctype html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.ArrayList"%>
<html lang="en">
    <head>
       <%@ include file="/WEB-INF/jspf/head.jspf" %>
           <link rel="stylesheet" href="../../style/css/bootstrap.css">
           <link rel="stylesheet" href="../../style/css/st4.css">
    </head>
        <body>
<div class="center"><h2>Add User</h2></div>
<table>
 <s:form action="createPost" class="center" >
    <tr>
        <td>
        <s:textfield name="user.login" label="Login" required="true"/>
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
<s:fielderror fieldName="user.firstName" />
                  <td>
        <s:textfield name="user.firstName" label="First Name" required="true" />
                  </td>
        <s:fielderror fieldName="user.firstName" />
  </tr>
<tr>
                  <td>
        <s:textfield name="user.lastName" label="Last Name" required="true" />
                   </td>
  </tr>
  <tr>
                    <td>
        <s:textfield name="user.email" label="Email" required="true"/>
                     </td>
    </tr>
 <tr>

         <td>
<s:textfield name="birthday" type="date" required="true" label="Birthday" />
        </td>
  </tr>
  <tr>
  <td><s:select label="Select Role"
            list="#{'1':'ADMIN', '2':'USER'}" name="role"
            value="defaultSubject" />
            </td>
    <tr>
        <td></td>
        <td></p><br/><br/> <input class="btn btn-success" method="post" type="submit" name="Submit" value="Save"> <a href="/admin" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></p></td>
    </tr>

</table>
</s:form>
<div class="center">
<s:fielderror />
<s:actionerror />
</div
</body>
</html>