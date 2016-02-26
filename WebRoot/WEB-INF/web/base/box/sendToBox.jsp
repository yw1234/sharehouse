<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id = "sendto_box">
	<div id = "st_top" onmousedown="move($(this).parent())">
		<span style = "float:left;width:100px;color:#e68303;font-family:'黑体',Arial;font-size:16px;line-height:31px;margin-left:20px;">分享圈选择:</span>
	</div>
	<div id = "st_main">
		<div style = "float:left;margin-left:18px;width:660px;height:0px;border:1px solid #e4e3e3;background:white;">
			<img id = "st_loading" src = "${pageContext.request.contextPath}/web/image/base/pic_notes_loading.gif" style = "display:none;"/>
		</div>
		<div id = "checkedcircle">
		</div>
		<div id = "checkedcirclename">
		</div>
		<div id = "st_circle_block">
			<div id = "st_circle">
			</div>
		</div>
		<div id = "st_op">
			<input type = "hidden" id = "st_type"/>
			<input type = "hidden" id = "st_location" value = "1"/>
			<div class = "st_btn st_cancel" style = "margin-right:20px;" onclick = "sendToBox.cancel()">取消</div>
			<div class = "st_btn st_confirm" onclick = "sendToBox.confirm()">确定</div>
		</div>
	</div>
</div>