<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="egovframework.spring.security.UserInfo" %>

<!-- spring security custom tag -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
  /**
  * @Class Name : egovSampleList.jsp
  * @Description : Sample List 화면
  * @Modification Information
  * 
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *  
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>

<%
/* Spring security를 이용하여 데이터를 가져오는 방법
Authentication auth = SecurityContextHolder.getContext().getAuthentication();

Object principal = auth.getPrincipal();
String name = "";

if(principal != null && principal instanceof UserInfo){
	name = ((UserInfo)principal).getId();
	out.println("로그인한 사용자 : "+name);
}else {
	out.println("사용자 정보 없음");
}*/

/* Servlet Spec을 이용하여 로그인 데이터를 가져오는 방법*/
Authentication auth = (Authentication)request.getUserPrincipal();

Object principal = auth.getPrincipal(); //동일한 내용이 세션에도 저장되어 있음
String name = "";

if(principal != null && principal instanceof UserInfo){
	UserInfo user_info = ((UserInfo)principal);
	name = user_info.getId();
	out.println("로그인한 사용자 : "+name);
}else {
	out.println("사용자 정보 없음");
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Basic Board List</title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
<!--
/* 글 수정 화면 function */
function fn_egov_select(id) {
	document.listForm.selectedId.value = id;
   	document.listForm.action = "<c:url value='/sample/updateSampleView.do'/>";
   	document.listForm.submit();		
}

/* 글 등록 화면 function */
function fn_egov_addView() {
   	document.listForm.action = "<c:url value='/sample/addSampleView.do'/>";
   	document.listForm.submit();		
}

/* 글 목록 화면 function */
function fn_egov_selectList() {
	document.listForm.action = "<c:url value='/sample/egovSampleList.do'/>";
   	document.listForm.submit();
}

/* pagination 페이지 링크 function */
function fn_egov_link_page(pageNo){
	document.listForm.pageIndex.value = pageNo;
	document.listForm.action = "<c:url value='/sample/egovSampleList.do'/>";
   	document.listForm.submit();
}

function aaa() {
	$.ajax({
		url: "/sample/jsonptest.do",
		jsonpCallback : "callback",
		dataType : "jsonp"
	}).done(function(data) {
		console.log(data);
		console.log(data.key1);
	});
}

function bbb() {
	$.ajax({
		url: "/sample/jsontest.do",
		dataType : "json"
	}).done(function(data) {
		console.log(data);
		console.log(data.key1);
	});
}
-->
</script>
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">
세션정보 : ${sessionScope}

<!-- spring security를 이용한 로그아웃 수행 로그아웃시 spring security에서 지정한 세션정보+내가 입력한 세션정보가 같이 삭제된다.-->
<a href="/logout.do">로그아웃</a> 
<a href="javascript:history.go(-1)">history.back()</a>
<a href="javascript:aaa()">jsonptest</a>
<a href="javascript:bbb()">jsontest</a> 
<br></br>
<br></br>
${param}
<form:form commandName="searchVO" name="listForm" method="post">
<input type="hidden" name="selectedId" />
<div id="content_pop">
	<!-- 타이틀 -->
	<div id="title">
		<ul>
			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>"> List Sample </li>
		</ul>
	</div>
	<!-- // 타이틀 -->
	<div id="search">
		<ul>
		<li>
			<form:select path="searchCondition" cssClass="use">
				<form:option value="1" label="Name" />
				<form:option value="0" label="ID" />
			</form:select>
		</li>
		<li><form:input path="searchKeyword" cssClass="txt"/></li>
		<li><span class="btn_blue_l"><a href="javascript:fn_egov_selectList();"><spring:message code="button.search" /></a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li></ul>		
	</div>
	<!-- List -->
	<div id="table">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="40">				
				<col width="100">
				<col width="150">
				<col width="80">
				<col width="">
				<col width="60">
			</colgroup>		  
			<tr>
				<th align="center">No</th>
				<th align="center">카테고리ID</th>
				<th align="center">카테고리명</th>
				<th align="center">사용여부</th>
				<th align="center">Description</th>
				<th align="center">등록자</th>
			</tr>
			<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
				<td align="center" class="listtd"><c:out value="${status.count}"/></td>
				<td align="center" class="listtd"><a href="javascript:fn_egov_select('<c:out value="${result.id}"/>')"><c:out value="${result.id}"/></a></td>
				<td align="left" class="listtd"><c:out value="${result.name}"/>&nbsp;</td>
				<td align="center" class="listtd"><c:out value="${result.useYn}"/>&nbsp;</td>
				<td align="center" class="listtd"><c:out value="${result.description}"/>&nbsp;</td>
				<td align="center" class="listtd"><c:out value="${result.regUser}"/>&nbsp;</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<!-- /List -->
	<div id="paging">
		<ui:pagination paginationInfo = "${paginationInfo}"
				   type="image"
				   jsFunction="fn_egov_link_page"
				   />
		<form:hidden path="pageIndex" />
	</div>
	<div id="sysbtn1">
		<ul>
		<div id="sysbtn"><ul>
		<li><span class="btn_blue_l"><a href="javascript:fn_egov_addView();">등록</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li></ul>
		</div>
		</ul>
	</div>
</div>
</form:form>
</body>
</html>
