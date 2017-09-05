package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */
public class FileBean  {

	/**
	 * 
	 */
	private MultipartFile[] file ;
	private String modname;
	
    public FileBean() {
    }
 


	public MultipartFile[] getFile() {
		return file;
	}



	public void setFile(MultipartFile[] file) {
		this.file = file;
	}



	public String getModname() {
		return modname;
	}

	public void setModname(String modname) {
		this.modname = modname;
	}
    
  
}
