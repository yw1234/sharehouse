<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/base/mtxx.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/base/jquery.uploadify.min.js"></script>
<div style = "float:left;margin-left:50px;margin-top:30px;line-height:28px;letter-spacing:2px;font-size:20px;">
	<span style = "float:left;">有什么闲置物品? 立刻</span><div class = "button" style = "float:left;width:60px;height:25px;font-size:17px;border-radius:3px;line-height:25px;" onclick="sendBox.show({target:$('#sendShareBox'),type:'goods'})">发布</div><span style="float:left;">上来吧!</span>
</div>
<div id = "sendBlock" style = "z-index:101;display:none;">
	<div class = "sendBox_main" id = "sendShareBox">
		<div class = "sendBox_nav" style = "height:25px;">
			<span style = "line-height:26px;">闲置着什么:</span>
			<input type = "text" class = "send_obj" id = "goods" maxlength="30"/>
			<span style="float:left;margin-left:5px;line-height:25px;color:#b1b1b1;font-size:15px;">(* 必填)</span>
			<a href = "javascript:void(0);" onclick = "sendBox.hide('share');"><img src = "${pageContext.request.contextPath}/web/image/base/del_button_1.png" style = "float:right;width:18px;margin-right:15px;margin-top:3px;" title="取消发布"/></a>
		</div>
		<div class = "sendBox_nav">
			<span>有图有真相</span>
			<span style = "float:left;color:#b1b1b1;font-size:13px;line-height:22px;">(可上传6张,最大为5M)</span><span class = "cancel">取消</span>
		</div>
		<div class = "pic_block">
			<div id = "idleUploadBox" style = "float:left;width:150px;height:150px;background:white;border:0px;border-radius:5px;">
				<input type = "file" name = "file" id = "idle_upload"/>
				<!-- 
				<div class = "grey_button" style = "float:left;width:130px;height:30px;line-height:31px;font-size:16px;margin-left:6px;margin-top:25px;cursor:pointer;position:relative;">
					直接上传
					<iframe id = "idle_upload_ifr" name="idle_upload_ifr" style="display:none;"></iframe>
					<input type="file" name = "file" id = "idle_upload" upload_btn>
					<form id = "idle_upload_form" method = "post" enctype="multipart/form-data"  target="idle_upload_ifr">
						<input type="file" name = "file" id = "idle_upload_input">
						<input type="hidden" id = "callBackFunc" name="callBackFunc"/>
						<input type="hidden" id = "uploadDir" name="uploadDir"/>
						<input type="hidden" id = "uploadType" name="uploadType"/>
					</form>
				</div> -->
				<div class = "grey_button" style = "float:left;width:130px;height:30px;line-height:31px;font-size:16px;margin-left:6px;margin-top:25px;" onclick = "mtxx.show();"><img src = "${pageContext.request.contextPath}/web/image/base/icon/mtxx_logo.png" style = "width:28px;vertical-align:top;"/>优化一下</div>
			</div>
		</div>
		<div class = "sendBox_nav" style = "height:25px;margin-top:15px;margin-left:5px;">
			<span style = "float:left;">希望怎样处理呢:</span>
			<select id= "deal_type" style = "float:left;margin-left:15px;width:150px;" onchange = "changeSendType(this.value);" >
				<option id = "change_sell_btn" class = "change_btn" style = "cursor:pointer;font-size:15px;color:#e68303;" value = "mainInfo_sell">卖了吧</option>
				<option id = "change_lend_btn" class = "change_btn" style = "cursor:pointer;font-size:15px;color:#e68303;" value = "mainInfo_lend">需要的话可以借走</option> 
				<option id = "change_gift_btn" class = "change_btn" style = "cursor:pointer;font-size:15px;color:#e68303;" value = "mainInfo_gift">送给别人</option>
			</select>
			<span style = "font-size:13px;color:#b1b1b1;">(以下信息非必须填写)</span>
		</div>
		<div id = "sendBox_mainInfo" style = "float:left;">
			<div id = "mainInfo_sell" class = "mainInfo" style = "float:left;width:100%;">
				<div class = "info_text" style = "margin-top:10px;"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/price.png"/></div><div class = "tab">价格:</div><input type = "text" maxlength="10" class = "mi_text price" id= "sell_price" onpaste = "return false;" onkeydown = "return numbervalidate();"/><span style="float:left;margin-left:10px;line-height:25px;color:#b1b1b1;font-size:13px;">(只可输入数字,参考:<a href="javascript:void(0);" style = "color:#e68303;" onclick = "linkTo.wbtc();">58</a>,<a href="javascript:void(0);" style = "color:#e68303;" onclick = "linkTo.ganji();">赶集</a>,<a href="javascript:void(0);" target = "_blank" style = "color:#e68303;" onclick = "linkTo.tzj();">跳蚤街</a>)</span></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/olddegree.png"/></div><div class = "tab">新旧程度:</div><s:select cssClass = "old_degree" id = "sell_old_degree" list = "#{'':'请选择','全新':'全新','九九成':'九九成','九五成':'九五成','九成':'九成','八成':'八成','七成':'七成','较旧':'较旧'}"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/bargain.png"/></div><div class = "tab">是否可刀:</div><s:select cssClass = "bargain" id = "sell_bargain" list = "#{'':'请选择','可刀':'可刀','小刀':'小刀','大刀':'大刀','不刀':'不刀'}"/><span style="float:left;margin-left:10px;line-height:23px;color:#b1b1b1;font-size:13px;">(就是议价的意思~)</span></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/goodslink.png"/></div><div class = "tab">报价链接:</div><input type = "text" maxlength="500" class = "long_text" id= "sell_link" style="width:150px;"/><span style="float:left;margin-left:10px;line-height:25px;color:#b1b1b1;font-size:13px;">(</span><a href="javascript:void(0);" onclick = "linkTo.jd();"><span class="sell_web_link">京东</span></a><a href="javascript:void(0)" onclick = "linkTo.amazon()"><span class="sell_web_link">亚马逊</span></a><a href="javascript:void(0)" onclick = "linkTo.taobao();"><span class="sell_web_link">淘宝</span></a><a href="javascript:void(0)" onclick = "linkTo.zol();" target = "_blank"><span class="sell_web_link">中关村</span></a><span style="float:left;margin-left:3px;line-height:25px;color:#b1b1b1;font-size:13px;">)</span></div>
			</div>
			<div id = "mainInfo_lend" class = "mainInfo" style = "float:left;width:100%;display:none;">
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/olddegree.png"/></div><div class = "tab">新旧程度:</div><s:select cssClass = "old_degree" id = "lend_old_degree" list = "#{'':'请选择','全新':'全新','九九成':'九九成','九五成':'九五成','九成':'九成','八成':'八成','七成':'七成','较旧':'较旧'}"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/borrow_time.png"/></div><div class = "tab">借出时长:</div><s:select cssClass = "lend_time" id = "lend_time" list = "#{'面议':'面议','一天':'一天','一周':'一周','一个月':'一个月','半年':'半年','一年及以上':'一年及以上'}"/></div>
			</div>
			<div id = "mainInfo_gift" class = "mainInfo" style = "float:left;width:100%;display:none;">
				<div class = "info_text" style = "margin-top:10px;"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/price.png"/></div><div class = "tab">估价:</div><input type = "text" maxlength="10" class = "mi_text price" id= "gift_price" onpaste = "return false;" onkeydown = "return numbervalidate();"/><span style="float:left;margin-left:10px;line-height:25px;color:#b1b1b1;font-size:14px;">(只可输入数字)</span></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/olddegree.png"/></div><div class = "tab">新旧程度:</div><s:select cssClass = "old_degree" id = "gift_old_degree" list = "#{'':'请选择','全新':'全新','九九成':'九九成','九五成':'九五成','九成':'九成','八成':'八成','七成':'七成','较旧':'较旧'}"/></div>
			</div>
			<div id = "mainInfo_back" class = "mainInfo" style = "float:left;width:100%;display:none;">
				<div class = "info_text" style = "margin-top:10px;"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/base/icon/place_icon.png"/></div><div class = "tab">得到地点:</div><input type = "text" maxlength="500" class = "long_text" id= "back_place" style="width:200px;"/></div>
			</div>
			<div id = "mainInfo_common" style = "float:left;width:100%;">
    		 	<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/boughtprice.png"/></div><div class = "tab">物品类型:</div><div style = "float:left;height:25px;font-weight:bold;font-size:14px;line-height:23px;"><select id= "goodstype_1"></select><select id= "goodstype_2"></select></div></div>
    		 	<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/web/image/home/contactperson.png"/></div><div class = "tab">联系方式:</div><div style = "float:left;height:25px;line-height:23px;"><input type = "radio" style = "float:left;line-height:25px;" name="goods_show_pri" id = "goods_show_pri_notshow" checked/><span style="float:left;font-size:14px;font-family:'黑体',Arial;">不公开</span><input type = "radio" style = "float:left;line-height:25px;margin-left:10px;" name="goods_show_pri" id = "goods_show_pri_show"/><span style="float:left;font-size:14px;font-family:'黑体',Arial;">公开</span><span style = "margin-left:10px;font-weight:400;font-size:13px;">(可到<a href = "${pageContext.request.contextPath}/user?page=privacy&nav=common&id=${user.id}" target="_blank"style = "color:#e68303;">隐私设置</a>页面设置可见人)</span></div></div>
				<!--  <div class = "info_text"><div class = "info_img"><img src = "web/image/home/sendto.png" style = "width:20px;height:20px;"/></div><div class = "tab">发布到:</div><span id = "goods_selected_circle" class="select_circle" style = "float:left;font-size:14px;line-height:23px;color:#e68303;cursor:pointer;" onclick = "sendToBox.init({type:'goods'})"><span style = "float:left;width:50px;height:20px;"></span></span></div>-->
				<div class = "info_text"><div class = "tab" style = "float:left;"><span style = "float:left;font-size:16px;font-family:'黑体',Arial;">补充信息:</span></div>
					<div class = "at-icon" target = "goods_more_info_text">@</div>
					<div class = "facelist face-switch" id = "goods_facelist" onclick = "getFaceList('goods_facelist',$('#goods_more_info_text'),'goods_textlimit');"><img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/></div>
					<div id = "goods_textlimit" style = "float:left;margin-left:30px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:2px;">0/140</div>
				</div>
				<div style = "float:left;width:100%">
					<textarea at-field class = "more_info_text" id = "goods_more_info_text" TextDefault="描述一下,轻松分享~" onkeyup="textLimit.getLength({id:'goods_textlimit',target_text:$(this)})"></textarea>
    		 		<div class = "button" id = "send_goods_btn" style = "float:left;width:85px;height:65px;margin-left:10px;margin-top:5px;line-height:65px;border-radius:5px;"><span style = "font-size:19px;color:white;">发布</span></div>
				</div>
			</div>
		</div>
		<div style = "float:left;width:100%;height:10px;"></div>
	</div>
</div>