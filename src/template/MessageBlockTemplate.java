package template;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import util.GetTime;
import util.StaticInfo;

public class MessageBlockTemplate {
	
	private static Integer msgSize = StaticInfo.msgNumber;;
	private static String URL = ServletActionContext.getRequest().getContextPath();

	/**
	 * 请求模板
	 * @param list
	 * @return
	 */
	public String requestMessageList(List list){
		if(list == null || list.size()<=0) return "<div style = 'float:left;width:100%;margin-top:40px;'>暂无未读请求</div>";
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			sb.append("<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = 'req_"+map.get("id")+"'>");
			sb.append("		<div style = 'position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url("+URL+"/web/image/base/white_placeholder.png);' id = 'req_del_loading_"+map.get("id")+"'><span>删除ing</span><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
			sb.append("		<div style = 'float:left;margin-left:20px;width:50px;'>");
			sb.append("         <a href = '"+URL+"/user/"+map.get("senderid")+"'><img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:50px;height:50px;'/></a>");
			sb.append(" 	</div>");
			sb.append("     <div style = 'float:left;margin-left:20px;width:520px;'>");
			sb.append("         <span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '"+URL+"/user/"+map.get("senderid")+"' target = '_blank' style='color:#e68303;'>"+map.get("senderName")+"</a>"+map.get("content")+"</span>");
			if(!map.get("reqType").toString().equals("addFriends")){
				sb.append("<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>物品详情 : <a href ='"+URL+"/details/"+map.get("reqType")+"/"+map.get("reqShareId")+"' target = '_blank' style = 'color:#e68303;'>"+map.get("reqShare")+"</a></span>");
				sb.append("<span style = 'float:left;width:100%;margin-top:5px;text-align:left;font-size:14px;'>");
				if(!map.get("reqShareImg1").toString().equals(""))
					sb.append("<a href ='"+URL+"/details/"+map.get("reqType")+"/"+map.get("reqShareId")+"' target = '_blank' ><img src = "+map.get("reqShareImg1")+" style = 'float:left;margin-left:20px;max-width:120px;max-height:120px;'/></a>");
				if(!map.get("reqShareImg2").toString().equals(""))
					sb.append("<a href ='"+URL+"/details/"+map.get("reqType")+"/"+map.get("reqShareId")+"' target = '_blank' ><img src = "+map.get("reqShareImg2")+" style = 'float:left;margin-left:20px;max-width:120px;max-height:120px;'/></a>");
				if(!map.get("reqShareImg3").toString().equals(""))
					sb.append("<a href ='"+URL+"/details/"+map.get("reqType")+"/"+map.get("reqShareId")+"' target = '_blank' ><img src = "+map.get("reqShareImg3")+" style = 'float:left;margin-left:20px;max-width:120px;max-height:120px;'/></a>");
				sb.append("</span>");
			}
			if(!map.get("reqMessage").toString().equals(""))
				sb.append("<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>请求信息 : "+map.get("reqMessage")+"</span>");
			sb.append("     <span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
			sb.append("		<div id = 'req_opt' style = 'float:left;'>");
			sb.append(" 			<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'request.accept({reqid:"+map.get("id")+",userid:"+map.get("senderid")+",reqType:\""+map.get("reqType")+"\"});'>同意</a>");
			sb.append("		</div>");
			sb.append(" 		<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:50px;' onclick = 'replyBox.show({userid:"+map.get("senderid")+",userName:\""+map.get("senderName")+"\"});'>私信</a><span style = 'float:left;color:#c1c1c1;'>"+map.get("sendTime")+"</span></span>");
			sb.append("		</div>");
			sb.append("		<div style = 'float:left;width:60px;'>");
			sb.append("			<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){request.ignore({reqid:"+map.get("id")+"});},msg:\"确认删除此条请求?\"});'><img src = '"+URL+"/web/image/base/del_button.png' style = 'float:left;width:18px;margin-left:15px;margin-top:3px;' title='删除请求'/></a>");
			sb.append("		</div>");
			sb.append("</div>");
		}
		//getMore
		if(list.size()==msgSize){
			sb.append("<div style = 'float:left;width:100%;font-size:15px;margin-top:10px;border-radius:0px;' id = 'req_getMore'>");
			sb.append("		<a href='javascript:void(0);' style='color:#e68303;' onclick = 'getMoreUnReadMessage.request();'>获取更多未读请求</a>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 私信模板
	 * @param list
	 * @return
	 */
	public String privateMessageList(List list){
		if(list == null || list.size()<=0) return "<div style = 'float:left;width:100%;margin-top:40px;'>暂无未读私信</div>";
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			sb.append("<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = 'pri_"+map.get("id")+"'>");
			sb.append("		<div style = 'position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url("+URL+"/web/image/base/white_placeholder.png);' id = 'pri_del_loading_"+map.get("id")+"'><span>删除ing</span><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
			sb.append("		<a href = '"+URL+"/user/"+map.get("senderid")+"'><img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>");
			sb.append("		<div style = 'float:left;margin-left:20px;width:520px;'>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '"+URL+"/user/"+map.get("senderid")+"' style='color:#e68303;'>"+map.get("senderName")+"</a> : "+map.get("content")+"</span>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
			sb.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:"+map.get("senderid")+",userName:\""+map.get("senderName")+"\"});'>私信</a>");
			sb.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'confirmBox.show({func:function(){shield.user({userid:"+map.get("senderid")+"});},msg:\"确认屏蔽"+map.get("senderName")+"所发信息?\"});'>屏蔽此人信息</a>");
			sb.append("         	<span style = 'float:left;color:#c1c1c1;'>"+map.get("sendTime")+"</span>");
			sb.append("         </span>");
			sb.append("     </div>");
			sb.append("		<div style = 'float:right;width:60px;'>");
			sb.append("			<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){privateMsg.ignore({priid:"+map.get("id")+"});},msg:\"确认忽略此条私信?\"});'><img src = '"+URL+"/web/image/base/del_button.png' style = 'float:left;width:18px;margin-left:15px;margin-top:3px;' title='忽略私信'/></a>");
			sb.append("		</div>");
			sb.append("</div>");
		}
		if(list.size()==msgSize){
			sb.append("<div style = 'float:left;width:100%;font-size:15px;margin-top:10px;border-radius:0px;' id = 'pri_getMore'>");
			sb.append("		<a href='javascript:void(0);' style='color:#e68303;' onclick = 'getMoreUnReadMessage.privateMsg();'>获取更多未读私信</a>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 通知模板
	 * @param list
	 * @return
	 */
	public String noticeMessageList(List list){
		if(list == null || list.size()<=0) return "<div style = 'float:left;width:100%;margin-top:40px;'>暂无未读通知</div>";
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			sb.append("<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = 'notice_"+map.get("id")+"'>");
			sb.append("		<div style = 'position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url("+URL+"/web/image/base/white_placeholder.png);' id = 'notice_del_loading_"+map.get("id")+"'><span>删除ing</span><img src = '"+URL+"/web/image/register/loading.gif'/></div>");
			sb.append("		<a href = '"+URL+"/user/"+map.get("senderid")+"'><img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>");
			sb.append("		<div style = 'float:left;margin-left:20px;width:520px;'>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '"+URL+"/user/"+map.get("senderid")+"' style='color:#e68303;'>"+map.get("senderName")+"</a>"+map.get("content")+"</span>");
			if(map.get("isShareType").toString().equals("1"))
				sb.append("<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>物品详情 : <a href ='"+URL+"/details/"+map.get("noticeType")+"/"+map.get("noticeShareId")+"' target = '_blank' style = 'color:#e68303;'>"+map.get("noticeShare")+"</a></span>");
			if(!map.get("additionalMessage").toString().equals(""))
				sb.append("<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>附加信息 : "+map.get("additionalMessage")+"</span>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
			if(map.get("isShareType").toString().equals("1")){
				sb.append("			<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:"+map.get("senderid")+",userName:\""+map.get("senderName")+"\"});'>私信</a>");
				sb.append(" 			<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'orderBox.load({idleid:"+map.get("noticeShareId")+"});'>分享单</a>");
			}
			sb.append("         	<span style = 'float:left;color:#c1c1c1;'>"+map.get("sendTime")+"</span>");
			sb.append("         </span>");
			sb.append("     </div>");
			sb.append("		<div style = 'float:right;width:60px;'>");
			sb.append("			<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){notice.ignore({noid:"+map.get("id")+"});},msg:\"确认删除这条通知?\"});'><img src = '"+URL+"/web/image/base/del_button.png' style = 'float:left;width:18px;margin-left:15px;margin-top:3px;' title='忽略通知'/></a>");
			sb.append("		</div>");
			sb.append("</div>");
		}
		if(list.size()==msgSize){
			sb.append("<div style = 'float:left;width:100%;font-size:15px;margin-top:10px;border-radius:0px;' id = 'no_getMore'>");
			sb.append("		<a href='javascript:void(0);' style='color:#e68303;' onclick = 'getMoreUnReadMessage.notice();'>获取更多未读通知</a>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 未读评论模板
	 * @param list
	 * @return
	 */
	public String commentMessageList(List list){
		if(list == null || list.size()<=0) return "<div style = 'float:left;width:100%;margin-top:40px;'>暂无未读留言</div>";
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String commType = map.get("commType").toString(),
					userName = map.get("senderName").toString(),
					id = map.get("id").toString(),
					senderid = map.get("senderid").toString();
			sb.append("<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = 'comm_"+id+"'>");
			sb.append("		<a href = '"+URL+"/user/"+senderid+"'><img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>");
			sb.append("		<div style = 'float:left;margin-left:20px;width:520px;'>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '"+URL+"/user/"+senderid+"' style='color:#e68303;'>"+userName+"</a> : "+map.get("content")+"</span>");
			//基于闲置分享留言
			if(commType.equals("goods"))
				sb.append("			<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>物品详情 : <a href ='"+URL+"/details/"+commType+"/"+map.get("commShareId")+"' target = '_blank' style = 'color:#e68303;'>"+map.get("commShare")+"</a></span>");
			sb.append("			<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
			sb.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'commentReply.show({target:$(\"#comm_reply_container_"+id+"\"),userName:\""+userName+"\",userid:"+senderid+",type:\""+commType+"\",typeid:"+map.get("commShareId")+",share:\""+map.get("commShare")+"\"});'>留言回复</a>");
			sb.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:"+senderid+",userName:\""+userName+"\"});'>私信</a>");
			sb.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'confirmBox.show({func:function(){shield.user({userid:"+senderid+"});},msg:\"确认屏蔽"+userName+"所发信息?\"});'>屏蔽此人信息</a>");
			sb.append("         	<span style = 'float:left;color:#c1c1c1;'>"+map.get("sendTime")+"</span>");
			sb.append("         </span>");
			sb.append("			<div id = 'comm_reply_container_"+id+"' style = 'float:left;width:100%;margin-top:5px;'></div>");
			sb.append("     </div>");
			sb.append("</div>");
		}
		if(list.size()==msgSize){
			sb.append("<div style = 'float:left;width:100%;font-size:15px;margin-top:10px;border-radius:0px;' id = 'comm_getMore'>");
			sb.append("		<a href='javascript:void(0);' style='color:#e68303;' onclick = 'getMoreUnReadMessage.comment();'>获取更多未读留言</a>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 相关评论模板
	 * @param commlist
	 * @param params
	 * @return
	 */
	public String matterCommentList(List commlist,Map params){
		StringBuffer msg = new StringBuffer();
		Object id = params.get("typeid"),type = params.get("type");
		if(commlist!=null&&commlist.size()>0)
		{
			Integer cnumber = (Integer)commlist.size();
			for(Integer i = 0 ; i < cnumber ; i++){
				Map map =  (Map) commlist.get(i);
				msg.append("<div style = 'float:left;width:500px;margin:5px;'>");
				msg.append("		<img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:30px;height:30px;'/>");
				msg.append("		<span style = 'float:left;text-align:left;margin-left:10px;width:450px;font-size:13px;'>");
				msg.append("			<a href = '"+URL+"/user/"+map.get("senderid")+"' style = 'color:#e68303;'>"+map.get("senderName")+"</a> : "+map.get("content"));
				msg.append("			<span style = 'float:left;text-align:left;margin-top:5px;width:450px;font-size:13px;'>");
				msg.append("				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;' onclick = 'commentReply.show({target:$(\"#comm_reply_container_"+type+"_"+id+"\"),userName:\""+map.get("senderName")+"\",userid:"+map.get("senderid")+",type:\""+type+"\",typeid:"+id+",share:\""+map.get("commShare")+"\"});'>回复</a>");
				msg.append("				<span style = 'float:left;color:#b1b1b1;margin-left:20px;'>");
				msg.append(					map.get("sendTime"));
				msg.append("				</span>");
				msg.append("			</span>");
				msg.append("		</span>");
				msg.append("</div>");
				msg.append("<div style = 'float:left;width:510px;margin-left:5px;border-bottom:1px dotted #d1d1d1;border-radius:0px;'></div>");
			}
			if(cnumber >= msgSize)
				msg.append("<div style = float:left;width:100%;height:25px;line-height:25px;font-size:13px;color:#e68303;cursor:pointer; onclick = 'commentReply.getMore({tb:$(\"#comment_block_"+type+"_"+id+"\"),target:$(this),type:\""+type+"\",typeid:"+id+",pn:"+params.get("pn")+"});'>查看更多留言信息</div>");
		}
		return msg.toString();
	}
	
	/**
	 * 未读评论模板
	 * @param list
	 * @return
	 */
	public String atMessageList(List list){
		if(list == null || list.size()<=0) return "<div style = 'float:left;width:100%;margin-top:40px;'>暂无未读@信息</div>";
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++){
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String atid = map.get("id").toString(),
					atType = map.get("atType").toString(),
					atShareId = map.get("atShareId").toString(),
					senderName = map.get("senderName").toString(),
					senderid = map.get("senderid").toString();
			Map pub = (Map) map.get("publish");
			sb.append("<div style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;position:relative;' id = 'at_"+atid+"'>");
			sb.append("	<div style = 'position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url("+StaticInfo.URL+"/web/image/base/white_placeholder.png);' id = 'at_del_loading_"+atid+"'><span>删除ing</span><img src = '"+StaticInfo.URL+"/web/image/register/loading.gif'/></div>");
			sb.append("	<a href = '"+StaticInfo.URL+"/user/"+senderid+"'>");
			sb.append("		<img src = '"+map.get("senderAvatar")+"' style = 'float:left;width:50px;height:50px;margin-left:20px;'/>");
			sb.append("	</a>");
			sb.append("	<div style = 'float:left;margin-left:20px;width:520px;'>");
			sb.append("		<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'>");
			sb.append("			<a href = '"+StaticInfo.URL+"/user/"+senderid+"' style='color:#e68303;'>"+senderName+" </a>");
			if(atType.equals("goods"))
				sb.append("	 		在一条物品发布中@了我 : ");
			else if(atType.equals("comment"))
				sb.append("	 		在一条评论中@了我 : ");
			sb.append("		</span>");
			sb.append("		<span style = 'float:left;width:100%;margin-top:5px;text-align:left;'>");
			sb.append("			“"+map.get("content")+"”");
			sb.append("		</span>");
			if(atType.equals("goods")){
				Object idleType = pub.get("idleType").toString(),
						img1 = pub.get("idleImg1"),img2 = pub.get("idleImg2"),img3 = pub.get("idleImg3");
				sb.append("		<div style = 'float:left;margin-top:10px;width:500px;background:#f1f1f1;padding:10px;'>");
				sb.append("			<a href = '"+StaticInfo.URL+"/user/"+pub.get("userid")+"'>");
				sb.append("				<img src = '"+pub.get("avatar")+"' style = 'float:left;width:40px;height:40px;'/>");
				sb.append("			</a>");
				sb.append("			<span style = 'float:left;margin-left:10px;margin-top:2px;text-align:left;'><a href = '"+StaticInfo.URL+"/user/"+pub.get("userid")+"' style='color:#e68303;'>"+pub.get("name")+" </a> :");
				if(idleType.equals("sell"))
					sb.append("			出售");
				else if(idleType.equals("lend"))
					sb.append("			出借");
				else if(idleType.equals("gift"))
					sb.append("			赠送");
				sb.append("				<a href = '"+StaticInfo.URL+"/details/goods/"+atShareId+"' target = '_blank' style = 'color:#e68303;'>"+pub.get("idle")+"</a>");
				sb.append("			</span>");
				sb.append("			<span style = 'float:left;'>");
				if(img1 != null)
					sb.append("				<a href ='"+StaticInfo.URL+"/details/goods/"+atShareId+"' target = '_blank' ><img src = '"+img1+"' style = 'float:left;margin-left:10px;max-width:120px;max-height:120px;margin-top:8px;'/></a>");
				if(img2 != null)
					sb.append("				<a href ='"+StaticInfo.URL+"/details/goods/"+atShareId+"' target = '_blank' ><img src = '"+img2+"' style = 'float:left;margin-left:20px;max-width:120px;max-height:120px;margin-top:8px;'/></a>");
				if(img3 != null)
					sb.append("				<a href ='"+StaticInfo.URL+"/details/goods/"+atShareId+"' target = '_blank' ><img src = '"+img3+"' style = 'float:left;margin-left:20px;max-width:120px;max-height:120px;margin-top:8px;'/></a>");
				sb.append("			</span>");
				sb.append("			<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
				sb.append("				<a href = 'javascript:void(0);' style = 'float:left;margin-left:50px;color:#e68303;' onclick = 'commentReply.show({target:$(\"#comm_reply_container_"+atid+"\"),userName:\""+pub.get("name")+"\",userid:"+pub.get("userid")+",type:\""+atType+"\",typeid:"+atShareId+",share:\""+pub.get("idle")+"\"});'>回复</a>");
				sb.append("				<span style = 'float:left;margin-left:20px;color:#b1b1b1;font-size:13px;'>");
				sb.append(pub.get("sendTime"));
				sb.append("				</span>");
				sb.append("			</span>");
				sb.append("			<div id = 'comm_reply_container_"+atid+"' style = 'float:left;width:100%;margin-top:5px;'></div>");
				sb.append("		</div>	");
			}
			else if(atType.equals("comment")){
				
			}
			sb.append("		<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>");
			sb.append("			<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:"+senderid+",userName:\""+senderName+"\"});'>私信</a>");
			sb.append("			<span style = 'float:left;margin-left:20px;color:#b1b1b1;font-size:13px;'>");
			sb.append(	map.get("sendTime"));
			sb.append("			</span>");
			sb.append("		</span>");
			sb.append("	</div>");
			sb.append("	<div style = 'float:left;width:60px;'>");
			sb.append("		<a href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){atMessage.ignore({atid:"+atid+"});},msg:\"确认删除此条@信息?\"});'><img src = '"+StaticInfo.URL+"/web/image/base/del_button.png' style = 'float:left;width:18px;margin-left:15px;margin-top:3px;' title='删除@信息'/></a>");
			sb.append("	</div>");
			sb.append("</div>");
		}
		if(list.size()==msgSize){
			sb.append("<div style = 'float:left;width:100%;font-size:15px;margin-top:10px;border-radius:0px;' id = 'at_getMore'>");
			sb.append("		<a href='javascript:void(0);' style='color:#e68303;' onclick = 'getMoreUnReadMessage.at();'>获取更多未读@信息</a>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * 请求盒子
	 * @param params
	 * @return
	 */
	public String requestBox(Map params){
		Object shareType = params.get("type"),
				phone = params.get("phone")!=null?params.get("phone").toString():"",
				price = params.get("price")!=null?params.get("price").toString():"",
				delivery	 = params.get("reqDelivery")!=null?params.get("reqDelivery").toString():"自主交易";
		if(shareType.equals("sell")) shareType = "卖";
		else if(shareType.equals("lend")) shareType = "借";
		else if(shareType.equals("gift")) shareType = "送";
		StringBuffer sb = new StringBuffer();
		sb.append("<div id = 'msg_request_box' style='position:absolute;display:none;width:480px;background:white;border-radius:10px;border:2px solid #c1c1c1;z-index:108;'>");
		sb.append("		<div style = 'float:left;width:440px;margin-left:20px;margin-top:10px;font-size:16px;'>");
		sb.append("         <div style = 'float:left;width:410px;'>");
		sb.append("				<span>同意分享 <label id = 'msg_request_idle' style = 'color:#e68303;'>"+params.get("goods").toString()+"</label> </span>");
		sb.append("				<span>给 <img id = 'msg_request_avatar' style = 'width:35px;height:35px;vertical-align:middle;' src = '"+params.get("head_ico").toString()+"' /> <label id = 'msg_request_name' style = 'color:#e68303;'>"+params.get("realname")+"</label></span>");
		sb.append("			</div>"); 
		sb.append(" 			<a href = 'javascript:void(0);' style = 'float:right;width:20px;margin-top:5px;'  onclick = 'requestBox.cancel()'><img src = '/web/image/base/del_button.png' style = 'width:20px;height:20px;' alt='取消'/></a>");
		sb.append("			<div style = 'float:left;width:100%;margin-top:5px;border-bottom:1px solid #d8d8d8;'></div>");
		sb.append("		</div>");
		sb.append("		<div id = 'msg_request_shareInfo' style = 'float:left;width:420px;margin-left:30px;margin-top:5px;'>");
		sb.append("			<span style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>分享方式 : </label><label id = 'msg_request_shareType' style = 'color:#e68303;'>"+shareType+"</label></span>");
		if(shareType.equals("卖")){
			sb.append("		<span class = 'msg_request_extend' style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>成交价格 : </label><label style = 'float:left'>￥ </label><input class = 'text' id = 'msg_request_price' style = 'width:60px;margin-top:5px;' value = '"+price+"' maxlength=7 onpaste = 'return false;' onkeydown = 'return numbervalidate();' /> <label style = 'float:left;margin-left:5px;'>* <label style = 'color:#b1b1b1;'>(将按此价格交易,请勿随意修改)</label></label></span>");
		}
		sb.append("		</div>");
		sb.append("		<div id = 'msg_request_get' style = 'float:left;width:420px;margin-left:30px;'>");
		sb.append("			<div style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>交易方式 : </label>" +
				"<div id = 'msg_request_sendInfo' style = 'float:left;width:310px;'><span id = 'msg_request_getType' style = 'float:left;width:100%;text-align:left;'>对方选择了<label id = 'msg_request_delivery' style = 'color:#e68303;'>"+delivery+"</label></span>");
		if(delivery.equals("自主交易")){
			sb.append("			<span style = 'float:left;line-height:25px;font-size:14px;width:100%;text-align:left;color:#b1b1b1;'>注 : 请及时与对方进行私信或线下联系</span>");
		}else if(delivery.equals("网站送货")){
			sb.append("			<div style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>物品接收地点 : </label>");
			sb.append("				<div style = 'float:left;width:200px;left;text-align:left;'>");
			sb.append("					<span style = 'float:left;width:100%;text-align:left;'><input type = 'radio' name = 'msg_request_sendType' name = 'msg_request_sendType_outField' checked/>网站外场</span>");
			sb.append("				</div>");
			sb.append("				<div style = 'float:left;width:100%;text-align:left;line-height:25px;font-size:14px;color:#b1b1b1;'>注 : 网站只提供<label style = 'color:#e68303;'>从本部到沙河送货</label>,成交后请将物品送到到指定外场。详情请参考<a style = 'color:#e68303;' target='_blank' href = '/activity/grad_carnival/show'>活动细则</a></div>");
			sb.append("			</div>");
		}
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("	</div>");
		sb.append("	<div id = 'msg_request_contactInfo' style = 'float:left;width:420px;margin-left:30px;'>");
		sb.append("		<span style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>联系电话 : </label><input id = 'msg_request_phone' class = 'text' style = 'margin-top:5px;' value = '"+phone+"' onpaste = 'return false;' onkeydown = 'return numbervalidate();' maxlength = '11' /><label style = 'float:left;margin-left:10px;cursor:pointer;'> * (告诉对方<input type = 'checkbox' checked id = 'msg_request_showContact'/>)</label></span>");
		sb.append("	</div>");
		sb.append("	<div id = 'msg_request_flag' style = 'float:left;width:420px;margin-left:30px;'>");
		sb.append("		<span style = 'float:left;line-height:35px;font-size:14px;width:100%;text-align:left;'><label class = 'msg_request_infoTitle'>标记为已分享 : </label><label style = 'float:left;'> <input id = 'msg_request_flag' type = 'checkbox' checked/> <label style='color:#b1b1b1;'>(若该条发布里还有未出物品请勿勾选)</label></label></span>");
		sb.append("	</div>");
		sb.append("	<div style = 'float:left;width:100%;height:35px;'>");
		sb.append("		<span id = 'msg_request_title' style = 'float:left;margin-left:30px;line-height:35px;font-size:14px;'>有什么要补充的吗 : </span>");
		sb.append("		<img src = '/web/image/register/loading.gif' style = 'float:left;margin-left:30px;margin-top:13px;display:none;' id = 'req_loading'/>");
		sb.append("	</div>");
		sb.append("	<div style = 'float:left;width:100%;'>");
		sb.append("		<textarea id = 'msg_request_text' style = 'float:left;margin-top:5px;margin-left:30px;width:280px;height:40px;border-radius:5px;border:1px solid #e6e6e6;resize:none;font-size:14px;text-align:left;' onkeyup='textLimit.getLength({id:\"msg_request_textlimit\",target_text:$(this)});' TextDefault = '有什么要补充的吗?'></textarea>");
		sb.append("		<div style='float:left;width:50px;margin-top:5px;'>");
		sb.append("		<div class = 'facelist face-switch' id = 'msg_request_facelist' onclick = 'getFaceList(\"msg_request_facelist\",$(\"#msg_request_text\"),\"msg_request_textlimit\");'><img src = '/web/image/base/face.png' style = 'float:left;'/></div>");
		sb.append("			<div id = 'msg_request_textlimit' style = 'float:left;margin-left:10px;height:20px;color:grey;font-size:13px;line-height:22px;margin-top:10px;'>0/140</div>");
		sb.append("		</div>");
		sb.append("		<div style = 'float:left;width:70px;margin-left:15px;'>");
		sb.append("			<input type = 'hidden' id = 'reqid' value = '"+params.get("reqid")+"'>");
		sb.append("			<input type = 'hidden' id = 'req_senderid' value = '"+params.get("senderid").toString()+"'>");
		sb.append("			<a href = 'javascript:void:(0)' class = 'button' style = 'float:left;width:70px;height:50px;line-height:51px;margin-top:5px;border-radius:3px;font-size:20px;' onclick = 'requestBox.submit()'>确定</a>");
		sb.append("		</div>");
		sb.append("	</div>");
		sb.append("	<div style = 'float:left;width:100%;height:20px;'></div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 分享单盒子
	 * @param params
	 * @param loggedUid
	 * @return
	 */
	public String orderBox(Map params,Long loggedUid){
		Object shareType = params.get("shareType"),
				price = (params.get("price")!=null && !params.get("price").toString().equals(""))?params.get("price").toString():"面议",
				lendTime = (params.get("lendTimeLong")!=null && !params.get("lendTimeLong").toString().equals(""))?params.get("lendTimeLong").toString():"面议";
		
		if(shareType.equals("sell")) shareType = "卖";
		else if(shareType.equals("lend")) shareType = "借";
		else if(shareType.equals("gift")) shareType = "送";
		StringBuffer sb = new StringBuffer();
		sb.append("<div id = 'order_box'>");
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<div style = 'float:left;width:390px;line-height:30px;'>");
		sb.append("				<span style = 'float:left;text-align:left;'><label style = 'color:#e68303;font-size:20px;'>物品分享单</label></span>");
		sb.append("			</div>");
		sb.append("			<a href = 'javascript:void(0);' style = 'float:right;width:20px;margin-top:5px;' onclick = 'orderBox.hide();'><img src = '/web/image/base/del_button.png' style = 'width:20px;height:20px;' alt='关闭分享单'/></a>");
		sb.append("			<div style = 'float:left;width:100%;margin-top:5px;border-bottom:1px solid #d8d8d8;'></div>");
		sb.append("		</div>");
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<span class = 'order_box_title'>编号 :</span>");
		sb.append("			<div style = 'float:left;width:320px;'>");
		sb.append("				<span class = 'order_box_info'><label id = 'order_id'>"+params.get("id")+"</label></span>");
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<span class = 'order_box_title'>物品名称 :</span>");
		sb.append("			<div style = 'float:left;width:320px;'>");
		sb.append("				<span class = 'order_box_info'><label id = 'order_idleName'>"+params.get("idle")+"</label></span>");
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class = 'order_box_row' style = 'line-height:40px;'>");
		sb.append("			<span class = 'order_box_title' style = 'line-height:40px;'>交易双方 :</span>");
		sb.append("			<div style = 'float:left;'>");
		sb.append("				<img src = '"+params.get("sellerAvatar")+"' style = 'float:left;width:40px;height:40px'/>");
		sb.append("				<span style = 'float:left;margin-left:5px;'>"+params.get("sellerName")+"</span>");
		sb.append("			</div>");
		sb.append("			<div style = 'float:left;margin-left:15px;'>");
		sb.append("				<img src = '/web/image/base/arrow_left.png' style = 'float:left;height:16px;margin-top:10px;'/>");
		sb.append("				<img src = '/web/image/base/arrow_left.png' style = 'float:left;height:16px;margin-top:10px;'/>");
		sb.append("			</div>");
		sb.append("			<div style = 'float:left;margin-left:15px;'>");
		sb.append("				<img src = '"+params.get("buyerAvatar")+"' style = 'float:left;width:40px;height:40px'/>");
		sb.append("				<span style = 'float:left;margin-left:5px;'>"+params.get("buyerName")+"</span>");
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<span class = 'order_box_title'>分享方式 :</span>");
		sb.append("			<div style = 'float:left;'>");
		sb.append("				<span class = 'order_box_info'>"+shareType+"</span>");
		sb.append("			</div>");
		sb.append("		</div>");
		if(shareType.equals("卖")){
			sb.append("		<div class = 'order_box_row'>");
			sb.append("			<span class = 'order_box_title'>价格(￥) :</span>");
			sb.append("			<div style = 'float:left;'>");
			sb.append("				<span class = 'order_box_info'><label>"+price+"</label></span>");
			sb.append("			</div>");
			sb.append("		</div>");
		}else if(shareType.equals("借")){
			sb.append("		<div class = 'order_box_row'>");
			sb.append("			<span class = 'order_box_title'>借出时长 :</span>");
			sb.append("			<div style = 'float:left;'>");
			sb.append("				<span class = 'order_box_info'><label>"+lendTime+"</label></span>");
			sb.append("			</div>");
			sb.append("		</div>");
		}
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<span class = 'order_box_title'>交易方式 :</span>");
		sb.append("			<div style = 'float:left;width:320px;'>");
		sb.append("				<span class = 'order_box_info' style = 'float:left;width:100%;text-align:left;'>"+params.get("deliveryType")+"</span>");
		sb.append("				<span style = 'float:left;color:#b1b1b1;font-size:14px;margin-top:5px;text-align:left;'>注 : "+params.get("remark")+"</span>");
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class = 'order_box_row'>");
		sb.append("			<span class = 'order_box_title'>分享状态 :</span>");
		sb.append("			<div style = 'float:left;width:320px;'>");
		if(params.get("isComplete").toString().equals("1")){
			sb.append("			<span class = 'order_box_info' style = 'float:left;width:100%;text-align:left;color:#e68303;'>已完成</span>");
		}else{
			if(loggedUid.toString().equals(params.get("seller_uid").toString())){
				sb.append("		<span class = 'order_box_info' style = 'float:left;width:100%;text-align:left;'>对方还未确认收到物品</span>");
			}else if(loggedUid.toString().equals(params.get("buyer_uid").toString())){
				sb.append("		<a id = 'order_confirm_btn' class = 'button' style = 'float:left;width:80px;height:25px;line-height:26px;border-radius:3px;' onclick = 'confirmBox.show({func:function(){orderBox.confirmTran({idleid:"+params.get("idleid")+"});},msg:\"确认收到"+params.get("idle")+"?<br><br>未收到物品前请勿确认此项!\"});'>确认收到</a>");
			}
		}
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div style = 'float:left;width:100%;height:25px;'></div>");
		sb.append("	</div>");
		return sb.toString();
	}
}
