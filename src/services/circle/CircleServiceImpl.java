package services.circle;

import java.util.List;

import dao.base.BaseDao;

public class CircleServiceImpl implements CircleService {

	BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List getCircleIdByName(String cName, String cLevel) {
		List list = null;
		String sql = "select id from circle where name = '"+cName+"' and level = '"+cLevel+"'";
		try{
			list = baseDao.getPageListBySQL(sql,1,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
}
