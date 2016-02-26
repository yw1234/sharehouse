package action.user;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import util.DeleteFile;
import util.GetAC;
import util.ImageScale;
import util.LocalName;
import util.StaticInfo;

import model.User;

import com.opensymphony.xwork2.ActionSupport;
import dao.user.UserAddDao;

public class UserUpload extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String uploadDir;
	private String callBackFunc;
	private String uploadType;
	

	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	private String p;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	/**
	 * 物品图片
	 * @return
	 * @throws IOException
	 */
	public String idleImages() throws IOException
	{
		boolean tag = false;
		String newfilename = null;
		String newfilename_thumbs = null;
		Date d = new Date();
		Integer year = d.getYear()+1900;
		Integer month = d.getMonth()+1;
		Integer day = d.getDate();
		String imgName = java.util.UUID.randomUUID().toString();
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
			newfilename = imgName + fileFileName.substring(index);
			newfilename_thumbs = imgName + "-thumbs" + fileFileName.substring(index);
		}
		else{
			newfilename = imgName;
			newfilename_thumbs = imgName + "-thumbs";
		}
		String thu = path+File.separator+newfilename_thumbs;
		path += File.separator+newfilename;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try{
			//原图
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
		try{
			ImageScale.reduceImg(path,600);
		}catch(Exception e){e.printStackTrace();}
		//生成缩略图
		try{
			ImageScale.copy(path,thu);
			ImageScale.reduceImg(thu,250);
		}catch(Exception e){e.printStackTrace();}
		
		String callback = "";
		String thumbs_url = (StaticInfo.ImgURL+"/"+uploadDir+"/"+year.toString()+"/"+month.toString()+"/"+day.toString()+"/"+newfilename_thumbs).trim();
		if(uploadType==null || uploadType.equals("common")){
			if(tag){
				callback = "<script>parent.upload.callBack({flag:\"1\",func:function(){parent."+callBackFunc+"({url:\""+thumbs_url+"\"});}});</script>";  
			}else callback = "<script>parent.upload.callBack({flag:\"0\"});</script>";  
		}else if(uploadType.equals("mtxx")){
			//mtxx
			if(tag){
				callback = "upload.callBack({flag:\"1\",func:function(){"+callBackFunc+"({url:\""+thumbs_url+"\"});}});";  
			}else callback = "upload.callBack({flag:\"0\"})";  
		}
		
		response.getWriter().println(callback.trim());
		return null;
	}
	
	/**
	 * 头像
	 * @return
	 * @throws IOException
	 */
	public String headIco() throws IOException
	{
		boolean tag = false;
		String newfilename_thumbs = null;
		Date d = new Date();
		Integer year = d.getYear()+1900;
		Integer month = d.getMonth()+1;
		Integer day = d.getDate();
		String imgName = java.util.UUID.randomUUID().toString();
		String path = StaticInfo.ImgPhysicalPath+File.separator+File.separator+uploadDir;
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
			newfilename_thumbs = imgName + "-thumbs" + fileFileName.substring(index);
		}
		else{
			newfilename_thumbs = imgName + "-thumbs";
		}
		path += File.separator+newfilename_thumbs;
		
		//原图
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try{	
			FileInputStream fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(new File(dir,newfilename_thumbs));
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
				response.getWriter().println("<script>parent.alert('照片不能超过5M');</script>");
			}
		}
		try{
			ImageScale.reduceImg(path,240);
		}catch(Exception e){e.printStackTrace();}
		if(tag){
			response.getWriter().println("<script>parent.upload.callBack({flag:'1',func:function(){parent."+callBackFunc+"({url:\""+(StaticInfo.ImgURL+"/"+uploadDir+"/"+year.toString()+"/"+month.toString()+"/"+day.toString()+"/"+newfilename_thumbs).trim()+"\"});}});</script>");  
		}else response.getWriter().println("<script>parent.upload.callBack({flag:'0'})</script>");  
		return null;
	}
	
	/**
	 * 裁剪头像
	 * @return
	 * @throws IOException
	 */
	public String cutimage() throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0";
		String message = "";
		FileInputStream is = null;
		ImageInputStream iis = null;
		String pt = p.substring(StaticInfo.ImgURL.length()+1);
		String path = StaticInfo.ImgPhysicalPath+File.separator+pt;
		int index = path.lastIndexOf(".");
		String endname = path.substring(index+1).trim();
		try { 
			Iterator <ImageReader> it = ImageIO.getImageReadersByFormatName(endname); 
			ImageReader reader = (ImageReader) it.next(); 
			is = new FileInputStream(path.trim()); 
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam(); 
			Rectangle rect = new Rectangle(x, y, w, h);  
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0 ,param); 
			ImageIO.write(bi,endname, new File(path.trim())); 
		} 
		finally{
		if (is != null )
		is.close() ; 
		if (iis != null )
		iis.close(); 
		} 
		try{
			ImageScale.copy(path,path.substring(0,index-7)+path.substring(index));
			//复制75*75小头像
			ImageScale.reduceImg(path,75);
		}catch(Exception e){e.printStackTrace();}
		HttpSession session = ServletActionContext.getRequest().getSession();
		id = (Long)session.getAttribute("userid");
		UserAddDao uad = (UserAddDao) GetAC.getAppContext().getBean("UserAddDao");
		int p_index = p.trim().lastIndexOf(".");
		String avatar_thumbs = p.trim()+"?temp="+new Date().getTime(),
				avatar_big = p.trim().substring(0,p_index-7)+p.trim().substring(p_index)+"?temp="+new Date().getTime();
		if(uad.addHeadIco(id,avatar_thumbs,avatar_big)){
			flag = "1";
			root.put("avatar_thumbs", avatar_thumbs);
			root.put("avatar_big", avatar_big);
		}else message = "头像修改失败";
		root.put("flag",flag);
		root.put("message",message);
		root.write(response.getWriter());
		return null;
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
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getW() {
		return w;
	}
	public void setW(Integer w) {
		this.w = w;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	

}

