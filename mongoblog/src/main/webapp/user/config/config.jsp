<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user.nick}</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<form method="post" id="fusuario" name="fusuario" action="../saveconfig">
			<fieldset>
			<legend>Config user</legend>
			<input type="hidden" name="id" value="${user.id}">
			<label for="nick">Nick:</label>
			<input type="text" id="nick" name="nick" value="${user.nick}" required><br>
			<label for="email">Email:</label>
			<input type="email" id="email" name="email" value="${user.email}" required><br>
			<label for="confirmEmail">Comfirm email:</label>
			<input type="email" id="confirmEmail" name="confirmEmail" value="${user.email}" required><br>
			<label for="inviteDate">Invite Date:</label>
			<fmt:formatDate value="${user.inviteDate}" var="formattedDate" type="date" pattern="dd/MM/yyyy HH:mm:ss" />
			<input type="text" id="inviteDate" name="inviteDate" value="${formattedDate}" class="readonly" readonly><br>
			<input type="submit" value="update">
			<div id="error">
			</div>
			</fieldset>
			<fieldset>
				<legend>Actions:</legend>
					<ul>
						<li><a href="../changepassword/${user.id}">Change password</a></li>
						<li><a href="../invite/${user.id}">Invite another user</a></li>
					</ul>
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
	document.getElementById("email").onchange = function() {
		document.getElementById("confirmEmail").value = "";
	}
	
	document.getElementById("fusuario").addEventListener("submit", function(event) {
			
			var error = !this.checkValidity;
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
</script>
</html>