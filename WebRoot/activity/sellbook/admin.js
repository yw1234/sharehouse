//管理员相关

var sendBox = {
	init:function(){
		mtxx.init({method:"activity/sell_book/admin/upload",callBack:"uploadImg.callBack",path:"activity/sellbook"});
		uploadImg.commonInit({action:"/activity/sell_book/admin/upload",callBack:"uploadImg.callBack",path:"activity/sellbook"});
	},
	show:function(){
		loadingBox.show("发布框加载中");
		uploadImg.addImg(0);
		lock.show();
		var st = document.body.scrollTop+document.documentElement.scrollTop+50+"px";
		var sl =($("html").width()-520)/2+"px";
		setTimeout(function(){
			$("#sendBox").css({left:sl,top:st}).show();
			loadingBox.hide();
		},200);
	},
	hide:function(){
		lock.hide();
		idleUploadBox.hide();
		$("#sendBox").hide();
		$("#pic_number").val("0");
		$(".send_obj,.more_info_text").val("").blur();
		$(".info_text").each(function(){
			$(".pic").each(function(){$(this).remove();});
			$(this).children("input,textarea").val($(this).attr("TextDefault"));
		});	
	}
}


var books = {
	send:function(){
		var book = $("#book").val().trim(),
			piclist = "",
			price=$("#price").val(),
			realprice = $("#real_price").val(),
			scope=$("#fit_department").val(),
			stock = $("#stock").val(),
			booktype=$("#book_type").val(),
			olddegree=$("#old_degree").val(),
			content = ($("#book_more_info_text").val() != $("#book_more_info_text").attr("TextDefault") && $("#book_more_info_text").val().trim() != "")?$("#book_more_info_text").val():"";	
		if(book==""){
			errorBox.show("请输入书名");
			return false;
		}
		if(price==""){
			errorBox.show("价格必须填写!");
			return false;
		}
		$(".show_img").each(function(){
			if($(this).attr("src")!= "" && $(this).attr("src")!= null){
				if(piclist == "")
					piclist += $(this).attr("src").trim();
				else piclist += ","+$(this).attr("src").trim();
			}
		});
		if(piclist==""){
			errorBox.show("要至少上传一张实物图哦~");
			return false;
		}
		if(!textLimit.validation({target_text:$("#book_more_info_text")})) {
			errorBox.show("字数有点多哦~");
			return false;
		}
		$.ajax({
			url:"/activity/sell_book/admin/send",
			type:"post",
			data:{
				book:book,
				piclist:piclist,
				price:price,
				realPrice:realprice,
				scope:scope,
				stock:stock,
				type:booktype,
				old_degree:olddegree,
				content:content
			},
			dataType:"json",
			beforeSend:function(){				
				$("#send_btn").addClass("sb_button_do").children("span").html("发布ing").unbind();
			},
			complete:function(){
				$("#send_btn").removeClass("sb_button_do").children("span").html("发布");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("发布成功");
					sendBox.hide();
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	},
	del:function(data){
		$.ajax({
			url:"/activity/sell_book/admin/deleteBooks",
			type:"post",
			data:{
				id:data.id
			},
			dataType:"json",
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					data.target.slideUp(100);
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},selected:function(data){
		var url = "/activity/sell_book/admin?page=books&p=1",
			key = (data==null)?$("#bi_key").val():data.key,
			params = "";
		if(key!=null && key!="")
			params += "&key="+encodeURIComponent(encodeURIComponent(key));
		window.location.href = url+params;
	},
	changePage:function(data){
		window.location.href = url.del(window.location.href, "p")+"&p="+data.p;
	}
}

var updateBox = {
	init:function(){
	},
	show:function(data){
		var $b_id = $("#update_id"),
			$b_name = $("#update_book"),
			$b_realPrice = $("#update_real_price"),
			$b_price = $("#update_price"),
			$b_oldDegree = $("#update_old_degree"),
			$b_stock = $("#update_stock"),
			$b_type = $("#update_book_type"),
			$b_scope = $("#update_fit_department"),
			$b_content = $("#update_box_info_text");
		$.ajax({
			url:"/activity/sell_book/getBookDetails",
			type:"post",
			data:{
				id:data.id
			},
			dataType:"json",
			beforeSend:function(){
				loadingBox.show();
			},
			complete:function(){
				loadingBox.hide();			
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					var details = root.details;
					$b_id.val(details.id);
					$b_name.val(details.name);
					$b_realPrice.val(details.realPrice);
					$b_price.val(details.price);
					$b_oldDegree.children("option[value="+details.old_degree+"]").attr("selected",true);
					$b_stock.val(details.stock);
					$b_type.children("option[value="+details.type+"]").attr("selected",true);
					$b_scope.children("option[value="+details.scope+"]").attr("selected",true);
					$b_content.val(details.content);
					lock.show();
					var st = document.body.scrollTop+document.documentElement.scrollTop+50+"px";
					var sl =($("html").width()-520)/2+"px";
					$("#updateBox").css({left:sl,top:st}).show();
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},
	submit:function(){
		var id = $("#update_id").val(),
			book = $("#update_book").val().trim(),
			price=$("#update_price").val(),
			realprice = $("#update_real_price").val(),
			scope=$("#update_fit_department").val(),
			stock = $("#update_stock").val(),
			booktype=$("#update_book_type").val(),
			olddegree=$("#update_old_degree").val(),
			content = ($("#update_box_info_text").val() != $("#update_box_info_text").attr("TextDefault") && $("#update_box_info_text").val().trim() != "")?$("#update_box_info_text").val():"";	
		if(book==""){
			errorBox.show("请输入书名");
			return false;
		}
		if(price==""){
			errorBox.show("价格必须填写!");
			return false;
		}
		if(!textLimit.validation({target_text:$("#book_more_info_text")})) {
			errorBox.show("字数有点多哦~");
			return false;
		}
		$.ajax({
			url:"/activity/sell_book/admin/updateBooks",
			type:"post",
			data:{
				id:id,
				book:book,
				price:price,
				realPrice:realprice,
				scope:scope,
				stock:stock,
				type:booktype,
				old_degree:olddegree,
				content:content
			},
			dataType:"json",
			beforeSend:function(){				
				$("#update_btn").addClass("sb_button_do").children("span").html("修改ing").unbind();
			},
			complete:function(){
				$("#update_btn").removeClass("sb_button_do").children("span").html("保存修改");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("修改成功");
					updateBox.hide();
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	},
	hide:function(){
		$("#updateBox").hide();
		lock.hide();
	}
}

var booking = {
	complete:function(data){
		$.ajax({
			url:"/activity/sell_book/admin/complete",
			type:"post",
			data:{
				id:data.id,
				bookid:data.bookid
			},
			dataType:"json",
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					data.target.html("已完成");		
				}else{
					errorBox.show(root.message);
				}
			}
		});
	},
	searchByselected:function(){
		var url = "/activity/sell_book/admin?page=booking&p=1",
			bookingType = $("#bookingType").val(),
			campus = $("#campus").val(),
			isComplete = $("#isComplete").val(),
			params = "";
		if(bookingType!=null && bookingType!="")
			params += "&bookingType="+encodeURIComponent(encodeURIComponent(bookingType));
		if(campus!=null && campus!="")
			params += "&campus="+encodeURIComponent(encodeURIComponent(campus));
		if(isComplete!=null && isComplete!="")
			params += "&isComplete="+isComplete;
		window.location.href = url+params;
	},
	searchByKey:function(data){
		var url = "/activity/sell_book/admin?page=booking&p=1",
			key = (data==null)?$("#bookingid").val():data.key,
			params = "";
		if(key!=null && key!=""){
			params += "&key="+encodeURIComponent(encodeURIComponent(key));
		}
		window.location.href = url+params;
	},
	changePage:function(data){
		window.location.href = url.del(window.location.href, "p")+"&p="+data.p;
	},
	cancel:function(data){
		$.ajax({
			url:"/activity/sell_book/cancelBooking",
			type:"post",
			data:{
				id:data.id
			},
			dataType:"json",
			beforeSend:function(){
				data.target.slideUp(200);
			},
			complete:function(){
			},
			timeout:20000,
			success:function(root){
				successBox.show("订单取消成功");
			}
		});
	}
}


//图片上传
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
	    if(Number($("#pic_number").val()) > 5){
	    		$("#pic_index").val(Number($("#pic_index").val())+1);
	    		uploadImg.addImg($("#pic_index").val());
	    }
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
		if(picnum < 5)
			uploadImg.addImg($("#pic_index").val());
	},
	commonInit:function(data){
		$("#idle_upload_form").attr("action",data.action);
		$("#callBackFunc").val(data.callBack)
		$("#uploadDir").val(data.path);
		$("#uploadType").val("common");
		$("#idle_upload_input").unbind().change(function(){
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

var bigImg={
	show:function(data){
		lock.show();
		getCenterPlace("show_big_img");
		$("#show_big_img").show();
		$.post(
			"/activity/sell_book/showBigPicture",
			{id:data.id},
			function(data){
				$("#big_img").attr("src",data.url);
			},"json"
		);
	},
	hide:function(){
		lock.hide();
		$("#big_img").attr("src","");
		$("#show_big_img").hide();
	}
}

$(document).ready(function(){
	//init
	$('input,textarea').bind({
		focus:function(){
			$(this).css({"box-shadow":"inset 0px 0px 5px #89b403"});
		},
		blur:function(){
			$(this).css({"box-shadow":"none"});
		}
	});
	$("#booking_search_confirm").click(function(){booking.searchByKey();});
	$("#booking_selected_confirm").click(function(){booking.searchByselected();});
	sendBox.init();
	updateBox.init();
});
