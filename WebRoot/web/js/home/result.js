var addFriends = {
	init:function(data){
		var left = getLeft(data.btn[0])-(data.btn.width())+"px";
		var top = getTop(data.btn[0])+data.btn.height()+"px";
		$("#add_username").html(data.username);
		$("#add_userid_input").val(data.userid);
		$(".add_rex_block").css({"left":left,"top":top}).slideDown(200);
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
				reqMessage:text
			},
			dataType:"json",
			beforeSend:function(){
				$("#addfriends_loading").show();
			},
			complete:function(){
				$("#addfriends_loading").hide();
			},
			timeout:20000,
			success:function(message){
				if(message.flag == "1"){
					successBox.show("请求成功")
					$(".add_rex_block").slideUp(200);
					$(".rex_text").val("");
					$(".rex_text").blur();
				}else{
					errorBox.show("请求失败");
					return false;
				}
			}
		});
	},
	cancel:function(){
		$(".rex_text").val("");
		$(".rex_text").blur();
		$(".add_rex_block").slideUp(200);
	}
}

var searchUser = {
	changePage:function(data){
		
	}
}