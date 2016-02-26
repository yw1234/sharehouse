<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style = "float:left;width:220px;height:80px;background:url(${pageContext.request.contextPath}/web/image/home/top_home_bg.png) left center;border-radius:0px;">
	<div style = "float:left;width:70px;height:70px;border-radius:4px;background:white;margin-top:5px;margin-left:10px;"><a href= "${pageContext.request.contextPath}/user/${user.id}?page=info"><img src = "${user.head_ico}" style = "float:left;margin-top:3px;margin-left:3px;width:64px;height:64px;border-radius:4px;"/></a></div>
	<div style = "float:left;margin-top:5px;margin-left:10px;height:70px;width:125px;">
		<span style = "float:left;width:125px;height:25px;text-align:left;line-height:20px;font-size:14px;"><a href= "${pageContext.request.contextPath}/user/${user.id}?page=info" style = "color:#e68303;">${user.realname}</a></span>
		<span style = "float:left;width:125px;height:25px;line-height:20px;text-align:left;font-size:13px;">连续登录<label style="color:#e68303;">${user.continue_online}</label>天</span>
		<span style = "float:left;width:125px;height:25px;line-height:20px;text-align:left;font-size:13px;">
			<s:if test = '#request.user.is_pass == "1" || #request.user.registerByAPI == "1"'>
				<img src = "${pageContext.request.contextPath}/web/image/base/icon/email_icon_activate.png" style = "float:left;width:20px;margin-top:3px;cursor:pointer;" title = "账号已验证"/>
			</s:if>
			<s:else>
				<a href = "javascript:void(0);" style = "font-size:13px;color:#888888" onclick = "activate.send({email:'${user.email}'});"><img src = "${pageContext.request.contextPath}/web/image/base/icon/email_icon_unactivate.png" style = "float:left;width:20px;margin-top:3px;" title = "点击发送验证邮件"/></a>
			</s:else>
			<s:if test = '#request.renren_binding == "1"'>
				<img src = "${pageContext.request.contextPath}/web/image/base/icon/renren_icon.png" style = "float:left;width:25px;margin-left:5px;margin-top:3px;cursor:pointer;" title = "已绑定人人"/>
			</s:if>
		</span>
	</div>
</div>