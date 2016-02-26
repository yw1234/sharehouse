package action.message;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import model.AtMessage;
import model.CommentMessage;
import model.RequestMessage;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.message.MessageService;
import services.share.ShareService;
import template.MessageBlockTemplate;
import template.NotesBlockTemplate;
import util.AddArray;
import util.GetAC;
import util.GetTime;
import util.StaticInfo;
import util.WordsFilter;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserDeleteDao;

public class CommentMessageAction extends ActionSupport {

	private Long id;
	private Long userid;
	private Long senderid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
	private String content;
	private Date sendTime;
	private String commType;
	private Long commShareId;
	private String commShare;
	private String message= "";
	private Integer pageno;
	private Integer msgSize = StaticInfo.msgNumber;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	/**
	 * 发布评论
	 * @throws IOException
	 */
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		if(GetTime.getTimeDifference(new Date(),ms.getCommentLastSendTime(senderid),"bySecond",10)){
			Map atMap = WordsFilter.atFilter(content,senderid);
			String comm = WordsFilter.imgFilter(atMap.get("text").toString());
			Date now = new Date();
			CommentMessage cm = new CommentMessage();
			cm.setSenderid(senderid);
			cm.setUserid(userid);
			cm.setSendTime(now);
			cm.setContent(comm);
			cm.setCommShareId(commShareId);
			cm.setCommShare(commShare);
			cm.setCommType(commType);
			ms.sendCommentMessage(cm);
			Long commid = cm.getId();
			if(commid != null)
			{
				//处理@
				List ulist = (List) atMap.get("uList");
				if(ulist != null && ulist.size() > 0){
					for(int i = 0; i < ulist.size(); i++){
						Object []u = (Object[]) ulist.get(i);
						AtMessage am = new AtMessage();
						am.setSenderid(senderid);
						am.setUserid(Long.parseLong(u[0].toString()));
						am.setSendTime(now);
						am.setContent(comm);
						am.setAtType("goods");
						am.setAtShareId(commShareId);
						ms.sendAtMessage(am);
					}
				}
				UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
				NotesBlockTemplate nbt = new NotesBlockTemplate();
				Map params = new HashMap();
				params.put("userid",senderid);
				params.put("senderid",userid);
				params.put("isAdmin","0");
				String data = nbt.commentList(ms.getCommentMessageByCommId(commid),params);
				uad.addOperationTimes("comment_size",commShareId, commType);
				if(!userid.equals(senderid)){
					if(!ms.isCommented(senderid, commShareId, commType))
						uad.addHotSpot(commShareId,commType, StaticInfo.addHot_commPoint);
				}
				if(data != null && !data.equals(""))
					root.put("data", data);
				else root.put("data", "");
				flag = "1";
			}else {message="留言失败,请稍后再试";}
		}else{message = "留言有点小频繁哦~10秒后再试吧";}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());	
	}
	
	/**
	 * 删除评论
	 * @throws IOException
	 */
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Long sid = ss.getIdleSender(commShareId);
			if(senderid.equals(sid) || senderid.equals(StaticInfo.adminId) || ms.isCommentMessageExists(senderid, id))
				if(ms.deleteCommentMessage(id)){
					UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
					udd.decreaseOperationTimes("comment_size",commShareId,"goods");
					flag = "1";
				}else message = "删除失败,请稍后再试";
			else{
				message = "您无权删除此评论";
			}
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
	 * 获取未读评论
	 * @throws IOException
	 */
	public void getUnRead() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getUnReadMessage(senderid, "comment",msgSize,pageno==null?0:pageno);
			if(list!=null&&list.size()>0){
				//删除已读消息
				String[] midList = {}; 
				for(int i = 0 ; i < list.size(); i++){
					Map map = (Map) list.get(i);
					midList = AddArray.addString(midList, map.get("id").toString());
				}
				ms.deleteUnReadMessageByType(senderid, "comment", midList);
			}
			MessageBlockTemplate mbt = new MessageBlockTemplate();
			root.put("data", mbt.commentMessageList(list));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "出错了...";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	/**
	 * 获取评论
	 * @throws IOException
	 */
	public void get() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getCommentMessageByShareId(commShareId, commType,msgSize, pageno);
			NotesBlockTemplate nbt = new NotesBlockTemplate();
			Map params = new HashMap();
			params.put("userid",senderid);
			params.put("senderid",userid);
			params.put("isAdmin",senderid.equals(StaticInfo.adminId)?"1":"0");
			root.put("data", nbt.commentList(list,params));
			root.put("commentSize", list!=null?list.size():0);
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "出错了...";
		}
		root.put("flag", flag);
		root.put("maxSize",msgSize);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	/**
	 * 屏蔽相关评论
	 * @throws IOException
	 */
	public void hideCommentedMessage() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
			if(ms.hideCommentedMessage(commShareId, commType, senderid)){
				flag = "1";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
	}
	
	/**
	 * 获取相关评论
	 * @throws IOException
	 */
	public void matter() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getCommentMessageByShareId(commShareId, commType, msgSize, pageno);
			MessageBlockTemplate nbt = new MessageBlockTemplate();
			Map params = new HashMap();
			params.put("typeid",commShareId);
			params.put("type",commType);
			params.put("pn", pageno+1);
			root.put("data", nbt.matterCommentList(list,params));
			root.put("commentSize", list!=null?list.size():0);
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "加载错误,请稍后再试";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());
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
	public String getCommType() {
		return commType;
	}
	public void setCommType(String commType) {
		this.commType = commType;
	}
	
	public Long getCommShareId() {
		return commShareId;
	}

	public void setCommShareId(Long commShareId) {
		this.commShareId = commShareId;
	}

	public String getCommShare() {
		return commShare;
	}
	public void setCommShare(String commShare) {
		this.commShare = commShare;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
}
