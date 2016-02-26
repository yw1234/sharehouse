/**
 * 好友以及关注的好友关联类
 * Share网站
 * @杨闻 2011,12,5
 */
package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Friends implements java.io.Serializable{
	
	public Friends(){}
	
	Long userid;				//用户id
	Long friendid;				//好友id
	String concerned = "0";		//是否关注  0 否 , 1是
	String isignore = "0";		//是否忽略  0 否, 1是
	Date last_vistedtime;		//上次访问时间
	Integer at_times = 0;		//at次数
	
	/**
	 * setter and getter
	 */
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getFriendid() {
		return friendid;
	}
	public void setFriendid(Long friendid) {
		this.friendid = friendid;
	}
	public String getConcerned() {
		return concerned;
	}
	public void setConcerned(String concerned) {
		this.concerned = concerned;
	}
	public String getIsignore() {
		return isignore;
	}
	public void setIsignore(String isignore) {
		this.isignore = isignore;
	}
	public Date getLast_vistedtime() {
		return last_vistedtime;
	}
	public void setLast_vistedtime(Date lastVistedtime) {
		last_vistedtime = lastVistedtime;
	}
	public Integer getAt_times() {
		return at_times;
	}
	public void setAt_times(Integer at_times) {
		this.at_times = at_times;
	}
	
	
	
}
