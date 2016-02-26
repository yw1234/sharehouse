$(document).ready(function(){
	showImgBox.init();
	mtxx.init({method:"upload/idleImages",callBack:"uploadImg.callBack",path:"idle-images"});
	uploadImg.commonInit({action:"/upload/idleImages",callBack:"uploadImg.callBack",path:"idle-images"});
});

function getImgDetails(data){
	var imgdef = "";
	if(data.btn.children("img").attr("src") != imgdef){
		showImgBox.show({show_num:data.btn.children("img").attr("id").split("_")[2]});
	}
}

function thumbsOnload(data){
	$(data.target).imageLens({ lensSize: 200,borderSize:4,borderColor:"#e68303" });
}

function sendShareRequest(data){
	var tranType = $('input[name="tranType"]:checked').val(),
		phone = $("#book_phone").val(),
		dorm = $("#book_dorm").val(),
		showContact = ($("#book_showContact").attr("checked"))?"1":"0",
		text = $("#book_req_text").html();
	$.ajax({
		url:"/message/sendRequest",
		type:"post",
		data:{
			userid:data.userid,
			reqType:data.type,
			reqShareId:data.reqid,
			reqShare:data.share,
			reqShareMethod:data.shareMethod,
			reqMessage:text,
			tranType:tranType,
			showContact:showContact,
			phone:phone,
			dorm:dorm
		},
		dataType:"json",
		beforeSend:function(){
			$("#sendreq_confirm").addClass("button_do").html("提交中");
			$("#sendreq_confirm")[0].onclick = function(){return false;}
		},
		complete:function(){
			data.target.val("");
			textLimit.reset({id:"gd_textlimit"});
		},
		timeout:20000,
		success:function(root){
			if(root.flag == "1"){
				successBox.show("请求发送成功,请对方等待回复");
				$("#sendreq_btn").addClass("button_do").html("已请求");
				$("#sendreq_btn")[0].onclick = function(){return false;}
				$.post("/email_tip/share",{id:data.reqid,userid:data.userid,reqMessage:text,idle:data.share},function(res){},"text");
			}else{
				$("#sendreq_confirm").removeClass("button_do").html("提交请求");
				$("#sendreq_confirm")[0].onclick = function(){sendShareRequest(data);}
				errorBox.show(root.message);
				return false;
			}
			bookingBox.hide();
		}
	});
	
}

var comment = {
	init:function(){
		introduceBox.init();
		$(".d_comm_block").unbind().hover(function(){$(this).children("div").children(".d_del_comm").children("img").show()},function(){$(this).children("div").children(".d_del_comm").children("img").hide()});
		$(".d_reply_btn").unbind().bind("click",function(){
			$(".leave_msg_text").focus().val("To"+$(this).parent().children(".reply_username").val()+":");
			$(".temp_id").val($(this).parent().children(".reply_userid").val());
		});
	},
	send:function(data){
		var text = data.target_text.val()
			,typeid = data.typeid
			,type = data.type
			,target = data.target_text;
		if(text == "" || text == data.target_text.attr("TextDefault")){
			errorBox.show("起码得说句话吧==||");
			return false;
		}
		if(!textLimit.validation({target_text:data.target_text})){errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/message/sendComment",
			type:"post",
			data:{
				userid:$(".temp_id").val(),
				commType:type,
				commShareId:typeid,
				commShare:data.share,
				content:text
			},
			dataType:"json",
			beforeSend:function(){
				$("#comm_btn").addClass("button_do").html("发布中");
			},
			complete:function(){
				$("#comm_btn").removeClass("button_do").html("留言");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					target.val("");
					textLimit.reset({id:"gd_textlimit"});
					$(".temp_id").val($("#obj_sendid").val());
					if(root.data != "")
					{
						if($("#noComment")[0]) $("#noComment").remove();
						var data = root.data;
						var before = $(".d_comments").html();
						$(".d_comments").html(data+before);
						comment.init();
					}
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},
	getMore:function(data){
		var pn = Number($(".d_comm_pn").val());
		$.ajax({
			url:"/message/getComment",
			type:"post",
			data:{
				pageno:pn,
				userid:data.userid,
				commShareId:data.typeid,
				commType:data.type
			},
			dataType:"json",
			beforeSend:function(){
				$(".d_comm_getmore").hide();
				$(".d_comm_getmore_loading").show();
			},
			complete:function(){
				$(".d_comm_getmore_loading").hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag =="1"){
					$(".d_comm_pn").val(pn+1);
					if(root.data != "")
						$(".d_comments").append(root.data);
					
					if(root.commentSize<root.maxSize) $(".d_comm_getmore").html("");
					$(".d_comm_getmore").show();
					comment.init();
				}else{
					errorBox.show(root.message)
				}
			}
		});
	},
	del:function(data){
		$.ajax({
			url:"/message/deleteComment",
			type:"post",
			data:{
				id:data.id,
				commShareId:data.typeid,
				commType:data.type
			},
			dataType:"json",
			beforeSend:function(){
				$("#d_comm_block_"+data.id).slideUp(100);
			},
			complete:function(){
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
				}else{
					errorBox.show(root.message);
				}
			}
		});
	}
}

