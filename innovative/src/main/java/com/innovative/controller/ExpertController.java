package com.innovative.controller;

import com.innovative.bean.Expert;
import com.innovative.service.ExpertService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/expert")
public class ExpertController extends BaseController {




    @Autowired
    ExpertService expertService;
  
    /**
     * 根据id获取专家详情
     *
     * @param id 专家id
     * @return
     */
    @RequestMapping(value = "/getExpert/{id}", method = RequestMethod.GET)
    public JsonResult getExpert(@PathVariable(name = "id") Integer id) {
    	
        Expert expert = expertService.getExpert(id);
        if (expert != null) {
            return new JsonResult(true, expert);
        }
        return new JsonResult(false, "参数不合法");
    }




    /**
     * 专家列表页
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getExpertList/{sectors}/{pageNum}", method = RequestMethod.GET)
    public JsonResult getExpertList(@PathVariable(name = "sectors", required = false) String sectors,
    								@PathVariable(name = "pageNum") Integer pageNum) {

        if (pageNum <= 0) {
            return new JsonResult(false, "参数不合法！");
        }
        return new JsonResult(true, expertService.getExpertList(sectors, pageNum));
    }



/**
 * 新增专家
 * @param expert 专家对象封装
 * @return
 */
    @RequestMapping(value = "/addExpert", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addExpert(@RequestBody Expert expert,HttpServletRequest req) {

    	expert.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!expertService.addForExpert(expert)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }






  /**
   * 根据专家id 修改专家
   * @param expert 专家对象
   * @param req
   * @return
   */
    @RequestMapping(value = "/updateExpert", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateExpert(@RequestBody Expert expert,HttpServletRequest req) {

        if(expertService.getExpert(expert.getId()) == null){
            return new JsonResult(false, "此记录不存在");
        }
        expert.setUpdatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!expertService.updateExpert(expert)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        return new JsonResult(true, "修改成功！");
    }








}
