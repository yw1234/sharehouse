package activity.sellbook;

import java.util.Map;

import util.StaticInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AdminSessionInterceptor implements Interceptor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception
	{
		ActionContext context = invocation.getInvocationContext();
		Map<String,Object> session = context.getSession();
		Object userid = session.get("userid");
		if(userid!=null && userid.toString().equals(StaticInfo.adminId.toString()))
		{
			return invocation.invoke();
		}
		else{
			return "tologin";
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}
}
