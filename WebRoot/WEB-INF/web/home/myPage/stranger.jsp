<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id = "notes">
	<input type = "hidden" id = "pageno" value = "0"/>
	<input type = "hidden" id = "type" value = "goods"/>
	<input type = "hidden" id = "method" value = "stranger"/>
	<div class = "feeds">
		<div id = "f_loading" style = 'margin-top:30px;'><span>闲置获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
	</div>
</div>
<script type="text/javascript">
   	//获取分享信息
   	feeds.get({type:"goods",method:"stranger"});
</script>
