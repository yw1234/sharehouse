var bookDetails = {
	init:function(data){
		$.ajax({
			url:"/activity/sell_book/getBookDetails",
			type:"post",
			data:{
				id:data.id
			},
			dataType:"json",
			beforeSend:function(){				
				lock.show();
				loadingBox.show("信息读取中");
			},
			complete:function(){
				loadingBox.hide();
			},
			timeout:20000,
			success:function(root){
				var details = root.details,
					content = details.content;
				$("#booking_bookid").val(details.id);
				$("#bd_name").html(details.name);
				$("#bd_price").html(details.price);
				$("#bd_type").html(details.type);
				$("#bd_scope").html(details.scope);
				$("#bd_stock").html(details.stock);
				$("#bd_looktimes").html(root.lookedTimes);
				$("#bd_bookTimes").html(details.requiredtimes);
				$("#bd_remark").html((content != "")?content:"无");
				booksPicture.load({urlList:root.urlList});
				bookDetails.show();
			}
		});
		bookDetails.show();
	},
	show:function(){
		var left = ($('html').width()-$('#book_details').width())/2 + "px";
		var top = 15 + document.body.scrollTop + "px";
		$("#book_details").css({"left":left,"top":top}).show();
	},
	hide:function(){
		$("#book_details").hide();
		$("#showImg_block").css({left:"0px"}).html("");
		lock.hide();
	}
}

var booksPicture = {
	load:function(data){
		var pic_pro = data.urlList.split(",");
		$("#showImg_backward,#showImg_forward").unbind().children("div").hide();
		$("#showImg_num").val("1");
		$("#showImg_total").val(pic_pro.length);
		$("#bd_img_num").html(pic_pro.length);
		html="";
		for(var i=0;i<pic_pro.length;i++){
			var left = (i)*560+"px";
			html+="<li id = 'showImg_border_"+i+"' style = 'position:absolute;top:0px;left:"+left+";overflow:hidden;margin:0px;padding:0px;width:560px;height:510px;line-height:510px;text-align:center;background:url(/web/image/base/pic_loading_default.gif) center center no-repeat;'>" +
				  "	  <img class = 'books_img' id = 'show_img_"+i+"' src = '"+pic_pro[i]+"' rotate='0' style = 'table-layout:fixed;vertical-align:middle;display:none;_width:expression(this.width>560?\"560px\":(this.width+\"px\"));max-width:560px;_height:expression(this.height>510?\"510px\":(this.height+\"px\"));max-height:510px;min-width:300px;min-height:300px;'/>" +
				  "</li>";
		}
		$("#showImg_block").html(html);
		$(".books_img").each(function(){
			document.getElementById($(this).attr("id")).onload = function(){
				$(this).fadeIn(100);
			}
		});
		$("#showImg_backward").hover(function(){Number($("#showImg_num").val())>1?$(this).children("div").fadeIn(200):$(this).children("div").hide();},function(){$(this).children("div").fadeOut(200);})
			.click(function(){
				if(Number($("#showImg_num").val())>1)
					booksPicture.backward();
				else return false;
		});
		$("#showImg_forward").hover(function(){Number($("#showImg_num").val())<Number($("#showImg_total").val())?$(this).children("div").fadeIn(200):$(this).children("div").hide();},function(){$(this).children("div").fadeOut(200);})
			.click(function(){
				if(Number($("#showImg_num").val())<Number($("#showImg_total").val()))
					booksPicture.forward();
				else return false;
		});
	},
	forward:function(){
		var showingNum = Number($("#showImg_num").val());
		var nextImg = 0-showingNum;
		$("#showImg_block").stop(true,false).animate({
			left:nextImg*560+"px"
		},300);
		$("#showImg_num").val(showingNum+1);
	},
	backward:function(){
		var showingNum = Number($("#showImg_num").val());
		var priorImg = 2-showingNum;
		$("#showImg_block").stop(true,false).animate({
			left:priorImg*560+"px"
		},300);
		$("#showImg_num").val(showingNum-1);
	}			
}

