package activity.sellbook.action;

import java.io.IOException;
import java.util.Date;
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
import util.WordsFilter;

import activity.sellbook.model.BookingMessage;
import activity.sellbook.service.BooksService;

import com.opensymphony.xwork2.ActionSupport;

public class BookingAction extends ActionSupport{

	/**预定相关**/
	private Long id;
	private Long bookid;
	private Integer count;
	private String bookingType;
	private String getTime;
	private String dorm;
	private String campus;
	private String dormNumber;
	private String bookerName;
	private String bookerContact;
	private String content;						//详情
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpServletRequest request = ServletActionContext.getRequest();
	private Integer feedsSize = StaticInfo.feedsNumber;
	private Integer pageno;
	
	public void booking() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Long userid = (Long)request.getSession().getAttribute("userid");
			Map m = bs.getBookInfo(bookid, "stock,price,requiredtimes");
			Integer st = Integer.parseInt(m.get("stock").toString());
			Integer p = Integer.parseInt(m.get("price").toString());
			Integer rt = Integer.parseInt(m.get("requiredtimes").toString());
			Integer tip = 0;
			if(st != 0){
				BookingMessage bm = new BookingMessage();
				bm.setBookerContact(bookerContact);
				bm.setBookerName(bookerName);
				bm.setBookid(bookid);
				bm.setUserid(userid);
				bm.setBookingTime(new Date());
				bm.setBookingType(bookingType);
				bm.setCampus(campus);
				bm.setCount(count);
				bm.setDorm(dorm);
				bm.setDormNumber(dormNumber);
				bm.setContent(WordsFilter.imgFilter(content));
				bm.setGetTime(getTime);
				bm.setTotalPrice(count*p+tip);
				if(bs.sendBookingMessage(bm)){
					m.put("requiredtimes", rt+1);
					bs.updateBookInfo(bookid, "requiredtimes", m);
					UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
					Map map = new HashMap();
					map.put("phone", bookerContact);
					us.updateUserInfo(userid, "phone", map);
					flag = "1";
				}else message = "预定失败,请稍后再试";
			}else message = "没有库存了...";
		}catch(Exception e){
			e.printStackTrace();
			message = "预定失败,请稍后再试";
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	}
	
	
	public void complete() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		JSONObject root = new JSONObject();
		String flag = "0",message = "";
		try{
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Map map = new HashMap();
			map.put("isComplete","1");
			if(bs.updateBookingInfo(id, "isComplete", map)){
				flag = "1";
				Map m = bs.getBookInfo(bookid, "stock");
				Integer st = Integer.parseInt(m.get("stock").toString());
				Map mm = new HashMap();
				mm.put("stock", (st - count >= 0)?st - count:0);
				bs.updateBookInfo(bookid, "stock", mm);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "操作失败,请稍后再试";
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
			Long userid = (Long) request.getSession().getAttribute("userid");
			BooksService bs = (BooksService) GetAC.getAppContext().getBean("BooksService");
			Map bookingInfo = bs.getBookingInfo(id, userid, "count,bookid");
			Long bookid = Long.parseLong(bookingInfo.get("bookid").toString());
			if(bs.deleteBookingMessage(id, userid)){
				Map<String, Integer> map = new HashMap<String, Integer>();
				Map bookInfo = bs.getBookInfo(bookid, "requiredtimes");
				Object reqTimes = bookInfo.get("requiredtimes");
				//录入
				map.put("requiredtimes", (Integer.parseInt(reqTimes.toString()) > 0)?Integer.parseInt(reqTimes.toString())-1:0);
				bs.updateBookInfo(bookid , "requiredtimes",map);
				flag = "1";
			}else message = "不能删除他人的订单";
		}catch(Exception e){
			e.printStackTrace();
			message = "操作失败,请稍后再试";
		}
		root.put("message",message);
		root.put("flag", flag);
		root.write(response.getWriter());
	} 
	
	public Long getBookid() {
		return bookid;
	}
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	public String getDorm() {
		return dorm;
	}
	public void setDorm(String dorm) {
		this.dorm = dorm;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getDormNumber() {
		return dormNumber;
	}
	public void setDormNumber(String dormNumber) {
		this.dormNumber = dormNumber;
	}
	public String getBookerName() {
		return bookerName;
	}
	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}
	public String getBookerContact() {
		return bookerContact;
	}
	public void setBookerContact(String bookerContact) {
		this.bookerContact = bookerContact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
