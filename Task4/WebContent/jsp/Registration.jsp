<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register new User</title>
</head>
<body>


<script language="javascript" type="text/javascript">

function action_submit() {

user_type_elem = document.getElementById("user_type");
if (user_type_elem.value == "-- Select User Type --")
{
        alert("Please, select User Type ");
        return;
}

if (document.form.first_name.value == "")
{
        alert("Please, enter First name !!!");
        return;
}

if (document.form.last_name.value == "")
{
        alert("Please, enter Last name !!!");
        return;
}

if (document.form.login.value == "")
{
        alert("Please, enter Login !!!");
        return;
}

reg=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
email_elem = document.getElementById("email");
if ( reg.exec(email_elem.value) == null )
{
	alert("Please, enter a valid e-mail address !!!");
	return;
}

if (document.form.password.value == "")
{
        alert("Please, enter Password !!!");
        return;
}

if (document.form.password2.value == "")
{
        alert("Please, enter Password confirm !!!");
        return;
}

if ( document.form.password.value != document.form.password2.value)
{
        alert("Passwords do not match each other !!!");
        return;
}

if (user_type_elem.value == "programmer") {
technology_elem = document.getElementById("technology");
if (technology_elem.value == "-- Select Technology --")
{
        alert("Please, select Technology !!!");
        return;
}
skill_level_elem = document.getElementById("skill_level");
if (skill_level_elem.value == "-- Select Skill Level --")
{
        alert("Please, select Skill Level !!!");
        return;
}
}

document.form.submit();

}


function select_user_type() {

var select_elem = document.getElementById("user_type");
var technology_title_elem = document.getElementById("technology_title");
var technology_container_elem = document.getElementById("technology_container");
var skill_level_title_elem = document.getElementById("skill_level_title");
var skill_level_container_elem = document.getElementById("skill_level_container");


if (select_elem.options[select_elem.selectedIndex].value == "programmer") {


technology_title_elem.innerHTML = "<b>Technology</b>";

var innerText = '<select name="technology" id="technology"/><option value="-- Select Technology --">-- Select Technology --</option><option value="java">Java</option><option value="python">Python</option><option value="javascript">Javascript</option><option value="php">Php</option><option value="sql">Sql</option><option value="c_plus_plus">C++</option><option value="html">Html</option></select>';
technology_container_elem.innerHTML = innerText;
skill_level_title_elem.innerHTML = "<b>Skill Level</b>";
innerText = '<select name="skill_level" id="skill_level" /><option value="-- Select Skill Level --">-- Select Skill Level --</option><option value="junior">Junior</option><option value="middle">Middle</option><option value="senior">Senior</option></select>';
skill_level_container_elem.innerHTML = innerText

} else {

technology_title_elem.innerHTML = "";
technology_container_elem.innerHTML = '';
skill_level_title_elem.innerHTML = "";
skill_level_container_elem.innerHTML = '';

}

}

</script>



<h1>Register new User</h1>

<form name="form" method="post" action="controller">

<table border="0" cellpadding="5px" >
<tr >
<td style="text-align:right;width:150px;">
<b>User Type</b>
</td>
<td>
<select name="user_type" id="user_type" onchange="select_user_type()" />
<option value="-- Select User Type --">-- Select User Type --</option>
<option value="programmer">Programmer</option>
<option value="customer">Customer</option>
<option value="manager">Manager</option>
</select>
</td>
</tr>
<tr>
<td style="text-align:right;">
<b>First name</b>
</td>
<td>
<input type="text" name="first_name" id="first_name" size="30" />
</td>
</tr>
<tr>
<td style="text-align:right;">
<b>Last name</b>
</td>
<td>
<input type="text" name="last_name" id="last_name" size="30" />
</td>
</tr>

<tr>
<td style="text-align:right;">
<b>Login</b>
</td>
<td>
<input type="text" name="login" id="login" size="30" />
</td>
</tr>

<tr>
<td style="text-align:right;">
<b>E-mail</b>
</td>
<td>
<input type="text" name="email" id="email" size="30" />
</td>
</tr>


<tr>
<td style="text-align:right;">
<b>Password</b>
</td>
<td>
<input type="password" name="password" id="password" size="30" />
</td>
</tr>

<tr>
<td style="text-align:right;">
<b>Password confirm</b>
</td>
<td>
<input type="password" name="password2" id="password2" size="30" />
</td>
</tr>

<tr>
<td style="text-align:right;">
<div id="technology_title">

</div>
</td>
<td>
<div id="technology_container">

</div>
</td>
</tr>


<tr>
<td style="text-align:right;">
<div id="skill_level_title">

</div>
</td>
<td>
<div id="skill_level_container">

</div>
</td>
</tr>

</table>
	

<div style="border:solid red 0px; padding:15px 0px 20px 170px;">
<input type="hidden" name="command" value="RegistrationSave" />
<input type=button value="Save" onClick="action_submit()" style="width:100px;">
</div>

</form>


</body>
</html>