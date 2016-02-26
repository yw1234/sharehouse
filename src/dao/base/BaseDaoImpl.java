package dao.base;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.Circle;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.user.UserDeleteDao;
import dao.user.UserJoinDao;
import dao.user.UserRemoveDao;

import util.GetAC;
import util.SHA;



public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{

	public Session getsession()
	{
		return this.getSession();
	}
	// 增加或修改的方法
	public boolean saveOrUpdate(Object object) {
		try {
			Session session = getsession();
			Transaction tran = session.beginTransaction();
			session.saveOrUpdate(object);
			tran.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	//获取
	@SuppressWarnings("unchecked")
	public Object get(Class objectClass, Serializable id) {
		Session session = getsession();
		Object object = session.get(objectClass, id);
		session.close();
		return object;
	}

	//删除
	@SuppressWarnings("unchecked")
	public boolean delete(Class objectClass, Serializable id) {
		try {
			Session session = getsession();
			Transaction tran = session.beginTransaction();
			Object object = session.get(objectClass, id);
			session.delete(object);
			tran.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	//获取全部
	public List<?> findAll(Class<?> objectClass) {
		try {
			List<?> list;
			Session session = getsession();
			String hql = "from " + objectClass.getName();
			list = session.createQuery(hql).list();
			session.close();
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	//分页查找,带参数
	@SuppressWarnings("unchecked")
	public List<?> getPageList(String hql, Object[] params, int pageNo,
			int pageSize) {
		Session session = getsession();
		Query query = session.createQuery(hql);
		List list = null;
		if(pageNo < 0 && pageSize < 0)
		{
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			} 
			list = query.list();
			session.close();
		}
		else{
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			} 
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			list = query.list();
			session.close();
		}
		
		return list;
	}

	//分页查找
	@SuppressWarnings("unchecked")
	public List<?> getPageList(String hql, int pageNo, int pageSize) {
		Session session = getsession();
		Query query = session.createQuery(hql);
		List list = null;
		if(pageNo < 0 && pageSize < 0)
		{
			list = query.list();
			session.close();
		}
		else{
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			list = query.list();
			session.close();
		}
		return list;
	}
	
	public List<?> getPageListBySQL(String sql, int pageSize,int pageNo) {
		Session session = getsession();
		Query query = session.createSQLQuery(sql);
		List list = null;
		if(pageNo < 0 && pageSize < 0)
		{
			list = query.list();
			session.close();
		}
		else{
			query.setFirstResult(pageNo * pageSize);
			query.setMaxResults(pageSize);
			list = query.list();
			session.close();
		}
		return list;
	}
	
	public List getPageListBySQL(String sql) {
		Session session = getsession();
		Query query = session.createSQLQuery(sql);
		List list = null;
		list = query.list();
		session.close();
		return list;
	}
	
	public boolean execute(String sql) {
		boolean flag = false;
		try {
			Session session = getsession();
			Transaction tran = session.beginTransaction();
			int i = session.createSQLQuery(sql).executeUpdate();
			if(i==1)
				flag = true;
			tran.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	
}