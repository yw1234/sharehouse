<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test = "#request.info.id == #request.user.id">
	<div id = "info_base">
		<input type = "hidden" id = "preloader_info">
		<div id = "info_saveinfo" class = "button" style = "float:right;width:80px;height:25px;line-height:26px;">保存修改</div>
		<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">基本资料</span></div>
		<div style = "float:left;margin-left:10px;">
			<div class = "info_list">
				<span>姓名:</span><input class = "info_val" name = "realname" id = "info_name" value = "${info.realname}" TextDefault = "请输入姓名" maxlength="6"/>
				<input class = "info_validate" id = "validate_info_name" type = "hidden" value = "1"/>
				<input id = "info_name_prev" type = "hidden" value = "${info.realname}"/>
			</div>
			<div class = "info_list">
				<span>头像:</span><a href = "javascript:void(0);"><span style= "color:#e68303;" id = "changeico_btn">修改头像</span></a>
				<div id = "info_img_loading" style = "display:none;"><span style = "float:left;font-size:16px;margin-left:35px;width:80px;">处理ing</span><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "float:left;margin-top:8px;"/></div>
			</div>
			<div id = "info_upload_block">
				<div id = "info_uploadico">
					<div id = "info_upload_img">
						<iframe id = "info_upload_ifr" name="info_upload_ifr" style="display:none;"></iframe>
						<form id = "info_upload_form" action = "${pageContext.request.contextPath}/upload/headIco" method = "post" enctype="multipart/form-data"  target="info_upload_ifr">
							<a id="info_upload_button" class = "info_upload_button" href = "javascript:void(0);"><input type="file" name = "file" id = "info_upload_input"></a>
							<input type="hidden" value="uploadAvatarCallBack" name="callBackFunc"/>
							<input type="hidden" value="avatar" name="uploadDir"/>
						</form>
					</div>
					<div id = "info_upload_show_loading" style = "display:none;">
					</div>
					<div id = "info_upload_show" style = "display:none;">
						<img id = "info_show_img"/>
					</div>
				</div>
				<form id = "uploadcutimage" action = "${pageContext.request.contextPath}/upload/cutimage" method = "post">
					 <div id = "info_upload_preview_box_border">
					 	<div id="info_upload_preview_box" class="info_upload_preview_box">
					 		<img id="info_crop_preview"/>
					 	</div> 
					 	<input type = "hidden" name = "p" id = "imgPath"/>
					 	<input type = "hidden" name = "x" id = "imgX"/>
					 	<input type = "hidden" name = "y" id = "imgY"/>
					 	<input type = "hidden" name = "w" id = "imgW"/>
					 	<input type = "hidden" name = "h" id = "imgH"/>
					 	<div class = "button"id = "comfirm_cut">确定</div>
					 </div>
				 </form>
			</div>
			<div class = "info_list">
				<span>性别:</span><span style = "float:left;">${info.sex}</span>
			</div>
			<div class = "info_list">
				<span>所在学校:</span><input name = "school" id = "info_school" class = "info_val" value = "${info.school}" readonly = "readonly"/>
			</div>
			<div class = "info_list">
				<span>院系及年级:</span>
				<input id = "u_department" value = "${info.department}" type = "hidden"/>
				<input id = "u_grade" value = "${info.hs_year}" type = "hidden"/>
				<select class = "info_select" id = "info_department" name = "department" style = "width:180px;"></select><s:select cssClass = "info_select" id = "info_edu" name = "edu" cssStyle = "margin-left:10px;" list = "#{'本科':'本科','硕士':'硕士','博士':'博士','博士后':'博士后'}" value = "#request.info.educational"/><select class = "info_select" id = "info_grade" name = "grade" style = "margin-left:10px;"></select>
			</div>
			<div class = "info_list">
				<span>所在地:</span>
				<input type = "hidden" value = "${info.province}-${info.city}" id = "info_place"/>
				<select class = "info_select province_select" id = "info_province" name = "province"></select><select class = "info_select" id = "info_city" name = "city" style = "margin-left:10px;"></select>
			</div>
		</div>
	</div>
	<div id = "info_more" style = "margin-top:30px;">
		<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">详细资料</span></div>
		<div style = "float:left;margin-left:10px;">
			<div class = "info_list more">
				<span>生日:</span>
				<input type = "hidden" value = "${info.birthday}" id = "info_birthday"/>
				<select class = "info_select" id = "info_year" name = "year"></select><select class = "info_select" id = "info_month" name = "month" style = "margin-left:10px;"></select><select class = "info_select" id = "info_day" name = "day" style = "margin-left:10px;"></select>
			</div>
			<div class = "info_list more">
				<span>星座:</span>
				<input type = "hidden" id = "info_constellation_input" value = "${info.constellation}" />
				<select class = "info_select" id = "info_constellation" name = "constellation" ></select>
			</div>
			<div class = "info_list more">
				<span>生肖:</span>
				<s:select cssClass = "info_select" id = "info_xx" name = "xx" list="#{0:'请选择',1:'鼠',2:'牛',3:'虎',4:'兔',5:'龙',6:'蛇',7:'马',8:'羊',9:'猴',10:'鸡',11:'狗',12:'猪'}" value="#request.info.sx"></s:select>
				<span style = "margin-left:20px;width:200px;color:#e68303;">(若不正确请自行改正)</span>
			</div>
			<div class = "info_list more">
				<span>婚恋:</span>
				<s:select cssClass = "info_select" id = "info_marry" name = "marry" list="#{0:'请选择',1:'单身',2:'非单',3:'不告你'}" value="#request.info.marry"></s:select>
			</div>
			<div class = "info_list more">
				<span>血型:</span>
				<s:select cssClass = "info_select" id = "info_blood" name = "blood" list="#{0:'请选择',1:'O',2:'A',3:'B',4:'AB'}" value="#request.info.blood"></s:select>
			</div>
			<div class = "info_list more">
				<span>家乡:</span>
				<input type = "hidden" value = "${info.home}" id = "info_home"/>
				<select class = "info_select province_select" id = "inho_province"></select><select class = "info_select" id = "inho_city" style = "margin-left:10px;"></select><select class = "info_select" id = "inho_area" style = "margin-left:10px;"></select>
			</div>
			<div class = "info_list more" style = "height:100px;">
				<span>其他:</span>
				<textarea id = "info_moretext" style = "float:left;width:300px;height:50px;overflow:auto;font-size:14px;border:1px solid #e6e6e6;resize:none;border-radius:5px;" TextDefault = "还补充些什么吗?" >${info.introduce}</textarea>
			</div>
		</div>
	</div>
