<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id = "checkfriends_box">
	<div id = "cf_top" onmousedown="move($(this).parent())">
		<span style = "float:left;color:#e68303;font-family:'黑体',Arial;font-size:16px;line-height:31px;margin-left:20px;">好友选择:</span>
	</div>
	<div id = "cf_main">
		<div style = "float:left;margin-left:18px;width:660px;height:0px;background:white;border:1px solid #e4e3e3;border-left:2px solid #e4e3e3;border-right:2px solid #e4e3e3;">
		</div>
		<img id = "cf_loading" src = "${pageContext.request.contextPath}/web/image/base/pic_notes_loading.gif" style = "display:none;"/>
		<div id = "cf_checked_user">
		</div>
		<div id = "cf_checked_username">
		</div>
		<div id = "cf_friends_block">
		</div>
	</div>
	<div id = "cf_op">
		<ul style = "float:left;margin-top:20px;margin-left:20px;">
			<li id = "cf_prior" style = "float:left;font-size:14px;font-family:'黑体',Arial;cursor:pointer;color:#c1c1c1;">上一页</li>
			<li id = "cf_next" style = "float:left;margin-left:20px;font-family:'黑体',Arial;font-size:14px;cursor:pointer;">下一页</li>
			<li id = "cf_selectAll" style = "float:left;margin-left:20px;font-family:'黑体',Arial;font-size:14px;cursor:pointer;">全选</li>
			<li id = "cf_unSelectAll" style = "float:left;margin-left:20px;font-family:'黑体',Arial;font-size:14px;cursor:pointer;">取消选择</li>
		</ul>
		<input type="hidden" id = "cf_load_pn" value="0"/>
		<input type="hidden" id = "cf_fnum" value="${user.friend_number}"/>
		<input type="hidden" id = "cf_pn" value="0"/>
		<div class = "cf_btn button cf_cancel" style = "margin-top:10px;margin-right:20px;" >取消</div>
		<div class = "cf_btn button cf_confirm" style = "margin-top:10px;margin-right:20px;">确定</div>
	</div>
</div>