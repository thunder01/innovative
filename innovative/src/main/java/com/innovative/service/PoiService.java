package com.innovative.service;


import com.github.pagehelper.StringUtil;
import com.innovative.bean.Expert;
import com.innovative.dao.PoiDao;
import com.innovative.utils.Misc;
import com.innovative.utils.PoiUtil;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.*;


@Service
@Transactional
public class PoiService {

    private static Logger log = Logger.getLogger(PoiService.class);

    @Autowired
    PoiDao poiDao;





    /**
     * 批量导入专家excel到数据库
     * @param file 专家文件（多个）
     * @return
     */
    
    public boolean importExcel(MultipartFile[] file){

        if(file == null){
            return false;
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
                    //封装每行sheet的数据
                    for(int a = 1 ; a <= totalRowNum ; a++){
                        //获得第a行对象
                        Row row = sheet.getRow(a);
                        map = new HashMap<>();
                        expert = new Expert();
                        if(StringUtil.isEmpty(row.getCell((short)1).getStringCellValue().toString())){
                            break;
                        }
                        //获得获得第a行第1列的对象,excel文档必须按照格式，根据最终的排版定义从第几个开始读取
                        expert.setName(row.getCell((short)1).getStringCellValue().toString());
                        expert.setSectors((row.getCell((short)2).getStringCellValue().toString()).split(","));  
                        expert.setUnit(row.getCell((short)5).getStringCellValue().toString());
                        expert.setEducation(row.getCell((short)6).getStringCellValue().toString());
                        expert.setJobTitle(row.getCell((short)7).getStringCellValue().toString());
                        expert.sethFactor((int)row.getCell((short)8).getNumericCellValue());
                        expert.setResearchDirection(row.getCell((short)9).getStringCellValue().toString());
                        expert.setResearchAchievement(row.getCell((short)10).getStringCellValue().toString());
                        expert.setResume(row.getCell((short)11).getStringCellValue().toString());
                        expert.setContact(row.getCell((short)13).getStringCellValue().toString());
                        //合作状态
                        expert.setCooperationStatus((int)row.getCell((short)15).getNumericCellValue());
                        expert.setCreatedBy(row.getCell((short)16).getStringCellValue());
                        expert.setRank((int)row.getCell((short)17).getNumericCellValue());
                        expert.setRowVersion((int)row.getCell((short)18).getNumericCellValue());
                        expert.setTags((row.getCell((short)19).getStringCellValue().toString()).split(","));
                        expert.setId(Misc.uuid());
                        /*expert.setTags("{}");*/
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
                    //重新规整后的sheet集合对象进行数据库保存操作
                 /*   for (Map<String, Object> result : resultList){
                        Object key[] = result.keySet().toArray();
                        Expert addExpert = (Expert)result.get(key[0]);
                       // addExpert.setId(Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(7,11)));
                        addExpert.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        poiDao.addPoiExpert(addExpert);
                    }*/
                     poiDao.batchAddPoiExpert(resultList);
                    
                }
        }
        }catch (Exception e){
        	e.printStackTrace();
            log.error("文件上传解析异常："+e);
            return false;
        }
        //每个文件都读取成功
        return true;
    }






}
