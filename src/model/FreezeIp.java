package model;

public class FreezeIp implements java.io.Serializable{
	public FreezeIp(){}
	String ip;					//ip
	String freezeReason;			//原因
	String freezeType;			//封ip的操作
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFreezeReason() {
		return freezeReason;
	}
	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}
	public String getFreezeType() {
		return freezeType;
	}
	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}
	
}
