<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-找回密码</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/index/reset.js"></script>
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
				<span style = "font-size:20px;float:left;width:120px;margin-left:50px;">找回密码</span>
				<s:if test = '#request.step == "0" || #request.step == "1"'>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px 0px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">1</span><span style = "color:white">请输入邮箱</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">2</span><span style = "color:white">重置密码</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">3</span><span style = "color:white">完成</span></div>
				</s:if>
				<s:elseif test='#request.step == "2"'>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">1</span><span style = "color:white">请输入邮箱</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -0px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">2</span><span style = "color:white">重置密码</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">3</span><span style = "color:white">完成</span></div>
				</s:elseif>
				<s:elseif test='#request.step == "3"'>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">1</span><span style = "color:white">请输入邮箱</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px -27px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">2</span><span style = "color:white">重置密码</span></div>
					<div style = "float:left;margin-top:10px;width:200px;height:27px;line-height:30px;background:url(${pageContext.request.contextPath}/web/image/index/getback_btn.png)0px 0px no-repeat;cursor:default;margin-left:15px;"><span style = "float:left;color:white;margin-left:9px;">3</span><span style = "color:white">完成</span></div>
				</s:elseif>
			</div>
			<div style = "float:left;width:100%;margin-top:50px;">
				<s:if test = '#request.step == "0" || #request.step == "1"'>
					<div style = "width:550px;">
						<div style = "float:left;width:100%;height:40px;line-height:20px;">
							<span id = "error_tip" style = "color:#e68303;"></span>
						</div>
						<span style = "float:left;font-size:16px;line-height:25px;">请输入注册邮箱:</span>
						<input type = "text" id = "email" name = "email" style = "float:left;margin-left:10px;float:left;line-height:22px;width:250px;height:22px;border-radius:6px;border:1px solid #b6b6b6;padding-left:10px;"/>
						<div class = "button" style = "float:right;width:120px;height:25px;line-height:26px;" id = "send_reset_mail">发送重置邮件</div>
					</div>
				</s:if>
				<s:elseif test='#request.step == "2"'>
					<s:if test='#request.flag == "1"'>
						<div style = "width:340px;">
							<div style = "float:left;width:100%;height:40px;line-height:20px;">
								<span id = "error_tip" style = "color:#e68303;"></span>
							</div>
							<form id = "reset_form" action="/pwd_reset" method = "post">
								<span style = "float:left;font-size:16px;line-height:25px;">请输入新密码:</span>
								<input type = "password" id = "password" name = "password" style = "float:left;margin-left:10px;line-height:22px;width:200px;height:22px;border-radius:6px;border:1px solid #b6b6b6;padding-left:10px;"/>
								<span style = "float:left;font-size:16px;line-height:25px;margin-top:20px;">确认输入密码:</span>
								<input type = "password" id = "re_password" style = "float:left;margin-left:10px;margin-top:20px;line-height:22px;width:200px;height:22px;border-radius:6px;border:1px solid #b6b6b6;padding-left:10px;"/>
								<div class = "button" style = "float:right;width:120px;height:25px;margin-top:30px;margin-right:20px;line-height:26px;" id = "reset_confirm">确认</div>
								<input type = "hidden" name = "step" value = "3"/>
							</form>
						</div>
					</s:if>
					<s:else>
						<div style = "width:500px;">
							${message}
						</div>
					</s:else>
				</s:elseif>
				<s:elseif test='#request.step == "3"'>
					<s:if test='#request.flag == "1"'>
						<script>
							window.setTimeout(function(){
								location.href = "/index";
							},3000);
						</script>
						<div style = "width:500px;">
							密码已重置成功! 3秒后将返回登录页面...
						</div>
					</s:if>
					<s:else>
						<div style = "width:500px;">
							${message}
						</div>
					</s:else>
				</s:elseif>
				<s:else>
					<s:include value="errorPage.jsp"></s:include>
				</s:else>
			</div>
		</div>
     </div>
      <s:include value="../foot/footDefault.jsp"/>
  </body>
</html>
     