<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%
/*判断自动登录*/
	String URL = ServletActionContext.getRequest().getContextPath();
	String user_agent = request.getHeader("user-agent");
	//||user_agent.indexOf("iPhone")!=-1||user_agent.indexOf("Android")!=-1||user_agent.indexOf("WindowsPhone")!=-1
	if(user_agent.indexOf("UCWEB")!=-1)
		response.sendRedirect(URL+"/m/touch/index");
	else if(user_agent.indexOf("Mobile")!=-1){
		response.sendRedirect(URL+"/m/3g/index");
	}else response.sendRedirect(URL+"/index");
%>