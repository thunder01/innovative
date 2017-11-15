package com.innovative.controller;

import com.innovative.bean.*;
import com.innovative.service.FullTextQueryService;
import com.innovative.service.StatisticsService;
import com.innovative.utils.DateUtil;
import com.innovative.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
         Map<String,Object> map=new HashMap<>();
         Map<String,Object> data=new LinkedHashMap<>();
         List list=new LinkedList();
         List number=new LinkedList();
         Map<String,Object> rdata=new LinkedHashMap<>();
         List rlist=new LinkedList();
         List rnumber=new LinkedList();
         Map<String,Object> xudata=new LinkedHashMap<>();
         List xulist=new LinkedList();
         List xunumber=new LinkedList();
         Map<String,Object> oderRdata=new LinkedHashMap<>();
         List oderRlist=new LinkedList();
         List oderRnumber=new LinkedList();
         Map<String,Object> infordata=new LinkedHashMap<>();
         List inforlist=new LinkedList();
         List infornumber=new LinkedList();
         Map<String,Object> inforNdata=new LinkedHashMap<>();
         List inforNnumber=new LinkedList();
         Map<String,Object> activedata=new LinkedHashMap<>();
         List activeNnumber=new LinkedList();
         Map<String,Object> top=new LinkedHashMap<>();
         List topList=new LinkedList();
         List topNnumber=new LinkedList();
         list.add("需求数");
         list.add("方案数");
         list.add("情报数");
         list.add("资源数");
         number.add(statisticsService.queryDemand());
         number.add(statisticsService.queryReport());
         number.add(statisticsService.queryIntelligence());
         number.add(statisticsService.queryAssociations()+statisticsService.queryEquipments()+statisticsService.queryExperts()+statisticsService.queryOrganizations()+statisticsService.querySolutions()+statisticsService.queryTechnicalReports());
         data.put("name",list);
         data.put("number",number);
         map.put("title",data);
         rlist.add("专家");
         rlist.add("合作机构");
         rlist.add("行业协会");
         rlist.add("技术报告");
         rlist.add("方案");
         rlist.add("仪器设备");
         rnumber.add(statisticsService.queryExperts());
         rnumber.add(statisticsService.queryOrganizations());
         rnumber.add(statisticsService.queryAssociations());
         rnumber.add(statisticsService.queryTechnicalReports());
         rnumber.add(statisticsService.querySolutions());
         rnumber.add(statisticsService.queryEquipments());
         rdata.put("name",rlist);
         rdata.put("value",rnumber);
         map.put("resourceN",rdata);
         xulist.add(DateUtil.getMouth(0));
         xulist.add(DateUtil.getMouth(-1));
         xulist.add(DateUtil.getMouth(-2));
         xulist.add(DateUtil.getMouth(-3));
         xulist.add(DateUtil.getMouth(-4));
         xulist.add(DateUtil.getMouth(-5));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(0)));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(-1)));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(-2)));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(-3)));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(-4)));
         xunumber.add(statisticsService.queryDemandNumber(DateUtil.getMouth(-5)));
         xudata.put("date",xulist);
         xudata.put("value",xunumber);
         map.put("orderN",xudata);
         oderRlist.add("已完成");
         oderRlist.add("寻源中");
         oderRlist.add("审批中");
         oderRlist.add("已下单");
         oderRnumber.add(statisticsService.queryOderV2());
         oderRnumber.add(statisticsService.queryProject());
         oderRnumber.add(statisticsService.queryCheckDemand());
         oderRnumber.add(statisticsService.queryDemand());
         oderRdata.put("name",oderRlist);
         oderRdata.put("value",oderRnumber);
         oderRdata.put("total",statisticsService.queryDemand());
         map.put("orderR",oderRdata);
         inforlist.add("已完成");
         inforlist.add("寻源中");
         inforlist.add("审批中");
         inforlist.add("已下单");
         infornumber.add(statisticsService.queryInforScore());
         infornumber.add(statisticsService.queryIntellxun());
         infornumber.add(statisticsService.queryIntellsp());
         infornumber.add(statisticsService.queryIntell());
         infordata.put("name",inforlist);
         infordata.put("value",infornumber);
         infordata.put("total",statisticsService.queryIntelligence());
         map.put("informationR",infordata);
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(0)));
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(-1)));
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(-2)));
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(-3)));
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(-4)));
         inforNnumber.add(statisticsService.queryIntellNumber(DateUtil.getMouth(-5)));
         inforNdata.put("date",xulist);
         inforNdata.put("value",inforNnumber);
         map.put("informationN",inforNdata);
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(0)));
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(-1)));
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(-2)));
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(-3)));
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(-4)));
         activeNnumber.add(statisticsService.queryGetUserNumber(DateUtil.getMouth(-5)));
         activedata.put("date",xulist);
         activedata.put("value",activeNnumber);
         map.put("activeUserN",activedata);
         List<Tages> tages=group();
         topList.add(tages.get(0).getName());
         topList.add(tages.get(1).getName());
         topList.add(tages.get(2).getName());
         topList.add(tages.get(3).getName());
         topList.add(tages.get(4).getName());
         topList.add(tages.get(5).getName());
         topList.add(tages.get(6).getName());
         topList.add(tages.get(7).getName());
         topList.add(tages.get(8).getName());
         topList.add(tages.get(9).getName());
         topNnumber.add(tages.get(0).getNumber());
         topNnumber.add(tages.get(1).getNumber());
         topNnumber.add(tages.get(2).getNumber());
         topNnumber.add(tages.get(3).getNumber());
         topNnumber.add(tages.get(4).getNumber());
         topNnumber.add(tages.get(5).getNumber());
         topNnumber.add(tages.get(6).getNumber());
         topNnumber.add(tages.get(7).getNumber());
         topNnumber.add(tages.get(8).getNumber());
         topNnumber.add(tages.get(9).getNumber());
         top.put("name",topList);
         top.put("value",topNnumber);
         map.put("top10",top);
         return  new JsonResult(true,map);
     }
     public List<Tages> group(){
         List<Object> list=new LinkedList<>();
         List<Object> lists=new LinkedList<>();
         List<Association> selectAssociations=statisticsService.selectAssociations();
         for (int i=0;i<selectAssociations.size();i++){
             if (selectAssociations.get(i).getTags()!=null&&!selectAssociations.get(i).getTags().equals("{}")){
                 String []a=selectAssociations.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         List<Expert> selectExperts=statisticsService.selectExperts();
         for (int i=0;i<selectExperts.size();i++){
             if (selectExperts.get(i).getTags()!=null&&!selectExperts.get(i).getTags().equals("{}")){
                 String []a=selectExperts.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         List<Organization> selectOrganizations=statisticsService.selectOrganizations();
         for (int i=0;i<selectOrganizations.size();i++){
             if (selectOrganizations.get(i).getTags()!=null&&!selectOrganizations.get(i).getTags().equals("{}")){
                 String []a=selectOrganizations.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         List<TechnicalReport> selectTechnicalReports=statisticsService.selectTechnicalReports();
         for (int i=0;i<selectTechnicalReports.size();i++){
             if (selectTechnicalReports.get(i).getTags()!=null&&!selectTechnicalReports.get(i).getTags().equals("{}")){
                 String []a=selectTechnicalReports.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         List<Solution> selectSolutions=statisticsService.selectSolutions();
         for (int i=0;i<selectSolutions.size();i++){
             if (selectSolutions.get(i).getTags()!=null&&!selectSolutions.get(i).getTags().equals("{}")){
                 String []a=selectSolutions.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         List<Equipment> selectEquipments=statisticsService.selectEquipments();
         for (int i=0;i<selectEquipments.size();i++){
             if (selectEquipments.get(i).getTags()!=null&&!selectEquipments.get(i).getTags().equals("{}")){
                 String []a=selectEquipments.get(i).getTags();
                 for (int aarry=0;aarry<a.length;aarry++){
                     list.add(a[aarry]);
                 }
             }
         }
         //去除重复值
         Map<Object,String> map=new HashMap();
        for (int i=0;i<list.size();i++){
            map.put(list.get(i),"");
        }
        for (Map.Entry<Object,String> entry:map.entrySet()){
            if (!entry.getKey().equals("")){
                lists.add(entry.getKey());
            }
        }
         System.out.println(lists);
         List<Tages> number = new LinkedList<>();
        for ( int i=0;i<lists.size();i++){
            Map<String, Object> fullTextQueryResults = fullTextQueryService.getFullTextQueryResults(lists.get(i).toString(),1);
            number.add(new Tages(lists.get(i).toString(),(Integer) fullTextQueryResults.get("totalCount")));
        }
         number.sort(Comparator.comparingInt(Tages::getNumber).reversed());
         return number;
     }

//    public static void main(String[] args) {
//        List<Tages> numbers = new LinkedList<>();
//        numbers.add(new Tages("小明",1));
//        numbers.add(new Tages("小张",3));
//        numbers.add(new Tages("小王",6));
//        numbers.add(new Tages("小红",10));
//        numbers.add(new Tages("小李",4));
//        numbers.sort(Comparator.comparingInt(Tages::getNumber).reversed());
//    }
}
