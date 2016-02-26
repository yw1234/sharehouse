$(document).ready(function(){
	if($("#privacy_pShowUser_input").val()=="on")
		$("#pi_showuser").attr("checked","checked");
	if($("#privacy_pShowCircle_input").val()=="on")
		$("#pi_showcircle").attr("checked","checked");
	$("#privacy_showname").change(function(){
		if($(this).val()==1){
			if($("#privacy_nickname").val()==null || $("#privacy_nickname").val()==""){
				$("#set_nickname").slideDown(100);
			}
		}
	});
	$("#saveinfo").click(function(){
		$.post(
			'/updateprivacy', 
			$("#privacy_form").serialize(), 
			function(message){
				if(message.match("ok"))
					successBox.show("保存成功");
				else errorBox.show("保存失败,请稍后再试");
			}, "text");
	});
	
	$("#savepwd").click(function(){
		$.post(
			'/updatepassword', 
			$("#privacy_pwd_form").serialize(),
			function(message){
				var message = decodeURIComponent(message.replace(/\+/g,"%20"));
				if(message.match("ok"))
					successBox.show("保存成功");
				else errorBox.show(message);
			}, "text");
	});
	$(".pi_set_checkbox").change(function(){
		if($(this).attr("checked")=="checked"){
			if($(this).attr("id")=="pi_showuser"){
				userBox.show();
			}else if($(this).attr("id")=="pi_showcircle"){
				var left = ($("html").width()-700)/2+"px";
				$("#sendto_box").css({"left":left}).fadeIn(200);
				$("#st_circle").css("marginLeft","0px");
				$("#st_location").val("1");
				$(".st_confirm").addClass("st_pi_confirm");
				$(".st_pi_confirm").bind("click",function(){
					$(".st_selected_place").remove();
					$("#show_pi_clist").val(getSelectedCircle());
					$("#sendto_box").fadeOut(200);
					$("#pageOverlay").css("visibility","hidden");
					setTimeout(function(){$("#st_circle").children().remove();$(".backward").remove();},200);
					$(".st_pi_confirm,.st_pi_cancel").unbind();
					if($("#show_pi_clist").val()=="")
						$("#pi_showcircle").attr("checked",false);
				});
				$(".st_cancel").addClass("st_pi_cancel");
				$(".st_pi_cancel").bind("click",function(){
					$(".st_selected_place").remove();$("#pi_showcircle").attr("checked",false);$("#sendto_box").fadeOut(200);$("#pageOverlay").css("visibility","hidden");$(".checkedcid").remove();setTimeout(function(){$("#st_circle").children().remove();$(".backward").remove();},200);
					$(".st_pi_confirm,.st_pi_cancel").unbind();
				});
				//getMyCircle({});
			}
		}else{
			if($(this).attr("id")=="pi_showuser"){$("#show_pi_ulist").val("");}
			else{$("#show_pi_clist").val("");}
		}
	});
	
	$(".cf_confirm").click(function(){$("#show_pi_ulist").val(userBox.getSelected());userBox.confirm();
		if($("#show_pi_ulist").val()=="")
			$("#pi_showuser").attr("checked",false);});
	$(".cf_cancel").click(function(){$("#show_pi_ulist").val("");$("#pi_showuser").attr("checked",false);userBox.cancel();});
});

