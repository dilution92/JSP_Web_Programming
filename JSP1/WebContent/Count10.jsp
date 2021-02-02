<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h2>1-10까지의 숫자를 화면에 표시</h2>
<%

	//1 ~ 10까지의 숫자를 화면에 출력하시오.
	for(int i=1; i<11; i++){
		System.out.println(i); //이건 콘솔에서 출력한다.
%>
		<%= i %><br/>
<%} %>






</body>
</html>