<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form id="loginfrm" name="loginfrm" method="POST" action="/j_spring_security_check">
	<table>
		<tr><td>User:</td><td><input type='text' name='id' id='id' value=''></td></tr>
		<tr><td>Password:</td><td><input type='password' name='pw' id='pw'/></td></tr>
		<tr><td colspan='2'><input name="submit" type="submit" value="로그인"/></td></tr>
	</table>
</form>
</body>
</html>