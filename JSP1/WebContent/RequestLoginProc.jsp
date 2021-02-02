<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- RequestLogin에서 넘어온 아이디와 패스워드를 읽어드림  -->

<%
	//사용자의 정보가 저장되어이 있는 객체 rquest의 getParameter()를 이용해서 사용자의 정보를 추출
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");

%>
	당신의 아이디는 <%= id %> 이고
	패스워드는 <%= pass %>입니다.
	
	<a href="./RequestForward.jsp">다음페이지</a>
</body>
</html>