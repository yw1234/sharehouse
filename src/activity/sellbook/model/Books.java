package activity.sellbook.model;

import model.Share;

public class Books extends Share implements java.io.Serializable{

	public Books(){super();}
	
	String name;							//分享物品
	Integer price;						//价格
	Integer realPrice;					//原价
	Integer stock;						//库存
	String old_degree;					//新旧程度
	String type;							//大类
	String scope;						//范围
	Integer lookedtimes = 0;				//被看次数
	Integer requiredtimes = 0;			//请求次数
	String firstimg_url = "";			//便签展示图片url
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getOld_degree() {
		return old_degree;
	}
	public void setOld_degree(String old_degree) {
		this.old_degree = old_degree;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
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
	public String getFirstimg_url() {
		return firstimg_url;
	}
	public void setFirstimg_url(String firstimg_url) {
		this.firstimg_url = firstimg_url;
	}
	public Integer getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Integer realPrice) {
		this.realPrice = realPrice;
	}
	
	
}
