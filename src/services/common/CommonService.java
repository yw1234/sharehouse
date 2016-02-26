package services.common;

import java.util.Map;

import model.OptionsKey;

public interface CommonService {

	/**
	 * 学校是否录入
	 * @param cName
	 * @return
	 */
	public boolean isSchoolExists(String cName);
	
	/**
	 * 获取校区
	 * @param sName
	 * @return
	 */
	public String [] getCampus(String sName);
	
	/**
	 * 获取物品概括分类
	 * @return
	 */
	public String [] getObjType1();
	
	/**
	 * 获取物品详细分类
	 * @param type1
	 * @return
	 */
	public String [] getObjType2(String type1);
	
	/**
	 * 添加操作密码
	 * @param rp
	 * @return
	 */
	public boolean addOptKey(OptionsKey rp);
	
	/**
	 * 删除操作密码
	 * @param key
	 * @return
	 */
	public boolean deleteOptKey(String key);
	
	/**
	 * 删除操作密码
	 * @param uid
	 * @param opt
	 * @return
	 */
	public boolean deleteOptKey(Long uid,String opt);
	
	/**
	 * 获取重置密码用户信息
	 * @param key
	 * @return
	 */
	public Map getResetUid(String key);
	
	/**
	 * 获取验证用户信息
	 * @param key
	 * @return
	 */
	public Map getEmailValidationUid(String key);
}
