var newsPage = {
	load:function(){
		var hash = location.hash;
		if(hash==null||hash=="")
		{
			var tar;
			$(".msg_count").each(function(index){
				if($(this).val()!="0"){
					tar = $(this).attr("target");
					return false;
				}
			});
			switch(tar){
				case "at":{this.getUnReadAtPage({btn:$("#at_btn"),pn:0});break;}
				case "comment":{this.getUnReadCommentPage({btn:$("#comm_btn"),pn:0});break;}
				case "private":{this.getUnReadPrivatePage({btn:$("#pri_btn"),pn:0});break;}
				case "request":{this.getUnReadReguestPage({btn:$("#req_btn"),pn:0});break;}
				case "notice":{this.getUnReadNoticePage({btn:$("#notice_btn"),pn:0});break;}
				default :{this.getUnReadPrivatePage({btn:$("#pri_btn"),pn:0});break;}
			}
		}else{
			this.getUnReadPrivatePage({btn:$("#pri_btn"),pn:0});
		}
	},
	getUnReadReguestPage:function(data){
		location.hash = "request";
		var req_view = $("#req_news_view");
		$(".msg_nav_btn").removeClass("checked");
		data.btn.children("li").addClass("checked");
		if(req_view.html().trim()==""){
			$.ajax({
				url:"/message/getUnReadRequest",
				type:"post",
				data:{pageno:data.pn},
				dataType:"json",
				beforeSend:function(){
					$("#msg_loading").show();
				},
				complete:function(){
					$("#msg_loading").hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						req_view.append(root.data);
						$(".message_block").messageFocus();
					}else errorBox.show(root.message);
				},error:function(){
					errorBox.show("获取失败,请稍后再试");
				}
			});
		}
		$(".msg_news_view").hide();
		req_view.show();
	},
	getUnReadPrivatePage:function(data){
		location.hash = "private";
		var pri_view = $("#pri_news_view");
		$(".msg_nav_btn").removeClass("checked");
		data.btn.children("li").addClass("checked");
		if(pri_view.html().trim()==""){
			$.ajax({
				url:"/message/getUnReadPrivate",
				type:"post",
				data:{pageno:data.pn},
				dataType:"json",
				beforeSend:function(){
					$("#msg_loading").show();
				},
				complete:function(){
					$("#msg_loading").hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						pri_view.append(root.data);
						$(".message_block").messageFocus();
					}else errorBox.show(root.message);
				},error:function(){
					errorBox.show("获取失败,请稍后再试");
				}
			});
		}
		$(".msg_news_view").hide();
		pri_view.show();
	},
	getUnReadCommentPage:function(data){
		location.hash = "comment";
		var comm_view = $("#comm_news_view");
		$(".msg_nav_btn").removeClass("checked");
		data.btn.children("li").addClass("checked");
		if(comm_view.html().trim()==""){
			$.ajax({
				url:"/message/getUnReadComment",
				type:"post",
				data:{pageno:data.pn},
				dataType:"json",
				beforeSend:function(){
					$("#msg_loading").show();
				},
				complete:function(){
					$("#msg_loading").hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						comm_view.append(root.data);
						$(".message_block").messageFocus();
					}else errorBox.show(root.message);
				},error:function(){
					errorBox.show("获取失败,请稍后再试");
				}
			});
		}
		$(".msg_news_view").hide();
		comm_view.show();
	},
	getUnReadNoticePage:function(data){
		location.hash = "notice";
		var no_view = $("#notice_news_view");
		$(".msg_nav_btn").removeClass("checked");
		data.btn.children("li").addClass("checked");
		if(no_view.html().trim()==""){
			$.ajax({
				url:"/message/getUnReadNotice",
				type:"post",
				data:{pageno:data.pn},
				dataType:"json",
				beforeSend:function(){
					$("#msg_loading").show();
				},
				complete:function(){
					$("#msg_loading").hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						no_view.append(root.data);
						$(".message_block").messageFocus();
					}else errorBox.show(root.message)
				},error:function(){
					errorBox.show("获取失败,请稍后再试");
				}
			});
		}
		$(".msg_news_view").hide();
		no_view.show();
	},
	getUnReadAtPage:function(data){
		location.hash = "at";
		var at_view = $("#at_news_view");
		$(".msg_nav_btn").removeClass("checked");
		data.btn.children("li").addClass("checked");
		if(at_view.html().trim()==""){
			$.ajax({
				url:"/message/getUnReadAt",
				type:"post",
				data:{pageno:data.pn},
				dataType:"json",
				beforeSend:function(){
					$("#msg_loading").show();
				},
				complete:function(){
					$("#msg_loading").hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						at_view.append(root.data);
					}else errorBox.show(root.message)
				},error:function(){
					errorBox.show("获取失败,请稍后再试");
				}
			});
		}
		$(".msg_news_view").hide();
		at_view.show();
	}
}

