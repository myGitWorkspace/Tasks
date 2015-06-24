<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All projects of the company</title>
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



<h1>All projects of the company</h1>


<table border="1" cellpadding="5px" >
<tr bgcolor="#d4d6e0">
<td style="text-align:center;width:150px;">
<b>Id</b>
</td>
<td style="text-align:center;width:150px;">
<b>Project Title</b>
</td>
<td style="text-align:center;width:300px;">
<b>Description</b>
</td>
<td style="text-align:center;width:100px;">
<b>Programmers in project</b>
</td>
<td style="text-align:center;width:100px;">
<b>Project start date</b>
</td>
<td style="text-align:center;width:100px;">
<b>Deadline</b>
</td>
<td style="text-align:center;width:100px;">
<b>Current status</b>
</td>

</tr>

<c:forEach var="project" items="${projects}">
<tr>
<td>
<c:out value="${project.id}" />
</td>

<td>
<div>
<c:out value="${project.title}" />
</div>
<div>
<c:out value="${project.technicalTask.title}" />
</div>
</td>

<td>
<div>
<c:out value="${project.description}" />
</div>
<div>
<c:out value="${project.technicalTask.description}" />
</div>
</td>

<td>
<div>
<c:forEach var="programmer" items="${project.programmers}">
<c:out value="${programmer.firstName}" />, 
</c:forEach>
<br>
</div>
<div>
<c:forEach var="task" items="${project.technicalTask.tasks}">
<c:out value="${task.technology}" /> <c:out value="${task.skillLevel}" />(<c:out value="${task.programmersNumber}" />),  
</c:forEach>
</div>
</td>

<td>
<c:out value="${project.creationDate.time}" />
</td>

<td>
<c:out value="${project.deadline.time}" />
</td>

<td>
<c:out value="${project.projectState}" />
</td>

</tr>
</c:forEach>

</table>

<br><br>

<h1>New technical tasks of the customers</h1>


<table border="1" cellpadding="5px" >
<tr bgcolor="#d4d6e0">
<td style="text-align:center;width:150px;">
<b>Id</b>
</td>
<td style="text-align:center;width:150px;">
<b>Customer name</b>
</td>
<td style="text-align:center;width:150px;">
<b>Technical task title</b>
</td>
<td style="text-align:center;width:300px;">
<b>Description</b>
</td>
<td style="text-align:center;width:100px;">
<b>Tasks</b>
</td>
<td style="text-align:center;width:100px;">
<b>Creation date</b>
</td>
<td style="text-align:center;width:100px;">
<b>Action</b>
</td>

</tr>

<c:forEach var="technicalTask" items="${technicalTasks}" varStatus="technicalTaskCounter">
 
<tr>
<td>
<c:out value="${technicalTask.id}" />
</td>

<td>
<c:out value="${technicalTask.customerName}" />
</td>

<td>
<c:out value="${technicalTask.title}" />
</td>

<td>
<c:out value="${technicalTask.description}" />
</td>

<td>
<c:forEach var="task" items="${technicalTask.tasks}" varStatus="counter">
<c:out value="${task.technology}" /> - <c:out value="${task.skillLevel}" />(<c:out value="${task.programmersNumber}" />)
<c:set var="taskCount" scope="page" value="${counter.count}"/>
</c:forEach>
</td>

<td>
<c:out value="${technicalTask.creationDate.time}" />
</td>

<td>
<form name="form${technicalTaskCounter.count}" method="post">
<input type="hidden" name="command" value="ManagerProjectCreate">
<input type="hidden" name="tasksCount" value="${taskCount}">
<input type="hidden" name="technicalTaskId" value="${technicalTask.id}">
<input type="hidden" name="technicalTaskTitle" value="${technicalTask.title}">
<input type="hidden" name="technicalTaskDescription" value="${technicalTask.description}">
<input type="button" value="Create Project" onClick="document.form${technicalTaskCounter.count}.submit()" style="width:100px;">
</form>
</td>

</tr>

</c:forEach>

</table>
<br><br>
<a href="controller?command=logout">Logout</a>

</body>

</html>