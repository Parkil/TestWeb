<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
세션정보 : ${sessionScope}
<form id="loginfrm" name="loginfrm" method="POST" action="/j_spring_security_check">
	<table>
		<tr><td>User:</td><td><input type='text' name='id' id='id' value=''></td></tr>
		<tr><td>Password:</td><td><input type='password' name='pw' id='pw'/></td></tr>
		<tr><td colspan='2'><input name="submit" type="submit" value="login"/></td></tr>
		<c:if test="${not empty param.fail}">
			<tr>
				<td>
					<font color="red">
						 로그인이 잘못 되었습니다.<br></br>
						 원인  : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					</font>
					<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
				</td>
			</tr>
		</c:if>
	</table>
</form>
</body>
</html>