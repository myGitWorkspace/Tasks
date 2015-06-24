<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Statistics for programmer</title>
</head>
<body onload="get_date()">


<script language="javascript" type="text/javascript">

function action_submit() {

reg = /^[1-9]{1,2}$/;
hours_number_elem = document.getElementById("hours_number");	
var hours_number_val = reg.exec(hours_number_elem.value);
if (hours_number_val == null)
{
        alert("Please, enter a valid number of hours spent on project !!!");
        return;
}

document.form.submit();

}


function get_date() {

var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();

if(dd<10) {
    dd='0'+dd
} 

if(mm<10) {
    mm='0'+mm
} 

today = mm+'/'+dd+'/'+yyyy;
var date_elem = document.getElementById("dateholder");
date_elem.innerHTML = today;

}

</script>

<h1>Statistics for programmer ${userName}</h1>

<form name="form" method="post">

<table border="1" cellpadding="5px" >
<tr bgcolor="#d4d6e0">
<td style="text-align:center;width:150px;">
<b>Project Id</b>
</td>
<td style="text-align:center;width:150px;">
<b>Current Project Title</b>
</td>
<td style="text-align:center;width:300px;">
<b>Description</b>
</td>
<td style="text-align:center;width:100px;">
<b>Deadline</b>
</td>

</tr>
<tr>
<td>
<div>
${project.id}
</div>
</td>

<td>
<div>
${project.title}
</div>
</td>

<td>
<div>
${project.description}
</div>
</td>

<td>
${project.deadline.time}
</td>
</tr>

</table>

<br><br>

<div style="float:left;">
Today
</div>
<div id="dateholder" style="float:left;padding-left:10px;padding-right:10px;">
</div>
<div style="float:left;">
Hours spent on project
</div>
<div style="float:left;padding-left:15px;">
<input type="text" id="hours_number" name="hours_number" size="1">
</div>
<div style="float:left;padding-left:20px;">
<input type=button value="Save" onClick="action_submit()" style="width:100px;">
</div>

<br><br><br>

<input type="hidden" name="projectId" value="${project.id}">
<input type="hidden" name="programmerId" value="${userId}">
<input type="hidden" name="command" value="saveProgrammerStatistics">
</form>
<a href="controller?command=logout">Logout</a>
</body>
</html>