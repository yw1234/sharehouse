$(document).ready(function(){
	
	document.getElementById("search_province").options[0] = new Option("请选择","");
	document.getElementById("search_city").options[0] = new Option("请选择","");
	
	$("#search_province").change(function(){
		if($(this).val()!="")
			getActList.city({id:"search_city",province:$(this).val()});
	});
	getActList.province({id:"search_province"});	
	$("#submit_btn").click(function(){
		var search_realname = $("#search_realname").val().trim()
			,search_sex = $("#search_sex").val().trim()
			,search_school = $("#search_school").val().trim()
			,search_province = $("#search_province").val().trim();
		if(search_realname=="" && search_sex=="" && search_school=="" && search_province==""){errorBox.show("至少得填写一项吧。。");return false;}
			$("#search_form").submit();
	});
	
	$(".sb_choose_province").hover(function(){if($(this).attr("choose")!="true")$(this).css({"background":"#e68303","color":"white"})},function(){if($(this).attr("choose")!="true")$(this).css({"background":"white","color":"#e68303"});}).click(function(){
		schoolBox.selProvince({pro:$(this).html(),btn:$(this)});
	});
	schoolBox.init();
});

var addFriends = {
	init:function(data){
		var left = getLeft(data.btn[0])-(data.btn.width())+"px";
		var top = getTop(data.btn[0])+(data.btn.height())+"px";
		$("#add_username").html(data.username);
		$("#add_userid_input").val(data.userid);
		$(".add_rex_block").css({"left":left,"top":top}).slideDown(200);
	},
	confirm:function(){
		var text = $(".rex_text").val();
		if(text == $(".rex_text").attr("TextDefault")){
			text = "";
		}
		if(text.length>140) {errorBox.show("字数有点多哦~");return false;}
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
					$(".add_rex_block").slideUp(200);
					successBox.show("请求发送成功")
					$(".rex_text").val("");
					$(".rex_text").blur();
				}else{
					errorBox.show(root.message)
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

var schoolBox={
	init:function(){
		schoolBox.selProvince({pro:'北京',btn:$(".sb_choose_province:first")});
	},
	show:function(){
		var st = document.body.scrollTop+document.documentElement.scrollTop+50+"px";
		var sl =($("html").width()-600)/2+"px";
		lock.show();
		$("#school_box").css({"top":st,"left":sl}).show();
	},
	comfirmSelect:function(data){
		$("#search_school").val(data.name);
		schoolBox.hide();
	},
	selProvince:function(data){
		var btn = data.btn;
		$.ajax({
			url:"/common/getschoolList",
			type:"post",
			data:{
				province:schoolBox.changePro(data.pro)
			},
			dataType:"json",
			beforeSend:function(){
				$(".sb_choose_province").css({"background":"white","color":"#e68303"}).attr("choose","false");
			},
			complete:function(){
				btn.css({"background":"#e68303","color":"white"}).attr("choose","true");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					var text = "";
					if(root.schoollist!="")
						text = root.schoollist;
					else text = "<div style='width:550px;margin-top:40px;font-family:\"黑体\",Arial;text-align:center;'>该地区高校暂未开放注册</div>";
					$("#school_box_main").html(text);
					$(".sb_school_list").hover(function(){$(this).css({"background":"#e68303","color":"white"})},function(){$(this).css({"background":"white","color":"#e68303"});});
				}else{
					errorBox.show("数据获取失败,请稍后再试");
					return false;
				}
			}
		});
	},
	hide:function(){
		lock.hide();
		$("#school_box").hide();
	},
	changePro:function(pro){
		pro=pro.trim();
		if(pro=="北京"||pro=="上海"||pro=="重庆"||pro=="天津") return pro+"市";
		else if(pro=="内蒙古"||pro=="西藏") return pro+"自治区";
		else if(pro=="广西") return pro+"壮族自治区"; 
		else if(pro=="新疆") return pro+"维吾尔自治区"; 
		else if(pro=="宁夏") return pro+"回族自治区";
		else if(pro=="香港"||pro=="澳门"||pro=="台湾") return pro;
		else return pro+"省"; 
	}
}

var friends = {
	mayKnow:function(data){
		var pn = (data.pn!=null)?data.pn:Number($("#pageno").val());
		$.ajax({
			url:"/getmayKnowUser",
			type:"post",
			data:{pageno:pn},
			dataType:"json",
			beforeSend:function(){
				$("#sf_more_btn").hide();
				$("#sf_loading").show();
			},
			complete:function(){
				$("#sf_loading").hide();		
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#pageno").val(pn+1);
					$("#show_friends").append(root.data);
					if(root.data=="" && pn == 0){
						$("#sf_notFound").show();
					}			
					if(pn<3 && Number(root.mkuNum)==root.size){
						$("#sf_more_btn").show();
					}else $("#sf_more_btn").hide();
				}else{
					errorBox.show("数据获取失败,请稍后再试");
					return false;
				}
			}
		});
	},
	mine:function(data){
		var pn = (data.pn!=null)?data.pn:Number($("#pageno").val()),
			count = Number($("#friends_count").html());
		if(data.opt != null){
			if(data.opt == "next") {
				pn += 1;
			}
			if(data.opt == "front") {
				pn -= 1;
			}
		}
		$.ajax({
			url:"/getfriends",
			type:"post",
			data:{pageno:pn,count:count},
			dataType:"json",
			beforeSend:function(){
				$("#sf_loading").show();
			},
			complete:function(){	
				$("#sf_loading").hide();		
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#show_friends").html(root.data);
					$("#pageno").val(root.pageno);
					if(root.hasNext == "1")
						$("#show_friends_next").show();
					else $("#show_friends_next").hide();
					if(root.hasFront == "1")
						$("#show_friends_front").show();
					else $("#show_friends_front").hide();
				}else{
					errorBox.show("数据获取失败,请稍后再试");
					return false;
				}
			}
		});
	}
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
			data.target.fadeOut(200);
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				successBox.show("该好友已被移除");
			}else {
				errorBox.show("操作失败,请稍后再试");
			}
		}
	});
}