package model;

import java.util.Date;

public class Customer implements java.io.Serializable{

	Long id;						//id编号,主键
	String isNewUser = "0";			//是否是新用户
	String password;				//密码
	String realname;				//真实姓名
	String email;					//注册E-mail
	String freeze = "0";			//该用户是否被冻结  1 是 , 0否
	String is_pass = "0";			//是否通过验证
	Date add_time;					//注册时间
	String register_ip;				//注册ip
	Date lastlogin_time;			//上次登录时间
	String lastlogin_ip;			//上次登录地址
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	public String getIs_pass() {
		return is_pass;
	}
	public void setIs_pass(String is_pass) {
		this.is_pass = is_pass;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public String getRegister_ip() {
		return register_ip;
	}
	public void setRegister_ip(String register_ip) {
		this.register_ip = register_ip;
	}
	public Date getLastlogin_time() {
		return lastlogin_time;
	}
	public void setLastlogin_time(Date lastlogin_time) {
		this.lastlogin_time = lastlogin_time;
	}
	public String getLastlogin_ip() {
		return lastlogin_ip;
	}
	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}
	public String getIsNewUser() {
		return isNewUser;
	}
	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}
	
	
}
