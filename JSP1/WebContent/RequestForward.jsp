<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	//사용자의 정보가 저장되어이 있는 객체 rquest의 getParameter()를 이용해서 사용자의 정보를 추출
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");

%>
	<h1>RequestForward페이지 입니다.</h1>
	당신의 아이디는 <%= id %> 이고
	패스워드는 <%= pass %>입니다.
</body>
</html>