/**
 * 圈子信息
 * Share网站
 * @杨闻 2011,12,5
 */
package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Circle implements java.io.Serializable{
	
	public Circle(){}
	
	Long id;								//主键	
	String name;							//圈子名称
	String type;							//圈子属性
	Integer level;							//等级  1不用加入,可关注(类似公共主页)  2联名申请  3自己创建(类似群)
	String info;							//圈子信息
	String notice;							//圈内公告
	String location;						//圈子位置
	Integer circle_count = 0;				//下属圈子数量
	Integer member_count = 0;				//成员数量
	Integer concerned_number = 0;			//关注数量
	String circle_ico = "/web/image/base/circle_default.png";						//圈子标识
	String join_type = "0";					//加入方式  0 随意加入  1 需验证  2 不能加入
	String circle_pinyin = "";				//圈子拼音
	String circle_level = "0";				//圈子地位	
	Long sendNumber = 0L;					//物品发布数量
	Integer company_num = 0;				//商家数量
	Date create_time;						//创建日期
	Long circle_belonged;					//属于哪个圈子
	String freeze = "0";					//是否查封
	String ispass = "0";					//是否通过认证
	Integer maxNumber;						//人数上限
	String isUserCreate="0";					//是否是人为创建
	
	Set<Circle>joineduser = new HashSet<Circle>();
	/**
	 * setter and getter
	 */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getMember_count() {
		return member_count;
	}
	public void setMember_count(Integer memberCount) {
		member_count = memberCount;
	}
	public Integer getConcerned_number() {
		return concerned_number;
	}
	public void setConcerned_number(Integer concernedNumber) {
		concerned_number = concernedNumber;
	}
	public String getCircle_ico() {
		return circle_ico;
	}
	public void setCircle_ico(String circleIco) {
		circle_ico = circleIco;
	}
	public String getJoin_type() {
		return join_type;
	}
	public void setJoin_type(String joinType) {
		join_type = joinType;
	}
	public Integer getCompany_num() {
		return company_num;
	}
	public void setCompany_num(Integer companyNum) {
		company_num = companyNum;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
	public Long getCircle_belonged() {
		return circle_belonged;
	}
	public void setCircle_belonged(Long circleBelonged) {
		circle_belonged = circleBelonged;
	}
	public Set<Circle> getJoineduser() {
		return joineduser;
	}
	public void setJoineduser(Set<Circle> joineduser) {
		this.joineduser = joineduser;
	}
	public String getCircle_pinyin() {
		return circle_pinyin;
	}
	public void setCircle_pinyin(String circlePinyin) {
		circle_pinyin = circlePinyin;
	}
	public String getCircle_level() {
		return circle_level;
	}
	public void setCircle_level(String circleLevel) {
		circle_level = circleLevel;
	}
	public Long getSendNumber() {
		return sendNumber;
	}
	public void setSendNumber(Long sendNumber) {
		this.sendNumber = sendNumber;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	public String getIspass() {
		return ispass;
	}
	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	public Integer getCircle_count() {
		return circle_count;
	}
	public void setCircle_count(Integer circleCount) {
		circle_count = circleCount;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getIsUserCreate() {
		return isUserCreate;
	}
	public void setIsUserCreate(String isUserCreate) {
		this.isUserCreate = isUserCreate;
	}
}
