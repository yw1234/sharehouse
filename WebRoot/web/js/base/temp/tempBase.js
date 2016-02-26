function selectedSubmit(data){
	//删除类型筛选
	if(data!=null&&data.clearType==true)
	{
		$("#selectedObjType1").val("");
		$("#selectedObjType2").val("");
	}
	var $feeds = $('.feeds'),method = $("#method").val(),type = $("#type").val();
	$("#pageno").val(0);
	$("#f_loading").show();	
	$feeds.html("");
	feeds.get({type:type,method:method});
}
function scrollbind(){
	var pn = $("#pageno").val();
	var oDiv = document.documentElement;
	$(window).unbind("scroll").bind("scroll",function(){
		 var n1 = parseInt(oDiv.scrollTop+document.body.scrollTop);
	     var n2 = parseInt(oDiv.clientHeight);
	     var n3 = parseInt(oDiv.scrollHeight)-parseInt(30);
	     if((n1+n2 > n3) && Number(pn)<10)
	     {
	    	 	$(this).unbind("scroll");
	    	 	feeds.get({type:$("#type").val(),method:$("#method").val()});
	     }
	});
}
function notesInit(){
	introduceBox.init();
	/*
	$(".new_notes").each(function(){
		document.getElementById($(this).attr("id")).onload = function(){
			showNotesImg({id:$(this).attr("id")})
		}; 
		$(this).removeClass("new_notes");
	});*/
}

function showNotesImg(data){
	var target = $("#"+data.id);
	target.css({"visibility":"visible","width":"auto","height":"auto","display":"none"}).fadeIn(100);;
	target.parent().css({"background":"transparent"});
}

var feeds = {
	get:function(data){
		var type = data.type
			,pn = (data.pn!=null)?data.pn:Number($("#pageno").val())
			,obj1 = $("#selectedObjType1").val()
			,obj2 = $("#selectedObjType2").val();
		$("#pageno").val(pn+1);
		//添加选择标注
		if(obj1!=null&&obj1.trim() != ""){
			$("#objType1").html(obj1).show();
			if(obj2!=null&&obj2.trim()!="")
				$("#objType2").html(">><label style ='margin-left:30px;color:#e68303;'>"+obj2+"</label>").show();
			else
				$("#objType2").html("").hide();
		}else{
			$("#objType1").html("").hide();
			$("#objType2").html("").hide();
		}
		$.ajax({
			url:"/getfeedsList",
			type:"post",
			data:{
				pageno:pn,
				type:type,
				method:data.method,
				shareType:$("#share_type").val(),
				sex:$("#select_sex").val(),
				objType1:obj1,
				objType2:obj2,
				onlyShared:$("#select_onlyShared").val(),
				sortType:$("#sort_type").val(),
				scope:$("#scope").val(),
				key:$("#key").val()
			},
			dataType:"json",
			beforeSend:function(){
				$(".morenotes").remove();
				$(".moreloading").show();
			},
			complete:function(){
				$("#f_loading").hide();		
			},
			timeout:20000,
			success:function(root){
				$(".moreloading").remove();
				if(root.flag == "1"){
					$(".feeds").append(root.data);
					if(root.hasNext == "1"){
						$(".morenotes").unbind("click").click(function(){
							feeds.get({type:type,method:data.method});
						}).show();
						scrollbind();
					}
					notesInit();
				}else{
					errorBox.show("数据获取失败")
				}
			}
		});
	}
}

