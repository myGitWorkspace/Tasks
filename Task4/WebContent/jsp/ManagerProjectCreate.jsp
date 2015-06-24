<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create new Project</title>
</head>
<body>

<script language="javascript" type="text/javascript">

var tasks_count = ${tasksCount};

function action_submit() {

if (document.form.title.value == "")
{
        alert("Please, enter the title for Project !!!");
        return;
}

if (document.form.description.value == "")
{
        alert("Please, enter description for Project !!!");
        return;
}

reg = /^[1-9]{1,3}$/;
deadline_elem = document.getElementById("deadline");
var deadline_val = reg.exec(deadline_elem.value);
if (deadline_val == null)
{
        alert("Please, enter a valid deadline (number of months) !!!");
        return;
}


for (i=1; i<(tasks_count+1); i++) {

var left_progr_number_elem_name = "left_number_progr" + i;
var left_number_progr = parseInt(document.getElementById(left_progr_number_elem_name).innerText);
if (left_number_progr > 0)
{
        alert("Please, select "+left_number_progr+" more programmers for task "+i+" !!!");
        return;
}

}

document.form.submit();

}


function select_programmers(id) {

var select_elem = document.getElementById(id);
if (select_elem.selectedIndex == 0)
	return;

var programmer_name = select_elem.options[select_elem.selectedIndex].text;
var programmer_id = select_elem.options[select_elem.selectedIndex].value;
var task_id = id.substring(11,id.length);
select_elem.options[select_elem.selectedIndex].disabled = true;

var selected_progr_number_elem_name = "selected_number_progr" + task_id;
var selected_number_progr = parseInt(document.getElementById(selected_progr_number_elem_name).innerText);
selected_number_progr = selected_number_progr+1; 
document.getElementById(selected_progr_number_elem_name).innerHTML = selected_number_progr;

var left_progr_number_elem_name = "left_number_progr" + task_id;
var left_number_progr = parseInt(document.getElementById(left_progr_number_elem_name).innerText);
left_number_progr = left_number_progr-1; 
document.getElementById(left_progr_number_elem_name).innerHTML = left_number_progr;


var new_node = document.createElement("div");
var hidden_id_progr_name = "programmer" + task_id + "_id"+selected_number_progr;
var new_node_content = programmer_name + '<input type="hidden" name="' + hidden_id_progr_name + '" value="' + programmer_id + '" >';
new_node.innerHTML=new_node_content;

var display_elem_name = "selected_programmers" + task_id;
var display_elem = document.getElementById(display_elem_name);
display_elem.appendChild(new_node);


var hidden_progr_number_elem_name = "number_selected_programmers" + task_id;
document.getElementById(hidden_progr_number_elem_name).value = selected_number_progr;

}

</script>



<h1>Create new Project by manager ${userName}</h1>

<form name="form" method="post">

<table border="0" cellpadding="5px" >
<tr >
<td style="text-align:right;width:150px;">
<b>Project Title</b>
</td>
<td>
<div>
<input type="text" name="title" id="title" size="50" />
<div>
<c:out value="${technicalTaskTitle}" />
</div>
</div>
</td>
</tr>
<tr>
<td style="text-align:right;">
<b>Description</b>
</td>
<td>
<div>
<textarea name="description" id="description"  rows="7" style="width:335px;"/></textarea>
<div>
<c:out value="${technicalTaskDescription}" />
</div>
</div>
</td>
</tr>
<tr>
<td style="text-align:right;">
<b>Deadline<br>(number of months)</b>
</td>
<td>
<input type="text" name="deadline" id="deadline" size="1" />
</td>
</tr>

<c:forEach var="task" items="${tasks}" varStatus="counter">
<tr>
<td style="text-align:right;">
<b>Task - ${counter.count}<br><c:out value="${task.technology}" /> - <c:out value="${task.skillLevel}" /> - <c:out value="${task.programmersNumber}" /></b>
</td>
<td>
<div style="float:left;">
<div>
<select name="programmers${counter.count}" id="programmers${counter.count}" onchange="select_programmers('programmers${counter.count}')" />
<option value="-- Select Programmers --">-- Select Programmers --</option>
<c:forEach var="programmer" items="${task.programmers}">
<option value="${programmer.id}">${programmer.firstName}</option>
</c:forEach>
</select>
</div>
<div>
Selected - <span id="selected_number_progr${counter.count}" style="color:red;font-weight:bold;">0</span> Left - <span id="left_number_progr${counter.count}" style="color:red;font-weight:bold;">${task.programmersNumber}</span>
</div>
</div>
<div id="selected_programmers${counter.count}" style="float:left;padding-left:10px;border:solid red 0px;">
<input type="hidden" id="number_selected_programmers${counter.count}" name="number_selected_programmers${counter.count}" value="0" >
</div>                     

</td>
</tr>
</c:forEach>

</table>
	

<div style="border:solid red 0px; padding:15px 0px 20px 240px;">
<div style="float:left;">
<input type=button value="Save" onClick="action_submit()" style="width:100px;">
</div>
<div style="float:left; padding-left:15px;">
<input type=button value="Cancel" onClick="history.go(-1)" style="width:100px;">
</div>
</div>
<input type="hidden" name="technicalTaskId" value="${technicalTaskId}" >
<input type="hidden" name="tasksCount" value="${tasksCount}" >
<input type="hidden" name="command" value="ManagerProjectSave" >
</form>

</body>
</html>