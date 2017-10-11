package com.innovative.service;


import com.innovative.bean.Notice;
import com.innovative.dao.FileDao;
import com.innovative.dao.NoticeDao;
import com.innovative.utils.FileUpload;
import com.innovative.utils.HttpClientUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NoticeService {



    @Autowired
    NoticeDao noticeDao;
    @Autowired
    FileDao fileDao;




    /**
     * 公告上传
     * @param title 标题
     * @param file 文件（多个）
     * @return
     */
    public boolean addNotice(Notice notice) {
   
        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        //params.put("id", 1);
        params.put("content", "");
        params.put("createdAt", new Timestamp(System.currentTimeMillis()));
        params.put("createdBy", "");
        params.put("deleted", false);
        params.put("deletedBy", "");
        params.put("title", notice.getTitle());
        //params.put("fileName", file[0].getOriginalFilename());
        params.put("isActive", true);
        params.put("rank", 0);
        params.put("rowVersion", 0);
        params.put("summary", "");
        params.put("tags", "{" + "" + "}");
        params.put("id", notice.getId());
        params.put("updatedBy", "");
       // params.put("file", HttpClientUpload.httpClientUploadFile(file, "noticePhoto"));
        fileDao.updateFile(notice.getId());
        return (noticeDao.addNotice(params) > 0);
    }




    /**
     * 公告列表
     * @return
     */
    public List<Notice> getNotices(){
        return noticeDao.getNotices();
    }





    /**
     * 公告逻辑删除
     * @param id id
     * @return
     */
    public boolean delNotice(String id){

        return (noticeDao.delNotice(id) > 0);
    }




	public boolean updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeDao.updateNotice(notice.getTitle(),notice.getId());
	}






}
