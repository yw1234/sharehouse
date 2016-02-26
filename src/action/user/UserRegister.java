package action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Email;
import model.NoticeMessage;
import model.OptionsKey;
import model.RequestMessage;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.common.CommonService;
import services.message.MessageService;
import services.user.UserService;
import template.UserBlockTemplate;
import util.EmailUtil;
import util.GetAC;
import util.StaticInfo;
import util.WordsFilter;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserRegisterDao;
import dao.user.UserUpdateDao;

public class UserRegister extends ActionSupport{
	
private static final long serialVersionUID = 1L;
	
	private Long id;
	private String uidlist;
	private String email;
	private String password;
	private String name;
	private String sex;
	private String school;
	private String checkcode;
	private String department;
	private String grade;
	private String edu;
	private String key;
	
	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = request.getSession();
	
	
	public String email_validate() throws IOException
	{
		UserRegisterDao urd = (UserRegisterDao) GetAC.getAppContext().getBean("UserRegisterDao");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String message = "";
		Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		if(!emailer.matcher(email).matches()){
			session.setAttribute("email",false);
			message = java.net.URLEncoder.encode("邮箱格式不合法","UTF-8");
		}
		else if(!urd.IsUniqueEmail(email.trim()))
		{
			session.setAttribute("email",false);
			message = java.net.URLEncoder.encode("这个邮箱已经被注册了","UTF-8");
		}
		else 
		{
			session.setAttribute("email", true);
			message = java.net.URLEncoder.encode("ok","UTF-8");
		}
		out.println(message);
		urd = null;
		return null;
	}
	
	
	public String name_validate() throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String message = "";
		String []bad_name = {"草泥马","共产党","死","勒个去","共党","逼","肏","傻逼","操你妈","垃","屄","屌","鸡巴","操你","乳房","你妹","我操","屎","尿","呵","雅蠛蝶","姓名"};
		boolean NoBadName = true;
		for(int i = 0 ; i < bad_name.length ; i++)
		{
			if(name.contains(bad_name[i]))
				NoBadName = false;
		}
		if(!NoBadName)
		{
			session.setAttribute("name", false);
			message = java.net.URLEncoder.encode("有些词...你懂得:)","UTF-8");
			out.println(message);
			return null;
		}
		else
		{
			session.setAttribute("name", true);
			message = java.net.URLEncoder.encode("ok","UTF-8");
			out.println(message);
			return null;
		}
	}
	
	public String checkcode_validate() throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String message = "";
		if(checkcode.toLowerCase().equals(session.getAttribute("CheckCode").toString().toLowerCase()))
		{ 
			session.setAttribute("checkcode", true);
			message = java.net.URLEncoder.encode("ok","UTF-8");
			out.println(message);
		}
		else
		{
			session.setAttribute("checkcode", false);
			message = java.net.URLEncoder.encode("输错了额..","UTF-8");
			out.println(message);
		}
		return null;
	}
	
	
	public String main_validate() throws IOException, NoSuchAlgorithmException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String message = "";
		String flag = "0";
		JSONObject root = new JSONObject();
		Long userid = null;
		if((Boolean)session.getAttribute("email") && (Boolean)session.getAttribute("name") && (Boolean)session.getAttribute("checkcode"))
		{
			session.removeAttribute("email");
			session.removeAttribute("name");
			session.removeAttribute("checkcode");
			UserRegisterDao urd = (UserRegisterDao) GetAC.getAppContext().getBean("UserRegisterDao");
			String ip = ServletActionContext.getRequest().getRemoteAddr();
			userid = urd.InputInfo(email, password, name, sex,"",ip); 
			if(userid != null)
			{
				//系统通知
				NoticeMessage nm = new NoticeMessage();
				nm.setContent("欢迎来到圈易物!在这里你可以发布自己的闲置物品，将他们卖，借，送给需要的人，在互助中结识新朋友。那么就赶快去发布自己的第一个闲置物品吧!");
				nm.setIsShareType("0");
				nm.setNoticeType("system");
				nm.setSendTime(new Date());
				nm.setUserid(userid);
				nm.setSenderid(StaticInfo.adminId);
				MessageService ms= (MessageService) GetAC.getAppContext().getBean("MessageService");
				ms.sendNoticeMessage(nm);
				//id存入session
				session.setAttribute("userid", userid);
				flag = "1";
			}
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
		return null;
	}
	
	public String commonUser() throws IOException{
		String flag = "0";
		response.setContentType("text/html");
		JSONObject root = new JSONObject();
		try{
			Long userid = (Long) session.getAttribute("userid");
			UserRegisterDao urd = (UserRegisterDao) GetAC.getAppContext().getBean("UserRegisterDao");		
			Map<String, String> map= new HashMap<String, String>();
			map.put("school",school);
			map.put("department",department);
			map.put("grade",grade);
			map.put("edu",edu);
			List ulist = urd.getCommonUser(userid,map,45,0);
			UserBlockTemplate ubt = new UserBlockTemplate();
			String data = java.net.URLEncoder.encode(ubt.registerUserList(ulist), "UTF-8");
			root.put("data", data);
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.write(response.getWriter());
		return null;
	}
	
	/**
	 * 注册完成
	 * @return
	 * @throws IOException
	 */
	public String complete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String message = "";
		String flag = "0";
		boolean status = true;
		JSONObject root = new JSONObject();
		Long userid = (Long) session.getAttribute("userid");
		if(school == null || school.equals("")){
			message = "还没有选择所在学校哦~";
			status = false;
		}
		if(status && (edu == null || edu.equals(""))){
			message = "学历?";
			status = false;
		}
		if(status && (department == null || department.equals(""))){
			message = "还没有选择所在的院系~";
			status = false;
		}
		if(status && (grade == null || grade.equals(""))){
			message = "你的入学年级是?";
			status = false;
		}
		//第三方登陆
		UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
		Map u_info = us.getUserInfo(userid, "registerByAPI");
		if(status && u_info.get("registerByAPI") != null && u_info.get("registerByAPI").toString().equals("1")){
			UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
			if(email!=null && !email.trim().equals("") && password!=null && !password.trim().equals(""))
			{
				if(password.length() < 6 || password.length() > 20){
					message = "密码长度要在6-20位之间";
					status = false;
				}
				if(!uud.updateLoginInfo(userid, email, password)){
					message = "这个邮箱已被注册过咯~";
					status = false;
				}
			}else {
				message = "请完善账号信息";
				status = false;
			}	
		}
		if(status){
			if(!uidlist.equals("") && uidlist != null && uidlist != ""){
				String[] uid = uidlist.split(",");
				MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
				for(int i = 0 ; i < uid.length;i++){
					RequestMessage pm = new RequestMessage();
					pm.setUserid(Long.parseLong(uid[i]));
					pm.setSenderid(userid);
					pm.setSendTime(new Date());
					pm.setContent("请求加为好友");
					pm.setReqMessage("");
					pm.setReqType("addFriends");
					ms.sendRequestMessage(pm);
				}
			}
			Map<String,String> update_info = new HashMap<String,String>();
			update_info.put("isNewUser","0");
			update_info.put("school",school);
			update_info.put("educational",edu);
			update_info.put("hs_year",grade);
			update_info.put("department",department);
			if(us.updateUserInfo(userid, "isNewUser,school,educational,hs_year,department", update_info)){
				flag = "1";
				root.put("redirectURL", StaticInfo.URL+"/home");
			}else message = "操作失败,请稍后再试";
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
		return null;
	}
	
	/**
	 * 注册-完善信息界面
	 * @return
	 */
	public String reg_complete(){
		try{
			Long userid = (Long) session.getAttribute("userid");
			UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
			Map map = us.getUserInfo(userid, "realname,head_ico_big,sex,school,department,educational,hs_year,isNewUser,registerByAPI");
			request.setAttribute("user",map);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUidlist() {
		return uidlist;
	}
	public void setUidlist(String uidlist) {
		this.uidlist = uidlist;
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
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
	
}
