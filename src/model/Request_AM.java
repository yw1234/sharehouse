package model;

public class Request_AM extends AdminMessage implements java.io.Serializable{
	
public Request_AM(){super();}
	
	private Long senderid;
	private String req_type;
	private String req_content;
	private Long remark_id;
	
	public Long getSenderid() {
		return senderid;
	}
	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}
	public String getReq_type() {
		return req_type;
	}
	public void setReq_type(String reqType) {
		req_type = reqType;
	}
	public String getReq_content() {
		return req_content;
	}
	public void setReq_content(String reqContent) {
		req_content = reqContent;
	}
	public Long getRemark_id() {
		return remark_id;
	}
	public void setRemark_id(Long remarkId) {
		remark_id = remarkId;
	}
	
}
