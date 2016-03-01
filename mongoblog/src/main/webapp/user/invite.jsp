<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user.nick} - Invite user</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
	<script src="../../js/mongoblog.js"></script>
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<form method="post" id="finvite" name="finvite" action="../sendinvite">
			<fieldset>
			<legend>Invite a friend</legend>
			<input type="hidden" name="id" value="${user.id}">
			<label for="email">email</label>
			<input type="email" id="email" name="email" value="" required><br>
			<label for="confirmEmail">Confirm email:</label>
			<input type="email" id="confirmEmail" name="confirmEmail" value="" required><br>
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
	
	document.getElementById("finvite").addEventListener("submit", function(event) {
			
			var error = !this.checkValidity;
			var div = cleanDom("error");
			if  ( document.getElementById("email").value != document.getElementById("confirmEmail").value)
			{
				document.getElementById("error").appendChild( document.createTextNode("The email and the confirm email fields don't match"));
				document.getElementById("error").appendChild( document.createElement("br"));
				error = true;
			}
			if ( error )
			{
				event.preventDefault();		
			}			
		});
	
	document.getElementById("email").onchange = function() {
		document.getElementById("confirmEmail").value = "";
	}
	
</script>
</html>