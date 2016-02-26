<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-<s:if test = "#request.info.id==#request.user.id">我</s:if><s:else>${info.realname}</s:else>的主页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/mypage.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/sendBox.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/tempBase.js"></script>
	<s:if test="%{#parameters.page[0]=='info'}">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery.Jcrop.min.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/myPage/info.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/jquery.Jcrop.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/myPage/info.css"/>
	</s:if>
	<s:if test="%{#parameters.page[0]=='privacy'}">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/myPage/privacy.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/myPage/privacy.css"/>
	</s:if>
	<s:if test="%{#parameters.page[0]=='friendsManage'}">
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/myPage/friendsManage.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/myPage/friendsManage.css"/>
	</s:if>
	<s:if test="%{#parameters.page[0]=='friends'}">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/friends.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/friends.js"></script>
	</s:if>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/sendBox.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/tempBase.css"/>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = 'lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
     	<input type = "hidden" id = "userinfo_id" value = "${info.id}"/>
    		<s:include value="../base/top.jsp"/>
		<div style = "float:left;">
			<div style = "float:left;width:160px;height:160px;background:url(${pageContext.request.contextPath}/web/image/home/top_block_bg.png) center center;border-radius:0px 0px 5px 5px;">
				<div style = "float:left;width:150px;height:150px;border-radius:5px;background:white;margin-top:5px;margin-left:5px;overflow:hidden;line-height:150px;"><img src = "${info.head_ico_big}" style = "table-layout:fixed;vertical-align:middle;width:150px;border-radius:5px;"/></div>
			</div>
			<div id = "top_block" style = "float:left;width:820px;height:80px;background:#efefef;border-radius:0px;">
				<s:if test = "#request.info.id == #request.user.id">
					<div id = "send_block">
		     			<s:include value="../base/box/sendBox.jsp"/>
		     		</div>
	     		</s:if>
	     		<s:else>
					<div id = "send_block">
						<div style = "float:left;margin-top:5px;margin-left:10px;height:70px;width:125px;">
							<span style = "float:left;width:125px;height:25px;text-align:left;line-height:20px;font-size:14px;"><a style = "color:#e68303;">${info.realname}</a></span>
							<span style = "float:left;width:125px;height:25px;line-height:20px;text-align:left;font-size:13px;">连续登录<label style="color:#e68303;">${info.continue_online}</label>天</span>
							<span style = "float:left;width:125px;height:25px;line-height:20px;text-align:left;font-size:13px;">
								<s:if test = '#request.info.is_pass == "1" || #request.info.registerByAPI == "1"'>
									<img src = "${pageContext.request.contextPath}/web/image/base/icon/email_icon_activate.png" style = "float:left;width:20px;margin-top:3px;curosr:pointer;" title = "账号已验证"/>
								</s:if>
								<s:else>
									<img src = "${pageContext.request.contextPath}/web/image/base/icon/email_icon_unactivate.png" style = "float:left;width:20px;margin-top:3px;curosr:pointer;" title = "账号未验证"/>
								</s:else>
								<s:if test = '#request.renren_binding == "1"'>
									<img src = "${pageContext.request.contextPath}/web/image/base/icon/renren_icon.png" style = "float:left;width:25px;margin-left:5px;margin-top:3px;curosr:pointer;" title = "已绑定人人"/>
								</s:if>
							</span>
						</div>
						<div style = "float:left;margin-top:18px;width:420px;height:65px;">
							<textarea id = "pl_leave_msg_text" style = "float:left;line-height:20px;width:320px;height:35px;border-radius:6px;border:1px solid #e6e6e6;padding-left:10px;overflow-y:auto;resize:none;" TextDefault="有什么想对${info.realname}说吗?给<s:if test='#request.info.sex=="男"'>他</s:if><s:else>她</s:else>个私信~" onkeyup=" textLimit.getLength({id:'pl_textlimit',target_text:$(this)});"></textarea>
			     			<div class = "facelist face-switch" id = "pl_facelist" onclick = "getFaceList('pl_facelist',$('#pl_leave_msg_text'),'pl_textlimit');" style = "margin-top:2px;margin-left:5px">
								<img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/>
							</div>
							<div id = "pl_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;">0/140</div>
							<div style = "float:left;margin-top:5px;margin-left:7px;text-align:left;width:69px;"><input type="checkbox" id = "pl_private" style="float:left;margin-left:0px;" checked/><span style="float:left;font-size:13px;line-height:22px;color:grey;">不公开</span></div>
						</div>
		     			<div class="button" id="pl_leave_btn" style = "float:left;width:50px;height:40px;margin-top:20px;border-radius:4px;line-height:40px;font-size:15px;cursor:pointer;font-family:'黑体',Arial;" onclick="sendPrivateMessage({btn:$(this),target_text:$('#pl_leave_msg_text'),userid:${info.id}});" >留言</div>
		     			<s:if test='#request.areFriends==0'>
		     				<div class="button" style = "float:left;margin-left:20px;width:80px;height:40px;margin-top:20px;border-radius:4px;line-height:40px;font-size:15px;cursor:pointer;font-family:'黑体',Arial;" onclick="addFriendsRequest({userid:'${info.id}'});">加为好友</div>
		     			</s:if>
		     		</div>
	    		</s:else>
			</div>
			<s:if test='#request.canSee=="1"'>
				<div class = "bar" style = "width:820px;">
		    		<a href="${pageContext.request.contextPath}/user/${info.id}"><span class = "bar_nav_btn <s:if test = "%{#parameters.page==null || #parameters.page[0]=='main'}">bar_selected</s:if>">闲置墙</span></a><div class = "bar_split"></div>
		    		<s:if test='%{#request.info.id != #request.user.id && #request.canSeeFri=="1"}'><a href="${pageContext.request.contextPath}/user/${info.id}?page=friends&p=1"><span class = "bar_nav_btn <s:if test = "%{#parameters.page[0]=='friends'}">bar_selected</s:if>">好友列表</span></a><div class = "bar_split"></div></s:if>
					<s:if test = "#request.info.id == #request.user.id"><a href="${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=all&p=1"><span class = "bar_nav_btn <s:if test = "%{#parameters.page[0]=='friendsManage'}">bar_selected</s:if>">好友管理</span></a><div class = "bar_split"></div></s:if>
					<a href="${pageContext.request.contextPath}/user/${info.id}?page=info"><span class = "bar_nav_btn <s:if test = "%{#parameters.page[0]=='info'}">bar_selected</s:if>">个人信息</span></a><div class = "bar_split"></div>
					<s:if test = "#request.info.id == #request.user.id"><a href="${pageContext.request.contextPath}/user/${info.id}?page=privacy&nav=common"><span class = "bar_nav_btn <s:if test = "%{#parameters.page[0]=='privacy'}">bar_selected</s:if>">隐私设置</span></a><div class = "bar_split"></div></s:if>
				</div>
				<s:if test = "%{#parameters.page==null || #parameters.page[0]=='main'}">
					<div id = "selected_block" style="float:left;border-bottom:1px solid #d8d8d8; width:820px;height:30px;margin-top:5px;border-radius:0px;">
						<div class = "selected_block_li">
							<span>处理方式：</span>
					    	<s:select name = "shareType" cssClass = "notes_select" style = "background:white;" id = "share_type" list = "#{'':'全部','sell':'卖','lend':'借','gift':'送'}" onchange = "selectedSubmit();"/>
						</div>
						<div class = "selected_block_li">
							<span>分享完成：</span>
							<s:select id = "select_onlyShared" name = "onlyShared" style = "background:white;"cssClass = "notes_select" list="#{'0':'否','1':'是'}" onchange = "selectedSubmit();"></s:select>
						</div>
					</div>
				</s:if>
			</s:if>
			<s:else>
				<span style = "float:left;margin-top:5px;letter-spacing:1px;line-height:25px;margin-left:10px;font-size:14px;text-align:left;font-size:14px;font-family:'黑体',Arial;">${info.school}-${info.educational}-${info.department}${info.hs_year}</span>
				<div style = "float:left;width:810px;margin-left:6px;margin-top:10px;height:35px;">
					<s:if test = '#request.info.privacy_myPage=="0"'><span style = "float:left;letter-spacing:1px;margin-left:5px;line-height:25px;font-size:16px;font-family:'黑体',Arial;text-align:left;">还没有加<span style = "color:#e68303;font-size:16px;font-family:'黑体',Arial;">${info.realname}</span>为好友,只能查看部分分享信息 | 共<span style = "color:#e68303;">${countNumber[1]}</span>个好友,分享<span style = "color:#e68303;">${info.send_active}</span>次</span></s:if>
					<s:elseif test = '#request.info.privacy_myPage=="1"'><span style = "float:left;letter-spacing:1px;margin-left:5px;line-height:25px;font-size:16px;font-family:'黑体',Arial;text-align:left;">还没有加<span style = "color:#e68303;font-size:16px;font-family:'黑体',Arial;">${info.realname}</span>为好友,只能查看部分分享信息 | 共<span style = "color:#e68303;">${countNumber[1]}</span>个好友,分享<span style = "color:#e68303;">${info.send_active}</span>次</span></s:elseif>
					<s:elseif test = '#request.info.privacy_myPage=="2"'></s:elseif>
				</div>
			</s:else>
		</div>
	    <div id = "content">
		    	<s:if test='#request.canSee=="1"'>
		    		<s:include value="myPage/main.jsp"/>
		    	</s:if>
		    	<s:else>
		    		<s:include value="myPage/stranger.jsp"/>
		    	</s:else>
	    </div>
    </div>
    <s:include value="../base/face.jsp"/>
    <s:include value="../base/toolBox.jsp"/>
    <s:include value="../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../base/bottomBox.jsp"/>
</html>
