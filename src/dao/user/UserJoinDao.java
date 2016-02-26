package dao.user;

import java.util.List;

public interface UserJoinDao {
	public boolean joincircle(Long userid , String []circleid);
	public boolean isJoined(Long userid,Long circleid);
}
