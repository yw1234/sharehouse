 <!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-首页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="quan15,圈易物,大学,朋友,物品分享,个人主页,分享圈,社区,交友,便利生活,校园,闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物网是一个基于真实社交网络的物品分享网站，真实互助，各取所需，分享关心，分享真情。 加入圈易物网你可以:卖、借、赠闲置物品；了解好友的最新需求，力所能及的帮助他们；找到关系较近的陌生人，在互助中结识新朋友；通过虚拟社区改善现实社区生活；经营自己的交际圈,展示自我。">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/home.js" ></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/tempBase.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/sendBox.js" async></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/sendBox.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/tempBase.css"/>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
    		<s:include value="../base/top.jsp"/>    		
	    <div id = "top_block" style = "float:left;width:980px;height:80px;background:#efefef;border-radius:0px;">
			<s:include value="../base/box/baseInfoBox.jsp"/>
			<div id = "send_block">
     			<s:include value="../base/box/sendBox.jsp"/>
     		</div>
		</div>
		<div id = "selected_bar" class = "bar" style = "float:left;width:100%;height:35px;border-radius:0px;z-index:101;">
			<div class = "tl_btn" style = "float:left;width:130px;position:relative;cursor:pointer;z-index:80;">
				<label  style = "cursor:pointer;"onclick = "selectedSubmit({clearType:true});">闲置分类</label>
				<s:if test='#request.typeList!=null'>
					<div id = "t_table" style = "position:absolute;top:35px;left:-1px;width:130px;border:1px solid #c1c1c1;border-top:0px;display:none;box-shadow: 0 5px 10px rgba(0,0,0,.5);z-index:101;">
						<input type="hidden" id="selectedObjType1"/>
						<input type="hidden" id="selectedObjType2"/>
						<s:set id = "typeList" name="typeList" value='#request.typeList.split(",")'></s:set>
						<s:iterator id = "tl" value = "#typeList" status = "tl_index">
							<div id = "tl_li_${tl_index.index+1}" class = "tl_li tlg">
								<input type = "hidden" value = "${tl_index.index}" class = "tl_index">
								<label class = "tl_type" style = "cursor:pointer;"><s:property value = "#tl"/></label>
								<div class = "tl_details" id = "tld_${tl_index.index+1}" style = "position:absolute;top:-1px;left:120px;width:130px;border:1px solid #c1c1c1;background:#f8f8f8;box-shadow: 2px 2px 10px rgba(0,0,0,.5);border-radius:0px 3px 3px 0px;z-index:102;display:none;">
									<img class = "tl_loading" src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/>
									<div class = "tld_flag"></div>
								</div>
							</div>
						</s:iterator>
					</div>
				</s:if>
			</div>
			<div class = "bar_split"></div>
			<div id = "selected_block" style="float:left;margin-top:4px;width:840px;height:30px;">
				<div class = "selected_block_li">
					<span>范围：</span>
					<s:select cssClass = "notes_select" name = "scope" id = "scope" list = "#{'':'同校','1':'相同院系','2':'同院系且同年级','3':'仅好友'}" onchange = "selectedSubmit();"></s:select>
				</div>
				<div class = "selected_block_li">
					<span>处理方式：</span>
			    	<s:select name = "shareType" cssClass = "notes_select" id = "share_type" list = "#{'':'全部','sell':'卖','lend':'借','gift':'送'}" onchange = "selectedSubmit();"/>
				</div>
				<div class = "selected_block_li">
					<span>排序方式：</span>
					<s:select cssClass = "notes_select" name = "sortType" id = "sort_type" list = "#{'':'按时间','1':'按热度'}" onchange = "selectedSubmit();"></s:select>
				</div>
				<div class = "selected_block_li">
					<span>只想看：</span>
					<s:select id = "select_sex" name = "sex" cssClass = "notes_select" style = "width:110px;" list="#{'':'所有人的分享','0':'美女的分享','1':'帅哥的分享'}" onchange = "selectedSubmit();"></s:select>
				</div>
				<div class = "selected_block_li">
					<span>分享完成：</span>
					<s:select id = "select_onlyShared" name = "onlyShared" cssClass = "notes_select" list="#{'0':'否','1':'是'}" onchange = "selectedSubmit();"></s:select>
				</div>
			</div>
		</div>
		<div id = "content">
		    	<div class = "notes" id = "notes">
	    			<input type = "hidden" id = "pageno" value = "0"/>
	    			<input type = "hidden" id = "type" value = "goods"/>
	    			<input type = "hidden" id = "method" value = "feeds"/>
    				<div style = "float:left;height:30px;line-height:30px;border-bottom:1px solid #c1c1c1;border-radius:0px;">
	    				<span id="objType1" style = "float:left;margin-left:30px;margin-right:30px;color:#e68303;display:none;"></span>
	    				<span id="objType2" style = "float:left;margin-right:30px;display:none;"></span>
	    			</div>
	    			<div id = "f_loading" style = 'float:left;width:100%;margin-top:40px;'><span>分享动态获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
		    		<div class = "feeds">
		    		</div>
		    	</div>
	    </div>
    </div>
    <script type="text/javascript">
    		//获取分享信息
  	  	feeds.get({type:"goods",method:"feeds",pn:"0"});
    </script>
	<s:include value="../base/face.jsp"/>
    <s:include value="../base/toolBox.jsp"/>
    <s:include value="../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../base/bottomBox.jsp"/>
</html>