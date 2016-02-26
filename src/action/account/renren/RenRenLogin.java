package action.account.renren;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NoticeMessage;
import model.User;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import services.account.AccountService;
import services.common.CommonService;
import services.message.MessageService;
import services.user.UserService;
import util.AES;
import util.AppConfig;
import util.GetAC;
import util.PinYinUtil;
import util.SHA;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.HttpURLUtils;
import dao.user.UserRegisterDao;

public class RenRenLogin extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String login() throws NoSuchAlgorithmException{
		String code = request.getParameter("code");
		if (code == null || code.length() == 0) {
			//缺乏有效参数，跳转到登录页去
			message = "缺乏有效code参数";
			return ERROR;
		}
		//到人人网的OAuth 2.0的token endpoint用code换取access token
		String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/token";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id", AppConfig.RenRenApiKey);
		parameters.put("client_secret", AppConfig.RenRenApiSecret);
		parameters.put("redirect_uri", "http://"+request.getServerName()+"/renren/login");//这个redirect_uri要和之前传给authorization endpoint的值一样
		parameters.put("grant_type", "authorization_code");
		parameters.put("code", code);
		String tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint, parameters);
		JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
		if (tokenJson != null) {
			String accessToken = (String) tokenJson.get("access_token");
			Long expiresIn = (Long) tokenJson.get("expires_in");//距离过期时的时间段（秒数）
			long currentTime = System.currentTimeMillis() / 1000;
			long expiresTime = currentTime + expiresIn;//即将过期的时间点（秒数）
			request.getSession().setAttribute("expiresTime", expiresTime);
			//调用人人网API获得用户信息
			RenrenApiClient apiClient = RenrenApiClient.getInstance();
			try{
				int rrUid = apiClient.getUserService().getLoggedInUser(new AccessToken(accessToken));
				JSONArray userInfo = apiClient.getUserService().getInfo(String.valueOf(rrUid), "name,sex,tinyurl,mainurl,university_history",new AccessToken(accessToken));
				if (userInfo != null && userInfo.size() > 0) {
					JSONObject currentUser = (JSONObject) userInfo.get(0);
					if (currentUser != null) {
						AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
						String name = currentUser.get("name").toString();
						String sex =  currentUser.get("sex").toString();//0女,1男
						String mainurl = currentUser.get("mainurl").toString();
						String tinyurl = currentUser.get("tinyurl").toString();//50*50
						String school = "",dep = "",grade = "";
						JSONArray uh = currentUser.get("university_history")!=null?(JSONArray) currentUser.get("university_history"):null;
						if(uh!=null&&uh.size()>0){
							JSONObject schoolInfo = (JSONObject) uh.get(uh.size()-1);
							school = schoolInfo.get("name").toString().trim();
							dep = schoolInfo.get("department").toString().trim();
							grade = schoolInfo.get("year").toString().trim();
						}
						CommonService xs = (CommonService) GetAC.getAppContext().getBean("XMLService");
						if(!xs.isSchoolExists(school)){
							message = "因为圈易物还处在公测阶段,目前只开放<label style='color:#e68303;'>北航</label>注册~不过别急,马上就会开放你所在的学校哦^_^";
							return ERROR;
						}
						//判断帐号关联表里有没有现成的关联
						Object obj = as.getRenRenIdMapping(rrUid);
						Long uid;
						if (obj == null) {
							//在帐号关联表里没有记录，用户是第一次来；为这个用户创建一个User对象
							Date d = new Date();
							UserRegisterDao urd = (UserRegisterDao) GetAC.getAppContext().getBean("UserRegisterDao");
							String password = UUID.randomUUID().toString();
							String n = name.length()>6?name.trim().substring(0,6):name.trim();
							//录入用户信息
							User user = new User();
							user.setEmail(StaticInfo.renren+"Login-"+UUID.randomUUID().toString());
							//自动拼装一个username并随即生成一个password；实际实现时，这里应该保证拼装出来的username不与其它帐号冲突
							user.setPassword(password);
							user.setRealname(n);
							user.setNickname(PinYinUtil.getPinYin(n));
							user.setNickhead(PinYinUtil.getPinYinHeadChar(n));
							user.setHead_ico(tinyurl);
							user.setHead_ico_big(mainurl);
							user.setSex(sex.equals("1")?"男":"女");
							user.setSchool(school);
							user.setDepartment(departmentMapping(dep,school));
							user.setHs_year(gradeMapping(grade));
							user.setRegister_ip(request.getRemoteAddr());
							user.setAdd_time(d);
							user.setLastlogin_time(d);
							//保存到用户表
							uid = urd.InputInfoByAPI(user);
							//保存到帐号关联表
							if(uid!=null){
								as.setRenRenIdMapping(rrUid, uid);
								//系统通知
								NoticeMessage nm = new NoticeMessage();
								nm.setContent("欢迎来到圈易物!在这里你可以发布自己的闲置物品，将他们卖，借，送给需要的人，在互助中结识新朋友。那么就赶快去发布自己的第一个闲置物品吧!");
								nm.setIsShareType("0");
								nm.setNoticeType("system");
								nm.setSendTime(d);
								nm.setUserid(uid);
								nm.setSenderid(StaticInfo.adminId);
								MessageService ms= (MessageService) GetAC.getAppContext().getBean("MessageService");
								ms.sendNoticeMessage(nm);
							}
						}
						else{
							//用户不是第一次来了，已经在帐号关联表里有了
							uid = Long.parseLong(obj.toString());
							as.bindRenRen(uid);
						}
						UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
						//将用户身份信息保存在cookie
						String uuid = UUID.randomUUID().toString();
						int time = 60*24*3600;
						Cookie c_sessionid = new Cookie("q_s",uuid);			
						Cookie c_uid = new Cookie("q_uid",AES.encrypt(uid.toString(), StaticInfo.AESKey));
						c_sessionid.setMaxAge(time);									
						c_uid.setMaxAge(time);
						c_uid.setPath("/");
						c_sessionid.setPath("/");
						response.addCookie(c_sessionid);								
						response.addCookie(c_uid);
						us.setSessionCookie(uid.toString(), uuid);
						//更新登录信息
						String ip = request.getRemoteAddr();
						us.updateLoginInfo(uid, ip);
						request.getSession().setAttribute("userid", uid);
					}
				}else {message = "无该用户";return ERROR;};
			}
			catch(Exception e){
				String msg = "人人登陆失败,错误原因:<br>";
				message = msg+e.getMessage();
				return ERROR;
			}
		}else {message = "返回参数错误";return ERROR;};
		//已登录，跳转到个人主页
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private static String departmentMapping(String renrenDep,String school){
		if(school.equals("北京航空航天大学")){
			Map<String,String> BUAAMap = new HashMap<String,String>();
			BUAAMap.put("材料科学与工程学院1系","材料科学与工程学院1系");
			BUAAMap.put("电子信息工程学院2系","电子信息工程学院2系");
			BUAAMap.put("自动化科学与电气工程学院3系","自动化科学与电气工程学院3系");
			BUAAMap.put("能源与动力工程学院4系","能源与动力工程学院4系");
			BUAAMap.put("航空科学与工程学院5系","航空科学与工程学院5系");
			BUAAMap.put("计算机","计算机学院6系");
			BUAAMap.put("机械工程及自动化学院7系","机械工程及自动化学院7系");
			BUAAMap.put("经济管理学院8系","经济管理学院8系");
			BUAAMap.put("理学院9系","数学与系统科学学院9系");
			BUAAMap.put("生物工程系10系","生物工程学院10系");
			BUAAMap.put("人文学院","人文社会科学学院11系");
			BUAAMap.put("人文社会科学学院","人文社科实验班29系");
			BUAAMap.put("外语系12系","外国语学院12系");
			BUAAMap.put("交通科学与工程学院13系","交通科学与工程学院13系");
			BUAAMap.put("工程系统工程系14系","可靠性与系统工程学院14系");
			BUAAMap.put("宇航学院15系","宇航学院15系");
			BUAAMap.put("飞行学院","飞行学院16系");
			BUAAMap.put("仪器科学与光电工程学院","仪器科学与光电工程学院17系");
			BUAAMap.put("物理科学与核能工程","物理学院19系");
			BUAAMap.put("法学院20系","法学院20系");
			BUAAMap.put("软件学院21系","软件学院21系");
			BUAAMap.put("高等工程学院23系","高等工程学院23系");
			BUAAMap.put("中法工程师学院","中法工程师学院24系");
			BUAAMap.put("新媒体艺术系","新媒体艺术学院26系");
			BUAAMap.put("化学与环境学院27系","化学与环境学院27系");
			BUAAMap.put("土木工程系","土木工程学院");
			BUAAMap.put("HND","国际学院");
			BUAAMap.put("工业与制造系统工程系","工业与制造系统工程系");
			BUAAMap.put("北海学院","北海学院");
			BUAAMap.put("中国语言文学系","中国语言文学系");
			BUAAMap.put("继续教育学院","继续教育学院");
			return BUAAMap.get(renrenDep)!=null?BUAAMap.get(renrenDep).toString():"";
		}
		return "";
	}
	
	private static String gradeMapping(String rr_grade){
		String localGrade = "";
		int g = Integer.parseInt(rr_grade);
		if(g > 2000){
			for(int i = 2001 ; i <= g; i++)
			{	int y = i-2000;
				if(y < 10)localGrade = "0"+String.valueOf(y)+"级";
				else localGrade = String.valueOf(y)+"级";
			}	
		}
		return localGrade.trim();
	}
	
}
