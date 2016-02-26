package activity.lostfound.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import util.GetAC;
import util.ImageScale;
import util.LocalName;
import util.StaticInfo;

import activity.lostfound.Template;
import activity.lostfound.model.Lost;
import activity.lostfound.service.LostService;

import com.opensymphony.xwork2.ActionSupport;

public class LostAction extends ActionSupport{
	
	private Long id;
	private String name;
	private String campus;
	private String place;
	private String place_gene;
	private String picList;
	private String content;
	private Integer size = StaticInfo.feedsNumber;
	private Integer pn;
	
	/**上传相关**/
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String uploadDir;
	private String callBackFunc;
	private String uploadType;
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	
	public String show(){
		try{
			Long userid = (Long) request.getSession().getAttribute("userid");
			if(userid.equals(StaticInfo.adminId)){
				request.setAttribute("isAdmin", "1");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public void get() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		try{
			LostService ls = (LostService) GetAC.getAppContext().getBean("LostService");
			Map params = new HashMap();
			params.put("campus",campus);
			params.put("place",place);
			List list = ls.get(params, size, pn);
			if(list!=null && list.size()>0){
				Long userid = (Long) request.getSession().getAttribute("userid");
				Map m = new HashMap();
				m.put("isAdmin", userid.equals(StaticInfo.adminId)?"1":"0");
				root.put("data", Template.sellBooksNotes(list,m));
				root.put("hasNext", list.size()>=size?"1":"0");
			}
			else root.put("data", "<div style='float:left;width:100%;margin-top:30px;'>暂时没有失物信息</div>");
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}
	
	public void getDetail() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		try{
			LostService ls = (LostService) GetAC.getAppContext().getBean("LostService");
			root.putAll(ls.getDetail(id));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}
	
	public void upload() throws IOException{
		boolean tag = false;
		String newfilename = null;
		String newfilename_thumbs = null;
		Date d = new Date();
		Integer year = d.getYear()+1900;
		Integer month = d.getMonth()+1;
		Integer day = d.getDate();
		String now = LocalName.getLocalName();
		String path = StaticInfo.ImgPhysicalPath+File.separator+uploadDir;
		path += File.separator+year.toString();
		File dirY = new File(path);
		if(!dirY.exists())
		{
			dirY.mkdirs();
		}
		path += File.separator+month.toString();
		File dirM = new File(path);
		if(!dirM.exists())
		{
			dirM.mkdirs();
		}
		path += File.separator+day.toString();
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		int index = fileFileName.lastIndexOf(".");
		if(index != -1)
		{
			newfilename = now + fileFileName.substring(index);
			newfilename_thumbs = now + "-thumbs" + fileFileName.substring(index);
		}
		else{
			newfilename = now;
			newfilename_thumbs = now + "-thumbs";
		}
		String thu = path+File.separator+newfilename_thumbs;
		path += File.separator+newfilename;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try{
			FileInputStream fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(new File(dir,newfilename));
			bos = new BufferedOutputStream(fos);
			byte [] buf = new byte[4096];
			int len = -1;
			while((len = bis.read(buf)) != -1)
			{
				bos.write(buf,0,len);
			}
			tag = true;
		}finally{
			try{
				if(bis != null)
				{
					bis.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
			try{
				if(bos != null)
				{
					bos.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		//生成大图
		try{
			ImageScale.reduceImg(path,800);
		}catch(Exception e){e.printStackTrace();}
		//小图
		try{
			ImageScale.copy(path, thu);
			ImageScale.reduceImg(thu,250);
		}catch(Exception e){e.printStackTrace();}
		String callback = "";
		if(uploadType==null || uploadType.equals("common")){
			if(tag){
				callback = "<script>parent.upload.callBack({flag:'1',func:function(){parent."+callBackFunc+"({url:\""+(StaticInfo.ImgURL+"/"+uploadDir+"/"+year.toString()+"/"+month.toString()+"/"+day.toString()+"/"+newfilename_thumbs).trim()+"\"});}});</script>";  
			}else callback = "<script>parent.upload.callBack({flag:'0'});</script>";  
		}else if(uploadType.equals("mtxx")){
			//mtxx
			if(tag){
				callback = "upload.callBack({flag:'1',func:function(){"+callBackFunc+"({url:\""+(StaticInfo.ImgURL+"/"+uploadDir+"/"+year.toString()+"/"+month.toString()+"/"+day.toString()+"/"+newfilename_thumbs).trim()+"\"});}});";  
			}else callback = "upload.callBack({flag:'0'})";  
		}
		response.getWriter().println(callback);
	}
	
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		try{
			Long userid = (Long) request.getSession().getAttribute("userid");
			Lost lost = new Lost();
			lost.setAdd_time(new Date());
			lost.setCampus(campus);
			lost.setContent(content);
			lost.setName(name);
			lost.setPlace(place);
			lost.setPlace_gene(place_gene);
			lost.setUserid(userid);
			LostService ls = (LostService) GetAC.getAppContext().getBean("LostService");
			if(ls.send(lost)){
				ls.addLostPicture(lost.getId(), picList.split(","));
				flag = "1";
			}else message = "发布失败";
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}
	
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		try{
			LostService ls = (LostService) GetAC.getAppContext().getBean("LostService");
			if(ls.delete(id)){
				flag = "1";
			}else message = "删除失败";
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag", flag);
		root.put("message", message);
		root.write(response.getWriter());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getSize() {
		return size;
	}
	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getCallBackFunc() {
		return callBackFunc;
	}

	public void setCallBackFunc(String callBackFunc) {
		this.callBackFunc = callBackFunc;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlace_gene() {
		return place_gene;
	}

	public void setPlace_gene(String place_gene) {
		this.place_gene = place_gene;
	}

	public String getPicList() {
		return picList;
	}

	public void setPicList(String picList) {
		this.picList = picList;
	}
	
	
}
