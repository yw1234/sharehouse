var TextDefault = {
    _support: (function() {
        return 'placeholder' in document.createElement('input');
    })(),
    className: 'TextDefaultColor',

    init: function() {
    		$('input,textarea').bind({
    			focus:function(){
    				$(this).css({"box-shadow":"inset 0px 0px 5px #e68303"});
    			},
    			blur:function(){
    				$(this).css({"box-shadow":"none"});
    			}
    		});
        if (!TextDefault._support) {
             $('input[TextDefault],textarea[TextDefault]').each(function(){
            		TextDefault.create(this);
             });
        }else{
        	 $('input,textarea').each(function(){
         		$(this).attr("placeholder",$(this).attr("TextDefault"));
          });
        }
    },
    create: function(input) {
        if (!TextDefault._support && $(input).attr('TextDefault')) {
        	if($(input).val().trim() === ''){
            	$(input).val($(input).attr('TextDefault'));
            }
            if($(input).attr('TextDefault')===$(input).val()){
                $(input).addClass(TextDefault.className);
            }
            $(input).bind({
                focus:function(){
                    if($(input).attr('TextDefault')===$(input).val() || $(input).val().trim() === '')
                    {
                        $(input).val("");
                        $(input).removeClass(TextDefault.className);
                    }
                },
                blur:function(){
                    if ($(input).val().trim() === '') {
                        $(input).val($(input).attr('TextDefault'));
                        $(input).addClass(TextDefault.className);
                    }
                }
            });
        }

    }
};

function numbervalidate(){
    var evt = getEvent();
    var rest = false;
    if(evt){
    	rest = true;
    	var element = evt.srcElement || evt.target;
    	evt = evt.keyCode || evt.charCode;
    	var text = element.value;
    	if ((evt <= 57 && evt >= 48) || (evt <= 105 && evt >= 96) || (evt == 8)){
        	rest =  true;
        } else {
         rest =  false;
        }
    }
    return rest;
}
function getEvent(){
	if(document.all){
		return window.event;
	}else{
		func = getEvent.caller;
		while(func != null){
			var arg0 = func.arguments[0];
			if(arg0){
				return arg0;
			}
			func = func.caller;
		}
		return null;
	}
}

function stopEvent(e){
	if (e&&e.preventDefault) { // Firefox  
		e.preventDefault();     
		e.stopPropagation(); 
	}else { // IE
		window.event.cancelBubble = true;
		window.event.returnValue=false; 
	    return false;     
	}   
}

function insert_content(text,id) {
    var obj = text[0];
	selection = document.selection;
	obj.focus();
	if(obj.value == "" || obj.value == obj.getAttribute("TextDefault")){
		obj.style.color = "#535353";
    		obj.value = "";
    }
	if(obj.selectionStart) {
		obj.value = obj.value.substr(0, obj.selectionStart) + id + obj.value.substr(obj.selectionEnd);
	}else if(selection && selection.createRange) {
		var CaretPos=0;
		var Sel_sub = selection.createRange();
		var Sel = selection.createRange();
		Sel.moveStart('character', -obj.value.length);  
		var t = Sel.text;
		for (var i = 0; i < obj.value.length; i++)  
            if (obj.value.substring(0, i + 1) == t.substring(t.length - i - 1, t.length))  
                CaretPos = i + 1;                            
		if(CaretPos==0&&obj.value.length>0)
			obj.value+=id;
		else Sel_sub.text = id;
	} else {
		obj.value += id;
	}
	text.focus();
}


function move(obj){
	  Evt2=getEvent();
	  var parW = obj.width();
	  var parH = obj.height();
	  var $=obj[0];
	  var x_cj=Evt2.clientX-$.offsetLeft;
	  var y_cj=Evt2.clientY-$.offsetTop;
	  var newx=0;
	  var newy=0;
	  var ism=1;
	  document.body.onmousemove=function(){moveit();};
	 function moveit(){
	    Evt=getEvent();
	    
	    if(ism==1){
		    newx=Evt.clientX-x_cj;
		    newy=Evt.clientY-y_cj;   
		    $.style.left=newx+"px";
		    $.style.top=newy+"px";
		    //防止区块拖出可见区
		    
		    if(newx<=0){
		      $.style.left="0px";
		     }
		    if(newx+parW >= document.documentElement.clientWidth){
		    	$.style.left=document.documentElement.clientWidth-parW;
		    }
		    if(newy <= document.body.scrollTop){
		    	$.style.top=document.body.scrollTop
		    }
		    if(newy+parH >= document.body.scrollTop+document.documentElement.clientHeight){
		    	$.style.top=document.body.scrollTop+document.documentElement.clientHeight-parH;
		    }
	    }else{
	    	document.body.style.cursor="";
	     }
	  }
	  document.body.onmouseup=function(){
	     document.body.style.cursor="default";
	     ism=0;
	   }
	  $.onmouseup=function(){
	     document.body.style.cursor="default";
	     ism=0;
	  }
}