//获取更多未读信息
var getMoreUnReadMessage = {
	request:function(data){
		$.ajax({
			url:"/message/getUnReadRequest",
			type:"post",
			dataType:"json",
			beforeSend:function(){
				$("#req_getMore").html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				$("#req_getMore").remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#req_news_view").append(root.data);
					$(".message_block").messageFocus();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("获取失败,请稍后再试");
			}
		});
	},
	privateMsg:function(data){
		$.ajax({
			url:"/message/getUnReadPrivate",
			type:"post",
			dataType:"json",
			beforeSend:function(){
				$("#pri_getMore").html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				$("#pri_getMore").remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#pri_news_view").append(root.data);
					$(".message_block").messageFocus();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("获取失败,请稍后再试");
			}
		});
	},
	notice:function(data){
		$.ajax({
			url:"/message/getUnReadNotice",
			type:"post",
			dataType:"json",
			beforeSend:function(){
				$("#no_getMore").html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				$("#no_getMore").remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#notice_news_view").append(root.data);
					$(".message_block").messageFocus();
				}else errorBox.show(root.message)
			},error:function(){
				errorBox.show("获取失败,请稍后再试");
			}
		});
	},
	comment:function(data){
		$.ajax({
			url:"/message/getUnReadComment",
			type:"post",
			dataType:"json",
			beforeSend:function(){
				$("#comm_getMore").html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				$("#comm_getMore").remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#comm_news_view").append(root.data);
					$(".message_block").messageFocus();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("获取失败,请稍后再试");
			}
		});
	},
	at:function(data){
		$.ajax({
			url:"/message/getUnReadAt",
			type:"post",
			dataType:"json",
			beforeSend:function(){
				$("#at_getMore").html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				$("#at_getMore").remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#at_news_view").append(root.data);
					//$(".message_block").messageFocus();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("获取失败,请稍后再试");
			}
		});
	}
}

//请求类处理
var request = {
	accept:function(data){
		if(data.reqType=="addFriends"){
			request.accAddFriend({id:data.reqid,userid:data.userid});
			$("#req_"+data.reqid).slideUp();
		}
		else{
			$.ajax({
				url:"/message/shareConfirm",
				type:"post",
				data:{id:data.reqid},
				dataType:"json",
				beforeSend:function(){
					loadingBox.show("分享信息读取中");
				},
				complete:function(){
					loadingBox.hide();
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						$.extend(root,data);
						requestBox.show(root);
					}else errorBox.show(root.message);
				},error:function(){
					errorBox.show("操作失败,请稍后再试");
					return false;
				}
			});
		}
	},
	ignore:function(data){
		$.ajax({
			url:"/message/deleteRequest",
			type:"post",
			data:{id:data.reqid},
			dataType:"json",
			beforeSend:function(){
				$("#req_del_loading_"+data.reqid).show();
			},
			complete:function(){
				$("#req_del_loading_"+data.reqid).hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#req_"+data.reqid).slideUp();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("操作失败,请稍后再试");
			}
		});
	},
	accSubmit:function(data){
		var text = $("#msg_request_text").val(),
			phone = $("#msg_request_phone").val(),
			price = $("#msg_request_price").val(),
			showPhone = ($("#msg_request_showContact").attr("checked"))?"1":"0",
			delivery = $("#msg_request_delivery").html(),
			isShared = ($("#msg_request_flag").attr("checked"))?"1":"0";
		if(text == $("#msg_request_text").attr("TextDefault") || text.trim() == ""){text = "";}
		if(!textLimit.validation({target_text:$("#msg_request_text")})){
			errorBox.show("字数有点多哦~");
			return false;
		}
		$.ajax({
			url:"/message/acceptRequest",
			type:"post",
			data:{userid:data.userid,id:data.id,content:text,phone:phone,showContact:showPhone,price:price,tranType:delivery,shareFlag:isShared},
			dataType:"json",
			beforeSend:function(){
				$("#req_loading").show();
			},
			complete:function(){
				$("#req_loading").hide();
				requestBox.hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show(root.message,2000);
					$("#req_opt").html("<span style = 'float:left;color:#c8c8c8;font-size:14px;margin-right:20px;'>(已处理)</span>");
					$("#req_opt").append("<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'orderBox.load({idleid:"+root.idleid+"});'>分享单</a>");
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("发送失败,请稍后再试");
			}
		});
	},
	accAddFriend:function(data){
		$.ajax({
			url:"/message/acceptRequest",
			type:"post",
			data:{userid:data.userid,id:data.id},
			dataType:"json",
			beforeSend:function(){
				$("#req_loading").show();
			},
			complete:function(){
				$("#req_loading").hide();
				requestBox.hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show(root.message);
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("发送失败,请稍后再试");
			}
		});
	}
}

