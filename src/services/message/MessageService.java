package services.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.base.BaseDao;

import model.AtMessage;
import model.CommentMessage;
import model.NoticeMessage;
import model.PrivateMessage;
import model.RequestMessage;

public interface MessageService {
	
	public BaseDao getBaseDao();
	
	//消息基本操作
	/**
	 * 获取各类未读消息数目
	 * @param userid
	 * @return
	 */
	public Map<String,Object> getUnReadMessageCount(Long userid);
	
	/**
	 * 未读总数
	 * @param userid
	 * @return
	 */
	public Map<String,Object> getUnReadMessageTotalCount(Long userid);
	
	/**
	 * 消息总数,不包括私信
	 * @param userid
	 * @param tableName
	 * @return
	 */
	public Object getMessageCount(Long userid,String tableName);
	
	/**
	 * 私信总数
	 * @param userid
	 * @param senderid
	 * @return
	 */
	public Object getPrivateMessageCount(Long userid,Long senderid);
	
	/**
	 * 获取未读消息列表
	 * @param userid
	 * @param unReadType
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getUnReadMessage(Long userid,String unReadType,int size,int pageno);
	
	/**
	 * 是否消息屏蔽
	 * @param userid
	 * @param shieldid
	 * @return
	 */
	public boolean isShieldMessage(Long userid,Long shieldid);
	
	/**
	 * 根据类型获取未读消息
	 * @param userid
	 * @param unReadType
	 * @param msgIdList
	 * @return
	 */
	public boolean deleteUnReadMessageByType(Long userid,String unReadType,String []msgIdList);
	
	/**
	 * 删除单个未读消息提醒
	 * @param messageid
	 * @return
	 */
	public boolean deleteUnReadMessageSingle(Long messageid);
	
	/**
	 * 插入未读消息提醒
	 * @param userid
	 * @param messageid
	 * @param unReadType
	 * @return
	 */
	public boolean insertUnReadMessage(Long userid,Long messageid,String unReadType);
	
	/**
	 * 获取用户请求
	 * @param userid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getRequestMessage(Long userid,int size,int pageno);
	
	/**
	 * 根据请求id获取
	 * @param reqid
	 * @return
	 */
	public Map getRequestMessage(Long reqid);
	
	/**
	 * 获取用户通知
	 * @param userid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getNoticeMessage(Long userid,int size,int pageno);
	
	/**
	 * 获取用户私信
	 * @param userid
	 * @param senderid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getPrivateMessage(Long userid,Long senderid,int size,int pageno);
	
	/**
	 * 根据被评论类id获取评论
	 * @param shareId
	 * @param shareType
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getCommentMessageByShareId(Long shareId,String shareType,int size,int pageno);
	
	/**
	 * 根据评论id获取评论
	 * @param commId
	 * @return
	 */
	public List getCommentMessageByCommId(Long commId);
	
	/**
	 * 删除请求信息
	 * @param messageid
	 * @return
	 */
	public boolean deleteRequestMessage(Long messageid);
	
	/**
	 * 删除通知
	 * @param messageid
	 * @return
	 */
	public boolean deleteNoticeMessage(Long messageid);
	
	/**
	 * 删除私信
	 * @param messageid
	 * @return
	 */
	public boolean deletePrivateMessage(Long messageid);
	
	/**
	 * 删除评论
	 * @param messageid
	 * @return
	 */
	public boolean deleteCommentMessage(Long messageid);
	
	/**
	 * 发送请求
	 * @param msg
	 * @return
	 */
	public boolean sendRequestMessage(RequestMessage msg);
	
	/**
	 * 发私信
	 * @param msg
	 * @return
	 */
	public boolean sendPrivateMessage(PrivateMessage msg);
	
	/**
	 * 发通知
	 * @param msg
	 * @return
	 */
	public boolean sendNoticeMessage(NoticeMessage msg);
	
	/**
	 * 发评论
	 * @param msg
	 * @return
	 */
	public boolean sendCommentMessage(CommentMessage msg);
	