var getActList={
	fillType:function(data){
		var t = data.idTitle;
		getActList.sendGeneralizeType({id:t+"1",val:data.t1});
		if(data.t1!=null&&data.t1!=""){
			getActList.sendDetailType({id:t+"2",gType:data.t1,val:data.t2});
		}
		
	},
	fillPlace:function(data){
		var t = data.idTitle;
		getActList.province({id:t+"province",target_province:data.province});
		if(data.province!=null&&data.province!=""&&data.city!=null&&data.city!="")
			getActList.city({id:t+"city",province:data.province,target_city:data.city});
		else return false;
		if(data.area!=null&&data.area!="")
			getActList.area({id:t+"area",province:data.province,city:data.city,target_area:data.area});
		else return false;
	},
	province:function(data){
		var id = data.id;
		var tar_province = data.target_province;
		$.post(
			"/common/getplace",
			{type:"province"},
			function(data){
				if(data.flag.match("ok")){
					provinceArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					for(i = 1 ; i < provinceArr.length; i++)
					{
						document.getElementById(id).options[i] = new Option(provinceArr[i-1],provinceArr[i-1]);
						if(tar_province!=null&&tar_province!=""&&provinceArr[i-1]==tar_province)
							document.getElementById(id).options[i].selected=true;
					}
				}
			},"json"
		);
	},
	city:function(data){
		var id = data.id;
		var areaid = data.areaid;
		var province = data.province;
		var tar_city=data.target_city;
		$.post(
			"/common/getplace",
			{type:"city",province:data.province},
			function(data){
				if(data.flag.match("ok")){
					document.getElementById(id).length = 0;
					cityArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					for(i = 0 ; i < cityArr.length-1; i++)
					{
						document.getElementById(id).options[i] = new Option(cityArr[i],cityArr[i]);
						if(tar_city!=null&&tar_city!=""&&cityArr[i]==tar_city)
							document.getElementById(id).options[i].selected=true;
					}
					if(tar_city==null&&cityArr!=""){
						getActList.area({id:areaid,province:province,city:cityArr[0]});
					}
				}
			},"json"
		);
	},
	area:function(data){
		var id = data.id;
		var tar_area=data.target_area;
		$.post(
			"/common/getplace",
			{type:"area",province:data.province,city:data.city},
			function(data){
				if(data.flag.match("ok")){
					document.getElementById(id).length = 0;
					areaArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					for(i = 0 ; i < areaArr.length; i++)
					{
						document.getElementById(id).options[i] = new Option(areaArr[i],areaArr[i]);
						if(tar_area!=null&&tar_area!=""&&areaArr[i]==tar_area)
							document.getElementById(id).options[i].selected=true;
					}
				}
			},"json"
		);
	},
	department:function(data){
		var id = data.id;
		var dep = data.dep_default;
		var fun = data.callback;
		$.post(
			"/common/getdepartment",
			{cname:data.school},
			function(data){
				if(data.flag.match("ok")){
					deptArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					for(i = 1 ; i < deptArr.length; i++)
					{
						document.getElementById(id).options[i] = new Option(deptArr[i-1],deptArr[i-1]);
						if(dep!=null && dep==deptArr[i-1]){
							document.getElementById(id).options[i].selected=true;
						}
					}
				}
				fun();
			},"json"
		);
	},
	sendGeneralizeType:function(data){
		var id = data.id;
		var gtype = data.val;
		$.post(
			"/common/getsendtype",
			{type:"generalize"},
			function(data){
				if(data.flag.match("ok")){
					typeArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					for(i = 1 ; i < typeArr.length; i++)
					{
						document.getElementById(id).options[i] = new Option(typeArr[i-1],typeArr[i-1]);
						if(gtype!=null && gtype==typeArr[i-1]){
							document.getElementById(id).options[i].selected=true;
						}
					}
				}
			},"json"
		);
		
	},sendDetailType:function(data){
		var id = data.id;
		var dtype = data.val;
		$.post(
			"/common/getsendtype",
			{type:"detail",gType:data.gType},
			function(data){
				if(data.flag.match("ok")){
					typeArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
					document.getElementById(id).length = 0;
					document.getElementById(id).options[0] = new Option("请选择","");
					for(i = 1 ; i < typeArr.length; i++)
					{
						document.getElementById(id).options[i] = new Option(typeArr[i-1],typeArr[i-1]);
						if(dtype!=null && dtype==typeArr[i-1]){
							document.getElementById(id).options[i].selected=true;
						}
					}
				}
			},"json"
		);
	}
}

var slideBlock={
	slideUp:function(su){
		var pn = parseInt(su.pageno/su.size)-((su.pageno%su.size==0)?1:0);
		if($("#"+su.id).css("top").substring(0,$("#"+su.id).css("top").length-2)<((1-pn)*su.height-10))return false;
		su.btn[0].onclick=function(){return false;}
		var top = Number($("#"+su.id).css("top").substring(0,$("#"+su.id).css("top").length-2))-su.height+"px";
		$("#"+su.id).animate({
			top:top
		},su.speed);
		setTimeout(function(){su.btn[0].onclick=function(){slideBlock.slideUp(su);}},su.speed);
	},
	slideDown:function(sd){
		if($("#"+sd.id).css("top").substring(0,$("#"+sd.id).css("top").length-2)>-10)return false;
		sd.btn[0].onclick=function(){return false;}
		var top = Number($("#"+sd.id).css("top").substring(0,$("#"+sd.id).css("top").length-2))+sd.height+"px";
		$("#"+sd.id).animate({
			top:top
		},sd.speed);
		setTimeout(function(){sd.btn[0].onclick=function(){slideBlock.slideDown(sd);}},sd.speed);
	}
}

var textLimit={
	getLength:function(data){
		var p=data.id,
			length = data.length || 140;
		var value = data.target_text.val().length+"/"+length;
		if(data.target_text.val().length > length) $("#"+p).css({"color":"red"});
		else $("#"+p).css({"color":"grey"});
		$("#"+p).html(value);
	},
	validation:function(data){
		var length = data.length || 140;
		if(data.target_text.val().length > length) return false;
		else return true;
	},
	reset:function(data){
		var length = data.length || 140;
		$("#"+data.id).css({"color":"grey"}).html("0/"+length);
	}
}
//url操作
var url = {
	getParams:function(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	},
	del:function(loc,ref){
		var str = "";
	    if (loc.indexOf('?') != -1)
	        str = loc.substr(loc.indexOf('?') + 1);
	    else
	        return loc;
	    var arr = "";
	    var returnurl = "";
	    var setparam = "";
	    if (str.indexOf('&') != -1) {
	        arr = str.split('&');
	        for (i in arr) {
	            if (arr[i].split('=')[0] != ref) {
	                returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
	            }
	        }
	        return loc.substr(0, loc.indexOf('?')) + "?" + returnurl.substr(0, returnurl.length - 1);
	    }
	    else {
	        arr = str.split('=');
	        if (arr[0] == ref)
	            return loc.substr(0, loc.indexOf('?'));
	        else
	            return loc;
	    }
	}
}

