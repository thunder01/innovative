package com.innovative.controller;

import com.innovative.service.LoggerUserService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jacks on 2017/11/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LoggerUserService loggerUserService;

    /**
     * 分页查询日志信息
     * @param offset
     * @return
     */
    @RequestMapping(value = "/findLog" ,method= RequestMethod.GET)
    public JsonResult findLog(@RequestParam(name="offset",defaultValue="0" ) Integer offset){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        JsonResult result=loggerUserService.findLog(page);

        return result;
    }
}
