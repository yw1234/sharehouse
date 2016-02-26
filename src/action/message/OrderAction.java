package action.message;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NoticeMessage;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.message.MessageService;
import services.share.ShareService;
import template.MessageBlockTemplate;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

public class OrderAction extends ActionSupport{
	
	private Long id;
	private String message= "";
	private Integer pageno;
	private Integer msgSize = StaticInfo.msgNumber;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	private Long loggedUid = (Long) request.getSession().getAttribute("userid");
	
	public void get() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Map map = ss.getOrderInfoByIdle(id,loggedUid);
			if(map != null){
				MessageBlockTemplate mbt = new MessageBlockTemplate();
				root.put("box",mbt.orderBox(map,loggedUid));
				flag = "1";
			}else{
				message = "该分享单不存在或已被删除";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message",message);
		root.write(response.getWriter());	
	}
	
	public void confirm() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			if(ss.confirmOrder(id, loggedUid)){
				Map io = ss.getOrderInfoByIdle(id, loggedUid);
				NoticeMessage nm = new NoticeMessage();
				nm.setContent("已经确认收到 : "+io.get("idle").toString());
				nm.setSendTime(new Date());
				nm.setSenderid(loggedUid);
				nm.setUserid(Long.parseLong(io.get("seller_uid").toString()));
				nm.setNoticeType("orderInfo");
				MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
				ms.sendNoticeMessage(nm);
				flag = "1";
			}else{
				message = "操作失败 ,该订单不存在";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
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

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getMsgSize() {
		return msgSize;
	}
	
	
}
