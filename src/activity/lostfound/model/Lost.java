package activity.lostfound.model;

import model.Share;

public class Lost extends Share {

	public Lost(){}
	String name;				//分享物品
	String place;			//获取地点
	String place_gene;		//获取地点概要
	String campus;			//校区
	String contact;			//联系方式
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPlace_gene() {
		return place_gene;
	}
	public void setPlace_gene(String place_gene) {
		this.place_gene = place_gene;
	}
	
	
	
}
