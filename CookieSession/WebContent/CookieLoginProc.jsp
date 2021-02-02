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
	
	//아이디 저장 체크 박스가 체크되었는지 판단 여부
	String save = request.getParameter("save");
	
	//아이디 값을 저장
	String id = request.getParameter("id");
	
	//체크되었는지 비교 판단
	if(save != null){
			
		//쿠키를 사용하려면 쿠키클래스를 생성해주어야 함
		Cookie cookie = new Cookie("id",id); //(key값,value값)
		//쿠키 유효시간 설정
		cookie.setMaxAge(60*10); //10분간 유효
		
		//사용자에게 쿠키 값을 넘겨줌
		response.addCookie(cookie);
		
		out.write("쿠키생성완료");
	}

	

%>

</body>
</html>