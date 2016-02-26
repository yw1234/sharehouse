package action.user;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import util.GetAC;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserDeleteDao;
import dao.user.UserRemoveDao;

public class UserExit extends ActionSupport{
		
	public String login(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			session.invalidate();
			Cookie cookies[] = request.getCookies();
			for(Cookie c : cookies)
			{
				boolean flag = (c.getName().equals("q_s") || c.getName().equals("q_uid"));
				if(flag)
				{
					c.setMaxAge(0);
					response.addCookie(c);					
				}
			}
			response.sendRedirect(request.getContextPath()+"/index");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
