/**  
 * @Title KryoUtil.java
 * @Description TODO 
 * @author bluecrush   
 * @date 2016-8-18上午11:23:42  
 * @version v1.1
 */
package com.tauriel.demo.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** 
 * @ClassName: KryoUtil 
 * @Description: 序列化工具
 * @author bluecrush
 * @date 2016-8-18 上午11:23:42 
 * @version v1.1 
 */
public class KryoUtil {
	/**
	 * 
	 * @Title:serialize
	 * @Description:TODO 序列化
	 * @param object
	 * @throws IOException 
	 * @return:byte[]
	 * @author:blueCrush 
	 * @date:2016-8-18上午11:26:03
	 */
	public static byte[] serialize(Object object) throws IOException{
		byte[] byteArray=null;
		if(!String.class.isAssignableFrom(object.getClass())){	//String 不进行序列化
			Kryo kryo = new Kryo();
			kryo.setReferences(false);
			kryo.setRegistrationRequired(false);
			kryo.register(object.getClass());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Output output=new Output(baos);
			kryo.writeObject(output, object);
			output.flush();
			output.close();
			byteArray = baos.toByteArray();
			baos.close();
		}else{
			byteArray=object.toString().getBytes("UTF-8");
		}
		return byteArray;
	}

	/**
	 *
	 * @Title:deserialize
	 * @Description:TODO 反序列化
	 * @param bs
	 * @param clazz
	 * @throws IOException
	 * @return:T
	 * @author:blueCrush
	 * @date:2016-8-18上午11:31:55
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] bs,Class<T> clazz) throws IOException{
		T result=null;
		if(!String.class.isAssignableFrom(clazz)){
			Kryo kryo = new Kryo();
			kryo.setReferences(false);
			kryo.setRegistrationRequired(false);
			kryo.register(clazz);
			Input input = new Input(bs);
			result = kryo.readObject(input, clazz);
			input.close();
		}else{
			result=(T) new String(bs,"UTF-8");
		}
		return result;
	}
}
