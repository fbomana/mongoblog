<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user} - blogs</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
	<link rel="stylesheet" href="../../css/config.css">
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<ul>
				<c:forEach var="blog" items="${blogs}">
					<li><a href="../../${blog.nick}/home">${blog.nick}</a></li>
				</c:forEach>
			</ul>
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