var requestBox = {
	show:function(data){
		lock.show();
		$("#req_box_container").html(data.reqBox);
		getCenterPlace("msg_request_box");
		$("#msg_request_box").show();
	},
	submit:function(){
		var reqid = $("#reqid").val();
		var senderid = $("#req_senderid").val();
		request.accSubmit({id:reqid,userid:senderid});
	},
	cancel:function(){
		requestBox.hide();
	},
	hide:function(){
		$("#req_box_container").html("");
		lock.hide();
	}
}

//私信处理
var privateMsg = {
	ignore:function(data){
		$.ajax({
			url:"/message/hidePrivate",
			type:"post",
			data:{id:data.priid},
			dataType:"json",
			beforeSend:function(){
				$("#pri_del_loading_"+data.priid).show();
			},
			complete:function(){
				$("#pri_del_loading_"+data.priid).hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#pri_"+data.priid).slideUp();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("操作失败,请稍后再试");
			}
		});
	}
}

//消息处理
var notice = {
	ignore:function(data){
		$.ajax({
			url:"/message/deleteNotice",
			type:"post",
			data:{id:data.noid},
			dataType:"json",
			beforeSend:function(){
				$("#notice_del_loading_"+data.noid).show();
			},
			complete:function(){
				$("#notice_del_loading_"+data.noid).hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#notice_"+data.noid).slideUp();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("操作失败,请稍后再试");
			}
		});
	}
}

//屏蔽信息
var shield = {
	user:function(data){
		var target = data.target;
		$.ajax({
			url:"/addshield",
			type:"post",
			data:{id:data.userid},
			dataType:"json",
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("屏蔽成功");
					if(target[0])
						target.slideUp(200);
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("请求超时,请稍后再试");
			}
		});
	},
	commented:function(data){
		var target = data.target;
		$.ajax({
			url:"/message/hideCommented",
			type:"post",
			data:{commShareId:data.id,commType:data.type},
			dataType:"json",
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("屏蔽成功");
					if(target[0])
						target.hide();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("请求超时,请稍后再试");
			}
		});
	}
}
//at
var atMessage = {
	ignore:function(data){
		$.ajax({
			url:"/message/deleteAt",
			type:"post",
			data:{id:data.atid},
			dataType:"json",
			beforeSend:function(){
				$("#at_del_loading_"+data.atid).show();
			},
			complete:function(){
				$("#at_del_loading_"+data.atid).hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#at_"+data.atid).slideUp();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("操作失败,请稍后再试");
			}
		});
	}
}

//分享单
var orderBox = {
	load:function(data){
		$.ajax({
			url:"/message/getOrder",
			type:"post",
			data:{id:data.idleid},
			dataType:"json",
			beforeSend:function(){
				loadingBox.show("分享单读取中");
			},
			complete:function(){
				loadingBox.hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#order_container").append(root.box);
					orderBox.show();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("请求超时,请稍后再试");
			}
		});
	},
	show:function(){
		lock.show();
		var st = document.body.scrollTop+document.documentElement.scrollTop+90+"px";
		var sl =($("html").width()-480)/2+"px";
		$("#order_box").css({"top":st,"left":sl}).show();
	},
	hide:function(){
		lock.hide();
		$("#order_box").remove();
	},
	confirmTran:function(data){
		var $confirmBtn = $("#order_confirm_btn");
		$.ajax({
			url:"/message/confirmOrder",
			type:"post",
			data:{id:data.idleid},
			dataType:"json",
			beforeSend:function(){
				$confirmBtn.addClass("button_do").html("处理中");
			},
			complete:function(){
				$confirmBtn.removeClass("button_do").html("确认收货");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					var $block = $confirmBtn.parent();
					$confirmBtn.remove();
					$block.html("<span class = 'order_box_info' style = 'float:left;width:100%;text-align:left;color:#e68303;'>已完成</span>");
					successBox.show("操作成功");
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("请求超时,请稍后再试");
			}
		});
	}
}

