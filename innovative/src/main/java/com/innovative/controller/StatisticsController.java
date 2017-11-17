package com.innovative.controller;

import com.alibaba.fastjson.JSONObject;
import com.innovative.bean.*;
import com.innovative.service.FullTextQueryService;
import com.innovative.service.StatisticsService;
import com.innovative.utils.DateUtil;
import com.innovative.utils.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/statis")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private FullTextQueryService fullTextQueryService;
/**
 * 查询数据
 */
@RequestMapping(value = "/statistic", method = RequestMethod.GET)
     public JsonResult getStatis(){
         List<Static> statics= statisticsService.timeStatic(DateUtil.getBeforeDayDate("1"));
         return  new JsonResult(true, JSONObject.parse(statics.get(0).getStatics()));
     }
    @RequestMapping(value = "/xdateStatis",method=RequestMethod.GET)
    public  JsonResult  xdateStatis(@RequestParam(name = "startDate") String startDate,@RequestParam(name = "endDate") String endDate){
           Map<String,Object> map=new HashMap<>();
           String []start=startDate.split("-");
           String []end=endDate.split("-");
           int data=(Integer.parseInt(end[0])-Integer.parseInt(start[0]))*12+(Integer.parseInt(end[1])-Integer.parseInt(start[1]));
           Map<String,Object> xudata=new LinkedHashMap<>();
           List xulist=new LinkedList();
           List xunumber=new LinkedList();
           xulist.add(startDate);
           xunumber.add(statisticsService.queryDemandNumber(startDate));
           for (int i=1;i<data;i++){
               String []start2=startDate.split("-");
               String []end2=endDate.split("-");
               int year=0;
               int yue=0;
               if (Integer.parseInt(start2[1])+1>12){
                    year=Integer.parseInt(start2[0])+1;
                    yue=0+1;
               }else{
                   year=Integer.parseInt(start2[0]);
                   yue=Integer.parseInt(start2[1])+1;
               }
               startDate=year+"-"+getNumber(yue);
               xulist.add(startDate);
               xunumber.add(statisticsService.queryDemandNumber(startDate));
           }
           xulist.add(endDate);
           xunumber.add(statisticsService.queryDemandNumber(endDate));
           xudata.put("date",xulist);
           xudata.put("value",xunumber);
           map.put("orderN",xudata);
           return  new JsonResult(true,map);
     }
    @RequestMapping(value = "/qdateStatis",method=RequestMethod.GET)
    public  JsonResult  qdateStatis(@RequestParam(name = "startDate") String startDate,@RequestParam(name = "endDate") String endDate){
        Map<String,Object> map=new HashMap<>();
        String []start=startDate.split("-");
        String []end=endDate.split("-");
        int data=(Integer.parseInt(end[0])-Integer.parseInt(start[0]))*12+(Integer.parseInt(end[1])-Integer.parseInt(start[1]));
        Map<String,Object> xudata=new LinkedHashMap<>();
        List xulist=new LinkedList();
        List xunumber=new LinkedList();
        xulist.add(startDate);
        xunumber.add(statisticsService.queryIntellNumber(startDate));
        for (int i=1;i<data;i++){
            String []start2=startDate.split("-");
            String []end2=endDate.split("-");
            int year=0;
            int yue=0;
            if (Integer.parseInt(start2[1])+1>12){
                year=Integer.parseInt(start2[0])+1;
                yue=0+1;
            }else{
                year=Integer.parseInt(start2[0]);
                yue=Integer.parseInt(start2[1])+1;
            }
            startDate=year+"-"+getNumber(yue);
            xulist.add(startDate);
            xunumber.add(statisticsService.queryIntellNumber(startDate));
        }
        xulist.add(endDate);
        xunumber.add(statisticsService.queryIntellNumber(endDate));
        xudata.put("date",xulist);
        xudata.put("value",xunumber);
        map.put("orderN",xudata);
        return  new JsonResult(true,map);
    }
    @RequestMapping(value = "/yhdateStatis",method=RequestMethod.GET)
    public  JsonResult  yhdateStatis(@RequestParam(name = "startDate") String startDate,@RequestParam(name = "endDate") String endDate){
        Map<String,Object> map=new HashMap<>();
        String []start=startDate.split("-");
        String []end=endDate.split("-");
        int data=(Integer.parseInt(end[0])-Integer.parseInt(start[0]))*12+(Integer.parseInt(end[1])-Integer.parseInt(start[1]));
        Map<String,Object> xudata=new LinkedHashMap<>();
        List xulist=new LinkedList();
        List xunumber=new LinkedList();
        xulist.add(startDate);
        xunumber.add(statisticsService.queryGetUserNumber(startDate));
        for (int i=1;i<data;i++){
            String []start2=startDate.split("-");
            String []end2=endDate.split("-");
            int year=0;
            int yue=0;
            if (Integer.parseInt(start2[1])+1>12){
                year=Integer.parseInt(start2[0])+1;
                yue=0+1;
            }else{
                year=Integer.parseInt(start2[0]);
                yue=Integer.parseInt(start2[1])+1;
            }
            startDate=year+"-"+getNumber(yue);
            xulist.add(startDate);
            xunumber.add(statisticsService.queryGetUserNumber(startDate));
        }
        xulist.add(endDate);
        xunumber.add(statisticsService.queryGetUserNumber(endDate));
        xudata.put("date",xulist);
        xudata.put("value",xunumber);
        map.put("orderN",xudata);
        return  new JsonResult(true,map);
    }
    /**
     * 月份补0策略
     */
    public  String getNumber (int number){
         String numbers="";
         if (number<10){
             numbers="0"+number;
         }else {
             numbers=number+"";
         }
         return  numbers;
    }
}
