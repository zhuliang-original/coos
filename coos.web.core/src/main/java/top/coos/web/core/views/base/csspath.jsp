<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<link href="${basePath}/resource/plugins/fontawesome/css/font-awesome.css" rel="stylesheet">
<c:if test="${!isdebug || !find_frame }">
	<link href="${basePath}/resource/coos/css/coos.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/css/coos.frame.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/css/coos.page.css" rel="stylesheet">
	
	<!-- <link href="/frame/resource/coos/css/coos.css" rel="stylesheet">
	<link href="/frame/resource/coos/css/coos.frame.css" rel="stylesheet">
	<link href="/frame/resource/coos/css/coos.page.css" rel="stylesheet"> -->
</c:if>
<c:if test="${isdebug && find_frame }">
	<link href="${basePath}/resource/coos/resource/coos.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/resource/coos.frame.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/resource/coos.page.css" rel="stylesheet">
</c:if>
<!--[if IE]>
<link href="${basePath}/resource/plugins/ie/ie.css" rel="stylesheet" >
<![endif]-->