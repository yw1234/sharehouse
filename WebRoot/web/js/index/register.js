$(document).ready(function(){
	if(!TextDefault._support){$(".pwd_field").removeAttr("TextDefault").val("");$("#password_placeholder").html("6-20个字符以内");$("#repassword_placeholder").html("再次输入密码");}
	$("#email,#password,#name,#checkcode").val("").blur();
	$("#register_button").hover(function(){$(this).attr("src",p+"/web/image/register/register_button_mouseover.png");},function(){$(this).attr("src",p+"/web/image/register/register_button.png");});
	$("#af_success_button").hover(function(){$(this).attr("src",p+"/web/image/register/success_button_mouseover.png");},function(){$(this).attr("src",p+"/web/image/register/success_button.png");});
	$(".text_tip").click(function(){
		var id = $(this).attr("id");
		if(id == "repassword_placeholder"){
			$("#repassword_placeholder").css("visibility","hidden");
			$("#repassword").focus();
		}else if(id == "password_placeholder"){
			$("#password_placeholder").css("visibility","hidden");
			$("#password").focus();
		}
	});
	$("#password").focus(function(){
		$("#password_placeholder").css("visibility","hidden");
	});
	$("#repassword").focus(function(){
		$("#repassword_placeholder").css("visibility","hidden");
	});	
	$("#email").keyup(function(e){
		emailTip.show(e);
	}).blur(
		 function(){
			 emailTip.confirm($(this));
			 var email = $('#email').val();
			 var email_reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			 if(email != "" && email != $('#email').attr("TextDefault")){
				 if(!email_reg.test(email)){
					 show_tip("email","邮箱格式不正确");
				 }
				 else{
				 $("#email_checking").show();
				  $.post(
				      p+"/RegisterValidate_email",  
				      {email:email},
				      	function(message)
						{
				    	  $("#email_checking").hide();
							message = decodeURI(message);
							show_tip("email",message);
						}
				  	);
				 }
			 }else{
				 $("#validate_email").val("0");
				 $("#email_tip").hide(); 
				 return false;
			 }
		 }
	);
	$("#password").blur(function(){
		var pwd = $("#password").val();
		var repwd = $("#repassword").val();
		if(pwd.trim() != ""){
			if(pwd.length < 6){
				show_tip("password","密码长度:6-25位");
				$("#validate_password").val("0");
			}else{
				if(pwd.trim()!=repwd.trim() && repwd.trim()!=""){
					show_tip("repassword","两次输入不一致");
					$("#validate_repassword").val("0");
				}else if(pwd.trim() == repwd.trim() && repwd.trim()!=""){
					show_tip("repassword","ok");
					$("#validate_repassword").val("1");
				}
				show_tip("password","ok");
				$("#validate_password").val("1");
			}
		}else{
			$("#password_placeholder").css("visibility","visible");
			$("#password_tip").hide();
		}
	});
	$("#repassword").blur(function(){
		var repwd = $("#repassword").val();
		var pwd = $("#password").val();
		if(repwd.trim() != ""){
			if(repwd!=pwd){
				show_tip("repassword","两次输入不一致");
				$("#validate_repassword").val("0");
			}else{
				show_tip("repassword","ok");
				$("#validate_repassword").val("1");
			}
		}else{
			$("#repassword_placeholder").css("visibility","visible");
			$("#repassword_tip").hide();
		}
	});
	$("#name").blur(
		 function(){
			 var name = $('#name').val();
			 if(name != "" && name != $("#name").attr("TextDefault")){
				$("#name_checking").show();
			  $.post(
					 p+"/RegisterValidate_name",
					{name:name},
					function(message)
					{
						$("#name_checking").hide();
						message = decodeURIComponent(message);
						show_tip("name",message);
					}
				);
			 }else{$("#validate_name").val("0");$("#name_tip").hide();return false;}
	});
	$("#checkcode").keyup(
		 function(){
			 var checkcode = $('#checkcode').val(),
			 	cclength = 5;
			 if(checkcode.length == cclength && checkcode != $("#checkcode").attr("TextDefault")){
			  $.post(
					  p+"/RegisterValidate_checkcode",
					{checkcode:$('#checkcode').val()},
					function(message)
					{
						message = decodeURI(message);
						show_tip("checkcode",message);
					}
				);}
			 else{$("#validate_checkcode").val("0");$("#checkcode_tip").hide();return false;}
		 }
	);
	$("#register_button").click(function(){
		register();
	});
	emailTip.init();
}).keypress(function(e){
	var event = e||window.event;
	if (event.keyCode == 13 && $("#itl_loc").val() == -1){
	    register();
    }
});

function register(){
	var $email = $("#email"),
		$password = $("#password"),
		$repassword = $("#repassword"),
		$name = $("#name"),
		$checkcode = $("#checkcode"),
		flag = "1";
	if($email.val().trim() == $email.attr("TextDefault")|| $("#validate_email").val() == "0"){
		show_tip("email","注册邮箱还没填好哦");
    		return false;
	}if($password.val().trim() == ""){
		show_tip("password","密码还没填好哦");
    		return false;
	}if($repassword.val().trim() == ""){
		show_tip("repassword","确认一下密码");
		return false;
	}
	if($name.val().trim() == $name.attr("TextDefault") || $("#validate_name").val() == "0"){
		show_tip("name","名字?");
		return false;
    }
	if($checkcode.val().trim() == $checkcode.attr("TextDefault") || $("#validate_checkcode").val() == "0"){
		show_tip("checkcode","输错了哦");
		return false;
    }
	$(".validate").each(function(){
		if($(this).val().trim() == "0"){
			flag = "0";
			return false;
		 }
	 }); 
	if(flag == "1")
	 $.ajax({
		url:p+"/RegisterValidate_main",
		type:"post",
		data:{
			email:$email.val(),password:$password.val(),name:$name.val(),sex:$(":radio:checked").val()
		},
		dataType:"json",
		beforeSend:function(){
			$("#register_button").unbind().attr("src",p+"/web/image/register/register_button_loading.png");
		},
		timeout:20000,
		success:function(root){
			if(root.flag == "1")
			{
				location.href = p+"/fill_info";	
			}else{
				alert("注册失败,请稍后再试");
			}
		}
	 });
	else return false;
}
function getCheckCode()
{
	$("#checkcode").val("");
	document.getElementById("createCheckCode").src = p+"/CheckCode?nocache="+new Date().getTime();
}

function show_tip(id,text){
	if(text.match("ok")){
		text = "<img src = '"+p+"/web/image/register/ok.png' style = 'float:left;'/>";
		document.getElementById("validate_"+id).value = "1";
	}else document.getElementById("validate_"+id).value = "0";
	document.getElementById(id+"_tip").style.display = "block";
	document.getElementById(id+"_tip_info").innerHTML = text;
}

var emailTip = {
	init:function(){
		//下拉框相关
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
	    if (event.keyCode == 13 && $("#itl_loc").val() != -1) {
    			emailTip.confirm($("#email"));
    			return false;
	    	}
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
var p = PATH;