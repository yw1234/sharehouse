package action.common;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Email;
import model.OptionsKey;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.common.CommonService;
import services.message.MessageService;
import services.user.UserService;
import util.EmailUtil;
import util.GetAC;
import util.StaticInfo;
import util.WordsFilter;

import com.opensymphony.xwork2.ActionSupport;

public class TipEmail extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userid;
	private String reqMessage;
	private String idle;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	private Long loggedid = (Long) request.getSession().getAttribute("userid");

	/**
	 * 分享提醒邮件
	 */
	public void share(){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		try{
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			Map<String, String> map = new HashMap<String, String>();
			map.put("reqType","goods");						//闲置分享请求
			map.put("reqShareId",id.toString());				//闲置id
			if(ms.isRequested(userid,loggedid,map)){
				UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
				Date date = new Date();
				Map userInfo = us.getUserInfo(userid, "email");
				Map senderInfo = us.getUserInfo(loggedid, "realname");
				String email = userInfo.get("email").toString(),userName = senderInfo.get("realname").toString();
				String url = StaticInfo.webURL+"/index?url="+ StaticInfo.webURL+"/message?page=news";
				String from = "noreply@quan15.com";
				String []to = {email};
				String subject = "你有新的物品分享信息哦~";
				Email e = new Email();
				e.setFrom(from);
				e.setTo(to);
				e.setSendDate(date);
				e.setSubject(subject);
				EmailUtil emailUtil = (EmailUtil) GetAC.getAppContext().getBean("EmailUtil");
				Map<String, String> mp = new HashMap<String, String>();
				mp.put("userName",userName);
				mp.put("idle", idle);
				mp.put("message", WordsFilter.imgFilter(reqMessage));
				mp.put("url", url);
				mp.put("fileName","shareNotice.ftl");
				emailUtil.send(e,mp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void activate() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		CommonService cs = (CommonService) GetAC.getAppContext().getBean("XMLService");
		UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
		String em = us.getUserInfo(loggedid, "email").get("email").toString();
		try{
			//删除之前的验证信息
			cs.deleteOptKey(loggedid, "emailValidation");
			Date date = new Date();
			String uuid = java.util.UUID.randomUUID().toString();
			OptionsKey ok = new OptionsKey();
			ok.setAdd_time(date);
			ok.setKey(uuid);
			ok.setUid(loggedid);
			ok.setEmail(em);
			ok.setOption("emailValidation");
			//添加验证信息
			if(cs.addOptKey(ok)){
				//发送激活邮件
				String url = StaticInfo.webURL+"/activate/do?key="+uuid;
				String from = "noreply@quan15.com";
				String []to = {em};
				String subject = "圈易物账号激活";
				Email email = new Email();
				email.setFrom(from);
				email.setTo(to);
				email.setSendDate(date);
				email.setSubject(subject);
				EmailUtil emailUtil = (EmailUtil) GetAC.getAppContext().getBean("EmailUtil");
				Map params = new HashMap();
				params.put("register_url", url);
				params.put("fileName","register.ftl");
				emailUtil.send(email,params);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public String getIdle() {
		return idle;
	}

	public void setIdle(String idle) {
		this.idle = idle;
	}
	
	
}
