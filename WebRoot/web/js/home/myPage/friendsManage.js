$(document).ready(function(){
	$("#fm_cancel").click(function(){
		$("#fm_box").slideUp(200);
	});
});

function showFmBox(data){
	$("#fm_box").hide();
	$("#fm_confirm").unbind();
	var type = data.type;
	if(type=="concerned"){
		html = "添加关注后,可在\"关注的人\"中及时看到<span style = 'color:#e68303;'>"+data.name+"</span>的动态。确认关注?";
		$("#fm_content").html(html);
		$("#fm_confirm").click(function(){
			addConcerned(data);
		});
	}else if(type=="addblacklist"){
		html = "确认将<span style = 'color:#e68303;'>"+data.name+"</span>拉入黑名单?";
		$("#fm_content").html(html);
		$("#fm_confirm").click(function(){
			updateBlacklist(data,"1");
		});
	}else if(type=="delete"){
		html = "确认从好友列表中删除<span style = 'color:#e68303;'>"+data.name+"</span>?";
		$("#fm_content").html(html);
		$("#fm_confirm").click(function(){
			deleteFriend(data);
		});
	}else if(type=="recover"){
		html = "确认将<span style = 'color:#e68303;'>"+data.name+"</span>从黑名单中清除?";
		$("#fm_content").html(html);
		$("#fm_confirm").click(function(){
			updateBlacklist(data,"0");
		});
	}else if(type=="cancel_con"){
		html = "不想关注<span style = 'color:#e68303;'>"+data.name+"</span>了吗?";
		$("#fm_content").html(html);
		$("#fm_confirm").click(function(){
			cancelConcerned(data);
		});
	}
	var top = getTop(data.btn)+16+"px";
	var left = getLeft(data.btn)-150+"px";
	$("#fm_box").css({left:left,top:top}).slideDown(200);
	
}

function cancelConcerned(data){
	$.ajax({
		url:"updateconcernedFriends",
		type:"post",
		data:{
			id:data.id
		},
		dataType:"text",
		beforeSend:function(){
			$("#fm_loading").show();
		},
		complete:function(){
			$("#fm_box").slideUp(200);
			$("#fm_loading").hide();
			$("#fm_success").show();
			setTimeout(function(){$("#fm_success").fadeOut()},800);
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				$("#concerned_btn_"+data.id).html("关注");
				$("#concerned_btn_"+data.id).unbind().click(function(){showFmBox({btn:data.btn,type:'concerned',id:data.id,name:data.name});});
			}else alert("操作失败,请稍后重试");
		}
	});
}

function addConcerned(data){
	$.ajax({
		url:"addconcerned",
		type:"post",
		data:{
			id:data.id
		},
		dataType:"text",
		beforeSend:function(){
			$("#fm_loading").show();
		},
		complete:function(){
			$("#fm_box").slideUp(200);
			$("#fm_loading").hide();
			$("#fm_success").show();
			setTimeout(function(){$("#fm_success").fadeOut()},800);
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				$("#concerned_btn_"+data.id).html("取消关注");
				$("#concerned_btn_"+data.id).unbind().click(function(){showFmBox({btn:data.btn,type:'cancel_con',id:data.id,name:data.name});});
			}else alert("操作失败,请稍后重试");
		}
	});
}

function deleteFriend(data){
	$.ajax({
		url:"/deletefriends",
		type:"post",
		data:{
			id:data.id
		},
		dataType:"text",
		beforeSend:function(){
			$("#fm_loading").show();
		},
		complete:function(){
			$("#fm_box").slideUp(200);
			$("#fm_loading").hide();
			$("#fm_success").show();
			setTimeout(function(){$("#fm_success").fadeOut()},800);
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				$("#commonfriends_"+data.id).hide(100);
			}else alert("操作失败,请稍后重试");
		}
	});
}

function updateBlacklist(data,flag){
	$.ajax({
		url:"/updateblacklist",
		type:"post",
		data:{
			id:data.id,
			flag:flag
		},
		dataType:"text",
		beforeSend:function(){
			$("#fm_loading").show();
		},
		complete:function(){
			$("#fm_box").slideUp(200);
			$("#fm_loading").hide();
			$("#fm_success").show();
			setTimeout(function(){$("#fm_success").fadeOut()},800);
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				if(flag=="1")
					$("#commonfriends_"+data.id).fadeOut(100);
				else $("#blackfriends_"+data.id).fadeOut(100);
			}else alert("操作失败,请稍后重试");
		}
	});
}