//获取元素绝对位置
function getTop(e){ 
	var offset=e.offsetTop; 
	if(e.offsetParent!=null) 
		offset+=getTop(e.offsetParent); 
	return offset; 
} 

function getLeft(e){ 
	var offset=e.offsetLeft; 
	if(e.offsetParent!=null) 
		offset+=getLeft(e.offsetParent); 
	return offset; 
}


function getTargetId(target){
	var id = target.attr("id").split("_");
	return id[id.length-1];
}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,'');
}


function getCenterPlace(id){
	var st = document.body.scrollTop+document.documentElement.scrollTop+(document.documentElement.clientHeight-$("#"+id).height())/2+"px";
	var sl =($("html").width()-$("#"+id).width())/2+"px";
	$("#"+id).css({"left":sl,"top":st});
}

function getFaceList(id,target_text,textlimitid){
	var bp = $('#basePath').val();
	var yct_block=$('#yct_face_block'),al_block=$('#al_face_block'),bo_block=$('#bobo_face_block');
	if(yct_block.html().trim()==""){
		var yct_face='';
		for(i=1 ; i<42;i++){
			yct_face+='<div class="face_icon yct"><img src="'+bp+'web/image/base/face/yct/yct-'+i+'.gif" id="'+i+'"/></div>';
		}
		yct_block.html(yct_face);
	}
	if(al_block.html().trim()==""){
		var al_face='';
		for(i=1 ; i<42;i++){
			al_face+='<div class="face_icon al"><img src="'+bp+'web/image/base/face/ali/al-'+i+'.gif" id="'+i+'"/></div>';
		}
		al_block.html(al_face);
	}
	if(bo_block.html().trim()==""){
		var bo_face='';
		for(i =1 ; i<49;i++){
			bo_face+='<div class="face_icon bobo"><img src="'+bp+'web/image/base/face/bobo/bobo-'+i+'.gif" id="'+i+'"/></div>';
		}
		bo_block.html(bo_face);
	}
	var left = getLeft($("#"+id)[0])-($("#facebox").width()-$("#"+id).width())/2+"px";
	var top = getTop($("#"+id)[0])+$("#"+id).height()+"px";
	$("#facebox").css({"left":left,"top":top});
	$("#facebox").show();
	$(".face_icon").unbind().bind(
		"click",function(){
			var type = $(this).attr("class").split(' ')[1]
            switch(type){
				case "yct":{insert_content(target_text,"[yc:"+$(this).children("img").attr("id")+":]");textLimit.getLength({id:textlimitid,target_text:target_text});break;}
				case "al":{insert_content(target_text,"[al:"+$(this).children("img").attr("id")+":]");textLimit.getLength({id:textlimitid,target_text:target_text});break;}
				case "bobo":{insert_content(target_text,"[bo:"+$(this).children("img").attr("id")+":]");textLimit.getLength({id:textlimitid,target_text:target_text});break;}
			}  
			$("#facebox").hide();
	}).bind("mouseover",function(){
		$("#face_preview_img").attr("src",$(this).children("img").attr("src"));
		$("#face_preview_box").show();
	}).bind("mouseout",function(){
		$("#face_preview_box").hide();
	});
	
	$(".face_type").click(function(){
		$(".face_type").removeClass("face_type_checked");
		$(this).addClass("face_type_checked");
		$(".face_block").hide();
		$("#"+$(this).attr("id")+"_block").show();
	});

}
//锁屏
var lock = {
	show:function(hiddenScroll,time,opac){
		var t = time || 400
			,o = opac || 0.3
			,_div = document.createElement("div");
		_div.id = "pageOverlay";
		_div.style.display = "block";
		_div.style.width = "100%";
		_div.style.height = "100%";
		_div.style.position = "fixed";
		_div.style.top = "0px";
		_div.style.left = "0px";
		_div.style.zIndex = "100";
		_div.style.background = "#000000";
		_div.style.opacity = 0.1;
		_div.style.filter = "alpha(opacity=10)";
		document.body.appendChild(_div);
		$(_div).animate({"opacity":"0.3"},t);
	},
	hide:function(hiddenScroll,time){
		var t = time || 400,
			_div = document.getElementById("pageOverlay");
		$(_div).animate({"opacity":"0"},t,function(){document.body.removeChild(_div);});
	},
	isLocking:function(){
		if($("#pageOverlay")[0])
			return true;
		else return false;
	},
	addStrengthLock:function(){
		$("#pageOverlay_strength").show();
	},
	cancelStrengthLock:function()
	{$("#pageOverlay_strength").hide();}
}

//上传基本操作
var upload = {
	init:function(){
		if($("#idle_upload")[0])
			$("#idle_upload").uploadify({
				fileObjName :'file',
				swf : 'web/swf/uploadify.swf', 
				uploader : '/upload/idleImages', 
				fileTypeDesc : '支持的图片格式为：gif，jpg，png',
				fileTypeExts :'*.gif; *.jpg; *.png; *.JPG; *.PNG; *.GIF; *.jpeg; *.JPEG;',
				fileSizeLimit : '5MB',
				buttonText : "快速上传",
				width : '130',
				height : '30',
				auto : true , 
				multi : true ,
				onUploadStart : function(file) {
					mtxx.hide();
					idlePicUpload.addImg();
		        },
				onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal){
					//$('.img_uploaded_percent').show().html((100*bytesUploaded/bytesTotal) + '%');
				},
				onFallback : function(){
					var url ='<a style = "color:#e68303;" target="_blank" href="http://get.adobe.com/flashplayer/?promoid=JZEFT"  onclick="s_objectID=&quot;http://get.adobe.com/flashplayer/?promoid=JZEFT_1&quot;;return this.s_oc?this.s_oc(e):true">下载Adobe Flash Player</a>'
					errorBox.show("目测你还没有安装flash哦~下载一个吧 "+url);
		        },
				onUploadSuccess	: function(file, root, response){
					idlePicUpload.callBack(root)
				},
				//错误处理
				onUploadError:function(file, errorCode, errorMsg, errorString) {
					errorBox.show("上传失败，请重新再试")
				}
			}); 
	},
	callBack:function(data){
		var flag = data.flag;
		if(flag=="1"){
			var func_callback = data.func;
			func_callback();
		}else{
		 	errorBox.show("上传失败,请稍后再试!"); 
		}
	},
	validator:function(data){
		var file = document.getElementById(data.id);
		for(i = 0 ; i < file.length;i++){
			var ext = file[i].value.substr(file[i].value.length - 4, file[i].value.length);
			if(file[i].value == ""){
				alert("请选择上传文件");
				return false;
			}
			if(ext == '' || (ext != '.JPG' && ext != '.jpg' && ext != 'JPEG' && ext != 'jpeg'&& ext != '.gif' && ext != '.GIF' && ext != '.PNG' && ext != '.png')){
				alert("请上传JPG、JPEG、GIF、PNG格式图片");
				return false;
			}
		}
		if($("#idleUploadBox")[0])
			idleUploadBox.hide();
		return true;
	}
}


