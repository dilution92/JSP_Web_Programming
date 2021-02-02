<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
	<form action="ResponseProc.jsp" method="post">
		<table width="400" border="1">
			<tr height="40">
				<td align="center" width="150">아이디</td>
				<td width="250"><input type="text" name="id"></td>
			</tr>
			<tr height="40">
				<td align="center" width="150">패스워드</td>
				<td width="250"><input type="password" name="id"></td>
			</tr>
			<tr height="40">
				<td align="center" colspan="2">
					<input type="submit" value="로그인">&nbsp;&nbsp;<input type="reset" value="취소"> 
				</td>
			</tr>
		</table>
		</form>
	</center>
</body>
</html>