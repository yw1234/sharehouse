<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style = "float:left;margin-left:20px;margin-top:10px;">
	<img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">${info.realname}共有<span style="color:#e68303;">${friendsNum}</span>个好友</span>
</div>
<div style = "float:left;width:880px;margin-left:15px;">
	<s:if test="#request.friendsList != null && #request.friendsList.size > 0">
		<s:iterator id = "mk" value = "#request.friendsList" status = "mi">
			<div class = "show_user_notes" style = "background:#f1f1f1;width:200px;height:90px;margin-left:5px;">
		    		<div style = "float:left;width:70px;margin-top:5px;">
		    			<a href = "${pageContext.request.contextPath}/user/${mk[0]}">
		    				<img src = "${mk[3]}" style = "float:left;margin-left:5px;width:60px;height:60px;border-radius:3px;"/>
		    			</a>
		    			<div style = "float:left;width:100%;">
		    				<s:if test='#mk[0] != #request.user.id'><span style = "color:#e68303;font-size:13px;cursor:pointer;" id = "leave_msg_btn" onclick = "replyBox.show({userid:'${mk[0]}',userName:'${mk[1]}'});">私信</span></s:if>
		    				<s:else><a href="${pageContext.request.contextPath}/user/${user.id}"><span style = "color:#e68303;font-size:13px;cursor:pointer;">我的主页</span></a></s:else>
		    			</div>
		    		</div>
		    		<div style = "float:left;margin-left:5px;text-align:left;width:120px;margin-top:2px;">
		    			<span class = "show_user_span"><s:if test = '#mk[4]=="男"'><img src = "${pageContext.request.contextPath}/web/image/base/icon/icon_male.png" style = "float:left;height:14px;"/></s:if><s:else><img src = "${pageContext.request.contextPath}/web/image/base/icon/icon_female.png" style = "float:left;height:14px;line-height:15px;"/></s:else><a href = "${pageContext.request.contextPath}/user?page=main&type=share&id=${mk[0]}"><span style = "float:left;margin-left:3px;color:#e68303;line-height:16px;">${mk[1]}</span></a></span>
		    			<span class = "show_user_span" style = "font-size:13px;margin-top:5px;">${mk[5]}</span>
		    			<span class = "show_user_span" style = "font-size:13px;margin-top:5px;">${mk[9]}</span>
		    		</div>
		    	</div>
		</s:iterator>
	</s:if>
</div>
<s:if test = "#request.friendsNum > #request.userSize">
	<div style = "float:left;width:100%;height:30px;margin-top:10px;">
		<div style = "float:right;margin-right:40px;margin-top:15px;">
			<ul style = "float:left;">
				<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
                     <s:param name="first"  value= "1"  />    
                     <s:param name="last"  value= "#request.friendsNum/#request.userSize+((#request.friendsNum%#request.userSize)!=0?1:0)"/>    
                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
                    	<a href="${pageContext.request.contextPath}/user?page=friends&id=${info.id}&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
                    	<s:if test = '%{(#parameters.p[0]>1+3)}'>
                    		<span style="float:left;margin-left:5px;">▪▪▪</span>
                    	</s:if>
                    	 </s:if>
                     	<s:iterator status="stat">
                     		<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
                     			<a href="${pageContext.request.contextPath}/user?page=friends&id=${info.id}&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>background:#e68303;color:#ffffff;</s:if>"><s:property/></li></a>
                     		</s:if>
                    	 </s:iterator>
                     	<s:if test = '%{(#parameters.p[0]<=last-3)}'>
                    		<s:if test = '%{(#parameters.p[0]< last-3)}'>
                    			<span style="float:left;margin-left:5px;">▪▪▪</span>
                    		</s:if>
                    		<a href="${pageContext.request.contextPath}/user?page=friends&id=${info.id}&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
                    	</s:if>    
                 </s:bean>
			</ul>
		</div>
	</div>
</s:if>
<div style = "float:left;width:100%;height:30px;"></div>
<div class = "add_rex_block">
   <input id = "add_userid_input" type = "hidden"/>
  	<div style = "float:left;width:300px;height:20px;line-height:20px;margin-top:5px;">
  		<span style = "float:left;text-align:left;font-size:15px;font-family:'黑体',Arial;margin-left:10px;">将<span id = "add_username" style = "font-size:15px;font-family:'黑体',Arial;color:#e68303;"></span>加为好友</span>
  	</div>
  	<textarea class = "rex_text" TextDefault="验证信息或留言"></textarea>
 	<div class = "button" id = "add_friends_confirm" onclick = "addFriends.confirm();" style = "float:left;width:50px;height:20px;margin-top:3px;margin-left:10px;">发送</div>
 	<div class = "button" id = "add_friends_cancel" onclick = "addFriends.cancel();" style = "float:left;width:50px;height:20px;margin-top:3px;margin-left:10px;">取消</div>
</div>