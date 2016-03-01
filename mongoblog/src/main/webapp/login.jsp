<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - Login</title>
</head>
<body>
	<form method="post" name="flogin" action="${loginUrl}">
		<fieldset style="width:20em;margin-left:auto;margin-right:auto;margin-top:10em;display:block;">
			<legend>Mongoblog login</legend>
			<label for="user" style="display:inline-block;width:7em;">User:</label>
			<input type="text" id="user" name="user" value=""><br>
			<label for="password" style="display:inline-block;width:7em;">Password:</label>
			<input type="password" id="password" name="password" value=""><br>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="login" style="margin-left:auto;margin-right:auto;display:block;width:6em;">
		</fieldset>		
	</form>
</body>
</html>