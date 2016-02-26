package action.interceptor;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import util.StaticInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserLoginInterceptor implements Interceptor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception
	{
		ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> session = context.getSession();
		if(session.get("userid") != null)
		{
			return invocation.invoke();
		}
		else{
			session.remove("auto_login");
			String url = StaticInfo.URL+"/index?url=";
			String req_params = request.getRequestURI()+"?";
			Enumeration params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String pname = (String) params.nextElement();
				req_params += (pname+"="+request.getParameter(pname)+"&");
			}
			String r = java.net.URLEncoder.encode(req_params.substring(0, req_params.length()-1),"UTF-8");
			ServletActionContext.getResponse().sendRedirect(url + java.net.URLEncoder.encode(r,"UTF-8"));
			return null;
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}
}
