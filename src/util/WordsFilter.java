package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import services.user.UserService;

public class WordsFilter {
	
	public static Map atFilter(String text,Long loggedUid){
		if(text == null || text.equals(""))
			return null;
		Map map = new HashMap();
		List list = new ArrayList();
		Pattern atPattern = Pattern.compile("[@][\\u4e00-\\u9fa5\\S][^@]+[)]"),
				numPattern = Pattern.compile("[0-9]*");
		Matcher m = atPattern.matcher(text);
		while(m.find())
		{
			String at = m.group();
			int leftBrackets = at.indexOf("("),rightBrackets = at.indexOf(")");
			if(leftBrackets >=0 && rightBrackets >= 0){
				String id = at.substring(leftBrackets+1,rightBrackets),
						name = at.substring(1,leftBrackets);
				UserService us = (UserService) GetAC.getAppContext().getBean("UserService");
				if(numPattern.matcher(id).matches() && us.isAtUserExists(Long.parseLong(id), name,loggedUid)){
					text = text.replace(at, "<a href='"+StaticInfo.URL+"/user/"+id+"' target='_blank' uCard="+id+">@"+name+"</a>");
					Object [] uinfo = {id,name};
					list.add(uinfo);
				}
			}
		}
		map.put("text", text);
		map.put("uList", list);
		return map;
	}
	
	public static String imgFilter(String text)
	{
		String newtext = null;
		if(text == null || text.equals(""))
			return "";
		text = text.trim();
		while(text.indexOf("[yc:") != -1)
		{
			int loc = text.indexOf("[yc:") + 4;
			String numstr = text.substring(loc, loc+2);
			Integer num = null;
			if(numstr.charAt(1) >='0' &&  numstr.charAt(1) <= '9')
			{
				num = Integer.parseInt(numstr);
			}
			else {num = numstr.charAt(0)-'0';}
			text = text.replace("[yc:"+num+":]", "<img class='face_gif' src='"+StaticInfo.URL+"/web/image/base/face/yct/yct-"+num.toString()+".gif'/>");
		}
		while(text.indexOf("[al:") != -1)
		{
			int loc = text.indexOf("[al:") + 4;
			String numstr = text.substring(loc, loc+2);
			Integer num = null;
			if(numstr.charAt(1) >='0' &&  numstr.charAt(1) <= '9')
			{
				num = Integer.parseInt(numstr);
			}
			else {num = numstr.charAt(0)-'0';}
			text = text.replace("[al:"+num+":]", "<img class='face_gif' src='"+StaticInfo.URL+"/web/image/base/face/ali/al-"+num.toString()+".gif'/>");
		}
		while(text.indexOf("[bo:") != -1)
		{
			int loc = text.indexOf("[bo:") + 4;
			String numstr = text.substring(loc, loc+2);
			Integer num = null;
			if(numstr.charAt(1) >='0' &&  numstr.charAt(1) <= '9')
			{
				num = Integer.parseInt(numstr);
			}
			else {num = numstr.charAt(0)-'0';}
			text = text.replace("[bo:"+num+":]", "<img class='face_gif' src='"+StaticInfo.URL+"/web/image/base/face/bobo/bobo-"+num.toString()+".gif'/>");
		}
		newtext = text;
		return newtext.length()>4000?newtext.substring(0,4000):newtext;
	}
	
	public static String badWordsFilter(String text){
		String newtext = null;
		if(text == null || text.equals(""))
			return "";
		text = text.trim();
		String []bad_name = {""};
		for(int i = 0 ; i < bad_name.length ; i++)
		{
			text.replaceAll(bad_name[i],"***");
		}
		newtext = text;
		return newtext.length()>4000?newtext.substring(0,4000):newtext;
	}
	
	public static void main (String []args){
		String text = "fce@@啊fqe@撒大范围为(123122) @杨闻dM^_^(234)被分手费凯文";
		//atFilter(text,52L);
		//atFilter(text);
	}
}
