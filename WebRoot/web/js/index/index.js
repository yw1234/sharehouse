$(document).ready(function(){
	$("#register_button").click(function(){
		location.href="p/register";
	});
	$("#email_text_tip").click(function(){
		$("#email_text_tip").css("visibility","hidden");
		$("#email").focus();
	});
	$("#password_text_tip").click(function(){
		$("#password_text_tip").css("visibility","hidden");
		$("#password").focus();
	});
	$("#email").focus(function(){
		$("#email_text_tip").css("visibility","hidden");
	}).keyup(function(e){
		emailTip.show(e);
	}).blur(function(){
		emailTip.confirm($(this));
	});
	
	$("#password").focus(function(){
		$("#password_text_tip").css("visibility","hidden");
	});
	$(".text").blur(function(){
		if($(this).val() == ""){
			$(this).parent().children(".text_tip").css("visibility","visible");
		}
	});
	emailTip.init();
	//幻灯
	count=$("#banner_list a").length;
	$("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt'));
	$("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")});
	$("#banner li").click(function() {
		var i = $(this).attr("loc") -1;
		n = i;
		if (i >= count) return;
		$("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
		$("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")});
		$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
		document.getElementById("banner").style.background="";
		$(this).toggleClass("on");
		$(this).siblings().removeAttr("class");
	});
	t = setInterval("showAuto()", 4000);
	$("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 4000);});
}).keypress(function(e){
	var event = e||window.event;
    if (event.keyCode == 13 && $("#itl_loc").val() == -1){
	    	login();
    }
});

function login(){
	var email_reg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/
		,email = $("#email").val()
		,pwd = $("#password").val()
		,u = url.getParams("url");
	if(email.trim() == ""){
		show_tip("error","请填写注册邮箱");
		$("#email").focus();
		return false;
	}else if(pwd == ""){
		show_tip("error","别忘了密码");
		$("#password").focus();
		return false;
	}
	else if(!email_reg.test(email)){
		show_tip("error","请输入正确的邮箱地址");
		$("#email").focus();
		return false;
	}else{
		var remind = $("#remindme").val();
		if(!$("#remindme").attr("checked"))
		{
			remind = "";
		}
		var lb = $("#login_button");
		$.ajax({
			url:"/login",
			type:"post",
			data:{
				user_email:$("#email").val(),
				user_password:$("#password").val(),
				remindme:remind,
				url:(u!=undefined)?u:""
			},
			dataType:"json",
			beforeSend:function(){
				lb.addClass("button_do");
				lb.html("登录中");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1") location.href=root.redirectURL;
				else{
					show_tip("error",root.message);
					lb.removeClass("button_do");
					lb.html("登录");
					return false;
				}
				
			}
		});
	}
}
function show_tip(id,text){
	var tipid = document.getElementById(id);
	tipid.style.visibility = "visible";
	tipid.innerHTML = text;
}
var t = n =0, count;
function showAuto()
{
	n = n >=(count -1) ?0 : ++n;
	$("#banner li").eq(n).trigger('click');
}
var emailTip = {
	init:function(){
		$(".input_tip_list").hover(
			function(){
				$(".itl_"+$("#itl_loc").val()).css({"background-color":"white","color":"black"});
				$("#itl_loc").val($(this).attr("class").split("_")[3]);
				$(this).css({"background-color":"#e68303","color":"white"});
			},
			function(){
				$(this).css({"background-color":"white","color":"black"});
				emailTip.removeSelect();
			}
		).each(function(index){
			$(this).addClass("itl_"+index);
		});
	},
	show:function(e){
		var $inputTip = $(".input_tip"),
			$inputTipList = $(".input_tip_list"),
			$email = $("#email"),
			$atIndex = $email.val().indexOf("@"),
			$loc = $("#itl_loc"),
			event = e||window.event;
	    if (event.keyCode == 13){
		    	if($("#itl_loc").val() != -1){
		    		emailTip.confirm($("#email"));
		    	}
		    	return false;}
	    else if(event.keyCode == 38){
	    		if($inputTip.css("display")!="none"){
    				emailTip.front($loc);
    				stopEvent(e);
	    		}
	    }
	    else if(event.keyCode == 40){
		    	if($inputTip.css("display")!="none"){
	    			emailTip.next($loc);
	    			stopEvent(e);
		    	}
	    }
		if($email.val().trim() != ""){
			$inputTip.show();
		}else {$inputTip.hide();return false;}
		
		if($atIndex==0){
			$inputTip.hide();
			return false;
		}else{
			$inputTipList.each(function(index){
				var emailStr = $email.val();
				if($atIndex == -1){
					$(this).html(emailStr+$(this).attr("m-data")).show();
				}else{
					var regx = emailStr.substr($atIndex),
						inputStr = emailStr.substr(0,$atIndex);
					if($(this).attr("m-data").indexOf(regx)==-1){
						$(this).hide();
					}else {	
						$(this).html(inputStr+$(this).attr("m-data")).show();}
				}
			});
		}
		
	},next:function(tar){
		var $loc = tar,tip_length = $(".input_tip_list").length;
		var locNow = Number($loc.val());
		locNow = (locNow < tip_length-1)?locNow+1:0;
		if($(".input_tip_list").eq(locNow).css("display")!="none"){
				emailTip.select(locNow);
		}else{				
			$loc.val(locNow);
			emailTip.next(tar);
		}
	},front:function(tar){
		var $loc = tar,tip_length = $(".input_tip_list").length;
		var locNow = Number($loc.val());
		locNow = (locNow > 0)?locNow-1:tip_length-1;
		if($(".input_tip_list").eq(locNow).css("display")!="none"){
			emailTip.select(locNow);
		}else{				
			$loc.val(locNow);
			emailTip.front(tar);
		}
	},
	hide:function(){
		$(".input_tip").hide();
		emailTip.removeSelect();
	},
	confirm:function(tar){
		var loc = $("#itl_loc").val();
		if(loc != -1){
			$("#email").val($(".itl_"+loc).html());
			tar.focus();
		}
		emailTip.hide();
	},
	removeSelect:function(){
		$("#itl_loc").val(-1);
		$(".input_tip_list").css({"background-color":"white","color":"black"});
	},
	select:function(loc){
		$(".input_tip_list").css({"background-color":"white","color":"black"});
		$(".itl_"+loc).css({"background-color":"#e68303","color":"white"});
		$("#itl_loc").val(loc);
	}
}
