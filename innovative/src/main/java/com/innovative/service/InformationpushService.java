package com.innovative.service;

import com.innovative.bean.Approuver;
import com.innovative.bean.CollectionPush;
import com.innovative.bean.FileBean;
import com.innovative.bean.InformationPushPartager;
import com.innovative.bean.Informationpush;
import com.innovative.dao.ApprouverDao;
import com.innovative.dao.CollectionDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.InformationpushDao;
import com.innovative.dao.InformationpushcomenterDao;
import com.innovative.utils.Config;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InformationpushService {

    @Autowired
    InformationpushDao informationpushDao;
    @Autowired
    InformationpushcomenterDao informationpushcomenterDao;
    @Autowired
    ApprouverDao approuverDao;//主要用于点赞
    @Autowired
    CollectionDao collectionDao;//主要用于信息推特收藏
    @Autowired
    FileDao fileDao;
    @Autowired
    MessageService messageService;
  
    

    /**
     * 根据id获取信息推送信息
     * @param id 推特信息id
     * @param userid 当前登录人
     * @return
     */
    public Informationpush getInformationpush(String id, String userid){
    	
    	Informationpush informationpush =	informationpushDao.getInformationpush(id,userid);
        		if(informationpush!=null){
        		   List<FileBean> fileList = fileDao.getFileById(id, "informationpushFile");
        		   if(fileList != null && fileList.size() > 0 )
        			   informationpush.setFilelist(fileList);
        		}
        		return informationpush;
    }
    



    /**
     * 获取信息推特信息列表
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getInformationpushList( Integer pageNum){

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);

        List<Informationpush> informationpushs = informationpushDao.getInformationpushList(pageInfo.getStartIndex(), pageInfo.getPageSize());
        for(Informationpush e: informationpushs){
        	if(e==null || "".equals(e.getId()))
        		continue;
        	
        	
        	//处理上传的文件
        	List<FileBean> fileList = fileDao.getFileById(e.getId(), Config.INFORMATIONPUSHFILE);
 		   if(fileList != null && fileList.size() > 0 )
 			   e.setFilelist(fileList);
        }
        int totalCount = informationpushDao.getTotalCount();

        Map<String, Object> map = new HashMap<>();
        
        
        map.put("items", informationpushs);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }



    /**
     * 添加专家
     * @param params 新增参数
     * @return
     */
    public boolean addInformationpush(Map<String, Object> params) {
    	
    	
        return (informationpushDao.addInformationpush(params) > 0);
    }




    /**
     * 修改信息推特
     * @param expert 修改参数
     * @return
     */
    @Transactional
    public boolean updateInformationpush(Informationpush informationpush) {
    	fileDao.updateFile(informationpush.getId());
        boolean flag =	(informationpushDao.updateInformationpush(informationpush) > 0) ;
         return flag ;
    }


/**
 * 增加一条推特信息
 * @param informationpush
 * @return
 */
    @Transactional
	public boolean addInformationpush(Informationpush informationpush) {
    	int num = informationpushDao.addInformationpush(informationpush);
    	//看这条是评论还是发布信息
    	fileDao.updateFile(informationpush.getId());
    	
		 return num>0 ? true  : false;
	}




	/*public Map<String, Object> getInformationpushLists(Integer page) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Informationpush> informationpushs = informationpushDao.getInformationpushLists( pageInfo.getStartIndex(), pageInfo.getPageSize());
	       int totalCount = informationpushDao.getTotalCount();
		
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("items", informationpushs);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
		 return map;
	}*/




	public boolean deleteInformationpush(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);
		//删除评论
		informationpushcomenterDao.deleteInformationpushcoment(id);
		return informationpushDao.deleteInformationpush(id);
	}



//获取剩余评论
	public  List<Informationpush> getInformationpushListSurplus(Integer offset) {
		 List<Informationpush> informationpushs = informationpushDao.getInformationpushSurplus(offset);
		 for(Informationpush e: informationpushs){
	        	if(e==null || "".equals(e.getId()))
	        		continue;
	        	
	        	
	        	//处理上传的文件
	        	List<FileBean> fileList = fileDao.getFileById(e.getId(), "informationpushFile");
	 		   if(fileList != null && fileList.size() > 0 )
	 			   e.setFilelist(fileList);
	        }
		return informationpushs;
	}



/**
 * 点赞次数加1
 * @param approuver
 * @return
 */
	@Transactional
	public boolean addApprouver(Approuver approuver) {
		//获取当前点赞人最近一次点赞时间
		/*Approuver ts = approuverDao.getApprouverLatest(approuver.getApprouverBy(),1);
		boolean  flag = false;
		//判断今天有没有点赞
		
		 if (ts!=null && ts.getApprouverAt() != null){
			 flag = Misc.isNowDay(new Date(ts.getApprouverAt().getTime()));
		}*/
		Integer num = approuverDao.isTodayApprouverInfornaionPush(approuver.getApprouverBy(),approuver.getComentId());
			 
		//今天没点过赞 继续点赞（否则点赞失败）(num==0)没有点过赞
		if(num == 0){
			//记录点赞次数
			informationpushDao.updateApprouverNum(approuver.getComentId());
			Informationpush informationpush = informationpushDao.getInformationpushById(approuver.getComentId());
			//增加消息推送（这条推特信息的主人推送消息）
			 messageService.insertMessage(informationpush.getComentBy(), approuver.getId(), Config.TT_DZ, 1);
			//增加一条点赞信息(这个没什么用,但是我不记录怎么判断他今天有没有点赞呢)
			return approuverDao.insertApprouver(approuver);
		}
		else{
			return false;
		}
	}
	public int getCommenterNum(String commentid){
		 return informationpushDao.getCommenterNum(commentid);
	}



