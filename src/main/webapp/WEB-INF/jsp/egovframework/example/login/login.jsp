<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#test3").click(function() {
			alert('test3');
		});
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
세션정보 : ${sessionScope}
<form id="loginfrm" name="loginfrm" method="POST" action="/j_spring_security_check">
	<table>
		<tr><td>User:</td><td><input type='text' name='id' id='id' value=''></td></tr>
		<tr><td>Password:</td><td><input type='password' name='pw' id='pw'/></td></tr>
		<tr>
			<td colspan='2'>
				<input name="submit" Type="submit" value="login"/>
				<%--
				<input tYpe="button" name="test1" value="input-button" onclick="javascript:alert('sdafdsafasd')"/>
				<input tyPe="image" name="test2" value="input-image"/>
				<a href="#" id="test3">Jquery event</a>
				 --%>
				<%-- <a href="#" onClick="javascript:alert('test12345')">Jquery event</a>--%>
			</td>
		</tr>
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