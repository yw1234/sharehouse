package dao.user;

public interface UserRemoveDao {
	public boolean removecircle(Long userid , Long circleid);
	public boolean removeConcernedFriends(Long userid,Long friends);
}
