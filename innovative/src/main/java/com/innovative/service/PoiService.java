package com.innovative.service;


import com.github.pagehelper.StringUtil;
import com.innovative.bean.Association;
import com.innovative.bean.Equipment;
import com.innovative.bean.Expert;
import com.innovative.bean.Organization;
import com.innovative.bean.Solution;
import com.innovative.bean.TechnicalReport;
import com.innovative.dao.PoiDao;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PoiUtil;
//import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;


@Service
@Transactional
public class PoiService {

//    private static Logger log = Logger.getLogger(PoiService.class);
	private static Logger log = LoggerFactory.getLogger(PoiService.class);

    @Autowired
    PoiDao poiDao;





    /**
     * 批量导入专家excel到数据库
     * @param file 专家文件（多个）
     * @return
     */
    
    public JsonResult importExcel(MultipartFile[] file,HttpServletRequest req){

        if(file == null){
            return new JsonResult(false,"没有要解析的文件!");
        }
        // 创建Workbook
        Workbook wb = null;
        // 创建sheet
        Sheet sheet = null;
        try {
        //循环传递过来的文件
        for (int i = 0; i < file.length; i++) {

                //将文件转换成流
                InputStream inputStream = file[i].getInputStream();
                // 获取文件后缀名
                String fileSufix = file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                //根据后缀判断excel 2003 or 2007+
                if (fileSufix.equals(".xls")) {
                    wb = WorkbookFactory.create(inputStream);
                } else {
                    wb = new XSSFWorkbook(inputStream);
                }
                //获取excel sheet总数
                int sheetNumbers = wb.getNumberOfSheets();
                //存放除了图片的专家信息对象，Key为行数
                List<Map<String, Object>> beanList = new ArrayList<>();
                Map<String, Object> map;
                Expert expert;
                // 循环sheet
                for (int she = 0; she < sheetNumbers; she++){
                    //取出当前循环的sheet
                    sheet = wb.getSheetAt(she);
                    if(sheet.getPhysicalNumberOfRows() <=0) {
                    	continue;
                    }
                    //获得当前sheet的总行数
                    int totalRowNum = sheet.getLastRowNum();
                    System.out.println(totalRowNum);
                    //封装每行sheet的数据
                    for(int a = 1 ; a <= totalRowNum ; a++){
                        //获得第a行对象
                        Row row = sheet.getRow(a);
                        map = new HashMap<>();
                        expert = new Expert();
                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
                        	 throw new Exception(a+"这个怎么没有专家名字呢核对数据吧!");
                        }
                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
                        expert.setName(getCellValue(row.getCell((short)1)));
                        expert.setSectors(getArrCellValue(row.getCell((short)2)));  
                        expert.setTags(getArrCellValue(row.getCell((short)3)));
                        expert.setUnit(getCellValue(row.getCell((short)4)));
                        expert.setEducation(getCellValue(row.getCell((short)5)));
                        expert.setJobTitle(getCellValue(row.getCell((short)6)));
                        expert.sethFactor(getCellIntValue(row.getCell((short)7)));
                        expert.setResearchDirection(getCellValue(row.getCell((short)8)));
                        expert.setResearchAchievement(getCellValue(row.getCell((short)9)));
                        expert.setResume(getCellValue(row.getCell((short)10)));//个人信息
                        expert.setCooperationStatus(getCellIntValue(row.getCell((short)11)));
                        expert.setContact(getCellValue(row.getCell((short)12 )));
                        //合作状态
                        expert.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
                        expert.setId(Misc.uuid());
                        /*expert.setTags("{}");*///123
                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
                        map.put(String.valueOf(she)+","+String.valueOf(a), expert);
                        beanList.add(map);
                    }
                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
                    // map存储excel图片
                    Map<String, PictureData> sheetIndexPicMap;
                    // 判断用07还是03的方法获取图片
                    if (fileSufix.equals("xls")){
                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
                    }else {
                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
                    }
                    //sheetIndexPicMap存放当前sheet所有的图片Key是角标
                    //将此sheet的map数据跟sheet对象集合进行对比，相同key的时候将此文件路径set到头像中，是个上传后的地址,并上传图片
                    
                    List<Map<String, Object>> resultList = PoiUtil.getbeanListAndPic(beanList, sheetIndexPicMap);
                    System.out.println(beanList.size());
                    //重新规整后的sheet集合对象进行数据库保存操作
                 /*   for (Map<String, Object> result : resultList){
                        Object key[] = result.keySet().toArray();
                        Expert addExpert = (Expert)result.get(key[0]);
                       // addExpert.setId(Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(7,11)));
                        addExpert.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        poiDao.addPoiExpert(addExpert);
                    }*/
                     poiDao.batchAddPoiExpert(resultList);
                     System.out.println(resultList.size());
                    
                }
        }
        }catch (Exception e){
        	e.printStackTrace();
            log.error("文件上传解析异常："+e);
            return new JsonResult(false, "文件上传解析异常："+e);
        }
        //每个文件都读取成功
        return new JsonResult(true, "解析成功!");
    }




