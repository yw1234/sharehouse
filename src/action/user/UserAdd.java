package action.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import model.User;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.message.MessageService;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserAddDao;
import dao.user.UserGetDao;
import dao.user.UserJoinDao;

public class UserAdd extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long typeid;
	private String type;
	private Long pmid;
	private String circlename;
	private String message= "";
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String circle() throws IOException{
		UserJoinDao ujd = (UserJoinDao) GetAC.getAppContext().getBean("UserJoinDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(ujd.joincircle(userid, id.toString().split(","))){
			ServletActionContext.getResponse().getWriter().println("ok");
		}else{
			ServletActionContext.getResponse().getWriter().println("error");
		}
		return null;
	}
	
	public String concerned() throws IOException{
		UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(uad.addConcernedFriends(userid,id)){
			ServletActionContext.getResponse().getWriter().println("ok");
		}else{
			ServletActionContext.getResponse().getWriter().println("error");
		}
		return null;
	}
	
	public String favor() throws IOException{
		UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
		id = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(uad.addFavor(id, typeid, type)){
			uad.addHotSpot(typeid, type, 3);
			ServletActionContext.getResponse().getWriter().println("ok");
		}else{
			ServletActionContext.getResponse().getWriter().println("error");
		}
		return null;
	}
	
	/*
	public String acceptCircle() throws IOException{
		JSONObject root = new JSONObject();
		UserJoinDao ujd = (UserJoinDao) GetAC.getAppContext().getBean("UserJoinDao");
		UserGetDao ugd = (UserGetDao) GetAC.getAppContext().getBean("UserGetDao");
		CircleGetDao cgd = (CircleGetDao) GetAC.getAppContext().getBean("CircleGetDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		Long cid = ugd.isRequest(userid, id, type);
		if(cid!=null){
			String idlist = cid.toString()+",";
			List sc = cgd.getSuperiorCircle(userid, cid);
			if(sc!=null&&sc.size()>0)
				for(int i=0;i<sc.size();i++)
					idlist+=sc.get(i).toString()+",";
			String [] circleid = idlist.split(",");
			if(ujd.joincircle(userid,circleid)){
				root.put("flag","ok");
			}
		}else{
			root.put("flag","error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}*/
	
	public void shield() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		MessageService ms = (MessageService) GetAC.getAppContext().getBean("MessageService");
		JSONObject root = new JSONObject();
		try{
			Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
			String sql = "insert into shield(userid,shieldid) values("+userid+","+id+")";
			if(ms.getBaseDao().execute(sql)){
				flag = "1";
			}else message = "操作失败,请稍后再试";
		}
		catch(Exception e){
			e.printStackTrace();
			message = "系统出现问题,操作失败,请稍后再试";
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

	public Long getPmid() {
		return pmid;
	}

	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}

	public String getCirclename() {
		return circlename;
	}

	public void setCirclename(String circlename) {
		this.circlename = circlename;
	}
}
