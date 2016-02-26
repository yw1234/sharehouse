<!DOCTYPE html><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-毕业季两校区分享狂欢节</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="圈易物-毕业季两校区分享狂欢节">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/activity/gradcarnival/main.js" ></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/activity/gradcarnival/main.css"/>
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
    		<div style = "float:left;width:800px;margin-left:90px;height:112px;margin-top:20px;">
    			<img src = "${pageContext.request.contextPath}/activity/gradcarnival/image/activity_gradcarnival_bar.jpg" style = "float:left;width:800px;height:112px;"/>
    		</div>
    		<div id = "gc_nav" style = "margin-top:20px;">
    			<ul style = "float:left;">
    				<a href = "#gc_main"><li class = "gc_nav_btn">活动简介</li></a>
    				<li class = "gc_nav_btn" style = "width:5px;font-weight:normal;color:#b1b1b1;">|</li>
    				<a href = "#gc_delivery"><li class = "gc_nav_btn">网站送货说明</li></a>
    				<li class = "gc_nav_btn" style = "width:5px;font-weight:normal;color:#b1b1b1;">|</li>
    				<a href = "#gc_special"><li class = "gc_nav_btn">其他信息</li></a>
    			</ul>
    		</div>
    		<div class = "gc_info_block" id = "gc_main">
    			<p style = "height:40px;"></p>
    			<p class = "title">活动简介</p>
    			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;临近毕业季相信您一定有许多心爱的书籍物品舍不得丢弃，怎么处理呢？最好的方式当然是分享给自己的学弟学妹们。可是北航有学院路和沙河两个校区，物品分享太麻烦？没关系，北航圈易物（www.quan15.com）推出“毕业季分享狂欢节”活动，包您满意！</p>
    			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;圈易物在活动期间，提供从北航本部到沙河的送货功能，本部同学请将成交物品按时送到指定外场，并提供其<label style = "color:#8b1f21;font-size:18px;">分享单编号</label>(交易达成时会自动生成)即可；沙河同学请在沙河外场时凭<label style = "color:#8b1f21;font-size:18px;">分享单编号</label>取货</p>
    			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本部第一次外场 ：<label style = "color:#8b1f21;font-size:18px;">4月26日 11:30-14:30，地点：绿园南门</label>(已举办完成)</p>
    			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本部第二次外场 ：待定</p>
    			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;沙河第一次外场 ：待定<!-- <label style = "color:#8b1f21;font-size:18px;">4月*日 10:00-16:40，地点：北航*****</label> --></p>
    		</div>
    		<div class = "gc_info_block" id = "gc_delivery">
    			<p style = "height:40px;"></p>
    			<p class = "title">网站送货说明</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;流程说明：</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择“网站送货”并双方达成交易后，卖方需要按时将物品送到本部指定物品接收点。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;届时将有专人在物品接收点接收物品并开具收据，请妥善保管好收据。网站再统一将物品送到沙河，买方需到沙河专门的外场领取物品并支付货款+运费。在交货之后，网站通过电话通知卖方，卖方凭收据收取货款。请务必填写准确的电话号码！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运费说明：</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运费由买方直接支付给网站，不包含在主页显示的物品价格中！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于网站采用特定送货箱（尺寸**x**），因此只接受满足尺寸要求的物品。运费为1元/单位体积，一个单位体积为一本高数书籍大小。每件的最低运费为1元。注：参加网站活动可以免除一些运费哦！</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;书籍特殊说明：书籍采用特殊运费1-5本/1元，6-10本/2元，11-15本/3元，15本以上4元。</p>
    		</div>
    		<div class = "gc_info_block" id = "gc_special">
    			<p style = "height:40px;"></p>
    			<p class = "title">特殊情况说明：</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、如卖家达成交易后未在指定时间将货物送到网站接收点，则视为自动放弃本次活动。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、如网站未把卖家货物按时送到沙河，将会组织第二次送货活动，买家需要同意网站变更交货时间。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、如买家未按照网站要求到沙河指定交货外场付款取货，网站将在活动后第二天再次联系买家取货，如买家仍未答复，则网站将把货物送回学院路的卖家。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、在网站送货并货款后，卖家才能在网站付款点收取货款。参加此活动的卖家视为自动同意该条。</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本活动最终解释权归圈易物项目团队所有</p>
    		    	<div style = "float:left;width:100%;height:30px;"></div>
    		</div>
	 </div>
    <s:include value="../../web/base/foot/footDefault.jsp"/>
  </body>
</html>