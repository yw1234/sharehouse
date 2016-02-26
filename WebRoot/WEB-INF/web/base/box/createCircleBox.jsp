<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id = "createCircleBox" style="position:absolute;top:50px;width:516px;background:white;border:2px solid #e4e3e3;border-radius:10px;z-index:101;display:none;">
	<div class = "createCircleBox_nav" style = "float:left;width:100%;margin-top:10px;height:25px;">
		<span style = "float:left;width:450px;margin-left:20px;font-size:16px;font-family:'黑体',Arial;text-align:left;line-height:26px;overflow:hidden;">创建<span style = "color:#e68303;font-size:16px;font-family:'黑体',Arial;">${c.name}</span>下属分享圈</span>
		<a href = "javascript:void(0);" onclick = "createCircle.hide();"><img src = "web/image/base/del_button.png" style = "float:right;width:18px;margin-right:15px;" title="取消创建"/></a>
	</div>
	<div class = "createCircleBox_nav" style = "float:left;width:100%;margin-top:10px;height:25px;">
		<span style = "float:left;width:90px;margin-left:20px;font-size:16px;font-family:'黑体',Arial;text-align:left;line-height:26px;">分享圈名称:</span>
		<input type = "text" id = "cc_name" maxlength="15" style="float:left;margin-left:5px;line-height:22px;width:320px;height:22px;border-radius:6px;border:1px solid #e6e6e6;padding-left:10px;"/>
	</div>
	<div style = "float:left;width:100%;margin-top:10px;">
		<span style = "width:100%;float:left;margin-left:20px;font-size:16px;font-family:'黑体',Arial;text-align:left;line-height:26px;">上传圈图标(可跳过此项)</span>
		<div id = "cc_upload_block">
			<div id = "cc_uploadico">
				<div id = "cc_upload_img">
					<iframe id = "cc_upload_ifr" name="cc_upload_ifr" style="display:none;"></iframe>
					<form id = "cc_upload_form" action = "c_updatecircleico" method = "post" enctype="multipart/form-data"  target="cc_upload_ifr">
						<a id="cc_upload_button" class = "cc_upload_button" href = "javascript:void(0);"><input type="file" name = "file" id = "cc_upload_input"></a>
						<input type="hidden" value="cc" name="callBackType"/>
						<input type="hidden" value="circle-ico" name="uploadDir"/>
					</form>
				</div>
				<div id = "cc_upload_show_loading" style = "display:none;">
				</div>
				<div id = "cc_upload_show" style = "display:none;">
					<img id = "cc_show_img"/>
				</div>
			</div>
			 <div id = "cc_upload_preview_box_border">
			 	<div id="cc_upload_preview_box" class="cc_upload_preview_box">
			 		<img id="cc_crop_preview"/>
			 	</div> 
			 	<input type = "hidden" id = "imgPath"/>
			 	<input type = "hidden" id = "imgX"/>
			 	<input type = "hidden" id = "imgY"/>
			 	<input type = "hidden" id = "imgW"/>
			 	<input type = "hidden" id = "imgH"/>
			 	<input id = "cc_token" name="token" type="hidden" value="${sessionScope.cc_token}" />
			 </div>
		</div>
	</div>
	<div class = "createCircleBox_nav" style = "float:left;width:100%;margin-top:10px;height:25px;">
		<span style = "float:left;width:90px;margin-left:20px;font-size:16px;font-family:'黑体',Arial;text-align:left;line-height:26px;">圈属性:</span>
		<s:select id = "cc_type" list="#{'班级':'班级'}" cssStyle="float:left;width:80px;padding:3px;height:25px;cursor:pointer;"></s:select>
	</div>
	<div class = "createCircleBox_nav" style = "float:left;width:100%;margin-top:10px;">
		<span style = "float:left;width:90px;margin-left:20px;font-size:16px;font-family:'黑体',Arial;text-align:left;line-height:26px;">分享圈简介:</span>
		<textarea style="float:left;margin-left:5px;width:270px;height:50px;resize:none;border-radius:5px;border:1px solid #e6e6e6;padding:5px;margin-top:5px;" id = "cc_notice"></textarea>
		<div class = "send_btn" id = "create_btn" style = "float:left;width:80px;height:65px;margin-left:10px;margin-top:5px;background:#e68303;cursor:pointer;line-height:65px;border-radius:10px;" onclick = "createCircle.create();"><span style = "font-size:19px;font-weight:bold;color:white;">创建</span></div>
	</div>
	<div style = "float:left;width:100%;height:20px;"></div>
</div>