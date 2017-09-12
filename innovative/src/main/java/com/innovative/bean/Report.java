package com.innovative.bean;

import java.sql.Timestamp;

/**
 * 报告，方案线索、寻源报告、会议记录、出差报告、问题记录、项目总结
 * @author fzy
 * @version 2.0
 * */
public class Report {
	private Integer id;//生成的主键id
	private String title;//标题
	private String content;//内容信息
	private String sector;//行业领域
	private String lable;//标签信息
	private String abstracts;//摘要信息
	private String type;//报告的类型，方案线索、寻源报告、会议记录、出差报告、问题记录、项目总结
    private String file;//文件存储路径
    private Timestamp create_date;//创建时间 
	private String create_by;//创建人
	private Timestamp late_date;//创建时间
	private String late_by;//最后修改者
	private Integer deleted;//是否被删除,约定 0 为已删除,1为未删除
	private Timestamp delete_date;//删除时间
	private String delete_by;//删除人
	
	public Report() {
		super();
	}
}
