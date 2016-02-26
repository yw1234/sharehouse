package activity.sellbook;

import java.util.List;
import java.util.Map;

import util.StaticInfo;

public class Template {
	private static String URL = StaticInfo.URL;
	
	public static String sellBooksNotes(List list){
		if(list==null)
			return "";
		Integer cnumber = (Integer)list.size();
		StringBuffer msg = new StringBuffer();
		
		for(Integer i = 0 ; i < cnumber ; i++){
			Map map = (Map) list.get(i);
			String book = (map.get("name").toString().length() > 12)?map.get("name").toString().substring(0,12)+"...":map.get("name").toString();
			msg.append("<div style='float:left;width:235px;height:360px;'>");
			msg.append("		<div class = 'sbn_main'>");
			msg.append("    <div style='position:absolute;z-index:10;font-size:18px;right:8px;top:176px;padding-left:5px;padding-right:5px;height:25px;line-height:26px;border-radius:5px;background:url("+URL+"/activity/sellbook/image/green.png) center center;color:white;font-style:italic;cursor:pointer;'>￥"+map.get("price")+"</div>");
			msg.append("    <a href = 'javascript:void(0);' onclick='bookDetails.init({id:"+map.get("id")+"});'>");
			msg.append("    		<div class='sbn_picblock'>");
			msg.append("         	<img id = 'books_img_"+map.get("id")+"' class='books_img' src='"+(map.get("firstImg")!=null?map.get("firstImg"):URL+"/web/image/home/notes_pic_bg.png")+"' style='table-layout: fixed; vertical-align: middle; max-width: 200px; max-height: 200px; min-width: 120px; min-height: 120px; visibility: visible; width: auto; height: auto; display: inline; '>");	
			msg.append(" 		</div>");
			msg.append("			<div class = 'sbn_bookname'>"+book+"</div>");
			msg.append("		</a>");
			msg.append(	    "<div style = 'float:left;width:100%;'>");
			msg.append(    		"<ul class = 'sbn_bookinfo'>");
		    	msg.append(    		"<li><span class = 'sbn_sel'>类型</span><span>:</span><label class = 'sbn_type'>"+map.get("bookType")+"</label></li>");
		    	msg.append(    		"<li><span class = 'sbn_sel'>库存</span><span>:</span><label class = 'sbn_stock'>"+map.get("stock")+"</label></li>");
		    	msg.append(    		"</ul>");
		    	msg.append(    		"<a class = 'sb_button' style = 'float:left;margin-top:10px;width:50px;height:43px;line-height:44px;border-radius:4px;border:0px;' onclick = 'bookingBox.show({bookid:"+map.get("id")+",stock:"+map.get("stock")+",bookName:\""+map.get("name")+"\",price:"+map.get("price")+"})'>预定</a>");
		    	msg.append(    	"</div>");
	    		msg.append("		</div>");
	    		msg.append("	<div style='float:left;width:100%;height:50px;'><div style = 'float:left;width:100%;margin-top:25px;height:2px;background:#d8d8d8;'></div></div>");
	    		msg.append("	</div>");
		}
		if(list.size()==StaticInfo.idleSize){
			msg.append("<div class = 'moreloading'><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
		    msg.append("<div class = 'morenotes'><span>获取更多书籍信息<img src = '"+URL+"/web/image/base/getmore.png' style = 'margin-left:10px;'/></span></div>");
		}
		return msg.toString();
	}
}
