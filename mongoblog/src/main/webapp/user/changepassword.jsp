<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user.nick} - Change password</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
	<script src="../../js/mongoblog.js"></script>
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<form method="post" id="fpassword" name="fpassword" action="../savepasswordchange">
			<fieldset>
			<legend>Change Password</legend>
			<input type="hidden" name="id" value="${user.id}">
			<label for="oldpassword">Old password</label>
			<input type="password" id="oldpassword" name="oldpassword" value="" required><br>
			<label for="newpassword">New Password:</label>
			<input type="password" id="newpassword" name="newpassword" value="" required><br>
			<label for="confirmpassword">Comfirm new password:</label>
			<input type="password" id="confirmpassword" name="confirmpassword" value="" required><br>
			<input type="submit" value="update">
			<div id="error">
			${error}
			</div>
			</fieldset>
			</form>
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="../config/${sessionScope.loggeduser.id}">config</a><br>
				<a href="../../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="../../login">login</a>
			</c:if>
		</div>
	</div>
</body>
<script>
	
	document.getElementById("fpassword").addEventListener("submit", function(event) {
			
			var error = !this.checkValidity;
			var div = cleanDom("error");
			if ( document.getElementById("oldpassword").value == document.getElementById("newpassword").value)
			{
				div.appendChild( document.createTextNode("The new password it's the same as the old one"));
				div.appendChild( document.createElement("br"));
				error = true;				
			}
			if  ( document.getElementById("newpassword").value != document.getElementById("confirmpassword").value)
			{
				div.appendChild( document.createTextNode("The new password and the confirm new password fields don't match"));
				div.appendChild( document.createElement("br"));
				error = true;
			}
			if ( error )
			{
				event.preventDefault();		
			}
			
		});
</script>
</html>