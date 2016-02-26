package action.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import model.AtMessage;
import model.Goods;
import model.User;
import net.sf.json.JSONObject;
import services.message.MessageService;
import services.share.ShareService;
import template.NotesBlockTemplate;
import util.GetAC;
import util.GetTime;
import util.PinYinUtil;
import util.WordsFilter;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserGetDao;
import dao.user.UserSendDao;
import dao.user.UserUpdateDao;

public class UserSend extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userid;
	private Long typeid;
	private Long circleid;
	private Long senderid;
	private String opflag;
	private String sendobject; 
	private String moreinfo;
	private String type;
	private String phone;
	private String qq;
	private String person;
	private String content;
	private Integer savetime;
	private String sendcircle;
	private String to;
	private String show_privacy;
	//goods
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
	//needs
	private String needs;
	private String needstype;
	private String wantprice;
	private String borrowtime;
	private String needstype1;
	private String needstype2;
	//complaint
	private String complaintType;
	
	private String showPri;
	private String ulist;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String goods() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		String [] pic = piclist.split(",");
		//String [] cid = null;
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		/*if(sendcircle!=null && !sendcircle.equals(""))
			cid = sendcircle.split(",");*/
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		Date now = new Date();
		if(!goods.equals("") && GetTime.getTimeDifference(new Date(),ugd.getLastSendTime(userid),"bySecond",15)){
			UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
			uud.updateLastSendTime(userid);
			UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Map atMap = WordsFilter.atFilter(content,userid);
			String goodsTitle = WordsFilter.badWordsFilter(goods),
					goodsContent = WordsFilter.imgFilter(atMap.get("text").toString());
			Goods g = new Goods();
			g.setUserid(userid);
			g.setType(sharetype);
			g.setGoods(goodsTitle);
			g.setIdle_alphabets(PinYinUtil.getPinYin(goodsTitle));
			g.setContent(goodsContent);
			g.setAdd_time(now);
			g.setPrice(price);
			g.setOld_degree(old_degree);
			g.setFirstimg_url(pic[0]);
			g.setShow_privacy(show_privacy);
			g.setSavetime(savetime);
			g.setGoodstype_1(goodstype1);
			g.setGoodstype_2(goodstype2);
			g.setBargain(bargain);
			g.setTotaltime(lendtime);
			g.setGoodslink(goodslink);
			ss.sendIdle(g);
			Long gid = g.getId();
			if(gid != null)
			{
				uad.addOperationTimes("send_active",userid,"sh_user");
				//处理@
				List ulist = (List) atMap.get("uList");
				if(ulist != null && ulist.size() > 0){
					MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
					for(int i = 0; i < ulist.size(); i++){
						Object []u = (Object[]) ulist.get(i);
						AtMessage am = new AtMessage();
						am.setSenderid(userid);
						am.setUserid(Long.parseLong(u[0].toString()));
						am.setSendTime(now);
						am.setContent(goodsContent);
						am.setAtType("goods");
						am.setAtShareId(gid);
						ms.sendAtMessage(am);
					}
				}
				/*if(circleid!=null)
					cid = circleid.toString().split(",");
				/*if(cid != null){
					usd.sendToCircle(userid,gid,"goods",cid);
					uad.addCircleSendNumber(cid);
					uad.addSendNumberInCircle(userid, cid);
					/*	暂不添加立即显示所发便签
					 * NotesBlockTemplate nbt = new NotesBlockTemplate();
					Object []j = {((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")),""};
					root.put("data",java.net.URLEncoder.encode(nbt.goodsNotesList(ugd.getGoodsList(userid, 1, 0,""),j),"UTF-8"));
				}*/
				ss.addIdlePicture(gid, pic);
				if(!piclist.equals(""))
					uad.addHotSpot(gid, "goods", 8+pic.length);
				Integer num = 0;
				root.put("resultNum", num);
				flag = "1";
			}else{
				message = "发布失败,请稍后再试";
			}
		}else{
			message = "发布有点小频繁哦~15秒后再试吧";
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
		return null;
	}
	/*
	public String needs() throws IOException{
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		JSONObject root = new JSONObject();
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		String [] cid = null;
		if(sendcircle!=null && !sendcircle.equals(""))
			cid = sendcircle.split(",");
		if(GetTime.getTimeDifference(new Date(),ugd.getLastSendTime(userid),"bySecond",15)){
			UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
			uud.updateLastSendTime(userid);
			UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
			UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
			Long nid = usd.sendNeeds(userid, needstype, WordsFilter.badWordsFilter(needs), WordsFilter.imgFilter(WordsFilter.badWordsFilter(content)), wantprice, old_degree, phone, qq, person,savetime,show_privacy,needstype1,needstype2,borrowtime);
			if(nid != null)
			{
				uad.addOperationTimes("send_active",userid,"sh_user");
				if(circleid!=null)
					cid = circleid.toString().split(",");
				if(cid != null){
					usd.sendToCircle(userid,nid,"needs",cid);
					uad.addCircleSendNumber(cid);
					uad.addSendNumberInCircle(userid, cid);
					/*NotesBlockTemplate nbt = new NotesBlockTemplate();
					Object []j = {((Long)ServletActionContext.getRequest().getSession().getAttribute("userid")),""};
					root.put("data",java.net.URLEncoder.encode(nbt.needsNotesList(ugd.getNeedsList(userid, 1, 0,""),j),"UTF-8"));
				}
				Integer num = 0;
				root.put("resultNum", num);
				root.put("flag","ok");
			}else{
				root.put("msg",java.net.URLEncoder.encode("发布失败","UTF-8"));
				root.put("flag", "error");
			}
		}else{
			root.put("msg",java.net.URLEncoder.encode("发布有点小频繁哦~","UTF-8"));
			root.put("flag", "error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}*/
	
	
	
	public String complaint() throws IOException{
		UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
		JSONObject root = new JSONObject();
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");			
		if(usd.sendComplaint(userid, senderid, complaintType, content))
		{
			root.put("flag","ok");
		}else{
			root.put("flag", "error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}
	/*
	public String circleInvite() throws IOException{
		UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
		CircleGetDao cgd = (CircleGetDao) GetAC.getAppContext().getBean("CircleGetDao");
		JSONObject root = new JSONObject();
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");			
		Circle c = cgd.getCircleInfo(circleid);
		if(cgd.isAdmin(userid, circleid)){
			String[] uid = ulist.split(",");
			for(int i = 0 ; i < uid.length;i++){
				String req = "邀请我加入<label id ='req_cname_"+circleid+"' style='color:#e68303;'>"+c.getName()+"</label>分享圈";
				Request_Msg rmsg = new Request_Msg();
				rmsg.setAddtime(new Date());
				rmsg.setReq_type("invite");
				rmsg.setReq_content((content!=null&&!content.trim().equals(""))?WordsFilter.imgFilter(content):"无");
				rmsg.setSenderid(userid);
				rmsg.setUserid(Long.parseLong(uid[i]));
				rmsg.setTitle(req);
				rmsg.setReqid(circleid);
				usd.sendRequestMessage(rmsg);
				root.put("flag","ok");
			}
		}else{
			String[] uid = ulist.split(",");
			UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
			User u = ugd.getUser(userid);
			Object []obj = {"<a href='user?page=main&type=share&id="+u.getId()+"'><img src = '"+ServletActionContext.getRequest().getContextPath()+"/"+u.getHead_ico()+"' style='float:left;width:45px;height:45px;border-radius:3px;'/><span style='float:left;margin-left:5px;color:#e68303;'>"+u.getRealname()+"</span></a>向我推荐了<a href='circle?cid="+circleid+"&page=c-feeds&type=share' style='color:#e68303;'>"+c.getName()+"</a>分享圈"};
			for(int i = 0 ; i < uid.length;i++){
				usd.sendNoticeMessage(Long.parseLong(uid[i]),"invite",(content!=null&&!content.trim().equals(""))?WordsFilter.imgFilter(content):"无",obj, 1);
				root.put("flag","ok");
			}
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}
	
	public String applyAdmin() throws IOException{
		UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
		CircleGetDao cgd = (CircleGetDao) GetAC.getAppContext().getBean("CircleGetDao");
		JSONObject root = new JSONObject();
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");			
		Circle c = cgd.getCircleInfo(circleid);
		String req = "申请成为<label id ='req_cname_"+circleid+"' style='color:#e68303;'>"+c.getName()+"</label>(id:"+c.getId()+")分享圈的管理员";
		if(usd.sendAdminRequest(userid, req, "applyCircleAdmin",content,circleid)){
			root.put("flag","ok");	
		}		
		else root.put("flag","error");	
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}*/
	
	public String suggest() throws IOException{
		UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
		JSONObject root = new JSONObject();
		userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");			
		if(usd.sendSuggest(userid,content)){
			root.put("flag","ok");	
		}		
		else root.put("flag","error");	
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}
	
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
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
	public String getOld_degree() {
		return old_degree;
	}
	public void setOld_degree(String oldDegree) {
		old_degree = oldDegree;
	}
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPiclist() {
		return piclist;
	}
	public void setPiclist(String piclist) {
		this.piclist = piclist;
	}
	public Integer getSavetime() {
		return savetime;
	}
	public void setSavetime(Integer savetime) {
		this.savetime = savetime;
	}
	public String getNeedstype() {
		return needstype;
	}
	public void setNeedstype(String needstype) {
		this.needstype = needstype;
	}
	public String getWantprice() {
		return wantprice;
	}
	public void setWantprice(String wantprice) {
		this.wantprice = wantprice;
	}
	public String getNeeds() {
		return needs;
	}
	public void setNeeds(String needs) {
		this.needs = needs;
	}
	public String getSendcircle() {
		return sendcircle;
	}
	public void setSendcircle(String sendcircle) {
		this.sendcircle = sendcircle;
	}
	public Long getSenderid() {
		return senderid;
	}
	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}
	public String getOpflag() {
		return opflag;
	}
	public void setOpflag(String opflag) {
		this.opflag = opflag;
	}
	public String getSendobject() {
		return sendobject;
	}
	public void setSendobject(String sendobject) {
		this.sendobject = sendobject;
	}
	public String getMoreinfo() {
		return moreinfo;
	}
	public void setMoreinfo(String moreinfo) {
		this.moreinfo = moreinfo;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getShow_privacy() {
		return show_privacy;
	}
	public void setShow_privacy(String showPrivacy) {
		show_privacy = showPrivacy;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public Long getCircleid() {
		return circleid;
	}

	public void setCircleid(Long circleid) {
		this.circleid = circleid;
	}

	public String getLendtime() {
		return lendtime;
	}

	public void setLendtime(String lendtime) {
		this.lendtime = lendtime;
	}

	public String getBorrowtime() {
		return borrowtime;
	}

	public void setBorrowtime(String borrowtime) {
		this.borrowtime = borrowtime;
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

	public String getNeedstype1() {
		return needstype1;
	}

	public void setNeedstype1(String needstype1) {
		this.needstype1 = needstype1;
	}

	public String getNeedstype2() {
		return needstype2;
	}

	public void setNeedstype2(String needstype2) {
		this.needstype2 = needstype2;
	}

	public String getShowPri() {
		return showPri;
	}

	public void setShowPri(String showPri) {
		this.showPri = showPri;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUlist() {
		return ulist;
	}

	public void setUlist(String ulist) {
		this.ulist = ulist;
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
	
}
