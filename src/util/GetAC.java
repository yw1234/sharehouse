/**
 * 实例化ApplicationContext消耗大量内存,应设置为静态
 * @author 杨闻
 * 2012/2/18
 */
package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetAC {
	private static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

	public static ApplicationContext getAppContext() {
	   return appContext;
	}

	public void setApplicationContext(ApplicationContext context) {
	   GetAC.appContext=context;
	}
}
