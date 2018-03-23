<%@ page trimDirectiveWhitespaces="true"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%><%@ include file="/WEB-INF/views/core/base/base.jsp"%>
<style type="text/css">
.error-content {
	max-width: 400px;
	margin: 20px auto;
	background-color: white;
	padding: 20px;
	margin-top: 100px;
}

.error-icon {
	margin: 20px auto;
	width: 100px;
	height: 100px;
	text-align: center;
	border-radius: 100px;
	background-color: #e4e4e4;
}

.error-icon .fa {
	line-height: 100px;
	font-size: 40px;
	font-weight: 600;
}
</style>
<input type="hidden" id="coos-need-full-page" />
<div class="coos-col-12 coos-page ">
	<div class="coos-page-content ">
		<div class="coos-page-content-center">
			<div class="coos-page-content-center-content">
				<div class="error-content text-center">
					<div class="error-icon coos-white coos-bg-yellow">
						<i class="fa fa-exclamation"></i>
					</div>
					<h2 class="error-title coos-red">500 Error</h2>
					<%--<a class="coreToIndexBtn  coos-pointer  coos-green">返回首页</a>

				--%>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	
</script>