var showImgBox={
	init:function(){
		var picNum = $("#pic_num").val();
		$("#showImg_backward,#showImg_forward").children("div").hide();
		$("#showImg_total").val(picNum);
		html="";
		for(var i=1;i<Number(picNum)+1;i++){
			var left = (i-1)*600+"px";
			html+="<li id = 'showImg_border_"+i+"' style = 'position:absolute;top:0px;left:"+left+";overflow:hidden;margin:0px;padding:0px;width:600px;height:600px;text-align:center;background:url(/web/image/base/pic_loading_default.gif) center center no-repeat;'>" +
				  "	  <img id = 'show_img_"+i+"' rotate='0' style = 'visibility:hidden;max-width:600px;max-height:600px;min-width:350px;min-height:350px;display:none;'/>" +
				  "</li>";
		}
		if($.browser.msie && $.browser.version<=7)$("#pic_pro_html").val(html);
		else $("#showImg_block").html(html);
		$("#showImg_backward").hover(function(){Number($("#showImg_num").val())>1?$(this).children("div").fadeIn(200):$(this).children("div").hide();},function(){$(this).children("div").fadeOut(200);})
			.click(function(){
				if(Number($("#showImg_num").val())>1)
					showImgBox.backward();
				else return false;
			});
		$("#showImg_forward").hover(function(){Number($("#showImg_num").val())<Number($("#showImg_total").val())?$(this).children("div").fadeIn(200):$(this).children("div").hide();},function(){$(this).children("div").fadeOut(200);})
			.click(function(){
				if(Number($("#showImg_num").val())<Number($("#showImg_total").val()))
					showImgBox.forward();
				else return false;
			});
	},
	show:function(data){
		var l = (1-Number(data.show_num))*600+"px",sl =($("html").width()-800)/2+"px",st=document.documentElement.scrollTop+document.body.scrollTop+10+"px";
		if($.browser.msie && $.browser.version<=7)
			$("#showImg_block").html($("#pic_pro_html").val());
		$("#showImg_num").val(data.show_num);
		$("#showImg_block").css({"left":l});
		$("#showImgBox").css({"left":sl,"top":st}).show();
		
		//加载大图
		var pic_pro = $("#pic_pro_list").val().split(",");
		for(i = 1 ; i < Number($("#showImg_total").val())+1;i++){
			document.getElementById("show_img_"+i).onload = function(){
				$(this).parent().css({"background":"transparent"});
				$(this).css({"visibility":"visible"}).fadeIn(200);
			}
			$("#show_img_"+i).attr("src",pic_pro[i-1]);
		}
		lock.show();
	},
	hide:function(){
		$("#showImgBox").hide();
		lock.hide();
	},
	forward:function(){
		var showingNum = Number($("#showImg_num").val());
		var nextImg = 0-showingNum;
		$("#showImg_block").stop(true,false).animate({
			left:nextImg*600+"px"
		},300);
		$("#showImg_num").val(showingNum+1);
	},
	backward:function(){
		var showingNum = Number($("#showImg_num").val());
		var priorImg = 2-showingNum;
		$("#showImg_block").stop(true,false).animate({
			left:priorImg*600+"px"
		},300);
		$("#showImg_num").val(showingNum-1);
	}
}

var idleImgTurn = {
	left:function(){
		var imgNum = $("#showImg_num").val();
		var rotateLeft = Number($("#show_img_"+imgNum).attr("rotate"))-90;
		$("#show_img_"+imgNum).rotate(rotateLeft);
		$("#show_img_"+imgNum).attr("rotate",rotateLeft);
	},
	right:function(){
		var imgNum = $("#showImg_num").val();
		var rotateRight = Number($("#show_img_"+imgNum).attr("rotate"))+90;
		$("#show_img_"+imgNum).rotate(rotateRight);
		$("#show_img_"+imgNum).attr("rotate",rotateRight);
		
	}
}


