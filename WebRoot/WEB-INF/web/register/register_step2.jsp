<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>    
    <title>圈易物-完善个人信息</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png"/>
	<script type="text/javascript">PATH = "${pageContext.request.contextPath}";</script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery.Jcrop.min.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/index/register_step2.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/index/register.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/jquery.Jcrop.css">
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = '${pageContext.request.contextPath}/lowVersion';
		</script>
	<![endif]-->
	<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css">
	<![endif]-->
  </head>
  <body>
    <div class = "main">
    		<s:if test = '#request.user.isNewUser == "1"'>
		    	<div id = "new_user_box">
		    		<div style = "float:left;width:100%;height:60px;border-radius:20px;">
		    			<span style = "float:left;line-height:60px;font-size:20px;color:#e68303;margin-left:20px;"><img src = "${pageContext.request.contextPath}/web/image/index/index_logo.png" style = "float:left;width:60px;height:60px;"/>注册成功</span>
		    		</div>
		    		<div style = "float:left;width:400px;min-height:440px;border-radius:0px;">
		    			<div style = "float:left;width:100%;">
		    				<img id = "user_avatar" src = "${user.head_ico_big}" style = "float:left;margin-left:40px;margin-top:10px;width:110px;"/>
		    				<span style = "float:left;text-align:left;width:200px;margin-left:20px;margin-top:12px;"><span style = "float:left;width:110px;color:#e68303;text-align:left;">${user.realname}</span></span>
		    				<span style = "float:left;text-align:left;width:200px;margin-left:20px;margin-top:20px;font-size:14px;text-align:left;"><a class = "button" style = "float:left;width:80px;height:22px;line-height:23px;border-radius:3px;" onclick = "avatarBox.show();">更换头像</a></span>
		    				<input type = "hidden" id = "u_school" value = "${user.school}"/>
		    			</div>
		    			<s:if test = '#request.user.registerByAPI == "1"'>
	    					<div style = "float:left;width:400px;margin-left:40px;margin-top:30px;">
	    						<span style = "float:left;width:100%;text-align:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;margin-top:1px;"/> <label style = "float:left;margin-left:10px;">为方便登录以及账号安全，请绑定常用邮箱</label></span>
	    						<div style = "float:left;width:100%;margin-top:20px;line-height:25px;"><span style = "float:left;font-size:16px;margin-left:25px;width:90px;text-align:left;">邮箱 : </span><input id = "email" type = "text" class = "long_text" style = "width:180px;" placeholder = "请输入常用邮箱"/></div>
	    						<div style = "float:left;width:100%;margin-top:20px;line-height:25px;"><span style = "float:left;font-size:16px;margin-left:25px;width:90px;text-align:left;">密码 : </span><input id = "password" type = "password" class = "long_text" style = "width:180px;" placeholder = "6-20位数字、英文"/></div>
	    					</div>
		    			</s:if>
		    			<div style = "float:left;width:400px;margin-left:40px;margin-top:30px;">
		    				<span style = "float:left;width:100%;text-align:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;margin-top:1px;"/> <label style = "float:left;margin-left:10px;">请完善个人信息，方便物品分享</label></span>
		    				<div style = "width:100%;float:left;margin-top:20px;font-size:16px;line-height:23px;"><span style = "float:left;margin-left:25px;font-size:16px;width:90px;text-align:left;">所在学校:</span><input id = "school" type = "text" class = "long_text" style = "float:left;cursor:pointer;" readonly onclick="schoolBox.show();" value = "${user.school}"/></div>
		    				<div style = "width:100%;float:left;margin-top:20px;font-size:16px;line-height:23px;"><span style = "float:left;margin-left:25px;font-size:16px;width:90px;text-align:left;">学历:</span><s:select id = "edu" cssStyle = "float:left;width:130px;" list = "#{'本科':'本科','硕士':'硕士','博士':'博士','博士后':'博士后'}"/></div>
		    				<div style = "width:100%;float:left;margin-top:20px;font-size:16px;line-height:23px;"><span style = "float:left;margin-left:25px;font-size:16px;width:90px;text-align:left;">院系&年级:</span><select id = "department" style = "float:left;width:130px;"></select><select id = "grade" name = "grade" style = "float:left;width:80px;"></select></div>
		    			</div>
		    		</div>
		    		<div style = "float:left;width:400px;margin-top:5px;">
		    			<div style = "float:left;width:100%;margin-top:10px;">
			    			<span style = "float:left;font-size:16px;">找出和你相同院系年级的好友吧~<label style = "color:#b1b1b1;margin-left:5px;">(需填写学校及院系)</label></span>
			    		</div>
		    			<div style = "float:left;width:100%;margin-top:10px;position:relative;">
						<div id = "new_sf_loading" style = "margin-top:80px;display:none;">
							<span style = "font-family:'黑体',Arial;color:#a1a1a1;">查找中</span><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style="margin-left:5px;"/>
							<input type="hidden" id = "new_department_id"/>
							<input type="hidden" id = "new_grade" value = "${user.hs_year}"/>
							<input type="hidden" id = "new_department" value = "${user.department}"/>
						</div>
						<div id = "new_show_user" style="position:absolute;width:100%;height:400px;margin-left:5px;left:0px;top:0px;overflow-y:auto;">
						</div>
					</div>
		    		</div>
		    		<div id = "new_complete" style = "float:left;width:100%;height:40px;margin-top:20px;">
		    			<div class = "button" id = "new_complete_btn" style = "width:120px;height:30px;cursor:pointer;line-height:31px;border-radius:4px;font-size:17px;letter-spacing:1px;" onclick = "newBox.complete();">完成</div>
		    		</div>
		    	</div>
		    <div id = "avatar_box_container"></div>
    		</s:if>
    </div>
    <s:include value="../base/box/schoolBox.jsp"/>
    <s:include value="../base/box/loadingBox.jsp"/>
     <s:include value="../base/box/errorBox.jsp"/>
    <s:include value="../base/foot/footDefault.jsp"/>

  </body>