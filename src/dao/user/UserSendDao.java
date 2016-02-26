package dao.user;

public interface UserSendDao {
	//建议
	public boolean sendSuggest(Long userid,String content);
	//向后台管理员发请求
	public boolean sendAdminRequest(Long userid,String content,String req_type,String req_content,Long remark_id);
	//向圈管理员发请求
	public boolean sendCircleRequest(Long userid,Long circleid,String content,String req_type,String req_content);
	//举报
	public boolean sendComplaint(Long userid,Long com_userid,String reason,String content);
	//物品需求
	public Long sendGoods(Long userid,String type,String goods,String content,Integer price,String old_degree,String phone,String qq,String contactperson,Integer savetime,String fpic,String show_pri,String t1,String t2,String lendtime,String goodslink);
	public Long sendNeeds(Long userid,String type,String needs,String content,String price,String old_degree,String phone,String qq,String contactperson,Integer savetime,String show_pri,String t1,String t2,String borrowtime);
	//评论
	//public Long sendComment(Long userid,Long senderid,Long typeid,String type,String content);
	//发布到圈中
	public boolean sendToCircle(Long userid,Long typeid,String type,String circleid[]);
	//发送物品图片
	public boolean addPicture(Long id,String type, String []pic);
	
	//消息一类
	/*
	public boolean sendRequestMessage(Long userid,Long senderid,Long reqid,String type,String content);
	public boolean sendRequestMessage(Request_Msg rmsg);
	public boolean sendResponseMessage(Long reqid,Long userid,Long senderid,Long resid,String type,String op,String req_object,String content,String moreinfo,String To);
	public boolean sendNoticeMessage(Long userid,String type ,String content,Object[]parm,Integer pnum);
	public boolean sendPrivateMessage(Long userid,Long senderid,String title,String pri_content,String showPri);
	*/
}
