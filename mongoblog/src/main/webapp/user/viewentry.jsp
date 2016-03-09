<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user.nick} - Edit entry</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
	<link rel="stylesheet" href="../../css/newentry.css">
	<script src="../../js/mongoblog.js"></script>
    <script src="../../js/vue.min.js"></script>
    <script src="../../js/marked.min.js"></script>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.10.1.js"></script>
	<script src="http://cdn.jsdelivr.net/webshim/1.12.4/extras/modernizr-custom.js"></script>
	<script src="http://cdn.jsdelivr.net/webshim/1.12.4/polyfiller.js"></script>
<script>
webshims.setOptions('waitReady', false);
webshims.setOptions('forms-ext', {types: 'date'});
webshims.polyfill('forms forms-ext');
</script>
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<h1>${entry.title}</h1>
			${entry.entry}
			<p>
				${entry.publishDate}
			</p>
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="../${sessionScope.loggeduser.nick}">home</a><br>
				<a href="../config/${sessionScope.loggeduser.id}">config</a><br>
				<a href="../../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="../../login">login</a>
			</c:if>
		</div>
	</div>
</body>
</html>