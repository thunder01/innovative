package com.innovative.utils;

import com.innovative.bean.Association;
import com.innovative.bean.Equipment;
import com.innovative.bean.Expert;
import com.innovative.bean.Organization;
import com.innovative.bean.Solution;
import com.innovative.bean.TechnicalReport;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.innovative.utils.FileUpload.mkdirsmy;

public class PoiUtil {


    /**
     * 获取Excel2003图片
     * @param sheetNum 当前sheet编号
     * @param sheet 当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
     * @throws IOException
     */
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum, HSSFSheet sheet, HSSFWorkbook workbook) {
        //图片MAP
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
        //获取所有图片
        List<HSSFPictureData> pictures = workbook.getAllPictures();

        if (pictures.size() != 0) {
            //获取图形形状 HSSFShape
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                //获取图形锚点
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                //判断锚点处图形的类型是否为图片
                if (shape instanceof HSSFPicture) {
                    HSSFPicture pic = (HSSFPicture) shape;
                    int pictureIndex = pic.getPictureIndex() - 1;
                    HSSFPictureData picData = pictures.get(pictureIndex);
                    String picIndex = String.valueOf(sheetNum) +","+String.valueOf(anchor.getRow1());
                    sheetIndexPicMap.put(picIndex, picData);
                }
            }
            return sheetIndexPicMap;
        } else {
            return null;
        }
    }



    /**
     * 获取Excel2007图片
     * @param sheetNum 当前sheet编号
     * @param sheet 当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
     */
    public static Map<String, PictureData> getSheetPictrues07(int sheetNum, XSSFSheet sheet, XSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();

        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = pic.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String picIndex = String.valueOf(sheetNum) +","+String.valueOf(ctMarker.getRow());
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());
                }
            }
        }
        return sheetIndexPicMap;
    }




    /**
     * 将map中的图片规整到集合对象的图片字段中，并上传图片
     * @param beanList 未封装图片的bean对象集合
     * @param sheetIndexPicMap 只有图片的map集合
     * @return 合并集合
     */
    public static List<Map<String, Object>> getbeanListAndPic(List<Map<String, Object>> beanList, Map<String, PictureData> sheetIndexPicMap) throws IOException{

        List<Map<String, Object>> resultList = new ArrayList<>();

        Map<String, Object> map;
        //获取map所有的key
        Object keys[] = sheetIndexPicMap.keySet().toArray();
        //同时遍历2个集合将相同的key合并
        for (int i = 0; i < beanList.size(); i++){

            Object[] key = beanList.get(i).keySet().toArray();
            Expert expert = (Expert)beanList.get(i).get(key[0].toString());
            map = new HashMap<>();
            for(Object PicMapKey : keys){
                if(key[0].toString().equals(PicMapKey.toString())){
                    //说明一样，进行上传合并
                   // 
                    //说明2个集合的key是一样
                    //生成此条记录的头像地址
                    // 获取图片流
                    PictureData pic = sheetIndexPicMap.get(PicMapKey);
                    String name = String.valueOf(System.currentTimeMillis());
                    // 获取图片格式
                    String ext = pic.suggestFileExtension();
                    String picUrl = Config.FILE_URL + "Expert" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

                    //上传图片到指定位置
                    byte[] data = pic.getData();
                    //创建文件路径和文件夹
                    String dir = Config.FILE_URL + "Expert" + "/" + DateUtil.getDay() + "/" ;
                    File f = mkdirsmy(dir, name + "." + ext);
                    //上传到指定位置
                    FileOutputStream write = new FileOutputStream(f);
                    write.write(data);
                    write.close();

                    //将此url修改到集合此key的头像url中
                   // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
                    expert.setAvatar(picUrl);
                }
                map.put(key[0].toString(), expert);
               
            }
            if(map.isEmpty()){
            	resultList.add(beanList.get(i));
            }else{
            	 resultList.add(map);
            }


        }
        return resultList;
    }
    
    /**
     * 将map中的图片规整到集合对象的图片字段中，并上传图片
     * @param beanList 未封装图片的bean对象集合
     * @param sheetIndexPicMap 只有图片的map集合
     * @return 合并集合
     */
    public static List<Map<String, Object>> getAssotionListAndPic(List<Map<String, Object>> beanList, Map<String, PictureData> sheetIndexPicMap) throws IOException{

        List<Map<String, Object>> resultList = new ArrayList<>();

        Map<String, Object> map;
        //获取map所有的key
        Object keys[] = sheetIndexPicMap.keySet().toArray();
        //同时遍历2个集合将相同的key合并
        for (int i = 0; i < beanList.size(); i++){

            Object[] key = beanList.get(i).keySet().toArray();
            Association association = (Association)beanList.get(i).get(key[0].toString());
            map = new HashMap<>();
            for(Object PicMapKey : keys){
                if(key[0].toString().equals(PicMapKey.toString())){
                    //说明一样，进行上传合并
                   // 
                    //说明2个集合的key是一样
                    //生成此条记录的头像地址
                    // 获取图片流
                    PictureData pic = sheetIndexPicMap.get(PicMapKey);
                    String name = String.valueOf(System.currentTimeMillis());
                    // 获取图片格式
                    String ext = pic.suggestFileExtension();
                    String picUrl = Config.FILE_URL + "Association" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

                    //上传图片到指定位置
                    byte[] data = pic.getData();
                    //创建文件路径和文件夹
                    String dir = Config.FILE_URL + "Association" + "/" + DateUtil.getDay() + "/" ;
                    File f = mkdirsmy(dir, name + "." + ext);
                    //上传到指定位置
                    FileOutputStream write = new FileOutputStream(f);
                    write.write(data);
                    write.close();

                    //将此url修改到集合此key的头像url中
                   // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
                    association.setLogo(picUrl);
                }
                map.put(key[0].toString(), association);
              
            }
            if(map.isEmpty())
            	resultList.add(beanList.get(i));
            else{
         	   resultList.add(map);
            }


        }
        return resultList;
    }
    
    /**
     * 将map中的图片规整到集合对象的图片字段中，并上传图片 (希望可以实现公共类)
     * @param beanList 未封装图片的bean对象集合
     * @param sheetIndexPicMap 只有图片的map集合
     * @return 合并集合
     */
    public static List<Map<String, Object>> getCommonbeanListAndPic(String modname ,List<Map<String, Object>> beanList, Map<String, PictureData> sheetIndexPicMap) throws IOException{

    	 List<Map<String, Object>> resultList = new ArrayList<>();

         Map<String, Object> map;
         //获取map所有的key
         Object keys[] = sheetIndexPicMap.keySet().toArray();
         //同时遍历2个集合将相同的key合并
         for (int i = 0; i < beanList.size(); i++){

             Object[] key = beanList.get(i).keySet().toArray();
             Expert expert = (Expert)beanList.get(i).get(key[0].toString());
             map = new HashMap<>();
             for(Object PicMapKey : keys){
                 if(key[0].toString().equals(PicMapKey.toString())){
                     //说明一样，进行上传合并
                    // 
                     //说明2个集合的key是一样
                     //生成此条记录的头像地址
                     // 获取图片流
                     PictureData pic = sheetIndexPicMap.get(PicMapKey);
                     String name = String.valueOf(System.currentTimeMillis());
                     // 获取图片格式
                     String ext = pic.suggestFileExtension();
                     String picUrl = Config.FILE_URL + "Expert" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

                     //上传图片到指定位置
                     byte[] data = pic.getData();
                     //创建文件路径和文件夹
                     String dir = Config.FILE_URL + "Expert" + "/" + DateUtil.getDay() + "/" ;
                     File f = mkdirsmy(dir, name + "." + ext);
                     //上传到指定位置
                     FileOutputStream write = new FileOutputStream(f);
                     write.write(data);
                     write.close();

                     //将此url修改到集合此key的头像url中
                    // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
                     expert.setAvatar(picUrl);
                 }
                 map.put(key[0].toString(), expert);
               
             }
             if(map.isEmpty())
             	resultList.add(beanList.get(i));
             else{
          	   resultList.add(map);
             }


         }
         return resultList;
    }


	/**
	 * 此方法代写
	 * @param beanList
	 * @param sheetIndexPicMap
	 * @return
	 */
	/*public   static Sheet getSheet(MultipartFile[] file){
		return null;
	}*/



	 /**
     * 将map中的图片规整到集合对象的图片字段中，并上传图片 (希望可以实现公共类)
     * @param beanList 未封装图片的bean对象集合
     * @param sheetIndexPicMap 只有图片的map集合
     * @return 合并集合
     */
   public static List<Map<String, Object>> getEquipmentListAndPic(List<Map<String, Object>> beanList, Map<String, PictureData> sheetIndexPicMap) throws IOException{

	   List<Map<String, Object>> resultList = new ArrayList<>();

       Map<String, Object> map;
       //获取map所有的key
       Object keys[] = sheetIndexPicMap.keySet().toArray();
       //同时遍历2个集合将相同的key合并
       for (int i = 0; i < beanList.size(); i++){

           Object[] key = beanList.get(i).keySet().toArray();
           Equipment equipment = (Equipment)beanList.get(i).get(key[0].toString());
           map = new HashMap<>();
           for(Object PicMapKey : keys){
               if(key[0].toString().equals(PicMapKey.toString())){
                   //说明一样，进行上传合并
                   //说明2个集合的key是一样
                   //生成此条记录的头像地址
                   // 获取图片流
                   PictureData pic = sheetIndexPicMap.get(PicMapKey);
                   String name = String.valueOf(System.currentTimeMillis());
                   // 获取图片格式
                   String ext = pic.suggestFileExtension();
                   String picUrl = Config.FILE_URL + "Equipment" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

                   //上传图片到指定位置
                   byte[] data = pic.getData();
                   //创建文件路径和文件夹
                   String dir = Config.FILE_URL + "Equipment" + "/" + DateUtil.getDay() + "/" ;
                   File f = mkdirsmy(dir, name + "." + ext);
                   //上传到指定位置
                   FileOutputStream write = new FileOutputStream(f);
                   write.write(data);
                   write.close();

                   //将此url修改到集合此key的头像url中
                  // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
                   equipment.setPicture(picUrl);
               }
               map.put(key[0].toString(), equipment);
             
           }
           if(map.isEmpty())
        	   resultList.add(beanList.get(i));
           else{
        	   resultList.add(map);
           }
           


       }
       return resultList;
    }



	public static List<Map<String, Object>> getOrganizationsListAndPic(List<Map<String, Object>> beanList,
			Map<String, PictureData> sheetIndexPicMap) throws IOException {
		  List<Map<String, Object>> resultList = new ArrayList<>();

	       Map<String, Object> map;
	       //获取map所有的key
	       Object keys[] = sheetIndexPicMap.keySet().toArray();
	       //同时遍历2个集合将相同的key合并
	       for (int i = 0; i < beanList.size(); i++){

	           Object[] key = beanList.get(i).keySet().toArray();
	           Organization organization = (Organization)beanList.get(i).get(key[0].toString());
	           map = new HashMap<>();
	           for(Object PicMapKey : keys){
	               if(key[0].toString().equals(PicMapKey.toString())){
	                   //说明一样，进行上传合并
	                  // 
	                   //说明2个集合的key是一样
	                   //生成此条记录的头像地址
	                   // 获取图片流
	                   PictureData pic = sheetIndexPicMap.get(PicMapKey);
	                   String name = String.valueOf(System.currentTimeMillis());
	                   // 获取图片格式
	                   String ext = pic.suggestFileExtension();
	                   String picUrl = Config.FILE_URL + "Organization" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

	                   //上传图片到指定位置
	                   byte[] data = pic.getData();
	                   //创建文件路径和文件夹
	                   String dir = Config.FILE_URL + "Organization" + "/" + DateUtil.getDay() + "/" ;
	                   File f = mkdirsmy(dir, name + "." + ext);
	                   //上传到指定位置
	                   FileOutputStream write = new FileOutputStream(f);
	                   write.write(data);
	                   write.close();

	                   //将此url修改到集合此key的头像url中
	                  // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
	                   organization.setLogo(picUrl);
	               }
	               map.put(key[0].toString(), organization);
	             
	           }
	           if(map.isEmpty())
	           	resultList.add(beanList.get(i));
	           else{
	        	   resultList.add(map);
	           }


	       }
	       return resultList;
	}



	public static List<Map<String, Object>> getTechnicalReportsListAndPic(List<Map<String, Object>> beanList,
			Map<String, PictureData> sheetIndexPicMap) throws IOException {
		List<Map<String, Object>> resultList = new ArrayList<>();

	       Map<String, Object> map;
	       //获取map所有的key
	       Object keys[] = sheetIndexPicMap.keySet().toArray();
	       //同时遍历2个集合将相同的key合并
	       for (int i = 0; i < beanList.size(); i++){

	           Object[] key = beanList.get(i).keySet().toArray();
	           TechnicalReport technicalReport = (TechnicalReport)beanList.get(i).get(key[0].toString());
	           map = new HashMap<>();
	           for(Object PicMapKey : keys){
	               if(key[0].toString().equals(PicMapKey.toString())){
	                   //说明一样，进行上传合并
	                  // 
	                   //说明2个集合的key是一样
	                   //生成此条记录的头像地址
	                   // 获取图片流
	                   PictureData pic = sheetIndexPicMap.get(PicMapKey);
	                   String name = String.valueOf(System.currentTimeMillis());
	                   // 获取图片格式
	                   String ext = pic.suggestFileExtension();
	                   String picUrl = Config.FILE_URL + "TechnicalReports" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

	                   //上传图片到指定位置
	                   byte[] data = pic.getData();
	                   //创建文件路径和文件夹
	                   String dir = Config.FILE_URL + "TechnicalReports" + "/" + DateUtil.getDay() + "/" ;
	                   File f = mkdirsmy(dir, name + "." + ext);
	                   //上传到指定位置
	                   FileOutputStream write = new FileOutputStream(f);
	                   write.write(data);
	                   write.close();

	                   //将此url修改到集合此key的头像url中
	                  // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
	                   technicalReport.setPictures(picUrl);
	               }
	               map.put(key[0].toString(), technicalReport);
	             
	           }
	           if(map.isEmpty())
	        	   resultList.add(beanList.get(i));
	           else{
	        	   resultList.add(map);
	           }


	       }
	       return resultList;
	}



	public static List<Map<String, Object>> getSolutionListAndPic(List<Map<String, Object>> beanList,
			Map<String, PictureData> sheetIndexPicMap) throws IOException {
		List<Map<String, Object>> resultList = new ArrayList<>();

	       Map<String, Object> map;
	       //获取map所有的key
	       Object keys[] = sheetIndexPicMap.keySet().toArray();
	       //同时遍历2个集合将相同的key合并
	       for (int i = 0; i < beanList.size(); i++){

	           Object[] key = beanList.get(i).keySet().toArray();
	           Solution solution = (Solution)beanList.get(i).get(key[0].toString());
	           map = new HashMap<>();
	           for(Object PicMapKey : keys){
	               if(key[0].toString().equals(PicMapKey.toString())){
	                   //说明一样，进行上传合并
	                  // 
	                   //说明2个集合的key是一样
	                   //生成此条记录的头像地址
	                   // 获取图片流
	                   PictureData pic = sheetIndexPicMap.get(PicMapKey);
	                   String name = String.valueOf(System.currentTimeMillis());
	                   // 获取图片格式
	                   String ext = pic.suggestFileExtension();
	                   String picUrl = Config.FILE_URL + "solution" + "/" + DateUtil.getDay() + "/" + name + "." + ext;

	                   //上传图片到指定位置
	                   byte[] data = pic.getData();
	                   //创建文件路径和文件夹
	                   String dir = Config.FILE_URL + "solution" + "/" + DateUtil.getDay() + "/" ;
	                   File f = mkdirsmy(dir, name + "." + ext);
	                   //上传到指定位置
	                   FileOutputStream write = new FileOutputStream(f);
	                   write.write(data);
	                   write.close();

	                   //将此url修改到集合此key的头像url中
	                  // Expert expert = (Expert)beanList.get(i).get(key[0].toString());
	                   solution.setPictures(picUrl);
	               }
	               map.put(key[0].toString(), solution);
	             
	           }
	           if(map.isEmpty())
	           	resultList.add(beanList.get(i));
	           else{
	        	   resultList.add(map);
	           }


	       }
	       return resultList;
	}






}
