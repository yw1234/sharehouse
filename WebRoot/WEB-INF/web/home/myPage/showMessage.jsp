<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.priMsg != null && #request.priMsg.size > 0">
	<s:iterator id = "ml" value = "#request.priMsg" status = "mi">
		<div style = "float:left;margin-top:15px;width:670px;border-bottom:2px solid #efefee;">
			<img src = "${ml[2]}" style = "float:left;width:45px;height:45px;border-radius:3px;"/>
			<div style = "float:left;margin-left:5px;font-size:14px;">
				<span class="pri_content">
					<a href="${pageContext.request.contextPath}/user/${ml[6]}"><span style = "font-family:'黑体',Arial;color:#e68303;">${ml[0]}:</span></a>
					${ml[7]}
				</span>
			</div>
			<div style = "float:left;width:600px;margin-top:5px;">
				<s:if test='#ml[6]!=#request.user.id'>
					<a href="javascript:void(0);" class = "pribtn" id= "pri_reply_${ml[3]}" onclick = "leaveMsg.init({btn:$(this).children('span'),userid:${ml[6]},username:'${ml[0]}'});"><span style="float:left;margin-top:5px;color:#e68303;font-size:13px;margin-left:5px;">留言</span></a>
					<span style = "float:left;width:10px;color:#afafaf;line-height:25px;">|</span>
					<a href="javascript:void(0);"  id= "pri_seeAll_${ml[3]}" onclick = "complaintBox.show({id:${ml[6]},name:'${ml[0]}'})"><span style="float:left;margin-top:5px;color:#e68303;font-size:13px;" >举报</span></a>
				</s:if>
				<span style = "margin-left:20px;float:left;text-align:left;margin-top:5px;font-size:13px;color:#afafaf"><s:property value = "#ml[8].toString().substring(0,19)"/></span>
			</div>
			<div style = "float:left;width:100%;height:10px;"></div>
		</div>
	</s:iterator>
	<s:if test = "#request.msgNum>10">
		<div style = "float:left;width:100%;height:30px;margin-top:10px;">
			<div style = "float:right;margin-right:40px;margin-top:15px;">
				<ul style = "float:left;">
					<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
	                     <s:param name="first"  value= "1"  />    
	                     <s:param name="last"  value= "#request.msgNum/10+((#request.msgNum%10)!=0?1:0)"/>    
	                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
	                    	<a href="${pageContext.request.contextPath}/user/${info.id}?page=priMsg&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
	                    	<s:if test = '%{(#parameters.p[0]>1+3)}'>
	                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
	                    	</s:if>
	                    	 </s:if>
	                     	<s:iterator status="stat">
	                     		<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
	                     			<a href="${pageContext.request.contextPath}/user/${info.id}?page=priMsg&p=<s:property/>"><li class = "p-btn"style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
	                     		</s:if>
	                    	 </s:iterator>
	                     	<s:if test = '%{(#parameters.p[0]<=last-3)}'>
	                    		<s:if test = '%{(#parameters.p[0]< last-3)}'>
	                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
	                    		</s:if>
	                    		<a href="${pageContext.request.contextPath}/user/${info.id}?page=priMsg&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
	                    	</s:if>    
	                 </s:bean>
				</ul>
			</div>
		</div>
	</s:if>
	<div style = "float:left;width:100%;height:30px;"></div>
	<div class = "leave_msg_block">
		<input id = "leave_userid_input" type = "hidden"/>
		<div style = "float:left;width:300px;height:20px;line-height:20px;margin-top:5px;">
	 		<span style = "float:left;text-align:left;font-family:'黑体',Arial;font-size:15px;margin-left:10px;">给<span id = "leave_username" style = "font-family:'黑体',Arial;font-size:15px;color:#e68303;"></span>发条私信~</span>
	  		<img id = "leavemsg_loading" src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "display:none;"/>
	  	</div>
		<textarea class = "leavemsg_text" TextDefault="填写留言"></textarea>
		<div class = "add_btn"  onclick = "leaveMsg.confirm();" style = "margin-top:3px;margin-left:10px;">发送</div>
	 	<div class = "add_btn"  onclick = "leaveMsg.cancel();" style = "margin-left:10px;">取消</div>
	</div>
</s:if>
<s:else>
	<div style = "float:left;margin-top:15px;font-family:'黑体',Arial;width:700px;border-bottom:2px solid #efefee;">
		还没有人给${info.realname}留言
	</div>
</s:else>