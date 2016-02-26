package dao.user;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.base.BaseDaoImpl;

public class UserRemoveDaoImpl extends BaseDaoImpl implements UserRemoveDao{

	public boolean removecircle(Long userid, Long circleid) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "delete from user_circle where userid=? and circleid=?";
			if(session.createSQLQuery(sql).setParameter(0, userid).setParameter(1, circleid).executeUpdate() == 1)
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

	public boolean removeConcernedFriends(Long userid, Long friendid) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update friends set concerned = '0' where userid = ? and friendid = ?";
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

}
