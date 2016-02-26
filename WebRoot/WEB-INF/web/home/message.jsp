<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <title>我的消息</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">    
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/message.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/message.css">
	<!--[if lte IE 9]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<s:if test='#request.page=="news"'>
	<script type="text/javascript">window.onload = function(){newsPage.load();}</script>
	</s:if>
  </head>
  
  <body>
    <div class = "main" id = "main">
    	<s:include value="../base/top.jsp"/>
    	<div id = "content">
    		<input type = "hidden" id = "typeid"/>
    		<input type = "hidden" id = "msg_type" value = "<s:property value = "#parameters.page[0]"/>"/>
			<div style = "width:850px;height:20px;float:left;">
				<img src = "${pageContext.request.contextPath}/web/image/home/messagetag.png" style = "float:left;"/>
				<span style = "float:left;margin-left:10px;line-height:24px;font-size:15px;font-weight:bold;">
					<s:if test='#request.page=="news"'>未读消息
					</s:if>
					<s:elseif test='#request.page=="pri"'>私信</s:elseif>
					<s:elseif test='#request.page=="at"'>@我的</s:elseif>
					<s:elseif test='#request.page=="comm"'>与我相关的留言</s:elseif>
					<s:elseif test='#request.page=="req"'>请求信息</s:elseif>
					<s:elseif test='#request.page=="notice"'>通知</s:elseif>
					<s:else>我的消息</s:else>
				</span>	
				<s:if test='#request.page=="news"'>
					<div style="float:left;width:680px;margin-left:50px;height:22px;border-radius:0px;border-bottom:1px solid #d8d8d8;">
						<ul class = "news_ul">
							<a href="javascript:void(0);" id = "at_btn" onclick = "newsPage.getUnReadAtPage({pn:0,btn:$(this)});"><li class = "msg_nav_btn">@我的 <span id = "news_count_at"><s:if test = "#request.unReadCount.atCount != 0">(${unReadCount.atCount})</s:if></span></li></a>
							<li class = "split">|</li>
							<a href="javascript:void(0);" id = "comm_btn" onclick = "newsPage.getUnReadCommentPage({pn:0,btn:$(this)});"><li class = "msg_nav_btn">留言 <span id = "news_count_comm"><s:if test = "#request.unReadCount.commentCount != 0">(${unReadCount.commentCount})</s:if></span></li></a>
							<li class = "split">|</li>
							<a href="javascript:void(0);" id = "pri_btn" onclick = "newsPage.getUnReadPrivatePage({pn:0,btn:$(this)});"><li class = "msg_nav_btn">私信 <span id = "news_count_pri"><s:if test = "#request.unReadCount.privateCount != 0">(${unReadCount.privateCount})</s:if></span></li></a>
							<li class = "split">|</li>
							<a href="javascript:void(0);" id = "req_btn" onclick = "newsPage.getUnReadReguestPage({pn:0,btn:$(this)});"><li class = "msg_nav_btn">请求 <span id = "news_count_req"><s:if test = "#request.unReadCount.requestCount != 0">(${unReadCount.requestCount})</s:if></span></li></a>
							<li class = "split">|</li>
							<a href="javascript:void(0);" id = "notice_btn" onclick = "newsPage.getUnReadNoticePage({pn:0,btn:$(this)});"><li class = "msg_nav_btn">通知 <span id = "news_count_notice"><s:if test = "#request.unReadCount.noticeCount != 0">(${unReadCount.noticeCount})</s:if></span></li></a>
							<li class = "split">|</li>
						</ul>
						<input type = "hidden" class = "msg_count" id = "at_count" target = "at" value = "${unReadCount.atCount}">
						<input type = "hidden" class = "msg_count" id = "comm_count" target = "comment" value = "${unReadCount.commentCount}">
						<input type = "hidden" class = "msg_count" id = "pri_count" target = "private" value = "${unReadCount.privateCount}">
						<input type = "hidden" class = "msg_count" id = "req_count" target = "request" value = "${unReadCount.requestCount}">
						<input type = "hidden" class = "msg_count" id = "notice_count" target = "notice" value = "${unReadCount.noticeCount}">
					</div>
				</s:if>
			</div> 
			<div class = "left_nav_box" style = "margin-top:25px;">
				<a href = "${pageContext.request.contextPath}/message?page=pri&p=1"><div class = "left_nav_box_btn" id = "pri_btn" <s:if test = "%{#parameters.page[0]=='pri'}">style='color:white;background:#e68303;'</s:if>>
					私信
				</div></a>
				<a href = "${pageContext.request.contextPath}/message?page=comm&p=1"><div class = "left_nav_box_btn" id = "pri_btn" <s:if test = "%{#parameters.page[0]=='comm'}">style='color:white;background:#e68303;'</s:if>>
					相关留言
				</div></a>
				<a href = "${pageContext.request.contextPath}/message?page=at&p=1"><div class = "left_nav_box_btn" id = "pri_btn" <s:if test = "%{#parameters.page[0]=='at'}">style='color:white;background:#e68303;'</s:if>>
					@我的
				</div></a>
				<a href = "${pageContext.request.contextPath}/message?page=req&p=1"><div class = "left_nav_box_btn" id = "req_btn" <s:if test = "%{#parameters.page[0]=='req'}">style='color:white;background:#e68303;'</s:if>>
					请求
				</div></a>
				<a href = "${pageContext.request.contextPath}/message?page=notice&p=1"><div class = "left_nav_box_btn" id = "notice_btn" <s:if test = "%{#parameters.page[0]=='notice'}">style='color:white;background:#e68303;'</s:if>>
					通知
				</div></a>
				<div style = "float:left;margin-top:10px;width:100px;height:20px;background:white;">
				</div>
			</div>  
			<div id = "msg_main">
				<s:if test='#request.page=="news"'>
					<div id = "msg_loading" style = "margin-top:40px;">
						<span>消息获取中</span>
						<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/>
					</div>
					<div class = "msg_news_view" id = "at_news_view">
					</div>
					<div class = "msg_news_view" id = "pri_news_view">
					</div>
					<div class = "msg_news_view" id = "req_news_view">
					</div>
					<div class = "msg_news_view" id = "notice_news_view">
					</div>
					<div class = "msg_news_view" id = "comm_news_view">
					</div>
				</s:if>
				<s:elseif test='#request.page=="at"'>
					<s:include value = "messagePage/at_msg.jsp"/>
				</s:elseif>
				<s:elseif test='#request.page=="pri"'>
					<s:include value = "messagePage/private_msg.jsp"/>
				</s:elseif>
				<s:elseif test='#request.page=="comm"'>
					<s:include value = "messagePage/comment_msg.jsp"/>
				</s:elseif>
				<s:elseif test='#request.page=="req"'>
					<s:include value = "messagePage/request_msg.jsp"/>
				</s:elseif>
				<s:elseif test='#request.page=="notice"'>
					<s:include value = "messagePage/notice_msg.jsp"/>
				</s:elseif>
				<s:else>
					出错了...
				</s:else>
			</div>	
			<div style = "float:left;width:100%;height:30px;"></div>
	    	</div>
		<div id = "req_box_container"></div>
		<div id = "order_container"></div>
	   	<s:include value="../base/face.jsp"/>
    	</div>
    	<s:include value="../base/toolBox.jsp"/>
   	<s:include value="../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../base/bottomBox.jsp"/>
</html>
