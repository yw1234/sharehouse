package dao.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.DeleteFile;

import dao.base.BaseDaoImpl;

public class UserDeleteDaoImpl extends BaseDaoImpl implements UserDeleteDao{

	@SuppressWarnings("unchecked")
	public boolean deleteGoods(Long userid,Long id,String path) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			DeleteFile df = new DeleteFile();
			List pic = session.createSQLQuery("select url,thumbs_url from picture where typeid = ?").setParameter(0,id).list();
			for(int i = 0 ; i < pic.size() ; i++){
				Object[] obj = (Object[])pic.get(i);
				df.deleteFile(path+"\\"+obj[0].toString());
				df.deleteFile(path+"\\"+obj[1].toString());
			}
			String del_comm = "delete from commentMessage where commShareId = ? and commType = ?"; 
			session.createSQLQuery(del_comm).setParameter(0,id).setParameter(1,"goods").executeUpdate();
			String del_goods_circle = "delete from goods_circle where goodsid = ?"; 
			session.createSQLQuery(del_goods_circle).setParameter(0,id).executeUpdate();
			String del_lookedtimes = "delete from looktimes where typeid = ? and userid = ? and type = 'goods'"; 
			session.createSQLQuery(del_lookedtimes).setParameter(0,id).setParameter(1,userid).executeUpdate();
			String sql_dp = "delete from picture where typeid = ?";
			session.createSQLQuery(sql_dp).setParameter(0,id).executeUpdate();
			String sql_dg = "delete from goods where id = ?";
			session.createSQLQuery(sql_dg).setParameter(0,id).executeUpdate();
			
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
	

	public boolean deleteNeeds(Long userid,Long id) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String del_comm = "delete from comment where typeid = ? and type = ?"; 
			session.createSQLQuery(del_comm).setParameter(0,id).setParameter(1,"needs").executeUpdate();
			String del_lookedtimes = "delete from looktimes where userid = ? and typeid = ? and type = ?"; 
			session.createSQLQuery(del_lookedtimes).setParameter(0,userid).setParameter(1,id).setParameter(2,"needs").executeUpdate();
			String del_needs_circle = "delete from needs_circle where needsid = ?"; 
			session.createSQLQuery(del_needs_circle).setParameter(0,id).executeUpdate();
			String sql = "delete from needs where id = ?";
			session.createSQLQuery(sql).setParameter(0,id).executeUpdate();
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


	public boolean deleteMessage(Long id, String type) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql1="",sql2="",sql3 = "";
			if(type.equals("request")){
				sql1 = "delete from user_message where messageid = ? and type = 'request'";
				sql2 = "delete from request_msg where id = ?";
				sql3 = "delete from response_reply where rid = ? and rtype = ?";
				session.createSQLQuery(sql3).setParameter(0,id).setParameter(1,type).executeUpdate();
			}
				
			else if(type.equals("response")){
				sql1 = "delete from user_message where messageid = ? and type = 'response'";
				sql2 = "delete from response_msg where id = ?";		
				sql3 = "delete from response_reply where rid = ? and rtype = ?";
				session.createSQLQuery(sql3).setParameter(0,id).setParameter(1,type).executeUpdate();
			}
				
			else if(type.equals("notice")){
				sql1 = "delete from user_message where messageid = ? and type = 'notice'";
				sql2 = "delete from notice_msg where id = ?";			}
			
			else if(type.equals("private")){
				sql1 = "delete from user_message where messageid = ? and type = 'private'";
				sql2 = "delete from private_msg where id = ?";			
			}
			session.createSQLQuery(sql1).setParameter(0,id).executeUpdate();
			session.createSQLQuery(sql2).setParameter(0,id).executeUpdate();
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
	
