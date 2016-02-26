$(function(){
	document.getElementById("info_province").options[0] = new Option("请选择","");
	document.getElementById("info_city").options[0] = new Option("请选择","");
	document.getElementById("inho_province").options[0] = new Option("请选择","");
	document.getElementById("inho_city").options[0] = new Option("请选择","");
	document.getElementById("inho_area").options[0] = new Option("请选择","");
	getActList.fillPlace({idTitle:"info_",province:$("#info_place").val().split("-")[0],city:$("#info_place").val().split("-")[1],area:null});
	getActList.fillPlace({idTitle:"inho_",province:$("#info_home").val().split("-")[0],city:$("#info_home").val().split("-")[1],area:$("#info_home").val().split("-")[2]});
	InitOperator.initBirthday({year:"info_year",month:"info_month",day:"info_day"});
	InitOperator.initConstellation({constellation:"info_constellation",month:"info_month",day:"info_day"});
	InitOperator.initXx({xx:"info_xx",year:"info_year"});
	//院系
	var y = new Date().getFullYear(),m = new Date().getMonth();
	var year = (m>=8)?y:y-1,dep = $("#u_department").val(),grade = $("#u_grade").val();
	document.getElementById("info_grade").options[0] = new Option("请选择","");
	document.getElementById("info_department").options[0] = new Option("请选择","");
	for(i = year ; i >= 2001; i--)
	{	var y = i-2000,g = (y<10)?"0"+y+"级":y+"级";
		document.getElementById("info_grade").options[y] = new Option(g,g);
		if(grade!=null&&grade==g)
			document.getElementById("info_grade").options[y].selected=true;
	}	
	getActList.department({school:$("#info_school").val(),id:"info_department",dep_default:dep});
	
	$(".province_select").unbind().change(function(){
		getActList.city({id:$(this).attr("id").substring(0,4)+"_city",areaid:$(this).attr("id").substring(0,4)+"_area",province:$(this).val()});
	});
	$("#inho_city").unbind().change(function(){
		getActList.area({id:$(this).attr("id").substring(0,4)+"_area",province:$("#inho_province").val(),city:$(this).val()});
	});
	$("#info_upload_input").unbind().change(function(){
		if(upload.validator({id:"info_upload_input"})){
			$("#info_upload_img").hide();
			$("#info_upload_show_loading").show();
			$("#info_upload_form").submit();
		}
		$(this).val("");
	});
	
	$("#changeico_btn").unbind().click(function(){
		$("#info_upload_block").slideDown(200);
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
					setTimeout(function(){window.location.reload(true);},1000);
				}else{
					errorBox.show(root.message);
					return false;
				}
			}
		});
	});
	
	$("#info_saveinfo").unbind().click(function(){
		if($("#info_name_prev").val()!=$("#info_name").val())
		{
			 var name = $('#info_name').val().trim();
			 if(name != "" && name != $("#info_name").attr("TextDefault")){
			 $.post(
					"/RegisterValidate_name",
					{name:name},
					function(message)
					{
						if(message.match("ok")){
							$("#validate_info_name").val("1");
							saveInfo();
						}else{
							message = decodeURI(message);
							errorBox.show(message);
							$("#validate_info_name").val("0");
							return false;
						}
					}
				);
			 }else {errorBox.show("请输入姓名");return false;}
		 }else{
			 saveInfo();
		 }
	});
});