// 各种盒子
 
var confirmBox = {
	show:function(data){
		stopEvent(window.event || arguments.callee.caller.arguments[0]);
		var box = '<div id = "confirm_box" style = "position:absolute;width:300px;max-height:152px;background:url(/web/image/base/msgBox_bg.png);box-shadow:0px 0px 15px #333333;z-index:300;border:1px solid #b3b3b3;display:none;border-radius:5px;">'+
				'	<div style = "float:left;width:260px;margin:20px;">'+
				'		<span id = "confirm_text" style = "float:left;text-align:left;font-size:16px;text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.6);">'+data.msg+'</span>'+
				'	</div>'+
				'	<div class = "button" style = "width:60px;height:25px;line-height:26px;border:0px;font-size:14px;" onclick="confirmBox.confirm()">确定</div>'+
				'	<div class = "button" style = "margin-left:30px;width:60px;height:25px;line-height:26px;border:0px;font-size:14px;" onclick="confirmBox.cancel()">取消</div>'+
				'	<div style = "float:left;width:100%;height:10px;">'+
				'		<input type="hidden" id="confirm_box_showPageOverlay" value="0"/>'+
				'	</div>'+
				'</div>';
		$("body").append(box);
		if(lock.isLocking())
			$("#confirm_box_showPageOverlay").val("1");
		else lock.show();
		lock.addStrengthLock();
		func = data.func;
		getCenterPlace("confirm_box");
		$("#confirm_box").show();
	},
	confirm:function(){
		func();
		confirmBox.hide();
	},
	cancel:function(){
		confirmBox.hide();
	},
	hide:function(){
		if($("#confirm_box_showPageOverlay").val()=="0"){
			lock.hide();
		}
		lock.cancelStrengthLock();
		$("#confirm_box").remove();
	}
};

var errorBox = {
	show:function(msg){
		var box = '<div id = "error_box" style = "position:absolute;width:300px;background:url(/web/image/base/msgBox_bg.png);box-shadow:0px 0px 15px #333333;z-index:300;border:1px solid #b3b3b3;display:none;border-radius:5px;">'+
				'	<div style = "float:left;width:260px;;margin:20px;">'+
				'		<span id = "error_text" style = "font-size:16px;text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.6);">'+msg+'</span>'+
				'	</div>'+
				'	<div class = "button" style = "width:60px;height:25px;line-height:26px;border:0px;font-size:14px;border-radius:3px;" onclick="errorBox.hide()">确定</div>'+
				'	<div style = "float:left;width:100%;height:10px;">'+
				'		<input type="hidden" id="error_box_showPageOverlay" value="0"/>'+
				'	</div>'+
				'</div>';
		$("body").append(box);
		lock.addStrengthLock();
		getCenterPlace("error_box");
		$("#error_box").show();
	},
	hide:function(){
		lock.cancelStrengthLock();
		$("#error_box").remove();
	}
};
var successBox = {
	show:function(msg,time){
		var box = '<div id = "success_box" style = "display:none;position:absolute;width:300px;background:url(/web/image/base/msgBox_bg.png);box-shadow:0px 0px 20px #333333;z-index:200;border:1px solid #b3b3b3;border-radius:5px;">'+
				'	<div style = "width:240;margin:30px;">'+
				//'		<img src = "/web/image/base/icon/succ_icon.png" style = "vertical-align:middle;"/>'+
				'		<span id = "success_text" style = "text-align:left;line-height:25px;margin-left:5px;font-size:16px;text-shadow: 1px 1px 0 rgba(255, 255, 255, 0.6);">'+msg+'</span>'+
				'	</div>'+
				'</div>';
		$("body").append(box);
		getCenterPlace("success_box");
		$("#success_box").show();
		setTimeout(function(){successBox.hide()},time!=null?time:1000);
	},
	hide:function(){
		$("#success_box").fadeOut(500,function(){$(this).remove();});
	}
};

var loadingBox = {
	show:function(msg){
		var box = '<div id = "loading_box" style = "position:absolute;width:320px;height:50px;background:white;border:2px solid #e4e3e3;border-radius:5px;z-index:105;line-height:50px;display:none;box-shadow:0px 0px 15px #4a4a4a;">'+
					'<span id = "loading_box_text">'+msg+'</span><img src = "/web/image/register/loading.gif" />'+
				'</div>';
		$("body").append(box);
		getCenterPlace("loading_box");
		$("#loading_box").show();
	},
	hide:function(){
		$("#loading_box").remove();
	}
}; 

