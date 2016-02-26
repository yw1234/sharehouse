package services.share;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.BeanHelper;
import util.DeleteFile;
import util.GetTime;
import util.StaticInfo;

import model.Goods;
import model.IdleOrder;
import model.Picture;
import model.User;

import dao.base.BaseDao;

public class ShareServiceImpl implements ShareService {

	BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private List pictureMapping(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0 ; i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Picture p = (Picture) list.get(i);
				map.put("id", p.getId());
				map.put("type", p.getType());
				map.put("typeId", p.getTypeid());
				map.put("url", p.getUrl());
				map.put("thumbs_url", p.getThumbs_url());
				newList.add(map);
			}
		}
		return newList;
	}
	
	private String fillIdleParams(Map params){
		StringBuffer sql = new StringBuffer();
		Object shareType = params.get("shareType"),
				objType1 = params.get("objType1"),
				objType2 = params.get("objType2"),
				sex = params.get("sex"),
				isShared = params.get("onlyShared");
		
		//发布的类型
		if(shareType!=null&&!shareType.toString().equals(""))
			sql.append("and g.type='"+shareType.toString()+"' ");
		//类型1
		if(objType1!=null&&!objType1.toString().equals(""))
			sql.append("and g.goodstype_1='"+objType1.toString()+"' ");
		//类型2
		if(objType2!=null&&!objType2.toString().equals(""))
			sql.append("and g.goodstype_2='"+objType2.toString()+"' ");
		//性别
		if(sex!=null&&!sex.toString().equals(""))
			sql.append("and u.sex='"+sex.toString()+"' ");
		//是否分享完
		if(isShared!=null&&!isShared.toString().equals(""))
			sql.append("and g.is_shared='"+isShared.toString()+"' ");
		//未封号
		sql.append("and u.freeze='0' ");
		return sql.toString();
	}
	
	private Map goodsMapping(Object[]o){
		if(o!=null){
			for(int i = 0 ; i < o.length ;i++)
				if(o[i]==null)
					o[i] = "";
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("obj_id",o[0]);
			root.put("obj_userid",o[1]);
			root.put("obj_type",o[2]);
			root.put("obj_object",o[3]);
			root.put("obj_content",o[4]);
			root.put("obj_reward",o[5]);
			root.put("obj_qq",o[6]);
			root.put("obj_phone",o[7]);
			root.put("obj_price",o[8]);
			root.put("obj_totalTime",o[9]);
			root.put("obj_oldDegree",o[10]);
			root.put("obj_isShared",o[11]);
			root.put("obj_addTime",GetTime.getSendTime((Date)o[12]));
			root.put("obj_showInHome",o[13]);
			root.put("obj_commentSize",o[14]);
			root.put("obj_firstImg",o[15]);
			root.put("obj_lookedTimes",o[16]);
			root.put("obj_requiredTimes",o[17]);
			root.put("obj_replyTimes",o[18]);
			root.put("obj_saveTime",o[19]);
			root.put("obj_contactPerson",o[20]);
			root.put("obj_showPrivacy",o[21]);
			root.put("obj_goodsType1",o[22].toString());
			root.put("obj_goodsType2",o[23].toString());
			root.put("obj_goodsType3",o[24].toString());
			root.put("obj_hotSpot",o[25]);
			root.put("obj_goodsLink",o[26]);
			root.put("obj_client",o[27]);
			root.put("obj_bargain",o[28]);
			return root;
		}else return null;
	}
	
	private Map detailsPageMapping(Object[]o){
		if(o!=null){
			for(int i = 0 ; i < o.length ;i++)
				if(o[i]==null)
					o[i] = "";
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("obj_id",o[0]);
			root.put("obj_userid",o[1]);
			root.put("obj_type",o[2]);
			root.put("obj_object",o[3]);
			root.put("obj_content",o[4]);
			root.put("obj_reward",o[5]);
			root.put("obj_qq",o[6]);
			root.put("obj_phone",o[7]);
			root.put("obj_price",!o[8].toString().trim().equals("")?("<label style = 'color:#e68303;'>"+o[8].toString()+"</label> RMB"):"面议");
			root.put("obj_totalTime",!o[9].toString().trim().equals("")?o[9]:"未说明");
			root.put("obj_oldDegree",!o[10].toString().trim().equals("")?o[10]:"未说明");
			root.put("obj_isShared",o[11]);
			root.put("obj_addTime",GetTime.getSendTime((Date)o[12]));
			root.put("obj_showInHome",o[13]);
			root.put("obj_commentSize",o[14]);
			root.put("obj_firstImg",o[15]);
			root.put("obj_lookedTimes",o[16]);
			root.put("obj_requiredTimes",o[17]);
			root.put("obj_replyTimes",o[18]);
			root.put("obj_saveTime",o[19]);
			root.put("obj_contactPerson",o[20]);
			root.put("obj_showPrivacy",o[21]);
			root.put("obj_goodsType1",o[22].toString());
			root.put("obj_goodsType2",o[23].toString());
			root.put("obj_goodsType3",o[24].toString());
			root.put("obj_hotSpot",o[25]);
			root.put("obj_goodsLink",o[26]);
			root.put("obj_client",o[27]);
			root.put("obj_bargain",!o[28].toString().trim().equals("")?o[28].toString():"未说明");
			return root;
		}else return null;
	}
	
	private Map detailsUserMapping(Object[] oio){
		if(oio!=null){
			for(int i = 0 ; i < oio.length ;i++)
				if(oio[i]==null)
					oio[i] = "";
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("sender_name",oio[0].toString());
			root.put("sender_nickname",oio[1].toString());
			root.put("sender_avatar_big",oio[2]);
			root.put("sender_sex",oio[3].toString());
			root.put("sender_phone",!oio[4].toString().trim().equals("")?oio[4].toString():"未公开");
			root.put("sender_qq",!oio[5].toString().trim().equals("")?oio[5].toString():"未公开");
			root.put("sender_email",!oio[6].toString().trim().equals("")?oio[6].toString():"未公开");
			root.put("sender_avatar",oio[7]);
			root.put("sender_school",oio[8]);
			root.put("sender_department",oio[9]);
			root.put("sender_grade",oio[10]);
			root.put("sender_edu",oio[11]);
			return root;
		}else{
			return null;
		}
	}
	
	private List idleListToMap(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("userid",obj[0]);
				map.put("userName",obj[1]);
				map.put("userNickName",obj[2]!=null?obj[2].toString():"");
				map.put("userAvatar",obj[3]);
				map.put("goodsid",obj[4]);
				map.put("goodsTitle",obj[5]);
				map.put("shareType",obj[6]);
				map.put("sendTime",GetTime.getSendTime((Date)obj[7]));
				map.put("firstImg",obj[8]);
				map.put("goodsDetails",obj[9]);
				map.put("isFavored",obj[10]);
				map.put("favorTimes",obj[11]!=null?obj[11]:"");
				map.put("commentSize",obj[12]!=null?obj[12]:"");
				map.put("lookedTimes",obj[13]!=null?obj[13]:"");
				map.put("requiredTimes",obj[14]!=null?obj[14]:"");
				map.put("isShared",obj[15]);
				map.put("price",obj[16]);
				newList.add(map);
			}	
		}
		return newList;
	}

	public List getIdleList(Long userid, int size, int pageno, Map params) {
		// TODO Auto-generated method stub
		List list = null;
		//要获取的字段
		String fields = "g.userid," +
				"u.realname," +
				"u.nickname," +
				"u.head_ico," +
				"g.id," +
				"g.goods," +
				"g.type," +
				"g.add_time," +
				"(select thumbs_url from picture where typeid=g.id and type ='goods' limit 1)," +
				"g.content," +
				"(select userid from favor where userid="+userid+" and type='goods' and typeid=g.id)," +
				"(select count(userid) from favor where type='goods' and typeid=g.id)," +
				"g.comment_size," +
				"g.lookedtimes," +
				"g.requiredtimes," +
				"g.is_shared," +
				"g.price";
		String sql = "";
		String m = params.get("method").toString();
		try{
			if(m==null||m.equals("feeds")){
				//范围
				User u = (User) baseDao.get(User.class, userid);
				Object scope = params.get("scope");
				//排序方式
				Object sortType = params.get("sortType");
				String sort = (sortType==null||sortType.equals(""))?"g.add_time desc":"g.hotspot desc,g.add_time desc";
				String s = " u.school = '"+u.getSchool()+"' or ";
				if(scope != null && scope.equals("1"))
					s = " u.school = '"+u.getSchool()+"' and u.department='"+u.getDepartment()+"' or ";
				else if(scope != null && scope.equals("2"))
					s = " u.school = '"+u.getSchool()+"' and u.department='"+u.getDepartment()+"' and u.hs_year='"+u.getHs_year()+"' or ";				
				else if(scope != null && scope.equals("3"))
					s = "";
				sql = "select distinct "+ fields +
							" from goods as g inner join sh_user as u on " +
								s+
									"exists (select friendid,isignore from friends where userid="+userid+" and friendid=u.id and isignore='0') or " +
											"u.id="+userid+" " +
												"where g.userid=u.id and not exists(select * from notes_hidden where userid="+userid+" and hiddenid=u.id) "+fillIdleParams(params)+" order by "+sort;
			}else if(m.equals("userFeeds")){
				sql = "select distinct "+ fields +" from sh_user as u ,goods as g where u.id = "+params.get("vid")+" and g.userid = u.id "+fillIdleParams(params)+" order by g.add_time desc";
			}else if(m.equals("stranger")){
				size = StaticInfo.feedsNumber;
				pageno = 0;
				sql = "select distinct "+ fields +" from sh_user as u ,goods as g where u.id = "+params.get("vid")+" and g.userid = u.id order by g.add_time desc";
			}else if(m.equals("result")){
				String key = params.get("key").toString();
				String keysplit = "";
				for(int i = 0 ; i < key.length();i++){
					keysplit+=key.substring(i,i+1)+"%";
				}
				keysplit = keysplit.equals("")?key:keysplit.substring(0,keysplit.length()-1);
				sql = "select distinct "+ fields +" from sh_user as u ,goods as g where g.userid=u.id and u.id!="+userid+" and (g.goods like '%"+keysplit+"%' or g.idle_alphabets like '%"+key+"%') order by g.hotspot desc,abs(length(g.goods)-length('"+key+"'))";
			}else{
				sql = "select distinct "+ fields +" from sh_user as u ,goods as g where u.id = "+userid+" and g.userid = u.id order by g.add_time desc";
			}
			list = baseDao.getPageListBySQL(sql, size, pageno);
		}catch(Exception e){
			e.printStackTrace();
		}
		return idleListToMap(list);
	}

	public boolean deleteIdle(Long userid, Long goodsid, String path) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			DeleteFile df = new DeleteFile();
			List pic = baseDao.getPageListBySQL("select url,thumbs_url from picture where typeid = "+goodsid);
			for(int i = 0 ; i < pic.size() ; i++){
				Object[] obj = (Object[])pic.get(i);
				String p = path+File.separator+obj[0].toString().substring(StaticInfo.ImgURL.length()+1),
					p_thu = path+File.separator+obj[1].toString().substring(StaticInfo.ImgURL.length()+1); 
				if(new File(p).exists())
					df.deleteFile(p);
				if(new File(p_thu).exists())
					df.deleteFile(p_thu);
			}
			String del_comm = "delete from commentMessage where commShareId = "+goodsid+" and commType = 'goods'"; 
			boolean f1 = baseDao.execute(del_comm);
			String del_lookedtimes = "delete from looktimes where typeid = "+goodsid+" and userid = "+userid+" and type = 'goods'"; 
			boolean f2 = baseDao.execute(del_lookedtimes);
			String sql_dp = "delete from picture where typeid = "+goodsid;
			boolean f3 = baseDao.execute(sql_dp);
			String sql_dg = "delete from goods where id = "+goodsid;
			boolean f4 = baseDao.execute(sql_dg);
			if(f1&&f2&&f3&&f4)
				flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isIdleExists(Long userid, Long goodsid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "select id from goods where userid = "+userid+" and id = "+goodsid;
			List list = baseDao.getPageListBySQL(sql);
			if(list != null && list.size()>0)
				flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Long getIdleSender(Long idleId) {
		Long id = null;
		try{
			String sql = "select userid from goods where id = "+idleId;
			List list = baseDao.getPageListBySQL(sql,1,0);
			if(list != null && list.size()>0)
				id = Long.parseLong(list.get(0).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public boolean sendIdle(Goods goods) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(goods);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean addIdlePicture(Long id, String[] picList) {
		boolean flag = false;
		try{
			for(int i = 0 ; i < picList.length;i++)
			{
				if(!picList[i].equals("")){
					Picture p = new Picture();
					p.setType("goods");
					p.setTypeid(id);
					p.setThumbs_url(picList[i]);
					Integer index = picList[i].lastIndexOf(".");
					p.setUrl(picList[i].substring(0,index-7)+picList[i].substring(index));
					baseDao.saveOrUpdate(p);
				}
			}
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Map getGoodsInfo(Long id) {
		// TODO Auto-generated method stub
		Object []obj = null;
		try{
			String sql = "select * from goods where id = "+id;
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list!=null&&list.size()>0){
				obj = (Object[]) list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return goodsMapping(obj);
	}
	
	public Map getGoodsInfo(Long id, String fields) {
		Object []obj = null;
		Map map = null;
		try{
			String sql = "select "+fields+" from goods where id = "+id;
			List list = baseDao.getPageListBySQL(sql, 1,0);
			if(list!=null && list.size()>0){
				obj = (Object[]) list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(obj != null)
		{
			map = new HashMap();
			String []field = fields.split(",");
			for(int i = 0 ; i < field.length ; i++){
				map.put(field[i], obj[i]);
			}
		}
		return map;
	}

	public Map getGoodsDetails(Long id) {
		Map map = new HashMap();
		try{
			String getGoods = "select * from goods where id = "+id;
			List list_g = baseDao.getPageListBySQL(getGoods, 1,0);
			if(list_g!=null&&list_g.size()>0){
				Map mg = detailsPageMapping((Object[])list_g.get(0));
				map.putAll(mg);
				//获取用户信息
				String getUser = "select sh_user.realname,sh_user.nickname,sh_user.head_ico_big,sh_user.sex,sh_user.phone,sh_user.qq,sh_user.show_email,sh_user.head_ico,sh_user.school,sh_user.department,sh_user.hs_year,sh_user.educational from sh_user ,goods where goods.id = "+id+" and goods.userid = sh_user.id";
				List list_u = baseDao.getPageListBySQL(getUser, 1,0);
				if(list_u!=null && list_u.size()>0){
					Map mu = detailsUserMapping((Object[])list_u.get(0));
					map.putAll(mu);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public boolean updateGoods(Goods goods){
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(goods);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Picture> getIdlePictureList(Long id, int size, int pageno) {
		List<Picture> piclist = null;
		try{
			String hql = "from Picture where typeid = "+id+" and type = 'goods'";
			piclist = (List<Picture>)baseDao.getPageList(hql, pageno, size);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pictureMapping(piclist);
	}

	public boolean addOrder(IdleOrder order) {
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(order);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteOrder(Long id) {
		boolean flag = false;
		try{
			flag = baseDao.delete(IdleOrder.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public Map getOrderInfoById(Long orderid) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			IdleOrder io = (IdleOrder) baseDao.get(IdleOrder.class, orderid);
			if(io != null){
				map = new HashMap();
				map.putAll(BeanHelper.convertBeanToMap(io));
				String userInfo = "select realname,head_ico,sex from sh_user where id = ";
				List buyer = baseDao.getPageListBySQL(userInfo+io.getBuyer_uid(), 1, 0);
				List seller = baseDao.getPageListBySQL(userInfo+io.getSeller_uid(), 1, 0);
				if(buyer != null && seller != null){
					Object [] buyerInfo = (Object[]) buyer.get(0),
							sellerInfo = (Object[]) seller.get(0);
					map.put("buyerName", buyerInfo[0]);
					map.put("sellerName", sellerInfo[0]);
					map.put("buyerAvatar", buyerInfo[1]);
					map.put("sellerAvatar", sellerInfo[1]);
					map.put("buyerSex", buyerInfo[2]);
					map.put("sellerSex", sellerInfo[2]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public List getMyOrderInfo(Long userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getOrderInfoByIdle(Long idleid , Long userid) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			String sql = "select id from idleOrder where idleid = "+idleid+" and (buyer_uid = '"+userid+"' or seller_uid = '"+userid+"')";
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size() > 0){
				Object id = list.get(0);
				IdleOrder io = (IdleOrder) baseDao.get(IdleOrder.class, Long.parseLong(id.toString()));
				map = new HashMap();
				map.putAll(BeanHelper.convertBeanToMap(io));
				String userInfo = "select realname,head_ico,sex from sh_user where id = ";
				List buyer = baseDao.getPageListBySQL(userInfo+io.getBuyer_uid(), 1, 0);
				List seller = baseDao.getPageListBySQL(userInfo+io.getSeller_uid(), 1, 0);
				if(buyer != null && seller != null){
					Object [] buyerInfo = (Object[]) buyer.get(0),
							sellerInfo = (Object[]) seller.get(0);
					map.put("buyerName", buyerInfo[0]);
					map.put("sellerName", sellerInfo[0]);
					map.put("buyerAvatar", buyerInfo[1]);
					map.put("sellerAvatar", sellerInfo[1]);
					map.put("buyerSex", buyerInfo[2]);
					map.put("sellerSex", sellerInfo[2]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public boolean confirmOrder(Long idleid, Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			String sql = "update idleOrder set isComplete = '1' where idleid = "+idleid+" and buyer_uid = "+userid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
}
