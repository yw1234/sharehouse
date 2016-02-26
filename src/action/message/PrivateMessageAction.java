package action.message;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import model.PrivateMessage;
import net.sf.json.JSONObject;
import services.message.MessageService;
import template.MessageBlockTemplate;
import util.AddArray;
import util.GetAC;
import util.GetTime;
import util.StaticInfo;
import util.WordsFilter;

public class PrivateMessageAction extends ActionSupport {
	private Long id;
	private Long userid;
	private Long senderid = (Long)ServletActionContext.getRequest().getSession().getAttribute("userid");
	private String content;
	private Date sendTime;
	private String isPublic;
	private Integer pageno;
	private String message="";
	private Integer msgSize = StaticInfo.msgNumber;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		if(GetTime.getTimeDifference(new Date(),ms.getPrivateLastSendTime(senderid),"bySecond",10)){
			if(ms.canBeSend(userid, senderid))
			{
				PrivateMessage pm = new PrivateMessage();
				pm.setUserid(userid);
				pm.setSenderid(senderid);
				pm.setSendTime(new Date());
				pm.setIsPublic(isPublic==null?"0":isPublic);
				pm.setContent(WordsFilter.imgFilter(content));
				if(ms.sendPrivateMessage(pm)){
					flag = "1";
				}else message = "发送失败,请稍后再试";
			}else message = "给自己留言是神马情况?";
			
		}else message = "私信有点小频繁哦~10秒后再试吧~";
		root.put("flag", flag);
		root.put("message", message);
		root.write(ServletActionContext.getResponse().getWriter());	
	}
	
	public void hide() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		if(ms.hidePrivateMessage(id,senderid)){
			flag = "1";
		}else message = "删除失败,请稍后再试";
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
			List list = ms.getUnReadMessage(senderid, "private",msgSize,pageno==null?0:pageno);
			if(list!=null&&list.size()>0){
				//删除已读消息
				String[] midList = {}; 
				for(int i = 0 ; i < list.size(); i++){
					Map map = (Map) list.get(i);
					midList = AddArray.addString(midList, map.get("id").toString());
				}
				ms.deleteUnReadMessageByType(senderid, "private", midList);
			}
			MessageBlockTemplate mbt = new MessageBlockTemplate();
			root.put("data", mbt.privateMessageList(list));
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
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
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
