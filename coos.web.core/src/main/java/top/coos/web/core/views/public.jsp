<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<!DOCTYPE html >
<!--[if IE 8]><html class="ie ie8" xmlns="http://www.w3.org/1999/xhtml"><![endif]-->
<!--[if IE 9]><html class="ie ie9" xmlns="http://www.w3.org/1999/xhtml"><![endif]-->
<head>
<title>${THIS_PROJECT.name }</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0 , user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="shortcut icon" type="image/x-icon" href="${basePath }/resource/coos/images/favicon.png" media="screen" />

<script type="text/javascript">
	var basePath = '${basePath}';
	var fileServerUrl = '${fileServerUrl}';
	var CORE_RESOURCE_JSON_PATH = '${CORE_RESOURCE_JSON_PATH }';
	var APP_RESOURCE_JSON_PATH = '${APP_RESOURCE_JSON_PATH}';
	var CORE_CONFIG = ${CORE_CONFIG_JSON == null?'{}':CORE_CONFIG_JSON};
	var THIS_USER = ${THIS_USER_JSON == null?'{isNull:true}':THIS_USER_JSON};
	THIS_USER = THIS_USER.isNull?null:THIS_USER;
	var THIS_MENUS = ${THIS_MENUS_JSON == null?'[]':THIS_MENUS_JSON};
	var THIS_STATICVALUE = ${THIS_STATICVALUE_JSON == null?'{isNull:true}':THIS_STATICVALUE_JSON};

	var THIS_PROJECT = ${THIS_PROJECT_JSON == null?'{}':THIS_PROJECT_JSON};
	
</script>
<%@ include file="frame/csspath.jsp"%>
<%@ include file="frame/jspath.jsp"%>
<style id="COOS-THEME-STYLE" type="text/css"></style>
</head>
<body>
	<div id="page-body-content">
		<c:if test="${THIS_IS_ERROR_PAGE == null }">
			<script type="text/javascript">
				(function() {
					var THIS_CORE_FRAME_MAIN_JS = "${CORE_FRAME_MAIN_JS}";
					if (coos.isFunction(window.history.pushState) && coos.page.config.single) {
						if (!coos.isEmpty(window.OLD_CORE_FRAME_MAIN_JS)) {
							if (window.OLD_CORE_FRAME_MAIN_JS != THIS_CORE_FRAME_MAIN_JS) {
								$('body').empty();
								window.location.reload();
								throw Error("reload frame");
							}
						}
					}
					window.OLD_CORE_FRAME_MAIN_JS = THIS_CORE_FRAME_MAIN_JS;
				})();
			</script>
		</c:if>
		<jsp:include page="${THIS_PAGE_ID }" flush="false" />
	</div>
	<a href="javascript:;" class="coos-back-top ">
		<i class="fa fa-angle-up"> </i>
	</a>
</body>
</html>