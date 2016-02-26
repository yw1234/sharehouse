<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <div id = "sellbook_nav" class = "sb_bar">
  		<a href = "${pageContext.request.contextPath}/activity/sell_book/show"><div id = "sellbook_nav_show" class = "sb_bar_btn" style = "margin-left:30px;">图书展示</div></a>
  		<img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_bar_split.jpg" style = "float:left;"/>
  		<a href = "${pageContext.request.contextPath}/activity/sell_book/book"><div id = "sellbook_nav_booking" class = "sb_bar_btn sb_bar_on">我的订单</div></a>
  </div>
 <div style = "width:920px;">
		<div style = "float:left;width:100%;line-height:30px;margin-top:10px;">
			<span style = "float:left;">订单筛选 : </span>
			<s:select cssClass ="booking_selected" id = "status" name = "status" cssStyle = "float:left;width:120px;margin-top:5px;margin-left:10px;" list = "#{'':'全部订单','0':'只看未完成的订单','1':'只看已完成的订单'}" />
			<span style = "float:left;margin-left:40px;">共计 : ￥<label id = "total_price" style = "color:#89b043"></label></span>
		</div>
		<table id = "sba_booking_tb">
			<tr style = "background:#89b403;color:white;">
				<td class = "b_tb_id">订单号</td>
				<td class = "b_tb_name">预定图书名称</td>
				<td class = "b_tb_tiny">缩略图</td>
				<td class = "b_tb_count">数量</td>
				<td class = "b_tb_price">总价</td>
				<td class = "b_tb_complete">订单状态</td>
			</tr>
			<s:iterator id = "bl" value = "#request.bookingList" status = "bl_status">
				<tr id = "booking_tr_${bl.id}">
					<td class = "b_tb_id">${bl.id}</td>
					<td class = "b_tb_name"><a href = "javascript:void(0);" style="color:#89b043" onclick = "bookDetails.init({id:${bl.bookid}});">${bl.bookName}</a></td>
					<td class = "b_tb_tiny"><img src = "${bl.tinyImage}"/></td>
					<td class = "b_tb_count">${bl.count}</td>
					<td class = "b_tb_price">￥<label class = "single_price">${bl.totalPrice}</label></td>
					<td class = "b_tb_complete" id = "b_tb_complete_${bl.id}">
						<s:if test = '#bl.isComplete == "0"'>
							<span>未完成</span>
							<a href = "javascript:void(0);" class = "sb_button" style = "width:60px;height:20px;line-height:21px;" onclick = "confirmBox.show({func:function(){books.cancelBooking({id:${bl.id},target:$('#booking_tr_${bl.id}')});},msg:'确认取消预定${bl.bookName}吗'});">取消订单</a>
						</s:if>
						<s:else>
							已完成
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
<div style = "float:left;width:100%;height:30px;"></div>
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
			</ul>
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