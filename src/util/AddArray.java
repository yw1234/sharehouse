package util;

public class AddArray {
	
	public static Object[] addObject(Object []a,Object b){
		Object[] temp = new Object[a.length + 1];
		for(int i = 0;i<a.length;i++){
			temp[i] = a[i];
		}
		temp[temp.length-1]=b;
		return temp;
	}
	public static Integer[] addNumber(Integer []a,Integer b){
		Integer[] temp = new Integer[a.length + 1];
		for(int i = 0;i<a.length;i++){
			temp[i] = a[i];
		}
		temp[temp.length-1]=b;
		return temp;
	}
	public static Long[] addLongNumber(Long []a,Long b){
		Long[] temp = new Long[a.length + 1];
		for(int i = 0;i<a.length;i++){
			temp[i] = a[i];
		}
		temp[temp.length-1]=b;
		return temp;
	}
	public static String[] addString(String []a,String b){
		String[] temp = new String[a.length + 1];
		for(int i = 0;i<a.length;i++){
			temp[i] = a[i];
		}
		temp[temp.length-1]=b;
		return temp;
	}
	
}
