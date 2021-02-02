<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>이 페이지는 로그인 정보가 넘어오는 페이지 입니다.</h2> 
	<%
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		
		//response.sendRedirect("ResponseRedirect.jsp?id="+id); // 흐름제어 이 형식으로 넘길수도 있다
	
	%> 
	<jsp:forward page="ResponseRedirect.jsp">
		<jsp:param value="이 부분으로 value값을 변경 가능하다" name="id"/>
	</jsp:forward>
	<h3>아이디는 = <%=id %></h3>
</body>
</html>