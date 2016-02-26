<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id = "acd_box" style = "position:absolute;width:400px;background:white;left:0px;top:100px;border-radius:10px;display:none;z-index:102">
	<div style = "float:left;border-radius:0px;width:400px;height:30px;line-height:30px;cursor:move;" onmousedown = "move($(this).parent())"><span style = "color:#e68303;font-size:16px;font-family:'黑体',Arial;">申请圈管理员</span>
	</div>
	<div style = "float:left;border-top:0px;width:296px;">
		<div style = "float:left;margin-left:20px;margin-top:5px;line-height:20px;">
			<img src = "web/image/base/arrow_left.png" style = "float:left;width:15px;"/>
			<span style = "float:left;margin-left:5px;font-size:15px;font-family:'黑体',Arial;">${c.name}</span>
		</div>
		<span style="float:left;margin-left:40px;margin-top:10px;font-size:15px;font-family:'黑体',Arial;">成为分享圈管理员,需要:</span>
		<ul style = "float:left;width:100%;">
			<li style = "float:left;margin-left:50px;font-size:15px;font-family:'黑体',Arial;color:#e68303;margin-top:5px;">• 能够经常登陆并管理分享圈</li>
			<li style = "float:left;margin-left:50px;font-size:15px;font-family:'黑体',Arial;color:#e68303;margin-top:5px;">• 及时删除圈中虚假不良信息</li>
			<li style = "float:left;margin-left:50px;font-size:15px;font-family:'黑体',Arial;color:#e68303;margin-top:5px;">• 踢除乱加的成员,解散乱建分享圈</li>
			<li style = "float:left;margin-left:50px;font-size:15px;font-family:'黑体',Arial;color:#e68303;margin-top:5px;">• 建立属于自己院系的下属分享圈</li>
			<li style = "float:left;margin-left:50px;font-size:15px;font-family:'黑体',Arial;color:#e68303;margin-top:5px;">• 记得邀请好友加入哦~</li>
		</ul>
		<textarea id = "acd_text" style = "float:left;margin-top:15px;margin-left:50px;width:300px;height:50px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;" TextDefault="填写一下身份验证信息吧~比如我是XXX院XX级的XXX~"></textarea>
		<div style = "float:left;margin-top:10px;width:100%;">
			<div style = "float:right;margin-right:25px;width:60px;height:20px;background:#e68303;line-height:20px;color:white;cursor:pointer;font-size:14px;" onclick="applyAdminBox.hide();">取消</div>
			<div style = "float:right;margin-right:25px;width:60px;height:20px;background:#e68303;line-height:20px;color:white;cursor:pointer;font-size:14px;" onclick="applyAdminBox.confirm({cid:${c.id}});">确定</div>
		</div>
		<div style = "float:left;width:100%;height:15px;"></div>
	</div>
</div>