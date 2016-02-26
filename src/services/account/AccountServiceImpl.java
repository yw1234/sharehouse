package services.account;

import java.util.List;

import dao.base.BaseDao;

public class AccountServiceImpl implements AccountService{

	BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public Object getRenRenIdMapping(int rrId) {
		// TODO Auto-generated method stub
		Object obj = null;
		try{
			String sql = "select userid from renrenMapping where rr_id = "+rrId;
			List list = baseDao.getPageListBySQL(sql);
			if(list !=null && list.size()>0){
				obj = list.get(0);
				baseDao.execute("update renrenMapping set is_binding = '1' where rr_id ="+rrId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	public boolean setRenRenIdMapping(int rrId, Long userid) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			baseDao.execute("delete from renrenMapping where rr_id = " + rrId);
			String sql = "insert into renrenMapping(rr_id,userid,is_binding) values("+rrId+","+userid+",'1')";
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isBindingRenRen(Long userid) {
		boolean flag = false;
		try{
			String sql = "select is_binding from renrenMapping where userid = "+userid;
			List list = baseDao.getPageListBySQL(sql, 1, 0);
			if(list != null && list.size() > 0){
				Object o = list.get(0);
				if(o.toString().equals("1")){
					flag = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean bindRenRen(Long userid) {
		boolean flag = false;
		try{
			String sql = "update renrenMapping set is_binding = '1' where userid = "+userid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean unbindRenRen(Long userid) {
		boolean flag = false;
		try{
			String sql = "update renrenMapping set is_binding = '0' where userid = "+userid;
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

}
