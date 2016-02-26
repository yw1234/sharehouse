<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%><%session.setAttribute("email",false);session.setAttribute("name",false);session.setAttribute("checkcode",false);%>
<!DOCTYPE html>
<html>
  <head>    
    <title>圈易物-注册新账号</title>
    <meta http-equiv="keywords" content="quan15,圈易物注册">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />
	<script type="text/javascript">PATH = "${pageContext.request.contextPath}";</script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/index/register.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/index/register.css">
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css">
	<![endif]-->
  </head>
  <body>
    <div class = "main">
    	<div id = "register_top">
    		<img src = "${pageContext.request.contextPath}/web/image/index/index_logo.png" style = "float:left;margin-left:30px;"/>
    		<div style = "margin-left:20px;margin-top:30px;float:left;height:30px;width:480px;line-height:30px;text-align:left;"><span style = "font-size:24px;font-family:'黑体',Arial;color:#3a3a3a;">赶快加入<span style = "font-family:'黑体',Arial;color:#e08614;">圈易物</span>来体验不一样的精彩</span></div>
    		<div class = "register_nav">
    			<span style = "font-size:14px;font-family:'黑体',Arial;">已有圈易物账号,<a href = "${pageContext.request.contextPath}/index">登录</a></span>
    		</div>
    	</div>
    	<div class = "register_main">
    		<div style = "float:left;margin-top:20px;width:980px;height:4px;background:url(${pageContext.request.contextPath}/web/image/register/split_line.png) center no-repeat;"></div>
    		<div id = "register">
    			<div style="float:left;width:380px;margin-left:50px;margin-top:20px;text-align:left;height:40px;line-height:40px;letter-spacing:1px;">
    				<span style="font-size:18px;font-family:'黑体',Arial;">开通<span style="font-size:18px;font-family:'黑体',Arial;color:#e68303;">圈易物</span>账号</span>
    			</div>
    			<div style="width:500px;height:540px;overflow:hidden;position:relative;">
    				<div id = "reg_slide_block" style="position:absolute;left:0px;top:0px;width:1000px;height:540px">
	    				<div id = "step_1">
			    			<div class = "register_input" style = "position:relative;z-index:10;">
			    				<div class = "reg_row" ><span style = "float:right;">注册邮箱 :</span></div>
			    				<input type ="text" id = "email" class = "register_info" name = "email"maxlength="30" TextDefault="请输入常用邮箱" />
			    				<label class = "input_tip" id = "email_input_tip">
		    						<input type="hidden" id = "itl_loc" value="-1"/>
		    						<span class = "input_tip_list" m-data = "@qq.com">@qq.com</span>
								<span class = "input_tip_list" m-data = "@163.com">@163.com</span>
								<span class = "input_tip_list" m-data = "@sina.com">@sina.com</span>
								<span class = "input_tip_list" m-data = "@sohu.com">@sohu.com</span>
								<span class = "input_tip_list" m-data = "@126.com">@126.com</span>
								<span class = "input_tip_list" m-data = "@gmail.com">@gmail.com</span>
								<span class = "input_tip_list" m-data = "@hotmail.com">@hotmail.com</span>
								<span class = "input_tip_list" m-data = "@yahoo.com">@yahoo.com</span>
								<span class = "input_tip_list" m-data = "@139.com">@139.com</span>
		    					</label>
							<img src = "${pageContext.request.contextPath}/web/image/base/pic_loading_default.gif" class = "checking" id = "email_checking"/>				    				<div class = "tip" id = "email_tip"><img src = "${pageContext.request.contextPath}/web/image/register/error_lefttip.png"/><div id = "email_tip_info"></div><img src = "${pageContext.request.contextPath}/web/image/register/error_righttip.png"/></div>
			    				<input class = "validate" id = "validate_email" type = "hidden" value = "0"/>
			    			</div>
			    			<div class = "register_input">
			    				<div class = "reg_row" ><span style = "float:right;">创建密码 :</span></div>
			    				<div class = "text_tip" id = "password_placeholder"></div><input name = "password" type ="password" class = "register_info pwd_field" maxlength="20" id = "password" TextDefault = "6-20个字符以内"/>
			    				<div class = "tip" id = "password_tip"><img src = "${pageContext.request.contextPath}/web/image/register/error_lefttip.png"/><div id = "password_tip_info"></div><img src = "${pageContext.request.contextPath}/web/image/register/error_righttip.png"/></div>
			    				<input class = "validate" id = "validate_password" type = "hidden" value = "0"/>
			    			</div>
			    			<div class = "register_input">
			    				<div class = "reg_row" ><span style = "float:right;">确认密码 :</span></div>
			    				<div class = "text_tip" id = "repassword_placeholder"></div><input name = "repassword" type ="password" class = "register_info pwd_field" maxlength="20" id = "repassword" TextDefault = "再次输入密码"/>
			    				<div class = "tip" id = "repassword_tip"><img src = "${pageContext.request.contextPath}/web/image/register/error_lefttip.png"/><div id = "repassword_tip_info"></div><img src = "${pageContext.request.contextPath}/web/image/register/error_righttip.png"/></div>
			    				<input class = "validate" id = "validate_repassword" type = "hidden" value = "0"/>
			    			</div>
			    			<div class = "register_input">
			    				<div class = "reg_row" ><span style = "float:right;">用户名 :</span></div>
			    				<input type ="text" id = "name" name = "name"class = "register_info" maxlength="25" TextDefault="请输入用户名"/>
			    				<img src = "${pageContext.request.contextPath}/web/image/base/pic_loading_default.gif" class = "checking" id = "name_checking"/>
			    				<div class = "tip" id = "name_tip"><img src = "${pageContext.request.contextPath}/web/image/register/error_lefttip.png"/><div id = "name_tip_info"></div><img src = "${pageContext.request.contextPath}/web/image/register/error_righttip.png"/></div>
			    				<input class = "validate" id = "validate_name" type = "hidden" value = "0"/>
			    			</div>
			    			<div class = "register_input">
			    				<div class = "reg_row" ><span style = "float:right;">性别 :</span></div>
			    				<div style = "float:left;margin-left:10px;width:250px;height:20px;margin-top:5px;line-height:20px;">
			    					<input type = "radio" class = "sex_radio"id = "male" class = "sex" name = "sex" checked="checked" value = "男"/><span style = "float:left;font-family:'黑体',Arial;">男</span>
			    					<input type = "radio" class = "sex_radio"id = "female" class = "sex" name = "sex" value = "女" style = "margin-left:70px;"/><span style = "float:left;font-family:'黑体',Arial;">女</span>
			    				</div>
			    			</div>
			    			<div class = "register_input">
				   				<div class = "reg_row"></div>
				   				<div style = "margin-left:10px;float:left;width:80px;height:30px;">
				   				<img src ="${pageContext.request.contextPath}/CheckCode" id = "createCheckCode" style = "width:80px;height:30px; float:left;" />
				   				</div>
				   				<a style = "float:left;margin-left:10px;font-size:13px;" href="javascript:void(0);" onclick = "getCheckCode()">看不清？换一张</a>
				   			</div>
				   			<div class = "register_input">
			    				<div class = "reg_row" ><span style = "float:right;">验证码 :</span></div>
			    				<input type ="text" id = "checkcode" name = "checkcode" class = "register_info code" maxlength="5" TextDefault="不分大小写"/>
			    				<div class = "tip" id = "checkcode_tip" style = "left:200px;"><img src = "${pageContext.request.contextPath}/web/image/register/error_lefttip.png"/><div id = "checkcode_tip_info"></div><img src = "${pageContext.request.contextPath}/web/image/register/error_righttip.png"/></div>
			    				<input class = "validate" id = "validate_checkcode" type = "hidden" value = "0"/>
			    			</div>
			    			<div class = "register_input" style = "margin-top:20px;">
			   				<div class = "reg_row" style = "width:70px;"></div>
			   				<span style = "float:left;font-size:13px;">注册并同意<a href="${pageContext.request.contextPath}/agreement" target="_blank">圈易物服务条款</a></span>
			   			</div>
			   			<div class = "register_input" style="width:500px;">
							<img src = "${pageContext.request.contextPath}/web/image/register/register_button.png" id ="register_button" style = "float:left;cursor:pointer;margin-left:120px;width:150px;"/>
			   			</div>
			   			<div class = "register_input">
			   				<div class = "reg_row" style = "width:130px;"></div>
			   				<span style= "float:left;font-size:13px;">去体验新的闲置物品交易方式吧</span>
			   			</div>
		    			</div>
	    			</div>
    			</div>
    		</div>
    	</div>
    </div>
    <s:include value="../base/foot/footDefault.jsp"/>
  </body>
</html>
