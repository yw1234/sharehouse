package activity.lostfound.service;

import java.util.List;
import java.util.Map;

import activity.lostfound.model.Lost;

public interface LostService {

	public boolean send(Lost lost);
	
	public List get(Map params,int size,int pn);
	
	public boolean addLostPicture(Long id,String []picList);
	
	public boolean delete(Long id);
	
	public Map getDetail(Long lostid);
}
