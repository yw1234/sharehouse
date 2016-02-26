package action.message;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.message.MessageService;
import template.MessageBlockTemplate;
import util.AddArray;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

public class NoticeMessageAction extends ActionSupport {
	private Long id;
	private Long userid;
	private Long senderid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
	private String content;
	private Date sendTime;
	private String additionalMessage;
	private String noticeType;
	private String noticeShare;
	private Long noticeShareId;
	private String isShareType;
	private String message= "";
	private Integer pageno;
	private Integer msgSize = StaticInfo.msgNumber;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			if(ms.deleteNoticeMessage(id)){
				flag = "1";
			}else message = "删除失败,请稍后再试";
		}
		catch(Exception e){
			e.printStackTrace();
			message = "系统出现问题,操作失败,请稍后再试";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(ServletActionContext.getResponse().getWriter());	
	}
	
	public void getUnRead() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getUnReadMessage(senderid, "notice",msgSize,pageno==null?0:pageno);
			if(list!=null&&list.size()>0){
				//删除已读消息
				String[] midList = {}; 
				for(int i = 0 ; i < list.size(); i++){
					Map map = (Map) list.get(i);
					midList = AddArray.addString(midList, map.get("id").toString());
				}
				ms.deleteUnReadMessageByType(senderid, "notice", midList);
			}
			MessageBlockTemplate mbt = new MessageBlockTemplate();
			root.put("data", mbt.noticeMessageList(list));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "出错了...";
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(ServletActionContext.getResponse().getWriter());
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
	public String getAdditionalMessage() {
		return additionalMessage;
	}
	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeShare() {
		return noticeShare;
	}
	public void setNoticeShare(String noticeShare) {
		this.noticeShare = noticeShare;
	}
	public Long getNoticeShareId() {
		return noticeShareId;
	}
	public void setNoticeShareId(Long noticeShareId) {
		this.noticeShareId = noticeShareId;
	}
	public String getIsShareType() {
		return isShareType;
	}
	public void setIsShareType(String isShareType) {
		this.isShareType = isShareType;
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
