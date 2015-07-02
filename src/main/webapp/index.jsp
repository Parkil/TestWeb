<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
	<c:when test="${empty sessionScope.SPRING_SECURITY_CONTEXT}">
		<jsp:forward page="/login.do"/>
	</c:when>
	<c:otherwise>
		<jsp:forward page="/sample/egovSampleList.do"/>
	</c:otherwise>
</c:choose>