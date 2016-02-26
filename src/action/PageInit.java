package action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import services.account.AccountService;
import services.message.MessageService;
import services.share.ShareService;
import services.user.UserService;
import util.GetAC;
import util.GetTime;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserGetDao;
import dao.user.UserUpdateDao;

public class PageInit extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String type;
	private String page;
	private Long senderid;
	private String nav;
	private Integer p;
	private Integer msgSize = StaticInfo.msgNumber;
	private Integer userSize = StaticInfo.userNumber;
	
	//搜索
	private String key;
	private String kw;
	private String typeList;
	
	//搜索条件-用户
	private String realname;
	private String sex;
	private String school;
	private String province;
	private String city;
	private String department;
	private String grade;
	
	//搜索条件-发布
	private String sendType;
	private String cid;
	private String shareType;
	private String objType1;
	private String objType2;
	private String onlyShared;

	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	//获取当前用户id
	private Long userid=(Long) request.getSession().getAttribute("userid");
	
	/**
	 * 首页
	 * @return
	 * @throws IOException 
	 */
	public String home() throws IOException{
		UserGetDao ugd = (UserGetDao)GetAC.getAppContext().getBean("UserGetDao");
		User u = ugd.getUser(userid);
		if(u.getIsNewUser()!=null && u.getIsNewUser().equals("1")){
			response.sendRedirect(StaticInfo.URL+"/fill_info");
			return null;
		}
		//获取分类列表
		try{
			String fileURL = request.getRealPath("/WEB-INF/type.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					List<Element>generalizeList = country.elements("type");
					Element generalizeElement = null;
					for(int i = 0 ; i < generalizeList.size();i++)
					{
						generalizeElement = generalizeList.get(i);
						result += generalizeElement.attributeValue("name")+",";
					}
					typeList = result.substring(0,result.length()-1);
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		} 
		//绑定账号
		AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
		//人人
		request.setAttribute("renren_binding", as.isBindingRenRen(userid));
		
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		//物品分类
		request.setAttribute("typeList", typeList);
		//用户信息
		request.setAttribute("user",u);
		//未读信息数量
		request.setAttribute("notReadCount", ms.getUnReadMessageTotalCount(userid));
		return SUCCESS;
		
	}
	
	/**
	 * 
	 * 个人主页
	 * @return
	 */
	public String user(){
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
		String []path = request.getRequestURI().split("/");
		Long id = Long.parseLong(path[path.length-1]);
		try{
			User mine = ugd.getUser(userid);
			/**
			 * 他人页面
			 */
			if(!userid.equals(id)){
				boolean flag = ugd.getCanSeePriInfo(userid, id,"privacy_myPage");
				UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao"); 
				request.getSession().setAttribute("vid",id);
				request.setAttribute("areFriends", ugd.getIsFriend(userid, id));
				request.setAttribute("info",ugd.getUser(id));
				request.setAttribute("notUser","1");
				//ServletActionContext.getRequest().setAttribute("f_rightlist",getFriRightList(id,ugd));
				if(flag == true){
					if(GetTime.getTimeDifference(new Date(),ugd.getLastVisitedTime(id, userid),"byMinute",20)){
						uad.addVisited(id, userid);
						uad.addOperationTimes("visted_times",id,"sh_user");
					}
				}else {
					//不可访问
					Map<String, String> map = new HashMap<String,String>();
					map.put("method","stranger");
					//访问一次
					List sglist = ss.getIdleList(id, StaticInfo.feedsNumber, 0, map);
					Object[] countNumber = {ugd.getCircleNumber(id), ugd.getFriendsNumber(id)};
					//不可见
					request.setAttribute("canSee","0");
					//部分物品信息
					request.setAttribute("noteslist",sglist);
					request.setAttribute("countNumber",countNumber);
					request.setAttribute("user",mine);
					return SUCCESS;
				}
			}
			else {
				request.getSession().removeAttribute("vid");
				request.setAttribute("info",mine);
			}
			//以下都为可访问状态
			List noteslist = null;
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			boolean canSeeFri = ugd.getCanSeePriInfo(userid, id,"privacy_myUserList");
			if(canSeeFri)request.setAttribute("canSeeFri","1");
			boolean canSeeMsg = ugd.getCanSeePriInfo(userid, id,"privacy_myMsgList");
			if(canSeeMsg)request.setAttribute("canSeeMsg","1");
			page = (page != null && !page.equals(""))?page:"main";
			/**
			 * 闲置墙
			 */
			if(page.equals("main")){
				
			}
			/**
			 * 个人信息
			 */
			else if(page.equals("info")){
				
			}
			/**
			 * 好友管理
			 */
			else if(userid.equals(id)&&page.equals("friendsManage")){
				/**
				 * 好友列表
				 */
				if(nav.equals("all")){
					//关注的好友
					request.setAttribute("concernedfriends",ugd.getConcernedFriends(userid));
					//好友人数统计
					request.setAttribute("friendsNum",ugd.getFriendsNumber(userid));
					//好友列表,每页20
					request.setAttribute("friendslist",ugd.getFriendsList(userid, userSize,p-1,false,""));
				/**
				 * 黑名单
				 */
				}else if(nav.equals("bl")){
					request.setAttribute("blacklist",ugd.getBlackList(userid,userSize,0));
				}
			}else if(userid.equals(id)&&page.equals("privacy")){
				/**
				 * 基本设置页面
				 */
				if(nav.equals("common")){
					request.setAttribute("show_clist",ugd.getShowPriCircle(userid));
					request.setAttribute("show_ulist",ugd.getShowPriUser(userid));
				/**
				* 重置密码
				*/
				}else if(nav.equals("cp")){
				}
			}
			/**
			 * 他人好友
			 */
			else if(!userid.equals(id)&&page.equals("friends")&&canSeeFri){
				//好友人数统计
				request.setAttribute("friendsNum",ugd.getFriendsNumber(id));
				//好友列表,每页20
				request.setAttribute("friendsList",ugd.getFriFriendsList(id,userid,userSize,p-1));
			/**
			 * 他人私信（暂不开放）
			 */
			}else if(!userid.equals(id)&&page.equals("priMsg")&&canSeeMsg){
				request.setAttribute("msgNum",ms.getFriMsgNumber(id));
				request.setAttribute("priMsg",ms.getFriendsPrivateMessage(id,StaticInfo.msgNumber,p-1));
			}
			//绑定账号
			AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
			//人人
			request.setAttribute("renren_binding", as.isBindingRenRen(userid));
			//可见
			request.setAttribute("canSee","1");
			//用户信息
			request.setAttribute("user",mine);
			//列表
			request.setAttribute("noteslist",noteslist);
			//未读消息
			request.setAttribute("notReadCount", ms.getUnReadMessageTotalCount(userid));
			return SUCCESS;
			
		}catch(Exception e){
			e.getStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	 * 我的好友
	 * @return
	 */
	public String myFriends(){
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			//好友人数统计
			request.setAttribute("friendsNum",ugd.getFriendsNumber(userid));
			request.setAttribute("user",ugd.getUser(userid));
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 好友查找
	 * @return
	 */
	public String searchFriends(){
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			request.setAttribute("user",ugd.getUser(userid));
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 消息
	 * @return
	 */
	public String message(){
		try{
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			List msglist = null;
			/**
			 * 新消息
			 */
			if(page.equals("news")){
				request.setAttribute("unReadCount",ms.getUnReadMessageCount(userid));
			}
			/**
			 * 请求
			 */
			else if(page.equals("req")){
				request.setAttribute("reqCount",ms.getMessageCount(userid, "requestMessage"));
				msglist = ms.getRequestMessage(userid, msgSize,p-1);
			}
			/**
			 * 私信
			 */
			else if(page.equals("pri")){
				if(senderid != null){
					UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
					Map<String, String> info = new HashMap<String, String>();
					info.put("id",senderid.toString());
					info.putAll(us.getUserInfo(senderid, "realname,head_ico"));
					request.setAttribute("senderInfo",info);	
				}
				request.setAttribute("priCount",ms.getPrivateMessageCount(userid, senderid));
				msglist = ms.getPrivateMessage(userid, senderid, msgSize, p-1);	
			}
			/**
			 * 通知
			 */
			else if(page.equals("notice")){
				request.setAttribute("noticeCount",ms.getMessageCount(userid, "noticeMessage"));
				msglist = ms.getNoticeMessage(userid, msgSize, p-1);
			}
			/**
			 * 评论
			 */
			else if(page.equals("comm")){
				request.setAttribute("commentCount",ms.getCommentedShareCount(userid));
				//被评论发布
				List list = ms.getCommentedShare(userid, msgSize, p-1);
				if(list!= null && list.size() > 0){
					msglist = new ArrayList();
					for(int i = 0; i < list.size() ; i++){						
						Object []pub_info = (Object[]) list.get(i);
						String pub_type = pub_info[0].toString() ,pub_id = pub_info[1].toString();
						Map pub = ms.getCommentedShareInfo(Long.parseLong(pub_id), pub_type , userid);
						if(pub != null){
							pub.put("comment",ms.getCommentMessageByShareId(Long.parseLong(pub_id), pub_type, 3, 0));
							msglist.add(pub);
						}
					}
				}
			}
			else if(page.equals("at")){
				request.setAttribute("atCount",ms.getMessageCount(userid, "atMessage"));
				//被评论发布
				List list = ms.getAtMessage(userid, msgSize, p-1);
				if(list!= null && list.size() > 0){
					msglist = new ArrayList();
					for(int i = 0; i < list.size() ; i++){						
						Map at_info = (Map) list.get(i);
						String at_type = at_info.get("atType").toString() ,at_id = at_info.get("atShareId").toString();
						at_info.put("publish",ms.getAtPublishInfo(Long.parseLong(at_id), at_type , userid));
						msglist.add(at_info);
					}
				}
			}
			request.setAttribute("user",ugd.getUser(userid));
			request.setAttribute("msglist", msglist);
			return SUCCESS;
			
		}catch(Exception e){
			e.getStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 搜索结果
	 * @return
	 */
	public String result(){
		try{
			UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
			List slist = null;
			p = (p != null)?p:0;
			if(type.equals("user")){
				Map params = new HashMap();
				params.put("realname", realname);
				params.put("sex", sex);
				params.put("school", school);
				params.put("province", province);
				params.put("city", city);
				params.put("department", department);
				params.put("grade",grade);
				slist = us.getSearchUser(userid,params,userSize,p);
				ServletActionContext.getRequest().setAttribute("userCount",us.getSearchUserCount(userid,params));
			}else if(type.equals("topSearch")){
				key = java.net.URLDecoder.decode(key,"UTF-8");
			}
			
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			request.setAttribute("user",ugd.getUser(userid));
			request.setAttribute("noteslist", slist);
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			request.setAttribute("notReadCount", ms.getUnReadMessageTotalCount(userid));			//ServletActionContext.getRequest().setAttribute("rightlist",getRightList(userid,ugd));
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 闲置详情
	 * @return
	 */
	public String details(){
		JSONObject root = new JSONObject();
		try{
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
			//只显示闲置
			if(type == null || type.equals("")) type = "goods";
			String []path = request.getRequestURI().split("/");
			Long idleId = Long.parseLong(path[path.length-1]);
			/*
			if(showCommLoc.equals("1")){
				root.put("commLoc","#d_comm_block_"+ugd.getCommentLocation(resid));
			}*/
			Map map = ss.getGoodsDetails(idleId);
			if(map != null){
				root.putAll(map);
				//向json中载入goods的全部属性
				Long senderid = Long.parseLong(map.get("obj_userid").toString());
				if(uad.addLookedTimes(userid, idleId,type)){
					uad.addHotSpot(idleId,type,StaticInfo.addHot_lookPoint);
				}
				root.put("showPri", ugd.getCanSeePrivacy(userid, idleId, type));
				Map<String, String> m =  new HashMap<String, String>();
				m.put("reqType",type);
				m.put("reqShareId",map.get("obj_id").toString());
				root.put("isReq",ms.isRequested(senderid,userid, m));
				if(senderid!=null){
					if(senderid!=0&&!senderid.equals(userid)&&ugd.getIsFriend(userid,senderid)==0)root.put("canAddFriend","1");
					else root.put("canAddFriend","0");
					if(senderid!=0&&!senderid.equals(userid))root.put("canComplaint","1");
					else root.put("canComplaint","0");
				}
				List piclist = ugd.getPictureList(idleId, type, 6, 0);
				if(piclist == null || piclist.size() == 0){
					root.put("picnum", 0);
				}else{
					root.put("picnum",piclist.size());
					String purl = "";
					String thumbs_purl=""; 
					for(int i = 0 ;i < piclist.size(); i++){
						Object []o = (Object[]) piclist.get(i);
						if(i == 0){
							purl += o[3].toString();
							thumbs_purl += o[4].toString();
						}
						else {
							purl += ","+o[3].toString();
							thumbs_purl += ","+o[4].toString();
						}
					}
					root.put("piclist",purl);
					root.put("thumbs_piclist",thumbs_purl);
				}
			}else return ERROR;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		request.setAttribute("root", root);
		return SUCCESS;
	}
	
	/**
	 * 
	 * 活动
	 * @return
	 */
	public String activity(){
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			request.setAttribute("user",ugd.getUser(userid));
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getObjType1() {
		return objType1;
	}
	public void setObjType1(String objType1) {
		this.objType1 = objType1;
	}
	public String getObjType2() {
		return objType2;
	}

	public void setObjType2(String objType2) {
		this.objType2 = objType2;
	}
	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}
	public String getOnlyShared() {
		return onlyShared;
	}
	public void setOnlyShared(String onlyShared) {
		this.onlyShared = onlyShared;
	}
	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getMsgSize() {
		return msgSize;
	}

	public void setMsgSize(Integer msgSize) {
		this.msgSize = msgSize;
	}

	public Long getSenderid() {
		return senderid;
	}

	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}

	public Integer getUserSize() {
		return userSize;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTypeList() {
		return typeList;
	}
	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	
}