var complaintBox = {
	show:function(data){
		var box ='<div id = "complaint_box" style = "position:absolute;width:400px;background:white;left:0px;top:50px;border-radius:10px;display:none;border:2px solid #c1c1c1;z-index:108;">'+
				'	<div style = "float:left;border-radius:0px;width:400px;height:30px;line-height:30px;cursor:move;" onmousedown = "move($(this).parent())"><span style = "color:#e68303;font-size:16px;">举报框</span>'+
				'	</div>'+
				'	<div style="float:left;width:370px;margin-left:30px;margin-top:5px;">'+
				'		<span style="float:left;font-size:15px;">举报<label id="complaint_username" style="color:#e68303;">'+data.name+'</label></span>'+
				'	</div>'+
				'	<div style = "float:left;margin-top:5px;width:296px;">'+
				'		<div style = "float:left;width:200px;margin-left:30px;margin-top:5px;height:20px;text-align:left;">'+
				'			<input type = "radio" name = "complaint_reason" checked/>'+
				'			<span style = "font-size:14px;">发布虚假或不良信息</span>'+
				'		</div>'+
				'		<div style = "float:left;width:200px;margin-left:30px;margin-top:5px;height:20px;text-align:left;">'+
				'			<input type = "radio" name = "complaint_reason"/>'+
				'			<span style = "font-size:14px;">骚扰</span>'+
				'		</div>'+
				'		<div style = "float:left;width:200px;margin-left:30px;margin-top:5px;height:20px;text-align:left;">'+
				'			<input type = "radio" name = "complaint_reason"/>'+
				'			<span style = "font-size:14px;">恶意灌水</span>'+
				'		</div>'+
				'		<div style = "float:left;width:200px;margin-left:30px;margin-top:5px;height:20px;text-align:left;">'+
				'			<input type = "radio" name = "complaint_reason"/>'+
				'			<span style = "font-size:14px;">谩骂他人</span>'+
				'		</div>'+
				'		<div style = "float:left;width:200px;margin-left:30px;margin-top:5px;height:20px;text-align:left;">'+
				'			<span style = "font-size:14px;">其他原因:</span>'+
				'		</div>'+
				'		<textarea id = "complaint_text" style = "float:left;margin-top:5px;margin-left:50px;width:300px;height:50px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;"></textarea>'+
				'		<div style = "float:left;margin-top:10px;width:100%;">'+
				'			<div class = "button" style = "float:right;margin-right:25px;width:60px;height:25px;line-height:25px;font-size:15px;" onclick = "complaintBox.hide();">取消</div>'+
				'			<div class = "button" style = "float:right;margin-right:25px;width:60px;height:25px;line-height:25px;font-size:15px;" onclick = "complaintBox.confirm();">确定</div>'+
				'		</div>'+
				'		<div style = "float:left;width:100%;height:15px;"></div>'+
				'		<input type="hidden" id="complaint_userid" value = "'+data.id+'"/>'+
				'		<input type="hidden" id="complaint_box_showPageOverlay" value="0"/>'+
				'	</div>'+
				'</div>';
		$("body").append(box);
		getCenterPlace("complaint_box");
		if(lock.isLocking()){
			$("#complaint_box_showPageOverlay").val("1");
		}else lock.show();
		$("#complaint_box").show();
	},
	hide:function(){
		if($("#complaint_box_showPageOverlay").val()!="1")
			lock.hide();
		$("#complaint_box").remove();
	},
	confirm:function(){
		var reason,content = $("#complaint_text").val();
		$("input[name='complaint_reason']").each(function(){
			if($(this).attr("checked")=="checked")
				reason=$(this).parent().children("span").html();
		});
		if(content.length>140){errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/send/complaint",
			type:"post",
			data:{
				senderid:$("#complaint_userid").val(),
				complaintType:reason,
				content:content
			},
			dataType:"text",
			timeout:20000,
			success:function(message){
				if(message.match("ok")){
					successBox.show("举报成功,管理员会尽快处理");
					complaintBox.hide();
				}else{
					errorBox.show("举报失败,请稍后再试");
					return false;
				}
			}
		});
	}
}

var suggestBox = {
	show:function(){
		var box = '<div id = "suggest_box" style = "position:absolute;width:400px;background:white;left:0px;top:100px;border-radius:5px;z-index:102;display:none;box-shadow:0px 0px 15px #4a4a4a;">'+
				'		<div style = "float:left;border-radius:0px;width:400px;height:30px;line-height:30px;cursor:move;" onmousedown = "move($(this).parent())"><span style = "color:#e68303;font-size:16px;">建议我们</span>'+
				'		</div>'+
				'		<div style = "float:left;border-top:0px;width:296px;">'+
				'			<div style = "float:left;width:370px;margin-left:20px;margin-top:5px;line-height:20px;">'+
				'				<span style = "float:left;margin-left:5px;font-size:15px;">对圈易物什么建议,或者找到了bug神马,快告诉我们吧~</span>'+
				'			</div>'+
				'			<textarea id = "suggest_text" style = "float:left;margin-top:15px;margin-left:50px;width:300px;height:50px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;" TextDefault="我们一定尽最大努力改进与完善~"></textarea>'+
				'			<div style = "float:left;margin-top:10px;width:100%;">'+
				'				<div class = "button" style = "float:right;margin-right:25px;width:60px;height:22px;line-height:23px;font-size:15px;border-radius:3px;" onclick="suggestBox.hide();">取消</div>'+
				'				<div class = "button" style = "float:right;margin-right:25px;width:60px;height:22px;line-height:23px;font-size:15px;border-radius:3px;" onclick="suggestBox.confirm();">确定</div>'+
				'			</div>'+
				'			<div style = "float:left;width:100%;height:15px;"></div>'+
				'		</div>'+
				'	</div>';
		$("body").append(box);
		getCenterPlace("suggest_box");
		if(lock.isLocking()){
			$("#suggest_box_showPageOverlay").val("1");
		}else lock.show();
		$("#suggest_text").val("").blur();
		$("#suggest_box").show();
	},
	hide:function(){
		if($("#suggest_box_showPageOverlay").val()!="1")
			lock.hide();
		$("#suggest_box").remove();
	},
	confirm:function(){
		var content = $("#suggest_text").val();
		if(content.trim()==""||$("#suggest_text").attr("TextDefault")==content){return false;}
		if(content.length>140){errorBox.show("字数有点多哦~");return false;}
		$.ajax({
			url:"/send/suggest",
			type:"post",
			data:{
				content:content
			},
			dataType:"text",
			timeout:20000,
			success:function(message){
				if(message.match("ok")){
					successBox.show("建议成功!我们会尽快处理");
					suggestBox.hide();
				}else{
					errorBox.show("建议失败,请稍后再试");
					return false;
				}
			}
		});
	}
}

