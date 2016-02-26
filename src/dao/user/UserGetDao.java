package dao.user;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import model.Picture;
import model.User;

public interface UserGetDao {
	/**
	 * api调用方法
	 * */
	//获取用户信息   1常规,2全部
	public List api_getUserInfo(Long userid,Integer infotype);
	//获取各种数量
	public Object api_getFriendsCount(Long userid);
	public Object api_getCircleCount(Long userid);
	public Object api_getMessageCount(Long userid);
	public Object api_getGoodsCount(Long userid);
	public Object api_getNeedsCount(Long userid);
	/**
	 * web调用方法
	 * */
	//获取api_key
	public boolean getAPIKey(String api_key);
	//获取密码
	public String getPassword(Long userid);
	//获取上次发布时间
	public Date getLastSendTime(Long userid);
	//获取上级圈子信息
	public List getSuperiorCircle(Long circleid);
	//获取好友留言板留言总数
	public Object getFriMsgNumber(Long userid); 
	//获取和某人的私信总数
	public Object getPrivateMsgNumber(Long userid,Long senderid); 
	//是否请求过
	public Long isRequest(Long userid,Long msgid,String type);
	//管理的分享圈
	public List getManageCircle(Long userid);
	//经常访问的分享圈
	public List getRegularlyVisitedCircle(Long userid ,int size,int pageno,int minVisitedTimes);
	//展示留言
	public List getFriPriMsg(Long userid,int size,int pageno);
	//是否已分享
	public boolean getIsShared(Long typeid,String type);
	//是否可获取隐私信息 1主页 2分享圈 3 好友
	public boolean getCanSeePriInfo(Long lookedid,Long userid,String field);
	//获取好友的朋友列表
	public List getFriFriendsList(Long id,Long userid,int size,int pageno);
	//根据省份获取学校
	public List getSchoolList(String province);
	//获取评论位置
	public String getCommentLocation(Long responseid); 
	//是否评论过,判断是否加经验
	public boolean getIsCommented(Long userid,Long typeid,String type);
	//获取用户所在的所有圈子
	public List getAllCircle(Long userid,String ctype);
	//获取上次访问时间,判断是否增加访问量
	public Date getLastVisitedTime(Long userid,Long friendid);
	//获取用户
	public User getUser(Long userid);
	//获取热门分享,需求
	public List getHotGoodsList(Long userid,int size,int pageno,String parm);
	public List getHotNeedsList(Long userid,int size,int pageno,String parm);
	//关注我的用户列表
	public List getConcernedMe(Long userid);
	//我关注的用户列表
	public List getConcernedFriends(Long userid);
	//黑名单
	public List getBlackList(Long userid,int size,int pageno); 
	//是否可看私密信息(联系方式)
	public boolean getCanSeePrivacy(Long userid,Long typeid,String type);
	//获取热门分享圈,按圈内发帖数量
	public List getHotCircle(int size,int pageno);
	//获取最近来访名单
	public List getLastVisitedList(Long userid,int size,int pageno);
	//我加入的分享圈列表
	public List getCircleList(Long userid,int size,int pageno);
	//获取共同圈子列表
	public List getCommonCircleList(Long userid,Long friendid,int size,int pageno);
	//获取加入的圈子数量
	public Object getCircleNumber(Long userid);
	//获取好友列表,isAll包括关注的
	public List getFriendsList(Long userid,int size,int pageno,boolean isAll,String condition);
	//获取共同好友列表
	public List getCommonFriendsList(Long userid,Long friendid,int size,int pageno);
	//获取好友数量
	public BigInteger getFriendsNumber(Long userid);
	//搜索物品,需求数量
	public List getSearchGoodsList(Object[]parm,int size,int pageno);
	public List getSearchNeedsList(Object[]parm,int size,int pageno);
	//获取个人发布的物品,需求
	public List getGoodsList(Long userid,int size,int pageno,String parm);
	public List getNeedsList(Long userid,int size,int pageno,String parm);
	//获取好友发布的物品,需求,分为关注的和非关注的
	public List getFriendsGoodsList(Long userid,int size,int pageno,boolean isconcerned,String parm);
	public List getFriendsNeedsList(Long userid,int size,int pageno,boolean isconcerned,String parm);
	//获取陌生人发布的物品,需求
	public List getStrangerGoodsList(Long userid,Long sid,int size,int pageno,String parm);
	public List getStrangerNeedsList(Long userid,Long sid,int size,int pageno,String parm);
	//获取物品照片
	public List<Picture> getPictureList(Long typeid ,String type, int size,int pageno);
	//获取评论列表,分别为根据评论id和发布的信息id及类型
	public List getCommentList(String commid,int size,int pageno);
	public List getCommentListByType(Long typeid,String type,int size,int pageno);
	//获取物品,需求详情
	public Object[] getGoodsDetails(Long goodsid);
	public Object[] getNeedsDetails(Long needsid);
	//判断是否发出分享请求过,只能请求一次
	public Integer getIsRequest(Long userid , Long reqid,String type);
	//判断是否是好友
	public Integer getIsFriend(Long userid,Long friendid);
	//获取关注的圈子
	public List getConcernedCircle(Long userid,Integer size,Integer pageno);
	//我的圈子以及下属圈子
	public List getMyCircle(Long userid,Long circleid,int size , int pageno);
	//可能喜欢的圈子,根据所在地
	public List getMayLikeCircle(Object[] obj,int size,int pageno);
	//未读信息列表
	public List getNotReadMessage(Long userid,int size,int pageno);
	//未读信息数量
	public Integer getNotReadNumber(Long userid);
	//获取消息回复
	public List getResponseReply(Long rid,String type);
	//消息总数量
	public Object getMsgTotalNumber(Long user,String table);
	//请求信息
	public List getRequest(Long userid,int size,int pageno);
	//回复信息
	public List getResponse(Long userid,int size,int pageno);
	//私信,可获取和某个人的单独对话
	public List getPrivate(Long userid,Long senderid,int size,int pageno);
	//通知
	public List getNotice(Long userid,int size,int pageno);
	//可能认识的人
	public List getMayKnowUser(Long userid,int size,int pageno);
	//查找的圈子列表
	public List getSearchCircle(Long userid,int size,int pageno,Object[]parm);
	//获取显示私密信息的圈子的id字符串,用","隔开
	public String getShowPriCircle(Long userid);
	//获取显示私密信息的用户的id字符串,用","隔开
	public String getShowPriUser(Long userid);
	
	
}
