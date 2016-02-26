<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.msglist != null && #request.msglist.size > 0">
	<s:if test = '%{#parameters.senderid!=null}'>
		<span style = "float:left;width:100%;text-align:left;margin:5px 0px 10px 0px;">与<a style = "color:#e68303;" href = "${pageContext.request.contextPath}/user?page=main&type=share&id=${senderInfo.id}">${senderInfo.realname}</a>的私信记录</span>
		<s:iterator id = "pri" value = "#request.msglist" status = "pri_index">
			<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = "pri_${pri.id}">
				<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'pri_del_loading_${pri.id}'><span>删除ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
				<a href = '${pageContext.request.contextPath}/user/${pri.senderid}'><img src = '${pri.senderAvatar}' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>
				<div style = 'float:left;margin-left:20px;width:520px;'>
					<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user/${pri.senderid}' style='color:#e68303;'>${pri.senderName}</a> : ${pri.content}</span>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
						<s:if test = '#pri.senderid!=#session.userid'>
							<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:${pri.senderid},userName:"${pri.senderName}"});'>私信</a>
						</s:if>
						<span style = 'float:left;color:#c1c1c1;'>${pri.sendTime}</span>
					</span>
				</div>
				<div style = 'float:right;width:60px;'>
					<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){privateMsg.ignore({priid:${pri.id}});},msg:"确认忽略此条私信?"});'><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:18px;margin-left:15px;margin-top:3px;" title="忽略私信"/></a>
				</div>
			</div>
		</s:iterator>
	</s:if>
	<s:else>
		<s:iterator id = "pri" value = "#request.msglist" status = "pri_index">
			<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;cursor:pointer;' id = "pri_${pri.id}" onclick = "location.href='${pageContext.request.contextPath}/message?page=pri&p=1&senderid=${pri.senderid}'">
				<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'pri_del_loading_${pri.id}'><span>删除ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
				<a href = '${pageContext.request.contextPath}/user/${pri.senderid}'><img src = '${pri.senderAvatar}' style = 'float:left;width:50px;height:50px;margin-left:20px;'/></a>
				<div style = 'float:left;margin-left:20px;width:520px;'>
					<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user?page=main&type=share&id=${pri.senderid}' style='color:#e68303;'>${pri.senderName}</a>
					</span>
					<s:if test = '#pri.content!=null && #pri.content!=""'>
						<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
							 <label style = 'color:#e68303;'>最近私信 : </label> ${pri.content}	
						</span>
					</s:if>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
						<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:${pri.senderid},userName:"${pri.senderName}"});'>私信</a>
						<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'confirmBox.show({func:function(){shield.user({userid:${pri.senderid},target:$("#pri_${pri.id}")});},msg:"确认屏蔽${pri.senderName}所发信息?"});'>屏蔽此人信息</a>
						<a href = '${pageContext.request.contextPath}/message?page=pri&p=1&senderid=${pri.senderid}' style = 'float:left;color:#e68303;margin-right:50px;'>查看记录</a>
					</span>
				</div>
			</div>
		</s:iterator>
	</s:else>
	<s:if test = "#request.priCount > #request.msgSize">
		<div style = "float:left;margin-top:15px;width:100%;">
			<ul style = "float:right;margin-right:20px;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first" value= "1"/>    
                     <s:param name="last" value= "#request.priCount/#request.msgSize+((#request.priCount%#request.msgSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    		<a href="${pageContext.request.contextPath}/message?page=pri<s:if test='%{#parameters.senderid!=null}'>&senderid=${senderid}</s:if>&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    		<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    	 </s:if>
                     <s:iterator status="stat">
                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     		<a href="${pageContext.request.contextPath}/message?page=pri<s:if test='%{#parameters.senderid!=null}'>&senderid=${senderid}</s:if>&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
                     	</s:if>
                     </s:iterator>
                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    		<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    		<a href="${pageContext.request.contextPath}/message?page=pri<s:if test='%{#parameters.senderid!=null}'>&senderid=${senderid}</s:if>&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
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
		<span class = "msg_title">暂时没有人给我私信</span>
	</div>
</s:else>