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
import services.user.UserService;
import util.EmailUtil;
import util.GetAC;
import util.GetTime;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

public class EmailValidation extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String email;
	
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String message = "",flag = "0";
		CommonService cs = (CommonService) GetAC.getAppContext().getBean("XMLService");
		UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
		try{
			Long uid = us.getUserId(email);
			//删除之前的验证信息
			cs.deleteOptKey(uid, "emailValidation");
			Date date = new Date();
			String uuid = java.util.UUID.randomUUID().toString();
			OptionsKey ok = new OptionsKey();
			ok.setAdd_time(date);
			ok.setKey(uuid);
			ok.setUid(uid);
			ok.setEmail(email);
			ok.setOption("emailValidation");
			//添加验证信息
			if(cs.addOptKey(ok)){
				//发送激活邮件
				String url = StaticInfo.webURL+"/activate/do?key="+uuid;
				String from = "noreply@quan15.com";
				String []to = {email};
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
				flag = "1";
				message = "激活邮件已发到你的邮箱中 , 请及时查看";
			}else message = "验证邮件发送失败 , 请稍后再试";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	public String activate(){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String message = "",flag = "0";
		try{
			CommonService cs = (CommonService) GetAC.getAppContext().getBean("XMLService");
			Map map = cs.getEmailValidationUid(key);
			if(map != null){
				Long uid = Long.parseLong(map.get("uid").toString());
				Date d = (Date) map.get("add_time");
				if(!GetTime.getTimeDifference(new Date(), d, "byDay", 2)){
					UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
					Map params = new HashMap();
					params.put("is_pass", "1");
					if(us.updateUserInfo(uid, "is_pass", params)){
						flag = "1";
					}else message = "操作失败,服务器错误";
				}else message = "该链接已经超过了有效期~ 请重新发送验证邮件";
				cs.deleteOptKey(uid, "emailValidation");
			}else{
				message = "该链接无效,请重新发送验证邮件";
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		request.setAttribute("flag", flag);
		request.setAttribute("message", message);
		return SUCCESS;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
