<%@page import="es.ait.mongoblog.model.User"%>
<%
	if ( session.getAttribute( "loggeduser" ) != null ) 
	{
		response.sendRedirect( ((User) session.getAttribute( "loggeduser" )).getNick() + "/home");
	}
	else
	{
		response.sendRedirect("login");
	}
%>