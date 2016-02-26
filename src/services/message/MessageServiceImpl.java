package services.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.BeanHelper;
import util.GetTime;

import model.AtMessage;
import model.CommentMessage;
import model.NoticeMessage;
import model.PrivateMessage;
import model.RequestMessage;

import dao.base.BaseDao;

public class MessageServiceImpl implements MessageService {

	BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	private List requestListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderName",obj[0]);
				map.put("senderNickName",obj[1]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[2]);
				map.put("id",obj[3]);
				map.put("userid",obj[4]);
				map.put("senderid",obj[5]);
				map.put("content",obj[6]);
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("reqMessage",obj[8]!=null?obj[8]:"");
				map.put("reqType",obj[9]!=null?obj[9]:"");
				map.put("reqShare",obj[10]!=null?obj[10]:"");
				map.put("reqShareType",obj[11]!=null?obj[11]:"");
				map.put("reqShareId",obj[12]!=null?obj[12]:"");
				map.put("isAccept",obj[13]!=null?obj[13]:"");
				map.put("reqDelivery",obj[14]!=null?obj[14]:"");
				map.put("reqShareImg1",obj[15]!=null?obj[15]:"");
				map.put("reqShareImg2",obj[16]!=null?obj[16]:"");
				map.put("reqShareImg3",obj[17]!=null?obj[17]:"");
				newList.add(map);
			}	
		}
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	private List privateListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderName",obj[0].toString());
				map.put("senderNickName",obj[1]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[2].toString());
				map.put("id",obj[3].toString());
				map.put("userid",obj[4].toString());
				map.put("senderid",obj[5].toString());
				map.put("content",obj[6].toString());
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("isPublic",obj[8].toString());
				newList.add(map);
			}	
		}
		return newList;
	}
	
	private List privateUserToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderid",obj[0].toString());
				map.put("senderName",obj[1].toString());
				map.put("senderNickName",obj[2]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[3].toString());
				map.put("content",obj[4]);
				newList.add(map);
			}	
		}
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	private List commentListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderName",obj[0].toString());
				map.put("senderNickName",obj[1]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[2].toString());
				map.put("id",obj[3].toString());
				map.put("userid",obj[4].toString());
				map.put("senderid",obj[5].toString());
				map.put("content",obj[6].toString());
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("commShare",obj[8]!=null?obj[8].toString():"");
				map.put("commShareId",obj[9]!=null?obj[9].toString():"");
				map.put("commType",obj[10]!=null?obj[10].toString():"");
				newList.add(map);
			}	
		}
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	private List noticeListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderName",obj[0].toString());
				map.put("senderNickName",obj[1]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[2].toString());
				map.put("id",obj[3].toString());
				map.put("userid",obj[4].toString());
				map.put("senderid",obj[5].toString());
				map.put("content",obj[6].toString());
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("noticeShare",obj[8]!=null?obj[8]:"");
				map.put("noticeShareId",obj[9]!=null?obj[9]:"");
				map.put("noticeType",obj[10]!=null?obj[10]:"");
				map.put("additionalMessage",obj[11]!=null?obj[11]:"");
				map.put("isShareType",obj[12].toString());
				newList.add(map);
			}	
		}
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	private List atListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("senderName",obj[0].toString());
				map.put("senderNickName",obj[1]!=null?obj[1].toString():"");
				map.put("senderAvatar",obj[2].toString());
				map.put("id",obj[3].toString());
				map.put("userid",obj[4].toString());
				map.put("senderid",obj[5].toString());
				map.put("content",obj[6].toString());
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("atShareId",obj[8].toString());
				map.put("atType",obj[9].toString());
				newList.add(map);
			}	
		}
		return newList;
	}
	
	private String messageSQLExtends(Long userid){
		//过滤屏蔽的用户
		StringBuffer sb = new StringBuffer();
		sb.append("and not exists(select userid from shield where userid = "+userid+" and shieldid = u.id)");
		return sb.toString();
	}
	
	public Map<String, Object> getUnReadMessageCount(Long userid) {
		// TODO Auto-generated method stub
		Map<String,Object>map = new HashMap<String,Object>();
		String count = "select sum(message_type='comment'),sum(message_type='request'),sum(message_type='notice'),sum(message_type='private'),sum(message_type='at') from user_message where userid="+userid;
		try{
			List ur = baseDao.getPageListBySQL(count, 1, 0);
			if(ur != null && ur.size() > 0){
				Object []o = (Object[]) ur.get(0);
				map.put("commentCount",o[0]);
				map.put("requestCount",o[1]);
				map.put("noticeCount",o[2]);
				map.put("privateCount",o[3]);
				map.put("atCount",o[4]);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
	}
	public Map<String, Object> getUnReadMessageTotalCount(Long userid) {
		// TODO Auto-generated method stub
		Map<String,Object>map = new HashMap<String,Object>();
		String ur = "select count(1) from user_message where userid="+userid;
		try{
			List urList = baseDao.getPageListBySQL(ur, 1, 0);
			Integer count = Integer.parseInt(urList.get(0).toString());
			map.put("count",urList!=null?(count>99?"99+":count):0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
	}

	public List getUnReadMessage(Long userid, String unReadType, int size,
			int pageno) {
		// TODO Auto-generated method stub
		String sql = null;
		List list = null;
		if(unReadType.equals("request")){
			sql = "select distinct u.realname,u.nickname,u.head_ico,rm.*,(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 0,1),(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 1,1),(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 2,1) from " +
					"user_message as um,requestMessage as rm inner join sh_user as u on (rm.userid = "+userid+" and rm.senderid = u.id) where um.userid = "+userid+" and um.messageid = rm.id and um.message_type='request' "+messageSQLExtends(userid)+" order by rm.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
			return requestListToMap(list);
		}else if(unReadType.equals("private")){
			sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from " +
					"user_message as um,privateMessage as pm inner join sh_user as u on (pm.userid = "+userid+" and pm.senderid = u.id) where um.userid = "+userid+" and um.messageid = pm.id and um.message_type='private' "+messageSQLExtends(userid)+" order by pm.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
			return privateListToMap(list);
		}else if(unReadType.equals("notice")){
			sql = "select distinct u.realname,u.nickname,u.head_ico,n.* from " +
					"user_message as um,noticeMessage as n inner join sh_user as u on (n.userid = "+userid+" and n.senderid = u.id) where um.userid = "+userid+" and um.messageid = n.id and um.message_type='notice' order by n.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
			return noticeListToMap(list);
		}else if(unReadType.equals("comment")){
			sql = "select distinct u.realname,u.nickname,u.head_ico,c.* from " +
					"user_message as um,commentMessage as c inner join sh_user as u on (c.userid = "+userid+" and c.senderid = u.id and c.senderid != "+userid+") where um.userid = "+userid+" and um.messageid = c.id and um.message_type='comment' "+messageSQLExtends(userid)+" order by c.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
			return commentListToMap(list);
		}else if(unReadType.equals("at")){
			sql = "select distinct u.realname,u.nickname,u.head_ico,a.* from " +
					"user_message as um,atMessage as a inner join sh_user as u on (a.userid = "+userid+" and a.senderid = u.id and a.senderid != "+userid+") where um.userid = "+userid+" and um.messageid = a.id and um.message_type='at' "+messageSQLExtends(userid)+" order by a.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
			return atListToMap(list);
		}else {sql = "";return null;}
	}

	public boolean deleteUnReadMessageByType(Long userid, String unReadType,String []msgIdList) {
		// TODO Auto-generated method stub
		boolean flag = false;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from user_message where userid = "+userid+" and message_type = '"+unReadType+"' and messageid in (");
		for(int i = 0 ; i< msgIdList.length;i++){
			sql.append(msgIdList[i]+",");
		}
		String del_sql = sql.substring(0,sql.length()-1)+")";
		try{
			flag = baseDao.execute(del_sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteUnReadMessageSingle(Long messageid) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean insertUnReadMessage(Long userid, Long messageid,
			String unReadType) {
		boolean flag = false;
		try{
			String sql = "insert into user_message(messageid,userid,message_type) values("+messageid+","+userid+",'"+unReadType+"')";
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public List getRequestMessage(Long userid, int size, int pageno) {
		// TODO Auto-generated method stub
		List list = null;
		try{
			String del_sql = "delete from user_message where userid = "+userid+" and message_type = 'request'";
			baseDao.execute(del_sql);
			String sql = "select distinct u.realname,u.nickname,u.head_ico,rm.*,(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 0,1),(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 1,1),(select thumbs_url from picture where type = rm.reqType and typeid = rm.reqShareId limit 2,1) from " +
					"requestMessage as rm inner join sh_user as u on (rm.userid = "+userid+" and rm.senderid = u.id "+messageSQLExtends(userid)+") order by rm.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return requestListToMap(list);
	}
	
	public Map getRequestMessage(Long reqid) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			RequestMessage rm = (RequestMessage) baseDao.get(RequestMessage.class,reqid);
			if(rm != null){
				map = new HashMap();
				map.putAll(BeanHelper.convertBeanToMap(rm));
				map.put("senderid",rm.getSenderid());
				map.put("userid",rm.getUserid());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public List getNoticeMessage(Long userid, int size, int pageno) {
		List list = null;
		try{
			String del_sql = "delete from user_message where userid = "+userid+" and message_type = 'notice'";
			baseDao.execute(del_sql);
			String sql = "select distinct u.realname,u.nickname,u.head_ico,n.* from " +
					"noticeMessage as n inner join sh_user as u on (n.userid = "+userid+" and n.senderid = u.id) order by n.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return noticeListToMap(list);
	}

	public List getPrivateMessage(Long userid,Long senderid, int size, int pageno) {
		List list = null;
		try{
			String del_sql = "delete from user_message where userid = "+userid+" and message_type = 'private'";
			baseDao.execute(del_sql);
			if(senderid != null){
				//获取和某个用户的私信列表
				String sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from " +
					"privateMessage as pm inner join sh_user as u on ((pm.userid = "+userid+" and pm.senderid = "+senderid+")or(pm.senderid = "+userid+" and pm.userid = "+senderid+")) and u.id = pm.senderid and not exists(select userid from privateMessageHidden where privateMessageId = pm.id and userid = "+userid+") order by pm.sendTime desc";
				list = baseDao.getPageListBySQL(sql, size, pageno);
				return privateListToMap(list);
			}
			else {
				//获取每个用户所发的第一条
				String getUserIdSql = "select distinct u.id from privateMessage pm " +
						"inner join sh_user as u on u.id!="+userid+" and ((pm.userid = "+userid+" and pm.senderid = u.id) or (pm.senderid = "+userid+" and pm.userid = u.id)) and not exists(select userid from privateMessageHidden where privateMessageId = pm.id and userid = "+userid+") "+messageSQLExtends(userid)+" order by pm.sendTime desc";
				List uList = baseDao.getPageListBySQL(getUserIdSql);
				for(int i = size*pageno; i< size*(pageno+1);i++){
					if(i < uList.size() && uList.get(i) != null){
						String uid = uList.get(i).toString();
						List singleList = baseDao.getPageListBySQL("select u.id,u.realname,u.nickname,u.head_ico,(select pm.content from privateMessage as pm inner join sh_user on ((pm.userid = "+userid+" and pm.senderid = "+uid+") or (pm.senderid = "+userid+" and pm.userid = "+uid+") and not exists(select userid from privateMessageHidden where privateMessageId = pm.id and userid = "+userid+")) order by pm.sendTime desc limit 0,1) from sh_user as u where u.id = "+uid,1,0);
						if(singleList!=null && singleList.size()>0){
							if(list==null) 
								list = new ArrayList();
							Object []obj = (Object[]) singleList.get(0);
							list.add(obj);
						}
					}else break;
				}
				return privateUserToMap(list);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List getCommentMessageByShareId(Long shareid,String shareType, int size, int pageno) {
		List list = null;
		try{
			String sql = "select distinct u.realname,u.nickname,u.head_ico,comm.* from " +
					"commentMessage as comm inner join sh_user as u on (comm.commShareId = "+shareid+" and comm.commType = '"+shareType+"' and comm.senderid = u.id) order by comm.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return commentListToMap(list);
	}
	
	public List getCommentMessageByCommId(Long commId) {
		List list = null;
		try{
			String sql = "select distinct u.realname,u.nickname,u.head_ico,comm.* from " +
					"commentMessage as comm inner join sh_user as u on (comm.id = "+commId+" and comm.senderid = u.id) order by comm.sendTime desc";
			list = baseDao.getPageListBySQL(sql,1,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return commentListToMap(list);
	}

	public boolean deleteRequestMessage(Long messageid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String del_sql = "delete from user_message where messageid = "+messageid+" and message_type = 'request'";
			baseDao.execute(del_sql);
			flag = baseDao.delete(RequestMessage.class,messageid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteNoticeMessage(Long messageid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String del_sql = "delete from user_message where messageid = "+messageid+" and message_type = 'notice'";
			baseDao.execute(del_sql);
			flag = baseDao.delete(NoticeMessage.class,messageid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deletePrivateMessage(Long messageid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String del_sql = "delete from user_message where messageid = "+messageid+" and message_type = 'private'";
			baseDao.execute(del_sql);
			flag = baseDao.delete(PrivateMessage.class,messageid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteCommentMessage(Long messageid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String del_sql = "delete from user_message where messageid = "+messageid+" and message_type = 'comment'";
			baseDao.execute(del_sql);
			flag = baseDao.delete(CommentMessage.class,messageid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean sendRequestMessage(RequestMessage msg) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(msg);
			if(flag&&!isShieldMessage(msg.getUserid(),msg.getSenderid())){
				this.insertUnReadMessage(msg.getUserid(), msg.getId(),"request");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean sendPrivateMessage(PrivateMessage msg) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(msg);
			if(flag&&!isShieldMessage(msg.getUserid(),msg.getSenderid())){
				this.insertUnReadMessage(msg.getUserid(), msg.getId(),"private");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean sendNoticeMessage(NoticeMessage msg) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(msg);
			if(flag){
				this.insertUnReadMessage(msg.getUserid(), msg.getId(),"notice");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean sendCommentMessage(CommentMessage msg) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(msg);
			//未屏蔽且非同一人
			if(flag&&!isShieldMessage(msg.getUserid(),msg.getSenderid())&&!msg.getSenderid().equals(msg.getUserid())){
				this.insertUnReadMessage(msg.getUserid(), msg.getId(),"comment");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isRequested(Long userid,Long senderid,Map map) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			//请求类型
			Object reqType = map.get("reqType");
			Object reqShareId = map.get("reqShareId");
			String sql = "select id from requestMessage where userid = "+userid+" and senderid = "+senderid+" and reqType='"+reqType.toString()+"'";
			if(reqType.equals("goods"))
				sql += "and reqShareId = "+reqShareId+"";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0)
				flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean canBeSend(Long userid, Long senderid) {
		boolean flag = false;
		if(!userid.equals(senderid))
			flag = true;
		return flag;
	}

	public boolean hidePrivateMessage(Long messageid,Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			PrivateMessage pm = (PrivateMessage) baseDao.get(PrivateMessage.class, messageid);
			//对方是否已经屏蔽,是则删除该条私信
			String isOtherHide = "";
			if(pm.getSenderid().equals(userid))
				isOtherHide = "select privateMessageId from privateMessageHidden where userid = "+pm.getUserid();
			else isOtherHide = "select privateMessageId from privateMessageHidden where userid = "+pm.getSenderid();
			List isOtherHideList = baseDao.getPageListBySQL(isOtherHide, 1, 0);
			if(isOtherHideList!=null && isOtherHideList.size()>0)
			{
				//是
				flag = this.deletePrivateMessage(messageid)&&baseDao.execute("delete from privateMessageHidden where privateMessageId = "+messageid);
			
			}else{
				//否
				flag = baseDao.execute("insert into privateMessageHidden(privateMessageId,userid) values("+messageid+","+userid+")");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isCommented(Long userid, Long typeId, String type) {
		boolean flag = false;
		try{
			String sql = "select id from commentMessage where senderid = "+userid+" and commShareId = "+typeId+" and commType='"+type.toString()+"'";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0)
				flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateToDealComplete(Long reqid) {
		boolean flag = false;
		try{
			String sql = "update requestMessage set isAccept = '1' where id = "+reqid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isDealComplete(Long reqid , Long userid , Long senderid) {
		boolean flag = false;
		try{
			String sql = "select isAccept from requestMessage where id = "+reqid+" and userid = "+userid+" and senderid = "+senderid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size()>0){
				String isAcc = (String) list.get(0);
				if(isAcc.equals("1"))
					flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Object getMessageCount(Long userid, String tableName) {
		String ur = "select count(1) from "+tableName+" where userid="+userid;
		Object o = null;
		try{
			List urList = baseDao.getPageListBySQL(ur, 1, 0);
			Object obj = urList!=null?urList.get(0):0;
			o = obj;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return o;
	}

	public Object getPrivateMessageCount(Long userid, Long senderid) {
		String ur = "";
		if(senderid!=null)
			ur = "select count(1) from " +
					"privateMessage as pm inner join sh_user as u on ((pm.userid = "+userid+" and pm.senderid = "+senderid+")or(pm.senderid = "+userid+" and pm.userid = "+senderid+")) and u.id = pm.senderid and not exists(select userid from privateMessageHidden where privateMessageId = pm.id and userid = "+userid+")";
		else ur = "select count(distinct u.id) from privateMessage pm " +
				"inner join sh_user as u on u.id!="+userid+" and ((pm.userid = "+userid+" and pm.senderid = u.id) or (pm.senderid = "+userid+" and pm.userid = u.id)) and not exists(select userid from privateMessageHidden where privateMessageId = pm.id and userid = "+userid+") "+messageSQLExtends(userid)+" order by pm.sendTime desc";
		Object o = null;
		try{
			List urList = baseDao.getPageListBySQL(ur, 1, 0);
			Object obj = urList!=null?urList.get(0):0;
			o = obj;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}

	public boolean isShieldMessage(Long userid, Long shieldid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "select userid from shield where userid = "+userid+" and shieldid = "+shieldid;
			List list = baseDao.getPageListBySQL(sql);
			if(list!=null&&list.size()>0)
				flag = true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public List getFriendsPrivateMessage(Long friendsid, int size, int pageno) {
		// TODO Auto-generated method stub
		List list = null;
		try{
			String sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from sh_user as u " +
					"inner join privateMessage as pm on (pm.userid = "+friendsid+" and u.id=pm.senderid and pm.isPublic='1' and NOT EXISTS(select userid from privateMessageHidden where privateMessageId=pm.id and userid=pm.userid)) order by pm.sendTime desc";
			list = baseDao.getPageListBySQL(sql,size,pageno);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return privateListToMap(list);
	}

	public Date getCommentLastSendTime(Long userid) {
		// TODO Auto-generated method stub
		Date d = null;
		try{
			String sql = "select sendTime from commentMessage where senderid = "+userid+" order by sendTime desc";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list!=null&&list.size()>0){
				d = (Date) list.get(0);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return d;
	}

	public Object getFriMsgNumber(Long userid) {
		Object cn = null;
		try{
			String sql = "select count(id) from privateMessage where userid = "+userid+" and isPublic='1' and NOT EXISTS(select userid from privateMessageHidden where privateMessageId=id and privateMessageHidden.userid=privateMessage.userid)";
			List l = baseDao.getPageListBySQL(sql,1,0);
			cn = l.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return cn;
	}

	public Date getPrivateLastSendTime(Long userid) {
		Date d = null;
		try{
			String sql = "select sendTime from privateMessage where senderid = "+userid+" order by sendTime desc";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list!=null&&list.size()>0){
				d = (Date) list.get(0);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return d;
	}

	public boolean isCommentMessageExists(Long userid, Long messageid) {
		boolean flag = false;
		try{
			String sql = "select id from commentMessage where id = "+messageid+" and senderid = "+userid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size()>0){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public List getCommentedShare(Long userid, int size, int pageno) {
		List list = null;
		try{
			String sql = "select distinct cm.commType,cm.commShareId from commentMessage as cm where (cm.senderid = "+userid+" or cm.userid = "+userid+") and not exists(select userid from commentedHidden as ch where ch.commentedId=cm.commShareId and ch.commentedType=cm.commType and ch.userid = "+userid+") order by cm.id desc";
			list = baseDao.getPageListBySQL(sql,size,pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public Object getCommentedShareCount(Long userid) {
		Object count = null;
		try{
			String sql = "select count(distinct cm.commType,cm.commShareId) from commentMessage as cm where (cm.senderid = "+userid+" or cm.userid = "+userid+") and not exists(select userid from commentedHidden as ch where ch.commentedId=cm.commShareId and ch.commentedType=cm.commType and ch.userid = "+userid+")";
			count = baseDao.getPageListBySQL(sql).get(0);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	
	public Map getCommentedShareInfo(Long shareId, String shareType , Long userid) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			String sql = "";
			//闲置发布
			if(shareType.equals("goods")){
				sql = "select g.userid," +
						"u.realname," +
						"u.head_ico," +
						"g.id," +
						"g.goods," +
						"g.type," +
						"g.content," +
						"g.add_time," +
						"(select userid from favor where userid="+userid+" and type='goods' and typeid=g.id)," +
						"(select count(userid) from favor where type='goods' and typeid=g.id)," +
						"g.comment_size," +
						"g.lookedtimes," +
						"g.requiredtimes," +
						"g.is_shared," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 0,1)," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 1,1)," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 2,1) " +
								"from goods as g inner join sh_user as u on u.id = g.userid and g.id = "+shareId;
				List list = baseDao.getPageListBySQL(sql,1, 0);
				if(list != null && list.size() > 0){
					map = new HashMap();
					Object []obj = (Object[]) list.get(0);
					map.put("shareType",shareType);
					map.put("userid", obj[0]);
					map.put("name", obj[1]);
					map.put("avatar", obj[2]);
					map.put("shareId", obj[3]);
					map.put("idle", obj[4]);
					map.put("idleType", obj[5]);
					map.put("content", obj[6]);
					map.put("sendTime", GetTime.getSendTime((Date)obj[7]));
					map.put("isFavored", obj[8]);
					map.put("favorTimes", obj[9]);
					map.put("commentSize", obj[10]);
					map.put("lookedTimes", obj[11]);
					map.put("requiredTimes", obj[12]);
					map.put("isShared", obj[13]);
					map.put("idleImg1", obj[14]);
					map.put("idleImg2", obj[15]);
					map.put("idleImg3", obj[16]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public boolean isRequestMessageExists(Long userid, Long messageid) {
		boolean flag = false;
		try{
			String sql = "select id from requestMessage where id = "+messageid+" and userid = "+userid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size()>0){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isIdleShared(Long idleid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "select is_shared from goods where id = "+idleid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size()>0){
				if(list.get(0).toString().equals("1")){
					flag = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean hideCommentedMessage(Long shareId, String shareType,
			Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "insert into commentedHidden(commentedId,commentedType,userid) values("+shareId+",'"+shareType+"',"+userid+")";
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public List getAtFriends(Long userid) {
		// TODO Auto-generated method stub
		List list = null;
		try{
			String sql = "select u.id,u.realname,u.nickname,u.head_ico from sh_user as u inner join friends as f on (f.userid = "+userid+" and f.friendid = u.id) order by f.at_times desc";
			list = baseDao.getPageListBySQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public boolean sendAtMessage(AtMessage msg) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(msg);
			if(flag){
				this.insertUnReadMessage(msg.getUserid(), msg.getId(),"at");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteAtMessage(Long messageid, Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String del = "delete from atMessage where userid = "+userid+" and id = "+messageid;
			flag = baseDao.execute(del);
			if(flag){
				baseDao.execute("delete from user_message where messageid = "+messageid+" and message_type = 'at'");			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public List getAtMessage(Long userid, int size, int pageno) {
		List list = null;
		try{
			String del_sql = "delete from user_message where userid = "+userid+" and message_type = 'at'";
			baseDao.execute(del_sql);
			String sql = "select distinct u.realname,u.nickname,u.head_ico,a.* from " +
					"atMessage as a inner join sh_user as u on (a.userid = "+userid+" and a.senderid = u.id "+messageSQLExtends(userid)+") order by a.sendTime desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return atListToMap(list);
	}

	public Map getAtPublishInfo(Long shareId, String shareType,Long loggedUid) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			String sql = "";
			List list = null;
			if(shareType.equals("goods")){
				sql = "select g.userid," +
						"u.realname," +
						"u.head_ico," +
						"g.id," +
						"g.goods," +
						"g.type," +
						"g.content," +
						"g.add_time," +
						"(select userid from favor where userid="+loggedUid+" and type='goods' and typeid=g.id)," +
						"(select count(userid) from favor where type='goods' and typeid=g.id)," +
						"g.comment_size," +
						"g.lookedtimes," +
						"g.requiredtimes," +
						"g.is_shared," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 0,1)," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 1,1)," +
						"(select thumbs_url from picture where type = 'goods' and typeid = "+shareId+" limit 2,1) " +
								"from goods as g inner join sh_user as u on u.id = g.userid and g.id = "+shareId;
				list = baseDao.getPageListBySQL(sql,1, 0);
				if(list != null && list.size() > 0){
					map = new HashMap();
					Object []obj = (Object[]) list.get(0);
					map.put("userid", obj[0]);
					map.put("name", obj[1]);
					map.put("avatar", obj[2]);
					map.put("shareId", obj[3]);
					map.put("idle", obj[4]);
					map.put("idleType", obj[5]);
					map.put("content", obj[6]);
					map.put("sendTime", GetTime.getSendTime((Date)obj[7]));
					map.put("isFavored", obj[8]);
					map.put("favorTimes", obj[9]);
					map.put("commentSize", obj[10]);
					map.put("lookedTimes", obj[11]);
					map.put("requiredTimes", obj[12]);
					map.put("isShared", obj[13]);
					map.put("idleImg1", obj[14]);
					map.put("idleImg2", obj[15]);
					map.put("idleImg3", obj[16]);
				}
			}else if(shareType.equals("comment")){
				sql = "select u.realname,u.nickname,u.head_ico,comm.* from commentMessage as comm inner join sh_user as u on u.id = comm.senderid where comm.id = "+shareId;
				list = baseDao.getPageListBySQL(sql,1, 0);
				if(list != null && list.size() > 0){
					map = new HashMap();
					Object []obj = (Object[]) list.get(0);
					map.put("senderName",obj[0].toString());
					map.put("senderNickName",obj[1]);
					map.put("senderAvatar",obj[2].toString());
					map.put("id",obj[3].toString());
					map.put("userid",obj[4].toString());
					map.put("senderid",obj[5].toString());
					map.put("content",obj[6].toString());
					map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
					map.put("commShare",obj[8]);
					map.put("commShareId",obj[9]);
					map.put("commType",obj[10]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
