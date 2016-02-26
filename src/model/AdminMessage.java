package model;

import java.util.Date;

public class AdminMessage implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public AdminMessage(){}
	Long id;
	String content;
	Date addtime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
