<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String name = request.getParameter("myname"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>JSP_Sample</title>
<head>
</head>
<body>
	<%
		if (name != null) {
	%>
	<font color="#ff0000"><b>Hello</b><br></font>
	<br>
	<%=name%>
	<%
		} else {
	%>
	<font color="#ff0000"><b>ERROR</b></font>
	<%
		}
	%>
</body>
</html>