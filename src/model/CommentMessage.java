package model;

import java.util.Date;
public class CommentMessage extends Message implements java.io.Serializable{

	public CommentMessage(){super ();}

	//物品相关
	Long commShareId;
	String commType;
	String commShare;
	public Long getCommShareId() {
		return commShareId;
	}
	public void setCommShareId(Long commShareId) {
		this.commShareId = commShareId;
	}
	public String getCommShare() {
		return commShare;
	}
	public void setCommShare(String commShare) {
		this.commShare = commShare;
	}
	public String getCommType() {
		return commType;
	}
	public void setCommType(String commType) {
		this.commType = commType;
	}
	
	
}
