package model;

public class NoticeMessage extends Message implements java.io.Serializable{
	public NoticeMessage(){super();}
	
	String noticeShare;
	Long noticeShareId;
	String noticeType = "system";			//默认系统通知
	String additionalMessage;
	String isShareType = "0";
	public String getNoticeShare() {
		return noticeShare;
	}
	public void setNoticeShare(String noticeShare) {
		this.noticeShare = noticeShare;
	}
	public Long getNoticeShareId() {
		return noticeShareId;
	}
	public void setNoticeShareId(Long noticeShareId) {
		this.noticeShareId = noticeShareId;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getAdditionalMessage() {
		return additionalMessage;
	}
	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
	public String getIsShareType() {
		return isShareType;
	}
	public void setIsShareType(String isShareType) {
		this.isShareType = isShareType;
	}
	
	
}
