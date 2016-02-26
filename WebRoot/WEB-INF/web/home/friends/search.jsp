<!DOCTYPE html><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-好友查找</title>
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
			<a href = "${pageContext.request.contextPath}/friends"><div class = "left_nav_box_btn" id = "pri_btn">
				好友列表
			</div></a>
			<a href = "${pageContext.request.contextPath}/friends/search"><div class = "left_nav_box_btn" id = "req_btn" style='color:white;background:#e68303;'>
				用户查找
			</div></a>
			<div style = "float:left;margin-top:10px;width:100px;height:20px;background:white;">
			</div>
		</div>  
		<div style = "float:left;margin-left:20px;width:820px;">
			<div style = "float:left;width:100%;height:25px;margin-top:20px;border-radius:0px;border-bottom:1px solid #d8d8d8;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:16px;margin-top:1px;margin-left:10px;"/><span style = "float:left;margin-left:5px;font-family:'黑体',Arial;font-size:16px;">用户搜索</span></div>
			<div class = "search_block">
				<form action = "${pageContext.request.contextPath}/result?type=user" method = "post" id = "search_form">
					<div style = "float:left;width:600px;">
						<div class = "search_list">
							<span>姓名:</span>
							<input class = "search_text" name = "realname" id = "search_realname" maxlength = "6"/>
						</div>
						<div class = "search_list">
							<span>性别:</span>
							<s:select cssClass = "search_select" name = "sex" id = "search_sex" list="#{'':'不限',2:'妹纸',1:'帅哥'}"></s:select>
						</div>
						<div class = "search_list">
							<span>所在学校:</span>
							<input class = "search_text" name = "school" id = "search_school" style="cursor:pointer;" readonly onclick="schoolBox.show();"/>
						</div>
						<div class = "search_list">
							<span>所在地:</span>
							<select class = "search_select" id = "search_province" name = "province"></select><select class = "search_select" id = "search_city" name = "city"></select>
						</div>
					</div>
					<input type="submit" id = "submit_btn" class = "button" style = "float:left;width:60px;margin-top:12px;line-height:30px;font-size:16px;border-radius:3px;" value = "搜索">
				</form>
			</div>
			<div style = "float:left;width:100%;height:25px;margin-top:40px;border-radius:0px;border-bottom:1px solid #d8d8d8;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:16px;margin-top:1px;margin-left:10px;"/><span style = "float:left;margin-left:5px;font-family:'黑体',Arial;font-size:16px;">好友推荐</span></div>
			<div class = "show_user_block">
		   	 	<input type = "hidden" id = "pageno" value = "0"/>
		   	 	<div id = "show_friends">
		    		</div>
		    	</div>
		    	<div style = "float:left;margin-top:10px;width:100%;height:30px;line-height:30px;">
		    		<span id = "sf_notFound" style = "float:left;width:100%;display:none;">没有找到可能认识的好友,手动搜一下吧</span>
		    		<span id = "sf_more_btn" style = "float:left;width:100%;font-size:16px;font-family:'黑体',Arial;color:#e68303;cursor:pointer;display:none;" onclick = "friends.mayKnow({});">再看一组</span>
		    		<span id = "sf_loading"><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/></span>
		    	</div>
			<div style = "float:left;width:100%;height:30px;">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		friends.mayKnow({pn:0});			
	</script>
	<div class = "add_rex_block">
	   	<input id = "add_userid_input" type = "hidden"/>
	  	<div style = "float:left;width:300px;height:20px;line-height:20px;margin-top:5px;">
	  		<span style = "float:left;text-align:left;font-size:15px;font-family:'黑体',Arial;margin-left:10px;">将<span id = "add_username" style = "font-family:'黑体',Arial;font-size:15px;color:#e68303;"></span>加为好友</span>
			<img id = "addfriends_loading" src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "display:none;"/>  	</div>
	  	<textarea class = "rex_text" TextDefault="验证信息或留言"></textarea>
	 	<div class = "button" id = "add_friends_confirm" onclick = "addFriends.confirm();" style = "float:left;width:50px;height:20px;margin-top:3px;margin-top:3px;margin-left:10px;">发送</div>
	 	<div class = "button" id = "add_friends_cancel" onclick = "addFriends.cancel();" style = "float:left;width:50px;height:20px;margin-top:5px;margin-left:10px;">取消</div>
	</div>
	<s:include value="../../base/box/schoolBox.jsp"/>
	<s:include value="../../base/face.jsp"/>
    <s:include value="../../base/toolBox.jsp"/>
    <s:include value="../../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../../base/bottomBox.jsp"/>
</html>