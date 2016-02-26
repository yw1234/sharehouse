package activity.sellbook.service;

import java.util.List;
import java.util.Map;

import dao.base.BaseDao;

import activity.sellbook.model.BookingMessage;
import activity.sellbook.model.Books;

public interface BooksService {
	
	public BaseDao getBaseDao();

	public Map getDetails(Long id);
	
	public List getBooksList(Map params,int size,int pageno);
	
	public List getBookingList(Map params ,int size ,int pageno);
	
	public boolean sendBooks(Books book);
	
	public boolean addBooksPicture(Long id,String []picList);
	
	public String getBooksPictureList(Long id);
	
	public boolean addLookedTimes(Long userid,Long typeid);
	
	public Object getLookedTimes(Long bookid);
	
	public boolean sendBookingMessage(BookingMessage bm);
	
	public boolean updateBookInfo(Long bookid,String fields,Map params);
	
	public boolean updateBookingInfo(Long bookingid,String fields,Map params);
	
	public Map getBookInfo(Long bookid,String fields);
	
	public Object getBookingCount(Map params);
	
	public Object getBooksCount(Map params);
	
	public boolean deleteBooks(Long id);
	
	public boolean deleteBookingMessage(Long id,Long userid);
	
	public Map getBookingInfo(Long bookingid,Long userid,String fields);
	
	public String getBigBooksPicture(Long bookid);
}
