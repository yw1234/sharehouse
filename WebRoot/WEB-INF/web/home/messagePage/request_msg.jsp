<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.msglist != null && #request.msglist.size > 0">
	<s:iterator id = "req" value = "#request.msglist" status = "req_index">
		<div class = 'message_block' style = 'float:left;width:100%;font-size:15px;padding:10px 0px 5px 0px;border-bottom:1px dotted #d8d8d8;border-radius:0px;position:relative;' id = "req_${req.id}">
			<div style = "position:absolute;top:0px;left:0px;width:100%;height:100%;line-height:100px;display:none;background:url(${pageContext.request.contextPath}/web/image/base/white_placeholder.png);" id = 'req_del_loading_${req.id}'><span>删除ing</span><img src = '${pageContext.request.contextPath}/web/image/register/loading.gif'/></div>
			<div style = "float:left;margin-left:20px;width:50px;">
				<a href = '${pageContext.request.contextPath}/user/${req.senderid}'><img src = '${req.senderAvatar}' style = 'float:left;width:50px;height:50px;'/></a>
			</div>
			<div style = 'float:left;margin-left:20px;width:520px;'>
				<span style = 'float:left;width:100%;margin-top:2px;text-align:left;'><a href = '${pageContext.request.contextPath}/user/${req.senderid}' style='color:#e68303;'>${req.senderName}</a>${req.content}</span>
				<s:if test = '#req.reqType!="addFriends"'>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>物品详情 : <a href ='${pageContext.request.contextPath}/details/${req.reqType}/${req.reqShareId}' target = '_blank' style = 'color:#e68303;'>${req.reqShare}</a></span>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
						<s:if test = '#req.reqShareImg1 != ""'>
							<a href ='${pageContext.request.contextPath}/details/${req.reqType}/${req.reqShareId}' target = '_blank' ><img src = "${reqShareImg1}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
						<s:if test = '#req.reqShareImg2 != ""'>
							<a href ='${pageContext.request.contextPath}/details/${req.reqType}/${req.reqShareId}' target = '_blank' ><img src = "${reqShareImg2}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
						<s:if test = '#req.reqShareImg3 != ""'>
							<a href ='${pageContext.request.contextPath}/details/${req.reqType}/${req.reqShareId}' target = '_blank' ><img src = "${reqShareImg3}" style = "float:left;margin-left:20px;max-width:120px;max-height:120px;"/></a>
						</s:if>
					</span>
				</s:if>
				<s:if test = '#req.reqMessage!=""'>
					<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>请求信息 : ${req.reqMessage}</span>
				</s:if>
				<span style = 'float:left;width:100%;margin-top:8px;text-align:left;font-size:14px;'>
				<s:if test = '#req.isAccept=="1"'>
					<span style="float:left;color:#c8c8c8;font-size:14px;margin-right:20px;">(已处理)</span>
					<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'orderBox.load({idleid:${req.reqShareId}});'>分享单</a>
				</s:if>
				<s:else>
					<div id = "req_opt" style = "float:left;">
						<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'request.accept({reqid:${req.id},userid:${req.senderid},reqType:"${req.reqType}"});'>同意</a>
					</div>
				</s:else>
				<a href = 'javascript:void(0);' style = 'float:left;color:#e68303;margin-right:20px;' onclick = 'replyBox.show({userid:${req.senderid},userName:"${req.senderName}"});'>私信</a>
				<span style = 'float:left;color:#c1c1c1;'>${req.sendTime}</span></span>
			</div>
			<div style = 'float:left;width:60px;'>
				<a class = 'message_opt' href = 'javascript:void(0);' onclick = 'confirmBox.show({func:function(){request.ignore({reqid:${req.id}});},msg:"确认删除此条请求?"});'><img src = "${pageContext.request.contextPath}/web/image/base/del_button.png" style = "float:left;width:18px;margin-left:15px;margin-top:3px;" title="删除请求"/></a>
			</div>
		</div>
	</s:iterator>
	<s:if test = "#request.reqCount > #request.msgSize">
		<div style = "float:left;margin-top:15px;width:100%;">
			<ul style = "float:right;margin-right:20px;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first"  value= "1"  />    
                     <s:param name="last"  value= "#request.reqCount/#request.msgSize+((#request.reqCount%#request.msgSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    		<a href="${pageContext.request.contextPath}/message?page=req&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    		<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    	 </s:if>
                     <s:iterator status="stat">
                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     		<a href="${pageContext.request.contextPath}/message?page=req&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
                     	</s:if>
                     </s:iterator>
                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    	<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
                   		</s:if>
                   		<a href="${pageContext.request.contextPath}/message?page=req&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
                   	</s:if>    
                 </s:bean>
			</ul>
		</div>
	</s:if>
</s:if>
<s:else>
	<div id = "msg_default_top">
		<img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;height:16px;"/>
		<span class = "msg_title">暂时没有请求信息</span>
	</div>
</s:else>