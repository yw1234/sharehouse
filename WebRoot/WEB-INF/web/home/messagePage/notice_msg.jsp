<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.msglist != null && #request.msglist.size > 0">
	<s:iterator id = "no" value = "#request.msglist" status = "no_index">
		<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;position:relative;' id = "notice_${no.id}">
			<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'notice_del_loading_${no.id}'><span>删除ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
			<a href = '${pageContext.request.contextPath}/user/${no.senderid}'><img src = '${no.senderAvatar}' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>
			<div style = 'float:left;margin-left:20px;width:520px;'>
				<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user/${no.senderid}' style='color:#e68303;'>${no.senderName}</a>${no.content}</span>
				<s:if test = '#no.isShareType=="1"'>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>物品详情 : <a href ='${pageContext.request.contextPath}/details/${no.noticeType}/${no.noticeShareId}' target = '_blank' style = 'color:#e68303;'>${no.noticeShare}</a></span>
				</s:if>
				<s:if test = '#no.additionalMessage!=""'>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>附加信息 : ${no.additionalMessage}</span>
				</s:if>
				<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
					<s:if test = '#no.isShareType=="1"'>
						<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:${no.senderid},userName:"${no.senderName}"});'>私信</a>
						<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:50px;' onclick = 'orderBox.load({idleid:${no.noticeShareId}});'>分享单</a>
					</s:if>
					<span style = 'float:left;color:#c1c1c1;'>${no.sendTime}</span>
				</span>
			</div>
			<div style = 'float:left;width:60px;'>
				<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){notice.ignore({noid:${no.id}});},msg:"确认删除此条通知?"});'><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:18px;margin-left:15px;margin-top:3px;" title="删除通知"/></a>
			</div>
		</div>
	</s:iterator>
	<s:if test = "#request.noticeCount > #request.msgSize">
		<div style = "float:left;margin-top:15px;width:100%;">
			<ul style = "float:right;margin-right:20px;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first"  value= "1"  />    
                     <s:param name="last"  value= "#request.noticeCount/#request.msgSize+((#request.noticeCount%#request.msgSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    		<a href="${pageContext.request.contextPath}/message?page=notice&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    		<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    	 </s:if>
                     <s:iterator status="stat">
                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     		<a href="${pageContext.request.contextPath}/message?page=notice&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
                     	</s:if>
                     </s:iterator>
                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    	<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
                   		</s:if>
                   		<a href="${pageContext.request.contextPath}/message?page=notice&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
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
		<span class = "msg_title">暂时没有新的通知</span>
	</div>
</s:else>