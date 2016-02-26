<!DOCTYPE html><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-我的好友</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="quan15，圈易物,大学,朋友,物品分享,个人主页,分享圈,社区,交友,便利生活,校园,闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物网是一个基于真实社交网络的物品分享网站，真实互助，各取所需，分享关心，分享真情。 加入圈易物网你可以:卖、借、赠送闲置物品；了解好友的最新需求，力所能及的帮助他们；找到关系较近的陌生人，在互助中结识新朋友；通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<script type="text/javascript">isNew = "${user.isNewUser}";</script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js" ></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/friends.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/friends.js"></script>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
   	 	<s:include value="../../base/top.jsp"/>
   	 	<div class = "left_nav_box" style = "margin-left:20px;margin-top:20px;">
			<a href = "${pageContext.request.contextPath}/friends"><div class = "left_nav_box_btn" id = "pri_btn" style='color:white;background:#e68303;'>
				好友列表
			</div></a>
			<a href = "${pageContext.request.contextPath}/friends/search"><div class = "left_nav_box_btn" id = "req_btn">
				用户查找
			</div></a>
			<div style = "float:left;margin-top:10px;width:100px;height:20px;background:white;">
			</div>
		</div>  
		<div style = "float:left;margin-left:20px;width:820px;">
			<div style = "float:left;width:100%;height:25px;margin-top:20px;border-radius:0px;border-bottom:1px solid #d8d8d8;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:16px;margin-top:1px;margin-left:10px;"/>
				<span style = "float:left;margin-left:5px;font-size:16px;">好友列表 , 共<label style = "color:#e68303;" id = "friends_count">${friendsNum}</label>个好友</span>
			</div>
			<div class = "show_user_block" style = "margin-top:15px;">
		   	 	<input type = "hidden" id = "pageno" value = "0"/>
		   	 	<div id = "show_friends">
		    		</div>
		    	</div>
		    	<div style = "float:left;width:100%;height:30px;line-height:30px;">
		    		<div style = "float:left;width:100%;height:20px;">
		    			<span id = "sf_loading">好友列表加载中 <img src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/></span>
		    		</div>
		    		<span id = "sf_notFound" style = "float:left;width:100%;display:none;">额..你还未添加任何好友，可以去找找看~</span>
		    		<span id = "sf_more_btn" style = "float:left;width:100%;">
		    			<a id = "show_friends_next" onclick = "friends.mine({opt:'next'});" style = "float:right;margin-right:20px;color:#e68303;cursor:pointer;display:none;">下一页</a>
		    			<a id = "show_friends_front" onclick = "friends.mine({opt:'front'});" style = "float:right;margin-right:20px;color:#e68303;cursor:pointer;display:none;">上一页</a>
		    		</span>
		    	</div>
		</div>
	</div>
	<script type="text/javascript">
		friends.mine({pn:0});			
	</script>
	<s:include value="../../base/face.jsp"/>
    <s:include value="../../base/toolBox.jsp"/>
    <s:include value="../../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../../base/bottomBox.jsp"/>
</html>
