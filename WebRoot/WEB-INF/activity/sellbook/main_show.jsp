<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <div id = "sellbook_nav" class = "sb_bar">
   		<a href = "${pageContext.request.contextPath}/activity/sell_book/show"><div id = "sellbook_nav_show" class = "sb_bar_btn sb_bar_on" style = "margin-left:30px;">图书展示</div></a>
   		<img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_bar_split.jpg" style = "float:left;"/>
   		<a href = "${pageContext.request.contextPath}/activity/sell_book/book"><div id = "sellbook_nav_booking" class = "sb_bar_btn">我的订单</div></a>
	   	<div class = "sellbook_nav_select" style = "float:right;line-height:30px;margin-top:5px;margin-right:30px;">
			<input id = "selected_name" type = "text" class = "sb_text" style = "float:left;width:180px;height:21px;margin-top:2px;margin-left:10px;" TextDefault="请输入要查找的书名"/>
			<a class = "sb_button" onclick = "books.changeType({searchType:'key'});" style = "float:left;width:60px;height:21px;line-height:22px;margin-top:3px;border-radius:3px;margin-left:10px;">查找</a>
		</div>
   </div>
  	<div id = "sellbook_nav_select">
  		<div class = "sb_nav_select_block">
  			<span>图书类别:</span>
  			<s:select id = "selected_type" cssClass = "sb_nav_selected" list="#{'':'不限','教科书':'教科书','教学辅助':'教学辅助','考研书籍':'考研书籍','英语相关':'英语相关','其他':'其他'}"></s:select>
  		</div>
  		<div class = "sb_nav_select_block">
  			<span>适用范围:</span>
  			<s:select id = "selected_scope" cssClass = "sb_nav_selected" list="#{'':'不限','数学':'数学','思想政治':'思想政治','材料科学':'材料科学','电子信息':'电子信息','自动化科学':'自动化科学','能源与动力':'能源与动力','航空航天':'航空航天','计算机与软件工程':'计算机与软件工程','机械工程':'机械工程','经济管理学':'经济管理学','生物与医学':'生物与医学','外语学':'外语学','交通科学':'交通科学','可靠性科学':'可靠性科学','仪器光电':'仪器光电','物理学':'物理学','法学':'法学','化学':'化学','人文社科':'人文社科','其他':'其他'}"></s:select>
  		</div>
  		<div class = "sb_nav_select_block">
  			<span>价格区间:</span>
  			<s:select id = "selected_priceLevel" cssClass = "sb_nav_selected" cssStyle = "width:80px;" list = "#{'':'不限','0-5':'5元以下','5-10':'5-10元','10-20':'10-20元','20-1000':'20元以上'}"/>
  		</div>
  		<div class = "sb_nav_select_block">
  			<span>库存:</span>
  			<s:select id = "selected_stock" cssClass = "sb_nav_selected" cssStyle = "width:70px;" list = "#{'0':'有库存','':'不限'}"/>
  		</div>
  </div>
  <div id = "sellbook_show">
	  <input id = "pageno" type = "hidden" value = "0"/>
	  	<div id = "f_loading" style = 'float:left;width:100%;margin-top:40px;'><span>图书信息获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
  		<div id = 'sellbook_notes'>
  		</div>
  </div>
	<script type="text/javascript">
		books.get({pn:0});
	</script>

<!--详情模板  -->
<div id = "book_details">
	<div id = "book_details_top">
		<span>图书名称:</span><span id = "bd_name" style = "color:#89b403;"></span>
		<a href = "javascript:void(0);" style = "float:right;margin-right:20px;" onclick = "bookDetails.hide()"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "width:30px;"/></a>
	</div>
	<div id = "book_details_main">
		<div id = "book_details_info">
			<ul>
				<li><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_price.png"><span>单价 :</span><span class = "details_info_list" id = "bd_price" style = "color:#89b403;font-size:30px;"></span></li>
				<li><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_type.png"><span>类型 :</span><span class = "details_info_list" id = "bd_type"></span></li>
				<li><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_scope.png"><span>范围 :</span><span class = "details_info_list" id = "bd_scope"></span></li>
				<li><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_stock.png"><span>库存 :</span><span class = "details_info_list" id = "bd_stock"></span></li>
				<li><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_looktimes.png"><span>浏览次数 :</span><span class = "details_info_list" id = "bd_looktimes"></span></li>
				<li><span>已被预定</span><span id = "bd_bookTimes" style = "margin-top:20px;line-height:26px;border-radius:100px;height:25px;padding-left:7px;padding-right:7px;background:#89b403;color:white;"></span><span>次</span></li>
				<li><a class = 'sb_button' style = "float:left;width:120px;height:40px;line-height:41px;" onclick = "bookingBox.show({bookid:$('#booking_bookid').val(),stock:$('#bd_stock').html(),bookName:$('#bd_name').html(),price:$('#bd_price').html()});">立刻预定</a></li>
			</ul>
			<div style = "float:left;margin-left:30px;width:310px;font-size:20px;line-height:30px;">
				<span style = "float:left;text-align:left;"><label style = "color:#83a841;font-weight:bold;">备注</label> : <span id = "bd_remark" ></span></span>
			</div>
		</div>
		<div id = 'book_detail_img'>
			<div style = "float:left;margin-left:20px;height:40px;line-height:40px;font-size:18px;color:white"><label style = "float:left;">共有</label><span id = "bd_img_num" style = "float:left;margin-left:4px;margin-top:7px;padding-left:6px;padding-right:6px;line-height:26px;height:25px;background:white;font-size:20px;color:#89b043;border-radius:100px;"></span><label style = "float:left;margin-left:4px;">张实物图</label></div>
			<div id = 'book_detail_img_show'>
				<div id= "showImg_backward" style = "z-index:160;left:0px;top:0px;height:530px;width:280px;position:absolute;background:url(${pageContext.request.contextPath}/web/image/base/place_holder_tran.png);"><div style = "position:absolute;top:0px;left:0px;width:280px;height:530px;background:url(${pageContext.request.contextPath}/activity/sellbook/image/sb_pic_left.png) left center no-repeat;cursor:pointer;"></div></div>
				<div id= "showImg_forward" style = "z-index:160;right:0px;top:0px;height:530px;width:280px;position:absolute;background:url(${pageContext.request.contextPath}/web/image/base/place_holder_tran.png);"><div style = "position:absolute;top:0px;left:0px;width:280px;height:530px;background:url(${pageContext.request.contextPath}/activity/sellbook/image/sb_pic_right.png) right center no-repeat;cursor:pointer;"></div></div>
				<ul id= "showImg_block">
				</ul>
				<input type="hidden" id="showImg_num" value = "1"/>
				<input type="hidden" id="showImg_total"/>
			</div>
		</div>
	</div>
