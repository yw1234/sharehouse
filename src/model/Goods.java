package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Goods extends Share{
	
	public Goods(){super();}
	String type;							//分享类型
	String goods;						//分享物品
	Integer reward = 0;					//奖励积分
	String is_shared = "0";				//是否已分享出 0 未分享 1 已分享 
	String obj_qq;						//qq
	String obj_phone;					//联系电话
	String goodstype_1;					//物品类别1
	String goodstype_2;					//物品类别2
	String goodstype_3;					//物品类别3
	Integer price;						//价格
	String totaltime;					//分享时长
	String old_degree;					//新旧程度
	String usedtime;						//已用时长
	Integer boughtprice;					//原价
	String contactperson;				//联系人
	String show_inhome="1";				//是否在主页显示  0 不显示
	Integer lookedtimes = 0;				//被看次数
	Integer requiredtimes = 0;			//请求次数
	Integer replytimes = 0;				//发布者回复次数
	Integer comment_size = 0;			//评论总数
	String firstimg_url = "";			//便签展示图片url
	Integer savetime;					//便签存在时长
	String show_privacy = "0";			//显示隐私
	Integer hotspot = 0;					//热度
	String goodslink = "";				//原物链接
	String bargain = "";					//刀
	String idle_alphabets = "";			//拼音
		
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
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
	public String getGoodstype_1() {
		return goodstype_1;
	}
	public void setGoodstype_1(String goodstype_1) {
		this.goodstype_1 = goodstype_1;
	}
	public String getGoodstype_2() {
		return goodstype_2;
	}
	public void setGoodstype_2(String goodstype_2) {
		this.goodstype_2 = goodstype_2;
	}
	public String getGoodstype_3() {
		return goodstype_3;
	}
	public void setGoodstype_3(String goodstype_3) {
		this.goodstype_3 = goodstype_3;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setBoughtprice(Integer boughtprice) {
		this.boughtprice = boughtprice;
	}
	public Integer getBoughtprice() {
		return boughtprice;
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
	public String getUsedtime() {
		return usedtime;
	}
	public void setUsedtime(String usedtime) {
		this.usedtime = usedtime;
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
	public Integer getReplytimes() {
		return replytimes;
	}
	public void setReplytimes(Integer replytimes) {
		this.replytimes = replytimes;
	}
	public Integer getComment_size() {
		return comment_size;
	}
	public void setComment_size(Integer commentSize) {
		comment_size = commentSize;
	}
	public String getFirstimg_url() {
		return firstimg_url;
	}
	public void setFirstimg_url(String firstimgUrl) {
		firstimg_url = firstimgUrl;
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
	public Integer getHotspot() {
		return hotspot;
	}
	public void setHotspot(Integer hotspot) {
		this.hotspot = hotspot;
	}
	public String getGoodslink() {
		return goodslink;
	}
	public void setGoodslink(String goodslink) {
		this.goodslink = goodslink;
	}
	public String getBargain() {
		return bargain;
	}
	public void setBargain(String bargain) {
		this.bargain = bargain;
	}
	public String getIdle_alphabets() {
		return idle_alphabets;
	}
	public void setIdle_alphabets(String idle_alphabets) {
		this.idle_alphabets = idle_alphabets;
	}
	
	
}
