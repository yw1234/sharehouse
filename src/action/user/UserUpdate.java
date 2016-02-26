package action.user;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ActionContextCleanUp;

import services.message.MessageService;
import services.share.ShareService;
import util.GetAC;
import util.PinYinUtil;
import util.SHA;
import util.WordsFilter;
import model.AtMessage;
import model.Goods;
import model.User;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserGetDao;
import dao.user.UserRegisterDao;
import dao.user.UserRemoveDao;
import dao.user.UserUpdateDao;

public class UserUpdate extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//userInfo
	private Long id;
	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	private String p;
	private String realname;
	private String nickname;
	private String province;
	private String city;
	private String school;
	private String birthday;
	private String constellation;
	private String xx;
	private String marry;
	private String blood;
	private String home;
	private String qq;
	private String phone;
	private String show_email;
	private String introduce;
	private String department;
	private String grade;
	private String edu;
	
	//privacy
	private String privacy_myPage;
	private String privacy_myCircleList;
	private String privacy_myUserList;
	private String privacy_myMsgList;
	private String pi_set;
	private String privacy_pShowUser;
	private String privacy_pShowCircle;
	private String show_pi_clist;
	private String show_pi_ulist;
	private String flag;
	
	//obj
	private Long typeid;
	private String type;
	private String content;
	private String show_privacy;
	private String goods;
	private String sharetype;
	private Integer price;
	private String old_degree;
	private String piclist;
	private String lendtime;
	private String goodstype1;
	private String goodstype2;
	private String goodslink;
	private String bargain;
	//pwd
	private String prev_pwd;
	private String new_pwd;
	private String re_pwd;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	private Long userid = (Long)ServletActionContext.getRequest().getSession().getAttribute("userid");
	
	public String info(){
		UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		User u = ugd.getUser(userid);
		try{
			if(x!=null && y!=null && w!=null && h!=null && p!=null)
			{
				cutimage(u.getId());
			}
			u.setRealname(realname);
			u.setNickname(PinYinUtil.getPinYin(realname));
			u.setNickhead(PinYinUtil.getPinYinHeadChar(realname));
			u.setSchool(school);
			u.setProvince(province);
			u.setCity(city);
			u.setBirthday(birthday);
			u.setBlood(blood);
			u.setConstellation(constellation);
			u.setMarry(marry);
			u.setIntroduce(introduce);
			u.setHome(home);
			u.setSx(xx);
			u.setEducational(edu);
			//avatar
			if(p!= null && !p.trim().equals(""))
				u.setHead_ico(p.trim());
			//院系
			if(department!=null && !department.equals(""))
				u.setDepartment(department);
			//年级
			if(grade!=null && !grade.equals(""))
				u.setHs_year(grade);
			if(uud.updateUserInfo(u)){
				response.getWriter().println("ok");
			}else return ERROR;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return null;
	}
	
	
	public void cutimage(Long userid) throws IOException
	{
		FileInputStream is = null;
		ImageInputStream iis = null;
		String path = ServletActionContext.getServletContext().getRealPath(p);
		int index = path.lastIndexOf(".");
		String endname = path.substring(index+1).trim();
		try { 
			Iterator <ImageReader> it = ImageIO.getImageReadersByFormatName(endname); 
			ImageReader reader = (ImageReader) it.next(); 
			is = new FileInputStream(path.trim()); 
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam(); 
			Rectangle rect = new Rectangle(x, y, w, h);  
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0 ,param); 
			ImageIO.write(bi,endname, new File(path.trim())); 
		} 
		finally{
		if (is != null)
		is.close() ; 
		if (iis != null)
		iis.close(); 
		} 
	}
	
	public String privacy(){
		UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
		UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		User u = ugd.getUser(userid);
		try{
			u.setNickname(nickname);
			u.setPrivacy_myCircleList(privacy_myCircleList);
			u.setPrivacy_myPage(privacy_myPage);
			u.setPrivacy_myUserList(privacy_myUserList);
			u.setPrivacy_myMsgList(privacy_myMsgList);
			u.setPrivacy_pInfo(pi_set);
			u.setPhone(phone);
			u.setQq(qq);
			u.setShow_email(show_email);
			u.setPrivacy_pShowUser(privacy_pShowUser);
			u.setPrivacy_pShowCircle(privacy_pShowCircle);
			if(privacy_pShowCircle!=null){uad.addShowPriCircle(((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")), show_pi_clist);}else{uad.addShowPriCircle(((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")),"");}
			if(privacy_pShowUser!=null){uad.addShowPriUser(((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")), show_pi_ulist);}else{uad.addShowPriUser(((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")),"");}
			if(uud.updateUserPrivacy(u)){
				response.getWriter().println("ok");
			}else return ERROR;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return null;
		
	}
	
	public String concernedFriends() throws IOException{
		UserRemoveDao urd = (UserRemoveDao) GetAC.getAppContext().getBean("UserRemoveDao");
		if(urd.removeConcernedFriends(userid, id)){
			response.getWriter().println("ok");
		}else{
			response.getWriter().println("error");
		}
		return null;
	}
	
	public String blacklist() throws IOException{
		UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
		if(uud.updateBlackList(userid, id, flag)){
			UserRemoveDao urd = (UserRemoveDao) GetAC.getAppContext().getBean("UserRemoveDao");
			urd.removeConcernedFriends(userid, id);
			response.getWriter().println("ok");
		}else{
			response.getWriter().println("error");
		}
		return null;
	}
	
	public String isShared() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
		ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
		if(ss.isIdleExists(userid, typeid)){
			if(uud.updateIsShared(typeid, type)){
				flag = "1";
			}else{
				message = "操作失败,请稍后再试";
			}
		}
		else message = "不能更改他人分享";
		root.put("flag",flag);
		root.put("message",message);
		root.write(response.getWriter());
		return null;
	}
	
	public String password() throws IOException, NoSuchAlgorithmException{
		String message = "";
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		if(ugd.getPassword(userid).equals(SHA.getSHA(prev_pwd))){
			if(re_pwd.equals(new_pwd)){
				UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
				if(uud.updatePassword(userid, SHA.getSHA(new_pwd))){
					message = "ok";
				}else{
					message="保存失败";
				}
			}else message="两次输入的密码不一致";
		}else{
			message="原密码错误";
		}
		response.getWriter().println(java.net.URLEncoder.encode(message,"UTF-8"));
		return null;
	}
	
	public void goods() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
		Map atMap = WordsFilter.atFilter(content,userid);
		String goodsTitle = WordsFilter.badWordsFilter(goods),
				goodsContent = WordsFilter.imgFilter(atMap.get("text").toString());
		if(ss.isIdleExists(userid, id)&&!goods.equals("")){
			String [] pic = piclist.split(",");
			Goods g = (Goods) ss.getBaseDao().get(Goods.class,id);
			g.setGoods(goodsTitle);
			g.setContent(goodsContent);
			g.setPrice(price);
			g.setOld_degree(old_degree);
			g.setShow_privacy(show_privacy);
			g.setGoodstype_1(goodstype1);
			g.setGoodstype_2(goodstype2);
			g.setBargain(bargain);
			g.setTotaltime(lendtime);
			g.setGoodslink(goodslink);
			if(ss.updateGoods(g)){
				//处理@
				List ulist = (List) atMap.get("uList");
				if(ulist != null && ulist.size() > 0){
					MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
					for(int i = 0; i < ulist.size(); i++){
						Object []u = (Object[]) ulist.get(i);
						AtMessage am = new AtMessage();
						am.setSenderid(userid);
						am.setUserid(Long.parseLong(u[0].toString()));
						am.setSendTime(new Date());
						am.setContent(goodsContent);
						am.setAtType("goods");
						am.setAtShareId(g.getId());
						ms.sendAtMessage(am);
					}
				}
				ss.addIdlePicture(id, pic);
				flag = "1";
			}else{message = "更新失败,请稍后再试";}
		}
		else message = "不能更改他人发布信息";
		root.put("flag",flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getW() {
		return w;
	}
	public void setW(Integer w) {
		this.w = w;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getXx() {
		return xx;
	}
	public void setXx(String xx) {
		this.xx = xx;
	}
	public String getMarry() {
		return marry;
	}
	public void setMarry(String marry) {
		this.marry = marry;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getPrivacy_myPage() {
		return privacy_myPage;
	}
	public void setPrivacy_myPage(String privacyMyPage) {
		privacy_myPage = privacyMyPage;
	}
	public String getPrivacy_myCircleList() {
		return privacy_myCircleList;
	}
	public void setPrivacy_myCircleList(String privacyMyCircleList) {
		privacy_myCircleList = privacyMyCircleList;
	}
	public String getPrivacy_myUserList() {
		return privacy_myUserList;
	}
	public void setPrivacy_myUserList(String privacyMyUserList) {
		privacy_myUserList = privacyMyUserList;
	}
	public String getPi_set() {
		return pi_set;
	}
	public void setPi_set(String piSet) {
		pi_set = piSet;
	}
	public String getPrivacy_pShowUser() {
		return privacy_pShowUser;
	}
	public void setPrivacy_pShowUser(String privacyPShowUser) {
		privacy_pShowUser = privacyPShowUser;
	}
	public String getPrivacy_pShowCircle() {
		return privacy_pShowCircle;
	}
	public void setPrivacy_pShowCircle(String privacyPShowCircle) {
		privacy_pShowCircle = privacyPShowCircle;
	}
	public String getPrivacy_myMsgList() {
		return privacy_myMsgList;
	}
	public void setPrivacy_myMsgList(String privacyMyMsgList) {
		privacy_myMsgList = privacyMyMsgList;
	}
	public String getShow_pi_clist() {
		return show_pi_clist;
	}
	public void setShow_pi_clist(String showPiClist) {
		show_pi_clist = showPiClist;
	}
	public String getShow_pi_ulist() {
		return show_pi_ulist;
	}
	public void setShow_pi_ulist(String showPiUlist) {
		show_pi_ulist = showPiUlist;
	}
	public String getShow_email() {
		return show_email;
	}
	public void setShow_email(String showEmail) {
		show_email = showEmail;
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
	public String getPrev_pwd() {
		return prev_pwd;
	}
	public void setPrev_pwd(String prevPwd) {
		prev_pwd = prevPwd;
	}
	public String getNew_pwd() {
		return new_pwd;
	}
	public void setNew_pwd(String newPwd) {
		new_pwd = newPwd;
	}
	public String getRe_pwd() {
		return re_pwd;
	}
	public void setRe_pwd(String rePwd) {
		re_pwd = rePwd;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getShow_privacy() {
		return show_privacy;
	}


	public void setShow_privacy(String show_privacy) {
		this.show_privacy = show_privacy;
	}


	public String getGoods() {
		return goods;
	}


	public void setGoods(String goods) {
		this.goods = goods;
	}


	public String getSharetype() {
		return sharetype;
	}


	public void setSharetype(String sharetype) {
		this.sharetype = sharetype;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public String getOld_degree() {
		return old_degree;
	}


	public void setOld_degree(String old_degree) {
		this.old_degree = old_degree;
	}


	public String getPiclist() {
		return piclist;
	}


	public void setPiclist(String piclist) {
		this.piclist = piclist;
	}


	public String getLendtime() {
		return lendtime;
	}


	public void setLendtime(String lendtime) {
		this.lendtime = lendtime;
	}


	public String getGoodstype1() {
		return goodstype1;
	}


	public void setGoodstype1(String goodstype1) {
		this.goodstype1 = goodstype1;
	}


	public String getGoodstype2() {
		return goodstype2;
	}


	public void setGoodstype2(String goodstype2) {
		this.goodstype2 = goodstype2;
	}


	public String getGoodslink() {
		return goodslink;
	}


	public void setGoodslink(String goodslink) {
		this.goodslink = goodslink;
	}


	public String getBargain() {
		return bargain;
	}


	public void setBargain(String bargain) {
		this.bargain = bargain;
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
	
	
}