</div>
<div id = "bookDetails_loading" style = "position:absolute;width:320px;height:50px;background:white;border:2px solid #e4e3e3;border-radius:10px;z-index:105;line-height:50px;display:none;">
	<span>图书信息读取中</span><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" />
</div>
	
<div id = "booking_box">
	<div style = "float:left;width:100%;height:35px;font-size:20px;line-height:38px;">
		<span id = "booking_bookName" style = "float:left;margin-left:25px;color:#89b043;font-weight:bold"></span>
		<a href = "javascript:void:(0)" onclick = "bookingBox.cancel()"> <img src = "${pageContext.request.contextPath}/web/image/base/del_button.png"  style = "float:right;width:20px;height:20px;line-height:21px;margin-top:8px;margin-right:20px;border-radius:3px;"/></a>
		<div style = "float:left;width:440px;margin-left:20px;height:1px;background:#b1b1b1;"></div>
	</div>
	<div style = "float:left;width:100%;height:35px;margin-top:10px;"><span id = "booking_title" style = "float:left;margin-left:30px;line-height:35px;font-size:17px;color:#89b043;font-weight:bold">预订信息</span></div>
	<div style = "float:left;width:100%;">
		<div style = "float:left;width:100%;line-height:22px;text-align:left;margin-top:5px;">
			<span style = "float:left;width:80px;margin-left:30px;text-align:left;">总价 :</span>
			<span style = "float:left;margin-left:10px;line-height:22px">￥ <label id = "booking_price" style = "color:#89b043;"></label></span>
		</div>
		<div style = "float:left;width:100%;line-height:22px;text-align:left;margin-top:10px;">
			<span style = "float:left;width:80px;margin-left:30px;text-align:left;">预订数量 :</span>
			<span style = "float:left;margin-left:10px;"><a href="javascript:void(0);" style="float:left;width:20px;font-weight:bold;color:#89b043;font-size:20px;cursor:pointer;" onclick = "books.decrease();">-</a><span style = "float:left;margin-left:6px;margin-right:6px;" id = "booking_count">1</span><a href="javascript:void(0);" style="float:left;width:20px;font-weight:bold;color:#89b043;font-size:20px;cursor:pointer;" onclick = "books.increase();">+</a></span>
			<span style = "float:left;margin-left:20px;line-height:22px">( 库存: <label id = "booking_stock" style = "color:#89b043;"></label> )</span>
		</div>
	</div>
	<div style = "float:left;width:100%;height:35px;margin-top:15px;"><span id = "booking_title" style = "float:left;margin-left:30px;line-height:35px;font-size:17px;color:#89b043;font-weight:bold">联系方式</span></div>
	<div style = "float:left;width:100%;line-height:35px;">
		<span style = "float:left;margin-left:30px;">姓名:</span>
		<input id = "booking_userName" type = "text" class = "sba_text" style = "float:left;width:100px;margin-left:10px;margin-top:3px;"  value = "${userInfo.realname}"/>
		<span style = "float:left;margin-left:30px;">手机:</span>
		<input id = "booking_userPhone" type = "text" class = "sba_text" style = "float:left;margin-left:10px;margin-top:3px;" onpaste = "return false;" onkeydown = "return numbervalidate();" maxlength = "11" value = "${userInfo.phone}"/>
	</div>
	<div style = "float:left;width:100%;margin-top:15px;">
		<textarea id = "booking_text" style = "float:left;margin-top:5px;margin-left:30px;width:290px;height:40px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;" onkeyup="textLimit.getLength({id:'booking_textlimit',target_text:$(this)})" TextDefault = "有什么要补充的吗~"></textarea>
		<div style="float:left;width:50px;margin-top:5px;">
			<div class = "facelist face-switch" id = "booking_facelist" onclick = "getFaceList('booking_facelist',$('#booking_text'),'booking_textlimit');"><img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/></div>
			<div id = "booking_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:10px;">0/140</div>
		</div>
		<a href = "javascript:void:(0)" id = "booking_submit" class = "sb_button" style = "float:left;width:70px;height:50px;line-height:51px;margin-top:5px;border-radius:3px;">提交</a>
	</div>
	<input type = "hidden" id = "booking_single_price"/>
	<div style = "float:left;width:100%;height:15px;"></div>
</div>
<input type = "hidden" id = "booking_bookid"/>
<input type = "hidden" id = "booking_box_locking" value = "0"/>