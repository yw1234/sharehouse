package dao.user;

import dao.base.BaseDaoImpl;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Circle;
import model.User;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import util.AddArray;
import util.GetAC;
import util.PinYinUtil;
import util.SHA;
import util.StaticInfo;
import dao.user.UserRegisterDao;


public class UserRegisterDaoImpl extends BaseDaoImpl implements UserRegisterDao{

	
	public Long InputInfo(String userEmail, String userPassword,
			String userName, String userSex,String userSchool,String userIp)
			throws NoSuchAlgorithmException, UnknownHostException {
		Long id = null;
		User user = new User();
		user.setHead_ico("/web/image/base/head_default.png");
		user.setHead_ico_big("/web/image/base/head_default.png");
		user.setEmail(userEmail);
		user.setPassword(SHA.getSHA(userPassword));
		user.setRealname(userName);
		user.setNickhead(PinYinUtil.getPinYinHeadChar(userName));
		user.setNickname(PinYinUtil.getPinYin(userName));
		user.setSex(userSex);
		user.setSchool(userSchool);
		user.setRegister_ip(userIp);
		user.setAdd_time(new Date());
		user.setLastlogin_time(new Date());
		user.setIsNewUser("1");
		if(this.saveOrUpdate(user))
		{
			id = user.getId();
		}
		return id;
	}
	
	public Long InputInfoByAPI(User u){
		Long id = null;
		u.setIsNewUser("1");
		u.setRegisterByAPI("1");
		if(this.saveOrUpdate(u))
		{
			id = u.getId();
		}
		return id;
	}
	
	public boolean IsUniqueEmail(String userEmail)
	{
		boolean flag = true;
		String hql = "from User u where u.email=?";
		Session session = getsession();
		Query query = session.createQuery(hql);
		Object []params = {userEmail};
		List<?> list = null;
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		} 
		list = query.list();
		session.close();
		if(list != null && list.size() > 0)
			flag = false;
		
		return flag;
	}

	/*
	@SuppressWarnings({ "null", "unchecked" })
	public List<Circle> getMayLikeCircle(String school,String province, String city) {
		
		List circlelist = new ArrayList();
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select id,name,type,info,location,member_count,circle_ico from circle where (location = ? or location like ?) and name <> ? and type = 'school'";
			List cl = session.createSQLQuery(sql).setParameter(0, province+"-"+city).setParameter(1,"%"+province+"%").setParameter(2,school).setMaxResults(30).list();
			if(cl!=null&&cl.size()>0)
			for(int i = 0 ; i < cl.size() ; i++)
				circlelist.add(cl.get(i));
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return circlelist;
	}*/

	public Object getUserObj(String setname,String getname ,Object obj) {
		// TODO Auto-generated method stub
		Object object = null;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select "+getname+" from sh_user where "+setname+" = ?";
			object = session.createSQLQuery(sql).setParameter(0, obj).list().get(0);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return object;
	}

	public List getCommonUser(Long userid,Map params,
			int size, int pageno) {
		List ulist = null;
		Session session = null;
		try{
			session = this.getsession();
			String school = params.get("school").toString();
			String department = params.get("department").toString();
			String grade = params.get("grade").toString();
			String edu = params.get("edu").toString();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex from sh_user as u where u.id!="+userid+" and u.school = '"+school+"' and u.department= '"+department+"' and u.hs_year = '"+grade+"' and u.educational='"+edu+"' order by u.send_active desc";
			ulist = session.createSQLQuery(sql).setMaxResults(size).setFirstResult(pageno*size).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return ulist;
	}

	/*
	public Long getCircleId(Long schoolid, String dp,String grade,Long userid) {
		Long cid = null;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select id from circle where name = ? and circle_belonged = ?";
			session.createSQLQuery("update sh_user set department='"+dp+"',hs_year='"+grade+"' where id = '"+userid+"'").executeUpdate();
			List l  = session.createSQLQuery(sql).setParameter(0,dp+""+grade).setParameter(1, schoolid).list();
			if(l!=null&&l.size()>0){
				cid = Long.parseLong(l.get(0).toString());
			}else{
				CircleGetDao cgd = (CircleGetDao) GetAC.getAppContext().getBean("CircleGetDao");
				CircleAddDao cad = (CircleAddDao) GetAC.getAppContext().getBean("CircleAddDao");
				Circle cb = cgd.getCircleInfo(schoolid);
				Circle c = new Circle();
				c.setName(dp+""+grade);
				c.setType("院系");
				c.setNotice("");
				c.setLocation(cb.getLocation());
				c.setJoin_type("1");
				c.setCircle_belonged(schoolid);
				c.setLevel(cb.getLevel()+1);
				c.setMaxNumber(400);
				c.setCreate_time(new Date());
				c.setIsUserCreate("0");
				cid = cad.addcircle(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return cid;
	}*/

}
