package action.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import services.message.MessageService;
import services.user.UserService;
import util.AES;
import util.GetAC;
import util.GetTime;
import util.SHA;
import util.StaticInfo;
import model.User;

import com.opensymphony.xwork2.ActionSupport;
import com.renren.api.client.RenrenApiConfig;

import dao.base.BaseDao;
import dao.user.UserGetDao;

public class UserLogin extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private String user_email;
	private String user_password;
	private String remindme;
	private String type;
	private String page;
	private String m;
	private String url;
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();

	public String judge() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		HttpSession session = request.getSession();
		String ip = request.getRemoteAddr();
		Object autoLogin = session.getAttribute("auto_login")!=null?session.getAttribute("auto_login"):"0";
		//移除自动登录
		session.removeAttribute("auto_login");
		//登陆跳转
		url = (url != null && !url.equals("") && !url.equals("null"))?java.net.URLDecoder.decode(url,"UTF-8"):StaticInfo.URL+"/home";
		UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
		if(autoLogin.toString().equals("1"))
		{
			Cookie cookies[] = request.getCookies();
			String sessionid = null;
			String q_uid = null;
			for(Cookie c : cookies)
			{
				if(c.getName().equals("q_s"))
					sessionid = c.getValue();
				else if(c.getName().equals("q_uid"))
					q_uid = c.getValue();
			}
			if(sessionid != null && q_uid != null)
			{
				Map info = us.loginByCookie(AES.decrypt(q_uid, StaticInfo.AESKey),sessionid);
				if(info != null){
					String is_freeze = info.get("freeze").toString();
					if(!is_freeze.equals("1")){
						Long userid = Long.parseLong(info.get("userid").toString());
						us.updateLoginInfo(userid,ip);
						session.setAttribute("userid", userid);
						return SUCCESS;
					}
				}
			}
			session.invalidate();
			return ERROR;
		}else{
			if(user_email != null && user_password != null){
				if(us.isUserFreeze(user_email.trim())){
					message = "您的账号已被查封,若有疑问请向我们的客服邮箱发送邮件";
				}else{
					Object uid = us.loginJudge(user_email, user_password);
					if(uid != null)
					{
						if(remindme.equals("autologin"))
						{
							String uuid = UUID.randomUUID().toString();
							int time = 60*24*3600;
							Cookie c_sessionid = new Cookie("q_s",uuid);			
							Cookie c_uid = new Cookie("q_uid",AES.encrypt(uid.toString(), StaticInfo.AESKey));
							c_sessionid.setMaxAge(time);									
							c_uid.setMaxAge(time);
							response.addCookie(c_sessionid);								
							response.addCookie(c_uid);
							us.setSessionCookie(uid.toString(), uuid);
						}
						Long userid = (Long) uid;
						
						us.updateLoginInfo(userid,ip);
						session.setAttribute("userid", userid);
						flag = "1";
					}else if(!us.isExists(user_email)){
						message = "该用户尚未注册";
					}
					else{
						message = "注册邮箱和密码不匹配...";
					}
				}
				root.put("redirectURL", url);
				root.put("message",message);
				root.put("flag",flag);
				root.write(response.getWriter());
			}else return ERROR;
		}
		return null;
	}
	
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String userEmail) {
		user_email = userEmail;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String userPassword) {
		user_password = userPassword;
	}
	public String getRemindme() {
		return remindme;
	}
	public void setRemindme(String remindme) {
		this.remindme = remindme;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
