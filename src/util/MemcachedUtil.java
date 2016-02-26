package util;

import com.danga.MemCached.MemCachedClient;

public class MemcachedUtil {

	 public static MemCachedClient getMemCachedClient() {
		  return (MemCachedClient) GetAC.getAppContext().getBean("memcachedClient");
		}
	 
	 public static void main(String args[]){
		 MemCachedClient m= getMemCachedClient();
		  m.set("key", "yunhui");
		  System.out.println(m.get("key"));
	 }
}
