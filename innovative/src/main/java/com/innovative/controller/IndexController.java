package com.innovative.controller;


import com.innovative.service.IndexService;
import com.innovative.service.IntegralService;
import com.innovative.utils.JsonResult;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private IndexService indexService;
    @Autowired
    private IntegralService integralService;

    /**
     * 获取图片列表，根据时间排序，取3张
     *
     * @return
     */
    @RequestMapping(value = "/getIndex/{id}", method = RequestMethod.GET)
    public JsonResult getCarouselList(@PathVariable(name = "id") String id) {
    	Map<String, Object> map = indexService.getAllData(id);
    	if(map.size()>0){
    		integralService.managerIntegral(1, id, null);
    	}
        return new JsonResult(true, map);

    }


}
