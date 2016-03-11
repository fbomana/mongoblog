<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user} - editors</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<ul>
				<c:forEach var="editor" items="${editors}">
					<li><a href="../../${editor}/home">${editor}</a></li>
				</c:forEach>
			</ul>
			<form method="post" name="feditor" action="editors">
			<label for="editor" style="width:18%">Invite a new editor to your blog:</label>
			<input type="text" id="editor" name="editor" value="" requited>&nbsp;&nbsp;<input type="submit" value="invite">
			</form>
			<p class="error">${error}<p>
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="../home">home</a><br>
				<a href="../config">config</a><br>
				<a href="../../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="../../login">login</a>
			</c:if>
		</div>
	</div>
	<form method="post" name="fedit" action="../../redirect">
		<input type="hidden" name="from" value="unpublished">
		<input type="hidden" name="to" value="edit">
		<input type="hidden" name="id" value="">
	</form>
</body>
<script>
function edit ( id )
{
	document.fedit.id.value = id;
	document.fedit.submit();
}
</script>
</html>