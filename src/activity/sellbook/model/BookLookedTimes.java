package activity.sellbook.model;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class BookLookedTimes implements java.io.Serializable{

	public BookLookedTimes(){}
	Long userid;
	Long bookid;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getBookid() {
		return bookid;
	}
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	
	public int hashCode(){
	   return new HashCodeBuilder(-528253723, -475504089)
	      .appendSuper(super.hashCode())
	      .append(this.userid)
	      .append(this.bookid)
	      .toHashCode();
	}
}
