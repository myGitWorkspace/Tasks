<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new Technical Task for customer</title>
</head>
<body>

<script language="javascript" type="text/javascript">

var tasks_count = 0;

function action_submit() {

if (document.form.title.value == "")
{
        alert("Please, enter the title for Technical task !!!");
        return;
}

if (document.form.description.value == "")
{
        alert("Please, enter description for Technical task !!!");
        return;
}

if (tasks_count == 0)
{
        alert("You must add at least 1 task !!!");
        return;
}

for (i=1; i<(tasks_count+1); i++) {

technology_elem = document.getElementById("technology"+i);
if (technology_elem.value == "-- Select Technology --")
{
        alert("Please, select Technology for Task # "+i);
        return;
}
skill_level_elem = document.getElementById("skill_level"+i);
if (skill_level_elem.value == "-- Select Skill Level --")
{
        alert("Please, select Skill Level for Task # "+i);
        return;
}
reg = /^[1-9]{1}$/;
amount_elem = document.getElementById("amount"+i);
var amount_val = reg.exec(amount_elem.value);
if ( amount_val == null )
{
        alert("Please, write correct field Amount ( must be a number [1-9] ) for Task # "+i);
        return;
}

}
document.form.tasksCount.value = tasks_count;
document.form.submit();

}


function add_column_titles() {

if (tasks_count == 0) {

div=document.getElementById("tasks");

newitem="";                                

newitem+="<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:620px;\">";

newitem+="<tr>";

        newitem+="<td style=\"width:120px;\">";
        newitem+="</td>";
        newitem+="<td style=\"text-align:center;width:200px;\">";
                newitem+="<b>Technology *</b>";
        newitem+="</td>";
        newitem+="<td style=\"text-align:center;width:200px;\">";
                newitem+="<b>Skill Level *</b>";
        newitem+="</td>";
        newitem+="<td style=\"text-align:center;width:100px;\">";
                newitem+="<b>Amount *</b>";
        newitem+="</td>";

newitem+="</tr>";

newitem+="</tr>";
newitem+="</table>";

newnode=document.createElement("div");
var newnode_name = "task_table_caption";
newnode.setAttribute("id", newnode_name);
newnode.innerHTML=newitem;
div.appendChild(newnode);

}


}

function add_task() {

add_column_titles();

if(tasks_count == 0) {
document.getElementById("delete_button").style.display = "block";
}

div=document.getElementById("tasks");

tasks_count++;

var name_elem="";
newitem="";                                

newitem+="<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:620px;\">";

newitem+="<tr>";

        newitem+="<td style=\"text-align:center;width:120px;\" >";
                newitem += "<h1>Task " + tasks_count + "</h1>";
        newitem+="</td>";

        newitem+="<td style=\"text-align:center;width:200px;\">";    
                name_elem="technology"+tasks_count;
                newitem+="<select name="+name_elem+" id="+name_elem+"  />";
                newitem+="<option value=\"-- Select Technology --\">-- Select Technology --</option>";
                newitem+="<option value=\"java\">Java</option>";
                newitem+="<option value=\"python\">Python</option>";
                newitem+="<option value=\"javascript\">Javascript</option>";
                newitem+="<option value=\"php\">Php</option>";
                newitem+="<option value=\"sql\">Sql</option>";
                newitem+="<option value=\"c_plus_plus\">C++</option>";
                newitem+="<option value=\"html\">Html</option>";                
                newitem+="</select>";

        newitem+="</td>";

        newitem+="<td style=\"text-align:center;width:200px;\">";    
                name_elem="skill_level"+tasks_count;
                newitem+="<select name="+name_elem+" id="+name_elem+" />";
                newitem+="<option value=\"-- Select Skill Level --\">-- Select Skill Level --</option>";
                newitem+="<option value=\"junior\">Junior</option>";
                newitem+="<option value=\"middle\">Middle</option>";
                newitem+="<option value=\"senior\">Senior</option>";
                newitem+="</select>";

        newitem+="</td>";

	newitem+="<td style=\"text-align:center;width:100px;\">";
        	name_elem="amount"+tasks_count;
		newitem+="<input type=\"text\" name="+name_elem+" id="+name_elem+"  size=\"4\" maxlength=\"100\" />";
	newitem+="</td>";

	

newitem+="</tr>";
newitem+="</table>";


newnode=document.createElement("div");
var newnode_name = "task_table_"+tasks_count;
newnode.setAttribute("id", newnode_name);
newnode.innerHTML=newitem;
div.appendChild(newnode);


}

function delete_task() {

if (tasks_count == 0)
	return;

var tasks_div=document.getElementById("tasks");

// child node .........
var table_name = "task_table_"+tasks_count;
table_elem = document.getElementById(table_name);

// remove child node ........
tasks_div.removeChild(table_elem);

if(tasks_count == 1) {
document.getElementById("delete_button").style.display = "none";
var caption = document.getElementById("task_table_caption");
tasks_div.removeChild(caption);
}

tasks_count-=1;

}

</script>



<h1>Add new Technical Task for customer ${userName}</h1>

<form name="form" method="post">

<table border="0" cellpadding="5px" >
<tr >
<td style="text-align:right;width:150px;">
<b>Technical Task Title</b>
</td>
<td>
<input type="text" name="title" id="title" size="50" />
</td>
</tr>
<tr>
<td style="text-align:right;">
<b>Description</b>
</td>
<td>
<textarea name="description" id="description"  rows="7" style="width:335px;"/></textarea>
</td>
</tr>
</table>
	

<div style="border:solid red 0px; padding:15px 0px 20px 240px;">
<div style="float:left;">
<input type=button value="Save" onClick="action_submit()" style="width:100px;">
</div>
<div style="float:left; padding-left:15px;">
<input type=button value="Cancel" onClick="history.go(-1)" style="width:100px;">
</div>
</div>

<br><br>

<div id="button" style="float:left; border:solid red 0px; padding-left:60px;">
<input type="button" value="Add new task" onClick="add_task()" style="width:100px;">
<br><br>
<input type="button" id="delete_button" value="Delete last task" onClick="delete_task()" style="width:120px;display:none;">
</div>

<div id="tasks" style="float:left; border:solid blue 0px; padding-left:20px;">
</div>
<input type="hidden" name="tasksCount" value=""/>
<input type="hidden" name="userId" value="${userId}"/>
<input type="hidden" name="command" value="CustomerTechnicalTaskSave"/>
</form>

</body>
</html>