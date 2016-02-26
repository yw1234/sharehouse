<!DOCTYPE html><%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>圈易物-收书活动管理主页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">  
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/activity/sellbook/admin.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/web/js/base/mtxx.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/activity/sellbook/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/activity/sellbook/admin.css">
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
  	<s:include value="../../web/base/top.jsp"/>
  	<div class = "main">
  		<input id = "pageno" type = "hidden" value = "0"/>
  		<div class = "sba_top">
  			<img src = "${pageContext.request.contextPath}/activity/sellbook/image/main_bar.jpg" style= "float:left;width:800px;height:110px;"/>
  			<a class = "sb_button" style = "float:left;margin-top:10px;margin-left:20px;width:120px;height:80px;border-radius:5px;line-height:81px;font-size:20px;" onclick = "sendBox.show();">
  				发布旧书
  			</a>
  		</div>
  		<div class = "sb_bar">
  			<a href = "${pageContext.request.contextPath}/activity/sell_book/admin?page=books&p=1">
	  			<div class = "sb_bar_btn <s:if test = '#parameters.page==null || #parameters.page[0]=="books"'>sb_bar_on</s:if>">
	  				已发布图书
	  			</div>
  			</a>
  			<img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_bar_split.jpg" style = "float:left;"/>
  			<a href = "${pageContext.request.contextPath}/activity/sell_book/admin?page=booking&p=1">
	  			<div class = "sb_bar_btn <s:if test = '#parameters.page[0]=="booking"'>sb_bar_on</s:if>">
	  				预定信息
	  			</div>
  			</a>
  			<img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_bar_split.jpg" style = "float:left;"/>
  		</div>
  		<div style = "float:left;width:100%;margin-top:10px;">
			<s:if test = '#parameters.page==null || #parameters.page[0]=="books"'>
				<div style = "width:920px;">
					<div style = "float:left;line-height:40px;">
		  				<input name = "key" id = "bi_key" type = "text" class = "sba_text" style = "float:left;width:200px;margin-top:5px;" TextDefault = "请输入已发布图书的编号或名字" value = "${key}"/>
		  				<a class = "sb_button" style = "float:left;margin-left:10px;width:60px;height:25px;line-height:26px;margin-top:6px;border-radius:3px;" onclick = "books.selected()">查询</a>
		  			</div>
					<table id = "sba_booksinfo_tb">
						<tr style = "background:#89b403;color:white;">
							<td class = "bi_tb_id">编号</td>
							<td class = "bi_tb_tiny">缩略图</td>
							<td class = "bi_tb_name">图书名称</td>
							<td class = "bi_tb_price">价格</td>
							<td class = "bi_tb_stock">图书类型</td>
							<td class = "bi_tb_type">适用范围</td>
							<td class = "bi_tb_stock">库存</td>
							<td class = "bi_tb_update">操作</td>
						</tr>
						<s:iterator id = "b" value = "#request.booksList" status = "b_status">
							<tr id = "bi_tr_${b.id}">
								<td class = "bi_tb_id">${b.id}</td>
								<td class = "bi_tb_tiny"><a href = "javascript:void(0);" onclick = "bigImg.show({id:${b.id}});"><img src = "${b.firstImg}" style = "table-layout:fixed;vertical-align:middle;max-width:145px;max-height:145px;min-width:80px;min-height:80px;"/></a></td>
								<td class = "bi_tb_name">${b.name}</td>
								<td class = "bi_tb_price">${b.price}</td>
								<td class = "bi_tb_stock">${b.bookType}</td>
								<td class = "bi_tb_type">${b.scope}</td>
								<td class = "bi_tb_stock">${b.stock}</td>
								<td class = "bi_tb_update">
									<a href= "javascript:void(0);" class = "sb_button" style = "width:50px;height:20px;line-height:21px;" onclick = "updateBox.show({id:${b.id}})">修改</a>
									<a href= "javascript:void(0);" class = "sb_button" style = "width:50px;height:20px;line-height:21px;margin-top:10px;" onclick = "confirmBox.show({func:function(){books.del({id:${b.id},target:$('#bi_tr_${b.id}')});},msg:'确认删除${b.name}这本书吗?'});">删除</a>
								</td>
							</tr>
						</s:iterator>
					</table>
					<s:if test = "#request.booksCount>#request.msgSize">
						<div style = "float:left;margin-top:15px;width:100%;">
							<ul style = "float:right;margin-right:20px;">
								<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
				                     <s:param name="first"  value= "1"  />    
				                     <s:param name="last"  value= "#request.booksCount/#request.msgSize+((#request.booksCount%#request.msgSize)!=0?1:0)"/>    
				                     <s:if test = '%{(#parameters.p[0]>=1+2)}'>
				                    		<a href="javascript:void(0);" onclick = "books.changePage({p:1})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
				                    		<s:if test = '%{(#parameters.p[0]>1+2)}'>
				                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
				                    		</s:if>
				                    	 </s:if>
				                     <s:iterator status="stat">
				                     	<s:if test = '%{(#parameters.p[0]>=#stat.index) && (#parameters.p[0]<=#stat.index+2)}'>
				                     		<a href="javascript:void(0);" onclick = "books.changePage({p:<s:property/>})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
				                     	</s:if>
				                     </s:iterator>
				                     <s:if test = '%{(#parameters.p[0]<=last-2)}'>
				                    	<s:if test = '%{(#parameters.p[0]< last-2)}'>
				                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
				                   		</s:if>
				                   		<a href="javascript:void(0);" onclick = "books.changePage({p:<s:property value = "last"/>})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
				                   	</s:if>    
				                 </s:bean>
							</ul>
						</div>
					</s:if>
					<div id = "show_big_img" style = "position:absolute;width:600px;height:600px;background:#d1d1d1;border-radius:2px;z-index:105;display:none;">
						<a href = "javascript:void:(0)" onclick = "bigImg.hide();"> <img src = "${pageContext.request.contextPath}/web/image/base/del_button.png"  style = "float:right;width:30px;height:30px;border-radius:3px;"/></a>
						<img id = "big_img" style = ""/>
					</div>
				</div>
			</s:if>
			<s:elseif test = '#parameters.page[0]=="booking"'>
				<div style = "width:920px;">
					<div style = "float:left;width:100%;line-height:20px;">
						<span style = "float:left;">取书方式:<s:select id = "bookingType" name = "bookingType" list = "#{'':'不限','自取':'自取','送货上门':'送货上门'}"/></span>
						<span style = "float:left;margin-left:30px;">所在校区:<s:select  id = "campus" name = "campus" list = "#{'':'不限','沙河':'沙河','本部':'本部'}"/></span>
						<span style = "float:left;margin-left:30px;">是否完成交易:<s:select id = "isComplete" name = "isComplete" list = "#{'':'不限','0':'否','1':'是'}"/></span>
						<a class = "sb_button booking_selected" id = "booking_selected_confirm" style = "float:left;margin-left:20px;width:60px;height:20px;line-height:21px;border-radius:3px;">筛选</a>
						<div style = "float:right;line-height:30px;">
							<input id = "bookingid" name = "id" type = "text" class = "sba_text" style = "float:left;margin-left:40px;width:150px;height:20px;line-height:20px;" TextDefault = "请输入订单号或用户名" value = "${key}"/>
  							<a class = "sb_button booking_selected" id = "booking_search_confirm" style = "float:left;margin-left:10px;width:60px;height:20px;line-height:21px;border-radius:3px;">查询</a>
						</div>
					</div>
					<table id = "sba_booking_tb">
						<tr style = "background:#89b403;color:white;">
							<td class = "b_tb_id">订单号</td>
							<td class = "b_tb_bookid">书编号</td>
							<td class = "b_tb_name">预定图书名称</td>
							<td class = "b_tb_count">数量</td>
							<td class = "b_tb_customer">联系人</td>
							<td class = "b_tb_price">总价</td>
							<td class = "b_tb_remark">备注</td>
							<td class = "b_tb_complete">状态</td>
						</tr>
						<s:iterator id = "bl" value = "#request.bookingList" status = "bl_status">
							<tr id = "b_tr_${bl_status.index}">
								<td class = "b_tb_id">${bl.id}</td>
								<td class = "b_tb_bookid">${bl.bookid}</td>
								<td class = "b_tb_name"><a href = "javascript:void(0);" style="color:#89b043" onclick = "books.selected({key:'${bl.bookName}'})">${bl.bookName}</a></td>
								<td class = "b_tb_count">${bl.count}</td>
								<td class = "b_tb_customer"><a href = "javascript:void(0);" style="color:#89b043" onclick = "booking.searchByKey({key:'${bl.contacts}'})">${bl.contacts}</a><br>${bl.phone}</td>
								<td class = "b_tb_price">￥${bl.totalPrice}</td>
								<td class = "b_tb_remark">${bl.content}</td>
								<td class = "b_tb_complete" id = "b_tb_complete_${bl.id}">
									<s:if test = '#bl.isComplete == "0"'>
										<a href = "javascript:void(0);" class = "sb_button" style = "width:60px;height:20px;line-height:21px;margin:5px;" onclick = "confirmBox.show({func:function(){booking.complete({id:${bl.id},bookid:${bl.bookid},count:${bl.count},target:$('#b_tb_complete_${bl.id}')});},msg:'确认交易已经完成?若未完成请取消'});">交易完成</a>
										<a href = "javascript:void(0);" class = "sb_button" style = "width:60px;height:20px;line-height:21px;margin:5px;" onclick = "confirmBox.show({func:function(){booking.cancel({id:${bl.id},target:$('#b_tr_${bl_status.index}')});},msg:'确认删除此订单?'});">删除</a>
									</s:if>
									<s:else>
										已完成
									</s:else>
								</td>
							</tr>
						</s:iterator>
					</table>
					<s:if test = "#request.bookingCount>#request.msgSize">
						<div style = "float:left;margin-top:15px;width:100%;">
							<ul style = "float:right;margin-right:20px;">
								<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
				                     <s:param name="first"  value= "1"  />    
				                     <s:param name="last"  value= "#request.bookingCount/#request.msgSize+((#request.bookingCount%#request.msgSize)!=0?1:0)"/>    
				                     <s:if test = '%{(#parameters.p[0]>=1+2)}'>
				                    		<a href="javascript:void(0);" onclick = "booking.changePage({p:1})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
				                    		<s:if test = '%{(#parameters.p[0]>1+2)}'>
				                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
				                    		</s:if>
				                    	 </s:if>
				                     <s:iterator status="stat">
				                     	<s:if test = '%{(#parameters.p[0]>=#stat.index) && (#parameters.p[0]<=#stat.index+2)}'>
				                     		<a href="javascript:void(0);" onclick = "booking.changePage({p:<s:property/>})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
				                     	</s:if>
				                     </s:iterator>
				                     <s:if test = '%{(#parameters.p[0]<=last-2)}'>
				                   	 	<s:if test = '%{(#parameters.p[0]< last-2)}'>
				                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
				                   		</s:if>
				                   		<a href="javascript:void(0);" onclick = "booking.changePage({p:<s:property value = "last"/>})"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
				                   	</s:if>    
				                 </s:bean>
							</ul>
						</div>
					</s:if>
				</div>
			</s:elseif>
  		</div>
  		<div style = "float:left;width:100%;height:30px;"></div>
    	</div>
	<div id = "sendBox">
		<div class = "sendBox_nav" style = "height:25px;">
			<span style = "line-height:26px;">书名:</span>
			<input type = "text" class = "send_obj" id = "book" maxlength="30"/>
			<a href = "javascript:void(0);" onclick = "sendBox.hide();"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:right;width:18px;margin-right:15px;margin-top:3px;" title="取消发布"/></a>
		</div>
		<div class = "sendBox_nav">
			<span>上传图书照片</span>
			<span style = "float:left;color:#b1b1b1;font-size:13px;line-height:22px;">(可上传6张,最大为5M,上传后不可修改)</span>
		</div>
		<div class = "pic_block">
			<input type = "hidden" id = "pic_index" value = "0"/>
			<input type = "hidden" id = "click_number"/>
			<input type = "hidden" id = "pic_number" value = "0"/>
		</div>
		<div id = "sendBox_mainInfo" style = "float:left;">
			<span style = "float:left;margin-top:15px;margin-left:20px;font-size:14px;color:#b1b1b1;">以下信息非必须全部填写</span>
			<div id = "mainInfo_sell" class = "mainInfo" style = "float:left;width:100%;">
				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_price.png"/></div><div class = "tab">价格:</div><input type = "text" maxlength="10" class = "mi_text" id= "price" onpaste = "return false;" onkeydown = "return numbervalidate();"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_stock.png"/></div><div class = "tab">库存:</div><input type = "text" maxlength="10" class = "mi_text" id= "stock" onpaste = "return false;" onkeydown = "return numbervalidate();"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_type.png"/></div><div class = "tab">图书类型:</div><s:select cssStyle = "float:left;width:120px;" id = "book_type" list="#{'教科书':'教科书','教学辅助':'教学辅助','考研书籍':'考研书籍','英语相关':'英语相关','其他':'其他'}"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_scope.png"/></div><div class = "tab">范围:</div><s:select id = "fit_department" style = "float:left;width:120px;" list="#{'数学':'数学','思想政治':'思想政治','材料科学':'材料科学','电子信息':'电子信息','自动化科学':'自动化科学','能源与动力':'能源与动力','航空航天':'航空航天','计算机与软件工程':'计算机与软件工程','机械工程':'机械工程','经济管理学':'经济管理学','生物与医学':'生物与医学','外语学':'外语学','交通科学':'交通科学','可靠性科学':'可靠性科学','仪器光电':'仪器光电','物理学':'物理学','法学':'法学','化学':'化学','人文社科':'人文社科','其他':'其他'}"/></div>
			</div>
			<div id = "mainInfo_common" style = "float:left;width:100%;">
				<div class = "info_text"><div class = "tab" style = "float:left;"><span style = "float:left;font-size:16px;font-family:'黑体',Arial;">补充说明:</span></div>
					<div class = "facelist face-switch" id = "book_facelist" onclick = "getFaceList('book_facelist',$('#book_more_info_text'),'book_details_textlimit');"><img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/></div>
					<div id = "book_details_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:2px;">0/140</div>
				</div>
				<div style = "float:left;width:100%">
					<textarea class = "more_info_text" id = "book_more_info_text" TextDefault="对书籍增添一些其他描述~" onkeyup="textLimit.getLength({id:'book_details_textlimit',target_text:$(this)})"></textarea>
    		 		<div class = "sb_button" id = "send_btn" style = "float:left;width:85px;height:65px;margin-left:10px;margin-top:5px;line-height:65px;border-radius:5px;" onclick = "books.send()"><span style = "font-size:19px;color:white;">发布</span></div>
				</div>
			</div>
		</div>
		<div style = "float:left;width:100%;height:10px;"></div>
	</div>
	<div id = "updateBox">
		<div class = "sendBox_nav" style = "height:25px;">
			<span style = "line-height:26px;">书名:</span>
			<input type = "text" class = "send_obj" id = "update_book" maxlength="30"/>
			<a href = "javascript:void(0);" onclick = "updateBox.hide();"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:right;width:18px;margin-right:15px;margin-top:3px;" title="取消修改"/></a>
		</div>
		<div id = "sendBox_mainInfo" style = "float:left;">
			<div id = "mainInfo_sell" class = "mainInfo" style = "float:left;width:100%;">
				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_price.png"/></div><div class = "tab">价格:</div><input type = "text" maxlength="10" class = "mi_text" id= "update_price" onpaste = "return false;" onkeydown = "return numbervalidate();"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_stock.png"/></div><div class = "tab">库存:</div><input type = "text" maxlength="10" class = "mi_text" id= "update_stock" onpaste = "return false;" onkeydown = "return numbervalidate();"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_type.png"/></div><div class = "tab">图书类型:</div><s:select cssStyle = "float:left;width:120px;" id = "update_book_type" list="#{'教科书':'教科书','教学辅助':'教学辅助','考研书籍':'考研书籍','英语相关':'英语相关','其他':'其他'}"/></div>
 				<div class = "info_text"><div class = "info_img"><img src = "${pageContext.request.contextPath}/activity/sellbook/image/sb_scope.png"/></div><div class = "tab">范围:</div><s:select id = "update_fit_department" style = "float:left;width:120px;" list="#{'数学':'数学','思想政治':'思想政治','材料科学':'材料科学','电子信息':'电子信息','自动化科学':'自动化科学','能源与动力':'能源与动力','航空航天':'航空航天','计算机与软件工程':'计算机与软件工程','机械工程':'机械工程','经济管理学':'经济管理学','生物与医学':'生物与医学','外语学':'外语学','交通科学':'交通科学','可靠性科学':'可靠性科学','仪器光电':'仪器光电','物理学':'物理学','法学':'法学','化学':'化学','人文社科':'人文社科','其他':'其他'}"/></div>
			</div>
			<div id = "mainInfo_common" style = "float:left;width:100%;">
				<div class = "info_text"><div class = "tab" style = "float:left;"><span style = "float:left;font-size:16px;font-family:'黑体',Arial;">补充说明:</span></div>
					<div class = "facelist face-switch" id = "update_box_facelist" onclick = "getFaceList('update_box_facelist',$('#update_box_info_text'),'update_box_textlimit');"><img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/></div>
					<div id = "update_box_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:2px;">0/140</div>
				</div>
				<div style = "float:left;width:100%">
					<textarea class = "more_info_text" id = "update_box_info_text" TextDefault="对书籍增添一些其他描述~" onkeyup="textLimit.getLength({id:'update_box_textlimit',target_text:$(this)})"></textarea>
    		 		<div class = "sb_button" id = "update_btn" style = "float:left;width:85px;height:65px;margin-left:10px;margin-top:5px;line-height:65px;border-radius:5px;" onclick = "updateBox.submit()"><span style = "font-size:19px;color:white;">保存修改</span></div>
				</div>
			</div>
		</div>
		<input type = "hidden" id = "update_id"/>
		<div style = "float:left;width:100%;height:10px;"></div>
	</div>
	<div id = "bookDetails_loading" style = "position:absolute;width:320px;height:50px;background:white;border:2px solid #e4e3e3;border-radius:10px;z-index:105;line-height:50px;display:none;">
		<span>图书信息读取中</span><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" />
	</div>
	<s:include value="../../web/base/face.jsp"/>
    <s:include value="../../web/base/toolBox.jsp"/>
    	<s:include value="../../web/base/foot/footDefault.jsp"/>
  </body>
</html>
