<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-售书活动专区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="圈易物-售书活动专区">
	<meta http-equiv="description" content="圈易物网是一个基于真实社交网络的物品分享网站，真实互助，各取所需，分享关心，分享真情。 加入圈易物网你可以:卖、借、赠送闲置物品；了解好友的最新需求，力所能及的帮助他们；找到关系较近的陌生人，在互助中结识新朋友；通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/activity/sellbook/main.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js" ></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/activity/sellbook/main.js" ></script>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
    		<s:include value="../../web/base/top.jsp"/>
	    <div id = "sellbook_top">
	    		<div style = "float:left;width:100%;">
	    			<img src = "${pageContext.request.contextPath}/activity/sellbook/image/sellbook_top.jpg" style = "float:left;width:800px;height:60px;margin-left:30px;"/>
	    			<s:if test = '#request.isSbAdmin=="1"'>
		    			<a class = "sb_button" href = "${pageContext.request.contextPath}/activity/sell_book/admin?page=books&p=1" style = "float:left;width:80px;height:40px;line-height:41px;border-radius:3px;margin-left:30px;margin-top:20px;">后台管理</a>
		    		</s:if>
	    		</div>
	    		<div id = "sellbook_introduce">
	    			<div id = "sellbook_introduce_main">
	    				<span style = "float:left;text-align:left;color:white;">
	    					<label style = "float:left;margin:5px;font-size:20px;">活动主旨 :</label>
	    					<label style = "float:left;margin:5px;line-height:25px;margin-top:0px;font-szie:14px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;圈易物联手北航校学生会举办书籍分享活动，为我航学子提供优质廉价的学习资源</label>
	    				</span>
	    			</div>
	    			<div style = "float:left;text-align:left;margin-top:10px;line-height:25px;">
		    			<span style = "float:left;color:#89b043;">取书方式 :</span>
		    			<div style = "float:left;margin-left:5px;width:360px;">
		    				<span style = "float:left;width:100%;text-align:left;">
			    				<label style = "color:#89b043;">1 .</label>北航沙河校区外场，时间地点待定
			    			</span>
			    			<span style = "float:left;width:100%;text-align:left;">
			    				<label style = "color:#89b043;">2 .</label>本部同学的订单我们会在一周之内处理完毕并主动和您联系，将书送到您手中。
			    			</span>
		    			</div>
	    			</div>
	    			<div style = "float:left;text-align:left;margin-top:10px;line-height:25px;">
		    			<span style = "float:left;">若有其他问题请与我们<a href = "javascript:void(0);" onclick = "replyBox.show({userid:302,userName:'大学生超市'});" class = "sb_button" style = "width:45px;height:18px;line-height:19px;">私信</a></span>
	    			</div>
	    		</div> 
	    		<div id = "sellbook_info">
	    			<span style = "color:#89b043;font-size:15px;">活动说明 : </span>
	    			<span><label style = "color:#89b043">1 .</label>活动书籍均为大学生超市活动未卖出的课本或学习资料，所有权为提供的该书的同学，本网站仅作为中转平台，交易所得均会交还卖家。</span>
				<span><label style = "color:#89b043">2 .</label>本活动会优先将书卖给在网站预定的同学，若预订数量超过库存或多人预定同一本书则在外场先到先得。未预定的同学可以在外场选购网上未被定完或未放在网站上的书籍。</span>
				<span><label style = "color:#89b043">3 .</label>网上出售方式分为按单本或按摞出售，单本出售点击"预定"按钮即可，按摞出售则需说明预定的图书在第一张图片中的位置(如左X,右X)或书名，以方便取书。</span>
				<span><label style = "color:#89b043">4 .</label>书籍标价为参考价格，图片均为实物图，若有质量或其他问题可面议价格或现场退订。若预定后未来外场取书则视为退订。</span>
	    		</div>
	    </div>
	    <s:if test = '#request.page=="show"'>
	    		<s:include value="main_show.jsp"/>
	    </s:if>
	    <s:elseif test = '#request.page=="booking"'>
	    		<s:include value="main_booking.jsp"/>
	    </s:elseif>
	 </div>
	<s:include value="../../web/base/face.jsp"/>
    <s:include value="../../web/base/toolBox.jsp"/>
    <s:include value="../../web/base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../../web/base/bottomBox.jsp"/>
</html>