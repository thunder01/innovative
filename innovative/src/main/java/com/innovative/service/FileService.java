package com.innovative.service;


import com.innovative.bean.Carousel;
import com.innovative.bean.FileBean;
import com.innovative.dao.CarouselDao;
import com.innovative.dao.FileDao;
import com.innovative.utils.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 * @param path 
 * @return
 * @throws IOException
 */
	public boolean uploadFile(MultipartFile[] files, String modname, String refid, String introductions) throws IOException {
		   if (files != null && files.length > 0) {
	               List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	                for (int i = 0; i < files.length; i++) {
	                	Map<String,String> urlmap = null;
	                	urlmap = FileUpload.copyInputStreamToFileForName(files[i], modname);
	                    list.add(urlmap);
	                    
	                }
	                //新增成功返回true 否则false
	                if (list.size()>0)
	                	return (fileDao.addFileList(list, modname, refid,introductions))==list.size() ? true:false;
		   }
		   return false;
	}

	@Transactional
	public boolean uploadFile(MultipartFile[] files, String modname, String refid, String introductions,HttpServletRequest req) throws IOException {
		   if (files != null && files.length > 0) {
	               List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	                for (int i = 0; i < files.length; i++) {
	                	Map<String,String> urlmap = null;
	                	urlmap = FileUpload.copyInputStreamToFileForName(files[i], modname,req);
	                	//如果有上传的图片我们提前给他分离出来我们要看这个图片在数据库中有没有
	                	fileDao.updateAddPhotoToDelete(refid, modname);
	                    list.add(urlmap);
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