var commentReply = {
	show:function(data){
		if($("#comm_reply_box")[0])
			$("#comm_reply_box").remove();
		var rbox = '<div id = "comm_reply_box" style = "float:left;width:100%;display:none;">'
				+'	<textarea id = "comm_reply_text" style = "float:left;width:350px;height:35px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;" onkeyup="textLimit.getLength({id:\'comm_reply_textlimit\',target_text:$(this)});"></textarea>'
				+'	<div style="float:left;width:50px;">'
				+'		<div class = "facelist face-switch" id = "comm_reply_facelist" onclick = "getFaceList(\'comm_reply_facelist\',$(\'#comm_reply_text\'),\'comm_reply_textlimit\');"><img src = "/web/image/base/face.png" style = "float:left;"/></div>'
				+'		<div id = "comm_reply_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:5px;">0/140</div>'
				+'	</div>'
				+'	<div style = "float:left;width:50px;margin-left:15px;">'
				+'		<a id = "comm_reply_btn" href = "javascript:void:(0)" class = "button" style = "float:left;margin-top:2px;width:50px;height:42px;line-height:43px;border-radius:3px;" onclick = "commentReply.submit({target:$(\'#comm_reply_container_'+data.id+'\'),userid:'+data.userid+',type:\''+data.type+'\',typeid:'+data.typeid+',share:\''+data.share+'\'});">回复</a>'
				+'	</div>'
				+'	<div style = "float:left;width:100%;height:10px;">'
				+'	</div>'
				+'</div>';
		data.target.html(rbox);
		$("#comm_reply_box").slideDown(200);
		$("#comm_reply_text").focus().val("To"+data.userName+":");
	},
	submit:function(data){
		var target_text = $("#comm_reply_text")
			,userid = data.userid
			,typeid = data.typeid
			,type = data.type
			,share = data.share;
		if(target_text.val() == "" || target_text.val() == target_text.attr("TextDefault")){
			errorBox.show("起码得说句话吧==||");
			return false;
		}
		if(!textLimit.validation({target_text:target_text})){errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/message/sendComment",
			type:"post",
			data:{
				userid:userid,
				commType:type,
				commShareId:typeid,
				commShare:share,
				content:target_text.val()
			},
			dataType:"json",
			beforeSend:function(){
				$("#comm_reply_btn").addClass("button_do").html("回复中");
			},
			complete:function(){
				$("#comm_reply_btn").removeClass("button_do").html("回复");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					commentReply.hide();
					successBox.show("留言回复成功")
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},
	hide:function(){
		$("#comm_reply_box").slideUp(200);
		$("#comm_reply_text").val("");
		textLimit.reset({id:"comm_reply_textlimit"});
	},
	getMore:function(data){
		var target = data.target;
		$.ajax({
			url:"/message/matterComment",
			type:"post",
			data:{
				commType:data.type,
				commShareId:data.typeid,
				pageno:data.pn
			},
			dataType:"json",
			beforeSend:function(){
				target.html("<img src = '/web/image/register/loading.gif'/>");
			},
			complete:function(){
				target.remove();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					if(data.pn == 0){
						data.tb.html(root.data);
					}else data.tb.append(root.data);
				}else{
					errorBox.show(root.message);
				}
			}
		});
	}
}
$.fn.messageFocus = function(){
	$(this).unbind().mouseenter(function(){
		$(this).css({"background":"#f1f1f1"}).find($('.message_opt')).show();
	}).mouseleave(function(){
		$(this).css({"background":"#ffffff"}).find($('.message_opt')).hide();
	});
}
$(document).ready(function(){
	$(".message_block").messageFocus();
});