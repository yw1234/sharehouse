package services.user;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 获取用户信息
	 * @param userid
	 * @param fields
	 * @return
	 */
	public Map getUserInfo(Long userid,String fields);
	
	/**
	 * 获取弹出用户介绍框信息
	 * @param userid
	 * @return
	 */
	public Map<String ,String> getUserIntroduce(Long userid);
	
	/**
	 * 判断好友关系
	 * @param userid
	 * @param friendid
	 * @return
	 */
	public boolean areFriends(Long userid,Long friendid);
	
	/**
	 * 判断账号是否存在
	 * @param email
	 * @return
	 */
	public boolean isExists(String email);
	
	/**
	 * 获取用户id
	 * @param email
	 * @return
	 */
	public Long getUserId(String email);
	
	/**
	 * 更新用户信息
	 * @param userid
	 * @param keys
	 * @param params
	 * @return
	 */
	public boolean updateUserInfo(Long userid,String keys,Map params);
	
	/**
	 * 获取搜索用户总数
	 * @param userid
	 * @param params
	 * @return
	 */
	public Object getSearchUserCount(Long userid, Map params);
	
	/**
	 * 获取搜索用用户
	 * @param userid
	 * @param params
	 * @param size
	 * @param pn
	 * @return
	 */
	public List getSearchUser(Long userid, Map params,int size,int pn);
	
	/**
	 * 登陆判断
	 * @param user_email
	 * @param user_password
	 * @return
	 */
	public Object loginJudge(String user_email,String user_password);
	/**
	 * 是否封号
	 * @param user_email
	 * @return
	 */
	public boolean isUserFreeze(String user_email);
	
	/**
	 * 更新登录信息
	 * @param userid
	 * @param ip
	 * @return
	 */
	public boolean updateLoginInfo(Long userid,String ip);
	
	/**
	 * 添加用于判断自动登陆的sessionid
	 * @param uid
	 * @param sessionid
	 * @return
	 */
	public boolean setSessionCookie(String uid,String sessionid);
	/**
	 * 通过cookie登陆
	 * @param userid
	 * @param sessionid
	 * @return
	 */
	public Map loginByCookie(String userid,String sessionid);
	
	/**
	 * 获取好友列表，适用于“我的好友”页面
	 * @param userid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getFriends(Long userid,int size,int pageno);
	
	/**
	 * at用户是否存在
	 * @param userid
	 * @param name
	 * @param loggedUid
	 * @return
	 */
	public boolean isAtUserExists(Long userid,String name,Long loggedUid);
}