var updateInfoBox = {
	init:function(data){
		lock.show();
		$.ajax({
			url:"/getgoods",
			type:"post",
			data:{
				typeid:data.id
			},
			dataType:"json",
			beforeSend:function(){
				loadingBox.show("信息读取中")
			},
			complete:function(){
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#updateBox").append(root.data);
					at.init();
					//联动列表
					$("#goodstype_1").change(function(){
						if($("#goodstype_1").val()==""){document.getElementById("goodstype_2").length = 0;document.getElementById("goodstype_2").options[0] = new Option("请选择","");}
						else getActList.sendDetailType({id:"goodstype_2",gType:$(this).val()});
					});
					//图片删除
					$(".del").bind("click",function(){
						var num = $(this).attr("id").substring(4).trim();
						var id = $(this).attr("targetId");
						confirmBox.show({func:function(){uploadImg.deleteImg({num:num,id:id});},msg:"确认删除这张图片?"});
					});
					var picCount = $("#pic_number").val();
					if(Number(picCount)<6){
						uploadImg.addImg(picCount);
					}
					var left = ($("html").width()-500)/2+"px";
					setTimeout(function(){
						loadingBox.hide();
						$("#sendShareBox").css({"top":"20px","left":left}).show();
					},100);
				}else{
					errorBox.show(root.message);
					updateInfoBox.hide();
					return false;
				}
			},error:function(){
				updateInfoBox.hide();
			}
		});
	},
	save:function(){
		var pri = ($("#goods_show_pri_show").attr("checked")=="checked")?"1":"0",
			piclist = "",
			goods=$("#goods").val().trim(),
			price=$("#price").val()==null?"":$("#price").val(),
			olddegree=$("#old_degree").val(),
			lendtime=$("#lend_time").val(),
			link=($("#sell_link").val()==null||$("#sell_link").val()=="")?"":$("#sell_link").val(),
			bargain = $("#bargain").val(),
			goodstype1 = $("#goodstype_1").val(),
			goodstype2 = $("#goodstype_2").val(),
			content = ($("#goods_more_info_text").val() != $("#goods_more_info_text").attr("TextDefault") && $("#goods_more_info_text").val().trim() != "")?$("#goods_more_info_text").val():"";
		if(goods == ""){
			errorBox.show("物品名称不能为空!");
			return false;
		}
		$(".new_img").each(function(){
			if($(this).attr("src")!= "" && $(this).attr("src")!= null){
				if(piclist == "")
					piclist += $(this).attr("src").trim();
				else piclist += ","+$(this).attr("src").trim();
			}
		});
	if(!textLimit.validation({target_text:$("#goods_more_info_text")})) {
		errorBox.show("字数有点多哦~");
		return false;
	}
	$.ajax({
		url:"/updategoods",
		type:"post",
		data:{
			id:$("#send_goodsid").val(),
			goods:goods,
			price:price,
			old_degree:olddegree,
			lendtime:lendtime,
			content:content,
			piclist:piclist,
			goodslink:link,
			bargain:bargain,
			show_privacy:pri,
			goodstype1:goodstype1,
			goodstype2:goodstype2
		},
		dataType:"json",
		beforeSend:function(){
			$("#send_goods_btn").addClass("button_do").children("span").html("保存ing").unbind();
		},
		complete:function(){
			$("#send_goods_btn").removeClass("button_do").children("span").html("保存修改");
		},
		timeout:20000,
		success:function(root){
			if(root.flag == "1"){
				successBox.show("修改成功!");
				updateInfoBox.hide();
			}else{
				errorBox.show(root.message);
				return false;
			}
		}
	});
	},
	hide:function(){
		$("#updateBox").children("div").remove();
		loadingBox.hide();
		idleUploadBox.hide();
		lock.hide();
	}
}

