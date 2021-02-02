<%@page import="model.BoardDao"%>
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
	String pass = request.getParameter("password");
	int num = Integer.parseInt(request.getParameter("num"));
	
	BoardDao dao = new BoardDao();
	String password = dao.getPass(num);
	 
	//기본 패스워값과 deleteForm에서 작성한 패스워드를 비교
	if(pass.equals(password)){
		dao.deleteBoard(num);
		response.sendRedirect("BoardList.jsp");
	}else{
%>
		<script type="text/javascript">
			alert("패스워드가 일치하지 않습니다. 다시 확인 후 수정해주세요.");
			history.go(-1);
		</script>
<%
	}
%>
</body>
</html>