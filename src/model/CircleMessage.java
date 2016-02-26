package model;

import java.util.Date;

public class CircleMessage implements java.io.Serializable{

	public CircleMessage(){}
	Long id;
	Long circleid;
	String content;
	Date addtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCircleid() {
		return circleid;
	}
	public void setCircleid(Long circleid) {
		this.circleid = circleid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
