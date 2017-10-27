package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.Solution;
import com.innovative.dao.FileDao;
import com.innovative.dao.SolutionDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;
    @Autowired
    FileDao fileDao;


    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    public Solution getSolutionById(String id) {

    	Solution solution = solutionDao.getSolutionById(id);
		if(solution!=null){
			//文件图片我们都改到一张专门的表来存储
		   List<String> urllist = fileDao.getPhotoByMOdAndId(id, "programPhoto");
		   if(urllist != null && urllist.size() > 0 )
			   solution.setPictures(urllist.get(0));
		}
		return solution;

    }

    /**
     * 分页条件查询方案list
     *
     * @param pageNum  页码
     * @param sectors 
     * @return
     */
    public Map<String, Object> getSolutionListByCondition(int pageNum, String sectors) {


    	  if (!StringUtils.isEmpty(sectors)) {
              sectors = "{" + sectors + "}";
          }

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Solution> items = solutionDao.getSolutionListByCondition( offset, limit,sectors);
        for(Solution so : items){
        	if(null == so|| "".equals(so.getId()))
        		continue;
        	 List<String> urllist = fileDao.getPhotoByMOdAndId(so.getId(), "programPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			   so.setPictures(urllist.get(0));
        }
        int totalCount = solutionDao.getCountByCondition(sectors);

        //数据组装
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());

        return map;

    }

    /**
     * 新增技术报告
     *
     * @param solution 参数集合
     * @return
     */
    @Transactional
    public boolean insertSolution(Solution solution) {

        solutionDao.insertSolution(solution);

        //增加成功
    		  
		 return fileDao.updateFile(solution.getId());
    }

    /**
     * 修改技术报告信息
     *
     * @param solution 参数集合
     * @return
     */
    public boolean updateSolution(Solution solution) {
    	 //删除之前的文件
        fileDao.deleteFiles(solution.getId(),"programFile");
        //新增新的文件
    	fileDao.updateFile(solution.getId());
        int result = solutionDao.updateSolution(solution);

        return result > 0 ;
    }

	public boolean deleteSolution(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);
		return solutionDao.deleteSolution(id);
	}
}
