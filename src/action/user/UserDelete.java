package action.user;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import model.Circle;
import model.Picture;
import model.User;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.share.ShareService;
import util.DeleteFile;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserDeleteDao;
import dao.user.UserSendDao;

public class UserDelete extends ActionSupport{
	
	private String url;
	private Long id;
	private String type;
	private Long typeid;
	private String del_type;
	private Long cid;
	private String content;
	private String cname;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	public String idlePicture() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(id!=null){
			ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
			Picture p = (Picture) ss.getBaseDao().get(Picture.class, id);
			if(ss.isIdleExists(userid,p.getTypeid())){
				ss.getBaseDao().delete(Picture.class,id);
				flag = "1";
				//删除文件
				String path = StaticInfo.ImgPhysicalPath;
				DeleteFile df = new DeleteFile();
				String imgBig = path+File.separator+p.getUrl().substring(StaticInfo.ImgURL.length()+1);
				String imgThumbs = path+File.separator+p.getThumbs_url().substring(StaticInfo.ImgURL.length()+1);
				if(new File(imgBig).exists())
					df.deleteFile(imgBig);
				if(new File(imgThumbs).exists())
					df.deleteFile(imgThumbs);
			}else message = "不能删除别人的上传图片";
		}else message = "该图片不存在";
		root.put("flag",flag);
		root.put("message", message);
		root.write(response.getWriter());
		return null;
	}
	
	public String goods() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		String path = StaticInfo.ImgPhysicalPath;
		//UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
		ShareService ss = (ShareService) GetAC.getAppContext().getBean("ShareService");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(del_type.equals("single")){
			//flag = udd.deleteGoodsInCircle(userid,typeid,cid);
			//if(flag){
				//udd.decreaseOperationTimes("sendNumber",cid,"circle");
				//udd.decreaseSendActiveInCircle(id, cid);
			//}
			
		}else{
			if(ss.isIdleExists(userid, typeid)){
				if(ss.deleteIdle(userid,typeid, path)){
					UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
					udd.decreaseOperationTimes("send_active",userid,"sh_user");
					flag = "1";
				}message = "删除失败,请稍后再试";
			}else message = "不能删别人的发布!";
			
		}
		root.put("flag",flag);
		root.put("message", message);
		root.write(response.getWriter());
		return null;
	}
	
	public String needs() throws IOException{
		UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		boolean flag = false;
		if(del_type.equals("single")){
			flag = udd.deleteNeedsInCircle(userid,typeid,cid);
			if(flag){
				udd.decreaseOperationTimes("sendNumber",cid,"circle");
				udd.decreaseSendActiveInCircle(id, cid);
			}
		}else{
			flag = udd.deleteNeeds(userid,typeid);
			if(flag){
				udd.decreaseOperationTimes("send_active",userid,"sh_user");
			}
		}
		if(flag){
			response.getWriter().println("ok");
		}else response.getWriter().println("error");
		return null;
	}
	
	/*public String userInCircle() throws IOException{
		UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
		CircleGetDao cgd = (CircleGetDao) GetAC.getAppContext().getBean("CircleGetDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		Circle c = cgd.getCircleInfo(cid);
		String msg = "";
		try{
			if(!userid.equals(id)){
				if(cgd.isAdmin(userid, cid))
					if(udd.deleteUserInCircle(id,c.getId(),"0")){
						Object []obj = {c.getName()};
						UserSendDao usd = (UserSendDao) GetAC.getAppContext().getBean("UserSendDao");
						//usd.sendNoticeMessage(id, "deleteUserInCircle","未填写",obj,1);
						udd.decreaseOperationTimes("circle_number", id, "sh_user");
						udd.decreaseOperationTimes("member_count", c.getId(), "circle");
						msg	= "ok";		
					}else msg = "不能踢管理员哦~";
			}else msg = "不要以这种方式退出分享圈哦~";
		}catch(Exception e){
			e.printStackTrace();
			msg = "操作失败,请稍候再试";
		}
		response.getWriter().println(java.net.URLEncoder.encode(msg,"UTF-8"));
		return null;
	}*/
	
	public String friends() throws IOException{
		UserDeleteDao udd = (UserDeleteDao) GetAC.getAppContext().getBean("UserDeleteDao");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		if(udd.deleteFriends(userid, id)){
			response.getWriter().println("ok");
			udd.decreaseOperationTimes("friend_number",userid, "sh_user");
			udd.decreaseOperationTimes("friend_number",id, "sh_user");
		}else response.getWriter().println("error");
		return null;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public String getDel_type() {
		return del_type;
	}
	public void setDel_type(String delType) {
		del_type = delType;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
}
