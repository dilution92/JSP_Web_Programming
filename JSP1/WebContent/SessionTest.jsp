<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h2>세션 연습</h2>
<%
	String name = "shin";
	//세션을 이용하여 데이터를 유지
	session.setAttribute("name", name);
	//세션 유지 시간
	session.setMaxInactiveInterval(10); //10초간 세션을 유지

	
%>
	<a href="SessionName.jsp">세션 네임 페이지로 이동</a>

</body>
</html>