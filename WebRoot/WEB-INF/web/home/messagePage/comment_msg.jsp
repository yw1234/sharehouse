<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.msglist != null && #request.msglist.size > 0">
	<s:iterator id = "comm" value = "#request.msglist" status = "comm_index">
		<div style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;position:relative;' id = "comm_${comm.shareType}_${comm.shareId}">
			<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'comm_del_loading_${comm.shareType}_${comm.shareId}'><span>屏蔽ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
			<a href = '${pageContext.request.contextPath}/user/${comm.userid}'>
				<img src = '${comm.avatar}' style = 'float:left;width:50px;height:50px;margin-left:20px;'/>
			</a>
			<div style = 'float:left;margin-left:20px;width:520px;'>
				<s:if test = '#comm.shareType == "goods"'>
					<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user/${comm.userid}' style='color:#e68303;'>${comm.name} </a> :
						<s:if test ='#comm.idleType == "sell"'>出售</s:if>
						<s:elseif test ='#comm.idleType == "lend"'>出借</s:elseif>
						<s:elseif test ='#comm.idleType == "gift"'>赠送</s:elseif>
						<a href = '${pageContext.request.contextPath}/details/goods/${comm.shareId}' target = "_blank" style = "color:#e68303;">${comm.idle}</a>
					</span>
					<s:if test = '#comm.content != null && #comm.content !=""'>
						<span style = 'float:left;width:100%;margin-top:5px;text-align:left;'>
							“${comm.content}”
						</span>
					</s:if>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
						<s:if test = '#comm.idleImg1 != ""'>
							<a href ='${pageContext.request.contextPath}/details/goods/${comm.shareId}' target = '_blank' ><img src = "${comm.idleImg1}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
						<s:if test = '#comm.idleImg2 != ""'>
							<a href ='${pageContext.request.contextPath}/details/goods/${comm.shareId}' target = '_blank' ><img src = "${comm.idleImg2}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
						<s:if test = '#comm.idleImg3 != ""'>
							<a href ='${pageContext.request.contextPath}/details/goods/${comm.shareId}' target = '_blank' ><img src = "${comm.idleImg3}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
					</span>
					<span style = 'float:left;width:100%;margin-top:5px;text-align:left;font-size:14px;'>
						<a href = "javascript:void(0);" style = "float:left;margin-left:5px;color:#e68303;" onclick = "commentReply.show({target:$('#comm_reply_container_${comm.shareType}_${comm.shareId}'),userName:'${comm.name}',userid:${comm.userid},type:'${comm.shareType}',typeid:${comm.shareId},share:'${comm.idle}'});">回复</a>
						<s:if test = '#comm.userid != #session.userid'>
							<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-left:20px;' onclick = 'replyBox.show({userid:${comm.userid},userName:"${comm.name}"});'>私信</a>
						</s:if>
						<span style = 'float:left;margin-left:20px;color:#b1b1b1;font-size:13px;'>
							${comm.sendTime}
						</span>
					</span>
					<div id = 'comm_reply_container_${comm.shareType}_${comm.shareId}' style = 'float:left;width:100%;margin-top:5px;'></div>
				</s:if>
				<s:if test = '#request.comm.comment != null && #request.comm.comment.size > 0'>
					<div id = "comment_block_${comm.shareType}_${comm.shareId}" style = "float:left;width:100%;margin-top:5px;background:#f1f1f1;">
						<s:iterator id = "commList" value = "#request.comm.comment">
							<div style = "float:left;width:500px;margin:5px;">
								<img src = "${commList.senderAvatar}" style = "float:left;width:30px;height:30px;"/>
								<span style = "float:left;text-align:left;margin-left:10px;width:450px;font-size:13px;">
									<a href = '${pageContext.request.contextPath}/user/${commList.senderid}' style = 'color:#e68303;'>${commList.senderName}</a> : ${commList.content}
									<span style = "float:left;text-align:left;margin-top:5px;width:450px;font-size:13px;">
										<a href = "javascript:void(0);" style = "float:left;color:#e68303;" onclick = "commentReply.show({target:$('#comm_reply_container_${comm.shareType}_${comm.shareId}'),userName:'${commList.senderName}',userid:${commList.senderid},type:'${commList.commType}',typeid:${commList.commShareId},share:'${commList.commShare}'});">回复</a>
										<span style = "float:left;color:#b1b1b1;margin-left:20px;">
											${commList.sendTime}
										</span>
									</span>
								</span>
							</div>
							<div style = 'float:left;width:510px;margin-left:5px;border-bottom:1px solid #d1d1d1;border-radius:0px;'></div>
						</s:iterator>
						<s:if test = "#request.comm.comment.size == 3">
							<div style = float:left;width:100%;height:25px;line-height:25px;font-size:13px;color:#e68303;cursor:pointer; onclick = "commentReply.getMore({tb:$('#comment_block_${comm.shareType}_${comm.shareId}'),target:$(this),pn:0,type:'${commList.commType}',typeid:${commList.commShareId}});">查看更多留言信息</div>
						</s:if>
					</div>
				</s:if>
			</div>
			<div style = 'float:left;width:60px;'>
				<a href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){shield.commented({id:${comm.shareId},type:"${comm.shareType}",target:$("#comm_${comm.shareType}_${comm.shareId}")});},msg:"确认忽略此条相关信息?"});'><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:18px;margin-left:15px;margin-top:3px;" title="删除相关信息"/></a>
			</div>
		</div>
	</s:iterator>
	<s:if test = "#request.commentCount > #request.msgSize">
		<div style = "float:left;margin-top:15px;width:100%;">
			<ul style = "float:right;margin-right:20px;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first"  value= "1"  />    
                     <s:param name="last"  value= "#request.commentCount/#request.msgSize+((#request.commentCount%#request.msgSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    		<a href="${pageContext.request.contextPath}/message?page=comm&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    		<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    	 </s:if>
                     <s:iterator status="stat">
                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     		<a href="${pageContext.request.contextPath}/message?page=comm&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
                     	</s:if>
                     </s:iterator>
                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    	<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
                   		</s:if>
                   		<a href="${pageContext.request.contextPath}/message?page=comm&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
                   	</s:if>    
                 </s:bean>
			</ul>
		</div>
	</s:if>
	<div style = "float:left;width:100%;height:30px;"></div>
</s:if>
<s:else>
	<div id = "msg_default_top">
		<img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:16px;"/>
		<span class = "msg_title">暂时没有与我相关的评论</span>
	</div>
</s:else>