var replyBox = {
	show:function(data){
		stopEvent(window.event || arguments.callee.caller.arguments[0]);
		var box = '<div id = "msg_reply_box" style="position:absolute;display:none;width:480px;height:110px;background:white;border-radius:5px;border:2px solid #c1c1c1;z-index:108;box-shadow:0px 0px 15px #4a4a4a;">'+
					'	<div style = "float:left;width:100%;height:35px;"><span id = "msg_reply_title" style = "float:left;margin-left:30px;line-height:35px;font-size:14px;">私信<a id = "reply_userLink" href = "/user/'+data.userid+'" target="_blank" style="color:#e68303;">'+data.userName+'</a></span></div>'+
					'	<div style = "float:left;width:100%;">'+
					'		<textarea id = "msg_reply_text" style = "float:left;margin-top:5px;margin-left:30px;width:300px;height:50px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;" onkeyup="textLimit.getLength({id:\'msg_reply_textlimit\',target_text:$(this)})"></textarea>'+
					'		<div style="float:left;width:50px;margin-top:5px;">'+
					'			<div class = "facelist face-switch" id = "msg_reply_facelist" onclick = "getFaceList(\'msg_reply_facelist\',$(\'#msg_reply_text\'),\'msg_reply_textlimit\');"><img src = "/web/image/base/face.png" style = "float:left;"/></div>'+
					'			<div id = "msg_reply_textlimit" style = "float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:10px;">0/140</div>'+
					'		</div>'+
					'	<div style = "float:left;width:50px;margin-left:15px;">'+
					'		<input type = "hidden" id = "msg_box_senderid" value = "'+data.userid+'">'+
					'		<a href = "javascript:void:(0)" class = "button" style = "float:left;width:50px;height:22px;line-height:23px;margin-top:5px;border-radius:3px;" onclick = "replyBox.submit()">确定</a>'+
					'		<a href = "javascript:void:(0)" class = "button" style = "float:left;width:50px;height:22px;line-height:23px;margin-top:10px;border-radius:3px;" onclick = "replyBox.cancel()"> 取消</a>'+
					'	</div>'+
					'</div>'+
				 '</div>';
		$("body").append(box);
		lock.show();
		getCenterPlace("msg_reply_box");
		$("#msg_reply_box").show();
		$("#msg_reply_text").focus();
	},
	submit:function(){
		var senderid = $("#msg_box_senderid").val();
		var tar_text = $("#msg_reply_text");
		var text = tar_text.val();
		if(text==tar_text.attr("TextDefault")||text.trim()==""){
			errorBox.show("至少得说句话吧--||");
			return false;
		}
		if(!textLimit.validation({target_text:tar_text})) {
			errorBox.show("字数有点多哦~");
			return false;
		}
		$.ajax({
			url:"/message/sendPrivate",
			type:"post",
			data:{userid:senderid,content:text},
			dataType:"json",
			beforeSend:function(){
				$("#reply_loading").show();
			},
			complete:function(){
				$("#reply_loading").hide();
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("私信发送成功");
					replyBox.hide();
				}else errorBox.show(root.message);
			},error:function(){
				errorBox.show("发送失败,请稍后再试");
			}
		});
	},
	cancel:function(){
		replyBox.hide();
	},
	hide:function(){
		$("#msg_reply_box").remove();
		lock.hide();
	}
}