/**
 * 获取一个推特信息的评论信息
 * @param pid
 * @return
 */
	public List<Informationpush> getInformationpushListByPid(String pid) {
		// TODO Auto-generated method stub
		return informationpushDao.getInformationpushListByPid(pid);
	}



	/**
	 * 用户收藏信息推特信息(此信息只能收藏一次)
	 * @param collection
	 * @return
	 */
	public boolean collectInformationpush(CollectionPush collection) {
		// TODO Auto-generated method stub
		//查看此推特信息今天是否已收藏
		int flag =collectionDao.isTodayCollectionInfornaionPush(collection.getCollectBy(), collection.getComentId());
		//没有收藏就出现增加收藏记录
		if(flag<=0){
			//推送消息
			Informationpush informationpush = informationpushDao.getInformationpushById(collection.getComentId());
			//增加消息推送（这条推特信息的主人推送消息）
		    messageService.insertMessage(informationpush.getComentBy(), collection.getId(), Config.TT_SC, 1);
			collectionDao.insertCollection(collection);
			return true;
		}else{
			return false;
		}
	}



/**
 * 用户收藏记录分页查找
 * @param userid
 * @param page
 * @return
 */
	public Map<String,Object> getCollects(String collectBy, Integer page) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageSize(Config.COLLCT_NUM);
        pageInfo.setCurrentPageNum(page);
        List<Map<String,Object>> collectlist =  collectionDao.getCollectionlist(collectBy,pageInfo.getStartIndex(),pageInfo.getPageSize());
		Integer collctNum = collectionDao.getTotalCountByUser(collectBy);
	        map.put("items", collectlist);
	        map.put("totalCount", collctNum);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
		
		 return map ;
	}



/**
 * 获取前5个收藏的内容
 * @param userid
 * @return
 */
	public List<Map<String,String>> getCollectFive(String userid) {
		//获取最近收藏的5条推特信息 用于页面的展示
		List<Map<String,String>> collections = collectionDao.getCollectionLatest(userid,  Config.COLLCT_STARTINDEX,Config.COLLCT_NUM);
	/*	//获取该用户收藏的总条数
		int collAllNum = collectionDao.getTotalCount(userid);*/
		
		return collections;
	}


/**
 * 获取所有用户信息
 * @param userid
 * @return
 */
public Map<String,Object> getAllinformation(String userid) {
	//获取用户最近收藏的5条记录
	List<Map<String,String>> collections = collectionDao.getCollectionLatest(userid, Config.COLLCT_STARTINDEX,Config.COLLCT_NUM);
	//获取前一页的推特信息
	PageInfo pageInfo = new PageInfo();
    pageInfo.setCurrentPageNum(1);
	 List<Informationpush> informationpushs = informationpushDao.getInformationpushList(pageInfo.getStartIndex(), pageInfo.getPageSize());
     for(Informationpush e: informationpushs){
     	if(e==null || "".equals(e.getId()))
     		continue;
     	
     	
     	//处理上传的文件
     	List<FileBean> fileList = fileDao.getFileById(e.getId(), Config.INFORMATIONPUSHFILE);
		   if(fileList != null && fileList.size() > 0 )
			   e.setFilelist(fileList);
     }
     //该用户收藏数与评论数与分享数（这个待定目前没弄好）
     
     

	return null;
}

/**
 * 根据用户id或去所有的分享记录（这个地方我们做个分页一次分享多少条）
 * @param userid 用户id
 * @param page 
 * @return
 */
public Map<String,Object> getInformationpushsMessageByUserid(String userid, int page) {
	Map<String,Object> map = new HashMap<String,Object>();
	  PageInfo pageInfo = new PageInfo();
      pageInfo.setCurrentPageNum(page);
      //一次取5条分享记录
      pageInfo.setPageSize(5);
      //转发的信息
      //用户的分享推特信息条数
      Integer pushPartagersNum = informationpushDao.getInformationpushPartagersNumByUserid(userid);
      map.put("partagersNum", pushPartagersNum);
      //获取该用户最近分享的推特信息
      List<Informationpush>  informationPushPartagers = informationpushDao.getInformationpushPartagersByUserid(userid,pageInfo.getPageSize(),pageInfo.getStartIndex());
      map.put("partagers", informationPushPartagers);
      
      //评论的信息
      //评论信息条数
      Integer comentNum = informationpushDao.getInformationpushComentNumByUserid(userid);
      map.put("comentNum", comentNum);
      //根据分页获取5条评论的推特信息
      List<Informationpush> informationPushcoments = informationpushDao.getInformationpushByComenterByUserId(userid,pageInfo.getPageSize(),pageInfo.getStartIndex());
      map.put("comenters", informationPushcoments);
      //收藏信息
      //用户收藏数量
      Integer collectNum = informationpushDao.getInformationpushCollectNumByUserid(userid);
      map.put("collectNum", collectNum);
      //用户收藏的推特信息
      List<Informationpush> informationPushCollect = informationpushDao.getInformationpushByCollectByUserId(userid,pageInfo.getPageSize(),pageInfo.getStartIndex());
      
      return map;
}



/**
 * 一条推特信息收藏的条数
 * @param comentId
 * @return
 */
public Integer getCollectNum(String comentId) {
	// TODO Auto-generated method stub
	return collectionDao.getCollectNum(comentId);
}


}
