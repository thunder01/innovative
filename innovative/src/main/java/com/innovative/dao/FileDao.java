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
	int addFileList(@Param(value="urllist")List<Map<String, String>> list,@Param(value="reftype") String reftype, @Param(value="refid")String refid, @Param(value="introductions") String introductions);
	
	List<FileBean> getFileById(@Param(value="refid")String refid, @Param(value="reftype")String reftype);
	
	boolean updateFile(@Param(value="refid")String refid);
	
	boolean deleteFile(@Param(value="refid")String refid);
	
	List<String> getPhotoByMOdAndId(@Param("id")String id ,@Param("refType") String mod);

}
