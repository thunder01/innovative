package com.innovative.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innovative.bean.DisassembleReport;
import com.innovative.service.DisassembleReportService;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;

/**
 * 拆解报告的上传web层
 * @author fzy
 * @version 1.0
 * */
@RestController
@RequestMapping("/disassemble")
public class DisassembleReportController {
	@Autowired
    private DisassembleReportService disassembleService;
	
	/**
	 * 拆解报告上传之后，将上传记录添加到数据库
	 * @param FileDate
	 * @param DisassembleReport
	 * @return
	 * */
	@RequestMapping(value = "/upload/{orderid}", method = RequestMethod.POST)
	public JsonResult saveDisassembleReport(@RequestParam(name = "FileData", required = true) MultipartFile[] FileData,
			DisassembleReport report,@PathVariable(name="orderid") Integer orderid){
		
		//用于存储上传后拆解报告的地址
        StringBuffer buffer=new StringBuffer();
		//上传拆解报告的操作
        if (FileData != null && FileData.length > 0) {
            try {
                String url = null;
                for (int i = 0; i < FileData.length; i++) {

                    url = FileUpload.copyFile(FileData[i], "disassemble");
                    
                    if(i==FileData.length-1){
                    	buffer.append(url);
                    }else{
                    	buffer.append(url+",");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //向数据库插入上传信息
        report.setFile(buffer.toString());//添加上传记录中的文件路径
        int result = disassembleService.saveDisassembleReport(report,orderid);
        if (result==0) {
            return new JsonResult(false, "报告上传失败！");
        }
        return new JsonResult(true, "报告上传成功！");
        
	}
	
	/**
	 * 删除拆解报告
	 * @param id
	 * @return
	 * */
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public JsonResult deleteDisassembleReportById(@PathVariable(name="id") Integer id){
		int flag=disassembleService.deleteDisassembleReportById(id);
		/*判断是否删除成功*/
		if (flag>0) {
			return new JsonResult(true, "删除成功");
		}else {
			return new JsonResult(false, "删除失败");
		}
		
	}
	
	/**
	 * 根据id查找拆解报告
	 * @param id
	 * @return
	 * */
	@RequestMapping(value="/select/{id}", method=RequestMethod.GET)
	public JsonResult a(@PathVariable(name="id") Integer id){
		DisassembleReport report=disassembleService.getDisassembleReportById(id);
		/*判断结果是否存在*/
		if (report!=null) {
			return new JsonResult(true, report);
		}else {
			return new JsonResult(false, "没有结果");
		}
		
	}
	
	/**
	 * 修改拆解报告
	 * @param report
	 * @return
	 * */
	@RequestMapping(value="/edit" ,method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateDisassembleReport(@RequestBody DisassembleReport report) {
		int flag=disassembleService.updateDisassembleReport(report);
		/*判断是否修改成功*/
		if (flag>0) {
			return new JsonResult(true, "修改成功");
		}else {
			return new JsonResult(false, "修改失败");
		}	
	}
}
