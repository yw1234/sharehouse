package services.user;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.GetTime;
import util.SHA;

import dao.base.BaseDao;

public class UserServiceImpl implements UserService{

	BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private String fillSearchParams(Map params){
		StringBuffer sb = new StringBuffer();
		Object name = params.get("realname"),
				sex = params.get("sex"),
			  school = params.get("school"),
			province = params.get("province"),
				city = params.get("city"),
			department = params.get("department"),
				grade = params.get("grade");
		if(name!= null && !name.toString().equals(""))
			sb.append(" and (realname like '%"+name.toString()+"%' or nickname like '%"+name.toString()+"%') ");
		if(sex!= null && !sex.toString().equals("")){
			String s = sex.toString().equals("1")?"男":"女";
			sb.append(" and sex='"+s+"' ");
		}
		if(school!= null && !school.toString().equals(""))
			sb.append(" and school='"+school.toString()+"' ");
		if(province!= null && !province.toString().equals(""))
			sb.append(" and province='"+province.toString()+"' ");
		if(city!= null && !city.toString().equals(""))
			sb.append(" and city='"+city.toString()+"' ");
		if(department!= null && !department.toString().equals(""))
			sb.append(" and department='"+department.toString()+"' ");
		if(grade!= null && !grade.toString().equals(""))
			sb.append(" and hs_year='"+grade.toString()+"' ");
		return sb.toString();
	}
	
