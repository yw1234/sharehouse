<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test = "%{#parameters.page==null || #parameters.page[0]=='main'}">
	<div id = "notes"> 
		<s:if test = "%{#parameters.type==null ||#parameters.type[0]=='share'}">
   			<input type = "hidden" id = "pageno" value = "0"/>
   			<input type = "hidden" id = "type" value = "goods"/>
   			<input type = "hidden" id = "method" value = "userFeeds"/>
    		<div class = "feeds">
    			<div id = "f_loading" style = 'margin-top:30px;'><span>闲置获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
    		</div>
    		<div class = "notes_delete notesdel-switch" style = "position:absolute;top:-999px;left:-999px;width:125px;border:2px solid #dedede;background:#ffffff;z-index:90">
			  	<input type = "hidden" id = "notes_delete_senderid"/>
			  	<input type = "hidden" id = "notes_delete_typeid"/>
			  	<input type = "hidden" id = "notes_delete_type"/>
			  	<input type = "hidden" id = "notes_isShared"/>
			  	<div class = "delete_op notesdel-switch">
				  	<div id = "notes_del_isShared" class = "notes_delete_list" style = "border-bottom:1px solid #dedede;" onclick = "signNotes();"></div>
				  	 <div class = "notes_delete_list" onclick = "deleteNotes({delNumber:'all'});">删除此条发布</div>
			  	</div>
			  	<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" class = "delete_loading" style = "display:none;"/>
			</div>
   		</s:if>
	</div>
	 <script type="text/javascript">
	    	//获取分享信息
	    	feeds.get({type:"goods",method:"userFeeds"});
	 </script>
</s:if>
<s:elseif test='%{#request.info.id!=#request.user.id && #parameters.page[0]=="friends"&&#request.canSeeFri=="1"}'>
	<div style = "float:left;margin-left:140px;margin-top:10px;">
		<s:include value="showFriends.jsp"/>
	</div>
</s:elseif>
<s:elseif test='%{#request.info.id!=#request.user.id && #parameters.page[0]=="priMsg"&& #request.canSeeMsg=="1"}'>
	<div style = "float:left;margin-left:140px;margin-top:10px;">
		<s:include value="showMessage.jsp"/>
	</div>
</s:elseif>
<s:elseif test="%{#parameters.page[0]=='info'}">
	<div style = "float:left;margin-left:140px;margin-top:10px;">
		<s:include value="info.jsp"/>
	</div>
</s:elseif>
<s:elseif test="%{#request.info.id == #request.user.id&&#parameters.page[0]=='friendsManage'}">
	<div style = "float:left;margin-left:60px;margin-top:10px;">
		<s:include value="friendsManage.jsp"/>
	</div>
</s:elseif>
<s:elseif test="%{#request.info.id == #request.user.id&&#parameters.page[0]=='privacy'}">
	<div style = "float:left;margin-left:60px;margin-top:10px;">
		<s:include value="privacy.jsp"/>
	</div>
</s:elseif>
<s:else>
	<s:include value="../../base/errorPage.jsp"/>
</s:else>