<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<%@ include file="/WEB-INF/views/core/base/csspath.jsp"%>
<c:forEach items="${CACHE_DATA_FOR_CSS }" var="CACHE_CSS">
	<c:if test="${CACHE_CSS!=null && CACHE_CSS!='' }">
		<c:if test="${fn:indexOf(CACHE_CSS, 'http')!=0}">
			<c:set var="CACHE_RESOURCE_CSS" value="${basePath}${CACHE_CSS }" />
		</c:if>
		<link href="${CACHE_CSS }" rel="stylesheet">
	</c:if>
</c:forEach>