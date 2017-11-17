package com.innovative.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.innovative.bean.Tages;
import com.innovative.service.FullTextQueryService;
import com.innovative.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Component
public class ExampleTimer {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private FullTextQueryService fullTextQueryService;
    @Scheduled(cron="0 0 2 * * ?")
    @Transactional
    public void timerRate() {
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
        String jsonObject = JSONObject.toJSONString(map);
        statisticsService.addStatis(jsonObject);
    }
    public List<Tages> group(){
        List<Object> list=new LinkedList<>();
        List<Object> lists=new LinkedList<>();
        List<Tages> selectAssociations=statisticsService.selectAssociations();
        for (int i=0;i<selectAssociations.size();i++){
            if (selectAssociations.get(i).getTags()!=null&&!selectAssociations.get(i).getTags().equals("{}")){
                String []a=selectAssociations.get(i).getTags();
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
        List<Tages> number = new LinkedList<>();
        for ( int i=0;i<lists.size();i++){
            Map<String, Object> fullTextQueryResults = fullTextQueryService.getFullTextQueryResults(lists.get(i).toString());
            number.add(new Tages(lists.get(i).toString(),(Integer) fullTextQueryResults.get("totalCount")));
        }
        number.sort(Comparator.comparingInt(Tages::getNumber).reversed());
        return number;
    }
}
