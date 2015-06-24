<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
  <style>
   #centerLayer {
    position: absolute;
    width: 190px;
    height: 240px;
    left: 50%;
    top: 50%;
    margin-left: -195px;
    margin-top: -190px;
    background: #f3f6ff;
    border: solid 1px black;
    padding: 10px;
    overflow: auto;
   }
  </style>
</head>
<body>
<div id="centerLayer">
<h1>Login</h1>

<form name="loginForm" method="POST" action="controller">
<input type="hidden" name="command" value="login" />
<div style="border:solid black 0px;">
<div style="padding-right:10px;">
User Name
</div>
<input type="text" name="login" value="" size="25"/>
</div>
<div style="margin-bottom:15px;border:solid black 0px;">
<div style="padding-right:10px;">
Password
</div>
<input type="password" name="password" value="" size="25"/>
</div>

<input type="button" value="Log in" onClick="document.loginForm.submit()" style="width:100px;">
</form>

<div style="padding-top:10px;">
<a href="controller?command=Registration">Registration</a>
</div>

</div>
	<br/>
	${errorLoginPassMessage}
	<br/>
	${wrongAction}
	<br/>
	${nullPage}
	<br/>
</body>
</html>