<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user}</title>
	<link rel="stylesheet" href="../css/mongoblog.css">
</head>
<body>
	<div id="header">
		Mongoblog ${user}
	</div>
	<div id="content">
		<div id="main">
			Main content
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="config/${sessionScope.loggeduser.id}">config</a><br>
				<a href="../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="../login">login</a>
			</c:if>
		</div>
	</div>
</body>
</html>