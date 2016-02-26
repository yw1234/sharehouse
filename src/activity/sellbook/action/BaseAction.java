package activity.sellbook.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.user.UserService;
import util.GetAC;
import util.StaticInfo;

import activity.sellbook.service.BooksService;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	private String page;
	private String status;
	
	private Integer feedsSize = StaticInfo.feedsNumber;
	private Integer pageno;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String show(){
		init();
		page = "show";
		return SUCCESS;
	}
	
	public String booking() throws IOException{
		init();
		page = "booking";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			Long userid = (Long)request.getSession().getAttribute("userid"); 
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Map params = new HashMap();
			params.put("uid", userid);
			params.put("isComplete", status);
			List list = bs.getBookingList(params, feedsSize, 0);
			request.setAttribute("bookingList", list);
			request.setAttribute("bookingCount", bs.getBookingCount(params));
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
		
		return SUCCESS;
	}
	
	private void init(){
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
		try{
			if(userid!=null && userid.toString().equals(StaticInfo.adminId.toString()))
				request.setAttribute("isSbAdmin", "1");
			else request.setAttribute("isSbAdmin", "0");
			request.setAttribute("userInfo", us.getUserInfo(userid, "realname,phone"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
