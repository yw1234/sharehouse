package util;

import java.io.File;

import org.apache.struts2.ServletActionContext;

public class StaticInfo {
	//管理员账号
	public final static Long adminId = 52L;
	
	//路径
	public final static String URL = ServletActionContext.getRequest().getContextPath();
	public final static String webURL = "http://www.quan15.us";
	//图片虚拟路径
	public final static String ImgURL = "http://images.quan15.us";
	//public final static String ImgURL = "/upload"; //test路径
	//物理路径
	public final static String ImgPhysicalPath = "~"+File.separator+"quan15-images";
	@SuppressWarnings("deprecation")
	//public final static String ImgPhysicalPath = ServletActionContext.getRequest().getRealPath("/upload"); //test url
	//用户列表
	public final static Integer userNumber = 20;
	//信息条数
	public final static Integer feedsNumber = 15;
	//闲置条数
	public final static Integer idleSize = 16;
	//消息条数
	public final static Integer msgNumber = 10;
	//客户端每次获取条数
	public final static Integer api_m_number = 10;
	
	//热度增加点数
	public final static Integer addHot_commPoint = 2;
	public final static Integer addHot_favorPoint = 3;
	public final static Integer addHot_lookPoint = 1;
	
	//人人
	public final static String renren = "renren";
	//微博
	public final static String weibo = "weibo";
	//QQ
	public final static String qq = "QQ"; 
	//aes_key
	public final static String AESKey = "8a4892f93d30f259";
}
