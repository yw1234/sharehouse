<!DOCTYPE html><%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-基于真实社交网络的闲置物品分享网站</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="quan15,圈易物,大学,朋友,物品分享,个人主页,分享圈,社区,交友,便利生活,校园,急求，闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物网是一个基于真实社交网络的物品分享网站，真实互助，各取所需，分享关心，分享真情。 加入圈易物网你可以:卖、借、赠闲置物品；了解好友的最新需求，力所能及的帮助他们；找到关系较近的陌生人，在互助中结识新朋友；通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
	<script type="text/javascript">PATH = "${pageContext.request.contextPath}";</script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/index/index.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/index/index.css">
  	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css">
	<![endif]-->
  </head>
  <%/*判断自动登录*/String URL = ServletActionContext.getRequest().getContextPath() + "login", u_s = null, u_id = null ,url_params = ServletActionContext.getRequest().getParameter("url");
	Cookie cookies[] = request.getCookies();
	if(cookies != null && cookies.length > 0)
		for(Cookie c : cookies)
		{
			if(c.getName().equals("q_s")){
				u_s = c.getValue();
			}
			else if(c.getName().equals("q_uid")){
				u_id = c.getValue();
			}
		}
	if(u_s != null && u_id != null)
	{
		session.setAttribute("auto_login","1".trim());
		if(url_params != null && !url_params.equals(""))
			URL += "?url="+url_params;
		response.sendRedirect(URL);
	}
%>
  <body>
  	<div class = "main">
  		<div id = "home_top">
  			<div id = "home_top_main">
  				<img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;"/>
		    		<div class = "index_nav">
		    			<ul>
		    				<li><a style = "color:#fdfdfd;" href="${pageContext.request.contextPath}/register">注册</a></li>
		    				<li><a style = "color:#fdfdfd;" href="${pageContext.request.contextPath}/about" target="_blank">帮助</a></li>
		    				<li><a style = "color:#fdfdfd;" href="${pageContext.request.contextPath}/contactUs" target="_blank">联系我们</a></li>
		    			</ul>
		    		</div>
  			</div>
    	</div>
    	<div id="index_main">
    		<div id = "op_main">
    			<div style="width:380px;">
    				<div id = "op_nav">
	    				<span style = "float:left;margin-left:15px;font-family:'黑体',Arial;color:#3a3a3a;font-size:28px;">欢迎来到<img src = "${pageContext.request.contextPath}/web/image/index/index_logo.png" style="vertical-align:middle;margin:5px;"/><span style = "color:#e08614;font-family:'黑体',Arial;">圈易物</span></span>
	    			</div>
	    			<div id = "login">
	    				<div id = "error"></div>
	    				<div class = "login_text" style = "margin-top:0px;"><span style = "float:left;font-family:'黑体',Arial;color:#3a3a3a;font-size:18px;">账号 :</span><div class = "login_text_block" style = "z-index:10;"><input type = "text" class = "text" id = "email" autocomplete="off"/><label class = "text_tip" id = "email_text_tip">请输入注册邮箱</label>
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
	    					</div>
	    				</div>
	    				<div class = "login_text"><span style = "float:left;font-family:'黑体',Arial;color:#3a3a3a;font-size:18px;">密码 :</span><div class = "login_text_block"><input type = "password" class = "text" id = "password"/> <label class = "text_tip" id = "password_text_tip">请输入密码</label></div></div>
	    				<div style = "margin-top:20px;margin-left:105px;width:300px;height:25px;float:left;line-height:20px;"><input type = "checkbox" class = "checkbox" id = "remindme" value = "autologin"style = "float:left;"/>
	    					<span style = "float:left;font-size:14px;font-family:'黑体',Arial;color:#454445;cursor:pointer;" checked onclick="document.getElementById('remindme').click()" title="公共场合慎选此项!">记住密码</span>
	    					<a href = "/pwd_reset" style = "float:left;margin-left:75px;"><span style = "font-size:14px;font-family:'黑体',Arial;color:#a1a1a1;cursor:pointer;">找回密码</span></a>
	    				</div>
	    				<div class = "index_button" style = "margin-top:30px;">
	    					<a class = "button" style = "float:left;width:120px;height:25px;border-radius:3px;margin-left:40px;line-height:26px;" id = "register_button">注册 (sign up)</a>
	    					<a class = "button" style = "float:left;width:120px;height:25px;border-radius:3px;margin-left:30px;line-height:26px;" id = "login_button" onclick="login()">登录 (sign in)</a>
	    				</div>
	    				<div style = "float:left;width:100%;line-height:18px;margin-top:20px;line-height:25px;">
	    					<p style="float:left;font-size:14px;margin-left:40px;color:#a1a1a1;"><span style = "float:left;color:#b1b1b1;">注册很麻烦,可以使用:</span>
							<a href="https://graph.renren.com/oauth/authorize?client_id=229690&response_type=code&redirect_uri=http://${pageContext.request.serverName}/renren/login&display=page">
        					  		<img src = "${pageContext.request.contextPath}/web/image/base/icon/rr_login_icon.png" style = "float:left;width:120px;height:25px;margin-left:20px;"/>
        					  	</a>
						</p>
	    				</div>
	    			</div>
    			</div>
    		</div>
    		<div id="banner"> 
			<ul> 
			<li class = "on" loc = "1"></li> 
			<li loc = "2"></li> 
			<li loc = "3"></li> 
			</ul> 
			<div id="banner_list"> 
				<a><img src = "${pageContext.request.contextPath}/web/image/index/index_instruction_default.jpg"/></a>
				 <a style = "display:none;"><img src = "${pageContext.request.contextPath}/web/image/index/act_sellbook_instruction.jpg" style = "margin-top:20px;"/></a>
			    	<a style = "display:none;"><img src = "${pageContext.request.contextPath}/web/image/index/act_lostback_instruction.jpg"/></a>
			</div> 
		</div> 
    	</div>
    	<s:include value="base/foot/footDefault.jsp"/>
  	</div>
  </body>
</html>
