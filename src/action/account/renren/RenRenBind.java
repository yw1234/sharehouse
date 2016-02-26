package action.account.renren;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import services.account.AccountService;
import util.AppConfig;
import util.GetAC;
import util.StaticInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.utils.HttpURLUtils;

import dao.user.UserRegisterDao;

public class RenRenBind extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();

	public String bind() throws NoSuchAlgorithmException{
		String message = "";
		String code = request.getParameter("code");
		if (code == null || code.length() == 0) {
			//缺乏有效参数，跳转到登录页去
			message = "缺乏有效code参数";
			return ERROR;
		}
		//到人人网的OAuth 2.0的token endpoint用code换取access token
		String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/token";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id", AppConfig.RenRenApiKey);
		parameters.put("client_secret", AppConfig.RenRenApiSecret);
		parameters.put("redirect_uri", "http://"+request.getServerName()+"/renren/bind");//这个redirect_uri要和之前传给authorization endpoint的值一样
		parameters.put("grant_type", "authorization_code");
		parameters.put("code", code);
		String tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint, parameters);
		JSONObject tokenJson = (JSONObject) JSONValue.parse(tokenResult);
		if (tokenJson != null) {
			String accessToken = (String) tokenJson.get("access_token");
			Long expiresIn = (Long) tokenJson.get("expires_in");//距离过期时的时间段（秒数）
			long currentTime = System.currentTimeMillis() / 1000;
			long expiresTime = currentTime + expiresIn;//即将过期的时间点（秒数）
			request.getSession().setAttribute("expiresTime", expiresTime);
			//调用人人网API获得用户信息
			RenrenApiClient apiClient = RenrenApiClient.getInstance();
			try{
				Long userid = (Long) request.getSession().getAttribute("userid");
				int rrUid = apiClient.getUserService().getLoggedInUser(new AccessToken(accessToken));
				AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
				as.setRenRenIdMapping(rrUid, userid);
			}
			catch(Exception e){
				String msg = "人人绑定失败,错误原因:<br>";
				message = msg+e.getMessage();
				return ERROR;
			}
		}else {message = "返回参数错误";
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public void unbind() throws IOException{
		String message = "",flag = "0";
		JSONObject root = new JSONObject();
		try{
			Long userid = (Long) request.getSession().getAttribute("userid");
			AccountService as = (AccountService) GetAC.getAppContext().getBean("AccountService");
			if(as.unbindRenRen(userid)){
				flag = "1";
			}
		}
		catch(Exception e){
			String msg = "解除人人绑定失败,错误原因:<br>";
			message = msg+e.getMessage();
		}
		root.put("flag",flag);
		root.put("message",message);
		root.writeJSONString(response.getWriter());
	}
}
