package action.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import services.message.MessageService;
import services.share.ShareService;
import template.MessageBlockTemplate;
import template.NotesBlockTemplate;
import util.AddArray;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserDeleteDao;

public class AtMessageAction extends ActionSupport{

	private Long id;
	private String key;
	private Integer pageno;
	private Integer msgSize = StaticInfo.msgNumber;
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	private Long loggedUid = (Long) request.getSession().getAttribute("userid");
	private String message = "";
	
	/**
	 * 获取好友
	 * @throws IOException
	 */
	public void getFriends() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		List list = ms.getAtFriends(loggedUid);
		if(list != null && list.size() > 0){
			root.put("user",list);
			flag = "1";
		}else{
			message = "还没有添加任何好友哦~";
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());	
	}
	
	/**
	 * 删除at信息
	 * @throws IOException
	 */
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			if(ms.deleteAtMessage(id, loggedUid))
				flag = "1";
			else{
				message = "您无权删除此@消息";
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
	 * 获取未读at
	 * @throws IOException
	 */
	public void getUnRead() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			List list = ms.getUnReadMessage(loggedUid, "at",msgSize,pageno==null?0:pageno),
				atList = null;
			if(list!=null&&list.size()>0){
				atList = new ArrayList();
				String[] midList = {}; 
				for(int i = 0; i < list.size() ; i++){
					//相关发布
					Map at_info = (Map) list.get(i);
					String at_type = at_info.get("atType").toString() ,at_id = at_info.get("atShareId").toString();
					at_info.put("publish",ms.getAtPublishInfo(Long.parseLong(at_id), at_type ,loggedUid));
					atList.add(at_info);
					//删除已读消息
					Map map = (Map) list.get(i);
					midList = AddArray.addString(midList, map.get("id").toString());
				}
				ms.deleteUnReadMessageByType(loggedUid, "at", midList);
			}
			MessageBlockTemplate mbt = new MessageBlockTemplate();
			root.put("data", mbt.atMessageList(atList));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "出错了...";
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
