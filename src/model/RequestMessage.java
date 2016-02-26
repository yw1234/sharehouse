package model;

public class RequestMessage extends Message implements java.io.Serializable{
	
	public RequestMessage(){super();}
	
	String reqMessage;
	String reqType;
	//物品相关
	String reqShare;				//	idleName
	Long reqShareId;				//	idleId
	String reqShareMethod;		//	分享方式
	String reqDelivery;			//	送货方式
	String isAccept = "0";
	public String getReqMessage() {
		return reqMessage;
	}
	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getReqShare() {
		return reqShare;
	}
	public void setReqShare(String reqShare) {
		this.reqShare = reqShare;
	}
	
	public Long getReqShareId() {
		return reqShareId;
	}
	public void setReqShareId(Long reqShareId) {
		this.reqShareId = reqShareId;
	}
	public String getReqShareMethod() {
		return reqShareMethod;
	}
	public void setReqShareMethod(String reqShareMethod) {
		this.reqShareMethod = reqShareMethod;
	}
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public String getReqDelivery() {
		return reqDelivery;
	}
	public void setReqDelivery(String reqDelivery) {
		this.reqDelivery = reqDelivery;
	}
	
	
	
}
