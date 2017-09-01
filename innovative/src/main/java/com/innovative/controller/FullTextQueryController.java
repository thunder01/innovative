package com.innovative.controller;

import com.innovative.service.FullTextQueryService;
import com.innovative.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FullTextQueryController {

    @Autowired
    private FullTextQueryService fullTextQueryService;

    @RequestMapping(value = "/getResults", method = RequestMethod.GET)
    public JsonResult getFullTextQueryResults(@RequestParam(name = "keyWords", required = false) String keyWords) {

        Map<String, Object> fullTextQueryResults = fullTextQueryService.getFullTextQueryResults(keyWords);

        return new JsonResult(true, fullTextQueryResults);
    }

}
