<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class = "bar">
	<a class = "bar_nav_btn bar_selected" style = "margin-left:30px;" href = "${pageContext.request.contextPath}/activity/lost_found/show">失物展示</a>
</div>
<div style = "float:left;width:100%;">
	<div id = "lostfound_nav_select">
  		<div class = "lf_nav_select_block">
  			<span>校区:</span>
  			<s:select id = "selected_campus" cssClass = "lf_nav_selected" list="#{'':'全部','沙河':'沙河','本部':'本部'}"></s:select>
  		</div>
		<div class = "lf_nav_select_block">
  			<span>获取地点:</span>
  			<s:select id = "selected_place" cssClass = "lf_nav_selected" list="#{'':'全部','超市':'超市','打印店':'打印店','理发店':'理发店','食堂':'食堂','公寓楼':'公寓楼'}"></s:select>
  		</div>
  </div>
	<div id = "lostfound_show">
	  	<input id = "pageno" type = "hidden" value = "0"/>
	  	<div id = "f_loading" style = 'float:left;width:100%;margin-top:40px;'><span>失物信息获取中<img src = "${pageContext.request.contextPath}/web/image/register/loading.gif" style = "margin-left:10px;"/></span></div>
  		<div id = 'lostfound_notes'>
  		</div>
  	</div>
	<script type="text/javascript">
		lost.get({pn:0});
	</script>
</div>