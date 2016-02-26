package dao.user;

import java.util.Date;
import java.util.List;

import model.Complaint_AM;
import model.Goods;
import model.Message;
import model.Needs;
import model.Picture;
import model.Request_AM;
import model.Request_CM;
import model.Suggest_AM;

import org.hibernate.Session;

import dao.base.BaseDaoImpl;

public class UserSendDaoImpl extends BaseDaoImpl implements UserSendDao{

	public Long sendGoods(Long userid, String type, String goods,
			String content, Integer price, String oldDegree,
			String phone, String qq, String contactperson,Integer savetime, String f_pic,String show_pri,String t1,String t2,String lendtime,String goodslink) {
		// TODO Auto-generated method stub
		Long id = null;
		try{
			Goods g = new Goods();
			g.setUserid(userid);
			g.setGoods(goods);
			g.setType(type);
			g.setAdd_time(new Date());
			g.setContent(content);
			g.setPrice(price);
			g.setOld_degree(oldDegree);
			g.setGoodslink(goodslink);
			//g.setObj_phone(phone);
			//g.setObj_qq(qq);
			//g.setContactperson(contactperson);
			g.setSavetime(savetime);
			g.setFirstimg_url(f_pic);
			g.setGoodstype_1(t1);
			g.setGoodstype_2(t2);
			g.setShow_privacy(show_pri);
			g.setTotaltime(lendtime);
			if(this.saveOrUpdate(g))
			{
				id = g.getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return id;
	}

	
	public boolean addPicture(Long id, String type, String[] pic) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Session session = null;
		try{
			session = this.getsession();
			for(int i = 0 ; i < pic.length;i++)
			{
				if(!pic[i].equals("") && pic[i] != ""){
					Picture p = new Picture();
					p.setType(type);
					p.setTypeid(id);
					p.setThumbs_url(pic[i]);
					Integer index = pic[i].lastIndexOf(".");
					p.setUrl(pic[i].substring(0,index-7)+pic[i].substring(index));
					session.saveOrUpdate(p);
				}
			}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


	public Long sendNeeds(Long userid, String type, String needs,
			String content, String price, String oldDegree, String phone,
			String qq, String contactperson,Integer savetime,String show_pri,String t1,String t2,String borrowtime) {
		Long id = null;
		try{
			Needs n = new Needs();
			n.setAdd_time(new Date());
			n.setUserid(userid);
			n.setNeeds(needs);
			n.setType(type);
			n.setContent(content);
			n.setPrice(price);
			n.setOld_degree(oldDegree);
			n.setTotaltime(borrowtime);
			//n.setObj_phone(phone);
			//n.setObj_qq(qq);
			//n.setContactperson(contactperson);
			n.setSavetime(savetime);
			n.setShow_privacy(show_pri);
			n.setNeedstype_1(t1);
			n.setNeedstype_2(t2);
			if(this.saveOrUpdate(n))
			{
				id = n.getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return id;
		// TODO Auto-generated method stub
	}

	public boolean sendToCircle(Long userid,Long typeid, String type ,String[] circleid) {
		boolean flag = false;
		Session session = null;
		try{
			session = this.getsession();
			for(int i = 0 ; i < circleid.length;i++)
			{
				if(!circleid[i].equals("") && circleid[i] != "" && session.createSQLQuery("select uc.userid from user_circle as uc where uc.userid = "+userid+" and uc.circleid = "+circleid[i]+" and not exists(select userid from firbid_send where userid=uc.userid and circleid = uc.circleid)").list()!=null){
					Long cid = Long.parseLong(circleid[i]);
					String sql = "insert into "+type+"_circle("+type+"id,circleid) values(?,?)";
					session.createSQLQuery(sql).setParameter(0,typeid).setParameter(1,cid).executeUpdate();
				}
			}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}
	/*
	public Long sendComment(Long userid,Long senderid, Long typeid, String type,
			String content) {
		Long id = null;
		try{
			Comment comm = new Comment();
			comm.setUserid(senderid);
			comm.setTypeid(typeid);
			comm.setType(type);
			comm.setContent(content);
			comm.setAddtime(new Date());
			if(this.saveOrUpdate(comm))
			{
				id = comm.getId();
				if(!userid.equals(senderid)){
					Response_Msg rmsg = new Response_Msg();
					String rex = "select id from response_msg where userid=? and resid=? and res_type='reply"+type+"'";
					List res = this.getsession().createSQLQuery(rex).setParameter(0,userid).setParameter(1,typeid).list();
					if(res != null && res.size()>0)
					{
						Object resid = res.get(0);
						rmsg = (Response_Msg) this.getHibernateTemplate().find("from Response_Msg where id="+resid+"").get(0);
						rmsg.setReplytimes(rmsg.getReplytimes()+1);
						String commidList= rmsg.getRes_moreinfo().trim();
						String islooked = (String) this.getsession().createSQLQuery("select islooked from user_message where userid="+userid+" and messageid="+resid+"").list().get(0);
						//看过之前的则消除已经看过的回复
						if(islooked.equals("1")){
							commidList=id.toString();
						}else commidList+=","+id;
						rmsg.setRes_moreinfo(commidList);
						this.getHibernateTemplate().update(rmsg);
						String sql = "update user_message set islooked = '0' where userid = ? and messageid = ? and type = ?";
						this.getsession().createSQLQuery(sql).setParameter(0,userid).setParameter(1, res.get(0)).setParameter(2, "response").executeUpdate();
					}else{
						Object obj[] = null;
						String title = "";
						String sql = "select o.type,o."+type+" from "+type+" as o where o.id = ?";
						List o = this.getsession().createSQLQuery(sql).setParameter(0,typeid).list();
						if(o != null && o.size()>0){
							obj = (Object[]) o.get(0);
							if(type.equals("goods")){
								if(obj[0].toString().equals("sell")){
									title = "出售"+obj[1].toString();
								}else if(obj[0].toString().equals("lend")){
									title = "出借"+obj[1].toString();
								}else if(obj[0].toString().equals("gift")){
									title = "赠送"+obj[1].toString();
								}else if(obj[0].toString().equals("unknow")){
									title = "赠送"+obj[1].toString();
								}
							}
							else if(type.equals("needs")){
								if(obj[0].toString().equals("buy")){
									title = "购买"+obj[1].toString();
								}else if(obj[0].toString().equals("borrow")){
									title = "求借"+obj[1].toString();
								}else if(obj[0].toString().equals("demand")){
									title = "想要"+obj[1].toString();
								}
							}
						}
						rmsg.setAddtime(new Date());
						rmsg.setResid(typeid);
						rmsg.setRes_content(content);
						rmsg.setSenderid(senderid);
						rmsg.setUserid(userid);
						rmsg.setRes_type("reply"+type);
						rmsg.setRes_flag("");
						rmsg.setTitle(title);
						rmsg.setRes_moreinfo(id.toString());
						rmsg.setReplytimes(1);
						if(this.saveOrUpdate(rmsg)){
							String sql1 = "insert into user_message (userid,messageid,type) values(?,?,?)";
							this.getsession().createSQLQuery(sql1).setParameter(0,userid).setParameter(1,rmsg.getId()).setParameter(2,"response").executeUpdate();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return id;
	}


	public boolean sendRequestMessage(Long userid, Long senderid, Long reqid,String type,
			String content) {
		boolean flag = false;
		boolean insert_flag = true;
		Long id = null;
		Session session = null;
		try{
			String title = "";
			String sned_object = "";
			session = this.getsession();
			Request_Msg rmsg = new Request_Msg();
			if(type.equals("goods") || type.equals("needs"))
			{
				String sql = "select type,"+type+" from "+type+" as o where o.id = ?";
				Object [] obj = (Object[]) session.createSQLQuery(sql).setParameter(0, reqid).list().get(0);
				if(type.equals("goods")){
					if(obj[0].toString().equals("sell")){
						title="请求购买";
				}else if(obj[0].toString().equals("lend")){
						title = "求借";
						}else if(obj[0].toString().equals("gift")){
						title = "想要";
					}
				}
				else if(type.equals("needs")){
					if(obj[0].toString().equals("buy")){
						title="想出售给我";
					}else if(obj[0].toString().equals("borrow")){
						title = "想借给我";
					}else if(obj[0].toString().equals("demand")){
						title = "想送给我";
					}
				}
				sned_object = obj[1].toString();
			}else if(type.equals("addfriends")){
				String rex = "select id from request_msg where userid = ? and senderid = ? and req_type = 'addfriends'";
				List rexl = session.createSQLQuery(rex).setParameter(0,userid).setParameter(1,senderid).list();
				if(rexl != null && rexl.size() > 0)
				{
					String update = "update user_message set islooked = '0' where userid = ? and messageid = ? and type = 'request'";
					int i = session.createSQLQuery(update).setParameter(0, userid).setParameter(1, rexl.get(0)).executeUpdate();
					if(i == 1)
						flag = true;
					insert_flag = false;
				}else{
					title = "想加你为好友";
				}	
			}
			if(insert_flag){
				rmsg.setAddtime(new Date());
				rmsg.setReqid(reqid);
				rmsg.setReq_content((content==null||content.trim().equals(""))?"未填写":content);
				rmsg.setSenderid(senderid);
				rmsg.setUserid(userid);
				rmsg.setReq_type(type);
				rmsg.setTitle(title);
				rmsg.setOp_object(sned_object);
				if(this.saveOrUpdate(rmsg) && !type.equals("addfriends")){
					String sql = "update "+type+" set requiredtimes=requiredtimes+1 where id = ?";
					session.createSQLQuery(sql).setParameter(0, reqid).executeUpdate();
				}else this.saveOrUpdate(rmsg);
				id = rmsg.getId();	
				String sql = "insert into user_message (userid,messageid,type) values(?,?,?)";
				int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,id).setParameter(2,"request").executeUpdate();
				if(i == 1)
					flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}
	
	public boolean sendRequestMessage(Request_Msg rmsg){
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			List rex = session.createSQLQuery("select id from request_msg where userid = "+rmsg.getUserid()+" and senderid = "+rmsg.getSenderid()+" and req_type = 'invite'").list();
			if(rex==null||rex.size()<=0){
				session.saveOrUpdate(rmsg);
				if(this.getsession().createSQLQuery("insert into user_message (userid,messageid,type) values("+rmsg.getUserid()+","+rmsg.getId()+",'request')").executeUpdate()==1)
					flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}
	
	public boolean sendResponseMessage(Long update_reqid,Long userid, Long senderid, Long resid,String type,
			String op,String req_object,String content,String moreinfo,String To) {
		boolean flag = false;
		Long id = null;
		Session session = null;
		if(op==null) op="";
		try{
			String title = "";
			session = this.getsession();
			Response_Msg rmsg = new Response_Msg();
			List res = null;
			//已经存在了该条请求或回复,添加留言,若为分享或需求,需要增加该物品id的筛选,否则不用
			if(!type.equals("goods")&&!type.equals("needs")){
				String res_rex = "select id from "+To+"_msg where userid = ? and senderid = ? and "+To.substring(0,3)+"_type = ?";
				res = session.createSQLQuery(res_rex).setParameter(0,userid).setParameter(1,senderid).setParameter(2,type).list();
			}else{
				String res_rex = "select id from "+To+"_msg where userid = ? and senderid = ? and "+To.substring(0,3)+"id = ?";
				res = session.createSQLQuery(res_rex).setParameter(0,userid).setParameter(1,senderid).setParameter(2,resid).list();
			}
			if(res != null && res.size() > 0 && !op.equals("同意了"))
			{
				id = Long.parseLong(res.get(0).toString());
				Response_reply rr = new Response_reply();
				rr.setRid(id);
				rr.setRtype(To);
				rr.setContent(content);
				rr.setAddtime(new Date());
				if(this.saveOrUpdate(rr)){
					String sql = "update user_message set islooked = '0' where userid = ? and messageid = ? and type = ?";
					String addrt = "update "+To+"_msg set replytimes=replytimes+1 where id=?";
					int i,j;
					i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,id).setParameter(2,To).executeUpdate();
					j = session.createSQLQuery(addrt).setParameter(0,id).executeUpdate();
					if(i == 1 && j == 1)
						flag = true;
				}
			}else{		//不存在该条请求或回复或同意时须单独回复,添加回复
				if(To.equals("response")){			//第一次回复请求,发布方为被请求者
					if(type.equals("goods") || type.equals("needs"))
					{
						String sql = "select o.type,o."+type+" from "+type+" as o where o.id = ?";
						Object [] obj = (Object[]) session.createSQLQuery(sql).setParameter(0, resid).list().get(0);
						if(type.equals("goods")){
							if(obj[0].toString().equals("sell")){
								title = "购买"+req_object;
							}else if(obj[0].toString().equals("lend")){
								title = "借"+req_object;
							}else if(obj[0].toString().equals("gift")){
								title = "想要"+req_object;
							}
						}
						else if(type.equals("needs")){
							if(obj[0].toString().equals("buy")){
								title = "出售"+req_object;
							}else if(obj[0].toString().equals("borrow")){
								title = "借出"+req_object;
							}else if(obj[0].toString().equals("demand")){
								title = "赠送"+req_object;
							}
						}
					}else if(type.equals("addfriends")){
						title = "添加好友的请求";
						Date d = new Date();
						if(op.equals("同意了")){//加好友成功
							List l = session.createSQLQuery("select userid from friends where userid=? and friendid=?").setParameter(0,userid).setParameter(1,senderid).list();
							if(l!=null&&l.size()>0){
								return flag;
							}
							String addf = "update sh_user set friend_number=friend_number+1 where id = ?";
							session.createSQLQuery(addf).setLong(0,userid).executeUpdate();
							session.createSQLQuery(addf).setLong(0,senderid).executeUpdate();
							String sql1 = "insert into friends(userid,friendid,last_vistedtime) values(?,?,?)";
							String sql2 = "insert into friends(userid,friendid,last_vistedtime) values(?,?,?)";
							int i = session.createSQLQuery(sql1).setParameter(0,userid).setParameter(1,senderid).setParameter(2, d).executeUpdate();
							int j =	session.createSQLQuery(sql2).setParameter(0,senderid).setParameter(1,userid).setParameter(2, d).executeUpdate();
							if(i == 1 && j == 1)
								flag = true;
							session.createSQLQuery("delete from notes_hidden where userid=? and hiddenid = ?").setParameter(0,userid).setParameter(1,senderid).executeUpdate();
							session.createSQLQuery("delete from notes_hidden where userid=? and hiddenid = ?").setParameter(0,senderid).setParameter(1,userid).executeUpdate();
							//标记为已处理
							session.createSQLQuery("update request_msg set req_flag='1' where id="+update_reqid+"").executeUpdate();
						}
					}
					rmsg.setAddtime(new Date());
					rmsg.setResid(resid);
					rmsg.setRes_content(content);
					rmsg.setSenderid(senderid);
					rmsg.setUserid(userid);
					rmsg.setRes_type(type);
					rmsg.setRes_flag(op);
					rmsg.setTitle(title);
					rmsg.setRes_moreinfo(moreinfo);
					boolean f = this.saveOrUpdate(rmsg);
					id = rmsg.getId();
					if(f && op.equals("同意了") && !type.equals("addfriends")){//分享成功
						String sql = "update "+type+" set replytimes=replytimes+1,is_shared='1' where id = ?";
						session.createSQLQuery(sql).setParameter(0, resid).executeUpdate();
						//分享成功记录
						String success = "insert into share_succeed(typeid,type,userid,friendid,addtime) values(?,?,?,?,?)";
						session.createSQLQuery(success).setParameter(0,resid).setParameter(1,type).setParameter(2,userid).setParameter(3,senderid).setParameter(4,new Date()).executeUpdate();
						//favor发送通知,暂不加
						//List fav_user = session.createSQLQuery("select userid from favor where typeid=? and type=?").setParameter(0,resid).setParameter(1,type).list();
						//标记为已处理
						session.createSQLQuery("update request_msg set req_flag='1' where id="+update_reqid+"").executeUpdate();
					}
					String sql = "insert into user_message (userid,messageid,type) values(?,?,?)";
					int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,id).setParameter(2,"response").executeUpdate();
					if(i == 1)
						flag = true;
				}
				else{		//第一次回复请求的回复,发布方为请求者
					op = "回复了";title = "";
					rmsg.setAddtime(new Date());
					rmsg.setResid(resid);
					rmsg.setRes_content(content);
					rmsg.setSenderid(senderid);
					rmsg.setUserid(userid);
					rmsg.setRes_type(type);
					rmsg.setRes_flag(op);
					rmsg.setTitle(title);
					rmsg.setRes_moreinfo(moreinfo);
					if(this.saveOrUpdate(rmsg)){
						id = rmsg.getId();
					}
					String sql = "insert into user_message (userid,messageid,type) values(?,?,?)";
					int i = session.createSQLQuery(sql).setParameter(0,userid).setParameter(1,id).setParameter(2,"response").executeUpdate();
					
					if(i == 1)
						flag = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


	public boolean sendNoticeMessage(Long userid, String type, String content,
			Object[] parm,Integer pNum) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			Notice_Msg nmsg = new Notice_Msg();
			if(pNum==1){
				nmsg.setAddtime(new Date());
				nmsg.setN_content(content);
				if(parm!=null)
					nmsg.setTitle(parm[0].toString());
				nmsg.setUserid(userid);
				nmsg.setN_flag(type);
				if(this.saveOrUpdate(nmsg)){
					String insert = "insert into user_message (userid,messageid,type) values(?,?,?)";
					this.getsession().createSQLQuery(insert).setParameter(0,userid).setParameter(1,nmsg.getId()).setParameter(2,"notice").executeUpdate();
				}
			}
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}
	
	public boolean sendPrivateMessage(Long userid, Long senderid, String title,
			String priContent,String priIsShow) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			Private_Msg pmsg = new Private_Msg();
			pmsg.setAddtime(new Date());
			pmsg.setPri_content(priContent);
			pmsg.setUserid(userid);
			pmsg.setSenderid(senderid);
			pmsg.setTitle(title);
			pmsg.setPri_isShow(priIsShow);
			if(this.saveOrUpdate(pmsg)){
				String insert = "insert into user_message (userid,messageid,type) values(?,?,?)";
				this.getsession().createSQLQuery(insert).setParameter(0,userid).setParameter(1,pmsg.getId()).setParameter(2,"private").executeUpdate();
				flag=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


*/
	public boolean sendComplaint(Long userid, Long comUserid,
			String reason, String content) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			Complaint_AM cam = new Complaint_AM();
			cam.setSenderid(userid);
			cam.setComplainted_userid(comUserid);
			cam.setAddtime(new Date());
			cam.setReason(reason);
			cam.setContent(content);
			if(this.saveOrUpdate(cam)){
				String addamsg = "insert into admin_message(a_messageid,type) values(?,?)";
				int i = session.createSQLQuery(addamsg).setParameter(0,cam.getId()).setParameter(1,"complaint").executeUpdate();
				if(i==1)
					flag=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


	public boolean sendCircleRequest(Long userid, Long circleid,
			String content,String reqType, String reqContent) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			Request_CM rc = new Request_CM();
			rc.setSenderid(userid);
			rc.setCircleid(circleid);
			rc.setContent(content);
			rc.setAddtime(new Date());
			rc.setReq_content(reqContent);
			rc.setReq_type(reqType);
			session.saveOrUpdate(rc);
			String addamsg = "insert into circle_message(c_messageid,circleid,type) values(?,?,?)";
			session.createSQLQuery(addamsg).setParameter(0,rc.getId()).setParameter(1,circleid).setParameter(2,"request").executeUpdate();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


	public boolean sendAdminRequest(Long userid, String content,
			String reqType, String reqContent,Long remark_id) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			List isRequest = session.createSQLQuery("select id from request_am where senderid="+userid+" and req_type='"+reqType+"'").list();
			if(isRequest==null||isRequest.size()<=0){
				Request_AM ra = new Request_AM();
				ra.setSenderid(userid);
				ra.setContent(content);
				ra.setAddtime(new Date());
				ra.setReq_content(reqContent);
				ra.setReq_type(reqType);
				ra.setRemark_id(remark_id);
				session.saveOrUpdate(ra);
				String addamsg = "insert into admin_message(a_messageid,type) values(?,?)";
				session.createSQLQuery(addamsg).setParameter(0,ra.getId()).setParameter(1,"request").executeUpdate();
			}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}


	public boolean sendSuggest(Long userid,String content) {
		boolean flag = false;
		Session session = null;
		try{
			session = getsession();
			Suggest_AM sam = new Suggest_AM();
			sam.setSenderid(userid);
			sam.setContent(content);
			sam.setAddtime(new Date());
			sam.setSug_type("");
			session.saveOrUpdate(sam);
			String addamsg = "insert into admin_message(a_messageid,type) values(?,?)";
			session.createSQLQuery(addamsg).setParameter(0,sam.getId()).setParameter(1,"suggest").executeUpdate();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return flag;
	}
}
