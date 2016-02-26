package services.account;

import dao.base.BaseDao;

public interface AccountService {

	public BaseDao getBaseDao();
	/**
	 * 获取人人用户id映射
	 * @param rrId
	 * @return
	 */
	public Object getRenRenIdMapping(int rrId);
	
	/**
	 * 添加人人映射
	 * @param rrId
	 * @param userid
	 * @return
	 */
	public boolean setRenRenIdMapping(int rrId,Long userid);
	
	/**
	 * 是否绑定人人
	 * @param userid
	 * @return
	 */
	public boolean isBindingRenRen(Long userid);
	
	/**
	 * 绑定人人
	 * @param userid
	 * @return
	 */
	public boolean bindRenRen(Long userid);
	
	/**
	 * 解除绑定人人
	 * @param userid
	 * @return
	 */
	public boolean unbindRenRen(Long userid);
}