	/**
	 * at
	 * @param msg
	 * @return
	 */
	public boolean sendAtMessage(AtMessage msg);
	
	//request类其他业务处理
	/**
	 * 判断是否请求过
	 * @param userid
	 * @param senderid
	 * @param reqInfo
	 * @return
	 */
	public boolean isRequested(Long userid,Long senderid,Map reqInfo);	
	/**
	 * 变为已处理
	 * @param reqid
	 * @return
	 */
	public boolean updateToDealComplete(Long reqid);
	/**
	 * 判断是否已处理
	 * @param reqid
	 * @param userid
	 * @param senderid
	 * @return
	 */
	public boolean isDealComplete(Long reqid,Long userid,Long senderid);
	/**
	 * 判断是否有此条请求
	 * @param userid
	 * @param messageid
	 * @return
	 */
	public boolean isRequestMessageExists(Long userid,Long messageid);
	/**
	 * 此物品是否已经分享完成,仅限isShareType为1,即物品分享或需求
	 * @param idleid
	 * @return
	 */
	public boolean isIdleShared(Long idleid);
	
	//private类其他业务处理
	/**
	 * 是否可发送私信。被发送者为自己则不能
	 * @param userid
	 * @param senderid
	 * @return
	 */
	public boolean canBeSend(Long userid,Long senderid);
	/**
	 * 删除私信
	 * @param id
	 * @param userid
	 * @return
	 */
	public boolean hidePrivateMessage(Long id,Long userid);
	
	/**
	 * 获取好友私信
	 * @param friendsid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getFriendsPrivateMessage(Long friendsid,int size,int pageno);
	
	/**
	 * 获取好友私信数量
	 * @param userid
	 * @return
	 */
	public Object getFriMsgNumber(Long userid);
	
	/**
	 * 获取私信最近一次发送时间
	 * @param userid
	 * @return
	 */
	public Date getPrivateLastSendTime(Long userid);
	
	//notice类其他业务处理
	
	//comment类其他业务处理
	/**
	 * 是否已经评论过此条发布
	 * @param userid
	 * @param typeId
	 * @param type
	 * @return
	 */
	public boolean isCommented(Long userid,Long typeId,String type);	
	/**
	 * 获取上次留言发布时间
	 * @param userid
	 * @return
	 */
	public Date getCommentLastSendTime(Long userid);
	/**
	 * 判断留言是否发过
	 * @param userid
	 * @param messageid
	 * @return
	 */
	public boolean isCommentMessageExists(Long userid,Long messageid);
	/**
	 * 获取评论过的发布id及类型列表
	 * @param userid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getCommentedShare(Long userid , int size , int pageno);
	
	/**
	 * 获取评论过的发布总数
	 * @param userid
	 * @return
	 */
	public Object getCommentedShareCount(Long userid);
	
	/**
	 * 获取评论过的发布相关信息
	 * @param shareId
	 * @param shareType
	 * @param userid
	 * @return
	 */
	public Map getCommentedShareInfo(Long shareId,String shareType,Long userid);
	
	/**
	 * 屏蔽评论过的消息
	 * @param shareId
	 * @param shareType
	 * @param userid
	 * @return
	 */
	public boolean hideCommentedMessage(Long shareId,String shareType,Long userid);
	
	//at相关
	/**
	 * 屏蔽评论过的消息
	 * @param userid
	 * @return
	 */
	public List getAtFriends(Long userid);
	
	/**
	 * 删除at信息
	 * @param messageid
	 * @return
	 */
	public boolean deleteAtMessage(Long messageid ,Long loggedUid);
	
	/**
	 * 获取at消息列表
	 * @param userid
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List getAtMessage(Long userid, int size, int pageno);
	
	/**
	 * 获取基于at消息的原评论或发布
	 * @param id
	 * @param type
	 * @return
	 */
	public Map getAtPublishInfo(Long shareId,String shareType,Long loggedUid);
	
}
