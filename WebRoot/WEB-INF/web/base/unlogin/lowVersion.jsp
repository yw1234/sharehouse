<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>圈易物-浏览器版本过低</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
  	<style type="text/css">
  		body{width:1024px;}
  		.main{width:1024px;}
  		ul li{font-family:'微软雅黑',Arial;color:#6d6d6d;font-size:17px;margin-left:10px;margin-top:5px;}
  	</style>
  </head>
  
  <body>
  	<div class="main" style="height:500px;">
	  	<div id = "home_top">
			<div id = "home_top_main">
				<a href = "${pageContext.request.contextPath}/home"><img src = "${pageContext.request.contextPath}/web/image/home/home_logo.jpg" style = "float:left;height:40px;"/></a>
			</div>
		</div>
		<div style="width:100%;">
			<div style="width:100%;height:480px;background:url(${pageContext.request.contextPath}/web/image/base/ie6.jpg) center center no-repeat;">
				<div style = "float:left;margin-left:20px;margin-top:20px;">
					<ul>
						<li>可以使用以下最新浏览器:</li>
						<li><a rel="nofollow" target="_blank" class="chrome" href="http://dlc2.pconline.com.cn/filedown_51614_6750119/2hSRUeby/CHRZ_Chrome_non_defaultV5.exe">Chrome</a></li>
						<li><a rel="nofollow" target="_blank" class="firefox" href="http://download.firefox.com.cn/releases/webins3.0/official/zh-CN/Firefox-latest.exe">Firefox</a></li>
						<li><a rel="nofollow" target="_blank" class="360" href="http://down.360safe.com/cse/360cse_6.0.0.310.exe">360极速浏览器</a></li>
						<li><a rel="nofollow" target="_blank" class="sogo" href="http://download.ie.sogou.com/se/sogou_explorer_4.0t.exe">搜狗浏览器</a></li>
						<li style="font-size:13px;margin-top:10px;">(友情提示:若下载360或sogo浏览器请切换到极速模式下浏览,效果更好!)</li>
					</ul>
				</div>
			</div>
		</div>
  	</div>
  	<div style = "float:left;width:980px;height:40px;">
		<img src = "${pageContext.request.contextPath}/web/image/base/icon_default.png" style = "float:left;margin-top:10px;"/>
		<div style = "float:left;margin-left:10px; margin-top:10px;line-height:30px;font-size:13px;">Copyright @ 2011-2014 quan15. All Rights Reserved. 圈易物网 版权所有</div>
	</div>
  	
  </body>
</html>
