<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.msglist != null && #request.msglist.size > 0">
	<s:iterator id = "at" value = "#request.msglist" status = "at_index">
		<div style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;position:relative;' id = "at_${at.id}">
			<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'at_del_loading_${at.id}'><span>删除ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
			<a href = '${pageContext.request.contextPath}/user/${at.senderid}'>
				<img src = '${at.senderAvatar}' style = 'float:left;width:50px;height:50px;margin-left:20px;'/>
			</a>
			<div style = 'float:left;margin-left:20px;width:520px;'>
				<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'>
					<a href = '${pageContext.request.contextPath}/user/${at.senderid}' style='color:#e68303;'>${at.senderName} </a>
				 	<s:if test ='#at.atType == "goods"'>在一条物品发布中@了我 : </s:if>
				 	<s:elseif test ='#at.atType == "comment"'>在一条评论中@了我 : </s:elseif>
				</span>
				<span style = 'float:left;width:100%;margin-top:5px;text-align:left;'>
					“${at.content}”
				</span>
				<s:if test = '#at.atType == "goods" && #at.publish != null'>
					<div style = "float:left;margin-top:10px;width:500px;background:#f1f1f1;padding:10px;">
						<a href = '${pageContext.request.contextPath}/user/${at.publish.userid}'>
							<img src = '${at.publish.avatar}' style = 'float:left;width:40px;height:40px;'/>
						</a>
						<span style = 'float:left;margin-left:10px;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user/${at.publish.userid}' style='color:#e68303;'>${at.publish.name} </a> :
							<s:if test ='#at.publish.idleType == "sell"'>出售</s:if>
							<s:elseif test ='#at.publish.idleType == "lend"'>出借</s:elseif>
							<s:elseif test ='#at.publish.idleType == "gift"'>赠送</s:elseif>
							<a href = '${pageContext.request.contextPath}/details/goods/${at.atShareId}' target = "_blank" style = "color:#e68303;">${at.publish.idle}</a>
						</span>
						<span style = 'float:left;'>
							<s:if test = '#at.publish.idleImg1 != ""'>
								<a href ='${pageContext.request.contextPath}/details/goods/${at.atShareId}' target = '_blank' ><img src = "${at.publish.idleImg1}" style = "float:left;margin-left:10px;max-width:120px;max-height:120px;margin-top:8px;"/></a>
							</s:if>
							<s:if test = '#at.publish.idleImg2 != ""'>
								<a href ='${pageContext.request.contextPath}/details/goods/${at.atShareId}' target = '_blank' ><img src = "${at.publish.idleImg2}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;margin-top:8px;"/></a>
							</s:if>
							<s:if test = '#at.publish.idleImg3 != ""'>
								<a href ='${pageContext.request.contextPath}/details/goods/${at.atShareId}' target = '_blank' ><img src = "${at.publish.idleImg3}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;margin-top:8px;"/></a>
							</s:if>
						</span>
						<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
							<a href = "javascript:void(0);" style = "float:left;margin-left:50px;color:#e68303;" onclick = "commentReply.show({target:$('#comm_reply_container_${at.id}'),userName:'${at.publish.name}',userid:${at.publish.userid},type:'${at.atType}',typeid:${at.atShareId},share:'${at.publish.idle}'});">回复</a>
							<span style = 'float:left;margin-left:20px;color:#b1b1b1;font-size:13px;'>
								${at.publish.sendTime}
							</span>
						</span>
						<div id = 'comm_reply_container_${at.id}' style = 'float:left;width:100%;margin-top:5px;'></div>
					</div>
				</s:if>	
				<s:elseif test = '#at.atType == "comment"'>
					
				</s:elseif>
				<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
					<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:${at.senderid},userName:"${at.senderName}"});'>私信</a>
					<span style = 'float:left;margin-left:20px;color:#b1b1b1;font-size:13px;'>
						${at.sendTime}
					</span>
				</span>
			</div>
		<div style = 'float:left;width:60px;'>
			<a href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){atMessage.ignore({atid:${at.id}});},msg:"确认删除此条@信息?"});'><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:18px;margin-left:15px;margin-top:3px;" title="删除@信息"/></a>
		</div>
	</div>
	</s:iterator>
	<s:if test = "#request.atCount > #request.msgSize">
		<div style = "float:left;margin-top:15px;width:100%;">
			<ul style = "float:right;margin-right:20px;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first"  value= "1"  />    
                     <s:param name="last"  value= "#request.atCount/#request.msgSize+((#request.atCount%#request.msgSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    		<a href="${pageContext.request.contextPath}/message?page=at&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    		<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    	 </s:if>
                     <s:iterator status="stat">
                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     		<a href="${pageContext.request.contextPath}/message?page=at&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
                     	</s:if>
                     </s:iterator>
                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    	<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
                   		</s:if>
                   		<a href="${pageContext.request.contextPath}/message?page=at&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
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
		<span class = "msg_title">暂时没有@我的信息</span>
	</div>
</s:else>