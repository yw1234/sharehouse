package dao.user;

import java.util.Date;
import java.util.List;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.SHA;

import dao.base.BaseDaoImpl;

public class UserUpdateDaoImpl extends BaseDaoImpl implements UserUpdateDao {

	public boolean updateLookingMessage(Long userid) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update user_message set islooked = '1' where userid = ?";
			if(session.createSQLQuery(sql).setParameter(0, userid).executeUpdate() == 1)
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

	public boolean updateUserInfo(User u) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			if(this.saveOrUpdate(u)){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateUserPrivacy(User u) {
		boolean flag = false;
		try{
			if(this.saveOrUpdate(u)){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateBlackList(Long userid, Long friendid,String flag) {
		boolean fl = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List l = session.createSQLQuery("select userid from friends where userid=? and friendid=?").setParameter(0,userid).setParameter(1,friendid).list();
			if(l!=null && l.size()>0){
				String sql = "update friends set isignore='"+flag+"' where userid=? and friendid=?";
				session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,friendid).executeUpdate();
			}
			if(flag.equals("1"))
				session.createSQLQuery("insert into notes_hidden(userid,hiddenid) values(?,?)").setParameter(0,userid).setParameter(1,friendid).executeUpdate();
			else session.createSQLQuery("delete from notes_hidden where userid=? and hiddenid = ?").setParameter(0,userid).setParameter(1,friendid).executeUpdate();
			tran.commit();
			fl = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return fl;
	}

	public boolean updateIsShared(Long typeid,String type) {
		boolean fl = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List list = session.createSQLQuery("select is_shared from "+type+" where id = "+typeid).list();
			if(list!=null && list.size()>0)
			{
				String is_shared = list.get(0).toString();
				String sql = "update "+type+" set is_shared='"+(is_shared.trim().equals("1")?"0":"1")+"' where id = ?";
				int i = session.createSQLQuery(sql).setParameter(0,typeid).executeUpdate();
				if(i == 1){
					fl=true;
				}
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return fl;
	}

	public boolean updateLastSendTime(Long userid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update sh_user set lastsendtime=? where id = ?";
			if(session.createSQLQuery(sql).setParameter(0,new Date()).setParameter(1, userid).executeUpdate() == 1)
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

	public boolean updatePassword(Long userid,String newPwd) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update sh_user set password=? where id = ?";
			if(session.createSQLQuery(sql).setParameter(0,newPwd).setParameter(1, userid).executeUpdate() == 1)
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

	public boolean updateNewUser(Long userid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update sh_user set isNewUser = '0' where id = ?";
			if(session.createSQLQuery(sql).setParameter(0, userid).executeUpdate() == 1)
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

	public boolean updateLoginInfo(Long userid, String email, String pwd) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List list = session.createSQLQuery("select id from sh_user where email = '"+email+"'").list();
			if(list == null || list.size()<=0){
				String sql = "update sh_user set email = '"+email+"',password = '"+SHA.getSHA(pwd)+"' where id = ?";
				if(session.createSQLQuery(sql).setParameter(0, userid).executeUpdate() == 1)
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
}
