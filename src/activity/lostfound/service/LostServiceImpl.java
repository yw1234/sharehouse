package activity.lostfound.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Picture;

import util.GetTime;

import activity.lostfound.model.Lost;
import dao.base.BaseDao;

public class LostServiceImpl implements LostService {

	BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private String fillLostParams(Map params){
		StringBuffer sb = new StringBuffer();
		Object campus = params.get("campus"),
				place = params.get("place");
		if(campus!=null && !campus.equals(""))
			sb.append(" and l.campus = '"+campus.toString()+"' ");
		if(place!=null && !place.equals(""))
			sb.append(" and l.place_gene = '"+place.toString()+"' ");
		return sb.toString();
	}
	
	private List lostMapping(List list){
		List newList = null;
		if(list != null && list.size()>0){
			newList = new ArrayList();
			for(int i = 0;i < list.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] obj = (Object[]) list.get(i);
				map.put("id",obj[0]);
				map.put("userid",obj[1]);
				map.put("sendTime",GetTime.getSendTime((Date)obj[2]));
				map.put("firstImg",obj[3]);
				map.put("content",obj[4]);
				map.put("name",obj[5]);
				map.put("place",obj[6]);
				map.put("campus",obj[7]);
				map.put("place_gene",obj[8]);
				newList.add(map);
			}	
		}
		return newList;
	}

	public boolean send(Lost lost) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(lost);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public List get(Map params, int size, int pn) {
		// TODO Auto-generated method stub
		List list = null;
		String fields = "l.id," +
					"l.userid," +
					"l.add_time," +
					"(select thumbs_url from picture where typeid=l.id and type ='activity_lostfound' limit 1)," +
					"l.content," +
					"l.name,"+
					"l.place,"+
					"l.campus," +
					"l.place_gene";
		try{
			String sql = "select "+fields+" from activity_lostfound_lost as l where l.id!=0 "+fillLostParams(params)+" order by l.add_time desc";
			list = baseDao.getPageListBySQL(sql, size, pn);
		}catch(Exception e){
			e.printStackTrace();
		}
		return lostMapping(list);
	}
	
	public boolean addLostPicture(Long id, String[] picList) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			for(int i = 0 ; i < picList.length;i++)
			{
				if(!picList[i].equals("") && picList[i] != ""){
					Picture p = new Picture();
					p.setType("activity_lostfound");
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

	public boolean delete(Long id) {
		boolean flag = false;
		try{
			flag = baseDao.delete(Lost.class, id);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return flag;
	}

	public Map getDetail(Long lostid) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		try{
			String sql = "select url from picture where type = 'activity_lostfound' and typeid = "+lostid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			map.put("image", list.get(0));
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
}
