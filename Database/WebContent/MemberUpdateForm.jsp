<%@page import="model.MemberBean"%>
<%@page import="model.MemberDao"%>
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
	String id = request.getParameter("id"); //memberlist id 받아줌
	MemberDao mdao = new MemberDao();
	MemberBean mbean = mdao.oneSelectMember(id);

%>

<center>

<h2>회원 정보 수정하기</h2>
	<table width="400" border="1">
	<form action="MemberUpdateProc.jsp" method="post">
		<tr height="50">
			<td align="center" width="150">아이디</td>
			<td width="250"><%=mbean.getId() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">이메일</td>
			<td width="250"><input type="email" name="email" value="<%=mbean.getEmail() %>"></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">전화번호</td>
			<td width="250"><input type="tel" name="tel" value="<%=mbean.getTel() %>"></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">패스워드</td>
			<td width="250"><input type="password" name="pass1"></td>
		</tr>
		<tr height="50">
			<td align="center" colspan="2">
			<input type="hidden" value="<%=mbean.getId() %>" name="id">
			<input type="submit" value="회원 수정하기"> &nbsp; &nbsp;
	</form>
			<button onclick="location.href='MemberList.jsp'">회원 전체 보기</button>
			</td>
		</tr>
	</table> 
</center>
</body>
</html>