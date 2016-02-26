<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id = "bottombox" style = "position:fixed;width:40px;height:85px;bottom:0px;right:0px;">
	<div style = "float:left;width:35px;height:35px;cursor:pointer;" id = "toTop">
		<img src = "${pageContext.request.contextPath}/web/image/base/toTop_1.png" style="float:left;width:30px;height:30px;"/>
	</div>
	<div style = "float:left;margin-top:10px;width:35px;height:35px;cursor:pointer;" onclick="suggestBox.show();" id = "contactUs">
		<img src = "${pageContext.request.contextPath}/web/image/base/contactUs_1.png" style="float:left;margin-left:3px;width:25px;height:30px;"/>
	</div>
</div>