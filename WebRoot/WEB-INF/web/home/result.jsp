<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-搜索结果</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/home/result.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/tempBase.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/result.css"/>
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
	   <div id = "content">
	    	<s:if test="%{#parameters.type[0]=='user'}">
	    		<s:if test = "#request.noteslist != null && #request.noteslist.size > 0">
			   	<div class = "tip_bar"><span style = "margin-left:20px;font-size:16px;font-family:'黑体',Arial;float:left;line-height:26px;"><span style = "float:left;width:90px;height:25px;line-height:26px;background:#e68303;color:white;border-radius:2px;">好友查找</span><!--  <label style = "color:#e68303;margin-left:10px;"><s:property value = "#request.realname"/></label>---共搜索到<span style = "color:#e68303;"><s:property value = "(#request.userCount>1000)?'1000+':#request.userCount"/></span>条结果--><label style = "margin-left:5px;color:#b1b1b1;">(显示部分用户)</label></span></div>
		    		<div class = "mayknow_block" style = "margin-top:20px;margin-left:60px;">
		    			<s:iterator value = "#request.noteslist" id = "mk" status = "mk_index">
			    			<div class = "mayknow_notes">
				    			<div style = "float:left;width:70px;">
				    				<a href = "${pageContext.request.contextPath}/user/${mk.id}">
					    				<img src = "${mk.avatar}" style = "width:70px;height:70px;border-radius:3px;float:left;"/>
					    			</a>
				    				<span style = "color:#e68303;font-size:13px;cursor:pointer;float:left;" id = "add_friends_btn" onclick = "addFriends.init({btn:$(this),userid:'${mk.id}',username:'${mk.name}'});">加好友</span>
					    			<span style = "float:left;width:5px;color:#c1c1c1;text-align:left;line-height:15px;">|</span>
					    			<span style = "color:#e68303;font-size:13px;cursor:pointer;float:left;" id = "leave_msg_btn" onclick = "replyBox.show({userid:'${mk.id}',userName:'${mk.name}'});">留言</span>
				    			</div>
				    			<div style = "float:left;margin-left:5px;text-align:left;width:125px;">
				    				<span class = "mayknow_span"><img src = "${pageContext.request.contextPath}/web/image/base/icon/icon_<s:if test = '#mk.sex!="男"'>fe</s:if>male.png" style = "float:left;height:14px;"/><a href = "${pageContext.request.contextPath}/user/${mk.id}" style = "color:#e68303;">${mk.name}</a></span>
				    				<span class = "mayknow_span" style = "font-size:13px;">${mk.school}</span>
				    				<span class = "mayknow_span" style = "font-size:13px;">${mk.department}${mk.grade}</span>
				    			</div>
				    		</div>
			    		</s:iterator>
		    		</div>
		    		<div style = "float:left;width:100%;height:30px;">
		    			<div>
		    			</div>
		    		</div>
				<div class = "add_rex_block">
				   <input id = "add_userid_input" type = "hidden"/>
				  	<div style = "float:left;width:300px;height:20px;line-height:20px;margin-top:5px;">
				  		<span style = "float:left;text-align:left;font-size:15px;font-family:'黑体',Arial;margin-left:10px;">将<span id = "add_username" style = "font-size:15px;font-family:'黑体',Arial;color:#e68303;"></span>加为好友</span>
				  		<img id = "addfriends_loading" src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "display:none;"/>
				  	</div>
				  	<textarea class = "rex_text" TextDefault="验证信息或留言"></textarea>
				 	<div class = "button" id = "add_friends_confirm" onclick = "addFriends.confirm();" style = "float:left;width:50px;height:20px;margin-top:3px;margin-left:10px;">发送</div>
				 	<div class = "button" id = "add_friends_cancel" onclick = "addFriends.cancel();" style = "float:left;width:50px;height:20px;margin-top:3px;margin-left:10px;">取消</div>
				</div>
		    	</s:if>
		    	<s:else>
		 		<div class = "tip_bar"><span style = "font-size:16px;float:left;margin-left:10px;line-height:26px;"><span style = "float:left;width:90px;height:25px;line-height:26px;background:#e68303;color:white;border-radius:2px;">好友查找</span><label style = "color:#e68303;margin-left:10px;"><s:property value = "#request.realname"/></label>---搜索结果</span></div>
		    		<div style = "float:left;width:820px;margin-top:15px;margin-left:10px;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:15px;"/><span style = "font-size:16px;float:left;margin-left:5px;">没有找到符合条件的用户</span></div>
		    	</s:else>
	    	</s:if>
    	<s:elseif test="%{#parameters.type[0]=='topSearch'}">
    		<input id = "result_id" type="hidden" value = "${sendType}"/>
    		<div class = "tip_bar"><span style = "font-size:16px;float:left;margin-left:20px;line-height:26px;"><span style = "float:left;width:90px;height:25px;line-height:26px;background:#e68303;color:white;border-radius:2px;">闲置搜索</span><span style = "font-size:16px;font-family:'黑体',Arial;color:#e68303;margin-left:10px;"><s:property value = "#request.key"/></span>---搜索结果</span></div>
    		<s:if test='#request.sendType=="share"'>
    				<div id = "notes" style = "margin-top:20px;"> 
    				<input type = "hidden" id = "key" value = "${key}"/>
    					<input type = "hidden" id = "pageno" value = "0"/>
		    			<input type = "hidden" id = "type" value = "goods"/>
		    			<input type = "hidden" id = "method" value = "result"/>
			    		<div class = "feeds">
			    			<div id = "f_loading" style = 'margin-top:30px;'><span>分享动态获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
			    		</div>
    				</div>
    				<script type="text/javascript">
				    	//获取分享信息
				    	feeds.get({type:"goods",method:"result"});
				</script>
    		</s:if>
	    </s:elseif>
		<s:else>
			<s:include value="../base/errorPage.jsp"/>
		</s:else>
		<div style="float:left;width:100%;height:30px;"></div>
	    </div>
	</div>
	 <s:include value="../base/face.jsp"/>
	<s:include value="../base/toolBox.jsp"/>
	<s:include value="../base/foot/footDefault.jsp"/>
  </body>
  <s:include value="../base/bottomBox.jsp"/>
</html>