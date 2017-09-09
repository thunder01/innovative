package com.innovative.service;


import com.innovative.bean.Carousel;
import com.innovative.bean.FileBean;
import com.innovative.dao.CarouselDao;
import com.innovative.dao.FileDao;
import com.innovative.utils.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileDao fileDao;


/**
 * 上传文件或图片
 * @param files 文件
 * @param modname 模块名字
 * @param refid 上传引用id
 * @param introductions 
 * @return
 * @throws IOException
 */
	public boolean uploadFile(MultipartFile[] files, String modname, String refid, String introductions) throws IOException {
		   if (files != null && files.length > 0) {
	               List<String> list = new ArrayList<String>();
	                for (int i = 0; i < files.length; i++) {
	                	String url = null;
	                    url = FileUpload.copyInputStreamToFile(files[i], modname);
	                    list.add(url);
	                  // return fileDao.addFile(url,modname,refid);
	                    
	                }
	                //新增成功返回true 否则false
	                if (list.size()>0)
	                	return (fileDao.addFileList(list, modname, refid,introductions))==list.size() ? true:false;
		   }
		   return false;
	}


	public List<FileBean> getFiles(String userid, String modname) {
		return fileDao.getFileById(userid,modname);
	}

}
