<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page errorPage="error.jsp" %>

<jsp:useBean id="process" scope="session" class="operation.Add"></jsp:useBean>
<jsp:setProperty property="first" name="process" param="firstNumber"/>
<jsp:setProperty property="second" name="process" param="secondNumber"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>
<h2>結果：</h2>
<jsp:getProperty property="result" name="process"/>
</body>
</html>