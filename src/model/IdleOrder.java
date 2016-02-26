package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class IdleOrder implements java.io.Serializable{

	public IdleOrder(){}
	Long id;							//订单号
	Long idleid;						//闲置id
	Long seller_uid;					//卖家id
	Long buyer_uid;					//买家id
	String idle;						//闲置名称
	String shareType;				//处理方式
	Integer price;					//价格，仅卖
	String lendTimeLong;				//借出时长
	Date add_time;					//成交时间
	String deliveryType;				//送货方式
	String deliveryLocation;			//送货地点
	String pickupLocation;			//取货地点
	Integer fare;					//运费
	String hub_flag = "0";			//中转获取标注
	String isComplete = "0";			//交易是否完成
	String remark;					//备注
	Set<Logistic>logistic = new HashSet<Logistic>();			//物流信息
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdleid() {
		return idleid;
	}
	public void setIdleid(Long idleid) {
		this.idleid = idleid;
	}
	public Long getSeller_uid() {
		return seller_uid;
	}
	public void setSeller_uid(Long seller_uid) {
		this.seller_uid = seller_uid;
	}
	public Long getBuyer_uid() {
		return buyer_uid;
	}
	public void setBuyer_uid(Long buyer_uid) {
		this.buyer_uid = buyer_uid;
	}
	public String getIdle() {
		return idle;
	}
	public void setIdle(String idle) {
		this.idle = idle;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getLendTimeLong() {
		return lendTimeLong;
	}
	public void setLendTimeLong(String lendTimeLong) {
		this.lendTimeLong = lendTimeLong;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryLocation() {
		return deliveryLocation;
	}
	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}
	public String getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public Integer getFare() {
		return fare;
	}
	public void setFare(Integer fare) {
		this.fare = fare;
	}
	public String getHub_flag() {
		return hub_flag;
	}
	public void setHub_flag(String hub_flag) {
		this.hub_flag = hub_flag;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<Logistic> getLogistic() {
		return logistic;
	}
	public void setLogistic(Set<Logistic> logistic) {
		this.logistic = logistic;
	}
	
	
	
	
}
