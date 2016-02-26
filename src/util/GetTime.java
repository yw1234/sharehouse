package util;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import dao.user.UserGetDao;

public class GetTime {
	
	@SuppressWarnings("deprecation")
	public static String getSendTime(Date d){
		if(d == null){
			 return "Recently published"; 
		}
	    Date now = new Date(); 
	    String result = ""; 
	    Long diffValue = now.getTime() - d.getTime();  
	    if(diffValue < 0){       
	        return "Recently published";      
	    } 
	    Long second = 1000L; 
	    Long minute = 1000L * 60;    
	    Long hour = minute * 60;    
	    Long hourC =diffValue/hour;    
	    Long minC =diffValue/minute;    
	    Long secondC = diffValue/second;
	    if(hourC>=3){
	    	result= d.toString().substring(2,4)+"-"+d.toString().substring(5,7)+"-"+d.toString().substring(8,10)+" "+d.toString().substring(11,13)+":"+d.toString().substring(14,16);
	    }        
	    else if(hourC>=1 && hourC<3){      
	        result= hourC +"hours ago";     
	    }else if(minC>=1){      
	        result=minC +"minutes ago";      
	    }else if(secondC>=1){  
	        result= secondC +"seconds ago";    
	    }   
	    else{
	    	result="Recently published";
	    }
	    return result;    
	}
	
	
	//判断是否超过时限,是为true
	public static boolean getTimeDifference(Date tNow,Date tLastOp,String type,Integer target_diff){
	    if(tLastOp==null) return true;
		boolean flag = false;
	    Long diffValue = tNow.getTime() - tLastOp.getTime();  
	    if(diffValue < 0){       
	        return flag;      
	    } 
	    Long second = 1000L; 
	    Long minute = 1000L * 60;    
	    Long hour = minute * 60;    
	    //计算差值
	    Long hourC =diffValue/hour;    
	    Long minC =diffValue/minute;    
	    Long secondC = diffValue/second;
	    if(type.equals("bySecond")){
	    	if(secondC>=target_diff)
				flag=true;
	    	else flag = false;
	    }
	    else if(type.equals("byMinute")){
	    	if(minC>=target_diff)
				flag=true;
	    	else flag = false;
	    }
	    else if(type.equals("byHour")){
			if(hourC>=target_diff)
				flag=true;
			else flag = false;
		}else if(type.equals("byDay")){
			if(hourC>=target_diff*24)
				flag=true;
			else flag = false;
		}
		return flag;
	}
}
