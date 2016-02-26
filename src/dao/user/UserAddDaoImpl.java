package dao.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.base.BaseDaoImpl;

public class UserAddDaoImpl extends BaseDaoImpl implements UserAddDao{

	public boolean addOperationTimes(String fieldName, Long typeid, String type) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update "+type+" set "+fieldName+"="+fieldName+"+1 where id="+typeid;
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addLookedTimes(Long userid, Long typeid, String type) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select userid from looktimes where userid = ? and typeid = ? and type = ?";
			List look = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1, typeid).setParameter(2,type.trim()).list();
			if(look == null || look.size() <= 0){
				String insert = "insert into looktimes (userid,typeid,type) values(?,?,?)";
				session.createSQLQuery(insert).setParameter(0,userid).setParameter(1, typeid).setParameter(2,type.trim()).executeUpdate();
				session.createSQLQuery("update "+type.trim()+" set lookedtimes=lookedtimes+1 where id = ?").setParameter(0,typeid).executeUpdate();
				flag=true;
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}
	
	public boolean addHeadIco(Long userid ,String ico_path,String ico_path_big) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update sh_user set head_ico=?,head_ico_big=? where id = ?";
			int i = session.createSQLQuery(sql).setParameter(0,ico_path).setParameter(1,ico_path_big).setParameter(2,userid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addFriends(Long userid, Long friendid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			Date d = new Date();
			String sql1 = "insert into friends(userid,friendid,last_vistedtime) values(?,?,?)";
			String sql2 = "insert into friends(userid,friendid,last_vistedtime) values(?,?,?)";
			int i = session.createSQLQuery(sql1).setParameter(0,userid).setParameter(1,friendid).setParameter(2, d).executeUpdate();
			int j =	session.createSQLQuery(sql2).setParameter(0,friendid).setParameter(1,userid).setParameter(2, d).executeUpdate();
			if(i == 1 && j == 1){
				flag = true;
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addConcernedCircle(Long userid, Long circleid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update user_circle set isconcerned = '1' where userid = ? and circleid = ?";
			int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,circleid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addVisited(Long userid, Long visitedid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String rex = "select userid from last_visited where userid = ? and visitedid = ?";
			List rexl = session.createSQLQuery(rex).setParameter(0,userid).setParameter(1,visitedid).list();
			if(rexl!=null && rexl.size()>0)
			{
				String sql = "update last_visited set visitedtime=? where userid = ? and visitedid = ?";
				int i = session.createSQLQuery(sql).setParameter(0,new Date()).setParameter(1,userid).setParameter(2,visitedid).executeUpdate();
				if(i == 1)
					flag = true;
			}else{
				String sql = "insert into last_visited(userid,visitedid,visitedtime) values(?,?,?)";
				int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,visitedid).setParameter(2, new Date()).executeUpdate();
				if(i == 1)
					flag = true;
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addCircleSendNumber(String[] cid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update circle set sendNumber=sendNumber+1 where id in (";
			for(int i = 0 ; i < cid.length ; i++){
				sql += cid[i]+",";
			}
			sql = sql.substring(0, sql.length()-1);
			sql+=")";
			int i = session.createSQLQuery(sql).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addSendNumberInCircle(Long userid, String[] cid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update user_circle set send_active_user=send_active_user+1 where userid=? and circleid in (";
			for(int i = 0 ; i < cid.length ; i++){
				sql += cid[i]+",";
			}
			sql = sql.substring(0, sql.length()-1);
			sql+=")";
			int i = session.createSQLQuery(sql).setParameter(0,userid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public Map<String,Object> addCircleAtRegister(Long userid, String circlename) {
		Session session = null;
		Transaction tran = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			session = getsession();
			tran = session.beginTransaction();
			String find = "select id,name,circle_ico from circle where name = ? limit 1";
			List l = session.createSQLQuery(find).setParameter(0,circlename).list();
			if(l != null && l.size()>0){
				Object[]obj = (Object[]) l.get(0);
				map.put("id",obj[0]);
				map.put("name",obj[1]);
				map.put("ico",obj[2]);
				String insert = "insert into user_circle(circleid,userid,checktimes) values(?,?,1)";
				session.createSQLQuery(insert).setParameter(0,obj[0]).setParameter(1,userid).executeUpdate();
				String update = "update circle set member_count=member_count+1 where id = ?";
				session.createSQLQuery(update).setParameter(0,obj[0]).executeUpdate();
				String updateu = "update sh_user set circle_number=circle_number+1 where id = ?";
				session.createSQLQuery(updateu).setParameter(0,userid).executeUpdate();
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return map;
	}

	public boolean addShowPriCircle(Long userid, String clist) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String del = "delete from show_privacy_circle where userid = ?";
			session.createSQLQuery(del).setParameter(0,userid).executeUpdate();
			int i = 0;
			if(!clist.equals("")){
				String sql = "insert into show_privacy_circle(userid,circleid) select ?,id from circle where id in("+clist+")";
				i = session.createSQLQuery(sql).setParameter(0,userid).executeUpdate();
			}
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}
	
	public boolean addShowPriUser(Long userid, String ulist) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String del = "delete from show_privacy_friends where userid = ?";
			session.createSQLQuery(del).setParameter(0,userid).executeUpdate();
			int i = 0;
			if(!ulist.equals("")){
				String sql = "insert into show_privacy_friends(userid,friendid) select ?,id from sh_user where id in("+ulist+")";
				i = session.createSQLQuery(sql).setParameter(0,userid).executeUpdate();
			}
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addConcernedFriends(Long userid, Long friendid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update friends set concerned = '1' where userid = ? and friendid = ?";
			int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,friendid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addFavor(Long userid, Long typeid, String type) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "insert into favor(userid,typeid,type) values(?,?,?)";
			int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,typeid).setParameter(2,type).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addHotSpot(Long typeid,String type, Integer number) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update "+type+" set hotspot=hotspot+"+number+" where id=?";
			int i = session.createSQLQuery(sql).setParameter(0,typeid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	public boolean addPmHidden(Long pmid, Long userid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "insert into pm_hidden(pmid,userid) values(?,?)";
			int i = session.createSQLQuery(sql).setParameter(0,pmid).setParameter(1,userid).executeUpdate();
			if(i == 1)
				flag = true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

}
