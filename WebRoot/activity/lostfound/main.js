var lost = {
	changeType:function(data){
		$("#pageno").val(0);
		$("#f_loading").show();	
		$("#lostfound_notes").html("");
		lost.get(data);
	},
	get:function(data){
		if(data.pn!=null)
			$("#pageno").val(data.pn);
		var pn = Number($("#pageno").val()),
			campus = $("#selected_campus").val(),
			place = $("#selected_place").val();
		$("#pageno").val(pn+1);
		$.ajax({
			url:"/activity/lost_found/getLost",
			type:"post",
			data:{
				pn:pn,
				campus:campus,
				place:place
			},
			dataType:"json",
			beforeSend:function(){
				$(".morenotes").remove();
				$(".moreloading").show();
			},
			complete:function(){
				$(".moreloading,#f_loading").hide();			
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					$("#lostfound_notes").append(root.data);
					if(root.hasNext == "1"){
						$(".morenotes").unbind("click").click(function(){
							lost.get({});
						}).show();
						scrollbind();
					}
					lostInit();
				}else{
					errorBox.show(root.message)
				}
			}
		});
	},
	showBox:function(){
		mtxx.init({method:"activity/lost_found/upload",callBack:"uploadImg.callBack",path:"activity/lostfound"});
		uploadImg.commonInit({action:"/activity/lost_found/upload",callBack:"uploadImg.callBack",path:"activity/lostfound"})
		lock.show();
		uploadImg.addImg(0);
		var st = document.body.scrollTop+document.documentElement.scrollTop+100+"px";
		var sl =($("html").width()-350)/2+"px";
		$("#lostBox").css({left:sl,top:st}).fadeIn(100);
	},
	hideBox:function(){
		lock.hide();
		$("#lostBox").hide();
		$(".lf_text").each(function(){
			$(this).val("");
		});	
		$(".pic").each(function(){$(this).remove();});
		idleUploadBox.hide();
	},
	send:function(){
		var name = $("#b_lost").val().trim(),
			piclist = "",
			campus = $("#b_campus").val(),
			place = $("#b_place").val(),
			place_gene = $("#b_place_gene").val().trim();
		if(name == ""){
			errorBox.show("lost");
			errorBox.show("失物名称必须填写!");
			return false;
		}
		$(".show_img").each(function(){
			if($(this).attr("src")!= "" && $(this).attr("src")!= null){
				if(piclist == "")
					piclist += $(this).attr("src").trim();
				else piclist += ","+$(this).attr("src").trim();
			}
		});
		$.ajax({
			url:"/activity/lost_found/send",
			type:"post",
			data:{
				name:name,
				campus:campus,
				place:place,
				place_gene:place_gene,
				picList:piclist
			},
			dataType:"json",
			beforeSend:function(){				
				$("#send_btn").addClass("button_do").html("发布ing").unbind();
			},
			complete:function(){
				$("#send_btn").removeClass("button_do").html("发布");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("发布成功");
					lost.hideBox();
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	},
	deletePub:function(data){
		$.ajax({
			url:"/activity/lost_found/delete",
			type:"post",
			data:{
				id:data.id
			},
			dataType:"json",
			beforeSend:function(){				
				data.target.fadeOut(200);
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("删除成功");
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	}
}

function scrollbind(){
	var pn = $("#pageno").val();
	var oDiv = document.documentElement;
	$(window).unbind("scroll").bind("scroll",function(){
		 var n1 = parseInt(oDiv.scrollTop+document.body.scrollTop);
	     var n2 = parseInt(oDiv.clientHeight);
	     var n3 = parseInt(oDiv.scrollHeight)-parseInt(30);
	     if((n1+n2 > n3)&&Number(pn)<10)
	     {
	    	 	$(this).unbind("scroll");
	    	 	lost.get({});
	     }
	});
}
function lostInit(){
	$(".new_notes").each(function(){
		document.getElementById($(this).attr("id")).onload = function(){
			showNotesImg({id:$(this).attr("id")})
		}; 
		$(this).removeClass("new_notes");
	});
}

function showNotesImg(data){
	$("#"+data.id).css({"visibility":"visible","width":"auto","height":"auto"}).hide();
	$("#"+data.id).parent().css({"background":"transparent"});
	if($.browser.msie)
		$("#"+data.id).show();
	else $("#"+data.id).show();
}

var uploadImg = {
		addImg:function(number){
			var num = Number(number);
			var data = '<div class = "pic" id = "pic_'+num+'">'+
							'<div class = "upb" id = "upb_'+num+'" title = "点击添加图片" style = "cursor:pointer;" onclick = "idleUploadBox.show({target:this});">'+
							'</div>'+
							'<div class = "upc" id = "upc_'+num+'">'+
								'<img class = "del" id = "del_'+num+'" src = "/web/image/base/del_button.png" style = "position:absolute;top:-10px;right:-10px;cursor:pointer;opacity:0.7;filter:alpha(opacity=70);visibility:hidden;width:20px;" title = "删除图片"/>'+
							 	'<img class = "show_img" id = "show_img_'+num+'" style="display:none;"/>'+
							'</div>'+
						'</div>';
			$(".pic_block").append(data);
			$(".upb").click(function(){
				var num = $(this).attr("id").substring(4);
				$("#click_number").val(Number(num.trim()));
			})
			$("#del_"+num).bind("click",function(){
				var num = $(this).attr("id").substring(4).trim();
				uploadImg.hideImg(num);
			});
		},
		hideImg:function(num){
			$("#pic_"+num).remove();
		    	$("#pic_index").val(Number($("#pic_index").val())+1);
		    	uploadImg.addImg($("#pic_index").val());
		    $("#pic_number").val(Number($("#pic_number").val())-1);
		},callBack:function(data){
			var url = data.url;
			var picnum = Number($("#pic_number").val());
			var clickNum = $("#click_number").val();
			$("#upb_"+clickNum).hide();
			$("#upc_"+clickNum).css("background","transparent").hide();
			$("#show_img_"+clickNum).attr("src",url).show();
			$("#upc_"+clickNum).fadeIn();
	    		$("#del_"+clickNum).css("visibility","visible");
	    		$(".cancel").hide();
			$("#pic_index").val(Number($("#pic_index").val())+1);
			$("#pic_number").val(picnum+1);
		},
		commonInit:function(data){
			$("#idle_upload_form").attr("action",data.action);
			$("#callBackFunc").val(data.callBack)
			$("#uploadDir").val(data.path);
			$("#uploadType").val("common");
			$("#idle_upload_input").change(function(){
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

$(document).ready(function(){
	$(".lf_nav_selected").bind("change",function(){lost.changeType({});});
});