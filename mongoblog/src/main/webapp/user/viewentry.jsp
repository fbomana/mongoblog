<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user.nick} - Edit entry</title>
	<link rel="stylesheet" href="../../css/mongoblog.css">
	<link rel="stylesheet" href="../../css/blog.css">
	<script src="../../js/mongoblog.js"></script>
    <script src="../../js/vue.min.js"></script>
    <script src="../../js/marked.min.js"></script>
</head>
<body>
	<div id="header">
		Mongoblog config
	</div>
	<div id="content">
		<div id="main">
			<section id="entry">
			<span class="title"><h1>${entry.title}</h1></span>
			<fmt:formatDate value="${entry.publishDate}" var="formattedDate" type="date" pattern="dd/MM/yyyy HH:mm" />
			<span class="date">${entry.author} - ${formattedDate}</span>
			<span class="content">${entry.entry}</span>
			</section>
			<section id="comments">
				<h1>Comments</h1>
				<c:forEach var="comment" items="${entry.comments}">
					<span class="author">${comment.author != null ? comment.author : "Anonimous"}</span><br>
					<span class="date">${ comment.date }</span><br>
					<span class="content">${ comment.comment }</span><br>
				</c:forEach>
				<form method="post" name="fcomment" action="../postcomment">
				<input type="hidden" name="entry" value="${entry.id}">
				<br>
				<label for="newcomment">New Comment:</label><br>
				<textarea name="newcomment" id="newcomment" required></textarea><br>
				<input type="submit" value="comment">
				</form>
			</section>
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
</body>
</html>