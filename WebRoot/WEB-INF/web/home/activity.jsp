<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-活动主页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  <body>
  	<div class = "main" id = "main">
   	 	<s:include value="../base/top.jsp"/>
   	 	<div style = "float:left;width:880px;margin-left:50px;margin-top:20px;">
   	 		<div class = "activity" id= "sell_book" style = "float:left;width:800px;margin-left:40px;">
   	 			<a href = "${pageContext.request.contextPath}/activity/sell_book/show"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/activity_sellbook_bar.jpg" style = "width:800px;height:80px;cursor:pointer;"/></a>
   	 			<span style = "float:left;margin-top:15px;text-align:left;width:100%;color:#717171;">圈易物书籍分享活动 &nbsp;&nbsp;<a href = "${pageContext.request.contextPath}/activity/sell_book/show" style = "color:#89b403;">点击进入</a></span>
   	 		</div>
   	 		<div style = "float:left;width:880px;height:15px;border-bottom:1px solid #d8d8d8;border-radius:0px;"></div>
   	 	</div>
   	 	<div style = "float:left;width:880px;margin-left:50px;margin-top:20px;">
   	 		<div class = "activity" id= "lost_back" style = "float:left;width:800px;margin-left:40px;">
   	 			<a href = "${pageContext.request.contextPath}/activity/grad_carnival/show"><img src = "${pageContext.request.contextPath}/activity/gradcarnival/image/activity_gradcarnival_bar.jpg" style = "float:left;width:800px;height:112px;cursor:pointer;"/></a>
   	 			<span style = "float:left;margin-top:15px;text-align:left;width:100%;color:#717171;">毕业季两校区分享狂欢节 &nbsp;&nbsp;活动时间为4月24日-6月30日 &nbsp;&nbsp;&nbsp;&nbsp;<a href = "${pageContext.request.contextPath}/activity/grad_carnival/show" style = "color:#8b1f21;">点击进入</a></span>
   	 		</div>
   	 		<div style = "float:left;width:880px;height:15px;border-bottom:1px solid #d8d8d8;border-radius:0px;"></div>
   	 	</div>
   	 	<div style = "float:left;width:880px;margin-left:50px;margin-top:40px;">
   	 		<div class = "activity" id= "lost_back" style = "float:left;width:800px;margin-left:40px;">
   	 			<a href = "${pageContext.request.contextPath}/activity/lost_found/show"><img src = "${pageContext.request.contextPath}/activity/lostfound/image/activity_lostfound_bar.jpg" style = "float:left;width:800px;height:80px;cursor:pointer;"/></a>
   	 			<span style = "float:left;margin-top:15px;text-align:left;width:100%;color:#717171;">沙河校区失物招领活动 &nbsp;&nbsp;活动时间为4月15日-7月1日 &nbsp;&nbsp;&nbsp;&nbsp;<a href = "${pageContext.request.contextPath}/activity/lost_found/show" style = "color:#e68303;">点击进入</a></span>
   	 		</div>
   	 		<div style = "float:left;width:880px;height:15px;border-bottom:1px solid #d8d8d8;border-radius:0px;"></div>
   	 	</div>
    </div>
	 <s:include value="../base/face.jsp"/>
	<s:include value="../base/toolBox.jsp"/>
	<s:include value="../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../base/bottomBox.jsp"/>
</html>