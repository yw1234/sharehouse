package activity.sellbook.action;

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

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import services.user.UserService;
import util.GetAC;
import util.ImageScale;
import util.LocalName;
import util.StaticInfo;
import util.WordsFilter;

import activity.sellbook.Template;
import activity.sellbook.model.BookingMessage;
import activity.sellbook.model.Books;
import activity.sellbook.service.BooksService;

import com.opensymphony.xwork2.ActionSupport;

public class BooksAction extends ActionSupport{

	private Long id;
	
	/**上传相关**/
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String uploadDir;
	private String callBackFunc;
	private String uploadType;
	
	/**发布相关**/
	private String piclist;						//书名
	private String book;							//书名
	private Integer price;						//价格
	private Integer realPrice;					//原价
	private Integer stock;						//库存
	private String old_degree;					//新旧程度
	private String type;							//大类
	private String scope;						//范围
	private String content;						//详情
	
	/**筛选相关**/
	private String searchType;
	private String price_level;
	private String key;
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	private Integer feedsSize = 16;
	private Integer pageno;
	
	public void getBooks() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Map params = new HashMap();
			params.put("search", "user");
			if(searchType.equals("key")){
				params.put("key", key);
			}else{
				params.put("stock", stock);
				params.put("type", type);
				params.put("scope", scope);
				params.put("old_degree", old_degree);
				params.put("price_level",price_level);
			}
			List list = bs.getBooksList(params, feedsSize, pageno);
			if(list!=null && list.size()>0){
				root.put("data", Template.sellBooksNotes(list));
				root.put("hasNext", list.size()>=feedsSize?"1":"0");
			}
			else root.put("data", "<div style='float:left;width:100%;margin-top:30px;'>没有找到相关的图书</div>");
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "图书信息获取失败,请稍后再试";
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	
	public void getBookDetails() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			bs.addLookedTimes((Long)ServletActionContext.getRequest().getSession().getAttribute("userid"), id);
			root.put("details",bs.getDetails(id));
			root.put("urlList",bs.getBooksPictureList(id));
			root.put("lookedTimes",bs.getLookedTimes(id));
			flag = "1";
		}catch(Exception e){
			e.printStackTrace();
			message = "图书信息获取失败,请稍后再试";
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	

	public void uploadBookImage() throws IOException{
		boolean tag = false;
		String newfilename = null;
		String newfilename_thumbs = null;
		String newfilename_tiny = null;
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
			newfilename_tiny = now + "-tiny" + fileFileName.substring(index);
		}
		else{
			newfilename = now;
			newfilename_thumbs = now + "-thumbs";
			newfilename_tiny = now + "-tiny";
		}
		String thu = path+File.separator+newfilename_thumbs;
		String tiny = path+File.separator+newfilename_tiny;
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
			ImageScale.reduceImg(path,580);
		}catch(Exception e){e.printStackTrace();}
		//小图
		try{
			ImageScale.copy(path, thu);
			ImageScale.reduceImg(thu,210);
		}catch(Exception e){e.printStackTrace();}
		//缩略
		try{
			ImageScale.copy(path, tiny);
			ImageScale.reduceImg(tiny,120);
		}catch(Exception e){e.printStackTrace();}
		String callback = "";
		String url = (StaticInfo.ImgURL+"/"+uploadDir+"/"+year.toString()+"/"+month.toString()+"/"+day.toString()+"/"+newfilename_thumbs).trim();
		if(uploadType==null || uploadType.equals("common")){
			if(tag){
				callback = "<script>parent.upload.callBack({flag:'1',func:function(){parent."+callBackFunc+"({url:\""+url+"\"});}});</script>";  
			}else callback = "<script>parent.upload.callBack({flag:'0'});</script>";  
		}else if(uploadType.equals("mtxx")){
			//mtxx
			if(tag){
				callback = "upload.callBack({flag:'1',func:function(){"+callBackFunc+"({url:\""+url+"\"});}});";  
			}else callback = "upload.callBack({flag:'0'})";  
		}
		response.getWriter().println(callback);
	}
	
	public void send() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		String [] pic = piclist.split(",");
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute("userid");
		try{
			Books books = new Books();
			books.setAdd_time(new Date());
			books.setContent(WordsFilter.imgFilter(content));
			books.setName(book);
			books.setPrice(price);
			books.setOld_degree(old_degree);
			books.setRealPrice(realPrice);
			books.setUserid(userid);
			books.setScope(scope);
			books.setStock((stock!=null)?stock:0);
			books.setType(type);
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			bs.sendBooks(books);
			Long bookid = books.getId();
			if(bookid != null && bs.addBooksPicture(bookid, pic))
			{
				flag = "1";
			}else{
				message = "发布失败,请稍后再试";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "发布失败,请稍后再试";
		}
		
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	
	public void update() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Books books = (Books) bs.getBaseDao().get(Books.class,id);
			books.setContent(WordsFilter.imgFilter(content));
			books.setName(book);
			books.setPrice(price);
			books.setOld_degree(old_degree);
			books.setRealPrice(realPrice);
			books.setScope(scope);
			books.setStock(stock);
			books.setType(type);
			if(bs.sendBooks(books)){
				flag = "1";
			}else message = "修改失败,请稍后再试";
		}catch(Exception e){
			e.printStackTrace();
			message = "修改失败,服务器出错";
		}
		
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	
	public void delete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			if(bs.deleteBooks(id)){
				flag = "1";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败,请稍后再试";
		}
		
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	
	public void showBigPicture() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			String url = bs.getBigBooksPicture(id);
			if(!url.equals("")){
				flag = "1";
				root.put("url",url);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "查看失败,请稍后再试";
		}
		
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
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

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Integer realPrice) {
		this.realPrice = realPrice;
	}

	public String getPiclist() {
		return piclist;
	}

	public void setPiclist(String piclist) {
		this.piclist = piclist;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getOld_degree() {
		return old_degree;
	}

	public void setOld_degree(String old_degree) {
		this.old_degree = old_degree;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFeedsSize() {
		return feedsSize;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public String getPrice_level() {
		return price_level;
	}

	public void setPrice_level(String price_level) {
		this.price_level = price_level;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	
}
