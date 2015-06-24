<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Technical Tasks for customer</title>
</head>
<body>


<h1>List of Technical Tasks for customer ${userName}</h1>

<table border="1" cellpadding="5px">
<tr bgcolor="#d4d6e0">
<td style="text-align:center;width:150px;">
<b>Technical Task Title</b>
</td>
<td style="text-align:center;width:300px;">
<b>Description</b>
</td>
<td style="text-align:center;width:200px;">
<b>Technology tasks</b>
</td>
<td style="text-align:center;width:100px;">
<b>Programmers spend time</b>
</td>
<td style="text-align:center;width:100px;">
<b>Creation date</b>
</td>
<td style="text-align:center;width:100px;">
<b>DeadLine</b>
</td>
<td style="text-align:center;width:100px;">
<b>Current State</b>
</td>
</tr>

<c:forEach var="element" items="${technicalTask}">
<tr>
<td>
<c:out value="${element.title}" />
</td>
<td>
<c:out value="${element.description}" />
</td>
<td>
<c:forEach var="task" items="${element.tasks}">
<c:out value="${task.technology}" /> - <c:out value="${task.skillLevel}" />(<c:out value="${task.programmersNumber}" />)
</c:forEach>
</td>
<td>
<c:forEach var="workTime" items="${element.workHoursDaily}">
<c:out value="${workTime.key.time}" /> - <c:out value="${workTime.value}" /> hours<br>
</c:forEach>
</td>
<td>
<c:out value="${element.creationDate.time}" />
</td>
<td>
<c:out value="${element.deadline.time}" />
</td>
<td>
<c:out value="${element.technicalTaskState}" />
</td>
</tr>

</c:forEach>


</table>
<br><br>
<form name="form" method="post">
<input type=button value="Create New TechnicalTask" onClick="document.form.submit()" style="width:200px;">
<input type="hidden" name="command" value="CustomerTechnicalTaskCreate">
</form>
<br><br>
<a href="controller?command=logout">Logout</a>

</body>
</html>