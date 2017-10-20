package com.innovative.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 序列化工具，实现对象的序列化与反序列化功能
 * @author fzy
 * @date 2017/10/20
 */
public class SerializeUtil {
	private static final SerializeUtil SERIALIZE_UTIL=new SerializeUtil();
	private SerializeUtil(){
		
	}
	/**
	 * 单例，返回工具类的对象
	 * @return
	 */
	public static SerializeUtil getInstance(){
		return SERIALIZE_UTIL;
	}
	
	/**
	 * 对象序列化为字符串
	 * @param object
	 * @return 序列化后的字符串
	 */
	public String objectSerialiable(Object object){
		String serStr = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
            //序列化操作
            objectOutputStream.writeObject(object);    
            serStr = byteArrayOutputStream.toString("ISO-8859-1");  
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
            //释放资源
            objectOutputStream.close();  
            byteArrayOutputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return serStr;
	}
	
	/**
	 * 将字符串反序列化成一个对象
	 * @param serStr
	 * @return
	 */
	public Object objectDeserialization(String serStr){
        Object newObj = null;
        if (serStr!=null) {
        	try {
                String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");  
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));  
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);   
                //反序列化操作
                newObj = objectInputStream.readObject();
                //释放资源
                objectInputStream.close();  
                byteArrayInputStream.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}else{
			return null;
		}
        
        return newObj;
    }
}
