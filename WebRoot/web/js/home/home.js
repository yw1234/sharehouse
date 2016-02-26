var welcome = {
	init:function(){},
	step1:function(){},
	step2:function(){},
	step3:function(){},
	hide:function(){}
}

$(document).ready(function(){
	$(".tl_btn").hover(
		function(){$("#t_table").slideDown(300);},
		function(){$("#t_table").hide();}
	);
	$(".tlg").hover(
		function(){
			var $tl = $(this),$tld = $(this).children(".tl_details"),top = 0;
			$tld.show();
			if($tl.children(".tl_index").val()<3)
				top = -(Number($tl.children(".tl_index").val())*40)+"px";
			else top = -78+"px"
			$tl.css({"z-index":"102"});
			if($tld.children(".tld_flag").html().trim()==""){
				var value = "";
				var gType = $tl.children(".tl_type").html();
				$.post(
					"/common/getsendtype",
					{type:"detail",gType:gType},
					function(data){
						if(data.flag.match("ok")){
							typeArr = decodeURIComponent(data.message.replace(/\+/g,"%20")).split(",");
							for(i = 1 ; i < typeArr.length; i++)
							{
								value+="<div class = 'tl_li tld' parType = '"+gType+"'><label class = 'tld_type' style = 'cursor:pointer;'>"+typeArr[i-1]+"</label></div>";
							}
							setTimeout(function(){
								$tld.children(".tl_loading").hide();
								$tld.html(value).css({"top":top});
								//绑定
								$(".tld").unbind("click").click(function(){
									$("#selectedObjType1").val($(this).attr("parType"));
									$("#selectedObjType2").val($(this).children(".tld_type").html());
									selectedSubmit();
									$("#t_table").hide();
									return false;
								});
							},200);
							
						}
					},"json"
				);
				
			}
		},function(){
			var $tl = $(this);
			Number
			$tl.css({"z-index":"101"});
			$tl.children(".tl_details").hide();
		}
	).unbind("click").click(function(){
		$("#selectedObjType1").val($(this).children(".tl_type").html());
		$("#selectedObjType2").val("");
		selectedSubmit();
		$("#t_table").hide();
		return false;
	});
});