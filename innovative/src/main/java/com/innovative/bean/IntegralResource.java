package com.innovative.bean;

import java.io.Serializable;

/**
 * 创新资源详情页进入次数
 * @author huang
 *
 */
public class IntegralResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id ;
	private String type ;
	private String resource_id;
	private int count;
	
	public IntegralResource() {}

	public IntegralResource(Integer id, String type, String resource_id, int count) {
		super();
		this.id = id;
		this.type = type;
		this.resource_id = resource_id;
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "IntegralResource [id=" + id + ", type=" + type + ", resource_id=" + resource_id + ", count=" + count
				+ "]";
	}
	
	
}
