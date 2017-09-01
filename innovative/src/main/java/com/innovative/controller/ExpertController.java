package com.innovative.controller;

import com.innovative.bean.Expert;
import com.innovative.service.ExpertService;
import com.innovative.utils.BaseController;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


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
     * 添加专家信息
     * @param name              专家名称
     * @param unit              所在单位
     * @param education         最高学历
     * @param jobTitle          职称
     * @param hFactor           H因子
     * @param researchDirection 研发方向
     * @param researchAchievement    研发成果
     * @param resume           个人简历
     * @param avatar           头像
     * @param contact           联系方式
     * @param cooperationStatus   合作状态
     * @param sectors          部门，领域
     * @param tags                标签
     * @param rank              等级
     * @param isActive          是否在线
     * @param id                    id
     * @param file                  文件
     * @return
     */
    @RequestMapping(value = "/addExpert", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addExpert(@RequestBody Expert expert) {

       /* //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", expert.getName());
        params.put("unit", expert.getUnit());
        params.put("education", expert.getEducation());
        params.put("jobTitle", expert.getJobTitle());
        params.put("hFactor", expert.gethFactor());
        params.put("researchDirection", expert.getResearchDirection());
        params.put("researchAchievement", expert.getResearchAchievement());
        params.put("resume", expert.getName());
        params.put("contact", expert.getContact());
        params.put("cooperationStatus", expert.getCooperationStatus());
        params.put("sectors",   expert.getSectors());//需要处理
        params.put("tags",  expert.getTags() );//需要处理
        params.put("rank", expert.getRank());
        params.put("isActive", expert.isActive());
        params.put("id", expert.getId());
        params.put("createdBy", "");
        params.put("deleted", false);
        params.put("deletedBy", "");
        params.put("rowVersion", 0);
        params.put("updatedBy", "");
        params.put("createdAt", new Timestamp(System.currentTimeMillis()));*/


        //获取上传文件和图片后返回的url地址
       /* params.put("avatar", "{" + FileUpload.getUrls(avatar, "Experts") + "}");
        params.put("file", "{" + FileUpload.getUrls(file, "Experts") + "}");*/

        if (!expertService.addForExpert(expert)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }






    /**
     * 根据专家id修改专家信息
     *
     * @param name              专家名称
     * @param unit              所在单位
     * @param education         最高学历
     * @param jobTitle          职称
     * @param hFactor           H因子
     * @param researchDirection 研发方向
     * @param researchAchievement    研发成果
     * @param resume           个人简历
     * @param contact           联系方式
     * @param cooperationStatus   合作状态
     * @param sectors          部门，领域
     * @param tags                标签
     * @param rank              等级
     * @param isActive          是否在线
     * @param id                    id
     * @param updateId          修改后的id
     * @return
     */
    @RequestMapping(value = "/updateExpert", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateExpert(@RequestBody Expert expert) {


        if(expertService.getExpert(expert.getId()) == null){
            return new JsonResult(false, "此记录不存在");
        }

       /* //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();

        params.put("name", name);
        params.put("unit", unit);
        params.put("education", education);
        params.put("jobTitle", jobTitle);
        params.put("hFactor", hFactor);
        params.put("researchDirection", researchDirection);
        params.put("researchAchievement", researchAchievement);
        params.put("resume", resume);
        params.put("contact", contact);
        params.put("cooperationStatus", cooperationStatus);
        if(!StringUtils.isEmpty(sectors)){
            params.put("sectors", "{" + sectors + "}");//需要处理
        }
        if(!StringUtils.isEmpty(tags)){
            params.put("tags", "{" + tags + "}");//需要处理
        }
        params.put("rank", rank);
        params.put("isActive", isActive);
        params.put("id", id);
        params.put("updateId", updateId);
        params.put("updatedBy", "");
        params.put("updateTime", new Timestamp(System.currentTimeMillis()));*/

        if (!expertService.updateExpert(expert)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        return new JsonResult(true, "修改成功！");
    }








}
