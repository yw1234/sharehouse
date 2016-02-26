package model;

public class Picture implements java.io.Serializable{
	
	Long id;
	Long typeid;
	String type;
	String url;
	String thumbs_url;
	String tiny_url;
	String is_shared;			//图片所对应的物品是否已出,仅限物品类有效
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTypeid() {
		return typeid;
	}
	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbs_url() {
		return thumbs_url;
	}
	public void setThumbs_url(String thumbsUrl) {
		thumbs_url = thumbsUrl;
	}
	public String getTiny_url() {
		return tiny_url;
	}
	public void setTiny_url(String tiny_url) {
		this.tiny_url = tiny_url;
	}
	public String getIs_shared() {
		return is_shared;
	}
	public void setIs_shared(String is_shared) {
		this.is_shared = is_shared;
	}
	
	
}
