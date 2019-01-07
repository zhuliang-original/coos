<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<c:if test="${this_page.csscontent != null && this_page.csscontent != '' }">
	<style type="text/css">
		${this_page.csscontent}
	</style>
</c:if>
<div>
	<c:if test="${this_page.needfullpage }">
		<input type="hidden" id="coos-need-full-page" />
	</c:if>

	<c:if test="${this_page.beforejsp != null && this_page.beforejsp != '' }">
		<jsp:include page="${this_page.beforejsp }" flush="false" />
	</c:if>
	<div class="coos-page-${this_page_id}"></div>
	<c:if test="${this_page.htmlcontent != null && this_page.htmlcontent != '' }">
		${this_page.htmlcontent }
	</c:if>
	<c:if test="${this_page.afterjsp != null && this_page.afterjsp != '' }">
		<jsp:include page="${this_page.afterjsp }" flush="false" />
	</c:if>
	<c:if test="${this_page.jscontent != null && this_page.jscontent != '' }">
		<script type="text/javascript">
			try{
				${this_page.jscontent}
			}catch(e){
				console.log(e);
			}
		</script>
	</c:if>
	<script type="text/javascript">
		$(function() {
			var pageid = "${this_page_id }";
			var cachestr = '${cachestr }';
			if(coos.isEmpty(cachestr)){
				cachestr = "{}";
			}
			var requestmapstr = '${requestmapstr }';
			var requestmap = {};
			try{
				requestmap = JSON.parse(requestmapstr);
			}catch(e){
				try{
					requestmap = eval('(' + requestmapstr + ')');
				}catch(e){
					
				}
			}
			var cache = {};
			try{
				cache = JSON.parse(cachestr);
			}catch(e){
				try{
					cache = eval('(' + cachestr + ')');
				}catch(e){
					
				}
			}
			var $pageContent = $('.coos-page-' + pageid);
			var config = {
				pageid : pageid,
				requestmap : requestmap || {},
				cache : cache || {},
				container : $pageContent
			};
			var page = coos.page.create(config);
			coos.element.init($pageContent);
			if (window.parent && window.parent.dev) {
				window.parent.dev.page.onload(page);
			}
			page.loadData();
		});
	</script>
</div>