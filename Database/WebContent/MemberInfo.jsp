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

	<!-- 0. MemberList에서 넘긴 id를 받아줌
		 1. 데이터 베이스에서 한 회원의 정보를 가져옴
		 2. 테이블태그를 이용하여 화면에 회원의 정보를 출력 -->

<%
	String id = request.getParameter("id"); //memberlist id 받아줌
	MemberDao mdao = new MemberDao();
	MemberBean mbean = mdao.oneSelectMember(id);

%>

<center>

<h2>회원 정보 보기</h2>
	<table width="400" border="1">
		<tr height="50">
			<td align="center" width="150">아이디</td>
			<td width="250"><%=mbean.getId() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">이메일</td>
			<td width="250"><%=mbean.getEmail() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">전화번호</td>
			<td width="250"><%=mbean.getTel() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">취미</td>
			<td width="250"><%=mbean.getHobby() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">직업</td>
			<td width="250"><%=mbean.getJob() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">나이</td>
			<td width="250"><%=mbean.getAge() %></td>
		</tr>
		<tr height="50">
			<td align="center" width="150">정보</td>
			<td width="250"><%=mbean.getInfo() %></td>
		</tr>
		<tr height="50">
			<td align="center" colspan="2">
			<button onclick="location.href='MemberUpdateForm.jsp?id=<%=mbean.getId() %>'">회원수정</button>
			<button onclick="location.href='MemberDeleteForm.jsp?id=<%=mbean.getId() %>'">회원삭제</button>
			<button onclick="location.href='MemberList.jsp'">목록보기</button>
			<button onclick="location.href='MemberJoin.jsp'">회원가입</button>
			</td>
		</tr>
	</table>
</center>


</body>
</html>