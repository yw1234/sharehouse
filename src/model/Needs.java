package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Needs implements java.io.Serializable{

	public Needs(){}
	
	Long id;							//主键
	Long userid;						//用户id
	String type;						//分享类型
	String needs;						//分享物品
	String content;						//内容
	Integer reward = 0;					//奖励积分
	String is_shared = "0";				//是否已分享出 0 未分享 1 已分享 
	Date add_time;						//发布时间
	String obj_qq;						//qq
	String obj_phone;					//联系电话
	String needstype_1;					//需求类别1
	String needstype_2;					//需求类别2
	String needstype_3;					//需求类别3
	String price;						//期望价格区间
	String totaltime;					//分享时长
	String old_degree;					//新旧程度
	String contactperson;				//联系人
	String show_inhome="1";				//是否在主页显示  0 不显示
	Integer lookedtimes = 0;			//被看次数
	Integer requiredtimes = 0;			//请求次数
	Integer comment_size = 0;			//评论总数
	Integer replytimes = 0;				//发布者回复次数
	Integer savetime;					//便签存在时长
	String show_privacy = "0";			//显示隐私
	Integer hotspot = 0;				//热度
	Set<Circle> circle = new HashSet<Circle>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNeeds() {
		return needs;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public String getIs_shared() {
		return is_shared;
	}

	public void setIs_shared(String isShared) {
		is_shared = isShared;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date addTime) {
		add_time = addTime;
	}

	public String getObj_qq() {
		return obj_qq;
	}

	public void setObj_qq(String objQq) {
		obj_qq = objQq;
	}

	public String getObj_phone() {
		return obj_phone;
	}

	public void setObj_phone(String objPhone) {
		obj_phone = objPhone;
	}

	
	public String getNeedstype_1() {
		return needstype_1;
	}

	public void setNeedstype_1(String needstype_1) {
		this.needstype_1 = needstype_1;
	}

	public String getNeedstype_2() {
		return needstype_2;
	}

	public void setNeedstype_2(String needstype_2) {
		this.needstype_2 = needstype_2;
	}

	public String getNeedstype_3() {
		return needstype_3;
	}

	public void setNeedstype_3(String needstype_3) {
		this.needstype_3 = needstype_3;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public String getOld_degree() {
		return old_degree;
	}

	public void setOld_degree(String oldDegree) {
		old_degree = oldDegree;
	}

	public String getContactperson() {
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getShow_inhome() {
		return show_inhome;
	}

	public void setShow_inhome(String showInhome) {
		show_inhome = showInhome;
	}

	public Integer getLookedtimes() {
		return lookedtimes;
	}

	public void setLookedtimes(Integer lookedtimes) {
		this.lookedtimes = lookedtimes;
	}

	public Integer getRequiredtimes() {
		return requiredtimes;
	}

	public void setRequiredtimes(Integer requiredtimes) {
		this.requiredtimes = requiredtimes;
	}

	public Integer getComment_size() {
		return comment_size;
	}

	public void setComment_size(Integer commentSize) {
		comment_size = commentSize;
	}

	public Integer getReplytimes() {
		return replytimes;
	}

	public void setReplytimes(Integer replytimes) {
		this.replytimes = replytimes;
	}

	public Integer getSavetime() {
		return savetime;
	}

	public void setSavetime(Integer savetime) {
		this.savetime = savetime;
	}
	
	public String getShow_privacy() {
		return show_privacy;
	}

	public void setShow_privacy(String showPrivacy) {
		show_privacy = showPrivacy;
	}

	public Set<Circle> getCircle() {
		return circle;
	}

	public void setCircle(Set<Circle> circle) {
		this.circle = circle;
	}

	public Integer getHotspot() {
		return hotspot;
	}

	public void setHotspot(Integer hotspot) {
		this.hotspot = hotspot;
	}
	
	
}
