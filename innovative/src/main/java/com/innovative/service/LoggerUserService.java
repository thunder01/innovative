package com.innovative.service;

import com.innovative.bean.LoggerUser;
import com.innovative.bean.User;
import com.innovative.dao.LoggerUserDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户行为日志
 * Created by jacks on 2017/11/17.
 */
@Service
public class LoggerUserService {
    @Autowired
    private LoggerUserDao loggerUserDao;
    @Autowired
    private UserDao userDao;

    public JsonResult findLog(Integer pageNum){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<LoggerUser> list=loggerUserDao.findLog(pageInfo.getPageSize(),pageInfo.getStartIndex());
        String result= JSONArray.fromObject(list).toString();
        System.out.println(result);

        for (LoggerUser log:list){
            String userid=log.getUserid();
            User user=userDao.getUserroleMessessage(userid);
            if (user.getRoleName()!=null&&""!=user.getRoleName()){
                log.setRolename(user.getRoleName());
            }else {
                log.setRolename("无");
            }
        }

        int totalCount=loggerUserDao.totalcount();
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());

        boolean flag=list.size()>0;
        return  new JsonResult(flag,map);
    }

}
