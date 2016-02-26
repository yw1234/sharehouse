package action.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import services.common.CommonService;

import org.apache.struts2.ServletActionContext;
import services.user.UserService;
import util.EmailUtil;
import util.GetAC;
import util.GetTime;
import util.SHA;
import util.StaticInfo;
import model.Email;
import model.OptionsKey;
import com.opensymphony.xwork2.ActionSupport;

public class Reset extends ActionSupport{

	private String step;
	private String key;
	private String email;
	private String password;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String reset(){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String message = "",flag = "0";
		step = (step != null)?step:"0";
		try{
			if(step.equals("1")){
				UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
				if(email!=null && !email.equals("")){
					Long uid = us.getUserId(email);
					if(uid != null){
						Date date = new Date();
						CommonService cs = (CommonService) GetAC.getAppContext().getBean("XMLService");
						cs.deleteOptKey(uid, "reset");
						String uuid = UUID.randomUUID().toString();
						OptionsKey rp = new OptionsKey();
						rp.setKey(uuid);
						rp.setOption("reset");
						rp.setEmail(email);
						rp.setAdd_time(date);
						rp.setUid(uid);
						if(cs.addOptKey(rp)){
							String url = StaticInfo.webURL+"/pwd_reset?step=2&key="+uuid;
							String from = "noreply@quan15.com";
							String []to = {email};
							String subject = "圈易物密码重置";
							Email email = new Email();
							email.setFrom(from);
							email.setTo(to);
							email.setSendDate(date);
							email.setSubject(subject);
							EmailUtil emailUtil = (EmailUtil) GetAC.getAppContext().getBean("EmailUtil");
							Map params = new HashMap();
							params.put("reset_url", url);
							params.put("fileName","reset.ftl");
							emailUtil.send(email,params);
							flag = "1";
							message = "密码重置邮件已经发送到了你的邮箱中,请及时查看";
						}else{
							message = "邮件发送失败,请稍后再试";
						}
					}else{
						message = "该账号不存在哦~";
					}
				}
				root.put("flag", flag);
				root.put("message",message);
				root.write(response.getWriter());
				return null;
			}else if(step.equals("2")){
				if(key==null || key.equals("")){
					return ERROR;
				}else{
					CommonService cs = (CommonService) GetAC.getAppContext().getBean("XMLService");
					Map map = cs.getResetUid(key);
					if(map!=null){
						Date d = (Date) map.get("add_time");
						if(!GetTime.getTimeDifference(new Date(), d, "byDay", 2)){
							request.getSession().setAttribute("reset", map.get("uid"));
							flag = "1";
						}else {
							message = "该连接已经超过了有效期~ 请重新发送重置邮件";
						}
						cs.deleteOptKey(key);
					}else{
						message = "该链接为无效连接,请重新发送重置邮件";
					}
				}
			}else if(step.equals("3")){
				Object uid = request.getSession().getAttribute("reset");
				if(uid != null){
					UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
					Map<String, String> params = new HashMap<String, String>();
					params.put("password", SHA.getSHA(password));
					if(us.updateUserInfo(Long.parseLong(uid.toString()), "password", params)){
						flag = "1";
					}else message = "密码重置失败,服务器错误";
				}else message = "连接超时,请重新发送重置邮件";
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		request.setAttribute("flag", flag);
		request.setAttribute("message", message);
		return SUCCESS;
	}
	
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
