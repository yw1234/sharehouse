package template;

import java.util.List;
import java.util.Map;

import services.common.CommonService;
import util.GetAC;
import util.StaticInfo;

public class NotesBlockTemplate {

	private static Integer idleSize = StaticInfo.idleSize;
	private static String URL = StaticInfo.URL;
	
	public String goodsNotesList(List noteslist,Object[]judge){
		if(noteslist==null)
			return "";
		Integer cnumber = (Integer)noteslist.size();
		StringBuffer msg = new StringBuffer();
		
		for(Integer i = 0 ; i < cnumber ; i++){
			Map map = (Map) noteslist.get(i);
			String goods = map.get("goodsTitle").toString().length()>13?map.get("goodsTitle").toString().substring(0,13)+"...":map.get("goodsTitle").toString().toString();
			String shareType =map.get("shareType").toString();
			boolean favor = (map.get("isFavored") == null&&!map.get("userid").toString().equals(judge[0].toString()));
			//模板
			msg.append("<div style='float:left;width:245px;height:364px;'>");
			msg.append("<div class = 'notes_block' id = 'goods_notes_"+map.get("goodsid").toString()+"' style = 'border-radius:5px;width:220px;height:312px;background: #c8c8c8; background: -webkit-gradient(linear, left top, left bottom, from(#e4e4e4), to(#a8a8a8));background: -moz-linear-gradient(top,  #e4e4e4,  #a8a8a8);filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr=\"#e4e4e4\", endColorstr=\"#a8a8a8\");-webkit-transition:border linear 0.2s,box-shadow linear 0.2s;-moz-transition: border linear 0.2s, box-shadow linear 0.2s;-o-transition: border linear 0.2s, box-shadow linear 0.2s;transition: border linear 0.2s, box-shadow linear 0.2s;position:relative;'>" );
			msg.append(	"<img style='position:absolute;z-index:10;left:5px;top:5px;width:30px;height:30px;' src = '"+URL+"/web/image/base/icon/"+shareType+"_icon.png'/>");
			if(shareType.equals("sell")&&map.get("price")!=null){
				msg.append(	"<div style='position:absolute;z-index:10;font-size:18px;right:5px;top:178px;padding-left:5px;padding-right:5px;height:25px;line-height:26px;border-radius:5px;background:url("+URL+"/web/image/base/orange.png) center center;color:white;font-style:italic;cursor:pointer;'>￥"+map.get("price").toString()+"</div>");
			}
			if((judge[0].toString().equals(map.get("userid").toString())&&(Boolean)judge[1]) || (Boolean)judge[2])
				msg.append(	"	<img class = 'notesdel-switch notesdel-img' src = '"+URL+"/web/image/base/notes_sel_btn.png' style = 'z-index:20;top:5px;right:5px;position:absolute;cursor:pointer;' onclick = 'show_notesDelete({loc:this,typeid:"+map.get("goodsid").toString()+",type:\"goods\",senderid:"+map.get("userid").toString()+",isShared:\""+map.get("isShared").toString()+"\"})'/>");
				msg.append("<div class='checkmore' style = 'float:left;width:210px;height:210px;margin-left:5px;margin-top:5px;cursor:pointer;line-height:210px;text-align:center;position:relative;background:url("+URL+"/web/image/base/pic_loading_default.gif) center center no-repeat;'>");
				msg.append("	<a href = "+URL+"/details/goods/"+map.get("goodsid").toString()+" target='_blank'>" );
			if(map.get("firstImg")==null||map.get("firstImg").toString().equals(""))
		    		msg.append(	"		<img src = '"+URL+"/web/image/home/notes_pic_bg.png' style = 'width:210px;height:210px;'/>");
		    	else msg.append("<img id= 'notes_f_img_"+map.get("goodsid").toString()+"' class = 'notes_f_img new_notes' src = "+map.get("firstImg").toString()+" style = 'table-layout:fixed;vertical-align:middle;max-width:210px;max-height:210px;min-width:120px;min-height:120px;' alt = '"+goods+"'/>");
		    		msg.append(	"</a></div>");
		    		msg.append("	<a href = "+URL+"/details/goods/"+map.get("goodsid").toString()+" target='_blank'>" );
		    			msg.append("	<div class='notes_object_title'>");
		    			msg.append(goods);
		    			msg.append("	</div>");
		    			msg.append("	</a>");
		    			msg.append("	<div id = 'notes_info_"+map.get("goodsid").toString()+"' style = 'float:left;width:210px;margin-top:5px;margin-left:5px;position:relative;'>");
		    			msg.append("		<div style = 'float:left;width:35px;height:35px;background:white;border-radius:3px;'><a href = '"+URL+"/user/"+map.get("userid").toString()+"' id = 'notes_sender_"+map.get("userid").toString()+"'><img uCard='"+map.get("userid").toString()+"' src = '"+map.get("userAvatar").toString()+"' style = 'float:left;width:33px;height:33px;margin-left:1px;margin-top:1px;'/></a></div>");
		    			msg.append("    <span style = 'float:left;font-size:14px;text-align:left;margin-top:8px;margin-left:5px;'><a href = '"+URL+"/user/"+map.get("userid").toString()+"' style='color:#4a4a4a'>"+map.get("userName").toString()+"</a></span>");
		    			msg.append("		<span style='float:right;margin-top:8px'>");
		    			msg.append("			<span style='float:left;font-size:13px;color:white'>"+map.get("sendTime").toString()+"</span>");
		    			msg.append("		</span>");
		    			msg.append("		<div style = 'float:left;width:100%;margin-top:5px;line-height:20px;'>" );
		    			msg.append("			<a href = "+URL+"/details/goods/"+map.get("goodsid").toString()+" target='_blank'><span id = 'requiredtimes_"+map.get("goodsid").toString()+"' style='float:right;color:white;font-size:12px;'><img src='"+URL+"/web/image/base/icon/favor_icon.png' style='float:left;width:15px;margin-top:2px;'/>想要(<span style='color:white;'>"+map.get("requiredTimes").toString()+"</span>)</span></a>" );
		    			msg.append("			<a href = "+URL+"/details/goods/"+map.get("goodsid").toString()+" target='_blank'><span id = 'commentsize_"+map.get("goodsid").toString()+"' style='float:right;margin-right:5px;color:white;font-size:12px;'><img src='"+URL+"/web/image/base/icon/commentsize_icon.png' style='float:left;width:15px;margin-top:2px;'/>评论(<span style='color:white;'>"+map.get("commentSize").toString()+"</span>)</span></a>" );
			    		msg.append("			<a href = "+URL+"/details/goods/"+map.get("goodsid").toString()+" target='_blank'><span id = 'lookedtimes_"+map.get("goodsid").toString()+"' style='float:right;margin-right:5px;color:white;font-size:12px;'><img src='"+URL+"/web/image/base/icon/lookedtimes_icon.png' style='float:left;width:15px;margin-top:2px;'/>看过(<span style='color:white;'>"+map.get("lookedTimes").toString()+"</span>)</span></a>");
					msg.append("		</div>");
					msg.append("	</div>");
		    			msg.append("</div>");
		    			msg.append("	<div style='float:left;width:100%;height:50px;'><div style = 'float:left;width:100%;margin-top:25px;height:2px;background:#d8d8d8;'></div></div>");
		    			msg.append("</div>");
			
		}
		if(noteslist.size() == idleSize){
			msg.append("<div class = 'moreloading'><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
		    msg.append("<div class = 'morenotes'><span>获取更多动态<img src = '"+URL+"/web/image/base/getmore.png' style = 'margin-left:10px;'/></span></div>");
		}
		return msg.toString();
	}


	public String commentList(List commlist,Map params){
		StringBuffer msg = new StringBuffer();
		if(commlist!=null&&commlist.size()>0)
		{
			String userid = params.get("userid").toString();
			String senderid = params.get("senderid").toString();
			String isAdmin = params.get("isAdmin").toString();
			Integer cnumber = (Integer)commlist.size();
			String realPath = StaticInfo.URL+"/";
			for(Integer i = 0 ; i < cnumber ; i++){
				Map map =  (Map) commlist.get(i);
				msg.append( "<div class = 'd_comm_block' id = 'd_comm_block_"+map.get("id").toString()+"'>");
				msg.append( 	"	<a href='"+realPath+"user/"+map.get("senderid").toString()+"'><img uCard='"+map.get("senderid").toString()+"' src = '"+map.get("senderAvatar").toString()+"' style = 'float:left;width:40px;height:40px;border-radius:2px;' id = 'd_comm_ico_"+map.get("id").toString()+"'/></a>");
				msg.append( 	"<div style = 'float:left;width:405px;margin-left:5px;'>");
				msg.append( 			"<div class = 'd_comm_text'>");
				msg.append( 	"<span class = 'd_comm_comment' id = 'd_comm_comment_"+map.get("id").toString()+"'><a href='"+realPath+"user/"+map.get("senderid").toString()+"' style='color:#e68303;'>"+map.get("senderName").toString()+"</a> : "+map.get("content").toString()+"</span>");
				msg.append( 	"</div>");
			if(userid.equals(senderid) || userid.equals(map.get("senderid")) || isAdmin.equals("1"))
				msg.append( "<div class='d_del_comm' id = 'd_del_comm_"+map.get("id").toString()+"' style='float:left;width:20px;height:20px;margin-left:5px;cursor:pointer;' title='删除该条评论'><img src='"+realPath+"web/image/base/del_button.png' style='float:left;display:none;width:16px;margin-top:5px;' onclick='confirmBox.show({func:function(){comment.del({id:"+map.get("id").toString()+",typeid:"+map.get("commShareId").toString()+",type:\""+map.get("commType").toString()+"\"});},msg:\"确认删除这条评论?\"});'/></div>");
				msg.append("<div style = 'width:380px;float:left;text-align:left;margin-top:3px;'>");
				msg.append( 			"<a href = 'javascript:void(0);'style = 'float:left;font-size:13px;color:#e68303;' class = 'd_reply_btn' id = 'd_reply_btn_"+map.get("id").toString()+"'>回复</a>");
				msg.append( 				"<a href = 'javascript:void(0);'style = 'float:left;margin-left:10px;font-size:13px;color:#e68303;' class = 'd_complaint_btn' id = 'd_complaint_btn_"+map.get("id").toString()+"' onclick = 'complaintBox.show({id:"+map.get("senderid").toString()+",name:\""+map.get("senderName").toString()+"\"})'>举报</a>");
				msg.append( 				"<span style = 'float:left;margin-left:30px;font-size:13px;color:#bdbdbd;'>"+map.get("sendTime")+"</span>" );
				msg.append( 				"<input type='hidden' class = 'reply_userid' value = '"+map.get("senderid").toString()+"'/>");
				msg.append( 				"<input type='hidden' class = 'reply_username' value = '"+map.get("senderName").toString()+"'/>");
				msg.append( 			"</div>");
				msg.append( "</div><div style = 'float:left;width:100%;height:10px;'></div>");
				msg.append( 	"</div>");
			
			}
			
		}else {
			msg.append("<div id = 'noComment' style = 'width:100%;float:left;margin-top:20px;'>");
			msg.append("		<span style = 'color:#e68303;'>暂时还没有人留言</span>");
			msg.append("</div>");
		}
		return msg.toString();
	}
	
	
	public String updateBox(Map map){
		StringBuffer sb = new StringBuffer();
		if(map!=null){
			String type = map.get("obj_type").toString();
			int picCount = Integer.parseInt(map.get("picCount").toString());
			String [] pList = map.get("pList").toString().split(",");
			String [] pId = map.get("pId").toString().split(",");
			sb.append("<div class = 'sendBox_main' id = 'sendShareBox'>");
			sb.append("	<div class = 'sendBox_nav' style = 'height:25px;'>");
			sb.append("		<span style = 'line-height:26px;'>物品名称:</span>");
			sb.append("		<input type = 'text' class = 'send_obj' id = 'goods' maxlength='30' value = '"+map.get("obj_object").toString()+"'/>");
			sb.append("		<span style='float:left;margin-left:5px;line-height:25px;color:#b1b1b1;font-size:15px;'></span>");
			sb.append("		<a href = 'javascript:void(0);' onclick = 'updateInfoBox.hide();'><img src = '"+URL+"/web/image/base/del_button_1.png' style = 'float:right;width:18px;margin-right:15px;margin-top:3px;' title='取消修改'/></a>");
			sb.append("	</div>");
			sb.append("	<div class = 'sendBox_nav'>");
			sb.append("		<span>实物图片修改</span>");
			sb.append("		<span style = 'float:left;color:#e68303;font-size:13px;line-height:22px;'>(可上传6张,最大为5M)</span><span class = 'cancel'>取消</span>");
			sb.append("		<div class = 'pic_block'>");
			sb.append("			<input type = 'hidden' id = 'pic_index' value = '"+picCount+"'/>");
			sb.append("			<input type = 'hidden' id = 'click_number'/>");
			sb.append("			<input type = 'hidden' id = 'pic_number' value = '"+picCount+"'/>");
			//图片
			for(int i = 0 ; i < picCount ; i++){
				sb.append("		<div class = 'pic' id = 'pic_"+i+"'>");
				sb.append("			<div class = 'upc' id = 'upc_"+i+"' style = 'display:block;background:transparent;'>");
				sb.append("				<img class = 'del' id = 'del_"+i+"' targetId = '"+pId[i]+"' src = '"+URL+"/web/image/base/del_button.png' style = 'position:absolute;top:-10px;right:-10px;cursor:pointer;opacity:0.7;filter:alpha(opacity=70);width:20px;' title = '删除图片'/>");
				sb.append(" 				<img class = 'show_img' id = 'show_img_"+i+"' src = '"+pList[i]+"'/>");
				sb.append("			</div>");
				sb.append("		</div>");
			}
			sb.append("		</div>");
			sb.append("	</div>");
			sb.append("<div id = 'sendBox_mainInfo' style = 'float:left;width:100%;'>");
			//卖
			if(type.equals("sell")){
				sb.append("<div id = 'mainInfo_sell' class = 'mainInfo' style = 'float:left;width:100%;'>");
				sb.append("		<div class = 'info_text' style = 'margin-top:10px;'><div class = 'info_img'><img src = '"+URL+"/web/image/home/price.png'/></div><div class = 'tab'>价格:</div><input type = 'text' value = '"+map.get("obj_price").toString()+"' maxlength='10' class = 'mi_text price' id= 'price' onpaste = 'return false;' onkeydown = 'return numbervalidate();'/><span style='float:left;margin-left:10px;line-height:25px;color:#b1b1b1;font-size:14px;'>(只可输入数字)</span></div>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/olddegree.png'/></div><div class = 'tab'>新旧程度:</div>"+oldDegreeSelector(map.get("obj_oldDegree").toString())+"</div>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/bargain.png'/></div><div class = 'tab'>是否可刀:</div>"+bargainSelector(map.get("obj_bargain").toString())+"</div>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/goodslink.png'/></div><div class = 'tab'>报价链接:</div><input type = 'text' value='"+map.get("obj_goodsLink").toString()+"' maxlength='500' class = 'long_text' id= 'sell_link' style='width:150px;'/><span style='float:left;margin-left:10px;line-height:25px;color:#b1b1b1;'>(</span><a href='http://www.360buy.com' target='_blank'><span class='sell_web_link'>京东</span></a><a href='http://www.taobao.com' target='_blank'><span class='sell_web_link'>淘宝</span></a><a href='http://www.zol.com' target='_blank'><span class='sell_web_link'>中关村</span></a><span style='float:left;margin-left:3px;line-height:25px;color:#b1b1b1;'>)</span></div>");
				sb.append("	</div>");
			}
			//借
			else if(type.equals("lend")){
				sb.append("	<div id = 'mainInfo_lend' class = 'mainInfo' style = 'float:left;width:100%;'>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/olddegree.png'/></div><div class = 'tab'>新旧程度:</div>"+oldDegreeSelector(map.get("obj_oldDegree").toString())+"</div>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/borrow_time.png'/></div><div class = 'tab'>借出时长:</div>"+lendTimeSelector(map.get("obj_totalTime").toString())+"</div>");
				sb.append("	</div>");
			}
			//送
			else if(type.equals("gift")){
				sb.append("	<div id = 'mainInfo_gift' class = 'mainInfo' style = 'float:left;width:100%;'>");
				sb.append("		<div class = 'info_text' style = 'margin-top:10px;'><div class = 'info_img'><img src = '"+URL+"/web/image/home/price.png'/></div><div class = 'tab'>估价:</div><input type = 'text' value = '"+map.get("obj_price").toString()+"' maxlength='10' class = 'mi_text price' id= 'price' onpaste = return false;' onkeydown = 'return numbervalidate();''/><span style='float:left;margin-left:10px;line-height:25px;color:#b1b1b1;font-size:14px;'>(只可输入数字)</span></div>");
				sb.append("		<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/olddegree.png'/></div><div class = 'tab'>新旧程度:</div>"+oldDegreeSelector(map.get("obj_oldDegree").toString())+"</div>");
				sb.append("	</div>");
			}
			
			sb.append("	<div id = 'mainInfo_common' style = 'float:left;width:100%;'>");
			sb.append("	<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/boughtprice.png'/></div><div class = 'tab'>物品类型:</div><div style = 'float:left;height:25px;font-weight:bold;font-size:14px;line-height:23px;'>"+typeGeneSelector(map.get("obj_goodsType1").toString())+typeDetailSelector(map.get("obj_goodsType1").toString(),map.get("obj_goodsType2").toString())+"</div></div>");
			sb.append("	<div class = 'info_text'><div class = 'info_img'><img src = '"+URL+"/web/image/home/contactperson.png'/></div><div class = 'tab'>联系方式:</div><div style = 'float:left;height:25px;line-height:23px;'><input type = 'radio' style = 'float:left;line-height:25px;' name='goods_show_pri' id = 'goods_show_pri_notshow' "+(map.get("obj_showPrivacy").equals("0")?"checked":"")+"/><span style='float:left;font-size:14px;font-family:'黑体',Arial;'>不公开</span><input type = 'radio' style = float:left;line-height:25px;margin-left:10px;' name='goods_show_pri' id = 'goods_show_pri_show' "+(map.get("obj_showPrivacy").equals("1")?"checked":"")+"/><span style='float:left;font-size:14px;'>公开</span><span style = 'margin-left:10px;font-weight:400;font-size:13px;'>(可到<a href = '"+URL+"/user?page=privacy&nav=common&id=${user.id}' target='_blank' style = 'color:#e68303;'>隐私设置</a>页面设置可见人)</span></div></div>");
			sb.append("		<div class = 'info_text'><div class = 'tab' style = 'float:left;'><span style = 'float:left;font-size:16px;'>补充信息:</span></div>");
			sb.append("		<div class = 'at-icon' target = 'goods_more_info_text'>@</div>");
			sb.append("			<div class = 'facelist face-switch' id = 'goods_facelist' onclick = 'getFaceList(\"goods_facelist\",$(\"#goods_more_info_text\"),\"goods_textlimit\");'><img src = '"+URL+"/web/image/base/face.png' style = 'float:left;'/></div>");
			sb.append("			<div id = 'goods_textlimit' style = 'float:left;margin-left:30px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:2px;'>0/140</div>");
			sb.append("		</div>");
			sb.append("		<div style = 'float:left;width:100%'>");
			sb.append("			<textarea at-field class = 'more_info_text' id = 'goods_more_info_text' onkeyup='textLimit.getLength({id:\"goods_textlimit\",target_text:$(this)})'>"+map.get("obj_content").toString()+"</textarea>");
			sb.append("		<div class = 'button' id = 'send_goods_btn' style = 'float:left;width:85px;height:65px;margin-left:10px;margin-top:5px;line-height:65px;border-radius:5px;' onclick = 'updateInfoBox.save();'><span style = 'font-size:19px;color:white;'>保存修改</span></div>");
			sb.append("		</div>");
			sb.append("	</div>");
			sb.append("</div>");
			sb.append("<input type = 'hidden' id = 'send_goodsid' value = '"+map.get("obj_id").toString()+"'/>");
			sb.append("<div style = 'float:left;width:100%;height:10px;'></div>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	private String oldDegreeSelector(String selected){
		StringBuffer sb = new StringBuffer();
		sb.append("<select class = 'old_degree' id = 'old_degree'>");
		sb.append("		<option value = '' "+(selected.equals("")?"selected":"")+">请选择</option>");
		sb.append("		<option value = '全新' "+(selected.equals("全新")?"selected":"")+">全新</option>");
		sb.append("		<option value = '九九成' "+(selected.equals("九九成")?"selected":"")+">九九成</option>");
		sb.append("		<option value = '九五成' "+(selected.equals("九五成")?"selected":"")+">九五成</option>");
		sb.append("		<option value = '九成' "+(selected.equals("九成")?"selected":"")+">九成</option>");
		sb.append("		<option value = '八成' "+(selected.equals("八成")?"selected":"")+">八成</option>");
		sb.append("		<option value = '七成' "+(selected.equals("七成")?"selected":"")+">七成</option>");
		sb.append("		<option value = '较旧' "+(selected.equals("较旧")?"selected":"")+">较旧</option>");
		sb.append("</select>");
		return sb.toString();
	}
	
	private String bargainSelector(String selected){
		StringBuffer sb = new StringBuffer();
		sb.append("<select class = 'bargain' id = 'bargain'>");
		sb.append("		<option value = '' "+(selected.equals("")?"selected":"")+">请选择</option>");
		sb.append("		<option value = '可刀' "+(selected.equals("可刀")?"selected":"")+">可刀</option>");
		sb.append("		<option value = '小刀' "+(selected.equals("小刀")?"selected":"")+">小刀</option>");
		sb.append("		<option value = '大刀' "+(selected.equals("大刀")?"selected":"")+">大刀</option>");
		sb.append("		<option value = '不刀' "+(selected.equals("不刀")?"selected":"")+">不刀</option>");
		sb.append("</select>");
		return sb.toString();
	}
	
	private String lendTimeSelector(String selected){
		StringBuffer sb = new StringBuffer();
		sb.append("<select class = 'lend_time' id = 'lend_time'>");
		sb.append("		<option value = '面议' "+(selected.equals("面议")?"selected":"")+">面议</option>");
		sb.append("		<option value = '一天' "+(selected.equals("一天")?"selected":"")+">一天</option>");
		sb.append("		<option value = '一周' "+(selected.equals("一周")?"selected":"")+">一周</option>");
		sb.append("		<option value = '一个月' "+(selected.equals("一个月")?"selected":"")+">一个月</option>");
		sb.append("		<option value = '半年' "+(selected.equals("半年")?"selected":"")+">半年</option>");
		sb.append("		<option value = '一年及以上' "+(selected.equals("一年及以上")?"selected":"")+">一年及以上</option>");
		sb.append("</select>");
		return sb.toString();
	}
	
	private String typeGeneSelector(String selectedObj1){
		StringBuffer sb = new StringBuffer();
		CommonService xs = (CommonService) GetAC.getAppContext().getBean("XMLService");
		String []t1 = xs.getObjType1();
		sb.append("<select id = 'goodstype_1'>");
		sb.append("		<option value = '' "+(selectedObj1.equals("")?"selected":"")+">请选择</option>");
		for(int i = 0 ; i < t1.length;i++){
			sb.append("		<option value = '"+t1[i]+"' "+(selectedObj1.equals(t1[i])?"selected":"")+">"+t1[i]+"</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	private String typeDetailSelector(String selectedObj1,String selectedObj2){
		StringBuffer sb = new StringBuffer();
		CommonService xs = (CommonService) GetAC.getAppContext().getBean("XMLService");
		String []t2 = xs.getObjType2(selectedObj1);
		sb.append("<select id = 'goodstype_2'>");
		sb.append("		<option value = '' "+(selectedObj1.equals("")?"selected":"")+">请选择</option>");
		if(t2!=null){
			for(int i = 0 ; i < t2.length;i++){
				sb.append("		<option value = '"+t2[i]+"' "+(selectedObj2.equals(t2[i])?"selected":"")+">"+t2[i]+"</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	
}
