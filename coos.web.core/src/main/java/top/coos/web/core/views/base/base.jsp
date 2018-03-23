<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--
	<%@ taglib uri="http://java.sun.com/jsp/basepm/base" prefix="base"%>
--%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="isdebug" value="${DEV_SERVER_CONFIG.debug }" />
<c:set var="find_frame" value="${DEV_SERVER_CONFIG.find_frame }" />
<c:set var="fileServerUrl" value="${CORE_CONFIG.server.file_server_url }" />
<c:set var="corepagesize" value="${CORE_CONFIG.project.pagesize }" />