var bookingBox = {
	show:function(data){
		if(!lock.isLocking()){
			$("#booking_box_locking").val("0");
			lock.show();
		}
		else $("#booking_box_locking").val("1");
		lock.addStrengthLock();
		getCenterPlace("booking_box");
		$("#booking_count").html("1");
		$("#booking_single_price").val(data.price);
		$("#booking_bookName").html(data.bookName);
		$("#booking_stock").html(data.stock);
		$("#booking_price").html(data.price);
		$("#booking_bookid").val(data.bookid);
		$("#booking_box").show();
		$(".bookingType").unbind("click").bind("click",function(){
			$(".booking_type_details").hide();
			$("#booking_type"+$(this).val()).show();
		});
		/*
		$("#booking_Campus").unbind("change").bind("change",function(){
			var campus = $(this).val();
			$(".booking_dorm").hide();
			if(campus=="沙河"){
				$("#booking_dorm_shahe").show();
			}
			else if(campus=="本部"){
				$("#booking_dorm_benbu").show();
			}
		});*/
	},
	hide:function(){
		$("#booking_text").val("");
		textLimit.reset({id:"booking_textlimit"});
		if($("#booking_box_locking").val()=="0")
			lock.hide();
		lock.cancelStrengthLock();
		$("#booking_box").hide();
	},
	cancel:function(){
		bookingBox.hide();
	},
	submit:function(){
		var bookingType = "自取"
			,count = $("#booking_count").html()
			,name = $("#booking_userName").val()
			,phone = $("#booking_userPhone").val()
			,getTime = $("#booking_getTime").val()
			,campus = $("#booking_Campus").val()
			,dorm = ""
			,dorm_dep = $("#booking_dorm_dep").val()
			,bookid = $("#booking_bookid").val()
			,content = $("#booking_text").val();
		$(".booking_dorm").each(function(){if($(this).css("display")!="none") dorm = $(this).val();});
		if(!textLimit.validation({target_text:$("#booking_text")}))
			{errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/activity/sell_book/booking",
			type:"post",
			data:{
				bookid:bookid,
				content:content,
				count:count,
				bookingType:bookingType,
				getTime:getTime,
				dorm:dorm,
				campus:campus,
				dormNumber:dorm_dep,
				bookerName:name,
				bookerContact:phone
			},
			dataType:"json",
			beforeSend:function(){
				$("#booking_submit").addClass("sb_button_do").html("提交中").unbind("click");
			},
			complete:function(){
				$("#booking_submit").removeClass("sb_button_do").html("提交").bind("click",function(){bookingBox.submit();});
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("预订成功! 请及时到指定外场取书",1500);
					bookingBox.cancel();
				}else{
					errorBox.show(root.message);
				}
			}
		});
	}
}

var books = {
	changeType:function(data){
		$("#pageno").val(0);
		$("#f_loading").show();	
		$("#sellbook_notes").html("");
		if(data.searchType!=null&&data.searchType=="key"){
			data = $.extend(data,{key:$("#selected_name").val()});
		}else $("#selected_name").val("");
		books.get(data);
	},
	get:function(data){
		var pn = (data.pn != null)?data.pn:Number($("#pageno").val());
		$("#pageno").val(pn+1);
		if(data.searchType!=null&&data.searchType=="key"){
			var $key = data.key;
		}
		else{
			var $type = $("#selected_type").val(),
				$scope = $("#selected_scope").val(),
				$old_degree = $("#selected_oldDegree").val(),
				$price_level = $("#selected_priceLevel").val(),
				$stock = $("#selected_stock").val();
		}
		$.ajax({
			url:"/activity/sell_book/getBooks",
			type:"post",
			data:{
				pageno:pn,
				searchType:data.searchType!=null?data.searchType:"",
				key:$key,
				type:$type,
				scope:$scope,
				old_degree:$old_degree,
				price_level:$price_level,
				stock:$stock
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
					$("#sellbook_notes").append(root.data);
					if(root.hasNext == "1"){
						$(".morenotes").unbind("click").click(function(){
							if(data!=null&&data.key=="true")
								books.get({key:true});
							else books.get({});
						}).show();
						scrollbind();
					}
					booksInit();
				}else{
					errorBox.show(root.message)
				}
			}
		});
	},
	increase:function(){
		var count = Number($("#booking_count").html()),
			singlePrice = Number($("#booking_single_price").val());
		if(count < $("#booking_stock").html()){
			$("#booking_price").html(singlePrice*(count+1));
			$("#booking_count").html(count+1);
		}
		else return false;
	},
	decrease:function(){
		var count = Number($("#booking_count").html()),
			singlePrice = Number($("#booking_single_price").val());
		if(count > 1){
			$("#booking_price").html(singlePrice*(count-1));
			$("#booking_count").html(count-1);
		}
			
		else return false;
	},
	cancelBooking:function(data){
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
				getTotalPrice();
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
	    	 	books.get({});
	     }
	});
}
function booksInit(){
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

function getTotalPrice(){
	var price = 0;
	$(".single_price").each(function(){
		price += Number($(this).html());
	});
	$("#total_price").html(price);
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
	$("#booking_submit").bind("click",function(){bookingBox.submit();});
	$(".sb_nav_selected").bind("change",function(){books.changeType({});});
	$(".booking_selected").bind("change",function(){
		var url = "/activity/sell_book/book",
			status = $("#status").val(),
			params = "";
		if(status != null && status != "")
			params += "?status="+status;
		window.location.href = url+params;
	});
	getTotalPrice();
});