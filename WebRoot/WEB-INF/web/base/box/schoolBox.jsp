<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id = "school_box" style = "position:absolute;width:600px;background:white;box-shadow:0px 0px 15px #333333;z-index:200;border:1px solid #b3b3b3;border-radius:5px;min-height:500px;display:none;">
	<div style = "float:left;width:100%;margin-top:5px;">
		<span style = "float:left;margin-left:20px;font-size:15px;font-family:'黑体',Arial;">请选择所在学校</span>
		<img  src = "${pageContext.request.contextPath}/web/image/base/del_button_1.png" style = "float:right;margin-top:2px;margin-right:10px;position:relative;cursor:pointer;" onclick = "schoolBox.hide();"/>
	</div>
	<ul style = "float:left;margin-top:5px;margin-bottom:0px;margin-left:15px;border:1px solid #e1e1e1;width:560px;padding:5px;">
		<li class = "sb_choose_province" style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">北京</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">上海</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">天津</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">重庆</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">黑龙江</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">吉林</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">辽宁</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">山东</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">山西</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">陕西</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">河北</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">河南</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">湖北</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">湖南</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">海南</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">江苏</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">江西</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">广东</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">广西</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">云南</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">贵州</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">四川</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">内蒙古</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">宁夏</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">甘肃</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">青海</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">西藏</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">新疆</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">安徽</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">浙江</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">福建</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">香港</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">澳门</li>
		<li class = "sb_choose_province"style = "float:left;list-style:none;font-size:13px;margin-left:10px;color:#e68303;cursor:pointer;">台湾</li>
	</ul>
	<ul id = "school_box_main" style = "float:left;margin-top:15px;margin-left:15px;padding:0px;">
	</ul>
	<div style = "float:left;width:100%;height:15px;">
	</div>
</div>