package com.innovative.dao;

import com.innovative.bean.FileBean;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface FileDao {


	/**
	 * 单个增加
	 * @param url
	 * @param reftype
	 * @param refid
	 * @return
	 */
	
	boolean addFile(@Param(value="url")String url,@Param(value="reftype") String reftype, @Param(value="refid")String refid);
	/*
	 * 批量增加
	 */
	int addFileList(@Param(value="urllist")List<Map<String, String>> list,@Param(value="reftype") String reftype, @Param(value="refid")String refid, @Param(value="type") String type);
	
	List<FileBean> getFileById(@Param(value="refid")String refid, @Param(value="reftype")String reftype);
	
	boolean updateFile(@Param(value="refid")String refid);
	
	boolean deleteFile(@Param(value="refid")String refid);
	
	List<String> getPhotoByMOdAndId(@Param("id")String id ,@Param("refType") String mod);
	//重复上传图片的时候把之前的图片变成已删除状态
	void updateAddPhotoToDelete(@Param("refid")String refid,@Param("refType") String refType);
	//删除指定的莫个文件
	boolean deleteZdFile(@Param(value="refid")String refid,@Param(value="filename")String filename);
	//删除之前的文件
	void deleteFiles(@Param(value="refid")String refid, @Param(value="reftype")String reftype);
	//获取轮播图片
	List<FileBean> getLunbophoto(@Param(value="reftype")String indexLunbo, @Param(value="limits")int limits);

}
