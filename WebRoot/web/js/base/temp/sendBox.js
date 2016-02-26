//获取发布框
var sendBox={
	show:function(data){
		var st = document.body.scrollTop+document.documentElement.scrollTop+"px";
		var sl =($("html").width()-520)/2+"px";
		lock.show();
		$("#sendBlock").css({"display":"block","top":st});
		sendBoxInit.base();
		if(data.type=="goods")sendBoxInit.shareBox();
		loadingBox.show("发布框加载中");
		setTimeout(function(){
			$("#"+data.target.attr("id")).css({"left":sl}).show();
			mtxx.init({method:"upload/idleImages",callBack:"uploadImg.callBack",path:"idle-images"});
			loadingBox.hide();
		},300);
	},
	hide:function(type){
		//sendToBox.cancel();
		$(".send_obj,.more_info_text").val("").blur();
		$(".info_text").each(function(){
			$(".pic").each(function(){$(this).remove();});
			$(this).children("input,textarea").val($(this).attr("TextDefault"));
		});
		$(".borrow_time option[value='0'],.lend_time option[value='0'],.old_degree option[value='0'],#deal_type option[value='0']").attr("selected", true);
		if(type=="share"){
			document.getElementById("goodstype_2").length = 0;
			$("#share_type option[value='0']").attr("selected", true);
		}
		$(".sendBox_main").hide();
		$("#sendBlock").css({"display":"none"}).unbind("click");
		lock.hide();
	}
}

//初始化
var sendBoxInit={
	base:function(){
		$(".cancel").unbind().click(function(){
			$("[idle-pic-new]").remove();
			$(".cancel").hide();
		});
		//$("#st_top").mousedown(function(){move($(this).parent())});
	},
	shareBox:function(){
		document.getElementById("goodstype_1").options[0] = new Option("请选择","");
		document.getElementById("goodstype_2").options[0] = new Option("请选择","");
		getActList.sendGeneralizeType({id:"goodstype_1"});
		$("#goodstype_1").unbind().change(function(){
			if($("#goodstype_1").val()==""){document.getElementById("goodstype_2").length = 0;
			document.getElementById("goodstype_2").options[0] = new Option("请选择","");}
			else getActList.sendDetailType({id:"goodstype_2",gType:$(this).val()});
		});
		changeSendType("mainInfo_sell");
		textLimit.reset({id:"goods_textlimit"});
		$("#send_goods_btn").unbind().bind("click",function(){send.goods()});
		//$("#goods_selected_circle").html("未选择");
	}/*,	
	needsBox:function(){
		document.getElementById("needstype_1").options[0] = new Option("请选择","");
		document.getElementById("needstype_2").options[0] = new Option("请选择","");
		getActList.sendGeneralizeType({id:"needstype_1"});
		$("#needstype_1").change(function(){
			if($("#needstype_1").val()==""){document.getElementById("needstype_2").length = 0;document.getElementById("needstype_2").options[0] = new Option("请选择","");}
			else getActList.sendDetailType({id:"needstype_2",gType:$(this).val()});
		});
		changeSendType("mainInfo_buy");
		$("#needs_textlimit").html("0/140");
		$("#needs_selected_circle").html("未选择");
	}*/
}

