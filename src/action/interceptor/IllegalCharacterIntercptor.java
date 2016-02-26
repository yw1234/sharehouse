package action.interceptor;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sun.net.httpserver.Filter.Chain;

public class IllegalCharacterIntercptor implements Interceptor{
	private final static long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation invocation) throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		Enumeration params = request.getParameterNames();
		boolean flag = true;
		String paramsValue = "";
		//非法字符
		String []illCharacter = {"'","\\"};
		//过滤非法字符
		while (params.hasMoreElements()) {
			paramsValue += request.getParameter((String) params.nextElement());
		}
		for(int i = 0 ; i < illCharacter.length; i++){
	    		if(paramsValue.indexOf(illCharacter[i]) >= 0){
	    			flag = false;
	    			break;
	    		}
	    }
		if(flag){
			return invocation.invoke();
		}else{
			JSONObject root = new JSONObject();
			root.put("flag","0");
			root.put("message","目测有不和谐的字符哦 :), 如'");
			root.write(response.getWriter());
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
