<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <title>圈易物-${root.obj_object}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">  
	<meta http-equiv="keywords" content="quan15，圈易物,大学,朋友,物品分享,分享圈,社区,交友,便利生活,闲置物品,圈易物网">
	<meta http-equiv="description" content="圈易物-${root.obj_object}  闲置详情:${root.obj_content}">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/image/base/s_icon.ico" />
	<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/web/image/base/icon.png" />	
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery-1.7.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/jquery.imageLens.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/base.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/rotate.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/temp/details.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/web/js/base/mtxx.js" async></script>
	<script type="text/javascript" src = "http://widget.renren.com/js/rrshare.js" async></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/base.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/common.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/tempBase.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/base/temp/sendBox.css"/>
	<!--[if lt IE 7]>
		<script type='text/javascript'>
			window.location.href = 'lowVersion';
		</script>
	<![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/home/homeForIE.css"><![endif]--> 
  </head>
  
  <body>
     <div class = "main" id = "main">
    	<s:include value="../../base/top.jsp"/>
		<input type = "hidden" id = "dg_showpri"/>
		<div class = "goods_d_block notesdetails" id = "goods_details">
			<div class = "d_left">
				<div class = "d_top">
					<div style = "float:left;margin-left:10px;line-height:38px;font-size:17px;">物品详情</div>
					<s:if test = "#request.root.obj_userid == #session.userid">
						<div style = "float:right;line-height:38px;margin-right:10px;">
							<a href= "javascript:void(0);" style = "color:#e68303;font-size:14px;" onclick = "updateInfoBox.init({id:${root.obj_id}})">信息修改</a>
							<label style = "color:#b1b1b1">&nbsp; | &nbsp;</label>
							<s:if test = '#request.root.obj_isShared == "0"'>
								<a href= "javascript:void(0);" style = "color:#e68303;font-size:14px;" onclick = "updateShareStatus({id:${root.obj_id}});">改为已出</a>
							</s:if>
							<s:else>
								<a href= "javascript:void(0);" style = "color:#e68303;font-size:14px;" onclick = "updateShareStatus({id:${root.obj_id}});">改为未出</a>
							</s:else>
						</div>
						<div id = "updateBox"></div>
					</s:if>
				</div>
					<div class = "d_left_top">
						<div style = "width:100px;float:left;">
							<a class="d_user_link"><img uCard="${root.obj_userid}" src = "${root.sender_avatar_big}" style = "float:left;width:100px;border-radius:4px;" id = "dg_userhead" class = "d_userhead"/></a>
							<a class="d_user_link" href = "${pageContext.request.contextPath}/user/${root.obj_userid}"><span class = "d_name" id = "d_name">${root.sender_name}</span></a>
						</div>
						<div style = "width:235px;height:120px;float:left;margin-top:5px;margin-left:5px;">
							<div style = "width:235px;float:left;text-align:left;line-height:25px;">
								<img src = "${pageContext.request.contextPath}/web/image/base/icon/${root.obj_type}_icon.png" style = "float:left;margin:5px;margin-top:0px;width:30px;" class = "d_type"/>
								<span class= "d_text_block" style="margin-top:10px;font-size:15px;font-weight:400;">${root.obj_object}</span>
							</div>
							<s:if test='#request.root.obj_goodsType1!="" && #request.root.obj_goodsType2!=""'>
								<div class = "d_text">
									<img src="${pageContext.request.contextPath}/web/image/base/selected_notes.png" style="float:left;margin-left:5px;"/>
									<div class = "d_sharetype1" style = "float:left;height:20px;line-height:20px;font-size:14px;font-weight:500;margin-left:5px;">${root.obj_goodsType1}</div>
									<div class = "d_sharetype2" style = "float:left;height:20px;line-height:20px;font-size:14px;font-weight:500;">-${root.obj_goodsType2}</div>
								</div>
							</s:if>
							<div class = "d_time">${root.obj_addTime}</div>
						</div>
					</div>
					<div class = "d_infodetails">
						<span class = "d_infodetails_block">
							<s:if test='#request.root.obj_content!=""'>
								${root.obj_content}
							</s:if>
						</span>
					</div>
					<div class = "d_left_main">
						<div class = "d_info" style = "margin-top:0px;"><img class = "d_left_arrow" src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;"/><span style = "width:100px;color:#e68303;">其他信息</span></div>
						<div class = "d_info_block">
							<s:if test = '#request.root.obj_type=="sell"'>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/price.png'/></div><span>价格:</span><div class = 'd_box d_price' id ='d_price'><span>${root.obj_price}</span></div>
								</div>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/olddegree.png'/></div><span>新旧程度:</span><div class = 'd_box d_olddegree' id ='d_olddegree'><span>${root.obj_oldDegree}</span></div>
								</div>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/bargain.png'/></div><span>是否可刀:</span><div class = 'd_box d_bargain' id ='d_bargain'><span>${root.obj_bargain}</span></div>
								</div>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/goodslink.png'/></div><span>报价链接:</span><div class = 'd_box d_goodslink' id ='d_goodslink'><span><s:if test = '#request.root.obj_goodsLink!=""'><a href='${root.obj_goodsLink}' target='_blank' style='color:#e68303;'>${root.obj_goodsLink}</a></s:if><s:else>未填写</s:else></span></div>
								</div>
							</s:if>
							<s:elseif test = '#request.root.obj_type=="lend"'>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/borrow_time.png'/></div><span>借出时长:</span><div class = 'd_box d_borrow_time' id ='d_borrow_time'><span>${root.obj_totalTime}</span></div>
								</div>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/olddegree.png'/></div><span>新旧程度:</span><div class = 'd_box d_olddegree' id ='d_olddegree'><span>${root.obj_oldDegree}</span></div>
								</div>
							</s:elseif>
							<s:elseif test = '#request.root.obj_type=="gift"'>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/price.png'/></div><span>估价:</span><div class = 'd_box d_price' id ='d_price'><span>${root.obj_price}</span></div>
								</div>
								<div class = 'd_info'>
									<div style = 'float:left;width:22px;height:20px;'><img src = '${pageContext.request.contextPath}/web/image/home/olddegree.png'/></div><span>新旧程度:</span><div class = 'd_box d_olddegree' id ='d_olddegree'><span>${root.obj_oldDegree}</span></div>
								</div>
							</s:elseif>
						</div>
						<s:if test = '#request.root.showPri == true'>
							<div class = "d_info" style = "margin-top:20px;"><img class = "d_left_arrow" src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;"/><span style = "width:100px;color:#e68303;">联系方式</span></div>
							<div class = "d_privacy" id = "d_privacy">
								<div class = "d_info"><div style = "float:left;width:22px;height:20px;"><img src = "${pageContext.request.contextPath}/web/image/home/phone.png"/></div><span>手机:</span><div class = "d_box d_phone" id ="d_phone"><span>${root.sender_phone}</span></div></div>
								<div class = "d_info"><div style = "float:left;width:22px;height:20px;"><img src = "${pageContext.request.contextPath}/web/image/home/qq.png"/></div><span>QQ:</span><div class = "d_box d_qq" id ="d_qq"><span>${root.sender_qq}</span></div></div>
								<div class = "d_info"><div style = "float:left;width:22px;height:20px;"><img src = "${pageContext.request.contextPath}/web/image/home/show_email.png"/></div><span>常用邮箱:</span><div class = "d_box d_show_email" id ="d_show_email"><span>${root.sender_email}</span></div></div>
							</div>
						</s:if>
					</div>
					<div class = "d_userop" id = "d_userop" style = "float:left;width:300px;margin-top:20px;">	
					</div>
					<div id = "d_complaint_box" class="d_complaint_box d_userop_box" style="float:left;width:300px;display:none;">
					</div>
					<div id = "d_addfriends_box" class="d_addfriends_box d_userop_box" style="float:left;width:300px;display:none;">
					</div>
					<div id = "d_addblacklist_box" class="d_addblacklist_box d_userop_box" style="float:left;width:300px;display:none;">
					</div>
				</div>
			<div class = "d_right">
				<div style = "float:left;width:470px;height:25px;">
					<img class = "d_left_arrow" src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;"/><span id = "d_stress_info" style = "font-size:16px;line-height:25px;float:left;margin-left:3px;"><span>已有:</span><span class = "stressNum d_lookednum">${root.obj_lookedTimes}</span><span>人看过,</span><span class = "stressNum d_commentnum">${root.obj_commentSize}</span><span>条留言,</span><span class = "stressNum d_likednum">${root.obj_requiredTimes}</span><span>人想要</span></span>
				</div>
				<div class = "d_pic_top_tip" style = "float:left;width:470px;height:25px;margin-top:10px;">
					<s:if test = "#request.root.picnum!=0">
						<img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:14px;padding-top:5px;"/><span style = "float:left;font-size:16px;line-height:25px;margin-left:3px;"><span style = "float:left;">有图有真相,共上传</span><span id = "d_picnum" class = "stressNum" >${root.picnum}</span><span style = "float:left;">张照片(点击看大图)</span></span>
					</s:if>
					<s:else><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:14px;padding-top:5px;"/><span style = "font-size:16px;line-height:25px;float:left;margin-left:3px;">无图无真相...</span></s:else>
				</div>
				<div class = "d_pic_main">
					<input type="hidden" id="pic_num" value = "${root.picnum}"/>
					<input type="hidden" id="pic_pro_list" value = "${root.piclist}"/>
					<input type="hidden" id="pic_pro_html"/>
					<s:if test="#request.root.picnum!=0">
						<s:set id = "picThumbs" name="picThumbs" value='#request.root.thumbs_piclist.split(",")'></s:set>
						<s:iterator id = "d_pic" value = "#picThumbs" status = "dp_index">
							<div class = "d_picblock" style = "margin-left:6px;">
								<div class = "d_pic" onclick="getImgDetails({btn:$(this)})">
									<img name = "d_pic_prev" id = "d_pic_${dp_index.index+1}" style = "table-layout:fixed;vertical-align:middle;max-width:138px;max-height:138px;" src = "<s:property value = "#d_pic"/>" onload = "thumbsOnload({target:this})"/>
								</div>
							</div>
						</s:iterator>
					</s:if>
				</div>
				<div style = "float:left;width:470px;height:25px;margin-top:13px;">
					<img class = "d_left_arrow" src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;"/><span style = "font-size:16px;line-height:25px;float:left;margin-left:3px;">我要给Ta</span>
					<div class = "at-icon" target="goods_leave_msg_text" style = "margin-left:30px;">@</div>
					<div class = "facelist face-switch" id = "gd_facelist" onclick = "getFaceList('gd_facelist',$('#goods_leave_msg_text'),'gd_textlimit');" style = "margin-left:10px;">
						<img src = "${pageContext.request.contextPath}/web/image/base/face.png" style = "float:left;"/>
					</div>
					<div id = "gd_textlimit" style = "float:left;margin-left:30px;height:20px;color:grey;font-size:13px;line-height:25px;">0/140</div>
					<div style = "float:left;margin-left:30px;">
						<span style = "font-size:15px;line-height:25px;float:left;">分享到 :</span>
						<a href = "javascript:void(0);" style = "float:left;margin-left:10px;" onclick = "rr_shareClick({title:'${root.obj_object}',description:'${root.obj_content}'});"><img src = "${pageContext.request.contextPath}/web/image/base/icon/renren_icon.png" style = "float:left;width:30px;height:16px;margin-top:5px;"/></a>
					</div>
				</div>
				<div style = "margin-left:20px;margin-top:5px;width:445px;height:62px;float:left;">
					<div style = "float:left;margin:0px;">
						<textarea at-field class = "leave_msg_text" id = "goods_leave_msg_text" TextDefault = "填写留言或请求信息" onkeyup=" textLimit.getLength({id:'gd_textlimit',target_text:$(this)});"></textarea>
					</div>
				</div>
				<div style = "float:right;width:385px;height:20px;margin-top:20px;">
					<s:if test = "#request.root.isReq == 1">
						<div id= "sendreq_btn" class = "button button_do" style = "height:25px;font-size:15px;line-height:26px;float:right;margin-right:0px;width:100px;">请求已发送</div>
					</s:if>
					<s:elseif test = '#request.root.obj_isShared == "1"'>
						<div id= "sendreq_btn" class = "button button_do" style = "height:25px;font-size:15px;line-height:26px;float:right;margin-right:0px;width:65px;">已分享</div>
					</s:elseif>
					<s:else>
						<div id= "sendreq_btn" class = "button" style = "float:right;margin-right:0px;width:110px;height:25px;line-height:26px;" onclick = "bookingBox.load({target:$('#goods_leave_msg_text')});">想要 , 求联系</div>
					</s:else>
					<div id= "comm_btn" class = "button" style = "float:right;margin-right:30px;width:65px;height:25px;line-height:26px;" onclick = "comment.send({typeid:${root.obj_id},type:'goods',share:'${root.obj_object}',target_text:$('#goods_leave_msg_text')})">留言</div>
					<input type="hidden" class="temp_id" value="${root.obj_userid}"/>
				</div>
				<div style = "float:left;width:470px;height:25px;margin-top:15px;">
					<img class = "d_left_arrow" src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;"/><span style = "font-size:16px;font-family:'黑体',Arial;line-height:25px;float:left;margin-left:3px;">留言板</span>
				</div>
				<div class = "d_comments">
				</div>
				<div class = "d_comm_getmore_loading" style = "float:left;width:100%;display:none;"><img src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/></div>
				<div class = "d_comm_getmore" style="cursor:pointer;" onclick="comment.getMore({userid:${root.obj_userid},typeid:${root.obj_id},type:'goods'})">
					<input type="hidden" class="d_comm_pn" value="0"/><span style = "color:#e68303;font-size:15px;font-family:\'黑体\',Arial;">获取更多评论</span>
				</div>
				<div style="float:left;width:100%;height:20px;"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		comment.getMore({firstGet:true,userid:"${root.obj_userid}",typeid:"${root.obj_id}",type:"goods"});
	</script>
	<div id = "showImgBox">
		<div style="float:left;width:100%;height:30px;line-height:40px;">
			<span style = "float:left;font-size:24px;color:#737373;font-weight:800;margin-left:350px;">物品大图</span>
			<a href = "javascript:void(0);" style = "float:right;margin-top:6px;margin-right:10px;width:25px;height:25px;" onclick = "showImgBox.hide();"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:25px;height:25px;"/></a>
		</div>
		<div id= "showImg_backward" style = "z-index:160;left:0px;top:40px;height:600px;width:400px;position:absolute;background:url(${pageContext.request.contextPath}/web/image/base/place_holder_tran.png);"><div style = "position:absolute;top:0px;left:0px;width:400px;height:600px;background:url(${pageContext.request.contextPath}/web/image/base/pic_turnleft.png) left center no-repeat;cursor:pointer;"></div></div>
		<div id= "showImg_forward" style = "z-index:160;right:0px;top:40px;height:600px;width:400px;position:absolute;background:url(${pageContext.request.contextPath}/web/image/base/place_holder_tran.png);"><div style = "position:absolute;top:0px;left:0px;width:400px;height:600px;background:url(${pageContext.request.contextPath}/web/image/base/pic_turnright.png) right center no-repeat;cursor:pointer;"></div></div>
		<div style = "float:left;overflow:hidden;width:600px;height:600px;margin-left:100px;margin-top:20px;position:relative;">
			<ul id= "showImg_block" style = "position:absolute;top:0px;height:600px;padding:0px;margin:0px;">
			</ul>
		</div>
		<div style = "float:left;width:100%;height:30px;">
			<a href = "javascript:void(0);" onclick="idleImgTurn.left();" style = "float:left;margin-left:260px;color:#737373;font-size:18px;margin-top:5px;"><img src="${pageContext.request.contextPath}/web/image/base/temp/turnLeft.png" style = "float:left;"/>向左转</a>
			<a href = "javascript:void(0);" onclick="idleImgTurn.right();" style = "float:left;margin-left:100px;color:#737373;font-size:18px;margin-top:5px;"><img src="${pageContext.request.contextPath}/web/image/base/temp/turnRight.png" style = "float:left;"/>向右转</a>
		</div>
	</div>
	<div id = "bookingBox" style = "display:none;position:absolute;top:30px;width:480px;background:white;border:2px solid #e4e3e3;border-radius:10px;z-index:104;">
		<div style = "float:left;margin-top:5px;width:420px;margin-left:30px;">
			<div style = "float:left;width:390px;line-height:35px;">
				<span style = "float:left;text-align:left;"><label style = "color:#e68303;" id = "book_idleName">${root.obj_object}</label></span>
			</div>
			<a href = "javascript:void(0);" style = "float:right;width:20px;margin-top:5px;"  onclick = "bookingBox.hide()"><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "width:20px;height:20px;" alt="取消"/></a>
			<div style = 'float:left;width:100%;margin-top:5px;border-bottom:1px solid #d8d8d8;'></div>
		</div>
		<div style = "float:left;width:420px;margin-left:30px;">
			<div style = "float:left;width:100%;line-height:35px;">
				<div style = "float:left;text-align:left;">
					<label style = "float:left;width:100px;text-align:left;">物品发布人: </label><img src = "${root.sender_avatar}" style = "float:left;width:60px;height:60px;margin-top:8px;"/>
					<div style = "float:left;width:240px;margin-left:10px;line-height:20px;">
						<span style = "float:left;color:#e68303;width:100%;text-align:left;line-height:35px">${root.sender_name}</span>
						<span style = "float:left;font-size:13px;width:100%;text-align:left;">${root.sender_school} ${root.sender_edu}</span>
						<span style = "float:left;font-size:13px;width:100%;text-align:left;">${root.sender_department} ${root.sender_grade}</span>
					</div>
				</div>
			</div>
			<div style = 'float:left;width:100%;margin-top:5px;border-bottom:1px solid #d8d8d8;'></div>
		</div>
		<div style = "float:left;margin-top:5px;width:420px;margin-left:30px;line-height:35px;">
			<span style = "float:left;width:100px;text-align:left;">交易方式 :</span>
			<div style = "float:left;width:320px;">
				<span style = "float:left;"><input type = "radio" class = "book_tranType" name = "tranType" id = "book_self_transaction" checked value = "自主交易"/> 自主交易</span>
				<span style = "float:left;margin-left:20px;"><input class = "book_tranType" type = "radio" name = "tranType" id = "book_delivery_transaction" value = "网站送货"/> 网站送货 <label style = "color:#b1b1b1;font-size:14px;">(仅活动期间)</label></span>
			</div>
		</div>
		<div style = "float:left;margin-top:5px;width:420px;margin-left:30px;line-height:35px;">
			<div style = "float:left;width:100%;">
				<span style = "float:left;width:100px;text-align:left;">联系电话 :</span>
				<div style = "float:left;width:320px;">
					<input id = "book_phone" type = "text" class = "text" style = "margin-top:5px;width:180px;padding-left:5px;" maxlength = "11" onpaste = 'return false;' onkeydown = 'return numbervalidate();' TextDefault = "常用联系电话"/>
					<label style = 'float:left;margin-left:20px;cursor:pointer;'> * (告诉对方<input type = 'checkbox' checked id = 'book_showContact'/>)</label>
				</div>
			</div>
		</div>
		<div id = "book_moreInfo"  style = "float:left;width:420px;margin-top:5px;margin-left:30px;line-height:35px;display:none;">
			<div style = "float:left;width:100%;">
				<span style = "float:left;width:100px;text-align:left;">所在地址 :</span>
				<div style = "float:left;width:320px;">
					<span style = "float:left;width:100%;"><input id = "book_dorm" type = "text" class = "text" style = "margin-top:5px;width:180px;padding-left:5px;" maxlength = "30" TextDefault = "校区-宿舍楼-宿舍号"/></span>
					<span style = "float:left;width:100%;margin-top:10px;text-align:left;color:#b1b1b1;font-size:14px;line-height:25px;">注 : 以上信息只用作收货联系 , 不会提供给对方 。<label style = "color:#e68303;">本活动仅提供从本部到沙河送货</label> , 送货需一定运费 , 详情见<a href = "/activity/grad_carnival/show" target = '_blank' style = "color:#e68303;">活动细则</a></span>
				</div>
			</div>
		</div>
		<div style = "float:left;width:420px;margin-left:30px;margin-top:5px;line-height:35px;">
			<span style = "float:left;width:100px;text-align:left;">请求信息 :</span>
			<span style = "float:left;width:320px;text-align:left;line-height:35px;font-size:14px;" id = "book_req_text">
			</span>
		</div>
		<div style = "float:left;width:420px;height:50px;margin-left:30px;line-height:35px;margin-top:10px;">
			<a id = "sendreq_confirm" class = "button" style = "float:right;width:80px;height:35px;line-height:35px;border-radius:3px;" onclick = "sendShareRequest({userid:${root.obj_userid},type:'goods',shareMethod:'${root.obj_type}',share:'${root.obj_object}',reqid:${root.obj_id},target:$('#goods_leave_msg_text')});">提交请求</a>
		</div>
	</div>
	<input type="hidden" id="showImg_num"/>
	<input type="hidden" id="showImg_total"/>
	<input type="hidden" id="obj_sendid" value = "${root.obj_userid}"/>
	<s:include value="../../base/face.jsp"/>
    <s:include value="../../base/toolBox.jsp"/>
    <s:include value="../../base/foot/footDefault.jsp"/>
  </body>
</html>
