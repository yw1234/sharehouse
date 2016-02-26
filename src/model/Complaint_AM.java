package model;

public class Complaint_AM extends AdminMessage implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Complaint_AM(){super();}
	
	private Long senderid;
	private Long complainted_userid;
	private String reason;

	public Long getComplainted_userid() {
		return complainted_userid;
	}
	public void setComplainted_userid(Long complaintedUserid) {
		complainted_userid = complaintedUserid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getSenderid() {
		return senderid;
	}
	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}
	
}
