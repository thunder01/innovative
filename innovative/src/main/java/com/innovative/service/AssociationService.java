package com.innovative.service;


import com.innovative.bean.Association;
import com.innovative.dao.AssociationDao;
import com.innovative.dao.FileDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssociationService {


    @Autowired
    AssociationDao associationDao;
    @Autowired
    CodeItemUtil codeItemUtil;
    @Autowired
    FileDao fileDao;

    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    public Association getAssociation(String id){

         
    	Association association	= associationDao.getAssociation(id);
	       
			if(association!=null){
				List<String> urllist = fileDao.getPhotoByMOdAndId(id, "indusinfoPhoto");
     		   if(urllist != null && urllist.size() > 0 )
     			 association.setLogo( urllist.get(0));
				List<Map<String,Object>> statuslist = codeItemUtil.getCodeItemList("EXPERT_COOPERSTATUS",association.getCooperationStatus());
				if(statuslist!=null&&statuslist.size()>0)
					association.setCooperationStatusMap(statuslist.get(0));
			}
			return association;
    }



    /**
     * 行业协会列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getAssociationList(Integer pageNum){

       /* if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }*/
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Association> associations = associationDao.getAssociationList( pageInfo.getStartIndex(), pageInfo.getPageSize());
        for(Association ass : associations){
        	if(ass.getId()==null || "".equals(ass.getId()))
        		continue;
        	List<String> urllist = fileDao.getPhotoByMOdAndId(ass.getId(), "indusinfoPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			 ass.setLogo( urllist.get(0));
        }
        int totalCount = associationDao.getTotalCount();

        Map<String, Object> map = new HashMap<>();
        map.put("items", associations);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }



    /**
     * 添加行业协会
     * @param association 新增参数
     * @return
     */
    @Transactional
    public boolean addAssociation(Association association) {

        //增加成功
    	  associationDao.addAssociation(association);
    		  
		 return fileDao.updateFile(association.getId());
    }




    /**
     * 修改行业协会
     * @param association 修改参数
     * @return
     */
    public boolean updateAssociation(Association association) {
    	 fileDao.updateFile(association.getId());
        return associationDao.updateAssociation(association) > 0 ;
    }


    /**
     * 根据id删除行业协会
     * @param id
     * @return
     */
	public boolean deleteAssociation(String id) {
		// TODO Auto-generated method stub
		if(null == id || "".equals(id)){
			return false;
		}
		return associationDao.deleteAssociation(id);
	}







}
