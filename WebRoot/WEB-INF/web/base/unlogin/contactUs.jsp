<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-联系我们</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">  
	<meta http-equiv="keywords" content="quan15,圈易物,大学,朋友,物品分享,个人主页,分享圈,社区,交友,便利生活,校园,急求，闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物（或者圈15网） 圈易物网是一个基于真实社交网络的物品分享网站,真实互助,各取所需,分享关心,分享真情。 加入圈易物网你可以:租、借、买卖、赠送闲置物品;了解好友的最新需求,力所能及的帮助他们;找到关系较近的陌生人,在互助中结识新朋友;通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
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
  		label{float:left;width:100%;font-size:22px;margin-top:20px;font-family:"黑体",Arial;}
  		p{float:left;width:100%;font-size:14px;text-align:left;line-height:20px;}
  	</style>
  </head>
  
  <body>
     <div class = "main" id = "main" style="min-height:0px;">
     	<div id = "home_top">
			<div id = "home_top_main">
				<a href = "${pageContext.request.contextPath}/home"><img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;"/></a>
			</div>
		</div>
		<div style="margin-top:0px;width:700px;">
			<div style="margin:0px;background:url(${pageContext.request.contextPath}/web/image/base/instruction_bg_top.jpg) no-repeat;width:700px;height:30px;">
			</div>
			<div style="background:url(${pageContext.request.contextPath}/web/image/base/instruction_bg.jpg);width:700px;float:left;">
				<div style="float:left;width:650px;margin-left:25px;margin-top:30px;margin-right:25px;">
					<label>
						圈易物
	交换放错了地方的资源，分享来自身边的美好
					</label>
					<label>
	圈易物出自北航，欢迎来自四方的朋友
					</label>
					<ul style="float:left;margin-left:100px;margin-top:40px;">
						<li style="float:left;width:500px;margin-top:10px;text-align:left;font-size:17px;line-height:30px;font-family:'黑体',Arial;">联系我们</li>
						<li style="float:left;width:500px;margin-top:10px;text-align:left;line-height:30px;">Email : <span style="color:#e68303;">kf@quan15.com</span></li>
						<li style="float:left;width:500px;margin-top:10px;text-align:left;line-height:30px;">QQ : <span style="color:#e68303;">2811083834</span></li>
						<li style="float:left;width:500px;margin-top:10px;text-align:left;line-height:30px;">人人公共主页 : <a style="color:#e68303;" href = "http://page.renren.com/601706701" target="_blank">圈易物网</a></li>
					</ul>
				</div>
				<div style = "float:left;width:100%;height:70px;"></div>		
			</div>
		</div>
     </div>
      <s:include value="../foot/footDefault.jsp"/>
  </body>
</html>
     