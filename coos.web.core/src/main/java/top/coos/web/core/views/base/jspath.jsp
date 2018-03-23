<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<!--[if lte IE 7]>  
	<script language="javascript" src="${basePath}/resource/plugins/ie/json2.js"></script>
<![endif]-->
<!--[if lt IE 9]>
  <script type="text/javascript" src="${basePath}/resource/plugins/excanvas/excanvas.js"></script>      
<![endif]-->
<!--[if lte IE 9]>  
	<script language="javascript" src="${basePath}/resource/plugins/ie/html5shiv.js"></script>
	<script language="javascript" src="${basePath}/resource/plugins/ie/respond.js"></script>
<![endif]-->
<!--[if lte IE 8]>  
	<script language="javascript" src="${basePath}/resource/plugins/ie/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="${basePath}/resource/plugins/jquery/jquery.js"></script>
<c:if test="${!isdebug || !find_frame}">
	
	<script type="text/javascript" src="${basePath}/resource/coos/js/coos.js"></script>
	<script type="text/javascript" src="${basePath}/resource/coos/js/coos.frame.js"></script>
	<script type="text/javascript" src="${basePath}/resource/coos/js/coos.page.js"></script>
	
	<!-- <script type="text/javascript" src="/frame/resource/coos/js/coos.js"></script>
	<script type="text/javascript" src="/frame/resource/coos/js/coos.frame.js"></script>
	<script type="text/javascript" src="/frame/resource/coos/js/coos.page.js"></script> -->
</c:if>
<c:if test="${isdebug && find_frame }">
	<script type="text/javascript" src="${basePath}/resource/coos/resource/coos.js"></script>
	<script type="text/javascript" src="${basePath}/resource/coos/resource/coos.frame.js"></script>
	<script type="text/javascript" src="${basePath}/resource/coos/resource/core.page.js"></script>
</c:if>