//获取分享圈
/*
function getMyCircle(data){
	var circleid = data.circleid;
	var loc = $("#st_location");
	$.ajax({
		url:"getmycircle",
		type:"post",
		data:{
			circleid:circleid
		},
		dataType:"json",
		beforeSend:function(){
			$("#st_loading").show();
		},
		complete:function(){
			$("#st_loading").hide();
		},
		timeout:20000,
		success:function(message){
			if(message.flag == "ok"){
				$(".circle_block").removeClass("select_circle_new");
				var data = decodeURIComponent(message.data.replace(/\+/g,"%20"));
				$("#st_circle").append(data);
				$(".select_circle_new").children("div").children("div").children(".selbelonged_btn").unbind().hover(function(){$(this).css({"filter":"alpha(opacity=100)","opacity":"1"});},function(){$(this).css({"filter":"alpha(opacity=50)","opacity":"0.5"});}).click(function(e){
					var cid = getTargetId($(this));
					getMyCircle({circleid:cid});
					loc.val(Number(loc.val())+1);
					selforward({name:$(this).parent().parent().children(".circlename").html()});
					stopEvent(e);
				});
				if(loc.val() != "1" && !$("#st_op").children(".st_btn").hasClass("backward")){
					$("#st_op").append("<div class = 'st_btn backward'>返回上级</div>");
					$(".backward").unbind().click(function(){
						$(this).unbind();
						loc.val(Number(loc.val())-1);
						if(loc.val() == "1")
							$(this).remove();
						setTimeout(function(){$("#st_circle").children(".circle_block").each(function(index){if(index == loc.val())$(this).remove()})},300);
						selbackward();
					});
				}
				$(".circleico").unbind().bind({"click":function(){
					var tar = $(this).children(".checkcircle"); 
					if(tar.css("display")=="none"){tar.show();$("#pi_clist").append("<input type ='hidden' class = 'checkedcid' id = '"+$(this).attr('id')+"_input' value = '"+$(this).attr('id')+"'/>");}
					else {tar.hide();$("#"+$(this).attr('id')+"_input").remove();} 
				}});
				$(".checkedcid").each(function(){
					var id = $(this).attr("id").split("_");
					$("#cid_"+id[1]).children(".checkcircle").show();
				});
			}
		}
 });
}
function selforward(data){
	$("#st_top").append('<span class = "st_selected_place" style = "float:left;color:#e68303;font-size:14px;line-height:31px;margin-left:1px;">'+data.name+'--></span>');
	var l = $("#st_circle").css("marginLeft");
	var marginleft = Number(l.substring(0,l.length-2))-696+"px";
	$("#st_circle").animate({"marginLeft":marginleft},300);
}
function selbackward(){
	$(".st_selected_place:last").remove();
	var l = $("#st_circle").css("marginLeft");
	var marginleft = Number(l.substring(0,l.length-2))+696+"px";
	$("#st_circle").animate({"marginLeft":marginleft},300,function(){
		var loc = $("#st_location");
		$(".backward").unbind().click(function(){
			$(this).unbind();
			loc.val(Number(loc.val())-1);
			if(loc.val() == "1")
				$(this).remove();
			setTimeout(function(){$("#st_circle").children(".circle_block").each(function(index){if(index == loc.val())$(this).remove()})},300);
			selbackward();
		});
	});
}
function getSelectedCircle(){
	$("#show_pi_clist").val("");
	var cid = "";
	$(".checkedcid").each(function(){
		cid += $(this).attr("id").split("_")[1]+",";
	});
	return cid.substr(0,cid.length-1);
}
*/
//好友盒子
var userBox = {
	show:function(data){
		lock.show();
		$("#cf_prior").click(function(){userBox.priorPage({});});
		$("#cf_next").click(function(){userBox.nextPage({});});
		$("#cf_selectAll").click(function(){userBox.checkAll($("#cf_pn").val())});
		$("#cf_unSelectAll").click(function(){userBox.uncheckAll($("#cf_pn").val())});
		var left = ($("html").width()-700)/2+"px";
		$("#checkfriends_box").css({"left":left}).fadeIn(200);
		if(Number($("#cf_fnum").val())<22) $("#cf_next").unbind().css({"color":"#c1c1c1"});
		if($("#cf_load_pn").val()=="0")userBox.get({pageno:0})
	},
	confirm:function(data){
		$("#checkfriends_box").fadeOut(200);
		$("#cf_prior,#cf_next,#cf_selectAll,#cf_unSelectAll").unbind();
		lock.hide();
	},
	cancel:function(){
		$("#checkfriends_box").fadeOut(200);
		$("#cf_checked_user").html("");
		$(".check_user_ok").hide();
		$("#cf_prior,#cf_next,#cf_selectAll,#cf_unSelectAll").unbind();
		lock.hide();
	},
	get:function(data){
		$.ajax({
			url:"/getmyfriends",
			type:"post",
			data:{
				pageno:data.pageno
			},
			dataType:"json",
			beforeSend:function(){},
			complete:function(){},
			timeout:20000,
			success:function(message){
				if(message.flag == "ok"){
					var data = decodeURIComponent(message.data.replace(/\+/g,"%20"));
					$("#cf_friends_block").append(data);
				}
			}
		});
	},
	nextPage:function(data){
		if(Number($("#cf_fnum").val())-21*(Number($("#cf_pn").val())+1)>0){
			$("#cf_next").unbind();
			$("#cf_pn").val(Number($("#cf_pn").val())+1);
			var ml = $("#cf_friends_block").css("left");
			var s = Number(ml.substring(0,ml.length-2));
			$("#cf_friends_block").animate({
				left:s-696+"px"
			},300,function(){
				$("#cf_next").click(function(){userBox.nextPage({});});
			});
			$("#cf_prior").css({"color":"#4a4a4a"});
			if((Number($("#cf_pn").val())+1)*21>=Number($("#cf_fnum").val()))$("#cf_next").css({"color":"#c1c1c1"});
			if(Number($("#cf_load_pn").val())<Number($("#cf_pn").val())){
				$("#cf_load_pn").val(1+Number($("#cf_load_pn").val()));
				userBox.get({pageno:Number($("#cf_pn").val())});
			}
		}else return false;
	},
	priorPage:function(data){
		if(Number($("#cf_pn").val())>0){
			$("#cf_prior").unbind();
			$("#cf_pn").val(Number($("#cf_pn").val())-1);
			var ml = $("#cf_friends_block").css("left");
			var s = Number(ml.substring(0,ml.length-2));
			$("#cf_friends_block").animate({
				left:s+696+"px"
			},300,function(){
				$("#cf_prior").click(function(){userBox.priorPage({});});
			});
			$("#cf_next").css({"color":"#4a4a4a"});
			if(Number($("#cf_pn").val())<=0)
				$("#cf_prior").css({"color":"#c1c1c1"});
		}else return false;
	},
	check:function(data){
		var id = data.id;
		if($("#ok_u_"+id).css("display")=="none")
		{	document.getElementById("ok_u_"+id).style.display = "block";
			$("#cf_checked_user").append("<input type ='hidden' class = 'checkeduid checkeduid_"+data.pn+"' id = 'u_"+id+"_input' value = '"+id+"'/>");
		}else{
			document.getElementById("ok_u_"+id).style.display = "none";
			$("#u_"+id+"_input").remove();
		}
	},
	getSelected:function(){
		var uid = "";
		$(".checkeduid").each(function(index){
			uid += $(this).attr("id").split("_")[1]+",";
		 }); 
		return uid.substr(0,uid.length-1);
	},
	checkAll:function(pn){
		$(".check_user_ok_"+pn).each(function(index){
			var id = $(this).attr("id").split("_")[2];
			if(!$("#u_"+id+"_input")[0])
				$("#cf_checked_user").append("<input type ='hidden' class = 'checkeduid checkeduid_"+pn+"' id = 'u_"+id+"_input' value = '"+id+"'/>");
		});
		$(".check_user_ok_"+pn).show();
	},
	uncheckAll:function(pn){
		$(".check_user_ok_"+pn).each(function(index){
			$(".checkeduid_"+pn).remove();
		});
		$(".check_user_ok_"+pn).hide();
	}
}

var account = {
	renrenBind:function(){
		var redirect_uri = "renren/bind"
			,url = "http://graph.renren.com/oauth/authorize?response_type=code&client_id=229690&redirect_uri=http://www.quan15.com/" + redirect_uri;
        location.href = url;
	},
	renrenUnbind:function(){
		var url = "/renren/unbind";
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
			beforeSend:function(){loadingBox.show("绑定解除中");},
			complete:function(){loadingBox.hide();},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					location.reload(true);
				}else{
					errorBox.show("解除绑定失败,请稍后再试")
				}
			}
		});
	}
}