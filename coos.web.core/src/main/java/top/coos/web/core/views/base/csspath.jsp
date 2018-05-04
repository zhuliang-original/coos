<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<link href="${basePath}/resource/plugins/fontawesome/css/font-awesome.css" rel="stylesheet">
<c:if test="${!FIND_FRAME_SERVER }">
	<link href="${basePath}/resource/coos/css/coos.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/css/coos.frame.css" rel="stylesheet">
	<link href="${basePath}/resource/coos/css/coos.page.css" rel="stylesheet">
	
	<!-- <link href="/frame/resource/coos/css/coos.css" rel="stylesheet">
	<link href="/frame/resource/coos/css/coos.frame.css" rel="stylesheet">
	<link href="/frame/resource/coos/css/coos.page.css" rel="stylesheet"> -->
</c:if>
<c:if test="${FIND_FRAME_SERVER }">
	<link href="${FRAME_COOS_CSS_PATH}" rel="stylesheet/less" type="text/css">
	<link href="${FRAME_COOS_FRAME_CSS_PATH}" rel="stylesheet/less" type="text/css">
	<link href="${FRAME_COOS_PAGE_CSS_PATH}" rel="stylesheet/less" type="text/css">
	<script type="application/javascript" src="${basePath }/resource/plugins/less/less.js"></script>
</c:if>
<!--[if IE]>
<link href="${basePath}/resource/plugins/ie/ie.css" rel="stylesheet" >
<![endif]-->