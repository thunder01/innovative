package com.innovative.controller;

import com.innovative.service.FullTextQueryService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
public class FullTextQueryController {

    @Autowired
    private FullTextQueryService fullTextQueryService;

    @RequestMapping(value = "/getResults", method = RequestMethod.GET)
    public JsonResult getFullTextQueryResults(@RequestParam(name = "keyWords", required = false) String keyWords,
    										@RequestParam(name="offset",defaultValue="0" ) Integer offset) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
    	/*try {
			keyWords = new String(keyWords.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new JsonResult(false, "转码异常!");
		}*/
        Map<String, Object> fullTextQueryResults = fullTextQueryService.getFullTextQueryResults(keyWords,page);

        return new JsonResult(true, fullTextQueryResults);
    }

}
