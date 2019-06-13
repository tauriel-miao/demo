package com.tauriel.demo.util;

import java.io.*;
import java.util.Properties;

/**
 *@description propertis util
 *@author 张鑫
 * *@param null
 *@return 
 *@throws
 *@create 2018/3/29 10:36
 */
public class PropertiesUtil {

	private static Properties properties;
	
	static{
		properties = new Properties();
		try {
	 		File file = new File("./config/brConfig.properties");
			InputStream in=null;
			if(file.exists()){
				in = new FileInputStream(file);
			}else{
				in = PropertiesUtil.class.getClassLoader().getResourceAsStream("brConfig.properties");
			}
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties() {
		return properties;
	}
	
	public static Integer getIntegerValue(String key){
		return Integer.parseInt(properties.getProperty(key));
	}
	
	public static String getStringValue(String key){
		return (String)properties.getProperty(key);
	}
	
	public static boolean getBooleanValue(String key){
		return Boolean.parseBoolean(properties.getProperty(key));
	}
	
	public static Object getValue(String key){
		return properties.getProperty(key);
	}
	public static void main(String []rags)
	{
		System.out.print(PropertiesUtil.getStringValue("mkey"));
	}
}