	private List searchUserMapping(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0 ; i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object []obj = (Object[]) list.get(i);
				map.put("id", obj[0]);
				map.put("name", obj[1]);
				map.put("pinyin", obj[2]);
				map.put("avatar", obj[3]);
				map.put("sex", obj[4]);
				map.put("school", obj[5]);
				map.put("department", obj[6]);
				map.put("grade", obj[7]);
				newList.add(map);
			}
		}
		return newList;
	}
	
	private List friendsMapping(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0 ; i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object []obj = (Object[]) list.get(i);
				map.put("id", obj[0]);
				map.put("name", obj[1]);
				map.put("avatar", obj[2]);
				map.put("sex", obj[3]);
				map.put("school", obj[4]);
				map.put("department", obj[5]);
				map.put("grade", obj[6]);
				map.put("is_pass", obj[7]);
				map.put("renren_binding", obj[8]);
				newList.add(map);
			}
		}
		return newList;
	}
	
	public Map<String, String> getUserIntroduce(Long userid) {
		// TODO Auto-generated method stub
		Map <String,String>map = new HashMap<String,String>();
		try{
			String sql = "select id,realname,head_ico_big,sex,school,department,hs_year,friend_number,send_active,educational,is_pass from sh_user where id="+userid;
			List list =  baseDao.getPageListBySQL(sql, 1, 0);
			if(list!=null&&list.size()>0)
			{
				Object []obj = (Object[]) list.get(0);
				map.put("userid", obj[0].toString());
				map.put("name", obj[1].toString());
				map.put("avatar", obj[2].toString());
				map.put("sex", obj[3].toString().equals("男")?"male":"female");
				map.put("school", obj[4].toString());
				map.put("department", obj[5].toString());
				map.put("grade", obj[6].toString());
				map.put("friendsCount", obj[7].toString());
				map.put("shareTimes", obj[8].toString());
				map.put("educational", obj[9]!=null?obj[9].toString():"");
				map.put("isPass", obj[10]!=null?obj[10].toString():"");				
			}else map = null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public boolean areFriends(Long userid, Long friendid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "select userid from friends where userid = "+userid+" and friendid = "+friendid;
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isExists(String email) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "select id from sh_user where email = '"+email+"'";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Map getUserInfo(Long userid,String fields) {
		// TODO Auto-generated method stub
		Map<String, Object> map = null;
		String [] field = fields.split(",");
		try{
			String sql = "select id,"+fields+" from sh_user where id = "+userid;
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

	public boolean updateUserInfo(Long userid, String keys, Map params) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String []field = keys.split(",");
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("update sh_user set ");
			for(int i = 0 ;i < field.length ; i++){
				sb.append(field[i]+"='"+params.get(field[i])+"',");
			}
			String sql = sb.toString();
			if(baseDao.execute(sql.substring(0,sql.length()-1)+" where id = "+userid)){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Long getUserId(String email) {
		// TODO Auto-generated method stub
		Long uid = null;
		try{
			String sql = "select id from sh_user where email = '"+email+"'";
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list != null && list.size()>0){
				uid = Long.parseLong(list.get(0).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return uid;
	}

	public Object getSearchUserCount(Long userid, Map params) {
		// TODO Auto-generated method stub
		Object obj = 0;
		try{
			String sql = "select count(id) from sh_user where id!="+userid+" and not exists(select userid from friends where userid="+userid+" and friendid=sh_user.id) "+fillSearchParams(params)+" order by sh_user.send_active desc";
			List list = baseDao.getPageListBySQL(sql,1,0);
			if(list != null && list.size()>0){
				obj = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	public List getSearchUser(Long userid, Map params, int size, int pn) {
		// TODO Auto-generated method stub
		List list = null;
		try{
			String sql = "select id,realname,nickname,head_ico,sex,school,department,hs_year from sh_user where id!="+userid+" and not exists(select userid from friends where userid="+userid+" and friendid=sh_user.id) "+fillSearchParams(params)+" order by sh_user.send_active desc";
			list = baseDao.getPageListBySQL(sql, size, pn);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchUserMapping(list);
	}
	
	public Object loginJudge(String userEmail, String userPassword) {
		// TODO Auto-generated method stub
		Object obj = null;
		try{
			String hql = "select u.id from User u where u.email='"+userEmail+"' and u.password='"+SHA.getSHA(userPassword)+"'";
			List list = baseDao.getPageList(hql, 0, 1);
			if(list!=null && list.size()>0)
				obj = list.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	public boolean isUserFreeze(String userEmail) {
		boolean flag = false;
		try{
			String sql = "select freeze from sh_user where email = '"+userEmail+"'";
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size() > 0){
				String j = (String) list.get(0);
				if(j.equals("1"))
					flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}


	public boolean updateLoginInfo(Long userid , String ip) {
		boolean flag = false;
		try{
			Date now = new Date();
			Map loginInfo = this.getUserInfo(userid,"continue_online,lastlogin_time");
			Date lt = (Date) loginInfo.get("lastlogin_time");
			Integer co = (Integer) loginInfo.get("continue_online");
			Integer update_co = co;
			if(GetTime.getTimeDifference(now,lt,"byHour",10) && !GetTime.getTimeDifference(now,lt,"byDay",1))
				update_co = 1 + co;
			else if(GetTime.getTimeDifference(now,lt,"byDay",1))
				update_co = 1;
			Map <String,String>params = new HashMap<String,String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String date = sdf.format(now);
			params.put("continue_online", update_co.toString());
			params.put("lastlogin_time", Timestamp.valueOf(date).toString());
			params.put("lastlogin_ip", ip);
			flag = this.updateUserInfo(userid, "continue_online,lastlogin_time,lastlogin_ip", params);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Map loginByCookie(String userid, String sessionid) {
		Map<String, Object> map = null;
		try{
			String hql = "select u.id,u.email,u.freeze from User u where u.id="+userid+" and u.autologin='"+sessionid+"'";
			List list = baseDao.getPageList(hql, 0, 1);
			if(list!=null && list.size()>0){
				Object []obj = (Object[]) list.get(0);
				map = new HashMap<String, Object>();
				map.put("userid", obj[0]);
				map.put("email", obj[1]);
				map.put("freeze", obj[2]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public boolean setSessionCookie(String uid, String sessionid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "update sh_user set autologin = '"+sessionid+"' where id ="+uid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public List getFriends(Long userid, int size, int pageno) {
		List list = null;
		try{
			String sql = "select id,realname,head_ico,sex,school,department,hs_year,is_pass,(select is_binding from renrenMapping where userid = sh_user.id) from sh_user where exists(select userid from friends where userid="+userid+" and friendid=sh_user.id) order by sh_user.send_active desc";
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return friendsMapping(list);
	}

	public boolean isAtUserExists(Long userid, String name,Long loggedUid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			List isExists = baseDao.getPageListBySQL("select id from friends as f inner join sh_user as u on u.id = f.friendid and u.realname = '"+name+"' and f.userid = "+loggedUid+" and f.friendid = "+userid);
			if(isExists != null && isExists.size() > 0){
				String sql = "update friends as f set f.at_times = f.at_times+1 where f.userid = "+loggedUid+" and f.friendid = "+userid;
				flag = baseDao.execute(sql);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
}
