package action.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import model.Picture;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import services.account.AccountService;
import services.share.ShareService;
import services.user.UserService;
import template.NotesBlockTemplate;
import template.UserBlockTemplate;
import util.GetAC;
import util.GetTime;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.base.BaseDao;
import dao.user.UserAddDao;
import dao.user.UserGetDao;

public class UserGet extends ActionSupport{
	
	private Long typeid;
	private Long circleid;
	private String type;
	private Integer pageno;
	private Integer comm_pn;
	private Long senderid;
	private String method;
	private String shareType;
	private String sortType;
	private String showCommLoc;
	private Long resid;
	private String commid;
	//筛选条件
	private String scope;
	private String objType1;
	private String objType2;
	private String onlyShared;
	private String keywords;
	private Double pricelow;
	private Double pricehigh;
	private String olddegree;
	private String sex;
	private String key;
	private String hadpic;
	private List noteslist;
	private Integer count;
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	/**
	 * id
	 */
	private Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
	
	public String feedsList() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		String hasNext = "0";
		int idleSize = StaticInfo.idleSize;
		try{
			Object vid = ServletActionContext.getRequest().getSession().getAttribute("vid");
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Map<String,Object> params = new HashMap<String,Object>();
			String sexSelect = "";
			if(sex!=null&&!sex.equals(""))
				if(sex.equals("0"))
					sexSelect = "女";
				else sexSelect = "男";
			//处理方式
			params.put("shareType", shareType);
			//类型1
			params.put("objType1", objType1);
			//类型2
			params.put("objType2", objType2);
			//发布人性别
			params.put("sex", sexSelect);
			//排序方式
			params.put("sortType", sortType);
			//范围
			params.put("scope", scope);
			//是否完成分享
			params.put("onlyShared",onlyShared!=null?onlyShared:"0");
			//分享类型:feeds(主页分享),userFeeds(个人分享),stranger(陌生人)
			params.put("method", method);
			//个人分享id
			params.put("vid",vid!=null?Long.parseLong(vid.toString()):userid);
			//关键词
			params.put("key",key);
			noteslist = ss.getIdleList(userid,idleSize, pageno, params);
			if(noteslist != null&&noteslist.size() > 0){
				boolean isadmin = false;
				NotesBlockTemplate nbt = new NotesBlockTemplate();
				Object []j = {userid,method.equals("userFeeds")?true:false,isadmin};
				root.put("data",nbt.goodsNotesList(noteslist, j));
				if(noteslist.size()==idleSize)
					hasNext = "1";
			}else root.put("data","<div style='float:left;width:100%;margin-top:30px;'>暂时没有分享信息</div>");
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("message",message);
		root.put("flag", flag);
		root.put("hasNext",hasNext);
		root.write(response.getWriter());
		return null;
	}
	
	
	public String picture() throws IOException{
		JSONObject root = new JSONObject();
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			ugd.getPictureList(typeid, type, 6, 0);
			root.put("flag", "ok");
		}catch(Exception e){
			e.printStackTrace();
			root.put("flag", "error");
		}
		root.write(response.getWriter());
		return null;
	}
	
	
	public String myfriends() throws IOException{
		JSONObject root = new JSONObject();
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			UserBlockTemplate ubt = new UserBlockTemplate();
			String sql = "";
			String msg = ubt.userBoxList(ugd.getFriendsList(userid,21, pageno,true,sql),pageno);
			if(msg != null && !msg.equals(""))
				root.put("data",java.net.URLEncoder.encode(msg,"UTF-8"));
			else root.put("data","");
			root.put("flag", "ok");
		}catch(Exception e){
			e.printStackTrace();
			root.put("flag", "error");
		}
		root.write(response.getWriter());
		return null;
	}
	
	/**
	 * 获取好友列表
	 */
	public String friends() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		int size = 24;
		try{
			pageno = (pageno != null && pageno >= 0)?pageno:0;
			UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
			UserBlockTemplate ubt = new UserBlockTemplate();
			List mku = us.getFriends(userid,size , pageno);
			if(mku!=null && mku.size()>0){
				Integer num = mku.size();
				root.put("mkuNum",num);
			}
			else root.put("mkuNum",0);
			Map map = new HashMap();
			map.put("pageno", pageno);
			String msg = ubt.friendsList(mku,map);
			if(msg != null && !msg.equals("")){
				root.put("data",msg);
			}
			else root.put("data","");
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag",flag);
		root.put("pageno",pageno);
		root.put("hasNext",((pageno + 1)*size < count)?"1":"0");
		root.put("hasFront",(pageno > 0)?"1":"0");
		root.write(response.getWriter());
		return null;
	}
	/**
	 * 可能认识的人
	 * @return
	 * @throws IOException
	 */
	public String mayKnowUser() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		try{
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			UserBlockTemplate ubt = new UserBlockTemplate();
			List mku = ugd.getMayKnowUser(userid,StaticInfo.userNumber,pageno);
			if(mku!=null&&mku.size()>0){
				Integer num = mku.size();
				root.put("mkuNum",num);
			}
			else root.put("mkuNum",0);
			String msg = ubt.myKnowUserList(mku);
			if(msg != null && !msg.equals("")){
				root.put("data",msg);
			}
			else root.put("data","");
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag",flag);
		root.put("size",StaticInfo.userNumber);
		root.write(response.getWriter());
		return null;
	}
	
	public String isShared() throws IOException{
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		if(ugd.getIsShared(typeid, type)){
			response.getWriter().println("ok");
		}else response.getWriter().println("error");
		return null;
	}
	
	public String superiorCircle() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		List sc = ugd.getSuperiorCircle(circleid);
		if(sc!=null&&sc.size()>0){
			Object[] obj = (Object[]) sc.get(0);
			root.put("cid",obj[0]);
			root.put("cname",java.net.URLEncoder.encode(obj[1].toString(),"UTF-8"));
			root.put("ctype",java.net.URLEncoder.encode(obj[2].toString(),"UTF-8"));
			root.put("cico",obj[3]);
			root.put("flag","ok");
		}else root.put("flag","error");
		root.write(response.getWriter());
		return null;
	}


	public void userIntroduce() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String message = "";
		String flag = "0";
		try{
			UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
			AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
			Map map = us.getUserIntroduce(senderid);
			if(map!=null)
			{
				root.put("userInfo",map);
				root.put("renrenbind",as.isBindingRenRen(senderid));
				root.put("areFriends", (us.areFriends(userid, senderid)==true)?"1":"0");
				root.put("isSelf", (senderid.equals(userid))?"1":"0");
				flag = "1";
			}else message = "用户信息获取失败,请稍后再试";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}
	
	public void goods() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		try{
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Map<String, Object> map = ss.getGoodsInfo(typeid);
			if(map!=null){
				List picList = ss.getIdlePictureList(typeid, 6,0);
				map.put("picCount", picList!=null?picList.size():0);
				String pList = "";
				String pId = "";
				if(picList !=null){
					for(int i = 0 ; i < picList.size();i++){
						Map m = (Map) picList.get(i);
						pList += m.get("thumbs_url").toString()+",";
						pId += m.get("id").toString()+",";
					}
					pList = pList.substring(0,pList.length()-1);
					pId = pId.substring(0,pId.length()-1);
				}
				map.put("pList",pList);
				map.put("pId", pId);
				NotesBlockTemplate nbt = new NotesBlockTemplate();
				root.put("data",nbt.updateBox(map));
				flag = "1";
			}else{
				message = "物品信息获取失败,请稍后再试";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag",flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPageno() {
		return pageno;
	}
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}
	public List getNoteslist() {
		return noteslist;
	}
	public void setNoteslist(List noteslist) {
		this.noteslist = noteslist;
	}
	public Long getCircleid() {
		return circleid;
	}
	public void setCircleid(Long circleid) {
		this.circleid = circleid;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Double getPricelow() {
		return pricelow;
	}
	public void setPricelow(Double pricelow) {
		this.pricelow = pricelow;
	}
	public Double getPricehigh() {
		return pricehigh;
	}
	public void setPricehigh(Double pricehigh) {
		this.pricehigh = pricehigh;
	}
	public String getOlddegree() {
		return olddegree;
	}
	public void setOlddegree(String olddegree) {
		this.olddegree = olddegree;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Long getSenderid() {
		return senderid;
	}
	public void setSenderid(Long senderid) {
		this.senderid = senderid;
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

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getHadpic() {
		return hadpic;
	}
	public void setHadpic(String hadpic) {
		this.hadpic = hadpic;
	}

	public String getShowCommLoc() {
		return showCommLoc;
	}
	public void setShowCommLoc(String showCommLoc) {
		this.showCommLoc = showCommLoc;
	}
	public Long getResid() {
		return resid;
	}
	public void setResid(Long resid) {
		this.resid = resid;
	}
	public Integer getComm_pn() {
		return comm_pn;
	}
	public void setComm_pn(Integer commPn) {
		comm_pn = commPn;
	}
	public String getCommid() {
		return commid;
	}
	public void setCommid(String commid) {
		this.commid = commid;
	}

	public String getOnlyShared() {
		return onlyShared;
	}

	public void setOnlyShared(String onlyShared) {
		this.onlyShared = onlyShared;
	}

	

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}
	
}
