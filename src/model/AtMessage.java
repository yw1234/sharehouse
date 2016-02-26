package model;

public class AtMessage extends Message implements java.io.Serializable{
	public AtMessage(){super();}
	
	String atType;
	Long atShareId;
	
	public String getAtType() {
		return atType;
	}
	public void setAtType(String atType) {
		this.atType = atType;
	}
	public Long getAtShareId() {
		return atShareId;
	}
	public void setAtShareId(Long atShareId) {
		this.atShareId = atShareId;
	}
	
	
}