	public boolean deleteAllMessage(Long userid, String type) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql1="",sql2="",sql3 = "";
			if(type.equals("request")){
				sql1 = "delete from user_message where userid = ? and type = 'request'";
				sql2 = "delete from request_msg where userid = ?";
				List idlist = session.createSQLQuery("select id from request_msg where userid=?").setParameter(0,userid).list();
				String il = "(";
				for(int i = 0;i<idlist.size();i++){
					il+=idlist.get(i)+",";
				}
				il=il.substring(0,il.length()-1)+")";
				sql3 = "delete from response_reply where rid in "+il+" and rtype = ?";
				session.createSQLQuery(sql3).setParameter(0,type).executeUpdate();
			}
			else if(type.equals("response")){
				sql1 = "delete from user_message where userid = ? and type = 'response'";
				sql2 = "delete from response_msg where userid = ?";		
				List idlist = session.createSQLQuery("select id from response_msg where userid=?").setParameter(0,userid).list();
				String il = "(";
				for(int i = 0;i<idlist.size();i++){
					il+=idlist.get(i)+",";
				}
				il=il.substring(0,il.length()-1)+")";
				sql3 = "delete from response_reply where rid in "+il+" and rtype = ?";
				session.createSQLQuery(sql3).setParameter(0,type).executeUpdate();
			}
				
			else if(type.equals("notice")){
				sql1 = "delete from user_message where userid = ? and type = 'notice'";
				sql2 = "delete from notice_msg where userid = ?";			}
			
			else if(type.equals("private")){
				sql1 = "delete from user_message where userid = ? and type = 'private'";
				sql2 = "delete from private_msg where userid = ?";			}
					
			session.createSQLQuery(sql1).setParameter(0,userid).executeUpdate();
			session.createSQLQuery(sql2).setParameter(0,userid).executeUpdate();
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

	public boolean deleteGoodsInCircle(Long userid,Long typeid, Long cid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List isadmin = session.createSQLQuery("select userid from user_circle where userid="+userid+" and circleid="+cid+" and isadmin='1'").list();
			if(isadmin!=null&&isadmin.size()>0){
				String sql = "delete from goods_circle where goodsid = ? and circleid = ?";
				int i = session.createSQLQuery(sql).setParameter(0,typeid).setParameter(1, cid).executeUpdate();
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


	public boolean deleteNeedsInCircle(Long userid,Long typeid, Long cid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List isadmin = session.createSQLQuery("select userid from user_circle where userid="+userid+" and circleid="+cid+" and isadmin='1'").list();
			if(isadmin!=null&&isadmin.size()>0){				String sql = "delete from needs_circle where needsid = ? and circleid = ?";
				int i = session.createSQLQuery(sql).setParameter(0,typeid).setParameter(1, cid).executeUpdate();
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


	public boolean deleteUserInCircle(Long userid, Long circleid,String delSendMark) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			List judge_adm = session.createSQLQuery("select uc.isadmin from user_circle as uc where uc.userid="+userid+" and uc.circleid="+circleid+" and (uc.isadmin='1' or uc.ishost='1')").list();
			if(judge_adm==null||judge_adm.size()<=0){
				String sql = "delete from user_circle where userid = ? and circleid = ?";
				int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1, circleid).executeUpdate();
				if(i == 1){
					if(delSendMark.equals("1")){
						session.createSQLQuery("delete from goods_circle where goods_circle.circleid="+circleid+" and exists(select id from goods where id=goods_circle.goodsid and userid="+userid+")").executeUpdate();
						session.createSQLQuery("delete from needs_circle where needs_circle.circleid="+circleid+" and exists(select id from needs where id=needs_circle.needsid and userid="+userid+")").executeUpdate();
					}
					flag = true;
				}
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


	public boolean deleteFriends(Long userid, Long friendid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			
			String sql = "delete from friends where userid = ? and friendid = ?";
			int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1, friendid).executeUpdate();
			int j = session.createSQLQuery(sql).setParameter(0,friendid).setParameter(1, userid).executeUpdate();
			if(i == 1 && j==1)
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
	
	public boolean decreaseOperationTimes(String op, Long typeid, String type) {
		boolean flag = false;
		Session session = null;
		Transaction tran = null;
		try{
			session = getsession();
			tran = session.beginTransaction();
			String sql = "update "+type+" set "+op+"="+op+"-1 where id="+typeid;
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


	public boolean deleteComment(Long id) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			
			String sql = "delete from comment where id = ?";
			int i = session.createSQLQuery(sql).setParameter(0,id).executeUpdate();
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


	public boolean decreaseSendActiveInCircle(Long userid, Long circleid) {
		Session session = null;
		Transaction tran = null;
		boolean flag = false;
		try{
			session = getsession();
			tran = session.beginTransaction();
			
			String sql = "update user_circle set send_active_user=send_active_user-1 where userid=? and circleid=?";
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

}
