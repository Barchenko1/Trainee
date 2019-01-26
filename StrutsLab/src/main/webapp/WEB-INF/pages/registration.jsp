<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/WEB-INF/jspf/head.jspf" %>
   <link rel="stylesheet" href="../../style/css/bootstrap.css">
   <link rel="stylesheet" href="../../style/css/st4.css">
   <script type='text/javascript' src='https://code.jquery.com/jquery-latest.min.js'></script>
   <script src='https://www.google.com/recaptcha/api.js'></script>
   <%@ taglib prefix="s" uri="/struts-tags" %>
   <title>Register</title>
  </head>
<body>

<div class="center"><h2>Add User</h2></div>
<s:form action="/regPost" class="needs-validation">
<table class="center">
       <tr>
           <td>
           <s:textfield name="user.login" label="Login" required="true"/>
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
               <s:password name="passwordAgain" label="Password again" required="true"/>
               </td>
    </tr>

     <tr>
                <td>
           <s:textfield name="user.firstName" label="First Name" required="true" />
                </td>
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
          <td></td>
              <td><div class="g-recaptcha" data-sitekey="6LdBXn8UAAAAADpyfsrCfNAUPqeROsb8xKdLpiF2"></div></td>
          </tr>

          <tr>
              <td></td>
              <td><br/><br/><input id="button1" class="btn btn-success" method="post" type="submit" name="Submit" value="Save"> <a href="/login" class="btn btn-success" role="button" aria-pressed="true">Cancel</a></td>
          </tr>
   </table>
   </s:form>
   <br/>
    <s:fielderror class="center" />
    <s:actionerror class= "center" />
  </body>
</html>