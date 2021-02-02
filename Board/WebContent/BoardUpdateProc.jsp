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
	request.setCharacterEncoding("utf-8");
	
%>
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*" />
</jsp:useBean>

<%
	//데이터 베이스에 연결 
	BoardDao dao = new BoardDao();
	String pass = dao.getPass(boardbean.getNum());
	
	//기존 패스워드값과 update시 작성했던 패스워드 값이 같은지 비교
	if(pass.equals(boardbean.getPassword())){
		//데이터 수정하는 메소드 호출
		dao.updateBoard(boardbean);
		 
		response.sendRedirect("BoardList.jsp");
	}else{
		//패스워다가 틀리다면
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