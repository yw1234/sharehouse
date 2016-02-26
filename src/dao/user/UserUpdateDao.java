package dao.user;

import model.User;

public interface UserUpdateDao {
	//更改密码
	public boolean updatePassword(Long userid,String newPwd);
	//上次发布时间
	public boolean updateLastSendTime(Long userid);
	//将未读信息变为已读
	public boolean updateLookingMessage(Long userid);
	//更新用户信息
	public boolean updateUserInfo(User u);
	//更新隐私信息
	public boolean updateUserPrivacy(User u);
	//更新黑名单
	public boolean updateBlackList(Long userid,Long friendid,String flag);
	//查看是否已分享
	public boolean updateIsShared(Long typeid,String type);
	//newUser
	public boolean updateNewUser(Long userid);
	//登录信息
	public boolean updateLoginInfo(Long userid,String email,String pwd);
}
