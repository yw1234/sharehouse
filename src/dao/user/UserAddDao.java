package dao.user;

import java.util.Map;

public interface UserAddDao {
	//增加热度点数
	public boolean addHotSpot(Long typeid,String type,Integer number);
	//增加赞点数
	public boolean addFavor(Long userid,Long typeid,String type);
	//根据圈子名称加入分享圈
	public Map<String,Object> addCircleAtRegister(Long userid,String circlename);
	//将操作点数加1:(要操作的字段名称,字段所在表的列id,要操作的字段所属表)
	public boolean addOperationTimes(String fieldName,Long tableid,String tableName);
	//增加看过次数
	public boolean addLookedTimes(Long userid,Long typeid,String type);
	//添加上传头像
	public boolean addHeadIco(Long userid,String ico_path,String ico_path_big);
	//添加好友
	public boolean addFriends(Long userid,Long friendid);
	//添加关注的圈子
	public boolean addConcernedCircle(Long userid,Long circleid);
	//添加关注的好友
	public boolean addConcernedFriends(Long userid,Long friendid);
	//添加最近来访
	public boolean addVisited(Long userid,Long visitedid);
	//增加圈子内部发布数量,提高热度
	public boolean addCircleSendNumber(String[]cid);								//提升圈子热度
	//增加用户在某个圈子内的发帖活跃度
	public boolean addSendNumberInCircle(Long userid,String[]cid);					//提升自己在圈子中的热度
	//添加显示私密信息的圈子
	public boolean addShowPriCircle(Long userid,String clist);
	//添加显示私密信息的用户
	public boolean addShowPriUser(Long userid, String ulist);
	//添加屏蔽的私信
	public boolean addPmHidden(Long pmid,Long userid);
}
