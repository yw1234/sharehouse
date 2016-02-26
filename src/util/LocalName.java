package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalName {
	
	public final static String getLocalName()
	{
		Date date = new Date();
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
}
