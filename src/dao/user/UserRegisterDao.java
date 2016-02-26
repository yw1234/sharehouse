package dao.user;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import model.Circle;
import model.User;

public interface UserRegisterDao {
	//录入注册用户基本信息
	public Long InputInfoByAPI(User u);
	public Long InputInfo(String user_email,String user_password,String user_name,String user_sex,String user_school,String user_ip) throws NoSuchAlgorithmException, UnknownHostException;
	//判断邮箱是否用过
	public boolean IsUniqueEmail(String user_email);
	//
	public Object getUserObj(String setname,String getname,Object obj);
	//圈中成员
	public List getCommonUser(Long userid,Map params,int size,int pageno);
	
}
