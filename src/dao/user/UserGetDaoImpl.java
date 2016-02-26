package dao.user;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Picture;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.AddArray;

import dao.base.BaseDaoImpl;

public class UserGetDaoImpl extends BaseDaoImpl implements UserGetDao{

	public User getUser(Long userid) {
		String hql = "from User u where u.id=?";
		Session session = getsession();
		Transaction tran = session.beginTransaction();
		Query query = session.createQuery(hql);
		Object []params = {userid};
		List<?> list = null;
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		} 
		list = query.list();
		User user = (User) list.get(0);
		tran.commit();
		session.close();
		return user;
	}
	
	public List getSearchGoodsList(Object[] parm, int size, int pageno) {
		List goodslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_g = "select distinct g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,g.add_time,g.firstimg_url,(select userid from favor where userid="+parm[0].toString()+" and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes from sh_user as u,goods_circle,goods as g,user_circle where ((user_circle.userid="+parm[0].toString()+" and goods_circle.circleid=user_circle.circleid and goods_circle.goodsid=g.id) or (exists(select userid from friends where friends.friendid="+parm[0].toString()+" and friends.userid=g.userid)))and g.userid=u.id and u.id!="+parm[0].toString()+" and g.goods like '%"+parm[1].toString()+"%' "+parm[2].toString()+" order by g.hotspot desc,abs(length(g.goods)-length('"+parm[3].toString()+"'))";
			goodslist = session.createSQLQuery(sql_g).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return goodslist;
	}

	public List getSearchNeedsList(Object[] parm, int size, int pageno) {
		List goodslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_n = "select distinct n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid="+parm[0].toString()+" and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes from sh_user as u,needs_circle,needs as n,user_circle where ((user_circle.userid="+parm[0].toString()+" and needs_circle.circleid=user_circle.circleid and needs_circle.needsid=n.id) or (exists(select userid from friends where friends.friendid="+parm[0].toString()+" and friends.userid=n.userid)))and n.userid=u.id and u.id!="+parm[0].toString()+" and n.needs like '%"+parm[1].toString()+"%' "+parm[2].toString()+" order by n.hotspot desc,abs(length(n.needs)-length('"+parm[3].toString()+"'))";
			goodslist = session.createSQLQuery(sql_n).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return goodslist;
	}
	
	public List getGoodsList(Long userid, int size,int pageno,String parm) {
		// TODO Auto-generated method stub
		List goodslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_g = "select g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,date_format(g.add_time,'%y-%m-%d %H:%i') as t,g.firstimg_url,(select userid from favor where userid=g.userid and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes,g.is_shared from sh_user as u ,goods as g where u.id = ? and g.userid = u.id "+parm+" order by t desc";
			goodslist = session.createSQLQuery(sql_g).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return goodslist;
	}
	
	public List getNeedsList(Long userid, int size, int pageno,String parm) {
		List needslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_n = "select n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid=n.userid and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes,n.is_shared from sh_user as u ,needs as n where u.id = ? and n.userid = u.id "+parm+" order by add_time desc";
			needslist = session.createSQLQuery(sql_n).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return needslist;
	}
	
	public List getFriendsGoodsList(Long userid, int size,int pageno,boolean isconcerned,String parm) {
		// TODO Auto-generated method stub
		List goodslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_g = "";
			if(!isconcerned)
				sql_g = "select g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,g.add_time,g.firstimg_url,(select userid from favor where userid=? and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes from sh_user as u ,goods as g where (exists(select friendid from friends where userid = ? and u.id=friendid and isignore='0') or u.id="+userid+")and g.userid = u.id "+parm+" order by add_time desc";
			else sql_g = "select g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,g.add_time,g.firstimg_url,(select userid from favor where userid=? and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes from sh_user as u ,goods as g where exists(select friendid from friends where userid = ? and u.id=friendid and isignore='0' and concerned='1')and g.userid = u.id "+parm+" order by add_time desc";
			goodslist = session.createSQLQuery(sql_g).setParameter(0, userid).setParameter(1, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return goodslist;
	}
	
	public List getFriendsNeedsList(Long userid, int size, int pageno,boolean isconcerned,String parm) {
		List needslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_n = "";
			if(!isconcerned)
				sql_n = "select n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid=? and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes from sh_user as u ,needs as n where (exists(select friendid from friends where userid = ? and u.id=friendid and isignore='0') or u.id="+userid+") and n.userid = u.id "+parm+" order by add_time desc";
			else sql_n = "select n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid=? and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes from sh_user as u ,needs as n where exists(select friendid from friends where userid = ? and u.id=friendid and isignore='0' and concerned='1') and n.userid = u.id "+parm+" order by add_time desc";
			needslist = session.createSQLQuery(sql_n).setParameter(0, userid).setParameter(1, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return needslist;
	}

	@SuppressWarnings("unchecked")
	public List<Picture> getPictureList(Long typeid, String type, int size,int pageno) {
		List<Picture> piclist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select * from picture where typeid = ? and type = ?";
			piclist = (List<Picture>)session.createSQLQuery(sql).setParameter(0, typeid).setParameter(1,type).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return piclist;
	}


	public Object[] getGoodsDetails(Long goodsid) {
		List gd=null,oi = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String getOtherInfo = "select sh_user.realname,sh_user.nickname,sh_user.head_ico_big,sh_user.sex,sh_user.phone,sh_user.qq,sh_user.show_email from sh_user ,goods where goods.id = ? and goods.userid = sh_user.id";
			String getGD = "select * from goods where id = ?";
			gd = session.createSQLQuery(getGD).setParameter(0, goodsid).list();
			oi = session.createSQLQuery(getOtherInfo).setParameter(0, goodsid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		Object[] obj = {gd,oi};
		return obj;
	}

	public Object[] getNeedsDetails(Long needsid) {
		List nd=null,oi = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String getOtherInfo = "select u.realname,u.nickname,u.head_ico_big,u.sex,u.phone,u.qq,u.show_email from sh_user as u ,needs as n where n.id = ? and n.userid = u.id";
			String getND = "select * from needs where id = ?";
			nd = session.createSQLQuery(getND).setParameter(0, needsid).list();
			oi = session.createSQLQuery(getOtherInfo).setParameter(0, needsid).list();			
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		Object[] obj = {nd,oi};
		return obj;
	}

	public List getCommentList(String commid, int size, int pageno) {
		List commlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.userid,u.realname,u.nickname,u.head_ico,c.id,c.typeid,c.type,c.content,date_format(c.addtime,'%Y-%m-%d %H:%i:%S') from sh_user as u ,comment as c where c.id in ("+commid+") and c.userid = u.id order by addtime desc";
			commlist = session.createSQLQuery(sql).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return commlist;
	}

	public List getCommentListByType(Long typeid, String type,int size, int pageno) {
		List commlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.userid,u.realname,u.nickname,u.head_ico,c.id,c.typeid,c.type,c.content,date_format(c.addtime,'%Y-%m-%d %H:%i:%S') from sh_user as u ,comment as c inner join "+type+" as t on (t.id = ? and c.typeid = t.id and c.type=?) where u.id = c.userid order by addtime desc";
			commlist = session.createSQLQuery(sql).setParameter(0, typeid).setParameter(1, type).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return commlist;
	}

	public List getMyCircle(Long userid, Long circleid, int size, int pageno) {
		
		List clist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "";
			if(circleid == null){
				sql = "select id,name,type,circle_ico,(select count(c.id) from circle as c,user_circle where user_circle.userid="+userid+" and c.circle_belonged=circle.id and user_circle.circleid=c.id) as hn,(select userid from firbid_send where userid = "+userid+" and circleid = circle.id) from circle inner join user_circle as uc on uc.userid ="+userid+" and uc.circleid = id and level=1 order by hn desc,sendNumber desc";
				clist = session.createSQLQuery(sql).setMaxResults(size).setFirstResult(pageno*size).list();
			}
			else {
				sql = "select id,name,type,circle_ico,(select count(c1.id) from circle as c1,user_circle where user_circle.userid="+userid+" and c1.circle_belonged=c.id and user_circle.circleid=c1.id) as hn,(select userid from firbid_send where userid = "+userid+" and circleid = c.id) from circle as c inner join user_circle as uc on (uc.userid = "+userid+" and uc.circleid = c.id) where c.circle_belonged = "+circleid+" order by hn desc,c.sendNumber desc";
				clist = session.createSQLQuery(sql).setMaxResults(size).setFirstResult(pageno*size).list();
			}
			
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return clist;
	}

	public List getMayLikeCircle(Object[] obj,int size,int pageno) {
		List clist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "";
			sql = "select id,name,type,circle_ico from circle as c where c.location = ? and c.level=1 and not exists (select * from user_circle as uc where uc.circleid = c.id and uc.userid = ?) order by sendNumber desc";
			clist = session.createSQLQuery(sql).setParameter(0, obj[1]).setParameter(1, obj[0]).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return clist;
	}

	public Integer getIsRequest(Long userid, Long reqid, String type) {
		Integer flag = 0;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select id from request_msg where senderid = ? and reqid = ? and req_type = ?";
			List l = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,reqid).setParameter(2, type).list();
			if(l != null && l.size() > 0)
				flag = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}

	public List getRequest(Long userid,int size,int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select distinct u.realname,u.nickname,u.head_ico,rm.* from " +
						"request_msg as rm inner join sh_user as u on (rm.userid = ? and rm.senderid = u.id) order by rm.addtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getResponse(Long userid,int size,int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select distinct u.realname,u.nickname,u.head_ico,rm.* from " +
						"response_msg as rm inner join sh_user as u on (rm.userid = ? and rm.senderid = u.id) order by rm.addtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}
	public List getNotice(Long userid,int size,int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select distinct u.realname,u.nickname,u.head_ico,nm.* from sh_user as u " +
						"inner join notice_msg as nm on nm.userid = ? and u.id=nm.userid order by nm.addtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}
	
	public List getPrivate(Long userid,Long senderid,int size,int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "";
			if(senderid==null){//其他人给的私信的第一条
				sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from sh_user as u " +
						"inner join private_msg as pm on (pm.userid = ? and u.id=pm.senderid and NOT EXISTS(select * from pm_hidden where pmid=pm.id and userid=pm.userid)) order by pm.addtime desc";
				rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			}else{//看和一个用户的私信对话
				sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from sh_user as u " +
						"inner join private_msg as pm on (((pm.userid = "+userid+" AND pm.senderid = "+senderid+") OR (pm.userid = "+senderid+" AND pm.senderid = "+userid+")) and u.id = pm.senderid and NOT EXISTS(select * from pm_hidden where pmid=pm.id and userid="+userid+")) order by pm.addtime desc";
				rlist = session.createSQLQuery(sql).setFirstResult(pageno*size).setMaxResults(size).list();
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getNotReadMessage(Long userid,int size,int pageno) {
		List rlist = null;
		List<Object[]> nrmlist = new ArrayList<Object[]>();
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql ="select messageid,type from user_message where userid = ? and islooked = '0'";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setMaxResults(size).setFirstResult(pageno*size).list();
			for(int i = 0 ; i < rlist.size(); i++){
				Object []obj = (Object[]) rlist.get(i);
				Object []o = null;
				if(!obj[1].toString().equals("notice")){
					String sql1 = "select um.type,u.realname,u.nickname,u.head_ico,m.* from "+obj[1].toString()+"_msg as m,user_message as um,sh_user as u where um.messageid = ? and um.type='"+obj[1].toString()+"' and um.messageid = m.id and u.id = m.senderid";
					o = (Object[])session.createSQLQuery(sql1).setParameter(0, Long.parseLong(obj[0].toString())).list().get(0);
					if(!o[o.length-1].toString().equals("0")){
						if(obj[1].toString().equals("response")&&o[o.length-2].toString().substring(0,5).equals("reply")){
							String commidList = o[o.length-4].toString();
							String sql2 = "select sh_user.realname,sh_user.head_ico,comment.* from sh_user,comment where comment.id in ("+commidList+") and comment.userid=sh_user.id order by comment.addtime desc";
							List reply = session.createSQLQuery(sql2).setMaxResults(15).list();
							if(reply != null && reply.size() > 0){
								o = AddArray.addObject(o, reply);
							}
						}
						else{
							String sql2 = "select * from response_reply where rid = ? and rtype = ? order by addtime desc";
							List reply = session.createSQLQuery(sql2).setParameter(0,o[4]).setParameter(1, o[0]).setMaxResults(15).list();
							if(reply != null && reply.size() > 0){
								o = AddArray.addObject(o, reply);
							}
						}
					}
				}else{
					String sql1 = "select um.type,u.realname,u.nickname,u.head_ico,m.* from "+obj[1].toString()+"_msg as m,user_message as um,sh_user as u where um.messageid = ? and um.type='notice' and um.messageid = m.id and u.id = m.userid";
					o = (Object[]) session.createSQLQuery(sql1).setParameter(0, Long.parseLong(obj[0].toString())).list().get(0);
				}
				nrmlist.add(0, o);
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return nrmlist;
	}

	public Integer getNotReadNumber(Long userid) {
		Integer num = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql ="select messageid from user_message where userid = ? and islooked = '0'";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			if(l == null || l.size() <= 0)
				num = 0;
			else num = l.size();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return num;
	}

	public List getResponseReply(Long rid, String type) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,rr.* from response_reply as rr ,sh_user as u inner join "+type+"_msg as o on (o.id = ? and u.id = o.senderid) where rr.rid = o.id order by addtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, rid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Integer getIsFriend(Long userid, Long friendid) {
		Integer flag = 0;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select userid from friends where userid = ? and friendid = ?";
			List l = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,friendid).list();
			if(l != null && l.size() > 0)
				flag = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}

	public List getMayKnowUser(Long userid,int size,int pageno) {
		List ulist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			User u = this.getUser(userid);
			/*String del = "delete from common_cf where userid = ?";
			session.createSQLQuery(del).setParameter(0,userid).executeUpdate();
			//插入共同好友
			String insert_cc = "INSERT INTO common_cf(userid,sid,sign,fnum,cnum) select ?,u.id,'cc',count(*),0 from sh_user as u,friends as f where (exists(select friendid from (select friendid from friends where userid=?)T where T.friendid=f.friendid and f.userid=u.id)and f.userid<> ?)group by u.id having count(f.friendid)>0";
			session.createSQLQuery(insert_cc).setParameter(0, userid).setParameter(1,userid).setParameter(2,userid).setFirstResult(size*pageno).setMaxResults(size).executeUpdate();
			//插入共同圈子
			String insert_cf = "INSERT INTO common_cf(userid,sid,sign,fnum,cnum) select ?,u.id,'cf',0,count(*) from sh_user as u,user_circle as c where (exists(select * from (select circleid from user_circle where userid=?)T where T.circleid=c.circleid and c.userid=u.id)and c.userid<> ?)group by u.id having count(c.circleid)>0";
			session.createSQLQuery(insert_cf).setParameter(0, userid).setParameter(1,userid).setParameter(2,userid).setFirstResult(size*pageno).setMaxResults(size).executeUpdate();
			String sql = "select sid,u.nickname,u.realname,u.head_ico,u.sex,u.school,sum(fnum),sum(cnum) from common_cf,sh_user as u where userid=? and u.id = sid and NOT EXISTS (select friendid from friends where userid=common_cf.userid and sid=friendid) GROUP BY sid order by sum(cnum),sum(fnum) desc";*/
			String sql = "select u.id,u.nickname,u.realname,u.head_ico,u.sex,u.school,u.department,u.hs_year,(select count(f.userid) from friends as f where (exists(select friendid from (select friendid from friends where userid="+userid+")T where T.friendid=f.friendid and f.userid=u.id))group by u.id having count(f.friendid)>0)as cf from sh_user as u where u.id<>"+userid+" and u.school='"+u.getSchool()+"' and u.department = '"+u.getDepartment()+"' and NOT EXISTS (select friendid from friends where userid="+userid+" and u.id=friendid) order by (IF(u.hs_year='"+u.getHs_year()+"',5,0)+IF(cf IS NULL,0,cf)+IF(u.send_active IS NULL,1,u.send_active)/2) desc";
			ulist = session.createSQLQuery(sql).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return ulist;
	}
	
	public List getSearchCircle(Long userid, int size, int pageno, Object[] parm) {
		List si = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.id,c.name,c.type,c.circle_ico,c.location,c.member_count from circle as c where ";
			if(parm[0]!= null && !parm[0].toString().equals("")){
				sql+="name like '%"+parm[0].toString()+"%'";
			}
			sql+=" order by abs(length(c.name)-length('"+parm[1].toString()+"'))";
			si = session.createSQLQuery(sql).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return si;
	}

	public List getConcernedCircle(Long userid,Integer size,Integer pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.id,c.name,c.type,c.circle_ico from circle as c inner join user_circle as cc on (cc.isconcerned = '1' and cc.userid=? and cc.circleid=c.id) order by cc.checktimes desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(size*pageno).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getCircleList(Long userid, int size, int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.id,c.name,c.type,c.circle_ico from circle as c inner join user_circle as uc on (uc.userid=? and uc.circleid=c.id) order by c.sendNumber desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Object getCircleNumber(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select circleid from user_circle where userid=? ";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			if(l == null || l.size()<=0)
				cn = 0;
			else cn = l.size();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public List getCommonCircleList(Long userid, Long friendid, int size,
			int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.id,c.name,c.type,c.circle_ico from circle as c ,user_circle as uc where (exists(select circleid from (select circleid from user_circle where userid=?)T where T.circleid = uc.circleid) and uc.userid = ? and c.id=uc.circleid) order by c.sendNumber desc";
			rlist = session.createSQLQuery(sql).setParameter(0, friendid).setParameter(1, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getCommonFriendsList(Long userid,Long friendid, int size, int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex from sh_user as u ,friends as f where (exists(select friendid from (select friendid from friends where userid=?)T where T.friendid = f.friendid) and f.userid = ? and u.id=f.friendid) order by send_active desc";
			rlist = session.createSQLQuery(sql).setParameter(0, friendid).setParameter(1, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getFriendsList(Long userid, int size, int pageno,boolean isAll,String condition) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "";
			//obj:是否是全部好友
			if(isAll)
				sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,f.concerned,f.isignore from sh_user as u inner join friends as f on (f.userid=? and f.friendid=u.id and f.isignore='0') "+condition+" order by send_active desc";
			else sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,f.concerned,f.isignore from sh_user as u inner join friends as f on (f.userid=? and f.friendid=u.id and f.isignore='0' and f.concerned='0') "+condition+" order by send_active desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public BigInteger getFriendsNumber(Long userid) {
		BigInteger cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from friends where userid=? and isignore='0'";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = (BigInteger) l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public List getStrangerGoodsList(Long userid,Long strangerid, int size, int pageno,String parm) {
		// TODO Auto-generated method stub
		List goodslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_g = "select g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,date_format(g.add_time,'%y-%m-%d %H:%i') as t,g.firstimg_url,(select userid from favor where userid=? and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes from goods as g ,sh_user as u ,goods_circle as gc where u.id = ? and g.userid=u.id and gc.goodsid = g.id and  exists(select circleid from (select circleid from user_circle where userid=?)T where T.circleid = gc.circleid) "+parm+" order by t desc";
			goodslist = session.createSQLQuery(sql_g).setParameter(0, userid).setParameter(1, strangerid).setParameter(2, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return goodslist;
	}
	
	public List getStrangerNeedsList(Long userid,Long strangerid, int size, int pageno,String parm) {
		List needslist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql_n = "select n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid=? and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes from needs as n,sh_user as u ,needs_circle as nc where u.id = ? and n.userid=u.id and nc.needsid = n.id and exists(select circleid from (select circleid from user_circle where userid=?)T where T.circleid = nc.circleid) "+parm+" order by n.add_time desc";
			needslist = session.createSQLQuery(sql_n).setParameter(0, userid).setParameter(1, strangerid).setParameter(2, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return needslist;
	}

	public List getLastVisitedList(Long userid, int size, int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,lv.visitedtime from sh_user as u inner join last_visited as lv on (lv.userid=? and lv.visitedid=u.id) order by lv.visitedtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getHotCircle(int size, int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select c.id,c.name,c.type,c.circle_ico,c.member_count from circle as c where c.level=1 order by c.sendNumber desc,c.member_count desc";
			rlist = session.createSQLQuery(sql).setFirstResult(pageno*size).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getBlackList(Long userid,int size,int pageno) {
		List bl = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,f.concerned,f.isignore from sh_user as u inner join friends as f on (f.userid=? and f.friendid=u.id and f.isignore='1') order by send_active desc";
			bl = session.createSQLQuery(sql).setParameter(0, userid).setMaxResults(size).setFirstResult(pageno*size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public String getShowPriCircle(Long userid) {
		String bl = "";
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select circleid from show_privacy_circle where userid = ?";
			List list = session.createSQLQuery(sql).setParameter(0, userid).list();
			if(list != null && list.size()>0)
			{
				for(int i = 0;i < list.size();i++){
					bl+=list.get(i)+",";
				}
				bl=bl.substring(0,bl.length()-1);
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public String getShowPriUser(Long userid) {
		String bl = "";
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select friendid from show_privacy_friends where userid = ?";
			List list = session.createSQLQuery(sql).setParameter(0, userid).list();
			if(list != null && list.size()>0)
			{
				for(int i = 0;i < list.size();i++){
					bl+=list.get(i)+",";
				}
				bl=bl.substring(0,bl.length()-1);
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public boolean getCanSeePrivacy(Long userid,Long typeid,String type) {
		boolean flag = false;
		Session session = null;
		try{
			session = this.getsession();
			String judge1 = "select userid,show_privacy from "+type+" where id = ?";
			List l = session.createSQLQuery(judge1).setParameter(0,typeid).list();
			if(l!= null && l.size()>0){
				Object[]obj = (Object[]) l.get(0);
				if(userid==Long.parseLong(obj[0].toString()))
					flag = true;
				else{
					if(Integer.parseInt(obj[1].toString())==1){
						String judge2 = "select userid from show_privacy_friends where userid = ? and friendid = ?";
						List l1 = session.createSQLQuery(judge2).setParameter(0,obj[0]).setParameter(1,userid).list();
						if(l1!=null&&l1.size()>0)flag = true;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}

	public List getConcernedFriends(Long userid) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,f.concerned,f.isignore from sh_user as u inner join friends as f on (f.userid=? and f.friendid=u.id and f.isignore='0' and f.concerned='1') order by u.send_active desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getConcernedMe(Long userid) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select u.id from sh_user as u inner join friends as f on (f.friendid=? and f.userid=u.id and f.isignore='0' and f.concerned='1')";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getHotGoodsList(Long userid, int size, int pageno,String parm) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select distinct g.userid,u.realname,u.nickname,u.head_ico,g.id,g.goods,g.type,g.add_time,g.firstimg_url,(select userid from favor where userid="+userid+" and type='goods' and typeid=g.id),(select count(userid) from favor where type='goods' and typeid=g.id),g.comment_size,g.lookedtimes from goods as g,sh_user as u inner join user_circle as uc on exists(select goodsid from goods_circle where goods_circle.circleid=uc.circleid and goodsid=g.id and uc.userid="+userid+") or exists (select friendid,isignore from friends where userid="+userid+" and friendid=u.id and isignore='0') or u.id="+userid+" where g.userid=u.id and not exists(select * from notes_hidden where userid="+userid+" and hiddenid=u.id) "+parm+" order by g.hotspot desc,add_time desc";
			rlist = session.createSQLQuery(sql).setFirstResult(size*pageno).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getHotNeedsList(Long userid, int size, int pageno,String parm) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select distinct n.userid,u.realname,u.nickname,u.head_ico,n.id,n.needs,n.type,n.add_time,n.content,(select userid from favor where userid="+userid+" and type='needs' and typeid=n.id),(select count(userid) from favor where type='needs' and typeid=n.id),n.comment_size,n.lookedtimes from needs as n,sh_user as u inner join user_circle as uc on exists(select needsid from needs_circle where needs_circle.circleid=uc.circleid and needsid=n.id and uc.userid="+userid+") or exists (select friendid,isignore from friends where userid="+userid+" and friendid=u.id and isignore='0') or u.id="+userid+" where n.userid=u.id and not exists(select * from notes_hidden where userid="+userid+" and hiddenid=u.id) "+parm+" order by n.hotspot desc,add_time desc";
			rlist = session.createSQLQuery(sql).setFirstResult(size*pageno).setMaxResults(size).list();
			tran.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Date getLastVisitedTime(Long userid, Long friendid) {
		Date d = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select visitedtime from last_visited where userid = ? and visitedid = ?";
			List list = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,friendid).list();
			if(list!=null&&list.size()>0)
				d = (Date) list.get(0);
			tran.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return d;
	}

	public List getAllCircle(Long userid,String ctype) {
		List circlelist = null;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select c.id,c.name,c.type,c.info,c.location,c.member_count,c.circle_ico from circle as c inner join user_circle on userid = ? and circleid = c.id and c.type='"+ctype+"' order by c.sendNumber desc";
			circlelist = session.createSQLQuery(sql).setParameter(0, userid).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return circlelist;
	}

	public boolean getIsCommented(Long userid, Long typeid,String type) {
		boolean bl = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select id from comment as c where c.userid=? and c.typeid=? and c.type=?";
			List list = session.createSQLQuery(sql).setParameter(0, userid).setParameter(1,typeid).setParameter(2,type).list();
			if(list!=null&&list.size()>0)
				bl=true;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public String getCommentLocation(Long responseid) {
		String bl = "";
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select res_moreinfo from response_msg  where id=?";
			List list = session.createSQLQuery(sql).setParameter(0,responseid).list();
			if(list!=null&&list.size()>0)
				bl=(String) list.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public List getSchoolList(String province) {
		List sl = null;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select id,name from circle where substring_index(location,'-',1)=? and type='校园' order by create_time";
			sl = session.createSQLQuery(sql).setParameter(0, province).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return sl;
	}

	public List getFriFriendsList(Long id, Long userid,int size, int pageno) {
		List sl = null;
		Session session = null;
		try{
			session = this.getsession();
			String sql = "select u.id,u.realname,u.nickname,u.head_ico,u.sex,u.school,(select userid from friends where userid=? and friendid=u.id) as isf,f.concerned,f.isignore,u.department,u.hs_year from sh_user as u inner join friends as f on (f.userid=? and f.friendid=u.id) order by isf,u.send_active desc";
			sl = session.createSQLQuery(sql).setParameter(0, userid).setParameter(1, id).setFirstResult(pageno*size).setMaxResults(size).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return sl;
	}

	public boolean getCanSeePriInfo(Long lookedid, Long userid, String field) {
		boolean bl = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String getPriLevel = "select "+field+" from sh_user where id=?";
			List list = session.createSQLQuery(getPriLevel).setParameter(0,userid).list();
			if(list!=null&&list.size()>0){
				String priLevel = list.get(0).toString();
				if(priLevel.equals("0"))
				{
					String sql = "select userid from friends where userid = ? and friendid = ?";
					List l = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,lookedid).list();
					if(l != null && l.size() > 0)
					{
						bl = true;
					}else{
						User u = this.getUser(userid);
						//String j = "select uc.circleid from user_circle as uc,circle as c where exists(select circleid from (select circleid from user_circle where userid = ?)T where T.circleid = uc.circleid and uc.userid = ?) and c.id=uc.circleid and c.level>1";
						String j = "select u.id from sh_user as u where u.id = ? and u.school = '"+u.getSchool()+"' and u.department = '"+u.getDepartment()+"' and u.hs_year = '"+u.getHs_year()+"'";
						List ju = session.createSQLQuery(j).setParameter(0,lookedid).list();
						if(ju != null && ju.size() > 0)
						{
							bl = true;
						}
					}
				}else if(priLevel.equals("1")){
					String sql = "select userid from friends where userid = ? and friendid = ?";
					List l = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,lookedid).list();
					if(l != null && l.size() > 0)
					{
						bl = true;
					}
				}else if(priLevel.equals("2")){}
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public boolean getIsShared(Long typeid, String type) {
		boolean bl = true;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select is_shared from "+type+" where id=?";
			List list = session.createSQLQuery(sql).setParameter(0,typeid).list();
			if(list!=null&&list.size()>0)
				if(list.get(0).toString().equals("0"))
					bl=false;
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return bl;
	}

	public List getFriPriMsg(Long userid,int size,int pageno) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();			
			String sql = "select distinct u.realname,u.nickname,u.head_ico,pm.* from sh_user as u " +
						"inner join privateMessage as pm on (pm.userid = ? and u.id=pm.senderid and pm.pri_isShow='1' and NOT EXISTS(select * from pm_hidden where pmid=pm.id and userid=pm.userid)) order by pm.addtime desc";
			rlist = session.createSQLQuery(sql).setParameter(0, userid).setMaxResults(size).setFirstResult(pageno*size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getRegularlyVisitedCircle(Long userid, int size, int pageno,
			int minVisitedTimes) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();			
			String sql = "select c.id,c.name,c.type,c.circle_ico from circle as c inner join user_circle as cc on (cc.checktimes>? and cc.userid=? and cc.circleid=c.id) order by cc.checktimes desc";
			rlist = session.createSQLQuery(sql).setParameter(0,minVisitedTimes).setParameter(1, userid).setMaxResults(size).setFirstResult(pageno*size).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public List getManageCircle(Long userid) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();			
			String sql = "select c.id,c.name,c.type,c.circle_ico,(select count(c_messageid)from circle_message where circleid=cc.circleid and islooked='0') as mn from circle as c inner join user_circle as cc on (cc.userid=? and cc.circleid=c.id and (cc.isadmin='1')) order by mn desc,cc.checktimes desc";
			rlist = session.createSQLQuery(sql).setParameter(0,userid).setMaxResults(50).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Long isRequest(Long userid, Long msgid, String type) {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select reqid from request_msg where id=? and userid=? and req_type=?";
			List list = session.createSQLQuery(sql).setParameter(0,msgid).setParameter(1,userid).setParameter(2,type).list();
			if(list!=null&&list.size()>0){
				id = Long.parseLong(list.get(0).toString());
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return id;
	}

	public Object getMsgTotalNumber(Long userid, String table) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(id) from "+table+" where userid = ?";
			List l = session.createSQLQuery(sql).setParameter(0,userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public Object getPrivateMsgNumber(Long userid, Long senderid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(id) from private_msg where ((userid = "+userid+" and senderid="+senderid+") or (senderid = "+userid+" and userid="+senderid+")) and NOT EXISTS(select * from pm_hidden where pmid=id and userid="+userid+")";
			List l = session.createSQLQuery(sql).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}
	
	public Object getFriMsgNumber(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(id) from privateMessage where userid = "+userid+" and isPublic='1' and NOT EXISTS(select userid from privateMessageHidden where privateMessageId=id and privateMessageHidden.userid=privateMessage.userid)";
			List l = session.createSQLQuery(sql).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public List getSuperiorCircle(Long circleid) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();			
			String sql = "select c.id,c.name,c.type,c.circle_ico from circle as c where c.id=(select c1.circle_belonged from circle as c1 where c1.id=?)";
			rlist = session.createSQLQuery(sql).setParameter(0,circleid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Date getLastSendTime(Long userid) {
		Date d = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select lastsendtime from sh_user where id = "+userid;
			List l = session.createSQLQuery(sql).list();
			d = (Date) l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return d;
	}

	public String getPassword(Long userid) {
		String pwd = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select password from sh_user where id = "+userid;
			List l = session.createSQLQuery(sql).list();
			pwd = l.get(0).toString();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return pwd;
	}

	public boolean getAPIKey(String apiKey) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select api_key from api_key where api_key = '"+apiKey+"'";
			List l = session.createSQLQuery(sql).list();
			if(l!=null&&l.size()>0)
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

	public List api_getUserInfo(Long userid, Integer infotype) {
		List rlist = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "";
			switch(infotype){
				case 1:{sql="select id,realname,head_ico,sex,school,province,city,coin,continue_online,friend_number,circle_number,is_pass,freeze from sh_user where id=?";break;}
				case 2:{sql="select * from sh_user where id=?";break;}
			}
			rlist = session.createSQLQuery(sql).setParameter(0,userid).list();
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return rlist;
	}

	public Object api_getCircleCount(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from user_circle where userid=? ";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public Object api_getFriendsCount(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from friends where userid=? ";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public Object api_getGoodsCount(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from goods where userid=?";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public Object api_getMessageCount(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from user_message where userid = ? and islooked = '0'";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

	public Object api_getNeedsCount(Long userid) {
		Object cn = null;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "select count(1) from needs where userid=?";
			List l = session.createSQLQuery(sql).setParameter(0, userid).list();
			cn = l.get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			tran.rollback();
		}finally{
			session.close();
		}
		return cn;
	}

}