//图片上传,附带删除
var uploadImg = {
	addImg:function(number){
		var num = Number(number);
		var data = '<div class = "pic" id = "pic_'+num+'">'+
						'<div class = "upb" id = "upb_'+num+'" title = "点击添加图片" onclick = "idleUploadBox.show({target:this});">'+
						'</div>'+
						'<div class = "upc" id = "upc_'+num+'">'+
							'<img class = "del" id = "del_'+num+'" src = "/web/image/base/del_button.png" style = "position:absolute;top:-10px;right:-10px;cursor:pointer;opacity:0.7;filter:alpha(opacity=70);visibility:hidden;width:20px;" title = "删除图片"/>'+
						 	'<img class = "show_img new_img" id = "show_img_'+num+'" src = "" style="display:none;"/>'+
						'</div>'+
					'</div>';
		
		$(".pic_block").append(data);
		$(".upb").unbind().click(function(){
			var num = $(this).attr("id").substring(4);
			$("#click_number").val(num.trim());
		})
		$("#del_"+num).unbind().bind("click",function(){
			var num = $(this).attr("id").substring(4).trim();
			uploadImg.hideImg(num);
		});
	},
	deleteImg:function(data) {
		var num = data.num;
		$.post(
			"/deleteidlePicture",
	    		{id:data.id},
	    		function(root){
	    			if(root.flag=="1") {
	    				$("#pic_"+num).remove();
	    				var picNum = Number($("#pic_number").val());
	    			    if(picNum > 5){
	    			    		$("#pic_index").val(picNum+1);
	    			    		uploadImg.addImg($("#pic_index").val());
	    			    }
	    			    $("#pic_number").val(picNum-1);
	    			}else{
	    				errorBox.show(root.message);
	    				return false;
	    			}
	    		},"json"
	    )
	},callBack:function(data){
		var url = data.url
			,picNum = Number($("#pic_number").val())
			,clickNum = Number($("#click_number").val())
			,picIndex = Number($("#pic_index").val());
		$("#upb_"+clickNum).css("display","none");
		$("#upc_"+clickNum).css("background","transparent").hide();
		$("#show_img_"+clickNum).attr("src",url).show();
		$("#upc_"+clickNum).fadeIn();
		$("#pic_index").val(picIndex+1);
		$("#pic_number").val(picNum+1);
		if(picNum < 5) 
			uploadImg.addImg(picIndex+1);
		$("#del_"+clickNum).css("visibility","visible");
    		$(".cancel").hide();
	},
	hideImg:function(num){
		$("#pic_"+num).remove();
	    if(Number($("#pic_number").val()) > 5){
	    		$("#pic_index").val(Number($("#pic_index").val())+1);
	    		uploadImg.addImg($("#pic_index").val());
	    }
	    $("#pic_number").val(Number($("#pic_number").val())-1);
	},
	commonInit:function(data){
		$("#idle_upload_form").attr("action",data.action);
		$("#callBackFunc").val(data.callBack)
		$("#uploadDir").val(data.path);
		$("#uploadType").val("common");
		$("#idle_upload_input").unbind().change(function(){
			if(upload.validator({id:"idle_upload_input"})){
				mtxx.hide();
				var clickNum = Number($("#click_number").val());
				$(".cancel").show();
				$("#upb_"+clickNum).css("display","none");
				$("#upc_"+clickNum).show();
				$("#idle_upload_form").submit();
			}
			$(this).val("");
		});
	}
}

var bookingBox = {
	load:function(data){
		var text = data.target.val();
		if(text == "" || text == data.target.attr("TextDefault")){
			text = "";
		}	
		if(!textLimit.validation({target_text:data.target}))
			{errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/message/bookInfo",
			type:"post",
			data:{},
			dataType:"json",
			beforeSend:function(){
				loadingBox.show("信息加载中");
			},
			complete:function(){
				loadingBox.hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					var info = root.userInfo;
					$("#book_phone").val(info.phone);
					$("#book_dorm").val(info.dorm);
					$("#book_req_text").html(text);
					$(".book_tranType").unbind().change(function(){
						if($(this).attr("id")=="book_delivery_transaction" && $(this).attr("checked")){
							$("#book_moreInfo").slideDown(200);
						}else $("#book_moreInfo").slideUp(200);
					})
					bookingBox.show();
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},
	show:function(){
		lock.show();
		var st = document.body.scrollTop+document.documentElement.scrollTop+90+"px";
		var sl =($("html").width()-480)/2+"px";
		$("#bookingBox").css({"top":st,"left":sl}).show();
	},
	hide:function(){
		$("#bookingBox").hide();
		lock.hide();
	}
}

function updateShareStatus(data){
	var url = location.href;
	$.ajax({
		url:"/updateisShared",
		type:"post",
		data:{
			typeid:data.id,
			type:"goods"
		},
		dataType:"json",
		beforeSend:function(){
			loadingBox.show("修改中")
		},
		timeout:20000,
		success:function(root){
			if(root.flag = "1"){
				location.href = url;
			}else{errorBox.show(root.message);
			}
		}
	});
} 
//人人分享
function rr_shareClick(data) {
	var rrShareParam = {
		resourceUrl : data.resourceUrl,		//分享的资源Url
		srcUrl : data.srcUrl,				//分享的资源来源Url,默认为header中的Referer,如果分享失败可以调整此值为resourceUrl试试
		pic : data.pic,						//分享的主题图片Url
		title : data.title,					//分享的标题
		description : data.description 		//分享的详细描述
	};
	rrShareOnclick(rrShareParam);
}