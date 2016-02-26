<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class = "left_nav_box">
	<input type = "hidden" id = "nav" value = "${nav}"/>
	<a href = "${pageContext.request.contextPath}/user/${info.id}?page=privacy&nav=common&p=1"><div class = "left_nav_box_btn" id = "common_btn" <s:if test = "%{#parameters.nav[0]=='common'}">style='color:white;background:#e68303;'</s:if>>
		常规设置
	</div></a>
	<a href = "${pageContext.request.contextPath}/user/${info.id}?page=privacy&nav=cp"><div class = "left_nav_box_btn" id = "cp_btn" <s:if test = "%{#parameters.nav[0]=='cp'}">style='color:white;background:#e68303;'</s:if>>
		密码修改
	</div></a>
	<div style="float:left;width:100%;height:10px;"></div>
</div>  
<s:if test = "%{#parameters.nav[0]=='common'}">
	<div style = "float:left;width:550px;margin-top:10px;margin-left:40px;">
		<input type = "hidden" id = "privacy_pShowUser_input" value = "${info.privacy_pShowUser}"/>
		<input type = "hidden" id = "privacy_pShowCircle_input" value = "${info.privacy_pShowCircle}"/>
		<form id = "privacy_form" style="float:left;width:100%;">
			<div id = "privacy_base">
				<div id = "saveinfo" class = "button" style = "float:right;width:80px;height:25px;line-height:26px;">保存修改</div>
				<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;margin-left:5px;">账号绑定</span></div>
				<div style = "float:left;margin-left:10px;">
					<div class = "privacy_list">
						<span style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/icon/renren_icon.png" style = "float:left;height:30px;height:16px;margin-top:3px;"/>人人账号:</span> 
						<s:if test = '#request.renren_binding == "1"'>
							<span style = "float:left;margin-left:10px;"><a href = "javascript:void(0);" style = "color:#e68303;" onclick = "account.renrenUnbind();">解除绑定</a></span>
						</s:if>
						<s:else>
							<span style = "float:left;margin-left:10px;"><a href = "javascript:void(0);" style = "color:#e68303;" onclick = "account.renrenBind();">绑定</a></span>
						</s:else>
					</div>
				</div>
				<div style = "float:left;margin-top:30px;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;margin-left:5px;">联系方式设置</span></div>
				<div style = "float:left;margin-left:10px;">
					<div class = "privacy_list" style = "height:auto;">
						<span style = "font-size:15px;font-family:'黑体',Arial;">设置可见人:</span> 
						<div style = "float:left;width:350px;">
							<input type = "hidden" id = "pi_set_input" value = "${user.privacy_pInfo}"/>
							<span style = "font-size:14px;width:300px;"><input name = "privacy_pShowUser" class = "pi_set_checkbox" type = "checkbox" id = "pi_showuser"/>指定好友   <!-- <input type = "checkbox" name = "privacy_pShowCircle" class = "pi_set_checkbox" id = "pi_showcircle"/>指定分享圈中用户--></span>  
							<div id = "pi_clist"></div>
							<input type = "hidden" id = "show_pi_clist" name = "show_pi_clist" value = "${show_clist}"/>
							<input type = "hidden" id = "show_pi_ulist" name = "show_pi_ulist" value = "${show_ulist}"/>
						</div>
					</div>
					<div class = "privacy_list">
						<span>手机号:</span> 
						<input type = "text" name = "phone" class = "privacy_input" maxlength = "20" value = "${user.phone}" onpaste = "return false;" onkeydown = "return numbervalidate();"/>
					</div>
					<div class = "privacy_list">
						<span>QQ号:</span> 
						<input type = "text" name = "qq" class = "privacy_input" maxlength = "20" value = "${user.qq}" onpaste = "return false;" onkeydown = "return numbervalidate();"/>
					</div>
					<div class = "privacy_list">
						<span>常用邮箱:</span> 
						<input type = "text" name = "show_email" class = "privacy_input" maxlength = "50" value = "${user.show_email}"/>
					</div>
				</div>
			</div>
			<div id = "privacy_more" style="float:left;width:100%;margin-top:30px;">
				<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">指定显示信息</span><span style = "font-size:14px;font-family:'黑体',Arial;margin-left:5px;color:#e68303;"></span></div>
				<div style = "float:left;margin-left:10px;">
					<div class = "privacy_list">
						<span>我的主页:</span> 
						<s:select name = "privacy_myPage" cssClass= "privacy_select" list="#{0:'同院系,好友可见',1:'好友可见',2:'就自己看'}" value = "#request.info.privacy_myPage"></s:select>
					</div>
					<!-- 
					<div class = "privacy_list">
						<span>所在分享圈:</span> 
						<s:select name = "privacy_myCircleList" cssClass= "privacy_select" list="#{0:'同圈,好友可见',1:'好友可见',2:'就自己看'}" value = "#request.info.privacy_myCircleList"></s:select>
					</div> -->
					<div class = "privacy_list">
						<span>我的好友:</span> 
						<s:select name = "privacy_myUserList" cssClass= "privacy_select" list="#{0:'同院系,好友可见',1:'好友可见',2:'就自己看'}" value = "#request.info.privacy_myUserList"></s:select>
					</div>
					<div class = "privacy_list">
						<span>留言板:</span> 
						<s:select name = "privacy_myMsgList" cssClass= "privacy_select" list="#{0:'同院系,好友可见',1:'好友可见',2:'就自己看'}" value = "#request.info.privacy_myMsgList"></s:select>
					</div>
				</div>
			</div>
		</form>
	</div>
	<s:include value="../../base/box/checkFriendsBox.jsp"></s:include>
</s:if>
<s:elseif test="%{#parameters.nav[0]=='cp'}">
	<div style = "float:left;width:550px;margin-top:10px;margin-left:40px;">
		<div id = "savepwd" class = "button" style = "float:right;width:80px;height:25px;line-height:26px;">保存修改</div>
		<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">密码修改</span></div>
		<div style = "float:left;margin-left:10px;">
			<form id = "privacy_pwd_form" >
				<div class = "privacy_list">
					<span>原密码:</span> 
					<input type = "password" name = "prev_pwd" class = "privacy_input" maxlength = "20"/>
				</div>
				<div class = "privacy_list" style="margin-top:20px;">
					<span>新密码:</span> 
					<input type = "password" name = "new_pwd" class = "privacy_input" maxlength = "20"/>
				</div>
				<div class = "privacy_list" style="margin-top:20px;">
					<span>确认新密码:</span> 
					<input type = "password" name = "re_pwd" class = "privacy_input" maxlength = "20"/>
				</div>
			</form>
		</div>
	</div>
</s:elseif>