<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class = "left_nav_box">
	<a href = "${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=all&p=1"><div class = "left_nav_box_btn" id = "all_btn" <s:if test = "%{#parameters.nav[0]=='all'}">style='color:white;background:#e68303;'</s:if>>
		全部好友
	</div></a>
	<a href = "${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=bl"><div class = "left_nav_box_btn" id = "bl_btn" <s:if test = "%{#parameters.nav[0]=='bl'}">style='color:white;background:#e68303;'</s:if>>
		黑名单
	</div></a>
	<div style = "float:left;margin-top:10px;width:100px;height:20px;background:white;">
		<img id = "fm_loading" src = "${pageContext.request.contextPath}/web/image/register/loading.gif"/>
		<span id = "fm_success" style = "color:#e68303;display:none;">操作成功</span>
	</div>
</div>  
<div id = "fm_base">
	<s:if test = "%{#parameters.nav[0]=='all'}">
		<input type = "hidden" id = "nav" value = "all"/>
		<s:if test = "#request.concernedfriends.size<=0 && #request.friendslist.size<=0">
			<div style = "float:left;"><span style = "font-size:15px;font-family:'黑体',Arial;margin-left:5px;">还没有添加任何好友哦~</span></div>
		</s:if>
		<!-- 
		<s:if test = "#request.concernedfriends!=null && #request.concernedfriends.size>0">
			<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;margin-top:1px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">特别关注</span></div>
			<div style = "float:left;width:670px;margin-top:5px;">
				<s:iterator id = "cf" value = "#request.concernedfriends">
					<div id = "commonfriends_${cf[0]}" style = "float:left;width:130px;height:60px;margin-left:10px;margin-top:15px;">
						<a href = "${pageContext.request.contextPath}/user?page=main&type=share&id=${cf[0]}"><img src = "${cf[3]}" style = "float:left;width:60px;height:60px;"/></a>
						<div style = "float:left;width:70px;">
							<span style = "float:left;margin-left:5px;text-align:left;font-size:13px;width:70px;font-weight:bold;">${cf[1]}</span>
							<a href= "javascript:void(0);"><span id = "concerned_btn_${cf[0]}" style = "float:left;width:70px;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'cancel_con',id:${cf[0]},name:'${cf[1]}'});">取消关注</span></a>
							<a href= "javascript:void(0);"><span id = "bl_btn_${cf[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'addblacklist',id:${cf[0]},name:'${cf[1]}'});">拉黑</span></a><a><span id = "del_btn_${cf[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'delete',id:${cf[0]},name:'${cf[1]}'});">删除</span></a>
						</div>
					</div>
				</s:iterator>
			</div>
			<div style = "float:left;width:670px;height:25px;"></div>
		</s:if> -->
		<s:if test = "#request.friendslist!=null && #request.friendslist.size>0">
			<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;margin-top:1px;"/><span style = "font-size:16px;font-family:'黑体',Arial;margin-left:5px;">我的好友,共有<span style = "color:#e68303;"><s:property value = "#request.friendsNum"/></span>人</span></div>
			<div style = "float:left;width:670px;margin-top:5px;">
				<s:iterator id = "fl" value = "#request.friendslist">
					<div id = "commonfriends_${fl[0]}" style = "float:left;width:150px;height:60px;margin-left:10px;margin-top:15px;">
						<a href = "${pageContext.request.contextPath}/user/${fl[0]}"><img src = "${fl[3]}" style = "float:left;width:60px;height:60px;"/></a>
						<div style = "float:left;width:70px;">
							<span style = "float:left;margin-left:5px;text-align:left;font-size:14px;width:70px;font-family:'黑体',Arial;">${fl[1]}</span>
							<!--<a href= "javascript:void(0);"><s:if test="#fl[5]=='0'"><span id = "concerned_btn_${fl[0]}" style = "float:left;width:70px;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'concerned',id:${fl[0]},name:'${fl[1]}'});">关注</span></s:if><s:else><span id = "concerned_btn_${fl[0]}" style = "float:left;width:70px;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'cancel_con',id:${fl[0]},name:'${fl[1]}'});">取消关注</span></s:else></a>  -->
							<div style = "float:left;margin-top:5px;"><a href= "javascript:void(0);"><span id = "bl_btn_${fl[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'addblacklist',id:${fl[0]},name:'${fl[1]}'});">拉黑</span></a><a><span id = "del_btn_${fl[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'delete',id:${fl[0]},name:'${fl[1]}'});">删除</span></a></div>
						</div>
					</div>
				</s:iterator>
			</div>
			<s:if test = "#request.friendsNum > #request.userSize">
				<div style = "float:right;margin-right:20px;margin-top:15px;">
					<ul style = "float:left;">
						<s:bean name= "org.apache.struts2.util.Counter" id= "counter">
		                     <s:param name="first"  value= "1"  />    
		                     <s:param name="last"  value= "#request.friendsNum/#request.userSize+((#request.friendsNum%#request.userSize)!=0?1:0)"/>    
		                     <s:if test = '%{(#parameters.p[0]>=1+3)}'>
	                     		<a href="${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=all&p=1"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;">1</li></a>
	                     		<s:if test = '%{(#parameters.p[0]>1+3)}'>
	                     			<span style="float:left;margin-left:5px;">▪▪▪</span>
	                     		</s:if>
	                     	 </s:if>
		                     <s:iterator status="stat">
		                     	<s:if test = '%{(#parameters.p[0]>=#stat.index-1) && (#parameters.p[0]<=#stat.index+3)}'>
		                     		<a href="${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=all&p=<s:property/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;<s:if test = '%{#parameters.p[0]==#stat.index+1}'>color:#ffffff;background:#e68303;</s:if>"><s:property/></li></a>
		                     	</s:if>
		                     </s:iterator>
		                     <s:if test = '%{(#parameters.p[0]<=last-3)}'>
	                     		<s:if test = '%{(#parameters.p[0]< last-3)}'>
	                     			<span style="float:left;margin-left:5px;">▪▪▪</span>
	                     		</s:if>
	                     		<a href="${pageContext.request.contextPath}/user/${info.id}?page=friendsManage&nav=all&p=<s:property value="last"/>"><li class = "p-btn" style = "float:left;margin-left:5px;cursor:pointer;"><s:property value="last"/></li></a>
	                     	</s:if>    
		                 </s:bean>
					</ul>
				</div>
			</s:if>
		</s:if>
	</s:if>
	<s:elseif test="%{#parameters.nav[0]=='bl'}">
		<input type = "hidden" id = "nav" value = "bl"/>
		<s:if test = "#request.blacklist!=null && #request.blacklist.size>0">
			<div style = "float:left;"><img src = "${pageContext.request.contextPath}/web/image/base/arrow_left.png" style = "float:left;width:15px;margin-top:1px;"/><span style = "font-size:14px;font-weight:bold;margin-left:5px;">黑名单共<span style = "color:#e68303;"><s:property value = "#request.blacklist.size"/></span>人</span></div>
			<div style = "float:left;width:670px;margin-top:5px;">
				<s:iterator id = "fl" value = "#request.blacklist">
					<div id = "blackfriends_${fl[0]}" style = "float:left;width:150px;height:60px;margin-left:10px;margin-top:15px;">
						<a href = "${pageContext.request.contextPath}/user/${fl[0]}"><img src = "${fl[3]}" style = "float:left;width:60px;height:60px;"/></a>
						<div style = "float:left;width:70px;">
							<span style = "float:left;margin-left:5px;text-align:left;font-size:13px;width:70px;font-weight:bold;">${fl[1]}</span>
							<a href= "javascript:void(0);"><span id = "bl_btn_${fl[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'recover',id:${fl[0]},name:'${fl[1]}'});">恢复为好友</span></a><a><span id = "del_btn_${fl[0]}" style = "float:left;margin-left:5px;text-align:left;font-size:13px;color:#e68303;" onclick = "showFmBox({btn:$(this)[0],type:'delete',id:${fl[0]},name:'${fl[1]}'});">删除</span></a>
						</div>
					</div>
				</s:iterator>
			</div>
		</s:if>
		<s:else>
			<div style = "float:left;"><span style = "font-size:15px;margin-left:5px;">还没有将任何人拉入黑名单</span></div>
		</s:else>
	</s:elseif>
</div>
<div id = "fm_box" style = "position:absolute;width:300px;border:2px solid #f1f1f1;box-shadow:0px 2px 10px #919191;z-index:150;background:#f8f8f8;display:none;">
	<input type="hidden" id = "fm_box_id"/>
	<div style = "float:left;width:270px;text-align:left;margin-left:15px;margin-top:10px;">
		<span id = "fm_content" style = "font-size:15px;"></span>
	</div>
	<div style = "float:left;width:300px;height:10px;"></div>
	<div class = "button" id = "fm_cancel" style = "float:right;margin-right:10px;width:50px;height:22px;line-height:23px;border:0px;font-size:15px;cursor:pointer;border-radius:3px;">取消</div>
	<div class = "button" id = "fm_confirm" style = "float:right;margin-right:10px;width:50px;height:22px;line-height:23px;border:0px;font-size:15px;cursor:pointer;border-radius:3px;">确定</div>
	<div style = "float:left;width:300px;height:10px;"></div>
</div>