/**
 * 上传行业协会
 * @param file
 * @param request 
 * @return
 */
	public JsonResult importAssociation(MultipartFile file, HttpServletRequest request) {
		if(file == null){
            return new JsonResult(false,"没有接收到文件，请联系管理员!");
        }
        // 创建Workbook
        Workbook wb = null;
        // 创建sheet
        Sheet sheet = null;
        try {
        //循环传递过来的文件

                //将文件转换成流
                InputStream inputStream = file.getInputStream();
                // 获取文件后缀名
                String fileSufix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //根据后缀判断excel 2003 or 2007+
                if (fileSufix.equals(".xls")) {
                    wb = WorkbookFactory.create(inputStream);
                } else {
                    wb = new XSSFWorkbook(inputStream);
                }
                //获取excel sheet总数
                int sheetNumbers = wb.getNumberOfSheets();
                //存放除了图片的专家信息对象，Key为行数
                List<Map<String, Object>> beanList = new ArrayList<>();
                Map<String, Object> map;
                Association association;
                // 循环sheet
                for (int she = 0; she < sheetNumbers; she++){
                    //取出当前循环的sheet
                    sheet = wb.getSheetAt(she);
                    if(sheet.getPhysicalNumberOfRows() <=0) {
                    	continue;
                    }
                    //获得当前sheet的总行数
                    int totalRowNum = sheet.getLastRowNum();
                    //封装每行sheet的数据
                    for(int a = 1 ; a <= totalRowNum ; a++){
                        //获得第a行对象
                        Row row = sheet.getRow(a);
                        map = new HashMap<>();
                       
                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
                        	 throw new Exception(a+"缺少数据请核对!");
                        }
                        association = new Association();
                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
                        association.setName(getCellValue(row.getCell((short)1)));//协会名称
                        association.setSectors(getArrCellValue(row.getCell((short)2)));//领域
                        association.setTags(getArrCellValue(row.getCell((short)3)));//标签
                        association.setIntroduction(getCellValue(row.getCell((short)5)));//简介
                        association.setNature(getCellValue(row.getCell((short)6)));//性质
                        association.setDuration(getCellValue(row.getCell((short)7)));//入会有效期
                        association.setAvailableResources(getCellValue(row.getCell((short)8)));//可用资源
                        association.setCooperationStatus(getCellIntValue(row.getCell((short)9)));//合作状态
                        association.setContact(getCellValue(row.getCell((short)10)));//联系方式
                        association.setWebsite(getCellValue(row.getCell((short)11)));//网站链接
                      /*  association.setLogo(getCellValue(row.getCell((short)5)));
                        association.setRank(getCellIntValue(row.getCell((short)8)));
                        association.setRowVersion(getCellIntValue(row.getCell((short)9)));*/
                        association.setCreatedBy(CookiesUtil.getCookieValue(request,"user_name"));
                        association.setId(Misc.uuid());
                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
                        map.put(String.valueOf(she)+","+String.valueOf(a), association);
                        beanList.add(map);
                    }
                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
                    // map存储excel图片
                    Map<String, PictureData> sheetIndexPicMap;
                    // 判断用07还是03的方法获取图片
                    if (fileSufix.equals("xls")){
                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
                    }else {
                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
                    }
                    List<Map<String, Object>> resultList = PoiUtil.getAssotionListAndPic(beanList, sheetIndexPicMap);
                    if(resultList.size()>0){
                    	 poiDao.batchAddPoiAssociation(resultList);
                    }else{
                    	 return new JsonResult(false,"没有要解析的内容");
                    }
                    
                    
        }
        }catch (Exception e){
        	e.printStackTrace();
            log.error("文件上传解析异常："+e);
            return new JsonResult(false,"文件上传解析异常："+e);
        }
        //每个文件都读取成功
        return new JsonResult(true,"解析成功!");
	}
	




		public JsonResult importEquipment(MultipartFile file, HttpServletRequest request) {
			if(file == null){
				 return new JsonResult(false,"没有要解析的文件!");
	        }
	        // 创建Workbook
	        Workbook wb = null;
	        // 创建sheet
	        Sheet sheet = null;
	        try {
	        //循环传递过来的文件

	                //将文件转换成流
	                InputStream inputStream = file.getInputStream();
	                // 获取文件后缀名
	                String fileSufix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	                //根据后缀判断excel 2003 or 2007+
	                if (fileSufix.equals(".xls")) {
	                    wb = WorkbookFactory.create(inputStream);
	                } else {
	                    wb = new XSSFWorkbook(inputStream);
	                }
	                //获取excel sheet总数
	                int sheetNumbers = wb.getNumberOfSheets();
	                //存放除了图片的专家信息对象，Key为行数
	                List<Map<String, Object>> beanList = new ArrayList<>();
	                Map<String, Object> map;
	                Equipment equipment;
	                // 循环sheet
	                for (int she = 0; she < sheetNumbers; she++){
	                    //取出当前循环的sheet
	                    sheet = wb.getSheetAt(she);
	                    if(sheet.getPhysicalNumberOfRows() <=0) {
	                    	continue;
	                    }
	                    //获得当前sheet的总行数
	                    int totalRowNum = sheet.getLastRowNum();
	                    //封装每行sheet的数据
	                    for(int a = 1 ; a <= totalRowNum ; a++){
	                        //获得第a行对象
	                        Row row = sheet.getRow(a);
	                        map = new HashMap<>();
	                       
	                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
	                            continue;
	                        }
	                        equipment = new Equipment();
	                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
	                        equipment.setName(getCellValue(row.getCell((short)1)));
	                        equipment.setSectors(getArrCellValue(row.getCell((short)2)));
	                        equipment.setTags(getArrCellValue(row.getCell((short)3)));
	                        equipment.setUnit(getCellValue(row.getCell((short)4)));
	                        equipment.setState(getCellValue(row.getCell((short)5)));
	                        equipment.setManufacturer(getCellValue(row.getCell((short)6)));
	                        equipment.setPurchasedAt(getCellValue(row.getCell((short)8)));
	                        equipment.setIntroduction(getCellValue(row.getCell((short)9)));
	                        equipment.setSharing(getCellValue(row.getCell((short)10)));
	                        
	                        equipment.setContact(getCellValue(row.getCell((short)11)));
	                       
	                        
	                       
	                       
	                        /*equipment.setRank(getCellIntValue(row.getCell((short)6)));
	                        equipment.setRowVersion(getCellIntValue(row.getCell((short)7)));*/
	                        
	                        
	                       
	                        
	                        
	                        equipment.setCreatedBy(CookiesUtil.getCookieValue(request,"user_name"));
	                        equipment.setId(Misc.uuid());
	                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
	                        map.put(String.valueOf(she)+","+String.valueOf(a), equipment);
	                        beanList.add(map);
	                    }
	                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
	                    // map存储excel图片
	                    Map<String, PictureData> sheetIndexPicMap;
	                    // 判断用07还是03的方法获取图片
	                    if (fileSufix.equals("xls")){
	                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
	                    }else {
	                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
	                    }
	                    List<Map<String, Object>> resultList = PoiUtil.getEquipmentListAndPic(beanList, sheetIndexPicMap);
	                    if(resultList.size()>0){
	                    	 poiDao.batchAddPoiEquipment(resultList);
	                    }else{
	                    	return new JsonResult(false,"没有要解析的文件");
	                    }
	                    
	                    
	        }
	        }catch (Exception e){
	        	e.printStackTrace();
	            log.error("文件上传解析异常："+e);
	            return new JsonResult(false,"文件上传解析异常："+e);
	        }
	        //每个文件都读取成功
	        return new JsonResult(true,"文件解析成功");
		}	
		
		
		/**
		 * 合作机构
		 * @param file
		 * @param request
		 * @return
		 */
		public JsonResult importOrganizations(MultipartFile file, HttpServletRequest request) {
			if(file == null){
				 return new JsonResult(false,"没有要解析的文件！");
	        }
	        // 创建Workbook
	        Workbook wb = null;
	        // 创建sheet
	        Sheet sheet = null;
	        try {
	        //循环传递过来的文件

	                //将文件转换成流
	                InputStream inputStream = file.getInputStream();
	                // 获取文件后缀名
	                String fileSufix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	                //根据后缀判断excel 2003 or 2007+
	                if (fileSufix.equals(".xls")) {
	                    wb = WorkbookFactory.create(inputStream);
	                } else {
	                    wb = new XSSFWorkbook(inputStream);
	                }
	                //获取excel sheet总数
	                int sheetNumbers = wb.getNumberOfSheets();
	                //存放除了图片的专家信息对象，Key为行数
	                List<Map<String, Object>> beanList = new ArrayList<>();
	                Map<String, Object> map;
	                Organization organization;
	                // 循环sheet
	                for (int she = 0; she < sheetNumbers; she++){
	                    //取出当前循环的sheet
	                    sheet = wb.getSheetAt(she);
	                    if(sheet.getPhysicalNumberOfRows() <=0) {
	                    	continue;
	                    }
	                    //获得当前sheet的总行数
	                    int totalRowNum = sheet.getLastRowNum();
	                    //封装每行sheet的数据
	                    for(int a = 1 ; a <= totalRowNum ; a++){
	                        //获得第a行对象
	                        Row row = sheet.getRow(a);
	                        map = new HashMap<>();
	                       
	                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
	                            continue;
	                        }
	                        organization = new Organization();
	                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
	                        organization.setName(getCellValue(row.getCell((short)1)));//合作机构名称
	                        organization.setSectors(getArrCellValue(row.getCell((short)2)));//领域
	                        organization.setTags(getArrCellValue(row.getCell((short)3)));//标签
	                        organization.setIntroduction(getCellValue(row.getCell((short)5)));//简介
	                        organization.setNature(getCellValue(row.getCell((short)6)));//性质
	                        organization.setAchievements(getCellValue(row.getCell((short)7)));//成果
	                        organization.setCooperationStatus(getCellIntValue(row.getCell((short)8)));//合作状态
	                        organization.setContact(getCellValue(row.getCell((short)9)));//联系方式
	                        organization.setWebsite(getCellValue(row.getCell((short)10)));//网址
	                        organization.setId(Misc.uuid());
	                        organization.setCreatedBy(CookiesUtil.getCookieValue(request,"user_name"));
	                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
	                        map.put(String.valueOf(she)+","+String.valueOf(a), organization);
	                        beanList.add(map);
	                    }
	                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
	                    // map存储excel图片
	                    Map<String, PictureData> sheetIndexPicMap;
	                    // 判断用07还是03的方法获取图片
	                    if (fileSufix.equals("xls")){
	                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
	                    }else {
	                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
	                    }
	                    List<Map<String, Object>> resultList = PoiUtil.getOrganizationsListAndPic(beanList, sheetIndexPicMap);
	                    if(resultList.size()>0){
	                    	 poiDao.batchAddPoiOrganization(resultList);
	                    }else{
	                    	return new JsonResult(false,"没有要上传的数据，请检查数据");
	                    }
	                    
	                    
	        }
	        }catch (Exception e){
	        	e.printStackTrace();
	            log.error("文件上传解析异常："+e);
	            return new JsonResult(false,"文件上传解析异常："+e);
	        }
	        //每个文件都读取成功
	           return new JsonResult(true,"文件上传成功!");
		}	
		

		//获取单元格返回单元格数据
		private String getCellValue(Cell cell) {
			String value = null;
			if (cell == null) {
				return "";
			}
			// 简单的查检列类型
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:// 字符串
					value = cell.getRichStringCellValue().getString();
					break;
				case Cell.CELL_TYPE_NUMERIC:// 数字
					long dd = (long) cell.getNumericCellValue();
					value = dd + "";
					break;
				case Cell.CELL_TYPE_BLANK:
					value = "";
					break;
				case Cell.CELL_TYPE_FORMULA:
					value = String.valueOf(cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_BOOLEAN:// boolean型值
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					value = String.valueOf(cell.getErrorCellValue());
					break;
				default:
					break;
			}
			if (null != value) {
				value = value.trim();
			}
			return value;
	}	
		
		//获取单元格返回单元格数据
			private String[] getArrCellValue(Cell cell) {
				String value[] = null;
				if (cell == null) {
					return value;
				}
				// 简单的查检列类型
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:// 字符串
						value = cell.getRichStringCellValue().getString().split(",");
						break;
					default:
						break;
				}
				return value;
		}	

		//获取单元格返回单元格数据
			private int getCellIntValue(Cell cell) {
				String value = null;
				if (cell == null) {
					return 0;
				}
				// 简单的查检列类型
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:// 数字
						long dd = (long) cell.getNumericCellValue();
						value = dd + "";
						break;
					default:
						break;
				}
				if (null != value) {
					value = value.trim();
				}else{
					return 0;
				}
				return Integer.valueOf(value).intValue();
		}




			public  JsonResult importSolution(MultipartFile file, HttpServletRequest request) {
				if(file == null){
		            return new JsonResult(false,"没有要解析的文件");
		        }
		        // 创建Workbook
		        Workbook wb = null;
		        // 创建sheet
		        Sheet sheet = null;
		        try {
		        //循环传递过来的文件

		                //将文件转换成流
		                InputStream inputStream = file.getInputStream();
		                // 获取文件后缀名
		                String fileSufix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		                //根据后缀判断excel 2003 or 2007+
		                if (fileSufix.equals(".xls")) {
		                    wb = WorkbookFactory.create(inputStream);
		                } else {
		                    wb = new XSSFWorkbook(inputStream);
		                }
		                //获取excel sheet总数
		                int sheetNumbers = wb.getNumberOfSheets();
		                //存放除了图片的专家信息对象，Key为行数
		                List<Map<String, Object>> beanList = new ArrayList<>();
		                Map<String, Object> map;
		                Solution solution;
		                // 循环sheet
		                for (int she = 0; she < sheetNumbers; she++){
		                    //取出当前循环的sheet
		                    sheet = wb.getSheetAt(she);
		                    if(sheet.getPhysicalNumberOfRows() <=0) {
		                    	continue;
		                    }
		                    //获得当前sheet的总行数
		                    int totalRowNum = sheet.getLastRowNum();
		                    //封装每行sheet的数据
		                    for(int a = 1 ; a <= totalRowNum ; a++){
		                        //获得第a行对象
		                        Row row = sheet.getRow(a);
		                        map = new HashMap<>();
		                       
		                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
		                            continue;
		                        }
		                        solution = new Solution();
		                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
		                        solution.setContent(getCellValue(row.getCell((short)0)));
		                        solution.setName(getCellValue(row.getCell((short)1)));
		                        solution.setRank(getCellIntValue(row.getCell((short)3)));
		                        solution.setRowVersion(getCellIntValue(row.getCell((short)4)));
		                        solution.setSectors(getArrCellValue(row.getCell((short)5)));
		                        solution.setSummary(getCellValue(row.getCell((short)6)));
		                        solution.setTags(getArrCellValue(row.getCell((short)7)));
		                        solution.setId(Misc.uuid());
		                        solution.setCreatedBy(CookiesUtil.getCookieValue(request,"user_name"));
		                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
		                        map.put(String.valueOf(she)+","+String.valueOf(a), solution);
		                        beanList.add(map);
		                    }
		                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
		                    // map存储excel图片
		                    Map<String, PictureData> sheetIndexPicMap;
		                    // 判断用07还是03的方法获取图片
		                    if (fileSufix.equals("xls")){
		                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
		                    }else {
		                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
		                    }
		                    List<Map<String, Object>> resultList = PoiUtil.getSolutionListAndPic(beanList, sheetIndexPicMap);
		                    if(resultList.size()>0){
		                    	 poiDao.batchAddSolution(resultList);
		                    }else{
		                    	return new JsonResult(false,"没有要上数据，请检查数据!");
		                    }
		                    
		                    
		        }
		        }catch (Exception e){
		        	e.printStackTrace();
		            log.error("文件上传解析异常："+e);
		            return  new JsonResult(false,"文件上传解析异常："+e);
		        }
		        //每个文件都读取成功
		        return new JsonResult(true,"文件上传解析成功");
			}




			public JsonResult importTechnicalReports(MultipartFile file, HttpServletRequest request) {
				if(file == null){
		            return new JsonResult(false,"没有要上传的文件!");
		        }
		        // 创建Workbook
		        Workbook wb = null;
		        // 创建sheet
		        Sheet sheet = null;
		        try {
		        //循环传递过来的文件

		                //将文件转换成流
		                InputStream inputStream = file.getInputStream();
		                // 获取文件后缀名
		                String fileSufix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		                //根据后缀判断excel 2003 or 2007+
		                if (fileSufix.equals(".xls")) {
		                    wb = WorkbookFactory.create(inputStream);
		                } else {
		                    wb = new XSSFWorkbook(inputStream);
		                }
		                //获取excel sheet总数
		                int sheetNumbers = wb.getNumberOfSheets();
		                //存放除了图片的专家信息对象，Key为行数
		                List<Map<String, Object>> beanList = new ArrayList<>();
		                Map<String, Object> map;
		                TechnicalReport technicalReport;
		                // 循环sheet
		                for (int she = 0; she < sheetNumbers; she++){
		                    //取出当前循环的sheet
		                    sheet = wb.getSheetAt(she);
		                    if(sheet.getPhysicalNumberOfRows() <=0) {
		                    	continue;
		                    }
		                    //获得当前sheet的总行数
		                    int totalRowNum = sheet.getLastRowNum();
		                    //封装每行sheet的数据
		                    for(int a = 1 ; a <= totalRowNum ; a++){
		                        //获得第a行对象
		                        Row row = sheet.getRow(a);
		                        map = new HashMap<>();
		                       
		                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
		                            break;
		                        }
		                        technicalReport = new TechnicalReport();
		                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
		                        technicalReport.setContent(getCellValue(row.getCell((short)0)));
		                        technicalReport.setName(getCellValue(row.getCell((short)1)));
		                        technicalReport.setRank(getCellIntValue(row.getCell((short)3)));
		                        technicalReport.setRowVersion(getCellIntValue(row.getCell((short)4)));
		                        technicalReport.setSectors(getArrCellValue(row.getCell((short)5)));
		                        technicalReport.setSummary(getCellValue(row.getCell((short)6)));
		                        technicalReport.setTags(getArrCellValue(row.getCell((short)7)));
		                        technicalReport.setId(Misc.uuid());
		                        technicalReport.setCreatedBy(CookiesUtil.getCookieValue(request,"user_name"));
		                        //封装第a行的对象信息,用map封装expert 对象 key值对应sheet.row下标
		                        map.put(String.valueOf(she)+","+String.valueOf(a), technicalReport);
		                        beanList.add(map);
		                    }
		                    //循环完第一个sheet之后beanList放入的是没有存储图片的行数记录对象
		                    // map存储excel图片
		                    Map<String, PictureData> sheetIndexPicMap;
		                    // 判断用07还是03的方法获取图片
		                    if (fileSufix.equals("xls")){
		                        sheetIndexPicMap = PoiUtil.getSheetPictrues03(she, (HSSFSheet) sheet, (HSSFWorkbook) wb);
		                    }else {
		                        sheetIndexPicMap = PoiUtil.getSheetPictrues07(she, (XSSFSheet) sheet, (XSSFWorkbook) wb);
		                    }
		                    List<Map<String, Object>> resultList = PoiUtil.getTechnicalReportsListAndPic(beanList, sheetIndexPicMap);
		                    if(resultList.size()>0){
		                    	 poiDao.batchAddTechnicalReports(resultList);
		                    }else{
		                    	return new JsonResult(false,"没有要解析的数据！请检查！");
		                    }
		                    
		                    
		        }
		        }catch (Exception e){
		        	e.printStackTrace();
		            log.error("文件上传解析异常："+e);
		            return new JsonResult(false,"文件上传解析异常："+e);
		        }
		        //每个文件都读取成功
		        return new JsonResult(true,"解析成功！");
			}



}
