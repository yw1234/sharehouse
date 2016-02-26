package template;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import util.StaticInfo;

public class UserBlockTemplate {
		
	/**
	 * 可能认识的人
	 * @param mkulist
	 * @return
	 */
	public String myKnowUserList(List mkulist){
		StringBuffer sb = new StringBuffer();
		if(mkulist==null||mkulist.size()<=0)
			return "";
		Integer mkunumber = (Integer)mkulist.size();
		for(Integer i = 0 ; i < mkunumber ; i++){
			Object []obj = (Object[]) mkulist.get(i);
			sb.append("<div class = 'show_user_notes'>");
			sb.append("<div style = 'float:left;width:70px;'>");
			sb.append(	"<a href = '"+StaticInfo.URL+"/user/"+obj[0].toString()+"'>");
			sb.append(	"	<img src = "+obj[3].toString()+" style = 'width:70px;height:70px;border-radius:3px;float:left;'/>");
			sb.append(	"</a>");
			sb.append(		"<span style = 'color:#e68303;font-size:13px;cursor:pointer;float:left;' id = 'add_friends_btn' onclick = 'addFriends.init({btn:$(this),userid:"+obj[0].toString()+",username:\""+obj[2].toString()+"\"});'>加好友</span>");
			sb.append(		"<span style = 'float:left;width:5px;color:#c1c1c1;text-align:left;line-height:15px;'>|</span>");
			sb.append(		"<span style = 'color:#e68303;font-size:13px;cursor:pointer;float:left;' id = 'leave_msg_btn' onclick = 'replyBox.show({userid:"+obj[0].toString()+",userName:\""+obj[2].toString()+"\"});'>留言</span>");
			sb.append(	"</div>");
			sb.append(	"<div style = 'float:left;margin-left:10px;text-align:left;width:125px;'>");
			sb.append("<span class = 'show_user_span'>");
		if(obj[4].toString().equals("男"))
			sb.append("<img src = '"+StaticInfo.URL+"/web/image/base/icon/icon_male.png' style = 'float:left;height:14px;'/>");
		else sb.append("<img src = '"+StaticInfo.URL+"/web/image/base/icon/icon_female.png' style = 'float:left;height:14px;'/>");
			sb.append("<a href = '"+StaticInfo.URL+"/user/"+obj[0].toString()+"'><span style = 'float:left;margin-left:3px;color:#e68303;line-height:17px;'>"+obj[2].toString()+"</span></a></span>");
			sb.append(	"<span class = 'show_user_span' style = 'font-size:13px;'>"+obj[5].toString()+"</span>");
			sb.append(	"<span class = 'show_user_span' style = 'font-size:13px;'>"+obj[6].toString()+obj[7].toString()+"</span>");
	    	if(obj[8]!=null&&!obj[8].toString().trim().equals("0"))
	    		sb.append("<span class = 'show_user_span' style = 'font-size:13px;'>有<span style = 'color:#e68303;'>"+obj[8].toString()+"</span>个共同好友</span>");
	    		sb.append("</div>");
	    		sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 好友列表(我的好友页面)
	 * @param mkulist
	 * @return
	 */
	public String friendsList(List list,Map params){
		StringBuffer sb = new StringBuffer();
		if(list==null||list.size()<=0)
			return "";
		Integer mkunumber = (Integer)list.size();
		Integer pn = Integer.parseInt(params.get("pageno").toString());		
		for(Integer i = 0 ; i < mkunumber ; i++){
			Map map =  (Map) list.get(i);
			Object uid = map.get("id"),
					name = map.get("name"),
					sex = (map.get("sex").toString().equals("男"))?"male":"female",
					renren_binding = (map.get("renren_binding") != null && map.get("renren_binding").toString().equals("1"))?true:false;
			sb.append("<div id = 'friends_notes_"+uid+"' class = 'show_friends_notes'>");
			sb.append("	<div style = 'float:left;width:70px;height:90px;'>");
			sb.append("		<a href = '"+StaticInfo.URL+"/user/"+uid+"'><img src = '"+map.get("avatar")+"' style = 'float:left;width:60px;height:60px;margin:5px;margin-bottom:2px;'/></a>");
			sb.append("		<span style = 'float:left;margin-left:2px;width:65px;font-size:13px;'><a href = 'javascript:void(0);' style='color:#e68303;' onclick = 'replyBox.show({userid:"+uid+",userName:\""+name+"\"})'>私信</a> | <a href = 'javascript:void(0);' style='color:#e68303;' onclick = 'confirmBox.show({func:function(){deleteFriend({id:"+uid+",target:$(\"#friends_notes_"+uid+"\")});},msg:\"确认和"+name+"解除好友关系?\"});'>删除</a></span>");
			sb.append("	</div>");
			sb.append("	<div class = 'show_friends_info'>");
			sb.append("		<span class = 'show_friends_row'>");
			sb.append(" 			<img src = '"+StaticInfo.URL+"/web/image/base/icon/icon_"+sex+".png' style = 'float:left;width:14px;margin-top:3px;'>");
			sb.append(" 			<a href = '"+StaticInfo.URL+"/user/"+uid+"' style = 'float:left;color:#e68303;margin-left:5px;font-size:14px;'>"+name+"</a>");
			if(map.get("is_pass").toString().equals("1"))
				sb.append(" 			<img src = '"+StaticInfo.URL+"/web/image/base/icon/email_icon_activate.png' style = 'float:left;width:20px;margin-left:5px;margin-top:4px;cursor:pointer;' title = '账号已验证'/>");
			if((Boolean)renren_binding)
				sb.append(" 			<img src = '"+StaticInfo.URL+"/web/image/base/icon/renren_icon.png' style = 'float:left;width:25px;margin-left:5px;margin-top:4px;cursor:pointer;' title = '已绑定人人'/>");
			sb.append(" 		</span>");
			sb.append(" 		<span class = 'show_friends_row'>");
			sb.append(			map.get("school"));
			sb.append(" 		</span>");
			sb.append(" 		<span class = 'show_friends_row'>");
			sb.append(			map.get("department").toString()+map.get("grade").toString());
			sb.append(" 		</span>");
			sb.append(" 	</div>");
			sb.append("</div>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 注册时展示用户
	 * @param userlist
	 * @return
	 */
	public String registerUserList(List userlist){
		StringBuffer sb = new StringBuffer();
		if(userlist == null || userlist.size()<=0){
			sb.append("<div style = 'float:left;width:100%;margin-top:50px;'>");
			sb.append("<span>你是第一个加入圈易物的哦^_^ 点击<label style='color:#e68303;'>完成</label>进入主页吧~</span>");
			sb.append("</div>");
		}
		Integer cnumber = (Integer)userlist.size();
		for(Integer i = 0 ; i < cnumber ; i++){
			Object []obj = (Object[]) userlist.get(i);
			sb.append("<div id = 'ub_"+i+"' onclick = 'checkuser("+obj[0]+");' style = 'width:75px;height:70px;float:left;margin-top:10px;cursor:pointer;'>");
			sb.append("	<div style = 'float:left;margin-left:11px;width:50px;height:50px;position:relative;'>");
			sb.append("		<img src = '"+obj[3]+"' style = 'float:left;width:50px;height:50px;border-radius:3px;'/>");
			sb.append("		<img src = '"+StaticInfo.URL+"/web/image/base/checked.png' style = 'position:absolute;left:0px;top:0px;cursor:pointer;width:50px;height:50px;display:none;' class = 'check_user_ok' id = 'ok_u_"+obj[0]+"'/>");
			sb.append("	</div>");
			sb.append("	<span style = 'float:left;width:72px;font-size:12px;'>"+obj[1].toString()+"</span>");
			sb.append("	<input class = 'user_checked' type = 'hidden' value = '0' id = 'u_"+obj[0]+"'/>");
			sb.append(	"</div>\n");
		}
		return sb.toString();
	}
	
	public String userBoxList(List userlist,Integer pn){
		String msg = "";
		if(userlist == null || userlist.size()<=0){
			msg = "<div class = 'cf_friends' id = 'cf_friends'>" +
				  "		<span style='line-height:323px;font-size:16px;font-family:\"黑体\",Arial;'>暂无可选择的好友</span>" +
				  "</div>";
		}else{
			Integer cnumber = (Integer)userlist.size();
			msg += "<div class = 'cf_friends' id = 'cf_friends_"+pn+"'>";
			for(Integer i = 0 ; i < cnumber ; i++){
				Object []obj = (Object[]) userlist.get(i);
				msg	+=	"<div class = 'user_block' id = 'ub_"+i+"' onclick = 'userBox.check({id:"+obj[0]+",pn:\""+pn+"\"});' style = 'width:90px;height:80px;float:left;margin-left:8px;margin-top:20px;cursor:pointer;'>"+
						"	<div style='float:left;width:60px;height:60px;margin-left:15px;position:relative;'>"+
						"		<img src = '"+obj[3].toString()+"' style = 'width:60px;height:60px;border-radius:3px;'/>"+
						"		<img src = '"+StaticInfo.URL+"/web/image/base/checked.png' style = 'cursor:pointer;position:absolute;width:60px;height:60px;top:0px;left:0px;display:none;' class = 'check_user_ok check_user_ok_"+pn+"' id = 'ok_u_"+obj[0]+"' data-role='{\"name\":\""+obj[1].toString()+"\"}'/>"+
						"	</div>"+
						"	<span style = 'float:left;width:90px;margin-top:3px;font-size:13px;color:#e68303;'>"+obj[1]+"</span>"+
						"</div>";
			}
			msg += "</div>";
		}
		
		return msg;
	}
}
