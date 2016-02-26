<!DOCTYPE html><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-失物招领活动专区</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="圈易物-失物招领专区">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/base/mtxx.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/activity/lostfound/main.js" ></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/activity/lostfound/main.css"/>
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
    		<div id = "lostfound_top">
	    		<div style = "float:left;width:100%;">
	    			<img src = "${pageContext.request.contextPath}/activity/lostfound/image/lostfound_bar.jpg" style = "float:left;width:940px;height:80px;margin-left:20px;"/>
	    		</div>
	    		<div id = "lostfound_avatar_box">
	    			<img src = "${pageContext.request.contextPath}/web/image/base/icon_small.png" style = "float:left;margin:2px;width:66px;height:66px;border-radius:3px;">
	    		</div>
	    		<div id = "lostfound_introduce">
	    			<span style = "float:left;text-align:left;margin-top:5px;">
	    				<label style = "float:left;width:100%;color:#e68303;font-size:18px;">活动说明: </label>
	    				<span style = "float:left;margin-top:5px;">
	    					<span style = "float:left;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;圈易物收集了北航沙河校区，学院路校区的打印店、食堂、超市、理发店、公寓等的现存失物信息，希望各位施主赶紧登陆看看有没有自己丢失的宝贝。</span>
	    				</span>
	    			</span>
	    		</div>
	    		<s:if test = '#request.isAdmin=="1"'>
	    			<a class = "button" href = "javascript:void(0);" style = "float:right;margin-right:30px;width:100px;height:40px;line-height:41px;border-radius:3px;margin-top:20px;font-size:20px;" onclick = "lost.showBox()">发布失物</a>
	    			<div id = "lostBox">
	    				<div style = "float:left;margin-top:10px;width:100%;line-height:30px;">
	    					<span style = "float:left;font-size:20px;margin-left:18px;color:#e68303;">发布失物</span>
	    					<a href = "javascript:void(0);" onclick = "lost.hideBox();"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:right;width:20px;margin-right:15px;margin-top:5px;" title="取消发布"/></a>
	    				</div>
	    				<div class = "pic_block">
						<input type = "hidden" id = "pic_index" value = "0"/>
						<input type = "hidden" id = "click_number"/>
						<input type = "hidden" id = "pic_number" value = "0"/>
					</div>
	    				<div style = "float:left;width:100%;margin-top:15px;">
	    					<div class = "box_lost_info">
	    						<span class = "b_title">失物名称:</span>
	    						<input id = "b_lost" class = "lf_text" type = "text" maxlength = "30"/>
	    					</div>
	    					<div class = "box_lost_info">
				  			<span class = "b_title">校区:</span>
				  			<s:select id = "b_campus" cssClass = "lf_selected" list="#{'沙河':'沙河','本部':'本部'}"></s:select>
				  		</div>
						<div class = "box_lost_info">
				  			<span class = "b_title">获取地点:</span>
				  			<s:select id = "b_place_gene" cssClass = "lf_selected" list="#{'打印店':'打印店','超市':'超市','理发店':'理发店','食堂':'食堂','公寓楼':'公寓楼'}"></s:select>
				  		</div>
				  		<div class = "box_lost_info">
				  			<span class = "b_title">详细地址:</span>
				  			<input id = "b_place" class = "lf_text" type = "text" maxlength = "30"/>
				  		</div>
	    				</div>
	    				<a id = "send_btn" class = "button" href= "javascript:void(0);" onclick = "lost.send();" style = "float:right;margin-right:20px;margin-top:15px;width:80px;height:30px;line-height:31px;border-radius:3px;">发布</a>
	    				<div style = "float:left;width:100%;height:15px;"></div>
	    			</div>
	    		</s:if>
	    </div>
	    	<s:include value="main_show.jsp"/>
	 </div>
	<s:include value="../../web/base/face.jsp"/>
    <s:include value="../../web/base/toolBox.jsp"/>
    <s:include value="../../web/base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../../web/base/bottomBox.jsp"/>
</html>