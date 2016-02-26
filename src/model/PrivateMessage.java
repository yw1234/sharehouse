package model;

public class PrivateMessage extends Message implements java.io.Serializable{

	public PrivateMessage(){super ();}
	String isPublic = "0";
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	
	
}
