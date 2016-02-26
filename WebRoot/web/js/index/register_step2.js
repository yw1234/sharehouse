$(document).ready(function(){
	$(".sb_choose_province").hover(function(){if($(this).attr("choose")!="true")$(this).css({"background":"#e68303","color":"white"})},function(){if($(this).attr("choose")!="true")$(this).css({"background":"white","color":"#e68303"});}).click(function(){
		schoolBox.selProvince({pro:$(this).html(),btn:$(this)});
	});
	newBox.init();
	schoolBox.init();
	$("#department,#grade,#edu").change(function(){
		new_getFriends();
	});
	//$.post("/email_tip/activate",{},function(res){},"text");
});

var schoolBox={
	init:function(){
		schoolBox.selProvince({pro:'北京',btn:$(".sb_choose_province:first")});
	},
	show:function(){
		var st = document.body.scrollTop+document.documentElement.scrollTop+50+"px";
		var sl =($("html").width()-600)/2+"px";
		lock.show();
		$("#school").blur();
		$("#school_box").css({"top":st,"left":sl}).show();
	},
	comfirmSelect:function(data){
		$("#school").val(data.name).css({color:"#535353"});
		//获取院系
		getActList.department({school:$("#school").val(),id:"department",dep_default:$("#new_department").val()});
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
					var text = root.schoollist;
					if(text!="")
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

var newBox = {
	init:function(){
		var y = new Date().getFullYear(),m = new Date().getMonth(),school = $("#school").val();
		var year = (m>=8)?y:y-1,dep = $("#new_department").val(),grade = $("#new_grade").val();
		document.getElementById("grade").options[0] = new Option("请选择","");
		document.getElementById("department").options[0] = new Option("请选择","");
		for(i = year ; i >= 2001; i--)
		{	var y = i-2000,g = (y<10)?"0"+y+"级":y+"级";
			document.getElementById("grade").options[y] = new Option(g,g);
			if(grade!=null && grade == g)
				document.getElementById("grade").options[y].selected = true;
		}	
		if(school.trim() != "")
			getActList.department({school:school,id:"department",dep_default:dep,callback:function(){new_getFriends();}});
	},
	complete:function(){
		var uid = "",
			school = $("#school").val(),
			edu = $("#edu").val(),
			dep = $("#department").val(),
			grade = $("#grade").val();
		if($("#email")[0]){
			var email = $("#email").val(),
				password =  $("#password").val(),
				email_reg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			if(email.trim() == "" || !email_reg.test(email)){
				 errorBox.show("邮箱格式不正确");
				 return false;
			 }
			if(password.trim() != ""){
				if(password.length < 6 || password.length > 20){
					errorBox.show("密码长度:6-20位");
					return false;
				}
			}else{
				errorBox.show("请输入密码");
				return false;
			}
		}
		$(".user_checked").each(function(index){
    			if($(this).val().trim() == "1"){
    				uid += $("#ub_"+index).children(".user_checked").attr("id").substring(2)+",";
    			}
    	 	}); 
		$.ajax({
			url:"/register_complete",
			type:"post",
			data:{
				school:school,edu:edu,department:dep,grade:grade,
				uidlist:uid.substring(0,uid.length-1),
				email:$("#email").val(),
				password:$("#password").val()
			},
			dataType:"json",
			timeout:20000,
			beforeSend:function(){
				$("#new_complete_btn").addClass("button_do").html("提交中..");
			},
			complete:function(){
			},
			success:function(root){
				if(root.flag == "1"){
					location.href = root.redirectURL;
				}else{
					$("#new_complete_btn").removeClass("button_do").html("完成");
					errorBox.show(root.message);
					return false;
				}
			}
		});
	}
}

function checkuser(id){
	var c = document.getElementById("u_"+id).value;
	if(c == "0")
	{
		document.getElementById("u_"+id).value = "1";
		document.getElementById("ok_u_"+id).style.display = "block";
	}else{
		document.getElementById("u_"+id).value = "0";
		document.getElementById("ok_u_"+id).style.display = "none";
	}
}

function new_getFriends(){
	var d = $("#department");
	var g = $("#grade");
	var e = $("#edu");
	if(!d.val()=="" && !g.val()==""){
		$.ajax({
			url:"/register_getCommonUser",
			type:"post",
			data:{
				school:$("#school").val(),department:d.val(),grade:g.val(),edu:e.val()
			},
			dataType:"json",
			beforeSend:function(){
				$("#new_show_user").hide();
				$("#new_sf_loading").show();
			},
			timeout:20000,
			success:function(root){
				data = decodeURIComponent(root.data.replace(/\+/g,"%20"));
				if(root.flag == "1")
				{
					var new_show_user = $("#new_show_user");
					$("#new_sf_loading").hide();
					new_show_user.html(data);
					new_show_user.fadeIn(200);
					new_show_user.bind("scroll",function(){//阻止滚动冒泡
						var _div = $("#new_show_user")[0];  
						if(jQuery.browser.mozilla){  
						    _div.addEventListener('DOMMouseScroll',function(e){  
						        _div.scrollTop += e.detail>0?60:-60;  
						        e.preventDefault();  
						    },false);  
						}else{  
						    _div.onmousewheel = function(e){  
						        e = e || window.event;  
						        _div.scrollTop += e.wheelDelta>0?-60:60;  
						        e.returnValue=false  
						    };  
						}  
					});
				}
			}
	 });
	}
}

function checkResult(){
	$("#checkresult").submit();
}

function show_infotip(text,type){
	var errortip = document.getElementById(type+"_info_tip");
	errortip.childNodes[0].innerHTML = text;
	errortip.style.display = "block";
}

var avatarBox = {
	init:function(){
		var box ='<div id = "avatar_box" style = "position:absolute;width:500px;background:white;box-shadow:0px 0px 15px #333333;z-index:200;border:1px solid #b3b3b3;border-radius:5px;min-height:280px;display:none;">'+
				'	<div style = "float:left;width:100%;margin-top:5px;">'+
				'		<span style = "float:left;margin-left:20px;font-size:15px;">点击上传图片</span>'+
				'		<img  src = "/web/image/base/del_button_1.png" style = "float:right;margin-top:2px;margin-right:10px;position:relative;cursor:pointer;" onclick = "avatarBox.hide();"/>'+
				'	</div>'+
				'	<div id = "info_upload_block">'+
				'		<div id = "info_uploadico">'+
				'			<div id = "info_upload_img">'+
				'				<iframe id = "info_upload_ifr" name="info_upload_ifr" style="display:none;"></iframe>'+
				'				<form id = "info_upload_form" action = "/upload/headIco" method = "post" enctype="multipart/form-data"  target="info_upload_ifr">'+
				'					<a id="info_upload_button" class = "info_upload_button" href = "javascript:void(0);"><input type="file" name = "file" id = "info_upload_input"></a>'+
				'					<input type="hidden" value="uploadAvatarCallBack" name="callBackFunc"/>'+
				'					<input type="hidden" value="avatar" name="uploadDir"/>'+
				'				</form>'+
				'			</div>'+
				'			<div id = "info_upload_show_loading" style = "display:none;">'+
				'			</div>'+
				'			<div id = "info_upload_show" style = "display:none;">'+
				'				<img id = "info_show_img"/>'+
				'			</div>'+
				'		</div>'+
				'		<form id = "uploadcutimage" action = "/upload/cutimage" method = "post">'+
				'			 <div id = "info_upload_preview_box_border">'+
				'			 	<div id="info_upload_preview_box" class="info_upload_preview_box">'+
				'			 		<img id="info_crop_preview"/>'+
				'			 	</div> '+
				'			 	<input type = "hidden" name = "p" id = "imgPath"/>'+
				'			 	<input type = "hidden" name = "x" id = "imgX"/>'+
				'			 	<input type = "hidden" name = "y" id = "imgY"/>'+
				'			 	<input type = "hidden" name = "w" id = "imgW"/>'+
				'			 	<input type = "hidden" name = "h" id = "imgH"/>'+
				'			 	<div class = "button"id = "comfirm_cut">确定</div>'+
				'			 </div>'+
				'		 </form>'+
				'	</div>'+
				'</div>'
		$("#avatar_box_container").html(box);
		getCenterPlace("avatar_box");
		$("#info_upload_input").unbind().change(function(){
			if(upload.validator({id:"info_upload_input"})){
				$("#info_upload_img").hide();
				$("#info_upload_show_loading").show();
				$("#info_upload_form").submit();
			}
			$(this).val("");
		});
		$("#comfirm_cut").unbind().click(function(){
			$.ajax({
				url:"/upload/cutimage",
				type:"post",
				data:{
					x:$("#imgX").val(),y:$("#imgY").val(),w:$("#imgW").val(),h:$("#imgH").val(),p:$("#imgPath").val()
				},
				dataType:"json",
				beforeSend:function(){
					$("#info_img_loading").show();
				},
				complete:function(){
				},
				timeout:20000,
				success:function(root){
					if(root.flag == "1"){
						$("#user_avatar").attr("src",root.avatar_big);
						avatarBox.hide();
					}else{
						errorBox.show(root.message);
						return false;
					}
				}
			});
		});
	},
	show:function(){
		avatarBox.init();
		lock.show();
		loadingBox.show("修改框加载中");
		setTimeout(function(){
			loadingBox.hide();
			$("#avatar_box").show();
		},200);
	},
	hide:function(){
		$("#avatar_box").remove();
		lock.hide();
	}
}

function infoCutImage(){
	$("#info_show_img").Jcrop({
		aspectRatio:1,
		setSelect:[0,0,110,110],
		onChange:showPreview,
		onSelect:showPreview,
		boxWidth:282,
		boxHeight:183
	});	
	function showPreview(coords){
		if(parseInt(coords.w) > 0){
			var rx = $("#info_upload_preview_box").width() / coords.w; 
			var ry = $("#info_upload_preview_box").height() / coords.h;
			$("#info_crop_preview").css({
				width:Math.round(rx * $("#info_show_img").width()) + "px",	//预览图片宽度为计算比例值与原图片宽度的乘积
				height:Math.round(rx * $("#info_show_img").height()) + "px",	//预览图片高度为计算比例值与原图片高度的乘积
				marginLeft:"-" + Math.round(rx * coords.x) + "px",
				marginTop:"-" + Math.round(ry * coords.y) + "px"
			});
			$("#imgX").val(coords.x);
			$("#imgY").val(coords.y);
			$("#imgW").val(coords.w);
			$("#imgH").val(coords.h);
		}
	}
}

function uploadAvatarCallBack(data){
	var url = data.url;
	$("#info_show_img").attr("src",url);
	$("#info_crop_preview").attr("src",url);
	$("#imgPath").val(url);
	infoCutImage();
	$("#info_upload_show_loading").hide();
	$("#info_upload_show").fadeIn(200);
	$("#info_upload_preview_box_border").show();
}