package action.message;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import model.Email;
import model.IdleOrder;
import model.NoticeMessage;
import model.OptionsKey;
import model.PrivateMessage;
import model.RequestMessage;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.common.CommonService;
import services.message.MessageService;
import services.share.ShareService;
import services.user.UserService;
import template.MessageBlockTemplate;
import util.AddArray;
import util.EmailUtil;
import util.GetAC;
import util.StaticInfo;
import util.WordsFilter;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserDeleteDao;
import dao.user.UserUpdateDao;

public class RequestMessageAction extends ActionSupport {
	
	private Long id;
	private Long userid;
	private String content;
	private Date sendTime;
	private String reqMessage;
	private String reqType;
	private String reqShare;
	private Long reqShareId;
	private String reqShareMethod;
	private String tranType;				//交易方式
	private String message= "";
	private Integer pageno;
	private Integer msgSize = StaticInfo.msgNumber;
	
	//成交信息
	private Integer price;
	private String phone;
	private String dorm;
	private String showContact;
	private String deliveryLocation;
	private String pickupLocation;
	private String shareFlag;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	private Long senderid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
	
	/**
	 * 发请求
	 * @throws IOException
	 */
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map.put("reqType",reqType);
		map.put("reqShareId",reqShareId!=null?reqShareId.toString():null);
		Date date = new Date();
		phone = phone!=null?phone:"";
		String reqMsg = WordsFilter.imgFilter(reqMessage);
		if(!phone.equals("") && showContact != null && showContact.equals("1")){
			reqMsg = !reqMsg.equals("")?" | "+reqMsg:"";
			reqMsg = "我的联系电话 - "+phone+ reqMsg;
		}else{
			reqMsg = !reqMsg.equals("")?reqMsg:"未填写";
		}
		if(!userid.equals(senderid)){
			/**
			 * 活动专用拦截器
			 */
			if(reqType.equals("goods") && tranType!=null && !tranType.equals("自主交易") && !canDelivery(userid)){
				message = "目测此交易不符合送货条件哦~ <br>主动和对方联系一下吧";
				root.put("flag", flag);
				root.put("message",message);
				root.write(response.getWriter());	
				return;
			}
			if(!ms.isRequested(userid,senderid,map))
			{
				RequestMessage pm = new RequestMessage();
				pm.setUserid(userid);
				pm.setSenderid(senderid);
				pm.setSendTime(date);
				pm.setContent(createReqContent());
				pm.setReqMessage(reqMsg);
				pm.setReqShare(reqShare);
				pm.setReqShareId(reqShareId);
				pm.setReqShareMethod(reqShareMethod);
				pm.setReqType(reqType);
				pm.setReqDelivery(tranType);
				if(ms.sendRequestMessage(pm)){
					flag = "1";
					if(reqType!=null && reqType.equals("goods")){
						UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
						phone = phone!=null?phone:"";
						Map params = new HashMap();
						params.put("phone", phone!=null?phone:"");
						params.put("dorm", dorm!=null?dorm:"");
						us.updateUserInfo(senderid, "phone,dorm", params);
						UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
						uad.addOperationTimes("requiredtimes",reqShareId,"goods");
					}
				}else message = "请求失败";
			}else{
				message = "已经发送过请求了~";
			}
		}else{
			message = "给自己发分享请求是神马情况?";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());	
	}
	
	/**
	 * 删除请求
	 * @throws IOException
	 */
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			if(ms.isRequestMessageExists(senderid, id)){
				if(ms.deleteRequestMessage(id)){
					flag = "1";
				}else message = "删除失败,请稍后再试";
			}else message = "您无权删除此条请求";
		}
		catch(Exception e){
			e.printStackTrace();
			message = "系统出现问题,操作失败,请稍后再试";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());	
	}
	
	/**
	 * 接受请求
	 * @throws IOException
	 */
	public void accept() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			//是否处理完毕
			if(!ms.isDealComplete(id,senderid,userid)){
				RequestMessage req = (RequestMessage) ms.getBaseDao().get(RequestMessage.class,id);
				reqType = req.getReqType();
				reqShareMethod = req.getReqShareMethod();
				reqShareId = req.getReqShareId();
				reqShare = req.getReqShare();
				tranType = req.getReqDelivery();
				String addMsg = WordsFilter.imgFilter(content);
				Date date = new Date();
				if(phone!=null && !phone.equals("")){
					//显示联系电话
					if(showContact != null && showContact.equals("1")){
						addMsg = "联系电话 - "+phone+" | "+addMsg;
					}
					//更新联系方式
					UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
					Map params = new HashMap();
					params.put("phone", phone);
					us.updateUserInfo(senderid, "phone", params);
				}
				//生成通知
				NoticeMessage nm = new NoticeMessage();
				nm.setContent(createNoticeContent());
				nm.setUserid(userid);
				nm.setSenderid(senderid);
				nm.setSendTime(date);
				nm.setAdditionalMessage(addMsg);
				nm.setNoticeType(reqType);
				nm.setNoticeShare(reqShare);
				nm.setNoticeShareId(reqShareId);
				//请求类型:加好友
				if(reqType.equals("addFriends")){
					UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
					if(uad.addFriends(userid, senderid)){
						//添加好友人数
						uad.addOperationTimes("friend_number", userid, "sh_user");
						uad.addOperationTimes("friend_number", senderid, "sh_user");
						//删除该条请求
						ms.deleteRequestMessage(id);
						//发送通知
						nm.setIsShareType("0");
						ms.sendNoticeMessage(nm);
						message = "添加好友成功";
						flag = "1";
					}else message = "添加好友失败,请稍后再试";
				}else{
					//请求类型:闲置分享
					if(reqType.equals("goods")){
						if(!ms.isIdleShared(reqShareId)){
							ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
							Map idleInfo = ss.getGoodsInfo(reqShareId, "price,totaltime");
							//生成订单
							IdleOrder order = new IdleOrder();
							order.setAdd_time(date);
							order.setIdleid(reqShareId);
							order.setIdle(reqShare);
							order.setLendTimeLong((idleInfo.get("totaltime")!=null)?idleInfo.get("totaltime").toString():"");
							order.setPrice(price!=null?price:0);
							order.setBuyer_uid(userid);
							order.setSeller_uid(senderid);
							order.setDeliveryType(tranType);
							order.setDeliveryLocation(deliveryLocation);
							order.setPickupLocation(pickupLocation);
							order.setShareType(reqShareMethod);
							order.setRemark(createDeliveryRemark(tranType));
							ss.addOrder(order);
							//发送通知
							nm.setIsShareType("1");
							if(ms.sendNoticeMessage(nm)){
								if(!addMsg.equals("")){
									//有附加信息，添加私信
									PrivateMessage pm = new PrivateMessage();
									pm.setContent(addMsg);
									pm.setIsPublic("0");
									pm.setSendTime(date);
									pm.setUserid(userid);
									pm.setSenderid(senderid);
									ms.getBaseDao().saveOrUpdate(pm);
								}
								ms.updateToDealComplete(id);
								message = "物品分享成功 ! 已生成分享单,请及时查看";
								root.put("idleid",reqShareId);
								flag = "1";
							}else message = "请求处理失败,请稍后再试";
							//若勾选已出，则变为已处理
							if(shareFlag != null && shareFlag.equals("1")){
								UserUpdateDao uud = (UserUpdateDao) GetAC.getAppContext().getBean("UserUpdateDao");
								uud.updateIsShared(reqShareId, "goods");
							}
						}else message = "这个物品已被分享给其他人了哦~<br>还有类似闲置也发布上来吧";
					}
				}
			}else message = "这条请求已经处理完成咯~";
		}
		catch(Exception e){
			message = e.getMessage();
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}
	
	/**
	 * 确认完成交易
	 * @throws IOException
	 */
	public void shareConfirm() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			Map reqMap = ms.getRequestMessage(id);
			if(reqMap != null){
				Long idleid = Long.parseLong(reqMap.get("reqShareId").toString());
				ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
				UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
				Map map = ss.getGoodsInfo(idleid, "id,userid,goods,type,price");
				if(map != null){
					map.put("reqDelivery",reqMap.get("reqDelivery"));
					map.put("reqid",id);
					map.put("senderid",reqMap.get("senderid").toString());
					Long senderid = Long.parseLong(reqMap.get("senderid").toString());
					Long userid = Long.parseLong(reqMap.get("userid").toString());
					map.putAll(us.getUserInfo(senderid, "realname,head_ico"));
					map.putAll(us.getUserInfo(userid, "phone"));
					MessageBlockTemplate mbt = new MessageBlockTemplate();
					root.put("reqBox",mbt.requestBox(map));
					flag = "1";
				}else{
					message = "此物品已经下架了哦~<br>若还有的话再发布上来吧";
				}
			}else message = "该请求不存在";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	/**
	 * 获取未读请求
	 * @throws IOException
	 */
	public void getUnRead() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getUnReadMessage(senderid, "request",msgSize,pageno==null?0:pageno);
			if(list!=null&&list.size()>0){
				//删除已读消息
				String[] midList = {}; 
				for(int i = 0 ; i < list.size(); i++){
					Map map = (Map) list.get(i);
					midList = AddArray.addString(midList, map.get("id").toString());
				}
				ms.deleteUnReadMessageByType(senderid, "request", midList);
			}
			MessageBlockTemplate mbt = new MessageBlockTemplate();
			root.put("data", mbt.requestMessageList(list));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	/**
	 * 获取预订信息
	 * @throws IOException
	 */
	public void getBookInfo() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			UserService uc = (UserService) GetAC.getAppContext().getBean("UserService");
			root.put("userInfo",uc.getUserInfo(senderid, "phone,dorm"));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	private String createReqContent(){
		String content = "";
		if(reqType.equals("addFriends"))
			content = "请求加为好友";
		else if(reqType.equals("goods")){
			if(reqShareMethod.equals("sell"))
				content = "向我发送了一条物品购买请求";
			else if(reqShareMethod.equals("lend"))
				content = "向我发送了一条借入物品的请求";
			else if(reqShareMethod.equals("gift"))
				content = "想要我所发布的物品";
			else content = "向我发送了一条物品请求";
		}	
		return content;
	}
	
	private String createNoticeContent(){
		String content = "";
		if(reqType.equals("addFriends"))
			content = "同意了添加好友的请求";
		else if(reqType.equals("goods")){
			if(reqShareMethod.equals("sell"))
				content = "同意了我的物品购买请求";
			else if(reqShareMethod.equals("lend"))
				content = "同意了我借入物品的请求";
			else if(reqShareMethod.equals("gift"))
				content = "同意了我的物品分享请求";
			else content = "同意了我的物品请求";
		}	
		return content;
	}
	
	private String createDeliveryRemark(String tranType){
		String content = "无";
		if(tranType.equals("自主交易"))	
			content = "请及时与对方进行私信或线下联系";
		else if(tranType.equals("网站送货")){
			content = "此方式仅限活动期间、物品从本部运送到沙河,详情见<a href='/activity/grad_carnival/show' target = '_blank'style = 'color:#e68303;'>活动细则</a>";
		}
		return content;
	}
	
	/**
	 * 是否能送货, 仅网站送货
	 */
	private boolean canDelivery(Long userid){
		boolean flag = false;
		try{
			UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
			Map buyer = us.getUserInfo(senderid, "department,hs_year,educational");
			Map seller = us.getUserInfo(userid, "department,hs_year,educational");
			Object b_dep = buyer.get("department"),
				b_grade = buyer.get("hs_year"),
				b_edu = buyer.get("educational"),
				s_dep = seller.get("department"),
				s_grade = seller.get("hs_year"),
				s_edu = seller.get("educational");
			boolean b_flag = (b_edu.equals("本科") && (b_dep.equals("飞行学院16系") || b_grade.equals("11级") || b_grade.equals("12级"))),
					s_flag = !(s_edu.equals("本科") && (s_dep.equals("飞行学院16系") || s_grade.equals("11级") || s_grade.equals("12级")));
			if(b_flag && s_flag){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getReqMessage() {
		return reqMessage;
	}
	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getReqShare() {
		return reqShare;
	}
	public void setReqShare(String reqShare) {
		this.reqShare = reqShare;
	}
	public Long getReqShareId() {
		return reqShareId;
	}
	public void setReqShareId(Long reqShareId) {
		this.reqShareId = reqShareId;
	}

	public String getReqShareMethod() {
		return reqShareMethod;
	}

	public void setReqShareMethod(String reqShareMethod) {
		this.reqShareMethod = reqShareMethod;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getMsgSize() {
		return msgSize;
	}

	public void setMsgSize(Integer msgSize) {
		this.msgSize = msgSize;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShowContact() {
		return showContact;
	}

	public void setShowContact(String showContact) {
		this.showContact = showContact;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getDorm() {
		return dorm;
	}

	public void setDorm(String dorm) {
		this.dorm = dorm;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}
	
	
}
