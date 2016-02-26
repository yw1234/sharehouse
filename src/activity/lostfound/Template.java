package activity.lostfound;

import java.util.List;
import java.util.Map;

import util.StaticInfo;

public class Template {
	private static String URL = StaticInfo.URL;
	
	public static String sellBooksNotes(List list,Map params){
		if(list==null)
			return "";
		Integer cnumber = (Integer)list.size();
		StringBuffer msg = new StringBuffer();
		
		for(Integer i = 0 ; i < cnumber ; i++){
			Map map = (Map) list.get(i);
			msg.append("<div style='float:left;width:325px;height:384px;' id = 'lost_note_"+map.get("id")+"'>");
			msg.append("		<div class = 'lf_main'>");
			if(params.get("isAdmin")!=null && params.get("isAdmin").toString().equals("1"))
				msg.append("<a class = 'lost_delete' onclick = 'confirmBox.show({func:function(){lost.deletePub({id:"+map.get("id")+",target:$(\"#lost_note_"+map.get("id")+"\")});},msg:\"确认删除这条发布?\"});'><img src= '/web/image/base/del_button.png'/></a>");
			msg.append("    <a href = 'javascript:void(0);'>");
			msg.append("    		<div class='lf_picblock'>");
			msg.append("         	<img id = 'lost_img_"+map.get("id")+"' class='lost_img' src='"+(map.get("firstImg")!=null?map.get("firstImg"):URL+"/web/image/home/notes_pic_bg.png")+"' style='table-layout: fixed; vertical-align: middle; max-width: 244px; max-height: 244px; min-width: 140px; min-height: 140px; visibility: visible; width: auto; height: auto; display: inline; '>");	
			msg.append(" 		</div>");
			msg.append("		</a>");
			msg.append("		<div class = 'lf_lostname'>"+map.get("name")+"</div>");
			msg.append(	    "<div style = 'float:left;width:100%;'>");
			msg.append(    		"<ul class = 'lf_lostinfo'>");
		    	msg.append(    		"<li><span class = 'lf_sel'>拾获地点</span><span>:</span><label class = 'lf_place'>"+map.get("place")+"</label></li>");
		    	msg.append(    		"</ul>");
		    	msg.append(    		"</div>");
	    		msg.append("		</div>");
	    		msg.append("	</div>");
		}
		if(list.size()==StaticInfo.feedsNumber){
			msg.append("<div class = 'moreloading'><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
		    msg.append("<div class = 'morenotes'><span>获取更多失物信息<img src = '"+URL+"/web/image/base/getmore.png' style = 'margin-left:10px;'/></span></div>");
		}
		return msg.toString();
	}
}
