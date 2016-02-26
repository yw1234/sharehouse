/**
 * 基本Dao接口
 * 2011,12,8
 * @杨闻
 */
package dao.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public interface BaseDao {

	// 增加或修改的方法
	public boolean saveOrUpdate(Object object);

	//获取
	@SuppressWarnings("unchecked")
	public Object get(Class objectClass, Serializable id);

	//删除
	@SuppressWarnings("unchecked")
	public boolean delete(Class objectClass, Serializable id);
	
	//获取全部
	public List<?> findAll(Class<?> objectClass);
		
	//分页查找,带参数
	@SuppressWarnings("unchecked")
	public List getPageList(String hql, Object[] params, int pageNo,
			int pageSize);
		
	//分页查找
	@SuppressWarnings("unchecked")
	public List getPageList(String hql, int pageNo, int pageSize);
		
	//分页查找
		@SuppressWarnings("unchecked")
	public List getPageListBySQL(String sql, int pageSize, int pageNo);
		
	public List getPageListBySQL(String sql);
		
	public boolean execute(String sql);
}
