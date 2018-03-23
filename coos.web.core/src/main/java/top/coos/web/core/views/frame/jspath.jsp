<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<%@ include file="/WEB-INF/views/core/base/jspath.jsp"%>
<script type="text/javascript" src="${basePath}/resource/core/app/js/app.js"></script>
<script type="text/javascript" core-main src="${basePath}/resource/core/app/js/main.js"></script>

<c:forEach items="${CACHE_DATA_FOR_JS }" var="CACHE_JS">
	<c:if test="${CACHE_JS!=null && CACHE_JS!='' }">

		<c:if test="${fn:indexOf(CACHE_JS, 'http')!=0}">
			<c:set var="CACHE_RESOURCE_JS" value="${basePath}${CACHE_JS }" />
		</c:if>
		<script type="text/javascript" src="${CACHE_JS }"></script>
	</c:if>
</c:forEach>
<c:forEach items="${THIS_PROJECT.resources }" var="ONE_RESOURCE">
	<c:set var="RESOURCE_URL" value="${ONE_RESOURCE.path }" />
	<c:if test="${RESOURCE_URL!=null && RESOURCE_URL!='' }">

		<c:if test="${fn:indexOf(RESOURCE_URL, 'http')!=0}">
			<c:set var="RESOURCE_URL" value="${basePath}${RESOURCE_URL }" />
		</c:if>
		<c:if test="${ONE_RESOURCE.type == 'JS'}">
			<script type="text/javascript" src="${RESOURCE_URL }"></script>
		</c:if>
		<c:if test="${ONE_RESOURCE.type == 'CSS'}">
			<link href="${RESOURCE_URL }" rel="stylesheet">
		</c:if>
	</c:if>
</c:forEach>
<c:if test="${CORE_APP_MAIN_JS != null }">
	<script type="text/javascript" src="${basePath}${CORE_APP_MAIN_JS}"></script>
</c:if>
<c:if test="${CORE_FRAME_MAIN_JS != null }">
	<script type="text/javascript" src="${basePath}${CORE_FRAME_MAIN_JS}"></script>
</c:if>