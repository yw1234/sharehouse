<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-账号授权失败</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
  	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = 'lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
     	<div id = "home_top">
			<div id = "home_top_main">
				<img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;"/>
				<a style = "float:right;margin-top:15px;margin-right:20px;color:white;font-size:18px;" href = "${pageContext.request.contextPath}/exitlogin">返回</a>
			</div>
		</div>
		<div style="margin-top:0px;width:700px;">
			<div style="background:url(${pageContext.request.contextPath}/web/image/base/instruction_bg.jpg) repeat;width:700px;height:300px;border-radius:20px;margin-top:30px;">
				<div style="float:left;width:650px;margin-left:25px;margin-right:25px;margin-top:40px;line-height:25px;">
					${message}
				</div>
			</div>
		</div>
	</div>
	<s:include value="foot/footDefault.jsp"/>
  </body>
</html>