<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleRegister.jsp
  * @Description : Sample Register 화면
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="registerFlag" value="${empty sampleVO.id ? '등록' : '수정'}"/>
<title>Sample <c:out value="${registerFlag}"/> </title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>

<!--For Commons Validator Client Side-->
<script type="text/javascript" src="<c:url value='/cmmn/validator.do'/>"></script>
<validator:javascript formName="sampleVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javaScript" language="javascript" defer="defer">
$(document).ready(function() {
	$("#list_btn").click(function() {
		fn_egov_selectList();
	});
});
<!---->
/* 글 목록 화면 function */
function fn_egov_selectList() {
   	document.detailForm.action = "<c:url value='/sample/egovSampleList.do'/>";
   	document.detailForm.submit();		
}

/* 글 삭제 function */
function fn_egov_delete() {
   	document.detailForm.action = "<c:url value='/sample/deleteSample.do'/>";
   	document.detailForm.submit();		
}

/* 글 등록 function */
function fn_egov_save() {	
	
	frm = document.detailForm;
	if(!validateSampleVO(frm)){
        return;
    }else{
    	frm.action = "<c:url value="${registerFlag == '등록' ? '/sample/addSample.do' : '/sample/updateSample.do'}"/>";
        frm.submit();
    }
	
	// ajaxSubmit Option 
	/*
    options = {
        beforeSubmit : applyBefore, // ajaxSubmit 전처리 함수
        success      : applyAfter,  // ajaxSubmit 후처리 함수
        dataType     : 'json',       // 데이터 타입 json
        error : test
    };
    // frmApply Form Data값을 testAjax.html 으로 ajax 전송
    $("#detailForm").ajaxSubmit(options);
	 */
   
}

function test(a,b,c) {
	console.log(a,b,c);
}

function applyBefore(formData, jqForm, options)
{        
   
    // ajaxSubmit 전 처리 작업 영역
 
    return true;
}
 
function applyAfter(objResponse, statusText, xhr, $form)
{
    alert('111')
	if (statusText == "success") {
        // ajax 통신 성공 후 처리영역
        if (objResponse.strResult == "SUCCESS" ) {
            // 리턴받은 json 배열의 strResult 값이 "SUCCESS"일 경우 처리영역
        } else {
            // SUCCESS 이외의 값으로 리턴됐을 경우 처리영역
        }
    } else {
        // ajax 통신 실패 처리영역
    }    
}
 

</script>
</head>

<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">

<form:form commandName="sampleVO" name="detailForm" id="detailForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="test" value="${param.selectedId}"/>
<div id="content_pop">
	<!-- 타이틀 -->
	<div id="title">
		<ul>
			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>"> 카테고리<c:out value="${registerFlag}"/></li>
		</ul>
	</div>
	<!-- // 타이틀 -->
	<div id="table">
	<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#D3E2EC" bordercolordark="#FFFFFF" style="BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
		<colgroup>
			<col width="150">
			<col width="">
		</colgroup>
		<c:if test="${registerFlag == '수정'}">
		<tr>
			<td class="tbtd_caption">카테고리ID</td>
			<td class="tbtd_content">
				<form:input path="id" cssClass="essentiality" maxlength="10" readonly="true" />
			</td>			
		</tr>
		</c:if>
		<tr>
			<td class="tbtd_caption">카테고리명</td>
			<td class="tbtd_content">
				<form:input path="name" maxlength="30" cssClass="txt"/>
				&nbsp;<form:errors path="name" />
			</td>
		</tr>
		<tr>
			<td class="tbtd_caption">사용여부</td>
			<td class="tbtd_content">
				<form:select path="useYn" cssClass="use">
					<form:option value="Y" label="Yes" />
					<form:option value="N" label="No" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="tbtd_caption">Description</td>
			<td class="tbtd_content">
				<form:textarea path="description" rows="5" cols="58" />
					&nbsp;<form:errors path="description" /></td>
		</tr>
		<tr>
			<td class="tbtd_caption">등록자</td>
			<td class="tbtd_content">
				<form:input path="regUser" maxlength="10" cssClass="txt"  />
				<!-- 
					Number Format을 비롯하여 Formatter가 작동하려면 binding이 되어야 한다.
					그냥 el로 ${sampleVO.sss}로 선언하면 formatter가 작동하지 않으며 아래처럼 spring:bind로 binding을 시켜주어야 한다.
				-->
				<spring:bind path="sampleVO.sss">${status.value}</spring:bind>
				&nbsp;<form:errors path="regUser" /></td>
		</tr>
		<tr>
			<td class="tbtd_caption">파일등록</td>
			<td class="tbtd_content">
				<input type="file" name="filevo"/> --- ${sampleVO.sss}
			</td>
		</tr>
	</table>
  </div>
	<div id="sysbtn">
		<%-- 
		<ul>
			<li><span class="btn_blue_l"><a href="javascript:fn_egov_selectList();">List</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
			<li><span class="btn_blue_l"><a href="javascript:fn_egov_save();"><c:out value='${registerFlag}'/></a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
			<c:if test="${registerFlag == '수정'}">
			<li><span class="btn_blue_l"><a href="javascript:fn_egov_delete();">삭제</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
			</c:if>
			<li><span class="btn_blue_l"><a href="javascript:document.detailForm.reset();">Reset</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
		</ul>
		--%>
		<ul>
			<li><span class="btn_blue_l"><a href="#" id="list_btn">List</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
			<li><span class="btn_blue_l"><a href="#" onclick="javascript:fn_egov_save();"><c:out value='${registerFlag}'/></a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
			<c:if test="${registerFlag == '수정'}">
			<li>
				<input type="button" name="del_btn" value="삭제" onclick="javascript:fn_egov_delete();"/>
			</li>
			</c:if>
			<li><span class="btn_blue_l"><a href="javascript:document.detailForm.reset();">Reset</a><img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;"></span></li>
		</ul>
	</div>
</div>
<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${searchVO.searchCondition}'/>"/>
<input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
</form:form>
</body>
</html>

