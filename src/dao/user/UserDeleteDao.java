package dao.user;

public interface UserDeleteDao {
	//减少1次操作次数,和add相对
	public boolean decreaseOperationTimes(String op,Long typeid,String type);
	//减少圈中分享
	public boolean decreaseSendActiveInCircle(Long userid,Long circleid);
	//删除好友
	public boolean deleteFriends(Long userid,Long friendid);
	//删除圈中发布的物品和需求,但不删除发布,解除连接关系(管理员操作)
	public boolean deleteGoodsInCircle(Long userid,Long typeid,Long cid);
	public boolean deleteNeedsInCircle(Long userid,Long typeid,Long cid);
	//删除发布的物品和需求
	public boolean deleteGoods(Long userid,Long id,String path); 
	public boolean deleteNeeds(Long userid,Long id);
	//删评论
	public boolean deleteComment(Long id);
	//删除消息
	public boolean deleteMessage(Long id,String type);
	//批量删除消息
	public boolean deleteAllMessage(Long userid,String type);
	//退出某个分享圈
	public boolean deleteUserInCircle(Long userid,Long circleid,String delSendMark);
}
