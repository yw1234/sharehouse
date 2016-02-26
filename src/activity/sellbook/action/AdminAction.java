package activity.sellbook.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import util.GetAC;
import util.StaticInfo;

import activity.sellbook.service.BooksService;

import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport{

	private Long id;
	
	/**订单筛选**/
	private String bookingType;
	private String campus;
	private String isComplete;
	
	/**图书筛选**/
	private String key;
	
	private String page;
	private Integer p;
	private Integer msgSize = StaticInfo.feedsNumber;
	
	protected HttpSession session = ServletActionContext.getRequest().getSession();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	
	public String init() throws UnsupportedEncodingException{
		BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
		p = (p!=null)?p:1;
		if(page==null || page.equals("books")){
			key = (key!=null)?java.net.URLDecoder.decode(key,"UTF8"):"";
			Map params = new HashMap();
			params.put("search", "admin");
			if(!key.equals("")){
				params.put("key", key);
			}else key = "";
			List list = bs.getBooksList(params, msgSize, p-1);
			request.setAttribute("booksList", list);
			request.setAttribute("booksCount", bs.getBooksCount(params));
		}else if(page.equals("booking")){
			bookingType = (bookingType!=null)?java.net.URLDecoder.decode(bookingType,"UTF8"):"";
			campus = (campus!=null)?java.net.URLDecoder.decode(campus,"UTF8"):"";
			key = (key!=null)?java.net.URLDecoder.decode(key,"UTF8"):"";
			Map params = new HashMap();
			if(!key.equals("")){
				params.put("key", key);
			}else{
				params.put("bookingType",bookingType);
				params.put("campus", campus);
				params.put("isComplete", isComplete);
			}
			List list = bs.getBookingList(params, msgSize, p-1);
			request.setAttribute("bookingList", list);
			request.setAttribute("bookingCount", bs.getBookingCount(params));
		}
		return SUCCESS;
	}
	
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getMsgSize() {
		return msgSize;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBookingType() {
		return bookingType;
	}


	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}


	public String getCampus() {
		return campus;
	}


	public void setCampus(String campus) {
		this.campus = campus;
	}


	public String getIsComplete() {
		return isComplete;
	}


	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
	
}
