package model;

public class Suggest_AM extends AdminMessage implements java.io.Serializable{
	
public Suggest_AM(){super();}
	
	private Long senderid;
	private String sug_type;
	
	public Long getSenderid() {
		return senderid;
	}
	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}
	public String getSug_type() {
		return sug_type;
	}
	public void setSug_type(String sugType) {
		sug_type = sugType;
	}
	
}
