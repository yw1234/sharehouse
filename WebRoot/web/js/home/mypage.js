function sendPrivateMessage(data){
	var text = data.target_text.val();
	if(text==data.target_text.attr("TextDefault")||text.trim()=="") {errorBox.show("木有要说的吗?");return false;}
	if(!textLimit.validation({target_text:data.target_text})) {
		errorBox.show("字数有点多哦~");
		return false;
	}
	$.ajax({
		url:"/message/sendPrivate",
		type:"post",
		data:{
			userid:data.userid,
			content:text,
			isPublic:($("#pl_private").attr("checked")=="checked")?"0":"1"
		},
		dataType:"json",
		beforeSend:function(){
			data.btn.addClass("button_do").html("发送中");
		},
		complete:function(){
			data.btn.removeClass("button_do").html("留言");
			data.target_text.val("").blur();
		},
		timeout:20000,
		success:function(root){
			if(root.flag=="1"){
				successBox.show("留言成功");
			}else{
				errorBox.show(root.message);
			}
		},error:function(){
			errorBox.show("发布失败,请稍后再试");
		}
	});
}

var addFriends = {
	init:function(data){
		var left = getLeft(data.btn[0])-110+"px";
		var top = getTop(data.btn[0])+data.btn.height()+"px";
		$("#add_username").html(data.username);
		$("#add_userid_input").val(data.userid);
		$(".add_rex_block").css({"left":left,"top":top}).slideDown(100);
	},
	confirm:function(){
		var text = $(".rex_text").val();
		if(text == $(".rex_text").attr("TextDefault")){
			text = "";
		}
		$.ajax({
			url:"/message/sendRequest",
			type:"post",
			data:{
				userid:$("#add_userid_input").val(),
				reqType:"addFriends",
				content:text
			},
			dataType:"json",
			beforeSend:function(){
				$("#addfriends_loading").show();
			},
			complete:function(){
				$("#addfriends_loading").hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("请求成功")
					$(".add_rex_block").slideUp(100);
					$(".rex_text").val("");
					$(".rex_text").blur();
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	},
	cancel:function(){
		$(".rex_text").val("");
		$(".rex_text").blur();
		$(".add_rex_block").slideUp(100);
	}
}


function addFriendsRequest(data){
	$.ajax({
		url:"/message/sendRequest",
		type:"post",
		data:{
			userid:data.userid,
			reqType:"addFriends",
		},
		dataType:"json",
		timeout:20000,
		success:function(root){
			if(root.flag == "1"){
				successBox.show("请求成功,请等待回复")
			}else{
				errorBox.show(root.message)
				return false;
			}
		},error:function(){
			errorBox.show("请求失败,请稍后再试")
		}
	});
}