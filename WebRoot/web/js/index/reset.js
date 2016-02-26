var reset = {
	sendMail:function(){
		var $email = $("#email");
		var email_reg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if($email.val().trim() == ""){
			errorTip.show("请输入注册邮箱")
			$email.focus();
			return false;
		}
		else if(!email_reg.test($email.val())){
			errorTip.show("请输入正确的邮箱地址");
			$email.focus();
			return false;
		}
		$.ajax({
			url:"/pwd_reset",
			type:"post",
			data:{
				email:$email.val(),
				step:"1"
			},
			dataType:"json",
			beforeSend:function(){
				$("#send_reset_mail").addClass("button_do").unbind("click").html("发送中...");
			},
			complete:function(){
			},
			timeout:20000,
			success:function(root){
				errorTip.show(root.message);
				$email.val("");
				if(root.flag == "1"){					
					$("#send_reset_mail").html("已发送");
				}else{
					$("#send_reset_mail").removeClass("button_do").bind("click",function(){reset.sendMail();}).html("发送重置邮件");
					return false;
				}
			}
		});
	},confirm:function(){
		var $pwd = $("#password");
		var $re_pwd = $("#re_password");
		if($pwd.val().trim() == ""){
			errorTip.show("请输入密码");
			$pwd.focus();
			return false;
		}
		if($re_pwd.val().trim() == ""){
			errorTip.show("请确认重复密码");
			$re_pwd.focus();
			return false;
		}
		if($pwd.val().trim()!=$re_pwd.val().trim()){
			errorTip.show("两次密码输入不一致,请重新输入");
			return false;
		}
		$("#reset_form").submit();
	}
}

var errorTip = {
	show:function(msg){
		$("#error_tip").html(msg);
	},
	clear:function(){
		$("#error_tip").html("");
	}
}

$(document).ready(function(){
	$(".reset_btn").hover(function(){$(this).css({background:"#edb43b"})},function(){$(this).css({background:"#df9a06"})});
	$("#send_reset_mail").bind("click",function(){
		reset.sendMail();
	});
	$("#reset_confirm").bind("click",function(){
		reset.confirm();
	});
});