package com.innovative.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传
 */
public class FileUpload {

    /**
     * 判断路径是否存在，否：创建此路径
     *
     * @param dir      文件路径
     * @param realName 文件名
     * @throws IOException
     */
    public static File mkdirsmy(String dir, String realName) throws IOException {
        File file = new File( dir,realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    /**
     * 上传文件到指定路径
     *
     * @param file
     * @param tableName
     * @throws IOException
     */
    public static Map<String,String> copyInputStreamToFileForName(MultipartFile file, String tableName) throws IOException {

        //创建文件路径和文件夹
        String dir = Config.FILE_URL + tableName + "/" + DateUtil.getDay() + "/";
        File f = mkdirsmy(dir, file.getOriginalFilename());
        
        
        
        //上传到指定位置
        FileOutputStream write = new FileOutputStream(f);
        byte[] decoderBytes = file.getBytes();
        write.write(decoderBytes);
        write.close();
        Map<String,String> map = new HashMap<String,String> ();
        map.put("url", f.getAbsolutePath());
        map.put("name", file.getOriginalFilename());

        return map;
    }
    
    /**
     * 上传文件到指定路径
     *
     * @param file
     * @param tableName
     * @throws IOException
     */
    public static String copyInputStreamToFile(MultipartFile file, String tableName) throws IOException {

        //创建文件路径和文件夹
        String dir = Config.FILE_URL + tableName + "/" + DateUtil.getDay() + "/";
        
        File f = mkdirsmy(dir, file.getOriginalFilename());

        
        //上传到指定位置
        FileOutputStream write = new FileOutputStream(f);
        byte[] decoderBytes = file.getBytes();
        write.write(decoderBytes);
        write.close();

        return f.getAbsolutePath();
    }
    /**
     * 上传文件到指定路径
     *
     * @param file
     * @param tableName
     * @throws IOException
     */
    public static String copyFile(MultipartFile file, String tableName) throws IOException {

        //创建文件路径和文件夹
        String dir = Config.FILE2_URL + tableName + "/" + DateUtil.getDay() + "/";
        
        File f = mkdirsmy(dir, file.getOriginalFilename());

        
        //上传到指定位置
        FileOutputStream write = new FileOutputStream(f);
        byte[] decoderBytes = file.getBytes();
        write.write(decoderBytes);
        write.close();

        return f.getAbsolutePath();
    }
    
    

    public static String getUrls(MultipartFile[] files, String tableName) {

        StringBuilder fileUrls = new StringBuilder();

        if (files != null && files.length > 0) {
            try {
                String url = null;
                for (int i = 0; i < files.length; i++) {

                    url = FileUpload.copyInputStreamToFile(files[i], tableName);
                    

                    fileUrls.append(url).append(",");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return  "error";
            }
            
        }
        return fileUrls.toString();
    }
    //上传单个文件
	public static Map<String, String> copyInputStreamToFileForName(MultipartFile file, String tableName,
			HttpServletRequest req) throws IOException {
		  //创建文件路径和文件夹
       /* String dir = Config.FILE_URL + tableName + "/" + DateUtil.getDay() + "/";
        File f = mkdirsmy(dir, file.getOriginalFilename());
        
        
        
        //上传到指定位置
        FileOutputStream write = new FileOutputStream(f);
        byte[] decoderBytes = file.getBytes();
        write.write(decoderBytes);
        write.close();*/
		MultipartFile files[] =  {file};
		//调用http客户端上传到图片服务器 直接返回一个图片的相对路径
		String url = HttpClientUpload.httpClientUploadFile(files, tableName);
		//服务器直接返回来的是绝对路径 我们把他截取个相对服务器返回路径
        Map<String,String> map = new HashMap<String,String> ();
        //往map中存放路径跟文件名
        map.put("url", url);
        map.put("name", file.getOriginalFilename());

        return map;
	}
    
    

}