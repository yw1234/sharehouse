package com.renren.api.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {
    public ConfigUtil(){}
    public static Map<String, String> props = new HashMap<String, String>();
    static{
        try {
            props.put("renrenApiUrl","http://api.renren.com/restserver.do");
            props.put("renrenApiVersion", "1.0");
            props.put("renrenApiKey", "9f5f12b73d784bafa0eafb092d0ab02a");
            props.put("renrenApiSecret", "91b318a2dd9e43babfca7c83f8a26964");
            props.put("renrenAppID", "229690");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return props.get(key).toString();
    }
}
