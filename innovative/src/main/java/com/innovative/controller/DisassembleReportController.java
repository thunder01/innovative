package com.innovative.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.innovative.bean.Order;
import com.innovative.service.DisassembleReportService;
import com.innovative.utils.HttpClientUpload;
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
	
	/*跳转上传页面*/
	@RequestMapping(value="/toUpload/{orderid}",method = RequestMethod.GET)
	public JsonResult toUpload(@PathVariable(name="orderid") Integer orderid){
		Map<String,Object> map=new HashMap<>();
		map.put("orderid", orderid);
		return new JsonResult(true,map);
	}
	/**
	 * 拆解报告上传之后，将上传记录添加到数据库
	 * @param FileData
	 * @param reportid 订单id
	 * @return
	 * */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonResult saveDisassembleReport(@RequestBody DisassembleReport report){
		System.out.println(report);
		
		/*if (FileData != null && FileData.length > 0) {
			使用httpclient上传到远程文件服务器
			String path=HttpClientUpload.httpClientUploadFile(FileData,"disassemble");
			report.setFile(path);//添加上传记录中的文件路径
		}*/		
		System.out.println(report);
		Map<String, Object> map= disassembleService.saveDisassembleReport(report,report.getOrderid());
        if (map!=null) {
            return new JsonResult(true, map);
        }
        return new JsonResult(false, "报告上传失败！");
        
	}

	/**
	 * 删除拆解报告
	 * @param 拆解报告id
	 * @return
	 * */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public JsonResult deleteDisassembleReportById(@RequestBody DisassembleReport report){
		Map<String,Object> map=disassembleService.deleteDisassembleReportById(report.getId(),report.getOrderid());
		/*判断是否删除成功*/
			return new JsonResult(true, map);
		
	}
	
	/**
	 * 根据id查找拆解报告，编辑信息时先查
	 * @param disid 拆解报告id
	 * @param orderid
	 * @return
	 * */
	@RequestMapping(value="/select/{disid}/{orderid}", method=RequestMethod.GET)
	public JsonResult a(@PathVariable(name="disid") Integer disid,@PathVariable(name="orderid") Integer orderid){
		Map<String,Object> map=disassembleService.getDisassembleReportById(disid);
		/*判断结果是否存在*/
		if (map!=null) {
			return new JsonResult(true, map);
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