function saveInfo(){
	var flag = "1";
	$(".info_validate").each(function(){
		if($(this).val().trim() == "0"){
			flag = "0";
			return false;
		}
	});
	var home,birthday,phone,qq,introduce;
	if($("#info_year").val().trim()!="" && $("#info_month").val().trim()!="" && $("#info_day").val().trim()!="") birthday=$("#info_year").val()+"-"+$("#info_month").val()+"-"+$("#info_day").val();
	if($("#inho_province").val().trim()!="" && $("#inho_city").val().trim()!="" && $("#inho_area").val().trim()!="") home = $("#inho_province").val()+"-"+$("#inho_city").val()+"-"+$("#inho_area").val();
	if($("#info_moretext").val()!="" && $("#info_moretext").val() != $("#info_moretext").attr("TextDefault")) introduce = $("#info_moretext").val();
	if(flag == "1")
	$.ajax({
		url:"/updateinfo",
		type:"post",
		data:{
			realname:$("#info_name").val(),province:$("#info_province").val(),city:$("#info_city").val(),school:$("#info_school").val(),
			x:$("#imgX").val(),y:$("#imgY").val(),h:$("#imgH").val(),w:$("#imgW").val(),p:$("#imgPath").val(),
			birthday:birthday,constellation:$("#info_constellation").val(),xx:$("#info_xx").val(),marry:$("#info_marry").val(),blood:$("#info_blood").val(),home:home,
			introduce:introduce,department:$("#info_department").val(),grade:$("#info_grade").val(),edu:$("#info_edu").val()
		},
		dataType:"text",
		beforeSend:function(){},
		complete:function(){},
		timeout:20000,
		success:function(message){
			if(message.match("ok")){successBox.show("保存成功")}
		}
	});
	else return false;

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

var InitOperator = {
	initBirthday:function(data){
		$("#"+data.month+",#"+data.year).change(function(){
			var dayNum,year = Number($("#"+data.year).val()),month = Number($("#"+data.month).val());
			if(year!= "" && month!=""){
				document.getElementById(data.day).length = 0;
				if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) dayNum=31;
				else if(month==4||month==6||month==9||month==11) dayNum = 30;
				else{
					if(year%400==0||(year%4==0 && year%100!=0)) dayNum=29;
					else dayNum=28;
				}
				for(i = 0 ; i < dayNum; i++)
				{
					document.getElementById(data.day).options[i] = new Option(i+1,i+1);
				}
			}
		});
		document.getElementById(data.year).length = 0;
		document.getElementById(data.year).options[0] = new Option("年","");
		for(i = 2012 ; i > 1970; i--)
		{
			document.getElementById(data.year).options[2012-i+1] = new Option(i,i);
		}
		document.getElementById(data.month).length = 0;
		document.getElementById(data.month).options[0] = new Option("月","");
		for(i = 1 ; i < 13; i++)
		{
			document.getElementById(data.month).options[i] = new Option(i,i);
		}
		document.getElementById(data.day).length = 0;
		document.getElementById(data.day).options[0] = new Option("日","");

		if($("#info_birthday").val() != "" && $("#info_birthday").val()!=null){
			var d = $("#info_birthday").val().split("-");
			var year = d[0],month=d[1];
			$("#"+data.year+" option[value='"+d[0]+"']").attr("selected", true);
			$("#"+data.month+" option[value='"+d[1]+"']").attr("selected", true);
			document.getElementById(data.day).length = 0;
			if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) dayNum=31;
			else if(month==4||month==6||month==9||month==11) dayNum = 30;
			else{
				if(year%400==0||(year%4==0 && year%100!=0)) dayNum=29;
				else dayNum=28;
			}
			for(i = 0 ; i < dayNum; i++)
			{
				document.getElementById(data.day).options[i] = new Option(i+1,i+1);
			}
			$("#"+data.day+" option[value='"+d[2]+"']").attr("selected", true);
		}
		
	},
	initConstellation:function(data){
		document.getElementById(data.constellation).length = 0;
		document.getElementById(data.constellation).options[0] = new Option("请选择","");
		for(i = 1 ; i <= 12; i++)
		{
			var c;
			switch(i){
				case 1:c="白羊座";break;case 2:c="金牛座";break;case 3:c="双子座";break;
				case 4:c="巨蟹座";break;case 5:c="狮子座";break;case 6:c="处女座";break;
				case 7:c="天秤座";break;case 8:c="天蝎座";break;case 9:c="射手座";break;
				case 10:c="摩羯座";break;case 11:c="水瓶座";break;case 12:c="双鱼座";break;
			}
			document.getElementById(data.constellation).options[i] = new Option(c,c);
		}
		$("#"+data.month+",#"+data.day).change(function(){
			var cn,month = Number($("#"+data.month).val()),day = Number($("#"+data.day).val());
			switch(month){
				case 1:{if(day>=20)cn="水瓶座";else cn="摩羯座";break;}
				case 2:{if(day<19)cn="水瓶座";else cn="双鱼座";break;}
				case 3:{if(day<21)cn="双鱼座";else cn="白羊座";break;}
				case 4:{if(day<20)cn="白羊座";else cn="金牛座";break;}
				case 5:{if(day<21)cn="金牛座";else cn="双子座";break;}
				case 6:{if(day<22)cn="双子座";else cn="巨蟹座";break;}
				case 7:{if(day<23)cn="巨蟹座";else cn="狮子座";break;}
				case 8:{if(day<23)cn="狮子座";else cn="处女座";break;}
				case 9:{if(day<24)cn="处女座";else cn="天秤座";break;}
				case 10:{if(day<24)cn="天秤座";else cn="天蝎座";break;}
				case 11:{if(day<22)cn="天蝎座";else cn="射手座";break;}
				case 12:{if(day<22)cn="射手座";else cn="摩羯座";break;}
			}
			$("#"+data.constellation+" option[value='"+cn+"']").attr("selected", true);
		});
		$("#"+data.constellation+" option[value='"+$("#info_constellation_input").val().trim()+"']").attr("selected", true);
	},
	initXx:function(data){
		$("#"+data.year).change(function(){
			var xx,year = Number($("#"+data.year).val());
			xx=((year-1960)%12+1);
			$("#"+data.xx+" option[value='"+xx+"']").attr("selected", true);
		});
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