<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id = "home_top">
	<div id = "home_top_main">
		<a href = "${pageContext.request.contextPath}/home"><img src = "${pageContext.request.contextPath}/web/image/home/home_logo.png" style = "float:left;height:40px;cursor:pointer;"/></a>
		<div id = "top_nav">
			<a href = "${pageContext.request.contextPath}/home"><div id = "tohome" class = "nav_block"><span style="color:white;">首页</span></div></a>
			<img src = "${pageContext.request.contextPath}/web/image/home/nav_split_bar.png" style = "float:left;height:40px;"/>
			<a href= "${pageContext.request.contextPath}/user/${userid}"><div id = "mypage" class = "nav_block"><span style="color:white;">我的主页</span></div></a>
			<img src = "${pageContext.request.contextPath}/web/image/home/nav_split_bar.png" style = "float:left;height:40px;"/>
			<a href= "${pageContext.request.contextPath}/friends"><div id = "sf" class = "nav_block"><span style="color:white;">我的好友</span></div></a>
			<img src = "${pageContext.request.contextPath}/web/image/home/nav_split_bar.png" style = "float:left;height:40px;"/>
			<a href= "${pageContext.request.contextPath}/activity"><div id = "activity" class = "nav_block"><img src = "${pageContext.request.contextPath}/web/image/base/icon/hot_icon.png" style = "float:left;width:20px;height:20px;margin-top:10px;"/><span style="color:white;">活动专区</span></div></a>
		</div>
		<div style = "float:right;margin-right:5px;">
			<div style = "float:left;width:100px;height:40px;">
				<s:if test="#request.notReadCount!=null&&#request.notReadCount.count>0">
					<a href="${pageContext.request.contextPath}/message?page=news" style = "float:left;margin-top:8px;cursor:pointer;width:26px;height:25px;position:relative;">
						<img title="消息" style = "width:26px;height:25px;" src = "${pageContext.request.contextPath}/web/image/base/icon/top_msg_icon.png"/>
						<div style = "position:absolute;padding:2px;background:white;border-radius:10px;top:-7px;left:15px;"><div style = "color:#fafafa;font-size:13px;background:#e68303;border-radius:100px;padding-left:6px;padding-right:6px;"><s:property value = "#request.notReadCount.count"/></div></div>
					</a>
				</s:if>
				<s:else>
					<a href="${pageContext.request.contextPath}/message?page=pri&p=1" style = "float:left;margin-top:8px;cursor:pointer;width:26px;height:25px;position:relative;">
						<img title="消息" style = "width:26px;height:25px;" src = "${pageContext.request.contextPath}/web/image/base/icon/top_msg_icon.png"/>
					</a>
				</s:else>
				<div style = "float:left;margin-top:8px;cursor:pointer;margin-left:25px;width:24px;height:24px;position:relative;" class = "top_config" id="top_config" title="设置">
					<img style = "width:24px;height:24px;" src = "${pageContext.request.contextPath}/web/image/base/icon/top_cfg_icon.png"/>
					<div class = "top_list_box" style = "position:absolute;left:0px;left:-40px;top:25px;width:100px;border:2px solid #f1f1f1;background:rgba(74,74,74,0.9);background:#4a4a4a;display:none;box-shadow:0px 2px 15px #c1c1c1;">
						<a href = "${pageContext.request.contextPath}/user/${userid}?page=info"><span class="top_list" style = "float:left;width:100%;height:25px;line-height:25px;color:white;font-size:15px;font-family:'黑体',Arial;" title="个人信息">个人信息</span></a>
						<a href = "${pageContext.request.contextPath}/user/${userid}?page=privacy&nav=common"><span class="top_list" style = "float:left;width:100%;height:25px;line-height:25px;color:white;font-size:15px;font-family:'黑体',Arial;" title="隐私设置">隐私设置</span></a>
						<a href = "${pageContext.request.contextPath}/about" target="_blank"><span class="top_list" style = "float:left;width:100%;height:25px;line-height:25px;color:white;font-size:15px;font-family:'黑体',Arial;" title="帮助">帮助</span></a>
						<a href = "${pageContext.request.contextPath}/exitlogin"><span class="top_list" style = "float:left;width:100%;height:25px;line-height:25px;color:white;font-size:15px;font-family:'黑体',Arial;" title="退出">退出</span></a>
					</div>
				</div>
			</div>
			<div style = "float:left;margin-top:9px;">
				<input id = "top_search" name = "key" maxlength="15" TextDefault="闲置搜索"/>
				<img src = "${pageContext.request.contextPath}/web/image/home/search_button.png" id = "search_button" onclick = "topNav.searchSubmit()"/>
			</div>
		</div>
		<input type="hidden" id = "basePath" value = "${pageContext.request.contextPath}/"/>
	</div>
</div>