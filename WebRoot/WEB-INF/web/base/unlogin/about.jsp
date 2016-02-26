<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>关于圈易物</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">  
	<meta http-equiv="keywords" content="quan15，圈易物,大学,朋友,物品分享,个人主页,分享圈,社区,交友,便利生活,校园,急求，闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物（或者圈15） 圈易物网是一个基于真实社交网络的物品分享网站，真实互助，各取所需，分享关心，分享真情。 加入圈易物网你可以:租、借、买卖、赠送闲置物品；了解好友的最新需求，力所能及的帮助他们；找到关系较近的陌生人，在互助中结识新朋友；通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
  	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = 'lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  	<style type="text/css">
  		label{float:left;margin-top:20px;font-family:"黑体",Arial;color:#e68303;}
  		p{float:left;width:100%;font-size:14px;text-align:left;line-height:20px;}
  	</style>
  </head>
  
  <body>
     <div class = "main" id = "main">
     	<div id = "home_top">
			<div id = "home_top_main">
				<a href = "${pageContext.request.contextPath}/home"><img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;"/></a>
			</div>
		</div>
		<div style="margin-top:0px;width:700px;">
			<div style="margin:0px;background:url(${pageContext.request.contextPath}/web/image/base/instruction_bg_top.jpg) no-repeat;width:700px;height:30px;">
			</div>
			<div style="background:url(${pageContext.request.contextPath}/web/image/base/instruction_bg.jpg) repeat;width:700px;float:left;border-radius:0px 0px 20px 20px;">
				<div style="float:left;width:650px;margin-left:25px;margin-right:25px;">
					<h3 style="font-family:'黑体',Arial;font-weight:400;">关于圈易物</h3>
					<label>
						圈易物简介
					</label>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在这里您可以处理您的闲置物品，并分享给自己现实生活中想要分享的好友、同学、邻居、同事等。
					</p>
					<label>
						闲置物品分享功能
					</label>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看闲置信息：在网站的首页，您可以看到缤纷满目的分享物品信息，如果您看中了某一件物品，您可以点击查看详细的闲置物品信息。
					</p>
					<p style = "margin-top:0px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首页中的分享信息均是和自己相关的同学的闲置分享信息，可以对其进行排序、筛选。
在发布物品信息时，您应添加物品的图片以方便您的物品更好更快的分享出去。
					</p>
					<p style = "margin-top:0px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当您对某件分享物品感兴趣时，您可以在物品详细信息页面“回复”或直接点“想要”。然后，双方通过网站留言、互换联系方式或现实交流约定好现实的物品分享过程，再在现实中完成物品分享。
					</p>
					<label>
						隐私保护
					</label>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户在网站中发布的分享信息只会显示在相同学校中，并且如选择不公开联系方式，除自己之外的人将无法看到，也可选择指定用户可见。
					</p>
				</div>
				<div style = "float:left;width:100%;height:40px;"></div>
			</div>
		</div>
	</div>
	<s:include value="../foot/footDefault.jsp"/>
  </body>
</html>
     