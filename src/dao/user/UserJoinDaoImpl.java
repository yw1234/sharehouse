package dao.user;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Circle;
import model.User;
import dao.base.BaseDaoImpl;

public class UserJoinDaoImpl extends BaseDaoImpl implements UserJoinDao{

	public boolean joincircle(Long userid, String []circleid) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			if(circleid.length > 0 && circleid != null){
				for(int i = 0 ;i < circleid.length ; i++){
					String sql = "insert into user_circle(userid, circleid,checktimes) values (?, ?,2)";
					session.createSQLQuery(sql).setParameter(0, userid).setParameter(1, Long.parseLong(circleid[i])).executeUpdate();
					String updatec = "update circle set member_count=member_count+1 where id = ?";
					session.createSQLQuery(updatec).setParameter(0,Long.parseLong(circleid[i])).executeUpdate();
					String updateu = "update sh_user set circle_number=circle_number+1 where id = ?";
					session.createSQLQuery(updateu).setParameter(0,userid).executeUpdate();
				}
			}
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

	public boolean isJoined(Long userid, Long circleid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select userid from user_circle where userid = ? and circleid = ?";
			List l = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,circleid).list();
			if(l != null && l.size() > 0)
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