var introduceBox = {
	showingId:"",
	delayTime:400,
	init:function(){
		$("[uCard]").unbind("hover").hover(
			function(){
				introduceBox.show({target:$(this),id:$(this).attr("uCard")});
			},
			function(){
				introduceBox.hide({target:$(this),id:$(this).attr("uCard")});
			}
		);
		
	},
	show:function(data){
		var basePath = $("#basePath").val()
			,id = data.id
			,$ava = data.target,
			userid = id;
		if(introduceBox.showingId!='userCardBox_'+id){
			$(".userCardBox").fadeOut(200);
			introduceBox.showingId = "userCardBox_"+id;
		}
		if(!$("#userCardBox_"+id)[0]){
			var box = '';
			box +='<div class = "userCardBox" id = "userCardBox_'+id+'" style = "position:absolute;width:300px;min-height:100px;background:white;border-radius:6px;z-index:90;cursor:default;border:2px solid #d8d8d8;display:none;box-shadow: 0px 0px 10px rgba(0,0,0,.5);">';
			box	+='		<img id = "userCardBox_loading_'+id+'" src = "'+basePath+'web/image/register/loading.gif" style = "margin-top:45px;"/>';
			box +='		<div id = "userCardBox_main_'+id+'" style = "float:left;margin:5px;width:100%;display:none;">';
			box +='			<div style = "float:left;width:110px;">';
			box +='				<a href = "'+basePath+'user/'+userid+'" target = "_blank"><img id = "userCardBox_avatar_'+id+'" style = "float:left;width:100px;min-height:50px;"/></a>';
			box +='			</div>';
			box +='		<div style = "float:left;width:180px;margin-top:5px;">';
			box +='			<div style = "float:left;width:100%;">';
			box +='				<img id = "userCardBox_sex_'+id+'" style = "float:left;height:18px;"/>';
			box +='				<a href = "'+basePath+'user/'+userid+'" target = "_blank"><span id = "userCardBox_name_'+id+'" style = "float:left;color:#e68303;margin-left:5px;"></span></a>';
			box +='			</div>';
			box +='			<div class = "userCardBox_account" style = "float:left;width:100%;margin-top:5px;">';
			box +='				<span id = "userCardBox_account_'+id+'" style = "float:left;text-align:left;"></span>';
			box +='			</div>';
			box +='			<div style = "float:left;width:100%;margin-top:5px;">';
			box +='				<span id = "userCardBox_school_'+id+'" style = "float:left;font-size:13px;text-align:left;"></span>';
			box +='			</div>';
			box +='			<div style = "float:left;width:100%;margin-top:3px;">';
			box +='				<span id = "userCardBox_department_'+id+'" style = "float:left;font-size:13px;text-align:left;"></span>';
			box +='			</div>';
			box +='			<div style = "float:left;width:100%;margin-top:3px;">';
			box +='				<span id = "userCardBox_friends_'+id+'" style = "float:left;font-size:13px;text-align:left;"></span>';
			box +='				<span id = "userCardBox_shareTimes_'+id+'" style = "float:left;font-size:13px;text-align:left;margin-left:10px;"></span>';
			box +='			</div>';
			box +='			<div id = "userCardBox_button_'+id+'" style = "float:left;width:100%;margin-top:2px;border-top:1px solid #d8d8d8;">';
			box +='				<input type = "hidden" id = "userCardBox_continueShow_'+id+'" value = false />';
			box +='			</div>';
			box +='		</div>';
			box +='	</div>';
			box +='</div>';
			//加上box
			$("body").append(box);
			var $userCardBox = $("#userCardBox_"+id)
				,$ucb_name = $("#userCardBox_name_"+id)
				,$ucb_sex = $("#userCardBox_sex_"+id)
				,$ucb_avatar = $("#userCardBox_avatar_"+id)
				,$ucb_account = $("#userCardBox_account_"+id)
				,$ucb_school = $("#userCardBox_school_"+id)
				,$ucb_department = $("#userCardBox_department_"+id)
				,$ucb_friendsCount = $("#userCardBox_friends_"+id)
				,$ucb_shareTimes = $("#userCardBox_shareTimes_"+id)
				,$ucb_button = $("#userCardBox_button_"+id)
				,$ucb_continueShow = $("#userCardBox_continueShow_"+id);
				$.ajax({
					url:"/getuserIntroduce",
					type:"post",
					data:{
						senderid:userid
					},
					dataType:"json",
					complete:function(){
						$("#userCardBox_loading_"+id).hide();
					},
					timeout:20000,
					success:function(root){
						if(root.flag == "1"){
							var accountInfo = "";
							if(root.userInfo.isPass == "1")
								accountInfo += "<img src = '/web/image/base/icon/email_icon_activate.png' style = 'float:left;height:15px;margin-right:10px;' title = '已通过账号验证'/>"
							if(root.renrenbind == "1")
								accountInfo += "<img src = '/web/image/base/icon/renren_icon.png' style = 'float:left;height:15px;margin-right:10px;' title = '已绑定人人'/>"
							if(accountInfo.trim() != "")
								$ucb_account.html(accountInfo);
							else $(".userCardBox_account").remove();
							$ucb_name.html(root.userInfo.name);
							$ucb_sex.attr("src",'/web/image/base/icon/icon_'+root.userInfo.sex+'.png');
							$ucb_avatar.attr("src",root.userInfo.avatar);
							$ucb_school.html(root.userInfo.school);
							$ucb_department.html(root.userInfo.department+" "+root.userInfo.educational+root.userInfo.grade);
							$ucb_friendsCount.html("好友<label style='color:#e68303;'>"+root.userInfo.friendsCount+"</label>人");
							$ucb_shareTimes.html("分享<label style='color:#e68303;'>"+root.userInfo.shareTimes+"</label>次");
							if(root.areFriends=="0"&&root.isSelf=="0"){
								$ucb_button.append('	<a class = "button" onclick = "introduceBox.addFriends({userid:'+userid+',btn:$(this)});" style = "float:left;width:55px;height:20px;line-height:21px;margin-left:10px;border-radius:3px;margin-top:8px;">加好友</a>');
							}
							if(root.isSelf=="0"){
								$ucb_button.append('	<a class = "button" onclick = "replyBox.show({userid:'+userid+',userName:\''+root.userInfo.name+'\'});" style = "float:left;width:75px;height:20px;line-height:21px;margin-left:10px;border-radius:3px;margin-top:8px;">给Ta私信</a>');
							}
							$("#userCardBox_main_"+id).show();
						}else{
							errorBox.show(root.message);
							return false;
						}
					}
				});
		}else{
			var $userCardBox = $("#userCardBox_"+id);
			var $ucb_continueShow = $("#userCardBox_continueShow_"+id);
			$ucb_continueShow.val(true);
		}
		var top = getTop($ava[0]),left = getLeft($ava[0])-20;
		setTimeout(function(){
			$userCardBox.css({top:top+$ava.height()+"px",left:left+"px"}).show();
		},introduceBox.delayTime);
		
		$userCardBox.unbind("hover").hover(
			function(){$ucb_continueShow.val(true);},
			function(){
				$ucb_continueShow.val(false);
				setTimeout(function(){
					if($ucb_continueShow.val()=="true"){
						return false;
					}
					$userCardBox.fadeOut(200,function(){$(this).remove();});
				},introduceBox.delayTime);
			}
		);
		
	},hide:function(data){
		var $userCardBox = $("#userCardBox_"+data.id);
		var $ucb_continueShow = $("#userCardBox_continueShow_"+data.id);
		$ucb_continueShow.val(false);
		setTimeout(function(){
			if($ucb_continueShow.val()=="true"){
				return false;
			}
			$userCardBox.fadeOut(200,function(){$(this).remove();});
		},introduceBox.delayTime);
	},
	addFriends:function(data){
		$.ajax({
			url:"/message/sendRequest",
			type:"post",
			data:{
				userid:data.userid,
				reqType:"addFriends"
			},
			dataType:"json",
			beforeSend:function(){
				data.btn.unbind("click").addClass("button_do").html("发送中");
			},
			complete:function(){
				data.btn.html("已发送");
			},
			timeout:20000,
			success:function(root){
				if(root.flag == "1"){
					successBox.show("请求成功")
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	}
}


/**
 * 激活邮件
 */
var activate = {
	send:function(data){
		$.ajax({
			url:"/activate/send",
			type:"post",
			data:{email:data.email},
			dataType:"json",
			beforeSend:function(){
				lock.show();
				loadingBox.show("激活邮件发送中 ");
			},
			complete:function(){
				lock.hide();
				loadingBox.hide();
			},
			timeout:20000,
			success:function(root){
				errorBox.show(root.message);
			},error:function(){
				errorBox.show("发送失败,请稍后再试");
			}
		});
	}
}

var hasFlash = function(){
	var hasFlash = false, n = navigator;
    if (n.plugins && n.plugins.length) {
        for (var ii = 0; ii < n.plugins.length; ii++) {
            if (n.plugins[ii] && n.plugins[ii].name && n.plugins[ii].name.indexOf('Shockwave Flash') != -1) {
                hasFlash = true;
                break;
            }
        }
    } else if (window.ActiveXObject) {
        for (var ii = 10; ii >= 2; ii--) {
            try {
                var fl = new ActiveXObject('ShockwaveFlash.ShockwaveFlash.' + ii);
                if (fl) {
                    hasFlash = true;
                    break;
                }
            } catch (e) {
            }
        }
    } 
    return hasFlash;
};

//美图秀秀
var mtxx = {
	init:function(data){
		 xiuxiu.setUploadURL("http://www.quan15.com/"+data.method);
		 xiuxiu.setUploadType(2);
		 xiuxiu.setUploadDataFieldName ("file");
		 xiuxiu. setLaunchVars ("nav", "edit");
		 xiuxiu.setUploadArgs({'callBackFunc' : data.callBack,'uploadDir' : data.path ,'uploadType':"mtxx"});
		 xiuxiu.onClose = function(id) {
			$('#altContainer').remove();
			$("#meituBox").hide();
			lock.cancelStrengthLock();
		 }
		 xiuxiu.onUploadResponse= function(data,id) {
			 xiuxiu.close();
			 var func = eval(data); 
			 func();		
		 }
		 xiuxiu.onBeforeUpload = function (data, id)
		 {
		   var size = data. size;
		   if(size > 5 * 1024 * 1024)
		   { 
		     errorBox.show("图片大小不能超过5M");
		     return false; 
		   }
		   return true; 
		 }
	},
	show:function(){
		if(hasFlash()){
			lock.addStrengthLock();
			getCenterPlace('meituBox');
			$("#meituBox").show();
			xiuxiu.embedSWF('altContainer', 1,'530px', '470px');
		}else{
			var url ='<a style = "color:#e68303;" target="blank" href="http://get.adobe.com/flashplayer/?promoid=JZEFT"  onclick="s_objectID=&quot;http://get.adobe.com/flashplayer/?promoid=JZEFT_1&quot;;return this.s_oc?this.s_oc(e):true">下载Adobe Flash Player</a>'
			errorBox.show("目测你还没有安装flash哦~下载一个吧 "+url);
		}
	},
	hide:function(){
		xiuxiu.close();
	}
}
var topNav = {
	init:function(){
		$("#top_config").unbind().hover(function(){$(this).children("div").stop(true,true).slideToggle("100");});
		$("#toTop").unbind().click(function(){$("html, body").animate({ scrollTop: 0 }, 500);}).hover(function(){$(this).children("img").attr("src","/web/image/base/toTop.png")},function(){$(this).children("img").attr("src","/web/image/base/toTop_1.png")});
		$("#contactUs").unbind().hover(function(){$(this).children("img").attr("src","/web/image/base/contactUs.png")},function(){$(this).children("img").attr("src","/web/image/base/contactUs_1.png")});
		$("#search_button").unbind().hover(function(){$(this).attr("src","/web/image/home/search_button_mouseover.png");},function(){$(this).attr("src","/web/image/home/search_button.png")});
	},
	searchSubmit:function(){
		var ts = $("#top_search");
		if(ts.val().trim() != "" && ts.val().trim() != ts.attr("TextDefault").trim()){
			var param = "&key="+encodeURIComponent(encodeURIComponent(ts.val().trim()));
			location.href="/result?type=topSearch&sendType=share"+param.trim();
		} 
	}
}
var at = {
	init:function(){
		$(".at-icon").unbind().bind("click",function(){at.insert({target:$("#"+$(this).attr("target"))})});
		$.getJSON("/message/getFriendsAt",function(res){
			if(res.flag == "1"){
				var data = $.map(res.user,function(user) {
	                return {id:user[0],name:user[1],avatar:user[3],query:user[1]+" "+user[2]};
	            });
				 $("[at-field]").atwho({
	                at: "@",
	                alias: "at-mentions",
	                data:data,
	                tpl: "<li data-search = '\${query}' data-value='\${name}(\${id})'><img src ='\${avatar}' style='float:left;width:30px;height:30px;'>\${name}</li>",
	                limit: 6,
	                search_key:"query"
	            });
			}
		});
	},
	insert:function(data){
		insert_content(data.target,"@");
	}
}

//初始化
$(document).ready(function(){
	TextDefault.init();
	topNav.init();
	at.init();
	introduceBox.init();
	upload.init();
}).click(function(e) {
	//faceBox隐藏
	if($(e.target).hasClass('face-switch') || $(e.target).parent().hasClass('face-switch')) return;      
	else $('#facebox').hide();   
	//删除隐藏
	if($(e.target).hasClass('notesdel-switch')) return;
	else $('.notes_delete').css({"left":"-999px","top":"-999px"});
}).keypress(function(event){
	var e = event||window.event;
	if(e.keyCode == 13){
		//屏蔽提示盒子
		if($("#error_box")[0]) {errorBox.hide();return false;}
		//confirm
		if($("#confirm_box")[0]) {confirmBox.confirm();return false;}
		//发布物品
		if($("#sendShareBox")[0] && $("#sendShareBox").css("display")!="none"){send.goods();return false;}
		//私信
		if($("#msg_reply_box")[0]){replyBox.submit();return false;}
		//提交全区搜索
		if($("#top_search")[0]){topNav.searchSubmit();return false;}
    }
});