</s:if>
<s:else>
	<div id = "info_base">
		<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;margin-left:5px;">基本资料</span></div>
		<div style = "float:left;margin-left:10px;">
			<div class = "info_list">
				<span>姓名:</span><span style = "width:400px;color:#e68303;">${info.realname}</span>
			</div>
			<div class = "info_list">
				<span>性别:</span><span style = "width:400px;color:#e68303;">${info.sex}</span>
			</div>
			<div class = "info_list">
				<span>所在学校:</span><span style = "width:400px;color:#e68303;">${info.school}</span>
			</div>
			<div class = "info_list">
				<span>院系:</span><span style = "width:400px;color:#e68303;">${info.department}</span>
			</div>
			<div class = "info_list">
				<span>年级:</span><span style = "width:400px;color:#e68303;">${info.hs_year}</span>
			</div>
			<s:if test = '#request.info.province != null && #request.info.city != null && #request.info.province != "" && #request.info.city != ""'>
				<div class = "info_list">
					<span>所在地:</span>
					<span style = "width:400px;color:#e68303;">${info.province}-${info.city}</span>
				</div>
			</s:if>
		</div>
	</div>
	<div id = "info_more" style = "margin-top:40px;">
		<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">更多信息</span></div>
		<div style = "float:left;margin-left:10px;">
			<s:if test = "#request.info.birthday != null && #request.info.birthday !=''">
				<div class = "info_list more">
					<span>生日:</span>
					<span style = "width:400px;color:#e68303;">${info.birthday}</span>
				</div>
			</s:if>
			<s:if test = "#request.info.constellation != null && #request.info.constellation !=''">
				<div class = "info_list more">
					<span>星座:</span>
					<span style = "width:400px;color:#e68303;">${info.constellation}</span>
				</div>
			</s:if>
			<s:if test="#request.info.xx != null && #request.info.xx !=0">
				<div class = "info_list more">
					<span>生肖:</span>
					<span style = "width:400px;color:#e68303;">
						<s:if test="#request.info.xx==1">鼠</s:if>
						<s:elseif test="#request.info.xx==2">牛</s:elseif>
						<s:elseif test="#request.info.xx==3">虎</s:elseif>
						<s:elseif test="#request.info.xx==4">兔</s:elseif>
						<s:elseif test="#request.info.xx==5">龙</s:elseif>
						<s:elseif test="#request.info.xx==6">蛇</s:elseif>
						<s:elseif test="#request.info.xx==7">马</s:elseif>
						<s:elseif test="#request.info.xx==8">羊</s:elseif>
						<s:elseif test="#request.info.xx==9">猴</s:elseif>
						<s:elseif test="#request.info.xx==10">鸡</s:elseif>
						<s:elseif test="#request.info.xx==11">狗</s:elseif>
						<s:elseif test="#request.info.xx==12">猪</s:elseif>
					</span>
				</div>
			</s:if>
			<s:if test="#request.info.marry != null && #request.info.marry !=0">
				<div class = "info_list more">
					<span>婚恋:</span>
					<span style = "width:400px;color:#e68303;">
					<s:if test="#request.info.marry==1">单身</s:if>
					<s:elseif test="#request.info.marry==2">非单</s:elseif>
					<s:elseif test="#request.info.marry==3">不告你</s:elseif>
					</span>
				</div>
			</s:if>
			<s:if test="#request.info.marry != null && #request.info.marry !=0">
				<div class = "info_list more">
					<span>血型:</span>
					<span style = "width:400px;color:#e68303;">
						<s:if test="#request.info.blood==1">O</s:if>
						<s:elseif test="#request.info.blood==2">A</s:elseif>
						<s:elseif test="#request.info.blood==3">B</s:elseif>
						<s:elseif test="#request.info.blood==4">AB</s:elseif>
					</span>
				</div>
			</s:if>
			<s:if test="#request.info.home != null && #request.info.home !=''">
				<div class = "info_list more">
					<span>家乡:</span>
					<span style = "width:400px;color:#e68303;">${info.home}</span>
				</div>
			</s:if>
			<s:if test="#request.info.introduce != null && #request.info.introduce !=''">
				<div class = "info_list more" style = "height:100px;">
					<span>其他:</span>
					<span style = "width:400px;color:#e68303;">${info.introduce}</span>
				</div>
			</s:if>
		</div>
	</div>
</s:else>