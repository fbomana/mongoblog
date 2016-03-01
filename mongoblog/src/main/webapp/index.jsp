<%
	if ( session.getAttribute( "loggeduser" ) != null ) 
	{
		response.sendRedirect("user/" + session.getAttribute( "loggeduser" ) );
	}
	else
	{
		response.sendRedirect("login");
	}
%>