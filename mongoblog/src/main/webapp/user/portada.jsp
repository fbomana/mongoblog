<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mongoblog - ${user}</title>
	<link rel="stylesheet" href="../css/mongoblog.css">
	<link rel="stylesheet" href="../css/blog.css">
	<script src="../js/mongoblog.js"></script>
	<script src="../js/marked.min.js"></script>
</head>
<body>
	<div id="header">
		<jsp:include page="../header.jsp"/>
	</div>
	<div id="content">
		<div id="main">
			<div id="entries"></div>
			<div id="navigation"></div>
		</div>
		<div id="side">
			<c:if test="${sessionScope.loggeduser != null}">
				<a href="config">config</a><br>
				<a href="../logout">logout</a>
			</c:if>
			<c:if test="${sessionScope.loggeduser == null}">
				<a href="../login">login</a>
			</c:if>
		</div>
	</div>
	<form method="post" name="fedit" action="../redirect">
		<input type="hidden" name="from" value="home">
		<input type="hidden" name="to" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="nick" value="${user}">
	</form>
<script>

window.onload = function() {
	navigate( ${ page != null ? page : "0" } );
}

function navigate( page )
{
	var params = new Array();
	params[0] = "blog";
	params[1] = "${user}";
	params[2] = "page";
	params[3] = page;
	params[4] = "size";
	params[5] = "3";
	
	get("../services/entries", params, function( ok, xhr )
		{
			if ( ok )
			{
				printEntries( JSON.parse( xhr.responseText ));
			}
		}, true );
}

function printEntries( list )
{
	var entriesLayer = cleanDom( "entries" );
	var navigationLayer = cleanDom( "navigation" );
	for ( var i = 0; i < list.content.length; i++ )
	{
		printEntry( entriesLayer, list.content[i], list.number);
	}
	printNavigation( navigationLayer, list.totalPages, list.number );
}

function printEntry( layer, entry, page )
{
	var div = document.createElement("div");
	div.className = "entry";
	
	var titleSpan = document.createElement("span");
	titleSpan.className = "title";
	titleSpan.appendChild( document.createTextNode( entry.title ));
	titleSpan.onclick=function( id, returnPage ) { return function(){ view( id, returnPage )};}( entry.id, page );
	titleSpan.style.cursor="pointer";
	<c:if test="${sessionScope.loggeduser != null}">
		var editIcon = document.createElement("img");
		editIcon.src="../img/pencil.png";
		editIcon.className="edit";
		editIcon.onclick= function( id, returnPage ) { return function(){ edit( id, returnPage )};}( entry.id, page );
		titleSpan.appendChild( editIcon ); 
	</c:if>
	
	var authorSpan = document.createElement("span");
	authorSpan.className="author";
	authorSpan.appendChild( document.createTextNode( entry.author ));
	
	var dateSpan = document.createElement("span");
	dateSpan.className = "date";
	dateSpan.appendChild( document.createTextNode( new Date ( entry.publishDate )));
	
	var entryContent = document.createElement("div");
	entryContent.className="content";
	entryContent.innerHTML= marked( entry.resume ? entry.resume : entry.entry ); 
	
	div.appendChild( titleSpan );
	div.appendChild( authorSpan );
	div.appendChild( dateSpan );
	div.appendChild( entryContent );
	
	layer.appendChild( div );
}

function printNavigation( layer, totalPages, actualPage )
{
	var first = document.createElement( "img" );
	first.src="../img/first.png";
	if ( actualPage > 0 )
	{
		first.style.cursor = "pointer";
		first.onclick = function(){ navigate(0)};
	}
	layer.appendChild( first );
	
	var previous = document.createElement("img");
	previous.src="../img/previous.png";
	if ( actualPage > 0 )
	{
		previous.style.cursor = "pointer";
		previous.onclick = function( pagina ) {return function(){navigate( pagina )};}( actualPage -1 );
	}
	layer.appendChild( previous );
	
	layer.appendChild ( document.createTextNode( " " + ( actualPage + 1 ) + " " ));
	var next = document.createElement("img");
	next.src="../img/next.png";
	if ( actualPage < totalPages - 1 )
	{
		next.style.cursor = "pointer";
		next.onclick = function( pagina ) {return function(){navigate( pagina )};}( actualPage + 1 );
	}
	layer.appendChild( next );
	
	var last = document.createElement("img");
	last.src="../img/last.png";
	if ( actualPage < totalPages - 1 )
	{
		last.style.cursor = "pointer";
		last.onclick = function( pagina ) {return function(){navigate( pagina )};}( totalPages - 1 );
	}
	layer.appendChild( last );
}
<c:if test="${sessionScope.loggeduser != null}">
	function edit( entry, page )
	{
		document.fedit.to.value="edit"
		document.fedit.id.value = entry;
		document.fedit.page.value = page;
		document.fedit.submit();
	}
</c:if>
	function view( entry, page )
	{
		document.fedit.to.value="viewentry"
		document.fedit.id.value = entry;
		document.fedit.page.value = page;
		document.fedit.submit();
	}

</script>
</body>
</html>