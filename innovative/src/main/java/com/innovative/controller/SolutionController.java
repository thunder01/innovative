package com.innovative.controller;


import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import com.innovative.bean.Solution;
import com.innovative.service.SolutionService;
import com.innovative.utils.CookiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/solution")
public class SolutionController {


    @Autowired
    private SolutionService solutionService;


    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public JsonResult getSolutionById(@PathVariable(name = "id", required = true) String id) {

        Solution solution = solutionService.getSolutionById(id);
        if (solution == null) {
            return new JsonResult(false, "无此方案信息");
        }

        return new JsonResult(true, solution);

    }
    
    /**
     * 根据删除解决方案
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/deleteSolution", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteSolution(@RequestBody Solution solution) {
    	String sectors = null;
    	if(solution.getSectors() != null &&solution.getSectors().length>0 )
    		 sectors = solution.getSectors()[0];
       boolean flag = solutionService.deleteSolution(solution.getId());
        if (flag) {
            return new JsonResult(true, solutionService.getSolutionListByCondition( 1,sectors));
        }
        return new JsonResult(false, "参数不合法");
    }


    /**
     * 分页条件查询方案list
     *
     * @param sectors 关键字
     * @param pageNum  页码
     * @return
     */
    @RequestMapping(value = "/getListByCondition", method = RequestMethod.GET)
    public JsonResult getSolutionByCondition(@RequestParam(name="offset",defaultValue="0") Integer offset,@RequestParam(name="sectors",required=false) String sectors) {

    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, solutionService.getSolutionListByCondition( page,sectors));
    }

    /**
     * 新增方案
     *
     * @param solution   方案bean    
     * @return
     */
    @RequestMapping(value = "/insertSolution", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertSolution(@RequestBody Solution solution,HttpServletRequest req) {

    	solution.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        //新增
        boolean result = solutionService.insertSolution(solution);

        if (!result) {
            return new JsonResult(false, "新增方案失败，请重试！");
        }

        return new JsonResult(true, "新增方案成功！");
    }

    /**
     * 修改方案信息
     *
     * @param solution    方案bean
     * @return
     */
    @RequestMapping(value = "updateSolution", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateSolution(@RequestBody Solution solution,HttpServletRequest req) {

        //判断有无此方案
        Solution t = solutionService.getSolutionById(solution.getId());
        if (t == null) {
            return new JsonResult(false, "无此方案，请重试！");
        }
        solution.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));


        //修改
        boolean result = solutionService.updateSolution(solution);

        if (!result) {
            return new JsonResult(false, "修改方案失败，请重试！");
        }
       Solution solu = solutionService.getSolutionById(solution.getId());
        return solu!=null ? new JsonResult(true,solu) : new JsonResult(false,"获取对象失败!");
    }

}
