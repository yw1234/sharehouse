package activity.sellbook.service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.BeanHelper;
import util.GetTime;

import model.Picture;

import activity.sellbook.model.BookLookedTimes;
import activity.sellbook.model.BookingMessage;
import activity.sellbook.model.Books;

import dao.base.BaseDao;

public class BooksServiceImpl implements BooksService {

	BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private List booksListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("id",obj[0]);
				map.put("userid",obj[1]);
				map.put("name",obj[2]);
				map.put("bookType",obj[3]);
				map.put("sendTime",GetTime.getSendTime((Date)obj[4]));
				map.put("firstImg",obj[5]);
				map.put("content",obj[6]);
				map.put("realPrice",obj[7]);
				map.put("price",obj[8]);
				map.put("stock",obj[9]);
				map.put("scope",obj[10]);
				map.put("oldDegree",obj[11]);
				newList.add(map);
			}	
		}
		return newList;
	}
	
	private List bookingListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("id",obj[0]);
				map.put("userid",obj[1]);
				map.put("bookid",obj[2]);
				map.put("bookingType",obj[3]);
				map.put("bookingTime",GetTime.getSendTime((Date)obj[4]));
				map.put("bookName",obj[5]);
				map.put("bookOldDegree",obj[6]);
				map.put("bookStock",obj[7]);
				map.put("totalPrice",obj[8]);
				map.put("phone",obj[9]);
				map.put("contacts",obj[10]);
				map.put("campus",obj[11]);
				map.put("dorm",obj[12]);
				map.put("dormNumber",obj[13]);
				map.put("count",obj[14]);
				map.put("getTime",obj[15]);
				map.put("content",obj[16]);
				map.put("isComplete",obj[17]);
				map.put("tinyImage",obj[18]);
				newList.add(map);
			}	
		}
		return newList;
	}
	
	private String fillBooksParamsByAdmin(Map params){
		StringBuffer sql = new StringBuffer();
		Object key = params.get("key");
		if(key!=null && !key.toString().equals("")){
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(key.toString());
			if(isNum.matches())
				sql.append(" and b.id = "+key);
			else{
				String keyString = key.toString();
				StringBuffer keysplit = new StringBuffer();
				for(int i = 0 ; i < key.toString().length();i++){
					keysplit.append(keyString.substring(i,i+1)+"%");
				}
				keyString = keysplit.toString().equals("")?keyString:keysplit.toString().substring(0,keysplit.length()-1);
				sql.append(" and b.name like '%"+keyString+"%'");
			}
		}
		
		return sql.toString();
	}
	
	private String fillBooksParamsByUser(Map params){
		StringBuffer sql = new StringBuffer();
		Object key = params.get("key");
		if(key!=null && !key.equals(""))
		{
			String keyString = key.toString();
			StringBuffer keysplit = new StringBuffer();
			for(int i = 0 ; i < key.toString().length();i++){
				keysplit.append(keyString.substring(i,i+1)+"%");
			}
			keyString = keysplit.toString().equals("")?keyString:keysplit.toString().substring(0,keysplit.length()-1);
			sql.append(" and b.name like '%"+keyString+"%'");
		}else{
			Object stock = params.get("stock")
				,type = params.get("type")
				,scope = params.get("scope")
				,old_degree = params.get("old_degree")
				,price_level = params.get("price_level");
			if(stock!=null&&!stock.toString().equals(""))
				sql.append(" and b.stock > 0");
			if(type!=null&&!type.toString().equals(""))
				sql.append(" and b.type='"+type+"'");
			if(scope!=null&&!scope.toString().equals(""))
				sql.append(" and b.scope='"+scope+"'");
			if(old_degree!=null&&!old_degree.toString().equals(""))
				sql.append(" and b.old_degree='"+old_degree+"'");
			if(price_level!=null&&!price_level.toString().equals("")){
				String []price = price_level.toString().split("-");
				sql.append(" and b.price between "+price[0]+" and "+price[1]);
			}		
		}
		return sql.toString();
	}
	
	private String fillBookingParams(Map params){
		StringBuffer sql = new StringBuffer();
		Object key = params.get("key") , 
			bookingType = params.get("bookingType"),
			campus = params.get("campus"),
			isComplete = params.get("isComplete"),
			uid = params.get("uid");
		if(key != null && !key.toString().equals("")){
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(key.toString());
			if(isNum.matches())
				sql.append(" and bm.id = "+key);
			else{
				sql.append(" and bm.bookerName like '%"+key+"%'");
			}
		}else{
			if(uid != null && !uid.toString().equals("")){
				sql.append(" and bm.userid = "+uid);
			}
			if(bookingType != null && !bookingType.toString().equals("")){
				sql.append(" and bm.bookingType = '"+bookingType+"'");
			}
			if(campus != null && !campus.toString().equals("")){
				sql.append(" and bm.campus = '"+campus+"'");
			}
			if(isComplete != null && !isComplete.toString().equals("")){
				sql.append(" and bm.isComplete = '"+isComplete+"'");
			}
		}
		
		return sql.toString();
	}
	
	public Map getDetails(Long id) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			String hql = "from Books where id = "+id;
			List list = baseDao.getPageList(hql, 0,1);
			if(list != null && list.size()>0){
				Books book = (Books) list.get(0);
				map = BeanHelper.convertBeanToMap(book);
				map.put("id",id);
				map.put("content", book.getContent());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public boolean sendBooks(Books book) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(book);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public boolean addBooksPicture(Long id, String[] picList) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			for(int i = 0 ; i < picList.length;i++)
			{
				if(!picList[i].equals("") && picList[i] != ""){
					Picture p = new Picture();
					p.setType("activity_sellbook");
					p.setTypeid(id);
					p.setThumbs_url(picList[i]);
					Integer index = picList[i].lastIndexOf(".");
					p.setUrl(picList[i].substring(0,index-7)+picList[i].substring(index));
					p.setTiny_url(picList[i].substring(0,index-7)+"-tiny"+picList[i].substring(index));
					baseDao.saveOrUpdate(p);
				}
			}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public List getBooksList(Map params, int size, int pageno) {
		// TODO Auto-generated method stub
		List booksList = null;
		String fields = "b.id," +
					"b.userid," +
					"b.name," +
					"b.type," +
					"b.add_time," +
					"(select thumbs_url from picture where typeid=b.id and type ='activity_sellbook' limit 1)," +
					"b.content," +
					"b.realPrice,"+
					"b.price,"+
					"b.stock," +
					"b.scope," +
					"b.old_degree";
		try{
			String search = params.get("search").toString();
			String sql = "select "+fields+" from activity_sellbook_books as b where b.id!=0 "+(search.equals("admin")?fillBooksParamsByAdmin(params):fillBooksParamsByUser(params))+" order by b.add_time desc";
			booksList = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return booksListToMap(booksList);
	}
	
	public List getBookingList(Map params, int size, int pageno) {
		List bookingList = null;
		String fields = "bm.id,"+
					"bm.userid,"+
					"bm.bookid,"+
					"bm.bookingType,"+
					"bm.bookingTime,"+
					"b.name,"+
					"b.old_degree,"+
					"b.stock,"+
					"bm.totalPrice,"+
					"bm.bookerContact,"+
					"bm.bookerName,"+
					"bm.campus,"+
					"bm.dorm,"+
					"bm.dormNumber,"+
					"bm.count,"+
					"bm.getTime,"+
					"bm.content,"+
					"bm.isComplete,"+
					"(select tiny_url from picture where typeid=b.id and type ='activity_sellbook' limit 1)";
		try{
			String sql = "select "+fields+" from activity_sellbook_bookingMessage as bm inner join activity_sellbook_books as b on (b.id = bm.bookid "+fillBookingParams(params)+") order by b.add_time desc";
			bookingList = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return bookingListToMap(bookingList);
	}
	
	public String getBooksPictureList(Long id) {
		// TODO Auto-generated method stub
		String piclist = "";
		try{
			String sql = "select url from picture where type='activity_sellbook' and typeid = "+id;
			List list = baseDao.getPageListBySQL(sql, 6,0);
			if(list!=null && list.size()>0){
				for(int i = 0 ; i < list.size(); i++){
					piclist += list.get(i).toString()+",";
				}
				piclist = piclist.substring(0, piclist.length()-1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return piclist;
	}
	public boolean addLookedTimes(Long userid, Long typeid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String isLooked = "select userid from BookLookedTimes where userid = "+userid+" and bookid = "+typeid;
			List list = baseDao.getPageList(isLooked, 0, 1);
			if(list==null || list.size()<=0){
				String insert = "insert into activity_sellbook_bookLookedTimes(userid,bookid) values("+userid+","+typeid+")";
				flag = baseDao.execute(insert);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public Object getLookedTimes(Long bookid) {
		// TODO Auto-generated method stub
		Object obj = 0;
		try{
			String hql = "select count(userid) from BookLookedTimes where bookid = "+bookid;
			List list = baseDao.getPageList(hql, 0, 1);
			if(list!= null && list.size()>0){
				obj = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	public boolean sendBookingMessage(BookingMessage bm) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(bm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public boolean updateBookInfo(Long bookid, String fields, Map params) {
		boolean flag = false;
		String []field = fields.split(",");
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("update activity_sellbook_books set ");
			for(int i = 0 ;i < field.length ; i++){
				sb.append(field[i]+"='"+params.get(field[i])+"',");
			}
			String sql = sb.toString();
			if(baseDao.execute(sql.substring(0,sql.length()-1)+" where id = "+bookid)){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public Map getBookInfo(Long bookid, String fields) {
		Map<String, Object> map = null;
		String [] field = fields.split(",");
		try{
			String sql = "select id,"+fields+" from activity_sellbook_books where id = "+bookid;
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0){
				map = new HashMap<String, Object>();
				Object []obj = (Object[]) list.get(0);
				for(int i = 1 ; i < obj.length ;i++){
					map.put(field[i-1], obj[i]);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public boolean updateBookingInfo(Long bookingid, String fields, Map params) {
		boolean flag = false;
		String []field = fields.split(",");
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("update activity_sellbook_bookingMessage set ");
			for(int i = 0 ;i < field.length ; i++){
				sb.append(field[i]+"='"+params.get(field[i])+"',");
			}
			String sql = sb.toString();
			if(baseDao.execute(sql.substring(0,sql.length()-1)+" where id = "+bookingid)){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public Object getBookingCount(Map params) {
		Object obj = null;
		try{
			String sql = "select count(1) from activity_sellbook_bookingMessage as bm where bm.id!=0 "+fillBookingParams(params);
			List list = baseDao.getPageListBySQL(sql);
			if(list!=null&&list.size()>0)
				obj = list.get(0);
			else obj = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	public Object getBooksCount(Map params) {
		Object obj = null;
		try{
			String search = params.get("search").toString();
			String sql = "select count(1) from activity_sellbook_books as b where b.id!=0 "+(search.equals("admin")?fillBooksParamsByAdmin(params):fillBooksParamsByUser(params));
			List list = baseDao.getPageListBySQL(sql);
			if(list!=null&&list.size()>0)
				obj = list.get(0);
			else obj = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public boolean deleteBooks(Long id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.delete(Books.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public boolean deleteBookingMessage(Long id,Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "delete from activity_sellbook_bookingMessage where id = "+id+" and userid = "+userid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public Map getBookingInfo(Long bookingid, Long userid ,String fields) {
		Map<String, Object> map = null;
		String [] field = fields.split(",");
		try{
			String sql = "select id,"+fields+" from activity_sellbook_bookingMessage where id = "+bookingid;
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0){
				map = new HashMap<String, Object>();
				Object []obj = (Object[]) list.get(0);
				for(int i = 1 ; i < obj.length ;i++){
					map.put(field[i-1], obj[i]);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	public String getBigBooksPicture(Long bookid) {
		// TODO Auto-generated method stub
		String url = "";
		try{
			String sql = "select url from picture where type = 'activity_sellbook' and typeid = "+bookid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size() > 0)
			{
				url = list.get(0).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	

}