var send={
	goods:function(){
		var t=$("#deal_type").val().trim().substring(9),
			pri = ($("#goods_show_pri_show").attr("checked")=="checked")?"1":"0",
			piclist = "",
			goods=$("#goods").val().trim(),
			price=$("#"+t+"_price").val()==null?"":$("#"+t+"_price").val(),
			olddegree=$("#"+t+"_old_degree").val(),
			lendtime=(t=="lend"?$("#lend_time").val():""),
			link=$("#sell_link").val()==null||$("#sell_link").val()==""?"":$("#sell_link").val(),
			bargain = $("#sell_bargain").val(),
			goodstype1=$("#goodstype_1").val(),
			goodstype2=$("#goodstype_2").val(),
			content = ($("#goods_more_info_text").val() != $("#goods_more_info_text").attr("TextDefault") && $("#goods_more_info_text").val().trim() != "")?$("#goods_more_info_text").val():"";
			if(goods == ""){
				errorBox.show("发布什么呢?");
				return false;
			}
		$(".show_img").each(function(){
			if($(this).attr("src")!= "" && $(this).attr("src")!= null){
				if(piclist == "")
					piclist += $(this).attr("src").trim();
				else piclist += ","+$(this).attr("src").trim();
			}
		});
		if(!textLimit.validation({target_text:$("#goods_more_info_text")})) {
			errorBox.show("字数有点多哦~");
			return false;
		}
		$.ajax({
			url:"/send/goods",
			type:"post",
			data:{
				goods:goods,
				sharetype:t,
				price:price,
				old_degree:olddegree,
				lendtime:lendtime,
				content:content,
				piclist:piclist,
				goodslink:link,
				bargain:bargain,
				show_privacy:pri,
				goodstype1:goodstype1,
				goodstype2:goodstype2,
				circleid:url.getParams("cid")!=null?url.getParams("cid"):""
			},
			dataType:"json",
			beforeSend:function(){
				$("#send_goods_btn").addClass("button_do").children("span").html("发布ing").unbind();
			},
			complete:function(){
				$("#send_goods_btn").removeClass("button_do").children("span").html("发布");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("发布成功");
					sendBox.hide("share");
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	}
}

//图片上传
var idlePicUpload = {
	addImg:function(){
		var data = '<div class = "pic">'+
						'<div class = "upc" idle-pic-new>'+
							'<img class = "upload_delete" src = "/web/image/base/del_button.png" style = "position:absolute;top:-10px;right:-10px;cursor:pointer;opacity:0.7;filter:alpha(opacity=70);visibility:hidden;width:20px;" title = "删除图片" onclick = "idlePicUpload.hideImg({target:$(this)});"/>'+
						 	'<img class = "show_img" src = "" style="display:none;"/>'+
						'</div>'+
					'</div>';
		$(".pic_block").append(data);
	},
	hideImg:function(data){
		var target = data.target;
		target.parent().parent().remove();
	    if($(".pic:visible").length < 6)
	    		$("#idleUploadBox").show();
	},callBack:function(data){
		var url = data.url,
			new_block = $("[idle-pic-new]");
		new_block.css("background","transparent").hide();
		new_block.children(".show_img").attr("src",url).show();
		new_block.fadeIn();
		new_block.children(".upload_delete").css("visibility","visible");
		new_block.removeAttr("idle-pic-new");
    		$(".cancel").hide();
    		if($(".pic:visible").length > 5)
			$("#idleUploadBox").hide();
	}
}

var linkTo = {
	jd:function(){
		var url = "http://search.jd.com/Search?keyword="+$("#goods").val().trim()+"&enc=utf-8&area=1";
		window.open(url,"_blank");
		$("#sell_link").val(url);
	},
	amazon:function(){
		var url = "http://www.amazon.cn/s/ref=nb_sb_noss_1?__mk_zh_CN=亚马逊网站&url=search-alias%3Daps&field-keywords="+$("#goods").val().trim()+"&rh=i%3Aaps%2Ck%3A"+$("#goods").val().trim();
		window.open(url,"_blank");
		$("#sell_link").val(url);
	},
	taobao:function(){
		var url = "http://s.taobao.com/search?q="+$("#goods").val().trim()+"&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&initiative_id=tbindexz_20130413";
		window.open(url,"_blank");
		$("#sell_link").val(url);
	},
	zol:function(){
		url = "http://detail.zol.com.cn/index.php?keyword=&c=SearchList";
		window.open(url,"_blank");
		/*
		var key = $("#goods").val().trim();
		$.post("common_getencode",{str:key,encodeType:"gb2312"},function(s){
			
			$("#sell_link").val(url);
		});
		setTimeout(function(){
			window.open(url,"_blank");
		},500);*/
		
	},
	wbtc:function(){
		var url = "http://bj.58.com/sou/jh_"+$("#goods").val().trim()+"/?from=home-search";
		window.open(url,"_blank");
	},
	ganji:function(){
		var url = "http://bj.ganji.com/wu/s/_"+$("#goods").val().trim()+"/";
		window.open(url,"_blank");
	},
	tzj:function(){
		url = "http://s.ershou.taobao.com/";
		window.open(url,"_blank");
		/*
		var key = $("#goods").val().trim();
		$.post("common_getencode",{str:key,encodeType:"gbk"},function(s){
			
		});
		setTimeout(function(){
			
		},500);*/
	}
}

function changeSendType(type){
	$(".mainInfo").hide();
	$("#"+type).show();
	$("#deal_type option[value='"+type+"']").attr("selected", true);
}


/*
var sendToBox = {
	init:function(data){
		sendToBox.clear();
		var type = data.type;
		var left = ($("body").width()-700)/2+"px";
		var top =  document.body.scrollTop+document.documentElement.scrollTop+100+"px";
		$("#sendto_box").css({"left":left,"top":top}).fadeIn(200);
		$("#st_circle").css("marginLeft","0px");
		$("#st_location").val("1");
		$("#st_type").val(type);
		$(".st_selected_place").remove();
		sendToBox.get({type:type});
	},
	selected:function(type){
		var cid = "";
		$(".checkedcid").each(function(){
			var t = $(this).attr("id").split("_")[2];
			if(type == t){
				cid += $(this).attr("id").split("_")[1]+",";
			}	
		});
		return cid.substr(0,cid.length-1);
	},
	get:function(data){
		var circleid = data.circleid;
		var type = data.type;
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
						sendToBox.get({circleid:cid,type:type});
						loc.val(Number(loc.val())+1);
						sendToBox.forward({name:$(this).parent().parent().children(".circlename").html()});
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
							sendToBox.backward();
						});
					}
					$(".circleico").unbind().bind({"click":function(){
						var tar = $(this).children(".checkcircle"); 
						if(tar.css("display")=="none"){tar.show();$("#checkedcircle").append("<input type ='hidden' class = 'checkedcid' id = '"+$(this).attr('id')+"_"+type+"_input' value = '"+$(this).attr('id')+"'/>");$("#checkedcirclename").append("<input type ='hidden' class = 'checkedcname' id = '"+$(this).attr('id')+"_"+type+"_name' value = '"+$(this).parent().children(".circlename").html()+"'/>");}
						else {tar.hide();$("#"+$(this).attr('id')+"_"+type+"_input").remove();$("#"+$(this).attr('id')+"_"+type+"_name").remove();} 
					}});
					$(".checkedcid").each(function(){
						var id = $(this).attr("id").split("_");
						if(type == id[2])
							$("#cid_"+id[1]).children(".checkcircle").show();
					});
				}else{
					alert("操作失败,请稍后再试");return false;
				}
			}
		});
	},
	forward:function(data){
		$("#st_top").append('<span class = "st_selected_place" style = "float:left;color:#e68303;font-size:14px;line-height:31px;margin-left:1px;">'+data.name+'--></span>');
		var l = $("#st_circle").css("marginLeft");
		var marginleft = Number(l.substring(0,l.length-2))-696+"px";
		$("#st_circle").animate({"marginLeft":marginleft},300);
	},
	backward:function(){
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
				sendToBox.backward();
			});
		});
	},
	confirm:function(){
		var namelist = "";
		$(".checkedcname").each(function(){
			namelist += $(this).val()+",";
		});
		if(namelist.trim()!="")$(".select_circle").html(getSubContent(namelist.substring(0,namelist.length-1),20));
		else $(".select_circle").html("未选择");
		sendToBox.selected($("#st_type").val());
		$("#sendto_box").fadeOut(200);
		setTimeout(function(){$("#st_circle").children().remove();$(".backward").remove();},300);
	},
	cancel:function(){
		$("#sendto_box").fadeOut(200);
		$(".checkedcid").remove();
		$(".checkedcname").remove();
		$(".select_circle").html("未选择");
		setTimeout(function(){$("#st_circle").children().remove();$(".backward").remove();},300);
	},
	clear:function(){
		$(".checkedcid").remove();
		$(".checkedcname").remove();
		$("#st_circle").children().remove();
		$(".backward").remove();
	}
}
*/
