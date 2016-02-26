<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-账号激活</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
  	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
     	<div id = "home_top">
			<div id = "home_top_main">
				<a href = "${pageContext.request.contextPath}/home"><img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;"/></a>
			</div>
		</div>
		<div style = "float:left;width:100%;">
			<div style = "float:left;width:100%;height:50px;line-height:50px;margin-top:50px;">
				<span style = "font-size:20px;float:left;width:120px;margin-left:50px;">账号激活</span>
				<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px 0px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;"></span><span style = "color:white">激活结果</span></div>
			</div>
			<div style = "float:left;width:100%;margin-top:50px;">
				<s:if test='#request.flag == "1"'>
					<script>
						window.setTimeout(function(){
							location.href = "/index";
						},3000);
					</script>
					<div style = "width:500px;">
						账号激活成功! 3秒后将返回主页面...
					</div>
				</s:if>
				<s:else>
					<div style = "width:500px;">
						${message}
					</div>
				</s:else>
			</div>
		</div>
     </div>
      <s:include value="../foot/footDefault.jsp"/>
  </body>
</html>