function show_notesDelete(data){
	var left = getLeft(data.loc);
	var top = getTop(data.loc);
	$(".notes_delete").css({"left":left-110,"top":top+10});
	$("#notes_delete_typeid").val(data.typeid);
	$("#notes_delete_type").val(data.type);
	$("#notes_delete_senderid").val(data.senderid);
	$("#notes_isShared").val(data.isShared);
	(data.isShared=="0")?$("#notes_del_isShared").html("标记为已分享"):$("#notes_del_isShared").html("修改为未分享");
}
function deleteNotes(data){
	if(data.delNumber=="single"){
		$.ajax({
			url:"/delete"+$("#notes_delete_type").val(),
			type:"post",
			data:{
				id:$("#notes_delete_senderid").val(),
				typeid:$("#notes_delete_typeid").val(),
				cid:url.getParams("cid"),
				del_type:"single"
			},
			dataType:"text",
			beforeSend:function(){
				$(".delete_op").hide();$(".delete_loading").show();
			},
			complete:function(){
				$(".delete_op").show();$(".delete_loading").hide();
				$('.notes_delete').css({"left":"-999px","top":"-999px"});
			},
			timeout:20000,
			success:function(message){
				if(message.match("ok")){
					$("#"+$("#notes_delete_type").val()+"_notes_"+$("#notes_delete_typeid").val()).parent("div").fadeOut(100);
					successBox.show("物品已删除", 800);
				}else{errorBox.show("删除失败,请稍后再试")
				}
			}
		});
	}else{
		confirmBox.show({msg:"确定删除这条发布?",func:function(){deleteTotalNotes();}});
	}
}
function deleteTotalNotes(){
	$.ajax({
		url:"/delete"+$("#notes_delete_type").val(),
		type:"post",
		data:{
			typeid:$("#notes_delete_typeid").val(),
			del_type:"all"
		},
		dataType:"json",
		beforeSend:function(){
			$(".delete_op").hide();$(".delete_loading").show();
		},
		complete:function(){
			$(".delete_op").show();$(".delete_loading").hide();
			$('.notes_delete').css({"left":"-999px","top":"-999px"});
		},
		timeout:20000,
		success:function(root){
			if(root.flag == "1"){
				$("#"+$("#notes_delete_type").val()+"_notes_"+$("#notes_delete_typeid").val()).parent("div").fadeOut(100);
				successBox.show("删除成功!", 800);
			}else{errorBox.show(root.message);
			}
		}
	});
}

function signNotes(){
	$.ajax({
		url:"/updateisShared",
		type:"post",
		data:{
			typeid:$("#notes_delete_typeid").val(),
			type:$("#notes_delete_type").val()
		},
		dataType:"json",
		beforeSend:function(){
			$(".delete_op").hide();$(".delete_loading").show();
		},
		complete:function(){
			$(".delete_op").show();$(".delete_loading").hide();
			$('.notes_delete').css({"left":"-999px","top":"-999px"});
		},
		timeout:20000,
		success:function(root){
			if(root.flag = "1"){
				$("#"+$("#notes_delete_type").val()+"_notes_"+$("#notes_delete_typeid").val()).parent("div").fadeOut(100);
			}else{errorBox.show(root.message);
			}
		}
	});
} 

function kickUser(){
	$.ajax({
		url:"/deleteuserInCircle",
		type:"post",
		data:{
			id:$("#notes_delete_senderid").val(),
			cid:url.getParams("cid")
		},
		dataType:"text",
		beforeSend:function(){
			$(".delete_op").hide();$(".delete_loading").show();
		},
		complete:function(){
			$(".delete_op").show();$(".delete_loading").hide();
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				successBox.show("操作成功");
			}else{
				errorBox.show(decodeURIComponent(message.replace(/\+/g,"%20")));
			}
		}
	});
}

function addFavor(data){
	$.ajax({
		url:"/addfavor",
		type:"post",
		data:{
			typeid:data.typeid,
			type:data.type
		},
		dataType:"text",
		beforeSend:function(){
		},
		complete:function(){
			document.getElementById("addfavor_btn_"+data.typeid).onclick=function(){};
			$("#addfavor_btn_"+data.typeid).css({"cursor":"text"});
		},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){
				var fn = Number($("#favor_number_"+data.typeid).html().trim());
				$("#favor_number_"+data.typeid).css("color","#e68303").html(fn+1);
			}else{
				errorBox.show("操作失败,请稍后再试")
			}
		}
	});
}

//禁言
function firbidSend(data){
	$.ajax({
		url:"c_updatefirbidSend",
		type:"post",
		data:{
			circleid:data.id,
			circlename:data.name,
			userid:$("#notes_delete_senderid").val(),
			flag:1
		},
		dataType:"json",
		beforeSend:function(){
		},
		complete:function(){
		},
		timeout:20000,
		success:function(a){
			if(a.flag=="ok"){
				successBox.show("操作成功!");
			}else{
				errorBox.show(decodeURIComponent(a.msg.replace(/\+/g,"%20")));
			}
		}
	});
}