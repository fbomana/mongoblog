<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user} - Edit entry</title>
	<link rel="stylesheet" href="${urlPrefix}../../css/mongoblog.css">
	<link rel="stylesheet" href="${urlPrefix}../../css/newentry.css">
	<script src="${urlPrefix}../../js/mongoblog.js"></script>
    <script src="${urlPrefix}../../js/vue.min.js"></script>
    <script src="${urlPrefix}../../js/marked.min.js"></script>
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
			<section id="formSection" style="left">
				<form method="POST" name="fentry" action="${urlPrefix}saveentry">
					<input type="hidden" name="id" value="${entry.id}">
					<label for="title">Title:</label>
					<input type="text" id="title" name="title" value="${entry.title}" required><br>
					<label for="publishdate">Publish Date:</label>
					<input type="date" id="publishDate" name="publishDate" value="${publishDate}" required>
					<input type="time" id="publishTime" name="publishTime" value="${publishTime}" required><br>
					<label for="resume" id="sampleLabel">Resume:</label>
						<div id="resumeEditor">
							<textarea required id="resume" name="resume" v-model="input" debounce="300"></textarea>
							<label for="preview">Preview:</label>
	            			<div v-html="input | marked" id="previewResume"></div>
            			</div><br>
					<label for="entry" id="sampleLabel">Entry:</label>
						<div id="editor">
							<textarea required id="entry" name="entry" v-model="input" debounce="300"></textarea>
							<label for="preview">Preview:</label>
	            			<div v-html="input | marked" id="preview"></div>
            			</div><br>
           			<input type="submit" value="Guardar">
				</form> 
			</section>
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="${urlPrefix}../home">home</a><br>
				<a href="${urlPrefix}../config">config</a><br>
				<a href="${urlPrefix}../../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="${urlPrefix}../../login">login</a>
			</c:if>
		</div>
	</div>
</body>

<script>
	
window.onresize = function( event ) {
	setTimeout( 100, resizeElements( event ));
};
window.onload = function() {
	var params = new Array();
	params[0] = "id";
	params[1] = "${entry.id}";
	get( "${urlPrefix}../../services/entry", params, function ( ok, xhr )
		{
			if ( ok )
			{
				var entry = JSON.parse( xhr.responseText );
				new Vue({
				    el: '#editor',
				    data: {
				      input: entry.entry ? entry.entry : ''
				    },
				    filters: {
				      marked: marked
				    }
			  	});
				new Vue({
				    el: '#resumeEditor',
				    data: {
				      input: entry.resume ? entry.resume : ''
				    },
				    filters: {
				      marked: marked
				    }
			  	});				
			}
		}, false );
	resizeElements();
}
  
function resizeElements( event )
{
	var sample = document.getElementById("sampleLabel");
	var width = document.getElementById("main").offsetWidth - sample.offsetWidth - 30;
    document.getElementById("entry").style.height = window.innerHeight / 2  + "px";
    document.getElementById("entry").style.width = width  + "px";
    document.getElementById("preview").style.height = window.innerHeight / 2  + "px";
    document.getElementById("preview").style.width = width  + "px";
    document.getElementById("resume").style.height = window.innerHeight / 4  + "px";
    document.getElementById("resume").style.width = width  + "px";
    document.getElementById("previewResume").style.height = window.innerHeight / 4  + "px";
    document.getElementById("previewResume").style.width = width  + "px";
    
}
</script>
</html>